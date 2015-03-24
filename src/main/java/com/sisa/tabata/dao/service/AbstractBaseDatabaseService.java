package com.sisa.tabata.dao.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;

/**
 * Created by Laca on 2015.03.24..
 */
public abstract class AbstractBaseDatabaseService extends SQLiteAssetHelper {

    protected static final int NEW_WORKOUT_ID = -1;
    protected static final int DATABASE_VERSION = TabataApplication.getAppContext().getResources().getInteger(R.integer.database_version);
    protected static final String DATABASE_NAME = TabataApplication.getAppContext().getString(R.string.database_name);
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
    protected static final String WHERE_ID_EQUALS = "id=";
    protected static final String WHERE_WORKOUT_ID_EQUALS = "workoutId=";
    protected static final String DEFAULT_WORKOUT_NAME = "Untitled-";

    private final SQLiteDatabase database;

    public AbstractBaseDatabaseService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        database = getWritableDatabase();
    }

    protected SQLiteDatabase getDatabase() {
        return database;
    }
}
