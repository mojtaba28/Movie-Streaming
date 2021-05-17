package com.example.moviestreaming.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreaming.Connection.ApiClient;
import com.example.moviestreaming.Connection.ApiService;
import com.example.moviestreaming.Model.ResponseModel;
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

public class ProfileFragmentViewModel extends ViewModel {

    //CP=ChangePassword
    private MutableLiveData<ResponseModel> CPMutableLiveData;
    private MutableLiveData<UserModel> profileImageMutableLiveData;

    public MutableLiveData<ResponseModel> getCPMutableLiveData(){
        if (CPMutableLiveData ==null){
            CPMutableLiveData =new MutableLiveData<>();
        }
        return CPMutableLiveData;
    }

    public MutableLiveData<UserModel>getProfileImageMutableLiveData(){
        if (profileImageMutableLiveData==null){
            profileImageMutableLiveData=new MutableLiveData<>();
        }

        return profileImageMutableLiveData;
    }

    public void changePassword(String userId,String oldPassword,String newPassword){

        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.changePassword(userId,oldPassword,newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseModel responseModel) {

                        CPMutableLiveData.postValue(responseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        CPMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getProfileImage(String userId) {

        ApiService apiService = ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getProfileImage(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserModel userModel) {

                        profileImageMutableLiveData.postValue(userModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        profileImageMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
