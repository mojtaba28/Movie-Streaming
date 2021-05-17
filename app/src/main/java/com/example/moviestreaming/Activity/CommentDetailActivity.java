package com.example.moviestreaming.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.baoyz.widget.PullRefreshLayout;
import com.example.moviestreaming.Adapter.CommentDetailAdapter;
import com.example.moviestreaming.Model.CommentModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.Utils.SessionManager;
import com.example.moviestreaming.databinding.ActivityCommentDetailBinding;
import com.example.moviestreaming.databinding.DialogLoginBinding;
import com.example.moviestreaming.viewmodel.CommentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class CommentDetailActivity extends AppCompatActivity {

    ActivityCommentDetailBinding binding;
    CommentDetailAdapter commentDetailAdapter;
    CommentViewModel commentViewModel;
    public boolean isRefreshed=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_comment_detail);
        commentViewModel=new ViewModelProvider(this).get(CommentViewModel.class);
        binding.toolbarLayout.tvTitle.setText(getResources().getString(R.string.comments));
        getComment();
        sendButtonAnim();
        setRefresh();
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


    private void sendButtonAnim() {

        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    binding.btnSendComment.hide();
                else if (dy <= 0)
                    binding.btnSendComment.show();
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnSendComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SessionManager sessionManager=new SessionManager(getApplicationContext());
                if (sessionManager.getLoggedIn()==false){

                    DialogLoginBinding dialogBinding=DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()),
                            R.layout.dialog_login,(ViewGroup)binding.getRoot(),false );

                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CommentDetailActivity.this, R.style.BottomSheetDialog);
                    bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    bottomSheetDialog.setContentView(dialogBinding.getRoot());
                    bottomSheetDialog.setCancelable(true);


                    bottomSheetDialog.show();
                    PushDownAnim.setPushDownAnimTo(dialogBinding.tvLogin).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        }
                    });

                    PushDownAnim.setPushDownAnimTo(dialogBinding.tvRegister).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        }
                    });

                }else {

                    Intent intent=new Intent(getApplicationContext(),SendCommentActivity.class);
                    intent.putExtra("movie_id",getIntent().getStringExtra("movie_id"));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);

                }
            }

        });


    }

    private void getComment(){

        commentViewModel.getComment(getIntent().getStringExtra("movie_id"));
        commentViewModel.getCommentMutableLiveData().observe(this, new Observer<List<CommentModel>>() {
            @Override
            public void onChanged(List<CommentModel> list) {
                binding.animProgress.setVisibility(View.GONE);
                if (list==null){

                    Methods.showSnackBar(CommentDetailActivity.this,
                            getResources().getString(R.string.unable_connect_to_server),
                            getResources().getColor(R.color.red));



                }else {
                    if (list.size()>0){

                        binding.tvEmpty.setVisibility(View.GONE);
                        commentDetailAdapter=new CommentDetailAdapter(getApplicationContext(),list);
                        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        binding.recyclerview.setAdapter(commentDetailAdapter);
                        commentDetailAdapter.notifyDataSetChanged();
                        binding.recyclerview.scheduleLayoutAnimation();

                        Log.i("comment",list.toString());

                    }else {
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                        Log.i("comment",list.toString());
                    }
                }
            }
        });

    }

    private void setRefresh() {

        binding.swipeRefresh.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);

        binding.swipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getComment();
                        binding.swipeRefresh.setRefreshing(false);
                    }
                },3000);


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