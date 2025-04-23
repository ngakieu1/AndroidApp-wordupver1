package com.example.wordup.TwoPane;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.wordup.TwoPane.LocaleHelper;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    private Context updateBaseContextLocale(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
        String language = preferences.getString("language", "en"); // Mặc định là tiếng Anh
        return LocaleHelper.setLocale(context, language);
    }
}