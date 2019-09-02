package com.example.marvelapp.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("code")
    private String code;

    @SerializedName("status")
    private String status;

    @SerializedName("copyright")
    private String copyright;

    @SerializedName("data")
    private CharacterContainerModel data;


    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public CharacterContainerModel getData() {
        return data;
    }

}
