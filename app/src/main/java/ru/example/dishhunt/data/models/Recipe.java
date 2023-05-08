package ru.example.dishhunt.data.models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    static class IngredientRow{
        String productName;
        int amount;
        String amountType;
    }

    private int id;
    private boolean mIsSaved, mIsLiked;
    private int mAuthorId, mViews, mLikes, mPrice, mPortions, mCookTime, mCookComplexity, mCalories, mProteins, mFats, mCarbohydrates;
    private String mTitle, mImgSrc, mDescription, mAuthorsName;
    private ArrayList<IngredientRow> mIngredientRow;
    private List<Comment> mComments;
    public  Recipe(){}

    public Recipe(int id, boolean mIsSaved, boolean mIsLiked, int mAuthorId, int mViews, int mLikes, int mPrice, int mPortions, int mCookTime, int mCookComplexity, int mCalories, int mProteins, int mFats, int mCarbohydrates, String mTitle, String mImgSrc, String mDescription, String mAuthorsName) {
        this.id = id;
        this.mIsSaved = mIsSaved;
        this.mIsLiked = mIsLiked;
        this.mAuthorId = mAuthorId;
        this.mViews = mViews;
        this.mLikes = mLikes;
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
        this.mAuthorsName = mAuthorsName;
        this.mIngredientRow = mIngredientRow;
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

    public boolean ismIsLiked() {
        return mIsLiked;
    }

    public void setmIsLiked(boolean mIsLiked) {
        this.mIsLiked = mIsLiked;
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

    public int getmLikes() {
        return mLikes;
    }

    public void setmLikes(int mLikes) {
        this.mLikes = mLikes;
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

    public String getmAuthorsName() {
        return mAuthorsName;
    }

    public void setmAuthorsName(String mAuthorsName) {
        this.mAuthorsName = mAuthorsName;
    }

    public ArrayList<IngredientRow> getmIngredientRow() {
        return mIngredientRow;
    }

    public void setmIngredientRow(ArrayList<IngredientRow> mIngredientRow) {
        this.mIngredientRow = mIngredientRow;
    }
}
