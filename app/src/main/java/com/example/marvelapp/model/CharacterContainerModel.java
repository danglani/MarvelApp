package com.example.marvelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterContainerModel {

    @SerializedName("offset")
    private String offset;

    @SerializedName("limit")
    private int limit;

    @SerializedName("total")
    private int total;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<CharacterModel> results;


    public String getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public List<CharacterModel> getResults() {
        return results;
    }

}
