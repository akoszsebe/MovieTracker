package com.example.movietracker.data.persitance.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.movietracker.data.persitance.AppDatabase;
import com.example.movietracker.data.persitance.dao.base.BaseDao;
import com.example.movietracker.data.persitance.entity.DbVideoProgress;

import javax.inject.Inject;

public class VideoProgressDaoImp extends BaseDao implements VideoProgressDao {

    @Inject
    public VideoProgressDaoImp(AppDatabase appDatabase) {
        super(appDatabase);
    }

    @Override
    public void insertVideoProgress(DbVideoProgress video) {
        SQLiteDatabase db = appDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbVideoProgress.COLUMN_VIDEO_ID, video.getVideo_id());
        values.put(DbVideoProgress.COLUMN_PROGRESS, video.getProgress());

        // insert row
        long id = db.insertWithOnConflict(DbVideoProgress.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        // close db connection
        db.close();
    }

    @Override
    public DbVideoProgress getVideoProgressById(String key) {
        DbVideoProgress response = new DbVideoProgress();
        SQLiteDatabase db = appDatabase.getWritableDatabase();
        Cursor cursor = db.rawQuery(DbVideoProgress.SELECT_QUERY + "'" + key + "'", null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            response.setVideo_id(cursor.getString(cursor.getColumnIndex(DbVideoProgress.COLUMN_VIDEO_ID)));
            response.setProgress(cursor.getInt(cursor.getColumnIndex(DbVideoProgress.COLUMN_PROGRESS)));
        } else {
            response.setProgress(0);
        }
        cursor.close();
        // close db connection
        db.close();
        return response;
    }
}