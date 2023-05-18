package ru.example.dishhunt.ui.recipe_creator;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.ui.recipe_creator.adapters.CreateIngredientsAdapter;
import ru.example.dishhunt.ui.recipe_creator.view_models.CreateRecipeViewModel;
import ru.example.dishhunt.databinding.CreateRecipeBinding;


public class CreateRecipeFragment extends Fragment implements IngredientClickInterface{
    private CreateRecipeBinding binding;
    private CreateRecipeViewModel mCreateRecipeViewModel;
    private CreateIngredientsAdapter Adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = CreateRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mCreateRecipeViewModel = new ViewModelProvider(requireActivity()).get(CreateRecipeViewModel.class);


        SetupBinding();

        RecyclerView ingredient_recyclerView = view.findViewById(R.id.create_ingredient_recycler_view);
        Adapter = new CreateIngredientsAdapter(new CreateIngredientsAdapter.RecipeDiff(), this);
        ingredient_recyclerView.setAdapter(Adapter);
        RecyclerView.LayoutManager ingredientLayoutManager = new GridLayoutManager(view.getContext(), 1);
        ingredient_recyclerView.setLayoutManager(ingredientLayoutManager);

        SetupViewModelRecipe();
        SetupAdapter();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void SetupViewModelRecipe(){
        Recipe loc_recipe = mCreateRecipeViewModel.recipe;
        binding.createTitle.setText(loc_recipe.getmTitle().toString());
        binding.createDescription.setText(loc_recipe.getmDescription().toString());
        binding.createCookTime.setText(loc_recipe.getmCookTime()+"");
        binding.createPortions.setText(loc_recipe.getmPortions()+"");
        binding.createIngredientDescription.setText(loc_recipe.getmIngredientsDescription().toString());
        binding.createHat1.setImageResource(R.drawable.hat_fill);
        binding.createHat2.setImageResource(loc_recipe.getmCookComplexity()>1?R.drawable.hat_fill:R.drawable.hat_empty);
        binding.createHat3.setImageResource(loc_recipe.getmCookComplexity()>2?R.drawable.hat_fill:R.drawable.hat_empty);


    }
    private void SetupAdapter(){
        Adapter.submitList(mCreateRecipeViewModel.recipe.getmIngredients());
    }

    private void SetupBinding(){
        binding.createHat1.setOnClickListener(v -> {
            mCreateRecipeViewModel.recipe.setmCookComplexity(1);
            binding.createHat1.setImageResource(R.drawable.hat_fill);
            binding.createHat2.setImageResource(R.drawable.hat_empty);
            binding.createHat3.setImageResource(R.drawable.hat_empty);

        });
        binding.createHat2.setOnClickListener(v -> {
            mCreateRecipeViewModel.recipe.setmCookComplexity(2);
            binding.createHat1.setImageResource(R.drawable.hat_fill);
            binding.createHat2.setImageResource(R.drawable.hat_fill);
            binding.createHat3.setImageResource(R.drawable.hat_empty);
        });
        binding.createHat3.setOnClickListener(v -> {
            mCreateRecipeViewModel.recipe.setmCookComplexity(3);
            binding.createHat1.setImageResource(R.drawable.hat_fill);
            binding.createHat2.setImageResource(R.drawable.hat_fill);
            binding.createHat3.setImageResource(R.drawable.hat_fill);
        });
        binding.createBackBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
        binding.createDoneBtn.setOnClickListener(view_f -> {
            CollectInfo();
            mCreateRecipeViewModel.addRecipe();
            NavHostFragment.findNavController(this).navigateUp();
        });
        binding.createTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                mCreateRecipeViewModel.recipe.setmTitle(binding.createTitle.getText().toString());

            }
        });

        binding.createDescription.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                mCreateRecipeViewModel.recipe.setmDescription(binding.createDescription.getText().toString());

            }
        });
        binding.createCookTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                String view_text = binding.createCookTime.getText().toString();
                mCreateRecipeViewModel.recipe.setmCookTime(view_text.equals("")?0:
                        Integer.parseInt(
                                view_text));

            }
        });

        binding.createPortions.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                String view_text = binding.createPortions.getText().toString();
                mCreateRecipeViewModel.recipe.setmPortions(view_text.equals("")?0:
                        Integer.parseInt(
                                view_text));
            }
        });

        binding.createAddIngredient.setOnClickListener(v -> {
            NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_createRecipeFragment_to_searchProductsFragment);

        });
    }

    void CollectInfo(){
        Recipe loc_recipe = mCreateRecipeViewModel.recipe;
        loc_recipe.setmTitle(binding.createTitle.getText().toString());
        loc_recipe.setmDescription(binding.createDescription.getText().toString());
        loc_recipe.setmCookTime(Integer.parseInt(binding.createCookTime.getText().toString()));
        loc_recipe.setmPortions(Integer.parseInt(binding.createPortions.getText().toString()));
        loc_recipe.setmIngredientsDescription(binding.createIngredientDescription.getText().toString());

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onMinusClick(Ingredient ingredient) {
        mCreateRecipeViewModel.recipe.deleteIngredient(ingredient);
        SetupAdapter();
        Adapter.notifyDataSetChanged();

    }

    @Override
    public void onEdit(Ingredient ingredient, int value) {
        mCreateRecipeViewModel.recipe.editIngredientAmount(ingredient, value);
        SetupAdapter();
    }
}