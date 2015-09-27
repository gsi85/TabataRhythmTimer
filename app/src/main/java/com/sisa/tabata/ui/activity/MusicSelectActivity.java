package com.sisa.tabata.ui.activity;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.media.domain.AudioStore;
import com.sisa.tabata.media.service.AudioStoreService;
import com.sisa.tabata.ui.listener.music.MusicSelectActionButtonClickListener;
import com.sisa.tabata.ui.provider.music.AlbumListTextViewsProvider;
import com.sisa.tabata.ui.provider.music.ArtistListTextViewsProvider;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.sisa.tabata.ui.provider.music.PlaylistTextViewsProvider;
import com.sisa.tabata.ui.provider.music.SongsListTextViewsProvider;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Music select activity.
 *
 * @author Laszlo Sisa
 */
public class MusicSelectActivity extends RoboActivity {

    @InjectView(R.id.musicSaveButton)
    private ImageButton saveButton;
    @InjectView(R.id.musicCancelButton)
    private ImageButton cancelButton;
    @InjectView(R.id.albumItemsLayout)
    private LinearLayout albumItemsLayout;
    @InjectView(R.id.artistItemsLayout)
    private LinearLayout artistItemsLayout;
    @InjectView(R.id.playlistItemsLayout)
    private LinearLayout playlistItemsLayout;
    @InjectView(R.id.songsItemsLayout)
    private LinearLayout songsItemsLayout;

    @Inject
    private MusicSelectActionButtonClickListener musicSelectActionButtonClickListener;
    @Inject
    private AlbumListTextViewsProvider albumListTextViewsProvider;
    @Inject
    private ArtistListTextViewsProvider artistListTextViewsProvider;
    @Inject
    private PlaylistTextViewsProvider playlistTextViewsProvider;
    @Inject
    private SongsListTextViewsProvider songsListTextViewsProvider;
    @Inject
    private AudioStoreService audioStoreService;
    private AudioStore audioStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);
        setUpAudioStore();
        setUpListeners();
        loadAudioItems();
    }

    @Override
    public void onBackPressed() {
        cancelButton.performClick();
    }

    private void setUpListeners() {
        saveButton.setOnClickListener(musicSelectActionButtonClickListener);
        cancelButton.setOnClickListener(musicSelectActionButtonClickListener);
    }

    private void setUpAudioStore() {
        audioStore = audioStoreService.getAudioStore(this);
    }

    private void loadAudioItems() {
        albumListTextViewsProvider.createAlbumsListTextViews(audioStore, albumItemsLayout, this);
        artistListTextViewsProvider.createArtistsListTextViews(audioStore, artistItemsLayout, this);
        playlistTextViewsProvider.createPlaylistListTextViews(audioStore, playlistItemsLayout, this);
        songsListTextViewsProvider.createSongListTextViews(audioStore, songsItemsLayout, this);
    }

}
