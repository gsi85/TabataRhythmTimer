package com.sisa.tabata.media.factory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Unit test for {@link SoundPoolFactory}.
 *
 * @author Laszlo Sisa
 */
public class SoundPoolFactoryTest {

    private static final int TEST_MAX_STREAM = 10;

    private SoundPoolFactory underTest;

    @Before
    public void setUp() {
        underTest = new SoundPoolFactory();
    }

    @Test
    public void testCreateWhenCalledShouldCreateObject() {
        //GIVEN in setup

        //WHEN
        SoundPool result = underTest.create(TEST_MAX_STREAM, AudioManager.STREAM_MUSIC);

        //THEN
        Assert.assertNotNull(result);
    }

}
