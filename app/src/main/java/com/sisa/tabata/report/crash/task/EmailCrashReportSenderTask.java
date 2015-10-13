package com.sisa.tabata.report.crash.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.report.crash.email.EmailSender;

/**
 * Asynchronous task for sending emails.
 *
 * @author Laszlo_Sisa
 */
@Singleton
public class EmailCrashReportSenderTask extends AsyncTask<String, Void, Void> {

    private static final int CRASH_REPORT_PARAM_INDEX = 0;

    @Inject
    private EmailSender emailSender;

    @Override
    protected Void doInBackground(final String... params) {
        try {
            String crashReport = params[CRASH_REPORT_PARAM_INDEX];
            emailSender.sendMail(crashReport);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        return null;
    }
}
