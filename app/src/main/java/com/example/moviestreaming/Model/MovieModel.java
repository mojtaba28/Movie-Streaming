package com.example.moviestreaming.Model;

import com.google.gson.annotations.SerializedName;

public class MovieModel {

    @SerializedName("status")
    private String status;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("image_name")
    private String image_name;

    @SerializedName("time")
    private String time;

    @SerializedName("published")
    private String published;

    @SerializedName("category")
    private String category;

    @SerializedName("rate")
    private String rate;

    @SerializedName("imdb_rank")
    private String imdb_rank;

    @SerializedName("description")
    private String description;

    @SerializedName("director")
    private String director;

    @SerializedName("box_office")
    private String box_office;

    @SerializedName("budget")
    private String budget;

    @SerializedName("genre")
    private String genre;

    @SerializedName("movie_preview")
    private String movie_preview;

    @SerializedName("movie_poster")
    private String movie_poster;

    @SerializedName("popular")
    private String popular;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImdb_rank() {
        return imdb_rank;
    }

    public void setImdb_rank(String imdb_rank) {
        this.imdb_rank = imdb_rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getBox_office() {
        return box_office;
    }

    public void setBox_office(String box_office) {
        this.box_office = box_office;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMovie_preview() {
        return movie_preview;
    }

    public void setMovie_preview(String movie_preview) {
        this.movie_preview = movie_preview;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
