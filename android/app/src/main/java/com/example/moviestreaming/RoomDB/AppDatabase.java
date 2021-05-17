package com.example.moviestreaming.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorites.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase instance;

    public static AppDatabase getInstance(Context context){

        if (instance==null){
            instance= Room.databaseBuilder(context, AppDatabase.class,"MovieStreaming")
                    .allowMainThreadQueries()
                    .build();

        }
        return instance;
    }

   public abstract AppDao dao();
}
