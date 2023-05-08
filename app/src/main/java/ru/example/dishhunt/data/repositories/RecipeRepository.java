package ru.example.dishhunt.data.repositories;



import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ru.example.dishhunt.data.data_sources.room.dao.RecipeDao;
import ru.example.dishhunt.data.data_sources.room.entites.CommentEntity;
import ru.example.dishhunt.data.data_sources.room.entites.IngredientEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserSavedRecipes;
import ru.example.dishhunt.data.data_sources.room.root.RecipeRoomDatabase;
import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.data.models.Recipe;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<RecipeEntity>> mAllRecipes;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public RecipeRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        mRecipeDao = db.recipeDao();
        mAllRecipes = mRecipeDao.getAlphabetizedRecipes();


    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Recipe>> getAllRecipes() {
        return Transformations.map(
                mAllRecipes,
                (values) -> values.stream().map(RecipeEntity::toDomainModel).collect(Collectors.toList())
        );
    }

    public LiveData<Recipe> getRecipeById(int recipeId) {
        return Transformations.map(
                mRecipeDao.getRecipe(recipeId),
                (values) -> values.toDomainModel());
    }
    public LiveData<List<Comment>> getRecipeCommentsById(int recipeId) {
        return Transformations.map(
                mRecipeDao.getRecipeComments(recipeId),
                (values) -> values.stream().map(CommentEntity::toDomainModel).collect(Collectors.toList()));

    }
    public LiveData<List<Recipe>> searchRecipes(int time_from, int time_to) {
        return Transformations.map(
                mRecipeDao.getSearchRecipes(time_from, time_to),
                (values) -> values.stream().map(RecipeEntity::toDomainModel)
                        .collect(Collectors.toList())
        );
    }

    public LiveData<List<Recipe>> getUserRecipes(int user_id) {
        return Transformations.map(
                mRecipeDao.getUserRecipes(user_id),
                (values) -> values.stream().map(RecipeEntity::toDomainModel)
                        .collect(Collectors.toList())
        );
    }

    public LiveData<List<Integer>> getUserSavedRecipesIds(int user_id) {
        return mRecipeDao.getUserSavedRecipesIds(user_id);
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertRecipe(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity(recipe.getmTitle(), recipe.getmDescription(), recipe.getmCookTime(), recipe.getmAuthorId(),recipe.getmViews(), recipe.getmLikes(), recipe.getmCookComplexity(),recipe.getmPortions(), recipe.getmImgSrc());
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRecipeDao.insertRecipe(recipeEntity);
        });
    }
    public void insertComment(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(comment.getRecipeId(), comment.getAuthorId(), comment.getText(), comment.getPubTime());
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRecipeDao.insertComment(commentEntity);
        });
    }

    public void insertUserSavedRecipe(int recipe_id, int user_id) {
        UserSavedRecipes userSavedRecipes = new UserSavedRecipes(recipe_id, user_id);
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRecipeDao.insertUserSavedRecipesIds(userSavedRecipes);
        });
    }
    public void deleteUserSavedRecipe(int recipe_id, int user_id) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRecipeDao.deleteUserSavedRecipesIds(user_id, recipe_id);
        });
    }

}