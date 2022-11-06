package edu.northeastern.cs5520fa22groupproject;

public class UserDetails {
    static String username = "";
    static String chatWith = "";

    private String id;

    public UserDetails(String username, String id) {
        this.id = id;
        this.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserDetails.username = username;
    }

    public static String getChatWith() {
        return chatWith;
    }

    public static void setChatWith(String chatWith) {
        UserDetails.chatWith = chatWith;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
