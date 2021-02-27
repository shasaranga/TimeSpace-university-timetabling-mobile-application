package com.example.studenttimetable.Model;

import java.sql.Timestamp;

public class Module {

    private int moduleID;
    private String moduleName;
    private String moduleDescription;
    private String courseName;
    private int courseID;
    private String lecturerID;
    private String lecturerFName;
    private String lecturerLName;
    private String lecturerFullName;
    private int classID;
    private String className;
    private int isActive;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

    private String createdString;
    private String modifiedString;


    public Module(int moduleID, String moduleName, String moduleDescription, String courseName, int courseID, String lecturerID, String lecturerFName, String lecturerLName,String lecturerFullName,int classID,String className, int isActive, String created, String createdBy, String modified, String modifiedBy) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.courseName = courseName;
        this.courseID = courseID;
        this.lecturerID = lecturerID;
        this.lecturerFName = lecturerFName;
        this.lecturerLName = lecturerLName;
        this.lecturerFullName = lecturerFullName;
        this.classID = classID;
        this.className = className;
        this.isActive = isActive;
        this.createdString = created;
        this.createdBy = createdBy;
        this.modifiedString = modified;
        this.modifiedBy = modifiedBy;
    }

    public Module(int moduleID, String moduleName, String moduleDescription, String courseName, int courseID, String lecturerID,int classID,String className, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.moduleDescription = moduleDescription;
        this.courseName = courseName;
        this.courseID = courseID;
        this.lecturerID = lecturerID;
        this.classID = classID;
        this.className = className;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
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

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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
}
