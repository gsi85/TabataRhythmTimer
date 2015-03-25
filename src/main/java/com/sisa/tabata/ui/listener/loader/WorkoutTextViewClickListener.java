package com.sisa.tabata.ui.listener.loader;

import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.25..
 */
@Singleton
public class WorkoutTextViewClickListener implements View.OnClickListener {

    private WorkoutLoadActivity workoutLoadActivity;

    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;

    public WorkoutTextViewClickListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onClick(View view) {
        loadSelectedWorkout((int) view.getTag());
        startWorkoutActivity();
    }

    private void loadSelectedWorkout(int id) {
        loadedWorkoutProvider.setLoadedWorkoutById(id);
    }

    private void startWorkoutActivity() {
        workoutLoadActivity.startActivity(new Intent(workoutLoadActivity, WorkoutActivity.class));
    }

    public void setWorkoutLoadActivity(WorkoutLoadActivity workoutLoadActivity) {
        this.workoutLoadActivity = workoutLoadActivity;
    }
}
