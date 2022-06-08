package com.sattazalyk.car_gps2.model;

public class Post {
    private String header, text, photoURL;

    public  Post(String header, String text, String photoURL){
        this.header = header;
        this.text = text;
        this.photoURL = photoURL;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
