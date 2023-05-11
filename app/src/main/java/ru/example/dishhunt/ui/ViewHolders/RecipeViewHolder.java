package ru.example.dishhunt.ui.ViewHolders;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.ui.home.RecipeClickInterface;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private final TextView recipeViewTitle;
    private final TextView recipeViewPrice;
    private final TextView recipeViewPortions;
    private final TextView recipeViewCookTime;
    private final ImageView recipeViewImg;
    private final ImageView recipeSaveBtn;
    private final RecipeClickInterface recipeClickInterface;


    RecipeViewHolder(View itemView, RecipeClickInterface recipeClickInterface) {
        super(itemView);
        recipeViewTitle = itemView.findViewById(R.id.recipe_preview_title);
        recipeViewPrice = itemView.findViewById(R.id.recipe_preview_price);
        recipeViewPortions = itemView.findViewById(R.id.recipe_preview_portions);
        recipeViewCookTime = itemView.findViewById(R.id.recipe_preview_cook_time);
        recipeViewImg = itemView.findViewById(R.id.recipe_preview_img);
        recipeSaveBtn = itemView.findViewById(R.id.recipe_preview_save_btn);
        this.recipeClickInterface = recipeClickInterface;

    }


    @SuppressLint("SetTextI18n")
    public void bind(Recipe recipe) {

        recipeSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipeClickInterface != null){
                    recipeClickInterface.onSaveClick(recipe.getId(), recipe.ismIsSaved());
                    recipe.setmIsSaved(!recipe.ismIsSaved());
                    recipeSaveBtn.setImageResource(recipe.ismIsSaved()?R.drawable.bookmark_filled:R.drawable.bookmark);
                }
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipeClickInterface != null){
                    recipeClickInterface.onCardClick(recipe.getId());
                }
            }
        });

        recipeSaveBtn.setImageResource(recipe.ismIsSaved()?R.drawable.bookmark_filled:R.drawable.bookmark);
        recipeViewTitle.setText(recipe.getmTitle());
        recipeViewPrice.setText(""+recipe.getmPrice());
        recipeViewPortions.setText(""+recipe.getmPortions());
        recipeViewImg.setImageResource(Integer.parseInt(recipe.getmImgSrc()));
        int cook_time = recipe.getmCookTime();
        if (cook_time>=60){
            recipeViewCookTime.setText(cook_time/60+"ч " + cook_time%60+"м");
        }
        else{
            recipeViewCookTime.setText(cook_time%60+"м");
        }

    }

    public static RecipeViewHolder create(ViewGroup parent, RecipeClickInterface recipeClickInterface) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_preview, parent, false);
        return new RecipeViewHolder(view, recipeClickInterface);
    }
}
