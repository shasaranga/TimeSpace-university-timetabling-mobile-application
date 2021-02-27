package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;

import static java.lang.Integer.parseInt;

public class EditCourseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView courseIDTv;
    private EditText courseNameTv;
    private EditText coursefieldTv;
    private Button submitButton;

    private BackgroundTask backgroundTask = null;
    private String adminId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        backgroundTask = new BackgroundTask(getApplicationContext());
        setupUiViews();
        initToolbar();
    }
    public void setupUiViews() {
        Course course = SharedPrefManager.getInstance(getApplicationContext()).getSelectedCourse();

        toolbar = (Toolbar) findViewById(R.id.ToolbarLecturerEditCourseDetails);

        courseIDTv = (TextView) findViewById(R.id.editCourseId);
        courseNameTv = (EditText) findViewById(R.id.editCourseName);
        coursefieldTv = (EditText) findViewById(R.id.editCourseField);
        submitButton = (Button) findViewById(R.id.submitEdittedCourse) ;

        courseIDTv.setText(String.valueOf(course.getId()));
        courseNameTv.setText(course.getCourseName());
        coursefieldTv.setText(course.getCourseField());

        adminId = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = courseNameTv.getText().toString();
                String courseField = coursefieldTv.getText().toString();
                int courseID = parseInt(courseIDTv.getText().toString());
                backgroundTask.editParticularCourse(courseID,courseName,courseField,adminId,EditCourseActivity.this);
            }
        });
    }
    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Course");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}