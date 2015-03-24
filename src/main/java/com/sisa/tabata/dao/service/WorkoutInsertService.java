package com.sisa.tabata.dao.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutSectionFactory;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.24..
 */
@Singleton
public class WorkoutInsertService extends AbstractBaseDatabaseService {

    private static final int EMPTY_SECTIONS_SIZE = 0;
    private static final String DEFAULT_WORKOUT_NAME_PATTERN = "%s%d";
    private static final String WHERE_DEFAULT_WORKOUT_NAME_PATTERN = "%s LIKE '%s%%'";
    private static final String WHERE_DEFAULT_WORKOUT_NAME_CLAUSE = String.format(WHERE_DEFAULT_WORKOUT_NAME_PATTERN, COLUMN_NAME, DEFAULT_WORKOUT_NAME);

    @Inject
    private WorkoutSectionFactory workoutSectionFactory;

    public WorkoutInsertService() {
        super(TabataApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    protected long insertUpdateWorkout(Workout workout) {
        checkAddIfSectionsEmpty(workout);
        ContentValues workoutValues = createWorkoutValues(workout);

        long workoutId = workout.getId();
        if (workoutId == NEW_WORKOUT_ID) {
            workoutId = insertNewWorkout(workoutValues);
        } else {
            updateExistingWorkout(workoutValues, workoutId);
        }

        insertUpdateWorkoutSections(workout, workoutId);

        return workoutId;
    }

    private void checkAddIfSectionsEmpty(Workout workout) {
        if (workout.getWorkoutSections().size() == EMPTY_SECTIONS_SIZE) {
            workout.getWorkoutSections().add(workoutSectionFactory.create());
        }
    }

    private ContentValues createWorkoutValues(Workout workout) {
        ContentValues workoutValues = new ContentValues();
        workoutValues.put(COLUMN_NAME, getNullSafeWorkoutName(workout.getName()));
        workoutValues.put(COLUMN_TIME_UNIT, workout.getTimeUnit().name());
        return workoutValues;
    }

    private String getNullSafeWorkoutName(String workoutName) {
        if (workoutName == null) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(TABLE_WORKOUT);
            queryBuilder.appendWhere(WHERE_DEFAULT_WORKOUT_NAME_CLAUSE);
            Cursor resultCursor = queryBuilder.query(getDatabase(), null, null, null, null, null, null);
            workoutName = String.format(DEFAULT_WORKOUT_NAME_PATTERN, DEFAULT_WORKOUT_NAME, (resultCursor.getCount() + 1));
        }
        return workoutName;
    }

    private long insertNewWorkout(ContentValues workoutValues) {
        long workoutId;
        workoutId = getDatabase().insert(TABLE_WORKOUT, null, workoutValues);
        return workoutId;
    }

    private void updateExistingWorkout(ContentValues workoutValues, long workoutId) {
        workoutValues.put(COLUMN_ID, workoutId);
        String whereClause = WHERE_ID_EQUALS + workoutId;
        getDatabase().update(TABLE_WORKOUT, workoutValues, whereClause, null);
    }

    private void insertUpdateWorkoutSections(Workout workout, long workoutId) {
        deleteExistingWorkoutSections(workoutId);
        for (int i = 0; i < workout.getWorkoutSections().size(); i++) {
            ContentValues workoutSectionValues = createWorkoutSectionValues(workout, workoutId, i);
            insertNewWorkoutSection(workoutSectionValues);
        }
    }

    private void deleteExistingWorkoutSections(long workoutId) {
        getDatabase().delete(TABLE_WORKOUT_SECTIONS, WHERE_WORKOUT_ID_EQUALS + workoutId, null);
    }

    private ContentValues createWorkoutSectionValues(Workout workout, long workoutId, int i) {
        WorkoutSection currentWorkoutSection = workout.getWorkoutSections().get(i);
        ContentValues workoutSectionValues = new ContentValues();
        workoutSectionValues.put(COLUMN_WORKOUT_ID, workoutId);
        workoutSectionValues.put(COLUMN_SECTION_ORDER, i);
        workoutSectionValues.put(COLUMN_ROUNDS, currentWorkoutSection.getRounds());
        workoutSectionValues.put(COLUMN_WARM_UP, currentWorkoutSection.getWarmUp());
        workoutSectionValues.put(COLUMN_WORK, currentWorkoutSection.getWork());
        workoutSectionValues.put(COLUMN_REST, currentWorkoutSection.getRest());
        workoutSectionValues.put(COLUMN_COOL_DOWN, currentWorkoutSection.getCoolDown());
        return workoutSectionValues;
    }

    private void insertNewWorkoutSection(ContentValues workoutSectionValues) {
        getDatabase().insert(TABLE_WORKOUT_SECTIONS, null, workoutSectionValues);
    }

}
