package com.sisa.tabata.report.crash;

import java.util.Map;

import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.SessionIdProvider;
import com.sisa.tabata.report.crash.task.EmailCrashReportSenderTask;

import android.content.Context;

/**
 * Send crash report via email using SMTP.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class EmailCrashReportSender implements ReportSender {

    private static final String SESSION_ID_KEY = "SESSION_ID";

    @Inject
    private EmailCrashReportSenderTask emailCrashReportSenderTask;
    @Inject
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public void send(final Context context, final CrashReportData errorContent) throws ReportSenderException {
        String messageBody = buildMessageBody(errorContent);
        sendMail(messageBody);
    }

    private String buildMessageBody(final CrashReportData errorContent) {
        String crashReportBodyPattern = applicationContextProvider.getStringResource(R.string.crash_report_body_pattern);
        String crashReportTableRows = buildTableRows(errorContent);
        return String.format(crashReportBodyPattern, crashReportTableRows);
    }

    private String buildTableRows(final CrashReportData errorContent) {
        String tableRowPattern = applicationContextProvider.getStringResource(R.string.crash_report_table_row_pattern);
        StringBuilder tableRowsBuilder = new StringBuilder();
        tableRowsBuilder.append(String.format(tableRowPattern, SESSION_ID_KEY, SessionIdProvider.SESSION_ID));
        for (Map.Entry errorEntry : errorContent.entrySet()) {
            tableRowsBuilder.append(String.format(tableRowPattern, errorEntry.getKey(), errorEntry.getValue()));
        }
        return tableRowsBuilder.toString();
    }

    private void sendMail(final String messageBody) {
        emailCrashReportSenderTask.execute(messageBody);
    }

}
