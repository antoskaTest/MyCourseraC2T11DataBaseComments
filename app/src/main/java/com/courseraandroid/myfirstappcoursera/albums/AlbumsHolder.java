package com.courseraandroid.myfirstappcoursera.albums;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courseraandroid.myfirstappcoursera.R;
import com.courseraandroid.myfirstappcoursera.model.Album;

public class AlbumsHolder extends RecyclerView.ViewHolder {
    private TextView mTitle;
    private  TextView mReleaseDate;

    public AlbumsHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_title);
        mReleaseDate = itemView.findViewById(R.id.tv_release_date);
    }

    public void bind(Album item, AlbumsAdapter.OnItemClickListener onItemClickListener){
        mTitle.setText(item.getName());
        mReleaseDate.setText(item.getRelease_date());
        if(onItemClickListener != null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }
}
