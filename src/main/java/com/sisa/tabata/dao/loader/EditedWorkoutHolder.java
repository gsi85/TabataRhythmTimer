package com.sisa.tabata.dao.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.factory.WorkoutFactory;

import java.util.concurrent.TimeUnit;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.20..
 */
@Singleton
public class EditedWorkoutHolder {

    @Inject
    private WorkoutFactory workoutFactory;
    private Workout editedWorkout;

    public EditedWorkoutHolder() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public Workout getEditedWorkout() {
        createIfEmpty();
        return editedWorkout;
    }

    private void createIfEmpty() {
        if (editedWorkout == null) {
            editedWorkout = workoutFactory.createWithTimeUnit(TimeUnit.SECONDS);
        }
    }

}
