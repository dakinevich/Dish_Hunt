package ru.example.dishhunt.ui.recipe.adapters;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Slide;
import ru.example.dishhunt.ui.recipe.RecipePageFragment;

public class RecipePageAdapter extends FragmentStateAdapter {
    private List<Slide> slides;
    public RecipePageAdapter(@NonNull FragmentActivity fragmentActivity, List<Slide> slides) {
        super(fragmentActivity);
        this.slides = slides;
    }

    public RecipePageAdapter(@NonNull Fragment fragment, List<Slide> slides) {
        super(fragment);
        this.slides = slides;
    }

    public RecipePageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment recipePageFragment = new RecipePageFragment();
        Bundle bundle = new Bundle();
        if (slides != null){
            bundle.putString("img_src", slides.get(position).getImgSrc().toString());
            bundle.putString("page_index", position+1+"/"+ slides.size());
            bundle.putString("page_text", slides.get(position).getDescription().toString());
        }


        recipePageFragment.setArguments(bundle);
        return recipePageFragment;
    }

    @Override
    public int getItemCount() {
        if (slides!=null){
            return slides.size();
        }
        else{
            return 0;
        }
    }
}


