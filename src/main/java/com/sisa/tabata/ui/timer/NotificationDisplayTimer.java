package com.sisa.tabata.ui.timer;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Laca on 2015.03.29..
 */
public class NotificationDisplayTimer extends CountDownTimer {

    private final TextView notificationView;

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
