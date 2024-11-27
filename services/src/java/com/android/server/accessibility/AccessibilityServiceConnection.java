package com.android.server.accessibility;

/* loaded from: classes.dex */
class AccessibilityServiceConnection extends com.android.server.accessibility.AbstractAccessibilityServiceConnection {
    private static final java.lang.String LOG_TAG = "AccessibilityServiceConnection";
    final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerService;
    private com.android.server.accessibility.BrailleDisplayConnection mBrailleDisplayConnection;
    final android.content.Intent mIntent;
    private final android.os.Handler mMainHandler;
    private java.util.List<android.os.Bundle> mTestBrailleDisplays;
    final int mUserId;
    final java.lang.ref.WeakReference<com.android.server.accessibility.AccessibilityUserState> mUserStateWeakReference;

    private static final class AccessibilityInputMethodSessionCallback extends com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback.Stub {
        private final int mUserId;

        AccessibilityInputMethodSessionCallback(int i) {
            this.mUserId = i;
        }

        public void sessionCreated(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i) {
            android.os.Trace.traceBegin(32L, "ASC.sessionCreated");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.inputmethod.InputMethodManagerInternal.get().onSessionForAccessibilityCreated(i, iAccessibilityInputMethodSession, this.mUserId);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.os.Trace.traceEnd(32L);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    AccessibilityServiceConnection(@android.annotation.Nullable com.android.server.accessibility.AccessibilityUserState accessibilityUserState, android.content.Context context, android.content.ComponentName componentName, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, android.os.Handler handler, java.lang.Object obj, com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport systemSupport, android.accessibilityservice.AccessibilityTrace accessibilityTrace, com.android.server.wm.WindowManagerInternal windowManagerInternal, com.android.server.accessibility.SystemActionPerformer systemActionPerformer, com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager, com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal) {
        super(context, componentName, accessibilityServiceInfo, i, handler, obj, accessibilitySecurityPolicy, systemSupport, accessibilityTrace, windowManagerInternal, systemActionPerformer, accessibilityWindowManager);
        this.mTestBrailleDisplays = null;
        this.mUserStateWeakReference = new java.lang.ref.WeakReference<>(accessibilityUserState);
        this.mUserId = accessibilityUserState == null ? com.android.server.am.ProcessList.INVALID_ADJ : accessibilityUserState.mUserId;
        this.mIntent = new android.content.Intent().setComponent(this.mComponentName);
        this.mMainHandler = handler;
        this.mIntent.putExtra("android.intent.extra.client_label", android.R.string.ThreeWCMmi);
        this.mActivityTaskManagerService = activityTaskManagerInternal;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mIntent.putExtra("android.intent.extra.client_intent", this.mSystemSupport.getPendingIntentActivity(this.mContext, 0, new android.content.Intent("android.settings.ACCESSIBILITY_SETTINGS"), 67108864));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void bindLocked() {
        int i;
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
        if (accessibilityUserState == null) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!accessibilityUserState.getBindInstantServiceAllowedLocked()) {
                i = 34607105;
            } else {
                i = 38801409;
            }
            if (this.mService == null && this.mContext.bindServiceAsUser(this.mIntent, this, i, new android.os.UserHandle(accessibilityUserState.mUserId))) {
                accessibilityUserState.getBindingServicesLocked().add(this.mComponentName);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mActivityTaskManagerService.setAllowAppSwitches(this.mComponentName.flattenToString(), this.mAccessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.uid, accessibilityUserState.mUserId);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void unbindLocked() {
        if (requestImeApis()) {
            this.mSystemSupport.unbindImeLocked(this);
        }
        this.mContext.unbindService(this);
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
        if (accessibilityUserState == null) {
            return;
        }
        accessibilityUserState.removeServiceLocked(this);
        this.mSystemSupport.getMagnificationProcessor().resetAllIfNeeded(this.mId);
        this.mActivityTaskManagerService.setAllowAppSwitches(this.mComponentName.flattenToString(), -1, accessibilityUserState.mUserId);
        resetLocked();
    }

    public boolean canRetrieveInteractiveWindowsLocked() {
        return this.mSecurityPolicy.canRetrieveWindowContentLocked(this) && this.mRetrieveInteractiveWindows;
    }

    public void disableSelf() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("disableSelf", "");
        }
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
                if (accessibilityUserState == null) {
                    return;
                }
                if (accessibilityUserState.getEnabledServicesLocked().remove(this.mComponentName)) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mSystemSupport.persistComponentNamesToSettingLocked("enabled_accessibility_services", accessibilityUserState.getEnabledServicesLocked(), accessibilityUserState.mUserId);
                        this.mSystemSupport.onClientChangeLocked(false);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
        if (accessibilityUserState != null) {
            com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
        }
        synchronized (this.mLock) {
            try {
                if (this.mService != iBinder) {
                    if (this.mService != null) {
                        this.mService.unlinkToDeath(this, 0);
                    }
                    this.mService = iBinder;
                    try {
                        this.mService.linkToDeath(this, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(LOG_TAG, "Failed registering death link");
                        binderDied();
                        return;
                    }
                }
                this.mServiceInterface = android.accessibilityservice.IAccessibilityServiceClient.Stub.asInterface(iBinder);
                if (accessibilityUserState == null) {
                    return;
                }
                accessibilityUserState.addServiceLocked(this);
                this.mSystemSupport.onClientChangeLocked(false);
                this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AccessibilityServiceConnection$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.accessibility.AccessibilityServiceConnection) obj).initializeService();
                    }
                }, this));
                if (requestImeApis()) {
                    this.mSystemSupport.requestImeLocked(this);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() {
        return this.mAccessibilityServiceInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void initializeService() {
        android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient;
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
                if (accessibilityUserState == null) {
                    return;
                }
                java.util.Set<android.content.ComponentName> bindingServicesLocked = accessibilityUserState.getBindingServicesLocked();
                java.util.Set<android.content.ComponentName> crashedServicesLocked = accessibilityUserState.getCrashedServicesLocked();
                if (!bindingServicesLocked.contains(this.mComponentName) && !crashedServicesLocked.contains(this.mComponentName)) {
                    iAccessibilityServiceClient = null;
                    if (iAccessibilityServiceClient == null && !accessibilityUserState.getEnabledServicesLocked().contains(this.mComponentName)) {
                        this.mSystemSupport.onClientChangeLocked(false);
                        return;
                    }
                    if (iAccessibilityServiceClient != null) {
                        binderDied();
                        return;
                    }
                    try {
                        if (svcClientTracingEnabled()) {
                            logTraceSvcClient("init", this + "," + this.mId + "," + this.mOverlayWindowTokens.get(0));
                        }
                        iAccessibilityServiceClient.init(this, this.mId, this.mOverlayWindowTokens.get(0));
                        return;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(LOG_TAG, "Error while setting connection for service: " + iAccessibilityServiceClient, e);
                        binderDied();
                        return;
                    }
                }
                bindingServicesLocked.remove(this.mComponentName);
                crashedServicesLocked.remove(this.mComponentName);
                this.mAccessibilityServiceInfo.crashed = false;
                iAccessibilityServiceClient = this.mServiceInterface;
                if (iAccessibilityServiceClient == null) {
                }
                if (iAccessibilityServiceClient != null) {
                }
            } finally {
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
        binderDied();
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
        if (accessibilityUserState != null) {
            this.mActivityTaskManagerService.setAllowAppSwitches(this.mComponentName.flattenToString(), -1, accessibilityUserState.mUserId);
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    protected boolean hasRightsToCurrentUserLocked() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 1000 || callingUid == 2000 || this.mSecurityPolicy.resolveProfileParentLocked(android.os.UserHandle.getUserId(callingUid)) == this.mSystemSupport.getCurrentUserIdLocked() || this.mSecurityPolicy.hasPermission("android.permission.INTERACT_ACROSS_USERS") || this.mSecurityPolicy.hasPermission("android.permission.INTERACT_ACROSS_USERS_FULL")) {
            return true;
        }
        return false;
    }

    public boolean setSoftKeyboardShowMode(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setSoftKeyboardShowMode", "showMode=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
                if (accessibilityUserState == null) {
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return accessibilityUserState.setSoftKeyboardModeLocked(i, this.mComponentName);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getSoftKeyboardShowMode() {
        int softKeyboardShowModeLocked;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getSoftKeyboardShowMode", "");
        }
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (accessibilityUserState == null) {
            softKeyboardShowModeLocked = 0;
        } else {
            try {
                softKeyboardShowModeLocked = accessibilityUserState.getSoftKeyboardShowModeLocked();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return softKeyboardShowModeLocked;
    }

    public boolean switchToInputMethod(java.lang.String str) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("switchToInputMethod", "imeId=" + str);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                int callingUserId = android.os.UserHandle.getCallingUserId();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return com.android.server.inputmethod.InputMethodManagerInternal.get().switchToInputMethod(str, callingUserId);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int setInputMethodEnabled(java.lang.String str, boolean z) throws java.lang.SecurityException {
        int canEnableDisableInputMethod;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("switchToInputMethod", "imeId=" + str);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return 2;
                }
                int callingUserId = android.os.UserHandle.getCallingUserId();
                com.android.server.inputmethod.InputMethodManagerInternal inputMethodManagerInternal = com.android.server.inputmethod.InputMethodManagerInternal.get();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (this.mLock) {
                        canEnableDisableInputMethod = this.mSecurityPolicy.canEnableDisableInputMethod(str, this);
                    }
                    if (canEnableDisableInputMethod != 0) {
                        return canEnableDisableInputMethod;
                    }
                    if (!inputMethodManagerInternal.setInputMethodEnabled(str, z, callingUserId)) {
                        return 2;
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return 0;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAccessibilityButtonAvailable() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("isAccessibilityButtonAvailable", "");
        }
        synchronized (this.mLock) {
            try {
                boolean z = false;
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
                    if (accessibilityUserState != null) {
                        if (isAccessibilityButtonAvailableLocked(accessibilityUserState)) {
                            z = true;
                        }
                    }
                    return z;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mLock) {
            try {
                if (isConnectedLocked()) {
                    if (requestImeApis()) {
                        this.mSystemSupport.unbindImeLocked(this);
                    }
                    this.mAccessibilityServiceInfo.crashed = true;
                    com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
                    if (accessibilityUserState != null) {
                        accessibilityUserState.serviceDisconnectedLocked(this);
                    }
                    resetLocked();
                    this.mSystemSupport.getMagnificationProcessor().resetAllIfNeeded(this.mId);
                    this.mSystemSupport.onClientChangeLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void resetLocked() {
        super.resetLocked();
        if (android.view.accessibility.Flags.brailleDisplayHid() && this.mBrailleDisplayConnection != null) {
            this.mBrailleDisplayConnection.disconnect();
        }
    }

    public boolean isAccessibilityButtonAvailableLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        return this.mRequestAccessibilityButton && this.mSystemSupport.isAccessibilityButtonShown();
    }

    @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public boolean isCapturingFingerprintGestures() {
        return this.mServiceInterface != null && this.mSecurityPolicy.canCaptureFingerprintGestures(this) && this.mCaptureFingerprintGestures;
    }

    @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public void onFingerprintGestureDetectionActiveChanged(boolean z) {
        android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient;
        if (!isCapturingFingerprintGestures()) {
            return;
        }
        synchronized (this.mLock) {
            iAccessibilityServiceClient = this.mServiceInterface;
        }
        if (iAccessibilityServiceClient != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onFingerprintCapturingGesturesChanged", java.lang.String.valueOf(z));
                }
                this.mServiceInterface.onFingerprintCapturingGesturesChanged(z);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public void onFingerprintGesture(int i) {
        android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient;
        if (!isCapturingFingerprintGestures()) {
            return;
        }
        synchronized (this.mLock) {
            iAccessibilityServiceClient = this.mServiceInterface;
        }
        if (iAccessibilityServiceClient != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onFingerprintGesture", java.lang.String.valueOf(i));
                }
                this.mServiceInterface.onFingerprintGesture(i);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void dispatchGesture(int i, android.content.pm.ParceledListSlice parceledListSlice, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mServiceInterface != null && this.mSecurityPolicy.canPerformGestures(this)) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        com.android.server.accessibility.MotionEventInjector motionEventInjectorForDisplayLocked = this.mSystemSupport.getMotionEventInjectorForDisplayLocked(i2);
                        if (wmTracingEnabled()) {
                            logTraceWM("isTouchOrFaketouchDevice", "");
                        }
                        if (motionEventInjectorForDisplayLocked != null && this.mWindowManagerService.isTouchOrFaketouchDevice()) {
                            motionEventInjectorForDisplayLocked.injectEvents(parceledListSlice.getList(), this.mServiceInterface, i, i2);
                        } else {
                            try {
                                if (svcClientTracingEnabled()) {
                                    logTraceSvcClient("onPerformGestureResult", i + ", false");
                                }
                                this.mServiceInterface.onPerformGestureResult(i, false);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.e(LOG_TAG, "Error sending motion event injection failure to " + this.mServiceInterface, e);
                            }
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setFocusAppearance(int i, int i2) {
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStateWeakReference.get();
        if (accessibilityUserState == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (hasRightsToCurrentUserLocked()) {
                    if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                        if (accessibilityUserState.getFocusStrokeWidthLocked() == i && accessibilityUserState.getFocusColorLocked() == i2) {
                            return;
                        }
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            accessibilityUserState.setFocusAppearanceLocked(i, i2);
                            this.mSystemSupport.onClientChangeLocked(false);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyMotionEvent(android.view.MotionEvent motionEvent) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityServiceConnection$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityServiceConnection) obj).notifyMotionEventInternal((android.view.MotionEvent) obj2);
            }
        }, this, motionEvent));
    }

    public void notifyTouchState(int i, int i2) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityServiceConnection$$ExternalSyntheticLambda2
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityServiceConnection) obj).notifyTouchStateInternal(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
            }
        }, this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
    }

    public boolean requestImeApis() {
        return this.mRequestImeApis;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    protected void createImeSessionInternal() {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("createImeSession", "");
                }
                serviceInterfaceSafely.createImeSession(new com.android.server.accessibility.AccessibilityServiceConnection.AccessibilityInputMethodSessionCallback(this.mUserId));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error requesting IME session from " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyMotionEventInternal(android.view.MotionEvent motionEvent) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (this.mTrace.isA11yTracingEnabled()) {
                    logTraceSvcClient(".onMotionEvent ", motionEvent.toString());
                }
                serviceInterfaceSafely.onMotionEvent(motionEvent);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending motion event to" + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTouchStateInternal(int i, int i2) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (this.mTrace.isA11yTracingEnabled()) {
                    logTraceSvcClient(".onTouchStateChanged ", android.accessibilityservice.TouchInteractionController.stateToString(i2));
                }
                serviceInterfaceSafely.onTouchStateChanged(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending motion event to" + this.mService, e);
            }
        }
    }

    private void checkAccessibilityAccessLocked() {
        if (!hasRightsToCurrentUserLocked() || !this.mSecurityPolicy.checkAccessibilityAccess(this)) {
            throw new java.lang.SecurityException("Caller does not have accessibility access");
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @android.annotation.RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    public void connectBluetoothBrailleDisplay(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) {
        if (!android.view.accessibility.Flags.brailleDisplayHid()) {
            throw new java.lang.IllegalStateException("Flag BRAILLE_DISPLAY_HID not enabled");
        }
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iBrailleDisplayController);
        this.mContext.enforceCallingPermission("android.permission.BLUETOOTH_CONNECT", "Missing BLUETOOTH_CONNECT permission");
        if (!android.bluetooth.BluetoothAdapter.checkBluetoothAddress(str)) {
            throw new java.lang.IllegalArgumentException(str + " is not a valid Bluetooth address");
        }
        synchronized (this.mLock) {
            try {
                checkAccessibilityAccessLocked();
                if (this.mBrailleDisplayConnection != null) {
                    throw new java.lang.IllegalStateException("This service already has a connected Braille display");
                }
                com.android.server.accessibility.BrailleDisplayConnection brailleDisplayConnection = new com.android.server.accessibility.BrailleDisplayConnection(this.mLock, this);
                if (this.mTestBrailleDisplays != null) {
                    brailleDisplayConnection.setTestData(this.mTestBrailleDisplays);
                }
                brailleDisplayConnection.connectLocked(str, 5, iBrailleDisplayController);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @android.annotation.NonNull
    @android.annotation.SuppressLint({"MissingPermission"})
    public void connectUsbBrailleDisplay(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, @android.annotation.NonNull android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) {
        if (!android.view.accessibility.Flags.brailleDisplayHid()) {
            throw new java.lang.IllegalStateException("Flag BRAILLE_DISPLAY_HID not enabled");
        }
        java.util.Objects.requireNonNull(usbDevice);
        java.util.Objects.requireNonNull(iBrailleDisplayController);
        android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager) this.mContext.getSystemService("usb");
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (usbManager != null) {
            try {
                if (usbManager.hasPermission(usbDevice, this.mComponentName.getPackageName(), callingPid, callingUid)) {
                    java.lang.String serialNumber = usbDevice.getSerialNumber();
                    if (android.text.TextUtils.isEmpty(serialNumber)) {
                        try {
                            iBrailleDisplayController.onConnectionFailed(2);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(LOG_TAG, "Error calling onConnectionFailed", e);
                        }
                        return;
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    synchronized (this.mLock) {
                        try {
                            checkAccessibilityAccessLocked();
                            if (this.mBrailleDisplayConnection != null) {
                                throw new java.lang.IllegalStateException("This service already has a connected Braille display");
                            }
                            com.android.server.accessibility.BrailleDisplayConnection brailleDisplayConnection = new com.android.server.accessibility.BrailleDisplayConnection(this.mLock, this);
                            if (this.mTestBrailleDisplays != null) {
                                brailleDisplayConnection.setTestData(this.mTestBrailleDisplays);
                            }
                            brailleDisplayConnection.connectLocked(serialNumber, 3, iBrailleDisplayController);
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    return;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        throw new java.lang.SecurityException("Caller does not have permission to access this UsbDevice");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @android.annotation.RequiresPermission("android.permission.MANAGE_ACCESSIBILITY")
    public void setTestBrailleDisplayData(java.util.List<android.os.Bundle> list) {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_ACCESSIBILITY", "Missing MANAGE_ACCESSIBILITY permission");
        this.mTestBrailleDisplays = list;
    }

    void onBrailleDisplayConnectedLocked(com.android.server.accessibility.BrailleDisplayConnection brailleDisplayConnection) {
        this.mBrailleDisplayConnection = brailleDisplayConnection;
    }

    void onBrailleDisplayDisconnectedLocked() {
        this.mBrailleDisplayConnection = null;
    }
}
