package com.example.movietracker.ui.moviedetails.overview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.movietracker.App;
import com.example.movietracker.R;
import com.example.movietracker.base.BaseFragment;

public class OverviewFragment extends BaseFragment<OverviewMvpPresenter> implements OverviewMvpView {
    private TextView title;
    private TextView description;
    private Button ok;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movie_details_overview;
    }

    @Override
    protected void injectDependencies() {
        App.instance().appComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.textView_title);
        description = view.findViewById(R.id.textView_description);
        ok = view.findViewById(R.id.button_ok);
        Bundle arguments = getArguments();
        if (arguments != null) {
            title.setText(OverviewFragmentArgs.fromBundle(arguments).getTitle());
            description.setText(OverviewFragmentArgs.fromBundle(arguments).getContent());
        }
        ok.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
    }

    @Override
    protected void attachToPresenter() {
        presenter.attach(this);
    }

    @Override
    protected void unsubscribePresenter() {
        presenter.unsubscribe();
    }
}
