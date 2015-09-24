package com.sisa.tabata.media.domain;

/**
 * Immutable domain object representing a song.
 *
 * @author Laszlo Sisa
 */
public final class Song {

    private final String trackId;
    private final String trackNumber;
    private final String title;
    private final String artist;
    private final String album;
    private final String dataStream;

    private Song(final Builder builder) {
        trackId = builder.trackId;
        trackNumber = builder.trackNumber;
        title = builder.title;
        artist = builder.artist;
        album = builder.album;
        dataStream = builder.dataStream;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getDataStream() {
        return dataStream;
    }

    /**
     * Builder for {@link Song}
     */
    public static class Builder {

        private String trackId;
        private String trackNumber;
        private String title;
        private String artist;
        private String album;
        private String dataStream;

        /**
         * Sets the track id.
         *
         * @param trackId track id
         * @return {@link Builder}
         */
        public Builder withTrackid(final String trackId) {
            this.trackId = trackId;
            return this;
        }

        /**
         * Sets the track number.
         *
         * @param trackNumber track number
         * @return {@link Builder}
         */
        public Builder withTracknumber(final String trackNumber) {
            this.trackNumber = trackNumber;
            return this;
        }

        /**
         * Sets the title.
         *
         * @param title title
         * @return {@link Builder}
         */
        public Builder withTitle(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the artist.
         *
         * @param artist artist
         * @return {@link Builder}
         */
        public Builder withArtist(final String artist) {
            this.artist = artist;
            return this;
        }

        /**
         * Sets the album.
         *
         * @param album album
         * @return {@link Builder}
         */
        public Builder withAlbum(final String album) {
            this.album = album;
            return this;
        }

        /**
         * Sets the data strea,.
         *
         * @param dataStream data stream
         * @return {@link Builder}
         */
        public Builder withDataStream(final String dataStream) {
            this.dataStream = dataStream;
            return this;
        }

        /**
         * Builds a new {@Song}.
         *
         * @return {@link Song}
         */
        public Song build() {
            return new Song(this);
        }

    }

}
