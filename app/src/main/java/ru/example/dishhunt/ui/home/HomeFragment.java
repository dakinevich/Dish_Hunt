package ru.example.dishhunt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import ru.example.dishhunt.R;
import ru.example.dishhunt.databinding.HomeBinding;

public class HomeFragment extends Fragment {
    private HomeBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = HomeBinding.inflate(inflater, container, false);


        binding.homeSearchBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_home_to_searchFragment);
        });


        return binding.getRoot();
    }
}
