package com.sisa.tabata.dao.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.dao.service.DatabaseDataService;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.workout.transformer.SerializedWorkoutTransformer;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.01..
 */
@Singleton
public class LoadedWorkoutProvider {

    @Inject
    private SerializedWorkoutTransformer serializedWorkoutTransformer;
    @Inject
    private DatabaseDataService databaseDataService;
    private Workout workout;

    public LoadedWorkoutProvider() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public Workout getLoadedWorkout() {
        checkLoadWorkout();
        return workout;
    }

    public SerializedWorkout getLoadedSerializedWorkout() {
        checkLoadWorkout();
        return serializedWorkoutTransformer.transform(workout);
    }

    public void setLoadedWorkoutById(long id) {
        workout = databaseDataService.getWorkoutById(id);
    }

    private void checkLoadWorkout() {
        if (workout == null) {
            loadLastUsedWorkout();
        }
    }

    private void loadLastUsedWorkout() {
        workout = databaseDataService.getWorkoutById(0);
    }

}
