package com.example.wordup;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wordup.Models.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private MediaPlayer mp;
    private ImageButton btnSpeak;
    private Button btnNext;
    private TextView questionText;
    private ImageButton imageOption1, imageOption2;
    List<QuestionModel> questionModels = new ArrayList<>();
    int currentQuestionIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);

        questionText = findViewById(R.id.question1);
        btnSpeak = findViewById(R.id.btn_speak);
        btnNext = findViewById(R.id.btn_next);
        imageOption1 = findViewById(R.id.option1);
        imageOption2 = findViewById(R.id.option2);

        String category = getIntent().getStringExtra("category");
        TextView categoryTitle = findViewById(R.id.category_title);
        ImageView categoryImage = findViewById(R.id.category_image);
        categoryTitle.setText(category.substring(0, 1).toUpperCase()+category.substring(1));

        Log.d("MainActivity2", "Category received:" + category);
        loadQuestions(category);
        if (!questionModels.isEmpty()){
            showQuestion(0);
        }else{
            Toast.makeText(this, "No questions available for this category", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSpeak = findViewById(R.id.btn_speak);
        questionText = findViewById(R.id.question1);

        btnSpeak.setOnClickListener(view -> playSound());

        btnNext.setOnClickListener(view -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionModels.size()){
                showQuestion(currentQuestionIndex);
            }
            else{
                Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        imageOption1.setOnClickListener(view -> checkAnswer(0));
        imageOption2.setOnClickListener(view -> checkAnswer(1));
    }
    private void loadQuestions(String category){
        switch(category){
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
                Log.e("MainActivity2", "Unknown category:"+category);
                break;
        }
    }

    private void showQuestion(int index){
        QuestionModel q = questionModels.get(index);
        questionText.setText(q.getWord());

        int[] images = q.getImageOptions();
        imageOption1.setImageResource(images[0]);
        imageOption2.setImageResource(images[1]);
    }
    private void checkAnswer(int selectedIndex){
        QuestionModel q = questionModels.get(currentQuestionIndex);
        if (selectedIndex == q.getCorrectIndex()){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
        }
    }
    private void playSound() {
        if (questionModels.isEmpty()) {
            Log.e("MainActivity2", "No questions loaded!");
            return;
        }

        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }

        int soundRes = questionModels.get(currentQuestionIndex).getSoundResId();
        mp = MediaPlayer.create(this, soundRes);
        if (mp != null) {
            mp.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.release();
                mp = null;
            });
            mp.start();  // safe to call directly here
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
            mp = null;
        }
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}