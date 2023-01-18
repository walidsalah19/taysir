package com.example.taysir.Models;

public class OldComplaintModel extends NewComplaintModel{
    String Answer;

    public OldComplaintModel(String userName, String inquire, String inquireId, String userId, int inquireNum, String Answer) {
        super(userName, inquire, inquireId, userId, inquireNum);
        this.Answer = Answer;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
