package ru.example.dishhunt.ui.search;

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
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.databinding.ResultsBinding;
import ru.example.dishhunt.ui.search.adapters.RecipeListAdapter;
import ru.example.dishhunt.ui.search.view_models.SearchViewModel;

public class ResultsFragment extends Fragment implements RecipeClickInterface {
    private SearchViewModel searchViewModel;

    private ResultsBinding binding;
    private RecipeListAdapter adapter;
    int time_from = 0, time_to = -1, portions_from = 0, portions_to = -1, cal_from = 0, cal_to = -1, price_from = 0, price_to = -1;
    String search_text = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //GettingArgs

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            search_text = bundle.getString("search_text", "");
            time_from = bundle.getInt("time_from", 0);
            time_to = bundle.getInt("time_to", -1);
            portions_from = bundle.getInt("portions_from", 0);
            portions_to = bundle.getInt("portions_to", -1);
            cal_from = bundle.getInt("cal_from", 0);
            cal_to = bundle.getInt("cal_to", -1);
            price_from = bundle.getInt("price_from", 0);
            price_to = bundle.getInt("price_to", -1);
            binding.resultsTextView.setText(search_text);
        }

        //RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.results_recyclerview);
        adapter = new RecipeListAdapter(new RecipeListAdapter.RecipeDiff(), this);
        recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        //ViewModel
        searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);

        searchViewModel.searchRecipes(time_from, time_to, portions_from, portions_to, search_text).observe(requireActivity(), combo -> {
            List<Recipe> recipes = combo.first;
            recipes.forEach((elem) -> {
                elem.setmIsSaved(combo.second.contains(elem.getId()));
            });

            adapter.submitList(filterRecipes(recipes));
        });


        //ClickListeners setup
        binding.resultsTextView.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigateUp();
        });


        return view;
    }


    @Override
    public void onCardClick(int card_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id", card_id);
        NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_recipeCardFragment, bundle);
    }

    @Override
    public void onSaveClick(int card_id, boolean saved) {

        if (saved){
            searchViewModel.removeWishlist(card_id);
        }
        else {
            searchViewModel.addWishlist(card_id);

        }
        /*new Thread()
        {
            public void run() {
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message inputMessage) {
                        try {
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyItemChanged(position);
                    }
                }.sendEmptyMessage(1);
            }
        }.start();*/


    }
    public List<Recipe> filterRecipes(List<Recipe> recipes){
        int inf = Integer.MAX_VALUE;
        cal_from = (cal_from<0)?inf:cal_from;
        cal_to = (cal_to<0)?inf:cal_to;
        price_from = (price_from<0)?inf:price_from;
        price_to = (price_to<0)?inf:price_to;

        List<Recipe> recipes_first_step = recipes.stream()
                .filter(r ->
                        (cal_from < r.getmCalories() && cal_to > r.getmCalories()) &&
                                (price_from < r.getmPrice() && price_to > r.getmPrice()))
                .collect(Collectors.toList());
        List<Recipe> recipes_second_step = recipes_first_step.stream()
                .filter(r -> {
                    boolean flag = true;
                    for(ProductEntity p: searchViewModel.includeProducts){
                        flag = flag&r.hasmProduct(p);
                    }
                    for(ProductEntity p: searchViewModel.excludeProducts){
                        if (r.hasmProduct(p)){
                            flag = false;
                        };
                    }
                    return flag;
                })
                .collect(Collectors.toList());
        return recipes_second_step;
    }
}
