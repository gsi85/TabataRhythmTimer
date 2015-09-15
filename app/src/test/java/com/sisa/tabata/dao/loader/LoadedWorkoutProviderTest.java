package com.sisa.tabata.dao.loader;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sisa.tabata.dao.service.WorkoutDao;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.workout.transformer.SerializedWorkoutTransformer;

/**
 * Unit test for {@link LoadedWorkoutProvider}.
 *
 * @author Laszlo Sisa
 */
public class LoadedWorkoutProviderTest {

    private static final long TEST_ID = 1;

    @InjectMocks
    private LoadedWorkoutProvider underTest;
    @Mock
    private SerializedWorkoutTransformer serializedWorkoutTransformer;
    @Mock
    private WorkoutDao workoutDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLoadedWorkoutWhenWorkoutNotSetShouldReturnFirstWorkout() {
        //GIVEN
        Workout expectedWorkout = new Workout();
        when(workoutDao.getAllWorkoutsSortedList()).thenReturn(Collections.singletonList(expectedWorkout));

        //WHEN
        Workout actualWorkout = underTest.getLoadedWorkout();

        //THEN
        verify(workoutDao).getAllWorkoutsSortedList();
        Assert.assertEquals(expectedWorkout, actualWorkout);
    }

    @Test
    public void testGetLoadedSerializedWorkoutNotSetShouldReturnFirstWorkout() {
        //GIVEN
        SerializedWorkout expectedWorkout = new SerializedWorkout();
        Workout workout = new Workout();
        when(workoutDao.getAllWorkoutsSortedList()).thenReturn(Collections.singletonList(workout));
        when(serializedWorkoutTransformer.transform(workout)).thenReturn(expectedWorkout);

        //WHEN
        SerializedWorkout actualWorkout = underTest.getLoadedSerializedWorkout();

        //THEN
        verify(workoutDao).getAllWorkoutsSortedList();
        verify(serializedWorkoutTransformer).transform(workout);
        Assert.assertEquals(expectedWorkout, actualWorkout);
    }

    @Test
    public void testLoadFirstWorkoutInListWhenCalledShouldSetWorkout() {
        //GIVEN
        Workout expectedWorkout = new Workout();
        when(workoutDao.getAllWorkoutsSortedList()).thenReturn(Collections.singletonList(expectedWorkout));

        //WHEN
        underTest.loadFirstWorkoutInList();

        //THEN
        verify(workoutDao).getAllWorkoutsSortedList();
        Assert.assertEquals(expectedWorkout, underTest.getLoadedWorkout());
    }

    @Test
    public void testSetLoadedWorkoutByIdWhenCalledShouldSetWorkout() {
        //GIVEN
        Workout expectedWorkout = new Workout();
        when(workoutDao.getWorkoutById(TEST_ID)).thenReturn(expectedWorkout);

        //WHEN
        underTest.setLoadedWorkoutById(TEST_ID);

        //THEN
        verify(workoutDao).getWorkoutById(TEST_ID);
        Assert.assertEquals(expectedWorkout, underTest.getLoadedWorkout());
    }

}
