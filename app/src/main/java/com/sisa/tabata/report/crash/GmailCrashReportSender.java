package com.sisa.tabata.report.crash;

import java.util.Map;

import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import com.google.inject.Singleton;
import com.sisa.tabata.email.sender.GmailSender;

import android.content.Context;
import android.util.Log;

/**
 * Send crash report via Google Mail API.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class GmailCrashReportSender implements ReportSender {

    private final GmailSender gmailSender;

    public GmailCrashReportSender() {
        //TODO refactor
        gmailSender = new GmailSender("tabatarhythmtimer@gmail.com", "password");
    }

    @Override
    public void send(final Context context, final CrashReportData errorContent) throws ReportSenderException {
        String messageBody = buildMessageBody(errorContent);
        sendMail(messageBody);
    }

    private String buildMessageBody(final CrashReportData errorContent) {
        StringBuilder errorMessageReportBuilder = new StringBuilder();
        for (Map.Entry entry : errorContent.entrySet()) {
            errorMessageReportBuilder.append(String.format("%s:%s\\r\\n", entry.getKey(), entry.getValue()));
        }
        return errorMessageReportBuilder.toString();
    }

    private void sendMail(final String messageBody) {
        try {
            gmailSender.sendMail("Crash report", messageBody, "tabatarhythmtimer@gmail.com", "tabatarhythmtimer@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

}
