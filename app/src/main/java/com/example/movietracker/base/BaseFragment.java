package com.example.movietracker.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import javax.inject.Inject;

public abstract class BaseFragment<MvpPresenterType> extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected
    MvpPresenterType presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        attachToPresenter();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutResId(), container, false);
    }

    protected abstract int getLayoutResId();

    protected abstract void injectDependencies();

    protected abstract void attachToPresenter();

    protected abstract void unsubscribePresenter();

    protected void onBackPressed() {
        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onDestroy() {
        unsubscribePresenter();
        super.onDestroy();
    }
}
