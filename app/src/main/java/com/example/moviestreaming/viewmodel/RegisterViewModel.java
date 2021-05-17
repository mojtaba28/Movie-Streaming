package com.example.moviestreaming.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.Utils.Constant;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<ResponseModel> checkRegisterMutableLiveData;
    private MutableLiveData<ResponseModel> registerMutableLiveData;
    private MutableLiveData<String> verifyCodeMutableLiveData;

    public MutableLiveData<ResponseModel> getCheckRegisterMutableLiveData(){
        if (checkRegisterMutableLiveData ==null){
            checkRegisterMutableLiveData =new MutableLiveData<>();
        }

        return checkRegisterMutableLiveData;
    }

    public MutableLiveData<ResponseModel> getRegisterMutableLiveData(){
        if (registerMutableLiveData ==null){
            registerMutableLiveData =new MutableLiveData<>();
        }

        return registerMutableLiveData;
    }

    public MutableLiveData<String> getVerifyCodeMutableLiveData(){
        if (verifyCodeMutableLiveData ==null){
            verifyCodeMutableLiveData =new MutableLiveData<>();
        }

        return verifyCodeMutableLiveData;
    }

    public void checkRegister(String phone){
        ApiService apiService = ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.checkRegister(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseModel responseModel) {

                        checkRegisterMutableLiveData.postValue(responseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        checkRegisterMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void register(String name,String lastname,String password,String phone){
        ApiService apiService = ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.register(name,lastname,password,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseModel responseModel) {

                        registerMutableLiveData.postValue(responseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        registerMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void verifyCode(String apiKey,String receptor,String token,String template){
        ApiService apiService = ApiClient.getRetrofitClient(Constant.SMS_VERIFY_URL).create(ApiService.class);
        apiService.verifyCode(apiKey,receptor,token,template)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                        verifyCodeMutableLiveData.postValue(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        verifyCodeMutableLiveData.postValue(null);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
