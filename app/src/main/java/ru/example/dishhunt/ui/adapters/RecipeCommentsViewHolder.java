package ru.example.dishhunt.ui.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Comment;

public class RecipeCommentsViewHolder extends RecyclerView.ViewHolder {
    private TextView commentViewTitle;
    private ImageView commentViewImg;
    private TextView commentViewName;
    private TextView commentViewTime;


    private RecipeCommentsViewHolder(@NonNull View itemView) {
        super(itemView);
        commentViewTitle = itemView.findViewById(R.id.comment_text);
        commentViewImg = itemView.findViewById(R.id.comment_img);
        commentViewName = itemView.findViewById(R.id.comment_name);
        commentViewTime = itemView.findViewById(R.id.comment_time);
    }


    @SuppressLint("SetTextI18n")
    public void bind(Comment comment) {
        commentViewTitle.setText(comment.getText());
    }

    static RecipeCommentsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment, parent, false);
        return new RecipeCommentsViewHolder(view);
    }
}
