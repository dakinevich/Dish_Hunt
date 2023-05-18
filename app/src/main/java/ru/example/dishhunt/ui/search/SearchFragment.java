package ru.example.dishhunt.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.databinding.SearchBinding;
import ru.example.dishhunt.ui.search.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.search.adapters.SearchIngredientsAdapter;
import ru.example.dishhunt.ui.search.view_models.SearchViewModel;

public class SearchFragment extends Fragment implements SearchIngredientClickInterface {
    private SearchBinding binding;
    private SearchViewModel mRecipePreviewViewModel;
    private SearchIngredientsAdapter adapter_remove;
    private SearchIngredientsAdapter adapter_add;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = SearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mRecipePreviewViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);


        RecyclerView recyclerView_add = view.findViewById(R.id.search_products_to_add_recycler_view);
        adapter_add = new SearchIngredientsAdapter(new SearchIngredientsAdapter.ProductDiff(), this, "add");
        recyclerView_add.setAdapter(adapter_add);
        recyclerView_add.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter_add.submitList(mRecipePreviewViewModel.includeProducts);

        RecyclerView recyclerView_remove = view.findViewById(R.id.search_products_to_remove_recycler_view);
        adapter_remove = new SearchIngredientsAdapter(new SearchIngredientsAdapter.ProductDiff(), this, "remove");
        recyclerView_remove.setAdapter(adapter_remove);
        recyclerView_remove.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter_remove.submitList(mRecipePreviewViewModel.excludeProducts);

        binding.searchBackBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
        binding.searchIngredientAdd.setOnClickListener(view_f -> {
            Bundle mode_data = new Bundle();
            mode_data.putString("mode", "add");
            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_searchIngredientsFragment, mode_data);

        });
        binding.searchIngredientRemove.setOnClickListener(view_f -> {
            Bundle mode_data = new Bundle();
            mode_data.putString("mode", "remove");
            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_searchIngredientsFragment, mode_data);

        });
        binding.searchForwardBtn.setOnClickListener(view_f -> {
            String search_text = binding.searchEditText.getText().toString();
            String search_time_from = binding.searchTimeFrom.getText().toString();
            String search_time_to = binding.searchTimeTo.getText().toString();
            String search_portions_from = binding.searchPortionsFrom.getText().toString();
            String search_portions_to = binding.searchPortionsTo.getText().toString();

            String search_cal_from = binding.searchCalFrom.getText().toString();
            String search_cal_to = binding.searchCalTo.getText().toString();
            String search_price_from = binding.searchPriceFrom.getText().toString();
            String search_price_to = binding.searchPriceTo.getText().toString();

            Bundle bundle_out = new Bundle();
            bundle_out.putString("search_text", search_text);

            if(!search_time_from.equals("")){bundle_out.putInt("time_from", Integer.parseInt(search_time_from));}
            if(!search_time_to.equals("")){bundle_out.putInt("time_to", Integer.parseInt(search_time_to));}
            if(!search_portions_from.equals("")){bundle_out.putInt("portions_from", Integer.parseInt(search_portions_from));}
            if(!search_portions_to.equals("")){bundle_out.putInt("portions_to", Integer.parseInt(search_portions_to));}
            if(!search_cal_from.equals("")){bundle_out.putInt("cal_from", Integer.parseInt(search_cal_from));}
            if(!search_cal_to.equals("")){bundle_out.putInt("cal_to", Integer.parseInt(search_cal_to));}
            if(!search_price_from.equals("")){bundle_out.putInt("price_from", Integer.parseInt(search_price_from));}
            if(!search_price_to.equals("")){bundle_out.putInt("price_to", Integer.parseInt(search_price_to));}

            NavHostFragment.findNavController(this).navigate(R.id.action_searchFragment_to_resultsFragment, bundle_out);
        });

        binding.savedRecipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.savedRecipesBtn);
                btn_unpressed(binding.savedCollectionsBtn);
            }
        });
        binding.savedCollectionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.savedCollectionsBtn);
                btn_unpressed(binding.savedRecipesBtn);
            }
        });

        return view;
    }
    private void btn_pressed(TextView btn){
        btn.setBackgroundResource(R.drawable.rounded_fill_light_20);
        btn.setTextColor(getResources().getColor(R.color.accent_dark, getResources().newTheme()));
    }
    private void btn_unpressed(TextView btn){
        btn.setBackgroundResource(R.drawable.rounded_corner_dark_20);
        btn.setTextColor(getResources().getColor(R.color.flat3, getResources().newTheme()));
    }

    @Override
    public void onClick(ProductEntity product) {
        mRecipePreviewViewModel.removeProduct(product);
        adapter_add.submitList(mRecipePreviewViewModel.includeProducts);
        adapter_add.notifyDataSetChanged();
        adapter_remove.submitList(mRecipePreviewViewModel.excludeProducts);
        adapter_remove.notifyDataSetChanged();

    }
}
