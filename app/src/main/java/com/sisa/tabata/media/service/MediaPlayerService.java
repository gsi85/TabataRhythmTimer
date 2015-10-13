package com.sisa.tabata.media.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.media.domain.Song;
import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;
import com.sisa.tabata.validation.Validation;

import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * Service for playing media files during workout.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class MediaPlayerService implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static final float QUIET_VOLUME = 0.3F;
    private static final int LOUD_VOLUME = 1;

    private final MediaPlayer mediaPlayer;
    private final SelectedMusicService selectedMusicService;
    private final ParseAnalyticsAdapter parseAnalyticsAdapter;
    private final Queue<Song> songsToPlay;
    private boolean prepared;
    private boolean shouldPlay;

    /**
     * DI constructor.
     *
     * @param selectedMusicService {@link SelectedMusicService}
     * @param parseAnalyticsAdapter {@link ParseAnalyticsAdapter}
     */
    @Inject
    public MediaPlayerService(final SelectedMusicService selectedMusicService, final ParseAnalyticsAdapter parseAnalyticsAdapter) {
        this.parseAnalyticsAdapter = parseAnalyticsAdapter;
        this.selectedMusicService = selectedMusicService;
        songsToPlay = new LinkedList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    /**
     * Reset the media player, by uninitializing the media player and reloading the selected songs from database.
     */
    public void reset() {
        prepared = false;
        shouldPlay = false;
        songsToPlay.clear();
        songsToPlay.addAll(selectedMusicService.getSelectedSongs());
        loadNextSong();
    }

    /**
     * Starts or resumes playback.
     */
    public void play() {
        shouldPlay = true;
        if (prepared) {
            startPlayback();
        }
    }

    /**
     * Pauses playback.
     */
    public void pause() {
        shouldPlay = false;
        mediaPlayer.pause();
    }

    /**
     * Quietens music playback.
     */
    public void quieten() {
        mediaPlayer.setVolume(QUIET_VOLUME, QUIET_VOLUME);
    }

    /**
     * Loudens music playback.
     */
    public void louden() {
        mediaPlayer.setVolume(LOUD_VOLUME, LOUD_VOLUME);
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        prepared = true;
        if (shouldPlay) {
            startPlayback();
        }
    }

    @Override
    public void onCompletion(final MediaPlayer mp) {
        prepared = false;
        loadNextSong();
    }

    private void loadNextSong() {
        if (Validation.notEmpty(songsToPlay)) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(songsToPlay.remove().getDataStream());
                mediaPlayer.prepareAsync();
                //TODO: handle exception properly (e.g.: handle gracefully if stream not available anymore
            } catch (IOException e) {
                parseAnalyticsAdapter.trackException(e);
                e.printStackTrace();
                loadNextSong();
            }
        }
    }

    private void startPlayback() {
        louden();
        mediaPlayer.start();
    }
}
