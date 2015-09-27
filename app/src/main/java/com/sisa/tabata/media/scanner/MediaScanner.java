package com.sisa.tabata.media.scanner;

import com.google.inject.Singleton;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;

/**
 * Scanner for refreshing media store's meta data.
 * Scans through files on external storage to update information in database.
 * This is operation should be run on a worker thread.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class MediaScanner {

    private static final String[] PATHS_TO_SCAN = new String[]{Environment.getExternalStorageDirectory().toString()};

    /**
     * Scans through the external storage to refresh data stored in media store's database.
     *
     * @param context {@link Context}
     */
    public void scanFiles(final Context context) {
        MediaScannerConnection.scanFile(context, PATHS_TO_SCAN, null, null);
    }

}
