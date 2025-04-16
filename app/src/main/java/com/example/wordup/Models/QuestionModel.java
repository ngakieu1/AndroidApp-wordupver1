package com.example.wordup.Models;

public class QuestionModel {
    String word;
    String pronunciation;
    int[] imageOptions;
    int correctIndex;

    int soundResId;
    public QuestionModel(String word, String pronunciation, int[] imageOptions, int correctIndex, int soundResId) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.imageOptions = imageOptions;
        this.correctIndex = correctIndex;
        this.soundResId = soundResId;
    }

    public String getWord() {
        return word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public int[] getImageOptions() {
        return imageOptions;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
    public int getSoundResId(){
        return soundResId;
    }
}

