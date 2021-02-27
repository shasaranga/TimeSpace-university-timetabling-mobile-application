package com.example.studenttimetable.Model;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.studenttimetable.AddNewModuleActivity;
import com.example.studenttimetable.BadgesListViewActivity;
import com.example.studenttimetable.ClassroomListActivty;
import com.example.studenttimetable.CoursesListViewActivity;
import com.example.studenttimetable.EditModuleActivity;
import com.example.studenttimetable.LecturerBookClassroom;
import com.example.studenttimetable.LecturerBookingListViewActivity;
import com.example.studenttimetable.LoginActivity;
import com.example.studenttimetable.ModuleListViewActivity;
import com.example.studenttimetable.PreferencesOptionView;
import com.example.studenttimetable.LecturerTimetableSearch;
import com.example.studenttimetable.SubPreferencesActivity;
import com.example.studenttimetable.TimetableSearchActivity;
import com.example.studenttimetable.Utils.ApiUtil;
import com.example.studenttimetable.WeekActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class BackgroundTask {
    private Context context;
    private Student student;
    private Lecturer lecturer;
    private boolean valid = false;
    private Context loginContext;

    private List<String> courseNamesList = new ArrayList<>();
    private String loggedInUser = null;

    List<Badge> badgesList = new ArrayList<>();
    List<Course> courseList = new ArrayList<>();
    List<Timetable> recordList = new ArrayList<>();
    List<Lecturer> lecturerList = new ArrayList<>();
    List<Classroom> classroomList = new ArrayList<>();
    List<Classroom> classroomModuleList = new ArrayList<>();
    List<Classroom> allClassroomList = new ArrayList<>();
    List<Module> moduleList = new ArrayList<>();

    List<Booking> bookingList = new ArrayList<>();
    List<Timetable> MondayRecords = new ArrayList<>();
    List<Timetable> TuesdayRecords = new ArrayList<>();
    List<Timetable> WednesdayRecords = new ArrayList<>();
    List<Timetable> ThursdayRecords = new ArrayList<>();
    List<Timetable> FridayRecords = new ArrayList<>();

    String url = null;

    public BackgroundTask(Context context){

        this.context = context;
    }

    public boolean loginUser(Context loginContext, String email, String pass){

        URL result = ApiUtil.buildUrl("UserLoginServlet");

        url = result.toString();


        //creating a string request as the post method needs data as form data not as Json so that th server could recognize the
        //the request an params and return a response

        StringRequest stringRequetObject = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response.toString());
                            JSONObject o = new JSONObject(response);
                            if(o.getString("userType").equals("Admin")){
                                Admin admin = new Admin(
                                        o.getInt("id"),
                                        o.getString("adminID"),
                                        o.getString("fName"),
                                        o.getString("lName"),
                                        o.getString("address"),
                                        o.getString("email"),
                                        o.getString("password"),
                                        o.getString("contactNumber"),
                                        o.getString("emergencyNum"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                SharedPrefManager.getInstance(context).setAdminInfoInfo(admin);
                                loginContext.startActivity(new Intent(loginContext, PreferencesOptionView.class));
                                loggedInUser = "ADMIN";
                                SharedPrefManager.getInstance(context).setCurrentUserType(loggedInUser);
                            }
                            else if(o.getString("userType").equals("Student")){
                                Student user = new Student(
                                        o.getInt("id"),
                                        o.getString("studentID"),
                                        o.getString("fName"),
                                        o.getString("lName"),
                                        o.getInt("courseID"),
                                        o.getString("address"),
                                        o.getString("email"),
                                        o.getString("password"),
                                        o.getString("mobileNumber"),
                                        o.getString("emergencyNum"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );

                                JSONObject co = o.getJSONObject("followingCourse");

                                    Course course = new Course(
                                        co.getInt("id"),
                                        co.getString("courseName"),
                                        co.getString("courseField"),
                                        co.getInt("isActive"),
                                        co.getString("created"),
                                        co.getString("createdBy"),
                                        co.getString("modified"),
                                        co.getString("modifiedBy")
                                );
                                user.setFollowingCourse(course);

                                loggedInUser = "STUDENT";
                                SharedPrefManager.getInstance(context).setCurrentUserType(loggedInUser);
                                student = user;
                                SharedPrefManager.getInstance(context).studentInfo(student);
                                valid = true;
                                loginContext.startActivity(new Intent(loginContext, PreferencesOptionView.class));
                                Toast.makeText(loginContext,"Login Successful !",Toast.LENGTH_SHORT).show();

                            }
                            else if(o.getString("userType").equals("Lecturer")){
                                Lecturer user = new Lecturer(
                                        o.getInt("id"),
                                        o.getString("lecturerID"),
                                        o.getString("fName"),
                                        o.getString("lName"),
                                        o.getString("address"),
                                        o.getString("email"),
                                        o.getString("password"),
                                        o.getString("contactNumber"),
                                        o.getString("emergencyNum"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                loggedInUser = "LECTURER";
                                SharedPrefManager.getInstance(context).setCurrentUserType(loggedInUser);
                                lecturer = user;
                                SharedPrefManager.getInstance(context).setLecturerInfoInfo(lecturer);
                                valid = true;
                                loginContext.startActivity(new Intent(loginContext, PreferencesOptionView.class));
                                Toast.makeText(loginContext,"Login Successful !",Toast.LENGTH_SHORT).show();

                            }


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                            Toast.makeText(loginContext,"Login Unsuccessful!  "+ e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("LOGIN ERROR : " + error.getLocalizedMessage());
                        Toast.makeText(loginContext,"Login Unsuccessful!  ",Toast.LENGTH_SHORT).show();

                    }
            }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //"cb008520@timespace.com"
                //"gihan"
                params.put("username",email );
                params.put("pass", pass);
                params.put("device", "Mobile");
                System.out.println(params.toString());
                return params;
            }
           };
        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequetObject);

        return valid;
    }

    public void logoutUser(Context prefContext){
        URL result = ApiUtil.buildUrl("LogOutServlet?device=Mobile&action=logout");

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true && messageClass.getMessage().equals("Logged out Successfully!")){
                                Toast.makeText(prefContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                prefContext.startActivity(new Intent(prefContext, LoginActivity.class));
                            }else{
                                Toast.makeText(prefContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }



                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(prefContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);
    }

    public List<Lecturer> getAvailableLecturers(Context cContext, String calledFrom){
        URL result = ApiUtil.buildUrl("LecturerServlet?device=Mobile&action=getAvailableLecturer");

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            System.out.println("********** Lecturer Details ************");
                            for(int i= 0; i <response.length(); i++){

                                JSONObject o = response.getJSONObject(i);

                                //System.out.println("BADGE ID : "+o.getInt("id"));
                                //System.out.println("BADGE NAME : "+o.getString("badgeName"));
                                Lecturer user = new Lecturer(
                                        o.getInt("id"),
                                        o.getString("lecturerID"),
                                        o.getString("fName"),
                                        o.getString("lName"),
                                        o.getString("address"),
                                        o.getString("email"),
                                        o.getString("password"),
                                        o.getString("contactNumber"),
                                        o.getString("emergencyNum"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                lecturerList.add(user);
                            }

                            SharedPrefManager.getInstance(context).setLecturerList(lecturerList);
                            SharedPrefManager.getInstance(context).setLecturerNamesList(lecturerList);

                            if(calledFrom.equals("PREFERENCES")){
                                cContext.startActivity(new Intent(cContext, LecturerTimetableSearch.class));
                            }else if(calledFrom.equals("EDIT_MODULE")){
                                cContext.startActivity(new Intent(cContext, EditModuleActivity.class));
                            }else{
                                cContext.startActivity(new Intent(cContext, AddNewModuleActivity.class));
                            }

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("LECTURER LIST SIZE : "+lecturerList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return lecturerList;
    }

    public List<Badge> getBadgesByCourseID(int courseId){
        URL result = ApiUtil.buildUrl("BadgesServlet?action=getBadgeByCourseID&device=Mobile&selectedCourseID=1");

        url = result.toString();
        System.out.println("URL : "+url);
        System.out.println("INPUTTED ID = "+ courseId);
        //creating a request
        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //JSONObject o1 = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            System.out.println("********** Badge Details ************");
                            System.out.println("BADGE JSON ARRAY : "+jsonArray.length());
                            for(int i= 0; i <jsonArray.length(); i++){

                                JSONObject o = jsonArray.getJSONObject(i);

//                                System.out.println("BADGE ID : "+o.getInt("id"));
//                                System.out.println("BADGE NAME : "+o.getString("badgeName"));
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

                                badgesList.add(badge);
                                // System.out.println("BADGE CURRENT : "+ badgesList.size());

                            }
                            System.out.println("$$$$$$$$$$$$$$ +   "+badgesList.size());
                            SharedPrefManager.getInstance(context).setBadgeList(badgesList);
                            SharedPrefManager.getInstance(context).setBadgeNamesList(badgesList);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

        return badgesList;
    }

    public List<Badge> getAvailableBadges(Context callingContext, String calledFrom){
        URL result = ApiUtil.buildUrl("BadgesServlet?action=getAllAvailableBadges&device=Mobile");

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //JSONObject o1 = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            System.out.println("********** Badge Details ************");
                            System.out.println("BADGE JSON ARRAY : "+jsonArray.length());
                            for(int i= 0; i <jsonArray.length(); i++){

                                JSONObject o = jsonArray.getJSONObject(i);

//                                System.out.println("BADGE ID : "+o.getInt("id"));
//                                System.out.println("BADGE NAME : "+o.getString("badgeName"));
                                Badge badge = new Badge(
                                        o.getInt("id"),
                                        o.getString("badgeName"),
                                        o.getInt("courseID"),
                                        o.getString("courseName"),
                                        o.getInt("badgeYear"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                // System.out.println("BADGE CREATED STRING : "+badge.getCreatedString());

                                badgesList.add(badge);
                                // System.out.println("BADGE CURRENT : "+ badgesList.size());

                            }

                            System.out.println("$$$$$$$$$$$$$$ +   "+badgesList.size());
                            SharedPrefManager.getInstance(context).setBadgeList(badgesList);
                            SharedPrefManager.getInstance(context).setBadgeNamesList(badgesList);
                            if(calledFrom != null && calledFrom != "" && calledFrom.equals("SUB_PREFERENCES_MANAGE_BADGES")){
                                if(callingContext != null){
                                    callingContext.startActivity(new Intent(callingContext, BadgesListViewActivity.class));
                                }
                            }

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

        return badgesList;
    }

    public List<Classroom> getAvailableClassrooms(/*Context cContext*/){
        URL result = ApiUtil.buildUrl("ClassServlet?action=getAvailableClassrooms&device=Mobile");

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            System.out.println("********** Classrooms' Details ************");
                            for(int i= 0; i <response.length(); i++){

                                JSONObject o = response.getJSONObject(i);

                                //System.out.println("BADGE ID : "+o.getInt("id"));
                                //System.out.println("BADGE NAME : "+o.getString("badgeName"));
                                Classroom classroom = new Classroom(
                                        o.getInt("id"),
                                        o.getString("classroomName"),
                                        o.getInt("floorLevel"),
                                        o.getInt("isAllocatedForCourseModule"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                classroomList.add(classroom);
                            }

                            SharedPrefManager.getInstance(context).setClassroomList(classroomList);
                            SharedPrefManager.getInstance(context).setClassroomNamesList(classroomList);

                           // cContext.startActivity(new Intent(cContext, TimetableSearchActivity.class));

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("CLASSROOM LIST SIZE : "+classroomList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return classroomList;
    }

    public List<Classroom> getAllClassrooms(Context cContext){
        URL result = ApiUtil.buildUrl("ClassServlet?action=getAllClassrooms&device=Mobile");

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            allClassroomList = new ArrayList<>();
                            System.out.println("********** Classrooms' Details ************");
                            for(int i= 0; i <response.length(); i++){

                                JSONObject o = response.getJSONObject(i);

                                //System.out.println("BADGE ID : "+o.getInt("id"));
                                //System.out.println("BADGE NAME : "+o.getString("badgeName"));
                                Classroom classroom = new Classroom(
                                        o.getInt("id"),
                                        o.getString("classroomName"),
                                        o.getInt("floorLevel"),
                                        o.getInt("isAllocatedForCourseModule"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                allClassroomList.add(classroom);
                            }
                            if(cContext != null){
                                cContext.startActivity(new Intent(cContext, ClassroomListActivty.class));
                            }
                            SharedPrefManager.getInstance(context).setAllClassroomList(allClassroomList);
                            SharedPrefManager.getInstance(context).setAllClassroomNamesList(allClassroomList);

                            // cContext.startActivity(new Intent(cContext, TimetableSearchActivity.class));

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("CLASSROOM LIST SIZE : "+classroomList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return classroomList;
    }

    public List<Classroom> getAvailableClassroomsForModules(/*Context cContext*/){
        URL result = ApiUtil.buildUrl("ClassServlet?action=getAvailableClassroomsForModules&device=Mobile");

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            System.out.println("********** Classrooms' Details ************");
                            for(int i= 0; i <response.length(); i++){

                                JSONObject o = response.getJSONObject(i);

                                //System.out.println("BADGE ID : "+o.getInt("id"));
                                //System.out.println("BADGE NAME : "+o.getString("badgeName"));
                                Classroom classroom = new Classroom(
                                        o.getInt("id"),
                                        o.getString("classroomName"),
                                        o.getInt("floorLevel"),
                                        o.getInt("isAllocatedForCourseModule"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                classroomModuleList.add(classroom);
                            }

                            SharedPrefManager.getInstance(context).setClassroomModuleList(classroomModuleList);
                            SharedPrefManager.getInstance(context).setClassroomModuleNamesList(classroomModuleList);

                            // cContext.startActivity(new Intent(cContext, TimetableSearchActivity.class));

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("CLASSROOM LIST SIZE : "+classroomList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return classroomModuleList;
    }

    public List<Course> getCourseDetails(Context context1){
        URL result = ApiUtil.buildUrl("CourseServlet?device=Mobile&action=getAllAvailableCourses");

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i= 0; i <response.length(); i++){

                                JSONObject o = response.getJSONObject(i);

                                System.out.println(o.getInt("id"));
                                System.out.println(o.getString("courseName"));
                                Course course = new Course(
                                        o.getInt("id"),
                                        o.getString("courseName"),
                                        o.getString("courseField"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                System.out.println("CREATED STRING : "+course.getCreatedString());

                                courseList.add(course);
                                System.out.println("CURRENT : "+ courseList.size());




                            }
                            if(context1 != null){
                                context1.startActivity(new Intent(context1,CoursesListViewActivity.class));
                            }

                            SharedPrefManager.getInstance(context).setCourseList(courseList);
                            SharedPrefManager.getInstance(context).setCourseNamesList(courseList);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("COURSE LIST SIZE : "+courseList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

        return courseList;
    }

    public List<Module> getModuleDetails(Context context1){
        URL result = ApiUtil.buildUrl("ModuleServlet?device=Mobile&action=getAvailableModules");

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i= 0; i <response.length(); i++){

                                JSONObject o = response.getJSONObject(i);

                                Module module = new Module(
                                        o.getInt("moduleID"),
                                        o.getString("moduleName"),
                                        o.getString("moduleDescription"),
                                        o.getString("courseName"),
                                        o.getInt("courseID"),
                                        o.getString("lecturerID"),
                                        o.getString("lecturerFName"),
                                        o.getString("lecturerLName"),
                                        o.getString("lecturerFullName"),
                                        o.getInt("classID"),
                                        o.getString("className"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                System.out.println("CREATED STRING : "+module.getCourseName());

                                moduleList.add(module);
                                System.out.println("CURRENT : "+ moduleList.size());

                            }
                            if(context1 != null){
                                context1.startActivity(new Intent(context1, ModuleListViewActivity.class));
                            }

                            SharedPrefManager.getInstance(context).setModuleList(moduleList);
                            SharedPrefManager.getInstance(context).setModuleNamesList(moduleList);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("Module LIST SIZE : "+moduleList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

        return moduleList;
    }

    public void bookClassroom(int courseID, int badgeID, int classroomID, String startDate,  int selectedDurationID, String userID, String userFullName, Context bookContext){

        String courseIDString = String.valueOf(courseID);
        String badgeIDString = String.valueOf(badgeID);

        URL result = ApiUtil.buildUrl("ScheduleClassServlet?");

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            recordList = new ArrayList<>();
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));


                            URL result = ApiUtil.buildUrl("ClassServlet?action=getAvailableClassrooms&device=Mobile");

                            url = result.toString();
                            System.out.println("URL : "+url);
                            //creating a request
                            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {

                                            try {
                                                System.out.println("********** Classrooms' Details ************");
                                                for(int i= 0; i <response.length(); i++){

                                                    JSONObject o = response.getJSONObject(i);

                                                    //System.out.println("BADGE ID : "+o.getInt("id"));
                                                    //System.out.println("BADGE NAME : "+o.getString("badgeName"));
                                                    Classroom classroom = new Classroom(
                                                            o.getInt("id"),
                                                            o.getString("classroomName"),
                                                            o.getInt("floorLevel"),
                                                            o.getInt("isAllocatedForCourseModule"),
                                                            o.getInt("isActive"),
                                                            o.getString("created"),
                                                            o.getString("createdBy"),
                                                            o.getString("modified"),
                                                            o.getString("modifiedBy")
                                                    );
                                                    classroomList.add(classroom);
                                                }

                                                SharedPrefManager.getInstance(context).setClassroomList(classroomList);
                                                SharedPrefManager.getInstance(context).setClassroomNamesList(classroomList);


                                                if(!messageClass.isValid() && messageClass.getMessage().equals("Selected Class is not available!")){
                                                    Toast.makeText(bookContext,"Selected Class is not available now!",Toast.LENGTH_SHORT).show();
                                                }else if(!messageClass.isValid() && messageClass.getMessage().equals("Booking is unsuccessful!")){
                                                    Toast.makeText(bookContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                                }else{
                                                    System.out.println("IS Booking OK?"+ messageClass.isValid());
                                                    bookContext.startActivity(new Intent(bookContext, LecturerBookClassroom.class));
                                                    Toast.makeText(bookContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                                }


                                                // cContext.startActivity(new Intent(cContext, TimetableSearchActivity.class));

                                            }catch (Exception e){
                                                System.out.println(e.getMessage());
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error.getLocalizedMessage());
                                    Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            );
                            System.out.println("OUT OF The Loop");

                            // and pass it to the Volley.newRequestQueue method
                            //here we add the above request to the queue
                            System.out.println("CLASSROOM LIST SIZE : "+classroomList.size());

                            VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);



                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(bookContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("device","Mobile" );
                params.put("classRoom", String.valueOf(classroomID));
                params.put("courseName", String.valueOf(courseID));
                params.put("badge", String.valueOf(badgeID));
                params.put("classDuration", String.valueOf(selectedDurationID));
                params.put("startDate", startDate);
                params.put("lectureID", userID);
                params.put("lectureFullName", userFullName);
//                params.put("selectedClassroomName", classroomName);
//                params.put("selectedClassroomFloorLevel", String.valueOf(floorLevel));
                System.out.println(params.toString());
                return params;
            }
        };
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("RECORD LIST SIZE : "+recordList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void deleteParticularBooking(int bookingID, Context deleteBookContext){

        String bookingIDString = String.valueOf(bookingID);

        URL result = ApiUtil.buildUrl("ControlPanelVerifyUser?device=Mobile&action=deleteParticularBooking&bookingID="+bookingIDString);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Toast.makeText(deleteBookContext,"Booking successfully deleted!",Toast.LENGTH_SHORT).show();
                            getLecturerBooking(SharedPrefManager.getInstance(context).getLecturerInfo().getLecturerID());

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(deleteBookContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void editParticularBooking(String lecturerID, int classID, int bookingID, String scheduledDate, int classDuration, Context editBooking){

        String bookingIDString = String.valueOf(bookingID);

        URL result = ApiUtil.buildUrl("ControlPanelVerifyUser?device=Mobile&action=editParticularBooking&bookingIDVal="+bookingIDString+"&SelectedClassID="+classID+"&classDuration="+classDuration+"&startDate="+scheduledDate +"&UserID="+lecturerID);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true && messageClass.getMessage().equals("Edited Successfully!")){
                                Toast.makeText(editBooking,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(editBooking,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            getLecturerBooking(SharedPrefManager.getInstance(context).getLecturerInfo().getLecturerID());

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(editBooking,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void getLecturerBooking(String lecturerID /*, Context bookingContext*/){

        URL result = ApiUtil.buildUrl("ControlPanelVerifyUser?action=getLecturerRelatedBookings&device=Mobile&lecturerID="+lecturerID);

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            bookingList = new ArrayList<>();
                            for(int i= 0; i <response.length(); i++){

                                JSONObject o = response.getJSONObject(i);
                                int year =0;
                                int month =0;
                                int day =0;

                                int hour =0;
                                int min =0;
                                int second =0;
                                int nano =0;

                                year = o.getJSONObject("startDateLocal").getJSONObject("date").getInt("year");
                                month = o.getJSONObject("startDateLocal").getJSONObject("date").getInt("month");
                                day = o.getJSONObject("startDateLocal").getJSONObject("date").getInt("day");

                                LocalDate date = LocalDate.of(year,month,day);
                                System.out.println("START : - " +date.toString() );

                                hour = o.getJSONObject("startDateLocal").getJSONObject("time").getInt("hour");
                                min = o.getJSONObject("startDateLocal").getJSONObject("time").getInt("minute");
                                //second  = o.getJSONObject("startDateLocal").getJSONObject("time").getInt("second");
                                //nano  = o.getJSONObject("startDateLocal").getJSONObject("time").getInt("nano");
                                LocalTime time = LocalTime.of(hour,min);
                                LocalDateTime startDate = LocalDateTime.of(date,time);
                                System.out.println("START DA: - " +startDate.toString() );

                                year = o.getJSONObject("endDateLocal").getJSONObject("date").getInt("year");
                                month = o.getJSONObject("endDateLocal").getJSONObject("date").getInt("month");
                                day = o.getJSONObject("endDateLocal").getJSONObject("date").getInt("day");

                                date = LocalDate.of(year,month,day);
                                System.out.println("END : - " +date.toString() );

                                hour = o.getJSONObject("endDateLocal").getJSONObject("time").getInt("hour");
                                min = o.getJSONObject("endDateLocal").getJSONObject("time").getInt("minute");
                               // second  = o.getJSONObject("endDateLocal").getJSONObject("time").getInt("second");
                             //   nano  = o.getJSONObject("endDateLocal").getJSONObject("time").getInt("nano");
                                time = LocalTime.of(hour,min);
                                LocalDateTime endDate = LocalDateTime.of(date,time);
                                System.out.println("END DATE: - " +endDate.toString() );


                                year = o.getJSONObject("createdLocal").getJSONObject("date").getInt("year");
                                month = o.getJSONObject("createdLocal").getJSONObject("date").getInt("month");
                                day = o.getJSONObject("createdLocal").getJSONObject("date").getInt("day");

                                date = LocalDate.of(year,month,day);
                                System.out.println("CREATED : - " +date.toString() );

                                hour = o.getJSONObject("createdLocal").getJSONObject("time").getInt("hour");
                                min = o.getJSONObject("createdLocal").getJSONObject("time").getInt("minute");
                                second  = o.getJSONObject("createdLocal").getJSONObject("time").getInt("second");
                                nano  = o.getJSONObject("createdLocal").getJSONObject("time").getInt("nano");
                                time = LocalTime.of(hour,min,second,nano);
                                LocalDateTime created = LocalDateTime.of(date,time);
                                System.out.println("CREATED DATE: - " +created.toString() );

                                year = o.getJSONObject("modifiedLocal").getJSONObject("date").getInt("year");
                                month = o.getJSONObject("modifiedLocal").getJSONObject("date").getInt("month");
                                day = o.getJSONObject("modifiedLocal").getJSONObject("date").getInt("day");

                                date = LocalDate.of(year,month,day);
                                System.out.println("MODIFIED : - " +date.toString() );

                                hour = o.getJSONObject("modifiedLocal").getJSONObject("time").getInt("hour");
                                min = o.getJSONObject("modifiedLocal").getJSONObject("time").getInt("minute");
                                second  = o.getJSONObject("modifiedLocal").getJSONObject("time").getInt("second");
                                nano  = o.getJSONObject("modifiedLocal").getJSONObject("time").getInt("nano");
                                time = LocalTime.of(hour,min,second,nano);
                                LocalDateTime modified = LocalDateTime.of(date,time);
                                System.out.println("MODIFIED DATE: - " +modified.toString());

                                Booking booking = new Booking(
                                        o.getInt("bookingID"),
                                        o.getInt("classID"),
                                        o.getString("className"),
                                        o.getString("badgeName"),
                                        o.getString("lecturerName"),
                                        o.getInt("duration"),
                                        startDate,
                                        endDate,
                                        o.getInt("isActive"),
                                        created,
                                        o.getString("createdBy"),
                                        modified,
                                        o.getString("modifiedBy")
                                );

                                bookingList.add(booking);

                            }
                            System.out.println("LECTURER BOOKING SIZE: " + bookingList.size());
                            SharedPrefManager.getInstance(context).setBookingList(bookingList);

                            Context lecturerOptionViewContext = SharedPrefManager.getInstance(context).getLecturerOptionView();

                            lecturerOptionViewContext.startActivity(new Intent(lecturerOptionViewContext, LecturerBookingListViewActivity.class));

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void addNewCourse(String courseName,String coursefield, String adminID, Context newCourseContext){
        URL result = ApiUtil.buildUrl("CourseServlet?device=Mobile&action=registration&currentUserLoggedInAdmin="+adminID+"&courseName="+courseName+"&courseField="+coursefield);

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(newCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                URL result = ApiUtil.buildUrl("CourseServlet?device=Mobile&action=getAllAvailableCourses");

                                url = result.toString();
                                System.out.println("URL : "+url);
                                //creating a request
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {

                                                try {
                                                    courseList = new ArrayList<>();
                                                    for(int i= 0; i <response.length(); i++){

                                                        JSONObject o = response.getJSONObject(i);

                                                        System.out.println(o.getInt("id"));
                                                        System.out.println(o.getString("courseName"));
                                                        Course course = new Course(
                                                                o.getInt("id"),
                                                                o.getString("courseName"),
                                                                o.getString("courseField"),
                                                                o.getInt("isActive"),
                                                                o.getString("created"),
                                                                o.getString("createdBy"),
                                                                o.getString("modified"),
                                                                o.getString("modifiedBy")
                                                        );
                                                        System.out.println("CREATED STRING : "+course.getCreatedString());

                                                        courseList.add(course);
                                                        System.out.println("CURRENT : "+ courseList.size());
                                                    }

                                                    SharedPrefManager.getInstance(context).setCourseList(courseList);
                                                    SharedPrefManager.getInstance(context).setCourseNamesList(courseList);
                                                    newCourseContext.startActivity(new Intent(newCourseContext, SubPreferencesActivity.class));
                                                }catch (Exception e){
                                                    System.out.println(e.getMessage());
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        System.out.println(error.getLocalizedMessage());
                                        Toast.makeText(newCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                                );
                                System.out.println("OUT OF The Loop");

                                // and pass it to the Volley.newRequestQueue method
                                //here we add the above request to the queue
                                System.out.println("COURSE LIST SIZE : "+courseList.size());

                                VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

                            }else{
                                if(messageClass.getMessage().equals("Required Parameters not Found!")){
                                    System.out.println("!!!!!!!!!!!!!!!! TECHNICAL ERROR !!!!!!!!!!!!!!!!!!");
                                    System.out.println(messageClass.getMessage());
                                }else{
                                    Toast.makeText(newCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }

                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(newCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);
    }

    public void deleteParticularCourse(int courseId, String adminID, Context deleteCourseContext){

        String courseIdString = String.valueOf(courseId);

        URL result = ApiUtil.buildUrl("CourseServlet?device=Mobile&action=Delete&courseID="+courseIdString+"&loggedInUserID="+adminID);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getCourseDetails(deleteCourseContext);
                            }else{
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(deleteCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void editParticularCourse(int courseId, String courseName, String courseField, String adminID, Context editedCourseContext){

        String courseIdString = String.valueOf(courseId);
        courseName = courseName.replaceAll("\\s+","+");
        courseField = courseField.replaceAll("\\s+","+");
        URL result = ApiUtil.buildUrl("CourseServlet?device=Mobile&action=Edit&selectedCourseID="+courseIdString+"&AdminUserID="+adminID+"&courseName="+courseName+"&courseField="+courseField);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(editedCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getCourseDetails(editedCourseContext);
                            }else{
                                Toast.makeText(editedCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(editedCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void addNewModule(int assignedCourse, String moduleName,String moduleDescription,  String adminID, String assignedLecturer, int assignedClassroom, Context newCourseContext){
        URL result = ApiUtil.buildUrl("ModuleServlet?device=Mobile&action=registration&currentUserLoggedInAdmin="+adminID+"&courseName="+assignedCourse+"&moduleName="+moduleName+"&courseDescription="+ moduleDescription+"&lecturerName="+assignedLecturer+"&classroom="+assignedClassroom);

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(newCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                newCourseContext.startActivity(new Intent(newCourseContext, SubPreferencesActivity.class));
                            }else{
                                if(messageClass.getMessage().equals("Required Parameters not Found!")){
                                    System.out.println("!!!!!!!!!!!!!!!! TECHNICAL ERROR !!!!!!!!!!!!!!!!!!");
                                    System.out.println(messageClass.getMessage());
                                }else{
                                    Toast.makeText(newCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }



                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(newCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);
    }

    public void deleteParticularModule(int courseID, int moduleId, String adminID, Context deleteModuleContext){
        String courseIDString = String.valueOf(courseID);
        String moduleIdString = String.valueOf(moduleId);

        URL result = ApiUtil.buildUrl("ModuleServlet?device=Mobile&action=Delete&courseID="+courseIDString+"&moduleID="+moduleIdString+"&loggedInUserID="+adminID);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(deleteModuleContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getModuleDetails(deleteModuleContext);
                            }else{
                                Toast.makeText(deleteModuleContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(deleteModuleContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void editParticularModule(int courseId, int moduleId, String moduleName, String moduleDescription,String lecturerID, int classroomID, String adminID, Context editedModuleContext){

        String courseIdString = String.valueOf(courseId);
        String classroomIDString = String.valueOf(classroomID);
        moduleName = moduleName.replaceAll("\\s+","+");
        moduleDescription = moduleDescription.replaceAll("\\s+","+");
        URL result = ApiUtil.buildUrl("ModuleServlet?device=Mobile&action=Edit&selectedCourseID="+courseIdString+"&AdminUserID="+adminID+"&selectedModuleID="+moduleId+"&moduleName="+moduleName+"&moduleDesc="+moduleDescription+"&lecturerName="+lecturerID+"&classRoom="+classroomIDString);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(editedModuleContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getModuleDetails(editedModuleContext);
                            }else{
                                Toast.makeText(editedModuleContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(editedModuleContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void addNewBadge(int selectedCourseID, String newBadgeName,String badgeYear, String adminID, Context newBadgeContext){
        newBadgeName = newBadgeName.replaceAll("\\s+","+");
        URL result = ApiUtil.buildUrl("BadgesServlet?device=Mobile&action=registration&currentUserLoggedInAdmin="+adminID+"&courseName="+selectedCourseID+"&badgeName="+newBadgeName+"&badgeYear="+badgeYear);

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(newBadgeContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                newBadgeContext.startActivity(new Intent(newBadgeContext, SubPreferencesActivity.class));
                            }else{
                                if(messageClass.getMessage().equals("Required Parameters not Found!")){
                                    System.out.println("!!!!!!!!!!!!!!!! TECHNICAL ERROR !!!!!!!!!!!!!!!!!!");
                                    System.out.println(messageClass.getMessage());
                                }else{
                                    Toast.makeText(newBadgeContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }



                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(newBadgeContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);
    }

    public void deleteParticularBadge(int courseId, int badgeID,  String adminID, Context deleteCourseContext){

        String courseIdString = String.valueOf(courseId);

        URL result = ApiUtil.buildUrl("BadgesServlet?device=Mobile&action=Delete&courseID="+courseIdString+"&badgeID="+badgeID+"&loggedInUserID="+adminID);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getAvailableBadges(deleteCourseContext,"SUB_PREFERENCES_MANAGE_BADGES");
                            }else{
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(deleteCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void editParticularBadge(int courseId, int badgeID, String badgeName, String badgeYear,  String adminID, Context deleteCourseContext){

        String courseIdString = String.valueOf(courseId);
        badgeName = badgeName.replaceAll("\\s+","+");
        badgeYear =badgeYear.replaceAll("\\s+","+");
        URL result = ApiUtil.buildUrl("BadgesServlet?device=Mobile&action=Edit&selectedCourseID="+courseIdString+"&selectedBadgeID="+badgeID+"&badgeName="+badgeName+"&badgeYear="+badgeYear+"&AdminUserID="+adminID);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getAvailableBadges(deleteCourseContext,"SUB_PREFERENCES_MANAGE_BADGES");
                            }else{
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(deleteCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void addNewClassroom(String className,String floorLevel, boolean isAllocated, String adminID, Context newClassroomContext){
        URL result = ApiUtil.buildUrl("ClassServlet?device=Mobile&action=registration&currentUserLoggedInAdmin="+adminID+"&className="+className+"&floorLevel="+floorLevel+"&isAllocatedForModule="+isAllocated);

        url = result.toString();
        System.out.println("URL : "+url);
        //creating a request
        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(newClassroomContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                newClassroomContext.startActivity(new Intent(newClassroomContext, SubPreferencesActivity.class));
                            }else{
                                if(messageClass.getMessage().equals("Required Parameters not found!")){
                                    System.out.println("!!!!!!!!!!!!!!!! TECHNICAL ERROR !!!!!!!!!!!!!!!!!!");
                                    System.out.println(messageClass.getMessage());
                                }else{
                                    Toast.makeText(newClassroomContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }



                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(newClassroomContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);
    }

    public void deleteParticularClassroom(int classID,  String adminID, Context deleteCourseContext){

        String classIDString = String.valueOf(classID);

        URL result = ApiUtil.buildUrl("ClassServlet?device=Mobile&action=Delete&classroomID="+classIDString+"&loggedInUserID="+adminID);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getAllClassrooms(deleteCourseContext);
                            }else{
                                Toast.makeText(deleteCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(deleteCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public void editParticularClassroom(int classID, String className, String floorLevel, int isAllocated, String adminID, Context editedCourseContext){

        String classIdString = String.valueOf(classID);
        className = className.replaceAll("\\s+","+");
        floorLevel = floorLevel.replaceAll("\\s+","+");
        URL result = ApiUtil.buildUrl("ClassServlet?device=Mobile&action=Edit&selectedClassID="+classIdString+"&AdminUserID="+adminID+"&className="+className+"&selectedClassIDIsAllocated="+isAllocated+"&floorLevel="+floorLevel);

        url = result.toString();
        System.out.println("URL : "+url);

        StringRequest stringArrayRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            MessageClass messageClass = new MessageClass(o.getBoolean("isValid"),o.getString("message"));
                            if(messageClass.isValid() == true){
                                Toast.makeText(editedCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                                getAllClassrooms(editedCourseContext);
                            }else{
                                Toast.makeText(editedCourseContext,messageClass.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(editedCourseContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        VolleySingleton.getInstance(context).addToRequestQueue(stringArrayRequest);

    }

    public List<Timetable> getRecordList(int courseID, int badgeID, Context searchContext){

        String courseIDString = String.valueOf(courseID);
        String badgeIDString = String.valueOf(badgeID);

        URL result = ApiUtil.buildUrl("CourseTimetableServlet?courseName="+courseIDString+"&badge="+badgeIDString+"&action=search&device=Mobile&viewType=commonStudentView");

        url = result.toString();
        System.out.println("URL : "+url);

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context); // here we get the current context
        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            recordList = new ArrayList<>();
                            for(int i= 0; i <response.length(); i++){

                                //During each iteration the a JSONObject will
                                //catch/hold the JSON object according to the
                                //array index (in this case it's 'i' value).
                                JSONObject o = response.getJSONObject(i);

                                //after getting an object the system will
                                //create a Product object where the data of the relevant
                                // JSON object is taken and passed a parameters, overloading
                                //the Product constructor in the following format:-
                                //
                                // Product(String imageURL, String name, String price, String sizes, String description,
                                // String catergory, Boolean isNew)
                                System.out.println(o.getInt("recordID"));
                                System.out.println(o.getInt("courseID"));
                                Timetable timetable = new Timetable(
                                        o.getInt("recordID"),
                                        o.getInt("courseID"),
                                        o.getString("courseName"),
                                        o.getInt("badgeID"),
                                        o.getString("badgeName"),
                                        o.getInt("weekDayID"),
                                        o.getString("weekday"),
                                        o.getInt("duration"),
                                        o.getString("startTime"),
                                        o.getString("endTime"),
                                        o.getInt("moduleID"),
                                        o.getString("moduleName"),
                                        o.getString("lecturerID"),
                                        o.getString("lecturerFName"),
                                        o.getString("lecturerLName"),
                                        o.getString("lecturerFullName"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                System.out.println(timetable.getBadgeID());
                                System.out.println("END TIME : "+timetable.getEndTimeString());

                                //after creating a Product object which contains the JSON object's data,
                                //will be now added to the productList (List type Collection variable).
                                recordList.add(timetable);
                                System.out.println("CURRENT : "+ recordList.size());


                            }

                            if(recordList != null && recordList.size() != 0){
                                SharedPrefManager.getInstance(context).setRecordList(recordList);
                                searchContext.startActivity(new Intent(searchContext, WeekActivity.class));
                                Toast.makeText(searchContext,"Timetable Found!",Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(searchContext,"Timetable Not Found!",Toast.LENGTH_SHORT).show();
                            }


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(searchContext,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
        System.out.println("RECORD LIST SIZE : "+recordList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

        return recordList;

    }

    public List<Timetable> getLecturerTimetable(String selectedLecturerID, Context searchContext){

        URL result = ApiUtil.buildUrl("CourseTimetableServlet?lecturerID="+selectedLecturerID+"&action=search&device=Mobile&viewType=commonLecturerView");

        url = result.toString();
        System.out.println("URL : "+url);

        //creating a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            recordList = new ArrayList<>();
                            for(int i= 0; i <response.length(); i++){

                                //During each iteration the a JSONObject will
                                //catch/hold the JSON object according to the
                                //array index (in this case it's 'i' value).
                                JSONObject o = response.getJSONObject(i);

                                //after getting an object the system will
                                //create a Product object where the data of the relevant
                                // JSON object is taken and passed a parameters, overloading
                                //the Product constructor in the following format:-
                                //
                                // Product(String imageURL, String name, String price, String sizes, String description,
                                // String catergory, Boolean isNew)
                                System.out.println(o.getInt("recordID"));
                                System.out.println(o.getInt("courseID"));
                                Timetable timetable = new Timetable(
                                        o.getInt("recordID"),
                                        o.getInt("courseID"),
                                        o.getString("courseName"),
                                        o.getInt("badgeID"),
                                        o.getString("badgeName"),
                                        o.getInt("weekDayID"),
                                        o.getString("weekday"),
                                        o.getInt("duration"),
                                        o.getString("startTime"),
                                        o.getString("endTime"),
                                        o.getInt("moduleID"),
                                        o.getString("moduleName"),
                                        o.getString("lecturerID"),
                                        o.getString("lecturerFName"),
                                        o.getString("lecturerLName"),
                                        o.getString("lecturerFullName"),
                                        o.getInt("isActive"),
                                        o.getString("created"),
                                        o.getString("createdBy"),
                                        o.getString("modified"),
                                        o.getString("modifiedBy")
                                );
                                System.out.println(timetable.getBadgeID());
                                System.out.println("END TIME : "+timetable.getEndTimeString());

                                //after creating a Product object which contains the JSON object's data,
                                //will be now added to the productList (List type Collection variable).
                                recordList.add(timetable);
                                System.out.println("CURRENT : "+ recordList.size());


                            }

                            if(recordList != null && recordList.size() != 0){
                                SharedPrefManager.getInstance(context).setRecordList(recordList);
                                searchContext.startActivity(new Intent(searchContext, WeekActivity.class));
                                Toast.makeText(searchContext,"Timetable Found!",Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(searchContext,"Timetable Not Found!",Toast.LENGTH_SHORT).show();
                            }


                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getLocalizedMessage());
                Toast.makeText(context,"Error!  "+ error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        System.out.println("OUT OF The Loop");

        // and pass it to the Volley.newRequestQueue method
        //here we add the above request to the queue
       // System.out.println("RECORD LIST SIZE : "+recordList.size());

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
        return recordList;
    }


    public List<Timetable> getMondayRecords(List<Timetable> allRecords) {
        MondayRecords = new ArrayList<>();
        for(int i =0; i < allRecords.size(); i++){

            if(allRecords.get(i).getWeekday().equals("Monday")){
                MondayRecords.add(allRecords.get(i));
            }
        }
        return MondayRecords;
    }

    public List<Timetable> getTuesdayRecords(List<Timetable> allRecords) {
        TuesdayRecords = new ArrayList<>();
        for(int i =0; i < allRecords.size(); i++){

            if(allRecords.get(i).getWeekday().equals("Tuesday")){
                TuesdayRecords.add(allRecords.get(i));
            }
        }
        return TuesdayRecords;
    }

    public List<Timetable> getWednesdayRecords(List<Timetable> allRecords) {
        WednesdayRecords = new ArrayList<>();
        for(int i =0; i < allRecords.size(); i++){

            if(allRecords.get(i).getWeekday().equals("Wednesday")){
                WednesdayRecords.add(allRecords.get(i));
            }
        }
        return WednesdayRecords;
    }

    public List<Timetable> getThursdayRecords(List<Timetable> allRecords) {
        ThursdayRecords = new ArrayList<>();
        for(int i =0; i < allRecords.size(); i++){

            if(allRecords.get(i).getWeekday().equals("Thursday")){
                ThursdayRecords.add(allRecords.get(i));
            }
        }
        return ThursdayRecords;
    }

    public List<Timetable> getFridayRecords(List<Timetable> allRecords) {
        FridayRecords = new ArrayList<>();
        for(int i =0; i < allRecords.size(); i++){

            if(allRecords.get(i).getWeekday().equals("Friday")){
                FridayRecords.add(allRecords.get(i));
            }
        }
        return FridayRecords;
    }


    public String getLoggedInUser(){
        return loggedInUser;
    }

}

