package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.studenttimetable.Adapters.WeekAdapter;
import com.example.studenttimetable.Model.BackgroundTask;

public class WeekActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String selectedDay;
//    public static ArrayList<Timetable> recordlist = new ArrayList<>();
    private BackgroundTask backgroundTask =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        backgroundTask = new BackgroundTask(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        setupUIViews();
        initToolbar();
        setupListView();
    }
    private void setupUIViews(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarWeek);
        listView = (ListView)findViewById(R.id.lvWeek);
        sharedPreferences = getSharedPreferences("MY_DAY",MODE_PRIVATE);

    }
    private void initToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Week Days");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void setupListView(){
        String[] week = getResources().getStringArray(R.array.Week);
        WeekAdapter adapter = new WeekAdapter(this, R.layout.activity_week_single_item, week);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: {
                        startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(selectedDay, "Monday").apply();


                        break;
                    }
                    case 1: {
                        startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(selectedDay, "Tuesday").apply();
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(selectedDay, "Wednesday").apply();
                        break;
                    }
                    case 3: {
                        startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(selectedDay, "Thursday").apply();
                        break;
                    }
                    case 4: {
                        startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(selectedDay, "Friday").apply();
                        break;
                    }
                    case 5: {
                        startActivity(new Intent(WeekActivity.this, DayDetail.class));
                        sharedPreferences.edit().putString(selectedDay, "Saturday").apply();
                        break;
                    }

                    default:break;
                }
            }
        });
    }


}
