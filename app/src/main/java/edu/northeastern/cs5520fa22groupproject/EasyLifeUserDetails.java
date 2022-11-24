package edu.northeastern.cs5520fa22groupproject;

public class EasyLifeUserDetails {
    static String username = "";
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
}
