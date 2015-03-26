package com.sisa.tabata.ui.dialog;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.listener.workout.VolumeSeekBarChangeListener;

import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.25..
 */
public class VolumeControlDialog extends RoboDialogFragment {

    @InjectView(R.id.volumeSeekBar)
    private SeekBar volumeSeekBar;
    @Inject
    private VolumeSeekBarChangeListener volumeSeekBarChangeListener;
    private AudioManager audioManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_volume_control, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpAudioManager();
        removeTitle();
        setUpSeekBar();
        setUpViewDependencies();
        setUpListeners();
    }

    private void setUpAudioManager() {
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    private void removeTitle() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    private void setUpSeekBar() {
        volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    private void setUpViewDependencies() {
        volumeSeekBarChangeListener.setAudioManager(audioManager);
    }

    private void setUpListeners() {
        volumeSeekBar.setOnSeekBarChangeListener(volumeSeekBarChangeListener);
    }

}
