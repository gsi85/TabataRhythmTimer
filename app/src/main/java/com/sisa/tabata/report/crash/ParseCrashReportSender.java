package com.sisa.tabata.report.crash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.parse.ParseObject;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;

import android.content.Context;

/**
 * Sends crash report to Parse.
 *
 * @author Laszlo_Sisa
 */
@Singleton
public class ParseCrashReportSender implements ReportSender {

    private static final String CRASH_REPORT_CLASS_NAME = "CrashReport";
    private static final List<ReportField> CRASH_EVENT_PROPERTIES = Arrays.asList(ReportField.REPORT_ID, ReportField.PHONE_MODEL, ReportField.BRAND);

    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;

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
            String keyAsString = errorEntry.getKey().name();
            String value = errorEntry.getValue();
            parseObject.put(keyAsString, value);
            if (CRASH_EVENT_PROPERTIES.contains(errorEntry.getKey())) {
                errorMap.put(keyAsString, value);
            }
        }

    }

    private void sendParseReport(final ParseObject parseObject, final Map<String, String> errorMap) {
        parseAnalyticsAdapter.trackAppCrashedEvent(errorMap);
        parseObject.saveInBackground();
    }

}
