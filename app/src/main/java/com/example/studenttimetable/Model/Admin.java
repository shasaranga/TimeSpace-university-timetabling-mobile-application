package com.example.studenttimetable.Model;

import java.sql.Timestamp;

public class Admin {

    private int id;
    private String adminID;
    private String fName;
    private String lName;
    private String address;
    private String email;
    private String password;
    private String contactNumber;
    private String emergencyNum;
  //  private String profileImgString;
    private int isActive;
    private Timestamp created;
    private String createdBy;
    private Timestamp modified;
    private String modifiedBy;

    private String createdString;
    private String modifiedString;

    private String userType;
    public Admin (){}
    public Admin(int id, String adminID, String fName, String lName, String address, String email, String password, String contactNumber, String emergencyNum, int isActive, Timestamp created, String createdBy, Timestamp modified, String modifiedBy) {
        this.id = id;
        this.adminID = adminID;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.emergencyNum = emergencyNum;
        this.isActive = isActive;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;

        this.userType = "Admin";
    }
    public Admin(int id, String adminID, String fName, String lName, String address, String email, String password, String contactNumber, String emergencyNum, int isActive, String created, String createdBy, String modified, String modifiedBy) {
        this.id = id;
        this.adminID = adminID;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.emergencyNum = emergencyNum;
        this.isActive = isActive;
        this.createdString = created;
        this.createdBy = createdBy;
        this.modifiedString = modified;
        this.modifiedBy = modifiedBy;

        this.userType = "Admin";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmergencyNum() {
        return emergencyNum;
    }

    public void setEmergencyNum(String emergencyNum) {
        this.emergencyNum = emergencyNum;
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
