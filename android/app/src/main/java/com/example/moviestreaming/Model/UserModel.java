package com.example.moviestreaming.Model;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("status")
    private String status;

    @SerializedName("id")
    private String user_id;

    @SerializedName("name")
    private String name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("token")
    private String token;

    @SerializedName("password")
    private String password;

    @SerializedName("image")
    private String profileImage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
