package ru.example.dishhunt.ui;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Collections;
import java.util.List;

import ru.example.dishhunt.data.models.Recipe;

public class CombinedLiveData extends MediatorLiveData<Pair<List<Recipe>, List<Integer>>> {
    private List<Recipe> recipes = Collections.emptyList();
    private List<Integer> savedIds = Collections.emptyList();

    public CombinedLiveData(LiveData<List<Recipe>> ld1, LiveData<List<Integer>> ld2) {
        setValue(Pair.create(recipes, savedIds));

        addSource(ld1, recipes -> {
            if(recipes != null) {
                this.recipes = recipes;
            }
            setValue(Pair.create(recipes, savedIds));
        });

        addSource(ld2, savedIds -> {
            if(savedIds != null) {
                this.savedIds = savedIds;
            }
            setValue(Pair.create(recipes, savedIds));
        });
    }
}