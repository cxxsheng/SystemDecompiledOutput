package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class OisSample {
    private final long mTimestampNs;
    private final float mXShift;
    private final float mYShift;

    public OisSample(long j, float f, float f2) {
        this.mTimestampNs = j;
        this.mXShift = com.android.internal.util.Preconditions.checkArgumentFinite(f, "xShift must be finite");
        this.mYShift = com.android.internal.util.Preconditions.checkArgumentFinite(f2, "yShift must be finite");
    }

    public long getTimestamp() {
        return this.mTimestampNs;
    }

    public float getXshift() {
        return this.mXShift;
    }

    public float getYshift() {
        return this.mYShift;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.OisSample)) {
            return false;
        }
        android.hardware.camera2.params.OisSample oisSample = (android.hardware.camera2.params.OisSample) obj;
        if (this.mTimestampNs != oisSample.mTimestampNs || this.mXShift != oisSample.mXShift || this.mYShift != oisSample.mYShift) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mXShift, this.mYShift, android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mTimestampNs));
    }

    public java.lang.String toString() {
        return java.lang.String.format("OisSample{timestamp:%d, shift_x:%f, shift_y:%f}", java.lang.Long.valueOf(this.mTimestampNs), java.lang.Float.valueOf(this.mXShift), java.lang.Float.valueOf(this.mYShift));
    }
}
