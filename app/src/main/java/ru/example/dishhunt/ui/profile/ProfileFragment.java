package ru.example.dishhunt.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.User;
import ru.example.dishhunt.databinding.ProfileBinding;
import ru.example.dishhunt.databinding.SavedBinding;
import ru.example.dishhunt.ui.saved.SavedCollections;
import ru.example.dishhunt.ui.saved.SavedRecipes;
import ru.example.dishhunt.ui.view_models.ProfileViewModel;
import ru.example.dishhunt.ui.view_models.RecipeCardViewModel;

public class ProfileFragment extends Fragment {
    private ProfileBinding binding;
    private int userId;
    private ProfileViewModel mProfileViewModel;
    private User user;
    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = ProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        userId = requireActivity().getPreferences(Context.MODE_PRIVATE).getInt(getString(R.string.my_id), 1);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_nav_menu);
            navBar.setVisibility(View.GONE);
            userId = bundle.getInt("user_id", 1);
        }
        mProfileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        mProfileViewModel.getProfile(userId).observe(requireActivity(), (elem) ->{
            user = elem;
            SetupViewData(user);
        });

        replaceFragment(new ProfileRecipesFragment());
        binding.profileRecipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.profileRecipesBtn);
                btn_unpressed(binding.profileCollectionsBtn);
                replaceFragment(new ProfileRecipesFragment());
            }
        });
        binding.profileCollectionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pressed(binding.profileCollectionsBtn);
                btn_unpressed(binding.profileRecipesBtn);

            }
        });
        return view;
    }

    private void btn_pressed(TextView btn){
        btn.setBackgroundResource(R.drawable.rounded_fill_light_20);
        btn.setTextColor(getResources().getColor(R.color.accent_dark, getResources().newTheme()));
    }
    private void btn_unpressed(TextView btn){
        btn.setBackgroundResource(R.drawable.rounded_corner_dark_20);
        btn.setTextColor(getResources().getColor(R.color.flat3, getResources().newTheme()));
    }
    private void replaceFragment(Fragment fragment){
        getChildFragmentManager().beginTransaction()
                .replace(R.id.profile_fragment_container, fragment)
                .commit();
    }
    private void SetupViewData(User user){
        binding.getRoot();
        binding.profileUserName.setText(user.getName());
        binding.profileUserBio.setText(user.getBio());
    }

}
