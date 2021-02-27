package com.example.studenttimetable.Model;

public class MessageClass {
    private String message;
    private boolean isValid;

    public MessageClass(boolean isValid, String message){
        this.isValid = isValid;
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
