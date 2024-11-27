package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class AuthenticationClient<T, O extends android.hardware.biometrics.AuthenticateOptions> extends com.android.server.biometrics.sensors.AcquisitionClient<T> implements com.android.server.biometrics.sensors.AuthenticationConsumer {
    public static final int STATE_NEW = 0;
    public static final int STATE_STARTED = 1;
    public static final int STATE_STARTED_PAUSED = 2;
    public static final int STATE_STARTED_PAUSED_ATTEMPTED = 3;
    public static final int STATE_STOPPED = 4;
    private static final java.lang.String TAG = "Biometrics/AuthenticationClient";
    private final android.app.ActivityTaskManager mActivityTaskManager;
    private final boolean mAllowBackgroundAuthentication;
    private boolean mAuthAttempted;
    private boolean mAuthSuccess;
    private final android.hardware.biometrics.BiometricManager mBiometricManager;
    private final boolean mIsRestricted;
    private final boolean mIsStrongBiometric;
    private final com.android.server.biometrics.sensors.LockoutTracker mLockoutTracker;
    protected final long mOperationId;
    private final O mOptions;
    private final boolean mRequireConfirmation;
    private final int mSensorStrength;
    private final boolean mShouldUseLockoutTracker;
    private long mStartTimeMs;

    @com.android.server.biometrics.sensors.AuthenticationClient.State
    protected int mState;

    @android.annotation.Nullable
    private final android.app.TaskStackListener mTaskStackListener;

    @interface State {
    }

    protected abstract void handleLifecycleAfterAuth(boolean z);

    public abstract boolean wasUserDetected();

    public AuthenticationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j, boolean z, @android.annotation.NonNull O o, int i, boolean z2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z3, @android.annotation.Nullable android.app.TaskStackListener taskStackListener, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, boolean z4, boolean z5, int i2) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, o.getUserId(), o.getOpPackageName(), i, o.getSensorId(), z5, biometricLogger, biometricContext);
        this.mState = 0;
        this.mAuthSuccess = false;
        this.mIsStrongBiometric = z3;
        this.mOperationId = j;
        this.mRequireConfirmation = z2;
        this.mActivityTaskManager = getActivityTaskManager();
        this.mBiometricManager = (android.hardware.biometrics.BiometricManager) context.getSystemService(android.hardware.biometrics.BiometricManager.class);
        this.mTaskStackListener = taskStackListener;
        this.mLockoutTracker = lockoutTracker;
        this.mIsRestricted = z;
        this.mAllowBackgroundAuthentication = z4;
        this.mShouldUseLockoutTracker = lockoutTracker != null;
        this.mSensorStrength = i2;
        this.mOptions = o;
    }

    public int handleFailedAttempt(int i) {
        com.android.server.biometrics.Flags.deHidl();
        return 0;
    }

    protected long getStartTimeMs() {
        return this.mStartTimeMs;
    }

    protected android.app.ActivityTaskManager getActivityTaskManager() {
        return android.app.ActivityTaskManager.getInstance();
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor, android.os.IBinder.DeathRecipient
    public void binderDied() {
        binderDiedInternal(!isBiometricPrompt());
    }

    public long getOperationId() {
        return this.mOperationId;
    }

    public boolean isRestricted() {
        return this.mIsRestricted;
    }

    public boolean isKeyguard() {
        return com.android.server.biometrics.Utils.isKeyguard(getContext(), getOwnerString());
    }

    private boolean isSettings() {
        return com.android.server.biometrics.Utils.isSettings(getContext(), getOwnerString());
    }

    protected O getOptions() {
        return this.mOptions;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    protected boolean isCryptoOperation() {
        return this.mOperationId != 0;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, boolean z, java.util.ArrayList<java.lang.Byte> arrayList) {
        boolean z2;
        boolean z3 = z;
        getLogger().logOnAuthenticated(getContext(), getOperationContext(), z, this.mRequireConfirmation, getTargetUserId(), isBiometricPrompt());
        com.android.server.biometrics.sensors.ClientMonitorCallbackConverter listener = getListener();
        android.util.Slog.v(TAG, "onAuthenticated(" + z3 + "), ID:" + identifier.getBiometricId() + ", Owner: " + getOwnerString() + ", isBP: " + isBiometricPrompt() + ", listener: " + listener + ", requireConfirmation: " + this.mRequireConfirmation + ", user: " + getTargetUserId() + ", clientMonitor: " + this);
        com.android.server.biometrics.sensors.PerformanceTracker instanceForSensorId = com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId());
        if (isCryptoOperation()) {
            instanceForSensorId.incrementCryptoAuthForUser(getTargetUserId(), z3);
        } else {
            instanceForSensorId.incrementAuthForUser(getTargetUserId(), z3);
        }
        if (this.mAllowBackgroundAuthentication) {
            android.util.Slog.w(TAG, "Allowing background authentication, this is allowed only for platform or test invocations");
        }
        if (!this.mAllowBackgroundAuthentication && z3 && !com.android.server.biometrics.Utils.isKeyguard(getContext(), getOwnerString()) && !com.android.server.biometrics.Utils.isSystem(getContext(), getOwnerString())) {
            z2 = com.android.server.biometrics.Utils.isBackground(getOwnerString());
        } else {
            z2 = false;
        }
        if (z2) {
            android.util.Slog.e(TAG, "Failing possible background authentication");
            android.content.pm.ApplicationInfo applicationInfo = getContext().getApplicationInfo();
            android.util.EventLog.writeEvent(1397638484, "159249069", java.lang.Integer.valueOf(applicationInfo != null ? applicationInfo.uid : -1), "Attempted background authentication");
            z3 = false;
        }
        if (z3) {
            if (z2) {
                android.content.pm.ApplicationInfo applicationInfo2 = getContext().getApplicationInfo();
                android.util.EventLog.writeEvent(1397638484, "159249069", java.lang.Integer.valueOf(applicationInfo2 != null ? applicationInfo2.uid : -1), "Successful background authentication!");
            }
            this.mAuthSuccess = true;
            markAlreadyDone();
            if (this.mTaskStackListener != null) {
                this.mActivityTaskManager.unregisterTaskStackListener(this.mTaskStackListener);
            }
            byte[] bArr = new byte[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                bArr[i] = arrayList.get(i).byteValue();
            }
            if (this.mIsStrongBiometric) {
                this.mBiometricManager.resetLockoutTimeBound(getToken(), getContext().getOpPackageName(), getSensorId(), getTargetUserId(), bArr);
            }
            if (isBiometricPrompt() || !this.mIsStrongBiometric) {
                android.util.Slog.d(TAG, "Skipping addAuthToken");
            } else {
                int addAuthToken = android.security.KeyStore.getInstance().addAuthToken(bArr);
                if (addAuthToken != 1) {
                    android.util.Slog.d(TAG, "Error adding auth token : " + addAuthToken);
                } else {
                    android.util.Slog.d(TAG, "addAuthToken: " + addAuthToken);
                }
            }
            try {
                if (!this.mIsRestricted) {
                    listener.onAuthenticationSucceeded(getSensorId(), identifier, bArr, getTargetUserId(), this.mIsStrongBiometric);
                } else {
                    listener.onAuthenticationSucceeded(getSensorId(), null, bArr, getTargetUserId(), this.mIsStrongBiometric);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to notify listener", e);
                this.mCallback.onClientFinished(this, false);
                return;
            }
        } else if (z2) {
            android.util.Slog.e(TAG, "Sending cancel to client(Due to background auth)");
            if (this.mTaskStackListener != null) {
                this.mActivityTaskManager.unregisterTaskStackListener(this.mTaskStackListener);
            }
            sendCancelOnly(getListener());
            this.mCallback.onClientFinished(this, false);
        } else {
            if (this.mShouldUseLockoutTracker && handleFailedAttempt(getTargetUserId()) != 0) {
                markAlreadyDone();
            }
            try {
                listener.onAuthenticationFailed(getSensorId());
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Unable to notify listener", e2);
                this.mCallback.onClientFinished(this, false);
                return;
            }
        }
        handleLifecycleAfterAuth(z3);
    }

    private void sendCancelOnly(@android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter) {
        if (clientMonitorCallbackConverter == null) {
            android.util.Slog.e(TAG, "Unable to sendAuthenticationCanceled, listener null");
            return;
        }
        try {
            clientMonitorCallbackConverter.onError(getSensorId(), getCookie(), 5, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        super.onAcquired(i, i2);
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        super.onError(i, i2);
        this.mState = 4;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        int lockoutStateFor;
        int i;
        super.start(clientMonitorCallback);
        if (this.mShouldUseLockoutTracker) {
            lockoutStateFor = this.mLockoutTracker.getLockoutModeForUser(getTargetUserId());
        } else {
            lockoutStateFor = getBiometricContext().getAuthSessionCoordinator().getLockoutStateFor(getTargetUserId(), this.mSensorStrength);
        }
        if (lockoutStateFor != 0) {
            android.util.Slog.v(TAG, "In lockout mode(" + lockoutStateFor + ") ; disallowing authentication");
            if (lockoutStateFor == 1) {
                i = 7;
            } else {
                i = 9;
            }
            onError(i, 0);
            return;
        }
        if (this.mTaskStackListener != null) {
            this.mActivityTaskManager.registerTaskStackListener(this.mTaskStackListener);
        }
        android.util.Slog.d(TAG, "Requesting auth for " + getOwnerString());
        this.mStartTimeMs = java.lang.System.currentTimeMillis();
        this.mAuthAttempted = true;
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void cancel() {
        super.cancel();
        if (this.mTaskStackListener != null) {
            this.mActivityTaskManager.unregisterTaskStackListener(this.mTaskStackListener);
        }
    }

    @com.android.server.biometrics.sensors.AuthenticationClient.State
    public int getState() {
        return this.mState;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 3;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        return true;
    }

    public boolean wasAuthAttempted() {
        return this.mAuthAttempted;
    }

    public boolean wasAuthSuccessful() {
        return this.mAuthSuccess;
    }

    protected int getSensorStrength() {
        return this.mSensorStrength;
    }

    protected com.android.server.biometrics.sensors.LockoutTracker getLockoutTracker() {
        return this.mLockoutTracker;
    }

    protected int getRequestReason() {
        if (isKeyguard()) {
            return 4;
        }
        if (isBiometricPrompt()) {
            return 3;
        }
        if (isSettings()) {
            return 6;
        }
        return 5;
    }
}
