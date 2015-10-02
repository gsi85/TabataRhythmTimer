package com.sisa.tabata.ui.provider.music;

import static android.text.Html.fromHtml;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.dao.loader.AudioStoreManager;
import com.sisa.tabata.media.domain.Song;

import android.text.Spanned;
import android.widget.LinearLayout;

import roboguice.inject.ContextSingleton;

/**
 * Songs list text view provider.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SongsListTextViewsProvider extends AbstractMusicSelectTextViewProvider {

    private static final int SONGS_TEXT_VIEW_PATTERN = R.string.songs_text_view_pattern;
    private static final String CATEGORY = "song";

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private AudioStoreManager audioStoreManager;


    /**
     * Creates songs items text views.
     *
     * @param songItemsContainer  {@link LinearLayout} container for songs list
     */
    public void createSongListTextViews(final LinearLayout songItemsContainer) {
        for (Song song : audioStoreManager.getAudioStore().getAllSongs()) {
            addView(songItemsContainer, getFormattedText(song), CATEGORY, String.valueOf(song.getTrackId()));
        }
    }

    private Spanned getFormattedText(final Song song) {
        String title = song.getTitle();
        String artist = song.getArtist();
        return fromHtml(String.format(applicationContextProvider.getStringResource(SONGS_TEXT_VIEW_PATTERN), title, artist));
    }
}
