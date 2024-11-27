package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class DtmbFrontendSettings extends android.media.tv.tuner.frontend.FrontendSettings {
    public static final int BANDWIDTH_6MHZ = 4;
    public static final int BANDWIDTH_8MHZ = 2;
    public static final int BANDWIDTH_AUTO = 1;
    public static final int BANDWIDTH_UNDEFINED = 0;
    public static final int CODERATE_2_5 = 2;
    public static final int CODERATE_3_5 = 4;
    public static final int CODERATE_4_5 = 8;
    public static final int CODERATE_AUTO = 1;
    public static final int CODERATE_UNDEFINED = 0;
    public static final int GUARD_INTERVAL_AUTO = 1;
    public static final int GUARD_INTERVAL_PN_420_CONST = 16;
    public static final int GUARD_INTERVAL_PN_420_VARIOUS = 2;
    public static final int GUARD_INTERVAL_PN_595_CONST = 4;
    public static final int GUARD_INTERVAL_PN_945_CONST = 32;
    public static final int GUARD_INTERVAL_PN_945_VARIOUS = 8;
    public static final int GUARD_INTERVAL_PN_RESERVED = 64;
    public static final int GUARD_INTERVAL_UNDEFINED = 0;
    public static final int MODULATION_CONSTELLATION_16QAM = 8;
    public static final int MODULATION_CONSTELLATION_32QAM = 16;
    public static final int MODULATION_CONSTELLATION_4QAM = 2;
    public static final int MODULATION_CONSTELLATION_4QAM_NR = 4;
    public static final int MODULATION_CONSTELLATION_64QAM = 32;
    public static final int MODULATION_CONSTELLATION_AUTO = 1;
    public static final int MODULATION_CONSTELLATION_UNDEFINED = 0;
    public static final int TIME_INTERLEAVE_MODE_AUTO = 1;
    public static final int TIME_INTERLEAVE_MODE_TIMER_INT_240 = 2;
    public static final int TIME_INTERLEAVE_MODE_TIMER_INT_720 = 4;
    public static final int TIME_INTERLEAVE_MODE_UNDEFINED = 0;
    public static final int TRANSMISSION_MODE_AUTO = 1;
    public static final int TRANSMISSION_MODE_C1 = 2;
    public static final int TRANSMISSION_MODE_C3780 = 4;
    public static final int TRANSMISSION_MODE_UNDEFINED = 0;
    private final int mBandwidth;
    private final int mCodeRate;
    private final int mGuardInterval;
    private final int mModulation;
    private final int mTimeInterleaveMode;
    private final int mTransmissionMode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Bandwidth {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CodeRate {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GuardInterval {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeInterleaveMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransmissionMode {
    }

    private DtmbFrontendSettings(long j, int i, int i2, int i3, int i4, int i5, int i6) {
        super(j);
        this.mModulation = i;
        this.mCodeRate = i2;
        this.mTransmissionMode = i3;
        this.mGuardInterval = i4;
        this.mTimeInterleaveMode = i5;
        this.mBandwidth = i6;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public int getCodeRate() {
        return this.mCodeRate;
    }

    public int getTransmissionMode() {
        return this.mTransmissionMode;
    }

    public int getBandwidth() {
        return this.mBandwidth;
    }

    public int getTimeInterleaveMode() {
        return this.mTimeInterleaveMode;
    }

    public int getGuardInterval() {
        return this.mGuardInterval;
    }

    public static android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder builder() {
        return new android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder();
    }

    public static final class Builder {
        private int mBandwidth;
        private int mCodeRate;
        private long mFrequency;
        private int mGuardInterval;
        private int mModulation;
        private int mTimeInterleaveMode;
        private int mTransmissionMode;

        private Builder() {
            this.mFrequency = 0L;
            this.mModulation = 0;
            this.mCodeRate = 0;
            this.mTransmissionMode = 0;
            this.mBandwidth = 0;
            this.mTimeInterleaveMode = 0;
            this.mGuardInterval = 0;
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setModulation(int i) {
            this.mModulation = i;
            return this;
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setCodeRate(int i) {
            this.mCodeRate = i;
            return this;
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setBandwidth(int i) {
            this.mBandwidth = i;
            return this;
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setTimeInterleaveMode(int i) {
            this.mTimeInterleaveMode = i;
            return this;
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setGuardInterval(int i) {
            this.mGuardInterval = i;
            return this;
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings.Builder setTransmissionMode(int i) {
            this.mTransmissionMode = i;
            return this;
        }

        public android.media.tv.tuner.frontend.DtmbFrontendSettings build() {
            return new android.media.tv.tuner.frontend.DtmbFrontendSettings(this.mFrequency, this.mModulation, this.mCodeRate, this.mTransmissionMode, this.mGuardInterval, this.mTimeInterleaveMode, this.mBandwidth);
        }
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 10;
    }
}
