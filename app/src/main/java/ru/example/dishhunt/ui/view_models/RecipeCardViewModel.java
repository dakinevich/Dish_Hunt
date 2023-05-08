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


public class RecipeCardViewModel extends AndroidViewModel {
    private RecipeRepository mRepository;
    private final LiveData<List<Integer>> mSavedRecipes;

    public RecipeCardViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mSavedRecipes = mRepository.getUserSavedRecipesIds(1);// TODO user id
    }

    public LiveData<List<Recipe>> getAllRecipes() { return mRepository.getAllRecipes(); }
    public CombinedLiveData getRecipeById(int id) {
        return new CombinedLiveData(mRepository.getRecipeById(id), mSavedRecipes);}

    public void addWishlist(int id) {
        mRepository.insertUserSavedRecipe(id, 1);
    }
    // TODO we
    public void removeWishlist(int id) {
        mRepository.deleteUserSavedRecipe(id, 1);
    }


    public void insert(Recipe recipe) { mRepository.insertRecipe(recipe); }

    public static class CombinedLiveData extends MediatorLiveData<Pair<Recipe, List<Integer>>> {
        private Recipe recipe;
        private List<Integer> savedIds = Collections.emptyList();

        public CombinedLiveData(LiveData<Recipe> ld1, LiveData<List<Integer>> ld2) {
            setValue(Pair.create(recipe, savedIds));

            addSource(ld1, recipe -> {
                if(recipe != null) {
                    this.recipe = recipe;
                }
                setValue(Pair.create(recipe, savedIds));
            });

            addSource(ld2, savedIds -> {
                if(savedIds != null) {
                    this.savedIds = savedIds;
                }
                setValue(Pair.create(recipe, savedIds));
            });
        }
    }
}