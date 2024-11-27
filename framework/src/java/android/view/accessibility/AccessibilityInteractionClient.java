package android.view.accessibility;

/* loaded from: classes4.dex */
public final class AccessibilityInteractionClient extends android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub {
    public static final java.lang.String CALL_STACK = "call_stack";
    private static final boolean CHECK_INTEGRITY = true;
    private static final boolean DEBUG = false;
    public static final java.lang.String IGNORE_CALL_STACK = "ignore_call_stack";
    private static final java.lang.String LOG_TAG = "AccessibilityInteractionClient";
    public static final int NO_ID = -1;
    private static final long TIMEOUT_INTERACTION_MILLIS = 5000;
    private final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private final android.util.SparseArray<android.util.Pair<java.util.concurrent.Executor, java.util.function.IntConsumer>> mAttachAccessibilityOverlayCallbacks;
    private java.util.List<java.lang.StackTraceElement> mCallStackOfCallback;
    private volatile int mCallingUid;
    private int mConnectionIdWaitingForPrefetchResult;
    private android.view.accessibility.AccessibilityNodeInfo mFindAccessibilityNodeInfoResult;
    private java.util.List<android.view.accessibility.AccessibilityNodeInfo> mFindAccessibilityNodeInfosResult;
    private final java.lang.Object mInstanceLock;
    private volatile int mInteractionId;
    private final java.util.concurrent.atomic.AtomicInteger mInteractionIdCounter;
    private int mInteractionIdWaitingForPrefetchResult;
    private android.os.Handler mMainHandler;
    private java.lang.String[] mPackageNamesForNextPrefetchResult;
    private boolean mPerformAccessibilityActionResult;
    private android.os.Message mSameThreadMessage;
    private final android.util.SparseArray<android.util.Pair<java.util.concurrent.Executor, android.accessibilityservice.AccessibilityService.TakeScreenshotCallback>> mTakeScreenshotOfWindowCallbacks;
    private static final long DISABLE_PREFETCHING_FOR_SCROLLING_MILLIS = (long) (android.view.ViewConfiguration.getSendRecurringAccessibilityEventsInterval() * 1.5d);
    private static final java.lang.Object sStaticLock = new java.lang.Object();
    private static final android.util.LongSparseArray<android.view.accessibility.AccessibilityInteractionClient> sClients = new android.util.LongSparseArray<>();
    private static final android.util.SparseArray<android.accessibilityservice.IAccessibilityServiceConnection> sConnectionCache = new android.util.SparseArray<>();
    private static int sDirectConnectionIdCounter = 1073741824;
    private static int sDirectConnectionCount = 0;
    private static final android.util.SparseLongArray sScrollingWindows = new android.util.SparseLongArray();
    private static android.util.SparseArray<android.view.accessibility.AccessibilityCache> sCaches = new android.util.SparseArray<>();

    public static android.view.accessibility.AccessibilityInteractionClient getInstance() {
        return getInstanceForThread(java.lang.Thread.currentThread().getId());
    }

    public static android.view.accessibility.AccessibilityInteractionClient getInstanceForThread(long j) {
        android.view.accessibility.AccessibilityInteractionClient accessibilityInteractionClient;
        synchronized (sStaticLock) {
            accessibilityInteractionClient = sClients.get(j);
            if (accessibilityInteractionClient == null) {
                accessibilityInteractionClient = new android.view.accessibility.AccessibilityInteractionClient();
                sClients.put(j, accessibilityInteractionClient);
            }
        }
        return accessibilityInteractionClient;
    }

    public static android.view.accessibility.AccessibilityInteractionClient getInstance(android.content.Context context) {
        long id = java.lang.Thread.currentThread().getId();
        if (context != null) {
            return getInstanceForThread(id, context);
        }
        return getInstanceForThread(id);
    }

    public static android.view.accessibility.AccessibilityInteractionClient getInstanceForThread(long j, android.content.Context context) {
        android.view.accessibility.AccessibilityInteractionClient accessibilityInteractionClient;
        synchronized (sStaticLock) {
            accessibilityInteractionClient = sClients.get(j);
            if (accessibilityInteractionClient == null) {
                accessibilityInteractionClient = new android.view.accessibility.AccessibilityInteractionClient(context);
                sClients.put(j, accessibilityInteractionClient);
            }
        }
        return accessibilityInteractionClient;
    }

    public static android.accessibilityservice.IAccessibilityServiceConnection getConnection(int i) {
        android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection;
        synchronized (sConnectionCache) {
            iAccessibilityServiceConnection = sConnectionCache.get(i);
        }
        return iAccessibilityServiceConnection;
    }

    public static void addConnection(int i, android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, boolean z) {
        if (i == -1) {
            return;
        }
        synchronized (sConnectionCache) {
            if (getConnection(i) instanceof android.view.accessibility.DirectAccessibilityConnection) {
                throw new java.lang.IllegalArgumentException("Cannot add service connection with id " + i + " which conflicts with existing direct connection.");
            }
            sConnectionCache.put(i, iAccessibilityServiceConnection);
            if (z) {
                sCaches.put(i, new android.view.accessibility.AccessibilityCache(new android.view.accessibility.AccessibilityCache.AccessibilityNodeRefresher()));
            }
        }
    }

    public static int addDirectConnection(android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, android.view.accessibility.AccessibilityManager accessibilityManager) {
        int i;
        synchronized (sConnectionCache) {
            i = sDirectConnectionIdCounter;
            sDirectConnectionIdCounter = i + 1;
            if (getConnection(i) != null) {
                throw new java.lang.IllegalArgumentException("Cannot add direct connection with existing id " + i);
            }
            sConnectionCache.put(i, new android.view.accessibility.DirectAccessibilityConnection(iAccessibilityInteractionConnection, accessibilityManager));
            sDirectConnectionCount++;
        }
        return i;
    }

    public static boolean hasAnyDirectConnection() {
        return sDirectConnectionCount > 0;
    }

    public static android.view.accessibility.AccessibilityCache getCache(int i) {
        android.view.accessibility.AccessibilityCache accessibilityCache;
        synchronized (sConnectionCache) {
            accessibilityCache = sCaches.get(i);
        }
        return accessibilityCache;
    }

    public static void removeConnection(int i) {
        synchronized (sConnectionCache) {
            if (getConnection(i) instanceof android.view.accessibility.DirectAccessibilityConnection) {
                sDirectConnectionCount--;
            }
            sConnectionCache.remove(i);
            sCaches.remove(i);
        }
    }

    public static void setCache(int i, android.view.accessibility.AccessibilityCache accessibilityCache) {
        synchronized (sConnectionCache) {
            sCaches.put(i, accessibilityCache);
        }
    }

    private AccessibilityInteractionClient() {
        this.mInteractionIdCounter = new java.util.concurrent.atomic.AtomicInteger();
        this.mInstanceLock = new java.lang.Object();
        this.mInteractionId = -1;
        this.mCallingUid = -1;
        this.mTakeScreenshotOfWindowCallbacks = new android.util.SparseArray<>();
        this.mAttachAccessibilityOverlayCallbacks = new android.util.SparseArray<>();
        this.mInteractionIdWaitingForPrefetchResult = -1;
        this.mMainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mAccessibilityManager = null;
    }

    private AccessibilityInteractionClient(android.content.Context context) {
        this.mInteractionIdCounter = new java.util.concurrent.atomic.AtomicInteger();
        this.mInstanceLock = new java.lang.Object();
        this.mInteractionId = -1;
        this.mCallingUid = -1;
        this.mTakeScreenshotOfWindowCallbacks = new android.util.SparseArray<>();
        this.mAttachAccessibilityOverlayCallbacks = new android.util.SparseArray<>();
        this.mInteractionIdWaitingForPrefetchResult = -1;
        this.mMainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mAccessibilityManager = (android.view.accessibility.AccessibilityManager) context.getSystemService(android.view.accessibility.AccessibilityManager.class);
    }

    public void setSameThreadMessage(android.os.Message message) {
        synchronized (this.mInstanceLock) {
            this.mSameThreadMessage = message;
            this.mInstanceLock.notifyAll();
        }
    }

    public android.view.accessibility.AccessibilityNodeInfo getRootInActiveWindow(int i, int i2) {
        return findAccessibilityNodeInfoByAccessibilityId(i, Integer.MAX_VALUE, android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID, false, i2, (android.os.Bundle) null);
    }

    public android.view.accessibility.AccessibilityWindowInfo getWindow(int i, int i2) {
        return getWindow(i, i2, false);
    }

    public android.view.accessibility.AccessibilityWindowInfo getWindow(int i, int i2, boolean z) {
        android.view.accessibility.AccessibilityWindowInfo window;
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                android.view.accessibility.AccessibilityCache cache = getCache(i);
                if (cache != null && !z && (window = cache.getWindow(i2)) != null) {
                    if (shouldTraceClient()) {
                        logTraceClient(connection, "getWindow cache", "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";bypassCache=false");
                    }
                    return window;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.view.accessibility.AccessibilityWindowInfo window2 = connection.getWindow(i2);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (shouldTraceClient()) {
                        logTraceClient(connection, "getWindow", "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";bypassCache=" + z);
                    }
                    if (window2 != null) {
                        if (!z && cache != null) {
                            cache.addWindow(window2);
                        }
                        return window2;
                    }
                    return null;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e("AccessibilityInteractionClient", "Error while calling remote getWindow", e);
            return null;
        }
    }

    public java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindows(int i) {
        return getWindowsOnDisplay(i, 0);
    }

    public java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindowsOnDisplay(int i, int i2) {
        return getWindowsOnAllDisplays(i).get(i2, java.util.Collections.emptyList());
    }

    public android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> getWindowsOnAllDisplays(int i) {
        android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> windowsOnAllDisplays;
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                android.view.accessibility.AccessibilityCache cache = getCache(i);
                if (cache != null && (windowsOnAllDisplays = cache.getWindowsOnAllDisplays()) != null) {
                    if (shouldTraceClient()) {
                        logTraceClient(connection, "getWindows cache", "connectionId=" + i);
                    }
                    return windowsOnAllDisplays;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    android.view.accessibility.AccessibilityWindowInfo.WindowListSparseArray windows = connection.getWindows();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (shouldTraceClient()) {
                        logTraceClient(connection, "getWindows", "connectionId=" + i);
                    }
                    if (windows != null) {
                        if (cache != null) {
                            cache.setWindowsOnAllDisplays(windows, uptimeMillis);
                        }
                        return windows;
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e("AccessibilityInteractionClient", "Error while calling remote getWindowsOnAllDisplays", e);
        }
        return new android.util.SparseArray<>();
    }

    public android.view.accessibility.AccessibilityNodeInfo findAccessibilityNodeInfoByAccessibilityId(int i, android.os.IBinder iBinder, long j, boolean z, int i2, android.os.Bundle bundle) {
        int i3;
        int i4;
        if (iBinder == null) {
            return null;
        }
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection == null) {
                i4 = -1;
            } else {
                i4 = connection.getWindowIdForLeashToken(iBinder);
            }
            i3 = i4;
        } catch (android.os.RemoteException e) {
            android.util.Log.e("AccessibilityInteractionClient", "Error while calling remote getWindowIdForLeashToken", e);
            i3 = -1;
        }
        if (i3 == -1) {
            return null;
        }
        return findAccessibilityNodeInfoByAccessibilityId(i, i3, j, z, i2, bundle);
    }

    public android.view.accessibility.AccessibilityNodeInfo findAccessibilityNodeInfoByAccessibilityId(int i, int i2, long j, boolean z, int i3, android.os.Bundle bundle) {
        int i4;
        java.lang.String str;
        java.lang.String str2;
        android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection;
        android.view.accessibility.AccessibilityInteractionClient accessibilityInteractionClient;
        int i5 = i3;
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                if (!z) {
                    android.view.accessibility.AccessibilityCache cache = getCache(i);
                    if (cache != null) {
                        android.view.accessibility.AccessibilityNodeInfo node = cache.getNode(i2, j);
                        if (node != null) {
                            if (shouldTraceClient()) {
                                logTraceClient(connection, "findAccessibilityNodeInfoByAccessibilityId cache", "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";bypassCache=" + z + ";prefetchFlags=" + i5 + ";arguments=" + bundle);
                            }
                            return node;
                        }
                        if (!cache.isEnabled()) {
                            i5 &= -64;
                        }
                    }
                } else {
                    i5 &= -64;
                }
                if ((i5 & 63) != 0 && isWindowScrolling(i2)) {
                    i4 = i5 & (-64);
                } else {
                    i4 = i5;
                }
                int i6 = i4 & 28;
                if ((i6 & (i6 - 1)) != 0) {
                    throw new java.lang.IllegalArgumentException("There can be no more than one descendant prefetching strategy");
                }
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                if (shouldTraceClient()) {
                    str2 = "findAccessibilityNodeInfoByAccessibilityId";
                    str = "InteractionId:";
                    iAccessibilityServiceConnection = connection;
                    accessibilityInteractionClient = this;
                    accessibilityInteractionClient.logTraceClient(iAccessibilityServiceConnection, str2, "InteractionId:" + andIncrement + "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";bypassCache=" + z + ";prefetchFlags=" + i4 + ";arguments=" + bundle);
                } else {
                    str = "InteractionId:";
                    str2 = "findAccessibilityNodeInfoByAccessibilityId";
                    iAccessibilityServiceConnection = connection;
                    accessibilityInteractionClient = this;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.String str3 = str2;
                    java.lang.String[] findAccessibilityNodeInfoByAccessibilityId = iAccessibilityServiceConnection.findAccessibilityNodeInfoByAccessibilityId(i2, j, andIncrement, this, i4, java.lang.Thread.currentThread().getId(), bundle);
                    if (findAccessibilityNodeInfoByAccessibilityId != null) {
                        if ((i4 & 32) != 0) {
                            java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosResultAndClear = accessibilityInteractionClient.getFindAccessibilityNodeInfosResultAndClear(andIncrement);
                            if (shouldTraceCallback()) {
                                accessibilityInteractionClient.logTraceCallback(iAccessibilityServiceConnection, str3, str + andIncrement + ";connectionId=" + i + ";Result: " + findAccessibilityNodeInfosResultAndClear);
                            }
                            accessibilityInteractionClient.finalizeAndCacheAccessibilityNodeInfos(findAccessibilityNodeInfosResultAndClear, i, z, findAccessibilityNodeInfoByAccessibilityId);
                            if (findAccessibilityNodeInfosResultAndClear != null && !findAccessibilityNodeInfosResultAndClear.isEmpty()) {
                                return findAccessibilityNodeInfosResultAndClear.get(0);
                            }
                            return null;
                        }
                        android.view.accessibility.AccessibilityNodeInfo findAccessibilityNodeInfoResultAndClear = accessibilityInteractionClient.getFindAccessibilityNodeInfoResultAndClear(andIncrement);
                        if (shouldTraceCallback()) {
                            accessibilityInteractionClient.logTraceCallback(iAccessibilityServiceConnection, str3, str + andIncrement + ";connectionId=" + i + ";Result: " + findAccessibilityNodeInfoResultAndClear);
                        }
                        if ((i4 & 63) != 0 && findAccessibilityNodeInfoResultAndClear != null) {
                            accessibilityInteractionClient.setInteractionWaitingForPrefetchResult(andIncrement, i, findAccessibilityNodeInfoByAccessibilityId);
                        }
                        accessibilityInteractionClient.finalizeAndCacheAccessibilityNodeInfo(findAccessibilityNodeInfoResultAndClear, i, z, findAccessibilityNodeInfoByAccessibilityId);
                        return findAccessibilityNodeInfoResultAndClear;
                    }
                    return null;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e("AccessibilityInteractionClient", "Error while calling remote findAccessibilityNodeInfoByAccessibilityId", e);
            return null;
        }
    }

    private void setInteractionWaitingForPrefetchResult(int i, int i2, java.lang.String[] strArr) {
        synchronized (this.mInstanceLock) {
            this.mInteractionIdWaitingForPrefetchResult = i;
            this.mConnectionIdWaitingForPrefetchResult = i2;
            this.mPackageNamesForNextPrefetchResult = strArr;
        }
    }

    private static java.lang.String idToString(int i, long j) {
        return i + "/" + android.view.accessibility.AccessibilityNodeInfo.idToString(j);
    }

    public java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(int i, int i2, long j, java.lang.String str) {
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (shouldTraceClient()) {
                        logTraceClient(connection, "findAccessibilityNodeInfosByViewId", "InteractionId=" + andIncrement + ";connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";viewId=" + str);
                    }
                    java.lang.String[] findAccessibilityNodeInfosByViewId = connection.findAccessibilityNodeInfosByViewId(i2, j, str, andIncrement, this, java.lang.Thread.currentThread().getId());
                    if (findAccessibilityNodeInfosByViewId != null) {
                        java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosResultAndClear = getFindAccessibilityNodeInfosResultAndClear(andIncrement);
                        if (shouldTraceCallback()) {
                            logTraceCallback(connection, "findAccessibilityNodeInfosByViewId", "InteractionId=" + andIncrement + ";connectionId=" + i + ":Result: " + findAccessibilityNodeInfosResultAndClear);
                        }
                        if (findAccessibilityNodeInfosResultAndClear != null) {
                            finalizeAndCacheAccessibilityNodeInfos(findAccessibilityNodeInfosResultAndClear, i, false, findAccessibilityNodeInfosByViewId);
                            return findAccessibilityNodeInfosResultAndClear;
                        }
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w("AccessibilityInteractionClient", "Error while calling remote findAccessibilityNodeInfoByViewIdInActiveWindow", e);
        }
        return java.util.Collections.emptyList();
    }

    public void takeScreenshotOfWindow(int i, int i2, java.util.concurrent.Executor executor, final android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback) {
        android.accessibilityservice.IAccessibilityServiceConnection connection;
        synchronized (this.mInstanceLock) {
            try {
                connection = getConnection(i);
            } catch (android.os.RemoteException e) {
                executor.execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.AccessibilityService.TakeScreenshotCallback.this.onFailure(1);
                    }
                });
            }
            if (connection == null) {
                executor.execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.AccessibilityService.TakeScreenshotCallback.this.onFailure(1);
                    }
                });
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                final int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                this.mTakeScreenshotOfWindowCallbacks.put(andIncrement, android.util.Pair.create(executor, takeScreenshotCallback));
                connection.takeScreenshotOfWindow(i2, andIncrement, new android.window.ScreenCapture.ScreenCaptureListener((java.util.function.ObjIntConsumer<android.window.ScreenCapture.ScreenshotHardwareBuffer>) new java.util.function.ObjIntConsumer() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda2
                    @Override // java.util.function.ObjIntConsumer
                    public final void accept(java.lang.Object obj, int i3) {
                        android.view.accessibility.AccessibilityInteractionClient.this.lambda$takeScreenshotOfWindow$1(andIncrement, (android.window.ScreenCapture.ScreenshotHardwareBuffer) obj, i3);
                    }
                }), this);
                this.mMainHandler.postDelayed(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.accessibility.AccessibilityInteractionClient.this.lambda$takeScreenshotOfWindow$2(andIncrement);
                    }
                }, 5000L);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotOfWindow$1(int i, android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, int i2) {
        if (i2 != 0) {
            sendTakeScreenshotOfWindowError(1, i);
        } else {
            sendWindowScreenshotSuccess(screenshotHardwareBuffer, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotOfWindow$2(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mTakeScreenshotOfWindowCallbacks.contains(i)) {
                sendTakeScreenshotOfWindowError(1, i);
            }
        }
    }

    public java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosByText(int i, int i2, long j, java.lang.String str) {
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                if (shouldTraceClient()) {
                    logTraceClient(connection, "findAccessibilityNodeInfosByText", "InteractionId:" + andIncrement + "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";text=" + str);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.String[] findAccessibilityNodeInfosByText = connection.findAccessibilityNodeInfosByText(i2, j, str, andIncrement, this, java.lang.Thread.currentThread().getId());
                    if (findAccessibilityNodeInfosByText != null) {
                        java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosResultAndClear = getFindAccessibilityNodeInfosResultAndClear(andIncrement);
                        if (shouldTraceCallback()) {
                            logTraceCallback(connection, "findAccessibilityNodeInfosByText", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result: " + findAccessibilityNodeInfosResultAndClear);
                        }
                        if (findAccessibilityNodeInfosResultAndClear != null) {
                            finalizeAndCacheAccessibilityNodeInfos(findAccessibilityNodeInfosResultAndClear, i, false, findAccessibilityNodeInfosByText);
                            return findAccessibilityNodeInfosResultAndClear;
                        }
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w("AccessibilityInteractionClient", "Error while calling remote findAccessibilityNodeInfosByViewText", e);
        }
        return java.util.Collections.emptyList();
    }

    public android.view.accessibility.AccessibilityNodeInfo findFocus(int i, int i2, long j, int i3) {
        android.view.accessibility.AccessibilityNodeInfo focus;
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                android.view.accessibility.AccessibilityCache cache = getCache(i);
                if (cache != null && (focus = cache.getFocus(i3, j, i2)) != null) {
                    return focus;
                }
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                if (shouldTraceClient()) {
                    logTraceClient(connection, "findFocus", "InteractionId:" + andIncrement + "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";focusType=" + i3);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.String[] findFocus = connection.findFocus(i2, j, i3, andIncrement, this, java.lang.Thread.currentThread().getId());
                    if (findFocus != null) {
                        android.view.accessibility.AccessibilityNodeInfo findAccessibilityNodeInfoResultAndClear = getFindAccessibilityNodeInfoResultAndClear(andIncrement);
                        if (shouldTraceCallback()) {
                            logTraceCallback(connection, "findFocus", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result:" + findAccessibilityNodeInfoResultAndClear);
                        }
                        finalizeAndCacheAccessibilityNodeInfo(findAccessibilityNodeInfoResultAndClear, i, false, findFocus);
                        return findAccessibilityNodeInfoResultAndClear;
                    }
                    return null;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.w("AccessibilityInteractionClient", "Error while calling remote findFocus", e);
            return null;
        }
    }

    public android.view.accessibility.AccessibilityNodeInfo focusSearch(int i, int i2, long j, int i3) {
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                if (shouldTraceClient()) {
                    logTraceClient(connection, "focusSearch", "InteractionId:" + andIncrement + "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";direction=" + i3);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.lang.String[] focusSearch = connection.focusSearch(i2, j, i3, andIncrement, this, java.lang.Thread.currentThread().getId());
                    if (focusSearch != null) {
                        android.view.accessibility.AccessibilityNodeInfo findAccessibilityNodeInfoResultAndClear = getFindAccessibilityNodeInfoResultAndClear(andIncrement);
                        finalizeAndCacheAccessibilityNodeInfo(findAccessibilityNodeInfoResultAndClear, i, false, focusSearch);
                        if (shouldTraceCallback()) {
                            logTraceCallback(connection, "focusSearch", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result:" + findAccessibilityNodeInfoResultAndClear);
                        }
                        return findAccessibilityNodeInfoResultAndClear;
                    }
                    return null;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.w("AccessibilityInteractionClient", "Error while calling remote accessibilityFocusSearch", e);
            return null;
        }
    }

    public boolean performAccessibilityAction(int i, int i2, long j, int i3, android.os.Bundle bundle) {
        try {
            android.accessibilityservice.IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                if (shouldTraceClient()) {
                    logTraceClient(connection, "performAccessibilityAction", "InteractionId:" + andIncrement + "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";action=" + i3 + ";arguments=" + bundle);
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (connection.performAccessibilityAction(i2, j, i3, bundle, andIncrement, this, java.lang.Thread.currentThread().getId())) {
                        boolean performAccessibilityActionResultAndClear = getPerformAccessibilityActionResultAndClear(andIncrement);
                        if (shouldTraceCallback()) {
                            logTraceCallback(connection, "performAccessibilityAction", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result: " + performAccessibilityActionResultAndClear);
                        }
                        return performAccessibilityActionResultAndClear;
                    }
                    return false;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.w("AccessibilityInteractionClient", "Error while calling remote performAccessibilityAction", e);
            return false;
        }
    }

    public void clearCache(int i) {
        android.view.accessibility.AccessibilityCache cache = getCache(i);
        if (cache == null) {
            return;
        }
        cache.clear();
    }

    public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, int i) {
        switch (accessibilityEvent.getEventType()) {
            case 4096:
                updateScrollingWindow(accessibilityEvent.getWindowId(), android.os.SystemClock.uptimeMillis());
                break;
            case 4194304:
                if (accessibilityEvent.getWindowChanges() == 2) {
                    deleteScrollingWindow(accessibilityEvent.getWindowId());
                    break;
                }
                break;
        }
        android.view.accessibility.AccessibilityCache cache = getCache(i);
        if (cache == null) {
            return;
        }
        cache.onAccessibilityEvent(accessibilityEvent);
    }

    private android.view.accessibility.AccessibilityNodeInfo getFindAccessibilityNodeInfoResultAndClear(int i) {
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        synchronized (this.mInstanceLock) {
            accessibilityNodeInfo = waitForResultTimedLocked(i) ? this.mFindAccessibilityNodeInfoResult : null;
            clearResultLocked();
        }
        return accessibilityNodeInfo;
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setFindAccessibilityNodeInfoResult(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        synchronized (this.mInstanceLock) {
            if (i > this.mInteractionId) {
                this.mFindAccessibilityNodeInfoResult = accessibilityNodeInfo;
                this.mInteractionId = i;
                this.mCallingUid = android.os.Binder.getCallingUid();
                this.mCallStackOfCallback = new java.util.ArrayList(java.util.Arrays.asList(java.lang.Thread.currentThread().getStackTrace()));
            }
            this.mInstanceLock.notifyAll();
        }
    }

    private java.util.List<android.view.accessibility.AccessibilityNodeInfo> getFindAccessibilityNodeInfosResultAndClear(int i) {
        java.util.List<android.view.accessibility.AccessibilityNodeInfo> emptyList;
        synchronized (this.mInstanceLock) {
            if (waitForResultTimedLocked(i)) {
                emptyList = this.mFindAccessibilityNodeInfosResult;
            } else {
                emptyList = java.util.Collections.emptyList();
            }
            clearResultLocked();
            if (android.os.Build.IS_DEBUGGABLE) {
                checkFindAccessibilityNodeInfoResultIntegrity(emptyList);
            }
        }
        return emptyList;
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setFindAccessibilityNodeInfosResult(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, int i) {
        synchronized (this.mInstanceLock) {
            if (i > this.mInteractionId) {
                if (list != null) {
                    if (!(android.os.Binder.getCallingPid() != android.os.Process.myPid())) {
                        this.mFindAccessibilityNodeInfosResult = new java.util.ArrayList(list);
                    } else {
                        this.mFindAccessibilityNodeInfosResult = list;
                    }
                } else {
                    this.mFindAccessibilityNodeInfosResult = java.util.Collections.emptyList();
                }
                this.mInteractionId = i;
                this.mCallingUid = android.os.Binder.getCallingUid();
                this.mCallStackOfCallback = new java.util.ArrayList(java.util.Arrays.asList(java.lang.Thread.currentThread().getStackTrace()));
            }
            this.mInstanceLock.notifyAll();
        }
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setPrefetchAccessibilityNodeInfoResult(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, int i) {
        java.lang.String[] strArr;
        int i2;
        int i3;
        if (list.isEmpty()) {
            return;
        }
        synchronized (this.mInstanceLock) {
            strArr = null;
            if (this.mInteractionIdWaitingForPrefetchResult != i) {
                i2 = -1;
                i3 = -1;
            } else {
                i2 = this.mInteractionIdWaitingForPrefetchResult;
                i3 = this.mConnectionIdWaitingForPrefetchResult;
                if (this.mPackageNamesForNextPrefetchResult != null) {
                    strArr = new java.lang.String[this.mPackageNamesForNextPrefetchResult.length];
                    for (int i4 = 0; i4 < this.mPackageNamesForNextPrefetchResult.length; i4++) {
                        strArr[i4] = this.mPackageNamesForNextPrefetchResult[i4];
                    }
                }
            }
        }
        if (i2 == i) {
            finalizeAndCacheAccessibilityNodeInfos(list, i3, false, strArr);
            if (shouldTraceCallback()) {
                logTrace(getConnection(i3), "setPrefetchAccessibilityNodeInfoResult", "InteractionId:" + i + ";connectionId=" + i3 + ";Result: " + list, android.os.Binder.getCallingUid(), java.util.Arrays.asList(java.lang.Thread.currentThread().getStackTrace()), new java.util.HashSet<>(java.util.Collections.singletonList("getStackTrace")), 32L);
            }
        }
    }

    private boolean getPerformAccessibilityActionResultAndClear(int i) {
        boolean z;
        synchronized (this.mInstanceLock) {
            z = waitForResultTimedLocked(i) ? this.mPerformAccessibilityActionResult : false;
            clearResultLocked();
        }
        return z;
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setPerformAccessibilityActionResult(boolean z, int i) {
        synchronized (this.mInstanceLock) {
            if (i > this.mInteractionId) {
                this.mPerformAccessibilityActionResult = z;
                this.mInteractionId = i;
                this.mCallingUid = android.os.Binder.getCallingUid();
                this.mCallStackOfCallback = new java.util.ArrayList(java.util.Arrays.asList(java.lang.Thread.currentThread().getStackTrace()));
            }
            this.mInstanceLock.notifyAll();
        }
    }

    private void sendWindowScreenshotSuccess(android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, int i) {
        if (screenshotHardwareBuffer == null) {
            sendTakeScreenshotOfWindowError(1, i);
            return;
        }
        synchronized (this.mInstanceLock) {
            if (this.mTakeScreenshotOfWindowCallbacks.contains(i)) {
                final android.accessibilityservice.AccessibilityService.ScreenshotResult screenshotResult = new android.accessibilityservice.AccessibilityService.ScreenshotResult(screenshotHardwareBuffer.getHardwareBuffer(), screenshotHardwareBuffer.getColorSpace(), android.os.SystemClock.uptimeMillis());
                android.util.Pair<java.util.concurrent.Executor, android.accessibilityservice.AccessibilityService.TakeScreenshotCallback> pair = this.mTakeScreenshotOfWindowCallbacks.get(i);
                java.util.concurrent.Executor executor = pair.first;
                final android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback = pair.second;
                executor.execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.AccessibilityService.TakeScreenshotCallback.this.onSuccess(screenshotResult);
                    }
                });
                this.mTakeScreenshotOfWindowCallbacks.remove(i);
            }
        }
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void sendTakeScreenshotOfWindowError(final int i, int i2) {
        synchronized (this.mInstanceLock) {
            if (this.mTakeScreenshotOfWindowCallbacks.contains(i2)) {
                android.util.Pair<java.util.concurrent.Executor, android.accessibilityservice.AccessibilityService.TakeScreenshotCallback> pair = this.mTakeScreenshotOfWindowCallbacks.get(i2);
                java.util.concurrent.Executor executor = pair.first;
                final android.accessibilityservice.AccessibilityService.TakeScreenshotCallback takeScreenshotCallback = pair.second;
                executor.execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.accessibilityservice.AccessibilityService.TakeScreenshotCallback.this.onFailure(i);
                    }
                });
                this.mTakeScreenshotOfWindowCallbacks.remove(i2);
            }
        }
    }

    private void clearResultLocked() {
        this.mInteractionId = -1;
        this.mFindAccessibilityNodeInfoResult = null;
        this.mFindAccessibilityNodeInfosResult = null;
        this.mPerformAccessibilityActionResult = false;
    }

    private boolean waitForResultTimedLocked(int i) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        while (true) {
            try {
                android.os.Message sameProcessMessageAndClear = getSameProcessMessageAndClear();
                if (sameProcessMessageAndClear != null) {
                    sameProcessMessageAndClear.getTarget().handleMessage(sameProcessMessageAndClear);
                }
            } catch (java.lang.InterruptedException e) {
            }
            if (this.mInteractionId == i) {
                return true;
            }
            if (this.mInteractionId > i) {
                return false;
            }
            long uptimeMillis2 = 5000 - (android.os.SystemClock.uptimeMillis() - uptimeMillis);
            if (uptimeMillis2 <= 0) {
                return false;
            }
            this.mInstanceLock.wait(uptimeMillis2);
        }
    }

    private void finalizeAndCacheAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, int i, boolean z, java.lang.String[] strArr) {
        android.view.accessibility.AccessibilityCache cache;
        java.lang.CharSequence packageName;
        if (accessibilityNodeInfo != null) {
            accessibilityNodeInfo.setConnectionId(i);
            if (!com.android.internal.util.ArrayUtils.isEmpty(strArr) && ((packageName = accessibilityNodeInfo.getPackageName()) == null || !com.android.internal.util.ArrayUtils.contains(strArr, packageName.toString()))) {
                accessibilityNodeInfo.setPackageName(strArr[0]);
            }
            accessibilityNodeInfo.setSealed(true);
            if (z || (cache = getCache(i)) == null) {
                return;
            }
            cache.add(accessibilityNodeInfo);
        }
    }

    private void finalizeAndCacheAccessibilityNodeInfos(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, int i, boolean z, java.lang.String[] strArr) {
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                finalizeAndCacheAccessibilityNodeInfo(list.get(i2), i, z, strArr);
            }
        }
    }

    private android.os.Message getSameProcessMessageAndClear() {
        android.os.Message message;
        synchronized (this.mInstanceLock) {
            message = this.mSameThreadMessage;
            this.mSameThreadMessage = null;
        }
        return message;
    }

    private void checkFindAccessibilityNodeInfoResultIntegrity(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
        if (list.size() == 0) {
            return;
        }
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = list.get(0);
        int size = list.size();
        for (int i = 1; i < size; i++) {
            int i2 = i;
            while (true) {
                if (i2 < size) {
                    android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2 = list.get(i2);
                    if (accessibilityNodeInfo.getParentNodeId() != accessibilityNodeInfo2.getSourceNodeId()) {
                        i2++;
                    } else {
                        accessibilityNodeInfo = accessibilityNodeInfo2;
                        break;
                    }
                }
            }
        }
        if (accessibilityNodeInfo == null) {
            android.util.Log.e("AccessibilityInteractionClient", "No root.");
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        arrayDeque.add(accessibilityNodeInfo);
        while (!arrayDeque.isEmpty()) {
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo3 = (android.view.accessibility.AccessibilityNodeInfo) arrayDeque.poll();
            if (!hashSet.add(accessibilityNodeInfo3)) {
                android.util.Log.e("AccessibilityInteractionClient", "Duplicate node.");
                return;
            }
            int childCount = accessibilityNodeInfo3.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                long childId = accessibilityNodeInfo3.getChildId(i3);
                for (int i4 = 0; i4 < size; i4++) {
                    android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo4 = list.get(i4);
                    if (accessibilityNodeInfo4.getSourceNodeId() == childId) {
                        arrayDeque.add(accessibilityNodeInfo4);
                    }
                }
            }
        }
        int size2 = list.size() - hashSet.size();
        if (size2 > 0) {
            android.util.Log.e("AccessibilityInteractionClient", size2 + " Disconnected nodes.");
        }
    }

    private void updateScrollingWindow(int i, long j) {
        synchronized (sScrollingWindows) {
            sScrollingWindows.put(i, j);
        }
    }

    private void deleteScrollingWindow(int i) {
        synchronized (sScrollingWindows) {
            sScrollingWindows.delete(i);
        }
    }

    private boolean isWindowScrolling(int i) {
        synchronized (sScrollingWindows) {
            long j = sScrollingWindows.get(i);
            if (j == 0) {
                return false;
            }
            if (android.os.SystemClock.uptimeMillis() > j + DISABLE_PREFETCHING_FOR_SCROLLING_MILLIS) {
                sScrollingWindows.delete(i);
                return false;
            }
            return true;
        }
    }

    private boolean shouldTraceClient() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isA11yInteractionClientTraceEnabled();
    }

    private boolean shouldTraceCallback() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isA11yInteractionConnectionCBTraceEnabled();
    }

    private void logTrace(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, java.lang.String str, java.lang.String str2, int i, java.util.List<java.lang.StackTraceElement> list, java.util.HashSet<java.lang.String> hashSet, long j) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putSerializable(CALL_STACK, new java.util.ArrayList(list));
            if (hashSet != null) {
                bundle.putSerializable(IGNORE_CALL_STACK, hashSet);
            }
            iAccessibilityServiceConnection.logTrace(android.os.SystemClock.elapsedRealtimeNanos(), "AccessibilityInteractionClient." + str, j, str2, android.os.Process.myPid(), java.lang.Thread.currentThread().getId(), i, bundle);
        } catch (android.os.RemoteException e) {
            android.util.Log.e("AccessibilityInteractionClient", "Failed to log trace. " + e);
        }
    }

    private void logTraceCallback(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, java.lang.String str, java.lang.String str2) {
        logTrace(iAccessibilityServiceConnection, str + " callback", str2, this.mCallingUid, this.mCallStackOfCallback, new java.util.HashSet<>(java.util.Arrays.asList("getStackTrace")), 32L);
    }

    private void logTraceClient(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, java.lang.String str, java.lang.String str2) {
        logTrace(iAccessibilityServiceConnection, str, str2, android.os.Binder.getCallingUid(), java.util.Arrays.asList(java.lang.Thread.currentThread().getStackTrace()), new java.util.HashSet<>(java.util.Arrays.asList("getStackTrace", "logTraceClient")), 262144L);
    }

    public void attachAccessibilityOverlayToWindow(int i, int i2, android.view.SurfaceControl surfaceControl, java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        android.accessibilityservice.IAccessibilityServiceConnection connection;
        synchronized (this.mInstanceLock) {
            try {
                connection = getConnection(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (connection == null) {
                executor.execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        intConsumer.accept(1);
                    }
                });
                return;
            }
            final int andIncrement = this.mInteractionIdCounter.getAndIncrement();
            this.mAttachAccessibilityOverlayCallbacks.put(andIncrement, android.util.Pair.create(executor, intConsumer));
            connection.attachAccessibilityOverlayToWindow(andIncrement, i2, surfaceControl, this);
            this.mMainHandler.postDelayed(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.accessibility.AccessibilityInteractionClient.this.lambda$attachAccessibilityOverlayToWindow$7(andIncrement);
                }
            }, 5000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachAccessibilityOverlayToWindow$7(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mAttachAccessibilityOverlayCallbacks.contains(i)) {
                sendAttachOverlayResult(1, i);
            }
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        android.accessibilityservice.IAccessibilityServiceConnection connection;
        synchronized (this.mInstanceLock) {
            try {
                connection = getConnection(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (connection == null) {
                executor.execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        intConsumer.accept(1);
                    }
                });
                return;
            }
            final int andIncrement = this.mInteractionIdCounter.getAndIncrement();
            this.mAttachAccessibilityOverlayCallbacks.put(andIncrement, android.util.Pair.create(executor, intConsumer));
            connection.attachAccessibilityOverlayToDisplay(andIncrement, i2, surfaceControl, this);
            this.mMainHandler.postDelayed(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.accessibility.AccessibilityInteractionClient.this.lambda$attachAccessibilityOverlayToDisplay$9(andIncrement);
                }
            }, 5000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachAccessibilityOverlayToDisplay$9(int i) {
        if (this.mAttachAccessibilityOverlayCallbacks.contains(i)) {
            sendAttachOverlayResult(1, i);
        }
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void sendAttachOverlayResult(final int i, int i2) {
        if (!android.view.accessibility.Flags.a11yOverlayCallbacks()) {
            return;
        }
        synchronized (this.mInstanceLock) {
            if (this.mAttachAccessibilityOverlayCallbacks.contains(i2)) {
                android.util.Pair<java.util.concurrent.Executor, java.util.function.IntConsumer> pair = this.mAttachAccessibilityOverlayCallbacks.get(i2);
                if (pair == null) {
                    return;
                }
                java.util.concurrent.Executor executor = pair.first;
                final java.util.function.IntConsumer intConsumer = pair.second;
                if (executor != null && intConsumer != null) {
                    executor.execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            intConsumer.accept(i);
                        }
                    });
                    this.mAttachAccessibilityOverlayCallbacks.remove(i2);
                }
            }
        }
    }
}
