package com.sisa.tabata.ui.listener.workout;

import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.loader.EditedWorkoutProvider;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;

import java.util.HashMap;
import java.util.Map;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.23..
 */
@Singleton
public class MainMenuOnClickListener implements View.OnClickListener {

    private static final String NEW_WORKOUT_NAME = "newWorkout";
    private static final int NEW_WORKOUT_ID = -1;
    public static final String WORKOUT_ID_NAME = "workoutId";

    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;
    @Inject
    private EditedWorkoutProvider editedWorkoutProvider;
    private WorkoutActivity workoutActivity;

    public MainMenuOnClickListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onClick(View view) {
        String menuAction = view.getTag().toString();
        Intent activityToStart = getActivitiesMAp().get(menuAction);
        if (activityToStart != null) {
            editedWorkoutProvider.setEditedWorkout(activityToStart.getIntExtra(WORKOUT_ID_NAME, NEW_WORKOUT_ID));
            workoutActivity.startActivity(activityToStart);
            workoutActivity.finish();
        }
    }

    private Map<String, Intent> getActivitiesMAp() {
        Map<String, Intent> activitiesMap = new HashMap<>();
        activitiesMap.put("EDIT", createWorkoutEditIntent(loadedWorkoutProvider.getLoadedWorkout().getId(), false));
        activitiesMap.put("NEW", createWorkoutEditIntent(NEW_WORKOUT_ID, true));
        activitiesMap.put("LOAD", createWorkoutLoadIntent());
        return activitiesMap;
    }

    private Intent createWorkoutEditIntent(int workoutId, boolean newWorkout) {
        Intent workoutEditIntent = new Intent(workoutActivity, WorkoutEditActivity.class);
        workoutEditIntent.putExtra(NEW_WORKOUT_NAME, newWorkout);
        workoutEditIntent.putExtra(WORKOUT_ID_NAME, workoutId);
        return workoutEditIntent;
    }

    private Intent createWorkoutLoadIntent() {
        return new Intent(workoutActivity, WorkoutLoadActivity.class);
    }


    public void setWorkoutActivity(WorkoutActivity workoutActivity) {
        this.workoutActivity = workoutActivity;
    }


}
