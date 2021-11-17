package com.example.movietracker.data.persitance.entity;

public class DbGenre {
    public static final String TABLE_NAME = "genre";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SELECTED = "selected";

    private int id;
    private String name;
    private int selected;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_SELECTED + " INTEGER"
                    + ")";

    public static final String SELECT_QUERY =
            "SELECT  * FROM " + TABLE_NAME;


    public DbGenre() {
    }

    public DbGenre(int id, String name, int selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public DbGenre(String name, int selected) {
        this.name = name;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public DbGenre setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DbGenre setName(String name) {
        this.name = name;
        return this;
    }

    public int isSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}
