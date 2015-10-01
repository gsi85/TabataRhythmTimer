package com.sisa.tabata.media.dao.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.media.domain.AudioStore;
import com.sisa.tabata.media.service.AudioStoreService;
import com.sisa.tabata.validation.Assert;

import android.content.Context;

/**
 * Manager for the loaded {@link AudioStore}.
 *
 * @author Laca
 */
@Singleton
public class AudioStoreManager {

    @Inject
    private AudioStoreService audioStoreService;
    private AudioStore audioStore;

    /**
     * Returns the stored {@link AudioStore}.
     * {@code audioStore} must be set before calling this method, otherwise exception thrown.
     *
     * @return {@link AudioStore}
     * @throws  IllegalArgumentException if {@code audioStore} is null
     */
    public AudioStore getAudioStore() {
        Assert.notNull(audioStore, "audioStore must be loaded before retrieving it");
        return audioStore;
    }

    /**
     * Loads {@link AudioStore} based on the entries in Android's Media Store.
     *
     * @param context {@link Context}
     */
    public void loadAudioStoreFromMediaStore(final Context context) {
        this.audioStore = audioStoreService.getAudioStoreFromMediaStore(context);
    }
}
