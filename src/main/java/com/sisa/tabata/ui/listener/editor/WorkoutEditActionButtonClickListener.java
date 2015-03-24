package com.sisa.tabata.ui.listener.editor;

import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.dao.helper.DatabaseHelper;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;

/**
 * Created by Laca on 2015.03.19..
 */
@Singleton
public class WorkoutEditActionButtonClickListener implements View.OnClickListener {

    private static final String SAVE_ACTION = "save_action";

    @Inject
    private DatabaseHelper databaseHelper;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;
    private Workout workout;
    private WorkoutEditActivity workoutEditActivity;

    @Override
    public void onClick(View view) {
        Intent workoutIntent = createIntent();
        if (isSaveAction(view)) {
            long workoutId = saveWorkoutToDb();
            setLoadedWorkout(workoutId);
        }
        workoutEditActivity.startActivity(workoutIntent);
        workoutEditActivity.finish();
    }

    private Intent createIntent() {
        return new Intent(workoutEditActivity, WorkoutActivity.class);
    }

    private boolean isSaveAction(View view) {
        return SAVE_ACTION.equals(view.getTag());
    }

    private long saveWorkoutToDb() {
        return databaseHelper.insertUpdateWorkout(workout);
    }

    private void setLoadedWorkout(long workoutId) {
        loadedWorkoutProvider.setLoadedWorkoutById(workoutId);
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void setWorkoutEditActivity(WorkoutEditActivity workoutEditActivity) {
        this.workoutEditActivity = workoutEditActivity;
    }
}
