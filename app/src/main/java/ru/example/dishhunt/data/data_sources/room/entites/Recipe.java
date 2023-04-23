package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @NonNull
    @ColumnInfo(name = "cook_time")
    private String mCookTime;

    @NonNull
    @ColumnInfo(name = "portions")
    private String mPortions;

    @ColumnInfo(name = "img_src")
    private String mImgSrc;

    public Recipe() {

    }

    public Recipe(@NonNull String Title, @NonNull String CookTime, @NonNull String Portions, String ImgSrc) {
        this.mTitle = Title;
        this.mCookTime = CookTime;
        this.mPortions = Portions;
        this.mImgSrc = ImgSrc;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getCookTime() {
        return mCookTime;
    }

    public String getPortions() {
        return mPortions;
    }

    public String getImgSrc() {
        return mImgSrc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    public void setCookTime(@NonNull String mCookTime) {
        this.mCookTime = mCookTime;
    }

    public void setPortions(@NonNull String mPortions) {
        this.mPortions = mPortions;
    }

    public void setImgSrc(String mImgSrc) {
        this.mImgSrc = mImgSrc;
    }
}
