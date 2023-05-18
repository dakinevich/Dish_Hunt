package ru.example.dishhunt.ui.recipe_creator.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.ui.recipe_creator.view_holders.CreateIngredientsViewHolder;
import ru.example.dishhunt.ui.recipe_creator.IngredientClickInterface;


public class CreateIngredientsAdapter extends ListAdapter<Ingredient, CreateIngredientsViewHolder> {

    IngredientClickInterface ingredientClickInterface;

    public CreateIngredientsAdapter(@NonNull DiffUtil.ItemCallback<Ingredient> diffCallback, IngredientClickInterface ingredientClickInterface) {
        super(diffCallback);
        this.ingredientClickInterface = ingredientClickInterface;
    }

    @Override
    public CreateIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CreateIngredientsViewHolder.create(parent, ingredientClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateIngredientsViewHolder holder, int position) {
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

