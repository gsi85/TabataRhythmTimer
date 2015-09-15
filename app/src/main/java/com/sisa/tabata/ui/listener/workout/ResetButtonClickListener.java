package com.sisa.tabata.ui.listener.workout;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

import android.view.View;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Reset button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class ResetButtonClickListener implements View.OnClickListener {

    private static final int DISPLAY_DURATION_MILLIS = 2000;
    private static final int NOTIFICATION_HOLD_TO_RESET = R.string.notification_hold_to_reset;

    @InjectView(R.id.workoutNotificationView)
    private TextView notificationView;
    @Inject
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public void onClick(View view) {
        String notificationText = applicationContextProvider.getStringResource(NOTIFICATION_HOLD_TO_RESET);
        new NotificationDisplayTimer(notificationView, notificationText, DISPLAY_DURATION_MILLIS).start();
    }

}
