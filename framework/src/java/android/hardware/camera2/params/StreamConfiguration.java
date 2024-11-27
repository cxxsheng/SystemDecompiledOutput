package android.hardware.camera2.params;

/* loaded from: classes.dex */
public class StreamConfiguration {
    protected int mFormat;
    protected int mHeight;
    protected boolean mInput;
    protected int mWidth;

    public StreamConfiguration(int i, int i2, int i3, boolean z) {
        this.mFormat = android.hardware.camera2.params.StreamConfigurationMap.checkArgumentFormatInternal(i);
        this.mWidth = com.android.internal.util.Preconditions.checkArgumentPositive(i2, "width must be positive");
        this.mHeight = com.android.internal.util.Preconditions.checkArgumentPositive(i3, "height must be positive");
        this.mInput = z;
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

    public boolean isInput() {
        return this.mInput;
    }

    public boolean isOutput() {
        return !this.mInput;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.StreamConfiguration)) {
            return false;
        }
        android.hardware.camera2.params.StreamConfiguration streamConfiguration = (android.hardware.camera2.params.StreamConfiguration) obj;
        if (this.mFormat != streamConfiguration.mFormat || this.mWidth != streamConfiguration.mWidth || this.mHeight != streamConfiguration.mHeight || this.mInput != streamConfiguration.mInput) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mFormat, this.mWidth, this.mHeight, this.mInput);
    }
}
