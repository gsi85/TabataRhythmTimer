package com.sisa.tabata.ui.listener.workout;

import android.content.Intent;
import android.widget.TextView;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;
import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.03.23..
 */
@ContextSingleton
public class BackButtonClickListener {

    private static final int NOTIFICATION_TIME_IN_MILLIS = 2000;
    @InjectView(R.id.workoutNotificationView)
    private TextView workoutNotificationView;
    private int backButtonCount;

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
        String notificationString = TabataApplication.getAppContext().getString(R.string.notification_press_again_to_exit);
        new NotificationDisplayTimer(workoutNotificationView, notificationString, NOTIFICATION_TIME_IN_MILLIS).start();
        backButtonCount++;
    }

}
