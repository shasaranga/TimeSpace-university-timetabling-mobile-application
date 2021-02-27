package com.example.studenttimetable;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studenttimetable.Model.BackgroundTask;
import com.example.studenttimetable.Model.Classroom;
import com.example.studenttimetable.Model.Course;
import com.example.studenttimetable.Model.Lecturer;
import com.example.studenttimetable.Model.Module;
import com.example.studenttimetable.Model.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static java.lang.Integer.parseInt;

public class EditModuleActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView courseName;
    private EditText moduleName;
    private EditText description;
    private Spinner lecturerSpinner;
    private Spinner classroomSpinner;
    private Button submitButton;
    private List<Lecturer> lecturerList = new ArrayList<>();
    private List<String> lecturerNamesList = new ArrayList<>();
    private BackgroundTask backgroundTask = null;
    private String adminId = null;
    private String selectedLecturerID;
    private Module module = null;
    private int selectedClassroomID;
    private List<Classroom> classroomList = new ArrayList<>();
    private List<String> classroomNameList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);

        backgroundTask = new BackgroundTask(getApplicationContext());
        setupUiViews();
        initToolbar();
        setAdapters();
    }
    public void setupUiViews() {
        module = SharedPrefManager.getInstance(getApplicationContext()).getSelectedModule();
        lecturerList = SharedPrefManager.getInstance(getApplicationContext()).getLecturerList();
        System.out.println("MY LECTURERS LIST : " + lecturerList.size());
        lecturerNamesList = SharedPrefManager.getInstance(getApplicationContext()).getLecturerNamesList();
        classroomList =SharedPrefManager.getInstance(getApplicationContext()).getClassroomModuleList();
        classroomNameList =SharedPrefManager.getInstance(getApplicationContext()).getClassroomModuleNamesList();

        toolbar = (Toolbar) findViewById(R.id.ToolbarEditModuleDetails);
        courseName = (TextView) findViewById(R.id.courseNameModuleEdit);
        moduleName = (EditText) findViewById(R.id.moduleNameEdit);
        description = (EditText) findViewById(R.id.moduleDescEdit);
        lecturerSpinner = (Spinner) findViewById(R.id.changeLecturer);
        classroomSpinner = (Spinner) findViewById(R.id.changeClassroom);
        submitButton = (Button) findViewById(R.id.submitEdittedModule) ;

        System.out.println("MOD : " + module.getCourseName());
        courseName.setText(module.getCourseName());
        moduleName.setText(module.getModuleName());
        description.setText(module.getModuleDescription());

        adminId = SharedPrefManager.getInstance(getApplicationContext()).getAdminInfo().getAdminID();



    }
    public void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Module");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setAdapters(){
        ArrayAdapter<String> lecturerAdapter = new ArrayAdapter<String>
                (EditModuleActivity.this, R.layout.color_spinner_layout_small,
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

        lecturerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_small);
        lecturerSpinner.setAdapter(lecturerAdapter);

        lecturerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
                        Toast.makeText(EditModuleActivity.this,"Lecturer Details not found!",Toast.LENGTH_SHORT).show();
                    }

                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        for(int i =0; i < lecturerNamesList.size(); i++){
            if(lecturerNamesList.get(i).equals(module.getLecturerFullName())){
                lecturerSpinner.setSelection(i);
                break;
            }
        }

        ArrayAdapter<String> classroomAdapter = new ArrayAdapter<String>
                (EditModuleActivity.this, R.layout.color_spinner_layout_small,
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
        classroomAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_small);
        classroomSpinner.setAdapter(classroomAdapter);

        classroomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0){
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    selectedClassroomID = SharedPrefManager.getInstance(getApplicationContext()).getClassroomID(selectedItem,classroomList);
                    System.out.println("SELECTED Classroom ID : "+ selectedClassroomID);

                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        for(int i =0; i < classroomNameList.size(); i++){
            if(classroomNameList.get(i).equals(module.getClassName())){
                classroomSpinner.setSelection(i);
                break;
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moduleNameVal = moduleName.getText().toString();
                String desc = description.getText().toString();
                backgroundTask.editParticularModule(module.getCourseID(),module.getModuleID(),moduleNameVal,desc,selectedLecturerID,selectedClassroomID,adminId,EditModuleActivity.this);
            }
        });

    }
}