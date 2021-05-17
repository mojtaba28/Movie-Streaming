package com.example.moviestreaming.RoomDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class Favorites {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image_name")
    public String image_name;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "published")
    public String published;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "imdb_rank")
    public String imdb_rank;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "director")
    public String director;

    @ColumnInfo(name = "box_office")
    public String box_office;

    @ColumnInfo(name = "budget")
    public String budget;

    @ColumnInfo(name = "rate")
    public String rate;

    @ColumnInfo(name = "genre")
    public String genre;

    @ColumnInfo(name = "movie_preview")
    public String movie_preview;

    @ColumnInfo(name = "movie_poster")
    public String movie_poster;

    @ColumnInfo(name = "popular")
    public String popular;

//    @ColumnInfo(name = "is_favorite",defaultValue = "0")
//    public String is_favorite;

}
