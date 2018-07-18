package bapspatil.mockingbird.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

public class Question extends RealmObject implements Parcelable {
    @PrimaryKey private int questionID;
    private String questionText;
    private String firstAnswer, secondAnswer, thirdAnswer, fourthAnswer;
    private String correctAnswer;

    public Question() {
    }

    public Question(int questionID, String questionText, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, String correctAnswer) {
        this.questionID = questionID;
        this.questionText = questionText;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionID=" + questionID +
                ", questionText='" + questionText + '\'' +
                ", firstAnswer='" + firstAnswer + '\'' +
                ", secondAnswer='" + secondAnswer + '\'' +
                ", thirdAnswer='" + thirdAnswer + '\'' +
                ", fourthAnswer='" + fourthAnswer + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.questionID);
        dest.writeString(this.questionText);
        dest.writeString(this.firstAnswer);
        dest.writeString(this.secondAnswer);
        dest.writeString(this.thirdAnswer);
        dest.writeString(this.fourthAnswer);
        dest.writeString(this.correctAnswer);
    }

    protected Question(Parcel in) {
        this.questionID = in.readInt();
        this.questionText = in.readString();
        this.firstAnswer = in.readString();
        this.secondAnswer = in.readString();
        this.thirdAnswer = in.readString();
        this.fourthAnswer = in.readString();
        this.correctAnswer = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
