package com.sisa.tabata.dao.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.domain.Workout;

/**
 * DAO class responsible for deleting {@link Workout} records from database.
 *
 * @author Laszlo sisa
 */
@Singleton
public class WorkoutDeleteDao extends AbstractBaseDao {

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     */
    @Inject
    public WorkoutDeleteDao(ApplicationContextProvider applicationContextProvider) {
        super(applicationContextProvider);
    }

    /**
     * Delete a workout record by it's id.
     *
     * @param id workout's id
     */
    protected void deleteWorkoutById(long id) {
        getDatabase().delete(TABLE_WORKOUT_SECTIONS, WHERE_WORKOUT_ID_EQUALS + id, null);
        getDatabase().delete(TABLE_WORKOUT, WHERE_ID_EQUALS + id, null);
    }
}
