package ru.example.dishhunt.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.databinding.HomeBinding;
import ru.example.dishhunt.databinding.RecipeCardBinding;
import ru.example.dishhunt.ui.adapters.RecipePageAdapter;
import ru.example.dishhunt.ui.view_models.RecipeCardViewModel;
import ru.example.dishhunt.ui.view_models.RecipePreviewViewModel;

public class RecipeCardFragment extends Fragment {
    private RecipeCardBinding binding;
    private ViewPager2 viewPager2;
    private RecipePageAdapter pageAdapter;
    private RecipeCardViewModel mRecipeCardViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = RecipeCardBinding.inflate(inflater, container, false);
        pageAdapter = new RecipePageAdapter(this);
        viewPager2 = binding.recipeCardViewpager;
        viewPager2.setAdapter(pageAdapter);



        int recipe_id = 0;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipe_id = bundle.getInt("recipe_id", 0);
        }

        mRecipeCardViewModel = new ViewModelProvider(requireActivity()).get(RecipeCardViewModel.class);
        mRecipeCardViewModel.getRecipeById(recipe_id).observe(requireActivity(), recipe -> {
            SetupViewData(recipe);
        });

        binding.recipeCardBackBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_recipeCardFragment_to_resultsFragment);
        });

        return binding.getRoot();
    }
    public void SetupViewData(Recipe recipe){
        binding.recipeCardTitle.setText(recipe.getmTitle());
        binding.recipeCardDescription.setText(recipe.getmDescription());

    }
}
