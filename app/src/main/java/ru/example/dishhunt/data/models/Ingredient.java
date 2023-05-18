package ru.example.dishhunt.data.models;


import java.util.Objects;

import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;

public class Ingredient {
    private int id, RecipeId;
    private int Amount;
    private ProductEntity Product;
    public Ingredient(){}
    public Ingredient(int recipeId, int amount, ProductEntity product) {
        RecipeId = recipeId;
        Amount = amount;
        Product = product;
    }

    public ProductEntity getProduct() {
        return Product;
    }

    public int getCalories(){
        return Product.getCalories()*Amount;
    }

    public int getProteins() {
        return Product.getProteins()*Amount;
    }

    public int getFats() {
        return Product.getFats()*Amount;
    }

    public int getCarbohydrates() {
        return Product.getCarbohydrates()*Amount;
    }

    public int getWeight() {
        return Product.getWeight()*Amount;
    }

    public int getPrice(){
        return Product.getPrice()*Amount;
    }

    public void setProduct(ProductEntity product) {
        Product = product;
    }


    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public String getName() {
        return Product.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id && RecipeId == that.RecipeId && Amount == that.Amount && Product.getId() == that.Product.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, RecipeId, Amount, Product);
    }
}
