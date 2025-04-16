package com.example.wordup;

import android.content.Intent;
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
import com.example.wordup.Adapters.PackAdapter;
import com.example.wordup.Models.PackModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

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

    ArrayList<PackModel> packModels = new ArrayList<>();

    int[] packImages = {R.drawable.whale, R.drawable.cloudy, R.drawable.languages, R.drawable.colour, R.drawable.clothes, R.drawable.numbers, R.drawable.car, R.drawable.technology};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setUpPackModels();

        PackAdapter adapter = new PackAdapter(this, packModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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


//        // Set up bottom navigation with the new listener
//        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);
//
//        // Set click listeners for cards
//        animals.setOnClickListener(view -> startQuestionActivity("animals"));
//        weather.setOnClickListener(view -> startQuestionActivity("weather"));
//        idioms.setOnClickListener(view -> startQuestionActivity("idioms"));
//        colours.setOnClickListener(view -> startQuestionActivity("colours"));
//
//        // Edge-to-edge handling
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_recycler_view), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//    private void startQuestionActivity(String category) {
//        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
//        intent.putExtra("category", category);
//        startActivity(intent);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here if needed
//        return false;
//    }
}