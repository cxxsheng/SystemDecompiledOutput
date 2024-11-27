package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class PesSettings extends android.media.tv.tuner.filter.Settings {
    private final boolean mIsRaw;
    private final int mStreamId;

    private PesSettings(int i, int i2, boolean z) {
        super(android.media.tv.tuner.TunerUtils.getFilterSubtype(i, 2));
        this.mStreamId = i2;
        this.mIsRaw = z;
    }

    public int getStreamId() {
        return this.mStreamId;
    }

    public boolean isRaw() {
        return this.mIsRaw;
    }

    public static android.media.tv.tuner.filter.PesSettings.Builder builder(int i) {
        return new android.media.tv.tuner.filter.PesSettings.Builder(i);
    }

    public static class Builder {
        private boolean mIsRaw;
        private final int mMainType;
        private int mStreamId;

        private Builder(int i) {
            this.mMainType = i;
        }

        public android.media.tv.tuner.filter.PesSettings.Builder setStreamId(int i) {
            this.mStreamId = i;
            return this;
        }

        public android.media.tv.tuner.filter.PesSettings.Builder setRaw(boolean z) {
            this.mIsRaw = z;
            return this;
        }

        public android.media.tv.tuner.filter.PesSettings build() {
            return new android.media.tv.tuner.filter.PesSettings(this.mMainType, this.mStreamId, this.mIsRaw);
        }
    }
}
