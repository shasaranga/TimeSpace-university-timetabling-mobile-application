package com.example.studenttimetable.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Booking {

    private int bookingID;
    private int classID;
    private String className;
    private int badgeID;
    private String badgeName;
    private int lecturerID;
    private String lecturerName;
    private int duration;
    private Timestamp startDate;
    private Timestamp endDate;
    private int isActive;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

    private LocalDateTime startDateLocal;
    private LocalDateTime endDateLocal;
    private LocalDateTime createdLocal;
    private LocalDateTime modifiedLocal;

    private String startDateLocalString;
    private String endDateLocalString;
    private String createdLocalString;
    private String modifiedLocalString;

    private String bookingIDString;
    private String webFormatStartDateString;
    private String webFormatEndDateString;

    public Booking(int bookingID, String bookingIDString, String className, String badgeName, String lecturerName, int duration, Timestamp startDate , Timestamp endDate, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.bookingID = bookingID;
        this.bookingIDString = bookingIDString;
        this.className = className;
        this.badgeName = badgeName;
        this.lecturerName = lecturerName;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }
    public Booking(int bookingID, int classroomID, String className, String badgeName, String lecturerName, int duration, Timestamp startDate , Timestamp endDate, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.bookingID = bookingID;
        this.classID = classroomID;
        this.bookingIDString = bookingIDString;
        this.className = className;
        this.badgeName = badgeName;
        this.lecturerName = lecturerName;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }

    public Booking(int bookingID, String className, String badgeName, String lecturerName, int duration, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.bookingID = bookingID;
        this.className = className;
        this.badgeName = badgeName;
        this.lecturerName = lecturerName;
        this.duration = duration;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }

    public Booking(int bookingID, int classroomID, String className, String badgeName, String lecturerName, int duration, LocalDateTime startDate , LocalDateTime endDate, int isActive, LocalDateTime created, String createdBy, LocalDateTime modified, String modifiedBy) {
        this.bookingID = bookingID;
        this.classID = classroomID;
        this.bookingIDString = bookingIDString;
        this.className = className;
        this.badgeName = badgeName;
        this.lecturerName = lecturerName;
        this.duration = duration;
        if(startDate != null){
            String date = startDate.toString().split("T")[0];
            String time = startDate.toString().split("T")[1];

            this.startDateLocalString = date + " " + time +":00";

            this.webFormatStartDateString =date +"+"+time+":00";

        }

        this.startDateLocal = startDate;
        if(endDate != null){
            String date = endDate.toString().split("T")[0];
            String time = endDate.toString().split("T")[1];

            this.endDateLocalString = date + " " + time +":00";
            this.webFormatEndDateString =date +"+"+time+":00";
        }
        this.endDateLocal = endDate;
        this.isActive = isActive;
        this.createdLocal = created;
        this.createdLocalString = created.toString();
        this.createdBy = createdBy;
        this.modifiedLocal = modified;
        this.modifiedLocalString = modified.toString();
        this.modifiedBy = modifiedBy;
    }
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingIDString() {
        return bookingIDString;
    }

    public void setBookingIDString(String bookingIDString) {
        this.bookingIDString = bookingIDString;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public int getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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

    public LocalDateTime getStartDateLocal() {
        return startDateLocal;
    }

    public void setStartDateLocal(LocalDateTime startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

    public LocalDateTime getEndDateLocal() {
        return endDateLocal;
    }

    public void setEndDateLocal(LocalDateTime endDateLocal) {
        this.endDateLocal = endDateLocal;
    }

    public LocalDateTime getCreatedLocal() {
        return createdLocal;
    }

    public void setCreatedLocal(LocalDateTime createdLocal) {
        this.createdLocal = createdLocal;
    }

    public LocalDateTime getModifiedLocal() {
        return modifiedLocal;
    }

    public void setModifiedLocal(LocalDateTime modifiedLocal) {
        this.modifiedLocal = modifiedLocal;
    }

    public String getStartDateLocalString() {
        return startDateLocalString;
    }

    public void setStartDateLocalString(String startDateLocalString) {
        this.startDateLocalString = startDateLocalString;
    }

    public String getEndDateLocalString() {
        return endDateLocalString;
    }

    public void setEndDateLocalString(String endDateLocalString) {
        this.endDateLocalString = endDateLocalString;
    }

    public String getCreatedLocalString() {
        return createdLocalString;
    }

    public void setCreatedLocalString(String createdLocalString) {
        this.createdLocalString = createdLocalString;
    }

    public String getModifiedLocalString() {
        return modifiedLocalString;
    }

    public void setModifiedLocalString(String modifiedLocalString) {
        this.modifiedLocalString = modifiedLocalString;
    }

    public String getWebFormatStartDateString() {
        return webFormatStartDateString;
    }

    public void setWebFormatStartDateString(String webFormatStartDateString) {
        this.webFormatStartDateString = webFormatStartDateString;
    }

    public String getWebFormatEndDateString() {
        return webFormatEndDateString;
    }

    public void setWebFormatEndDateString(String webFormatEndDateString) {
        this.webFormatEndDateString = webFormatEndDateString;
    }
}
