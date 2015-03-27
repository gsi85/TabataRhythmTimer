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

    public void loadFirstWorkoutInList() {
        workout = databaseDataService.getAllWorkoutsSortedList().get(0);
    }

    private void checkLoadWorkout() {
        if (workout == null) {
            loadFirstWorkoutInList();
        }
    }

}
