package com.courseraandroid.myfirstappcoursera.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.courseraandroid.myfirstappcoursera.model.Album;
import com.courseraandroid.myfirstappcoursera.model.AlbumSong;
import com.courseraandroid.myfirstappcoursera.model.Song;

import java.util.List;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    @Query("select * from album")
    List<Album> getAlbums();

    @Query("select * from album where id = :albumId")
    Album getAlbum(int albumId);

    @Delete
    void deleteAlbum(Album album);

    @Query("delete from album where id = :albumId")
    void deleteAlbumById(int albumId);

    //----------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Query("select * from song")
    Album getSongs();

    //@Query("select * from song join albumsong on song.id = albumsong.songId where albumsong.albumId = :albumId")
    @Query("select song.id, song.name, song.duration " +
            "from song join albumsong " +
            "on song.id = albumsong.songId " +
            "where albumsong.albumId = :albumId " +
            "order by song.id")
    List<Song> getSongByAlbumId(int albumId);

    //-----------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumSong(AlbumSong albumSong);
}
