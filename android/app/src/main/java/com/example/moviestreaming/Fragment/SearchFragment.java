package com.example.moviestreaming.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreaming.Activity.MovieDetailActivity;
import com.example.moviestreaming.Adapter.SearchAdapter;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.Model.MovieResult;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.databinding.FragmentSearchBinding;
import com.example.moviestreaming.viewmodel.SearchViewModel;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    FragmentSearchBinding binding;
    SearchAdapter searchAdapter;
    SearchViewModel searchViewModel;
    TabLayout tabLayout;
    List<MovieModel> movieList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);
        View view=binding.getRoot();
        searchViewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        setTabLayout();
        Search();
        return view;
    }

    private void setTabLayout() {
        tabLayout=binding.tabLayout;

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.by_name)),0);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.by_director)),1);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.by_genre)),2);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.by_year)),3);

    }

    private void Search(){





        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()==0){
                    movieList.clear();
                    binding.animProgress.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.emptyAnim.setVisibility(View.VISIBLE);
                }else {
                    binding.animProgress.setVisibility(View.VISIBLE);
                    binding.tvEmpty.setVisibility(View.GONE);
                    binding.emptyAnim.setVisibility(View.GONE);
                }

                if (binding.tabLayout.getSelectedTabPosition()==0 ){
                    //filter by name

                    searchViewModel.searchMovie("movie_name",newText);
                    searchViewModel.getSearchMutableLiveData().observe(getActivity(), new Observer<MovieResult>() {
                        @Override
                        public void onChanged(MovieResult movieResult) {

                            if (movieResult==null){

                                Methods.showSnackBar(getActivity(),getResources().getString(R.string.unable_connect_to_server)
                                        ,getResources().getColor(R.color.red));

                            }else {
                                movieList=movieResult.getMoviesList();

                                if (movieList.size()>0){

                                    binding.tvEmpty.setVisibility(View.GONE);
                                    binding.emptyAnim.setVisibility(View.GONE);
                                    binding.animProgress.setVisibility(View.GONE);

                                }else {

                                    binding.tvEmpty.setVisibility(View.VISIBLE);
                                    binding.emptyAnim.setVisibility(View.VISIBLE);
                                    binding.animProgress.setVisibility(View.GONE);
                                }

                                binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                                searchAdapter=new SearchAdapter(getContext(), movieList, false, new SearchAdapter.SearchItemOnClick() {
                                    @Override
                                    public void onItemClick(MovieModel model) {
                                        setIntent(model);
                                    }
                                });
                                binding.recyclerview.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                                binding.recyclerview.scheduleLayoutAnimation();

                            }

                        }
                    });
                }else if (binding.tabLayout.getSelectedTabPosition()==1){
                    //filter by name

                    searchViewModel.searchMovie("director",newText);
                    searchViewModel.getSearchMutableLiveData().observe(getActivity(), new Observer<MovieResult>() {
                        @Override
                        public void onChanged(MovieResult movieResult) {

                            if (movieResult==null){

                                Methods.showSnackBar(getActivity(),getResources().getString(R.string.unable_connect_to_server)
                                        ,getResources().getColor(R.color.red));

                            }else {
                                movieList=movieResult.getMoviesList();

                                if (movieList.size()>0){

                                    binding.tvEmpty.setVisibility(View.GONE);
                                    binding.emptyAnim.setVisibility(View.GONE);
                                    binding.animProgress.setVisibility(View.GONE);

                                }else {

                                    binding.tvEmpty.setVisibility(View.VISIBLE);
                                    binding.emptyAnim.setVisibility(View.VISIBLE);
                                    binding.animProgress.setVisibility(View.GONE);
                                }

                                binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                                searchAdapter=new SearchAdapter(getContext(), movieList, false, new SearchAdapter.SearchItemOnClick() {
                                    @Override
                                    public void onItemClick(MovieModel model) {
                                        setIntent(model);
                                    }
                                });
                                binding.recyclerview.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                                binding.recyclerview.scheduleLayoutAnimation();

                            }

                        }
                    });
                }else if (binding.tabLayout.getSelectedTabPosition()==2){
                    //filter by name

                    searchViewModel.searchMovie("genre",newText);
                    searchViewModel.getSearchMutableLiveData().observe(getActivity(), new Observer<MovieResult>() {
                        @Override
                        public void onChanged(MovieResult movieResult) {

                            if (movieResult==null){

                                Methods.showSnackBar(getActivity(),getResources().getString(R.string.unable_connect_to_server)
                                        ,getResources().getColor(R.color.red));

                            }else {
                                movieList=movieResult.getMoviesList();

                                if (movieList.size()>0){

                                    binding.tvEmpty.setVisibility(View.GONE);
                                    binding.emptyAnim.setVisibility(View.GONE);
                                    binding.animProgress.setVisibility(View.GONE);

                                }else {

                                    binding.tvEmpty.setVisibility(View.VISIBLE);
                                    binding.emptyAnim.setVisibility(View.VISIBLE);
                                    binding.animProgress.setVisibility(View.GONE);
                                }

                                binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                                searchAdapter=new SearchAdapter(getContext(), movieList, false, new SearchAdapter.SearchItemOnClick() {
                                    @Override
                                    public void onItemClick(MovieModel model) {
                                        setIntent(model);
                                    }
                                });
                                binding.recyclerview.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                                binding.recyclerview.scheduleLayoutAnimation();

                            }

                        }
                    });
                }
                else if (binding.tabLayout.getSelectedTabPosition()==3){
                    //filter by name

                    searchViewModel.searchMovie("year",newText);
                    searchViewModel.getSearchMutableLiveData().observe(getActivity(), new Observer<MovieResult>() {
                        @Override
                        public void onChanged(MovieResult movieResult) {

                            if (movieResult==null){

                                Methods.showSnackBar(getActivity(),getResources().getString(R.string.unable_connect_to_server)
                                        ,getResources().getColor(R.color.red));

                            }else {
                                movieList=movieResult.getMoviesList();

                                if (movieList.size()>0){

                                    binding.tvEmpty.setVisibility(View.GONE);
                                    binding.emptyAnim.setVisibility(View.GONE);
                                    binding.animProgress.setVisibility(View.GONE);

                                }else {

                                    binding.tvEmpty.setVisibility(View.VISIBLE);
                                    binding.emptyAnim.setVisibility(View.VISIBLE);
                                    binding.animProgress.setVisibility(View.GONE);
                                }

                                binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                                searchAdapter=new SearchAdapter(getContext(), movieList, false, new SearchAdapter.SearchItemOnClick() {
                                    @Override
                                    public void onItemClick(MovieModel model) {
                                        setIntent(model);
                                    }
                                });
                                binding.recyclerview.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                                binding.recyclerview.scheduleLayoutAnimation();

                            }

                        }
                    });
                }



                return false;
            }
        });

    }

    private void setIntent(MovieModel model){
        Intent intent=new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("type","movie");
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
}