package com.android.server.wm;

/* loaded from: classes3.dex */
public class WindowManagerService extends android.view.IWindowManager.Stub implements com.android.server.Watchdog.Monitor, com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs {
    private static final int ANIMATION_COMPLETED_TIMEOUT_MS = 5000;
    private static final int ANIMATION_DURATION_SCALE = 2;
    private static final int BOOT_ANIMATION_POLL_INTERVAL = 50;
    private static final java.lang.String BOOT_ANIMATION_SERVICE = "bootanim";
    private static final java.lang.String DENSITY_OVERRIDE = "ro.config.density_override";
    private static final int INPUT_DEVICES_READY_FOR_SAFE_MODE_DETECTION_TIMEOUT_MILLIS = 1000;
    static final int LAST_ANR_LIFETIME_DURATION_MSECS = 7200000;
    static final int LAYOUT_REPEAT_THRESHOLD = 4;
    static final int LOGTAG_INPUT_FOCUS = 62001;
    static final int MAX_ANIMATION_DURATION = 10000;
    static final boolean PROFILE_ORIENTATION = false;
    private static final java.lang.String PROPERTY_EMULATOR_CIRCULAR = "ro.boot.emulator.circular";
    private static final java.lang.String SIZE_OVERRIDE = "ro.config.size_override";
    private static final int SYNC_INPUT_TRANSACTIONS_TIMEOUT_MS = 5000;
    private static final java.lang.String SYSTEM_DEBUGGABLE = "ro.debuggable";
    private static final java.lang.String SYSTEM_SECURE = "ro.secure";
    private static final java.lang.String TAG = "WindowManager";
    private static final int TRACE_MAX_SECTION_NAME_LENGTH = 127;
    private static final int TRANSITION_ANIMATION_SCALE = 1;
    static final int UPDATE_FOCUS_NORMAL = 0;
    static final int UPDATE_FOCUS_PLACING_SURFACES = 2;
    static final int UPDATE_FOCUS_REMOVING_FOCUS = 4;
    static final int UPDATE_FOCUS_WILL_ASSIGN_LAYERS = 1;
    static final int UPDATE_FOCUS_WILL_PLACE_SURFACES = 3;
    static final int WINDOWS_FREEZING_SCREENS_ACTIVE = 1;
    static final int WINDOWS_FREEZING_SCREENS_NONE = 0;
    static final int WINDOWS_FREEZING_SCREENS_TIMEOUT = 2;
    private static final int WINDOW_ANIMATION_SCALE = 0;
    static final int WINDOW_FREEZE_TIMEOUT_DURATION = 2000;
    final com.android.server.wm.AccessibilityController mAccessibilityController;
    final android.app.IActivityManager mActivityManager;
    final boolean mAllowAnimationsInLowPowerMode;
    final boolean mAllowBootMessages;
    boolean mAllowTheaterModeWakeFromLayout;
    final android.app.ActivityManagerInternal mAmInternal;
    private boolean mAnimationsDisabled;
    final com.android.server.wm.WindowAnimator mAnimator;
    final com.android.server.wm.AnrController mAnrController;
    final android.app.AppOpsManager mAppOps;
    final boolean mAssistantOnTopOfDream;
    final com.android.server.wm.ActivityTaskManagerService mAtmService;
    final com.android.server.wm.BlurController mBlurController;
    final int mConfigTypes;
    final com.android.server.wm.WindowManagerConstants mConstants;
    final android.content.Context mContext;
    int mCurrentUserId;
    final int mDecorTypes;
    boolean mDisableTransitionAnimation;
    private final com.android.server.wm.DisplayAreaPolicy.Provider mDisplayAreaPolicyProvider;
    private final com.android.server.wm.DisplayHashController mDisplayHashController;
    final android.hardware.display.DisplayManager mDisplayManager;
    final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    final com.android.server.wm.DisplayWindowListenerController mDisplayNotificationController;
    boolean mDisplayReady;

    @android.annotation.NonNull
    final com.android.server.wm.DisplayWindowSettings mDisplayWindowSettings;

    @android.annotation.NonNull
    final com.android.server.wm.DisplayWindowSettingsProvider mDisplayWindowSettingsProvider;
    final com.android.server.wm.DragDropController mDragDropController;
    final long mDrawLockTimeoutMillis;
    final com.android.server.wm.EmbeddedWindowController mEmbeddedWindowController;
    com.android.server.wm.EmulatorDisplayOverlay mEmulatorDisplayOverlay;
    private int mEnterAnimId;
    private boolean mEventDispatchingEnabled;
    private int mExitAnimId;
    final com.android.server.wm.WindowManagerFlags mFlags;
    boolean mFocusMayChange;
    private com.android.server.wm.InputTarget mFocusedInputTarget;
    boolean mForceDesktopModeOnExternalDisplays;
    final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    boolean mHardKeyboardAvailable;
    com.android.server.wm.WindowManagerInternal.OnHardKeyboardStatusChangeListener mHardKeyboardStatusChangeListener;
    private boolean mHasHdrSupport;
    final boolean mHasPermanentDpad;
    private boolean mHasWideColorGamutSupport;
    final com.android.server.wm.HighRefreshRateDenylist mHighRefreshRateDenylist;

    @android.annotation.Nullable
    com.android.server.wm.ImeTargetChangeListener mImeTargetChangeListener;
    final com.android.server.input.InputManagerService mInputManager;
    boolean mIsFakeTouchDevice;
    private boolean mIsIgnoreOrientationRequestDisabled;
    boolean mIsPc;
    boolean mIsTouchDevice;
    private final com.android.server.wm.KeyguardDisableHandler mKeyguardDisableHandler;
    java.lang.String mLastANRState;
    final com.android.internal.util.LatencyTracker mLatencyTracker;
    final com.android.server.wm.LetterboxConfiguration mLetterboxConfiguration;
    final boolean mLimitedAlphaCompositing;
    final int mMaxUiWidth;

    @android.annotation.Nullable
    final com.android.server.wm.WindowManagerService.MousePositionTracker mMousePositionTracker;

    @com.android.internal.annotations.VisibleForTesting
    boolean mPerDisplayFocusEnabled;
    final android.content.pm.PackageManagerInternal mPmInternal;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.policy.WindowManagerPolicy mPolicy;
    final com.android.server.wm.PossibleDisplayInfoMapper mPossibleDisplayInfoMapper;
    android.os.PowerManager mPowerManager;
    android.os.PowerManagerInternal mPowerManagerInternal;
    private com.android.server.wm.RecentsAnimationController mRecentsAnimationController;

    @android.annotation.NonNull
    final com.android.server.wm.RootWindowContainer mRoot;
    final com.android.server.wm.RotationWatcherController mRotationWatcherController;
    boolean mSafeMode;
    private final android.os.PowerManager.WakeLock mScreenFrozenLock;
    private final com.android.server.wm.ScreenRecordingCallbackController mScreenRecordingCallbackController;
    com.android.server.wm.WindowManagerService.SettingsObserver mSettingsObserver;

    @com.android.internal.annotations.VisibleForTesting
    boolean mSkipActivityRelaunchWhenDocking;
    final com.android.server.wm.SnapshotController mSnapshotController;
    final com.android.server.wm.StartingSurfaceController mStartingSurfaceController;
    com.android.server.wm.StrictModeFlash mStrictModeFlash;
    com.android.server.wm.SurfaceAnimationRunner mSurfaceAnimationRunner;
    java.util.function.Function<android.view.SurfaceSession, android.view.SurfaceControl.Builder> mSurfaceControlFactory;
    final com.android.server.wm.BLASTSyncEngine mSyncEngine;
    android.window.SystemPerformanceHinter mSystemPerformanceHinter;
    final com.android.server.wm.TaskFpsCallbackController mTaskFpsCallbackController;
    final com.android.server.wm.TaskPositioningController mTaskPositioningController;
    final com.android.server.wm.TaskSnapshotController mTaskSnapshotController;
    final com.android.server.wm.TaskSystemBarsListenerController mTaskSystemBarsListenerController;
    android.view.TaskTransitionSpec mTaskTransitionSpec;
    private android.view.WindowContentFrameStats mTempWindowRenderStats;
    private final android.content.pm.TestUtilityService mTestUtilityService;
    private final android.view.SurfaceControl.Transaction mTransaction;
    java.util.function.Supplier<android.view.SurfaceControl.Transaction> mTransactionFactory;
    int mTransactionSequence;
    private float mTransitionAnimationScaleSetting;
    final com.android.server.wm.TransitionTracer mTransitionTracer;
    final com.android.server.pm.UserManagerInternal mUmInternal;
    private com.android.server.wm.ViewServer mViewServer;
    com.android.server.wm.Watermark mWatermark;
    private float mWindowAnimationScaleSetting;
    final com.android.server.wm.WindowSurfacePlacer mWindowPlacerLocked;
    final com.android.server.wm.WindowTracing mWindowTracing;
    static final int MY_PID = android.os.Process.myPid();
    static final int MY_UID = android.os.Process.myUid();
    public static final java.lang.String ENABLE_SHELL_TRANSITIONS = "persist.wm.debug.shell_transit";
    public static final boolean sEnableShellTransitions = android.os.SystemProperties.getBoolean(ENABLE_SHELL_TRANSITIONS, true);
    static final boolean ENABLE_FIXED_ROTATION_TRANSFORM = android.os.SystemProperties.getBoolean("persist.wm.fixed_rotation_transform", true);
    static com.android.server.wm.WindowManagerThreadPriorityBooster sThreadPriorityBooster = new com.android.server.wm.WindowManagerThreadPriorityBooster();
    private final android.os.RemoteCallbackList<com.android.internal.policy.IKeyguardLockedStateListener> mKeyguardLockedStateListeners = new android.os.RemoteCallbackList<>();
    private boolean mDispatchedKeyguardLockedState = false;
    int mVr2dDisplayId = -1;
    boolean mVrModeEnabled = false;
    final java.util.Map<android.os.IBinder, com.android.internal.policy.KeyInterceptionInfo> mKeyInterceptionInfoForToken = java.util.Collections.synchronizedMap(new android.util.ArrayMap());
    private final android.service.vr.IVrStateCallbacks mVrStateCallbacks = new com.android.server.wm.WindowManagerService.AnonymousClass1();
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.wm.WindowManagerService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case 988075300:
                    if (action.equals("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED")) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.wm.WindowManagerService.this.mKeyguardDisableHandler.updateKeyguardEnabled(getSendingUserId());
                    break;
            }
        }
    };
    private final com.android.server.utils.PriorityDump.PriorityDumper mPriorityDumper = new com.android.server.utils.PriorityDump.PriorityDumper() { // from class: com.android.server.wm.WindowManagerService.3
        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public void dumpCritical(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
            com.android.server.wm.WindowManagerService.this.doDump(fileDescriptor, printWriter, new java.lang.String[]{"-a"}, z);
        }

        @Override // com.android.server.utils.PriorityDump.PriorityDumper
        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
            com.android.server.wm.WindowManagerService.this.doDump(fileDescriptor, printWriter, strArr, z);
        }
    };
    boolean mShowAlertWindowNotifications = true;
    final android.util.ArraySet<com.android.server.wm.Session> mSessions = new android.util.ArraySet<>();
    final java.util.HashMap<android.os.IBinder, com.android.server.wm.WindowState> mWindowMap = new java.util.HashMap<>();
    final java.util.HashMap<android.os.IBinder, com.android.server.wm.WindowState> mInputToWindowMap = new java.util.HashMap<>();
    final java.util.ArrayList<com.android.server.wm.WindowState> mResizingWindows = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.WindowState> mFrameChangingWindows = new java.util.ArrayList<>();
    volatile java.util.Map<java.lang.Integer, java.lang.Integer> mDisplayImePolicyCache = java.util.Collections.unmodifiableMap(new android.util.ArrayMap());
    final java.util.ArrayList<com.android.server.wm.WindowState> mDestroySurface = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.WindowState> mForceRemoves = new java.util.ArrayList<>();
    final android.util.ArrayMap<com.android.server.wm.WindowContainer<?>, android.os.Message> mWaitingForDrawnCallbacks = new android.util.ArrayMap<>();
    private java.util.ArrayList<com.android.server.wm.WindowState> mHidingNonSystemOverlayWindows = new java.util.ArrayList<>();
    private final android.util.SparseIntArray mOrientationMapping = new android.util.SparseIntArray();
    final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    boolean mDisplayEnabled = false;
    boolean mSystemBooted = false;
    boolean mForceDisplayEnabled = false;
    boolean mShowingBootMessages = false;
    boolean mSystemReady = false;
    boolean mBootAnimationStopped = false;
    long mBootWaitForWindowsStartTime = -1;
    final com.android.server.wm.WallpaperVisibilityListeners mWallpaperVisibilityListeners = new com.android.server.wm.WallpaperVisibilityListeners();
    android.view.IDisplayChangeWindowController mDisplayChangeController = null;
    private final android.os.IBinder.DeathRecipient mDisplayChangeControllerDeath = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda16
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            com.android.server.wm.WindowManagerService.this.lambda$new$0();
        }
    };
    boolean mDisplayFrozen = false;
    long mDisplayFreezeTime = 0;
    int mLastDisplayFreezeDuration = 0;
    java.lang.Object mLastFinishedFreezeSource = null;
    boolean mSwitchingUser = false;
    int mWindowsFreezingScreen = 0;
    boolean mClientFreezingScreen = false;
    int mAppsFreezingScreen = 0;
    int mWindowsInsetsChanged = 0;
    final com.android.server.wm.WindowManagerService.H mH = new com.android.server.wm.WindowManagerService.H();
    final android.os.Handler mAnimationHandler = new android.os.Handler(com.android.server.AnimationThread.getHandler().getLooper());
    volatile float mMaximumObscuringOpacityForTouch = 0.8f;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wm.WindowContextListenerController mWindowContextListenerController = new com.android.server.wm.WindowContextListenerController();

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wm.ContentRecordingController mContentRecordingController = new com.android.server.wm.ContentRecordingController();
    private final com.android.server.wm.SurfaceSyncGroupController mSurfaceSyncGroupController = new com.android.server.wm.SurfaceSyncGroupController();
    final com.android.server.wm.TrustedPresentationListenerController mTrustedPresentationListenerController = new com.android.server.wm.TrustedPresentationListenerController();
    private float mAnimatorDurationScaleSetting = 1.0f;
    boolean mPointerLocationEnabled = false;
    private int mFrozenDisplayId = -1;
    final android.util.ArrayMap<com.android.server.wm.AnimationAdapter, com.android.server.wm.SurfaceAnimator> mAnimationTransferMap = new android.util.ArrayMap<>();
    final java.util.ArrayList<com.android.server.wm.WindowManagerService.WindowChangeListener> mWindowChangeListeners = new java.util.ArrayList<>();
    boolean mWindowsChanged = false;

    @com.android.internal.annotations.GuardedBy({"mGlobalLock"})
    final com.android.server.wm.SensitiveContentPackages mSensitiveContentPackages = new com.android.server.wm.SensitiveContentPackages();
    final com.android.server.wm.WindowManagerInternal.AppTransitionListener mActivityManagerAppTransitionNotifier = new com.android.server.wm.WindowManagerInternal.AppTransitionListener() { // from class: com.android.server.wm.WindowManagerService.4
        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionCancelledLocked(boolean z) {
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionFinishedLocked(android.os.IBinder iBinder) {
            com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
            if (forTokenLocked == null) {
                return;
            }
            if (forTokenLocked.mLaunchTaskBehind && !com.android.server.wm.WindowManagerService.this.isRecentsAnimationTarget(forTokenLocked)) {
                com.android.server.wm.WindowManagerService.this.mAtmService.mTaskSupervisor.scheduleLaunchTaskBehindComplete(forTokenLocked.token);
                forTokenLocked.mLaunchTaskBehind = false;
                return;
            }
            forTokenLocked.updateReportedVisibilityLocked();
            if (forTokenLocked.mEnteringAnimation && !com.android.server.wm.WindowManagerService.this.isRecentsAnimationTarget(forTokenLocked)) {
                forTokenLocked.mEnteringAnimation = false;
                if (forTokenLocked.attachedToProcess()) {
                    try {
                        forTokenLocked.app.getThread().scheduleEnterAnimationComplete(forTokenLocked.token);
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
        }
    };
    final java.util.ArrayList<com.android.server.wm.WindowManagerService.AppFreezeListener> mAppFreezeListeners = new java.util.ArrayList<>();
    final com.android.server.wm.InputManagerCallback mInputManagerCallback = new com.android.server.wm.InputManagerCallback(this);

    interface AppFreezeListener {
        void onAppFreezeTimeout();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface UpdateAnimationScaleMode {
    }

    public interface WindowChangeListener {
        void focusChanged();

        void windowsChanged();
    }

    /* renamed from: com.android.server.wm.WindowManagerService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.vr.IVrStateCallbacks.Stub {
        AnonymousClass1() {
        }

        public void onVrStateChanged(final boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mVrModeEnabled = z;
                    com.android.server.wm.WindowManagerService.this.mRoot.forAllDisplayPolicies(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.wm.DisplayPolicy) obj).onVrStateChangedLw(z);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mDisplayChangeController = null;
    }

    @com.android.internal.annotations.VisibleForTesting
    final class SettingsObserver extends android.database.ContentObserver {
        private final android.net.Uri mAnimationDurationScaleUri;
        private final android.net.Uri mDevEnableNonResizableMultiWindowUri;
        private final android.net.Uri mDisplayInversionEnabledUri;
        private final android.net.Uri mDisplaySettingsPathUri;
        private final android.net.Uri mForceDesktopModeOnExternalDisplaysUri;
        private final android.net.Uri mForceResizableUri;
        private final android.net.Uri mFreeformWindowUri;
        private final android.net.Uri mImmersiveModeConfirmationsUri;
        private final android.net.Uri mMaximumObscuringOpacityForTouchUri;
        private final android.net.Uri mPolicyControlUri;
        private final android.net.Uri mTransitionAnimationScaleUri;
        private final android.net.Uri mWindowAnimationScaleUri;

        public SettingsObserver() {
            super(new android.os.Handler());
            this.mDisplayInversionEnabledUri = android.provider.Settings.Secure.getUriFor("accessibility_display_inversion_enabled");
            this.mWindowAnimationScaleUri = android.provider.Settings.Global.getUriFor("window_animation_scale");
            this.mTransitionAnimationScaleUri = android.provider.Settings.Global.getUriFor("transition_animation_scale");
            this.mAnimationDurationScaleUri = android.provider.Settings.Global.getUriFor("animator_duration_scale");
            this.mImmersiveModeConfirmationsUri = android.provider.Settings.Secure.getUriFor("immersive_mode_confirmations");
            this.mPolicyControlUri = android.provider.Settings.Global.getUriFor("policy_control");
            this.mForceDesktopModeOnExternalDisplaysUri = android.provider.Settings.Global.getUriFor("force_desktop_mode_on_external_displays");
            this.mFreeformWindowUri = android.provider.Settings.Global.getUriFor("enable_freeform_support");
            this.mForceResizableUri = android.provider.Settings.Global.getUriFor("force_resizable_activities");
            this.mDevEnableNonResizableMultiWindowUri = android.provider.Settings.Global.getUriFor("enable_non_resizable_multi_window");
            this.mDisplaySettingsPathUri = android.provider.Settings.Global.getUriFor("wm_display_settings_path");
            this.mMaximumObscuringOpacityForTouchUri = android.provider.Settings.Global.getUriFor("maximum_obscuring_opacity_for_touch");
            android.content.ContentResolver contentResolver = com.android.server.wm.WindowManagerService.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(this.mDisplayInversionEnabledUri, false, this, -1);
            contentResolver.registerContentObserver(this.mWindowAnimationScaleUri, false, this, -1);
            contentResolver.registerContentObserver(this.mTransitionAnimationScaleUri, false, this, -1);
            contentResolver.registerContentObserver(this.mAnimationDurationScaleUri, false, this, -1);
            contentResolver.registerContentObserver(this.mImmersiveModeConfirmationsUri, false, this, -1);
            contentResolver.registerContentObserver(this.mPolicyControlUri, false, this, -1);
            contentResolver.registerContentObserver(this.mForceDesktopModeOnExternalDisplaysUri, false, this, -1);
            contentResolver.registerContentObserver(this.mFreeformWindowUri, false, this, -1);
            contentResolver.registerContentObserver(this.mForceResizableUri, false, this, -1);
            contentResolver.registerContentObserver(this.mDevEnableNonResizableMultiWindowUri, false, this, -1);
            contentResolver.registerContentObserver(this.mDisplaySettingsPathUri, false, this, -1);
            contentResolver.registerContentObserver(this.mMaximumObscuringOpacityForTouchUri, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (uri == null) {
                return;
            }
            int i = 1;
            if (this.mImmersiveModeConfirmationsUri.equals(uri) || this.mPolicyControlUri.equals(uri)) {
                updateSystemUiSettings(true);
                return;
            }
            if (this.mForceDesktopModeOnExternalDisplaysUri.equals(uri)) {
                updateForceDesktopModeOnExternalDisplays();
                return;
            }
            if (this.mFreeformWindowUri.equals(uri)) {
                updateFreeformWindowManagement();
                return;
            }
            if (this.mForceResizableUri.equals(uri)) {
                updateForceResizableTasks();
                return;
            }
            if (this.mDevEnableNonResizableMultiWindowUri.equals(uri)) {
                updateDevEnableNonResizableMultiWindow();
                return;
            }
            if (this.mDisplaySettingsPathUri.equals(uri)) {
                updateDisplaySettingsLocation();
                return;
            }
            if (this.mMaximumObscuringOpacityForTouchUri.equals(uri)) {
                updateMaximumObscuringOpacityForTouch();
                return;
            }
            if (this.mWindowAnimationScaleUri.equals(uri)) {
                i = 0;
            } else if (!this.mTransitionAnimationScaleUri.equals(uri)) {
                if (this.mAnimationDurationScaleUri.equals(uri)) {
                    i = 2;
                } else {
                    return;
                }
            }
            com.android.server.wm.WindowManagerService.this.mH.sendMessage(com.android.server.wm.WindowManagerService.this.mH.obtainMessage(51, i, 0));
        }

        void loadSettings() {
            updateSystemUiSettings(false);
            updateMaximumObscuringOpacityForTouch();
        }

        void updateMaximumObscuringOpacityForTouch() {
            android.content.ContentResolver contentResolver = com.android.server.wm.WindowManagerService.this.mContext.getContentResolver();
            com.android.server.wm.WindowManagerService.this.mMaximumObscuringOpacityForTouch = android.provider.Settings.Global.getFloat(contentResolver, "maximum_obscuring_opacity_for_touch", 0.8f);
            if (com.android.server.wm.WindowManagerService.this.mMaximumObscuringOpacityForTouch < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || com.android.server.wm.WindowManagerService.this.mMaximumObscuringOpacityForTouch > 1.0f) {
                com.android.server.wm.WindowManagerService.this.mMaximumObscuringOpacityForTouch = 0.8f;
            }
        }

        void updateSystemUiSettings(boolean z) {
            boolean z2;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (z) {
                        z2 = com.android.server.wm.WindowManagerService.this.getDefaultDisplayContentLocked().getDisplayPolicy().onSystemUiSettingsChanged();
                    } else {
                        com.android.server.wm.ImmersiveModeConfirmation.loadSetting(com.android.server.wm.WindowManagerService.this.mCurrentUserId, com.android.server.wm.WindowManagerService.this.mContext);
                        z2 = false;
                    }
                    if (z2) {
                        com.android.server.wm.WindowManagerService.this.mWindowPlacerLocked.requestTraversal();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        void updateForceDesktopModeOnExternalDisplays() {
            boolean z = android.provider.Settings.Global.getInt(com.android.server.wm.WindowManagerService.this.mContext.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0;
            if (com.android.server.wm.WindowManagerService.this.mForceDesktopModeOnExternalDisplays == z) {
                return;
            }
            com.android.server.wm.WindowManagerService.this.setForceDesktopModeOnExternalDisplays(z);
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0020, code lost:
        
            if (android.provider.Settings.Global.getInt(r0, "enable_freeform_support", 0) != 0) goto L6;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void updateFreeformWindowManagement() {
            android.content.ContentResolver contentResolver = com.android.server.wm.WindowManagerService.this.mContext.getContentResolver();
            boolean z = com.android.server.wm.WindowManagerService.this.mContext.getPackageManager().hasSystemFeature("android.software.freeform_window_management");
            if (com.android.server.wm.WindowManagerService.this.mAtmService.mSupportsFreeformWindowManagement != z) {
                com.android.server.wm.WindowManagerService.this.mAtmService.mSupportsFreeformWindowManagement = z;
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        com.android.server.wm.WindowManagerService.this.mRoot.onSettingsRetrieved();
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        }

        void updateForceResizableTasks() {
            com.android.server.wm.WindowManagerService.this.mAtmService.mForceResizableActivities = android.provider.Settings.Global.getInt(com.android.server.wm.WindowManagerService.this.mContext.getContentResolver(), "force_resizable_activities", 0) != 0;
        }

        void updateDevEnableNonResizableMultiWindow() {
            com.android.server.wm.WindowManagerService.this.mAtmService.mDevEnableNonResizableMultiWindow = android.provider.Settings.Global.getInt(com.android.server.wm.WindowManagerService.this.mContext.getContentResolver(), "enable_non_resizable_multi_window", 0) != 0;
        }

        void updateDisplaySettingsLocation() {
            java.lang.String string = android.provider.Settings.Global.getString(com.android.server.wm.WindowManagerService.this.mContext.getContentResolver(), "wm_display_settings_path");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mDisplayWindowSettingsProvider.setBaseSettingsFilePath(string);
                    com.android.server.wm.WindowManagerService.this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$SettingsObserver$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.WindowManagerService.SettingsObserver.this.lambda$updateDisplaySettingsLocation$0((com.android.server.wm.DisplayContent) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateDisplaySettingsLocation$0(com.android.server.wm.DisplayContent displayContent) {
            com.android.server.wm.WindowManagerService.this.mDisplayWindowSettings.applySettingsToDisplayLocked(displayContent);
            displayContent.reconfigureDisplayLocked();
        }
    }

    static void boostPriorityForLockedSection() {
        sThreadPriorityBooster.boost();
    }

    static void resetPriorityAfterLockedSection() {
        sThreadPriorityBooster.reset();
    }

    public static com.android.server.wm.WindowManagerService main(android.content.Context context, com.android.server.input.InputManagerService inputManagerService, boolean z, com.android.server.policy.WindowManagerPolicy windowManagerPolicy, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        com.android.server.wm.WindowManagerService main = main(context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, new com.android.server.wm.DisplayWindowSettingsProvider(), new java.util.function.Supplier() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda14
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return new android.view.SurfaceControl.Transaction();
            }
        }, new java.util.function.Function() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return new android.view.SurfaceControl.Builder((android.view.SurfaceSession) obj);
            }
        });
        android.view.WindowManagerGlobal.setWindowManagerServiceForSystemProcess(main);
        return main;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.wm.WindowManagerService main(final android.content.Context context, final com.android.server.input.InputManagerService inputManagerService, final boolean z, final com.android.server.policy.WindowManagerPolicy windowManagerPolicy, final com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, final com.android.server.wm.DisplayWindowSettingsProvider displayWindowSettingsProvider, final java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier, final java.util.function.Function<android.view.SurfaceSession, android.view.SurfaceControl.Builder> function) {
        final com.android.server.wm.WindowManagerService[] windowManagerServiceArr = new com.android.server.wm.WindowManagerService[1];
        com.android.server.DisplayThread.getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerService.lambda$main$1(windowManagerServiceArr, context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, displayWindowSettingsProvider, supplier, function);
            }
        }, 0L);
        return windowManagerServiceArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$main$1(com.android.server.wm.WindowManagerService[] windowManagerServiceArr, android.content.Context context, com.android.server.input.InputManagerService inputManagerService, boolean z, com.android.server.policy.WindowManagerPolicy windowManagerPolicy, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.DisplayWindowSettingsProvider displayWindowSettingsProvider, java.util.function.Supplier supplier, java.util.function.Function function) {
        windowManagerServiceArr[0] = new com.android.server.wm.WindowManagerService(context, inputManagerService, z, windowManagerPolicy, activityTaskManagerService, displayWindowSettingsProvider, supplier, function);
    }

    private void initPolicy() {
        com.android.server.UiThread.getHandler().runWithScissors(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService.5
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.view.WindowManagerPolicyThread.set(java.lang.Thread.currentThread(), android.os.Looper.myLooper());
                com.android.server.wm.WindowManagerService.this.mPolicy.init(com.android.server.wm.WindowManagerService.this.mContext, com.android.server.wm.WindowManagerService.this);
            }
        }, 0L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.wm.WindowManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private WindowManagerService(android.content.Context context, com.android.server.input.InputManagerService inputManagerService, boolean z, com.android.server.policy.WindowManagerPolicy windowManagerPolicy, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.DisplayWindowSettingsProvider displayWindowSettingsProvider, java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier, java.util.function.Function<android.view.SurfaceSession, android.view.SurfaceControl.Builder> function) {
        com.android.server.wm.WindowManagerService.MousePositionTracker mousePositionTracker;
        byte b;
        byte b2 = 0;
        byte b3 = 0;
        this.mWindowAnimationScaleSetting = 1.0f;
        this.mTransitionAnimationScaleSetting = 1.0f;
        this.mAnimationsDisabled = false;
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            mousePositionTracker = null;
        } else {
            mousePositionTracker = new com.android.server.wm.WindowManagerService.MousePositionTracker();
        }
        this.mMousePositionTracker = mousePositionTracker;
        com.android.server.LockGuard.installLock(this, 5);
        this.mGlobalLock = activityTaskManagerService.getGlobalLock();
        this.mAtmService = activityTaskManagerService;
        this.mContext = context;
        this.mFlags = new com.android.server.wm.WindowManagerFlags();
        this.mIsPc = this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.pc");
        this.mAllowBootMessages = z;
        this.mLimitedAlphaCompositing = context.getResources().getBoolean(android.R.bool.config_setColorTransformAccelerated);
        this.mHasPermanentDpad = context.getResources().getBoolean(android.R.bool.config_guestUserAutoCreated);
        this.mDrawLockTimeoutMillis = context.getResources().getInteger(android.R.integer.config_doublelineClockDefault);
        this.mAllowAnimationsInLowPowerMode = context.getResources().getBoolean(android.R.bool.config_allowAnimationsInLowPowerMode);
        this.mMaxUiWidth = context.getResources().getInteger(android.R.integer.config_maxResolverActivityColumns);
        this.mDisableTransitionAnimation = context.getResources().getBoolean(android.R.bool.config_disableTaskSnapshots);
        this.mPerDisplayFocusEnabled = context.getResources().getBoolean(android.R.bool.config_perDisplayFocusEnabled);
        this.mAssistantOnTopOfDream = context.getResources().getBoolean(android.R.bool.config_assistantOnTopOfDream);
        this.mSkipActivityRelaunchWhenDocking = context.getResources().getBoolean(android.R.bool.config_silenceSensorAvailable);
        if (!context.getResources().getBoolean(android.R.bool.config_decoupleStatusBarAndDisplayCutoutFromScreenSize) || !this.mFlags.mAllowsScreenSizeDecoupledFromStatusBarAndCutout) {
            b = false;
        } else {
            b = true;
        }
        if (b == false) {
            this.mDecorTypes = android.view.WindowInsets.Type.displayCutout() | android.view.WindowInsets.Type.navigationBars();
            this.mConfigTypes = android.view.WindowInsets.Type.displayCutout() | android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars();
        } else {
            this.mDecorTypes = android.view.WindowInsets.Type.navigationBars();
            this.mConfigTypes = android.view.WindowInsets.Type.navigationBars();
        }
        this.mLetterboxConfiguration = new com.android.server.wm.LetterboxConfiguration(android.app.ActivityThread.currentActivityThread().getSystemUiContext());
        this.mInputManager = inputManagerService;
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        this.mPossibleDisplayInfoMapper = new com.android.server.wm.PossibleDisplayInfoMapper(this.mDisplayManagerInternal);
        this.mSurfaceControlFactory = function;
        this.mTransactionFactory = supplier;
        this.mTransaction = this.mTransactionFactory.get();
        this.mPolicy = windowManagerPolicy;
        this.mAnimator = new com.android.server.wm.WindowAnimator(this);
        this.mRoot = new com.android.server.wm.RootWindowContainer(this);
        android.content.ContentResolver contentResolver = context.getContentResolver();
        this.mSyncEngine = new com.android.server.wm.BLASTSyncEngine(this);
        this.mWindowPlacerLocked = new com.android.server.wm.WindowSurfacePlacer(this);
        this.mSnapshotController = new com.android.server.wm.SnapshotController(this);
        this.mTaskSnapshotController = this.mSnapshotController.mTaskSnapshotController;
        this.mWindowTracing = com.android.server.wm.WindowTracing.createDefaultAndStartLooper(this, android.view.Choreographer.getInstance());
        if (android.tracing.Flags.perfettoTransitionTracing()) {
            this.mTransitionTracer = new com.android.server.wm.PerfettoTransitionTracer();
        } else {
            this.mTransitionTracer = new com.android.server.wm.LegacyTransitionTracer();
        }
        com.android.server.LocalServices.addService(com.android.server.policy.WindowManagerPolicy.class, this.mPolicy);
        this.mDisplayManager = (android.hardware.display.DisplayManager) context.getSystemService("display");
        this.mKeyguardDisableHandler = com.android.server.wm.KeyguardDisableHandler.create(this.mContext, this.mPolicy, this.mH);
        this.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
        this.mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        if (this.mPowerManagerInternal != null) {
            this.mPowerManagerInternal.registerLowPowerModeObserver(new android.os.PowerManagerInternal.LowPowerModeListener() { // from class: com.android.server.wm.WindowManagerService.6
                public int getServiceType() {
                    return 3;
                }

                public void onLowPowerModeChanged(android.os.PowerSaveState powerSaveState) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            boolean z2 = powerSaveState.batterySaverEnabled;
                            if (com.android.server.wm.WindowManagerService.this.mAnimationsDisabled != z2 && !com.android.server.wm.WindowManagerService.this.mAllowAnimationsInLowPowerMode) {
                                com.android.server.wm.WindowManagerService.this.mAnimationsDisabled = z2;
                                com.android.server.wm.WindowManagerService.this.dispatchNewAnimatorScaleLocked(null);
                            }
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            });
            this.mAnimationsDisabled = this.mPowerManagerInternal.getLowPowerState(3).batterySaverEnabled;
        }
        this.mScreenFrozenLock = this.mPowerManager.newWakeLock(1, "SCREEN_FROZEN");
        this.mScreenFrozenLock.setReferenceCounted(false);
        this.mRotationWatcherController = new com.android.server.wm.RotationWatcherController(this);
        this.mDisplayNotificationController = new com.android.server.wm.DisplayWindowListenerController(this);
        this.mTaskSystemBarsListenerController = new com.android.server.wm.TaskSystemBarsListenerController();
        this.mActivityManager = android.app.ActivityManager.getService();
        this.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mUmInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mAppOps = (android.app.AppOpsManager) context.getSystemService("appops");
        android.app.AppOpsManager.OnOpChangedListener onOpChangedListener = new android.app.AppOpsManager.OnOpChangedInternalListener() { // from class: com.android.server.wm.WindowManagerService.7
            public void onOpChanged(int i, java.lang.String str) {
                com.android.server.wm.WindowManagerService.this.updateAppOpsState();
            }
        };
        this.mAppOps.startWatchingMode(24, (java.lang.String) null, onOpChangedListener);
        this.mAppOps.startWatchingMode(45, (java.lang.String) null, onOpChangedListener);
        this.mPmInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mTestUtilityService = (android.content.pm.TestUtilityService) com.android.server.LocalServices.getService(android.content.pm.TestUtilityService.class);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        context.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.wm.WindowManagerService.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                com.android.server.wm.WindowManagerService.this.updateHiddenWhileSuspendedState(new android.util.ArraySet(java.util.Arrays.asList(stringArrayExtra)), "android.intent.action.PACKAGES_SUSPENDED".equals(intent.getAction()));
            }
        }, android.os.UserHandle.ALL, intentFilter, null, null);
        this.mWindowAnimationScaleSetting = getWindowAnimationScaleSetting();
        this.mTransitionAnimationScaleSetting = getTransitionAnimationScaleSetting();
        setAnimatorDurationScale(getAnimatorDurationScaleSetting());
        this.mForceDesktopModeOnExternalDisplays = android.provider.Settings.Global.getInt(contentResolver, "force_desktop_mode_on_external_displays", 0) != 0;
        java.lang.String string = android.provider.Settings.Global.getString(contentResolver, "wm_display_settings_path");
        this.mDisplayWindowSettingsProvider = displayWindowSettingsProvider;
        if (string != null) {
            this.mDisplayWindowSettingsProvider.setBaseSettingsFilePath(string);
        }
        this.mDisplayWindowSettings = new com.android.server.wm.DisplayWindowSettings(this, this.mDisplayWindowSettingsProvider);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
        this.mLatencyTracker = com.android.internal.util.LatencyTracker.getInstance(context);
        this.mSettingsObserver = new com.android.server.wm.WindowManagerService.SettingsObserver();
        this.mSurfaceAnimationRunner = new com.android.server.wm.SurfaceAnimationRunner(this.mTransactionFactory, this.mPowerManagerInternal);
        this.mAllowTheaterModeWakeFromLayout = context.getResources().getBoolean(android.R.bool.config_allowTheaterModeWakeFromWindowLayout);
        this.mTaskPositioningController = new com.android.server.wm.TaskPositioningController(this);
        this.mDragDropController = new com.android.server.wm.DragDropController(this, this.mH.getLooper());
        this.mHighRefreshRateDenylist = com.android.server.wm.HighRefreshRateDenylist.create(context.getResources());
        this.mConstants = new com.android.server.wm.WindowManagerConstants(this, android.provider.DeviceConfigInterface.REAL);
        this.mConstants.start(new android.os.HandlerExecutor(this.mH));
        com.android.server.LocalServices.addService(com.android.server.wm.WindowManagerInternal.class, new com.android.server.wm.WindowManagerService.LocalService());
        com.android.server.LocalServices.addService(com.android.server.wm.ImeTargetVisibilityPolicy.class, new com.android.server.wm.WindowManagerService.ImeTargetVisibilityPolicyImpl());
        this.mEmbeddedWindowController = new com.android.server.wm.EmbeddedWindowController(this.mAtmService, inputManagerService);
        this.mDisplayAreaPolicyProvider = com.android.server.wm.DisplayAreaPolicy.Provider.fromResources(this.mContext.getResources());
        this.mDisplayHashController = new com.android.server.wm.DisplayHashController(this.mContext);
        setGlobalShadowSettings();
        this.mAnrController = new com.android.server.wm.AnrController(this);
        this.mStartingSurfaceController = new com.android.server.wm.StartingSurfaceController(this);
        this.mBlurController = new com.android.server.wm.BlurController(this.mContext, this.mPowerManager);
        this.mTaskFpsCallbackController = new com.android.server.wm.TaskFpsCallbackController(this.mContext);
        this.mAccessibilityController = new com.android.server.wm.AccessibilityController(this);
        this.mScreenRecordingCallbackController = new com.android.server.wm.ScreenRecordingCallbackController(this);
        this.mSystemPerformanceHinter = new android.window.SystemPerformanceHinter(this.mContext, new android.window.SystemPerformanceHinter.DisplayRootProvider() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda17
            public final android.view.SurfaceControl getRootForDisplay(int i) {
                android.view.SurfaceControl lambda$new$2;
                lambda$new$2 = com.android.server.wm.WindowManagerService.this.lambda$new$2(i);
                return lambda$new$2;
            }
        }, this.mTransactionFactory);
        this.mSystemPerformanceHinter.mTraceTag = 32L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.SurfaceControl lambda$new$2(int i) {
        android.view.SurfaceControl surfaceControl;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                surfaceControl = displayContent == null ? null : displayContent.getSurfaceControl();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return surfaceControl;
    }

    com.android.server.wm.DisplayAreaPolicy.Provider getDisplayAreaPolicyProvider() {
        return this.mDisplayAreaPolicyProvider;
    }

    private void setGlobalShadowSettings() {
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, com.android.internal.R.styleable.Lighting, 0, 0);
        float dimension = obtainStyledAttributes.getDimension(3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        float dimension2 = obtainStyledAttributes.getDimension(4, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        float dimension3 = obtainStyledAttributes.getDimension(2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        float f = obtainStyledAttributes.getFloat(0, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        float f2 = obtainStyledAttributes.getFloat(1, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        obtainStyledAttributes.recycle();
        android.view.SurfaceControl.setGlobalShadowSettings(new float[]{com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f}, new float[]{com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f2}, dimension, dimension2, dimension3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getTransitionAnimationScaleSetting() {
        return android.view.WindowManager.fixScale(android.provider.Settings.Global.getFloat(this.mContext.getContentResolver(), "transition_animation_scale", this.mContext.getResources().getFloat(android.R.dimen.chooser_preview_image_max_dimen)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getAnimatorDurationScaleSetting() {
        return android.view.WindowManager.fixScale(android.provider.Settings.Global.getFloat(this.mContext.getContentResolver(), "animator_duration_scale", this.mAnimatorDurationScaleSetting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getWindowAnimationScaleSetting() {
        return android.view.WindowManager.fixScale(android.provider.Settings.Global.getFloat(this.mContext.getContentResolver(), "window_animation_scale", this.mWindowAnimationScaleSetting));
    }

    public void onInitReady() {
        initPolicy();
        com.android.server.Watchdog.getInstance().addMonitor(this);
        createWatermark();
        showEmulatorDisplayOverlayIfNeeded();
    }

    public com.android.server.wm.InputManagerCallback getInputManagerCallback() {
        return this.mInputManagerCallback;
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            if (!(e instanceof java.lang.SecurityException)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.wtf(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3655576047584951173L, 0, "Window Manager Crash %s", java.lang.String.valueOf(e));
            }
            throw e;
        }
    }

    static boolean excludeWindowTypeFromTapOutTask(int i) {
        switch (i) {
            case 2000:
            case 2012:
            case 2019:
            case com.android.server.notification.NotificationShellCmd.NOTIFICATION_ID /* 2020 */:
            case 2040:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:122:0x0628, code lost:
    
        if (r8 != r39) goto L251;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x05ea A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x05f3 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x065c A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0685 A[Catch: all -> 0x0285, TRY_ENTER, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0690 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x069b A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x06ce  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0763 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x076b A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0789 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0798 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x07ce A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0825 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:185:0x082c A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0849 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0867 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0794  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0726 A[Catch: all -> 0x0285, TryCatch #1 {all -> 0x0285, blocks: (B:90:0x027a, B:92:0x0280, B:96:0x028a, B:97:0x056f, B:99:0x05d7, B:103:0x05de, B:107:0x05ea, B:110:0x05f3, B:112:0x05fb, B:113:0x0613, B:117:0x061a, B:119:0x0620, B:121:0x0624, B:123:0x063e, B:125:0x0644, B:127:0x064e, B:129:0x065c, B:131:0x067e, B:134:0x0685, B:135:0x068c, B:137:0x0690, B:138:0x0695, B:140:0x069b, B:141:0x06a0, B:144:0x06d1, B:148:0x06e9, B:150:0x0757, B:152:0x0763, B:153:0x0765, B:155:0x076b, B:156:0x076d, B:158:0x0771, B:160:0x077b, B:162:0x0789, B:166:0x0798, B:168:0x07a1, B:169:0x07b1, B:171:0x07b5, B:173:0x07bd, B:175:0x07ce, B:176:0x07d8, B:178:0x0816, B:181:0x081f, B:183:0x0825, B:185:0x082c, B:186:0x082f, B:188:0x0849, B:190:0x085e, B:191:0x086e, B:192:0x0874, B:196:0x0867, B:198:0x07c5, B:201:0x0779, B:204:0x0713, B:206:0x071d, B:209:0x0726, B:212:0x072f, B:213:0x073b, B:215:0x0741, B:216:0x0748, B:218:0x0750, B:220:0x062a, B:224:0x029e, B:226:0x02a6, B:228:0x02aa, B:229:0x02ae, B:231:0x02e7, B:233:0x02f3, B:234:0x02fa, B:235:0x02f6, B:351:0x0886, B:240:0x0330, B:242:0x0336, B:243:0x0355, B:246:0x035b, B:248:0x0361, B:249:0x0380, B:254:0x0388, B:256:0x038c, B:257:0x03a4, B:260:0x03a9, B:262:0x03ad, B:263:0x03c5, B:270:0x03da, B:272:0x03de, B:273:0x03ff, B:280:0x0411, B:282:0x0415, B:283:0x0436, B:289:0x0448, B:291:0x044c, B:292:0x046d, B:298:0x047f, B:300:0x0483, B:301:0x04a4, B:307:0x04b6, B:309:0x04c0, B:311:0x04c6, B:312:0x04e7, B:319:0x04fe, B:321:0x0502, B:322:0x0523, B:327:0x0530, B:329:0x0536, B:347:0x087e, B:348:0x0885), top: B:10:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x072b  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x06d0  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x063c  */
    /* JADX WARN: Type inference failed for: r6v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v20 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int addWindow(com.android.server.wm.Session session, android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, android.view.InputChannel inputChannel, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.graphics.Rect rect, float[] fArr) {
        int[] iArr;
        com.android.server.wm.WindowState windowState;
        int i5;
        int i6;
        int i7;
        int i8;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        com.android.server.wm.DisplayContent displayContent;
        int i9;
        char c;
        int i10;
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.WindowToken build;
        boolean z;
        boolean z2;
        boolean z3;
        ?? r6;
        com.android.server.wm.DisplayContent displayContent2;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        char c2;
        int windowType;
        array.set((android.view.InsetsSourceControl[]) null);
        int[] iArr2 = new int[1];
        boolean z8 = (layoutParams.privateFlags & 1048576) != 0;
        int checkAddPermission = this.mPolicy.checkAddPermission(layoutParams.type, z8, layoutParams.packageName, iArr2);
        if (checkAddPermission != 0) {
            return checkAddPermission;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        int i11 = layoutParams.type;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock2) {
            try {
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                if (!this.mDisplayReady) {
                    throw new java.lang.IllegalStateException("Display has not been initialialized");
                }
                if (session.isClientDead()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -3029436704707366221L, 0, "Attempted to add window with a client %s that is dead. Aborting.", java.lang.String.valueOf(session));
                    resetPriorityAfterLockedSection();
                    return -4;
                }
                com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2, layoutParams.token);
                if (displayContentOrCreate == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1303710477998542095L, 1, "Attempted to add window to a display that does not exist: %d. Aborting.", java.lang.Long.valueOf(i2));
                    resetPriorityAfterLockedSection();
                    return -9;
                }
                if (!displayContentOrCreate.hasAccess(session.mUid)) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8039410207325630747L, 1, "Attempted to add window to a display for which the application does not have access: %d.  Aborting.", java.lang.Long.valueOf(displayContentOrCreate.getDisplayId()));
                    resetPriorityAfterLockedSection();
                    return -9;
                }
                if (this.mWindowMap.containsKey(iWindow.asBinder())) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -3451016577701561221L, 0, "Window %s is already added", java.lang.String.valueOf(iWindow));
                    resetPriorityAfterLockedSection();
                    return -5;
                }
                if (i11 < 1000 || i11 > 1999) {
                    iArr = iArr2;
                    windowState = null;
                } else {
                    iArr = iArr2;
                    windowState = windowForClientLocked((com.android.server.wm.Session) null, layoutParams.token, false);
                    if (windowState == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 7245919222637411747L, 0, "Attempted to add window with token that is not a window: %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                        resetPriorityAfterLockedSection();
                        return -2;
                    }
                    if (windowState.mAttrs.type >= 1000 && windowState.mAttrs.type <= 1999) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -8579305050440451727L, 0, "Attempted to add window with token that is a sub-window: %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                        resetPriorityAfterLockedSection();
                        return -2;
                    }
                }
                if (i11 == 2037 || i11 == 2030) {
                    i5 = 1;
                    this.mDisplayManagerInternal.onPresentation(displayContentOrCreate.getDisplay().getDisplayId(), true);
                } else {
                    i5 = 1;
                }
                if (i11 == 2030 && !displayContentOrCreate.isPrivate()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1075040941127814341L, 0, "Attempted to add private presentation window to a non-private display.  Aborting.", null);
                    resetPriorityAfterLockedSection();
                    return -8;
                }
                if (i11 == 2037 && !displayContentOrCreate.getDisplay().isPublicPresentation()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 7599690046549866326L, 0, "Attempted to add presentation window to a non-suitable display.  Aborting.", null);
                    resetPriorityAfterLockedSection();
                    return -9;
                }
                int userId = android.os.UserHandle.getUserId(session.mUid);
                if (i3 != userId) {
                    try {
                        i6 = i11;
                        this.mAmInternal.handleIncomingUser(callingPid, callingUid, i3, false, 0, (java.lang.String) null, (java.lang.String) null);
                        i7 = callingPid;
                        i8 = i3;
                    } catch (java.lang.Exception e) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -2546047231197102533L, 1, "Trying to add window with invalid user=%d", java.lang.Long.valueOf(i3));
                        resetPriorityAfterLockedSection();
                        return -11;
                    }
                } else {
                    i6 = i11;
                    i7 = callingPid;
                    i8 = userId;
                }
                int i12 = windowState != null ? i5 : 0;
                com.android.server.wm.WindowToken windowToken = displayContentOrCreate.getWindowToken(i12 != 0 ? windowState.mAttrs.token : layoutParams.token);
                int i13 = i12 != 0 ? windowState.mAttrs.type : i6;
                int i14 = i7;
                android.os.IBinder iBinder = layoutParams.mWindowContextToken;
                if (windowToken == null) {
                    boolean z9 = z8;
                    int i15 = i6;
                    windowManagerGlobalLock = windowManagerGlobalLock2;
                    if (!unprivilegedAppCanCreateTokenWith(windowState, callingUid, i6, i13, layoutParams.token, layoutParams.packageName)) {
                        resetPriorityAfterLockedSection();
                        return -1;
                    }
                    if (i12 != 0) {
                        build = windowState.mToken;
                        i10 = callingUid;
                        displayContent = displayContentOrCreate;
                        i9 = i15;
                        activityRecord = null;
                        c = 3;
                        z = false;
                    } else if (this.mWindowContextListenerController.hasListener(iBinder)) {
                        i9 = i15;
                        displayContent = displayContentOrCreate;
                        build = new com.android.server.wm.WindowToken.Builder(this, layoutParams.token != null ? layoutParams.token : iBinder, i9).setDisplayContent(displayContent).setOwnerCanManageAppTokens(session.mCanAddInternalSystemWindow).setRoundedCornerOverlay(z9).setFromClientToken(true).setOptions(this.mWindowContextListenerController.getOptions(iBinder)).build();
                        i10 = callingUid;
                        activityRecord = null;
                        c = 3;
                        z = false;
                    } else {
                        displayContent = displayContentOrCreate;
                        i9 = i15;
                        build = new com.android.server.wm.WindowToken.Builder(this, layoutParams.token != null ? layoutParams.token : iWindow.asBinder(), i9).setDisplayContent(displayContent).setOwnerCanManageAppTokens(session.mCanAddInternalSystemWindow).setRoundedCornerOverlay(z9).build();
                        i10 = callingUid;
                        activityRecord = null;
                        c = 3;
                        z = false;
                    }
                } else {
                    windowManagerGlobalLock = windowManagerGlobalLock2;
                    displayContent = displayContentOrCreate;
                    int i16 = i5;
                    i9 = i6;
                    if (i13 < i16 || i13 > 99) {
                        c = 3;
                        if (i13 == 2011) {
                            if (windowToken.windowType != 2011) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -5450131464624918523L, 0, "Attempted to add input method window with bad token %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                                resetPriorityAfterLockedSection();
                                return -1;
                            }
                            i10 = callingUid;
                            activityRecord = null;
                        } else if (i13 == 2031) {
                            if (windowToken.windowType != 2031) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -6484128707849211138L, 0, "Attempted to add voice interaction window with bad token %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                                resetPriorityAfterLockedSection();
                                return -1;
                            }
                            i10 = callingUid;
                            activityRecord = null;
                        } else if (i13 == 2013) {
                            if (windowToken.windowType != 2013) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 7768591536609704658L, 0, "Attempted to add wallpaper window with bad token %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                                resetPriorityAfterLockedSection();
                                return -1;
                            }
                            i10 = callingUid;
                            activityRecord = null;
                        } else if (i13 == 2032) {
                            if (windowToken.windowType != 2032) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 7497077135474110999L, 0, "Attempted to add Accessibility overlay window with bad token %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                                resetPriorityAfterLockedSection();
                                return -1;
                            }
                            i10 = callingUid;
                            activityRecord = null;
                        } else if (i9 == 2005) {
                            i10 = callingUid;
                            boolean doesAddToastWindowRequireToken = doesAddToastWindowRequireToken(layoutParams.packageName, i10, windowState);
                            if (doesAddToastWindowRequireToken && windowToken.windowType != 2005) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8957851092580119204L, 0, "Attempted to add a toast window with bad token %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                                resetPriorityAfterLockedSection();
                                return -1;
                            }
                            build = windowToken;
                            z = doesAddToastWindowRequireToken;
                            activityRecord = null;
                        } else {
                            i10 = callingUid;
                            if (i9 == 2035) {
                                if (windowToken.windowType != 2035) {
                                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1945746969404688952L, 0, "Attempted to add QS dialog window with bad token %s.  Aborting.", java.lang.String.valueOf(layoutParams.token));
                                    resetPriorityAfterLockedSection();
                                    return -1;
                                }
                                activityRecord = null;
                            } else if (windowToken.asActivityRecord() != null) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3419934373251134563L, 1, "Non-null activity for system window of rootType=%d", java.lang.Long.valueOf(i13));
                                activityRecord = null;
                                layoutParams.token = null;
                                build = new com.android.server.wm.WindowToken.Builder(this, iWindow.asBinder(), i9).setDisplayContent(displayContent).setOwnerCanManageAppTokens(session.mCanAddInternalSystemWindow).build();
                                z = false;
                            } else {
                                activityRecord = null;
                            }
                        }
                        build = windowToken;
                    } else {
                        activityRecord = windowToken.asActivityRecord();
                        if (activityRecord == null) {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3713874359318494804L, 0, "Attempted to add window with non-application token .%s Aborting.", java.lang.String.valueOf(windowToken));
                            resetPriorityAfterLockedSection();
                            return -3;
                        }
                        if (activityRecord.getParent() == null) {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -6507147599943157469L, 0, "Attempted to add window with exiting application token .%s Aborting.", java.lang.String.valueOf(windowToken));
                            resetPriorityAfterLockedSection();
                            return -4;
                        }
                        c = 3;
                        if (i9 == 3) {
                            if (activityRecord.mStartingWindow != null) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1409483453189443362L, 0, "Attempted to add starting window to token with already existing starting window", null);
                                resetPriorityAfterLockedSection();
                                return -5;
                            }
                            if (activityRecord.mStartingData == null) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1806907994917883598L, 0, "Attempted to add starting window to token but already cleaned", null);
                                resetPriorityAfterLockedSection();
                                return -5;
                            }
                        }
                        build = windowToken;
                        i10 = callingUid;
                    }
                    z = false;
                }
                com.android.server.wm.ActivityRecord activityRecord2 = activityRecord;
                int i17 = i10;
                com.android.server.wm.WindowToken windowToken2 = build;
                com.android.server.wm.WindowState windowState2 = windowState;
                com.android.server.wm.DisplayContent displayContent3 = displayContent;
                int i18 = i9;
                com.android.server.wm.WindowState windowState3 = new com.android.server.wm.WindowState(this, session, iWindow, windowToken2, windowState2, iArr[0], layoutParams, i, session.mUid, i8, session.mCanAddInternalSystemWindow);
                com.android.server.wm.DisplayPolicy displayPolicy = displayContent3.getDisplayPolicy();
                displayPolicy.adjustWindowParamsLw(windowState3, windowState3.mAttrs);
                layoutParams.flags = sanitizeFlagSlippery(layoutParams.flags, windowState3.getName(), i17, i14);
                layoutParams.inputFeatures = sanitizeSpyWindow(layoutParams.inputFeatures, windowState3.getName(), i17, i14);
                windowState3.setRequestedVisibleTypes(i4);
                int validateAddingWindowLw = displayPolicy.validateAddingWindowLw(layoutParams, i14, i17);
                if (validateAddingWindowLw != 0) {
                    resetPriorityAfterLockedSection();
                    return validateAddingWindowLw;
                }
                if (inputChannel != null) {
                    z2 = true;
                    r6 = 1;
                    if ((layoutParams.inputFeatures & 1) == 0) {
                        z3 = true;
                        if (z3) {
                            windowState3.openInputChannel(inputChannel);
                        }
                        if (i18 != 2005) {
                            com.android.server.wm.DisplayContent displayContent4 = displayContent3;
                            if (!displayContent4.canAddToastWindowForUid(i17)) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1161056447389155729L, 0, "Adding more than one toast window for UID at a time.", null);
                                resetPriorityAfterLockedSection();
                                return -5;
                            }
                            if (!z && (layoutParams.flags & 8) != 0 && displayContent4.mCurrentFocus != null) {
                                int i19 = displayContent4.mCurrentFocus.mOwnerUid;
                                displayContent2 = displayContent4;
                            }
                            this.mH.sendMessageDelayed(this.mH.obtainMessage(52, windowState3), windowState3.mAttrs.hideTimeoutMilliseconds);
                            displayContent2 = displayContent4;
                        } else {
                            displayContent2 = displayContent3;
                        }
                        if (!windowState3.isChildWindow() && this.mWindowContextListenerController.hasListener(iBinder)) {
                            windowType = this.mWindowContextListenerController.getWindowType(iBinder);
                            android.os.Bundle options = this.mWindowContextListenerController.getOptions(iBinder);
                            if (i18 == windowType) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -7518552252637236411L, 5, "Window types in WindowContext and LayoutParams.type should match! Type from LayoutParams is %d, but type from WindowContext is %d", java.lang.Long.valueOf(i18), java.lang.Long.valueOf(windowType));
                                if (!android.window.WindowProviderService.isWindowProviderService(options)) {
                                    resetPriorityAfterLockedSection();
                                    return -10;
                                }
                            } else {
                                this.mWindowContextListenerController.updateContainerForWindowContextListener(iBinder, windowToken2);
                            }
                        }
                        if (displayContent2.mCurrentFocus == null) {
                            displayContent2.mWinAddedSinceNullFocus.add(windowState3);
                        }
                        if (excludeWindowTypeFromTapOutTask(i18)) {
                            displayContent2.mTapExcludedWindows.add(windowState3);
                        }
                        windowState3.mSession.onWindowAdded(windowState3);
                        this.mWindowMap.put(iWindow.asBinder(), windowState3);
                        windowState3.initAppOpsState();
                        windowState3.setHiddenWhileSuspended(this.mPmInternal.isPackageSuspended(windowState3.getOwningPackage(), android.os.UserHandle.getUserId(windowState3.getOwningUid())));
                        windowState3.setForceHideNonSystemOverlayWindowIfNeeded(this.mHidingNonSystemOverlayWindows.isEmpty() ? r6 : false);
                        windowState3.mToken.addWindow(windowState3);
                        displayPolicy.addWindowLw(windowState3, layoutParams);
                        displayPolicy.setDropInputModePolicy(windowState3, windowState3.mAttrs);
                        if (i18 == 3 || activityRecord2 == null) {
                            if (i18 != 2011 && (windowState3.getAttrs().flags & 16) == 0) {
                                displayContent2.setInputMethodWindowLocked(windowState3);
                                z4 = false;
                            } else if (i18 != 2012) {
                                displayContent2.computeImeTarget(r6);
                                z4 = false;
                            } else if (i18 == 2013) {
                                displayContent2.mWallpaperController.clearLastWallpaperTimeoutTime();
                                displayContent2.pendingLayoutChanges |= 4;
                            } else if (windowState3.hasWallpaper()) {
                                displayContent2.pendingLayoutChanges |= 4;
                            } else if (displayContent2.mWallpaperController.isBelowWallpaperTarget(windowState3)) {
                                displayContent2.pendingLayoutChanges |= 4;
                            }
                            com.android.server.wm.WindowStateAnimator windowStateAnimator = windowState3.mWinAnimator;
                            windowStateAnimator.mEnterAnimationPending = r6;
                            windowStateAnimator.mEnteringAnimation = r6;
                            if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                                validateAddingWindowLw |= 4;
                            }
                            if (displayContent2.isInTouchMode()) {
                                validateAddingWindowLw |= 1;
                            }
                            if (windowState3.mActivityRecord != null || windowState3.mActivityRecord.isClientVisible()) {
                                validateAddingWindowLw |= 2;
                            }
                            displayContent2.getInputMonitor().setUpdateInputWindowsNeededLw();
                            if (windowState3.canReceiveKeys()) {
                                z5 = z4;
                                z6 = false;
                            } else {
                                z6 = updateFocusedWindowLocked(r6, false);
                                z5 = z6 ? false : z4;
                            }
                            if (z5) {
                                displayContent2.computeImeTarget(r6);
                                if (windowState3.isImeOverlayLayeringTarget()) {
                                    dispatchImeTargetOverlayVisibilityChanged(iWindow.asBinder(), windowState3.mAttrs.type, windowState3.isVisibleRequestedOrAdding(), false);
                                }
                            }
                            if (windowState3.mActivityRecord == null && windowState3.mActivityRecord.isEmbedded()) {
                                windowState3.getTask().assignChildLayers();
                            } else {
                                windowState3.getParent().assignChildLayers();
                            }
                            if (z6) {
                                displayContent2.getInputMonitor().setInputFocusLw(displayContent2.mCurrentFocus, false);
                            }
                            displayContent2.getInputMonitor().updateInputWindowsLw(false);
                            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -2829980616540274784L, 0, null, java.lang.String.valueOf(iWindow.asBinder()), java.lang.String.valueOf(windowState3), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
                            z7 = (windowState3.isVisibleRequestedOrAdding() || !displayContent2.updateOrientation()) ? false : r6;
                            if (windowState3.providesDisplayDecorInsets()) {
                                z7 |= displayPolicy.updateDecorInsetsInfo();
                            }
                            if (z7) {
                                displayContent2.sendNewConfiguration();
                            }
                            displayContent2.getInsetsStateController().updateAboveInsetsState(false);
                            insetsState.set(windowState3.getCompatInsetsState(), (boolean) r6);
                            getInsetsSourceControls(windowState3, array);
                            if (windowState3.mLayoutAttached) {
                                c2 = 0;
                                rect.set(0, 0, -1, -1);
                            } else {
                                rect.set(windowState3.getParentWindow().getFrame());
                                if (windowState3.mInvGlobalScale != 1.0f) {
                                    rect.scale(windowState3.mInvGlobalScale);
                                    c2 = 0;
                                } else {
                                    c2 = 0;
                                }
                            }
                            fArr[c2] = windowState3.getCompatScaleForClient();
                            resetPriorityAfterLockedSection();
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return validateAddingWindowLw;
                        }
                        activityRecord2.attachStartingWindow(windowState3);
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -6055615852717459196L, 0, null, java.lang.String.valueOf(activityRecord2), java.lang.String.valueOf(windowState3));
                        z4 = r6;
                        com.android.server.wm.WindowStateAnimator windowStateAnimator2 = windowState3.mWinAnimator;
                        windowStateAnimator2.mEnterAnimationPending = r6;
                        windowStateAnimator2.mEnteringAnimation = r6;
                        if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                        }
                        if (displayContent2.isInTouchMode()) {
                        }
                        if (windowState3.mActivityRecord != null) {
                        }
                        validateAddingWindowLw |= 2;
                        displayContent2.getInputMonitor().setUpdateInputWindowsNeededLw();
                        if (windowState3.canReceiveKeys()) {
                        }
                        if (z5) {
                        }
                        if (windowState3.mActivityRecord == null) {
                        }
                        windowState3.getParent().assignChildLayers();
                        if (z6) {
                        }
                        displayContent2.getInputMonitor().updateInputWindowsLw(false);
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -2829980616540274784L, 0, null, java.lang.String.valueOf(iWindow.asBinder()), java.lang.String.valueOf(windowState3), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
                        if (windowState3.isVisibleRequestedOrAdding()) {
                        }
                        if (windowState3.providesDisplayDecorInsets()) {
                        }
                        if (z7) {
                        }
                        displayContent2.getInsetsStateController().updateAboveInsetsState(false);
                        insetsState.set(windowState3.getCompatInsetsState(), (boolean) r6);
                        getInsetsSourceControls(windowState3, array);
                        if (windowState3.mLayoutAttached) {
                        }
                        fArr[c2] = windowState3.getCompatScaleForClient();
                        resetPriorityAfterLockedSection();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return validateAddingWindowLw;
                    }
                } else {
                    z2 = true;
                }
                z3 = false;
                r6 = z2;
                if (z3) {
                }
                if (i18 != 2005) {
                }
                if (!windowState3.isChildWindow()) {
                    windowType = this.mWindowContextListenerController.getWindowType(iBinder);
                    android.os.Bundle options2 = this.mWindowContextListenerController.getOptions(iBinder);
                    if (i18 == windowType) {
                    }
                }
                if (displayContent2.mCurrentFocus == null) {
                }
                if (excludeWindowTypeFromTapOutTask(i18)) {
                }
                windowState3.mSession.onWindowAdded(windowState3);
                this.mWindowMap.put(iWindow.asBinder(), windowState3);
                windowState3.initAppOpsState();
                windowState3.setHiddenWhileSuspended(this.mPmInternal.isPackageSuspended(windowState3.getOwningPackage(), android.os.UserHandle.getUserId(windowState3.getOwningUid())));
                windowState3.setForceHideNonSystemOverlayWindowIfNeeded(this.mHidingNonSystemOverlayWindows.isEmpty() ? r6 : false);
                windowState3.mToken.addWindow(windowState3);
                displayPolicy.addWindowLw(windowState3, layoutParams);
                displayPolicy.setDropInputModePolicy(windowState3, windowState3.mAttrs);
                if (i18 == 3) {
                }
                if (i18 != 2011) {
                }
                if (i18 != 2012) {
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private boolean unprivilegedAppCanCreateTokenWith(com.android.server.wm.WindowState windowState, int i, int i2, int i3, android.os.IBinder iBinder, java.lang.String str) {
        if (i3 >= 1 && i3 <= 99) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -7315179333005789167L, 0, "Attempted to add application window with unknown token %s.  Aborting.", java.lang.String.valueOf(iBinder));
            return false;
        }
        if (i3 == 2011) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -7547709658889961930L, 0, "Attempted to add input method window with unknown token %s.  Aborting.", java.lang.String.valueOf(iBinder));
            return false;
        }
        if (i3 == 2031) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3009864422591182484L, 0, "Attempted to add voice interaction window with unknown token %s.  Aborting.", java.lang.String.valueOf(iBinder));
            return false;
        }
        if (i3 == 2013) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -2639914438438144071L, 0, "Attempted to add wallpaper window with unknown token %s.  Aborting.", java.lang.String.valueOf(iBinder));
            return false;
        }
        if (i3 == 2035) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -7529563697886120786L, 0, "Attempted to add QS dialog window with unknown token %s.  Aborting.", java.lang.String.valueOf(iBinder));
            return false;
        }
        if (i3 == 2032) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 4253401518117961686L, 0, "Attempted to add Accessibility overlay window with unknown token %s.  Aborting.", java.lang.String.valueOf(iBinder));
            return false;
        }
        if (i2 != 2005 || !doesAddToastWindowRequireToken(str, i, windowState)) {
            return true;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 5834230650841873680L, 0, "Attempted to add a toast window with unknown token %s.  Aborting.", java.lang.String.valueOf(iBinder));
        return false;
    }

    private com.android.server.wm.DisplayContent getDisplayContentOrCreate(int i, android.os.IBinder iBinder) {
        com.android.server.wm.WindowToken windowToken;
        if (iBinder != null && (windowToken = this.mRoot.getWindowToken(iBinder)) != null) {
            return windowToken.getDisplayContent();
        }
        return this.mRoot.getDisplayContentOrCreate(i);
    }

    private boolean doesAddToastWindowRequireToken(java.lang.String str, int i, com.android.server.wm.WindowState windowState) {
        if (windowState != null) {
            return windowState.mActivityRecord != null && windowState.mActivityRecord.mTargetSdk >= 26;
        }
        android.content.pm.ApplicationInfo applicationInfo = this.mPmInternal.getApplicationInfo(str, 0L, 1000, android.os.UserHandle.getUserId(i));
        if (applicationInfo != null && applicationInfo.uid == i) {
            return applicationInfo.targetSdkVersion >= 26;
        }
        throw new java.lang.SecurityException("Package " + str + " not in UID " + i);
    }

    public void refreshScreenCaptureDisabled() {
        if (android.os.Binder.getCallingUid() != MY_UID) {
            throw new java.lang.SecurityException("Only system can call refreshScreenCaptureDisabled.");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.refreshSecureSurfaceState();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    void removeClientToken(com.android.server.wm.Session session, android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iBinder, false);
                if (windowForClientLocked != null) {
                    windowForClientLocked.removeIfPossible();
                    resetPriorityAfterLockedSection();
                } else {
                    this.mEmbeddedWindowController.remove(iBinder);
                    resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void postWindowRemoveCleanupLocked(com.android.server.wm.WindowState windowState) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 5265273548711408921L, 0, null, java.lang.String.valueOf(windowState));
        this.mWindowMap.remove(windowState.mClient.asBinder());
        com.android.server.wm.DisplayContent displayContent = windowState.getDisplayContent();
        displayContent.getDisplayRotation().markForSeamlessRotation(windowState, false);
        windowState.resetAppOpsState();
        if (displayContent.mCurrentFocus == null) {
            displayContent.mWinRemovedSinceNullFocus.add(windowState);
        }
        this.mEmbeddedWindowController.onWindowRemoved(windowState);
        this.mResizingWindows.remove(windowState);
        updateNonSystemOverlayWindowsVisibilityIfNeeded(windowState, false);
        this.mWindowsChanged = true;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_MOVEMENT, -3847568084407666790L, 0, null, java.lang.String.valueOf(windowState));
        com.android.server.wm.DisplayContent displayContent2 = windowState.getDisplayContent();
        if (displayContent2.mInputMethodWindow == windowState) {
            displayContent2.setInputMethodWindowLocked(null);
        }
        com.android.server.wm.WindowToken windowToken = windowState.mToken;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 1419572818243106725L, 0, null, java.lang.String.valueOf(windowState), java.lang.String.valueOf(windowToken));
        if (windowToken.isEmpty() && !windowToken.mPersistOnEmpty) {
            windowToken.removeImmediately();
        }
        if (windowState.mActivityRecord != null) {
            windowState.mActivityRecord.postWindowRemoveStartingWindowCleanup(windowState);
        }
        if (windowState.mAttrs.type == 2013) {
            displayContent.mWallpaperController.clearLastWallpaperTimeoutTime();
            displayContent.pendingLayoutChanges |= 4;
        } else if (displayContent.mWallpaperController.isWallpaperTarget(windowState)) {
            displayContent.pendingLayoutChanges |= 4;
        }
        if (!this.mWindowPlacerLocked.isInLayout()) {
            displayContent.assignWindowLayers(true);
            this.mWindowPlacerLocked.performSurfacePlacement();
            if (windowState.mActivityRecord != null) {
                windowState.mActivityRecord.updateReportedVisibilityLocked();
            }
        }
        displayContent.getInputMonitor().updateInputWindowsLw(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHiddenWhileSuspendedState(android.util.ArraySet<java.lang.String> arraySet, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.updateHiddenWhileSuspendedState(arraySet, z);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppOpsState() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.updateAppOpsState();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    static void logSurface(com.android.server.wm.WindowState windowState, java.lang.String str, boolean z) {
        java.lang.String str2 = "  SURFACE " + str + ": " + windowState;
        if (z) {
            logWithStack(TAG, str2);
        } else {
            android.util.Slog.i(TAG, str2);
        }
    }

    static void logWithStack(java.lang.String str, java.lang.String str2) {
        android.util.Slog.i(str, str2, (java.lang.Throwable) null);
    }

    void clearTouchableRegion(com.android.server.wm.Session session, android.view.IWindow iWindow) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    windowForClientLocked(session, iWindow, false).clearClientTouchableRegion();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void setInsetsWindow(com.android.server.wm.Session session, android.view.IWindow iWindow, int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Region region) {
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked != null) {
                        windowForClientLocked.mGivenInsetsPending = false;
                        windowForClientLocked.mGivenContentInsets.set(rect);
                        windowForClientLocked.mGivenVisibleInsets.set(rect2);
                        windowForClientLocked.mGivenTouchableRegion.set(region);
                        windowForClientLocked.mTouchableInsets = i;
                        if (windowForClientLocked.mGlobalScale != 1.0f) {
                            windowForClientLocked.mGivenContentInsets.scale(windowForClientLocked.mGlobalScale);
                            windowForClientLocked.mGivenVisibleInsets.scale(windowForClientLocked.mGlobalScale);
                            windowForClientLocked.mGivenTouchableRegion.scale(windowForClientLocked.mGlobalScale);
                        }
                        windowForClientLocked.setDisplayLayoutNeeded();
                        windowForClientLocked.updateSourceFrame(windowForClientLocked.getFrame());
                        this.mWindowPlacerLocked.performSurfacePlacement();
                        windowForClientLocked.getDisplayContent().getInputMonitor().updateInputWindowsLw(true);
                        if (this.mAccessibilityController.hasCallbacks()) {
                            this.mAccessibilityController.onSomeWindowResizedOrMovedWithCallingUid(callingUid, windowForClientLocked.getDisplayContent().getDisplayId());
                        }
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onRectangleOnScreenRequested(android.os.IBinder iBinder, android.graphics.Rect rect) {
        com.android.server.wm.WindowState windowState;
        com.android.server.wm.AccessibilityController.AccessibilityControllerInternalImpl accessibilityControllerInternal = com.android.server.wm.AccessibilityController.getAccessibilityControllerInternal(this);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (accessibilityControllerInternal.hasWindowManagerEventDispatcher() && (windowState = this.mWindowMap.get(iBinder)) != null) {
                    accessibilityControllerInternal.onRectangleOnScreenRequested(windowState.getDisplayId(), rect);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public android.view.IWindowId getWindowId(android.os.IBinder iBinder) {
        com.android.server.wm.WindowState.WindowId windowId;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowState = this.mWindowMap.get(iBinder);
                windowId = windowState != null ? windowState.mWindowId : null;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return windowId;
    }

    public void pokeDrawLock(com.android.server.wm.Session session, android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iBinder, false);
                if (windowForClientLocked != null) {
                    windowForClientLocked.pokeDrawLockLw(this.mDrawLockTimeoutMillis);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    private boolean hasStatusBarPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.STATUS_BAR", i, i2) == 0;
    }

    public boolean cancelDraw(com.android.server.wm.Session session, android.view.IWindow iWindow) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean cancelAndRedraw = windowForClientLocked.cancelAndRedraw();
                resetPriorityAfterLockedSection();
                return cancelAndRedraw;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int relayoutWindow(com.android.server.wm.Session session, android.view.IWindow iWindow, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, android.window.ClientWindowFrames clientWindowFrames, android.util.MergedConfiguration mergedConfiguration, android.view.SurfaceControl surfaceControl, android.view.InsetsState insetsState, android.view.InsetsSourceControl.Array array, android.os.Bundle bundle) {
        int i7;
        long j;
        int i8;
        int i9;
        boolean z;
        android.view.SurfaceControl surfaceControl2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i10;
        long j2;
        int i11;
        if (array != null) {
            array.set((android.view.InsetsSourceControl[]) null);
        }
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                if (windowForClientLocked.mRelayoutSeq < i5) {
                    windowForClientLocked.mRelayoutSeq = i5;
                } else if (windowForClientLocked.mRelayoutSeq > i5) {
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                if (windowForClientLocked.cancelAndRedraw() && windowForClientLocked.mPrepareSyncSeqId <= i6) {
                    i7 = 16;
                } else {
                    i7 = 0;
                }
                com.android.server.wm.DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                com.android.server.wm.DisplayPolicy displayPolicy = displayContent.getDisplayPolicy();
                com.android.server.wm.WindowStateAnimator windowStateAnimator = windowForClientLocked.mWinAnimator;
                if (i3 != 8) {
                    windowForClientLocked.setRequestedSize(i, i2);
                }
                if (layoutParams == null) {
                    j = clearCallingIdentity;
                    i8 = 0;
                    i9 = 0;
                    z = false;
                } else {
                    displayPolicy.adjustWindowParamsLw(windowForClientLocked, layoutParams);
                    layoutParams.flags = sanitizeFlagSlippery(layoutParams.flags, windowForClientLocked.getName(), callingUid, callingPid);
                    layoutParams.inputFeatures = sanitizeSpyWindow(layoutParams.inputFeatures, windowForClientLocked.getName(), callingUid, callingPid);
                    int i12 = (layoutParams.systemUiVisibility | layoutParams.subtreeSystemUiVisibility) & 134152192;
                    if (i12 != 0 && !hasStatusBarPermission(callingPid, callingUid)) {
                        i12 = 0;
                    }
                    windowForClientLocked.mDisableFlags = i12;
                    if (windowForClientLocked.mAttrs.type != layoutParams.type) {
                        throw new java.lang.IllegalArgumentException("Window type can not be changed after the window is added.");
                    }
                    if (windowForClientLocked.mAttrs.providedInsets == null && layoutParams.providedInsets == null) {
                        j = clearCallingIdentity;
                    } else {
                        if (windowForClientLocked.mAttrs.providedInsets == null || layoutParams.providedInsets == null || windowForClientLocked.mAttrs.providedInsets.length != layoutParams.providedInsets.length) {
                            throw new java.lang.IllegalArgumentException("Insets amount can not be changed after the window is added.");
                        }
                        int length = layoutParams.providedInsets.length;
                        int i13 = 0;
                        while (i13 < length) {
                            if (!windowForClientLocked.mAttrs.providedInsets[i13].idEquals(layoutParams.providedInsets[i13])) {
                                throw new java.lang.IllegalArgumentException("Insets ID can not be changed after the window is added.");
                            }
                            android.view.InsetsFrameProvider.InsetsSizeOverride[] insetsSizeOverrides = windowForClientLocked.mAttrs.providedInsets[i13].getInsetsSizeOverrides();
                            android.view.InsetsFrameProvider.InsetsSizeOverride[] insetsSizeOverrides2 = layoutParams.providedInsets[i13].getInsetsSizeOverrides();
                            if (insetsSizeOverrides != null || insetsSizeOverrides2 != null) {
                                if (insetsSizeOverrides != null && insetsSizeOverrides2 != null) {
                                    i10 = length;
                                    j2 = clearCallingIdentity;
                                    if (insetsSizeOverrides.length == insetsSizeOverrides2.length) {
                                        int length2 = insetsSizeOverrides.length;
                                        int i14 = 0;
                                        while (i14 < length2) {
                                            int i15 = length2;
                                            if (insetsSizeOverrides[i14].getWindowType() == insetsSizeOverrides2[i14].getWindowType()) {
                                                i14++;
                                                length2 = i15;
                                            } else {
                                                throw new java.lang.IllegalArgumentException("Insets override types can not be changed after the window is added.");
                                            }
                                        }
                                    }
                                }
                                throw new java.lang.IllegalArgumentException("Insets override types can not be changed after the window is added.");
                            }
                            i10 = length;
                            j2 = clearCallingIdentity;
                            i13++;
                            length = i10;
                            clearCallingIdentity = j2;
                        }
                        j = clearCallingIdentity;
                    }
                    boolean isWindowTrustedOverlay = windowForClientLocked.isWindowTrustedOverlay();
                    int i16 = windowForClientLocked.mAttrs.flags ^ layoutParams.flags;
                    int i17 = windowForClientLocked.mAttrs.privateFlags ^ layoutParams.privateFlags;
                    i9 = windowForClientLocked.mAttrs.copyFrom(layoutParams);
                    boolean z6 = (i9 & 1) != 0;
                    if (z6 || (i9 & 16384) != 0) {
                        windowForClientLocked.mLayoutNeeded = true;
                    }
                    if (z6 && windowForClientLocked.providesDisplayDecorInsets()) {
                        z = displayPolicy.updateDecorInsetsInfo();
                    } else {
                        z = false;
                    }
                    if (isWindowTrustedOverlay != windowForClientLocked.isWindowTrustedOverlay()) {
                        windowForClientLocked.updateTrustedOverlay();
                    }
                    if (windowForClientLocked.mActivityRecord != null && ((i16 & 524288) != 0 || (4194304 & i16) != 0)) {
                        windowForClientLocked.mActivityRecord.checkKeyguardFlagsChanged();
                    }
                    if ((i17 & 524288) != 0) {
                        updateNonSystemOverlayWindowsVisibilityIfNeeded(windowForClientLocked, windowForClientLocked.mWinAnimator.getShown());
                    }
                    if ((131072 & i9) != 0) {
                        windowStateAnimator.setColorSpaceAgnosticLocked((windowForClientLocked.mAttrs.privateFlags & 16777216) != 0);
                    }
                    if (!displayContent.mDwpcHelper.hasController() || windowForClientLocked.mActivityRecord == null) {
                        i11 = i16;
                    } else if (windowForClientLocked.mRelayoutCalled && i16 == 0 && i17 == 0) {
                        i11 = i16;
                    } else {
                        int i18 = !windowForClientLocked.mRelayoutCalled ? windowForClientLocked.mAttrs.flags : i16;
                        if (!windowForClientLocked.mRelayoutCalled) {
                            i17 = windowForClientLocked.mAttrs.privateFlags;
                        }
                        i11 = i16;
                        if (!displayContent.mDwpcHelper.keepActivityOnWindowFlagsChanged(windowForClientLocked.mActivityRecord.info, i18, i17, windowForClientLocked.mAttrs.flags, windowForClientLocked.mAttrs.privateFlags)) {
                            this.mH.sendMessage(this.mH.obtainMessage(65, windowForClientLocked.mActivityRecord.getTask()));
                            android.util.Slog.w(TAG, "Activity " + windowForClientLocked.mActivityRecord + " window flag changed, can't remain on display " + displayContent.getDisplayId());
                            resetPriorityAfterLockedSection();
                            return 0;
                        }
                    }
                    i8 = i11;
                }
                if ((i9 & 128) != 0) {
                    windowStateAnimator.mAlpha = layoutParams.alpha;
                }
                windowForClientLocked.setWindowScale(windowForClientLocked.mRequestedWidth, windowForClientLocked.mRequestedHeight);
                if (windowForClientLocked.mAttrs.surfaceInsets.left != 0 || windowForClientLocked.mAttrs.surfaceInsets.top != 0 || windowForClientLocked.mAttrs.surfaceInsets.right != 0 || windowForClientLocked.mAttrs.surfaceInsets.bottom != 0) {
                    windowStateAnimator.setOpaqueLocked(false);
                }
                int i19 = windowForClientLocked.mViewVisibility;
                boolean z7 = (131080 & i8) != 0 || ((i19 == 4 || i19 == 8) && i3 == 0);
                boolean z8 = (windowForClientLocked.mViewVisibility == i3 && (i8 & 8) == 0 && windowForClientLocked.mRelayoutCalled) ? false : true;
                boolean z9 = (windowForClientLocked.mViewVisibility != i3 && windowForClientLocked.hasWallpaper()) | ((i8 & 1048576) != 0);
                if ((i8 & 8192) != 0) {
                    windowForClientLocked.setSecureLocked(windowForClientLocked.isSecureLocked());
                }
                boolean isVisible = windowForClientLocked.isVisible();
                windowForClientLocked.mRelayoutCalled = true;
                windowForClientLocked.mInRelayout = true;
                windowForClientLocked.setViewVisibility(i3);
                boolean z10 = z7;
                boolean z11 = z8;
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, 8312693933819247897L, 20, null, java.lang.String.valueOf(windowForClientLocked), java.lang.Long.valueOf(i19), java.lang.Long.valueOf(i3), java.lang.String.valueOf(new java.lang.RuntimeException().fillInStackTrace()));
                windowForClientLocked.setDisplayLayoutNeeded();
                windowForClientLocked.mGivenInsetsPending = (i4 & 1) != 0;
                boolean z12 = i3 == 0 && (windowForClientLocked.mActivityRecord == null || windowForClientLocked.mAttrs.type == 3 || windowForClientLocked.mActivityRecord.isClientVisible());
                if (!z12 && windowStateAnimator.hasSurface() && !windowForClientLocked.mAnimatingExit) {
                    i7 |= 2;
                    if (z9) {
                        displayContent.mWallpaperController.adjustWallpaperWindows();
                    }
                    tryStartExitingAnimation(windowForClientLocked, windowStateAnimator);
                }
                if (z12) {
                    surfaceControl2 = surfaceControl;
                    if (surfaceControl2 != null) {
                        try {
                            i7 = createSurfaceControl(surfaceControl2, i7, windowForClientLocked, windowStateAnimator);
                        } catch (java.lang.Exception e) {
                            displayContent.getInputMonitor().updateInputWindowsLw(true);
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8319702790708803735L, 0, "Exception thrown when creating surface for client %s (%s). %s", java.lang.String.valueOf(iWindow), java.lang.String.valueOf(windowForClientLocked.mAttrs.getTitle()), java.lang.String.valueOf(e));
                            android.os.Binder.restoreCallingIdentity(j);
                            resetPriorityAfterLockedSection();
                            return 0;
                        }
                    }
                } else {
                    surfaceControl2 = surfaceControl;
                }
                this.mWindowPlacerLocked.performSurfacePlacement(true);
                if (z12) {
                    android.os.Trace.traceBegin(32L, "relayoutWindow: viewVisibility_1");
                    i7 = windowForClientLocked.relayoutVisibleWindow(i7);
                    if ((i7 & 1) == 0) {
                        z5 = z11;
                    } else {
                        z5 = true;
                    }
                    if (windowForClientLocked.mAttrs.type == 2011 && displayContent.mInputMethodWindow == null) {
                        displayContent.setInputMethodWindowLocked(windowForClientLocked);
                        z2 = true;
                    } else {
                        z2 = z10;
                    }
                    windowForClientLocked.adjustStartingWindowFlags();
                    android.os.Trace.traceEnd(32L);
                    z3 = z5;
                } else {
                    android.os.Trace.traceBegin(32L, "relayoutWindow: viewVisibility_2");
                    windowStateAnimator.mEnterAnimationPending = false;
                    windowStateAnimator.mEnteringAnimation = false;
                    if (surfaceControl2 != null) {
                        if (i3 == 0 && windowStateAnimator.hasSurface()) {
                            android.os.Trace.traceBegin(32L, "relayoutWindow: getSurface");
                            windowStateAnimator.mSurfaceController.getSurfaceControl(surfaceControl2);
                            android.os.Trace.traceEnd(32L);
                        } else {
                            try {
                                android.os.Trace.traceBegin(32L, "wmReleaseOutSurface_" + ((java.lang.Object) windowForClientLocked.mAttrs.getTitle()));
                                surfaceControl.release();
                                android.os.Trace.traceEnd(32L);
                            } finally {
                                android.os.Trace.traceEnd(32L);
                            }
                        }
                    }
                    android.os.Trace.traceEnd(32L);
                    z2 = z10;
                    z3 = z11;
                }
                if (z3 && updateFocusedWindowLocked(0, true)) {
                    z2 = false;
                }
                boolean z13 = (i7 & 1) != 0;
                if (z2) {
                    displayContent.computeImeTarget(true);
                    if (z13) {
                        displayContent.assignWindowLayers(false);
                    }
                }
                if (z9) {
                    displayContent.pendingLayoutChanges |= 4;
                }
                if (windowForClientLocked.mActivityRecord != null) {
                    displayContent.mUnknownAppVisibilityController.notifyRelayouted(windowForClientLocked.mActivityRecord);
                }
                android.os.Trace.traceBegin(32L, "relayoutWindow: updateOrientation");
                boolean updateOrientation = displayContent.updateOrientation() | z;
                android.os.Trace.traceEnd(32L);
                if (z13 && windowForClientLocked.mIsWallpaper) {
                    displayContent.mWallpaperController.updateWallpaperOffset(windowForClientLocked, false);
                }
                if (windowForClientLocked.mActivityRecord != null) {
                    windowForClientLocked.mActivityRecord.updateReportedVisibilityLocked();
                }
                if (displayPolicy.areSystemBarsForcedConsumedLw()) {
                    i7 |= 8;
                }
                if (!windowForClientLocked.isGoneForLayout()) {
                    windowForClientLocked.mResizedWhileGone = false;
                }
                if (clientWindowFrames != null && mergedConfiguration != null) {
                    windowForClientLocked.fillClientWindowFramesAndConfiguration(clientWindowFrames, mergedConfiguration, false, z12);
                    windowForClientLocked.onResizeHandled();
                }
                if (insetsState != null) {
                    z4 = true;
                    insetsState.set(windowForClientLocked.getCompatInsetsState(), true);
                } else {
                    z4 = true;
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, 212929172223901460L, 12, null, java.lang.String.valueOf(windowForClientLocked), java.lang.Boolean.valueOf(z3));
                windowForClientLocked.mInRelayout = false;
                boolean z14 = windowForClientLocked.isVisible() != isVisible ? z4 : false;
                if (windowForClientLocked.isImeOverlayLayeringTarget() && z14) {
                    dispatchImeTargetOverlayVisibilityChanged(iWindow.asBinder(), windowForClientLocked.mAttrs.type, windowForClientLocked.isVisible(), false);
                }
                if (windowForClientLocked.getDisplayContent().getImeInputTarget() != windowForClientLocked) {
                    z4 = false;
                }
                if (z4 && z14) {
                    dispatchImeInputTargetVisibilityChanged(windowForClientLocked.mClient.asBinder(), windowForClientLocked.isVisible(), false);
                }
                if (bundle != null) {
                    if (windowForClientLocked.syncNextBuffer() && i3 == 0 && windowForClientLocked.mSyncSeqId > i6) {
                        r5 = windowForClientLocked.shouldSyncWithBuffers() ? windowForClientLocked.mSyncSeqId : -1;
                        windowForClientLocked.markRedrawForSyncReported();
                    }
                    bundle.putInt("seqid", r5);
                }
                if (updateOrientation) {
                    android.os.Trace.traceBegin(32L, "relayoutWindow: postNewConfigurationToHandler");
                    displayContent.sendNewConfiguration();
                }
                if (array != null) {
                    getInsetsSourceControls(windowForClientLocked, array);
                }
                resetPriorityAfterLockedSection();
                android.os.Binder.restoreCallingIdentity(j);
                return i7;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void getInsetsSourceControls(com.android.server.wm.WindowState windowState, android.view.InsetsSourceControl.Array array) {
        android.view.InsetsSourceControl[] controlsForDispatch = windowState.getDisplayContent().getInsetsStateController().getControlsForDispatch(windowState);
        if (controlsForDispatch != null) {
            int length = controlsForDispatch.length;
            android.view.InsetsSourceControl[] insetsSourceControlArr = new android.view.InsetsSourceControl[length];
            for (int i = 0; i < length; i++) {
                if (controlsForDispatch[i] != null) {
                    insetsSourceControlArr[i] = new android.view.InsetsSourceControl(controlsForDispatch[i]);
                    insetsSourceControlArr[i].setParcelableFlags(1);
                }
            }
            array.set(insetsSourceControlArr);
        }
    }

    private void tryStartExitingAnimation(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowStateAnimator windowStateAnimator) {
        int i;
        java.lang.String str;
        if (windowState.mAttrs.type != 3) {
            i = 2;
        } else {
            i = 5;
        }
        if (windowState.isVisible() && windowState.isDisplayed() && windowState.mDisplayContent.okToAnimate()) {
            if (windowStateAnimator.applyAnimationLocked(i, false)) {
                str = "applyAnimation";
            } else if (windowState.isSelfAnimating(0, 16)) {
                str = "selfAnimating";
            } else if (windowState.mTransitionController.isShellTransitionsEnabled()) {
                if (windowState.mActivityRecord != null && windowState.mActivityRecord.inTransition()) {
                    windowState.mTransitionController.mAnimatingExitWindows.add(windowState);
                    str = "inTransition";
                }
                str = null;
            } else {
                if (windowState.isAnimating(3, 9)) {
                    str = "inLegacyTransition";
                }
                str = null;
            }
            if (str != null) {
                windowState.mAnimatingExit = true;
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -255991894956556845L, 0, null, java.lang.String.valueOf(str), java.lang.String.valueOf(windowState));
            }
        }
        if (!windowState.mAnimatingExit) {
            boolean z = windowState.mActivityRecord == null || windowState.mActivityRecord.mAppStopped;
            windowState.mDestroying = true;
            windowState.destroySurface(false, z);
        }
        if (this.mAccessibilityController.hasCallbacks()) {
            this.mAccessibilityController.onWindowTransition(windowState, i);
        }
    }

    private int createSurfaceControl(android.view.SurfaceControl surfaceControl, int i, com.android.server.wm.WindowState windowState, com.android.server.wm.WindowStateAnimator windowStateAnimator) {
        if (!windowState.mHasSurface) {
            i |= 2;
        }
        try {
            android.os.Trace.traceBegin(32L, "createSurfaceControl");
            com.android.server.wm.WindowSurfaceController createSurfaceLocked = windowStateAnimator.createSurfaceLocked();
            if (createSurfaceLocked != null) {
                createSurfaceLocked.getSurfaceControl(surfaceControl);
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, 6555160513135851764L, 0, null, java.lang.String.valueOf(surfaceControl));
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -196459205494031145L, 0, "Failed to create surface control for %s", java.lang.String.valueOf(windowState));
                surfaceControl.release();
            }
            return i;
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    public boolean outOfMemoryWindow(com.android.server.wm.Session session, android.view.IWindow iWindow) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    if (windowForClientLocked != null) {
                        boolean reclaimSomeSurfaceMemory = this.mRoot.reclaimSomeSurfaceMemory(windowForClientLocked.mWinAnimator, "from-client", false);
                        resetPriorityAfterLockedSection();
                        return reclaimSomeSurfaceMemory;
                    }
                    resetPriorityAfterLockedSection();
                    return false;
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void finishDrawingWindow(com.android.server.wm.Session session, android.view.IWindow iWindow, @android.annotation.Nullable android.view.SurfaceControl.Transaction transaction, int i) {
        if (transaction != null) {
            transaction.sanitize(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -5512006943172316333L, 0, null, java.lang.String.valueOf(windowForClientLocked), java.lang.String.valueOf(windowForClientLocked != null ? windowForClientLocked.mWinAnimator.drawStateToString() : "null"));
                    if (windowForClientLocked != null && windowForClientLocked.finishDrawing(transaction, i)) {
                        if (windowForClientLocked.hasWallpaper()) {
                            windowForClientLocked.getDisplayContent().pendingLayoutChanges |= 4;
                        }
                        windowForClientLocked.setDisplayLayoutNeeded();
                        this.mWindowPlacerLocked.requestTraversal();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean checkCallingPermission(java.lang.String str, java.lang.String str2) {
        return checkCallingPermission(str, str2, true);
    }

    boolean checkCallingPermission(java.lang.String str, java.lang.String str2, boolean z) {
        if (android.os.Binder.getCallingPid() == MY_PID || this.mContext.checkCallingPermission(str) == 0) {
            return true;
        }
        if (z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -2577785761087081584L, 20, "Permission Denial: %s from pid=%d, uid=%d requires %s", java.lang.String.valueOf(str2), java.lang.Long.valueOf(android.os.Binder.getCallingPid()), java.lang.Long.valueOf(android.os.Binder.getCallingUid()), java.lang.String.valueOf(str));
            return false;
        }
        return false;
    }

    public void addWindowToken(@android.annotation.NonNull android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable android.os.Bundle bundle) {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "addWindowToken()")) {
            throw new java.lang.SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i2, null);
                if (displayContentOrCreate == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 4547566763172245740L, 4, "addWindowToken: Attempted to add token: %s for non-exiting displayId=%d", java.lang.String.valueOf(iBinder), java.lang.Long.valueOf(i2));
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.WindowToken windowToken = displayContentOrCreate.getWindowToken(iBinder);
                if (windowToken != null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -972832559831959983L, 16, "addWindowToken: Attempted to add binder token: %s for already created window token: %s displayId=%d", java.lang.String.valueOf(iBinder), java.lang.String.valueOf(windowToken), java.lang.Long.valueOf(i2));
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (i == 2013) {
                    new com.android.server.wm.WallpaperWindowToken(this, iBinder, true, displayContentOrCreate, true, bundle);
                } else {
                    new com.android.server.wm.WindowToken.Builder(this, iBinder, i).setDisplayContent(displayContentOrCreate).setPersistOnEmpty(true).setOwnerCanManageAppTokens(true).setOptions(bundle).build();
                }
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.window.WindowContextInfo attachWindowContextToDisplayArea(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(iApplicationThread);
        java.util.Objects.requireNonNull(iBinder);
        boolean checkCallingPermission = checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "attachWindowContextToDisplayArea", false);
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowProcessController processController = this.mAtmService.getProcessController(iApplicationThread);
                    if (processController == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8372202339190060748L, 5, "attachWindowContextToDisplayArea: calling from non-existing process pid=%d uid=%d", java.lang.Long.valueOf(callingPid), java.lang.Long.valueOf(callingUid));
                        resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i2);
                    if (displayContentOrCreate == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 1904306629015452865L, 1, "attachWindowContextToDisplayArea: trying to attach to a non-existing display:%d", java.lang.Long.valueOf(i2));
                        resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.DisplayArea findAreaForWindowType = displayContentOrCreate.findAreaForWindowType(i, bundle, checkCallingPermission, false);
                    this.mWindowContextListenerController.registerWindowContainerListener(processController, iBinder, findAreaForWindowType, i, bundle, false);
                    android.window.WindowContextInfo windowContextInfo = new android.window.WindowContextInfo(findAreaForWindowType.getConfiguration(), i2);
                    resetPriorityAfterLockedSection();
                    return windowContextInfo;
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public android.window.WindowContextInfo attachWindowContextToDisplayContent(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.os.IBinder iBinder, int i) {
        java.util.Objects.requireNonNull(iApplicationThread);
        java.util.Objects.requireNonNull(iBinder);
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowProcessController processController = this.mAtmService.getProcessController(iApplicationThread);
                    if (processController == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -6845859096032432107L, 5, "attachWindowContextToDisplayContent: calling from non-existing process pid=%d uid=%d", java.lang.Long.valueOf(callingPid), java.lang.Long.valueOf(callingUid));
                        resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        this.mWindowContextListenerController.registerWindowContainerListener(processController, iBinder, displayContent, -1, null, false);
                        android.window.WindowContextInfo windowContextInfo = new android.window.WindowContextInfo(displayContent.getConfiguration(), i);
                        resetPriorityAfterLockedSection();
                        return windowContextInfo;
                    }
                    if (callingPid == MY_PID) {
                        resetPriorityAfterLockedSection();
                        return null;
                    }
                    throw new android.view.WindowManager.InvalidDisplayException("attachWindowContextToDisplayContent: trying to attach to a non-existing display:" + i);
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public android.window.WindowContextInfo attachWindowContextToWindowToken(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
        java.util.Objects.requireNonNull(iApplicationThread);
        java.util.Objects.requireNonNull(iBinder);
        java.util.Objects.requireNonNull(iBinder2);
        boolean checkCallingPermission = checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "attachWindowContextToWindowToken", false);
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowProcessController processController = this.mAtmService.getProcessController(iApplicationThread);
                    if (processController == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 1473791807245791604L, 5, "attachWindowContextToWindowToken: calling from non-existing process pid=%d uid=%d", java.lang.Long.valueOf(callingPid), java.lang.Long.valueOf(callingUid));
                        resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.WindowToken windowToken = this.mRoot.getWindowToken(iBinder2);
                    if (windowToken == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -2056866750160555704L, 0, "Then token:%s is invalid. It might be removed", java.lang.String.valueOf(iBinder2));
                        resetPriorityAfterLockedSection();
                        return null;
                    }
                    int windowType = this.mWindowContextListenerController.getWindowType(iBinder);
                    if (windowType == -1) {
                        throw new java.lang.IllegalArgumentException("The clientToken:" + iBinder + " should have been attached.");
                    }
                    if (windowType != windowToken.windowType) {
                        throw new java.lang.IllegalArgumentException("The WindowToken's type should match the created WindowContext's type. WindowToken's type is " + windowToken.windowType + ", while WindowContext's is " + windowType);
                    }
                    if (!this.mWindowContextListenerController.assertCallerCanModifyListener(iBinder, checkCallingPermission, callingUid)) {
                        resetPriorityAfterLockedSection();
                        return null;
                    }
                    this.mWindowContextListenerController.registerWindowContainerListener(processController, iBinder, windowToken, windowToken.windowType, windowToken.mOptions, false);
                    android.window.WindowContextInfo windowContextInfo = new android.window.WindowContextInfo(windowToken.getConfiguration(), windowToken.getDisplayContent().getDisplayId());
                    resetPriorityAfterLockedSection();
                    return windowContextInfo;
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void detachWindowContext(@android.annotation.NonNull android.os.IBinder iBinder) {
        java.util.Objects.requireNonNull(iBinder);
        boolean checkCallingPermission = checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "detachWindowContext", false);
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!this.mWindowContextListenerController.assertCallerCanModifyListener(iBinder, checkCallingPermission, callingUid)) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.WindowContainer<?> container = this.mWindowContextListenerController.getContainer(iBinder);
                    this.mWindowContextListenerController.unregisterWindowContainerListener(iBinder);
                    com.android.server.wm.WindowToken asWindowToken = container.asWindowToken();
                    if (asWindowToken != null && asWindowToken.isFromClient()) {
                        removeWindowToken(asWindowToken.token, asWindowToken.getDisplayContent().getDisplayId());
                    }
                    resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWindowToken(android.os.IBinder iBinder) {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = this.mRoot.getWindowToken(iBinder) != null;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return z;
    }

    void removeWindowToken(android.os.IBinder iBinder, boolean z, boolean z2, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1045756671264607145L, 4, "removeWindowToken: Attempted to remove token: %s for non-exiting displayId=%d", java.lang.String.valueOf(iBinder), java.lang.Long.valueOf(i));
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.WindowToken removeWindowToken = displayContent.removeWindowToken(iBinder, z2);
                if (removeWindowToken == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 874825105313641295L, 0, "removeWindowToken: Attempted to remove non-existing token: %s", java.lang.String.valueOf(iBinder));
                    resetPriorityAfterLockedSection();
                } else {
                    if (z) {
                        removeWindowToken.removeAllWindowsIfPossible();
                    }
                    displayContent.getInputMonitor().updateInputWindowsLw(true);
                    resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void removeWindowToken(android.os.IBinder iBinder, int i) {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "removeWindowToken()")) {
            throw new java.lang.SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removeWindowToken(iBinder, false, true, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void moveWindowTokenToDisplay(android.os.IBinder iBinder, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i);
                if (displayContentOrCreate == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 5128669121055635771L, 4, "moveWindowTokenToDisplay: Attempted to move token: %s to non-exiting displayId=%d", java.lang.String.valueOf(iBinder), java.lang.Long.valueOf(i));
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.WindowToken windowToken = this.mRoot.getWindowToken(iBinder);
                if (windowToken == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 6497954191906583839L, 0, "moveWindowTokenToDisplay: Attempted to move non-existing token: %s", java.lang.String.valueOf(iBinder));
                    resetPriorityAfterLockedSection();
                } else if (windowToken.getDisplayContent() == displayContentOrCreate) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 2865882097969084039L, 0, "moveWindowTokenToDisplay: Cannot move to the original display for token: %s", java.lang.String.valueOf(iBinder));
                    resetPriorityAfterLockedSection();
                } else {
                    displayContentOrCreate.reParentWindowToken(windowToken);
                    resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void prepareAppTransitionNone() {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "prepareAppTransition()")) {
            throw new java.lang.SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        getDefaultDisplayContentLocked().prepareAppTransition(0);
    }

    public void overridePendingAppTransitionMultiThumbFuture(android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, android.os.IRemoteCallback iRemoteCallback, boolean z, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.w(TAG, "Attempted to call overridePendingAppTransitionMultiThumbFuture for the display " + i + " that does not exist.");
                    resetPriorityAfterLockedSection();
                    return;
                }
                displayContent.mAppTransition.overridePendingAppTransitionMultiThumbFuture(iAppTransitionAnimationSpecsFuture, iRemoteCallback, z);
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void overridePendingAppTransitionRemote(android.view.RemoteAnimationAdapter remoteAnimationAdapter, int i) {
        if (!checkCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "overridePendingAppTransitionRemote()")) {
            throw new java.lang.SecurityException("Requires CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.w(TAG, "Attempted to call overridePendingAppTransitionRemote for the display " + i + " that does not exist.");
                    resetPriorityAfterLockedSection();
                    return;
                }
                remoteAnimationAdapter.setCallingPidUid(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
                displayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter);
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void endProlongedAnimations() {
    }

    public void executeAppTransition() {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "executeAppTransition()")) {
            throw new java.lang.SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        getDefaultDisplayContentLocked().executeAppTransition();
    }

    void initializeRecentsAnimation(int i, android.view.IRecentsAnimationRunner iRecentsAnimationRunner, com.android.server.wm.RecentsAnimationController.RecentsAnimationCallbacks recentsAnimationCallbacks, int i2, android.util.SparseBooleanArray sparseBooleanArray, com.android.server.wm.ActivityRecord activityRecord) {
        this.mRecentsAnimationController = new com.android.server.wm.RecentsAnimationController(this, iRecentsAnimationRunner, recentsAnimationCallbacks, i2);
        this.mRoot.getDisplayContent(i2).mAppTransition.updateBooster();
        this.mRecentsAnimationController.initialize(i, sparseBooleanArray, activityRecord);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setRecentsAnimationController(com.android.server.wm.RecentsAnimationController recentsAnimationController) {
        this.mRecentsAnimationController = recentsAnimationController;
    }

    com.android.server.wm.RecentsAnimationController getRecentsAnimationController() {
        return this.mRecentsAnimationController;
    }

    void cancelRecentsAnimation(@com.android.server.wm.RecentsAnimationController.ReorderMode int i, java.lang.String str) {
        if (this.mRecentsAnimationController != null) {
            this.mRecentsAnimationController.cancelAnimation(i, str);
        }
    }

    void cleanupRecentsAnimation(@com.android.server.wm.RecentsAnimationController.ReorderMode int i) {
        if (this.mRecentsAnimationController != null) {
            com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mRecentsAnimationController;
            this.mRecentsAnimationController = null;
            recentsAnimationController.cleanupAnimation(i);
            com.android.server.wm.DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
            if (defaultDisplayContentLocked.mAppTransition.isTransitionSet()) {
                defaultDisplayContentLocked.mSkipAppTransitionAnimation = true;
            }
            defaultDisplayContentLocked.forAllWindowContainers(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.WindowManagerService.lambda$cleanupRecentsAnimation$3((com.android.server.wm.WindowContainer) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$cleanupRecentsAnimation$3(com.android.server.wm.WindowContainer windowContainer) {
        if (windowContainer.isAnimating(1, 1)) {
            windowContainer.cancelAnimation();
        }
    }

    boolean isRecentsAnimationTarget(com.android.server.wm.ActivityRecord activityRecord) {
        return this.mRecentsAnimationController != null && this.mRecentsAnimationController.isTargetApp(activityRecord);
    }

    boolean isValidPictureInPictureAspectRatio(com.android.server.wm.DisplayContent displayContent, float f) {
        return displayContent.getPinnedTaskController().isValidPictureInPictureAspectRatio(f);
    }

    boolean isValidExpandedPictureInPictureAspectRatio(com.android.server.wm.DisplayContent displayContent, float f) {
        return displayContent.getPinnedTaskController().isValidExpandedPictureInPictureAspectRatio(f);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void notifyKeyguardTrustedChanged() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (this.mAtmService.mKeyguardController.isKeyguardShowing(0)) {
                        this.mRoot.ensureActivitiesVisible();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void screenTurningOff(int i, com.android.server.policy.WindowManagerPolicy.ScreenOffListener screenOffListener) {
        this.mTaskSnapshotController.screenTurningOff(i, screenOffListener);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void triggerAnimationFailsafe() {
        this.mH.sendEmptyMessage(60);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void onKeyguardShowingAndNotOccludedChanged() {
        this.mH.sendEmptyMessage(61);
        dispatchKeyguardLockedState();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void onPowerKeyDown(final boolean z) {
        this.mRoot.forAllDisplayPolicies(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.DisplayPolicy) obj).onPowerKeyDown(z);
            }
        });
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void onUserSwitched() {
        this.mSettingsObserver.updateSystemUiSettings(true);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllDisplayPolicies(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wm.DisplayPolicy) obj).resetSystemBarAttributes();
                    }
                });
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void moveDisplayToTopIfAllowed(int i) {
        moveDisplayToTopInternal(i);
        syncInputTransactions(true);
    }

    void moveDisplayToTopInternal(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && this.mRoot.getTopChild() != displayContent) {
                    if (!displayContent.canStealTopFocus()) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -886583195545553099L, 5, null, java.lang.Long.valueOf(i), java.lang.Long.valueOf(this.mRoot.getTopFocusedDisplayContent().getDisplayId()));
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.getParent().positionChildAt(Integer.MAX_VALUE, displayContent, true);
                }
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public boolean isAppTransitionStateIdle() {
        return getDefaultDisplayContentLocked().mAppTransition.isIdle();
    }

    public void startFreezingScreen(int i, int i2) {
        if (!checkCallingPermission("android.permission.FREEZE_SCREEN", "startFreezingScreen()")) {
            throw new java.lang.SecurityException("Requires FREEZE_SCREEN permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mClientFreezingScreen) {
                    this.mClientFreezingScreen = true;
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        startFreezingDisplay(i, i2);
                        this.mH.removeMessages(30);
                        this.mH.sendEmptyMessageDelayed(30, 5000L);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void stopFreezingScreen() {
        if (!checkCallingPermission("android.permission.FREEZE_SCREEN", "stopFreezingScreen()")) {
            throw new java.lang.SecurityException("Requires FREEZE_SCREEN permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mClientFreezingScreen) {
                    this.mClientFreezingScreen = false;
                    this.mLastFinishedFreezeSource = "client";
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        stopFreezingDisplayLocked();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void disableKeyguard(android.os.IBinder iBinder, java.lang.String str, int i) {
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 2, "disableKeyguard", (java.lang.String) null);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new java.lang.SecurityException("Requires DISABLE_KEYGUARD permission");
        }
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mKeyguardDisableHandler.disableKeyguard(iBinder, str, callingUid, handleIncomingUser);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reenableKeyguard(android.os.IBinder iBinder, int i) {
        int handleIncomingUser = this.mAmInternal.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, 2, "reenableKeyguard", (java.lang.String) null);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DISABLE_KEYGUARD") != 0) {
            throw new java.lang.SecurityException("Requires DISABLE_KEYGUARD permission");
        }
        java.util.Objects.requireNonNull(iBinder, "token is null");
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mKeyguardDisableHandler.reenableKeyguard(iBinder, callingUid, handleIncomingUser);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.DISABLE_KEYGUARD")
    public void exitKeyguardSecurely(final android.view.IOnKeyguardExitResult iOnKeyguardExitResult) {
        exitKeyguardSecurely_enforcePermission();
        if (iOnKeyguardExitResult == null) {
            throw new java.lang.IllegalArgumentException("callback == null");
        }
        this.mPolicy.exitKeyguardSecurely(new com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult() { // from class: com.android.server.wm.WindowManagerService.9
            @Override // com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult
            public void onKeyguardExitResult(boolean z) {
                try {
                    iOnKeyguardExitResult.onKeyguardExitResult(z);
                } catch (android.os.RemoteException e) {
                }
            }
        });
    }

    public boolean isKeyguardLocked() {
        return this.mPolicy.isKeyguardLocked();
    }

    public boolean isKeyguardShowingAndNotOccluded() {
        return this.mPolicy.isKeyguardShowingAndNotOccluded();
    }

    public boolean isKeyguardSecure(int i) {
        if (i != android.os.UserHandle.getCallingUserId() && !checkCallingPermission("android.permission.INTERACT_ACROSS_USERS", "isKeyguardSecure")) {
            throw new java.lang.SecurityException("Requires INTERACT_ACROSS_USERS permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mPolicy.isKeyguardSecure(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dismissKeyguard(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) {
        if (!checkCallingPermission("android.permission.CONTROL_KEYGUARD", "dismissKeyguard")) {
            throw new java.lang.SecurityException("Requires CONTROL_KEYGUARD permission");
        }
        if (this.mAtmService.mKeyguardController.isShowingDream()) {
            this.mAtmService.mTaskSupervisor.wakeUp("leaveDream");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPolicy.dismissKeyguardLw(iKeyguardDismissCallback, charSequence);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @android.annotation.RequiresPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE")
    public void addKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) {
        enforceSubscribeToKeyguardLockedStatePermission();
        if (!this.mKeyguardLockedStateListeners.register(iKeyguardLockedStateListener)) {
            android.util.Slog.w(TAG, "Failed to register listener: " + iKeyguardLockedStateListener);
        }
    }

    @android.annotation.RequiresPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE")
    public void removeKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) {
        enforceSubscribeToKeyguardLockedStatePermission();
        this.mKeyguardLockedStateListeners.unregister(iKeyguardLockedStateListener);
    }

    private void enforceSubscribeToKeyguardLockedStatePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE", "android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE permission required to subscribe to keyguard locked state changes");
    }

    private void dispatchKeyguardLockedState() {
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerService.this.lambda$dispatchKeyguardLockedState$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchKeyguardLockedState$5() {
        boolean isKeyguardShowing = this.mPolicy.isKeyguardShowing();
        if (this.mDispatchedKeyguardLockedState == isKeyguardShowing) {
            return;
        }
        int beginBroadcast = this.mKeyguardLockedStateListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mKeyguardLockedStateListeners.getBroadcastItem(i).onKeyguardLockedStateChanged(isKeyguardShowing);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mKeyguardLockedStateListeners.finishBroadcast();
        this.mDispatchedKeyguardLockedState = isKeyguardShowing;
    }

    void dispatchImeTargetOverlayVisibilityChanged(@android.annotation.NonNull final android.os.IBinder iBinder, final int i, final boolean z, final boolean z2) {
        if (this.mImeTargetChangeListener != null) {
            this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.WindowManagerService.this.lambda$dispatchImeTargetOverlayVisibilityChanged$6(iBinder, i, z, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchImeTargetOverlayVisibilityChanged$6(android.os.IBinder iBinder, int i, boolean z, boolean z2) {
        this.mImeTargetChangeListener.onImeTargetOverlayVisibilityChanged(iBinder, i, z, z2);
    }

    void dispatchImeInputTargetVisibilityChanged(@android.annotation.NonNull final android.os.IBinder iBinder, final boolean z, final boolean z2) {
        if (this.mImeTargetChangeListener != null) {
            this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.WindowManagerService.this.lambda$dispatchImeInputTargetVisibilityChanged$7(iBinder, z, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchImeInputTargetVisibilityChanged$7(android.os.IBinder iBinder, boolean z, boolean z2) {
        this.mImeTargetChangeListener.onImeInputTargetVisibilityChanged(iBinder, z, z2);
    }

    public void setSwitchingUser(boolean z) {
        if (!checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "setSwitchingUser()")) {
            throw new java.lang.SecurityException("Requires INTERACT_ACROSS_USERS_FULL permission");
        }
        this.mPolicy.setSwitchingUser(z);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mSwitchingUser = z;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @android.annotation.RequiresPermission("android.permission.INTERNAL_SYSTEM_WINDOW")
    public void showGlobalActions() {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "showGlobalActions()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        this.mPolicy.showGlobalActions();
    }

    public void closeSystemDialogs(java.lang.String str) {
        if (!this.mAtmService.checkCanCloseSystemDialogs(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null)) {
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.closeSystemDialogs(str);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void setAnimationScale(int i, float f) {
        if (!checkCallingPermission("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()")) {
            throw new java.lang.SecurityException("Requires SET_ANIMATION_SCALE permission");
        }
        float fixScale = android.view.WindowManager.fixScale(f);
        switch (i) {
            case 0:
                this.mWindowAnimationScaleSetting = fixScale;
                break;
            case 1:
                this.mTransitionAnimationScaleSetting = fixScale;
                break;
            case 2:
                this.mAnimatorDurationScaleSetting = fixScale;
                break;
        }
        this.mH.sendEmptyMessage(14);
    }

    public void setAnimationScales(float[] fArr) {
        if (!checkCallingPermission("android.permission.SET_ANIMATION_SCALE", "setAnimationScale()")) {
            throw new java.lang.SecurityException("Requires SET_ANIMATION_SCALE permission");
        }
        if (fArr != null) {
            if (fArr.length >= 1) {
                this.mWindowAnimationScaleSetting = android.view.WindowManager.fixScale(fArr[0]);
            }
            if (fArr.length >= 2) {
                this.mTransitionAnimationScaleSetting = android.view.WindowManager.fixScale(fArr[1]);
            }
            if (fArr.length >= 3) {
                this.mAnimatorDurationScaleSetting = android.view.WindowManager.fixScale(fArr[2]);
                dispatchNewAnimatorScaleLocked(null);
            }
        }
        this.mH.sendEmptyMessage(14);
    }

    private void setAnimatorDurationScale(float f) {
        this.mAnimatorDurationScaleSetting = f;
        android.animation.ValueAnimator.setDurationScale(f);
    }

    public float getWindowAnimationScaleLocked() {
        return this.mAnimationsDisabled ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : this.mWindowAnimationScaleSetting;
    }

    public float getTransitionAnimationScaleLocked() {
        return this.mAnimationsDisabled ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : this.mTransitionAnimationScaleSetting;
    }

    public float getAnimationScale(int i) {
        switch (i) {
            case 0:
                return this.mWindowAnimationScaleSetting;
            case 1:
                return this.mTransitionAnimationScaleSetting;
            case 2:
                return this.mAnimatorDurationScaleSetting;
            default:
                return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
    }

    public float[] getAnimationScales() {
        return new float[]{this.mWindowAnimationScaleSetting, this.mTransitionAnimationScaleSetting, this.mAnimatorDurationScaleSetting};
    }

    public float getCurrentAnimatorScale() {
        float f;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                f = this.mAnimationsDisabled ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : this.mAnimatorDurationScaleSetting;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return f;
    }

    void dispatchNewAnimatorScaleLocked(com.android.server.wm.Session session) {
        this.mH.obtainMessage(34, session).sendToTarget();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void registerPointerEventListener(android.view.WindowManagerPolicyConstants.PointerEventListener pointerEventListener, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.registerPointerEventListener(pointerEventListener);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void unregisterPointerEventListener(android.view.WindowManagerPolicyConstants.PointerEventListener pointerEventListener, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.unregisterPointerEventListener(pointerEventListener);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public int getLidState() {
        int switchState = this.mInputManager.getSwitchState(-1, -256, 0);
        if (switchState > 0) {
            return 0;
        }
        return switchState == 0 ? 1 : -1;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void lockDeviceNow() {
        lockNow(null);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public int getCameraLensCoverState() {
        int switchState = this.mInputManager.getSwitchState(-1, -256, 9);
        if (switchState > 0) {
            return 1;
        }
        return switchState == 0 ? 0 : -1;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void switchKeyboardLayout(int i, int i2) {
        this.mInputManager.switchKeyboardLayout(i, i2);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void shutdown(boolean z) {
        com.android.server.power.ShutdownThread.shutdown(android.app.ActivityThread.currentActivityThread().getSystemUiContext(), "userrequested", z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void reboot(boolean z) {
        com.android.server.power.ShutdownThread.reboot(android.app.ActivityThread.currentActivityThread().getSystemUiContext(), "userrequested", z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void reboot(boolean z, java.lang.String str) {
        com.android.server.power.ShutdownThread.rebootCustom(android.app.ActivityThread.currentActivityThread().getSystemUiContext(), str, z);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void rebootSafeMode(boolean z) {
        com.android.server.power.ShutdownThread.rebootSafeMode(android.app.ActivityThread.currentActivityThread().getSystemUiContext(), z);
    }

    public void setCurrentUser(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtmService.getTransitionController().requestTransitionIfNeeded(1, null);
                this.mCurrentUserId = i;
                this.mPolicy.setCurrentUserLw(i);
                this.mKeyguardDisableHandler.setCurrentUser(i);
                this.mRoot.switchUser(i);
                this.mWindowPlacerLocked.performSurfacePlacement();
                com.android.server.wm.DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
                if (this.mDisplayReady) {
                    int forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked(i);
                    if (forcedDisplayDensityForUserLocked == 0) {
                        forcedDisplayDensityForUserLocked = defaultDisplayContentLocked.getInitialDisplayDensity();
                    }
                    defaultDisplayContentLocked.setForcedDensity(forcedDisplayDensityForUserLocked, -2);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    boolean isUserVisible(int i) {
        return this.mUmInternal.isUserVisible(i);
    }

    int getUserAssignedToDisplay(int i) {
        return this.mUmInternal.getUserAssignedToDisplay(i);
    }

    boolean shouldPlacePrimaryHomeOnDisplay(int i) {
        return shouldPlacePrimaryHomeOnDisplay(i, this.mUmInternal.getUserAssignedToDisplay(i));
    }

    boolean shouldPlacePrimaryHomeOnDisplay(int i, int i2) {
        return this.mUmInternal.getMainDisplayAssignedToUser(i2) == i;
    }

    public void enableScreenAfterBoot() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean z = this.mDisplayEnabled;
                boolean z2 = this.mForceDisplayEnabled;
                boolean z3 = this.mShowingBootMessages;
                boolean z4 = this.mSystemBooted;
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -1557387535886241553L, 255, null, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.String.valueOf(new java.lang.RuntimeException("here").fillInStackTrace()));
                if (this.mSystemBooted) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mSystemBooted = true;
                hideBootMessagesLocked();
                this.mH.sendEmptyMessageDelayed(23, 30000L);
                resetPriorityAfterLockedSection();
                this.mPolicy.systemBooted();
                performEnableScreen();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void enableScreenIfNeeded() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                enableScreenIfNeededLocked();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    void enableScreenIfNeededLocked() {
        boolean z = this.mDisplayEnabled;
        boolean z2 = this.mForceDisplayEnabled;
        boolean z3 = this.mShowingBootMessages;
        boolean z4 = this.mSystemBooted;
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -6467850045030187736L, 255, null, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.String.valueOf(new java.lang.RuntimeException("here").fillInStackTrace()));
        if (this.mDisplayEnabled) {
            return;
        }
        if (!this.mSystemBooted && !this.mShowingBootMessages) {
            return;
        }
        this.mH.sendEmptyMessage(16);
    }

    public void performBootTimeout() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mDisplayEnabled) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 179762478329442868L, 0, "***** BOOT TIMEOUT: forcing display enabled", null);
                this.mForceDisplayEnabled = true;
                resetPriorityAfterLockedSection();
                performEnableScreen();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void onSystemUiStarted() {
        this.mPolicy.onSystemUiStarted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performEnableScreen() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -3417569256875279779L, 255, null, java.lang.Boolean.valueOf(this.mDisplayEnabled), java.lang.Boolean.valueOf(this.mForceDisplayEnabled), java.lang.Boolean.valueOf(this.mShowingBootMessages), java.lang.Boolean.valueOf(this.mSystemBooted), java.lang.String.valueOf(new java.lang.RuntimeException("here").fillInStackTrace()));
                if (this.mDisplayEnabled) {
                    return;
                }
                if (!this.mSystemBooted && !this.mShowingBootMessages) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!this.mShowingBootMessages && !this.mPolicy.canDismissBootAnimation()) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!this.mForceDisplayEnabled) {
                    if (this.mBootWaitForWindowsStartTime < 0) {
                        this.mBootWaitForWindowsStartTime = android.os.SystemClock.elapsedRealtime();
                    }
                    for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
                        if (this.mRoot.getChildAt(childCount).shouldWaitForSystemDecorWindowsOnBoot()) {
                            resetPriorityAfterLockedSection();
                            return;
                        }
                    }
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mBootWaitForWindowsStartTime;
                    this.mBootWaitForWindowsStartTime = -1L;
                    if (elapsedRealtime > 10) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -7516915153725082358L, 1, null, java.lang.Long.valueOf(elapsedRealtime));
                    }
                }
                if (!this.mBootAnimationStopped) {
                    android.os.Trace.asyncTraceBegin(32L, "Stop bootanim", 0);
                    android.os.SystemProperties.set("service.bootanim.exit", "1");
                    this.mBootAnimationStopped = true;
                }
                if (!this.mForceDisplayEnabled && !checkBootAnimationCompleteLocked()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -1541244520024033685L, 0, null, null);
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (!android.view.SurfaceControl.bootFinished()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 2670150656385758826L, 0, "performEnableScreen: bootFinished() failed.", null);
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.EventLogTags.writeWmBootAnimationDone(android.os.SystemClock.uptimeMillis());
                android.os.Trace.asyncTraceEnd(32L, "Stop bootanim", 0);
                this.mDisplayEnabled = true;
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, 530628508916855904L, 0, null, null);
                this.mInputManagerCallback.setEventDispatchingLw(this.mEventDispatchingEnabled);
                resetPriorityAfterLockedSection();
                try {
                    this.mActivityManager.bootAnimationComplete();
                } catch (android.os.RemoteException e) {
                }
                this.mPolicy.enableScreenAfterBoot();
                updateRotationUnchecked(false, false);
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        this.mAtmService.getTransitionController().mIsWaitingForDisplayEnabled = false;
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 5477889324043875194L, 0, null, null);
                    } finally {
                    }
                }
                resetPriorityAfterLockedSection();
            } finally {
                resetPriorityAfterLockedSection();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkBootAnimationCompleteLocked() {
        if (android.os.SystemService.isRunning(BOOT_ANIMATION_SERVICE)) {
            this.mH.removeMessages(37);
            this.mH.sendEmptyMessageDelayed(37, 50L);
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -2061779801633179448L, 0, null, null);
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -8177456840019985809L, 0, null, null);
        return true;
    }

    public void showBootMessage(java.lang.CharSequence charSequence, boolean z) {
        boolean z2;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -333924817004774456L, 1020, null, java.lang.String.valueOf(charSequence), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(this.mAllowBootMessages), java.lang.Boolean.valueOf(this.mShowingBootMessages), java.lang.Boolean.valueOf(this.mSystemBooted), java.lang.String.valueOf(new java.lang.RuntimeException("here").fillInStackTrace()));
                if (!this.mAllowBootMessages) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (this.mShowingBootMessages) {
                    z2 = false;
                } else {
                    if (!z) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    z2 = true;
                }
                if (this.mSystemBooted) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mShowingBootMessages = true;
                this.mPolicy.showBootMessage(charSequence, z);
                resetPriorityAfterLockedSection();
                if (z2) {
                    performEnableScreen();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void hideBootMessagesLocked() {
        boolean z = this.mDisplayEnabled;
        boolean z2 = this.mForceDisplayEnabled;
        boolean z3 = this.mShowingBootMessages;
        boolean z4 = this.mSystemBooted;
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, 2994810644159608200L, 255, null, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.String.valueOf(new java.lang.RuntimeException("here").fillInStackTrace()));
        if (this.mShowingBootMessages) {
            this.mShowingBootMessages = false;
            this.mPolicy.hideBootMessages();
        }
    }

    public void setInTouchMode(boolean z, int i) {
        int i2;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (!this.mPerDisplayFocusEnabled || (displayContent != null && displayContent.isInTouchMode() != z)) {
                    boolean z2 = displayContent != null && displayContent.hasOwnFocus();
                    if (z2 && displayContent.isInTouchMode() == z) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    int callingPid = android.os.Binder.getCallingPid();
                    int callingUid = android.os.Binder.getCallingUid();
                    boolean hasTouchModePermission = hasTouchModePermission(callingPid);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (this.mPerDisplayFocusEnabled || z2) {
                            if (this.mInputManager.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, i)) {
                                displayContent.setInTouchMode(z);
                            }
                        } else {
                            int size = this.mRoot.mChildren.size();
                            int i3 = 0;
                            while (i3 < size) {
                                com.android.server.wm.DisplayContent displayContent2 = (com.android.server.wm.DisplayContent) this.mRoot.mChildren.get(i3);
                                if (displayContent2.isInTouchMode() == z) {
                                    i2 = size;
                                } else if (displayContent2.hasOwnFocus()) {
                                    i2 = size;
                                } else {
                                    i2 = size;
                                    if (this.mInputManager.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, displayContent2.mDisplayId)) {
                                        displayContent2.setInTouchMode(z);
                                    }
                                }
                                i3++;
                                size = i2;
                            }
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        resetPriorityAfterLockedSection();
                        return;
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th2) {
                resetPriorityAfterLockedSection();
                throw th2;
            }
        }
    }

    public void setInTouchModeOnAllDisplays(boolean z) {
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        boolean hasTouchModePermission = hasTouchModePermission(callingPid);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                for (int i = 0; i < this.mRoot.mChildren.size(); i++) {
                    try {
                        com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mRoot.mChildren.get(i);
                        if (displayContent.isInTouchMode() != z && this.mInputManager.setInTouchMode(z, callingPid, callingUid, hasTouchModePermission, displayContent.mDisplayId)) {
                            displayContent.setInTouchMode(z);
                        }
                    } catch (java.lang.Throwable th) {
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean hasTouchModePermission(int i) {
        return this.mAtmService.instrumentationSourceHasPermission(i, "android.permission.MODIFY_TOUCH_MODE_STATE") || checkCallingPermission("android.permission.MODIFY_TOUCH_MODE_STATE", "setInTouchMode()", false);
    }

    public boolean isInTouchMode(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    boolean z = this.mContext.getResources().getBoolean(android.R.bool.config_defaultInTouchMode);
                    resetPriorityAfterLockedSection();
                    return z;
                }
                boolean isInTouchMode = displayContent.isInTouchMode();
                resetPriorityAfterLockedSection();
                return isInTouchMode;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void showEmulatorDisplayOverlayIfNeeded() {
        if (this.mContext.getResources().getBoolean(android.R.bool.config_watchlistUseFileHashesCache) && android.os.SystemProperties.getBoolean(PROPERTY_EMULATOR_CIRCULAR, false) && android.os.Build.IS_EMULATOR) {
            this.mH.sendMessage(this.mH.obtainMessage(36));
        }
    }

    public void showEmulatorDisplayOverlay() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mEmulatorDisplayOverlay == null) {
                    this.mEmulatorDisplayOverlay = new com.android.server.wm.EmulatorDisplayOverlay(this.mContext, getDefaultDisplayContentLocked(), (this.mPolicy.getWindowLayerFromTypeLw(2018) * 10000) + 10, this.mTransaction);
                }
                this.mEmulatorDisplayOverlay.setVisibility(true, this.mTransaction);
                this.mTransaction.apply();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void showStrictModeViolation(boolean z) {
        int callingPid = android.os.Binder.getCallingPid();
        if (!z) {
            this.mH.sendMessage(this.mH.obtainMessage(25, 0, callingPid));
        } else {
            this.mH.sendMessage(this.mH.obtainMessage(25, 1, callingPid));
            this.mH.sendMessageDelayed(this.mH.obtainMessage(25, 0, callingPid), 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showStrictModeViolation(int i, int i2) {
        boolean z = i != 0;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (z) {
                try {
                    if (!this.mRoot.canShowStrictModeViolation(i2)) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (this.mStrictModeFlash == null) {
                this.mStrictModeFlash = new com.android.server.wm.StrictModeFlash(getDefaultDisplayContentLocked(), this.mTransaction);
            }
            this.mStrictModeFlash.setVisibility(z, this.mTransaction);
            this.mTransaction.apply();
            resetPriorityAfterLockedSection();
        }
    }

    public void setStrictModeVisualIndicatorPreference(java.lang.String str) {
        android.os.SystemProperties.set("persist.sys.strictmode.visual", str);
    }

    public android.graphics.Bitmap screenshotWallpaper() {
        android.graphics.Bitmap screenshotWallpaperLocked;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "screenshotWallpaper()")) {
            throw new java.lang.SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        try {
            android.os.Trace.traceBegin(32L, "screenshotWallpaper");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    screenshotWallpaperLocked = this.mRoot.getDisplayContent(0).mWallpaperController.screenshotWallpaperLocked();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return screenshotWallpaperLocked;
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    public android.view.SurfaceControl mirrorWallpaperSurface(int i) {
        android.view.SurfaceControl mirrorWallpaperSurface;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                mirrorWallpaperSurface = this.mRoot.getDisplayContent(i).mWallpaperController.mirrorWallpaperSurface();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return mirrorWallpaperSurface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.window.ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot() {
        android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer;
        android.window.ScreenCapture.LayerCaptureArgs layerCaptureArgs;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "requestAssistScreenshot()")) {
            throw new java.lang.SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(0);
                screenshotHardwareBuffer = null;
                if (displayContent == null) {
                    layerCaptureArgs = null;
                } else {
                    layerCaptureArgs = displayContent.getLayerCaptureArgs();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        if (layerCaptureArgs != null) {
            android.window.ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = android.window.ScreenCapture.createSyncCaptureListener();
            android.window.ScreenCapture.captureLayers(layerCaptureArgs, createSyncCaptureListener);
            screenshotHardwareBuffer = createSyncCaptureListener.getBuffer();
        }
        if (screenshotHardwareBuffer == null) {
            android.util.Slog.w(TAG, "Failed to take screenshot");
        }
        return screenshotHardwareBuffer;
    }

    public boolean requestAssistScreenshot(final android.app.IAssistDataReceiver iAssistDataReceiver) {
        android.window.ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot = takeAssistScreenshot();
        final android.graphics.Bitmap asBitmap = takeAssistScreenshot != null ? takeAssistScreenshot.asBitmap() : null;
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerService.lambda$requestAssistScreenshot$8(iAssistDataReceiver, asBitmap);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$requestAssistScreenshot$8(android.app.IAssistDataReceiver iAssistDataReceiver, android.graphics.Bitmap bitmap) {
        try {
            iAssistDataReceiver.onHandleAssistScreenshot(bitmap);
        } catch (android.os.RemoteException e) {
        }
    }

    public android.window.TaskSnapshot getTaskSnapshot(int i, int i2, boolean z, boolean z2) {
        return this.mTaskSnapshotController.getSnapshot(i, i2, z2, z);
    }

    @android.annotation.Nullable
    public android.graphics.Bitmap captureTaskBitmap(int i, @android.annotation.NonNull android.window.ScreenCapture.LayerCaptureArgs.Builder builder) {
        if (this.mTaskSnapshotController.shouldDisableSnapshots()) {
            return null;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task anyTaskForId = this.mRoot.anyTaskForId(i);
                if (anyTaskForId == null) {
                    resetPriorityAfterLockedSection();
                    return null;
                }
                anyTaskForId.getBounds(this.mTmpRect);
                this.mTmpRect.offsetTo(0, 0);
                android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers = android.window.ScreenCapture.captureLayers(builder.setLayer(anyTaskForId.getSurfaceControl()).setSourceCrop(this.mTmpRect).build());
                if (captureLayers == null) {
                    android.util.Slog.w(TAG, "Could not get screenshot buffer for taskId: " + i);
                    resetPriorityAfterLockedSection();
                    return null;
                }
                android.graphics.Bitmap asBitmap = captureLayers.asBitmap();
                resetPriorityAfterLockedSection();
                return asBitmap;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void removeObsoleteTaskFiles(android.util.ArraySet<java.lang.Integer> arraySet, int[] iArr) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mTaskSnapshotController.removeObsoleteTaskFiles(arraySet, iArr);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void setFixedToUserRotation(int i, int i2) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "setFixedToUserRotation()")) {
            throw new java.lang.SecurityException("Requires SET_ORIENTATION permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        android.util.Slog.w(TAG, "Trying to set fixed to user rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.getDisplayRotation().setFixedToUserRotation(i2);
                        resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    int getFixedToUserRotation(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.w(TAG, "Trying to get fixed to user rotation for a missing display.");
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int fixedToUserRotationMode = displayContent.getDisplayRotation().getFixedToUserRotationMode();
                resetPriorityAfterLockedSection();
                return fixedToUserRotationMode;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setIgnoreOrientationRequest(int i, boolean z) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "setIgnoreOrientationRequest()")) {
            throw new java.lang.SecurityException("Requires SET_ORIENTATION permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        android.util.Slog.w(TAG, "Trying to setIgnoreOrientationRequest() for a missing display.");
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.setIgnoreOrientationRequest(z);
                        resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean getIgnoreOrientationRequest(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.w(TAG, "Trying to getIgnoreOrientationRequest() for a missing display.");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean ignoreOrientationRequest = displayContent.getIgnoreOrientationRequest();
                resetPriorityAfterLockedSection();
                return ignoreOrientationRequest;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void setOrientationRequestPolicy(boolean z, @android.annotation.Nullable int[] iArr, @android.annotation.Nullable int[] iArr2) {
        this.mOrientationMapping.clear();
        if (iArr != null && iArr2 != null && iArr.length == iArr2.length) {
            for (int i = 0; i < iArr.length; i++) {
                this.mOrientationMapping.put(iArr[i], iArr2[i]);
            }
        }
        if (z == this.mIsIgnoreOrientationRequestDisabled) {
            return;
        }
        this.mIsIgnoreOrientationRequestDisabled = z;
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            this.mRoot.getChildAt(childCount).onIsIgnoreOrientationRequestDisabledChanged();
        }
    }

    int mapOrientationRequest(int i) {
        if (!this.mIsIgnoreOrientationRequestDisabled) {
            return i;
        }
        return this.mOrientationMapping.get(i, i);
    }

    boolean isIgnoreOrientationRequestDisabled() {
        return this.mIsIgnoreOrientationRequestDisabled || !this.mLetterboxConfiguration.isIgnoreOrientationRequestAllowed();
    }

    public void freezeRotation(int i, java.lang.String str) {
        freezeDisplayRotation(0, i, str);
    }

    public void freezeDisplayRotation(int i, int i2, java.lang.String str) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "freezeRotation()")) {
            throw new java.lang.SecurityException("Requires SET_ORIENTATION permission");
        }
        if (i2 < -1 || i2 > 3) {
            throw new java.lang.IllegalArgumentException("Rotation argument must be -1 or a valid rotation constant.");
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -6625203651195752178L, 5, null, java.lang.Long.valueOf(getDefaultDisplayRotation()), java.lang.Long.valueOf(i2), java.lang.String.valueOf(str));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        android.util.Slog.w(TAG, "Trying to freeze rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.getDisplayRotation().freezeRotation(i2, str);
                        resetPriorityAfterLockedSection();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        updateRotationUnchecked(false, false);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void thawRotation(java.lang.String str) {
        thawDisplayRotation(0, str);
    }

    public void thawDisplayRotation(int i, java.lang.String str) {
        if (!checkCallingPermission("android.permission.SET_ORIENTATION", "thawRotation()")) {
            throw new java.lang.SecurityException("Requires SET_ORIENTATION permission");
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 8988910478484254861L, 1, null, java.lang.Long.valueOf(getDefaultDisplayRotation()), java.lang.String.valueOf(str));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        android.util.Slog.w(TAG, "Trying to thaw rotation for a missing display.");
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.getDisplayRotation().thawRotation(str);
                        resetPriorityAfterLockedSection();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        updateRotationUnchecked(false, false);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isRotationFrozen() {
        return isDisplayRotationFrozen(0);
    }

    public boolean isDisplayRotationFrozen(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.w(TAG, "Trying to check if rotation is frozen on a missing display.");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean isRotationFrozen = displayContent.getDisplayRotation().isRotationFrozen();
                resetPriorityAfterLockedSection();
                return isRotationFrozen;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    int getDisplayUserRotation(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.w(TAG, "Trying to get user rotation of a missing display.");
                    resetPriorityAfterLockedSection();
                    return -1;
                }
                int userRotation = displayContent.getDisplayRotation().getUserRotation();
                resetPriorityAfterLockedSection();
                return userRotation;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public void updateRotation(boolean z, boolean z2) {
        updateRotationUnchecked(z, z2);
    }

    private void updateRotationUnchecked(boolean z, boolean z2) {
        boolean z3;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7261084872394224738L, 15, null, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2));
        android.os.Trace.traceBegin(32L, "updateRotation");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    int size = this.mRoot.mChildren.size();
                    boolean z4 = false;
                    for (int i = 0; i < size; i++) {
                        com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mRoot.mChildren.get(i);
                        android.os.Trace.traceBegin(32L, "updateRotation: display");
                        boolean updateRotationUnchecked = displayContent.updateRotationUnchecked();
                        android.os.Trace.traceEnd(32L);
                        if (updateRotationUnchecked) {
                            this.mAtmService.getTaskChangeNotificationController().notifyOnActivityRotation(displayContent.mDisplayId);
                        }
                        if (!updateRotationUnchecked || (!displayContent.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange() && !displayContent.mTransitionController.isCollecting())) {
                            z3 = false;
                        } else {
                            z3 = true;
                        }
                        if (!z3) {
                            if (z2) {
                                displayContent.setLayoutNeeded();
                                z4 = true;
                            }
                            if (updateRotationUnchecked || z) {
                                displayContent.sendNewConfiguration();
                            }
                        }
                    }
                    if (z4) {
                        android.os.Trace.traceBegin(32L, "updateRotation: performSurfacePlacement");
                        this.mWindowPlacerLocked.performSurfacePlacement();
                        android.os.Trace.traceEnd(32L);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.os.Trace.traceEnd(32L);
        }
    }

    public int getDefaultDisplayRotation() {
        int rotation;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                rotation = getDefaultDisplayContentLocked().getRotation();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return rotation;
    }

    public void setDisplayChangeWindowController(android.view.IDisplayChangeWindowController iDisplayChangeWindowController) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("setDisplayWindowRotationController");
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (this.mDisplayChangeController != null) {
                        this.mDisplayChangeController.asBinder().unlinkToDeath(this.mDisplayChangeControllerDeath, 0);
                        this.mDisplayChangeController = null;
                    }
                    iDisplayChangeWindowController.asBinder().linkToDeath(this.mDisplayChangeControllerDeath, 0);
                    this.mDisplayChangeController = iDisplayChangeWindowController;
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unable to set rotation controller", e);
        }
    }

    /* JADX WARN: Finally extract failed */
    @android.annotation.EnforcePermission("android.permission.MANAGE_APP_TOKENS")
    public android.view.SurfaceControl addShellRoot(int i, android.view.IWindow iWindow, int i2) {
        addShellRoot_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        android.view.SurfaceControl addShellRoot = displayContent.addShellRoot(iWindow, i2);
                        resetPriorityAfterLockedSection();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return addShellRoot;
                    }
                    resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APP_TOKENS")
    public void setShellRootAccessibilityWindow(int i, int i2, android.view.IWindow iWindow) {
        setShellRootAccessibilityWindow_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.ShellRoot shellRoot = displayContent.mShellRoots.get(i2);
                    if (shellRoot == null) {
                        resetPriorityAfterLockedSection();
                    } else {
                        shellRoot.setAccessibilityWindow(iWindow);
                        resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APP_TOKENS")
    public void setDisplayWindowInsetsController(int i, android.view.IDisplayWindowInsetsController iDisplayWindowInsetsController) {
        setDisplayWindowInsetsController_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        resetPriorityAfterLockedSection();
                    } else {
                        displayContent.setRemoteInsetsController(iDisplayWindowInsetsController);
                        resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APP_TOKENS")
    public void updateDisplayWindowRequestedVisibleTypes(int i, int i2) {
        updateDisplayWindowRequestedVisibleTypes_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null || displayContent.mRemoteInsetsControlTarget == null) {
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.mRemoteInsetsControlTarget.setRequestedVisibleTypes(i2);
                    displayContent.getInsetsStateController().onRequestedVisibleTypesChanged(displayContent.mRemoteInsetsControlTarget);
                    resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int watchRotation(android.view.IRotationWatcher iRotationWatcher, int i) {
        int rotation;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new java.lang.IllegalArgumentException("Trying to register rotation event for invalid display: " + i);
                }
                this.mRotationWatcherController.registerDisplayRotationWatcher(iRotationWatcher, i);
                rotation = displayContent.getRotation();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return rotation;
    }

    public void removeRotationWatcher(android.view.IRotationWatcher iRotationWatcher) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRotationWatcherController.removeRotationWatcher(iRotationWatcher);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public int registerProposedRotationListener(android.os.IBinder iBinder, android.view.IRotationWatcher iRotationWatcher) {
        int proposedRotation;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowContainer<?> associatedWindowContainer = this.mRotationWatcherController.getAssociatedWindowContainer(iBinder);
                if (associatedWindowContainer == null) {
                    android.util.Slog.w(TAG, "Register rotation listener from non-existing token, uid=" + android.os.Binder.getCallingUid());
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                this.mRotationWatcherController.registerProposedRotationListener(iRotationWatcher, iBinder);
                com.android.server.wm.WindowOrientationListener orientationListener = associatedWindowContainer.mDisplayContent.getDisplayRotation().getOrientationListener();
                if (orientationListener != null && (proposedRotation = orientationListener.getProposedRotation()) >= 0) {
                    resetPriorityAfterLockedSection();
                    return proposedRotation;
                }
                int rotation = associatedWindowContainer.getWindowConfiguration().getRotation();
                resetPriorityAfterLockedSection();
                return rotation;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean registerWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        boolean isWallpaperVisible;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new java.lang.IllegalArgumentException("Trying to register visibility event for invalid display: " + i);
                }
                this.mWallpaperVisibilityListeners.registerWallpaperVisibilityListener(iWallpaperVisibilityListener, i);
                isWallpaperVisible = displayContent.mWallpaperController.isWallpaperVisible();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return isWallpaperVisible;
    }

    public void unregisterWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWallpaperVisibilityListeners.unregisterWallpaperVisibilityListener(iWallpaperVisibilityListener, i);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void registerSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new java.lang.IllegalArgumentException("Trying to register system gesture exclusion event for invalid display: " + i);
                }
                displayContent.registerSystemGestureExclusionListener(iSystemGestureExclusionListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void unregisterSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new java.lang.IllegalArgumentException("Trying to unregister system gesture exclusion event for invalid display: " + i);
                }
                displayContent.unregisterSystemGestureExclusionListener(iSystemGestureExclusionListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void registerDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) {
        if (!checkCallingPermission("android.permission.MONITOR_INPUT", "registerDecorViewGestureListener()")) {
            throw new java.lang.SecurityException("Requires MONITOR_INPUT permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new java.lang.IllegalArgumentException("Trying to register DecorView gesture event listenerfor invalid display: " + i);
                }
                displayContent.registerDecorViewGestureListener(iDecorViewGestureListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void unregisterDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) {
        if (!checkCallingPermission("android.permission.MONITOR_INPUT", "unregisterSystemGestureExclusionListener()")) {
            throw new java.lang.SecurityException("Requires MONITOR_INPUT permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new java.lang.IllegalArgumentException("Trying to unregister DecorView gesture event listenerfor invalid display: " + i);
                }
                displayContent.unregisterDecorViewGestureListener(iDecorViewGestureListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    void reportDecorViewGestureChanged(com.android.server.wm.Session session, android.view.IWindow iWindow, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    resetPriorityAfterLockedSection();
                } else {
                    windowForClientLocked.getDisplayContent().updateDecorViewGestureIntercepted(windowForClientLocked.mToken.token, z);
                    resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void reportSystemGestureExclusionChanged(com.android.server.wm.Session session, android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    android.util.Slog.i(TAG, "reportSystemGestureExclusionChanged(): No window state for package:" + session.mPackageName);
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (windowForClientLocked.setSystemGestureExclusion(list)) {
                    windowForClientLocked.getDisplayContent().updateSystemGestureExclusion();
                }
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void reportKeepClearAreasChanged(com.android.server.wm.Session session, android.view.IWindow iWindow, java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    android.util.Slog.i(TAG, "reportKeepClearAreasChanged(): No window state for package:" + session.mPackageName);
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (windowForClientLocked.setKeepClearAreas(list, list2)) {
                    windowForClientLocked.getDisplayContent().updateKeepClearAreas();
                }
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void registerDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) {
        this.mPolicy.registerDisplayFoldListener(iDisplayFoldListener);
    }

    public void unregisterDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) {
        this.mPolicy.unregisterDisplayFoldListener(iDisplayFoldListener);
    }

    void setOverrideFoldedArea(@android.annotation.NonNull android.graphics.Rect rect) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new java.lang.SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mPolicy.setOverrideFoldedArea(rect);
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    android.graphics.Rect getFoldedArea() {
        android.graphics.Rect foldedArea;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    foldedArea = this.mPolicy.getFoldedArea();
                } finally {
                }
            }
            resetPriorityAfterLockedSection();
            return foldedArea;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int[] registerDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("registerDisplayWindowListener");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mDisplayNotificationController.registerListener(iDisplayWindowListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("unregisterDisplayWindowListener");
        this.mDisplayNotificationController.unregisterListener(iDisplayWindowListener);
    }

    public int getPreferredOptionsPanelGravity(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return 81;
                }
                int preferredOptionsPanelGravity = displayContent.getPreferredOptionsPanelGravity();
                resetPriorityAfterLockedSection();
                return preferredOptionsPanelGravity;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean startViewServer(int i) {
        if (isSystemSecure() || !checkCallingPermission("android.permission.DUMP", "startViewServer") || i < 1024) {
            return false;
        }
        if (this.mViewServer != null) {
            if (!this.mViewServer.isRunning()) {
                try {
                    return this.mViewServer.start();
                } catch (java.io.IOException e) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8664813170125714536L, 0, "View server did not start", null);
                }
            }
            return false;
        }
        try {
            this.mViewServer = new com.android.server.wm.ViewServer(this, i);
            return this.mViewServer.start();
        } catch (java.io.IOException e2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8664813170125714536L, 0, "View server did not start", null);
            return false;
        }
    }

    private boolean isSystemSecure() {
        return "1".equals(android.os.SystemProperties.get(SYSTEM_SECURE, "1")) && "0".equals(android.os.SystemProperties.get(SYSTEM_DEBUGGABLE, "0"));
    }

    public boolean stopViewServer() {
        if (isSystemSecure() || !checkCallingPermission("android.permission.DUMP", "stopViewServer") || this.mViewServer == null) {
            return false;
        }
        return this.mViewServer.stop();
    }

    public boolean isViewServerRunning() {
        return !isSystemSecure() && checkCallingPermission("android.permission.DUMP", "isViewServerRunning") && this.mViewServer != null && this.mViewServer.isRunning();
    }

    boolean viewServerListWindows(java.net.Socket socket) {
        java.io.BufferedWriter bufferedWriter;
        boolean z = false;
        if (isSystemSecure()) {
            return false;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        arrayList.add((com.android.server.wm.WindowState) obj);
                    }
                }, false);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        java.io.BufferedWriter bufferedWriter2 = null;
        try {
            try {
                bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(socket.getOutputStream()), 8192);
            } catch (java.lang.Exception e) {
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
            try {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) arrayList.get(i);
                    bufferedWriter.write(java.lang.Integer.toHexString(java.lang.System.identityHashCode(windowState)));
                    bufferedWriter.write(32);
                    bufferedWriter.append(windowState.mAttrs.getTitle());
                    bufferedWriter.write(10);
                }
                bufferedWriter.write("DONE.\n");
                bufferedWriter.flush();
                bufferedWriter.close();
                z = true;
            } catch (java.lang.Exception e2) {
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                }
                return z;
            } catch (java.lang.Throwable th3) {
                th = th3;
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    try {
                        bufferedWriter2.close();
                    } catch (java.io.IOException e3) {
                    }
                }
                throw th;
            }
        } catch (java.io.IOException e4) {
        }
        return z;
    }

    boolean viewServerGetFocusedWindow(java.net.Socket socket) {
        boolean z = false;
        if (isSystemSecure()) {
            return false;
        }
        com.android.server.wm.WindowState focusedWindow = getFocusedWindow();
        java.io.BufferedWriter bufferedWriter = null;
        try {
            try {
                java.io.BufferedWriter bufferedWriter2 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(socket.getOutputStream()), 8192);
                if (focusedWindow != null) {
                    try {
                        bufferedWriter2.write(java.lang.Integer.toHexString(java.lang.System.identityHashCode(focusedWindow)));
                        bufferedWriter2.write(32);
                        bufferedWriter2.append(focusedWindow.mAttrs.getTitle());
                    } catch (java.lang.Exception e) {
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        return z;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (java.io.IOException e2) {
                            }
                        }
                        throw th;
                    }
                }
                bufferedWriter2.write(10);
                bufferedWriter2.flush();
                bufferedWriter2.close();
                z = true;
            } catch (java.lang.Exception e3) {
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e4) {
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean viewServerWindowCommand(java.net.Socket socket, java.lang.String str, java.lang.String str2) {
        java.io.BufferedWriter bufferedWriter;
        android.os.Parcel parcel;
        if (isSystemSecure()) {
            return false;
        }
        android.os.Parcel parcel2 = null;
        java.io.BufferedWriter bufferedWriter2 = null;
        parcel2 = null;
        try {
            int indexOf = str2.indexOf(32);
            if (indexOf == -1) {
                indexOf = str2.length();
            }
            int parseLong = (int) java.lang.Long.parseLong(str2.substring(0, indexOf), 16);
            str2 = indexOf < str2.length() ? str2.substring(indexOf + 1) : "";
            com.android.server.wm.WindowState findWindow = findWindow(parseLong);
            if (findWindow == null) {
                return false;
            }
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                obtain.writeInterfaceToken("android.view.IWindow");
                obtain.writeString(str);
                obtain.writeString(str2);
                obtain.writeInt(1);
                android.os.ParcelFileDescriptor.fromSocket(socket).writeToParcel(obtain, 0);
                parcel = android.os.Parcel.obtain();
                try {
                    findWindow.mClient.asBinder().transact(1, obtain, parcel, 0);
                    parcel.readException();
                    if (!socket.isOutputShutdown()) {
                        bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(socket.getOutputStream()));
                        try {
                            bufferedWriter.write("DONE\n");
                            bufferedWriter.flush();
                            bufferedWriter2 = bufferedWriter;
                        } catch (java.lang.Exception e) {
                            e = e;
                            parcel2 = obtain;
                            try {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -8019372496359375449L, 0, "Could not send command %s with parameters %s. %s", java.lang.String.valueOf(str), java.lang.String.valueOf(str2), java.lang.String.valueOf(e));
                                if (parcel2 != null) {
                                    parcel2.recycle();
                                }
                                if (parcel != null) {
                                    parcel.recycle();
                                }
                                if (bufferedWriter == null) {
                                    return false;
                                }
                                try {
                                    bufferedWriter.close();
                                    return false;
                                } catch (java.io.IOException e2) {
                                    return false;
                                }
                            } catch (java.lang.Throwable th) {
                                th = th;
                                if (parcel2 != null) {
                                    parcel2.recycle();
                                }
                                if (parcel != null) {
                                    parcel.recycle();
                                }
                                if (bufferedWriter != null) {
                                    try {
                                        bufferedWriter.close();
                                    } catch (java.io.IOException e3) {
                                    }
                                }
                                throw th;
                            }
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            parcel2 = obtain;
                            if (parcel2 != null) {
                            }
                            if (parcel != null) {
                            }
                            if (bufferedWriter != null) {
                            }
                            throw th;
                        }
                    }
                    obtain.recycle();
                    parcel.recycle();
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (java.io.IOException e4) {
                        }
                    }
                    return true;
                } catch (java.lang.Exception e5) {
                    e = e5;
                    bufferedWriter = null;
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    bufferedWriter = null;
                }
            } catch (java.lang.Exception e6) {
                e = e6;
                bufferedWriter = null;
                parcel = null;
            } catch (java.lang.Throwable th4) {
                th = th4;
                bufferedWriter = null;
                parcel = null;
            }
        } catch (java.lang.Exception e7) {
            e = e7;
            bufferedWriter = null;
            parcel = null;
        } catch (java.lang.Throwable th5) {
            th = th5;
            bufferedWriter = null;
            parcel = null;
        }
    }

    public void addWindowChangeListener(com.android.server.wm.WindowManagerService.WindowChangeListener windowChangeListener) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWindowChangeListeners.add(windowChangeListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void removeWindowChangeListener(com.android.server.wm.WindowManagerService.WindowChangeListener windowChangeListener) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWindowChangeListeners.remove(windowChangeListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWindowsChanged() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mWindowChangeListeners.isEmpty()) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.WindowManagerService.WindowChangeListener[] windowChangeListenerArr = (com.android.server.wm.WindowManagerService.WindowChangeListener[]) this.mWindowChangeListeners.toArray(new com.android.server.wm.WindowManagerService.WindowChangeListener[this.mWindowChangeListeners.size()]);
                resetPriorityAfterLockedSection();
                for (com.android.server.wm.WindowManagerService.WindowChangeListener windowChangeListener : windowChangeListenerArr) {
                    windowChangeListener.windowsChanged();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void notifyFocusChanged() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mWindowChangeListeners.isEmpty()) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.WindowManagerService.WindowChangeListener[] windowChangeListenerArr = (com.android.server.wm.WindowManagerService.WindowChangeListener[]) this.mWindowChangeListeners.toArray(new com.android.server.wm.WindowManagerService.WindowChangeListener[this.mWindowChangeListeners.size()]);
                resetPriorityAfterLockedSection();
                for (com.android.server.wm.WindowManagerService.WindowChangeListener windowChangeListener : windowChangeListenerArr) {
                    windowChangeListener.focusChanged();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private com.android.server.wm.WindowState findWindow(final int i) {
        com.android.server.wm.WindowState window;
        if (i == -1) {
            return getFocusedWindow();
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                window = this.mRoot.getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda31
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$findWindow$10;
                        lambda$findWindow$10 = com.android.server.wm.WindowManagerService.lambda$findWindow$10(i, (com.android.server.wm.WindowState) obj);
                        return lambda$findWindow$10;
                    }
                });
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return window;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findWindow$10(int i, com.android.server.wm.WindowState windowState) {
        return java.lang.System.identityHashCode(windowState) == i;
    }

    public android.content.res.Configuration computeNewConfiguration(int i) {
        android.content.res.Configuration computeNewConfigurationLocked;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                computeNewConfigurationLocked = computeNewConfigurationLocked(i);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return computeNewConfigurationLocked;
    }

    private android.content.res.Configuration computeNewConfigurationLocked(int i) {
        if (!this.mDisplayReady) {
            return null;
        }
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        this.mRoot.getDisplayContent(i).computeScreenConfiguration(configuration);
        return configuration;
    }

    void notifyHardKeyboardStatusChange() {
        com.android.server.wm.WindowManagerInternal.OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener;
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                onHardKeyboardStatusChangeListener = this.mHardKeyboardStatusChangeListener;
                z = this.mHardKeyboardAvailable;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        if (onHardKeyboardStatusChangeListener != null) {
            onHardKeyboardStatusChangeListener.onHardKeyboardStatusChange(z);
        }
    }

    public void setEventDispatching(boolean z) {
        if (!checkCallingPermission("android.permission.MANAGE_APP_TOKENS", "setEventDispatching()")) {
            throw new java.lang.SecurityException("Requires MANAGE_APP_TOKENS permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mEventDispatchingEnabled = z;
                if (this.mDisplayEnabled) {
                    this.mInputManagerCallback.setEventDispatchingLw(z);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.wm.WindowState getFocusedWindow() {
        com.android.server.wm.WindowState focusedWindowLocked;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                focusedWindowLocked = getFocusedWindowLocked();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return focusedWindowLocked;
    }

    com.android.server.wm.WindowState getFocusedWindowLocked() {
        return this.mRoot.getTopFocusedDisplayContent().mCurrentFocus;
    }

    com.android.server.wm.Task getImeFocusRootTaskLocked() {
        com.android.server.wm.ActivityRecord activityRecord = this.mRoot.getTopFocusedDisplayContent().mFocusedApp;
        if (activityRecord == null || activityRecord.getTask() == null) {
            return null;
        }
        return activityRecord.getTask().getRootTask();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean detectSafeMode() {
        if (!this.mInputManagerCallback.waitForInputDevicesReady(1000L)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 1893303527772009363L, 1, "Devices still not ready after waiting %d milliseconds before attempting to detect safe mode.", 1000L);
        }
        if (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "safe_boot_disallowed", 0) != 0) {
            return false;
        }
        int keyCodeState = this.mInputManager.getKeyCodeState(-1, -256, 82);
        int keyCodeState2 = this.mInputManager.getKeyCodeState(-1, -256, 47);
        int keyCodeState3 = this.mInputManager.getKeyCodeState(-1, 513, 23);
        int scanCodeState = this.mInputManager.getScanCodeState(-1, 65540, 272);
        this.mSafeMode = keyCodeState > 0 || keyCodeState2 > 0 || keyCodeState3 > 0 || scanCodeState > 0 || this.mInputManager.getKeyCodeState(-1, -256, 25) > 0;
        if (android.os.SystemProperties.getInt(com.android.server.power.ShutdownThread.REBOOT_SAFEMODE_PROPERTY, 0) == 0) {
            if (android.os.SystemProperties.getInt(com.android.server.power.ShutdownThread.RO_SAFEMODE_PROPERTY, 0) != 0) {
            }
            if (!this.mSafeMode) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -3652974372240081071L, 85, "SAFE MODE ENABLED (menu=%d s=%d dpad=%d trackball=%d)", java.lang.Long.valueOf(keyCodeState), java.lang.Long.valueOf(keyCodeState2), java.lang.Long.valueOf(keyCodeState3), java.lang.Long.valueOf(scanCodeState));
                if (android.os.SystemProperties.getInt(com.android.server.power.ShutdownThread.RO_SAFEMODE_PROPERTY, 0) == 0) {
                    android.os.SystemProperties.set(com.android.server.power.ShutdownThread.RO_SAFEMODE_PROPERTY, "1");
                }
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 4945624619344146947L, 0, "SAFE MODE not enabled", null);
            }
            this.mPolicy.setSafeMode(this.mSafeMode);
            return this.mSafeMode;
        }
        this.mSafeMode = true;
        android.os.SystemProperties.set(com.android.server.power.ShutdownThread.REBOOT_SAFEMODE_PROPERTY, "");
        if (!this.mSafeMode) {
        }
        this.mPolicy.setSafeMode(this.mSafeMode);
        return this.mSafeMode;
    }

    public void displayReady() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mMaxUiWidth > 0) {
                    this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.WindowManagerService.this.lambda$displayReady$11((com.android.server.wm.DisplayContent) obj);
                        }
                    });
                }
                applyForcedPropertiesForDefaultDisplay();
                this.mAnimator.ready();
                this.mDisplayReady = true;
                this.mHasWideColorGamutSupport = queryWideColorGamutSupport();
                this.mHasHdrSupport = queryHdrSupport();
                this.mIsTouchDevice = this.mContext.getPackageManager().hasSystemFeature("android.hardware.touchscreen");
                this.mIsFakeTouchDevice = this.mContext.getPackageManager().hasSystemFeature("android.hardware.faketouch");
                this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wm.DisplayContent) obj).reconfigureDisplayLocked();
                    }
                });
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayReady$11(com.android.server.wm.DisplayContent displayContent) {
        if (displayContent.mDisplay.getType() == 1) {
            displayContent.setMaxUiWidth(this.mMaxUiWidth);
        }
    }

    public void systemReady() {
        this.mSystemReady = true;
        this.mPolicy.systemReady();
        this.mRoot.forAllDisplayPolicies(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.DisplayPolicy) obj).systemReady();
            }
        });
        this.mSnapshotController.systemReady();
        android.os.Handler handler = com.android.server.UiThread.getHandler();
        final com.android.server.wm.WindowManagerService.SettingsObserver settingsObserver = this.mSettingsObserver;
        java.util.Objects.requireNonNull(settingsObserver);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerService.SettingsObserver.this.loadSettings();
            }
        });
        android.service.vr.IVrManager asInterface = android.service.vr.IVrManager.Stub.asInterface(android.os.ServiceManager.getService("vrmanager"));
        if (asInterface != null) {
            try {
                boolean vrModeState = asInterface.getVrModeState();
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        asInterface.registerListener(this.mVrStateCallbacks);
                        if (vrModeState) {
                            this.mVrModeEnabled = vrModeState;
                            this.mVrStateCallbacks.onVrStateChanged(vrModeState);
                        }
                    } catch (java.lang.Throwable th) {
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                resetPriorityAfterLockedSection();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private static boolean queryWideColorGamutSupport() {
        java.util.Optional has_wide_color_display = android.sysprop.SurfaceFlingerProperties.has_wide_color_display();
        if (has_wide_color_display.isPresent()) {
            return ((java.lang.Boolean) has_wide_color_display.get()).booleanValue();
        }
        try {
            android.hardware.configstore.V1_0.OptionalBool hasWideColorDisplay = android.hardware.configstore.V1_1.ISurfaceFlingerConfigs.getService().hasWideColorDisplay();
            if (hasWideColorDisplay != null) {
                return hasWideColorDisplay.value;
            }
        } catch (android.os.RemoteException e) {
        } catch (java.util.NoSuchElementException e2) {
            return false;
        }
        return false;
    }

    private static boolean queryHdrSupport() {
        java.util.Optional has_HDR_display = android.sysprop.SurfaceFlingerProperties.has_HDR_display();
        if (has_HDR_display.isPresent()) {
            return ((java.lang.Boolean) has_HDR_display.get()).booleanValue();
        }
        try {
            android.hardware.configstore.V1_0.OptionalBool hasHDRDisplay = android.hardware.configstore.V1_1.ISurfaceFlingerConfigs.getService().hasHDRDisplay();
            if (hasHDRDisplay != null) {
                return hasHDRDisplay.value;
            }
        } catch (android.os.RemoteException e) {
        } catch (java.util.NoSuchElementException e2) {
            return false;
        }
        return false;
    }

    @android.annotation.Nullable
    com.android.server.wm.InputTarget getInputTargetFromToken(android.os.IBinder iBinder) {
        com.android.server.wm.WindowState windowState = this.mInputToWindowMap.get(iBinder);
        if (windowState != null) {
            return windowState;
        }
        com.android.server.wm.EmbeddedWindowController.EmbeddedWindow embeddedWindow = this.mEmbeddedWindowController.get(iBinder);
        if (embeddedWindow != null) {
            return embeddedWindow;
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.InputTarget getInputTargetFromWindowTokenLocked(android.os.IBinder iBinder) {
        com.android.server.wm.WindowState windowState = this.mWindowMap.get(iBinder);
        if (windowState != null) {
            return windowState;
        }
        return this.mEmbeddedWindowController.getByWindowToken(iBinder);
    }

    void reportFocusChanged(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.InputTarget inputTargetFromToken = getInputTargetFromToken(iBinder);
                com.android.server.wm.InputTarget inputTargetFromToken2 = getInputTargetFromToken(iBinder2);
                if (inputTargetFromToken2 == null && inputTargetFromToken == null) {
                    android.util.Slog.v(TAG, "Unknown focus tokens, dropping reportFocusChanged");
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mFocusedInputTarget = inputTargetFromToken2;
                this.mAccessibilityController.onFocusChanged(inputTargetFromToken, inputTargetFromToken2);
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -3428027271337724889L, 0, null, java.lang.String.valueOf(inputTargetFromToken), java.lang.String.valueOf(inputTargetFromToken2));
                resetPriorityAfterLockedSection();
                com.android.server.wm.WindowState windowState = inputTargetFromToken2 != null ? inputTargetFromToken2.getWindowState() : null;
                if (windowState != null && windowState.mInputChannelToken == iBinder2) {
                    this.mAnrController.onFocusChanged(windowState);
                    windowState.reportFocusChangedSerialized(true);
                    notifyFocusChanged();
                }
                com.android.server.wm.WindowState windowState2 = inputTargetFromToken != null ? inputTargetFromToken.getWindowState() : null;
                if (windowState2 != null && windowState2.mInputChannelToken == iBinder) {
                    windowState2.reportFocusChangedSerialized(false);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    final class H extends android.os.Handler {
        public static final int ANIMATION_FAILSAFE = 60;
        public static final int APP_FREEZE_TIMEOUT = 17;
        public static final int BOOT_TIMEOUT = 23;
        public static final int CHECK_IF_BOOT_ANIMATION_FINISHED = 37;
        public static final int CLIENT_FREEZE_TIMEOUT = 30;
        public static final int ENABLE_SCREEN = 16;
        public static final int INSETS_CHANGED = 66;
        public static final int NEW_ANIMATOR_SCALE = 34;
        public static final int NOTIFY_ACTIVITY_DRAWN = 32;
        public static final int ON_POINTER_DOWN_OUTSIDE_FOCUS = 62;
        public static final int PERSIST_ANIMATION_SCALE = 14;
        public static final int RECOMPUTE_FOCUS = 61;
        public static final int REPARENT_TASK_TO_DEFAULT_DISPLAY = 65;
        public static final int REPORT_HARD_KEYBOARD_STATUS_CHANGE = 22;
        public static final int REPORT_WINDOWS_CHANGE = 19;
        public static final int RESET_ANR_MESSAGE = 38;
        public static final int RESTORE_POINTER_ICON = 55;
        public static final int SET_HAS_OVERLAY_UI = 58;
        public static final int SHOW_EMULATOR_DISPLAY_OVERLAY = 36;
        public static final int SHOW_STRICT_MODE_VIOLATION = 25;
        public static final int UNUSED = 0;
        public static final int UPDATE_ANIMATION_SCALE = 51;
        public static final int UPDATE_MULTI_WINDOW_STACKS = 41;
        public static final int WAITING_FOR_DRAWN_TIMEOUT = 24;
        public static final int WALLPAPER_DRAW_PENDING_TIMEOUT = 39;
        public static final int WINDOW_FREEZE_TIMEOUT = 11;
        public static final int WINDOW_HIDE_TIMEOUT = 52;
        public static final int WINDOW_STATE_BLAST_SYNC_TIMEOUT = 64;

        H() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.os.Message remove;
            boolean checkBootAnimationCompleteLocked;
            switch (message.what) {
                case 11:
                    com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) message.obj;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            displayContent.onWindowFreezeTimeout();
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 14:
                    android.provider.Settings.Global.putFloat(com.android.server.wm.WindowManagerService.this.mContext.getContentResolver(), "window_animation_scale", com.android.server.wm.WindowManagerService.this.mWindowAnimationScaleSetting);
                    android.provider.Settings.Global.putFloat(com.android.server.wm.WindowManagerService.this.mContext.getContentResolver(), "transition_animation_scale", com.android.server.wm.WindowManagerService.this.mTransitionAnimationScaleSetting);
                    android.provider.Settings.Global.putFloat(com.android.server.wm.WindowManagerService.this.mContext.getContentResolver(), "animator_duration_scale", com.android.server.wm.WindowManagerService.this.mAnimatorDurationScaleSetting);
                    return;
                case 16:
                    com.android.server.wm.WindowManagerService.this.performEnableScreen();
                    return;
                case 17:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 1624328195833150047L, 0, "App freeze timeout expired.", null);
                            com.android.server.wm.WindowManagerService.this.mWindowsFreezingScreen = 2;
                            for (int size = com.android.server.wm.WindowManagerService.this.mAppFreezeListeners.size() - 1; size >= 0; size--) {
                                com.android.server.wm.WindowManagerService.this.mAppFreezeListeners.get(size).onAppFreezeTimeout();
                            }
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 19:
                    if (com.android.server.wm.WindowManagerService.this.mWindowsChanged) {
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock3 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock3) {
                            try {
                                com.android.server.wm.WindowManagerService.this.mWindowsChanged = false;
                            } finally {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        com.android.server.wm.WindowManagerService.this.notifyWindowsChanged();
                        return;
                    }
                    return;
                case 22:
                    com.android.server.wm.WindowManagerService.this.notifyHardKeyboardStatusChange();
                    return;
                case 23:
                    com.android.server.wm.WindowManagerService.this.performBootTimeout();
                    return;
                case 24:
                    com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) message.obj;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock4 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock4) {
                        try {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 5830724144971462783L, 0, "Timeout waiting for drawn: undrawn=%s", java.lang.String.valueOf(windowContainer.mWaitingForDrawn));
                            if (android.os.Trace.isTagEnabled(32L)) {
                                for (int i = 0; i < windowContainer.mWaitingForDrawn.size(); i++) {
                                    com.android.server.wm.WindowManagerService.this.traceEndWaitingForWindowDrawn(windowContainer.mWaitingForDrawn.get(i));
                                }
                            }
                            windowContainer.mWaitingForDrawn.clear();
                            remove = com.android.server.wm.WindowManagerService.this.mWaitingForDrawnCallbacks.remove(windowContainer);
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    if (remove != null) {
                        remove.sendToTarget();
                        return;
                    }
                    return;
                case 25:
                    com.android.server.wm.WindowManagerService.this.showStrictModeViolation(message.arg1, message.arg2);
                    return;
                case 30:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock5 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock5) {
                        try {
                            if (com.android.server.wm.WindowManagerService.this.mClientFreezingScreen) {
                                com.android.server.wm.WindowManagerService.this.mClientFreezingScreen = false;
                                com.android.server.wm.WindowManagerService.this.mLastFinishedFreezeSource = "client-timeout";
                                com.android.server.wm.WindowManagerService.this.stopFreezingDisplayLocked();
                            }
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 32:
                    com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) message.obj;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock6 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock6) {
                        try {
                            if (activityRecord.isAttached()) {
                                activityRecord.getRootTask().notifyActivityDrawnLocked(activityRecord);
                            }
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 34:
                    float currentAnimatorScale = com.android.server.wm.WindowManagerService.this.getCurrentAnimatorScale();
                    android.animation.ValueAnimator.setDurationScale(currentAnimatorScale);
                    com.android.server.wm.Session session = (com.android.server.wm.Session) message.obj;
                    if (session != null) {
                        try {
                            session.mCallback.onAnimatorScaleChanged(currentAnimatorScale);
                            return;
                        } catch (android.os.RemoteException e) {
                            return;
                        }
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock7 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock7) {
                        for (int i2 = 0; i2 < com.android.server.wm.WindowManagerService.this.mSessions.size(); i2++) {
                            try {
                                arrayList.add(com.android.server.wm.WindowManagerService.this.mSessions.valueAt(i2).mCallback);
                            } finally {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        try {
                            ((android.view.IWindowSessionCallback) arrayList.get(i3)).onAnimatorScaleChanged(currentAnimatorScale);
                        } catch (android.os.RemoteException e2) {
                        }
                    }
                    return;
                case 36:
                    com.android.server.wm.WindowManagerService.this.showEmulatorDisplayOverlay();
                    return;
                case 37:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock8 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock8) {
                        try {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, -2240705227895260140L, 0, null, null);
                            checkBootAnimationCompleteLocked = com.android.server.wm.WindowManagerService.this.checkBootAnimationCompleteLocked();
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    if (checkBootAnimationCompleteLocked) {
                        com.android.server.wm.WindowManagerService.this.performEnableScreen();
                        return;
                    }
                    return;
                case 38:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock9 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock9) {
                        try {
                            com.android.server.wm.WindowManagerService.this.mLastANRState = null;
                            com.android.server.wm.WindowManagerService.this.mAtmService.mLastANRState = null;
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 39:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock10 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock10) {
                        try {
                            com.android.server.wm.WallpaperController wallpaperController = (com.android.server.wm.WallpaperController) message.obj;
                            if (wallpaperController != null && wallpaperController.processWallpaperDrawPendingTimeout()) {
                                com.android.server.wm.WindowManagerService.this.mWindowPlacerLocked.performSurfacePlacement();
                            }
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 41:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock11 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock11) {
                        try {
                            com.android.server.wm.DisplayContent displayContent2 = (com.android.server.wm.DisplayContent) message.obj;
                            if (displayContent2 != null) {
                                displayContent2.adjustForImeIfNeeded();
                            }
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 51:
                    switch (message.arg1) {
                        case 0:
                            com.android.server.wm.WindowManagerService.this.mWindowAnimationScaleSetting = com.android.server.wm.WindowManagerService.this.getWindowAnimationScaleSetting();
                            return;
                        case 1:
                            com.android.server.wm.WindowManagerService.this.mTransitionAnimationScaleSetting = com.android.server.wm.WindowManagerService.this.getTransitionAnimationScaleSetting();
                            return;
                        case 2:
                            com.android.server.wm.WindowManagerService.this.mAnimatorDurationScaleSetting = com.android.server.wm.WindowManagerService.this.getAnimatorDurationScaleSetting();
                            com.android.server.wm.WindowManagerService.this.dispatchNewAnimatorScaleLocked(null);
                            return;
                        default:
                            return;
                    }
                case 52:
                    com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) message.obj;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock12 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock12) {
                        try {
                            windowState.mAttrs.flags &= -129;
                            windowState.hidePermanentlyLw();
                            windowState.setDisplayLayoutNeeded();
                            com.android.server.wm.WindowManagerService.this.mWindowPlacerLocked.performSurfacePlacement();
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 55:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock13 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock13) {
                        try {
                            com.android.server.wm.WindowManagerService.this.restorePointerIconLocked((com.android.server.wm.DisplayContent) message.obj, message.arg1, message.arg2);
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 58:
                    com.android.server.wm.WindowManagerService.this.mAmInternal.setHasOverlayUi(message.arg1, message.arg2 == 1);
                    return;
                case 60:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock14 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock14) {
                        try {
                            if (com.android.server.wm.WindowManagerService.this.mRecentsAnimationController != null) {
                                com.android.server.wm.WindowManagerService.this.mRecentsAnimationController.scheduleFailsafe();
                            }
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 61:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock15 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock15) {
                        try {
                            com.android.server.wm.WindowManagerService.this.updateFocusedWindowLocked(0, true);
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 62:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock16 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock16) {
                        try {
                            com.android.server.wm.WindowManagerService.this.onPointerDownOutsideFocusLocked(com.android.server.wm.WindowManagerService.this.getInputTargetFromToken((android.os.IBinder) message.obj));
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 64:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock17 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock17) {
                        try {
                            com.android.server.wm.WindowState windowState2 = (com.android.server.wm.WindowState) message.obj;
                            android.util.Slog.i(com.android.server.wm.WindowManagerService.TAG, "Blast sync timeout: " + windowState2);
                            windowState2.immediatelyNotifyBlastSync();
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 65:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock18 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock18) {
                        try {
                            com.android.server.wm.Task task = (com.android.server.wm.Task) message.obj;
                            task.reparent(com.android.server.wm.WindowManagerService.this.mRoot.getDefaultTaskDisplayArea(), true);
                            task.resumeNextFocusAfterReparent();
                        } finally {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 66:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock19 = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock19) {
                        try {
                            if (com.android.server.wm.WindowManagerService.this.mWindowsInsetsChanged > 0) {
                                com.android.server.wm.WindowManagerService.this.mWindowPlacerLocked.performSurfacePlacement();
                            }
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    return;
            }
        }

        void sendNewMessageDelayed(int i, java.lang.Object obj, long j) {
            removeMessages(i, obj);
            sendMessageDelayed(obtainMessage(i, obj), j);
        }
    }

    public android.view.IWindowSession openSession(android.view.IWindowSessionCallback iWindowSessionCallback) {
        return new com.android.server.wm.Session(this, iWindowSessionCallback);
    }

    public void getInitialDisplaySize(int i, android.graphics.Point point) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && displayContent.hasAccess(android.os.Binder.getCallingUid())) {
                    point.x = displayContent.mInitialDisplayWidth;
                    point.y = displayContent.mInitialDisplayHeight;
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void getBaseDisplaySize(int i, android.graphics.Point point) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && displayContent.hasAccess(android.os.Binder.getCallingUid())) {
                    point.x = displayContent.mBaseDisplayWidth;
                    point.y = displayContent.mBaseDisplayHeight;
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
    public void setForcedDisplaySize(int i, int i2, int i3) {
        setForcedDisplaySize_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedSize(i2, i3);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
    public void setForcedDisplayScalingMode(int i, int i2) {
        setForcedDisplayScalingMode_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedScalingMode(i2);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void setSandboxDisplayApis(int i, boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new java.lang.SecurityException("Must hold permission android.permission.WRITE_SECURE_SETTINGS");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setSandboxDisplayApis(z);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean applyForcedPropertiesForDefaultDisplay() {
        boolean z;
        int forcedDisplayDensityForUserLocked;
        int indexOf;
        com.android.server.wm.DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
        java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "display_size_forced");
        if (string == null || string.length() == 0) {
            string = android.os.SystemProperties.get(SIZE_OVERRIDE, (java.lang.String) null);
        }
        if (string != null && string.length() > 0 && (indexOf = string.indexOf(44)) > 0 && string.lastIndexOf(44) == indexOf) {
            try {
                android.graphics.Point validForcedSize = defaultDisplayContentLocked.getValidForcedSize(java.lang.Integer.parseInt(string.substring(0, indexOf)), java.lang.Integer.parseInt(string.substring(indexOf + 1)));
                int i = validForcedSize.x;
                int i2 = validForcedSize.y;
                if (defaultDisplayContentLocked.mBaseDisplayWidth == i && defaultDisplayContentLocked.mBaseDisplayHeight == i2) {
                    z = false;
                } else {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8641557333789260779L, 5, "FORCED DISPLAY SIZE: %dx%d", java.lang.Long.valueOf(i), java.lang.Long.valueOf(i2));
                    defaultDisplayContentLocked.updateBaseDisplayMetrics(i, i2, defaultDisplayContentLocked.mBaseDisplayDensity, defaultDisplayContentLocked.mBaseDisplayPhysicalXDpi, defaultDisplayContentLocked.mBaseDisplayPhysicalYDpi);
                    z = true;
                }
            } catch (java.lang.NumberFormatException e) {
            }
            forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked(this.mCurrentUserId);
            if (forcedDisplayDensityForUserLocked != 0 && forcedDisplayDensityForUserLocked != defaultDisplayContentLocked.mBaseDisplayDensity) {
                defaultDisplayContentLocked.mBaseDisplayDensity = forcedDisplayDensityForUserLocked;
                z = true;
            }
            if (defaultDisplayContentLocked.mDisplayScalingDisabled != (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "display_scaling_force", 0) != 0)) {
                return z;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3781141652793604337L, 0, "FORCED DISPLAY SCALING DISABLED", null);
            defaultDisplayContentLocked.mDisplayScalingDisabled = true;
            return true;
        }
        z = false;
        forcedDisplayDensityForUserLocked = getForcedDisplayDensityForUserLocked(this.mCurrentUserId);
        if (forcedDisplayDensityForUserLocked != 0) {
            defaultDisplayContentLocked.mBaseDisplayDensity = forcedDisplayDensityForUserLocked;
            z = true;
        }
        if (defaultDisplayContentLocked.mDisplayScalingDisabled != (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "display_scaling_force", 0) != 0)) {
        }
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
    public void clearForcedDisplaySize(int i) {
        clearForcedDisplaySize_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedSize(displayContent.mInitialDisplayWidth, displayContent.mInitialDisplayHeight, displayContent.mInitialPhysicalXDpi, displayContent.mInitialPhysicalXDpi);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getInitialDisplayDensity(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && displayContent.hasAccess(android.os.Binder.getCallingUid())) {
                    int initialDisplayDensity = displayContent.getInitialDisplayDensity();
                    resetPriorityAfterLockedSection();
                    return initialDisplayDensity;
                }
                android.view.DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                if (displayInfo != null && displayInfo.hasAccess(android.os.Binder.getCallingUid())) {
                    int i2 = displayInfo.logicalDensityDpi;
                    resetPriorityAfterLockedSection();
                    return i2;
                }
                resetPriorityAfterLockedSection();
                return -1;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getBaseDisplayDensity(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null && displayContent.hasAccess(android.os.Binder.getCallingUid())) {
                    int i2 = displayContent.mBaseDisplayDensity;
                    resetPriorityAfterLockedSection();
                    return i2;
                }
                resetPriorityAfterLockedSection();
                return -1;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getDisplayIdByUniqueId(java.lang.String str) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(str);
                if (displayContent != null && displayContent.hasAccess(android.os.Binder.getCallingUid())) {
                    int i = displayContent.mDisplayId;
                    resetPriorityAfterLockedSection();
                    return i;
                }
                resetPriorityAfterLockedSection();
                return -1;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
    public void setForcedDisplayDensityForUser(int i, int i2, int i3) {
        setForcedDisplayDensityForUser_enforcePermission();
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i3, false, true, "setForcedDisplayDensityForUser", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedDensity(i2, handleIncomingUser);
                    } else {
                        android.view.DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                        if (displayInfo != null) {
                            this.mDisplayWindowSettings.setForcedDensity(displayInfo, i2, i3);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
    public void clearForcedDisplayDensityForUser(int i, int i2) {
        clearForcedDisplayDensityForUser_enforcePermission();
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "clearForcedDisplayDensityForUser", null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        displayContent.setForcedDensity(displayContent.getInitialDisplayDensity(), handleIncomingUser);
                    } else {
                        android.view.DisplayInfo displayInfo = this.mDisplayManagerInternal.getDisplayInfo(i);
                        if (displayInfo != null) {
                            this.mDisplayWindowSettings.setForcedDensity(displayInfo, displayInfo.logicalDensityDpi, i2);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private int getForcedDisplayDensityForUserLocked(int i) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "display_density_forced", i);
        if (stringForUser == null || stringForUser.length() == 0) {
            stringForUser = android.os.SystemProperties.get(DENSITY_OVERRIDE, (java.lang.String) null);
        }
        if (stringForUser != null && stringForUser.length() > 0) {
            try {
                return java.lang.Integer.parseInt(stringForUser);
            } catch (java.lang.NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public void startWindowTrace() {
        this.mWindowTracing.startTrace(null);
    }

    public void stopWindowTrace() {
        this.mWindowTracing.stopTrace(null);
    }

    public void saveWindowTraceToFile() {
        this.mWindowTracing.saveForBugreport(null);
    }

    public boolean isWindowTraceEnabled() {
        return this.mWindowTracing.isEnabled();
    }

    public void startTransitionTrace() {
        this.mTransitionTracer.startTrace(null);
    }

    public void stopTransitionTrace() {
        this.mTransitionTracer.stopTrace(null);
    }

    public boolean isTransitionTraceEnabled() {
        return this.mTransitionTracer.isTracing();
    }

    public boolean registerCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) {
        return this.mBlurController.registerCrossWindowBlurEnabledListener(iCrossWindowBlurEnabledListener);
    }

    public void unregisterCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) {
        this.mBlurController.unregisterCrossWindowBlurEnabledListener(iCrossWindowBlurEnabledListener);
    }

    final com.android.server.wm.WindowState windowForClientLocked(com.android.server.wm.Session session, android.view.IWindow iWindow, boolean z) {
        return windowForClientLocked(session, iWindow.asBinder(), z);
    }

    final com.android.server.wm.WindowState windowForClientLocked(com.android.server.wm.Session session, android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.WindowState windowState = this.mWindowMap.get(iBinder);
        if (windowState == null) {
            if (z) {
                throw new java.lang.IllegalArgumentException("Requested window " + iBinder + " does not exist");
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 4117606810523219596L, 0, "Failed looking up window session=%s callers=%s", java.lang.String.valueOf(session), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
            return null;
        }
        if (session != null && windowState.mSession != session) {
            if (z) {
                throw new java.lang.IllegalArgumentException("Requested window " + iBinder + " is in session " + windowState.mSession + ", not " + session);
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 4117606810523219596L, 0, "Failed looking up window session=%s callers=%s", java.lang.String.valueOf(session), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
            return null;
        }
        return windowState;
    }

    void makeWindowFreezingScreenIfNeededLocked(com.android.server.wm.WindowState windowState) {
        if (this.mFrozenDisplayId != -1 && this.mFrozenDisplayId == windowState.getDisplayId() && this.mWindowsFreezingScreen != 2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 1233670725456443473L, 0, null, java.lang.String.valueOf(windowState));
            if (windowState.isVisibleRequested()) {
                windowState.setOrientationChanging(true);
            }
            if (this.mWindowsFreezingScreen == 0) {
                this.mWindowsFreezingScreen = 1;
                this.mH.sendNewMessageDelayed(11, windowState.getDisplayContent(), 2000L);
            }
        }
    }

    void checkDrawnWindowsLocked() {
        if (this.mWaitingForDrawnCallbacks.isEmpty()) {
            return;
        }
        for (int size = this.mWaitingForDrawnCallbacks.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer<?> keyAt = this.mWaitingForDrawnCallbacks.keyAt(size);
            for (int size2 = keyAt.mWaitingForDrawn.size() - 1; size2 >= 0; size2--) {
                com.android.server.wm.WindowState windowState = keyAt.mWaitingForDrawn.get(size2);
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, -1716033239040181528L, 508, null, java.lang.String.valueOf(windowState), java.lang.Boolean.valueOf(windowState.mRemoved), java.lang.Boolean.valueOf(windowState.isVisible()), java.lang.Boolean.valueOf(windowState.mHasSurface), java.lang.Long.valueOf(windowState.mWinAnimator.mDrawState));
                if (windowState.mRemoved || !windowState.mHasSurface || !windowState.isVisibleByPolicy()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, -4609828204247499633L, 0, null, java.lang.String.valueOf(windowState));
                    keyAt.mWaitingForDrawn.remove(windowState);
                    if (android.os.Trace.isTagEnabled(32L)) {
                        traceEndWaitingForWindowDrawn(windowState);
                    }
                } else if (windowState.hasDrawn()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, -7561054602203220590L, 0, null, java.lang.String.valueOf(windowState));
                    keyAt.mWaitingForDrawn.remove(windowState);
                    if (android.os.Trace.isTagEnabled(32L)) {
                        traceEndWaitingForWindowDrawn(windowState);
                    }
                }
            }
            if (keyAt.mWaitingForDrawn.isEmpty()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, 2809030008663191766L, 0, null, null);
                this.mH.removeMessages(24, keyAt);
                this.mWaitingForDrawnCallbacks.removeAt(size).sendToTarget();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void traceStartWaitingForWindowDrawn(com.android.server.wm.WindowState windowState) {
        java.lang.String str = "waitForAllWindowsDrawn#" + ((java.lang.Object) windowState.getWindowTag());
        android.os.Trace.asyncTraceBegin(32L, str.substring(0, java.lang.Math.min(127, str.length())), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void traceEndWaitingForWindowDrawn(com.android.server.wm.WindowState windowState) {
        java.lang.String str = "waitForAllWindowsDrawn#" + ((java.lang.Object) windowState.getWindowTag());
        android.os.Trace.asyncTraceEnd(32L, str.substring(0, java.lang.Math.min(127, str.length())), 0);
    }

    void requestTraversal() {
        this.mWindowPlacerLocked.requestTraversal();
    }

    void scheduleAnimationLocked() {
        this.mAnimator.scheduleAnimation();
    }

    boolean updateFocusedWindowLocked(int i, boolean z) {
        android.os.Trace.traceBegin(32L, "wmUpdateFocus");
        boolean updateFocusedWindowLocked = this.mRoot.updateFocusedWindowLocked(i, z);
        android.os.Trace.traceEnd(32L);
        return updateFocusedWindowLocked;
    }

    void startFreezingDisplay(int i, int i2) {
        startFreezingDisplay(i, i2, getDefaultDisplayContentLocked());
    }

    void startFreezingDisplay(int i, int i2, com.android.server.wm.DisplayContent displayContent) {
        startFreezingDisplay(i, i2, displayContent, -1);
    }

    void startFreezingDisplay(final int i, final int i2, final com.android.server.wm.DisplayContent displayContent, final int i3) {
        if (this.mDisplayFrozen || displayContent.getDisplayRotation().isRotatingSeamlessly() || !displayContent.isReady() || !displayContent.getDisplayPolicy().isScreenOnFully() || displayContent.getDisplayInfo().state == 1 || !displayContent.okToAnimate()) {
            return;
        }
        displayContent.requestDisplayUpdate(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerService.this.lambda$startFreezingDisplay$12(i, i2, displayContent, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startFreezingDisplay$12(int i, int i2, com.android.server.wm.DisplayContent displayContent, int i3) {
        android.os.Trace.traceBegin(32L, "WMS.doStartFreezingDisplay");
        doStartFreezingDisplay(i, i2, displayContent, i3);
        android.os.Trace.traceEnd(32L);
    }

    private void doStartFreezingDisplay(int i, int i2, com.android.server.wm.DisplayContent displayContent, int i3) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -1615905649072328410L, 5, null, java.lang.Long.valueOf(i), java.lang.Long.valueOf(i2), java.lang.String.valueOf(android.os.Debug.getCallers(8)));
        this.mScreenFrozenLock.acquire();
        this.mAtmService.startPowerMode(2);
        this.mDisplayFrozen = true;
        this.mDisplayFreezeTime = android.os.SystemClock.elapsedRealtime();
        this.mLastFinishedFreezeSource = null;
        this.mFrozenDisplayId = displayContent.getDisplayId();
        this.mInputManagerCallback.freezeInputDispatchingLw();
        if (displayContent.mAppTransition.isTransitionSet()) {
            displayContent.mAppTransition.freeze();
        }
        this.mLatencyTracker.onActionStart(6);
        this.mExitAnimId = i;
        this.mEnterAnimId = i2;
        if (i3 == -1) {
            i3 = displayContent.getDisplayInfo().rotation;
        }
        displayContent.setRotationAnimation(new com.android.server.wm.ScreenRotationAnimation(displayContent, i3));
    }

    void stopFreezingDisplayLocked() {
        int i;
        boolean z;
        boolean z2;
        if (this.mDisplayFrozen) {
            com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(this.mFrozenDisplayId);
            if (displayContent != null) {
                i = displayContent.mOpeningApps.size();
                z = displayContent.mWaitingForConfig;
                z2 = displayContent.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange();
            } else {
                i = 0;
                z = false;
                z2 = false;
            }
            if (z || z2 || this.mAppsFreezingScreen > 0 || this.mWindowsFreezingScreen == 1 || this.mClientFreezingScreen || i > 0) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 4565793239453546297L, 1887, null, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), java.lang.Long.valueOf(this.mAppsFreezingScreen), java.lang.Long.valueOf(this.mWindowsFreezingScreen), java.lang.Boolean.valueOf(this.mClientFreezingScreen), java.lang.Long.valueOf(i));
                return;
            }
            android.os.Trace.traceBegin(32L, "WMS.doStopFreezingDisplayLocked-" + this.mLastFinishedFreezeSource);
            doStopFreezingDisplayLocked(displayContent);
            android.os.Trace.traceEnd(32L);
        }
    }

    private void doStopFreezingDisplayLocked(com.android.server.wm.DisplayContent displayContent) {
        com.android.server.wm.ScreenRotationAnimation rotationAnimation;
        boolean z;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -6877112251967196129L, 0, null, null);
        this.mFrozenDisplayId = -1;
        boolean z2 = false;
        this.mDisplayFrozen = false;
        this.mInputManagerCallback.thawInputDispatchingLw();
        this.mLastDisplayFreezeDuration = (int) (android.os.SystemClock.elapsedRealtime() - this.mDisplayFreezeTime);
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Screen frozen for ");
        android.util.TimeUtils.formatDuration(this.mLastDisplayFreezeDuration, sb);
        if (this.mLastFinishedFreezeSource != null) {
            sb.append(" due to ");
            sb.append(this.mLastFinishedFreezeSource);
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 721393258715103117L, 0, "%s", java.lang.String.valueOf(sb.toString()));
        this.mH.removeMessages(17);
        this.mH.removeMessages(30);
        if (displayContent == null) {
            rotationAnimation = null;
        } else {
            rotationAnimation = displayContent.getRotationAnimation();
        }
        if (rotationAnimation != null && rotationAnimation.hasScreenshot()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -5706083447992207254L, 0, null, null);
            android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
            if (!displayContent.getDisplayRotation().validateRotationAnimation(this.mExitAnimId, this.mEnterAnimId, false)) {
                this.mEnterAnimId = 0;
                this.mExitAnimId = 0;
            }
            if (rotationAnimation.dismiss(this.mTransaction, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, getTransitionAnimationScaleLocked(), displayInfo.logicalWidth, displayInfo.logicalHeight, this.mExitAnimId, this.mEnterAnimId)) {
                this.mTransaction.apply();
                z = false;
            } else {
                rotationAnimation.kill();
                displayContent.setRotationAnimation(null);
                z = true;
            }
        } else {
            if (rotationAnimation != null) {
                rotationAnimation.kill();
                displayContent.setRotationAnimation(null);
            }
            z = true;
        }
        if (displayContent != null && displayContent.updateOrientation()) {
            z2 = true;
        }
        this.mScreenFrozenLock.release();
        if (z && displayContent != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 2233371241933584073L, 0, null, null);
            z2 |= displayContent.updateRotationUnchecked();
        }
        if (z2) {
            displayContent.sendNewConfiguration();
        }
        this.mAtmService.endPowerMode(2);
        this.mLatencyTracker.onActionEnd(6);
    }

    static int getPropertyInt(java.lang.String[] strArr, int i, int i2, int i3, android.util.DisplayMetrics displayMetrics) {
        java.lang.String str;
        if (i < strArr.length && (str = strArr[i]) != null && str.length() > 0) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.Exception e) {
            }
        }
        if (i2 == 0) {
            return i3;
        }
        return (int) android.util.TypedValue.applyDimension(i2, i3, displayMetrics);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:25:0x004c
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    void createWatermark() {
        /*
            r8 = this;
            com.android.server.wm.Watermark r0 = r8.mWatermark
            if (r0 == 0) goto L5
            return
        L5:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/system/etc/setup.conf"
            r0.<init>(r1)
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L5c java.io.FileNotFoundException -> L5f
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L57 java.io.IOException -> L5c java.io.FileNotFoundException -> L5f
            java.io.DataInputStream r0 = new java.io.DataInputStream     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 java.io.FileNotFoundException -> L55
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L53 java.io.FileNotFoundException -> L55
            java.lang.String r1 = r0.readLine()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            if (r1 == 0) goto L47
            java.lang.String r3 = "%"
            java.lang.String[] r1 = r1.split(r3)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            if (r1 == 0) goto L47
            int r3 = r1.length     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            if (r3 <= 0) goto L47
            com.android.server.wm.DisplayContent r3 = r8.getDefaultDisplayContentLocked()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            com.android.server.wm.Watermark r4 = new com.android.server.wm.Watermark     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            android.util.DisplayMetrics r5 = r3.mRealDisplayMetrics     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            android.view.SurfaceControl$Transaction r6 = r8.mTransaction     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            r4.<init>(r3, r5, r1, r6)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            r8.mWatermark = r4     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            android.view.SurfaceControl$Transaction r1 = r8.mTransaction     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            r1.apply()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41 java.io.FileNotFoundException -> L44
            goto L47
        L3f:
            r1 = move-exception
            goto L62
        L41:
            r1 = move-exception
            r1 = r0
            goto L73
        L44:
            r1 = move-exception
            r1 = r0
            goto L82
        L47:
            r0.close()     // Catch: java.io.IOException -> L4c
        L4b:
            goto L8f
        L4c:
            r0 = move-exception
            goto L4b
        L4e:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L62
        L53:
            r0 = move-exception
            goto L73
        L55:
            r0 = move-exception
            goto L82
        L57:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
            goto L62
        L5c:
            r0 = move-exception
            r2 = r1
            goto L73
        L5f:
            r0 = move-exception
            r2 = r1
            goto L82
        L62:
            if (r0 != 0) goto L6c
            if (r2 == 0) goto L72
            r2.close()     // Catch: java.io.IOException -> L6a
            goto L72
        L6a:
            r0 = move-exception
            goto L72
        L6c:
            r0.close()     // Catch: java.io.IOException -> L70
        L6f:
            goto L72
        L70:
            r0 = move-exception
            goto L6f
        L72:
            throw r1
        L73:
            if (r1 == 0) goto L7a
            r1.close()     // Catch: java.io.IOException -> L4c
            goto L4b
        L7a:
            if (r2 == 0) goto L8f
            r2.close()     // Catch: java.io.IOException -> L80
        L7f:
            goto L8f
        L80:
            r0 = move-exception
            goto L7f
        L82:
            if (r1 == 0) goto L89
            r1.close()     // Catch: java.io.IOException -> L4c
            goto L4b
        L89:
            if (r2 == 0) goto L8f
            r2.close()     // Catch: java.io.IOException -> L80
            goto L7f
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowManagerService.createWatermark():void");
    }

    public void setRecentsVisibility(boolean z) {
        if (!checkCallingPermission("android.permission.STATUS_BAR", "setRecentsVisibility()")) {
            throw new java.lang.SecurityException("Requires STATUS_BAR permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPolicy.setRecentsVisibilityLw(z);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void hideTransientBars(int i) {
        if (!checkCallingPermission("android.permission.STATUS_BAR", "hideTransientBars()")) {
            throw new java.lang.SecurityException("Requires STATUS_BAR permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.getInsetsPolicy().hideTransient();
                } else {
                    android.util.Slog.w(TAG, "hideTransientBars with invalid displayId=" + i);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void updateStaticPrivacyIndicatorBounds(int i, android.graphics.Rect[] rectArr) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.updatePrivacyIndicatorBounds(rectArr);
                } else {
                    android.util.Slog.w(TAG, "updateStaticPrivacyIndicatorBounds with invalid displayId=" + i);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @android.annotation.EnforcePermission("android.permission.STATUS_BAR")
    public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) {
        setNavBarVirtualKeyHapticFeedbackEnabled_enforcePermission();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPolicy.setNavBarVirtualKeyHapticFeedbackEnabledLw(z);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void createInputConsumer(android.os.IBinder iBinder, java.lang.String str, int i, android.view.InputChannel inputChannel) {
        if (!this.mAtmService.isCallerRecents(android.os.Binder.getCallingUid()) && this.mContext.checkCallingOrSelfPermission("android.permission.INPUT_CONSUMER") != 0) {
            throw new java.lang.SecurityException("createInputConsumer requires INPUT_CONSUMER permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent != null) {
                    displayContent.getInputMonitor().createInputConsumer(iBinder, str, inputChannel, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUserHandle());
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public boolean destroyInputConsumer(android.os.IBinder iBinder, int i) {
        if (!this.mAtmService.isCallerRecents(android.os.Binder.getCallingUid()) && this.mContext.checkCallingOrSelfPermission("android.permission.INPUT_CONSUMER") != 0) {
            throw new java.lang.SecurityException("destroyInputConsumer requires INPUT_CONSUMER permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean destroyInputConsumer = displayContent.getInputMonitor().destroyInputConsumer(iBinder);
                resetPriorityAfterLockedSection();
                return destroyInputConsumer;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.RESTRICTED_VR_ACCESS")
    public android.graphics.Region getCurrentImeTouchRegion() {
        getCurrentImeTouchRegion_enforcePermission();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.graphics.Region region = new android.graphics.Region();
                for (int size = this.mRoot.mChildren.size() - 1; size >= 0; size--) {
                    com.android.server.wm.DisplayContent displayContent = (com.android.server.wm.DisplayContent) this.mRoot.mChildren.get(size);
                    if (displayContent.mInputMethodWindow != null) {
                        displayContent.mInputMethodWindow.getTouchableRegion(region);
                        resetPriorityAfterLockedSection();
                        return region;
                    }
                }
                resetPriorityAfterLockedSection();
                return region;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean hasNavigationBar(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean hasNavigationBar = displayContent.getDisplayPolicy().hasNavigationBar();
                resetPriorityAfterLockedSection();
                return hasNavigationBar;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void lockNow(android.os.Bundle bundle) {
        this.mPolicy.lockNow(bundle);
    }

    public void showRecentApps() {
        this.mPolicy.showRecentApps();
    }

    public boolean isSafeModeEnabled() {
        return this.mSafeMode;
    }

    public boolean clearWindowContentFrameStats(android.os.IBinder iBinder) {
        if (!checkCallingPermission("android.permission.FRAME_STATS", "clearWindowContentFrameStats()")) {
            throw new java.lang.SecurityException("Requires FRAME_STATS permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowState = this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                com.android.server.wm.WindowSurfaceController windowSurfaceController = windowState.mWinAnimator.mSurfaceController;
                if (windowSurfaceController == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean clearWindowContentFrameStats = windowSurfaceController.clearWindowContentFrameStats();
                resetPriorityAfterLockedSection();
                return clearWindowContentFrameStats;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public android.view.WindowContentFrameStats getWindowContentFrameStats(android.os.IBinder iBinder) {
        if (!checkCallingPermission("android.permission.FRAME_STATS", "getWindowContentFrameStats()")) {
            throw new java.lang.SecurityException("Requires FRAME_STATS permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowState = this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    resetPriorityAfterLockedSection();
                    return null;
                }
                com.android.server.wm.WindowSurfaceController windowSurfaceController = windowState.mWinAnimator.mSurfaceController;
                if (windowSurfaceController == null) {
                    resetPriorityAfterLockedSection();
                    return null;
                }
                if (this.mTempWindowRenderStats == null) {
                    this.mTempWindowRenderStats = new android.view.WindowContentFrameStats();
                }
                android.view.WindowContentFrameStats windowContentFrameStats = this.mTempWindowRenderStats;
                if (windowSurfaceController.getWindowContentFrameStats(windowContentFrameStats)) {
                    resetPriorityAfterLockedSection();
                    return windowContentFrameStats;
                }
                resetPriorityAfterLockedSection();
                return null;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void dumpPolicyLocked(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("WINDOW MANAGER POLICY STATE (dumpsys window policy)");
        this.mPolicy.dump("    ", printWriter, strArr);
    }

    private void dumpAnimatorLocked(java.io.PrintWriter printWriter, boolean z) {
        printWriter.println("WINDOW MANAGER ANIMATOR STATE (dumpsys window animator)");
        this.mAnimator.dumpLocked(printWriter, "    ", z);
    }

    private void dumpTokensLocked(java.io.PrintWriter printWriter, boolean z) {
        printWriter.println("WINDOW MANAGER TOKENS (dumpsys window tokens)");
        this.mRoot.dumpTokens(printWriter, z);
    }

    private void dumpHighRefreshRateBlacklist(java.io.PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER HIGH REFRESH RATE BLACKLIST (dumpsys window refresh)");
        this.mHighRefreshRateDenylist.dump(printWriter);
    }

    private void dumpTraceStatus(java.io.PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER TRACE (dumpsys window trace)");
        printWriter.print(this.mWindowTracing.getStatus() + "\n");
    }

    private void dumpLogStatus(java.io.PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER LOGGING (dumpsys window logging)");
        if (android.tracing.Flags.perfettoProtologTracing()) {
            printWriter.println("Deprecated legacy command. Use Perfetto commands instead.");
        } else {
            com.android.internal.protolog.ProtoLogImpl_1545807451.getSingleInstance().getStatus();
        }
    }

    private void dumpSessionsLocked(java.io.PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER SESSIONS (dumpsys window sessions)");
        for (int i = 0; i < this.mSessions.size(); i++) {
            com.android.server.wm.Session valueAt = this.mSessions.valueAt(i);
            printWriter.print("  Session ");
            printWriter.print(valueAt);
            printWriter.println(':');
            valueAt.dump(printWriter, "    ");
        }
    }

    void dumpDebugLocked(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        this.mPolicy.dumpDebug(protoOutputStream, 1146756268033L);
        this.mRoot.dumpDebug(protoOutputStream, 1146756268034L, i);
        com.android.server.wm.DisplayContent topFocusedDisplayContent = this.mRoot.getTopFocusedDisplayContent();
        if (topFocusedDisplayContent.mCurrentFocus != null) {
            topFocusedDisplayContent.mCurrentFocus.writeIdentifierToProto(protoOutputStream, 1146756268035L);
        }
        if (topFocusedDisplayContent.mFocusedApp != null) {
            topFocusedDisplayContent.mFocusedApp.writeNameToProto(protoOutputStream, 1138166333444L);
        }
        com.android.server.wm.WindowState currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
        if (currentInputMethodWindow != null) {
            currentInputMethodWindow.writeIdentifierToProto(protoOutputStream, 1146756268037L);
        }
        protoOutputStream.write(1133871366150L, this.mDisplayFrozen);
        protoOutputStream.write(1120986464265L, topFocusedDisplayContent.getDisplayId());
        protoOutputStream.write(1133871366154L, this.mHardKeyboardAvailable);
        protoOutputStream.write(1133871366155L, true);
        this.mAtmService.mBackNavigationController.dumpDebug(protoOutputStream, 1146756268044L);
    }

    private void dumpWindowsLocked(final java.io.PrintWriter printWriter, boolean z, java.util.ArrayList<com.android.server.wm.WindowState> arrayList) {
        printWriter.println("WINDOW MANAGER WINDOWS (dumpsys window windows)");
        this.mRoot.dumpWindowsNoHeader(printWriter, z, arrayList);
        if (!this.mHidingNonSystemOverlayWindows.isEmpty()) {
            printWriter.println();
            printWriter.println("  Hiding System Alert Windows:");
            for (int size = this.mHidingNonSystemOverlayWindows.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowState windowState = this.mHidingNonSystemOverlayWindows.get(size);
                printWriter.print("  #");
                printWriter.print(size);
                printWriter.print(' ');
                printWriter.print(windowState);
                if (z) {
                    printWriter.println(":");
                    windowState.dump(printWriter, "    ", true);
                } else {
                    printWriter.println();
                }
            }
        }
        if (this.mForceRemoves != null && !this.mForceRemoves.isEmpty()) {
            printWriter.println();
            printWriter.println("  Windows force removing:");
            for (int size2 = this.mForceRemoves.size() - 1; size2 >= 0; size2--) {
                com.android.server.wm.WindowState windowState2 = this.mForceRemoves.get(size2);
                printWriter.print("  Removing #");
                printWriter.print(size2);
                printWriter.print(' ');
                printWriter.print(windowState2);
                if (z) {
                    printWriter.println(":");
                    windowState2.dump(printWriter, "    ", true);
                } else {
                    printWriter.println();
                }
            }
        }
        if (!this.mDestroySurface.isEmpty()) {
            printWriter.println();
            printWriter.println("  Windows waiting to destroy their surface:");
            for (int size3 = this.mDestroySurface.size() - 1; size3 >= 0; size3--) {
                com.android.server.wm.WindowState windowState3 = this.mDestroySurface.get(size3);
                if (arrayList == null || arrayList.contains(windowState3)) {
                    printWriter.print("  Destroy #");
                    printWriter.print(size3);
                    printWriter.print(' ');
                    printWriter.print(windowState3);
                    if (z) {
                        printWriter.println(":");
                        windowState3.dump(printWriter, "    ", true);
                    } else {
                        printWriter.println();
                    }
                }
            }
        }
        if (!this.mResizingWindows.isEmpty()) {
            printWriter.println();
            printWriter.println("  Windows waiting to resize:");
            for (int size4 = this.mResizingWindows.size() - 1; size4 >= 0; size4--) {
                com.android.server.wm.WindowState windowState4 = this.mResizingWindows.get(size4);
                if (arrayList == null || arrayList.contains(windowState4)) {
                    printWriter.print("  Resizing #");
                    printWriter.print(size4);
                    printWriter.print(' ');
                    printWriter.print(windowState4);
                    if (z) {
                        printWriter.println(":");
                        windowState4.dump(printWriter, "    ", true);
                    } else {
                        printWriter.println();
                    }
                }
            }
        }
        if (!this.mWaitingForDrawnCallbacks.isEmpty()) {
            printWriter.println();
            printWriter.println("  Clients waiting for these windows to be drawn:");
            this.mWaitingForDrawnCallbacks.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.wm.WindowManagerService.lambda$dumpWindowsLocked$13(printWriter, (com.android.server.wm.WindowContainer) obj, (android.os.Message) obj2);
                }
            });
        }
        printWriter.println();
        printWriter.print("  mGlobalConfiguration=");
        printWriter.println(this.mRoot.getConfiguration());
        printWriter.print("  mHasPermanentDpad=");
        printWriter.println(this.mHasPermanentDpad);
        this.mRoot.dumpTopFocusedDisplayId(printWriter);
        this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WindowManagerService.lambda$dumpWindowsLocked$14(printWriter, (com.android.server.wm.DisplayContent) obj);
            }
        });
        printWriter.print("  mBlurEnabled=");
        printWriter.println(this.mBlurController.getBlurEnabled());
        printWriter.print("  mLastDisplayFreezeDuration=");
        android.util.TimeUtils.formatDuration(this.mLastDisplayFreezeDuration, printWriter);
        if (this.mLastFinishedFreezeSource != null) {
            printWriter.print(" due to ");
            printWriter.print(this.mLastFinishedFreezeSource);
        }
        printWriter.println();
        this.mInputManagerCallback.dump(printWriter, "  ");
        this.mSnapshotController.dump(printWriter, " ");
        dumpAccessibilityController(printWriter, false);
        if (z) {
            java.lang.Object currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
            if (currentInputMethodWindow != null) {
                printWriter.print("  mInputMethodWindow=");
                printWriter.println(currentInputMethodWindow);
            }
            this.mWindowPlacerLocked.dump(printWriter, "  ");
            printWriter.print("  mSystemBooted=");
            printWriter.print(this.mSystemBooted);
            printWriter.print(" mDisplayEnabled=");
            printWriter.println(this.mDisplayEnabled);
            this.mRoot.dumpLayoutNeededDisplayIds(printWriter);
            printWriter.print("  mTransactionSequence=");
            printWriter.println(this.mTransactionSequence);
            printWriter.print("  mDisplayFrozen=");
            printWriter.print(this.mDisplayFrozen);
            printWriter.print(" windows=");
            printWriter.print(this.mWindowsFreezingScreen);
            printWriter.print(" client=");
            printWriter.print(this.mClientFreezingScreen);
            printWriter.print(" apps=");
            printWriter.println(this.mAppsFreezingScreen);
            com.android.server.wm.DisplayContent defaultDisplayContentLocked = getDefaultDisplayContentLocked();
            printWriter.print("  mRotation=");
            printWriter.println(defaultDisplayContentLocked.getRotation());
            printWriter.print("  mLastOrientation=");
            printWriter.println(defaultDisplayContentLocked.getLastOrientation());
            printWriter.print("  mWaitingForConfig=");
            printWriter.println(defaultDisplayContentLocked.mWaitingForConfig);
            printWriter.print("  mWindowsInsetsChanged=");
            printWriter.println(this.mWindowsInsetsChanged);
            this.mRotationWatcherController.dump(printWriter);
            printWriter.print("  Animation settings: disabled=");
            printWriter.print(this.mAnimationsDisabled);
            printWriter.print(" window=");
            printWriter.print(this.mWindowAnimationScaleSetting);
            printWriter.print(" transition=");
            printWriter.print(this.mTransitionAnimationScaleSetting);
            printWriter.print(" animator=");
            printWriter.println(this.mAnimatorDurationScaleSetting);
            if (this.mRecentsAnimationController != null) {
                printWriter.print("  mRecentsAnimationController=");
                printWriter.println(this.mRecentsAnimationController);
                this.mRecentsAnimationController.dump(printWriter, "    ");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpWindowsLocked$13(java.io.PrintWriter printWriter, com.android.server.wm.WindowContainer windowContainer, android.os.Message message) {
        printWriter.print("  WindowContainer ");
        printWriter.println(windowContainer.getName());
        for (int size = windowContainer.mWaitingForDrawn.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = windowContainer.mWaitingForDrawn.get(size);
            printWriter.print("  Waiting #");
            printWriter.print(size);
            printWriter.print(' ');
            printWriter.print(windowState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpWindowsLocked$14(java.io.PrintWriter printWriter, com.android.server.wm.DisplayContent displayContent) {
        int displayId = displayContent.getDisplayId();
        com.android.server.wm.InsetsControlTarget imeTarget = displayContent.getImeTarget(0);
        com.android.server.wm.InputTarget imeInputTarget = displayContent.getImeInputTarget();
        com.android.server.wm.InsetsControlTarget imeTarget2 = displayContent.getImeTarget(2);
        if (imeTarget != null) {
            printWriter.print("  imeLayeringTarget in display# ");
            printWriter.print(displayId);
            printWriter.print(' ');
            printWriter.println(imeTarget);
        }
        if (imeInputTarget != null) {
            printWriter.print("  imeInputTarget in display# ");
            printWriter.print(displayId);
            printWriter.print(' ');
            printWriter.println(imeInputTarget);
        }
        if (imeTarget2 != null) {
            printWriter.print("  imeControlTarget in display# ");
            printWriter.print(displayId);
            printWriter.print(' ');
            printWriter.println(imeTarget2);
        }
        printWriter.print("  Minimum task size of display#");
        printWriter.print(displayId);
        printWriter.print(' ');
        printWriter.print(displayContent.mMinSizeOfResizeableTaskDp);
    }

    private void dumpAccessibilityController(java.io.PrintWriter printWriter, boolean z) {
        boolean hasCallbacks = this.mAccessibilityController.hasCallbacks();
        if (!hasCallbacks && !z) {
            return;
        }
        if (!hasCallbacks) {
            printWriter.println("AccessibilityController doesn't have callbacks, but printing it anways:");
        } else {
            printWriter.println("AccessibilityController:");
        }
        this.mAccessibilityController.dump(printWriter, "  ");
    }

    private void dumpAccessibilityLocked(java.io.PrintWriter printWriter) {
        dumpAccessibilityController(printWriter, true);
    }

    private boolean dumpWindows(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        final java.util.ArrayList<com.android.server.wm.WindowState> arrayList = new java.util.ArrayList<>();
        if ("apps".equals(str) || com.android.server.wm.ActivityTaskManagerService.DUMP_VISIBLE_ACTIVITIES.equals(str) || "visible-apps".equals(str)) {
            final boolean contains = str.contains("apps");
            final boolean contains2 = str.contains(com.android.server.wm.ActivityTaskManagerService.DUMP_VISIBLE_ACTIVITIES);
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (contains) {
                    try {
                        this.mRoot.dumpDisplayContents(printWriter);
                    } catch (java.lang.Throwable th) {
                        resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                this.mRoot.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda28
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.WindowManagerService.lambda$dumpWindows$15(contains2, contains, arrayList, (com.android.server.wm.WindowState) obj);
                    }
                }, true);
            }
            resetPriorityAfterLockedSection();
        } else {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    this.mRoot.getWindowsByName(arrayList, str);
                } finally {
                }
            }
            resetPriorityAfterLockedSection();
        }
        if (arrayList.isEmpty()) {
            return false;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock3 = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock3) {
            try {
                dumpWindowsLocked(printWriter, z, arrayList);
            } finally {
            }
        }
        resetPriorityAfterLockedSection();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpWindows$15(boolean z, boolean z2, java.util.ArrayList arrayList, com.android.server.wm.WindowState windowState) {
        if (!z || windowState.isVisible()) {
            if (!z2 || windowState.mActivityRecord != null) {
                arrayList.add(windowState);
            }
        }
    }

    private void dumpLastANRLocked(java.io.PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER LAST ANR (dumpsys window lastanr)");
        if (this.mLastANRState == null) {
            printWriter.println("  <no ANR has occurred since boot>");
        } else {
            printWriter.println(this.mLastANRState);
        }
    }

    void saveANRStateLocked(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.WindowState windowState, java.lang.String str) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        final java.io.PrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(stringWriter, false, 1024);
        fastPrintWriter.println("  ANR time: " + java.text.DateFormat.getDateTimeInstance().format(new java.util.Date()));
        if (activityRecord != null) {
            fastPrintWriter.println("  Application at fault: " + activityRecord.stringName);
        }
        if (windowState != null) {
            fastPrintWriter.println("  Window at fault: " + ((java.lang.Object) windowState.mAttrs.getTitle()));
        }
        if (str != null) {
            fastPrintWriter.println("  Reason: " + str);
        }
        fastPrintWriter.println();
        final java.util.ArrayList<com.android.server.wm.WindowState> arrayList = new java.util.ArrayList<>();
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = this.mRoot.getChildAt(childCount);
            int displayId = childAt.getDisplayId();
            final com.android.server.wm.WindowState windowState2 = childAt.mCurrentFocus;
            final com.android.server.wm.ActivityRecord activityRecord2 = childAt.mFocusedApp;
            fastPrintWriter.println("  Display #" + displayId + " currentFocus=" + windowState2 + " focusedApp=" + activityRecord2);
            if (!childAt.mWinAddedSinceNullFocus.isEmpty()) {
                fastPrintWriter.println("  Windows added in display #" + displayId + " since null focus: " + childAt.mWinAddedSinceNullFocus);
            }
            if (!childAt.mWinRemovedSinceNullFocus.isEmpty()) {
                fastPrintWriter.println("  Windows removed in display #" + displayId + " since null focus: " + childAt.mWinRemovedSinceNullFocus);
            }
            fastPrintWriter.println("  Tasks in top down Z order:");
            childAt.forAllTaskDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda21
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.TaskDisplayArea) obj).dump(fastPrintWriter, "    ", false);
                }
            });
            childAt.getInputMonitor().dump(fastPrintWriter, "  ");
            fastPrintWriter.println();
            childAt.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.WindowManagerService.lambda$saveANRStateLocked$17(com.android.server.wm.WindowState.this, activityRecord2, arrayList, (com.android.server.wm.WindowState) obj);
                }
            }, true);
        }
        if (windowState != null && !arrayList.contains(windowState)) {
            arrayList.add(windowState);
        }
        this.mRoot.dumpWindowsNoHeader(fastPrintWriter, true, arrayList);
        fastPrintWriter.println();
        fastPrintWriter.close();
        this.mLastANRState = stringWriter.toString();
        this.mH.removeMessages(38);
        this.mH.sendEmptyMessageDelayed(38, com.android.server.usage.AppStandbyController.ConstantsObserver.DEFAULT_SYSTEM_UPDATE_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveANRStateLocked$17(com.android.server.wm.WindowState windowState, com.android.server.wm.ActivityRecord activityRecord, java.util.ArrayList arrayList, com.android.server.wm.WindowState windowState2) {
        if ((windowState != null && java.util.Objects.equals(windowState2.mAttrs.packageName, windowState.mAttrs.packageName)) || (activityRecord != null && java.util.Objects.equals(windowState2.mAttrs.packageName, activityRecord.packageName))) {
            arrayList.add(windowState2);
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.server.utils.PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00c8, code lost:
    
        r9 = new android.util.proto.ProtoOutputStream(r8);
        r8 = r7.mGlobalLock;
        boostPriorityForLockedSection();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d2, code lost:
    
        monitor-enter(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d3, code lost:
    
        dumpDebugLocked(r9, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d6, code lost:
    
        monitor-exit(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d7, code lost:
    
        resetPriorityAfterLockedSection();
        r9.flush();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00dd, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00de, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e3, code lost:
    
        throw r9;
     */
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void doDump(java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
        java.lang.String str;
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            int i = 0;
            boolean z2 = false;
            while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
                i++;
                if ("-a".equals(str)) {
                    z2 = true;
                } else {
                    if ("-h".equals(str)) {
                        printWriter.println("Window manager dump options:");
                        printWriter.println("  [-a] [-h] [cmd] ...");
                        printWriter.println("  cmd may be one of:");
                        printWriter.println("    l[astanr]: last ANR information");
                        printWriter.println("    p[policy]: policy state");
                        printWriter.println("    a[animator]: animator state");
                        printWriter.println("    s[essions]: active sessions");
                        printWriter.println("    surfaces: active surfaces (debugging enabled only)");
                        printWriter.println("    d[isplays]: active display contents");
                        printWriter.println("    t[okens]: token list");
                        printWriter.println("    w[indows]: window list");
                        printWriter.println("    a11y[accessibility]: accessibility-related state");
                        printWriter.println("    package-config: installed packages having app-specific config");
                        printWriter.println("    trace: print trace status and write Winscope trace to file");
                        printWriter.println("  cmd may also be a NAME to dump windows.  NAME may");
                        printWriter.println("    be a partial substring in a window name, a");
                        printWriter.println("    Window hex object identifier, or");
                        printWriter.println("    \"all\" for all windows, or");
                        printWriter.println("    \"visible\" for the visible windows.");
                        printWriter.println("    \"visible-apps\" for the visible app windows.");
                        printWriter.println("  -a: include all available server state.");
                        printWriter.println("  --proto: output dump in protocol buffer format.");
                        return;
                    }
                    printWriter.println("Unknown argument: " + str + "; use -h for help");
                }
            }
            if (i < strArr.length) {
                java.lang.String str2 = strArr[i];
                if (com.android.server.wm.ActivityTaskManagerService.DUMP_LASTANR_CMD.equals(str2) || "l".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            dumpLastANRLocked(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("policy".equals(str2) || "p".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            dumpPolicyLocked(printWriter, strArr);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("animator".equals(str2) || com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_SHORT_CMD.equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock3 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock3) {
                        try {
                            dumpAnimatorLocked(printWriter, true);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("sessions".equals(str2) || "s".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock4 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock4) {
                        try {
                            dumpSessionsLocked(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("displays".equals(str2) || "d".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock5 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock5) {
                        try {
                            this.mRoot.dumpDisplayContents(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("tokens".equals(str2) || "t".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock6 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock6) {
                        try {
                            dumpTokensLocked(printWriter, true);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("windows".equals(str2) || "w".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock7 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock7) {
                        try {
                            dumpWindowsLocked(printWriter, true, null);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("accessibility".equals(str2) || "a11y".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock8 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock8) {
                        try {
                            dumpAccessibilityLocked(printWriter);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("all".equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock9 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock9) {
                        try {
                            dumpWindowsLocked(printWriter, true, null);
                        } finally {
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (com.android.server.wm.ActivityTaskManagerService.DUMP_CONTAINERS_CMD.equals(str2)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock10 = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock10) {
                        try {
                            this.mRoot.dumpChildrenNames(printWriter, "");
                            printWriter.println(" ");
                            this.mRoot.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda27
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    printWriter.println((com.android.server.wm.WindowState) obj);
                                }
                            }, true);
                        } finally {
                            resetPriorityAfterLockedSection();
                        }
                    }
                    resetPriorityAfterLockedSection();
                    return;
                }
                if ("trace".equals(str2)) {
                    dumpTraceStatus(printWriter);
                    return;
                }
                if ("logging".equals(str2)) {
                    dumpLogStatus(printWriter);
                    return;
                }
                if ("refresh".equals(str2)) {
                    dumpHighRefreshRateBlacklist(printWriter);
                    return;
                }
                if ("constants".equals(str2)) {
                    this.mConstants.dump(printWriter);
                    return;
                }
                if ("package-config".equals(str2)) {
                    this.mAtmService.dumpInstalledPackagesConfig(printWriter);
                    return;
                }
                if (!dumpWindows(printWriter, str2, z2)) {
                    printWriter.println("Bad window command, or no windows match: " + str2);
                    printWriter.println("Use -h for help.");
                    return;
                }
                return;
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock11 = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock11) {
                try {
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpLastANRLocked(printWriter);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpPolicyLocked(printWriter, strArr);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpAnimatorLocked(printWriter, z2);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpSessionsLocked(printWriter);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    this.mRoot.dumpDisplayContents(printWriter);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpTokensLocked(printWriter, z2);
                    printWriter.println();
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpWindowsLocked(printWriter, z2, null);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpTraceStatus(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpLogStatus(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    dumpHighRefreshRateBlacklist(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    this.mAtmService.dumpInstalledPackagesConfig(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    this.mConstants.dump(printWriter);
                    if (z2) {
                        printWriter.println("-------------------------------------------------------------------------------");
                    }
                    this.mSystemPerformanceHinter.dump(printWriter, "");
                    this.mTrustedPresentationListenerController.dump(printWriter);
                    this.mSensitiveContentPackages.dump(printWriter);
                    this.mScreenRecordingCallbackController.dump(printWriter);
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    com.android.server.wm.DisplayContent getDefaultDisplayContentLocked() {
        return this.mRoot.getDisplayContent(0);
    }

    public void onOverlayChanged() {
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerService.this.lambda$onOverlayChanged$20();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOverlayChanged$20() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtmService.deferWindowLayout();
                this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.WindowManagerService.lambda$onOverlayChanged$19((com.android.server.wm.DisplayContent) obj);
                    }
                });
                this.mAtmService.continueWindowLayout();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onOverlayChanged$19(com.android.server.wm.DisplayContent displayContent) {
        displayContent.getDisplayPolicy().onOverlayChanged();
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public java.lang.Object getWindowManagerLock() {
        return this.mGlobalLock;
    }

    public int getDockedStackSide() {
        return 0;
    }

    void setForceDesktopModeOnExternalDisplays(boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mForceDesktopModeOnExternalDisplays = z;
                this.mRoot.updateDisplayImePolicyCache();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    @com.android.internal.annotations.VisibleForTesting
    void setIsPc(boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mIsPc = z;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    static int dipToPixel(int i, android.util.DisplayMetrics displayMetrics) {
        return (int) android.util.TypedValue.applyDimension(1, i, displayMetrics);
    }

    public void registerPinnedTaskListener(int i, android.view.IPinnedTaskListener iPinnedTaskListener) {
        if (!checkCallingPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "registerPinnedTaskListener()") || !this.mAtmService.mSupportsPictureInPicture) {
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.getDisplayContent(i).getPinnedTaskController().registerPinnedTaskListener(iPinnedTaskListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) {
        enforceRegisterWindowManagerListenersPermission("requestAppKeyboardShortcuts");
        com.android.server.wm.WindowState focusedWindow = getFocusedWindow();
        if (focusedWindow == null || focusedWindow.mClient == null) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
            return;
        }
        try {
            focusedWindow.mClient.requestAppKeyboardShortcuts(iResultReceiver, i);
        } catch (android.os.RemoteException e) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
        }
    }

    public void requestImeKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) {
        enforceRegisterWindowManagerListenersPermission("requestImeKeyboardShortcuts");
        com.android.server.wm.WindowState currentInputMethodWindow = this.mRoot.getCurrentInputMethodWindow();
        if (currentInputMethodWindow == null || currentInputMethodWindow.mClient == null) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
            return;
        }
        try {
            currentInputMethodWindow.mClient.requestAppKeyboardShortcuts(iResultReceiver, i);
        } catch (android.os.RemoteException e) {
            notifyReceiverWithEmptyBundle(iResultReceiver);
        }
    }

    private void enforceRegisterWindowManagerListenersPermission(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", str);
    }

    private static void notifyReceiverWithEmptyBundle(com.android.internal.os.IResultReceiver iResultReceiver) {
        try {
            iResultReceiver.send(0, android.os.Bundle.EMPTY);
        } catch (android.os.RemoteException e) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 1010635158502326025L, 0, "unable to call receiver for empty keyboard shortcuts", null);
        }
    }

    public void getStableInsets(int i, android.graphics.Rect rect) throws android.os.RemoteException {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                getStableInsetsLocked(i, rect);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    void getStableInsetsLocked(int i, android.graphics.Rect rect) {
        rect.setEmpty();
        com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (displayContent != null) {
            android.view.DisplayInfo displayInfo = displayContent.getDisplayInfo();
            rect.set(displayContent.getDisplayPolicy().getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight).mConfigInsets);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MousePositionTracker implements android.view.WindowManagerPolicyConstants.PointerEventListener {
        private boolean mLatestEventWasMouse;
        private float mLatestMouseX;
        private float mLatestMouseY;
        private int mPointerDisplayId;

        private MousePositionTracker() {
            this.mPointerDisplayId = -1;
        }

        boolean updatePosition(int i, float f, float f2) {
            synchronized (this) {
                try {
                    this.mLatestEventWasMouse = true;
                    if (i != this.mPointerDisplayId) {
                        return false;
                    }
                    this.mLatestMouseX = f;
                    this.mLatestMouseY = f2;
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void setPointerDisplayId(int i) {
            synchronized (this) {
                this.mPointerDisplayId = i;
            }
        }

        public void onPointerEvent(android.view.MotionEvent motionEvent) {
            if (motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1)) {
                updatePosition(motionEvent.getDisplayId(), motionEvent.getRawX(), motionEvent.getRawY());
            } else {
                synchronized (this) {
                    this.mLatestEventWasMouse = false;
                }
            }
        }
    }

    void updatePointerIcon(android.view.IWindow iWindow) {
        if (this.mMousePositionTracker == null) {
            return;
        }
        synchronized (this.mMousePositionTracker) {
            try {
                if (this.mMousePositionTracker.mLatestEventWasMouse) {
                    float f = this.mMousePositionTracker.mLatestMouseX;
                    float f2 = this.mMousePositionTracker.mLatestMouseY;
                    int i = this.mMousePositionTracker.mPointerDisplayId;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                    boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (this.mDragDropController.dragDropActiveLocked()) {
                                resetPriorityAfterLockedSection();
                                return;
                            }
                            com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked((com.android.server.wm.Session) null, iWindow, false);
                            if (windowForClientLocked == null) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 1278715281433572858L, 0, "Bad requesting window %s", java.lang.String.valueOf(iWindow));
                                resetPriorityAfterLockedSection();
                                return;
                            }
                            com.android.server.wm.DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                            if (displayContent == null) {
                                resetPriorityAfterLockedSection();
                                return;
                            }
                            if (i != displayContent.getDisplayId()) {
                                resetPriorityAfterLockedSection();
                                return;
                            }
                            com.android.server.wm.WindowState touchableWinAtPointLocked = displayContent.getTouchableWinAtPointLocked(f, f2);
                            if (touchableWinAtPointLocked != windowForClientLocked) {
                                resetPriorityAfterLockedSection();
                                return;
                            }
                            try {
                                touchableWinAtPointLocked.mClient.updatePointerIcon(touchableWinAtPointLocked.translateToWindowX(f), touchableWinAtPointLocked.translateToWindowY(f2));
                            } catch (android.os.RemoteException e) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -707915937966769475L, 0, "unable to update pointer icon", null);
                            }
                            resetPriorityAfterLockedSection();
                        } catch (java.lang.Throwable th) {
                            resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    void restorePointerIconLocked(com.android.server.wm.DisplayContent displayContent, float f, float f2) {
        if (this.mMousePositionTracker == null || !this.mMousePositionTracker.updatePosition(displayContent.getDisplayId(), f, f2)) {
            return;
        }
        com.android.server.wm.WindowState touchableWinAtPointLocked = displayContent.getTouchableWinAtPointLocked(f, f2);
        if (touchableWinAtPointLocked != null) {
            try {
                touchableWinAtPointLocked.mClient.updatePointerIcon(touchableWinAtPointLocked.translateToWindowX(f), touchableWinAtPointLocked.translateToWindowY(f2));
                return;
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -8663841671650918687L, 0, "unable to restore pointer icon", null);
                return;
            }
        }
        ((android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class)).setPointerIconType(1000);
    }

    void setMousePointerDisplayId(int i) {
        if (this.mMousePositionTracker == null) {
            return;
        }
        this.mMousePositionTracker.setPointerDisplayId(i);
    }

    void updateTapExcludeRegion(android.view.IWindow iWindow, android.graphics.Region region) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked((com.android.server.wm.Session) null, iWindow, false);
                if (windowForClientLocked == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 1278715281433572858L, 0, "Bad requesting window %s", java.lang.String.valueOf(iWindow));
                    resetPriorityAfterLockedSection();
                } else {
                    windowForClientLocked.updateTapExcludeRegion(region);
                    resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void requestScrollCapture(int i, @android.annotation.Nullable android.os.IBinder iBinder, int i2, android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        android.view.ScrollCaptureResponse.Builder builder;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "requestScrollCapture()")) {
            throw new java.lang.SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                builder = new android.view.ScrollCaptureResponse.Builder();
                windowManagerGlobalLock = this.mGlobalLock;
                boostPriorityForLockedSection();
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -8972916676375201577L, 0, "requestScrollCapture: caught exception dispatching callback: %s", java.lang.String.valueOf(e));
            }
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -6186782212018913664L, 1, "Invalid displayId for requestScrollCapture: %d", java.lang.Long.valueOf(i));
                        builder.setDescription(java.lang.String.format("bad displayId: %d", java.lang.Integer.valueOf(i)));
                        iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.WindowState findScrollCaptureTargetWindow = displayContent.findScrollCaptureTargetWindow(iBinder != null ? windowForClientLocked((com.android.server.wm.Session) null, iBinder, false) : null, i2);
                    if (findScrollCaptureTargetWindow == null) {
                        builder.setDescription("findScrollCaptureTargetWindow returned null");
                        iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    try {
                        findScrollCaptureTargetWindow.mClient.requestScrollCapture(iScrollCaptureResponseListener);
                    } catch (android.os.RemoteException e2) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 51378282333944649L, 0, "requestScrollCapture: caught exception dispatching to window.token=%s", java.lang.String.valueOf(findScrollCaptureTargetWindow.mClient.asBinder()));
                        builder.setWindowTitle(findScrollCaptureTargetWindow.getName());
                        builder.setPackageName(findScrollCaptureTargetWindow.getOwningPackage());
                        builder.setDescription(java.lang.String.format("caught exception: %s", e2));
                        iScrollCaptureResponseListener.onScrollCaptureResponse(builder.build());
                    }
                    resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getWindowingMode(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "getWindowingMode()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -1875125162673622728L, 1, "Attempted to get windowing mode of a display that does not exist: %d", java.lang.Long.valueOf(i));
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                int windowingModeLocked = this.mDisplayWindowSettings.getWindowingModeLocked(displayContent);
                resetPriorityAfterLockedSection();
                return windowingModeLocked;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setWindowingMode(int i, int i2) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setWindowingMode()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3938331948687900219L, 1, "Attempted to set windowing mode to a display that does not exist: %d", java.lang.Long.valueOf(i));
                        resetPriorityAfterLockedSection();
                        return;
                    }
                    int windowingMode = displayContentOrCreate.getWindowingMode();
                    this.mDisplayWindowSettings.setWindowingModeLocked(displayContentOrCreate, i2);
                    displayContentOrCreate.reconfigureDisplayLocked();
                    if (windowingMode != displayContentOrCreate.getWindowingMode()) {
                        displayContentOrCreate.sendNewConfiguration();
                        displayContentOrCreate.executeAppTransition();
                    }
                    resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getRemoveContentMode(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "getRemoveContentMode()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 4200292050699107329L, 1, "Attempted to get remove mode of a display that does not exist: %d", java.lang.Long.valueOf(i));
                    resetPriorityAfterLockedSection();
                    return 0;
                }
                int removeContentModeLocked = this.mDisplayWindowSettings.getRemoveContentModeLocked(displayContent);
                resetPriorityAfterLockedSection();
                return removeContentModeLocked;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setRemoveContentMode(int i, int i2) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setRemoveContentMode()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -5574580669790275797L, 1, "Attempted to set remove mode to a display that does not exist: %d", java.lang.Long.valueOf(i));
                        resetPriorityAfterLockedSection();
                    } else {
                        this.mDisplayWindowSettings.setRemoveContentModeLocked(displayContentOrCreate, i2);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean shouldShowWithInsecureKeyguard(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "shouldShowWithInsecureKeyguard()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 525945815055875796L, 1, "Attempted to get flag of a display that does not exist: %d", java.lang.Long.valueOf(i));
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean shouldShowWithInsecureKeyguardLocked = this.mDisplayWindowSettings.shouldShowWithInsecureKeyguardLocked(displayContent);
                resetPriorityAfterLockedSection();
                return shouldShowWithInsecureKeyguardLocked;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setShouldShowWithInsecureKeyguard(int i, boolean z) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setShouldShowWithInsecureKeyguard()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 8186524992939307511L, 1, "Attempted to set flag to a display that does not exist: %d", java.lang.Long.valueOf(i));
                        resetPriorityAfterLockedSection();
                    } else {
                        this.mDisplayWindowSettings.setShouldShowWithInsecureKeyguardLocked(displayContentOrCreate, z);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean shouldShowSystemDecors(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "shouldShowSystemDecors()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, -600035824255550632L, 1, "Attempted to get system decors flag of a display that does not exist: %d", java.lang.Long.valueOf(i));
                    resetPriorityAfterLockedSection();
                    return false;
                }
                boolean supportsSystemDecorations = displayContent.supportsSystemDecorations();
                resetPriorityAfterLockedSection();
                return supportsSystemDecorations;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setShouldShowSystemDecors(int i, boolean z) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setShouldShowSystemDecors()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3056518663346732662L, 1, "Attempted to set system decors flag to a display that does not exist: %d", java.lang.Long.valueOf(i));
                        resetPriorityAfterLockedSection();
                    } else if (displayContentOrCreate.isTrusted()) {
                        this.mDisplayWindowSettings.setShouldShowSystemDecorsLocked(displayContentOrCreate, z);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    } else {
                        throw new java.lang.SecurityException("Attempted to set system decors flag to an untrusted virtual display: " + i);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getDisplayImePolicy(int i) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "getDisplayImePolicy()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        java.util.Map<java.lang.Integer, java.lang.Integer> map = this.mDisplayImePolicyCache;
        if (!map.containsKey(java.lang.Integer.valueOf(i))) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 5177195624625618567L, 1, "Attempted to get IME policy of a display that does not exist: %d", java.lang.Long.valueOf(i));
            return 1;
        }
        return map.get(java.lang.Integer.valueOf(i)).intValue();
    }

    public void setDisplayImePolicy(int i, int i2) {
        if (!checkCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setDisplayImePolicy()")) {
            throw new java.lang.SecurityException("Requires INTERNAL_SYSTEM_WINDOW permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, null);
                    if (displayContentOrCreate == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 3932627933834459400L, 1, "Attempted to set IME policy to a display that does not exist: %d", java.lang.Long.valueOf(i));
                        resetPriorityAfterLockedSection();
                    } else if (displayContentOrCreate.isTrusted()) {
                        this.mDisplayWindowSettings.setDisplayImePolicy(displayContentOrCreate, i2);
                        displayContentOrCreate.reconfigureDisplayLocked();
                        resetPriorityAfterLockedSection();
                    } else {
                        throw new java.lang.SecurityException("Attempted to set IME policy to an untrusted virtual display: " + i);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerShortcutKey(long j, com.android.internal.policy.IShortcutService iShortcutService) throws android.os.RemoteException {
        if (!checkCallingPermission("android.permission.REGISTER_WINDOW_MANAGER_LISTENERS", "registerShortcutKey")) {
            throw new java.lang.SecurityException("Requires REGISTER_WINDOW_MANAGER_LISTENERS permission");
        }
        this.mPolicy.registerShortcutKey(j, iShortcutService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class LocalService extends com.android.server.wm.WindowManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal getAccessibilityController() {
            return com.android.server.wm.AccessibilityController.getAccessibilityControllerInternal(com.android.server.wm.WindowManagerService.this);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void clearSnapshotCache() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mTaskSnapshotController.clearSnapshotCache();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void requestTraversalFromDisplayManager() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.requestTraversal();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void onDisplayManagerReceivedDeviceState(final int i) {
            com.android.server.wm.WindowManagerService.this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.WindowManagerService.LocalService.this.lambda$onDisplayManagerReceivedDeviceState$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayManagerReceivedDeviceState$0(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mRoot.onDisplayManagerReceivedDeviceState(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setMagnificationSpec(int i, android.view.MagnificationSpec magnificationSpec) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        com.android.server.wm.WindowManagerService.this.mAccessibilityController.setMagnificationSpec(i, magnificationSpec);
                    } else {
                        throw new java.lang.IllegalStateException("Magnification callbacks not set!");
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setFullscreenMagnificationActivated(int i, boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        com.android.server.wm.WindowManagerService.this.mAccessibilityController.setFullscreenMagnificationActivated(i, z);
                    } else {
                        throw new java.lang.IllegalStateException("Magnification callbacks not set!");
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void getMagnificationRegion(int i, @android.annotation.NonNull android.graphics.Region region) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.WindowManagerService.this.mAccessibilityController.hasCallbacks()) {
                        com.android.server.wm.WindowManagerService.this.mAccessibilityController.getMagnificationRegion(i, region);
                    } else {
                        throw new java.lang.IllegalStateException("Magnification callbacks not set!");
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean setMagnificationCallbacks(int i, @android.annotation.Nullable com.android.server.wm.WindowManagerInternal.MagnificationCallbacks magnificationCallbacks) {
            boolean magnificationCallbacks2;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    magnificationCallbacks2 = com.android.server.wm.WindowManagerService.this.mAccessibilityController.setMagnificationCallbacks(i, magnificationCallbacks);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return magnificationCallbacks2;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setWindowsForAccessibilityCallback(int i, com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mAccessibilityController.setWindowsForAccessibilityCallback(i, windowsForAccessibilityCallback);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setInputFilter(android.view.IInputFilter iInputFilter) {
            com.android.server.wm.WindowManagerService.this.mInputManager.setInputFilter(iInputFilter);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public android.os.IBinder getFocusedWindowToken() {
            android.os.IBinder focusedWindowToken;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    focusedWindowToken = com.android.server.wm.WindowManagerService.this.mAccessibilityController.getFocusedWindowToken();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return focusedWindowToken;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public android.os.IBinder getFocusedWindowTokenFromWindowStates() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState focusedWindowLocked = com.android.server.wm.WindowManagerService.this.getFocusedWindowLocked();
                    if (focusedWindowLocked == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    android.os.IBinder asBinder = focusedWindowLocked.mClient.asBinder();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return asBinder;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void moveDisplayToTopIfAllowed(int i) {
            com.android.server.wm.WindowManagerService.this.moveDisplayToTopIfAllowed(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void requestWindowFocus(android.os.IBinder iBinder) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.onPointerDownOutsideFocusLocked(com.android.server.wm.WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder));
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isKeyguardLocked() {
            return com.android.server.wm.WindowManagerService.this.isKeyguardLocked();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isKeyguardShowingAndNotOccluded() {
            return com.android.server.wm.WindowManagerService.this.isKeyguardShowingAndNotOccluded();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isKeyguardSecure(int i) {
            return com.android.server.wm.WindowManagerService.this.mPolicy.isKeyguardSecure(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void showGlobalActions() {
            com.android.server.wm.WindowManagerService.this.showGlobalActions();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void getWindowFrame(android.os.IBinder iBinder, android.graphics.Rect rect) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState != null) {
                        rect.set(windowState.getFrame());
                    } else {
                        rect.setEmpty();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public android.util.Pair<android.graphics.Matrix, android.view.MagnificationSpec> getWindowTransformationMatrixAndMagnificationSpec(android.os.IBinder iBinder) {
            return com.android.server.wm.WindowManagerService.this.mAccessibilityController.getWindowTransformationMatrixAndMagnificationSpec(iBinder);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void waitForAllWindowsDrawn(android.os.Message message, long j, int i) {
            boolean z;
            java.util.Objects.requireNonNull(message.getTarget());
            com.android.server.wm.WindowContainer<?> displayContent = i == -1 ? com.android.server.wm.WindowManagerService.this.mRoot : com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
            if (displayContent == null) {
                message.sendToTarget();
                return;
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    displayContent.waitForAllWindowsDrawn();
                    com.android.server.wm.WindowManagerService.this.mWindowPlacerLocked.requestTraversal();
                    com.android.server.wm.WindowManagerService.this.mH.removeMessages(24, displayContent);
                    if (displayContent.mWaitingForDrawn.isEmpty()) {
                        z = true;
                    } else {
                        if (android.os.Trace.isTagEnabled(32L)) {
                            for (int i2 = 0; i2 < displayContent.mWaitingForDrawn.size(); i2++) {
                                com.android.server.wm.WindowManagerService.this.traceStartWaitingForWindowDrawn(displayContent.mWaitingForDrawn.get(i2));
                            }
                        }
                        com.android.server.wm.WindowManagerService.this.mWaitingForDrawnCallbacks.put(displayContent, message);
                        com.android.server.wm.WindowManagerService.this.mH.sendNewMessageDelayed(24, displayContent, j);
                        com.android.server.wm.WindowManagerService.this.checkDrawnWindowsLocked();
                        z = false;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            if (z) {
                message.sendToTarget();
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setForcedDisplaySize(int i, int i2, int i3) {
            com.android.server.wm.WindowManagerService.this.setForcedDisplaySize(i, i2, i3);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void clearForcedDisplaySize(int i) {
            com.android.server.wm.WindowManagerService.this.clearForcedDisplaySize(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addWindowToken(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable android.os.Bundle bundle) {
            com.android.server.wm.WindowManagerService.this.addWindowToken(iBinder, i, i2, bundle);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeWindowToken(android.os.IBinder iBinder, boolean z, boolean z2, int i) {
            com.android.server.wm.WindowManagerService.this.removeWindowToken(iBinder, z, z2, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void moveWindowTokenToDisplay(android.os.IBinder iBinder, int i) {
            com.android.server.wm.WindowManagerService.this.moveWindowTokenToDisplay(iBinder, i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerAppTransitionListener(com.android.server.wm.WindowManagerInternal.AppTransitionListener appTransitionListener) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.getDefaultDisplayContentLocked().mAppTransition.registerListenerLocked(appTransitionListener);
                    com.android.server.wm.WindowManagerService.this.mAtmService.getTransitionController().registerLegacyListener(appTransitionListener);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerTaskSystemBarsListener(com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mTaskSystemBarsListenerController.registerListener(taskSystemBarsListener);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void unregisterTaskSystemBarsListener(com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mTaskSystemBarsListenerController.unregisterListener(taskSystemBarsListener);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerKeyguardExitAnimationStartListener(com.android.server.wm.WindowManagerInternal.KeyguardExitAnimationStartListener keyguardExitAnimationStartListener) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.getDefaultDisplayContentLocked().mAppTransition.registerKeygaurdExitAnimationStartListener(keyguardExitAnimationStartListener);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void reportPasswordChanged(int i) {
            com.android.server.wm.WindowManagerService.this.mKeyguardDisableHandler.updateKeyguardEnabled(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getInputMethodWindowVisibleHeight(int i) {
            int inputMethodWindowVisibleHeight;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    inputMethodWindowVisibleHeight = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i).getInputMethodWindowVisibleHeight();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return inputMethodWindowVisibleHeight;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setDismissImeOnBackKeyPressed(boolean z) {
            com.android.server.wm.WindowManagerService.this.mPolicy.setDismissImeOnBackKeyPressed(z);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void updateInputMethodTargetWindow(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.InputTarget inputTargetFromWindowTokenLocked = com.android.server.wm.WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder2);
                    if (inputTargetFromWindowTokenLocked != null) {
                        inputTargetFromWindowTokenLocked.getDisplayContent().updateImeInputAndControlTarget(inputTargetFromWindowTokenLocked);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isHardKeyboardAvailable() {
            boolean z;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    z = com.android.server.wm.WindowManagerService.this.mHardKeyboardAvailable;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setOnHardKeyboardStatusChangeListener(com.android.server.wm.WindowManagerInternal.OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mHardKeyboardStatusChangeListener = onHardKeyboardStatusChangeListener;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void computeWindowsForAccessibility(int i) {
            com.android.server.wm.WindowManagerService.this.mAccessibilityController.performComputeChangedWindowsNot(i, true);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setVr2dDisplayId(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mVr2dDisplayId = i;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void registerDragDropControllerCallback(com.android.server.wm.WindowManagerInternal.IDragDropCallback iDragDropCallback) {
            com.android.server.wm.WindowManagerService.this.mDragDropController.registerCallback(iDragDropCallback);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void lockNow() {
            com.android.server.wm.WindowManagerService.this.lockNow(null);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getWindowOwnerUserId(android.os.IBinder iBinder) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return com.android.server.am.ProcessList.INVALID_ADJ;
                    }
                    int i = windowState.mShowUserId;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setWallpaperShowWhenLocked(android.os.IBinder iBinder, boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowToken windowToken = com.android.server.wm.WindowManagerService.this.mRoot.getWindowToken(iBinder);
                    if (windowToken == null || windowToken.asWallpaperToken() == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 5770211341769258866L, 0, "setWallpaperShowWhenLocked: non-existent wallpaper token: %s", java.lang.String.valueOf(iBinder));
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        windowToken.asWallpaperToken().setShowWhenLocked(z);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setWallpaperCropHints(android.os.IBinder iBinder, android.util.SparseArray<android.graphics.Rect> sparseArray) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowToken windowToken = com.android.server.wm.WindowManagerService.this.mRoot.getWindowToken(iBinder);
                    if (windowToken == null || windowToken.asWallpaperToken() == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 698926505694016512L, 0, "setWallpaperCropHints: non-existent wallpaper token: %s", java.lang.String.valueOf(iBinder));
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        windowToken.asWallpaperToken().setCropHints(sparseArray);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setWallpaperCropUtils(com.android.server.wallpaper.WallpaperCropper.WallpaperCropUtils wallpaperCropUtils) {
            com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(0).mWallpaperController.setWallpaperCropUtils(wallpaperCropUtils);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isUidFocused(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    for (int childCount = com.android.server.wm.WindowManagerService.this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
                        com.android.server.wm.DisplayContent childAt = com.android.server.wm.WindowManagerService.this.mRoot.getChildAt(childCount);
                        if (childAt.mCurrentFocus != null && i == childAt.mCurrentFocus.getOwningUid()) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int hasInputMethodClientFocus(android.os.IBinder iBinder, int i, int i2, int i3) {
            if (i3 == -1) {
                return -3;
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent topFocusedDisplayContent = com.android.server.wm.WindowManagerService.this.mRoot.getTopFocusedDisplayContent();
                    com.android.server.wm.InputTarget inputTargetFromWindowTokenLocked = com.android.server.wm.WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder);
                    if (inputTargetFromWindowTokenLocked == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    int displayId = inputTargetFromWindowTokenLocked.getDisplayContent().getDisplayId();
                    if (displayId != i3) {
                        android.util.Slog.e(com.android.server.wm.WindowManagerService.TAG, "isInputMethodClientFocus: display ID mismatch. from client: " + i3 + " from window: " + displayId);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return -2;
                    }
                    if (topFocusedDisplayContent != null && topFocusedDisplayContent.getDisplayId() == i3 && topFocusedDisplayContent.hasAccess(i)) {
                        if (inputTargetFromWindowTokenLocked.isInputMethodClientFocus(i, i2)) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return 0;
                        }
                        com.android.server.wm.WindowState windowState = topFocusedDisplayContent.mCurrentFocus;
                        if (windowState == null || windowState.mSession.mUid != i || windowState.mSession.mPid != i2) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return -1;
                        }
                        int i4 = windowState.canBeImeTarget() ? 0 : -1;
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return i4;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return -3;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void showImePostLayout(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.InputTarget inputTargetFromWindowTokenLocked = com.android.server.wm.WindowManagerService.this.getInputTargetFromWindowTokenLocked(iBinder);
                    if (inputTargetFromWindowTokenLocked == null) {
                        android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 20);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 20);
                    android.os.Trace.asyncTraceBegin(32L, "WMS.showImePostLayout", 0);
                    com.android.server.wm.InsetsControlTarget imeControlTarget = inputTargetFromWindowTokenLocked.getImeControlTarget();
                    com.android.server.wm.WindowState window = imeControlTarget.getWindow();
                    (window != null ? window.getDisplayContent() : com.android.server.wm.WindowManagerService.this.getDefaultDisplayContentLocked()).getInsetsStateController().getImeSourceProvider().scheduleShowImePostLayout(imeControlTarget, token);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void hideIme(android.os.IBinder iBinder, int i, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token) {
            android.os.Trace.traceBegin(32L, "WMS.hideIme");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -7428028317216329062L, 0, null, java.lang.String.valueOf(windowState));
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (windowState != null) {
                        com.android.server.wm.WindowState window = windowState.getImeControlTarget().getWindow();
                        if (window != null) {
                            displayContent = window.getDisplayContent();
                        }
                        displayContent.getInsetsStateController().getImeSourceProvider().abortShowImePostLayout();
                    }
                    if (displayContent == null || displayContent.getImeTarget(2) == null) {
                        android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 20);
                    } else {
                        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 20);
                        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 1006302987953651112L, 0, null, java.lang.String.valueOf(displayContent.getImeTarget(2)));
                        displayContent.getImeTarget(2).hideInsets(android.view.WindowInsets.Type.ime(), true, token);
                    }
                    if (displayContent != null) {
                        displayContent.getInsetsStateController().getImeSourceProvider().setImeShowing(false);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            android.os.Trace.traceEnd(32L);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isUidAllowedOnDisplay(int i, int i2) {
            boolean z = true;
            if (i == 0) {
                return true;
            }
            if (i == -1) {
                return false;
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null || !displayContent.hasAccess(i2)) {
                        z = false;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getDisplayIdForWindow(android.os.IBinder iBinder) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    int displayId = windowState.getDisplayContent().getDisplayId();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return displayId;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getTopFocusedDisplayId() {
            int displayId;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    displayId = com.android.server.wm.WindowManagerService.this.mRoot.getTopFocusedDisplayContent().getDisplayId();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return displayId;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public android.content.Context getTopFocusedDisplayUiContext() {
            android.content.Context displayUiContext;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    displayUiContext = com.android.server.wm.WindowManagerService.this.mRoot.getTopFocusedDisplayContent().getDisplayUiContext();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return displayUiContext;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setHomeSupportedOnDisplay(java.lang.String str, int i, boolean z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        com.android.server.wm.WindowManagerService.this.mDisplayWindowSettings.setHomeSupportedOnDisplayLocked(str, i, z);
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isHomeSupportedOnDisplay(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 5213970642134448962L, 1, "Attempted to get home support flag of a display that does not exist: %d", java.lang.Long.valueOf(i));
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean isHomeSupported = displayContent.isHomeSupported();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return isHomeSupported;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void clearDisplaySettings(java.lang.String str, int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        com.android.server.wm.WindowManagerService.this.mDisplayWindowSettings.clearDisplaySettings(str, i);
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public int getDisplayImePolicy(int i) {
            return com.android.server.wm.WindowManagerService.this.getDisplayImePolicy(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addRefreshRateRangeForPackage(@android.annotation.NonNull final java.lang.String str, final float f, final float f2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.WindowManagerService.LocalService.lambda$addRefreshRateRangeForPackage$1(str, f, f2, (com.android.server.wm.DisplayContent) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$addRefreshRateRangeForPackage$1(java.lang.String str, float f, float f2, com.android.server.wm.DisplayContent displayContent) {
            displayContent.getDisplayPolicy().getRefreshRatePolicy().addRefreshRateRangeForPackage(str, f, f2);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeRefreshRateRangeForPackage(@android.annotation.NonNull final java.lang.String str) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$LocalService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.WindowManagerService.LocalService.lambda$removeRefreshRateRangeForPackage$2(str, (com.android.server.wm.DisplayContent) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$removeRefreshRateRangeForPackage$2(java.lang.String str, com.android.server.wm.DisplayContent displayContent) {
            displayContent.getDisplayPolicy().getRefreshRatePolicy().removeRefreshRateRangeForPackage(str);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isTouchOrFaketouchDevice() {
            boolean z;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.WindowManagerService.this.mIsTouchDevice && !com.android.server.wm.WindowManagerService.this.mIsFakeTouchDevice) {
                        throw new java.lang.IllegalStateException("touchscreen supported device must report faketouch.");
                    }
                    z = com.android.server.wm.WindowManagerService.this.mIsFakeTouchDevice;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        @android.annotation.Nullable
        public com.android.internal.policy.KeyInterceptionInfo getKeyInterceptionInfoFromToken(android.os.IBinder iBinder) {
            return com.android.server.wm.WindowManagerService.this.mKeyInterceptionInfoForToken.get(iBinder);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setAccessibilityIdToSurfaceMetadata(android.os.IBinder iBinder, int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        android.util.Slog.w(com.android.server.wm.WindowManagerService.TAG, "Cannot find window which accessibility connection is added to");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        com.android.server.wm.WindowManagerService.this.mTransaction.setMetadata(windowState.mSurfaceControl, 5, i).apply();
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public java.lang.String getWindowName(@android.annotation.NonNull android.os.IBinder iBinder) {
            java.lang.String name;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    name = windowState != null ? windowState.getName() : null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return name;
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public com.android.server.wm.WindowManagerInternal.ImeTargetInfo onToggleImeRequested(boolean z, android.os.IBinder iBinder, android.os.IBinder iBinder2, int i) {
            java.lang.String name;
            java.lang.String name2;
            java.lang.String str;
            java.lang.String str2;
            java.lang.String str3;
            java.lang.String str4;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    name = windowState != null ? windowState.getName() : "null";
                    com.android.server.wm.WindowState windowState2 = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder2);
                    name2 = windowState2 != null ? windowState2.getName() : "null";
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        com.android.server.wm.InsetsControlTarget imeTarget = displayContent.getImeTarget(2);
                        if (imeTarget != null) {
                            com.android.server.wm.WindowState asWindowOrNull = com.android.server.wm.InsetsControlTarget.asWindowOrNull(imeTarget);
                            str4 = asWindowOrNull != null ? asWindowOrNull.getName() : imeTarget.toString();
                        } else {
                            str4 = "null";
                        }
                        com.android.server.wm.InsetsControlTarget imeTarget2 = displayContent.getImeTarget(0);
                        java.lang.String name3 = imeTarget2 != null ? imeTarget2.getWindow().getName() : "null";
                        android.view.SurfaceControl surfaceControl = displayContent.mInputMethodSurfaceParent;
                        java.lang.String surfaceControl2 = surfaceControl != null ? surfaceControl.toString() : "null";
                        if (z) {
                            displayContent.onShowImeRequested();
                        }
                        str = str4;
                        str2 = name3;
                        str3 = surfaceControl2;
                    } else {
                        str = "no-display";
                        str2 = str;
                        str3 = str2;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return new com.android.server.wm.WindowManagerInternal.ImeTargetInfo(name, name2, str, str2, str3);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean shouldRestoreImeVisibility(android.os.IBinder iBinder) {
            return com.android.server.wm.WindowManagerService.this.shouldRestoreImeVisibility(iBinder);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addTrustedTaskOverlay(int i, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
            if (surfacePackage == null) {
                throw new java.lang.IllegalArgumentException("Invalid overlay passed in for task=" + i);
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (surfacePackage.getSurfaceControl() == null || !surfacePackage.getSurfaceControl().isValid()) {
                        throw new java.lang.IllegalArgumentException("Invalid overlay surfacecontrol passed in for task=" + i);
                    }
                    com.android.server.wm.Task rootTask = com.android.server.wm.WindowManagerService.this.mRoot.getRootTask(i);
                    if (rootTask == null) {
                        throw new java.lang.IllegalArgumentException("no task with taskId" + i);
                    }
                    rootTask.addTrustedOverlay(surfacePackage, rootTask.getTopVisibleAppMainWindow());
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeTrustedTaskOverlay(int i, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) {
            if (surfacePackage == null) {
                throw new java.lang.IllegalArgumentException("Invalid overlay passed in for task=" + i);
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (surfacePackage.getSurfaceControl() == null || !surfacePackage.getSurfaceControl().isValid()) {
                        throw new java.lang.IllegalArgumentException("Invalid overlay surfacecontrol passed in for task=" + i);
                    }
                    com.android.server.wm.Task rootTask = com.android.server.wm.WindowManagerService.this.mRoot.getRootTask(i);
                    if (rootTask == null) {
                        throw new java.lang.IllegalArgumentException("no task with taskId" + i);
                    }
                    rootTask.removeTrustedOverlay(surfacePackage);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public android.view.SurfaceControl getHandwritingSurfaceForDisplay(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        android.util.Slog.e(com.android.server.wm.WindowManagerService.TAG, "Failed to create a handwriting surface on display: " + i + " - DisplayContent not found.");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    android.view.SurfaceControl inputOverlayLayer = displayContent.getInputOverlayLayer();
                    if (inputOverlayLayer == null) {
                        android.util.Slog.e(com.android.server.wm.WindowManagerService.TAG, "Failed to create a gesture monitor on display: " + i + " - Input overlay layer is not initialized.");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    android.view.SurfaceControl build = com.android.server.wm.WindowManagerService.this.makeSurfaceBuilder(displayContent.getSession()).setContainerLayer().setName("IME Handwriting Surface").setCallsite("getHandwritingSurfaceForDisplay").setParent(inputOverlayLayer).build();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return build;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean isPointInsideWindow(@android.annotation.NonNull android.os.IBinder iBinder, int i, float f, float f2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null || windowState.getDisplayId() != i) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean contains = windowState.getBounds().contains((int) f, (int) f2);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return contains;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean setContentRecordingSession(@android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (contentRecordingSession != null) {
                    try {
                        if (contentRecordingSession.getContentToRecord() == 1) {
                            com.android.server.wm.WindowManagerService.WindowContainerInfo taskWindowContainerInfoForLaunchCookie = com.android.server.wm.WindowManagerService.this.getTaskWindowContainerInfoForLaunchCookie(contentRecordingSession.getTokenToRecord());
                            if (taskWindowContainerInfoForLaunchCookie == null) {
                                android.util.Slog.w(com.android.server.wm.WindowManagerService.TAG, "Handling a new recording session; unable to find the WindowContainerToken");
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                            contentRecordingSession.setTokenToRecord(taskWindowContainerInfoForLaunchCookie.getToken().asBinder());
                            contentRecordingSession.setTargetUid(taskWindowContainerInfoForLaunchCookie.getUid());
                            com.android.server.wm.WindowManagerService.this.mContentRecordingController.setContentRecordingSessionLocked(contentRecordingSession, com.android.server.wm.WindowManagerService.this);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.this.mContentRecordingController.setContentRecordingSessionLocked(contentRecordingSession, com.android.server.wm.WindowManagerService.this);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return true;
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public android.view.SurfaceControl getA11yOverlayLayer(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent != null) {
                        android.view.SurfaceControl a11yOverlayLayer = displayContent.getA11yOverlayLayer();
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return a11yOverlayLayer;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void captureDisplay(int i, @android.annotation.Nullable android.window.ScreenCapture.CaptureArgs captureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) {
            com.android.server.wm.WindowManagerService.this.captureDisplay(i, captureArgs, screenCaptureListener);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean hasNavigationBar(int i) {
            return com.android.server.wm.WindowManagerService.this.hasNavigationBar(i);
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setInputMethodTargetChangeListener(@android.annotation.NonNull com.android.server.wm.ImeTargetChangeListener imeTargetChangeListener) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.mImeTargetChangeListener = imeTargetChangeListener;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService.this.setOrientationRequestPolicy(z, iArr, iArr2);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        @android.annotation.Nullable
        public android.os.IBinder getTargetWindowTokenFromInputToken(android.os.IBinder iBinder) {
            com.android.server.wm.InputTarget inputTargetFromToken = com.android.server.wm.WindowManagerService.this.getInputTargetFromToken(iBinder);
            if (inputTargetFromToken == null) {
                return null;
            }
            return inputTargetFromToken.getWindowToken();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void addBlockScreenCaptureForApps(android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.WindowManagerService.this.mSensitiveContentPackages.addBlockScreenCaptureForApps(arraySet)) {
                        com.android.server.wm.WindowManagerService.this.refreshScreenCaptureDisabled();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void removeBlockScreenCaptureForApps(android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.WindowManagerService.this.mSensitiveContentPackages.removeBlockScreenCaptureForApps(arraySet)) {
                        com.android.server.wm.WindowManagerService.this.refreshScreenCaptureDisabled();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public void clearBlockedApps() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.WindowManagerService.this.mSensitiveContentPackages.clearBlockedApps()) {
                        com.android.server.wm.WindowManagerService.this.refreshScreenCaptureDisabled();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public boolean moveFocusToTopEmbeddedWindowIfNeeded() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState focusedWindow = com.android.server.wm.WindowManagerService.this.getFocusedWindow();
                    if (focusedWindow == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (com.android.server.wm.WindowManagerService.this.moveFocusToTopEmbeddedWindow(focusedWindow)) {
                        com.android.server.wm.WindowManagerService.this.syncInputTransactions(false);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal
        public android.window.ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot() {
            return com.android.server.wm.WindowManagerService.this.takeAssistScreenshot();
        }
    }

    private final class ImeTargetVisibilityPolicyImpl extends com.android.server.wm.ImeTargetVisibilityPolicy {
        private ImeTargetVisibilityPolicyImpl() {
        }

        @Override // com.android.server.wm.ImeTargetVisibilityPolicy
        public boolean showImeScreenshot(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = com.android.server.wm.WindowManagerService.this.mWindowMap.get(iBinder);
                    if (windowState == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        android.util.Slog.w(com.android.server.wm.WindowManagerService.TAG, "Invalid displayId:" + i + ", fail to show ime screenshot");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    displayContent.showImeScreenshot(windowState);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ImeTargetVisibilityPolicy
        public boolean removeImeScreenshot(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = com.android.server.wm.WindowManagerService.this.mRoot.getDisplayContent(i);
                    if (displayContent == null) {
                        android.util.Slog.w(com.android.server.wm.WindowManagerService.TAG, "Invalid displayId:" + i + ", fail to remove ime screenshot");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    displayContent.removeImeSurfaceImmediately();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    void registerAppFreezeListener(com.android.server.wm.WindowManagerService.AppFreezeListener appFreezeListener) {
        if (!this.mAppFreezeListeners.contains(appFreezeListener)) {
            this.mAppFreezeListeners.add(appFreezeListener);
        }
    }

    void unregisterAppFreezeListener(com.android.server.wm.WindowManagerService.AppFreezeListener appFreezeListener) {
        this.mAppFreezeListeners.remove(appFreezeListener);
    }

    public void disableNonVrUi(boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            boolean z2 = !z;
            try {
                if (z2 == this.mShowAlertWindowNotifications) {
                    resetPriorityAfterLockedSection();
                    return;
                }
                this.mShowAlertWindowNotifications = z2;
                for (int size = this.mSessions.size() - 1; size >= 0; size--) {
                    this.mSessions.valueAt(size).setShowingAlertWindowNotificationAllowed(this.mShowAlertWindowNotifications);
                }
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    boolean hasWideColorGamutSupport() {
        return this.mHasWideColorGamutSupport && android.os.SystemProperties.getInt("persist.sys.sf.native_mode", 0) != 1;
    }

    boolean hasHdrSupport() {
        return this.mHasHdrSupport && hasWideColorGamutSupport();
    }

    void updateNonSystemOverlayWindowsVisibilityIfNeeded(com.android.server.wm.WindowState windowState, boolean z) {
        if (!windowState.hideNonSystemOverlayWindowsWhenVisible() && !this.mHidingNonSystemOverlayWindows.contains(windowState)) {
            return;
        }
        boolean z2 = !this.mHidingNonSystemOverlayWindows.isEmpty();
        if (z && windowState.hideNonSystemOverlayWindowsWhenVisible()) {
            if (!this.mHidingNonSystemOverlayWindows.contains(windowState)) {
                this.mHidingNonSystemOverlayWindows.add(windowState);
            }
        } else {
            this.mHidingNonSystemOverlayWindows.remove(windowState);
        }
        final boolean z3 = !this.mHidingNonSystemOverlayWindows.isEmpty();
        if (z2 == z3) {
            return;
        }
        this.mRoot.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda32
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowState) obj).setForceHideNonSystemOverlayWindowIfNeeded(z3);
            }
        }, false);
    }

    public void applyMagnificationSpecLocked(int i, android.view.MagnificationSpec magnificationSpec) {
        com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (displayContent != null) {
            displayContent.applyMagnificationSpec(magnificationSpec);
        }
    }

    android.view.SurfaceControl.Builder makeSurfaceBuilder(android.view.SurfaceSession surfaceSession) {
        return this.mSurfaceControlFactory.apply(surfaceSession);
    }

    void onLockTaskStateChanged(final int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRoot.forAllDisplayPolicies(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wm.DisplayPolicy) obj).onLockTaskStateChangedLw(i);
                    }
                });
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public void syncInputTransactions(boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (z) {
                try {
                    waitForAnimationsToComplete();
                } catch (java.lang.InterruptedException e) {
                    android.util.Slog.e(TAG, "Exception thrown while waiting for window infos to be reported", e);
                }
            }
            final android.view.SurfaceControl.Transaction transaction = this.mTransactionFactory.get();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mWindowPlacerLocked.performSurfacePlacementIfScheduled();
                    this.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda20
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.WindowManagerService.lambda$syncInputTransactions$23(transaction, (com.android.server.wm.DisplayContent) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            java.util.Objects.requireNonNull(countDownLatch);
            transaction.addWindowInfosReportedListener(new com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda0(countDownLatch)).apply();
            countDownLatch.await(5000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$syncInputTransactions$23(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.DisplayContent displayContent) {
        displayContent.getInputMonitor().updateInputWindowsImmediately(transaction);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:7|(1:46)(1:11)|12|(2:14|(1:44)(2:17|(5:36|37|38|40|41)(2:21|22)))|45|(1:19)|36|37|38|40|41|5) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void waitForAnimationsToComplete() {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAnimator.mNotifyWhenNoAnimation = true;
                int i = 0;
                long j = 5000;
                boolean z2 = false;
                while (j > 0) {
                    z2 = !this.mAtmService.getTransitionController().isShellTransitionsEnabled() && this.mRoot.forAllActivities(new com.android.server.wm.Transition$ChangeInfo$$ExternalSyntheticLambda0());
                    if (!this.mAnimator.isAnimationScheduled()) {
                        if (!this.mRoot.isAnimating(5, -1) && !z2) {
                            z = false;
                            if (z && !this.mAtmService.getTransitionController().inTransition()) {
                                break;
                            }
                            long currentTimeMillis = java.lang.System.currentTimeMillis();
                            this.mGlobalLock.wait(j);
                            j -= java.lang.System.currentTimeMillis() - currentTimeMillis;
                        }
                    }
                    z = true;
                    if (z) {
                    }
                    long currentTimeMillis2 = java.lang.System.currentTimeMillis();
                    this.mGlobalLock.wait(j);
                    j -= java.lang.System.currentTimeMillis() - currentTimeMillis2;
                }
                this.mAnimator.mNotifyWhenNoAnimation = false;
                com.android.server.wm.WindowContainer animatingContainer = this.mRoot.getAnimatingContainer(5, -1);
                if (this.mAnimator.isAnimationScheduled() || animatingContainer != null || z2) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Timed out waiting for animations to complete, animatingContainer=");
                    sb.append(animatingContainer);
                    sb.append(" animationType=");
                    if (animatingContainer != null) {
                        i = animatingContainer.mSurfaceAnimator.getAnimationType();
                    }
                    sb.append(com.android.server.wm.SurfaceAnimator.animationTypeToString(i));
                    sb.append(" animateStarting=");
                    sb.append(z2);
                    android.util.Slog.w(TAG, sb.toString());
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    void onAnimationFinished() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mGlobalLock.notifyAll();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPointerDownOutsideFocusLocked(com.android.server.wm.InputTarget inputTarget) {
        com.android.server.wm.Task task;
        if (inputTarget == null || !inputTarget.receiveFocusFromTapOutside()) {
            return;
        }
        if (this.mRecentsAnimationController != null && this.mRecentsAnimationController.getTargetAppMainWindow() == inputTarget) {
            return;
        }
        com.android.server.wm.WindowState windowState = inputTarget.getWindowState();
        if (windowState != null && (task = windowState.getTask()) != null && windowState.mTransitionController.isTransientHide(task)) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -2065144681579661392L, 0, null, java.lang.String.valueOf(inputTarget));
        if (this.mFocusedInputTarget != inputTarget && this.mFocusedInputTarget != null) {
            this.mFocusedInputTarget.handleTapOutsideFocusOutsideSelf();
        }
        this.mAtmService.mTaskSupervisor.mUserLeaving = true;
        inputTarget.handleTapOutsideFocusInsideSelf();
        this.mAtmService.mTaskSupervisor.mUserLeaving = false;
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleTaskFocusChange(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord) {
        if (task == null) {
            return;
        }
        if (task.isActivityTypeHome()) {
            com.android.server.wm.TaskDisplayArea displayArea = task.getDisplayArea();
            com.android.server.wm.WindowState focusedWindow = getFocusedWindow();
            if (focusedWindow != null && displayArea != null && focusedWindow.isDescendantOf(displayArea)) {
                return;
            }
        }
        this.mAtmService.setFocusedTask(task.mTaskId, activityRecord);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class WindowContainerInfo {

        @android.annotation.NonNull
        private final android.window.WindowContainerToken mToken;
        private final int mUid;

        private WindowContainerInfo(int i, @android.annotation.NonNull android.window.WindowContainerToken windowContainerToken) {
            this.mUid = i;
            this.mToken = windowContainerToken;
        }

        public int getUid() {
            return this.mUid;
        }

        @android.annotation.NonNull
        public android.window.WindowContainerToken getToken() {
            return this.mToken;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.wm.WindowManagerService.WindowContainerInfo getTaskWindowContainerInfoForLaunchCookie(@android.annotation.NonNull final android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord activity = this.mRoot.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTaskWindowContainerInfoForLaunchCookie$24;
                lambda$getTaskWindowContainerInfoForLaunchCookie$24 = com.android.server.wm.WindowManagerService.lambda$getTaskWindowContainerInfoForLaunchCookie$24(iBinder, (com.android.server.wm.ActivityRecord) obj);
                return lambda$getTaskWindowContainerInfoForLaunchCookie$24;
            }
        });
        if (activity == null) {
            android.util.Slog.w(TAG, "Unable to find the activity for this launch cookie");
            return null;
        }
        if (activity.getTask() == null) {
            android.util.Slog.w(TAG, "Unable to find the task for this launch cookie");
            return null;
        }
        android.window.WindowContainerToken windowContainerToken = activity.getTask().mRemoteToken.toWindowContainerToken();
        if (windowContainerToken == null) {
            android.util.Slog.w(TAG, "Unable to find the WindowContainerToken for " + activity.getName());
            return null;
        }
        return new com.android.server.wm.WindowManagerService.WindowContainerInfo(activity.getUid(), windowContainerToken);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTaskWindowContainerInfoForLaunchCookie$24(android.os.IBinder iBinder, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.mLaunchCookie == iBinder;
    }

    private int sanitizeFlagSlippery(int i, java.lang.String str, int i2, int i3) {
        if ((536870912 & i) == 0) {
            return i;
        }
        if (this.mContext.checkPermission("android.permission.ALLOW_SLIPPERY_TOUCHES", i3, i2) != 0) {
            android.util.Slog.w(TAG, "Removing FLAG_SLIPPERY from '" + str + "' because it doesn't have ALLOW_SLIPPERY_TOUCHES permission");
            return i & (-536870913);
        }
        return i;
    }

    private int sanitizeSpyWindow(int i, java.lang.String str, int i2, int i3) {
        if ((i & 4) == 0) {
            return i;
        }
        if (this.mContext.checkPermission("android.permission.MONITOR_INPUT", i3, i2) != 0) {
            throw new java.lang.IllegalArgumentException("Cannot use INPUT_FEATURE_SPY from '" + str + "' because it doesn't the have MONITOR_INPUT permission");
        }
        return i;
    }

    void grantInputChannel(com.android.server.wm.Session session, int i, int i2, int i3, android.view.SurfaceControl surfaceControl, android.os.IBinder iBinder, @android.annotation.Nullable android.window.InputTransferToken inputTransferToken, int i4, int i5, int i6, int i7, android.os.IBinder iBinder2, android.window.InputTransferToken inputTransferToken2, java.lang.String str, android.view.InputChannel inputChannel) {
        com.android.server.wm.WindowState windowState;
        android.view.InputApplicationHandle applicationHandle;
        java.lang.String embeddedWindow;
        int sanitizeWindowType = sanitizeWindowType(session, i3, iBinder2, i7);
        java.util.Objects.requireNonNull(inputChannel);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (inputTransferToken != null) {
                try {
                    windowState = this.mInputToWindowMap.get(inputTransferToken.mToken);
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            } else {
                windowState = null;
            }
            com.android.server.wm.EmbeddedWindowController.EmbeddedWindow embeddedWindow2 = new com.android.server.wm.EmbeddedWindowController.EmbeddedWindow(session, this, iBinder, windowState, i, i2, sanitizeWindowType, i3, inputTransferToken2, str, (i4 & 8) == 0);
            embeddedWindow2.openInputChannel(inputChannel);
            this.mEmbeddedWindowController.add(inputChannel.getToken(), embeddedWindow2);
            applicationHandle = embeddedWindow2.getApplicationHandle();
            embeddedWindow = embeddedWindow2.toString();
        }
        resetPriorityAfterLockedSection();
        updateInputChannel(inputChannel.getToken(), i, i2, i3, surfaceControl, embeddedWindow, applicationHandle, i4, i5, i6, sanitizeWindowType, null, iBinder);
    }

    public boolean transferTouchGesture(@android.annotation.NonNull android.window.InputTransferToken inputTransferToken, @android.annotation.NonNull android.window.InputTransferToken inputTransferToken2) {
        boolean transferToEmbedded;
        java.util.Objects.requireNonNull(inputTransferToken);
        java.util.Objects.requireNonNull(inputTransferToken2);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState windowState = this.mInputToWindowMap.get(inputTransferToken2.mToken);
                    if (windowState != null) {
                        transferToEmbedded = this.mEmbeddedWindowController.transferToHost(inputTransferToken, windowState);
                    } else {
                        transferToEmbedded = this.mEmbeddedWindowController.transferToEmbedded(this.mInputToWindowMap.get(inputTransferToken.mToken), inputTransferToken2);
                    }
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return transferToEmbedded;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void updateInputChannel(android.os.IBinder iBinder, int i, int i2, int i3, android.view.SurfaceControl surfaceControl, java.lang.String str, android.view.InputApplicationHandle inputApplicationHandle, int i4, int i5, int i6, int i7, android.graphics.Region region, android.os.IBinder iBinder2) {
        android.view.InputWindowHandle inputWindowHandle = new android.view.InputWindowHandle(inputApplicationHandle, i3);
        inputWindowHandle.token = iBinder;
        inputWindowHandle.setWindowToken(iBinder2);
        inputWindowHandle.name = str;
        int sanitizeFlagSlippery = sanitizeFlagSlippery(i4, str, i, i2);
        int sanitizeSpyWindow = sanitizeSpyWindow(i6, str, i, i2);
        int i8 = (536870936 & sanitizeFlagSlippery) | 32;
        inputWindowHandle.layoutParamsType = i7;
        inputWindowHandle.layoutParamsFlags = i8;
        inputWindowHandle.inputConfig = com.android.server.wm.InputConfigAdapter.getInputConfigFromWindowParams(i7, i8, sanitizeSpyWindow);
        if ((sanitizeFlagSlippery & 8) != 0) {
            inputWindowHandle.inputConfig |= 4;
        }
        inputWindowHandle.dispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        inputWindowHandle.ownerUid = i;
        inputWindowHandle.ownerPid = i2;
        if (region == null) {
            inputWindowHandle.replaceTouchableRegionWithCrop((android.view.SurfaceControl) null);
        } else {
            inputWindowHandle.touchableRegion.set(region);
            inputWindowHandle.replaceTouchableRegionWithCrop = false;
            if (this.mContext.checkPermission("android.permission.MANAGE_ACTIVITY_TASKS", i2, i) != 0) {
                inputWindowHandle.setTouchableRegionCrop(surfaceControl);
            }
        }
        android.view.SurfaceControl.Transaction transaction = this.mTransactionFactory.get();
        inputWindowHandle.setTrustedOverlay(transaction, surfaceControl, (536870912 & i5) != 0);
        transaction.setInputWindowInfo(surfaceControl, inputWindowHandle);
        transaction.apply();
        transaction.close();
        surfaceControl.release();
    }

    void updateInputChannel(android.os.IBinder iBinder, int i, android.view.SurfaceControl surfaceControl, int i2, int i3, int i4, android.graphics.Region region) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.EmbeddedWindowController.EmbeddedWindow embeddedWindow = this.mEmbeddedWindowController.get(iBinder);
                if (embeddedWindow == null) {
                    android.util.Slog.e(TAG, "Couldn't find window for provided channelToken.");
                    resetPriorityAfterLockedSection();
                    return;
                }
                java.lang.String embeddedWindow2 = embeddedWindow.toString();
                android.view.InputApplicationHandle applicationHandle = embeddedWindow.getApplicationHandle();
                embeddedWindow.setIsFocusable((i2 & 8) == 0);
                resetPriorityAfterLockedSection();
                updateInputChannel(iBinder, embeddedWindow.mOwnerUid, embeddedWindow.mOwnerPid, i, surfaceControl, embeddedWindow2, applicationHandle, i2, i3, i4, embeddedWindow.mWindowType, region, embeddedWindow.mClient);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    boolean moveFocusToTopEmbeddedWindow(@android.annotation.NonNull com.android.server.wm.WindowState windowState) {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.TaskFragment taskFragment = windowState.getTaskFragment();
        if (taskFragment == null || !com.android.window.flags.Flags.embeddedActivityBackNavFlag() || (activityRecord = taskFragment.getTask().topRunningActivity(true)) == null || activityRecord == windowState.mActivityRecord || !activityRecord.isEmbedded()) {
            return false;
        }
        com.android.server.wm.TaskFragment taskFragment2 = activityRecord.getTaskFragment();
        if (taskFragment2.isIsolatedNav() && taskFragment.getAdjacentTaskFragment() == taskFragment2) {
            return false;
        }
        moveFocusToActivity(activityRecord);
        return !windowState.isFocused();
    }

    boolean moveFocusToAdjacentWindow(@android.annotation.NonNull com.android.server.wm.WindowState windowState, int i) {
        com.android.server.wm.TaskFragment taskFragment;
        com.android.server.wm.TaskFragment adjacentTaskFragment;
        if (!windowState.isFocused() || (taskFragment = windowState.getTaskFragment()) == null || (adjacentTaskFragment = taskFragment.getAdjacentTaskFragment()) == null || adjacentTaskFragment.asTask() != null || adjacentTaskFragment.isIsolatedNav()) {
            return false;
        }
        android.graphics.Rect bounds = taskFragment.getBounds();
        android.graphics.Rect bounds2 = adjacentTaskFragment.getBounds();
        switch (i) {
            case 1:
            case 2:
                break;
            case 17:
                if (bounds2.left >= bounds.left) {
                    return false;
                }
                break;
            case 33:
                if (bounds2.top >= bounds.top) {
                    return false;
                }
                break;
            case 66:
                if (bounds2.right <= bounds.right) {
                    return false;
                }
                break;
            case 130:
                if (bounds2.bottom <= bounds.bottom) {
                    return false;
                }
                break;
            default:
                return false;
        }
        com.android.server.wm.ActivityRecord activityRecord = adjacentTaskFragment.topRunningActivity(true);
        if (activityRecord == null) {
            return false;
        }
        moveFocusToActivity(activityRecord);
        return !windowState.isFocused();
    }

    @com.android.internal.annotations.VisibleForTesting
    void moveFocusToActivity(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        moveDisplayToTopInternal(activityRecord.getDisplayId());
        handleTaskFocusChange(activityRecord.getTask(), activityRecord);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0063 A[Catch: all -> 0x003c, TRY_ENTER, TryCatch #2 {all -> 0x003c, blocks: (B:10:0x0031, B:24:0x0063, B:26:0x0068, B:27:0x006b, B:18:0x0054, B:20:0x0059), top: B:4:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0068 A[Catch: all -> 0x003c, TryCatch #2 {all -> 0x003c, blocks: (B:10:0x0031, B:24:0x0063, B:26:0x0068, B:27:0x006b, B:18:0x0054, B:20:0x0059), top: B:4:0x0012 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isLayerTracing() {
        android.os.Parcel parcel;
        if (!checkCallingPermission("android.permission.DUMP", "isLayerTracing()")) {
            throw new java.lang.SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.os.Parcel parcel2 = null;
        try {
            try {
                android.os.IBinder service = android.os.ServiceManager.getService("SurfaceFlinger");
                if (service != null) {
                    parcel = android.os.Parcel.obtain();
                    try {
                        try {
                            parcel2 = android.os.Parcel.obtain();
                            parcel2.writeInterfaceToken("android.ui.ISurfaceComposer");
                            service.transact(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_HEADSET, parcel2, parcel, 0);
                            boolean readBoolean = parcel.readBoolean();
                            parcel2.recycle();
                            parcel.recycle();
                            return readBoolean;
                        } catch (java.lang.Throwable th) {
                            th = th;
                            if (parcel2 != null) {
                                parcel2.recycle();
                            }
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            throw th;
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "Failed to get layer tracing");
                        if (parcel2 != null) {
                            parcel2.recycle();
                        }
                        if (parcel != null) {
                            parcel.recycle();
                        }
                        return false;
                    }
                }
            } catch (android.os.RemoteException e2) {
                parcel = null;
            } catch (java.lang.Throwable th2) {
                th = th2;
                parcel = null;
                if (parcel2 != null) {
                }
                if (parcel != null) {
                }
                throw th;
            }
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004b, code lost:
    
        if (r2 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0052, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003d, code lost:
    
        r2.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x003b, code lost:
    
        if (r2 != null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setLayerTracing(boolean z) {
        if (!checkCallingPermission("android.permission.DUMP", "setLayerTracing()")) {
            throw new java.lang.SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.os.Parcel parcel = null;
        try {
            try {
                try {
                    android.os.IBinder service = android.os.ServiceManager.getService("SurfaceFlinger");
                    if (service != null) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                            obtain.writeInt(z ? 1 : 0);
                            service.transact(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_HANDSET, obtain, null, 0);
                            parcel = obtain;
                        } catch (android.os.RemoteException e) {
                            parcel = obtain;
                            android.util.Slog.e(TAG, "Failed to set layer tracing");
                        } catch (java.lang.Throwable th) {
                            th = th;
                            parcel = obtain;
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            throw th;
                        }
                    }
                } catch (android.os.RemoteException e2) {
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0046, code lost:
    
        if (r2 == null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004d, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0038, code lost:
    
        r2.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0036, code lost:
    
        if (r2 != null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setLayerTracingFlags(int i) {
        if (!checkCallingPermission("android.permission.DUMP", "setLayerTracingFlags")) {
            throw new java.lang.SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.os.Parcel parcel = null;
        try {
            try {
                try {
                    android.os.IBinder service = android.os.ServiceManager.getService("SurfaceFlinger");
                    if (service != null) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                            obtain.writeInt(i);
                            service.transact(1033, obtain, null, 0);
                            parcel = obtain;
                        } catch (android.os.RemoteException e) {
                            parcel = obtain;
                            android.util.Slog.e(TAG, "Failed to set layer tracing flags");
                        } catch (java.lang.Throwable th) {
                            th = th;
                            parcel = obtain;
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            throw th;
                        }
                    }
                } catch (android.os.RemoteException e2) {
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004b, code lost:
    
        if (r2 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0052, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003d, code lost:
    
        r2.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x003b, code lost:
    
        if (r2 != null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setActiveTransactionTracing(boolean z) {
        if (!checkCallingPermission("android.permission.DUMP", "setActiveTransactionTracing()")) {
            throw new java.lang.SecurityException("Requires DUMP permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.os.Parcel parcel = null;
        try {
            try {
                try {
                    android.os.IBinder service = android.os.ServiceManager.getService("SurfaceFlinger");
                    if (service != null) {
                        android.os.Parcel obtain = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                            obtain.writeInt(z ? 1 : 0);
                            service.transact(1041, obtain, null, 0);
                            parcel = obtain;
                        } catch (android.os.RemoteException e) {
                            parcel = obtain;
                            android.util.Slog.e(TAG, "Failed to set transaction tracing");
                        } catch (java.lang.Throwable th) {
                            th = th;
                            parcel = obtain;
                            if (parcel != null) {
                                parcel.recycle();
                            }
                            throw th;
                        }
                    }
                } catch (android.os.RemoteException e2) {
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public boolean mirrorDisplay(int i, android.view.SurfaceControl surfaceControl) {
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "mirrorDisplay()")) {
            throw new java.lang.SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.e(TAG, "Invalid displayId " + i + " for mirrorDisplay");
                    resetPriorityAfterLockedSection();
                    return false;
                }
                android.view.SurfaceControl windowingLayer = displayContent.getWindowingLayer();
                resetPriorityAfterLockedSection();
                android.view.SurfaceControl mirrorSurface = android.view.SurfaceControl.mirrorSurface(windowingLayer);
                surfaceControl.copyFrom(mirrorSurface, "WMS.mirrorDisplay");
                mirrorSurface.release();
                return true;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean getWindowInsets(int i, android.os.IBinder iBinder, android.view.InsetsState insetsState) {
        boolean areSystemBarsForcedConsumedLw;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContentOrCreate = getDisplayContentOrCreate(i, iBinder);
                    if (displayContentOrCreate == null) {
                        throw new android.view.WindowManager.InvalidDisplayException("Display#" + i + "could not be found!");
                    }
                    displayContentOrCreate.getInsetsPolicy().getInsetsForWindowMetrics(displayContentOrCreate.getWindowToken(iBinder), insetsState);
                    areSystemBarsForcedConsumedLw = displayContentOrCreate.getDisplayPolicy().areSystemBarsForcedConsumedLw();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            return areSystemBarsForcedConsumedLw;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.view.DisplayInfo> getPossibleDisplayInfo(int i) {
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!this.mAtmService.isCallerRecents(callingUid) && (!com.android.window.flags.Flags.multiCrop() || callingUid != 1000)) {
                        android.util.Slog.e(TAG, "Unable to verify uid for getPossibleDisplayInfo on uid " + callingUid);
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        resetPriorityAfterLockedSection();
                        return arrayList;
                    }
                    java.util.List<android.view.DisplayInfo> possibleDisplayInfos = this.mPossibleDisplayInfoMapper.getPossibleDisplayInfos(i);
                    resetPriorityAfterLockedSection();
                    return possibleDisplayInfos;
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    java.util.List<android.view.DisplayInfo> getPossibleDisplayInfoLocked(int i) {
        return this.mPossibleDisplayInfoMapper.getPossibleDisplayInfos(i);
    }

    void grantEmbeddedWindowFocus(com.android.server.wm.Session session, android.window.InputTransferToken inputTransferToken, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.EmbeddedWindowController.EmbeddedWindow byInputTransferToken = this.mEmbeddedWindowController.getByInputTransferToken(inputTransferToken);
                if (byInputTransferToken == null) {
                    android.util.Slog.e(TAG, "Embedded window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (byInputTransferToken.mSession != session) {
                    android.util.Slog.e(TAG, "Window not in session:" + session);
                    resetPriorityAfterLockedSection();
                    return;
                }
                android.os.IBinder inputChannelToken = byInputTransferToken.getInputChannelToken();
                if (inputChannelToken == null) {
                    android.util.Slog.e(TAG, "Focus token found but input channel token not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                android.view.SurfaceControl.Transaction transaction = this.mTransactionFactory.get();
                int i = byInputTransferToken.mDisplayId;
                if (z) {
                    transaction.setFocusedWindow(inputChannelToken, byInputTransferToken.toString(), i).apply();
                    android.util.EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Focus request " + byInputTransferToken, "reason=grantEmbeddedWindowFocus(true)");
                } else {
                    com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                    com.android.server.wm.WindowState findFocusedWindow = displayContent == null ? null : displayContent.findFocusedWindow();
                    if (findFocusedWindow == null) {
                        transaction.setFocusedWindow(null, null, i).apply();
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -7394143854567081754L, 0, null, java.lang.String.valueOf(byInputTransferToken));
                        resetPriorityAfterLockedSection();
                        return;
                    } else {
                        transaction.setFocusedWindow(findFocusedWindow.mInputChannelToken, findFocusedWindow.getName(), i).apply();
                        android.util.EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Focus request " + findFocusedWindow, "reason=grantEmbeddedWindowFocus(false)");
                    }
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -6056928081282320632L, 0, null, java.lang.String.valueOf(byInputTransferToken), java.lang.String.valueOf(z));
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void grantEmbeddedWindowFocus(com.android.server.wm.Session session, android.view.IWindow iWindow, android.window.InputTransferToken inputTransferToken, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    android.util.Slog.e(TAG, "Host window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (windowForClientLocked.mInputChannel == null) {
                    android.util.Slog.e(TAG, "Host window does not have an input channel");
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.EmbeddedWindowController.EmbeddedWindow byInputTransferToken = this.mEmbeddedWindowController.getByInputTransferToken(inputTransferToken);
                if (byInputTransferToken == null) {
                    android.util.Slog.e(TAG, "Embedded window not found");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (byInputTransferToken.mHostWindowState != windowForClientLocked) {
                    android.util.Slog.e(TAG, "Embedded window does not belong to the host");
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (z) {
                    windowForClientLocked.mInputWindowHandle.setFocusTransferTarget(byInputTransferToken.getInputChannelToken());
                    android.util.EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Transfer focus request " + byInputTransferToken, "reason=grantEmbeddedWindowFocus(true)");
                } else {
                    windowForClientLocked.mInputWindowHandle.setFocusTransferTarget(null);
                    android.util.EventLog.writeEvent(LOGTAG_INPUT_FOCUS, "Transfer focus request " + windowForClientLocked, "reason=grantEmbeddedWindowFocus(false)");
                }
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(windowForClientLocked.getDisplayId());
                if (displayContent != null) {
                    displayContent.getInputMonitor().updateInputWindowsLw(true);
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -6056928081282320632L, 0, null, java.lang.String.valueOf(byInputTransferToken), java.lang.String.valueOf(z));
                resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void holdLock(android.os.IBinder iBinder, int i) {
        this.mTestUtilityService.verifyHoldLockToken(iBinder);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.os.SystemClock.sleep(i);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }

    public java.lang.String[] getSupportedDisplayHashAlgorithms() {
        return this.mDisplayHashController.getSupportedHashAlgorithms();
    }

    public android.view.displayhash.VerifiedDisplayHash verifyDisplayHash(android.view.displayhash.DisplayHash displayHash) {
        return this.mDisplayHashController.verifyDisplayHash(displayHash);
    }

    public void setDisplayHashThrottlingEnabled(boolean z) {
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "setDisplayHashThrottle()")) {
            throw new java.lang.SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        this.mDisplayHashController.setDisplayHashThrottlingEnabled(z);
    }

    public boolean isTaskSnapshotSupported() {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = !this.mTaskSnapshotController.shouldDisableSnapshots();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return z;
    }

    void generateDisplayHash(com.android.server.wm.Session session, android.view.IWindow iWindow, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback) {
        android.graphics.Rect rect2 = new android.graphics.Rect(rect);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowForClientLocked = windowForClientLocked(session, iWindow, false);
                if (windowForClientLocked == null) {
                    android.util.Slog.w(TAG, "Failed to generate DisplayHash. Invalid window");
                    this.mDisplayHashController.sendDisplayHashError(remoteCallback, -3);
                    resetPriorityAfterLockedSection();
                    return;
                }
                if (windowForClientLocked.mActivityRecord == null || !windowForClientLocked.mActivityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
                    this.mDisplayHashController.sendDisplayHashError(remoteCallback, -3);
                    resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.DisplayContent displayContent = windowForClientLocked.getDisplayContent();
                if (displayContent == null) {
                    android.util.Slog.w(TAG, "Failed to generate DisplayHash. Window is not on a display");
                    this.mDisplayHashController.sendDisplayHashError(remoteCallback, -4);
                    resetPriorityAfterLockedSection();
                    return;
                }
                android.view.SurfaceControl surfaceControl = displayContent.getSurfaceControl();
                this.mDisplayHashController.calculateDisplayHashBoundsLocked(windowForClientLocked, rect, rect2);
                if (rect2.isEmpty()) {
                    android.util.Slog.w(TAG, "Failed to generate DisplayHash. Bounds are not on screen");
                    this.mDisplayHashController.sendDisplayHashError(remoteCallback, -4);
                    resetPriorityAfterLockedSection();
                } else {
                    resetPriorityAfterLockedSection();
                    int i = session.mUid;
                    this.mDisplayHashController.generateDisplayHash(new android.window.ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setUid(i).setSourceCrop(rect2), rect, str, i, remoteCallback);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    boolean shouldRestoreImeVisibility(android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowState windowState = this.mWindowMap.get(iBinder);
                if (windowState == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                com.android.server.wm.Task task = windowState.getTask();
                if (task == null) {
                    resetPriorityAfterLockedSection();
                    return false;
                }
                if (windowState.mActivityRecord != null && windowState.mActivityRecord.mLastImeShown) {
                    resetPriorityAfterLockedSection();
                    return true;
                }
                resetPriorityAfterLockedSection();
                android.window.TaskSnapshot taskSnapshot = getTaskSnapshot(task.mTaskId, task.mUserId, false, false);
                return taskSnapshot != null && taskSnapshot.hasImeSurface();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getImeDisplayId() {
        int displayId;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent topFocusedDisplayContent = this.mRoot.getTopFocusedDisplayContent();
                displayId = topFocusedDisplayContent.getImePolicy() == 0 ? topFocusedDisplayContent.getDisplayId() : 0;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return displayId;
    }

    public void setTaskSnapshotEnabled(boolean z) {
        this.mTaskSnapshotController.setSnapshotEnabled(z);
    }

    public void setTaskTransitionSpec(android.view.TaskTransitionSpec taskTransitionSpec) {
        if (!checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "setTaskTransitionSpec()")) {
            throw new java.lang.SecurityException("Requires MANAGE_ACTIVITY_TASKS permission");
        }
        this.mTaskTransitionSpec = taskTransitionSpec;
    }

    public void clearTaskTransitionSpec() {
        if (!checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "clearTaskTransitionSpec()")) {
            throw new java.lang.SecurityException("Requires MANAGE_ACTIVITY_TASKS permission");
        }
        this.mTaskTransitionSpec = null;
    }

    @android.annotation.RequiresPermission("android.permission.ACCESS_FPS_COUNTER")
    public void registerTaskFpsCallback(int i, android.window.ITaskFpsCallback iTaskFpsCallback) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FPS_COUNTER") != 0) {
            throw new java.lang.SecurityException("Access denied to process: " + android.os.Binder.getCallingPid() + ", must have permission android.permission.ACCESS_FPS_COUNTER");
        }
        if (this.mRoot.anyTaskForId(i) == null) {
            throw new java.lang.IllegalArgumentException("no task with taskId: " + i);
        }
        this.mTaskFpsCallbackController.registerListener(i, iTaskFpsCallback);
    }

    @android.annotation.RequiresPermission("android.permission.ACCESS_FPS_COUNTER")
    public void unregisterTaskFpsCallback(android.window.ITaskFpsCallback iTaskFpsCallback) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_FPS_COUNTER") != 0) {
            throw new java.lang.SecurityException("Access denied to process: " + android.os.Binder.getCallingPid() + ", must have permission android.permission.ACCESS_FPS_COUNTER");
        }
        this.mTaskFpsCallbackController.lambda$registerListener$0(iTaskFpsCallback);
    }

    public android.graphics.Bitmap snapshotTaskForRecents(int i) {
        android.window.TaskSnapshot captureSnapshot;
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "snapshotTaskForRecents()")) {
            throw new java.lang.SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRoot.anyTaskForId(i, 1);
                    if (anyTaskForId == null) {
                        throw new java.lang.IllegalArgumentException("Failed to find matching task for taskId=" + i);
                    }
                    captureSnapshot = this.mTaskSnapshotController.captureSnapshot(anyTaskForId);
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
            if (captureSnapshot == null || captureSnapshot.getHardwareBuffer() == null) {
                return null;
            }
            return android.graphics.Bitmap.wrapHardwareBuffer(captureSnapshot.getHardwareBuffer(), captureSnapshot.getColorSpace());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setRecentsAppBehindSystemBars(boolean z) {
        if (!checkCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "setRecentsAppBehindSystemBars()")) {
            throw new java.lang.SecurityException("Requires START_TASKS_FROM_RECENTS permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task task = this.mRoot.getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda30
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$setRecentsAppBehindSystemBars$25;
                            lambda$setRecentsAppBehindSystemBars$25 = com.android.server.wm.WindowManagerService.lambda$setRecentsAppBehindSystemBars$25((com.android.server.wm.Task) obj);
                            return lambda$setRecentsAppBehindSystemBars$25;
                        }
                    });
                    if (task != null) {
                        task.getTask().setCanAffectSystemUiFlags(z);
                        this.mWindowPlacerLocked.requestTraversal();
                    }
                    com.android.server.inputmethod.InputMethodManagerInternal.get().maybeFinishStylusHandwriting();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setRecentsAppBehindSystemBars$25(com.android.server.wm.Task task) {
        return task.isActivityTypeHomeOrRecents() && task.getTopVisibleActivity() != null;
    }

    public int getLetterboxBackgroundColorInArgb() {
        return this.mLetterboxConfiguration.getLetterboxBackgroundColor().toArgb();
    }

    public boolean isLetterboxBackgroundMultiColored() {
        int letterboxBackgroundType = this.mLetterboxConfiguration.getLetterboxBackgroundType();
        switch (letterboxBackgroundType) {
            case 0:
                return false;
            case 1:
            case 2:
            case 3:
                return true;
            default:
                throw new java.lang.AssertionError("Unexpected letterbox background type: " + letterboxBackgroundType);
        }
    }

    public void captureDisplay(int i, @android.annotation.Nullable android.window.ScreenCapture.CaptureArgs captureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        android.util.Slog.d(TAG, "captureDisplay");
        if (!checkCallingPermission("android.permission.READ_FRAME_BUFFER", "captureDisplay()")) {
            throw new java.lang.SecurityException("Requires READ_FRAME_BUFFER permission");
        }
        android.window.ScreenCapture.LayerCaptureArgs captureArgs2 = getCaptureArgs(i, captureArgs);
        android.window.ScreenCapture.captureLayers(captureArgs2, screenCaptureListener);
        if (android.os.Binder.getCallingUid() != 1000) {
            captureArgs2.release();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.window.ScreenCapture.LayerCaptureArgs getCaptureArgs(int i, @android.annotation.Nullable android.window.ScreenCapture.CaptureArgs captureArgs) {
        android.view.SurfaceControl surfaceControl;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    throw new java.lang.IllegalArgumentException("Trying to screenshot and invalid display: " + i);
                }
                surfaceControl = displayContent.getSurfaceControl();
                if (captureArgs == null) {
                    captureArgs = new android.window.ScreenCapture.CaptureArgs.Builder().build();
                }
                if (captureArgs.mSourceCrop.isEmpty()) {
                    displayContent.getBounds(this.mTmpRect);
                    this.mTmpRect.offsetTo(0, 0);
                } else {
                    this.mTmpRect.set(captureArgs.mSourceCrop);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
        return new android.window.ScreenCapture.LayerCaptureArgs.Builder(surfaceControl, captureArgs).setSourceCrop(this.mTmpRect).build();
    }

    public boolean isGlobalKey(int i) {
        return this.mPolicy.isGlobalKey(i);
    }

    private int sanitizeWindowType(com.android.server.wm.Session session, int i, android.os.IBinder iBinder, int i2) {
        boolean z = true;
        if (i2 == 2032 && iBinder != null) {
            com.android.server.wm.WindowToken windowToken = this.mRoot.getDisplayContent(i).getWindowToken(iBinder);
            if (windowToken == null) {
                z = false;
            } else if (i2 != windowToken.getWindowType()) {
                z = false;
            }
        } else if (!session.mCanAddInternalSystemWindow && i2 != 0) {
            android.util.Slog.w(TAG, "Requires INTERNAL_SYSTEM_WINDOW permission if assign type to input. New type will be 0.");
            z = false;
        }
        if (z) {
            return i2;
        }
        return 0;
    }

    public boolean addToSurfaceSyncGroup(android.os.IBinder iBinder, boolean z, @android.annotation.Nullable android.window.ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) {
        return this.mSurfaceSyncGroupController.addToSyncGroup(iBinder, z, iSurfaceSyncGroupCompletedListener, addToSurfaceSyncGroupResult);
    }

    public void markSurfaceSyncGroupReady(android.os.IBinder iBinder) {
        this.mSurfaceSyncGroupController.markSyncGroupReady(iBinder);
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs
    public java.util.List<android.content.ComponentName> notifyScreenshotListeners(int i) {
        if (!checkCallingPermission("android.permission.STATUS_BAR_SERVICE", "notifyScreenshotListeners()")) {
            throw new java.lang.SecurityException("Requires STATUS_BAR_SERVICE permission");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    resetPriorityAfterLockedSection();
                    return arrayList;
                }
                final android.util.ArraySet arraySet = new android.util.ArraySet();
                displayContent.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerService$$ExternalSyntheticLambda24
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.WindowManagerService.lambda$notifyScreenshotListeners$26(arraySet, (com.android.server.wm.ActivityRecord) obj);
                    }
                }, true);
                java.util.List<android.content.ComponentName> copyOf = java.util.List.copyOf(arraySet);
                resetPriorityAfterLockedSection();
                return copyOf;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyScreenshotListeners$26(android.util.ArraySet arraySet, com.android.server.wm.ActivityRecord activityRecord) {
        if (!arraySet.contains(activityRecord.mActivityComponent) && activityRecord.isVisible() && activityRecord.isRegisteredForScreenCaptureCallback()) {
            activityRecord.reportScreenCaptured();
            arraySet.add(activityRecord.mActivityComponent);
        }
    }

    /* JADX WARN: Finally extract failed */
    @android.annotation.RequiresPermission("android.permission.ACCESS_SURFACE_FLINGER")
    public boolean replaceContentOnDisplay(int i, android.view.SurfaceControl surfaceControl) {
        if (!checkCallingPermission("android.permission.ACCESS_SURFACE_FLINGER", "replaceDisplayContent()")) {
            throw new java.lang.SecurityException("Requires ACCESS_SURFACE_FLINGER permission");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContentOrCreate = this.mRoot.getDisplayContentOrCreate(i);
                    if (displayContentOrCreate != null) {
                        displayContentOrCreate.replaceContent(surfaceControl);
                        resetPriorityAfterLockedSection();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                    resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public void registerTrustedPresentationListener(android.os.IBinder iBinder, android.window.ITrustedPresentationListener iTrustedPresentationListener, android.window.TrustedPresentationThresholds trustedPresentationThresholds, int i) {
        this.mTrustedPresentationListenerController.registerListener(iBinder, iTrustedPresentationListener, trustedPresentationThresholds, i);
    }

    public void unregisterTrustedPresentationListener(android.window.ITrustedPresentationListener iTrustedPresentationListener, int i) {
        this.mTrustedPresentationListenerController.unregisterListener(iTrustedPresentationListener, i);
    }

    @android.annotation.EnforcePermission("android.permission.DETECT_SCREEN_RECORDING")
    public boolean registerScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) {
        registerScreenRecordingCallback_enforcePermission();
        return this.mScreenRecordingCallbackController.register(iScreenRecordingCallback);
    }

    @android.annotation.EnforcePermission("android.permission.DETECT_SCREEN_RECORDING")
    public void unregisterScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) {
        unregisterScreenRecordingCallback_enforcePermission();
        this.mScreenRecordingCallbackController.unregister(iScreenRecordingCallback);
    }

    void onProcessActivityVisibilityChanged(int i, boolean z) {
        this.mScreenRecordingCallbackController.onProcessActivityVisibilityChanged(i, z);
    }

    public void setGlobalDragListener(android.window.IGlobalDragListener iGlobalDragListener) throws android.os.RemoteException {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("setUnhandledDragListener");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDragDropController.setGlobalDragListener(iGlobalDragListener);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterLockedSection();
                throw th;
            }
        }
        resetPriorityAfterLockedSection();
    }
}
