package com.example.moviestreaming.Interface;

import com.example.moviestreaming.Model.GenreModel;

import java.util.List;

public interface GenreResponse {

    void getGenreList(List<GenreModel> list);
}
