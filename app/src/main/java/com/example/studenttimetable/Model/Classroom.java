package com.example.studenttimetable.Model;

import java.sql.Timestamp;

public class Classroom {

    private int id;
    private String classroomName;
    private int floorLevel;
    private int isActive;
    private int isAllocatedForCourseModule;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

    private String createdString;
    private String modifiedString;

    public Classroom(int id, String classroomName, int floorLevel, int isAllocatedForCourseModule, int isActive, String created, String createdBy, String modified, String modifiedBy) {
        this.id = id;
        this.classroomName = classroomName;
        this.floorLevel = floorLevel;
        this.isAllocatedForCourseModule = isAllocatedForCourseModule;
        this.isActive = isActive;
        this.createdString = created;
        this.createdBy = createdBy;
        this.modifiedString = modified;
        this.modifiedBy = modifiedBy;
    }

    public Classroom(int id, String classroomName, int floorLevel, int isAllocatedForCourseModule, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.id = id;
        this.classroomName = classroomName;
        this.floorLevel = floorLevel;
        this.isAllocatedForCourseModule = isAllocatedForCourseModule;
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

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsAllocatedForCourseModule() {
        return isAllocatedForCourseModule;
    }

    public void setIsAllocatedForCourseModule(int isAllocatedForCourseModule) {
        this.isAllocatedForCourseModule = isAllocatedForCourseModule;
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
