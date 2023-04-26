package ru.example.dishhunt.ui.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.repositories.RecipeRepository;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeRepository mRepository;

    private final LiveData<List<Recipe>> mAllRecipes;

    public RecipeViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() { return mAllRecipes; }

    public void insert(Recipe recipe) { mRepository.insert(recipe); }
}
