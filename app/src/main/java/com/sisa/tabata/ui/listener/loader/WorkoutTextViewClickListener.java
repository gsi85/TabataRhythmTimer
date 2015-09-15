package com.sisa.tabata.ui.listener.loader;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import com.google.inject.Inject;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.activity.WorkoutLoadActivity;

import android.content.Intent;
import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * Workout text view click listener.
 *
 * @author Laszlo Sisa
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
        isInstanceOf(WorkoutLoadActivity.class, view.getContext(), "View context is not a WorkoutLoadActivity");
        return (WorkoutLoadActivity) view.getContext();
    }

}
