package com.sisa.tabata.changelog.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;

import android.util.JsonReader;

/**
 * Json file based implementation of {@link ChangeLogDao}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class ChangeLogJsonDao implements ChangeLogDao {

    private static final String CHANGE_LOG_FILE_ENCODING = "UTF-8";

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private ParseAnalyticsAdapter parseAnalyticsAdapter;

    @Override
    public String getChangeLogForVersionName(final String versionName) {
        JsonReader jsonReader = getJsonReader();
        Map<String, String> changeLog = buildChangeLogMap(jsonReader);
        return changeLog.get(versionName);
    }

    private JsonReader getJsonReader() {
        try {
            InputStream inputStream = applicationContextProvider.openRawResource(R.raw.changelog);
            return new JsonReader(new InputStreamReader(inputStream, CHANGE_LOG_FILE_ENCODING));
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Map<String, String> buildChangeLogMap(final JsonReader jsonReader) {
        Map<String, String> changeLog = new HashMap<>();
        try {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                changeLog.put(jsonReader.nextName(), jsonReader.nextString());
                jsonReader.endObject();
            }
            jsonReader.endArray();
        } catch (IOException exception) {
            parseAnalyticsAdapter.trackException(exception);
        }
        return changeLog;
    }

}
