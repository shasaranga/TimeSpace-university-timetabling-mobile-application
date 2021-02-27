package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class AddNewBadgeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner courseNames;
    private EditText badgeName;
    private EditText badgeYear;
    private Button addButton;

    private BackgroundTask backgroundTask = null;
    private List<Course> courseList = new ArrayList<>();
    private List<String> courseNamesList = new ArrayList<>();
    private int selectedCourseId;
    private String adminId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_badge);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setUiViews();
        initToolbar();
        setAdapters();
    }

    public void setUiViews(){
        courseList = SharedPrefManager.getInstance(getApplicationContext()).getCourseList();
        courseNamesList = SharedPrefManager.getInstance(getApplicationContext()).getCourseNamesList();
        adminId = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        toolbar = (Toolbar) findViewById(R.id.ToolbarAddNewBadge);
        courseNames = (Spinner) findViewById(R.id.allocatedCourseName);
        badgeName = (EditText) findViewById(R.id.newBadgeName);
        badgeYear =(EditText) findViewById(R.id.newBadgeYear);
        addButton =(Button) findViewById(R.id.addBadgeButton);

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Badge");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setAdapters() {
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>
                (AddNewBadgeActivity.this, R.layout.color_spinner_layout,
                        courseNamesList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };

        courseAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        courseNames.setAdapter(courseAdapter);

        courseNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                selectedCourseId = SharedPrefManager.getInstance(getApplicationContext()).getCourseID(selectedItem,courseList);
                System.out.println("SELECTED COURSE ID : "+ selectedCourseId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newBadgeName = badgeName.getText().toString();
                String newBadgeYear = badgeYear.getText().toString();
                backgroundTask.addNewBadge(selectedCourseId,newBadgeName,newBadgeYear,adminId,AddNewBadgeActivity.this);
            }
        });
    }
}