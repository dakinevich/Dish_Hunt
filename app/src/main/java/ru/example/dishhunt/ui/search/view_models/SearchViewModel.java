package ru.example.dishhunt.ui.search.view_models;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.repositories.RecipeRepository;
import ru.example.dishhunt.ui.CombinedLiveData;

public class SearchViewModel extends AndroidViewModel {
    private RecipeRepository mRepository;

    private final LiveData<List<Recipe>> mAllRecipes;
    private final LiveData<List<Integer>> mSavedRecipes;
    private int MyId;
    public List<ProductEntity> includeProducts =new ArrayList<>();
    public List<ProductEntity> excludeProducts=new ArrayList<>();



    public SearchViewModel(Application application) {
        super(application);
        Resources resources = getApplication().getResources();
        MyId = getApplication().getSharedPreferences(resources.getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE).getInt(resources.getString(R.string.my_id), 1);
        mRepository = new RecipeRepository(application);
        mAllRecipes = mRepository.getAllRecipes();
        mSavedRecipes = mRepository.getUserSavedRecipesIds(MyId);
        includeProducts = new ArrayList<>();
        excludeProducts = new ArrayList<>();

    }
    public LiveData<List<ProductEntity>> search_products(String re){
        return mRepository.searchProducts(re);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mAllRecipes; }

    public CombinedLiveData searchRecipes(int time_from, int time_to, int portions_from, int portions_to, String re) {
        return new CombinedLiveData(mRepository.searchRecipes(time_from, time_to, portions_from, portions_to, re), mSavedRecipes);

    }
    public void addWishlist(int id) {
        mRepository.insertUserSavedRecipe(id, MyId);
    }

    public void removeWishlist(int id) {
        mRepository.deleteUserSavedRecipe(id, MyId);
    }

    public void addIncludeProduct(ProductEntity product){
        if(findProduct(excludeProducts, product)!=-1){
            excludeProducts.remove(findProduct(excludeProducts, product));
        }
        if(findProduct(includeProducts, product)!=-1){
            return;
        }
        includeProducts.add(product);

    }
    public void addExcludeProduct(ProductEntity product){
        if(findProduct(includeProducts, product)!=-1){
            includeProducts.remove(findProduct(includeProducts, product));
        }
        if(findProduct(excludeProducts, product)!=-1){
            return;
        }
        excludeProducts.add(product);

    }

    public void removeProduct(ProductEntity product){
        if(findProduct(includeProducts, product)!=-1){
            includeProducts.remove(findProduct(includeProducts, product));
        }
        if(findProduct(excludeProducts, product)!=-1){
            excludeProducts.remove(findProduct(excludeProducts, product));
        }
    }

    private int findProduct(List<ProductEntity> pl, ProductEntity p){
        for (int i = 0; i<pl.size(); i++){
            if (pl.get(i).getName().equals(p.getName())){
                return i;
            }
        }
        return -1;
    }



    public void insert(Recipe recipe) { mRepository.insertRecipe(recipe); }



}
