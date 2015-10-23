package com.sisa.tabata;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import com.google.inject.Inject;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.sisa.tabata.dao.loader.WorkoutManager;
import com.sisa.tabata.media.service.SelectedMusicService;
import com.sisa.tabata.media.service.SelectedMusicValidationService;
import com.sisa.tabata.preferences.PreferencesType;
import com.sisa.tabata.report.crash.EmailCrashReportSender;
import com.sisa.tabata.report.crash.ParseCrashReportSender;

import android.app.Application;
import android.content.SharedPreferences;

import roboguice.RoboGuice;

/**
 * Base class of application to maintain global state.
 *
 * @author Laszlo Sisa
 */
@ReportsCrashes(mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class TabataApplication extends Application {

    private static final String PREFERENCES_FILE_NAME = "TabataPreferences";
    private static final long NO_LOADED_WORKOUT_ID = -1;

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private EmailCrashReportSender emailCrashReportSender;
    @Inject
    private ParseCrashReportSender parseCrashReportSender;
    @Inject
    private SelectedMusicService selectedMusicService;
    @Inject
    private WorkoutManager workoutManager;

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.injectMembers(getApplicationContext(), this);
        setUpParse();
        setUpCrashReport();
        validateSelectedSongs();
        setLoadedWorkout();
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

    private void validateSelectedSongs() {
        new SelectedMusicValidationService(selectedMusicService, applicationContextProvider).execute();
    }

    private void setLoadedWorkout() {
        if (getPreviouslyLoadedWorkoutId() == NO_LOADED_WORKOUT_ID) {
            workoutManager.setLoadedWorkoutById(0);
        }
    }

    //TODO: move this a common preference manager.
    private long getPreviouslyLoadedWorkoutId() {
        SharedPreferences settings = applicationContextProvider.getContext().getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        return settings.getLong(PreferencesType.LOADED_WORKOUT_ID.name(), NO_LOADED_WORKOUT_ID);
    }

}
