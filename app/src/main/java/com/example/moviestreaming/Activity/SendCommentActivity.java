package com.example.moviestreaming.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.Utils.SessionManager;
import com.example.moviestreaming.databinding.ActivitySendCommentBinding;
import com.example.moviestreaming.viewmodel.CommentViewModel;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class SendCommentActivity extends AppCompatActivity {

    ActivitySendCommentBinding binding;
    CommentViewModel commentViewModel;
    String spoil="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_send_comment);
        binding.toolbarLayout.tvTitle.setText(getResources().getString(R.string.send_comment));
        binding.toolbarLayout.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        commentViewModel= new ViewModelProvider(this).get(CommentViewModel.class);
        sendComment();
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

    private void sendComment() {

        PushDownAnim.setPushDownAnimTo(binding.btnSendComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.edtComment.getText().toString().isEmpty()){
                    Methods.showSnackBar(SendCommentActivity.this,getResources().getString(R.string.empty_comment)
                            , Color.RED);

                }else {

                    binding.animProgress.setVisibility(View.VISIBLE);

                    SessionManager sessionManager=new SessionManager(getApplicationContext());


                    String movieId=getIntent().getStringExtra("movie_id");
                    String userId=sessionManager.getUserId();
                    String token=sessionManager.getUserToken();
                    String comment=binding.edtComment.getText().toString();
                    if (binding.spoilCheckBox.isChecked()){
                        spoil="1";
                    }else {
                        spoil="0";
                    }


                    commentViewModel.sendComment(token,movieId,comment,spoil);
                    commentViewModel.sendCommentMutableLiveData().observe(SendCommentActivity.this, new Observer<ResponseModel>() {
                        @Override
                        public void onChanged(ResponseModel responseModel) {
                            binding.animProgress.setVisibility(View.GONE);
                            if (responseModel!=null){
                                if (responseModel.getStatus().equals("successful")){
                                    Methods.showSnackBar(SendCommentActivity.this,getResources().getString
                                            (R.string.comment_sent_successfully), getResources().getColor(R.color.green));
                                    binding.edtComment.setText("");
                                }else {

                                    Methods.showSnackBar(SendCommentActivity.this,
                                            responseModel.getStatus(), Color.RED);

                                }
                            }else {
                                Methods.showSnackBar(SendCommentActivity.this,
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