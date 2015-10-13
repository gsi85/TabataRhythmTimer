package com.sisa.tabata.media.dao;

import static com.sisa.tabata.validation.Validation.empty;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;
import com.sisa.tabata.media.domain.Song;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * DAO service for retrieving audio files from {@link MediaStore.Audio.Media}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class AudioMediaStoreDao {

    private static final Uri EXTERNAL_CONTENT_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final String SELECTION = MediaStore.Audio.Media.IS_MUSIC + " != 0";
    private static final String[] PROJECTION = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TRACK, MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.DATA};
    private static final int TRACK_ID_COLUMN_INDEX = 0;
    private static final int TRACK_NUMBER_COLUMN_INDEX = 1;
    private static final int TITLE_COLUMN_INDEX = 2;
    private static final int ARTIST_COLUMN_INDEX = 3;
    private static final int ALBUM_COLUMN_INDEX = 4;
    private static final int DATA_STREAM_COLUMN_INDEX = 5;
    private static final String UNKNOWN_ARTIST = "Unknown artist";
    private static final String UNKNOWN_ALBUM = "Unknown album";
    private static final String UNKNOWN = "<unknown>";

    /**
     * Returns all audio files from {@link MediaStore} represented as list of {@link Song}.
     *
     * @param context {@link Context}
     * @return the list of {@link Song}
     */
    public List<Song> getSongs(final Context context) {
        List<Song> songs = new ArrayList<>();
        Cursor cursor = getCursor(context);
        while (cursor.moveToNext()) {
            songs.add(buildSong(cursor));
        }
        cursor.close();
        return songs;
    }

    private Cursor getCursor(final Context context) {
        CursorLoader cursorLoader = new CursorLoader(context, EXTERNAL_CONTENT_URI, PROJECTION, SELECTION, null, null);
        return cursorLoader.loadInBackground();
    }

    private Song buildSong(final Cursor cursor) {
        return new Song.Builder()
                .withTrackid(cursor.getLong(TRACK_ID_COLUMN_INDEX))
                .withTracknumber(cursor.getInt(TRACK_NUMBER_COLUMN_INDEX))
                .withTitle(cursor.getString(TITLE_COLUMN_INDEX))
                .withArtist(getNullSafeText(cursor.getString(ARTIST_COLUMN_INDEX), UNKNOWN_ARTIST))
                .withAlbum(getNullSafeText(cursor.getString(ALBUM_COLUMN_INDEX), UNKNOWN_ALBUM))
                .withDataStream(cursor.getString(DATA_STREAM_COLUMN_INDEX))
                .build();
    }

    private String getNullSafeText(final String text, final String defaultText) {
        return empty(text) || UNKNOWN.equals(text) ? defaultText : text;
    }

}
