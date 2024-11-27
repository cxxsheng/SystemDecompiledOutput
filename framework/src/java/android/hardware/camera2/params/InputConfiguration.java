package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class InputConfiguration {
    private final int mFormat;
    private final int mHeight;
    private final boolean mIsMultiResolution;
    private final int mWidth;

    public InputConfiguration(int i, int i2, int i3) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mIsMultiResolution = false;
    }

    public InputConfiguration(java.util.Collection<android.hardware.camera2.params.MultiResolutionStreamInfo> collection, int i) {
        com.android.internal.util.Preconditions.checkCollectionNotEmpty(collection, "Input multi-resolution stream info");
        android.hardware.camera2.params.MultiResolutionStreamInfo next = collection.iterator().next();
        this.mWidth = next.getWidth();
        this.mHeight = next.getHeight();
        this.mFormat = i;
        this.mIsMultiResolution = true;
    }

    public InputConfiguration(int i, int i2, int i3, boolean z) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mIsMultiResolution = z;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public boolean isMultiResolution() {
        return this.mIsMultiResolution;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.hardware.camera2.params.InputConfiguration)) {
            return false;
        }
        android.hardware.camera2.params.InputConfiguration inputConfiguration = (android.hardware.camera2.params.InputConfiguration) obj;
        return inputConfiguration.getWidth() == this.mWidth && inputConfiguration.getHeight() == this.mHeight && inputConfiguration.getFormat() == this.mFormat && inputConfiguration.isMultiResolution() == this.mIsMultiResolution;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mWidth, this.mHeight, this.mFormat, this.mIsMultiResolution);
    }

    public java.lang.String toString() {
        return java.lang.String.format("InputConfiguration(w:%d, h:%d, format:%d, isMultiResolution %b)", java.lang.Integer.valueOf(this.mWidth), java.lang.Integer.valueOf(this.mHeight), java.lang.Integer.valueOf(this.mFormat), java.lang.Boolean.valueOf(this.mIsMultiResolution));
    }
}
