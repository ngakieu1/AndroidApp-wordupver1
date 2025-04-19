package com.example.wordup.TwoPane;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wordup.R;

public class QuestionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Bạn có thể xử lý nhận dữ liệu từ intent nếu cần ở đây
        // String packName = getIntent().getStringExtra("packName");
        // int imageResId = getIntent().getIntExtra("imageResId", R.drawable.default_img);
    }
}