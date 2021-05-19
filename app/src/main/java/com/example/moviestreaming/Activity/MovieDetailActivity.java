package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.example.moviestreaming.Adapter.ActorsAdapter;
import com.example.moviestreaming.Adapter.CommentAdapter;
import com.example.moviestreaming.Adapter.MovieByGenreAdapter;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.ActorModel;
import com.example.moviestreaming.Model.CommentModel;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.RoomDB.AppDatabase;
import com.example.moviestreaming.RoomDB.Favorites;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.Utils.SessionManager;
import com.example.moviestreaming.databinding.ActivityMovieDetailBinding;
import com.example.moviestreaming.databinding.DialogDownloadBinding;
import com.example.moviestreaming.databinding.DialogLoginBinding;
import com.example.moviestreaming.viewmodel.MovieDetailActivityViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class MovieDetailActivity extends AppCompatActivity {

    ActivityMovieDetailBinding binding;
    Bundle bundle;
    public AppDatabase appDatabase;
    MovieDetailActivityViewModel viewModel;

    //Similar Movie
    MovieByGenreAdapter movieByGenreAdapter;

    //movie actors
    List<ActorModel> actorsList=new ArrayList<>();
    ActorsAdapter actorsAdapter;

    //comments
    CommentAdapter commentAdapter;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_movie_detail);
        bundle=getIntent().getExtras();
        sessionManager=new SessionManager(getApplicationContext());
        viewModel=new ViewModelProvider(this).get(MovieDetailActivityViewModel.class);
        appDatabase = AppDatabase.getInstance(this);

        checkFavorite();
        Favorite();
        setData();
        getActors();
        getSimilarMovie();
        getComment();
        commentFab();
        playMovie();
        downloadMovie();
        backButton();

    }

    private void backButton() {

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });

    }

    private void downloadMovie() {
        binding.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.getLoggedIn()){


                    DialogDownloadBinding dialogBinding=DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),
                            R.layout.dialog_download,(ViewGroup)binding.getRoot(),false );

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MovieDetailActivity.this, R.style.BottomSheetDialog);
                    bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    bottomSheetDialog.setContentView(dialogBinding.getRoot());
                    bottomSheetDialog.setCancelable(true);


                    bottomSheetDialog.show();
                    PushDownAnim.setPushDownAnimTo(dialogBinding.tv480).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    PushDownAnim.setPushDownAnimTo(dialogBinding.tv720).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    PushDownAnim.setPushDownAnimTo(dialogBinding.tv1080).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });



                }else {
                    DialogLoginBinding dialogBinding=DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),
                            R.layout.dialog_login,(ViewGroup)binding.getRoot(),false );

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MovieDetailActivity.this, R.style.BottomSheetDialog);
                    bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    bottomSheetDialog.setContentView(dialogBinding.getRoot());
                    bottomSheetDialog.setCancelable(true);


                    bottomSheetDialog.show();
                    PushDownAnim.setPushDownAnimTo(dialogBinding.tvLogin).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        }
                    });

                    PushDownAnim.setPushDownAnimTo(dialogBinding.tvRegister).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        }
                    });
                }

            }
        });

    }

    private void playMovie() {
        PushDownAnim.setPushDownAnimTo(binding.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moviePreview=getIntent().getStringExtra("movie_preview");
                String movieName=getIntent().getStringExtra("name");
                Intent intent=new Intent(getApplicationContext(),MoviePreviewActivity.class);
                intent.putExtra("name",movieName);
                intent.putExtra("movie_preview",moviePreview);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
    }


    private void setData(){

        Glide.with(getApplicationContext())
                .load(Constant.MAIN_URL+ Constant.CONTENT+"poster/"+bundle.getString("movie_poster"))
                .into(binding.imgMovie);
        binding.tvName.setText(bundle.getString("name"));
        binding.tvGenre.setText(bundle.getString("genre"));
        binding.tvRate.setText(bundle.getString("rate"));
        binding.tvPublished.setText(bundle.getString("published"));
        binding.tvTime.setText(bundle.getString("time"));
        binding.tvDirector.setText(bundle.getString("director"));
        binding.tvBudget.setText(bundle.getString("budget"));
        binding.tvBoxOffice.setText(bundle.getString("box_office"));
        binding.tvDescription.setText(bundle.getString("description"));

    }

    private void commentFab(){

        PushDownAnim.setPushDownAnimTo(binding.btnComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CommentDetailActivity.class);
                intent.putExtra("movie_id",bundle.getString("id"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });


        binding.nestedScroolView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                 if(scrollY > oldScrollY && binding.btnComment.getVisibility() == View.VISIBLE) {
                    binding.btnComment.hide();
                } else if (scrollY < oldScrollY && binding.btnComment.getVisibility() != View.VISIBLE) {
                    binding.btnComment.show();
                }
            }
        });


    }

    private void getActors(){
//        api.getActors(getApplicationContext(), requestQueue, actorsList, bundle.getString("id"),
//                new ActorsResponse() {
//                    @Override
//                    public void getActors(List<ActorsModel> list) {
//                        binding.castRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
//                        actorsAdapter=new ActorsAdapter(getApplicationContext(),list);
//                        binding.castRecyclerview.setAdapter(actorsAdapter);
//                    }
//                });

        viewModel.getActors(bundle.getString("id")).observe(this, new Observer<List<ActorModel>>() {
            @Override
            public void onChanged(List<ActorModel> list) {

                if (list!=null){

                    binding.animProgress.setVisibility(View.GONE);
                    binding.castRecyclerview.setLayoutManager
                            (new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    actorsAdapter=new ActorsAdapter(getApplicationContext(),list);
                    binding.castRecyclerview.setAdapter(actorsAdapter);
                    actorsAdapter.notifyDataSetChanged();
                    binding.castRecyclerview.scheduleLayoutAnimation();
                }

            }
        });


    }

    private void getSimilarMovie(){
        String s=bundle.getString("genre");
        if (s.contains("/")){
            s=s.substring(0, s.indexOf("/"));
        }

        viewModel.getSimilarMovie(s).observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> list) {
                if (list!=null){

                    binding.similarMovieRecyclerview.setLayoutManager
                            (new LinearLayoutManager(getApplicationContext(),
                                    LinearLayoutManager.HORIZONTAL,false));
                    movieByGenreAdapter=new MovieByGenreAdapter
                            (getApplicationContext(), list, true, new MovieByGenreAdapter.MovieByGenreItemOnClick() {
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
                                    finish();
                                }
                            });
                    binding.similarMovieRecyclerview.setAdapter(movieByGenreAdapter);
                    movieByGenreAdapter.notifyDataSetChanged();
                    binding.similarMovieRecyclerview.scheduleLayoutAnimation();

                }
            }
        });
    }

    public void Favorite(){


        PushDownAnim.setPushDownAnimTo(binding.imgFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Favorites favorites=new Favorites();
                favorites.id=bundle.getString("id");
                favorites.name=bundle.getString("name");
                favorites.genre=bundle.getString("genre");
                favorites.rate=bundle.getString("rate");
                favorites.published=bundle.getString("published");
                favorites.time=bundle.getString("time");
                favorites.director=bundle.getString("director");
                favorites.budget=bundle.getString("budget");
                favorites.box_office=bundle.getString("box_office");
                favorites.image_name=bundle.getString("image_name");
                favorites.movie_poster=bundle.getString("movie_poster");
                favorites.movie_preview=bundle.getString("movie_preview");
                favorites.description=bundle.getString("description");



                int favoriteId=Integer.parseInt(bundle.getString("id"));
                if (!viewModel.isFavorite(favoriteId)){

                    viewModel.insertItem(favorites);
                    binding.imgFavorite.setImageResource(R.drawable.ic_baseline_bookmark_24);
                    Methods.showSnackBar(MovieDetailActivity.this,getResources().
                            getString(R.string.movie_added),getResources().getColor(R.color.green));
                }else {


                    viewModel.deleteItem(favorites);
                    binding.imgFavorite.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
                    Methods.showSnackBar(MovieDetailActivity.this,getResources().
                            getString(R.string.movie_removed),getResources().getColor(R.color.green));

                }

            }
        }).setScale(MODE_SCALE);

    }

    private void checkFavorite(){
        int favoriteId=Integer.parseInt(bundle.getString("id"));
        if (!viewModel.isFavorite(favoriteId)){
            binding.imgFavorite.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
        }else {
            binding.imgFavorite.setImageResource(R.drawable.ic_baseline_bookmark_24);
        }


    }

    private void getComment(){

        viewModel.getComment(bundle.getString("id")).observe(this, new Observer<List<CommentModel>>() {
            @Override
            public void onChanged(List<CommentModel> list) {
                if (list!=null){

                    if (list.size()>0){

                        if (list.size()>10){
                            binding.tvMoreComment.setVisibility(View.VISIBLE);
                        }else {
                            binding.tvMoreComment.setVisibility(View.GONE);
                        }
                        binding.tvComment.setVisibility(View.VISIBLE);
                        binding.commentRecyclerview.setVisibility(View.VISIBLE);
                        binding.commentRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                        commentAdapter=new CommentAdapter(getApplicationContext(),list,true);
                        binding.commentRecyclerview.setAdapter(commentAdapter);
                        commentAdapter.notifyDataSetChanged();
                        binding.commentRecyclerview.scheduleLayoutAnimation();


                    }else {
                        binding.tvComment.setVisibility(View.GONE);
                        binding.commentRecyclerview.setVisibility(View.GONE);
                        binding.tvMoreComment.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}