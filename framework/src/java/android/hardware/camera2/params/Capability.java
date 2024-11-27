package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class Capability {
    public static final int COUNT = 3;
    private final android.util.Size mMaxStreamingSize;
    private final int mMode;
    private final android.util.Range<java.lang.Float> mZoomRatioRange;

    public Capability(int i, android.util.Size size, android.util.Range<java.lang.Float> range) {
        this.mMode = i;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(size.getWidth(), "maxStreamingSize.getWidth() must be nonnegative");
        com.android.internal.util.Preconditions.checkArgumentNonnegative(size.getHeight(), "maxStreamingSize.getHeight() must be nonnegative");
        this.mMaxStreamingSize = size;
        if (range.getLower().floatValue() > range.getUpper().floatValue()) {
            throw new java.lang.IllegalArgumentException("zoomRatioRange.getLower() " + range.getLower() + " is greater than zoomRatioRange.getUpper() " + range.getUpper());
        }
        com.android.internal.util.Preconditions.checkArgumentPositive(range.getLower().floatValue(), "zoomRatioRange.getLower() must be positive");
        com.android.internal.util.Preconditions.checkArgumentPositive(range.getUpper().floatValue(), "zoomRatioRange.getUpper() must be positive");
        this.mZoomRatioRange = range;
    }

    public int getMode() {
        return this.mMode;
    }

    public android.util.Size getMaxStreamingSize() {
        return this.mMaxStreamingSize;
    }

    public android.util.Range<java.lang.Float> getZoomRatioRange() {
        return this.mZoomRatioRange;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.Capability)) {
            return false;
        }
        android.hardware.camera2.params.Capability capability = (android.hardware.camera2.params.Capability) obj;
        if (this.mMode != capability.mMode || !this.mMaxStreamingSize.equals(capability.mMaxStreamingSize) || !this.mZoomRatioRange.equals(capability.mZoomRatioRange)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mMode, this.mMaxStreamingSize.getWidth(), this.mMaxStreamingSize.getHeight(), this.mZoomRatioRange.getLower().floatValue(), this.mZoomRatioRange.getUpper().floatValue());
    }

    public java.lang.String toString() {
        return java.lang.String.format("(mode:%d, maxStreamingSize:%d x %d, zoomRatio: %f-%f)", java.lang.Integer.valueOf(this.mMode), java.lang.Integer.valueOf(this.mMaxStreamingSize.getWidth()), java.lang.Integer.valueOf(this.mMaxStreamingSize.getHeight()), this.mZoomRatioRange.getLower(), this.mZoomRatioRange.getUpper());
    }
}
