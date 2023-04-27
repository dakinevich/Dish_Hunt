package ru.example.dishhunt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import ru.example.dishhunt.databinding.ResultsBinding;
import ru.example.dishhunt.ui.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.view_models.RecipePreviewViewModel;

public class ResultsFragment extends Fragment {
    private RecipePreviewViewModel mRecipePreviewViewModel;

    private ResultsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //GettingArgs
        int time_from = 0, time_to = 0;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String result = bundle.getString("search_text", "");
            time_from = bundle.getInt("time_from", 0);
            time_to = bundle.getInt("time_to", 100000);
            binding.resultsTextView.setText(result);
        }

        //RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.results_recyclerview);
        final RecipeListAdapter adapter = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff());
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        //ViewModel
        mRecipePreviewViewModel = new ViewModelProvider(requireActivity()).get(RecipePreviewViewModel.class);
        mRecipePreviewViewModel.searchRecipes(time_from, time_to).observe(requireActivity(), recipe -> {
            adapter.submitList(recipe);
        });


        //ClickListeners setup
        binding.resultsBackBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_home);
        });
        //so wet
        binding.resultsTextView.setOnClickListener(view_f -> {
            String search_text = binding.resultsTextView.getText().toString();
            Bundle bundle_out = new Bundle();
            bundle_out.putString("search_text", search_text);
            NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_searchFragment, bundle_out);
        });
        binding.resultsSettingsBtn.setOnClickListener(view_f -> {
            String search_text = binding.resultsTextView.getText().toString();
            Bundle bundle_out = new Bundle();
            bundle_out.putString("search_text", search_text);
            NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_searchFragment, bundle_out);
        });


        return view;
    }


}
