package com.example.studenttimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;

public class AddNewModuleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner courseSpinner;
    private EditText newModuleName;
    private EditText moduleDescription;
    private Spinner assignModuleTo;
    private Spinner assignClassForModule;
    private Button addButton;
    private BackgroundTask backgroundTask =null;

    private String adminID  = null;
    private int assignedCourse;
    private String assingedLecturer = null;
    private int assignedClassroom;

    private List<Course> courseList = new ArrayList<>();
    private List<String> courseNamesList = new ArrayList<>();

    private List<Lecturer> lecturerList = new ArrayList<>();
    private List<String> lecturerNamesList = new ArrayList<>();

    private List<Classroom> classroomList = new ArrayList<>();
    private List<String> classroomNameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_module);

        backgroundTask = new BackgroundTask(getApplicationContext());

        setupUiView();
        initToolbar();
        setAdapters();
    }



    public void setupUiView(){
        courseList = SharedPrefManager.getInstance(getApplicationContext()).getCourseList();
        courseNamesList = SharedPrefManager.getInstance(getApplicationContext()).getCourseNamesList();

        lecturerList = SharedPrefManager.getInstance(getApplicationContext()).getLecturerList();
        System.out.println("MY LECTURERS LIST : " + lecturerList.size());
        lecturerNamesList = SharedPrefManager.getInstance(getApplicationContext()).getLecturerNamesList();

        classroomList =SharedPrefManager.getInstance(getApplicationContext()).getClassroomModuleList();
        classroomNameList =SharedPrefManager.getInstance(getApplicationContext()).getClassroomModuleNamesList();

        toolbar = (Toolbar) findViewById(R.id.ToolbarAddNewModule);
        courseSpinner = (Spinner) findViewById(R.id.courseNameNewModule);
        newModuleName =(EditText) findViewById(R.id.newModuleName);
        moduleDescription = (EditText) findViewById(R.id.moduleDescription);
        assignModuleTo = (Spinner) findViewById(R.id.assignModuleTo);
        assignClassForModule = (Spinner) findViewById(R.id.assignClassForModule);

        addButton = (Button) findViewById(R.id.addModuleButton);

        adminID = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();

    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Module");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setAdapters(){

        ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>
                (AddNewModuleActivity.this, R.layout.color_spinner_layout,
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
                if(position != 0) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    assignedCourse = SharedPrefManager.getInstance(getApplicationContext()).getCourseID(selectedItem, courseList);
                    System.out.println("SELECTED COURSE ID : " + assignedCourse);
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        lecturerNamesList.set(0,"Assign Module To...");
        ArrayAdapter<String> lecturerAdapter = new ArrayAdapter<String>
                (AddNewModuleActivity.this, R.layout.color_spinner_layout,
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
        assignModuleTo.setAdapter(lecturerAdapter);

        assignModuleTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    System.out.println("SELECTED LECTURER NAME : "+ selectedItem);
                    if(selectedItem != null && selectedItem != ""){
                        String fName = selectedItem.split(" ")[0];
                        System.out.println(fName);
                        String lName = selectedItem.split(" ")[1];
                        System.out.println(lName);
                        System.out.println(lecturerList.size());
                        assingedLecturer = SharedPrefManager.getInstance(getApplicationContext()).getLecturerID(fName,lName,lecturerList);
                        System.out.println("SELECTED LECTURER ID : "+ assingedLecturer);
                    }
                    else{
                        Toast.makeText(AddNewModuleActivity.this,"Lecturer Details not found!",Toast.LENGTH_SHORT).show();
                    }
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        classroomNameList.set(0,"Assign Classroom for Module...");
        ArrayAdapter<String> classroomAdapter = new ArrayAdapter<String>
                (AddNewModuleActivity.this, R.layout.color_spinner_layout,
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
        assignClassForModule.setAdapter(classroomAdapter);

        assignClassForModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0){
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    assignedClassroom = SharedPrefManager.getInstance(getApplicationContext()).getClassroomID(selectedItem,classroomList);
                    System.out.println("SELECTED Classroom ID : "+ assignedClassroom);

                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newModuleName.getText().toString();
                String desc =  moduleDescription.getText().toString();
                if(assignedCourse !=0 && name != null && desc != null && adminID != null && assingedLecturer != null && assignedClassroom != 0){
                    backgroundTask.addNewModule(assignedCourse,newModuleName.getText().toString(),moduleDescription.getText().toString(), adminID,assingedLecturer,assignedClassroom,AddNewModuleActivity.this);
                }else{
                    Toast.makeText(AddNewModuleActivity.this,"Please enter all the fields!",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}