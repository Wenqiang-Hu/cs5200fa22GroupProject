package edu.northeastern.cs5520fa22groupproject.model;

import java.util.Date;

public class Message {

    private String user;
    private long time;
    private String sticker;

    public Message(String sticker, String user){
        this.sticker = sticker;
        this.user = user;
        time = new Date().getTime();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }
}
