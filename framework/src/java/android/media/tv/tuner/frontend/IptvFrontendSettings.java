package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class IptvFrontendSettings extends android.media.tv.tuner.frontend.FrontendSettings {
    public static final int IGMP_UNDEFINED = 0;
    public static final int IGMP_V1 = 1;
    public static final int IGMP_V2 = 2;
    public static final int IGMP_V3 = 4;
    public static final int PROTOCOL_RTP = 2;
    public static final int PROTOCOL_UDP = 1;
    public static final int PROTOCOL_UNDEFINED = 0;
    private final long mBitrate;
    private final java.lang.String mContentUrl;
    private final byte[] mDstIpAddress;
    private final int mDstPort;
    private final android.media.tv.tuner.frontend.IptvFrontendSettingsFec mFec;
    private final int mIgmp;
    private final int mProtocol;
    private final byte[] mSrcIpAddress;
    private final int mSrcPort;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Igmp {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Protocol {
    }

    private IptvFrontendSettings(byte[] bArr, byte[] bArr2, int i, int i2, android.media.tv.tuner.frontend.IptvFrontendSettingsFec iptvFrontendSettingsFec, int i3, int i4, long j, java.lang.String str) {
        super(0L);
        this.mSrcIpAddress = bArr;
        this.mDstIpAddress = bArr2;
        this.mSrcPort = i;
        this.mDstPort = i2;
        this.mFec = iptvFrontendSettingsFec;
        this.mProtocol = i3;
        this.mIgmp = i4;
        this.mBitrate = j;
        this.mContentUrl = str;
    }

    public byte[] getSrcIpAddress() {
        return this.mSrcIpAddress;
    }

    public byte[] getDstIpAddress() {
        return this.mDstIpAddress;
    }

    public int getSrcPort() {
        return this.mSrcPort;
    }

    public int getDstPort() {
        return this.mDstPort;
    }

    public android.media.tv.tuner.frontend.IptvFrontendSettingsFec getFec() {
        return this.mFec;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public int getIgmp() {
        return this.mIgmp;
    }

    public long getBitrate() {
        return this.mBitrate;
    }

    public java.lang.String getContentUrl() {
        return this.mContentUrl;
    }

    public static final class Builder {
        private byte[] mSrcIpAddress = {0, 0, 0, 0};
        private byte[] mDstIpAddress = {0, 0, 0, 0};
        private int mSrcPort = 0;
        private int mDstPort = 0;
        private android.media.tv.tuner.frontend.IptvFrontendSettingsFec mFec = null;
        private int mProtocol = 0;
        private int mIgmp = 0;
        private long mBitrate = 0;
        private java.lang.String mContentUrl = "";

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setSrcIpAddress(byte[] bArr) {
            this.mSrcIpAddress = bArr;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setDstIpAddress(byte[] bArr) {
            this.mDstIpAddress = bArr;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setSrcPort(int i) {
            this.mSrcPort = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setDstPort(int i) {
            this.mDstPort = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setFec(android.media.tv.tuner.frontend.IptvFrontendSettingsFec iptvFrontendSettingsFec) {
            this.mFec = iptvFrontendSettingsFec;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setProtocol(int i) {
            this.mProtocol = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setIgmp(int i) {
            this.mIgmp = i;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setBitrate(long j) {
            this.mBitrate = j;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings.Builder setContentUrl(java.lang.String str) {
            this.mContentUrl = str;
            return this;
        }

        public android.media.tv.tuner.frontend.IptvFrontendSettings build() {
            return new android.media.tv.tuner.frontend.IptvFrontendSettings(this.mSrcIpAddress, this.mDstIpAddress, this.mSrcPort, this.mDstPort, this.mFec, this.mProtocol, this.mIgmp, this.mBitrate, this.mContentUrl);
        }
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 11;
    }
}
