package com.shades.shade.activities;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.shades.shade.R;
import com.shades.shade.utility.AppConstant;
import com.shades.shade.widgets.ShadeTextView;

public class SettingActivity extends ShadeBaseActivity implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private View parent_layout;
    private SwitchCompat switchSensorNotification;
    private SwitchCompat switchMorningeminder;
    private SwitchCompat switchEveningReminder;
    private ImageView topBar_batteryStatus;

    @Override
    protected void onUiLayout() {
        setContentView(R.layout.activity_settings);
        context = SettingActivity.this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onUiComponentInit() {
        ((ShadeTextView) findViewById(R.id.topBar_txt_title)).setText(getString(R.string.settings_title));
        parent_layout = findViewById(R.id.parent_layout);
        switchSensorNotification = (SwitchCompat) findViewById(R.id.settings_sensorCoveredNotification_switch);
        switchMorningeminder = (SwitchCompat) findViewById(R.id.settings_MorningReminder_switch);
        switchEveningReminder = (SwitchCompat) findViewById(R.id.settings_EveningReminder_switch);

        topBar_batteryStatus = (ImageView) findViewById(R.id.topBar_batteryStatus);
        topBar_batteryStatus.setImageResource(R.drawable.battery_full);//Battery statusChange
    }

    @Override
    protected void onListenersInit() {
        ((ImageView) findViewById(R.id.topBar_btn_back)).setOnClickListener(this);
        switchSensorNotification.setOnCheckedChangeListener(this);
        switchMorningeminder.setOnCheckedChangeListener(this);
        switchEveningReminder.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onListenersRemove() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topBar_btn_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.settings_sensorCoveredNotification_switch:
                AppConstant.showSnakeBarShortTIme(parent_layout, "Sensor Covered Notification : " + (b ? "ON" : "OFF"));
                break;
            case R.id.settings_MorningReminder_switch:
                AppConstant.showSnakeBarShortTIme(parent_layout, "Morning Reminder : " + (b ? "ON" : "OFF"));
                break;
            case R.id.settings_EveningReminder_switch:
                AppConstant.showSnakeBarShortTIme(parent_layout, "Evening Reminder : " + (b ? "ON" : "OFF"));
                break;
        }
    }
}
