package com.courseraandroid.myfirstappcoursera.comments;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.courseraandroid.myfirstappcoursera.R;
import com.courseraandroid.myfirstappcoursera.model.Comment;

public class CommentsHolder extends RecyclerView.ViewHolder {
    private TextView mAuthor;
    private TextView mText;
    private TextView mTime;

    public CommentsHolder(View view) {
        super(view);
        mAuthor = view.findViewById(R.id.tv_author);
        mText = view.findViewById(R.id.tv_text);
        mTime = view.findViewById(R.id.tv_time);
    }


    public void bind(Comment comment) {
        mAuthor.setText(comment.getAuthor());
        mText.setText(comment.getText());
        mTime.setText(comment.getTimestamp());
    }
}
