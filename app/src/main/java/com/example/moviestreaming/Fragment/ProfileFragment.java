package com.example.moviestreaming.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreaming.Activity.AboutUsActivity;
import com.example.moviestreaming.Activity.EditProfileActivity;
import com.example.moviestreaming.Activity.FavoriteActivity;
import com.example.moviestreaming.Activity.HomeActivity;
import com.example.moviestreaming.Activity.LoginActivity;
import com.example.moviestreaming.Activity.RegisterActivity;
import com.example.moviestreaming.Model.ResponseModel;
import com.example.moviestreaming.Model.UserModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.Utils.Methods;
import com.example.moviestreaming.Utils.SessionManager;
import com.example.moviestreaming.databinding.DialogChangePasswordBinding;
import com.example.moviestreaming.databinding.FragmentProfileBinding;
import com.example.moviestreaming.viewmodel.ProfileFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class ProfileFragment extends Fragment {


    FragmentProfileBinding binding;
    SessionManager sessionManager;
    ProfileFragmentViewModel profileFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        View view= binding.getRoot();
        sessionManager=new SessionManager(getContext());
        profileFragmentViewModel= new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
        onClick();
        checkIsLogin();
        getProfileImage();

        return view;
    }


    private void checkIsLogin() {
        if (sessionManager.getLoggedIn()==true){
            binding.linearExit.setVisibility(View.VISIBLE);
            binding.viewAbout.setVisibility(View.VISIBLE);
            binding.linearEditProfile.setVisibility(View.VISIBLE);
            binding.viewEditProfile.setVisibility(View.VISIBLE);
            binding.linearChangePassword.setVisibility(View.VISIBLE);
            binding.viewChangePassword.setVisibility(View.VISIBLE);
            binding.tvRegister.setVisibility(View.GONE);
            binding.tvLogin.setVisibility(View.GONE);
            binding.tvName.setText(sessionManager.getUserName());
            binding.tvPhone.setText(sessionManager.getUserPhone());
            binding.tvPhone.setVisibility(View.VISIBLE);
            binding.tvName.setVisibility(View.VISIBLE);

            if (binding.tvName.getText().toString().isEmpty()){

                binding.tvName.setVisibility(View.GONE);
            }

        }else {
            binding.linearExit.setVisibility(View.GONE);
            binding.viewAbout.setVisibility(View.GONE);
            binding.tvPhone.setVisibility(View.GONE);
            binding.tvName.setVisibility(View.GONE);
            binding.linearEditProfile.setVisibility(View.GONE);
            binding.viewEditProfile.setVisibility(View.GONE);
            binding.linearChangePassword.setVisibility(View.GONE);
            binding.viewChangePassword.setVisibility(View.GONE);
            binding.tvRegister.setVisibility(View.VISIBLE);
            binding.tvLogin.setVisibility(View.VISIBLE);
        }

    }

    private void getProfileImage() {

        if (sessionManager.getLoggedIn()==true){
            String token=sessionManager.getUserToken();
            profileFragmentViewModel.getProfileImage(token);
            profileFragmentViewModel.getProfileImageMutableLiveData().observe(getActivity(), new Observer<UserModel>() {
                @Override
                public void onChanged(UserModel userModel) {
                    if (userModel!=null){
                        if (userModel.getStatus().equals("successful")){

                            String imageUrl=userModel.getProfileImage();
                            Log.i("profile_image", Constant.MAIN_URL+imageUrl);
                            Glide.with(getContext()).load(Constant.MAIN_URL+imageUrl)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .into(binding.imgProfile);
                        }else {
                            binding.imgProfile.setImageResource(R.drawable.profile_ic3);
                        }
                    }
                }
            });
        }
    }


    private void onClick() {
        binding.tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutUsActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        binding.tvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FavoriteActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });

        binding.linearExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);

                      alertDialog.setMessage(getResources().getString(R.string.exit_of_account))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.setLoggedIn(false);
                                Intent intent=new Intent(getContext(),HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                dialog.dismiss();
                                getActivity().overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();


            }
        });

        binding.linearEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });

        binding.linearChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogChangePasswordBinding dialogBinding=DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                        R.layout.dialog_change_password,(ViewGroup)binding.getRoot(),false );

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
                bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                bottomSheetDialog.setContentView(dialogBinding.getRoot());
                bottomSheetDialog.setCancelable(true);


                bottomSheetDialog.show();

                PushDownAnim.setPushDownAnimTo(dialogBinding.btnConfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String userId=sessionManager.getUserId();
                        String oldPassword=dialogBinding.edtOldPassword.getText().toString();
                        String newPassword=dialogBinding.edtNewPassword.getText().toString();
                        String confirmPassword=dialogBinding.edtConfirmNewPassword.getText().toString();

                        if (oldPassword.isEmpty()){

                            dialogBinding.edtOldPassword.setError(getResources().getString(R.string.enter_current_password));

                        }else if (newPassword.isEmpty()){

                            dialogBinding.edtNewPassword.setError(getResources().getString(R.string.enter_new_password));


                        }else if (confirmPassword.isEmpty()){

                            dialogBinding.edtConfirmNewPassword.setError(getResources().getString(R.string.enter_confirm_password));
                        }else if (!newPassword.equals(confirmPassword)){

                            dialogBinding.edtConfirmNewPassword.setError(getResources().getString
                                    (R.string.new_passwords_not_equal));
                        }else {


                            changePassword(userId,oldPassword,newPassword);
                            bottomSheetDialog.dismiss();

                        }

                    }
                });





            }

        });
    }

    private void changePassword(String userId,String oldPassword,String newPassword) {

        profileFragmentViewModel.changePassword(userId,oldPassword,newPassword);
        profileFragmentViewModel.getCPMutableLiveData().observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                if (responseModel==null){
                    Methods.showSnackBar(getActivity(),getResources().getString(R.string.unable_connect_to_server)
                    ,getResources().getColor(R.color.red));

                }else {

                    if (responseModel.getStatus().equals("successful")){

                        Methods.showSnackBar(getActivity(),getResources().getString(R.string.your_password_changed_successfully)
                                ,getResources().getColor(R.color.green));

                    }else {

                        Methods.showSnackBar(getActivity(),responseModel.getStatus()
                                ,getResources().getColor(R.color.red));
                    }

                }
            }
        });


    }



    @Override
    public void onResume() {
        super.onResume();
        checkIsLogin();
        getProfileImage();
    }

}