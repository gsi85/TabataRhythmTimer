package com.sisa.tabata.ui.listener.editor;

import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutEditActivity;

import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.19..
 */
@ContextSingleton
public class WorkoutEditActionButtonClickListener implements View.OnClickListener {

    private static final String SAVE_ACTION = "save_action";

    @Inject
    private WorkoutDao workoutDao;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;

    @Override
    public void onClick(View view) {
        WorkoutEditActivity workoutEditActivity = getCheckedContext(view);
        Intent workoutIntent = createIntent(workoutEditActivity);
        if (isSaveAction(view)) {
            long workoutId = saveWorkoutToDb(workoutEditActivity.getEditedWorkout());
            setLoadedWorkout(workoutId);
        }
        workoutEditActivity.startActivity(workoutIntent);
        workoutEditActivity.finish();
    }

    private Intent createIntent(final WorkoutEditActivity workoutEditActivity) {
        return new Intent(workoutEditActivity, WorkoutActivity.class);
    }

    private boolean isSaveAction(View view) {
        return SAVE_ACTION.equals(view.getTag());
    }

    private long saveWorkoutToDb(final Workout editedWorkout) {
        return workoutDao.insertUpdateWorkout(editedWorkout);
    }

    private void setLoadedWorkout(long workoutId) {
        loadedWorkoutProvider.setLoadedWorkoutById(workoutId);
    }

    private WorkoutEditActivity getCheckedContext(final View view) {
        //TODO: assert
        if (!(view.getContext() instanceof WorkoutEditActivity)) {
            throw new IllegalArgumentException("View context is not a WorkoutEditActivity");
        }
        return (WorkoutEditActivity) view.getContext();
    }

}
