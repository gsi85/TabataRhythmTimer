package com.sisa.tabata.ui.listener.workout;

import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.dialog.VolumeControlDialog;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.26..
 */
@Singleton
public class VolumeButtonClickListener implements View.OnClickListener {

    @Inject
    private VolumeControlDialog volumeControlDialog;
    private WorkoutActivity workoutActivity;

    public VolumeButtonClickListener(){
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onClick(View view) {
        volumeControlDialog.show(workoutActivity.getSupportFragmentManager(), "dialog_volume_control");
    }

    public void setWorkoutActivity(WorkoutActivity workoutActivity) {
        this.workoutActivity = workoutActivity;
    }
}
