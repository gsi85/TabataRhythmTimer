package com.sisa.tabata.media.transformer;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.dao.loader.AudioStoreManager;
import com.sisa.tabata.media.domain.AudioStore;
import com.sisa.tabata.media.domain.Playlist;
import com.sisa.tabata.media.domain.Song;
import com.sisa.tabata.ui.provider.music.AlbumListTextViewsProvider;
import com.sisa.tabata.ui.provider.music.ArtistListTextViewsProvider;
import com.sisa.tabata.ui.provider.music.PlaylistTextViewsProvider;
import com.sisa.tabata.ui.provider.music.SongsListTextViewsProvider;

import android.util.Pair;

/**
 * Transformer for creating list of {@link Song} based on the data stored in {@link AudioStore} from the list of checkboxes selected.
 *
 * @author Laszlo_Sisa
 */
@Singleton
public class SelectedCheckboxTransformer {

    @Inject
    private AudioStoreManager audioStoreManager;
    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Transforms the checkboxes selected be the user on the music select activity into a list of {@link Song}.
     *
     * @param selectedCheckboxes list of selected checkboxes
     * @return the list of {@link Song}
     */
    public List<Song> transformCheckBoxesToSongs(final List<Pair<String, String>> selectedCheckboxes) {
        AudioStore audioStore = audioStoreManager.getAudioStore();
        List<Song> selectedSongs = new ArrayList<>();
        for (Pair<String, String> checkbox : selectedCheckboxes) {
            String checkBoxCategory = checkbox.first;
            String checkBoxKeyValue = checkbox.second;
            if (isAlbum(checkBoxCategory)) {
                selectedSongs.addAll(getSongsInAlbum(audioStore, checkBoxKeyValue));
            } else if (isArtist(checkBoxCategory)) {
                selectedSongs.addAll(getSongsByArtist(audioStore, checkBoxKeyValue));
            } else if (isPlaylist(checkBoxCategory)) {
                selectedSongs.add(getPlaylistAsSong(audioStore, checkBoxKeyValue));
            } else if (isSong(checkBoxCategory)) {
                selectedSongs.add(getSongByTrackId(audioStore, checkBoxKeyValue));
            }
        }
        return selectedSongs;
    }

    private boolean isAlbum(final String checkBoxCategory) {
        return AlbumListTextViewsProvider.CATEGORY.equals(checkBoxCategory);
    }

    private List<Song> getSongsInAlbum(final AudioStore audioStore, final String checkBoxKeyValue) {
        return audioStore.getSongsByAlbums().get(checkBoxKeyValue);
    }

    private boolean isArtist(final String checkBoxCategory) {
        return ArtistListTextViewsProvider.CATEGORY.equals(checkBoxCategory);
    }

    private List<Song> getSongsByArtist(final AudioStore audioStore, final String checkBoxKeyValue) {
        return audioStore.getSongsByArtists().get(checkBoxKeyValue);
    }

    private boolean isPlaylist(final String checkBoxCategory) {
        return PlaylistTextViewsProvider.CATEGORY.equals(checkBoxCategory);
    }

    private Song getPlaylistAsSong(final AudioStore audioStore, final String checkBoxKeyValue) {
        Playlist playlist = audioStore.getPlaylistById(Long.valueOf(checkBoxKeyValue));
        return new Song.Builder()
                .withTrackid(playlist.getPlaylistId())
                .withAlbum(applicationContextProvider.getStringResource(R.string.music_select_playlist))
                .withArtist(playlist.getName())
                .withDataStream(playlist.getDataStream())
                .build();
    }

    private boolean isSong(final String checkBoxCategory) {
        return SongsListTextViewsProvider.CATEGORY.equals(checkBoxCategory);
    }

    private Song getSongByTrackId(final AudioStore audioStore, final String checkBoxKeyValue) {
        return audioStore.getSongByTrackId(Long.valueOf(checkBoxKeyValue));
    }

}
