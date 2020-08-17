package com.courseraandroid.myfirstappcoursera;

import com.courseraandroid.myfirstappcoursera.model.Album;
import com.courseraandroid.myfirstappcoursera.model.Song;
import com.courseraandroid.myfirstappcoursera.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AcademyApi {
    @POST("registration")
    Completable registration(@Body User user);

    @GET("user")
    Single<User> authentication();
    //Call<UserForAuth> authentication();

    @GET("albums")
    Single<List<Album>> getAlbums();

    @GET("albums/{id}")
    Single<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<List<Song>> getSongs();

    @GET("songs/{id}")
    Call<Song> getSong(@Path("id") int id);
}
