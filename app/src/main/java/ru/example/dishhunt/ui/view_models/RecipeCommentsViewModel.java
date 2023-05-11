package ru.example.dishhunt.ui.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.data.models.Ingredient;
import ru.example.dishhunt.data.models.Recipe;
import ru.example.dishhunt.data.repositories.RecipeRepository;

public class RecipeCommentsViewModel extends AndroidViewModel {
    private RecipeRepository mRepository;


    public RecipeCommentsViewModel(Application application) {
        super(application);
        mRepository = new RecipeRepository(application);
    }
    public LiveData<List<Comment>> getRecipeCommentsById(int id) { return mRepository.getRecipeCommentsById(id); }
    public void addComment(Comment comment) {
        mRepository.insertComment(comment);
    }
}
