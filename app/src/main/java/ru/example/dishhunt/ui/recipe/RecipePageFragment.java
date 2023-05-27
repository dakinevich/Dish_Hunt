package ru.example.dishhunt.ui.recipe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.example.dishhunt.data.ImageStorage;
import ru.example.dishhunt.databinding.RecipeCardBinding;
import ru.example.dishhunt.databinding.RecipeSlideBinding;

public class RecipePageFragment extends Fragment {

        private RecipeSlideBinding binding;
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {

            binding = RecipeSlideBinding.inflate(inflater, container, false);
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                String imgSrc = bundle.getString("img_src", "");
                String index = bundle.getString("page_index", "?/?");
                String slideText = bundle.getString("page_text", "");

                binding.recipePageIndex.setText(index);
                binding.recipePageImg.setImageBitmap(ImageStorage.getImage(imgSrc, requireContext()));
                binding.recipePageText.setText(slideText);
            }
            return binding.getRoot();
        }


}
