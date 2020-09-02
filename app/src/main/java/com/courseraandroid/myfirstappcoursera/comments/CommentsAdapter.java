package com.courseraandroid.myfirstappcoursera.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courseraandroid.myfirstappcoursera.R;
import com.courseraandroid.myfirstappcoursera.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsHolder> {
    private final List<Comment> mComments = new ArrayList<>();

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_coment, parent, false);
        return new CommentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {
        holder.bind(mComments.get(position));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void addData(List<Comment> data, boolean isRefreshed){
        if(isRefreshed){
            mComments.clear();
        }
        mComments.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(Comment comment){
        mComments.add(comment);
        notifyDataSetChanged();
    }
}
