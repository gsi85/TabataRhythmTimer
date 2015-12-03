package com.sisa.tabata.ui.listener.workout;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.sisa.tabata.ui.provider.VolumeButtonImageResourceProvider;

import android.media.AudioManager;
import android.widget.ImageButton;
import android.widget.SeekBar;

import roboguice.inject.ContextSingleton;

/**
 * Volume seek bar change listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class VolumeSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private final List<ImageButton> musicVolumeIcons = new ArrayList<>();

    @Inject
    private VolumeButtonImageResourceProvider volumeButtonImageResourceProvider;
    private AudioManager audioManager;

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        volumeButtonImageResourceProvider.setVolumeImageResource(musicVolumeIcons);
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

    /**
     * Adds an image button to the list of views to update when volume changes.
     *
     * @param musicVolumeIcon {@link ImageButton}
     */
    public void addMusicVolumeIcon(final ImageButton musicVolumeIcon) {
        musicVolumeIcons.add(musicVolumeIcon);
    }
}
