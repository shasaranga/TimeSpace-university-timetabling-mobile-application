package com.example.studenttimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.Module;
import com.example.studenttimetable.Model.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ManageModuleActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView courseNameTv;
    private TextView moduleNameTv;
    private TextView moduleDescTv;
    private TextView lecturerNameTv;
    private TextView classroomNameTv;

    private Button editButton;
    private Button deleteButton;
    private BackgroundTask backgroundTask = null;

    private String adminID = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_module);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiViews();
        initToolbar();
    }

    public void setupUiViews(){

        Module module = SharedPrefManager.getInstance(getApplicationContext()).getSelectedModule();
        adminID = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        toolbar = (Toolbar) findViewById(R.id.ToolbarManageModuleDetails);

        courseNameTv = (TextView)findViewById(R.id.courseNameManageModule);
        moduleNameTv = (TextView) findViewById(R.id.moduleNameManage);
        moduleDescTv = (TextView) findViewById(R.id.moduleDescManage);
        lecturerNameTv = (TextView) findViewById(R.id.assignedLecturer);
        classroomNameTv = (TextView) findViewById(R.id.assignedClass);

        editButton = (Button) findViewById(R.id.editModule);
        deleteButton = (Button) findViewById(R.id.deleteModule);

        System.out.println(module.getModuleID());
        courseNameTv.setText(String.valueOf(module.getCourseName()));
        moduleNameTv.setText(module.getModuleName());
        moduleDescTv.setText(module.getModuleDescription());
        lecturerNameTv.setText(module.getLecturerFullName());
        classroomNameTv.setText(module.getClassName());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backgroundTask.getAvailableLecturers(ManageModuleActivity.this,"EDIT_MODULE");
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("MODULE ID : "+ module.getModuleID());
                backgroundTask.deleteParticularModule(module.getCourseID(),module.getModuleID(),adminID, ManageModuleActivity.this);
            }
        });

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Module");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}