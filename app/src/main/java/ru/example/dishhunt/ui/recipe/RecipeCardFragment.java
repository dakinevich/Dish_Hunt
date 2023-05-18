package ru.example.dishhunt.ui.recipe;

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
import androidx.viewpager2.widget.ViewPager2;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.User;
import ru.example.dishhunt.databinding.RecipeCardBinding;
import ru.example.dishhunt.ui.recipe.adapters.RecipeCommentsAdapter;
import ru.example.dishhunt.ui.recipe.adapters.RecipeIngredientsAdapter;
import ru.example.dishhunt.ui.recipe.adapters.RecipePageAdapter;
import ru.example.dishhunt.ui.recipe.view_models.RecipeCardViewModel;
import ru.example.dishhunt.ui.recipe.view_models.RecipeCommentsViewModel;

public class RecipeCardFragment extends Fragment implements CommentClickInterface{
    private RecipeCardBinding binding;
    private ViewPager2 viewPager2;
    private RecipePageAdapter pageAdapter;
    private RecipeCardViewModel mRecipeCardViewModel;
    private RecipeCommentsViewModel mRecipeCommentsViewModel;
    private int recipeId;
    private Recipe recipe;
    private User author;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = RecipeCardBinding.inflate(inflater, container, false);
        pageAdapter = new RecipePageAdapter(this);
        viewPager2 = binding.recipeCardViewpager;
        viewPager2.setAdapter(pageAdapter);
        View view = binding.getRoot();
        recipe = new Recipe();



        recipeId = 0;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipeId = bundle.getInt("recipe_id", 0);
        }

        mRecipeCardViewModel = new ViewModelProvider(requireActivity()).get(RecipeCardViewModel.class);


        RecyclerView comments_recyclerView = view.findViewById(R.id.comments_recyclerview);
        final RecipeCommentsAdapter comments_adapter = new RecipeCommentsAdapter(new RecipeCommentsAdapter.RecipeDiff(), this);
        comments_recyclerView.setAdapter(comments_adapter);
        RecyclerView.LayoutManager commentsLayoutManager = new GridLayoutManager(view.getContext(), 1);
        comments_recyclerView.setLayoutManager(commentsLayoutManager);
        //so wet
        RecyclerView ingredient_recyclerView = view.findViewById(R.id.recipe_ingredient_container);
        final RecipeIngredientsAdapter ingredient_adapter = new RecipeIngredientsAdapter(new RecipeIngredientsAdapter.RecipeDiff());
        ingredient_recyclerView.setAdapter(ingredient_adapter);
        RecyclerView.LayoutManager ingredientLayoutManager = new GridLayoutManager(view.getContext(), 1);
        ingredient_recyclerView.setLayoutManager(ingredientLayoutManager);

        //ViewModel
        mRecipeCommentsViewModel = new ViewModelProvider(requireActivity()).get(RecipeCommentsViewModel.class);

        //Observers
        mRecipeCommentsViewModel.getRecipeCommentsById(recipeId).observe(requireActivity(), comments_adapter::submitList);
        mRecipeCardViewModel.getRecipeById(recipeId).observe(requireActivity(), (combo) ->{
            Recipe res = combo.first;
            if (res != null){
                res.setmIsSaved(combo.second.contains(res.getId()));
                recipe = res;
                SetupRecipeViewData(res);

                ingredient_adapter.submitList(recipe.getmIngredients());

            }
        });
        mRecipeCardViewModel.getAuthor(recipeId).observe(requireActivity(), (elem) ->{
            author = elem;
            SetupAuthorViewData(author);
        });
        mRecipeCardViewModel.getSavedCount(recipeId).observe(requireActivity(), (elem) ->{
            binding.recipeCardSaveCount.setText(elem.toString());
        });
        new Thread(() -> mRecipeCardViewModel.recipeChecked(recipeId)).start();

        binding.recipeCardBackBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
        binding.recipeCardSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipe.ismIsSaved()){
                    mRecipeCardViewModel.removeWishlist(recipeId);
                }
                else {
                    mRecipeCardViewModel.addWishlist(recipeId);
                }
            }
        });
        binding.recipeCardAuthorContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", recipe.getmAuthorId());
                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_recipeCardFragment_to_profile, bundle);
            }
        });
        binding.recipeMyComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.recipeAddComment.setEnabled(hasFocus);
                if (hasFocus){
                    binding.recipeMyComment.setHint("");
                }
                else{
                    binding.recipeMyComment.setHint(R.string.comment_hint);
                }

            }
        });
        binding.recipeAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = binding.recipeMyComment.getText().toString();
                Comment comment = new Comment(recipeId, 1, commentText, 0);
                mRecipeCommentsViewModel.addComment(comment);
                binding.recipeMyComment.setText("");
                binding.recipeMyComment.clearFocus();
            }
        });

        return view;
    }
    @SuppressLint("SetTextI18n")
    public void SetupRecipeViewData(Recipe recipe){
        binding.recipeCardSaveBtn.setImageResource(recipe.ismIsSaved()?R.drawable.bookmark_filled:R.drawable.bookmark);
        binding.recipeCardTitle.setText(recipe.getmTitle());
        binding.recipeCardDescription.setText(recipe.getmDescription());
        binding.recipeCardImg.setImageResource(Integer.parseInt(recipe.getmImgSrc()));
        int total_weight = 0;
        for(Ingredient elem: recipe.getmIngredients()){
            total_weight+=elem.getWeight();
        }
        binding.ingredientsTotalWeight.setText(total_weight+" г");
        binding.recipeCardViews.setText(""+recipe.getmViews());
        if (recipe.getmCookTime()>59){
            binding.recipeCardCookTime.setText(recipe.getmCookTime()/60+"ч "+recipe.getmCookTime()%60+"минут");
        }
        else{
            binding.recipeCardCookTime.setText(recipe.getmCookTime()+"минут");

        }
        binding.recipeCardDescription.setText(recipe.getmDescription());
        binding.recipeCardPortions.setText(""+recipe.getmPortions());
        binding.recipeCardIngredientsDescription.setText(recipe.getmIngredientsDescription());
        binding.recipeCardPrice.setText(""+recipe.getmPrice());

        binding.recipeHat1.setImageResource(R.drawable.hat_fill);
        binding.recipeHat2.setImageResource(recipe.getmCookComplexity()>1?R.drawable.hat_fill:R.drawable.hat_empty);
        binding.recipeHat3.setImageResource(recipe.getmCookComplexity()>2?R.drawable.hat_fill:R.drawable.hat_empty);

        binding.ingredientsTotalWeight.setText(""+recipe.getmWeight());

        binding.recipeCardProteins.setText(""+recipe.getmProteins());
        binding.recipeCardFats.setText(""+recipe.getmFats());
        binding.recipeCardCarbohydrates.setText(""+recipe.getmCarbohydrates());
        binding.recipeCardCalories.setText(""+recipe.getmCalories());

    }
    public void SetupAuthorViewData(User author){
        binding.recipeCardAuthorName.setText(author.getName());

    }

    @Override
    public void onClick(int profile_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", profile_id);
        NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_recipeCardFragment_to_profile, bundle);

    }
}
