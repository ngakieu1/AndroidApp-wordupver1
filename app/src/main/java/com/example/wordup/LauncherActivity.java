package com.example.wordup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.wordup.Notification.NotificationScheduler;
import android.Manifest;


public class LauncherActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationScheduler.scheduleDailyNotification(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float widthDp = metrics.widthPixels / metrics.density;

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean firstRun = prefs.getBoolean("firstRun", true);

        if (firstRun) {
            NotificationScheduler.scheduleDailyNotification(this);

            prefs.edit().putBoolean("firstRun", false).apply();
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            if (widthDp >= 600) {
                // Tablet
                intent = new Intent(this, com.example.wordup.TwoPane.MainActivity.class);
            } else {
                // Phone
                intent = new Intent(this, com.example.wordup.MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 300); // Delay 300ms
    }
}
