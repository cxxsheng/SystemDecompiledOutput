package com.android.server.accessibility;

/* loaded from: classes.dex */
public class AccessibilityWindowManager {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "AccessibilityWindowManager";
    private static final boolean VERBOSE = false;
    private static int sNextWindowId;
    private final com.android.server.accessibility.AccessibilityWindowManager.AccessibilityEventSender mAccessibilityEventSender;
    private final com.android.server.accessibility.AccessibilitySecurityPolicy.AccessibilityUserManager mAccessibilityUserManager;
    private final android.os.Handler mHandler;
    private boolean mHasProxy;
    private int mLastNonProxyTopFocusedDisplayId;
    private final java.lang.Object mLock;
    private com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection mPictureInPictureActionReplacingConnection;
    private final com.android.server.accessibility.AccessibilitySecurityPolicy mSecurityPolicy;
    private int mTopFocusedDisplayId;
    private android.os.IBinder mTopFocusedWindowToken;
    private boolean mTouchInteractionInProgress;
    private final com.android.server.accessibility.AccessibilityTraceManager mTraceManager;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    private final android.util.SparseArray<com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection> mGlobalInteractionConnections = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.os.IBinder> mGlobalWindowTokens = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.util.SparseArray<com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection>> mInteractionConnections = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.util.SparseArray<android.os.IBinder>> mWindowTokens = new android.util.SparseArray<>();
    private int mActiveWindowId = -1;
    private int mTopFocusedWindowId = -1;
    private int mAccessibilityFocusedWindowId = -1;
    private long mAccessibilityFocusNodeId = 2147483647L;
    private int mAccessibilityFocusedDisplayId = -1;
    private final android.util.SparseArray<com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver> mDisplayWindowsObservers = new android.util.SparseArray<>();
    private final android.util.ArrayMap<android.os.IBinder, android.os.IBinder> mHostEmbeddedMap = new android.util.ArrayMap<>();
    private final android.util.SparseArray<android.os.IBinder> mWindowIdMap = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.view.accessibility.AccessibilityWindowAttributes> mWindowAttributes = new android.util.SparseArray<>();

    public interface AccessibilityEventSender {
        void sendAccessibilityEventForCurrentUserLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent);
    }

    public void setAccessibilityWindowAttributes(int i, int i2, int i3, android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes) {
        synchronized (this.mLock) {
            try {
                if (getWindowTokenForUserAndWindowIdLocked(this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i3), i2) == null) {
                    return;
                }
                this.mWindowAttributes.put(i2, accessibilityWindowAttributes);
                boolean z = findWindowInfoByIdLocked(i2) != null;
                if (z) {
                    this.mWindowManagerInternal.computeWindowsForAccessibility(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean windowIdBelongsToDisplayType(int i, int i2) {
        boolean z = true;
        if (!this.mHasProxy || (i2 & 3) == 3) {
            return true;
        }
        synchronized (this.mLock) {
            try {
                int size = this.mDisplayWindowsObservers.size();
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i3);
                    if (valueAt != null && valueAt.findA11yWindowInfoByIdLocked(i) != null) {
                        if (valueAt.mIsProxy) {
                            if ((i2 & 2) == 0) {
                                z = false;
                            }
                        } else if ((i2 & 1) == 0) {
                            z = false;
                        }
                        return z;
                    }
                }
                return false;
            } finally {
            }
        }
    }

    private final class DisplayWindowsObserver implements com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback {
        private final int mDisplayId;
        private boolean mHasWatchOutsideTouchWindow;
        private boolean mIsProxy;
        private java.util.List<android.view.accessibility.AccessibilityWindowInfo> mWindows;
        private final android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> mA11yWindowInfoById = new android.util.SparseArray<>();
        private final android.util.SparseArray<android.view.WindowInfo> mWindowInfoById = new android.util.SparseArray<>();
        private final java.util.List<android.view.WindowInfo> mCachedWindowInfos = new java.util.ArrayList();
        private boolean mTrackingWindows = false;
        private int mProxyDisplayAccessibilityFocusedWindow = -1;

        DisplayWindowsObserver(int i) {
            this.mDisplayId = i;
        }

        void startTrackingWindowsLocked() {
            if (!this.mTrackingWindows) {
                this.mTrackingWindows = true;
                if (com.android.server.accessibility.AccessibilityWindowManager.this.traceWMEnabled()) {
                    com.android.server.accessibility.AccessibilityWindowManager.this.logTraceWM("setWindowsForAccessibilityCallback", "displayId=" + this.mDisplayId + ";callback=" + this);
                }
                com.android.server.accessibility.AccessibilityWindowManager.this.mWindowManagerInternal.setWindowsForAccessibilityCallback(this.mDisplayId, this);
            }
        }

        void stopTrackingWindowsLocked() {
            if (this.mTrackingWindows) {
                if (com.android.server.accessibility.AccessibilityWindowManager.this.traceWMEnabled()) {
                    com.android.server.accessibility.AccessibilityWindowManager.this.logTraceWM("setWindowsForAccessibilityCallback", "displayId=" + this.mDisplayId + ";callback=null");
                }
                com.android.server.accessibility.AccessibilityWindowManager.this.mWindowManagerInternal.setWindowsForAccessibilityCallback(this.mDisplayId, null);
                this.mTrackingWindows = false;
                clearWindowsLocked();
            }
        }

        boolean isTrackingWindowsLocked() {
            return this.mTrackingWindows;
        }

        @android.annotation.Nullable
        java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindowListLocked() {
            return this.mWindows;
        }

        @android.annotation.Nullable
        android.view.accessibility.AccessibilityWindowInfo findA11yWindowInfoByIdLocked(int i) {
            return this.mA11yWindowInfoById.get(i);
        }

        @android.annotation.Nullable
        android.view.WindowInfo findWindowInfoByIdLocked(int i) {
            return this.mWindowInfoById.get(i);
        }

        @android.annotation.Nullable
        android.view.accessibility.AccessibilityWindowInfo getPictureInPictureWindowLocked() {
            if (this.mWindows != null) {
                int size = this.mWindows.size();
                for (int i = 0; i < size; i++) {
                    android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = this.mWindows.get(i);
                    if (accessibilityWindowInfo.isInPictureInPictureMode()) {
                        return accessibilityWindowInfo;
                    }
                }
                return null;
            }
            return null;
        }

        boolean setActiveWindowLocked(int i) {
            if (this.mWindows == null) {
                return false;
            }
            int size = this.mWindows.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = this.mWindows.get(i2);
                if (accessibilityWindowInfo.getId() == i) {
                    z = true;
                    accessibilityWindowInfo.setActive(true);
                } else {
                    accessibilityWindowInfo.setActive(false);
                }
            }
            return z;
        }

        boolean setAccessibilityFocusedWindowLocked(int i) {
            if (this.mWindows == null) {
                return false;
            }
            int size = this.mWindows.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = this.mWindows.get(i2);
                if (accessibilityWindowInfo.getId() == i) {
                    z = true;
                    accessibilityWindowInfo.setAccessibilityFocused(true);
                } else {
                    accessibilityWindowInfo.setAccessibilityFocused(false);
                }
            }
            return z;
        }

        boolean computePartialInteractiveRegionForWindowLocked(int i, boolean z, @android.annotation.NonNull android.graphics.Region region) {
            boolean z2 = false;
            if (this.mWindows == null) {
                return false;
            }
            int size = this.mWindows.size();
            android.graphics.Region region2 = new android.graphics.Region();
            android.graphics.Region region3 = null;
            for (int i2 = size - 1; i2 >= 0; i2--) {
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = this.mWindows.get(i2);
                if (region3 == null) {
                    if (accessibilityWindowInfo.getId() == i) {
                        accessibilityWindowInfo.getRegionInScreen(region2);
                        region.set(region2);
                        if (!z) {
                            region3 = region;
                        } else {
                            region3 = region;
                            z2 = true;
                        }
                    }
                } else if (accessibilityWindowInfo.getType() != 4) {
                    accessibilityWindowInfo.getRegionInScreen(region2);
                    if (region3.op(region2, android.graphics.Region.Op.DIFFERENCE)) {
                        z2 = true;
                    }
                }
            }
            return z2;
        }

        java.util.List<java.lang.Integer> getWatchOutsideTouchWindowIdLocked(int i) {
            android.view.WindowInfo windowInfo = this.mWindowInfoById.get(i);
            if (windowInfo != null && this.mHasWatchOutsideTouchWindow) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i2 = 0; i2 < this.mWindowInfoById.size(); i2++) {
                    android.view.WindowInfo valueAt = this.mWindowInfoById.valueAt(i2);
                    if (valueAt != null && valueAt.layer < windowInfo.layer && valueAt.hasFlagWatchOutsideTouch) {
                        arrayList.add(java.lang.Integer.valueOf(this.mWindowInfoById.keyAt(i2)));
                    }
                }
                return arrayList;
            }
            return java.util.Collections.emptyList();
        }

        @Override // com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback
        public void onWindowsForAccessibilityChanged(boolean z, int i, android.os.IBinder iBinder, @android.annotation.NonNull java.util.List<android.view.WindowInfo> list) {
            synchronized (com.android.server.accessibility.AccessibilityWindowManager.this.mLock) {
                try {
                    updateWindowsByWindowAttributesLocked(list);
                    if (shouldUpdateWindowsLocked(z, list)) {
                        com.android.server.accessibility.AccessibilityWindowManager.this.mTopFocusedDisplayId = i;
                        if (!com.android.server.accessibility.AccessibilityWindowManager.this.isProxyed(i)) {
                            com.android.server.accessibility.AccessibilityWindowManager.this.mLastNonProxyTopFocusedDisplayId = i;
                        }
                        com.android.server.accessibility.AccessibilityWindowManager.this.mTopFocusedWindowToken = iBinder;
                        cacheWindows(list);
                        updateWindowsLocked(com.android.server.accessibility.AccessibilityWindowManager.this.mAccessibilityUserManager.getCurrentUserIdLocked(), list);
                        com.android.server.accessibility.AccessibilityWindowManager.this.mLock.notifyAll();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private static /* synthetic */ java.lang.String lambda$onWindowsForAccessibilityChanged$0(android.view.WindowInfo windowInfo) {
            return "{displayId=" + windowInfo.displayId + ", title=" + ((java.lang.Object) windowInfo.title) + "}";
        }

        @Override // com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback
        public void onAccessibilityWindowsChanged(boolean z, int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.graphics.Point point, @android.annotation.NonNull java.util.List<com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow> list) {
            onWindowsForAccessibilityChanged(z, i, iBinder, createWindowInfoList(point, list));
        }

        private static java.util.List<android.view.WindowInfo> createWindowInfoList(@android.annotation.NonNull android.graphics.Point point, @android.annotation.NonNull java.util.List<com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow> list) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            java.util.ArrayList<android.view.WindowInfo> arrayList = new java.util.ArrayList();
            android.graphics.Region region = new android.graphics.Region();
            android.graphics.Region region2 = new android.graphics.Region();
            boolean z = false;
            android.graphics.Region region3 = new android.graphics.Region(0, 0, point.x, point.y);
            for (com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow : list) {
                accessibilityWindow.getTouchableRegionInWindow(region);
                if (windowMattersToAccessibility(accessibilityWindow, region, region3)) {
                    android.view.WindowInfo windowInfo = accessibilityWindow.getWindowInfo();
                    if (windowInfo.token != null) {
                        windowInfo.regionInScreen.set(region);
                        windowInfo.layer = arraySet.size();
                        arrayList.add(windowInfo);
                        arraySet.add(windowInfo.token);
                    }
                    if (windowMattersToUnaccountedSpaceComputation(accessibilityWindow)) {
                        accessibilityWindow.getTouchableRegionInScreen(region2);
                        region3.op(region2, region3, android.graphics.Region.Op.REVERSE_DIFFERENCE);
                    }
                    z = accessibilityWindow.isFocused() | z;
                } else if (accessibilityWindow.isUntouchableNavigationBar() && accessibilityWindow.getSystemBarInsetsFrame() != null) {
                    region3.op(accessibilityWindow.getSystemBarInsetsFrame(), region3, android.graphics.Region.Op.REVERSE_DIFFERENCE);
                }
                if (region3.isEmpty() && z) {
                    break;
                }
            }
            for (android.view.WindowInfo windowInfo2 : arrayList) {
                if (!arraySet.contains(windowInfo2.parentToken)) {
                    windowInfo2.parentToken = null;
                }
                if (windowInfo2.childTokens != null) {
                    for (int size = windowInfo2.childTokens.size() - 1; size >= 0; size--) {
                        if (!arraySet.contains(windowInfo2.childTokens.get(size))) {
                            windowInfo2.childTokens.remove(size);
                        }
                    }
                }
            }
            return arrayList;
        }

        private static boolean windowMattersToAccessibility(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow, android.graphics.Region region, android.graphics.Region region2) {
            if (accessibilityWindow.ignoreRecentsAnimationForAccessibility()) {
                return false;
            }
            if (accessibilityWindow.isFocused()) {
                return true;
            }
            return (accessibilityWindow.isTouchable() || accessibilityWindow.getType() == 2034 || accessibilityWindow.isPIPMenu()) && !region2.quickReject(region) && isReportedWindowType(accessibilityWindow.getType());
        }

        private static boolean isReportedWindowType(int i) {
            return (i == 2013 || i == 2021 || i == 2026 || i == 2016 || i == 2022 || i == 2018 || i == 2027 || i == 1004 || i == 2015 || i == 2030) ? false : true;
        }

        private static boolean windowMattersToUnaccountedSpaceComputation(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow) {
            return (accessibilityWindow.isTouchable() || accessibilityWindow.getType() == 2034 || !accessibilityWindow.isTrustedOverlay()) && accessibilityWindow.getType() != 2032;
        }

        private void updateWindowsByWindowAttributesLocked(java.util.List<android.view.WindowInfo> list) {
            for (int size = list.size() - 1; size >= 0; size--) {
                android.view.WindowInfo windowInfo = list.get(size);
                updateWindowWithWindowAttributes(windowInfo, (android.view.accessibility.AccessibilityWindowAttributes) com.android.server.accessibility.AccessibilityWindowManager.this.mWindowAttributes.get(com.android.server.accessibility.AccessibilityWindowManager.this.findWindowIdLocked(com.android.server.accessibility.AccessibilityWindowManager.this.mAccessibilityUserManager.getCurrentUserIdLocked(), windowInfo.token)));
            }
        }

        private void updateWindowWithWindowAttributes(@android.annotation.NonNull android.view.WindowInfo windowInfo, @android.annotation.Nullable android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes) {
            if (accessibilityWindowAttributes == null) {
                return;
            }
            windowInfo.title = accessibilityWindowAttributes.getWindowTitle();
            windowInfo.locales = accessibilityWindowAttributes.getLocales();
        }

        private boolean shouldUpdateWindowsLocked(boolean z, @android.annotation.NonNull java.util.List<android.view.WindowInfo> list) {
            int size;
            if (z || this.mCachedWindowInfos.size() != (size = list.size())) {
                return true;
            }
            if (!this.mCachedWindowInfos.isEmpty() || !list.isEmpty()) {
                for (int i = 0; i < size; i++) {
                    if (windowChangedNoLayer(this.mCachedWindowInfos.get(i), list.get(i))) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void cacheWindows(java.util.List<android.view.WindowInfo> list) {
            for (int size = this.mCachedWindowInfos.size() - 1; size >= 0; size--) {
                this.mCachedWindowInfos.remove(size).recycle();
            }
            int size2 = list.size();
            for (int i = 0; i < size2; i++) {
                this.mCachedWindowInfos.add(android.view.WindowInfo.obtain(list.get(i)));
            }
        }

        private boolean windowChangedNoLayer(android.view.WindowInfo windowInfo, android.view.WindowInfo windowInfo2) {
            if (windowInfo == windowInfo2) {
                return false;
            }
            if (windowInfo == null || windowInfo2 == null || windowInfo.type != windowInfo2.type || windowInfo.focused != windowInfo2.focused) {
                return true;
            }
            if (windowInfo.token == null) {
                if (windowInfo2.token != null) {
                    return true;
                }
            } else if (!windowInfo.token.equals(windowInfo2.token)) {
                return true;
            }
            if (windowInfo.parentToken == null) {
                if (windowInfo2.parentToken != null) {
                    return true;
                }
            } else if (!windowInfo.parentToken.equals(windowInfo2.parentToken)) {
                return true;
            }
            if (windowInfo.activityToken == null) {
                if (windowInfo2.activityToken != null) {
                    return true;
                }
            } else if (!windowInfo.activityToken.equals(windowInfo2.activityToken)) {
                return true;
            }
            if (!windowInfo.regionInScreen.equals(windowInfo2.regionInScreen)) {
                return true;
            }
            if ((windowInfo.childTokens == null || windowInfo2.childTokens == null || windowInfo.childTokens.equals(windowInfo2.childTokens)) && android.text.TextUtils.equals(windowInfo.title, windowInfo2.title) && windowInfo.accessibilityIdOfAnchor == windowInfo2.accessibilityIdOfAnchor && windowInfo.inPictureInPicture == windowInfo2.inPictureInPicture && windowInfo.hasFlagWatchOutsideTouch == windowInfo2.hasFlagWatchOutsideTouch && windowInfo.displayId == windowInfo2.displayId && windowInfo.taskId == windowInfo2.taskId && java.util.Arrays.equals(windowInfo.mTransformMatrix, windowInfo2.mTransformMatrix)) {
                return false;
            }
            return true;
        }

        private void clearWindowsLocked() {
            java.util.List<android.view.WindowInfo> emptyList = java.util.Collections.emptyList();
            int i = com.android.server.accessibility.AccessibilityWindowManager.this.mActiveWindowId;
            updateWindowsLocked(com.android.server.accessibility.AccessibilityWindowManager.this.mAccessibilityUserManager.getCurrentUserIdLocked(), emptyList);
            com.android.server.accessibility.AccessibilityWindowManager.this.mActiveWindowId = i;
            this.mWindows = null;
        }

        private void updateWindowsLocked(int i, @android.annotation.NonNull java.util.List<android.view.WindowInfo> list) {
            int i2;
            boolean z;
            boolean z2;
            android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo;
            int i3 = i;
            if (this.mWindows == null) {
                this.mWindows = new java.util.ArrayList();
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mWindows);
            android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> clone = this.mA11yWindowInfoById.clone();
            this.mWindows.clear();
            this.mA11yWindowInfoById.clear();
            for (int i4 = 0; i4 < this.mWindowInfoById.size(); i4++) {
                this.mWindowInfoById.valueAt(i4).recycle();
            }
            this.mWindowInfoById.clear();
            this.mHasWatchOutsideTouchWindow = false;
            int size = list.size();
            boolean z3 = this.mDisplayId == com.android.server.accessibility.AccessibilityWindowManager.this.mTopFocusedDisplayId;
            boolean z4 = this.mDisplayId == com.android.server.accessibility.AccessibilityWindowManager.this.mAccessibilityFocusedDisplayId || (this.mIsProxy && this.mProxyDisplayAccessibilityFocusedWindow != -1);
            if (z3) {
                if (size > 0) {
                    com.android.server.accessibility.AccessibilityWindowManager.this.mTopFocusedWindowId = com.android.server.accessibility.AccessibilityWindowManager.this.findWindowIdLocked(i3, com.android.server.accessibility.AccessibilityWindowManager.this.mTopFocusedWindowToken);
                } else {
                    com.android.server.accessibility.AccessibilityWindowManager.this.mTopFocusedWindowId = -1;
                }
                if (!com.android.server.accessibility.AccessibilityWindowManager.this.mTouchInteractionInProgress) {
                    com.android.server.accessibility.AccessibilityWindowManager.this.mActiveWindowId = -1;
                }
            }
            if (this.mIsProxy) {
                i2 = this.mProxyDisplayAccessibilityFocusedWindow;
            } else {
                i2 = com.android.server.accessibility.AccessibilityWindowManager.this.mAccessibilityFocusedWindowId;
            }
            if (!z4) {
                z = false;
            } else {
                z = i2 != -1;
            }
            if (size > 0) {
                int i5 = 0;
                boolean z5 = false;
                boolean z6 = true;
                while (i5 < size) {
                    android.view.WindowInfo windowInfo = list.get(i5);
                    if (this.mTrackingWindows) {
                        accessibilityWindowInfo = populateReportedWindowLocked(i3, windowInfo, clone);
                        if (accessibilityWindowInfo == null) {
                            z5 = true;
                        }
                    } else {
                        accessibilityWindowInfo = null;
                    }
                    if (accessibilityWindowInfo != null) {
                        accessibilityWindowInfo.setLayer((size - 1) - accessibilityWindowInfo.getLayer());
                        int id = accessibilityWindowInfo.getId();
                        if (accessibilityWindowInfo.isFocused() && z3) {
                            if (!com.android.server.accessibility.AccessibilityWindowManager.this.mTouchInteractionInProgress) {
                                com.android.server.accessibility.AccessibilityWindowManager.this.mActiveWindowId = id;
                                accessibilityWindowInfo.setActive(true);
                            } else if (id == com.android.server.accessibility.AccessibilityWindowManager.this.mActiveWindowId) {
                                z6 = false;
                            }
                        }
                        if (!this.mHasWatchOutsideTouchWindow && windowInfo.hasFlagWatchOutsideTouch) {
                            this.mHasWatchOutsideTouchWindow = true;
                        }
                        this.mWindows.add(accessibilityWindowInfo);
                        this.mA11yWindowInfoById.put(id, accessibilityWindowInfo);
                        this.mWindowInfoById.put(id, android.view.WindowInfo.obtain(windowInfo));
                    }
                    i5++;
                    i3 = i;
                }
                int size2 = this.mWindows.size();
                if (z5) {
                    for (int i6 = 0; i6 < size2; i6++) {
                        this.mWindows.get(i6).setLayer((size2 - 1) - i6);
                    }
                }
                if (z3) {
                    if (com.android.server.accessibility.AccessibilityWindowManager.this.mTouchInteractionInProgress && z6) {
                        com.android.server.accessibility.AccessibilityWindowManager.this.mActiveWindowId = com.android.server.accessibility.AccessibilityWindowManager.this.mTopFocusedWindowId;
                    }
                    for (int i7 = 0; i7 < size2; i7++) {
                        android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo2 = this.mWindows.get(i7);
                        if (accessibilityWindowInfo2.getId() == com.android.server.accessibility.AccessibilityWindowManager.this.mActiveWindowId) {
                            accessibilityWindowInfo2.setActive(true);
                        }
                    }
                }
                if (z4) {
                    for (int i8 = 0; i8 < size2; i8++) {
                        android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo3 = this.mWindows.get(i8);
                        if (accessibilityWindowInfo3.getId() == i2) {
                            accessibilityWindowInfo3.setAccessibilityFocused(true);
                            z2 = false;
                            break;
                        }
                    }
                }
            }
            z2 = z;
            sendEventsForChangedWindowsLocked(arrayList, clone);
            for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                arrayList.remove(size3).recycle();
            }
            if (z2) {
                com.android.server.accessibility.AccessibilityWindowManager.this.clearAccessibilityFocusLocked(i2);
            }
        }

        private void sendEventsForChangedWindowsLocked(java.util.List<android.view.accessibility.AccessibilityWindowInfo> list, android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> sparseArray) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = list.get(i);
                if (this.mA11yWindowInfoById.get(accessibilityWindowInfo.getId()) == null) {
                    arrayList.add(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(this.mDisplayId, accessibilityWindowInfo.getId(), 2));
                }
            }
            int size2 = this.mWindows.size();
            for (int i2 = 0; i2 < size2; i2++) {
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo2 = this.mWindows.get(i2);
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo3 = sparseArray.get(accessibilityWindowInfo2.getId());
                if (accessibilityWindowInfo3 == null) {
                    arrayList.add(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(this.mDisplayId, accessibilityWindowInfo2.getId(), 1));
                } else {
                    int differenceFrom = accessibilityWindowInfo2.differenceFrom(accessibilityWindowInfo3);
                    if (differenceFrom != 0) {
                        arrayList.add(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(this.mDisplayId, accessibilityWindowInfo2.getId(), differenceFrom));
                    }
                }
            }
            int size3 = arrayList.size();
            for (int i3 = 0; i3 < size3; i3++) {
                com.android.server.accessibility.AccessibilityWindowManager.this.mAccessibilityEventSender.sendAccessibilityEventForCurrentUserLocked((android.view.accessibility.AccessibilityEvent) arrayList.get(i3));
            }
        }

        private android.view.accessibility.AccessibilityWindowInfo populateReportedWindowLocked(int i, android.view.WindowInfo windowInfo, android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> sparseArray) {
            int findWindowIdLocked = com.android.server.accessibility.AccessibilityWindowManager.this.findWindowIdLocked(i, windowInfo.token);
            if (findWindowIdLocked < 0 || com.android.server.accessibility.AccessibilityWindowManager.this.isEmbeddedHierarchyWindowsLocked(findWindowIdLocked)) {
                return null;
            }
            android.view.accessibility.AccessibilityWindowInfo obtain = android.view.accessibility.AccessibilityWindowInfo.obtain();
            obtain.setId(findWindowIdLocked);
            obtain.setType(getTypeForWindowManagerWindowType(windowInfo.type));
            obtain.setLayer(windowInfo.layer);
            obtain.setFocused(windowInfo.focused);
            obtain.setRegionInScreen(windowInfo.regionInScreen);
            obtain.setTitle(windowInfo.title);
            obtain.setAnchorId(windowInfo.accessibilityIdOfAnchor);
            obtain.setPictureInPicture(windowInfo.inPictureInPicture);
            obtain.setDisplayId(windowInfo.displayId);
            obtain.setTaskId(windowInfo.taskId);
            obtain.setLocales(windowInfo.locales);
            int findWindowIdLocked2 = com.android.server.accessibility.AccessibilityWindowManager.this.findWindowIdLocked(i, windowInfo.parentToken);
            if (findWindowIdLocked2 >= 0) {
                obtain.setParentId(findWindowIdLocked2);
            }
            if (windowInfo.childTokens != null) {
                int size = windowInfo.childTokens.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int findWindowIdLocked3 = com.android.server.accessibility.AccessibilityWindowManager.this.findWindowIdLocked(i, (android.os.IBinder) windowInfo.childTokens.get(i2));
                    if (findWindowIdLocked3 >= 0) {
                        obtain.addChild(findWindowIdLocked3);
                    }
                }
            }
            android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = sparseArray.get(findWindowIdLocked);
            if (accessibilityWindowInfo == null) {
                obtain.setTransitionTimeMillis(android.os.SystemClock.uptimeMillis());
            } else {
                android.graphics.Region region = new android.graphics.Region();
                accessibilityWindowInfo.getRegionInScreen(region);
                if (region.equals(windowInfo.regionInScreen)) {
                    obtain.setTransitionTimeMillis(accessibilityWindowInfo.getTransitionTimeMillis());
                } else {
                    obtain.setTransitionTimeMillis(android.os.SystemClock.uptimeMillis());
                }
            }
            return obtain;
        }

        private int getTypeForWindowManagerWindowType(int i) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 1000:
                case 1001:
                case 1002:
                case 1003:
                case 1005:
                case 2002:
                case 2005:
                case 2007:
                case 2012:
                    return 1;
                case 2000:
                case 2001:
                case 2003:
                case 2006:
                case 2008:
                case 2009:
                case 2010:
                case 2017:
                case 2019:
                case com.android.server.notification.NotificationShellCmd.NOTIFICATION_ID /* 2020 */:
                case 2024:
                case 2036:
                case 2038:
                case 2040:
                case 2041:
                    return 3;
                case 2011:
                    return 2;
                case 2032:
                    return 4;
                case 2034:
                    return 5;
                case 2039:
                    return 6;
                default:
                    return -1;
            }
        }

        void dumpLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (this.mIsProxy) {
                printWriter.println("Proxy accessibility focused window = " + this.mProxyDisplayAccessibilityFocusedWindow);
                printWriter.println();
            }
            if (this.mWindows != null) {
                int size = this.mWindows.size();
                for (int i = 0; i < size; i++) {
                    if (i == 0) {
                        printWriter.append("Display[");
                        printWriter.append((java.lang.CharSequence) java.lang.Integer.toString(this.mDisplayId));
                        printWriter.append("] : ");
                        printWriter.println();
                    }
                    if (i > 0) {
                        printWriter.append(',');
                        printWriter.println();
                    }
                    printWriter.append("A11yWindow[");
                    android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = this.mWindows.get(i);
                    printWriter.append((java.lang.CharSequence) accessibilityWindowInfo.toString());
                    printWriter.append(']');
                    printWriter.println();
                    android.view.WindowInfo findWindowInfoByIdLocked = findWindowInfoByIdLocked(accessibilityWindowInfo.getId());
                    if (findWindowInfoByIdLocked != null) {
                        printWriter.append("WindowInfo[");
                        printWriter.append((java.lang.CharSequence) findWindowInfoByIdLocked.toString());
                        printWriter.append("]");
                        printWriter.println();
                    }
                }
                printWriter.println();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public final class RemoteAccessibilityConnection implements android.os.IBinder.DeathRecipient {
        private final android.view.accessibility.IAccessibilityInteractionConnection mConnection;
        private final java.lang.String mPackageName;
        private final int mUid;
        private final int mUserId;
        private final int mWindowId;

        RemoteAccessibilityConnection(int i, android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, java.lang.String str, int i2, int i3) {
            this.mWindowId = i;
            this.mPackageName = str;
            this.mUid = i2;
            this.mUserId = i3;
            this.mConnection = iAccessibilityInteractionConnection;
        }

        int getUid() {
            return this.mUid;
        }

        java.lang.String getPackageName() {
            return this.mPackageName;
        }

        android.view.accessibility.IAccessibilityInteractionConnection getRemote() {
            return this.mConnection;
        }

        void linkToDeath() throws android.os.RemoteException {
            this.mConnection.asBinder().linkToDeath(this, 0);
        }

        void unlinkToDeath() {
            this.mConnection.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            unlinkToDeath();
            synchronized (com.android.server.accessibility.AccessibilityWindowManager.this.mLock) {
                com.android.server.accessibility.AccessibilityWindowManager.this.removeAccessibilityInteractionConnectionLocked(this.mWindowId, this.mUserId);
            }
        }
    }

    public AccessibilityWindowManager(@android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.wm.WindowManagerInternal windowManagerInternal, @android.annotation.NonNull com.android.server.accessibility.AccessibilityWindowManager.AccessibilityEventSender accessibilityEventSender, @android.annotation.NonNull com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, @android.annotation.NonNull com.android.server.accessibility.AccessibilitySecurityPolicy.AccessibilityUserManager accessibilityUserManager, @android.annotation.NonNull com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager) {
        this.mLock = obj;
        this.mHandler = handler;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mAccessibilityEventSender = accessibilityEventSender;
        this.mSecurityPolicy = accessibilitySecurityPolicy;
        this.mAccessibilityUserManager = accessibilityUserManager;
        this.mTraceManager = accessibilityTraceManager;
    }

    public void startTrackingWindows(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i);
                if (displayWindowsObserver == null) {
                    displayWindowsObserver = new com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver(i);
                }
                if (z && !displayWindowsObserver.mIsProxy) {
                    displayWindowsObserver.mIsProxy = true;
                    this.mHasProxy = true;
                }
                if (displayWindowsObserver.isTrackingWindowsLocked()) {
                    return;
                }
                displayWindowsObserver.startTrackingWindowsLocked();
                this.mDisplayWindowsObservers.put(i, displayWindowsObserver);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopTrackingWindows(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i);
                if (displayWindowsObserver != null) {
                    displayWindowsObserver.stopTrackingWindowsLocked();
                    this.mDisplayWindowsObservers.remove(i);
                }
                resetHasProxyIfNeededLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopTrackingDisplayProxy(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i);
                if (displayWindowsObserver != null) {
                    displayWindowsObserver.mIsProxy = false;
                }
                resetHasProxyIfNeededLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void resetHasProxyIfNeededLocked() {
        int size = this.mDisplayWindowsObservers.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i);
            if (valueAt != null && valueAt.mIsProxy) {
                z = true;
            }
        }
        this.mHasProxy = z;
    }

    public boolean isTrackingWindowsLocked() {
        if (this.mDisplayWindowsObservers.size() > 0) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isProxyed(int i) {
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i);
        return displayWindowsObserver != null && displayWindowsObserver.mIsProxy;
    }

    void moveNonProxyTopFocusedDisplayToTopIfNeeded() {
        if (this.mHasProxy && this.mLastNonProxyTopFocusedDisplayId != this.mTopFocusedDisplayId) {
            this.mWindowManagerInternal.moveDisplayToTopIfAllowed(this.mLastNonProxyTopFocusedDisplayId);
        }
    }

    int getLastNonProxyTopFocusedDisplayId() {
        return this.mLastNonProxyTopFocusedDisplayId;
    }

    public boolean isTrackingWindowsLocked(int i) {
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i);
        if (displayWindowsObserver != null) {
            return displayWindowsObserver.isTrackingWindowsLocked();
        }
        return false;
    }

    @android.annotation.Nullable
    public java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindowListLocked(int i) {
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i);
        if (displayWindowsObserver != null) {
            return displayWindowsObserver.getWindowListLocked();
        }
        return null;
    }

    public int addAccessibilityInteractionConnection(@android.annotation.NonNull android.view.IWindow iWindow, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, @android.annotation.NonNull java.lang.String str, int i) throws android.os.RemoteException {
        int i2;
        boolean z;
        android.os.IBinder asBinder = iWindow.asBinder();
        if (traceWMEnabled()) {
            logTraceWM("getDisplayIdForWindow", "token=" + asBinder);
        }
        int displayIdForWindow = this.mWindowManagerInternal.getDisplayIdForWindow(asBinder);
        synchronized (this.mLock) {
            try {
                int resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                int uid = android.os.UserHandle.getUid(resolveCallingUserIdEnforcingPermissionsLocked, android.os.UserHandle.getCallingAppId());
                java.lang.String resolveValidReportedPackageLocked = this.mSecurityPolicy.resolveValidReportedPackageLocked(str, android.os.UserHandle.getCallingAppId(), resolveCallingUserIdEnforcingPermissionsLocked, android.os.Binder.getCallingPid());
                i2 = sNextWindowId;
                sNextWindowId = i2 + 1;
                if (this.mSecurityPolicy.isCallerInteractingAcrossUsers(i)) {
                    com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection = new com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection(i2, iAccessibilityInteractionConnection, resolveValidReportedPackageLocked, uid, -1);
                    remoteAccessibilityConnection.linkToDeath();
                    this.mGlobalInteractionConnections.put(i2, remoteAccessibilityConnection);
                    this.mGlobalWindowTokens.put(i2, asBinder);
                } else {
                    com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection2 = new com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection(i2, iAccessibilityInteractionConnection, resolveValidReportedPackageLocked, uid, resolveCallingUserIdEnforcingPermissionsLocked);
                    remoteAccessibilityConnection2.linkToDeath();
                    getInteractionConnectionsForUserLocked(resolveCallingUserIdEnforcingPermissionsLocked).put(i2, remoteAccessibilityConnection2);
                    getWindowTokensForUserLocked(resolveCallingUserIdEnforcingPermissionsLocked).put(i2, asBinder);
                }
                if (!isTrackingWindowsLocked(displayIdForWindow)) {
                    z = false;
                } else {
                    z = true;
                }
                registerIdLocked(iBinder, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            if (traceWMEnabled()) {
                logTraceWM("computeWindowsForAccessibility", "displayId=" + displayIdForWindow);
            }
            this.mWindowManagerInternal.computeWindowsForAccessibility(displayIdForWindow);
        }
        if (traceWMEnabled()) {
            logTraceWM("setAccessibilityIdToSurfaceMetadata", "token=" + asBinder + ";windowId=" + i2);
        }
        this.mWindowManagerInternal.setAccessibilityIdToSurfaceMetadata(asBinder, i2);
        return i2;
    }

    public void removeAccessibilityInteractionConnection(@android.annotation.NonNull android.view.IWindow iWindow) {
        synchronized (this.mLock) {
            try {
                this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(android.os.UserHandle.getCallingUserId());
                android.os.IBinder asBinder = iWindow.asBinder();
                int removeAccessibilityInteractionConnectionInternalLocked = removeAccessibilityInteractionConnectionInternalLocked(asBinder, this.mGlobalWindowTokens, this.mGlobalInteractionConnections);
                if (removeAccessibilityInteractionConnectionInternalLocked >= 0) {
                    onAccessibilityInteractionConnectionRemovedLocked(removeAccessibilityInteractionConnectionInternalLocked, asBinder);
                    return;
                }
                int size = this.mWindowTokens.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mWindowTokens.keyAt(i);
                    int removeAccessibilityInteractionConnectionInternalLocked2 = removeAccessibilityInteractionConnectionInternalLocked(asBinder, getWindowTokensForUserLocked(keyAt), getInteractionConnectionsForUserLocked(keyAt));
                    if (removeAccessibilityInteractionConnectionInternalLocked2 >= 0) {
                        onAccessibilityInteractionConnectionRemovedLocked(removeAccessibilityInteractionConnectionInternalLocked2, asBinder);
                        return;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection getConnectionLocked(int i, int i2) {
        com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection = this.mGlobalInteractionConnections.get(i2);
        if (remoteAccessibilityConnection == null && isValidUserForInteractionConnectionsLocked(i)) {
            remoteAccessibilityConnection = getInteractionConnectionsForUserLocked(i).get(i2);
        }
        if (remoteAccessibilityConnection != null && remoteAccessibilityConnection.getRemote() != null) {
            return remoteAccessibilityConnection;
        }
        return null;
    }

    private int removeAccessibilityInteractionConnectionInternalLocked(android.os.IBinder iBinder, android.util.SparseArray<android.os.IBinder> sparseArray, android.util.SparseArray<com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection> sparseArray2) {
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            if (sparseArray.valueAt(i) == iBinder) {
                int keyAt = sparseArray.keyAt(i);
                sparseArray.removeAt(i);
                sparseArray2.get(keyAt).unlinkToDeath();
                sparseArray2.remove(keyAt);
                return keyAt;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAccessibilityInteractionConnectionLocked(int i, int i2) {
        android.os.IBinder iBinder;
        android.os.IBinder iBinder2;
        if (i2 == -1) {
            iBinder2 = this.mGlobalWindowTokens.get(i);
            this.mGlobalWindowTokens.remove(i);
            this.mGlobalInteractionConnections.remove(i);
        } else {
            if (!isValidUserForWindowTokensLocked(i2)) {
                iBinder = null;
            } else {
                iBinder = getWindowTokensForUserLocked(i2).get(i);
                getWindowTokensForUserLocked(i2).remove(i);
            }
            if (isValidUserForInteractionConnectionsLocked(i2)) {
                getInteractionConnectionsForUserLocked(i2).remove(i);
            }
            iBinder2 = iBinder;
        }
        onAccessibilityInteractionConnectionRemovedLocked(i, iBinder2);
    }

    private void onAccessibilityInteractionConnectionRemovedLocked(int i, @android.annotation.Nullable android.os.IBinder iBinder) {
        if (!isTrackingWindowsLocked() && i >= 0 && this.mActiveWindowId == i) {
            this.mActiveWindowId = -1;
        }
        if (iBinder != null) {
            if (traceWMEnabled()) {
                logTraceWM("setAccessibilityIdToSurfaceMetadata", "token=" + iBinder + ";windowId=AccessibilityWindowInfo.UNDEFINED_WINDOW_ID");
            }
            this.mWindowManagerInternal.setAccessibilityIdToSurfaceMetadata(iBinder, -1);
        }
        unregisterIdLocked(i);
        this.mWindowAttributes.remove(i);
    }

    @android.annotation.Nullable
    public android.os.IBinder getWindowTokenForUserAndWindowIdLocked(int i, int i2) {
        android.os.IBinder iBinder = this.mGlobalWindowTokens.get(i2);
        if (iBinder == null && isValidUserForWindowTokensLocked(i)) {
            return getWindowTokensForUserLocked(i).get(i2);
        }
        return iBinder;
    }

    public int getWindowOwnerUserId(@android.annotation.NonNull android.os.IBinder iBinder) {
        if (traceWMEnabled()) {
            logTraceWM("getWindowOwnerUserId", "token=" + iBinder);
        }
        return this.mWindowManagerInternal.getWindowOwnerUserId(iBinder);
    }

    public int findWindowIdLocked(int i, @android.annotation.NonNull android.os.IBinder iBinder) {
        int indexOfValue;
        int indexOfValue2 = this.mGlobalWindowTokens.indexOfValue(iBinder);
        if (indexOfValue2 >= 0) {
            return this.mGlobalWindowTokens.keyAt(indexOfValue2);
        }
        if (isValidUserForWindowTokensLocked(i) && (indexOfValue = getWindowTokensForUserLocked(i).indexOfValue(iBinder)) >= 0) {
            return getWindowTokensForUserLocked(i).keyAt(indexOfValue);
        }
        return -1;
    }

    public void associateEmbeddedHierarchyLocked(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
        associateLocked(iBinder2, iBinder);
    }

    public void disassociateEmbeddedHierarchyLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        disassociateLocked(iBinder);
    }

    public int resolveParentWindowIdLocked(int i) {
        android.os.IBinder leashTokenLocked = getLeashTokenLocked(i);
        if (leashTokenLocked == null) {
            return i;
        }
        int windowIdLocked = getWindowIdLocked(resolveTopParentTokenLocked(leashTokenLocked));
        return windowIdLocked != -1 ? windowIdLocked : i;
    }

    private android.os.IBinder resolveTopParentTokenLocked(android.os.IBinder iBinder) {
        android.os.IBinder hostTokenLocked = getHostTokenLocked(iBinder);
        if (hostTokenLocked == null) {
            return iBinder;
        }
        return resolveTopParentTokenLocked(hostTokenLocked);
    }

    public boolean computePartialInteractiveRegionForWindowLocked(int i, @android.annotation.NonNull android.graphics.Region region) {
        int resolveParentWindowIdLocked = resolveParentWindowIdLocked(i);
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked);
        if (displayWindowObserverByWindowIdLocked != null) {
            return displayWindowObserverByWindowIdLocked.computePartialInteractiveRegionForWindowLocked(resolveParentWindowIdLocked, resolveParentWindowIdLocked != i, region);
        }
        return false;
    }

    public void updateActiveAndAccessibilityFocusedWindowLocked(int i, int i2, long j, int i3, int i4) {
        switch (i3) {
            case 32:
                synchronized (this.mLock) {
                    try {
                        if (!isTrackingWindowsLocked()) {
                            this.mTopFocusedWindowId = findFocusedWindowId(i);
                            if (i2 == this.mTopFocusedWindowId) {
                                this.mActiveWindowId = i2;
                            }
                        }
                    } finally {
                    }
                }
                return;
            case 128:
                synchronized (this.mLock) {
                    try {
                        if (this.mTouchInteractionInProgress && this.mActiveWindowId != i2) {
                            setActiveWindowLocked(i2);
                        }
                    } finally {
                    }
                }
                return;
            case 32768:
                synchronized (this.mLock) {
                    try {
                        if (this.mHasProxy && setProxyFocusLocked(i2)) {
                            return;
                        }
                        if (this.mAccessibilityFocusedWindowId != i2) {
                            clearAccessibilityFocusLocked(this.mAccessibilityFocusedWindowId);
                            setAccessibilityFocusedWindowLocked(i2);
                        }
                        this.mAccessibilityFocusNodeId = j;
                        return;
                    } finally {
                    }
                }
            case 65536:
                synchronized (this.mLock) {
                    try {
                        if (this.mHasProxy && clearProxyFocusLocked(i2, i4)) {
                            return;
                        }
                        if (this.mAccessibilityFocusNodeId == j) {
                            this.mAccessibilityFocusNodeId = 2147483647L;
                        }
                        if (this.mAccessibilityFocusNodeId == 2147483647L && this.mAccessibilityFocusedWindowId == i2 && i4 != 64) {
                            this.mAccessibilityFocusedWindowId = -1;
                            this.mAccessibilityFocusedDisplayId = -1;
                        }
                        return;
                    } finally {
                    }
                }
            default:
                return;
        }
    }

    public void onTouchInteractionStart() {
        synchronized (this.mLock) {
            this.mTouchInteractionInProgress = true;
        }
    }

    public void onTouchInteractionEnd() {
        synchronized (this.mLock) {
            try {
                this.mTouchInteractionInProgress = false;
                int i = this.mActiveWindowId;
                setActiveWindowLocked(this.mTopFocusedWindowId);
                if (i != this.mActiveWindowId && this.mAccessibilityFocusedWindowId == i && accessibilityFocusOnlyInActiveWindowLocked()) {
                    clearAccessibilityFocusLocked(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getActiveWindowId(int i) {
        if (this.mActiveWindowId == -1 && !this.mTouchInteractionInProgress) {
            this.mActiveWindowId = findFocusedWindowId(i);
        }
        return this.mActiveWindowId;
    }

    private void setActiveWindowLocked(int i) {
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked;
        if (this.mActiveWindowId != i) {
            java.util.ArrayList arrayList = new java.util.ArrayList(2);
            if (this.mActiveWindowId != -1 && (displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(this.mActiveWindowId)) != null) {
                arrayList.add(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(displayWindowObserverByWindowIdLocked.mDisplayId, this.mActiveWindowId, 32));
            }
            this.mActiveWindowId = i;
            int size = this.mDisplayWindowsObservers.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i2);
                if (valueAt != null && valueAt.setActiveWindowLocked(i)) {
                    arrayList.add(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(valueAt.mDisplayId, i, 32));
                }
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mAccessibilityEventSender.sendAccessibilityEventForCurrentUserLocked((android.view.accessibility.AccessibilityEvent) it.next());
            }
        }
    }

    private void setAccessibilityFocusedWindowLocked(int i) {
        if (this.mAccessibilityFocusedWindowId != i) {
            java.util.ArrayList arrayList = new java.util.ArrayList(2);
            if (this.mAccessibilityFocusedDisplayId != -1 && this.mAccessibilityFocusedWindowId != -1) {
                arrayList.add(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(this.mAccessibilityFocusedDisplayId, this.mAccessibilityFocusedWindowId, 128));
            }
            this.mAccessibilityFocusedWindowId = i;
            int size = this.mDisplayWindowsObservers.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i2);
                if (valueAt != null && valueAt.setAccessibilityFocusedWindowLocked(i)) {
                    this.mAccessibilityFocusedDisplayId = valueAt.mDisplayId;
                    arrayList.add(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(valueAt.mDisplayId, i, 128));
                }
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mAccessibilityEventSender.sendAccessibilityEventForCurrentUserLocked((android.view.accessibility.AccessibilityEvent) it.next());
            }
        }
    }

    @android.annotation.Nullable
    public android.view.accessibility.AccessibilityWindowInfo findA11yWindowInfoByIdLocked(int i) {
        int resolveParentWindowIdLocked = resolveParentWindowIdLocked(i);
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked);
        if (displayWindowObserverByWindowIdLocked != null) {
            return displayWindowObserverByWindowIdLocked.findA11yWindowInfoByIdLocked(resolveParentWindowIdLocked);
        }
        return null;
    }

    @android.annotation.Nullable
    public android.view.WindowInfo findWindowInfoByIdLocked(int i) {
        int resolveParentWindowIdLocked = resolveParentWindowIdLocked(i);
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked);
        if (displayWindowObserverByWindowIdLocked != null) {
            return displayWindowObserverByWindowIdLocked.findWindowInfoByIdLocked(resolveParentWindowIdLocked);
        }
        return null;
    }

    public int getFocusedWindowId(int i) {
        return getFocusedWindowId(i, -1);
    }

    public int getFocusedWindowId(int i, int i2) {
        if (i2 == -1 || i2 == 0 || !this.mHasProxy) {
            return getDefaultFocus(i);
        }
        com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i2);
        if (displayWindowsObserver != null && displayWindowsObserver.mIsProxy) {
            return getProxyFocus(i, displayWindowsObserver);
        }
        return getDefaultFocus(i);
    }

    private int getDefaultFocus(int i) {
        if (i == 1) {
            return this.mTopFocusedWindowId;
        }
        if (i == 2) {
            return this.mAccessibilityFocusedWindowId;
        }
        return -1;
    }

    private int getProxyFocus(int i, com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver) {
        if (i == 1) {
            return this.mTopFocusedWindowId;
        }
        if (i == 2) {
            return displayWindowsObserver.mProxyDisplayAccessibilityFocusedWindow;
        }
        return -1;
    }

    @android.annotation.Nullable
    public android.view.accessibility.AccessibilityWindowInfo getPictureInPictureWindowLocked() {
        int size = this.mDisplayWindowsObservers.size();
        android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = null;
        for (int i = 0; i < size; i++) {
            com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i);
            if (valueAt != null && (accessibilityWindowInfo = valueAt.getPictureInPictureWindowLocked()) != null) {
                break;
            }
        }
        return accessibilityWindowInfo;
    }

    public void setPictureInPictureActionReplacingConnection(@android.annotation.Nullable android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (this.mPictureInPictureActionReplacingConnection != null) {
                    this.mPictureInPictureActionReplacingConnection.unlinkToDeath();
                    this.mPictureInPictureActionReplacingConnection = null;
                }
                if (iAccessibilityInteractionConnection != null) {
                    com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection = new com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection(-3, iAccessibilityInteractionConnection, "foo.bar.baz", 1000, -1);
                    this.mPictureInPictureActionReplacingConnection = remoteAccessibilityConnection;
                    remoteAccessibilityConnection.linkToDeath();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection getPictureInPictureActionReplacingConnection() {
        return this.mPictureInPictureActionReplacingConnection;
    }

    public void notifyOutsideTouch(int i, int i2) {
        int i3;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(i2);
                if (displayWindowObserverByWindowIdLocked != null) {
                    java.util.List<java.lang.Integer> watchOutsideTouchWindowIdLocked = displayWindowObserverByWindowIdLocked.getWatchOutsideTouchWindowIdLocked(i2);
                    for (int i4 = 0; i4 < watchOutsideTouchWindowIdLocked.size(); i4++) {
                        arrayList.add(getConnectionLocked(i, watchOutsideTouchWindowIdLocked.get(i4).intValue()));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (i3 = 0; i3 < arrayList.size(); i3++) {
            com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection = (com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection) arrayList.get(i3);
            if (remoteAccessibilityConnection != null) {
                if (traceIntConnEnabled()) {
                    logTraceIntConn("notifyOutsideTouch");
                }
                try {
                    remoteAccessibilityConnection.getRemote().notifyOutsideTouch();
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    public int getDisplayIdByUserIdAndWindowIdLocked(int i, int i2) {
        android.os.IBinder windowTokenForUserAndWindowIdLocked = getWindowTokenForUserAndWindowIdLocked(i, i2);
        if (traceWMEnabled()) {
            logTraceWM("getDisplayIdForWindow", "token=" + windowTokenForUserAndWindowIdLocked);
        }
        return this.mWindowManagerInternal.getDisplayIdForWindow(windowTokenForUserAndWindowIdLocked);
    }

    public java.util.ArrayList<java.lang.Integer> getDisplayListLocked(int i) {
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
        int size = this.mDisplayWindowsObservers.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i2);
            if (valueAt != null) {
                if (!valueAt.mIsProxy && (i & 1) != 0) {
                    arrayList.add(java.lang.Integer.valueOf(valueAt.mDisplayId));
                } else if (valueAt.mIsProxy && (i & 2) != 0) {
                    arrayList.add(java.lang.Integer.valueOf(valueAt.mDisplayId));
                }
            }
        }
        return arrayList;
    }

    boolean accessibilityFocusOnlyInActiveWindowLocked() {
        return !isTrackingWindowsLocked();
    }

    private int findFocusedWindowId(int i) {
        int findWindowIdLocked;
        if (traceWMEnabled()) {
            logTraceWM("getFocusedWindowToken", "");
        }
        android.os.IBinder focusedWindowTokenFromWindowStates = this.mWindowManagerInternal.getFocusedWindowTokenFromWindowStates();
        synchronized (this.mLock) {
            findWindowIdLocked = findWindowIdLocked(i, focusedWindowTokenFromWindowStates);
        }
        return findWindowIdLocked;
    }

    private boolean isValidUserForInteractionConnectionsLocked(int i) {
        return this.mInteractionConnections.indexOfKey(i) >= 0;
    }

    private boolean isValidUserForWindowTokensLocked(int i) {
        return this.mWindowTokens.indexOfKey(i) >= 0;
    }

    private android.util.SparseArray<com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection> getInteractionConnectionsForUserLocked(int i) {
        android.util.SparseArray<com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection> sparseArray = this.mInteractionConnections.get(i);
        if (sparseArray == null) {
            android.util.SparseArray<com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection> sparseArray2 = new android.util.SparseArray<>();
            this.mInteractionConnections.put(i, sparseArray2);
            return sparseArray2;
        }
        return sparseArray;
    }

    private android.util.SparseArray<android.os.IBinder> getWindowTokensForUserLocked(int i) {
        android.util.SparseArray<android.os.IBinder> sparseArray = this.mWindowTokens.get(i);
        if (sparseArray == null) {
            android.util.SparseArray<android.os.IBinder> sparseArray2 = new android.util.SparseArray<>();
            this.mWindowTokens.put(i, sparseArray2);
            return sparseArray2;
        }
        return sparseArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilityFocusLocked(int i) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityWindowManager$$ExternalSyntheticLambda0
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityWindowManager) obj).clearAccessibilityFocusMainThread(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
            }
        }, this, java.lang.Integer.valueOf(this.mAccessibilityUserManager.getCurrentUserIdLocked()), java.lang.Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilityFocusMainThread(int i, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection connectionLocked = getConnectionLocked(i, i2);
                if (connectionLocked == null) {
                    return;
                }
                if (traceIntConnEnabled()) {
                    logTraceIntConn("notifyOutsideTouch");
                }
                try {
                    connectionLocked.getRemote().clearAccessibilityFocus();
                } catch (android.os.RemoteException e) {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver getDisplayWindowObserverByWindowIdLocked(int i) {
        int size = this.mDisplayWindowsObservers.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i2);
            if (valueAt != null && valueAt.findWindowInfoByIdLocked(i) != null) {
                return this.mDisplayWindowsObservers.get(valueAt.mDisplayId);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean traceWMEnabled() {
        return this.mTraceManager.isA11yTracingEnabledForTypes(512L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logTraceWM(java.lang.String str, java.lang.String str2) {
        this.mTraceManager.logTrace("WindowManagerInternal." + str, 512L, str2);
    }

    private boolean traceIntConnEnabled() {
        return this.mTraceManager.isA11yTracingEnabledForTypes(16L);
    }

    private void logTraceIntConn(java.lang.String str) {
        this.mTraceManager.logTrace("AccessibilityWindowManager." + str, 16L);
    }

    void associateLocked(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        this.mHostEmbeddedMap.put(iBinder, iBinder2);
    }

    void disassociateLocked(android.os.IBinder iBinder) {
        this.mHostEmbeddedMap.remove(iBinder);
        for (int size = this.mHostEmbeddedMap.size() - 1; size >= 0; size--) {
            if (this.mHostEmbeddedMap.valueAt(size).equals(iBinder)) {
                this.mHostEmbeddedMap.removeAt(size);
            }
        }
    }

    void registerIdLocked(android.os.IBinder iBinder, int i) {
        this.mWindowIdMap.put(i, iBinder);
    }

    void unregisterIdLocked(int i) {
        android.os.IBinder iBinder = this.mWindowIdMap.get(i);
        if (iBinder == null) {
            return;
        }
        disassociateLocked(iBinder);
        this.mWindowIdMap.remove(i);
    }

    android.os.IBinder getLeashTokenLocked(int i) {
        return this.mWindowIdMap.get(i);
    }

    int getWindowIdLocked(android.os.IBinder iBinder) {
        int indexOfValue = this.mWindowIdMap.indexOfValue(iBinder);
        if (indexOfValue == -1) {
            return indexOfValue;
        }
        return this.mWindowIdMap.keyAt(indexOfValue);
    }

    android.os.IBinder getHostTokenLocked(android.os.IBinder iBinder) {
        return this.mHostEmbeddedMap.get(iBinder);
    }

    boolean isEmbeddedHierarchyWindowsLocked(int i) {
        android.os.IBinder leashTokenLocked;
        if (this.mHostEmbeddedMap.size() == 0 || (leashTokenLocked = getLeashTokenLocked(i)) == null) {
            return false;
        }
        return this.mHostEmbeddedMap.containsKey(leashTokenLocked);
    }

    private boolean clearProxyFocusLocked(int i, int i2) {
        if (i2 == 64) {
            return false;
        }
        for (int i3 = 0; i3 < this.mDisplayWindowsObservers.size(); i3++) {
            com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = this.mDisplayWindowsObservers.get(i3);
            if (displayWindowsObserver != null && displayWindowsObserver.mWindows != null && displayWindowsObserver.mIsProxy) {
                int size = displayWindowsObserver.mWindows.size();
                for (int i4 = 0; i4 < size; i4++) {
                    if (((android.view.accessibility.AccessibilityWindowInfo) displayWindowsObserver.mWindows.get(i4)).getId() == i) {
                        displayWindowsObserver.mProxyDisplayAccessibilityFocusedWindow = -1;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean setProxyFocusLocked(int i) {
        for (int i2 = 0; i2 < this.mDisplayWindowsObservers.size(); i2++) {
            com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i2);
            if (valueAt != null && valueAt.mIsProxy && valueAt.setAccessibilityFocusedWindowLocked(i)) {
                int i3 = valueAt.mProxyDisplayAccessibilityFocusedWindow;
                if (i3 == i) {
                    return true;
                }
                if (i3 != -1) {
                    clearAccessibilityFocusLocked(i3);
                    this.mAccessibilityEventSender.sendAccessibilityEventForCurrentUserLocked(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(valueAt.mDisplayId, i3, 128));
                }
                valueAt.mProxyDisplayAccessibilityFocusedWindow = i;
                this.mAccessibilityEventSender.sendAccessibilityEventForCurrentUserLocked(android.view.accessibility.AccessibilityEvent.obtainWindowsChangedEvent(valueAt.mDisplayId, valueAt.mProxyDisplayAccessibilityFocusedWindow, 128));
                return true;
            }
        }
        return false;
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.append("Global Info [ ");
        printWriter.println("Top focused display Id = " + this.mTopFocusedDisplayId);
        printWriter.println("     Active Window Id = " + this.mActiveWindowId);
        printWriter.println("     Top Focused Window Id = " + this.mTopFocusedWindowId);
        printWriter.println("     Accessibility Focused Window Id = " + this.mAccessibilityFocusedWindowId + " ]");
        printWriter.println();
        int size = this.mDisplayWindowsObservers.size();
        for (int i = 0; i < size; i++) {
            com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver valueAt = this.mDisplayWindowsObservers.valueAt(i);
            if (valueAt != null) {
                valueAt.dumpLocked(fileDescriptor, printWriter, strArr);
            }
        }
        printWriter.println();
        printWriter.append("Window attributes:[");
        printWriter.append((java.lang.CharSequence) this.mWindowAttributes.toString());
        printWriter.append("]");
        printWriter.println();
    }
}
