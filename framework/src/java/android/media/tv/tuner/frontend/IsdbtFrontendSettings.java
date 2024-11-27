package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class IsdbtFrontendSettings extends android.media.tv.tuner.frontend.FrontendSettings {
    public static final int BANDWIDTH_6MHZ = 8;
    public static final int BANDWIDTH_7MHZ = 4;
    public static final int BANDWIDTH_8MHZ = 2;
    public static final int BANDWIDTH_AUTO = 1;
    public static final int BANDWIDTH_UNDEFINED = 0;
    public static final int MODE_1 = 2;
    public static final int MODE_2 = 4;
    public static final int MODE_3 = 8;
    public static final int MODE_AUTO = 1;
    public static final int MODE_UNDEFINED = 0;
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_16QAM = 8;
    public static final int MODULATION_MOD_64QAM = 16;
    public static final int MODULATION_MOD_DQPSK = 2;
    public static final int MODULATION_MOD_QPSK = 4;
    public static final int MODULATION_UNDEFINED = 0;
    public static final int PARTIAL_RECEPTION_FLAG_FALSE = 2;
    public static final int PARTIAL_RECEPTION_FLAG_TRUE = 4;
    public static final int PARTIAL_RECEPTION_FLAG_UNDEFINED = 0;
    private static final java.lang.String TAG = "IsdbtFrontendSettings";
    public static final int TIME_INTERLEAVE_MODE_1_0 = 2;
    public static final int TIME_INTERLEAVE_MODE_1_16 = 16;
    public static final int TIME_INTERLEAVE_MODE_1_4 = 4;
    public static final int TIME_INTERLEAVE_MODE_1_8 = 8;
    public static final int TIME_INTERLEAVE_MODE_2_0 = 32;
    public static final int TIME_INTERLEAVE_MODE_2_2 = 64;
    public static final int TIME_INTERLEAVE_MODE_2_4 = 128;
    public static final int TIME_INTERLEAVE_MODE_2_8 = 256;
    public static final int TIME_INTERLEAVE_MODE_3_0 = 512;
    public static final int TIME_INTERLEAVE_MODE_3_1 = 1024;
    public static final int TIME_INTERLEAVE_MODE_3_2 = 2048;
    public static final int TIME_INTERLEAVE_MODE_3_4 = 4096;
    public static final int TIME_INTERLEAVE_MODE_AUTO = 1;
    public static final int TIME_INTERLEAVE_MODE_UNDEFINED = 0;
    private final int mBandwidth;
    private final int mGuardInterval;
    private final android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[] mLayerSettings;
    private final int mMode;
    private final int mPartialReceptionFlag;
    private final int mServiceAreaId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Bandwidth {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PartialReceptionFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeInterleaveMode {
    }

    private IsdbtFrontendSettings(long j, int i, int i2, int i3, int i4, android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[] isdbtLayerSettingsArr, int i5) {
        super(j);
        this.mBandwidth = i;
        this.mMode = i2;
        this.mGuardInterval = i3;
        this.mServiceAreaId = i4;
        this.mLayerSettings = new android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[isdbtLayerSettingsArr.length];
        for (int i6 = 0; i6 < isdbtLayerSettingsArr.length; i6++) {
            this.mLayerSettings[i6] = isdbtLayerSettingsArr[i6];
        }
        this.mPartialReceptionFlag = i5;
    }

    @java.lang.Deprecated
    public int getModulation() {
        if (!android.media.tv.tuner.TunerVersionChecker.isHigherOrEqualVersionTo(131072) && this.mLayerSettings.length > 0) {
            return this.mLayerSettings[0].getModulation();
        }
        return 0;
    }

    public int getBandwidth() {
        return this.mBandwidth;
    }

    public int getMode() {
        return this.mMode;
    }

    @java.lang.Deprecated
    public int getCodeRate() {
        if (!android.media.tv.tuner.TunerVersionChecker.isHigherOrEqualVersionTo(131072) && this.mLayerSettings.length > 0) {
            return this.mLayerSettings[0].getCodeRate();
        }
        return 0;
    }

    public int getGuardInterval() {
        return this.mGuardInterval;
    }

    public int getServiceAreaId() {
        return this.mServiceAreaId;
    }

    public android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[] getLayerSettings() {
        return this.mLayerSettings;
    }

    public int getPartialReceptionFlag() {
        return this.mPartialReceptionFlag;
    }

    public static android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder builder() {
        return new android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder();
    }

    public static class Builder {
        private int mBandwidth;
        private long mFrequency;
        private int mGuardInterval;
        private android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[] mLayerSettings;
        private int mMode;
        private int mPartialReceptionFlag;
        private int mServiceAreaId;

        private Builder() {
            this.mFrequency = 0L;
            this.mBandwidth = 0;
            this.mMode = 0;
            this.mGuardInterval = 0;
            this.mServiceAreaId = 0;
            this.mLayerSettings = new android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[0];
            this.mPartialReceptionFlag = 0;
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setModulation(int i) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setModulation")) {
                android.util.Log.d(android.media.tv.tuner.frontend.IsdbtFrontendSettings.TAG, "Use IsdbtLayerSettings on HAL 2.0 or higher");
            } else {
                android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder builder = android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.builder();
                builder.setModulation(i);
                if (this.mLayerSettings.length == 0) {
                    this.mLayerSettings = new android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[1];
                } else {
                    builder.setCodeRate(this.mLayerSettings[0].getCodeRate());
                }
                this.mLayerSettings[0] = builder.build();
            }
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setBandwidth(int i) {
            this.mBandwidth = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setMode(int i) {
            this.mMode = i;
            return this;
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setCodeRate(int i) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setModulation")) {
                android.util.Log.d(android.media.tv.tuner.frontend.IsdbtFrontendSettings.TAG, "Use IsdbtLayerSettings on HAL 2.0 or higher");
            } else {
                android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder builder = android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.builder();
                builder.setCodeRate(i);
                if (this.mLayerSettings.length == 0) {
                    this.mLayerSettings = new android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[1];
                } else {
                    builder.setModulation(this.mLayerSettings[0].getModulation());
                }
                this.mLayerSettings[0] = builder.build();
            }
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setGuardInterval(int i) {
            this.mGuardInterval = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setServiceAreaId(int i) {
            this.mServiceAreaId = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setLayerSettings(android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[] isdbtLayerSettingsArr) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setLayerSettings")) {
                this.mLayerSettings = new android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings[isdbtLayerSettingsArr.length];
                for (int i = 0; i < isdbtLayerSettingsArr.length; i++) {
                    this.mLayerSettings[i] = isdbtLayerSettingsArr[i];
                }
            }
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings.Builder setPartialReceptionFlag(int i) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setPartialReceptionFlag")) {
                this.mPartialReceptionFlag = i;
            }
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbtFrontendSettings build() {
            return new android.media.tv.tuner.frontend.IsdbtFrontendSettings(this.mFrequency, this.mBandwidth, this.mMode, this.mGuardInterval, this.mServiceAreaId, this.mLayerSettings, this.mPartialReceptionFlag);
        }
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 9;
    }

    public static final class IsdbtLayerSettings {
        private final int mCodeRate;
        private final int mModulation;
        private final int mNumOfSegments;
        private final int mTimeInterleaveMode;

        private IsdbtLayerSettings(int i, int i2, int i3, int i4) {
            this.mModulation = i;
            this.mTimeInterleaveMode = i2;
            this.mCodeRate = i3;
            this.mNumOfSegments = i4;
        }

        public int getModulation() {
            return this.mModulation;
        }

        public int getTimeInterleaveMode() {
            return this.mTimeInterleaveMode;
        }

        public int getCodeRate() {
            return this.mCodeRate;
        }

        public int getNumberOfSegments() {
            return this.mNumOfSegments;
        }

        public static android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder builder() {
            return new android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder();
        }

        public static final class Builder {
            private int mCodeRate;
            private int mModulation;
            private int mNumOfSegments;
            private int mTimeInterleaveMode;

            private Builder() {
                this.mModulation = 0;
                this.mTimeInterleaveMode = 0;
                this.mCodeRate = 0;
                this.mNumOfSegments = 0;
            }

            public android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder setModulation(int i) {
                this.mModulation = i;
                return this;
            }

            public android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder setTimeInterleaveMode(int i) {
                this.mTimeInterleaveMode = i;
                return this;
            }

            public android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder setCodeRate(int i) {
                this.mCodeRate = i;
                return this;
            }

            public android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings.Builder setNumberOfSegments(int i) {
                this.mNumOfSegments = i;
                return this;
            }

            public android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings build() {
                return new android.media.tv.tuner.frontend.IsdbtFrontendSettings.IsdbtLayerSettings(this.mModulation, this.mTimeInterleaveMode, this.mCodeRate, this.mNumOfSegments);
            }
        }
    }
}
