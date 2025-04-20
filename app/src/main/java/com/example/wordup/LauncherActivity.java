package com.example.wordup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class LauncherActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float widthDp = metrics.widthPixels / metrics.density;

        if (widthDp >= 600) {
            // Tablet
            startActivity(new Intent(this, com.example.wordup.TwoPane.MainActivity.class));
        } else {
            // Phone
            startActivity(new Intent(this, com.example.wordup.MainActivity.class));
        }

        finish();
    }
}
