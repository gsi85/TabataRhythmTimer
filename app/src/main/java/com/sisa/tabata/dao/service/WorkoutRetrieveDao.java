package com.sisa.tabata.dao.service;

import static com.sisa.tabata.validation.Validation.notEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutFactory;
import com.sisa.tabata.factory.WorkoutSectionFactory;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * DAO for retrieving stored {@link Workout} objects from database.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutRetrieveDao extends AbstractBaseDao {

    private static final int ID_COLUMN_INDEX = 0;
    private static final int NAME_COLUMN_INDEX = 1;
    private static final int TIME_UNIT_COLUMN_INDEX = 2;
    private static final int DESCRIPTION_COLUMN_INDEX = 3;
    private static final int ROUNDS_COLUMN_INDEX = 1;
    private static final int WARM_UP_COLUMN_INDEX = 2;
    private static final int WORK_COLUMN_INDEX = 3;
    private static final int REST_COLUMN_INDEX = 4;
    private static final int COOL_DOWN_COLUMN_INDEX = 5;

    private final WorkoutFactory workoutFactory;
    private final WorkoutSectionFactory workoutSectionFactory;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     * @param workoutFactory {@link WorkoutFactory}
     * @param workoutSectionFactory {@link WorkoutSectionFactory}
     */
    @Inject
    public WorkoutRetrieveDao(final ApplicationContextProvider applicationContextProvider, final WorkoutFactory workoutFactory,
            final WorkoutSectionFactory workoutSectionFactory) {
        super(applicationContextProvider);
        this.workoutFactory = workoutFactory;
        this.workoutSectionFactory = workoutSectionFactory;
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
        return notEmpty(workouts) ? workouts.get(0) : null;
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
            int workoutId = resultCursor.getInt(ID_COLUMN_INDEX);
            workout.setId(workoutId);
            workout.setName(resultCursor.getString(NAME_COLUMN_INDEX));
            workout.setTimeUnit(TimeUnit.valueOf(resultCursor.getString(TIME_UNIT_COLUMN_INDEX)));
            workout.setDescription(resultCursor.getString(DESCRIPTION_COLUMN_INDEX));
            workout.setWorkoutSections(getWorkoutSectionsByWorkoutId(workoutId));
            resultWorkoutList.add(workout);
        }

        resultCursor.close();
        return resultWorkoutList;
    }

    private List<WorkoutSection> getWorkoutSectionsByWorkoutId(final int workoutId) {
        List<WorkoutSection> workoutSectionList = new ArrayList<>();
        String[] columns = {COLUMN_SECTION_ORDER, COLUMN_ROUNDS, COLUMN_WARM_UP, COLUMN_WORK, COLUMN_REST, COLUMN_COOL_DOWN};
        Cursor resultCursor = executeQuery(TABLE_WORKOUT_SECTIONS, columns, WHERE_WORKOUT_ID_EQUALS + workoutId, COLUMN_SECTION_ORDER);

        while (resultCursor.moveToNext()) {
            WorkoutSection currentWorkoutSection = workoutSectionFactory.create();
            currentWorkoutSection.setRounds(resultCursor.getInt(ROUNDS_COLUMN_INDEX));
            currentWorkoutSection.setWarmUp(resultCursor.getLong(WARM_UP_COLUMN_INDEX));
            currentWorkoutSection.setWork(resultCursor.getLong(WORK_COLUMN_INDEX));
            currentWorkoutSection.setRest(resultCursor.getLong(REST_COLUMN_INDEX));
            currentWorkoutSection.setCoolDown(resultCursor.getLong(COOL_DOWN_COLUMN_INDEX));
            workoutSectionList.add(currentWorkoutSection);

        }
        return workoutSectionList;
    }

    private Cursor executeQuery(final String tableName, final String[] columnsToSelect, final String whereClause, final String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tableName);
        if (whereClause != null) {
            queryBuilder.appendWhere(whereClause);
        }
        return queryBuilder.query(getDatabase(), columnsToSelect, null, null, null, null, sortOrder);
    }
}
