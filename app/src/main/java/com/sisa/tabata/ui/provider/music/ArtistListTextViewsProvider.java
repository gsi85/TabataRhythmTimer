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
import com.sisa.tabata.ui.activity.MusicSelectActivity;

import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Creates artists items text views.
     *
     * @param audioStore            {@link AudioStore}
     * @param artistsItemsContainer {@link LinearLayout} container for artist list
     * @param musicSelectActivity   {@link MusicSelectActivity}
     */
    public void createArtistsListTextViews(final AudioStore audioStore, final LinearLayout artistsItemsContainer,
            final MusicSelectActivity musicSelectActivity) {
        Map<String, List<Song>> songsByArtists = audioStore.getSongsByArtists();
        SortedSet<String> albums = new TreeSet<>(songsByArtists.keySet());
        for (String album : albums) {
            TextView textView = createTextView(musicSelectActivity);
            textView.setText(getFormattedText(songsByArtists.get(album)));
            setStyle(musicSelectActivity, textView);
            artistsItemsContainer.addView(textView);
        }
    }

    private Spanned getFormattedText(final List<Song> songs) {
        Song song = songs.get(FIRST_ITEM_INDEX);
        String artist = song.getArtist();
        int numberOfSongs = songs.size();
        return fromHtml(String.format(applicationContextProvider.getStringResource(ARTIST_TEXT_VIEW_PATTERN), artist, numberOfSongs));
    }

}
