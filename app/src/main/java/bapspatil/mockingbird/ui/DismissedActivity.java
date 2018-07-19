package bapspatil.mockingbird.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import bapspatil.mockingbird.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DismissedActivity extends AppCompatActivity {
    @BindView(R.id.closeTextView)
    TextView closeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismissed);
        ButterKnife.bind(this);
        hideSystemUI();
        closeTextView.setOnClickListener(v -> finish());
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

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }
}
