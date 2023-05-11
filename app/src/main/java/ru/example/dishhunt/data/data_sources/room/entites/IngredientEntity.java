package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.data.models.Ingredient;

@Entity(tableName = "ingredient_table")
public class IngredientEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private int ProductId;
    @NonNull
    private int RecipeId;
    @NonNull
    private int Amount;



    public IngredientEntity(){}

    public IngredientEntity(int productId, int recipeId, int amount) {
        ProductId = productId;
        RecipeId = recipeId;
        Amount = amount;
    }

    public Ingredient toDomainModel(ProductEntity productEntity) {
        return new Ingredient(id, Amount, productEntity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
