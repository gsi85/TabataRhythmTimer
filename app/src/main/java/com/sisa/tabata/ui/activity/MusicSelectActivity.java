package com.sisa.tabata.ui.activity;

import java.util.Arrays;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.media.domain.AudioStore;
import com.sisa.tabata.media.service.AudioStoreService;
import com.sisa.tabata.ui.listener.music.MusicSelectActionButtonClickListener;
import com.sisa.tabata.ui.listener.music.MusicSelectHeaderButtonClickListener;
import com.sisa.tabata.ui.provider.music.AlbumListTextViewsProvider;
import com.sisa.tabata.ui.provider.music.ArtistListTextViewsProvider;
import com.sisa.tabata.ui.provider.music.PlaylistTextViewsProvider;
import com.sisa.tabata.ui.provider.music.SongsListTextViewsProvider;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
    @InjectView(R.id.albumLayout)
    private LinearLayout albumLayout;
    @InjectView(R.id.artistLayout)
    private LinearLayout artistLayout;
    @InjectView(R.id.playlistLayout)
    private LinearLayout playlistLayout;
    @InjectView(R.id.songsLayout)
    private LinearLayout songsLayout;

    @Inject
    private MusicSelectActionButtonClickListener musicSelectActionButtonClickListener;
    @Inject
    private MusicSelectHeaderButtonClickListener musicSelectHeaderButtonClickListener;
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
        loadAudioItems();
        setViewDependencies();
        setUpListeners();
    }

    @Override
    public void onBackPressed() {
        cancelButton.performClick();
    }

    private void setUpAudioStore() {
        audioStore = audioStoreService.getAudioStore(this);
    }

    private void loadAudioItems() {
        albumListTextViewsProvider.createAlbumsListTextViews(audioStore, albumItemsLayout);
        artistListTextViewsProvider.createArtistsListTextViews(audioStore, artistItemsLayout);
        playlistTextViewsProvider.createPlaylistListTextViews(audioStore, playlistItemsLayout);
        songsListTextViewsProvider.createSongListTextViews(audioStore, songsItemsLayout);
    }

    private void setViewDependencies() {
        musicSelectHeaderButtonClickListener
                .setAudioItemContainers(Arrays.asList(new Pair<>(albumLayout, albumItemsLayout), new Pair<>(artistLayout, artistItemsLayout),
                        new Pair<>(playlistLayout, playlistItemsLayout), new Pair<>(songsLayout, songsItemsLayout)));
    }

    private void setUpListeners() {
        saveButton.setOnClickListener(musicSelectActionButtonClickListener);
        cancelButton.setOnClickListener(musicSelectActionButtonClickListener);
        albumLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
        artistLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
        playlistLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
        songsLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
    }

}
