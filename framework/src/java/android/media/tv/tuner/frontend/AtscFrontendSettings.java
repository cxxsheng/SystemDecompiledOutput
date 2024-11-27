package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AtscFrontendSettings extends android.media.tv.tuner.frontend.FrontendSettings {
    public static final int MODULATION_AUTO = 1;
    public static final int MODULATION_MOD_16VSB = 8;
    public static final int MODULATION_MOD_8VSB = 4;
    public static final int MODULATION_UNDEFINED = 0;
    private final int mModulation;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Modulation {
    }

    private AtscFrontendSettings(long j, int i) {
        super(j);
        this.mModulation = i;
    }

    public int getModulation() {
        return this.mModulation;
    }

    public static android.media.tv.tuner.frontend.AtscFrontendSettings.Builder builder() {
        return new android.media.tv.tuner.frontend.AtscFrontendSettings.Builder();
    }

    public static class Builder {
        private long mFrequency;
        private int mModulation;

        private Builder() {
            this.mFrequency = 0L;
            this.mModulation = 0;
        }

        @java.lang.Deprecated
        public android.media.tv.tuner.frontend.AtscFrontendSettings.Builder setFrequency(int i) {
            return setFrequencyLong(i);
        }

        public android.media.tv.tuner.frontend.AtscFrontendSettings.Builder setFrequencyLong(long j) {
            this.mFrequency = j;
            return this;
        }

        public android.media.tv.tuner.frontend.AtscFrontendSettings.Builder setModulation(int i) {
            this.mModulation = i;
            return this;
        }

        public android.media.tv.tuner.frontend.AtscFrontendSettings build() {
            return new android.media.tv.tuner.frontend.AtscFrontendSettings(this.mFrequency, this.mModulation);
        }
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 2;
    }
}
