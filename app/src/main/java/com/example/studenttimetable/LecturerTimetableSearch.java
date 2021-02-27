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
import com.example.studenttimetable.Model.Lecturer;
import com.example.studenttimetable.Model.SharedPrefManager;
import com.example.studenttimetable.Model.Timetable;
import com.example.studenttimetable.Model.VolleySingleton;
import com.example.studenttimetable.Utils.ApiUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LecturerTimetableSearch extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner lecturerNames;
    private Button searchButton;

    private List<Lecturer> lecturerList = new ArrayList<>();
    private List<String> lecturerNamesList = new ArrayList<>();
    private List<Timetable> lecturerTimetable = new ArrayList<>();
    private BackgroundTask backgroundTask = null;

    private String selectedLecturerID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_timetable_search);
        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setAdapter();
    }

    public void setupUiView(){
        toolbar = (Toolbar) findViewById(R.id.ToolbarLecturerSearchTimetable);
        lecturerNames = (Spinner) findViewById(R.id.lecturerName);
        searchButton = (Button) findViewById(R.id.lecturerTimetableSearch);

        lecturerList = SharedPrefManager.getInstance(getApplicationContext()).getLecturerList();
        System.out.println("MY LECTURERS LIST : " + lecturerList.size());
        lecturerNamesList = SharedPrefManager.getInstance(getApplicationContext()).getLecturerNamesList();

    }
    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Lecturer Timetable");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void setAdapter(){
        ArrayAdapter<String> lecturerAdapter = new ArrayAdapter<String>
                (LecturerTimetableSearch.this, R.layout.color_spinner_layout,
                        lecturerNamesList){
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

        lecturerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        lecturerNames.setAdapter(lecturerAdapter);

        lecturerNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                System.out.println("LECTURER LIST ITEM POSITION " + position);
                if(position != 0){
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    System.out.println("SELECTED LECTURER NAME : "+ selectedItem);
                    if(selectedItem != null && selectedItem != ""){
                        String fName = selectedItem.split(" ")[0];
                        System.out.println(fName);
                        String lName = selectedItem.split(" ")[1];
                        System.out.println(lName);
                        System.out.println(lecturerList.size());
                        selectedLecturerID = SharedPrefManager.getInstance(getApplicationContext()).getLecturerID(fName,lName,lecturerList);
                        System.out.println("SELECTED LECTURER ID : "+ selectedLecturerID);
                    }
                    else{
                        Toast.makeText(LecturerTimetableSearch.this,"Lecturer Details not found!",Toast.LENGTH_SHORT).show();
                    }

                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lecturerTimetable =   backgroundTask.getLecturerTimetable(selectedLecturerID, LecturerTimetableSearch.this);

            }
        });
    }
}