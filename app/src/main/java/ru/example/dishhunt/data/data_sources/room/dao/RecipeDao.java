package ru.example.dishhunt.data.data_sources.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RecipeEntity recipeEntity);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    LiveData<List<RecipeEntity>> getAlphabetizedRecipes();
}