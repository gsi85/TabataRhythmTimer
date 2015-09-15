package com.sisa.tabata.dao.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sisa.tabata.domain.Workout;

/**
 * Unit test for {@link WorkoutDao}.
 *
 * @author Laszlo sisa
 */
public class WorkoutDaoTest {

    private static final long TEST_WORKOUT_ID = 10;

    @InjectMocks
    private WorkoutDao underTest;
    @Mock
    private WorkoutInsertDao workoutInsertDao;
    @Mock
    private WorkoutRetrieveDao workoutRetrieveDao;
    @Mock
    private WorkoutDeleteDao workoutDeleteDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertUpdateWorkoutWhenCalledShouldInsertWorkout() {
        //GIVEN
        Workout newWorkout = new Workout();
        when(workoutInsertDao.insertUpdateWorkout(newWorkout)).thenReturn(TEST_WORKOUT_ID);

        //WHEN
        long result = underTest.insertUpdateWorkout(newWorkout);

        //THEN
        verify(workoutInsertDao).insertUpdateWorkout(newWorkout);
        Assert.assertEquals(TEST_WORKOUT_ID, result);
    }

    @Test
    public void testGetWorkoutByIdWhenCalledShouldReturnWorkout() {
        //GIVEN
        Workout existingWorkout = new Workout();
        when(workoutRetrieveDao.getWorkoutById(TEST_WORKOUT_ID)).thenReturn(existingWorkout);

        //WHEN
        Workout result = underTest.getWorkoutById(TEST_WORKOUT_ID);

        //THEN
        verify(workoutRetrieveDao).getWorkoutById(TEST_WORKOUT_ID);
        Assert.assertSame(existingWorkout, result);
    }

    @Test
    public void testGetAllWorkoutsSortedListWhenCalledShouldReturnWorkoutList() {
        //GIVEN
        List<Workout> existingWorkouts = Collections.singletonList(new Workout());
        when(workoutRetrieveDao.getAllWorkoutsSortedList()).thenReturn(existingWorkouts);

        //WHEN
        List<Workout> result = underTest.getAllWorkoutsSortedList();

        //THEN
        verify(workoutRetrieveDao).getAllWorkoutsSortedList();
        Assert.assertSame(existingWorkouts, result);
    }

    @Test
    public void testDeleteWorkoutByIdWhenCalledShouldDeleteWorkout() {
        //GIVEN in setup

        //WHEN
        underTest.deleteWorkoutById(TEST_WORKOUT_ID);

        //THEN
        verify(workoutDeleteDao).deleteWorkoutById(TEST_WORKOUT_ID);
    }
}
