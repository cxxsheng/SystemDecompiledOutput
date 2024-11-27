package android.hardware.camera2;

/* loaded from: classes.dex */
public class CaptureFailure {
    public static final int REASON_ERROR = 0;
    public static final int REASON_FLUSHED = 1;
    private final java.lang.String mErrorPhysicalCameraId;
    private final long mFrameNumber;
    private final int mReason;
    private final android.hardware.camera2.CaptureRequest mRequest;
    private final int mSequenceId;
    private final boolean mWasImageCaptured;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    public CaptureFailure(android.hardware.camera2.CaptureRequest captureRequest, int i, boolean z, int i2, long j, java.lang.String str) {
        this.mRequest = captureRequest;
        this.mReason = i;
        this.mWasImageCaptured = z;
        this.mSequenceId = i2;
        this.mFrameNumber = j;
        this.mErrorPhysicalCameraId = str;
    }

    public android.hardware.camera2.CaptureRequest getRequest() {
        return this.mRequest;
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    public int getReason() {
        return this.mReason;
    }

    public boolean wasImageCaptured() {
        return this.mWasImageCaptured;
    }

    public int getSequenceId() {
        return this.mSequenceId;
    }

    public java.lang.String getPhysicalCameraId() {
        return this.mErrorPhysicalCameraId;
    }
}
