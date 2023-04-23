package ru.example.dishhunt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import ru.example.dishhunt.R;
import ru.example.dishhunt.databinding.HomeBinding;
import ru.example.dishhunt.databinding.SavedBinding;

public class HomeFragment extends Fragment {
    private HomeBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = HomeBinding.inflate(inflater, container, false);

        binding.homeSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("search_text", "");
                NavHostFragment.findNavController(FragmentManager.findFragment(v)).navigate(R.id.action_home_to_searchFragment, bundle);
            }
        });


        return binding.getRoot();
    }
}
