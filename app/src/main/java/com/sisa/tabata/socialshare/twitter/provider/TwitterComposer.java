package com.sisa.tabata.socialshare.twitter.provider;

import static java.lang.String.format;

import android.content.Context;
import android.net.Uri;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

/**
 * Composer for Tweeter tweets.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class TwitterComposer {

    private static final String NEW_LINE = "\n";
    private static final String INTERNAL_RESOURCE_PATTERN = "android.resource://%s/%s";

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Composes a tweet about a finished workout.
     *
     * @param activity {@link Context}
     * @param workout {@link Workout}
     */
    public void composeWorkoutFinishedTweet(final Context activity, final Workout workout) {
        new TweetComposer.Builder(activity)
                .text(buildWorkoutTweetText(workout))
                .image(buildFeatureGraphicsImage())
                .show();

    }

    private String buildWorkoutTweetText(final Workout workout) {
        return getHeadline(workout) + NEW_LINE + getString(R.string.app_play_url_short);
    }

    private String getHeadline(final Workout workout) {
        return format(getString(R.string.workout_finished_tweet), workout.getName());
    }

    private Uri buildFeatureGraphicsImage() {
        return Uri.parse(format(INTERNAL_RESOURCE_PATTERN, getString(R.string.package_name), R.drawable.feature_grapchics));
    }

    private String getString(final int id) {
        return applicationContextProvider.getStringResource(id);
    }

}
