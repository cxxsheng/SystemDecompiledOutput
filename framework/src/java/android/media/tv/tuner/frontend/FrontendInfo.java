package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class FrontendInfo {
    private final long mAcquireRange;
    private final int mExclusiveGroupId;
    private final android.util.Range<java.lang.Long> mFrequencyRange;
    private final android.media.tv.tuner.frontend.FrontendCapabilities mFrontendCap;
    private final int mId;
    private final int[] mStatusCaps;
    private final android.util.Range<java.lang.Integer> mSymbolRateRange;
    private final int mType;

    private FrontendInfo(int i, int i2, long j, long j2, int i3, int i4, long j3, int i5, int[] iArr, android.media.tv.tuner.frontend.FrontendCapabilities frontendCapabilities) {
        this.mId = i;
        this.mType = i2;
        this.mFrequencyRange = new android.util.Range<>(java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2 < 0 ? 2147483647L : j2));
        this.mSymbolRateRange = new android.util.Range<>(java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
        this.mAcquireRange = j3;
        this.mExclusiveGroupId = i5;
        this.mStatusCaps = iArr;
        this.mFrontendCap = frontendCapabilities;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    @java.lang.Deprecated
    public android.util.Range<java.lang.Integer> getFrequencyRange() {
        return new android.util.Range<>(java.lang.Integer.valueOf((int) this.mFrequencyRange.getLower().longValue()), java.lang.Integer.valueOf((int) this.mFrequencyRange.getUpper().longValue()));
    }

    public android.util.Range<java.lang.Long> getFrequencyRangeLong() {
        return this.mFrequencyRange;
    }

    public android.util.Range<java.lang.Integer> getSymbolRateRange() {
        return this.mSymbolRateRange;
    }

    @java.lang.Deprecated
    public int getAcquireRange() {
        return (int) getAcquireRangeLong();
    }

    public long getAcquireRangeLong() {
        return this.mAcquireRange;
    }

    public int getExclusiveGroupId() {
        return this.mExclusiveGroupId;
    }

    public int[] getStatusCapabilities() {
        return this.mStatusCaps;
    }

    public android.media.tv.tuner.frontend.FrontendCapabilities getFrontendCapabilities() {
        return this.mFrontendCap;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.tv.tuner.frontend.FrontendInfo)) {
            return false;
        }
        android.media.tv.tuner.frontend.FrontendInfo frontendInfo = (android.media.tv.tuner.frontend.FrontendInfo) obj;
        if (this.mId == frontendInfo.getId() && this.mType == frontendInfo.getType() && java.util.Objects.equals(this.mFrequencyRange, frontendInfo.getFrequencyRangeLong()) && java.util.Objects.equals(this.mSymbolRateRange, frontendInfo.getSymbolRateRange()) && this.mAcquireRange == frontendInfo.getAcquireRangeLong() && this.mExclusiveGroupId == frontendInfo.getExclusiveGroupId() && java.util.Arrays.equals(this.mStatusCaps, frontendInfo.getStatusCapabilities())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.mId;
    }
}
