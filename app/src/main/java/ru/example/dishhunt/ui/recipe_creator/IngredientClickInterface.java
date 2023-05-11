package ru.example.dishhunt.ui.recipe_creator;

import ru.example.dishhunt.data.models.Ingredient;

public interface IngredientClickInterface {
    void onMinusClick(Ingredient ingredient);
    void onEdit(Ingredient ingredient, int value);
}
