package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Classroom;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;

import static java.lang.Integer.parseInt;

public class EditClassroomActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText classNameEtv;
    private EditText floorLevelEtv;
    private TextView isAllocatedTv;
    private Button submitButton;

    private BackgroundTask backgroundTask = null;
    private String adminId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_classroom);

        backgroundTask = new BackgroundTask(getApplicationContext());
        setupUiViews();
        initToolbar();
    }
    public void setupUiViews() {
        Classroom classroom = SharedPrefManager.getInstance(getApplicationContext()).getSelectedClassroomAll();

        toolbar = (Toolbar) findViewById(R.id.ToolbarLecturerEditClassDetails);


        classNameEtv = (EditText) findViewById(R.id.editClassName);
        floorLevelEtv = (EditText) findViewById(R.id.editFloorLevel);
        isAllocatedTv = (TextView) findViewById(R.id.editIsAllocated);
        submitButton = (Button) findViewById(R.id.submitEdittedClass) ;

        classNameEtv.setText(String.valueOf(classroom.getClassroomName()));
        floorLevelEtv.setText(String.valueOf(classroom.getFloorLevel()));
        if(classroom.getIsAllocatedForCourseModule() == 1){
            isAllocatedTv.setText("Yes");
        }else{
            isAllocatedTv.setText("No");
        }

        adminId = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className = classNameEtv.getText().toString();
                String floorLevel = floorLevelEtv.getText().toString();
                int isAllocated =classroom.getIsAllocatedForCourseModule();
               backgroundTask.editParticularClassroom(classroom.getId(),className,floorLevel,isAllocated,adminId,EditClassroomActivity.this);
            }
        });
    }
    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Classroom Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}