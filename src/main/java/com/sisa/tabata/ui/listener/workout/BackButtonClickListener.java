package com.sisa.tabata.ui.listener.workout;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.widget.TextView;

import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

/**
 * Back button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class BackButtonClickListener {

    private static final int NOTIFICATION_TIME_IN_MILLIS = 2000;
    private static final int NOTIFICATION_PRESS_AGAIN_TO_EXIT = R.string.notification_press_again_to_exit;

    @InjectView(R.id.workoutNotificationView)
    private TextView workoutNotificationView;
    private int backButtonCount;

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

    private boolean isPressedTwice() {
        return backButtonCount >= 1;
    }

    private void exitApp(WorkoutActivity workoutActivity) {
        backButtonCount = 0;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        workoutActivity.startActivity(intent);
    }

    private void showFirstPressNotification() {
        String notificationString = TabataApplication.getAppContext().getString(NOTIFICATION_PRESS_AGAIN_TO_EXIT);
        new NotificationDisplayTimer(workoutNotificationView, notificationString, NOTIFICATION_TIME_IN_MILLIS).start();
        backButtonCount++;
    }

}
