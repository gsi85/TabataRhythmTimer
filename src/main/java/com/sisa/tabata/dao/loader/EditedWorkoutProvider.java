package com.sisa.tabata.dao.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.factory.WorkoutFactory;
import java.util.concurrent.TimeUnit;

/**
 * Provider of the currently edited {@link Workout} instance.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class EditedWorkoutProvider {

    private static final int NEW_WORKOUT_ID = -1;

    @Inject
    private WorkoutDao workoutDao;
    @Inject
    private WorkoutFactory workoutFactory;
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
        if (NEW_WORKOUT_ID == workoutId) {
            createNewWorkout();
        } else {
            loadWorkoutById(workoutId);
        }
    }

    private void loadWorkoutById(final int workoutId) {
        editedWorkout = workoutDao.getWorkoutById(workoutId);
    }

    private void createNewWorkout() {
        editedWorkout = workoutFactory.createWithTimeUnit(TimeUnit.SECONDS);
    }
}
