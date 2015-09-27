package com.sisa.tabata.media.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.media.dao.AudioStoreDao;
import com.sisa.tabata.media.dao.PlaylistStoreDao;
import com.sisa.tabata.media.domain.AudioStore;

import android.content.Context;

/**
 * Service for retrieving {@link AudioStore} from Android's media store.
 *
 * @author Laca
 */
@Singleton
public class AudioStoreService {

    @Inject
    private AudioStoreDao audioStoreDao;
    @Inject
    private PlaylistStoreDao playlistStoreDao;

    /**
     * Builds {@link AudioStore} based on the meta information stored in media store.
     *
     * @param context {@link Context}
     * @return {@link AudioStore}
     */
    public AudioStore getAudioStore(final Context context) {
        return new AudioStore.Builder()
                .addSongs(audioStoreDao.getSongs(context))
                .withPlaylists(playlistStoreDao.getPlaylists(context))
                .build();
    }

}
