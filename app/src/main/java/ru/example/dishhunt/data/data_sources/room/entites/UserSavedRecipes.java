package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.room.Entity;



@Entity(tableName = "user_saved_recipes_table", primaryKeys = {"recipe_id", "user_id"})
public class UserSavedRecipes {

    private int recipe_id;
    private int user_id;
    public UserSavedRecipes() {

    }

    public UserSavedRecipes(int recipe_id, int user_id) {
        this.recipe_id = recipe_id;
        this.user_id = user_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
