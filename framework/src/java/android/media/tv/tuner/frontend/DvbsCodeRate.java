package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class DvbsCodeRate {
    private final int mBitsPer1000Symbol;
    private final long mInnerFec;
    private final boolean mIsLinear;
    private final boolean mIsShortFrames;

    private DvbsCodeRate(long j, boolean z, boolean z2, int i) {
        this.mInnerFec = j;
        this.mIsLinear = z;
        this.mIsShortFrames = z2;
        this.mBitsPer1000Symbol = i;
    }

    public long getInnerFec() {
        return this.mInnerFec;
    }

    public boolean isLinear() {
        return this.mIsLinear;
    }

    public boolean isShortFrameEnabled() {
        return this.mIsShortFrames;
    }

    public int getBitsPer1000Symbol() {
        return this.mBitsPer1000Symbol;
    }

    public static android.media.tv.tuner.frontend.DvbsCodeRate.Builder builder() {
        return new android.media.tv.tuner.frontend.DvbsCodeRate.Builder();
    }

    public static class Builder {
        private int mBitsPer1000Symbol;
        private long mFec;
        private boolean mIsLinear;
        private boolean mIsShortFrames;

        private Builder() {
        }

        public android.media.tv.tuner.frontend.DvbsCodeRate.Builder setInnerFec(long j) {
            this.mFec = j;
            return this;
        }

        public android.media.tv.tuner.frontend.DvbsCodeRate.Builder setLinear(boolean z) {
            this.mIsLinear = z;
            return this;
        }

        public android.media.tv.tuner.frontend.DvbsCodeRate.Builder setShortFrameEnabled(boolean z) {
            this.mIsShortFrames = z;
            return this;
        }

        public android.media.tv.tuner.frontend.DvbsCodeRate.Builder setBitsPer1000Symbol(int i) {
            this.mBitsPer1000Symbol = i;
            return this;
        }

        public android.media.tv.tuner.frontend.DvbsCodeRate build() {
            return new android.media.tv.tuner.frontend.DvbsCodeRate(this.mFec, this.mIsLinear, this.mIsShortFrames, this.mBitsPer1000Symbol);
        }
    }
}
