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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Classroom;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.Lecturer;
import com.example.studenttimetable.Model.SharedPrefManager;
import com.example.studenttimetable.Model.VolleySingleton;
import com.example.studenttimetable.Utils.ApiUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LecturerBookClassroom extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner courseSpinner;
    private Spinner badgeSpinner;
    private Spinner classroomSpinner;
    private Spinner durationSpinner;
    private DatePickerDialog picker;
    private TimePickerDialog timePickerDialog;
    private EditText eText;
    private Button bookButton;

    private int selectedCourseId;
    private int selectBadgeID;
    private int selectedClassroomID;
    private int selectedDurationID;
    private String selectedClassroomName;
    private int selectedClassroomFloorLevel;

    private List<Course> courseList = new ArrayList<>();
    private List<String> courseNamesList = new ArrayList<>();
    private List<Badge> badgeList = new ArrayList<>();
    private List<String> badgeNamesList = new ArrayList<>();
    private List<Classroom> classroomList = new ArrayList<>();
    private List<String> classroomNameList = new ArrayList<>();
    private List<String> durationList = new ArrayList<>();
    private BackgroundTask backgroundTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_book_classroom);

        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setAdapters();
    }

    public void setupUiView(){

        courseList = SharedPrefManager.getInstance(getApplicationContext()).getCourseList();
        courseNamesList = SharedPrefManager.getInstance(getApplicationContext()).getCourseNamesList();

        classroomList =SharedPrefManager.getInstance(getApplicationContext()).getClassroomList();
        classroomNameList =SharedPrefManager.getInstance(getApplicationContext()).getClassroomNamesList();

        durationList = SharedPrefManager.getInstance(getApplicationContext()).getDuration();


        toolbar = (Toolbar) findViewById(R.id.ToolbarLecturerBookClassRoom);
        courseSpinner = (Spinner) findViewById(R.id.courseNameBooking);
        badgeSpinner = (Spinner) findViewById(R.id.badgeNameBooking);
        classroomSpinner = (Spinner) findViewById(R.id.classroom);
        durationSpinner = (Spinner) findViewById(R.id.duration);
        eText=(EditText) findViewById(R.id.startDate);
        bookButton = (Button) findViewById(R.id.bookButton);


    }
    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book a Classroom");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setAdapters(){

        badgeNamesList = new ArrayList<>();
        badgeNamesList.add("Select a badge...");
        ArrayAdapter<String> badgeAdapter = new ArrayAdapter<String>
                (LecturerBookClassroom.this, R.layout.color_spinner_layout,
                        badgeNamesList)
        {
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

        badgeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        badgeSpinner.setAdapter(badgeAdapter);


        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>
                (LecturerBookClassroom.this, R.layout.color_spinner_layout,
                        courseNamesList){
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

        courseAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        courseSpinner.setAdapter(courseAdapter);

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0){
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedCourseId = SharedPrefManager.getInstance(getApplicationContext()).getCourseID(selectedItem,courseList);
                    System.out.println("SELECTED COURSE ID : "+ selectedCourseId);

//                    badgeList = backgroundTask.getBadgesByCourseID(selectedItemId);


                    URL result = ApiUtil.buildUrl("BadgesServlet?action=getBadgeByCourseID&device=Mobile&selectedCourseID="+String.valueOf(selectedCourseId));

                    String url = result.toString();
                    System.out.println("URL : "+url);
                    System.out.println("INPUTTED ID = "+ selectedCourseId);
                    //creating a request
                    StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        //JSONObject o1 = new JSONObject(response);
                                        JSONArray jsonArray = new JSONArray(response);
                                        badgeList = new ArrayList<>();
                                        System.out.println("********** Badge Details ************");
                                        System.out.println("BADGE JSON ARRAY : "+jsonArray.length());
                                        for(int i= 0; i <jsonArray.length(); i++){

                                            JSONObject o = jsonArray.getJSONObject(i);

                                            Badge badge = new Badge(
                                                    o.getInt("id"),
                                                    o.getString("badgeName"),
                                                    o.getInt("courseID"),
                                                    "",
                                                    o.getInt("badgeYear"),
                                                    o.getInt("isActive"),
                                                    o.getString("created"),
                                                    o.getString("createdBy"),
                                                    o.getString("modified"),
                                                    o.getString("modifiedBy")
                                            );
                                            // System.out.println("BADGE CREATED STRING : "+badge.getCreatedString());

                                            badgeList.add(badge);
                                            // System.out.println("BADGE CURRENT : "+ badgesList.size());

                                        }
                                        System.out.println("$$$$$$$$$$$$$$ +   "+badgeList.size());
                                        SharedPrefManager.getInstance(getApplicationContext()).setBadgeList(badgeList);
                                        SharedPrefManager.getInstance(getApplicationContext()).setBadgeNamesList(badgeList);


                                        badgeNamesList = SharedPrefManager.getInstance(getApplicationContext()).getBadgeNamesList();



                                        ArrayAdapter<String> badgeAdapter = new ArrayAdapter<String>
                                                (LecturerBookClassroom.this, R.layout.color_spinner_layout,
                                                        badgeNamesList)
                                        {
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

                                        badgeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                                        badgeSpinner.setAdapter(badgeAdapter);
                                        badgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                if(position !=0){
                                                    String selectedItem = parent.getItemAtPosition(position).toString();

                                                    selectBadgeID = SharedPrefManager.getInstance(getApplicationContext()).getBadgeID(selectedItem,badgeList);
                                                    System.out.println("SELECTED BADGE ID : "+ selectBadgeID);
                                                }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });

                                    }catch (Exception e){
                                        System.out.println(e.getMessage());
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getLocalizedMessage());
                            Toast.makeText(getApplicationContext(),"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });


                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringArrayRequest);



                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ArrayAdapter<String> classroomAdapter = new ArrayAdapter<String>
                (LecturerBookClassroom.this, R.layout.color_spinner_layout,
                        classroomNameList){
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
        classroomAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        classroomSpinner.setAdapter(classroomAdapter);

        classroomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0){
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedClassroomName  =selectedItem;
                    selectedClassroomID = SharedPrefManager.getInstance(getApplicationContext()).getClassroomID(selectedItem,classroomList);
                    System.out.println("SELECTED Classroom ID : "+ selectedClassroomID);

                    for(int i = 0; i < classroomList.size(); i++){
                        if(classroomList.get(i).getId() == selectedClassroomID){
                            selectedClassroomFloorLevel = classroomList.get(i).getFloorLevel();
                            break;
                        }
                    }

                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ArrayAdapter<String> durationAdapter = new ArrayAdapter<String>
                (LecturerBookClassroom.this, R.layout.color_spinner_layout,
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


        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                int hour = cldr.get(Calendar.HOUR);
                int min = cldr.get(Calendar.MINUTE);
                // date picker dialog
                picker = new DatePickerDialog(LecturerBookClassroom.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                timePickerDialog = new TimePickerDialog(LecturerBookClassroom.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        eText.setText(year+ "-" +(monthOfYear + 1)  + "-" + dayOfMonth + " " + hourOfDay + ":" +minute +":00");
                                    }
                                },hour,min,true);
                                timePickerDialog.show();
                            }
                        }, year, month, day);
                picker.show();

            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lecturer lecturer = SharedPrefManager.getInstance(getApplicationContext()).getLecturerInfo();
                String userFullName = lecturer.getfName() + " " + lecturer.getlName();
                String userId = lecturer.getLecturerID();
                backgroundTask.bookClassroom(selectedCourseId,selectBadgeID, selectedClassroomID, eText.getText().toString(),selectedDurationID,userId,userFullName,LecturerBookClassroom.this);

            }
        });
    }
}