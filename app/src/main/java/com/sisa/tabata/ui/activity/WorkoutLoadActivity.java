package com.sisa.tabata.ui.activity;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewClickListener;
import com.sisa.tabata.ui.listener.loader.WorkoutTextViewLongClickListener;
import com.sisa.tabata.ui.provider.loader.WorkoutListTextViewProvider;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import roboguice.inject.InjectView;

/**
 * Workout load activity.
 *
 * @author Laszlo Sisa
 */
public class WorkoutLoadActivity extends BaseActivity {

    @InjectView(R.id.existingWorkoutLayout)
    private LinearLayout existingWorkoutLayout;
    @InjectView(R.id.workoutLoadNotificationView)
    private TextView workoutLoadNotificationView;
    @InjectView(R.id.cancelLoadButton)
    private ImageButton cancelButton;

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
        createWorkoutList();
        setUpCancelButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String notificationText = getString(R.string.notification_long_press_to_delete_workout);
        new NotificationDisplayTimer(workoutLoadNotificationView, notificationText, getResources().getInteger(R.integer.long_notification_duration))
                .start();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, WorkoutActivity.class));
        finish();
    }

    private void createWorkoutList() {
        workoutListTextViewProvider.createWorkoutTextViews(this, getApplicationContext(), existingWorkoutLayout);
    }

    private void setUpCancelButton() {
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });
    }
}
