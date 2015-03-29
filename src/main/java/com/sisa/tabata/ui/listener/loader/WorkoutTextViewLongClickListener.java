package com.sisa.tabata.ui.listener.loader;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.dao.service.DatabaseDataService;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.25..
 */
@Singleton
public class WorkoutTextViewLongClickListener implements View.OnLongClickListener {

    private static final int MINIMUM_REMAINING_WORKOUTS_COUNT = 1;
    private static final int DISPLAY_TIME_IN_MILLIS = 2000;

    @Inject
    private DatabaseDataService databaseDataService;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;
    private LinearLayout existingWorkoutLayout;
    private TextView workoutLoadNotificationView;

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
        String notificationString = TabataApplication.getAppContext().getString(R.string.notification_cant_delete_last_workout);
        new NotificationDisplayTimer(workoutLoadNotificationView, notificationString, DISPLAY_TIME_IN_MILLIS).start();
    }

    private void refreshWorkoutTextView(View view) {
        existingWorkoutLayout.removeView(view);
    }

    private void refreshLoadedWorkout(int id) {
        if (loadedWorkoutProvider.getLoadedWorkout().getId() == id) {
            loadedWorkoutProvider.loadFirstWorkoutInList();
        }
    }

    private boolean hasWorkoutLeftAfterDelete() {
        return databaseDataService.getAllWorkoutsSortedList().size() > MINIMUM_REMAINING_WORKOUTS_COUNT;
    }

    public void setExistingWorkoutLayout(LinearLayout existingWorkoutLayout) {
        this.existingWorkoutLayout = existingWorkoutLayout;
    }

    public void setWorkoutLoadNotificationView(TextView workoutLoadNotificationView) {
        this.workoutLoadNotificationView = workoutLoadNotificationView;
    }
}
