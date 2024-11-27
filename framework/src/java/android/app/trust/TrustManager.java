package android.app.trust;

/* loaded from: classes.dex */
public class TrustManager {
    private static final java.lang.String DATA_FLAGS = "initiatedByUser";
    private static final java.lang.String DATA_GRANTED_MESSAGES = "grantedMessages";
    private static final java.lang.String DATA_MESSAGE = "message";
    private static final java.lang.String DATA_NEWLY_UNLOCKED = "newlyUnlocked";
    private static final int MSG_ENABLED_TRUST_AGENTS_CHANGED = 4;
    private static final int MSG_IS_ACTIVE_UNLOCK_RUNNING = 5;
    private static final int MSG_TRUST_CHANGED = 1;
    private static final int MSG_TRUST_ERROR = 3;
    private static final int MSG_TRUST_MANAGED_CHANGED = 2;
    private static final java.lang.String TAG = "TrustManager";
    private final android.app.trust.ITrustManager mService;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.app.trust.TrustManager.2
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.os.Bundle peekData = message.peekData();
                    ((android.app.trust.TrustManager.TrustListener) message.obj).onTrustChanged(message.arg1 != 0, (peekData != null ? peekData.getInt(android.app.trust.TrustManager.DATA_NEWLY_UNLOCKED) : 0) != 0, message.arg2, peekData != null ? peekData.getInt(android.app.trust.TrustManager.DATA_FLAGS) : 0, message.getData().getStringArrayList(android.app.trust.TrustManager.DATA_GRANTED_MESSAGES));
                    break;
                case 2:
                    ((android.app.trust.TrustManager.TrustListener) message.obj).onTrustManagedChanged(message.arg1 != 0, message.arg2);
                    break;
                case 3:
                    ((android.app.trust.TrustManager.TrustListener) message.obj).onTrustError(message.peekData().getCharSequence("message"));
                    break;
                case 4:
                    ((android.app.trust.TrustManager.TrustListener) message.obj).onEnabledTrustAgentsChanged(message.arg1);
                    break;
                case 5:
                    ((android.app.trust.TrustManager.TrustListener) message.obj).onIsActiveUnlockRunningChanged(message.arg1 != 0, message.arg2);
                    break;
            }
        }
    };
    private final android.util.ArrayMap<android.app.trust.TrustManager.TrustListener, android.app.trust.ITrustListener> mTrustListeners = new android.util.ArrayMap<>();

    public interface TrustListener {
        void onEnabledTrustAgentsChanged(int i);

        void onIsActiveUnlockRunningChanged(boolean z, int i);

        void onTrustChanged(boolean z, boolean z2, int i, int i2, java.util.List<java.lang.String> list);

        void onTrustError(java.lang.CharSequence charSequence);

        void onTrustManagedChanged(boolean z, int i);
    }

    public TrustManager(android.os.IBinder iBinder) {
        this.mService = android.app.trust.ITrustManager.Stub.asInterface(iBinder);
    }

    public void setDeviceLockedForUser(int i, boolean z) {
        try {
            this.mService.setDeviceLockedForUser(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUnlockAttempt(boolean z, int i) {
        try {
            this.mService.reportUnlockAttempt(z, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserRequestedUnlock(int i, boolean z) {
        try {
            this.mService.reportUserRequestedUnlock(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserMayRequestUnlock(int i) {
        try {
            this.mService.reportUserMayRequestUnlock(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUnlockLockout(int i, int i2) {
        try {
            this.mService.reportUnlockLockout(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportEnabledTrustAgentsChanged(int i) {
        try {
            this.mService.reportEnabledTrustAgentsChanged(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportKeyguardShowingChanged() {
        try {
            this.mService.reportKeyguardShowingChanged();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActiveUnlockRunning(int i) {
        try {
            return this.mService.isActiveUnlockRunning(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTrustListener(final android.app.trust.TrustManager.TrustListener trustListener) {
        try {
            android.app.trust.ITrustListener.Stub stub = new android.app.trust.ITrustListener.Stub() { // from class: android.app.trust.TrustManager.1
                @Override // android.app.trust.ITrustListener
                public void onTrustChanged(boolean z, boolean z2, int i, int i2, java.util.List<java.lang.String> list) {
                    android.os.Message obtainMessage = android.app.trust.TrustManager.this.mHandler.obtainMessage(1, z ? 1 : 0, i, trustListener);
                    if (i2 != 0) {
                        obtainMessage.getData().putInt(android.app.trust.TrustManager.DATA_FLAGS, i2);
                    }
                    obtainMessage.getData().putInt(android.app.trust.TrustManager.DATA_NEWLY_UNLOCKED, z2 ? 1 : 0);
                    obtainMessage.getData().putCharSequenceArrayList(android.app.trust.TrustManager.DATA_GRANTED_MESSAGES, (java.util.ArrayList) list);
                    obtainMessage.sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onEnabledTrustAgentsChanged(int i) {
                    android.app.trust.TrustManager.this.mHandler.obtainMessage(4, i, 0, trustListener).sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onTrustManagedChanged(boolean z, int i) {
                    android.app.trust.TrustManager.this.mHandler.obtainMessage(2, z ? 1 : 0, i, trustListener).sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onTrustError(java.lang.CharSequence charSequence) {
                    android.os.Message obtainMessage = android.app.trust.TrustManager.this.mHandler.obtainMessage(3, trustListener);
                    obtainMessage.getData().putCharSequence("message", charSequence);
                    obtainMessage.sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onIsActiveUnlockRunningChanged(boolean z, int i) {
                    android.app.trust.TrustManager.this.mHandler.obtainMessage(5, z ? 1 : 0, i, trustListener).sendToTarget();
                }
            };
            this.mService.registerTrustListener(stub);
            this.mTrustListeners.put(trustListener, stub);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterTrustListener(android.app.trust.TrustManager.TrustListener trustListener) {
        android.app.trust.ITrustListener remove = this.mTrustListeners.remove(trustListener);
        if (remove != null) {
            try {
                this.mService.unregisterTrustListener(remove);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isTrustUsuallyManaged(int i) {
        try {
            return this.mService.isTrustUsuallyManaged(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unlockedByBiometricForUser(int i, android.hardware.biometrics.BiometricSourceType biometricSourceType) {
        try {
            this.mService.unlockedByBiometricForUser(i, biometricSourceType);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearAllBiometricRecognized(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i) {
        try {
            this.mService.clearAllBiometricRecognized(biometricSourceType, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
