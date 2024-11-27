package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AnalogFrontendSettings extends android.media.tv.tuner.frontend.FrontendSettings {
    public static final int AFT_FLAG_FALSE = 2;
    public static final int AFT_FLAG_TRUE = 1;
    public static final int AFT_FLAG_UNDEFINED = 0;
    public static final int SIF_AUTO = 1;
    public static final int SIF_BG = 2;
    public static final int SIF_BG_A2 = 4;
    public static final int SIF_BG_NICAM = 8;
    public static final int SIF_DK = 32;
    public static final int SIF_DK1_A2 = 64;
    public static final int SIF_DK2_A2 = 128;
    public static final int SIF_DK3_A2 = 256;
    public static final int SIF_DK_NICAM = 512;
    public static final int SIF_I = 16;
    public static final int SIF_I_NICAM = 32768;
    public static final int SIF_L = 1024;
    public static final int SIF_L_NICAM = 65536;
    public static final int SIF_L_PRIME = 131072;
    public static final int SIF_M = 2048;
    public static final int SIF_M_A2 = 8192;
    public static final int SIF_M_BTSC = 4096;
    public static final int SIF_M_EIAJ = 16384;
    public static final int SIF_UNDEFINED = 0;
    public static final int SIGNAL_TYPE_AUTO = 1;
    public static final int SIGNAL_TYPE_NTSC = 32;
    public static final int SIGNAL_TYPE_NTSC_443 = 64;
    public static final int SIGNAL_TYPE_PAL = 2;
    public static final int SIGNAL_TYPE_PAL_60 = 16;
    public static final int SIGNAL_TYPE_PAL_M = 4;
    public static final int SIGNAL_TYPE_PAL_N = 8;
    public static final int SIGNAL_TYPE_SECAM = 128;
    public static final int SIGNAL_TYPE_UNDEFINED = 0;
    private final int mAftFlag;
    private final int mSifStandard;
    private final int mSignalType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AftFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SifStandard {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignalType {
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 1;
    }

    public int getSignalType() {
        return this.mSignalType;
    }

    public int getSifStandard() {
        return this.mSifStandard;
    }

    public int getAftFlag() {
        return this.mAftFlag;
    }

    public static android.media.tv.tuner.frontend.AnalogFrontendSettings.Builder builder() {
        return new android.media.tv.tuner.frontend.AnalogFrontendSettings.Builder();
    }

    private AnalogFrontendSettings(long j, int i, int i2, int i3) {
        super(j);
        this.mSignalType = i;
        this.mSifStandard = i2;
        this.mAftFlag = i3;
    }

    public static class Builder {
        private int mAftFlag;
        private long mFrequency;
        private int mSifStandard;
        private int mSignalType;

        private Builder() {
            this.mFrequency = 0L;
            this.mSignalType = 0;
            this.mSifStandard = 0;
            this.mAftFlag = 0;
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.AnalogFrontendSettings.Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public android.media.tv.tuner.frontend.AnalogFrontendSettings.Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public android.media.tv.tuner.frontend.AnalogFrontendSettings.Builder setAftFlag(int i) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "setAftFlag")) {
                this.mAftFlag = i;
            }
            return this;
        }

        public android.media.tv.tuner.frontend.AnalogFrontendSettings.Builder setSignalType(int i) {
            this.mSignalType = i;
            return this;
        }

        public android.media.tv.tuner.frontend.AnalogFrontendSettings.Builder setSifStandard(int i) {
            this.mSifStandard = i;
            return this;
        }

        public android.media.tv.tuner.frontend.AnalogFrontendSettings build() {
            return new android.media.tv.tuner.frontend.AnalogFrontendSettings(this.mFrequency, this.mSignalType, this.mSifStandard, this.mAftFlag);
        }
    }
}
