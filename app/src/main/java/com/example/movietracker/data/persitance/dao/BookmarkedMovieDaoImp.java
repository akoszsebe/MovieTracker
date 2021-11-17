package com.example.movietracker.data.persitance.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.movietracker.data.networking.models.Movie;
import com.example.movietracker.data.persitance.AppDatabase;
import com.example.movietracker.data.persitance.dao.base.BaseDao;
import com.example.movietracker.data.persitance.entity.DbBookmarkedMovie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class BookmarkedMovieDaoImp extends BaseDao implements BookmarkedMovieDao {

    @Inject
    public BookmarkedMovieDaoImp(AppDatabase appDatabase) {
        super(appDatabase);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void insertMovie(Movie movie) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbBookmarkedMovie.COLUMN_ID, movie.getId());
        values.put(DbBookmarkedMovie.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(DbBookmarkedMovie.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(DbBookmarkedMovie.COLUMN_GENRE_NAMES, String.join(",", movie.getGenreNames()));
        values.put(DbBookmarkedMovie.COLUMN_TITLE, movie.getTitle());
        values.put(DbBookmarkedMovie.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());

        // insert row
        long id = db.insertWithOnConflict(DbBookmarkedMovie.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        // close db connection
        db.close();
    }

    @Override
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        db.delete(DbBookmarkedMovie.TABLE_NAME, DbBookmarkedMovie.COLUMN_ID + "=" + movie.getId(), null);
    }

    @Override
    public List<Movie> getAllBookmarkedMovies() {
        List<Movie> movies = new ArrayList<>();

        SQLiteDatabase db = appDatabase.getWritableDatabase();
        Cursor cursor = db.rawQuery(DbBookmarkedMovie.SELECT_QUERY, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getString(cursor.getColumnIndex(DbBookmarkedMovie.COLUMN_ID)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(DbBookmarkedMovie.COLUMN_POSTER_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(DbBookmarkedMovie.COLUMN_RELEASE_DATE)));
                movie.setGenreNames(Arrays.asList(cursor.getString(cursor.getColumnIndex(DbBookmarkedMovie.COLUMN_GENRE_NAMES)).split(",")));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(DbBookmarkedMovie.COLUMN_TITLE)));
                movie.setVoteAverage(cursor.getFloat(cursor.getColumnIndex(DbBookmarkedMovie.COLUMN_VOTE_AVERAGE)));
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();
        return movies;
    }
}
