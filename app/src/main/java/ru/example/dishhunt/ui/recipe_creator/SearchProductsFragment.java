package ru.example.dishhunt.ui.recipe_creator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.databinding.CreateSearchProductsBinding;
import ru.example.dishhunt.ui.recipe_creator.adapters.SearchProductsAdapter;
import ru.example.dishhunt.ui.recipe_creator.view_models.CreateRecipeViewModel;

public class SearchProductsFragment extends Fragment implements ProductClickInterface {
    private CreateSearchProductsBinding binding;
    private CreateRecipeViewModel mCreateRecipeViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = CreateSearchProductsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mCreateRecipeViewModel = new ViewModelProvider(requireActivity()).get(CreateRecipeViewModel.class);

        RecyclerView products_recyclerView = view.findViewById(R.id.search_products_recycler_view);
        final SearchProductsAdapter product_adapter = new SearchProductsAdapter(new SearchProductsAdapter.RecipeDiff(), this);
        products_recyclerView.setAdapter(product_adapter);
        RecyclerView.LayoutManager productLayoutManager = new GridLayoutManager(view.getContext(), 1);
        products_recyclerView.setLayoutManager(productLayoutManager);

        mCreateRecipeViewModel.search_products("").observe(requireActivity(), product_adapter::submitList);


        binding.searchProductsBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(requireParentFragment()).navigateUp();

        });
        binding.searchProductsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("we", s.toString());
                mCreateRecipeViewModel.search_products(s.toString()).observe(requireActivity(), product_adapter::submitList);
            }
        });

        return view;
    }

    @Override
    public void onClick(ProductEntity product) {
        mCreateRecipeViewModel.recipe.addIngredient(product);
        NavHostFragment.findNavController(requireParentFragment()).navigateUp();
    }
}
