package com.sisa.tabata.dao.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.sqlite.SQLiteDatabase;

/**
 * Unit test for {@link WorkoutDeleteDao}.
 *
 * @author Laszlo Sisa
 */
public class WorkoutDeleteDaoTest {

    private static final int TEST_WORKOUT_ID = 10;
    private static final int TEST_DATABASE_VERSION = 1;
    private static final String TEST_DATABASE_NAME = "test database";
    private static final String TEST_DATA_DIR = "test data dir";

    private WorkoutDeleteDao underTest;
    @Mock
    private ApplicationContextProvider applicationContextProvider;
    @Mock
    private Context context;
    @Mock
    private SQLiteDatabase sqLiteDatabase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ApplicationInfo applicationInfo = new ExtendedApplicationInfo();
        when(applicationContextProvider.getIntResource(R.integer.database_version)).thenReturn(TEST_DATABASE_VERSION);
        when(applicationContextProvider.getStringResource(R.string.database_name)).thenReturn(TEST_DATABASE_NAME);
        when(applicationContextProvider.getContext()).thenReturn(context);
        when(context.getApplicationInfo()).thenReturn(applicationInfo);
        underTest = new ExtendedWorkoutDeleteDao(applicationContextProvider);
    }

    @Test
    public void testDeleteWorkoutByIdWhenCalledShouldDeleteWorkout() {
        //GIVEN in setup

        //WHEN
        underTest.deleteWorkoutById(TEST_WORKOUT_ID);

        //THEN
        verify(sqLiteDatabase).delete(WorkoutDeleteDao.TABLE_WORKOUT_SECTIONS, WorkoutDeleteDao.WHERE_WORKOUT_ID_EQUALS + TEST_WORKOUT_ID, null);
        verify(sqLiteDatabase).delete(WorkoutDeleteDao.TABLE_WORKOUT, WorkoutDeleteDao.WHERE_ID_EQUALS + TEST_WORKOUT_ID, null);
    }

    private class ExtendedApplicationInfo extends ApplicationInfo {

        private ExtendedApplicationInfo() {
            this.dataDir = TEST_DATA_DIR;
        }
    }

    private class ExtendedWorkoutDeleteDao extends WorkoutDeleteDao {

        public ExtendedWorkoutDeleteDao(final ApplicationContextProvider applicationContextProvider) {
            super(applicationContextProvider);
        }

        @Override
        public synchronized SQLiteDatabase getWritableDatabase() {
            return sqLiteDatabase;
        }
    }

}
