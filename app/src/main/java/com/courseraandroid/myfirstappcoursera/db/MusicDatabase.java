package com.courseraandroid.myfirstappcoursera.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.courseraandroid.myfirstappcoursera.model.Album;

@Database(entities = {Album.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
