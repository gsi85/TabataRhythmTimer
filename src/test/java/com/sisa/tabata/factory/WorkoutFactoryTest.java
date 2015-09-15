package com.sisa.tabata.factory;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;

/**
 * Unit test for {@link WorkoutFactory}.
 *
 * @author Laszlo Sisa
 */
public class WorkoutFactoryTest {

    private static final int NEW_WORKOUT_ID = -1;
    private static final int EXPECTED_SECTIONS_SIE = 0;

    private WorkoutFactory underTest;
    @Mock
    private ApplicationContextProvider applicationContextProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(applicationContextProvider.getIntResource(R.integer.new_workout_id)).thenReturn(NEW_WORKOUT_ID);
        underTest = new WorkoutFactory(applicationContextProvider);
    }

    @Test
    public void testCreateWithTimeUnitWhenCalledShouldCreateObject() {
        //GIVEN in setup

        //WHEN
        Workout result = underTest.createWithTimeUnit(TimeUnit.SECONDS);

        //THEN
        verify(applicationContextProvider).getIntResource(R.integer.new_workout_id);
        Assert.assertNotNull(result);
        Assert.assertEquals(TimeUnit.SECONDS, result.getTimeUnit());
        Assert.assertEquals(NEW_WORKOUT_ID, result.getId());
        Assert.assertNotNull(result.getWorkoutSections());
        Assert.assertEquals(EXPECTED_SECTIONS_SIE, result.getWorkoutSections().size());
    }

}
