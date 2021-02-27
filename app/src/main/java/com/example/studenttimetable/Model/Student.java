package com.example.studenttimetable.Model;

import java.sql.Timestamp;

public class Student {
    private int id;
    private String studentID;
    private String fName;
    private String lName;
    private int courseID;
    private String courseName;
    private String address;
    private String email;
    private String password;
    private String mobileNumber;
    private String emergencyNum;
    private String profileImgString;
    private int isActive;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

    private String userType;

    private String createdString;
    private String modifiedString;

    private byte[] profileImg;

    private Course followingCourse;
    public Student(){
    }
    public Student(int id, String studentID, String fName, String lName, int courseID, String address, String email, String password, String mobileNumber, String emergencyNum, int isActive, String created, String createdBy, String modified, String modifiedBy) {
        this.id = id;
        this.studentID = studentID;
        this.fName = fName;
        this.lName = lName;
        this.courseID = courseID;
        this.address = address;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.emergencyNum = emergencyNum;
        this.isActive = isActive;
        this.createdString = created;
        this.createdBy = createdBy;
        this.modifiedString = modified;
        this.modifiedBy = modifiedBy;

        this.userType = "Student";
        this.followingCourse = null;
    }

    public Student(int id, String studentID, String fName, String lName, int courseID, String address, String email, String password, String mobileNumber, String emergencyNum, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.id = id;
        this.studentID = studentID;
        this.fName = fName;
        this.lName = lName;
        this.courseID = courseID;
        this.address = address;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.emergencyNum = emergencyNum;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }

    public Student(int id, String studentID, String fName, String lName, int courseID, String courseName, String address, String email, String password, String mobileNumber, String emergencyNum, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.id = id;
        this.studentID = studentID;
        this.fName = fName;
        this.lName = lName;
        this.courseID = courseID;
        this.courseName = courseName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.emergencyNum = emergencyNum;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmergencyNum() {
        return emergencyNum;
    }

    public void setEmergencyNum(String emergencyNum) {
        this.emergencyNum = emergencyNum;
    }

    public String getProfileImgString() {
        return profileImgString;
    }

    public void setProfileImgString(String profileImgString) {
        this.profileImgString = profileImgString;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public byte[] getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(byte[] profileImg) {
        this.profileImg = profileImg;
    }

    public Course getFollowingCourse() {
        return followingCourse;
    }

    public void setFollowingCourse(Course followingCourse) {
        this.followingCourse = followingCourse;
    }
}
