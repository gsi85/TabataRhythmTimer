package com.sisa.tabata.dao.loader;

import static com.sisa.tabata.validation.Validation.empty;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.preferences.PreferencesType;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.workout.transformer.SerializedWorkoutTransformer;

import android.content.SharedPreferences;

/**
 * Manages the currently loaded {@link Workout} instance.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutManager {

    private static final String PREFERENCES_FILE_NAME = "TabataPreferences";
    private static final long NO_LOADED_WORKOUT_ID = -1;

    @Inject
    private ApplicationContextProvider applicationContextProvider;
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
        //TODO: move this a common preference manager.
        SharedPreferences settings = applicationContextProvider.getContext().getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(PreferencesType.LOADED_WORKOUT_ID.name(), 0);
        editor.apply();
    }

    /**
     * Set currently loaded {@link Workout} by it's id.
     *
     * @param id the id of the workout to be loaded
     */
    public void setLoadedWorkoutById(long id) {
        workout = workoutDao.getWorkoutById(id);
        //TODO: move this a common preference manager.
        SharedPreferences settings = applicationContextProvider.getContext().getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(PreferencesType.LOADED_WORKOUT_ID.name(), id);
        editor.apply();
    }

    private void checkLoadWorkout() {
        if (empty(workout)) {
            if (hasPreviouslyLoadedWorkout()) {
                loadPreviouslySavedWorkout();
            } else {
                loadFirstWorkoutInList();
            }
        }
    }

    private boolean hasPreviouslyLoadedWorkout() {
        return getPreviouslyLoadedWorkoutId() != NO_LOADED_WORKOUT_ID;
    }

    private void loadPreviouslySavedWorkout() {
        workout = workoutDao.getWorkoutById(getPreviouslyLoadedWorkoutId());
    }

    //TODO: move this a common preference manager.
    private long getPreviouslyLoadedWorkoutId() {
        SharedPreferences settings = applicationContextProvider.getContext().getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return settings.getLong(PreferencesType.LOADED_WORKOUT_ID.name(), NO_LOADED_WORKOUT_ID);
    }

}
