package com.sisa.tabata.ui.activity;

import java.util.Arrays;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.media.dao.loader.AudioStoreManager;
import com.sisa.tabata.media.dao.loader.SelectedMusicManager;
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

import roboguice.inject.InjectView;

/**
 * Music select activity.
 *
 * @author Laszlo Sisa
 */
public class MusicSelectActivity extends BaseActivity {

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
    @InjectView(R.id.rescanLayout)
    private LinearLayout rescanLayout;
    @InjectView(R.id.itemsRescanLayout)
    private LinearLayout itemsRescanLayout;

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
    private AudioStoreManager audioStoreManager;
    @Inject
    private SelectedMusicManager selectedMusicManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);
        setUpAudioManagers();
        loadAudioItems();
        setViewDependencies();
        setUpListeners();
    }

    @Override
    public void onBackPressed() {
        cancelButton.performClick();
    }

    private void setUpAudioManagers() {
        audioStoreManager.loadAudioStoreFromMediaStore(this);
        selectedMusicManager.loadSelectedCheckboxes();
    }

    private void loadAudioItems() {
        albumListTextViewsProvider.createAlbumsListTextViews(albumItemsLayout);
        artistListTextViewsProvider.createArtistsListTextViews(artistItemsLayout);
        playlistTextViewsProvider.createPlaylistListTextViews(playlistItemsLayout);
        songsListTextViewsProvider.createSongListTextViews(songsItemsLayout);
    }

    private void setViewDependencies() {
        musicSelectHeaderButtonClickListener.setAudioItemContainers(Arrays.asList(Pair.create(albumLayout, albumItemsLayout),
                Pair.create(artistLayout, artistItemsLayout), Pair.create(playlistLayout, playlistItemsLayout),
                Pair.create(songsLayout, songsItemsLayout), Pair.create(rescanLayout, itemsRescanLayout)));
    }

    private void setUpListeners() {
        saveButton.setOnClickListener(musicSelectActionButtonClickListener);
        cancelButton.setOnClickListener(musicSelectActionButtonClickListener);
        albumLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
        artistLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
        playlistLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
        songsLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
        rescanLayout.setOnClickListener(musicSelectHeaderButtonClickListener);
    }

}
