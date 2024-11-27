package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class BaseClientMonitor implements android.os.IBinder.DeathRecipient {
    protected static final boolean DEBUG = true;
    private static final java.lang.String TAG = "BaseClientMonitor";
    private static int sCount = 0;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final int mCookie;

    @android.annotation.NonNull
    private com.android.server.biometrics.sensors.ClientMonitorCallbackConverter mListener;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricLogger mLogger;

    @android.annotation.NonNull
    private final java.lang.String mOwner;
    private long mRequestId;
    private final int mSensorId;
    private final int mSequentialId;
    private final int mTargetUserId;

    @android.annotation.Nullable
    private android.os.IBinder mToken;
    private boolean mAlreadyDone = false;

    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallback mCallback = new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.BaseClientMonitor.1
        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
            android.util.Slog.e(com.android.server.biometrics.sensors.BaseClientMonitor.TAG, "mCallback onClientStarted: called before set (should not happen)");
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
            android.util.Slog.e(com.android.server.biometrics.sensors.BaseClientMonitor.TAG, "mCallback onClientFinished: called before set (should not happen)");
        }
    };

    public abstract int getProtoEnum();

    public BaseClientMonitor(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        int i4 = sCount;
        sCount = i4 + 1;
        this.mSequentialId = i4;
        this.mContext = context;
        this.mToken = iBinder;
        this.mRequestId = -1L;
        this.mListener = clientMonitorCallbackConverter == null ? new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter((android.hardware.biometrics.IBiometricSensorReceiver) new android.hardware.biometrics.IBiometricSensorReceiver.Default()) : clientMonitorCallbackConverter;
        this.mTargetUserId = i;
        this.mOwner = str;
        this.mCookie = i2;
        this.mSensorId = i3;
        this.mLogger = biometricLogger;
        this.mBiometricContext = biometricContext;
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "caught remote exception in linkToDeath: ", e);
            }
        }
    }

    public boolean interruptsPrecedingClients() {
        return false;
    }

    public void waitForCookie(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mCallback = clientMonitorCallback;
    }

    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mCallback = wrapCallbackForStart(clientMonitorCallback);
        this.mCallback.onClientStarted(this);
    }

    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallback wrapCallbackForStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        return clientMonitorCallback;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public void destroy() {
        this.mAlreadyDone = true;
        if (this.mToken != null) {
            try {
                this.mToken.unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Slog.e(TAG, "destroy(): " + this + ":", new java.lang.Exception("here"));
            }
            this.mToken = null;
        }
    }

    void markAlreadyDone() {
        android.util.Slog.d(TAG, "marking operation as done: " + this);
        this.mAlreadyDone = true;
    }

    public boolean isAlreadyDone() {
        return this.mAlreadyDone;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        binderDiedInternal(true);
    }

    void binderDiedInternal(boolean z) {
        android.util.Slog.e(TAG, "Binder died, operation: " + this);
        if (this.mAlreadyDone) {
            android.util.Slog.w(TAG, "Binder died but client is finished, ignoring");
            return;
        }
        if (isInterruptable()) {
            android.util.Slog.e(TAG, "Binder died, cancelling client");
            cancel();
        }
        this.mToken = null;
        if (z) {
            this.mListener = new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter((android.hardware.biometrics.IBiometricSensorReceiver) new android.hardware.biometrics.IBiometricSensorReceiver.Default());
        }
    }

    protected boolean isCryptoOperation() {
        return false;
    }

    @android.annotation.NonNull
    protected com.android.server.biometrics.log.BiometricContext getBiometricContext() {
        return this.mBiometricContext;
    }

    @android.annotation.NonNull
    public com.android.server.biometrics.log.BiometricLogger getLogger() {
        return this.mLogger;
    }

    @android.annotation.NonNull
    public final android.content.Context getContext() {
        return this.mContext;
    }

    @android.annotation.NonNull
    public final java.lang.String getOwnerString() {
        return this.mOwner;
    }

    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallbackConverter getListener() {
        return this.mListener;
    }

    public int getTargetUserId() {
        return this.mTargetUserId;
    }

    @android.annotation.Nullable
    public final android.os.IBinder getToken() {
        return this.mToken;
    }

    public int getSensorId() {
        return this.mSensorId;
    }

    public int getCookie() {
        return this.mCookie;
    }

    public long getRequestId() {
        return this.mRequestId;
    }

    public boolean hasRequestId() {
        return this.mRequestId > 0;
    }

    protected final void setRequestId(long j) {
        if (j <= 0) {
            throw new java.lang.IllegalArgumentException("request id must be positive");
        }
        this.mRequestId = j;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.biometrics.sensors.ClientMonitorCallback getCallback() {
        return this.mCallback;
    }

    public java.lang.String toString() {
        return "{[" + this.mSequentialId + "] " + getClass().getName() + ", proto=" + getProtoEnum() + ", owner=" + getOwnerString() + ", cookie=" + getCookie() + ", requestId=" + getRequestId() + ", userId=" + getTargetUserId() + "}";
    }

    public void cancel() {
        cancelWithoutStarting(this.mCallback);
    }

    public void cancelWithoutStarting(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        android.util.Slog.d(TAG, "cancelWithoutStarting: " + this);
        try {
            getListener().onError(getSensorId(), getCookie(), 5, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to invoke sendError", e);
        }
        clientMonitorCallback.onClientFinished(this, true);
    }

    public boolean isInterruptable() {
        return false;
    }
}
