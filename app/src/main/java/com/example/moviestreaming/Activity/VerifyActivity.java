package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.databinding.ActivityVerifyBinding;
import com.example.moviestreaming.viewmodel.RegisterViewModel;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class VerifyActivity extends AppCompatActivity {

    ActivityVerifyBinding binding;
    Intent intent;
    public static ProgressBar progressBar;
    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_verify);
        registerViewModel=new ViewModelProvider(this).get(RegisterViewModel.class);
        intent=getIntent();
        progressBar=binding.progressBar;
        verifyCode();
        resendTimer();
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



   private void resendTimer(){


       binding.tvPhone.setText(intent.getStringExtra("phone"));
       new CountDownTimer(30000, 1000) {

           public void onTick(long millisUntilFinished) {
               binding.tvTime.setText("00:"+millisUntilFinished / 1000+"");
               //here you can have your logic to set text to edittext
           }

           public void onFinish() {
               binding.tvTime.setText(getResources().getString(R.string.resend));
               binding.tvTime.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       verifyCode();
                       resendTimer();
                   }
               });
           }

       }.start();
   }

   private void verifyCode(){

       String phone=intent.getStringExtra("phone");
       String token= Methods.generateToken();
       Log.i("token",token);
       registerViewModel.verifyCode(Constant.API_KEY,phone,token,Constant.SMS_TEMPLATE);
       registerViewModel.getVerifyCodeMutableLiveData();

       PushDownAnim.setPushDownAnimTo(binding.btnConfirm).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (binding.pinView.getText().toString().equals(token)){
                   binding.pinView.setLineColor(Color.GREEN);

                   Handler handler=new Handler();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           Intent intent=new Intent(VerifyActivity.this,SendInfoActivity.class);
                           intent.putExtra("phone",phone);
                           startActivity(intent);
                           overridePendingTransition(R.anim.slide_in_right,
                                   R.anim.slide_out_left);
                           finish();

                       }
                   },1000);


               }else if (binding.pinView.getText().toString().isEmpty()){

                   Methods.showSnackBar(VerifyActivity.this,getResources().getString(R.string.enter_verify_code),Color.RED);
               }
               else {

                   Methods.showSnackBar(VerifyActivity.this,getResources().getString(R.string.verify_code_not_correct),Color.RED);

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