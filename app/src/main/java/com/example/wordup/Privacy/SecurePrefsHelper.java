package com.example.wordup.Privacy;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurePrefsHelper {

    private static final String PREF_NAME = "secure_prefs";
    private static SecurePrefsHelper instance;
    private final SharedPreferences sharedPreferences;

    private SecurePrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SecurePrefsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SecurePrefsHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Optional helper method if you want to access raw SharedPreferences
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

}
