package com.sisa.tabata.ui.timer;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * Notification display timer.
 *
 * @author Laszlo Sisa
 */
public class NotificationDisplayTimer extends CountDownTimer {

    private final TextView notificationView;

    /**
     * DI constructor.
     *
     * @param notificationView {@link TextView} where the notification is displayed
     * @param textToDisplay text to display
     * @param durationMillis notifications duration in milli seconds
     */
    public NotificationDisplayTimer(TextView notificationView, String textToDisplay, long durationMillis) {
        super(durationMillis, durationMillis);
        this.notificationView = notificationView;
        this.notificationView.setText(textToDisplay);
        notificationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        notificationView.setVisibility(View.GONE);
    }
}
