package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.Slide;

@Entity(tableName = "slides_table")
public class SlideEntity implements Comparable<SlideEntity>{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String ImgSrc;
    @NonNull
    private String Description;
    @NonNull
    private int RecipeId;
    @NonNull
    private int n;

    public SlideEntity(@NonNull String imgSrc, @NonNull String description, int recipeId, int n) {
        ImgSrc = imgSrc;
        Description = description;
        RecipeId = recipeId;
        this.n = n;
    }
    public SlideEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(@NonNull String imgSrc) {
        ImgSrc = imgSrc;
    }

    @NonNull
    public String getDescription() {
        return Description;
    }

    public void setDescription(@NonNull String description) {
        Description = description;
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public List<Slide> toDomainModel(List<SlideEntity> slideEntities) {
        Collections.sort(slideEntities);

        return slideEntities.stream().map(
                e -> new Slide(e.getImgSrc(), e.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public int compareTo(SlideEntity o) {
        return -Integer.compare(o.getN(), n);
    }
}

