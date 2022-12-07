package edu.northeastern.cs5520fa22groupproject.model;

public class Post {
    String user;
    String context;
    String icon;
    String location;

    public Post(String user, String context, String icon, String location) {
        this.user = user;
        this.context = context;
        this.icon = icon;
        this.location = location;
    }

    public Post() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContext() {
        return context;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}