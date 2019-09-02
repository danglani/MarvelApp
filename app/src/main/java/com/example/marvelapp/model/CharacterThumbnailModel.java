package com.example.marvelapp.model;

import com.google.gson.annotations.SerializedName;

public class CharacterThumbnailModel {

    @SerializedName("path")
    private String path;

    @SerializedName("extension")
    private String extension;


    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }

    public String getFileName(){
        return path + "." + extension;
    }

}
