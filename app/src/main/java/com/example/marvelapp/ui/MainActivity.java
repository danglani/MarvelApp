package com.example.marvelapp.ui;

import android.os.Bundle;

import com.example.marvelapp.R;
import com.example.marvelapp.ui.fragment.home_page.CharacterListFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private static final int COLUMNS_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharacterListFragment fragment = CharacterListFragment.newInstance(COLUMNS_COUNT);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_main_content, fragment);
        transaction.commit();
    }


}
