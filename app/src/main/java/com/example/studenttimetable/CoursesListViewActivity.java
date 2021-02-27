package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.studenttimetable.Adapters.CourseAdapter;
import com.example.studenttimetable.Adapters.LecturerBookingAdapter;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Booking;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class CoursesListViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;

    private BackgroundTask backgroundTask =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list_view);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setupListView();
    }

    public void setupUiView(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarCoursesList);
        listView = (ListView)findViewById(R.id.lvCourseListView);

    }

    private void initToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Available Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupListView() {
        List<String> courseNamesList = new ArrayList<>();
        List<Course> list= SharedPrefManager.getInstance(getApplicationContext()).getCourseList();
        for(int i =0; i< list.size(); i++){
            courseNamesList.add(list.get(i).getCourseName());
        }
        if(courseNamesList.isEmpty()){
            courseNamesList.add("No Courses Available!");
            System.out.println("COURSE LIST SIZE : "+ courseNamesList.size());
            CourseAdapter adapter = new CourseAdapter(this, R.layout.activity_single_item_reuable_common_card, courseNamesList,list);
            listView.setAdapter(adapter);

        }else{
            System.out.println("COURSE NAMES LIST SIZE : "+ courseNamesList.size());
            CourseAdapter adapter = new CourseAdapter(this, R.layout.activity_single_item_reuable_common_card, courseNamesList,list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedCoursePosition(position);
                    startActivity(new Intent(CoursesListViewActivity.this,ManageCourseActivity.class));
                }
            });
        }


    }
}