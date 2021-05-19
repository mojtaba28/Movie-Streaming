package com.example.moviestreaming.Model;

import com.google.gson.annotations.SerializedName;

public class ActorModel {

    @SerializedName("id")
    private String id;

    @SerializedName("movie_id")
    private String movie_id;

    @SerializedName("name")
    private String name;

    @SerializedName("image_name")
    private String image_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
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
}
