package com.example.movietracker.data.persitance.entity;

public class DbBookmarkedMovie {
    public static final String TABLE_NAME = "bookmarked_movie";

    public static final String COLUMN_ID = "movie_id";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_GENRE_NAMES = "genre_names";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT PRIMARY KEY,"
                    + COLUMN_POSTER_PATH + " TEXT,"
                    + COLUMN_RELEASE_DATE + " TEXT,"
                    + COLUMN_GENRE_NAMES + " TEXT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_VOTE_AVERAGE + " REAL"
                    + ")";

    public static final String SELECT_QUERY =
            "SELECT  * FROM " + TABLE_NAME;
}
