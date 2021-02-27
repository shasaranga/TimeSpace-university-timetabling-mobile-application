package com.example.studenttimetable.Model;

import java.sql.Timestamp;

public class Badge {
    private int id;
    private String badgeName;
    private int courseID;
    private String courseName;
    private int badgeYear;
    private int isActive;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

    private String createdString;
    private String modifiedString;

    public Badge(int id, String badgeName, int courseID, String courseName, int badgeYear, int isActive, String created, String createdBy, String modified, String modifiedBy) {
        this.id = id;
        this.badgeName = badgeName;
        this.courseID = courseID;
        this.courseName = courseName;
        this.badgeYear = badgeYear;
        this.isActive = isActive;
        this.createdString = created;
        this.createdBy = createdBy;
        this.modifiedString = modified;
        this.modifiedBy = modifiedBy;
    }

    public Badge(int id, String badgeName, int courseID, String courseName, int badgeYear, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.id = id;
        this.badgeName = badgeName;
        this.courseID = courseID;
        this.courseName = courseName;
        this.badgeYear = badgeYear;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
    }

    public Badge(int id, String badgeName, int courseID, int badgeYear, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.id = id;
        this.badgeName = badgeName;
        this.courseID = courseID;
        this.badgeYear = badgeYear;
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

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
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

    public int getBadgeYear() {
        return badgeYear;
    }

    public void setBadgeYear(int badgeYear) {
        this.badgeYear = badgeYear;
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
}
