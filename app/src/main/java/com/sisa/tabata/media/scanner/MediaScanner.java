package com.sisa.tabata.media.scanner;

import static android.media.MediaScannerConnection.OnScanCompletedListener;
import static android.media.MediaScannerConnection.scanFile;
import static com.sisa.tabata.validation.Assert.isInstanceOf;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.activity.MusicSelectActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * Scanner for refreshing media store's meta data.
 * Scans through files on external storage to update information in database.
 * This is operation should be run on a worker thread.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class MediaScanner implements OnScanCompletedListener {

    private static final String[] PATHS_TO_SCAN = new String[]{Environment.getExternalStorageDirectory().toString()};

    private Context context;

    /**
     * Scans through the external storage to refresh data stored in media store's database.
     *
     * @param context {@link Context}
     */
    public void scanFiles(final Context context) {
        this.context = context;
        scanFile(context, PATHS_TO_SCAN, null, this);
    }

    @Override
    public void onScanCompleted(final String path, final Uri uri) {
        MusicSelectActivity musicSelectActivity = getContext();
        Intent intent = musicSelectActivity.getIntent();
        musicSelectActivity.finish();
        musicSelectActivity.startActivity(intent);
    }

    private MusicSelectActivity getContext() {
        isInstanceOf(MusicSelectActivity.class, context, "context is not a MusicSelectActivity!");
        return (MusicSelectActivity) context;
    }

}
