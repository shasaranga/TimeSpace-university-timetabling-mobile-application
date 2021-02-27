package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.studenttimetable.Adapters.BadgeAdapter;
import com.example.studenttimetable.Adapters.CourseAdapter;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class BadgesListViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;

    private BackgroundTask backgroundTask =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges_list_view);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setupListView();
    }

    public void setupUiView(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarBadgesList);
        listView = (ListView)findViewById(R.id.lvBadgesListView);

    }

    private void initToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Available Badges");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    private void setupListView() {
        List<String> badgesNameList = new ArrayList<>();
        List<Badge> list= SharedPrefManager.getInstance(getApplicationContext()).getBadgeList();
        for(int i =0; i< list.size(); i++){
            badgesNameList.add(list.get(i).getBadgeName());
        }
        if(badgesNameList.isEmpty()){
            badgesNameList.add("No Badges Available!");
            System.out.println("BADGES LIST SIZE : "+ badgesNameList.size());
            BadgeAdapter adapter = new BadgeAdapter(this, R.layout.activity_single_item_reuable_common_card, badgesNameList,list);
            listView.setAdapter(adapter);

        }else{
            System.out.println("COURSE NAMES LIST SIZE : "+ badgesNameList.size());
            BadgeAdapter adapter = new BadgeAdapter(this, R.layout.activity_single_item_reuable_common_card, badgesNameList,list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedBadgePosition(position);
                    startActivity(new Intent(BadgesListViewActivity.this,ManageBadgeActivity.class));
                }
            });
        }


    }
}