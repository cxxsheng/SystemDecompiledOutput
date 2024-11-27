package com.android.server.wm;

/* loaded from: classes3.dex */
public class ActivityTaskManagerService extends android.app.IActivityTaskManager.Stub {
    static final long ACTIVITY_BG_START_GRACE_PERIOD_MS = 10000;
    static final boolean ANIMATE = true;
    static final int APP_SWITCH_ALLOW = 2;
    static final int APP_SWITCH_DISALLOW = 0;
    static final int APP_SWITCH_FG_ONLY = 1;
    static final int DEMOTE_TOP_REASON_ANIMATING_RECENTS = 2;
    static final int DEMOTE_TOP_REASON_DURING_UNLOCKING = 1;
    private static final long DOZE_ANIMATING_STATE_RETAIN_TIME_MS = 2000;
    public static final java.lang.String DUMP_ACTIVITIES_CMD = "activities";
    public static final java.lang.String DUMP_ACTIVITIES_SHORT_CMD = "a";
    public static final java.lang.String DUMP_CONTAINERS_CMD = "containers";
    public static final java.lang.String DUMP_LASTANR_CMD = "lastanr";
    public static final java.lang.String DUMP_LASTANR_TRACES_CMD = "lastanr-traces";
    public static final java.lang.String DUMP_RECENTS_CMD = "recents";
    public static final java.lang.String DUMP_RECENTS_SHORT_CMD = "r";
    public static final java.lang.String DUMP_STARTER_CMD = "starter";
    public static final java.lang.String DUMP_TOP_RESUMED_ACTIVITY = "top-resumed";
    public static final java.lang.String DUMP_VISIBLE_ACTIVITIES = "visible";
    static final long INSTRUMENTATION_KEY_DISPATCHING_TIMEOUT_MILLIS = 60000;
    static final int LAYOUT_REASON_CONFIG_CHANGED = 1;
    static final int LAYOUT_REASON_VISIBILITY_CHANGED = 2;
    private static final int PENDING_ASSIST_EXTRAS_LONG_TIMEOUT = 2000;
    private static final int PENDING_ASSIST_EXTRAS_TIMEOUT = 500;
    private static final int PENDING_AUTOFILL_ASSIST_STRUCTURE_TIMEOUT = 2000;
    static final int POWER_MODE_REASON_ALL = 3;
    static final int POWER_MODE_REASON_CHANGE_DISPLAY = 2;
    static final int POWER_MODE_REASON_START_ACTIVITY = 1;
    static final int POWER_MODE_REASON_UNKNOWN_VISIBILITY = 4;
    private static final long POWER_MODE_UNKNOWN_VISIBILITY_TIMEOUT_MS = 1000;
    public static final int RELAUNCH_REASON_FREE_RESIZE = 2;
    public static final int RELAUNCH_REASON_NONE = 0;
    public static final int RELAUNCH_REASON_WINDOWING_MODE_RESIZE = 1;
    private static final long RESUME_FG_APP_SWITCH_MS = 500;
    private static final java.lang.String TAG = "ActivityTaskManager";
    static final java.lang.String TAG_ROOT_TASK = "ActivityTaskManager";
    static final java.lang.String TAG_SWITCH = "ActivityTaskManager";

    @android.annotation.Nullable
    private volatile android.content.ComponentName mActiveDreamComponent;
    android.content.ComponentName mActiveVoiceInteractionServiceComponent;
    com.android.server.wm.ActivityClientController mActivityClientController;
    private com.android.server.wm.ActivityStartController mActivityStartController;
    android.app.ActivityManagerInternal mAmInternal;
    private android.app.AppOpsManager mAppOpsManager;
    com.android.server.wm.AppWarnings mAppWarnings;

    @android.annotation.Nullable
    private com.android.server.wm.BackgroundActivityStartCallback mBackgroundActivityStartCallback;
    com.android.server.wm.CompatModePackages mCompatModePackages;
    private int mConfigurationSeq;
    android.content.Context mContext;
    com.android.server.am.AppTimeTracker mCurAppTimeTracker;
    volatile int mDemoteTopAppReasons;
    boolean mDevEnableNonResizableMultiWindow;
    boolean mForceResizableActivities;
    private int mGlobalAssetsSeq;
    com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal mGrammaticalManagerInternal;
    com.android.server.wm.ActivityTaskManagerService.H mH;
    boolean mHasCompanionDeviceSetupFeature;
    boolean mHasHeavyWeightFeature;
    boolean mHasLeanbackFeature;
    volatile com.android.server.wm.WindowProcessController mHeavyWeightProcess;
    volatile com.android.server.wm.WindowProcessController mHomeProcess;
    com.android.server.firewall.IntentFirewall mIntentFirewall;
    com.android.server.wm.KeyguardController mKeyguardController;
    java.lang.String mLastANRState;
    com.android.server.wm.ActivityRecord mLastResumedActivity;
    private volatile long mLastStopAppSwitchesTime;
    private int mLayoutReasons;
    private org.lineageos.internal.applications.LineageActivityManager mLineageActivityManager;
    private com.android.server.wm.LockTaskController mLockTaskController;
    float mMinPercentageMultiWindowSupportHeight;
    float mMinPercentageMultiWindowSupportWidth;
    com.android.server.wm.PackageConfigPersister mPackageConfigPersister;
    com.android.server.am.PendingIntentController mPendingIntentController;
    private com.android.server.policy.PermissionPolicyInternal mPermissionPolicyInternal;
    private android.content.pm.PackageManagerInternal mPmInternal;
    private android.os.PowerManagerInternal mPowerManagerInternal;
    private int mPowerModeReasons;
    volatile com.android.server.wm.WindowProcessController mPreviousProcess;
    private long mPreviousProcessVisibleTime;
    private com.android.server.wm.RecentTasks mRecentTasks;
    int mRespectsActivityMinWidthHeightMultiWindow;
    private volatile boolean mRetainPowerModeAndTopProcessState;
    com.android.server.wm.RootWindowContainer mRootWindowContainer;
    android.service.voice.IVoiceInteractionSession mRunningVoice;
    private com.android.server.wm.ActivityTaskManagerService.SettingObserver mSettingsObserver;
    volatile boolean mShuttingDown;
    private volatile boolean mSleeping;
    private com.android.server.statusbar.StatusBarManagerInternal mStatusBarManagerInternal;
    boolean mSupportsExpandedPictureInPicture;
    boolean mSupportsFreeformWindowManagement;
    boolean mSupportsMultiDisplay;
    boolean mSupportsMultiWindow;
    int mSupportsNonResizableMultiWindow;
    boolean mSupportsPictureInPicture;
    boolean mSupportsSplitScreenMultiWindow;
    boolean mSuppressResizeConfigChanges;
    private android.content.ComponentName mSysUiServiceComponent;
    private com.android.server.wm.TaskChangeNotificationController mTaskChangeNotificationController;
    com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;
    private int mThumbnailHeight;
    private int mThumbnailWidth;
    volatile com.android.server.wm.WindowProcessController mTopApp;
    android.content.ComponentName mTopComponent;
    java.lang.String mTopData;

    @android.annotation.Nullable
    private com.android.server.wm.ActivityRecord mTracedResumedActivity;
    com.android.server.uri.UriGrantsManagerInternal mUgmInternal;
    com.android.server.wm.ActivityTaskManagerService.UiHandler mUiHandler;
    private android.app.usage.UsageStatsManagerInternal mUsageStatsInternal;
    private com.android.server.pm.UserManagerService mUserManager;
    volatile com.android.server.wm.WindowProcessController mVisibleDozeUiProcess;
    android.os.PowerManager.WakeLock mVoiceWakeLock;
    com.android.server.wm.VrController mVrController;
    private com.android.server.wallpaper.WallpaperManagerInternal mWallpaperManagerInternal;
    com.android.server.wm.WindowManagerService mWindowManager;
    final com.android.server.wm.WindowManagerGlobalLock mGlobalLock = new com.android.server.wm.WindowManagerGlobalLock();
    final java.lang.Object mGlobalLockWithoutBoost = this.mGlobalLock;
    final com.android.server.wm.MirrorActiveUids mActiveUids = new com.android.server.wm.MirrorActiveUids();
    final com.android.internal.app.ProcessMap<com.android.server.wm.WindowProcessController> mProcessNames = new com.android.internal.app.ProcessMap<>();
    final com.android.server.wm.WindowProcessControllerMap mProcessMap = new com.android.server.wm.WindowProcessControllerMap();
    private boolean mKeyguardShown = false;
    private int mViSessionId = 1000;
    private final java.util.ArrayList<com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras> mPendingAssistExtras = new java.util.ArrayList<>();
    private final java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> mCompanionAppUidsMap = new android.util.ArrayMap();
    private android.util.SparseArray<com.android.server.wm.ActivityInterceptorCallback> mActivityInterceptorCallbacks = new android.util.SparseArray<>();
    final com.android.server.wm.ActivityTaskManagerService.UpdateConfigurationResult mTmpUpdateConfigurationResult = new com.android.server.wm.ActivityTaskManagerService.UpdateConfigurationResult();
    private java.lang.String[] mSupportedSystemLocales = null;
    private android.content.res.Configuration mTempConfig = new android.content.res.Configuration();
    private volatile int mAppSwitchesState = 2;

    @com.android.internal.annotations.GuardedBy({"itself"})
    private final java.util.List<android.app.AnrController> mAnrController = new java.util.ArrayList();
    android.app.IActivityController mController = null;
    boolean mControllerIsAMonkey = false;
    java.lang.String mTopAction = "android.intent.action.MAIN";
    java.lang.String mProfileApp = null;
    com.android.server.wm.WindowProcessController mProfileProc = null;
    android.app.ProfilerInfo mProfilerInfo = null;
    private final android.os.UpdateLock mUpdateLock = new android.os.UpdateLock("immersive");
    final android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.lang.Integer>> mAllowAppSwitchUids = new android.util.SparseArray<>();
    final java.util.List<com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver> mScreenObservers = java.util.Collections.synchronizedList(new java.util.ArrayList());
    int mVr2dDisplayId = -1;
    volatile int mTopProcessState = 2;
    private boolean mShowDialogs = true;
    private int[] mAccessibilityServiceUids = new int[0];
    private int mDeviceOwnerUid = -1;
    private java.util.Set<java.lang.Integer> mProfileOwnerUids = new android.util.ArraySet();
    private final java.lang.Runnable mUpdateOomAdjRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.updateOomAdj(1);
        }
    };
    final int mFactoryTest = android.os.FactoryTest.getMode();
    final android.app.ActivityThread mSystemThread = android.app.ActivityThread.currentActivityThread();
    private final android.content.Context mUiContext = this.mSystemThread.getSystemUiContext();
    private final com.android.server.wm.ClientLifecycleManager mLifecycleManager = new com.android.server.wm.ClientLifecycleManager();
    final com.android.server.wm.VisibleActivityProcessTracker mVisibleActivityProcessTracker = new com.android.server.wm.VisibleActivityProcessTracker(this);

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wm.ActivityTaskManagerInternal mInternal = new com.android.server.wm.ActivityTaskManagerService.LocalService();
    final int GL_ES_VERSION = android.os.SystemProperties.getInt("ro.opengles.version", 0);
    com.android.server.wm.WindowOrganizerController mWindowOrganizerController = new com.android.server.wm.WindowOrganizerController(this);
    com.android.server.wm.TaskOrganizerController mTaskOrganizerController = this.mWindowOrganizerController.mTaskOrganizerController;
    com.android.server.wm.TaskFragmentOrganizerController mTaskFragmentOrganizerController = this.mWindowOrganizerController.mTaskFragmentOrganizerController;
    final com.android.server.wm.BackNavigationController mBackNavigationController = new com.android.server.wm.BackNavigationController();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface AppSwitchState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface DemoteTopReason {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface HotPath {
        public static final int LRU_UPDATE = 2;
        public static final int NONE = 0;
        public static final int OOM_ADJUSTMENT = 1;
        public static final int PROCESS_CHANGE = 3;
        public static final int START_SERVICE = 4;

        int caller() default 0;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface LayoutReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PowerModeReason {
    }

    static final class UpdateConfigurationResult {
        boolean activityRelaunched;
        int changes;
        boolean mIsUpdating;

        UpdateConfigurationResult() {
        }
    }

    private final class SettingObserver extends android.database.ContentObserver {
        private final android.net.Uri mFontScaleUri;
        private final android.net.Uri mFontWeightAdjustmentUri;
        private final android.net.Uri mHideErrorDialogsUri;

        SettingObserver() {
            super(com.android.server.wm.ActivityTaskManagerService.this.mH);
            this.mFontScaleUri = android.provider.Settings.System.getUriFor("font_scale");
            this.mHideErrorDialogsUri = android.provider.Settings.Global.getUriFor("hide_error_dialogs");
            this.mFontWeightAdjustmentUri = android.provider.Settings.Secure.getUriFor("font_weight_adjustment");
            android.content.ContentResolver contentResolver = com.android.server.wm.ActivityTaskManagerService.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(this.mFontScaleUri, false, this, -1);
            contentResolver.registerContentObserver(this.mHideErrorDialogsUri, false, this, -1);
            contentResolver.registerContentObserver(this.mFontWeightAdjustmentUri, false, this, -1);
        }

        public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i, int i2) {
            for (android.net.Uri uri : collection) {
                if (this.mFontScaleUri.equals(uri)) {
                    com.android.server.wm.ActivityTaskManagerService.this.updateFontScaleIfNeeded(i2);
                } else if (this.mHideErrorDialogsUri.equals(uri)) {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.ActivityTaskManagerService.this.updateShouldShowDialogsLocked(com.android.server.wm.ActivityTaskManagerService.this.getGlobalConfiguration());
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else if (this.mFontWeightAdjustmentUri.equals(uri)) {
                    com.android.server.wm.ActivityTaskManagerService.this.updateFontWeightAdjustmentIfNeeded(i2);
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public ActivityTaskManagerService(android.content.Context context) {
        this.mContext = context;
    }

    public void onSystemReady() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
                this.mHasHeavyWeightFeature = packageManager.hasSystemFeature("android.software.cant_save_state");
                this.mHasLeanbackFeature = packageManager.hasSystemFeature("android.software.leanback");
                this.mHasCompanionDeviceSetupFeature = packageManager.hasSystemFeature("android.software.companion_device_setup");
                this.mVrController.onSystemReady();
                this.mRecentTasks.onSystemReadyLocked();
                this.mTaskSupervisor.onSystemReady();
                this.mActivityClientController.onSystemReady();
                com.android.server.wm.ActivitySecurityModelFeatureFlags.initialize(this.mContext.getMainExecutor());
                this.mGrammaticalManagerInternal = (com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal) com.android.server.LocalServices.getService(com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal.class);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void onInitPowerManagement() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mTaskSupervisor.initPowerManagement();
                android.os.PowerManager powerManager = (android.os.PowerManager) this.mContext.getSystemService("power");
                this.mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
                this.mVoiceWakeLock = powerManager.newWakeLock(1, "*voice*");
                this.mVoiceWakeLock.setReferenceCounted(false);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void installSystemProviders() {
        this.mSettingsObserver = new com.android.server.wm.ActivityTaskManagerService.SettingObserver();
        this.mLineageActivityManager = new org.lineageos.internal.applications.LineageActivityManager(this.mContext);
    }

    public void retrieveSettings(android.content.ContentResolver contentResolver) {
        boolean z = this.mContext.getPackageManager().hasSystemFeature("android.software.freeform_window_management") || android.provider.Settings.Global.getInt(contentResolver, "enable_freeform_support", 0) != 0;
        boolean supportsMultiWindow = android.app.ActivityTaskManager.supportsMultiWindow(this.mContext);
        boolean z2 = supportsMultiWindow && this.mContext.getPackageManager().hasSystemFeature("android.software.picture_in_picture");
        boolean z3 = z2 && this.mContext.getPackageManager().hasSystemFeature("android.software.expanded_picture_in_picture");
        boolean supportsSplitScreenMultiWindow = android.app.ActivityTaskManager.supportsSplitScreenMultiWindow(this.mContext);
        boolean hasSystemFeature = this.mContext.getPackageManager().hasSystemFeature("android.software.activities_on_secondary_displays");
        boolean z4 = android.provider.Settings.Global.getInt(contentResolver, "debug.force_rtl", 0) != 0;
        boolean z5 = android.provider.Settings.Global.getInt(contentResolver, "force_resizable_activities", 0) != 0;
        boolean z6 = android.provider.Settings.Global.getInt(contentResolver, "enable_non_resizable_multi_window", 0) != 0;
        int integer = this.mContext.getResources().getInteger(android.R.integer.config_sidefpsSkipWaitForPowerAcquireMessage);
        int integer2 = this.mContext.getResources().getInteger(android.R.integer.config_reduceBrightColorsStrengthDefault);
        float f = this.mContext.getResources().getFloat(android.R.dimen.config_letterboxBackgroundWallaperDarkScrimAlpha);
        float f2 = this.mContext.getResources().getFloat(android.R.dimen.config_letterboxBackgroundWallpaperBlurRadius);
        android.sysprop.DisplayProperties.debug_force_rtl(java.lang.Boolean.valueOf(z4));
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        android.provider.Settings.System.getConfiguration(contentResolver, configuration);
        if (z4) {
            configuration.setLayoutDirection(configuration.locale);
        }
        configuration.setGrammaticalGender(this.mGrammaticalManagerInternal.retrieveSystemGrammaticalGender(configuration));
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mForceResizableActivities = z5;
                this.mDevEnableNonResizableMultiWindow = z6;
                this.mSupportsNonResizableMultiWindow = integer;
                this.mRespectsActivityMinWidthHeightMultiWindow = integer2;
                this.mMinPercentageMultiWindowSupportHeight = f;
                this.mMinPercentageMultiWindowSupportWidth = f2;
                boolean z7 = z || supportsSplitScreenMultiWindow || z2 || hasSystemFeature;
                if ((supportsMultiWindow || z5) && z7) {
                    this.mSupportsMultiWindow = true;
                    this.mSupportsFreeformWindowManagement = z;
                    this.mSupportsSplitScreenMultiWindow = supportsSplitScreenMultiWindow;
                    this.mSupportsPictureInPicture = z2;
                    this.mSupportsExpandedPictureInPicture = z3;
                    this.mSupportsMultiDisplay = hasSystemFeature;
                } else {
                    this.mSupportsMultiWindow = false;
                    this.mSupportsFreeformWindowManagement = false;
                    this.mSupportsSplitScreenMultiWindow = false;
                    this.mSupportsPictureInPicture = false;
                    this.mSupportsExpandedPictureInPicture = false;
                    this.mSupportsMultiDisplay = false;
                }
                this.mWindowManager.mRoot.onSettingsRetrieved();
                updateConfigurationLocked(configuration, null, true);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 5445799252721678675L, 0, null, java.lang.String.valueOf(getGlobalConfiguration()));
                android.content.res.Resources resources = this.mContext.getResources();
                this.mThumbnailWidth = resources.getDimensionPixelSize(android.R.dimen.thumbnail_width);
                this.mThumbnailHeight = resources.getDimensionPixelSize(android.R.dimen.thumbnail_height);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public com.android.server.wm.WindowManagerGlobalLock getGlobalLock() {
        return this.mGlobalLock;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.wm.ActivityTaskManagerInternal getAtmInternal() {
        return this.mInternal;
    }

    public void initialize(com.android.server.firewall.IntentFirewall intentFirewall, com.android.server.am.PendingIntentController pendingIntentController, android.os.Looper looper) {
        this.mH = new com.android.server.wm.ActivityTaskManagerService.H(looper);
        this.mUiHandler = new com.android.server.wm.ActivityTaskManagerService.UiHandler();
        this.mIntentFirewall = intentFirewall;
        java.io.File ensureSystemDir = com.android.server.SystemServiceManager.ensureSystemDir();
        this.mAppWarnings = createAppWarnings(this.mUiContext, this.mH, this.mUiHandler, ensureSystemDir);
        this.mCompatModePackages = new com.android.server.wm.CompatModePackages(this, ensureSystemDir, this.mH);
        this.mPendingIntentController = pendingIntentController;
        this.mTaskSupervisor = createTaskSupervisor();
        this.mActivityClientController = new com.android.server.wm.ActivityClientController(this);
        this.mTaskChangeNotificationController = new com.android.server.wm.TaskChangeNotificationController(this.mTaskSupervisor, this.mH);
        this.mLockTaskController = new com.android.server.wm.LockTaskController(this.mContext, this.mTaskSupervisor, this.mH, this.mTaskChangeNotificationController);
        this.mActivityStartController = new com.android.server.wm.ActivityStartController(this);
        setRecentTasks(new com.android.server.wm.RecentTasks(this, this.mTaskSupervisor));
        this.mVrController = new com.android.server.wm.VrController(this.mGlobalLock);
        this.mKeyguardController = this.mTaskSupervisor.getKeyguardController();
        this.mPackageConfigPersister = new com.android.server.wm.PackageConfigPersister(this.mTaskSupervisor.mPersisterQueue, this);
    }

    public void onActivityManagerInternalAdded() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
                this.mUgmInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    int increaseConfigurationSeqLocked() {
        int i = this.mConfigurationSeq + 1;
        this.mConfigurationSeq = i;
        this.mConfigurationSeq = java.lang.Math.max(i, 1);
        return this.mConfigurationSeq;
    }

    protected com.android.server.wm.ActivityTaskSupervisor createTaskSupervisor() {
        com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor = new com.android.server.wm.ActivityTaskSupervisor(this, this.mH.getLooper());
        activityTaskSupervisor.initialize();
        return activityTaskSupervisor;
    }

    protected com.android.server.wm.AppWarnings createAppWarnings(android.content.Context context, android.os.Handler handler, android.os.Handler handler2, java.io.File file) {
        return new com.android.server.wm.AppWarnings(this, context, handler, handler2, file);
    }

    public void setWindowManager(com.android.server.wm.WindowManagerService windowManagerService) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mWindowManager = windowManagerService;
                this.mRootWindowContainer = windowManagerService.mRoot;
                this.mWindowOrganizerController.mTransitionController.setWindowManager(windowManagerService);
                this.mLifecycleManager.setWindowManager(windowManagerService);
                this.mTempConfig.setToDefaults();
                this.mTempConfig.setLocales(android.os.LocaleList.getDefault());
                this.mTempConfig.seq = 1;
                this.mConfigurationSeq = 1;
                this.mRootWindowContainer.onConfigurationChanged(this.mTempConfig);
                this.mLockTaskController.setWindowManager(windowManagerService);
                this.mTaskSupervisor.setWindowManager(windowManagerService);
                this.mRootWindowContainer.setWindowManager(windowManagerService);
                this.mBackNavigationController.setWindowManager(windowManagerService);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setUsageStatsManager(android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mUsageStatsInternal = usageStatsManagerInternal;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    android.content.Context getUiContext() {
        return this.mUiContext;
    }

    com.android.server.pm.UserManagerService getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user"));
        }
        return this.mUserManager;
    }

    android.app.AppOpsManager getAppOpsManager() {
        if (this.mAppOpsManager == null) {
            this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        }
        return this.mAppOpsManager;
    }

    boolean hasUserRestriction(java.lang.String str, int i) {
        return getUserManager().hasUserRestriction(str, i);
    }

    boolean hasSystemAlertWindowPermission(int i, int i2, java.lang.String str) {
        int noteOpNoThrow = getAppOpsManager().noteOpNoThrow(24, i, str, (java.lang.String) null, "");
        return noteOpNoThrow == 3 ? checkPermission("android.permission.SYSTEM_ALERT_WINDOW", i2, i) == 0 : noteOpNoThrow == 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setRecentTasks(com.android.server.wm.RecentTasks recentTasks) {
        this.mRecentTasks = recentTasks;
        this.mTaskSupervisor.setRecentTasks(recentTasks);
    }

    com.android.server.wm.RecentTasks getRecentTasks() {
        return this.mRecentTasks;
    }

    com.android.server.wm.ClientLifecycleManager getLifecycleManager() {
        return this.mLifecycleManager;
    }

    com.android.server.wm.ActivityStartController getActivityStartController() {
        return this.mActivityStartController;
    }

    com.android.server.wm.TaskChangeNotificationController getTaskChangeNotificationController() {
        return this.mTaskChangeNotificationController;
    }

    com.android.server.wm.LockTaskController getLockTaskController() {
        return this.mLockTaskController;
    }

    com.android.server.wm.TransitionController getTransitionController() {
        return this.mWindowOrganizerController.getTransitionController();
    }

    private android.content.res.Configuration getGlobalConfigurationForCallingPid() {
        int callingPid = android.os.Binder.getCallingPid();
        if (callingPid == com.android.server.wm.WindowManagerService.MY_PID || callingPid < 0) {
            return getGlobalConfiguration();
        }
        com.android.server.wm.WindowProcessController process = this.mProcessMap.getProcess(callingPid);
        return process != null ? process.getConfiguration() : getGlobalConfiguration();
    }

    public android.content.pm.ConfigurationInfo getDeviceConfigurationInfo() {
        android.content.pm.ConfigurationInfo configurationInfo = new android.content.pm.ConfigurationInfo();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.content.res.Configuration globalConfigurationForCallingPid = getGlobalConfigurationForCallingPid();
                configurationInfo.reqTouchScreen = globalConfigurationForCallingPid.touchscreen;
                configurationInfo.reqKeyboardType = globalConfigurationForCallingPid.keyboard;
                configurationInfo.reqNavigation = globalConfigurationForCallingPid.navigation;
                if (globalConfigurationForCallingPid.navigation == 2 || globalConfigurationForCallingPid.navigation == 3) {
                    configurationInfo.reqInputFeatures |= 2;
                }
                if (globalConfigurationForCallingPid.keyboard != 0 && globalConfigurationForCallingPid.keyboard != 1) {
                    configurationInfo.reqInputFeatures |= 1;
                }
                configurationInfo.reqGlEsVersion = this.GL_ES_VERSION;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return configurationInfo;
    }

    @android.annotation.Nullable
    public com.android.server.wm.BackgroundActivityStartCallback getBackgroundActivityStartCallback() {
        return this.mBackgroundActivityStartCallback;
    }

    android.util.SparseArray<com.android.server.wm.ActivityInterceptorCallback> getActivityInterceptorCallbacks() {
        return this.mActivityInterceptorCallbacks;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start() {
        com.android.server.LocalServices.addService(com.android.server.wm.ActivityTaskManagerInternal.class, this.mInternal);
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.wm.ActivityTaskManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mService = new com.android.server.wm.ActivityTaskManagerService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("activity_task", this.mService);
            this.mService.start();
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocked(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            com.android.server.wm.WindowManagerGlobalLock globalLock = this.mService.getGlobalLock();
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (globalLock) {
                try {
                    this.mService.mTaskSupervisor.onUserUnlocked(targetUser.getUserIdentifier());
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            com.android.server.wm.WindowManagerGlobalLock globalLock = this.mService.getGlobalLock();
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (globalLock) {
                try {
                    this.mService.mTaskSupervisor.mLaunchParamsPersister.onCleanupUser(targetUser.getUserIdentifier());
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        public com.android.server.wm.ActivityTaskManagerService getService() {
            return this.mService;
        }
    }

    public final int startActivity(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle) {
        return startActivityAsUser(iApplicationThread, str, str2, intent, str3, iBinder, str4, i, i2, profilerInfo, bundle, android.os.UserHandle.getCallingUserId());
    }

    public final int startActivities(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, android.os.Bundle bundle, int i) {
        assertPackageMatchesCallingUid(str);
        enforceNotIsolatedCaller("startActivities");
        return getActivityStartController().startActivities(iApplicationThread, -1, 0, -1, str, str2, intentArr, strArr, iBinder, com.android.server.wm.SafeActivityOptions.fromBundle(bundle), handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "startActivities"), "startActivities", null, android.app.BackgroundStartPrivileges.NONE);
    }

    public int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) {
        return startActivityAsUser(iApplicationThread, str, str2, intent, str3, iBinder, str4, i, i2, profilerInfo, bundle, i3, true);
    }

    static boolean isSdkSandboxActivityIntent(android.content.Context context, android.content.Intent intent) {
        return intent != null && (!com.android.sdksandbox.flags.Flags.sandboxActivitySdkBasedContext() ? !intent.isSandboxActivity(context) : !android.app.sdksandbox.sandboxactivity.SdkSandboxActivityAuthority.isSdkSandboxActivityIntent(context, intent));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3, boolean z) {
        com.android.server.wm.SafeActivityOptions fromBundle = com.android.server.wm.SafeActivityOptions.fromBundle(bundle);
        assertPackageMatchesCallingUid(str);
        enforceNotIsolatedCaller("startActivityAsUser");
        if (isSdkSandboxActivityIntent(this.mContext, intent)) {
            ((com.android.server.sdksandbox.SdkSandboxManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.sdksandbox.SdkSandboxManagerLocal.class)).enforceAllowedToHostSandboxedActivity(intent, android.os.Binder.getCallingUid(), str);
        }
        if (android.os.Process.isSdkSandboxUid(android.os.Binder.getCallingUid())) {
            com.android.server.sdksandbox.SdkSandboxManagerLocal sdkSandboxManagerLocal = (com.android.server.sdksandbox.SdkSandboxManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.sdksandbox.SdkSandboxManagerLocal.class);
            if (sdkSandboxManagerLocal == null) {
                throw new java.lang.IllegalStateException("SdkSandboxManagerLocal not found when starting an activity from an SDK sandbox uid.");
            }
            sdkSandboxManagerLocal.enforceAllowedToStartActivity(intent);
        }
        return getActivityStartController().obtainStarter(intent, "startActivityAsUser").setCaller(iApplicationThread).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str3).setResultTo(iBinder).setResultWho(str4).setRequestCode(i).setStartFlags(i2).setProfilerInfo(profilerInfo).setActivityOptions(fromBundle).setUserId(getActivityStartController().checkTargetUser(i3, z, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), "startActivityAsUser")).execute();
    }

    public int startActivityIntentSender(android.app.IApplicationThread iApplicationThread, android.content.IIntentSender iIntentSender, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder2, java.lang.String str2, int i, int i2, int i3, android.os.Bundle bundle) {
        enforceNotIsolatedCaller("startActivityIntentSender");
        if (intent != null) {
            if (intent.hasFileDescriptors()) {
                throw new java.lang.IllegalArgumentException("File descriptors passed in Intent");
            }
            intent.removeExtendedFlags(1);
        }
        if (!(iIntentSender instanceof com.android.server.am.PendingIntentRecord)) {
            throw new java.lang.IllegalArgumentException("Bad PendingIntent object");
        }
        com.android.server.am.PendingIntentRecord pendingIntentRecord = (com.android.server.am.PendingIntentRecord) iIntentSender;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                if (topDisplayFocusedRootTask != null && topDisplayFocusedRootTask.getTopResumedActivity() != null && topDisplayFocusedRootTask.getTopResumedActivity().info.applicationInfo.uid == android.os.Binder.getCallingUid()) {
                    this.mAppSwitchesState = 2;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return pendingIntentRecord.sendInner(iApplicationThread, 0, intent, str, iBinder, null, null, iBinder2, str2, i, i2, i3, bundle);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ac, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ad, code lost:
    
        if (r8 >= r7) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b7, code lost:
    
        r7 = ((android.content.pm.ResolveInfo) r0.get(r8)).activityInfo;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ba, code lost:
    
        if (r15 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00bc, code lost:
    
        android.util.Slog.v("ActivityTaskManager", "Next matching activity: found current " + r4.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + r4.info.name);
        r8 = new java.lang.StringBuilder();
        r8.append("Next matching activity: next is ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00ec, code lost:
    
        if (r7 != null) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00ee, code lost:
    
        r9 = "null";
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x010b, code lost:
    
        r8.append(r9);
        android.util.Slog.v("ActivityTaskManager", r8.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00f3, code lost:
    
        r9 = r7.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + r7.name;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00b9, code lost:
    
        r7 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean startNextMatchingActivity(android.os.IBinder iBinder, android.content.Intent intent, android.os.Bundle bundle) {
        boolean z;
        android.content.pm.ActivityInfo activityInfo;
        if (intent != null && intent.hasFileDescriptors()) {
            throw new java.lang.IllegalArgumentException("File descriptors passed in Intent");
        }
        com.android.server.wm.SafeActivityOptions fromBundle = com.android.server.wm.SafeActivityOptions.fromBundle(bundle);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                boolean z2 = false;
                if (isInRootTaskLocked == null) {
                    com.android.server.wm.SafeActivityOptions.abort(fromBundle);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (isInRootTaskLocked.attachedToProcess()) {
                    android.content.Intent intent2 = new android.content.Intent(intent);
                    intent2.removeExtendedFlags(1);
                    intent2.setDataAndType(isInRootTaskLocked.intent.getData(), isInRootTaskLocked.intent.getType());
                    intent2.setComponent(null);
                    if ((intent2.getFlags() & 8) == 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    try {
                        java.util.List list = android.app.AppGlobals.getPackageManager().queryIntentActivities(intent2, isInRootTaskLocked.resolvedType, 66560L, android.os.UserHandle.getCallingUserId()).getList();
                        int size = list != null ? list.size() : 0;
                        int i = 0;
                        while (true) {
                            if (i >= size) {
                                activityInfo = null;
                                break;
                            }
                            android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) list.get(i);
                            if (resolveInfo.activityInfo.packageName.equals(isInRootTaskLocked.packageName) && resolveInfo.activityInfo.name.equals(isInRootTaskLocked.info.name)) {
                                break;
                            }
                            i++;
                        }
                    } catch (android.os.RemoteException e) {
                        activityInfo = null;
                    }
                    if (activityInfo == null) {
                        com.android.server.wm.SafeActivityOptions.abort(fromBundle);
                        if (z) {
                            android.util.Slog.d("ActivityTaskManager", "Next matching activity: nothing found");
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    intent2.setComponent(new android.content.ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
                    intent2.setFlags(intent2.getFlags() & (-503316481));
                    boolean z3 = isInRootTaskLocked.finishing;
                    isInRootTaskLocked.finishing = true;
                    com.android.server.wm.ActivityRecord activityRecord = isInRootTaskLocked.resultTo;
                    java.lang.String str = isInRootTaskLocked.resultWho;
                    int i2 = isInRootTaskLocked.requestCode;
                    isInRootTaskLocked.resultTo = null;
                    if (activityRecord != null) {
                        activityRecord.removeResultsLocked(isInRootTaskLocked, str, i2);
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    if (fromBundle == null) {
                        try {
                            fromBundle = new com.android.server.wm.SafeActivityOptions(android.app.ActivityOptions.makeBasic());
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    fromBundle.getOptions(isInRootTaskLocked).setAvoidMoveToFront();
                    int execute = getActivityStartController().obtainStarter(intent2, "startNextMatchingActivity").setCaller(isInRootTaskLocked.app.getThread()).setResolvedType(isInRootTaskLocked.resolvedType).setActivityInfo(activityInfo).setResultTo(activityRecord != null ? activityRecord.token : null).setResultWho(str).setRequestCode(i2).setCallingPid(-1).setCallingUid(isInRootTaskLocked.launchedFromUid).setCallingPackage(isInRootTaskLocked.launchedFromPackage).setCallingFeatureId(isInRootTaskLocked.launchedFromFeatureId).setRealCallingPid(-1).setRealCallingUid(isInRootTaskLocked.launchedFromUid).setActivityOptions(fromBundle).execute();
                    isInRootTaskLocked.finishing = z3;
                    if (execute == 0) {
                        z2 = true;
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return z2;
                }
                com.android.server.wm.SafeActivityOptions.abort(fromBundle);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return false;
            } catch (java.lang.Throwable th2) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
    }

    boolean isDreaming() {
        return this.mActiveDreamComponent != null;
    }

    boolean canLaunchDreamActivity(java.lang.String str) {
        if (this.mActiveDreamComponent == null || str == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_DREAM, -3811526397232923712L, 0, "Cannot launch dream activity due to invalid state. dream component: %s packageName: %s", java.lang.String.valueOf(this.mActiveDreamComponent), java.lang.String.valueOf(str));
            return false;
        }
        if (str.equals(this.mActiveDreamComponent.getPackageName())) {
            return true;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_DREAM, -6981899770129924827L, 0, "Dream packageName does not match active dream. Package %s does not match %s", java.lang.String.valueOf(str), java.lang.String.valueOf(java.lang.String.valueOf(this.mActiveDreamComponent)));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.IAppTask startDreamActivityInternal(@android.annotation.NonNull android.content.Intent intent, int i, int i2) {
        android.app.IAppTask appTaskImpl;
        android.content.pm.ActivityInfo activityInfo = new android.content.pm.ActivityInfo();
        activityInfo.theme = android.R.style.Theme.Dialog.RecentApplications;
        activityInfo.exported = true;
        activityInfo.name = android.service.dreams.DreamActivity.class.getName();
        activityInfo.enabled = true;
        activityInfo.persistableMode = 1;
        activityInfo.screenOrientation = -1;
        activityInfo.colorMode = 0;
        activityInfo.flags |= 32;
        activityInfo.configChanges = -1;
        if (android.service.controls.flags.Flags.homePanelDream()) {
            activityInfo.launchMode = 0;
            activityInfo.documentLaunchMode = 2;
        } else {
            activityInfo.resizeMode = 0;
            activityInfo.launchMode = 3;
        }
        android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
        makeBasic.setLaunchActivityType(5);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowProcessController process = this.mProcessMap.getProcess(i2);
                activityInfo.packageName = process.mInfo.packageName;
                activityInfo.applicationInfo = process.mInfo;
                activityInfo.processName = process.mName;
                activityInfo.uiOptions = process.mInfo.uiOptions;
                activityInfo.taskAffinity = "android:" + activityInfo.packageName + "/dream";
                com.android.server.wm.ActivityRecord[] activityRecordArr = new com.android.server.wm.ActivityRecord[1];
                getActivityStartController().obtainStarter(intent, "dream").setCallingUid(i).setCallingPid(i2).setCallingPackage(intent.getPackage()).setActivityInfo(activityInfo).setActivityOptions(createSafeActivityOptionsWithBalAllowed(makeBasic)).setOutActivity(activityRecordArr).setRealCallingUid(android.os.Binder.getCallingUid()).setBackgroundStartPrivileges(android.app.BackgroundStartPrivileges.ALLOW_BAL).execute();
                com.android.server.wm.ActivityRecord activityRecord = activityRecordArr[0];
                appTaskImpl = activityRecord == null ? null : new com.android.server.wm.AppTaskImpl(this, activityRecord.getTask().mTaskId, i);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return appTaskImpl;
    }

    public final android.app.WaitResult startActivityAndWait(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i3) {
        assertPackageMatchesCallingUid(str);
        android.app.WaitResult waitResult = new android.app.WaitResult();
        enforceNotIsolatedCaller("startActivityAndWait");
        getActivityStartController().obtainStarter(intent, "startActivityAndWait").setCaller(iApplicationThread).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str3).setResultTo(iBinder).setResultWho(str4).setRequestCode(i).setStartFlags(i2).setActivityOptions(bundle).setUserId(handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i3, "startActivityAndWait")).setProfilerInfo(profilerInfo).setWaitResult(waitResult).execute();
        return waitResult;
    }

    public final int startActivityWithConfig(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i, int i2, android.content.res.Configuration configuration, android.os.Bundle bundle, int i3) {
        assertPackageMatchesCallingUid(str);
        enforceNotIsolatedCaller("startActivityWithConfig");
        return getActivityStartController().obtainStarter(intent, "startActivityWithConfig").setCaller(iApplicationThread).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str3).setResultTo(iBinder).setResultWho(str4).setRequestCode(i).setStartFlags(i2).setGlobalConfiguration(configuration).setActivityOptions(bundle).setUserId(handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i3, "startActivityWithConfig")).execute();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0071, code lost:
    
        if (r6.getComponent() == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0077, code lost:
    
        if (r6.getSelector() != null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0081, code lost:
    
        throw new java.lang.SecurityException("Selector not allowed with ignoreTargetSecurity");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0089, code lost:
    
        throw new java.lang.SecurityException("Component must be specified with ignoreTargetSecurity");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int startActivityAsCaller(android.app.IApplicationThread iApplicationThread, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.os.IBinder iBinder, java.lang.String str3, int i, int i2, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, boolean z, int i3) {
        com.android.server.wm.ActivityRecord isInAnyTask;
        int i4;
        java.lang.String str4;
        java.lang.String str5;
        boolean isResolverOrChildActivity;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (iBinder == null) {
                    throw new java.lang.SecurityException("Must be called from an activity");
                }
                isInAnyTask = com.android.server.wm.ActivityRecord.isInAnyTask(iBinder);
                if (isInAnyTask == null) {
                    throw new java.lang.SecurityException("Called with bad activity token: " + iBinder);
                }
                if (isInAnyTask.app == null) {
                    throw new java.lang.SecurityException("Called without a process attached to activity");
                }
                if (checkCallingPermission("android.permission.START_ACTIVITY_AS_CALLER") != 0) {
                    if (!isInAnyTask.info.packageName.equals(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
                        throw new java.lang.SecurityException("Must be called from an activity that is declared in the android package");
                    }
                    if (android.os.UserHandle.getAppId(isInAnyTask.app.mUid) != 1000 && isInAnyTask.app.mUid != isInAnyTask.launchedFromUid) {
                        throw new java.lang.SecurityException("Calling activity in uid " + isInAnyTask.app.mUid + " must be system uid or original calling uid " + isInAnyTask.launchedFromUid);
                    }
                }
                i4 = isInAnyTask.launchedFromUid;
                str4 = isInAnyTask.launchedFromPackage;
                str5 = isInAnyTask.launchedFromFeatureId;
                isResolverOrChildActivity = isInAnyTask.isResolverOrChildActivity();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (i3 == -10000) {
            i3 = android.os.UserHandle.getUserId(isInAnyTask.app.mUid);
        }
        try {
            com.android.server.wm.ActivityStarter ignoreTargetSecurity = getActivityStartController().obtainStarter(intent, "startActivityAsCaller").setCallingUid(i4).setCallingPackage(str4).setCallingFeatureId(str5).setResolvedType(str2).setResultTo(iBinder).setResultWho(str3).setRequestCode(i).setStartFlags(i2).setActivityOptions(createSafeActivityOptionsWithBalAllowed(bundle)).setUserId(i3).setIgnoreTargetSecurity(z);
            if (isResolverOrChildActivity) {
                i4 = 0;
            }
            return ignoreTargetSecurity.setFilterCallingUid(i4).setBackgroundStartPrivileges(android.app.BackgroundStartPrivileges.ALLOW_BAL).execute();
        } catch (java.lang.SecurityException e) {
            throw e;
        }
    }

    int handleIncomingUser(int i, int i2, int i3, java.lang.String str) {
        return this.mAmInternal.handleIncomingUser(i, i2, i3, false, 0, str, (java.lang.String) null);
    }

    public int startVoiceActivity(java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor, int i3, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, int i4) {
        assertPackageMatchesCallingUid(str);
        this.mAmInternal.enforceCallingPermission("android.permission.BIND_VOICE_INTERACTION", "startVoiceActivity()");
        if (iVoiceInteractionSession == null || iVoiceInteractor == null) {
            throw new java.lang.NullPointerException("null session or interactor");
        }
        return getActivityStartController().obtainStarter(intent, "startVoiceActivity").setCallingUid(i2).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str3).setVoiceSession(iVoiceInteractionSession).setVoiceInteractor(iVoiceInteractor).setStartFlags(i3).setProfilerInfo(profilerInfo).setActivityOptions(createSafeActivityOptionsWithBalAllowed(bundle)).setUserId(handleIncomingUser(i, i2, i4, "startVoiceActivity")).setBackgroundStartPrivileges(android.app.BackgroundStartPrivileges.ALLOW_BAL).execute();
    }

    public java.lang.String getVoiceInteractorPackageName(android.os.IBinder iBinder) {
        return ((android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class)).getVoiceInteractorPackageName(iBinder);
    }

    public int startAssistantActivity(java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle, int i3) {
        assertPackageMatchesCallingUid(str);
        this.mAmInternal.enforceCallingPermission("android.permission.BIND_VOICE_INTERACTION", "startAssistantActivity()");
        int handleIncomingUser = handleIncomingUser(i, i2, i3, "startAssistantActivity");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getActivityStartController().obtainStarter(intent, "startAssistantActivity").setCallingUid(i2).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str3).setActivityOptions(createSafeActivityOptionsWithBalAllowed(bundle)).setUserId(handleIncomingUser).setBackgroundStartPrivileges(android.app.BackgroundStartPrivileges.ALLOW_BAL).execute();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void startRecentsActivity(android.content.Intent intent, long j, @android.annotation.Nullable android.view.IRecentsAnimationRunner iRecentsAnimationRunner) {
        enforceTaskPermission("startRecentsActivity()");
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.RecentsAnimation recentsAnimation = new com.android.server.wm.RecentsAnimation(this, this.mTaskSupervisor, getActivityStartController(), this.mWindowManager, intent, this.mRecentTasks.getRecentsComponent(), this.mRecentTasks.getRecentsComponentFeatureId(), this.mRecentTasks.getRecentsComponentUid(), getProcessController(callingPid, callingUid));
                    if (iRecentsAnimationRunner == null) {
                        recentsAnimation.preloadRecentsActivity();
                    } else {
                        recentsAnimation.startRecentsActivity(iRecentsAnimationRunner, j);
                    }
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

    public final int startActivityFromRecents(int i, android.os.Bundle bundle) {
        this.mAmInternal.enforceCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "startActivityFromRecents()");
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.wm.SafeActivityOptions fromBundle = com.android.server.wm.SafeActivityOptions.fromBundle(bundle);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mTaskSupervisor.startActivityFromRecents(callingPid, callingUid, i, fromBundle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int startActivityFromGameSession(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, int i, int i2, android.content.Intent intent, int i3, int i4) {
        if (checkCallingPermission("android.permission.MANAGE_GAME_ACTIVITY") != 0) {
            java.lang.String str3 = "Permission Denial: startActivityFromGameSession() from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires android.permission.MANAGE_GAME_ACTIVITY";
            android.util.Slog.w("ActivityTaskManager", str3);
            throw new java.lang.SecurityException(str3);
        }
        assertPackageMatchesCallingUid(str);
        android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
        makeBasic.setLaunchTaskId(i3);
        int handleIncomingUser = handleIncomingUser(i, i2, i4, "startActivityFromGameSession");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getActivityStartController().obtainStarter(intent, "startActivityFromGameSession").setCaller(iApplicationThread).setCallingUid(i2).setCallingPid(i).setCallingPackage(intent.getPackage()).setCallingFeatureId(str2).setUserId(handleIncomingUser).setActivityOptions(makeBasic.toBundle()).setRealCallingUid(android.os.Binder.getCallingUid()).execute();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.window.BackNavigationInfo startBackNavigation(android.os.RemoteCallback remoteCallback, android.window.BackAnimationAdapter backAnimationAdapter) {
        this.mAmInternal.enforceCallingPermission("android.permission.START_TASKS_FROM_RECENTS", "startBackNavigation()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mBackNavigationController.startBackNavigation(remoteCallback, backAnimationAdapter);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isActivityStartAllowedOnDisplay(int i, android.content.Intent intent, java.lang.String str, int i2) {
        boolean canPlaceEntityOnDisplay;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.ActivityInfo resolveActivityInfoForIntent = resolveActivityInfoForIntent(intent, str, i2, callingUid, callingPid);
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    canPlaceEntityOnDisplay = this.mTaskSupervisor.canPlaceEntityOnDisplay(i, callingPid, callingUid, resolveActivityInfoForIntent);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return canPlaceEntityOnDisplay;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    android.content.pm.ActivityInfo resolveActivityInfoForIntent(android.content.Intent intent, java.lang.String str, int i, int i2, int i3) {
        return this.mAmInternal.getActivityInfoForUser(this.mTaskSupervisor.resolveActivity(intent, str, 0, null, i, com.android.server.wm.ActivityStarter.computeResolveFilterUid(i2, i2, com.android.server.am.ProcessList.INVALID_ADJ), i3), i);
    }

    public android.app.IActivityClientController getActivityClientController() {
        return this.mActivityClientController;
    }

    void applyUpdateLockStateLocked(final com.android.server.wm.ActivityRecord activityRecord) {
        final boolean z = activityRecord != null && activityRecord.immersive;
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskManagerService.this.lambda$applyUpdateLockStateLocked$0(z, activityRecord);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyUpdateLockStateLocked$0(boolean z, com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mUpdateLock.isHeld() != z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IMMERSIVE, 6075150529915862250L, 0, null, java.lang.String.valueOf(z), java.lang.String.valueOf(activityRecord));
            if (z) {
                this.mUpdateLock.acquire();
            } else {
                this.mUpdateLock.release();
            }
        }
    }

    public boolean isTopActivityImmersive() {
        enforceNotIsolatedCaller("isTopActivityImmersive");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                boolean z = false;
                if (topDisplayFocusedRootTask == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                com.android.server.wm.ActivityRecord activityRecord = topDisplayFocusedRootTask.topRunningActivity();
                if (activityRecord != null && activityRecord.immersive) {
                    z = true;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getFrontActivityScreenCompatMode() {
        enforceNotIsolatedCaller("getFrontActivityScreenCompatMode");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                com.android.server.wm.ActivityRecord activityRecord = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.topRunningActivity() : null;
                if (activityRecord == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return -3;
                }
                int computeCompatModeLocked = this.mCompatModePackages.computeCompatModeLocked(activityRecord.info.applicationInfo);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return computeCompatModeLocked;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setFrontActivityScreenCompatMode(int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.SET_SCREEN_COMPATIBILITY", "setFrontActivityScreenCompatMode");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                com.android.server.wm.ActivityRecord activityRecord = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.topRunningActivity() : null;
                if (activityRecord == null) {
                    android.util.Slog.w("ActivityTaskManager", "setFrontActivityScreenCompatMode failed: no top activity");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    this.mCompatModePackages.setPackageScreenCompatModeLocked(activityRecord.info.applicationInfo, i);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public android.app.ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo() throws android.os.RemoteException {
        enforceTaskPermission("getFocusedRootTaskInfo()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                    if (topDisplayFocusedRootTask != null) {
                        android.app.ActivityTaskManager.RootTaskInfo rootTaskInfo = this.mRootWindowContainer.getRootTaskInfo(topDisplayFocusedRootTask.mTaskId);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return rootTaskInfo;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setFocusedRootTask(int i) {
        enforceTaskPermission("setFocusedRootTask()");
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -4356952232698761083L, 1, null, java.lang.Long.valueOf(i));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task rootTask = this.mRootWindowContainer.getRootTask(i);
                    if (rootTask == null) {
                        android.util.Slog.w("ActivityTaskManager", "setFocusedRootTask: No task with id=" + i);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.ActivityRecord activityRecord = rootTask.topRunningActivity();
                    if (activityRecord != null && activityRecord.moveFocusableActivityToTop("setFocusedRootTask")) {
                        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setFocusedTask(int i) {
        enforceTaskPermission("setFocusedTask()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    setFocusedTask(i, null);
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

    public void focusTopTask(int i) {
        enforceTaskPermission("focusTopTask()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(i);
                    if (displayContent == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.Task task = displayContent.getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$focusTopTask$1;
                            lambda$focusTopTask$1 = com.android.server.wm.ActivityTaskManagerService.lambda$focusTopTask$1((com.android.server.wm.Task) obj);
                            return lambda$focusTopTask$1;
                        }
                    }, true);
                    if (task == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        setFocusedTask(task.mTaskId, null);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$focusTopTask$1(com.android.server.wm.Task task) {
        return task.isLeafTask() && task.isTopActivityFocusable();
    }

    void setFocusedTask(int i, com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivityRecord activityRecord2;
        com.android.server.wm.Transition transition;
        com.android.server.wm.TaskFragment taskFragment;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, 301842347780487555L, 1, null, java.lang.Long.valueOf(i), java.lang.String.valueOf(activityRecord));
        com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
        if (anyTaskForId == null || (activityRecord2 = anyTaskForId.topRunningActivityLocked()) == null) {
            return;
        }
        if ((activityRecord == null || activityRecord2 == activityRecord) && activityRecord2.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && activityRecord2 == this.mRootWindowContainer.getTopResumedActivity()) {
            setLastResumedActivityUncheckLocked(activityRecord2, "setFocusedTask-alreadyTop");
            return;
        }
        if (getTransitionController().isCollecting() || !getTransitionController().isShellTransitionsEnabled()) {
            transition = null;
        } else {
            transition = getTransitionController().createTransition(3);
        }
        if (transition != null) {
            transition.setReady(anyTaskForId, true);
        }
        boolean moveFocusableActivityToTop = activityRecord2.moveFocusableActivityToTop("setFocusedTask");
        if (moveFocusableActivityToTop) {
            if (transition != null) {
                getTransitionController().requestStartTransition(transition, null, null, null);
            }
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        } else if (activityRecord != null && activityRecord.isFocusable() && (taskFragment = activityRecord.getTaskFragment()) != null && taskFragment.isEmbedded()) {
            activityRecord.getDisplayContent().setFocusedApp(activityRecord);
            this.mWindowManager.updateFocusedWindowLocked(0, true);
        }
        if (transition != null && !moveFocusableActivityToTop) {
            transition.abort();
        }
    }

    public boolean removeTask(int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.REMOVE_TASKS", "removeTask()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
                    if (anyTaskForId != null) {
                        if (anyTaskForId.isLeafTask()) {
                            this.mTaskSupervisor.removeTask(anyTaskForId, true, true, "remove-task");
                        } else {
                            this.mTaskSupervisor.removeRootTask(anyTaskForId);
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                    android.util.Slog.w("ActivityTaskManager", "removeTask: No task remove with id=" + i);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void removeAllVisibleRecentTasks() {
        this.mAmInternal.enforceCallingPermission("android.permission.REMOVE_TASKS", "removeAllVisibleRecentTasks()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    getRecentTasks().removeAllVisibleTasks(this.mAmInternal.getCurrentUserId());
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public android.graphics.Rect getTaskBounds(int i) {
        enforceTaskPermission("getTaskBounds()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.graphics.Rect rect = new android.graphics.Rect();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
                    if (anyTaskForId != null) {
                        if (anyTaskForId.getParent() != null) {
                            rect.set(anyTaskForId.getBounds());
                        } else if (anyTaskForId.mLastNonFullscreenBounds != null) {
                            rect.set(anyTaskForId.mLastNonFullscreenBounds);
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return rect;
                    }
                    android.util.Slog.w("ActivityTaskManager", "getTaskBounds: taskId=" + i + " not found");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return rect;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.app.ActivityManager.TaskDescription getTaskDescription(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                enforceTaskPermission("getTaskDescription()");
                com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
                if (anyTaskForId != null) {
                    android.app.ActivityManager.TaskDescription taskDescription = anyTaskForId.getTaskDescription();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return taskDescription;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setLocusId(android.content.LocusId locusId, android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    isInRootTaskLocked.setLocusId(locusId);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    com.android.server.uri.NeededUriGrants collectGrants(android.content.Intent intent, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord != null) {
            return this.mUgmInternal.checkGrantUriPermissionFromIntent(intent, android.os.Binder.getCallingUid(), activityRecord.packageName, activityRecord.mUserId);
        }
        return null;
    }

    public void unhandledBack() {
        this.mAmInternal.enforceCallingPermission("android.permission.FORCE_BACK", "unhandledBack()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                    if (topDisplayFocusedRootTask != null) {
                        topDisplayFocusedRootTask.unhandledBackLocked();
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void moveTaskToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str, int i, int i2, android.os.Bundle bundle) {
        this.mAmInternal.enforceCallingPermission("android.permission.REORDER_TASKS", "moveTaskToFront()");
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 7095858131234795548L, 1, null, java.lang.Long.valueOf(i));
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                moveTaskToFrontLocked(iApplicationThread, str, i, i2, com.android.server.wm.SafeActivityOptions.fromBundle(bundle));
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void moveTaskToFrontLocked(@android.annotation.Nullable android.app.IApplicationThread iApplicationThread, @android.annotation.Nullable java.lang.String str, int i, int i2, com.android.server.wm.SafeActivityOptions safeActivityOptions) {
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        assertPackageMatchesCallingUid(str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStart = this.mTaskSupervisor.getBackgroundActivityLaunchController().checkBackgroundActivityStart(callingUid, callingPid, str, -1, -1, iApplicationThread != null ? getProcessController(iApplicationThread) : null, null, android.app.BackgroundStartPrivileges.NONE, null, null, null);
        if (checkBackgroundActivityStart.blocks() && !isBackgroundActivityStartsEnabled()) {
            android.util.Slog.w("ActivityTaskManager", "moveTaskToFront blocked: " + checkBackgroundActivityStart);
            return;
        }
        try {
            com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
            if (anyTaskForId == null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -4458288191054594222L, 1, null, java.lang.Long.valueOf(i));
                com.android.server.wm.SafeActivityOptions.abort(safeActivityOptions);
            } else if (!getLockTaskController().isLockTaskModeViolation(anyTaskForId)) {
                this.mTaskSupervisor.findTaskToMoveToFront(anyTaskForId, i2, safeActivityOptions != null ? safeActivityOptions.getOptions(this.mTaskSupervisor) : null, "moveTaskToFront", false);
            } else {
                android.util.Slog.e("ActivityTaskManager", "moveTaskToFront: Attempt to violate Lock Task Mode");
                com.android.server.wm.SafeActivityOptions.abort(safeActivityOptions);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isSameApp(int i, @android.annotation.Nullable java.lang.String str) {
        if (i != 0 && i != 1000) {
            return this.mPmInternal.isSameApp(str, i, android.os.UserHandle.getUserId(i));
        }
        return true;
    }

    void assertPackageMatchesCallingUid(@android.annotation.Nullable java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (isSameApp(callingUid, str)) {
            return;
        }
        java.lang.String str2 = "Permission Denial: package=" + str + " does not belong to uid=" + callingUid;
        android.util.Slog.w("ActivityTaskManager", str2);
        throw new java.lang.SecurityException(str2);
    }

    int getBalAppSwitchesState() {
        return this.mAppSwitchesState;
    }

    public void registerAnrController(android.app.AnrController anrController) {
        synchronized (this.mAnrController) {
            this.mAnrController.add(anrController);
        }
    }

    public void unregisterAnrController(android.app.AnrController anrController) {
        synchronized (this.mAnrController) {
            this.mAnrController.remove(anrController);
        }
    }

    @android.annotation.Nullable
    public android.app.AnrController getAnrController(android.content.pm.ApplicationInfo applicationInfo) {
        java.util.ArrayList arrayList;
        android.app.AnrController anrController = null;
        if (applicationInfo == null || applicationInfo.packageName == null) {
            return null;
        }
        synchronized (this.mAnrController) {
            arrayList = new java.util.ArrayList(this.mAnrController);
        }
        java.lang.String str = applicationInfo.packageName;
        int i = applicationInfo.uid;
        java.util.Iterator it = arrayList.iterator();
        long j = 0;
        while (it.hasNext()) {
            android.app.AnrController anrController2 = (android.app.AnrController) it.next();
            long anrDelayMillis = anrController2.getAnrDelayMillis(str, i);
            if (anrDelayMillis > 0 && anrDelayMillis > j) {
                anrController = anrController2;
                j = anrDelayMillis;
            }
        }
        return anrController;
    }

    public void setActivityController(android.app.IActivityController iActivityController, boolean z) {
        this.mAmInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "setActivityController()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mController = iActivityController;
                this.mControllerIsAMonkey = z;
                com.android.server.Watchdog.getInstance().setActivityController(iActivityController);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean isControllerAMonkey() {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                z = this.mController != null && this.mControllerIsAMonkey;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i) {
        return getTasks(i, false, false, -1);
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2) {
        return getTasks(i, z, z2, -1);
    }

    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getTasks(int i, boolean z, boolean z2, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int i3 = (z ? 1 : 0) | (z2 ? 8 : 0) | (isCrossUserAllowed(callingPid, callingUid) ? 4 : 0);
        int[] profileIds = getUserManager().getProfileIds(android.os.UserHandle.getUserId(callingUid), true);
        android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
        for (int i4 : profileIds) {
            arraySet.add(java.lang.Integer.valueOf(i4));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRootWindowContainer.getRunningTasks(i, arrayList, i3 | (isGetTasksAllowed("getTasks", callingPid, callingUid) ? 2 : 0), callingUid, arraySet, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return arrayList;
    }

    public void moveTaskToRootTask(int i, int i2, boolean z) {
        enforceTaskPermission("moveTaskToRootTask()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i);
                    if (anyTaskForId == null) {
                        android.util.Slog.w("ActivityTaskManager", "moveTaskToRootTask: No task for id=" + i);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -1136891560663761442L, 53, null, java.lang.Long.valueOf(i), java.lang.Long.valueOf(i2), java.lang.Boolean.valueOf(z));
                    com.android.server.wm.Task rootTask = this.mRootWindowContainer.getRootTask(i2);
                    if (rootTask == null) {
                        throw new java.lang.IllegalStateException("moveTaskToRootTask: No rootTask for rootTaskId=" + i2);
                    }
                    if (rootTask.isActivityTypeStandardOrUndefined()) {
                        anyTaskForId.reparent(rootTask, z, 1, true, false, "moveTaskToRootTask");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    throw new java.lang.IllegalArgumentException("moveTaskToRootTask: Attempt to move task " + i + " to rootTask " + i2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void removeRootTasksInWindowingModes(int[] iArr) {
        enforceTaskPermission("removeRootTasksInWindowingModes()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mRootWindowContainer.removeRootTasksInWindowingModes(iArr);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void removeRootTasksWithActivityTypes(int[] iArr) {
        enforceTaskPermission("removeRootTasksWithActivityTypes()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mRootWindowContainer.removeRootTasksWithActivityTypes(iArr);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public android.content.pm.ParceledListSlice<android.app.ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, int i3) {
        android.content.pm.ParceledListSlice<android.app.ActivityManager.RecentTaskInfo> recentTasks;
        int callingUid = android.os.Binder.getCallingUid();
        int handleIncomingUser = handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i3, "getRecentTasks");
        boolean isGetTasksAllowed = isGetTasksAllowed("getRecentTasks", android.os.Binder.getCallingPid(), callingUid);
        if (!this.mAmInternal.isUserRunning(handleIncomingUser, 4)) {
            android.util.Slog.i("ActivityTaskManager", "User " + handleIncomingUser + " is locked. Cannot load recents");
            return android.content.pm.ParceledListSlice.emptyList();
        }
        this.mRecentTasks.loadRecentTasksIfNeeded(handleIncomingUser);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                recentTasks = this.mRecentTasks.getRecentTasks(i, i2, isGetTasksAllowed, handleIncomingUser, callingUid);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return recentTasks;
    }

    public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos() {
        java.util.ArrayList<android.app.ActivityTaskManager.RootTaskInfo> allRootTaskInfos;
        enforceTaskPermission("getAllRootTaskInfos()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    allRootTaskInfos = this.mRootWindowContainer.getAllRootTaskInfos(-1);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return allRootTaskInfos;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfo(int i, int i2) {
        android.app.ActivityTaskManager.RootTaskInfo rootTaskInfo;
        enforceTaskPermission("getRootTaskInfo()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    rootTaskInfo = this.mRootWindowContainer.getRootTaskInfo(i, i2);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return rootTaskInfo;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.app.ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int i) {
        java.util.ArrayList<android.app.ActivityTaskManager.RootTaskInfo> allRootTaskInfos;
        enforceTaskPermission("getAllRootTaskInfosOnDisplay()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    allRootTaskInfos = this.mRootWindowContainer.getAllRootTaskInfos(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return allRootTaskInfos;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.app.ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int i, int i2, int i3) {
        android.app.ActivityTaskManager.RootTaskInfo rootTaskInfo;
        enforceTaskPermission("getRootTaskInfoOnDisplay()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    rootTaskInfo = this.mRootWindowContainer.getRootTaskInfo(i, i2, i3);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return rootTaskInfo;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void cancelRecentsAnimation(boolean z) {
        int i;
        enforceTaskPermission("cancelRecentsAnimation()");
        long callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowManagerService windowManagerService = this.mWindowManager;
                    if (z) {
                        i = 2;
                    } else {
                        i = 0;
                    }
                    windowManagerService.cancelRecentsAnimation(i, "cancelRecentsAnimation/uid=" + callingUid);
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

    public void startSystemLockTaskMode(int i) {
        enforceTaskPermission("startSystemLockTaskMode");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
                    if (anyTaskForId == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    anyTaskForId.getRootTask().moveToFront("startSystemLockTaskMode");
                    startLockTaskMode(anyTaskForId, true);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void stopSystemLockTaskMode() throws android.os.RemoteException {
        enforceTaskPermission("stopSystemLockTaskMode");
        stopLockTaskModeInternal(null, true);
    }

    void startLockTaskMode(@android.annotation.Nullable com.android.server.wm.Task task, boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, 6954122272402912822L, 0, null, java.lang.String.valueOf(task));
        if (task == null || task.mLockTaskAuth == 0) {
            return;
        }
        com.android.server.wm.Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null || task != topDisplayFocusedRootTask.getTopMostTask()) {
            throw new java.lang.IllegalArgumentException("Invalid task, not in foreground");
        }
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            getLockTaskController().startLockTaskMode(task, z, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void stopLockTaskModeInternal(@android.annotation.Nullable android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.Task task;
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (iBinder == null) {
                    task = null;
                } else {
                    try {
                        com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                        if (forTokenLocked != null) {
                            task = forTokenLocked.getTask();
                        } else {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                getLockTaskController().stopLockTaskMode(task, z, callingUid);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService("telecom");
                if (telecomManager != null) {
                    telecomManager.showInCallScreen(false);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void updateLockTaskPackages(int i, java.lang.String[] strArr) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000) {
            this.mAmInternal.enforceCallingPermission("android.permission.UPDATE_LOCK_TASK_PACKAGES", "updateLockTaskPackages()");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, -829638795650515884L, 1, null, java.lang.Long.valueOf(i), java.lang.String.valueOf(java.util.Arrays.toString(strArr)));
                    getLockTaskController().updateLockTaskPackages(i, strArr);
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

    public boolean isInLockTaskMode() {
        return getLockTaskModeState() != 0;
    }

    public int getLockTaskModeState() {
        return getLockTaskController().getLockTaskModeState();
    }

    public java.util.List<android.os.IBinder> getAppTasks(java.lang.String str) {
        assertPackageMatchesCallingUid(str);
        return getAppTasks(str, android.os.Binder.getCallingUid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.os.IBinder> getAppTasks(java.lang.String str, int i) {
        java.util.ArrayList<android.os.IBinder> appTasksList;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    appTasksList = this.mRecentTasks.getAppTasksList(i, str);
                } finally {
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return appTasksList;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void finishVoiceTask(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mRootWindowContainer.finishVoiceTask(iVoiceInteractionSession);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void reportAssistContextExtras(android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, android.net.Uri uri) {
        android.os.Bundle bundle2;
        com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras pendingAssistExtras = (com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras) iBinder;
        synchronized (pendingAssistExtras) {
            try {
                pendingAssistExtras.result = bundle;
                pendingAssistExtras.structure = assistStructure;
                pendingAssistExtras.content = assistContent;
                if (uri != null) {
                    pendingAssistExtras.extras.putParcelable("android.intent.extra.REFERRER", uri);
                }
                if (pendingAssistExtras.activity.isAttached()) {
                    if (assistStructure != null) {
                        assistStructure.setTaskId(pendingAssistExtras.activity.getTask().mTaskId);
                        assistStructure.setActivityComponent(pendingAssistExtras.activity.mActivityComponent);
                        assistStructure.setHomeActivity(pendingAssistExtras.isHome);
                    }
                    pendingAssistExtras.haveResult = true;
                    pendingAssistExtras.notifyAll();
                    if (pendingAssistExtras.intent == null && pendingAssistExtras.receiver == null) {
                        return;
                    }
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            buildAssistBundleLocked(pendingAssistExtras, bundle);
                            boolean remove = this.mPendingAssistExtras.remove(pendingAssistExtras);
                            this.mUiHandler.removeCallbacks(pendingAssistExtras);
                            if (!remove) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            android.app.IAssistDataReceiver iAssistDataReceiver = pendingAssistExtras.receiver;
                            if (iAssistDataReceiver == null) {
                                bundle2 = null;
                            } else {
                                bundle2 = new android.os.Bundle();
                                bundle2.putInt(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_TASK_ID, pendingAssistExtras.activity.getTask().mTaskId);
                                bundle2.putBinder(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_ACTIVITY_ID, pendingAssistExtras.activity.assistToken);
                                bundle2.putBundle("data", pendingAssistExtras.extras);
                                bundle2.putParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_STRUCTURE, pendingAssistExtras.structure);
                                bundle2.putParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT, pendingAssistExtras.content);
                                bundle2.putBundle(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_RECEIVER_EXTRAS, pendingAssistExtras.receiverExtras);
                            }
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            if (iAssistDataReceiver != null) {
                                try {
                                    iAssistDataReceiver.onHandleAssistData(bundle2);
                                    return;
                                } catch (android.os.RemoteException e) {
                                    return;
                                }
                            }
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                pendingAssistExtras.intent.replaceExtras(pendingAssistExtras.extras);
                                pendingAssistExtras.intent.setFlags(872415232);
                                this.mInternal.closeSystemDialogs(com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_ASSIST);
                                try {
                                    this.mContext.startActivityAsUser(pendingAssistExtras.intent, new android.os.UserHandle(pendingAssistExtras.userHandle));
                                } catch (android.content.ActivityNotFoundException e2) {
                                    android.util.Slog.w("ActivityTaskManager", "No activity to handle assist action.", e2);
                                }
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            } finally {
            }
        }
    }

    public int addAppTask(android.os.IBinder iBinder, android.content.Intent intent, android.app.ActivityManager.TaskDescription taskDescription, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        throw new java.lang.IllegalArgumentException("Activity does not exist; token=" + iBinder);
                    }
                    android.content.ComponentName component = intent.getComponent();
                    if (component == null) {
                        throw new java.lang.IllegalArgumentException("Intent " + intent + " must specify explicit component");
                    }
                    if (bitmap.getWidth() != this.mThumbnailWidth || bitmap.getHeight() != this.mThumbnailHeight) {
                        throw new java.lang.IllegalArgumentException("Bad thumbnail size: got " + bitmap.getWidth() + "x" + bitmap.getHeight() + ", require " + this.mThumbnailWidth + "x" + this.mThumbnailHeight);
                    }
                    if (intent.getSelector() != null) {
                        intent.setSelector(null);
                    }
                    if (intent.getSourceBounds() != null) {
                        intent.setSourceBounds(null);
                    }
                    if ((intent.getFlags() & 524288) != 0 && (intent.getFlags() & 8192) == 0) {
                        intent.addFlags(8192);
                    }
                    android.content.pm.ActivityInfo activityInfo = android.app.AppGlobals.getPackageManager().getActivityInfo(component, 1024L, android.os.UserHandle.getUserId(callingUid));
                    if (activityInfo == null || activityInfo.applicationInfo.uid != callingUid) {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("Can't add task for another application: target uid=");
                        sb.append(activityInfo == null ? -1 : activityInfo.applicationInfo.uid);
                        sb.append(", calling uid=");
                        sb.append(callingUid);
                        android.util.Slog.e("ActivityTaskManager", sb.toString());
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    com.android.server.wm.Task rootTask = isInRootTaskLocked.getRootTask();
                    com.android.server.wm.Task build = new com.android.server.wm.Task.Builder(this).setWindowingMode(rootTask.getWindowingMode()).setActivityType(rootTask.getActivityType()).setActivityInfo(activityInfo).setIntent(intent).setTaskId(rootTask.getDisplayArea().getNextRootTaskId()).build();
                    if (!this.mRecentTasks.addToBottom(build)) {
                        rootTask.removeChild(build, "addAppTask");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return -1;
                    }
                    build.getTaskDescription().copyFrom(taskDescription);
                    int i = build.mTaskId;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.graphics.Point getAppTaskThumbnailSize() {
        android.graphics.Point point;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                point = new android.graphics.Point(this.mThumbnailWidth, this.mThumbnailHeight);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return point;
    }

    public void setTaskResizeable(int i, int i2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
                if (anyTaskForId == null) {
                    android.util.Slog.w("ActivityTaskManager", "setTaskResizeable: taskId=" + i + " not found");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                anyTaskForId.setResizeMode(i2);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void resizeTask(int i, final android.graphics.Rect rect, final int i2) {
        enforceTaskPermission("resizeTask()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
                    if (anyTaskForId == null) {
                        android.util.Slog.w("ActivityTaskManager", "resizeTask: taskId=" + i + " not found");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (!anyTaskForId.getWindowConfiguration().canResizeTask()) {
                        android.util.Slog.w("ActivityTaskManager", "resizeTask not allowed on task=" + anyTaskForId);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    final boolean z = (i2 & 1) != 0;
                    if (!getTransitionController().isShellTransitionsEnabled()) {
                        anyTaskForId.resize(rect, i2, z);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(6, 0, getTransitionController(), this.mWindowManager.mSyncEngine);
                        getTransitionController().startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda5
                            @Override // com.android.server.wm.TransitionController.OnStartCollect
                            public final void onCollectStarted(boolean z2) {
                                com.android.server.wm.ActivityTaskManagerService.this.lambda$resizeTask$2(anyTaskForId, transition, rect, i2, z, z2);
                            }
                        });
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resizeTask$2(com.android.server.wm.Task task, com.android.server.wm.Transition transition, android.graphics.Rect rect, int i, boolean z, boolean z2) {
        if (z2 && !task.getWindowConfiguration().canResizeTask()) {
            android.util.Slog.w("ActivityTaskManager", "resizeTask not allowed on task=" + task);
            transition.abort();
            return;
        }
        getTransitionController().requestStartTransition(transition, task, null, null);
        getTransitionController().collect(task);
        task.resize(rect, i, z);
        transition.setReady(task, true);
    }

    public void releaseSomeActivities(android.app.IApplicationThread iApplicationThread) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    getProcessController(iApplicationThread).releaseSomeActivities("low-mem");
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setLockScreenShown(final boolean z, final boolean z2) {
        if (checkCallingPermission("android.permission.DEVICE_POWER") != 0) {
            throw new java.lang.SecurityException("Requires permission android.permission.DEVICE_POWER");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (this.mKeyguardShown != z) {
                    this.mKeyguardShown = z;
                    this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            ((android.app.ActivityManagerInternal) obj).reportCurKeyguardUsageEvent(((java.lang.Boolean) obj2).booleanValue());
                        }
                    }, this.mAmInternal, java.lang.Boolean.valueOf(z)));
                }
                if ((this.mDemoteTopAppReasons & 1) != 0) {
                    this.mDemoteTopAppReasons &= -2;
                    if (this.mTopApp != null) {
                        this.mTopApp.scheduleUpdateOomAdj();
                    }
                }
                try {
                    android.os.Trace.traceBegin(32L, "setLockScreenShown");
                    this.mRootWindowContainer.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.ActivityTaskManagerService.this.lambda$setLockScreenShown$3(z, z2, (com.android.server.wm.DisplayContent) obj);
                        }
                    });
                    maybeHideLockedProfileActivityLocked();
                } finally {
                    android.os.Trace.traceEnd(32L);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskManagerService.this.lambda$setLockScreenShown$4(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLockScreenShown$3(boolean z, boolean z2, com.android.server.wm.DisplayContent displayContent) {
        this.mKeyguardController.setKeyguardShown(displayContent.getDisplayId(), z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLockScreenShown$4(boolean z) {
        for (int size = this.mScreenObservers.size() - 1; size >= 0; size--) {
            this.mScreenObservers.get(size).onKeyguardStateChanged(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mGlobalLock"})
    public void maybeHideLockedProfileActivityLocked() {
        android.content.pm.UserInfo userInfo;
        if (this.mKeyguardController.isKeyguardLocked(0) && this.mLastResumedActivity != null && (userInfo = getUserManager().getUserInfo(this.mLastResumedActivity.mUserId)) != null && userInfo.isManagedProfile() && this.mAmInternal.shouldConfirmCredentials(this.mLastResumedActivity.mUserId)) {
            this.mInternal.startHomeActivity(this.mAmInternal.getCurrentUserId(), "maybeHideLockedProfileActivityLocked");
        }
    }

    public void onScreenAwakeChanged(final boolean z) {
        com.android.server.wm.WindowProcessController process;
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskManagerService.this.lambda$onScreenAwakeChanged$5(z);
            }
        });
        if (z) {
            return;
        }
        synchronized (this.mGlobalLockWithoutBoost) {
            try {
                this.mDemoteTopAppReasons &= -2;
                com.android.server.wm.WindowState notificationShade = this.mRootWindowContainer.getDefaultDisplay().getDisplayPolicy().getNotificationShade();
                process = notificationShade != null ? notificationShade.getProcess() : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        setProcessAnimatingWhileDozing(process);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onScreenAwakeChanged$5(boolean z) {
        for (int size = this.mScreenObservers.size() - 1; size >= 0; size--) {
            this.mScreenObservers.get(size).onAwakeStateChanged(z);
        }
    }

    void setProcessAnimatingWhileDozing(com.android.server.wm.WindowProcessController windowProcessController) {
        if (windowProcessController == null) {
            return;
        }
        windowProcessController.setRunningAnimationUnsafe();
        this.mH.sendMessage(this.mH.obtainMessage(5, windowProcessController));
        this.mH.removeMessages(6, windowProcessController);
        this.mH.sendMessageDelayed(this.mH.obtainMessage(6, windowProcessController), DOZE_ANIMATING_STATE_RETAIN_TIME_MS);
        android.os.Trace.instant(32L, "requestWakefulnessAnimating");
    }

    public android.graphics.Bitmap getTaskDescriptionIcon(java.lang.String str, int i) {
        int handleIncomingUser = handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getTaskDescriptionIcon");
        if (!new java.io.File(com.android.server.wm.TaskPersister.getUserImagesDir(handleIncomingUser), new java.io.File(str).getName()).getPath().equals(str) || !str.contains("_activity_icon_")) {
            throw new java.lang.IllegalArgumentException("Bad file path: " + str + " passed for userId " + handleIncomingUser);
        }
        return this.mRecentTasks.getTaskDescriptionIcon(str);
    }

    public void moveRootTaskToDisplay(int i, int i2) {
        this.mAmInternal.enforceCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "moveRootTaskToDisplay()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 893763316922465955L, 5, null, java.lang.Long.valueOf(i), java.lang.Long.valueOf(i2));
                    this.mRootWindowContainer.moveRootTaskToDisplay(i, i2, true);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) {
        enforceTaskPermission("registerTaskStackListener()");
        this.mTaskChangeNotificationController.registerTaskStackListener(iTaskStackListener);
    }

    public void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) {
        enforceTaskPermission("unregisterTaskStackListener()");
        this.mTaskChangeNotificationController.unregisterTaskStackListener(iTaskStackListener);
    }

    public boolean requestAssistContextExtras(int i, android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, boolean z, boolean z2) {
        return enqueueAssistContext(i, null, null, iAssistDataReceiver, bundle, iBinder, z, z2, android.os.UserHandle.getCallingUserId(), null, DOZE_ANIMATING_STATE_RETAIN_TIME_MS, 0) != null;
    }

    public boolean requestAssistDataForTask(android.app.IAssistDataReceiver iAssistDataReceiver, int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        this.mAmInternal.enforceCallingPermission("android.permission.GET_TOP_ACTIVITY_INFO", "requestAssistDataForTask()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = this.mInternal.getAttachedNonFinishingActivityForTask(i, null);
            if (attachedNonFinishingActivityForTask == null) {
                android.util.Log.e("ActivityTaskManager", "Could not find activity for task " + i);
                return false;
            }
            com.android.server.am.AssistDataRequester assistDataRequester = new com.android.server.am.AssistDataRequester(this.mContext, this.mWindowManager, getAppOpsManager(), new com.android.server.wm.AssistDataReceiverProxy(iAssistDataReceiver, str), new java.lang.Object(), 49, -1);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(attachedNonFinishingActivityForTask.getActivityToken());
            assistDataRequester.requestAssistData(arrayList, true, false, false, true, false, true, android.os.Binder.getCallingUid(), str, str2);
            return true;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean requestAutofillData(android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, int i) {
        return enqueueAssistContext(2, null, null, iAssistDataReceiver, bundle, iBinder, true, true, android.os.UserHandle.getCallingUserId(), null, DOZE_ANIMATING_STATE_RETAIN_TIME_MS, i) != null;
    }

    public android.os.Bundle getAssistContextExtras(int i) {
        com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras enqueueAssistContext = enqueueAssistContext(i, null, null, null, null, null, true, true, android.os.UserHandle.getCallingUserId(), null, 500L, 0);
        if (enqueueAssistContext == null) {
            return null;
        }
        synchronized (enqueueAssistContext) {
            while (!enqueueAssistContext.haveResult) {
                try {
                    enqueueAssistContext.wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                buildAssistBundleLocked(enqueueAssistContext, enqueueAssistContext.result);
                this.mPendingAssistExtras.remove(enqueueAssistContext);
                this.mUiHandler.removeCallbacks(enqueueAssistContext);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return enqueueAssistContext.extras;
    }

    private static int checkCallingPermission(java.lang.String str) {
        return checkPermission(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
    }

    boolean checkCanCloseSystemDialogs(int i, int i2, @android.annotation.Nullable java.lang.String str) {
        com.android.server.wm.WindowProcessController process;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                process = this.mProcessMap.getProcess(i);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (str == null && process != null) {
            str = process.mInfo.packageName;
        }
        java.lang.String str2 = "(pid=" + i + ", uid=" + i2 + ")";
        if (str != null) {
            str2 = str + " " + str2;
        }
        if (canCloseSystemDialogs(i, i2)) {
            return true;
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(174664365L, i2)) {
            throw new java.lang.SecurityException("Permission Denial: android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from " + str2 + " requires android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS.");
        }
        if (android.app.compat.CompatChanges.isChangeEnabled(174664120L, i2)) {
            android.util.Slog.e("ActivityTaskManager", "Permission Denial: android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from " + str2 + " requires android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS, dropping broadcast.");
            return false;
        }
        android.util.Slog.w("ActivityTaskManager", "android.intent.action.CLOSE_SYSTEM_DIALOGS broadcast from " + str2 + " will require android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS in future builds.");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canCloseSystemDialogs(int i, int i2) {
        if (checkPermission("android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS", i, i2) == 0) {
            return true;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.util.ArraySet<com.android.server.wm.WindowProcessController> processes = this.mProcessMap.getProcesses(i2);
                if (processes != null) {
                    int size = processes.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        com.android.server.wm.WindowProcessController valueAt = processes.valueAt(i3);
                        int instrumentationSourceUid = valueAt.getInstrumentationSourceUid();
                        if (valueAt.isInstrumenting() && instrumentationSourceUid != -1 && checkPermission("android.permission.BROADCAST_CLOSE_SYSTEM_DIALOGS", -1, instrumentationSourceUid) == 0) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                        if (valueAt.canCloseSystemDialogsByToken()) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return true;
                        }
                    }
                }
                if (!android.app.compat.CompatChanges.isChangeEnabled(174664365L, i2)) {
                    if (this.mRootWindowContainer.hasVisibleWindowAboveButDoesNotOwnNotificationShade(i2)) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return true;
                    }
                    if (com.android.internal.util.ArrayUtils.contains(this.mAccessibilityServiceUids, i2)) {
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

    static void enforceTaskPermission(java.lang.String str) {
        if (checkCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS") == 0) {
            return;
        }
        if (checkCallingPermission("android.permission.MANAGE_ACTIVITY_STACKS") == 0) {
            android.util.Slog.w("ActivityTaskManager", "MANAGE_ACTIVITY_STACKS is deprecated, please use alternative permission: MANAGE_ACTIVITY_TASKS");
            return;
        }
        java.lang.String str2 = "Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires android.permission.MANAGE_ACTIVITY_TASKS";
        android.util.Slog.w("ActivityTaskManager", str2);
        throw new java.lang.SecurityException(str2);
    }

    static int checkPermission(java.lang.String str, int i, int i2) {
        if (str == null) {
            return -1;
        }
        return checkComponentPermission(str, i, i2, -1, true);
    }

    public static int checkComponentPermission(java.lang.String str, int i, int i2, int i3, boolean z) {
        return com.android.server.am.ActivityManagerService.checkComponentPermission(str, i, i2, i3, z);
    }

    boolean isCallerRecents(int i) {
        return this.mRecentTasks.isCallerRecents(i);
    }

    boolean isGetTasksAllowed(java.lang.String str, int i, int i2) {
        boolean z = true;
        if (isCallerRecents(i2)) {
            return true;
        }
        boolean z2 = checkPermission("android.permission.REAL_GET_TASKS", i, i2) == 0;
        if (!z2) {
            if (checkPermission("android.permission.GET_TASKS", i, i2) == 0) {
                try {
                    if (!android.app.AppGlobals.getPackageManager().isUidPrivileged(i2)) {
                        z = z2;
                    } else {
                        try {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 8392804603924461448L, 4, null, java.lang.String.valueOf(str), java.lang.Long.valueOf(i2));
                        } catch (android.os.RemoteException e) {
                            z2 = z;
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 4303745325174700522L, 4, null, java.lang.String.valueOf(str), java.lang.Long.valueOf(i2));
                            return z2;
                        }
                    }
                    z2 = z;
                } catch (android.os.RemoteException e2) {
                    z = z2;
                }
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 4303745325174700522L, 4, null, java.lang.String.valueOf(str), java.lang.Long.valueOf(i2));
        }
        return z2;
    }

    boolean isCrossUserAllowed(int i, int i2) {
        return checkPermission("android.permission.INTERACT_ACROSS_USERS", i, i2) == 0 || checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2) == 0;
    }

    private com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras enqueueAssistContext(int i, android.content.Intent intent, java.lang.String str, android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle, android.os.IBinder iBinder, boolean z, boolean z2, int i2, android.os.Bundle bundle2, long j, int i3) {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.ActivityRecord forTokenLocked;
        this.mAmInternal.enforceCallingPermission("android.permission.GET_TOP_ACTIVITY_INFO", "enqueueAssistContext()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                com.android.server.wm.ActivityRecord topNonFinishingActivity = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopNonFinishingActivity() : null;
                if (topNonFinishingActivity == null) {
                    android.util.Slog.w("ActivityTaskManager", "getAssistContextExtras failed: no top activity");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (!topNonFinishingActivity.attachedToProcess()) {
                    android.util.Slog.w("ActivityTaskManager", "getAssistContextExtras failed: no process for " + topNonFinishingActivity);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (z) {
                    if (iBinder != null && topNonFinishingActivity != (forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder))) {
                        android.util.Slog.w("ActivityTaskManager", "enqueueAssistContext failed: caller " + forTokenLocked + " is not current top " + topNonFinishingActivity);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    activityRecord = topNonFinishingActivity;
                } else {
                    com.android.server.wm.ActivityRecord forTokenLocked2 = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked2 == null) {
                        android.util.Slog.w("ActivityTaskManager", "enqueueAssistContext failed: activity for token=" + iBinder + " couldn't be found");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    if (forTokenLocked2.attachedToProcess()) {
                        activityRecord = forTokenLocked2;
                    } else {
                        android.util.Slog.w("ActivityTaskManager", "enqueueAssistContext failed: no process for " + forTokenLocked2);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                }
                android.os.Bundle bundle3 = new android.os.Bundle();
                if (bundle2 != null) {
                    bundle3.putAll(bundle2);
                }
                bundle3.putString("android.intent.extra.ASSIST_PACKAGE", activityRecord.packageName);
                bundle3.putInt("android.intent.extra.ASSIST_UID", activityRecord.app.mUid);
                com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras pendingAssistExtras = new com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras(activityRecord, bundle3, intent, str, iAssistDataReceiver, bundle, i2);
                pendingAssistExtras.isHome = activityRecord.isActivityTypeHome();
                if (z2) {
                    this.mViSessionId++;
                }
                try {
                    activityRecord.app.getThread().requestAssistContextExtras(activityRecord.token, pendingAssistExtras, i, this.mViSessionId, i3);
                    this.mPendingAssistExtras.add(pendingAssistExtras);
                    this.mUiHandler.postDelayed(pendingAssistExtras, j);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return pendingAssistExtras;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("ActivityTaskManager", "getAssistContextExtras failed: crash calling " + activityRecord);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void buildAssistBundleLocked(com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras pendingAssistExtras, android.os.Bundle bundle) {
        if (bundle != null) {
            pendingAssistExtras.extras.putBundle("android.intent.extra.ASSIST_CONTEXT", bundle);
        }
        if (pendingAssistExtras.hint != null) {
            pendingAssistExtras.extras.putBoolean(pendingAssistExtras.hint, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pendingAssistExtrasTimedOut(com.android.server.wm.ActivityTaskManagerService.PendingAssistExtras pendingAssistExtras) {
        android.app.IAssistDataReceiver iAssistDataReceiver;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPendingAssistExtras.remove(pendingAssistExtras);
                iAssistDataReceiver = pendingAssistExtras.receiver;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (iAssistDataReceiver != null) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putBundle(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_RECEIVER_EXTRAS, pendingAssistExtras.receiverExtras);
            try {
                pendingAssistExtras.receiver.onHandleAssistData(bundle);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public class PendingAssistExtras extends android.os.Binder implements java.lang.Runnable {
        public final com.android.server.wm.ActivityRecord activity;
        public final android.os.Bundle extras;
        public final java.lang.String hint;
        public final android.content.Intent intent;
        public boolean isHome;
        public final android.app.IAssistDataReceiver receiver;
        public android.os.Bundle receiverExtras;
        public final int userHandle;
        public boolean haveResult = false;
        public android.os.Bundle result = null;
        public android.app.assist.AssistStructure structure = null;
        public android.app.assist.AssistContent content = null;

        public PendingAssistExtras(com.android.server.wm.ActivityRecord activityRecord, android.os.Bundle bundle, android.content.Intent intent, java.lang.String str, android.app.IAssistDataReceiver iAssistDataReceiver, android.os.Bundle bundle2, int i) {
            this.activity = activityRecord;
            this.extras = bundle;
            this.intent = intent;
            this.hint = str;
            this.receiver = iAssistDataReceiver;
            this.receiverExtras = bundle2;
            this.userHandle = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.util.Slog.w("ActivityTaskManager", "getAssistContextExtras failed: timeout retrieving from " + this.activity);
            synchronized (this) {
                this.haveResult = true;
                notifyAll();
            }
            com.android.server.wm.ActivityTaskManagerService.this.pendingAssistExtrasTimedOut(this);
        }
    }

    public boolean isAssistDataAllowed() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = getTopDisplayFocusedRootTask();
                if (topDisplayFocusedRootTask == null || topDisplayFocusedRootTask.isActivityTypeAssistant()) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                com.android.server.wm.ActivityRecord topNonFinishingActivity = topDisplayFocusedRootTask.getTopNonFinishingActivity();
                if (topNonFinishingActivity == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                int i = topNonFinishingActivity.mUserId;
                com.android.server.wm.DisplayContent displayContent = topNonFinishingActivity.getDisplayContent();
                if (displayContent == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    boolean forAllWindows = displayContent.forAllWindows(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda21
                        public final boolean apply(java.lang.Object obj) {
                            boolean lambda$isAssistDataAllowed$6;
                            lambda$isAssistDataAllowed$6 = com.android.server.wm.ActivityTaskManagerService.this.lambda$isAssistDataAllowed$6((com.android.server.wm.WindowState) obj);
                            return lambda$isAssistDataAllowed$6;
                        }
                    }, true);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return android.app.admin.DevicePolicyCache.getInstance().isScreenCaptureAllowed(i) && !forAllWindows;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isAssistDataAllowed$6(com.android.server.wm.WindowState windowState) {
        return windowState.isOnScreen() && (android.os.UserManager.isUserTypePrivateProfile(getUserManager().getProfileType(windowState.mShowUserId)) || hasUserRestriction("no_assist_content", windowState.mShowUserId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLocalVoiceInteractionStartedLocked(android.os.IBinder iBinder, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
        com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            return;
        }
        forTokenLocked.setVoiceSessionLocked(iVoiceInteractionSession);
        try {
            forTokenLocked.app.getThread().scheduleLocalVoiceInteractionStarted(iBinder, iVoiceInteractor);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                startRunningVoiceLocked(iVoiceInteractionSession, forTokenLocked.info.applicationInfo.uid);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (android.os.RemoteException e) {
            forTokenLocked.clearVoiceSessionLocked();
        }
    }

    private void startRunningVoiceLocked(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, int i) {
        android.util.Slog.d("ActivityTaskManager", "<<<  startRunningVoiceLocked()");
        this.mVoiceWakeLock.setWorkSource(new android.os.WorkSource(i));
        if (this.mRunningVoice == null || this.mRunningVoice.asBinder() != iVoiceInteractionSession.asBinder()) {
            boolean z = this.mRunningVoice != null;
            this.mRunningVoice = iVoiceInteractionSession;
            if (!z) {
                this.mVoiceWakeLock.acquire();
                updateSleepIfNeededLocked();
            }
        }
    }

    void finishRunningVoiceLocked() {
        if (this.mRunningVoice != null) {
            this.mRunningVoice = null;
            this.mVoiceWakeLock.release();
            updateSleepIfNeededLocked();
        }
    }

    public void setVoiceKeepAwake(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mRunningVoice != null && this.mRunningVoice.asBinder() == iVoiceInteractionSession.asBinder()) {
                    if (z) {
                        this.mVoiceWakeLock.acquire();
                    } else {
                        this.mVoiceWakeLock.release();
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void keyguardGoingAway(final int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_KEYGUARD", "unlock keyguard");
        enforceNotIsolatedCaller("keyguardGoingAway");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if ((i & 16) != 0) {
                        this.mActivityClientController.invalidateHomeTaskSnapshot(null);
                    } else if (this.mKeyguardShown) {
                        this.mDemoteTopAppReasons |= 1;
                    }
                    this.mRootWindowContainer.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda15
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.ActivityTaskManagerService.this.lambda$keyguardGoingAway$7(i, (com.android.server.wm.DisplayContent) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            com.android.server.wallpaper.WallpaperManagerInternal wallpaperManagerInternal = getWallpaperManagerInternal();
            if (wallpaperManagerInternal != null) {
                wallpaperManagerInternal.onKeyguardGoingAway();
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$keyguardGoingAway$7(int i, com.android.server.wm.DisplayContent displayContent) {
        this.mKeyguardController.keyguardGoingAway(displayContent.getDisplayId(), i);
    }

    public void suppressResizeConfigChanges(boolean z) throws android.os.RemoteException {
        this.mAmInternal.enforceCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "suppressResizeConfigChanges()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mSuppressResizeConfigChanges = z;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void onSplashScreenViewCopyFinished(int i, @android.annotation.Nullable android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) throws android.os.RemoteException {
        com.android.server.wm.ActivityRecord topWaitSplashScreenActivity;
        this.mAmInternal.enforceCallingPermission("android.permission.MANAGE_ACTIVITY_TASKS", "copySplashScreenViewFinish()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
                if (anyTaskForId != null && (topWaitSplashScreenActivity = anyTaskForId.getTopWaitSplashScreenActivity()) != null) {
                    topWaitSplashScreenActivity.onCopySplashScreenFinish(splashScreenViewParcelable);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    boolean prepareAutoEnterPictureAndPictureMode(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.inPinnedWindowingMode()) {
            return true;
        }
        if (activityRecord.canAutoEnterPip() && getTransitionController().getCollectingTransition() != null) {
            getTransitionController().getCollectingTransition().setPipActivity(activityRecord);
            return true;
        }
        return false;
    }

    boolean enterPictureInPictureMode(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull android.app.PictureInPictureParams pictureInPictureParams, boolean z) {
        return enterPictureInPictureMode(activityRecord, pictureInPictureParams, z, false);
    }

    boolean enterPictureInPictureMode(@android.annotation.NonNull final com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull final android.app.PictureInPictureParams pictureInPictureParams, boolean z, final boolean z2) {
        com.android.server.wm.Transition transition;
        if (activityRecord.inPinnedWindowingMode()) {
            return true;
        }
        if (!activityRecord.checkEnterPictureInPictureState("enterPictureInPictureMode", false)) {
            return false;
        }
        if (isPip2ExperimentEnabled()) {
            final com.android.server.wm.Transition transition2 = new com.android.server.wm.Transition(10, 0, getTransitionController(), this.mWindowManager.mSyncEngine);
            transition2.setPipActivity(activityRecord);
            getTransitionController().startCollectOrQueue(transition2, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda7
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z3) {
                    com.android.server.wm.ActivityTaskManagerService.this.lambda$enterPictureInPictureMode$8(transition2, activityRecord, z3);
                }
            });
            return true;
        }
        boolean z3 = z && (!activityRecord.isState(com.android.server.wm.ActivityRecord.State.PAUSING) || pictureInPictureParams.isAutoEnterEnabled());
        if (getTransitionController().isShellTransitionsEnabled() && z3) {
            transition = new com.android.server.wm.Transition(10, 0, getTransitionController(), this.mWindowManager.mSyncEngine);
        } else {
            transition = null;
        }
        if (activityRecord.getTaskFragment() != null && activityRecord.getTaskFragment().isEmbeddedWithBoundsOverride() && transition != null) {
            transition.addFlag(512);
        }
        final com.android.server.wm.Transition transition3 = transition;
        final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskManagerService.this.lambda$enterPictureInPictureMode$9(activityRecord, transition3, z2, pictureInPictureParams);
            }
        };
        if (activityRecord.isKeyguardLocked()) {
            this.mActivityClientController.dismissKeyguard(activityRecord.token, new com.android.server.wm.ActivityTaskManagerService.AnonymousClass2(transition, runnable), null);
        } else if (transition != null) {
            getTransitionController().startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda9
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z4) {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterPictureInPictureMode$8(com.android.server.wm.Transition transition, com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        getTransitionController().requestStartTransition(transition, activityRecord.getTask(), null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enterPictureInPictureMode$9(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.Transition transition, boolean z, android.app.PictureInPictureParams pictureInPictureParams) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (activityRecord.getParent() == null) {
                    android.util.Slog.e("ActivityTaskManager", "Skip enterPictureInPictureMode, destroyed " + activityRecord);
                    if (transition != null) {
                        transition.abort();
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.EventLogTags.writeWmEnterPip(activityRecord.mUserId, java.lang.System.identityHashCode(activityRecord), activityRecord.shortComponentName, java.lang.Boolean.toString(z));
                activityRecord.setPictureInPictureParams(pictureInPictureParams);
                activityRecord.mAutoEnteringPip = z;
                this.mRootWindowContainer.moveActivityToPinnedRootTask(activityRecord, null, "enterPictureInPictureMode", transition);
                if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.PAUSING) && activityRecord.mPauseSchedulePendingForPip) {
                    activityRecord.getTask().schedulePauseActivity(activityRecord, false, false, true, "auto-pip");
                }
                activityRecord.mAutoEnteringPip = false;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.wm.ActivityTaskManagerService$2, reason: invalid class name */
    class AnonymousClass2 extends com.android.internal.policy.KeyguardDismissCallback {
        final /* synthetic */ java.lang.Runnable val$enterPipRunnable;
        final /* synthetic */ com.android.server.wm.Transition val$transition;

        AnonymousClass2(com.android.server.wm.Transition transition, java.lang.Runnable runnable) {
            this.val$transition = transition;
            this.val$enterPipRunnable = runnable;
        }

        public void onDismissSucceeded() {
            if (this.val$transition == null) {
                com.android.server.wm.ActivityTaskManagerService.this.mH.post(this.val$enterPipRunnable);
                return;
            }
            com.android.server.wm.TransitionController transitionController = com.android.server.wm.ActivityTaskManagerService.this.getTransitionController();
            com.android.server.wm.Transition transition = this.val$transition;
            final java.lang.Runnable runnable = this.val$enterPipRunnable;
            transitionController.startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityTaskManagerService$2$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z) {
                    com.android.server.wm.ActivityTaskManagerService.AnonymousClass2.this.lambda$onDismissSucceeded$0(runnable, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDismissSucceeded$0(java.lang.Runnable runnable, boolean z) {
            if (z) {
                runnable.run();
            } else {
                com.android.server.wm.ActivityTaskManagerService.this.mH.post(runnable);
            }
        }
    }

    public android.window.IWindowOrganizerController getWindowOrganizerController() {
        return this.mWindowOrganizerController;
    }

    public void enforceSystemHasVrFeature() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) {
            throw new java.lang.UnsupportedOperationException("VR mode not supported on this device!");
        }
    }

    public boolean supportsLocalVoiceInteraction() {
        return ((android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class)).supportsLocalVoiceInteraction();
    }

    public boolean updateConfiguration(android.content.res.Configuration configuration) {
        android.content.res.Configuration configuration2;
        this.mAmInternal.enforceCallingPermission("android.permission.CHANGE_CONFIGURATION", "updateConfiguration()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mWindowManager == null) {
                    android.util.Slog.w("ActivityTaskManager", "Skip updateConfiguration because mWindowManager isn't set");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                if (configuration != null) {
                    configuration2 = configuration;
                } else {
                    configuration2 = this.mWindowManager.computeNewConfiguration(0);
                }
                this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda23(), this.mAmInternal, 0));
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                if (configuration2 != null) {
                    try {
                        android.provider.Settings.System.clearConfiguration(configuration2);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                updateConfigurationLocked(configuration2, null, false, false, com.android.server.am.ProcessList.INVALID_ADJ, false);
                boolean z = this.mTmpUpdateConfigurationResult.changes != 0;
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (java.lang.Throwable th2) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
    }

    public void cancelTaskWindowTransition(int i) {
        enforceTaskPermission("cancelTaskWindowTransition()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 0);
                    if (anyTaskForId != null) {
                        anyTaskForId.cancelTaskWindowTransition();
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    android.util.Slog.w("ActivityTaskManager", "cancelTaskWindowTransition: taskId=" + i + " not found");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Finally extract failed */
    public android.window.TaskSnapshot getTaskSnapshot(int i, boolean z) {
        this.mAmInternal.enforceCallingPermission("android.permission.READ_FRAME_BUFFER", "getTaskSnapshot()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
                    if (anyTaskForId != null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        android.window.TaskSnapshot snapshot = this.mWindowManager.mTaskSnapshotController.getSnapshot(i, anyTaskForId.mUserId, true, z);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return snapshot;
                    }
                    android.util.Slog.w("ActivityTaskManager", "getTaskSnapshot: taskId=" + i + " not found");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public android.window.TaskSnapshot takeTaskSnapshot(int i, boolean z) {
        this.mAmInternal.enforceCallingPermission("android.permission.READ_FRAME_BUFFER", "takeTaskSnapshot()");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
                    if (anyTaskForId != null && anyTaskForId.isVisible()) {
                        if (z) {
                            android.window.TaskSnapshot recordSnapshot = this.mWindowManager.mTaskSnapshotController.recordSnapshot(anyTaskForId);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return recordSnapshot;
                        }
                        android.window.TaskSnapshot snapshot = this.mWindowManager.mTaskSnapshotController.snapshot(anyTaskForId);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return snapshot;
                    }
                    android.util.Slog.w("ActivityTaskManager", "takeTaskSnapshot: taskId=" + i + " not found or not visible");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getLastResumedActivityUserId() {
        this.mAmInternal.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "getLastResumedActivityUserId()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mLastResumedActivity == null) {
                    int currentUserId = getCurrentUserId();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return currentUserId;
                }
                int i = this.mLastResumedActivity.mUserId;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getLastResumedActivityUid() {
        this.mAmInternal.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "getLastResumedActivityUserId()");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mLastResumedActivity == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                }
                int uid = this.mLastResumedActivity.getUid();
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return uid;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void updateLockTaskFeatures(int i, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 1000) {
            this.mAmInternal.enforceCallingPermission("android.permission.UPDATE_LOCK_TASK_PACKAGES", "updateLockTaskFeatures()");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_LOCKTASK, -559595900417262876L, 1, null, java.lang.Long.valueOf(i), java.lang.String.valueOf(java.lang.Integer.toHexString(i2)));
                getLockTaskController().updateLockTaskFeatures(i, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void registerRemoteAnimationForNextActivityStart(java.lang.String str, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.os.IBinder iBinder) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "registerRemoteAnimationForNextActivityStart");
        remoteAnimationAdapter.setCallingPidUid(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    getActivityStartController().registerRemoteAnimationForNextActivityStart(str, remoteAnimationAdapter, iBinder);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void registerRemoteAnimationsForDisplay(int i, android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "registerRemoteAnimations");
        remoteAnimationDefinition.setCallingPidUid(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(i);
                if (displayContent == null) {
                    android.util.Slog.e("ActivityTaskManager", "Couldn't find display with id: " + i);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    displayContent.registerRemoteAnimations(remoteAnimationDefinition);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void alwaysShowUnsupportedCompileSdkWarning(android.content.ComponentName componentName) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mAppWarnings.alwaysShowUnsupportedCompileSdkWarning(componentName);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setVrThread(int i) {
        enforceSystemHasVrFeature();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int callingPid = android.os.Binder.getCallingPid();
                this.mVrController.setVrThreadLocked(i, callingPid, this.mProcessMap.getProcess(callingPid));
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setPersistentVrThread(int i) {
        if (checkCallingPermission("android.permission.RESTRICTED_VR_ACCESS") != 0) {
            java.lang.String str = "Permission Denial: setPersistentVrThread() from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires android.permission.RESTRICTED_VR_ACCESS";
            android.util.Slog.w("ActivityTaskManager", str);
            throw new java.lang.SecurityException(str);
        }
        enforceSystemHasVrFeature();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int callingPid = android.os.Binder.getCallingPid();
                this.mVrController.setPersistentVrThreadLocked(i, callingPid, this.mProcessMap.getProcess(callingPid));
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void stopAppSwitches() {
        this.mAmInternal.enforceCallingPermission("android.permission.STOP_APP_SWITCHES", "stopAppSwitches");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAppSwitchesState = 0;
                this.mLastStopAppSwitchesTime = android.os.SystemClock.uptimeMillis();
                this.mH.removeMessages(4);
                this.mH.sendEmptyMessageDelayed(4, 500L);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void resumeAppSwitches() {
        this.mAmInternal.enforceCallingPermission("android.permission.STOP_APP_SWITCHES", "resumeAppSwitches");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAppSwitchesState = 2;
                this.mH.removeMessages(4);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    long getLastStopAppSwitchesTime() {
        return this.mLastStopAppSwitchesTime;
    }

    boolean shouldDisableNonVrUiLocked() {
        return this.mVrController.shouldDisableNonVrUiLocked();
    }

    void applyUpdateVrModeLocked(final com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.requestedVrComponent != null && activityRecord.getDisplayId() != 0) {
            android.util.Slog.i("ActivityTaskManager", "Moving " + activityRecord.shortComponentName + " from display " + activityRecord.getDisplayId() + " to main display for VR");
            this.mRootWindowContainer.moveRootTaskToDisplay(activityRecord.getRootTaskId(), 0, true);
        }
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskManagerService.this.lambda$applyUpdateVrModeLocked$11(activityRecord);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyUpdateVrModeLocked$11(com.android.server.wm.ActivityRecord activityRecord) {
        if (!this.mVrController.onVrModeChanged(activityRecord)) {
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean shouldDisableNonVrUiLocked = this.mVrController.shouldDisableNonVrUiLocked();
                this.mWindowManager.disableNonVrUi(shouldDisableNonVrUiLocked);
                if (shouldDisableNonVrUiLocked) {
                    this.mRootWindowContainer.removeRootTasksInWindowingModes(2);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public int getPackageScreenCompatMode(java.lang.String str) {
        int packageScreenCompatModeLocked;
        enforceNotIsolatedCaller("getPackageScreenCompatMode");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                packageScreenCompatModeLocked = this.mCompatModePackages.getPackageScreenCompatModeLocked(str);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return packageScreenCompatModeLocked;
    }

    public void setPackageScreenCompatMode(java.lang.String str, int i) {
        this.mAmInternal.enforceCallingPermission("android.permission.SET_SCREEN_COMPATIBILITY", "setPackageScreenCompatMode");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mCompatModePackages.setPackageScreenCompatModeLocked(str, i);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean getPackageAskScreenCompat(java.lang.String str) {
        boolean packageAskCompatModeLocked;
        enforceNotIsolatedCaller("getPackageAskScreenCompat");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                packageAskCompatModeLocked = this.mCompatModePackages.getPackageAskCompatModeLocked(str);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return packageAskCompatModeLocked;
    }

    public void setPackageAskScreenCompat(java.lang.String str, boolean z) {
        this.mAmInternal.enforceCallingPermission("android.permission.SET_SCREEN_COMPATIBILITY", "setPackageAskScreenCompat");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mCompatModePackages.setPackageAskCompatModeLocked(str, z);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public static java.lang.String relaunchReasonToString(int i) {
        switch (i) {
            case 1:
                return "window_resize";
            case 2:
                return "free_resize";
            default:
                return null;
        }
    }

    com.android.server.wm.Task getTopDisplayFocusedRootTask() {
        return this.mRootWindowContainer.getTopDisplayFocusedRootTask();
    }

    void notifyTaskPersisterLocked(com.android.server.wm.Task task, boolean z) {
        this.mRecentTasks.notifyTaskPersisterLocked(task, z);
    }

    public void clearLaunchParamsForPackages(java.util.List<java.lang.String> list) {
        enforceTaskPermission("clearLaunchParamsForPackages");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            for (int i = 0; i < list.size(); i++) {
                try {
                    this.mTaskSupervisor.mLaunchParamsPersister.removeRecordForPackage(list.get(i));
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void onPictureInPictureUiStateChanged(android.app.PictureInPictureUiState pictureInPictureUiState) {
        com.android.server.wm.Task rootTask;
        enforceTaskPermission("onPictureInPictureUiStateChanged");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mRootWindowContainer.getDefaultTaskDisplayArea().hasPinnedTask()) {
                    rootTask = this.mRootWindowContainer.getDefaultTaskDisplayArea().getRootPinnedTask();
                } else {
                    rootTask = this.mRootWindowContainer.getDefaultTaskDisplayArea().getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda12
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean isActivityTypeStandard;
                            isActivityTypeStandard = ((com.android.server.wm.Task) obj).isActivityTypeStandard();
                            return isActivityTypeStandard;
                        }
                    });
                }
                if (rootTask != null && rootTask.getTopMostActivity() != null && !rootTask.getTopMostActivity().isState(com.android.server.wm.ActivityRecord.State.FINISHING, com.android.server.wm.ActivityRecord.State.DESTROYING, com.android.server.wm.ActivityRecord.State.DESTROYED)) {
                    this.mWindowManager.mAtmService.mActivityClientController.onPictureInPictureUiStateChanged(rootTask.getTopMostActivity(), pictureInPictureUiState);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void detachNavigationBarFromApp(@android.annotation.NonNull android.os.IBinder iBinder) {
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "detachNavigationBarFromApp");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    getTransitionController().legacyDetachNavigationBarFromApp(iBinder);
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

    void dumpLastANRLocked(java.io.PrintWriter printWriter) {
        printWriter.println("ACTIVITY MANAGER LAST ANR (dumpsys activity lastanr)");
        if (this.mLastANRState == null) {
            printWriter.println("  <no ANR has occurred since boot>");
        } else {
            printWriter.println(this.mLastANRState);
        }
    }

    void dumpLastANRTracesLocked(java.io.PrintWriter printWriter) {
        printWriter.println("ACTIVITY MANAGER LAST ANR TRACES (dumpsys activity lastanr-traces)");
        java.io.File[] listFiles = new java.io.File(com.android.server.am.StackTracesDumpHelper.ANR_TRACE_DIR).listFiles();
        if (com.android.internal.util.ArrayUtils.isEmpty(listFiles)) {
            printWriter.println("  <no ANR has occurred since boot>");
            return;
        }
        java.io.File file = null;
        for (java.io.File file2 : listFiles) {
            if (file == null || file.lastModified() < file2.lastModified()) {
                file = file2;
            }
        }
        printWriter.print("File: ");
        printWriter.print(file.getName());
        printWriter.println();
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file));
            while (true) {
                try {
                    java.lang.String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        printWriter.println(readLine);
                    } else {
                        bufferedReader.close();
                        return;
                    }
                } finally {
                }
            }
        } catch (java.io.IOException e) {
            printWriter.print("Unable to read: ");
            printWriter.print(e);
            printWriter.println();
        }
    }

    void dumpTopResumedActivityLocked(java.io.PrintWriter printWriter) {
        printWriter.println("ACTIVITY MANAGER TOP-RESUMED (dumpsys activity top-resumed)");
        com.android.server.wm.ActivityRecord topResumedActivity = this.mRootWindowContainer.getTopResumedActivity();
        if (topResumedActivity != null) {
            topResumedActivity.dump(printWriter, "", true);
        }
    }

    void dumpVisibleActivitiesLocked(java.io.PrintWriter printWriter, int i) {
        printWriter.println("ACTIVITY MANAGER VISIBLE ACTIVITIES (dumpsys activity visible)");
        boolean z = false;
        java.util.ArrayList<com.android.server.wm.ActivityRecord> dumpActivities = this.mRootWindowContainer.getDumpActivities("all", true, false, -1);
        boolean z2 = false;
        for (int size = dumpActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord = dumpActivities.get(size);
            if (activityRecord.isVisible() && (i == -1 || activityRecord.getDisplayId() == i)) {
                if (z2) {
                    printWriter.println();
                }
                activityRecord.dump(printWriter, "", true);
                z = true;
                z2 = true;
            }
        }
        if (!z) {
            printWriter.println("(nothing)");
        }
    }

    void dumpActivitiesLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, boolean z2, java.lang.String str, int i2) {
        dumpActivitiesLocked(fileDescriptor, printWriter, strArr, i, z, z2, str, i2, "ACTIVITY MANAGER ACTIVITIES (dumpsys activity activities)");
    }

    void dumpActivitiesLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, boolean z2, java.lang.String str, int i2, java.lang.String str2) {
        boolean z3;
        printWriter.println(str2);
        boolean dumpActivities = this.mRootWindowContainer.dumpActivities(fileDescriptor, printWriter, z, z2, str, i2);
        boolean z4 = true;
        if (!com.android.server.wm.ActivityTaskSupervisor.printThisActivity(printWriter, this.mRootWindowContainer.getTopResumedActivity(), str, i2, dumpActivities, "  ResumedActivity: ", null)) {
            z3 = dumpActivities;
        } else {
            dumpActivities = false;
            z3 = true;
        }
        if (str != null) {
            z4 = z3;
        } else {
            if (dumpActivities) {
                printWriter.println();
            }
            this.mTaskSupervisor.dump(printWriter, "  ");
            this.mTaskOrganizerController.dump(printWriter, "  ");
            this.mVisibleActivityProcessTracker.dump(printWriter, "  ");
            this.mActiveUids.dump(printWriter, "  ");
            if (this.mDemoteTopAppReasons != 0) {
                printWriter.println("  mDemoteTopAppReasons=" + this.mDemoteTopAppReasons);
            }
        }
        if (!z4) {
            printWriter.println("  (nothing)");
        }
    }

    void dumpActivityContainersLocked(java.io.PrintWriter printWriter) {
        printWriter.println("ACTIVITY MANAGER CONTAINERS (dumpsys activity containers)");
        this.mRootWindowContainer.dumpChildrenNames(printWriter, "");
        printWriter.println(" ");
    }

    void dumpActivityStarterLocked(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println("ACTIVITY MANAGER STARTER (dumpsys activity starter)");
        getActivityStartController().dump(printWriter, "", str);
    }

    void dumpInstalledPackagesConfig(java.io.PrintWriter printWriter) {
        this.mPackageConfigPersister.dump(printWriter, getCurrentUserId());
    }

    protected boolean dumpActivity(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String str, java.lang.String[] strArr, int i, boolean z, boolean z2, boolean z3, int i2, int i3) {
        java.util.ArrayList<com.android.server.wm.ActivityRecord> dumpActivities;
        com.android.server.wm.Task task;
        boolean z4;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                dumpActivities = this.mRootWindowContainer.getDumpActivities(str, z2, z3, i3);
            } finally {
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        boolean z5 = false;
        if (dumpActivities.size() <= 0) {
            return false;
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length - i];
        java.lang.System.arraycopy(strArr, i, strArr2, 0, strArr.length - i);
        com.android.server.wm.Task task2 = null;
        int size = dumpActivities.size() - 1;
        boolean z6 = false;
        while (size >= 0) {
            com.android.server.wm.ActivityRecord activityRecord = dumpActivities.get(size);
            if (z6) {
                printWriter.println();
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    com.android.server.wm.Task task3 = activityRecord.getTask();
                    int displayId = task3.getDisplayId();
                    if (i2 == -1 || displayId == i2) {
                        if (task2 != task3) {
                            printWriter.print("TASK ");
                            printWriter.print(task3.affinity);
                            printWriter.print(" id=");
                            printWriter.print(task3.mTaskId);
                            printWriter.print(" userId=");
                            printWriter.print(task3.mUserId);
                            printDisplayInfoAndNewLine(printWriter, activityRecord);
                            if (z) {
                                task3.dump(printWriter, "  ");
                            }
                            task = task3;
                            z4 = true;
                        } else {
                            task = task2;
                            z4 = z5;
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        dumpActivity("  ", fileDescriptor, printWriter, dumpActivities.get(size), strArr2, z);
                        task2 = task;
                        z5 = z4;
                    } else {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } finally {
                }
            }
            size--;
            z6 = true;
        }
        if (!z5) {
            printWriter.println("(nothing)");
        }
        return true;
    }

    private void dumpActivity(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, com.android.server.wm.ActivityRecord activityRecord, java.lang.String[] strArr, boolean z) {
        android.app.IApplicationThread iApplicationThread;
        java.lang.String str2 = str + "  ";
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                printWriter.print(str);
                printWriter.print("ACTIVITY ");
                printWriter.print(activityRecord.shortComponentName);
                printWriter.print(" ");
                printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(activityRecord)));
                printWriter.print(" pid=");
                if (activityRecord.hasProcess()) {
                    printWriter.print(activityRecord.app.getPid());
                    iApplicationThread = activityRecord.app.getThread();
                } else {
                    printWriter.print("(not running)");
                    iApplicationThread = null;
                }
                printWriter.print(" userId=");
                printWriter.print(activityRecord.mUserId);
                printWriter.print(" uid=");
                printWriter.print(activityRecord.getUid());
                printDisplayInfoAndNewLine(printWriter, activityRecord);
                if (z) {
                    activityRecord.dump(printWriter, str2, true);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (iApplicationThread != null) {
            printWriter.flush();
            try {
                com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
                try {
                    iApplicationThread.dumpActivity(transferPipe.getWriteFd(), activityRecord.token, str2, strArr);
                    transferPipe.go(fileDescriptor);
                    transferPipe.close();
                } catch (java.lang.Throwable th2) {
                    try {
                        transferPipe.close();
                    } catch (java.lang.Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                    throw th2;
                }
            } catch (android.os.RemoteException e) {
                printWriter.println(str2 + "Got a RemoteException while dumping the activity");
            } catch (java.io.IOException e2) {
                printWriter.println(str2 + "Failure while dumping the activity: " + e2);
            }
        }
    }

    private void printDisplayInfoAndNewLine(java.io.PrintWriter printWriter, com.android.server.wm.ActivityRecord activityRecord) {
        printWriter.print(" displayId=");
        com.android.server.wm.DisplayContent displayContent = activityRecord.getDisplayContent();
        if (displayContent == null) {
            printWriter.println("N/A");
            return;
        }
        android.view.Display display = displayContent.getDisplay();
        printWriter.print(display.getDisplayId());
        printWriter.print("(type=");
        printWriter.print(android.view.Display.typeToString(display.getType()));
        printWriter.println(")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeSleepStateToProto(android.util.proto.ProtoOutputStream protoOutputStream, int i, boolean z) {
        long start = protoOutputStream.start(1146756268059L);
        protoOutputStream.write(1159641169921L, android.os.PowerManagerInternal.wakefulnessToProtoEnum(i));
        int size = this.mRootWindowContainer.mSleepTokens.size();
        for (int i2 = 0; i2 < size; i2++) {
            protoOutputStream.write(2237677961218L, this.mRootWindowContainer.mSleepTokens.valueAt(i2).toString());
        }
        protoOutputStream.write(1133871366147L, this.mSleeping);
        protoOutputStream.write(1133871366148L, this.mShuttingDown);
        protoOutputStream.write(1133871366149L, z);
        protoOutputStream.end(start);
    }

    int getCurrentUserId() {
        return this.mAmInternal.getCurrentUserId();
    }

    static void enforceNotIsolatedCaller(java.lang.String str) {
        if (android.os.UserHandle.isIsolated(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Isolated process not allowed to call " + str);
        }
    }

    public android.content.res.Configuration getConfiguration() {
        android.content.res.Configuration configuration;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                configuration = new android.content.res.Configuration(getGlobalConfigurationForCallingPid());
                configuration.userSetLocale = false;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return configuration;
    }

    android.content.res.Configuration getGlobalConfiguration() {
        return this.mRootWindowContainer != null ? this.mRootWindowContainer.getConfiguration() : new android.content.res.Configuration();
    }

    boolean updateConfigurationLocked(android.content.res.Configuration configuration, com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        return updateConfigurationLocked(configuration, activityRecord, z, false);
    }

    boolean updateConfigurationLocked(android.content.res.Configuration configuration, com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2) {
        return updateConfigurationLocked(configuration, activityRecord, z, false, com.android.server.am.ProcessList.INVALID_ADJ, z2);
    }

    public void updatePersistentConfiguration(android.content.res.Configuration configuration, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    configuration.windowConfiguration.setToDefaults();
                    updateConfigurationLocked(configuration, null, false, true, i, false);
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

    boolean updateConfigurationLocked(android.content.res.Configuration configuration, com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, int i, boolean z3) {
        int updateGlobalConfigurationLocked;
        deferWindowLayout();
        if (configuration == null) {
            updateGlobalConfigurationLocked = 0;
        } else {
            try {
                updateGlobalConfigurationLocked = updateGlobalConfigurationLocked(configuration, z, z2, i);
                this.mTmpUpdateConfigurationResult.changes = updateGlobalConfigurationLocked;
                this.mTmpUpdateConfigurationResult.mIsUpdating = true;
            } catch (java.lang.Throwable th) {
                this.mTmpUpdateConfigurationResult.mIsUpdating = false;
                continueWindowLayout();
                throw th;
            }
        }
        boolean ensureConfigAndVisibilityAfterUpdate = z3 ? true : ensureConfigAndVisibilityAfterUpdate(activityRecord, updateGlobalConfigurationLocked);
        this.mTmpUpdateConfigurationResult.mIsUpdating = false;
        continueWindowLayout();
        this.mTmpUpdateConfigurationResult.activityRelaunched = !ensureConfigAndVisibilityAfterUpdate;
        return ensureConfigAndVisibilityAfterUpdate;
    }

    int updateGlobalConfigurationLocked(@android.annotation.NonNull android.content.res.Configuration configuration, boolean z, boolean z2, int i) {
        this.mTempConfig.setTo(getGlobalConfiguration());
        int updateFrom = this.mTempConfig.updateFrom(configuration);
        int i2 = 0;
        if (updateFrom == 0) {
            return 0;
        }
        android.os.Trace.traceBegin(32L, "updateGlobalConfiguration");
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 2008996027621913637L, 0, null, java.lang.String.valueOf(configuration));
        com.android.server.am.EventLogTags.writeConfigurationChanged(updateFrom);
        com.android.internal.util.FrameworkStatsLog.write(66, configuration.colorMode, configuration.densityDpi, configuration.fontScale, configuration.hardKeyboardHidden, configuration.keyboard, configuration.keyboardHidden, configuration.mcc, configuration.mnc, configuration.navigation, configuration.navigationHidden, configuration.orientation, configuration.screenHeightDp, configuration.screenLayout, configuration.screenWidthDp, configuration.smallestScreenWidthDp, configuration.touchscreen, configuration.uiMode);
        if (android.os.Process.myUid() == 1000) {
            if (configuration.mcc != 0) {
                android.os.SystemProperties.set("debug.tracing.mcc", java.lang.Integer.toString(configuration.mcc));
            }
            if (configuration.mnc != 0) {
                android.os.SystemProperties.set("debug.tracing.mnc", java.lang.Integer.toString(configuration.mnc));
            }
        }
        if (!z && !configuration.getLocales().isEmpty() && configuration.userSetLocale) {
            android.os.LocaleList locales = configuration.getLocales();
            if (locales.size() > 1) {
                if (this.mSupportedSystemLocales == null) {
                    this.mSupportedSystemLocales = android.content.res.Resources.getSystem().getAssets().getLocales();
                }
                i2 = java.lang.Math.max(0, locales.getFirstMatchIndex(this.mSupportedSystemLocales));
            }
            android.os.SystemProperties.set("persist.sys.locale", locales.get(i2).toLanguageTag());
            android.os.LocaleList.setDefault(locales, i2);
        }
        this.mTempConfig.seq = increaseConfigurationSeqLocked();
        android.util.Slog.i("ActivityTaskManager", "Config changes=" + java.lang.Integer.toHexString(updateFrom) + " " + this.mTempConfig);
        this.mUsageStatsInternal.reportConfigurationChange(this.mTempConfig, this.mAmInternal.getCurrentUserId());
        updateShouldShowDialogsLocked(this.mTempConfig);
        com.android.internal.policy.AttributeCache instance = com.android.internal.policy.AttributeCache.instance();
        if (instance != null) {
            instance.updateConfiguration(this.mTempConfig);
        }
        this.mSystemThread.applyConfigurationToResources(this.mTempConfig);
        if (z2 && android.provider.Settings.System.hasInterestingConfigurationChanges(updateFrom)) {
            this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda13
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.wm.ActivityTaskManagerService) obj).sendPutConfigurationForUserMsg(((java.lang.Integer) obj2).intValue(), (android.content.res.Configuration) obj3);
                }
            }, this, java.lang.Integer.valueOf(i), new android.content.res.Configuration(this.mTempConfig)));
        }
        android.util.SparseArray<com.android.server.wm.WindowProcessController> pidMap = this.mProcessMap.getPidMap();
        for (int size = pidMap.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowProcessController windowProcessController = pidMap.get(pidMap.keyAt(size));
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -6404059840638143757L, 0, null, java.lang.String.valueOf(windowProcessController.mName), java.lang.String.valueOf(this.mTempConfig));
            windowProcessController.onConfigurationChanged(this.mTempConfig);
        }
        this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda14
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((android.app.ActivityManagerInternal) obj).broadcastGlobalConfigurationChanged(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue());
            }
        }, this.mAmInternal, java.lang.Integer.valueOf(updateFrom), java.lang.Boolean.valueOf(z)));
        android.os.Trace.traceBegin(32L, "RootConfigChange");
        this.mRootWindowContainer.onConfigurationChanged(this.mTempConfig);
        android.os.Trace.traceEnd(32L);
        android.os.Trace.traceEnd(32L);
        return updateFrom;
    }

    private int increaseAssetConfigurationSeq() {
        int i = this.mGlobalAssetsSeq + 1;
        this.mGlobalAssetsSeq = i;
        this.mGlobalAssetsSeq = java.lang.Math.max(i, 1);
        return this.mGlobalAssetsSeq;
    }

    public void updateActivityApplicationInfo(int i, android.util.ArrayMap<java.lang.String, android.content.pm.ApplicationInfo> arrayMap) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mRootWindowContainer != null) {
                    this.mRootWindowContainer.updateActivityApplicationInfo(i, arrayMap);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void updateAssetConfiguration(java.util.List<com.android.server.wm.WindowProcessController> list, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int increaseAssetConfigurationSeq = increaseAssetConfigurationSeq();
                if (z) {
                    android.content.res.Configuration configuration = new android.content.res.Configuration();
                    configuration.assetsSeq = increaseAssetConfigurationSeq;
                    updateConfiguration(configuration);
                }
                for (int size = list.size() - 1; size >= 0; size--) {
                    list.get(size).updateAssetConfiguration(increaseAssetConfigurationSeq);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void startPowerMode(int i) {
        int i2 = this.mPowerModeReasons;
        this.mPowerModeReasons |= i;
        if ((i & 4) != 0) {
            if (this.mRetainPowerModeAndTopProcessState) {
                this.mH.removeMessages(3);
            }
            this.mRetainPowerModeAndTopProcessState = true;
            this.mH.sendEmptyMessageDelayed(3, 1000L);
            android.util.Slog.d("ActivityTaskManager", "Temporarily retain top process state for launching app");
        }
        if (this.mPowerManagerInternal == null) {
            return;
        }
        if ((i & 1) != 0 && (i2 & 1) == 0) {
            android.os.Trace.instant(32L, "StartModeLaunch");
            this.mPowerManagerInternal.setPowerMode(5, true);
        } else if (i == 2 && (i2 & 2) == 0) {
            android.os.Trace.instant(32L, "StartModeDisplayChange");
            this.mPowerManagerInternal.setPowerMode(17, true);
        }
    }

    void endPowerMode(int i) {
        if (this.mPowerModeReasons == 0) {
            return;
        }
        int i2 = this.mPowerModeReasons;
        this.mPowerModeReasons = (~i) & this.mPowerModeReasons;
        if ((this.mPowerModeReasons & 4) != 0) {
            boolean z = true;
            for (int childCount = this.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
                z &= this.mRootWindowContainer.getChildAt(childCount).mUnknownAppVisibilityController.allResolved();
            }
            if (z) {
                this.mPowerModeReasons &= -5;
                this.mRetainPowerModeAndTopProcessState = false;
                this.mH.removeMessages(3);
            }
        }
        if (this.mPowerManagerInternal == null) {
            return;
        }
        if ((i2 & 5) != 0 && (this.mPowerModeReasons & 5) == 0) {
            android.os.Trace.instant(32L, "EndModeLaunch");
            this.mPowerManagerInternal.setPowerMode(5, false);
        }
        if ((i2 & 2) != 0 && (this.mPowerModeReasons & 2) == 0) {
            android.os.Trace.instant(32L, "EndModeDisplayChange");
            this.mPowerManagerInternal.setPowerMode(17, false);
        }
    }

    void deferWindowLayout() {
        if (!this.mWindowManager.mWindowPlacerLocked.isLayoutDeferred()) {
            this.mLayoutReasons = 0;
        }
        this.mWindowManager.mWindowPlacerLocked.deferLayout();
    }

    void continueWindowLayout() {
        this.mWindowManager.mWindowPlacerLocked.continueLayout(this.mLayoutReasons != 0);
        this.mLifecycleManager.onLayoutContinued();
    }

    void addWindowLayoutReasons(int i) {
        this.mLayoutReasons = i | this.mLayoutReasons;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEventDispatchingLocked(boolean z) {
        this.mWindowManager.setEventDispatching(z && !this.mShuttingDown);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPutConfigurationForUserMsg(int i, android.content.res.Configuration configuration) {
        android.provider.Settings.System.putConfigurationForUser(this.mContext.getContentResolver(), configuration, i);
    }

    boolean isActivityStartsLoggingEnabled() {
        return this.mAmInternal.isActivityStartsLoggingEnabled();
    }

    boolean isBackgroundActivityStartsEnabled() {
        return this.mAmInternal.isBackgroundActivityStartsEnabled();
    }

    static long getInputDispatchingTimeoutMillisLocked(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null || !activityRecord.hasProcess()) {
            return android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        }
        return getInputDispatchingTimeoutMillisLocked(activityRecord.app);
    }

    private static long getInputDispatchingTimeoutMillisLocked(com.android.server.wm.WindowProcessController windowProcessController) {
        if (windowProcessController == null) {
            return android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
        }
        return windowProcessController.getInputDispatchingTimeoutMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShouldShowDialogsLocked(android.content.res.Configuration configuration) {
        boolean z = false;
        boolean z2 = (configuration.keyboard == 1 && configuration.touchscreen == 1 && configuration.navigation == 1) ? false : true;
        boolean z3 = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "hide_error_dialogs", 0) != 0;
        if (z2 && android.app.ActivityTaskManager.currentUiModeSupportsErrorDialogs(configuration) && !z3) {
            z = true;
        }
        this.mShowDialogs = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFontScaleIfNeeded(int i) {
        if (i != getCurrentUserId()) {
            return;
        }
        float floatForUser = android.provider.Settings.System.getFloatForUser(this.mContext.getContentResolver(), "font_scale", 1.0f, i);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (getGlobalConfiguration().fontScale == floatForUser) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                android.content.res.Configuration computeNewConfiguration = this.mWindowManager.computeNewConfiguration(0);
                computeNewConfiguration.fontScale = floatForUser;
                updatePersistentConfiguration(computeNewConfiguration, i);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFontWeightAdjustmentIfNeeded(int i) {
        if (i != getCurrentUserId()) {
            return;
        }
        int intForUser = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "font_weight_adjustment", Integer.MAX_VALUE, i);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (getGlobalConfiguration().fontWeightAdjustment == intForUser) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                android.content.res.Configuration computeNewConfiguration = this.mWindowManager.computeNewConfiguration(0);
                computeNewConfiguration.fontWeightAdjustment = intForUser;
                updatePersistentConfiguration(computeNewConfiguration, i);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    boolean isSleepingOrShuttingDownLocked() {
        return isSleepingLocked() || this.mShuttingDown;
    }

    boolean isSleepingLocked() {
        return this.mSleeping;
    }

    void setLastResumedActivityUncheckLocked(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str) {
        android.service.voice.IVoiceInteractionSession iVoiceInteractionSession;
        com.android.server.wm.Task task = activityRecord.getTask();
        if (task.isActivityTypeStandard()) {
            if (this.mCurAppTimeTracker != activityRecord.appTimeTracker) {
                if (this.mCurAppTimeTracker != null) {
                    this.mCurAppTimeTracker.stop();
                    this.mH.obtainMessage(1, this.mCurAppTimeTracker).sendToTarget();
                    this.mRootWindowContainer.clearOtherAppTimeTrackers(activityRecord.appTimeTracker);
                    this.mCurAppTimeTracker = null;
                }
                if (activityRecord.appTimeTracker != null) {
                    this.mCurAppTimeTracker = activityRecord.appTimeTracker;
                    startTimeTrackingFocusedActivityLocked();
                }
            } else {
                startTimeTrackingFocusedActivityLocked();
            }
        } else {
            activityRecord.appTimeTracker = null;
        }
        if (task.voiceInteractor != null) {
            startRunningVoiceLocked(task.voiceSession, activityRecord.info.applicationInfo.uid);
        } else {
            finishRunningVoiceLocked();
            if (this.mLastResumedActivity != null) {
                com.android.server.wm.Task task2 = this.mLastResumedActivity.getTask();
                if (task2 != null && task2.voiceSession != null) {
                    iVoiceInteractionSession = task2.voiceSession;
                } else {
                    iVoiceInteractionSession = this.mLastResumedActivity.voiceSession;
                }
                if (iVoiceInteractionSession != null) {
                    finishVoiceTask(iVoiceInteractionSession);
                }
            }
        }
        if (this.mLastResumedActivity != null && activityRecord.mUserId != this.mLastResumedActivity.mUserId) {
            this.mAmInternal.sendForegroundProfileChanged(activityRecord.mUserId);
        }
        com.android.server.wm.Task task3 = this.mLastResumedActivity != null ? this.mLastResumedActivity.getTask() : null;
        updateResumedAppTrace(activityRecord);
        this.mLastResumedActivity = activityRecord;
        boolean z = false;
        if (!getTransitionController().isTransientCollect(activityRecord)) {
            boolean focusedApp = activityRecord.mDisplayContent.setFocusedApp(activityRecord);
            if (focusedApp) {
                this.mWindowManager.updateFocusedWindowLocked(0, true);
            }
            z = focusedApp;
        }
        if (task != task3) {
            this.mTaskSupervisor.mRecentTasks.add(task);
        }
        if (z) {
            applyUpdateLockStateLocked(activityRecord);
        }
        if (this.mVrController.mVrService != null) {
            applyUpdateVrModeLocked(activityRecord);
        }
        com.android.server.wm.EventLogTags.writeWmSetResumedActivity(activityRecord.mUserId, activityRecord.shortComponentName, str);
    }

    final class SleepTokenAcquirerImpl implements com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer {
        private final android.util.SparseArray<com.android.server.wm.RootWindowContainer.SleepToken> mSleepTokens = new android.util.SparseArray<>();
        private final java.lang.String mTag;

        SleepTokenAcquirerImpl(@android.annotation.NonNull java.lang.String str) {
            this.mTag = str;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer
        public void acquire(int i) {
            acquire(i, false);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer
        public void acquire(int i, boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!this.mSleepTokens.contains(i)) {
                        this.mSleepTokens.append(i, com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.createSleepToken(this.mTag, i, z));
                        com.android.server.wm.ActivityTaskManagerService.this.updateSleepIfNeededLocked();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer
        public void release(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.RootWindowContainer.SleepToken sleepToken = this.mSleepTokens.get(i);
                    if (sleepToken != null) {
                        com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.removeSleepToken(sleepToken);
                        this.mSleepTokens.remove(i);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x002e, code lost:
    
        if (r2 != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void updateSleepIfNeededLocked() {
        boolean z = true;
        boolean z2 = !this.mRootWindowContainer.hasAwakeDisplay();
        boolean z3 = this.mSleeping;
        if (!z2) {
            if (z3) {
                this.mSleeping = false;
                com.android.internal.util.FrameworkStatsLog.write(14, 2);
                startTimeTrackingFocusedActivityLocked();
                this.mTopProcessState = 2;
                android.util.Slog.d("ActivityTaskManager", "Top Process State changed to PROCESS_STATE_TOP");
                this.mTaskSupervisor.comeOutOfSleepIfNeededLocked();
            }
            this.mRootWindowContainer.applySleepTokens(true);
        } else {
            if (!this.mSleeping && z2) {
                this.mSleeping = true;
                com.android.internal.util.FrameworkStatsLog.write(14, 1);
                if (this.mCurAppTimeTracker != null) {
                    this.mCurAppTimeTracker.stop();
                }
                this.mTopProcessState = 12;
                android.util.Slog.d("ActivityTaskManager", "Top Process State changed to PROCESS_STATE_TOP_SLEEPING");
                this.mTaskSupervisor.goingToSleepLocked();
                updateResumedAppTrace(null);
            }
            z = false;
        }
        if (z) {
            updateOomAdj();
        }
    }

    void updateOomAdj() {
        this.mH.removeCallbacks(this.mUpdateOomAdjRunnable);
        this.mH.post(this.mUpdateOomAdjRunnable);
    }

    void updateCpuStats() {
        com.android.server.wm.ActivityTaskManagerService.H h = this.mH;
        final android.app.ActivityManagerInternal activityManagerInternal = this.mAmInternal;
        java.util.Objects.requireNonNull(activityManagerInternal);
        h.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                activityManagerInternal.updateCpuStats();
            }
        });
    }

    void updateBatteryStats(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda10
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                ((android.app.ActivityManagerInternal) obj).updateBatteryStats((android.content.ComponentName) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Boolean) obj5).booleanValue());
            }
        }, this.mAmInternal, activityRecord.mActivityComponent, java.lang.Integer.valueOf(activityRecord.app.mUid), java.lang.Integer.valueOf(activityRecord.mUserId), java.lang.Boolean.valueOf(z)));
    }

    void updateTopApp(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null) {
            activityRecord = this.mRootWindowContainer.getTopResumedActivity();
        }
        this.mTopApp = activityRecord != null ? activityRecord.app : null;
        if (this.mTopApp == this.mPreviousProcess) {
            this.mPreviousProcess = null;
        }
    }

    void updatePreviousProcess(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.app != null && this.mTopApp != null && activityRecord.app != this.mTopApp && activityRecord.lastVisibleTime > this.mPreviousProcessVisibleTime && activityRecord.app != this.mHomeProcess) {
            this.mPreviousProcess = activityRecord.app;
            this.mPreviousProcessVisibleTime = activityRecord.lastVisibleTime;
        }
    }

    void updateActivityUsageStats(com.android.server.wm.ActivityRecord activityRecord, int i) {
        int i2;
        android.content.ComponentName componentName;
        com.android.server.wm.Task task = activityRecord.getTask();
        android.content.ComponentName componentName2 = null;
        if (task == null) {
            i2 = -1;
            componentName = null;
        } else {
            com.android.server.wm.ActivityRecord rootActivity = task.getRootActivity();
            if (rootActivity != null) {
                componentName2 = rootActivity.mActivityComponent;
            }
            i2 = task.mTaskId;
            componentName = componentName2;
        }
        this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HeptConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda11
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7) {
                ((android.app.ActivityManagerInternal) obj).updateActivityUsageStats((android.content.ComponentName) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), (android.os.IBinder) obj5, (android.content.ComponentName) obj6, (android.app.assist.ActivityId) obj7);
            }
        }, this.mAmInternal, activityRecord.mActivityComponent, java.lang.Integer.valueOf(activityRecord.mUserId), java.lang.Integer.valueOf(i), activityRecord.token, componentName, new android.app.assist.ActivityId(i2, activityRecord.shareableActivityToken)));
    }

    void startProcessAsync(com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, java.lang.String str) {
        try {
            if (android.os.Trace.isTagEnabled(32L)) {
                android.os.Trace.traceBegin(32L, "dispatchingStartProcess:" + activityRecord.processName);
            }
            this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HeptConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda20
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7) {
                    ((android.app.ActivityManagerInternal) obj).startProcess((java.lang.String) obj2, (android.content.pm.ApplicationInfo) obj3, ((java.lang.Boolean) obj4).booleanValue(), ((java.lang.Boolean) obj5).booleanValue(), (java.lang.String) obj6, (android.content.ComponentName) obj7);
                }
            }, this.mAmInternal, activityRecord.processName, activityRecord.info.applicationInfo, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), str, activityRecord.intent.getComponent()));
            android.os.Trace.traceEnd(32L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    void setBooting(boolean z) {
        this.mAmInternal.setBooting(z);
    }

    boolean isBooting() {
        return this.mAmInternal.isBooting();
    }

    void setBooted(boolean z) {
        this.mAmInternal.setBooted(z);
    }

    boolean isBooted() {
        return this.mAmInternal.isBooted();
    }

    void postFinishBooting(final boolean z, final boolean z2) {
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskManagerService.this.lambda$postFinishBooting$13(z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postFinishBooting$13(boolean z, boolean z2) {
        if (z) {
            this.mAmInternal.finishBooting();
        }
        if (z2) {
            this.mInternal.enableScreenAfterBoot(isBooted());
        }
    }

    void setHeavyWeightProcess(com.android.server.wm.ActivityRecord activityRecord) {
        this.mHeavyWeightProcess = activityRecord.app;
        this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda17
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                ((com.android.server.wm.ActivityTaskManagerService) obj).postHeavyWeightProcessNotification((com.android.server.wm.WindowProcessController) obj2, (android.content.Intent) obj3, ((java.lang.Integer) obj4).intValue());
            }
        }, this, activityRecord.app, activityRecord.intent, java.lang.Integer.valueOf(activityRecord.mUserId)));
    }

    void clearHeavyWeightProcessIfEquals(com.android.server.wm.WindowProcessController windowProcessController) {
        if (this.mHeavyWeightProcess == null || this.mHeavyWeightProcess != windowProcessController) {
            return;
        }
        this.mHeavyWeightProcess = null;
        this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda16
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.wm.ActivityTaskManagerService) obj).cancelHeavyWeightProcessNotification(((java.lang.Integer) obj2).intValue());
            }
        }, this, java.lang.Integer.valueOf(windowProcessController.mUserId)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelHeavyWeightProcessNotification(int i) {
        android.app.INotificationManager service = android.app.NotificationManager.getService();
        if (service == null) {
            return;
        }
        try {
            service.cancelNotificationWithTag(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, (java.lang.String) null, 11, i);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.RuntimeException e2) {
            android.util.Slog.w("ActivityTaskManager", "Error canceling notification for service", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postHeavyWeightProcessNotification(com.android.server.wm.WindowProcessController windowProcessController, android.content.Intent intent, int i) {
        android.app.INotificationManager service;
        if (windowProcessController == null || (service = android.app.NotificationManager.getService()) == null) {
            return;
        }
        try {
            android.content.Context createPackageContext = this.mContext.createPackageContext(windowProcessController.mInfo.packageName, 0);
            java.lang.String string = this.mContext.getString(android.R.string.global_actions, createPackageContext.getApplicationInfo().loadLabel(createPackageContext.getPackageManager()));
            try {
                service.enqueueNotificationWithTag(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, (java.lang.String) null, 11, new android.app.Notification.Builder(createPackageContext, com.android.internal.notification.SystemNotificationChannels.HEAVY_WEIGHT_APP).setSmallIcon(android.R.drawable.stat_notify_sync_anim0).setWhen(0L).setOngoing(true).setTicker(string).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).setContentTitle(string).setContentText(this.mContext.getText(android.R.string.global_actions_airplane_mode_off_status)).setContentIntent(android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF, null, new android.os.UserHandle(i))).build(), i);
            } catch (android.os.RemoteException e) {
            } catch (java.lang.RuntimeException e2) {
                android.util.Slog.w("ActivityTaskManager", "Error showing notification for heavy-weight app", e2);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
            android.util.Slog.w("ActivityTaskManager", "Unable to create context for heavy notification", e3);
        }
    }

    android.content.IIntentSender getIntentSenderLocked(int i, java.lang.String str, java.lang.String str2, int i2, int i3, android.os.IBinder iBinder, java.lang.String str3, int i4, android.content.Intent[] intentArr, java.lang.String[] strArr, int i5, android.os.Bundle bundle) {
        com.android.server.wm.ActivityRecord activityRecord;
        if (i != 3) {
            activityRecord = null;
        } else {
            com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
            if (isInRootTaskLocked == null) {
                android.util.Slog.w("ActivityTaskManager", "Failed createPendingResult: activity " + iBinder + " not in any root task");
                return null;
            }
            if (!isInRootTaskLocked.finishing) {
                activityRecord = isInRootTaskLocked;
            } else {
                android.util.Slog.w("ActivityTaskManager", "Failed createPendingResult: activity " + isInRootTaskLocked + " is finishing");
                return null;
            }
        }
        com.android.server.am.PendingIntentRecord intentSender = this.mPendingIntentController.getIntentSender(i, str, str2, i2, i3, iBinder, str3, i4, intentArr, strArr, i5, bundle);
        if ((i5 & 536870912) != 0) {
            return intentSender;
        }
        if (i == 3) {
            if (activityRecord.pendingResults == null) {
                activityRecord.pendingResults = new java.util.HashSet<>();
            }
            activityRecord.pendingResults.add(intentSender.ref);
        }
        return intentSender;
    }

    private void startTimeTrackingFocusedActivityLocked() {
        com.android.server.wm.ActivityRecord topResumedActivity = this.mRootWindowContainer.getTopResumedActivity();
        if (!this.mSleeping && this.mCurAppTimeTracker != null && topResumedActivity != null) {
            this.mCurAppTimeTracker.start(topResumedActivity.packageName);
        }
    }

    private void updateResumedAppTrace(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        if (android.os.Trace.isTagEnabled(32L)) {
            if (this.mTracedResumedActivity != null) {
                android.os.Trace.asyncTraceForTrackEnd(32L, "Focused app", java.lang.System.identityHashCode(this.mTracedResumedActivity));
            }
            if (activityRecord != null) {
                android.os.Trace.asyncTraceForTrackBegin(32L, "Focused app", activityRecord.mActivityComponent.flattenToShortString(), java.lang.System.identityHashCode(activityRecord));
            }
        }
        this.mTracedResumedActivity = activityRecord;
    }

    boolean ensureConfigAndVisibilityAfterUpdate(com.android.server.wm.ActivityRecord activityRecord, int i) {
        com.android.server.wm.Task topDisplayFocusedRootTask;
        if ((activityRecord == null && this.mTaskSupervisor.isRootVisibilityUpdateDeferred()) || (topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask()) == null) {
            return true;
        }
        if (i != 0 && activityRecord == null) {
            activityRecord = topDisplayFocusedRootTask.topRunningActivity();
        }
        if (activityRecord == null) {
            return true;
        }
        boolean ensureActivityConfiguration = activityRecord.ensureActivityConfiguration();
        this.mRootWindowContainer.ensureActivitiesVisible(activityRecord);
        return ensureActivityConfiguration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleAppGcsLocked$14() {
        this.mAmInternal.scheduleAppGcs();
    }

    void scheduleAppGcsLocked() {
        this.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskManagerService.this.lambda$scheduleAppGcsLocked$14();
            }
        });
    }

    android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked(android.content.pm.ApplicationInfo applicationInfo) {
        return this.mCompatModePackages.compatibilityInfoForPackageLocked(applicationInfo);
    }

    android.content.pm.IPackageManager getPackageManager() {
        return android.app.AppGlobals.getPackageManager();
    }

    android.content.pm.PackageManagerInternal getPackageManagerInternalLocked() {
        if (this.mPmInternal == null) {
            this.mPmInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        }
        return this.mPmInternal;
    }

    android.content.ComponentName getSysUiServiceComponentLocked() {
        if (this.mSysUiServiceComponent == null) {
            this.mSysUiServiceComponent = getPackageManagerInternalLocked().getSystemUiServiceComponent();
        }
        return this.mSysUiServiceComponent;
    }

    com.android.server.policy.PermissionPolicyInternal getPermissionPolicyInternal() {
        if (this.mPermissionPolicyInternal == null) {
            this.mPermissionPolicyInternal = (com.android.server.policy.PermissionPolicyInternal) com.android.server.LocalServices.getService(com.android.server.policy.PermissionPolicyInternal.class);
        }
        return this.mPermissionPolicyInternal;
    }

    com.android.server.statusbar.StatusBarManagerInternal getStatusBarManagerInternal() {
        if (this.mStatusBarManagerInternal == null) {
            this.mStatusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
        }
        return this.mStatusBarManagerInternal;
    }

    com.android.server.wallpaper.WallpaperManagerInternal getWallpaperManagerInternal() {
        if (this.mWallpaperManagerInternal == null) {
            this.mWallpaperManagerInternal = (com.android.server.wallpaper.WallpaperManagerInternal) com.android.server.LocalServices.getService(com.android.server.wallpaper.WallpaperManagerInternal.class);
        }
        return this.mWallpaperManagerInternal;
    }

    com.android.server.wm.AppWarnings getAppWarningsLocked() {
        return this.mAppWarnings;
    }

    android.content.Intent getHomeIntent() {
        android.content.Intent intent = new android.content.Intent(this.mTopAction, this.mTopData != null ? android.net.Uri.parse(this.mTopData) : null);
        intent.setComponent(this.mTopComponent);
        intent.addFlags(256);
        if (this.mFactoryTest != 1) {
            intent.addCategory("android.intent.category.HOME");
        }
        return intent;
    }

    android.content.Intent getSecondaryHomeIntent(java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(this.mTopAction, this.mTopData != null ? android.net.Uri.parse(this.mTopData) : null);
        boolean z = this.mContext.getResources().getBoolean(android.R.bool.config_useGnssHardwareProvider);
        if (str == null || z) {
            intent.setPackage(this.mContext.getResources().getString(android.R.string.config_satellite_emergency_handover_intent_action));
        } else {
            intent.setPackage(str);
        }
        intent.addFlags(256);
        if (this.mFactoryTest != 1) {
            intent.addCategory("android.intent.category.SECONDARY_HOME");
        }
        return intent;
    }

    android.content.pm.ApplicationInfo getAppInfoForUser(android.content.pm.ApplicationInfo applicationInfo, int i) {
        if (applicationInfo == null) {
            return null;
        }
        android.content.pm.ApplicationInfo applicationInfo2 = new android.content.pm.ApplicationInfo(applicationInfo);
        applicationInfo2.initForUser(i);
        return applicationInfo2;
    }

    com.android.server.wm.WindowProcessController getProcessController(java.lang.String str, int i) {
        if (i == 1000) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) this.mProcessNames.getMap().get(str);
            if (sparseArray == null) {
                return null;
            }
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = sparseArray.keyAt(i2);
                if (!android.os.UserHandle.isApp(keyAt) && android.os.UserHandle.isSameUser(keyAt, i)) {
                    return (com.android.server.wm.WindowProcessController) sparseArray.valueAt(i2);
                }
            }
        }
        return (com.android.server.wm.WindowProcessController) this.mProcessNames.get(str, i);
    }

    com.android.server.wm.WindowProcessController getProcessController(android.app.IApplicationThread iApplicationThread) {
        if (iApplicationThread == null) {
            return null;
        }
        android.os.IBinder asBinder = iApplicationThread.asBinder();
        android.util.ArrayMap map = this.mProcessNames.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(size);
            for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                com.android.server.wm.WindowProcessController windowProcessController = (com.android.server.wm.WindowProcessController) sparseArray.valueAt(size2);
                if (windowProcessController.hasThread() && windowProcessController.getThread().asBinder() == asBinder) {
                    return windowProcessController;
                }
            }
        }
        return null;
    }

    com.android.server.wm.WindowProcessController getProcessController(int i, int i2) {
        com.android.server.wm.WindowProcessController process = this.mProcessMap.getProcess(i);
        if (process == null || !android.os.UserHandle.isApp(i2) || process.mUid != i2) {
            return null;
        }
        return process;
    }

    @android.annotation.Nullable
    java.lang.String getPackageNameIfUnique(int i, int i2) {
        com.android.server.wm.WindowProcessController process = this.mProcessMap.getProcess(i2);
        if (process == null || process.mUid != i) {
            android.util.Slog.w("ActivityTaskManager", "callingPackage for (uid=" + i + ", pid=" + i2 + ") has no WPC");
            return null;
        }
        java.util.List<java.lang.String> packageList = process.getPackageList();
        if (packageList.size() != 1) {
            android.util.Slog.w("ActivityTaskManager", "callingPackage for (uid=" + i + ", pid=" + i2 + ") is ambiguous: " + packageList);
            return null;
        }
        return packageList.get(0);
    }

    boolean hasActiveVisibleWindow(int i) {
        if (this.mVisibleActivityProcessTracker.hasVisibleActivity(i)) {
            return true;
        }
        return this.mActiveUids.hasNonAppVisibleWindow(i);
    }

    boolean isDeviceOwner(int i) {
        return i >= 0 && this.mDeviceOwnerUid == i;
    }

    void setDeviceOwnerUid(int i) {
        this.mDeviceOwnerUid = i;
    }

    boolean isAffiliatedProfileOwner(int i) {
        return i >= 0 && this.mProfileOwnerUids.contains(java.lang.Integer.valueOf(i)) && android.app.admin.DeviceStateCache.getInstance().hasAffiliationWithDevice(android.os.UserHandle.getUserId(i));
    }

    void setProfileOwnerUids(java.util.Set<java.lang.Integer> set) {
        this.mProfileOwnerUids = set;
    }

    void saveANRState(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        final java.io.PrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(stringWriter, false, 1024);
        fastPrintWriter.println("  ANR time: " + java.text.DateFormat.getDateTimeInstance().format(new java.util.Date()));
        if (str != null) {
            fastPrintWriter.println("  Reason: " + str);
        }
        fastPrintWriter.println();
        if (activityRecord != null) {
            com.android.server.wm.Task rootTask = activityRecord.getRootTask();
            if (rootTask != null) {
                rootTask.forAllTaskFragments(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda19
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.wm.TaskFragment) obj).dumpInner("  ", fastPrintWriter, true, null);
                    }
                });
                fastPrintWriter.println();
            }
            this.mActivityStartController.dump(fastPrintWriter, "  ", activityRecord.packageName);
            if (this.mActivityStartController.getLastStartActivity() != activityRecord) {
                activityRecord.dump(fastPrintWriter, "  ", true);
            }
        }
        com.android.server.wm.ActivityTaskSupervisor.printThisActivity(fastPrintWriter, this.mRootWindowContainer.getTopResumedActivity(), null, -1, true, "  ResumedActivity: ", null);
        this.mLockTaskController.dump(fastPrintWriter, "  ");
        this.mKeyguardController.dump(fastPrintWriter, "  ");
        fastPrintWriter.println("-------------------------------------------------------------------------------");
        fastPrintWriter.close();
        this.mLastANRState = stringWriter.toString();
    }

    void logAppTooSlow(com.android.server.wm.WindowProcessController windowProcessController, long j, java.lang.String str) {
    }

    boolean isAssociatedCompanionApp(int i, int i2) {
        java.util.Set<java.lang.Integer> set = this.mCompanionAppUidsMap.get(java.lang.Integer.valueOf(i));
        if (set == null) {
            return false;
        }
        return set.contains(java.lang.Integer.valueOf(i2));
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            throw logAndRethrowRuntimeExceptionOnTransact("ActivityTaskManager", e);
        }
    }

    static java.lang.RuntimeException logAndRethrowRuntimeExceptionOnTransact(java.lang.String str, java.lang.RuntimeException runtimeException) {
        if (!(runtimeException instanceof java.lang.SecurityException)) {
            android.util.Slog.w("ActivityTaskManager", str + " onTransact aborts UID:" + android.os.Binder.getCallingUid() + " PID:" + android.os.Binder.getCallingPid(), runtimeException);
            throw runtimeException;
        }
        throw runtimeException;
    }

    void onImeWindowSetOnDisplayArea(int i, @android.annotation.NonNull com.android.server.wm.DisplayArea displayArea) {
        if (i == com.android.server.wm.WindowManagerService.MY_PID || i < 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 2959074735946674755L, 0, null, null);
            return;
        }
        com.android.server.wm.WindowProcessController process = this.mProcessMap.getProcess(i);
        if (process == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 5668810920995272206L, 1, null, java.lang.Long.valueOf(i));
        } else {
            process.registerDisplayAreaConfigurationListener(displayArea);
        }
    }

    public void setRunningRemoteTransitionDelegate(android.app.IApplicationThread iApplicationThread) {
        com.android.server.wm.TransitionController transitionController = getTransitionController();
        if (iApplicationThread != null && transitionController.mRemotePlayer.reportRunning(iApplicationThread)) {
            return;
        }
        this.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "setRunningRemoteTransition");
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.WindowProcessController processController = getProcessController(callingPid, callingUid);
                if (processController == null || !processController.isRunningRemoteTransition()) {
                    java.lang.String str = "Can't call setRunningRemoteTransition from a process (pid=" + callingPid + " uid=" + callingUid + ") which isn't itself running a remote transition.";
                    android.util.Slog.e("ActivityTaskManager", str);
                    throw new java.lang.SecurityException(str);
                }
                com.android.server.wm.WindowProcessController processController2 = getProcessController(iApplicationThread);
                if (processController2 == null) {
                    android.util.Slog.w("ActivityTaskManager", "setRunningRemoteTransition: no process for " + iApplicationThread);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                transitionController.mRemotePlayer.update(processController2, true, false);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void registerScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) {
        this.mAmInternal.enforceCallingPermission("android.permission.DETECT_SCREEN_CAPTURE", "registerScreenCaptureObserver");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.registerCaptureObserver(iScreenCaptureObserver);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void unregisterScreenCaptureObserver(android.os.IBinder iBinder, android.app.IScreenCaptureObserver iScreenCaptureObserver) {
        this.mAmInternal.enforceCallingPermission("android.permission.DETECT_SCREEN_CAPTURE", "unregisterScreenCaptureObserver");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.unregisterCaptureObserver(iScreenCaptureObserver);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void registerCompatScaleProvider(int i, @android.annotation.NonNull com.android.server.wm.CompatScaleProvider compatScaleProvider) {
        this.mCompatModePackages.registerCompatScaleProvider(i, compatScaleProvider);
    }

    void unregisterCompatScaleProvider(int i) {
        this.mCompatModePackages.unregisterCompatScaleProvider(i);
    }

    boolean instrumentationSourceHasPermission(int i, java.lang.String str) {
        com.android.server.wm.WindowProcessController process;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                process = this.mProcessMap.getProcess(i);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return process != null && process.isInstrumenting() && checkPermission(str, -1, process.getInstrumentationSourceUid()) == 0;
    }

    private com.android.server.wm.SafeActivityOptions createSafeActivityOptionsWithBalAllowed(@android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        if (activityOptions == null) {
            activityOptions = android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1);
        } else if (activityOptions.getPendingIntentBackgroundActivityStartMode() == 0) {
            activityOptions.setPendingIntentBackgroundActivityStartMode(1);
        }
        return new com.android.server.wm.SafeActivityOptions(activityOptions);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.wm.SafeActivityOptions createSafeActivityOptionsWithBalAllowed(@android.annotation.Nullable android.os.Bundle bundle) {
        return createSafeActivityOptionsWithBalAllowed(android.app.ActivityOptions.fromBundle(bundle));
    }

    final class H extends android.os.Handler {
        static final int ADD_WAKEFULNESS_ANIMATING_REASON = 5;
        static final int END_POWER_MODE_UNKNOWN_VISIBILITY_MSG = 3;
        static final int FIRST_ACTIVITY_TASK_MSG = 100;
        static final int FIRST_SUPERVISOR_TASK_MSG = 200;
        static final int REMOVE_WAKEFULNESS_ANIMATING_REASON = 6;
        static final int REPORT_TIME_TRACKER_MSG = 1;
        static final int RESUME_FG_APP_SWITCH_MSG = 4;

        H(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    ((com.android.server.am.AppTimeTracker) message.obj).deliverResult(com.android.server.wm.ActivityTaskManagerService.this.mContext);
                    return;
                case 2:
                default:
                    return;
                case 3:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.ActivityTaskManagerService.this.mRetainPowerModeAndTopProcessState = false;
                            com.android.server.wm.ActivityTaskManagerService.this.endPowerMode(4);
                            if (com.android.server.wm.ActivityTaskManagerService.this.mTopApp != null && com.android.server.wm.ActivityTaskManagerService.this.mTopProcessState == 12) {
                                com.android.server.wm.ActivityTaskManagerService.this.mTopApp.updateProcessInfo(false, false, true, false);
                            }
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 4:
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            if (com.android.server.wm.ActivityTaskManagerService.this.mAppSwitchesState == 0) {
                                com.android.server.wm.ActivityTaskManagerService.this.mAppSwitchesState = 1;
                            }
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 5:
                    com.android.server.wm.WindowProcessController windowProcessController = (com.android.server.wm.WindowProcessController) message.obj;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock3 = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock3) {
                        try {
                            windowProcessController.addAnimatingReason(2);
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                case 6:
                    com.android.server.wm.WindowProcessController windowProcessController2 = (com.android.server.wm.WindowProcessController) message.obj;
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock4 = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock4) {
                        try {
                            windowProcessController2.removeAnimatingReason(2);
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    android.os.Trace.instant(32L, "finishWakefulnessAnimating");
                    return;
            }
        }
    }

    final class UiHandler extends android.os.Handler {
        static final int DISMISS_DIALOG_UI_MSG = 1;

        public UiHandler() {
            super(com.android.server.UiThread.get().getLooper(), null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    ((android.app.Dialog) message.obj).dismiss();
                    break;
            }
        }
    }

    final class LocalService extends com.android.server.wm.ActivityTaskManagerInternal {
        LocalService() {
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer createSleepTokenAcquirer(@android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            return com.android.server.wm.ActivityTaskManagerService.this.new SleepTokenAcquirerImpl(str);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.content.ComponentName getHomeActivityForUser(int i) {
            android.content.ComponentName componentName;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord defaultDisplayHomeActivityForUser = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.getDefaultDisplayHomeActivityForUser(i);
                    componentName = defaultDisplayHomeActivityForUser == null ? null : defaultDisplayHomeActivityForUser.mActivityComponent;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return componentName;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onLocalVoiceInteractionStarted(android.os.IBinder iBinder, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.onLocalVoiceInteractionStartedLocked(iBinder, iVoiceInteractionSession, iVoiceInteractor);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public java.util.List<com.android.server.wm.ActivityAssistInfo> getTopVisibleActivities() {
            java.util.List<com.android.server.wm.ActivityAssistInfo> topVisibleActivities;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    topVisibleActivities = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.getTopVisibleActivities();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return topVisibleActivities;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean hasResumedActivity(int i) {
            return com.android.server.wm.ActivityTaskManagerService.this.mVisibleActivityProcessTracker.hasResumedActivity(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setBackgroundActivityStartCallback(@android.annotation.Nullable com.android.server.wm.BackgroundActivityStartCallback backgroundActivityStartCallback) {
            com.android.server.wm.ActivityTaskManagerService.this.mBackgroundActivityStartCallback = backgroundActivityStartCallback;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setAccessibilityServiceUids(android.util.IntArray intArray) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mAccessibilityServiceUids = intArray.toArray();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        /* JADX WARN: Finally extract failed */
        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int startActivitiesAsPackage(java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, android.content.Intent[] intentArr, android.os.Bundle bundle) {
            int i2;
            java.util.Objects.requireNonNull(intentArr, "intents");
            java.lang.String[] strArr = new java.lang.String[intentArr.length];
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            for (int i3 = 0; i3 < intentArr.length; i3++) {
                try {
                    try {
                        strArr[i3] = intentArr[i3].resolveTypeIfNeeded(com.android.server.wm.ActivityTaskManagerService.this.mContext.getContentResolver());
                    } catch (android.os.RemoteException e) {
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            try {
                int packageUid = android.app.AppGlobals.getPackageManager().getPackageUid(str, 268435456L, i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                i2 = packageUid;
            } catch (android.os.RemoteException e2) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                i2 = 0;
                return com.android.server.wm.ActivityTaskManagerService.this.getActivityStartController().startActivitiesInPackage(i2, str, str2, intentArr, strArr, null, com.android.server.wm.SafeActivityOptions.fromBundle(bundle), i, false, null, android.app.BackgroundStartPrivileges.NONE);
            }
            return com.android.server.wm.ActivityTaskManagerService.this.getActivityStartController().startActivitiesInPackage(i2, str, str2, intentArr, strArr, null, com.android.server.wm.SafeActivityOptions.fromBundle(bundle), i, false, null, android.app.BackgroundStartPrivileges.NONE);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int startActivitiesInPackage(int i, int i2, int i3, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent[] intentArr, java.lang.String[] strArr, android.os.IBinder iBinder, com.android.server.wm.SafeActivityOptions safeActivityOptions, int i4, boolean z, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
            com.android.server.wm.ActivityTaskManagerService.this.assertPackageMatchesCallingUid(str);
            return com.android.server.wm.ActivityTaskManagerService.this.getActivityStartController().startActivitiesInPackage(i, i2, i3, str, str2, intentArr, strArr, iBinder, safeActivityOptions, i4, z, pendingIntentRecord, backgroundStartPrivileges);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int startActivityInPackage(int i, int i2, int i3, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.IBinder iBinder, java.lang.String str4, int i4, int i5, com.android.server.wm.SafeActivityOptions safeActivityOptions, int i6, com.android.server.wm.Task task, java.lang.String str5, boolean z, com.android.server.am.PendingIntentRecord pendingIntentRecord, android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
            com.android.server.wm.ActivityTaskManagerService.this.assertPackageMatchesCallingUid(str);
            return com.android.server.wm.ActivityTaskManagerService.this.getActivityStartController().startActivityInPackage(i, i2, i3, str, str2, intent, str3, iBinder, str4, i4, i5, safeActivityOptions, i6, task, str5, z, pendingIntentRecord, backgroundStartPrivileges);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.content.Intent intent, @android.annotation.Nullable android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) {
            return com.android.server.wm.ActivityTaskManagerService.this.startActivityAsUser(iApplicationThread, str, str2, intent, intent.resolveTypeIfNeeded(com.android.server.wm.ActivityTaskManagerService.this.mContext.getContentResolver()), iBinder, null, 0, i, null, bundle, i2, false);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int startActivityWithScreenshot(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable android.os.Bundle bundle, int i3) {
            return com.android.server.wm.ActivityTaskManagerService.this.getActivityStartController().obtainStarter(intent, "startActivityWithScreenshot").setCallingUid(i).setCallingPid(i2).setCallingPackage(str).setResultTo(iBinder).setActivityOptions(com.android.server.wm.ActivityTaskManagerService.this.createSafeActivityOptionsWithBalAllowed(bundle)).setRealCallingUid(android.os.Binder.getCallingUid()).setUserId(com.android.server.wm.ActivityTaskManagerService.this.getActivityStartController().checkTargetUser(i3, false, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), "startActivityWithScreenshot")).setBackgroundStartPrivileges(android.app.BackgroundStartPrivileges.ALLOW_BAL).setFreezeScreen(true).execute();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setVr2dDisplayId(int i) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -1123414663662718691L, 1, null, java.lang.Long.valueOf(i));
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mVr2dDisplayId = i;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int getDisplayId(android.os.IBinder iBinder) {
            int displayId;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked == null) {
                        throw new java.lang.IllegalArgumentException("getDisplayId: No activity record matching token=" + iBinder);
                    }
                    displayId = forTokenLocked.getDisplayId();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return displayId;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void registerScreenObserver(com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver screenObserver) {
            com.android.server.wm.ActivityTaskManagerService.this.mScreenObservers.add(screenObserver);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void unregisterScreenObserver(com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver screenObserver) {
            com.android.server.wm.ActivityTaskManagerService.this.mScreenObservers.remove(screenObserver);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isCallerRecents(int i) {
            return com.android.server.wm.ActivityTaskManagerService.this.isCallerRecents(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isRecentsComponentHomeActivity(int i) {
            return com.android.server.wm.ActivityTaskManagerService.this.getRecentTasks().isRecentsComponentHomeActivity(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean checkCanCloseSystemDialogs(int i, int i2, @android.annotation.Nullable java.lang.String str) {
            return com.android.server.wm.ActivityTaskManagerService.this.checkCanCloseSystemDialogs(i, i2, str);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean canCloseSystemDialogs(int i, int i2) {
            return com.android.server.wm.ActivityTaskManagerService.this.canCloseSystemDialogs(i, i2);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void notifyActiveVoiceInteractionServiceChanged(android.content.ComponentName componentName) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mActiveVoiceInteractionServiceComponent = componentName;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void notifyActiveDreamChanged(@android.annotation.Nullable android.content.ComponentName componentName) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mActiveDreamComponent = componentName;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.app.IAppTask startDreamActivity(@android.annotation.NonNull android.content.Intent intent, int i, int i2) {
            return com.android.server.wm.ActivityTaskManagerService.this.startDreamActivityInternal(intent, i, i2);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setAllowAppSwitches(@android.annotation.NonNull java.lang.String str, int i, int i2) {
            if (!com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.isUserRunning(i2, 1)) {
                return;
            }
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap = com.android.server.wm.ActivityTaskManagerService.this.mAllowAppSwitchUids.get(i2);
                    if (arrayMap == null) {
                        if (i < 0) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        } else {
                            arrayMap = new android.util.ArrayMap<>();
                            com.android.server.wm.ActivityTaskManagerService.this.mAllowAppSwitchUids.put(i2, arrayMap);
                        }
                    }
                    if (i < 0) {
                        arrayMap.remove(str);
                    } else {
                        arrayMap.put(str, java.lang.Integer.valueOf(i));
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onUserStopped(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.getRecentTasks().unloadUserDataFromMemoryLocked(i);
                    com.android.server.wm.ActivityTaskManagerService.this.mAllowAppSwitchUids.remove(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isGetTasksAllowed(java.lang.String str, int i, int i2) {
            return com.android.server.wm.ActivityTaskManagerService.this.isGetTasksAllowed(str, i, i2);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onProcessAdded(com.android.server.wm.WindowProcessController windowProcessController) {
            synchronized (com.android.server.wm.ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                com.android.server.wm.ActivityTaskManagerService.this.mProcessNames.put(windowProcessController.mName, windowProcessController.mUid, windowProcessController);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onProcessRemoved(java.lang.String str, int i) {
            synchronized (com.android.server.wm.ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                com.android.server.wm.ActivityTaskManagerService.this.mProcessNames.remove(str, i);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onCleanUpApplicationRecord(com.android.server.wm.WindowProcessController windowProcessController) {
            synchronized (com.android.server.wm.ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                try {
                    if (windowProcessController == com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess) {
                        com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess = null;
                    }
                    if (windowProcessController == com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess) {
                        com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int getTopProcessState() {
            if (com.android.server.wm.ActivityTaskManagerService.this.mRetainPowerModeAndTopProcessState) {
                return 2;
            }
            return com.android.server.wm.ActivityTaskManagerService.this.mTopProcessState;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean useTopSchedGroupForTopProcess() {
            return com.android.server.wm.ActivityTaskManagerService.this.mDemoteTopAppReasons == 0;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void clearHeavyWeightProcessIfEquals(com.android.server.wm.WindowProcessController windowProcessController) {
            synchronized (com.android.server.wm.ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                com.android.server.wm.ActivityTaskManagerService.this.clearHeavyWeightProcessIfEquals(windowProcessController);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void finishHeavyWeightApp() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess != null) {
                        com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess.finishActivities();
                    }
                    com.android.server.wm.ActivityTaskManagerService.this.clearHeavyWeightProcessIfEquals(com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isSleeping() {
            return com.android.server.wm.ActivityTaskManagerService.this.mSleeping;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isShuttingDown() {
            return com.android.server.wm.ActivityTaskManagerService.this.mShuttingDown;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean shuttingDown(boolean z, int i) {
            boolean shutdownLocked;
            com.android.server.wm.ActivityTaskManagerService.this.mShuttingDown = true;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.prepareForShutdown();
                    com.android.server.wm.ActivityTaskManagerService.this.updateEventDispatchingLocked(z);
                    com.android.server.wm.ActivityTaskManagerService.this.notifyTaskPersisterLocked(null, true);
                    shutdownLocked = com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.shutdownLocked(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return shutdownLocked;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void enableScreenAfterBoot(boolean z) {
            com.android.server.am.EventLogTags.writeBootProgressEnableScreen(android.os.SystemClock.uptimeMillis());
            com.android.server.wm.ActivityTaskManagerService.this.mWindowManager.enableScreenAfterBoot();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.updateEventDispatchingLocked(z);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean showStrictModeViolationDialog() {
            boolean z;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    z = (!com.android.server.wm.ActivityTaskManagerService.this.mShowDialogs || com.android.server.wm.ActivityTaskManagerService.this.mSleeping || com.android.server.wm.ActivityTaskManagerService.this.mShuttingDown) ? false : true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void showSystemReadyErrorDialogsIfNeeded() {
            if (android.os.Trace.isTagEnabled(32L)) {
                android.os.Trace.traceBegin(32L, "showSystemReadyErrorDialogs");
            }
            boolean isBuildConsistent = android.os.Build.isBuildConsistent();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    try {
                        if (android.app.AppGlobals.getPackageManager().hasSystemUidErrors()) {
                            android.util.Slog.e("ActivityTaskManager", "UIDs on the system are inconsistent, you need to wipe your data partition or your device will be unstable.");
                            com.android.server.wm.ActivityTaskManagerService.this.mUiHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.wm.ActivityTaskManagerService.LocalService.this.lambda$showSystemReadyErrorDialogsIfNeeded$0();
                                }
                            });
                        }
                    } catch (android.os.RemoteException e) {
                    }
                    if (!isBuildConsistent) {
                        android.util.Slog.e("ActivityTaskManager", "Build fingerprint is not consistent, warning user");
                        com.android.server.wm.ActivityTaskManagerService.this.mUiHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.wm.ActivityTaskManagerService.LocalService.this.lambda$showSystemReadyErrorDialogsIfNeeded$1();
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            android.os.Trace.traceEnd(32L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showSystemReadyErrorDialogsIfNeeded$0() {
            if (com.android.server.wm.ActivityTaskManagerService.this.mShowDialogs) {
                com.android.server.am.BaseErrorDialog baseErrorDialog = new com.android.server.am.BaseErrorDialog(com.android.server.wm.ActivityTaskManagerService.this.mUiContext);
                baseErrorDialog.getWindow().setType(2010);
                baseErrorDialog.setCancelable(false);
                baseErrorDialog.setTitle(com.android.server.wm.ActivityTaskManagerService.this.mUiContext.getText(android.R.string.alternative_face_setup_notification_content));
                baseErrorDialog.setMessage(com.android.server.wm.ActivityTaskManagerService.this.mUiContext.getText(android.R.string.status_bar_sensors_off));
                baseErrorDialog.setButton(-1, com.android.server.wm.ActivityTaskManagerService.this.mUiContext.getText(android.R.string.ok), com.android.server.wm.ActivityTaskManagerService.this.mUiHandler.obtainMessage(1, baseErrorDialog));
                baseErrorDialog.show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showSystemReadyErrorDialogsIfNeeded$1() {
            if (com.android.server.wm.ActivityTaskManagerService.this.mShowDialogs) {
                com.android.server.am.BaseErrorDialog baseErrorDialog = new com.android.server.am.BaseErrorDialog(com.android.server.wm.ActivityTaskManagerService.this.mUiContext);
                baseErrorDialog.getWindow().setType(2010);
                baseErrorDialog.setCancelable(false);
                baseErrorDialog.setTitle(com.android.server.wm.ActivityTaskManagerService.this.mUiContext.getText(android.R.string.alternative_face_setup_notification_content));
                baseErrorDialog.setMessage(com.android.server.wm.ActivityTaskManagerService.this.mUiContext.getText(android.R.string.status_bar_secure));
                baseErrorDialog.setButton(-1, com.android.server.wm.ActivityTaskManagerService.this.mUiContext.getText(android.R.string.ok), com.android.server.wm.ActivityTaskManagerService.this.mUiHandler.obtainMessage(1, baseErrorDialog));
                baseErrorDialog.show();
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onProcessMapped(int i, com.android.server.wm.WindowProcessController windowProcessController) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mProcessMap.put(i, windowProcessController);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onProcessUnMapped(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mProcessMap.remove(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onPackageDataCleared(java.lang.String str, int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mCompatModePackages.handlePackageDataClearedLocked(str);
                    com.android.server.wm.ActivityTaskManagerService.this.mAppWarnings.onPackageDataCleared(str);
                    com.android.server.wm.ActivityTaskManagerService.this.mPackageConfigPersister.onPackageDataCleared(str, i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onPackageUninstalled(java.lang.String str, int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mAppWarnings.onPackageUninstalled(str);
                    com.android.server.wm.ActivityTaskManagerService.this.mCompatModePackages.handlePackageUninstalledLocked(str);
                    com.android.server.wm.ActivityTaskManagerService.this.mPackageConfigPersister.onPackageUninstall(str, i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onPackageAdded(java.lang.String str, boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mCompatModePackages.handlePackageAddedLocked(str, z);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onPackageReplaced(android.content.pm.ApplicationInfo applicationInfo) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.updateActivityApplicationInfo(applicationInfo);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.content.res.CompatibilityInfo compatibilityInfoForPackage(android.content.pm.ApplicationInfo applicationInfo) {
            android.content.res.CompatibilityInfo compatibilityInfoForPackageLocked;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    compatibilityInfoForPackageLocked = com.android.server.wm.ActivityTaskManagerService.this.compatibilityInfoForPackageLocked(applicationInfo);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return compatibilityInfoForPackageLocked;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void sendActivityResult(int i, android.os.IBinder iBinder, java.lang.String str, int i2, int i3, android.content.Intent intent) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null || isInRootTaskLocked.getRootTask() == null) {
                        return;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    com.android.server.uri.NeededUriGrants collectGrants = com.android.server.wm.ActivityTaskManagerService.this.collectGrants(intent, isInRootTaskLocked);
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            isInRootTaskLocked.sendResult(i, str, i2, i3, intent, new android.os.Binder(), collectGrants);
                        } finally {
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void clearPendingResultForActivity(android.os.IBinder iBinder, java.lang.ref.WeakReference<com.android.server.am.PendingIntentRecord> weakReference) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null && isInRootTaskLocked.pendingResults != null) {
                        isInRootTaskLocked.pendingResults.remove(weakReference);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.content.ComponentName getActivityName(android.os.IBinder iBinder) {
            android.content.ComponentName component;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    component = isInRootTaskLocked != null ? isInRootTaskLocked.intent.getComponent() : null;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return component;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens getAttachedNonFinishingActivityForTask(int i, android.os.IBinder iBinder) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.anyTaskForId(i, 0);
                    if (anyTaskForId == null) {
                        android.util.Slog.w("ActivityTaskManager", "getApplicationThreadForTopActivity failed: Requested task not found");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    final java.util.ArrayList arrayList = new java.util.ArrayList();
                    anyTaskForId.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.ActivityTaskManagerService.LocalService.lambda$getAttachedNonFinishingActivityForTask$2(arrayList, (com.android.server.wm.ActivityRecord) obj);
                        }
                    });
                    if (arrayList.size() <= 0) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    if (iBinder != null || !((com.android.server.wm.ActivityRecord) arrayList.get(0)).attachedToProcess()) {
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) arrayList.get(i2);
                            if (activityRecord.shareableActivityToken == iBinder && activityRecord.attachedToProcess()) {
                                com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens activityTokens = new com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens(activityRecord.token, activityRecord.assistToken, activityRecord.app.getThread(), activityRecord.shareableActivityToken, activityRecord.getUid());
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return activityTokens;
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.ActivityRecord activityRecord2 = (com.android.server.wm.ActivityRecord) arrayList.get(0);
                    com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens activityTokens2 = new com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens(activityRecord2.token, activityRecord2.assistToken, activityRecord2.app.getThread(), activityRecord2.shareableActivityToken, activityRecord2.getUid());
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return activityTokens2;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getAttachedNonFinishingActivityForTask$2(java.util.List list, com.android.server.wm.ActivityRecord activityRecord) {
            if (!activityRecord.finishing) {
                list.add(activityRecord);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.content.IIntentSender getIntentSender(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i2, int i3, android.os.IBinder iBinder, java.lang.String str3, int i4, android.content.Intent[] intentArr, java.lang.String[] strArr, int i5, android.os.Bundle bundle) {
            android.content.IIntentSender intentSenderLocked;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    intentSenderLocked = com.android.server.wm.ActivityTaskManagerService.this.getIntentSenderLocked(i, str, str2, i2, i3, iBinder, str3, i4, intentArr, strArr, i5, bundle);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return intentSenderLocked;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public com.android.server.wm.ActivityServiceConnectionsHolder getServiceConnectionsHolder(android.os.IBinder iBinder) {
            com.android.server.wm.ActivityRecord forToken = com.android.server.wm.ActivityRecord.forToken(iBinder);
            if (forToken == null || !forToken.inHistory) {
                return null;
            }
            return forToken.getOrCreateServiceConnectionsHolder();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.content.Intent getHomeIntent() {
            android.content.Intent homeIntent;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    homeIntent = com.android.server.wm.ActivityTaskManagerService.this.getHomeIntent();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return homeIntent;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean startHomeActivity(int i, java.lang.String str) {
            boolean startHomeOnDisplay;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    startHomeOnDisplay = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnDisplay(i, str, 0);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return startHomeOnDisplay;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean startHomeOnDisplay(int i, java.lang.String str, int i2, boolean z, boolean z2) {
            boolean startHomeOnDisplay;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    startHomeOnDisplay = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnDisplay(i, str, i2, z, z2);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return startHomeOnDisplay;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean startHomeOnAllDisplays(int i, java.lang.String str) {
            boolean startHomeOnAllDisplays;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    startHomeOnAllDisplays = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.startHomeOnAllDisplays(i, str);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return startHomeOnAllDisplays;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void updateTopComponentForFactoryTest() {
            final java.lang.CharSequence text;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mFactoryTest != 1) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    android.content.pm.ResolveInfo resolveActivity = com.android.server.wm.ActivityTaskManagerService.this.mContext.getPackageManager().resolveActivity(new android.content.Intent("android.intent.action.FACTORY_TEST"), 1024);
                    if (resolveActivity != null) {
                        android.content.pm.ActivityInfo activityInfo = resolveActivity.activityInfo;
                        android.content.pm.ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                        if ((1 & applicationInfo.flags) != 0) {
                            com.android.server.wm.ActivityTaskManagerService.this.mTopAction = "android.intent.action.FACTORY_TEST";
                            com.android.server.wm.ActivityTaskManagerService.this.mTopData = null;
                            com.android.server.wm.ActivityTaskManagerService.this.mTopComponent = new android.content.ComponentName(applicationInfo.packageName, activityInfo.name);
                            text = null;
                        } else {
                            text = com.android.server.wm.ActivityTaskManagerService.this.mContext.getResources().getText(android.R.string.face_error_no_space);
                        }
                    } else {
                        text = com.android.server.wm.ActivityTaskManagerService.this.mContext.getResources().getText(android.R.string.face_error_lockout_screen_lock);
                    }
                    if (text == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.ActivityTaskManagerService.this.mTopAction = null;
                    com.android.server.wm.ActivityTaskManagerService.this.mTopData = null;
                    com.android.server.wm.ActivityTaskManagerService.this.mTopComponent = null;
                    com.android.server.wm.ActivityTaskManagerService.this.mUiHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.wm.ActivityTaskManagerService.LocalService.this.lambda$updateTopComponentForFactoryTest$3(text);
                        }
                    });
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateTopComponentForFactoryTest$3(java.lang.CharSequence charSequence) {
            new com.android.server.wm.FactoryErrorDialog(com.android.server.wm.ActivityTaskManagerService.this.mUiContext, charSequence).show();
            com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.ensureBootCompleted();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void handleAppDied(com.android.server.wm.WindowProcessController windowProcessController, boolean z, java.lang.Runnable runnable) {
            synchronized (com.android.server.wm.ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.beginDeferResume();
                    try {
                        boolean handleAppDied = windowProcessController.handleAppDied();
                        if (!z && handleAppDied) {
                            com.android.server.wm.ActivityTaskManagerService.this.deferWindowLayout();
                            try {
                                com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.ensureVisibilityOnVisibleActivityDiedOrCrashed("handleAppDied");
                                com.android.server.wm.ActivityTaskManagerService.this.continueWindowLayout();
                            } catch (java.lang.Throwable th) {
                                com.android.server.wm.ActivityTaskManagerService.this.continueWindowLayout();
                                throw th;
                            }
                        }
                    } finally {
                        com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.endDeferResume();
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
            if (windowProcessController.isInstrumenting()) {
                runnable.run();
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void closeSystemDialogs(java.lang.String str) {
            com.android.server.wm.ActivityTaskManagerService.enforceNotIsolatedCaller("closeSystemDialogs");
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            if (checkCanCloseSystemDialogs(callingPid, callingUid, null)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        if (callingUid >= 10000) {
                            try {
                                com.android.server.wm.WindowProcessController process = com.android.server.wm.ActivityTaskManagerService.this.mProcessMap.getProcess(callingPid);
                                if (!process.isPerceptible()) {
                                    android.util.Slog.w("ActivityTaskManager", "Ignoring closeSystemDialogs " + str + " from background process " + process);
                                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                            } catch (java.lang.Throwable th) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        com.android.server.wm.ActivityTaskManagerService.this.mWindowManager.closeSystemDialogs(str);
                        com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.closeSystemDialogActivities(str);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.broadcastCloseSystemDialogs(str);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void cleanupDisabledPackageComponents(java.lang.String str, java.util.Set<java.lang.String> set, int i, boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.finishDisabledPackageActivities(str, set, true, false, i, false) && z) {
                        com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                        com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.scheduleIdle();
                    }
                    com.android.server.wm.ActivityTaskManagerService.this.getRecentTasks().cleanupDisabledPackageTasksLocked(str, set, i);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean onForceStopPackage(java.lang.String str, boolean z, boolean z2, int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean finishDisabledPackageActivities = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.finishDisabledPackageActivities(str, null, z, z2, i, true);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return finishDisabledPackageActivities;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void resumeTopActivities(boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    if (z) {
                        com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.scheduleIdle();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void preBindApplication(com.android.server.wm.WindowProcessController windowProcessController) {
            synchronized (com.android.server.wm.ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.getActivityMetricsLogger().notifyBindApplication(windowProcessController.mInfo);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean attachApplication(com.android.server.wm.WindowProcessController windowProcessController) throws android.os.RemoteException {
            boolean attachApplication;
            synchronized (com.android.server.wm.ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                if (android.os.Trace.isTagEnabled(32L)) {
                    android.os.Trace.traceBegin(32L, "attachApplication:" + windowProcessController.mName);
                }
                try {
                    attachApplication = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.attachApplication(windowProcessController);
                } finally {
                    android.os.Trace.traceEnd(32L);
                }
            }
            return attachApplication;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void notifyLockedProfile(int i) {
            try {
                if (!android.app.AppGlobals.getPackageManager().isUidPrivileged(android.os.Binder.getCallingUid())) {
                    throw new java.lang.SecurityException("Only privileged app can call notifyLockedProfile");
                }
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            if (com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.shouldConfirmCredentials(i)) {
                                com.android.server.wm.ActivityTaskManagerService.this.maybeHideLockedProfileActivityLocked();
                                com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.lockAllProfileTasks(i);
                            }
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (android.os.RemoteException e) {
                throw new java.lang.SecurityException("Fail to check is caller a privileged app", e);
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void startConfirmDeviceCredentialIntent(android.content.Intent intent, android.os.Bundle bundle) {
            com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("startConfirmDeviceCredentialIntent");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        intent.addFlags(276824064);
                        com.android.server.wm.ActivityTaskManagerService.this.mContext.startActivityAsUser(intent, (bundle != null ? new android.app.ActivityOptions(bundle) : android.app.ActivityOptions.makeBasic()).toBundle(), android.os.UserHandle.CURRENT);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th2;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void writeActivitiesToProto(android.util.proto.ProtoOutputStream protoOutputStream) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.dumpDebug(protoOutputStream, 1146756268034L, 0);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i, boolean z, boolean z2, java.lang.String str2, int i2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_CMD.equals(str) && !com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_SHORT_CMD.equals(str)) {
                        if (com.android.server.wm.ActivityTaskManagerService.DUMP_LASTANR_CMD.equals(str)) {
                            com.android.server.wm.ActivityTaskManagerService.this.dumpLastANRLocked(printWriter);
                        } else if (com.android.server.wm.ActivityTaskManagerService.DUMP_LASTANR_TRACES_CMD.equals(str)) {
                            com.android.server.wm.ActivityTaskManagerService.this.dumpLastANRTracesLocked(printWriter);
                        } else if (com.android.server.wm.ActivityTaskManagerService.DUMP_STARTER_CMD.equals(str)) {
                            com.android.server.wm.ActivityTaskManagerService.this.dumpActivityStarterLocked(printWriter, str2);
                        } else if (com.android.server.wm.ActivityTaskManagerService.DUMP_CONTAINERS_CMD.equals(str)) {
                            com.android.server.wm.ActivityTaskManagerService.this.dumpActivityContainersLocked(printWriter);
                        } else if (com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_CMD.equals(str) || com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD.equals(str)) {
                            if (com.android.server.wm.ActivityTaskManagerService.this.getRecentTasks() != null) {
                                com.android.server.wm.ActivityTaskManagerService.this.getRecentTasks().dump(printWriter, z, str2);
                            }
                        } else if (com.android.server.wm.ActivityTaskManagerService.DUMP_TOP_RESUMED_ACTIVITY.equals(str)) {
                            com.android.server.wm.ActivityTaskManagerService.this.dumpTopResumedActivityLocked(printWriter);
                        } else if (com.android.server.wm.ActivityTaskManagerService.DUMP_VISIBLE_ACTIVITIES.equals(str)) {
                            com.android.server.wm.ActivityTaskManagerService.this.dumpVisibleActivitiesLocked(printWriter, i2);
                        }
                    }
                    com.android.server.wm.ActivityTaskManagerService.this.dumpActivitiesLocked(fileDescriptor, printWriter, strArr, i, z, z2, str2, i2);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean dumpForProcesses(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, boolean z, java.lang.String str, int i, boolean z2, boolean z3, int i2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess != null) {
                        if (str != null) {
                            if (com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess.containsPackage(str)) {
                            }
                        }
                        if (z2) {
                            printWriter.println();
                            z2 = false;
                        }
                        printWriter.println("  mHomeProcess: " + com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess);
                    }
                    if (com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess != null && (str == null || com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess.containsPackage(str))) {
                        if (z2) {
                            printWriter.println();
                            z2 = false;
                        }
                        printWriter.println("  mPreviousProcess: " + com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess);
                    }
                    if (z && (com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess == null || str == null || com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess.containsPackage(str))) {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                        sb.append("  mPreviousProcessVisibleTime: ");
                        android.util.TimeUtils.formatDuration(com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcessVisibleTime, sb);
                        printWriter.println(sb);
                    }
                    if (com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess != null && (str == null || com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess.containsPackage(str))) {
                        if (z2) {
                            printWriter.println();
                            z2 = false;
                        }
                        printWriter.println("  mHeavyWeightProcess: " + com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess);
                    }
                    if (str == null) {
                        printWriter.println("  mGlobalConfiguration: " + com.android.server.wm.ActivityTaskManagerService.this.getGlobalConfiguration());
                        com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.dumpDisplayConfigs(printWriter, "  ");
                    }
                    if (z) {
                        com.android.server.wm.Task topDisplayFocusedRootTask = com.android.server.wm.ActivityTaskManagerService.this.getTopDisplayFocusedRootTask();
                        if (str == null && topDisplayFocusedRootTask != null) {
                            printWriter.println("  mConfigWillChange: " + topDisplayFocusedRootTask.mConfigWillChange);
                        }
                        if (com.android.server.wm.ActivityTaskManagerService.this.mCompatModePackages.getPackages().size() > 0) {
                            boolean z4 = false;
                            for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : com.android.server.wm.ActivityTaskManagerService.this.mCompatModePackages.getPackages().entrySet()) {
                                java.lang.String key = entry.getKey();
                                int intValue = entry.getValue().intValue();
                                if (str == null || str.equals(key)) {
                                    if (!z4) {
                                        printWriter.println("  mScreenCompatPackages:");
                                        z4 = true;
                                    }
                                    printWriter.println("    " + key + ": " + intValue);
                                }
                            }
                        }
                    }
                    if (str == null) {
                        printWriter.println("  mWakefulness=" + android.os.PowerManagerInternal.wakefulnessToString(i2));
                        printWriter.println("  mSleepTokens=" + com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.mSleepTokens);
                        if (com.android.server.wm.ActivityTaskManagerService.this.mRunningVoice != null) {
                            printWriter.println("  mRunningVoice=" + com.android.server.wm.ActivityTaskManagerService.this.mRunningVoice);
                            printWriter.println("  mVoiceWakeLock" + com.android.server.wm.ActivityTaskManagerService.this.mVoiceWakeLock);
                        }
                        printWriter.println("  mSleeping=" + com.android.server.wm.ActivityTaskManagerService.this.mSleeping);
                        printWriter.println("  mShuttingDown=" + com.android.server.wm.ActivityTaskManagerService.this.mShuttingDown + " mTestPssMode=" + z3);
                        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                        sb2.append("  mVrController=");
                        sb2.append(com.android.server.wm.ActivityTaskManagerService.this.mVrController);
                        printWriter.println(sb2.toString());
                    }
                    if (com.android.server.wm.ActivityTaskManagerService.this.mCurAppTimeTracker != null) {
                        com.android.server.wm.ActivityTaskManagerService.this.mCurAppTimeTracker.dumpWithHeader(printWriter, "  ", true);
                    }
                    if (com.android.server.wm.ActivityTaskManagerService.this.mAllowAppSwitchUids.size() > 0) {
                        boolean z5 = false;
                        for (int i3 = 0; i3 < com.android.server.wm.ActivityTaskManagerService.this.mAllowAppSwitchUids.size(); i3++) {
                            android.util.ArrayMap<java.lang.String, java.lang.Integer> valueAt = com.android.server.wm.ActivityTaskManagerService.this.mAllowAppSwitchUids.valueAt(i3);
                            for (int i4 = 0; i4 < valueAt.size(); i4++) {
                                if (str == null || android.os.UserHandle.getAppId(valueAt.valueAt(i4).intValue()) == i) {
                                    if (z2) {
                                        printWriter.println();
                                        z2 = false;
                                    }
                                    if (!z5) {
                                        printWriter.println("  mAllowAppSwitchUids:");
                                        z5 = true;
                                    }
                                    printWriter.print("    User ");
                                    printWriter.print(com.android.server.wm.ActivityTaskManagerService.this.mAllowAppSwitchUids.keyAt(i3));
                                    printWriter.print(": Type ");
                                    printWriter.print(valueAt.keyAt(i4));
                                    printWriter.print(" = ");
                                    android.os.UserHandle.formatUid(printWriter, valueAt.valueAt(i4).intValue());
                                    printWriter.println();
                                }
                            }
                        }
                    }
                    if (str == null) {
                        if (com.android.server.wm.ActivityTaskManagerService.this.mController != null) {
                            printWriter.println("  mController=" + com.android.server.wm.ActivityTaskManagerService.this.mController + " mControllerIsAMonkey=" + com.android.server.wm.ActivityTaskManagerService.this.mControllerIsAMonkey);
                        }
                        printWriter.println("  mGoingToSleepWakeLock=" + com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.mGoingToSleepWakeLock);
                        printWriter.println("  mLaunchingActivityWakeLock=" + com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.mLaunchingActivityWakeLock);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return z2;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void writeProcessesToProto(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str, int i, boolean z) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                if (str == null) {
                    try {
                        com.android.server.wm.ActivityTaskManagerService.this.getGlobalConfiguration().dumpDebug(protoOutputStream, 1146756268051L);
                        com.android.server.wm.Task topDisplayFocusedRootTask = com.android.server.wm.ActivityTaskManagerService.this.getTopDisplayFocusedRootTask();
                        if (topDisplayFocusedRootTask != null) {
                            protoOutputStream.write(1133871366165L, topDisplayFocusedRootTask.mConfigWillChange);
                        }
                        com.android.server.wm.ActivityTaskManagerService.this.writeSleepStateToProto(protoOutputStream, i, z);
                        if (com.android.server.wm.ActivityTaskManagerService.this.mRunningVoice != null) {
                            long start = protoOutputStream.start(1146756268060L);
                            protoOutputStream.write(1138166333441L, com.android.server.wm.ActivityTaskManagerService.this.mRunningVoice.toString());
                            com.android.server.wm.ActivityTaskManagerService.this.mVoiceWakeLock.dumpDebug(protoOutputStream, 1146756268034L);
                            protoOutputStream.end(start);
                        }
                        com.android.server.wm.ActivityTaskManagerService.this.mVrController.dumpDebug(protoOutputStream, 1146756268061L);
                        if (com.android.server.wm.ActivityTaskManagerService.this.mController != null) {
                            long start2 = protoOutputStream.start(1146756268069L);
                            protoOutputStream.write(1138166333441L, com.android.server.wm.ActivityTaskManagerService.this.mController.toString());
                            protoOutputStream.write(1133871366146L, com.android.server.wm.ActivityTaskManagerService.this.mControllerIsAMonkey);
                            protoOutputStream.end(start2);
                        }
                        com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.mGoingToSleepWakeLock.dumpDebug(protoOutputStream, 1146756268079L);
                        com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.mLaunchingActivityWakeLock.dumpDebug(protoOutputStream, 1146756268080L);
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                if (com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess != null && (str == null || com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess.containsPackage(str))) {
                    com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess.dumpDebug(protoOutputStream, 1146756268047L);
                }
                if (com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess != null && (str == null || com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess.containsPackage(str))) {
                    com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess.dumpDebug(protoOutputStream, 1146756268048L);
                    protoOutputStream.write(1112396529681L, com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcessVisibleTime);
                }
                if (com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess != null && (str == null || com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess.containsPackage(str))) {
                    com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess.dumpDebug(protoOutputStream, 1146756268050L);
                }
                for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : com.android.server.wm.ActivityTaskManagerService.this.mCompatModePackages.getPackages().entrySet()) {
                    java.lang.String key = entry.getKey();
                    int intValue = entry.getValue().intValue();
                    if (str == null || str.equals(key)) {
                        long start3 = protoOutputStream.start(2246267895830L);
                        protoOutputStream.write(1138166333441L, key);
                        protoOutputStream.write(1120986464258L, intValue);
                        protoOutputStream.end(start3);
                    }
                }
                if (com.android.server.wm.ActivityTaskManagerService.this.mCurAppTimeTracker != null) {
                    com.android.server.wm.ActivityTaskManagerService.this.mCurAppTimeTracker.dumpDebug(protoOutputStream, 1146756268063L, true);
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean dumpActivity(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String str, java.lang.String[] strArr, int i, boolean z, boolean z2, boolean z3, int i2, int i3) {
            return com.android.server.wm.ActivityTaskManagerService.this.dumpActivity(fileDescriptor, printWriter, str, strArr, i, z, z2, z3, i2, i3);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void dumpForOom(java.io.PrintWriter printWriter) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    printWriter.println("  mHomeProcess: " + com.android.server.wm.ActivityTaskManagerService.this.mHomeProcess);
                    printWriter.println("  mPreviousProcess: " + com.android.server.wm.ActivityTaskManagerService.this.mPreviousProcess);
                    if (com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess != null) {
                        printWriter.println("  mHeavyWeightProcess: " + com.android.server.wm.ActivityTaskManagerService.this.mHeavyWeightProcess);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean canGcNow() {
            boolean z;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    z = isSleeping() || com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.allResumedActivitiesIdle();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public com.android.server.wm.WindowProcessController getTopApp() {
            return com.android.server.wm.ActivityTaskManagerService.this.mTopApp;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void scheduleDestroyAllActivities(java.lang.String str) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.scheduleDestroyAllActivities(str);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void removeUser(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.removeUser(i);
                    com.android.server.wm.ActivityTaskManagerService.this.mPackageConfigPersister.removeUser(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean switchUser(int i, com.android.server.am.UserState userState) {
            boolean switchUser;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    switchUser = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.switchUser(i, userState);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return switchUser;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onHandleAppCrash(@android.annotation.NonNull com.android.server.wm.WindowProcessController windowProcessController) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    windowProcessController.handleAppCrash();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int finishTopCrashedActivities(com.android.server.wm.WindowProcessController windowProcessController, java.lang.String str) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.deferWindowLayout();
                    try {
                        com.android.server.wm.Task finishTopCrashedActivities = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.finishTopCrashedActivities(windowProcessController, str);
                        if (finishTopCrashedActivities == null) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return -1;
                        }
                        com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.ensureVisibilityOnVisibleActivityDiedOrCrashed(str);
                        int i = finishTopCrashedActivities.mTaskId;
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return i;
                    } finally {
                        com.android.server.wm.ActivityTaskManagerService.this.continueWindowLayout();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onUidActive(int i, int i2) {
            com.android.server.wm.ActivityTaskManagerService.this.mActiveUids.onUidActive(i, i2);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onUidInactive(int i) {
            com.android.server.wm.ActivityTaskManagerService.this.mActiveUids.onUidInactive(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onUidProcStateChanged(int i, int i2) {
            com.android.server.wm.ActivityTaskManagerService.this.mActiveUids.onUidProcStateChanged(i, i2);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean handleAppCrashInActivityController(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, long j, java.lang.String str4, java.lang.Runnable runnable) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mController == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    java.lang.Runnable runnable2 = null;
                    try {
                        if (!com.android.server.wm.ActivityTaskManagerService.this.mController.appCrashed(str, i, str2, str3, j, str4)) {
                            runnable2 = runnable;
                        }
                    } catch (android.os.RemoteException e) {
                        com.android.server.wm.ActivityTaskManagerService.this.mController = null;
                        com.android.server.Watchdog.getInstance().setActivityController(null);
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    if (runnable2 == null) {
                        return false;
                    }
                    runnable2.run();
                    return true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void removeRecentTasksByPackageName(java.lang.String str, int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRecentTasks.removeTasksByPackageName(str, i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void cleanupRecentTasksForUser(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRecentTasks.cleanupLocked(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void loadRecentTasksForUser(int i) {
            com.android.server.wm.ActivityTaskManagerService.this.mRecentTasks.loadRecentTasksIfNeeded(i);
            com.android.server.wm.ActivityTaskManagerService.this.mPackageConfigPersister.loadUserPackages(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void onPackagesSuspendedChanged(java.lang.String[] strArr, boolean z, int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mRecentTasks.onPackagesSuspendedChanged(strArr, z, i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void flushRecentTasks() {
            com.android.server.wm.ActivityTaskManagerService.this.mRecentTasks.flush();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void clearLockedTasks(java.lang.String str) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.getLockTaskController().clearLockedTasks(str);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void updateUserConfiguration() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    android.content.res.Configuration configuration = new android.content.res.Configuration(com.android.server.wm.ActivityTaskManagerService.this.getGlobalConfiguration());
                    int currentUserId = com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.getCurrentUserId();
                    android.provider.Settings.System.adjustConfigurationForUser(com.android.server.wm.ActivityTaskManagerService.this.mContext.getContentResolver(), configuration, currentUserId, android.provider.Settings.System.canWrite(com.android.server.wm.ActivityTaskManagerService.this.mContext));
                    com.android.server.wm.ActivityTaskManagerService.this.updateConfigurationLocked(configuration, null, false, false, currentUserId, false);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean canShowErrorDialogs() {
            boolean z;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    z = false;
                    if (com.android.server.wm.ActivityTaskManagerService.this.mShowDialogs) {
                        if (!com.android.server.wm.ActivityTaskManagerService.this.mSleeping) {
                            if (!com.android.server.wm.ActivityTaskManagerService.this.mShuttingDown) {
                                if (!com.android.server.wm.ActivityTaskManagerService.this.mKeyguardController.isKeyguardOrAodShowing(0)) {
                                    if (!com.android.server.wm.ActivityTaskManagerService.this.hasUserRestriction("no_system_error_dialogs", com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.getCurrentUserId())) {
                                        if (android.os.UserManager.isDeviceInDemoMode(com.android.server.wm.ActivityTaskManagerService.this.mContext)) {
                                            if (!com.android.server.wm.ActivityTaskManagerService.this.mAmInternal.getCurrentUser().isDemo()) {
                                            }
                                        }
                                        z = true;
                                    }
                                }
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return z;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setProfileApp(java.lang.String str) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mProfileApp = str;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setProfileProc(com.android.server.wm.WindowProcessController windowProcessController) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mProfileProc = windowProcessController;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setProfilerInfo(android.app.ProfilerInfo profilerInfo) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mProfilerInfo = profilerInfo;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public com.android.server.wm.ActivityMetricsLaunchObserverRegistry getLaunchObserverRegistry() {
            com.android.server.wm.ActivityMetricsLaunchObserverRegistry launchObserverRegistry;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    launchObserverRegistry = com.android.server.wm.ActivityTaskManagerService.this.mTaskSupervisor.getActivityMetricsLogger().getLaunchObserverRegistry();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return launchObserverRegistry;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        @android.annotation.Nullable
        public android.os.IBinder getUriPermissionOwnerForActivity(@android.annotation.NonNull android.os.IBinder iBinder) {
            android.os.Binder externalToken;
            com.android.server.wm.ActivityTaskManagerService.enforceNotIsolatedCaller("getUriPermissionOwnerForActivity");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    externalToken = isInRootTaskLocked == null ? null : isInRootTaskLocked.getUriPermissionsLocked().getExternalToken();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return externalToken;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.window.TaskSnapshot getTaskSnapshotBlocking(int i, boolean z) {
            return com.android.server.wm.ActivityTaskManagerService.this.getTaskSnapshot(i, z);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isUidForeground(int i) {
            return com.android.server.wm.ActivityTaskManagerService.this.hasActiveVisibleWindow(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setDeviceOwnerUid(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.setDeviceOwnerUid(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setProfileOwnerUids(java.util.Set<java.lang.Integer> set) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.setProfileOwnerUids(set);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void setCompanionAppUids(int i, java.util.Set<java.lang.Integer> set) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityTaskManagerService.this.mCompanionAppUidsMap.put(java.lang.Integer.valueOf(i), set);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isBaseOfLockedTask(java.lang.String str) {
            boolean isBaseOfLockedTask;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    isBaseOfLockedTask = com.android.server.wm.ActivityTaskManagerService.this.getLockTaskController().isBaseOfLockedTask(str);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return isBaseOfLockedTask;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater createPackageConfigurationUpdater() {
            return new com.android.server.wm.PackageConfigurationUpdaterImpl(android.os.Binder.getCallingPid(), com.android.server.wm.ActivityTaskManagerService.this);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public com.android.server.wm.ActivityTaskManagerInternal.PackageConfigurationUpdater createPackageConfigurationUpdater(java.lang.String str, int i) {
            return new com.android.server.wm.PackageConfigurationUpdaterImpl(str, i, com.android.server.wm.ActivityTaskManagerService.this);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        @android.annotation.Nullable
        public com.android.server.wm.ActivityTaskManagerInternal.PackageConfig getApplicationConfig(java.lang.String str, int i) {
            return com.android.server.wm.ActivityTaskManagerService.this.mPackageConfigPersister.findPackageConfiguration(str, i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean hasSystemAlertWindowPermission(int i, int i2, java.lang.String str) {
            return com.android.server.wm.ActivityTaskManagerService.this.hasSystemAlertWindowPermission(i, i2, str);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void registerActivityStartInterceptor(int i, com.android.server.wm.ActivityInterceptorCallback activityInterceptorCallback) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.ActivityTaskManagerService.this.mActivityInterceptorCallbacks.contains(i)) {
                        throw new java.lang.IllegalArgumentException("Duplicate id provided: " + i);
                    }
                    if (activityInterceptorCallback == null) {
                        throw new java.lang.IllegalArgumentException("The passed ActivityInterceptorCallback can not be null");
                    }
                    if (!com.android.server.wm.ActivityInterceptorCallback.isValidOrderId(i)) {
                        throw new java.lang.IllegalArgumentException("Provided id " + i + " is not in range of valid ids for system services [0,5] nor in range of valid ids for mainline module services [1000,1001]");
                    }
                    com.android.server.wm.ActivityTaskManagerService.this.mActivityInterceptorCallbacks.put(i, activityInterceptorCallback);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void unregisterActivityStartInterceptor(int i) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!com.android.server.wm.ActivityTaskManagerService.this.mActivityInterceptorCallbacks.contains(i)) {
                        throw new java.lang.IllegalArgumentException("ActivityInterceptorCallback with id (" + i + ") is not registered");
                    }
                    com.android.server.wm.ActivityTaskManagerService.this.mActivityInterceptorCallbacks.remove(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public android.app.ActivityManager.RecentTaskInfo getMostRecentTaskFromBackground() {
            java.util.List<android.app.ActivityManager.RunningTaskInfo> tasks = com.android.server.wm.ActivityTaskManagerService.this.getTasks(1);
            if (tasks.size() > 0) {
                android.app.ActivityManager.RunningTaskInfo runningTaskInfo = tasks.get(0);
                for (android.app.ActivityManager.RecentTaskInfo recentTaskInfo : com.android.server.wm.ActivityTaskManagerService.this.getRecentTasks(2, 2, com.android.server.wm.ActivityTaskManagerService.this.mContext.getUserId()).getList()) {
                    if (recentTaskInfo.id != runningTaskInfo.id) {
                        return recentTaskInfo;
                    }
                }
                return null;
            }
            android.util.Slog.i("ActivityTaskManager", "No running task found!");
            return null;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public java.util.List<android.app.ActivityManager.AppTask> getAppTasks(java.lang.String str, int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.List appTasks = com.android.server.wm.ActivityTaskManagerService.this.getAppTasks(str, i);
            int size = appTasks.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(new android.app.ActivityManager.AppTask(android.app.IAppTask.Stub.asInterface((android.os.IBinder) appTasks.get(i2))));
            }
            return arrayList;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public int getTaskToShowPermissionDialogOn(java.lang.String str, int i) {
            int taskToShowPermissionDialogOn;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    taskToShowPermissionDialogOn = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.getTaskToShowPermissionDialogOn(str, i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return taskToShowPermissionDialogOn;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void restartTaskActivityProcessIfVisible(int i, final java.lang.String str) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskManagerService.this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = com.android.server.wm.ActivityTaskManagerService.this.mRootWindowContainer.anyTaskForId(i, 0);
                    if (anyTaskForId == null) {
                        android.util.Slog.w("ActivityTaskManager", "Failed to restart Activity. No task found for id: " + i);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.ActivityRecord activity = anyTaskForId.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityTaskManagerService$LocalService$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$restartTaskActivityProcessIfVisible$4;
                            lambda$restartTaskActivityProcessIfVisible$4 = com.android.server.wm.ActivityTaskManagerService.LocalService.lambda$restartTaskActivityProcessIfVisible$4(str, (com.android.server.wm.ActivityRecord) obj);
                            return lambda$restartTaskActivityProcessIfVisible$4;
                        }
                    });
                    if (activity == null) {
                        android.util.Slog.w("ActivityTaskManager", "Failed to restart Activity. No Activity found for package name: " + str + " in task: " + i);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    activity.restartProcessIfVisible();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$restartTaskActivityProcessIfVisible$4(java.lang.String str, com.android.server.wm.ActivityRecord activityRecord) {
            return str.equals(activityRecord.packageName) && !activityRecord.finishing;
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void registerTaskStackListener(android.app.ITaskStackListener iTaskStackListener) {
            com.android.server.wm.ActivityTaskManagerService.this.registerTaskStackListener(iTaskStackListener);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void unregisterTaskStackListener(android.app.ITaskStackListener iTaskStackListener) {
            com.android.server.wm.ActivityTaskManagerService.this.unregisterTaskStackListener(iTaskStackListener);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void registerCompatScaleProvider(int i, @android.annotation.NonNull com.android.server.wm.CompatScaleProvider compatScaleProvider) {
            com.android.server.wm.ActivityTaskManagerService.this.registerCompatScaleProvider(i, compatScaleProvider);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public void unregisterCompatScaleProvider(int i) {
            com.android.server.wm.ActivityTaskManagerService.this.unregisterCompatScaleProvider(i);
        }

        @Override // com.android.server.wm.ActivityTaskManagerInternal
        public boolean isAssistDataAllowed() {
            return com.android.server.wm.ActivityTaskManagerService.this.isAssistDataAllowed();
        }
    }

    static boolean isPip2ExperimentEnabled() {
        com.android.wm.shell.Flags.enablePip2Implementation();
        return false;
    }

    public boolean shouldForceLongScreen(java.lang.String str) {
        return this.mLineageActivityManager.shouldForceLongScreen(str);
    }
}
