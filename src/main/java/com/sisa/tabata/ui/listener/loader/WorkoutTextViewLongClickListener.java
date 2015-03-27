package com.sisa.tabata.ui.listener.loader;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.dao.service.DatabaseDataService;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.25..
 */
@Singleton
public class WorkoutTextViewLongClickListener implements View.OnLongClickListener {

    public static final int MINIMUM_REMAINING_WORKOUTS_COUNT = 1;
    @Inject
    private DatabaseDataService databaseDataService;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;
    private LinearLayout existingWorkoutLayout;

    public WorkoutTextViewLongClickListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public boolean onLongClick(View view) {
        deleteWorkout(view);
        return true;
    }

    private void deleteWorkout(View view) {
        if (hasWorkoutLeftAfterDelete()) {
            int id = (int) view.getTag();
            databaseDataService.deleteWorkoutById(id);
            refreshLoadedWorkout(id);
            refreshWorkoutTextView(view);
        } else {
            showCantDeleteMessage();
        }
    }

    private void showCantDeleteMessage() {
        Toast.makeText(TabataApplication.getAppContext(), "Can't delete last workout", Toast.LENGTH_SHORT).show();
    }

    private void refreshWorkoutTextView(View view) {
        existingWorkoutLayout.removeView(view);
    }

    private void refreshLoadedWorkout(int id) {
        if (loadedWorkoutProvider.getLoadedWorkout().getId() == id){
            loadedWorkoutProvider.loadFirstWorkoutInList();
        }
    }

    private boolean hasWorkoutLeftAfterDelete() {
        return databaseDataService.getAllWorkoutsSortedList().size() > MINIMUM_REMAINING_WORKOUTS_COUNT;
    }

    public void setExistingWorkoutLayout(LinearLayout existingWorkoutLayout) {
        this.existingWorkoutLayout = existingWorkoutLayout;
    }
}
