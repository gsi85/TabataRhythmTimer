package com.sisa.tabata.dao.loader;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.factory.WorkoutFactory;

/**
 * Unit test for {@link EditedWorkoutProvider}.
 */
public class EditedWorkoutProviderTest {

    private static final int NEW_WORKOUT_ID = -1;
    private static final int EXISTING_WORKOUT_ID = 1;

    @InjectMocks
    private EditedWorkoutProvider underTest;
    @Mock
    private WorkoutDao workoutDao;
    @Mock
    private WorkoutFactory workoutFactory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void teardown() {
        Mockito.reset();
    }

    @Test
    public void testGetEditedWorkoutWhenWorkoutNotSetShouldReturnNull() {
        //GIVEN in setup

        //WHEN
        Workout actualWorkout = underTest.getEditedWorkout();

        //THEN
        Assert.assertNull(actualWorkout);
    }

    @Test
    public void testSetEditedWorkoutWithNewWorkoutIdShouldCreateNewWorkout() {
        //GIVEN
        Workout expectedWorkout = new Workout();
        when(workoutFactory.createWithTimeUnit(TimeUnit.SECONDS)).thenReturn(expectedWorkout);

        //WHEN
        underTest.setEditedWorkout(NEW_WORKOUT_ID);

        //THEN
        verify(workoutFactory).createWithTimeUnit(TimeUnit.SECONDS);
        Assert.assertSame(expectedWorkout, underTest.getEditedWorkout());
    }

    @Test
    public void testSetEditedWorkoutWithExistingWorkoutIdShouldLoadNewWorkout() {
        //GIVEN
        Workout expectedWorkout = new Workout();
        when(workoutDao.getWorkoutById(EXISTING_WORKOUT_ID)).thenReturn(expectedWorkout);

        //WHEN
        underTest.setEditedWorkout(EXISTING_WORKOUT_ID);

        //THEN
        verify(workoutDao).getWorkoutById(EXISTING_WORKOUT_ID);
        Assert.assertSame(expectedWorkout, underTest.getEditedWorkout());
    }

}
