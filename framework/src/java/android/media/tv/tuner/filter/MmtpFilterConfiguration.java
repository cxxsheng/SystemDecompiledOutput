package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class MmtpFilterConfiguration extends android.media.tv.tuner.filter.FilterConfiguration {
    private final int mMmtpPid;

    private MmtpFilterConfiguration(android.media.tv.tuner.filter.Settings settings, int i) {
        super(settings);
        this.mMmtpPid = i;
    }

    @Override // android.media.tv.tuner.filter.FilterConfiguration
    public int getType() {
        return 2;
    }

    public int getMmtpPacketId() {
        return this.mMmtpPid;
    }

    public static android.media.tv.tuner.filter.MmtpFilterConfiguration.Builder builder() {
        return new android.media.tv.tuner.filter.MmtpFilterConfiguration.Builder();
    }

    public static final class Builder {
        private int mMmtpPid;
        private android.media.tv.tuner.filter.Settings mSettings;

        private Builder() {
            this.mMmtpPid = 65535;
        }

        public android.media.tv.tuner.filter.MmtpFilterConfiguration.Builder setMmtpPacketId(int i) {
            this.mMmtpPid = i;
            return this;
        }

        public android.media.tv.tuner.filter.MmtpFilterConfiguration.Builder setSettings(android.media.tv.tuner.filter.Settings settings) {
            this.mSettings = settings;
            return this;
        }

        public android.media.tv.tuner.filter.MmtpFilterConfiguration build() {
            return new android.media.tv.tuner.filter.MmtpFilterConfiguration(this.mSettings, this.mMmtpPid);
        }
    }
}
