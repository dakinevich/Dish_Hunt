package ru.example.dishhunt.ui.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.repositories.RecipeRepository;


public class RecipeCardViewModel extends AndroidViewModel {
    private RecipeRepository mRepository;



    public RecipeCardViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);

    }

    public LiveData<List<Recipe>> getAllRecipes() { return mRepository.getAllRecipes(); }
    public LiveData<Recipe> getRecipeById(int id) { return mRepository.getRecipeById(id); }

    public void insert(Recipe recipe) { mRepository.insert(recipe); }
}