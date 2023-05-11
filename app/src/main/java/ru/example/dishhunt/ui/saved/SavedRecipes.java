package ru.example.dishhunt.ui.saved;

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

import java.util.List;
import java.util.stream.Collectors;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.databinding.SavedRecipesBinding;
import ru.example.dishhunt.ui.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.home.RecipeClickInterface;
import ru.example.dishhunt.ui.view_models.SavedViewModel;

public class SavedRecipes extends Fragment implements RecipeClickInterface {
    private SavedViewModel mSavedViewModel;
    private SavedRecipesBinding binding;
    private RecipeListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SavedRecipesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.saved_recyclerview);
        adapter = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff(), this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        //ViewModel
        mSavedViewModel = new ViewModelProvider(requireActivity()).get(SavedViewModel.class);

        mSavedViewModel.getSavedRecipes().observe(requireActivity(), combo -> {
            List<Recipe> recipes = combo.first;
            recipes.forEach((elem) -> {
                elem.setmIsSaved(combo.second.contains(elem.getId()));
            });
            adapter.submitList(recipes.stream()
                    .filter(Recipe::ismIsSaved)
                    .collect(Collectors.toList()));
        });
        return view;
    }


    @Override
    public void onCardClick(int card_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id", card_id);
        NavHostFragment.findNavController(this).navigate(R.id.action_saved_to_recipeCardFragment, bundle);
    }

    @Override
    public void onSaveClick(int card_id, boolean saved) {
        if (saved){
            mSavedViewModel.removeWishlist(card_id);
        }
        else {
            mSavedViewModel.addWishlist(card_id);

        }
    }
}

