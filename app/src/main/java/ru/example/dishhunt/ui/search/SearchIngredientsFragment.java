package ru.example.dishhunt.ui.search;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.databinding.SearchProductsBinding;
import ru.example.dishhunt.ui.recipe_creator.ProductClickInterface;
import ru.example.dishhunt.ui.recipe_creator.adapters.SearchProductsAdapter;
import ru.example.dishhunt.ui.recipe_creator.view_models.CreateRecipeViewModel;
import ru.example.dishhunt.ui.search.view_models.SearchViewModel;

public class SearchIngredientsFragment extends DialogFragment implements ProductClickInterface {
    private SearchProductsBinding binding;
    private SearchViewModel mSearchViewModel;
    private String mode;
    private MyDialogCloseListener closeListener;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = SearchProductsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle input_mode = getArguments();
        if (input_mode!=null){
            mode = input_mode.getString("mode");
        }
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        mSearchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        RecyclerView products_recyclerView = view.findViewById(R.id.search_products_recycler_view);
        final SearchProductsAdapter product_adapter = new SearchProductsAdapter(new SearchProductsAdapter.RecipeDiff(), this, mode);
        products_recyclerView.setAdapter(product_adapter);
        RecyclerView.LayoutManager productLayoutManager = new GridLayoutManager(view.getContext(), 1);
        products_recyclerView.setLayoutManager(productLayoutManager);

        mSearchViewModel.search_products("").observe(requireActivity(), product_adapter::submitList);


        binding.searchProductsBack.setOnClickListener(v -> {
            //NavHostFragment.findNavController(requireParentFragment()).navigateUp();
            dismiss();


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
                mSearchViewModel.search_products(s.toString()).observe(requireActivity(), product_adapter::submitList);
            }
        });
        return view;
    }


    @Override
    public void onProductClick(ProductEntity product) {
        if (Objects.equals(mode, "add")){
            mSearchViewModel.addIncludeProduct(product);
        }
        else{
            mSearchViewModel.addExcludeProduct(product);
        }
        dismiss();
        //NavHostFragment.findNavController(requireParentFragment()).navigateUp();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(closeListener != null) {
            closeListener.handleDialogClose(null);
        }
    }


    public void DismissListener(MyDialogCloseListener closeListener) {
        this.closeListener = closeListener;
    }
}
