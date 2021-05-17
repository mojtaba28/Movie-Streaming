package com.example.moviestreaming.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


@androidx.room.Dao
public interface AppDao {

    @Query("SELECT * FROM favorite")
    List<Favorites> getAllItems();

    @Insert
    void insert(Favorites...model);

    @Delete
    void delete (Favorites model);

    @Query("SELECT EXISTS(SELECT 1 FROM FAVORITE WHERE id=:id )")
    int isFavorite(int id);
}
