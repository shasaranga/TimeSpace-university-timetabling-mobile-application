 package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Booking;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LecturerManageBooking extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView bookingIdTv;
    private TextView classroomId;
    private TextView badgeNameTv;
    private TextView lecturerIDTv;
    private TextView scheduledForDate;
    private TextView durationBooking;
    private Button editButton;
    private Button deleteButton;



    private BackgroundTask backgroundTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_manage_booking);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiViews();
        initToolbar();
    }

    public void setupUiViews(){

        Booking booking = SharedPrefManager.getInstance(getApplicationContext()).getLecturerSelectedBooking();

        toolbar = (Toolbar) findViewById(R.id.ToolbarLecturerManageBookingDetails);

        bookingIdTv = (TextView)findViewById(R.id.bookingId);
        classroomId = (TextView) findViewById(R.id.classroomIdBooking);
        badgeNameTv = (TextView) findViewById(R.id.badgeNameIDBooking);
        lecturerIDTv = (TextView) findViewById(R.id.lecturerIDBooking);
        scheduledForDate= (TextView) findViewById(R.id.scheduledForDate);
        durationBooking = (TextView) findViewById(R.id.durationBooking);
        editButton = (Button) findViewById(R.id.editBooking);
        deleteButton = (Button) findViewById(R.id.deleteBooking);

        System.out.println(booking.getBookingID());
        bookingIdTv.setText(String.valueOf(booking.getBookingID()));
        classroomId.setText(booking.getClassName());
        badgeNameTv.setText(booking.getBadgeName());
        lecturerIDTv.setText(booking.getLecturerName());
        scheduledForDate.setText(booking.getStartDateLocalString());
        durationBooking.setText(String.valueOf(booking.getDuration()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LecturerManageBooking.this,EditBookingActivity.class));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("BOOKING ID : "+ booking.getBookingID());
                backgroundTask.deleteParticularBooking(booking.getBookingID(),LecturerManageBooking.this);
            }
        });

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Booking");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}