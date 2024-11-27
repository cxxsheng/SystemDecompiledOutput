package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class IsdbsFrontendSettings extends android.media.tv.tuner.frontend.FrontendSettings {
    public static final int CODERATE_1_2 = 2;
    public static final int CODERATE_2_3 = 4;
    public static final int CODERATE_3_4 = 8;
    public static final int CODERATE_5_6 = 16;
    public static final int CODERATE_7_8 = 32;
    public static final int CODERATE_AUTO = 1;
    public static final int CODERATE_UNDEFINED = 0;
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_BPSK = 2;
    public static final int MODULATION_MOD_QPSK = 4;
    public static final int MODULATION_MOD_TC8PSK = 8;
    public static final int MODULATION_UNDEFINED = 0;
    public static final int ROLLOFF_0_35 = 1;
    public static final int ROLLOFF_UNDEFINED = 0;
    public static final int STREAM_ID_TYPE_ID = 0;
    public static final int STREAM_ID_TYPE_RELATIVE_NUMBER = 1;
    private final int mCodeRate;
    private final int mModulation;
    private final int mRolloff;
    private final int mStreamId;
    private final int mStreamIdType;
    private final int mSymbolRate;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CodeRate {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Rolloff {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StreamIdType {
    }

    private IsdbsFrontendSettings(long j, int i, int i2, int i3, int i4, int i5, int i6) {
        super(j);
        this.mStreamId = i;
        this.mStreamIdType = i2;
        this.mModulation = i3;
        this.mCodeRate = i4;
        this.mSymbolRate = i5;
        this.mRolloff = i6;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public int getStreamIdType() {
        return this.mStreamIdType;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public int getCodeRate() {
        return this.mCodeRate;
    }

    public int getSymbolRate() {
        return this.mSymbolRate;
    }

    public int getRolloff() {
        return this.mRolloff;
    }

    public static android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder builder() {
        return new android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder();
    }

    public static class Builder {
        private int mCodeRate;
        private long mFrequency;
        private int mModulation;
        private int mRolloff;
        private int mStreamId;
        private int mStreamIdType;
        private int mSymbolRate;

        private Builder() {
            this.mFrequency = 0L;
            this.mStreamId = 65535;
            this.mStreamIdType = 0;
            this.mModulation = 0;
            this.mCodeRate = 0;
            this.mSymbolRate = 0;
            this.mRolloff = 0;
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setStreamId(int i) {
            this.mStreamId = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setStreamIdType(int i) {
            this.mStreamIdType = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setModulation(int i) {
            this.mModulation = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setCodeRate(int i) {
            this.mCodeRate = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setSymbolRate(int i) {
            this.mSymbolRate = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings.Builder setRolloff(int i) {
            this.mRolloff = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IsdbsFrontendSettings build() {
            return new android.media.tv.tuner.frontend.IsdbsFrontendSettings(this.mFrequency, this.mStreamId, this.mStreamIdType, this.mModulation, this.mCodeRate, this.mSymbolRate, this.mRolloff);
        }
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 7;
    }
}
