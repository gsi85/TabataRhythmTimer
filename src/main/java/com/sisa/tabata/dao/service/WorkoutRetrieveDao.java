package com.sisa.tabata.dao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutFactory;
import com.sisa.tabata.factory.WorkoutSectionFactory;

/**
 * DAO for retrieving stored {@link Workout} objects from database.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutRetrieveDao extends AbstractBaseDao {

    private final WorkoutFactory workoutFactory;
    private final WorkoutSectionFactory workoutSectionFactory;
    private final SQLiteQueryBuilder queryBuilder;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     * @param workoutFactory {@link WorkoutFactory}
     * @param workoutSectionFactory {@link WorkoutSectionFactory}
     * @param queryBuilder {@link SQLiteQueryBuilder}
     */
    @Inject
    public WorkoutRetrieveDao(final ApplicationContextProvider applicationContextProvider, final WorkoutFactory workoutFactory,
            final WorkoutSectionFactory workoutSectionFactory, final SQLiteQueryBuilder queryBuilder) {
        super(applicationContextProvider);
        this.workoutFactory = workoutFactory;
        this.workoutSectionFactory = workoutSectionFactory;
        this.queryBuilder = queryBuilder;
    }

    /**
     * Retrieves a specified {@link Workout} by it's id.
     *
     * @param id the workout's id
     * @return {@link Workout} if workout exists in database with given id, null otherwise
     */
    protected Workout getWorkoutById(final long id) {
        String whereClause = WHERE_ID_EQUALS + id;
        List<Workout> workouts = getWorkoutWithSpecifiedCondition(whereClause);
        //TODO: replace with validation
        return workouts.size() != 0 ? workouts.get(0) : null;
    }

    /**
     * Retrieves all stored {@link Workout} from database sorted by "name" attribute.
     *
     * @return list of sorted {@link Workout}
     */
    protected List<Workout> getAllWorkoutsSortedList() {
        return getWorkoutWithSpecifiedCondition(null);
    }

    private List<Workout> getWorkoutWithSpecifiedCondition(final String whereClause) {
        List<Workout> resultWorkoutList = new ArrayList<>();

        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_TIME_UNIT, COLUMN_DESCRIPTION};
        Cursor resultCursor = executeQuery(TABLE_WORKOUT, columns, whereClause, COLUMN_NAME);

        while (resultCursor.moveToNext()) {
            Workout workout = workoutFactory.createWithTimeUnit(null);
            int workoutId = resultCursor.getInt(0);
            workout.setId(workoutId);
            workout.setName(resultCursor.getString(1));
            workout.setTimeUnit(TimeUnit.valueOf(resultCursor.getString(2)));
            workout.setDescription(resultCursor.getString(3));
            workout.setWorkoutSections(getWorkoutSectionsByWorkoutId(workoutId));
            resultWorkoutList.add(workout);
        }

        return resultWorkoutList;
    }

    private List<WorkoutSection> getWorkoutSectionsByWorkoutId(final int workoutId) {
        List<WorkoutSection> workoutSectionList = new ArrayList<>();
        String[] columns = {COLUMN_SECTION_ORDER, COLUMN_ROUNDS, COLUMN_WARM_UP, COLUMN_WORK, COLUMN_REST, COLUMN_COOL_DOWN};
        Cursor resultCursor = executeQuery(TABLE_WORKOUT_SECTIONS, columns, WHERE_WORKOUT_ID_EQUALS + workoutId, COLUMN_SECTION_ORDER);

        while (resultCursor.moveToNext()) {
            WorkoutSection currentWorkoutSection = workoutSectionFactory.create();
            currentWorkoutSection.setRounds(resultCursor.getInt(1));
            currentWorkoutSection.setWarmUp(resultCursor.getLong(2));
            currentWorkoutSection.setWork(resultCursor.getLong(3));
            currentWorkoutSection.setRest(resultCursor.getLong(4));
            currentWorkoutSection.setCoolDown(resultCursor.getLong(5));
            workoutSectionList.add(currentWorkoutSection);

        }
        return workoutSectionList;
    }

    private Cursor executeQuery(final String tableName, final String[] columnsToSelect, final String whereClause, final String sortOrder) {
        queryBuilder.setTables(tableName);
        if (whereClause != null)
            queryBuilder.appendWhere(whereClause);
        return queryBuilder.query(getDatabase(), columnsToSelect, null, null, null, null, sortOrder);
    }
}
