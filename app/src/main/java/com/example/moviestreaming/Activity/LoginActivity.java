package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.moviestreaming.Model.UserModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.Utils.SessionManager;
import com.example.moviestreaming.databinding.ActivityLoginBinding;
import com.example.moviestreaming.viewmodel.LoginViewModel;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        activity =this;
        registerIntent();
        login();
        backButton();

    }

    private void backButton(){

        binding.toolbarLayout.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });

    }

    private void registerIntent() {

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
    }

    private void login() {


        PushDownAnim.setPushDownAnimTo(binding.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=binding.edtPhone.getText().toString();
                String password=binding.edtPassword.getText().toString();

                if (phone.isEmpty()){

                    Methods.showSnackBar(LoginActivity.this,getResources().getString(R.string.enter_phone), Color.RED);
                }else if (password.isEmpty()){

                    Methods.showSnackBar(LoginActivity.this,getResources().getString(R.string.enter_password), Color.RED);
                }else {

                    //api.login(phone,password,LoginActivity.this,requestQueue);

                    binding.animProgress.setVisibility(View.VISIBLE);
                    loginViewModel.login(phone,password);
                    loginViewModel.getLoginMutableLiveData().observe(LoginActivity.this, new Observer<UserModel>() {
                        @Override
                        public void onChanged(UserModel userModel) {
                            binding.animProgress.setVisibility(View.GONE);
                            if (userModel==null){
                                Methods.showSnackBar(LoginActivity.this,
                                        getResources().getString(R.string.unable_connect_to_server),
                                        getResources().getColor(R.color.red));
                            }else {

                                if (userModel.getStatus().equals("successful")){
                                    Log.i("response",userModel.getToken());
                                    SessionManager sessionManager=new SessionManager(getApplicationContext());
                                    sessionManager.setLoggedIn(true);
                                    sessionManager.setUserId(userModel.getUser_id());
                                    sessionManager.setUserName(userModel.getName());
                                    sessionManager.setUserPhone(phone);
                                    sessionManager.setUserToken(userModel.getToken());
                                    sessionManager.setUserPassword(password);
                                    sessionManager.setUserLastName(userModel.getLast_name());
                                    Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,
                                            R.anim.slide_out_left);
                                    finish();
                                }else {

                                    Methods.showSnackBar(LoginActivity.this,userModel.getStatus(),Color.RED);
                                }
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}