package com.example.moviestreaming.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviestreaming.RoomDB.AppDatabase;
import com.example.moviestreaming.RoomDB.Favorites;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteViewModel extends AndroidViewModel {

    private MutableLiveData<List<Favorites>> favoriteMutableLiveData;
    private AppDatabase appDatabase;
    List<Favorites> favoritesList=new ArrayList<>();

    public FavoriteViewModel(Application application){
        super(application);

        if (favoriteMutableLiveData ==null){
            favoriteMutableLiveData =new MutableLiveData<>();
        }
        appDatabase=AppDatabase.getInstance(application.getApplicationContext());
    }

    public MutableLiveData<List<Favorites>>getAllItems(){
        favoritesList=appDatabase.dao().getAllItems();
        if (favoritesList.size()>0){
            favoriteMutableLiveData.postValue(favoritesList);
        }else {
            favoriteMutableLiveData.postValue(null);
        }

        return favoriteMutableLiveData;
    }





    public void insertItem(Favorites favorites){

        appDatabase.dao().insert(favorites);

    }

    public void deleteItem(Favorites favorites){

        appDatabase.dao().delete(favorites);

    }

    public boolean isFavorite(int id){
        if (appDatabase.dao().isFavorite(id)!=1){
            return false;
        }else {
            return true;
        }
    }
}
