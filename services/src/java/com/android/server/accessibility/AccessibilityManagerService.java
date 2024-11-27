package com.android.server.accessibility;

/* loaded from: classes.dex */
public class AccessibilityManagerService extends android.view.accessibility.IAccessibilityManager.Stub implements com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport, com.android.server.accessibility.AccessibilityUserState.ServiceInfoChangeListener, com.android.server.accessibility.AccessibilityWindowManager.AccessibilityEventSender, com.android.server.accessibility.AccessibilitySecurityPolicy.AccessibilityUserManager, com.android.server.accessibility.SystemActionPerformer.SystemActionsChangedListener, com.android.server.accessibility.SystemActionPerformer.DisplayUpdateCallBack, com.android.server.accessibility.ProxyManager.SystemSupport {
    private static final char COMPONENT_NAME_SEPARATOR = ':';
    private static final boolean DEBUG = false;
    private static final java.lang.String FUNCTION_REGISTER_UI_TEST_AUTOMATION_SERVICE = "registerUiTestAutomationService";
    private static final java.lang.String GET_WINDOW_TOKEN = "getWindowToken";
    public static final int INVALID_SERVICE_ID = -1;
    private static final java.lang.String LOG_TAG = "AccessibilityManagerService";
    public static final int MAGNIFICATION_GESTURE_HANDLER_ID = 0;
    private static final int POSTPONE_WINDOW_STATE_CHANGED_EVENT_TIMEOUT_MILLIS = 500;
    private static final java.lang.String SET_PIP_ACTION_REPLACEMENT = "setPictureInPictureActionReplacingConnection";
    private static final int WAIT_FOR_USER_STATE_FULLY_INITIALIZED_MILLIS = 3000;
    private static final int WAIT_INPUT_FILTER_INSTALL_TIMEOUT_MS = 1000;
    private final com.android.server.accessibility.AccessibilityManagerService.AccessibilityDisplayListener mA11yDisplayListener;
    private android.util.SparseArray<android.view.SurfaceControl> mA11yOverlayLayers;
    private final com.android.server.accessibility.AccessibilityWindowManager mA11yWindowManager;
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerService;
    private final com.android.server.accessibility.CaptioningManagerImpl mCaptioningManagerImpl;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentUserId;
    android.view.inputmethod.EditorInfo mEditorInfo;
    private android.app.AlertDialog mEnableTouchExplorationDialog;
    private com.android.server.accessibility.FingerprintGestureDispatcher mFingerprintGestureDispatcher;
    private final com.android.server.accessibility.FlashNotificationsController mFlashNotificationsController;
    private final android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> mGlobalClients;
    private boolean mHasInputFilter;
    private boolean mInitialized;
    private boolean mInputBound;
    private com.android.server.accessibility.AccessibilityInputFilter mInputFilter;
    private boolean mInputFilterInstalled;
    boolean mInputSessionRequested;
    private com.android.server.accessibility.AccessibilityManagerService.InteractionBridge mInteractionBridge;
    private boolean mIsAccessibilityButtonShown;
    private com.android.server.accessibility.KeyEventDispatcher mKeyEventDispatcher;
    private final java.lang.Object mLock;
    private final com.android.server.accessibility.magnification.MagnificationController mMagnificationController;
    private final com.android.server.accessibility.magnification.MagnificationProcessor mMagnificationProcessor;
    private final android.os.Handler mMainHandler;
    private android.util.SparseArray<com.android.server.accessibility.MotionEventInjector> mMotionEventInjectors;
    private final android.content.pm.PackageManager mPackageManager;
    private com.android.internal.content.PackageMonitor mPackageMonitor;
    private final android.os.PowerManager mPowerManager;
    private final com.android.server.accessibility.ProxyManager mProxyManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mRealCurrentUserId;
    com.android.internal.inputmethod.IRemoteAccessibilityInputConnection mRemoteInputConnection;
    boolean mRestarting;
    private final com.android.server.accessibility.AccessibilitySecurityPolicy mSecurityPolicy;
    private final java.util.List<com.android.server.accessibility.AccessibilityManagerService.SendWindowStateChangedEventRunnable> mSendWindowStateChangedEventRunnables;
    private final android.text.TextUtils.SimpleStringSplitter mStringColonSplitter;
    private com.android.server.accessibility.SystemActionPerformer mSystemActionPerformer;
    private final java.util.Set<android.content.ComponentName> mTempComponentNameSet;
    private final android.util.IntArray mTempIntArray;
    private android.graphics.Point mTempPoint;
    private final android.graphics.Rect mTempRect;
    private final android.graphics.Rect mTempRect1;
    private final com.android.server.accessibility.AccessibilityTraceManager mTraceManager;
    private final com.android.server.accessibility.UiAutomationManager mUiAutomationManager;
    private final com.android.server.pm.UserManagerInternal mUmi;

    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<com.android.server.accessibility.AccessibilityUserState> mUserStates;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private final android.util.SparseBooleanArray mVisibleBgUserIds;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerService;
    private static final int OWN_PROCESS_ID = android.os.Process.myPid();
    private static int sIdCounter = 1;

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.accessibility.AccessibilityUserState getCurrentUserStateLocked() {
        return getUserStateLocked(this.mCurrentUserId);
    }

    public void changeMagnificationMode(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (i == 0) {
                    persistMagnificationModeSettingsLocked(i2);
                } else {
                    com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                    if (i2 != currentUserStateLocked.getMagnificationModeLocked(i)) {
                        currentUserStateLocked.setMagnificationModeLocked(i, i2);
                        updateMagnificationModeChangeSettingsLocked(currentUserStateLocked, i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static final class LocalServiceImpl extends com.android.server.AccessibilityManagerInternal {

        @android.annotation.NonNull
        private final com.android.server.accessibility.AccessibilityManagerService mService;

        LocalServiceImpl(@android.annotation.NonNull com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService) {
            this.mService = accessibilityManagerService;
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void setImeSessionEnabled(android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray, boolean z) {
            this.mService.scheduleSetImeSessionEnabled(sparseArray, z);
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void unbindInput() {
            this.mService.scheduleUnbindInput();
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void bindInput() {
            this.mService.scheduleBindInput();
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void createImeSession(android.util.ArraySet<java.lang.Integer> arraySet) {
            this.mService.scheduleCreateImeSession(arraySet);
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void startInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
            this.mService.scheduleStartInput(iRemoteAccessibilityInputConnection, editorInfo, z);
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void performSystemAction(int i) {
            this.mService.getSystemActionPerformer().performSystemAction(i);
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public boolean isTouchExplorationEnabled(int i) {
            boolean isTouchExplorationEnabledLocked;
            synchronized (this.mService.mLock) {
                isTouchExplorationEnabledLocked = this.mService.getUserStateLocked(i).isTouchExplorationEnabledLocked();
            }
            return isTouchExplorationEnabledLocked;
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.accessibility.AccessibilityManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mService = new com.android.server.accessibility.AccessibilityManagerService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            com.android.server.LocalServices.addService(com.android.server.AccessibilityManagerInternal.class, new com.android.server.accessibility.AccessibilityManagerService.LocalServiceImpl(this.mService));
            publishBinderService("accessibility", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            this.mService.onBootPhase(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    AccessibilityManagerService(android.content.Context context, android.os.Handler handler, android.content.pm.PackageManager packageManager, com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy, com.android.server.accessibility.SystemActionPerformer systemActionPerformer, com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager, com.android.server.accessibility.AccessibilityManagerService.AccessibilityDisplayListener accessibilityDisplayListener, com.android.server.accessibility.magnification.MagnificationController magnificationController, @android.annotation.Nullable com.android.server.accessibility.AccessibilityInputFilter accessibilityInputFilter, com.android.server.accessibility.ProxyManager proxyManager) {
        this.mLock = new java.lang.Object();
        this.mStringColonSplitter = new android.text.TextUtils.SimpleStringSplitter(COMPONENT_NAME_SEPARATOR);
        this.mTempRect = new android.graphics.Rect();
        this.mTempRect1 = new android.graphics.Rect();
        this.mTempComponentNameSet = new java.util.HashSet();
        this.mTempIntArray = new android.util.IntArray(0);
        this.mGlobalClients = new android.os.RemoteCallbackList<>();
        this.mUserStates = new android.util.SparseArray<>();
        this.mUiAutomationManager = new com.android.server.accessibility.UiAutomationManager(this.mLock);
        this.mSendWindowStateChangedEventRunnables = new java.util.ArrayList();
        this.mCurrentUserId = 0;
        this.mRealCurrentUserId = -2;
        this.mTempPoint = new android.graphics.Point();
        this.mA11yOverlayLayers = new android.util.SparseArray<>();
        this.mContext = context;
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService("power");
        this.mWindowManagerService = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mTraceManager = com.android.server.accessibility.AccessibilityTraceManager.getInstance(this.mWindowManagerService.getAccessibilityController(), this, this.mLock);
        this.mMainHandler = handler;
        this.mActivityTaskManagerService = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mPackageManager = packageManager;
        this.mSecurityPolicy = accessibilitySecurityPolicy;
        this.mSystemActionPerformer = systemActionPerformer;
        this.mA11yWindowManager = accessibilityWindowManager;
        this.mA11yDisplayListener = accessibilityDisplayListener;
        this.mMagnificationController = magnificationController;
        this.mMagnificationProcessor = new com.android.server.accessibility.magnification.MagnificationProcessor(this.mMagnificationController);
        this.mCaptioningManagerImpl = new com.android.server.accessibility.CaptioningManagerImpl(this.mContext);
        this.mProxyManager = proxyManager;
        if (accessibilityInputFilter != null) {
            this.mInputFilter = accessibilityInputFilter;
            this.mHasInputFilter = true;
        }
        this.mFlashNotificationsController = new com.android.server.accessibility.FlashNotificationsController(this.mContext);
        this.mUmi = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mVisibleBgUserIds = null;
        init();
    }

    public AccessibilityManagerService(android.content.Context context) {
        this.mLock = new java.lang.Object();
        this.mStringColonSplitter = new android.text.TextUtils.SimpleStringSplitter(COMPONENT_NAME_SEPARATOR);
        this.mTempRect = new android.graphics.Rect();
        this.mTempRect1 = new android.graphics.Rect();
        this.mTempComponentNameSet = new java.util.HashSet();
        this.mTempIntArray = new android.util.IntArray(0);
        this.mGlobalClients = new android.os.RemoteCallbackList<>();
        this.mUserStates = new android.util.SparseArray<>();
        this.mUiAutomationManager = new com.android.server.accessibility.UiAutomationManager(this.mLock);
        this.mSendWindowStateChangedEventRunnables = new java.util.ArrayList();
        this.mCurrentUserId = 0;
        this.mRealCurrentUserId = -2;
        this.mTempPoint = new android.graphics.Point();
        this.mA11yOverlayLayers = new android.util.SparseArray<>();
        this.mContext = context;
        this.mPowerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        this.mWindowManagerService = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mTraceManager = com.android.server.accessibility.AccessibilityTraceManager.getInstance(this.mWindowManagerService.getAccessibilityController(), this, this.mLock);
        this.mMainHandler = new com.android.server.accessibility.AccessibilityManagerService.MainHandler(this.mContext.getMainLooper());
        this.mActivityTaskManagerService = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mPackageManager = this.mContext.getPackageManager();
        this.mSecurityPolicy = new com.android.server.accessibility.AccessibilitySecurityPolicy(new com.android.server.accessibility.PolicyWarningUIController(this.mMainHandler, context, new com.android.server.accessibility.PolicyWarningUIController.NotificationController(context)), this.mContext, this, (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class));
        this.mA11yWindowManager = new com.android.server.accessibility.AccessibilityWindowManager(this.mLock, this.mMainHandler, this.mWindowManagerService, this, this.mSecurityPolicy, this, this.mTraceManager);
        this.mA11yDisplayListener = new com.android.server.accessibility.AccessibilityManagerService.AccessibilityDisplayListener(this.mContext, this.mMainHandler);
        this.mMagnificationController = new com.android.server.accessibility.magnification.MagnificationController(this, this.mLock, this.mContext, new com.android.server.accessibility.magnification.MagnificationScaleProvider(this.mContext), java.util.concurrent.Executors.newSingleThreadExecutor());
        this.mMagnificationProcessor = new com.android.server.accessibility.magnification.MagnificationProcessor(this.mMagnificationController);
        this.mCaptioningManagerImpl = new com.android.server.accessibility.CaptioningManagerImpl(this.mContext);
        this.mProxyManager = new com.android.server.accessibility.ProxyManager(this.mLock, this.mA11yWindowManager, this.mContext, this.mMainHandler, this.mUiAutomationManager, this);
        this.mFlashNotificationsController = new com.android.server.accessibility.FlashNotificationsController(this.mContext);
        this.mUmi = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        if (android.os.UserManager.isVisibleBackgroundUsersEnabled()) {
            this.mVisibleBgUserIds = new android.util.SparseBooleanArray();
            this.mUmi.addUserVisibilityListener(new com.android.server.pm.UserManagerInternal.UserVisibilityListener() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda34
                @Override // com.android.server.pm.UserManagerInternal.UserVisibilityListener
                public final void onUserVisibilityChanged(int i, boolean z) {
                    com.android.server.accessibility.AccessibilityManagerService.this.lambda$new$0(i, z);
                }
            });
        } else {
            this.mVisibleBgUserIds = null;
        }
        init();
    }

    private void init() {
        this.mSecurityPolicy.setAccessibilityWindowManager(this.mA11yWindowManager);
        registerBroadcastReceivers();
        new com.android.server.accessibility.AccessibilityManagerService.AccessibilityContentObserver(this.mMainHandler).register(this.mContext.getContentResolver());
        disableAccessibilityMenuToMigrateIfNeeded();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean unsafeIsLockHeld() {
        return java.lang.Thread.holdsLock(this.mLock);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport, com.android.server.accessibility.AccessibilitySecurityPolicy.AccessibilityUserManager
    public int getCurrentUserIdLocked() {
        return this.mCurrentUserId;
    }

    @Override // com.android.server.accessibility.AccessibilitySecurityPolicy.AccessibilityUserManager
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.util.SparseBooleanArray getVisibleUserIdsLocked() {
        return this.mVisibleBgUserIds;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public boolean isAccessibilityButtonShown() {
        return this.mIsAccessibilityButtonShown;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public android.util.Pair<float[], android.view.MagnificationSpec> getWindowTransformationMatrixAndMagnificationSpec(int i) {
        android.view.WindowInfo findWindowInfoByIdLocked;
        android.os.IBinder windowTokenForUserAndWindowIdLocked;
        synchronized (this.mLock) {
            findWindowInfoByIdLocked = this.mA11yWindowManager.findWindowInfoByIdLocked(i);
        }
        if (findWindowInfoByIdLocked != null) {
            android.view.MagnificationSpec magnificationSpec = new android.view.MagnificationSpec();
            magnificationSpec.setTo(findWindowInfoByIdLocked.mMagnificationSpec);
            return new android.util.Pair<>(findWindowInfoByIdLocked.mTransformMatrix, magnificationSpec);
        }
        synchronized (this.mLock) {
            windowTokenForUserAndWindowIdLocked = this.mA11yWindowManager.getWindowTokenForUserAndWindowIdLocked(this.mCurrentUserId, i);
        }
        android.util.Pair<android.graphics.Matrix, android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = this.mWindowManagerService.getWindowTransformationMatrixAndMagnificationSpec(windowTokenForUserAndWindowIdLocked);
        float[] fArr = new float[9];
        android.graphics.Matrix matrix = (android.graphics.Matrix) windowTransformationMatrixAndMagnificationSpec.first;
        android.view.MagnificationSpec magnificationSpec2 = (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
        if (!magnificationSpec2.isNop()) {
            matrix.postScale(magnificationSpec2.scale, magnificationSpec2.scale);
            matrix.postTranslate(magnificationSpec2.offsetX, magnificationSpec2.offsetY);
        }
        matrix.getValues(fArr);
        return new android.util.Pair<>(fArr, (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second);
    }

    public android.view.accessibility.IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int i) {
        android.view.accessibility.IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = new android.view.accessibility.IAccessibilityManager.WindowTransformationSpec();
        android.util.Pair<float[], android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = getWindowTransformationMatrixAndMagnificationSpec(i);
        windowTransformationSpec.transformationMatrix = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
        windowTransformationSpec.magnificationSpec = (android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
        return windowTransformationSpec;
    }

    @Override // com.android.server.accessibility.AccessibilityUserState.ServiceInfoChangeListener
    public void onServiceInfoChangedLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        this.mSecurityPolicy.onBoundServicesChangedLocked(accessibilityUserState.mUserId, accessibilityUserState.mBoundServices);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    @android.annotation.Nullable
    public com.android.server.accessibility.FingerprintGestureDispatcher getFingerprintGestureDispatcher() {
        return this.mFingerprintGestureDispatcher;
    }

    public void onInputFilterInstalled(boolean z) {
        synchronized (this.mLock) {
            this.mInputFilterInstalled = z;
            this.mLock.notifyAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBootPhase(int i) {
        if (i == 500 && this.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
            this.mSecurityPolicy.setAppWidgetManager((android.appwidget.AppWidgetManagerInternal) com.android.server.LocalServices.getService(android.appwidget.AppWidgetManagerInternal.class));
        }
        if (i == 600) {
            setNonA11yToolNotificationToMatchSafetyCenter();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNonA11yToolNotificationToMatchSafetyCenter() {
        boolean z = !((android.safetycenter.SafetyCenterManager) this.mContext.getSystemService(android.safetycenter.SafetyCenterManager.class)).isSafetyCenterEnabled();
        synchronized (this.mLock) {
            this.mSecurityPolicy.setSendingNonA11yToolNotificationLocked(z);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.Object getLock() {
        return this.mLock;
    }

    com.android.server.accessibility.AccessibilityUserState getCurrentUserState() {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked;
        synchronized (this.mLock) {
            currentUserStateLocked = getCurrentUserStateLocked();
        }
        return currentUserStateLocked;
    }

    private com.android.server.accessibility.AccessibilityUserState getUserState(int i) {
        com.android.server.accessibility.AccessibilityUserState userStateLocked;
        synchronized (this.mLock) {
            userStateLocked = getUserStateLocked(i);
        }
        return userStateLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public com.android.server.accessibility.AccessibilityUserState getUserStateLocked(int i) {
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState = this.mUserStates.get(i);
        if (accessibilityUserState == null) {
            com.android.server.accessibility.AccessibilityUserState accessibilityUserState2 = new com.android.server.accessibility.AccessibilityUserState(i, this.mContext, this);
            this.mUserStates.put(i, accessibilityUserState2);
            return accessibilityUserState2;
        }
        return accessibilityUserState;
    }

    boolean getBindInstantServiceAllowed(int i) {
        boolean bindInstantServiceAllowedLocked;
        synchronized (this.mLock) {
            bindInstantServiceAllowedLocked = getUserStateLocked(i).getBindInstantServiceAllowedLocked();
        }
        return bindInstantServiceAllowedLocked;
    }

    void setBindInstantServiceAllowed(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIND_INSTANT_SERVICE", "setBindInstantServiceAllowed");
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(i);
                if (z != userStateLocked.getBindInstantServiceAllowedLocked()) {
                    userStateLocked.setBindInstantServiceAllowedLocked(z);
                    onUserStateChangedLocked(userStateLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSomePackagesChangedLocked() {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
        currentUserStateLocked.mInstalledServices.clear();
        if (readConfigurationForUserStateLocked(currentUserStateLocked)) {
            onUserStateChangedLocked(currentUserStateLocked);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSomePackagesChangedLocked(@android.annotation.Nullable java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list, @android.annotation.Nullable java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> list2) {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
        currentUserStateLocked.mInstalledServices.clear();
        if (readConfigurationForUserStateLocked(currentUserStateLocked, list, list2)) {
            onUserStateChangedLocked(currentUserStateLocked);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageRemovedLocked(final java.lang.String str) {
        com.android.server.accessibility.AccessibilityUserState currentUserState = getCurrentUserState();
        java.util.function.Predicate<? super android.content.ComponentName> predicate = new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onPackageRemovedLocked$1;
                lambda$onPackageRemovedLocked$1 = com.android.server.accessibility.AccessibilityManagerService.lambda$onPackageRemovedLocked$1(str, (android.content.ComponentName) obj);
                return lambda$onPackageRemovedLocked$1;
            }
        };
        currentUserState.mBindingServices.removeIf(predicate);
        currentUserState.mCrashedServices.removeIf(predicate);
        java.util.Iterator<android.content.ComponentName> it = currentUserState.mEnabledServices.iterator();
        boolean z = false;
        while (it.hasNext()) {
            android.content.ComponentName next = it.next();
            if (next.getPackageName().equals(str)) {
                it.remove();
                currentUserState.mTouchExplorationGrantedServices.remove(next);
                z = true;
            }
        }
        if (z) {
            persistComponentNamesToSettingLocked("enabled_accessibility_services", currentUserState.mEnabledServices, this.mCurrentUserId);
            persistComponentNamesToSettingLocked("touch_exploration_granted_accessibility_services", currentUserState.mTouchExplorationGrantedServices, this.mCurrentUserId);
            onUserStateChangedLocked(currentUserState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackageRemovedLocked$1(java.lang.String str, android.content.ComponentName componentName) {
        return componentName != null && componentName.getPackageName().equals(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean onPackagesForceStoppedLocked(java.lang.String[] strArr, com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        final java.util.List list = accessibilityUserState.mInstalledServices.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda39
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onPackagesForceStoppedLocked$2;
                lambda$onPackagesForceStoppedLocked$2 = com.android.server.accessibility.AccessibilityManagerService.lambda$onPackagesForceStoppedLocked$2((android.accessibilityservice.AccessibilityServiceInfo) obj);
                return lambda$onPackagesForceStoppedLocked$2;
            }
        }).map(new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda40
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$onPackagesForceStoppedLocked$3;
                lambda$onPackagesForceStoppedLocked$3 = com.android.server.accessibility.AccessibilityManagerService.lambda$onPackagesForceStoppedLocked$3((android.accessibilityservice.AccessibilityServiceInfo) obj);
                return lambda$onPackagesForceStoppedLocked$3;
            }
        }).toList();
        java.util.Iterator<android.content.ComponentName> it = accessibilityUserState.mEnabledServices.iterator();
        boolean z = false;
        while (it.hasNext()) {
            android.content.ComponentName next = it.next();
            java.lang.String packageName = next.getPackageName();
            for (java.lang.String str : strArr) {
                if (packageName.equals(str)) {
                    it.remove();
                    accessibilityUserState.getBindingServicesLocked().remove(next);
                    accessibilityUserState.getCrashedServicesLocked().remove(next);
                    z = true;
                }
            }
        }
        if (z) {
            persistComponentNamesToSettingLocked("enabled_accessibility_services", accessibilityUserState.mEnabledServices, accessibilityUserState.mUserId);
        }
        boolean removeIf = accessibilityUserState.mAccessibilityButtonTargets.removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onPackagesForceStoppedLocked$5;
                lambda$onPackagesForceStoppedLocked$5 = com.android.server.accessibility.AccessibilityManagerService.lambda$onPackagesForceStoppedLocked$5(list, (java.lang.String) obj);
                return lambda$onPackagesForceStoppedLocked$5;
            }
        });
        if (removeIf) {
            persistColonDelimitedSetToSettingLocked("accessibility_button_targets", accessibilityUserState.mUserId, accessibilityUserState.mAccessibilityButtonTargets, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda42
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$onPackagesForceStoppedLocked$6;
                    lambda$onPackagesForceStoppedLocked$6 = com.android.server.accessibility.AccessibilityManagerService.lambda$onPackagesForceStoppedLocked$6((java.lang.String) obj);
                    return lambda$onPackagesForceStoppedLocked$6;
                }
            });
        }
        return z || removeIf;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackagesForceStoppedLocked$2(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        return (accessibilityServiceInfo.flags & 256) == 256;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$onPackagesForceStoppedLocked$3(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        return accessibilityServiceInfo.getComponentName().flattenToString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackagesForceStoppedLocked$5(java.util.List list, final java.lang.String str) {
        return list.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda58
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean equals;
                equals = java.util.Objects.equals(str, (java.lang.String) obj);
                return equals;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$onPackagesForceStoppedLocked$6(java.lang.String str) {
        return str;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.internal.content.PackageMonitor getPackageMonitor() {
        return this.mPackageMonitor;
    }

    /* renamed from: com.android.server.accessibility.AccessibilityManagerService$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.content.PackageMonitor {
        AnonymousClass1() {
        }

        public void onSomePackagesChanged() {
            if (com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.logTrace("AccessibilityManagerService.PM.onSomePackagesChanged", 32768L);
            }
            int changingUserId = getChangingUserId();
            com.android.server.accessibility.Flags.scanPackagesWithoutLock();
            java.util.List parseAccessibilityServiceInfos = com.android.server.accessibility.AccessibilityManagerService.this.parseAccessibilityServiceInfos(changingUserId);
            java.util.List parseAccessibilityShortcutInfos = com.android.server.accessibility.AccessibilityManagerService.this.parseAccessibilityShortcutInfos(changingUserId);
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    if (changingUserId != com.android.server.accessibility.AccessibilityManagerService.this.mCurrentUserId) {
                        return;
                    }
                    com.android.server.accessibility.Flags.scanPackagesWithoutLock();
                    com.android.server.accessibility.AccessibilityManagerService.this.onSomePackagesChangedLocked(parseAccessibilityServiceInfos, parseAccessibilityShortcutInfos);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onPackageUpdateFinished(final java.lang.String str, int i) {
            boolean z;
            if (com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.logTrace("AccessibilityManagerService.PM.onPackageUpdateFinished", 32768L, "packageName=" + str + ";uid=" + i);
            }
            int changingUserId = getChangingUserId();
            com.android.server.accessibility.Flags.scanPackagesWithoutLock();
            java.util.List parseAccessibilityServiceInfos = com.android.server.accessibility.AccessibilityManagerService.this.parseAccessibilityServiceInfos(changingUserId);
            java.util.List parseAccessibilityShortcutInfos = com.android.server.accessibility.AccessibilityManagerService.this.parseAccessibilityShortcutInfos(changingUserId);
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    if (changingUserId != com.android.server.accessibility.AccessibilityManagerService.this.mCurrentUserId) {
                        return;
                    }
                    com.android.server.accessibility.AccessibilityUserState userStateLocked = com.android.server.accessibility.AccessibilityManagerService.this.getUserStateLocked(changingUserId);
                    if (!userStateLocked.getBindingServicesLocked().removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$onPackageUpdateFinished$0;
                            lambda$onPackageUpdateFinished$0 = com.android.server.accessibility.AccessibilityManagerService.AnonymousClass1.lambda$onPackageUpdateFinished$0(str, (android.content.ComponentName) obj);
                            return lambda$onPackageUpdateFinished$0;
                        }
                    }) && !userStateLocked.mCrashedServices.removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$onPackageUpdateFinished$1;
                            lambda$onPackageUpdateFinished$1 = com.android.server.accessibility.AccessibilityManagerService.AnonymousClass1.lambda$onPackageUpdateFinished$1(str, (android.content.ComponentName) obj);
                            return lambda$onPackageUpdateFinished$1;
                        }
                    })) {
                        z = false;
                        userStateLocked.mInstalledServices.clear();
                        com.android.server.accessibility.Flags.scanPackagesWithoutLock();
                        boolean readConfigurationForUserStateLocked = com.android.server.accessibility.AccessibilityManagerService.this.readConfigurationForUserStateLocked(userStateLocked, parseAccessibilityServiceInfos, parseAccessibilityShortcutInfos);
                        if (!z || readConfigurationForUserStateLocked) {
                            com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked);
                        }
                        com.android.server.accessibility.AccessibilityManagerService.this.migrateAccessibilityButtonSettingsIfNecessaryLocked(userStateLocked, str, 0);
                    }
                    z = true;
                    userStateLocked.mInstalledServices.clear();
                    com.android.server.accessibility.Flags.scanPackagesWithoutLock();
                    boolean readConfigurationForUserStateLocked2 = com.android.server.accessibility.AccessibilityManagerService.this.readConfigurationForUserStateLocked(userStateLocked, parseAccessibilityServiceInfos, parseAccessibilityShortcutInfos);
                    if (!z) {
                    }
                    com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked);
                    com.android.server.accessibility.AccessibilityManagerService.this.migrateAccessibilityButtonSettingsIfNecessaryLocked(userStateLocked, str, 0);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$onPackageUpdateFinished$0(java.lang.String str, android.content.ComponentName componentName) {
            return componentName != null && componentName.getPackageName().equals(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$onPackageUpdateFinished$1(java.lang.String str, android.content.ComponentName componentName) {
            return componentName != null && componentName.getPackageName().equals(str);
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            if (com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.logTrace("AccessibilityManagerService.PM.onPackageRemoved", 32768L, "packageName=" + str + ";uid=" + i);
            }
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    if (getChangingUserId() != com.android.server.accessibility.AccessibilityManagerService.this.mCurrentUserId) {
                        return;
                    }
                    com.android.server.accessibility.AccessibilityManagerService.this.onPackageRemovedLocked(str);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
            if (com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.logTrace("AccessibilityManagerService.PM.onHandleForceStop", 32768L, "intent=" + intent + ";packages=" + java.util.Arrays.toString(strArr) + ";uid=" + i + ";doit=" + z);
            }
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    int changingUserId = getChangingUserId();
                    if (changingUserId != com.android.server.accessibility.AccessibilityManagerService.this.mCurrentUserId) {
                        return false;
                    }
                    com.android.server.accessibility.AccessibilityUserState userStateLocked = com.android.server.accessibility.AccessibilityManagerService.this.getUserStateLocked(changingUserId);
                    com.android.server.accessibility.Flags.disableContinuousShortcutOnForceStop();
                    java.util.Iterator<android.content.ComponentName> it = userStateLocked.mEnabledServices.iterator();
                    while (it.hasNext()) {
                        android.content.ComponentName next = it.next();
                        java.lang.String packageName = next.getPackageName();
                        for (java.lang.String str : strArr) {
                            if (packageName.equals(str)) {
                                if (!z) {
                                    return true;
                                }
                                it.remove();
                                userStateLocked.getBindingServicesLocked().remove(next);
                                userStateLocked.getCrashedServicesLocked().remove(next);
                                com.android.server.accessibility.AccessibilityManagerService.this.persistComponentNamesToSettingLocked("enabled_accessibility_services", userStateLocked.mEnabledServices, changingUserId);
                                com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked);
                            }
                        }
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void registerBroadcastReceivers() {
        this.mPackageMonitor = new com.android.server.accessibility.AccessibilityManagerService.AnonymousClass1();
        this.mPackageMonitor.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.ALL, true);
        com.android.server.accessibility.Flags.deprecatePackageListObserver();
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (packageManagerInternal != null) {
            packageManagerInternal.getPackageList(new android.content.pm.PackageManagerInternal.PackageListObserver() { // from class: com.android.server.accessibility.AccessibilityManagerService.2
                @Override // android.content.pm.PackageManagerInternal.PackageListObserver
                public void onPackageAdded(java.lang.String str, int i) {
                    int userId = android.os.UserHandle.getUserId(i);
                    synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                        try {
                            if (userId == com.android.server.accessibility.AccessibilityManagerService.this.mCurrentUserId) {
                                com.android.server.accessibility.AccessibilityManagerService.this.onSomePackagesChangedLocked();
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.content.pm.PackageManagerInternal.PackageListObserver
                public void onPackageRemoved(java.lang.String str, int i) {
                    int userId = android.os.UserHandle.getUserId(i);
                    synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                        try {
                            if (userId == com.android.server.accessibility.AccessibilityManagerService.this.mCurrentUserId) {
                                com.android.server.accessibility.AccessibilityManagerService.this.onPackageRemovedLocked(str);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.os.action.SETTING_RESTORED");
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.accessibility.AccessibilityManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.isA11yTracingEnabledForTypes(65536L)) {
                    com.android.server.accessibility.AccessibilityManagerService.this.mTraceManager.logTrace("AccessibilityManagerService.BR.onReceive", 65536L, "context=" + context + ";intent=" + intent);
                }
                java.lang.String action = intent.getAction();
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    com.android.server.accessibility.AccessibilityManagerService.this.switchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                    com.android.server.accessibility.AccessibilityManagerService.this.unlockUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    com.android.server.accessibility.AccessibilityManagerService.this.removeUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.os.action.SETTING_RESTORED".equals(action)) {
                    java.lang.String stringExtra = intent.getStringExtra("setting_name");
                    if ("enabled_accessibility_services".equals(stringExtra)) {
                        synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                            com.android.server.accessibility.AccessibilityManagerService.this.restoreEnabledAccessibilityServicesLocked(intent.getStringExtra("previous_value"), intent.getStringExtra("new_value"), intent.getIntExtra("restored_from_sdk_int", 0));
                        }
                    } else if ("accessibility_display_magnification_navbar_enabled".equals(stringExtra)) {
                        synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                            com.android.server.accessibility.AccessibilityManagerService.this.restoreLegacyDisplayMagnificationNavBarIfNeededLocked(intent.getStringExtra("new_value"), intent.getIntExtra("restored_from_sdk_int", 0));
                        }
                    } else if ("accessibility_button_targets".equals(stringExtra)) {
                        synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                            com.android.server.accessibility.AccessibilityManagerService.this.restoreAccessibilityButtonTargetsLocked(intent.getStringExtra("previous_value"), intent.getStringExtra("new_value"));
                        }
                    }
                }
            }
        }, android.os.UserHandle.ALL, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED");
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.accessibility.AccessibilityManagerService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.accessibility.AccessibilityManagerService.this.setNonA11yToolNotificationToMatchSafetyCenter();
            }
        }, android.os.UserHandle.ALL, intentFilter2, null, this.mMainHandler, 2);
        if (!android.companion.virtual.flags.Flags.vdmPublicApis()) {
            this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.accessibility.AccessibilityManagerService.5
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    com.android.server.accessibility.AccessibilityManagerService.this.mProxyManager.clearConnections(intent.getIntExtra("android.companion.virtual.extra.VIRTUAL_DEVICE_ID", 0));
                }
            }, new android.content.IntentFilter("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED"), 4);
        }
    }

    private void disableAccessibilityMenuToMigrateIfNeeded() {
        int i;
        synchronized (this.mLock) {
            i = this.mCurrentUserId;
        }
        android.content.ComponentName accessibilityMenuComponentToMigrate = com.android.internal.accessibility.util.AccessibilityUtils.getAccessibilityMenuComponentToMigrate(this.mPackageManager, i);
        if (accessibilityMenuComponentToMigrate != null) {
            this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().setComponentEnabledSetting(accessibilityMenuComponentToMigrate, 2, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreLegacyDisplayMagnificationNavBarIfNeededLocked(java.lang.String str, int i) {
        if (i >= 30) {
            return;
        }
        try {
            boolean z = java.lang.Integer.parseInt(str) == 1;
            com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(0);
            android.util.ArraySet arraySet = new android.util.ArraySet();
            readColonDelimitedSettingToSet("accessibility_button_targets", userStateLocked.mUserId, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$7;
                    lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$7 = com.android.server.accessibility.AccessibilityManagerService.lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$7((java.lang.String) obj);
                    return lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$7;
                }
            }, arraySet);
            if (arraySet.contains("com.android.server.accessibility.MagnificationController") == z) {
                return;
            }
            if (z) {
                arraySet.add("com.android.server.accessibility.MagnificationController");
            } else {
                arraySet.remove("com.android.server.accessibility.MagnificationController");
            }
            persistColonDelimitedSetToSettingLocked("accessibility_button_targets", userStateLocked.mUserId, arraySet, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$8;
                    lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$8 = com.android.server.accessibility.AccessibilityManagerService.lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$8((java.lang.String) obj);
                    return lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$8;
                }
            });
            readAccessibilityButtonTargetsLocked(userStateLocked);
            onUserStateChangedLocked(userStateLocked);
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.w(LOG_TAG, "number format is incorrect" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$7(java.lang.String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$restoreLegacyDisplayMagnificationNavBarIfNeededLocked$8(java.lang.String str) {
        return str;
    }

    public long addClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.addClient", 4L, "callback=" + iAccessibilityManagerClient + ";userId=" + i);
        }
        synchronized (this.mLock) {
            try {
                int resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(resolveCallingUserIdEnforcingPermissionsLocked);
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(android.os.Binder.getCallingUid());
                com.android.server.accessibility.AccessibilityManagerService.Client client = new com.android.server.accessibility.AccessibilityManagerService.Client(iAccessibilityManagerClient, android.os.Binder.getCallingUid(), userStateLocked, firstDeviceIdForUidLocked);
                if (this.mSecurityPolicy.isCallerInteractingAcrossUsers(i)) {
                    if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                        return com.android.internal.util.IntPair.of(this.mProxyManager.getStateLocked(firstDeviceIdForUidLocked), client.mLastSentRelevantEventTypes);
                    }
                    this.mGlobalClients.register(iAccessibilityManagerClient, client);
                } else {
                    if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                        return com.android.internal.util.IntPair.of(this.mProxyManager.getStateLocked(firstDeviceIdForUidLocked), client.mLastSentRelevantEventTypes);
                    }
                    userStateLocked.mUserClients.register(iAccessibilityManagerClient, client);
                }
                return com.android.internal.util.IntPair.of(resolveCallingUserIdEnforcingPermissionsLocked == this.mCurrentUserId ? getClientStateLocked(userStateLocked) : 0, client.mLastSentRelevantEventTypes);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean removeClient(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i));
                if (this.mSecurityPolicy.isCallerInteractingAcrossUsers(i)) {
                    return this.mGlobalClients.unregister(iAccessibilityManagerClient);
                }
                return userStateLocked.mUserClients.unregister(iAccessibilityManagerClient);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, int i) {
        int resolveCallingUserIdEnforcingPermissionsLocked;
        boolean z;
        boolean z2;
        android.view.accessibility.AccessibilityWindowInfo pictureInPictureWindowLocked;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.sendAccessibilityEvent", 4L, "event=" + accessibilityEvent + ";userId=" + i);
        }
        synchronized (this.mLock) {
            try {
                if (accessibilityEvent.getWindowId() == -3 && (pictureInPictureWindowLocked = this.mA11yWindowManager.getPictureInPictureWindowLocked()) != null) {
                    accessibilityEvent.setWindowId(pictureInPictureWindowLocked.getId());
                }
                resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                accessibilityEvent.setPackageName(this.mSecurityPolicy.resolveValidReportedPackageLocked(accessibilityEvent.getPackageName(), android.os.UserHandle.getCallingAppId(), resolveCallingUserIdEnforcingPermissionsLocked, android.view.accessibility.IAccessibilityManager.Stub.getCallingPid()));
                z = true;
                if (resolveCallingUserIdEnforcingPermissionsLocked != this.mCurrentUserId) {
                    z2 = false;
                } else {
                    if (!this.mSecurityPolicy.canDispatchAccessibilityEventLocked(this.mCurrentUserId, accessibilityEvent)) {
                        z2 = false;
                    } else {
                        this.mA11yWindowManager.updateActiveAndAccessibilityFocusedWindowLocked(this.mCurrentUserId, accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId(), accessibilityEvent.getEventType(), accessibilityEvent.getAction());
                        this.mSecurityPolicy.updateEventSourceLocked(accessibilityEvent);
                        z2 = true;
                    }
                    if (this.mHasInputFilter && this.mInputFilter != null) {
                        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda4
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                ((com.android.server.accessibility.AccessibilityManagerService) obj).sendAccessibilityEventToInputFilter((android.view.accessibility.AccessibilityEvent) obj2);
                            }
                        }, this, android.view.accessibility.AccessibilityEvent.obtain(accessibilityEvent)));
                    }
                }
            } finally {
            }
        }
        if (z2) {
            int displayId = accessibilityEvent.getDisplayId();
            synchronized (this.mLock) {
                try {
                    int windowId = accessibilityEvent.getWindowId();
                    if (windowId != -1 && displayId == -1) {
                        displayId = this.mA11yWindowManager.getDisplayIdByUserIdAndWindowIdLocked(resolveCallingUserIdEnforcingPermissionsLocked, windowId);
                        accessibilityEvent.setDisplayId(displayId);
                    }
                    if (accessibilityEvent.getEventType() != 32 || displayId == -1 || !this.mA11yWindowManager.isTrackingWindowsLocked(displayId)) {
                        z = false;
                    }
                } finally {
                }
            }
            if (z) {
                if (this.mTraceManager.isA11yTracingEnabledForTypes(512L)) {
                    this.mTraceManager.logTrace("WindowManagerInternal.computeWindowsForAccessibility", 512L, "display=" + displayId);
                }
                ((com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class)).computeWindowsForAccessibility(displayId);
                if (postponeWindowStateEvent(accessibilityEvent)) {
                    return;
                }
            }
            synchronized (this.mLock) {
                dispatchAccessibilityEventLocked(accessibilityEvent);
            }
        }
        if (OWN_PROCESS_ID != android.os.Binder.getCallingPid()) {
            accessibilityEvent.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchAccessibilityEventLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mProxyManager.isProxyedDisplay(accessibilityEvent.getDisplayId())) {
            this.mProxyManager.sendAccessibilityEventLocked(accessibilityEvent);
        } else {
            notifyAccessibilityServicesDelayedLocked(accessibilityEvent, false);
            notifyAccessibilityServicesDelayedLocked(accessibilityEvent, true);
        }
        this.mUiAutomationManager.sendAccessibilityEventLocked(accessibilityEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAccessibilityEventToInputFilter(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.notifyAccessibilityEvent(accessibilityEvent);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        accessibilityEvent.recycle();
    }

    public void registerSystemAction(android.app.RemoteAction remoteAction, int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.registerSystemAction", 4L, "action=" + remoteAction + ";actionId=" + i);
        }
        this.mSecurityPolicy.enforceCallingOrSelfPermission("android.permission.MANAGE_ACCESSIBILITY");
        getSystemActionPerformer().registerSystemAction(i, remoteAction);
    }

    public void unregisterSystemAction(int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.unregisterSystemAction", 4L, "actionId=" + i);
        }
        this.mSecurityPolicy.enforceCallingOrSelfPermission("android.permission.MANAGE_ACCESSIBILITY");
        getSystemActionPerformer().unregisterSystemAction(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.accessibility.SystemActionPerformer getSystemActionPerformer() {
        if (this.mSystemActionPerformer == null) {
            this.mSystemActionPerformer = new com.android.server.accessibility.SystemActionPerformer(this.mContext, this.mWindowManagerService, null, this, this);
        }
        return this.mSystemActionPerformer;
    }

    public android.content.pm.ParceledListSlice<android.accessibilityservice.AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getInstalledAccessibilityServiceList", 4L, "userId=" + i);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(android.os.Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return new android.content.pm.ParceledListSlice<>(this.mProxyManager.getInstalledAndEnabledServiceInfosLocked(-1, firstDeviceIdForUidLocked));
                }
                int resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                java.util.ArrayList arrayList = new java.util.ArrayList(getUserStateLocked(resolveCallingUserIdEnforcingPermissionsLocked).mInstalledServices);
                if (android.os.Binder.getCallingPid() == OWN_PROCESS_ID) {
                    return new android.content.pm.ParceledListSlice<>(arrayList);
                }
                android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                int callingUid = android.os.Binder.getCallingUid();
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    if (packageManagerInternal.filterAppAccess(((android.accessibilityservice.AccessibilityServiceInfo) arrayList.get(size)).getComponentName().getPackageName(), callingUid, resolveCallingUserIdEnforcingPermissionsLocked)) {
                        arrayList.remove(size);
                    }
                }
                return new android.content.pm.ParceledListSlice<>(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getEnabledAccessibilityServiceList", 4L, "feedbackType=" + i + ";userId=" + i2);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(android.os.Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return this.mProxyManager.getInstalledAndEnabledServiceInfosLocked(i, firstDeviceIdForUidLocked);
                }
                com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i2));
                if (this.mUiAutomationManager.suppressingAccessibilityServicesLocked()) {
                    return java.util.Collections.emptyList();
                }
                java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = userStateLocked.mBoundServices;
                int size = arrayList.size();
                java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = arrayList.get(i3);
                    if ((accessibilityServiceConnection.mFeedbackType & i) != 0 || i == -1) {
                        arrayList2.add(accessibilityServiceConnection.getServiceInfo());
                    }
                }
                return arrayList2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void interrupt(int i) {
        java.util.ArrayList arrayList;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.interrupt", 4L, "userId=" + i);
        }
        synchronized (this.mLock) {
            try {
                int resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                if (resolveCallingUserIdEnforcingPermissionsLocked != this.mCurrentUserId) {
                    return;
                }
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(android.os.Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    arrayList = new java.util.ArrayList();
                    this.mProxyManager.addServiceInterfacesLocked(arrayList, firstDeviceIdForUidLocked);
                } else {
                    java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList2 = getUserStateLocked(resolveCallingUserIdEnforcingPermissionsLocked).mBoundServices;
                    java.util.ArrayList arrayList3 = new java.util.ArrayList(arrayList2.size());
                    for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                        com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = arrayList2.get(i2);
                        android.os.IBinder iBinder = accessibilityServiceConnection.mService;
                        android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient = accessibilityServiceConnection.mServiceInterface;
                        if (iBinder != null && iAccessibilityServiceClient != null) {
                            arrayList3.add(iAccessibilityServiceClient);
                        }
                    }
                    arrayList = arrayList3;
                }
                int size = arrayList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    try {
                        if (this.mTraceManager.isA11yTracingEnabledForTypes(2L)) {
                            this.mTraceManager.logTrace("AccessibilityManagerService.IAccessibilityServiceClient.onInterrupt", 2L);
                        }
                        ((android.accessibilityservice.IAccessibilityServiceClient) arrayList.get(i3)).onInterrupt();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(LOG_TAG, "Error sending interrupt request to " + arrayList.get(i3), e);
                    }
                }
            } finally {
            }
        }
    }

    public int addAccessibilityInteractionConnection(android.view.IWindow iWindow, android.os.IBinder iBinder, android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection, java.lang.String str, int i) throws android.os.RemoteException {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.addAccessibilityInteractionConnection", 4L, "windowToken=" + iWindow + "leashToken=" + iBinder + ";connection=" + iAccessibilityInteractionConnection + "; packageName=" + str + ";userId=" + i);
        }
        return this.mA11yWindowManager.addAccessibilityInteractionConnection(iWindow, iBinder, iAccessibilityInteractionConnection, str, i);
    }

    public void removeAccessibilityInteractionConnection(android.view.IWindow iWindow) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.removeAccessibilityInteractionConnection", 4L, "window=" + iWindow);
        }
        this.mA11yWindowManager.removeAccessibilityInteractionConnection(iWindow);
    }

    public void setPictureInPictureActionReplacingConnection(android.view.accessibility.IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws android.os.RemoteException {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.setPictureInPictureActionReplacingConnection", 4L, "connection=" + iAccessibilityInteractionConnection);
        }
        this.mSecurityPolicy.enforceCallingPermission("android.permission.MODIFY_ACCESSIBILITY_DATA", SET_PIP_ACTION_REPLACEMENT);
        this.mA11yWindowManager.setPictureInPictureActionReplacingConnection(iAccessibilityInteractionConnection);
    }

    public void registerUiTestAutomationService(android.os.IBinder iBinder, android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.registerUiTestAutomationService", 4L, "owner=" + iBinder + ";serviceClient=" + iAccessibilityServiceClient + ";accessibilityServiceInfo=" + accessibilityServiceInfo + ";flags=" + i2);
        }
        this.mSecurityPolicy.enforceCallingPermission("android.permission.RETRIEVE_WINDOW_CONTENT", FUNCTION_REGISTER_UI_TEST_AUTOMATION_SERVICE);
        synchronized (this.mLock) {
            changeCurrentUserForTestAutomationIfNeededLocked(i);
            com.android.server.accessibility.UiAutomationManager uiAutomationManager = this.mUiAutomationManager;
            android.content.Context context = this.mContext;
            int i3 = sIdCounter;
            sIdCounter = i3 + 1;
            uiAutomationManager.registerUiTestAutomationServiceLocked(iBinder, iAccessibilityServiceClient, context, accessibilityServiceInfo, i3, this.mMainHandler, this.mSecurityPolicy, this, getTraceManager(), this.mWindowManagerService, getSystemActionPerformer(), this.mA11yWindowManager, i2);
            onUserStateChangedLocked(getCurrentUserStateLocked());
        }
    }

    public void unregisterUiTestAutomationService(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.unregisterUiTestAutomationService", 4L, "serviceClient=" + iAccessibilityServiceClient);
        }
        synchronized (this.mLock) {
            this.mUiAutomationManager.unregisterUiTestAutomationServiceLocked(iAccessibilityServiceClient);
            restoreCurrentUserAfterTestAutomationIfNeededLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void changeCurrentUserForTestAutomationIfNeededLocked(int i) {
        if (this.mVisibleBgUserIds == null) {
            com.android.server.utils.Slogf.d(LOG_TAG, "changeCurrentUserForTestAutomationIfNeededLocked(%d): ignoring because device doesn't support visible background users", java.lang.Integer.valueOf(i));
            return;
        }
        if (!this.mVisibleBgUserIds.get(i)) {
            com.android.server.utils.Slogf.wtf(LOG_TAG, "changeCurrentUserForTestAutomationIfNeededLocked(): cannot change current user to %d as it's not visible (mVisibleUsers=%s)", java.lang.Integer.valueOf(i), this.mVisibleBgUserIds);
        } else {
            if (this.mCurrentUserId == i) {
                com.android.server.utils.Slogf.d(LOG_TAG, "changeCurrentUserForTestAutomationIfNeededLocked(): NOT changing current user for test automation purposes as it is already %d", java.lang.Integer.valueOf(this.mCurrentUserId));
                return;
            }
            com.android.server.utils.Slogf.i(LOG_TAG, "changeCurrentUserForTestAutomationIfNeededLocked(): changing current user from %d to %d for test automation purposes", java.lang.Integer.valueOf(this.mCurrentUserId), java.lang.Integer.valueOf(i));
            this.mRealCurrentUserId = this.mCurrentUserId;
            switchUser(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void restoreCurrentUserAfterTestAutomationIfNeededLocked() {
        if (this.mVisibleBgUserIds == null) {
            com.android.server.utils.Slogf.d(LOG_TAG, "restoreCurrentUserForTestAutomationIfNeededLocked(): ignoring because device doesn't support visible background users");
            return;
        }
        if (this.mRealCurrentUserId == -2) {
            com.android.server.utils.Slogf.d(LOG_TAG, "restoreCurrentUserForTestAutomationIfNeededLocked(): ignoring because mRealCurrentUserId is already USER_CURRENT");
            return;
        }
        com.android.server.utils.Slogf.i(LOG_TAG, "restoreCurrentUserForTestAutomationIfNeededLocked(): restoring current user to %d after using %d for test automation purposes", java.lang.Integer.valueOf(this.mRealCurrentUserId), java.lang.Integer.valueOf(this.mCurrentUserId));
        int i = this.mRealCurrentUserId;
        this.mRealCurrentUserId = -2;
        switchUser(i);
    }

    public android.os.IBinder getWindowToken(int i, int i2) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getWindowToken", 4L, "windowId=" + i + ";userId=" + i2);
        }
        this.mSecurityPolicy.enforceCallingPermission("android.permission.RETRIEVE_WINDOW_TOKEN", GET_WINDOW_TOKEN);
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i2) != this.mCurrentUserId) {
                    return null;
                }
                android.view.accessibility.AccessibilityWindowInfo findA11yWindowInfoByIdLocked = this.mA11yWindowManager.findA11yWindowInfoByIdLocked(i);
                if (findA11yWindowInfoByIdLocked == null) {
                    return null;
                }
                return this.mA11yWindowManager.getWindowTokenForUserAndWindowIdLocked(i2, findA11yWindowInfoByIdLocked.getId());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyAccessibilityButtonClicked(int i, java.lang.String str) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.notifyAccessibilityButtonClicked", 4L, "displayId=" + i + ";targetName=" + str);
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE") != 0) {
            throw new java.lang.SecurityException("Caller does not hold permission android.permission.STATUS_BAR_SERVICE");
        }
        if (str == null) {
            synchronized (this.mLock) {
                str = getCurrentUserStateLocked().getTargetAssignedToAccessibilityButton();
            }
        }
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda29(), this, java.lang.Integer.valueOf(i), 0, str));
    }

    public void notifyAccessibilityButtonVisibilityChanged(boolean z) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.notifyAccessibilityButtonVisibilityChanged", 4L, "shown=" + z);
        }
        this.mSecurityPolicy.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE");
        synchronized (this.mLock) {
            notifyAccessibilityButtonVisibilityChangedLocked(z);
        }
    }

    @android.annotation.RequiresPermission("android.permission.STATUS_BAR_SERVICE")
    public void notifyQuickSettingsTilesChanged(int i, java.util.List<android.content.ComponentName> list) {
        this.mSecurityPolicy.enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", "notifyQuickSettingsTilesChanged");
        android.util.Slog.d(LOG_TAG, android.text.TextUtils.formatSimple("notifyQuickSettingsTilesChanged userId: %d, tileComponentNames: %s", new java.lang.Object[]{java.lang.Integer.valueOf(i), list}));
    }

    public boolean onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) {
        boolean notifyGestureLocked;
        synchronized (this.mLock) {
            try {
                notifyGestureLocked = notifyGestureLocked(accessibilityGestureEvent, false);
                if (!notifyGestureLocked) {
                    notifyGestureLocked = notifyGestureLocked(accessibilityGestureEvent, true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return notifyGestureLocked;
    }

    public boolean sendMotionEventToListeningServices(android.view.MotionEvent motionEvent) {
        return scheduleNotifyMotionEvent(android.view.MotionEvent.obtain(motionEvent));
    }

    public boolean onTouchStateChanged(int i, int i2) {
        return scheduleNotifyTouchState(i, i2);
    }

    @Override // com.android.server.accessibility.SystemActionPerformer.SystemActionsChangedListener
    public void onSystemActionsChanged() {
        synchronized (this.mLock) {
            notifySystemActionsChangedLocked(getCurrentUserStateLocked());
        }
    }

    @Override // com.android.server.accessibility.SystemActionPerformer.DisplayUpdateCallBack
    public void moveNonProxyTopFocusedDisplayToTopIfNeeded() {
        this.mA11yWindowManager.moveNonProxyTopFocusedDisplayToTopIfNeeded();
    }

    @Override // com.android.server.accessibility.SystemActionPerformer.DisplayUpdateCallBack
    public int getLastNonProxyTopFocusedDisplayId() {
        return this.mA11yWindowManager.getLastNonProxyTopFocusedDisplayId();
    }

    @com.android.internal.annotations.VisibleForTesting
    void notifySystemActionsChangedLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        for (int size = accessibilityUserState.mBoundServices.size() - 1; size >= 0; size--) {
            accessibilityUserState.mBoundServices.get(size).notifySystemActionsChangedLocked();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean notifyKeyEvent(android.view.KeyEvent keyEvent, int i) {
        synchronized (this.mLock) {
            try {
                java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = getCurrentUserStateLocked().mBoundServices;
                if (arrayList.isEmpty()) {
                    return false;
                }
                return getKeyEventDispatcher().notifyKeyEventLocked(keyEvent, i, arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyMagnificationChanged(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
        synchronized (this.mLock) {
            notifyClearAccessibilityCacheLocked();
            notifyMagnificationChangedLocked(i, region, magnificationConfig);
        }
    }

    void setMotionEventInjectors(android.util.SparseArray<com.android.server.accessibility.MotionEventInjector> sparseArray) {
        synchronized (this.mLock) {
            this.mMotionEventInjectors = sparseArray;
            this.mLock.notifyAll();
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    @android.annotation.Nullable
    public com.android.server.accessibility.MotionEventInjector getMotionEventInjectorForDisplayLocked(int i) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis() + 1000;
        while (this.mMotionEventInjectors == null && android.os.SystemClock.uptimeMillis() < uptimeMillis) {
            try {
                this.mLock.wait(uptimeMillis - android.os.SystemClock.uptimeMillis());
            } catch (java.lang.InterruptedException e) {
            }
        }
        if (this.mMotionEventInjectors == null) {
            android.util.Slog.e(LOG_TAG, "MotionEventInjector installation timed out");
            return null;
        }
        return this.mMotionEventInjectors.get(i);
    }

    public boolean getAccessibilityFocusClickPointInScreen(android.graphics.Point point) {
        return getInteractionBridge().getAccessibilityFocusClickPointInScreenNotLocked(point);
    }

    public boolean performActionOnAccessibilityFocusedItem(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
        return getInteractionBridge().performActionOnAccessibilityFocusedItemNotLocked(accessibilityAction);
    }

    public boolean accessibilityFocusOnlyInActiveWindow() {
        boolean accessibilityFocusOnlyInActiveWindowLocked;
        synchronized (this.mLock) {
            accessibilityFocusOnlyInActiveWindowLocked = this.mA11yWindowManager.accessibilityFocusOnlyInActiveWindowLocked();
        }
        return accessibilityFocusOnlyInActiveWindowLocked;
    }

    boolean getWindowBounds(int i, android.graphics.Rect rect) {
        android.os.IBinder windowToken;
        synchronized (this.mLock) {
            windowToken = getWindowToken(i, this.mCurrentUserId);
        }
        if (this.mTraceManager.isA11yTracingEnabledForTypes(512L)) {
            this.mTraceManager.logTrace("WindowManagerInternal.getWindowFrame", 512L, "token=" + windowToken + ";outBounds=" + rect);
        }
        this.mWindowManagerService.getWindowFrame(windowToken, rect);
        if (!rect.isEmpty()) {
            return true;
        }
        return false;
    }

    public int getActiveWindowId() {
        return this.mA11yWindowManager.getActiveWindowId(this.mCurrentUserId);
    }

    public void onTouchInteractionStart() {
        this.mA11yWindowManager.onTouchInteractionStart();
    }

    public void onTouchInteractionEnd() {
        this.mA11yWindowManager.onTouchInteractionEnd();
    }

    @com.android.internal.annotations.VisibleForTesting
    void switchUser(int i) {
        this.mMagnificationController.updateUserIdIfNeeded(i);
        com.android.server.accessibility.Flags.scanPackagesWithoutLock();
        java.util.List<android.accessibilityservice.AccessibilityServiceInfo> parseAccessibilityServiceInfos = parseAccessibilityServiceInfos(i);
        java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> parseAccessibilityShortcutInfos = parseAccessibilityShortcutInfos(i);
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId == i && this.mInitialized) {
                    return;
                }
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                currentUserStateLocked.onSwitchToAnotherUserLocked();
                if (currentUserStateLocked.mUserClients.getRegisteredCallbackCount() > 0) {
                    this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda46
                        public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                            ((com.android.server.accessibility.AccessibilityManagerService) obj).sendStateToClients(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                        }
                    }, this, 0, java.lang.Integer.valueOf(currentUserStateLocked.mUserId)));
                }
                boolean z = true;
                if (((android.os.UserManager) this.mContext.getSystemService("user")).getUsers().size() <= 1) {
                    z = false;
                }
                this.mCurrentUserId = i;
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked2 = getCurrentUserStateLocked();
                com.android.server.accessibility.Flags.scanPackagesWithoutLock();
                readConfigurationForUserStateLocked(currentUserStateLocked2, parseAccessibilityServiceInfos, parseAccessibilityShortcutInfos);
                this.mSecurityPolicy.onSwitchUserLocked(this.mCurrentUserId, currentUserStateLocked2.mEnabledServices);
                onUserStateChangedLocked(currentUserStateLocked2);
                migrateAccessibilityButtonSettingsIfNecessaryLocked(currentUserStateLocked2, null, 0);
                disableAccessibilityMenuToMigrateIfNeeded();
                if (z) {
                    this.mMainHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda47
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.accessibility.AccessibilityManagerService) obj).announceNewUserIfNeeded();
                        }
                    }, this), com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void announceNewUserIfNeeded() {
        synchronized (this.mLock) {
            try {
                if (getCurrentUserStateLocked().isHandlingAccessibilityEventsLocked()) {
                    java.lang.String string = this.mContext.getString(android.R.string.unsupported_compile_sdk_check_update, ((android.os.UserManager) this.mContext.getSystemService("user")).getUserInfo(this.mCurrentUserId).name);
                    android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(16384);
                    obtain.getText().add(string);
                    sendAccessibilityEventLocked(obtain, this.mCurrentUserId);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unlockUser(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.resolveProfileParentLocked(i) == this.mCurrentUserId) {
                    onUserStateChangedLocked(getUserStateLocked(this.mCurrentUserId));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUser(int i) {
        synchronized (this.mLock) {
            this.mUserStates.remove(i);
        }
        getMagnificationController().onUserRemoved(i);
    }

    void restoreEnabledAccessibilityServicesLocked(java.lang.String str, java.lang.String str2, int i) {
        readComponentNamesFromStringLocked(str, this.mTempComponentNameSet, false);
        readComponentNamesFromStringLocked(str2, this.mTempComponentNameSet, true);
        com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(0);
        userStateLocked.mEnabledServices.clear();
        userStateLocked.mEnabledServices.addAll(this.mTempComponentNameSet);
        persistComponentNamesToSettingLocked("enabled_accessibility_services", userStateLocked.mEnabledServices, 0);
        onUserStateChangedLocked(userStateLocked);
        migrateAccessibilityButtonSettingsIfNecessaryLocked(userStateLocked, null, i);
    }

    void restoreAccessibilityButtonTargetsLocked(java.lang.String str, java.lang.String str2) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        readColonDelimitedStringToSet(str, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda53
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$restoreAccessibilityButtonTargetsLocked$9;
                lambda$restoreAccessibilityButtonTargetsLocked$9 = com.android.server.accessibility.AccessibilityManagerService.lambda$restoreAccessibilityButtonTargetsLocked$9((java.lang.String) obj);
                return lambda$restoreAccessibilityButtonTargetsLocked$9;
            }
        }, arraySet, false);
        readColonDelimitedStringToSet(str2, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda54
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$restoreAccessibilityButtonTargetsLocked$10;
                lambda$restoreAccessibilityButtonTargetsLocked$10 = com.android.server.accessibility.AccessibilityManagerService.lambda$restoreAccessibilityButtonTargetsLocked$10((java.lang.String) obj);
                return lambda$restoreAccessibilityButtonTargetsLocked$10;
            }
        }, arraySet, true);
        com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(0);
        userStateLocked.mAccessibilityButtonTargets.clear();
        userStateLocked.mAccessibilityButtonTargets.addAll((java.util.Collection<? extends java.lang.String>) arraySet);
        persistColonDelimitedSetToSettingLocked("accessibility_button_targets", 0, userStateLocked.mAccessibilityButtonTargets, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda55
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$restoreAccessibilityButtonTargetsLocked$11;
                lambda$restoreAccessibilityButtonTargetsLocked$11 = com.android.server.accessibility.AccessibilityManagerService.lambda$restoreAccessibilityButtonTargetsLocked$11((java.lang.String) obj);
                return lambda$restoreAccessibilityButtonTargetsLocked$11;
            }
        });
        scheduleNotifyClientsOfServicesStateChangeLocked(userStateLocked);
        onUserStateChangedLocked(userStateLocked);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$restoreAccessibilityButtonTargetsLocked$9(java.lang.String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$restoreAccessibilityButtonTargetsLocked$10(java.lang.String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$restoreAccessibilityButtonTargetsLocked$11(java.lang.String str) {
        return str;
    }

    private int getClientStateLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        return accessibilityUserState.getClientStateLocked(this.mUiAutomationManager.canIntrospect(), this.mTraceManager.getTraceStateForAccessibilityManagerClientState());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.accessibility.AccessibilityManagerService.InteractionBridge getInteractionBridge() {
        com.android.server.accessibility.AccessibilityManagerService.InteractionBridge interactionBridge;
        synchronized (this.mLock) {
            try {
                if (this.mInteractionBridge == null) {
                    this.mInteractionBridge = new com.android.server.accessibility.AccessibilityManagerService.InteractionBridge();
                }
                interactionBridge = this.mInteractionBridge;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return interactionBridge;
    }

    private boolean notifyGestureLocked(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent, boolean z) {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
        for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
            if (accessibilityServiceConnection.mRequestTouchExplorationMode && accessibilityServiceConnection.mIsDefault == z) {
                accessibilityServiceConnection.notifyGesture(accessibilityGestureEvent);
                return true;
            }
        }
        return false;
    }

    private boolean scheduleNotifyMotionEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        int displayId = motionEvent.getDisplayId();
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                z = false;
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (!accessibilityServiceConnection.wantsGenericMotionEvent(motionEvent)) {
                        if (motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3) && accessibilityServiceConnection.isServiceDetectsGesturesEnabled(displayId)) {
                        }
                    }
                    accessibilityServiceConnection.notifyMotionEvent(motionEvent);
                    z = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    private boolean scheduleNotifyTouchState(int i, int i2) {
        boolean z;
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                z = false;
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (accessibilityServiceConnection.isServiceDetectsGesturesEnabled(i)) {
                        accessibilityServiceConnection.notifyTouchState(i, i2);
                        z = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    @Override // com.android.server.accessibility.ProxyManager.SystemSupport
    public void notifyClearAccessibilityCacheLocked() {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
        for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
            currentUserStateLocked.mBoundServices.get(size).notifyClearAccessibilityNodeInfoCache();
        }
        this.mProxyManager.clearCacheLocked();
    }

    private void notifyMagnificationChangedLocked(int i, @android.annotation.NonNull android.graphics.Region region, @android.annotation.NonNull android.accessibilityservice.MagnificationConfig magnificationConfig) {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
        for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
            currentUserStateLocked.mBoundServices.get(size).notifyMagnificationChangedLocked(i, region, magnificationConfig);
        }
    }

    private void sendAccessibilityButtonToInputFilter(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.notifyAccessibilityButtonClicked(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void showAccessibilityTargetsSelection(int i, int i2) {
        java.lang.String name;
        android.content.Intent intent = new android.content.Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
        if (i2 == 1) {
            name = com.android.internal.accessibility.dialog.AccessibilityShortcutChooserActivity.class.getName();
        } else {
            name = com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity.class.getName();
        }
        intent.setClassName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, name);
        intent.addFlags(268468224);
        this.mContext.startActivityAsUser(intent, android.app.ActivityOptions.makeBasic().setLaunchDisplayId(i).toBundle(), android.os.UserHandle.of(this.mCurrentUserId));
    }

    private void launchShortcutTargetActivity(int i, android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent();
        android.os.Bundle bundle = android.app.ActivityOptions.makeBasic().setLaunchDisplayId(i).toBundle();
        intent.setComponent(componentName);
        intent.addFlags(268435456);
        try {
            this.mContext.startActivityAsUser(intent, bundle, android.os.UserHandle.of(this.mCurrentUserId));
        } catch (android.content.ActivityNotFoundException e) {
        }
    }

    private void launchAccessibilitySubSettings(int i, android.content.ComponentName componentName) {
        android.content.Intent intent = new android.content.Intent("android.settings.ACCESSIBILITY_DETAILS_SETTINGS");
        android.os.Bundle bundle = android.app.ActivityOptions.makeBasic().setLaunchDisplayId(i).toBundle();
        intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName.flattenToString());
        try {
            this.mContext.startActivityAsUser(intent, bundle, android.os.UserHandle.of(this.mCurrentUserId));
        } catch (android.content.ActivityNotFoundException e) {
        }
    }

    private void notifyAccessibilityButtonVisibilityChangedLocked(boolean z) {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
        this.mIsAccessibilityButtonShown = z;
        for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
            if (accessibilityServiceConnection.mRequestAccessibilityButton) {
                accessibilityServiceConnection.notifyAccessibilityButtonAvailabilityChangedLocked(accessibilityServiceConnection.isAccessibilityButtonAvailableLocked(currentUserStateLocked));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo> parseAccessibilityServiceInfos(int i) {
        int i2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            if (!getUserStateLocked(i).getBindInstantServiceAllowedLocked()) {
                i2 = 819332;
            } else {
                i2 = 9207940;
            }
        }
        java.util.List queryIntentServicesAsUser = this.mPackageManager.queryIntentServicesAsUser(new android.content.Intent("android.accessibilityservice.AccessibilityService"), i2, i);
        int size = queryIntentServicesAsUser.size();
        for (int i3 = 0; i3 < size; i3++) {
            android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i3);
            if (this.mSecurityPolicy.canRegisterService(resolveInfo.serviceInfo)) {
                try {
                    android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = new android.accessibilityservice.AccessibilityServiceInfo(resolveInfo, this.mContext);
                    if (!accessibilityServiceInfo.isWithinParcelableSize()) {
                        android.util.Slog.e(LOG_TAG, "Skipping service " + accessibilityServiceInfo.getResolveInfo().getComponentInfo() + " because service info size is larger than safe parcelable limits.");
                    } else {
                        arrayList.add(accessibilityServiceInfo);
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.e(LOG_TAG, "Error while initializing AccessibilityServiceInfo", e);
                }
            }
        }
        return arrayList;
    }

    private boolean readInstalledAccessibilityServiceLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, @android.annotation.Nullable java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = list.get(i);
            if (accessibilityUserState.mCrashedServices.contains(accessibilityServiceInfo.getComponentName())) {
                accessibilityServiceInfo.crashed = true;
            }
        }
        if (list.equals(accessibilityUserState.mInstalledServices)) {
            return false;
        }
        accessibilityUserState.mInstalledServices.clear();
        accessibilityUserState.mInstalledServices.addAll(list);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> parseAccessibilityShortcutInfos(int i) {
        return android.view.accessibility.AccessibilityManager.getInstance(this.mContext).getInstalledAccessibilityShortcutListAsUser(this.mContext, i);
    }

    private boolean readInstalledAccessibilityShortcutLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> list) {
        if (!list.equals(accessibilityUserState.mInstalledShortcuts)) {
            accessibilityUserState.mInstalledShortcuts.clear();
            accessibilityUserState.mInstalledShortcuts.addAll(list);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readEnabledAccessibilityServicesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        this.mTempComponentNameSet.clear();
        readComponentNamesFromSettingLocked("enabled_accessibility_services", accessibilityUserState.mUserId, this.mTempComponentNameSet);
        if (!this.mTempComponentNameSet.equals(accessibilityUserState.mEnabledServices)) {
            accessibilityUserState.mEnabledServices.clear();
            accessibilityUserState.mEnabledServices.addAll(this.mTempComponentNameSet);
            this.mTempComponentNameSet.clear();
            return true;
        }
        this.mTempComponentNameSet.clear();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readTouchExplorationGrantedAccessibilityServicesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        this.mTempComponentNameSet.clear();
        readComponentNamesFromSettingLocked("touch_exploration_granted_accessibility_services", accessibilityUserState.mUserId, this.mTempComponentNameSet);
        if (!this.mTempComponentNameSet.equals(accessibilityUserState.mTouchExplorationGrantedServices)) {
            accessibilityUserState.mTouchExplorationGrantedServices.clear();
            accessibilityUserState.mTouchExplorationGrantedServices.addAll(this.mTempComponentNameSet);
            this.mTempComponentNameSet.clear();
            return true;
        }
        this.mTempComponentNameSet.clear();
        return false;
    }

    private void notifyAccessibilityServicesDelayedLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z) {
        try {
            com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
            int size = currentUserStateLocked.mBoundServices.size();
            for (int i = 0; i < size; i++) {
                com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(i);
                if (accessibilityServiceConnection.mIsDefault == z) {
                    accessibilityServiceConnection.notifyAccessibilityEvent(accessibilityEvent);
                }
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
        }
    }

    private void updateRelevantEventsLocked(final com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(2L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.updateRelevantEventsLocked", 2L, "userState=" + accessibilityUserState);
        }
        this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.AccessibilityManagerService.this.lambda$updateRelevantEventsLocked$13(accessibilityUserState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRelevantEventsLocked$13(final com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        broadcastToClients(accessibilityUserState, com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda27
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.accessibility.AccessibilityManagerService.this.lambda$updateRelevantEventsLocked$12(accessibilityUserState, (com.android.server.accessibility.AccessibilityManagerService.Client) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRelevantEventsLocked$12(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, com.android.server.accessibility.AccessibilityManagerService.Client client) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                int computeRelevantEventTypesLocked = computeRelevantEventTypesLocked(accessibilityUserState, client);
                if (!this.mProxyManager.isProxyedDeviceId(client.mDeviceId) && client.mLastSentRelevantEventTypes != computeRelevantEventTypesLocked) {
                    client.mLastSentRelevantEventTypes = computeRelevantEventTypesLocked;
                    client.mCallback.setRelevantEventTypes(computeRelevantEventTypesLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int computeRelevantEventTypesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, com.android.server.accessibility.AccessibilityManagerService.Client client) {
        int i;
        int size = accessibilityUserState.mBoundServices.size();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = accessibilityUserState.mBoundServices.get(i4);
            if (isClientInPackageAllowlist(accessibilityServiceConnection.getServiceInfo(), client)) {
                i = accessibilityServiceConnection.getRelevantEventTypes();
            } else {
                i = 0;
            }
            i3 |= i;
        }
        if (isClientInPackageAllowlist(this.mUiAutomationManager.getServiceInfo(), client)) {
            i2 = this.mUiAutomationManager.getRelevantEventTypes();
        }
        return i3 | i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMagnificationModeChangeSettingsLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, int i) {
        if (accessibilityUserState.mUserId != this.mCurrentUserId || fallBackMagnificationModeSettingsLocked(accessibilityUserState, i)) {
            return;
        }
        this.mMagnificationController.transitionMagnificationModeLocked(i, accessibilityUserState.getMagnificationModeLocked(i), new com.android.server.accessibility.magnification.MagnificationController.TransitionCallBack() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda32
            @Override // com.android.server.accessibility.magnification.MagnificationController.TransitionCallBack
            public final void onResult(int i2, boolean z) {
                com.android.server.accessibility.AccessibilityManagerService.this.onMagnificationTransitionEndedLocked(i2, z);
            }
        });
    }

    void onMagnificationTransitionEndedLocked(int i, boolean z) {
        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
        int magnificationModeLocked = currentUserStateLocked.getMagnificationModeLocked(i) ^ 3;
        if (!z && magnificationModeLocked != 0) {
            currentUserStateLocked.setMagnificationModeLocked(i, magnificationModeLocked);
            if (i == 0) {
                persistMagnificationModeSettingsLocked(magnificationModeLocked);
                return;
            }
            return;
        }
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda38
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).notifyRefreshMagnificationModeToInputFilter(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRefreshMagnificationModeToInputFilter(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter) {
                    java.util.ArrayList<android.view.Display> validDisplayList = getValidDisplayList();
                    for (int i2 = 0; i2 < validDisplayList.size(); i2++) {
                        android.view.Display display = validDisplayList.get(i2);
                        if (display != null && display.getDisplayId() == i) {
                            this.mInputFilter.refreshMagnificationMode(display);
                            return;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static boolean isClientInPackageAllowlist(@android.annotation.Nullable android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo, com.android.server.accessibility.AccessibilityManagerService.Client client) {
        if (accessibilityServiceInfo == null) {
            return false;
        }
        java.lang.String[] strArr = client.mPackageNames;
        boolean isEmpty = com.android.internal.util.ArrayUtils.isEmpty(accessibilityServiceInfo.packageNames);
        if (!isEmpty && strArr != null) {
            for (java.lang.String str : strArr) {
                if (com.android.internal.util.ArrayUtils.contains(accessibilityServiceInfo.packageNames, str)) {
                    return true;
                }
            }
            return isEmpty;
        }
        return isEmpty;
    }

    private void broadcastToClients(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, java.util.function.Consumer<com.android.server.accessibility.AccessibilityManagerService.Client> consumer) {
        this.mGlobalClients.broadcastForEachCookie(consumer);
        accessibilityUserState.mUserClients.broadcastForEachCookie(consumer);
    }

    @com.android.internal.annotations.VisibleForTesting
    void readComponentNamesFromSettingLocked(java.lang.String str, int i, java.util.Set<android.content.ComponentName> set) {
        readColonDelimitedSettingToSet(str, i, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda28
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.content.ComponentName unflattenFromString;
                unflattenFromString = android.content.ComponentName.unflattenFromString((java.lang.String) obj);
                return unflattenFromString;
            }
        }, set);
    }

    private void readComponentNamesFromStringLocked(java.lang.String str, java.util.Set<android.content.ComponentName> set, boolean z) {
        readColonDelimitedStringToSet(str, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.content.ComponentName unflattenFromString;
                unflattenFromString = android.content.ComponentName.unflattenFromString((java.lang.String) obj);
                return unflattenFromString;
            }
        }, set, z);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void persistComponentNamesToSettingLocked(java.lang.String str, java.util.Set<android.content.ComponentName> set, int i) {
        persistColonDelimitedSetToSettingLocked(str, i, set, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String flattenToShortString;
                flattenToShortString = ((android.content.ComponentName) obj).flattenToShortString();
                return flattenToShortString;
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    <T> void readColonDelimitedSettingToSet(java.lang.String str, int i, java.util.function.Function<java.lang.String, T> function, java.util.Set<T> set) {
        readColonDelimitedStringToSet(android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i), function, set, false);
    }

    private <T> void readColonDelimitedStringToSet(java.lang.String str, java.util.function.Function<java.lang.String, T> function, java.util.Set<T> set, boolean z) {
        T apply;
        if (!z) {
            set.clear();
        }
        if (!android.text.TextUtils.isEmpty(str)) {
            android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = this.mStringColonSplitter;
            simpleStringSplitter.setString(str);
            while (simpleStringSplitter.hasNext()) {
                java.lang.String next = simpleStringSplitter.next();
                if (!android.text.TextUtils.isEmpty(next) && (apply = function.apply(next)) != null) {
                    set.add(apply);
                }
            }
        }
    }

    private <T> void persistColonDelimitedSetToSettingLocked(java.lang.String str, int i, java.util.Set<T> set, java.util.function.Function<T, java.lang.String> function) {
        java.lang.String str2;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<T> it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            T next = it.next();
            str2 = next != null ? function.apply(next) : null;
            if (!android.text.TextUtils.isEmpty(str2)) {
                if (sb.length() > 0) {
                    sb.append(COMPONENT_NAME_SEPARATOR);
                }
                sb.append(str2);
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String sb2 = sb.toString();
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            if (!android.text.TextUtils.isEmpty(sb2)) {
                str2 = sb2;
            }
            android.provider.Settings.Secure.putStringForUser(contentResolver, str, str2, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void updateServicesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int i;
        int i2;
        java.util.Map<android.content.ComponentName, com.android.server.accessibility.AccessibilityServiceConnection> map;
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState2;
        com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService;
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState3;
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState4;
        com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService2 = this;
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState5 = accessibilityUserState;
        java.util.Map<android.content.ComponentName, com.android.server.accessibility.AccessibilityServiceConnection> map2 = accessibilityUserState5.mComponentNameToServiceMap;
        boolean isUserUnlockingOrUnlocked = accessibilityManagerService2.mUmi.isUserUnlockingOrUnlocked(accessibilityUserState5.mUserId);
        accessibilityManagerService2.mTempComponentNameSet.clear();
        int size = accessibilityUserState5.mInstalledServices.size();
        int i3 = 0;
        while (i3 < size) {
            android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = accessibilityUserState5.mInstalledServices.get(i3);
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(accessibilityServiceInfo.getId());
            accessibilityManagerService2.mTempComponentNameSet.add(unflattenFromString);
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = map2.get(unflattenFromString);
            if (!isUserUnlockingOrUnlocked && !accessibilityServiceInfo.isDirectBootAware()) {
                android.util.Slog.d(LOG_TAG, "Ignoring non-encryption-aware service " + unflattenFromString);
                i = i3;
                i2 = size;
                map = map2;
                accessibilityUserState2 = accessibilityUserState5;
                accessibilityManagerService = accessibilityManagerService2;
            } else if (accessibilityUserState.getBindingServicesLocked().contains(unflattenFromString)) {
                i = i3;
                i2 = size;
                map = map2;
                accessibilityUserState2 = accessibilityUserState5;
                accessibilityManagerService = accessibilityManagerService2;
            } else if (accessibilityUserState.getCrashedServicesLocked().contains(unflattenFromString)) {
                i = i3;
                i2 = size;
                map = map2;
                accessibilityUserState2 = accessibilityUserState5;
                accessibilityManagerService = accessibilityManagerService2;
            } else {
                if (!accessibilityUserState5.mEnabledServices.contains(unflattenFromString)) {
                    i = i3;
                    i2 = size;
                    map = map2;
                    accessibilityUserState3 = accessibilityUserState5;
                } else if (accessibilityManagerService2.mUiAutomationManager.suppressingAccessibilityServicesLocked()) {
                    i = i3;
                    i2 = size;
                    map = map2;
                    accessibilityUserState3 = accessibilityUserState5;
                } else if (!accessibilityManagerService2.isAccessibilityTargetAllowed(unflattenFromString.getPackageName(), accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.uid, accessibilityUserState5.mUserId)) {
                    android.util.Slog.d(LOG_TAG, "Skipping enabling service disallowed by device admin policy: " + unflattenFromString);
                    accessibilityManagerService2.disableAccessibilityServiceLocked(unflattenFromString, accessibilityUserState5.mUserId);
                    i = i3;
                    i2 = size;
                    map = map2;
                    accessibilityUserState2 = accessibilityUserState5;
                    accessibilityManagerService = accessibilityManagerService2;
                } else {
                    if (accessibilityServiceConnection == null) {
                        android.content.Context context = accessibilityManagerService2.mContext;
                        int i4 = sIdCounter;
                        sIdCounter = i4 + 1;
                        android.os.Handler handler = accessibilityManagerService2.mMainHandler;
                        java.lang.Object obj = accessibilityManagerService2.mLock;
                        com.android.server.accessibility.AccessibilitySecurityPolicy accessibilitySecurityPolicy = accessibilityManagerService2.mSecurityPolicy;
                        com.android.server.accessibility.AccessibilityTraceManager traceManager = getTraceManager();
                        com.android.server.wm.WindowManagerInternal windowManagerInternal = accessibilityManagerService2.mWindowManagerService;
                        com.android.server.accessibility.SystemActionPerformer systemActionPerformer = getSystemActionPerformer();
                        com.android.server.accessibility.AccessibilityWindowManager accessibilityWindowManager = accessibilityManagerService2.mA11yWindowManager;
                        com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal = accessibilityManagerService2.mActivityTaskManagerService;
                        i = i3;
                        i2 = size;
                        map = map2;
                        accessibilityUserState4 = accessibilityUserState5;
                        accessibilityServiceConnection = new com.android.server.accessibility.AccessibilityServiceConnection(accessibilityUserState, context, unflattenFromString, accessibilityServiceInfo, i4, handler, obj, accessibilitySecurityPolicy, this, traceManager, windowManagerInternal, systemActionPerformer, accessibilityWindowManager, activityTaskManagerInternal);
                    } else {
                        i = i3;
                        i2 = size;
                        map = map2;
                        accessibilityUserState4 = accessibilityUserState5;
                        if (accessibilityUserState4.mBoundServices.contains(accessibilityServiceConnection)) {
                            accessibilityManagerService = this;
                            accessibilityUserState2 = accessibilityUserState4;
                        }
                    }
                    accessibilityServiceConnection.bindLocked();
                    accessibilityManagerService = this;
                    accessibilityUserState2 = accessibilityUserState4;
                }
                if (accessibilityServiceConnection == null) {
                    accessibilityManagerService = this;
                    accessibilityUserState2 = accessibilityUserState3;
                } else {
                    accessibilityServiceConnection.unbindLocked();
                    accessibilityManagerService = this;
                    accessibilityUserState2 = accessibilityUserState3;
                    accessibilityManagerService.removeShortcutTargetForUnboundServiceLocked(accessibilityUserState2, accessibilityServiceConnection);
                }
            }
            i3 = i + 1;
            accessibilityManagerService2 = accessibilityManagerService;
            accessibilityUserState5 = accessibilityUserState2;
            map2 = map;
            size = i2;
        }
        com.android.server.accessibility.AccessibilityUserState accessibilityUserState6 = accessibilityUserState5;
        final com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService3 = accessibilityManagerService2;
        int size2 = accessibilityUserState6.mBoundServices.size();
        accessibilityManagerService3.mTempIntArray.clear();
        for (int i5 = 0; i5 < size2; i5++) {
            android.content.pm.ResolveInfo resolveInfo = accessibilityUserState6.mBoundServices.get(i5).mAccessibilityServiceInfo.getResolveInfo();
            if (resolveInfo != null) {
                accessibilityManagerService3.mTempIntArray.add(resolveInfo.serviceInfo.applicationInfo.uid);
            }
        }
        android.media.AudioManagerInternal audioManagerInternal = (android.media.AudioManagerInternal) com.android.server.LocalServices.getService(android.media.AudioManagerInternal.class);
        if (audioManagerInternal != null) {
            audioManagerInternal.setAccessibilityServiceUids(accessibilityManagerService3.mTempIntArray);
        }
        accessibilityManagerService3.mActivityTaskManagerService.setAccessibilityServiceUids(accessibilityManagerService3.mTempIntArray);
        if (accessibilityUserState6.mEnabledServices.removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda30
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj2) {
                boolean lambda$updateServicesLocked$17;
                lambda$updateServicesLocked$17 = com.android.server.accessibility.AccessibilityManagerService.this.lambda$updateServicesLocked$17((android.content.ComponentName) obj2);
                return lambda$updateServicesLocked$17;
            }
        }) || accessibilityUserState6.mTouchExplorationGrantedServices.removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda31
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj2) {
                boolean lambda$updateServicesLocked$18;
                lambda$updateServicesLocked$18 = com.android.server.accessibility.AccessibilityManagerService.this.lambda$updateServicesLocked$18((android.content.ComponentName) obj2);
                return lambda$updateServicesLocked$18;
            }
        })) {
            accessibilityManagerService3.persistComponentNamesToSettingLocked("enabled_accessibility_services", accessibilityUserState6.mEnabledServices, accessibilityUserState6.mUserId);
            accessibilityManagerService3.persistComponentNamesToSettingLocked("touch_exploration_granted_accessibility_services", accessibilityUserState6.mTouchExplorationGrantedServices, accessibilityUserState6.mUserId);
        }
        updateAccessibilityEnabledSettingLocked(accessibilityUserState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateServicesLocked$17(android.content.ComponentName componentName) {
        return !this.mTempComponentNameSet.contains(componentName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateServicesLocked$18(android.content.ComponentName componentName) {
        return !this.mTempComponentNameSet.contains(componentName);
    }

    void scheduleUpdateClientsIfNeededLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        scheduleUpdateClientsIfNeededLocked(accessibilityUserState, false);
    }

    void scheduleUpdateClientsIfNeededLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, boolean z) {
        int clientStateLocked = getClientStateLocked(accessibilityUserState);
        if (accessibilityUserState.getLastSentClientStateLocked() != clientStateLocked || z) {
            if (this.mGlobalClients.getRegisteredCallbackCount() > 0 || accessibilityUserState.mUserClients.getRegisteredCallbackCount() > 0) {
                accessibilityUserState.setLastSentClientStateLocked(clientStateLocked);
                this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda37
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.accessibility.AccessibilityManagerService) obj).sendStateToAllClients(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                    }
                }, this, java.lang.Integer.valueOf(clientStateLocked), java.lang.Integer.valueOf(accessibilityUserState.mUserId)));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendStateToAllClients(int i, int i2) {
        sendStateToClients(i, this.mGlobalClients);
        sendStateToClients(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendStateToClients(int i, int i2) {
        sendStateToClients(i, getUserState(i2).mUserClients);
    }

    private void sendStateToClients(final int i, android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> remoteCallbackList) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(8L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.sendStateToClients", 8L, "clientState=" + i);
        }
        remoteCallbackList.broadcastForEachCookie(com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda8
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.accessibility.AccessibilityManagerService.this.lambda$sendStateToClients$19(i, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendStateToClients$19(int i, java.lang.Object obj) throws android.os.RemoteException {
        com.android.server.accessibility.AccessibilityManagerService.Client client = (com.android.server.accessibility.AccessibilityManagerService.Client) obj;
        if (!this.mProxyManager.isProxyedDeviceId(client.mDeviceId)) {
            client.mCallback.setState(i);
        }
    }

    private void scheduleNotifyClientsOfServicesStateChangeLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        updateRecommendedUiTimeoutLocked(accessibilityUserState);
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda23
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).sendServicesStateChanged((android.os.RemoteCallbackList) obj2, ((java.lang.Long) obj3).longValue());
            }
        }, this, accessibilityUserState.mUserClients, java.lang.Long.valueOf(getRecommendedTimeoutMillisLocked(accessibilityUserState))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendServicesStateChanged(android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> remoteCallbackList, long j) {
        notifyClientsOfServicesStateChange(this.mGlobalClients, j);
        notifyClientsOfServicesStateChange(remoteCallbackList, j);
    }

    private void notifyClientsOfServicesStateChange(android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> remoteCallbackList, final long j) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(8L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.notifyClientsOfServicesStateChange", 8L, "uiTimeout=" + j);
        }
        remoteCallbackList.broadcastForEachCookie(com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda15
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.accessibility.AccessibilityManagerService.this.lambda$notifyClientsOfServicesStateChange$20(j, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyClientsOfServicesStateChange$20(long j, java.lang.Object obj) throws android.os.RemoteException {
        com.android.server.accessibility.AccessibilityManagerService.Client client = (com.android.server.accessibility.AccessibilityManagerService.Client) obj;
        if (!this.mProxyManager.isProxyedDeviceId(client.mDeviceId)) {
            client.mCallback.notifyServicesStateChanged(j);
        }
    }

    private void scheduleUpdateInputFilter(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda60
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).updateInputFilter((com.android.server.accessibility.AccessibilityUserState) obj2);
            }
        }, this, accessibilityUserState));
    }

    private void scheduleUpdateFingerprintGestureHandling(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda63
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).updateFingerprintGestureHandling((com.android.server.accessibility.AccessibilityUserState) obj2);
            }
        }, this, accessibilityUserState));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputFilter(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z;
        int i;
        com.android.server.accessibility.AccessibilityInputFilter accessibilityInputFilter;
        if (this.mUiAutomationManager.suppressingAccessibilityServicesLocked()) {
            return;
        }
        synchronized (this.mLock) {
            try {
                z = true;
                if (!accessibilityUserState.isMagnificationSingleFingerTripleTapEnabledLocked()) {
                    i = 0;
                } else {
                    i = 1;
                }
                com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
                if (accessibilityUserState.isShortcutMagnificationEnabledLocked()) {
                    i |= 64;
                }
                if (userHasMagnificationServicesLocked(accessibilityUserState)) {
                    i |= 32;
                }
                if (accessibilityUserState.isHandlingAccessibilityEventsLocked() && accessibilityUserState.isTouchExplorationEnabledLocked()) {
                    i |= 2;
                    if (accessibilityUserState.isServiceHandlesDoubleTapEnabledLocked()) {
                        i |= 128;
                    }
                    if (accessibilityUserState.isMultiFingerGesturesEnabledLocked()) {
                        i |= 256;
                    }
                    if (accessibilityUserState.isTwoFingerPassthroughEnabledLocked()) {
                        i |= 512;
                    }
                }
                if (accessibilityUserState.isFilterKeyEventsEnabledLocked()) {
                    i |= 4;
                }
                if (accessibilityUserState.isSendMotionEventsEnabled()) {
                    i |= 1024;
                }
                if (accessibilityUserState.isAutoclickEnabledLocked()) {
                    i |= 8;
                }
                if (accessibilityUserState.isPerformGesturesEnabledLocked()) {
                    i |= 16;
                }
                java.util.Iterator<com.android.server.accessibility.AccessibilityServiceConnection> it = accessibilityUserState.mBoundServices.iterator();
                int i2 = 0;
                int i3 = 0;
                while (it.hasNext()) {
                    com.android.server.accessibility.AccessibilityServiceConnection next = it.next();
                    i2 |= next.mGenericMotionEventSources;
                    i3 |= next.mObservedMotionEventSources;
                }
                if (i2 != 0) {
                    i |= 2048;
                }
                accessibilityInputFilter = null;
                if (i != 0) {
                    if (this.mHasInputFilter) {
                        z = false;
                    } else {
                        this.mHasInputFilter = true;
                        if (this.mInputFilter == null) {
                            this.mInputFilter = new com.android.server.accessibility.AccessibilityInputFilter(this.mContext, this);
                        }
                        accessibilityInputFilter = this.mInputFilter;
                    }
                    this.mInputFilter.setUserAndEnabledFeatures(accessibilityUserState.mUserId, i);
                    this.mInputFilter.setCombinedGenericMotionEventSources(i2);
                    this.mInputFilter.setCombinedMotionEventObservedSources(i3);
                } else if (!this.mHasInputFilter) {
                    z = false;
                } else {
                    this.mHasInputFilter = false;
                    this.mInputFilter.setUserAndEnabledFeatures(accessibilityUserState.mUserId, 0);
                    this.mInputFilter.resetServiceDetectsGestures();
                    if (accessibilityUserState.isTouchExplorationEnabledLocked()) {
                        java.util.Iterator<android.view.Display> it2 = getValidDisplayList().iterator();
                        while (it2.hasNext()) {
                            int displayId = it2.next().getDisplayId();
                            this.mInputFilter.setServiceDetectsGesturesEnabled(displayId, accessibilityUserState.isServiceDetectsGesturesEnabled(displayId));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            if (this.mTraceManager.isA11yTracingEnabledForTypes(4608L)) {
                this.mTraceManager.logTrace("WindowManagerInternal.setInputFilter", 4608L, "inputFilter=" + accessibilityInputFilter);
            }
            this.mWindowManagerService.setInputFilter(accessibilityInputFilter);
            this.mProxyManager.setAccessibilityInputFilter(accessibilityInputFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEnableTouchExplorationDialog(final com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        synchronized (this.mLock) {
            try {
                java.lang.String charSequence = accessibilityServiceConnection.getServiceInfo().getResolveInfo().loadLabel(this.mContext.getPackageManager()).toString();
                final com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                if (currentUserStateLocked.isTouchExplorationEnabledLocked()) {
                    return;
                }
                if (this.mEnableTouchExplorationDialog == null || !this.mEnableTouchExplorationDialog.isShowing()) {
                    this.mEnableTouchExplorationDialog = new android.app.AlertDialog.Builder(this.mContext).setIconAttribute(android.R.attr.alertDialogIcon).setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.accessibility.AccessibilityManagerService.7
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(android.content.DialogInterface dialogInterface, int i) {
                            currentUserStateLocked.mTouchExplorationGrantedServices.add(accessibilityServiceConnection.mComponentName);
                            com.android.server.accessibility.AccessibilityManagerService.this.persistComponentNamesToSettingLocked("touch_exploration_granted_accessibility_services", currentUserStateLocked.mTouchExplorationGrantedServices, currentUserStateLocked.mUserId);
                            currentUserStateLocked.setTouchExplorationEnabledLocked(true);
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                android.provider.Settings.Secure.putIntForUser(com.android.server.accessibility.AccessibilityManagerService.this.mContext.getContentResolver(), "touch_exploration_enabled", 1, currentUserStateLocked.mUserId);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                            } catch (java.lang.Throwable th) {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                    }).setNegativeButton(android.R.string.cancel, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.accessibility.AccessibilityManagerService.6
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(android.content.DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setTitle(android.R.string.dynamic_mode_notification_summary_v2).setMessage(this.mContext.getString(android.R.string.dynamic_mode_notification_summary, charSequence)).create();
                    this.mEnableTouchExplorationDialog.getWindow().setType(2003);
                    this.mEnableTouchExplorationDialog.getWindow().getAttributes().privateFlags |= 16;
                    this.mEnableTouchExplorationDialog.setCanceledOnTouchOutside(true);
                    this.mEnableTouchExplorationDialog.show();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onUserVisibilityChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mVisibleBgUserIds.put(i, z);
                } else {
                    this.mVisibleBgUserIds.delete(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStateChangedLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        onUserStateChangedLocked(accessibilityUserState, false);
    }

    private void onUserStateChangedLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, boolean z) {
        this.mInitialized = true;
        updateLegacyCapabilitiesLocked(accessibilityUserState);
        updateServicesLocked(accessibilityUserState);
        updateWindowsForAccessibilityCallbackLocked(accessibilityUserState);
        updateFilterKeyEventsLocked(accessibilityUserState);
        updateTouchExplorationLocked(accessibilityUserState);
        updatePerformGesturesLocked(accessibilityUserState);
        updateMagnificationLocked(accessibilityUserState);
        scheduleUpdateFingerprintGestureHandling(accessibilityUserState);
        scheduleUpdateInputFilter(accessibilityUserState);
        updateRelevantEventsLocked(accessibilityUserState);
        scheduleUpdateClientsIfNeededLocked(accessibilityUserState, z);
        updateAccessibilityShortcutKeyTargetsLocked(accessibilityUserState);
        updateAccessibilityButtonTargetsLocked(accessibilityUserState);
        updateMagnificationCapabilitiesSettingsChangeLocked(accessibilityUserState);
        updateMagnificationModeChangeSettingsForAllDisplaysLocked(accessibilityUserState);
        updateFocusAppearanceDataLocked(accessibilityUserState);
    }

    private void updateMagnificationModeChangeSettingsForAllDisplaysLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        java.util.ArrayList<android.view.Display> validDisplayList = getValidDisplayList();
        for (int i = 0; i < validDisplayList.size(); i++) {
            updateMagnificationModeChangeSettingsLocked(accessibilityUserState, validDisplayList.get(i).getDisplayId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWindowsForAccessibilityCallbackLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = this.mUiAutomationManager.canRetrieveInteractiveWindowsLocked() || this.mProxyManager.canRetrieveInteractiveWindowsLocked();
        java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = accessibilityUserState.mBoundServices;
        int size = arrayList.size();
        for (int i = 0; !z && i < size; i++) {
            if (arrayList.get(i).canRetrieveInteractiveWindowsLocked()) {
                accessibilityUserState.setAccessibilityFocusOnlyInActiveWindow(false);
                z = true;
            }
        }
        accessibilityUserState.setAccessibilityFocusOnlyInActiveWindow(true);
        java.util.ArrayList<android.view.Display> validDisplayList = getValidDisplayList();
        for (int i2 = 0; i2 < validDisplayList.size(); i2++) {
            android.view.Display display = validDisplayList.get(i2);
            if (display != null) {
                if (z) {
                    this.mA11yWindowManager.startTrackingWindows(display.getDisplayId(), this.mProxyManager.isProxyedDisplay(display.getDisplayId()));
                } else {
                    this.mA11yWindowManager.stopTrackingWindows(display.getDisplayId());
                }
            }
        }
    }

    private void updateLegacyCapabilitiesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int size = accessibilityUserState.mInstalledServices.size();
        for (int i = 0; i < size; i++) {
            android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = accessibilityUserState.mInstalledServices.get(i);
            android.content.pm.ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
            if ((accessibilityServiceInfo.getCapabilities() & 2) == 0 && resolveInfo.serviceInfo.applicationInfo.targetSdkVersion <= 17) {
                if (accessibilityUserState.mTouchExplorationGrantedServices.contains(new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name))) {
                    accessibilityServiceInfo.setCapabilities(accessibilityServiceInfo.getCapabilities() | 2);
                }
            }
        }
    }

    private void updatePerformGesturesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int size = accessibilityUserState.mBoundServices.size();
        for (int i = 0; i < size; i++) {
            if ((accessibilityUserState.mBoundServices.get(i).getCapabilities() & 32) != 0) {
                accessibilityUserState.setPerformGesturesEnabledLocked(true);
                return;
            }
        }
        accessibilityUserState.setPerformGesturesEnabledLocked(false);
    }

    private void updateFilterKeyEventsLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int size = accessibilityUserState.mBoundServices.size();
        for (int i = 0; i < size; i++) {
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = accessibilityUserState.mBoundServices.get(i);
            if (accessibilityServiceConnection.mRequestFilterKeyEvents && (accessibilityServiceConnection.getCapabilities() & 8) != 0) {
                accessibilityUserState.setFilterKeyEventsEnabledLocked(true);
                return;
            }
        }
        accessibilityUserState.setFilterKeyEventsEnabledLocked(false);
    }

    private boolean readConfigurationForUserStateLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        return readConfigurationForUserStateLocked(accessibilityUserState, parseAccessibilityServiceInfos(this.mCurrentUserId), parseAccessibilityShortcutInfos(this.mCurrentUserId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readConfigurationForUserStateLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, java.util.List<android.accessibilityservice.AccessibilityServiceInfo> list, java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> list2) {
        return readAlwaysOnMagnificationLocked(accessibilityUserState) | readInstalledAccessibilityServiceLocked(accessibilityUserState, list) | readInstalledAccessibilityShortcutLocked(accessibilityUserState, list2) | readEnabledAccessibilityServicesLocked(accessibilityUserState) | readTouchExplorationGrantedAccessibilityServicesLocked(accessibilityUserState) | readTouchExplorationEnabledSettingLocked(accessibilityUserState) | readHighTextContrastEnabledSettingLocked(accessibilityUserState) | readAudioDescriptionEnabledSettingLocked(accessibilityUserState) | readMagnificationEnabledSettingsLocked(accessibilityUserState) | readAutoclickEnabledSettingLocked(accessibilityUserState) | readAccessibilityShortcutKeySettingLocked(accessibilityUserState) | readAccessibilityButtonTargetsLocked(accessibilityUserState) | readAccessibilityButtonTargetComponentLocked(accessibilityUserState) | readUserRecommendedUiTimeoutSettingsLocked(accessibilityUserState) | readMagnificationModeForDefaultDisplayLocked(accessibilityUserState) | readMagnificationCapabilitiesLocked(accessibilityUserState) | readMagnificationFollowTypingLocked(accessibilityUserState);
    }

    private void updateAccessibilityEnabledSettingLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int i = 1;
        boolean z = this.mUiAutomationManager.canIntrospect() || accessibilityUserState.isHandlingAccessibilityEventsLocked();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            if (!z) {
                i = 0;
            }
            android.provider.Settings.Secure.putIntForUser(contentResolver, "accessibility_enabled", i, accessibilityUserState.mUserId);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readTouchExplorationEnabledSettingLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "touch_exploration_enabled", 0, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.isTouchExplorationEnabledLocked()) {
            return false;
        }
        accessibilityUserState.setTouchExplorationEnabledLocked(z);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readMagnificationEnabledSettingsLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_enabled", 0, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.isMagnificationSingleFingerTripleTapEnabledLocked()) {
            return false;
        }
        accessibilityUserState.setMagnificationSingleFingerTripleTapEnabledLocked(z);
        return true;
    }

    private boolean readMagnificationTwoFingerTripleTapSettingsLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_two_finger_triple_tap_enabled", 0, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.isMagnificationTwoFingerTripleTapEnabledLocked()) {
            return false;
        }
        accessibilityUserState.setMagnificationTwoFingerTripleTapEnabledLocked(z);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readAutoclickEnabledSettingLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_autoclick_enabled", 0, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.isAutoclickEnabledLocked()) {
            return false;
        }
        accessibilityUserState.setAutoclickEnabledLocked(z);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readHighTextContrastEnabledSettingLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "high_text_contrast_enabled", 0, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.isTextHighContrastEnabledLocked()) {
            return false;
        }
        accessibilityUserState.setTextHighContrastEnabledLocked(z);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readAudioDescriptionEnabledSettingLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "enabled_accessibility_audio_description_by_default", 0, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.isAudioDescriptionByDefaultEnabledLocked()) {
            return false;
        }
        accessibilityUserState.setAudioDescriptionByDefaultEnabledLocked(z);
        return true;
    }

    private void updateTouchExplorationLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean isTouchExplorationEnabledLocked = this.mUiAutomationManager.isTouchExplorationEnabledLocked();
        int size = accessibilityUserState.mBoundServices.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                z = false;
                z2 = false;
                z3 = false;
                z4 = false;
                break;
            }
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = accessibilityUserState.mBoundServices.get(i);
            if (!canRequestAndRequestsTouchExplorationLocked(accessibilityServiceConnection, accessibilityUserState)) {
                i++;
            } else {
                boolean isServiceHandlesDoubleTapEnabled = accessibilityServiceConnection.isServiceHandlesDoubleTapEnabled();
                boolean isMultiFingerGesturesEnabled = accessibilityServiceConnection.isMultiFingerGesturesEnabled();
                boolean isTwoFingerPassthroughEnabled = accessibilityServiceConnection.isTwoFingerPassthroughEnabled();
                z4 = accessibilityServiceConnection.isSendMotionEventsEnabled();
                z3 = isTwoFingerPassthroughEnabled;
                z2 = isMultiFingerGesturesEnabled;
                z = isServiceHandlesDoubleTapEnabled;
                isTouchExplorationEnabledLocked = true;
                break;
            }
        }
        if (isTouchExplorationEnabledLocked != accessibilityUserState.isTouchExplorationEnabledLocked()) {
            accessibilityUserState.setTouchExplorationEnabledLocked(isTouchExplorationEnabledLocked);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "touch_exploration_enabled", isTouchExplorationEnabledLocked ? 1 : 0, accessibilityUserState.mUserId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        accessibilityUserState.resetServiceDetectsGestures();
        java.util.ArrayList<android.view.Display> validDisplayList = getValidDisplayList();
        java.util.Iterator<com.android.server.accessibility.AccessibilityServiceConnection> it = accessibilityUserState.mBoundServices.iterator();
        while (it.hasNext()) {
            com.android.server.accessibility.AccessibilityServiceConnection next = it.next();
            java.util.Iterator<android.view.Display> it2 = validDisplayList.iterator();
            while (it2.hasNext()) {
                int displayId = it2.next().getDisplayId();
                if (next.isServiceDetectsGesturesEnabled(displayId)) {
                    accessibilityUserState.setServiceDetectsGesturesEnabled(displayId, true);
                }
            }
        }
        accessibilityUserState.setServiceHandlesDoubleTapLocked(z);
        accessibilityUserState.setMultiFingerGesturesLocked(z2);
        accessibilityUserState.setTwoFingerPassthroughLocked(z3);
        accessibilityUserState.setSendMotionEventsEnabled(z4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readAccessibilityShortcutKeySettingLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "accessibility_shortcut_target_service", accessibilityUserState.mUserId);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        readColonDelimitedStringToSet(stringForUser, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda35
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$readAccessibilityShortcutKeySettingLocked$21;
                lambda$readAccessibilityShortcutKeySettingLocked$21 = com.android.server.accessibility.AccessibilityManagerService.lambda$readAccessibilityShortcutKeySettingLocked$21((java.lang.String) obj);
                return lambda$readAccessibilityShortcutKeySettingLocked$21;
            }
        }, arraySet, false);
        if (stringForUser == null) {
            java.lang.String string = this.mContext.getString(android.R.string.config_customCountryDetector);
            if (!android.text.TextUtils.isEmpty(string)) {
                arraySet.add(string);
            }
        }
        java.util.Set shortcutTargetsLocked = accessibilityUserState.getShortcutTargetsLocked(1);
        if (arraySet.equals(shortcutTargetsLocked)) {
            return false;
        }
        shortcutTargetsLocked.clear();
        shortcutTargetsLocked.addAll(arraySet);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$readAccessibilityShortcutKeySettingLocked$21(java.lang.String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readAccessibilityButtonTargetsLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        readColonDelimitedSettingToSet("accessibility_button_targets", accessibilityUserState.mUserId, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$readAccessibilityButtonTargetsLocked$22;
                lambda$readAccessibilityButtonTargetsLocked$22 = com.android.server.accessibility.AccessibilityManagerService.lambda$readAccessibilityButtonTargetsLocked$22((java.lang.String) obj);
                return lambda$readAccessibilityButtonTargetsLocked$22;
            }
        }, arraySet);
        android.util.ArraySet<java.lang.String> shortcutTargetsLocked = accessibilityUserState.getShortcutTargetsLocked(0);
        if (arraySet.equals(shortcutTargetsLocked)) {
            return false;
        }
        shortcutTargetsLocked.clear();
        shortcutTargetsLocked.addAll((java.util.Collection<? extends java.lang.String>) arraySet);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$readAccessibilityButtonTargetsLocked$22(java.lang.String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readAccessibilityButtonTargetComponentLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "accessibility_button_target_component", accessibilityUserState.mUserId);
        if (android.text.TextUtils.isEmpty(stringForUser)) {
            if (accessibilityUserState.getTargetAssignedToAccessibilityButton() == null) {
                return false;
            }
            accessibilityUserState.setTargetAssignedToAccessibilityButton(null);
            return true;
        }
        if (stringForUser.equals(accessibilityUserState.getTargetAssignedToAccessibilityButton())) {
            return false;
        }
        accessibilityUserState.setTargetAssignedToAccessibilityButton(stringForUser);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readUserRecommendedUiTimeoutSettingsLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int intForUser = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_non_interactive_ui_timeout_ms", 0, accessibilityUserState.mUserId);
        int intForUser2 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_interactive_ui_timeout_ms", 0, accessibilityUserState.mUserId);
        this.mProxyManager.updateTimeoutsIfNeeded(intForUser, intForUser2);
        if (intForUser == accessibilityUserState.getUserNonInteractiveUiTimeoutLocked() && intForUser2 == accessibilityUserState.getUserInteractiveUiTimeoutLocked()) {
            return false;
        }
        accessibilityUserState.setUserNonInteractiveUiTimeoutLocked(intForUser);
        accessibilityUserState.setUserInteractiveUiTimeoutLocked(intForUser2);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
        return true;
    }

    private void updateAccessibilityShortcutKeyTargetsLocked(final com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        android.util.ArraySet<java.lang.String> shortcutTargetsLocked = accessibilityUserState.getShortcutTargetsLocked(1);
        int size = shortcutTargetsLocked.size();
        if (size == 0) {
            return;
        }
        shortcutTargetsLocked.removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda61
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateAccessibilityShortcutKeyTargetsLocked$23;
                lambda$updateAccessibilityShortcutKeyTargetsLocked$23 = com.android.server.accessibility.AccessibilityManagerService.lambda$updateAccessibilityShortcutKeyTargetsLocked$23(com.android.server.accessibility.AccessibilityUserState.this, (java.lang.String) obj);
                return lambda$updateAccessibilityShortcutKeyTargetsLocked$23;
            }
        });
        if (size == shortcutTargetsLocked.size()) {
            return;
        }
        persistColonDelimitedSetToSettingLocked("accessibility_shortcut_target_service", accessibilityUserState.mUserId, shortcutTargetsLocked, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda62
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$updateAccessibilityShortcutKeyTargetsLocked$24;
                lambda$updateAccessibilityShortcutKeyTargetsLocked$24 = com.android.server.accessibility.AccessibilityManagerService.lambda$updateAccessibilityShortcutKeyTargetsLocked$24((java.lang.String) obj);
                return lambda$updateAccessibilityShortcutKeyTargetsLocked$24;
            }
        });
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateAccessibilityShortcutKeyTargetsLocked$23(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, java.lang.String str) {
        return !accessibilityUserState.isShortcutTargetInstalledLocked(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$updateAccessibilityShortcutKeyTargetsLocked$24(java.lang.String str) {
        return str;
    }

    private boolean canRequestAndRequestsTouchExplorationLocked(com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection, com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        if (!accessibilityServiceConnection.canReceiveEventsLocked() || !accessibilityServiceConnection.mRequestTouchExplorationMode) {
            return false;
        }
        if (accessibilityServiceConnection.getServiceInfo().getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 17) {
            if (accessibilityUserState.mTouchExplorationGrantedServices.contains(accessibilityServiceConnection.mComponentName)) {
                return true;
            }
            if (this.mEnableTouchExplorationDialog == null || !this.mEnableTouchExplorationDialog.isShowing()) {
                this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda64
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((com.android.server.accessibility.AccessibilityManagerService) obj).showEnableTouchExplorationDialog((com.android.server.accessibility.AccessibilityServiceConnection) obj2);
                    }
                }, this, accessibilityServiceConnection));
            }
        } else if ((accessibilityServiceConnection.getCapabilities() & 2) != 0) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMagnificationLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        if (accessibilityUserState.mUserId != this.mCurrentUserId) {
            return;
        }
        if (this.mUiAutomationManager.suppressingAccessibilityServicesLocked() && this.mMagnificationController.isFullScreenMagnificationControllerInitialized()) {
            getMagnificationController().getFullScreenMagnificationController().unregisterAll();
            return;
        }
        java.util.ArrayList<android.view.Display> validDisplayList = getValidDisplayList();
        int i = 0;
        if (!accessibilityUserState.isMagnificationSingleFingerTripleTapEnabledLocked()) {
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
            if (!accessibilityUserState.isShortcutMagnificationEnabledLocked()) {
                while (i < validDisplayList.size()) {
                    int displayId = validDisplayList.get(i).getDisplayId();
                    if (userHasListeningMagnificationServicesLocked(accessibilityUserState, displayId)) {
                        getMagnificationController().getFullScreenMagnificationController().register(displayId);
                    } else if (this.mMagnificationController.isFullScreenMagnificationControllerInitialized()) {
                        getMagnificationController().getFullScreenMagnificationController().unregister(displayId);
                    }
                    i++;
                }
                return;
            }
        }
        while (i < validDisplayList.size()) {
            getMagnificationController().getFullScreenMagnificationController().register(validDisplayList.get(i).getDisplayId());
            i++;
        }
    }

    private void updateMagnificationConnectionIfNeeded(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z;
        if (!this.mMagnificationController.supportWindowMagnification()) {
            return;
        }
        if (accessibilityUserState.isShortcutMagnificationEnabledLocked() || accessibilityUserState.isMagnificationSingleFingerTripleTapEnabledLocked()) {
            z = true;
        } else {
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
            z = false;
        }
        getMagnificationConnectionManager().requestConnection((z && (com.android.window.flags.Flags.magnificationAlwaysDrawFullscreenBorder() || accessibilityUserState.getMagnificationCapabilitiesLocked() != 1)) || userHasMagnificationServicesLocked(accessibilityUserState));
    }

    private boolean userHasMagnificationServicesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = accessibilityUserState.mBoundServices;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (this.mSecurityPolicy.canControlMagnification(arrayList.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean userHasListeningMagnificationServicesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, int i) {
        java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = accessibilityUserState.mBoundServices;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = arrayList.get(i2);
            if (this.mSecurityPolicy.canControlMagnification(accessibilityServiceConnection) && accessibilityServiceConnection.isMagnificationCallbackEnabled(i)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFingerprintGestureHandling(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList;
        synchronized (this.mLock) {
            try {
                arrayList = accessibilityUserState.mBoundServices;
                if (this.mFingerprintGestureDispatcher == null && this.mPackageManager.hasSystemFeature("android.hardware.fingerprint")) {
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        if (arrayList.get(i).isCapturingFingerprintGestures()) {
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                android.hardware.fingerprint.IFingerprintService asInterface = android.hardware.fingerprint.IFingerprintService.Stub.asInterface(android.os.ServiceManager.getService("fingerprint"));
                                if (asInterface != null) {
                                    this.mFingerprintGestureDispatcher = new com.android.server.accessibility.FingerprintGestureDispatcher(asInterface, this.mContext.getResources(), this.mLock);
                                    break;
                                }
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        i++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (this.mFingerprintGestureDispatcher != null) {
            this.mFingerprintGestureDispatcher.updateClientList(arrayList);
        }
    }

    private void updateAccessibilityButtonTargetsLocked(final com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        for (int size = accessibilityUserState.mBoundServices.size() - 1; size >= 0; size--) {
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = accessibilityUserState.mBoundServices.get(size);
            if (accessibilityServiceConnection.mRequestAccessibilityButton) {
                accessibilityServiceConnection.notifyAccessibilityButtonAvailabilityChangedLocked(accessibilityServiceConnection.isAccessibilityButtonAvailableLocked(accessibilityUserState));
            }
        }
        android.util.ArraySet<java.lang.String> shortcutTargetsLocked = accessibilityUserState.getShortcutTargetsLocked(0);
        int size2 = shortcutTargetsLocked.size();
        if (size2 == 0) {
            return;
        }
        shortcutTargetsLocked.removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda50
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateAccessibilityButtonTargetsLocked$25;
                lambda$updateAccessibilityButtonTargetsLocked$25 = com.android.server.accessibility.AccessibilityManagerService.lambda$updateAccessibilityButtonTargetsLocked$25(com.android.server.accessibility.AccessibilityUserState.this, (java.lang.String) obj);
                return lambda$updateAccessibilityButtonTargetsLocked$25;
            }
        });
        if (size2 == shortcutTargetsLocked.size()) {
            return;
        }
        persistColonDelimitedSetToSettingLocked("accessibility_button_targets", accessibilityUserState.mUserId, shortcutTargetsLocked, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda51
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$updateAccessibilityButtonTargetsLocked$26;
                lambda$updateAccessibilityButtonTargetsLocked$26 = com.android.server.accessibility.AccessibilityManagerService.lambda$updateAccessibilityButtonTargetsLocked$26((java.lang.String) obj);
                return lambda$updateAccessibilityButtonTargetsLocked$26;
            }
        });
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateAccessibilityButtonTargetsLocked$25(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, java.lang.String str) {
        return !accessibilityUserState.isShortcutTargetInstalledLocked(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$updateAccessibilityButtonTargetsLocked$26(java.lang.String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void migrateAccessibilityButtonSettingsIfNecessaryLocked(final com.android.server.accessibility.AccessibilityUserState accessibilityUserState, @android.annotation.Nullable final java.lang.String str, int i) {
        if (i > 29) {
            return;
        }
        final android.util.ArraySet<java.lang.String> shortcutTargetsLocked = accessibilityUserState.getShortcutTargetsLocked(0);
        int size = shortcutTargetsLocked.size();
        shortcutTargetsLocked.removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$27;
                lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$27 = com.android.server.accessibility.AccessibilityManagerService.lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$27(str, accessibilityUserState, (java.lang.String) obj);
                return lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$27;
            }
        });
        boolean z = size != shortcutTargetsLocked.size();
        int size2 = shortcutTargetsLocked.size();
        final android.util.ArraySet<java.lang.String> shortcutTargetsLocked2 = accessibilityUserState.getShortcutTargetsLocked(1);
        accessibilityUserState.mEnabledServices.forEach(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.accessibility.AccessibilityManagerService.lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$28(str, accessibilityUserState, shortcutTargetsLocked, shortcutTargetsLocked2, (android.content.ComponentName) obj);
            }
        });
        if (!(z | (size2 != shortcutTargetsLocked.size()))) {
            return;
        }
        persistColonDelimitedSetToSettingLocked("accessibility_button_targets", accessibilityUserState.mUserId, shortcutTargetsLocked, new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$29;
                lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$29 = com.android.server.accessibility.AccessibilityManagerService.lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$29((java.lang.String) obj);
                return lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$29;
            }
        });
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$27(java.lang.String str, com.android.server.accessibility.AccessibilityUserState accessibilityUserState, java.lang.String str2) {
        android.content.ComponentName unflattenFromString;
        android.accessibilityservice.AccessibilityServiceInfo installedServiceInfoLocked;
        if ((str != null && str2 != null && !str2.contains(str)) || (unflattenFromString = android.content.ComponentName.unflattenFromString(str2)) == null || (installedServiceInfoLocked = accessibilityUserState.getInstalledServiceInfoLocked(unflattenFromString)) == null) {
            return false;
        }
        if (installedServiceInfoLocked.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 29) {
            android.util.Slog.v(LOG_TAG, "Legacy service " + unflattenFromString + " should not in the button");
            return true;
        }
        if (!((installedServiceInfoLocked.flags & 256) != 0) || accessibilityUserState.mEnabledServices.contains(unflattenFromString)) {
            return false;
        }
        android.util.Slog.v(LOG_TAG, "Service requesting a11y button and be assigned to the button" + unflattenFromString + " should be enabled state");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$28(java.lang.String str, com.android.server.accessibility.AccessibilityUserState accessibilityUserState, java.util.Set set, java.util.Set set2, android.content.ComponentName componentName) {
        android.accessibilityservice.AccessibilityServiceInfo installedServiceInfoLocked;
        if ((str != null && componentName != null && !str.equals(componentName.getPackageName())) || (installedServiceInfoLocked = accessibilityUserState.getInstalledServiceInfoLocked(componentName)) == null) {
            return;
        }
        boolean z = (installedServiceInfoLocked.flags & 256) != 0;
        if (installedServiceInfoLocked.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 29 || !z) {
            return;
        }
        java.lang.String flattenToString = componentName.flattenToString();
        if (android.text.TextUtils.isEmpty(flattenToString) || com.android.server.accessibility.AccessibilityUserState.doesShortcutTargetsStringContain(set, flattenToString) || com.android.server.accessibility.AccessibilityUserState.doesShortcutTargetsStringContain(set2, flattenToString)) {
            return;
        }
        android.util.Slog.v(LOG_TAG, "A enabled service requesting a11y button " + componentName + " should be assign to the button or shortcut.");
        set.add(flattenToString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$migrateAccessibilityButtonSettingsIfNecessaryLocked$29(java.lang.String str) {
        return str;
    }

    private void removeShortcutTargetForUnboundServiceLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        if (!accessibilityServiceConnection.mRequestAccessibilityButton || accessibilityServiceConnection.getServiceInfo().getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 29) {
            return;
        }
        android.content.ComponentName componentName = accessibilityServiceConnection.getComponentName();
        if (accessibilityUserState.removeShortcutTargetLocked(1, componentName)) {
            persistColonDelimitedSetToSettingLocked("accessibility_shortcut_target_service", accessibilityUserState.mUserId, accessibilityUserState.getShortcutTargetsLocked(1), new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda13
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$removeShortcutTargetForUnboundServiceLocked$30;
                    lambda$removeShortcutTargetForUnboundServiceLocked$30 = com.android.server.accessibility.AccessibilityManagerService.lambda$removeShortcutTargetForUnboundServiceLocked$30((java.lang.String) obj);
                    return lambda$removeShortcutTargetForUnboundServiceLocked$30;
                }
            });
        }
        if (accessibilityUserState.removeShortcutTargetLocked(0, componentName)) {
            persistColonDelimitedSetToSettingLocked("accessibility_button_targets", accessibilityUserState.mUserId, accessibilityUserState.getShortcutTargetsLocked(0), new java.util.function.Function() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda14
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$removeShortcutTargetForUnboundServiceLocked$31;
                    lambda$removeShortcutTargetForUnboundServiceLocked$31 = com.android.server.accessibility.AccessibilityManagerService.lambda$removeShortcutTargetForUnboundServiceLocked$31((java.lang.String) obj);
                    return lambda$removeShortcutTargetForUnboundServiceLocked$31;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$removeShortcutTargetForUnboundServiceLocked$30(java.lang.String str) {
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$removeShortcutTargetForUnboundServiceLocked$31(java.lang.String str) {
        return str;
    }

    private void updateRecommendedUiTimeoutLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int userNonInteractiveUiTimeoutLocked = accessibilityUserState.getUserNonInteractiveUiTimeoutLocked();
        int userInteractiveUiTimeoutLocked = accessibilityUserState.getUserInteractiveUiTimeoutLocked();
        if (userNonInteractiveUiTimeoutLocked == 0 || userInteractiveUiTimeoutLocked == 0) {
            java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = accessibilityUserState.mBoundServices;
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                int interactiveUiTimeoutMillis = arrayList.get(i3).getServiceInfo().getInteractiveUiTimeoutMillis();
                if (i < interactiveUiTimeoutMillis) {
                    i = interactiveUiTimeoutMillis;
                }
                int nonInteractiveUiTimeoutMillis = arrayList.get(i3).getServiceInfo().getNonInteractiveUiTimeoutMillis();
                if (i2 < nonInteractiveUiTimeoutMillis) {
                    i2 = nonInteractiveUiTimeoutMillis;
                }
            }
            if (userNonInteractiveUiTimeoutLocked == 0) {
                userNonInteractiveUiTimeoutLocked = i2;
            }
            if (userInteractiveUiTimeoutLocked == 0) {
                userInteractiveUiTimeoutLocked = i;
            }
        }
        accessibilityUserState.setNonInteractiveUiTimeoutLocked(userNonInteractiveUiTimeoutLocked);
        accessibilityUserState.setInteractiveUiTimeoutLocked(userInteractiveUiTimeoutLocked);
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public com.android.server.accessibility.KeyEventDispatcher getKeyEventDispatcher() {
        if (this.mKeyEventDispatcher == null) {
            this.mKeyEventDispatcher = new com.android.server.accessibility.KeyEventDispatcher(this.mMainHandler, 8, this.mLock, this.mPowerManager);
        }
        return this.mKeyEventDispatcher;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public android.app.PendingIntent getPendingIntentActivity(android.content.Context context, int i, android.content.Intent intent, int i2) {
        return android.app.PendingIntent.getActivity(context, i, intent, i2);
    }

    public void performAccessibilityShortcut(java.lang.String str) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.performAccessibilityShortcut", 4L, "targetName=" + str);
        }
        if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000 && this.mContext.checkCallingPermission("android.permission.MANAGE_ACCESSIBILITY") != 0) {
            throw new java.lang.SecurityException("performAccessibilityShortcut requires the MANAGE_ACCESSIBILITY permission");
        }
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda29(), this, 0, 1, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performAccessibilityShortcutInternal(int i, int i2, @android.annotation.Nullable java.lang.String str) {
        java.util.List<java.lang.String> accessibilityShortcutTargetsInternal = getAccessibilityShortcutTargetsInternal(i2);
        if (accessibilityShortcutTargetsInternal.isEmpty()) {
            android.util.Slog.d(LOG_TAG, "No target to perform shortcut, shortcutType=" + i2);
            return;
        }
        if (str != null && !com.android.server.accessibility.AccessibilityUserState.doesShortcutTargetsStringContain(accessibilityShortcutTargetsInternal, str)) {
            android.util.Slog.v(LOG_TAG, "Perform shortcut failed, invalid target name:" + str);
            str = null;
        }
        if (str == null) {
            if (accessibilityShortcutTargetsInternal.size() > 1) {
                showAccessibilityTargetsSelection(i, i2);
                return;
            }
            str = accessibilityShortcutTargetsInternal.get(0);
        }
        if (str.equals("com.android.server.accessibility.MagnificationController")) {
            com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, com.android.internal.accessibility.AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME, i2, !getMagnificationController().getFullScreenMagnificationController().isActivated(i));
            sendAccessibilityButtonToInputFilter(i);
            return;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            android.util.Slog.d(LOG_TAG, "Perform shortcut failed, invalid target name:" + str);
            return;
        }
        if (performAccessibilityFrameworkFeature(i, unflattenFromString, i2)) {
            return;
        }
        if (performAccessibilityShortcutTargetActivity(i, unflattenFromString)) {
            com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, unflattenFromString, i2);
        } else {
            performAccessibilityShortcutTargetService(i, i2, unflattenFromString);
        }
    }

    private boolean performAccessibilityFrameworkFeature(int i, android.content.ComponentName componentName, int i2) {
        java.util.Map frameworkShortcutFeaturesMap = com.android.internal.accessibility.AccessibilityShortcutController.getFrameworkShortcutFeaturesMap();
        if (!frameworkShortcutFeaturesMap.containsKey(componentName)) {
            return false;
        }
        com.android.internal.accessibility.AccessibilityShortcutController.FrameworkFeatureInfo frameworkFeatureInfo = (com.android.internal.accessibility.AccessibilityShortcutController.FrameworkFeatureInfo) frameworkShortcutFeaturesMap.get(componentName);
        android.provider.SettingsStringUtil.SettingStringHelper settingStringHelper = new android.provider.SettingsStringUtil.SettingStringHelper(this.mContext.getContentResolver(), frameworkFeatureInfo.getSettingKey(), this.mCurrentUserId);
        if (frameworkFeatureInfo instanceof com.android.internal.accessibility.AccessibilityShortcutController.LaunchableFrameworkFeatureInfo) {
            com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, true);
            launchAccessibilityFrameworkFeature(i, componentName);
            return true;
        }
        if (!android.text.TextUtils.equals(frameworkFeatureInfo.getSettingOnValue(), settingStringHelper.read())) {
            com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, true);
            settingStringHelper.write(frameworkFeatureInfo.getSettingOnValue());
        } else {
            com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, false);
            settingStringHelper.write(frameworkFeatureInfo.getSettingOffValue());
        }
        return true;
    }

    private boolean performAccessibilityShortcutTargetActivity(int i, android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                for (int i2 = 0; i2 < currentUserStateLocked.mInstalledShortcuts.size(); i2++) {
                    if (currentUserStateLocked.mInstalledShortcuts.get(i2).getComponentName().equals(componentName)) {
                        launchShortcutTargetActivity(i, componentName);
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean performAccessibilityShortcutTargetService(int i, int i2, android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                android.accessibilityservice.AccessibilityServiceInfo installedServiceInfoLocked = currentUserStateLocked.getInstalledServiceInfoLocked(componentName);
                if (installedServiceInfoLocked == null) {
                    android.util.Slog.d(LOG_TAG, "Perform shortcut failed, invalid component name:" + componentName);
                    return false;
                }
                com.android.server.accessibility.AccessibilityServiceConnection serviceConnectionLocked = currentUserStateLocked.getServiceConnectionLocked(componentName);
                int i3 = installedServiceInfoLocked.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
                boolean z = (installedServiceInfoLocked.flags & 256) != 0;
                if ((i3 <= 29 && i2 == 1) || (i3 > 29 && !z)) {
                    if (serviceConnectionLocked != null) {
                        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, false);
                        disableAccessibilityServiceLocked(componentName, this.mCurrentUserId);
                    } else {
                        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, true);
                        enableAccessibilityServiceLocked(componentName, this.mCurrentUserId);
                    }
                    return true;
                }
                if (i2 == 1 && i3 > 29 && z && !currentUserStateLocked.getEnabledServicesLocked().contains(componentName)) {
                    enableAccessibilityServiceLocked(componentName, this.mCurrentUserId);
                    return true;
                }
                if (serviceConnectionLocked != null) {
                    if (currentUserStateLocked.mBoundServices.contains(serviceConnectionLocked) && serviceConnectionLocked.mRequestAccessibilityButton) {
                        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, true);
                        serviceConnectionLocked.notifyAccessibilityButtonClickedLocked(i);
                        return true;
                    }
                }
                android.util.Slog.d(LOG_TAG, "Perform shortcut failed, service is not ready:" + componentName);
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void launchAccessibilityFrameworkFeature(int i, android.content.ComponentName componentName) {
        if (componentName.equals(com.android.internal.accessibility.AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME)) {
            launchAccessibilitySubSettings(i, com.android.internal.accessibility.AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME);
        }
    }

    public java.util.List<java.lang.String> getAccessibilityShortcutTargets(int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getAccessibilityShortcutTargets", 4L, "shortcutType=" + i);
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ACCESSIBILITY") != 0) {
            throw new java.lang.SecurityException("getAccessibilityShortcutService requires the MANAGE_ACCESSIBILITY permission");
        }
        return getAccessibilityShortcutTargetsInternal(i);
    }

    private java.util.List<java.lang.String> getAccessibilityShortcutTargetsInternal(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                java.util.ArrayList arrayList = new java.util.ArrayList(currentUserStateLocked.getShortcutTargetsLocked(i));
                if (i != 0) {
                    return arrayList;
                }
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (accessibilityServiceConnection.mRequestAccessibilityButton && accessibilityServiceConnection.getServiceInfo().getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 29) {
                        java.lang.String flattenToString = accessibilityServiceConnection.getComponentName().flattenToString();
                        if (!android.text.TextUtils.isEmpty(flattenToString)) {
                            arrayList.add(flattenToString);
                        }
                    }
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void enableAccessibilityServiceLocked(android.content.ComponentName componentName, int i) {
        this.mTempComponentNameSet.clear();
        readComponentNamesFromSettingLocked("enabled_accessibility_services", i, this.mTempComponentNameSet);
        this.mTempComponentNameSet.add(componentName);
        persistComponentNamesToSettingLocked("enabled_accessibility_services", this.mTempComponentNameSet, i);
        com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked.mEnabledServices.add(componentName)) {
            onUserStateChangedLocked(userStateLocked);
        }
    }

    private void disableAccessibilityServiceLocked(android.content.ComponentName componentName, int i) {
        this.mTempComponentNameSet.clear();
        readComponentNamesFromSettingLocked("enabled_accessibility_services", i, this.mTempComponentNameSet);
        this.mTempComponentNameSet.remove(componentName);
        persistComponentNamesToSettingLocked("enabled_accessibility_services", this.mTempComponentNameSet, i);
        com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked.mEnabledServices.remove(componentName)) {
            onUserStateChangedLocked(userStateLocked);
        }
    }

    @Override // com.android.server.accessibility.AccessibilityWindowManager.AccessibilityEventSender
    public void sendAccessibilityEventForCurrentUserLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getWindowChanges() == 1) {
            sendPendingWindowStateChangedEventsForAvailableWindowLocked(accessibilityEvent.getWindowId());
        }
        sendAccessibilityEventLocked(accessibilityEvent, this.mCurrentUserId);
    }

    private void sendAccessibilityEventLocked(android.view.accessibility.AccessibilityEvent accessibilityEvent, int i) {
        accessibilityEvent.setEventTime(android.os.SystemClock.uptimeMillis());
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda25
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).sendAccessibilityEvent((android.view.accessibility.AccessibilityEvent) obj2, ((java.lang.Integer) obj3).intValue());
            }
        }, this, accessibilityEvent, java.lang.Integer.valueOf(i)));
    }

    public boolean sendFingerprintGesture(int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(131076L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.sendFingerprintGesture", 131076L, "gestureKeyCode=" + i);
        }
        synchronized (this.mLock) {
            if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000) {
                throw new java.lang.SecurityException("Only SYSTEM can call sendFingerprintGesture");
            }
        }
        if (this.mFingerprintGestureDispatcher == null) {
            return false;
        }
        return this.mFingerprintGestureDispatcher.onFingerprintGesture(i);
    }

    public int getAccessibilityWindowId(@android.annotation.Nullable android.os.IBinder iBinder) {
        int findWindowIdLocked;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getAccessibilityWindowId", 4L, "windowToken=" + iBinder);
        }
        synchronized (this.mLock) {
            try {
                if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000) {
                    throw new java.lang.SecurityException("Only SYSTEM can call getAccessibilityWindowId");
                }
                findWindowIdLocked = this.mA11yWindowManager.findWindowIdLocked(this.mCurrentUserId, iBinder);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return findWindowIdLocked;
    }

    public long getRecommendedTimeoutMillis() {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getRecommendedTimeoutMillis", 4L);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(android.os.Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return this.mProxyManager.getRecommendedTimeoutMillisLocked(firstDeviceIdForUidLocked);
                }
                return getRecommendedTimeoutMillisLocked(getCurrentUserStateLocked());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private long getRecommendedTimeoutMillisLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        return com.android.internal.util.IntPair.of(accessibilityUserState.getInteractiveUiTimeoutLocked(), accessibilityUserState.getNonInteractiveUiTimeoutLocked());
    }

    public void setMagnificationConnection(android.view.accessibility.IMagnificationConnection iMagnificationConnection) throws android.os.RemoteException {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(132L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.setMagnificationConnection", 132L, "connection=" + iMagnificationConnection);
        }
        this.mSecurityPolicy.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE");
        getMagnificationConnectionManager().setConnection(iMagnificationConnection);
    }

    public com.android.server.accessibility.magnification.MagnificationConnectionManager getMagnificationConnectionManager() {
        com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager;
        synchronized (this.mLock) {
            magnificationConnectionManager = this.mMagnificationController.getMagnificationConnectionManager();
        }
        return magnificationConnectionManager;
    }

    com.android.server.accessibility.magnification.MagnificationController getMagnificationController() {
        return this.mMagnificationController;
    }

    public void associateEmbeddedHierarchy(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.associateEmbeddedHierarchy", 4L, "host=" + iBinder + ";embedded=" + iBinder2);
        }
        synchronized (this.mLock) {
            this.mA11yWindowManager.associateEmbeddedHierarchyLocked(iBinder, iBinder2);
        }
    }

    public void disassociateEmbeddedHierarchy(@android.annotation.NonNull android.os.IBinder iBinder) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.disassociateEmbeddedHierarchy", 4L, "token=" + iBinder);
        }
        synchronized (this.mLock) {
            this.mA11yWindowManager.disassociateEmbeddedHierarchyLocked(iBinder);
        }
    }

    public int getFocusStrokeWidth() {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getFocusStrokeWidth", 4L);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(android.os.Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return this.mProxyManager.getFocusStrokeWidthLocked(firstDeviceIdForUidLocked);
                }
                return getCurrentUserStateLocked().getFocusStrokeWidthLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getFocusColor() {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getFocusColor", 4L);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(android.os.Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return this.mProxyManager.getFocusColorLocked(firstDeviceIdForUidLocked);
                }
                return getCurrentUserStateLocked().getFocusColorLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAudioDescriptionByDefaultEnabled() {
        boolean isAudioDescriptionByDefaultEnabledLocked;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.isAudioDescriptionByDefaultEnabled", 4L);
        }
        synchronized (this.mLock) {
            isAudioDescriptionByDefaultEnabledLocked = getCurrentUserStateLocked().isAudioDescriptionByDefaultEnabledLocked();
        }
        return isAudioDescriptionByDefaultEnabledLocked;
    }

    public void setAccessibilityWindowAttributes(int i, int i2, int i3, android.view.accessibility.AccessibilityWindowAttributes accessibilityWindowAttributes) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.setAccessibilityWindowAttributes", 4L);
        }
        this.mA11yWindowManager.setAccessibilityWindowAttributes(i, i2, i3, accessibilityWindowAttributes);
    }

    @android.annotation.RequiresPermission("android.permission.SET_SYSTEM_AUDIO_CAPTION")
    public void setSystemAudioCaptioningEnabled(boolean z, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SET_SYSTEM_AUDIO_CAPTION", "setSystemAudioCaptioningEnabled");
        this.mCaptioningManagerImpl.setSystemAudioCaptioningEnabled(z, i);
    }

    public boolean isSystemAudioCaptioningUiEnabled(int i) {
        return this.mCaptioningManagerImpl.isSystemAudioCaptioningUiEnabled(i);
    }

    @android.annotation.RequiresPermission("android.permission.SET_SYSTEM_AUDIO_CAPTION")
    public void setSystemAudioCaptioningUiEnabled(boolean z, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SET_SYSTEM_AUDIO_CAPTION", "setSystemAudioCaptioningUiEnabled");
        this.mCaptioningManagerImpl.setSystemAudioCaptioningUiEnabled(z, i);
    }

    public boolean registerProxyForDisplay(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws android.os.RemoteException {
        this.mSecurityPolicy.enforceCallingOrSelfPermission("android.permission.CREATE_VIRTUAL_DEVICE");
        this.mSecurityPolicy.checkForAccessibilityPermissionOrRole();
        if (iAccessibilityServiceClient == null) {
            return false;
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("The display id " + i + " is invalid.");
        }
        if (!isTrackedDisplay(i)) {
            throw new java.lang.IllegalArgumentException("The display " + i + " does not exist or is not tracked by accessibility.");
        }
        if (this.mProxyManager.isProxyedDisplay(i)) {
            throw new java.lang.IllegalArgumentException("The display " + i + " is already being proxy-ed");
        }
        if (!this.mProxyManager.displayBelongsToCaller(android.os.Binder.getCallingUid(), i)) {
            throw new java.lang.SecurityException("The display " + i + " does not belong to the caller.");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.accessibility.ProxyManager proxyManager = this.mProxyManager;
            int i2 = sIdCounter;
            sIdCounter = i2 + 1;
            proxyManager.registerProxy(iAccessibilityServiceClient, i, i2, this.mSecurityPolicy, this, getTraceManager(), this.mWindowManagerService);
            synchronized (this.mLock) {
                notifyClearAccessibilityCacheLocked();
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean unregisterProxyForDisplay(int i) {
        this.mSecurityPolicy.enforceCallingOrSelfPermission("android.permission.CREATE_VIRTUAL_DEVICE");
        this.mSecurityPolicy.checkForAccessibilityPermissionOrRole();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mProxyManager.unregisterProxy(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean isDisplayProxyed(int i) {
        return this.mProxyManager.isProxyedDisplay(i);
    }

    public boolean startFlashNotificationSequence(java.lang.String str, int i, android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mFlashNotificationsController.startFlashNotificationSequence(str, i, iBinder);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean stopFlashNotificationSequence(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mFlashNotificationsController.stopFlashNotificationSequence(str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean startFlashNotificationEvent(java.lang.String str, int i, java.lang.String str2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mFlashNotificationsController.startFlashNotificationEvent(str, i, str2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isAccessibilityTargetAllowed(java.lang.String str, int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.List permittedAccessibilityServices = ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getPermittedAccessibilityServices(i2);
            boolean z = true;
            if (!(permittedAccessibilityServices == null || permittedAccessibilityServices.contains(str))) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            if (!android.permission.flags.Flags.enhancedConfirmationModeApisEnabled() || !android.security.Flags.extendEcmToAllSettings()) {
                try {
                    int noteOpNoThrow = ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).noteOpNoThrow(119, i, str);
                    if (this.mContext.getResources().getBoolean(android.R.bool.config_enable_emergency_call_while_sim_locked) && noteOpNoThrow != 0) {
                        z = false;
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return z;
                } catch (java.lang.Exception e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            }
            try {
                boolean z2 = !((android.app.ecm.EnhancedConfirmationManager) this.mContext.getSystemService(android.app.ecm.EnhancedConfirmationManager.class)).isRestricted(str, "android:bind_accessibility_service");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return z2;
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                android.util.Log.e(LOG_TAG, "Exception when retrieving package:" + str, e2);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean sendRestrictedDialogIntent(java.lang.String str, int i, int i2) {
        if (isAccessibilityTargetAllowed(str, i, i2)) {
            return false;
        }
        com.android.settingslib.RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed = com.android.server.accessibility.RestrictedLockUtilsInternal.checkIfAccessibilityServiceDisallowed(this.mContext, str, i2);
        if (checkIfAccessibilityServiceDisallowed != null) {
            com.android.settingslib.RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mContext, checkIfAccessibilityServiceDisallowed);
            return true;
        }
        if (android.permission.flags.Flags.enhancedConfirmationModeApisEnabled() && android.security.Flags.extendEcmToAllSettings()) {
            try {
                this.mContext.startActivity(((android.app.ecm.EnhancedConfirmationManager) this.mContext.getSystemService(android.app.ecm.EnhancedConfirmationManager.class)).createRestrictedSettingDialogIntent(str, "android:bind_accessibility_service"));
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(LOG_TAG, "Exception when retrieving package:" + str, e);
            }
        } else {
            com.android.settingslib.RestrictedLockUtils.sendShowRestrictedSettingDialogIntent(this.mContext, str, i);
        }
        return true;
    }

    public boolean isAccessibilityServiceWarningRequired(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        this.mSecurityPolicy.enforceCallingOrSelfPermission("android.permission.MANAGE_ACCESSIBILITY");
        android.content.ComponentName componentName = accessibilityServiceInfo.getComponentName();
        synchronized (this.mLock) {
            try {
                if (getCurrentUserStateLocked().getEnabledServicesLocked().contains(componentName)) {
                    return false;
                }
                for (int i : android.view.accessibility.AccessibilityManager.SHORTCUT_TYPES) {
                    if (getAccessibilityShortcutTargets(i).contains(componentName.flattenToString())) {
                        return false;
                    }
                }
                return (android.view.accessibility.Flags.skipAccessibilityWarningDialogForTrustedServices() && isAccessibilityServicePreinstalledAndTrusted(accessibilityServiceInfo)) ? false : true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isAccessibilityServicePreinstalledAndTrusted(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        final android.content.ComponentName componentName = accessibilityServiceInfo.getComponentName();
        if (accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.isSystemApp()) {
            java.util.stream.Stream map = java.util.Arrays.stream(this.mContext.getResources().getStringArray(android.R.array.config_tether_usb_regexs)).map(new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda56());
            java.util.Objects.requireNonNull(componentName);
            if (map.anyMatch(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda57
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return componentName.equals((android.content.ComponentName) obj);
                }
            })) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, LOG_TAG, printWriter)) {
            synchronized (this.mLock) {
                try {
                    printWriter.println("ACCESSIBILITY MANAGER (dumpsys accessibility)");
                    printWriter.println();
                    printWriter.append("currentUserId=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mCurrentUserId));
                    if (this.mRealCurrentUserId != -2 && this.mCurrentUserId != this.mRealCurrentUserId) {
                        printWriter.append(" (set for UiAutomation purposes; \"real\" current user is ").append((java.lang.CharSequence) java.lang.String.valueOf(this.mRealCurrentUserId)).append(")");
                    }
                    printWriter.println();
                    if (this.mVisibleBgUserIds != null) {
                        printWriter.append("visibleBgUserIds=").append((java.lang.CharSequence) this.mVisibleBgUserIds.toString());
                        printWriter.println();
                    }
                    printWriter.append("hasMagnificationConnection=").append((java.lang.CharSequence) java.lang.String.valueOf(getMagnificationConnectionManager().isConnected()));
                    printWriter.println();
                    this.mMagnificationProcessor.dump(printWriter, getValidDisplayList());
                    int size = this.mUserStates.size();
                    for (int i = 0; i < size; i++) {
                        this.mUserStates.valueAt(i).dump(fileDescriptor, printWriter, strArr);
                    }
                    if (this.mUiAutomationManager.isUiAutomationRunningLocked()) {
                        this.mUiAutomationManager.dumpUiAutomationService(fileDescriptor, printWriter, strArr);
                        printWriter.println();
                    }
                    this.mA11yWindowManager.dump(fileDescriptor, printWriter, strArr);
                    if (this.mHasInputFilter && this.mInputFilter != null) {
                        this.mInputFilter.dump(fileDescriptor, printWriter, strArr);
                    }
                    printWriter.println("Global client list info:{");
                    this.mGlobalClients.dump(printWriter, "    Client list ");
                    printWriter.println("    Registered clients:{");
                    for (int i2 = 0; i2 < this.mGlobalClients.getRegisteredCallbackCount(); i2++) {
                        printWriter.append((java.lang.CharSequence) java.util.Arrays.toString(((com.android.server.accessibility.AccessibilityManagerService.Client) this.mGlobalClients.getRegisteredCallbackCookie(i2)).mPackageNames));
                    }
                    printWriter.println();
                    this.mProxyManager.dump(fileDescriptor, printWriter, strArr);
                    this.mA11yDisplayListener.dump(fileDescriptor, printWriter, strArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    final class MainHandler extends android.os.Handler {
        public static final int MSG_SEND_KEY_EVENT_TO_INPUT_FILTER = 8;

        public MainHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 8) {
                android.view.KeyEvent keyEvent = (android.view.KeyEvent) message.obj;
                int i = message.arg1;
                synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                    try {
                        if (com.android.server.accessibility.AccessibilityManagerService.this.mHasInputFilter && com.android.server.accessibility.AccessibilityManagerService.this.mInputFilter != null) {
                            com.android.server.accessibility.AccessibilityManagerService.this.mInputFilter.sendInputEvent(keyEvent, i);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                keyEvent.recycle();
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public com.android.server.accessibility.magnification.MagnificationProcessor getMagnificationProcessor() {
        return this.mMagnificationProcessor;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void onClientChangeLocked(boolean z) {
        onClientChangeLocked(z, false);
    }

    public void onClientChangeLocked(boolean z, boolean z2) {
        com.android.server.accessibility.AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
        onUserStateChangedLocked(userStateLocked, z2);
        if (z) {
            scheduleNotifyClientsOfServicesStateChangeLocked(userStateLocked);
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void onProxyChanged(int i) {
        this.mProxyManager.onProxyChanged(i);
    }

    @Override // com.android.server.accessibility.ProxyManager.SystemSupport
    public void removeDeviceIdLocked(int i) {
        resetClientsLocked(i, getCurrentUserStateLocked().mUserClients);
        resetClientsLocked(i, this.mGlobalClients);
        onClientChangeLocked(true, true);
    }

    private void resetClientsLocked(int i, android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> remoteCallbackList) {
        if (remoteCallbackList == null || remoteCallbackList.getRegisteredCallbackCount() == 0) {
            return;
        }
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
                try {
                    com.android.server.accessibility.AccessibilityManagerService.Client client = (com.android.server.accessibility.AccessibilityManagerService.Client) remoteCallbackList.getRegisteredCallbackCookie(i2);
                    if (client.mDeviceId == i) {
                        client.mDeviceId = 0;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.server.accessibility.ProxyManager.SystemSupport
    public void updateWindowsForAccessibilityCallbackLocked() {
        updateWindowsForAccessibilityCallbackLocked(getUserStateLocked(this.mCurrentUserId));
    }

    @Override // com.android.server.accessibility.ProxyManager.SystemSupport
    public android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> getGlobalClientsLocked() {
        return this.mGlobalClients;
    }

    @Override // com.android.server.accessibility.ProxyManager.SystemSupport
    public android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> getCurrentUserClientsLocked() {
        return getCurrentUserState().mUserClients;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.accessibility.AccessibilityShellCommand(this.mContext, this, this.mSystemActionPerformer).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private final class InteractionBridge {
        private final android.content.ComponentName COMPONENT_NAME = new android.content.ComponentName("com.android.server.accessibility", "InteractionBridge");
        private final android.view.accessibility.AccessibilityInteractionClient mClient;
        private final int mConnectionId;
        private final android.view.Display mDefaultDisplay;

        public InteractionBridge() {
            com.android.server.accessibility.AccessibilityUserState currentUserStateLocked;
            android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = new android.accessibilityservice.AccessibilityServiceInfo();
            accessibilityServiceInfo.setCapabilities(1);
            accessibilityServiceInfo.flags |= 64;
            accessibilityServiceInfo.flags |= 2;
            accessibilityServiceInfo.setAccessibilityTool(true);
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                currentUserStateLocked = com.android.server.accessibility.AccessibilityManagerService.this.getCurrentUserStateLocked();
            }
            android.content.Context context = com.android.server.accessibility.AccessibilityManagerService.this.mContext;
            android.content.ComponentName componentName = this.COMPONENT_NAME;
            int i = com.android.server.accessibility.AccessibilityManagerService.sIdCounter;
            com.android.server.accessibility.AccessibilityManagerService.sIdCounter = i + 1;
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = new com.android.server.accessibility.AccessibilityServiceConnection(currentUserStateLocked, context, componentName, accessibilityServiceInfo, i, com.android.server.accessibility.AccessibilityManagerService.this.mMainHandler, com.android.server.accessibility.AccessibilityManagerService.this.mLock, com.android.server.accessibility.AccessibilityManagerService.this.mSecurityPolicy, com.android.server.accessibility.AccessibilityManagerService.this, com.android.server.accessibility.AccessibilityManagerService.this.getTraceManager(), com.android.server.accessibility.AccessibilityManagerService.this.mWindowManagerService, com.android.server.accessibility.AccessibilityManagerService.this.getSystemActionPerformer(), com.android.server.accessibility.AccessibilityManagerService.this.mA11yWindowManager, com.android.server.accessibility.AccessibilityManagerService.this.mActivityTaskManagerService) { // from class: com.android.server.accessibility.AccessibilityManagerService.InteractionBridge.1
                @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
                public boolean supportsFlagForNotImportantViews(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo2) {
                    return true;
                }
            };
            this.mConnectionId = accessibilityServiceConnection.mId;
            this.mClient = android.view.accessibility.AccessibilityInteractionClient.getInstance(com.android.server.accessibility.AccessibilityManagerService.this.mContext);
            android.view.accessibility.AccessibilityInteractionClient.addConnection(this.mConnectionId, accessibilityServiceConnection, false);
            this.mDefaultDisplay = ((android.hardware.display.DisplayManager) com.android.server.accessibility.AccessibilityManagerService.this.mContext.getSystemService("display")).getDisplay(0);
        }

        boolean getAccessibilityFocusClickPointInScreen(android.graphics.Point point) {
            return com.android.server.accessibility.AccessibilityManagerService.this.getInteractionBridge().getAccessibilityFocusClickPointInScreenNotLocked(point);
        }

        public boolean performActionOnAccessibilityFocusedItemNotLocked(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
            android.view.accessibility.AccessibilityNodeInfo accessibilityFocusNotLocked = getAccessibilityFocusNotLocked();
            if (accessibilityFocusNotLocked == null || !accessibilityFocusNotLocked.getActionList().contains(accessibilityAction)) {
                return false;
            }
            return accessibilityFocusNotLocked.performAction(accessibilityAction.getId());
        }

        public boolean getAccessibilityFocusClickPointInScreenNotLocked(android.graphics.Point point) {
            android.view.MagnificationSpec magnificationSpec;
            android.view.accessibility.AccessibilityNodeInfo accessibilityFocusNotLocked = getAccessibilityFocusNotLocked();
            if (accessibilityFocusNotLocked == null) {
                return false;
            }
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    android.graphics.Rect rect = com.android.server.accessibility.AccessibilityManagerService.this.mTempRect;
                    accessibilityFocusNotLocked.getBoundsInScreen(rect);
                    android.graphics.Point point2 = new android.graphics.Point(rect.centerX(), rect.centerY());
                    android.util.Pair<float[], android.view.MagnificationSpec> windowTransformationMatrixAndMagnificationSpec = com.android.server.accessibility.AccessibilityManagerService.this.getWindowTransformationMatrixAndMagnificationSpec(accessibilityFocusNotLocked.getWindowId());
                    if (windowTransformationMatrixAndMagnificationSpec != null && windowTransformationMatrixAndMagnificationSpec.second != null) {
                        magnificationSpec = new android.view.MagnificationSpec();
                        magnificationSpec.setTo((android.view.MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second);
                    } else {
                        magnificationSpec = null;
                    }
                    if (magnificationSpec != null && !magnificationSpec.isNop()) {
                        rect.offset((int) (-magnificationSpec.offsetX), (int) (-magnificationSpec.offsetY));
                        rect.scale(1.0f / magnificationSpec.scale);
                    }
                    android.graphics.Rect rect2 = com.android.server.accessibility.AccessibilityManagerService.this.mTempRect1;
                    com.android.server.accessibility.AccessibilityManagerService.this.getWindowBounds(accessibilityFocusNotLocked.getWindowId(), rect2);
                    if (!rect.intersect(rect2)) {
                        return false;
                    }
                    android.graphics.Point point3 = com.android.server.accessibility.AccessibilityManagerService.this.mTempPoint;
                    this.mDefaultDisplay.getRealSize(point3);
                    if (!rect.intersect(0, 0, point3.x, point3.y)) {
                        return false;
                    }
                    point.set(point2.x, point2.y);
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private android.view.accessibility.AccessibilityNodeInfo getAccessibilityFocusNotLocked() {
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    int focusedWindowId = com.android.server.accessibility.AccessibilityManagerService.this.mA11yWindowManager.getFocusedWindowId(2);
                    if (focusedWindowId == -1) {
                        return null;
                    }
                    return getAccessibilityFocusNotLocked(focusedWindowId);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private android.view.accessibility.AccessibilityNodeInfo getAccessibilityFocusNotLocked(int i) {
            return this.mClient.findFocus(this.mConnectionId, i, android.view.accessibility.AccessibilityNodeInfo.ROOT_NODE_ID, 2);
        }
    }

    public java.util.ArrayList<android.view.Display> getValidDisplayList() {
        return this.mA11yDisplayListener.getValidDisplayList();
    }

    private boolean isTrackedDisplay(int i) {
        java.util.Iterator<android.view.Display> it = getValidDisplayList().iterator();
        while (it.hasNext()) {
            if (it.next().getDisplayId() == i) {
                return true;
            }
        }
        return false;
    }

    public class AccessibilityDisplayListener implements android.hardware.display.DisplayManager.DisplayListener {
        private final android.hardware.display.DisplayManager mDisplayManager;
        private final java.util.ArrayList<android.view.Display> mDisplaysList = new java.util.ArrayList<>();
        private int mSystemUiUid;

        AccessibilityDisplayListener(android.content.Context context, android.os.Handler handler) {
            this.mSystemUiUid = 0;
            com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
            this.mDisplayManager = (android.hardware.display.DisplayManager) context.getSystemService("display");
            this.mDisplayManager.registerDisplayListener(this, handler);
            initializeDisplayList();
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            if (packageManagerInternal != null) {
                this.mSystemUiUid = packageManagerInternal.getPackageUid(packageManagerInternal.getSystemUiServiceComponent().getPackageName(), 1048576L, com.android.server.accessibility.AccessibilityManagerService.this.mCurrentUserId);
            }
        }

        public java.util.ArrayList<android.view.Display> getValidDisplayList() {
            java.util.ArrayList<android.view.Display> arrayList;
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                arrayList = this.mDisplaysList;
            }
            return arrayList;
        }

        private void initializeDisplayList() {
            android.view.Display[] displays = this.mDisplayManager.getDisplays();
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    this.mDisplaysList.clear();
                    for (android.view.Display display : displays) {
                        if (isValidDisplay(display)) {
                            this.mDisplaysList.add(display);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
            android.view.Display display = this.mDisplayManager.getDisplay(i);
            if (!isValidDisplay(display)) {
                return;
            }
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    this.mDisplaysList.add(display);
                    com.android.server.accessibility.AccessibilityManagerService.this.mA11yOverlayLayers.put(i, com.android.server.accessibility.AccessibilityManagerService.this.mWindowManagerService.getA11yOverlayLayer(i));
                    if (com.android.server.accessibility.AccessibilityManagerService.this.mInputFilter != null) {
                        com.android.server.accessibility.AccessibilityManagerService.this.mInputFilter.onDisplayAdded(display);
                    }
                    com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = com.android.server.accessibility.AccessibilityManagerService.this.getCurrentUserStateLocked();
                    com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
                    java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = currentUserStateLocked.mBoundServices;
                    if (i != 0) {
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            arrayList.get(i2).addWindowTokenForDisplay(i);
                        }
                    }
                    com.android.server.accessibility.AccessibilityManagerService.this.updateMagnificationLocked(currentUserStateLocked);
                    com.android.server.accessibility.AccessibilityManagerService.this.updateWindowsForAccessibilityCallbackLocked(currentUserStateLocked);
                    com.android.server.accessibility.AccessibilityManagerService.this.notifyClearAccessibilityCacheLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    if (removeDisplayFromList(i)) {
                        com.android.server.accessibility.AccessibilityManagerService.this.mA11yOverlayLayers.remove(i);
                        if (com.android.server.accessibility.AccessibilityManagerService.this.mInputFilter != null) {
                            com.android.server.accessibility.AccessibilityManagerService.this.mInputFilter.onDisplayRemoved(i);
                        }
                        com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = com.android.server.accessibility.AccessibilityManagerService.this.getCurrentUserStateLocked();
                        if (i != 0) {
                            java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = currentUserStateLocked.mBoundServices;
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                arrayList.get(i2).onDisplayRemoved(i);
                            }
                        }
                        com.android.server.accessibility.AccessibilityManagerService.this.mMagnificationController.onDisplayRemoved(i);
                        com.android.server.accessibility.AccessibilityManagerService.this.mA11yWindowManager.stopTrackingWindows(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean removeDisplayFromList(int i) {
            for (int i2 = 0; i2 < this.mDisplaysList.size(); i2++) {
                if (this.mDisplaysList.get(i2).getDisplayId() == i) {
                    this.mDisplaysList.remove(i2);
                    return true;
                }
            }
            return false;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
        }

        void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            printWriter.println("Accessibility Display Listener:");
            printWriter.println("    SystemUI uid: " + this.mSystemUiUid);
            int size = this.mDisplaysList.size();
            printWriter.printf("    %d valid display%s: ", java.lang.Integer.valueOf(size), size == 1 ? "" : "s");
            for (int i = 0; i < size; i++) {
                printWriter.print(this.mDisplaysList.get(i).getDisplayId());
                if (i < size - 1) {
                    printWriter.print(", ");
                }
            }
            printWriter.println();
        }

        private boolean isValidDisplay(@android.annotation.Nullable android.view.Display display) {
            if (display == null || display.getType() == 4) {
                return false;
            }
            if (display.getType() == 5 && (display.getFlags() & 4) != 0 && display.getOwnerUid() != this.mSystemUiUid) {
                return false;
            }
            return true;
        }
    }

    class Client {
        final android.view.accessibility.IAccessibilityManagerClient mCallback;
        int mDeviceId;
        int mLastSentRelevantEventTypes;
        final java.lang.String[] mPackageNames;
        int mUid;

        private Client(android.view.accessibility.IAccessibilityManagerClient iAccessibilityManagerClient, int i, com.android.server.accessibility.AccessibilityUserState accessibilityUserState, int i2) {
            this.mDeviceId = 0;
            this.mCallback = iAccessibilityManagerClient;
            this.mPackageNames = com.android.server.accessibility.AccessibilityManagerService.this.mPackageManager.getPackagesForUid(i);
            this.mUid = i;
            this.mDeviceId = i2;
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    if (com.android.server.accessibility.AccessibilityManagerService.this.mProxyManager.isProxyedDeviceId(i2)) {
                        this.mLastSentRelevantEventTypes = com.android.server.accessibility.AccessibilityManagerService.this.mProxyManager.computeRelevantEventTypesLocked(this);
                    } else {
                        this.mLastSentRelevantEventTypes = com.android.server.accessibility.AccessibilityManagerService.this.computeRelevantEventTypesLocked(accessibilityUserState, this);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class AccessibilityContentObserver extends android.database.ContentObserver {
        private final android.net.Uri mAccessibilityButtonComponentIdUri;
        private final android.net.Uri mAccessibilityButtonTargetsUri;
        private final android.net.Uri mAccessibilityShortcutServiceIdUri;
        private final android.net.Uri mAccessibilitySoftKeyboardModeUri;
        private final android.net.Uri mAlwaysOnMagnificationUri;
        private final android.net.Uri mAudioDescriptionByDefaultUri;
        private final android.net.Uri mAutoclickEnabledUri;
        private final android.net.Uri mEnabledAccessibilityServicesUri;
        private final android.net.Uri mHighTextContrastUri;
        private final android.net.Uri mMagnificationCapabilityUri;
        private final android.net.Uri mMagnificationFollowTypingUri;
        private final android.net.Uri mMagnificationModeUri;
        private final android.net.Uri mMagnificationTwoFingerTripleTapEnabledUri;
        private final android.net.Uri mMagnificationmSingleFingerTripleTapEnabledUri;
        private final android.net.Uri mShowImeWithHardKeyboardUri;
        private final android.net.Uri mTouchExplorationEnabledUri;
        private final android.net.Uri mTouchExplorationGrantedAccessibilityServicesUri;
        private final android.net.Uri mUserInteractiveUiTimeoutUri;
        private final android.net.Uri mUserNonInteractiveUiTimeoutUri;

        public AccessibilityContentObserver(android.os.Handler handler) {
            super(handler);
            this.mTouchExplorationEnabledUri = android.provider.Settings.Secure.getUriFor("touch_exploration_enabled");
            this.mMagnificationmSingleFingerTripleTapEnabledUri = android.provider.Settings.Secure.getUriFor("accessibility_display_magnification_enabled");
            this.mMagnificationTwoFingerTripleTapEnabledUri = android.provider.Settings.Secure.getUriFor("accessibility_magnification_two_finger_triple_tap_enabled");
            this.mAutoclickEnabledUri = android.provider.Settings.Secure.getUriFor("accessibility_autoclick_enabled");
            this.mEnabledAccessibilityServicesUri = android.provider.Settings.Secure.getUriFor("enabled_accessibility_services");
            this.mTouchExplorationGrantedAccessibilityServicesUri = android.provider.Settings.Secure.getUriFor("touch_exploration_granted_accessibility_services");
            this.mHighTextContrastUri = android.provider.Settings.Secure.getUriFor("high_text_contrast_enabled");
            this.mAudioDescriptionByDefaultUri = android.provider.Settings.Secure.getUriFor("enabled_accessibility_audio_description_by_default");
            this.mAccessibilitySoftKeyboardModeUri = android.provider.Settings.Secure.getUriFor("accessibility_soft_keyboard_mode");
            this.mShowImeWithHardKeyboardUri = android.provider.Settings.Secure.getUriFor("show_ime_with_hard_keyboard");
            this.mAccessibilityShortcutServiceIdUri = android.provider.Settings.Secure.getUriFor("accessibility_shortcut_target_service");
            this.mAccessibilityButtonComponentIdUri = android.provider.Settings.Secure.getUriFor("accessibility_button_target_component");
            this.mAccessibilityButtonTargetsUri = android.provider.Settings.Secure.getUriFor("accessibility_button_targets");
            this.mUserNonInteractiveUiTimeoutUri = android.provider.Settings.Secure.getUriFor("accessibility_non_interactive_ui_timeout_ms");
            this.mUserInteractiveUiTimeoutUri = android.provider.Settings.Secure.getUriFor("accessibility_interactive_ui_timeout_ms");
            this.mMagnificationModeUri = android.provider.Settings.Secure.getUriFor("accessibility_magnification_mode");
            this.mMagnificationCapabilityUri = android.provider.Settings.Secure.getUriFor("accessibility_magnification_capability");
            this.mMagnificationFollowTypingUri = android.provider.Settings.Secure.getUriFor("accessibility_magnification_follow_typing_enabled");
            this.mAlwaysOnMagnificationUri = android.provider.Settings.Secure.getUriFor("accessibility_magnification_always_on_enabled");
        }

        public void register(android.content.ContentResolver contentResolver) {
            contentResolver.registerContentObserver(this.mTouchExplorationEnabledUri, false, this, -1);
            contentResolver.registerContentObserver(this.mMagnificationmSingleFingerTripleTapEnabledUri, false, this, -1);
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
            contentResolver.registerContentObserver(this.mAutoclickEnabledUri, false, this, -1);
            contentResolver.registerContentObserver(this.mEnabledAccessibilityServicesUri, false, this, -1);
            contentResolver.registerContentObserver(this.mTouchExplorationGrantedAccessibilityServicesUri, false, this, -1);
            contentResolver.registerContentObserver(this.mHighTextContrastUri, false, this, -1);
            contentResolver.registerContentObserver(this.mAudioDescriptionByDefaultUri, false, this, -1);
            contentResolver.registerContentObserver(this.mAccessibilitySoftKeyboardModeUri, false, this, -1);
            contentResolver.registerContentObserver(this.mShowImeWithHardKeyboardUri, false, this, -1);
            contentResolver.registerContentObserver(this.mAccessibilityShortcutServiceIdUri, false, this, -1);
            contentResolver.registerContentObserver(this.mAccessibilityButtonComponentIdUri, false, this, -1);
            contentResolver.registerContentObserver(this.mAccessibilityButtonTargetsUri, false, this, -1);
            contentResolver.registerContentObserver(this.mUserNonInteractiveUiTimeoutUri, false, this, -1);
            contentResolver.registerContentObserver(this.mUserInteractiveUiTimeoutUri, false, this, -1);
            contentResolver.registerContentObserver(this.mMagnificationModeUri, false, this, -1);
            contentResolver.registerContentObserver(this.mMagnificationCapabilityUri, false, this, -1);
            contentResolver.registerContentObserver(this.mMagnificationFollowTypingUri, false, this, -1);
            contentResolver.registerContentObserver(this.mAlwaysOnMagnificationUri, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                try {
                    com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = com.android.server.accessibility.AccessibilityManagerService.this.getCurrentUserStateLocked();
                    if (this.mTouchExplorationEnabledUri.equals(uri)) {
                        if (com.android.server.accessibility.AccessibilityManagerService.this.readTouchExplorationEnabledSettingLocked(currentUserStateLocked)) {
                            com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                        }
                    } else if (this.mMagnificationmSingleFingerTripleTapEnabledUri.equals(uri)) {
                        if (com.android.server.accessibility.AccessibilityManagerService.this.readMagnificationEnabledSettingsLocked(currentUserStateLocked)) {
                            com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                        }
                    } else {
                        com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
                        if (this.mAutoclickEnabledUri.equals(uri)) {
                            if (com.android.server.accessibility.AccessibilityManagerService.this.readAutoclickEnabledSettingLocked(currentUserStateLocked)) {
                                com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                            }
                        } else if (this.mEnabledAccessibilityServicesUri.equals(uri)) {
                            if (com.android.server.accessibility.AccessibilityManagerService.this.readEnabledAccessibilityServicesLocked(currentUserStateLocked)) {
                                com.android.server.accessibility.AccessibilityManagerService.this.mSecurityPolicy.onEnabledServicesChangedLocked(currentUserStateLocked.mUserId, currentUserStateLocked.mEnabledServices);
                                currentUserStateLocked.removeDisabledServicesFromTemporaryStatesLocked();
                                com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                            }
                        } else if (this.mTouchExplorationGrantedAccessibilityServicesUri.equals(uri)) {
                            if (com.android.server.accessibility.AccessibilityManagerService.this.readTouchExplorationGrantedAccessibilityServicesLocked(currentUserStateLocked)) {
                                com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                            }
                        } else if (this.mHighTextContrastUri.equals(uri)) {
                            if (com.android.server.accessibility.AccessibilityManagerService.this.readHighTextContrastEnabledSettingLocked(currentUserStateLocked)) {
                                com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                            }
                        } else if (this.mAudioDescriptionByDefaultUri.equals(uri)) {
                            if (com.android.server.accessibility.AccessibilityManagerService.this.readAudioDescriptionEnabledSettingLocked(currentUserStateLocked)) {
                                com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                            }
                        } else {
                            if (!this.mAccessibilitySoftKeyboardModeUri.equals(uri) && !this.mShowImeWithHardKeyboardUri.equals(uri)) {
                                if (this.mAccessibilityShortcutServiceIdUri.equals(uri)) {
                                    if (com.android.server.accessibility.AccessibilityManagerService.this.readAccessibilityShortcutKeySettingLocked(currentUserStateLocked)) {
                                        com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                                    }
                                } else if (this.mAccessibilityButtonComponentIdUri.equals(uri)) {
                                    if (com.android.server.accessibility.AccessibilityManagerService.this.readAccessibilityButtonTargetComponentLocked(currentUserStateLocked)) {
                                        com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                                    }
                                } else if (this.mAccessibilityButtonTargetsUri.equals(uri)) {
                                    if (com.android.server.accessibility.AccessibilityManagerService.this.readAccessibilityButtonTargetsLocked(currentUserStateLocked)) {
                                        com.android.server.accessibility.AccessibilityManagerService.this.onUserStateChangedLocked(currentUserStateLocked);
                                    }
                                } else {
                                    if (!this.mUserNonInteractiveUiTimeoutUri.equals(uri) && !this.mUserInteractiveUiTimeoutUri.equals(uri)) {
                                        if (this.mMagnificationModeUri.equals(uri)) {
                                            if (com.android.server.accessibility.AccessibilityManagerService.this.readMagnificationModeForDefaultDisplayLocked(currentUserStateLocked)) {
                                                com.android.server.accessibility.AccessibilityManagerService.this.updateMagnificationModeChangeSettingsLocked(currentUserStateLocked, 0);
                                            }
                                        } else if (this.mMagnificationCapabilityUri.equals(uri)) {
                                            if (com.android.server.accessibility.AccessibilityManagerService.this.readMagnificationCapabilitiesLocked(currentUserStateLocked)) {
                                                com.android.server.accessibility.AccessibilityManagerService.this.updateMagnificationCapabilitiesSettingsChangeLocked(currentUserStateLocked);
                                            }
                                        } else if (this.mMagnificationFollowTypingUri.equals(uri)) {
                                            com.android.server.accessibility.AccessibilityManagerService.this.readMagnificationFollowTypingLocked(currentUserStateLocked);
                                        } else if (this.mAlwaysOnMagnificationUri.equals(uri)) {
                                            com.android.server.accessibility.AccessibilityManagerService.this.readAlwaysOnMagnificationLocked(currentUserStateLocked);
                                        }
                                    }
                                    com.android.server.accessibility.AccessibilityManagerService.this.readUserRecommendedUiTimeoutSettingsLocked(currentUserStateLocked);
                                }
                            }
                            currentUserStateLocked.reconcileSoftKeyboardModeWithSettingsLocked();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
    
        if (r6.isShortcutMagnificationEnabledLocked() != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateMagnificationCapabilitiesSettingsChangeLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        java.util.ArrayList<android.view.Display> validDisplayList = getValidDisplayList();
        for (int i = 0; i < validDisplayList.size(); i++) {
            int displayId = validDisplayList.get(i).getDisplayId();
            if (fallBackMagnificationModeSettingsLocked(accessibilityUserState, displayId)) {
                updateMagnificationModeChangeSettingsLocked(accessibilityUserState, displayId);
            }
        }
        updateMagnificationConnectionIfNeeded(accessibilityUserState);
        if (!accessibilityUserState.isMagnificationSingleFingerTripleTapEnabledLocked()) {
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        }
        if (accessibilityUserState.getMagnificationCapabilitiesLocked() == 3) {
            return;
        }
        for (int i2 = 0; i2 < validDisplayList.size(); i2++) {
            getMagnificationConnectionManager().removeMagnificationButton(validDisplayList.get(i2).getDisplayId());
        }
    }

    private boolean fallBackMagnificationModeSettingsLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, int i) {
        if (accessibilityUserState.isValidMagnificationModeLocked(i)) {
            return false;
        }
        android.util.Slog.w(LOG_TAG, "displayId " + i + ", invalid magnification mode:" + accessibilityUserState.getMagnificationModeLocked(i));
        int magnificationCapabilitiesLocked = accessibilityUserState.getMagnificationCapabilitiesLocked();
        accessibilityUserState.setMagnificationModeLocked(i, magnificationCapabilitiesLocked);
        if (i == 0) {
            persistMagnificationModeSettingsLocked(magnificationCapabilitiesLocked);
            return true;
        }
        return true;
    }

    private void persistMagnificationModeSettingsLocked(final int i) {
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.AccessibilityManagerService.this.lambda$persistMagnificationModeSettingsLocked$32(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$persistMagnificationModeSettingsLocked$32(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_mode", i, this.mCurrentUserId);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getMagnificationMode(int i) {
        int magnificationModeLocked;
        synchronized (this.mLock) {
            magnificationModeLocked = getCurrentUserStateLocked().getMagnificationModeLocked(i);
        }
        return magnificationModeLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readMagnificationModeForDefaultDisplayLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int intForUser = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_mode", 1, accessibilityUserState.mUserId);
        if (intForUser == accessibilityUserState.getMagnificationModeLocked(0)) {
            return false;
        }
        accessibilityUserState.setMagnificationModeLocked(0, intForUser);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean readMagnificationCapabilitiesLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        int intForUser = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_capability", 1, accessibilityUserState.mUserId);
        if (intForUser != accessibilityUserState.getMagnificationCapabilitiesLocked()) {
            accessibilityUserState.setMagnificationCapabilitiesLocked(intForUser);
            this.mMagnificationController.setMagnificationCapabilities(intForUser);
            return true;
        }
        return false;
    }

    boolean readMagnificationFollowTypingLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_follow_typing_enabled", 1, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.isMagnificationFollowTypingEnabled()) {
            return false;
        }
        accessibilityUserState.setMagnificationFollowTypingEnabled(z);
        this.mMagnificationController.setMagnificationFollowTypingEnabled(z);
        return true;
    }

    public void updateAlwaysOnMagnification() {
        synchronized (this.mLock) {
            readAlwaysOnMagnificationLocked(getCurrentUserState());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean readAlwaysOnMagnificationLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        boolean z = this.mMagnificationController.isAlwaysOnMagnificationFeatureFlagEnabled() && (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_always_on_enabled", 1, accessibilityUserState.mUserId) == 1);
        if (z == accessibilityUserState.isAlwaysOnMagnificationEnabled()) {
            return false;
        }
        accessibilityUserState.setAlwaysOnMagnificationEnabled(z);
        this.mMagnificationController.setAlwaysOnMagnificationEnabled(z);
        return true;
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void setGestureDetectionPassthroughRegion(int i, android.graphics.Region region) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda12
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).setGestureDetectionPassthroughRegionInternal(((java.lang.Integer) obj2).intValue(), (android.graphics.Region) obj3);
            }
        }, this, java.lang.Integer.valueOf(i), region));
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void setTouchExplorationPassthroughRegion(int i, android.graphics.Region region) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda9
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).setTouchExplorationPassthroughRegionInternal(((java.lang.Integer) obj2).intValue(), (android.graphics.Region) obj3);
            }
        }, this, java.lang.Integer.valueOf(i), region));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTouchExplorationPassthroughRegionInternal(int i, android.graphics.Region region) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.setTouchExplorationPassthroughRegion(i, region);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGestureDetectionPassthroughRegionInternal(int i, android.graphics.Region region) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.setGestureDetectionPassthroughRegion(i, region);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void setServiceDetectsGesturesEnabled(int i, boolean z) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda20
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).setServiceDetectsGesturesInternal(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue());
            }
        }, this, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setServiceDetectsGesturesInternal(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                getCurrentUserStateLocked().setServiceDetectsGesturesEnabled(i, z);
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.setServiceDetectsGesturesEnabled(i, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void requestTouchExploration(int i) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda36
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).requestTouchExplorationInternal(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestTouchExplorationInternal(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.requestTouchExploration(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void requestDragging(int i, int i2) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda21
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).requestDraggingInternal(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
            }
        }, this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestDraggingInternal(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.requestDragging(i, i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void requestDelegating(int i) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda59
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).requestDelegatingInternal(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestDelegatingInternal(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.requestDelegating(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void onDoubleTap(int i) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda45
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).onDoubleTapInternal(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDoubleTapInternal(int i) {
        com.android.server.accessibility.AccessibilityInputFilter accessibilityInputFilter;
        synchronized (this.mLock) {
            try {
                accessibilityInputFilter = (!this.mHasInputFilter || this.mInputFilter == null) ? null : this.mInputFilter;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (accessibilityInputFilter != null) {
            accessibilityInputFilter.onDoubleTap(i);
        }
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void onDoubleTapAndHold(int i) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda26
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).onDoubleTapAndHoldInternal(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(i)));
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void requestImeLocked(com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        if (!(abstractAccessibilityServiceConnection instanceof com.android.server.accessibility.AccessibilityServiceConnection) || (abstractAccessibilityServiceConnection instanceof com.android.server.accessibility.ProxyAccessibilityServiceConnection)) {
            return;
        }
        com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = (com.android.server.accessibility.AccessibilityServiceConnection) abstractAccessibilityServiceConnection;
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda43
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).createSessionForConnection((com.android.server.accessibility.AccessibilityServiceConnection) obj2);
            }
        }, this, accessibilityServiceConnection));
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda44
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).bindAndStartInputForConnection((com.android.server.accessibility.AccessibilityServiceConnection) obj2);
            }
        }, this, accessibilityServiceConnection));
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void unbindImeLocked(com.android.server.accessibility.AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        if (!(abstractAccessibilityServiceConnection instanceof com.android.server.accessibility.AccessibilityServiceConnection) || (abstractAccessibilityServiceConnection instanceof com.android.server.accessibility.ProxyAccessibilityServiceConnection)) {
            return;
        }
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda49
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).unbindInputForConnection((com.android.server.accessibility.AccessibilityServiceConnection) obj2);
            }
        }, this, (com.android.server.accessibility.AccessibilityServiceConnection) abstractAccessibilityServiceConnection));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSessionForConnection(com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        synchronized (this.mLock) {
            try {
                if (this.mInputSessionRequested) {
                    accessibilityServiceConnection.createImeSessionLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindAndStartInputForConnection(com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        synchronized (this.mLock) {
            try {
                if (this.mInputBound) {
                    accessibilityServiceConnection.bindInputLocked();
                    accessibilityServiceConnection.startInputLocked(this.mRemoteInputConnection, this.mEditorInfo, this.mRestarting);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindInputForConnection(com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        com.android.server.inputmethod.InputMethodManagerInternal.get().unbindAccessibilityFromCurrentClient(accessibilityServiceConnection.mId, accessibilityServiceConnection.mUserId);
        synchronized (this.mLock) {
            accessibilityServiceConnection.unbindInputLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDoubleTapAndHoldInternal(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && this.mInputFilter != null) {
                    this.mInputFilter.onDoubleTapAndHold(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateFocusAppearanceDataLocked(final com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        if (accessibilityUserState.mUserId != this.mCurrentUserId) {
            return;
        }
        if (this.mTraceManager.isA11yTracingEnabledForTypes(2L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.updateFocusAppearanceDataLocked", 2L, "userState=" + accessibilityUserState);
        }
        this.mMainHandler.post(new java.lang.Runnable() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.AccessibilityManagerService.this.lambda$updateFocusAppearanceDataLocked$34(accessibilityUserState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFocusAppearanceDataLocked$34(final com.android.server.accessibility.AccessibilityUserState accessibilityUserState) {
        broadcastToClients(accessibilityUserState, com.android.internal.util.FunctionalUtils.ignoreRemoteException(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda48
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.accessibility.AccessibilityManagerService.this.lambda$updateFocusAppearanceDataLocked$33(accessibilityUserState, (com.android.server.accessibility.AccessibilityManagerService.Client) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFocusAppearanceDataLocked$33(com.android.server.accessibility.AccessibilityUserState accessibilityUserState, com.android.server.accessibility.AccessibilityManagerService.Client client) throws android.os.RemoteException {
        if (!this.mProxyManager.isProxyedDeviceId(client.mDeviceId)) {
            client.mCallback.setFocusAppearance(accessibilityUserState.getFocusStrokeWidthLocked(), accessibilityUserState.getFocusColorLocked());
        }
    }

    public com.android.server.accessibility.AccessibilityTraceManager getTraceManager() {
        return this.mTraceManager;
    }

    public void scheduleBindInput() {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).bindInput();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindInput() {
        synchronized (this.mLock) {
            try {
                this.mInputBound = true;
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (accessibilityServiceConnection.requestImeApis()) {
                        accessibilityServiceConnection.bindInputLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void scheduleUnbindInput() {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda52
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).unbindInput();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindInput() {
        synchronized (this.mLock) {
            try {
                this.mInputBound = false;
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (accessibilityServiceConnection.requestImeApis()) {
                        accessibilityServiceConnection.unbindInputLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void scheduleStartInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda33
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).startInput((com.android.internal.inputmethod.IRemoteAccessibilityInputConnection) obj2, (android.view.inputmethod.EditorInfo) obj3, ((java.lang.Boolean) obj4).booleanValue());
            }
        }, this, iRemoteAccessibilityInputConnection, editorInfo, java.lang.Boolean.valueOf(z)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        synchronized (this.mLock) {
            try {
                this.mRemoteInputConnection = iRemoteAccessibilityInputConnection;
                this.mEditorInfo = editorInfo;
                this.mRestarting = z;
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (accessibilityServiceConnection.requestImeApis()) {
                        accessibilityServiceConnection.startInputLocked(iRemoteAccessibilityInputConnection, editorInfo, z);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void scheduleCreateImeSession(android.util.ArraySet<java.lang.Integer> arraySet) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).createImeSession((android.util.ArraySet) obj2);
            }
        }, this, arraySet));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createImeSession(android.util.ArraySet<java.lang.Integer> arraySet) {
        synchronized (this.mLock) {
            try {
                this.mInputSessionRequested = true;
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (!arraySet.contains(java.lang.Integer.valueOf(accessibilityServiceConnection.mId)) && accessibilityServiceConnection.requestImeApis()) {
                        accessibilityServiceConnection.createImeSessionLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void scheduleSetImeSessionEnabled(android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray, boolean z) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda65
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.accessibility.AccessibilityManagerService) obj).setImeSessionEnabled((android.util.SparseArray) obj2, ((java.lang.Boolean) obj3).booleanValue());
            }
        }, this, sparseArray, java.lang.Boolean.valueOf(z)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImeSessionEnabled(android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray, boolean z) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.AccessibilityUserState currentUserStateLocked = getCurrentUserStateLocked();
                for (int size = currentUserStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection = currentUserStateLocked.mBoundServices.get(size);
                    if (sparseArray.contains(accessibilityServiceConnection.mId) && accessibilityServiceConnection.requestImeApis()) {
                        accessibilityServiceConnection.setImeSessionEnabledLocked(sparseArray.get(accessibilityServiceConnection.mId), z);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void injectInputEventToInputFilter(android.view.InputEvent inputEvent) {
        this.mSecurityPolicy.enforceCallingPermission("android.permission.INJECT_EVENTS", "injectInputEventToInputFilter");
        synchronized (this.mLock) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis() + 1000;
            while (!this.mInputFilterInstalled && android.os.SystemClock.uptimeMillis() < uptimeMillis) {
                try {
                    this.mLock.wait(uptimeMillis - android.os.SystemClock.uptimeMillis());
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
        if (this.mInputFilterInstalled && this.mInputFilter != null) {
            this.mInputFilter.onInputEvent(inputEvent, 1090519040);
        } else {
            android.util.Slog.w(LOG_TAG, "Cannot injectInputEventToInputFilter because the AccessibilityInputFilter is not installed.");
        }
    }

    private final class SendWindowStateChangedEventRunnable implements java.lang.Runnable {
        private final android.view.accessibility.AccessibilityEvent mPendingEvent;
        private final int mWindowId;

        SendWindowStateChangedEventRunnable(@android.annotation.NonNull android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            this.mPendingEvent = accessibilityEvent;
            this.mWindowId = accessibilityEvent.getWindowId();
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.accessibility.AccessibilityManagerService.this.mLock) {
                android.util.Slog.w(com.android.server.accessibility.AccessibilityManagerService.LOG_TAG, " wait for adding window timeout: " + this.mWindowId);
                sendPendingEventLocked();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void sendPendingEventLocked() {
            com.android.server.accessibility.AccessibilityManagerService.this.mSendWindowStateChangedEventRunnables.remove(this);
            com.android.server.accessibility.AccessibilityManagerService.this.dispatchAccessibilityEventLocked(this.mPendingEvent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getWindowId() {
            return this.mWindowId;
        }
    }

    void sendPendingWindowStateChangedEventsForAvailableWindowLocked(int i) {
        for (int size = this.mSendWindowStateChangedEventRunnables.size() - 1; size >= 0; size--) {
            com.android.server.accessibility.AccessibilityManagerService.SendWindowStateChangedEventRunnable sendWindowStateChangedEventRunnable = this.mSendWindowStateChangedEventRunnables.get(size);
            if (sendWindowStateChangedEventRunnable.getWindowId() == i) {
                this.mMainHandler.removeCallbacks(sendWindowStateChangedEventRunnable);
                sendWindowStateChangedEventRunnable.sendPendingEventLocked();
            }
        }
    }

    private boolean postponeWindowStateEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        synchronized (this.mLock) {
            try {
                if (this.mA11yWindowManager.findWindowInfoByIdLocked(this.mA11yWindowManager.resolveParentWindowIdLocked(accessibilityEvent.getWindowId())) != null) {
                    return false;
                }
                com.android.server.accessibility.AccessibilityManagerService.SendWindowStateChangedEventRunnable sendWindowStateChangedEventRunnable = new com.android.server.accessibility.AccessibilityManagerService.SendWindowStateChangedEventRunnable(new android.view.accessibility.AccessibilityEvent(accessibilityEvent));
                this.mMainHandler.postDelayed(sendWindowStateChangedEventRunnable, 500L);
                this.mSendWindowStateChangedEventRunnables.add(sendWindowStateChangedEventRunnable);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, android.view.SurfaceControl surfaceControl) {
        this.mContext.enforceCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "attachAccessibilityOverlayToDisplay");
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda24(), this, -1, java.lang.Integer.valueOf(i), surfaceControl, (java.lang.Object) null));
    }

    @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection.SystemSupport
    public void attachAccessibilityOverlayToDisplay(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        this.mMainHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda24(), this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), surfaceControl, iAccessibilityInteractionConnectionCallback));
    }

    void attachAccessibilityOverlayToDisplayInternal(int i, int i2, android.view.SurfaceControl surfaceControl, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        int i3;
        if (!this.mA11yOverlayLayers.contains(i2)) {
            this.mA11yOverlayLayers.put(i2, this.mWindowManagerService.getA11yOverlayLayer(i2));
        }
        android.view.SurfaceControl surfaceControl2 = this.mA11yOverlayLayers.get(i2);
        if (surfaceControl2 == null) {
            android.util.Slog.e(LOG_TAG, "Unable to get accessibility overlay SurfaceControl.");
            this.mA11yOverlayLayers.remove(i2);
            i3 = 2;
        } else {
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            transaction.reparent(surfaceControl, surfaceControl2).setTrustedOverlay(surfaceControl, true).apply();
            transaction.close();
            i3 = 0;
        }
        if (iAccessibilityInteractionConnectionCallback != null) {
            try {
                iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(i3, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(LOG_TAG, "Exception while attaching overlay.", e);
            }
        }
    }
}
