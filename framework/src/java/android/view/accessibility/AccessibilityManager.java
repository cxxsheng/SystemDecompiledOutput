package android.view.accessibility;

/* loaded from: classes4.dex */
public final class AccessibilityManager {
    public static final int ACCESSIBILITY_BUTTON = 0;
    public static final int ACCESSIBILITY_SHORTCUT_KEY = 1;
    public static final java.lang.String ACTION_CHOOSE_ACCESSIBILITY_BUTTON = "com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON";
    public static final int AUTOCLICK_DELAY_DEFAULT = 600;
    public static final int DALTONIZER_CORRECT_DEUTERANOMALY = 12;
    public static final int DALTONIZER_DISABLED = -1;
    public static final int DALTONIZER_SIMULATE_MONOCHROMACY = 0;
    private static final boolean DEBUG = false;
    public static final int FLAG_CONTENT_CONTROLS = 4;
    public static final int FLAG_CONTENT_ICONS = 1;
    public static final int FLAG_CONTENT_TEXT = 2;
    public static final int FLASH_REASON_ALARM = 2;
    public static final int FLASH_REASON_CALL = 1;
    public static final int FLASH_REASON_NOTIFICATION = 3;
    public static final int FLASH_REASON_PREVIEW = 4;
    private static final java.lang.String LOG_TAG = "AccessibilityManager";
    public static final int STATE_FLAG_ACCESSIBILITY_ENABLED = 1;
    public static final int STATE_FLAG_AUDIO_DESCRIPTION_BY_DEFAULT_ENABLED = 4096;
    public static final int STATE_FLAG_DISPATCH_DOUBLE_TAP = 8;
    public static final int STATE_FLAG_HIGH_TEXT_CONTRAST_ENABLED = 4;
    public static final int STATE_FLAG_REQUEST_MULTI_FINGER_GESTURES = 16;
    public static final int STATE_FLAG_TOUCH_EXPLORATION_ENABLED = 2;
    public static final int STATE_FLAG_TRACE_A11Y_INTERACTION_CLIENT_ENABLED = 1024;
    public static final int STATE_FLAG_TRACE_A11Y_INTERACTION_CONNECTION_CB_ENABLED = 512;
    public static final int STATE_FLAG_TRACE_A11Y_INTERACTION_CONNECTION_ENABLED = 256;
    public static final int STATE_FLAG_TRACE_A11Y_SERVICE_ENABLED = 2048;
    private static android.view.accessibility.AccessibilityManager sInstance;
    android.view.accessibility.AccessibilityManager.AccessibilityPolicy mAccessibilityPolicy;
    private int mFocusColor;
    private int mFocusStrokeWidth;
    final android.os.Handler mHandler;
    int mInteractiveUiTimeout;
    boolean mIsAudioDescriptionByDefaultRequested;
    boolean mIsEnabled;
    boolean mIsHighTextContrastEnabled;
    boolean mIsTouchExplorationEnabled;
    int mNonInteractiveUiTimeout;
    private boolean mRequestFromAccessibilityTool;
    private android.util.SparseArray<java.util.List<android.view.accessibility.AccessibilityRequestPreparer>> mRequestPreparerLists;
    private android.view.accessibility.IAccessibilityManager mService;
    final int mUserId;
    public static final int[] SHORTCUT_TYPES = {0, 1};
    static final java.lang.Object sInstanceSync = new java.lang.Object();
    private final java.lang.Object mLock = new java.lang.Object();
    int mRelevantEventTypes = -1;
    int mAccessibilityTracingState = 0;
    private int mPerformingAction = 0;
    private final android.util.ArrayMap<android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener, android.os.Handler> mAccessibilityStateChangeListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener, android.os.Handler> mTouchExplorationStateChangeListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener, android.os.Handler> mHighTextContrastStateChangeListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener, java.util.concurrent.Executor> mServicesStateChangeListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.view.accessibility.AccessibilityManager.AudioDescriptionRequestedChangeListener, java.util.concurrent.Executor> mAudioDescriptionRequestedChangeListeners = new android.util.ArrayMap<>();
    private final android.os.Binder mBinder = new android.os.Binder();
    private final android.view.accessibility.IAccessibilityManagerClient.Stub mClient = new android.view.accessibility.AccessibilityManager.AnonymousClass1();
    final android.os.Handler.Callback mCallback = new android.view.accessibility.AccessibilityManager.MyCallback();

    public interface AccessibilityPolicy {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list);

        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList(java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list);

        int getRelevantEventTypes(int i);

        boolean isEnabled(boolean z);

        android.view.accessibility.AccessibilityEvent onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z, int i);
    }

    public interface AccessibilityServicesStateChangeListener {
        void onAccessibilityServicesStateChanged(android.view.accessibility.AccessibilityManager accessibilityManager);
    }

    public interface AccessibilityStateChangeListener {
        void onAccessibilityStateChanged(boolean z);
    }

    public interface AudioDescriptionRequestedChangeListener {
        void onAudioDescriptionRequestedChanged(boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FlashNotificationReason {
    }

    public interface HighTextContrastChangeListener {
        void onHighTextContrastStateChanged(boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShortcutType {
    }

    public interface TouchExplorationStateChangeListener {
        void onTouchExplorationStateChanged(boolean z);
    }

    /* renamed from: android.view.accessibility.AccessibilityManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.view.accessibility.IAccessibilityManagerClient.Stub {
        AnonymousClass1() {
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setState(int i) {
            android.view.accessibility.AccessibilityManager.this.mHandler.obtainMessage(1, i, 0).sendToTarget();
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void notifyServicesStateChanged(long j) {
            android.view.accessibility.AccessibilityManager.this.updateUiTimeout(j);
            synchronized (android.view.accessibility.AccessibilityManager.this.mLock) {
                if (android.view.accessibility.AccessibilityManager.this.mServicesStateChangeListeners.isEmpty()) {
                    return;
                }
                int size = new android.util.ArrayMap(android.view.accessibility.AccessibilityManager.this.mServicesStateChangeListeners).size();
                for (int i = 0; i < size; i++) {
                    final android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener = (android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener) android.view.accessibility.AccessibilityManager.this.mServicesStateChangeListeners.keyAt(i);
                    ((java.util.concurrent.Executor) android.view.accessibility.AccessibilityManager.this.mServicesStateChangeListeners.valueAt(i)).execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityManager$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.view.accessibility.AccessibilityManager.AnonymousClass1.this.lambda$notifyServicesStateChanged$0(accessibilityServicesStateChangeListener);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyServicesStateChanged$0(android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
            accessibilityServicesStateChangeListener.onAccessibilityServicesStateChanged(android.view.accessibility.AccessibilityManager.this);
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setRelevantEventTypes(int i) {
            android.view.accessibility.AccessibilityManager.this.mRelevantEventTypes = i;
        }

        @Override // android.view.accessibility.IAccessibilityManagerClient
        public void setFocusAppearance(int i, int i2) {
            synchronized (android.view.accessibility.AccessibilityManager.this.mLock) {
                android.view.accessibility.AccessibilityManager.this.updateFocusAppearanceLocked(i, i2);
            }
        }
    }

    public static android.view.accessibility.AccessibilityManager getInstance(android.content.Context context) {
        int i;
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                if (android.os.Binder.getCallingUid() != 1000 && context.checkCallingOrSelfPermission(android.Manifest.permission.INTERACT_ACROSS_USERS) != 0 && context.checkCallingOrSelfPermission(android.Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
                    i = context.getUserId();
                    sInstance = new android.view.accessibility.AccessibilityManager(context, null, i);
                }
                i = -2;
                sInstance = new android.view.accessibility.AccessibilityManager(context, null, i);
            }
        }
        return sInstance;
    }

    public AccessibilityManager(android.content.Context context, android.view.accessibility.IAccessibilityManager iAccessibilityManager, int i) {
        this.mHandler = new android.os.Handler(context.getMainLooper(), this.mCallback);
        this.mUserId = i;
        synchronized (this.mLock) {
            initialFocusAppearanceLocked(context.getResources());
            tryConnectToServiceLocked(iAccessibilityManager);
        }
    }

    public AccessibilityManager(android.content.Context context, android.os.Handler handler, android.view.accessibility.IAccessibilityManager iAccessibilityManager, int i, boolean z) {
        this.mHandler = handler;
        this.mUserId = i;
        synchronized (this.mLock) {
            initialFocusAppearanceLocked(context.getResources());
            if (z) {
                tryConnectToServiceLocked(iAccessibilityManager);
            }
        }
    }

    public android.view.accessibility.IAccessibilityManagerClient getClient() {
        return this.mClient;
    }

    public boolean removeClient() {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.removeClient(this.mClient, this.mUserId);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "AccessibilityManagerService is dead", e);
                return false;
            }
        }
    }

    public android.os.Handler.Callback getCallback() {
        return this.mCallback;
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsEnabled || hasAnyDirectConnection() || (this.mAccessibilityPolicy != null && this.mAccessibilityPolicy.isEnabled(this.mIsEnabled));
        }
        return z;
    }

    public boolean hasAnyDirectConnection() {
        return android.view.accessibility.AccessibilityInteractionClient.hasAnyDirectConnection();
    }

    public boolean isTouchExplorationEnabled() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            return this.mIsTouchExplorationEnabled;
        }
    }

    public boolean isHighTextContrastEnabled() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            return this.mIsHighTextContrastEnabled;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x009c, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0099, code lost:
    
        if (r7 == r2) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        android.view.accessibility.AccessibilityEvent accessibilityEvent2;
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            accessibilityEvent.setEventTime(android.os.SystemClock.uptimeMillis());
            if (accessibilityEvent.getAction() == 0) {
                accessibilityEvent.setAction(this.mPerformingAction);
            }
            if (this.mAccessibilityPolicy != null) {
                accessibilityEvent2 = this.mAccessibilityPolicy.onAccessibilityEvent(accessibilityEvent, this.mIsEnabled, this.mRelevantEventTypes);
                if (accessibilityEvent2 == null) {
                    return;
                }
            } else {
                accessibilityEvent2 = accessibilityEvent;
            }
            if (!isEnabled()) {
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                    throw new java.lang.IllegalStateException("Accessibility off. Did you forget to check that?");
                }
                android.util.Log.e(LOG_TAG, "AccessibilityEvent sent with accessibility disabled");
            } else {
                if ((accessibilityEvent2.getEventType() & this.mRelevantEventTypes) == 0) {
                    return;
                }
                int i = this.mUserId;
                try {
                    try {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            serviceLocked.sendAccessibilityEvent(accessibilityEvent2, i);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(LOG_TAG, "Error during sending " + accessibilityEvent2 + " ", e);
                    }
                } finally {
                    if (accessibilityEvent != accessibilityEvent2) {
                        accessibilityEvent.recycle();
                    }
                    accessibilityEvent2.recycle();
                }
            }
        }
    }

    public void interrupt() {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            if (!isEnabled()) {
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                    throw new java.lang.IllegalStateException("Accessibility off. Did you forget to check that?");
                }
                android.util.Log.e(LOG_TAG, "Interrupt called with accessibility disabled");
            } else {
                int i = this.mUserId;
                try {
                    serviceLocked.interrupt(i);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(LOG_TAG, "Error while requesting interrupt from all services. ", e);
                }
            }
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.content.pm.ServiceInfo> getAccessibilityServiceList() {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> installedAccessibilityServiceList = getInstalledAccessibilityServiceList();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = installedAccessibilityServiceList.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(installedAccessibilityServiceList.get(i).getResolveInfo().serviceInfo);
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList() {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list;
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return java.util.Collections.emptyList();
            }
            int i = this.mUserId;
            try {
                list = serviceLocked.getInstalledAccessibilityServiceList(i).getList();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while obtaining the installed AccessibilityServices. ", e);
                list = null;
            }
            if (this.mAccessibilityPolicy != null) {
                list = this.mAccessibilityPolicy.getInstalledAccessibilityServiceList(list);
            }
            if (list != null) {
                return java.util.Collections.unmodifiableList(list);
            }
            return java.util.Collections.emptyList();
        }
    }

    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i) {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list;
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return java.util.Collections.emptyList();
            }
            int i2 = this.mUserId;
            try {
                list = serviceLocked.getEnabledAccessibilityServiceList(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while obtaining the enabled AccessibilityServices. ", e);
                list = null;
            }
            if (this.mAccessibilityPolicy != null) {
                list = this.mAccessibilityPolicy.getEnabledAccessibilityServiceList(i, list);
            }
            if (list != null) {
                return java.util.Collections.unmodifiableList(list);
            }
            return java.util.Collections.emptyList();
        }
    }

    public boolean isAccessibilityServiceWarningRequired(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return true;
            }
            try {
                return serviceLocked.isAccessibilityServiceWarningRequired(accessibilityServiceInfo);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while checking isAccessibilityServiceWarningRequired: ", e);
                return true;
            }
        }
    }

    public boolean addAccessibilityStateChangeListener(android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener) {
        addAccessibilityStateChangeListener(accessibilityStateChangeListener, null);
        return true;
    }

    public void addAccessibilityStateChangeListener(android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener, android.os.Handler handler) {
        synchronized (this.mLock) {
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener, android.os.Handler> arrayMap = this.mAccessibilityStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(accessibilityStateChangeListener, handler);
        }
    }

    public boolean removeAccessibilityStateChangeListener(android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            int indexOfKey = this.mAccessibilityStateChangeListeners.indexOfKey(accessibilityStateChangeListener);
            this.mAccessibilityStateChangeListeners.remove(accessibilityStateChangeListener);
            z = indexOfKey >= 0;
        }
        return z;
    }

    public boolean addTouchExplorationStateChangeListener(android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        addTouchExplorationStateChangeListener(touchExplorationStateChangeListener, null);
        return true;
    }

    public void addTouchExplorationStateChangeListener(android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener, android.os.Handler handler) {
        synchronized (this.mLock) {
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener, android.os.Handler> arrayMap = this.mTouchExplorationStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(touchExplorationStateChangeListener, handler);
        }
    }

    public boolean removeTouchExplorationStateChangeListener(android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            int indexOfKey = this.mTouchExplorationStateChangeListeners.indexOfKey(touchExplorationStateChangeListener);
            this.mTouchExplorationStateChangeListeners.remove(touchExplorationStateChangeListener);
            z = indexOfKey >= 0;
        }
        return z;
    }

    public void addAccessibilityServicesStateChangeListener(java.util.concurrent.Executor executor, android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        synchronized (this.mLock) {
            this.mServicesStateChangeListeners.put(accessibilityServicesStateChangeListener, executor);
        }
    }

    public void addAccessibilityServicesStateChangeListener(android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        addAccessibilityServicesStateChangeListener(new android.os.HandlerExecutor(this.mHandler), accessibilityServicesStateChangeListener);
    }

    public boolean removeAccessibilityServicesStateChangeListener(android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mServicesStateChangeListeners.remove(accessibilityServicesStateChangeListener) != null;
        }
        return z;
    }

    public boolean isRequestFromAccessibilityTool() {
        return this.mRequestFromAccessibilityTool;
    }

    public void setRequestFromAccessibilityTool(boolean z) {
        this.mRequestFromAccessibilityTool = z;
    }

    public void addAccessibilityRequestPreparer(android.view.accessibility.AccessibilityRequestPreparer accessibilityRequestPreparer) {
        if (this.mRequestPreparerLists == null) {
            this.mRequestPreparerLists = new android.util.SparseArray<>(1);
        }
        int accessibilityViewId = accessibilityRequestPreparer.getAccessibilityViewId();
        java.util.List<android.view.accessibility.AccessibilityRequestPreparer> list = this.mRequestPreparerLists.get(accessibilityViewId);
        if (list == null) {
            list = new java.util.ArrayList<>(1);
            this.mRequestPreparerLists.put(accessibilityViewId, list);
        }
        list.add(accessibilityRequestPreparer);
    }

    public void removeAccessibilityRequestPreparer(android.view.accessibility.AccessibilityRequestPreparer accessibilityRequestPreparer) {
        int accessibilityViewId;
        java.util.List<android.view.accessibility.AccessibilityRequestPreparer> list;
        if (this.mRequestPreparerLists != null && (list = this.mRequestPreparerLists.get((accessibilityViewId = accessibilityRequestPreparer.getAccessibilityViewId()))) != null) {
            list.remove(accessibilityRequestPreparer);
            if (list.isEmpty()) {
                this.mRequestPreparerLists.remove(accessibilityViewId);
            }
        }
    }

    public int getRecommendedTimeoutMillis(int i, int i2) {
        boolean z = (i2 & 4) != 0;
        boolean z2 = ((i2 & 1) == 0 && (i2 & 2) == 0) ? false : true;
        if (z) {
            i = java.lang.Math.max(i, this.mInteractiveUiTimeout);
        }
        if (z2) {
            return java.lang.Math.max(i, this.mNonInteractiveUiTimeout);
        }
        return i;
    }

    public int getAccessibilityFocusStrokeWidth() {
        int i;
        synchronized (this.mLock) {
            i = this.mFocusStrokeWidth;
        }
        return i;
    }

    public int getAccessibilityFocusColor() {
        int i;
        synchronized (this.mLock) {
            i = this.mFocusColor;
        }
        return i;
    }

    public boolean isA11yInteractionConnectionTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 256) != 0;
        }
        return z;
    }

    public boolean isA11yInteractionConnectionCBTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 512) != 0;
        }
        return z;
    }

    public boolean isA11yInteractionClientTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 1024) != 0;
        }
        return z;
    }

    public boolean isA11yServiceTraceEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mAccessibilityTracingState & 2048) != 0;
        }
        return z;
    }

    public java.util.List<android.view.accessibility.AccessibilityRequestPreparer> getRequestPreparersForAccessibilityId(int i) {
        if (this.mRequestPreparerLists == null) {
            return null;
        }
        return this.mRequestPreparerLists.get(i);
    }

    public void notifyPerformingAction(int i) {
        this.mPerformingAction = i;
    }

    public int getPerformingAction() {
        return this.mPerformingAction;
    }

    public void addHighTextContrastStateChangeListener(android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener highTextContrastChangeListener, android.os.Handler handler) {
        synchronized (this.mLock) {
            android.util.ArrayMap<android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener, android.os.Handler> arrayMap = this.mHighTextContrastStateChangeListeners;
            if (handler == null) {
                handler = this.mHandler;
            }
            arrayMap.put(highTextContrastChangeListener, handler);
        }
    }

    public void removeHighTextContrastStateChangeListener(android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener highTextContrastChangeListener) {
        synchronized (this.mLock) {
            this.mHighTextContrastStateChangeListeners.remove(highTextContrastChangeListener);
        }
    }

    public void addAudioDescriptionRequestedChangeListener(java.util.concurrent.Executor executor, android.view.accessibility.AccessibilityManager.AudioDescriptionRequestedChangeListener audioDescriptionRequestedChangeListener) {
        synchronized (this.mLock) {
            this.mAudioDescriptionRequestedChangeListeners.put(audioDescriptionRequestedChangeListener, executor);
        }
    }

    public boolean removeAudioDescriptionRequestedChangeListener(android.view.accessibility.AccessibilityManager.AudioDescriptionRequestedChangeListener audioDescriptionRequestedChangeListener) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAudioDescriptionRequestedChangeListeners.remove(audioDescriptionRequestedChangeListener) != null;
        }
        return z;
    }

    public void setAccessibilityPolicy(android.view.accessibility.AccessibilityManager.AccessibilityPolicy accessibilityPolicy) {
        synchronized (this.mLock) {
            this.mAccessibilityPolicy = accessibilityPolicy;
        }
    }

    public boolean isAccessibilityVolumeStreamActive() {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> enabledAccessibilityServiceList = getEnabledAccessibilityServiceList(-1);
        for (int i = 0; i < enabledAccessibilityServiceList.size(); i++) {
            if ((enabledAccessibilityServiceList.get(i).flags & 128) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean sendFingerprintGesture(int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.sendFingerprintGesture(i);
            } catch (android.os.RemoteException e) {
                return false;
            }
        }
    }

    @android.annotation.SystemApi
    public int getAccessibilityWindowId(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return -1;
        }
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return -1;
            }
            try {
                return serviceLocked.getAccessibilityWindowId(iBinder);
            } catch (android.os.RemoteException e) {
                return -1;
            }
        }
    }

    public void associateEmbeddedHierarchy(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.associateEmbeddedHierarchy(iBinder, iBinder2);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void disassociateEmbeddedHierarchy(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return;
        }
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.disassociateEmbeddedHierarchy(iBinder);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStateLocked(int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 4) != 0;
        boolean z4 = (i & 4096) != 0;
        boolean isEnabled = isEnabled();
        boolean z5 = this.mIsTouchExplorationEnabled;
        boolean z6 = this.mIsHighTextContrastEnabled;
        boolean z7 = this.mIsAudioDescriptionByDefaultRequested;
        this.mIsEnabled = z;
        this.mIsTouchExplorationEnabled = z2;
        this.mIsHighTextContrastEnabled = z3;
        this.mIsAudioDescriptionByDefaultRequested = z4;
        if (isEnabled != isEnabled()) {
            notifyAccessibilityStateChanged();
        }
        if (z5 != z2) {
            notifyTouchExplorationStateChanged();
        }
        if (z6 != z3) {
            notifyHighTextContrastStateChanged();
        }
        if (z7 != z4) {
            notifyAudioDescriptionbyDefaultStateChanged();
        }
        updateAccessibilityTracingState(i);
    }

    public android.accessibilityservice.AccessibilityServiceInfo getInstalledServiceInfoWithComponentName(android.content.ComponentName componentName) {
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> installedAccessibilityServiceList = getInstalledAccessibilityServiceList();
        if (installedAccessibilityServiceList == null || componentName == null) {
            return null;
        }
        for (int i = 0; i < installedAccessibilityServiceList.size(); i++) {
            if (componentName.equals(installedAccessibilityServiceList.get(i).getComponentName())) {
                return installedAccessibilityServiceList.get(i);
            }
        }
        return null;
    }

    public int addAccessibilityInteractionConnection(android.view.IWindow iWindow, android.os.IBinder iBinder, java.lang.String str, android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return -1;
            }
            int i = this.mUserId;
            try {
                return serviceLocked.addAccessibilityInteractionConnection(iWindow, iBinder, iAccessibilityInteractionConnection, str, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while adding an accessibility interaction connection. ", e);
                return -1;
            }
        }
    }

    public void removeAccessibilityInteractionConnection(android.view.IWindow iWindow) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.removeAccessibilityInteractionConnection(iWindow);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while removing an accessibility interaction connection. ", e);
            }
        }
    }

    @android.annotation.SystemApi
    public void performAccessibilityShortcut() {
        performAccessibilityShortcut(null);
    }

    public void performAccessibilityShortcut(java.lang.String str) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.performAccessibilityShortcut(str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error performing accessibility shortcut. ", e);
            }
        }
    }

    @android.annotation.SystemApi
    public void registerSystemAction(android.app.RemoteAction remoteAction, int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.registerSystemAction(remoteAction, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error registering system action " + ((java.lang.Object) remoteAction.getTitle()) + " ", e);
            }
        }
    }

    @android.annotation.SystemApi
    public void unregisterSystemAction(int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.unregisterSystemAction(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error unregistering system action with actionId " + i + " ", e);
            }
        }
    }

    public void notifyAccessibilityButtonClicked(int i) {
        notifyAccessibilityButtonClicked(i, null);
    }

    public void notifyAccessibilityButtonClicked(int i, java.lang.String str) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.notifyAccessibilityButtonClicked(i, str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while dispatching accessibility button click", e);
            }
        }
    }

    public void notifyAccessibilityButtonVisibilityChanged(boolean z) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.notifyAccessibilityButtonVisibilityChanged(z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while dispatching accessibility button visibility change", e);
            }
        }
    }

    public void setPictureInPictureActionReplacingConnection(android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setPictureInPictureActionReplacingConnection(iAccessibilityInteractionConnection);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error setting picture in picture action replacement", e);
            }
        }
    }

    public java.util.List<java.lang.String> getAccessibilityShortcutTargets(int i) {
        android.view.accessibility.IAccessibilityManager serviceLocked;
        synchronized (this.mLock) {
            serviceLocked = getServiceLocked();
        }
        if (serviceLocked != null) {
            try {
                return serviceLocked.getAccessibilityShortcutTargets(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return java.util.Collections.emptyList();
    }

    public java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> getInstalledAccessibilityShortcutListAsUser(android.content.Context context, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MAIN);
        intent.addCategory(android.content.Intent.CATEGORY_ACCESSIBILITY_SHORTCUT_TARGET);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser = context.getPackageManager().queryIntentActivitiesAsUser(intent, 819329, i);
        for (int i2 = 0; i2 < queryIntentActivitiesAsUser.size(); i2++) {
            android.accessibilityservice.AccessibilityShortcutInfo shortcutInfo = getShortcutInfo(context, queryIntentActivitiesAsUser.get(i2));
            if (shortcutInfo != null) {
                arrayList.add(shortcutInfo);
            }
        }
        return arrayList;
    }

    private android.accessibilityservice.AccessibilityShortcutInfo getShortcutInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo) {
        android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (activityInfo == null || activityInfo.metaData == null || activityInfo.metaData.getInt(android.accessibilityservice.AccessibilityShortcutInfo.META_DATA) == 0) {
            return null;
        }
        try {
            return new android.accessibilityservice.AccessibilityShortcutInfo(context, activityInfo);
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(LOG_TAG, "Error while initializing AccessibilityShortcutInfo", e);
            return null;
        }
    }

    public void setMagnificationConnection(android.view.accessibility.IMagnificationConnection iMagnificationConnection) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setMagnificationConnection(iMagnificationConnection);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error setting magnification connection", e);
            }
        }
    }

    public boolean isAudioDescriptionRequested() {
        synchronized (this.mLock) {
            if (getServiceLocked() == null) {
                return false;
            }
            return this.mIsAudioDescriptionByDefaultRequested;
        }
    }

    public void setSystemAudioCaptioningEnabled(boolean z, int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setSystemAudioCaptioningEnabled(z, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isSystemAudioCaptioningUiEnabled(int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isSystemAudioCaptioningUiEnabled(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setSystemAudioCaptioningUiEnabled(boolean z, int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setSystemAudioCaptioningUiEnabled(z, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setAccessibilityWindowAttributes(int i, int i2, android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.setAccessibilityWindowAttributes(i, i2, this.mUserId, accessibilityWindowAttributes);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public boolean registerDisplayProxy(android.view.accessibility.AccessibilityDisplayProxy accessibilityDisplayProxy) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.registerProxyForDisplay(accessibilityDisplayProxy.mServiceClient, accessibilityDisplayProxy.getDisplayId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public boolean unregisterDisplayProxy(android.view.accessibility.AccessibilityDisplayProxy accessibilityDisplayProxy) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.unregisterProxyForDisplay(accessibilityDisplayProxy.getDisplayId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean startFlashNotificationSequence(android.content.Context context, int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.startFlashNotificationSequence(context.getOpPackageName(), i, this.mBinder);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while start flash notification sequence", e);
                return false;
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean stopFlashNotificationSequence(android.content.Context context) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.stopFlashNotificationSequence(context.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while stop flash notification sequence", e);
                return false;
            }
        }
    }

    public boolean startFlashNotificationEvent(android.content.Context context, int i, java.lang.String str) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.startFlashNotificationEvent(context.getOpPackageName(), i, str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while start flash notification event", e);
                return false;
            }
        }
    }

    public boolean isAccessibilityTargetAllowed(java.lang.String str, int i, int i2) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.isAccessibilityTargetAllowed(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while check accessibility target status", e);
                return false;
            }
        }
    }

    public boolean sendRestrictedDialogIntent(java.lang.String str, int i, int i2) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return false;
            }
            try {
                return serviceLocked.sendRestrictedDialogIntent(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error while show restricted dialog", e);
                return false;
            }
        }
    }

    private android.view.accessibility.IAccessibilityManager getServiceLocked() {
        if (this.mService == null) {
            tryConnectToServiceLocked(null);
        }
        return this.mService;
    }

    private void tryConnectToServiceLocked(android.view.accessibility.IAccessibilityManager iAccessibilityManager) {
        if (iAccessibilityManager == null) {
            android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.ACCESSIBILITY_SERVICE);
            if (service == null) {
                return;
            } else {
                iAccessibilityManager = android.view.accessibility.IAccessibilityManager.Stub.asInterface(service);
            }
        }
        try {
            long addClient = iAccessibilityManager.addClient(this.mClient, this.mUserId);
            setStateLocked(com.android.internal.util.IntPair.first(addClient));
            this.mRelevantEventTypes = com.android.internal.util.IntPair.second(addClient);
            updateUiTimeout(iAccessibilityManager.getRecommendedTimeoutMillis());
            updateFocusAppearanceLocked(iAccessibilityManager.getFocusStrokeWidth(), iAccessibilityManager.getFocusColor());
            this.mService = iAccessibilityManager;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "AccessibilityManagerService is dead", e);
        }
    }

    public void notifyAccessibilityStateChanged() {
        synchronized (this.mLock) {
            if (this.mAccessibilityStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean isEnabled = isEnabled();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mAccessibilityStateChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener = (android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener) arrayMap.keyAt(i);
                ((android.os.Handler) arrayMap.valueAt(i)).post(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener.this.onAccessibilityStateChanged(isEnabled);
                    }
                });
            }
        }
    }

    private void notifyTouchExplorationStateChanged() {
        synchronized (this.mLock) {
            if (this.mTouchExplorationStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean z = this.mIsTouchExplorationEnabled;
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mTouchExplorationStateChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = (android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener) arrayMap.keyAt(i);
                ((android.os.Handler) arrayMap.valueAt(i)).post(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener.this.onTouchExplorationStateChanged(z);
                    }
                });
            }
        }
    }

    private void notifyHighTextContrastStateChanged() {
        synchronized (this.mLock) {
            if (this.mHighTextContrastStateChangeListeners.isEmpty()) {
                return;
            }
            final boolean z = this.mIsHighTextContrastEnabled;
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mHighTextContrastStateChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener highTextContrastChangeListener = (android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener) arrayMap.keyAt(i);
                ((android.os.Handler) arrayMap.valueAt(i)).post(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener.this.onHighTextContrastStateChanged(z);
                    }
                });
            }
        }
    }

    private void notifyAudioDescriptionbyDefaultStateChanged() {
        synchronized (this.mLock) {
            if (this.mAudioDescriptionRequestedChangeListeners.isEmpty()) {
                return;
            }
            final boolean z = this.mIsAudioDescriptionByDefaultRequested;
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mAudioDescriptionRequestedChangeListeners);
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                final android.view.accessibility.AccessibilityManager.AudioDescriptionRequestedChangeListener audioDescriptionRequestedChangeListener = (android.view.accessibility.AccessibilityManager.AudioDescriptionRequestedChangeListener) arrayMap.keyAt(i);
                ((java.util.concurrent.Executor) arrayMap.valueAt(i)).execute(new java.lang.Runnable() { // from class: android.view.accessibility.AccessibilityManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.accessibility.AccessibilityManager.AudioDescriptionRequestedChangeListener.this.onAudioDescriptionRequestedChanged(z);
                    }
                });
            }
        }
    }

    private void updateAccessibilityTracingState(int i) {
        synchronized (this.mLock) {
            this.mAccessibilityTracingState = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiTimeout(long j) {
        this.mInteractiveUiTimeout = com.android.internal.util.IntPair.first(j);
        this.mNonInteractiveUiTimeout = com.android.internal.util.IntPair.second(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFocusAppearanceLocked(int i, int i2) {
        if (this.mFocusStrokeWidth == i && this.mFocusColor == i2) {
            return;
        }
        this.mFocusStrokeWidth = i;
        this.mFocusColor = i2;
    }

    private void initialFocusAppearanceLocked(android.content.res.Resources resources) {
        try {
            this.mFocusStrokeWidth = resources.getDimensionPixelSize(com.android.internal.R.dimen.accessibility_focus_highlight_stroke_width);
            this.mFocusColor = resources.getColor(com.android.internal.R.color.accessibility_focus_highlight_color);
        } catch (android.content.res.Resources.NotFoundException e) {
            this.mFocusStrokeWidth = (int) (resources.getDisplayMetrics().density * 4.0f);
            this.mFocusColor = -1086737152;
            android.util.Log.e(LOG_TAG, "Error while initialing the focus appearance data then setting to default value by hardcoded", e);
        }
    }

    public static boolean isAccessibilityButtonSupported() {
        return android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_showNavigationBar);
    }

    private final class MyCallback implements android.os.Handler.Callback {
        public static final int MSG_SET_STATE = 1;

        private MyCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    int i = message.arg1;
                    synchronized (android.view.accessibility.AccessibilityManager.this.mLock) {
                        android.view.accessibility.AccessibilityManager.this.setStateLocked(i);
                    }
                    return true;
                default:
                    return true;
            }
        }
    }

    public android.view.accessibility.IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int i) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return null;
            }
            try {
                return serviceLocked.getWindowTransformationSpec(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, android.view.SurfaceControl surfaceControl) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.attachAccessibilityOverlayToDisplay(i, surfaceControl);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifyQuickSettingsTilesChanged(int i, java.util.List<android.content.ComponentName> list) {
        synchronized (this.mLock) {
            android.view.accessibility.IAccessibilityManager serviceLocked = getServiceLocked();
            if (serviceLocked == null) {
                return;
            }
            try {
                serviceLocked.notifyQuickSettingsTilesChanged(i, list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
