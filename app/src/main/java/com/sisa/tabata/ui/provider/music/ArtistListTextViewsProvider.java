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
 * Provider of artists list items.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class ArtistListTextViewsProvider extends AbstractMusicSelectTextViewProvider {

    private static final int ARTIST_TEXT_VIEW_PATTERN = R.string.artists_text_view_pattern;
    private static final int FIRST_ITEM_INDEX = 0;
    private static final String CATEGORY = "artist";

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private AudioStoreManager audioStoreManager;


    /**
     * Creates artists items text views.
     *
     * @param artistsItemsContainer {@link LinearLayout} container for artist list
     */
    public void createArtistsListTextViews(final LinearLayout artistsItemsContainer) {
        Map<String, List<Song>> songsByArtists = audioStoreManager.getAudioStore().getSongsByArtists();
        SortedSet<String> artists = new TreeSet<>(songsByArtists.keySet());
        for (String artist : artists) {
            addView(artistsItemsContainer, getFormattedText(songsByArtists.get(artist)), CATEGORY, artist);
        }
    }

    private Spanned getFormattedText(final List<Song> songs) {
        Song song = songs.get(FIRST_ITEM_INDEX);
        String artist = song.getArtist();
        int numberOfSongs = songs.size();
        return fromHtml(String.format(applicationContextProvider.getStringResource(ARTIST_TEXT_VIEW_PATTERN), artist, numberOfSongs));
    }

}
