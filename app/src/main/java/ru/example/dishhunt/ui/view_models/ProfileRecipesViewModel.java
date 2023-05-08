package ru.example.dishhunt.ui.view_models;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Collections;
import java.util.List;

import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.repositories.RecipeRepository;

public class ProfileRecipesViewModel extends AndroidViewModel  {

    private RecipeRepository mRepository;

    private final LiveData<List<Recipe>> mUserRecipes;


    public ProfileRecipesViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mUserRecipes = mRepository.getUserRecipes(1);//TODO user id
    }



    // TODO Recipe saved set mb relations
    public LiveData<List<Recipe>> getUserRecipes() {
        return mUserRecipes;

    }
    public void addWishlist(int id) {
        mRepository.insertUserSavedRecipe(id, 1);
    }
    // TODO we
    public void removeWishlist(int id) {
        mRepository.deleteUserSavedRecipe(id, 1);
    }



    public void insert(Recipe recipe) { mRepository.insertRecipe(recipe); }


}
