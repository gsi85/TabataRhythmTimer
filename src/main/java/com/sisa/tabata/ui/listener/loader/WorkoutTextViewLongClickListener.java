package com.sisa.tabata.ui.listener.loader;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.service.DatabaseDataService;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;
import com.sisa.tabata.ui.dialog.DeleteWorkoutDialog;
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
    private DeleteWorkoutDialog deleteWorkoutDialog;
    private LinearLayout existingWorkoutLayout;
    private TextView workoutLoadNotificationView;
    private WorkoutLoadActivity workoutLoadActivity;

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
            deleteWorkoutDialog.showDeleteWorkoutDialog(workoutLoadActivity, view, existingWorkoutLayout);
        } else {
            showCantDeleteMessage();
        }
    }

    private void showCantDeleteMessage() {
        String notificationString = TabataApplication.getAppContext().getString(R.string.notification_cant_delete_last_workout);
        new NotificationDisplayTimer(workoutLoadNotificationView, notificationString, DISPLAY_TIME_IN_MILLIS).start();
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

    public void setWorkoutLoadActivity(WorkoutLoadActivity workoutLoadActivity) {
        this.workoutLoadActivity = workoutLoadActivity;
    }
}
