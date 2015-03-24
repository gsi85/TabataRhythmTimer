package com.sisa.tabata.dao.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.19..
 */
@Singleton
public class DatabaseDataService {

    @Inject
    private WorkoutInsertService workoutInsertService;
    @Inject
    private WorkoutRetrieveServiceBase workoutRetrieveService;

    public DatabaseDataService() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public long insertUpdateWorkout(Workout workout) {
        return workoutInsertService.insertUpdateWorkout(workout);
    }

    public Workout getWorkoutById(long id) {
        return workoutRetrieveService.getWorkoutById(id);
    }


}
