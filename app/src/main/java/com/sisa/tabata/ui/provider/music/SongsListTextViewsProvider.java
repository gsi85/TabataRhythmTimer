package com.sisa.tabata.ui.provider.music;

import static android.text.Html.fromHtml;

import java.util.List;

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
 * Songs list text view provider.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class SongsListTextViewsProvider extends AbstractMusicSelectTextViewProvider {

    private static final int SONGS_TEXT_VIEW_PATTERN = R.string.songs_text_view_pattern;

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Creates songs items text views.
     *
     * @param audioStore          {@link AudioStore}
     * @param songItemsContainer  {@link LinearLayout} container for songs list
     * @param musicSelectActivity {@link MusicSelectActivity}
     */
    public void createSongListTextViews(final AudioStore audioStore, final LinearLayout songItemsContainer,
            final MusicSelectActivity musicSelectActivity) {
        List<Song> songs = audioStore.getAllSongs();
        for (Song song : songs) {
            TextView textView = createTextView(musicSelectActivity);
            textView.setText(getFormattedText(song));
            setStyle(musicSelectActivity, textView);
            songItemsContainer.addView(textView);
        }
    }

    private Spanned getFormattedText(final Song song) {
        String title = song.getTitle();
        String artist = song.getArtist();
        return fromHtml(String.format(applicationContextProvider.getStringResource(SONGS_TEXT_VIEW_PATTERN), title, artist));
    }
}
