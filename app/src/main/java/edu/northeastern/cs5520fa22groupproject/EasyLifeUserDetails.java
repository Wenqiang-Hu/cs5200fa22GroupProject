package edu.northeastern.cs5520fa22groupproject;

public class EasyLifeUserDetails {
    static String username = "";
    static String chatWith = "";
    static String imageURL = "default";

    private String id;

    public EasyLifeUserDetails(String username, String id) {
        this.id = id;
        this.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        EasyLifeUserDetails.username = username;
    }

    public static String getChatWith() {
        return chatWith;
    }

    public static void setChatWith(String chatWith) {
        EasyLifeUserDetails.chatWith = chatWith;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static String getImageURL() {
        return imageURL;
    }
}
