package com.sisa.tabata.ui.listener.workout;

import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.domain.Size;
import com.sisa.tabata.ui.drawable.CircularProgressBarDrawable;
import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.02.23..
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
