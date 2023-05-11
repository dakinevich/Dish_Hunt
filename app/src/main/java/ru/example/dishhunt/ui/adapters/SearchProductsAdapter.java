package ru.example.dishhunt.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.ui.ViewHolders.RecipeIngredientsViewHolder;
import ru.example.dishhunt.ui.ViewHolders.SearchProductsViewHolder;
import ru.example.dishhunt.ui.home.RecipeClickInterface;
import ru.example.dishhunt.ui.recipe_creator.ProductClickInterface;


public class SearchProductsAdapter extends ListAdapter<ProductEntity, SearchProductsViewHolder> {
    private final ProductClickInterface productClickInterface;


    public SearchProductsAdapter(@NonNull DiffUtil.ItemCallback<ProductEntity> diffCallback, ProductClickInterface productClickInterface) {
        super(diffCallback);
        this.productClickInterface = productClickInterface;

    }

    @Override
    public SearchProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SearchProductsViewHolder.create(parent, productClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductsViewHolder holder, int position) {
        ProductEntity current = getItem(position);
        holder.bind(current);
    }

    public static class RecipeDiff extends DiffUtil.ItemCallback<ProductEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull ProductEntity oldItem, @NonNull ProductEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductEntity oldItem, @NonNull ProductEntity newItem) {
            return true;
            //TODO df
        }
    }
}

