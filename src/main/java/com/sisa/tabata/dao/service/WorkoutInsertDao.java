package com.sisa.tabata.dao.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutSectionFactory;

/**
 * DAO for persisting new {@link Workout} objects into database.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutInsertDao extends AbstractBaseDao {

    private static final int EMPTY_SECTIONS_SIZE = 0;
    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_WORKOUT_NAME_PATTERN = "%s%d";
    private static final String WHERE_DEFAULT_WORKOUT_NAME_PATTERN = "%s LIKE '%s%%'";
    private static final String WHERE_DEFAULT_WORKOUT_NAME_CLAUSE = String.format(WHERE_DEFAULT_WORKOUT_NAME_PATTERN, COLUMN_NAME,
            DEFAULT_WORKOUT_NAME);

    private final ApplicationContextProvider applicationContextProvider;
    private final WorkoutSectionFactory workoutSectionFactory;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     * @param workoutSectionFactory {@link WorkoutSectionFactory}
     */
    @Inject
    public WorkoutInsertDao(final ApplicationContextProvider applicationContextProvider, final WorkoutSectionFactory workoutSectionFactory) {
        super(applicationContextProvider);
        this.applicationContextProvider = applicationContextProvider;
        this.workoutSectionFactory = workoutSectionFactory;
    }

    /**
     * Persists new or updates existing {@link Workout} in database.
     *
     * @param workout the {@link Workout} to persist
     * @return the row ID of the newly inserted {@link Workout}, or -1 if an error occurred
     */
    protected long insertUpdateWorkout(final Workout workout) {
        checkAddSections(workout);
        ContentValues workoutValues = createWorkoutValues(workout);

        long workoutId = workout.getId();
        if (isNewWorkout(workoutId)) {
            workoutId = insertNewWorkout(workoutValues);
        } else {
            updateExistingWorkout(workoutValues, workoutId);
        }

        insertUpdateWorkoutSections(workout, workoutId);

        return workoutId;
    }

    private void checkAddSections(final Workout workout) {
        if (workout.getWorkoutSections().size() == EMPTY_SECTIONS_SIZE) {
            workout.getWorkoutSections().add(workoutSectionFactory.create());
        }
    }

    private ContentValues createWorkoutValues(final Workout workout) {
        ContentValues workoutValues = new ContentValues();
        workoutValues.put(COLUMN_NAME, getNullSafeWorkoutName(workout.getName()));
        workoutValues.put(COLUMN_TIME_UNIT, workout.getTimeUnit().name());
        workoutValues.put(COLUMN_DESCRIPTION, workout.getDescription() == null ? EMPTY_STRING : workout.getDescription());
        return workoutValues;
    }

    private String getNullSafeWorkoutName(final String currentWorkoutName) {
        String workoutName = currentWorkoutName;
        //TODO: replace with validation
        if (workoutName == null) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(TABLE_WORKOUT);
            queryBuilder.appendWhere(WHERE_DEFAULT_WORKOUT_NAME_CLAUSE);
            Cursor resultCursor = queryBuilder.query(getDatabase(), null, null, null, null, null, null);
            workoutName = String.format(DEFAULT_WORKOUT_NAME_PATTERN, DEFAULT_WORKOUT_NAME, resultCursor.getCount() + 1);
        }
        return workoutName;
    }

    private boolean isNewWorkout(final long workoutId) {
        return workoutId == applicationContextProvider.getIntResource(R.integer.new_workout_id);
    }

    private long insertNewWorkout(final ContentValues workoutValues) {
        return getDatabase().insert(TABLE_WORKOUT, null, workoutValues);
    }

    private void updateExistingWorkout(final ContentValues workoutValues, final long workoutId) {
        workoutValues.put(COLUMN_ID, workoutId);
        String whereClause = WHERE_ID_EQUALS + workoutId;
        getDatabase().update(TABLE_WORKOUT, workoutValues, whereClause, null);
    }

    private void insertUpdateWorkoutSections(final Workout workout, final long workoutId) {
        deleteExistingWorkoutSections(workoutId);
        for (int i = 0; i < workout.getWorkoutSections().size(); i++) {
            ContentValues workoutSectionValues = createWorkoutSectionValues(workout, workoutId, i);
            insertNewWorkoutSection(workoutSectionValues);
        }
    }

    private void deleteExistingWorkoutSections(final long workoutId) {
        getDatabase().delete(TABLE_WORKOUT_SECTIONS, WHERE_WORKOUT_ID_EQUALS + workoutId, null);
    }

    private ContentValues createWorkoutSectionValues(final Workout workout, final long workoutId, final int i) {
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

    private void insertNewWorkoutSection(final ContentValues workoutSectionValues) {
        getDatabase().insert(TABLE_WORKOUT_SECTIONS, null, workoutSectionValues);
    }

}
