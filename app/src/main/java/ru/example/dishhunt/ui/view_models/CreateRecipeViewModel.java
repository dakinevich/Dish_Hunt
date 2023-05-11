package ru.example.dishhunt.ui.view_models;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.repositories.RecipeRepository;


public class CreateRecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    public LiveData<List<ProductEntity>> allProducts;
    public Recipe recipe;
    public CreateRecipeViewModel(Application application) {
        super(application);
        recipe = getDefaultRecipe();
        mRepository = new RecipeRepository(application);
        allProducts = mRepository.getAllProducts();
    }

    public void addRecipe() {
        mRepository.insertRecipe(recipe);
        recipe = getDefaultRecipe();
    }
    public Recipe getDefaultRecipe(){
        Resources resources = getApplication().getResources();
        int my_id = getApplication().getSharedPreferences(resources.getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE).getInt(resources.getString(R.string.my_id), 1);
        return new Recipe(0,false,my_id,0,0,0,1,0,1,0,0,0,0,"", ""+R.drawable.sample_food_1,"", new ArrayList<>(), "");
    }
}
