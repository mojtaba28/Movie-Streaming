package com.example.moviestreaming.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.Utils.Constant;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreViewModel extends ViewModel {

    private MutableLiveData<List<GenreModel>> genreMutableLiveData;

    public MutableLiveData<List<GenreModel>> getGenreMutableLiveData(){
        if (genreMutableLiveData==null){
            genreMutableLiveData=new MutableLiveData<>();
        }

        return genreMutableLiveData;
    }

    public void getGenre(){
        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getGenre().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GenreModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<GenreModel> list) {

                        genreMutableLiveData.postValue(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        genreMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
