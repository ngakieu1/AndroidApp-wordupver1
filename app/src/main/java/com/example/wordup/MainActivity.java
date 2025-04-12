package com.example.wordup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wordup.Activities.QuestionActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private CardView animals, weather, idioms, colours;

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    if (itemId == R.id.nav_home) {
                        // Handle home selection
                        return true;
                    } else if (itemId == R.id.nav_account) {
                        // Handle account selection
                        return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize views
        bottomNavigationView = findViewById(R.id.bottomNav);
        animals = findViewById(R.id.animals);
        weather = findViewById(R.id.weather);
        idioms = findViewById(R.id.idioms);
        colours = findViewById(R.id.colours);

        // Set up bottom navigation with the new listener
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);

        // Set click listeners for cards
        animals.setOnClickListener(view -> startQuestionActivity("animals"));
        weather.setOnClickListener(view -> startQuestionActivity("weather"));
        idioms.setOnClickListener(view -> startQuestionActivity("idioms"));
        colours.setOnClickListener(view -> startQuestionActivity("colours"));

        // Edge-to-edge handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startQuestionActivity(String category) {
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here if needed
        return false;
    }
}