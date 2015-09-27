package com.sisa.tabata.ui.provider.music;

import static android.text.Html.fromHtml;

import java.util.List;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.domain.AudioStore;
import com.sisa.tabata.media.domain.Playlist;
import com.sisa.tabata.ui.activity.MusicSelectActivity;

import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;

/**
 * Playlist text views provider.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class PlaylistTextViewsProvider extends AbstractMusicSelectTextViewProvider {

    private static final int PLAYLIST_TEXT_VIEW_PATTERN = R.string.playlists_text_view_pattern;

    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Creates playlist items text views.
     *
     * @param audioStore              {@link AudioStore}
     * @param playlistsItemsContainer {@link LinearLayout} container for playlists list
     * @param musicSelectActivity     {@link MusicSelectActivity}
     */
    public void createPlaylistListTextViews(final AudioStore audioStore, final LinearLayout playlistsItemsContainer,
            final MusicSelectActivity musicSelectActivity) {
        List<Playlist> playlists = audioStore.getPlaylists();
        for (Playlist playlist : playlists) {
            TextView textView = createTextView(musicSelectActivity);
            textView.setText(getFormattedText(playlist.getName()));
            setStyle(musicSelectActivity, textView);
            playlistsItemsContainer.addView(textView);
        }
    }

    private Spanned getFormattedText(final String name) {
        return fromHtml(String.format(applicationContextProvider.getStringResource(PLAYLIST_TEXT_VIEW_PATTERN), name));
    }

}
