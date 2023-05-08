package ru.example.dishhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;


import ru.example.dishhunt.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApplicationContext().deleteDatabase("recipe_database");

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
            if(navDestination.getId() == R.id.recipeCardFragment) {

                binding.bottomNavMenu.setVisibility(View.GONE);
            } else {
                binding.bottomNavMenu.setVisibility(View.VISIBLE);

            }
        });
    }


}