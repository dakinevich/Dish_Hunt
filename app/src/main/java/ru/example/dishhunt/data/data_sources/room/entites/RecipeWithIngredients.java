package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecipeWithIngredients {
    @Embedded
    public RecipeEntity recipeEntity;
    @Relation(
            entity = IngredientEntity.class,
            parentColumn = "id",
            entityColumn = "RecipeId"
    )
    public List<IngredientWithProduct> ingredientWithProduct;
    public RecipeWithIngredients(){}
}
