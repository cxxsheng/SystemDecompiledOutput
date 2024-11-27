package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class AlpFilterConfiguration extends android.media.tv.tuner.filter.FilterConfiguration {
    public static final int LENGTH_TYPE_UNDEFINED = 0;
    public static final int LENGTH_TYPE_WITHOUT_ADDITIONAL_HEADER = 1;
    public static final int LENGTH_TYPE_WITH_ADDITIONAL_HEADER = 2;
    public static final int PACKET_TYPE_COMPRESSED = 2;
    public static final int PACKET_TYPE_EXTENSION = 6;
    public static final int PACKET_TYPE_IPV4 = 0;
    public static final int PACKET_TYPE_MPEG2_TS = 7;
    public static final int PACKET_TYPE_SIGNALING = 4;
    private final int mLengthType;
    private final int mPacketType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LengthType {
    }

    private AlpFilterConfiguration(android.media.tv.tuner.filter.Settings settings, int i, int i2) {
        super(settings);
        this.mPacketType = i;
        this.mLengthType = i2;
    }

    @Override // android.media.tv.tuner.filter.FilterConfiguration
    public int getType() {
        return 16;
    }

    public int getPacketType() {
        return this.mPacketType;
    }

    public int getLengthType() {
        return this.mLengthType;
    }

    public static android.media.tv.tuner.filter.AlpFilterConfiguration.Builder builder() {
        return new android.media.tv.tuner.filter.AlpFilterConfiguration.Builder();
    }

    public static final class Builder {
        private int mLengthType;
        private int mPacketType;
        private android.media.tv.tuner.filter.Settings mSettings;

        private Builder() {
            this.mPacketType = 0;
            this.mLengthType = 0;
        }

        public android.media.tv.tuner.filter.AlpFilterConfiguration.Builder setPacketType(int i) {
            this.mPacketType = i;
            return this;
        }

        public android.media.tv.tuner.filter.AlpFilterConfiguration.Builder setLengthType(int i) {
            this.mLengthType = i;
            return this;
        }

        public android.media.tv.tuner.filter.AlpFilterConfiguration.Builder setSettings(android.media.tv.tuner.filter.Settings settings) {
            this.mSettings = settings;
            return this;
        }

        public android.media.tv.tuner.filter.AlpFilterConfiguration build() {
            return new android.media.tv.tuner.filter.AlpFilterConfiguration(this.mSettings, this.mPacketType, this.mLengthType);
        }
    }
}
