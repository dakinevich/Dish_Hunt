package ru.example.dishhunt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import ru.example.dishhunt.R;
import ru.example.dishhunt.databinding.SearchBinding;
import ru.example.dishhunt.ui.view_models.RecipePreviewViewModel;

public class SearchFragment extends Fragment {
    private SearchBinding binding;
    private RecipePreviewViewModel mRecipePreviewViewModel;

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
            NavHostFragment.findNavController(this).navigateUp();
        });
        binding.searchForwardBtn.setOnClickListener(view -> {
            String search_text = binding.searchEditText.getText().toString();
            String search_time_from = binding.searchTimeFrom.getText().toString();
            String search_time_to = binding.searchTimeTo.getText().toString();
            String search_portions_from = binding.searchPortionsFrom.getText().toString();
            String search_portions_to = binding.searchPortionsTo.getText().toString();

            Bundle bundle_out = new Bundle();
            bundle_out.putString("search_text", search_text);

            if(!search_time_from.equals("")){bundle_out.putInt("time_from", Integer.parseInt(search_time_from));}
            if(!search_time_to.equals("")){bundle_out.putInt("time_to", Integer.parseInt(search_time_to));}
            if(!search_portions_from.equals("")){bundle_out.putInt("portions_from", Integer.parseInt(search_portions_from));}
            if(!search_portions_to.equals("")){bundle_out.putInt("portions_to", Integer.parseInt(search_portions_to));}

            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_resultsFragment, bundle_out);
        });


        return binding.getRoot();
    }
}
