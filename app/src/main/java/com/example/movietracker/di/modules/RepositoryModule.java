package com.example.movietracker.di.modules;

import android.content.Context;

import com.example.movietracker.data.networking.api.TheMovieDbAPI;
import com.example.movietracker.data.networking.api.YoutubeAPI;
import com.example.movietracker.data.persitance.AppDatabase;
import com.example.movietracker.data.persitance.dao.BookmarkedMovieDao;
import com.example.movietracker.data.persitance.dao.BookmarkedMovieDaoImp;
import com.example.movietracker.data.persitance.dao.FavoriteMovieDao;
import com.example.movietracker.data.persitance.dao.FavoriteMovieDaoImp;
import com.example.movietracker.data.persitance.dao.MovieGenreDao;
import com.example.movietracker.data.persitance.dao.MovieGenreDaoImp;
import com.example.movietracker.data.persitance.dao.VideoProgressDao;
import com.example.movietracker.data.persitance.dao.VideoProgressDaoImp;
import com.example.movietracker.data.repository.MovieRepository;
import com.example.movietracker.data.repository.VideoRepository;
import com.example.movietracker.data.repository.YoutubeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    private Context mAppContext;

    public RepositoryModule(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    @Provides
    @Singleton
    YoutubeRepository provideYoutubeRepository(YoutubeAPI youtubeAPI) {
        return new YoutubeRepository(youtubeAPI);
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(TheMovieDbAPI theMovieDbAPI, MovieGenreDao movieGenreDao, BookmarkedMovieDao bookmarkedMovieDao, FavoriteMovieDao favoriteMovieDao) {
        return new MovieRepository(theMovieDbAPI, movieGenreDao, bookmarkedMovieDao, favoriteMovieDao);
    }

    @Provides
    @Singleton
    VideoRepository provideVideoRepository(TheMovieDbAPI theMovieDbAPI, VideoProgressDao videoProgressDao) {
        return new VideoRepository(theMovieDbAPI, videoProgressDao);
    }

    @Provides
    MovieGenreDao provideMovieGenreDao(AppDatabase appDatabase) {
        return new MovieGenreDaoImp(appDatabase);
    }

    @Provides
    VideoProgressDao provideVideoProgressDao(AppDatabase appDatabase) {
        return new VideoProgressDaoImp(appDatabase);
    }

    @Provides
    BookmarkedMovieDao provideBookmarkedMovieDao(AppDatabase appDatabase) {
        return new BookmarkedMovieDaoImp(appDatabase);
    }

    @Provides
    FavoriteMovieDao provideFavoriteMovieDao(AppDatabase appDatabase) {
        return new FavoriteMovieDaoImp(appDatabase);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return new AppDatabase(mAppContext);
    }
}
