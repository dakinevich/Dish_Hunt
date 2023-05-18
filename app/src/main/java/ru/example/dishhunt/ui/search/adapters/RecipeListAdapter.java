package ru.example.dishhunt.ui.search.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.ui.search.view_holders.RecipeViewHolder;
import ru.example.dishhunt.ui.search.RecipeClickInterface;

public class RecipeListAdapter extends ListAdapter<Recipe, RecipeViewHolder>{

    private final RecipeClickInterface recipeClickInterface;

    public RecipeListAdapter(@NonNull DiffUtil.ItemCallback<Recipe> diffCallback, RecipeClickInterface recipeClickInterface) {
        super(diffCallback);
        this.recipeClickInterface = recipeClickInterface;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecipeViewHolder.create(parent, recipeClickInterface);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe current = getItem(position);
        holder.bind(current);

    }


    public static class RecipeDiff extends DiffUtil.ItemCallback<Recipe> {

        @Override
        public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getmTitle().equals(newItem.getmTitle());
        }
    }
}
