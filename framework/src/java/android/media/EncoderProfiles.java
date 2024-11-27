package android.media;

/* loaded from: classes2.dex */
public final class EncoderProfiles {
    private java.util.List<android.media.EncoderProfiles.AudioProfile> audioProfiles;
    private int durationSecs;
    private int fileFormat;
    private java.util.List<android.media.EncoderProfiles.VideoProfile> videoProfiles;

    public int getDefaultDurationSeconds() {
        return this.durationSecs;
    }

    public int getRecommendedFileFormat() {
        return this.fileFormat;
    }

    public static final class VideoProfile {
        public static final int HDR_DOLBY_VISION = 4;
        public static final int HDR_HDR10 = 2;
        public static final int HDR_HDR10PLUS = 3;
        public static final int HDR_HLG = 1;
        public static final int HDR_NONE = 0;
        public static final int YUV_420 = 0;
        public static final int YUV_422 = 1;
        public static final int YUV_444 = 2;
        private int bitDepth;
        private int bitrate;
        private int chromaSubsampling;
        private int codec;
        private int frameRate;
        private int hdrFormat;
        private int height;
        private int profile;
        private int width;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ChromaSubsampling {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface HdrFormat {
        }

        public int getCodec() {
            return this.codec;
        }

        public java.lang.String getMediaType() {
            if (this.codec == 1) {
                return "video/3gpp";
            }
            if (this.codec == 2) {
                return android.media.MediaFormat.MIMETYPE_VIDEO_AVC;
            }
            if (this.codec == 3) {
                return android.media.MediaFormat.MIMETYPE_VIDEO_MPEG4;
            }
            if (this.codec == 4) {
                return android.media.MediaFormat.MIMETYPE_VIDEO_VP8;
            }
            if (this.codec == 5) {
                return android.media.MediaFormat.MIMETYPE_VIDEO_HEVC;
            }
            if (this.codec == 6) {
                return android.media.MediaFormat.MIMETYPE_VIDEO_VP9;
            }
            if (this.codec == 7) {
                return android.media.MediaFormat.MIMETYPE_VIDEO_DOLBY_VISION;
            }
            if (this.codec == 8) {
                return android.media.MediaFormat.MIMETYPE_VIDEO_AV1;
            }
            throw new java.lang.RuntimeException("Unknown codec");
        }

        public int getBitrate() {
            return this.bitrate;
        }

        public int getFrameRate() {
            return this.frameRate;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getProfile() {
            return this.profile;
        }

        public int getBitDepth() {
            return this.bitDepth;
        }

        public int getChromaSubsampling() {
            return this.chromaSubsampling;
        }

        public int getHdrFormat() {
            return this.hdrFormat;
        }

        VideoProfile(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.codec = i;
            this.width = i2;
            this.height = i3;
            this.frameRate = i4;
            this.bitrate = i5;
            this.profile = i6;
            this.chromaSubsampling = i7;
            this.bitDepth = i8;
            this.hdrFormat = i9;
        }

        VideoProfile(int i, int i2, int i3, int i4, int i5, int i6) {
            this(i, i2, i3, i4, i5, i6, 0, 8, 0);
        }
    }

    public java.util.List<android.media.EncoderProfiles.AudioProfile> getAudioProfiles() {
        return this.audioProfiles;
    }

    public java.util.List<android.media.EncoderProfiles.VideoProfile> getVideoProfiles() {
        return this.videoProfiles;
    }

    public static final class AudioProfile {
        private int bitrate;
        private int channels;
        private int codec;
        private int profile;
        private int sampleRate;

        public int getCodec() {
            return this.codec;
        }

        public java.lang.String getMediaType() {
            if (this.codec == 1) {
                return "audio/3gpp";
            }
            if (this.codec == 2) {
                return android.media.MediaFormat.MIMETYPE_AUDIO_AMR_WB;
            }
            if (this.codec == 3 || this.codec == 4 || this.codec == 5) {
                return android.media.MediaFormat.MIMETYPE_AUDIO_AAC;
            }
            if (this.codec == 6) {
                return android.media.MediaFormat.MIMETYPE_AUDIO_VORBIS;
            }
            if (this.codec == 7) {
                return android.media.MediaFormat.MIMETYPE_AUDIO_OPUS;
            }
            throw new java.lang.RuntimeException("Unknown codec");
        }

        public int getBitrate() {
            return this.bitrate;
        }

        public int getSampleRate() {
            return this.sampleRate;
        }

        public int getChannels() {
            return this.channels;
        }

        public int getProfile() {
            if (this.codec == 3) {
                return 1;
            }
            if (this.codec == 4) {
                return 5;
            }
            if (this.codec == 5) {
                return 39;
            }
            return this.profile;
        }

        AudioProfile(int i, int i2, int i3, int i4, int i5) {
            this.codec = i;
            this.channels = i2;
            this.sampleRate = i3;
            this.bitrate = i4;
            this.profile = i5;
        }
    }

    EncoderProfiles(int i, int i2, android.media.EncoderProfiles.VideoProfile[] videoProfileArr, android.media.EncoderProfiles.AudioProfile[] audioProfileArr) {
        this.durationSecs = i;
        this.fileFormat = i2;
        this.videoProfiles = java.util.Collections.unmodifiableList(java.util.Arrays.asList(videoProfileArr));
        this.audioProfiles = java.util.Collections.unmodifiableList(java.util.Arrays.asList(audioProfileArr));
    }
}
