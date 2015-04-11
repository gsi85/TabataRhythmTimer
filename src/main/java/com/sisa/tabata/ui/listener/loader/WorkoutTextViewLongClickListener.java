package com.sisa.tabata.ui.listener.loader;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.ui.dialog.DeleteWorkoutDialog;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.25..
 */
@ContextSingleton
public class WorkoutTextViewLongClickListener implements View.OnLongClickListener {

    private static final int MINIMUM_REMAINING_WORKOUTS_COUNT = 1;

    @InjectView(R.id.existingWorkoutLayout)
    private LinearLayout existingWorkoutLayout;
    @InjectView(R.id.workoutLoadNotificationView)
    private TextView workoutLoadNotificationView;

    @Inject
    private DeleteWorkoutDialog deleteWorkoutDialog;
    @Inject
    private WorkoutDao workoutDao;

    @Override
    public boolean onLongClick(View view) {
        deleteWorkout(view);
        return true;
    }

    private void deleteWorkout(View view) {
        if (hasWorkoutLeftAfterDelete()) {
            deleteWorkoutDialog.showDeleteWorkoutDialog(view.getContext(), view, existingWorkoutLayout);
        } else {
            showCantDeleteMessage(view);
        }
    }

    private void showCantDeleteMessage(final View view) {
        String notificationString = view.getContext().getString(R.string.notification_cant_delete_last_workout);
        new NotificationDisplayTimer(workoutLoadNotificationView, notificationString, view.getContext().getResources()
                .getInteger(R.integer.short_notification_duration)).start();
    }

    private boolean hasWorkoutLeftAfterDelete() {
        return workoutDao.getAllWorkoutsSortedList().size() > MINIMUM_REMAINING_WORKOUTS_COUNT;
    }

}
