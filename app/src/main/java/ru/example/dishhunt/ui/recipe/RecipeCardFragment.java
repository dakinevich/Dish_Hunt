package ru.example.dishhunt.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import ru.example.dishhunt.databinding.HomeBinding;
import ru.example.dishhunt.databinding.RecipeCardBinding;
import ru.example.dishhunt.ui.adapters.RecipePageAdapter;

public class RecipeCardFragment extends Fragment {
    private RecipeCardBinding binding;
    private ViewPager2 viewPager2;
    private RecipePageAdapter pageAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = RecipeCardBinding.inflate(inflater, container, false);
        pageAdapter = new RecipePageAdapter(this);
        viewPager2 = binding.recipeCardViewpager;
        viewPager2.setAdapter(pageAdapter);

        return binding.getRoot();
    }
}
