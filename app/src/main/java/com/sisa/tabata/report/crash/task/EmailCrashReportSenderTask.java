package com.sisa.tabata.report.crash.task;

import com.google.inject.Singleton;
import com.sisa.tabata.report.crash.email.EmailSender;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Asynchronous task for sending emails.
 *
 * @author Laszlo_Sisa
 */
@Singleton
public class EmailCrashReportSenderTask extends AsyncTask<String, Void, Void> {

    private static final int CRASH_REPORT_PARAM_INDEX = 0;

    private final EmailSender emailSender;
    private final ParseAnalyticsAdapter parseAnalyticsAdapter;

    /**
     * DI constructor.
     *
     * @param emailSender {@link EmailSender}
     * @param parseAnalyticsAdapter {@link ParseAnalyticsAdapter}
     */
    public EmailCrashReportSenderTask(final EmailSender emailSender, final ParseAnalyticsAdapter parseAnalyticsAdapter) {
        this.emailSender = emailSender;
        this.parseAnalyticsAdapter = parseAnalyticsAdapter;
    }

    @Override
    protected Void doInBackground(final String... params) {
        try {
            String crashReport = params[CRASH_REPORT_PARAM_INDEX];
            emailSender.sendMail(crashReport);
        } catch (Exception e) {
            parseAnalyticsAdapter.trackException(e);
            Log.e("SendMail", e.getMessage(), e);
        }
        return null;
    }
}
