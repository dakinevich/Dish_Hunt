package ru.example.dishhunt.data.data_sources.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.example.dishhunt.data.data_sources.room.entites.CommentEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserSavedRecipes;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRecipe(RecipeEntity recipeEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertComment(CommentEntity commentEntity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserSavedRecipesIds(UserSavedRecipes userSavedRecipes);
    @Query("DELETE FROM user_saved_recipes_table WHERE user_id = :user_id AND recipe_id = :recipe_id ")
    void deleteUserSavedRecipesIds(int user_id, int recipe_id);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    LiveData<List<RecipeEntity>> getAlphabetizedRecipes();

    @Query("SELECT * FROM recipe_table WHERE CookTime BETWEEN :time_from AND :time_to ORDER BY title ASC")
    LiveData<List<RecipeEntity>> getSearchRecipes(int time_from, int time_to);

    @Query("SELECT * FROM recipe_table WHERE id = :id_m")
    LiveData<RecipeEntity> getRecipe(int id_m);

    @Query("SELECT * FROM comment_table WHERE RecipeId = :id_m")
    LiveData<List<CommentEntity>> getRecipeComments(int id_m);

    @Query("SELECT recipe_id FROM user_saved_recipes_table WHERE user_id = :user_id")
    LiveData<List<Integer>> getUserSavedRecipesIds(int user_id);

    @Query("SELECT * FROM recipe_table WHERE AuthorId = :user_id")
    LiveData<List<RecipeEntity>> getUserRecipes(int user_id);
}
