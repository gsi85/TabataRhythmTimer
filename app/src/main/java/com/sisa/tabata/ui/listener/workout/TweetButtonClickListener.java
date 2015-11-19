package com.sisa.tabata.ui.listener.workout;

import com.google.inject.Inject;
import com.sisa.tabata.dao.loader.WorkoutManager;
import com.sisa.tabata.socialshare.twitter.provider.TwitterComposer;

import android.view.View;

import roboguice.inject.ContextSingleton;

/**
 * Tweet button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class TweetButtonClickListener implements View.OnClickListener {

    @Inject
    private TwitterComposer twitterComposer;
    @Inject
    private WorkoutManager workoutManager;

    @Override
    public void onClick(final View view) {
        twitterComposer.composeWorkoutFinishedTweet(view.getContext(), workoutManager.getLoadedWorkout());
    }
}
