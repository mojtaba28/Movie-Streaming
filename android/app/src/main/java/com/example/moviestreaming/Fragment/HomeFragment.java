package com.example.moviestreaming.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.example.moviestreaming.Activity.MovieActivity;
import com.example.moviestreaming.Activity.MovieDetailActivity;
import com.example.moviestreaming.Adapter.GenreAdapter;
import com.example.moviestreaming.Adapter.IMDBTopAdapter;
import com.example.moviestreaming.Adapter.NewMoviesAdapter;
import com.example.moviestreaming.Adapter.PopularAdapter;
import com.example.moviestreaming.Adapter.SliderAdapter;
import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.Model.SliderModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.databinding.FragmentHomeBinding;
import com.example.moviestreaming.viewmodel.HomeViewModel;
import com.rd.animation.type.AnimationType;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {

    FragmentHomeBinding binding;
    HomeViewModel homeViewModel;



    SliderAdapter sliderAdapter;
    IMDBTopAdapter imdbTopAdapter;
    NewMoviesAdapter newMoviesAdapter;
    PopularAdapter popularAdapter;
    GenreAdapter genreAdapter;

    List<SliderModel> sliderList=new ArrayList<>();

    public static boolean showShimmer=true;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        View view= binding.getRoot();
        homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);


        //initSkeletonScreen();



        getSlider();
        getIMDBTopMovies();
        getNewMovies();
        getPopularMovies();
        getGenre();
        setRefresh();

        //more items onClick
        binding.tvMoreGenre.setOnClickListener(this);
        binding.tvMoreTopIMDB.setOnClickListener(this);
        binding.tvMoreNewMovie.setOnClickListener(this);
        binding.tvMorePopular.setOnClickListener(this);





        return view;
    }

    private void setRefresh() {

        binding.swipeRefresh.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);

        binding.swipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getIMDBTopMovies();
                        getNewMovies();
                        getPopularMovies();
                        getGenre();
                        binding.swipeRefresh.setRefreshing(false);
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction().replace(HomeFragment.this.getId(), new HomeFragment()).commit();
                    }
                },3000);
            }
        });


    }



    private void getSlider(){
       // viewPager_skeletonScreen.show();
        homeViewModel.getSlider();
        homeViewModel.getSliderMutableLiveData().observe(getActivity(), new Observer<List<SliderModel>>() {
            @Override
            public void onChanged(List<SliderModel> list) {

                if (list==null){

                }else {
                    if (list.size()>0){
                        binding.animProgress.setVisibility(View.GONE);
                        // viewPager_skeletonScreen.hide();
                        sliderAdapter=new SliderAdapter(getContext(), list, new SliderAdapter.SliderItemOnClick() {
                            @Override
                            public void onItemClick(SliderModel model) {

                                sliderIntent(model);

                            }
                        });
                        Methods.sliderAutoChanger(getActivity(),list.size(),binding.viewpager);
                        binding.viewpager.setAdapter(sliderAdapter);
                        Methods.viewPageTransformer(binding.viewpager);
                        setSliderIndicator(list.size());
                    }
                }




            }
        });

    }

    private void setSliderIndicator(int count){

        binding.pageIndicatorView.setCount(count);
        binding.pageIndicatorView.setAnimationDuration(600);
        binding.pageIndicatorView.setAnimationType(AnimationType.THIN_WORM);
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void getIMDBTopMovies() {
        //topIMDB_skeletonScreen.show();
        binding.imdbRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        homeViewModel.getIMDBMovie("top_imdb");
        homeViewModel.getIMDBMovieMutableLiveData().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> list) {
                if (list==null){
                    Methods.showSnackBar(getActivity(),getResources().getString(R.string.unable_connect_to_server),
                            getResources().getColor(R.color.red));
                }else {
                    if ( list.size()>0){

                        //topIMDB_skeletonScreen.hide();
                        showShimmer=false;
                        imdbTopAdapter=new IMDBTopAdapter(getContext(), list, true, new IMDBTopAdapter.IMDBItemOnClick() {
                            @Override
                            public void onItemClick(MovieModel model) {
                                movieDetailIntent(model);
                            }
                        });
                        binding.imdbRecyclerview.setAdapter(imdbTopAdapter);
                        imdbTopAdapter.notifyDataSetChanged();
                        binding.imdbRecyclerview.scheduleLayoutAnimation();
                        binding.tvImdb.setVisibility(View.VISIBLE);
                        binding.tvMoreTopIMDB.setVisibility(View.VISIBLE);

                    }
                }

            }
        });

    }

    private void getNewMovies() {
       // newMovies_skeletonScreen.show();
        binding.newMovieRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        homeViewModel.getNewMovie("new");
        homeViewModel.getNewMovieMutableLiveData().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> list) {
                if (list==null){

                }else {
                    if ( list.size()>0){
                        // newMovies_skeletonScreen.hide();
                        newMoviesAdapter=new NewMoviesAdapter(getContext(), list, true, new NewMoviesAdapter.NewMovieItemOnClick() {
                            @Override
                            public void onItemClick(MovieModel model) {
                                movieDetailIntent(model);
                            }
                        });
                        binding.newMovieRecyclerview.setAdapter(newMoviesAdapter);
                        newMoviesAdapter.notifyDataSetChanged();
                        binding.newMovieRecyclerview.scheduleLayoutAnimation();
                        binding.tvNewMovies.setVisibility(View.VISIBLE);
                        binding.tvMoreNewMovie.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

    private void getPopularMovies() {
        //popular_skeletonScreen.show();
        binding.popularRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        homeViewModel.getPopularMovie("1");
        homeViewModel.getPopularMovieMutableLiveData().observe(getActivity(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> list) {
                if (list==null){

                }else {
                    if (list.size()>0){
                        //popular_skeletonScreen.hide();
                        popularAdapter=new PopularAdapter(getContext(), list, true, new PopularAdapter.PopularMovieItemOnClick() {
                            @Override
                            public void onItemClick(MovieModel model) {
                                movieDetailIntent(model);
                            }
                        });
                        binding.popularRecyclerview.setAdapter(popularAdapter);
                        popularAdapter.notifyDataSetChanged();
                        binding.popularRecyclerview.scheduleLayoutAnimation();
                        binding.tvPopular.setVisibility(View.VISIBLE);
                        binding.tvMorePopular.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

    private void sliderIntent(SliderModel model){
        Intent intent=new Intent(getContext(), MovieDetailActivity.class);
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
        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }

    private void movieDetailIntent(MovieModel model){

        Intent intent=new Intent(getContext(), MovieDetailActivity.class);
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
        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

    }

    private void getGenre() {
       // genre_skeletonScreen.show();
        binding.genreRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        homeViewModel.getGenre();
        homeViewModel.getGenreMutableLiveData().observe(getActivity(), new Observer<List<GenreModel>>() {
            @Override
            public void onChanged(List<GenreModel> list) {
                if (list==null){

                }else {
                    if (list.size()>0){
                        //genre_skeletonScreen.hide();
                        genreAdapter=new GenreAdapter(getContext(), list, new GenreAdapter.GenreClick() {
                            @Override
                            public void onItemClick(GenreModel genreModel) {
                                Intent intent=new Intent(getContext(), MovieActivity.class);
                                intent.putExtra("type","genre");
                                intent.putExtra("name",genreModel.getName());
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                            }
                        });
                        binding.genreRecyclerview.setAdapter(genreAdapter);
                        genreAdapter.notifyDataSetChanged();
                        binding.genreRecyclerview.scheduleLayoutAnimation();
                        binding.tvGenre.setVisibility(View.VISIBLE);
                        binding.tvMoreGenre.setVisibility(View.VISIBLE);
                    }
                }
                //genreList=list;

            }
        });



    }

    @Override
    public void onClick(View view) {

        int id=view.getId();

        if (id==binding.tvMoreGenre.getId()){

            Navigation.findNavController(getActivity(), R.id.navHost)
                    .navigate(R.id.genreFragment);

        } else if (id==binding.tvMoreTopIMDB.getId()){

            Intent intent =new Intent(getContext(),MovieActivity.class);
            intent.putExtra("type","top_imdb");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);

        } else if (id==binding.tvMoreNewMovie.getId()){
            Intent intent =new Intent(getContext(),MovieActivity.class);
            intent.putExtra("type","new");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);

        } else if (id==binding.tvMorePopular.getId()){
            Intent intent =new Intent(getContext(),MovieActivity.class);
            intent.putExtra("type","popular");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        }
    }


}