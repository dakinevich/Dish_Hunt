package ru.example.dishhunt.ui.adapters;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.ui.ViewHolders.RecipeCommentsViewHolder;

public class RecipeCommentsAdapter extends ListAdapter<Comment, RecipeCommentsViewHolder> {


    public RecipeCommentsAdapter(@NonNull DiffUtil.ItemCallback<Comment> diffCallback) {
        super(diffCallback);
    }

    @Override
    public RecipeCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RecipeCommentsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCommentsViewHolder holder, int position) {
        Comment current = getItem(position);
        holder.bind(current);
    }

    public static class RecipeDiff extends DiffUtil.ItemCallback<Comment> {

        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getText().equals(newItem.getText());
        }
    }
}
