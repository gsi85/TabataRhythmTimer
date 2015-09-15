package com.sisa.tabata.ui.listener.loader;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.ui.dialog.DeleteWorkoutDialog;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

/**
 * Workout text view long click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class WorkoutTextViewLongClickListener implements View.OnLongClickListener {

    private static final int MINIMUM_REMAINING_WORKOUTS_COUNT = 1;
    private static final int NOTIFICATION_CANT_DELETE_LAST_WORKOUT = R.string.notification_cant_delete_last_workout;
    private static final int SHORT_NOTIFICATION_DURATION = R.integer.short_notification_duration;

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
        Context context = view.getContext();
        if (hasWorkoutLeftAfterDelete()) {
            showDeleteWorkoutDialog(view);
        } else {
            showCantDeleteMessage(context);
        }
    }

    private boolean hasWorkoutLeftAfterDelete() {
        return workoutDao.getAllWorkoutsSortedList().size() > MINIMUM_REMAINING_WORKOUTS_COUNT;
    }

    private void showDeleteWorkoutDialog(final View view) {
        deleteWorkoutDialog.showDeleteWorkoutDialog(view.getContext(), view, existingWorkoutLayout);
    }

    private void showCantDeleteMessage(final Context context) {
        String notificationString = context.getString(NOTIFICATION_CANT_DELETE_LAST_WORKOUT);
        int durationMillis = context.getResources().getInteger(SHORT_NOTIFICATION_DURATION);
        new NotificationDisplayTimer(workoutLoadNotificationView, notificationString, durationMillis).start();
    }

}
