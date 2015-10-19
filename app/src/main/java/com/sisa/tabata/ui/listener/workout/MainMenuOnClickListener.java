package com.sisa.tabata.ui.listener.workout;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.EditedWorkoutManager;
import com.sisa.tabata.dao.loader.WorkoutManager;
import com.sisa.tabata.media.service.MediaPlayerService;
import com.sisa.tabata.ui.activity.MusicSelectActivity;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;
import com.sisa.tabata.ui.timer.WorkoutCountDownTimerManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * Main menu on click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class MainMenuOnClickListener extends AbstractWorkoutActivityButtonClickListener {

    private static final String NEW_WORKOUT_NAME = "newWorkout";
    private static final int NEW_WORKOUT_ID = -1;
    private static final String WORKOUT_ID_NAME = "workoutId";

    @Inject
    private WorkoutManager workoutManager;
    @Inject
    private EditedWorkoutManager editedWorkoutManager;
    @Inject
    private WorkoutCountDownTimerManager workoutCountDownTimerManager;
    @Inject
    private MediaPlayerService mediaPlayerService;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (isWorkoutInProgress()) {
            showConfirmationDialog(view);
        } else {
            startSelectedActivity(view);
        }
    }

    private boolean isWorkoutInProgress() {
        return workoutCountDownTimerManager.isWorkoutInProgress() && !workoutCountDownTimerManager.isFinished();
    }

    private void showConfirmationDialog(final View view) {
        new AlertDialog.Builder(view.getContext(), AlertDialog.THEME_HOLO_DARK).setTitle(R.string.workout_cancel_dialog_title)
                .setMessage(R.string.workout_cancel_dialog_message).setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.dialog_button_yes, new AlertDialogClickListener(view)).setNegativeButton(R.string.dialog_button_no, null)
                .show();
    }

    private void startSelectedActivity(final View view) {
        WorkoutActivity workoutActivity = getCheckedActivity(view);
        String menuAction = view.getTag().toString();
        Intent activityToStart = getActivitiesMAp(workoutActivity).get(menuAction);
        if (activityToStart != null) {
            workoutCountDownTimerManager.pause();
            mediaPlayerService.pause();
            editedWorkoutManager.setEditedWorkout(activityToStart.getIntExtra(WORKOUT_ID_NAME, NEW_WORKOUT_ID));
            workoutActivity.startActivity(activityToStart);
            workoutActivity.finish();
        }
    }

    private WorkoutActivity getCheckedActivity(final View view) {
        isInstanceOf(WorkoutActivity.class, view.getContext(), "View context is not WorkoutActivity");
        return (WorkoutActivity) view.getContext();
    }

    private Map<String, Intent> getActivitiesMAp(final WorkoutActivity workoutActivity) {
        Map<String, Intent> activitiesMap = new HashMap<>();
        activitiesMap.put("EDIT", createWorkoutEditIntent(workoutActivity, workoutManager.getLoadedWorkout().getId(), false));
        activitiesMap.put("NEW", createWorkoutEditIntent(workoutActivity, NEW_WORKOUT_ID, true));
        activitiesMap.put("LOAD", createWorkoutLoadIntent(workoutActivity));
        activitiesMap.put("SET MUSIC", createSelectMusicIntent(workoutActivity));
        return activitiesMap;
    }

    private Intent createWorkoutEditIntent(final WorkoutActivity workoutActivity, int workoutId, boolean newWorkout) {
        Intent workoutEditIntent = new Intent(workoutActivity, WorkoutEditActivity.class);
        workoutEditIntent.putExtra(NEW_WORKOUT_NAME, newWorkout);
        workoutEditIntent.putExtra(WORKOUT_ID_NAME, workoutId);
        return workoutEditIntent;
    }

    private Intent createWorkoutLoadIntent(final WorkoutActivity workoutActivity) {
        return new Intent(workoutActivity, WorkoutLoadActivity.class);
    }

    private Intent createSelectMusicIntent(final WorkoutActivity workoutActivity) {
        return new Intent(workoutActivity, MusicSelectActivity.class);
    }

    private final class AlertDialogClickListener implements DialogInterface.OnClickListener {

        private final View view;

        private AlertDialogClickListener(final View view) {
            this.view = view;
        }

        @Override
        public void onClick(final DialogInterface dialog, final int which) {
            startSelectedActivity(view);
        }
    }

}
