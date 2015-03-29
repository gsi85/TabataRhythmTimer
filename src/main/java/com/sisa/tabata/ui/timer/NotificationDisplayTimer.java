package com.sisa.tabata.ui.timer;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.sisa.tabata.util.DropDownAnim;

/**
 * Created by Laca on 2015.03.29..
 */
public class NotificationDisplayTimer extends CountDownTimer {

    private final TextView notificationView;

    public NotificationDisplayTimer(TextView notificationView, String textToDisplay, long durationMillis) {
        super(durationMillis, durationMillis);
        this.notificationView = notificationView;
        this.notificationView.setText(textToDisplay);
        DropDownAnim.expand(notificationView);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        DropDownAnim.collapse(notificationView);
    }
}
