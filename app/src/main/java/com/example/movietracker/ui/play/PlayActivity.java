package com.example.movietracker.ui.play;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.movietracker.R;
import com.example.movietracker.base.BaseActivity;

public class PlayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_play);
        Bundle bundle = new Bundle();
        PlayActivityArgs args = PlayActivityArgs.fromBundle(getIntent().getExtras());
        bundle.putInt("index", args.getIndex());
        bundle.putParcelable("videos", args.getVideos());
        navController.setGraph(navController.getGraph(),bundle);
    }
}