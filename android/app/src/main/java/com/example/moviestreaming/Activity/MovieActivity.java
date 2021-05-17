package com.example.moviestreaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.baoyz.widget.PullRefreshLayout;
import com.example.moviestreaming.Adapter.IMDBTopAdapter;
import com.example.moviestreaming.Adapter.MovieByGenreAdapter;
import com.example.moviestreaming.Adapter.NewMoviesAdapter;
import com.example.moviestreaming.Adapter.PopularAdapter;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.R;

import com.example.moviestreaming.databinding.ActivityMovieBinding;
import com.example.moviestreaming.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    ActivityMovieBinding binding;
    MovieViewModel movieViewModel;
    TextView tv_title;
    RecyclerView recyclerView;

    IMDBTopAdapter imdbTopAdapter;
    NewMoviesAdapter newMoviesAdapter;
    PopularAdapter popularAdapter;
    MovieByGenreAdapter movieByGenreAdapter;

    List<MovieModel> movieList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_movie);
        movieViewModel=new ViewModelProvider(this).get(MovieViewModel.class);
        init();
        //initSkeletonScreen();
        getMovies();
        recyclerScrollChange();
        backButton();



    }

    private void recyclerScrollChange(){

        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                if (topRowVerticalPosition>=0){
                    binding.swipeRefresh.setEnabled(true);
                    setRefresh();
                }else {
                    binding.swipeRefresh.setEnabled(false);
                }
            }
        });

    }

    private void backButton() {

        binding.toolbarLayout.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    private void init(){
        tv_title=binding.toolbarLayout.tvTitle;
        recyclerView=binding.recyclerview;

    }

    private void setRefresh() {
        binding.swipeRefresh.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);

        binding.swipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMovies();
                        binding.swipeRefresh.setRefreshing(false);
//                        String type=getIntent().getStringExtra("type");
//                        if (type.equals("genre")){
//                            String genreName=getIntent().getStringExtra("name");
//                            Intent intent=new Intent(getApplicationContext(),MovieActivity.class);
//                            intent.putExtra("type",type);
//                            intent.putExtra("name",genreName);
//                            startActivity(intent);
//                            finish();
//                        }else {
//                            Intent intent=new Intent(getApplicationContext(),MovieActivity.class);
//                            intent.putExtra("type",type);
//                            startActivity(intent);
//                            finish();
//                        }

                    }
                },3000);
            }
        });


    }

    private void getMovies() {

        if (getIntent().getStringExtra("type").equals("top_imdb")){

            tv_title.setText(getResources().getString(R.string.top_imdb));
            movieViewModel.getIMDBMovie("top_imdb");
            movieViewModel.getIMDBMovieMutableLiveData().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> list) {
                    if (list.size()>0){
                        binding.animProgress.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                        imdbTopAdapter=new IMDBTopAdapter(getApplicationContext(), list, false, new IMDBTopAdapter.IMDBItemOnClick() {
                            @Override
                            public void onItemClick(MovieModel model) {
                                Intent intent=new Intent(getApplicationContext(), MovieDetailActivity.class);
                                intent.putExtra("type","top_imdb");
                                intent.putExtra("id",model.getId());
                                intent.putExtra("name",model.getName());
                                intent.putExtra("genre",model.getGenre());
                                intent.putExtra("rate",model.getRate());
                                intent.putExtra("published",model.getPublished());
                                intent.putExtra("time",model.getTime());
                                intent.putExtra("director",model.getDirector());
                                intent.putExtra("budget",model.getBudget());
                                intent.putExtra("box_office",model.getBox_office());
                                intent.putExtra("image_name",model.getImage_name());
                                intent.putExtra("movie_poster",model.getMovie_poster());
                                intent.putExtra("movie_preview",model.getMovie_preview());
                                intent.putExtra("description",model.getDescription());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                            }
                        });
                        binding.recyclerview.setAdapter(imdbTopAdapter);
                        imdbTopAdapter.notifyDataSetChanged();
                        binding.recyclerview.scheduleLayoutAnimation();

                    }
                }
            });
        } else if (getIntent().getStringExtra("type").equals("new")){

            tv_title.setText(getResources().getString(R.string.new_movies));
            movieViewModel.getNewMovie("new");
            movieViewModel.getNewMovieMutableLiveData().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> list) {
                    if (list.size()>0){
                        binding.animProgress.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                        newMoviesAdapter=new NewMoviesAdapter(getApplicationContext(), list, false, new NewMoviesAdapter.NewMovieItemOnClick() {
                            @Override
                            public void onItemClick(MovieModel model) {
                                Intent intent=new Intent(getApplicationContext(), MovieDetailActivity.class);
                                intent.putExtra("type","new");
                                intent.putExtra("id",model.getId());
                                intent.putExtra("name",model.getName());
                                intent.putExtra("genre",model.getGenre());
                                intent.putExtra("rate",model.getRate());
                                intent.putExtra("published",model.getPublished());
                                intent.putExtra("time",model.getTime());
                                intent.putExtra("director",model.getDirector());
                                intent.putExtra("budget",model.getBudget());
                                intent.putExtra("box_office",model.getBox_office());
                                intent.putExtra("image_name",model.getImage_name());
                                intent.putExtra("movie_poster",model.getMovie_poster());
                                intent.putExtra("movie_preview",model.getMovie_preview());
                                intent.putExtra("description",model.getDescription());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                            }
                        });
                        binding.recyclerview.setAdapter(newMoviesAdapter);
                        newMoviesAdapter.notifyDataSetChanged();
                        binding.recyclerview.scheduleLayoutAnimation();
                    }
                }
            });

        }else if (getIntent().getStringExtra("type").equals("popular")){
            tv_title.setText(getResources().getString(R.string.popular));
            movieViewModel.getPopularMovie("1");
            movieViewModel.getPopularMovieMutableLiveData().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> list) {
                    if (list.size()>0){
                        binding.animProgress.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                        popularAdapter=new PopularAdapter(getApplicationContext(), list, false, new PopularAdapter.PopularMovieItemOnClick() {
                            @Override
                            public void onItemClick(MovieModel model) {
                                Intent intent=new Intent(getApplicationContext(), MovieDetailActivity.class);
                                intent.putExtra("type","popular");
                                intent.putExtra("id",model.getId());
                                intent.putExtra("name",model.getName());
                                intent.putExtra("genre",model.getGenre());
                                intent.putExtra("rate",model.getRate());
                                intent.putExtra("published",model.getPublished());
                                intent.putExtra("time",model.getTime());
                                intent.putExtra("director",model.getDirector());
                                intent.putExtra("budget",model.getBudget());
                                intent.putExtra("box_office",model.getBox_office());
                                intent.putExtra("image_name",model.getImage_name());
                                intent.putExtra("movie_poster",model.getMovie_poster());
                                intent.putExtra("movie_preview",model.getMovie_preview());
                                intent.putExtra("description",model.getDescription());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                            }
                        });
                        recyclerView.setAdapter(popularAdapter);
                        popularAdapter.notifyDataSetChanged();
                        binding.recyclerview.scheduleLayoutAnimation();
                    }
                }
            });

        }else if (getIntent().getStringExtra("type").equals("genre")){
            String genreName=getIntent().getStringExtra("name");
            tv_title.setText(genreName);
            movieViewModel.getMovieByGenre(genreName);
            movieViewModel.getMovieByGenreMutableLiveData().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> list) {

                    if (list==null){
                        binding.tvEmpty.setText(getResources().getString(R.string.unable_connect_to_server));
                        binding.animProgress.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                        binding.emptyAnim.setVisibility(View.VISIBLE);
                    }else {

                        if (list.size()>0){
                            binding.animProgress.setVisibility(View.GONE);
                            binding.tvEmpty.setVisibility(View.GONE);
                            binding.emptyAnim.setVisibility(View.GONE);
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                            movieByGenreAdapter=new MovieByGenreAdapter(getApplicationContext(), list, false, new MovieByGenreAdapter.MovieByGenreItemOnClick() {
                                @Override
                                public void onItemClick(MovieModel model) {
                                    Intent intent=new Intent(getApplicationContext(), MovieDetailActivity.class);
                                    intent.putExtra("type","genre");
                                    intent.putExtra("id",model.getId());
                                    intent.putExtra("name",model.getName());
                                    intent.putExtra("genre",model.getGenre());
                                    intent.putExtra("rate",model.getRate());
                                    intent.putExtra("published",model.getPublished());
                                    intent.putExtra("time",model.getTime());
                                    intent.putExtra("director",model.getDirector());
                                    intent.putExtra("budget",model.getBudget());
                                    intent.putExtra("box_office",model.getBox_office());
                                    intent.putExtra("image_name",model.getImage_name());
                                    intent.putExtra("movie_poster",model.getMovie_poster());
                                    intent.putExtra("movie_preview",model.getMovie_preview());
                                    intent.putExtra("description",model.getDescription());
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,
                                            R.anim.slide_out_left);
                                }
                            });
                            recyclerView.setAdapter(movieByGenreAdapter);
                            movieByGenreAdapter.notifyDataSetChanged();
                            binding.recyclerview.scheduleLayoutAnimation();
                        }else {
                            binding.tvEmpty.setVisibility(View.VISIBLE);
                            binding.emptyAnim.setVisibility(View.VISIBLE);
                            binding.animProgress.setVisibility(View.GONE);
                        }
                    }


                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}