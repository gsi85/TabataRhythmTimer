package com.sisa.tabata.ui.listener.workout;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import com.google.inject.Inject;
import com.sisa.tabata.media.service.MediaPlayerService;
import com.sisa.tabata.ui.activity.WorkoutActivity;

import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * Reset button long click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class ResetButtonLongClickListener extends AbstractWorkoutActivityButtonClickListener implements View.OnLongClickListener {

    @Inject
    private MediaPlayerService mediaPlayerService;

    @Override
    public boolean onLongClick(View view) {
        super.onClick(view);
        getCheckedPlayButtonListener(view).resetWorkout();
        mediaPlayerService.reset();
        return true;
    }

    private PlayButtonClickListener getCheckedPlayButtonListener(final View view) {
        isInstanceOf(WorkoutActivity.class, view.getContext(), "View context is not a WorkoutActivity");
        return ((WorkoutActivity) view.getContext()).getPlayButtonClickListener();
    }

}
