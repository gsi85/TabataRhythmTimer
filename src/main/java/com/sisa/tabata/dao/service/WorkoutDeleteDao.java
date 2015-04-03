package com.sisa.tabata.dao.service;

import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;

/**
 * DAO class resposible for deleting workout records from database.
 *
 * @author Laszlo sisa
 */
@Singleton
public class WorkoutDeleteDao extends AbstractBaseDao {

    /**
     * DI constructor.
     */
    public WorkoutDeleteDao() {
        super(TabataApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
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
