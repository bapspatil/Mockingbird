package bapspatil.mockingbird.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import bapspatil.mockingbird.R;
import bapspatil.mockingbird.model.AlarmItem;
import bapspatil.mockingbird.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class AlarmsActivity extends AppCompatActivity {
    @BindView(R.id.appBar) AppBarLayout appBar;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.alarmsRecyclerView) RecyclerView alarmsRecyclerView;
    @BindView(R.id.userNameTextView) TextView userNameTextView;
    @BindView(R.id.addAlarmFab) FloatingActionButton addAlarmFab;
    @BindView(R.id.placeholderLayout) ConstraintLayout placeholderLayout;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        setupAppBar();

        addAlarmFab.setOnClickListener(v -> {

        });
        RealmResults alarmsRealmResults = realm.where(AlarmItem.class).findAll();
        if(alarmsRealmResults.isEmpty()) {
            placeholderLayout.setVisibility(View.VISIBLE);
            alarmsRecyclerView.setVisibility(View.GONE);
            Log.d("ALARMITEMS_REALM_COUNT", "Empty");
        } else {
            placeholderLayout.setVisibility(View.GONE);
            alarmsRecyclerView.setVisibility(View.VISIBLE);
            Log.d("ALARMITEMS_REALM_COUNT", alarmsRealmResults.size() + "");
            populateRecyclerView();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setupAppBar() {
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_name));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbar.setExpandedTitleTypeface(ResourcesCompat.getFont(this, R.font.gaegu_bold));
        collapsingToolbar.setCollapsedTitleTypeface(ResourcesCompat.getFont(this, R.font.gaegu_bold));

        String userName = getIntent().getStringExtra(Constants.USER_KEY);
        userNameTextView.setText(getGreetingForTimeOfDay() + ",\n" + userName + "!");
    }

    private String getGreetingForTimeOfDay() {
        int hourOfTheDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(4 <= hourOfTheDay && hourOfTheDay < 12)
            return "Good morning";
        else if(12 <= hourOfTheDay && hourOfTheDay < 17)
            return "Good afternoon";
        else if(17 <= hourOfTheDay && hourOfTheDay < 20)
            return "Good evening";
        else
            return "Goodnight";
    }

    private void populateRecyclerView() {

    }
}
