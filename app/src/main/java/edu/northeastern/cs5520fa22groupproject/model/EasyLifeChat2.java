package edu.northeastern.cs5520fa22groupproject.model;

public class EasyLifeChat2 {
    private String senderId;
//    private String senderUserName;
    private String message;

    public EasyLifeChat2(String senderId, String message) {
        this.senderId = senderId;
//        this.senderUserName = senderUserName;
        this.message = message;
    }

    public EasyLifeChat2() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

//    public String getSenderUserName() {
//        return senderUserName;
//    }
//
//    public void setSenderUserName(String senderUserName) {
//        this.senderUserName = senderUserName;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
