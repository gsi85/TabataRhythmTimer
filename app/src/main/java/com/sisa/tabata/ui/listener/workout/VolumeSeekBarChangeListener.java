package com.sisa.tabata.ui.listener.workout;

import android.media.AudioManager;
import android.widget.SeekBar;

import roboguice.inject.ContextSingleton;

/**
 * Volume seek bar change listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private AudioManager audioManager;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

}
