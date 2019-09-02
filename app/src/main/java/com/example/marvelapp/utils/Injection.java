package com.example.marvelapp.utils;

import com.example.marvelapp.network.CharacterRepository;
import com.example.marvelapp.network.CharacterServiceImpl;

public class Injection {

    public static CharacterRepository provideCharacterRepository() {
        return CharacterRepository.getInstance(new CharacterServiceImpl());
    }
}
