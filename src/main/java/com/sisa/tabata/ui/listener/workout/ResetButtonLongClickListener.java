package com.sisa.tabata.ui.listener.workout;

import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.28..
 */
@Singleton
public class ResetButtonLongClickListener implements View.OnLongClickListener {

    @Inject
    private PlayButtonClickListener playButtonClickListener;

    public ResetButtonLongClickListener(){
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public boolean onLongClick(View v) {
        playButtonClickListener.resetWorkout();
        return true;
    }
}
