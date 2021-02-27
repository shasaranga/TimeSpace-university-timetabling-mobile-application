package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Course;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView regLink;
    private Button loginBtn;
    private BackgroundTask backgroundTask = null;
    private List<Course> courseList = new ArrayList<>();
    private List<String> courseNamesList = new ArrayList<>();

    private List<Badge> badgeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backgroundTask = new BackgroundTask(getApplicationContext());
        courseList = backgroundTask.getCourseDetails(null);
        email = (EditText) findViewById(R.id.email2);
        password = (EditText)findViewById(R.id.pass2);

        
        loginBtn = (Button) findViewById(R.id.Login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {

       backgroundTask.loginUser(this,email.getText().toString(),password.getText().toString());

    }
}