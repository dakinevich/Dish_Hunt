package ru.example.dishhunt.ui.recipe_creator;

public interface SlideClickInterface {
    void onImgClick(int index);
    void onDeleteClick(int index);
    void onSlideTextEdit(int index, String text);
    void onAddClick(int index);

}
