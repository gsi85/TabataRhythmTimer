package com.sisa.tabata.ui.listener.music;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import com.sisa.tabata.ui.activity.MusicSelectActivity;
import com.sisa.tabata.ui.activity.WorkoutActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * Music select action button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class MusicSelectActionButtonClickListener implements View.OnClickListener {

    private static final String SAVE_ACTION = "save_action";

    @Override
    public void onClick(final View view) {
        MusicSelectActivity musicSelectActivity = getCheckedContext(view);
        Intent workoutIntent = createIntent(musicSelectActivity);
        musicSelectActivity.startActivity(workoutIntent);
        musicSelectActivity.finish();
    }

    @NonNull
    private Intent createIntent(final MusicSelectActivity musicSelectActivity) {
        return new Intent(musicSelectActivity, WorkoutActivity.class);
    }

    private MusicSelectActivity getCheckedContext(final View view) {
        isInstanceOf(MusicSelectActivity.class, view.getContext(), "View context is not a MusicSelectActivity");
        return (MusicSelectActivity) view.getContext();
    }
}
