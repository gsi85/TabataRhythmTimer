package com.sisa.tabata.ui.listener.workout;

import android.view.View;
import android.widget.TextView;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;
import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.02.28..
 */
@ContextSingleton
public class ResetButtonClickListener implements View.OnClickListener {

    private static final int DISPLAY_DURATION_MILLIS = 2000;

    @InjectView(R.id.workoutNotificationView)
    private TextView notificationView;

    @Override
    public void onClick(View view) {
        String notificationText = TabataApplication.getAppContext().getString(R.string.notification_hold_to_reset);
        new NotificationDisplayTimer(notificationView, notificationText, DISPLAY_DURATION_MILLIS).start();
    }

}
