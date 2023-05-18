package ru.example.dishhunt.ui.recipe.adapters;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.example.dishhunt.R;
import ru.example.dishhunt.ui.recipe.RecipePageFragment;

public class RecipePageAdapter extends FragmentStateAdapter {
    private final int[] dr_list = {R.drawable.test_page_1, R.drawable.test_page_2, R.drawable.test_page_3};
    public RecipePageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public RecipePageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public RecipePageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment recipePageFragment = new RecipePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("img_src", dr_list[position]);
        bundle.putString("page_index", position+1+"/"+ dr_list.length);

        recipePageFragment.setArguments(bundle);
        return recipePageFragment;
    }

    @Override
    public int getItemCount() {
        return dr_list.length;
    }
}


