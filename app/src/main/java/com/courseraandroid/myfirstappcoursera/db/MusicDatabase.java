package com.courseraandroid.myfirstappcoursera.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.courseraandroid.myfirstappcoursera.model.Album;
import com.courseraandroid.myfirstappcoursera.model.Song;

@Database(entities = {Album.class, Song.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
