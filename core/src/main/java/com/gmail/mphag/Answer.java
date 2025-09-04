package com.gmail.mphag;

public class Answer {

    private boolean isCorrect;
    private int answerNumber;
    private String answer;

    public Answer(boolean isCorrect, String answer) {
        this.isCorrect = isCorrect;
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }
}
