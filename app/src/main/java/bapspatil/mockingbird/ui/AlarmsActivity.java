package bapspatil.mockingbird.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.aviran.cookiebar2.CookieBar;

import java.util.Calendar;
import java.util.Random;

import bapspatil.mockingbird.R;
import bapspatil.mockingbird.adapter.AlarmsRecyclerViewAdapter;
import bapspatil.mockingbird.model.AlarmItem;
import bapspatil.mockingbird.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class AlarmsActivity extends AppCompatActivity {
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.alarmsRecyclerView)
    RecyclerView alarmsRecyclerView;
    @BindView(R.id.userNameTextView)
    TextView userNameTextView;
    @BindView(R.id.addAlarmFab)
    FloatingActionButton addAlarmFab;
    @BindView(R.id.placeholderLayout)
    ConstraintLayout placeholderLayout;

    private Realm realm;
    private RealmResults<AlarmItem> alarmsRealmResults;
    private int mHourOfDay, mMinute;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        random = new Random();
        setupAppBar();

        alarmsRealmResults = realm.where(AlarmItem.class).findAll();
        AlarmsRecyclerViewAdapter alarmsAdapter = new AlarmsRecyclerViewAdapter(alarmsRealmResults, true, true, alarmItem -> {
            deleteAlarm(alarmItem);
        });

        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        alarmsRecyclerView.setAdapter(alarmsAdapter);
        alarmsRecyclerView.setItemAnimator(new LandingAnimator());

        addAlarmFab.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mHourOfDay = calendar.get(Calendar.HOUR_OF_DAY) + 1;
            mMinute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                addAlarmItem(hourOfDay, minute);
                setUiBasedOnAlarms(alarmsRealmResults);
            }, mHourOfDay, mMinute, false);
            timePickerDialog.show();
        });
        setUiBasedOnAlarms(alarmsRealmResults);
    }

    private void setUiBasedOnAlarms(RealmResults alarmsRealmResults) {
        if (alarmsRealmResults.isEmpty()) {
            placeholderLayout.setVisibility(View.VISIBLE);
            alarmsRecyclerView.setVisibility(View.GONE);
            Log.d("ALARMITEMS_REALM_COUNT", "Empty");
        } else {
            placeholderLayout.setVisibility(View.GONE);
            alarmsRecyclerView.setVisibility(View.VISIBLE);
            Log.d("ALARMITEMS_REALM_COUNT", alarmsRealmResults.size() + "");
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
        if (4 <= hourOfTheDay && hourOfTheDay < 12)
            return "Good morning";
        else if (12 <= hourOfTheDay && hourOfTheDay < 17)
            return "Good afternoon";
        else if (17 <= hourOfTheDay && hourOfTheDay < 20)
            return "Good evening";
        else
            return "Goodnight";
    }

    private void addAlarmItem(int hourOfDay, int minute) {
        // Generating a unique random request code
        int requestCode = random.nextInt(10000) + 1000;
        RealmResults<AlarmItem> alarmItems = realm.where(AlarmItem.class).equalTo("requestCode", requestCode).findAll();
        while (!alarmItems.isEmpty()) {
            requestCode = random.nextInt(10000) + 1000;
            alarmItems = realm.where(AlarmItem.class).equalTo("requestCode", requestCode).findAll();
        }

        String friendlyTime = getFriendlyTime(hourOfDay, minute);

        Calendar timeSet = Calendar.getInstance();
        timeSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        timeSet.set(Calendar.MINUTE, minute);
        if (timeSet.before(Calendar.getInstance())) { // If time set is before the current time, set the alarm to the same time the next day
            timeSet.add(Calendar.DAY_OF_YEAR, 1);
        }
        // Creating an AlarmItem object
        AlarmItem alarmItem = new AlarmItem(requestCode, timeSet.getTimeInMillis(), friendlyTime);
        Log.d("ALARMITEM_CREATED", alarmItem.toString());
        // Storing the AlarmItem object in the Realm database
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(alarmItem));

        // Setting the alarm with the AlarmManager API
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intentToQuestionsActivity = new Intent(getApplicationContext(), QuestionsActivity.class);
        PendingIntent alarmPendingIntent = PendingIntent.getActivity(getApplicationContext(), alarmItem.getRequestCode(), intentToQuestionsActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeSet.getTimeInMillis(), alarmPendingIntent);
        }
        Log.d("ALARM_SET_AT", timeSet.getTime().toString());

        CookieBar.build(this)
                .setTitle("Alarm set at " + friendlyTime + ".")
                .setMessage("Until then, go get some sleep, walk around, or take a chill pill!")
                .setDuration(6000)
                .setBackgroundColor(R.color.darkGoldenrod)
                .show();
    }

    @NonNull
    private String getFriendlyTime(int hourOfDay, int minute) {
        String friendlyMinute;
        if (minute < 10)
            friendlyMinute = "0" + minute;
        else
            friendlyMinute = "" + minute;

        String friendlyTime;
        if (hourOfDay == 0) {
            friendlyTime = 12 + ":" + friendlyMinute + " AM";
        } else if (hourOfDay < 12) {
            if (hourOfDay < 10)
                friendlyTime = "0" + hourOfDay + ":" + friendlyMinute + " AM";
            else
                friendlyTime = hourOfDay + ":" + friendlyMinute + " AM";
        } else if (hourOfDay == 12) {
            friendlyTime = hourOfDay + ":" + friendlyMinute + " PM";
        } else {
            if (hourOfDay < 22)
                friendlyTime = "0" + (hourOfDay - 12) + ":" + friendlyMinute + " PM";
            else
                friendlyTime = (hourOfDay - 12) + ":" + friendlyMinute + " PM";
        }

        return friendlyTime;
    }

    private void deleteAlarm(AlarmItem alarmItem) {
        // Cancel the alarm with AlarmManager API
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intentToQuestionsActivity = new Intent(getApplicationContext(), QuestionsActivity.class);
        PendingIntent canceledAlarmPendingIntent = PendingIntent.getActivity(getApplicationContext(), alarmItem.getRequestCode(), intentToQuestionsActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.cancel(canceledAlarmPendingIntent);
        }

        // Delete alarm from the Realm database
        realm.executeTransaction(realm1 -> alarmItem.deleteFromRealm());

        // Inform the user that the alarm has been deleted
        CookieBar.build(this)
                .setTitle("Alarm deleted!")
                .setMessage("Your alarm was deleted. No hard feelings.")
                .setDuration(3000)
                .setBackgroundColor(R.color.red)
                .show();

        // Check if there are zero alarms, show placeholder if yes
        setUiBasedOnAlarms(alarmsRealmResults);
    }
}
