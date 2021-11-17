package com.example.movietracker.base;

public interface BasePlayerMvpView {
    void playVideo(String downloadUrl);

    void enableNext(boolean enabled);

    void enablePrev(boolean enabled);

    void showError(String message);
}
