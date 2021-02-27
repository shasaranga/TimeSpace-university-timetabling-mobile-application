package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;

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

import androidx.appcompat.widget.Toolbar;

public class PreferencesOptionView extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;

    private BackgroundTask backgroundTask = null;
    private List<Lecturer>lecturerList = new ArrayList<>();
    private List<Classroom>classroomList = new ArrayList<>();
    private String currentUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_option_view);
        backgroundTask = new BackgroundTask(getApplicationContext());
        SharedPrefManager.getInstance(getApplicationContext()).setLecturerOptionView(PreferencesOptionView.this);
        classroomList = backgroundTask.getAvailableClassrooms();
        List<Classroom>classroomModuleList =backgroundTask.getAvailableClassroomsForModules();

        currentUser = SharedPrefManager.getInstance(getApplicationContext()).getCurrentUserType();
        System.out.println(currentUser);
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
        getSupportActionBar().setTitle("Preferences");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        backgroundTask.logoutUser(PreferencesOptionView.this);
        return true;
    }
    public void setupListView(){
        if(currentUser.equals("LECTURER")){
            String [] titleArray = getResources().getStringArray(R.array.LecturerOptions);
            String [] descriptionArray = getResources().getStringArray(R.array.LecturerOptionsDescription);

            PreferencesOptionsAdapter adapter = new PreferencesOptionsAdapter(this,titleArray,descriptionArray,currentUser,false);
            listView.setAdapter(adapter);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0: {

                            lecturerList = backgroundTask.getAvailableLecturers(PreferencesOptionView.this,"PREFERENCES");
                            // startActivity(new Intent(LecturerOptionView.this, LecturerTimetableSearch.class));
                            break;
                        }
                        case 1: {

                            startActivity(new Intent(PreferencesOptionView.this, TimetableSearchActivity.class));
                            break;
                        }
                        case 2: {

                            startActivity(new Intent(PreferencesOptionView.this, LecturerBookClassroom.class));
                            break;
                        }
                        case 3: {

//                        backgroundTask.getLecturerBooking(SharedPrefManager.getInstance(getApplicationContext()).getLecturerInfo().getLecturerID(),LecturerOptionView.this);
                            backgroundTask.getLecturerBooking(SharedPrefManager.getInstance(getApplicationContext()).getLecturerInfo().getLecturerID());
                            break;
                        }

                        default:break;
                    }
                }
            });
        }
        else if(currentUser.equals("STUDENT")){
            String [] titleArray = getResources().getStringArray(R.array.StudentOptions);
            String [] descriptionArray = getResources().getStringArray(R.array.StudentOptionsDescription);

            PreferencesOptionsAdapter adapter = new PreferencesOptionsAdapter(this,titleArray,descriptionArray,currentUser,false);
            listView.setAdapter(adapter);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0: {

                            lecturerList = backgroundTask.getAvailableLecturers(PreferencesOptionView.this,"PREFERENCES");
                            // startActivity(new Intent(LecturerOptionView.this, LecturerTimetableSearch.class));
                            break;
                        }
                        case 1: {

                            startActivity(new Intent(PreferencesOptionView.this, TimetableSearchActivity.class));
                            break;
                        }

                        default:break;
                    }
                }
            });
        }
        else if(currentUser.equals("ADMIN")){
            String [] titleArray = getResources().getStringArray(R.array.AdminOptions);
            String [] descriptionArray = getResources().getStringArray(R.array.AdminOptionsDescription);

            PreferencesOptionsAdapter adapter = new PreferencesOptionsAdapter(this,titleArray,descriptionArray,currentUser,false);
            listView.setAdapter(adapter);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0: {

                            lecturerList = backgroundTask.getAvailableLecturers(PreferencesOptionView.this,"PREFERENCES");
                            // startActivity(new Intent(LecturerOptionView.this, LecturerTimetableSearch.class));
                            break;
                        }
                        case 1: {

                            startActivity(new Intent(PreferencesOptionView.this, TimetableSearchActivity.class));
                            break;
                        }
                        case 2: {
                            SharedPrefManager.getInstance(getApplicationContext()).setAdminSelectedOption("MANAGE_COURSES");
                            startActivity(new Intent(PreferencesOptionView.this, SubPreferencesActivity.class));
                            break;
                        }
                        case 3: {
                            SharedPrefManager.getInstance(getApplicationContext()).setAdminSelectedOption("MANAGE_BADGES");
                            startActivity(new Intent(PreferencesOptionView.this, SubPreferencesActivity.class));
                            break;
                        }
                        case 4: {
                            SharedPrefManager.getInstance(getApplicationContext()).setAdminSelectedOption("MANAGE_CLASSROOMS");
                            startActivity(new Intent(PreferencesOptionView.this, SubPreferencesActivity.class));
                            break;
                        }

                        default:break;
                    }
                }
            });
        }

    }


}