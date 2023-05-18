package ru.example.dishhunt.ui.recipe.view_holders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.models.Comment;
import ru.example.dishhunt.ui.recipe.CommentClickInterface;

public class RecipeCommentsViewHolder extends RecyclerView.ViewHolder {
    private TextView commentViewTitle;
    private ImageView commentViewImg;
    private TextView commentViewName;
    private TextView commentViewTime;
    private final CommentClickInterface commentClickInterface;



    private RecipeCommentsViewHolder(@NonNull View itemView, CommentClickInterface commentClickInterface) {
        super(itemView);
        commentViewTitle = itemView.findViewById(R.id.comment_text);
        commentViewImg = itemView.findViewById(R.id.comment_img);
        commentViewName = itemView.findViewById(R.id.comment_name);
        commentViewTime = itemView.findViewById(R.id.comment_time);
        this.commentClickInterface = commentClickInterface;
    }


    @SuppressLint("SetTextI18n")
    public void bind(Comment comment) {
        itemView.setOnClickListener(v -> commentClickInterface.onClick(comment.getAuthorId()));
        commentViewTitle.setText(comment.getText());
    }

    public static RecipeCommentsViewHolder create(ViewGroup parent, CommentClickInterface commentClickInterface) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment, parent, false);
        return new RecipeCommentsViewHolder(view, commentClickInterface);
    }
}
