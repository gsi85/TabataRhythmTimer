package com.sisa.tabata.media.domain;

import static com.sisa.tabata.validation.Assert.isInstanceOf;

import android.support.annotation.NonNull;

/**
 * Domain object representing an audio playlist.
 *
 * @author Laszlo Sisa
 */
public final class Playlist implements Comparable {

    private final long playlistId;
    private final String name;
    private final String dataStream;

    private Playlist(final Builder builder) {
        playlistId = builder.playlistId;
        name = builder.name;
        dataStream = builder.dataStream;
    }

    public long getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public String getDataStream() {
        return dataStream;
    }

    @Override
    public int compareTo(@NonNull final Object another) {
        isInstanceOf(Playlist.class, another, "can't compare to non Playlist type!");
        return getName().compareTo(((Playlist) another).getName());
    }

    /**
     * Builder for {@link Playlist}.
     */
    public static class Builder {

        private long playlistId;
        private String name;
        private String dataStream;

        /**
         * Sets the playlist id.
         *
         * @param playlistId playlist id
         * @return {@link Builder}
         */
        public Builder withPlaylistId(final long playlistId) {
            this.playlistId = playlistId;
            return this;
        }

        /**
         * Sets the playlist name.
         *
         * @param name the playlist name
         * @return {@link Builder}
         */
        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the playlist data stream.
         *
         * @param dataStream the data stream
         * @return {@link Builder}
         */
        public Builder withDataStream(final String dataStream) {
            this.dataStream = dataStream;
            return this;
        }

        /**
         * Builds a new {@link Playlist} instance.
         *
         * @return {@link Playlist}
         */
        public Playlist build() {
            return new Playlist(this);
        }

    }

}
