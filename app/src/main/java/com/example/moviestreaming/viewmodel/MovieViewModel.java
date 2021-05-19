package com.example.moviestreaming.viewmodel;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.Utils.Methods;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<MovieModel>> similarMovieMutableLiveData;
    private MutableLiveData<List<MovieModel>> newMovieMutableLiveData;
    private MutableLiveData<List<MovieModel>> IMDBMovieMutableLiveData;
    private MutableLiveData<List<MovieModel>> popularMovieMutableLiveData;
    private MutableLiveData<List<MovieModel>> movieByGenreMutableLiveData;


    public MutableLiveData<List<MovieModel>> getNewMovieMutableLiveData(){
        if (newMovieMutableLiveData==null){
            newMovieMutableLiveData=new MutableLiveData<>();
        }

        return newMovieMutableLiveData;
    }

    public MutableLiveData<List<MovieModel>> getIMDBMovieMutableLiveData(){
        if (IMDBMovieMutableLiveData==null){
            IMDBMovieMutableLiveData=new MutableLiveData<>();
        }

        return IMDBMovieMutableLiveData;
    }

    public MutableLiveData<List<MovieModel>> getPopularMovieMutableLiveData(){
        if (popularMovieMutableLiveData==null){
            popularMovieMutableLiveData=new MutableLiveData<>();
        }

        return popularMovieMutableLiveData;
    }

    public MutableLiveData<List<MovieModel>> getMovieByGenreMutableLiveData(){
        if (movieByGenreMutableLiveData==null){
            movieByGenreMutableLiveData=new MutableLiveData<>();
        }

        return movieByGenreMutableLiveData;
    }


    public void getNewMovie(String category){
        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getMovie(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<MovieModel> list) {

                        newMovieMutableLiveData.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        newMovieMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getIMDBMovie(String category){
        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getMovie(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<MovieModel> list) {

                        IMDBMovieMutableLiveData.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        IMDBMovieMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getPopularMovie(String popular){
        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getPopularMovie(popular)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<MovieModel> list) {

                        popularMovieMutableLiveData.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        popularMovieMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getMovieByGenre(String genre){
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

                        movieByGenreMutableLiveData.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        movieByGenreMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
