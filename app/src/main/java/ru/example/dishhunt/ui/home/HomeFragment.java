package ru.example.dishhunt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.databinding.HomeBinding;
import ru.example.dishhunt.ui.home.view_models.HomeViewModel;
import ru.example.dishhunt.ui.search.RecipeClickInterface;
import ru.example.dishhunt.ui.search.adapters.RecipeListAdapter;

public class HomeFragment extends Fragment implements RecipeClickInterface {
    private HomeBinding binding;
    private RecipeListAdapter adapter_1;
    private RecipeListAdapter adapter_2;
    private RecipeListAdapter adapter_3;
    private HomeViewModel mHomeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = HomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.homeSearchBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_home_to_searchFragment);
        });

        //1
        adapter_1 = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff(), this);
        RecyclerView.LayoutManager mLayoutManager_1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView_1 = view.findViewById(R.id.home_recycler_view_1);
        recyclerView_1.setAdapter(adapter_1);
        recyclerView_1.setLayoutManager(mLayoutManager_1);
        //2
        adapter_2 = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff(), this);
        RecyclerView.LayoutManager mLayoutManager_2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView_2 = view.findViewById(R.id.home_recycler_view_2);
        recyclerView_2.setAdapter(adapter_2);
        recyclerView_2.setLayoutManager(mLayoutManager_2);
        //3
        adapter_3 = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff(), this);
        RecyclerView.LayoutManager mLayoutManager_3 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView_3 = view.findViewById(R.id.home_recycler_view_3);
        recyclerView_3.setAdapter(adapter_3);
        recyclerView_3.setLayoutManager(mLayoutManager_3);

        //ViewModel
        mHomeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        mHomeViewModel.searchRecipes(0, -1, 0, -1).observe(requireActivity(), combo -> {
            List<Recipe> recipes = combo.first;
            recipes.forEach((elem) -> elem.setmIsSaved(combo.second.contains(elem.getId())));
            adapter_1.submitList(recipes);
        });
        mHomeViewModel.searchRecipes(0, 20, 0, -1).observe(requireActivity(), combo -> {
            List<Recipe> recipes = combo.first;
            recipes.forEach((elem) -> elem.setmIsSaved(combo.second.contains(elem.getId())));
            adapter_2.submitList(recipes);
        });
        mHomeViewModel.searchRecipes(0, -1, 0, -1).observe(requireActivity(), combo -> {
            List<Recipe> recipes = combo.first;
            recipes.forEach((elem) -> elem.setmIsSaved(combo.second.contains(elem.getId())));
            adapter_3.submitList(filterRecipes(recipes, 0, -1, 0, 200));
        });

        return view;
    }


    @Override
    public void onCardClick(int card_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id", card_id);
        NavHostFragment.findNavController(this).navigate(R.id.action_home_to_recipeCardFragment, bundle);
    }

    @Override
    public void onSaveClick(int card_id, boolean saved) {

        if (saved){
            mHomeViewModel.removeWishlist(card_id);
        }
        else {
            mHomeViewModel.addWishlist(card_id);

        }

    }
    public List<Recipe> filterRecipes(List<Recipe> recipes, int cal_from, int cal_to, int price_from, int price_to){
        int inf = Integer.MAX_VALUE;
        int cal_from_t = (cal_from<0)?inf:cal_from;
        int cal_to_t = (cal_to<0)?inf:cal_to;
        int price_from_t = (price_from<0)?inf:price_from;
        int price_to_t = (price_to<0)?inf:price_to;

        return recipes.stream()
                .filter(r ->
                        (cal_from_t < r.getmCalories() && cal_to_t > r.getmCalories())&&
                                (price_from_t < r.getmPrice() && price_to_t > r.getmPrice()))
                .collect(Collectors.toList());
    }
}
