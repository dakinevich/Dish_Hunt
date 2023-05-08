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

public class SavedViewModel extends AndroidViewModel  {

    private RecipeRepository mRepository;

    private final LiveData<List<Recipe>> mAllRecipes;
    private final LiveData<List<Integer>> mSavedRecipes;


    public SavedViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
        mSavedRecipes = mRepository.getUserSavedRecipesIds(1);// TODO user id
    }


    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes; }
    // TODO Recipe saved set mb relations
    public CombinedLiveData getSavedRecipes() {
        return new CombinedLiveData(mAllRecipes, mSavedRecipes);

    }
    public void addWishlist(int id) {
        mRepository.insertUserSavedRecipe(id, 1);
    }
    // TODO we
    public void removeWishlist(int id) {
        mRepository.deleteUserSavedRecipe(id, 1);
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
