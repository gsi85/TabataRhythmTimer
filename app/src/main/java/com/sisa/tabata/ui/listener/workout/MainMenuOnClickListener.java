package com.sisa.tabata.ui.listener.workout;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.sisa.tabata.dao.loader.EditedWorkoutProvider;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;

import android.content.Intent;
import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * Main menu on click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class MainMenuOnClickListener implements View.OnClickListener {

    private static final String NEW_WORKOUT_NAME = "newWorkout";
    private static final int NEW_WORKOUT_ID = -1;
    private static final String WORKOUT_ID_NAME = "workoutId";

    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;
    @Inject
    private EditedWorkoutProvider editedWorkoutProvider;

    @Override
    public void onClick(View view) {
        WorkoutActivity workoutActivity = getCheckedActivity(view);
        String menuAction = view.getTag().toString();
        Intent activityToStart = getActivitiesMAp(workoutActivity).get(menuAction);
        if (activityToStart != null) {
            editedWorkoutProvider.setEditedWorkout(activityToStart.getIntExtra(WORKOUT_ID_NAME, NEW_WORKOUT_ID));
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
        activitiesMap.put("EDIT", createWorkoutEditIntent(workoutActivity, loadedWorkoutProvider.getLoadedWorkout().getId(), false));
        activitiesMap.put("NEW", createWorkoutEditIntent(workoutActivity, NEW_WORKOUT_ID, true));
        activitiesMap.put("LOAD", createWorkoutLoadIntent(workoutActivity));
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

}
