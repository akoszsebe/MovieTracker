package com.example.movietracker.data.persitance.entity;

public class DbFavorite {
    public static final String TABLE_NAME = "favorite_movie";

    public static final String COLUMN_ID = "movie_id";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT PRIMARY KEY"
                    + ")";

    public static final String SELECT_QUERY =
            "SELECT  * FROM " + TABLE_NAME;
}
