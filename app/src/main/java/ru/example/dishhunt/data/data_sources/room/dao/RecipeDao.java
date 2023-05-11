package ru.example.dishhunt.data.data_sources.room.dao;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ru.example.dishhunt.data.data_sources.room.entites.CommentEntity;
import ru.example.dishhunt.data.data_sources.room.entites.IngredientEntity;
import ru.example.dishhunt.data.data_sources.room.entites.IngredientWithProduct;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeWithIngredients;
import ru.example.dishhunt.data.data_sources.room.entites.UserEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserSavedRecipes;
import ru.example.dishhunt.data.data_sources.room.root.RecipeRoomDatabase;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;

@Dao
public interface RecipeDao {
    //Recipes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertRecipe(RecipeEntity recipeEntity);

    default void insertRecipeWithIngredients(Recipe recipe){
        //TODO something with your live
        new Thread(() -> {
            RecipeEntity recipeEntity = new RecipeEntity(recipe.getmTitle(), recipe.getmDescription(), recipe.getmCookTime(), recipe.getmAuthorId(), recipe.getmViews(), recipe.getmCookComplexity(), recipe.getmPortions(), recipe.getmImgSrc(), recipe.getmIngredientsDescription());
            int recipe_id = (int) insertRecipe(recipeEntity);

            recipe.getmIngredients().forEach(elem ->{
                insertIngredient(new IngredientEntity(elem.getProduct().getId(), recipe_id, elem.getAmount()));

            });
        }).start();

    }

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    LiveData<List<RecipeWithIngredients>> getAlphabetizedRecipes();

    @Transaction
    @Query("SELECT * FROM recipe_table WHERE CookTime " +
            "BETWEEN :time_from AND :time_to " +
            "AND Portions BETWEEN :portions_from AND :portions_to " +
            "ORDER BY title ASC")
    LiveData<List<RecipeWithIngredients>> getSearchRecipes(int time_from, int time_to, int portions_from, int portions_to);

    @Transaction
    @Query("SELECT * FROM recipe_table WHERE id = :id_m")
    LiveData<RecipeWithIngredients> getRecipe(int id_m);

    @Transaction
    @Query("SELECT * FROM recipe_table WHERE AuthorId = :user_id")
    LiveData<List<RecipeWithIngredients>> getUserRecipes(int user_id);

    @Query("UPDATE recipe_table SET Views = Views + 1 WHERE id = :recipe_id")
    void updateViewsOnRecipe(int recipe_id);



     //Ingredients
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertIngredient(IngredientEntity ingredientEntity);

    @Query("SELECT * FROM ingredient_table WHERE RecipeId = :recipe_id")
    LiveData<List<IngredientWithProduct>> getIngredients(int recipe_id);



    //Products
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(ProductEntity productEntity);

    @Query("SELECT * FROM product_table")
    LiveData<List<ProductEntity>> getAllProducts();



    //Comments
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertComment(CommentEntity commentEntity);

    @Query("SELECT * FROM comment_table WHERE RecipeId = :id_m")
    LiveData<List<CommentEntity>> getRecipeComments(int id_m);



    //User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(UserEntity userEntity);

    @Query("SELECT * FROM user_table WHERE id = :user_id")
    LiveData<UserEntity> getUser(int user_id);

    @Query("SELECT  user_table.* FROM user_table INNER JOIN recipe_table ON user_table.id = recipe_table.AuthorId WHERE recipe_table.id = :recipe_id")
    LiveData<UserEntity> getUserByRecipeId(int recipe_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserSavedRecipesIds(UserSavedRecipes userSavedRecipes);

    @Query("DELETE FROM user_saved_recipes_table WHERE user_id = :user_id AND recipe_id = :recipe_id ")
    void deleteUserSavedRecipesIds(int user_id, int recipe_id);

    @Query("SELECT recipe_id FROM user_saved_recipes_table WHERE user_id = :user_id")
    LiveData<List<Integer>> getUserSavedRecipesIds(int user_id);

    @Query("SELECT COUNT(*) FROM user_saved_recipes_table WHERE recipe_id = :recipe_id")
    LiveData<Integer> getSavedCount(int recipe_id);




}
