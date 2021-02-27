package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.SharedPrefManager;

public class AddNewCourseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText newCourseName;
    private EditText courseField;
    private Button addButton;
    private BackgroundTask backgroundTask =null;

    private String adminID  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_course);

        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
    }



    public void setupUiView(){
        toolbar = (Toolbar) findViewById(R.id.ToolbarAddNewCourse);
        newCourseName =(EditText) findViewById(R.id.newCourseName);
        courseField = (EditText) findViewById(R.id.courseField);
        addButton = (Button) findViewById(R.id.addCourseButton);

        adminID = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundTask.addNewCourse(newCourseName.getText().toString(),courseField.getText().toString(),adminID, AddNewCourseActivity.this);
            }
        });
    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Course");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}