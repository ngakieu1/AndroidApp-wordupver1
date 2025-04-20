package com.example.wordup.Leaderboard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardManager {

    private final Context context;

    public LeaderboardManager(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public void saveScore(String playerName, int score) {
        if (!isNetworkAvailable()) {
            Toast.makeText(context, "Không có kết nối mạng để lưu điểm!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> scoreData = new HashMap<>();
        scoreData.put("name", playerName);
        scoreData.put("score", score);

        db.collection("leaderboard")
                .add(scoreData)
                .addOnSuccessListener(documentReference ->
                        Log.d("Firebase", "Score saved with ID: " + documentReference.getId()))
                .addOnFailureListener(e ->
                        Log.e("Firebase", "Error adding score", e));
    }

    public void loadTopScores() {
        if (!isNetworkAvailable()) {
            Toast.makeText(context, "Không có kết nối mạng để xem bảng xếp hạng!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("leaderboard")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String name = doc.getString("name");
                        Long score = doc.getLong("score");
                        Log.d("Leaderboard", name + ": " + score);
                    }
                })
                .addOnFailureListener(e ->
                        Log.e("Firebase", "Error fetching leaderboard", e));
    }

}


//(chưa sử dung)