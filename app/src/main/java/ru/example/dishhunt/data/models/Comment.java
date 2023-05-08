package ru.example.dishhunt.data.models;


public class Comment {

    private int id;
    private int RecipeId;
    private int AuthorId;
    private String Text;
    private int PubTime;
    private String ImgSrc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public int getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(int authorId) {
        AuthorId = authorId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getPubTime() {
        return PubTime;
    }

    public void setPubTime(int pubTime) {
        PubTime = pubTime;
    }

    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        ImgSrc = imgSrc;
    }

    public Comment(int recipeId, int authorId, String text, int pubTime) {
        RecipeId = recipeId;
        AuthorId = authorId;
        Text = text;
        PubTime = pubTime;
    }
}
