package com.sisa.tabata.dao.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;

import java.util.List;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.19..
 */
@Singleton
public class DatabaseDataService {

    @Inject
    private WorkoutInsertService workoutInsertService;
    @Inject
    private WorkoutRetrieveService workoutRetrieveService;
    @Inject
    private WorkoutDeleteService workoutDeleteService;

    public DatabaseDataService() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public long insertUpdateWorkout(Workout workout) {
        return workoutInsertService.insertUpdateWorkout(workout);
    }

    public Workout getWorkoutById(long id) {
        return workoutRetrieveService.getWorkoutById(id);
    }

    public List<Workout> getAllWorkoutsSortedList() {
        return workoutRetrieveService.getAllWorkoutsSortedList();
    }

    public void deleteWorkoutById(long id){
        workoutDeleteService.deleteWorkoutById(id);
    }


}
