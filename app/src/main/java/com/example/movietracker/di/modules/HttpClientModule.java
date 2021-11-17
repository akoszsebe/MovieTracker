package com.example.movietracker.di.modules;

import android.app.Application;

import com.example.movietracker.data.networking.api.TheMovieDbAPI;
import com.example.movietracker.data.networking.api.YoutubeAPI;
import com.example.movietracker.di.AppScope;
import com.example.movietracker.utils.Constants;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpClientModule {

    private static final long DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Provides
    @AppScope
    OkHttpClient provideOkHttpClient(Application app, HttpLoggingInterceptor loggingInterceptor, StethoInterceptor stethoInterceptor) {
        File cacheDir = new File(app.getCacheDir(), "http");
        return new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .cache(new okhttp3.Cache(cacheDir, DISK_CACHE_SIZE))
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .build();
    }

    @Provides
    @Named("movieDB") // Name is used in case a second Retrofit api is provided.
    @AppScope
    Retrofit provideRetrofitRestAdapter(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Named("youtube") // Name is used in case a second Retrofit api is provided.
    @AppScope
    Retrofit provideRetrofitRestAdapterForYoutube(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.YOUTUBE_API_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    TheMovieDbAPI provideMoviesDBApi(@Named("movieDB") Retrofit restAdapter) {
        return restAdapter.create(TheMovieDbAPI.class);
    }

    @Provides
    YoutubeAPI provideYoutubeApi(@Named("youtube") Retrofit restAdapter) {
        return restAdapter.create(YoutubeAPI.class);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
