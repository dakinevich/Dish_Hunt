package ru.example.dishhunt.data.data_sources.room.entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
