package com.example.movietracker.data.repository;

import java.util.ArrayList;
import java.util.List;

public class ObservableRepository {
    private List<RepositoryDataChangeListener> repositoryDataChangeListeners;

    public ObservableRepository() {
        this.repositoryDataChangeListeners = new ArrayList<>();
    }

    public void registerRepositoryObserver(RepositoryDataChangeListener databaseObserver) {
        if (!repositoryDataChangeListeners.contains(databaseObserver)) {
            repositoryDataChangeListeners.add(databaseObserver);
        }
    }

    public void unregisterRepositoryObserver(RepositoryDataChangeListener databaseObserver) {
        repositoryDataChangeListeners.remove(databaseObserver);
    }

    void notifyRepositoryChanged(DbChangeAction action, Object item) {
        for (RepositoryDataChangeListener databaseObserver : repositoryDataChangeListeners) {
            if (databaseObserver != null) {
                databaseObserver.onChange(action, item);
            }
        }
    }

    public interface RepositoryDataChangeListener {
        void onChange(DbChangeAction action, Object item);
    }

    public enum DbChangeAction {
        INSERT_FAVORITE_MOVIE,
        UPDATE_VIDEO_PROGRESS,
        DELETE_FAVORITE_MOVIE
    }
}
