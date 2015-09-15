package com.sisa.tabata.media.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.factory.SoundPoolFactory;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Unit test for {@link EffectPlayerService}.
 *
 * @author Laszlo Sisa
 */
public class EffectPlayerServiceTest {

    private static final int TEST_NUMBER_OF_EFFECTS = 3;
    private static final int DEFAULT_SOUND_PRIORITY = 1;
    private static final int TEST_SHORT_BEEP_ID = 1;
    private static final int TEST_LONG_BEEP_ID = 2;
    private static final int TEST_WORKOUT_OVER_ID = 3;
    private static final int NO_LOOP_MODE = 0;
    private static final int VOLUME = 1;
    private static final float NORMAL_PLAYBACK_RATE = 1F;

    private EffectPlayerService underTest;
    @Mock
    private ApplicationContextProvider applicationContextProvider;
    @Mock
    private Context context;
    @Mock
    private SoundPoolFactory soundPoolFactory;
    @Mock
    private SoundPool soundPool;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(applicationContextProvider.getContext()).thenReturn(context);
        when(soundPoolFactory.create(TEST_NUMBER_OF_EFFECTS, AudioManager.STREAM_MUSIC)).thenReturn(soundPool);
        when(soundPool.load(context, R.raw.short_beep, DEFAULT_SOUND_PRIORITY)).thenReturn(TEST_SHORT_BEEP_ID);
        when(soundPool.load(context, R.raw.long_beep, DEFAULT_SOUND_PRIORITY)).thenReturn(TEST_LONG_BEEP_ID);
        when(soundPool.load(context, R.raw.workout_over, DEFAULT_SOUND_PRIORITY)).thenReturn(TEST_WORKOUT_OVER_ID);
        underTest = new EffectPlayerService(applicationContextProvider, soundPoolFactory);
    }

    @Test
    public void testPlayShortBeepWhenCalledShouldPlaySound() {
        //GIVEN
        when(soundPool.play(TEST_SHORT_BEEP_ID, VOLUME, VOLUME, DEFAULT_SOUND_PRIORITY, NO_LOOP_MODE, NORMAL_PLAYBACK_RATE)).thenReturn(
                TEST_SHORT_BEEP_ID);

        //WHEN
        underTest.playShortBeep();

        //THEN
        verifyConstructorCalls();
        verify(soundPool).play(TEST_SHORT_BEEP_ID, VOLUME, VOLUME, DEFAULT_SOUND_PRIORITY, NO_LOOP_MODE, NORMAL_PLAYBACK_RATE);
    }

    @Test
    public void testPlayLongBeepWhenCalledShouldPlaySound() {
        //GIVEN
        when(soundPool.play(TEST_LONG_BEEP_ID, VOLUME, VOLUME, DEFAULT_SOUND_PRIORITY, NO_LOOP_MODE, NORMAL_PLAYBACK_RATE)).thenReturn(
                TEST_LONG_BEEP_ID);

        //WHEN
        underTest.playLongBeep();

        //THEN
        verifyConstructorCalls();
        verify(soundPool).play(TEST_LONG_BEEP_ID, VOLUME, VOLUME, DEFAULT_SOUND_PRIORITY, NO_LOOP_MODE, NORMAL_PLAYBACK_RATE);
    }

    @Test
    public void testPlayWorkoutOverWhenCalledShouldPlaySound() {
        //GIVEN
        when(soundPool.play(TEST_WORKOUT_OVER_ID, VOLUME, VOLUME, DEFAULT_SOUND_PRIORITY, NO_LOOP_MODE, NORMAL_PLAYBACK_RATE)).thenReturn(
                TEST_WORKOUT_OVER_ID);

        //WHEN
        underTest.playWorkoutOver();

        //THEN
        verifyConstructorCalls();
        verify(soundPool).play(TEST_WORKOUT_OVER_ID, VOLUME, VOLUME, DEFAULT_SOUND_PRIORITY, NO_LOOP_MODE, NORMAL_PLAYBACK_RATE);
    }

    private void verifyConstructorCalls() {
        verify(applicationContextProvider, times(3)).getContext();
        verify(soundPool).load(context, R.raw.short_beep, DEFAULT_SOUND_PRIORITY);
        verify(soundPool).load(context, R.raw.long_beep, DEFAULT_SOUND_PRIORITY);
        verify(soundPool).load(context, R.raw.workout_over, DEFAULT_SOUND_PRIORITY);
    }

}
