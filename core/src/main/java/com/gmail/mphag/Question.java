package com.gmail.mphag;

public class Question {

    private String question;

    private Answer answer1;
    private Answer answer2;
    private Answer answer3;

    public Question(String question, Answer answer1, Answer answer2, Answer answer3) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;

    }

    public boolean answer(int number) {
        if(answer1.getAnswerNumber() == number) {
            if(answer1.isCorrect()) {
                return true;
            }
            return false;
        } else if(answer2.getAnswerNumber() == number) {
            if(answer2.isCorrect()) {
                return true;
            }
            return false;
        } else if(answer3.getAnswerNumber() == number) {
            if(answer3.isCorrect()) {
                return true;
            }
            return false;
        }
        return false;
    }

}
