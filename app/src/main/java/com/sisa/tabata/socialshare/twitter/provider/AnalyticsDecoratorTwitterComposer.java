package com.sisa.tabata.socialshare.twitter.provider;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.report.domain.TrackingEvents;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;

import android.content.Context;

/**
 * Decorator for sending report on Tweeter events.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class AnalyticsDecoratorTwitterComposer implements TwitterComposer {

    @Inject
    private DefaultTwitterComposer twitterComposer;
    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;

    @Override
    public void composeWorkoutFinishedTweet(final Context activity, final Workout workout) {
        parseAnalyticsAdapter.trackEvent(TrackingEvents.TWITTER_WORKOUT_TWEET_OPENED, null);
        twitterComposer.composeWorkoutFinishedTweet(activity, workout);
    }

    @Override
    public void composeGenericTweet(final Context activity) {
        parseAnalyticsAdapter.trackEvent(TrackingEvents.TWITTER_GENERIC_TWEET_OPENED, null);
        twitterComposer.composeGenericTweet(activity);
    }
}
