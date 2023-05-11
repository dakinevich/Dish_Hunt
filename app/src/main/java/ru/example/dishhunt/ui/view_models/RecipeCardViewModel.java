package ru.example.dishhunt.ui.view_models;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Collections;
import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.User;
import ru.example.dishhunt.data.repositories.RecipeRepository;


public class RecipeCardViewModel extends AndroidViewModel {
    private RecipeRepository mRepository;
    private final LiveData<List<Integer>> mSavedRecipes;
    private int MyId;

    public RecipeCardViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        Resources resources = getApplication().getResources();
        MyId = getApplication().getSharedPreferences(resources.getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE).getInt(resources.getString(R.string.my_id), 1);
        mSavedRecipes = mRepository.getUserSavedRecipesIds(MyId);
    }

    public LiveData<List<Recipe>> getAllRecipes() { return mRepository.getAllRecipes(); }
    public CombinedLiveData getRecipeById(int id) {
        return new CombinedLiveData(mRepository.getRecipeById(id), mSavedRecipes);}
    public LiveData<User> getAuthor(int recipe_id){
        return mRepository.getRecipeAuthor(recipe_id);
    }

    public void addWishlist(int id) {
        mRepository.insertUserSavedRecipe(id, MyId);
    }

    public void removeWishlist(int id) {
        mRepository.deleteUserSavedRecipe(id, MyId);
    }

    public void recipeChecked(int recipe_id){
        mRepository.updateViewsOnRecipe(recipe_id);
    }

    public LiveData<Integer> getSavedCount(int recipe_id) {
        return mRepository.getSavedCount(recipe_id);
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