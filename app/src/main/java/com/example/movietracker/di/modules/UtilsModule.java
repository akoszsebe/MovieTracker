package com.example.movietracker.di.modules;

import android.content.Context;

import com.example.movietracker.utils.YoutubeExtractorHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    private Context mAppContext;

    public UtilsModule(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    @Provides
    @Singleton
    YoutubeExtractorHelper provideYoutubeExtractorHelper(){
        return new YoutubeExtractorHelper(mAppContext);
    }
}
