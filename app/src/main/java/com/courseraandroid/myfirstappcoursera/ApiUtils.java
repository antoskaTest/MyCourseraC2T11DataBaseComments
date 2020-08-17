package com.courseraandroid.myfirstappcoursera;

import com.courseraandroid.myfirstappcoursera.model.converter.DataConverterFactory;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static Gson gson;
    private static AcademyApi academyApi;

    public static OkHttpClient getBasicAuthClient(final String email, final String password, boolean newInstance){
        if(newInstance || okHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.authenticator(new Authenticator() {
                @Nullable
                @Override
                public Request authenticate(@Nullable Route route, @NotNull Response response) throws IOException {
                    String credentials = Credentials.basic(email, password);
                    return response.request().newBuilder().header("Authorization", credentials).build();
                }
            });
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    public static Retrofit getRetrofit(){
        if(gson == null){
            gson = new Gson();
        }
       if(retrofit == null){
            retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.SERVER_URL)
                            //need for interceptors
                            .client(getBasicAuthClient("","", false))
                            .addConverterFactory(new DataConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
        }
        return retrofit;
    }

    public static AcademyApi getApi() {
        if(academyApi == null){
            academyApi = getRetrofit().create(AcademyApi.class);
        }
        return academyApi;

    }
    public static void resetRetrofit(){
        retrofit = null;
        academyApi = null;
    }
}
