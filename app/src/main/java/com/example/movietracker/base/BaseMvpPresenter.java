package com.example.movietracker.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseMvpPresenter<MvpViewType> {
    protected CompositeDisposable disposables = new CompositeDisposable();
    protected MvpViewType view;

    public void attach(MvpViewType view){
        this.view = view;
    }

    public void unsubscribe(){
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
