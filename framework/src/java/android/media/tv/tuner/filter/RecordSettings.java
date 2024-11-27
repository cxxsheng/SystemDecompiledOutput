package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class RecordSettings extends android.media.tv.tuner.filter.Settings {
    public static final int INDEX_TYPE_NONE = 0;
    public static final int INDEX_TYPE_SC = 1;
    public static final int INDEX_TYPE_SC_AVC = 3;
    public static final int INDEX_TYPE_SC_HEVC = 2;
    public static final int INDEX_TYPE_SC_VVC = 4;
    public static final int MPT_INDEX_AUDIO = 262144;
    public static final int MPT_INDEX_MPT = 65536;
    public static final int MPT_INDEX_TIMESTAMP_TARGET_AUDIO = 1048576;
    public static final int MPT_INDEX_TIMESTAMP_TARGET_VIDEO = 524288;
    public static final int MPT_INDEX_VIDEO = 131072;
    public static final int SC_HEVC_INDEX_AUD = 2;
    public static final int SC_HEVC_INDEX_SLICE_BLA_N_LP = 16;
    public static final int SC_HEVC_INDEX_SLICE_BLA_W_RADL = 8;
    public static final int SC_HEVC_INDEX_SLICE_CE_BLA_W_LP = 4;
    public static final int SC_HEVC_INDEX_SLICE_IDR_N_LP = 64;
    public static final int SC_HEVC_INDEX_SLICE_IDR_W_RADL = 32;
    public static final int SC_HEVC_INDEX_SLICE_TRAIL_CRA = 128;
    public static final int SC_HEVC_INDEX_SPS = 1;
    public static final int SC_INDEX_B_FRAME = 4;
    public static final int SC_INDEX_B_SLICE = 64;
    public static final int SC_INDEX_I_FRAME = 1;
    public static final int SC_INDEX_I_SLICE = 16;
    public static final int SC_INDEX_P_FRAME = 2;
    public static final int SC_INDEX_P_SLICE = 32;
    public static final int SC_INDEX_SEQUENCE = 8;
    public static final int SC_INDEX_SI_SLICE = 128;
    public static final int SC_INDEX_SP_SLICE = 256;
    public static final int SC_VVC_INDEX_AUD = 64;
    public static final int SC_VVC_INDEX_SLICE_CRA = 4;
    public static final int SC_VVC_INDEX_SLICE_GDR = 8;
    public static final int SC_VVC_INDEX_SLICE_IDR_N_LP = 2;
    public static final int SC_VVC_INDEX_SLICE_IDR_W_RADL = 1;
    public static final int SC_VVC_INDEX_SPS = 32;
    public static final int SC_VVC_INDEX_VPS = 16;
    public static final int TS_INDEX_ADAPTATION_EXTENSION_FLAG = 4096;
    public static final int TS_INDEX_CHANGE_TO_EVEN_SCRAMBLED = 8;
    public static final int TS_INDEX_CHANGE_TO_NOT_SCRAMBLED = 4;
    public static final int TS_INDEX_CHANGE_TO_ODD_SCRAMBLED = 16;
    public static final int TS_INDEX_DISCONTINUITY_INDICATOR = 32;
    public static final int TS_INDEX_FIRST_PACKET = 1;
    public static final int TS_INDEX_INVALID = 0;
    public static final int TS_INDEX_OPCR_FLAG = 512;
    public static final int TS_INDEX_PAYLOAD_UNIT_START_INDICATOR = 2;
    public static final int TS_INDEX_PCR_FLAG = 256;
    public static final int TS_INDEX_PRIORITY_INDICATOR = 128;
    public static final int TS_INDEX_PRIVATE_DATA = 2048;
    public static final int TS_INDEX_RANDOM_ACCESS_INDICATOR = 64;
    public static final int TS_INDEX_SPLICING_POINT_FLAG = 1024;
    private final int mScIndexMask;
    private final int mScIndexType;
    private final int mTsIndexMask;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScHevcIndex {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScIndex {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScIndexMask {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScIndexType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScVvcIndex {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TsIndexMask {
    }

    private RecordSettings(int i, int i2, int i3, int i4) {
        super(android.media.tv.tuner.TunerUtils.getFilterSubtype(i, 6));
        this.mTsIndexMask = i2;
        this.mScIndexType = i3;
        this.mScIndexMask = i4;
    }

    public int getTsIndexMask() {
        return this.mTsIndexMask;
    }

    public int getScIndexType() {
        return this.mScIndexType;
    }

    public int getScIndexMask() {
        return this.mScIndexMask;
    }

    public static android.media.tv.tuner.filter.RecordSettings.Builder builder(int i) {
        return new android.media.tv.tuner.filter.RecordSettings.Builder(i);
    }

    public static class Builder {
        private final int mMainType;
        private int mScIndexMask;
        private int mScIndexType;
        private int mTsIndexMask;

        private Builder(int i) {
            this.mMainType = i;
        }

        public android.media.tv.tuner.filter.RecordSettings.Builder setTsIndexMask(int i) {
            this.mTsIndexMask = i;
            return this;
        }

        public android.media.tv.tuner.filter.RecordSettings.Builder setScIndexType(int i) {
            this.mScIndexType = i;
            return this;
        }

        public android.media.tv.tuner.filter.RecordSettings.Builder setScIndexMask(int i) {
            this.mScIndexMask = i;
            return this;
        }

        public android.media.tv.tuner.filter.RecordSettings build() {
            return new android.media.tv.tuner.filter.RecordSettings(this.mMainType, this.mTsIndexMask, this.mScIndexType, this.mScIndexMask);
        }
    }
}
