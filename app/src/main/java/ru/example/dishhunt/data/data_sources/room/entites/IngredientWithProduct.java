package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.room.DatabaseView;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class IngredientWithProduct {
    @Embedded
    public IngredientEntity ingredientEntity;
    @Relation(
            parentColumn = "ProductId",
            entityColumn = "id"
    )
    public ProductEntity productEntity;

    public IngredientWithProduct(){}

}
