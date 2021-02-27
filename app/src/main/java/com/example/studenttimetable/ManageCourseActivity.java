package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Booking;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;

public class ManageCourseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView courseNameTv;
    private TextView courseFieldTv;

    private Button editButton;
    private Button deleteButton;
    private BackgroundTask backgroundTask = null;

    private String adminID = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_course);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiViews();
        initToolbar();
    }

    public void setupUiViews(){

        Course course = SharedPrefManager.getInstance(getApplicationContext()).getSelectedCourse();
        adminID = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        toolbar = (Toolbar) findViewById(R.id.ToolbarManageCourseDetails);

        courseNameTv = (TextView)findViewById(R.id.courseNameManage);
        courseFieldTv = (TextView) findViewById(R.id.courseFieldManage);

        editButton = (Button) findViewById(R.id.editCourse);
        deleteButton = (Button) findViewById(R.id.deleteCourse);

        System.out.println(course.getId());
        courseNameTv.setText(String.valueOf(course.getCourseName()));
        courseFieldTv.setText(course.getCourseField());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ManageCourseActivity.this,EditCourseActivity.class));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("COURSE ID : "+ course.getId());
                backgroundTask.deleteParticularCourse(course.getId(),adminID,ManageCourseActivity.this);
            }
        });

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Courses");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}