package android.hardware.camera2;

/* loaded from: classes.dex */
public final class TotalCaptureResult extends android.hardware.camera2.CaptureResult {
    private final java.util.List<android.hardware.camera2.CaptureResult> mPartialResults;
    private final java.util.HashMap<java.lang.String, android.hardware.camera2.TotalCaptureResult> mPhysicalCaptureResults;
    private final int mSessionId;

    public TotalCaptureResult(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.impl.CaptureResultExtras captureResultExtras, java.util.List<android.hardware.camera2.CaptureResult> list, int i, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) {
        super(str, cameraMetadataNative, captureRequest, captureResultExtras);
        if (list == null) {
            this.mPartialResults = new java.util.ArrayList();
        } else {
            this.mPartialResults = list;
        }
        this.mSessionId = i;
        this.mPhysicalCaptureResults = new java.util.HashMap<>();
        for (android.hardware.camera2.impl.PhysicalCaptureResultInfo physicalCaptureResultInfo : physicalCaptureResultInfoArr) {
            this.mPhysicalCaptureResults.put(physicalCaptureResultInfo.getCameraId(), new android.hardware.camera2.TotalCaptureResult(physicalCaptureResultInfo.getCameraId(), physicalCaptureResultInfo.getCameraMetadata(), captureRequest, captureResultExtras, null, i, new android.hardware.camera2.impl.PhysicalCaptureResultInfo[0]));
        }
    }

    public TotalCaptureResult(java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, android.hardware.camera2.CaptureRequest captureRequest, int i, long j, java.util.List<android.hardware.camera2.CaptureResult> list, int i2, android.hardware.camera2.impl.PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) {
        super(str, cameraMetadataNative, captureRequest, i, j);
        if (list == null) {
            this.mPartialResults = new java.util.ArrayList();
        } else {
            this.mPartialResults = list;
        }
        this.mSessionId = i2;
        this.mPhysicalCaptureResults = new java.util.HashMap<>();
        int length = physicalCaptureResultInfoArr.length;
        int i3 = 0;
        int i4 = 0;
        while (i4 < length) {
            android.hardware.camera2.impl.PhysicalCaptureResultInfo physicalCaptureResultInfo = physicalCaptureResultInfoArr[i4];
            this.mPhysicalCaptureResults.put(physicalCaptureResultInfo.getCameraId(), new android.hardware.camera2.TotalCaptureResult(physicalCaptureResultInfo.getCameraId(), physicalCaptureResultInfo.getCameraMetadata(), captureRequest, i, j, null, i2, new android.hardware.camera2.impl.PhysicalCaptureResultInfo[i3]));
            i4++;
            i3 = 0;
        }
    }

    public TotalCaptureResult(android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative, int i) {
        super(cameraMetadataNative, i);
        this.mPartialResults = new java.util.ArrayList();
        this.mSessionId = -1;
        this.mPhysicalCaptureResults = new java.util.HashMap<>();
    }

    public java.util.List<android.hardware.camera2.CaptureResult> getPartialResults() {
        return java.util.Collections.unmodifiableList(this.mPartialResults);
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public java.util.Map<java.lang.String, android.hardware.camera2.CaptureResult> getPhysicalCameraResults() {
        return java.util.Collections.unmodifiableMap(this.mPhysicalCaptureResults);
    }

    public java.util.Map<java.lang.String, android.hardware.camera2.TotalCaptureResult> getPhysicalCameraTotalResults() {
        return java.util.Collections.unmodifiableMap(this.mPhysicalCaptureResults);
    }
}
