package com.sisa.tabata.factory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.sisa.tabata.domain.WorkoutSection;

/**
 * Unit test for {@link WorkoutSectionFactory}.
 *
 * @author Laszlo Sisa
 */
public class WorkoutSectionFactoryTest {

    private WorkoutSectionFactory underTest;

    @Before
    public void setUp() {
        underTest = new WorkoutSectionFactory();
    }

    @Test
    public void testCreateWhenCalledShouldReturnObject() {
        //GIVEN in setup

        //WHEN
        WorkoutSection result = underTest.create();

        //THEN
        Assert.assertNotNull(result);
    }

}
