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
import ru.example.dishhunt.data.repositories.RecipeRepository;

public class ProfileRecipesViewModel extends AndroidViewModel  {

    private RecipeRepository mRepository;
    private int MyId;

    public ProfileRecipesViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mRepository.getAllRecipes();
        Resources resources = getApplication().getResources();
        MyId = getApplication().getSharedPreferences(resources.getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE).getInt(resources.getString(R.string.my_id), 1);


    }


    public CombinedLiveData getUserRecipes(int user_id) {
        return new CombinedLiveData(mRepository.getUserRecipes(user_id), mRepository.getUserSavedRecipesIds(MyId));
    }
    public void addWishlist(int id) {
        mRepository.insertUserSavedRecipe(id, MyId);
    }

    public void removeWishlist(int id) {
        mRepository.deleteUserSavedRecipe(id, MyId);
    }

    public void insert(Recipe recipe) { mRepository.insertRecipe(recipe); }


    public static class CombinedLiveData extends MediatorLiveData<Pair<List<Recipe>, List<Integer>>> {
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
