package ru.example.dishhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.navigation.NavigationBarView;

import ru.example.dishhunt.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       getApplicationContext().deleteDatabase("recipe_database");

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE);
        if (sharedPref.getInt(getString(R.string.my_id),0) == 0){
            SharedPreferences.Editor editor = sharedPref.edit();
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


}