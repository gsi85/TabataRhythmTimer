package com.sisa.tabata.media.dao;

import static com.sisa.tabata.validation.Validation.empty;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;
import com.sisa.tabata.media.domain.Playlist;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * DAO service for retrieving audio files from {@link MediaStore.Audio.Playlists}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class PlaylistMediaStoreDao {

    private static final Uri EXTERNAL_CONTENT_URI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
    private static final String[] PROJECTION = new String[]{MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME,
        MediaStore.Audio.Playlists.DATA};
    private static final int PLAYLIST_ID_COLUMN_INDEX = 0;
    private static final int NAME_COLUMN_INDEX = 1;
    private static final int DATA_STREAM_COLUMN_INDEX = 2;
    private static final String UNKNOWN_PLAYLIST = "Unknown playlist";
    private static final String UNKNOWN = "<unknown>";

    /**
     * Returns all playlist files from {@link MediaStore} represented as list of {@link Playlist}.
     *
     * @param context {@link Context}
     * @return the list of {@link Playlist}
     */
    public List<Playlist> getPlaylists(final Context context) {
        List<Playlist> playlists = new ArrayList<>();
        Cursor cursor = getCursor(context);
        while (cursor.moveToNext()) {
            playlists.add(buildPlaylist(cursor));
        }
        cursor.close();
        return playlists;
    }

    private Cursor getCursor(final Context context) {
        CursorLoader cursorLoader = new CursorLoader(context, EXTERNAL_CONTENT_URI, PROJECTION, null, null, null);
        return cursorLoader.loadInBackground();
    }

    private Playlist buildPlaylist(final Cursor cursor) {
        return new Playlist.Builder()
                .withPlaylistId(cursor.getLong(PLAYLIST_ID_COLUMN_INDEX))
                .withName(getNullSafeText(cursor.getString(NAME_COLUMN_INDEX), UNKNOWN_PLAYLIST))
                .withDataStream(cursor.getString(DATA_STREAM_COLUMN_INDEX))
                .build();
    }

    private String getNullSafeText(final String text, final String defaultText) {
        return empty(text) || UNKNOWN.equals(text) ? defaultText : text;
    }

}
