package ru.example.dishhunt.data.models;

public class Recipe {
    private int id;
    private String mTitle;
    private String mCookTime;
    private String mPortions;
    private String mImgSrc;
    private String mPrice;

    public Recipe(String Title, String CookTime, String Portions, String ImgSrc) {
        this.mTitle = Title;
        this.mCookTime = CookTime;
        this.mPortions = Portions;
        this.mImgSrc = ImgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getCookTime() {
        return mCookTime;
    }

    public void setCookTime(String mCookTime) {
        this.mCookTime = mCookTime;
    }

    public String getPortions() {
        return mPortions;
    }

    public void setPortions(String mPortions) {
        this.mPortions = mPortions;
    }

    public String getImgSrc() {
        return mImgSrc;
    }

    public void setImgSrc(String mImgSrc) {
        this.mImgSrc = mImgSrc;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }


}
