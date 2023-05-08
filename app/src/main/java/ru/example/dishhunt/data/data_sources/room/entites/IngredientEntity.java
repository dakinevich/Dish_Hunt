package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

}
