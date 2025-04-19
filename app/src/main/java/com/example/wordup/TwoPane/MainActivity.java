package com.example.wordup.TwoPane;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wordup.Models.PackModel;
import com.example.wordup.R;
import com.example.wordup.RecyclerViewInterface;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface, FragmentActionHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twopane);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_main, mainFragment)
                    .commit();
        }
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
        Intent intent = new Intent(this, com.example.wordup.Activities.SplashScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        // Không cần thiết vì xử lý ở adapter
    }
}
