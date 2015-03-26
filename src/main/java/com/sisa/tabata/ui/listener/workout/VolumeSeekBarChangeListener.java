package com.sisa.tabata.ui.listener.workout;

import android.media.AudioManager;
import android.widget.SeekBar;

import com.google.inject.Singleton;

/**
 * Created by Laca on 2015.03.26..
 */
@Singleton
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
