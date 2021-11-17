package com.example.movietracker.data.persitance.dao;

import com.example.movietracker.data.persitance.entity.DbVideoProgress;

public interface VideoProgressDao {
    void insertVideoProgress(DbVideoProgress videoProgress);

    DbVideoProgress getVideoProgressById(String id);
}
