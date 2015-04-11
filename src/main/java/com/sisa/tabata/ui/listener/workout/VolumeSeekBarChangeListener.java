package com.sisa.tabata.ui.listener.workout;

import android.media.AudioManager;
import android.widget.SeekBar;
import com.google.inject.Inject;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.media.service.EffectPlayerService;
import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.26..
 */
@ContextSingleton
public class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    @Inject
    private EffectPlayerService effectPlayerService;
    private AudioManager audioManager;

    public VolumeSeekBarChangeListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        effectPlayerService.playShortBeep();
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
