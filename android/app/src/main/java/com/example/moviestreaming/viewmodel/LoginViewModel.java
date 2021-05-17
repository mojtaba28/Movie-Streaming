package com.example.moviestreaming.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.UserModel;
import com.example.moviestreaming.Utils.Constant;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<UserModel> loginMutableLiveData;

    public MutableLiveData<UserModel> getLoginMutableLiveData(){
        if (loginMutableLiveData==null){
            loginMutableLiveData=new MutableLiveData<>();
        }

        return loginMutableLiveData;
    }

    public void login(String phone,String password){
        ApiService apiService = ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.login(phone,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserModel userModel) {

                        loginMutableLiveData.postValue(userModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        loginMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
