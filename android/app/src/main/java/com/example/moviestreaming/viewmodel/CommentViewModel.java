package com.example.moviestreaming.viewmodel;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.CommentModel;
import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.Utils.Methods;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentViewModel extends ViewModel {

    private MutableLiveData<List<CommentModel>> commentMutableLiveData;
    private MutableLiveData<ResponseModel> sendCommentMutableLiveData;

    public MutableLiveData<List<CommentModel>> getCommentMutableLiveData(){
        if (commentMutableLiveData ==null){
            commentMutableLiveData =new MutableLiveData<>();
        }

        return commentMutableLiveData;
    }

    public MutableLiveData<ResponseModel> sendCommentMutableLiveData(){
        if (sendCommentMutableLiveData ==null){
            sendCommentMutableLiveData =new MutableLiveData<>();
        }

        return sendCommentMutableLiveData;
    }

    public void getComment(String movieId){

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
    }

    public void sendComment(String token,String movieId, String comment, String spoil){

        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);

        apiService.sendComment(token,movieId,comment,spoil)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseModel responseModel) {

                        sendCommentMutableLiveData.postValue(responseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        sendCommentMutableLiveData.postValue(null);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
