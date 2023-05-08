package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.data.models.Recipe;

@Entity(tableName = "comment_table")
public class CommentEntity {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private int RecipeId;
    @NonNull
    private int AuthorId;
    @NonNull
    private String Text;
    @NonNull
    private int PubTime;

    public CommentEntity(int recipeId, int authorId, @NonNull String text, int pubTime) {
        AuthorId = authorId;
        Text = text;
        PubTime = pubTime;
        RecipeId = recipeId;
    }

    public CommentEntity() {

    }

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

    @NonNull
    public String getText() {
        return Text;
    }

    public void setText(@NonNull String text) {
        Text = text;
    }

    public int getPubTime() {
        return PubTime;
    }

    public void setPubTime(int pubTime) {
        PubTime = pubTime;
    }

    public Comment toDomainModel() {
        return new Comment(id, AuthorId, Text, PubTime);
    }
}
