package com.example.marvelapp.utils;

import android.content.SharedPreferences;

import com.example.marvelapp.MyApplication;
import com.google.crypto.tink.subtle.Hex;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class EncryptPreferencesMgmt {

    private static final EncryptPreferencesMgmt ourInstance = new EncryptPreferencesMgmt();

    public static synchronized EncryptPreferencesMgmt getInstance() {
        return ourInstance;
    }


    private EncryptPreferencesMgmt() {
    }

    public void setTimeStamp(long ts){
        try {
            String masterKeyAlias;
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences.Editor editor = EncryptedSharedPreferences.create("secret_shared_prefs",
                    masterKeyAlias,
                    MyApplication.getINSTANCE(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ).edit();

            editor.putLong(Constants.TIME_STAMP, ts).apply();

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public long getTimeStamp(){
        try {
            String masterKeyAlias;
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences preferences = EncryptedSharedPreferences.create("secret_shared_prefs",
                    masterKeyAlias,
                    MyApplication.getINSTANCE(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            return preferences.getLong(Constants.TIME_STAMP, 0);

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public String getMd5Hash(String publicApiKey, String privateApiKey){
        String concatenateKeys = getTimeStamp() + privateApiKey + publicApiKey;
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(concatenateKeys.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



}
