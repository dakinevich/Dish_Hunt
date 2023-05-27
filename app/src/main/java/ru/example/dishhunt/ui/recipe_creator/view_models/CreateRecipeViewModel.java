package ru.example.dishhunt.ui.recipe_creator.view_models;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.ImageStorage;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.Slide;
import ru.example.dishhunt.data.repositories.RecipeRepository;


public class CreateRecipeViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    public LiveData<List<ProductEntity>> allProducts;
    public Recipe recipe;
    public Bitmap recipe_img;
    public List<Bitmap> slides_imgs;

    public CreateRecipeViewModel(Application application) {
        super(application);
        recipe = getDefaultRecipe();
        mRepository = new RecipeRepository(application);
        allProducts = mRepository.getAllProducts();
        slides_imgs = new ArrayList<>();

    }

    public void addRecipe() {

        for(int i =0; i<slides_imgs.size(); i++){
            String src = ImageStorage.putImage(slides_imgs.get(i), getApplication().getApplicationContext());
            recipe.mSlides.get(i).setImgSrc(src);
        }
        String img_name = ImageStorage.putImage(recipe_img, getApplication().getApplicationContext());
        recipe.setmImgSrc(img_name);
        mRepository.insertRecipe(recipe);
        recipe_img = null;
        slides_imgs = new ArrayList<>();
        recipe = getDefaultRecipe();
    }
    public LiveData<List<ProductEntity>> search_products(String re){
        return mRepository.searchProducts(re);
    }
    public Recipe getDefaultRecipe(){
        Resources resources = getApplication().getResources();
        int my_id = getApplication().getSharedPreferences(resources.getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE).getInt(resources.getString(R.string.my_id), 1);

        return new Recipe(0,false,my_id,0,0,1,30,1,"", ""+R.drawable.sample_food_1,"", new ArrayList<>(), new ArrayList<>(), "");
    }
}
