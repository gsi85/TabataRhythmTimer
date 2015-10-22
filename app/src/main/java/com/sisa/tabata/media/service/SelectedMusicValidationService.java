package com.sisa.tabata.media.service;

import java.io.File;

import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.media.domain.Song;

import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Service to validate stored selected music.
 * Checks if every stored audio item still exists.
 * If necessary removes non existing entries, and notifies user.
 *
 * @author Laszlo Sisa
 */
public class SelectedMusicValidationService extends AsyncTask<Void, Void, Boolean> {

    private final SelectedMusicService selectedMusicService;
    private final ApplicationContextProvider applicationContextProvider;

    /**
     * DI constructor.
     *
     * @param selectedMusicService       {@link SelectedMusicService}
     * @param applicationContextProvider {@link ApplicationContextProvider}
     */
    public SelectedMusicValidationService(final SelectedMusicService selectedMusicService,
            final ApplicationContextProvider applicationContextProvider) {
        this.applicationContextProvider = applicationContextProvider;
        this.selectedMusicService = selectedMusicService;
    }

    @Override
    protected Boolean doInBackground(final Void... params) {
        return validateSavedSongs();
    }

    @Override
    protected void onPostExecute(final Boolean shouldNotify) {
        if (Boolean.TRUE.equals(shouldNotify)) {
            displayNotificationDialog();
        }
    }

    private boolean validateSavedSongs() {
        boolean shouldNotify = false;
        for (Song song : selectedMusicService.getSelectedSongs()) {
            File songAsFile = new File(song.getDataStream());
            if (fileNotExists(songAsFile)) {
                shouldNotify = true;
                selectedMusicService.deleteSongById(song.getTrackId());
                MediaScannerConnection.scanFile(applicationContextProvider.getContext(), new String[]{song.getDataStream()}, null, null);
            }
        }
        return shouldNotify;
    }

    private boolean fileNotExists(final File songAsFile) {
        return !songAsFile.exists();
    }

    private void displayNotificationDialog() {
        String notification = applicationContextProvider.getStringResource(R.string.media_file_not_found);
        Toast.makeText(applicationContextProvider.getContext(), notification, Toast.LENGTH_LONG).show();
    }
}
