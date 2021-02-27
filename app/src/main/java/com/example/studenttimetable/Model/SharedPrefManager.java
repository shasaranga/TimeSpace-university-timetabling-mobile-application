package com.example.studenttimetable.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static SharedPrefManager mInstance;
    private static Context ctx;
    private Student studentInfo =new Student();
    private Lecturer lecturerInfo = new Lecturer();
    private Admin adminInfo = new Admin();
    private List<Module> moduleList = new ArrayList<>();
    private List<String> moduleNamesList = new ArrayList<>();

    private List<Course> courseList = new ArrayList<>();
    private List<String> courseNamesList = new ArrayList<>();



    private List<Badge> badgeList = new ArrayList<>();
    private List<String> badgeNamesList = new ArrayList<>();
    private List<String> lecturerNamesList = new ArrayList<>();
    private List<Timetable> recordList = new ArrayList<>();
    private List<Lecturer> lecturerList = new ArrayList<>();
    private List<Classroom> classroomList = new ArrayList<>();
    private List<String> classroomNamesList = new ArrayList<>();

    private List<Classroom> classroomModuleList = new ArrayList<>();
    private List<String> classroomModuleNamesList = new ArrayList<>();

    private List<Classroom> allClassroomList = new ArrayList<>();
    private List<String> allClassroomNamesList = new ArrayList<>();

    private List<String> bookingDurationList = new ArrayList<>();

    private String adminSelectedOption =null;

    private  List<Booking> lecturerBookingList = new ArrayList<>();
    private Booking selectedBooking = null;
    private Course selectedCourse = null;
    private Module selectedModule = null;
    private Badge selectedBadge = null;
    private Classroom selectedClassroom = null;
    private int selectedPosition = 0;

    private String currentUserType = null;

    private Context lecturerOptionView = null;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the student data in shared preferences
    public void studentLogin(Student student) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, student.getId());
        editor.putString(KEY_USERNAME, student.getEmail());
        editor.putString(KEY_EMAIL, student.getEmail());
       // editor.putString(KEY_GENDER, student.get());
        editor.apply();
    }


    public void studentInfo(Student student){
        this.studentInfo = student;
    }

    public Student getStudentInfo(){
        return studentInfo;
    }


    public void setLecturerInfoInfo(Lecturer lecturer){
        this.lecturerInfo = lecturer;
    }

    public Lecturer getLecturerInfo(){
        return lecturerInfo;
    }

    public void setAdminInfoInfo(Admin admin){
        this.adminInfo = admin;
    }

    public Admin getAdminInfo(){
        return adminInfo;
    }

    public List<Lecturer> getLecturerList(){
        return lecturerList;
    }

    public void setLecturerList(List<Lecturer> list){
        this.lecturerList = list;
    }

    public void setLecturerNamesList(List<Lecturer> lecturerList){
        lecturerNamesList = new ArrayList<>();
        System.out.println("Lecturer Name LIST SIZE : " + lecturerList.size());
        lecturerNamesList.add("Select a lecturer...");
        for(int i =0; i < lecturerList.size(); i++){
            this.lecturerNamesList.add(lecturerList.get(i).getfName() +" " + lecturerList.get(i).getlName());

        }
    }
    public List<String> getLecturerNamesList(){
        for(int i =0; i < lecturerNamesList.size(); i++){
            System.out.println("LECTURER NAMES");
            System.out.println("Lecturer Name : " +lecturerNamesList.get(i));

        }
        return lecturerNamesList;
    }

    public String getLecturerID (String lecturerfName, String lecturerlName, List<Lecturer> list){
        String selectedLecturerID = null;

        for(int i =0; i< list.size(); i++){
            if(list.get(i).getfName().equals(lecturerfName) && list.get(i).getlName().equals(lecturerlName)){
                selectedLecturerID = list.get(i).getLecturerID();
                break;
            }
        }

        return selectedLecturerID;
    }


    public void setCourseList(List<Course> list){
        this.courseList = list;
    }

    public List<Course> getCourseList(){
        return courseList;
    }
    public void setModuleList(List<Module> list){
        this.moduleList = list;
    }

    public List<Module> getModuleList(){
        return moduleList;
    }

    public int getCourseID (String courseName, List<Course> list){
        int selectedCourseID = 0;

        for(int i =0; i< list.size(); i++){
            if(list.get(i).getCourseName().equals(courseName)){
                selectedCourseID = list.get(i).getId();
                break;
            }
        }

        return selectedCourseID;
    }
    public void setCourseNamesList(List<Course> courseList){
        courseNamesList = new ArrayList<>();
        System.out.println("COURSE SIZE : " + courseList.size());
        courseNamesList.add("Select a course...");
        for(int i =0; i < courseList.size(); i++){
            this.courseNamesList.add(courseList.get(i).getCourseName());

        }
    }
    public List<String> getCourseNamesList(){
        System.out.println("************* SETTING COURSE NAMES *************");
        for(int i =0; i < courseNamesList.size(); i++){
            System.out.println("COURSE NAMES");
            System.out.println("Course Names : " +courseNamesList.get(i));

        }
        return courseNamesList;
    }

    public void setModuleNamesList(List<Module> moduleList){
        moduleNamesList = new ArrayList<>();
        System.out.println("MODULE SIZE : " + moduleList.size());
        moduleNamesList.add("Select a module...");
        for(int i =0; i < moduleList.size(); i++){
            this.moduleNamesList.add(moduleList.get(i).getModuleName());

        }
    }
    public List<String> getModuleNamesList(){
        System.out.println("************* SETTING MODULE NAMES *************");
        for(int i =0; i < moduleNamesList.size(); i++){
            System.out.println("MODULE NAMES");
            System.out.println("Module Names : " +moduleNamesList.get(i));

        }
        return moduleNamesList;
    }

    public void setBookingList(List<Booking> list){
        this.lecturerBookingList = list;
    }

    public List<Booking> getBookingList(){
        return lecturerBookingList;
    }

    public void setSelectedBookingPosition(int position){
        this.selectedPosition = position;
    }
    public Booking getLecturerSelectedBooking(){
        selectedBooking = null;
        System.out.println("POSTION : " + selectedPosition);
        for(int i =0; i < lecturerBookingList.size(); i++){
            if(i == selectedPosition){
                selectedBooking = lecturerBookingList.get(i);
                break;
            }
        }
        return selectedBooking;
    }

    public void setSelectedCoursePosition(int position){
        this.selectedPosition = position;
    }
    public Course getSelectedCourse(){
        selectedCourse = null;
        System.out.println("POSTION : " + selectedPosition);
        for(int i =0; i < courseList.size(); i++){
            if(i == selectedPosition){
                selectedCourse = courseList.get(i);
                break;
            }
        }
        return selectedCourse;
    }

    public void setSelectedModulePosition(int position){
        this.selectedPosition = position;
    }
    public Module getSelectedModule(){
        selectedModule = null;
        System.out.println("POSTION : " + selectedPosition);
        for(int i =0; i < moduleList.size(); i++){
            if(i == selectedPosition){
                selectedModule = moduleList.get(i);
                break;
            }
        }
        return selectedModule;
    }
    public void setBadgeList(List<Badge> list){
        this.badgeList = list;
        System.out.println("MY BADGES LIST : "+ list.size());
    }

    public List<Badge> getBadgeList(){
        System.out.println("&&&&&&&& BADGE SSSS &&&&&&&&&&&&&&");
        System.out.println("SIZE : "+ badgeList.size());
        return badgeList;
    }

    public int getBadgeID (String badgeName, List<Badge> list){
        int selectedBadgeId = 0;

        for(int i =0; i< list.size(); i++){
            if(list.get(i).getBadgeName().equals(badgeName)){
                selectedBadgeId = list.get(i).getId();
                break;
            }
        }

        return selectedBadgeId;
    }
    public void setBadgeNamesList(List<Badge> badgeList){
        badgeNamesList = new ArrayList<>();
        System.out.println("BADGE NAME LIST SIZE : " + badgeList.size());
        badgeNamesList.add("Select a badge...");
        for(int i =0; i < badgeList.size(); i++){
            this.badgeNamesList.add(badgeList.get(i).getBadgeName());

        }
    }
    public List<String> getBadgeNamesList(){
       // System.out.println("************* SETTING BADGE NAMES *************");
        for(int i =0; i < badgeNamesList.size(); i++){
            System.out.println("BADGE NAMES");
            System.out.println("Badge Names : " +badgeNamesList.get(i));

        }
        return badgeNamesList;
    }




    public void setSelectedBadgePosition(int position){
        this.selectedPosition = position;
    }
    public Badge getSelectedBadge(){
        selectedBadge = null;
        System.out.println("POSTION : " + selectedPosition);
        for(int i =0; i < badgeList.size(); i++){
            if(i == selectedPosition){
                selectedBadge = badgeList.get(i);
                break;
            }
        }
        return selectedBadge;
    }



    public void setSelectedClassAllPosition(int position){
        this.selectedPosition = position;
    }
    public Classroom getSelectedClassroomAll(){
        selectedClassroom = null;
        System.out.println("POSTION : " + selectedPosition);
        for(int i =0; i < allClassroomList.size(); i++){
            if(i == selectedPosition){
                selectedClassroom = allClassroomList.get(i);
                break;
            }
        }
        return selectedClassroom;
    }

    public void setRecordList(List<Timetable>timetable){
        this.recordList= new ArrayList<>();
        recordList = timetable;
    }

    public List<Timetable> getRecordList(){
        System.out.println("****** RECCCCCORDD LISSST ******");
        System.out.println(recordList.size());
        return recordList;
    }


    public void setClassroomList(List<Classroom> list){
        this.classroomList = list;
    }
    public List<Classroom> getClassroomList(){
        return  classroomList;
    }

    public void setClassroomNamesList(List<Classroom> classroomList){
        classroomNamesList = new ArrayList<>();
        System.out.println("Classroom Name LIST SIZE : " + classroomList.size());
        classroomNamesList.add("Select a Classroom...");
        for(int i =0; i < classroomList.size(); i++){
            this.classroomNamesList.add(classroomList.get(i).getClassroomName());
        }
    }
    public List<String> getClassroomNamesList(){
        for(int i =0; i < classroomNamesList.size(); i++){
            System.out.println("CLASSROOM NAMES");
            System.out.println("CLASSROOM Name : " +classroomNamesList.get(i));

        }
        return classroomNamesList;
    }

    public int getClassroomID (String classname, List<Classroom> list){
        int selectedClassroomID = 0;

        for(int i =0; i< list.size(); i++){
            if(list.get(i).getClassroomName().equals(classname)){
                selectedClassroomID = list.get(i).getId();
                break;
            }
        }

        return selectedClassroomID;
    }





    public void setClassroomModuleList(List<Classroom> list){
        this.classroomModuleList = list;
    }
    public List<Classroom> getClassroomModuleList(){
        return  classroomModuleList;
    }

    public void setClassroomModuleNamesList(List<Classroom> classroomList){
        classroomModuleNamesList = new ArrayList<>();
        System.out.println("Classroom Name LIST SIZE : " + classroomList.size());
        classroomModuleNamesList.add("Select a Classroom...");
        for(int i =0; i < classroomList.size(); i++){
            this.classroomModuleNamesList.add(classroomList.get(i).getClassroomName());
        }
    }
    public List<String> getClassroomModuleNamesList(){
        for(int i =0; i < classroomModuleNamesList.size(); i++){
            System.out.println("CLASSROOM NAMES");
            System.out.println("CLASSROOM Name : " +classroomModuleNamesList.get(i));

        }
        return classroomModuleNamesList;
    }

    public int getClassroomModuleID (String classname, List<Classroom> list){
        int selectedClassroomID = 0;

        for(int i =0; i< list.size(); i++){
            if(list.get(i).getClassroomName().equals(classname)){
                selectedClassroomID = list.get(i).getId();
                break;
            }
        }

        return selectedClassroomID;
    }




    public void setAllClassroomList(List<Classroom> list){
        this.allClassroomList = list;
    }
    public List<Classroom> getAllClassroomList(){
        return  allClassroomList;
    }

    public void setAllClassroomNamesList(List<Classroom> classroomList){
        allClassroomNamesList = new ArrayList<>();
        System.out.println("Classroom Name LIST SIZE : " + classroomList.size());
        allClassroomNamesList.add("Select a Classroom...");
        for(int i =0; i < classroomList.size(); i++){
            this.allClassroomNamesList.add(classroomList.get(i).getClassroomName());
        }
    }
    public List<String> getAllClassroomNamesList(){
        for(int i =0; i < allClassroomNamesList.size(); i++){
            System.out.println("CLASSROOM NAMES");
            System.out.println("CLASSROOM Name : " +allClassroomNamesList.get(i));

        }
        return allClassroomNamesList;
    }

    public int getAllClassroomID (String classname, List<Classroom> list){
        int selectedClassroomID = 0;

        for(int i =0; i< list.size(); i++){
            if(list.get(i).getClassroomName().equals(classname)){
                selectedClassroomID = list.get(i).getId();
                break;
            }
        }

        return selectedClassroomID;
    }




    public List<String> getDuration(){
        bookingDurationList = new ArrayList<>();
        bookingDurationList.add("Select a duration...");
        for(int i =1; i < 8; i++){
            if(i == 1){
                bookingDurationList.add(i+" hour");
            }else{
                bookingDurationList.add(i+" hours");
            }
        }
        return bookingDurationList;
    }


    public Context getLecturerOptionView() {
        return lecturerOptionView;
    }

    public void setLecturerOptionView(Context lecturerOptionView) {
        this.lecturerOptionView = lecturerOptionView;
    }

    public String getCurrentUserType() {
        return currentUserType;
    }

    public void setCurrentUserType(String currentUserType) {
        this.currentUserType = currentUserType;
    }

    public String getAdminSelectedOption() {
        return adminSelectedOption;
    }

    public void setAdminSelectedOption(String adminSelectedOption) {
        this.adminSelectedOption = adminSelectedOption;
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
//    public User getUser() {
//        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return new User(
//                sharedPreferences.getInt(KEY_ID, -1),
//                sharedPreferences.getString(KEY_USERNAME, null),
//                sharedPreferences.getString(KEY_EMAIL, null),
//                sharedPreferences.getString(KEY_GENDER, null)
//        );
//    }

    //this method will logout the user
//    public void logout() {
//        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//        ctx.startActivity(new Intent(ctx, LoginActivity.class));
//    }
}
