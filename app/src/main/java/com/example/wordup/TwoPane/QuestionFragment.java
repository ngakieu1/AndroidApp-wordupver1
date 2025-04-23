package com.example.wordup.TwoPane;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordup.Activities.LocaleHelper;
import com.example.wordup.Models.QuestionModel;
import com.example.wordup.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {

    private MediaPlayer mp;
    private ImageButton btnSpeak;
    private Button btnNext;
    private TextView questionText;
    private ImageButton imageOption1, imageOption2;

    private List<QuestionModel> questionModels = new ArrayList<>();
    private int currentQuestionIndex = 0;

    public QuestionFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // View references
        questionText = view.findViewById(R.id.question1);
        btnSpeak = view.findViewById(R.id.btn_speak);
        btnNext = view.findViewById(R.id.btn_next);
        imageOption1 = view.findViewById(R.id.option1);
        imageOption2 = view.findViewById(R.id.option2);

        Bundle args = getArguments();
        if (args != null) {
            String packName = args.getString("packName");
            TextView categoryTitle = view.findViewById(R.id.category_title);
            ImageView categoryImage = view.findViewById(R.id.category_image);

            if (categoryTitle != null) categoryTitle.setText(capitalize(packName));
            if (categoryImage != null && args.containsKey("imageResId")) {
                categoryImage.setImageResource(args.getInt("imageResId"));
            }

            loadQuestions(packName);
            if (!questionModels.isEmpty()) {
                showQuestion(0);
            } else {
                Toast.makeText(getContext(), "No questions available", Toast.LENGTH_SHORT).show();
            }
        }

        btnSpeak.setOnClickListener(v -> playSound());

        btnNext.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionModels.size()) {
                showQuestion(currentQuestionIndex);
            } else {
                Toast.makeText(getContext(), "Quiz Finished!", Toast.LENGTH_SHORT).show();
            }
        });

        imageOption1.setOnClickListener(v -> checkAnswer(0));
        imageOption2.setOnClickListener(v -> checkAnswer(1));
    }

    private void loadQuestions(String category) {
        questionModels.clear(); // important
        switch (category) {
            case "Animals":
                questionModels.add(new QuestionModel("hippopotamus", "/ˌhɪp.əˈpɒt.ə.məs/",
                        new int[]{R.drawable.hippo, R.drawable.whale}, 0, R.raw.hippopotamus));
                questionModels.add(new QuestionModel("zebra", "/ˈziː.brə/",
                        new int[]{R.drawable.tiger, R.drawable.zebra}, 1, R.raw.zebra));
                break;
            case "Weather":
                questionModels.add(new QuestionModel("downpour", "/ˈdaʊn.pɔːr/",
                        new int[]{R.drawable.downpour, R.drawable.clothes}, 0, R.raw.downpour));
                questionModels.add(new QuestionModel("gale", "/ɡeɪl/",
                        new int[]{R.drawable.cloudy, R.drawable.gale}, 1, R.raw.gale));
                break;
            default:
                Log.e("QuestionFragment", "Unknown category: " + category);
                break;
        }
    }

    private void showQuestion(int index) {
        QuestionModel q = questionModels.get(index);
        questionText.setText(q.getWord());
        int[] images = q.getImageOptions();
        imageOption1.setImageResource(images[0]);
        imageOption2.setImageResource(images[1]);
    }

    private void showToastBelowNextButton(String message) {
        View nextButton = getView().findViewById(R.id.btn_next); // ID của nút Next
        if (nextButton == null) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Tạo custom toast view
        TextView textView = new TextView(getContext());
        textView.setText(message);
        textView.setBackgroundResource(android.R.drawable.toast_frame);
        textView.setPadding(40, 20, 40, 20);
        textView.setGravity(Gravity.CENTER);

        Toast toast = new Toast(getContext());
        toast.setView(textView);
        toast.setDuration(Toast.LENGTH_SHORT);

        // Định vị dựa trên vị trí của nút Next
        nextButton.post(() -> {
            int[] location = new int[2];
            nextButton.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            int width = nextButton.getWidth();
            int height = nextButton.getHeight();

            toast.setGravity(Gravity.TOP | Gravity.START,
                    x + width / 2 - 80,
                    y + height + 300);

            toast.show();
        });
    }


    private void checkAnswer(int selectedIndex) {
        QuestionModel q = questionModels.get(currentQuestionIndex);
        if (selectedIndex == q.getCorrectIndex()) {
            showToastBelowNextButton("Correct!");
        } else {
            showToastBelowNextButton("Wrong! ");
        }
    }





    private void playSound() {
        if (questionModels.isEmpty()) return;

        if (mp != null) {
            if (mp.isPlaying()) mp.stop();
            mp.release();
        }

        int soundRes = questionModels.get(currentQuestionIndex).getSoundResId();
        mp = MediaPlayer.create(getContext(), soundRes);
        if (mp != null) {
            mp.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.release();
                mp = null;
            });
            mp.start();
        }
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return "";
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mp != null) {
            if (mp.isPlaying()) mp.stop();
            mp.release();
            mp = null;
        }
    }
}
