package ru.example.dishhunt.ui.search.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.ui.search.RecipeClickInterface;
import ru.example.dishhunt.ui.search.SearchIngredientClickInterface;
import ru.example.dishhunt.ui.search.view_holders.RecipeViewHolder;
import ru.example.dishhunt.ui.search.view_holders.SearchIngredientsViewHolder;

public class SearchIngredientsAdapter extends ListAdapter<ProductEntity, SearchIngredientsViewHolder>{

    private final SearchIngredientClickInterface searchIngredientClickInterface;
    private String mode;
    public SearchIngredientsAdapter(@NonNull DiffUtil.ItemCallback<ProductEntity> diffCallback, SearchIngredientClickInterface searchIngredientClickInterface, String mode) {
        super(diffCallback);
        this.mode = mode;
        this.searchIngredientClickInterface = searchIngredientClickInterface;
    }

    @Override
    public SearchIngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SearchIngredientsViewHolder.create(parent, searchIngredientClickInterface, mode);
    }

    @Override
    public void onBindViewHolder(SearchIngredientsViewHolder holder, int position) {
        ProductEntity current = getItem(position);
        holder.bind(current);

    }


    public static class ProductDiff extends DiffUtil.ItemCallback<ProductEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull ProductEntity oldItem, @NonNull ProductEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductEntity oldItem, @NonNull ProductEntity newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
