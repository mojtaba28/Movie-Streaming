package com.example.moviestreaming.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.Model.UserModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.Utils.SessionManager;
import com.example.moviestreaming.databinding.ActivityEditProfileBinding;
import com.example.moviestreaming.viewmodel.EditProfileViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    SessionManager sessionManager;
    EditProfileViewModel editProfileViewModel;
    private int PICK_IMAGE_REQUEST = 100;
    private Uri filePath;
    private Bitmap profileBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);
        sessionManager =new SessionManager(getApplicationContext());
        editProfileViewModel= new ViewModelProvider(this).get(EditProfileViewModel.class);
        binding.toolbarLayout.tvTitle.setText(getResources().getString(R.string.edit_profile));
        binding.toolbarLayout.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setInfo();
        updateInfo();
        uploadProfileImage();
        getProfileImage();
        removeProfileImage();
        backButton();

    }

    private void backButton() {

        binding.toolbarLayout.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });

    }

    private void uploadProfileImage() {
        PushDownAnim.setPushDownAnimTo(binding.imgProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withContext(EditProfileActivity.this)
                        .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }
                        }).check();
            }
        });
    }

    private void setInfo() {

        String name=sessionManager.getUserName();
        String lastName=sessionManager.getUserLastName();

        binding.edtName.setText(name);
        binding.edtLastName.setText(lastName);

    }

    private void getProfileImage(){
        String token=sessionManager.getUserToken();
        editProfileViewModel.getProfileImage(token);
        editProfileViewModel.getProfileImageMutableLiveData().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel!=null){
                    if (userModel.getStatus().equals("successful")){

                        String imageUrl=userModel.getProfileImage();
                        Log.i("profile_image",Constant.MAIN_URL+imageUrl);
                        Glide.with(getApplicationContext()).load(Constant.MAIN_URL+imageUrl)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.imgProfile);
                    }
                }

            }
        });
    }

    private void updateInfo() {

        PushDownAnim.setPushDownAnimTo(binding.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.edtName.getText().toString().equals(sessionManager.getUserName())
                        && binding.edtLastName.getText().toString().equals(sessionManager.getUserLastName()) && filePath==null){
                    Methods.showSnackBar
                            (EditProfileActivity.this,getResources().getString
                                    (R.string.no_changes_have_been_made),getResources().getColor(R.color.red));
                }else {
                    binding.animProgress.setVisibility(View.VISIBLE);
                    String name=binding.edtName.getText().toString();
                    String lastName=binding.edtLastName.getText().toString();
                    String userId=sessionManager.getUserId();
                    editProfileViewModel.updateProfile(name,lastName,userId);
                    editProfileViewModel.getUpdateProfileMutableLiveData().observe(EditProfileActivity.this, new Observer<ResponseModel>() {
                        @Override
                        public void onChanged(ResponseModel responseModel) {
                            binding.animProgress.setVisibility(View.GONE);
                            if (responseModel!=null){
                                if (responseModel.getStatus().equals("successful")){
                                    Methods.showSnackBar(EditProfileActivity.this,
                                            getResources().getString(R.string.profile_updated_successfully),
                                            getResources().getColor(R.color.green));
                                    sessionManager.setUserName(name);
                                    sessionManager.setUserLastName(lastName);
                                }else {

                                    Methods.showSnackBar(EditProfileActivity.this,
                                            getResources().getString(R.string.failed),
                                            getResources().getColor(R.color.red));
                                }
                            }else {
                                Methods.showSnackBar(EditProfileActivity.this,
                                        getResources().getString(R.string.unable_connect_to_server),
                                        getResources().getColor(R.color.red));
                            }
                        }
                    });
                    //upload profile
                    if(filePath!=null){

                        File file = new File(getPath(filePath));
                        // String path = getPath(filePath);
                        RequestBody requestFile =
                                RequestBody.create(MediaType.parse("multipart/form-data"), file);

                        // MultipartBody.Part is used to send also the actual file name
                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("image", file.getName(), requestFile);


                        // add another part within the multipart request
                        String token=sessionManager.getUserToken();
                        editProfileViewModel.uploadImage(token,body);
                        editProfileViewModel.getUploadImageMutableLiveData().observe(EditProfileActivity.this, new Observer<ResponseBody>() {
                            @Override
                            public void onChanged(ResponseBody responseBody) {
                                if (responseBody==null){
                                    Log.i("upload",responseBody.toString());
                                }else {
                                    Log.i("upload",responseBody.toString());
                                }
                            }
                        });

                    }

                }
            }
        });

    }

    private void removeProfileImage(){
        binding.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String token=sessionManager.getUserToken();
                editProfileViewModel.removeProfileImage(token);
                editProfileViewModel.getRemoveProfileImageMutableLiveData().observe(EditProfileActivity.this, new Observer<ResponseModel>() {
                    @Override
                    public void onChanged(ResponseModel responseModel) {
                        if (responseModel==null){

                            Methods.showSnackBar(EditProfileActivity.this,
                                    getResources().getString(R.string.unable_connect_to_server)
                                    ,getResources().getColor(R.color.red));

                        }else {
                            if (responseModel.getStatus().equals(getResources().getString(R.string.successful))){
                                binding.imgProfile.setImageResource(R.drawable.profile_ic3);
                            }else {
                                Methods.showSnackBar(EditProfileActivity.this,
                                        responseModel.getStatus(),getResources().getColor(R.color.red));
                            }
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath =data.getData();
            try {
                profileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                binding.imgProfile.setImageBitmap(profileBitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}