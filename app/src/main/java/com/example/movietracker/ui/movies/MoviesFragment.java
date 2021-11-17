package com.example.movietracker.ui.movies;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.VerticalGridView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.movietracker.App;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseFragment;
import com.example.movietracker.data.networking.models.GenreMovies;
import com.example.movietracker.data.networking.models.Movie;
import com.example.movietracker.ui.movies.adapter.GenreMoviesRecyclerAdapter;
import com.example.movietracker.utils.Utils;

import java.util.List;

public class MoviesFragment extends BaseFragment<MoviesMvpPresenter> implements MoviesMvpView {
    private GenreMoviesRecyclerAdapter adapter;
    private TextView title;
    private VerticalGridView gridView;
    private Movie lastNavigatedMovie;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movies;
    }

    @Override
    protected void injectDependencies() {
        App.instance().appComponent().inject(this);
    }

    @Override
    protected void attachToPresenter() {
        presenter.attach(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new GenreMoviesRecyclerAdapter(item -> {
            lastNavigatedMovie = (Movie) item;
            navigateToMovieDetailsPage(lastNavigatedMovie);
        });
        Bundle arguments = getArguments();
        if (arguments != null) {
            presenter.init(MoviesFragmentArgs.fromBundle(arguments).getGenresResponse().getGenres());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gridview);
        title = view.findViewById(R.id.textView_genre);
        int spacing = Utils.convertDpToPixels(view.getContext(), 8);
        gridView.setHorizontalSpacing(spacing);
        gridView.setVerticalSpacing(spacing);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        presenter.loadGenres();
        if (lastNavigatedMovie != null) {
            presenter.findAndScrollToPosition(lastNavigatedMovie, adapter.getCurrentList());
        }
    }

    @Override
    public void showGenreMovies(List<GenreMovies> genreMovies) {
        adapter.submitList(genreMovies);
        if (gridView != null) {
            gridView.requestChildFocus(gridView.getChildAt(0), gridView.findFocus());
        }
    }

    @Override
    public void refreshBookmarks(GenreMovies bookmarks) {
        adapter.getCurrentList().get(0).setMovies(bookmarks.getMovies());
        adapter.notifyItemChanged(0);
    }

    @Override
    public void addBookmarks(List<GenreMovies> genreMovies) {
        adapter.submitList(genreMovies);
    }

    @Override
    public void setTitle(String text) {
        if (text.isEmpty()) {
            title.setText(R.string.no_selected_genres);
        } else {
            title.setText(text);
        }
    }

    @Override
    public void navigateToMovieDetailsPage(Movie movie) {
        NavDirections direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movie.getId());
        if (this.getView() != null) {
            Navigation.findNavController(this.getView()).navigate(direction);
        }
    }

    @Override
    protected void unsubscribePresenter() {
        presenter.unsubscribe();
    }

}
