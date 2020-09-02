package com.courseraandroid.myfirstappcoursera.comments;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.courseraandroid.myfirstappcoursera.R;
import com.courseraandroid.myfirstappcoursera.model.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentsHolder extends RecyclerView.ViewHolder {
    private TextView mAuthor;
    private TextView mText;
    private TextView mTime;
    DateFormat df_datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    DateFormat df_date = new SimpleDateFormat("dd.MM.yyyy");
    DateFormat df_time = new SimpleDateFormat("HH:mm:ss");

    public CommentsHolder(View view) {
        super(view);
        mAuthor = view.findViewById(R.id.tv_author);
        mText = view.findViewById(R.id.tv_text);
        mTime = view.findViewById(R.id.tv_time);
    }


    public void bind(Comment comment) {
        mAuthor.setText(comment.getAuthor());
        mText.setText(comment.getText());
        try {
            Date date = df_datetime.parse(comment.getTimestamp());
            if((new Date()).getTime() - date.getTime() < 1000*60*60*24)
                mTime.setText(df_time.format(date));
            else
                mTime.setText(df_date.format(date));
        } catch (Exception ex) {
            //Если не разберем, то выводим что получили
            mTime.setText(comment.getTimestamp());
            ex.printStackTrace();
        }
    }
}
