package com.sisa.tabata.ui.provider.music;

import static android.text.Html.fromHtml;

import android.text.Spanned;
import android.widget.LinearLayout;
import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.dao.loader.AudioStoreManager;
import com.sisa.tabata.media.domain.Song;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import roboguice.inject.ContextSingleton;

/**
 * Provider of albums list items.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class AlbumListTextViewsProvider extends AbstractMusicSelectTextViewProvider {

    public static final String CATEGORY = "album";
    private static final int ALBUMS_TEXT_VIEW_PATTERN = R.string.albums_text_view_pattern;
    private static final int FIRST_ITEM_INDEX = 0;

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private AudioStoreManager audioStoreManager;

    /**
     * Creates albums items text views.
     *
     * @param albumItemsContainer {@link LinearLayout} container for album list
     */
    public void createAlbumsListTextViews(final LinearLayout albumItemsContainer) {
        Map<String, List<Song>> songsByAlbums = audioStoreManager.getAudioStore().getSongsByAlbums();
        SortedSet<String> albums = new TreeSet<>(songsByAlbums.keySet());
        for (String album : albums) {
            addView(albumItemsContainer, getFormattedText(songsByAlbums.get(album)), CATEGORY, album);
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
