package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class TsFilterConfiguration extends android.media.tv.tuner.filter.FilterConfiguration {
    private final int mTpid;

    private TsFilterConfiguration(android.media.tv.tuner.filter.Settings settings, int i) {
        super(settings);
        this.mTpid = i;
    }

    @Override // android.media.tv.tuner.filter.FilterConfiguration
    public int getType() {
        return 1;
    }

    public int getTpid() {
        return this.mTpid;
    }

    public static android.media.tv.tuner.filter.TsFilterConfiguration.Builder builder() {
        return new android.media.tv.tuner.filter.TsFilterConfiguration.Builder();
    }

    public static final class Builder {
        private android.media.tv.tuner.filter.Settings mSettings;
        private int mTpid;

        private Builder() {
            this.mTpid = 0;
        }

        public android.media.tv.tuner.filter.TsFilterConfiguration.Builder setTpid(int i) {
            this.mTpid = i;
            return this;
        }

        public android.media.tv.tuner.filter.TsFilterConfiguration.Builder setSettings(android.media.tv.tuner.filter.Settings settings) {
            this.mSettings = settings;
            return this;
        }

        public android.media.tv.tuner.filter.TsFilterConfiguration build() {
            return new android.media.tv.tuner.filter.TsFilterConfiguration(this.mSettings, this.mTpid);
        }
    }
}
