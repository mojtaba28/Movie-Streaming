package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.databinding.ActivitySplashBinding;
import com.example.moviestreaming.databinding.ConnectionDialogBinding;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Method;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    static int SPLASH_DELAY=5000;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash);

        animate();
        checkConnection();

    }

    private void checkConnection() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Methods.isNetworkConnected(getApplicationContext())==true){

                    startActivity(new Intent(getApplicationContext() , HomeActivity.class));
                    finish();
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);

                }else {
                    connectionDialog();
                }

            }
        },SPLASH_DELAY);


    }

    private void animate() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_without_duration);
        fadeInAnimation.setDuration(SPLASH_DELAY);
        binding.appName.setAnimation(fadeInAnimation);
    }

    private void connectionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        ConnectionDialogBinding connectionBinding=DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),
                R.layout.connection_dialog, (ViewGroup) binding.getRoot(),false);
        //View view = inflater.inflate(R.layout.connection_dialog , null);

        builder.setView(connectionBinding.getRoot());

        connectionBinding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                checkConnection();

            }
        });

        connectionBinding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                checkConnection();

            }
        });

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

    }
}