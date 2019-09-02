package com.example.marvelapp.network.api;

public interface APICallback<T> {
    void onSuccess(T object);
    void onFailure(String error);
}
