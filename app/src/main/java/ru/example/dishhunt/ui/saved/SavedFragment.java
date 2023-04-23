package ru.example.dishhunt.ui.saved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.example.dishhunt.R;
import ru.example.dishhunt.databinding.SavedBinding;

public class SavedFragment extends Fragment {
    private SavedBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = SavedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        replaceFragment( new SavedRecipes());
        binding.savedRecipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.savedRecipesBtn);
                btn_unpressed(binding.savedCollectionsBtn);
                replaceFragment(new SavedRecipes());
            }
        });
        binding.savedCollectionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.savedCollectionsBtn);
                btn_unpressed(binding.savedRecipesBtn);
                replaceFragment(new SavedCollections());
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void btn_pressed(TextView btn){
        btn.setBackgroundResource(R.drawable.rounded_fill_light_20);
        btn.setTextColor(getResources().getColor(R.color.accent_dark, getResources().newTheme()));
    }
    private void btn_unpressed(TextView btn){
        btn.setBackgroundResource(R.drawable.rounded_corner_light_20);
        btn.setTextColor(getResources().getColor(R.color.flat2, getResources().newTheme()));
    }
    private void replaceFragment(Fragment fragment){
        getChildFragmentManager().beginTransaction()
                .replace(R.id.saved_fragment_container, fragment)
                .commit();
    }

}
