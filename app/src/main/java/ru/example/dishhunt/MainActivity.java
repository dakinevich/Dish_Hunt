package ru.example.dishhunt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;


import ru.example.dishhunt.data.ImageStorage;
import ru.example.dishhunt.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getApplicationContext().deleteDatabase("recipe_database");
        //ImageStorage.clearStorage(this);
        setupBaseImgs();

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE);

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            str.append(i).append(",");
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.resent_views), str.toString());
        editor.apply();

        if (sharedPref.getInt(getString(R.string.my_id),0) == 0){
            editor = sharedPref.edit();
            editor.putInt(getString(R.string.my_id), 1);
            editor.apply();
        }


        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //BottomNavigationView navView = findViewById(R.id.bottom_nav_menu);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.bottomNavMenu, navController);
        setupBottomNav(navController);


    }

    private void setupBottomNav(NavController navController){
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            /*if(navDestination.getId() == R.id.recipeCardFragment
                    | navDestination.getId() == R.id.searchProductsFragment
                    | navDestination.getId() == R.id.createRecipeFragment) {
                binding.bottomNavMenu.setVisibility(View.GONE);
            } else {
                binding.bottomNavMenu.setVisibility(View.VISIBLE);

            }
            binding.bottomNavMenu.setVisibility(View.VISIBLE);*/
            binding.bottomNavMenu.setOnItemSelectedListener(item -> {
                if (binding.bottomNavMenu.getSelectedItemId() == item.getItemId()){
                    int nav_depth = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).getBackQueue().size();
                    for(int i = 0; i<nav_depth-2; i++){
                        navController.navigateUp();
                    }
                }
                NavigationUI.onNavDestinationSelected(item, navController);
                return true;
            });

        });
    }
    private void setupBaseImgs(){
        int[] imgs = new int[]{R.drawable.sample_food_1,R.drawable.sample_food_2,R.drawable.sample_food_3,R.drawable.sample_food_4,R.drawable.sample_food_5,R.drawable.sample_food_6,R.drawable.sample_food_7,R.drawable.sample_food_8,R.drawable.sample_food_9,R.drawable.sample_food_10};

        for (int i = 0; i<10;i++){
            Bitmap bMap = BitmapFactory.decodeResource(getResources(), imgs[i]);
            String file_name = ImageStorage.putImage(bMap, this);
            Log.e("qwe", file_name+" img saved");
        }
        imgs = new int[]{R.drawable.test_page_1,R.drawable.test_page_2,R.drawable.test_page_3};

        for (int i = 0; i<3;i++){
            Bitmap bMap = BitmapFactory.decodeResource(getResources(), imgs[i]);
            String file_name = ImageStorage.putImage(bMap, this);
            Log.e("qwe", file_name+" img saved");
        }

    }
}