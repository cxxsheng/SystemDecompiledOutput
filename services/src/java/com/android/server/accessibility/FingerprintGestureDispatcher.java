package com.android.server.accessibility;

/* loaded from: classes.dex */
public class FingerprintGestureDispatcher extends android.hardware.fingerprint.IFingerprintClientActiveCallback.Stub implements android.os.Handler.Callback {
    private static final java.lang.String LOG_TAG = "FingerprintGestureDispatcher";
    private static final int MSG_REGISTER = 1;
    private static final int MSG_UNREGISTER = 2;
    private final java.util.List<com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient> mCapturingClients;
    private final android.hardware.fingerprint.IFingerprintService mFingerprintService;
    private final android.os.Handler mHandler;
    private final boolean mHardwareSupportsGestures;
    private final java.lang.Object mLock;
    private boolean mRegisteredReadOnlyExceptInHandler;

    public interface FingerprintGestureClient {
        boolean isCapturingFingerprintGestures();

        void onFingerprintGesture(int i);

        void onFingerprintGestureDetectionActiveChanged(boolean z);
    }

    public FingerprintGestureDispatcher(android.hardware.fingerprint.IFingerprintService iFingerprintService, android.content.res.Resources resources, java.lang.Object obj) {
        this.mCapturingClients = new java.util.ArrayList(0);
        this.mFingerprintService = iFingerprintService;
        this.mHardwareSupportsGestures = resources.getBoolean(android.R.bool.config_faceAuthSupportsSelfIllumination);
        this.mLock = obj;
        this.mHandler = new android.os.Handler(this);
    }

    public FingerprintGestureDispatcher(android.hardware.fingerprint.IFingerprintService iFingerprintService, android.content.res.Resources resources, java.lang.Object obj, android.os.Handler handler) {
        this.mCapturingClients = new java.util.ArrayList(0);
        this.mFingerprintService = iFingerprintService;
        this.mHardwareSupportsGestures = resources.getBoolean(android.R.bool.config_faceAuthSupportsSelfIllumination);
        this.mLock = obj;
        this.mHandler = handler;
    }

    public void updateClientList(java.util.List<? extends com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient> list) {
        if (this.mHardwareSupportsGestures) {
            synchronized (this.mLock) {
                try {
                    this.mCapturingClients.clear();
                    for (int i = 0; i < list.size(); i++) {
                        com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient fingerprintGestureClient = list.get(i);
                        if (fingerprintGestureClient.isCapturingFingerprintGestures()) {
                            this.mCapturingClients.add(fingerprintGestureClient);
                        }
                    }
                    if (this.mCapturingClients.isEmpty()) {
                        if (this.mRegisteredReadOnlyExceptInHandler) {
                            this.mHandler.obtainMessage(2).sendToTarget();
                        }
                    } else if (!this.mRegisteredReadOnlyExceptInHandler) {
                        this.mHandler.obtainMessage(1).sendToTarget();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void onClientActiveChanged(boolean z) {
        if (this.mHardwareSupportsGestures) {
            synchronized (this.mLock) {
                for (int i = 0; i < this.mCapturingClients.size(); i++) {
                    try {
                        this.mCapturingClients.get(i).onFingerprintGestureDetectionActiveChanged(!z);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public boolean isFingerprintGestureDetectionAvailable() {
        if (!this.mHardwareSupportsGestures) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return !this.mFingerprintService.isClientActive();
        } catch (android.os.RemoteException e) {
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean onFingerprintGesture(int i) {
        int i2;
        synchronized (this.mLock) {
            try {
                if (this.mCapturingClients.isEmpty()) {
                    return false;
                }
                switch (i) {
                    case com.android.internal.util.FrameworkStatsLog.TV_CAS_SESSION_OPEN_STATUS /* 280 */:
                        i2 = 4;
                        break;
                    case com.android.internal.util.FrameworkStatsLog.ASSISTANT_INVOCATION_REPORTED /* 281 */:
                        i2 = 8;
                        break;
                    case com.android.internal.util.FrameworkStatsLog.DISPLAY_WAKE_REPORTED /* 282 */:
                        i2 = 2;
                        break;
                    case 283:
                        i2 = 1;
                        break;
                    default:
                        return false;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mCapturingClients);
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    ((com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient) arrayList.get(i3)).onFingerprintGesture(i2);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        long clearCallingIdentity;
        if (message.what == 1) {
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    this.mFingerprintService.addClientActiveCallback(this);
                    this.mRegisteredReadOnlyExceptInHandler = true;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(LOG_TAG, "Failed to register for fingerprint activity callbacks");
                }
                return false;
            } finally {
            }
        }
        if (message.what == 2) {
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    this.mFingerprintService.removeClientActiveCallback(this);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(LOG_TAG, "Failed to unregister for fingerprint activity callbacks");
                }
                this.mRegisteredReadOnlyExceptInHandler = false;
                return true;
            } finally {
            }
        }
        android.util.Slog.e(LOG_TAG, "Unknown message: " + message.what);
        return false;
    }
}
