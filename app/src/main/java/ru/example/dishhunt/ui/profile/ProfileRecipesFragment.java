package ru.example.dishhunt.ui.profile;

import android.content.Context;
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

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.databinding.ProfileRecipesBinding;
import ru.example.dishhunt.ui.search.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.search.RecipeClickInterface;
import ru.example.dishhunt.ui.profile.view_models.ProfileRecipesViewModel;

public class ProfileRecipesFragment extends Fragment implements RecipeClickInterface {
    private ProfileRecipesViewModel mProfileRecipesViewModel;
    private ProfileRecipesBinding binding;
    private RecipeListAdapter adapter;
    private int userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProfileRecipesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        userId = requireActivity().getSharedPreferences(getString(R.string.main_shared_preferences_name), Context.MODE_PRIVATE).getInt(getString(R.string.my_id), 1);
        assert getParentFragment() != null;
        Bundle bundle =  getParentFragment().getArguments();
        if (bundle != null) {
            userId = bundle.getInt("user_id", userId);
        }
        //RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.profile_recyclerview);
        adapter = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff(), this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        //ViewModel
        mProfileRecipesViewModel = new ViewModelProvider(requireActivity()).get(ProfileRecipesViewModel.class);
        mProfileRecipesViewModel.getUserRecipes(userId).observe(requireActivity(), combo -> {
            List<Recipe> recipes = combo.first;
            recipes.forEach((elem) -> {
                elem.setmIsSaved(combo.second.contains(elem.getId()));
            });
            adapter.submitList(recipes);
        });
        return view;
    }


    @Override
    public void onCardClick(int card_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id", card_id);
        Fragment p = getParentFragment();
        if (p != null &
                p.getParentFragment() != null &
                p.getParentFragment().getClass()== MyProfileContainerFragment.class){
            NavHostFragment.findNavController(this).navigate(R.id.action_my_profile_to_recipeCardFragment, bundle);
        }
        else{
            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_recipeCardFragment, bundle);
        }

    }

    @Override
    public void onSaveClick(int card_id, boolean saved) {
        if (saved){
            mProfileRecipesViewModel.removeWishlist(card_id);
        }
        else {
            mProfileRecipesViewModel.addWishlist(card_id);

        }
    }
}