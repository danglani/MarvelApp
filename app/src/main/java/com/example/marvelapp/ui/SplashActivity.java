package com.example.marvelapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.marvelapp.R;
import com.example.marvelapp.utils.EncryptPreferencesMgmt;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {


    private static final long SPLASH_DURATION = 3000;
    @BindView(R.id.iv_character) ImageView ivCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ivCharacter.setImageResource(getRandomCharacter());

        EncryptPreferencesMgmt.getInstance().setTimeStamp(0);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }, SPLASH_DURATION);
    }


    private int getRandomCharacter() {
        final TypedArray imgs = getResources().obtainTypedArray(R.array.splash_characters);
        final Random rand = new Random();
        final int rndInt = rand.nextInt(imgs.length());
        int resId = imgs.getResourceId(rndInt, 0);
        imgs.recycle();
        return resId;

    }
}
