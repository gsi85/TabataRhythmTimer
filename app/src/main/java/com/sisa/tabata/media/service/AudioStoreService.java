package com.sisa.tabata.media.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.media.dao.loader.AudioStoreManager;
import com.sisa.tabata.media.dao.AudioMediaStoreDao;
import com.sisa.tabata.media.dao.PlaylistMediaStoreDao;
import com.sisa.tabata.media.domain.AudioStore;

import android.content.Context;

/**
 * Service for retrieving {@link AudioStore} from Android's media store.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class AudioStoreService {

    @Inject
    private AudioMediaStoreDao audioMediaStoreDao;
    @Inject
    private PlaylistMediaStoreDao playlistMediaStoreDao;

    /**
     * Creates a {@link AudioStore} based on the meta information stored in media store an loads into {@link AudioStoreManager}.
     *
     * @param context {@link Context}
     * @return {@link AudioStore}
     */
    public AudioStore getAudioStoreFromMediaStore(final Context context) {
        return new AudioStore.Builder().addSongs(audioMediaStoreDao.getSongs(context)).withPlaylists(playlistMediaStoreDao.getPlaylists(context))
                .build();
    }

}
