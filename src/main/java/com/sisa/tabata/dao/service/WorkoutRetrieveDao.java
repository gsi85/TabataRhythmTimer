package com.sisa.tabata.dao.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutFactory;
import com.sisa.tabata.factory.WorkoutSectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.03.24..
 */
@Singleton
public class WorkoutRetrieveDao extends AbstractBaseDao {

    @Inject
    private WorkoutFactory workoutFactory;
    @Inject
    private WorkoutSectionFactory workoutSectionFactory;

    public WorkoutRetrieveDao() {
        super(TabataApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    protected Workout getWorkoutById(long id) {
        String whereClause = WHERE_ID_EQUALS + id;
        return getWorkoutWithSpecifiedCondition(whereClause).get(0);
    }

    protected List<Workout> getAllWorkoutsSortedList() {
        return getWorkoutWithSpecifiedCondition(null);
    }

    private List<Workout> getWorkoutWithSpecifiedCondition(String whereClause) {
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

    private List<WorkoutSection> getWorkoutSectionsByWorkoutId(int workoutId) {
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

    private Cursor executeQuery(String tableName, String[] columnsToSelect, String whereClause, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tableName);
        if (whereClause != null) queryBuilder.appendWhere(whereClause);
        return queryBuilder.query(getDatabase(), columnsToSelect, null, null, null, null, sortOrder);
    }
}
