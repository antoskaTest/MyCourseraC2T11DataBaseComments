package com.courseraandroid.myfirstappcoursera.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import retrofit2.SkipCallbackExecutor;

@Entity(foreignKeys = {
        @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "albumId"),
        @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "songId")})
public class AlbumSong {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "albumId")
    @SerializedName("albumId")
    private int albumId;

    @ColumnInfo(name = "songId")
    @SerializedName("songId")
    private int songId;

    public AlbumSong(int id, int albumId, int songId) {
        this.id = id;
        this.albumId = albumId;
        this.songId = songId;
    }

//    public AlbumSong(int albumId, int songId) {
//        this.albumId = albumId;
//        this.songId = songId;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }


}
