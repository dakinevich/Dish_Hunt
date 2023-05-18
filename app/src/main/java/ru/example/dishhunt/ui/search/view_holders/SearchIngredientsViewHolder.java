package ru.example.dishhunt.ui.search.view_holders;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.ui.search.RecipeClickInterface;
import ru.example.dishhunt.ui.search.SearchIngredientClickInterface;

public class SearchIngredientsViewHolder extends RecyclerView.ViewHolder {
    private final TextView ingredientName;
    private final SearchIngredientClickInterface searchIngredientClickInterface;
    private String mode;

    SearchIngredientsViewHolder(View itemView, SearchIngredientClickInterface searchIngredientClickInterface, String mode) {
        super(itemView);
        ingredientName = itemView.findViewById(R.id.search_ingredient_name);
        this.searchIngredientClickInterface = searchIngredientClickInterface;
        this.mode = mode;
    }


    @SuppressLint("SetTextI18n")
    public void bind(ProductEntity product) {

        ingredientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchIngredientClickInterface != null){
                    searchIngredientClickInterface.onClick(product);
                }
            }
        });
        if(Objects.equals(mode, "add")){
            ingredientName.setBackgroundResource(R.drawable.rounded_corner_positive_10);
        }
        else{
            ingredientName.setBackgroundResource(R.drawable.rounded_corner_negative_10);
        }
        ingredientName.setText(product.getName());

    }

    public static SearchIngredientsViewHolder create(ViewGroup parent, SearchIngredientClickInterface searchIngredientClickInterface, String mode) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_ingredient, parent, false);
        return new SearchIngredientsViewHolder(view, searchIngredientClickInterface, mode);
    }
}
