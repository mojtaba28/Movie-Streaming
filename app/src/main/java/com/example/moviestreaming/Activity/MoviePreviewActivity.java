package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.moviestreaming.R;
import com.example.moviestreaming.databinding.ActivityMoviePreviewBinding;
import com.halilibo.bettervideoplayer.BetterVideoPlayer;

public class MoviePreviewActivity extends AppCompatActivity{

    ActivityMoviePreviewBinding binding;
    BetterVideoPlayer betterVideoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_preview);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_movie_preview);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //playerView=binding.playerView;
        setVideoPlayer();


    }

    private void setVideoPlayer() {
        betterVideoPlayer=binding.betterVideoPlayer;
        String link=getIntent().getStringExtra("movie_preview");
        betterVideoPlayer.setSource(Uri.parse(link));
        betterVideoPlayer.setAutoPlay(true);
        betterVideoPlayer.setBottomProgressBarVisibility(true);
        betterVideoPlayer.enableSwipeGestures();
        betterVideoPlayer.enableControls();
        betterVideoPlayer.start();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}