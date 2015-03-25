package com.sisa.tabata.ui.listener.loader;

import android.view.View;
import android.widget.LinearLayout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.service.DatabaseDataService;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.25..
 */
@Singleton
public class WorkoutTextViewLongClickListener implements View.OnLongClickListener {

    @Inject
    private DatabaseDataService databaseDataService;
    private LinearLayout existingWorkoutLayout;

    public WorkoutTextViewLongClickListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public boolean onLongClick(View view) {
        deleteWorkout((int) view.getTag());
        refreshWorkoutTextView(view);
        return true;
    }

    private void deleteWorkout(int id) {
        databaseDataService.deleteWorkoutById(id);
    }

    private void refreshWorkoutTextView(View view) {
        existingWorkoutLayout.removeView(view);
    }

    public void setExistingWorkoutLayout(LinearLayout existingWorkoutLayout) {
        this.existingWorkoutLayout = existingWorkoutLayout;
    }
}
