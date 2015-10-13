package com.sisa.tabata;

import android.app.Application;
import com.google.inject.Inject;
import com.sisa.tabata.report.crash.EmailCrashReportSender;
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
    private EmailCrashReportSender emailCrashReportSender;

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.injectMembers(getApplicationContext(), this);
        setUpCrashReport();
    }

    private void setUpCrashReport() {
        ACRA.init(this);
        ACRA.getErrorReporter().setReportSender(emailCrashReportSender);
    }

}
