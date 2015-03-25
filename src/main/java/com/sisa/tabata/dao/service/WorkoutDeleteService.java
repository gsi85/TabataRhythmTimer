package com.sisa.tabata.dao.service;

import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.25..
 */
@Singleton
public class WorkoutDeleteService extends AbstractBaseDatabaseService {

    public WorkoutDeleteService() {
        super(TabataApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    protected void deleteWorkoutById(long id) {
        getDatabase().delete(TABLE_WORKOUT_SECTIONS, WHERE_WORKOUT_ID_EQUALS + id, null);
        getDatabase().delete(TABLE_WORKOUT, WHERE_ID_EQUALS + id, null);
    }
}
