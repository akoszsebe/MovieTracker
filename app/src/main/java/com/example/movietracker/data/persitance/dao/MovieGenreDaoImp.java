package com.example.movietracker.data.persitance.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.persitance.AppDatabase;
import com.example.movietracker.data.persitance.dao.base.BaseDao;
import com.example.movietracker.data.persitance.entity.DbGenre;
import com.example.movietracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MovieGenreDaoImp extends BaseDao implements MovieGenreDao {

    @Inject
    public MovieGenreDaoImp(AppDatabase appDatabase) {
        super(appDatabase);
    }

    public void insertGenre(Genre genre) {
        // get writable database as we want to write data
        SQLiteDatabase db = appDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbGenre.COLUMN_ID, genre.getId());
        values.put(DbGenre.COLUMN_NAME, genre.getName());
        values.put(DbGenre.COLUMN_SELECTED, 0);

        // insert row
        long id = db.insert(DbGenre.TABLE_NAME, null, values);

        // close db connection
        db.close();
    }

    public void updateGenre(Genre genre) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbGenre.COLUMN_NAME, genre.getName());
        values.put(DbGenre.COLUMN_SELECTED, Utils.boolToInt(genre.isSelected()));

        // updating row
        db.update(DbGenre.TABLE_NAME, values, DbGenre.COLUMN_ID + " = ?",
                new String[]{String.valueOf(genre.getId())});
    }

    public List<Genre> getAllMovieGenres() {
        List<Genre> genres = new ArrayList<>();

        SQLiteDatabase db = appDatabase.getWritableDatabase();
        Cursor cursor = db.rawQuery(DbGenre.SELECT_QUERY, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Genre dbGenre = new Genre();
                dbGenre.setId(cursor.getInt(cursor.getColumnIndex(DbGenre.COLUMN_ID)));
                dbGenre.setName(cursor.getString(cursor.getColumnIndex(DbGenre.COLUMN_NAME)));
                dbGenre.setSelected(Utils.intToBool(cursor.getInt(cursor.getColumnIndex(DbGenre.COLUMN_SELECTED))));

                genres.add(dbGenre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();
        return genres;
    }

    public void insertMovieGenres(List<Genre> genres) {
        for (Genre genre : genres) {
            insertGenre(genre);
        }
    }

    @Override
    public void updateGenres(List<Genre> genres) {
        for (Genre genre : genres) {
            updateGenre(genre);
        }
    }
}
