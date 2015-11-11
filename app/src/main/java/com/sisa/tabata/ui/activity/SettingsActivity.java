package com.sisa.tabata.ui.activity;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.provider.settings.SettingsCheckBoxProvider;

import android.content.Intent;
import android.os.Bundle;

/**
 * Settings Activity.
 *
 * @author Laszlo Sisa
 */
public class SettingsActivity extends BaseActivity {

    @Inject
    private SettingsCheckBoxProvider settingsCheckBoxProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initCheckBoxes();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, WorkoutActivity.class));
        finish();
    }

    private void initCheckBoxes() {
        settingsCheckBoxProvider.setUpCheckBoxes();
    }

}
