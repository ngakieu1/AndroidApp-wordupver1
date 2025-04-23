package com.example.wordup.TwoPane;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wordup.Activities.LocaleHelper;
import com.example.wordup.Models.PackModel;
import com.example.wordup.R;
import com.example.wordup.RecyclerViewInterface;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface, FragmentActionHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twopane);

        if (savedInstanceState == null ||
                "main".equals(getIntent().getStringExtra("load_fragment"))) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_main, new MainFragment())
                    .commit();
        }
    }

    private boolean isLoadMainFragmentFromIntent() {
        Intent intent = getIntent();
        return intent != null && "main".equals(intent.getStringExtra("load_fragment"));
    }

    private void loadMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_main, new MainFragment())
                .commit();
    }

    @Override
    public void showAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_detail, new AccountFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showQuestion(PackModel packModel) {
        QuestionFragment fragment = new QuestionFragment();

        Bundle bundle = new Bundle();
        bundle.putString("packName", packModel.getPackName());
        bundle.putInt("imageResId", packModel.getImage());
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_detail, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goHome() {
        // Load lại chính MainActivity và truyền cờ để load lại MainFragment
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("load_fragment", "main");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity(); // Đảm bảo thoát các activity khác
    }

    @Override
    public void onItemClick(int position) {
        // Không cần dùng nếu adapter đã xử lý
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences preferences = newBase.getSharedPreferences("user_prefs", MODE_PRIVATE);
        String lang = preferences.getString("language", "en");
        Context context = LocaleHelper.setLocale(newBase, lang);
        super.attachBaseContext(context);
    }
}
