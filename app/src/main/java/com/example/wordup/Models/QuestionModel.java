package com.example.wordup.Models;

public class QuestionModel {
    private String question, option1, option2, correctAnswer;

    public QuestionModel(String question, String option1, String option2, String correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.correctAnswer = correctAnswer;
    }
    public QuestionModel(){

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
