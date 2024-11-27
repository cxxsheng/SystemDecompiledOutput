package com.android.server.biometrics.log;

/* loaded from: classes.dex */
public final class BiometricContextProvider implements com.android.server.biometrics.log.BiometricContext {
    private static final int SESSION_TYPES = 3;
    private static final java.lang.String TAG = "BiometricContextProvider";
    private static com.android.server.biometrics.log.BiometricContextProvider sInstance;
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;

    @android.annotation.Nullable
    private final android.os.Handler mHandler;
    private final android.view.WindowManager mWindowManager;

    @android.annotation.NonNull
    private final java.util.Map<com.android.server.biometrics.log.OperationContextExt, java.util.function.Consumer<android.hardware.biometrics.common.OperationContext>> mSubscribers = new java.util.concurrent.ConcurrentHashMap();

    @android.annotation.Nullable
    private final java.util.Map<java.lang.Integer, com.android.server.biometrics.log.BiometricContextSessionInfo> mSession = new java.util.concurrent.ConcurrentHashMap();
    private int mDockState = 0;
    private int mFoldState = 0;
    private int mDisplayState = 0;
    private boolean mIsHardwareIgnoringTouches = false;

    @com.android.internal.annotations.VisibleForTesting
    final android.content.BroadcastReceiver mDockStateReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.biometrics.log.BiometricContextProvider.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.biometrics.log.BiometricContextProvider.this.mDockState = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
        }
    };

    static com.android.server.biometrics.log.BiometricContextProvider defaultProvider(@android.annotation.NonNull android.content.Context context) {
        synchronized (com.android.server.biometrics.log.BiometricContextProvider.class) {
            if (sInstance == null) {
                try {
                    sInstance = new com.android.server.biometrics.log.BiometricContextProvider(context, (android.view.WindowManager) context.getSystemService("window"), com.android.internal.statusbar.IStatusBarService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("statusbar")), null, new com.android.server.biometrics.sensors.AuthSessionCoordinator());
                } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                    throw new java.lang.IllegalStateException("Failed to find required service", e);
                }
            }
        }
        return sInstance;
    }

    @com.android.internal.annotations.VisibleForTesting
    public BiometricContextProvider(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.view.WindowManager windowManager, @android.annotation.NonNull com.android.internal.statusbar.IStatusBarService iStatusBarService, @android.annotation.Nullable android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator) {
        this.mWindowManager = windowManager;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mHandler = handler;
        subscribeBiometricContextListener(iStatusBarService);
        subscribeDockState(context);
    }

    private void subscribeBiometricContextListener(@android.annotation.NonNull com.android.internal.statusbar.IStatusBarService iStatusBarService) {
        try {
            iStatusBarService.setBiometicContextListener(new android.hardware.biometrics.IBiometricContextListener.Stub() { // from class: com.android.server.biometrics.log.BiometricContextProvider.2
                public void onFoldChanged(int i) {
                    if (com.android.server.biometrics.log.BiometricContextProvider.this.mFoldState != i) {
                        com.android.server.biometrics.log.BiometricContextProvider.this.mFoldState = i;
                        com.android.server.biometrics.log.BiometricContextProvider.this.notifyChanged();
                    }
                }

                public void onDisplayStateChanged(int i) {
                    if (i != com.android.server.biometrics.log.BiometricContextProvider.this.mDisplayState) {
                        com.android.server.biometrics.log.BiometricContextProvider.this.mDisplayState = i;
                        com.android.server.biometrics.log.BiometricContextProvider.this.notifyChanged();
                    }
                }

                public void onHardwareIgnoreTouchesChanged(boolean z) {
                    if (com.android.server.biometrics.log.BiometricContextProvider.this.mIsHardwareIgnoringTouches != z) {
                        com.android.server.biometrics.log.BiometricContextProvider.this.mIsHardwareIgnoringTouches = z;
                        com.android.server.biometrics.log.BiometricContextProvider.this.notifyChanged();
                    }
                }
            });
            iStatusBarService.registerSessionListener(3, new com.android.internal.statusbar.ISessionListener.Stub() { // from class: com.android.server.biometrics.log.BiometricContextProvider.3
                public void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) {
                    com.android.server.biometrics.log.BiometricContextProvider.this.mSession.put(java.lang.Integer.valueOf(i), new com.android.server.biometrics.log.BiometricContextSessionInfo(instanceId));
                }

                public void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) {
                    com.android.server.biometrics.log.BiometricContextSessionInfo biometricContextSessionInfo = (com.android.server.biometrics.log.BiometricContextSessionInfo) com.android.server.biometrics.log.BiometricContextProvider.this.mSession.remove(java.lang.Integer.valueOf(i));
                    if (biometricContextSessionInfo != null && instanceId != null && biometricContextSessionInfo.getId() != instanceId.getId()) {
                        android.util.Slog.w(com.android.server.biometrics.log.BiometricContextProvider.TAG, "session id mismatch");
                    }
                }
            });
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to register biometric context listener", e);
        }
    }

    private void subscribeDockState(@android.annotation.NonNull android.content.Context context) {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        context.registerReceiver(this.mDockStateReceiver, intentFilter);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public com.android.server.biometrics.log.OperationContextExt updateContext(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt, boolean z) {
        return operationContextExt.update(this, z);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    @android.annotation.Nullable
    public com.android.server.biometrics.log.BiometricContextSessionInfo getKeyguardEntrySessionInfo() {
        return this.mSession.get(1);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    @android.annotation.Nullable
    public com.android.server.biometrics.log.BiometricContextSessionInfo getBiometricPromptSessionInfo() {
        return this.mSession.get(2);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public boolean isAod() {
        return this.mDisplayState == 4;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public boolean isAwake() {
        switch (this.mDisplayState) {
            case 0:
            case 1:
            case 3:
                return true;
            case 2:
            default:
                return false;
        }
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public boolean isDisplayOn() {
        return this.mWindowManager.getDefaultDisplay().getState() == 2;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getDockedState() {
        return this.mDockState;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getFoldState() {
        return this.mFoldState;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getCurrentRotation() {
        return this.mWindowManager.getDefaultDisplay().getRotation();
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public int getDisplayState() {
        return this.mDisplayState;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public boolean isHardwareIgnoringTouches() {
        return this.mIsHardwareIgnoringTouches;
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public void subscribe(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt, @android.annotation.NonNull java.util.function.Consumer<android.hardware.biometrics.common.OperationContext> consumer) {
        this.mSubscribers.put(operationContextExt, consumer);
        if (operationContextExt.getDisplayState() != getDisplayState()) {
            consumer.accept(operationContextExt.update(this, operationContextExt.isCrypto()).toAidlContext());
        }
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public void subscribe(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt, @android.annotation.NonNull java.util.function.Consumer<android.hardware.biometrics.common.OperationContext> consumer, @android.annotation.NonNull java.util.function.Consumer<android.hardware.biometrics.common.OperationContext> consumer2, @android.annotation.Nullable android.hardware.biometrics.AuthenticateOptions authenticateOptions) {
        this.mSubscribers.put(updateContext(operationContextExt, operationContextExt.isCrypto()), consumer2);
        if (authenticateOptions != null) {
            consumer.accept(operationContextExt.toAidlContext(authenticateOptions));
        } else {
            consumer.accept(operationContextExt.toAidlContext());
        }
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public void unsubscribe(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt) {
        this.mSubscribers.remove(operationContextExt);
    }

    @Override // com.android.server.biometrics.log.BiometricContext
    public com.android.server.biometrics.sensors.AuthSessionCoordinator getAuthSessionCoordinator() {
        return this.mAuthSessionCoordinator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyChanged() {
        if (this.mHandler != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.log.BiometricContextProvider$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.log.BiometricContextProvider.this.notifySubscribers();
                }
            });
        } else {
            notifySubscribers();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySubscribers() {
        this.mSubscribers.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.biometrics.log.BiometricContextProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.biometrics.log.BiometricContextProvider.this.lambda$notifySubscribers$0((com.android.server.biometrics.log.OperationContextExt) obj, (java.util.function.Consumer) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifySubscribers$0(com.android.server.biometrics.log.OperationContextExt operationContextExt, java.util.function.Consumer consumer) {
        consumer.accept(operationContextExt.update(this, operationContextExt.isCrypto()).toAidlContext());
    }

    public java.lang.String toString() {
        return "[keyguard session: " + getKeyguardEntrySessionInfo() + ", bp session: " + getBiometricPromptSessionInfo() + ", displayState: " + getDisplayState() + ", isAwake: " + isAwake() + ", isHardwareIgnoring: " + isHardwareIgnoringTouches() + ", isDisplayOn: " + isDisplayOn() + ", dock: " + getDockedState() + ", rotation: " + getCurrentRotation() + ", foldState: " + this.mFoldState + "]";
    }
}
