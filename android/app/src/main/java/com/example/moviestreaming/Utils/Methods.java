package com.example.moviestreaming.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.animation.content.Content;
import com.example.moviestreaming.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Methods {

    public static Timer timer;


    public static void sliderAutoChanger(Activity context,final int length, final ViewPager viewPager) {



//        Handler handler = new Handler();
//        Runnable update = new Runnable()  {
//
//
//            public void run() {
//
//
//
//            }
//
//
//
//
//        };


//        new Timer().schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                handler.post(update);
//            }
//        }, 2000, 5000);

        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (viewPager.getCurrentItem()==length-1){

                            viewPager.setCurrentItem(0);
                        }else {

                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                        }




                        Log.i("currentpage",viewPager.getCurrentItem()+"");
                        Log.i("sliderCurrentItem",viewPager.getCurrentItem()+"");
                        Log.i("length",length+"");

                    }
                });
            }
        },2000,5000);


    }



    public static void viewPageTransformer(ViewPager viewPager){

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {


                if (position < -1){    // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0);

                }
                else if (position <= 0){    // [-1,0]
                    page.setAlpha(1);
                    page.setTranslationX(0);
                    page.setScaleX(1);
                    page.setScaleY(1);

                }
                else if (position <= 1){    // (0,1]
                    page.setTranslationX(-position*page.getWidth());
                    page.setAlpha(1-Math.abs(position));
                    page.setScaleX(1-Math.abs(position));
                    page.setScaleY(1-Math.abs(position));

                }
                else {    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);

                }

            }
        });


    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void showSnackBar(Activity activity,String message,int color){

        Snackbar.make(activity.findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG).
                setBackgroundTint(color)
                .show();
    }

    public static String generateToken(){

        String token= new DecimalFormat("00000").format(new Random().nextInt(99999));
        return token;


    }

}
