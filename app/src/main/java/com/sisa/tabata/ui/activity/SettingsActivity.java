package com.sisa.tabata.ui.activity;

import com.sisa.tabata.R;

import android.content.Intent;
import android.os.Bundle;

/**
 * Settings Activity.
 *
 * @author Laszlo Sisa
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, WorkoutActivity.class));
        finish();
    }

}
