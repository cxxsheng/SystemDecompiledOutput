package android.media.tv.tuner.dvr;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class DvrSettings {
    public static final int DATA_FORMAT_ES = 2;
    public static final int DATA_FORMAT_PES = 1;
    public static final int DATA_FORMAT_SHV_TLV = 3;
    public static final int DATA_FORMAT_TS = 0;
    private final int mDataFormat;
    private final long mHighThreshold;
    private final long mLowThreshold;
    private final long mPacketSize;
    private final int mStatusMask;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataFormat {
    }

    private DvrSettings(int i, long j, long j2, long j3, int i2) {
        this.mStatusMask = i;
        this.mLowThreshold = j;
        this.mHighThreshold = j2;
        this.mPacketSize = j3;
        this.mDataFormat = i2;
    }

    public int getStatusMask() {
        return this.mStatusMask;
    }

    public long getLowThreshold() {
        return this.mLowThreshold;
    }

    public long getHighThreshold() {
        return this.mHighThreshold;
    }

    public long getPacketSize() {
        return this.mPacketSize;
    }

    public int getDataFormat() {
        return this.mDataFormat;
    }

    public static android.media.tv.tuner.dvr.DvrSettings.Builder builder() {
        return new android.media.tv.tuner.dvr.DvrSettings.Builder();
    }

    public static final class Builder {
        private int mDataFormat;
        private long mHighThreshold;
        private long mLowThreshold;
        private long mPacketSize;
        private int mStatusMask;

        public android.media.tv.tuner.dvr.DvrSettings.Builder setStatusMask(int i) {
            this.mStatusMask = i;
            return this;
        }

        public android.media.tv.tuner.dvr.DvrSettings.Builder setLowThreshold(long j) {
            this.mLowThreshold = j;
            return this;
        }

        public android.media.tv.tuner.dvr.DvrSettings.Builder setHighThreshold(long j) {
            this.mHighThreshold = j;
            return this;
        }

        public android.media.tv.tuner.dvr.DvrSettings.Builder setPacketSize(long j) {
            this.mPacketSize = j;
            return this;
        }

        public android.media.tv.tuner.dvr.DvrSettings.Builder setDataFormat(int i) {
            this.mDataFormat = i;
            return this;
        }

        public android.media.tv.tuner.dvr.DvrSettings build() {
            return new android.media.tv.tuner.dvr.DvrSettings(this.mStatusMask, this.mLowThreshold, this.mHighThreshold, this.mPacketSize, this.mDataFormat);
        }
    }
}
