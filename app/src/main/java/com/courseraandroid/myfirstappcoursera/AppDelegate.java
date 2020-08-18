package com.courseraandroid.myfirstappcoursera;

import android.app.Application;

import androidx.room.Room;

import com.courseraandroid.myfirstappcoursera.db.MusicDatabase;

public class AppDelegate extends Application {
    private MusicDatabase mMusicDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mMusicDatabase = Room.databaseBuilder(getApplicationContext(), MusicDatabase.class, "music_database")
                //.allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public MusicDatabase getMusicDatabase() {
        return mMusicDatabase;
    }
}
