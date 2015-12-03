package com.sisa.tabata.ui.provider;

import java.util.List;

import com.google.inject.Singleton;
import com.sisa.tabata.R;

import android.content.Context;
import android.media.AudioManager;
import android.widget.ImageButton;

/**
 * Volume button image resource provider.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class VolumeButtonImageResourceProvider {

    private static final float HIGH_LEVEL_THRESHOLD = 0.66f;
    private static final float MEDIUM_LEVEL_THRESHOLD = 0.33f;
    private static final float LOW_LEVEL_THRESHOLD = 0f;

    /**
     * Sets the {@link ImageButton} drawable resource according to the current volume level of the music stream.
     *
     * @param volumeButtons list of {@link ImageButton}
     */
    public void setVolumeImageResource(final List<ImageButton> volumeButtons) {
        for (ImageButton volumeButton : volumeButtons) {
            float currentVolumeLevel = getCurrentVolumeLevel(volumeButton.getContext());
            volumeButton.setImageResource(getIconId(currentVolumeLevel));
        }
    }

    private float getCurrentVolumeLevel(final Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        return currentVolume / maxVolume;
    }

    private int getIconId(final float currentVolumeLevel) {
        int resourceId;
        if (currentVolumeLevel > HIGH_LEVEL_THRESHOLD) {
            resourceId = R.drawable.ic_volume_bar_max;
        } else if (currentVolumeLevel > MEDIUM_LEVEL_THRESHOLD) {
            resourceId = R.drawable.ic_volume_bar_medium;
        } else if (currentVolumeLevel > LOW_LEVEL_THRESHOLD) {
            resourceId = R.drawable.ic_volume_bar_low;
        } else {
            resourceId = R.drawable.ic_volume_bar_mute;
        }
        return resourceId;
    }

}
