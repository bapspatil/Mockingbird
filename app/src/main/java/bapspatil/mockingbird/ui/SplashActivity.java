package bapspatil.mockingbird.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import bapspatil.mockingbird.R;
import bapspatil.mockingbird.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.splashImageView) ImageView splashImageView;
    @BindView(R.id.splashTextView) TextView splashTextView;
    @BindView(R.id.userNameTil) TextInputLayout userNameTil;
    @BindView(R.id.userNameEt) TextInputEditText userNameEt;
    @BindView(R.id.greetTextView) TextView greetTextView;
    @BindView(R.id.nextFab) FloatingActionButton nextFab;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        hideSystemUI();
        splashTextView.setTypeface(ResourcesCompat.getFont(this, R.font.gaegu_bold));
        int SPLASH_TIME_OUT = 1000;

        RealmResults<User> usersReamResults = realm.where(User.class).findAll();
        if(usersReamResults.isEmpty()) {
            splashImageView.animate().translationYBy(350).setDuration(2000).withEndAction(() -> {
                animateUserNameInput();
                nextFab.setOnClickListener(v -> validateUserName());
            }).start();
            splashTextView.animate().alpha(0).setDuration(1000).start();
        } else {
            new Handler().postDelayed(() -> {
                Intent i = new Intent(SplashActivity.this, AlarmsActivity.class);
                String userName = Objects.requireNonNull(usersReamResults.first()).getName();
                i.putExtra("USER", userName);
                startActivity(i);
                finish();
            }, SPLASH_TIME_OUT);
        }
    }

    private void validateUserName() {
        String firstName = userNameEt.getText().toString().trim();
        if(firstName.isEmpty() && !firstName.equals("")) {
            userNameTil.setError("Umm...that's not a valid name.");
        } else if(firstName.length() > 15) {
            userNameTil.setError("That's too long. Can you just share your first name?");
        } else {
            userNameTil.setError(null);
            User user = new User(firstName);
            realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(user));
            Log.d("REALM_USER_ADDED", "User added: " + user.getName());
            Intent i = new Intent(SplashActivity.this, AlarmsActivity.class);
            i.putExtra("USER", user.getName());
            startActivity(i);
            finish();
        }
    }

    private void animateUserNameInput() {
        greetTextView.animate().alpha(1).translationYBy(165).setDuration(2000).start();
        userNameTil.setVisibility(View.VISIBLE);
        userNameTil.animate().alpha(1).translationYBy(-125).setDuration(2000).start();
        nextFab.setVisibility(View.VISIBLE);
        nextFab.animate().translationYBy(-150).setDuration(1000).start();
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
}
