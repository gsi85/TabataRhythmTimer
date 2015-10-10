package com.sisa.tabata.dao.loader;

import static com.sisa.tabata.validation.Validation.empty;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.workout.transformer.SerializedWorkoutTransformer;

/**
 * Manages the currently loaded {@link Workout} instance.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutManager {

    @Inject
    private SerializedWorkoutTransformer serializedWorkoutTransformer;
    @Inject
    private WorkoutDao workoutDao;
    private Workout workout;

    /**
     * Returns the currently loaded {@link Workout}.
     * By default the workout is the first workout returned by {@link WorkoutDao}.
     *
     * @return the currently loaded {@link Workout}
     */
    public Workout getLoadedWorkout() {
        checkLoadWorkout();
        return workout;
    }

    /**
     * Returns the currently loaded {@link Workout} transformed to {@link SerializedWorkout}.
     * By default the workout is the first workout returned by {@link WorkoutDao}.
     *
     * @return the currently loaded {@link Workout}
     */
    public SerializedWorkout getLoadedSerializedWorkout() {
        checkLoadWorkout();
        return serializedWorkoutTransformer.transform(workout);
    }

    /**
     * Loads and sets the first {@link Workout} returned by {@link WorkoutDao}.
     */
    public void loadFirstWorkoutInList() {
        workout = workoutDao.getAllWorkoutsSortedList().get(0);
    }

    /**
     * Set currently loaded {@link Workout} by it's id.
     *
     * @param id the id of the workout to be loaded
     */
    public void setLoadedWorkoutById(long id) {
        workout = workoutDao.getWorkoutById(id);
    }

    private void checkLoadWorkout() {
        if (empty(workout)) {
            loadFirstWorkoutInList();
        }
    }

}
