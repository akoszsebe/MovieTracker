package com.example.movietracker.data.persitance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movietracker.data.persitance.entity.DbBookmarkedMovie;
import com.example.movietracker.data.persitance.entity.DbFavorite;
import com.example.movietracker.data.persitance.entity.DbGenre;
import com.example.movietracker.data.persitance.entity.DbVideoProgress;

public class AppDatabase extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "movies_db";


    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(DbGenre.CREATE_TABLE);
        db.execSQL(DbVideoProgress.CREATE_TABLE);
        db.execSQL(DbBookmarkedMovie.CREATE_TABLE);
        db.execSQL(DbFavorite.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DbGenre.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbVideoProgress.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbBookmarkedMovie.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbFavorite.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
}
