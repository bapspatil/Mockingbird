package bapspatil.mockingbird.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import bapspatil.mockingbird.R;
import bapspatil.mockingbird.model.AlarmItem;
import bapspatil.mockingbird.util.Constants;
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

    private Realm realm;
    private AlarmItem alarmItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        alarmItem = getIntent().getParcelableExtra(Constants.ALARMITEM_KEY);

        if(alarmItem != null) {
            alarmTimeTextView.setText(alarmItem.getFriendlyTimeSet());
        }
    }
}
