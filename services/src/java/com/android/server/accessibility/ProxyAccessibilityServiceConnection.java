package com.android.server.accessibility;

/* loaded from: classes.dex */
public class ProxyAccessibilityServiceConnection extends com.android.server.accessibility.AccessibilityServiceConnection {
    private static final java.lang.String LOG_TAG = "ProxyAccessibilityServiceConnection";
    private int mDeviceId;
    private int mDisplayId;
    private int mFocusColor;
    private int mFocusStrokeWidth;
    private java.util.List<android.accessibilityservice.AccessibilityServiceInfo> mInstalledAndEnabledServices;
    private int mInteractiveTimeout;
    private int mNonInteractiveTimeout;

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        super.attachAccessibilityOverlayToDisplay(i, i2, surfaceControl, iAccessibilityInteractionConnectionCallback);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void attachAccessibilityOverlayToWindow(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        super.attachAccessibilityOverlayToWindow(i, i2, surfaceControl, iAccessibilityInteractionConnectionCallback);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void bindInputLocked() {
        super.bindInputLocked();
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void bindLocked() {
        super.bindLocked();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean canReceiveEventsLocked() {
        return super.canReceiveEventsLocked();
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean canRetrieveInteractiveWindowsLocked() {
        return super.canRetrieveInteractiveWindowsLocked();
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @android.annotation.RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    public /* bridge */ /* synthetic */ void connectBluetoothBrailleDisplay(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) {
        super.connectBluetoothBrailleDisplay(str, iBrailleDisplayController);
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @android.annotation.NonNull
    @android.annotation.SuppressLint({"MissingPermission"})
    public /* bridge */ /* synthetic */ void connectUsbBrailleDisplay(@android.annotation.NonNull android.hardware.usb.UsbDevice usbDevice, @android.annotation.NonNull android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) {
        super.connectUsbBrailleDisplay(usbDevice, iBrailleDisplayController);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void createImeSessionLocked() {
        super.createImeSessionLocked();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ java.lang.String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, android.os.Bundle bundle) throws android.os.RemoteException {
        return super.findAccessibilityNodeInfoByAccessibilityId(i, j, i2, iAccessibilityInteractionConnectionCallback, i3, j2, bundle);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ java.lang.String[] findAccessibilityNodeInfosByText(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        return super.findAccessibilityNodeInfosByText(i, j, str, i2, iAccessibilityInteractionConnectionCallback, j2);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ java.lang.String[] findAccessibilityNodeInfosByViewId(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        return super.findAccessibilityNodeInfosByViewId(i, j, str, i2, iAccessibilityInteractionConnectionCallback, j2);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ java.lang.String[] findFocus(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        return super.findFocus(i, j, i2, i3, iAccessibilityInteractionConnectionCallback, j2);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ java.lang.String[] focusSearch(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        return super.focusSearch(i, j, i2, i3, iAccessibilityInteractionConnectionCallback, j2);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ int getCapabilities() {
        return super.getCapabilities();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ android.content.ComponentName getComponentName() {
        return super.getComponentName();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ android.os.IBinder getOverlayWindowToken(int i) {
        return super.getOverlayWindowToken(i);
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() {
        return super.getServiceInfo();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ android.view.accessibility.AccessibilityWindowInfo getWindow(int i) {
        return super.getWindow(i);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ int getWindowIdForLeashToken(@android.annotation.NonNull android.os.IBinder iBinder) {
        return super.getWindowIdForLeashToken(iBinder);
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean isAccessibilityButtonAvailableLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        return super.isAccessibilityButtonAvailableLocked(accessibilityUserState);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean isConnectedLocked() {
        return super.isConnectedLocked();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean isMultiFingerGesturesEnabled() {
        return super.isMultiFingerGesturesEnabled();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean isSendMotionEventsEnabled() {
        return super.isSendMotionEventsEnabled();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean isServiceDetectsGesturesEnabled(int i) {
        return super.isServiceDetectsGesturesEnabled(i);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean isServiceHandlesDoubleTapEnabled() {
        return super.isServiceHandlesDoubleTapEnabled();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean isTwoFingerPassthroughEnabled() {
        return super.isTwoFingerPassthroughEnabled();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void logTrace(long j, java.lang.String str, long j2, java.lang.String str2, int i, long j3, int i2, android.os.Bundle bundle) {
        super.logTrace(j, str, j2, str2, i, j3, i2, bundle);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyAccessibilityButtonAvailabilityChangedLocked(boolean z) {
        super.notifyAccessibilityButtonAvailabilityChangedLocked(z);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyAccessibilityButtonClickedLocked(int i) {
        super.notifyAccessibilityButtonClickedLocked(i);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.notifyAccessibilityEvent(accessibilityEvent);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyClearAccessibilityNodeInfoCache() {
        super.notifyClearAccessibilityNodeInfoCache();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
        super.notifyGesture(accessibilityGestureEvent);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyMagnificationChangedLocked(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
        super.notifyMagnificationChangedLocked(i, region, magnificationConfig);
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyMotionEvent(android.view.MotionEvent motionEvent) {
        super.notifyMotionEvent(motionEvent);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifySoftKeyboardShowModeChangedLocked(int i) {
        super.notifySoftKeyboardShowModeChangedLocked(i);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifySystemActionsChangedLocked() {
        super.notifySystemActionsChangedLocked();
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void notifyTouchState(int i, int i2) {
        super.notifyTouchState(i, i2);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void onDisplayRemoved(int i) {
        super.onDisplayRemoved(i);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void onRemoved() {
        super.onRemoved();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean performAccessibilityAction(int i, long j, int i2, android.os.Bundle bundle, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        return super.performAccessibilityAction(i, j, i2, bundle, i3, iAccessibilityInteractionConnectionCallback, j2);
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean requestImeApis() {
        return super.requestImeApis();
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void resetLocked() {
        super.resetLocked();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void setAttributionTag(java.lang.String str) {
        super.setAttributionTag(str);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void setCacheEnabled(boolean z) {
        super.setCacheEnabled(z);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void setDynamicallyConfigurableProperties(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        super.setDynamicallyConfigurableProperties(accessibilityServiceInfo);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void setImeSessionEnabledLocked(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) {
        super.setImeSessionEnabledLocked(iAccessibilityInputMethodSession, z);
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @android.annotation.RequiresPermission("android.permission.MANAGE_ACCESSIBILITY")
    public /* bridge */ /* synthetic */ void setTestBrailleDisplayData(java.util.List list) {
        super.setTestBrailleDisplayData(list);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void startInputLocked(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        super.startInputLocked(iRemoteAccessibilityInputConnection, editorInfo, z);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void takeScreenshotOfWindow(int i, int i2, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        super.takeScreenshotOfWindow(i, i2, screenCaptureListener, iAccessibilityInteractionConnectionCallback);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void unbindInputLocked() {
        super.unbindInputLocked();
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public /* bridge */ /* synthetic */ void unbindLocked() {
        super.unbindLocked();
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public /* bridge */ /* synthetic */ boolean wantsGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return super.wantsGenericMotionEvent(motionEvent);
    }

    ProxyAccessibilityServiceConnection(android.content.Context context, android.content.ComponentName componentName, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, android.os.Handler handler, java.lang.Object obj, com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport systemSupport, android.accessibilityservice.AccessibilityTrace accessibilityTrace, com.android.server.wm.WindowManagerInternal windowManagerInternal, com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager, int i2, int i3) {
        super(null, context, componentName, accessibilityServiceInfo, i, handler, obj, accessibilitySecurityPolicy, systemSupport, accessibilityTrace, windowManagerInternal, null, accessibilityWindowManager, null);
        this.mDisplayId = i2;
        setDisplayTypes(2);
        this.mFocusStrokeWidth = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.accessibility_focus_highlight_stroke_width);
        this.mFocusColor = this.mContext.getResources().getColor(android.R.color.accent_device_default_dark);
        this.mDeviceId = i3;
    }

    int getDisplayId() {
        return this.mDisplayId;
    }

    int getDeviceId() {
        return this.mDeviceId;
    }

    void initializeServiceInterface(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient) throws android.os.RemoteException {
        this.mServiceInterface = iAccessibilityServiceClient;
        this.mService = iAccessibilityServiceClient.asBinder();
        this.mServiceInterface.init(this, this.mId, this.mOverlayWindowTokens.get(this.mDisplayId));
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setInstalledAndEnabledServices(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    this.mInstalledAndEnabledServices = list;
                    android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = this.mAccessibilityServiceInfo;
                    accessibilityServiceInfo.flags = 0;
                    accessibilityServiceInfo.eventTypes = 0;
                    accessibilityServiceInfo.notificationTimeout = 0L;
                    java.util.HashSet hashSet = new java.util.HashSet();
                    boolean z = false;
                    int i = 0;
                    int i2 = 0;
                    boolean z2 = false;
                    for (android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo2 : list) {
                        z |= accessibilityServiceInfo2.isAccessibilityTool();
                        if (accessibilityServiceInfo2.packageNames == null || accessibilityServiceInfo2.packageNames.length == 0) {
                            z2 = true;
                        } else if (!z2) {
                            hashSet.addAll(java.util.Arrays.asList(accessibilityServiceInfo2.packageNames));
                        }
                        i = java.lang.Math.max(i, accessibilityServiceInfo2.getInteractiveUiTimeoutMillis());
                        i2 = java.lang.Math.max(i2, accessibilityServiceInfo2.getNonInteractiveUiTimeoutMillis());
                        accessibilityServiceInfo.notificationTimeout = java.lang.Math.max(accessibilityServiceInfo.notificationTimeout, accessibilityServiceInfo2.notificationTimeout);
                        accessibilityServiceInfo.eventTypes |= accessibilityServiceInfo2.eventTypes;
                        accessibilityServiceInfo.feedbackType |= accessibilityServiceInfo2.feedbackType;
                        accessibilityServiceInfo.flags |= accessibilityServiceInfo2.flags;
                        setDefaultPropertiesIfNullLocked(accessibilityServiceInfo2);
                        hashSet = hashSet;
                    }
                    java.util.HashSet hashSet2 = hashSet;
                    accessibilityServiceInfo.setAccessibilityTool(z);
                    accessibilityServiceInfo.setInteractiveUiTimeoutMillis(i);
                    accessibilityServiceInfo.setNonInteractiveUiTimeoutMillis(i2);
                    this.mInteractiveTimeout = i;
                    this.mNonInteractiveTimeout = i2;
                    if (z2) {
                        accessibilityServiceInfo.packageNames = null;
                    } else {
                        accessibilityServiceInfo.packageNames = (java.lang.String[]) hashSet2.toArray(new java.lang.String[0]);
                    }
                    setDynamicallyConfigurableProperties(accessibilityServiceInfo);
                    this.mSystemSupport.onProxyChanged(this.mDeviceId);
                } finally {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void setDefaultPropertiesIfNullLocked(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        java.lang.String str = "ProxyClass" + this.mDisplayId;
        if (accessibilityServiceInfo.getResolveInfo() == null) {
            android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
            android.content.pm.ServiceInfo serviceInfo = new android.content.pm.ServiceInfo();
            android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
            serviceInfo.packageName = "ProxyPackage";
            serviceInfo.name = str;
            applicationInfo.processName = "ProxyPackage";
            applicationInfo.className = str;
            resolveInfo.serviceInfo = serviceInfo;
            serviceInfo.applicationInfo = applicationInfo;
            accessibilityServiceInfo.setResolveInfo(resolveInfo);
        }
        if (accessibilityServiceInfo.getComponentName() == null) {
            accessibilityServiceInfo.setComponentName(new android.content.ComponentName("ProxyPackage", str));
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAndEnabledServices() {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list;
        synchronized (this.mLock) {
            list = this.mInstalledAndEnabledServices;
        }
        return list;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray getWindows() {
        android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray windows = super.getWindows();
        android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray windowListSparseArray = new android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray();
        windowListSparseArray.put(this.mDisplayId, (java.util.List) windows.get(this.mDisplayId, java.util.Collections.emptyList()));
        return windowListSparseArray;
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setFocusAppearance(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (hasRightsToCurrentUserLocked()) {
                    if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                        if (getFocusStrokeWidthLocked() == i && getFocusColorLocked() == i2) {
                            return;
                        }
                        this.mFocusStrokeWidth = i;
                        this.mFocusColor = i2;
                        this.mSystemSupport.onProxyChanged(this.mDeviceId);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getFocusStrokeWidthLocked() {
        return this.mFocusStrokeWidth;
    }

    public int getFocusColorLocked() {
        return this.mFocusColor;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    int resolveAccessibilityWindowIdForFindFocusLocked(int i, int i2) {
        if (i == -2) {
            int focusedWindowId = this.mA11yWindowManager.getFocusedWindowId(i2, this.mDisplayId);
            if (!this.mA11yWindowManager.windowIdBelongsToDisplayType(focusedWindowId, this.mDisplayTypes)) {
                return -1;
            }
            return focusedWindowId;
        }
        return i;
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, android.os.IBinder.DeathRecipient
    public void binderDied() {
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    protected boolean supportsFlagForNotImportantViews(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        return true;
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    protected boolean hasRightsToCurrentUserLocked() {
        return true;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection, com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter
    public boolean onKeyEvent(android.view.KeyEvent keyEvent, int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("onKeyEvent is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public boolean isCapturingFingerprintGestures() throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("isCapturingFingerprintGestures is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public void onFingerprintGestureDetectionActiveChanged(boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("onFingerprintGestureDetectionActiveChanged is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
    public void onFingerprintGesture(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("onFingerprintGesture is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public boolean isFingerprintGestureDetectionAvailable() throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("isFingerprintGestureDetectionAvailable is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("onServiceConnected is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("onServiceDisconnected is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setServiceInfo is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public void disableSelf() throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("disableSelf is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public boolean performGlobalAction(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("performGlobalAction is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setOnKeyEventResult(boolean z, int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setOnKeyEventResult is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @android.annotation.NonNull
    public java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getSystemActions is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    @androidx.annotation.Nullable
    public android.accessibilityservice.MagnificationConfig getMagnificationConfig(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getMagnificationConfig is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public float getMagnificationScale(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getMagnificationScale is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public float getMagnificationCenterX(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getMagnificationCenterX is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public float getMagnificationCenterY(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getMagnificationCenterY is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public android.graphics.Region getMagnificationRegion(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getMagnificationRegion is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public android.graphics.Region getCurrentMagnificationRegion(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getCurrentMagnificationRegion is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public boolean resetMagnification(int i, boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("resetMagnification is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public boolean resetCurrentMagnification(int i, boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("resetCurrentMagnification is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public boolean setMagnificationConfig(int i, @androidx.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setMagnificationConfig is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setMagnificationCallbackEnabled(int i, boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setMagnificationCallbackEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public boolean isMagnificationCallbackEnabled(int i) {
        throw new java.lang.UnsupportedOperationException("isMagnificationCallbackEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public boolean setSoftKeyboardShowMode(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setSoftKeyboardShowMode is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public int getSoftKeyboardShowMode() throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("getSoftKeyboardShowMode is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setSoftKeyboardCallbackEnabled(boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setSoftKeyboardCallbackEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public boolean switchToInputMethod(java.lang.String str) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("switchToInputMethod is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public int setInputMethodEnabled(java.lang.String str, boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setInputMethodEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection
    public boolean isAccessibilityButtonAvailable() throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("isAccessibilityButtonAvailable is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void sendGesture(int i, android.content.pm.ParceledListSlice parceledListSlice) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("sendGesture is not supported");
    }

    @Override // com.android.server.accessibility.AccessibilityServiceConnection, com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void dispatchGesture(int i, android.content.pm.ParceledListSlice parceledListSlice, int i2) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("dispatchGesture is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void takeScreenshot(int i, android.os.RemoteCallback remoteCallback) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("takeScreenshot is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setGestureDetectionPassthroughRegion is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setTouchExplorationPassthroughRegion is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setServiceDetectsGesturesEnabled(int i, boolean z) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setServiceDetectsGesturesEnabled is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void requestTouchExploration(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("requestTouchExploration is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void requestDragging(int i, int i2) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("requestDragging is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void requestDelegating(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("requestDelegating is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void onDoubleTap(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("onDoubleTap is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void onDoubleTapAndHold(int i) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("onDoubleTapAndHold is not supported");
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void setAnimationScale(float f) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("setAnimationScale is not supported");
    }

    public int getInteractiveTimeout() {
        return this.mInteractiveTimeout;
    }

    public int getNonInteractiveTimeout() {
        return this.mNonInteractiveTimeout;
    }

    public boolean updateTimeouts(int i, int i2) {
        boolean z;
        if (i2 == 0) {
            i2 = this.mAccessibilityServiceInfo.getInteractiveUiTimeoutMillis();
        }
        if (i == 0) {
            i = this.mAccessibilityServiceInfo.getNonInteractiveUiTimeoutMillis();
        }
        if (this.mInteractiveTimeout == i2) {
            z = false;
        } else {
            this.mInteractiveTimeout = i2;
            z = true;
        }
        if (this.mNonInteractiveTimeout == i) {
            return z;
        }
        this.mNonInteractiveTimeout = i;
        return true;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, LOG_TAG, printWriter)) {
            synchronized (this.mLock) {
                printWriter.append((java.lang.CharSequence) ("Proxy[displayId=" + this.mDisplayId));
                printWriter.append((java.lang.CharSequence) (", deviceId=" + this.mDeviceId));
                printWriter.append((java.lang.CharSequence) (", feedbackType" + android.accessibilityservice.AccessibilityServiceInfo.feedbackTypeToString(this.mFeedbackType)));
                printWriter.append((java.lang.CharSequence) (", capabilities=" + this.mAccessibilityServiceInfo.getCapabilities()));
                printWriter.append((java.lang.CharSequence) (", eventTypes=" + android.view.accessibility.AccessibilityEvent.eventTypeToString(this.mEventTypes)));
                printWriter.append((java.lang.CharSequence) (", notificationTimeout=" + this.mNotificationTimeout));
                printWriter.append(", nonInteractiveUiTimeout=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mNonInteractiveTimeout));
                printWriter.append(", interactiveUiTimeout=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mInteractiveTimeout));
                printWriter.append(", focusStrokeWidth=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mFocusStrokeWidth));
                printWriter.append(", focusColor=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mFocusColor));
                printWriter.append(", installedAndEnabledServiceCount=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mInstalledAndEnabledServices.size()));
                printWriter.append(", installedAndEnabledServices=").append((java.lang.CharSequence) this.mInstalledAndEnabledServices.toString());
                printWriter.append("]");
            }
        }
    }
}
