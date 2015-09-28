package com.sisa.tabata.ui.provider.music;

import static android.text.Html.fromHtml;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.domain.AudioStore;
import com.sisa.tabata.media.domain.Song;

import android.text.Spanned;
import android.widget.LinearLayout;

import roboguice.inject.ContextSingleton;

/**
 * Provider of albums list items.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class AlbumListTextViewsProvider extends AbstractMusicSelectTextViewProvider {

    private static final int ALBUMS_TEXT_VIEW_PATTERN = R.string.albums_text_view_pattern;
    private static final int FIRST_ITEM_INDEX = 0;

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Creates albums items text views.
     *
     * @param audioStore          {@link AudioStore}
     * @param albumItemsContainer {@link LinearLayout} container for album list
     */
    public void createAlbumsListTextViews(final AudioStore audioStore, final LinearLayout albumItemsContainer) {
        Map<String, List<Song>> songsByAlbums = audioStore.getSongsByAlbums();
        SortedSet<String> albums = new TreeSet<>(songsByAlbums.keySet());
        for (String album : albums) {
            addView(albumItemsContainer, getFormattedText(songsByAlbums.get(album)));
        }
    }

    private Spanned getFormattedText(final List<Song> songs) {
        Song song = songs.get(FIRST_ITEM_INDEX);
        String artist = song.getArtist();
        String album = song.getAlbum();
        int numberOfSongs = songs.size();
        return fromHtml(String.format(applicationContextProvider.getStringResource(ALBUMS_TEXT_VIEW_PATTERN), album, artist, numberOfSongs));
    }

}