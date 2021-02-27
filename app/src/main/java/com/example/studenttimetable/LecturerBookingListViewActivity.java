package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.studenttimetable.Adapters.LecturerBookingAdapter;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Booking;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class LecturerBookingListViewActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView listView;

    private BackgroundTask backgroundTask =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_booking_list_view);

        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setupListView();
    }

    public void setupUiView(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarLecturerBookingList);
        listView = (ListView)findViewById(R.id.lvLecturerBookingView);

    }

    private void initToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Bookings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupListView() {
        List<String> bookedClassNames = new ArrayList<>();
        List<Booking> list= SharedPrefManager.getInstance(getApplicationContext()).getBookingList();
        for(int i =0; i< list.size(); i++){
            bookedClassNames.add(list.get(i).getClassName());
        }
        if(bookedClassNames.isEmpty()){
            bookedClassNames.add("No Booking Available!");
            System.out.println("BOOKED CLASS NAMES LIST SIZE : "+ bookedClassNames.size());
            LecturerBookingAdapter adapter = new LecturerBookingAdapter(this, R.layout.activity_bookings_single_item, bookedClassNames,list);
            listView.setAdapter(adapter);

        }else{
            System.out.println("BOOKED CLASS NAMES LIST SIZE : "+ bookedClassNames.size());
            LecturerBookingAdapter adapter = new LecturerBookingAdapter(this, R.layout.activity_bookings_single_item, bookedClassNames,list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SharedPrefManager.getInstance(getApplicationContext()).setSelectedBookingPosition(position);
                    startActivity(new Intent(LecturerBookingListViewActivity.this,LecturerManageBooking.class));
                }
            });
        }


    }
}