package com.sisa.tabata.dao.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.sisa.tabata.R;
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
 * Created by Laca on 2015.03.19..
 */
@Singleton
public class DatabaseHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = TabataApplication.getAppContext().getResources().getInteger(R.integer.database_version);
    private static final String DATABASE_NAME = TabataApplication.getAppContext().getString(R.string.database_name);
    private static final String TABLE_WORKOUT = "tbl_workout";
    private static final String TABLE_WORKOUT_SECTIONS = "tbl_workout_sections";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TIME_UNIT = "timeUnit";
    private static final String COLUMN_WORKOUT_ID = "workoutId";
    private static final String COLUMN_SECTION_ORDER = "sectionOrder";
    private static final String COLUMN_ROUNDS = "rounds";
    private static final String COLUMN_WARM_UP = "warmUp";
    private static final String COLUMN_WORK = "work";
    private static final String COLUMN_REST = "rest";
    private static final String COLUMN_COOL_DOWN = "coolDown";
    private static final String COLUMN_ID = "id";
    private static final String WHERE_ID_EQUALS = "id=";
    private static final String WHERE_WORKOUT_ID_EQUALS = "workoutId=";

    @Inject
    private WorkoutFactory workoutFactory;
    @Inject
    private WorkoutSectionFactory workoutSectionFactory;

    public DatabaseHelper() {
        super(TabataApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public long insertWorkout(Workout workout) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues workoutValues = new ContentValues();
        //TODO: get safe name (check for null and duplicate)
        workoutValues.put(COLUMN_NAME, workout.getName());
        workoutValues.put(COLUMN_TIME_UNIT, workout.getTimeUnit().name());
        long workoutId = database.insert(TABLE_WORKOUT, null, workoutValues);

        for (int i = 0; i < workout.getWorkoutSections().size(); i++) {
            WorkoutSection currentWorkoutSection = workout.getWorkoutSections().get(i);
            ContentValues workoutSectionValues = new ContentValues();
            workoutSectionValues.put(COLUMN_WORKOUT_ID, workoutId);
            workoutSectionValues.put(COLUMN_SECTION_ORDER, i);
            workoutSectionValues.put(COLUMN_ROUNDS, currentWorkoutSection.getRounds());
            workoutSectionValues.put(COLUMN_WARM_UP, currentWorkoutSection.getWarmUp());
            workoutSectionValues.put(COLUMN_WORK, currentWorkoutSection.getWork());
            workoutSectionValues.put(COLUMN_REST, currentWorkoutSection.getRest());
            workoutSectionValues.put(COLUMN_COOL_DOWN, currentWorkoutSection.getCoolDown());
            database.insert(TABLE_WORKOUT_SECTIONS, null, workoutSectionValues);
        }

        close();
        return workoutId;
    }

    public Workout getWorkoutById(long id) {
        SQLiteDatabase database = getReadableDatabase();
        Workout workout = null;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlWorkoutSelect = {COLUMN_ID, COLUMN_NAME, COLUMN_TIME_UNIT};
        queryBuilder.setTables(TABLE_WORKOUT);
        queryBuilder.appendWhere(WHERE_ID_EQUALS);
        queryBuilder.appendWhere(Long.toString(id));
        Cursor resultCursor = queryBuilder.query(database, sqlWorkoutSelect, null, null, null, null, null);

        if (resultCursor.moveToFirst()) {
            int workoutId = resultCursor.getInt(0);
            workout = workoutFactory.createWithTimeUnit(null);
            workout.setId(workoutId);
            workout.setName(resultCursor.getString(1));
            workout.setTimeUnit(TimeUnit.valueOf(resultCursor.getString(2)));
            workout.setWorkoutSections(getWorkoutSectionsByWorkoutId(workoutId, database));
        }

        close();
        return workout;
    }

    private List<WorkoutSection> getWorkoutSectionsByWorkoutId(int workoutId, SQLiteDatabase database) {
        List<WorkoutSection> workoutSectionList = new ArrayList<>();

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String[] sqlWorkoutSectionSelect = {COLUMN_SECTION_ORDER, COLUMN_ROUNDS, COLUMN_WARM_UP, COLUMN_WORK, COLUMN_REST, COLUMN_COOL_DOWN};
        queryBuilder.setTables(TABLE_WORKOUT_SECTIONS);
        queryBuilder.appendWhere(WHERE_WORKOUT_ID_EQUALS);
        queryBuilder.appendWhere(Integer.toString(workoutId));
        Cursor resultCursor = queryBuilder.query(database, sqlWorkoutSectionSelect, null, null, null, null, COLUMN_SECTION_ORDER);

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

}
