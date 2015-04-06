package com.sisa.tabata.dao.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;

/**
 * DAO class managing and persisting {@link Workout} objects.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutDao {

    @Inject
    private WorkoutInsertDao workoutInsertDao;
    @Inject
    private WorkoutRetrieveDao workoutRetrieveDao;
    @Inject
    private WorkoutDeleteDao workoutDeleteDao;

    /**
     * Persist a new {@link Workout} to database.
     *
     * @param workout the {@link Workout} to be saved
     * @return the newly inserted record's id
     */
    public long insertUpdateWorkout(Workout workout) {
        return workoutInsertDao.insertUpdateWorkout(workout);
    }

    /**
     * Retrieves a {@link Workout} from the database by it's id.
     *
     * @param id the {@link Workout} id
     * @return {@link Workout} if found, null otherwise
     */
    public Workout getWorkoutById(long id) {
        return workoutRetrieveDao.getWorkoutById(id);
    }

    /**
     * Returns the list of all persisted {@link Workout} sorted by it's name.
     *
     * @return the list of {@link Workout}
     */
    public List<Workout> getAllWorkoutsSortedList() {
        return workoutRetrieveDao.getAllWorkoutsSortedList();
    }

    /**
     * Deletes a {@link Workout} from the database.
     *
     * @param id id of the {@link Workout} to be deleted
     */
    public void deleteWorkoutById(long id) {
        workoutDeleteDao.deleteWorkoutById(id);
    }

}
