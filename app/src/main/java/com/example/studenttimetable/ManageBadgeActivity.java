package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studenttimetable.Adapters.EditBadgeActivity;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;

public class ManageBadgeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView courseNameTv;
    private TextView badgeNameTv;
    private TextView badgeYearTv;

    private Button editButton;
    private Button deleteButton;
    private BackgroundTask backgroundTask = null;

    private String adminID = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_badge);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiViews();
        initToolbar();
    }
    public void setupUiViews(){

        Badge badge = SharedPrefManager.getInstance(getApplicationContext()).getSelectedBadge();
        adminID = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        toolbar = (Toolbar) findViewById(R.id.ToolbarManageBadgeDetails);

        courseNameTv = (TextView)findViewById(R.id.courseNameManageBadge);
        badgeNameTv = (TextView) findViewById(R.id.badgeNameManage);
        badgeYearTv = (TextView) findViewById(R.id.badgeYear);

        editButton = (Button) findViewById(R.id.editBadge);
        deleteButton = (Button) findViewById(R.id.deleteBadge);

        System.out.println(badge.getCourseName());
        courseNameTv.setText(badge.getCourseName());
        badgeNameTv.setText(badge.getBadgeName());
        badgeYearTv.setText(String.valueOf(badge.getBadgeYear()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ManageBadgeActivity.this, EditBadgeActivity.class));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("BADGE ID : "+ badge.getId());
                backgroundTask.deleteParticularBadge(badge.getCourseID(),badge.getId(),adminID,ManageBadgeActivity.this);
            }
        });

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Badge");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}