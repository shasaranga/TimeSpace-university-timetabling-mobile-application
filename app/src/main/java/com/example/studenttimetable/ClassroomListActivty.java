package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.studenttimetable.Adapters.BadgeAdapter;
import com.example.studenttimetable.Adapters.ClassroomAdapter;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Classroom;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ClassroomListActivty extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;

    private BackgroundTask backgroundTask =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_list_activty);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setupListView();
    }

    public void setupUiView(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarClassList);
        listView = (ListView)findViewById(R.id.lvClassListView);

    }

    private void initToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Available Classrooms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    private void setupListView() {
        List<String> classroomNameList = new ArrayList<>();
        List<Classroom> list= SharedPrefManager.getInstance(getApplicationContext()).getAllClassroomList();
        for(int i =0; i< list.size(); i++){
            classroomNameList.add(list.get(i).getClassroomName());
        }
        if(classroomNameList.isEmpty()){
            classroomNameList.add("No Classroom Available!");
            System.out.println("CLASSES LIST SIZE : "+ classroomNameList.size());
            ClassroomAdapter adapter = new ClassroomAdapter(this, R.layout.activity_single_item_reuable_common_card, classroomNameList,list);
            listView.setAdapter(adapter);

        }else{
            System.out.println("CLASSES NAMES LIST SIZE : "+ classroomNameList.size());
            ClassroomAdapter adapter = new ClassroomAdapter(this, R.layout.activity_single_item_reuable_common_card, classroomNameList,list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedClassAllPosition(position);
                    startActivity(new Intent(ClassroomListActivty.this,ManageClassroomActivity.class));
                }
            });
        }


    }
}