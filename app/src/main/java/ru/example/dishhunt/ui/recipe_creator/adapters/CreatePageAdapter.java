package ru.example.dishhunt.ui.recipe_creator.adapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.ArrayList;
import java.util.List;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.ImageStorage;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.models.Slide;
import ru.example.dishhunt.databinding.CreateSlideAddBinding;
import ru.example.dishhunt.databinding.CreateSlideBinding;
import ru.example.dishhunt.ui.recipe_creator.ProductClickInterface;
import ru.example.dishhunt.ui.recipe_creator.SlideClickInterface;
import ru.example.dishhunt.ui.recipe_creator.view_holders.SearchProductsViewHolder;


public class CreatePageAdapter extends FragmentStateAdapter {
    private List<Slide> slides;
    private static List<Bitmap> slides_img;
    public static SlideClickInterface slideClickInterface;
    public CreatePageAdapter(@NonNull FragmentActivity fragmentActivity, List<Slide> slides, List<Bitmap> slides_img, SlideClickInterface slideClickInterface) {
        super(fragmentActivity);
        this.slides = new ArrayList<>();
        this.slides.addAll(slides);
        this.slides.add(new Slide("", ""));
        CreatePageAdapter.slides_img = slides_img;
        CreatePageAdapter.slides_img = slides_img;
        CreatePageAdapter.slideClickInterface = slideClickInterface;
    }

    public CreatePageAdapter(@NonNull Fragment fragment, List<Slide> slides, List<Bitmap> slides_img, SlideClickInterface slideClickInterface) {
        super(fragment);
        this.slides = new ArrayList<>();
        this.slides.addAll(slides);
        this.slides.add(new Slide("", ""));
        CreatePageAdapter.slides_img = slides_img;
        CreatePageAdapter.slideClickInterface = slideClickInterface;
    }

    public CreatePageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment createPageFragment = new CreatePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page_index", position);
        bundle.putInt("page_max_index", slides.size()-1);
        bundle.putString("page_text", slides.get(position).getDescription().toString());
        createPageFragment.setArguments(bundle);
        return createPageFragment;
    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public static class CreatePageFragment extends Fragment {
        private CreateSlideBinding binding;
        @SuppressLint("SetTextI18n")
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {


            binding = CreateSlideBinding.inflate(inflater, container, false);

            Bundle bundle = this.getArguments();
            int index = 0, max_index = 0;
            String slideText = "";

            if (bundle != null) {
                index = bundle.getInt("page_index", 0);
                max_index = bundle.getInt("page_max_index", 0);
                slideText = bundle.getString("page_text", "");
            }
            if (index == max_index){
                int index_c = index;
                CreateSlideAddBinding binding2 = CreateSlideAddBinding.inflate(inflater, container, false);
                binding2.getRoot().setOnClickListener(v -> slideClickInterface.onAddClick(index_c));
                return binding2.getRoot();
            }
            else {
                if(index!=0){
                    binding.arrow.setVisibility(View.GONE);
                }
                int index_c = index;
                binding.recipePageImg.setOnClickListener(v -> slideClickInterface.onImgClick(index_c));
                binding.recipePageDelete.setOnClickListener(v -> slideClickInterface.onDeleteClick(index_c));
                binding.recipePageText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        slideClickInterface.onSlideTextEdit(index_c, binding.recipePageText.getText().toString());

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                binding.recipePageIndex.setText(index+1+"/"+max_index);
                binding.recipePageImg.setImageBitmap(slides_img.get(index));
                binding.recipePageText.setText(slideText);
                return binding.getRoot();

            }

        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }
    }

}
