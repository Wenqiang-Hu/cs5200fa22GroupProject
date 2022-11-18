package edu.northeastern.cs5520fa22groupproject.model;

public class Chatroom {
    private String intro;
    private String logoPath;
    private String roomname;

    public Chatroom(String intro, String logoPath, String roomname) {
        this.intro = intro;
        this.logoPath = logoPath;
        this.roomname = roomname;
    }

    public String getIntro() {
        return intro;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getRoomname() {
        return roomname;
    }
}
