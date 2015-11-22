package com.sisa.tabata.socialshare.facebook.callback;

import java.util.HashMap;
import java.util.Map;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.report.AnalyticsDimensions;
import com.sisa.tabata.report.TrackingEvents;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;

/**
 * Facebook analytics callback.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class FacebookAnalyticsCallback implements FacebookCallback<Sharer.Result> {

    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;

    @Override
    public void onSuccess(final Sharer.Result result) {
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put(AnalyticsDimensions.POST_ID.getName(), result.getPostId());
        parseAnalyticsAdapter.trackEvent(TrackingEvents.FACEBOOK_SHARE_SUCCESS, dimensions);
    }

    @Override
    public void onCancel() {
        parseAnalyticsAdapter.trackEvent(TrackingEvents.FACEBOOK_SHARE_CANCELLED, null);
    }

    @Override
    public void onError(final FacebookException error) {
        Map<String, String> dimensions = new HashMap<>();
        dimensions.put(AnalyticsDimensions.FACEBOOK_ERROR.getName(), error.getMessage());
        parseAnalyticsAdapter.trackEvent(TrackingEvents.FACEBOOK_SHARE_ERROR, dimensions);
    }
}
