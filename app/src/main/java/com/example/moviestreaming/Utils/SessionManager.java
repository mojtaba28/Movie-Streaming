package com.example.moviestreaming.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    Context context;
    SharedPreferences sharedPreferences;
    Boolean loggedIn=false;

    public SessionManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
    }

    public Boolean getLoggedIn(){
        return sharedPreferences.getBoolean("login",false);
    }

    public void setLoggedIn(Boolean loggedIn){
        sharedPreferences.edit().putBoolean("login",loggedIn).apply();
    }


    public void setUserPhone(String phone){
        sharedPreferences.edit().putString("phone",phone).apply();

    }

    public void setUserToken(String token){
        sharedPreferences.edit().putString("token",token).apply();

    }

    public void setUserId(String userId){
        sharedPreferences.edit().putString("user_id",userId).apply();

    }

    public void setUserPassword(String password){
        sharedPreferences.edit().putString("password",password).apply();

    }

    public void setUserName(String name){
        sharedPreferences.edit().putString("name",name).apply();
    }

    public void setUserLastName(String lastName){
        sharedPreferences.edit().putString("last_name",lastName).apply();
    }

    public String getUserName(){
        return sharedPreferences.getString("name","");
    }

    public String getUserLastName(){
        return sharedPreferences.getString("last_name","");
    }

    public String getUserPhone(){
        return sharedPreferences.getString("phone","");
    }


    public String getUserToken(){
        return sharedPreferences.getString("token","");
    }

    public String getUserPassword(){
        return sharedPreferences.getString("password","");
    }


    public String getUserId(){
        return sharedPreferences.getString("user_id","");
    }



}
