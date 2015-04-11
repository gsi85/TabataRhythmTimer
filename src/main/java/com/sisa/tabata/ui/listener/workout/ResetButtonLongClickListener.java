package com.sisa.tabata.ui.listener.workout;

import android.view.View;
import com.sisa.tabata.ui.activity.WorkoutActivity;
import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.02.28..
 */
@ContextSingleton
public class ResetButtonLongClickListener implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View view) {
        getCheckedPlayButtonListener(view).resetWorkout();
        return true;
    }

    private PlayButtonClickListener getCheckedPlayButtonListener(final View view) {
        //TODO: replace with Assert
        if(!(view.getContext() instanceof WorkoutActivity)){
            throw new IllegalArgumentException("View context is not a WorkoutActivity");
        }
        return ((WorkoutActivity) view.getContext()).getPlayButtonClickListener();
    }

}
