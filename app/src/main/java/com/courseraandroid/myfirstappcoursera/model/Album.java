package com.courseraandroid.myfirstappcoursera.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Album implements Serializable {

    /**
     * data : {"id":0,"name":"string","songs":[{"id":0,"name":"string","duration":"string"}],"release_date":"2020-08-06T15:52:03.253Z"}
     */

        /**
         * id : 0
         * name : string
         * songs : [{"id":0,"name":"string","duration":"string"}]
         * release_date : 2020-08-06T15:52:03.253Z
         */
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        private int id;

        @ColumnInfo(name = "name")
        @SerializedName("name")
        private String name;

        @ColumnInfo(name = "release")
        @SerializedName("release_date")
        private String release_date;


        @SerializedName("songs")
        @Ignore
        private List<Song> songs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public List<Song> getSongs() {
            return songs;
        }

        public void setSongs(List<Song> songs) {
            this.songs = songs;
        }


}
