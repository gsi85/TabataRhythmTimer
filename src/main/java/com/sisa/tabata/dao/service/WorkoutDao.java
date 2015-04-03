package com.sisa.tabata.dao.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;

/**
 * Created by Laca on 2015.03.19..
 */
@Singleton
public class WorkoutDao {

    @Inject
    private WorkoutInsertDao workoutInsertDao;
    @Inject
    private WorkoutRetrieveDao workoutRetrieveDao;
    @Inject
    private WorkoutDeleteDao workoutDeleteDao;

    public long insertUpdateWorkout(Workout workout) {
        return workoutInsertDao.insertUpdateWorkout(workout);
    }

    public Workout getWorkoutById(long id) {
        return workoutRetrieveDao.getWorkoutById(id);
    }

    public List<Workout> getAllWorkoutsSortedList() {
        return workoutRetrieveDao.getAllWorkoutsSortedList();
    }

    public void deleteWorkoutById(long id) {
        workoutDeleteDao.deleteWorkoutById(id);
    }

}
