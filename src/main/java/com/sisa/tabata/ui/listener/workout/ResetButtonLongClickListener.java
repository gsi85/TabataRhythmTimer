package com.sisa.tabata.ui.listener.workout;

import android.view.View;
import android.widget.ImageButton;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.28..
 */
@Singleton
public class ResetButtonLongClickListener implements View.OnLongClickListener {

    @Inject
    private PlayButtonClickListener playButtonClickListener;
    private ImageButton playButton;

    public ResetButtonLongClickListener(){
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public boolean onLongClick(View view) {
        playButtonClickListener.resetWorkout(playButton);
        return true;
    }

    public void setPlayButton(ImageButton playButton) {
        this.playButton = playButton;
    }
}
