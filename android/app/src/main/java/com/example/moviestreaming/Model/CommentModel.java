package com.example.moviestreaming.Model;

import com.google.gson.annotations.SerializedName;

public class CommentModel {

    @SerializedName("id")
    private String id;
    @SerializedName("movie_id")
    private String movie_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("comment")
    private String comment;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;
    @SerializedName("spoil")
    private int spoil;
    @SerializedName("image")
    private String userImage;



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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSpoil() {
        return spoil;
    }

    public void setSpoil(int spoil) {
        this.spoil = spoil;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
