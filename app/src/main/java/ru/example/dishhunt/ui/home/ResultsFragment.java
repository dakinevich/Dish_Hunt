package ru.example.dishhunt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.repositories.RecipeRepository;
import ru.example.dishhunt.databinding.HomeBinding;
import ru.example.dishhunt.databinding.ResultsBinding;
import ru.example.dishhunt.ui.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.view_models.RecipeViewModel;

public class ResultsFragment extends Fragment {
    private RecipeViewModel mRecipeViewModel;

    private ResultsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().getApplicationContext().deleteDatabase("recipe_database");
        binding = ResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        String result = getArguments().getString("search_text");
        binding.resultsTextView.setText(result);

        RecyclerView recyclerView = view.findViewById(R.id.results_recyclerview);
        final RecipeListAdapter adapter = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff());
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        mRecipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel .class);
        mRecipeViewModel.getAllRecipes().observe(requireActivity(), recipe -> {
            adapter.submitList(recipe);
        });

        binding.resultsBackBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_home);
        });
        binding.resultsSettingsBtn.setOnClickListener(view_f -> {
            String search_text = binding.resultsTextView.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("search_text", search_text);
            NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_searchFragment, bundle);
        });


        return view;
    }


}
