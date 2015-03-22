package com.sisa.tabata.ui.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.adapter.SpinnerArrayAdapter;
import com.sisa.tabata.ui.listener.workout.MainMenuOnSelectedListener;
import com.sisa.tabata.ui.listener.workout.PlayButtonClickListener;
import com.sisa.tabata.ui.listener.workout.ResetButtonClickListener;
import com.sisa.tabata.ui.listener.workout.ResetButtonLongClickListener;
import com.sisa.tabata.ui.listener.workout.TimerLayoutListener;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class WorkoutActivity extends RoboActivity {

    @InjectView(R.id.timerLayout)
    private RelativeLayout timerLayout;
    @InjectView(R.id.playButton)
    private ImageButton playButton;
    @InjectView(R.id.resetButton)
    private ImageButton resetButton;
    @InjectView(R.id.totalRemainingTimeCounter)
    private TextView totalRemainingTimeCounter;
    @InjectView(R.id.workoutProgressBar)
    private ProgressBar workoutProgressBar;
    @InjectView(R.id.currentBlockCounter)
    private TextView currentBlockCounter;
    @InjectView(R.id.roundCounter)
    private TextView roundCounter;
    @InjectView(R.id.sectionCounter)
    private TextView sectionCounter;
    @InjectView(R.id.workoutTypeText)
    private TextView workoutTypeText;
    @InjectView(R.id.workoutSummaryText)
    private TextView workoutSummaryText;
    @InjectView(R.id.mainMenuSpinner)
    private Spinner mainMenu;
    @Inject
    private PlayButtonClickListener playButtonClickListener;
    @Inject
    private TimerLayoutListener timerLayoutListener;
    @Inject
    private ResetButtonClickListener resetButtonClickListener;
    @Inject
    private ResetButtonLongClickListener resetButtonLongClickListener;
    @Inject
    private CurrentRoundProgressBar currentRoundProgressBar;
    @Inject
    private TotalWorkoutProgressBar totalWorkoutProgressBar;
    @Inject
    private MainMenuOnSelectedListener menuSpinnerItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setUpMainMenu();
        setUpListeners();
        setUpViewDependencies();
        initProgressBars();
    }

    private void setUpListeners() {
        timerLayout.getViewTreeObserver().addOnGlobalLayoutListener(timerLayoutListener);
        playButtonClickListener.setPlayButton(playButton);
        playButton.setOnClickListener(playButtonClickListener);
        resetButton.setOnClickListener(resetButtonClickListener);
        resetButton.setOnLongClickListener(resetButtonLongClickListener);
    }

    private void setUpViewDependencies() {
        totalWorkoutProgressBar.setTotalRemainingTimeCounter(totalRemainingTimeCounter);
        totalWorkoutProgressBar.setWorkoutProgressBar(workoutProgressBar);
        totalWorkoutProgressBar.setWorkoutSummaryText(workoutSummaryText);
        currentRoundProgressBar.setCurrentBlockCounter(currentBlockCounter);
        currentRoundProgressBar.setRoundCounter(roundCounter);
        currentRoundProgressBar.setSectionCounter(sectionCounter);
        currentRoundProgressBar.setWorkoutTypeText(workoutTypeText);
        timerLayoutListener.setTimerLayout(timerLayout);
    }

    private void initProgressBars() {
        playButtonClickListener.resetWorkout();
    }

    private void setUpMainMenu() {
        CharSequence titleText = workoutSummaryText.getText();
        menuSpinnerItemSelectedListener.setWorkoutActivity(this);
        String[] menuItems = getResources().getStringArray(R.array.main_menu_items);
        SpinnerArrayAdapter<String> adapter = new SpinnerArrayAdapter<>(this, menuItems, titleText);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        mainMenu.setAdapter(adapter);
        mainMenu.setOnItemSelectedListener(menuSpinnerItemSelectedListener);
    }

}
