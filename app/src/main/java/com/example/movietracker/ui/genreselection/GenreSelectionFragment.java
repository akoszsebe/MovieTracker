package com.example.movietracker.ui.genreselection;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.widget.VerticalGridView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.movietracker.App;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseFragment;
import com.example.movietracker.data.networking.models.Genre;
import com.example.movietracker.data.networking.models.GenresResponse;
import com.example.movietracker.ui.genreselection.adapter.GenreRecyclerAdapter;
import com.example.movietracker.utils.Utils;

import java.util.List;

public class GenreSelectionFragment extends BaseFragment<GenreSelectionMvpPresenter> implements GenreSelectionMvpView {
    private GenreRecyclerAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_genre_selection;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VerticalGridView gridView = view.findViewById(R.id.gridview);
        int spacing = Utils.convertDpToPixels(view.getContext(), 8);
        gridView.setHorizontalSpacing(spacing);
        gridView.setVerticalSpacing(spacing);
        adapter = new GenreRecyclerAdapter(genre -> {
            presenter.clickedGenre((Genre)genre);
        });
        gridView.setAdapter(adapter);
        presenter.loadGenres();
    }

    @Override
    public void showGenres(List<Genre> response) {
        // add continue item
        response.add(new Genre());
        adapter.submitList(response);
    }

    @Override
    public void navigateToMoviesPage(GenresResponse genres) {
        // remove continue element
        genres.getGenres().remove(genres.getGenres().size()-1);
        NavDirections direction = GenreSelectionFragmentDirections.actionGenreSelectionFragmentToMoviesFragment(genres);
        if (this.getView()!=null) {
            Navigation.findNavController(this.getView()).navigate(direction);
        }
    }

    @Override
    public void elementUpdated(int index) {
        adapter.notifyItemChanged(index);
    }

    @Override
    protected void unsubscribePresenter() {
        presenter.unsubscribe();
    }

    @Override
    protected void onBackPressed() {
        super.onBackPressed();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }
}
