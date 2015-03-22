package com.sisa.tabata.media.service;

import android.media.AudioManager;
import android.media.SoundPool;

import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;

/**
 * Created by Laca on 2015.03.03..
 */
@Singleton
public class EffectPlayerService {

    private static final int NUMBER_OF_EFFECT = 3;
    private static final int SHORT_BEEP_INDEX = 0;
    private static final int LONG_BEEP_INDEX = 1;
    private static final int WORKOUT_OVER_INDEX = 2;

    private final SoundPool soundEffectPool;
    private final int[] soundIds;


    public EffectPlayerService() {
        soundEffectPool = new SoundPool(NUMBER_OF_EFFECT, AudioManager.STREAM_MUSIC, 0);
        soundIds = new int[NUMBER_OF_EFFECT];
        soundIds[SHORT_BEEP_INDEX] = soundEffectPool.load(TabataApplication.getAppContext(), R.raw.short_beep, 1);
        soundIds[LONG_BEEP_INDEX] = soundEffectPool.load(TabataApplication.getAppContext(), R.raw.long_beep, 1);
        soundIds[WORKOUT_OVER_INDEX] = soundEffectPool.load(TabataApplication.getAppContext(), R.raw.workout_over, 1);
    }

    public void playShortBeep() {
        playEffect(SHORT_BEEP_INDEX);
    }

    public void playLongBeep() {
       playEffect(LONG_BEEP_INDEX);
    }

    public void playWorkoutOver() {
        playEffect(WORKOUT_OVER_INDEX);
    }

    private void playEffect(int effectIndex) {
        soundEffectPool.play(soundIds[effectIndex], 1, 1, 1, 0, 1F);
    }
}
