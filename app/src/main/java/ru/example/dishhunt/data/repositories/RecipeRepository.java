package ru.example.dishhunt.data.repositories;



import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.example.dishhunt.data.data_sources.room.dao.RecipeDao;
import ru.example.dishhunt.data.data_sources.room.entites.CommentEntity;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeWithIngredientsAndSlides;
import ru.example.dishhunt.data.data_sources.room.entites.UserEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserSavedRecipes;
import ru.example.dishhunt.data.data_sources.room.root.RecipeRoomDatabase;
import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.User;

public class RecipeRepository {

    private RecipeDao mRecipeDao;
    private LiveData<List<RecipeWithIngredientsAndSlides>> mAllRecipes;
    private int inf = Integer.MAX_VALUE;

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
                (values) -> values.stream().map(value ->
                        value.recipeEntity.toDomainModel(value.ingredientWithProduct, value.slideEntities))
                        .collect(Collectors.toList())
        );
    }
    public LiveData<List<ProductEntity>> getAllProducts() {
        return  mRecipeDao.getAllProducts();
    }


    public LiveData<Recipe> getRecipeById(int recipeId) {
        return Transformations.map(
                mRecipeDao.getRecipe(recipeId),
                value -> {
                    Log.e("qwe", value.recipeEntity.getTitle());
                    return value.recipeEntity.toDomainModel(value.ingredientWithProduct, value.slideEntities);});
    }

    public LiveData<List<Recipe>> getRecipesByIds(List<Integer> recipeIds) {
        return Transformations.map(
                mRecipeDao.getRecipes(recipeIds),
                (values) -> values.stream().map(value -> value.recipeEntity.
                                toDomainModel(value.ingredientWithProduct, value.slideEntities))
                        .collect(Collectors.toList())
        );

    }

    public LiveData<List<Comment>> getRecipeCommentsById(int recipeId) {
        return Transformations.map(
                mRecipeDao.getRecipeComments(recipeId),
                (values) -> values.stream().map(CommentEntity::toDomainModel).collect(Collectors.toList()));

    }
    public LiveData<List<Recipe>> searchRecipes(int time_from, int time_to, int portions_from, int portions_to, String re) {
        time_from = (time_from<0)?inf:time_from;
        time_to = (time_to<0)?inf:time_to;
        portions_from = (portions_from<0)?inf:portions_from;
        portions_to = (portions_to<0)?inf:portions_to;

        return Transformations.map(
                mRecipeDao.searchRecipes(time_from, time_to, portions_from, portions_to, re),
                (values) -> values.stream().map(value -> {
                    return value.recipeEntity.toDomainModel(value.ingredientWithProduct, value.slideEntities);
                        })
                        .collect(Collectors.toList())
        );
    }
    public LiveData<List<ProductEntity>> searchProducts(String re) {
        return mRecipeDao.searchProducts(re);
    }


    public LiveData<List<Recipe>> getUserRecipes(int user_id) {
        return Transformations.map(
                mRecipeDao.getUserRecipes(user_id),
                (values) -> values.stream().map(value -> {
                            return value.recipeEntity.toDomainModel(value.ingredientWithProduct, value.slideEntities);
                        })
                        .collect(Collectors.toList())
        );
    }
    public LiveData<User> getRecipeAuthor(int recipe_id){
        return Transformations.map(
                mRecipeDao.getUserByRecipeId(recipe_id),
                UserEntity::toDomainModel);
    }
    public LiveData<Integer> getSavedCount(int recipe_id){
        return mRecipeDao.getSavedCount(recipe_id);
    }
    public LiveData<User> getUser(int user_id){
        Log.e("qwe", "requesting user inside repositiory");
        return Transformations.map(
                mRecipeDao.getUser(user_id),
                elem -> {
                    UserEntity e = elem;
                    if(e == null){
                        return new User("ща сек", "момент буквально","", 1);
                    }
                    return e.toDomainModel();
                });
    }
    public void updateViewsOnRecipe(int recipe_id){
        mRecipeDao.updateViewsOnRecipe(recipe_id);
    }
    public LiveData<List<Ingredient>> getIngredients(int recipe_id){
        return Transformations.map(
                mRecipeDao.getIngredients(recipe_id),
                (values) -> values.stream().map(value-> {
                    return value.ingredientEntity
                            .toDomainModel(value.productEntity);
                        })
                        .collect(Collectors.toList())
        );
    }

    public LiveData<List<Integer>> getUserSavedRecipesIds(int user_id) {
        return mRecipeDao.getUserSavedRecipesIds(user_id);
    }



    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertRecipe(Recipe recipe) {
        mRecipeDao.insertRecipeWithIngredientsAndSlides(recipe);
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