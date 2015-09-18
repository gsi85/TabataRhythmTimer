package com.sisa.tabata.ui.listener.workout;

import com.google.inject.Inject;
import com.sisa.tabata.ui.activity.WorkoutActivity;

import android.view.View;

/**
 * Abstract class used click listener for buttons placed on {@link WorkoutActivity}.
 *
 * @author Laca
 */
public abstract class AbstractWorkoutActivityButtonClickListener implements View.OnClickListener {

    @Inject
    private BackButtonClickCountListener backButtonClickCountListener;

    @Override
    public void onClick(View view) {
        backButtonClickCountListener.resetClickCount();
    }

}
