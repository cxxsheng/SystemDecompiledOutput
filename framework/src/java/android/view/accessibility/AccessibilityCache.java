package android.view.accessibility;

/* loaded from: classes4.dex */
public class AccessibilityCache {
    public static final int CACHE_CRITICAL_EVENTS_MASK = 4307005;
    private static final boolean CHECK_INTEGRITY;
    private static final boolean DEBUG;
    private static final java.lang.String LOG_TAG = "AccessibilityCache";
    private static final boolean VERBOSE;
    private final android.view.accessibility.AccessibilityCache.AccessibilityNodeRefresher mAccessibilityNodeRefresher;
    private boolean mIsAllWindowsCached;
    private android.view.accessibility.AccessibilityCache.OnNodeAddedListener mOnNodeAddedListener;
    private boolean mEnabled = true;
    private final java.lang.Object mLock = new java.lang.Object();
    private long mAccessibilityFocus = 2147483647L;
    private long mInputFocus = 2147483647L;
    private long mValidWindowCacheTimeStamp = 0;
    private int mAccessibilityFocusedWindow = -1;
    private int mInputFocusWindow = -1;
    private final android.util.SparseArray<android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo>> mWindowCacheByDisplay = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo>> mNodeCache = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> mTempWindowArray = new android.util.SparseArray<>();

    public interface OnNodeAddedListener {
        void onNodeAdded(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo);
    }

    static {
        DEBUG = android.util.Log.isLoggable(LOG_TAG, 3) && android.os.Build.IS_DEBUGGABLE;
        VERBOSE = android.util.Log.isLoggable(LOG_TAG, 2) && android.os.Build.IS_DEBUGGABLE;
        CHECK_INTEGRITY = android.os.Build.IS_ENG;
    }

    public AccessibilityCache(android.view.accessibility.AccessibilityCache.AccessibilityNodeRefresher accessibilityNodeRefresher) {
        this.mAccessibilityNodeRefresher = accessibilityNodeRefresher;
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEnabled;
        }
        return z;
    }

    public void setEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mEnabled = z;
            clear();
        }
    }

    public void setWindowsOnAllDisplays(android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> sparseArray, long j) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return;
            }
            if (DEBUG) {
                android.util.Log.i(LOG_TAG, "Set windows");
            }
            if (this.mValidWindowCacheTimeStamp > j) {
                return;
            }
            clearWindowCacheLocked();
            if (sparseArray == null) {
                return;
            }
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                java.util.List<android.view.accessibility.AccessibilityWindowInfo> valueAt = sparseArray.valueAt(i);
                if (valueAt != null) {
                    int keyAt = sparseArray.keyAt(i);
                    int size2 = valueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        addWindowByDisplayLocked(keyAt, valueAt.get(i2));
                    }
                }
            }
            this.mIsAllWindowsCached = true;
        }
    }

    public void addWindow(android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
            } else {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Caching window: " + accessibilityWindowInfo.getId() + " at display Id [ " + accessibilityWindowInfo.getDisplayId() + " ]");
                }
                addWindowByDisplayLocked(accessibilityWindowInfo.getDisplayId(), accessibilityWindowInfo);
            }
        }
    }

    private void addWindowByDisplayLocked(int i, android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo) {
        android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> sparseArray = this.mWindowCacheByDisplay.get(i);
        if (sparseArray == null) {
            sparseArray = new android.util.SparseArray<>();
            this.mWindowCacheByDisplay.put(i, sparseArray);
        }
        sparseArray.put(accessibilityWindowInfo.getId(), new android.view.accessibility.AccessibilityWindowInfo(accessibilityWindowInfo));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return;
            }
            if (DEBUG) {
                android.util.Log.i(LOG_TAG, "onAccessibilityEvent(" + accessibilityEvent + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = null;
            switch (accessibilityEvent.getEventType()) {
                case 1:
                case 4:
                case 16:
                case 8192:
                    accessibilityNodeInfo = removeCachedNodeLocked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                    if (accessibilityNodeInfo != null) {
                        if (DEBUG) {
                            android.util.Log.i(LOG_TAG, "Refreshing and re-adding cached node.");
                        }
                        if (this.mAccessibilityNodeRefresher.refreshNode(accessibilityNodeInfo, true)) {
                            add(accessibilityNodeInfo);
                        }
                    }
                    if (!CHECK_INTEGRITY) {
                        checkIntegrity();
                        return;
                    }
                    return;
                case 8:
                    if (this.mInputFocus != 2147483647L) {
                        removeCachedNodeLocked(accessibilityEvent.getWindowId(), this.mInputFocus);
                    }
                    this.mInputFocus = accessibilityEvent.getSourceNodeId();
                    this.mInputFocusWindow = accessibilityEvent.getWindowId();
                    accessibilityNodeInfo = removeCachedNodeLocked(accessibilityEvent.getWindowId(), this.mInputFocus);
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
                case 32:
                    this.mValidWindowCacheTimeStamp = accessibilityEvent.getEventTime();
                    clear();
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
                case 2048:
                    synchronized (this.mLock) {
                        int windowId = accessibilityEvent.getWindowId();
                        long sourceNodeId = accessibilityEvent.getSourceNodeId();
                        if ((accessibilityEvent.getContentChangeTypes() & 1) != 0) {
                            clearSubTreeLocked(windowId, sourceNodeId);
                        } else {
                            accessibilityNodeInfo = removeCachedNodeLocked(windowId, sourceNodeId);
                        }
                    }
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
                case 4096:
                    clearSubTreeLocked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
                case 32768:
                    if (this.mAccessibilityFocus != 2147483647L) {
                        removeCachedNodeLocked(this.mAccessibilityFocusedWindow, this.mAccessibilityFocus);
                    }
                    this.mAccessibilityFocus = accessibilityEvent.getSourceNodeId();
                    this.mAccessibilityFocusedWindow = accessibilityEvent.getWindowId();
                    accessibilityNodeInfo = removeCachedNodeLocked(this.mAccessibilityFocusedWindow, this.mAccessibilityFocus);
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
                case 65536:
                    if (this.mAccessibilityFocus == accessibilityEvent.getSourceNodeId() && this.mAccessibilityFocusedWindow == accessibilityEvent.getWindowId()) {
                        accessibilityNodeInfo = removeCachedNodeLocked(this.mAccessibilityFocusedWindow, this.mAccessibilityFocus);
                        this.mAccessibilityFocus = 2147483647L;
                        this.mAccessibilityFocusedWindow = -1;
                    }
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
                case 4194304:
                    this.mValidWindowCacheTimeStamp = accessibilityEvent.getEventTime();
                    if (accessibilityEvent.getWindowChanges() == 128) {
                        clearWindowCacheLocked();
                        if (accessibilityNodeInfo != null) {
                        }
                        if (!CHECK_INTEGRITY) {
                        }
                    }
                    this.mValidWindowCacheTimeStamp = accessibilityEvent.getEventTime();
                    clear();
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
                default:
                    if (accessibilityNodeInfo != null) {
                    }
                    if (!CHECK_INTEGRITY) {
                    }
                    break;
            }
        }
    }

    private android.view.accessibility.AccessibilityNodeInfo removeCachedNodeLocked(int i, long j) {
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        if (DEBUG) {
            android.util.Log.i(LOG_TAG, "Removing cached node.");
        }
        android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i);
        if (longSparseArray == null || (accessibilityNodeInfo = longSparseArray.get(j)) == null) {
            return null;
        }
        longSparseArray.remove(j);
        return accessibilityNodeInfo;
    }

    public android.view.accessibility.AccessibilityNodeInfo getNode(int i, long j) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i);
            if (longSparseArray == null) {
                return null;
            }
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j);
            if (accessibilityNodeInfo != null) {
                accessibilityNodeInfo = new android.view.accessibility.AccessibilityNodeInfo(accessibilityNodeInfo);
            }
            if (VERBOSE) {
                android.util.Log.i(LOG_TAG, "get(0x" + java.lang.Long.toHexString(j) + ") = " + accessibilityNodeInfo);
            }
            return accessibilityNodeInfo;
        }
    }

    public boolean isNodeInCache(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        int windowId = accessibilityNodeInfo.getWindowId();
        long sourceNodeId = accessibilityNodeInfo.getSourceNodeId();
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return false;
            }
            android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(windowId);
            if (longSparseArray == null) {
                return false;
            }
            return longSparseArray.get(sourceNodeId) != null;
        }
    }

    public android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> getWindowsOnAllDisplays() {
        int size;
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            if (!this.mIsAllWindowsCached) {
                return null;
            }
            android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityWindowInfo>> sparseArray = new android.util.SparseArray<>();
            int size2 = this.mWindowCacheByDisplay.size();
            if (size2 <= 0) {
                return null;
            }
            for (int i = 0; i < size2; i++) {
                int keyAt = this.mWindowCacheByDisplay.keyAt(i);
                android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> valueAt = this.mWindowCacheByDisplay.valueAt(i);
                if (valueAt != null && (size = valueAt.size()) > 0) {
                    android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> sparseArray2 = this.mTempWindowArray;
                    sparseArray2.clear();
                    for (int i2 = 0; i2 < size; i2++) {
                        android.view.accessibility.AccessibilityWindowInfo valueAt2 = valueAt.valueAt(i2);
                        sparseArray2.put(valueAt2.getLayer(), valueAt2);
                    }
                    int size3 = sparseArray2.size();
                    java.util.ArrayList arrayList = new java.util.ArrayList(size3);
                    for (int i3 = size3 - 1; i3 >= 0; i3--) {
                        arrayList.add(new android.view.accessibility.AccessibilityWindowInfo(sparseArray2.valueAt(i3)));
                        sparseArray2.removeAt(i3);
                    }
                    sparseArray.put(keyAt, arrayList);
                }
            }
            return sparseArray;
        }
    }

    public android.view.accessibility.AccessibilityWindowInfo getWindow(int i) {
        android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo;
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            int size = this.mWindowCacheByDisplay.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> valueAt = this.mWindowCacheByDisplay.valueAt(i2);
                if (valueAt != null && (accessibilityWindowInfo = valueAt.get(i)) != null) {
                    return new android.view.accessibility.AccessibilityWindowInfo(accessibilityWindowInfo);
                }
            }
            return null;
        }
    }

    public void add(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return;
            }
            if (VERBOSE) {
                android.util.Log.i(LOG_TAG, "add(" + accessibilityNodeInfo + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            int windowId = accessibilityNodeInfo.getWindowId();
            android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(windowId);
            if (longSparseArray == null) {
                longSparseArray = new android.util.LongSparseArray<>();
                this.mNodeCache.put(windowId, longSparseArray);
            }
            long sourceNodeId = accessibilityNodeInfo.getSourceNodeId();
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2 = longSparseArray.get(sourceNodeId);
            if (accessibilityNodeInfo2 != null) {
                android.util.LongArray childNodeIds = accessibilityNodeInfo.getChildNodeIds();
                int childCount = accessibilityNodeInfo2.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    long childId = accessibilityNodeInfo2.getChildId(i);
                    if (childNodeIds == null || childNodeIds.indexOf(childId) < 0) {
                        clearSubTreeLocked(windowId, childId);
                    }
                    if (longSparseArray.get(sourceNodeId) == null) {
                        clearNodesForWindowLocked(windowId);
                        return;
                    }
                }
                long parentNodeId = accessibilityNodeInfo2.getParentNodeId();
                if (accessibilityNodeInfo.getParentNodeId() != parentNodeId) {
                    clearSubTreeLocked(windowId, parentNodeId);
                }
            }
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo3 = new android.view.accessibility.AccessibilityNodeInfo(accessibilityNodeInfo);
            longSparseArray.put(sourceNodeId, accessibilityNodeInfo3);
            if (accessibilityNodeInfo3.isAccessibilityFocused()) {
                if (this.mAccessibilityFocus != 2147483647L && this.mAccessibilityFocus != sourceNodeId) {
                    removeCachedNodeLocked(windowId, this.mAccessibilityFocus);
                }
                this.mAccessibilityFocus = sourceNodeId;
                this.mAccessibilityFocusedWindow = windowId;
            } else if (this.mAccessibilityFocus == sourceNodeId) {
                this.mAccessibilityFocus = 2147483647L;
                this.mAccessibilityFocusedWindow = -1;
            }
            if (accessibilityNodeInfo3.isFocused()) {
                this.mInputFocus = sourceNodeId;
                this.mInputFocusWindow = windowId;
            }
            if (this.mOnNodeAddedListener != null) {
                this.mOnNodeAddedListener.onNodeAdded(accessibilityNodeInfo3);
            }
        }
    }

    public void clear() {
        synchronized (this.mLock) {
            if (DEBUG) {
                android.util.Log.i(LOG_TAG, "clear()");
            }
            clearWindowCacheLocked();
            for (int size = this.mNodeCache.size() - 1; size >= 0; size--) {
                clearNodesForWindowLocked(this.mNodeCache.keyAt(size));
            }
            this.mAccessibilityFocus = 2147483647L;
            this.mInputFocus = 2147483647L;
            this.mAccessibilityFocusedWindow = -1;
            this.mInputFocusWindow = -1;
        }
    }

    private void clearWindowCacheLocked() {
        if (DEBUG) {
            android.util.Log.i(LOG_TAG, "clearWindowCacheLocked");
        }
        int size = this.mWindowCacheByDisplay.size();
        if (size > 0) {
            for (int i = size - 1; i >= 0; i--) {
                int keyAt = this.mWindowCacheByDisplay.keyAt(i);
                android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> sparseArray = this.mWindowCacheByDisplay.get(keyAt);
                if (sparseArray != null) {
                    sparseArray.clear();
                }
                this.mWindowCacheByDisplay.remove(keyAt);
            }
        }
        this.mIsAllWindowsCached = false;
    }

    public android.view.accessibility.AccessibilityNodeInfo getFocus(int i, long j, int i2) {
        int i3;
        long j2;
        java.lang.String str;
        java.lang.String str2;
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            if (i == 2) {
                i3 = this.mAccessibilityFocusedWindow;
                j2 = this.mAccessibilityFocus;
            } else {
                i3 = this.mInputFocusWindow;
                j2 = this.mInputFocus;
            }
            if (i3 == -1) {
                return null;
            }
            if (i2 != -2 && i2 != i3) {
                return null;
            }
            android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i3);
            if (longSparseArray == null) {
                return null;
            }
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j2);
            if (accessibilityNodeInfo == null) {
                return null;
            }
            if (j != j2 && !isCachedNodeOrDescendantLocked(accessibilityNodeInfo.getParentNodeId(), j, longSparseArray)) {
                if (VERBOSE) {
                    java.lang.StringBuilder append = new java.lang.StringBuilder().append("getFocus is null with type: ");
                    if (i == 2) {
                        str2 = "FOCUS_ACCESSIBILITY";
                    } else {
                        str2 = "FOCUS_INPUT";
                    }
                    android.util.Log.i(LOG_TAG, append.append(str2).toString());
                }
                return null;
            }
            if (VERBOSE) {
                java.lang.StringBuilder append2 = new java.lang.StringBuilder().append("getFocus(0x").append(java.lang.Long.toHexString(j2)).append(") = ").append(accessibilityNodeInfo).append(" with type: ");
                if (i == 2) {
                    str = "FOCUS_ACCESSIBILITY";
                } else {
                    str = "FOCUS_INPUT";
                }
                android.util.Log.i(LOG_TAG, append2.append(str).toString());
            }
            return new android.view.accessibility.AccessibilityNodeInfo(accessibilityNodeInfo);
        }
    }

    private boolean isCachedNodeOrDescendantLocked(long j, long j2, android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray) {
        if (j2 == j) {
            return true;
        }
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j);
        if (accessibilityNodeInfo == null) {
            return false;
        }
        return isCachedNodeOrDescendantLocked(accessibilityNodeInfo.getParentNodeId(), j2, longSparseArray);
    }

    private void clearNodesForWindowLocked(int i) {
        if (DEBUG) {
            android.util.Log.i(LOG_TAG, "clearNodesForWindowLocked(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mNodeCache.get(i) == null) {
            return;
        }
        this.mNodeCache.remove(i);
    }

    public boolean clearSubTree(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    android.util.Log.i(LOG_TAG, "Cache is disabled");
                }
                return false;
            }
            clearSubTreeLocked(accessibilityNodeInfo.getWindowId(), accessibilityNodeInfo.getSourceNodeId());
            return true;
        }
    }

    private void clearSubTreeLocked(int i, long j) {
        if (DEBUG) {
            android.util.Log.i(LOG_TAG, "Clearing cached subtree.");
        }
        android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i);
        if (longSparseArray != null) {
            clearSubTreeRecursiveLocked(longSparseArray, j);
        }
    }

    private boolean clearSubTreeRecursiveLocked(android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> longSparseArray, long j) {
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j);
        if (accessibilityNodeInfo == null) {
            clear();
            return true;
        }
        longSparseArray.remove(j);
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (clearSubTreeRecursiveLocked(longSparseArray, accessibilityNodeInfo.getChildId(i))) {
                return true;
            }
        }
        return false;
    }

    public void checkIntegrity() {
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        boolean z;
        android.view.accessibility.AccessibilityCache accessibilityCache = this;
        synchronized (accessibilityCache.mLock) {
            if (accessibilityCache.mWindowCacheByDisplay.size() > 0 || accessibilityCache.mNodeCache.size() != 0) {
                int size = accessibilityCache.mWindowCacheByDisplay.size();
                android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2 = null;
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo = null;
                android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo2 = null;
                for (int i = 0; i < size; i++) {
                    android.util.SparseArray<android.view.accessibility.AccessibilityWindowInfo> valueAt = accessibilityCache.mWindowCacheByDisplay.valueAt(i);
                    if (valueAt != null) {
                        int size2 = valueAt.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            android.view.accessibility.AccessibilityWindowInfo valueAt2 = valueAt.valueAt(i2);
                            if (valueAt2.isActive()) {
                                if (accessibilityWindowInfo != null) {
                                    android.util.Log.e(LOG_TAG, "Duplicate active window:" + valueAt2);
                                } else {
                                    accessibilityWindowInfo = valueAt2;
                                }
                            }
                            if (valueAt2.isFocused()) {
                                if (accessibilityWindowInfo2 != null) {
                                    android.util.Log.e(LOG_TAG, "Duplicate focused window:" + valueAt2);
                                } else {
                                    accessibilityWindowInfo2 = valueAt2;
                                }
                            }
                        }
                    }
                }
                int size3 = accessibilityCache.mNodeCache.size();
                android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo3 = null;
                int i3 = 0;
                while (i3 < size3) {
                    android.util.LongSparseArray<android.view.accessibility.AccessibilityNodeInfo> valueAt3 = accessibilityCache.mNodeCache.valueAt(i3);
                    if (valueAt3.size() > 0) {
                        android.util.ArraySet arraySet = new android.util.ArraySet();
                        int keyAt = accessibilityCache.mNodeCache.keyAt(i3);
                        int size4 = valueAt3.size();
                        for (int i4 = 0; i4 < size4; i4++) {
                            android.view.accessibility.AccessibilityNodeInfo valueAt4 = valueAt3.valueAt(i4);
                            if (!arraySet.add(valueAt4)) {
                                android.util.Log.e(LOG_TAG, "Duplicate node: " + valueAt4 + " in window:" + keyAt);
                            } else {
                                if (valueAt4.isAccessibilityFocused()) {
                                    if (accessibilityNodeInfo2 != null) {
                                        android.util.Log.e(LOG_TAG, "Duplicate accessibility focus:" + valueAt4 + " in window:" + keyAt);
                                    } else {
                                        accessibilityNodeInfo2 = valueAt4;
                                    }
                                }
                                if (valueAt4.isFocused()) {
                                    if (accessibilityNodeInfo3 != null) {
                                        android.util.Log.e(LOG_TAG, "Duplicate input focus: " + valueAt4 + " in window:" + keyAt);
                                    } else {
                                        accessibilityNodeInfo3 = valueAt4;
                                    }
                                }
                                android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo4 = valueAt3.get(valueAt4.getParentNodeId());
                                if (accessibilityNodeInfo4 == null) {
                                    accessibilityNodeInfo = accessibilityNodeInfo3;
                                } else {
                                    int childCount = accessibilityNodeInfo4.getChildCount();
                                    int i5 = 0;
                                    while (true) {
                                        if (i5 >= childCount) {
                                            accessibilityNodeInfo = accessibilityNodeInfo3;
                                            z = false;
                                            break;
                                        }
                                        accessibilityNodeInfo = accessibilityNodeInfo3;
                                        if (valueAt3.get(accessibilityNodeInfo4.getChildId(i5)) != valueAt4) {
                                            i5++;
                                            accessibilityNodeInfo3 = accessibilityNodeInfo;
                                        } else {
                                            z = true;
                                            break;
                                        }
                                    }
                                    if (!z) {
                                        android.util.Log.e(LOG_TAG, "Invalid parent-child relation between parent: " + accessibilityNodeInfo4 + " and child: " + valueAt4);
                                    }
                                }
                                int childCount2 = valueAt4.getChildCount();
                                for (int i6 = 0; i6 < childCount2; i6++) {
                                    android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo5 = valueAt3.get(valueAt4.getChildId(i6));
                                    if (accessibilityNodeInfo5 != null && valueAt3.get(accessibilityNodeInfo5.getParentNodeId()) != valueAt4) {
                                        android.util.Log.e(LOG_TAG, "Invalid child-parent relation between child: " + valueAt4 + " and parent: " + accessibilityNodeInfo4);
                                    }
                                }
                                accessibilityNodeInfo3 = accessibilityNodeInfo;
                            }
                        }
                    }
                    i3++;
                    accessibilityCache = this;
                }
            }
        }
    }

    public void registerOnNodeAddedListener(android.view.accessibility.AccessibilityCache.OnNodeAddedListener onNodeAddedListener) {
        synchronized (this.mLock) {
            this.mOnNodeAddedListener = onNodeAddedListener;
        }
    }

    public void clearOnNodeAddedListener() {
        synchronized (this.mLock) {
            this.mOnNodeAddedListener = null;
        }
    }

    public static class AccessibilityNodeRefresher {
        public boolean refreshNode(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            return accessibilityNodeInfo.refresh(null, z);
        }

        public boolean refreshWindow(android.view.accessibility.AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.refresh();
        }
    }
}
