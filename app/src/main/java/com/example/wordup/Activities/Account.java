package com.example.wordup.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wordup.MainActivity;
import com.example.wordup.R;

import java.util.Locale;

public class Account extends AppCompatActivity {
    // command a
    TextView tvUserName, tvLanguage, tvHome;
    Button btnLogout;
    Spinner spinnerLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tải ngôn ngữ đã lưu trước khi setContentView
        loadLocale();

        // Thiết lập giao diện activity_account
        setContentView(R.layout.activity_account);

        tvUserName = findViewById(R.id.tvUserName);
        tvLanguage = findViewById(R.id.tvLanguage);
        btnLogout = findViewById(R.id.btnLogout);
        tvHome = findViewById(R.id.tvHome);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);

        // Các lựa chọn ngôn ngữ
        String[] languages = {"English", "Spanish", "Vietnamese"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        // Sự kiện thay đổi ngôn ngữ từ Spinner
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                Toast.makeText(Account.this, "Selected: " + selectedLanguage, Toast.LENGTH_SHORT).show();

                // Chuyển đổi ngôn ngữ khi chọn ngôn ngữ từ Spinner
                switch (selectedLanguage) {
                    case "English":
                        setLocale("en");
                        break;
                    case "Spanish":
                        setLocale("es");
                        break;
                    case "Vietnamese":
                        setLocale("vi");
                        break;
                }

                // Cập nhật giao diện sau khi thay đổi ngôn ngữ
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Sự kiện quay lại màn hình chính
        tvHome.setOnClickListener(v -> {
            Intent intent = new Intent(Account.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Sự kiện đăng xuất
        btnLogout.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear(); // Hoặc editor.remove("isLoggedIn");
            editor.apply();

            Toast.makeText(Account.this, "You have been logged out", Toast.LENGTH_SHORT).show();

            // Chuyển hướng về màn hình đăng nhập
            Intent intent = new Intent(Account.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    // Hàm thay đổi ngôn ngữ
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        // Lưu ngôn ngữ vào SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    // Hàm tải ngôn ngữ đã lưu
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en"); // Mặc định là Tiếng Anh
        setLocale(language);  // Áp dụng ngôn ngữ đã lưu
    }

    // Hàm cập nhật lại giao diện sau khi thay đổi ngôn ngữ
    private void updateUI() {
        // Làm mới các thành phần giao diện (TextViews, Buttons, v.v.)
        tvUserName.setText(getString(R.string.user_name));
        tvLanguage.setText(getString(R.string.language));
        btnLogout.setText(getString(R.string.log_out));
        tvHome.setText(getString(R.string.home));
    }
}
