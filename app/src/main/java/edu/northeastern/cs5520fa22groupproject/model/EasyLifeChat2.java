package edu.northeastern.cs5520fa22groupproject.model;

public class EasyLifeChat2 {
    private String senderId;
    private String username;
    private String message;

    public EasyLifeChat2(String message, String senderId, String username) {
        this.senderId = senderId;
        this.message = message;
        this.username = username;
    }

    public EasyLifeChat2() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
