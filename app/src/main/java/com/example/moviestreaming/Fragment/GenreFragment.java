package com.example.moviestreaming.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreaming.Activity.MovieActivity;
import com.example.moviestreaming.Adapter.GenreAdapter;
import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.databinding.FragmentGenreBinding;
import com.example.moviestreaming.viewmodel.GenreViewModel;

import java.util.ArrayList;
import java.util.List;


public class GenreFragment extends Fragment {


    public static FragmentGenreBinding binding;
    GenreViewModel genreViewModel;

    //Genre
    List<GenreModel> genreList = new ArrayList<>();
    GenreAdapter genreAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_genre,container,false);
        View view=binding.getRoot();
        genreViewModel=new ViewModelProvider(this).get(GenreViewModel.class);
        getGenre();



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getGenre() {
        binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        genreViewModel.getGenre();
        genreViewModel.getGenreMutableLiveData().observe(getActivity(), new Observer<List<GenreModel>>() {
            @Override
            public void onChanged(List<GenreModel> list) {
                if (list==null){

                    Methods.showSnackBar(getActivity(),
                            getResources().getString(R.string.unable_connect_to_server),
                            getResources().getColor(R.color.red));

                }else {

                    if (list.size()>0){
                        binding.animProgress.setVisibility(View.GONE);
                        genreList=list;
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
                        binding.recyclerview.setAdapter(genreAdapter);
                        genreAdapter.notifyDataSetChanged();
                        binding.recyclerview.scheduleLayoutAnimation();
                    }

                }

            }
        });


    }


}