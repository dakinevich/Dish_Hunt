package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import ru.example.dishhunt.data.models.Recipe;

@Entity(tableName = "recipe_table")
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String Title;
    @NonNull
    private String Description;
    @NonNull
    private int CookTime;
    @NonNull
    private int AuthorId;
    @NonNull
    private int Views;
    @NonNull
    private int Likes;
    @NonNull
    private int CookComplexity;
    @NonNull
    private int Portions;

    private String ImgSrc;

    public RecipeEntity() {

    }

    public RecipeEntity(@NonNull String Title, @NonNull String Description, int CookTime, int AuthorId, int Views, int Likes, int CookComplexity, int Portions, String ImgSrc) {
        this.id = id;
        this.Title = Title;
        this.Description = Description;
        this.CookTime = CookTime;
        this.AuthorId = AuthorId;
        this.Views = Views;
        this.Likes = Likes;
        this.CookComplexity = CookComplexity;
        this.Portions = Portions;
        this.ImgSrc = ImgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return Title;
    }

    public void setTitle(@NonNull String title) {
        Title = title;
    }

    @NonNull
    public String getDescription() {
        return Description;
    }

    public void setDescription(@NonNull String description) {
        Description = description;
    }

    public int getCookTime() {
        return CookTime;
    }

    public void setCookTime(int cookTime) {
        CookTime = cookTime;
    }

    public int getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(int authorId) {
        AuthorId = authorId;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public int getCookComplexity() {
        return CookComplexity;
    }

    public void setCookComplexity(int cookComplexity) {
        CookComplexity = cookComplexity;
    }

    public int getPortions() {
        return Portions;
    }

    public void setPortions(int portions) {
        Portions = portions;
    }

    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        ImgSrc = imgSrc;
    }

    public Recipe toDomainModel() {
        return new Recipe(id,false, false, AuthorId, Views, Likes, 100, Portions, CookTime, CookComplexity, 0, 0, 0, 0, Title, ImgSrc, Description, "");
    }
}
