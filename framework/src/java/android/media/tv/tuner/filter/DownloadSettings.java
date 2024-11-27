package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class DownloadSettings extends android.media.tv.tuner.filter.Settings {
    private final int mDownloadId;
    private final boolean mUseDownloadId;

    private DownloadSettings(int i, boolean z, int i2) {
        super(android.media.tv.tuner.TunerUtils.getFilterSubtype(i, 5));
        this.mUseDownloadId = z;
        this.mDownloadId = i2;
    }

    public int getDownloadId() {
        return this.mDownloadId;
    }

    public boolean useDownloadId() {
        return this.mUseDownloadId;
    }

    public static android.media.tv.tuner.filter.DownloadSettings.Builder builder(int i) {
        return new android.media.tv.tuner.filter.DownloadSettings.Builder(i);
    }

    public static class Builder {
        private int mDownloadId;
        private final int mMainType;
        private boolean mUseDownloadId;

        private Builder(int i) {
            this.mUseDownloadId = false;
            this.mMainType = i;
        }

        public android.media.tv.tuner.filter.DownloadSettings.Builder setUseDownloadId(boolean z) {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "setUseDownloadId")) {
                this.mUseDownloadId = z;
            }
            return this;
        }

        public android.media.tv.tuner.filter.DownloadSettings.Builder setDownloadId(int i) {
            this.mDownloadId = i;
            return this;
        }

        public android.media.tv.tuner.filter.DownloadSettings build() {
            return new android.media.tv.tuner.filter.DownloadSettings(this.mMainType, this.mUseDownloadId, this.mDownloadId);
        }
    }
}
