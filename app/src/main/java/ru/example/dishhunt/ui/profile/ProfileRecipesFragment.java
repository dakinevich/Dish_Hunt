package ru.example.dishhunt.ui.profile;

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
import ru.example.dishhunt.databinding.ProfileRecipesBinding;
import ru.example.dishhunt.databinding.SavedRecipesBinding;
import ru.example.dishhunt.ui.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.home.RecyclerViewInterface;
import ru.example.dishhunt.ui.view_models.ProfileRecipesViewModel;
import ru.example.dishhunt.ui.view_models.SavedViewModel;

public class ProfileRecipesFragment extends Fragment implements RecyclerViewInterface {
    private ProfileRecipesViewModel mProfileRecipesViewModel;
    private ProfileRecipesBinding binding;
    private RecipeListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProfileRecipesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.profile_recyclerview);
        adapter = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff(), this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        //ViewModel
        mProfileRecipesViewModel = new ViewModelProvider(requireActivity()).get(ProfileRecipesViewModel.class);

        mProfileRecipesViewModel.getUserRecipes().observe(requireActivity(), recipes -> {
            adapter.submitList(recipes);
        });
        return view;
    }


    @Override
    public void onCardClick(int card_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id", card_id);
        NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_recipeCardFragment, bundle);
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