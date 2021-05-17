package com.example.moviestreaming.Activity;


import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.example.moviestreaming.Fragment.GenreFragment;
import com.example.moviestreaming.Fragment.HomeFragment;
import com.example.moviestreaming.Fragment.ProfileFragment;
import com.example.moviestreaming.Fragment.SearchFragment;
import com.example.moviestreaming.R;

import com.example.moviestreaming.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;


public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);


        initNavigation();

    }

    private void initNavigation(){
        BottomNavigationView bottomNavigationView=binding.bottomNavigation;
        NavController navController= Navigation.findNavController(this,R.id.navHost);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }



    @Override
    public void onBackPressed() {


//        if (fragmentManager.getBackStackEntryCount()>0){
//
//            fragmentManager.popBackStack();
//
//        }
            super.onBackPressed();



    }
}