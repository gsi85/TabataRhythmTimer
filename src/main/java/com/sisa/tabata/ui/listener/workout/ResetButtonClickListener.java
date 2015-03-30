package com.sisa.tabata.ui.listener.workout;

import android.view.View;
import android.widget.TextView;

import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;

/**
 * Created by Laca on 2015.02.28..
 */
@Singleton
public class ResetButtonClickListener implements View.OnClickListener {

    private static final int DISPLAY_DURATION_MILLIS = 2000;
    private TextView notificationView;

    @Override
    public void onClick(View view) {
        String notificationText = TabataApplication.getAppContext().getString(R.string.notification_hold_to_reset);
        new NotificationDisplayTimer(notificationView, notificationText, DISPLAY_DURATION_MILLIS).start();
    }

    public void setNotificationView(TextView notificationView) {
        this.notificationView = notificationView;
    }
}
