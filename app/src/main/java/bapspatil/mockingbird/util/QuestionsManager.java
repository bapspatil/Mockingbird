package bapspatil.mockingbird.util;
/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

import android.util.Log;

import java.util.Random;

import bapspatil.mockingbird.model.Question;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class QuestionsManager {
    public static String[] questions = {
            "What is the sum of 24 and 50?",
            "What is the difference of 10 and 3?",
            "What is the product of 40 and 5?",
            "What is the quotient of 9 divided by 3?",
            "Which the biggest country in the world, geographically?",
            "Which is the biggest continent?",
            "What's the first name of Trump?",
            "What's the first name of Hitler?"
    };

    public static String[] firstAnswers = {
            "23",
            "7",
            "300",
            "2",
            "China",
            "Africa",
            "John",
            "Elon"
    };

    public static String[] secondAnswers = {
            "90",
            "5",
            "200",
            "6",
            "India",
            "Asia",
            "Jim",
            "Peter"
    };

    public static String[] thirdAnswers = {
            "64",
            "6",
            "400",
            "3",
            "Croatia",
            "Europe",
            "Adam",
            "Adolf"
    };

    public static String[] fourthAnswers = {
            "74",
            "8",
            "700",
            "9",
            "France",
            "South America",
            "Donald",
            "Adam"
    };

    public static String[] correctAnswers = {
            "74",
            "7",
            "200",
            "3",
            "China",
            "Asia",
            "Donald",
            "Adolf"
    };

    public static void addQuestionsToDatabase(Realm realm) {
        realm.executeTransactionAsync(realm1 -> {
            for (int i = 0; i < questions.length; i++) {
                Question question = new Question(i, questions[i], firstAnswers[i], secondAnswers[i], thirdAnswers[i], fourthAnswers[i], correctAnswers[i]);
                realm1.copyToRealmOrUpdate(question);
                Log.d("QUESTION_ADDED_TO_DB", question.toString());
            }
        });
    }

    public static Question getRandomQuestion(Realm realm) {
        Random random = new Random();
        RealmResults<Question> questionRealmResults = realm.where(Question.class).findAll();
        return questionRealmResults.get(random.nextInt(questionRealmResults.size()));
    }
}
