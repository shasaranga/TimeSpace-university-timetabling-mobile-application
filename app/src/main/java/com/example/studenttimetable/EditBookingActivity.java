package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Booking;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditBookingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView bookingIdTv;
    private TextView classroomId;
    private TextView badgeNameTv;
    private TextView lecturerIDTv;
    private EditText startDate;
    private Spinner durationSpinner;
    private Button submitButton;
    private DatePickerDialog picker;
    private TimePickerDialog timePickerDialog;

    private BackgroundTask backgroundTask = null;
    private List<String> durationList = new ArrayList<>();
    private int selectedDurationID;
    private String webformatStartDate =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking);
        backgroundTask = new BackgroundTask(getApplicationContext());
        setupUiViews();
        initToolbar();
    }

    public void setupUiViews(){
        Booking booking = SharedPrefManager.getInstance(getApplicationContext()).getLecturerSelectedBooking();

        toolbar = (Toolbar) findViewById(R.id.ToolbarLecturerEditBookingDetails);

        bookingIdTv = (TextView)findViewById(R.id.editBookingId);
        classroomId = (TextView) findViewById(R.id.editClassroomIdBooking);
        badgeNameTv = (TextView) findViewById(R.id.editBadgeNameIDBooking);
        lecturerIDTv = (TextView) findViewById(R.id.editLecturerIDBooking);

        startDate = (EditText) findViewById(R.id.startDateEdit);
        durationSpinner = (Spinner) findViewById(R.id.durationEditingBooking);
        submitButton = (Button) findViewById(R.id.submitEdittedBooking);

        bookingIdTv.setText(String.valueOf(booking.getBookingID()));
        classroomId.setText(booking.getClassName());
        badgeNameTv.setText(booking.getBadgeName());
        lecturerIDTv.setText(booking.getLecturerName());

        startDate.setInputType(InputType.TYPE_NULL);
        webformatStartDate = booking.getWebFormatStartDateString();
        startDate.setText(booking.getStartDateLocalString());
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hour = cldr.get(Calendar.HOUR);
                int min = cldr.get(Calendar.MINUTE);
                // date picker dialog
                picker = new DatePickerDialog(EditBookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                timePickerDialog = new TimePickerDialog(EditBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        startDate.setText(year+ "-" +(monthOfYear + 1)  + "-" + dayOfMonth + " " + hourOfDay + ":" +minute +":00");
                                        webformatStartDate = year+ "-" +(monthOfYear + 1)  + "-" + dayOfMonth + "+" + hourOfDay + ":" +minute +":00";
                                    }
                                },hour,min,true);
                                timePickerDialog.show();
                            }
                        }, year, month, day);
                picker.show();

            }
        });

        durationList = SharedPrefManager.getInstance(getApplicationContext()).getDuration();

        ArrayAdapter<String> durationAdapter = new ArrayAdapter<String>
                (EditBookingActivity.this, R.layout.color_spinner_layout,
                        durationList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
        };
        durationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        durationSpinner.setAdapter(durationAdapter);


        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0){
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    for(int i=0; i < durationList.size(); i++){
                        if(durationList.get(i).equals(selectedItem)){
                            selectedDurationID = i;
                            break;
                        }
                    }
                    System.out.println("SELECTED Duration ID : "+ selectedDurationID);

                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        String bookingDurationString = null;
        System.out.println(booking.getDuration());
        if(booking.getDuration() == 1){
            bookingDurationString = booking.getDuration() + " hour";
        }else{
            bookingDurationString = booking.getDuration() + " hours";
        }

        for(int i = 0; i < durationList.size(); i++){

            if(durationList.get(i).equals(bookingDurationString)){
                durationSpinner.setSelection(i);
                break;
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(booking.getLecturerName());

                backgroundTask.editParticularBooking(booking.getLecturerName(), booking.getClassID(),booking.getBookingID(),webformatStartDate,selectedDurationID,EditBookingActivity.this);
            }
        });
    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Booking");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


}