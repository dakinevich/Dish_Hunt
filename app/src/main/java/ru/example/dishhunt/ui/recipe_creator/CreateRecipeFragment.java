package ru.example.dishhunt.ui.recipe_creator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.function.Function;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.Slide;
import ru.example.dishhunt.databinding.CreateSlideBinding;
import ru.example.dishhunt.ui.recipe_creator.adapters.CreateIngredientsAdapter;
import ru.example.dishhunt.ui.recipe_creator.adapters.CreatePageAdapter;
import ru.example.dishhunt.ui.recipe_creator.view_models.CreateRecipeViewModel;
import ru.example.dishhunt.databinding.CreateRecipeBinding;
import ru.example.dishhunt.ui.search.MyDialogCloseListener;


public class CreateRecipeFragment extends Fragment implements IngredientClickInterface, SlideClickInterface{
    private CreateRecipeBinding binding;
    private CreateRecipeViewModel mCreateRecipeViewModel;
    private CreateIngredientsAdapter Adapter;
    private ViewPager2 viewPager2;
    private CreatePageAdapter pageAdapter;
    private Function<Uri, Void> currentCallback;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = CreateRecipeBinding.inflate(inflater, container, false);
        viewPager2 = binding.createViewpager;
        View view = binding.getRoot();
        mCreateRecipeViewModel = new ViewModelProvider(requireActivity()).get(CreateRecipeViewModel.class);


        SetupBinding();

        RecyclerView ingredient_recyclerView = view.findViewById(R.id.create_ingredient_recycler_view);
        Adapter = new CreateIngredientsAdapter(new CreateIngredientsAdapter.RecipeDiff(), this);
        ingredient_recyclerView.setAdapter(Adapter);
        RecyclerView.LayoutManager ingredientLayoutManager = new GridLayoutManager(view.getContext(), 1);
        ingredient_recyclerView.setLayoutManager(ingredientLayoutManager);

        SetupViewModelRecipe();
        SetupAdapter();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void SetupViewModelRecipe(){
        Recipe loc_recipe = mCreateRecipeViewModel.recipe;
        binding.createTitle.setText(loc_recipe.getmTitle().toString());
        binding.createDescription.setText(loc_recipe.getmDescription().toString());
        binding.createCookTime.setText(loc_recipe.getmCookTime()+"");
        binding.createPortions.setText(loc_recipe.getmPortions()+"");
        binding.createIngredientDescription.setText(loc_recipe.getmIngredientsDescription().toString());
        binding.createHat1.setImageResource(R.drawable.hat_fill);
        binding.createHat2.setImageResource(loc_recipe.getmCookComplexity()>1?R.drawable.hat_fill:R.drawable.hat_empty);
        binding.createHat3.setImageResource(loc_recipe.getmCookComplexity()>2?R.drawable.hat_fill:R.drawable.hat_empty);
        binding.createImg.setImageBitmap(mCreateRecipeViewModel.recipe_img);
        SetupPager(0);
    }
    private void SetupAdapter(){
        Adapter.submitList(mCreateRecipeViewModel.recipe.getmIngredients());
    }
    private void SetupPager(int index){
        pageAdapter = new CreatePageAdapter(this, mCreateRecipeViewModel.recipe.getmSlides(), mCreateRecipeViewModel.slides_imgs, this);
        viewPager2.setAdapter(pageAdapter);
        viewPager2.setCurrentItem(index);
    }

    private void SetupBinding(){
        binding.createHat1.setOnClickListener(v -> {
            mCreateRecipeViewModel.recipe.setmCookComplexity(1);
            binding.createHat1.setImageResource(R.drawable.hat_fill);
            binding.createHat2.setImageResource(R.drawable.hat_empty);
            binding.createHat3.setImageResource(R.drawable.hat_empty);

        });
        binding.createHat2.setOnClickListener(v -> {
            mCreateRecipeViewModel.recipe.setmCookComplexity(2);
            binding.createHat1.setImageResource(R.drawable.hat_fill);
            binding.createHat2.setImageResource(R.drawable.hat_fill);
            binding.createHat3.setImageResource(R.drawable.hat_empty);
        });
        binding.createHat3.setOnClickListener(v -> {
            mCreateRecipeViewModel.recipe.setmCookComplexity(3);
            binding.createHat1.setImageResource(R.drawable.hat_fill);
            binding.createHat2.setImageResource(R.drawable.hat_fill);
            binding.createHat3.setImageResource(R.drawable.hat_fill);
        });
        binding.createBackBtn.setOnClickListener(view_f -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
        binding.createDoneBtn.setOnClickListener(view_f -> {
            CollectInfo();
            mCreateRecipeViewModel.addRecipe();
            NavHostFragment.findNavController(this).navigateUp();
        });
        binding.createTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                mCreateRecipeViewModel.recipe.setmTitle(binding.createTitle.getText().toString());

            }
        });

        binding.createDescription.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                mCreateRecipeViewModel.recipe.setmDescription(binding.createDescription.getText().toString());

            }
        });
        binding.createCookTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                String view_text = binding.createCookTime.getText().toString();
                mCreateRecipeViewModel.recipe.setmCookTime(view_text.equals("")?0:
                        Integer.parseInt(
                                view_text));

            }
        });

        binding.createPortions.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                String view_text = binding.createPortions.getText().toString();
                mCreateRecipeViewModel.recipe.setmPortions(view_text.equals("")?0:
                        Integer.parseInt(
                                view_text));
            }
        });
        binding.createImg.setOnClickListener(view -> {
            currentCallback = uri -> {
                binding.createImg.setImageURI(uri);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getApplication().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCreateRecipeViewModel.recipe_img = bitmap;
                return null;
            };
            askRecipeImg();
        });
        @SuppressLint("NotifyDataSetChanged") MyDialogCloseListener closeListener = dialog -> {
            Adapter.notifyDataSetChanged();
        };
        binding.createAddIngredient.setOnClickListener(v -> {
            SearchProductsFragment a = new SearchProductsFragment();
            Bundle mode_data = new Bundle();
            mode_data.putString("mode", "add");
            a.setArguments(mode_data);
            a.DismissListener(closeListener);
            a.show(requireActivity().getSupportFragmentManager(), "test tag");
            //NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_createRecipeFragment_to_searchProductsFragment);
        });
    }

    void CollectInfo(){
        Recipe loc_recipe = mCreateRecipeViewModel.recipe;
        loc_recipe.setmTitle(binding.createTitle.getText().toString());
        loc_recipe.setmDescription(binding.createDescription.getText().toString());
        loc_recipe.setmCookTime(Integer.parseInt(binding.createCookTime.getText().toString()));
        loc_recipe.setmPortions(Integer.parseInt(binding.createPortions.getText().toString()));
        loc_recipe.setmIngredientsDescription(binding.createIngredientDescription.getText().toString());

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onMinusClick(Ingredient ingredient) {
        mCreateRecipeViewModel.recipe.deleteIngredient(ingredient);
        SetupAdapter();
        Adapter.notifyDataSetChanged();

    }

    @Override
    public void onEdit(Ingredient ingredient, int value) {
        mCreateRecipeViewModel.recipe.editIngredientAmount(ingredient, value);
        SetupAdapter();
    }

    private void askRecipeImg() {
        int permissionCheck = ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            startGallery();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
    private void startGallery() {
        Intent cameraIntent = new Intent();
        cameraIntent.setType("image/*");
        cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            someActivityResultLauncher.launch(cameraIntent);
            }
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri returnUri = data.getData();
                    currentCallback.apply(returnUri);
                }
            });




    @Override
    public void onImgClick(int index) {
        Log.e("qwe", "onImgClick");
        currentCallback = uri -> {{
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getApplication().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* Fragment page = requireActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.create_viewpager + ":" + viewPager2.getCurrentItem());
            ImageView img = page.getView().findViewById(R.id.recipe_preview_img);
            img.setImageURI(uri);*/
            mCreateRecipeViewModel.slides_imgs.set(index, bitmap);
            SetupPager(index);
            return null;
        }};
        askRecipeImg();
    }

    @Override
    public void onDeleteClick(int index) {
        mCreateRecipeViewModel.recipe.mSlides.remove(index);
        mCreateRecipeViewModel.slides_imgs.remove(index);
        SetupPager(index);
        Log.e("qwe", "onDeleteClick");
    }

    @Override
    public void onSlideTextEdit(int index, String text) {
        mCreateRecipeViewModel.recipe.mSlides.get(index).setDescription(text);
        Log.e("qwe", "onSlideTextEdit " + text);
    }

    @Override
    public void onAddClick(int index) {
        mCreateRecipeViewModel.recipe.mSlides.add(new Slide("", ""));
        mCreateRecipeViewModel.slides_imgs.add(null);
        SetupPager(index);
        Log.e("qwe", "onAddClick");
    }
}