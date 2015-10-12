package com.sisa.tabata.report.crash;

import com.sisa.tabata.report.crash.email.GmailSender;
import java.util.Map;

import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;

import android.content.Context;
import android.util.Log;

/**
 * Send crash report via Google Mail API.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class GmailCrashReportSender implements ReportSender {

    @Inject
    private GmailSender gmailSender;
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
        for (Map.Entry entry : errorContent.entrySet()) {
            tableRowsBuilder.append(String.format(tableRowPattern, entry.getKey(), entry.getValue()));
        }
        return tableRowsBuilder.toString();
    }

    private void sendMail(final String messageBody) {
        try {
            gmailSender.sendMail(messageBody);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

}
