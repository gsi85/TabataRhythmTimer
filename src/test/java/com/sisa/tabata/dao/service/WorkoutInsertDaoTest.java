package com.sisa.tabata.dao.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataTestRunner;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.factory.WorkoutSectionFactory;

/**
 * Unit test for {@link WorkoutInsertDao}.
 *
 * @author Laszlo Sisa
 */
@RunWith(TabataTestRunner.class)
@Config(emulateSdk = 16)
public class WorkoutInsertDaoTest {

    private static final int NEW_WORKOUT_ID = -1;
    private static final int TEST_WORKOUT_ID = 10;
    private static final int TEST_RECORD_COUNT = 5;
    private static final int TEST_DATABASE_VERSION = 1;
    private static final String TEST_DATABASE_NAME = "test database";
    private static final String TEST_DATA_DIR = "test data dir";
    private static final String TEST_NAME = "test name";
    private static final String TEST_DESCRIPTION = "test description";

    private WorkoutInsertDao underTest;
    @Mock
    private ApplicationContextProvider applicationContextProvider;
    @Mock
    private SQLiteDatabase sqLiteDatabase;
    @Mock
    private WorkoutSectionFactory workoutSectionFactory;
    @Mock
    private Context context;
    @Mock
    private SQLiteQueryBuilder queryBuilder;
    @Mock
    private Cursor cursor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ApplicationInfo applicationInfo = new ExtendedApplicationInfo();
        when(applicationContextProvider.getIntResource(R.integer.database_version)).thenReturn(TEST_DATABASE_VERSION);
        when(applicationContextProvider.getStringResource(R.string.database_name)).thenReturn(TEST_DATABASE_NAME);
        when(applicationContextProvider.getContext()).thenReturn(context);
        when(context.getApplicationInfo()).thenReturn(applicationInfo);
        underTest = new ExtendedWorkoutInsertDao(applicationContextProvider, workoutSectionFactory);
    }

    @Test
    public void testInsertUpdateWorkoutWithExistingWorkoutShouldUpdate() {
        //GIVEN
        Workout workout = new Workout();
        workout.setWorkoutSections(Collections.singletonList(new WorkoutSection()));
        workout.setName(TEST_NAME);
        workout.setDescription(TEST_DESCRIPTION);
        workout.setTimeUnit(TimeUnit.SECONDS);
        workout.setId(TEST_WORKOUT_ID);

        when(applicationContextProvider.getIntResource(R.integer.new_workout_id)).thenReturn(NEW_WORKOUT_ID);

        //WHEN
        long result = underTest.insertUpdateWorkout(workout);

        //THEN
        verify(applicationContextProvider).getIntResource(R.integer.new_workout_id);
        verify(sqLiteDatabase).update(eq(WorkoutInsertDao.TABLE_WORKOUT), any(ContentValues.class),
                eq(WorkoutInsertDao.WHERE_ID_EQUALS + TEST_WORKOUT_ID), any(String[].class));
        verify(sqLiteDatabase).delete(WorkoutInsertDao.TABLE_WORKOUT_SECTIONS, WorkoutInsertDao.WHERE_WORKOUT_ID_EQUALS + TEST_WORKOUT_ID, null);
        verify(sqLiteDatabase).insert(eq(WorkoutInsertDao.TABLE_WORKOUT_SECTIONS), any(String.class), any(ContentValues.class));
        Assert.assertEquals(TEST_WORKOUT_ID, result);
    }

    @Test
    public void testInsertUpdateWorkoutWithNewWorkoutShouldInsertWithDefaultValues() {
        //GIVEN
        Workout workout = new Workout();
        workout.setWorkoutSections(Collections.singletonList(new WorkoutSection()));
        workout.setTimeUnit(TimeUnit.SECONDS);
        workout.setId(NEW_WORKOUT_ID);

        when(applicationContextProvider.getIntResource(R.integer.new_workout_id)).thenReturn(NEW_WORKOUT_ID);
        when(sqLiteDatabase.rawQueryWithFactory(null, "SELECT * FROM tbl_workout WHERE (name LIKE 'Untitled-%')", null, "tbl_workout", null)).thenReturn(cursor);
        when(cursor.getCount()).thenReturn(TEST_RECORD_COUNT);
        when(sqLiteDatabase.insert(eq(WorkoutInsertDao.TABLE_WORKOUT), any(String.class), any(ContentValues.class))).thenReturn(
                (long) TEST_WORKOUT_ID);

        //WHEN
        long result = underTest.insertUpdateWorkout(workout);

        //THEN
        verify(applicationContextProvider).getIntResource(R.integer.new_workout_id);
        verify(sqLiteDatabase).rawQueryWithFactory(null, "SELECT * FROM tbl_workout WHERE (name LIKE 'Untitled-%')", null, "tbl_workout", null);
        verify(cursor).getCount();
        verify(sqLiteDatabase).insert(eq(WorkoutInsertDao.TABLE_WORKOUT), any(String.class), any(ContentValues.class));
        verify(sqLiteDatabase).delete(WorkoutInsertDao.TABLE_WORKOUT_SECTIONS, WorkoutInsertDao.WHERE_WORKOUT_ID_EQUALS + TEST_WORKOUT_ID, null);
        verify(sqLiteDatabase).insert(eq(WorkoutInsertDao.TABLE_WORKOUT_SECTIONS), any(String.class), any(ContentValues.class));
        Assert.assertEquals(TEST_WORKOUT_ID, result);
    }

    private class ExtendedApplicationInfo extends ApplicationInfo {

        private ExtendedApplicationInfo() {
            this.dataDir = TEST_DATA_DIR;
        }
    }

    private class ExtendedWorkoutInsertDao extends WorkoutInsertDao {

        private ExtendedWorkoutInsertDao(final ApplicationContextProvider applicationContextProvider,
                final WorkoutSectionFactory workoutSectionFactory) {
            super(applicationContextProvider, workoutSectionFactory);
        }

        @Override
        public synchronized SQLiteDatabase getWritableDatabase() {
            return sqLiteDatabase;
        }
    }

}
