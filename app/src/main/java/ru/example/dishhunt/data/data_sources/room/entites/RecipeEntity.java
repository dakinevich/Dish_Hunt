package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.example.dishhunt.data.models.Ingredient;
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
    private String IngredientsDescription;
    @NonNull
    private int CookTime;
    @NonNull
    private int AuthorId;
    @NonNull
    private int Views;
    @NonNull
    private int CookComplexity;
    @NonNull
    private int Portions;

    private String ImgSrc;

    public RecipeEntity() {

    }

    public RecipeEntity(@NonNull String Title, @NonNull String Description, int CookTime, int AuthorId, int Views, int CookComplexity, int Portions, String ImgSrc, String ingredientsDescription) {
        this.Title = Title;
        this.Description = Description;
        this.CookTime = CookTime;
        this.AuthorId = AuthorId;
        this.Views = Views;
        this.CookComplexity = CookComplexity;
        this.Portions = Portions;
        this.ImgSrc = ImgSrc;
        this.IngredientsDescription = ingredientsDescription;
    }

    @NonNull
    public String getIngredientsDescription() {
        return IngredientsDescription;
    }

    public void setIngredientsDescription(@NonNull String ingredientsDescription) {
        IngredientsDescription = ingredientsDescription;
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

    public Recipe toDomainModel(List<IngredientWithProduct> ingredientsWithProduct) {
        List<Ingredient> ingredients = ingredientsWithProduct.stream().map(val->{
            return val.ingredientEntity.toDomainModel(val.productEntity);
        }).collect(Collectors.toList());
        return new Recipe(id, false, AuthorId, Views, 0,
                Portions, CookTime, CookComplexity, Title, ImgSrc, Description, ingredients, IngredientsDescription);

    }
}
