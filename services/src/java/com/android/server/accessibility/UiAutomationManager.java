package com.android.server.accessibility;

/* loaded from: classes.dex */
class UiAutomationManager {
    private static final android.content.ComponentName COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "UiAutomation");
    private static final java.lang.String LOG_TAG = "UiAutomationManager";
    private final java.lang.Object mLock;
    private com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport mSystemSupport;
    private int mUiAutomationFlags;
    private com.android.server.accessibility.UiAutomationManager.UiAutomationService mUiAutomationService;
    private android.os.IBinder mUiAutomationServiceOwner;
    private final android.os.IBinder.DeathRecipient mUiAutomationServiceOwnerDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.accessibility.UiAutomationManager.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.accessibility.UiAutomationManager.this.mUiAutomationServiceOwner.unlinkToDeath(this, 0);
            com.android.server.accessibility.UiAutomationManager.this.mUiAutomationServiceOwner = null;
            com.android.server.accessibility.UiAutomationManager.this.destroyUiAutomationService();
            android.util.Slog.v(com.android.server.accessibility.UiAutomationManager.LOG_TAG, "UiAutomation service owner died");
        }
    };

    UiAutomationManager(java.lang.Object obj) {
        this.mLock = obj;
    }

    void registerUiTestAutomationServiceLocked(android.os.IBinder iBinder, android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, android.content.Context context, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, android.os.Handler handler, com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport systemSupport, android.accessibilityservice.AccessibilityTrace accessibilityTrace, com.android.server.wm.WindowManagerInternal windowManagerInternal, com.android.server.accessibility.SystemActionPerformer systemActionPerformer, com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager, int i2) {
        accessibilityServiceInfo.setComponentName(COMPONENT_NAME);
        com.android.server.utils.Slogf.i(LOG_TAG, "Registering UiTestAutomationService (id=%s) when called by user %d", accessibilityServiceInfo.getId(), java.lang.Integer.valueOf(android.os.Binder.getCallingUserHandle().getIdentifier()));
        if (this.mUiAutomationService != null) {
            throw new java.lang.IllegalStateException("UiAutomationService " + this.mUiAutomationService.mServiceInterface + "already registered!");
        }
        try {
            iBinder.linkToDeath(this.mUiAutomationServiceOwnerDeathRecipient, 0);
            this.mUiAutomationFlags = i2;
            this.mSystemSupport = systemSupport;
            if (useAccessibility()) {
                this.mUiAutomationService = new com.android.server.accessibility.UiAutomationManager.UiAutomationService(context, accessibilityServiceInfo, i, handler, this.mLock, accessibilitySecurityPolicy, systemSupport, accessibilityTrace, windowManagerInternal, systemActionPerformer, accessibilityWindowManager);
                this.mUiAutomationServiceOwner = iBinder;
                this.mUiAutomationService.mServiceInterface = iAccessibilityServiceClient;
                try {
                    this.mUiAutomationService.mServiceInterface.asBinder().linkToDeath(this.mUiAutomationService, 0);
                    com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
                    this.mUiAutomationService.addWindowTokensForAllDisplays();
                    this.mUiAutomationService.connectServiceUnknownThread();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(LOG_TAG, "Failed registering death link: " + e);
                    destroyUiAutomationService();
                }
            }
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(LOG_TAG, "Couldn't register for the death of a UiTestAutomationService!", e2);
        }
    }

    void unregisterUiTestAutomationServiceLocked(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient) {
        synchronized (this.mLock) {
            try {
                if (useAccessibility() && (this.mUiAutomationService == null || iAccessibilityServiceClient == null || this.mUiAutomationService.mServiceInterface == null || iAccessibilityServiceClient.asBinder() != this.mUiAutomationService.mServiceInterface.asBinder())) {
                    throw new java.lang.IllegalStateException("UiAutomationService " + iAccessibilityServiceClient + " not registered!");
                }
                destroyUiAutomationService();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void sendAccessibilityEventLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mUiAutomationService != null) {
            this.mUiAutomationService.notifyAccessibilityEvent(accessibilityEvent);
        }
    }

    boolean isUiAutomationRunningLocked() {
        return (this.mUiAutomationService == null && useAccessibility()) ? false : true;
    }

    boolean suppressingAccessibilityServicesLocked() {
        return !(this.mUiAutomationService == null && useAccessibility()) && (this.mUiAutomationFlags & 1) == 0;
    }

    boolean useAccessibility() {
        return (this.mUiAutomationFlags & 2) == 0;
    }

    boolean canIntrospect() {
        return this.mUiAutomationService != null;
    }

    boolean isTouchExplorationEnabledLocked() {
        return this.mUiAutomationService != null && this.mUiAutomationService.mRequestTouchExplorationMode;
    }

    boolean canRetrieveInteractiveWindowsLocked() {
        return this.mUiAutomationService != null && this.mUiAutomationService.mRetrieveInteractiveWindows;
    }

    int getRequestedEventMaskLocked() {
        if (this.mUiAutomationService == null) {
            return 0;
        }
        return this.mUiAutomationService.mEventTypes;
    }

    int getRelevantEventTypes() {
        com.android.server.accessibility.UiAutomationManager.UiAutomationService uiAutomationService;
        synchronized (this.mLock) {
            uiAutomationService = this.mUiAutomationService;
        }
        if (uiAutomationService == null) {
            return 0;
        }
        return uiAutomationService.getRelevantEventTypes();
    }

    @android.annotation.Nullable
    android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() {
        com.android.server.accessibility.UiAutomationManager.UiAutomationService uiAutomationService;
        synchronized (this.mLock) {
            uiAutomationService = this.mUiAutomationService;
        }
        if (uiAutomationService == null) {
            return null;
        }
        return uiAutomationService.getServiceInfo();
    }

    void dumpUiAutomationService(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.server.accessibility.UiAutomationManager.UiAutomationService uiAutomationService;
        synchronized (this.mLock) {
            uiAutomationService = this.mUiAutomationService;
        }
        if (uiAutomationService != null) {
            uiAutomationService.dump(fileDescriptor, printWriter, strArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyUiAutomationService() {
        synchronized (this.mLock) {
            try {
                if (this.mUiAutomationService != null) {
                    this.mUiAutomationService.mServiceInterface.asBinder().unlinkToDeath(this.mUiAutomationService, 0);
                    this.mUiAutomationService.onRemoved();
                    this.mUiAutomationService.resetLocked();
                    this.mUiAutomationService = null;
                    if (this.mUiAutomationServiceOwner != null) {
                        this.mUiAutomationServiceOwner.unlinkToDeath(this.mUiAutomationServiceOwnerDeathRecipient, 0);
                        this.mUiAutomationServiceOwner = null;
                    }
                }
                this.mUiAutomationFlags = 0;
                this.mSystemSupport.onClientChangeLocked(false);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class UiAutomationService extends com.android.server.accessibility.AbstractAccessibilityServiceConnection {
        private final android.os.Handler mMainHandler;

        UiAutomationService(android.content.Context context, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, android.os.Handler handler, java.lang.Object obj, com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport systemSupport, android.accessibilityservice.AccessibilityTrace accessibilityTrace, com.android.server.wm.WindowManagerInternal windowManagerInternal, com.android.server.accessibility.SystemActionPerformer systemActionPerformer, com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager) {
            super(context, com.android.server.accessibility.UiAutomationManager.COMPONENT_NAME, accessibilityServiceInfo, i, handler, obj, accessibilitySecurityPolicy, systemSupport, accessibilityTrace, windowManagerInternal, systemActionPerformer, accessibilityWindowManager);
            boolean z = handler.getLooper() == android.os.Looper.getMainLooper();
            if (android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) {
                com.android.internal.util.Preconditions.checkArgument(z, "UiAutomationService must use the main handler");
            } else if (!z) {
                android.util.Slog.e(com.android.server.accessibility.UiAutomationManager.LOG_TAG, "UiAutomationService must use the main handler");
            }
            this.mMainHandler = handler;
            setDisplayTypes(3);
        }

        void connectServiceUnknownThread() {
            this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.UiAutomationManager$UiAutomationService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.accessibility.UiAutomationManager.UiAutomationService.this.lambda$connectServiceUnknownThread$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$connectServiceUnknownThread$0() {
            android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient;
            try {
                synchronized (this.mLock) {
                    try {
                        iAccessibilityServiceClient = this.mServiceInterface;
                        if (iAccessibilityServiceClient == null) {
                            this.mService = null;
                        } else {
                            this.mService = this.mServiceInterface.asBinder();
                            this.mService.linkToDeath(this, 0);
                        }
                    } finally {
                    }
                }
                if (iAccessibilityServiceClient != null) {
                    com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
                    if (this.mTrace.isA11yTracingEnabledForTypes(2L)) {
                        this.mTrace.logTrace("UiAutomationService.connectServiceUnknownThread", 2L, "serviceConnection=" + this + ";connectionId=" + this.mId + "windowToken=" + this.mOverlayWindowTokens.get(0));
                    }
                    iAccessibilityServiceClient.init(this, this.mId, this.mOverlayWindowTokens.get(0));
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.accessibility.UiAutomationManager.LOG_TAG, "Error initializing connection", e);
                com.android.server.accessibility.UiAutomationManager.this.destroyUiAutomationService();
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.accessibility.UiAutomationManager.this.destroyUiAutomationService();
        }

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        protected boolean hasRightsToCurrentUserLocked() {
            return true;
        }

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        protected boolean supportsFlagForNotImportantViews(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
            return true;
        }

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, com.android.server.accessibility.UiAutomationManager.LOG_TAG, printWriter)) {
                synchronized (this.mLock) {
                    printWriter.append((java.lang.CharSequence) ("Ui Automation[eventTypes=" + android.view.accessibility.AccessibilityEvent.eventTypeToString(this.mEventTypes)));
                    printWriter.append((java.lang.CharSequence) (", notificationTimeout=" + this.mNotificationTimeout));
                    printWriter.append("]");
                }
            }
        }

        public boolean setSoftKeyboardShowMode(int i) {
            return false;
        }

        public int getSoftKeyboardShowMode() {
            return 0;
        }

        public boolean switchToInputMethod(java.lang.String str) {
            return false;
        }

        public int setInputMethodEnabled(java.lang.String str, boolean z) {
            return 2;
        }

        public boolean isAccessibilityButtonAvailable() {
            return false;
        }

        public void disableSelf() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
        }

        @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
        public boolean isCapturingFingerprintGestures() {
            return false;
        }

        @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
        public void onFingerprintGestureDetectionActiveChanged(boolean z) {
        }

        @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
        public void onFingerprintGesture(int i) {
        }

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        public void takeScreenshot(int i, android.os.RemoteCallback remoteCallback) {
        }
    }
}
