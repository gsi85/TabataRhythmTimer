package com.sisa.tabata.media.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Pair;

/**
 * DAO for storing the selected checkboxes on the music select activity.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class SelectMusicCheckboxDao extends SQLiteAssetHelper {

    private static final String MUSIC_CHECKBOX_TABLE = "tbl_music_checkbox";
    private static final String KEY_COLUMN = "key";
    private static final String VALUE_COLUMN = "value";
    private static final String[] ALL_COLUMNS = new String[]{KEY_COLUMN, VALUE_COLUMN};
    private static final int KEY_COLUMN_INDEX = 0;
    private static final int VALUE_COLUMN_INDEX = 1;

    private final SQLiteDatabase database;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     */
    @Inject
    public SelectMusicCheckboxDao(ApplicationContextProvider applicationContextProvider) {
        super(applicationContextProvider.getContext(), applicationContextProvider.getStringResource(R.string.database_name), null,
                applicationContextProvider.getIntResource(R.integer.database_version));
        database = getWritableDatabase();
    }

    /**
     * Persist the checkboxes selected on music select activity.
     *
     * @param selectedCheckboxes list of selected checkboxes
     */
    public void persistCheckBoxState(final List<Pair<String, String>> selectedCheckboxes) {
        database.delete(MUSIC_CHECKBOX_TABLE, null, null);
        for (Pair<String, String> entry : selectedCheckboxes) {
            ContentValues contentValues = createContentValue(entry);
            database.insert(MUSIC_CHECKBOX_TABLE, KEY_COLUMN, contentValues);
        }
    }

    /**
     * Returns saved checkbox states.
     *
     * @return the list of previously selected checkboxes
     */
    public List<Pair<String, String>> getCheckBoxState() {
        List<Pair<String, String>> selectedCheckBoxes = new ArrayList<>();
        Cursor allRowsCursor = executeRetrieveAllRowsQuery();
        while (allRowsCursor.moveToNext()) {
            selectedCheckBoxes.add(new Pair<>(allRowsCursor.getString(KEY_COLUMN_INDEX), allRowsCursor.getString(VALUE_COLUMN_INDEX)));
        }
        allRowsCursor.close();
        return selectedCheckBoxes;
    }

    private ContentValues createContentValue(final Pair<String, String> entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_COLUMN, entry.first);
        contentValues.put(VALUE_COLUMN, entry.second);
        return contentValues;
    }

    private Cursor executeRetrieveAllRowsQuery() {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MUSIC_CHECKBOX_TABLE);
        return queryBuilder.query(database, ALL_COLUMNS, null, null, null, null, null);
    }

}
