package com.example.moviestreaming.Model;

import com.google.gson.annotations.SerializedName;

import java.security.PrivateKey;
import java.util.List;

public class MovieResult {

    @SerializedName("movies")
    private List<MovieModel> moviesList;

    public List<MovieModel> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<MovieModel> moviesList) {
        this.moviesList = moviesList;
    }


}
