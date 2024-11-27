package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Atsc3FrontendSettings extends android.media.tv.tuner.frontend.FrontendSettings {
    public static final int BANDWIDTH_AUTO = 1;
    public static final int BANDWIDTH_BANDWIDTH_6MHZ = 2;
    public static final int BANDWIDTH_BANDWIDTH_7MHZ = 4;
    public static final int BANDWIDTH_BANDWIDTH_8MHZ = 8;
    public static final int BANDWIDTH_UNDEFINED = 0;
    public static final int CODERATE_10_15 = 512;
    public static final int CODERATE_11_15 = 1024;
    public static final int CODERATE_12_15 = 2048;
    public static final int CODERATE_13_15 = 4096;
    public static final int CODERATE_2_15 = 2;
    public static final int CODERATE_3_15 = 4;
    public static final int CODERATE_4_15 = 8;
    public static final int CODERATE_5_15 = 16;
    public static final int CODERATE_6_15 = 32;
    public static final int CODERATE_7_15 = 64;
    public static final int CODERATE_8_15 = 128;
    public static final int CODERATE_9_15 = 256;
    public static final int CODERATE_AUTO = 1;
    public static final int CODERATE_UNDEFINED = 0;
    public static final int DEMOD_OUTPUT_FORMAT_ATSC3_LINKLAYER_PACKET = 1;
    public static final int DEMOD_OUTPUT_FORMAT_BASEBAND_PACKET = 2;
    public static final int DEMOD_OUTPUT_FORMAT_UNDEFINED = 0;
    public static final int FEC_AUTO = 1;
    public static final int FEC_BCH_LDPC_16K = 2;
    public static final int FEC_BCH_LDPC_64K = 4;
    public static final int FEC_CRC_LDPC_16K = 8;
    public static final int FEC_CRC_LDPC_64K = 16;
    public static final int FEC_LDPC_16K = 32;
    public static final int FEC_LDPC_64K = 64;
    public static final int FEC_UNDEFINED = 0;
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_1024QAM = 32;
    public static final int MODULATION_MOD_16QAM = 4;
    public static final int MODULATION_MOD_256QAM = 16;
    public static final int MODULATION_MOD_4096QAM = 64;
    public static final int MODULATION_MOD_64QAM = 8;
    public static final int MODULATION_MOD_QPSK = 2;
    public static final int MODULATION_UNDEFINED = 0;
    public static final int TIME_INTERLEAVE_MODE_AUTO = 1;
    public static final int TIME_INTERLEAVE_MODE_CTI = 2;
    public static final int TIME_INTERLEAVE_MODE_HTI = 4;
    public static final int TIME_INTERLEAVE_MODE_UNDEFINED = 0;
    private final int mBandwidth;
    private final int mDemodOutputFormat;
    private final android.media.tv.tuner.frontend.Atsc3PlpSettings[] mPlpSettings;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Bandwidth {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CodeRate {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DemodOutputFormat {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Fec {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeInterleaveMode {
    }

    private Atsc3FrontendSettings(long j, int i, int i2, android.media.tv.tuner.frontend.Atsc3PlpSettings[] atsc3PlpSettingsArr) {
        super(j);
        this.mBandwidth = i;
        this.mDemodOutputFormat = i2;
        this.mPlpSettings = atsc3PlpSettingsArr;
    }

    public int getBandwidth() {
        return this.mBandwidth;
    }

    public int getDemodOutputFormat() {
        return this.mDemodOutputFormat;
    }

    public android.media.tv.tuner.frontend.Atsc3PlpSettings[] getPlpSettings() {
        return this.mPlpSettings;
    }

    public static android.media.tv.tuner.frontend.Atsc3FrontendSettings.Builder builder() {
        return new android.media.tv.tuner.frontend.Atsc3FrontendSettings.Builder();
    }

    public static class Builder {
        private int mBandwidth;
        private int mDemodOutputFormat;
        private long mFrequency;
        private android.media.tv.tuner.frontend.Atsc3PlpSettings[] mPlpSettings;

        private Builder() {
            this.mFrequency = 0L;
            this.mBandwidth = 0;
            this.mDemodOutputFormat = 0;
            this.mPlpSettings = new android.media.tv.tuner.frontend.Atsc3PlpSettings[0];
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.Atsc3FrontendSettings.Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public android.media.tv.tuner.frontend.Atsc3FrontendSettings.Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3FrontendSettings.Builder setBandwidth(int i) {
            this.mBandwidth = i;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3FrontendSettings.Builder setDemodOutputFormat(int i) {
            this.mDemodOutputFormat = i;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3FrontendSettings.Builder setPlpSettings(android.media.tv.tuner.frontend.Atsc3PlpSettings[] atsc3PlpSettingsArr) {
            this.mPlpSettings = atsc3PlpSettingsArr;
            return this;
        }

        public android.media.tv.tuner.frontend.Atsc3FrontendSettings build() {
            return new android.media.tv.tuner.frontend.Atsc3FrontendSettings(this.mFrequency, this.mBandwidth, this.mDemodOutputFormat, this.mPlpSettings);
        }
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 3;
    }
}
