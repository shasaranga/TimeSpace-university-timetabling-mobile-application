package com.example.studenttimetable.Model;

import java.sql.Time;
import java.sql.Timestamp;

public class Timetable {
    private int recordID;
    private int courseID;
    private String courseName;
    private int badgeID;
    private String badgeName;
    private int weekDayID;
    private String weekday;
    private int duration;
    private Time startTime;
    private Time endTime;
    private int moduleID;
    private String moduleName;
    private String lecturerID;
    private String lecturerFName;
    private String lecturerLName;
    private String lecturerFullName;
    private int isActive;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

    private String createdString;
    private  String modifiedString;
    private String startTimeString;
    private String endTimeString;

    private  String notScheduleMessage;

    public Timetable(String notScheduleMessage) {
        this.notScheduleMessage = notScheduleMessage;
    }

    public Timetable(int recordID, int courseID, String courseName, int badgeID, String badgeName, int weekDayID, String weekday, int duration, String startTime, String endTime, int moduleID, String moduleName, String lecturerID, String lecturerFName, String lecturerLName, String lecturerFullName, int isActive, String createdString, String createdBy, String modifiedString, String modifiedBy) {
        this.recordID = recordID;
        this.courseID = courseID;
        this.courseName = courseName;
        this.badgeID = badgeID;
        this.badgeName = badgeName;
        this.weekDayID = weekDayID;
        this.weekday = weekday;
        this.duration = duration;

        this.startTimeString = startTime;
       // this.startTime = startTime;

        this.endTimeString = endTime;
        //this.endTime = endTime;


        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.lecturerID = lecturerID;
        this.lecturerFName = lecturerFName;
        this.lecturerLName = lecturerLName;
        this.lecturerFullName = lecturerFullName;
        this.isActive = isActive;


        this.createdString = createdString;
//        this.created = Timestamp.valueOf(createdString);
//        System.out.println("***** Created : "+created.toString()+"*****");

        this.createdBy = createdBy;

        this.modifiedString = modifiedString;
//        this.created = Timestamp.valueOf(modifiedString);
//        System.out.println("***** Modified : "+modified.toString()+"*****");

        this.modifiedBy = modifiedBy;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
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

    public int getBadgeID() {
        return badgeID;
    }

    public void setBadgeID(int badgeID) {
        this.badgeID = badgeID;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public int getWeekDayID() {
        return weekDayID;
    }

    public void setWeekDayID(int weekDayID) {
        this.weekDayID = weekDayID;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getLecturerFName() {
        return lecturerFName;
    }

    public void setLecturerFName(String lecturerFName) {
        this.lecturerFName = lecturerFName;
    }

    public String getLecturerLName() {
        return lecturerLName;
    }

    public void setLecturerLName(String lecturerLName) {
        this.lecturerLName = lecturerLName;
    }

    public String getLecturerFullName() {
        return lecturerFullName;
    }

    public void setLecturerFullName(String lecturerFullName) {
        this.lecturerFullName = lecturerFullName;
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

    public String getCreatedString() {
        return createdString;
    }

    public void setCreatedString(String createdString) {
        this.createdString = createdString;
    }

    public String getModifiedString() {
        return modifiedString;
    }

    public void setModifiedString(String modifiedString) {
        this.modifiedString = modifiedString;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public String getNotScheduleMessage() {
        return notScheduleMessage;
    }

    public void setNotScheduleMessage(String notScheduleMessage) {
        this.notScheduleMessage = notScheduleMessage;
    }
}
