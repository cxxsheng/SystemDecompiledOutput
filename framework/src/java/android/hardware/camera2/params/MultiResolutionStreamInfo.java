package android.hardware.camera2.params;

/* loaded from: classes.dex */
public class MultiResolutionStreamInfo {
    private java.lang.String mPhysicalCameraId;
    private int mStreamHeight;
    private int mStreamWidth;

    public MultiResolutionStreamInfo(int i, int i2, java.lang.String str) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid stream width " + i);
        }
        if (i2 <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid stream height " + i2);
        }
        this.mStreamWidth = i;
        this.mStreamHeight = i2;
        this.mPhysicalCameraId = str;
    }

    public int getWidth() {
        return this.mStreamWidth;
    }

    public int getHeight() {
        return this.mStreamHeight;
    }

    public java.lang.String getPhysicalCameraId() {
        return this.mPhysicalCameraId;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.MultiResolutionStreamInfo)) {
            return false;
        }
        android.hardware.camera2.params.MultiResolutionStreamInfo multiResolutionStreamInfo = (android.hardware.camera2.params.MultiResolutionStreamInfo) obj;
        if (this.mStreamWidth != multiResolutionStreamInfo.mStreamWidth || this.mStreamHeight != multiResolutionStreamInfo.mStreamHeight || !this.mPhysicalCameraId.equals(multiResolutionStreamInfo.mPhysicalCameraId)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mStreamWidth), java.lang.Integer.valueOf(this.mStreamHeight), this.mPhysicalCameraId);
    }
}
