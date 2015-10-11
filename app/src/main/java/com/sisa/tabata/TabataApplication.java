package com.sisa.tabata;

import android.app.Application;
import com.google.inject.Inject;
import com.sisa.tabata.report.crash.GmailCrashReportSender;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import roboguice.RoboGuice;

/**
 * Base class of application to maintain global state.
 *
 * @author Laszlo Sisa
 */
@ReportsCrashes
public class TabataApplication extends Application {

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private GmailCrashReportSender gmailCrashReportSender;

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.injectMembers(getApplicationContext(), this);
        applicationContextProvider.setContext(getApplicationContext());
        setUpCrashReport();
    }

    private void setUpCrashReport() {
        ACRA.init(this);
        ACRA.getErrorReporter().setReportSender(gmailCrashReportSender);
    }

}
