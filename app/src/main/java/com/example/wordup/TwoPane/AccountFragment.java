package com.example.wordup.TwoPane;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.wordup.Activities.LocaleHelper;
import com.example.wordup.Activities.Login;
import com.example.wordup.Activities.SplashScreenActivity;
import com.example.wordup.R;
import com.example.wordup.ShareHepler.FacebookShareHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;

public class AccountFragment extends Fragment {

    private TextView tvUserName, tvLanguage, tvHome;
    private Button btnLogout, btnShareFacebook;
    private Spinner spinnerLanguage;
    private boolean isFirstLoad = true;

    public AccountFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvLanguage = view.findViewById(R.id.tvLanguage);
        tvHome = view.findViewById(R.id.tvHome);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnShareFacebook = view.findViewById(R.id.btnShareFacebook);
        spinnerLanguage = view.findViewById(R.id.spinnerLanguage);

        String[] languages = {"English", "Spanish", "Vietnamese"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        SharedPreferences preferences = requireContext().getSharedPreferences("user_prefs", requireContext().MODE_PRIVATE);
        String currentLang = preferences.getString("language", "en");
        int selectedIndex = currentLang.equals("es") ? 1 : currentLang.equals("vi") ? 2 : 0;
        spinnerLanguage.setSelection(selectedIndex);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstLoad) {
                    isFirstLoad = false;
                    return;
                }

                String selectedLanguage = parent.getItemAtPosition(position).toString();
                String langCode = selectedLanguage.equals("Spanish") ? "es" :
                        selectedLanguage.equals("Vietnamese") ? "vi" : "en";

                if (!langCode.equals(currentLang)) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("language", langCode);
                    editor.apply();

                    LocaleHelper.setLocale(requireContext(), langCode);
                    Toast.makeText(requireContext(), "Language changed", Toast.LENGTH_SHORT).show();

                    // Restart MainActivity and load main fragment
                    Intent intent = new Intent(requireContext(), MainActivity.class);
                    intent.putExtra("load_fragment", "main");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    requireActivity().finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        tvHome.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SplashScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finishAffinity();
        });

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(requireContext(), "You have been logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        btnShareFacebook.setOnClickListener(v -> shareImageAndText());

        return view;
    }

    private void shareImageAndText() {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

            File cachePath = new File(requireContext().getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

            Uri contentUri = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().getPackageName() + ".fileprovider",
                    file
            );

            String message = "Check out my progress on WordUp! 🚀";
            FacebookShareHelper.shareImageAndText(requireContext(), contentUri, message);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to share image", Toast.LENGTH_SHORT).show();
        }
    }
}
