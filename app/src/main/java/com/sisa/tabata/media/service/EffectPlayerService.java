package com.sisa.tabata.media.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.factory.SoundPoolFactory;

import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Service for playing sound effects.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class EffectPlayerService {

    private static final int NUMBER_OF_EFFECTS = 3;
    private static final int SHORT_BEEP_INDEX = 0;
    private static final int LONG_BEEP_INDEX = 1;
    private static final int WORKOUT_OVER_INDEX = 2;
    private static final int DEFAULT_SOUND_PRIORITY = 1;
    private static final int NO_LOOP_MODE = 0;
    private static final int VOLUME = 1;
    private static final float NORMAL_PLAYBACK_RATE = 1F;

    private final SoundPool soundEffectPool;
    private final int[] soundIds;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     * @param soundPoolFactory {@link SoundPoolFactory}
     */
    @Inject
    public EffectPlayerService(final ApplicationContextProvider applicationContextProvider, final SoundPoolFactory soundPoolFactory) {
        soundEffectPool = soundPoolFactory.create(NUMBER_OF_EFFECTS, AudioManager.STREAM_MUSIC);
        soundIds = new int[NUMBER_OF_EFFECTS];
        soundIds[SHORT_BEEP_INDEX] = soundEffectPool.load(applicationContextProvider.getContext(), R.raw.short_beep, DEFAULT_SOUND_PRIORITY);
        soundIds[LONG_BEEP_INDEX] = soundEffectPool.load(applicationContextProvider.getContext(), R.raw.long_beep, DEFAULT_SOUND_PRIORITY);
        soundIds[WORKOUT_OVER_INDEX] = soundEffectPool.load(applicationContextProvider.getContext(), R.raw.workout_over, DEFAULT_SOUND_PRIORITY);
    }

    /**
     * Plays a short beep effect.
     */
    public void playShortBeep() {
        playEffect(SHORT_BEEP_INDEX);
    }

    /**
     * Plays a long beep effect.
     */
    public void playLongBeep() {
        playEffect(LONG_BEEP_INDEX);
    }

    /**
     * Play the workout over effect.
     */
    public void playWorkoutOver() {
        playEffect(WORKOUT_OVER_INDEX);
    }

    private void playEffect(int effectIndex) {
        soundEffectPool.play(soundIds[effectIndex], VOLUME, VOLUME, DEFAULT_SOUND_PRIORITY, NO_LOOP_MODE, NORMAL_PLAYBACK_RATE);
    }
}
