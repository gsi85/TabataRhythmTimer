package com.sisa.tabata.ui.listener.workout;

import android.content.Context;
import android.media.AudioManager;
import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * Volume button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class VolumeButtonClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        AudioManager audio = (AudioManager) view.getContext().getSystemService(Context.AUDIO_SERVICE);
        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
    }

}
