package com.example.movietracker.di.modules;

import com.example.movietracker.data.repository.MovieRepository;
import com.example.movietracker.data.repository.VideoRepository;
import com.example.movietracker.data.repository.YoutubeRepository;
import com.example.movietracker.ui.genreselection.GenreSelectionMvpPresenter;
import com.example.movietracker.ui.moviedetails.MovieDetailsMvpPresenter;
import com.example.movietracker.ui.moviedetails.overview.OverviewMvpPresenter;
import com.example.movietracker.ui.movies.MoviesMvpPresenter;
import com.example.movietracker.ui.play.PlayMvpPresenter;
import com.example.movietracker.ui.playrecommendation.PlayRecommendationMvpPresenter;
import com.example.movietracker.utils.YoutubeExtractorHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentsModule {

    @Provides
    GenreSelectionMvpPresenter provideGenreSelectionMvpPresenter(MovieRepository movieRepository) {
        return new GenreSelectionMvpPresenter(movieRepository);
    }

    @Provides
    MoviesMvpPresenter provideMoviesMvpPresenter(MovieRepository movieRepository) {
        return new MoviesMvpPresenter(movieRepository);
    }

    @Provides
    MovieDetailsMvpPresenter provideMovieDetailsMvpPresenter(MovieRepository movieRepository, VideoRepository videoRepository) {
        return new MovieDetailsMvpPresenter(movieRepository, videoRepository);
    }

    @Provides
    OverviewMvpPresenter provideOverviewMvpPresenter() {
        return new OverviewMvpPresenter();
    }

    @Provides
    PlayMvpPresenter providePlayMvpPresenter(YoutubeExtractorHelper youtubeExtractorHelper, YoutubeRepository youtubeRepository, VideoRepository videoRepository) {
        return new PlayMvpPresenter(youtubeExtractorHelper, youtubeRepository, videoRepository);
    }

    @Provides
    PlayRecommendationMvpPresenter providePlayRecommendationMvpPresenter(YoutubeExtractorHelper youtubeExtractorHelper) {
        return new PlayRecommendationMvpPresenter(youtubeExtractorHelper);
    }
}
