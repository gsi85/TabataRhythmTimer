package com.sisa.tabata.ui.listener.workout;

import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.domain.Size;
import com.sisa.tabata.ui.drawable.CircularProgressBarDrawable;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.23..
 */
@Singleton
public class TimerLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private RelativeLayout timerLayout;
    @Inject
    private CircularProgressBarDrawable circularProgressBarDrawable;

    public TimerLayoutListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onGlobalLayout() {
        circularProgressBarDrawable.setContainerSize(new Size(timerLayout.getWidth(), (timerLayout.getHeight())));
        timerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        timerLayout.setBackgroundDrawable(circularProgressBarDrawable);
    }

    public void setTimerLayout(RelativeLayout timerLayout) {
        this.timerLayout = timerLayout;
    }
}
