package ru.example.dishhunt.ui.profile.view_models;

import android.app.Application;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Collections;
import java.util.List;

import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.models.User;
import ru.example.dishhunt.data.repositories.RecipeRepository;


public class ProfileViewModel extends AndroidViewModel {

    private RecipeRepository mRepository;
    private final LiveData<List<Recipe>> mAllRecipes;


    public ProfileViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
        Log.e("qwe", "creating repos");
        mAllRecipes = mRepository.getAllRecipes();
    }

    public LiveData<User> getProfile(int user_id) {
        Log.e("qwe", "requesting usr");
        LiveData<User> u =  mRepository.getUser(user_id);
        return u; }

}