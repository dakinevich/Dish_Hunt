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
    private float Calories;
    @NonNull
    private float Proteins;
    @NonNull
    private float Fats ;
    @NonNull
    private float Carbohydrates;
    @NonNull
    private int Weight;
    @NonNull
    private float Price;



    public ProductEntity(){}

    public ProductEntity(@NonNull String name, @NonNull String amountValue, float calories, float proteins, float fats, float carbohydrates, int weight, float price) {
        Name = name;
        AmountValue = amountValue;
        Calories = calories;
        Proteins = proteins;
        Fats = fats;
        Carbohydrates = carbohydrates;
        Weight = weight;
        Price = price;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
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

    public float getCalories() {
        return Calories;
    }

    public void setCalories(float calories) {
        Calories = calories;
    }

    public float getProteins() {
        return Proteins;
    }

    public void setProteins(float proteins) {
        Proteins = proteins;
    }

    public float getFats() {
        return Fats;
    }

    public void setFats(float fats) {
        Fats = fats;
    }

    public float getCarbohydrates() {
        return Carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        Carbohydrates = carbohydrates;
    }
}
