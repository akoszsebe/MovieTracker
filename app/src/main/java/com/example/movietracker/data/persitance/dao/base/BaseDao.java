package com.example.movietracker.data.persitance.dao.base;

import com.example.movietracker.data.persitance.AppDatabase;

public class BaseDao {
    public BaseDao(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    protected AppDatabase appDatabase;
}
