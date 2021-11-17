package com.example.movietracker;

import android.app.Application;

import com.example.movietracker.di.components.ApplicationComponent;
import com.example.movietracker.di.components.DaggerApplicationComponent;
import com.example.movietracker.di.modules.ApplicationModule;
import com.example.movietracker.di.modules.FragmentsModule;
import com.example.movietracker.di.modules.HttpClientModule;
import com.example.movietracker.di.modules.RepositoryModule;
import com.example.movietracker.di.modules.UtilsModule;
import com.facebook.stetho.Stetho;

public class App extends Application {
    private static App instance;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        instance = this;

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpClientModule(new HttpClientModule())
                .fragmentsModule(new FragmentsModule())
                .repositoryModule(new RepositoryModule(this))
                .utilsModule(new UtilsModule(this))
                .build();

        mApplicationComponent.inject(this);
    }

    public static App instance() {
        return instance;
    }

    public ApplicationComponent appComponent() {
        return mApplicationComponent;
    }

}
