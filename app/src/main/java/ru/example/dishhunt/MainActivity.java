package ru.example.dishhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import ru.example.dishhunt.databinding.ActivityMainBinding;
import ru.example.dishhunt.ui.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.view_models.RecipeViewModel;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //BottomNavigationView navView = findViewById(R.id.bottom_nav_menu);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.bottomNavMenu, navController);


    }


}