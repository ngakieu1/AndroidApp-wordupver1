package com.example.wordup;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.util.Consumer;

import java.util.Map;

public class FirebaseHelper {
    private FirebaseFirestore db;

    public FirebaseHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public void saveUserData(String userId, Map<String, Object> data) {
        db.collection("users").document(userId).set(data)
                .addOnSuccessListener(aVoid -> Log.d("Firebase", "User data saved"))
                .addOnFailureListener(e -> Log.w("Firebase", "Error saving data", e));
    }

    @SuppressLint("RestrictedApi")
    public void getUserData(String userId, @SuppressLint("RestrictedApi") Consumer<Map<String, Object>> onResult) {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        onResult.accept(document.getData());
                    }
                });
    }
}
