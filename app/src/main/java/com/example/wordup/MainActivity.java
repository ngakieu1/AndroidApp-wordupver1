package com.example.wordup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.wordup.Activities.QuestionActivity;
import com.example.wordup.Activities.Account;
import com.example.wordup.Activities.LocaleHelper;
import com.example.wordup.Adapters.PackAdapter;
import com.example.wordup.Models.PackModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    private BottomNavigationView bottomNavigationView;
    private CardView animals, weather, idioms, colours;
    ArrayList<PackModel> packModels = new ArrayList<>();

    int[] packImages = {R.drawable.whale, R.drawable.cloudy, R.drawable.languages, R.drawable.colour, R.drawable.clothes, R.drawable.numbers, R.drawable.car, R.drawable.technology};
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences preferences = newBase.getSharedPreferences("user_prefs", MODE_PRIVATE);
        String lang = preferences.getString("language", "en");
        Context context = LocaleHelper.setLocale(newBase, lang);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        bottomNavigationView = findViewById(R.id.bottomNav);

        setUpPackModels();

        PackAdapter adapter = new PackAdapter(this, packModels, this, false );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //  Set BottomNavigationView listener properly here — not nested!
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    // Already on home, no action needed
                    return true;
                } else if (itemId == R.id.nav_account) {
                    startActivity(new Intent(MainActivity.this, Account.class));
                    return true;
                }
                return false;
            }
        });
    }
    private void setUpPackModels() {
        String[] packVocabs = getResources().getStringArray(R.array.category_vocab);
        for (int i = 0; i < packVocabs.length; i++) {
            packModels.add(new PackModel(packVocabs[i], packImages[i]));
        }
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);


        intent.putExtra("VOCAB", packModels.get(position).getPackName());
        intent.putExtra("IMAGE", packModels.get(position).getImage());
        intent.putExtra("category", packModels.get(position).getPackName());

        startActivity(intent);
    }


}