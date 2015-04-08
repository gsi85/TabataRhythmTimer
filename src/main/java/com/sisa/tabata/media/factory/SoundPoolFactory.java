package com.sisa.tabata.media.factory;

import android.media.AudioManager;
import android.media.SoundPool;

import com.google.inject.Singleton;

/**
 * Factory for {@link SoundPool}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class SoundPoolFactory {

    private static final int DEFAULT_SOURCE_QUALITY = 0;

    /**
     * Creates a new {@link SoundPool} instance.
     *
     * @param maxStreams    the maximum number of simultaneous streams for this {@link SoundPool} object
     * @param streamType    the audio stream type as described in AudioManager for example, game applications will normally use {@link AudioManager#STREAM_MUSIC}
     * @return {@link SoundPool}
     */
    public SoundPool create(final int maxStreams, final int streamType) {
        return new SoundPool(maxStreams, streamType, DEFAULT_SOURCE_QUALITY);
    }

}
