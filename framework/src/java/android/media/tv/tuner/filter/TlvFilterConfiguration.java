package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class TlvFilterConfiguration extends android.media.tv.tuner.filter.FilterConfiguration {
    public static final int PACKET_TYPE_COMPRESSED = 3;
    public static final int PACKET_TYPE_IPV4 = 1;
    public static final int PACKET_TYPE_IPV6 = 2;
    public static final int PACKET_TYPE_NULL = 255;
    public static final int PACKET_TYPE_SIGNALING = 254;
    private final boolean mIsCompressedIpPacket;
    private final int mPacketType;
    private final boolean mPassthrough;

    private TlvFilterConfiguration(android.media.tv.tuner.filter.Settings settings, int i, boolean z, boolean z2) {
        super(settings);
        this.mPacketType = i;
        this.mIsCompressedIpPacket = z;
        this.mPassthrough = z2;
    }

    @Override // android.media.tv.tuner.filter.FilterConfiguration
    public int getType() {
        return 8;
    }

    public int getPacketType() {
        return this.mPacketType;
    }

    public boolean isCompressedIpPacket() {
        return this.mIsCompressedIpPacket;
    }

    public boolean isPassthrough() {
        return this.mPassthrough;
    }

    public static android.media.tv.tuner.filter.TlvFilterConfiguration.Builder builder() {
        return new android.media.tv.tuner.filter.TlvFilterConfiguration.Builder();
    }

    public static final class Builder {
        private boolean mIsCompressedIpPacket;
        private int mPacketType;
        private boolean mPassthrough;
        private android.media.tv.tuner.filter.Settings mSettings;

        private Builder() {
            this.mPacketType = 255;
            this.mIsCompressedIpPacket = false;
            this.mPassthrough = false;
        }

        public android.media.tv.tuner.filter.TlvFilterConfiguration.Builder setPacketType(int i) {
            this.mPacketType = i;
            return this;
        }

        public android.media.tv.tuner.filter.TlvFilterConfiguration.Builder setCompressedIpPacket(boolean z) {
            this.mIsCompressedIpPacket = z;
            return this;
        }

        public android.media.tv.tuner.filter.TlvFilterConfiguration.Builder setPassthrough(boolean z) {
            this.mPassthrough = z;
            return this;
        }

        public android.media.tv.tuner.filter.TlvFilterConfiguration.Builder setSettings(android.media.tv.tuner.filter.Settings settings) {
            this.mSettings = settings;
            return this;
        }

        public android.media.tv.tuner.filter.TlvFilterConfiguration build() {
            return new android.media.tv.tuner.filter.TlvFilterConfiguration(this.mSettings, this.mPacketType, this.mIsCompressedIpPacket, this.mPassthrough);
        }
    }
}
