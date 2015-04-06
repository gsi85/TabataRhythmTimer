package com.sisa.tabata.dao.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

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
import com.sisa.tabata.factory.WorkoutFactory;
import com.sisa.tabata.factory.WorkoutSectionFactory;

/**
 * Unit test for {@link WorkoutRetrieveDao}.
 *
 * @author Laszlo Sisa
 */
@RunWith(TabataTestRunner.class)
@Config(emulateSdk = 16)
public class WorkoutRetrieveDaoTest {

    private static final int TEST_DATABASE_VERSION = 1;
    private static final int TEST_EXISTING_ID = 5;
    private static final int TEST_NON_EXISTING_ID = 10;
    private static final String TEST_DATABASE_NAME = "test database";
    private static final String[] SECTION_COLUMNS = new String[]{WorkoutRetrieveDao.COLUMN_SECTION_ORDER, WorkoutRetrieveDao.COLUMN_ROUNDS,
        WorkoutRetrieveDao.COLUMN_WARM_UP, WorkoutRetrieveDao.COLUMN_WORK, WorkoutRetrieveDao.COLUMN_REST, WorkoutRetrieveDao.COLUMN_COOL_DOWN};

    private static final String[] WORKOUT_COLUMNS = new String[]{WorkoutRetrieveDao.COLUMN_ID, WorkoutRetrieveDao.COLUMN_NAME,
        WorkoutRetrieveDao.COLUMN_TIME_UNIT, WorkoutRetrieveDao.COLUMN_DESCRIPTION};
    private static final String TEST_DATA_DIR = "test data dir";

    private WorkoutRetrieveDao underTest;
    @Mock
    private ApplicationContextProvider applicationContextProvider;
    @Mock
    private Context context;
    @Mock
    private SQLiteDatabase sqLiteDatabase;
    @Mock
    private WorkoutFactory workoutFactory;
    @Mock
    private WorkoutSectionFactory workoutSectionFactory;
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
        underTest = new ExtendedWorkoutRetrieveDao(applicationContextProvider, workoutFactory, workoutSectionFactory, queryBuilder);
    }

    @Test
    public void testGetWorkoutByIdWithNonExistingIdShouldReturnNull() {
        //GIVEN
        when(queryBuilder.query(sqLiteDatabase, WORKOUT_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_NAME)).thenReturn(cursor);

        //WHEN
        Workout result = underTest.getWorkoutById(TEST_NON_EXISTING_ID);

        //THEN
        verify(queryBuilder).setTables(WorkoutRetrieveDao.TABLE_WORKOUT);
        verify(queryBuilder).appendWhere(WorkoutRetrieveDao.WHERE_ID_EQUALS + TEST_NON_EXISTING_ID);
        verify(queryBuilder).query(sqLiteDatabase, WORKOUT_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_NAME);
        Assert.assertNull(result);
    }

    @Test
    public void testGetWorkoutByIdWithExistingIdShouldReturnWorkout() {
        //GIVEN
        Workout workout = new Workout();
        when(queryBuilder.query(sqLiteDatabase, WORKOUT_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_NAME)).thenReturn(cursor);
        when(cursor.moveToNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(cursor.getInt(0)).thenReturn(TEST_EXISTING_ID);
        when(workoutFactory.createWithTimeUnit(null)).thenReturn(workout);
        when(cursor.getString(2)).thenReturn(TimeUnit.SECONDS.name());
        when(queryBuilder.query(sqLiteDatabase, SECTION_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_SECTION_ORDER)).thenReturn(cursor);
        when(workoutSectionFactory.create()).thenReturn(new WorkoutSection());

        //WHEN
        Workout result = underTest.getWorkoutById(TEST_EXISTING_ID);

        //THEN
        verify(queryBuilder).setTables(WorkoutRetrieveDao.TABLE_WORKOUT);
        verify(queryBuilder).appendWhere(WorkoutRetrieveDao.WHERE_ID_EQUALS + TEST_EXISTING_ID);
        verify(queryBuilder).query(sqLiteDatabase, WORKOUT_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_NAME);
        verify(cursor, times(4)).moveToNext();
        verify(cursor).getInt(0);
        verify(workoutFactory).createWithTimeUnit(null);
        verify(cursor).getString(2);
        verify(queryBuilder).setTables(WorkoutRetrieveDao.TABLE_WORKOUT_SECTIONS);
        verify(queryBuilder).appendWhere(WorkoutRetrieveDao.WHERE_WORKOUT_ID_EQUALS + TEST_EXISTING_ID);
        verify(queryBuilder).query(sqLiteDatabase, SECTION_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_SECTION_ORDER);
        verify(workoutSectionFactory).create();
        Assert.assertSame(workout, result);
    }

    @Test
    public void testGetAllWorkoutsSortedListWhenDatabaseEmptyShouldReturnEmptyList() {
        //GIVEN
        when(queryBuilder.query(sqLiteDatabase, WORKOUT_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_NAME)).thenReturn(cursor);

        //WHEN
        List<Workout> result = underTest.getAllWorkoutsSortedList();

        //THEN
        verify(queryBuilder).setTables(WorkoutRetrieveDao.TABLE_WORKOUT);
        verify(queryBuilder).query(sqLiteDatabase, WORKOUT_COLUMNS, null, null, null, null, WorkoutRetrieveDao.COLUMN_NAME);
        Assert.assertEquals(0, result.size());
    }

    private class ExtendedApplicationInfo extends ApplicationInfo {

        private ExtendedApplicationInfo() {
            this.dataDir = TEST_DATA_DIR;
        }
    }

    private class ExtendedWorkoutRetrieveDao extends WorkoutRetrieveDao {

        private ExtendedWorkoutRetrieveDao(final ApplicationContextProvider applicationContextProvider, final WorkoutFactory workoutFactory,
                final WorkoutSectionFactory workoutSectionFactory, final SQLiteQueryBuilder queryBuilder) {
            super(applicationContextProvider, workoutFactory, workoutSectionFactory, queryBuilder);
        }

        @Override
        public synchronized SQLiteDatabase getWritableDatabase() {
            return sqLiteDatabase;
        }
    }

}
