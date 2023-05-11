package ru.example.dishhunt.data.models;

import java.util.ArrayList;
import java.util.List;

import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;

public class Recipe {


    private int id;
    private boolean mIsSaved;
    private int mSaveCount, mAuthorId, mViews, mPrice, mPortions, mCookTime, mCookComplexity, mCalories, mProteins, mFats, mCarbohydrates;
    private String mTitle, mImgSrc, mDescription, mIngredientsDescription;
    private List<Comment> mComments;
    private List<Ingredient> mIngredients;
    public  Recipe(){}

    public Recipe(int id, boolean mIsSaved, int mAuthorId, int mViews, int mSaveCount, int mPrice, int mPortions, int mCookTime, int mCookComplexity, int mCalories, int mProteins, int mFats, int mCarbohydrates, String mTitle, String mImgSrc, String mDescription, List<Ingredient> mIngredients, String mIngredientsDescription) {
        this.id = id;
        this.mIsSaved = mIsSaved;
        this.mAuthorId = mAuthorId;
        this.mViews = mViews;
        this.mPrice = mPrice;
        this.mPortions = mPortions;
        this.mCookTime = mCookTime;
        this.mCookComplexity = mCookComplexity;
        this.mCalories = mCalories;
        this.mProteins = mProteins;
        this.mFats = mFats;
        this.mCarbohydrates = mCarbohydrates;
        this.mTitle = mTitle;
        this.mImgSrc = mImgSrc;
        this.mDescription = mDescription;
        this.mIngredients = mIngredients;
        this.mSaveCount = mSaveCount;
        this.mIngredientsDescription = mIngredientsDescription;
    }

    public boolean addIngredient(ProductEntity product){
        for (int i = 0; i<mIngredients.size(); i++ ){
            if (mIngredients.get(i).getProduct().getId() == product.getId()){
                return false;
            }
        }
        this.mIngredients.add(new Ingredient(id, 1, product));
        return true;
    }

    public String getmIngredientsDescription() {
        return mIngredientsDescription;
    }

    public void setmIngredientsDescription(String mIngredientsDescription) {
        this.mIngredientsDescription = mIngredientsDescription;
    }

    public int getmSaveCount() {
        return mSaveCount;
    }

    public void setmSaveCount(int mSaveCount) {
        this.mSaveCount = mSaveCount;
    }

    public List<Comment> getmComments() {
        return mComments;
    }

    public void setmComments(List<Comment> mComments) {
        this.mComments = mComments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean ismIsSaved() {
        return mIsSaved;
    }

    public void setmIsSaved(boolean mIsSaved) {
        this.mIsSaved = mIsSaved;
    }

    public int getmAuthorId() {
        return mAuthorId;
    }

    public void setmAuthorId(int mAuthorId) {
        this.mAuthorId = mAuthorId;
    }

    public int getmViews() {
        return mViews;
    }

    public void setmViews(int mViews) {
        this.mViews = mViews;
    }


    public int getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public int getmPortions() {
        return mPortions;
    }

    public void setmPortions(int mPortions) {
        this.mPortions = mPortions;
    }

    public int getmCookTime() {
        return mCookTime;
    }

    public void setmCookTime(int mCookTime) {
        this.mCookTime = mCookTime;
    }

    public int getmCookComplexity() {
        return mCookComplexity;
    }

    public void setmCookComplexity(int mCookComplexity) {
        this.mCookComplexity = mCookComplexity;
    }

    public int getmCalories() {
        return mCalories;
    }

    public void setmCalories(int mCalories) {
        this.mCalories = mCalories;
    }

    public int getmProteins() {
        return mProteins;
    }

    public void setmProteins(int mProteins) {
        this.mProteins = mProteins;
    }

    public int getmFats() {
        return mFats;
    }

    public void setmFats(int mFats) {
        this.mFats = mFats;
    }

    public int getmCarbohydrates() {
        return mCarbohydrates;
    }

    public void setmCarbohydrates(int mCarbohydrates) {
        this.mCarbohydrates = mCarbohydrates;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImgSrc() {
        return mImgSrc;
    }

    public void setmImgSrc(String mImgSrc) {
        this.mImgSrc = mImgSrc;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public List<Ingredient> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public void editIngredientAmount(Ingredient ingredient, int amount){
        for (int i = 0; i<mIngredients.size(); i++ ){
            if (ingredient.equals(mIngredients.get(i))){
                mIngredients.get(i).setAmount(amount);
                break;
            }
        }
    }

    public void deleteIngredient(Ingredient ingredient){
        for (int i = 0; i<mIngredients.size(); i++ ){
            if (ingredient.equals(mIngredients.get(i))){
                mIngredients.remove(i);
                break;
            }
        }
    }
}
