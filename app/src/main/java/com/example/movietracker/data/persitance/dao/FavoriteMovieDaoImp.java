package com.example.movietracker.data.persitance.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.movietracker.data.persitance.AppDatabase;
import com.example.movietracker.data.persitance.dao.base.BaseDao;
import com.example.movietracker.data.persitance.entity.DbFavorite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoriteMovieDaoImp extends BaseDao implements FavoriteMovieDao {

    @Inject
    public FavoriteMovieDaoImp(AppDatabase appDatabase) {
        super(appDatabase);
    }

    @Override
    public void insertFavorite(String movieId) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbFavorite.COLUMN_ID, movieId);

        // insert row
        long id = db.insertWithOnConflict(DbFavorite.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        // close db connection
        db.close();

    }

    @Override
    public void deleteFavorite(String movieId) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        db.delete(DbFavorite.TABLE_NAME, DbFavorite.COLUMN_ID + "=" + movieId, null);
    }

    @Override
    public List<String> getAllFavoriteMovieIds() {
        List<String> movieIds = new ArrayList<>();

        SQLiteDatabase db = appDatabase.getWritableDatabase();
        Cursor cursor = db.rawQuery(DbFavorite.SELECT_QUERY, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String movieId = cursor.getString(cursor.getColumnIndex(DbFavorite.COLUMN_ID));
                movieIds.add(movieId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();
        return movieIds;
    }
}
