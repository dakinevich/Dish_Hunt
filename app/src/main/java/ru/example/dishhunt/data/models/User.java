package ru.example.dishhunt.data.models;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class User {
    private int id;

    private String Name;
    private String Bio;
    private String ImgSrc;


    public User(String name, String bio, String imgSrc, int id) {
        Name = name;
        Bio = bio;
        ImgSrc = imgSrc;
        this.id = id;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        ImgSrc = imgSrc;
    }
}
