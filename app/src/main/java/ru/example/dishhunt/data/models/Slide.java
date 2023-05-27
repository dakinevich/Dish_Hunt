package ru.example.dishhunt.data.models;


public class Slide {
    private String ImgSrc;
    private String Description;

    public Slide(String imgSrc, String description) {
        ImgSrc = imgSrc;
        Description = description;
    }

    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        ImgSrc = imgSrc;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}
