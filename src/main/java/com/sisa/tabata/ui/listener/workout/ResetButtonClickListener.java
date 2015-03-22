package com.sisa.tabata.ui.listener.workout;

import android.view.View;
import android.widget.Toast;

import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;

/**
 * Created by Laca on 2015.02.28..
 */
@Singleton
public class ResetButtonClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Toast.makeText(TabataApplication.getAppContext(), "Hold to reset", Toast.LENGTH_SHORT).show();
    }
}
