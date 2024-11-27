package android.app;

/* loaded from: classes.dex */
public class KeyguardManager {
    public static final java.lang.String ACTION_CONFIRM_DEVICE_CREDENTIAL = "android.app.action.CONFIRM_DEVICE_CREDENTIAL";
    public static final java.lang.String ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER = "android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER";
    public static final java.lang.String ACTION_CONFIRM_FRP_CREDENTIAL = "android.app.action.CONFIRM_FRP_CREDENTIAL";
    public static final java.lang.String ACTION_CONFIRM_REMOTE_DEVICE_CREDENTIAL = "android.app.action.CONFIRM_REMOTE_DEVICE_CREDENTIAL";
    public static final java.lang.String ACTION_CONFIRM_REPAIR_MODE_DEVICE_CREDENTIAL = "android.app.action.CONFIRM_REPAIR_MODE_DEVICE_CREDENTIAL";
    public static final java.lang.String ACTION_PREPARE_REPAIR_MODE_DEVICE_CREDENTIAL = "android.app.action.PREPARE_REPAIR_MODE_DEVICE_CREDENTIAL";
    public static final java.lang.String EXTRA_ALTERNATE_BUTTON_LABEL = "android.app.extra.ALTERNATE_BUTTON_LABEL";
    public static final java.lang.String EXTRA_CHECKBOX_LABEL = "android.app.extra.CHECKBOX_LABEL";
    public static final java.lang.String EXTRA_DESCRIPTION = "android.app.extra.DESCRIPTION";
    public static final java.lang.String EXTRA_DISALLOW_BIOMETRICS_IF_POLICY_EXISTS = "check_dpm";
    public static final java.lang.String EXTRA_FORCE_TASK_OVERLAY = "android.app.KeyguardManager.FORCE_TASK_OVERLAY";
    public static final java.lang.String EXTRA_REMOTE_LOCKSCREEN_VALIDATION_SESSION = "android.app.extra.REMOTE_LOCKSCREEN_VALIDATION_SESSION";
    public static final java.lang.String EXTRA_TITLE = "android.app.extra.TITLE";

    @android.annotation.SystemApi
    public static final int PASSWORD = 0;

    @android.annotation.SystemApi
    public static final int PATTERN = 2;

    @android.annotation.SystemApi
    public static final int PIN = 1;
    public static final int RESULT_ALTERNATE = 1;
    private static final java.lang.String TAG = "KeyguardManager";
    private final android.content.Context mContext;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    private final android.util.ArrayMap<android.app.KeyguardManager.WeakEscrowTokenRemovedListener, com.android.internal.widget.IWeakEscrowTokenRemovedListener> mListeners = new android.util.ArrayMap<>();
    private final com.android.internal.policy.IKeyguardLockedStateListener mIKeyguardLockedStateListener = new android.app.KeyguardManager.AnonymousClass1();
    private final android.util.ArrayMap<android.app.KeyguardManager.KeyguardLockedStateListener, java.util.concurrent.Executor> mKeyguardLockedStateListeners = new android.util.ArrayMap<>();
    private final android.view.IWindowManager mWM = android.view.WindowManagerGlobal.getWindowManagerService();
    private final android.app.IActivityManager mAm = android.app.ActivityManager.getService();
    private final android.app.trust.ITrustManager mTrustManager = android.app.trust.ITrustManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TRUST_SERVICE));
    private final android.app.INotificationManager mNotificationManager = android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("notification"));

    @java.lang.FunctionalInterface
    public interface KeyguardLockedStateListener {
        void onKeyguardLockedStateChanged(boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface LockTypes {
    }

    @java.lang.Deprecated
    public interface OnKeyguardExitResult {
        void onKeyguardExitResult(boolean z);
    }

    @android.annotation.SystemApi
    public interface WeakEscrowTokenActivatedListener {
        void onWeakEscrowTokenActivated(long j, android.os.UserHandle userHandle);
    }

    @android.annotation.SystemApi
    public interface WeakEscrowTokenRemovedListener {
        void onWeakEscrowTokenRemoved(long j, android.os.UserHandle userHandle);
    }

    /* renamed from: android.app.KeyguardManager$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.policy.IKeyguardLockedStateListener.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.internal.policy.IKeyguardLockedStateListener
        public void onKeyguardLockedStateChanged(final boolean z) {
            android.app.KeyguardManager.this.mKeyguardLockedStateListeners.forEach(new java.util.function.BiConsumer() { // from class: android.app.KeyguardManager$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    java.util.concurrent.Executor executor = (java.util.concurrent.Executor) obj2;
                    executor.execute(new java.lang.Runnable() { // from class: android.app.KeyguardManager$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.KeyguardManager.KeyguardLockedStateListener.this.onKeyguardLockedStateChanged(r2);
                        }
                    });
                }
            });
        }
    }

    @java.lang.Deprecated
    public android.content.Intent createConfirmDeviceCredentialIntent(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (!isDeviceSecure()) {
            return null;
        }
        android.content.Intent intent = new android.content.Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL);
        intent.putExtra(EXTRA_TITLE, charSequence);
        intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
        intent.setPackage(getSettingsPackageForIntent(intent));
        return intent;
    }

    public android.content.Intent createConfirmDeviceCredentialIntent(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i) {
        if (!isDeviceSecure(i)) {
            return null;
        }
        android.content.Intent intent = new android.content.Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER);
        intent.putExtra(EXTRA_TITLE, charSequence);
        intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
        intent.putExtra(android.content.Intent.EXTRA_USER_ID, i);
        intent.setPackage(getSettingsPackageForIntent(intent));
        return intent;
    }

    public android.content.Intent createConfirmDeviceCredentialIntent(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i, boolean z) {
        android.content.Intent createConfirmDeviceCredentialIntent = createConfirmDeviceCredentialIntent(charSequence, charSequence2, i);
        if (createConfirmDeviceCredentialIntent != null) {
            createConfirmDeviceCredentialIntent.putExtra(EXTRA_DISALLOW_BIOMETRICS_IF_POLICY_EXISTS, z);
        }
        return createConfirmDeviceCredentialIntent;
    }

    @android.annotation.SystemApi
    public android.content.Intent createConfirmFactoryResetCredentialIntent(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3) {
        if (!com.android.internal.widget.LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            android.util.Log.w(TAG, "Factory reset credentials not supported.");
            throw new java.lang.UnsupportedOperationException("not supported on this device");
        }
        if (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
            android.util.Log.e(TAG, "Factory reset credential cannot be verified after provisioning.");
            throw new java.lang.IllegalStateException("must not be provisioned yet");
        }
        try {
            android.service.persistentdata.IPersistentDataBlockService asInterface = android.service.persistentdata.IPersistentDataBlockService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.PERSISTENT_DATA_BLOCK_SERVICE));
            if (asInterface == null) {
                android.util.Log.e(TAG, "No persistent data block service");
                throw new java.lang.UnsupportedOperationException("not supported on this device");
            }
            if (!asInterface.hasFrpCredentialHandle()) {
                android.util.Log.i(TAG, "The persistent data block does not have a factory reset credential.");
                return null;
            }
            android.content.Intent intent = new android.content.Intent(ACTION_CONFIRM_FRP_CREDENTIAL);
            intent.putExtra(EXTRA_TITLE, charSequence);
            intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
            intent.putExtra(EXTRA_ALTERNATE_BUTTON_LABEL, charSequence3);
            intent.setPackage(getSettingsPackageForIntent(intent));
            return intent;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.content.Intent createConfirmDeviceCredentialForRemoteValidationIntent(android.app.RemoteLockscreenValidationSession remoteLockscreenValidationSession, android.content.ComponentName componentName, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, java.lang.CharSequence charSequence4) {
        android.content.Intent putExtra = new android.content.Intent(ACTION_CONFIRM_REMOTE_DEVICE_CREDENTIAL).putExtra(EXTRA_REMOTE_LOCKSCREEN_VALIDATION_SESSION, remoteLockscreenValidationSession).putExtra(android.content.Intent.EXTRA_COMPONENT_NAME, componentName).putExtra(EXTRA_TITLE, charSequence).putExtra(EXTRA_DESCRIPTION, charSequence2).putExtra(EXTRA_CHECKBOX_LABEL, charSequence3).putExtra(EXTRA_ALTERNATE_BUTTON_LABEL, charSequence4);
        putExtra.setPackage(getSettingsPackageForIntent(putExtra));
        return putExtra;
    }

    @android.annotation.SystemApi
    public void setPrivateNotificationsAllowed(boolean z) {
        try {
            this.mNotificationManager.setPrivateNotificationsAllowed(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean getPrivateNotificationsAllowed() {
        try {
            return this.mNotificationManager.getPrivateNotificationsAllowed();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private java.lang.String getSettingsPackageForIntent(android.content.Intent intent) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(intent, 1048576);
        if (queryIntentActivities.size() > 0) {
            return queryIntentActivities.get(0).activityInfo.packageName;
        }
        return "com.android.settings";
    }

    @java.lang.Deprecated
    public class KeyguardLock {
        private final java.lang.String mTag;
        private final android.os.IBinder mToken = new android.os.Binder();

        KeyguardLock(java.lang.String str) {
            this.mTag = str;
        }

        public void disableKeyguard() {
            try {
                android.app.KeyguardManager.this.mWM.disableKeyguard(this.mToken, this.mTag, android.app.KeyguardManager.this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
            }
        }

        public void reenableKeyguard() {
            try {
                android.app.KeyguardManager.this.mWM.reenableKeyguard(this.mToken, android.app.KeyguardManager.this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public static abstract class KeyguardDismissCallback {
        public void onDismissError() {
        }

        public void onDismissSucceeded() {
        }

        public void onDismissCancelled() {
        }
    }

    KeyguardManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mLockPatternUtils = new com.android.internal.widget.LockPatternUtils(context);
    }

    @java.lang.Deprecated
    public android.app.KeyguardManager.KeyguardLock newKeyguardLock(java.lang.String str) {
        return new android.app.KeyguardManager.KeyguardLock(str);
    }

    public boolean isKeyguardLocked() {
        try {
            return this.mWM.isKeyguardLocked();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean isKeyguardSecure() {
        try {
            return this.mWM.isKeyguardSecure(this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean inKeyguardRestrictedInputMode() {
        return isKeyguardLocked();
    }

    public boolean isDeviceLocked() {
        return isDeviceLocked(this.mContext.getUserId(), this.mContext.getDeviceId());
    }

    public boolean isDeviceLocked(int i) {
        return isDeviceLocked(i, this.mContext.getDeviceId());
    }

    public boolean isDeviceLocked(int i, int i2) {
        try {
            return this.mTrustManager.isDeviceLocked(i, i2);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean isDeviceSecure() {
        return isDeviceSecure(this.mContext.getUserId(), this.mContext.getDeviceId());
    }

    public boolean isDeviceSecure(int i) {
        return isDeviceSecure(i, this.mContext.getDeviceId());
    }

    public boolean isDeviceSecure(int i, int i2) {
        try {
            return this.mTrustManager.isDeviceSecure(i, i2);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void requestDismissKeyguard(android.app.Activity activity, android.app.KeyguardManager.KeyguardDismissCallback keyguardDismissCallback) {
        requestDismissKeyguard(activity, null, keyguardDismissCallback);
    }

    @android.annotation.SystemApi
    public void requestDismissKeyguard(final android.app.Activity activity, java.lang.CharSequence charSequence, final android.app.KeyguardManager.KeyguardDismissCallback keyguardDismissCallback) {
        android.app.ActivityClient.getInstance().dismissKeyguard(activity.getActivityToken(), new com.android.internal.policy.IKeyguardDismissCallback.Stub() { // from class: android.app.KeyguardManager.2
            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissError() throws android.os.RemoteException {
                if (keyguardDismissCallback != null && !activity.isDestroyed()) {
                    android.os.Handler handler = activity.mHandler;
                    final android.app.KeyguardManager.KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                    java.util.Objects.requireNonNull(keyguardDismissCallback2);
                    handler.post(new java.lang.Runnable() { // from class: android.app.KeyguardManager$2$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.KeyguardManager.KeyguardDismissCallback.this.onDismissError();
                        }
                    });
                }
            }

            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissSucceeded() throws android.os.RemoteException {
                if (keyguardDismissCallback != null && !activity.isDestroyed()) {
                    android.os.Handler handler = activity.mHandler;
                    final android.app.KeyguardManager.KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                    java.util.Objects.requireNonNull(keyguardDismissCallback2);
                    handler.post(new java.lang.Runnable() { // from class: android.app.KeyguardManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.KeyguardManager.KeyguardDismissCallback.this.onDismissSucceeded();
                        }
                    });
                }
            }

            @Override // com.android.internal.policy.IKeyguardDismissCallback
            public void onDismissCancelled() throws android.os.RemoteException {
                if (keyguardDismissCallback != null && !activity.isDestroyed()) {
                    android.os.Handler handler = activity.mHandler;
                    final android.app.KeyguardManager.KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                    java.util.Objects.requireNonNull(keyguardDismissCallback2);
                    handler.post(new java.lang.Runnable() { // from class: android.app.KeyguardManager$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.KeyguardManager.KeyguardDismissCallback.this.onDismissCancelled();
                        }
                    });
                }
            }
        }, charSequence);
    }

    @java.lang.Deprecated
    public void exitKeyguardSecurely(final android.app.KeyguardManager.OnKeyguardExitResult onKeyguardExitResult) {
        try {
            this.mWM.exitKeyguardSecurely(new android.view.IOnKeyguardExitResult.Stub() { // from class: android.app.KeyguardManager.3
                @Override // android.view.IOnKeyguardExitResult
                public void onKeyguardExitResult(boolean z) throws android.os.RemoteException {
                    if (onKeyguardExitResult != null) {
                        onKeyguardExitResult.onKeyguardExitResult(z);
                    }
                }
            });
        } catch (android.os.RemoteException e) {
        }
    }

    public boolean checkInitialLockMethodUsage() {
        if (!hasPermission(android.Manifest.permission.SET_INITIAL_LOCK)) {
            throw new java.lang.SecurityException("Requires SET_INITIAL_LOCK permission.");
        }
        return true;
    }

    private boolean hasPermission(java.lang.String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    @android.annotation.SystemApi
    public boolean isValidLockPasswordComplexity(int i, byte[] bArr, int i2) {
        if (!checkInitialLockMethodUsage()) {
            return false;
        }
        java.util.Objects.requireNonNull(bArr, "Password cannot be null.");
        int sanitizeComplexityLevel = android.app.admin.PasswordMetrics.sanitizeComplexityLevel(i2);
        android.app.admin.PasswordMetrics requestedPasswordMetrics = this.mLockPatternUtils.getRequestedPasswordMetrics(this.mContext.getUserId());
        com.android.internal.widget.LockscreenCredential createLockscreenCredential = createLockscreenCredential(i, bArr);
        try {
            boolean z = android.app.admin.PasswordMetrics.validateCredential(requestedPasswordMetrics, sanitizeComplexityLevel, createLockscreenCredential).size() == 0;
            if (createLockscreenCredential != null) {
                createLockscreenCredential.close();
            }
            return z;
        } catch (java.lang.Throwable th) {
            if (createLockscreenCredential != null) {
                try {
                    createLockscreenCredential.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.annotation.SystemApi
    public int getMinLockLength(boolean z, int i) {
        if (!checkInitialLockMethodUsage()) {
            return -1;
        }
        return android.app.admin.PasswordMetrics.applyComplexity(this.mLockPatternUtils.getRequestedPasswordMetrics(this.mContext.getUserId()), z, android.app.admin.PasswordMetrics.sanitizeComplexityLevel(i)).length;
    }

    @android.annotation.SystemApi
    public boolean setLock(int i, byte[] bArr, int i2) {
        if (!checkInitialLockMethodUsage()) {
            return false;
        }
        int userId = this.mContext.getUserId();
        if (isDeviceSecure(userId)) {
            android.util.Log.e(TAG, "Password already set, rejecting call to setLock");
            return false;
        }
        if (!isValidLockPasswordComplexity(i, bArr, i2)) {
            android.util.Log.e(TAG, "Password is not valid, rejecting call to setLock");
            return false;
        }
        try {
            return this.mLockPatternUtils.setLockCredential(createLockscreenCredential(i, bArr), com.android.internal.widget.LockscreenCredential.createNone(), userId);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Save lock exception", e);
            return false;
        } finally {
            java.util.Arrays.fill(bArr, (byte) 0);
        }
    }

    @android.annotation.SystemApi
    public long addWeakEscrowToken(byte[] bArr, android.os.UserHandle userHandle, java.util.concurrent.Executor executor, android.app.KeyguardManager.WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener) {
        java.util.Objects.requireNonNull(bArr, "Token cannot be null.");
        java.util.Objects.requireNonNull(userHandle, "User cannot be null.");
        java.util.Objects.requireNonNull(executor, "Executor cannot be null.");
        java.util.Objects.requireNonNull(weakEscrowTokenActivatedListener, "Listener cannot be null.");
        return this.mLockPatternUtils.addWeakEscrowToken(bArr, userHandle.getIdentifier(), new android.app.KeyguardManager.AnonymousClass4(executor, weakEscrowTokenActivatedListener));
    }

    /* renamed from: android.app.KeyguardManager$4, reason: invalid class name */
    class AnonymousClass4 extends com.android.internal.widget.IWeakEscrowTokenActivatedListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.app.KeyguardManager.WeakEscrowTokenActivatedListener val$listener;

        AnonymousClass4(java.util.concurrent.Executor executor, android.app.KeyguardManager.WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener) {
            this.val$executor = executor;
            this.val$listener = weakEscrowTokenActivatedListener;
        }

        @Override // com.android.internal.widget.IWeakEscrowTokenActivatedListener
        public void onWeakEscrowTokenActivated(final long j, int i) {
            final android.os.UserHandle of = android.os.UserHandle.of(i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.app.KeyguardManager.WeakEscrowTokenActivatedListener weakEscrowTokenActivatedListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.app.KeyguardManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.KeyguardManager.WeakEscrowTokenActivatedListener.this.onWeakEscrowTokenActivated(j, of);
                    }
                });
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.util.Log.i(android.app.KeyguardManager.TAG, "Weak escrow token activated.");
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    @android.annotation.SystemApi
    public boolean removeWeakEscrowToken(long j, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(userHandle, "User cannot be null.");
        return this.mLockPatternUtils.removeWeakEscrowToken(j, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public boolean isWeakEscrowTokenActive(long j, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(userHandle, "User cannot be null.");
        return this.mLockPatternUtils.isWeakEscrowTokenActive(j, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public boolean isWeakEscrowTokenValid(long j, byte[] bArr, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(bArr, "Token cannot be null.");
        java.util.Objects.requireNonNull(userHandle, "User cannot be null.");
        return this.mLockPatternUtils.isWeakEscrowTokenValid(j, bArr, userHandle.getIdentifier());
    }

    @android.annotation.SystemApi
    public boolean registerWeakEscrowTokenRemovedListener(java.util.concurrent.Executor executor, android.app.KeyguardManager.WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener) {
        java.util.Objects.requireNonNull(weakEscrowTokenRemovedListener, "Listener cannot be null.");
        java.util.Objects.requireNonNull(executor, "Executor cannot be null.");
        com.android.internal.util.Preconditions.checkArgument(!this.mListeners.containsKey(weakEscrowTokenRemovedListener), "Listener already registered: %s", weakEscrowTokenRemovedListener);
        android.app.KeyguardManager.AnonymousClass5 anonymousClass5 = new android.app.KeyguardManager.AnonymousClass5(executor, weakEscrowTokenRemovedListener);
        if (this.mLockPatternUtils.registerWeakEscrowTokenRemovedListener(anonymousClass5)) {
            this.mListeners.put(weakEscrowTokenRemovedListener, anonymousClass5);
            return true;
        }
        android.util.Log.e(TAG, "Listener failed to register");
        return false;
    }

    /* renamed from: android.app.KeyguardManager$5, reason: invalid class name */
    class AnonymousClass5 extends com.android.internal.widget.IWeakEscrowTokenRemovedListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.app.KeyguardManager.WeakEscrowTokenRemovedListener val$listener;

        AnonymousClass5(java.util.concurrent.Executor executor, android.app.KeyguardManager.WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener) {
            this.val$executor = executor;
            this.val$listener = weakEscrowTokenRemovedListener;
        }

        @Override // com.android.internal.widget.IWeakEscrowTokenRemovedListener
        public void onWeakEscrowTokenRemoved(final long j, int i) {
            final android.os.UserHandle of = android.os.UserHandle.of(i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.app.KeyguardManager.WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.app.KeyguardManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.KeyguardManager.WeakEscrowTokenRemovedListener.this.onWeakEscrowTokenRemoved(j, of);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.SystemApi
    public boolean unregisterWeakEscrowTokenRemovedListener(android.app.KeyguardManager.WeakEscrowTokenRemovedListener weakEscrowTokenRemovedListener) {
        java.util.Objects.requireNonNull(weakEscrowTokenRemovedListener, "Listener cannot be null.");
        com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener = this.mListeners.get(weakEscrowTokenRemovedListener);
        com.android.internal.util.Preconditions.checkArgument(iWeakEscrowTokenRemovedListener != null, "Listener was not registered");
        if (this.mLockPatternUtils.unregisterWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener)) {
            this.mListeners.remove(weakEscrowTokenRemovedListener);
            return true;
        }
        android.util.Log.e(TAG, "Listener failed to unregister.");
        return false;
    }

    public boolean setLock(int i, byte[] bArr, int i2, byte[] bArr2) {
        int userId = this.mContext.getUserId();
        com.android.internal.widget.LockscreenCredential createLockscreenCredential = createLockscreenCredential(i2, bArr2);
        com.android.internal.widget.LockscreenCredential createLockscreenCredential2 = createLockscreenCredential(i, bArr);
        java.util.List<com.android.internal.widget.PasswordValidationError> validateCredential = android.app.admin.PasswordMetrics.validateCredential(this.mLockPatternUtils.getRequestedPasswordMetrics(this.mContext.getUserId()), 0, createLockscreenCredential2);
        if (!validateCredential.isEmpty()) {
            android.util.Log.e(TAG, "New credential is not valid: " + validateCredential.get(0));
            return false;
        }
        return this.mLockPatternUtils.setLockCredential(createLockscreenCredential2, createLockscreenCredential, userId);
    }

    public boolean checkLock(int i, byte[] bArr) {
        com.android.internal.widget.VerifyCredentialResponse verifyCredential = this.mLockPatternUtils.verifyCredential(createLockscreenCredential(i, bArr), this.mContext.getUserId(), 0);
        return verifyCredential != null && verifyCredential.getResponseCode() == 0;
    }

    @android.annotation.SystemApi
    public android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        return this.mLockPatternUtils.startRemoteLockscreenValidation();
    }

    @android.annotation.SystemApi
    public android.app.RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) {
        return this.mLockPatternUtils.validateRemoteLockscreen(bArr);
    }

    private com.android.internal.widget.LockscreenCredential createLockscreenCredential(int i, byte[] bArr) {
        if (bArr == null) {
            return com.android.internal.widget.LockscreenCredential.createNone();
        }
        switch (i) {
            case 0:
                return com.android.internal.widget.LockscreenCredential.createPassword(new java.lang.String(bArr, java.nio.charset.Charset.forName(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8)));
            case 1:
                return com.android.internal.widget.LockscreenCredential.createPin(new java.lang.String(bArr));
            case 2:
                byte lockPatternSize = new com.android.internal.widget.LockPatternUtils(this.mContext).getLockPatternSize(this.mContext.getUserId());
                return com.android.internal.widget.LockscreenCredential.createPattern(com.android.internal.widget.LockPatternUtils.byteArrayToPattern(bArr, lockPatternSize), lockPatternSize);
            default:
                throw new java.lang.IllegalArgumentException("Unknown lock type " + i);
        }
    }

    public void addKeyguardLockedStateListener(java.util.concurrent.Executor executor, android.app.KeyguardManager.KeyguardLockedStateListener keyguardLockedStateListener) {
        synchronized (this.mKeyguardLockedStateListeners) {
            this.mKeyguardLockedStateListeners.put(keyguardLockedStateListener, executor);
            if (this.mKeyguardLockedStateListeners.size() > 1) {
                return;
            }
            try {
                this.mWM.addKeyguardLockedStateListener(this.mIKeyguardLockedStateListener);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeKeyguardLockedStateListener(android.app.KeyguardManager.KeyguardLockedStateListener keyguardLockedStateListener) {
        synchronized (this.mKeyguardLockedStateListeners) {
            this.mKeyguardLockedStateListeners.remove(keyguardLockedStateListener);
            if (this.mKeyguardLockedStateListeners.isEmpty()) {
                try {
                    this.mWM.removeKeyguardLockedStateListener(this.mIKeyguardLockedStateListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }
}
