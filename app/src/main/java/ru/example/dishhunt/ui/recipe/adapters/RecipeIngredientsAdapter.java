package ru.example.dishhunt.ui.recipe.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.ui.recipe.view_holders.RecipeIngredientsViewHolder;


public class RecipeIngredientsAdapter extends ListAdapter<Ingredient, RecipeIngredientsViewHolder> {


    public RecipeIngredientsAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback) {
        super(diffCallback);
    }

    @Override
    public RecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecipeIngredientsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsViewHolder holder, int position) {
        Ingredient current = getItem(position);
        holder.bind(current);
    }

    public static class RecipeDiff extends DiffUtil.ItemCallback<Ingredient> {

        @Override
        public boolean areItemsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ingredient oldItem, @NonNull Ingredient newItem) {
            return true;
            //TODO df
        }
    }
}

