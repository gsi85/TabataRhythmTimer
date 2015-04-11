package com.sisa.tabata.ui.listener.loader;

import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;

import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.25..
 */
@ContextSingleton
public class WorkoutTextViewClickListener implements View.OnClickListener {

    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;

    @Override
    public void onClick(View view) {
        loadSelectedWorkout((int) view.getTag());
        startWorkoutActivity(view);
    }

    private void loadSelectedWorkout(int id) {
        loadedWorkoutProvider.setLoadedWorkoutById(id);
    }

    private void startWorkoutActivity(final View view) {
        WorkoutLoadActivity workoutLoadActivity = getCheckedContext(view);
        workoutLoadActivity.startActivity(new Intent(workoutLoadActivity, WorkoutActivity.class));
        workoutLoadActivity.finish();
    }

    private WorkoutLoadActivity getCheckedContext(final View view) {
        //TODO: assert
        if (!(view.getContext() instanceof WorkoutLoadActivity)) {
            throw new IllegalArgumentException("View context is not a WorkoutLoadActivity");
        }
        return (WorkoutLoadActivity) view.getContext();
    }

}
