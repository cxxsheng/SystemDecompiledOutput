package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CaptureCallbackHolder {
    private final android.hardware.camera2.impl.CaptureCallback mCallback;
    private final java.util.concurrent.Executor mExecutor;
    private final boolean mHasBatchedOutputs;
    private final boolean mRepeating;
    private final java.util.List<android.hardware.camera2.CaptureRequest> mRequestList;
    private final int mSessionId;

    CaptureCallbackHolder(android.hardware.camera2.impl.CaptureCallback captureCallback, java.util.List<android.hardware.camera2.CaptureRequest> list, java.util.concurrent.Executor executor, boolean z, int i) {
        if (captureCallback == null || executor == null) {
            throw new java.lang.UnsupportedOperationException("Must have a valid handler and a valid callback");
        }
        this.mRepeating = z;
        this.mExecutor = executor;
        this.mRequestList = new java.util.ArrayList(list);
        this.mCallback = captureCallback;
        this.mSessionId = i;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                z2 = true;
                break;
            }
            android.hardware.camera2.CaptureRequest captureRequest = list.get(i2);
            if (!captureRequest.isPartOfCRequestList() || (i2 == 0 && captureRequest.getTargets().size() != 2)) {
                break;
            } else {
                i2++;
            }
        }
        this.mHasBatchedOutputs = z2;
    }

    public boolean isRepeating() {
        return this.mRepeating;
    }

    public android.hardware.camera2.impl.CaptureCallback getCallback() {
        return this.mCallback;
    }

    public android.hardware.camera2.CaptureRequest getRequest(int i) {
        if (i >= this.mRequestList.size()) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Requested subsequenceId %d is larger than request list size %d.", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.mRequestList.size())));
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Requested subsequenceId %d is negative", java.lang.Integer.valueOf(i)));
        }
        return this.mRequestList.get(i);
    }

    public android.hardware.camera2.CaptureRequest getRequest() {
        return getRequest(0);
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getRequestCount() {
        return this.mRequestList.size();
    }

    public boolean hasBatchedOutputs() {
        return this.mHasBatchedOutputs;
    }
}
