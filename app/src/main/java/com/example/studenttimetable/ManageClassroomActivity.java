package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Classroom;
import com.example.studenttimetable.Model.SharedPrefManager;

public class ManageClassroomActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView classNameTv;
    private TextView floorLevelTv;
    private TextView isAllocatedTv;

    private Button editButton;
    private Button deleteButton;
    private BackgroundTask backgroundTask = null;

    private String adminID = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classroom);

        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiViews();
        initToolbar();
    }

    public void setupUiViews(){

        Classroom classroom = SharedPrefManager.getInstance(getApplicationContext()).getSelectedClassroomAll();
        adminID = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        toolbar = (Toolbar) findViewById(R.id.ToolbarManageClassDetails);

        classNameTv = (TextView)findViewById(R.id.classNameManage);
        floorLevelTv = (TextView) findViewById(R.id.classroomFloorLevelManage);
        isAllocatedTv = (TextView) findViewById(R.id.isAllocatedForCourseModule);

        editButton = (Button) findViewById(R.id.editClassroom);
        deleteButton = (Button) findViewById(R.id.deleteClassroom);

        System.out.println(classroom.getClassroomName());
        classNameTv.setText(classroom.getClassroomName());
        floorLevelTv.setText(String.valueOf(classroom.getFloorLevel()));

        if(classroom.getIsAllocatedForCourseModule() == 1){
            isAllocatedTv.setText("Yes");
        }else{
            isAllocatedTv.setText("No");
        }


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ManageClassroomActivity.this,EditClassroomActivity.class));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("BADGE ID : "+ classroom.getId());
                backgroundTask.deleteParticularClassroom(classroom.getId(),adminID,ManageClassroomActivity.this);
            }
        });

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Classroom");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}