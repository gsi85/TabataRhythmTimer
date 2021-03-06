package com.sisa.tabata.media.domain;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Domain object, storing {@link Song} in categorized manner.
 *
 * @author Laszlo Sisa
 */
public final class AudioStore {

    private final Map<String, List<Song>> songsByAlbums;
    private final Map<String, List<Song>> songsByArtists;
    private final List<Song> allSongs;
    private final List<Playlist> playlists;

    private AudioStore(Builder builder) {
        sort(builder.playlists);
        sort(builder.allSongs);
        songsByAlbums = unmodifiableMap(builder.songsByAlbums);
        songsByArtists = unmodifiableMap(builder.songsByArtists);
        allSongs = unmodifiableList(builder.allSongs);
        playlists = unmodifiableList(builder.playlists);
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

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Searches for a {@link Song} with the given {@code trackId}.
     *
     * @param trackId the track id to search for
     * @return {@link Song} if song exists with the trackId, null otherwise
     */
    public Song getSongByTrackId(final long trackId) {
        Song song = null;
        boolean found = false;
        Iterator<Song> iterator = allSongs.iterator();
        while (!found && iterator.hasNext()) {
            Song currentSong = iterator.next();
            if (currentSong.getTrackId() == trackId) {
                song = currentSong;
                found = true;
            }
        }
        return song;
    }

    /**
     * Searches for a {@link Playlist} with the given {@code playlistId}.
     *
     * @param playlistId the playlist id to search for
     * @return {@link Playlist} if playlist exists with the playlistId, null otherwise
     */
    public Playlist getPlaylistById(final long playlistId) {
        Playlist playlist = null;
        boolean found = false;
        Iterator<Playlist> iterator = playlists.iterator();
        while (!found && iterator.hasNext()) {
            Playlist currentPlaylist = iterator.next();
            if (currentPlaylist.getPlaylistId() == playlistId) {
                playlist = currentPlaylist;
                found = true;
            }
        }
        return playlist;
    }

    /**
     * Builder for {@link AudioStore}.
     */
    public static class Builder {

        private Map<String, List<Song>> songsByAlbums;
        private Map<String, List<Song>> songsByArtists;
        private List<Song> allSongs;
        private List<Playlist> playlists;

        /**
         * Default constructor.
         */
        public Builder() {
            songsByAlbums = new HashMap<>();
            songsByArtists = new HashMap<>();
            allSongs = new ArrayList<>();
        }

        /**
         * Adds a list of songs to the store.
         *
         * @param songs the list of {@link Song} to add
         * @return {@link Builder}
         */
        public Builder addSongs(final List<Song> songs) {
            for (Song song : songs) {
                addToAlbumsMap(song);
                addToArtistsMap(song);
                addToSongsList(song);
            }
            return this;
        }

        /**
         * Sets the list of {@link Playlist}.
         *
         * @param playlists the list of {@link Playlist}
         * @return {@link Builder}
         */
        public Builder withPlaylists(final List<Playlist> playlists) {
            this.playlists = playlists;
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
