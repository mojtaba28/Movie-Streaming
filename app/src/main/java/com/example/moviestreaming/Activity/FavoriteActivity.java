package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moviestreaming.Adapter.FavoriteAdapter;
import com.example.moviestreaming.R;
import com.example.moviestreaming.RoomDB.AppDatabase;
import com.example.moviestreaming.RoomDB.Favorites;
import com.example.moviestreaming.databinding.ActivityFavoriteBinding;
import com.example.moviestreaming.viewmodel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

public class FavoriteActivity extends AppCompatActivity {

    ActivityFavoriteBinding binding;
    FavoriteAdapter favoriteAdapter;
    List<Favorites> favoritesList=new ArrayList<>();
    FavoriteViewModel favoriteViewModel;
    boolean isEmpty=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_favorite);
        favoriteViewModel=new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding.toolbarLayout.tvTitle.setText(getResources().getString(R.string.favorite));
        getFavorites();
        backButton();



    }

    private void backButton() {

        binding.toolbarLayout.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });

    }

    private void getFavorites(){


        favoriteViewModel.getAllItems().observe(this, new Observer<List<Favorites>>() {
            @Override
            public void onChanged(List<Favorites> list) {
                favoritesList=list;

                if (favoritesList==null){

                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.emptyAnim.setVisibility(View.VISIBLE);
                }else {
                    binding.tvEmpty.setVisibility(View.GONE);
                    binding.emptyAnim.setVisibility(View.GONE);
                    binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                    favoriteAdapter=new FavoriteAdapter(getApplicationContext(), favoritesList, false, new FavoriteAdapter.FavoriteClick() {
                        @Override
                        public void onDeleteClick(Favorites favorites,int position) {

                            AlertDialog.Builder alertDialog=new AlertDialog.Builder(FavoriteActivity.this,R.style.AlertDialogCustom);

                            alertDialog.setMessage(getResources().getString(R.string.do_you_want_delete_movie))
                                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            favoriteViewModel.deleteItem(favorites);
                                            dialog.dismiss();
                                            isEmpty=favoriteAdapter.notifyChange(position);
                                            if (isEmpty==true){
                                                binding.tvEmpty.setVisibility(View.VISIBLE);
                                                binding.emptyAnim.setVisibility(View.VISIBLE);
                                            }else {

                                                binding.tvEmpty.setVisibility(View.GONE);
                                                binding.emptyAnim.setVisibility(View.GONE);
                                            }
                                        }
                                    })
                                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onItemClick(Favorites favorites) {
                            Intent intent=new Intent(getApplicationContext(), MovieDetailActivity.class);
                            intent.putExtra("type","movie");
                            intent.putExtra("id",favorites.id);
                            intent.putExtra("name",favorites.name);
                            intent.putExtra("genre",favorites.genre);
                            intent.putExtra("rate",favorites.rate);
                            intent.putExtra("published",favorites.published);
                            intent.putExtra("time",favorites.time);
                            intent.putExtra("director",favorites.director);
                            intent.putExtra("budget",favorites.budget);
                            intent.putExtra("box_office",favorites.box_office);
                            intent.putExtra("image_name",favorites.image_name);
                            intent.putExtra("movie_poster",favorites.movie_poster);
                            intent.putExtra("movie_preview",favorites.movie_preview);
                            intent.putExtra("description",favorites.description);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        }
                    });
                    binding.recyclerview.setAdapter(favoriteAdapter);
                    favoriteAdapter.notifyDataSetChanged();
                    binding.recyclerview.scheduleLayoutAnimation();

                }

            }
        });

    }

    @Override
    protected void onRestart() {
        favoritesList.clear();
        getFavorites();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}