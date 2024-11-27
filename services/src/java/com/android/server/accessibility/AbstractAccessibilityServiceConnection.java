package com.android.server.accessibility;

/* loaded from: classes.dex */
abstract class AbstractAccessibilityServiceConnection extends android.accessibilityservice.IAccessibilityServiceConnection.Stub implements android.content.ServiceConnection, android.os.IBinder.DeathRecipient, com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter, com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient {
    private static final boolean DEBUG = false;
    public static final int DISPLAY_TYPE_DEFAULT = 1;
    public static final int DISPLAY_TYPE_PROXY = 2;
    private static final java.lang.String LOG_TAG = "AbstractAccessibilityServiceConnection";
    protected static final java.lang.String TAKE_SCREENSHOT = "takeScreenshot";
    private static final java.lang.String TRACE_SVC_CLIENT = "AbstractAccessibilityServiceConnection.IAccessibilityServiceClient";
    private static final java.lang.String TRACE_SVC_CONN = "AbstractAccessibilityServiceConnection.IAccessibilityServiceConnection";
    private static final java.lang.String TRACE_WM = "WindowManagerInternal";
    private static final int WAIT_WINDOWS_TIMEOUT_MILLIS = 5000;
    final com.android.server.accessibility.AccessibilityWindowManager mA11yWindowManager;
    protected final android.accessibilityservice.AccessibilityServiceInfo mAccessibilityServiceInfo;
    protected java.lang.String mAttributionTag;
    boolean mCaptureFingerprintGestures;
    final android.content.ComponentName mComponentName;
    protected final android.content.Context mContext;
    private final android.hardware.display.DisplayManager mDisplayManager;
    public android.os.Handler mEventDispatchHandler;
    int mEventTypes;
    int mFeedbackType;
    int mFetchFlags;
    int mGenericMotionEventSources;
    final int mId;
    public final com.android.server.accessibility.AbstractAccessibilityServiceConnection.InvocationHandler mInvocationHandler;
    boolean mIsDefault;
    boolean mLastAccessibilityButtonCallbackState;
    protected final java.lang.Object mLock;
    private final android.os.Handler mMainHandler;
    long mNotificationTimeout;
    int mObservedMotionEventSources;
    private final android.os.PowerManager mPowerManager;
    boolean mReceivedAccessibilityButtonCallbackSinceBind;
    boolean mRequestAccessibilityButton;
    boolean mRequestFilterKeyEvents;
    boolean mRequestImeApis;
    private boolean mRequestMultiFingerGestures;
    private long mRequestTakeScreenshotTimestampMs;
    boolean mRequestTouchExplorationMode;
    private boolean mRequestTwoFingerPassthrough;
    boolean mRetrieveInteractiveWindows;
    protected final com.android.server.accessibility.AccessibilitySecurityPolicy mSecurityPolicy;
    private boolean mSendMotionEvents;
    android.os.IBinder mService;
    private boolean mServiceHandlesDoubleTap;
    android.accessibilityservice.IAccessibilityServiceClient mServiceInterface;
    private final com.android.server.accessibility.SystemActionPerformer mSystemActionPerformer;
    protected final com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport mSystemSupport;
    protected final android.accessibilityservice.AccessibilityTrace mTrace;
    protected final com.android.server.wm.WindowManagerInternal mWindowManagerService;
    protected int mDisplayTypes = 1;
    java.util.Set<java.lang.String> mPackageNames = new java.util.HashSet();
    private android.util.SparseArray<java.lang.Boolean> mServiceDetectsGestures = new android.util.SparseArray<>(0);
    final android.util.SparseArray<android.view.accessibility.AccessibilityEvent> mPendingEvents = new android.util.SparseArray<>();
    boolean mUsesAccessibilityCache = false;
    final android.util.SparseArray<android.os.IBinder> mOverlayWindowTokens = new android.util.SparseArray<>();
    private java.util.List<android.view.SurfaceControl> mOverlays = new java.util.ArrayList();
    private android.util.SparseArray<java.lang.Long> mRequestTakeScreenshotOfWindowTimestampMs = new android.util.SparseArray<>();
    private final com.android.internal.compat.IPlatformCompat mIPlatformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayTypes {
    }

    public interface SystemSupport {
        void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback);

        int getCurrentUserIdLocked();

        @android.annotation.Nullable
        com.android.server.accessibility.FingerprintGestureDispatcher getFingerprintGestureDispatcher();

        @android.annotation.NonNull
        com.android.server.accessibility.KeyEventDispatcher getKeyEventDispatcher();

        @android.annotation.NonNull
        com.android.server.accessibility.magnification.MagnificationProcessor getMagnificationProcessor();

        @android.annotation.Nullable
        com.android.server.accessibility.MotionEventInjector getMotionEventInjectorForDisplayLocked(int i);

        android.app.PendingIntent getPendingIntentActivity(android.content.Context context, int i, android.content.Intent intent, int i2);

        android.util.Pair<float[], android.view.MagnificationSpec> getWindowTransformationMatrixAndMagnificationSpec(int i);

        boolean isAccessibilityButtonShown();

        void onClientChangeLocked(boolean z);

        void onDoubleTap(int i);

        void onDoubleTapAndHold(int i);

        void onProxyChanged(int i);

        void persistComponentNamesToSettingLocked(java.lang.String str, java.util.Set<android.content.ComponentName> set, int i);

        void requestDelegating(int i);

        void requestDragging(int i, int i2);

        void requestImeLocked(com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection);

        void requestTouchExploration(int i);

        void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region);

        void setServiceDetectsGesturesEnabled(int i, boolean z);

        void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region);

        void unbindImeLocked(com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection);
    }

    protected abstract boolean hasRightsToCurrentUserLocked();

    public AbstractAccessibilityServiceConnection(android.content.Context context, android.content.ComponentName componentName, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, android.os.Handler handler, java.lang.Object obj, com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport systemSupport, android.accessibilityservice.AccessibilityTrace accessibilityTrace, com.android.server.wm.WindowManagerInternal windowManagerInternal, com.android.server.accessibility.SystemActionPerformer systemActionPerformer, com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager) {
        this.mContext = context;
        this.mWindowManagerService = windowManagerInternal;
        this.mId = i;
        this.mComponentName = componentName;
        this.mAccessibilityServiceInfo = accessibilityServiceInfo;
        this.mLock = obj;
        this.mSecurityPolicy = accessibilitySecurityPolicy;
        this.mSystemActionPerformer = systemActionPerformer;
        this.mSystemSupport = systemSupport;
        this.mTrace = accessibilityTrace;
        this.mMainHandler = handler;
        this.mInvocationHandler = new com.android.server.accessibility.AbstractAccessibilityServiceConnection.InvocationHandler(handler.getLooper());
        this.mA11yWindowManager = accessibilityWindowManager;
        this.mDisplayManager = (android.hardware.display.DisplayManager) context.getSystemService("display");
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService("power");
        this.mEventDispatchHandler = new android.os.Handler(handler.getLooper()) { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifyAccessibilityEventInternal(message.what, (android.view.accessibility.AccessibilityEvent) message.obj, message.arg1 != 0);
            }
        };
        setDynamicallyConfigurableProperties(accessibilityServiceInfo);
    }

    @Override // com.android.server.accessibility.KeyEventDispatcher.KeyEventFilter
    public boolean onKeyEvent(android.view.KeyEvent keyEvent, int i) {
        if (!this.mRequestFilterKeyEvents || this.mServiceInterface == null || (this.mAccessibilityServiceInfo.getCapabilities() & 8) == 0 || !this.mSecurityPolicy.checkAccessibilityAccess(this)) {
            return false;
        }
        try {
            if (svcClientTracingEnabled()) {
                logTraceSvcClient("onKeyEvent", keyEvent + ", " + i);
            }
            this.mServiceInterface.onKeyEvent(keyEvent, i);
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void setDynamicallyConfigurableProperties(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        this.mEventTypes = accessibilityServiceInfo.eventTypes;
        this.mFeedbackType = accessibilityServiceInfo.feedbackType;
        java.lang.String[] strArr = accessibilityServiceInfo.packageNames;
        this.mPackageNames.clear();
        if (strArr != null) {
            this.mPackageNames.addAll(java.util.Arrays.asList(strArr));
        }
        this.mNotificationTimeout = accessibilityServiceInfo.notificationTimeout;
        this.mIsDefault = (accessibilityServiceInfo.flags & 1) != 0;
        this.mGenericMotionEventSources = accessibilityServiceInfo.getMotionEventSources();
        if (android.view.accessibility.Flags.motionEventObserving()) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESSIBILITY_MOTION_EVENT_OBSERVING") == 0) {
                this.mObservedMotionEventSources = accessibilityServiceInfo.getObservedMotionEventSources();
            } else {
                android.util.Slog.e(LOG_TAG, "Observing motion events requires android.Manifest.permission.ACCESSIBILITY_MOTION_EVENT_OBSERVING.");
                this.mObservedMotionEventSources = 0;
            }
        }
        if (supportsFlagForNotImportantViews(accessibilityServiceInfo)) {
            if ((accessibilityServiceInfo.flags & 2) != 0) {
                this.mFetchFlags |= 128;
            } else {
                this.mFetchFlags &= -129;
            }
        }
        if ((accessibilityServiceInfo.flags & 16) != 0) {
            this.mFetchFlags |= 256;
        } else {
            this.mFetchFlags &= -257;
        }
        if (this.mAccessibilityServiceInfo.isAccessibilityTool()) {
            this.mFetchFlags |= 512;
        } else {
            this.mFetchFlags &= -513;
        }
        this.mRequestTouchExplorationMode = (accessibilityServiceInfo.flags & 4) != 0;
        this.mServiceHandlesDoubleTap = (accessibilityServiceInfo.flags & 2048) != 0;
        this.mRequestMultiFingerGestures = (accessibilityServiceInfo.flags & 4096) != 0;
        this.mRequestTwoFingerPassthrough = (accessibilityServiceInfo.flags & 8192) != 0;
        this.mSendMotionEvents = (accessibilityServiceInfo.flags & 16384) != 0;
        this.mRequestFilterKeyEvents = (accessibilityServiceInfo.flags & 32) != 0;
        this.mRetrieveInteractiveWindows = (accessibilityServiceInfo.flags & 64) != 0;
        this.mCaptureFingerprintGestures = (accessibilityServiceInfo.flags & 512) != 0;
        this.mRequestAccessibilityButton = (accessibilityServiceInfo.flags & 256) != 0;
        this.mRequestImeApis = (accessibilityServiceInfo.flags & 32768) != 0;
    }

    protected boolean supportsFlagForNotImportantViews(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion >= 16;
    }

    public boolean canReceiveEventsLocked() {
        return (this.mEventTypes == 0 || this.mService == null) ? false : true;
    }

    public void setOnKeyEventResult(boolean z, int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setOnKeyEventResult", "handled=" + z + ";sequence=" + i);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.getKeyEventDispatcher().setOnKeyEventResult(this, z, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() {
        android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getServiceInfo", "");
        }
        synchronized (this.mLock) {
            accessibilityServiceInfo = this.mAccessibilityServiceInfo;
        }
        return accessibilityServiceInfo;
    }

    public int getCapabilities() {
        return this.mAccessibilityServiceInfo.getCapabilities();
    }

    int getRelevantEventTypes() {
        return (this.mUsesAccessibilityCache ? 4307005 : 32) | this.mEventTypes;
    }

    public void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setServiceInfo", "info=" + accessibilityServiceInfo);
        }
        if (!accessibilityServiceInfo.isWithinParcelableSize()) {
            throw new java.lang.IllegalStateException("Cannot update service info: size is larger than safe parcelable limits.");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    boolean z = this.mRequestImeApis;
                    android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo2 = this.mAccessibilityServiceInfo;
                    if (accessibilityServiceInfo2 != null) {
                        accessibilityServiceInfo2.updateDynamicallyConfigurableProperties(this.mIPlatformCompat, accessibilityServiceInfo);
                        setDynamicallyConfigurableProperties(accessibilityServiceInfo2);
                    } else {
                        setDynamicallyConfigurableProperties(accessibilityServiceInfo);
                    }
                    this.mSystemSupport.onClientChangeLocked(true);
                    if (!z && this.mRequestImeApis) {
                        this.mSystemSupport.requestImeLocked(this);
                    } else if (z && !this.mRequestImeApis) {
                        this.mSystemSupport.unbindImeLocked(this);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setInstalledAndEnabledServices(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) {
    }

    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAndEnabledServices() {
        return null;
    }

    public void setAttributionTag(java.lang.String str) {
        this.mAttributionTag = str;
    }

    java.lang.String getAttributionTag() {
        return this.mAttributionTag;
    }

    @android.annotation.Nullable
    public android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray getWindows() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getWindows", "");
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                if (!this.mSecurityPolicy.canRetrieveWindowsLocked(this)) {
                    return null;
                }
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray windowListSparseArray = new android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray();
                    java.util.ArrayList<java.lang.Integer> displayListLocked = this.mA11yWindowManager.getDisplayListLocked(this.mDisplayTypes);
                    int size = displayListLocked.size();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            int intValue = displayListLocked.get(i).intValue();
                            ensureWindowsAvailableTimedLocked(intValue);
                            java.util.List<android.view.accessibility.AccessibilityWindowInfo> windowsByDisplayLocked = getWindowsByDisplayLocked(intValue);
                            if (windowsByDisplayLocked != null) {
                                windowListSparseArray.put(intValue, windowsByDisplayLocked);
                            }
                        }
                    }
                    return windowListSparseArray;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void setDisplayTypes(int i) {
        this.mDisplayTypes = i;
    }

    public android.view.accessibility.AccessibilityWindowInfo getWindow(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getWindow", "windowId=" + i);
        }
        synchronized (this.mLock) {
            int i2 = -1;
            if (i != -1) {
                try {
                    i2 = this.mA11yWindowManager.getDisplayIdByUserIdAndWindowIdLocked(this.mSystemSupport.getCurrentUserIdLocked(), i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            ensureWindowsAvailableTimedLocked(i2);
            if (!hasRightsToCurrentUserLocked()) {
                return null;
            }
            if (!this.mSecurityPolicy.canRetrieveWindowsLocked(this)) {
                return null;
            }
            if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                return null;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.view.accessibility.AccessibilityWindowInfo findA11yWindowInfoByIdLocked = this.mA11yWindowManager.findA11yWindowInfoByIdLocked(i);
                if (findA11yWindowInfoByIdLocked == null) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                }
                android.view.accessibility.AccessibilityWindowInfo obtain = android.view.accessibility.AccessibilityWindowInfo.obtain(findA11yWindowInfoByIdLocked);
                obtain.setConnectionId(this.mId);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return obtain;
            } catch (java.lang.Throwable th2) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        }
    }

    public java.lang.String[] findAccessibilityNodeInfosByViewId(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.graphics.Region region;
        int i3;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("findAccessibilityNodeInfosByViewId", "accessibilityWindowId=" + i + ";accessibilityNodeId=" + j + ";viewIdResName=" + str + ";interactionId=" + i2 + ";callback=" + iAccessibilityInteractionConnectionCallback + ";interrogatingTid=" + j2);
        }
        android.graphics.Region obtain = android.graphics.Region.obtain();
        synchronized (this.mLock) {
            try {
                this.mUsesAccessibilityCache = true;
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this.mSystemSupport.getCurrentUserIdLocked(), this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                android.util.Pair<float[], android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = android.os.Binder.getCallingPid();
                android.graphics.Region region2 = region;
                android.view.accessibility.IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i2, callingPid, j2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(str);
                    sb.append(";");
                    sb.append(region2);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    sb.append(this.mFetchFlags);
                    sb.append(";");
                    i3 = callingPid;
                    sb.append(i3);
                    sb.append(";");
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(java.util.Arrays.toString(fArr));
                    logTraceIntConn("findAccessibilityNodeInfosByViewId", sb.toString());
                } else {
                    i3 = callingPid;
                }
                try {
                    connectionLocked.getRemote().findAccessibilityNodeInfosByViewId(j, str, region2, i2, replaceCallbackIfNeeded, this.mFetchFlags, i3, j2, magnificationSpec, fArr);
                    java.lang.String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(connectionLocked.getPackageName(), connectionLocked.getUid());
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    return computeValidReportedPackages;
                } catch (android.os.RemoteException e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 == null || !android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        return null;
                    }
                    region2.recycle();
                    return null;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public java.lang.String[] findAccessibilityNodeInfosByText(int i, long j, java.lang.String str, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.graphics.Region region;
        int i3;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("findAccessibilityNodeInfosByText", "accessibilityWindowId=" + i + ";accessibilityNodeId=" + j + ";text=" + str + ";interactionId=" + i2 + ";callback=" + iAccessibilityInteractionConnectionCallback + ";interrogatingTid=" + j2);
        }
        android.graphics.Region obtain = android.graphics.Region.obtain();
        synchronized (this.mLock) {
            try {
                this.mUsesAccessibilityCache = true;
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this.mSystemSupport.getCurrentUserIdLocked(), this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                android.util.Pair<float[], android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = android.os.Binder.getCallingPid();
                android.graphics.Region region2 = region;
                android.view.accessibility.IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i2, callingPid, j2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(str);
                    sb.append(";");
                    sb.append(region2);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    sb.append(this.mFetchFlags);
                    sb.append(";");
                    i3 = callingPid;
                    sb.append(i3);
                    sb.append(";");
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(java.util.Arrays.toString(fArr));
                    logTraceIntConn("findAccessibilityNodeInfosByText", sb.toString());
                } else {
                    i3 = callingPid;
                }
                try {
                    connectionLocked.getRemote().findAccessibilityNodeInfosByText(j, str, region2, i2, replaceCallbackIfNeeded, this.mFetchFlags, i3, j2, magnificationSpec, fArr);
                    java.lang.String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(connectionLocked.getPackageName(), connectionLocked.getUid());
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    return computeValidReportedPackages;
                } catch (android.os.RemoteException e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 == null || !android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        return null;
                    }
                    region2.recycle();
                    return null;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public java.lang.String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, android.os.Bundle bundle) throws android.os.RemoteException {
        android.graphics.Region region;
        int i4;
        android.view.MagnificationSpec magnificationSpec;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("findAccessibilityNodeInfoByAccessibilityId", "accessibilityWindowId=" + i + ";accessibilityNodeId=" + j + ";interactionId=" + i2 + ";callback=" + iAccessibilityInteractionConnectionCallback + ";flags=" + i3 + ";interrogatingTid=" + j2 + ";arguments=" + bundle);
        }
        android.graphics.Region obtain = android.graphics.Region.obtain();
        synchronized (this.mLock) {
            try {
                this.mUsesAccessibilityCache = true;
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this.mSystemSupport.getCurrentUserIdLocked(), this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                android.util.Pair<float[], android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                android.view.MagnificationSpec magnificationSpec2 = (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = android.os.Binder.getCallingPid();
                android.graphics.Region region2 = region;
                android.view.accessibility.IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i2, callingPid, j2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(region2);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    sb.append(this.mFetchFlags | i3);
                    sb.append(";");
                    i4 = callingPid;
                    sb.append(i4);
                    sb.append(";");
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec2);
                    sb.append(";");
                    sb.append(java.util.Arrays.toString(fArr));
                    sb.append(";");
                    magnificationSpec = magnificationSpec2;
                    sb.append(bundle);
                    logTraceIntConn("findAccessibilityNodeInfoByAccessibilityId", sb.toString());
                } else {
                    i4 = callingPid;
                    magnificationSpec = magnificationSpec2;
                }
                try {
                    connectionLocked.getRemote().findAccessibilityNodeInfoByAccessibilityId(j, region2, i2, replaceCallbackIfNeeded, i3 | this.mFetchFlags, i4, j2, magnificationSpec, fArr, bundle);
                    java.lang.String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(connectionLocked.getPackageName(), connectionLocked.getUid());
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    return computeValidReportedPackages;
                } catch (android.os.RemoteException e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 == null || !android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        return null;
                    }
                    region2.recycle();
                    return null;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public java.lang.String[] findFocus(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.graphics.Region region;
        int i4;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("findFocus", "accessibilityWindowId=" + i + ";accessibilityNodeId=" + j + ";focusType=" + i2 + ";interactionId=" + i3 + ";callback=" + iAccessibilityInteractionConnectionCallback + ";interrogatingTid=" + j2);
        }
        android.graphics.Region obtain = android.graphics.Region.obtain();
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdForFindFocusLocked = resolveAccessibilityWindowIdForFindFocusLocked(i, i2);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this.mSystemSupport.getCurrentUserIdLocked(), this, resolveAccessibilityWindowIdForFindFocusLocked)) {
                    return null;
                }
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdForFindFocusLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdForFindFocusLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                android.util.Pair<float[], android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdForFindFocusLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = android.os.Binder.getCallingPid();
                android.graphics.Region region2 = region;
                android.view.accessibility.IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdForFindFocusLocked, i3, callingPid, j2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(region2);
                    sb.append(";");
                    sb.append(i3);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    sb.append(this.mFetchFlags);
                    sb.append(";");
                    i4 = callingPid;
                    sb.append(i4);
                    sb.append(";");
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(java.util.Arrays.toString(fArr));
                    logTraceIntConn("findFocus", sb.toString());
                } else {
                    i4 = callingPid;
                }
                try {
                    connectionLocked.getRemote().findFocus(j, i2, region2, i3, replaceCallbackIfNeeded, this.mFetchFlags, i4, j2, magnificationSpec, fArr);
                    java.lang.String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(connectionLocked.getPackageName(), connectionLocked.getUid());
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    return computeValidReportedPackages;
                } catch (android.os.RemoteException e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 == null || !android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        return null;
                    }
                    region2.recycle();
                    return null;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public java.lang.String[] focusSearch(int i, long j, int i2, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        android.graphics.Region region;
        int i4;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("focusSearch", "accessibilityWindowId=" + i + ";accessibilityNodeId=" + j + ";direction=" + i2 + ";interactionId=" + i3 + ";callback=" + iAccessibilityInteractionConnectionCallback + ";interrogatingTid=" + j2);
        }
        android.graphics.Region obtain = android.graphics.Region.obtain();
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this.mSystemSupport.getCurrentUserIdLocked(), this, resolveAccessibilityWindowIdLocked)) {
                    return null;
                }
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdLocked);
                if (connectionLocked == null) {
                    return null;
                }
                if (this.mA11yWindowManager.computePartialInteractiveRegionForWindowLocked(resolveAccessibilityWindowIdLocked, obtain)) {
                    region = obtain;
                } else {
                    obtain.recycle();
                    region = null;
                }
                android.util.Pair<float[], android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = getWindowTransformationMatrixAndMagnificationSpec(resolveAccessibilityWindowIdLocked);
                float[] fArr = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
                android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return null;
                }
                int callingPid = android.os.Binder.getCallingPid();
                android.graphics.Region region2 = region;
                android.view.accessibility.IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded = replaceCallbackIfNeeded(iAccessibilityInteractionConnectionCallback, resolveAccessibilityWindowIdLocked, i3, callingPid, j2);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (intConnTracingEnabled()) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(j);
                    sb.append(";");
                    sb.append(i2);
                    sb.append(";");
                    sb.append(region2);
                    sb.append(";");
                    sb.append(i3);
                    sb.append(";");
                    sb.append(replaceCallbackIfNeeded);
                    sb.append(";");
                    sb.append(this.mFetchFlags);
                    sb.append(";");
                    i4 = callingPid;
                    sb.append(i4);
                    sb.append(";");
                    sb.append(j2);
                    sb.append(";");
                    sb.append(magnificationSpec);
                    sb.append(";");
                    sb.append(java.util.Arrays.toString(fArr));
                    logTraceIntConn("focusSearch", sb.toString());
                } else {
                    i4 = callingPid;
                }
                try {
                    connectionLocked.getRemote().focusSearch(j, i2, region2, i3, replaceCallbackIfNeeded, this.mFetchFlags, i4, j2, magnificationSpec, fArr);
                    java.lang.String[] computeValidReportedPackages = this.mSecurityPolicy.computeValidReportedPackages(connectionLocked.getPackageName(), connectionLocked.getUid());
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    return computeValidReportedPackages;
                } catch (android.os.RemoteException e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 == null || !android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        return null;
                    }
                    region2.recycle();
                    return null;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (region2 != null && android.os.Binder.isProxy(connectionLocked.getRemote())) {
                        region2.recycle();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public void sendGesture(int i, android.content.pm.ParceledListSlice parceledListSlice) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("sendGesture", "sequence=" + i + ";gestureSteps=" + parceledListSlice);
        }
    }

    public void dispatchGesture(int i, android.content.pm.ParceledListSlice parceledListSlice, int i2) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("dispatchGesture", "sequence=" + i + ";gestureSteps=" + parceledListSlice + ";displayId=" + i2);
        }
    }

    public boolean performAccessibilityAction(int i, long j, int i2, android.os.Bundle bundle, int i3, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws android.os.RemoteException {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("performAccessibilityAction", "accessibilityWindowId=" + i + ";accessibilityNodeId=" + j + ";action=" + i2 + ";arguments=" + bundle + ";interactionId=" + i3 + ";callback=" + iAccessibilityInteractionConnectionCallback + ";interrogatingTid=" + j2);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                int resolveAccessibilityWindowIdLocked = resolveAccessibilityWindowIdLocked(i);
                if (!this.mSecurityPolicy.canGetAccessibilityNodeInfoLocked(this.mSystemSupport.getCurrentUserIdLocked(), this, resolveAccessibilityWindowIdLocked)) {
                    return false;
                }
                if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    return performAccessibilityActionInternal(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdLocked, j, i2, bundle, i3, iAccessibilityInteractionConnectionCallback, this.mFetchFlags, j2);
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean performGlobalAction(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("performGlobalAction", "action=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSystemActionPerformer.performSystemAction(i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getSystemActions() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getSystemActions", "");
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return java.util.Collections.emptyList();
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSystemActionPerformer.getSystemActions();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isFingerprintGestureDetectionAvailable() {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("isFingerprintGestureDetectionAvailable", "");
        }
        boolean z = false;
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!isCapturingFingerprintGestures()) {
                return false;
            }
            com.android.server.accessibility.FingerprintGestureDispatcher fingerprintGestureDispatcher = this.mSystemSupport.getFingerprintGestureDispatcher();
            if (fingerprintGestureDispatcher != null) {
                if (fingerprintGestureDispatcher.isFingerprintGestureDetectionAvailable()) {
                    z = true;
                }
            }
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public android.accessibilityservice.MagnificationConfig getMagnificationConfig(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationConfig", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return null;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSystemSupport.getMagnificationProcessor().getMagnificationConfig(i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getMagnificationScale(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationScale", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return 1.0f;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSystemSupport.getMagnificationProcessor().getScale(i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.graphics.Region getMagnificationRegion(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationRegion", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                android.graphics.Region obtain = android.graphics.Region.obtain();
                if (!hasRightsToCurrentUserLocked()) {
                    return obtain;
                }
                com.android.server.accessibility.magnification.MagnificationProcessor magnificationProcessor = this.mSystemSupport.getMagnificationProcessor();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    magnificationProcessor.getFullscreenMagnificationRegion(i, obtain, this.mSecurityPolicy.canControlMagnification(this));
                    return obtain;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.graphics.Region getCurrentMagnificationRegion(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getCurrentMagnificationRegion", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                android.graphics.Region obtain = android.graphics.Region.obtain();
                if (!hasRightsToCurrentUserLocked()) {
                    return obtain;
                }
                com.android.server.accessibility.magnification.MagnificationProcessor magnificationProcessor = this.mSystemSupport.getMagnificationProcessor();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    magnificationProcessor.getCurrentMagnificationRegion(i, obtain, this.mSecurityPolicy.canControlMagnification(this));
                    return obtain;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getMagnificationCenterX(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationCenterX", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                }
                com.android.server.accessibility.magnification.MagnificationProcessor magnificationProcessor = this.mSystemSupport.getMagnificationProcessor();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return magnificationProcessor.getCenterX(i, this.mSecurityPolicy.canControlMagnification(this));
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public float getMagnificationCenterY(int i) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getMagnificationCenterY", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                }
                com.android.server.accessibility.magnification.MagnificationProcessor magnificationProcessor = this.mSystemSupport.getMagnificationProcessor();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return magnificationProcessor.getCenterY(i, this.mSecurityPolicy.canControlMagnification(this));
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
    
        if (r3.isMagnifying(r5) == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean resetMagnification(int i, boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("resetMagnification", "displayId=" + i + ";animate=" + z);
        }
        synchronized (this.mLock) {
            try {
                boolean z2 = false;
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                if (!this.mSecurityPolicy.canControlMagnification(this)) {
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.accessibility.magnification.MagnificationProcessor magnificationProcessor = this.mSystemSupport.getMagnificationProcessor();
                    if (!magnificationProcessor.resetFullscreenMagnification(i, z)) {
                    }
                    z2 = true;
                    return z2;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
    
        if (r3.isMagnifying(r5) == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean resetCurrentMagnification(int i, boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("resetCurrentMagnification", "displayId=" + i + ";animate=" + z);
        }
        synchronized (this.mLock) {
            try {
                boolean z2 = false;
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                if (!this.mSecurityPolicy.canControlMagnification(this)) {
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.accessibility.magnification.MagnificationProcessor magnificationProcessor = this.mSystemSupport.getMagnificationProcessor();
                    if (!magnificationProcessor.resetCurrentMagnification(i, z)) {
                    }
                    z2 = true;
                    return z2;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setMagnificationConfig(int i, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig, boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setMagnificationSpec", "displayId=" + i + ", config=" + magnificationConfig.toString());
        }
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    return false;
                }
                if (!this.mSecurityPolicy.canControlMagnification(this)) {
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mSystemSupport.getMagnificationProcessor().setMagnificationConfig(i, magnificationConfig, z, this.mId);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setMagnificationCallbackEnabled(int i, boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setMagnificationCallbackEnabled", "displayId=" + i + ";enabled=" + z);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInvocationHandler.setMagnificationCallbackEnabled(i, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isMagnificationCallbackEnabled(int i) {
        return this.mInvocationHandler.isMagnificationCallbackEnabled(i);
    }

    public void setSoftKeyboardCallbackEnabled(boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setSoftKeyboardCallbackEnabled", "enabled=" + z);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInvocationHandler.setSoftKeyboardCallbackEnabled(z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void takeScreenshotOfWindow(int i, int i2, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (uptimeMillis - this.mRequestTakeScreenshotOfWindowTimestampMs.get(i, 0L).longValue() <= 333) {
            iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(3, i2);
            return;
        }
        this.mRequestTakeScreenshotOfWindowTimestampMs.put(i, java.lang.Long.valueOf(uptimeMillis));
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(1, i2);
                    return;
                }
                if (!this.mSecurityPolicy.canTakeScreenshotLocked(this)) {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(2, i2);
                    return;
                }
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(2, i2);
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdLocked(i));
                    if (connectionLocked == null) {
                        iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(5, i2);
                    } else {
                        connectionLocked.getRemote().takeScreenshotOfWindow(i2, screenCaptureListener, iAccessibilityInteractionConnectionCallback);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void takeScreenshot(final int i, final android.os.RemoteCallback remoteCallback) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn(TAKE_SCREENSHOT, "displayId=" + i + ";callback=" + remoteCallback);
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (this.mRequestTakeScreenshotTimestampMs != 0 && uptimeMillis - this.mRequestTakeScreenshotTimestampMs <= 333) {
            sendScreenshotFailure(3, remoteCallback);
            return;
        }
        this.mRequestTakeScreenshotTimestampMs = uptimeMillis;
        synchronized (this.mLock) {
            try {
                if (!hasRightsToCurrentUserLocked()) {
                    sendScreenshotFailure(1, remoteCallback);
                    return;
                }
                if (!this.mSecurityPolicy.canTakeScreenshotLocked(this)) {
                    throw new java.lang.SecurityException("Services don't have the capability of taking the screenshot.");
                }
                if (!this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                    sendScreenshotFailure(2, remoteCallback);
                    return;
                }
                android.view.Display display = ((android.hardware.display.DisplayManager) this.mContext.getSystemService("display")).getDisplay(i);
                if (display == null || (display.getType() == 5 && (display.getFlags() & 4) != 0)) {
                    sendScreenshotFailure(4, remoteCallback);
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (com.android.window.flags.Flags.deleteCaptureDisplay()) {
                    try {
                        try {
                            this.mWindowManagerService.captureDisplay(i, null, new android.window.ScreenCapture.ScreenCaptureListener(new java.util.function.ObjIntConsumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda1
                                @Override // java.util.function.ObjIntConsumer
                                public final void accept(java.lang.Object obj, int i2) {
                                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.lambda$takeScreenshot$0(remoteCallback, (android.window.ScreenCapture.ScreenshotHardwareBuffer) obj, i2);
                                }
                            }));
                        } catch (java.lang.Exception e) {
                            sendScreenshotFailure(4, remoteCallback);
                        }
                        return;
                    } finally {
                    }
                }
                try {
                    this.mMainHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.lambda$takeScreenshot$1(i, remoteCallback, obj);
                        }
                    }, (java.lang.Object) null).recycleOnUse());
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshot$0(android.os.RemoteCallback remoteCallback, android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, int i) {
        if (screenshotHardwareBuffer != null && i == 0) {
            sendScreenshotSuccess(screenshotHardwareBuffer, remoteCallback);
        } else {
            sendScreenshotFailure(4, remoteCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshot$1(int i, android.os.RemoteCallback remoteCallback, java.lang.Object obj) {
        android.window.ScreenCapture.ScreenshotHardwareBuffer userScreenshot = ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).userScreenshot(i);
        if (userScreenshot != null) {
            sendScreenshotSuccess(userScreenshot, remoteCallback);
        } else {
            sendScreenshotFailure(4, remoteCallback);
        }
    }

    private void sendScreenshotSuccess(final android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, final android.os.RemoteCallback remoteCallback) {
        if (com.android.window.flags.Flags.deleteCaptureDisplay()) {
            this.mMainHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.lambda$sendScreenshotSuccess$2(screenshotHardwareBuffer, remoteCallback, obj);
                }
            }, (java.lang.Object) null).recycleOnUse());
            return;
        }
        android.hardware.HardwareBuffer hardwareBuffer = screenshotHardwareBuffer.getHardwareBuffer();
        android.graphics.ParcelableColorSpace parcelableColorSpace = new android.graphics.ParcelableColorSpace(screenshotHardwareBuffer.getColorSpace());
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("screenshot_status", 0);
        bundle.putParcelable("screenshot_hardwareBuffer", hardwareBuffer);
        bundle.putParcelable("screenshot_colorSpace", parcelableColorSpace);
        bundle.putLong("screenshot_timestamp", android.os.SystemClock.uptimeMillis());
        remoteCallback.sendResult(bundle);
        hardwareBuffer.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendScreenshotSuccess$2(android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, android.os.RemoteCallback remoteCallback, java.lang.Object obj) {
        android.hardware.HardwareBuffer hardwareBuffer = screenshotHardwareBuffer.getHardwareBuffer();
        android.graphics.ParcelableColorSpace parcelableColorSpace = new android.graphics.ParcelableColorSpace(screenshotHardwareBuffer.getColorSpace());
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("screenshot_status", 0);
        bundle.putParcelable("screenshot_hardwareBuffer", hardwareBuffer);
        bundle.putParcelable("screenshot_colorSpace", parcelableColorSpace);
        bundle.putLong("screenshot_timestamp", android.os.SystemClock.uptimeMillis());
        remoteCallback.sendResult(bundle);
        hardwareBuffer.close();
    }

    private void sendScreenshotFailure(final int i, final android.os.RemoteCallback remoteCallback) {
        this.mMainHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.accessibility.AbstractAccessibilityServiceConnection.lambda$sendScreenshotFailure$3(i, remoteCallback, obj);
            }
        }, (java.lang.Object) null).recycleOnUse());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendScreenshotFailure$3(int i, android.os.RemoteCallback remoteCallback, java.lang.Object obj) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("screenshot_status", i);
        remoteCallback.sendResult(bundle);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, LOG_TAG, printWriter)) {
            synchronized (this.mLock) {
                printWriter.append((java.lang.CharSequence) ("Service[label=" + ((java.lang.Object) this.mAccessibilityServiceInfo.getResolveInfo().loadLabel(this.mContext.getPackageManager()))));
                printWriter.append((java.lang.CharSequence) (", feedbackType" + android.accessibilityservice.AccessibilityServiceInfo.feedbackTypeToString(this.mFeedbackType)));
                printWriter.append((java.lang.CharSequence) (", capabilities=" + this.mAccessibilityServiceInfo.getCapabilities()));
                printWriter.append((java.lang.CharSequence) (", eventTypes=" + android.view.accessibility.AccessibilityEvent.eventTypeToString(this.mEventTypes)));
                printWriter.append((java.lang.CharSequence) (", notificationTimeout=" + this.mNotificationTimeout));
                printWriter.append((java.lang.CharSequence) (", requestA11yBtn=" + this.mRequestAccessibilityButton));
                printWriter.append("]");
            }
        }
    }

    void addWindowTokensForAllDisplays() {
        for (android.view.Display display : this.mDisplayManager.getDisplays()) {
            addWindowTokenForDisplay(display.getDisplayId());
        }
    }

    void addWindowTokenForDisplay(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.Binder binder = new android.os.Binder();
            if (wmTracingEnabled()) {
                logTraceWM("addWindowToken", binder + ";TYPE_ACCESSIBILITY_OVERLAY;" + i + ";null");
            }
            this.mWindowManagerService.addWindowToken(binder, 2032, i, null);
            synchronized (this.mLock) {
                this.mOverlayWindowTokens.put(i, binder);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void onRemoved() {
        for (android.view.Display display : this.mDisplayManager.getDisplays()) {
            onDisplayRemoved(display.getDisplayId());
        }
        com.android.server.accessibility.Flags.cleanupA11yOverlays();
    }

    public void onDisplayRemoved(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (wmTracingEnabled()) {
            logTraceWM("addWindowToken", this.mOverlayWindowTokens.get(i) + ";true;" + i);
        }
        try {
            this.mWindowManagerService.removeWindowToken(this.mOverlayWindowTokens.get(i), true, i);
            synchronized (this.mLock) {
                this.mOverlayWindowTokens.remove(i);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.os.IBinder getOverlayWindowToken(int i) {
        android.os.IBinder iBinder;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getOverlayWindowToken", "displayId=" + i);
        }
        synchronized (this.mLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    iBinder = this.mOverlayWindowTokens.get(i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return iBinder;
    }

    public int getWindowIdForLeashToken(@android.annotation.NonNull android.os.IBinder iBinder) {
        int windowIdLocked;
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("getWindowIdForLeashToken", "token=" + iBinder);
        }
        synchronized (this.mLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    windowIdLocked = this.mA11yWindowManager.getWindowIdLocked(iBinder);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return windowIdLocked;
    }

    public void resetLocked() {
        com.android.server.accessibility.Flags.resettableDynamicProperties();
        this.mSystemSupport.getKeyEventDispatcher().flush(this);
        try {
            if (this.mServiceInterface != null) {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("init", "null, " + this.mId + ", null");
                }
                this.mServiceInterface.init((android.accessibilityservice.IAccessibilityServiceConnection) null, this.mId, (android.os.IBinder) null);
            }
        } catch (android.os.RemoteException e) {
        }
        if (this.mService != null) {
            try {
                this.mService.unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e2) {
                android.util.Slog.e(LOG_TAG, "Failed unregistering death link");
            }
            this.mService = null;
        }
        this.mServiceInterface = null;
        this.mReceivedAccessibilityButtonCallbackSinceBind = false;
    }

    public boolean isConnectedLocked() {
        return this.mService != null;
    }

    public void notifyAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        android.os.Message obtainMessage;
        synchronized (this.mLock) {
            try {
                int eventType = accessibilityEvent.getEventType();
                boolean wantsEventLocked = wantsEventLocked(accessibilityEvent);
                boolean z = this.mUsesAccessibilityCache && (4307005 & eventType) != 0;
                if (wantsEventLocked || z) {
                    if (this.mSecurityPolicy.checkAccessibilityAccess(this)) {
                        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(accessibilityEvent);
                        if (this.mNotificationTimeout > 0 && eventType != 2048) {
                            android.view.accessibility.AccessibilityEvent accessibilityEvent2 = this.mPendingEvents.get(eventType);
                            this.mPendingEvents.put(eventType, obtain);
                            if (accessibilityEvent2 != null) {
                                this.mEventDispatchHandler.removeMessages(eventType);
                                accessibilityEvent2.recycle();
                            }
                            obtainMessage = this.mEventDispatchHandler.obtainMessage(eventType);
                        } else {
                            obtainMessage = this.mEventDispatchHandler.obtainMessage(eventType, obtain);
                        }
                        obtainMessage.arg1 = wantsEventLocked ? 1 : 0;
                        this.mEventDispatchHandler.sendMessageDelayed(obtainMessage, this.mNotificationTimeout);
                    }
                }
            } finally {
            }
        }
    }

    private boolean wantsEventLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (!canReceiveEventsLocked()) {
            return false;
        }
        boolean z = (this.mFetchFlags & 128) != 0;
        if (accessibilityEvent.getWindowId() != -1 && !accessibilityEvent.isImportantForAccessibility() && !z) {
            return false;
        }
        if (accessibilityEvent.isAccessibilityDataSensitive() && (this.mFetchFlags & 512) == 0) {
            return false;
        }
        int eventType = accessibilityEvent.getEventType();
        if ((this.mEventTypes & eventType) != eventType) {
            return false;
        }
        java.util.Set<java.lang.String> set = this.mPackageNames;
        return set.isEmpty() || set.contains(accessibilityEvent.getPackageName() != null ? accessibilityEvent.getPackageName().toString() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAccessibilityEventInternal(int i, android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z) {
        synchronized (this.mLock) {
            try {
                android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient = this.mServiceInterface;
                if (iAccessibilityServiceClient == null) {
                    return;
                }
                if (accessibilityEvent == null) {
                    accessibilityEvent = this.mPendingEvents.get(i);
                    if (accessibilityEvent == null) {
                        return;
                    } else {
                        this.mPendingEvents.remove(i);
                    }
                }
                if (this.mSecurityPolicy.canRetrieveWindowContentLocked(this)) {
                    accessibilityEvent.setConnectionId(this.mId);
                } else {
                    accessibilityEvent.setSource(null);
                }
                accessibilityEvent.setSealed(true);
                try {
                    try {
                        if (svcClientTracingEnabled()) {
                            logTraceSvcClient("onAccessibilityEvent", accessibilityEvent + ";" + z);
                        }
                        iAccessibilityServiceClient.onAccessibilityEvent(accessibilityEvent, z);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(LOG_TAG, "Error during sending " + accessibilityEvent + " to " + iAccessibilityServiceClient, e);
                    }
                    accessibilityEvent.recycle();
                } catch (java.lang.Throwable th) {
                    accessibilityEvent.recycle();
                    throw th;
                }
            } finally {
            }
        }
    }

    public void notifyGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
        if (android.view.accessibility.Flags.copyEventsForGestureDetection()) {
            this.mInvocationHandler.obtainMessage(1, accessibilityGestureEvent.copyForAsync()).sendToTarget();
        } else {
            this.mInvocationHandler.obtainMessage(1, accessibilityGestureEvent).sendToTarget();
        }
    }

    public void notifySystemActionsChangedLocked() {
        this.mInvocationHandler.sendEmptyMessage(9);
    }

    public void notifyClearAccessibilityNodeInfoCache() {
        this.mInvocationHandler.sendEmptyMessage(2);
    }

    public void notifyMagnificationChangedLocked(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
        this.mInvocationHandler.notifyMagnificationChangedLocked(i, region, magnificationConfig);
    }

    public void notifySoftKeyboardShowModeChangedLocked(int i) {
        this.mInvocationHandler.notifySoftKeyboardShowModeChangedLocked(i);
    }

    public void notifyAccessibilityButtonClickedLocked(int i) {
        this.mInvocationHandler.notifyAccessibilityButtonClickedLocked(i);
    }

    public void notifyAccessibilityButtonAvailabilityChangedLocked(boolean z) {
        this.mInvocationHandler.notifyAccessibilityButtonAvailabilityChangedLocked(z);
    }

    public void createImeSessionLocked() {
        this.mInvocationHandler.createImeSessionLocked();
    }

    public void setImeSessionEnabledLocked(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) {
        this.mInvocationHandler.setImeSessionEnabledLocked(iAccessibilityInputMethodSession, z);
    }

    public void bindInputLocked() {
        this.mInvocationHandler.bindInputLocked();
    }

    public void unbindInputLocked() {
        this.mInvocationHandler.unbindInputLocked();
    }

    public void startInputLocked(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        this.mInvocationHandler.startInputLocked(iRemoteAccessibilityInputConnection, editorInfo, z);
    }

    @android.annotation.Nullable
    private android.util.Pair<float[], android.view.MagnificationSpec> getWindowTransformationMatrixAndMagnificationSpec(int i) {
        return this.mSystemSupport.getWindowTransformationMatrixAndMagnificationSpec(i);
    }

    public boolean wantsGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return ((motionEvent.getSource() & (-256)) & this.mGenericMotionEventSources) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyMagnificationChangedInternal(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onMagnificationChanged", i + ", " + region + ", " + magnificationConfig.toString());
                }
                serviceInterfaceSafely.onMagnificationChanged(i, region, magnificationConfig);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending magnification changes to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySoftKeyboardShowModeChangedInternal(int i) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onSoftKeyboardShowModeChanged", java.lang.String.valueOf(i));
                }
                serviceInterfaceSafely.onSoftKeyboardShowModeChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending soft keyboard show mode changes to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAccessibilityButtonClickedInternal(int i) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onAccessibilityButtonClicked", java.lang.String.valueOf(i));
                }
                serviceInterfaceSafely.onAccessibilityButtonClicked(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending accessibility button click to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAccessibilityButtonAvailabilityChangedInternal(boolean z) {
        if (this.mReceivedAccessibilityButtonCallbackSinceBind && this.mLastAccessibilityButtonCallbackState == z) {
            return;
        }
        this.mReceivedAccessibilityButtonCallbackSinceBind = true;
        this.mLastAccessibilityButtonCallbackState = z;
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onAccessibilityButtonAvailabilityChanged", java.lang.String.valueOf(z));
                }
                serviceInterfaceSafely.onAccessibilityButtonAvailabilityChanged(z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending accessibility button availability change to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyGestureInternal(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onGesture", accessibilityGestureEvent.toString());
                }
                serviceInterfaceSafely.onGesture(accessibilityGestureEvent);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error during sending gesture " + accessibilityGestureEvent + " to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySystemActionsChangedInternal() {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("onSystemActionsChanged", "");
                }
                serviceInterfaceSafely.onSystemActionsChanged();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error sending system actions change to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyClearAccessibilityCacheInternal() {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("clearAccessibilityCache", "");
                }
                serviceInterfaceSafely.clearAccessibilityCache();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error during requesting accessibility info cache to be cleared.", e);
            }
        }
    }

    protected void createImeSessionInternal() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImeSessionEnabledInternal(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null && iAccessibilityInputMethodSession != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("createImeSession", "");
                }
                serviceInterfaceSafely.setImeSessionEnabled(iAccessibilityInputMethodSession, z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error requesting IME session from " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindInputInternal() {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("bindInput", "");
                }
                serviceInterfaceSafely.bindInput();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error binding input to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindInputInternal() {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("unbindInput", "");
                }
                serviceInterfaceSafely.unbindInput();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error unbinding input to " + this.mService, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInputInternal(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        android.accessibilityservice.IAccessibilityServiceClient serviceInterfaceSafely = getServiceInterfaceSafely();
        if (serviceInterfaceSafely != null) {
            try {
                if (svcClientTracingEnabled()) {
                    logTraceSvcClient("startInput", "editorInfo=" + editorInfo + " restarting=" + z);
                }
                serviceInterfaceSafely.startInput(iRemoteAccessibilityInputConnection, editorInfo, z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Error starting input to " + this.mService, e);
            }
        }
    }

    protected android.accessibilityservice.IAccessibilityServiceClient getServiceInterfaceSafely() {
        android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient;
        synchronized (this.mLock) {
            iAccessibilityServiceClient = this.mServiceInterface;
        }
        return iAccessibilityServiceClient;
    }

    private int resolveAccessibilityWindowIdLocked(int i) {
        if (i == Integer.MAX_VALUE) {
            int activeWindowId = this.mA11yWindowManager.getActiveWindowId(this.mSystemSupport.getCurrentUserIdLocked());
            if (!this.mA11yWindowManager.windowIdBelongsToDisplayType(activeWindowId, this.mDisplayTypes)) {
                return -1;
            }
            return activeWindowId;
        }
        return i;
    }

    int resolveAccessibilityWindowIdForFindFocusLocked(int i, int i2) {
        if (i == -2) {
            int focusedWindowId = this.mA11yWindowManager.getFocusedWindowId(i2);
            if (!this.mA11yWindowManager.windowIdBelongsToDisplayType(focusedWindowId, this.mDisplayTypes)) {
                return -1;
            }
            return focusedWindowId;
        }
        return i;
    }

    private void ensureWindowsAvailableTimedLocked(int i) {
        if (i == -1 || this.mA11yWindowManager.getWindowListLocked(i) != null) {
            return;
        }
        if (!this.mA11yWindowManager.isTrackingWindowsLocked(i)) {
            this.mSystemSupport.onClientChangeLocked(false);
        }
        if (!this.mA11yWindowManager.isTrackingWindowsLocked(i)) {
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        while (this.mA11yWindowManager.getWindowListLocked(i) == null) {
            long uptimeMillis2 = 5000 - (android.os.SystemClock.uptimeMillis() - uptimeMillis);
            if (uptimeMillis2 <= 0) {
                return;
            } else {
                try {
                    this.mLock.wait(uptimeMillis2);
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    private boolean performAccessibilityActionInternal(int i, int i2, long j, int i3, android.os.Bundle bundle, int i4, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i5, long j2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(i, i2);
                if (connectionLocked == null) {
                    return false;
                }
                boolean z = i3 == 64 || i3 == 128;
                android.os.IBinder windowTokenForUserAndWindowIdLocked = !z ? this.mA11yWindowManager.getWindowTokenForUserAndWindowIdLocked(i, i2) : null;
                android.view.accessibility.AccessibilityWindowInfo findA11yWindowInfoByIdLocked = this.mA11yWindowManager.findA11yWindowInfoByIdLocked(i2);
                if (findA11yWindowInfoByIdLocked != null && findA11yWindowInfoByIdLocked.isInPictureInPictureMode() && this.mA11yWindowManager.getPictureInPictureActionReplacingConnection() != null && !z) {
                    connectionLocked = this.mA11yWindowManager.getPictureInPictureActionReplacingConnection();
                }
                int callingPid = android.os.Binder.getCallingPid();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), 3, 0);
                    if (i3 == 16 || i3 == 32) {
                        this.mA11yWindowManager.notifyOutsideTouch(i, i2);
                    }
                    if (windowTokenForUserAndWindowIdLocked != null) {
                        this.mWindowManagerService.requestWindowFocus(windowTokenForUserAndWindowIdLocked);
                    }
                    if (intConnTracingEnabled()) {
                        logTraceIntConn("performAccessibilityAction", j + ";" + i3 + ";" + bundle + ";" + i4 + ";" + iAccessibilityInteractionConnectionCallback + ";" + this.mFetchFlags + ";" + callingPid + ";" + j2);
                    }
                    connectionLocked.getRemote().performAccessibilityAction(j, i3, bundle, i4, iAccessibilityInteractionConnectionCallback, i5, callingPid, j2);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                } catch (android.os.RemoteException e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } finally {
            }
        }
    }

    private android.view.accessibility.IAccessibilityInteractionConnectionCallback replaceCallbackIfNeeded(android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i, int i2, int i3, long j) {
        com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection pictureInPictureActionReplacingConnection = this.mA11yWindowManager.getPictureInPictureActionReplacingConnection();
        synchronized (this.mLock) {
            android.view.accessibility.AccessibilityWindowInfo findA11yWindowInfoByIdLocked = this.mA11yWindowManager.findA11yWindowInfoByIdLocked(i);
            return (findA11yWindowInfoByIdLocked == null || !findA11yWindowInfoByIdLocked.isInPictureInPictureMode() || pictureInPictureActionReplacingConnection == null) ? iAccessibilityInteractionConnectionCallback : new com.android.server.accessibility.ActionReplacingCallback(iAccessibilityInteractionConnectionCallback, pictureInPictureActionReplacingConnection.getRemote(), i2, i3, j);
        }
    }

    private java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindowsByDisplayLocked(int i) {
        java.util.List<android.view.accessibility.AccessibilityWindowInfo> windowListLocked = this.mA11yWindowManager.getWindowListLocked(i);
        if (windowListLocked == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = windowListLocked.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.accessibility.AccessibilityWindowInfo obtain = android.view.accessibility.AccessibilityWindowInfo.obtain(windowListLocked.get(i2));
            obtain.setConnectionId(this.mId);
            arrayList.add(obtain);
        }
        return arrayList;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    private final class InvocationHandler extends android.os.Handler {
        private static final int MSG_BIND_INPUT = 12;
        public static final int MSG_CLEAR_ACCESSIBILITY_CACHE = 2;
        private static final int MSG_CREATE_IME_SESSION = 10;
        private static final int MSG_ON_ACCESSIBILITY_BUTTON_AVAILABILITY_CHANGED = 8;
        private static final int MSG_ON_ACCESSIBILITY_BUTTON_CLICKED = 7;
        public static final int MSG_ON_GESTURE = 1;
        private static final int MSG_ON_MAGNIFICATION_CHANGED = 5;
        private static final int MSG_ON_SOFT_KEYBOARD_STATE_CHANGED = 6;
        private static final int MSG_ON_SYSTEM_ACTIONS_CHANGED = 9;
        private static final int MSG_SET_IME_SESSION_ENABLED = 11;
        private static final int MSG_START_INPUT = 14;
        private static final int MSG_UNBIND_INPUT = 13;
        private boolean mIsSoftKeyboardCallbackEnabled;

        @com.android.internal.annotations.GuardedBy({"mlock"})
        private final android.util.SparseArray<java.lang.Boolean> mMagnificationCallbackState;

        public InvocationHandler(android.os.Looper looper) {
            super(looper, null, true);
            this.mMagnificationCallbackState = new android.util.SparseArray<>(0);
            this.mIsSoftKeyboardCallbackEnabled = false;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    java.lang.Object obj = message.obj;
                    if (obj instanceof android.accessibilityservice.AccessibilityGestureEvent) {
                        android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent = (android.accessibilityservice.AccessibilityGestureEvent) obj;
                        com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifyGestureInternal(accessibilityGestureEvent);
                        if (android.view.accessibility.Flags.copyEventsForGestureDetection()) {
                            accessibilityGestureEvent.recycle();
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifyClearAccessibilityCacheInternal();
                    return;
                case 3:
                case 4:
                default:
                    throw new java.lang.IllegalArgumentException("Unknown message: " + i);
                case 5:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifyMagnificationChangedInternal(someArgs.argi1, (android.graphics.Region) someArgs.arg1, (android.accessibilityservice.MagnificationConfig) someArgs.arg2);
                    someArgs.recycle();
                    return;
                case 6:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifySoftKeyboardShowModeChangedInternal(message.arg1);
                    return;
                case 7:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifyAccessibilityButtonClickedInternal(message.arg1);
                    return;
                case 8:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifyAccessibilityButtonAvailabilityChangedInternal(message.arg1 != 0);
                    return;
                case 9:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.notifySystemActionsChangedInternal();
                    return;
                case 10:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.createImeSessionInternal();
                    return;
                case 11:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.setImeSessionEnabledInternal((com.android.internal.inputmethod.IAccessibilityInputMethodSession) message.obj, message.arg1 != 0);
                    return;
                case 12:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.bindInputInternal();
                    return;
                case 13:
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.unbindInputInternal();
                    return;
                case 14:
                    boolean z = message.arg1 != 0;
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.startInputInternal((com.android.internal.inputmethod.IRemoteAccessibilityInputConnection) someArgs2.arg1, (android.view.inputmethod.EditorInfo) someArgs2.arg2, z);
                    someArgs2.recycle();
                    return;
            }
        }

        public void notifyMagnificationChangedLocked(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
            synchronized (com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.mLock) {
                try {
                    if (this.mMagnificationCallbackState.get(i) == null) {
                        return;
                    }
                    com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                    obtain.arg1 = region;
                    obtain.arg2 = magnificationConfig;
                    obtain.argi1 = i;
                    obtainMessage(5, obtain).sendToTarget();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setMagnificationCallbackEnabled(int i, boolean z) {
            synchronized (com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.mLock) {
                try {
                    if (z) {
                        this.mMagnificationCallbackState.put(i, true);
                    } else {
                        this.mMagnificationCallbackState.remove(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isMagnificationCallbackEnabled(int i) {
            boolean z;
            synchronized (com.android.server.accessibility.AbstractAccessibilityServiceConnection.this.mLock) {
                z = this.mMagnificationCallbackState.get(i) != null;
            }
            return z;
        }

        public void notifySoftKeyboardShowModeChangedLocked(int i) {
            if (!this.mIsSoftKeyboardCallbackEnabled) {
                return;
            }
            obtainMessage(6, i, 0).sendToTarget();
        }

        public void setSoftKeyboardCallbackEnabled(boolean z) {
            this.mIsSoftKeyboardCallbackEnabled = z;
        }

        public void notifyAccessibilityButtonClickedLocked(int i) {
            obtainMessage(7, i, 0).sendToTarget();
        }

        public void notifyAccessibilityButtonAvailabilityChangedLocked(boolean z) {
            obtainMessage(8, z ? 1 : 0, 0).sendToTarget();
        }

        public void createImeSessionLocked() {
            obtainMessage(10).sendToTarget();
        }

        public void setImeSessionEnabledLocked(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) {
            obtainMessage(11, z ? 1 : 0, 0, iAccessibilityInputMethodSession).sendToTarget();
        }

        public void bindInputLocked() {
            obtainMessage(12).sendToTarget();
        }

        public void unbindInputLocked() {
            obtainMessage(13).sendToTarget();
        }

        public void startInputLocked(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = iRemoteAccessibilityInputConnection;
            obtain.arg2 = editorInfo;
            obtainMessage(14, z ? 1 : 0, 0, obtain).sendToTarget();
        }
    }

    public boolean isServiceHandlesDoubleTapEnabled() {
        return this.mServiceHandlesDoubleTap;
    }

    public boolean isMultiFingerGesturesEnabled() {
        return this.mRequestMultiFingerGestures;
    }

    public boolean isTwoFingerPassthroughEnabled() {
        return this.mRequestTwoFingerPassthrough;
    }

    public boolean isSendMotionEventsEnabled() {
        return this.mSendMotionEvents;
    }

    public void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setGestureDetectionPassthroughRegion", "displayId=" + i + ";region=" + region);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.setGestureDetectionPassthroughRegion(i, region);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setTouchExplorationPassthroughRegion", "displayId=" + i + ";region=" + region);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.setTouchExplorationPassthroughRegion(i, region);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setFocusAppearance(int i, int i2) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setFocusAppearance", "strokeWidth=" + i + ";color=" + i2);
        }
    }

    public void setCacheEnabled(boolean z) {
        if (svcConnTracingEnabled()) {
            logTraceSvcConn("setCacheEnabled", "enabled=" + z);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                this.mUsesAccessibilityCache = z;
                this.mSystemSupport.onClientChangeLocked(true);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void logTrace(long j, java.lang.String str, long j2, java.lang.String str2, int i, long j3, int i2, android.os.Bundle bundle) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mTrace.isA11yTracingEnabledForTypes(j2)) {
                java.util.ArrayList arrayList = (java.util.ArrayList) bundle.getSerializable("call_stack", java.util.ArrayList.class);
                this.mTrace.logTrace(j, str, j2, str2, i, j3, i2, (java.lang.StackTraceElement[]) arrayList.toArray(new java.lang.StackTraceElement[arrayList.size()]), (java.util.HashSet) bundle.getSerializable("ignore_call_stack", java.util.HashSet.class));
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected boolean svcClientTracingEnabled() {
        return this.mTrace.isA11yTracingEnabledForTypes(2L);
    }

    protected void logTraceSvcClient(java.lang.String str, java.lang.String str2) {
        this.mTrace.logTrace("AbstractAccessibilityServiceConnection.IAccessibilityServiceClient." + str, 2L, str2);
    }

    protected boolean svcConnTracingEnabled() {
        return this.mTrace.isA11yTracingEnabledForTypes(1L);
    }

    protected void logTraceSvcConn(java.lang.String str, java.lang.String str2) {
        this.mTrace.logTrace("AbstractAccessibilityServiceConnection.IAccessibilityServiceConnection." + str, 1L, str2);
    }

    protected boolean intConnTracingEnabled() {
        return this.mTrace.isA11yTracingEnabledForTypes(16L);
    }

    protected void logTraceIntConn(java.lang.String str, java.lang.String str2) {
        this.mTrace.logTrace("AbstractAccessibilityServiceConnection." + str, 16L, str2);
    }

    protected boolean wmTracingEnabled() {
        return this.mTrace.isA11yTracingEnabledForTypes(512L);
    }

    protected void logTraceWM(java.lang.String str, java.lang.String str2) {
        this.mTrace.logTrace("WindowManagerInternal." + str, 512L, str2);
    }

    public void setServiceDetectsGesturesEnabled(int i, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mServiceDetectsGestures.put(i, java.lang.Boolean.valueOf(z));
            this.mSystemSupport.setServiceDetectsGesturesEnabled(i, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isServiceDetectsGesturesEnabled(int i) {
        if (this.mServiceDetectsGestures.contains(i)) {
            return this.mServiceDetectsGestures.get(i).booleanValue();
        }
        return false;
    }

    public void requestTouchExploration(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.requestTouchExploration(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestDragging(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.requestDragging(i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void requestDelegating(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.requestDelegating(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onDoubleTap(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.onDoubleTap(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onDoubleTapAndHold(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.onDoubleTapAndHold(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setAnimationScale(float f) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Global.putFloat(this.mContext.getContentResolver(), "window_animation_scale", f);
            android.provider.Settings.Global.putFloat(this.mContext.getContentResolver(), "transition_animation_scale", f);
            android.provider.Settings.Global.putFloat(this.mContext.getContentResolver(), "animator_duration_scale", f);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemSupport.attachAccessibilityOverlayToDisplay(i, i2, surfaceControl, iAccessibilityInteractionConnectionCallback);
            this.mOverlays.add(surfaceControl);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void attachAccessibilityOverlayToWindow(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws android.os.RemoteException {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            transaction.setTrustedOverlay(surfaceControl, true).apply();
            transaction.close();
            synchronized (this.mLock) {
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = this.mA11yWindowManager.getConnectionLocked(this.mSystemSupport.getCurrentUserIdLocked(), resolveAccessibilityWindowIdLocked(i2));
                if (connectionLocked == null) {
                    iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(2, i);
                } else {
                    connectionLocked.getRemote().attachAccessibilityOverlayToWindow(surfaceControl, i, iAccessibilityInteractionConnectionCallback);
                    this.mOverlays.add(surfaceControl);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected void detachAllOverlays() {
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        for (android.view.SurfaceControl surfaceControl : this.mOverlays) {
            if (surfaceControl.isValid()) {
                transaction.reparent(surfaceControl, null);
            }
        }
        transaction.apply();
        transaction.close();
        this.mOverlays.clear();
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void connectBluetoothBrailleDisplay(java.lang.String str, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) {
        throw new java.lang.UnsupportedOperationException();
    }

    public void connectUsbBrailleDisplay(android.hardware.usb.UsbDevice usbDevice, android.accessibilityservice.IBrailleDisplayController iBrailleDisplayController) {
        throw new java.lang.UnsupportedOperationException();
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    public void setTestBrailleDisplayData(java.util.List<android.os.Bundle> list) {
        throw new java.lang.UnsupportedOperationException();
    }
}
