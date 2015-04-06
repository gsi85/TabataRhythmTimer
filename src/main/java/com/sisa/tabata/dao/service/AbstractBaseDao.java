package com.sisa.tabata.dao.service;

import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;

/**
 * Base class for DAO classes, holding writable {@link SQLiteDatabase} and commonly used constants.
 *
 * @author Laszlo Sisa
 */
public abstract class AbstractBaseDao extends SQLiteAssetHelper {

    protected static final String TABLE_WORKOUT = "tbl_workout";
    protected static final String TABLE_WORKOUT_SECTIONS = "tbl_workout_sections";
    protected static final String COLUMN_NAME = "name";
    protected static final String COLUMN_TIME_UNIT = "timeUnit";
    protected static final String COLUMN_WORKOUT_ID = "workoutId";
    protected static final String COLUMN_SECTION_ORDER = "sectionOrder";
    protected static final String COLUMN_ROUNDS = "rounds";
    protected static final String COLUMN_WARM_UP = "warmUp";
    protected static final String COLUMN_WORK = "work";
    protected static final String COLUMN_REST = "rest";
    protected static final String COLUMN_COOL_DOWN = "coolDown";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_DESCRIPTION = "description";
    protected static final String WHERE_ID_EQUALS = "id=";
    protected static final String WHERE_WORKOUT_ID_EQUALS = "workoutId=";
    protected static final String DEFAULT_WORKOUT_NAME = "Untitled-";

    private final SQLiteDatabase database;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     */
    public AbstractBaseDao(ApplicationContextProvider applicationContextProvider) {
        super(applicationContextProvider.getContext(), applicationContextProvider.getStringResource(R.string.database_name), null,
                applicationContextProvider.getIntResource(R.integer.database_version));
        database = getWritableDatabase();
    }

    /**
     * Returns writable {@link SQLiteDatabase}.
     *
     * @return {@link SQLiteDatabase}
     */
    protected SQLiteDatabase getDatabase() {
        return database;
    }

}
