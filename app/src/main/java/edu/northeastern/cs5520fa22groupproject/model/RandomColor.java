package edu.northeastern.cs5520fa22groupproject.model;

import com.google.gson.annotations.SerializedName;

public class RandomColor {
    private String hex;
    private String rgb;
    private String hsl;
    @SerializedName("body")

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getHsl() {
        return hsl;
    }

    public void setHsl(String hsl) {
        this.hsl = hsl;
    }
}
