package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.databinding.ActivityRegisterBinding;
import com.example.moviestreaming.viewmodel.RegisterViewModel;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    View contentView;
    RegisterViewModel registerViewModel;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_register);
        registerViewModel=new ViewModelProvider(this).get(RegisterViewModel.class);
        contentView=findViewById(android.R.id.content);
        activity=this;
        PushDownAnim.setPushDownAnimTo(binding.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        loginIntent();
        backButton();


    }

    private void loginIntent(){
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
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

    private void register() {

        String regexStr = "^[+]?[0-9]{10,13}$";

        String phone=binding.edtPhone.getText().toString();

        if(phone.length()<10 || phone.length()>13 || phone.matches(regexStr)==false ) {

           Methods.showSnackBar(RegisterActivity.this,getResources().getString(R.string.invalid_phone),Color.RED);


        }

        else {
            binding.animProgress.setVisibility(View.VISIBLE);
            registerViewModel.checkRegister(phone);
            registerViewModel.getCheckRegisterMutableLiveData().observe(this, new Observer<ResponseModel>() {
                @Override
                public void onChanged(ResponseModel responseModel) {
                    binding.animProgress.setVisibility(View.GONE);
                    if (responseModel!=null){

                        if (responseModel.getStatus().equals("exist")){
                            Methods.showSnackBar(RegisterActivity.this,
                                    getResources().getString(R.string.user_already_exist), Color.RED);
                        }else {
                            Intent intent=new Intent(getApplicationContext(),VerifyActivity.class);
                            intent.putExtra("phone",phone);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        }

                    }else {
                        Methods.showSnackBar(RegisterActivity.this,
                                getResources().getString(R.string.unable_connect_to_server),
                                getResources().getColor(R.color.red));
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}