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

    @Query("SELECT * FROM recipe_table WHERE CookTime BETWEEN :time_from AND :time_to ORDER BY title ASC")
    LiveData<List<RecipeEntity>> getSearchRecipes(int time_from, int time_to);

    @Query("SELECT * FROM recipe_table WHERE id = :id_m")
    LiveData<RecipeEntity> getRecipe(int id_m);
}
