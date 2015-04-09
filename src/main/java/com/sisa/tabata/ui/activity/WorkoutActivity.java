package com.sisa.tabata.ui.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.adapter.SpinnerArrayAdapterFactory;
import com.sisa.tabata.ui.listener.workout.BackButtonClickListener;
import com.sisa.tabata.ui.listener.workout.PlayButtonClickListener;
import com.sisa.tabata.ui.listener.workout.ResetButtonClickListener;
import com.sisa.tabata.ui.listener.workout.ResetButtonLongClickListener;
import com.sisa.tabata.ui.listener.workout.TimerLayoutListener;
import com.sisa.tabata.ui.listener.workout.VolumeButtonClickListener;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

public class WorkoutActivity extends RoboFragmentActivity {

    @InjectView(R.id.timerLayout)
    private RelativeLayout timerLayout;
    @InjectView(R.id.playButton)
    private ImageButton playButton;
    @InjectView(R.id.resetButton)
    private ImageButton resetButton;

    @InjectView(R.id.mainMenuSpinner)
    private Spinner mainMenu;
    @InjectView(R.id.volumeButton)
    private ImageButton volumeButton;

    @Inject
    private PlayButtonClickListener playButtonClickListener;
    @Inject
    private TimerLayoutListener timerLayoutListener;
    @Inject
    private ResetButtonClickListener resetButtonClickListener;
    @Inject
    private ResetButtonLongClickListener resetButtonLongClickListener;
    @Inject
    private SpinnerArrayAdapterFactory spinnerArrayAdapterFactory;
    @Inject
    private BackButtonClickListener backButtonClickListener;
    @Inject
    private VolumeButtonClickListener volumeButtonClickListener;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setUpMainMenu();
        setUpListeners();
        initProgressBars();
    }

    @Override
    public void onBackPressed() {
        backButtonClickListener.onClick(this);
    }

    private void setUpMainMenu() {
        mainMenu.setAdapter(spinnerArrayAdapterFactory.create(this));
        mainMenu.setSelection(0);
    }

    private void setUpListeners() {
        timerLayout.getViewTreeObserver().addOnGlobalLayoutListener(timerLayoutListener);
        playButton.setOnClickListener(playButtonClickListener);
        resetButton.setOnClickListener(resetButtonClickListener);
        resetButton.setOnLongClickListener(resetButtonLongClickListener);
        volumeButton.setOnClickListener(volumeButtonClickListener);
    }

    private void initProgressBars() {
        playButtonClickListener.resetWorkout();
    }

    public PlayButtonClickListener getPlayButtonClickListener() {
        return playButtonClickListener;
    }

}
