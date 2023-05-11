package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.stream.Collectors;

import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.User;

@Entity(tableName = "user_table")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String Name;
    @NonNull
    private String Bio;
    @NonNull
    private String ImgSrc;

    public UserEntity(){}

    public UserEntity(@NonNull String name, @NonNull String bio, @NonNull String imgSrc) {
        Name = name;
        Bio = bio;
        ImgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return Name;
    }

    public void setName(@NonNull String name) {
        Name = name;
    }

    @NonNull
    public String getBio() {
        return Bio;
    }

    public void setBio(@NonNull String bio) {
        Bio = bio;
    }

    @NonNull
    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(@NonNull String imgSrc) {
        ImgSrc = imgSrc;
    }
    public User toDomainModel() {
        return new User(Name, Bio, ImgSrc);
    }
}
