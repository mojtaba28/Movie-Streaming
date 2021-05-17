package com.example.moviestreaming.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.MovieResult;
import com.example.moviestreaming.Utils.Constant;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<MovieResult> searchMutableLiveData;

    public MutableLiveData<MovieResult> getSearchMutableLiveData(){

        if (searchMutableLiveData ==null){
            searchMutableLiveData =new MutableLiveData<>();
        }

        return searchMutableLiveData;
    }

    public void searchMovie(String filter,String searchQuery){

        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.searchMovie(filter,searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MovieResult movieResult) {

                        searchMutableLiveData.postValue(movieResult);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        searchMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
