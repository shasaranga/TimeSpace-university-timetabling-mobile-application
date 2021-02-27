package com.example.studenttimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.studenttimetable.Adapters.CourseAdapter;
import com.example.studenttimetable.Adapters.ModuleAdapter;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.Module;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ModuleListViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;

    private BackgroundTask backgroundTask =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list_view);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setupListView();
    }

    public void setupUiView(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarModulesList);
        listView = (ListView)findViewById(R.id.lvModuleListView);

    }

    private void initToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Available Modules");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupListView() {
        List<String> moduleNameList = new ArrayList<>();
        List<Module> list= SharedPrefManager.getInstance(getApplicationContext()).getModuleList();
        for(int i =0; i< list.size(); i++){
            moduleNameList.add(list.get(i).getModuleName());
        }
        if(moduleNameList.isEmpty()){
            moduleNameList.add("No Modules Available!");
            System.out.println("MODULES LIST SIZE : "+ moduleNameList.size());
            ModuleAdapter adapter = new ModuleAdapter(this, R.layout.activity_single_item_reuable_common_card, moduleNameList,list);
            listView.setAdapter(adapter);

        }else{
            System.out.println("COURSE NAMES LIST SIZE : "+ moduleNameList.size());
            ModuleAdapter adapter = new ModuleAdapter(this, R.layout.activity_single_item_reuable_common_card, moduleNameList,list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedBookingPosition(position);
                   startActivity(new Intent(ModuleListViewActivity.this,ManageModuleActivity.class));
                }
            });
        }


    }
}