package com.sisa.tabata.media.dao.loader;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;

import com.google.inject.Inject;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.domain.Song;

/**
 * @author Laszlo_Sisa
 */
public class SelectedMusicDao extends SQLiteAssetHelper {

    private static final String SONG_TABLE = "tbl_song";
    private static final String TRACK_ID_COLUMN = "trackId";
    private static final String TRACK_NUMBER_COLUMN = "trackNumber";
    private static final String TITLE_COLUMN = "title";
    private static final String ARTIST_COLUMN = "artist";
    private static final String ALBUM_COLUMN = "album";
    private static final String DATA_STREAM_COLUMN = "dataStream";
    private static final String[] ALL_COLUMNS = {TRACK_ID_COLUMN, TRACK_NUMBER_COLUMN, TITLE_COLUMN, ARTIST_COLUMN, ALBUM_COLUMN, DATA_STREAM_COLUMN};
    private static final int TRACK_ID_COLUMN_INDEX = 0;
    private static final int TRACK_NUMBER_COLUMN_INDEX = 1;
    private static final int TITLE_COLUMN_INDEX = 2;
    private static final int ARTIST_COLUMN_INDEX = 3;
    private static final int ALBUM_COLUMN_INDEX = 4;
    private static final int DATA_STREAM_COLUMN_INDEX = 5;

    private final SQLiteDatabase database;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     */
    @Inject
    public SelectedMusicDao(ApplicationContextProvider applicationContextProvider) {
        super(applicationContextProvider.getContext(), applicationContextProvider.getStringResource(R.string.database_name), null,
                applicationContextProvider.getIntResource(R.integer.database_version));
        database = getWritableDatabase();
    }

    /**
     * Persists songs selected by user on music select activity.
     *
     * @param selectedSongs list of selected songs
     */
    public void persistSelectedSongs(List<Song> selectedSongs) {
        database.delete(SONG_TABLE, null, null);
        for (Song song : selectedSongs) {
            ContentValues contentValues = createContentValues(song);
            database.insert(SONG_TABLE, TRACK_ID_COLUMN, contentValues);
        }
    }

    /**
     * Returns the saved songs list selected by the user.
     */
    public List<Song> getSelectedSongs() {
        List<Song> selectedSongs = initializeSongs();
        Cursor allRowsCursor = executeRetrieveAllRowsQuery();
        while (allRowsCursor.moveToNext()) {
            selectedSongs.add(buildSong(allRowsCursor));
        }
        allRowsCursor.close();
        return selectedSongs;
    }

    private ContentValues createContentValues(final Song song) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACK_ID_COLUMN, song.getTrackId());
        contentValues.put(TRACK_NUMBER_COLUMN, song.getTrackNumber());
        contentValues.put(TITLE_COLUMN, song.getTitle());
        contentValues.put(ARTIST_COLUMN, song.getArtist());
        contentValues.put(ALBUM_COLUMN, song.getAlbum());
        contentValues.put(DATA_STREAM_COLUMN, song.getDataStream());
        return contentValues;
    }

    @NonNull
    private ArrayList<Song> initializeSongs() {
        return new ArrayList<>();
    }

    private Cursor executeRetrieveAllRowsQuery() {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(SONG_TABLE);
        return queryBuilder.query(database, ALL_COLUMNS, null, null, null, null, null);
    }

    private Song buildSong(final Cursor cursor) {
        return new Song.Builder()
                .withTrackid(cursor.getLong(TRACK_ID_COLUMN_INDEX))
                .withTracknumber(cursor.getInt(TRACK_NUMBER_COLUMN_INDEX))
                .withTitle(cursor.getString(TITLE_COLUMN_INDEX))
                .withArtist(cursor.getString(ARTIST_COLUMN_INDEX))
                .withAlbum(cursor.getString(ALBUM_COLUMN_INDEX))
                .withDataStream(cursor.getString(DATA_STREAM_COLUMN_INDEX))
                .build();
    }

}
