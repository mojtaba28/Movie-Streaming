package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.databinding.ActivitySendInfoBinding;
import com.example.moviestreaming.viewmodel.RegisterViewModel;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class SendInfoActivity extends AppCompatActivity {

    ActivitySendInfoBinding binding;
    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_send_info);
        registerViewModel=new ViewModelProvider(this).get(RegisterViewModel.class);
        register();
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

    private void register() {

        PushDownAnim.setPushDownAnimTo(binding.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=binding.edtName.getText().toString();
                String lastName=binding.edtLastName.getText().toString();
                String password=binding.edtPassword.getText().toString();
                String confirmPassword=binding.edtConfirmPassword.getText().toString();
                String phone=getIntent().getStringExtra("phone");

                if (name.isEmpty()){
                    binding.edtName.setError(getResources().getString(R.string.enter_name));

                }else if (lastName.isEmpty()){
                    binding.edtLastName.setError(getResources().getString(R.string.enter_lastName));

                }else if (password.isEmpty()){
                    binding.edtPassword.setError(getResources().getString(R.string.enter_password));
                }

                else if (confirmPassword.isEmpty()){
                    binding.edtConfirmPassword.setError(getResources().getString(R.string.enter_confirm_password));
                }

                else if (!confirmPassword.equals(password)){

                    Methods.showSnackBar(SendInfoActivity.this,getResources().getString(R.string.passwords_not_equal), Color.RED);
                }

                else {

                    binding.animProgress.setVisibility(View.VISIBLE);

                    registerViewModel.register(name,lastName,password,phone);
                    registerViewModel.getRegisterMutableLiveData().observe(SendInfoActivity.this, new Observer<ResponseModel>() {
                        @Override
                        public void onChanged(ResponseModel responseModel) {
                            binding.animProgress.setVisibility(View.GONE);
                            if (responseModel!=null){

                                if (responseModel.getStatus().equals("successful")){
                                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_right,
                                            R.anim.slide_out_left);

                                }else {
                                    Methods.showSnackBar(SendInfoActivity.this,
                                            responseModel.getStatus(), Color.RED);
                                }

                            }else {
                                Methods.showSnackBar(SendInfoActivity.this,
                                        getResources().getString(R.string.unable_connect_to_server), Color.RED);
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