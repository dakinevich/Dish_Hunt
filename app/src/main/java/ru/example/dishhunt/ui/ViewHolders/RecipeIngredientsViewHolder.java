package ru.example.dishhunt.ui.ViewHolders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Ingredient;

public class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {

    private TextView ingredientViewName;
    private TextView ingredientViewAmount;
    private TextView ingredientViewAmountType;


    private RecipeIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredientViewName =itemView.findViewById(R.id.ingredient_name);
        ingredientViewAmount =itemView.findViewById(R.id.ingredient_amount);
        ingredientViewAmountType =itemView.findViewById(R.id.ingredient_amount_type);

    }


    @SuppressLint("SetTextI18n")
    public void bind(Ingredient ingredient) {
        ingredientViewName.setText(ingredient.getName());
        ingredientViewAmount.setText(""+ingredient.getAmount());
        ingredientViewAmountType.setText(" "+ingredient.getProduct().getAmountValue());

    }

    public static RecipeIngredientsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient, parent, false);
        return new RecipeIngredientsViewHolder(view);
    }
}

