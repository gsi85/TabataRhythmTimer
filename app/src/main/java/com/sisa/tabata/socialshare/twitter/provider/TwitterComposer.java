package com.sisa.tabata.socialshare.twitter.provider;

import com.sisa.tabata.domain.Workout;

import android.content.Context;

/**
 * Composer for Tweeter tweets.
 *
 * @author Laszlo Sisa
 */
public interface TwitterComposer {

    /**
     * Composes a tweet about a finished workout.
     *
     * @param activity {@link Context}
     * @param workout {@link Workout}
     */
    void composeWorkoutFinishedTweet(final Context activity, final Workout workout);

    /**
     * Composes a generic tweet about the application.
     *
     * @param activity {@link Context}
     */
    void composeGenericTweet(final Context activity);

}
