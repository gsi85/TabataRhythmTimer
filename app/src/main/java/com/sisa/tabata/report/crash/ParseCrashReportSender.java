package com.sisa.tabata.report.crash;

import java.util.HashMap;
import java.util.Map;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.content.Context;

import com.google.inject.Singleton;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

/**
 * Sends crash report to Parse.
 *
 * @author Laszlo_Sisa
 */
@Singleton
public class ParseCrashReportSender implements ReportSender {

    private static final String CRASH_REPORT_CLASS_NAME = "TabataCrashReport";
    private static final String APPLICATION_CRASH_EVENT = "ApplicationCrash";

    @Override
    public void send(final Context context, final CrashReportData errorContent) throws ReportSenderException {
        ParseObject parseObject = createParseObject();
        Map<String, String> errorMap = createErrorMap();
        populateErrorData(errorContent, parseObject, errorMap);
        sendParseReport(parseObject, errorMap);
    }

    private ParseObject createParseObject() {
        return new ParseObject(CRASH_REPORT_CLASS_NAME);
    }

    private Map<String, String> createErrorMap() {
        return new HashMap<>();
    }

    private void populateErrorData(final CrashReportData errorContent, final ParseObject parseObject, final Map<String, String> errorMap) {
        for (Map.Entry<ReportField, String> errorEntry : errorContent.entrySet()) {
            String key = errorEntry.getKey().name();
            String value = errorEntry.getValue();
            parseObject.put(key, value);
            errorMap.put(key, value);
        }
    }

    private void sendParseReport(final ParseObject parseObject, final Map<String, String> errorMap) {
        ParseAnalytics.trackEventInBackground(APPLICATION_CRASH_EVENT, errorMap);
        parseObject.saveInBackground();
    }

}
