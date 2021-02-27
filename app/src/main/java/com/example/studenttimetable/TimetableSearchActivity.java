package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Badge;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.SharedPrefManager;
import com.example.studenttimetable.Model.Student;
import com.example.studenttimetable.Model.Timetable;
import com.example.studenttimetable.Model.VolleySingleton;
import com.example.studenttimetable.Utils.ApiUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TimetableSearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner courseNames;
    private Spinner badgeNames;
    private Button searchButton;

    private BackgroundTask backgroundTask = null;
    private List<Course> courseList = new ArrayList<>();
    private List<String> courseNamesList = new ArrayList<>();

    private List<Badge> badgeList = new ArrayList<>();
    private List<String> badgeNamesList = new ArrayList<>();
    public static List<Timetable> recordlist = new ArrayList<>();
    private int selectedCourseId;

    private int selectBadgeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_timetable);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setUiViews();
        initToolbar();
        setAdapters();

    }

    public void setUiViews(){
        courseList = SharedPrefManager.getInstance(getApplicationContext()).getCourseList();
        courseNamesList = SharedPrefManager.getInstance(getApplicationContext()).getCourseNamesList();

        toolbar = (Toolbar) findViewById(R.id.ToolbarSearchStudentTimetable);
        courseNames = (Spinner) findViewById(R.id.courseName);
        badgeNames = (Spinner) findViewById(R.id.badgeName);
        searchButton = (Button) findViewById(R.id.timetableSearch);

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Student Timetable");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setAdapters(){
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>
                (TimetableSearchActivity.this, R.layout.color_spinner_layout,
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
        courseNames.setAdapter(courseAdapter);

        courseNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
                                                (TimetableSearchActivity.this, R.layout.color_spinner_layout,
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
                                        badgeNames.setAdapter(badgeAdapter);
                                        badgeNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordlist = backgroundTask.getRecordList(selectedCourseId,selectBadgeID, TimetableSearchActivity.this);

                System.out.println("RECCC : "+recordlist.size());

            }
        });
    }


}