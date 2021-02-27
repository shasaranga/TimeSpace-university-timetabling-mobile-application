package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.studenttimetable.Adapters.PreferencesOptionsAdapter;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Classroom;
import com.example.studenttimetable.Model.Lecturer;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class SubPreferencesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;

    private BackgroundTask backgroundTask = null;
    private List<Lecturer> lecturerList = new ArrayList<>();
    private List<Classroom>classroomList = new ArrayList<>();
    private String currentUser = null;
    private String selectedMainOption = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_option_view);
        backgroundTask = new BackgroundTask(getApplicationContext());
        classroomList = backgroundTask.getAvailableClassrooms();

        currentUser = SharedPrefManager.getInstance(getApplicationContext()).getCurrentUserType();
        System.out.println(currentUser);
        selectedMainOption = SharedPrefManager.getInstance(getApplicationContext()).getAdminSelectedOption();

        setupUiView();
        initToolbar();
        setupListView();

    }

    public void setupUiView(){

        toolbar = (Toolbar) findViewById(R.id.ToolbarLecturerOptionView);
        listView = (ListView) findViewById(R.id.LecturerOptionList);

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);

        if(selectedMainOption.equals("MANAGE_COURSES")){
            getSupportActionBar().setTitle("Manage Courses");
        }else if(selectedMainOption.equals("MANAGE_BADGES")){
            getSupportActionBar().setTitle("Manage Badges");
        }else if(selectedMainOption.equals("MANAGE_CLASSROOMS")){
            getSupportActionBar().setTitle("Manage Classrooms");
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void setupListView(){
        if(selectedMainOption.equals("MANAGE_COURSES")){
            if(currentUser.equals("ADMIN")){
                String [] titleArray = getResources().getStringArray(R.array.AdminManageCourseSubOptions);
                String [] descriptionArray = getResources().getStringArray(R.array.AdminManageCourseSubOptionsDescription);

                PreferencesOptionsAdapter adapter = new PreferencesOptionsAdapter(this,titleArray,descriptionArray,currentUser, true);
                listView.setAdapter(adapter);



                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0: {

                                 startActivity(new Intent(SubPreferencesActivity.this, AddNewCourseActivity.class));
                                break;
                            }
                            case 1: {
                                lecturerList = backgroundTask.getAvailableLecturers(SubPreferencesActivity.this,"SUB_PREFERENCES");
                                //startActivity(new Intent(SubPreferencesActivity.this, AddNewModuleActivity.class));
                                break;
                            }
                            case 2: {
                                backgroundTask.getCourseDetails(SubPreferencesActivity.this);

                                break;
                            }
                            case 3: {
                                backgroundTask.getModuleDetails(SubPreferencesActivity.this);

                                break;
                            }

                            default:break;
                        }
                    }
                });
            }
        }
        else if(selectedMainOption.equals("MANAGE_BADGES")){
            if(currentUser.equals("ADMIN")){
                String [] titleArray = getResources().getStringArray(R.array.AdminManageBadgeSubOptions);
                String [] descriptionArray = getResources().getStringArray(R.array.AdminManageBadgeSubOptionsDescription);

                PreferencesOptionsAdapter adapter = new PreferencesOptionsAdapter(this,titleArray,descriptionArray,currentUser, true);
                listView.setAdapter(adapter);



                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0: {

                                startActivity(new Intent(SubPreferencesActivity.this, AddNewBadgeActivity.class));
                                break;
                            }
                            case 1: {
                                backgroundTask.getAvailableBadges(SubPreferencesActivity.this,"SUB_PREFERENCES_MANAGE_BADGES");
                                //startActivity(new Intent(SubPreferencesActivity.this, AddNewModuleActivity.class));
                                break;
                            }


                            default:break;
                        }
                    }
                });
            }
        }

        else if(selectedMainOption.equals("MANAGE_CLASSROOMS")){
            if(currentUser.equals("ADMIN")){
                String [] titleArray = getResources().getStringArray(R.array.AdminManageClassroomsSubOptions);
                String [] descriptionArray = getResources().getStringArray(R.array.AdminManageClassroomsSubOptionsDescription);

                PreferencesOptionsAdapter adapter = new PreferencesOptionsAdapter(this,titleArray,descriptionArray,currentUser, true);
                listView.setAdapter(adapter);



                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0: {

                                startActivity(new Intent(SubPreferencesActivity.this, AddNewClassroom.class));
                                break;
                            }
                            case 1: {
                                backgroundTask.getAllClassrooms(SubPreferencesActivity.this);
                                //startActivity(new Intent(SubPreferencesActivity.this, AddNewModuleActivity.class));
                                break;
                            }


                            default:break;
                        }
                    }
                });
            }
        }
    }
}