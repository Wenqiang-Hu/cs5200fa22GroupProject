package edu.northeastern.cs5520fa22groupproject.model;

public class EasyLifeUserDetails {
    static String username = "";
    static String imageURL = "default";
    private String id;

    public EasyLifeUserDetails(String id, String url, String username) {
        this.id = id;
        this.username = username;
        this.imageURL = url;
    }

    public EasyLifeUserDetails() {
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        EasyLifeUserDetails.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void setImageURL(String imageURL) {
        EasyLifeUserDetails.imageURL = imageURL;
    }

    public static String getImageURL() {
        return imageURL;
    }
}
