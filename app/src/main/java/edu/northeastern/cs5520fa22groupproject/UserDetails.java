package edu.northeastern.cs5520fa22groupproject;

public class UserDetails {
    static String username = "";
    static String chatWith = "";
    static int happyUsed = 0;
    static int sadUsed = 0;
    static String uid = "";
    static String imageURL = "";

    public static int getSadUsed() {
        return sadUsed;
    }

    public static void setSadUsed(int sadUsed) {
        UserDetails.sadUsed = sadUsed;
    }

    public static int getHappyUsed() {
        return happyUsed;
    }

    public static void setHappyUsed(int happyUsed) {
        UserDetails.happyUsed = happyUsed;
    }

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
