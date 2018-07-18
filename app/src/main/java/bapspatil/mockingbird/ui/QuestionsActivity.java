package bapspatil.mockingbird.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bapspatil.mockingbird.R;
import bapspatil.mockingbird.model.AlarmItem;
import bapspatil.mockingbird.util.Constants;
import butterknife.ButterKnife;
import io.realm.Realm;

public class QuestionsActivity extends AppCompatActivity {
    private Realm realm;
    private AlarmItem alarmItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        alarmItem = getIntent().getParcelableExtra(Constants.ALARMITEM_KEY);
    }
}
