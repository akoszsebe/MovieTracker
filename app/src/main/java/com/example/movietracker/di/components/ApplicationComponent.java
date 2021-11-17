package com.example.movietracker.di.components;

import com.example.movietracker.App;
import com.example.movietracker.di.AppScope;
import com.example.movietracker.di.modules.ApplicationModule;
import com.example.movietracker.di.modules.FragmentsModule;
import com.example.movietracker.di.modules.HttpClientModule;
import com.example.movietracker.di.modules.RepositoryModule;
import com.example.movietracker.di.modules.UtilsModule;
import com.example.movietracker.ui.genreselection.GenreSelectionFragment;
import com.example.movietracker.ui.genreselection.GenreSelectionMvpPresenter;
import com.example.movietracker.ui.moviedetails.MovieDetailsFragment;
import com.example.movietracker.ui.moviedetails.MovieDetailsMvpPresenter;
import com.example.movietracker.ui.moviedetails.overview.OverviewFragment;
import com.example.movietracker.ui.moviedetails.overview.OverviewMvpPresenter;
import com.example.movietracker.ui.movies.MoviesFragment;
import com.example.movietracker.ui.movies.MoviesMvpPresenter;
import com.example.movietracker.ui.play.PlayFragment;
import com.example.movietracker.ui.play.PlayMvpPresenter;
import com.example.movietracker.ui.playrecommendation.PlayRecommendationFragment;
import com.example.movietracker.ui.playrecommendation.PlayRecommendationMvpPresenter;

import javax.inject.Singleton;

import dagger.Component;

@AppScope
@Singleton
@Component(modules = {
        ApplicationModule.class,
        HttpClientModule.class,
        FragmentsModule.class,
        RepositoryModule.class,
        UtilsModule.class
})
public interface ApplicationComponent {

    void inject(App app);
    // Inject Fragments
    void inject(GenreSelectionFragment genreSelectionFragment);
    void inject(MoviesFragment moviesFragment);
    void inject(MovieDetailsFragment movieDetailsFragment);
    void inject(OverviewFragment overviewFragment);
    void inject(PlayFragment overviewFragment);
    void inject(PlayRecommendationFragment playRecommendationFragment);

    // Inject Mvp Presenters
    void inject(GenreSelectionMvpPresenter genreSelectionMvpPresenter);
    void inject(MoviesMvpPresenter moviesMvpPresenter);
    void inject(MovieDetailsMvpPresenter movieDetailsMvpPresenter);
    void inject(OverviewMvpPresenter overviewMvpPresenter);
    void inject(PlayMvpPresenter playMvpPresenter);
    void inject(PlayRecommendationMvpPresenter playRecommendationMvpPresenter);
}
