package com.android.server.devicestate;

/* loaded from: classes.dex */
final class OverrideRequestController {
    static final int FLAG_POWER_SAVE_ENABLED = 2;
    static final int FLAG_THERMAL_CRITICAL = 1;
    static final int STATUS_ACTIVE = 1;
    static final int STATUS_CANCELED = 2;
    static final int STATUS_UNKNOWN = 0;
    private static final java.lang.String TAG = "OverrideRequestController";
    private com.android.server.devicestate.OverrideRequest mBaseStateRequest;
    private final com.android.server.devicestate.OverrideRequestController.StatusChangeListener mListener;
    private com.android.server.devicestate.OverrideRequest mRequest;
    private boolean mStickyRequest;
    private boolean mStickyRequestsAllowed;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface RequestStatus {
    }

    public interface StatusChangeListener {
        void onStatusChanged(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest, int i, int i2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface StatusChangedFlag {
    }

    static java.lang.String statusToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ACTIVE";
            case 2:
                return "CANCELED";
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + i);
        }
    }

    OverrideRequestController(@android.annotation.NonNull com.android.server.devicestate.OverrideRequestController.StatusChangeListener statusChangeListener) {
        this.mListener = statusChangeListener;
    }

    void setStickyRequestsAllowed(boolean z) {
        this.mStickyRequestsAllowed = z;
        if (!this.mStickyRequestsAllowed) {
            cancelStickyRequest();
        }
    }

    void addRequest(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest) {
        com.android.server.devicestate.OverrideRequest overrideRequest2 = this.mRequest;
        this.mRequest = overrideRequest;
        this.mListener.onStatusChanged(overrideRequest, 1, 0);
        if (overrideRequest2 != null) {
            cancelRequestLocked(overrideRequest2);
        }
    }

    void addBaseStateRequest(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest) {
        com.android.server.devicestate.OverrideRequest overrideRequest2 = this.mBaseStateRequest;
        this.mBaseStateRequest = overrideRequest;
        this.mListener.onStatusChanged(overrideRequest, 1, 0);
        if (overrideRequest2 != null) {
            cancelRequestLocked(overrideRequest2);
        }
    }

    void cancelRequest(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest) {
        if (!hasRequest(overrideRequest.getToken(), overrideRequest.getRequestType())) {
            return;
        }
        cancelCurrentRequestLocked();
    }

    void cancelStickyRequest() {
        if (this.mStickyRequest) {
            cancelCurrentRequestLocked();
        }
    }

    void cancelOverrideRequest() {
        cancelCurrentRequestLocked();
    }

    void cancelBaseStateOverrideRequest() {
        cancelCurrentBaseStateRequestLocked();
    }

    boolean hasRequest(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        return i == 1 ? this.mBaseStateRequest != null && iBinder == this.mBaseStateRequest.getToken() : this.mRequest != null && iBinder == this.mRequest.getToken();
    }

    void handleProcessDied(int i) {
        if (this.mBaseStateRequest != null && this.mBaseStateRequest.getPid() == i) {
            cancelCurrentBaseStateRequestLocked();
        }
        if (this.mRequest != null && this.mRequest.getPid() == i) {
            if (this.mRequest.getRequestedDeviceState().hasFlag(8)) {
                cancelCurrentRequestLocked();
            } else if (this.mStickyRequestsAllowed) {
                this.mStickyRequest = true;
            } else {
                cancelCurrentRequestLocked();
            }
        }
    }

    void handleBaseStateChanged(int i) {
        if (this.mBaseStateRequest != null && i != this.mBaseStateRequest.getRequestedStateIdentifier()) {
            cancelBaseStateOverrideRequest();
        }
        if (this.mRequest != null && (this.mRequest.getFlags() & 1) != 0) {
            cancelCurrentRequestLocked();
        }
    }

    void handleNewSupportedStates(int[] iArr, int i) {
        int i2 = (i == 3 ? 1 : 0) | 0 | (i == 4 ? 2 : 0);
        if (this.mBaseStateRequest != null && !contains(iArr, this.mBaseStateRequest.getRequestedStateIdentifier())) {
            cancelCurrentBaseStateRequestLocked(i2);
        }
        if (this.mRequest != null && !contains(iArr, this.mRequest.getRequestedStateIdentifier())) {
            cancelCurrentRequestLocked(i2);
        }
    }

    void dumpInternal(java.io.PrintWriter printWriter) {
        com.android.server.devicestate.OverrideRequest overrideRequest = this.mRequest;
        boolean z = overrideRequest != null;
        printWriter.println();
        printWriter.println("Override Request active: " + z);
        if (z) {
            printWriter.println("Request: mPid=" + overrideRequest.getPid() + ", mRequestedState=" + overrideRequest.getRequestedStateIdentifier() + ", mFlags=" + overrideRequest.getFlags() + ", mStatus=" + statusToString(1));
        }
    }

    private void cancelRequestLocked(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest) {
        cancelRequestLocked(overrideRequest, 0);
    }

    private void cancelRequestLocked(@android.annotation.NonNull com.android.server.devicestate.OverrideRequest overrideRequest, int i) {
        this.mListener.onStatusChanged(overrideRequest, 2, i);
    }

    private void cancelCurrentRequestLocked() {
        cancelCurrentRequestLocked(0);
    }

    private void cancelCurrentRequestLocked(int i) {
        if (this.mRequest == null) {
            android.util.Slog.w(TAG, "Attempted to cancel a null OverrideRequest");
            return;
        }
        this.mStickyRequest = false;
        cancelRequestLocked(this.mRequest, i);
        this.mRequest = null;
    }

    private void cancelCurrentBaseStateRequestLocked() {
        cancelCurrentBaseStateRequestLocked(0);
    }

    private void cancelCurrentBaseStateRequestLocked(int i) {
        if (this.mBaseStateRequest == null) {
            android.util.Slog.w(TAG, "Attempted to cancel a null OverrideRequest");
        } else {
            cancelRequestLocked(this.mBaseStateRequest, i);
            this.mBaseStateRequest = null;
        }
    }

    private static boolean contains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
