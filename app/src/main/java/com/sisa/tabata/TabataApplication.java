package com.sisa.tabata;

import static roboguice.RoboGuice.injectMembers;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import com.facebook.FacebookSdk;
import com.google.inject.Inject;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.sisa.tabata.dao.loader.WorkoutManager;
import com.sisa.tabata.media.service.SelectedMusicService;
import com.sisa.tabata.media.service.SelectedMusicValidationService;
import com.sisa.tabata.preferences.DefaultPreferenceValuesProvider;
import com.sisa.tabata.preferences.PreferenceKeys;
import com.sisa.tabata.preferences.PreferencesSource;
import com.sisa.tabata.report.TrackingEvents;
import com.sisa.tabata.report.crash.EmailCrashReportSender;
import com.sisa.tabata.report.crash.ParseCrashReportSender;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

import android.app.Application;

/**
 * Base class of application to maintain global state.
 *
 * @author Laszlo Sisa
 */
@ReportsCrashes(mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class TabataApplication extends Application {

    private static final int DEFAULT_WORKOUT_ID = 0;

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private EmailCrashReportSender emailCrashReportSender;
    @Inject
    private ParseCrashReportSender parseCrashReportSender;
    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;
    @Inject
    private SelectedMusicService selectedMusicService;
    @Inject
    private PreferencesSource preferencesSource;
    @Inject
    private WorkoutManager workoutManager;
    @Inject
    private DefaultPreferenceValuesProvider defaultPreferenceValuesProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        injectMembers(getApplicationContext(), this);
        setDefaultPreferences();
        setUpParse();
        setUpCrashReport();
        setUpFaceBook();
        setUpTwitter();
        validateSelectedSongs();
        performFirstTimeOpenedAction();
    }

    private void setDefaultPreferences() {
        defaultPreferenceValuesProvider.checkSetDefaultValues();
    }

    private void setUpParse() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_id));
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
    }

    private void setUpCrashReport() {
        ACRA.init(this);
        ACRA.getErrorReporter().addReportSender(emailCrashReportSender);
        ACRA.getErrorReporter().addReportSender(parseCrashReportSender);
    }

    private void setUpFaceBook() {
        FacebookSdk.sdkInitialize(this);
    }

    private void setUpTwitter() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key), getString(R.string.twitter_api_secret));
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
    }

    private void validateSelectedSongs() {
        new SelectedMusicValidationService(selectedMusicService, applicationContextProvider).execute();
    }

    private void performFirstTimeOpenedAction() {
        if (preferencesSource.is(PreferenceKeys.FIRST_TIME_OPENED)) {
            workoutManager.setLoadedWorkoutById(DEFAULT_WORKOUT_ID);
            parseAnalyticsAdapter.trackEvent(TrackingEvents.APP_OPENED_FIRST_TIME, null);
            preferencesSource.setBoolean(PreferenceKeys.FIRST_TIME_OPENED, false);
        }
    }

}
