package ru.example.dishhunt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.databinding.HomeBinding;
import ru.example.dishhunt.databinding.SearchBinding;
import ru.example.dishhunt.ui.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.view_models.RecipeViewModel;

public class SearchFragment extends Fragment {
    private SearchBinding binding;
    private RecipeViewModel mRecipeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = SearchBinding.inflate(inflater, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String result = bundle.getString("search_text", "");
            binding.searchEditText.setText(result);
        }

        binding.searchBackBtn.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_home);
        });
        binding.searchForwardBtn.setOnClickListener(view -> {
            String search_text = binding.searchEditText.getText().toString();
            Bundle bundle_out = new Bundle();
            bundle_out.putString("search_text", search_text);
            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_resultsFragment, bundle_out);
        });


        return binding.getRoot();
    }
}
