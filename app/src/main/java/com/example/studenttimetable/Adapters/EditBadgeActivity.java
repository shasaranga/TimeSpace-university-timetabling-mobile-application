package com.example.studenttimetable.Adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studenttimetable.EditCourseActivity;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;
import com.example.studenttimetable.R;

import static java.lang.Integer.parseInt;

public class EditBadgeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText badgeNameEt;
    private EditText badgeYearEt;
    private Button submitButton;

    private BackgroundTask backgroundTask = null;
    private String adminId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_badge);

        backgroundTask = new BackgroundTask(getApplicationContext());
        setupUiViews();
        initToolbar();
    }

    public void setupUiViews() {
        Badge badge = SharedPrefManager.getInstance(getApplicationContext()).getSelectedBadge();

        toolbar = (Toolbar) findViewById(R.id.ToolbarEditBadgeDetails);

        badgeNameEt = (EditText) findViewById(R.id.editBadgeName);
        badgeYearEt = (EditText) findViewById(R.id.editBadgeYear);
        submitButton = (Button) findViewById(R.id.submitEdittedBadge) ;

        badgeNameEt.setText(badge.getBadgeName());
        badgeYearEt.setText(String.valueOf(badge.getBadgeYear()));

        adminId = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String badgeName = badgeNameEt.getText().toString();
                String badgeYear = badgeYearEt.getText().toString();
                backgroundTask.editParticularBadge(badge.getCourseID(),badge.getId(),badgeName,badgeYear,adminId,EditBadgeActivity.this);
            }
        });
    }
    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Badge Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}