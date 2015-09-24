package com.sisa.tabata.media.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Domain objects storing {@link Song} in categorized manner.
 *
 * @author Laca
 */
public final class AudioStore {

    private final Map<String, List<Song>> songsByAlbums;
    private final Map<String, List<Song>> songsByArtists;
    private final List<Song> allSongs;

    private AudioStore(Builder builder) {
        songsByAlbums = Collections.unmodifiableMap(builder.songsByAlbums);
        songsByArtists = Collections.unmodifiableMap(builder.songsByArtists);
        allSongs = Collections.unmodifiableList(builder.allSongs);
    }

    public Map<String, List<Song>> getSongsByAlbums() {
        return songsByAlbums;
    }

    public Map<String, List<Song>> getSongsByArtists() {
        return songsByArtists;
    }

    public List<Song> getAllSongs() {
        return allSongs;
    }

    /**
     * Builder for {@link AudioStore}
     */
    public static class Builder {

        private Map<String, List<Song>> songsByAlbums;
        private Map<String, List<Song>> songsByArtists;
        private List<Song> allSongs;

        /**
         * Default constructor.
         */
        public Builder() {
            songsByAlbums = new HashMap<>();
            songsByArtists = new HashMap<>();
            allSongs = new ArrayList<>();
        }

        /**
         * Adds a song to the store.
         *
         * @param song the {@link Song} to add
         * @return {@link Builder}
         */
        public Builder addSong(final Song song) {
            addToAlbumsMap(song);
            addToArtistsMap(song);
            addToSongsList(song);
            return this;
        }

        /**
         * Builds a new {@link Song} instance.
         *
         * @return {@link AudioStore}
         */
        public AudioStore build() {
            return new AudioStore(this);
        }

        private void addToAlbumsMap(final Song song) {
            populateMap(song, song.getAlbum(), songsByAlbums);
        }

        private void addToArtistsMap(final Song song) {
            populateMap(song, song.getArtist(), songsByArtists);
        }

        private void addToSongsList(final Song song) {
            allSongs.add(song);
        }

        private void populateMap(final Song song, final String key, final Map<String, List<Song>> mapToPopulate) {
            if (mapToPopulate.containsKey(key)) {
                mapToPopulate.get(key).add(song);
            } else {
                mapToPopulate.put(key, initializeListWithValue(song));
            }
        }

        private List<Song> initializeListWithValue(final Song song) {
            return new ArrayList<Song>() {
                {
                    add(song);
                }
            };
        }

    }

}
