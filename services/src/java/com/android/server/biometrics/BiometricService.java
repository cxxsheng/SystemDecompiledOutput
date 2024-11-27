package com.android.server.biometrics;

/* loaded from: classes.dex */
public class BiometricService extends com.android.server.SystemService {
    static final java.lang.String TAG = "BiometricService";

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.biometrics.AuthSession mAuthSession;
    private final com.android.server.biometrics.BiometricCameraManager mBiometricCameraManager;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;
    private final com.android.server.biometrics.BiometricNotificationLogger mBiometricNotificationLogger;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.biometrics.BiometricStrengthController mBiometricStrengthController;
    private final android.app.admin.DevicePolicyManager mDevicePolicyManager;
    private final java.util.List<com.android.server.biometrics.BiometricService.EnabledOnKeyguardCallback> mEnabledOnKeyguardCallbacks;

    @com.android.internal.annotations.VisibleForTesting
    android.service.gatekeeper.IGateKeeperService mGateKeeper;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.biometrics.IBiometricService.Stub mImpl;
    private final com.android.server.biometrics.BiometricService.Injector mInjector;

    @com.android.internal.annotations.VisibleForTesting
    android.security.KeyStore mKeyStore;

    @com.android.internal.annotations.VisibleForTesting
    android.security.authorization.IKeystoreAuthorization mKeystoreAuthorization;
    private final java.util.Random mRandom;

    @android.annotation.NonNull
    private final java.util.function.Supplier<java.lang.Long> mRequestCounter;
    final java.util.ArrayList<com.android.server.biometrics.BiometricSensor> mSensors;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.biometrics.BiometricService.SettingObserver mSettingObserver;

    @com.android.internal.annotations.VisibleForTesting
    com.android.internal.statusbar.IStatusBarService mStatusBarService;

    @com.android.internal.annotations.VisibleForTesting
    android.app.trust.ITrustManager mTrustManager;
    private final android.os.UserManager mUserManager;

    @com.android.internal.annotations.VisibleForTesting
    static class InvalidationTracker {

        @android.annotation.NonNull
        private final android.hardware.biometrics.IInvalidationCallback mClientCallback;

        @android.annotation.NonNull
        private final java.util.Set<java.lang.Integer> mSensorsPendingInvalidation = new android.util.ArraySet();

        public static com.android.server.biometrics.BiometricService.InvalidationTracker start(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.ArrayList<com.android.server.biometrics.BiometricSensor> arrayList, int i, int i2, @android.annotation.NonNull android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
            return new com.android.server.biometrics.BiometricService.InvalidationTracker(context, arrayList, i, i2, iInvalidationCallback);
        }

        private InvalidationTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.ArrayList<com.android.server.biometrics.BiometricSensor> arrayList, int i, int i2, @android.annotation.NonNull android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
            this.mClientCallback = iInvalidationCallback;
            java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = arrayList.iterator();
            while (it.hasNext()) {
                final com.android.server.biometrics.BiometricSensor next = it.next();
                if (next.id != i2 && com.android.server.biometrics.Utils.isAtLeastStrength(next.oemStrength, 15)) {
                    try {
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote Exception", e);
                    }
                    if (next.impl.hasEnrolledTemplates(i, context.getOpPackageName())) {
                        android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "Requesting authenticatorId invalidation for sensor: " + next.id);
                        synchronized (this) {
                            this.mSensorsPendingInvalidation.add(java.lang.Integer.valueOf(next.id));
                        }
                        try {
                            next.impl.invalidateAuthenticatorId(i, new android.hardware.biometrics.IInvalidationCallback.Stub() { // from class: com.android.server.biometrics.BiometricService.InvalidationTracker.1
                                public void onCompleted() {
                                    com.android.server.biometrics.BiometricService.InvalidationTracker.this.onInvalidated(next.id);
                                }
                            });
                        } catch (android.os.RemoteException e2) {
                            android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "RemoteException", e2);
                        }
                    } else {
                        continue;
                    }
                }
            }
            synchronized (this) {
                if (this.mSensorsPendingInvalidation.isEmpty()) {
                    try {
                        android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "No sensors require invalidation");
                        this.mClientCallback.onCompleted();
                    } catch (android.os.RemoteException e3) {
                        android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote Exception", e3);
                    }
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        void onInvalidated(int i) {
            synchronized (this) {
                this.mSensorsPendingInvalidation.remove(java.lang.Integer.valueOf(i));
                android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "Sensor " + i + " invalidated, remaining size: " + this.mSensorsPendingInvalidation.size());
                if (this.mSensorsPendingInvalidation.isEmpty()) {
                    try {
                        this.mClientCallback.onCompleted();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote Exception", e);
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class SettingObserver extends android.database.ContentObserver {
        private static final boolean DEFAULT_ALWAYS_REQUIRE_CONFIRMATION = false;
        private static final boolean DEFAULT_APP_ENABLED = true;
        private static final boolean DEFAULT_KEYGUARD_ENABLED = true;
        private final android.net.Uri BIOMETRIC_APP_ENABLED;
        private final android.net.Uri BIOMETRIC_KEYGUARD_ENABLED;
        private final android.net.Uri FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION;
        private final android.net.Uri FACE_UNLOCK_APP_ENABLED;
        private final android.net.Uri FACE_UNLOCK_KEYGUARD_ENABLED;
        private final java.util.Map<java.lang.Integer, java.lang.Boolean> mBiometricEnabledForApps;
        private final java.util.Map<java.lang.Integer, java.lang.Boolean> mBiometricEnabledOnKeyguard;
        private final java.util.List<com.android.server.biometrics.BiometricService.EnabledOnKeyguardCallback> mCallbacks;
        private final android.content.ContentResolver mContentResolver;
        private final java.util.Map<java.lang.Integer, java.lang.Boolean> mFaceAlwaysRequireConfirmation;
        private final boolean mUseLegacyFaceOnlySettings;

        public SettingObserver(android.content.Context context, android.os.Handler handler, java.util.List<com.android.server.biometrics.BiometricService.EnabledOnKeyguardCallback> list) {
            super(handler);
            this.FACE_UNLOCK_KEYGUARD_ENABLED = android.provider.Settings.Secure.getUriFor("face_unlock_keyguard_enabled");
            this.FACE_UNLOCK_APP_ENABLED = android.provider.Settings.Secure.getUriFor("face_unlock_app_enabled");
            this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION = android.provider.Settings.Secure.getUriFor("face_unlock_always_require_confirmation");
            this.BIOMETRIC_KEYGUARD_ENABLED = android.provider.Settings.Secure.getUriFor("biometric_keyguard_enabled");
            this.BIOMETRIC_APP_ENABLED = android.provider.Settings.Secure.getUriFor("biometric_app_enabled");
            this.mBiometricEnabledOnKeyguard = new java.util.HashMap();
            this.mBiometricEnabledForApps = new java.util.HashMap();
            this.mFaceAlwaysRequireConfirmation = new java.util.HashMap();
            this.mContentResolver = context.getContentResolver();
            this.mCallbacks = list;
            this.mUseLegacyFaceOnlySettings = android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT <= 29 && context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face") && !context.getPackageManager().hasSystemFeature("android.hardware.fingerprint");
            updateContentObserver();
        }

        public void updateContentObserver() {
            this.mContentResolver.unregisterContentObserver(this);
            if (this.mUseLegacyFaceOnlySettings) {
                this.mContentResolver.registerContentObserver(this.FACE_UNLOCK_KEYGUARD_ENABLED, false, this, -1);
                this.mContentResolver.registerContentObserver(this.FACE_UNLOCK_APP_ENABLED, false, this, -1);
            } else {
                this.mContentResolver.registerContentObserver(this.BIOMETRIC_KEYGUARD_ENABLED, false, this, -1);
                this.mContentResolver.registerContentObserver(this.BIOMETRIC_APP_ENABLED, false, this, -1);
            }
            this.mContentResolver.registerContentObserver(this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (this.FACE_UNLOCK_KEYGUARD_ENABLED.equals(uri)) {
                this.mBiometricEnabledOnKeyguard.put(java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(android.provider.Settings.Secure.getIntForUser(this.mContentResolver, "face_unlock_keyguard_enabled", 1, i) != 0));
                if (i == android.app.ActivityManager.getCurrentUser() && !z) {
                    notifyEnabledOnKeyguardCallbacks(i);
                    return;
                }
                return;
            }
            if (this.FACE_UNLOCK_APP_ENABLED.equals(uri)) {
                this.mBiometricEnabledForApps.put(java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(android.provider.Settings.Secure.getIntForUser(this.mContentResolver, "face_unlock_app_enabled", 1, i) != 0));
                return;
            }
            if (this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION.equals(uri)) {
                this.mFaceAlwaysRequireConfirmation.put(java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(android.provider.Settings.Secure.getIntForUser(this.mContentResolver, "face_unlock_always_require_confirmation", 0, i) != 0));
                return;
            }
            if (this.BIOMETRIC_KEYGUARD_ENABLED.equals(uri)) {
                this.mBiometricEnabledOnKeyguard.put(java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(android.provider.Settings.Secure.getIntForUser(this.mContentResolver, "biometric_keyguard_enabled", 1, i) != 0));
                if (i == android.app.ActivityManager.getCurrentUser() && !z) {
                    notifyEnabledOnKeyguardCallbacks(i);
                    return;
                }
                return;
            }
            if (this.BIOMETRIC_APP_ENABLED.equals(uri)) {
                this.mBiometricEnabledForApps.put(java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(android.provider.Settings.Secure.getIntForUser(this.mContentResolver, "biometric_app_enabled", 1, i) != 0));
            }
        }

        public boolean getEnabledOnKeyguard(int i) {
            if (!this.mBiometricEnabledOnKeyguard.containsKey(java.lang.Integer.valueOf(i))) {
                if (this.mUseLegacyFaceOnlySettings) {
                    onChange(true, this.FACE_UNLOCK_KEYGUARD_ENABLED, i);
                } else {
                    onChange(true, this.BIOMETRIC_KEYGUARD_ENABLED, i);
                }
            }
            return this.mBiometricEnabledOnKeyguard.get(java.lang.Integer.valueOf(i)).booleanValue();
        }

        public boolean getEnabledForApps(int i) {
            if (!this.mBiometricEnabledForApps.containsKey(java.lang.Integer.valueOf(i))) {
                if (this.mUseLegacyFaceOnlySettings) {
                    onChange(true, this.FACE_UNLOCK_APP_ENABLED, i);
                } else {
                    onChange(true, this.BIOMETRIC_APP_ENABLED, i);
                }
            }
            return this.mBiometricEnabledForApps.getOrDefault(java.lang.Integer.valueOf(i), true).booleanValue();
        }

        public boolean getConfirmationAlwaysRequired(int i, int i2) {
            switch (i) {
                case 8:
                    if (!this.mFaceAlwaysRequireConfirmation.containsKey(java.lang.Integer.valueOf(i2))) {
                        onChange(true, this.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION, i2);
                    }
                    return this.mFaceAlwaysRequireConfirmation.get(java.lang.Integer.valueOf(i2)).booleanValue();
                default:
                    return false;
            }
        }

        void notifyEnabledOnKeyguardCallbacks(int i) {
            java.util.List<com.android.server.biometrics.BiometricService.EnabledOnKeyguardCallback> list = this.mCallbacks;
            for (int i2 = 0; i2 < list.size(); i2++) {
                list.get(i2).notify(this.mBiometricEnabledOnKeyguard.getOrDefault(java.lang.Integer.valueOf(i), true).booleanValue(), i);
            }
        }
    }

    final class EnabledOnKeyguardCallback implements android.os.IBinder.DeathRecipient {
        private final android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback mCallback;

        EnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
            this.mCallback = iBiometricEnabledOnKeyguardCallback;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.biometrics.BiometricService.TAG, "Unable to linkToDeath", e);
            }
        }

        void notify(boolean z, int i) {
            try {
                this.mCallback.onChanged(z, i);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.w(com.android.server.biometrics.BiometricService.TAG, "Death while invoking notify", e);
                com.android.server.biometrics.BiometricService.this.mEnabledOnKeyguardCallbacks.remove(this);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.w(com.android.server.biometrics.BiometricService.TAG, "Failed to invoke onChanged", e2);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Enabled callback binder died");
            com.android.server.biometrics.BiometricService.this.mEnabledOnKeyguardCallbacks.remove(this);
        }
    }

    /* renamed from: com.android.server.biometrics.BiometricService$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.biometrics.IBiometricSensorReceiver.Stub {
        final /* synthetic */ long val$requestId;

        AnonymousClass1(long j) {
            this.val$requestId = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationSucceeded$0(long j, int i, byte[] bArr) {
            com.android.server.biometrics.BiometricService.this.handleAuthenticationSucceeded(j, i, bArr);
        }

        public void onAuthenticationSucceeded(final int i, final byte[] bArr) {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass1.this.lambda$onAuthenticationSucceeded$0(j, i, bArr);
                }
            });
        }

        public void onAuthenticationFailed(final int i) {
            android.util.Slog.v(com.android.server.biometrics.BiometricService.TAG, "onAuthenticationFailed");
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass1.this.lambda$onAuthenticationFailed$1(j, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticationFailed$1(long j, int i) {
            com.android.server.biometrics.BiometricService.this.handleAuthenticationRejected(j, i);
        }

        public void onError(final int i, final int i2, final int i3, final int i4) {
            if (i3 == 3) {
                android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
                final long j = this.val$requestId;
                handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.biometrics.BiometricService.AnonymousClass1.this.lambda$onError$2(j, i, i2, i3, i4);
                    }
                });
            } else {
                android.os.Handler handler2 = com.android.server.biometrics.BiometricService.this.mHandler;
                final long j2 = this.val$requestId;
                handler2.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.biometrics.BiometricService.AnonymousClass1.this.lambda$onError$3(j2, i, i2, i3, i4);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(long j, int i, int i2, int i3, int i4) {
            com.android.server.biometrics.BiometricService.this.handleAuthenticationTimedOut(j, i, i2, i3, i4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$3(long j, int i, int i2, int i3, int i4) {
            com.android.server.biometrics.BiometricService.this.handleOnError(j, i, i2, i3, i4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$4(long j, int i, int i2, int i3) {
            com.android.server.biometrics.BiometricService.this.handleOnAcquired(j, i, i2, i3);
        }

        public void onAcquired(final int i, final int i2, final int i3) {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass1.this.lambda$onAcquired$4(j, i, i2, i3);
                }
            });
        }
    }

    private android.hardware.biometrics.IBiometricSensorReceiver createBiometricSensorReceiver(long j) {
        return new com.android.server.biometrics.BiometricService.AnonymousClass1(j);
    }

    /* renamed from: com.android.server.biometrics.BiometricService$2, reason: invalid class name */
    class AnonymousClass2 extends android.hardware.biometrics.IBiometricSysuiReceiver.Stub {
        final /* synthetic */ long val$requestId;

        AnonymousClass2(long j) {
            this.val$requestId = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDialogDismissed$0(long j, int i, byte[] bArr) {
            com.android.server.biometrics.BiometricService.this.handleOnDismissed(j, i, bArr);
        }

        public void onDialogDismissed(final int i, @android.annotation.Nullable final byte[] bArr) {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass2.this.lambda$onDialogDismissed$0(j, i, bArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTryAgainPressed$1(long j) {
            com.android.server.biometrics.BiometricService.this.handleOnTryAgainPressed(j);
        }

        public void onTryAgainPressed() {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass2.this.lambda$onTryAgainPressed$1(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDeviceCredentialPressed$2(long j) {
            com.android.server.biometrics.BiometricService.this.handleOnDeviceCredentialPressed(j);
        }

        public void onDeviceCredentialPressed() {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass2.this.lambda$onDeviceCredentialPressed$2(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemEvent$3(long j, int i) {
            com.android.server.biometrics.BiometricService.this.handleOnSystemEvent(j, i);
        }

        public void onSystemEvent(final int i) {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass2.this.lambda$onSystemEvent$3(j, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDialogAnimatedIn$4(long j, boolean z) {
            com.android.server.biometrics.BiometricService.this.handleOnDialogAnimatedIn(j, z);
        }

        public void onDialogAnimatedIn(final boolean z) {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass2.this.lambda$onDialogAnimatedIn$4(j, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStartFingerprintNow$5(long j) {
            com.android.server.biometrics.BiometricService.this.handleOnStartFingerprintNow(j);
        }

        public void onStartFingerprintNow() {
            android.os.Handler handler = com.android.server.biometrics.BiometricService.this.mHandler;
            final long j = this.val$requestId;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.AnonymousClass2.this.lambda$onStartFingerprintNow$5(j);
                }
            });
        }
    }

    private android.hardware.biometrics.IBiometricSysuiReceiver createSysuiReceiver(long j) {
        return new com.android.server.biometrics.BiometricService.AnonymousClass2(j);
    }

    private com.android.server.biometrics.AuthSession.ClientDeathReceiver createClientDeathReceiver(final long j) {
        return new com.android.server.biometrics.AuthSession.ClientDeathReceiver() { // from class: com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.AuthSession.ClientDeathReceiver
            public final void onClientDied() {
                com.android.server.biometrics.BiometricService.this.lambda$createClientDeathReceiver$1(j);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createClientDeathReceiver$1(final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.BiometricService.this.lambda$createClientDeathReceiver$0(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class BiometricServiceWrapper extends android.hardware.biometrics.IBiometricService.Stub {
        private BiometricServiceWrapper() {
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
            super.createTestSession_enforcePermission();
            java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                com.android.server.biometrics.BiometricSensor next = it.next();
                if (next.id == i) {
                    return next.impl.createTestSession(iTestSessionCallback, str);
                }
            }
            android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Unknown sensor for createTestSession: " + i);
            return null;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public java.util.List<android.hardware.biometrics.SensorPropertiesInternal> getSensorProperties(java.lang.String str) throws android.os.RemoteException {
            super.getSensorProperties_enforcePermission();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                arrayList.add(android.hardware.biometrics.SensorPropertiesInternal.from(it.next().impl.getSensorProperties(str)));
            }
            return arrayList;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void onReadyForAuthentication(final long j, final int i) {
            super.onReadyForAuthentication_enforcePermission();
            com.android.server.biometrics.BiometricService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$BiometricServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.BiometricServiceWrapper.this.lambda$onReadyForAuthentication$0(j, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReadyForAuthentication$0(long j, int i) {
            com.android.server.biometrics.BiometricService.this.handleOnReadyForAuthentication(j, i);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long authenticate(final android.os.IBinder iBinder, final long j, final int i, final android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, final java.lang.String str, final android.hardware.biometrics.PromptInfo promptInfo) {
            super.authenticate_enforcePermission();
            if (iBinder == null || iBiometricServiceReceiver == null || str == null || promptInfo == null) {
                android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Unable to authenticate, one or more null arguments");
                return -1L;
            }
            if (!com.android.server.biometrics.Utils.isValidAuthenticatorConfig(promptInfo)) {
                throw new java.lang.SecurityException("Invalid authenticator configuration");
            }
            com.android.server.biometrics.Utils.combineAuthenticatorBundles(promptInfo);
            final long longValue = ((java.lang.Long) com.android.server.biometrics.BiometricService.this.mRequestCounter.get()).longValue();
            com.android.server.biometrics.BiometricService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$BiometricServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.BiometricServiceWrapper.this.lambda$authenticate$1(iBinder, longValue, j, i, iBiometricServiceReceiver, str, promptInfo);
                }
            });
            return longValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$authenticate$1(android.os.IBinder iBinder, long j, long j2, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo) {
            com.android.server.biometrics.BiometricService.this.handleAuthenticate(iBinder, j, j2, i, iBiometricServiceReceiver, str, promptInfo);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void cancelAuthentication(android.os.IBinder iBinder, java.lang.String str, final long j) {
            super.cancelAuthentication_enforcePermission();
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = iBinder;
            obtain.arg2 = str;
            obtain.arg3 = java.lang.Long.valueOf(j);
            com.android.server.biometrics.BiometricService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$BiometricServiceWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.BiometricService.BiometricServiceWrapper.this.lambda$cancelAuthentication$2(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cancelAuthentication$2(long j) {
            com.android.server.biometrics.BiometricService.this.handleCancelAuthentication(j);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public int canAuthenticate(java.lang.String str, int i, int i2, int i3) {
            super.canAuthenticate_enforcePermission();
            android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "canAuthenticate: User=" + i + ", Caller=" + i2 + ", Authenticators=" + i3);
            if (!com.android.server.biometrics.Utils.isValidAuthenticatorConfig(i3)) {
                throw new java.lang.SecurityException("Invalid authenticator configuration");
            }
            try {
                return com.android.server.biometrics.BiometricService.this.createPreAuthInfo(str, i, i3).getCanAuthenticateResult();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote exception", e);
                return 1;
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long getLastAuthenticationTime(int i, int i2) {
            super.getLastAuthenticationTime_enforcePermission();
            if (!android.hardware.biometrics.Flags.lastAuthenticationTime()) {
                throw new java.lang.UnsupportedOperationException();
            }
            com.android.server.utils.Slogf.d(com.android.server.biometrics.BiometricService.TAG, "getLastAuthenticationTime(userId=%d, authenticators=0x%x)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            try {
                long secureUserId = com.android.server.biometrics.BiometricService.this.mGateKeeper.getSecureUserId(i);
                if (secureUserId == 0) {
                    com.android.server.utils.Slogf.w(com.android.server.biometrics.BiometricService.TAG, "No secure user id for " + i);
                    return -1L;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(2);
                if ((32768 & i2) != 0) {
                    arrayList.add(1);
                }
                if ((i2 & 15) != 0) {
                    arrayList.add(2);
                }
                if (arrayList.isEmpty()) {
                    throw new java.lang.IllegalArgumentException("authenticators must not be empty");
                }
                try {
                    return com.android.server.biometrics.BiometricService.this.mKeystoreAuthorization.getLastAuthTime(secureUserId, arrayList.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray());
                } catch (android.os.ServiceSpecificException e) {
                    if (e.errorCode != 6) {
                        return -1L;
                    }
                    throw new java.lang.UnsupportedOperationException();
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.biometrics.BiometricService.TAG, "Error getting last auth time: " + e2);
                    return -1L;
                }
            } catch (android.os.RemoteException e3) {
                com.android.server.utils.Slogf.w(com.android.server.biometrics.BiometricService.TAG, "Failed to get secure user id for " + i, e3);
                return -1L;
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public boolean hasEnrolledBiometrics(int i, java.lang.String str) {
            super.hasEnrolledBiometrics_enforcePermission();
            try {
                java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
                while (it.hasNext()) {
                    if (it.next().impl.hasEnrolledTemplates(i, str)) {
                        return true;
                    }
                }
                return false;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote exception", e);
                return false;
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public synchronized void registerAuthenticator(int i, int i2, int i3, @android.annotation.NonNull android.hardware.biometrics.IBiometricAuthenticator iBiometricAuthenticator) {
            try {
                super.registerAuthenticator_enforcePermission();
                android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "Registering ID: " + i + " Modality: " + i2 + " Strength: " + i3);
                if (iBiometricAuthenticator == null) {
                    throw new java.lang.IllegalArgumentException("Authenticator must not be null. Did you forget to modify the core/res/res/values/xml overlay for config_biometric_sensors?");
                }
                if (i3 != 15 && i3 != 255 && i3 != 4095) {
                    throw new java.lang.IllegalStateException("Unsupported strength");
                }
                java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
                while (it.hasNext()) {
                    if (it.next().id == i) {
                        throw new java.lang.IllegalStateException("Cannot register duplicate authenticator");
                    }
                }
                com.android.server.biometrics.BiometricService.this.mSensors.add(new com.android.server.biometrics.BiometricSensor(com.android.server.biometrics.BiometricService.this.getContext(), i, i2, i3, iBiometricAuthenticator) { // from class: com.android.server.biometrics.BiometricService.BiometricServiceWrapper.1
                    @Override // com.android.server.biometrics.BiometricSensor
                    boolean confirmationAlwaysRequired(int i4) {
                        return com.android.server.biometrics.BiometricService.this.mSettingObserver.getConfirmationAlwaysRequired(this.modality, i4);
                    }

                    @Override // com.android.server.biometrics.BiometricSensor
                    boolean confirmationSupported() {
                        return com.android.server.biometrics.Utils.isConfirmationSupported(this.modality);
                    }
                });
                com.android.server.biometrics.BiometricService.this.mBiometricStrengthController.updateStrengths();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
            super.registerEnabledOnKeyguardCallback_enforcePermission();
            com.android.server.biometrics.BiometricService.this.mEnabledOnKeyguardCallbacks.add(com.android.server.biometrics.BiometricService.this.new EnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback));
            try {
                java.util.Iterator it = com.android.server.biometrics.BiometricService.this.mUserManager.getAliveUsers().iterator();
                while (it.hasNext()) {
                    int i = ((android.content.pm.UserInfo) it.next()).id;
                    iBiometricEnabledOnKeyguardCallback.onChanged(com.android.server.biometrics.BiometricService.this.mSettingObserver.getEnabledOnKeyguard(i), i);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.biometrics.BiometricService.TAG, "Remote exception", e);
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
            super.invalidateAuthenticatorIds_enforcePermission();
            com.android.server.biometrics.BiometricService.InvalidationTracker.start(com.android.server.biometrics.BiometricService.this.getContext(), com.android.server.biometrics.BiometricService.this.mSensors, i, i2, iInvalidationCallback);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public long[] getAuthenticatorIds(int i) {
            super.getAuthenticatorIds_enforcePermission();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                com.android.server.biometrics.BiometricSensor next = it.next();
                try {
                    boolean hasEnrolledTemplates = next.impl.hasEnrolledTemplates(i, com.android.server.biometrics.BiometricService.this.getContext().getOpPackageName());
                    long authenticatorId = next.impl.getAuthenticatorId(i);
                    if (!hasEnrolledTemplates || !com.android.server.biometrics.Utils.isAtLeastStrength(next.getCurrentStrength(), 15)) {
                        android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "Sensor " + next + ", sensorId " + next.id + ", hasEnrollments: " + hasEnrolledTemplates + " cannot participate in Keystore operations");
                    } else {
                        arrayList.add(java.lang.Long.valueOf(authenticatorId));
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "RemoteException", e);
                }
            }
            long[] jArr = new long[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                jArr[i2] = ((java.lang.Long) arrayList.get(i2)).longValue();
            }
            return jArr;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) {
            super.resetLockoutTimeBound_enforcePermission();
            if (!com.android.server.biometrics.Utils.isAtLeastStrength(com.android.server.biometrics.BiometricService.this.getSensorForId(i).getCurrentStrength(), 15)) {
                android.util.Slog.w(com.android.server.biometrics.BiometricService.TAG, "Sensor: " + i + " is does not meet the required strength to request resetLockout");
                return;
            }
            java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                com.android.server.biometrics.BiometricSensor next = it.next();
                if (next.id != i) {
                    try {
                        android.hardware.biometrics.SensorPropertiesInternal sensorProperties = next.impl.getSensorProperties(com.android.server.biometrics.BiometricService.this.getContext().getOpPackageName());
                        boolean z = sensorProperties.resetLockoutRequiresHardwareAuthToken && !sensorProperties.resetLockoutRequiresChallenge;
                        boolean z2 = !sensorProperties.resetLockoutRequiresHardwareAuthToken;
                        if (z || z2) {
                            android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "resetLockout from: " + i + ", for: " + next.id + ", userId: " + i2);
                            next.impl.resetLockout(iBinder, str, i2, bArr);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote exception", e);
                    }
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public void resetLockout(int i, byte[] bArr) {
            super.resetLockout_enforcePermission();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("resetLockout(userId=");
            sb.append(i);
            sb.append(", hat=");
            sb.append(bArr == null ? "null " : "present");
            sb.append(")");
            android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, sb.toString());
            com.android.server.biometrics.BiometricService.this.mBiometricContext.getAuthSessionCoordinator().resetLockoutFor(i, 15, -1L);
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public int getCurrentStrength(int i) {
            super.getCurrentStrength_enforcePermission();
            java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
            while (it.hasNext()) {
                com.android.server.biometrics.BiometricSensor next = it.next();
                if (next.id == i) {
                    return next.getCurrentStrength();
                }
            }
            android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Unknown sensorId: " + i);
            return 0;
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public int getCurrentModality(java.lang.String str, int i, int i2, int i3) {
            super.getCurrentModality_enforcePermission();
            android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "getCurrentModality: User=" + i + ", Caller=" + i2 + ", Authenticators=" + i3);
            if (!com.android.server.biometrics.Utils.isValidAuthenticatorConfig(i3)) {
                throw new java.lang.SecurityException("Invalid authenticator configuration");
            }
            try {
                return ((java.lang.Integer) com.android.server.biometrics.BiometricService.this.createPreAuthInfo(str, i, i3).getPreAuthenticateStatus().first).intValue();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote exception", e);
                return 0;
            }
        }

        @android.annotation.EnforcePermission("android.permission.USE_BIOMETRIC_INTERNAL")
        public int getSupportedModalities(int i) {
            int i2;
            super.getSupportedModalities_enforcePermission();
            android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "getSupportedModalities: Authenticators=" + i);
            if (!com.android.server.biometrics.Utils.isValidAuthenticatorConfig(i)) {
                throw new java.lang.SecurityException("Invalid authenticator configuration");
            }
            if (com.android.server.biometrics.Utils.isCredentialRequested(i)) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (com.android.server.biometrics.Utils.isBiometricRequested(i)) {
                int publicBiometricStrength = com.android.server.biometrics.Utils.getPublicBiometricStrength(i);
                java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
                while (it.hasNext()) {
                    com.android.server.biometrics.BiometricSensor next = it.next();
                    if (com.android.server.biometrics.Utils.isAtLeastStrength(next.getCurrentStrength(), publicBiometricStrength)) {
                        i2 |= next.modality;
                    }
                }
            }
            return i2;
        }

        protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.biometrics.BiometricService.this.getContext(), com.android.server.biometrics.BiometricService.TAG, printWriter)) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.BiometricService.TAG, "Remote exception", e);
                }
                if (strArr.length > 0) {
                    if ("--proto".equals(strArr[0])) {
                        boolean z = true;
                        if (strArr.length <= 1 || !"--clear-scheduler-buffer".equals(strArr[1])) {
                            z = false;
                        }
                        android.util.Slog.d(com.android.server.biometrics.BiometricService.TAG, "ClearSchedulerBuffer: " + z);
                        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
                        protoOutputStream.write(1159641169922L, com.android.server.biometrics.BiometricService.this.mAuthSession != null ? com.android.server.biometrics.BiometricService.this.mAuthSession.getState() : 0);
                        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = com.android.server.biometrics.BiometricService.this.mSensors.iterator();
                        while (it.hasNext()) {
                            protoOutputStream.write(2246267895809L, it.next().impl.dumpSensorServiceStateProto(z));
                        }
                        protoOutputStream.flush();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                com.android.server.biometrics.BiometricService.this.dumpInternal(printWriter);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    private void checkInternalPermission() {
        getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL", "Must have USE_BIOMETRIC_INTERNAL permission");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public com.android.server.biometrics.PreAuthInfo createPreAuthInfo(@android.annotation.NonNull java.lang.String str, int i, int i2) throws android.os.RemoteException {
        android.hardware.biometrics.PromptInfo promptInfo = new android.hardware.biometrics.PromptInfo();
        promptInfo.setAuthenticators(i2);
        return com.android.server.biometrics.PreAuthInfo.create(this.mTrustManager, this.mDevicePolicyManager, this.mSettingObserver, this.mSensors, i, promptInfo, str, false, getContext(), this.mBiometricCameraManager);
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Injector {
        public android.app.IActivityManager getActivityManagerService() {
            return android.app.ActivityManager.getService();
        }

        public android.security.authorization.IKeystoreAuthorization getKeystoreAuthorizationService() {
            return android.security.Authorization.getService();
        }

        public android.service.gatekeeper.IGateKeeperService getGateKeeperService() {
            return android.security.GateKeeper.getService();
        }

        public android.app.trust.ITrustManager getTrustManager() {
            return android.app.trust.ITrustManager.Stub.asInterface(android.os.ServiceManager.getService("trust"));
        }

        public com.android.internal.statusbar.IStatusBarService getStatusBarService() {
            return com.android.internal.statusbar.IStatusBarService.Stub.asInterface(android.os.ServiceManager.getService("statusbar"));
        }

        public com.android.server.biometrics.BiometricService.SettingObserver getSettingObserver(android.content.Context context, android.os.Handler handler, java.util.List<com.android.server.biometrics.BiometricService.EnabledOnKeyguardCallback> list) {
            return new com.android.server.biometrics.BiometricService.SettingObserver(context, handler, list);
        }

        public android.security.KeyStore getKeyStore() {
            return android.security.KeyStore.getInstance();
        }

        public boolean isDebugEnabled(android.content.Context context, int i) {
            return com.android.server.biometrics.Utils.isDebugEnabled(context, i);
        }

        public void publishBinderService(com.android.server.biometrics.BiometricService biometricService, android.hardware.biometrics.IBiometricService.Stub stub) {
            biometricService.publishBinderService("biometric", stub);
        }

        public com.android.server.biometrics.BiometricStrengthController getBiometricStrengthController(com.android.server.biometrics.BiometricService biometricService) {
            return new com.android.server.biometrics.BiometricStrengthController(biometricService);
        }

        public java.lang.String[] getConfiguration(android.content.Context context) {
            return context.getResources().getStringArray(android.R.array.config_biometric_sensors);
        }

        public android.app.admin.DevicePolicyManager getDevicePolicyManager(android.content.Context context) {
            return (android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class);
        }

        public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getFingerprintSensorProperties(android.content.Context context) {
            android.hardware.fingerprint.FingerprintManager fingerprintManager;
            if (context.getPackageManager().hasSystemFeature("android.hardware.fingerprint") && (fingerprintManager = (android.hardware.fingerprint.FingerprintManager) context.getSystemService(android.hardware.fingerprint.FingerprintManager.class)) != null) {
                return fingerprintManager.getSensorPropertiesInternal();
            }
            return new java.util.ArrayList();
        }

        public java.util.function.Supplier<java.lang.Long> getRequestGenerator() {
            final java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong(0L);
            return new java.util.function.Supplier() { // from class: com.android.server.biometrics.BiometricService$Injector$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Long lambda$getRequestGenerator$0;
                    lambda$getRequestGenerator$0 = com.android.server.biometrics.BiometricService.Injector.lambda$getRequestGenerator$0(atomicLong);
                    return lambda$getRequestGenerator$0;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Long lambda$getRequestGenerator$0(java.util.concurrent.atomic.AtomicLong atomicLong) {
            return java.lang.Long.valueOf(atomicLong.incrementAndGet());
        }

        public com.android.server.biometrics.log.BiometricContext getBiometricContext(android.content.Context context) {
            return com.android.server.biometrics.log.BiometricContext.getInstance(context);
        }

        public android.os.UserManager getUserManager(android.content.Context context) {
            return (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        }

        public com.android.server.biometrics.BiometricCameraManager getBiometricCameraManager(android.content.Context context) {
            return new com.android.server.biometrics.BiometricCameraManagerImpl((android.hardware.camera2.CameraManager) context.getSystemService(android.hardware.camera2.CameraManager.class), (android.hardware.SensorPrivacyManager) context.getSystemService(android.hardware.SensorPrivacyManager.class));
        }

        public com.android.server.biometrics.BiometricNotificationLogger getNotificationLogger() {
            return new com.android.server.biometrics.BiometricNotificationLogger();
        }
    }

    public BiometricService(android.content.Context context) {
        this(context, new com.android.server.biometrics.BiometricService.Injector(), com.android.server.biometrics.BiometricHandlerProvider.getInstance());
    }

    @com.android.internal.annotations.VisibleForTesting
    BiometricService(android.content.Context context, com.android.server.biometrics.BiometricService.Injector injector, com.android.server.biometrics.BiometricHandlerProvider biometricHandlerProvider) {
        super(context);
        this.mRandom = new java.util.Random();
        this.mSensors = new java.util.ArrayList<>();
        this.mInjector = injector;
        this.mHandler = biometricHandlerProvider.getBiometricCallbackHandler();
        this.mDevicePolicyManager = this.mInjector.getDevicePolicyManager(context);
        this.mImpl = new com.android.server.biometrics.BiometricService.BiometricServiceWrapper();
        this.mEnabledOnKeyguardCallbacks = new java.util.ArrayList();
        this.mSettingObserver = this.mInjector.getSettingObserver(context, this.mHandler, this.mEnabledOnKeyguardCallbacks);
        this.mRequestCounter = this.mInjector.getRequestGenerator();
        this.mBiometricContext = injector.getBiometricContext(context);
        this.mUserManager = injector.getUserManager(context);
        this.mBiometricCameraManager = injector.getBiometricCameraManager(context);
        this.mKeystoreAuthorization = injector.getKeystoreAuthorizationService();
        this.mGateKeeper = injector.getGateKeeperService();
        this.mBiometricNotificationLogger = injector.getNotificationLogger();
        try {
            injector.getActivityManagerService().registerUserSwitchObserver(new android.app.UserSwitchObserver() { // from class: com.android.server.biometrics.BiometricService.3
                public void onUserSwitchComplete(int i) {
                    com.android.server.biometrics.BiometricService.this.mSettingObserver.updateContentObserver();
                    com.android.server.biometrics.BiometricService.this.mSettingObserver.notifyEnabledOnKeyguardCallbacks(i);
                }
            }, com.android.server.biometrics.BiometricService.class.getName());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to register user switch observer", e);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.mKeyStore = this.mInjector.getKeyStore();
        this.mStatusBarService = this.mInjector.getStatusBarService();
        this.mTrustManager = this.mInjector.getTrustManager();
        this.mInjector.publishBinderService(this, this.mImpl);
        this.mBiometricStrengthController = this.mInjector.getBiometricStrengthController(this);
        this.mBiometricStrengthController.startListening();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    com.android.server.biometrics.BiometricService.this.mBiometricNotificationLogger.registerAsSystemService(com.android.server.biometrics.BiometricService.this.getContext(), new android.content.ComponentName(com.android.server.biometrics.BiometricService.this.getContext(), (java.lang.Class<?>) com.android.server.biometrics.BiometricNotificationLogger.class), -1);
                } catch (android.os.RemoteException e) {
                }
            }
        });
    }

    private boolean isStrongBiometric(int i) {
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mSensors.iterator();
        while (it.hasNext()) {
            com.android.server.biometrics.BiometricSensor next = it.next();
            if (next.id == i) {
                return com.android.server.biometrics.Utils.isAtLeastStrength(next.getCurrentStrength(), 15);
            }
        }
        android.util.Slog.e(TAG, "Unknown sensorId: " + i);
        return false;
    }

    @android.annotation.Nullable
    private com.android.server.biometrics.AuthSession getAuthSessionIfCurrent(long j) {
        com.android.server.biometrics.AuthSession authSession = this.mAuthSession;
        if (authSession != null && authSession.getRequestId() == j) {
            return authSession;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAuthenticationSucceeded(long j, int i, byte[] bArr) {
        android.util.Slog.v(TAG, "handleAuthenticationSucceeded(), sensorId: " + i);
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.e(TAG, "handleAuthenticationSucceeded: AuthSession is null");
        } else {
            authSessionIfCurrent.onAuthenticationSucceeded(i, isStrongBiometric(i), bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAuthenticationRejected(long j, int i) {
        android.util.Slog.v(TAG, "handleAuthenticationRejected()");
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleAuthenticationRejected: AuthSession is not current");
        } else {
            authSessionIfCurrent.onAuthenticationRejected(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAuthenticationTimedOut(long j, int i, int i2, int i3, int i4) {
        android.util.Slog.v(TAG, "handleAuthenticationTimedOut(), sensorId: " + i + ", cookie: " + i2 + ", error: " + i3 + ", vendorCode: " + i4);
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleAuthenticationTimedOut: AuthSession is not current");
        } else {
            authSessionIfCurrent.onAuthenticationTimedOut(i, i2, i3, i4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnError(long j, int i, int i2, int i3, int i4) {
        android.util.Slog.d(TAG, "handleOnError() sensorId: " + i + ", cookie: " + i2 + ", error: " + i3 + ", vendorCode: " + i4);
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleOnError: AuthSession is not current");
            return;
        }
        try {
            if (authSessionIfCurrent.onErrorReceived(i, i2, i3, i4)) {
                android.util.Slog.d(TAG, "handleOnError: AuthSession finished");
                this.mAuthSession = null;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnAcquired(long j, int i, int i2, int i3) {
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "onAcquired: AuthSession is not current");
        } else {
            authSessionIfCurrent.onAcquired(i, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDismissed(long j, int i, @android.annotation.Nullable byte[] bArr) {
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.e(TAG, "onDismissed: " + i + ", AuthSession is not current");
            return;
        }
        authSessionIfCurrent.onDialogDismissed(i, bArr);
        this.mAuthSession = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnTryAgainPressed(long j) {
        android.util.Slog.d(TAG, "onTryAgainPressed");
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleOnTryAgainPressed: AuthSession is not current");
        } else {
            authSessionIfCurrent.onTryAgainPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDeviceCredentialPressed(long j) {
        android.util.Slog.d(TAG, "onDeviceCredentialPressed");
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleOnDeviceCredentialPressed: AuthSession is not current");
        } else {
            authSessionIfCurrent.onDeviceCredentialPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnSystemEvent(long j, int i) {
        android.util.Slog.d(TAG, "onSystemEvent: " + i);
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleOnSystemEvent: AuthSession is not current");
        } else {
            authSessionIfCurrent.onSystemEvent(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleClientDied, reason: merged with bridge method [inline-methods] */
    public void lambda$createClientDeathReceiver$0(long j) {
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleClientDied: AuthSession is not current");
            return;
        }
        android.util.Slog.e(TAG, "Session: " + authSessionIfCurrent);
        if (authSessionIfCurrent.onClientDied()) {
            this.mAuthSession = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDialogAnimatedIn(long j, boolean z) {
        android.util.Slog.d(TAG, "handleOnDialogAnimatedIn");
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleOnDialogAnimatedIn: AuthSession is not current");
        } else {
            authSessionIfCurrent.onDialogAnimatedIn(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnStartFingerprintNow(long j) {
        android.util.Slog.d(TAG, "handleOnStartFingerprintNow");
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleOnStartFingerprintNow: AuthSession is not current");
        } else {
            authSessionIfCurrent.onStartFingerprint();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnReadyForAuthentication(long j, int i) {
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleOnReadyForAuthentication: AuthSession is not current");
        } else {
            authSessionIfCurrent.onCookieReceived(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAuthenticate(final android.os.IBinder iBinder, final long j, final long j2, final int i, final android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, final java.lang.String str, final android.hardware.biometrics.PromptInfo promptInfo) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.BiometricService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.BiometricService.this.lambda$handleAuthenticate$2(i, promptInfo, str, j, iBinder, j2, iBiometricServiceReceiver);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleAuthenticate$2(int i, android.hardware.biometrics.PromptInfo promptInfo, java.lang.String str, long j, android.os.IBinder iBinder, long j2, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver) {
        try {
            com.android.server.biometrics.PreAuthInfo create = com.android.server.biometrics.PreAuthInfo.create(this.mTrustManager, this.mDevicePolicyManager, this.mSettingObserver, this.mSensors, i, promptInfo, str, promptInfo.isDisallowBiometricsIfPolicyExists(), getContext(), this.mBiometricCameraManager);
            if (promptInfo.isUseDefaultTitle() && android.text.TextUtils.isEmpty(promptInfo.getTitle())) {
                promptInfo.setTitle(getContext().getString(android.R.string.battery_saver_description_with_learn_more));
            }
            int eligibleModalities = create.getEligibleModalities();
            boolean z = true;
            boolean z2 = (eligibleModalities & 2) == 2;
            if ((eligibleModalities & 8) != 8) {
                z = false;
            }
            if (promptInfo.isUseDefaultSubtitle()) {
                if (z2 && z) {
                    promptInfo.setSubtitle(getContext().getString(android.R.string.battery_saver_description));
                } else if (z2) {
                    promptInfo.setSubtitle(getContext().getString(android.R.string.fcError));
                } else if (z) {
                    promptInfo.setSubtitle(getContext().getString(android.R.string.face_acquired_recalibrate_alt));
                } else {
                    promptInfo.setSubtitle(getContext().getString(android.R.string.roamingText5));
                }
            }
            android.util.Pair<java.lang.Integer, java.lang.Integer> preAuthenticateStatus = create.getPreAuthenticateStatus();
            android.util.Slog.d(TAG, "handleAuthenticate: modality(" + preAuthenticateStatus.first + "), status(" + preAuthenticateStatus.second + "), preAuthInfo: " + create + " requestId: " + j + " promptInfo.isIgnoreEnrollmentState: " + promptInfo.isIgnoreEnrollmentState());
            if (((java.lang.Integer) preAuthenticateStatus.second).intValue() == 0) {
                if (create.credentialRequested && create.credentialAvailable && create.eligibleSensors.isEmpty()) {
                    promptInfo.setAuthenticators(32768);
                }
                authenticateInternal(iBinder, j, j2, i, iBiometricServiceReceiver, str, promptInfo, create);
                return;
            }
            iBiometricServiceReceiver.onError(((java.lang.Integer) preAuthenticateStatus.first).intValue(), ((java.lang.Integer) preAuthenticateStatus.second).intValue(), 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
    }

    private void authenticateInternal(android.os.IBinder iBinder, long j, long j2, int i, android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, java.lang.String str, android.hardware.biometrics.PromptInfo promptInfo, com.android.server.biometrics.PreAuthInfo preAuthInfo) {
        android.util.Slog.d(TAG, "Creating authSession with authRequest: " + preAuthInfo);
        if (this.mAuthSession != null) {
            android.util.Slog.w(TAG, "Existing AuthSession: " + this.mAuthSession);
            this.mAuthSession.onCancelAuthSession(true);
            this.mAuthSession = null;
        }
        this.mAuthSession = new com.android.server.biometrics.AuthSession(getContext(), this.mBiometricContext, this.mStatusBarService, createSysuiReceiver(j), this.mKeyStore, this.mRandom, createClientDeathReceiver(j), preAuthInfo, iBinder, j, j2, i, createBiometricSensorReceiver(j), iBiometricServiceReceiver, str, promptInfo, this.mInjector.isDebugEnabled(getContext(), i), this.mInjector.getFingerprintSensorProperties(getContext()));
        try {
            this.mAuthSession.goToInitialState();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCancelAuthentication(long j) {
        com.android.server.biometrics.AuthSession authSessionIfCurrent = getAuthSessionIfCurrent(j);
        if (authSessionIfCurrent == null) {
            android.util.Slog.w(TAG, "handleCancelAuthentication: AuthSession is not current");
        } else if (authSessionIfCurrent.onCancelAuthSession(false)) {
            android.util.Slog.d(TAG, "handleCancelAuthentication: AuthSession finished");
            this.mAuthSession = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.biometrics.BiometricSensor getSensorForId(int i) {
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mSensors.iterator();
        while (it.hasNext()) {
            com.android.server.biometrics.BiometricSensor next = it.next();
            if (next.id == i) {
                return next;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(java.io.PrintWriter printWriter) {
        printWriter.println("Legacy Settings: " + this.mSettingObserver.mUseLegacyFaceOnlySettings);
        printWriter.println();
        printWriter.println("Sensors:");
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mSensors.iterator();
        while (it.hasNext()) {
            printWriter.println(" " + it.next());
        }
        printWriter.println();
        printWriter.println("CurrentSession: " + this.mAuthSession);
        printWriter.println();
    }
}
