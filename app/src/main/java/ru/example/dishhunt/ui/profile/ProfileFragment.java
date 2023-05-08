package ru.example.dishhunt.ui.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import ru.example.dishhunt.R;
import ru.example.dishhunt.databinding.ProfileBinding;
import ru.example.dishhunt.databinding.SavedBinding;
import ru.example.dishhunt.ui.saved.SavedCollections;
import ru.example.dishhunt.ui.saved.SavedRecipes;

public class ProfileFragment extends Fragment {
    private ProfileBinding binding;
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = ProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        replaceFragment(new ProfileRecipesFragment());
        binding.profileRecipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.profileRecipesBtn);
                btn_unpressed(binding.profileCollectionsBtn);
                replaceFragment(new SavedRecipes());
            }
        });
        binding.profileCollectionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.profileCollectionsBtn);
                btn_unpressed(binding.profileRecipesBtn);
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
                .replace(R.id.profile_fragment_container, fragment)
                .commit();
    }

}
