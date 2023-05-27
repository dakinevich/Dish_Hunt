package ru.example.dishhunt.ui.recipe_creator.view_holders;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.ui.recipe_creator.IngredientClickInterface;

public class CreateIngredientsViewHolder extends RecyclerView.ViewHolder {

    private TextView ingredientViewName;
    private EditText ingredientViewAmount;
    private TextView ingredientViewAmountType;
    private ImageView ingredientViewMinus;

    private IngredientClickInterface ingredientClickInterface;


    private CreateIngredientsViewHolder(@NonNull View itemView, IngredientClickInterface ingredientClickInterface) {
        super(itemView);
        this.ingredientClickInterface = ingredientClickInterface;
        ingredientViewName = itemView.findViewById(R.id.create_ingredient_name);
        ingredientViewAmount = itemView.findViewById(R.id.create_ingredient_amount);
        ingredientViewAmountType = itemView.findViewById(R.id.create_ingredient_amount_type);
        ingredientViewMinus = itemView.findViewById(R.id.create_ingredient_minus);
    }


    @SuppressLint("SetTextI18n")
    public void bind(Ingredient ingredient) {
        ingredientViewName.setText(ingredient.getName());
        ingredientViewAmount.setText(""+ingredient.getAmount());
        ingredientViewAmountType.setText(" "+ingredient.getProduct().getAmountValue());
        ingredientViewMinus.setOnClickListener(v -> {
            ingredientClickInterface.onMinusClick(ingredient);
        });
        ingredientViewAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientClickInterface.onEdit(ingredient, Integer.parseInt("0"+ingredientViewAmount.getText()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static CreateIngredientsViewHolder create(ViewGroup parent, IngredientClickInterface ingredientClickInterface) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.creare_ingredient, parent, false);
        return new CreateIngredientsViewHolder(view, ingredientClickInterface);
    }
}

