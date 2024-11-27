package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class BiometricStateCallback<T extends com.android.server.biometrics.sensors.BiometricServiceProvider<P>, P extends android.hardware.biometrics.SensorPropertiesInternal> implements com.android.server.biometrics.sensors.ClientMonitorCallback, android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "BiometricStateCallback";

    @android.annotation.NonNull
    private final android.os.UserManager mUserManager;

    @android.annotation.NonNull
    private final java.util.concurrent.CopyOnWriteArrayList<android.hardware.biometrics.IBiometricStateListener> mBiometricStateListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    @android.annotation.NonNull
    private java.util.List<T> mProviders = java.util.List.of();
    private int mBiometricState = 0;

    public BiometricStateCallback(@android.annotation.NonNull android.os.UserManager userManager) {
        this.mUserManager = userManager;
    }

    public synchronized void start(@android.annotation.NonNull java.util.List<T> list) {
        this.mProviders = java.util.Collections.unmodifiableList(list);
        broadcastCurrentEnrollmentState(null);
    }

    public int getBiometricState() {
        return this.mBiometricState;
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        int i = this.mBiometricState;
        if (baseClientMonitor instanceof com.android.server.biometrics.sensors.AuthenticationClient) {
            com.android.server.biometrics.sensors.AuthenticationClient authenticationClient = (com.android.server.biometrics.sensors.AuthenticationClient) baseClientMonitor;
            if (authenticationClient.isKeyguard()) {
                this.mBiometricState = 2;
            } else if (authenticationClient.isBiometricPrompt()) {
                this.mBiometricState = 3;
            } else {
                this.mBiometricState = 4;
            }
        } else if (baseClientMonitor instanceof com.android.server.biometrics.sensors.EnrollClient) {
            this.mBiometricState = 1;
        } else {
            android.util.Slog.w(TAG, "Other authentication client: " + com.android.server.biometrics.Utils.getClientName(baseClientMonitor));
            this.mBiometricState = 0;
        }
        android.util.Slog.d(TAG, "State updated from " + i + " to " + this.mBiometricState + ", client " + baseClientMonitor);
        notifyBiometricStateListeners(this.mBiometricState);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
        this.mBiometricState = 0;
        android.util.Slog.d(TAG, "Client finished, state updated to " + this.mBiometricState + ", client " + baseClientMonitor);
        if (baseClientMonitor instanceof com.android.server.biometrics.sensors.EnrollmentModifier) {
            com.android.server.biometrics.sensors.EnrollmentModifier enrollmentModifier = (com.android.server.biometrics.sensors.EnrollmentModifier) baseClientMonitor;
            boolean hasEnrollmentStateChanged = enrollmentModifier.hasEnrollmentStateChanged();
            android.util.Slog.d(TAG, "Enrollment state changed: " + hasEnrollmentStateChanged);
            if (hasEnrollmentStateChanged) {
                notifyAllEnrollmentStateChanged(baseClientMonitor.getTargetUserId(), baseClientMonitor.getSensorId(), enrollmentModifier.hasEnrollments());
            }
        }
        notifyBiometricStateListeners(this.mBiometricState);
    }

    private void notifyBiometricStateListeners(int i) {
        java.util.Iterator<android.hardware.biometrics.IBiometricStateListener> it = this.mBiometricStateListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onStateChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in biometric state change", e);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onBiometricAction(int i) {
        java.util.Iterator<android.hardware.biometrics.IBiometricStateListener> it = this.mBiometricStateListeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().onBiometricAction(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in onBiometricAction", e);
            }
        }
    }

    public synchronized void registerBiometricStateListener(@android.annotation.NonNull android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) {
        this.mBiometricStateListeners.add(iBiometricStateListener);
        broadcastCurrentEnrollmentState(iBiometricStateListener);
        try {
            iBiometricStateListener.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to link to death", e);
        }
    }

    private synchronized void broadcastCurrentEnrollmentState(@android.annotation.Nullable android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) {
        try {
            for (T t : this.mProviders) {
                for (android.hardware.biometrics.SensorPropertiesInternal sensorPropertiesInternal : t.getSensorProperties()) {
                    for (android.content.pm.UserInfo userInfo : this.mUserManager.getAliveUsers()) {
                        boolean hasEnrollments = t.hasEnrollments(sensorPropertiesInternal.sensorId, userInfo.id);
                        if (iBiometricStateListener != null) {
                            notifyEnrollmentStateChanged(iBiometricStateListener, userInfo.id, sensorPropertiesInternal.sensorId, hasEnrollments);
                        } else {
                            notifyAllEnrollmentStateChanged(userInfo.id, sensorPropertiesInternal.sensorId, hasEnrollments);
                        }
                    }
                }
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void notifyAllEnrollmentStateChanged(int i, int i2, boolean z) {
        java.util.Iterator<android.hardware.biometrics.IBiometricStateListener> it = this.mBiometricStateListeners.iterator();
        while (it.hasNext()) {
            notifyEnrollmentStateChanged(it.next(), i, i2, z);
        }
    }

    private void notifyEnrollmentStateChanged(@android.annotation.NonNull android.hardware.biometrics.IBiometricStateListener iBiometricStateListener, int i, int i2, boolean z) {
        try {
            iBiometricStateListener.onEnrollmentsChanged(i, i2, z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(final android.os.IBinder iBinder) {
        android.util.Slog.w(TAG, "Callback binder died: " + iBinder);
        if (this.mBiometricStateListeners.removeIf(new java.util.function.Predicate() { // from class: com.android.server.biometrics.sensors.BiometricStateCallback$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$binderDied$0;
                lambda$binderDied$0 = com.android.server.biometrics.sensors.BiometricStateCallback.lambda$binderDied$0(iBinder, (android.hardware.biometrics.IBiometricStateListener) obj);
                return lambda$binderDied$0;
            }
        })) {
            android.util.Slog.w(TAG, "Removed dead listener for " + iBinder);
            return;
        }
        android.util.Slog.w(TAG, "No dead listeners found");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$binderDied$0(android.os.IBinder iBinder, android.hardware.biometrics.IBiometricStateListener iBiometricStateListener) {
        return iBiometricStateListener.asBinder().equals(iBinder);
    }
}
