package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class RecommendedStreamConfiguration extends android.hardware.camera2.params.StreamConfiguration {
    private final int mUsecaseBitmap;

    public RecommendedStreamConfiguration(int i, int i2, int i3, boolean z, int i4) {
        super(i, i2, i3, z);
        this.mUsecaseBitmap = i4;
    }

    public int getUsecaseBitmap() {
        return this.mUsecaseBitmap;
    }

    @Override // android.hardware.camera2.params.StreamConfiguration
    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.RecommendedStreamConfiguration)) {
            return false;
        }
        android.hardware.camera2.params.RecommendedStreamConfiguration recommendedStreamConfiguration = (android.hardware.camera2.params.RecommendedStreamConfiguration) obj;
        if (this.mFormat != recommendedStreamConfiguration.mFormat || this.mWidth != recommendedStreamConfiguration.mWidth || this.mHeight != recommendedStreamConfiguration.mHeight || this.mUsecaseBitmap != recommendedStreamConfiguration.mUsecaseBitmap || this.mInput != recommendedStreamConfiguration.mInput) {
            return false;
        }
        return true;
    }

    @Override // android.hardware.camera2.params.StreamConfiguration
    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mFormat, this.mWidth, this.mHeight, this.mInput, this.mUsecaseBitmap);
    }
}
