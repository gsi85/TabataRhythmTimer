package com.sisa.tabata.ui.listener.workout;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

import android.content.Intent;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Back button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class BackButtonClickCountListener {

    private static final int NOTIFICATION_TIME_IN_MILLIS = 2000;
    private static final int NOTIFICATION_PRESS_AGAIN_TO_EXIT = R.string.notification_press_again_to_exit;

    @InjectView(R.id.workoutNotificationView)
    private TextView workoutNotificationView;
    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;
    private int backButtonClickCount;

    /**
     * DI constructor.
     *
     * @param workoutActivity {@link WorkoutActivity}
     */
    public void onClick(WorkoutActivity workoutActivity) {
        if (isPressedTwice()) {
            exitApp(workoutActivity);
        } else {
            showFirstPressNotification();
        }
    }

    /**
     * Resets back button click count.
     * Every button' onClick event placed on the {@link WorkoutActivity} should call this.
     */
    public void resetClickCount() {
        backButtonClickCount = 0;
    }

    private boolean isPressedTwice() {
        return backButtonClickCount >= 1;
    }

    private void exitApp(WorkoutActivity workoutActivity) {
        parseAnalyticsAdapter.trackAppClosedEvent();
        backButtonClickCount = 0;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        workoutActivity.startActivity(intent);
    }

    private void showFirstPressNotification() {
        String notificationString = applicationContextProvider.getStringResource(NOTIFICATION_PRESS_AGAIN_TO_EXIT);
        new NotificationDisplayTimer(workoutNotificationView, notificationString, NOTIFICATION_TIME_IN_MILLIS).start();
        backButtonClickCount++;
    }

}
