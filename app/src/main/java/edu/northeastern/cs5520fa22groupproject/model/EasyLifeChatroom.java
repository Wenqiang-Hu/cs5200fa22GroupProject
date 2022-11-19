package edu.northeastern.cs5520fa22groupproject.model;

public class EasyLifeChatroom {
    private String intro;
    private String logoPath;
    private String roomname;
    private String id;

    public EasyLifeChatroom(String id, String intro, String logoPath, String roomname) {
        this.id = id;
        this.intro = intro;
        this.logoPath = logoPath;
        this.roomname = roomname;
    }

    public String getId() {
        return id;
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
