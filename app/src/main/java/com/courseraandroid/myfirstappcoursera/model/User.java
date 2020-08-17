package com.courseraandroid.myfirstappcoursera.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("name")
    private String mName;
    @SerializedName("email")
    private String mLogin;
    @SerializedName("password")
    private String mPassword;
    private String mPhotoUri;
    private boolean mHasSuccessLogin;

    public User(String login, String name, String password) {
        mLogin = login;
        mPassword = password;
        mName = name;
    }

    public String getmLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name){
        mName = name;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }

    public boolean hasSuccessLogin() {
        return mHasSuccessLogin;
    }

    public void setHasSuccessLogin(boolean hasSuccessLogin) {
        mHasSuccessLogin = hasSuccessLogin;
    }
}
