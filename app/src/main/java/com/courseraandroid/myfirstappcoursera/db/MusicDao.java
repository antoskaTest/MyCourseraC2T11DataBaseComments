package com.courseraandroid.myfirstappcoursera.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.courseraandroid.myfirstappcoursera.model.Album;

import java.util.List;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Query("select * from album")
    List<Album> getAlbums();

    @Delete
    void deleteAlbum(Album album);

    @Query("delete from album where id = :albumId")
    void deleteAlbumById(int albumId);
}
