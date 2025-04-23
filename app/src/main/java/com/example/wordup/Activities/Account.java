package com.example.wordup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.wordup.MainActivity;
import com.example.wordup.R;

import java.io.File;
import java.io.FileOutputStream;

public class Account extends AppCompatActivity {

    TextView tvUserName, tvLanguage, tvHome;
    Button btnLogout, btnShareFacebook;
    Spinner spinnerLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);

        tvUserName = findViewById(R.id.tvUserName);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvHome = findViewById(R.id.tvHome);
        btnLogout = findViewById(R.id.btnLogout);
        btnShareFacebook = findViewById(R.id.btnShareFacebook);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);

        String[] languages = {"English", "Spanish", "Vietnamese"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String currentLang = preferences.getString("language", "en");
        int selectedIndex = 0;
        if (currentLang.equals("es")) selectedIndex = 1;
        else if (currentLang.equals("vi")) selectedIndex = 2;
        spinnerLanguage.setSelection(selectedIndex);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                String langCode = "en";
                switch (selectedLanguage) {
                    case "English": langCode = "en"; break;
                    case "Spanish": langCode = "es"; break;
                    case "Vietnamese": langCode = "vi"; break;
                }

                if (!langCode.equals(currentLang)) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("language", langCode);
                    editor.apply();

                    LocaleHelper.setLocale(Account.this, langCode);
                    Toast.makeText(Account.this, "Language changed. Restarting app...", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Account.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finishAffinity();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        tvHome.setOnClickListener(v -> {
            Intent intent = new Intent(Account.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(Account.this, "You have been logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Account.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Chia sẻ ảnh và text lên Facebook
        btnShareFacebook.setOnClickListener(v -> shareImageAndText());
    }

    private void shareImageAndText() {
        try {
            // Tạo bitmap từ ảnh logo
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

            // Ghi ra file tạm trong bộ nhớ cache
            File cachePath = new File(getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            // Tạo URI từ FileProvider
            Uri contentUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    file
            );

            // Gọi helper để chia sẻ
            String message = "Check out my progress on WordUp! 🚀";
            com.example.wordup.ShareHepler.FacebookShareHelper.shareImageAndText(this, contentUri, message);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to share image", Toast.LENGTH_SHORT).show();
        }
    }

}
