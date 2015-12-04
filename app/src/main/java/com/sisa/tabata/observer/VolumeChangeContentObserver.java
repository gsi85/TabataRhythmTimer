package com.sisa.tabata.observer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ui.provider.VolumeButtonImageResourceProvider;

import android.database.ContentObserver;
import android.os.Handler;
import android.widget.ImageButton;

/**
 * This class is a bit of a workaround as currently there is no broadcast action for volume change.
 * If a setting is change, which a volume change is, it updates the volume button image resource on the workout activity.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class VolumeChangeContentObserver extends ContentObserver {

    @Inject
    private VolumeButtonImageResourceProvider volumeButtonImageResourceProvider;
    private ImageButton volumeButton;

    /**
     * Creates a content observer.
     */
    public VolumeChangeContentObserver() {
        super(new Handler());
    }

    @Override
    public void onChange(boolean selfChange) {
        volumeButtonImageResourceProvider.setVolumeImageResource(volumeButton);
    }

    public void setVolumeButton(final ImageButton volumeButton) {
        this.volumeButton = volumeButton;
    }
}
