package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class HighSpeedVideoConfiguration {
    private static final int HIGH_SPEED_MAX_MINIMAL_FPS = 120;
    private final int mBatchSizeMax;
    private final int mFpsMax;
    private final int mFpsMin;
    private final android.util.Range<java.lang.Integer> mFpsRange;
    private final int mHeight;
    private final android.util.Size mSize;
    private final int mWidth;

    public HighSpeedVideoConfiguration(int i, int i2, int i3, int i4, int i5) {
        if (i4 < 120) {
            throw new java.lang.IllegalArgumentException("fpsMax must be at least 120");
        }
        this.mFpsMax = i4;
        this.mWidth = com.android.internal.util.Preconditions.checkArgumentPositive(i, "width must be positive");
        this.mHeight = com.android.internal.util.Preconditions.checkArgumentPositive(i2, "height must be positive");
        this.mFpsMin = com.android.internal.util.Preconditions.checkArgumentPositive(i3, "fpsMin must be positive");
        this.mSize = new android.util.Size(this.mWidth, this.mHeight);
        this.mBatchSizeMax = com.android.internal.util.Preconditions.checkArgumentPositive(i5, "batchSizeMax must be positive");
        this.mFpsRange = new android.util.Range<>(java.lang.Integer.valueOf(this.mFpsMin), java.lang.Integer.valueOf(this.mFpsMax));
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFpsMin() {
        return this.mFpsMin;
    }

    public int getFpsMax() {
        return this.mFpsMax;
    }

    public android.util.Size getSize() {
        return this.mSize;
    }

    public int getBatchSizeMax() {
        return this.mBatchSizeMax;
    }

    public android.util.Range<java.lang.Integer> getFpsRange() {
        return this.mFpsRange;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.HighSpeedVideoConfiguration)) {
            return false;
        }
        android.hardware.camera2.params.HighSpeedVideoConfiguration highSpeedVideoConfiguration = (android.hardware.camera2.params.HighSpeedVideoConfiguration) obj;
        if (this.mWidth != highSpeedVideoConfiguration.mWidth || this.mHeight != highSpeedVideoConfiguration.mHeight || this.mFpsMin != highSpeedVideoConfiguration.mFpsMin || this.mFpsMax != highSpeedVideoConfiguration.mFpsMax || this.mBatchSizeMax != highSpeedVideoConfiguration.mBatchSizeMax) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mWidth, this.mHeight, this.mFpsMin, this.mFpsMax);
    }
}
