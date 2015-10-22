package com.sisa.tabata.ui.provider.music;

import static android.text.Html.fromHtml;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.dao.loader.AudioStoreManager;
import com.sisa.tabata.media.domain.Playlist;

import android.text.Spanned;
import android.widget.LinearLayout;

import roboguice.inject.ContextSingleton;

/**
 * Playlist text views provider.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class PlaylistTextViewsProvider extends AbstractMusicSelectTextViewProvider {

    public static final String CATEGORY = "playlist";
    private static final int PLAYLIST_TEXT_VIEW_PATTERN = R.string.playlists_text_view_pattern;

    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @Inject
    private AudioStoreManager audioStoreManager;

    /**
     * Creates playlist items text views.
     *
     * @param playlistsItemsContainer {@link LinearLayout} container for playlists list
     */
    public void createPlaylistListTextViews(final LinearLayout playlistsItemsContainer) {
        for (Playlist playlist : audioStoreManager.getAudioStore().getPlaylists()) {
            addView(playlistsItemsContainer, getFormattedText(playlist.getName()), CATEGORY, String.valueOf(playlist.getPlaylistId()));
        }
    }

    private Spanned getFormattedText(final String name) {
        return fromHtml(String.format(applicationContextProvider.getStringResource(PLAYLIST_TEXT_VIEW_PATTERN), name));
    }

}
