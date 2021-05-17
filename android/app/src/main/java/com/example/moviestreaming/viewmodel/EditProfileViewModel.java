package com.example.moviestreaming.viewmodel;

import android.util.Log;

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
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileViewModel extends ViewModel {

    private MutableLiveData<ResponseModel> updateProfileMutableLiveData;
    private MutableLiveData<ResponseBody> uploadImageMutableLiveData;
    private MutableLiveData<UserModel> profileImageMutableLiveData;
    private MutableLiveData<ResponseModel> removeProfileImageMutableLiveData;

    public MutableLiveData<ResponseModel> getUpdateProfileMutableLiveData(){

        if (updateProfileMutableLiveData ==null){
            updateProfileMutableLiveData =new MutableLiveData<>();
        }

        return updateProfileMutableLiveData;
    }

    public MutableLiveData<ResponseBody>getUploadImageMutableLiveData(){
        if (uploadImageMutableLiveData==null){
            uploadImageMutableLiveData=new MutableLiveData<>();
        }

        return uploadImageMutableLiveData;
    }


    public MutableLiveData<UserModel>getProfileImageMutableLiveData(){
        if (profileImageMutableLiveData==null){
            profileImageMutableLiveData=new MutableLiveData<>();
        }

        return profileImageMutableLiveData;
    }

    public MutableLiveData<ResponseModel>getRemoveProfileImageMutableLiveData(){
        if (removeProfileImageMutableLiveData==null){
            removeProfileImageMutableLiveData=new MutableLiveData<>();
        }

        return removeProfileImageMutableLiveData;
    }

    public void updateProfile(String name,String lastName,String userId){

        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.updateProfile(name,lastName,userId).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseModel responseModel) {

                        updateProfileMutableLiveData.postValue(responseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        updateProfileMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void uploadImage(String token, MultipartBody.Part body){

        ApiService apiService= ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.uploadProfile(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        uploadImageMutableLiveData.postValue(responseBody);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        uploadImageMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getProfileImage(String token) {

        ApiService apiService = ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.getProfileImage(token)
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

    public void removeProfileImage(String token) {

        ApiService apiService = ApiClient.getRetrofitClient(Constant.MAIN_URL).create(ApiService.class);
        apiService.removeProfileImage(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseModel responseModel) {

                        removeProfileImageMutableLiveData.postValue(responseModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        removeProfileImageMutableLiveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
