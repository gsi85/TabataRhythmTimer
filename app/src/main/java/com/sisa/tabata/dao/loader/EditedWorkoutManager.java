package com.sisa.tabata.dao.loader;

import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.factory.WorkoutFactory;

/**
 * Loader of the currently edited {@link Workout} instance.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class EditedWorkoutManager {

    @Inject
    private WorkoutDao workoutDao;
    @Inject
    private WorkoutFactory workoutFactory;
    @Inject
    private ApplicationContextProvider applicationContextProvider;
    private Workout editedWorkout;

    /**
     * Returns the currently edited {@link Workout}.
     *
     * @return the currently edited {@link Workout} if set, null otherwise
     */
    public Workout getEditedWorkout() {
        return editedWorkout;
    }

    /**
     * Sets edited {@link Workout}.
     *
     * @param workoutId {@link Workout}
     */
    public void setEditedWorkout(final int workoutId) {
        if (isNewWorkoutId(workoutId)) {
            createNewWorkout();
        } else {
            loadWorkoutById(workoutId);
        }
    }

    private boolean isNewWorkoutId(final int workoutId) {
        return applicationContextProvider.getIntResource(R.integer.new_workout_id) == workoutId;
    }

    private void loadWorkoutById(final int workoutId) {
        editedWorkout = workoutDao.getWorkoutById(workoutId);
    }

    private void createNewWorkout() {
        editedWorkout = workoutFactory.createWithTimeUnit(TimeUnit.SECONDS);
    }
}
