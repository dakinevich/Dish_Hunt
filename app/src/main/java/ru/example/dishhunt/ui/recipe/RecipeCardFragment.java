package ru.example.dishhunt.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.sql.Time;
import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.databinding.HomeBinding;
import ru.example.dishhunt.databinding.RecipeCardBinding;
import ru.example.dishhunt.ui.adapters.RecipeCommentsAdapter;
import ru.example.dishhunt.ui.adapters.RecipeCommentsViewHolder;
import ru.example.dishhunt.ui.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.adapters.RecipePageAdapter;
import ru.example.dishhunt.ui.view_models.RecipeCardViewModel;
import ru.example.dishhunt.ui.view_models.RecipeCommentsViewModel;
import ru.example.dishhunt.ui.view_models.RecipePreviewViewModel;

public class RecipeCardFragment extends Fragment {
    private RecipeCardBinding binding;
    private ViewPager2 viewPager2;
    private RecipePageAdapter pageAdapter;
    private RecipeCardViewModel mRecipeCardViewModel;
    private RecipeCommentsViewModel mRecipeCommentsViewModel;
    private int recipeId;
    private Recipe recipe;
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
        mRecipeCardViewModel.getRecipeById(recipeId).observe(requireActivity(), (combo) ->{
            Recipe res = combo.first;
            if (res != null){
                res.setmIsSaved(combo.second.contains(res.getId()));
                recipe = res;
                SetupViewData(res);
            }
        });

        RecyclerView comments_recyclerView = view.findViewById(R.id.comments_recyclerview);
        final RecipeCommentsAdapter adapter = new RecipeCommentsAdapter(new RecipeCommentsAdapter.RecipeDiff());
        comments_recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 1);
        comments_recyclerView.setLayoutManager(mLayoutManager);

        //ViewModel
        mRecipeCommentsViewModel = new ViewModelProvider(requireActivity()).get(RecipeCommentsViewModel.class);
        mRecipeCommentsViewModel.getRecipeCommentsById(recipeId).observe(requireActivity(), adapter::submitList);



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
        binding.recipeAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = binding.recipeMyComment.getText().toString();
                Comment comment = new Comment(recipeId, 0, commentText, 0);
                mRecipeCommentsViewModel.addComment(comment);
                binding.recipeMyComment.setText("");
            }
        });

        return view;
    }
    public void SetupViewData(Recipe recipe){
        binding.recipeCardSaveBtn.setImageResource(recipe.ismIsSaved()?R.drawable.bookmark_filled:R.drawable.bookmark);
        binding.recipeCardTitle.setText(recipe.getmTitle());
        binding.recipeCardDescription.setText(recipe.getmDescription());
        binding.recipeCardImg.setImageResource(Integer.parseInt(recipe.getmImgSrc()));

    }
}
