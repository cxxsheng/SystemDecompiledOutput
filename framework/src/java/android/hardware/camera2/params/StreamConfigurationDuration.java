package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class StreamConfigurationDuration {
    private final long mDurationNs;
    private final int mFormat;
    private final int mHeight;
    private final int mWidth;

    public StreamConfigurationDuration(int i, int i2, int i3, long j) {
        this.mFormat = android.hardware.camera2.params.StreamConfigurationMap.checkArgumentFormatInternal(i);
        this.mWidth = com.android.internal.util.Preconditions.checkArgumentPositive(i2, "width must be positive");
        this.mHeight = com.android.internal.util.Preconditions.checkArgumentPositive(i3, "height must be positive");
        this.mDurationNs = com.android.internal.util.Preconditions.checkArgumentNonnegative(j, "durationNs must be non-negative");
    }

    public final int getFormat() {
        return this.mFormat;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public android.util.Size getSize() {
        return new android.util.Size(this.mWidth, this.mHeight);
    }

    public long getDuration() {
        return this.mDurationNs;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.StreamConfigurationDuration)) {
            return false;
        }
        android.hardware.camera2.params.StreamConfigurationDuration streamConfigurationDuration = (android.hardware.camera2.params.StreamConfigurationDuration) obj;
        if (this.mFormat != streamConfigurationDuration.mFormat || this.mWidth != streamConfigurationDuration.mWidth || this.mHeight != streamConfigurationDuration.mHeight || this.mDurationNs != streamConfigurationDuration.mDurationNs) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mFormat, this.mWidth, this.mHeight, (int) this.mDurationNs, (int) (this.mDurationNs >>> 32));
    }
}
