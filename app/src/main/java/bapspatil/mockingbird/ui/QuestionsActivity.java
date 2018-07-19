package bapspatil.mockingbird.ui;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.aviran.cookiebar2.CookieBar;

import java.util.Objects;

import bapspatil.mockingbird.R;
import bapspatil.mockingbird.model.AlarmItem;
import bapspatil.mockingbird.model.Question;
import bapspatil.mockingbird.model.User;
import bapspatil.mockingbird.util.Constants;
import bapspatil.mockingbird.util.QuestionsManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class QuestionsActivity extends AppCompatActivity {
    @BindView(R.id.alarmTimeTextView)
    TextView alarmTimeTextView;
    @BindView(R.id.questionTextView)
    TextView questionTextView;
    @BindView(R.id.firstAnswerTextView)
    TextView firstAnswerTextView;
    @BindView(R.id.secondAnswerTextView)
    TextView secondAnswerTextView;
    @BindView(R.id.thirdAnswerTextView)
    TextView thirdAnswerTextView;
    @BindView(R.id.fourthAnswerTextView)
    TextView fourthAnswerTextView;
    @BindView(R.id.containerLayout)
    LinearLayout containerLayout;

    private Realm realm;
    private AlarmItem alarmItem;
    private Question question;
    private MediaPlayer mediaPlayer;
    private boolean isCorrectAnswerSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        isCorrectAnswerSelected = false;
        // Hide system UI and block user touch
        hideSystemUI();

        realm = Realm.getDefaultInstance();
        alarmItem = getIntent().getParcelableExtra(Constants.ALARMITEM_KEY);

        startRinging();

        if (alarmItem != null) {
            alarmTimeTextView.setText(alarmItem.getFriendlyTimeSet());
        }

        setRandomQuestionToView();
        setClickListeners();
    }

    private void setRandomQuestionToView() {
        containerLayout.setAlpha(0);
        question = QuestionsManager.getRandomQuestion(realm);
        questionTextView.setText(question.getQuestionText());
        firstAnswerTextView.setText(question.getFirstAnswer());
        secondAnswerTextView.setText(question.getSecondAnswer());
        thirdAnswerTextView.setText(question.getThirdAnswer());
        fourthAnswerTextView.setText(question.getFourthAnswer());
        containerLayout.animate().alpha(1).setDuration(1500).start();
    }

    private void setClickListeners() {
        firstAnswerTextView.setOnClickListener(v -> checkIfAnswerIsCorrect(question.getFirstAnswer()));
        secondAnswerTextView.setOnClickListener(v -> checkIfAnswerIsCorrect(question.getSecondAnswer()));
        thirdAnswerTextView.setOnClickListener(v -> checkIfAnswerIsCorrect(question.getThirdAnswer()));
        fourthAnswerTextView.setOnClickListener(v -> checkIfAnswerIsCorrect(question.getFourthAnswer()));
    }

    private void checkIfAnswerIsCorrect(String answerGiven) {
        if (answerGiven.equals(question.getCorrectAnswer())) {
            isCorrectAnswerSelected = true;
            realm.executeTransaction(realm1 -> Objects.requireNonNull(realm1.where(User.class).findFirst()).setQuestionAnswered(true));
            // Stop ringing
            mediaPlayer.stop();
            // Delete alarm from Realm database
            if (alarmItem != null)
                alarmItem.deleteFromRealm();
            // Start DismissedActivity
            Intent intent = new Intent(this, DismissedActivity.class);
            Bundle animationBundle = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            startActivity(intent, animationBundle);
        } else {
            isCorrectAnswerSelected = false;
            realm.executeTransaction(realm1 -> Objects.requireNonNull(realm1.where(User.class).findFirst()).setQuestionAnswered(true));
            setRandomQuestionToView();
            CookieBar.build(this)
                    .setTitle("Wrong answer!")
                    .setMessage("Just to make sure you're awake, here's another question.")
                    .setDuration(2000)
                    .setBackgroundColor(R.color.red)
                    .show();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void startRinging() {
        // Setting it to full volume with AudioManager
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
        }
        // Playing the alarm with the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.mockingbird);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isCorrectAnswerSelected)
            startRinging();
    }

    @Override
    public void onAttachedToWindow() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    @Override
    public void onBackPressed() {
        showAnswerQuestionMessage();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        showAnswerQuestionMessage();
        return true;
    }

    private void showAnswerQuestionMessage() {
        CookieBar.build(this)
                .setTitle("Please answer the question.")
                .setMessage("Until then, the bird will keep on chirping! :P")
                .setDuration(2000)
                .setBackgroundColor(R.color.darkGoldenrod)
                .show();
    }
}
