package com.example.moviestreaming.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("status")
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
