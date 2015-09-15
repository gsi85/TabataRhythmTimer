package com.sisa.tabata.ui.listener.workout;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.domain.Size;
import com.sisa.tabata.ui.drawable.CircularProgressBarDrawable;

/**
 * Timer layout listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class TimerLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

    @InjectView(R.id.timerLayout)
    private RelativeLayout timerLayout;
    @Inject
    private CircularProgressBarDrawable circularProgressBarDrawable;

    @Override
    public void onGlobalLayout() {
        circularProgressBarDrawable.setContainerSize(new Size(timerLayout.getWidth(), timerLayout.getHeight()));
        timerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        timerLayout.setBackgroundDrawable(circularProgressBarDrawable);
    }

}
