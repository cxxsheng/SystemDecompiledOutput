package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AvSettings extends android.media.tv.tuner.filter.Settings {
    public static final int AUDIO_STREAM_TYPE_AAC = 6;
    public static final int AUDIO_STREAM_TYPE_AAC_ADTS = 16;
    public static final int AUDIO_STREAM_TYPE_AAC_HE_ADTS = 18;
    public static final int AUDIO_STREAM_TYPE_AAC_HE_LATM = 19;
    public static final int AUDIO_STREAM_TYPE_AAC_LATM = 17;
    public static final int AUDIO_STREAM_TYPE_AC3 = 7;
    public static final int AUDIO_STREAM_TYPE_AC4 = 9;
    public static final int AUDIO_STREAM_TYPE_DRA = 15;
    public static final int AUDIO_STREAM_TYPE_DTS = 10;
    public static final int AUDIO_STREAM_TYPE_DTS_HD = 11;
    public static final int AUDIO_STREAM_TYPE_EAC3 = 8;
    public static final int AUDIO_STREAM_TYPE_MP3 = 2;
    public static final int AUDIO_STREAM_TYPE_MPEG1 = 3;
    public static final int AUDIO_STREAM_TYPE_MPEG2 = 4;
    public static final int AUDIO_STREAM_TYPE_MPEGH = 5;
    public static final int AUDIO_STREAM_TYPE_OPUS = 13;
    public static final int AUDIO_STREAM_TYPE_PCM = 1;
    public static final int AUDIO_STREAM_TYPE_UNDEFINED = 0;
    public static final int AUDIO_STREAM_TYPE_VORBIS = 14;
    public static final int AUDIO_STREAM_TYPE_WMA = 12;
    public static final int VIDEO_STREAM_TYPE_AV1 = 10;
    public static final int VIDEO_STREAM_TYPE_AVC = 5;
    public static final int VIDEO_STREAM_TYPE_AVS = 11;
    public static final int VIDEO_STREAM_TYPE_AVS2 = 12;
    public static final int VIDEO_STREAM_TYPE_HEVC = 6;
    public static final int VIDEO_STREAM_TYPE_MPEG1 = 2;
    public static final int VIDEO_STREAM_TYPE_MPEG2 = 3;
    public static final int VIDEO_STREAM_TYPE_MPEG4P2 = 4;
    public static final int VIDEO_STREAM_TYPE_RESERVED = 1;
    public static final int VIDEO_STREAM_TYPE_UNDEFINED = 0;
    public static final int VIDEO_STREAM_TYPE_VC1 = 7;
    public static final int VIDEO_STREAM_TYPE_VP8 = 8;
    public static final int VIDEO_STREAM_TYPE_VP9 = 9;
    public static final int VIDEO_STREAM_TYPE_VVC = 13;
    private int mAudioStreamType;
    private final boolean mIsPassthrough;
    private final boolean mUseSecureMemory;
    private int mVideoStreamType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioStreamType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VideoStreamType {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private AvSettings(int i, boolean z, boolean z2, int i2, int i3, boolean z3) {
        super(android.media.tv.tuner.TunerUtils.getFilterSubtype(i, r2));
        int i4;
        if (z) {
            i4 = 3;
        } else {
            i4 = 4;
        }
        this.mAudioStreamType = 0;
        this.mVideoStreamType = 0;
        this.mIsPassthrough = z2;
        this.mAudioStreamType = i2;
        this.mVideoStreamType = i3;
        this.mUseSecureMemory = z3;
    }

    public boolean isPassthrough() {
        return this.mIsPassthrough;
    }

    public int getAudioStreamType() {
        return this.mAudioStreamType;
    }

    public int getVideoStreamType() {
        return this.mVideoStreamType;
    }

    public boolean useSecureMemory() {
        return this.mUseSecureMemory;
    }

    public static android.media.tv.tuner.filter.AvSettings.Builder builder(int i, boolean z) {
        return new android.media.tv.tuner.filter.AvSettings.Builder(i, z);
    }

    public static class Builder {
        private int mAudioStreamType;
        private final boolean mIsAudio;
        private boolean mIsPassthrough;
        private final int mMainType;
        boolean mUseSecureMemory;
        private int mVideoStreamType;

        private Builder(int i, boolean z) {
            this.mIsPassthrough = false;
            this.mAudioStreamType = 0;
            this.mVideoStreamType = 0;
            this.mUseSecureMemory = false;
            this.mMainType = i;
            this.mIsAudio = z;
        }

        public android.media.tv.tuner.filter.AvSettings.Builder setPassthrough(boolean z) {
            this.mIsPassthrough = z;
            return this;
        }

        public android.media.tv.tuner.filter.AvSettings.Builder setAudioStreamType(int i) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setAudioStreamType") && this.mIsAudio) {
                this.mAudioStreamType = i;
                this.mVideoStreamType = 0;
            }
            return this;
        }

        public android.media.tv.tuner.filter.AvSettings.Builder setVideoStreamType(int i) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setVideoStreamType") && !this.mIsAudio) {
                this.mVideoStreamType = i;
                this.mAudioStreamType = 0;
            }
            return this;
        }

        public android.media.tv.tuner.filter.AvSettings.Builder setUseSecureMemory(boolean z) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setSecureMemory")) {
                this.mUseSecureMemory = z;
            }
            return this;
        }

        public android.media.tv.tuner.filter.AvSettings build() {
            return new android.media.tv.tuner.filter.AvSettings(this.mMainType, this.mIsAudio, this.mIsPassthrough, this.mAudioStreamType, this.mVideoStreamType, this.mUseSecureMemory);
        }
    }
}
