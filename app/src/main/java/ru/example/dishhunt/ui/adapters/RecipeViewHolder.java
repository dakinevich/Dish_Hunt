package ru.example.dishhunt.ui.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;

class RecipeViewHolder extends RecyclerView.ViewHolder {
    private final TextView recipeItemView;

    private RecipeViewHolder(View itemView) {
        super(itemView);
        recipeItemView = itemView.findViewById(R.id.recipe_preview_title);
    }

    public void bind(String text) {
        recipeItemView.setText(text);
    }

    static RecipeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_preview, parent, false);
        return new RecipeViewHolder(view);
    }
}
