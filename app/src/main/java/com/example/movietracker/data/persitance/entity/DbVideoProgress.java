package com.example.movietracker.data.persitance.entity;

public class DbVideoProgress {
    public static final String TABLE_NAME = "video_progress";

    public static final String COLUMN_VIDEO_ID = "video_id";
    public static final String COLUMN_PROGRESS = "progress";

    private String video_id;
    private int progress;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_VIDEO_ID + " TEXT PRIMARY KEY,"
                    + COLUMN_PROGRESS + " INTEGER"
                    + ")";

    public static final String SELECT_QUERY =
            "SELECT  * FROM " + TABLE_NAME + " WHERE " + COLUMN_VIDEO_ID + " = ";

    public DbVideoProgress() {
    }


    public DbVideoProgress(String video_id, int progress) {
        this.video_id = video_id;
        this.progress = progress;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

