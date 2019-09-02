package com.example.marvelapp;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication INSTANCE = null;

    public MyApplication() {
        super();
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }


    public static MyApplication getINSTANCE() {
        return INSTANCE;
    }
}
