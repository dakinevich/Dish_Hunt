package ru.example.dishhunt.ui.adapters;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;

class RecipeViewHolder extends RecyclerView.ViewHolder {
    private final TextView recipeViewTitle;
    private final TextView recipeViewPrice;
    private final TextView recipeViewPortions;
    private final TextView recipeViewCookTime;




    private RecipeViewHolder(View itemView) {
        super(itemView);
        recipeViewTitle = itemView.findViewById(R.id.recipe_preview_title);
        recipeViewPrice = itemView.findViewById(R.id.recipe_preview_price);
        recipeViewPortions = itemView.findViewById(R.id.recipe_preview_portions);
        recipeViewCookTime = itemView.findViewById(R.id.recipe_preview_cook_time);

    }


    @SuppressLint("SetTextI18n")
    public void bind(Recipe recipe) {
        itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("recipe_id", recipe.getId());
            NavHostFragment.findNavController(FragmentManager.findFragment(v)).navigate(R.id.action_resultsFragment_to_recipeCardFragment, bundle);
        });
        recipeViewTitle.setText(recipe.getmTitle());
        recipeViewPrice.setText(""+recipe.getmPrice());
        recipeViewPortions.setText(""+recipe.getmPortions());
        int cook_time = recipe.getmCookTime();
        if (cook_time>=60){
            recipeViewCookTime.setText(cook_time/60+"ч " + cook_time%60+"м");
        }
        else{
            recipeViewCookTime.setText(cook_time%60+"м");
        }

    }

    static RecipeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_preview, parent, false);
        return new RecipeViewHolder(view);
    }
}
