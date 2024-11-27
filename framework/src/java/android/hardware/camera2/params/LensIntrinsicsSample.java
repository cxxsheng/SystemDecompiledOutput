package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class LensIntrinsicsSample {
    private final float[] mLensIntrinsics;
    private final long mTimestampNs;

    public LensIntrinsicsSample(long j, float[] fArr) {
        this.mTimestampNs = j;
        com.android.internal.util.Preconditions.checkArgument(fArr.length == 5);
        this.mLensIntrinsics = fArr;
    }

    public long getTimestampNanos() {
        return this.mTimestampNs;
    }

    public float[] getLensIntrinsics() {
        return this.mLensIntrinsics;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.LensIntrinsicsSample)) {
            return false;
        }
        android.hardware.camera2.params.LensIntrinsicsSample lensIntrinsicsSample = (android.hardware.camera2.params.LensIntrinsicsSample) obj;
        if (this.mTimestampNs != lensIntrinsicsSample.mTimestampNs || !java.util.Arrays.equals(this.mLensIntrinsics, lensIntrinsicsSample.getLensIntrinsics())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(java.util.Arrays.hashCode(this.mLensIntrinsics), android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mTimestampNs));
    }

    public java.lang.String toString() {
        return android.text.TextUtils.formatSimple("LensIntrinsicsSample{timestamp:%d, sample:%s}", java.lang.Long.valueOf(this.mTimestampNs), java.util.Arrays.toString(this.mLensIntrinsics));
    }
}
