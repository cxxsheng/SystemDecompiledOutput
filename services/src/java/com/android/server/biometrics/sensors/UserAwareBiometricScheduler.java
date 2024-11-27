package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class UserAwareBiometricScheduler<T, U> extends com.android.server.biometrics.sensors.BiometricScheduler<T, U> {
    private static final java.lang.String TAG = "UaBiometricScheduler";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever mCurrentUserRetriever;

    @android.annotation.Nullable
    private com.android.server.biometrics.sensors.StopUserClient<?> mStopUserClient;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback mUserSwitchCallback;

    public interface CurrentUserRetriever {
        int getCurrentUserId();
    }

    public interface UserSwitchCallback {
        @android.annotation.NonNull
        com.android.server.biometrics.sensors.StartUserClient<?, ?> getStartUserClient(int i);

        @android.annotation.NonNull
        com.android.server.biometrics.sensors.StopUserClient<?> getStopUserClient(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ClientFinishedCallback implements com.android.server.biometrics.sensors.ClientMonitorCallback {

        @android.annotation.NonNull
        private final com.android.server.biometrics.sensors.BaseClientMonitor mOwner;

        ClientFinishedCallback(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
            this.mOwner = baseClientMonitor;
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public void onClientFinished(@android.annotation.NonNull final com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, final boolean z) {
            com.android.server.biometrics.sensors.UserAwareBiometricScheduler.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.UserAwareBiometricScheduler$ClientFinishedCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.UserAwareBiometricScheduler.ClientFinishedCallback.this.lambda$onClientFinished$0(baseClientMonitor, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientFinished$0(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
            android.util.Slog.d(com.android.server.biometrics.sensors.UserAwareBiometricScheduler.TAG, "[Client finished] " + baseClientMonitor + ", success: " + z);
            if (baseClientMonitor instanceof com.android.server.biometrics.sensors.StopUserClient) {
                if (!z) {
                    android.util.Slog.w(com.android.server.biometrics.sensors.UserAwareBiometricScheduler.TAG, "StopUserClient failed(), is the HAL stuck? Clearing mStopUserClient");
                }
                com.android.server.biometrics.sensors.UserAwareBiometricScheduler.this.mStopUserClient = null;
            }
            if (com.android.server.biometrics.sensors.UserAwareBiometricScheduler.this.mCurrentOperation != null && com.android.server.biometrics.sensors.UserAwareBiometricScheduler.this.mCurrentOperation.isFor(this.mOwner)) {
                com.android.server.biometrics.sensors.UserAwareBiometricScheduler.this.mCurrentOperation = null;
            } else {
                android.util.Slog.w(com.android.server.biometrics.sensors.UserAwareBiometricScheduler.TAG, "operation is already null or different (reset?): " + com.android.server.biometrics.sensors.UserAwareBiometricScheduler.this.mCurrentOperation);
            }
            com.android.server.biometrics.sensors.UserAwareBiometricScheduler.this.startNextOperationIfIdle();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public UserAwareBiometricScheduler(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.Handler handler, int i, @android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull android.hardware.biometrics.IBiometricService iBiometricService, @android.annotation.NonNull com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever currentUserRetriever, @android.annotation.NonNull com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback userSwitchCallback) {
        super(handler, i, gestureAvailabilityDispatcher, iBiometricService, 50);
        this.mCurrentUserRetriever = currentUserRetriever;
        this.mUserSwitchCallback = userSwitchCallback;
    }

    public UserAwareBiometricScheduler(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever currentUserRetriever, @android.annotation.NonNull com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback userSwitchCallback) {
        this(str, new android.os.Handler(android.os.Looper.getMainLooper()), i, gestureAvailabilityDispatcher, android.hardware.biometrics.IBiometricService.Stub.asInterface(android.os.ServiceManager.getService("biometric")), currentUserRetriever, userSwitchCallback);
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    protected void startNextOperationIfIdle() {
        if (this.mCurrentOperation != null) {
            android.util.Slog.v(TAG, "Not idle, current operation: " + this.mCurrentOperation);
            return;
        }
        if (this.mPendingOperations.isEmpty()) {
            android.util.Slog.d(TAG, "No operations, returning to idle");
            return;
        }
        int currentUserId = this.mCurrentUserRetriever.getCurrentUserId();
        int targetUserId = this.mPendingOperations.getFirst().getTargetUserId();
        if (targetUserId == currentUserId || this.mPendingOperations.getFirst().isStartUserOperation()) {
            super.startNextOperationIfIdle();
            return;
        }
        if (currentUserId == -10000) {
            com.android.server.biometrics.sensors.StartUserClient<?, ?> startUserClient = this.mUserSwitchCallback.getStartUserClient(targetUserId);
            com.android.server.biometrics.sensors.UserAwareBiometricScheduler.ClientFinishedCallback clientFinishedCallback = new com.android.server.biometrics.sensors.UserAwareBiometricScheduler.ClientFinishedCallback(startUserClient);
            android.util.Slog.d(TAG, "[Starting User] " + startUserClient);
            this.mCurrentOperation = new com.android.server.biometrics.sensors.BiometricSchedulerOperation(startUserClient, clientFinishedCallback, 2);
            startUserClient.start(clientFinishedCallback);
            return;
        }
        if (this.mStopUserClient != null) {
            android.util.Slog.d(TAG, "[Waiting for StopUser] " + this.mStopUserClient);
            return;
        }
        this.mStopUserClient = this.mUserSwitchCallback.getStopUserClient(currentUserId);
        com.android.server.biometrics.sensors.UserAwareBiometricScheduler.ClientFinishedCallback clientFinishedCallback2 = new com.android.server.biometrics.sensors.UserAwareBiometricScheduler.ClientFinishedCallback(this.mStopUserClient);
        android.util.Slog.d(TAG, "[Stopping User] current: " + currentUserId + ", next: " + targetUserId + ". " + this.mStopUserClient);
        this.mCurrentOperation = new com.android.server.biometrics.sensors.BiometricSchedulerOperation(this.mStopUserClient, clientFinishedCallback2, 2);
        this.mStopUserClient.start(clientFinishedCallback2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    public void onUserStopped() {
        if (this.mStopUserClient == null) {
            android.util.Slog.e(TAG, "Unexpected onUserStopped");
            return;
        }
        android.util.Slog.d(TAG, "[OnUserStopped]: " + this.mStopUserClient);
        this.mStopUserClient.onUserStopped();
        this.mStopUserClient = null;
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public com.android.server.biometrics.sensors.StopUserClient<?> getStopUserClient() {
        return this.mStopUserClient;
    }
}
