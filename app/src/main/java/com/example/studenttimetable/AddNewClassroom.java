package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.SharedPrefManager;

public class AddNewClassroom extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText newClassnameEt;
    private EditText floorYearEt;
    private CheckBox isAllocatedForModuleCheckbox;
    private Button addButton;
    private BackgroundTask backgroundTask =null;

    private String adminID  = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_classroom);

        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
    }

    public void setupUiView(){
        toolbar = (Toolbar) findViewById(R.id.ToolbarAddNewClassroom);
        newClassnameEt =(EditText) findViewById(R.id.newClassroomName);
        floorYearEt = (EditText) findViewById(R.id.classroomFloorLevel);
        isAllocatedForModuleCheckbox = (CheckBox) findViewById(R.id.isAllocatedForCourseModule);
        addButton = (Button) findViewById(R.id.addClassroomButton);

        adminID = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(isAllocatedForModuleCheckbox.isChecked());
                boolean isAllocated = isAllocatedForModuleCheckbox.isChecked();
                String newClassName = newClassnameEt.getText().toString();
                String floor = floorYearEt.getText().toString();
                backgroundTask.addNewClassroom(newClassName,floor,isAllocated,adminID,AddNewClassroom.this);
            }
        });
    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Classroom");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}