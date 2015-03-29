package com.sisa.tabata.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewClickListener;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewLongClickListener;
import com.sisa.tabata.ui.provider.WorkoutListTextViewProvider;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.25..
 */
public class WorkoutLoadActivity extends RoboActivity {

    private static final int DISPLAY_TIME_IN_MILLIS = 3000;

    @InjectView(R.id.existingWorkoutLayout)
    private LinearLayout existingWorkoutLayout;
    @InjectView(R.id.workoutLoadNotificationView)
    private TextView workoutLoadNotificationView;

    @Inject
    private WorkoutTextViewClickListener workoutTextViewClickListener;
    @Inject
    private WorkoutTextViewLongClickListener workoutTextViewLongClickListener;

    @Inject
    private WorkoutListTextViewProvider workoutListTextViewProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_load);
        setUpViewDependencies();
        createWorkoutList();
    }

    @Override
    protected void onResume() {
        super.onStart();
        String notificationText = getString(R.string.notification_long_press_to_delete_workout);
        new NotificationDisplayTimer(workoutLoadNotificationView, notificationText, DISPLAY_TIME_IN_MILLIS).start();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, WorkoutActivity.class));
    }

    private void setUpViewDependencies() {
        workoutTextViewClickListener.setWorkoutLoadActivity(this);
        workoutTextViewLongClickListener.setExistingWorkoutLayout(existingWorkoutLayout);
        workoutTextViewLongClickListener.setWorkoutLoadNotificationView(workoutLoadNotificationView);
    }

    private void createWorkoutList() {
        workoutListTextViewProvider.createWorkoutTextViews(this, getApplicationContext(), existingWorkoutLayout);
    }
}
