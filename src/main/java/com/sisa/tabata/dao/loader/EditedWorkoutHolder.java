package com.sisa.tabata.dao.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.helper.DatabaseHelper;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.factory.WorkoutFactory;

import java.util.concurrent.TimeUnit;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.20..
 */
@Singleton
public class EditedWorkoutHolder {

    private static final int NEW_WORKOUT_ID = -1;

    @Inject
    private DatabaseHelper databaseHelper;
    @Inject
    private WorkoutFactory workoutFactory;
    private Workout editedWorkout;

    public EditedWorkoutHolder() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public Workout getEditedWorkout() {
        return editedWorkout;
    }

    public void setEditedWorkout(int workoutId) {
        if (NEW_WORKOUT_ID == workoutId)
            createNewWorkout();
        else
            loadWorkoutById(workoutId);
    }

    private void loadWorkoutById(int workoutId) {
        editedWorkout = databaseHelper.getWorkoutById(workoutId);
    }

    private void createNewWorkout() {
        editedWorkout = workoutFactory.createWithTimeUnit(TimeUnit.SECONDS);
    }
}
