package com.courseraandroid.myfirstappcoursera.albums;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courseraandroid.myfirstappcoursera.R;
import com.courseraandroid.myfirstappcoursera.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsHolder> {

    @NonNull
    private final List<Album> mAlbums = new ArrayList<>();
    private final OnItemClickListener mOnItemClickListener;


    public AlbumsAdapter(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AlbumsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_album, parent, false);
        return new AlbumsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsHolder holder, int position) {
        holder.bind(mAlbums.get(position), mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public void addData(List<Album> data, boolean isRefreshed){
        if(isRefreshed){
            mAlbums.clear();
        }
        mAlbums.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Album album);
    }
}
