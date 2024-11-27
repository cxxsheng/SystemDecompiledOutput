package android.service.trust;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class TrustAgentService extends android.app.Service {
    private static final boolean DEBUG = false;
    private static final java.lang.String EXTRA_TOKEN = "token";
    private static final java.lang.String EXTRA_TOKEN_HANDLE = "token_handle";
    private static final java.lang.String EXTRA_TOKEN_REMOVED_RESULT = "token_removed_result";
    private static final java.lang.String EXTRA_TOKEN_STATE = "token_state";
    private static final java.lang.String EXTRA_USER_HANDLE = "user_handle";
    public static final int FLAG_GRANT_TRUST_DISMISS_KEYGUARD = 2;
    public static final int FLAG_GRANT_TRUST_DISPLAY_MESSAGE = 8;
    public static final int FLAG_GRANT_TRUST_INITIATED_BY_USER = 1;
    public static final int FLAG_GRANT_TRUST_TEMPORARY_AND_RENEWABLE = 4;
    private static final int MSG_CONFIGURE = 2;
    private static final int MSG_DEVICE_LOCKED = 4;
    private static final int MSG_DEVICE_UNLOCKED = 5;
    private static final int MSG_ESCROW_TOKEN_ADDED = 7;
    private static final int MSG_ESCROW_TOKEN_REMOVED = 9;
    private static final int MSG_ESCROW_TOKEN_STATE_RECEIVED = 8;
    private static final int MSG_TRUST_TIMEOUT = 3;
    private static final int MSG_UNLOCK_ATTEMPT = 1;
    private static final int MSG_UNLOCK_LOCKOUT = 6;
    private static final int MSG_USER_MAY_REQUEST_UNLOCK = 11;
    private static final int MSG_USER_REQUESTED_UNLOCK = 10;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.trust.TrustAgentService";
    public static final int TOKEN_STATE_ACTIVE = 1;
    public static final int TOKEN_STATE_INACTIVE = 0;
    public static final java.lang.String TRUST_AGENT_META_DATA = "android.service.trust.trustagent";
    private android.service.trust.ITrustAgentServiceCallback mCallback;
    private boolean mManagingTrust;
    private java.lang.Runnable mPendingGrantTrustTask;
    private final java.lang.String TAG = android.service.trust.TrustAgentService.class.getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + getClass().getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.os.Handler mHandler = new android.os.Handler() { // from class: android.service.trust.TrustAgentService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.service.trust.TrustAgentService.this.onUnlockAttempt(message.arg1 != 0);
                    break;
                case 2:
                    android.service.trust.TrustAgentService.ConfigurationData configurationData = (android.service.trust.TrustAgentService.ConfigurationData) message.obj;
                    boolean onConfigure = android.service.trust.TrustAgentService.this.onConfigure(configurationData.options);
                    if (configurationData.token != null) {
                        try {
                            synchronized (android.service.trust.TrustAgentService.this.mLock) {
                                android.service.trust.TrustAgentService.this.mCallback.onConfigureCompleted(onConfigure, configurationData.token);
                            }
                            break;
                        } catch (android.os.RemoteException e) {
                            android.service.trust.TrustAgentService.this.onError("calling onSetTrustAgentFeaturesEnabledCompleted()");
                            return;
                        }
                    }
                    break;
                case 3:
                    android.service.trust.TrustAgentService.this.onTrustTimeout();
                    break;
                case 4:
                    android.service.trust.TrustAgentService.this.onDeviceLocked();
                    break;
                case 5:
                    android.service.trust.TrustAgentService.this.onDeviceUnlocked();
                    break;
                case 6:
                    android.service.trust.TrustAgentService.this.onDeviceUnlockLockout(message.arg1);
                    break;
                case 7:
                    android.os.Bundle data = message.getData();
                    android.service.trust.TrustAgentService.this.onEscrowTokenAdded(data.getByteArray("token"), data.getLong(android.service.trust.TrustAgentService.EXTRA_TOKEN_HANDLE), (android.os.UserHandle) data.getParcelable("user_handle", android.os.UserHandle.class));
                    break;
                case 8:
                    android.os.Bundle data2 = message.getData();
                    android.service.trust.TrustAgentService.this.onEscrowTokenStateReceived(data2.getLong(android.service.trust.TrustAgentService.EXTRA_TOKEN_HANDLE), data2.getInt(android.service.trust.TrustAgentService.EXTRA_TOKEN_STATE, 0));
                    break;
                case 9:
                    android.os.Bundle data3 = message.getData();
                    android.service.trust.TrustAgentService.this.onEscrowTokenRemoved(data3.getLong(android.service.trust.TrustAgentService.EXTRA_TOKEN_HANDLE), data3.getBoolean(android.service.trust.TrustAgentService.EXTRA_TOKEN_REMOVED_RESULT));
                    break;
                case 10:
                    android.service.trust.TrustAgentService.this.onUserRequestedUnlock(message.arg1 != 0);
                    break;
                case 11:
                    android.service.trust.TrustAgentService.this.onUserMayRequestUnlock();
                    break;
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GrantTrustFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TokenState {
    }

    private static final class ConfigurationData {
        final java.util.List<android.os.PersistableBundle> options;
        final android.os.IBinder token;

        ConfigurationData(java.util.List<android.os.PersistableBundle> list, android.os.IBinder iBinder) {
            this.options = list;
            this.token = iBinder;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        android.content.ComponentName componentName = new android.content.ComponentName(this, getClass());
        try {
            if (!android.Manifest.permission.BIND_TRUST_AGENT.equals(getPackageManager().getServiceInfo(componentName, 0).permission)) {
                throw new java.lang.IllegalStateException(componentName.flattenToShortString() + " is not declared with the permission \"" + android.Manifest.permission.BIND_TRUST_AGENT + "\"");
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(this.TAG, "Can't get ServiceInfo for " + componentName.toShortString());
        }
    }

    public void onUnlockAttempt(boolean z) {
    }

    public void onUserMayRequestUnlock() {
    }

    public void onUserRequestedUnlock(boolean z) {
    }

    public void onTrustTimeout() {
    }

    public void onDeviceLocked() {
    }

    public void onDeviceUnlocked() {
    }

    public void onDeviceUnlockLockout(long j) {
    }

    public void onEscrowTokenAdded(byte[] bArr, long j, android.os.UserHandle userHandle) {
    }

    public void onEscrowTokenStateReceived(long j, int i) {
    }

    public void onEscrowTokenRemoved(long j, boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(java.lang.String str) {
        android.util.Slog.v(this.TAG, "Remote exception while " + str);
    }

    public boolean onConfigure(java.util.List<android.os.PersistableBundle> list) {
        return false;
    }

    @java.lang.Deprecated
    public final void grantTrust(java.lang.CharSequence charSequence, long j, boolean z) {
        grantTrust(charSequence, j, z ? 1 : 0);
    }

    @java.lang.Deprecated
    public final void grantTrust(java.lang.CharSequence charSequence, long j, int i) {
        grantTrust(charSequence, j, i, null);
    }

    public final void grantTrust(final java.lang.CharSequence charSequence, final long j, final int i, final java.util.function.Consumer<android.service.trust.GrantTrustResult> consumer) {
        synchronized (this.mLock) {
            if (!this.mManagingTrust) {
                throw new java.lang.IllegalStateException("Cannot grant trust if agent is not managing trust. Call setManagingTrust(true) first.");
            }
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            androidFuture.thenAccept(new java.util.function.Consumer() { // from class: android.service.trust.TrustAgentService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.trust.TrustAgentService.this.lambda$grantTrust$1(consumer, (android.service.trust.GrantTrustResult) obj);
                }
            });
            if (this.mCallback != null) {
                try {
                    this.mCallback.grantTrust(charSequence.toString(), j, i, androidFuture);
                } catch (android.os.RemoteException e) {
                    onError("calling enableTrust()");
                }
            } else {
                this.mPendingGrantTrustTask = new java.lang.Runnable() { // from class: android.service.trust.TrustAgentService.2
                    @Override // java.lang.Runnable
                    public void run() {
                        android.service.trust.TrustAgentService.this.grantTrust(charSequence, j, i, consumer);
                    }
                };
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantTrust$1(final java.util.function.Consumer consumer, final android.service.trust.GrantTrustResult grantTrustResult) {
        if (consumer != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.service.trust.TrustAgentService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(grantTrustResult);
                }
            });
        }
    }

    public final void revokeTrust() {
        synchronized (this.mLock) {
            if (this.mPendingGrantTrustTask != null) {
                this.mPendingGrantTrustTask = null;
            }
            if (this.mCallback != null) {
                try {
                    this.mCallback.revokeTrust();
                } catch (android.os.RemoteException e) {
                    onError("calling revokeTrust()");
                }
            }
        }
    }

    public final void setManagingTrust(boolean z) {
        synchronized (this.mLock) {
            if (this.mManagingTrust != z) {
                this.mManagingTrust = z;
                if (this.mCallback != null) {
                    try {
                        this.mCallback.setManagingTrust(z);
                    } catch (android.os.RemoteException e) {
                        onError("calling setManagingTrust()");
                    }
                }
            }
        }
    }

    public final void addEscrowToken(byte[] bArr, android.os.UserHandle userHandle) {
        synchronized (this.mLock) {
            if (this.mCallback == null) {
                android.util.Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                throw new java.lang.IllegalStateException("Trust agent is not connected");
            }
            try {
                this.mCallback.addEscrowToken(bArr, userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                onError("calling addEscrowToken");
            }
        }
    }

    public final void isEscrowTokenActive(long j, android.os.UserHandle userHandle) {
        synchronized (this.mLock) {
            if (this.mCallback == null) {
                android.util.Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                throw new java.lang.IllegalStateException("Trust agent is not connected");
            }
            try {
                this.mCallback.isEscrowTokenActive(j, userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                onError("calling isEscrowTokenActive");
            }
        }
    }

    public final void removeEscrowToken(long j, android.os.UserHandle userHandle) {
        synchronized (this.mLock) {
            if (this.mCallback == null) {
                android.util.Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                throw new java.lang.IllegalStateException("Trust agent is not connected");
            }
            try {
                this.mCallback.removeEscrowToken(j, userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                onError("callling removeEscrowToken");
            }
        }
    }

    public final void unlockUserWithToken(long j, byte[] bArr, android.os.UserHandle userHandle) {
        if (((android.os.UserManager) getSystemService("user")).isUserUnlocked(userHandle)) {
            android.util.Slog.i(this.TAG, "User already unlocked");
            return;
        }
        synchronized (this.mLock) {
            if (this.mCallback == null) {
                android.util.Slog.w(this.TAG, "Cannot add escrow token if the agent is not connecting to framework");
                throw new java.lang.IllegalStateException("Trust agent is not connected");
            }
            try {
                this.mCallback.unlockUserWithToken(j, bArr, userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                onError("calling unlockUserWithToken");
            }
        }
    }

    public final void lockUser() {
        if (this.mCallback != null) {
            try {
                this.mCallback.lockUser();
            } catch (android.os.RemoteException e) {
                onError("calling lockUser");
            }
        }
    }

    public final void showKeyguardErrorMessage(java.lang.CharSequence charSequence) {
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("message cannot be null");
        }
        synchronized (this.mLock) {
            if (this.mCallback == null) {
                android.util.Slog.w(this.TAG, "Cannot show message because service is not connected to framework.");
                throw new java.lang.IllegalStateException("Trust agent is not connected");
            }
            try {
                this.mCallback.showKeyguardErrorMessage(charSequence);
            } catch (android.os.RemoteException e) {
                onError("calling showKeyguardErrorMessage");
            }
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.service.trust.TrustAgentService.TrustAgentServiceWrapper();
    }

    private final class TrustAgentServiceWrapper extends android.service.trust.ITrustAgentService.Stub {
        private TrustAgentServiceWrapper() {
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUnlockAttempt(boolean z) {
            android.service.trust.TrustAgentService.this.mHandler.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUserRequestedUnlock(boolean z) {
            android.service.trust.TrustAgentService.this.mHandler.obtainMessage(10, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUserMayRequestUnlock() {
            android.service.trust.TrustAgentService.this.mHandler.obtainMessage(11).sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onUnlockLockout(int i) {
            android.service.trust.TrustAgentService.this.mHandler.obtainMessage(6, i, 0).sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onTrustTimeout() {
            android.service.trust.TrustAgentService.this.mHandler.sendEmptyMessage(3);
        }

        @Override // android.service.trust.ITrustAgentService
        public void onConfigure(java.util.List<android.os.PersistableBundle> list, android.os.IBinder iBinder) {
            android.service.trust.TrustAgentService.this.mHandler.obtainMessage(2, new android.service.trust.TrustAgentService.ConfigurationData(list, iBinder)).sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onDeviceLocked() throws android.os.RemoteException {
            android.service.trust.TrustAgentService.this.mHandler.obtainMessage(4).sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onDeviceUnlocked() throws android.os.RemoteException {
            android.service.trust.TrustAgentService.this.mHandler.obtainMessage(5).sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void setCallback(android.service.trust.ITrustAgentServiceCallback iTrustAgentServiceCallback) {
            synchronized (android.service.trust.TrustAgentService.this.mLock) {
                android.service.trust.TrustAgentService.this.mCallback = iTrustAgentServiceCallback;
                if (android.service.trust.TrustAgentService.this.mManagingTrust) {
                    try {
                        android.service.trust.TrustAgentService.this.mCallback.setManagingTrust(android.service.trust.TrustAgentService.this.mManagingTrust);
                    } catch (android.os.RemoteException e) {
                        android.service.trust.TrustAgentService.this.onError("calling setManagingTrust()");
                    }
                }
                if (android.service.trust.TrustAgentService.this.mPendingGrantTrustTask != null) {
                    android.service.trust.TrustAgentService.this.mPendingGrantTrustTask.run();
                    android.service.trust.TrustAgentService.this.mPendingGrantTrustTask = null;
                }
            }
        }

        @Override // android.service.trust.ITrustAgentService
        public void onEscrowTokenAdded(byte[] bArr, long j, android.os.UserHandle userHandle) {
            android.os.Message obtainMessage = android.service.trust.TrustAgentService.this.mHandler.obtainMessage(7);
            obtainMessage.getData().putByteArray("token", bArr);
            obtainMessage.getData().putLong(android.service.trust.TrustAgentService.EXTRA_TOKEN_HANDLE, j);
            obtainMessage.getData().putParcelable("user_handle", userHandle);
            obtainMessage.sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onTokenStateReceived(long j, int i) {
            android.os.Message obtainMessage = android.service.trust.TrustAgentService.this.mHandler.obtainMessage(8);
            obtainMessage.getData().putLong(android.service.trust.TrustAgentService.EXTRA_TOKEN_HANDLE, j);
            obtainMessage.getData().putInt(android.service.trust.TrustAgentService.EXTRA_TOKEN_STATE, i);
            obtainMessage.sendToTarget();
        }

        @Override // android.service.trust.ITrustAgentService
        public void onEscrowTokenRemoved(long j, boolean z) {
            android.os.Message obtainMessage = android.service.trust.TrustAgentService.this.mHandler.obtainMessage(9);
            obtainMessage.getData().putLong(android.service.trust.TrustAgentService.EXTRA_TOKEN_HANDLE, j);
            obtainMessage.getData().putBoolean(android.service.trust.TrustAgentService.EXTRA_TOKEN_REMOVED_RESULT, z);
            obtainMessage.sendToTarget();
        }
    }
}
