package ru.example.dishhunt.ui.adapters;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.example.dishhunt.data.models.Recipe;

public class RecipeListAdapter extends ListAdapter<Recipe, RecipeViewHolder> {

    public RecipeListAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback) {
        super(diffCallback);
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecipeViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        Recipe current = getItem(position);
        holder.bind(current.getTitle());
    }

    public static class RecipeDiff extends DiffUtil.ItemCallback<Recipe> {

        @Override
        public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
}
