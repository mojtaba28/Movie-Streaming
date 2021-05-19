package com.example.moviestreaming.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;

import com.example.moviestreaming.Model.ActorModel;
import com.example.moviestreaming.Model.CommentModel;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.RoomDB.AppDatabase;
import com.example.moviestreaming.RoomDB.Favorites;
import com.example.moviestreaming.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Favorites>> favoriteMutableLiveData;
    private MutableLiveData<List<CommentModel>> commentMutableLiveData;
    private MutableLiveData<List<MovieModel>> similarMovieMutableLiveData;
    private MutableLiveData<List<ActorModel>> actorMutableLiveData;
    private AppDatabase appDatabase;
    List<Favorites> favoritesList=new ArrayList<>();

    public MovieDetailActivityViewModel(Application application){
        super(application);

        if (favoriteMutableLiveData ==null){
            favoriteMutableLiveData =new MutableLiveData<>();
        }
        if (commentMutableLiveData==null){
            commentMutableLiveData=new MutableLiveData<>();
        }
        if (similarMovieMutableLiveData==null){
            similarMovieMutableLiveData=new MutableLiveData<>();
        }

        if (actorMutableLiveData==null){
            actorMutableLiveData=new MutableLiveData<>();
        }
        appDatabase=AppDatabase.getInstance(application.getApplicationContext());
    }

    public MutableLiveData<List<Favorites>>getAllFavoriteItems(){
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

    public MutableLiveData<List<CommentModel>> getComment(String movieId){

        ApiService apiService = ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getComment(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CommentModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<CommentModel> commentModels) {
                        commentMutableLiveData.postValue(commentModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        commentMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return commentMutableLiveData;
    }

    public MutableLiveData<List<MovieModel>> getSimilarMovie(String genre){
        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getMovieByGenre(genre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<MovieModel> list) {

                        similarMovieMutableLiveData.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        similarMovieMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return similarMovieMutableLiveData;
    }

    public MutableLiveData<List<ActorModel>> getActors(String movieId){
        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getActors(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ActorModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<ActorModel> actorModels) {

                        actorMutableLiveData.postValue(actorModels);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        actorMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return actorMutableLiveData;
    }
}
