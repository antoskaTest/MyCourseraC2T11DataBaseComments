package com.courseraandroid.myfirstappcoursera.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Comment implements Serializable {
    /**
     * id : 0
     * album_id : 0
     * text : string
     * author : string
     * timestamp : 2020-08-25T15:59:26.556Z
     */
    @SerializedName("id")
    private int id;
    @SerializedName("album_id")
    private int album_id;
    @SerializedName("text")
    private String text;
    private String author;
    private String timestamp;

    public Comment(int album_id, String text) {
        this.album_id = album_id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                album_id == comment.album_id &&
                Objects.equals(author, comment.author) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(timestamp, comment.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, album_id, author, text, timestamp);
    }

}
