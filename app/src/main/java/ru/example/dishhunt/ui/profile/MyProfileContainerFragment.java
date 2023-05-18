package ru.example.dishhunt.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import ru.example.dishhunt.R;
import ru.example.dishhunt.databinding.MyProfileContainerBinding;


public class MyProfileContainerFragment extends Fragment {
    private MyProfileContainerBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = MyProfileContainerBinding.inflate(inflater, container, false);
        replaceFragment(new ProfileFragment());

        View view = binding.getRoot();

        binding.myProfileCreateBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_my_profile_to_createRecipeFragment);
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void replaceFragment(Fragment fragment){
        getChildFragmentManager().beginTransaction()
                .replace(R.id.my_profile_fragment_container, fragment)
                .commit();
    }
}
