package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(tableName = "product_table")

public class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String Name;
    @NonNull
    private String AmountValue;
    @NonNull
    private int Calories;
    @NonNull
    private int Proteins;
    @NonNull
    private int Fats ;
    @NonNull
    private int Carbohydrates;
    @NonNull
    private int Weight;



    public ProductEntity(){}

    public ProductEntity(@NonNull String name, @NonNull String amountValue, int calories, int proteins, int fats, int carbohydrates, int weight) {
        Name = name;
        AmountValue = amountValue;
        Calories = calories;
        Proteins = proteins;
        Fats = fats;
        Carbohydrates = carbohydrates;
        Weight = weight;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return Name;
    }

    public void setName(@NonNull String name) {
        Name = name;
    }

    @NonNull
    public String getAmountValue() {
        return AmountValue;
    }

    public void setAmountValue(@NonNull String amountValue) {
        AmountValue = amountValue;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public int getProteins() {
        return Proteins;
    }

    public void setProteins(int proteins) {
        Proteins = proteins;
    }

    public int getFats() {
        return Fats;
    }

    public void setFats(int fats) {
        Fats = fats;
    }

    public int getCarbohydrates() {
        return Carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        Carbohydrates = carbohydrates;
    }
}
