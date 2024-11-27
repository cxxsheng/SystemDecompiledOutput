package com.android.server.wm;

/* loaded from: classes3.dex */
final class AccessibilityController {
    private final com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl mAccessibilityTracing;
    private final com.android.server.wm.AccessibilityWindowsPopulator mAccessibilityWindowsPopulator;
    private final com.android.server.wm.WindowManagerService mService;
    private static final java.lang.String TAG = com.android.server.wm.AccessibilityController.class.getSimpleName();
    private static final java.lang.Object STATIC_LOCK = new java.lang.Object();
    private static final android.graphics.Rect EMPTY_RECT = new android.graphics.Rect();
    private static final float[] sTempFloats = new float[9];
    private final android.util.SparseArray<com.android.server.wm.AccessibilityController.DisplayMagnifier> mDisplayMagnifiers = new android.util.SparseArray<>();
    private final android.util.SparseArray<com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver> mWindowsForAccessibilityObserver = new android.util.SparseArray<>();
    private android.util.SparseArray<android.os.IBinder> mFocusedWindow = new android.util.SparseArray<>();
    private int mFocusedDisplay = -1;
    private final android.util.SparseBooleanArray mIsImeVisibleArray = new android.util.SparseBooleanArray();
    private boolean mAllObserversInitialized = true;

    static com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl getAccessibilityControllerInternal(com.android.server.wm.WindowManagerService windowManagerService) {
        return com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl.getInstance(windowManagerService);
    }

    AccessibilityController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mAccessibilityTracing = getAccessibilityControllerInternal(windowManagerService);
        this.mAccessibilityWindowsPopulator = new com.android.server.wm.AccessibilityWindowsPopulator(this.mService, this);
    }

    boolean setMagnificationCallbacks(int i, com.android.server.wm.WindowManagerInternal.MagnificationCallbacks magnificationCallbacks) {
        android.view.Display display;
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".setMagnificationCallbacks", 2048L, "displayId=" + i + "; callbacks={" + magnificationCallbacks + "}");
        }
        if (magnificationCallbacks != null) {
            if (this.mDisplayMagnifiers.get(i) != null) {
                throw new java.lang.IllegalStateException("Magnification callbacks already set!");
            }
            com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(i);
            if (displayContent == null || (display = displayContent.getDisplay()) == null || display.getType() == 4) {
                return false;
            }
            com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = new com.android.server.wm.AccessibilityController.DisplayMagnifier(this.mService, displayContent, display, magnificationCallbacks);
            displayMagnifier.notifyImeWindowVisibilityChanged(this.mIsImeVisibleArray.get(i, false));
            this.mDisplayMagnifiers.put(i, displayMagnifier);
            return true;
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier2 = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier2 == null) {
            throw new java.lang.IllegalStateException("Magnification callbacks already cleared!");
        }
        displayMagnifier2.destroy();
        this.mDisplayMagnifiers.remove(i);
        return true;
    }

    void setWindowsForAccessibilityCallback(int i, com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback) {
        if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".setWindowsForAccessibilityCallback", 1024L, "displayId=" + i + "; callback={" + windowsForAccessibilityCallback + "}");
        }
        if (windowsForAccessibilityCallback != null) {
            if (this.mWindowsForAccessibilityObserver.get(i) != null) {
                java.lang.String str = "Windows for accessibility callback of display " + i + " already set!";
                android.util.Slog.e(TAG, str);
                if (android.os.Build.IS_DEBUGGABLE) {
                    throw new java.lang.IllegalStateException(str);
                }
                this.mWindowsForAccessibilityObserver.remove(i);
            }
            this.mAccessibilityWindowsPopulator.setWindowsNotification(true);
            com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver windowsForAccessibilityObserver = new com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver(this.mService, i, windowsForAccessibilityCallback, this.mAccessibilityWindowsPopulator);
            this.mWindowsForAccessibilityObserver.put(i, windowsForAccessibilityObserver);
            this.mAllObserversInitialized &= windowsForAccessibilityObserver.mInitialized;
            return;
        }
        if (this.mWindowsForAccessibilityObserver.get(i) == null) {
            java.lang.String str2 = "Windows for accessibility callback of display " + i + " already cleared!";
            android.util.Slog.e(TAG, str2);
            if (android.os.Build.IS_DEBUGGABLE) {
                throw new java.lang.IllegalStateException(str2);
            }
        }
        this.mWindowsForAccessibilityObserver.remove(i);
        if (this.mWindowsForAccessibilityObserver.size() <= 0) {
            this.mAccessibilityWindowsPopulator.setWindowsNotification(false);
        }
    }

    void performComputeChangedWindowsNot(int i, boolean z) {
        com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver windowsForAccessibilityObserver;
        if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".performComputeChangedWindowsNot", 1024L, "displayId=" + i + "; forceSend=" + z);
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowsForAccessibilityObserver = this.mWindowsForAccessibilityObserver.get(i);
                if (windowsForAccessibilityObserver == null) {
                    windowsForAccessibilityObserver = null;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (windowsForAccessibilityObserver != null) {
            windowsForAccessibilityObserver.performComputeChangedWindows(z);
        }
    }

    void setMagnificationSpec(int i, android.view.MagnificationSpec magnificationSpec) {
        if (this.mAccessibilityTracing.isTracingEnabled(3072L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".setMagnificationSpec", 3072L, "displayId=" + i + "; spec={" + magnificationSpec + "}");
        }
        this.mAccessibilityWindowsPopulator.setMagnificationSpec(i, magnificationSpec);
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.setMagnificationSpec(magnificationSpec);
        }
        com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver windowsForAccessibilityObserver = this.mWindowsForAccessibilityObserver.get(i);
        if (windowsForAccessibilityObserver != null) {
            windowsForAccessibilityObserver.scheduleComputeChangedWindows();
        }
    }

    void getMagnificationRegion(int i, android.graphics.Region region) {
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".getMagnificationRegion", 2048L, "displayId=" + i + "; outMagnificationRegion={" + region + "}");
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.getMagnificationRegion(region);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.view.Surface forceShowMagnifierSurface(int i) {
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.mMagnifiedViewport.mWindow.setAlpha(255);
            return displayMagnifier.mMagnifiedViewport.mWindow.mSurface;
        }
        return null;
    }

    void onWindowLayersChanged(int i) {
        if (this.mAccessibilityTracing.isTracingEnabled(3072L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".onWindowLayersChanged", 3072L, "displayId=" + i);
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.onWindowLayersChanged();
        }
        com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver windowsForAccessibilityObserver = this.mWindowsForAccessibilityObserver.get(i);
        if (windowsForAccessibilityObserver != null) {
            windowsForAccessibilityObserver.scheduleComputeChangedWindows();
        }
    }

    void onDisplaySizeChanged(com.android.server.wm.DisplayContent displayContent) {
        if (this.mAccessibilityTracing.isTracingEnabled(3072L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".onRotationChanged", 3072L, "displayContent={" + displayContent + "}");
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(displayContent.getDisplayId());
        if (displayMagnifier != null) {
            displayMagnifier.onDisplaySizeChanged(displayContent);
        }
    }

    void onAppWindowTransition(int i, int i2) {
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".onAppWindowTransition", 2048L, "displayId=" + i + "; transition=" + i2);
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.onAppWindowTransition(i, i2);
        }
    }

    void onWMTransition(int i, int i2) {
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".onAppWindowTransition", 2048L, "displayId=" + i + "; type=" + i2);
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.onWMTransition(i, i2);
        }
    }

    void onWindowTransition(com.android.server.wm.WindowState windowState, int i) {
        if (this.mAccessibilityTracing.isTracingEnabled(3072L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".onWindowTransition", 3072L, "windowState={" + windowState + "}; transition=" + i);
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(windowState.getDisplayId());
        if (displayMagnifier != null) {
            displayMagnifier.onWindowTransition(windowState, i);
        }
    }

    void onWindowFocusChangedNot(int i) {
        com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver windowsForAccessibilityObserver;
        if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".onWindowFocusChangedNot", 1024L, "displayId=" + i);
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowsForAccessibilityObserver = this.mWindowsForAccessibilityObserver.get(i);
                if (windowsForAccessibilityObserver == null) {
                    windowsForAccessibilityObserver = null;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (windowsForAccessibilityObserver != null) {
            windowsForAccessibilityObserver.performComputeChangedWindows(false);
        }
        sendCallbackToUninitializedObserversIfNeeded();
    }

    private void sendCallbackToUninitializedObserversIfNeeded() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mAllObserversInitialized) {
                    return;
                }
                if (this.mService.mRoot.getTopFocusedDisplayContent().mCurrentFocus == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int size = this.mWindowsForAccessibilityObserver.size() - 1; size >= 0; size--) {
                    com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver valueAt = this.mWindowsForAccessibilityObserver.valueAt(size);
                    if (!valueAt.mInitialized) {
                        arrayList.add(valueAt);
                    }
                }
                this.mAllObserversInitialized = true;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                boolean z = true;
                for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                    com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver windowsForAccessibilityObserver = (com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver) arrayList.get(size2);
                    windowsForAccessibilityObserver.performComputeChangedWindows(true);
                    z &= windowsForAccessibilityObserver.mInitialized;
                }
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        this.mAllObserversInitialized &= z;
                    } finally {
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    void onSomeWindowResizedOrMoved(int... iArr) {
        onSomeWindowResizedOrMovedWithCallingUid(android.os.Binder.getCallingUid(), iArr);
    }

    void onSomeWindowResizedOrMovedWithCallingUid(int i, int... iArr) {
        if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".onSomeWindowResizedOrMoved", 1024L, "displayIds={" + java.util.Arrays.toString(iArr) + "}", "".getBytes(), i);
        }
        for (int i2 : iArr) {
            com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver windowsForAccessibilityObserver = this.mWindowsForAccessibilityObserver.get(i2);
            if (windowsForAccessibilityObserver != null) {
                windowsForAccessibilityObserver.scheduleComputeChangedWindows();
            }
        }
    }

    void drawMagnifiedRegionBorderIfNeeded(int i) {
        if (com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
            return;
        }
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".drawMagnifiedRegionBorderIfNeeded", 2048L, "displayId=" + i);
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.drawMagnifiedRegionBorderIfNeeded();
        }
    }

    public android.util.Pair<android.graphics.Matrix, android.view.MagnificationSpec> getWindowTransformationMatrixAndMagnificationSpec(android.os.IBinder iBinder) {
        android.util.Pair<android.graphics.Matrix, android.view.MagnificationSpec> pair;
        android.view.MagnificationSpec magnificationSpecForWindow;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.graphics.Matrix matrix = new android.graphics.Matrix();
                android.view.MagnificationSpec magnificationSpec = new android.view.MagnificationSpec();
                com.android.server.wm.WindowState windowState = this.mService.mWindowMap.get(iBinder);
                if (windowState != null) {
                    windowState.getTransformationMatrix(new float[9], matrix);
                    if (hasCallbacks() && (magnificationSpecForWindow = getMagnificationSpecForWindow(windowState)) != null && !magnificationSpecForWindow.isNop()) {
                        magnificationSpec.setTo(magnificationSpecForWindow);
                    }
                }
                pair = new android.util.Pair<>(matrix, magnificationSpec);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return pair;
    }

    android.view.MagnificationSpec getMagnificationSpecForWindow(com.android.server.wm.WindowState windowState) {
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".getMagnificationSpecForWindow", 2048L, "windowState={" + windowState + "}");
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(windowState.getDisplayId());
        if (displayMagnifier != null) {
            return displayMagnifier.getMagnificationSpecForWindow(windowState);
        }
        return null;
    }

    boolean hasCallbacks() {
        if (this.mAccessibilityTracing.isTracingEnabled(3072L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".hasCallbacks", 3072L);
        }
        return this.mDisplayMagnifiers.size() > 0 || this.mWindowsForAccessibilityObserver.size() > 0;
    }

    void setFullscreenMagnificationActivated(int i, boolean z) {
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".setFullscreenMagnificationActivated", 2048L, "displayId=" + i + "; activated=" + z);
        }
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.setFullscreenMagnificationActivated(z);
        }
    }

    void updateImeVisibilityIfNeeded(int i, boolean z) {
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace(TAG + ".updateImeVisibilityIfNeeded", 2048L, "displayId=" + i + ";shown=" + z);
        }
        if (this.mIsImeVisibleArray.get(i, false) == z) {
            return;
        }
        this.mIsImeVisibleArray.put(i, z);
        com.android.server.wm.AccessibilityController.DisplayMagnifier displayMagnifier = this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            displayMagnifier.notifyImeWindowVisibilityChanged(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void populateTransformationMatrix(com.android.server.wm.WindowState windowState, android.graphics.Matrix matrix) {
        windowState.getTransformationMatrix(sTempFloats, matrix);
    }

    void dump(final java.io.PrintWriter printWriter, final java.lang.String str) {
        com.android.internal.util.DumpUtils.dumpSparseArray(printWriter, str, this.mDisplayMagnifiers, "magnification display", new com.android.internal.util.DumpUtils.KeyDumper() { // from class: com.android.server.wm.AccessibilityController$$ExternalSyntheticLambda0
            public final void dump(int i, int i2) {
                com.android.server.wm.AccessibilityController.lambda$dump$0(printWriter, str, i, i2);
            }
        }, new com.android.internal.util.DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityController$$ExternalSyntheticLambda1
            public final void dump(java.lang.Object obj) {
                ((com.android.server.wm.AccessibilityController.DisplayMagnifier) obj).dump(printWriter, "");
            }
        });
        com.android.internal.util.DumpUtils.dumpSparseArrayValues(printWriter, str, this.mWindowsForAccessibilityObserver, "windows for accessibility observer");
        this.mAccessibilityWindowsPopulator.dump(printWriter, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$0(java.io.PrintWriter printWriter, java.lang.String str, int i, int i2) {
        printWriter.printf("%sDisplay #%d:", str + "  ", java.lang.Integer.valueOf(i2));
    }

    void onFocusChanged(com.android.server.wm.InputTarget inputTarget, com.android.server.wm.InputTarget inputTarget2) {
        if (inputTarget != null) {
            this.mFocusedWindow.remove(inputTarget.getDisplayId());
        }
        if (inputTarget2 != null) {
            this.mFocusedWindow.put(inputTarget2.getDisplayId(), inputTarget2.getWindowToken());
        }
    }

    public void onDisplayRemoved(int i) {
        this.mIsImeVisibleArray.delete(i);
        this.mFocusedWindow.remove(i);
    }

    public void setFocusedDisplay(int i) {
        this.mFocusedDisplay = i;
    }

    @android.annotation.Nullable
    android.os.IBinder getFocusedWindowToken() {
        return this.mFocusedWindow.get(this.mFocusedDisplay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DisplayMagnifier {
        private static final boolean DEBUG_DISPLAY_SIZE = false;
        private static final boolean DEBUG_LAYERS = false;
        private static final boolean DEBUG_RECTANGLE_REQUESTED = false;
        private static final boolean DEBUG_VIEWPORT_WINDOW = false;
        private static final boolean DEBUG_WINDOW_TRANSITIONS = false;
        private static final java.lang.String LOG_TAG = "WindowManager";
        private final com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl mAccessibilityTracing;
        private final com.android.server.wm.WindowManagerInternal.MagnificationCallbacks mCallbacks;
        private final android.graphics.Path mCircularPath;
        private final android.view.Display mDisplay;
        private final com.android.server.wm.DisplayContent mDisplayContent;
        private final android.content.Context mDisplayContext;
        private final android.os.Handler mHandler;
        private final long mLongAnimationDuration;
        private final com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport mMagnifiedViewport;
        private final com.android.server.wm.WindowManagerService mService;
        private final android.graphics.Rect mTempRect1 = new android.graphics.Rect();
        private final android.graphics.Rect mTempRect2 = new android.graphics.Rect();
        private final android.graphics.Region mTempRegion1 = new android.graphics.Region();
        private final android.graphics.Region mTempRegion2 = new android.graphics.Region();
        private final android.graphics.Region mTempRegion3 = new android.graphics.Region();
        private final android.graphics.Region mTempRegion4 = new android.graphics.Region();
        private boolean mIsFullscreenMagnificationActivated = false;
        private final android.graphics.Region mMagnificationRegion = new android.graphics.Region();
        private final android.graphics.Region mOldMagnificationRegion = new android.graphics.Region();
        private final android.view.MagnificationSpec mMagnificationSpec = new android.view.MagnificationSpec();
        private int mTempLayer = 0;
        private final android.graphics.Point mScreenSize = new android.graphics.Point();
        private final android.util.SparseArray<com.android.server.wm.WindowState> mTempWindowStates = new android.util.SparseArray<>();
        private final android.graphics.RectF mTempRectF = new android.graphics.RectF();
        private final android.graphics.Matrix mTempMatrix = new android.graphics.Matrix();

        DisplayMagnifier(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, android.view.Display display, com.android.server.wm.WindowManagerInternal.MagnificationCallbacks magnificationCallbacks) {
            this.mDisplayContext = windowManagerService.mContext.createDisplayContext(display);
            this.mService = windowManagerService;
            this.mCallbacks = magnificationCallbacks;
            this.mDisplayContent = displayContent;
            this.mDisplay = display;
            this.mHandler = new com.android.server.wm.AccessibilityController.DisplayMagnifier.MyHandler(this.mService.mH.getLooper());
            this.mMagnifiedViewport = com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder() ? null : new com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport();
            this.mAccessibilityTracing = com.android.server.wm.AccessibilityController.getAccessibilityControllerInternal(this.mService);
            this.mLongAnimationDuration = this.mDisplayContext.getResources().getInteger(android.R.integer.config_longAnimTime);
            if (this.mDisplayContext.getResources().getConfiguration().isScreenRound()) {
                this.mCircularPath = new android.graphics.Path();
                getDisplaySizeLocked(this.mScreenSize);
                float f = this.mScreenSize.x / 2;
                this.mCircularPath.addCircle(f, f, f, android.graphics.Path.Direction.CW);
            } else {
                this.mCircularPath = null;
            }
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.DisplayMagnifier.constructor", 2048L, "windowManagerService={" + windowManagerService + "}; displayContent={" + displayContent + "}; display={" + display + "}; callbacks={" + magnificationCallbacks + "}");
            }
            recomputeBounds();
        }

        void setMagnificationSpec(android.view.MagnificationSpec magnificationSpec) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.setMagnificationSpec", 2048L, "spec={" + magnificationSpec + "}");
            }
            updateMagnificationSpec(magnificationSpec);
            recomputeBounds();
            this.mService.applyMagnificationSpecLocked(this.mDisplay.getDisplayId(), magnificationSpec);
            this.mService.scheduleAnimationLocked();
        }

        void updateMagnificationSpec(android.view.MagnificationSpec magnificationSpec) {
            if (magnificationSpec != null) {
                this.mMagnificationSpec.initialize(magnificationSpec.scale, magnificationSpec.offsetX, magnificationSpec.offsetY);
            } else {
                this.mMagnificationSpec.clear();
            }
            if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                this.mMagnifiedViewport.setShowMagnifiedBorderIfNeeded();
            }
        }

        void setFullscreenMagnificationActivated(boolean z) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.setFullscreenMagnificationActivated", 2048L, "activated=" + z);
            }
            this.mIsFullscreenMagnificationActivated = z;
            if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                this.mMagnifiedViewport.setMagnifiedRegionBorderShown(z, true);
                this.mMagnifiedViewport.showMagnificationBoundsIfNeeded();
            }
        }

        boolean isFullscreenMagnificationActivated() {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.isFullscreenMagnificationActivated", 2048L);
            }
            return this.mIsFullscreenMagnificationActivated;
        }

        void onWindowLayersChanged() {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.onWindowLayersChanged", 2048L);
            }
            recomputeBounds();
            this.mService.scheduleAnimationLocked();
        }

        void onDisplaySizeChanged(com.android.server.wm.DisplayContent displayContent) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.onDisplaySizeChanged", 2048L, "displayContent={" + displayContent + "}");
            }
            recomputeBounds();
            if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                this.mMagnifiedViewport.onDisplaySizeChanged();
            }
            this.mHandler.sendEmptyMessage(4);
        }

        void onAppWindowTransition(int i, int i2) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.onAppWindowTransition", 2048L, "displayId=" + i + "; transition=" + i2);
            }
            if (isFullscreenMagnificationActivated()) {
                switch (i2) {
                    case 6:
                    case 8:
                    case 10:
                    case 12:
                    case 13:
                    case 14:
                    case 28:
                        this.mHandler.sendEmptyMessage(3);
                        break;
                }
            }
        }

        void onWMTransition(int i, int i2) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.onWMTransition", 2048L, "displayId=" + i + "; type=" + i2);
            }
            if (isFullscreenMagnificationActivated()) {
                switch (i2) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        this.mHandler.sendEmptyMessage(3);
                        break;
                }
            }
        }

        void onWindowTransition(com.android.server.wm.WindowState windowState, int i) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.onWindowTransition", 2048L, "windowState={" + windowState + "}; transition=" + i);
            }
            boolean isFullscreenMagnificationActivated = isFullscreenMagnificationActivated();
            int i2 = windowState.mAttrs.type;
            switch (i) {
                case 1:
                case 3:
                    if (isFullscreenMagnificationActivated && windowState.shouldMagnify()) {
                        switch (i2) {
                            case 2:
                            case 4:
                            case 1000:
                            case 1001:
                            case 1002:
                            case 1003:
                            case 1005:
                            case 2001:
                            case 2002:
                            case 2003:
                            case 2005:
                            case 2006:
                            case 2007:
                            case 2008:
                            case 2009:
                            case 2010:
                            case com.android.server.notification.NotificationShellCmd.NOTIFICATION_ID /* 2020 */:
                            case 2024:
                            case 2035:
                            case 2038:
                                android.graphics.Rect rect = this.mTempRect2;
                                getMagnifiedFrameInContentCoords(rect);
                                android.graphics.Rect rect2 = this.mTempRect1;
                                windowState.getTouchableRegion(this.mTempRegion1);
                                this.mTempRegion1.getBounds(rect2);
                                if (!rect.intersect(rect2)) {
                                    this.mCallbacks.onRectangleOnScreenRequested(rect2.left, rect2.top, rect2.right, rect2.bottom);
                                    break;
                                }
                                break;
                        }
                    }
                    break;
            }
        }

        void getMagnifiedFrameInContentCoords(android.graphics.Rect rect) {
            this.mMagnificationRegion.getBounds(rect);
            rect.offset((int) (-this.mMagnificationSpec.offsetX), (int) (-this.mMagnificationSpec.offsetY));
            rect.scale(1.0f / this.mMagnificationSpec.scale);
        }

        void notifyImeWindowVisibilityChanged(boolean z) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.notifyImeWindowVisibilityChanged", 2048L, "shown=" + z);
            }
            this.mHandler.obtainMessage(6, z ? 1 : 0, 0).sendToTarget();
        }

        android.view.MagnificationSpec getMagnificationSpecForWindow(com.android.server.wm.WindowState windowState) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.getMagnificationSpecForWindow", 2048L, "windowState={" + windowState + "}");
            }
            if (this.mMagnificationSpec != null && !this.mMagnificationSpec.isNop() && !windowState.shouldMagnify()) {
                return null;
            }
            return this.mMagnificationSpec;
        }

        void getMagnificationRegion(android.graphics.Region region) {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.getMagnificationRegion", 2048L, "outMagnificationRegion={" + region + "}");
            }
            recomputeBounds();
            region.set(this.mMagnificationRegion);
        }

        boolean isMagnifying() {
            return this.mMagnificationSpec.scale > 1.0f;
        }

        void destroy() {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.destroy", 2048L);
            }
            if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                this.mMagnifiedViewport.destroyWindow();
            }
        }

        void drawMagnifiedRegionBorderIfNeeded() {
            if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.drawMagnifiedRegionBorderIfNeeded", 2048L);
            }
            if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                this.mMagnifiedViewport.drawWindowIfNeeded();
            }
        }

        void recomputeBounds() {
            getDisplaySizeLocked(this.mScreenSize);
            int i = this.mScreenSize.x;
            int i2 = this.mScreenSize.y;
            this.mMagnificationRegion.set(0, 0, 0, 0);
            android.graphics.Region region = this.mTempRegion1;
            region.set(0, 0, i, i2);
            if (this.mCircularPath != null) {
                region.setPath(this.mCircularPath, region);
            }
            android.graphics.Region region2 = this.mTempRegion4;
            region2.set(0, 0, 0, 0);
            android.util.SparseArray<com.android.server.wm.WindowState> sparseArray = this.mTempWindowStates;
            sparseArray.clear();
            populateWindowsOnScreen(sparseArray);
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowState valueAt = sparseArray.valueAt(size);
                if (!isExcludedWindowType(valueAt.mAttrs.type) && (valueAt.mAttrs.privateFlags & 2097152) == 0 && (valueAt.mAttrs.privateFlags & 1048576) == 0) {
                    android.graphics.Matrix matrix = this.mTempMatrix;
                    com.android.server.wm.AccessibilityController.populateTransformationMatrix(valueAt, matrix);
                    android.graphics.Region region3 = this.mTempRegion3;
                    valueAt.getTouchableRegion(region3);
                    android.graphics.Rect rect = this.mTempRect1;
                    region3.getBounds(rect);
                    android.graphics.RectF rectF = this.mTempRectF;
                    rectF.set(rect);
                    rectF.offset(-valueAt.getFrame().left, -valueAt.getFrame().top);
                    matrix.mapRect(rectF);
                    android.graphics.Region region4 = this.mTempRegion2;
                    region4.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                    android.graphics.Region region5 = this.mTempRegion3;
                    region5.set(this.mMagnificationRegion);
                    region5.op(region2, android.graphics.Region.Op.UNION);
                    region4.op(region5, android.graphics.Region.Op.DIFFERENCE);
                    if (valueAt.shouldMagnify()) {
                        this.mMagnificationRegion.op(region4, android.graphics.Region.Op.UNION);
                        this.mMagnificationRegion.op(region, android.graphics.Region.Op.INTERSECT);
                    } else {
                        region2.op(region4, android.graphics.Region.Op.UNION);
                        region.op(region4, android.graphics.Region.Op.DIFFERENCE);
                    }
                    if (com.android.server.wm.AccessibilityController.isUntouchableNavigationBar(valueAt, this.mTempRegion3)) {
                        android.graphics.Rect systemBarInsetsFrame = com.android.server.wm.AccessibilityController.getSystemBarInsetsFrame(valueAt);
                        region2.op(systemBarInsetsFrame, android.graphics.Region.Op.UNION);
                        region.op(systemBarInsetsFrame, android.graphics.Region.Op.DIFFERENCE);
                    }
                    if (valueAt.areAppWindowBoundsLetterboxed()) {
                        android.graphics.Region letterboxBounds = getLetterboxBounds(valueAt);
                        region2.op(letterboxBounds, android.graphics.Region.Op.UNION);
                        region.op(letterboxBounds, android.graphics.Region.Op.DIFFERENCE);
                    }
                    android.graphics.Region region6 = this.mTempRegion2;
                    region6.set(this.mMagnificationRegion);
                    region6.op(region2, android.graphics.Region.Op.UNION);
                    region6.op(0, 0, i, i2, android.graphics.Region.Op.INTERSECT);
                    if (region6.isRect()) {
                        android.graphics.Rect rect2 = this.mTempRect1;
                        region6.getBounds(rect2);
                        if (rect2.width() == i && rect2.height() == i2) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            sparseArray.clear();
            if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                this.mMagnifiedViewport.intersectWithDrawBorderInset(i, i2);
            }
            if (!this.mOldMagnificationRegion.equals(this.mMagnificationRegion)) {
                if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                    this.mMagnifiedViewport.updateBorderDrawingStatus(i, i2);
                }
                this.mOldMagnificationRegion.set(this.mMagnificationRegion);
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = android.graphics.Region.obtain(this.mMagnificationRegion);
                this.mHandler.obtainMessage(1, obtain).sendToTarget();
            }
        }

        private android.graphics.Region getLetterboxBounds(com.android.server.wm.WindowState windowState) {
            com.android.server.wm.ActivityRecord activityRecord = windowState.mActivityRecord;
            if (activityRecord == null) {
                return new android.graphics.Region();
            }
            android.graphics.Rect bounds = windowState.getBounds();
            android.graphics.Rect letterboxInsets = activityRecord.getLetterboxInsets();
            android.graphics.Rect copyOrNull = android.graphics.Rect.copyOrNull(bounds);
            copyOrNull.inset(android.graphics.Insets.subtract(android.graphics.Insets.NONE, android.graphics.Insets.of(letterboxInsets)));
            android.graphics.Region region = new android.graphics.Region();
            region.set(copyOrNull);
            region.op(bounds, android.graphics.Region.Op.DIFFERENCE);
            return region;
        }

        private boolean isExcludedWindowType(int i) {
            return i == 2027 || i == 2039;
        }

        private void populateWindowsOnScreen(final android.util.SparseArray<com.android.server.wm.WindowState> sparseArray) {
            this.mTempLayer = 0;
            this.mDisplayContent.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.AccessibilityController$DisplayMagnifier$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.lambda$populateWindowsOnScreen$0(sparseArray, (com.android.server.wm.WindowState) obj);
                }
            }, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$populateWindowsOnScreen$0(android.util.SparseArray sparseArray, com.android.server.wm.WindowState windowState) {
            if (windowState.isOnScreen() && windowState.isVisible() && windowState.mAttrs.alpha != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                this.mTempLayer++;
                sparseArray.put(this.mTempLayer, windowState);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void getDisplaySizeLocked(android.graphics.Point point) {
            android.graphics.Rect bounds = this.mDisplayContent.getConfiguration().windowConfiguration.getBounds();
            point.set(bounds.width(), bounds.height());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                this.mMagnifiedViewport.dump(printWriter, str);
            }
        }

        private final class MagnifiedViewport {
            private final float mBorderWidth;
            private final int mDrawBorderInset;
            private boolean mFullRedrawNeeded;
            private final int mHalfBorderWidth;

            @android.annotation.Nullable
            private final com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow mWindow;

            MagnifiedViewport() {
                this.mBorderWidth = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mDisplayContext.getResources().getDimension(android.R.dimen.accessibility_magnification_indicator_width);
                this.mHalfBorderWidth = (int) java.lang.Math.ceil(this.mBorderWidth / 2.0f);
                this.mDrawBorderInset = ((int) this.mBorderWidth) / 2;
                this.mWindow = new com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mDisplayContext);
            }

            void updateBorderDrawingStatus(int i, int i2) {
                this.mWindow.setBounds(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mMagnificationRegion);
                android.graphics.Rect rect = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mTempRect1;
                if (this.mFullRedrawNeeded) {
                    this.mFullRedrawNeeded = false;
                    rect.set(this.mDrawBorderInset, this.mDrawBorderInset, i - this.mDrawBorderInset, i2 - this.mDrawBorderInset);
                    this.mWindow.invalidate(rect);
                } else {
                    android.graphics.Region region = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mTempRegion3;
                    region.set(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mMagnificationRegion);
                    region.op(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mOldMagnificationRegion, android.graphics.Region.Op.XOR);
                    region.getBounds(rect);
                    this.mWindow.invalidate(rect);
                }
            }

            void setShowMagnifiedBorderIfNeeded() {
                if (!com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mHandler.hasMessages(5)) {
                    setMagnifiedRegionBorderShown(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.isFullscreenMagnificationActivated(), true);
                }
            }

            void showMagnificationBoundsIfNeeded() {
                if (com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mAccessibilityTracing.logTrace("WindowManager.showMagnificationBoundsIfNeeded", 2048L);
                }
                com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mHandler.obtainMessage(5).sendToTarget();
            }

            void intersectWithDrawBorderInset(int i, int i2) {
                com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mMagnificationRegion.op(this.mDrawBorderInset, this.mDrawBorderInset, i - this.mDrawBorderInset, i2 - this.mDrawBorderInset, android.graphics.Region.Op.INTERSECT);
            }

            void onDisplaySizeChanged() {
                if (com.android.server.wm.AccessibilityController.DisplayMagnifier.this.isFullscreenMagnificationActivated()) {
                    setMagnifiedRegionBorderShown(false, false);
                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mHandler.sendMessageDelayed(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mHandler.obtainMessage(5), (long) (com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mLongAnimationDuration * com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.getWindowAnimationScaleLocked()));
                }
                this.mWindow.updateSize();
            }

            void setMagnifiedRegionBorderShown(boolean z, boolean z2) {
                if (this.mWindow.setShown(z, z2)) {
                    this.mFullRedrawNeeded = true;
                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mOldMagnificationRegion.set(0, 0, 0, 0);
                }
            }

            void drawWindowIfNeeded() {
                com.android.server.wm.AccessibilityController.DisplayMagnifier.this.recomputeBounds();
                this.mWindow.postDrawIfNeeded();
            }

            void destroyWindow() {
                this.mWindow.releaseSurface();
            }

            void dump(java.io.PrintWriter printWriter, java.lang.String str) {
                this.mWindow.dump(printWriter, str);
            }

            private final class ViewportWindow implements java.lang.Runnable {
                private static final java.lang.String SURFACE_TITLE = "Magnification Overlay";
                private int mAlpha;
                private final com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow.AnimationController mAnimationController;
                private final android.graphics.BLASTBufferQueue mBlastBufferQueue;
                private volatile boolean mInvalidated;
                private boolean mLastSurfaceShown;
                private boolean mShown;
                private final android.view.Surface mSurface;
                private final android.view.SurfaceControl mSurfaceControl;
                private final android.view.SurfaceControl.Transaction mTransaction;
                private final android.graphics.Region mBounds = new android.graphics.Region();
                private final android.graphics.Rect mDirtyRect = new android.graphics.Rect();
                private final android.graphics.Paint mPaint = new android.graphics.Paint();

                ViewportWindow(android.content.Context context) {
                    android.view.SurfaceControl surfaceControl;
                    try {
                        surfaceControl = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mDisplayContent.makeOverlay().setName(SURFACE_TITLE).setBLASTLayer().setFormat(-3).setCallsite("ViewportWindow").build();
                    } catch (android.view.Surface.OutOfResourcesException e) {
                        surfaceControl = null;
                    }
                    this.mSurfaceControl = surfaceControl;
                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mDisplay.getRealSize(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize);
                    this.mBlastBufferQueue = new android.graphics.BLASTBufferQueue(SURFACE_TITLE, this.mSurfaceControl, com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize.x, com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize.y, 1);
                    android.view.SurfaceControl.Transaction transaction = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mTransactionFactory.get();
                    transaction.setLayer(this.mSurfaceControl, com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mPolicy.getWindowLayerFromTypeLw(2027) * 10000).setPosition(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                    com.android.server.wm.InputMonitor.setTrustedOverlayInputInfo(this.mSurfaceControl, transaction, com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mDisplayContent.getDisplayId(), SURFACE_TITLE);
                    transaction.apply();
                    this.mTransaction = transaction;
                    this.mSurface = this.mBlastBufferQueue.createSurface();
                    this.mAnimationController = new com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow.AnimationController(context, com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mH.getLooper());
                    android.util.TypedValue typedValue = new android.util.TypedValue();
                    context.getTheme().resolveAttribute(android.R.attr.colorActivatedHighlight, typedValue, true);
                    int color = context.getColor(typedValue.resourceId);
                    this.mPaint.setStyle(android.graphics.Paint.Style.STROKE);
                    this.mPaint.setStrokeWidth(com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.this.mBorderWidth);
                    this.mPaint.setColor(color);
                    this.mInvalidated = true;
                }

                boolean setShown(boolean z, boolean z2) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (this.mShown == z) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                            this.mShown = z;
                            this.mAnimationController.onFrameShownStateChanged(z, z2);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return z;
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }

                int getAlpha() {
                    int i;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            i = this.mAlpha;
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                }

                void setAlpha(int i) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (this.mAlpha == i) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            this.mAlpha = i;
                            invalidate(null);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }

                void setBounds(android.graphics.Region region) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (this.mBounds.equals(region)) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            this.mBounds.set(region);
                            invalidate(this.mDirtyRect);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }

                void updateSize() {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.AccessibilityController.DisplayMagnifier.this.getDisplaySizeLocked(com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize);
                            this.mBlastBufferQueue.update(this.mSurfaceControl, com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize.x, com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize.y, 1);
                            invalidate(this.mDirtyRect);
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }

                void invalidate(android.graphics.Rect rect) {
                    if (rect != null) {
                        this.mDirtyRect.set(rect);
                    } else {
                        this.mDirtyRect.setEmpty();
                    }
                    this.mInvalidated = true;
                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.scheduleAnimationLocked();
                }

                void postDrawIfNeeded() {
                    if (this.mInvalidated) {
                        com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mAnimationHandler.post(this);
                    }
                }

                @Override // java.lang.Runnable
                public void run() {
                    drawOrRemoveIfNeeded();
                }

                private void drawOrRemoveIfNeeded() {
                    android.graphics.Region region;
                    android.graphics.Rect rect;
                    boolean z;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (this.mBlastBufferQueue.mNativeObject == 0) {
                                if (this.mSurface.isValid()) {
                                    this.mTransaction.remove(this.mSurfaceControl).apply();
                                    this.mSurface.release();
                                }
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            if (!this.mInvalidated) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            this.mInvalidated = false;
                            int i = this.mAlpha;
                            android.graphics.Canvas canvas = null;
                            if (i <= 0) {
                                region = null;
                                rect = null;
                            } else {
                                region = new android.graphics.Region(this.mBounds);
                                if (this.mDirtyRect.isEmpty()) {
                                    this.mBounds.getBounds(this.mDirtyRect);
                                }
                                this.mDirtyRect.inset(-com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.this.mHalfBorderWidth, -com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.this.mHalfBorderWidth);
                                rect = new android.graphics.Rect(this.mDirtyRect);
                            }
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            if (i > 0) {
                                try {
                                    canvas = this.mSurface.lockCanvas(rect);
                                } catch (android.view.Surface.OutOfResourcesException | java.lang.IllegalArgumentException e) {
                                }
                                if (canvas != null) {
                                    canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
                                    this.mPaint.setAlpha(i);
                                    canvas.drawPath(region.getBoundaryPath(), this.mPaint);
                                    this.mSurface.unlockCanvasAndPost(canvas);
                                    z = true;
                                } else {
                                    return;
                                }
                            } else {
                                z = false;
                            }
                            if (z && !this.mLastSurfaceShown) {
                                this.mTransaction.show(this.mSurfaceControl).apply();
                                this.mLastSurfaceShown = true;
                            } else if (!z && this.mLastSurfaceShown) {
                                this.mTransaction.hide(this.mSurfaceControl).apply();
                                this.mLastSurfaceShown = false;
                            }
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }

                @com.android.internal.annotations.GuardedBy({"mService.mGlobalLock"})
                void releaseSurface() {
                    this.mBlastBufferQueue.destroy();
                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mAnimationHandler.post(this);
                }

                void dump(java.io.PrintWriter printWriter, java.lang.String str) {
                    printWriter.println(str + " mBounds= " + this.mBounds + " mDirtyRect= " + this.mDirtyRect + " mWidth= " + com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize.x + " mHeight= " + com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mScreenSize.y);
                }

                private final class AnimationController extends android.os.Handler {
                    private static final int MAX_ALPHA = 255;
                    private static final int MIN_ALPHA = 0;
                    private static final int MSG_FRAME_SHOWN_STATE_CHANGED = 1;
                    private static final java.lang.String PROPERTY_NAME_ALPHA = "alpha";
                    private final android.animation.ValueAnimator mShowHideFrameAnimator;

                    AnimationController(android.content.Context context, android.os.Looper looper) {
                        super(looper);
                        this.mShowHideFrameAnimator = android.animation.ObjectAnimator.ofInt(com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow.this, PROPERTY_NAME_ALPHA, 0, 255);
                        android.view.animation.DecelerateInterpolator decelerateInterpolator = new android.view.animation.DecelerateInterpolator(2.5f);
                        long integer = context.getResources().getInteger(android.R.integer.config_longAnimTime);
                        this.mShowHideFrameAnimator.setInterpolator(decelerateInterpolator);
                        this.mShowHideFrameAnimator.setDuration(integer);
                    }

                    void onFrameShownStateChanged(boolean z, boolean z2) {
                        obtainMessage(1, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
                    }

                    @Override // android.os.Handler
                    public void handleMessage(android.os.Message message) {
                        switch (message.what) {
                            case 1:
                                boolean z = message.arg1 == 1;
                                if (message.arg2 == 1) {
                                    if (this.mShowHideFrameAnimator.isRunning()) {
                                        this.mShowHideFrameAnimator.reverse();
                                        break;
                                    } else if (z) {
                                        this.mShowHideFrameAnimator.start();
                                        break;
                                    } else {
                                        this.mShowHideFrameAnimator.reverse();
                                        break;
                                    }
                                } else {
                                    this.mShowHideFrameAnimator.cancel();
                                    if (z) {
                                        com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow.this.setAlpha(255);
                                        break;
                                    } else {
                                        com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow.this.setAlpha(0);
                                        break;
                                    }
                                }
                        }
                    }
                }
            }
        }

        private class MyHandler extends android.os.Handler {
            public static final int MESSAGE_NOTIFY_DISPLAY_SIZE_CHANGED = 4;
            public static final int MESSAGE_NOTIFY_IME_WINDOW_VISIBILITY_CHANGED = 6;
            public static final int MESSAGE_NOTIFY_MAGNIFICATION_REGION_CHANGED = 1;
            public static final int MESSAGE_NOTIFY_USER_CONTEXT_CHANGED = 3;
            public static final int MESSAGE_SHOW_MAGNIFIED_REGION_BOUNDS_IF_NEEDED = 5;

            MyHandler(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        android.graphics.Region region = (android.graphics.Region) ((com.android.internal.os.SomeArgs) message.obj).arg1;
                        com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mCallbacks.onMagnificationRegionChanged(region);
                        region.recycle();
                        return;
                    case 2:
                    default:
                        return;
                    case 3:
                        com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mCallbacks.onUserContextChanged();
                        return;
                    case 4:
                        com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mCallbacks.onDisplaySizeChanged();
                        return;
                    case 5:
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                if (com.android.server.wm.AccessibilityController.DisplayMagnifier.this.isFullscreenMagnificationActivated()) {
                                    if (!com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder()) {
                                        com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mMagnifiedViewport.setMagnifiedRegionBorderShown(true, true);
                                    }
                                    com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mService.scheduleAnimationLocked();
                                }
                            } catch (java.lang.Throwable th) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    case 6:
                        com.android.server.wm.AccessibilityController.DisplayMagnifier.this.mCallbacks.onImeWindowVisibilityChanged(message.arg1 == 1);
                        return;
                }
            }
        }
    }

    static boolean isUntouchableNavigationBar(com.android.server.wm.WindowState windowState, android.graphics.Region region) {
        if (windowState.mAttrs.type != 2019) {
            return false;
        }
        windowState.getTouchableRegion(region);
        return region.isEmpty();
    }

    static android.graphics.Rect getSystemBarInsetsFrame(com.android.server.wm.WindowState windowState) {
        if (windowState == null) {
            return EMPTY_RECT;
        }
        com.android.server.wm.InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
        return controllableInsetProvider != null ? controllableInsetProvider.getSource().getFrame() : EMPTY_RECT;
    }

    private static final class WindowsForAccessibilityObserver {
        private static final boolean DEBUG = false;
        private static final java.lang.String LOG_TAG = "WindowManager";
        private final com.android.server.wm.AccessibilityWindowsPopulator mA11yWindowsPopulator;
        private final com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl mAccessibilityTracing;
        private final com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback mCallback;
        private final int mDisplayId;
        private final android.os.Handler mHandler;
        private boolean mInitialized;
        private final com.android.server.wm.WindowManagerService mService;
        private final java.util.Set<android.os.IBinder> mTempBinderSet = new android.util.ArraySet();
        private final android.graphics.Region mTempRegion = new android.graphics.Region();
        private final android.graphics.Region mTempRegion2 = new android.graphics.Region();
        private final long mRecurringAccessibilityEventsIntervalMillis = android.view.ViewConfiguration.getSendRecurringAccessibilityEventsInterval();

        WindowsForAccessibilityObserver(com.android.server.wm.WindowManagerService windowManagerService, int i, com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback, com.android.server.wm.AccessibilityWindowsPopulator accessibilityWindowsPopulator) {
            this.mService = windowManagerService;
            this.mCallback = windowsForAccessibilityCallback;
            this.mDisplayId = i;
            this.mHandler = new com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver.MyHandler(this.mService.mH.getLooper());
            this.mAccessibilityTracing = com.android.server.wm.AccessibilityController.getAccessibilityControllerInternal(this.mService);
            this.mA11yWindowsPopulator = accessibilityWindowsPopulator;
            computeChangedWindows(true);
        }

        void performComputeChangedWindows(boolean z) {
            if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.performComputeChangedWindows", 1024L, "forceSend=" + z);
            }
            this.mHandler.removeMessages(1);
            computeChangedWindows(z);
        }

        void scheduleComputeChangedWindows() {
            if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.scheduleComputeChangedWindows", 1024L);
            }
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.sendEmptyMessageDelayed(1, this.mRecurringAccessibilityEventsIntervalMillis);
            }
        }

        void computeChangedWindows(boolean z) {
            com.android.server.wm.WindowState topFocusWindow;
            if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.computeChangedWindows", 1024L, "forceSend=" + z);
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.graphics.Point point = new android.graphics.Point();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
                    if (recentsAnimationController != null) {
                        topFocusWindow = recentsAnimationController.getTargetAppMainWindow();
                    } else {
                        topFocusWindow = getTopFocusWindow();
                    }
                    if (topFocusWindow == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(this.mDisplayId);
                    if (displayContent == null) {
                        android.util.Slog.w(LOG_TAG, "display content is null, should be created later");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.getDisplay().getRealSize(point);
                    this.mA11yWindowsPopulator.populateVisibleWindowsOnScreenLocked(this.mDisplayId, arrayList);
                    com.android.server.accessibility.Flags.computeWindowChangesOnA11y();
                    java.util.List<android.view.WindowInfo> buildWindowInfoListLocked = buildWindowInfoListLocked(arrayList, point);
                    int displayId = this.mService.mRoot.getTopFocusedDisplayContent().getDisplayId();
                    android.os.IBinder asBinder = topFocusWindow.mClient.asBinder();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    com.android.server.accessibility.Flags.computeWindowChangesOnA11y();
                    this.mCallback.onWindowsForAccessibilityChanged(z, displayId, asBinder, buildWindowInfoListLocked);
                    java.util.Iterator<com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow> it = arrayList.iterator();
                    while (it.hasNext()) {
                        it.next().getWindowInfo().recycle();
                    }
                    this.mInitialized = true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        private java.util.List<android.view.WindowInfo> buildWindowInfoListLocked(java.util.List<com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow> list, android.graphics.Point point) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Set<android.os.IBinder> set = this.mTempBinderSet;
            set.clear();
            int size = list.size();
            android.graphics.Region region = this.mTempRegion;
            region.set(0, 0, point.x, point.y);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow = list.get(i);
                android.graphics.Region region2 = new android.graphics.Region();
                accessibilityWindow.getTouchableRegionInWindow(region2);
                if (windowMattersToAccessibility(accessibilityWindow, region2, region)) {
                    addPopulatedWindowInfo(accessibilityWindow, region2, arrayList, set);
                    if (windowMattersToUnaccountedSpaceComputation(accessibilityWindow)) {
                        updateUnaccountedSpace(accessibilityWindow, region);
                    }
                    z |= accessibilityWindow.isFocused();
                } else if (accessibilityWindow.isUntouchableNavigationBar()) {
                    region.op(com.android.server.wm.AccessibilityController.getSystemBarInsetsFrame(this.mService.mWindowMap.get(accessibilityWindow.getWindowInfo().token)), region, android.graphics.Region.Op.REVERSE_DIFFERENCE);
                }
                if (region.isEmpty() && z) {
                    break;
                }
            }
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                android.view.WindowInfo windowInfo = (android.view.WindowInfo) arrayList.get(i2);
                if (!set.contains(windowInfo.parentToken)) {
                    windowInfo.parentToken = null;
                }
                if (windowInfo.childTokens != null) {
                    for (int size3 = windowInfo.childTokens.size() - 1; size3 >= 0; size3--) {
                        if (!set.contains(windowInfo.childTokens.get(size3))) {
                            windowInfo.childTokens.remove(size3);
                        }
                    }
                }
            }
            set.clear();
            return arrayList;
        }

        private boolean windowMattersToUnaccountedSpaceComputation(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow) {
            return (accessibilityWindow.isTouchable() || accessibilityWindow.getType() == 2034 || !accessibilityWindow.isTrustedOverlay()) && accessibilityWindow.getType() != 2032;
        }

        private boolean windowMattersToAccessibility(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow, android.graphics.Region region, android.graphics.Region region2) {
            if (accessibilityWindow.ignoreRecentsAnimationForAccessibility()) {
                return false;
            }
            if (accessibilityWindow.isFocused()) {
                return true;
            }
            return (accessibilityWindow.isTouchable() || accessibilityWindow.getType() == 2034 || accessibilityWindow.isPIPMenu()) && !region2.quickReject(region) && isReportedWindowType(accessibilityWindow.getType());
        }

        private void updateUnaccountedSpace(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow, android.graphics.Region region) {
            if (accessibilityWindow.getType() != 2032) {
                android.graphics.Region region2 = this.mTempRegion2;
                accessibilityWindow.getTouchableRegionInScreen(region2);
                region.op(region2, region, android.graphics.Region.Op.REVERSE_DIFFERENCE);
            }
        }

        private static void addPopulatedWindowInfo(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow, android.graphics.Region region, java.util.List<android.view.WindowInfo> list, java.util.Set<android.os.IBinder> set) {
            android.view.WindowInfo windowInfo = accessibilityWindow.getWindowInfo();
            if (windowInfo.token == null) {
                return;
            }
            windowInfo.regionInScreen.set(region);
            windowInfo.layer = set.size();
            list.add(windowInfo);
            set.add(windowInfo.token);
        }

        private static boolean isReportedWindowType(int i) {
            return (i == 2013 || i == 2021 || i == 2026 || i == 2016 || i == 2022 || i == 2018 || i == 2027 || i == 1004 || i == 2015 || i == 2030) ? false : true;
        }

        private com.android.server.wm.WindowState getTopFocusWindow() {
            return this.mService.mRoot.getTopFocusedDisplayContent().mCurrentFocus;
        }

        public java.lang.String toString() {
            return "WindowsForAccessibilityObserver{mDisplayId=" + this.mDisplayId + ", mInitialized=" + this.mInitialized + '}';
        }

        private class MyHandler extends android.os.Handler {
            public static final int MESSAGE_COMPUTE_CHANGED_WINDOWS = 1;

            public MyHandler(android.os.Looper looper) {
                super(looper, null, false);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.wm.AccessibilityController.WindowsForAccessibilityObserver.this.computeChangedWindows(false);
                        break;
                }
            }
        }
    }

    static final class AccessibilityControllerInternalImpl implements com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal {
        private static com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl sInstance;
        private com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl.UiChangesForAccessibilityCallbacksDispatcher mCallbacksDispatcher;
        private volatile long mEnabledTracingFlags = 0;
        private final android.os.Looper mLooper;
        private final com.android.server.wm.AccessibilityController.AccessibilityTracing mTracing;

        static com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl getInstance(com.android.server.wm.WindowManagerService windowManagerService) {
            com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl accessibilityControllerInternalImpl;
            synchronized (com.android.server.wm.AccessibilityController.STATIC_LOCK) {
                try {
                    if (sInstance == null) {
                        sInstance = new com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl(windowManagerService);
                    }
                    accessibilityControllerInternalImpl = sInstance;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return accessibilityControllerInternalImpl;
        }

        private AccessibilityControllerInternalImpl(com.android.server.wm.WindowManagerService windowManagerService) {
            this.mLooper = windowManagerService.mH.getLooper();
            this.mTracing = com.android.server.wm.AccessibilityController.AccessibilityTracing.getInstance(windowManagerService);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal
        public void startTrace(long j) {
            this.mEnabledTracingFlags = j;
            this.mTracing.startTrace();
        }

        @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal
        public void stopTrace() {
            this.mTracing.stopTrace();
            this.mEnabledTracingFlags = 0L;
        }

        @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal
        public boolean isAccessibilityTracingEnabled() {
            return this.mTracing.isEnabled();
        }

        boolean isTracingEnabled(long j) {
            return (j & this.mEnabledTracingFlags) != 0;
        }

        void logTrace(java.lang.String str, long j) {
            logTrace(str, j, "");
        }

        void logTrace(java.lang.String str, long j, java.lang.String str2) {
            logTrace(str, j, str2, "".getBytes(), android.os.Binder.getCallingUid());
        }

        void logTrace(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i) {
            this.mTracing.logState(str, j, str2, bArr, i, new java.util.HashSet(java.util.Arrays.asList("logTrace")));
        }

        @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal
        public void logTrace(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.lang.StackTraceElement[] stackTraceElementArr, java.util.Set<java.lang.String> set) {
            this.mTracing.logState(str, j, str2, bArr, i, stackTraceElementArr, set);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal
        public void logTrace(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.lang.StackTraceElement[] stackTraceElementArr, long j2, int i2, long j3, java.util.Set<java.lang.String> set) {
            this.mTracing.logState(str, j, str2, bArr, i, stackTraceElementArr, j2, i2, j3, set);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal
        public void setUiChangesForAccessibilityCallbacks(com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks) {
            if (isTracingEnabled(2048L)) {
                logTrace(com.android.server.wm.AccessibilityController.TAG + ".setAccessibilityWindowManagerCallbacks", 2048L, "callbacks={" + uiChangesForAccessibilityCallbacks + "}");
            }
            if (uiChangesForAccessibilityCallbacks != null) {
                if (this.mCallbacksDispatcher != null) {
                    throw new java.lang.IllegalStateException("Accessibility window manager callback already set!");
                }
                this.mCallbacksDispatcher = new com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl.UiChangesForAccessibilityCallbacksDispatcher(this, this.mLooper, uiChangesForAccessibilityCallbacks);
            } else {
                if (this.mCallbacksDispatcher == null) {
                    throw new java.lang.IllegalStateException("Accessibility window manager callback already cleared!");
                }
                this.mCallbacksDispatcher = null;
            }
        }

        public boolean hasWindowManagerEventDispatcher() {
            if (isTracingEnabled(3072L)) {
                logTrace(com.android.server.wm.AccessibilityController.TAG + ".hasCallbacks", 3072L);
            }
            return this.mCallbacksDispatcher != null;
        }

        public void onRectangleOnScreenRequested(int i, android.graphics.Rect rect) {
            if (isTracingEnabled(2048L)) {
                logTrace(com.android.server.wm.AccessibilityController.TAG + ".onRectangleOnScreenRequested", 2048L, "rectangle={" + rect + "}");
            }
            if (this.mCallbacksDispatcher != null) {
                this.mCallbacksDispatcher.onRectangleOnScreenRequested(i, rect);
            }
        }

        private static final class UiChangesForAccessibilityCallbacksDispatcher {
            private static final boolean DEBUG_RECTANGLE_REQUESTED = false;
            private static final java.lang.String LOG_TAG = "WindowManager";
            private final com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl mAccessibilityTracing;

            @android.annotation.NonNull
            private final com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks mCallbacks;
            private final android.os.Handler mHandler;

            UiChangesForAccessibilityCallbacksDispatcher(com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl accessibilityControllerInternalImpl, android.os.Looper looper, @android.annotation.NonNull com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks) {
                this.mAccessibilityTracing = accessibilityControllerInternalImpl;
                this.mCallbacks = uiChangesForAccessibilityCallbacks;
                this.mHandler = new android.os.Handler(looper);
            }

            void onRectangleOnScreenRequested(int i, android.graphics.Rect rect) {
                if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
                    this.mAccessibilityTracing.logTrace("WindowManager.onRectangleOnScreenRequested", 2048L, "rectangle={" + rect + "}");
                }
                final com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks = this.mCallbacks;
                java.util.Objects.requireNonNull(uiChangesForAccessibilityCallbacks);
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.wm.AccessibilityController$AccessibilityControllerInternalImpl$UiChangesForAccessibilityCallbacksDispatcher$$ExternalSyntheticLambda0
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                        com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks.this.onRectangleOnScreenRequested(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue());
                    }
                }, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(rect.left), java.lang.Integer.valueOf(rect.top), java.lang.Integer.valueOf(rect.right), java.lang.Integer.valueOf(rect.bottom)));
            }
        }
    }

    private static final class AccessibilityTracing {
        private static final int BUFFER_CAPACITY = 12582912;
        private static final int CPU_STATS_COUNT = 5;
        private static final long MAGIC_NUMBER_VALUE = 4846245196254490945L;
        private static final java.lang.String TAG = "AccessibilityTracing";
        private static final java.lang.String TRACE_FILENAME = "/data/misc/a11ytrace/a11y_trace.winscope";
        private static com.android.server.wm.AccessibilityController.AccessibilityTracing sInstance;
        private volatile boolean mEnabled;
        private final com.android.server.wm.AccessibilityController.AccessibilityTracing.LogHandler mHandler;
        private final com.android.server.wm.WindowManagerService mService;
        private final java.lang.Object mLock = new java.lang.Object();
        private final java.io.File mTraceFile = new java.io.File(TRACE_FILENAME);
        private final com.android.internal.util.TraceBuffer mBuffer = new com.android.internal.util.TraceBuffer(BUFFER_CAPACITY);

        static com.android.server.wm.AccessibilityController.AccessibilityTracing getInstance(com.android.server.wm.WindowManagerService windowManagerService) {
            com.android.server.wm.AccessibilityController.AccessibilityTracing accessibilityTracing;
            synchronized (com.android.server.wm.AccessibilityController.STATIC_LOCK) {
                try {
                    if (sInstance == null) {
                        sInstance = new com.android.server.wm.AccessibilityController.AccessibilityTracing(windowManagerService);
                    }
                    accessibilityTracing = sInstance;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return accessibilityTracing;
        }

        AccessibilityTracing(com.android.server.wm.WindowManagerService windowManagerService) {
            this.mService = windowManagerService;
            android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
            handlerThread.start();
            this.mHandler = new com.android.server.wm.AccessibilityController.AccessibilityTracing.LogHandler(handlerThread.getLooper());
        }

        void startTrace() {
            if (android.os.Build.IS_USER) {
                android.util.Slog.e(TAG, "Error: Tracing is not supported on user builds.");
                return;
            }
            synchronized (this.mLock) {
                this.mEnabled = true;
                this.mBuffer.resetBuffer();
            }
        }

        void stopTrace() {
            if (android.os.Build.IS_USER) {
                android.util.Slog.e(TAG, "Error: Tracing is not supported on user builds.");
                return;
            }
            synchronized (this.mLock) {
                try {
                    this.mEnabled = false;
                    if (this.mEnabled) {
                        android.util.Slog.e(TAG, "Error: tracing enabled while waiting for flush.");
                    } else {
                        writeTraceToFile();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        boolean isEnabled() {
            return this.mEnabled;
        }

        void logState(java.lang.String str, long j) {
            if (!this.mEnabled) {
                return;
            }
            logState(str, j, "");
        }

        void logState(java.lang.String str, long j, java.lang.String str2) {
            if (!this.mEnabled) {
                return;
            }
            logState(str, j, str2, "".getBytes());
        }

        void logState(java.lang.String str, long j, java.lang.String str2, byte[] bArr) {
            if (!this.mEnabled) {
                return;
            }
            logState(str, j, str2, bArr, android.os.Binder.getCallingUid(), new java.util.HashSet(java.util.Arrays.asList("logState")));
        }

        void logState(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.util.Set<java.lang.String> set) {
            if (!this.mEnabled) {
                return;
            }
            java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
            set.add("logState");
            logState(str, j, str2, bArr, i, stackTrace, set);
        }

        void logState(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.lang.StackTraceElement[] stackTraceElementArr, java.util.Set<java.lang.String> set) {
            if (!this.mEnabled) {
                return;
            }
            log(str, j, str2, bArr, i, stackTraceElementArr, android.os.SystemClock.elapsedRealtimeNanos(), android.os.Process.myPid() + ":" + android.app.Application.getProcessName(), java.lang.Thread.currentThread().getId() + ":" + java.lang.Thread.currentThread().getName(), set);
        }

        void logState(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.lang.StackTraceElement[] stackTraceElementArr, long j2, int i2, long j3, java.util.Set<java.lang.String> set) {
            if (!this.mEnabled) {
                return;
            }
            log(str, j, str2, bArr, i, stackTraceElementArr, j2, java.lang.String.valueOf(i2), java.lang.String.valueOf(j3), set);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String toStackTraceString(java.lang.StackTraceElement[] stackTraceElementArr, java.util.Set<java.lang.String> set) {
            if (stackTraceElementArr == null) {
                return "";
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int i = 0;
            int i2 = -1;
            while (i < stackTraceElementArr.length) {
                java.util.Iterator<java.lang.String> it = set.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (stackTraceElementArr[i].toString().contains(it.next())) {
                        i2 = i;
                        break;
                    }
                }
                if (i2 >= 0) {
                    break;
                }
                i++;
            }
            if (i < stackTraceElementArr.length) {
                do {
                    i++;
                    if (i >= stackTraceElementArr.length) {
                        break;
                    }
                    java.util.Iterator<java.lang.String> it2 = set.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (stackTraceElementArr[i].toString().contains(it2.next())) {
                            i2 = i;
                            break;
                        }
                    }
                } while (i2 == i);
            }
            while (true) {
                i2++;
                if (i2 < stackTraceElementArr.length) {
                    sb.append(stackTraceElementArr[i2].toString());
                    sb.append("\n");
                } else {
                    return sb.toString();
                }
            }
        }

        private void log(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.lang.StackTraceElement[] stackTraceElementArr, long j2, java.lang.String str3, java.lang.String str4, java.util.Set<java.lang.String> set) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argl1 = j2;
            obtain.argl2 = j;
            obtain.arg1 = str;
            obtain.arg2 = str3;
            obtain.arg3 = str4;
            obtain.arg4 = set;
            obtain.arg5 = str2;
            obtain.arg6 = stackTraceElementArr;
            obtain.arg7 = bArr;
            this.mHandler.obtainMessage(1, i, 0, obtain).sendToTarget();
        }

        void writeTraceToFile() {
            this.mHandler.sendEmptyMessage(2);
        }

        private class LogHandler extends android.os.Handler {
            public static final int MESSAGE_LOG_TRACE_ENTRY = 1;
            public static final int MESSAGE_WRITE_FILE = 2;

            LogHandler(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                        try {
                            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                            long start = protoOutputStream.start(2246267895810L);
                            long j = someArgs.argl1;
                            long time = new java.util.Date().getTime() - ((android.os.SystemClock.elapsedRealtimeNanos() - j) / 1000000);
                            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
                            protoOutputStream.write(1125281431553L, j);
                            protoOutputStream.write(1138166333442L, simpleDateFormat.format(java.lang.Long.valueOf(time)).toString());
                            java.util.Iterator it = android.accessibilityservice.AccessibilityTrace.getNamesOfLoggingTypes(someArgs.argl2).iterator();
                            while (it.hasNext()) {
                                protoOutputStream.write(2237677961219L, (java.lang.String) it.next());
                            }
                            protoOutputStream.write(1138166333446L, (java.lang.String) someArgs.arg1);
                            protoOutputStream.write(1138166333444L, (java.lang.String) someArgs.arg2);
                            protoOutputStream.write(1138166333445L, (java.lang.String) someArgs.arg3);
                            protoOutputStream.write(1138166333447L, packageManagerInternal.getNameForUid(message.arg1));
                            protoOutputStream.write(1138166333448L, (java.lang.String) someArgs.arg5);
                            protoOutputStream.write(1138166333449L, com.android.server.wm.AccessibilityController.AccessibilityTracing.this.toStackTraceString((java.lang.StackTraceElement[]) someArgs.arg6, (java.util.Set) someArgs.arg4));
                            protoOutputStream.write(1146756268042L, (byte[]) someArgs.arg7);
                            long start2 = protoOutputStream.start(1146756268043L);
                            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.AccessibilityController.AccessibilityTracing.this.mService.mGlobalLock;
                            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    com.android.server.wm.AccessibilityController.AccessibilityTracing.this.mService.dumpDebugLocked(protoOutputStream, 0);
                                } catch (java.lang.Throwable th) {
                                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            protoOutputStream.end(start2);
                            protoOutputStream.write(1138166333452L, com.android.server.wm.AccessibilityController.AccessibilityTracing.this.printCpuStats(j));
                            protoOutputStream.end(start);
                            synchronized (com.android.server.wm.AccessibilityController.AccessibilityTracing.this.mLock) {
                                com.android.server.wm.AccessibilityController.AccessibilityTracing.this.mBuffer.add(protoOutputStream);
                            }
                            return;
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.wm.AccessibilityController.AccessibilityTracing.TAG, "Exception while tracing state", e);
                            return;
                        }
                    case 2:
                        synchronized (com.android.server.wm.AccessibilityController.AccessibilityTracing.this.mLock) {
                            com.android.server.wm.AccessibilityController.AccessibilityTracing.this.writeTraceToFileInternal();
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeTraceToFileInternal() {
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                protoOutputStream.write(1125281431553L, MAGIC_NUMBER_VALUE);
                protoOutputStream.write(1125281431555L, java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(java.lang.System.currentTimeMillis()) - android.os.SystemClock.elapsedRealtimeNanos());
                this.mBuffer.writeTraceToFile(this.mTraceFile, protoOutputStream);
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Unable to write buffer to file", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String printCpuStats(long j) {
            android.util.Pair appProfileStatsForDebugging = this.mService.mAmInternal.getAppProfileStatsForDebugging(j, 5);
            return ((java.lang.String) appProfileStatsForDebugging.first) + ((java.lang.String) appProfileStatsForDebugging.second);
        }
    }
}
