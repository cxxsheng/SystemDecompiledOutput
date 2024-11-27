package android.app;

/* loaded from: classes.dex */
public final class UiAutomation {
    private static final int CONNECTION_ID_UNDEFINED = -1;
    private static final long CONNECT_TIMEOUT_MILLIS = 5000;
    private static final boolean DEBUG = false;
    public static final int FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES = 1;
    public static final int FLAG_DONT_USE_ACCESSIBILITY = 2;
    public static final int FLAG_NOT_ACCESSIBILITY_TOOL = 4;
    public static final int ROTATION_FREEZE_0 = 0;
    public static final int ROTATION_FREEZE_180 = 2;
    public static final int ROTATION_FREEZE_270 = 3;
    public static final int ROTATION_FREEZE_90 = 1;
    public static final int ROTATION_FREEZE_CURRENT = -1;
    public static final int ROTATION_UNFREEZE = -2;
    private static final boolean VERBOSE = false;
    private android.accessibilityservice.IAccessibilityServiceClient mClient;
    private int mConnectionId;
    private int mConnectionState;
    private final int mDisplayId;
    private final java.util.ArrayList<android.view.accessibility.AccessibilityEvent> mEventQueue;
    private int mFlags;
    private int mGenerationId;
    private boolean mIsDestroyed;
    private long mLastEventTimeMillis;
    private final android.os.Handler mLocalCallbackHandler;
    private final java.lang.Object mLock;
    private android.app.UiAutomation.OnAccessibilityEventListener mOnAccessibilityEventListener;
    private android.os.HandlerThread mRemoteCallbackThread;
    private final android.app.IUiAutomationConnection mUiAutomationConnection;
    private boolean mWaitingForEventDelivery;
    private static final java.lang.String LOG_TAG = android.app.UiAutomation.class.getSimpleName();
    public static final java.util.Set<java.lang.String> ALL_PERMISSIONS = java.util.Set.of("_ALL_PERMISSIONS_");

    public interface AccessibilityEventFilter {
        boolean accept(android.view.accessibility.AccessibilityEvent accessibilityEvent);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ConnectionState {
        public static final int CONNECTED = 2;
        public static final int CONNECTING = 1;
        public static final int DISCONNECTED = 0;
        public static final int FAILED = 3;
    }

    public interface OnAccessibilityEventListener {
        void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);
    }

    public UiAutomation(android.content.Context context, android.app.IUiAutomationConnection iUiAutomationConnection) {
        this(getDisplayId(context), context.getMainLooper(), iUiAutomationConnection);
    }

    @java.lang.Deprecated
    public UiAutomation(android.os.Looper looper, android.app.IUiAutomationConnection iUiAutomationConnection) {
        this(0, looper, iUiAutomationConnection);
        android.util.Log.w(LOG_TAG, "Created with deprecatead constructor, assumes DEFAULT_DISPLAY");
    }

    private UiAutomation(int i, android.os.Looper looper, android.app.IUiAutomationConnection iUiAutomationConnection) {
        boolean z;
        this.mLock = new java.lang.Object();
        this.mEventQueue = new java.util.ArrayList<>();
        this.mConnectionId = -1;
        this.mConnectionState = 0;
        this.mGenerationId = 0;
        if (looper == null) {
            z = false;
        } else {
            z = true;
        }
        com.android.internal.util.Preconditions.checkArgument(z, "Looper cannot be null!");
        com.android.internal.util.Preconditions.checkArgument(iUiAutomationConnection != null, "Connection cannot be null!");
        this.mLocalCallbackHandler = new android.os.Handler(looper);
        this.mUiAutomationConnection = iUiAutomationConnection;
        this.mDisplayId = i;
        android.util.Log.i(LOG_TAG, "Initialized for user " + android.os.Process.myUserHandle().getIdentifier() + " on display " + this.mDisplayId);
    }

    public void connect() {
        try {
            connectWithTimeout(0, 5000L);
        } catch (java.util.concurrent.TimeoutException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void connect(int i) {
        try {
            connectWithTimeout(i, 5000L);
        } catch (java.util.concurrent.TimeoutException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public void connectWithTimeout(int i, long j) throws java.util.concurrent.TimeoutException {
        synchronized (this.mLock) {
            throwIfConnectedLocked();
            if (this.mConnectionState == 1) {
                return;
            }
            this.mConnectionState = 1;
            this.mRemoteCallbackThread = new android.os.HandlerThread("UiAutomation");
            this.mRemoteCallbackThread.start();
            android.os.Looper looper = this.mRemoteCallbackThread.getLooper();
            int i2 = this.mGenerationId + 1;
            this.mGenerationId = i2;
            this.mClient = new android.app.UiAutomation.IAccessibilityServiceClientImpl(looper, i2);
            try {
                this.mUiAutomationConnection.connect(this.mClient, i);
                this.mFlags = i;
                if (!useAccessibility()) {
                    this.mConnectionState = 0;
                    return;
                }
                synchronized (this.mLock) {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    while (this.mConnectionState != 2) {
                        long uptimeMillis2 = j - (android.os.SystemClock.uptimeMillis() - uptimeMillis);
                        if (uptimeMillis2 <= 0) {
                            this.mConnectionState = 3;
                            throw new java.util.concurrent.TimeoutException("Timeout while connecting " + this);
                        }
                        try {
                            this.mLock.wait(uptimeMillis2);
                        } catch (java.lang.InterruptedException e) {
                        }
                    }
                }
            } catch (android.os.RemoteException e2) {
                throw new java.lang.RuntimeException("Error while connecting " + this, e2);
            }
        }
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void disconnect() {
        synchronized (this.mLock) {
            if (this.mConnectionState == 1) {
                throw new java.lang.IllegalStateException("Cannot call disconnect() while connecting " + this);
            }
            if (useAccessibility() && this.mConnectionState == 0) {
                return;
            }
            this.mConnectionState = 0;
            this.mConnectionId = -1;
            this.mGenerationId++;
            try {
                try {
                    this.mUiAutomationConnection.disconnect();
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException("Error while disconnecting " + this, e);
                }
            } finally {
                if (this.mRemoteCallbackThread != null) {
                    this.mRemoteCallbackThread.quit();
                    this.mRemoteCallbackThread = null;
                }
            }
        }
    }

    public int getConnectionId() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return i;
    }

    public boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    public void setOnAccessibilityEventListener(android.app.UiAutomation.OnAccessibilityEventListener onAccessibilityEventListener) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            this.mOnAccessibilityEventListener = onAccessibilityEventListener;
        }
    }

    public void destroy() {
        disconnect();
        this.mIsDestroyed = true;
    }

    public boolean clearCache() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        android.view.accessibility.AccessibilityCache cache = android.view.accessibility.AccessibilityInteractionClient.getCache(i);
        if (cache == null) {
            return false;
        }
        cache.clear();
        return true;
    }

    public boolean isNodeInCache(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        android.view.accessibility.AccessibilityCache cache = android.view.accessibility.AccessibilityInteractionClient.getCache(i);
        if (cache == null) {
            return false;
        }
        return cache.isNodeInCache(accessibilityNodeInfo);
    }

    public android.view.accessibility.AccessibilityCache getCache() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getCache(i);
    }

    public void adoptShellPermissionIdentity() {
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(android.os.Process.myUid(), null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void adoptShellPermissionIdentity(java.lang.String... strArr) {
        try {
            this.mUiAutomationConnection.adoptShellPermissionIdentity(android.os.Process.myUid(), strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void dropShellPermissionIdentity() {
        try {
            this.mUiAutomationConnection.dropShellPermissionIdentity();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<java.lang.String> getAdoptedShellPermissions() {
        try {
            java.util.List<java.lang.String> adoptedShellPermissions = this.mUiAutomationConnection.getAdoptedShellPermissions();
            return adoptedShellPermissions == null ? ALL_PERMISSIONS : new android.util.ArraySet(adoptedShellPermissions);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final boolean performGlobalAction(int i) {
        android.accessibilityservice.IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            android.view.accessibility.AccessibilityInteractionClient.getInstance();
            connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                return connection.performGlobalAction(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Error while calling performGlobalAction", e);
                return false;
            }
        }
        return false;
    }

    public android.view.accessibility.AccessibilityNodeInfo findFocus(int i) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID, i);
    }

    public final android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() {
        android.accessibilityservice.IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            android.view.accessibility.AccessibilityInteractionClient.getInstance();
            connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                return connection.getServiceInfo();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", e);
                return null;
            }
        }
        return null;
    }

    public final void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        android.accessibilityservice.IAccessibilityServiceConnection connection;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            android.view.accessibility.AccessibilityInteractionClient.getInstance().clearCache(this.mConnectionId);
            android.view.accessibility.AccessibilityInteractionClient.getInstance();
            connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        }
        if (connection != null) {
            try {
                connection.setServiceInfo(accessibilityServiceInfo);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfo", e);
            }
        }
    }

    public java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindows() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().getWindowsOnDisplay(i, this.mDisplayId);
    }

    public android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> getWindowsOnAllDisplays() {
        int i;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i = this.mConnectionId;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().getWindowsOnAllDisplays(i);
    }

    public android.view.accessibility.AccessibilityNodeInfo getRootInActiveWindow() {
        return getRootInActiveWindow(4);
    }

    public android.view.accessibility.AccessibilityNodeInfo getRootInActiveWindow(int i) {
        int i2;
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            i2 = this.mConnectionId;
        }
        return android.view.accessibility.AccessibilityInteractionClient.getInstance().getRootInActiveWindow(i2, i);
    }

    public boolean injectInputEvent(android.view.InputEvent inputEvent, boolean z) {
        return injectInputEvent(inputEvent, z, true);
    }

    public boolean injectInputEvent(android.view.InputEvent inputEvent, boolean z, boolean z2) {
        try {
            return this.mUiAutomationConnection.injectInputEvent(inputEvent, z, z2);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error while injecting input event!", e);
            return false;
        }
    }

    public void injectInputEventToInputFilter(android.view.InputEvent inputEvent) {
        try {
            this.mUiAutomationConnection.injectInputEventToInputFilter(inputEvent);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error while injecting input event to input filter", e);
        }
    }

    public void setAnimationScale(float f) {
        android.view.accessibility.AccessibilityInteractionClient.getInstance();
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (connection != null) {
            try {
                connection.setAnimationScale(f);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void syncInputTransactions() {
        try {
            this.mUiAutomationConnection.syncInputTransactions(true);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error while syncing input transactions!", e);
        }
    }

    public void syncInputTransactions(boolean z) {
        try {
            this.mUiAutomationConnection.syncInputTransactions(z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error while syncing input transactions!", e);
        }
    }

    public boolean setRotation(int i) {
        switch (i) {
            case -2:
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
                try {
                    this.mUiAutomationConnection.setRotation(i);
                    return true;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(LOG_TAG, "Error while setting rotation!", e);
                    return false;
                }
            default:
                throw new java.lang.IllegalArgumentException("Invalid rotation.");
        }
    }

    public android.view.accessibility.AccessibilityEvent executeAndWaitForEvent(java.lang.Runnable runnable, android.app.UiAutomation.AccessibilityEventFilter accessibilityEventFilter, long j) throws java.util.concurrent.TimeoutException {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            this.mEventQueue.clear();
            this.mWaitingForEventDelivery = true;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        runnable.run();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
            while (true) {
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                synchronized (this.mLock) {
                    arrayList2.addAll(this.mEventQueue);
                    this.mEventQueue.clear();
                }
                while (!arrayList2.isEmpty()) {
                    android.view.accessibility.AccessibilityEvent accessibilityEvent = (android.view.accessibility.AccessibilityEvent) arrayList2.remove(0);
                    if (accessibilityEvent.getEventTime() >= uptimeMillis) {
                        if (accessibilityEventFilter.accept(accessibilityEvent)) {
                            int size = arrayList.size();
                            for (int i = 0; i < size; i++) {
                                ((android.view.accessibility.AccessibilityEvent) arrayList.get(i)).recycle();
                            }
                            synchronized (this.mLock) {
                                this.mWaitingForEventDelivery = false;
                                this.mEventQueue.clear();
                                this.mLock.notifyAll();
                            }
                            return accessibilityEvent;
                        }
                        arrayList.add(accessibilityEvent);
                    }
                }
                long uptimeMillis3 = j - (android.os.SystemClock.uptimeMillis() - uptimeMillis2);
                if (uptimeMillis3 <= 0) {
                    throw new java.util.concurrent.TimeoutException("Expected event not received within: " + j + " ms among: " + arrayList);
                }
                synchronized (this.mLock) {
                    if (this.mEventQueue.isEmpty()) {
                        try {
                            this.mLock.wait(uptimeMillis3);
                        } catch (java.lang.InterruptedException e) {
                        }
                    }
                }
            }
        } catch (java.lang.Throwable th) {
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((android.view.accessibility.AccessibilityEvent) arrayList.get(i2)).recycle();
            }
            synchronized (this.mLock) {
                this.mWaitingForEventDelivery = false;
                this.mEventQueue.clear();
                this.mLock.notifyAll();
                throw th;
            }
        }
    }

    public void waitForIdle(long j, long j2) throws java.util.concurrent.TimeoutException {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (this.mLastEventTimeMillis <= 0) {
                this.mLastEventTimeMillis = uptimeMillis;
            }
            while (true) {
                long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                if (j2 - (uptimeMillis2 - uptimeMillis) <= 0) {
                    throw new java.util.concurrent.TimeoutException("No idle state with idle timeout: " + j + " within global timeout: " + j2);
                }
                long j3 = j - (uptimeMillis2 - this.mLastEventTimeMillis);
                if (j3 > 0) {
                    try {
                        this.mLock.wait(j3);
                    } catch (java.lang.InterruptedException e) {
                    }
                }
            }
        }
    }

    public android.graphics.Bitmap takeScreenshot() {
        android.view.Display realDisplay = android.hardware.display.DisplayManagerGlobal.getInstance().getRealDisplay(this.mDisplayId);
        android.graphics.Point point = new android.graphics.Point();
        realDisplay.getRealSize(point);
        android.window.ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = android.window.ScreenCapture.createSyncCaptureListener();
        try {
            if (!this.mUiAutomationConnection.takeScreenshot(new android.graphics.Rect(0, 0, point.x, point.y), createSyncCaptureListener)) {
                return null;
            }
            android.window.ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
            if (buffer == null) {
                android.util.Log.e(LOG_TAG, "Failed to take screenshot for display=" + this.mDisplayId);
                return null;
            }
            android.graphics.Bitmap asBitmap = buffer.asBitmap();
            if (asBitmap == null) {
                android.util.Log.e(LOG_TAG, "Failed to take screenshot for display=" + this.mDisplayId);
                return null;
            }
            android.hardware.HardwareBuffer hardwareBuffer = buffer.getHardwareBuffer();
            try {
                android.graphics.Bitmap copy = asBitmap.copy(android.graphics.Bitmap.Config.ARGB_8888, false);
                if (hardwareBuffer != null) {
                    hardwareBuffer.close();
                }
                asBitmap.recycle();
                copy.setHasAlpha(false);
                return copy;
            } catch (java.lang.Throwable th) {
                if (hardwareBuffer != null) {
                    try {
                        hardwareBuffer.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error while taking screenshot of display " + this.mDisplayId, e);
            return null;
        }
    }

    public android.graphics.Bitmap takeScreenshot(android.view.Window window) {
        android.view.View peekDecorView;
        android.view.ViewRootImpl viewRootImpl;
        if (window == null || (peekDecorView = window.peekDecorView()) == null || (viewRootImpl = peekDecorView.getViewRootImpl()) == null) {
            return null;
        }
        android.view.SurfaceControl surfaceControl = viewRootImpl.getSurfaceControl();
        if (!surfaceControl.isValid()) {
            return null;
        }
        new android.view.SurfaceControl.Transaction().apply(true);
        android.window.ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = android.window.ScreenCapture.createSyncCaptureListener();
        try {
            if (!this.mUiAutomationConnection.takeSurfaceControlScreenshot(surfaceControl, createSyncCaptureListener)) {
                android.util.Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            android.window.ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
            if (buffer == null) {
                android.util.Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            android.graphics.Bitmap asBitmap = buffer.asBitmap();
            if (asBitmap == null) {
                android.util.Log.e(LOG_TAG, "Failed to take screenshot for window=" + window);
                return null;
            }
            android.hardware.HardwareBuffer hardwareBuffer = buffer.getHardwareBuffer();
            try {
                android.graphics.Bitmap copy = asBitmap.copy(android.graphics.Bitmap.Config.ARGB_8888, false);
                if (hardwareBuffer != null) {
                    hardwareBuffer.close();
                }
                asBitmap.recycle();
                return copy;
            } catch (java.lang.Throwable th) {
                if (hardwareBuffer != null) {
                    try {
                        hardwareBuffer.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error while taking screenshot!", e);
            return null;
        }
    }

    public void setRunAsMonkey(boolean z) {
        try {
            android.app.ActivityManager.getService().setUserIsMonkey(z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error while setting run as monkey!", e);
        }
    }

    public boolean clearWindowContentFrameStats(int i) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.clearWindowContentFrameStats(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error clearing window content frame stats!", e);
            return false;
        }
    }

    public android.view.WindowContentFrameStats getWindowContentFrameStats(int i) {
        synchronized (this.mLock) {
            throwIfNotConnectedLocked();
        }
        try {
            return this.mUiAutomationConnection.getWindowContentFrameStats(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error getting window content frame stats!", e);
            return null;
        }
    }

    @java.lang.Deprecated
    public void clearWindowAnimationFrameStats() {
        try {
            this.mUiAutomationConnection.clearWindowAnimationFrameStats();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error clearing window animation frame stats!", e);
        }
    }

    @java.lang.Deprecated
    public android.view.WindowAnimationFrameStats getWindowAnimationFrameStats() {
        try {
            return this.mUiAutomationConnection.getWindowAnimationFrameStats();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error getting window animation frame stats!", e);
            return null;
        }
    }

    public void grantRuntimePermission(java.lang.String str, java.lang.String str2) {
        grantRuntimePermissionAsUser(str, str2, android.os.Process.myUserHandle());
    }

    @java.lang.Deprecated
    public boolean grantRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        grantRuntimePermissionAsUser(str, str2, userHandle);
        return true;
    }

    public void grantRuntimePermissionAsUser(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        try {
            this.mUiAutomationConnection.grantRuntimePermission(str, str2, userHandle.getIdentifier());
        } catch (java.lang.Exception e) {
            throw new java.lang.SecurityException("Error granting runtime permission", e);
        }
    }

    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2) {
        revokeRuntimePermissionAsUser(str, str2, android.os.Process.myUserHandle());
    }

    @java.lang.Deprecated
    public boolean revokeRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        revokeRuntimePermissionAsUser(str, str2, userHandle);
        return true;
    }

    public void revokeRuntimePermissionAsUser(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        try {
            this.mUiAutomationConnection.revokeRuntimePermission(str, str2, userHandle.getIdentifier());
        } catch (java.lang.Exception e) {
            throw new java.lang.SecurityException("Error granting runtime permission", e);
        }
    }

    public android.os.ParcelFileDescriptor executeShellCommand(java.lang.String str) {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        warnIfBetterCommand(str);
        android.os.ParcelFileDescriptor parcelFileDescriptor2 = null;
        try {
            try {
                android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
                parcelFileDescriptor = createPipe[0];
                try {
                    android.os.ParcelFileDescriptor parcelFileDescriptor3 = createPipe[1];
                    try {
                        this.mUiAutomationConnection.executeShellCommand(str, parcelFileDescriptor3, null);
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor3);
                    } catch (android.os.RemoteException e) {
                        e = e;
                        parcelFileDescriptor2 = parcelFileDescriptor3;
                        android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
                        return parcelFileDescriptor;
                    } catch (java.io.IOException e2) {
                        e = e2;
                        parcelFileDescriptor2 = parcelFileDescriptor3;
                        android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
                        return parcelFileDescriptor;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        parcelFileDescriptor2 = parcelFileDescriptor3;
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
                        throw th;
                    }
                } catch (android.os.RemoteException e3) {
                    e = e3;
                } catch (java.io.IOException e4) {
                    e = e4;
                }
            } catch (android.os.RemoteException e5) {
                e = e5;
                parcelFileDescriptor = null;
            } catch (java.io.IOException e6) {
                e = e6;
                parcelFileDescriptor = null;
            }
            return parcelFileDescriptor;
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public android.os.ParcelFileDescriptor[] executeShellCommandRw(java.lang.String str) {
        return executeShellCommandInternal(str, false);
    }

    public android.os.ParcelFileDescriptor[] executeShellCommandRwe(java.lang.String str) {
        return executeShellCommandInternal(str, true);
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.os.ParcelFileDescriptor[] executeShellCommandInternal(java.lang.String str, boolean z) {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        android.os.ParcelFileDescriptor parcelFileDescriptor2;
        android.os.ParcelFileDescriptor parcelFileDescriptor3;
        android.os.ParcelFileDescriptor parcelFileDescriptor4;
        android.os.ParcelFileDescriptor parcelFileDescriptor5;
        android.os.ParcelFileDescriptor[] createPipe;
        android.os.ParcelFileDescriptor parcelFileDescriptor6;
        android.os.ParcelFileDescriptor[] createPipe2;
        android.os.ParcelFileDescriptor[] createPipe3;
        warnIfBetterCommand(str);
        android.os.ParcelFileDescriptor parcelFileDescriptor7 = null;
        try {
            try {
                try {
                    createPipe = android.os.ParcelFileDescriptor.createPipe();
                    parcelFileDescriptor2 = createPipe[0];
                } catch (android.os.RemoteException e) {
                    e = e;
                    parcelFileDescriptor = null;
                    parcelFileDescriptor2 = null;
                    parcelFileDescriptor3 = null;
                } catch (java.io.IOException e2) {
                    e = e2;
                    parcelFileDescriptor = null;
                    parcelFileDescriptor2 = null;
                    parcelFileDescriptor3 = null;
                }
                try {
                    parcelFileDescriptor6 = createPipe[1];
                    try {
                        createPipe2 = android.os.ParcelFileDescriptor.createPipe();
                        parcelFileDescriptor4 = createPipe2[0];
                    } catch (android.os.RemoteException e3) {
                        e = e3;
                        parcelFileDescriptor3 = null;
                        parcelFileDescriptor4 = null;
                        parcelFileDescriptor5 = null;
                    } catch (java.io.IOException e4) {
                        e = e4;
                        parcelFileDescriptor3 = null;
                        parcelFileDescriptor4 = null;
                        parcelFileDescriptor5 = null;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        parcelFileDescriptor4 = null;
                        parcelFileDescriptor7 = parcelFileDescriptor6;
                        parcelFileDescriptor = null;
                    }
                } catch (android.os.RemoteException e5) {
                    e = e5;
                    parcelFileDescriptor = null;
                    parcelFileDescriptor3 = null;
                    parcelFileDescriptor4 = parcelFileDescriptor3;
                    parcelFileDescriptor5 = parcelFileDescriptor4;
                    android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    android.os.ParcelFileDescriptor[] parcelFileDescriptorArr = new android.os.ParcelFileDescriptor[z ? 3 : 2];
                    parcelFileDescriptorArr[0] = parcelFileDescriptor2;
                    parcelFileDescriptorArr[1] = parcelFileDescriptor3;
                    if (z) {
                    }
                    return parcelFileDescriptorArr;
                } catch (java.io.IOException e6) {
                    e = e6;
                    parcelFileDescriptor = null;
                    parcelFileDescriptor3 = null;
                    parcelFileDescriptor4 = parcelFileDescriptor3;
                    parcelFileDescriptor5 = parcelFileDescriptor4;
                    android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    android.os.ParcelFileDescriptor[] parcelFileDescriptorArr2 = new android.os.ParcelFileDescriptor[z ? 3 : 2];
                    parcelFileDescriptorArr2[0] = parcelFileDescriptor2;
                    parcelFileDescriptorArr2[1] = parcelFileDescriptor3;
                    if (z) {
                    }
                    return parcelFileDescriptorArr2;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                parcelFileDescriptor = null;
                parcelFileDescriptor4 = null;
            }
            try {
                try {
                    parcelFileDescriptor3 = createPipe2[1];
                    if (z) {
                        try {
                            createPipe3 = android.os.ParcelFileDescriptor.createPipe();
                            parcelFileDescriptor5 = createPipe3[0];
                        } catch (android.os.RemoteException e7) {
                            e = e7;
                            parcelFileDescriptor5 = null;
                            parcelFileDescriptor7 = parcelFileDescriptor6;
                            parcelFileDescriptor = parcelFileDescriptor5;
                            android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                            android.os.ParcelFileDescriptor[] parcelFileDescriptorArr22 = new android.os.ParcelFileDescriptor[z ? 3 : 2];
                            parcelFileDescriptorArr22[0] = parcelFileDescriptor2;
                            parcelFileDescriptorArr22[1] = parcelFileDescriptor3;
                            if (z) {
                            }
                            return parcelFileDescriptorArr22;
                        } catch (java.io.IOException e8) {
                            e = e8;
                            parcelFileDescriptor5 = null;
                            parcelFileDescriptor7 = parcelFileDescriptor6;
                            parcelFileDescriptor = parcelFileDescriptor5;
                            android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                            android.os.ParcelFileDescriptor[] parcelFileDescriptorArr222 = new android.os.ParcelFileDescriptor[z ? 3 : 2];
                            parcelFileDescriptorArr222[0] = parcelFileDescriptor2;
                            parcelFileDescriptorArr222[1] = parcelFileDescriptor3;
                            if (z) {
                            }
                            return parcelFileDescriptorArr222;
                        }
                        try {
                            parcelFileDescriptor7 = createPipe3[1];
                        } catch (android.os.RemoteException e9) {
                            e = e9;
                            parcelFileDescriptor = parcelFileDescriptor7;
                            parcelFileDescriptor7 = parcelFileDescriptor6;
                            android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                            android.os.ParcelFileDescriptor[] parcelFileDescriptorArr2222 = new android.os.ParcelFileDescriptor[z ? 3 : 2];
                            parcelFileDescriptorArr2222[0] = parcelFileDescriptor2;
                            parcelFileDescriptorArr2222[1] = parcelFileDescriptor3;
                            if (z) {
                            }
                            return parcelFileDescriptorArr2222;
                        } catch (java.io.IOException e10) {
                            e = e10;
                            parcelFileDescriptor = parcelFileDescriptor7;
                            parcelFileDescriptor7 = parcelFileDescriptor6;
                            android.util.Log.e(LOG_TAG, "Error executing shell command!", e);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                            android.os.ParcelFileDescriptor[] parcelFileDescriptorArr22222 = new android.os.ParcelFileDescriptor[z ? 3 : 2];
                            parcelFileDescriptorArr22222[0] = parcelFileDescriptor2;
                            parcelFileDescriptorArr22222[1] = parcelFileDescriptor3;
                            if (z) {
                            }
                            return parcelFileDescriptorArr22222;
                        }
                    } else {
                        parcelFileDescriptor5 = null;
                    }
                    this.mUiAutomationConnection.executeShellCommandWithStderr(str, parcelFileDescriptor6, parcelFileDescriptor4, parcelFileDescriptor7);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor6);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                } catch (android.os.RemoteException e11) {
                    e = e11;
                    parcelFileDescriptor3 = null;
                    parcelFileDescriptor5 = null;
                } catch (java.io.IOException e12) {
                    e = e12;
                    parcelFileDescriptor3 = null;
                    parcelFileDescriptor5 = null;
                }
                android.os.ParcelFileDescriptor[] parcelFileDescriptorArr222222 = new android.os.ParcelFileDescriptor[z ? 3 : 2];
                parcelFileDescriptorArr222222[0] = parcelFileDescriptor2;
                parcelFileDescriptorArr222222[1] = parcelFileDescriptor3;
                if (z) {
                    parcelFileDescriptorArr222222[2] = parcelFileDescriptor5;
                }
                return parcelFileDescriptorArr222222;
            } catch (java.lang.Throwable th3) {
                th = th3;
                parcelFileDescriptor = parcelFileDescriptor7;
                parcelFileDescriptor7 = parcelFileDescriptor6;
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor7);
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor4);
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("UiAutomation@").append(java.lang.Integer.toHexString(hashCode()));
        sb.append("[id=").append(this.mConnectionId);
        sb.append(", displayId=").append(this.mDisplayId);
        sb.append(", flags=").append(this.mFlags);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    private void throwIfConnectedLocked() {
        if (this.mConnectionState == 2) {
            throw new java.lang.IllegalStateException("UiAutomation connected, " + this);
        }
    }

    private void throwIfNotConnectedLocked() {
        java.lang.String str;
        if (this.mConnectionState != 2) {
            if (useAccessibility()) {
                str = "UiAutomation not connected, ";
            } else {
                str = "UiAutomation not connected: Accessibility-dependent method called with FLAG_DONT_USE_ACCESSIBILITY set, ";
            }
            throw new java.lang.IllegalStateException(str + this);
        }
    }

    private void warnIfBetterCommand(java.lang.String str) {
        if (str.startsWith("pm grant ")) {
            android.util.Log.w(LOG_TAG, "UiAutomation.grantRuntimePermission() is more robust and should be used instead of 'pm grant'");
        } else if (str.startsWith("pm revoke ")) {
            android.util.Log.w(LOG_TAG, "UiAutomation.revokeRuntimePermission() is more robust and should be used instead of 'pm revoke'");
        }
    }

    private boolean useAccessibility() {
        return (this.mFlags & 2) == 0;
    }

    private static int getDisplayId(android.content.Context context) {
        com.android.internal.util.Preconditions.checkArgument(context != null, "Context cannot be null!");
        android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        if (!userManager.isVisibleBackgroundUsersSupported()) {
            return 0;
        }
        int displayId = context.getDisplayId();
        if (displayId == -1) {
            android.util.Log.e(LOG_TAG, "UiAutomation created UI context with invalid display id, assuming it's running in the display assigned to the user");
            return getMainDisplayIdAssignedToUser(context, userManager);
        }
        if (displayId != 0) {
            return displayId;
        }
        return getMainDisplayIdAssignedToUser(context, userManager);
    }

    private static int getMainDisplayIdAssignedToUser(android.content.Context context, android.os.UserManager userManager) {
        if (!userManager.isUserVisible()) {
            android.util.Log.e(LOG_TAG, "User (" + context.getUserId() + ") is not visible, using DEFAULT_DISPLAY");
            return 0;
        }
        return userManager.getMainDisplayIdAssignedToUser();
    }

    private class IAccessibilityServiceClientImpl extends android.accessibilityservice.AccessibilityService.IAccessibilityServiceClientWrapper {
        public IAccessibilityServiceClientImpl(android.os.Looper looper, final int i) {
            super((android.content.Context) null, looper, new android.accessibilityservice.AccessibilityService.Callbacks() { // from class: android.app.UiAutomation.IAccessibilityServiceClientImpl.1
                private final int mGenerationId;

                {
                    this.mGenerationId = i;
                }

                private boolean isGenerationChangedLocked() {
                    return this.mGenerationId != android.app.UiAutomation.this.mGenerationId;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void init(int i2, android.os.IBinder iBinder) {
                    synchronized (android.app.UiAutomation.this.mLock) {
                        if (isGenerationChangedLocked()) {
                            return;
                        }
                        android.app.UiAutomation.this.mConnectionState = 2;
                        android.app.UiAutomation.this.mConnectionId = i2;
                        android.app.UiAutomation.this.mLock.notifyAll();
                        if (android.os.Build.IS_DEBUGGABLE) {
                            android.util.Log.v(android.app.UiAutomation.LOG_TAG, "Init " + android.app.UiAutomation.this);
                        }
                    }
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onServiceConnected() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onInterrupt() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSystemActionsChanged() {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void startInput(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMotionEvent(android.view.MotionEvent motionEvent) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onTouchStateChanged(int i2, int i3) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
                    synchronized (android.app.UiAutomation.this.mLock) {
                        if (isGenerationChangedLocked()) {
                            return;
                        }
                        android.app.UiAutomation.this.mLastEventTimeMillis = java.lang.Math.max(android.app.UiAutomation.this.mLastEventTimeMillis, accessibilityEvent.getEventTime());
                        if (android.app.UiAutomation.this.mWaitingForEventDelivery) {
                            android.app.UiAutomation.this.mEventQueue.add(android.view.accessibility.AccessibilityEvent.obtain(accessibilityEvent));
                        }
                        android.app.UiAutomation.this.mLock.notifyAll();
                        android.app.UiAutomation.OnAccessibilityEventListener onAccessibilityEventListener = android.app.UiAutomation.this.mOnAccessibilityEventListener;
                        if (onAccessibilityEventListener != null) {
                            android.app.UiAutomation.this.mLocalCallbackHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.app.UiAutomation$IAccessibilityServiceClientImpl$1$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                    ((android.app.UiAutomation.OnAccessibilityEventListener) obj).onAccessibilityEvent((android.view.accessibility.AccessibilityEvent) obj2);
                                }
                            }, onAccessibilityEventListener, android.view.accessibility.AccessibilityEvent.obtain(accessibilityEvent)));
                        }
                    }
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public boolean onKeyEvent(android.view.KeyEvent keyEvent) {
                    return false;
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onMagnificationChanged(int i2, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onSoftKeyboardShowModeChanged(int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onPerformGestureResult(int i2, boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintCapturingGesturesChanged(boolean z) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onFingerprintGesture(int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonClicked(int i2) {
                }

                @Override // android.accessibilityservice.AccessibilityService.Callbacks
                public void onAccessibilityButtonAvailabilityChanged(boolean z) {
                }
            });
        }
    }
}
