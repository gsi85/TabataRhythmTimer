package com.sisa.tabata.media.service;

import java.io.IOException;

import com.google.inject.Inject;

import android.media.AudioManager;
import android.media.MediaPlayer;

import roboguice.inject.ContextSingleton;

/**
 * Service for playing media files during workout.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class MediaPlayerService implements MediaPlayer.OnPreparedListener {

    private final MediaPlayer mediaPlayer;
    private final SelectedMusicService selectedMusicService;
    private boolean prepaired;

    /**
     * DI constructor.
     *
     * @param selectedMusicService {@link SelectedMusicService}
     */
    @Inject
    public MediaPlayerService(final SelectedMusicService selectedMusicService) throws IOException {
        this.selectedMusicService = selectedMusicService;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(selectedMusicService.getSelectedSongs().get(0).getDataStream());
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.prepareAsync();
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        prepaired = true;
        mediaPlayer.start();
    }

}
