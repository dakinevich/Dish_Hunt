package ru.example.dishhunt.ui.recipe_creator;

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

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.databinding.SearchProductsBinding;
import ru.example.dishhunt.ui.adapters.SearchProductsAdapter;
import ru.example.dishhunt.ui.view_models.CreateRecipeViewModel;

public class SearchProductsFragment extends Fragment implements ProductClickInterface {
    private SearchProductsBinding binding;
    private CreateRecipeViewModel mCreateRecipeViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = SearchProductsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mCreateRecipeViewModel = new ViewModelProvider(requireActivity()).get(CreateRecipeViewModel.class);

        RecyclerView products_recyclerView = view.findViewById(R.id.search_products_recycler_view);
        final SearchProductsAdapter product_adapter = new SearchProductsAdapter(new SearchProductsAdapter.RecipeDiff(), this);
        products_recyclerView.setAdapter(product_adapter);
        RecyclerView.LayoutManager productLayoutManager = new GridLayoutManager(view.getContext(), 1);
        products_recyclerView.setLayoutManager(productLayoutManager);

        mCreateRecipeViewModel.allProducts.observe(requireActivity(), product_adapter::submitList);

        binding.searchProductsBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(requireParentFragment()).navigateUp();

        });

        return view;
    }

    @Override
    public void onClick(ProductEntity product) {
        mCreateRecipeViewModel.recipe.addIngredient(product);
        NavHostFragment.findNavController(requireParentFragment()).navigateUp();
    }
}
