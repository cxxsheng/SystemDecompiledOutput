package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class BiometricSchedulerOperation {
    private static final int CANCEL_WATCHDOG_DELAY_MS = 3000;
    protected static final int STATE_FINISHED = 5;
    protected static final int STATE_STARTED = 2;
    protected static final int STATE_STARTED_CANCELING = 3;
    protected static final int STATE_WAITING_FOR_COOKIE = 4;
    protected static final int STATE_WAITING_IN_QUEUE = 0;
    protected static final int STATE_WAITING_IN_QUEUE_CANCELING = 1;
    protected static final java.lang.String TAG = "BiometricSchedulerOperation";

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    final java.lang.Runnable mCancelWatchdog;

    @android.annotation.Nullable
    private final com.android.server.biometrics.sensors.ClientMonitorCallback mClientCallback;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BaseClientMonitor mClientMonitor;

    @android.annotation.NonNull
    private final java.util.function.BooleanSupplier mIsDebuggable;

    @android.annotation.Nullable
    private com.android.server.biometrics.sensors.ClientMonitorCallback mOnStartCallback;
    private int mState;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    protected @interface OperationState {
    }

    BiometricSchedulerOperation(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this(baseClientMonitor, clientMonitorCallback, 0);
    }

    @com.android.internal.annotations.VisibleForTesting
    BiometricSchedulerOperation(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, @android.annotation.NonNull java.util.function.BooleanSupplier booleanSupplier) {
        this(baseClientMonitor, clientMonitorCallback, 0, booleanSupplier);
    }

    protected BiometricSchedulerOperation(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, int i) {
        this(baseClientMonitor, clientMonitorCallback, i, new java.util.function.BooleanSupplier() { // from class: com.android.server.biometrics.sensors.BiometricSchedulerOperation$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return android.os.Build.isDebuggable();
            }
        });
    }

    private BiometricSchedulerOperation(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, int i, @android.annotation.NonNull java.util.function.BooleanSupplier booleanSupplier) {
        this.mClientMonitor = baseClientMonitor;
        this.mClientCallback = clientMonitorCallback;
        this.mState = i;
        this.mIsDebuggable = booleanSupplier;
        this.mCancelWatchdog = new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.BiometricSchedulerOperation$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.BiometricSchedulerOperation.this.lambda$new$0();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (!isFinished()) {
            android.util.Slog.e(TAG, "[Watchdog Triggered]: " + this);
            getWrappedCallback(this.mOnStartCallback).onClientFinished(this.mClientMonitor, false);
        }
    }

    public int isReadyToStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        if (this.mState == 4 || this.mState == 0) {
            int cookie = this.mClientMonitor.getCookie();
            if (cookie != 0) {
                this.mState = 4;
                this.mClientMonitor.waitForCookie(getWrappedCallback(clientMonitorCallback));
            }
            return cookie;
        }
        return 0;
    }

    public boolean start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        if (errorWhenNoneOf("start", 0, 4, 1)) {
            return false;
        }
        if (this.mClientMonitor.getCookie() != 0) {
            if (this.mIsDebuggable.getAsBoolean()) {
                throw new java.lang.IllegalStateException("operation requires cookie");
            }
            android.util.Slog.e(TAG, "operation requires cookie");
        }
        return doStart(clientMonitorCallback);
    }

    public boolean startWithCookie(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, int i) {
        if (this.mClientMonitor.getCookie() != i) {
            android.util.Slog.e(TAG, "Mismatched cookie for operation: " + this + ", received: " + i);
            return false;
        }
        if (errorWhenNoneOf("start", 0, 4, 1)) {
            return false;
        }
        return doStart(clientMonitorCallback);
    }

    private boolean doStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mOnStartCallback = clientMonitorCallback;
        com.android.server.biometrics.sensors.ClientMonitorCallback wrappedCallback = getWrappedCallback(clientMonitorCallback);
        if (this.mState == 1) {
            android.util.Slog.d(TAG, "Operation marked for cancellation, cancelling now: " + this);
            wrappedCallback.onClientFinished(this.mClientMonitor, true);
            if (this.mClientMonitor instanceof com.android.server.biometrics.sensors.ErrorConsumer) {
                ((com.android.server.biometrics.sensors.ErrorConsumer) this.mClientMonitor).onError(5, 0);
            } else {
                android.util.Slog.w(TAG, "monitor cancelled but does not implement ErrorConsumer");
            }
            return false;
        }
        if (isUnstartableHalOperation()) {
            android.util.Slog.v(TAG, "unable to start: " + this);
            ((com.android.server.biometrics.sensors.HalClientMonitor) this.mClientMonitor).unableToStart();
            wrappedCallback.onClientFinished(this.mClientMonitor, false);
            return false;
        }
        this.mState = 2;
        this.mClientMonitor.start(wrappedCallback);
        android.util.Slog.v(TAG, "started: " + this);
        return true;
    }

    public void abort() {
        if (errorWhenNoneOf("abort", 0, 4, 1)) {
            return;
        }
        if (isHalOperation()) {
            ((com.android.server.biometrics.sensors.HalClientMonitor) this.mClientMonitor).unableToStart();
        }
        getWrappedCallback().onClientFinished(this.mClientMonitor, false);
        android.util.Slog.v(TAG, "Aborted: " + this);
    }

    public boolean markCanceling() {
        if (this.mState == 0 && isInterruptable()) {
            this.mState = 1;
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    void markCancelingForWatchdog() {
        this.mState = 1;
    }

    public void cancel(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        if (errorWhenOneOf("cancel", 5)) {
            return;
        }
        int i = this.mState;
        if (i == 3) {
            android.util.Slog.w(TAG, "Cannot cancel - already invoked for operation: " + this);
            return;
        }
        this.mState = 3;
        if (i == 0 || i == 1 || i == 4) {
            android.util.Slog.d(TAG, "[Cancelling] Current client (without start): " + this.mClientMonitor);
            this.mClientMonitor.cancelWithoutStarting(getWrappedCallback(clientMonitorCallback));
        } else {
            android.util.Slog.d(TAG, "[Cancelling] Current client: " + this.mClientMonitor);
            this.mClientMonitor.cancel();
        }
        handler.postDelayed(this.mCancelWatchdog, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS);
    }

    @android.annotation.NonNull
    private com.android.server.biometrics.sensors.ClientMonitorCallback getWrappedCallback() {
        return getWrappedCallback(null);
    }

    @android.annotation.NonNull
    private com.android.server.biometrics.sensors.ClientMonitorCallback getWrappedCallback(@android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.BiometricSchedulerOperation.1
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                android.util.Slog.d(com.android.server.biometrics.sensors.BiometricSchedulerOperation.TAG, "[Finished / destroy]: " + baseClientMonitor);
                com.android.server.biometrics.sensors.BiometricSchedulerOperation.this.mClientMonitor.destroy();
                com.android.server.biometrics.sensors.BiometricSchedulerOperation.this.mState = 5;
            }
        }, clientMonitorCallback, this.mClientCallback);
    }

    public int getSensorId() {
        return this.mClientMonitor.getSensorId();
    }

    public int getProtoEnum() {
        return this.mClientMonitor.getProtoEnum();
    }

    public int getTargetUserId() {
        return this.mClientMonitor.getTargetUserId();
    }

    public boolean isFor(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        return this.mClientMonitor == baseClientMonitor;
    }

    public boolean isInterruptable() {
        return this.mClientMonitor.isInterruptable();
    }

    private boolean isHalOperation() {
        return this.mClientMonitor instanceof com.android.server.biometrics.sensors.HalClientMonitor;
    }

    private boolean isUnstartableHalOperation() {
        if (isHalOperation() && ((com.android.server.biometrics.sensors.HalClientMonitor) this.mClientMonitor).getFreshDaemon() == null) {
            return true;
        }
        return false;
    }

    public boolean isEnrollOperation() {
        return this.mClientMonitor instanceof com.android.server.biometrics.sensors.EnrollClient;
    }

    public boolean isAuthenticateOperation() {
        return this.mClientMonitor instanceof com.android.server.biometrics.sensors.AuthenticationClient;
    }

    public boolean isAuthenticationOrDetectionOperation() {
        return (this.mClientMonitor instanceof com.android.server.biometrics.sensors.AuthenticationConsumer) || (this.mClientMonitor instanceof com.android.server.biometrics.sensors.DetectionConsumer);
    }

    public boolean isStartUserOperation() {
        return this.mClientMonitor instanceof com.android.server.biometrics.sensors.StartUserClient;
    }

    public boolean isAcquisitionOperation() {
        return this.mClientMonitor instanceof com.android.server.biometrics.sensors.AcquisitionClient;
    }

    public boolean isMatchingRequestId(long j) {
        return !this.mClientMonitor.hasRequestId() || this.mClientMonitor.getRequestId() == j;
    }

    public boolean isMatchingToken(@android.annotation.Nullable android.os.IBinder iBinder) {
        return this.mClientMonitor.getToken() == iBinder;
    }

    public boolean isStarted() {
        return this.mState == 2;
    }

    public boolean isCanceling() {
        return this.mState == 3;
    }

    public boolean isFinished() {
        return this.mState == 5;
    }

    public boolean isMarkedCanceling() {
        return this.mState == 1;
    }

    @java.lang.Deprecated
    public com.android.server.biometrics.sensors.BaseClientMonitor getClientMonitor() {
        return this.mClientMonitor;
    }

    private boolean errorWhenOneOf(java.lang.String str, int... iArr) {
        boolean contains = com.android.internal.util.ArrayUtils.contains(iArr, this.mState);
        if (contains) {
            java.lang.String str2 = str + ": mState must not be " + this.mState;
            if (this.mIsDebuggable.getAsBoolean()) {
                throw new java.lang.IllegalStateException(str2);
            }
            android.util.Slog.e(TAG, str2);
        }
        return contains;
    }

    private boolean errorWhenNoneOf(java.lang.String str, int... iArr) {
        boolean z = !com.android.internal.util.ArrayUtils.contains(iArr, this.mState);
        if (z) {
            java.lang.String str2 = str + ": mState=" + this.mState + " must be one of " + java.util.Arrays.toString(iArr);
            if (this.mIsDebuggable.getAsBoolean()) {
                throw new java.lang.IllegalStateException(str2);
            }
            android.util.Slog.e(TAG, str2);
        }
        return z;
    }

    public java.lang.String toString() {
        return this.mClientMonitor + ", State: " + this.mState;
    }
}
