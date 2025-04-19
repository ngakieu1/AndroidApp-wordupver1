//package com.example.wordup.Activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.res.ResourcesCompat;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.wordup.MainActivity;
//import com.example.wordup.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class SplashScreenActivity extends AppCompatActivity {
//
//    private TextView appName;
//    FirebaseAuth firebaseAuth;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        fEdgeToEdge.enable(this);
//        setContentView(R.layout.activity_splash_screen);
//        appName = findViewById(R.id.app_name);
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
//        appName.setAnimation(anim);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        new Handler().postDelayed(() -> {
//            if (currentUser == null) {
//
//                startActivity(new Intent(SplashScreenActivity.this, Register.class));
//            } else {
//
//                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//            }
//            finish();
//        }, 3000);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
//    }
//}
package com.example.wordup.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wordup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView appName;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        appName = findViewById(R.id.app_name);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        appName.setAnimation(anim);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        new Handler().postDelayed(() -> {
            if (currentUser == null) {
                startActivity(new Intent(SplashScreenActivity.this, Register.class));
            } else {
                if (isTablet()) {
                    // Tablet: mở TwoPane
                    startActivity(new Intent(SplashScreenActivity.this, com.example.wordup.TwoPane.MainActivity.class));
                } else {
                    // Phone: mở MainActivity thông thường
                    startActivity(new Intent(SplashScreenActivity.this, com.example.wordup.MainActivity.class));
                }
            }
            finish();
        }, 3000);
    }

    private boolean isTablet() {
        return getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }
}
