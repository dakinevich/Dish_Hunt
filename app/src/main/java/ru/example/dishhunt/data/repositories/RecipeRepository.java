package ru.example.dishhunt.data.repositories;



import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.example.dishhunt.data.data_sources.room.dao.RecipeDao;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;
import ru.example.dishhunt.data.data_sources.room.root.RecipeRoomDatabase;
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
                mRecipeDao.getAlphabetizedRecipes(),
                (values) -> values.stream().map(RecipeEntity::toDomainModel).collect(Collectors.toList())
        );
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity(recipe.getTitle(),recipe.getCookTime(),recipe.getPortions(),recipe.getImgSrc());
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mRecipeDao.insert(recipeEntity);
        });
    }
}