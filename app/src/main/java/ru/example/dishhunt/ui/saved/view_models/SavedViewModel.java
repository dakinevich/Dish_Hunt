package ru.example.dishhunt.ui.saved.view_models;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.User;
import ru.example.dishhunt.data.repositories.RecipeRepository;

public class SavedViewModel extends AndroidViewModel  {

    private RecipeRepository mRepository;

    private final LiveData<List<Recipe>> mAllRecipes;
    private final LiveData<List<Integer>> mSavedRecipes;
    public int MyId;

    public SavedViewModel(Application application) {
        super(application);
        Resources resources = getApplication().getResources();
        MyId = getApplication().getSharedPreferences(resources.getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE).getInt(resources.getString(R.string.my_id), 1);

        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
        mSavedRecipes = mRepository.getUserSavedRecipesIds(MyId);

    }


    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes; }
    public CombinedLiveData getSavedRecipes() {
        Log.e("qwe", "requesting saved");
        return new CombinedLiveData(mAllRecipes, mSavedRecipes);

    }
    public void addWishlist(int id) {
        mRepository.insertUserSavedRecipe(id, MyId);
    }

    public void removeWishlist(int id) {
        mRepository.deleteUserSavedRecipe(id, MyId);
    }



    public void insert(Recipe recipe) { mRepository.insertRecipe(recipe); }

    public class CombinedLiveData extends MediatorLiveData<Pair<List<Recipe>, List<Integer>>> {
        private List<Recipe> recipes = Collections.emptyList();
        private List<Integer> savedIds = Collections.emptyList();

        public CombinedLiveData(LiveData<List<Recipe>> ld1, LiveData<List<Integer>> ld2) {
            setValue(Pair.create(recipes, savedIds));

            addSource(ld1, recipes -> {
                if(recipes != null) {
                    this.recipes = recipes;
                }
                setValue(Pair.create(recipes, savedIds));
            });

            addSource(ld2, savedIds -> {
                if(savedIds != null) {
                    this.savedIds = savedIds;
                }
                setValue(Pair.create(recipes, savedIds));
            });
        }
    }
}
