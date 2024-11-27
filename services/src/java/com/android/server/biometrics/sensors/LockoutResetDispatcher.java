package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class LockoutResetDispatcher implements android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "LockoutResetTracker";

    @com.android.internal.annotations.VisibleForTesting
    final java.util.concurrent.ConcurrentLinkedQueue<com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback> mClientCallbacks = new java.util.concurrent.ConcurrentLinkedQueue<>();
    private final android.content.Context mContext;

    private static class ClientCallback {
        private static final long WAKELOCK_TIMEOUT_MS = 2000;
        private final android.hardware.biometrics.IBiometricServiceLockoutResetCallback mCallback;
        private final java.lang.String mOpPackageName;
        private final android.os.PowerManager.WakeLock mWakeLock;

        ClientCallback(android.content.Context context, android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) {
            android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
            this.mOpPackageName = str;
            this.mCallback = iBiometricServiceLockoutResetCallback;
            this.mWakeLock = powerManager.newWakeLock(1, "LockoutResetMonitor:SendLockoutReset");
        }

        void sendLockoutReset(int i) {
            if (this.mCallback != null) {
                try {
                    this.mWakeLock.acquire(WAKELOCK_TIMEOUT_MS);
                    this.mCallback.onLockoutReset(i, new android.os.IRemoteCallback.Stub() { // from class: com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback.1
                        public void sendResult(android.os.Bundle bundle) {
                            com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback.this.releaseWakelock();
                        }
                    });
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.biometrics.sensors.LockoutResetDispatcher.TAG, "Failed to invoke onLockoutReset: ", e);
                    releaseWakelock();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void releaseWakelock() {
            if (this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
    }

    public LockoutResetDispatcher(android.content.Context context) {
        this.mContext = context;
    }

    public void addCallback(android.hardware.biometrics.IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, java.lang.String str) {
        if (iBiometricServiceLockoutResetCallback == null) {
            android.util.Slog.w(TAG, "Callback from : " + str + " is null");
            return;
        }
        this.mClientCallbacks.add(new com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback(this.mContext, iBiometricServiceLockoutResetCallback, str));
        try {
            iBiometricServiceLockoutResetCallback.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to link to death", e);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(android.os.IBinder iBinder) {
        android.util.Slog.e(TAG, "Callback binder died: " + iBinder);
        java.util.Iterator<com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback> it = this.mClientCallbacks.iterator();
        while (it.hasNext()) {
            com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback next = it.next();
            if (next.mCallback.asBinder().equals(iBinder)) {
                android.util.Slog.e(TAG, "Removing dead callback for: " + next.mOpPackageName);
                next.releaseWakelock();
                it.remove();
            }
        }
    }

    public void notifyLockoutResetCallbacks(int i) {
        java.util.Iterator<com.android.server.biometrics.sensors.LockoutResetDispatcher.ClientCallback> it = this.mClientCallbacks.iterator();
        while (it.hasNext()) {
            it.next().sendLockoutReset(i);
        }
    }
}
