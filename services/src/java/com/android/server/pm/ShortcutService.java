package com.android.server.pm;

/* loaded from: classes2.dex */
public class ShortcutService extends android.content.pm.IShortcutService.Stub {
    private static final java.lang.String ATTR_VALUE = "value";
    private static final long CALLBACK_DELAY = 100;
    static final boolean DEBUG = false;
    static final boolean DEBUG_LOAD = false;
    static final boolean DEBUG_PROCSTATE = false;
    static final boolean DEBUG_REBOOT = false;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_ICON_PERSIST_QUALITY = 100;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_MAX_ICON_DIMENSION_DP = 96;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_MAX_ICON_DIMENSION_LOWRAM_DP = 48;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_MAX_SHORTCUTS_PER_ACTIVITY = 15;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_MAX_SHORTCUTS_PER_APP = 100;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_MAX_UPDATES_PER_INTERVAL = 10;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_RESET_INTERVAL_SEC = 86400;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_SAVE_DELAY_MS = 3000;
    static final java.lang.String DIRECTORY_BITMAPS = "bitmaps";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DIRECTORY_DUMP = "shortcut_dump";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DIRECTORY_PER_USER = "shortcut_service";
    private static final java.lang.String DUMMY_MAIN_ACTIVITY = "android.__dummy__";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String FILENAME_BASE_STATE = "shortcut_service.xml";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String FILENAME_USER_PACKAGES = "shortcuts.xml";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String FILENAME_USER_PACKAGES_RESERVE_COPY = "shortcuts.xml.reservecopy";
    private static final java.lang.String KEY_ICON_SIZE = "iconSize";
    private static final java.lang.String KEY_LOW_RAM = "lowRam";
    private static final java.lang.String KEY_SHORTCUT = "shortcut";
    private static final java.lang.String LAUNCHER_INTENT_CATEGORY = "android.intent.category.LAUNCHER";
    static final int OPERATION_ADD = 1;
    static final int OPERATION_SET = 0;
    static final int OPERATION_UPDATE = 2;
    private static final int PACKAGE_MATCH_FLAGS = 795136;
    private static final int PROCESS_STATE_FOREGROUND_THRESHOLD = 5;
    private static final int SYSTEM_APP_MASK = 129;
    static final java.lang.String TAG = "ShortcutService";
    private static final java.lang.String TAG_LAST_RESET_TIME = "last_reset_time";
    private static final java.lang.String TAG_ROOT = "root";
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final java.util.concurrent.atomic.AtomicBoolean mBootCompleted;
    private android.content.ComponentName mChooserActivity;
    final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.lang.Integer> mDirtyUserIds;
    private final android.os.Handler mHandler;
    private final android.content.pm.IPackageManager mIPackageManager;
    private android.graphics.Bitmap.CompressFormat mIconPersistFormat;
    private int mIconPersistQuality;
    private final boolean mIsAppSearchEnabled;
    private int mLastLockedUser;

    @com.android.internal.annotations.GuardedBy({"mWtfLock"})
    private java.lang.Exception mLastWtfStacktrace;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<android.content.pm.ShortcutServiceInternal.ShortcutChangeListener> mListeners;
    private final java.lang.Object mLock;
    private int mMaxIconDimension;
    private int mMaxShortcuts;
    private int mMaxShortcutsPerApp;
    int mMaxUpdatesPerInterval;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.internal.logging.MetricsLogger mMetricsLogger;
    private final java.lang.Object mNonPersistentUsersLock;
    private final android.app.role.OnRoleHoldersChangedListener mOnRoleHoldersChangedListener;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @com.android.internal.annotations.VisibleForTesting
    final android.content.BroadcastReceiver mPackageMonitor;
    private final java.util.concurrent.atomic.AtomicLong mRawLastResetTime;
    final android.content.BroadcastReceiver mReceiver;
    private long mResetInterval;
    private final android.app.role.RoleManager mRoleManager;
    int mSaveDelayMillis;
    private final java.lang.Runnable mSaveDirtyInfoRunner;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<android.content.pm.LauncherApps.ShortcutChangeCallback> mShortcutChangeCallbacks;
    private final com.android.server.pm.ShortcutDumpFiles mShortcutDumpFiles;

    @com.android.internal.annotations.GuardedBy({"mNonPersistentUsersLock"})
    private final android.util.SparseArray<com.android.server.pm.ShortcutNonPersistentUser> mShortcutNonPersistentUsers;
    private final com.android.server.pm.ShortcutRequestPinProcessor mShortcutRequestPinProcessor;
    private final java.util.concurrent.atomic.AtomicBoolean mShutdown;
    private final android.content.BroadcastReceiver mShutdownReceiver;
    private final com.android.internal.util.StatLogger mStatLogger;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseLongArray mUidLastForegroundElapsedTime;
    private final android.app.IUidObserver mUidObserver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseIntArray mUidState;

    @com.android.internal.annotations.GuardedBy({"mUnlockedUsers"})
    final android.util.SparseBooleanArray mUnlockedUsers;
    private final android.app.IUriGrantsManager mUriGrantsManager;
    private final com.android.server.uri.UriGrantsManagerInternal mUriGrantsManagerInternal;
    private final android.os.IBinder mUriPermissionOwner;
    private final android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal;
    final com.android.server.pm.UserManagerInternal mUserManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.pm.ShortcutUser> mUsers;

    @com.android.internal.annotations.GuardedBy({"mWtfLock"})
    private int mWtfCount;
    private final java.lang.Object mWtfLock;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String DEFAULT_ICON_PERSIST_FORMAT = android.graphics.Bitmap.CompressFormat.PNG.name();
    private static final java.util.List<android.content.pm.ResolveInfo> EMPTY_RESOLVE_INFO = new java.util.ArrayList(0);
    private static final java.util.function.Predicate<android.content.pm.ResolveInfo> ACTIVITY_NOT_EXPORTED = new java.util.function.Predicate<android.content.pm.ResolveInfo>() { // from class: com.android.server.pm.ShortcutService.1
        @Override // java.util.function.Predicate
        public boolean test(android.content.pm.ResolveInfo resolveInfo) {
            return !resolveInfo.activityInfo.exported;
        }
    };
    private static final java.util.function.Predicate<android.content.pm.ResolveInfo> ACTIVITY_NOT_INSTALLED = new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda25
        @Override // java.util.function.Predicate
        public final boolean test(java.lang.Object obj) {
            boolean lambda$static$0;
            lambda$static$0 = com.android.server.pm.ShortcutService.lambda$static$0((android.content.pm.ResolveInfo) obj);
            return lambda$static$0;
        }
    };
    private static final java.util.function.Predicate<android.content.pm.PackageInfo> PACKAGE_NOT_INSTALLED = new java.util.function.Predicate<android.content.pm.PackageInfo>() { // from class: com.android.server.pm.ShortcutService.2
        @Override // java.util.function.Predicate
        public boolean test(android.content.pm.PackageInfo packageInfo) {
            return !com.android.server.pm.ShortcutService.isInstalled(packageInfo);
        }
    };

    @com.android.internal.annotations.VisibleForTesting
    interface ConfigConstants {
        public static final java.lang.String KEY_ICON_FORMAT = "icon_format";
        public static final java.lang.String KEY_ICON_QUALITY = "icon_quality";
        public static final java.lang.String KEY_MAX_ICON_DIMENSION_DP = "max_icon_dimension_dp";
        public static final java.lang.String KEY_MAX_ICON_DIMENSION_DP_LOWRAM = "max_icon_dimension_dp_lowram";
        public static final java.lang.String KEY_MAX_SHORTCUTS = "max_shortcuts";
        public static final java.lang.String KEY_MAX_SHORTCUTS_PER_APP = "max_shortcuts_per_app";
        public static final java.lang.String KEY_MAX_UPDATES_PER_INTERVAL = "max_updates_per_interval";
        public static final java.lang.String KEY_RESET_INTERVAL_SEC = "reset_interval_sec";
        public static final java.lang.String KEY_SAVE_DELAY_MILLIS = "save_delay_ms";
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ShortcutOperation {
    }

    @com.android.internal.annotations.VisibleForTesting
    interface Stats {
        public static final int ASYNC_PRELOAD_USER_DELAY = 15;
        public static final int CHECK_LAUNCHER_ACTIVITY = 12;
        public static final int CHECK_PACKAGE_CHANGES = 8;
        public static final int CLEANUP_DANGLING_BITMAPS = 5;
        public static final int COUNT = 17;
        public static final int GET_ACTIVITY_WITH_METADATA = 6;
        public static final int GET_APPLICATION_INFO = 3;
        public static final int GET_APPLICATION_RESOURCES = 9;
        public static final int GET_DEFAULT_HOME = 0;
        public static final int GET_DEFAULT_LAUNCHER = 16;
        public static final int GET_INSTALLED_PACKAGES = 7;
        public static final int GET_LAUNCHER_ACTIVITY = 11;
        public static final int GET_PACKAGE_INFO = 1;
        public static final int GET_PACKAGE_INFO_WITH_SIG = 2;
        public static final int IS_ACTIVITY_ENABLED = 13;
        public static final int LAUNCHER_PERMISSION_CHECK = 4;
        public static final int PACKAGE_UPDATE_CHECK = 14;
        public static final int RESOURCE_NAME_LOOKUP = 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$0(android.content.pm.ResolveInfo resolveInfo) {
        return !isInstalled(resolveInfo.activityInfo);
    }

    static class InvalidFileFormatException extends java.lang.Exception {
        public InvalidFileFormatException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }
    }

    public ShortcutService(android.content.Context context) {
        this(context, getBgLooper(), false);
    }

    private static android.os.Looper getBgLooper() {
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(KEY_SHORTCUT, 10);
        handlerThread.start();
        return handlerThread.getLooper();
    }

    @com.android.internal.annotations.VisibleForTesting
    ShortcutService(android.content.Context context, android.os.Looper looper, boolean z) {
        this.mLock = new java.lang.Object();
        this.mNonPersistentUsersLock = new java.lang.Object();
        this.mWtfLock = new java.lang.Object();
        this.mListeners = new java.util.ArrayList<>(1);
        this.mShortcutChangeCallbacks = new java.util.ArrayList<>(1);
        this.mRawLastResetTime = new java.util.concurrent.atomic.AtomicLong(0L);
        this.mUsers = new android.util.SparseArray<>();
        this.mShortcutNonPersistentUsers = new android.util.SparseArray<>();
        this.mUidState = new android.util.SparseIntArray();
        this.mUidLastForegroundElapsedTime = new android.util.SparseLongArray();
        this.mDirtyUserIds = new java.util.ArrayList();
        this.mBootCompleted = new java.util.concurrent.atomic.AtomicBoolean();
        this.mShutdown = new java.util.concurrent.atomic.AtomicBoolean();
        this.mUnlockedUsers = new android.util.SparseBooleanArray();
        this.mStatLogger = new com.android.internal.util.StatLogger(new java.lang.String[]{"getHomeActivities()", "Launcher permission check", "getPackageInfo()", "getPackageInfo(SIG)", "getApplicationInfo", "cleanupDanglingBitmaps", "getActivity+metadata", "getInstalledPackages", "checkPackageChanges", "getApplicationResources", "resourceNameLookup", "getLauncherActivity", "checkLauncherActivity", "isActivityEnabled", "packageUpdateCheck", "asyncPreloadUserDelay", "getDefaultLauncher()"});
        this.mWtfCount = 0;
        this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        this.mOnRoleHoldersChangedListener = new com.android.server.pm.ShortcutService.AnonymousClass3();
        this.mUidObserver = new com.android.server.pm.ShortcutService.AnonymousClass4();
        this.mSaveDirtyInfoRunner = new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutService.this.saveDirtyInfo();
            }
        };
        this.mLastLockedUser = -1;
        this.mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.ShortcutService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (!com.android.server.pm.ShortcutService.this.mBootCompleted.get()) {
                    return;
                }
                try {
                    if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                        com.android.server.pm.ShortcutService.this.handleLocaleChanged();
                    }
                } catch (java.lang.Exception e) {
                    com.android.server.pm.ShortcutService.this.wtf("Exception in mReceiver.onReceive", e);
                }
            }
        };
        this.mPackageMonitor = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.ShortcutService.6
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                if (intExtra == -10000) {
                    android.util.Slog.w(com.android.server.pm.ShortcutService.TAG, "Intent broadcast does not contain user handle: " + intent);
                    return;
                }
                java.lang.String action = intent.getAction();
                long injectClearCallingIdentity = com.android.server.pm.ShortcutService.this.injectClearCallingIdentity();
                try {
                    try {
                    } catch (java.lang.Exception e) {
                        com.android.server.pm.ShortcutService.this.wtf("Exception in mPackageMonitor.onReceive", e);
                    }
                    synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                        if (!com.android.server.pm.ShortcutService.this.isUserUnlockedL(intExtra)) {
                            com.android.server.pm.ShortcutService.this.injectRestoreCallingIdentity(injectClearCallingIdentity);
                            return;
                        }
                        android.net.Uri data = intent.getData();
                        java.lang.String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                        if (schemeSpecificPart == null) {
                            android.util.Slog.w(com.android.server.pm.ShortcutService.TAG, "Intent broadcast does not contain package name: " + intent);
                            com.android.server.pm.ShortcutService.this.injectRestoreCallingIdentity(injectClearCallingIdentity);
                            return;
                        }
                        char c = 0;
                        boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                        switch (action.hashCode()) {
                            case 172491798:
                                if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 267468725:
                                if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 525384130:
                                if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1544582882:
                                if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                if (!booleanExtra) {
                                    com.android.server.pm.ShortcutService.this.handlePackageAdded(schemeSpecificPart, intExtra);
                                    break;
                                } else {
                                    com.android.server.pm.ShortcutService.this.handlePackageUpdateFinished(schemeSpecificPart, intExtra);
                                    break;
                                }
                            case 1:
                                if (!booleanExtra) {
                                    com.android.server.pm.ShortcutService.this.handlePackageRemoved(schemeSpecificPart, intExtra);
                                    break;
                                }
                                break;
                            case 2:
                                com.android.server.pm.ShortcutService.this.handlePackageChanged(schemeSpecificPart, intExtra);
                                break;
                            case 3:
                                com.android.server.pm.ShortcutService.this.handlePackageDataCleared(schemeSpecificPart, intExtra);
                                break;
                        }
                    }
                } finally {
                    com.android.server.pm.ShortcutService.this.injectRestoreCallingIdentity(injectClearCallingIdentity);
                }
            }
        };
        this.mShutdownReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.ShortcutService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                    try {
                        if (com.android.server.pm.ShortcutService.this.mHandler.hasCallbacks(com.android.server.pm.ShortcutService.this.mSaveDirtyInfoRunner)) {
                            com.android.server.pm.ShortcutService.this.mHandler.removeCallbacks(com.android.server.pm.ShortcutService.this.mSaveDirtyInfoRunner);
                            com.android.server.pm.ShortcutService.this.forEachLoadedUserLocked(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$7$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    ((com.android.server.pm.ShortcutUser) obj).cancelAllInFlightTasks();
                                }
                            });
                            com.android.server.pm.ShortcutService.this.saveDirtyInfo();
                        }
                        com.android.server.pm.ShortcutService.this.mShutdown.set(true);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        com.android.server.LocalServices.addService(android.content.pm.ShortcutServiceInternal.class, new com.android.server.pm.ShortcutService.LocalService());
        this.mHandler = new android.os.Handler(looper);
        this.mIPackageManager = android.app.AppGlobals.getPackageManager();
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        java.util.Objects.requireNonNull(packageManagerInternal);
        this.mPackageManagerInternal = packageManagerInternal;
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        java.util.Objects.requireNonNull(userManagerInternal);
        this.mUserManagerInternal = userManagerInternal;
        android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        java.util.Objects.requireNonNull(usageStatsManagerInternal);
        this.mUsageStatsManagerInternal = usageStatsManagerInternal;
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        java.util.Objects.requireNonNull(activityManagerInternal);
        this.mActivityManagerInternal = activityManagerInternal;
        android.app.IUriGrantsManager service = android.app.UriGrantsManager.getService();
        java.util.Objects.requireNonNull(service);
        this.mUriGrantsManager = service;
        com.android.server.uri.UriGrantsManagerInternal uriGrantsManagerInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
        java.util.Objects.requireNonNull(uriGrantsManagerInternal);
        this.mUriGrantsManagerInternal = uriGrantsManagerInternal;
        this.mUriPermissionOwner = this.mUriGrantsManagerInternal.newUriPermissionOwner(TAG);
        android.app.role.RoleManager roleManager = (android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class);
        java.util.Objects.requireNonNull(roleManager);
        this.mRoleManager = roleManager;
        this.mShortcutRequestPinProcessor = new com.android.server.pm.ShortcutRequestPinProcessor(this, this.mLock);
        this.mShortcutDumpFiles = new com.android.server.pm.ShortcutDumpFiles(this);
        this.mIsAppSearchEnabled = android.provider.DeviceConfig.getBoolean("systemui", "shortcut_appsearch_integration", false) && !injectIsLowRamDevice();
        if (z) {
            return;
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        intentFilter.setPriority(1000);
        this.mContext.registerReceiverAsUser(this.mPackageMonitor, android.os.UserHandle.ALL, intentFilter, null, this.mHandler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter2.setPriority(1000);
        this.mContext.registerReceiverAsUser(this.mReceiver, android.os.UserHandle.ALL, intentFilter2, null, this.mHandler);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter3.setPriority(1000);
        this.mContext.registerReceiverAsUser(this.mShutdownReceiver, android.os.UserHandle.SYSTEM, intentFilter3, null, this.mHandler);
        injectRegisterUidObserver(this.mUidObserver, 3);
        injectRegisterRoleHoldersListener(this.mOnRoleHoldersChangedListener);
    }

    boolean isAppSearchEnabled() {
        return this.mIsAppSearchEnabled;
    }

    long getStatStartTime() {
        return this.mStatLogger.getTime();
    }

    void logDurationStat(int i, long j) {
        this.mStatLogger.logDurationStat(i, j);
    }

    public java.lang.String injectGetLocaleTagsForUser(int i) {
        return android.os.LocaleList.getDefault().toLanguageTags();
    }

    /* renamed from: com.android.server.pm.ShortcutService$3, reason: invalid class name */
    class AnonymousClass3 implements android.app.role.OnRoleHoldersChangedListener {
        AnonymousClass3() {
        }

        public void onRoleHoldersChanged(java.lang.String str, final android.os.UserHandle userHandle) {
            if ("android.app.role.HOME".equals(str)) {
                com.android.server.pm.ShortcutService.this.injectPostToHandler(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.ShortcutService.AnonymousClass3.this.lambda$onRoleHoldersChanged$0(userHandle);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRoleHoldersChanged$0(android.os.UserHandle userHandle) {
            com.android.server.pm.ShortcutService.this.handleOnDefaultLauncherChanged(userHandle.getIdentifier());
        }
    }

    void handleOnDefaultLauncherChanged(int i) {
        this.mUriGrantsManagerInternal.revokeUriPermissionFromOwner(this.mUriPermissionOwner, null, -1, 0);
        synchronized (this.mLock) {
            try {
                if (isUserLoadedLocked(i)) {
                    getUserShortcutsLocked(i).setCachedLauncher(null);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.pm.ShortcutService$4, reason: invalid class name */
    class AnonymousClass4 extends android.app.UidObserver {
        AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUidStateChanged$0(int i, int i2) {
            com.android.server.pm.ShortcutService.this.handleOnUidStateChanged(i, i2);
        }

        public void onUidStateChanged(final int i, final int i2, long j, int i3) {
            com.android.server.pm.ShortcutService.this.injectPostToHandler(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.ShortcutService.AnonymousClass4.this.lambda$onUidStateChanged$0(i, i2);
                }
            });
        }

        public void onUidGone(final int i, boolean z) {
            com.android.server.pm.ShortcutService.this.injectPostToHandler(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.ShortcutService.AnonymousClass4.this.lambda$onUidGone$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUidGone$1(int i) {
            com.android.server.pm.ShortcutService.this.handleOnUidStateChanged(i, 20);
        }
    }

    void handleOnUidStateChanged(int i, int i2) {
        android.os.Trace.traceBegin(524288L, "shortcutHandleOnUidStateChanged");
        synchronized (this.mLock) {
            try {
                this.mUidState.put(i, i2);
                if (isProcessStateForeground(i2)) {
                    this.mUidLastForegroundElapsedTime.put(i, injectElapsedRealtime());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.os.Trace.traceEnd(524288L);
    }

    private boolean isProcessStateForeground(int i) {
        return i <= 5;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isUidForegroundLocked(int i) {
        if (i == 1000 || isProcessStateForeground(this.mUidState.get(i, 20))) {
            return true;
        }
        return isProcessStateForeground(this.mActivityManagerInternal.getUidProcessState(i));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getUidLastForegroundElapsedTimeLocked(int i) {
        return this.mUidLastForegroundElapsedTime.get(i);
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        final com.android.server.pm.ShortcutService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mService = new com.android.server.pm.ShortcutService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService(com.android.server.pm.ShortcutService.KEY_SHORTCUT, this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            this.mService.onBootPhase(i);
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.handleStopUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mService.handleUnlockUser(targetUser.getUserIdentifier());
        }
    }

    void onBootPhase(int i) {
        switch (i) {
            case com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY /* 480 */:
                initialize();
                break;
            case 1000:
                this.mBootCompleted.set(true);
                break;
        }
    }

    void handleUnlockUser(final int i) {
        synchronized (this.mUnlockedUsers) {
            this.mUnlockedUsers.put(i, true);
        }
        final long statStartTime = getStatStartTime();
        injectRunOnNewThread(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutService.this.lambda$handleUnlockUser$1(statStartTime, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleUnlockUser$1(long j, int i) {
        android.os.Trace.traceBegin(524288L, "shortcutHandleUnlockUser");
        synchronized (this.mLock) {
            logDurationStat(15, j);
            getUserShortcutsLocked(i);
        }
        android.os.Trace.traceEnd(524288L);
    }

    void handleStopUser(int i) {
        android.os.Trace.traceBegin(524288L, "shortcutHandleStopUser");
        synchronized (this.mLock) {
            unloadUserLocked(i);
            synchronized (this.mUnlockedUsers) {
                this.mUnlockedUsers.put(i, false);
            }
        }
        android.os.Trace.traceEnd(524288L);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unloadUserLocked(int i) {
        getUserShortcutsLocked(i).cancelAllInFlightTasks();
        saveDirtyInfo();
        this.mUsers.delete(i);
    }

    final com.android.server.pm.ResilientAtomicFile getBaseStateFile() {
        return new com.android.server.pm.ResilientAtomicFile(new java.io.File(injectSystemDataPath(), FILENAME_BASE_STATE), new java.io.File(injectSystemDataPath(), "shortcut_service.xml.backup"), new java.io.File(injectSystemDataPath(), "shortcut_service.xml.reservecopy"), 505, "base shortcut", null);
    }

    private void initialize() {
        synchronized (this.mLock) {
            loadConfigurationLocked();
            loadBaseStateLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadConfigurationLocked() {
        updateConfigurationLocked(injectShortcutManagerConstants());
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean updateConfigurationLocked(java.lang.String str) {
        boolean z;
        int i;
        android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
            z = true;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Bad shortcut manager settings", e);
            z = false;
        }
        this.mSaveDelayMillis = java.lang.Math.max(0, (int) keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_SAVE_DELAY_MILLIS, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS));
        this.mResetInterval = java.lang.Math.max(1L, keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_RESET_INTERVAL_SEC, DEFAULT_RESET_INTERVAL_SEC) * 1000);
        this.mMaxUpdatesPerInterval = java.lang.Math.max(0, (int) keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_MAX_UPDATES_PER_INTERVAL, 10L));
        this.mMaxShortcuts = java.lang.Math.max(0, (int) keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_MAX_SHORTCUTS, 15L));
        this.mMaxShortcutsPerApp = java.lang.Math.max(0, (int) keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_MAX_SHORTCUTS_PER_APP, CALLBACK_DELAY));
        if (injectIsLowRamDevice()) {
            i = (int) keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_MAX_ICON_DIMENSION_DP_LOWRAM, 48L);
        } else {
            i = (int) keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_MAX_ICON_DIMENSION_DP, 96L);
        }
        this.mMaxIconDimension = injectDipToPixel(java.lang.Math.max(1, i));
        this.mIconPersistFormat = android.graphics.Bitmap.CompressFormat.valueOf(keyValueListParser.getString(com.android.server.pm.ShortcutService.ConfigConstants.KEY_ICON_FORMAT, DEFAULT_ICON_PERSIST_FORMAT));
        this.mIconPersistQuality = (int) keyValueListParser.getLong(com.android.server.pm.ShortcutService.ConfigConstants.KEY_ICON_QUALITY, CALLBACK_DELAY);
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String injectShortcutManagerConstants() {
        return android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "shortcut_manager_constants");
    }

    @com.android.internal.annotations.VisibleForTesting
    int injectDipToPixel(int i) {
        return (int) android.util.TypedValue.applyDimension(1, i, this.mContext.getResources().getDisplayMetrics());
    }

    @android.annotation.Nullable
    static java.lang.String parseStringAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        return typedXmlPullParser.getAttributeValue((java.lang.String) null, str);
    }

    static boolean parseBooleanAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        return parseLongAttribute(typedXmlPullParser, str) == 1;
    }

    static boolean parseBooleanAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, boolean z) {
        return parseLongAttribute(typedXmlPullParser, str, z ? 1L : 0L) == 1;
    }

    static int parseIntAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        return (int) parseLongAttribute(typedXmlPullParser, str);
    }

    static int parseIntAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i) {
        return (int) parseLongAttribute(typedXmlPullParser, str, i);
    }

    static long parseLongAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        return parseLongAttribute(typedXmlPullParser, str, 0L);
    }

    static long parseLongAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, long j) {
        java.lang.String parseStringAttribute = parseStringAttribute(typedXmlPullParser, str);
        if (android.text.TextUtils.isEmpty(parseStringAttribute)) {
            return j;
        }
        try {
            return java.lang.Long.parseLong(parseStringAttribute);
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(TAG, "Error parsing long " + parseStringAttribute);
            return j;
        }
    }

    @android.annotation.Nullable
    static android.content.ComponentName parseComponentNameAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.lang.String parseStringAttribute = parseStringAttribute(typedXmlPullParser, str);
        if (android.text.TextUtils.isEmpty(parseStringAttribute)) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(parseStringAttribute);
    }

    @android.annotation.Nullable
    static android.content.Intent parseIntentAttributeNoDefault(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.lang.String parseStringAttribute = parseStringAttribute(typedXmlPullParser, str);
        if (!android.text.TextUtils.isEmpty(parseStringAttribute)) {
            try {
                return android.content.Intent.parseUri(parseStringAttribute, 0);
            } catch (java.net.URISyntaxException e) {
                android.util.Slog.e(TAG, "Error parsing intent", e);
            }
        }
        return null;
    }

    @android.annotation.Nullable
    static android.content.Intent parseIntentAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        android.content.Intent parseIntentAttributeNoDefault = parseIntentAttributeNoDefault(typedXmlPullParser, str);
        if (parseIntentAttributeNoDefault == null) {
            return new android.content.Intent("android.intent.action.VIEW");
        }
        return parseIntentAttributeNoDefault;
    }

    static void writeTagValue(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        if (android.text.TextUtils.isEmpty(str2)) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_VALUE, str2);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    static void writeTagValue(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, long j) throws java.io.IOException {
        writeTagValue(typedXmlSerializer, str, java.lang.Long.toString(j));
    }

    static void writeTagValue(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, android.content.ComponentName componentName) throws java.io.IOException {
        if (componentName == null) {
            return;
        }
        writeTagValue(typedXmlSerializer, str, componentName.flattenToString());
    }

    static void writeTagExtra(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, android.os.PersistableBundle persistableBundle) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        if (persistableBundle == null) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, str);
        persistableBundle.saveToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    static void writeAttr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.CharSequence charSequence) throws java.io.IOException {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return;
        }
        typedXmlSerializer.attribute((java.lang.String) null, str, charSequence.toString());
    }

    static void writeAttr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, long j) throws java.io.IOException {
        writeAttr(typedXmlSerializer, str, java.lang.String.valueOf(j));
    }

    static void writeAttr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, boolean z) throws java.io.IOException {
        if (z) {
            writeAttr(typedXmlSerializer, str, "1");
        } else {
            writeAttr(typedXmlSerializer, str, "0");
        }
    }

    static void writeAttr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, android.content.ComponentName componentName) throws java.io.IOException {
        if (componentName == null) {
            return;
        }
        writeAttr(typedXmlSerializer, str, componentName.flattenToString());
    }

    static void writeAttr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, android.content.Intent intent) throws java.io.IOException {
        if (intent == null) {
            return;
        }
        writeAttr(typedXmlSerializer, str, intent.toUri(0));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void saveBaseState() {
        java.io.IOException e;
        com.android.server.pm.ResilientAtomicFile baseStateFile = getBaseStateFile();
        try {
            try {
            } catch (java.lang.Throwable th) {
                if (baseStateFile != null) {
                    try {
                        baseStateFile.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException e2) {
            e = e2;
        }
        try {
            try {
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
        } catch (java.io.IOException e3) {
            e = e3;
            android.util.Slog.e(TAG, "Failed to write to file " + baseStateFile.getBaseFile(), e);
            baseStateFile.failWrite(null);
            if (baseStateFile == null) {
            }
        }
        synchronized (this.mLock) {
            try {
                java.io.FileOutputStream startWrite = baseStateFile.startWrite();
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, TAG_ROOT);
                writeTagValue(resolveSerializer, TAG_LAST_RESET_TIME, this.mRawLastResetTime.get());
                resolveSerializer.endTag((java.lang.String) null, TAG_ROOT);
                resolveSerializer.endDocument();
                baseStateFile.finishWrite(startWrite);
                if (baseStateFile == null) {
                    baseStateFile.close();
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void loadBaseStateLocked() {
        char c;
        this.mRawLastResetTime.set(0L);
        com.android.server.pm.ResilientAtomicFile baseStateFile = getBaseStateFile();
        try {
            try {
                try {
                    java.io.FileInputStream openRead = baseStateFile.openRead();
                    if (openRead == null) {
                        throw new java.io.FileNotFoundException(baseStateFile.getBaseFile().getAbsolutePath());
                    }
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next != 1) {
                            if (next == 2) {
                                int depth = resolvePullParser.getDepth();
                                java.lang.String name = resolvePullParser.getName();
                                if (depth == 1) {
                                    if (!TAG_ROOT.equals(name)) {
                                        android.util.Slog.e(TAG, "Invalid root tag: " + name);
                                        baseStateFile.close();
                                        return;
                                    }
                                } else {
                                    switch (name.hashCode()) {
                                        case -68726522:
                                            if (name.equals(TAG_LAST_RESET_TIME)) {
                                                c = 0;
                                                break;
                                            }
                                        default:
                                            c = 65535;
                                            break;
                                    }
                                    switch (c) {
                                        case 0:
                                            this.mRawLastResetTime.set(parseLongAttribute(resolvePullParser, ATTR_VALUE));
                                            break;
                                        default:
                                            android.util.Slog.e(TAG, "Invalid tag: " + name);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                } catch (java.io.FileNotFoundException e) {
                }
                if (baseStateFile != null) {
                    baseStateFile.close();
                }
                getLastResetTimeLocked();
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                baseStateFile.failRead(null, e2);
                loadBaseStateLocked();
                baseStateFile.close();
            }
        } catch (java.lang.Throwable th) {
            if (baseStateFile != null) {
                try {
                    baseStateFile.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.pm.ResilientAtomicFile getUserFile(int i) {
        return new com.android.server.pm.ResilientAtomicFile(new java.io.File(injectUserDataPath(i), FILENAME_USER_PACKAGES), new java.io.File(injectUserDataPath(i), "shortcuts.xml.backup"), new java.io.File(injectUserDataPath(i), FILENAME_USER_PACKAGES_RESERVE_COPY), 505, "user shortcut", null);
    }

    private void saveUser(int i) {
        java.io.FileOutputStream startWrite;
        com.android.server.pm.ResilientAtomicFile userFile = getUserFile(i);
        try {
            try {
                synchronized (this.mLock) {
                    startWrite = userFile.startWrite();
                    saveUserInternalLocked(i, startWrite, false);
                }
                userFile.finishWrite(startWrite);
                cleanupDanglingBitmapDirectoriesLocked(i);
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(TAG, "Failed to write to file " + userFile, e);
                userFile.failWrite(null);
            }
            if (userFile != null) {
                userFile.close();
            }
            getUserShortcutsLocked(i).logSharingShortcutStats(this.mMetricsLogger);
        } catch (java.lang.Throwable th) {
            if (userFile != null) {
                try {
                    userFile.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void saveUserInternalLocked(int i, java.io.OutputStream outputStream, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer;
        if (z) {
            resolveSerializer = android.util.Xml.newFastSerializer();
            resolveSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        } else {
            resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        }
        resolveSerializer.startDocument((java.lang.String) null, true);
        getUserShortcutsLocked(i).saveToXml(resolveSerializer, z);
        resolveSerializer.endDocument();
        outputStream.flush();
    }

    static java.io.IOException throwForInvalidTag(int i, java.lang.String str) throws java.io.IOException {
        throw new java.io.IOException(java.lang.String.format("Invalid tag '%s' found at depth %d", str, java.lang.Integer.valueOf(i)));
    }

    static void warnForInvalidTag(int i, java.lang.String str) throws java.io.IOException {
        android.util.Slog.w(TAG, java.lang.String.format("Invalid tag '%s' found at depth %d", str, java.lang.Integer.valueOf(i)));
    }

    @android.annotation.Nullable
    private com.android.server.pm.ShortcutUser loadUserLocked(int i) {
        java.io.FileInputStream fileInputStream;
        java.lang.Exception e;
        com.android.server.pm.ResilientAtomicFile userFile = getUserFile(i);
        try {
            try {
                fileInputStream = userFile.openRead();
                if (fileInputStream != null) {
                    try {
                        com.android.server.pm.ShortcutUser loadUserInternal = loadUserInternal(i, fileInputStream, false);
                        userFile.close();
                        return loadUserInternal;
                    } catch (java.lang.Exception e2) {
                        e = e2;
                        userFile.failRead(fileInputStream, e);
                        com.android.server.pm.ShortcutUser loadUserLocked = loadUserLocked(i);
                        userFile.close();
                        return loadUserLocked;
                    }
                }
                userFile.close();
                return null;
            } catch (java.lang.Throwable th) {
                if (userFile != null) {
                    try {
                        userFile.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.lang.Exception e3) {
            fileInputStream = null;
            e = e3;
        }
    }

    private com.android.server.pm.ShortcutUser loadUserInternal(int i, java.io.InputStream inputStream, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, com.android.server.pm.ShortcutService.InvalidFileFormatException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        com.android.server.pm.ShortcutUser shortcutUser = null;
        if (z) {
            resolvePullParser = android.util.Xml.newFastPullParser();
            resolvePullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        } else {
            resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        }
        while (true) {
            int next = resolvePullParser.next();
            if (next != 1) {
                if (next == 2) {
                    int depth = resolvePullParser.getDepth();
                    java.lang.String name = resolvePullParser.getName();
                    if (depth == 1 && "user".equals(name)) {
                        shortcutUser = com.android.server.pm.ShortcutUser.loadFromXml(this, resolvePullParser, i, z);
                    } else {
                        throwForInvalidTag(depth, name);
                    }
                }
            } else {
                return shortcutUser;
            }
        }
    }

    private void scheduleSaveBaseState() {
        scheduleSaveInner(com.android.server.am.ProcessList.INVALID_ADJ);
    }

    void scheduleSaveUser(int i) {
        scheduleSaveInner(i);
    }

    private void scheduleSaveInner(int i) {
        synchronized (this.mLock) {
            try {
                if (!this.mDirtyUserIds.contains(java.lang.Integer.valueOf(i))) {
                    this.mDirtyUserIds.add(java.lang.Integer.valueOf(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mHandler.removeCallbacks(this.mSaveDirtyInfoRunner);
        this.mHandler.postDelayed(this.mSaveDirtyInfoRunner, this.mSaveDelayMillis);
    }

    @com.android.internal.annotations.VisibleForTesting
    void saveDirtyInfo() {
        java.util.List<java.lang.Integer> list;
        if (this.mShutdown.get()) {
            return;
        }
        try {
            try {
                android.os.Trace.traceBegin(524288L, "shortcutSaveDirtyInfo");
                java.util.ArrayList arrayList = new java.util.ArrayList();
                synchronized (this.mLock) {
                    list = this.mDirtyUserIds;
                    this.mDirtyUserIds = arrayList;
                }
                for (int size = list.size() - 1; size >= 0; size--) {
                    int intValue = list.get(size).intValue();
                    if (intValue == -10000) {
                        saveBaseState();
                    } else {
                        saveUser(intValue);
                    }
                }
            } catch (java.lang.Exception e) {
                wtf("Exception in saveDirtyInfo", e);
            }
            android.os.Trace.traceEnd(524288L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(524288L);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getLastResetTimeLocked() {
        updateTimesLocked();
        return this.mRawLastResetTime.get();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getNextResetTimeLocked() {
        updateTimesLocked();
        return this.mRawLastResetTime.get() + this.mResetInterval;
    }

    static boolean isClockValid(long j) {
        return j >= 1420070400;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateTimesLocked() {
        long injectCurrentTimeMillis = injectCurrentTimeMillis();
        long j = this.mRawLastResetTime.get();
        if (j != 0) {
            if (injectCurrentTimeMillis < j) {
                if (isClockValid(injectCurrentTimeMillis)) {
                    android.util.Slog.w(TAG, "Clock rewound");
                }
                injectCurrentTimeMillis = j;
            } else {
                if (this.mResetInterval + j <= injectCurrentTimeMillis) {
                    injectCurrentTimeMillis = ((injectCurrentTimeMillis / this.mResetInterval) * this.mResetInterval) + (j % this.mResetInterval);
                }
                injectCurrentTimeMillis = j;
            }
        }
        this.mRawLastResetTime.set(injectCurrentTimeMillis);
        if (j != injectCurrentTimeMillis) {
            scheduleSaveBaseState();
        }
    }

    protected boolean isUserUnlockedL(int i) {
        synchronized (this.mUnlockedUsers) {
            try {
                if (this.mUnlockedUsers.get(i)) {
                    return true;
                }
                return this.mUserManagerInternal.isUserUnlockingOrUnlocked(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void throwIfUserLockedL(int i) {
        if (!isUserUnlockedL(i)) {
            throw new java.lang.IllegalStateException("User " + i + " is locked or not running");
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUserLoadedLocked(int i) {
        return this.mUsers.get(i) != null;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.pm.ShortcutUser getUserShortcutsLocked(int i) {
        if (!isUserUnlockedL(i)) {
            if (i != this.mLastLockedUser) {
                wtf("User still locked");
                this.mLastLockedUser = i;
            }
        } else {
            this.mLastLockedUser = -1;
        }
        com.android.server.pm.ShortcutUser shortcutUser = this.mUsers.get(i);
        if (shortcutUser == null) {
            shortcutUser = loadUserLocked(i);
            if (shortcutUser == null) {
                shortcutUser = new com.android.server.pm.ShortcutUser(this, i);
            }
            this.mUsers.put(i, shortcutUser);
            checkPackageChanges(i);
        }
        return shortcutUser;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mNonPersistentUsersLock"})
    com.android.server.pm.ShortcutNonPersistentUser getNonPersistentUserLocked(int i) {
        com.android.server.pm.ShortcutNonPersistentUser shortcutNonPersistentUser = this.mShortcutNonPersistentUsers.get(i);
        if (shortcutNonPersistentUser == null) {
            com.android.server.pm.ShortcutNonPersistentUser shortcutNonPersistentUser2 = new com.android.server.pm.ShortcutNonPersistentUser(i);
            this.mShortcutNonPersistentUsers.put(i, shortcutNonPersistentUser2);
            return shortcutNonPersistentUser2;
        }
        return shortcutNonPersistentUser;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void forEachLoadedUserLocked(@android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.ShortcutUser> consumer) {
        for (int size = this.mUsers.size() - 1; size >= 0; size--) {
            consumer.accept(this.mUsers.valueAt(size));
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.pm.ShortcutPackage getPackageShortcutsLocked(@android.annotation.NonNull java.lang.String str, int i) {
        return getUserShortcutsLocked(i).getPackageShortcuts(str);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.pm.ShortcutPackage getPackageShortcutsForPublisherLocked(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.pm.ShortcutPackage packageShortcuts = getUserShortcutsLocked(i).getPackageShortcuts(str);
        packageShortcuts.getUser().onCalledByPublisher(str);
        return packageShortcuts;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.pm.ShortcutLauncher getLauncherShortcutsLocked(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        return getUserShortcutsLocked(i).getLauncherShortcuts(str, i2);
    }

    public void cleanupBitmapsForPackage(int i, java.lang.String str) {
        java.io.File file = new java.io.File(getUserBitmapFilePath(i), str);
        if (!file.isDirectory()) {
            return;
        }
        if (!android.os.FileUtils.deleteContents(file) || !file.delete()) {
            android.util.Slog.w(TAG, "Unable to remove directory " + file);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void cleanupDanglingBitmapDirectoriesLocked(int i) {
        long statStartTime = getStatStartTime();
        com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        java.io.File[] listFiles = getUserBitmapFilePath(i).listFiles();
        if (listFiles == null) {
            return;
        }
        for (java.io.File file : listFiles) {
            if (file.isDirectory()) {
                java.lang.String name = file.getName();
                if (!userShortcutsLocked.hasPackage(name)) {
                    cleanupBitmapsForPackage(i, name);
                } else {
                    userShortcutsLocked.getPackageShortcuts(name).cleanupDanglingBitmapFiles(file);
                }
            }
        }
        logDurationStat(5, statStartTime);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class FileOutputStreamWithPath extends java.io.FileOutputStream {
        private final java.io.File mFile;

        public FileOutputStreamWithPath(java.io.File file) throws java.io.FileNotFoundException {
            super(file);
            this.mFile = file;
        }

        public java.io.File getFile() {
            return this.mFile;
        }
    }

    com.android.server.pm.ShortcutService.FileOutputStreamWithPath openIconFileForWrite(int i, android.content.pm.ShortcutInfo shortcutInfo) throws java.io.IOException {
        java.lang.String str;
        java.io.File file = new java.io.File(getUserBitmapFilePath(i), shortcutInfo.getPackage());
        if (!file.isDirectory()) {
            file.mkdirs();
            if (!file.isDirectory()) {
                throw new java.io.IOException("Unable to create directory " + file);
            }
            android.os.SELinux.restorecon(file);
        }
        java.lang.String valueOf = java.lang.String.valueOf(injectCurrentTimeMillis());
        int i2 = 0;
        while (true) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (i2 == 0) {
                str = valueOf;
            } else {
                str = valueOf + "_" + i2;
            }
            sb.append(str);
            sb.append(".png");
            java.io.File file2 = new java.io.File(file, sb.toString());
            if (file2.exists()) {
                i2++;
            } else {
                return new com.android.server.pm.ShortcutService.FileOutputStreamWithPath(file2);
            }
        }
    }

    void saveIconAndFixUpShortcutLocked(com.android.server.pm.ShortcutPackage shortcutPackage, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo.hasIconFile() || shortcutInfo.hasIconResource() || shortcutInfo.hasIconUri()) {
            return;
        }
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            shortcutPackage.removeIcon(shortcutInfo);
            android.graphics.drawable.Icon icon = shortcutInfo.getIcon();
            if (icon == null) {
                return;
            }
            int i = this.mMaxIconDimension;
            try {
                switch (icon.getType()) {
                    case 1:
                        icon.getBitmap();
                        break;
                    case 2:
                        injectValidateIconResPackage(shortcutInfo, icon);
                        shortcutInfo.setIconResourceId(icon.getResId());
                        shortcutInfo.addFlags(4);
                        shortcutInfo.clearIcon();
                        return;
                    case 3:
                    default:
                        throw android.content.pm.ShortcutInfo.getInvalidIconException();
                    case 4:
                        shortcutInfo.setIconUri(icon.getUriString());
                        shortcutInfo.addFlags(32768);
                        shortcutInfo.clearIcon();
                        return;
                    case 5:
                        icon.getBitmap();
                        i = (int) (i * ((android.graphics.drawable.AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f));
                        break;
                    case 6:
                        shortcutInfo.setIconUri(icon.getUriString());
                        shortcutInfo.addFlags(33280);
                        shortcutInfo.clearIcon();
                        return;
                }
                shortcutPackage.saveBitmap(shortcutInfo, i, this.mIconPersistFormat, this.mIconPersistQuality);
                shortcutInfo.clearIcon();
            } catch (java.lang.Throwable th) {
                shortcutInfo.clearIcon();
                throw th;
            }
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    void injectValidateIconResPackage(android.content.pm.ShortcutInfo shortcutInfo, android.graphics.drawable.Icon icon) {
        if (!shortcutInfo.getPackage().equals(icon.getResPackage())) {
            throw new java.lang.IllegalArgumentException("Icon resource must reside in shortcut owner package");
        }
    }

    static android.graphics.Bitmap shrinkBitmap(android.graphics.Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= i && height <= i) {
            return bitmap;
        }
        int max = java.lang.Math.max(width, height);
        int i2 = (width * i) / max;
        int i3 = (height * i) / max;
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i2, i3, android.graphics.Bitmap.Config.ARGB_8888);
        new android.graphics.Canvas(createBitmap).drawBitmap(bitmap, (android.graphics.Rect) null, new android.graphics.RectF(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2, i3), (android.graphics.Paint) null);
        return createBitmap;
    }

    void fixUpShortcutResourceNamesAndValues(android.content.pm.ShortcutInfo shortcutInfo) {
        android.content.res.Resources injectGetResourcesForApplicationAsUser = injectGetResourcesForApplicationAsUser(shortcutInfo.getPackage(), shortcutInfo.getUserId());
        if (injectGetResourcesForApplicationAsUser != null) {
            long statStartTime = getStatStartTime();
            try {
                shortcutInfo.lookupAndFillInResourceNames(injectGetResourcesForApplicationAsUser);
                logDurationStat(10, statStartTime);
                shortcutInfo.resolveResourceStrings(injectGetResourcesForApplicationAsUser);
            } catch (java.lang.Throwable th) {
                logDurationStat(10, statStartTime);
                throw th;
            }
        }
    }

    private boolean isCallerSystem() {
        return android.os.UserHandle.isSameApp(injectBinderCallingUid(), 1000);
    }

    private boolean isCallerShell() {
        int injectBinderCallingUid = injectBinderCallingUid();
        return injectBinderCallingUid == 2000 || injectBinderCallingUid == 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.ComponentName injectChooserActivity() {
        if (this.mChooserActivity == null) {
            this.mChooserActivity = android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(android.R.string.config_bodyFontFamily));
        }
        return this.mChooserActivity;
    }

    private boolean isCallerChooserActivity() {
        int injectBinderCallingUid = injectBinderCallingUid();
        android.content.ComponentName injectChooserActivity = injectChooserActivity();
        return injectChooserActivity != null && android.os.UserHandle.getAppId(injectGetPackageUid(injectChooserActivity.getPackageName(), 0)) == android.os.UserHandle.getAppId(injectBinderCallingUid);
    }

    private void enforceSystemOrShell() {
        if (!isCallerSystem() && !isCallerShell()) {
            throw new java.lang.SecurityException("Caller must be system or shell");
        }
    }

    private void enforceShell() {
        if (!isCallerShell()) {
            throw new java.lang.SecurityException("Caller must be shell");
        }
    }

    private void enforceSystem() {
        if (!isCallerSystem()) {
            throw new java.lang.SecurityException("Caller must be system");
        }
    }

    private void enforceResetThrottlingPermission() {
        if (isCallerSystem()) {
            return;
        }
        enforceCallingOrSelfPermission("android.permission.RESET_SHORTCUT_MANAGER_THROTTLING", null);
    }

    private void enforceCallingOrSelfPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (isCallerSystem()) {
            return;
        }
        injectEnforceCallingPermission(str, str2);
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectEnforceCallingPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        this.mContext.enforceCallingPermission(str, str2);
    }

    private void verifyCallerUserId(int i) {
        if (!isCallerSystem() && android.os.UserHandle.getUserId(injectBinderCallingUid()) != i) {
            throw new java.lang.SecurityException("Invalid user-ID");
        }
    }

    private void verifyCaller(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        if (isCallerSystem()) {
            return;
        }
        int injectBinderCallingUid = injectBinderCallingUid();
        if (android.os.UserHandle.getUserId(injectBinderCallingUid) != i) {
            throw new java.lang.SecurityException("Invalid user-ID");
        }
        if (injectGetPackageUid(str, i) != injectBinderCallingUid) {
            throw new java.lang.SecurityException("Calling package name mismatch");
        }
        com.android.internal.util.Preconditions.checkState(!isEphemeralApp(str, i), "Ephemeral apps can't use ShortcutManager");
    }

    private void verifyShortcutInfoPackage(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo) {
        if (shortcutInfo == null) {
            return;
        }
        if (!java.util.Objects.equals(str, shortcutInfo.getPackage())) {
            android.util.EventLog.writeEvent(1397638484, "109824443", -1, "");
            throw new java.lang.SecurityException("Shortcut package name mismatch");
        }
        if (android.os.UserHandle.getUserId(injectBinderCallingUid()) != shortcutInfo.getUserId()) {
            throw new java.lang.SecurityException("User-ID in shortcut doesn't match the caller");
        }
    }

    private void verifyShortcutInfoPackages(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            verifyShortcutInfoPackage(str, list.get(i));
        }
    }

    void injectPostToHandler(java.lang.Runnable runnable) {
        this.mHandler.post(runnable);
    }

    void injectRunOnNewThread(java.lang.Runnable runnable) {
        new java.lang.Thread(runnable).start();
    }

    void injectPostToHandlerDebounced(@android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull java.lang.Runnable runnable) {
        java.util.Objects.requireNonNull(obj);
        java.util.Objects.requireNonNull(runnable);
        synchronized (this.mLock) {
            this.mHandler.removeCallbacksAndMessages(obj);
            this.mHandler.postDelayed(runnable, obj, CALLBACK_DELAY);
        }
    }

    void enforceMaxActivityShortcuts(int i) {
        if (i > this.mMaxShortcuts) {
            throw new java.lang.IllegalArgumentException("Max number of dynamic shortcuts exceeded");
        }
    }

    int getMaxActivityShortcuts() {
        return this.mMaxShortcuts;
    }

    int getMaxAppShortcuts() {
        return this.mMaxShortcutsPerApp;
    }

    void packageShortcutsChanged(@android.annotation.NonNull com.android.server.pm.ShortcutPackage shortcutPackage, @android.annotation.Nullable java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.Nullable java.util.List<android.content.pm.ShortcutInfo> list2) {
        java.util.Objects.requireNonNull(shortcutPackage);
        java.lang.String packageName = shortcutPackage.getPackageName();
        int packageUserId = shortcutPackage.getPackageUserId();
        injectPostToHandlerDebounced(shortcutPackage, notifyListenerRunnable(packageName, packageUserId));
        notifyShortcutChangeCallbacks(packageName, packageUserId, list, list2);
        shortcutPackage.scheduleSave();
    }

    private void notifyListeners(@android.annotation.NonNull java.lang.String str, int i) {
        injectPostToHandler(notifyListenerRunnable(str, i));
    }

    private java.lang.Runnable notifyListenerRunnable(@android.annotation.NonNull final java.lang.String str, final int i) {
        return new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutService.this.lambda$notifyListenerRunnable$2(i, str);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyListenerRunnable$2(int i, java.lang.String str) {
        try {
            synchronized (this.mLock) {
                try {
                    if (isUserUnlockedL(i)) {
                        java.util.ArrayList arrayList = new java.util.ArrayList(this.mListeners);
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            ((android.content.pm.ShortcutServiceInternal.ShortcutChangeListener) arrayList.get(size)).onShortcutChanged(str, i);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (java.lang.Exception e) {
        }
    }

    private void notifyShortcutChangeCallbacks(@android.annotation.NonNull final java.lang.String str, final int i, @android.annotation.Nullable java.util.List<android.content.pm.ShortcutInfo> list, @android.annotation.Nullable java.util.List<android.content.pm.ShortcutInfo> list2) {
        final java.util.List<android.content.pm.ShortcutInfo> removeNonKeyFields = removeNonKeyFields(list);
        final java.util.List<android.content.pm.ShortcutInfo> removeNonKeyFields2 = removeNonKeyFields(list2);
        final android.os.UserHandle of = android.os.UserHandle.of(i);
        injectPostToHandler(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.ShortcutService.this.lambda$notifyShortcutChangeCallbacks$3(i, removeNonKeyFields, str, of, removeNonKeyFields2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyShortcutChangeCallbacks$3(int i, java.util.List list, java.lang.String str, android.os.UserHandle userHandle, java.util.List list2) {
        try {
            synchronized (this.mLock) {
                try {
                    if (isUserUnlockedL(i)) {
                        java.util.ArrayList arrayList = new java.util.ArrayList(this.mShortcutChangeCallbacks);
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            if (!com.android.internal.util.CollectionUtils.isEmpty(list)) {
                                ((android.content.pm.LauncherApps.ShortcutChangeCallback) arrayList.get(size)).onShortcutsAddedOrUpdated(str, list, userHandle);
                            }
                            if (!com.android.internal.util.CollectionUtils.isEmpty(list2)) {
                                ((android.content.pm.LauncherApps.ShortcutChangeCallback) arrayList.get(size)).onShortcutsRemoved(str, list2, userHandle);
                            }
                        }
                    }
                } finally {
                }
            }
        } catch (java.lang.Exception e) {
        }
    }

    private java.util.List<android.content.pm.ShortcutInfo> removeNonKeyFields(@android.annotation.Nullable java.util.List<android.content.pm.ShortcutInfo> list) {
        if (com.android.internal.util.CollectionUtils.isEmpty(list)) {
            return list;
        }
        int size = list.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            android.content.pm.ShortcutInfo shortcutInfo = list.get(i);
            if (shortcutInfo.hasKeyFieldsOnly()) {
                arrayList.add(shortcutInfo);
            } else {
                arrayList.add(shortcutInfo.clone(4));
            }
        }
        return arrayList;
    }

    private void fixUpIncomingShortcutInfo(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo, boolean z, boolean z2) {
        if (shortcutInfo.isReturnedByServer()) {
            android.util.Log.w(TAG, "Re-publishing ShortcutInfo returned by server is not supported. Some information such as icon may lost from shortcut.");
        }
        java.util.Objects.requireNonNull(shortcutInfo, "Null shortcut detected");
        if (shortcutInfo.getActivity() != null) {
            com.android.internal.util.Preconditions.checkState(shortcutInfo.getPackage().equals(shortcutInfo.getActivity().getPackageName()), "Cannot publish shortcut: activity " + shortcutInfo.getActivity() + " does not belong to package " + shortcutInfo.getPackage());
            com.android.internal.util.Preconditions.checkState(injectIsMainActivity(shortcutInfo.getActivity(), shortcutInfo.getUserId()), "Cannot publish shortcut: activity " + shortcutInfo.getActivity() + " is not main activity");
        }
        if (!z) {
            shortcutInfo.enforceMandatoryFields(z2);
            if (!z2) {
                com.android.internal.util.Preconditions.checkState(shortcutInfo.getActivity() != null, "Cannot publish shortcut: target activity is not set");
            }
        }
        if (shortcutInfo.getIcon() != null) {
            android.content.pm.ShortcutInfo.validateIcon(shortcutInfo.getIcon());
            validateIconURI(shortcutInfo);
        }
        shortcutInfo.replaceFlags(shortcutInfo.getFlags() & 8192);
    }

    private void validateIconURI(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        int injectBinderCallingUid = injectBinderCallingUid();
        android.graphics.drawable.Icon icon = shortcutInfo.getIcon();
        if (icon == null) {
            return;
        }
        int type = icon.getType();
        if (type != 4 && type != 6) {
            return;
        }
        android.net.Uri uri = icon.getUri();
        this.mUriGrantsManagerInternal.checkGrantUriPermission(injectBinderCallingUid, shortcutInfo.getPackage(), android.content.ContentProvider.getUriWithoutUserId(uri), 1, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(injectBinderCallingUid)));
    }

    private void fixUpIncomingShortcutInfo(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo, boolean z) {
        fixUpIncomingShortcutInfo(shortcutInfo, z, false);
    }

    public void validateShortcutForPinRequest(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo) {
        fixUpIncomingShortcutInfo(shortcutInfo, false, true);
    }

    private void fillInDefaultActivity(java.util.List<android.content.pm.ShortcutInfo> list) {
        android.content.ComponentName componentName = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ShortcutInfo shortcutInfo = list.get(size);
            if (shortcutInfo.getActivity() == null) {
                if (componentName == null) {
                    componentName = injectGetDefaultMainActivity(shortcutInfo.getPackage(), shortcutInfo.getUserId());
                    com.android.internal.util.Preconditions.checkState(componentName != null, "Launcher activity not found for package " + shortcutInfo.getPackage());
                }
                shortcutInfo.setActivity(componentName);
            }
        }
    }

    private void assignImplicitRanks(java.util.List<android.content.pm.ShortcutInfo> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).setImplicitRank(size);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.content.pm.ShortcutInfo> setReturnedByServer(java.util.List<android.content.pm.ShortcutInfo> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).setReturnedByServer();
        }
        return list;
    }

    public boolean setDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) {
        verifyCaller(str, i);
        boolean injectHasUnlimitedShortcutsApiCallsPermission = injectHasUnlimitedShortcutsApiCallsPermission(injectBinderCallingPid(), injectBinderCallingUid());
        java.util.List<android.content.pm.ShortcutInfo> list = parceledListSlice.getList();
        verifyShortcutInfoPackages(str, list);
        int size = list.size();
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list, true);
                packageShortcutsForPublisherLocked.ensureNoBitmapIconIfShortcutIsLongLived(list);
                fillInDefaultActivity(list);
                packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(list, 0);
                if (!packageShortcutsForPublisherLocked.tryApiCall(injectHasUnlimitedShortcutsApiCallsPermission)) {
                    return false;
                }
                packageShortcutsForPublisherLocked.clearAllImplicitRanks();
                assignImplicitRanks(list);
                for (int i2 = 0; i2 < size; i2++) {
                    fixUpIncomingShortcutInfo(list.get(i2), false);
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                packageShortcutsForPublisherLocked.findAll(arrayList, new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda14
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$setDynamicShortcuts$4;
                        lambda$setDynamicShortcuts$4 = com.android.server.pm.ShortcutService.lambda$setDynamicShortcuts$4((android.content.pm.ShortcutInfo) obj);
                        return lambda$setDynamicShortcuts$4;
                    }
                }, 4);
                java.util.List<android.content.pm.ShortcutInfo> deleteAllDynamicShortcuts = packageShortcutsForPublisherLocked.deleteAllDynamicShortcuts();
                for (int i3 = 0; i3 < size; i3++) {
                    packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut(list.get(i3));
                }
                packageShortcutsForPublisherLocked.adjustRanks();
                packageShortcutsChanged(packageShortcutsForPublisherLocked, prepareChangedShortcuts(arrayList, list, deleteAllDynamicShortcuts, packageShortcutsForPublisherLocked), deleteAllDynamicShortcuts);
                verifyStates();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setDynamicShortcuts$4(android.content.pm.ShortcutInfo shortcutInfo) {
        return shortcutInfo.isVisibleToPublisher() && shortcutInfo.isDynamic() && (shortcutInfo.isCached() || shortcutInfo.isPinned());
    }

    public boolean updateShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) {
        verifyCaller(str, i);
        boolean injectHasUnlimitedShortcutsApiCallsPermission = injectHasUnlimitedShortcutsApiCallsPermission(injectBinderCallingPid(), injectBinderCallingUid());
        java.util.List<android.content.pm.ShortcutInfo> list = parceledListSlice.getList();
        verifyShortcutInfoPackages(str, list);
        int size = list.size();
        final java.util.ArrayList arrayList = new java.util.ArrayList(1);
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                final com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list, true);
                packageShortcutsForPublisherLocked.ensureNoBitmapIconIfShortcutIsLongLived(list);
                packageShortcutsForPublisherLocked.ensureAllShortcutsVisibleToLauncher(list);
                packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(list, 2);
                boolean tryApiCall = packageShortcutsForPublisherLocked.tryApiCall(injectHasUnlimitedShortcutsApiCallsPermission);
                if (!tryApiCall) {
                    return false;
                }
                packageShortcutsForPublisherLocked.clearAllImplicitRanks();
                assignImplicitRanks(list);
                for (int i2 = 0; i2 < size; i2++) {
                    final android.content.pm.ShortcutInfo shortcutInfo = list.get(i2);
                    fixUpIncomingShortcutInfo(shortcutInfo, true);
                    packageShortcutsForPublisherLocked.mutateShortcut(shortcutInfo.getId(), null, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda17
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.ShortcutService.this.lambda$updateShortcuts$5(shortcutInfo, packageShortcutsForPublisherLocked, arrayList, (android.content.pm.ShortcutInfo) obj);
                        }
                    });
                }
                packageShortcutsForPublisherLocked.adjustRanks();
                if (arrayList.isEmpty()) {
                    arrayList = null;
                }
                packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
                verifyStates();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateShortcuts$5(android.content.pm.ShortcutInfo shortcutInfo, com.android.server.pm.ShortcutPackage shortcutPackage, java.util.List list, android.content.pm.ShortcutInfo shortcutInfo2) {
        if (shortcutInfo2 == null || !shortcutInfo2.isVisibleToPublisher()) {
            return;
        }
        if (shortcutInfo2.isEnabled() != shortcutInfo.isEnabled()) {
            android.util.Slog.w(TAG, "ShortcutInfo.enabled cannot be changed with updateShortcuts()");
        }
        if (shortcutInfo2.isLongLived() != shortcutInfo.isLongLived()) {
            android.util.Slog.w(TAG, "ShortcutInfo.longLived cannot be changed with updateShortcuts()");
        }
        if (shortcutInfo.hasRank()) {
            shortcutInfo2.setRankChanged();
            shortcutInfo2.setImplicitRank(shortcutInfo.getImplicitRank());
        }
        boolean z = shortcutInfo.getIcon() != null;
        if (z) {
            shortcutPackage.removeIcon(shortcutInfo2);
        }
        shortcutInfo2.copyNonNullFieldsFrom(shortcutInfo);
        shortcutInfo2.setTimestamp(injectCurrentTimeMillis());
        if (z) {
            saveIconAndFixUpShortcutLocked(shortcutPackage, shortcutInfo2);
        }
        if (z || shortcutInfo.hasStringResources()) {
            fixUpShortcutResourceNamesAndValues(shortcutInfo2);
        }
        list.add(shortcutInfo2);
    }

    public boolean addDynamicShortcuts(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, int i) {
        verifyCaller(str, i);
        boolean injectHasUnlimitedShortcutsApiCallsPermission = injectHasUnlimitedShortcutsApiCallsPermission(injectBinderCallingPid(), injectBinderCallingUid());
        java.util.List<android.content.pm.ShortcutInfo> list = parceledListSlice.getList();
        verifyShortcutInfoPackages(str, list);
        int size = list.size();
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncluded(list, true);
                packageShortcutsForPublisherLocked.ensureNoBitmapIconIfShortcutIsLongLived(list);
                fillInDefaultActivity(list);
                packageShortcutsForPublisherLocked.enforceShortcutCountsBeforeOperation(list, 1);
                packageShortcutsForPublisherLocked.clearAllImplicitRanks();
                assignImplicitRanks(list);
                if (!packageShortcutsForPublisherLocked.tryApiCall(injectHasUnlimitedShortcutsApiCallsPermission)) {
                    return false;
                }
                java.util.ArrayList arrayList = null;
                for (int i2 = 0; i2 < size; i2++) {
                    android.content.pm.ShortcutInfo shortcutInfo = list.get(i2);
                    fixUpIncomingShortcutInfo(shortcutInfo, false);
                    shortcutInfo.setRankChanged();
                    packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut(shortcutInfo);
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList(1);
                    }
                    arrayList.add(shortcutInfo);
                }
                packageShortcutsForPublisherLocked.adjustRanks();
                packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
                verifyStates();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void pushDynamicShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i) {
        java.util.List<android.content.pm.ShortcutInfo> list;
        verifyCaller(str, i);
        verifyShortcutInfoPackage(str, shortcutInfo);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                packageShortcutsForPublisherLocked.ensureNotImmutable(shortcutInfo.getId(), true);
                fillInDefaultActivity(java.util.Arrays.asList(shortcutInfo));
                if (!shortcutInfo.hasRank()) {
                    shortcutInfo.setRank(0);
                }
                packageShortcutsForPublisherLocked.clearAllImplicitRanks();
                shortcutInfo.setImplicitRank(0);
                fixUpIncomingShortcutInfo(shortcutInfo, false);
                shortcutInfo.setRankChanged();
                if (!packageShortcutsForPublisherLocked.pushDynamicShortcut(shortcutInfo, arrayList)) {
                    list = null;
                } else {
                    if (arrayList.isEmpty()) {
                        return;
                    }
                    list = java.util.Collections.singletonList(arrayList.get(0));
                    arrayList.clear();
                }
                arrayList.add(shortcutInfo);
                packageShortcutsForPublisherLocked.adjustRanks();
                packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, list);
                packageShortcutsForPublisherLocked.reportShortcutUsed(this.mUsageStatsManagerInternal, shortcutInfo.getId());
                verifyStates();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void requestPinShortcut(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, android.content.IntentSender intentSender, int i, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) {
        java.util.Objects.requireNonNull(shortcutInfo);
        com.android.internal.util.Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
        com.android.internal.util.Preconditions.checkArgument(true ^ shortcutInfo.isExcludedFromSurfaces(1), "Shortcut excluded from launcher cannot be pinned");
        androidFuture.complete(java.lang.String.valueOf(requestPinItem(str, i, shortcutInfo, null, null, intentSender)));
    }

    public void createShortcutResultIntent(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo, int i, com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
        android.content.Intent createShortcutResultIntent;
        java.util.Objects.requireNonNull(shortcutInfo);
        com.android.internal.util.Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut must be enabled");
        verifyCaller(str, i);
        verifyShortcutInfoPackage(str, shortcutInfo);
        synchronized (this.mLock) {
            throwIfUserLockedL(i);
            createShortcutResultIntent = this.mShortcutRequestPinProcessor.createShortcutResultIntent(shortcutInfo, i);
        }
        verifyStates();
        androidFuture.complete(createShortcutResultIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestPinItem(java.lang.String str, int i, android.content.pm.ShortcutInfo shortcutInfo, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, android.os.Bundle bundle, android.content.IntentSender intentSender) {
        return requestPinItem(str, i, shortcutInfo, appWidgetProviderInfo, bundle, intentSender, injectBinderCallingPid(), injectBinderCallingUid());
    }

    private boolean requestPinItem(java.lang.String str, int i, android.content.pm.ShortcutInfo shortcutInfo, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, android.os.Bundle bundle, android.content.IntentSender intentSender, int i2, int i3) {
        boolean requestPinItemLocked;
        verifyCaller(str, i);
        if (shortcutInfo == null || !injectHasAccessShortcutsPermission(i2, i3)) {
            verifyShortcutInfoPackage(str, shortcutInfo);
        }
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                com.android.internal.util.Preconditions.checkState(isUidForegroundLocked(i3), "Calling application must have a foreground activity or a foreground service");
                if (shortcutInfo != null) {
                    com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(shortcutInfo.getPackage(), i);
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndInvisibleToPublisher(shortcutInfo.getId())) {
                        packageShortcutsForPublisherLocked.updateInvisibleShortcutForPinRequestWith(shortcutInfo);
                        packageShortcutsChanged(packageShortcutsForPublisherLocked, java.util.Collections.singletonList(shortcutInfo), null);
                    }
                }
                requestPinItemLocked = this.mShortcutRequestPinProcessor.requestPinItemLocked(shortcutInfo, appWidgetProviderInfo, bundle, i, intentSender);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        verifyStates();
        return requestPinItemLocked;
    }

    public void disableShortcuts(java.lang.String str, java.util.List list, java.lang.CharSequence charSequence, int i, int i2) {
        com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked;
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        verifyCaller(str, i2);
        java.util.Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i2);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i2);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
                java.lang.String charSequence2 = charSequence == null ? null : charSequence.toString();
                arrayList = null;
                arrayList2 = null;
                for (int size = list.size() - 1; size >= 0; size--) {
                    java.lang.String str2 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty((java.lang.String) list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
                        android.content.pm.ShortcutInfo disableWithId = packageShortcutsForPublisherLocked.disableWithId(str2, charSequence2, i, false, true, 1);
                        if (disableWithId == null) {
                            if (arrayList == null) {
                                arrayList = new java.util.ArrayList(1);
                            }
                            arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
                        } else {
                            if (arrayList2 == null) {
                                arrayList2 = new java.util.ArrayList(1);
                            }
                            arrayList2.add(disableWithId);
                        }
                    }
                }
                packageShortcutsForPublisherLocked.adjustRanks();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
        verifyStates();
    }

    public void enableShortcuts(java.lang.String str, java.util.List list, int i) {
        com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked;
        java.util.ArrayList arrayList;
        verifyCaller(str, i);
        java.util.Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
                arrayList = null;
                for (int size = list.size() - 1; size >= 0; size--) {
                    java.lang.String str2 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty((java.lang.String) list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
                        packageShortcutsForPublisherLocked.enableWithId(str2);
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList(1);
                        }
                        arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, null);
        verifyStates();
    }

    public void removeDynamicShortcuts(java.lang.String str, java.util.List<java.lang.String> list, int i) {
        com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked;
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        verifyCaller(str, i);
        java.util.Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
                arrayList = null;
                arrayList2 = null;
                for (int size = list.size() - 1; size >= 0; size--) {
                    java.lang.String str2 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
                        android.content.pm.ShortcutInfo deleteDynamicWithId = packageShortcutsForPublisherLocked.deleteDynamicWithId(str2, true, false);
                        if (deleteDynamicWithId == null) {
                            if (arrayList == null) {
                                arrayList = new java.util.ArrayList(1);
                            }
                            arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
                        } else {
                            if (arrayList2 == null) {
                                arrayList2 = new java.util.ArrayList(1);
                            }
                            arrayList2.add(deleteDynamicWithId);
                        }
                    }
                }
                packageShortcutsForPublisherLocked.adjustRanks();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
        verifyStates();
    }

    public void removeAllDynamicShortcuts(java.lang.String str, int i) {
        com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked;
        java.util.List<android.content.pm.ShortcutInfo> deleteAllDynamicShortcuts;
        java.util.List<android.content.pm.ShortcutInfo> prepareChangedShortcuts;
        verifyCaller(str, i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            throwIfUserLockedL(i);
            packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
            packageShortcutsForPublisherLocked.findAll(arrayList, new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda20
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeAllDynamicShortcuts$6;
                    lambda$removeAllDynamicShortcuts$6 = com.android.server.pm.ShortcutService.lambda$removeAllDynamicShortcuts$6((android.content.pm.ShortcutInfo) obj);
                    return lambda$removeAllDynamicShortcuts$6;
                }
            }, 4);
            deleteAllDynamicShortcuts = packageShortcutsForPublisherLocked.deleteAllDynamicShortcuts();
            prepareChangedShortcuts = prepareChangedShortcuts(arrayList, (java.util.List<android.content.pm.ShortcutInfo>) null, deleteAllDynamicShortcuts, packageShortcutsForPublisherLocked);
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, prepareChangedShortcuts, deleteAllDynamicShortcuts);
        verifyStates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeAllDynamicShortcuts$6(android.content.pm.ShortcutInfo shortcutInfo) {
        return shortcutInfo.isVisibleToPublisher() && shortcutInfo.isDynamic() && (shortcutInfo.isCached() || shortcutInfo.isPinned());
    }

    public void removeLongLivedShortcuts(java.lang.String str, java.util.List list, int i) {
        com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked;
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        verifyCaller(str, i);
        java.util.Objects.requireNonNull(list, "shortcutIds must be provided");
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                packageShortcutsForPublisherLocked.ensureImmutableShortcutsNotIncludedWithIds(list, true);
                arrayList = null;
                arrayList2 = null;
                for (int size = list.size() - 1; size >= 0; size--) {
                    java.lang.String str2 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty((java.lang.String) list.get(size));
                    if (packageShortcutsForPublisherLocked.isShortcutExistsAndVisibleToPublisher(str2)) {
                        android.content.pm.ShortcutInfo deleteLongLivedWithId = packageShortcutsForPublisherLocked.deleteLongLivedWithId(str2, true);
                        if (deleteLongLivedWithId != null) {
                            if (arrayList2 == null) {
                                arrayList2 = new java.util.ArrayList(1);
                            }
                            arrayList2.add(deleteLongLivedWithId);
                        } else {
                            if (arrayList == null) {
                                arrayList = new java.util.ArrayList(1);
                            }
                            arrayList.add(packageShortcutsForPublisherLocked.findShortcutById(str2));
                        }
                    }
                }
                packageShortcutsForPublisherLocked.adjustRanks();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        packageShortcutsChanged(packageShortcutsForPublisherLocked, arrayList, arrayList2);
        verifyStates();
    }

    public android.content.pm.ParceledListSlice<android.content.pm.ShortcutInfo> getShortcuts(java.lang.String str, int i, int i2) {
        android.content.pm.ParceledListSlice<android.content.pm.ShortcutInfo> shortcutsWithQueryLocked;
        verifyCaller(str, i2);
        synchronized (this.mLock) {
            throwIfUserLockedL(i2);
            boolean z = true;
            int i3 = (i & 2) != 0 ? 1 : 0;
            boolean z2 = (i & 4) != 0;
            boolean z3 = (i & 1) != 0;
            if ((i & 8) == 0) {
                z = false;
            }
            final int i4 = (z2 ? 2 : 0) | i3 | (z3 ? 32 : 0) | (z ? 1610629120 : 0);
            shortcutsWithQueryLocked = getShortcutsWithQueryLocked(str, i2, 9, new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getShortcuts$7;
                    lambda$getShortcuts$7 = com.android.server.pm.ShortcutService.lambda$getShortcuts$7(i4, (android.content.pm.ShortcutInfo) obj);
                    return lambda$getShortcuts$7;
                }
            });
        }
        return shortcutsWithQueryLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getShortcuts$7(int i, android.content.pm.ShortcutInfo shortcutInfo) {
        return shortcutInfo.isVisibleToPublisher() && (i & shortcutInfo.getFlags()) != 0;
    }

    public android.content.pm.ParceledListSlice getShareTargets(java.lang.String str, final android.content.IntentFilter intentFilter, int i) {
        android.content.pm.ParceledListSlice parceledListSlice;
        com.android.internal.util.Preconditions.checkStringNotEmpty(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        java.util.Objects.requireNonNull(intentFilter, "intentFilter");
        if (!isCallerChooserActivity()) {
            verifyCaller(str, i);
        }
        enforceCallingOrSelfPermission("android.permission.MANAGE_APP_PREDICTIONS", "getShareTargets");
        android.content.ComponentName injectChooserActivity = injectChooserActivity();
        final java.lang.String packageName = injectChooserActivity != null ? injectChooserActivity.getPackageName() : this.mContext.getPackageName();
        synchronized (this.mLock) {
            throwIfUserLockedL(i);
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            getUserShortcutsLocked(i).forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutService.lambda$getShareTargets$8(arrayList, intentFilter, packageName, (com.android.server.pm.ShortcutPackage) obj);
                }
            });
            parceledListSlice = new android.content.pm.ParceledListSlice(arrayList);
        }
        return parceledListSlice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getShareTargets$8(java.util.List list, android.content.IntentFilter intentFilter, java.lang.String str, com.android.server.pm.ShortcutPackage shortcutPackage) {
        list.addAll(shortcutPackage.getMatchingShareTargets(intentFilter, str));
    }

    public boolean hasShareTargets(java.lang.String str, java.lang.String str2, int i) {
        boolean hasShareTargets;
        verifyCaller(str, i);
        enforceCallingOrSelfPermission("android.permission.MANAGE_APP_PREDICTIONS", "hasShareTargets");
        synchronized (this.mLock) {
            throwIfUserLockedL(i);
            hasShareTargets = getPackageShortcutsLocked(str2, i).hasShareTargets();
        }
        return hasShareTargets;
    }

    public boolean isSharingShortcut(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2, @android.annotation.NonNull android.content.IntentFilter intentFilter) {
        verifyCaller(str, i);
        enforceCallingOrSelfPermission("android.permission.MANAGE_APP_PREDICTIONS", "isSharingShortcut");
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i2);
                throwIfUserLockedL(i);
                java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> matchingShareTargets = getPackageShortcutsLocked(str2, i2).getMatchingShareTargets(intentFilter);
                int size = matchingShareTargets.size();
                for (int i3 = 0; i3 < size; i3++) {
                    if (matchingShareTargets.get(i3).getShortcutInfo().getId().equals(str3)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.ParceledListSlice<android.content.pm.ShortcutInfo> getShortcutsWithQueryLocked(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.NonNull java.util.function.Predicate<android.content.pm.ShortcutInfo> predicate) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        getPackageShortcutsForPublisherLocked(str, i).findAll(arrayList, predicate, i2);
        return new android.content.pm.ParceledListSlice<>(setReturnedByServer(arrayList));
    }

    public int getMaxShortcutCountPerActivity(java.lang.String str, int i) throws android.os.RemoteException {
        verifyCaller(str, i);
        return this.mMaxShortcuts;
    }

    public int getRemainingCallCount(java.lang.String str, int i) {
        int apiCallCount;
        verifyCaller(str, i);
        boolean injectHasUnlimitedShortcutsApiCallsPermission = injectHasUnlimitedShortcutsApiCallsPermission(injectBinderCallingPid(), injectBinderCallingUid());
        synchronized (this.mLock) {
            throwIfUserLockedL(i);
            apiCallCount = this.mMaxUpdatesPerInterval - getPackageShortcutsForPublisherLocked(str, i).getApiCallCount(injectHasUnlimitedShortcutsApiCallsPermission);
        }
        return apiCallCount;
    }

    public long getRateLimitResetTime(java.lang.String str, int i) {
        long nextResetTimeLocked;
        verifyCaller(str, i);
        synchronized (this.mLock) {
            throwIfUserLockedL(i);
            nextResetTimeLocked = getNextResetTimeLocked();
        }
        return nextResetTimeLocked;
    }

    public int getIconMaxDimensions(java.lang.String str, int i) {
        int i2;
        verifyCaller(str, i);
        synchronized (this.mLock) {
            i2 = this.mMaxIconDimension;
        }
        return i2;
    }

    public void reportShortcutUsed(java.lang.String str, java.lang.String str2, int i) {
        verifyCaller(str, i);
        java.util.Objects.requireNonNull(str2);
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked = getPackageShortcutsForPublisherLocked(str, i);
                if (packageShortcutsForPublisherLocked.findShortcutById(str2) == null) {
                    android.util.Log.w(TAG, java.lang.String.format("reportShortcutUsed: package %s doesn't have shortcut %s", str, str2));
                } else {
                    packageShortcutsForPublisherLocked.reportShortcutUsed(this.mUsageStatsManagerInternal, str2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isRequestPinItemSupported(int i, int i2) {
        verifyCallerUserId(i);
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            return this.mShortcutRequestPinProcessor.isRequestPinItemSupported(i, i2);
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    public void resetThrottling() {
        enforceSystemOrShell();
        resetThrottlingInner(getCallingUserId());
    }

    void resetThrottlingInner(int i) {
        synchronized (this.mLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    android.util.Log.w(TAG, "User " + i + " is locked or not running");
                    return;
                }
                getUserShortcutsLocked(i).resetThrottling();
                scheduleSaveUser(i);
                android.util.Slog.i(TAG, "ShortcutManager: throttling counter reset for user " + i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void resetAllThrottlingInner() {
        this.mRawLastResetTime.set(injectCurrentTimeMillis());
        scheduleSaveBaseState();
        android.util.Slog.i(TAG, "ShortcutManager: throttling counter reset for all users");
    }

    public void onApplicationActive(java.lang.String str, int i) {
        enforceResetThrottlingPermission();
        synchronized (this.mLock) {
            try {
                if (isUserUnlockedL(i)) {
                    getPackageShortcutsLocked(str, i).resetRateLimitingForCommandLineNoSaving();
                    saveUser(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean hasShortcutHostPermission(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3) {
        if (canSeeAnyPinnedShortcut(str, i, i2, i3)) {
            return true;
        }
        long statStartTime = getStatStartTime();
        try {
            return hasShortcutHostPermissionInner(str, i);
        } finally {
            logDurationStat(4, statStartTime);
        }
    }

    boolean canSeeAnyPinnedShortcut(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3) {
        boolean hasHostPackage;
        if (injectHasAccessShortcutsPermission(i2, i3)) {
            return true;
        }
        synchronized (this.mNonPersistentUsersLock) {
            hasHostPackage = getNonPersistentUserLocked(i).hasHostPackage(str);
        }
        return hasHostPackage;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean injectHasAccessShortcutsPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.ACCESS_SHORTCUTS", i, i2) == 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean injectHasUnlimitedShortcutsApiCallsPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.UNLIMITED_SHORTCUTS_API_CALLS", i, i2) == 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasShortcutHostPermissionInner(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                throwIfUserLockedL(i);
                java.lang.String defaultLauncher = getDefaultLauncher(i);
                if (defaultLauncher == null) {
                    return false;
                }
                return defaultLauncher.equals(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean areShortcutsSupportedOnHomeScreen(int i) {
        boolean z = true;
        if (!android.os.Flags.allowPrivateProfile() || !android.multiuser.Flags.disablePrivateSpaceItemsOnHome()) {
            return true;
        }
        long statStartTime = getStatStartTime();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            synchronized (this.mLock) {
                if (this.mUserManagerInternal.getUserProperties(i).areItemsRestrictedOnHomeScreen()) {
                    z = false;
                }
            }
            return z;
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            logDurationStat(16, statStartTime);
        }
    }

    @android.annotation.Nullable
    java.lang.String getDefaultLauncher(int i) {
        long statStartTime = getStatStartTime();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            synchronized (this.mLock) {
                throwIfUserLockedL(i);
                com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                java.lang.String cachedLauncher = userShortcutsLocked.getCachedLauncher();
                if (cachedLauncher != null) {
                    return cachedLauncher;
                }
                long statStartTime2 = getStatStartTime();
                java.lang.String injectGetHomeRoleHolderAsUser = injectGetHomeRoleHolderAsUser(getParentOrSelfUserId(i));
                logDurationStat(0, statStartTime2);
                if (injectGetHomeRoleHolderAsUser != null) {
                    userShortcutsLocked.setCachedLauncher(injectGetHomeRoleHolderAsUser);
                } else {
                    android.util.Slog.e(TAG, "Default launcher not found. user: " + i);
                }
                return injectGetHomeRoleHolderAsUser;
            }
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            logDurationStat(16, statStartTime);
        }
    }

    public void setShortcutHostPackage(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i) {
        synchronized (this.mNonPersistentUsersLock) {
            getNonPersistentUserLocked(i).setShortcutHostPackage(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanUpPackageForAllLoadedUsers(final java.lang.String str, final int i, final boolean z) {
        synchronized (this.mLock) {
            forEachLoadedUserLocked(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutService.this.lambda$cleanUpPackageForAllLoadedUsers$9(str, i, z, (com.android.server.pm.ShortcutUser) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanUpPackageForAllLoadedUsers$9(java.lang.String str, int i, boolean z, com.android.server.pm.ShortcutUser shortcutUser) {
        cleanUpPackageLocked(str, shortcutUser.getUserId(), i, z);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void cleanUpPackageLocked(final java.lang.String str, int i, final int i2, boolean z) {
        boolean z2;
        boolean isUserLoadedLocked = isUserLoadedLocked(i);
        com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        final com.android.server.pm.ShortcutPackage removePackage = i2 == i ? userShortcutsLocked.removePackage(str) : null;
        if (removePackage == null) {
            z2 = false;
        } else {
            z2 = true;
        }
        userShortcutsLocked.removeLauncher(i2, str);
        userShortcutsLocked.forAllLaunchers(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.pm.ShortcutLauncher) obj).cleanUpPackage(str, i2);
            }
        });
        userShortcutsLocked.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.pm.ShortcutPackage) obj).refreshPinnedFlags();
            }
        });
        if (z2) {
            notifyListeners(str, i);
        }
        if (z && i2 == i) {
            userShortcutsLocked.rescanPackageIfNeeded(str, true);
        }
        if (!z && i2 == i && removePackage != null) {
            injectPostToHandler(new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.ShortcutPackage.this.removeShortcutPackageItem();
                }
            });
        }
        if (!isUserLoadedLocked) {
            unloadUserLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LocalService extends android.content.pm.ShortcutServiceInternal {
        private LocalService() {
        }

        public java.util.List<android.content.pm.ShortcutInfo> getShortcuts(final int i, @android.annotation.NonNull final java.lang.String str, final long j, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable final java.util.List<android.content.LocusId> list2, @android.annotation.Nullable final android.content.ComponentName componentName, final int i2, final int i3, final int i4, final int i5) {
            final int i6;
            final java.util.List<java.lang.String> list3;
            final java.util.ArrayList<android.content.pm.ShortcutInfo> arrayList;
            java.util.ArrayList<android.content.pm.ShortcutInfo> arrayList2 = new java.util.ArrayList<>();
            if ((i2 & 4) != 0) {
                i6 = 4;
            } else if ((i2 & 2048) == 0) {
                i6 = 27;
            } else {
                i6 = 11;
            }
            if (str2 != null) {
                list3 = list;
            } else {
                list3 = null;
            }
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i3);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i3, i).attemptToRestoreIfNeededAndSave();
                    if (str2 != null) {
                        arrayList = arrayList2;
                        getShortcutsInnerLocked(i, str, str2, list3, list2, j, componentName, i2, i3, arrayList2, i6, i4, i5);
                    } else {
                        arrayList = arrayList2;
                        com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i3).forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda9
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.pm.ShortcutService.LocalService.this.lambda$getShortcuts$0(i, str, list3, list2, j, componentName, i2, i3, arrayList, i6, i4, i5, (com.android.server.pm.ShortcutPackage) obj);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return com.android.server.pm.ShortcutService.this.setReturnedByServer(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getShortcuts$0(int i, java.lang.String str, java.util.List list, java.util.List list2, long j, android.content.ComponentName componentName, int i2, int i3, java.util.ArrayList arrayList, int i4, int i5, int i6, com.android.server.pm.ShortcutPackage shortcutPackage) {
            getShortcutsInnerLocked(i, str, shortcutPackage.getPackageName(), list, list2, j, componentName, i2, i3, arrayList, i4, i5, i6);
        }

        @com.android.internal.annotations.GuardedBy({"ShortcutService.this.mLock"})
        private void getShortcutsInnerLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<android.content.LocusId> list2, long j, @android.annotation.Nullable android.content.ComponentName componentName, int i2, int i3, java.util.ArrayList<android.content.pm.ShortcutInfo> arrayList, int i4, int i5, int i6) {
            android.util.ArraySet<java.lang.String> arraySet = list == null ? null : new android.util.ArraySet<>(list);
            com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i3).getPackageShortcutsIfExists(str2);
            if (packageShortcutsIfExists == null) {
                return;
            }
            boolean z = com.android.server.pm.ShortcutService.this.canSeeAnyPinnedShortcut(str, i, i5, i6) && (i2 & 1024) != 0;
            packageShortcutsIfExists.findAll(arrayList, getFilterFromQuery(arraySet, list2, j, componentName, i2 | (z ? 2 : 0), z), i4, str, i, z);
        }

        private java.util.function.Predicate<android.content.pm.ShortcutInfo> getFilterFromQuery(@android.annotation.Nullable final android.util.ArraySet<java.lang.String> arraySet, @android.annotation.Nullable java.util.List<android.content.LocusId> list, final long j, @android.annotation.Nullable final android.content.ComponentName componentName, int i, final boolean z) {
            final android.util.ArraySet arraySet2 = list == null ? null : new android.util.ArraySet(list);
            final boolean z2 = (i & 1) != 0;
            final boolean z3 = (i & 2) != 0;
            final boolean z4 = (i & 8) != 0;
            final boolean z5 = (i & 16) != 0;
            return new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getFilterFromQuery$1;
                    lambda$getFilterFromQuery$1 = com.android.server.pm.ShortcutService.LocalService.lambda$getFilterFromQuery$1(j, arraySet, arraySet2, componentName, z2, z3, z, z4, z5, (android.content.pm.ShortcutInfo) obj);
                    return lambda$getFilterFromQuery$1;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getFilterFromQuery$1(long j, android.util.ArraySet arraySet, android.util.ArraySet arraySet2, android.content.ComponentName componentName, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, android.content.pm.ShortcutInfo shortcutInfo) {
            if (shortcutInfo.getLastChangedTimestamp() < j) {
                return false;
            }
            if (arraySet != null && !arraySet.contains(shortcutInfo.getId())) {
                return false;
            }
            if (arraySet2 != null && !arraySet2.contains(shortcutInfo.getLocusId())) {
                return false;
            }
            if (componentName != null && shortcutInfo.getActivity() != null && !shortcutInfo.getActivity().equals(componentName)) {
                return false;
            }
            if (z && shortcutInfo.isDynamic()) {
                return true;
            }
            if ((z2 || z3) && shortcutInfo.isPinned()) {
                return true;
            }
            if (z4 && shortcutInfo.isDeclaredInManifest()) {
                return true;
            }
            return z5 && shortcutInfo.isCached();
        }

        public void getShortcutsAsync(int i, @android.annotation.NonNull java.lang.String str, long j, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<android.content.LocusId> list2, @android.annotation.Nullable android.content.ComponentName componentName, int i2, int i3, int i4, int i5, @android.annotation.NonNull final com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture) {
            com.android.server.pm.ShortcutPackage packageShortcutsIfExists;
            final int i6;
            final java.util.List<android.content.pm.ShortcutInfo> shortcuts = getShortcuts(i, str, j, str2, list, list2, componentName, i2, i3, i4, i5);
            if (list == null || str2 == null || shortcuts.size() >= list.size()) {
                androidFuture.complete(shortcuts);
                return;
            }
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i3).getPackageShortcutsIfExists(str2);
            }
            if (packageShortcutsIfExists == null) {
                androidFuture.complete(shortcuts);
                return;
            }
            final android.util.ArraySet arraySet = new android.util.ArraySet(list);
            java.util.List list3 = (java.util.List) shortcuts.stream().map(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.content.pm.ShortcutInfo) obj).getId();
                }
            }).collect(java.util.stream.Collectors.toList());
            java.util.Objects.requireNonNull(arraySet);
            list3.forEach(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    arraySet.remove((java.lang.String) obj);
                }
            });
            if ((i2 & 4) != 0) {
                i6 = 4;
            } else if ((i2 & 2048) == 0) {
                i6 = 27;
            } else {
                i6 = 11;
            }
            packageShortcutsIfExists.getShortcutByIdsAsync(arraySet, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutService.LocalService.lambda$getShortcutsAsync$3(i6, shortcuts, androidFuture, (java.util.List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getShortcutsAsync$3(final int i, java.util.List list, com.android.internal.infra.AndroidFuture androidFuture, java.util.List list2) {
            if (list2 != null) {
                java.util.stream.Stream map = list2.stream().map(new java.util.function.Function() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        android.content.pm.ShortcutInfo lambda$getShortcutsAsync$2;
                        lambda$getShortcutsAsync$2 = com.android.server.pm.ShortcutService.LocalService.lambda$getShortcutsAsync$2(i, (android.content.pm.ShortcutInfo) obj);
                        return lambda$getShortcutsAsync$2;
                    }
                });
                java.util.Objects.requireNonNull(list);
                map.forEach(new com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda43(list));
            }
            androidFuture.complete(list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ android.content.pm.ShortcutInfo lambda$getShortcutsAsync$2(int i, android.content.pm.ShortcutInfo shortcutInfo) {
            return shortcutInfo.clone(i);
        }

        public boolean isPinnedByCaller(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2) {
            boolean z;
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            com.android.internal.util.Preconditions.checkStringNotEmpty(str3, "shortcutId");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    android.content.pm.ShortcutInfo shortcutInfoLocked = getShortcutInfoLocked(i, str, str2, str3, i2, false);
                    z = shortcutInfoLocked != null && shortcutInfoLocked.isPinned();
                } finally {
                }
            }
            return z;
        }

        @com.android.internal.annotations.GuardedBy({"ShortcutService.this.mLock"})
        private android.content.pm.ShortcutInfo getShortcutInfoLocked(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull final java.lang.String str3, int i2, boolean z) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            com.android.internal.util.Preconditions.checkStringNotEmpty(str3, "shortcutId");
            com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
            com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
            com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
            if (packageShortcutsIfExists == null) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(1);
            packageShortcutsIfExists.findAll(arrayList, new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getShortcutInfoLocked$4;
                    lambda$getShortcutInfoLocked$4 = com.android.server.pm.ShortcutService.LocalService.lambda$getShortcutInfoLocked$4(str3, (android.content.pm.ShortcutInfo) obj);
                    return lambda$getShortcutInfoLocked$4;
                }
            }, 0, str, i, z);
            if (arrayList.size() == 0) {
                return null;
            }
            return (android.content.pm.ShortcutInfo) arrayList.get(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getShortcutInfoLocked$4(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo) {
            return str.equals(shortcutInfo.getId());
        }

        private void getShortcutInfoAsync(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i2, @android.annotation.NonNull final java.util.function.Consumer<android.content.pm.ShortcutInfo> consumer) {
            com.android.server.pm.ShortcutPackage packageShortcutsIfExists;
            com.android.internal.util.Preconditions.checkStringNotEmpty(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "shortcutId");
            com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
            com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str);
            }
            if (packageShortcutsIfExists == null) {
                consumer.accept(null);
            } else {
                packageShortcutsIfExists.getShortcutByIdsAsync(java.util.Collections.singleton(str2), new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.ShortcutService.LocalService.lambda$getShortcutInfoAsync$5(consumer, (java.util.List) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getShortcutInfoAsync$5(java.util.function.Consumer consumer, java.util.List list) {
            consumer.accept((list == null || list.isEmpty()) ? null : (android.content.pm.ShortcutInfo) list.get(0));
        }

        public void pinShortcuts(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<java.lang.String> list, int i2) {
            com.android.server.pm.ShortcutPackage packageShortcutsIfExists;
            java.util.ArrayList arrayList;
            java.util.List<android.content.pm.ShortcutInfo> prepareChangedShortcuts;
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(list, "shortcutIds");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutLauncher launcherShortcutsLocked = com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i);
                    launcherShortcutsLocked.attemptToRestoreIfNeededAndSave();
                    packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        arrayList = null;
                    } else {
                        arrayList = new java.util.ArrayList();
                        packageShortcutsIfExists.findAll(arrayList, new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(java.lang.Object obj) {
                                boolean lambda$pinShortcuts$6;
                                lambda$pinShortcuts$6 = com.android.server.pm.ShortcutService.LocalService.lambda$pinShortcuts$6((android.content.pm.ShortcutInfo) obj);
                                return lambda$pinShortcuts$6;
                            }
                        }, 4, str, i, false);
                    }
                    android.util.ArraySet<java.lang.String> pinnedShortcutIds = launcherShortcutsLocked.getPinnedShortcutIds(str2, i2);
                    launcherShortcutsLocked.pinShortcuts(i2, str2, list, false);
                    if (pinnedShortcutIds != null && arrayList != null) {
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            pinnedShortcutIds.remove(arrayList.get(i3).getId());
                        }
                    }
                    prepareChangedShortcuts = com.android.server.pm.ShortcutService.this.prepareChangedShortcuts(pinnedShortcutIds, (android.util.ArraySet<java.lang.String>) new android.util.ArraySet(list), arrayList, packageShortcutsIfExists);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (packageShortcutsIfExists != null) {
                com.android.server.pm.ShortcutService.this.packageShortcutsChanged(packageShortcutsIfExists, prepareChangedShortcuts, arrayList);
            }
            com.android.server.pm.ShortcutService.this.verifyStates();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$pinShortcuts$6(android.content.pm.ShortcutInfo shortcutInfo) {
            return (!shortcutInfo.isVisibleToPublisher() || !shortcutInfo.isPinned() || shortcutInfo.isCached() || shortcutInfo.isDynamic() || shortcutInfo.isDeclaredInManifest()) ? false : true;
        }

        public void cacheShortcuts(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<java.lang.String> list, int i2, int i3) {
            updateCachedShortcutsInternal(i, str, str2, list, i2, i3, true);
        }

        public void uncacheShortcuts(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<java.lang.String> list, int i2, int i3) {
            updateCachedShortcutsInternal(i, str, str2, list, i2, i3, false);
        }

        public java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> getShareTargets(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.IntentFilter intentFilter, int i) {
            return com.android.server.pm.ShortcutService.this.getShareTargets(str, intentFilter, i).getList();
        }

        public boolean isSharingShortcut(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2, @android.annotation.NonNull android.content.IntentFilter intentFilter) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str, "callingPackage");
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            com.android.internal.util.Preconditions.checkStringNotEmpty(str3, "shortcutId");
            return com.android.server.pm.ShortcutService.this.isSharingShortcut(i, str, str2, str3, i2, intentFilter);
        }

        private void updateCachedShortcutsInternal(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<java.lang.String> list, int i2, int i3, boolean z) {
            android.content.pm.ShortcutInfo shortcutInfo;
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(list, "shortcutIds");
            com.android.internal.util.Preconditions.checkState((1610629120 & i3) != 0, "invalid cacheFlags");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    int size = list.size();
                    com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    if (size == 0 || packageShortcutsIfExists == null) {
                        return;
                    }
                    java.util.ArrayList arrayList = null;
                    java.util.ArrayList arrayList2 = null;
                    for (int i4 = 0; i4 < size; i4++) {
                        java.lang.String str3 = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(list.get(i4));
                        android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                        if (findShortcutById != null && z != findShortcutById.hasFlags(i3)) {
                            if (z) {
                                if (findShortcutById.isLongLived()) {
                                    findShortcutById.addFlags(i3);
                                    if (arrayList == null) {
                                        arrayList = new java.util.ArrayList(1);
                                    }
                                    arrayList.add(findShortcutById);
                                } else {
                                    android.util.Log.w(com.android.server.pm.ShortcutService.TAG, "Only long lived shortcuts can get cached. Ignoring id " + findShortcutById.getId());
                                }
                            } else {
                                findShortcutById.clearFlags(i3);
                                if (!findShortcutById.isDynamic() && !findShortcutById.isCached()) {
                                    shortcutInfo = packageShortcutsIfExists.deleteLongLivedWithId(str3, true);
                                } else {
                                    shortcutInfo = null;
                                }
                                if (shortcutInfo == null) {
                                    if (arrayList == null) {
                                        arrayList = new java.util.ArrayList(1);
                                    }
                                    arrayList.add(findShortcutById);
                                } else {
                                    if (arrayList2 == null) {
                                        arrayList2 = new java.util.ArrayList(1);
                                    }
                                    arrayList2.add(shortcutInfo);
                                }
                            }
                        }
                    }
                    com.android.server.pm.ShortcutService.this.packageShortcutsChanged(packageShortcutsIfExists, arrayList, arrayList2);
                    com.android.server.pm.ShortcutService.this.verifyStates();
                } finally {
                }
            }
        }

        public android.content.Intent[] createShortcutIntents(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2, int i3, int i4) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "packageName can't be empty");
            com.android.internal.util.Preconditions.checkStringNotEmpty(str3, "shortcutId can't be empty");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    boolean canSeeAnyPinnedShortcut = com.android.server.pm.ShortcutService.this.canSeeAnyPinnedShortcut(str, i, i3, i4);
                    android.content.pm.ShortcutInfo shortcutInfoLocked = getShortcutInfoLocked(i, str, str2, str3, i2, canSeeAnyPinnedShortcut);
                    if (shortcutInfoLocked == null || !shortcutInfoLocked.isEnabled() || (!shortcutInfoLocked.isAlive() && !canSeeAnyPinnedShortcut)) {
                        android.util.Log.e(com.android.server.pm.ShortcutService.TAG, "Shortcut " + str3 + " does not exist or disabled");
                        return null;
                    }
                    return shortcutInfoLocked.getIntents();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void createShortcutIntentsAsync(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2, int i3, int i4, @android.annotation.NonNull final com.android.internal.infra.AndroidFuture<android.content.Intent[]> androidFuture) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "packageName can't be empty");
            com.android.internal.util.Preconditions.checkStringNotEmpty(str3, "shortcutId can't be empty");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    boolean canSeeAnyPinnedShortcut = com.android.server.pm.ShortcutService.this.canSeeAnyPinnedShortcut(str, i, i3, i4);
                    android.content.pm.ShortcutInfo shortcutInfoLocked = getShortcutInfoLocked(i, str, str2, str3, i2, canSeeAnyPinnedShortcut);
                    if (shortcutInfoLocked != null) {
                        if (!shortcutInfoLocked.isEnabled() || (!shortcutInfoLocked.isAlive() && !canSeeAnyPinnedShortcut)) {
                            android.util.Log.e(com.android.server.pm.ShortcutService.TAG, "Shortcut " + str3 + " does not exist or disabled");
                            androidFuture.complete((java.lang.Object) null);
                            return;
                        }
                        androidFuture.complete(shortcutInfoLocked.getIntents());
                        return;
                    }
                    getShortcutInfoAsync(i, str2, str3, i2, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.ShortcutService.LocalService.lambda$createShortcutIntentsAsync$7(androidFuture, (android.content.pm.ShortcutInfo) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$createShortcutIntentsAsync$7(com.android.internal.infra.AndroidFuture androidFuture, android.content.pm.ShortcutInfo shortcutInfo) {
            androidFuture.complete(shortcutInfo == null ? null : shortcutInfo.getIntents());
        }

        public void addListener(@android.annotation.NonNull android.content.pm.ShortcutServiceInternal.ShortcutChangeListener shortcutChangeListener) {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                java.util.ArrayList arrayList = com.android.server.pm.ShortcutService.this.mListeners;
                java.util.Objects.requireNonNull(shortcutChangeListener);
                arrayList.add(shortcutChangeListener);
            }
        }

        public void addShortcutChangeCallback(@android.annotation.NonNull android.content.pm.LauncherApps.ShortcutChangeCallback shortcutChangeCallback) {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                java.util.ArrayList arrayList = com.android.server.pm.ShortcutService.this.mShortcutChangeCallbacks;
                java.util.Objects.requireNonNull(shortcutChangeCallback);
                arrayList.add(shortcutChangeCallback);
            }
        }

        public int getShortcutIconResId(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2) {
            java.util.Objects.requireNonNull(str, "callingPackage");
            java.util.Objects.requireNonNull(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(str3, "shortcutId");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    int i3 = 0;
                    if (packageShortcutsIfExists == null) {
                        return 0;
                    }
                    android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById != null && findShortcutById.hasIconResource()) {
                        i3 = findShortcutById.getIconResourceId();
                    }
                    return i3;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.Nullable
        public java.lang.String getShortcutStartingThemeResName(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2) {
            java.util.Objects.requireNonNull(str, "callingPackage");
            java.util.Objects.requireNonNull(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(str3, "shortcutId");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        return null;
                    }
                    android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    return findShortcutById != null ? findShortcutById.getStartingThemeResName() : null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public android.os.ParcelFileDescriptor getShortcutIconFd(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2) {
            java.util.Objects.requireNonNull(str, "callingPackage");
            java.util.Objects.requireNonNull(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(str3, "shortcutId");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        return null;
                    }
                    android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById == null) {
                        return null;
                    }
                    return getShortcutIconParcelFileDescriptor(packageShortcutsIfExists, findShortcutById);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void getShortcutIconFdAsync(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2, @android.annotation.NonNull final com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture) {
            java.util.Objects.requireNonNull(str, "callingPackage");
            java.util.Objects.requireNonNull(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(str3, "shortcutId");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    final com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        androidFuture.complete((java.lang.Object) null);
                        return;
                    }
                    android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById != null) {
                        androidFuture.complete(getShortcutIconParcelFileDescriptor(packageShortcutsIfExists, findShortcutById));
                    } else {
                        getShortcutInfoAsync(i, str2, str3, i2, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda11
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.pm.ShortcutService.LocalService.this.lambda$getShortcutIconFdAsync$8(androidFuture, packageShortcutsIfExists, (android.content.pm.ShortcutInfo) obj);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getShortcutIconFdAsync$8(com.android.internal.infra.AndroidFuture androidFuture, com.android.server.pm.ShortcutPackage shortcutPackage, android.content.pm.ShortcutInfo shortcutInfo) {
            androidFuture.complete(getShortcutIconParcelFileDescriptor(shortcutPackage, shortcutInfo));
        }

        @android.annotation.Nullable
        private android.os.ParcelFileDescriptor getShortcutIconParcelFileDescriptor(@android.annotation.Nullable com.android.server.pm.ShortcutPackage shortcutPackage, @android.annotation.Nullable android.content.pm.ShortcutInfo shortcutInfo) {
            if (shortcutPackage == null || shortcutInfo == null || !shortcutInfo.hasIconFile()) {
                return null;
            }
            java.lang.String bitmapPathMayWait = shortcutPackage.getBitmapPathMayWait(shortcutInfo);
            if (bitmapPathMayWait == null) {
                android.util.Slog.w(com.android.server.pm.ShortcutService.TAG, "null bitmap detected in getShortcutIconFd()");
                return null;
            }
            try {
                return android.os.ParcelFileDescriptor.open(new java.io.File(bitmapPathMayWait), 268435456);
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.e(com.android.server.pm.ShortcutService.TAG, "Icon file not found: " + bitmapPathMayWait);
                return null;
            }
        }

        public java.lang.String getShortcutIconUri(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2) {
            java.util.Objects.requireNonNull(str, "launcherPackage");
            java.util.Objects.requireNonNull(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(str3, "shortcutId");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        return null;
                    }
                    android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById == null) {
                        return null;
                    }
                    return getShortcutIconUriInternal(i, str, str2, findShortcutById, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void getShortcutIconUriAsync(final int i, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, @android.annotation.NonNull java.lang.String str3, final int i2, @android.annotation.NonNull final com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) {
            java.util.Objects.requireNonNull(str, "launcherPackage");
            java.util.Objects.requireNonNull(str2, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            java.util.Objects.requireNonNull(str3, "shortcutId");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i2);
                    com.android.server.pm.ShortcutService.this.throwIfUserLockedL(i);
                    com.android.server.pm.ShortcutService.this.getLauncherShortcutsLocked(str, i2, i).attemptToRestoreIfNeededAndSave();
                    com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(i2).getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists == null) {
                        androidFuture.complete((java.lang.Object) null);
                        return;
                    }
                    android.content.pm.ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str3);
                    if (findShortcutById != null) {
                        androidFuture.complete(getShortcutIconUriInternal(i, str, str2, findShortcutById, i2));
                    } else {
                        getShortcutInfoAsync(i, str2, str3, i2, new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$LocalService$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.pm.ShortcutService.LocalService.this.lambda$getShortcutIconUriAsync$9(androidFuture, i, str, str2, i2, (android.content.pm.ShortcutInfo) obj);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getShortcutIconUriAsync$9(com.android.internal.infra.AndroidFuture androidFuture, int i, java.lang.String str, java.lang.String str2, int i2, android.content.pm.ShortcutInfo shortcutInfo) {
            androidFuture.complete(shortcutInfo == null ? null : getShortcutIconUriInternal(i, str, str2, shortcutInfo, i2));
        }

        private java.lang.String getShortcutIconUriInternal(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo, int i2) {
            if (!shortcutInfo.hasIconUri()) {
                return null;
            }
            java.lang.String iconUri = shortcutInfo.getIconUri();
            if (iconUri == null) {
                android.util.Slog.w(com.android.server.pm.ShortcutService.TAG, "null uri detected in getShortcutIconUri()");
                return null;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.pm.ShortcutService.this.mUriGrantsManager.grantUriPermissionFromOwner(com.android.server.pm.ShortcutService.this.mUriPermissionOwner, com.android.server.pm.ShortcutService.this.mPackageManagerInternal.getPackageUid(str2, 268435456L, i2), str, android.net.Uri.parse(iconUri), 1, i2, i);
                return iconUri;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.ShortcutService.TAG, "Failed to grant uri access to " + str + " for " + iconUri, e);
                return null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean hasShortcutHostPermission(int i, @android.annotation.NonNull java.lang.String str, int i2, int i3) {
            return com.android.server.pm.ShortcutService.this.hasShortcutHostPermission(str, i, i2, i3);
        }

        public boolean areShortcutsSupportedOnHomeScreen(int i) {
            return com.android.server.pm.ShortcutService.this.areShortcutsSupportedOnHomeScreen(i);
        }

        public void setShortcutHostPackage(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i) {
            com.android.server.pm.ShortcutService.this.setShortcutHostPackage(str, str2, i);
        }

        public boolean requestPinAppWidget(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.Nullable android.content.IntentSender intentSender, int i) {
            java.util.Objects.requireNonNull(appWidgetProviderInfo);
            return com.android.server.pm.ShortcutService.this.requestPinItem(str, i, null, appWidgetProviderInfo, bundle, intentSender);
        }

        public boolean isRequestPinItemSupported(int i, int i2) {
            return com.android.server.pm.ShortcutService.this.isRequestPinItemSupported(i, i2);
        }

        public boolean isForegroundDefaultLauncher(@android.annotation.NonNull java.lang.String str, int i) {
            java.util.Objects.requireNonNull(str);
            java.lang.String defaultLauncher = com.android.server.pm.ShortcutService.this.getDefaultLauncher(android.os.UserHandle.getUserId(i));
            if (defaultLauncher == null || !str.equals(defaultLauncher)) {
                return false;
            }
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    if (!com.android.server.pm.ShortcutService.this.isUidForegroundLocked(i)) {
                        return false;
                    }
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void handleLocaleChanged() {
        scheduleSaveBaseState();
        synchronized (this.mLock) {
            try {
                long injectClearCallingIdentity = injectClearCallingIdentity();
                try {
                    forEachLoadedUserLocked(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda16
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.pm.ShortcutUser) obj).detectLocaleChange();
                        }
                    });
                } finally {
                    injectRestoreCallingIdentity(injectClearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void checkPackageChanges(int i) {
        if (injectIsSafeModeEnabled()) {
            android.util.Slog.i(TAG, "Safe mode, skipping checkPackageChanges()");
            return;
        }
        long statStartTime = getStatStartTime();
        try {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (this.mLock) {
                try {
                    com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                    userShortcutsLocked.forAllPackageItems(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda18
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.ShortcutService.this.lambda$checkPackageChanges$14(arrayList, (com.android.server.pm.ShortcutPackageItem) obj);
                        }
                    });
                    if (arrayList.size() > 0) {
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            android.content.pm.UserPackage userPackage = (android.content.pm.UserPackage) arrayList.get(size);
                            cleanUpPackageLocked(userPackage.packageName, i, userPackage.userId, false);
                        }
                    }
                    rescanUpdatedPackagesLocked(i, userShortcutsLocked.getLastAppScanTime());
                } finally {
                }
            }
            logDurationStat(8, statStartTime);
            verifyStates();
        } catch (java.lang.Throwable th) {
            logDurationStat(8, statStartTime);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkPackageChanges$14(java.util.ArrayList arrayList, com.android.server.pm.ShortcutPackageItem shortcutPackageItem) {
        if (!shortcutPackageItem.getPackageInfo().isShadow() && !isPackageInstalled(shortcutPackageItem.getPackageName(), shortcutPackageItem.getPackageUserId())) {
            arrayList.add(android.content.pm.UserPackage.of(shortcutPackageItem.getPackageUserId(), shortcutPackageItem.getPackageName()));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void rescanUpdatedPackagesLocked(final int i, long j) {
        final com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
        long injectCurrentTimeMillis = injectCurrentTimeMillis();
        forUpdatedPackages(i, j, !injectBuildFingerprint().equals(userShortcutsLocked.getLastAppScanOsFingerprint()), new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.ShortcutService.this.lambda$rescanUpdatedPackagesLocked$15(userShortcutsLocked, i, (android.content.pm.ApplicationInfo) obj);
            }
        });
        userShortcutsLocked.setLastAppScanTime(injectCurrentTimeMillis);
        userShortcutsLocked.setLastAppScanOsFingerprint(injectBuildFingerprint());
        scheduleSaveUser(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rescanUpdatedPackagesLocked$15(com.android.server.pm.ShortcutUser shortcutUser, int i, android.content.pm.ApplicationInfo applicationInfo) {
        shortcutUser.attemptToRestoreIfNeededAndSave(this, applicationInfo.packageName, i);
        shortcutUser.rescanPackageIfNeeded(applicationInfo.packageName, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageAdded(java.lang.String str, int i) {
        synchronized (this.mLock) {
            com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
            userShortcutsLocked.attemptToRestoreIfNeededAndSave(this, str, i);
            userShortcutsLocked.rescanPackageIfNeeded(str, true);
        }
        verifyStates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageUpdateFinished(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                userShortcutsLocked.attemptToRestoreIfNeededAndSave(this, str, i);
                if (isPackageInstalled(str, i)) {
                    userShortcutsLocked.rescanPackageIfNeeded(str, true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        verifyStates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageRemoved(java.lang.String str, int i) {
        cleanUpPackageForAllLoadedUsers(str, i, false);
        verifyStates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageDataCleared(java.lang.String str, int i) {
        cleanUpPackageForAllLoadedUsers(str, i, true);
        verifyStates();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageChanged(java.lang.String str, int i) {
        if (!isPackageInstalled(str, i)) {
            handlePackageRemoved(str, i);
            return;
        }
        synchronized (this.mLock) {
            getUserShortcutsLocked(i).rescanPackageIfNeeded(str, true);
        }
        verifyStates();
    }

    @android.annotation.Nullable
    final android.content.pm.PackageInfo getPackageInfoWithSignatures(java.lang.String str, int i) {
        return getPackageInfo(str, i, true);
    }

    @android.annotation.Nullable
    final android.content.pm.PackageInfo getPackageInfo(java.lang.String str, int i) {
        return getPackageInfo(str, i, false);
    }

    int injectGetPackageUid(@android.annotation.NonNull java.lang.String str, int i) {
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            try {
                return this.mIPackageManager.getPackageUid(str, 795136L, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "RemoteException", e);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                return -1;
            }
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    final android.content.pm.PackageInfo getPackageInfo(java.lang.String str, int i, boolean z) {
        return isInstalledOrNull(injectPackageInfoWithUninstalled(str, i, z));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.content.pm.PackageInfo injectPackageInfoWithUninstalled(java.lang.String str, int i, boolean z) {
        long statStartTime = getStatStartTime();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            try {
                android.content.pm.PackageInfo packageInfo = this.mIPackageManager.getPackageInfo(str, (z ? 134217728 : 0) | PACKAGE_MATCH_FLAGS, i);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                logDurationStat(z ? 2 : 1, statStartTime);
                return packageInfo;
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "RemoteException", e);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                logDurationStat(z ? 2 : 1, statStartTime);
                return null;
            }
        } catch (java.lang.Throwable th) {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            logDurationStat(z ? 2 : 1, statStartTime);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    final android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, int i) {
        return isInstalledOrNull(injectApplicationInfoWithUninstalled(str, i));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.content.pm.ApplicationInfo injectApplicationInfoWithUninstalled(java.lang.String str, int i) {
        long statStartTime = getStatStartTime();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            try {
                return this.mIPackageManager.getApplicationInfo(str, 795136L, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "RemoteException", e);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                logDurationStat(3, statStartTime);
                return null;
            }
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            logDurationStat(3, statStartTime);
        }
    }

    @android.annotation.Nullable
    final android.content.pm.ActivityInfo getActivityInfoWithMetadata(android.content.ComponentName componentName, int i) {
        return isInstalledOrNull(injectGetActivityInfoWithMetadataWithUninstalled(componentName, i));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.content.pm.ActivityInfo injectGetActivityInfoWithMetadataWithUninstalled(android.content.ComponentName componentName, int i) {
        long statStartTime = getStatStartTime();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            try {
                return this.mIPackageManager.getActivityInfo(componentName, 795264L, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "RemoteException", e);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                logDurationStat(6, statStartTime);
                return null;
            }
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            logDurationStat(6, statStartTime);
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    final java.util.List<android.content.pm.PackageInfo> getInstalledPackages(int i) {
        long statStartTime = getStatStartTime();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            try {
                java.util.List<android.content.pm.PackageInfo> injectGetPackagesWithUninstalled = injectGetPackagesWithUninstalled(i);
                injectGetPackagesWithUninstalled.removeIf(PACKAGE_NOT_INSTALLED);
                return injectGetPackagesWithUninstalled;
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "RemoteException", e);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                logDurationStat(7, statStartTime);
                return null;
            }
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            logDurationStat(7, statStartTime);
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.content.pm.PackageInfo> injectGetPackagesWithUninstalled(int i) throws android.os.RemoteException {
        android.content.pm.ParceledListSlice installedPackages = this.mIPackageManager.getInstalledPackages(795136L, i);
        if (installedPackages == null) {
            return java.util.Collections.emptyList();
        }
        return installedPackages.getList();
    }

    private void forUpdatedPackages(int i, long j, boolean z, java.util.function.Consumer<android.content.pm.ApplicationInfo> consumer) {
        java.util.List<android.content.pm.PackageInfo> installedPackages = getInstalledPackages(i);
        for (int size = installedPackages.size() - 1; size >= 0; size--) {
            android.content.pm.PackageInfo packageInfo = installedPackages.get(size);
            if (z || packageInfo.lastUpdateTime >= j) {
                consumer.accept(packageInfo.applicationInfo);
            }
        }
    }

    private boolean isApplicationFlagSet(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.content.pm.ApplicationInfo injectApplicationInfoWithUninstalled = injectApplicationInfoWithUninstalled(str, i);
        return injectApplicationInfoWithUninstalled != null && (injectApplicationInfoWithUninstalled.flags & i2) == i2;
    }

    private boolean isEnabled(@android.annotation.Nullable android.content.pm.ActivityInfo activityInfo, int i) {
        if (activityInfo == null) {
            return false;
        }
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            try {
                int componentEnabledSetting = this.mIPackageManager.getComponentEnabledSetting(activityInfo.getComponentName(), i);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                return (componentEnabledSetting == 0 && activityInfo.enabled) || componentEnabledSetting == 1;
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(TAG, "RemoteException", e);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                return false;
            }
        } catch (java.lang.Throwable th) {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            throw th;
        }
    }

    private static boolean isSystem(@android.annotation.Nullable android.content.pm.ActivityInfo activityInfo) {
        return activityInfo != null && isSystem(activityInfo.applicationInfo);
    }

    private static boolean isSystem(@android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo) {
        return (applicationInfo == null || (applicationInfo.flags & 129) == 0) ? false : true;
    }

    private static boolean isInstalled(@android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo) {
        return (applicationInfo == null || !applicationInfo.enabled || (applicationInfo.flags & 8388608) == 0) ? false : true;
    }

    private static boolean isEphemeralApp(@android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo) {
        return applicationInfo != null && applicationInfo.isInstantApp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isInstalled(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
        return packageInfo != null && isInstalled(packageInfo.applicationInfo);
    }

    private static boolean isInstalled(@android.annotation.Nullable android.content.pm.ActivityInfo activityInfo) {
        return activityInfo != null && isInstalled(activityInfo.applicationInfo);
    }

    private static android.content.pm.ApplicationInfo isInstalledOrNull(android.content.pm.ApplicationInfo applicationInfo) {
        if (isInstalled(applicationInfo)) {
            return applicationInfo;
        }
        return null;
    }

    private static android.content.pm.PackageInfo isInstalledOrNull(android.content.pm.PackageInfo packageInfo) {
        if (isInstalled(packageInfo)) {
            return packageInfo;
        }
        return null;
    }

    private static android.content.pm.ActivityInfo isInstalledOrNull(android.content.pm.ActivityInfo activityInfo) {
        if (isInstalled(activityInfo)) {
            return activityInfo;
        }
        return null;
    }

    boolean isPackageInstalled(java.lang.String str, int i) {
        return getApplicationInfo(str, i) != null;
    }

    boolean isEphemeralApp(java.lang.String str, int i) {
        return isEphemeralApp(getApplicationInfo(str, i));
    }

    @android.annotation.Nullable
    android.content.res.XmlResourceParser injectXmlMetaData(android.content.pm.ActivityInfo activityInfo, java.lang.String str) {
        return activityInfo.loadXmlMetaData(this.mContext.getPackageManager(), str);
    }

    @android.annotation.Nullable
    android.content.res.Resources injectGetResourcesForApplicationAsUser(java.lang.String str, int i) {
        long statStartTime = getStatStartTime();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            try {
                return this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager().getResourcesForApplication(str);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, "Resources of package " + str + " for user " + i + " not found");
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                logDurationStat(9, statStartTime);
                return null;
            }
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            logDurationStat(9, statStartTime);
        }
    }

    private android.content.Intent getMainActivityIntent() {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory(LAUNCHER_INTENT_CATEGORY);
        return intent;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.ComponentName componentName, int i) {
        java.util.Objects.requireNonNull(str);
        intent.setPackage(str);
        if (componentName != null) {
            intent.setComponent(componentName);
        }
        return queryActivities(intent, i, true);
    }

    @android.annotation.NonNull
    java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull android.content.Intent intent, final int i, boolean z) {
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser = this.mContext.getPackageManager().queryIntentActivitiesAsUser(intent, PACKAGE_MATCH_FLAGS, i);
            if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.size() == 0) {
                return EMPTY_RESOLVE_INFO;
            }
            queryIntentActivitiesAsUser.removeIf(ACTIVITY_NOT_INSTALLED);
            queryIntentActivitiesAsUser.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda29
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$queryActivities$16;
                    lambda$queryActivities$16 = com.android.server.pm.ShortcutService.this.lambda$queryActivities$16(i, (android.content.pm.ResolveInfo) obj);
                    return lambda$queryActivities$16;
                }
            });
            if (z) {
                queryIntentActivitiesAsUser.removeIf(ACTIVITY_NOT_EXPORTED);
            }
            return queryIntentActivitiesAsUser;
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$queryActivities$16(int i, android.content.pm.ResolveInfo resolveInfo) {
        android.content.pm.ActivityInfo activityInfo = resolveInfo.activityInfo;
        return (isSystem(activityInfo) || isEnabled(activityInfo, i)) ? false : true;
    }

    @android.annotation.Nullable
    android.content.ComponentName injectGetDefaultMainActivity(@android.annotation.NonNull java.lang.String str, int i) {
        long statStartTime = getStatStartTime();
        try {
            java.util.List<android.content.pm.ResolveInfo> queryActivities = queryActivities(getMainActivityIntent(), str, null, i);
            return queryActivities.size() != 0 ? queryActivities.get(0).activityInfo.getComponentName() : null;
        } finally {
            logDurationStat(11, statStartTime);
        }
    }

    boolean injectIsMainActivity(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        long statStartTime = getStatStartTime();
        try {
            if (componentName == null) {
                wtf("null activity detected");
                return false;
            }
            if (DUMMY_MAIN_ACTIVITY.equals(componentName.getClassName())) {
                return true;
            }
            return queryActivities(getMainActivityIntent(), componentName.getPackageName(), componentName, i).size() > 0;
        } finally {
            logDurationStat(12, statStartTime);
        }
    }

    @android.annotation.NonNull
    android.content.ComponentName getDummyMainActivity(@android.annotation.NonNull java.lang.String str) {
        return new android.content.ComponentName(str, DUMMY_MAIN_ACTIVITY);
    }

    boolean isDummyMainActivity(@android.annotation.Nullable android.content.ComponentName componentName) {
        return componentName != null && DUMMY_MAIN_ACTIVITY.equals(componentName.getClassName());
    }

    @android.annotation.NonNull
    java.util.List<android.content.pm.ResolveInfo> injectGetMainActivities(@android.annotation.NonNull java.lang.String str, int i) {
        long statStartTime = getStatStartTime();
        try {
            return queryActivities(getMainActivityIntent(), str, null, i);
        } finally {
            logDurationStat(12, statStartTime);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean injectIsActivityEnabledAndExported(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        long statStartTime = getStatStartTime();
        try {
            return queryActivities(new android.content.Intent(), componentName.getPackageName(), componentName, i).size() > 0;
        } finally {
            logDurationStat(13, statStartTime);
        }
    }

    @android.annotation.Nullable
    android.content.ComponentName injectGetPinConfirmationActivity(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        java.lang.String str2;
        java.util.Objects.requireNonNull(str);
        if (i2 == 1) {
            str2 = "android.content.pm.action.CONFIRM_PIN_SHORTCUT";
        } else {
            str2 = "android.content.pm.action.CONFIRM_PIN_APPWIDGET";
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it = queryActivities(new android.content.Intent(str2).setPackage(str), i, false).iterator();
        if (it.hasNext()) {
            return it.next().activityInfo.getComponentName();
        }
        return null;
    }

    boolean injectIsSafeModeEnabled() {
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            boolean isSafeModeEnabled = android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService("window")).isSafeModeEnabled();
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            return isSafeModeEnabled;
        } catch (android.os.RemoteException e) {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            throw th;
        }
    }

    int getParentOrSelfUserId(int i) {
        return this.mUserManagerInternal.getProfileParentId(i);
    }

    void injectSendIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) {
        if (intentSender == null) {
            return;
        }
        try {
            intentSender.sendIntent(this.mContext, 0, intent, null, null, null, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(2).toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
            android.util.Slog.w(TAG, "sendIntent failed().", e);
        }
    }

    boolean shouldBackupApp(java.lang.String str, int i) {
        return isApplicationFlagSet(str, i, 32768);
    }

    static boolean shouldBackupApp(android.content.pm.PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & 32768) != 0;
    }

    public byte[] getBackupPayload(int i) {
        enforceSystem();
        synchronized (this.mLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    wtf("Can't backup: user " + i + " is locked or not running");
                    return null;
                }
                com.android.server.pm.ShortcutUser userShortcutsLocked = getUserShortcutsLocked(i);
                if (userShortcutsLocked == null) {
                    wtf("Can't backup: user not found: id=" + i);
                    return null;
                }
                userShortcutsLocked.forAllPackageItems(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda26
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.pm.ShortcutPackageItem) obj).refreshPackageSignatureAndSave();
                    }
                });
                userShortcutsLocked.forAllPackages(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda27
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.pm.ShortcutPackage) obj).rescanPackageIfNeeded(false, true);
                    }
                });
                userShortcutsLocked.forAllLaunchers(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda28
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.pm.ShortcutLauncher) obj).ensurePackageInfo();
                    }
                });
                scheduleSaveUser(i);
                saveDirtyInfo();
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(32768);
                try {
                    saveUserInternalLocked(i, byteArrayOutputStream, true);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    this.mShortcutDumpFiles.save("backup-1-payload.txt", byteArray);
                    return byteArray;
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.w(TAG, "Backup failed.", e);
                    return null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void applyRestore(byte[] bArr, int i) {
        enforceSystem();
        synchronized (this.mLock) {
            try {
                if (!isUserUnlockedL(i)) {
                    wtf("Can't restore: user " + i + " is locked or not running");
                    return;
                }
                this.mShortcutDumpFiles.save("restore-0-start.txt", new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.ShortcutService.this.lambda$applyRestore$20((java.io.PrintWriter) obj);
                    }
                });
                this.mShortcutDumpFiles.save("restore-1-payload.xml", bArr);
                try {
                    com.android.server.pm.ShortcutUser loadUserInternal = loadUserInternal(i, new java.io.ByteArrayInputStream(bArr), true);
                    this.mShortcutDumpFiles.save("restore-2.txt", new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.ShortcutService.this.dumpInner((java.io.PrintWriter) obj);
                        }
                    });
                    getUserShortcutsLocked(i).mergeRestoredFile(loadUserInternal);
                    this.mShortcutDumpFiles.save("restore-3.txt", new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.ShortcutService.this.dumpInner((java.io.PrintWriter) obj);
                        }
                    });
                    rescanUpdatedPackagesLocked(i, 0L);
                    this.mShortcutDumpFiles.save("restore-4.txt", new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.ShortcutService.this.dumpInner((java.io.PrintWriter) obj);
                        }
                    });
                    this.mShortcutDumpFiles.save("restore-5-finish.txt", new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.ShortcutService.this.lambda$applyRestore$21((java.io.PrintWriter) obj);
                        }
                    });
                    saveUser(i);
                } catch (com.android.server.pm.ShortcutService.InvalidFileFormatException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Slog.w(TAG, "Restoration failed.", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyRestore$20(java.io.PrintWriter printWriter) {
        printWriter.print("Start time: ");
        dumpCurrentTime(printWriter);
        printWriter.println();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyRestore$21(java.io.PrintWriter printWriter) {
        printWriter.print("Finish time: ");
        dumpCurrentTime(printWriter);
        printWriter.println();
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            dumpNoCheck(fileDescriptor, printWriter, strArr);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void dumpNoCheck(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.server.pm.ShortcutService.DumpFilter parseDumpArgs = parseDumpArgs(strArr);
        if (parseDumpArgs.shouldDumpCheckIn()) {
            dumpCheckin(printWriter, parseDumpArgs.shouldCheckInClear());
            return;
        }
        if (parseDumpArgs.shouldDumpMain()) {
            dumpInner(printWriter, parseDumpArgs);
            printWriter.println();
        }
        if (parseDumpArgs.shouldDumpUid()) {
            dumpUid(printWriter);
            printWriter.println();
        }
        if (parseDumpArgs.shouldDumpFiles()) {
            dumpDumpFiles(printWriter);
            printWriter.println();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0100, code lost:
    
        if (r2 >= r6.length) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0102, code lost:
    
        r0.addPackage(r6[r2]);
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x010b, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static com.android.server.pm.ShortcutService.DumpFilter parseDumpArgs(java.lang.String[] strArr) {
        com.android.server.pm.ShortcutService.DumpFilter dumpFilter = new com.android.server.pm.ShortcutService.DumpFilter();
        if (strArr == null) {
            return dumpFilter;
        }
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                break;
            }
            int i2 = i + 1;
            java.lang.String str = strArr[i];
            if ("-c".equals(str)) {
                dumpFilter.setDumpCheckIn(true);
            } else if ("--checkin".equals(str)) {
                dumpFilter.setDumpCheckIn(true);
                dumpFilter.setCheckInClear(true);
            } else if ("-a".equals(str) || "--all".equals(str)) {
                dumpFilter.setDumpUid(true);
                dumpFilter.setDumpFiles(true);
            } else if ("-u".equals(str) || "--uid".equals(str)) {
                dumpFilter.setDumpUid(true);
            } else if ("-f".equals(str) || "--files".equals(str)) {
                dumpFilter.setDumpFiles(true);
            } else if ("-n".equals(str) || "--no-main".equals(str)) {
                dumpFilter.setDumpMain(false);
            } else if ("--user".equals(str)) {
                if (i2 >= strArr.length) {
                    throw new java.lang.IllegalArgumentException("Missing user ID for --user");
                }
                i = i2 + 1;
                try {
                    dumpFilter.addUser(java.lang.Integer.parseInt(strArr[i2]));
                } catch (java.lang.NumberFormatException e) {
                    throw new java.lang.IllegalArgumentException("Invalid user ID", e);
                }
            } else if ("-p".equals(str) || "--package".equals(str)) {
                if (i2 >= strArr.length) {
                    throw new java.lang.IllegalArgumentException("Missing package name for --package");
                }
                i = i2 + 1;
                dumpFilter.addPackageRegex(strArr[i2]);
                dumpFilter.setDumpDetails(false);
            } else {
                if (str.startsWith("-")) {
                    throw new java.lang.IllegalArgumentException("Unknown option " + str);
                }
                i = i2;
            }
            i = i2;
        }
    }

    static class DumpFilter {
        private boolean mDumpCheckIn = false;
        private boolean mCheckInClear = false;
        private boolean mDumpMain = true;
        private boolean mDumpUid = false;
        private boolean mDumpFiles = false;
        private boolean mDumpDetails = true;
        private final java.util.List<java.util.regex.Pattern> mPackagePatterns = new java.util.ArrayList();
        private final java.util.List<java.lang.Integer> mUsers = new java.util.ArrayList();

        DumpFilter() {
        }

        void addPackageRegex(java.lang.String str) {
            this.mPackagePatterns.add(java.util.regex.Pattern.compile(str));
        }

        public void addPackage(java.lang.String str) {
            addPackageRegex(java.util.regex.Pattern.quote(str));
        }

        void addUser(int i) {
            this.mUsers.add(java.lang.Integer.valueOf(i));
        }

        boolean isPackageMatch(java.lang.String str) {
            if (this.mPackagePatterns.size() == 0) {
                return true;
            }
            for (int i = 0; i < this.mPackagePatterns.size(); i++) {
                if (this.mPackagePatterns.get(i).matcher(str).find()) {
                    return true;
                }
            }
            return false;
        }

        boolean isUserMatch(int i) {
            if (this.mUsers.size() == 0) {
                return true;
            }
            for (int i2 = 0; i2 < this.mUsers.size(); i2++) {
                if (this.mUsers.get(i2).intValue() == i) {
                    return true;
                }
            }
            return false;
        }

        public boolean shouldDumpCheckIn() {
            return this.mDumpCheckIn;
        }

        public void setDumpCheckIn(boolean z) {
            this.mDumpCheckIn = z;
        }

        public boolean shouldCheckInClear() {
            return this.mCheckInClear;
        }

        public void setCheckInClear(boolean z) {
            this.mCheckInClear = z;
        }

        public boolean shouldDumpMain() {
            return this.mDumpMain;
        }

        public void setDumpMain(boolean z) {
            this.mDumpMain = z;
        }

        public boolean shouldDumpUid() {
            return this.mDumpUid;
        }

        public void setDumpUid(boolean z) {
            this.mDumpUid = z;
        }

        public boolean shouldDumpFiles() {
            return this.mDumpFiles;
        }

        public void setDumpFiles(boolean z) {
            this.mDumpFiles = z;
        }

        public boolean shouldDumpDetails() {
            return this.mDumpDetails;
        }

        public void setDumpDetails(boolean z) {
            this.mDumpDetails = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInner(java.io.PrintWriter printWriter) {
        dumpInner(printWriter, new com.android.server.pm.ShortcutService.DumpFilter());
    }

    private void dumpInner(java.io.PrintWriter printWriter, com.android.server.pm.ShortcutService.DumpFilter dumpFilter) {
        synchronized (this.mLock) {
            if (dumpFilter.shouldDumpDetails()) {
                long injectCurrentTimeMillis = injectCurrentTimeMillis();
                printWriter.print("Now: [");
                printWriter.print(injectCurrentTimeMillis);
                printWriter.print("] ");
                printWriter.print(formatTime(injectCurrentTimeMillis));
                printWriter.print("  Raw last reset: [");
                printWriter.print(this.mRawLastResetTime.get());
                printWriter.print("] ");
                printWriter.print(formatTime(this.mRawLastResetTime.get()));
                long lastResetTimeLocked = getLastResetTimeLocked();
                printWriter.print("  Last reset: [");
                printWriter.print(lastResetTimeLocked);
                printWriter.print("] ");
                printWriter.print(formatTime(lastResetTimeLocked));
                long nextResetTimeLocked = getNextResetTimeLocked();
                printWriter.print("  Next reset: [");
                printWriter.print(nextResetTimeLocked);
                printWriter.print("] ");
                printWriter.print(formatTime(nextResetTimeLocked));
                printWriter.println();
                printWriter.println();
                printWriter.print("  Config:");
                printWriter.print("    Max icon dim: ");
                printWriter.println(this.mMaxIconDimension);
                printWriter.print("    Icon format: ");
                printWriter.println(this.mIconPersistFormat);
                printWriter.print("    Icon quality: ");
                printWriter.println(this.mIconPersistQuality);
                printWriter.print("    saveDelayMillis: ");
                printWriter.println(this.mSaveDelayMillis);
                printWriter.print("    resetInterval: ");
                printWriter.println(this.mResetInterval);
                printWriter.print("    maxUpdatesPerInterval: ");
                printWriter.println(this.mMaxUpdatesPerInterval);
                printWriter.print("    maxShortcutsPerActivity: ");
                printWriter.println(this.mMaxShortcuts);
                printWriter.println();
                this.mStatLogger.dump(printWriter, "  ");
                synchronized (this.mWtfLock) {
                    try {
                        printWriter.println();
                        printWriter.print("  #Failures: ");
                        printWriter.println(this.mWtfCount);
                        if (this.mLastWtfStacktrace != null) {
                            printWriter.print("  Last failure stack trace: ");
                            printWriter.println(android.util.Log.getStackTraceString(this.mLastWtfStacktrace));
                        }
                    } finally {
                    }
                }
                printWriter.println();
            }
            for (int i = 0; i < this.mUsers.size(); i++) {
                com.android.server.pm.ShortcutUser valueAt = this.mUsers.valueAt(i);
                if (dumpFilter.isUserMatch(valueAt.getUserId())) {
                    valueAt.dump(printWriter, "  ", dumpFilter);
                    printWriter.println();
                }
            }
            for (int i2 = 0; i2 < this.mShortcutNonPersistentUsers.size(); i2++) {
                com.android.server.pm.ShortcutNonPersistentUser valueAt2 = this.mShortcutNonPersistentUsers.valueAt(i2);
                if (dumpFilter.isUserMatch(valueAt2.getUserId())) {
                    valueAt2.dump(printWriter, "  ", dumpFilter);
                    printWriter.println();
                }
            }
        }
    }

    private void dumpUid(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("** SHORTCUT MANAGER UID STATES (dumpsys shortcut -n -u)");
                for (int i = 0; i < this.mUidState.size(); i++) {
                    int keyAt = this.mUidState.keyAt(i);
                    int valueAt = this.mUidState.valueAt(i);
                    printWriter.print("    UID=");
                    printWriter.print(keyAt);
                    printWriter.print(" state=");
                    printWriter.print(valueAt);
                    if (isProcessStateForeground(valueAt)) {
                        printWriter.print("  [FG]");
                    }
                    printWriter.print("  last FG=");
                    printWriter.print(this.mUidLastForegroundElapsedTime.get(keyAt));
                    printWriter.println();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static java.lang.String formatTime(long j) {
        return android.text.format.TimeMigrationUtils.formatMillisWithFixedFormat(j);
    }

    private void dumpCurrentTime(java.io.PrintWriter printWriter) {
        printWriter.print(formatTime(injectCurrentTimeMillis()));
    }

    private void dumpCheckin(java.io.PrintWriter printWriter, boolean z) {
        synchronized (this.mLock) {
            try {
                try {
                    org.json.JSONArray jSONArray = new org.json.JSONArray();
                    for (int i = 0; i < this.mUsers.size(); i++) {
                        jSONArray.put(this.mUsers.valueAt(i).dumpCheckin(z));
                    }
                    org.json.JSONObject jSONObject = new org.json.JSONObject();
                    jSONObject.put(KEY_SHORTCUT, jSONArray);
                    jSONObject.put(KEY_LOW_RAM, injectIsLowRamDevice());
                    jSONObject.put(KEY_ICON_SIZE, this.mMaxIconDimension);
                    printWriter.println(jSONObject.toString(1));
                } catch (org.json.JSONException e) {
                    android.util.Slog.e(TAG, "Unable to write in json", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dumpDumpFiles(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("** SHORTCUT MANAGER FILES (dumpsys shortcut -n -f)");
            this.mShortcutDumpFiles.dumpAll(printWriter);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        enforceShell();
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            resultReceiver.send(new com.android.server.pm.ShortcutService.MyShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver), null);
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    static class CommandException extends java.lang.Exception {
        public CommandException(java.lang.String str) {
            super(str);
        }
    }

    private class MyShellCommand extends android.os.ShellCommand {
        private int mShortcutMatchFlags;
        private int mUserId;

        private MyShellCommand() {
            this.mUserId = 0;
            this.mShortcutMatchFlags = 15;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void parseOptionsLocked(boolean z) throws com.android.server.pm.ShortcutService.CommandException {
            char c;
            while (true) {
                java.lang.String nextOption = getNextOption();
                if (nextOption != null) {
                    switch (nextOption.hashCode()) {
                        case -1626182425:
                            if (nextOption.equals("--flags")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1333469547:
                            if (nextOption.equals("--user")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            if (z) {
                                this.mUserId = android.os.UserHandle.parseUserArg(getNextArgRequired());
                                if (!com.android.server.pm.ShortcutService.this.isUserUnlockedL(this.mUserId)) {
                                    throw new com.android.server.pm.ShortcutService.CommandException("User " + this.mUserId + " is not running or locked");
                                }
                            } else {
                                this.mShortcutMatchFlags = java.lang.Integer.parseInt(getNextArgRequired());
                            }
                        case 1:
                            this.mShortcutMatchFlags = java.lang.Integer.parseInt(getNextArgRequired());
                        default:
                            throw new com.android.server.pm.ShortcutService.CommandException("Unknown option: " + nextOption);
                    }
                } else {
                    return;
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(java.lang.String str) {
            char c;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                switch (str.hashCode()) {
                    case -1610733672:
                        if (str.equals("has-shortcut-access")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1117067818:
                        if (str.equals("verify-states")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -749565587:
                        if (str.equals("clear-shortcuts")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -276993226:
                        if (str.equals("get-shortcuts")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -139706031:
                        if (str.equals("reset-all-throttling")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -76794781:
                        if (str.equals("override-config")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 188791973:
                        if (str.equals("reset-throttling")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1190495043:
                        if (str.equals("get-default-launcher")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1411888601:
                        if (str.equals("unload-user")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1964247424:
                        if (str.equals("reset-config")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        handleResetThrottling();
                        break;
                    case 1:
                        handleResetAllThrottling();
                        break;
                    case 2:
                        handleOverrideConfig();
                        break;
                    case 3:
                        handleResetConfig();
                        break;
                    case 4:
                        handleGetDefaultLauncher();
                        break;
                    case 5:
                        handleUnloadUser();
                        break;
                    case 6:
                        handleClearShortcuts();
                        break;
                    case 7:
                        handleGetShortcuts();
                        break;
                    case '\b':
                        handleVerifyStates();
                        break;
                    case '\t':
                        handleHasShortcutAccess();
                        break;
                    default:
                        return handleDefaultCommands(str);
                }
                outPrintWriter.println("Success");
                return 0;
            } catch (com.android.server.pm.ShortcutService.CommandException e) {
                outPrintWriter.println("Error: " + e.getMessage());
                return 1;
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Usage: cmd shortcut COMMAND [options ...]");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut reset-throttling [--user USER_ID]");
            outPrintWriter.println("    Reset throttling for all packages and users");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut reset-all-throttling");
            outPrintWriter.println("    Reset the throttling state for all users");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut override-config CONFIG");
            outPrintWriter.println("    Override the configuration for testing (will last until reboot)");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut reset-config");
            outPrintWriter.println("    Reset the configuration set with \"update-config\"");
            outPrintWriter.println();
            outPrintWriter.println("[Deprecated] cmd shortcut get-default-launcher [--user USER_ID]");
            outPrintWriter.println("    Show the default launcher");
            outPrintWriter.println("    Note: This command is deprecated. Callers should query the default launcher from RoleManager instead.");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut unload-user [--user USER_ID]");
            outPrintWriter.println("    Unload a user from the memory");
            outPrintWriter.println("    (This should not affect any observable behavior)");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut clear-shortcuts [--user USER_ID] PACKAGE");
            outPrintWriter.println("    Remove all shortcuts from a package, including pinned shortcuts");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut get-shortcuts [--user USER_ID] [--flags FLAGS] PACKAGE");
            outPrintWriter.println("    Show the shortcuts for a package that match the given flags");
            outPrintWriter.println();
            outPrintWriter.println("cmd shortcut has-shortcut-access [--user USER_ID] PACKAGE");
            outPrintWriter.println("    Prints \"true\" if the package can access shortcuts, \"false\" otherwise");
            outPrintWriter.println();
        }

        private void handleResetThrottling() throws com.android.server.pm.ShortcutService.CommandException {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                parseOptionsLocked(true);
                android.util.Slog.i("ShellCommand", "cmd: handleResetThrottling: user=" + this.mUserId);
                com.android.server.pm.ShortcutService.this.resetThrottlingInner(this.mUserId);
            }
        }

        private void handleResetAllThrottling() {
            android.util.Slog.i("ShellCommand", "cmd: handleResetAllThrottling");
            com.android.server.pm.ShortcutService.this.resetAllThrottlingInner();
        }

        private void handleOverrideConfig() throws com.android.server.pm.ShortcutService.CommandException {
            java.lang.String nextArgRequired = getNextArgRequired();
            android.util.Slog.i("ShellCommand", "cmd: handleOverrideConfig: " + nextArgRequired);
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    if (!com.android.server.pm.ShortcutService.this.updateConfigurationLocked(nextArgRequired)) {
                        throw new com.android.server.pm.ShortcutService.CommandException("override-config failed.  See logcat for details.");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void handleResetConfig() {
            android.util.Slog.i("ShellCommand", "cmd: handleResetConfig");
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                com.android.server.pm.ShortcutService.this.loadConfigurationLocked();
            }
        }

        private void handleGetDefaultLauncher() throws com.android.server.pm.ShortcutService.CommandException {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    parseOptionsLocked(true);
                    java.lang.String defaultLauncher = com.android.server.pm.ShortcutService.this.getDefaultLauncher(this.mUserId);
                    if (defaultLauncher == null) {
                        throw new com.android.server.pm.ShortcutService.CommandException("Failed to get the default launcher for user " + this.mUserId);
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    com.android.server.pm.ShortcutService.this.mPackageManagerInternal.getHomeActivitiesAsUser(arrayList, com.android.server.pm.ShortcutService.this.getParentOrSelfUserId(this.mUserId));
                    java.util.Iterator it = arrayList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        android.content.pm.ComponentInfo componentInfo = ((android.content.pm.ResolveInfo) it.next()).getComponentInfo();
                        if (componentInfo.packageName.equals(defaultLauncher)) {
                            getOutPrintWriter().println("Launcher: " + componentInfo.getComponentName());
                            break;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void handleUnloadUser() throws com.android.server.pm.ShortcutService.CommandException {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                parseOptionsLocked(true);
                android.util.Slog.i("ShellCommand", "cmd: handleUnloadUser: user=" + this.mUserId);
                com.android.server.pm.ShortcutService.this.handleStopUser(this.mUserId);
            }
        }

        private void handleClearShortcuts() throws com.android.server.pm.ShortcutService.CommandException {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                parseOptionsLocked(true);
                java.lang.String nextArgRequired = getNextArgRequired();
                android.util.Slog.i("ShellCommand", "cmd: handleClearShortcuts: user" + this.mUserId + ", " + nextArgRequired);
                com.android.server.pm.ShortcutService.this.cleanUpPackageForAllLoadedUsers(nextArgRequired, this.mUserId, true);
            }
        }

        private void handleGetShortcuts() throws com.android.server.pm.ShortcutService.CommandException {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                try {
                    parseOptionsLocked(true);
                    java.lang.String nextArgRequired = getNextArgRequired();
                    android.util.Slog.i("ShellCommand", "cmd: handleGetShortcuts: user=" + this.mUserId + ", flags=" + this.mShortcutMatchFlags + ", package=" + nextArgRequired);
                    com.android.server.pm.ShortcutPackage packageShortcutsIfExists = com.android.server.pm.ShortcutService.this.getUserShortcutsLocked(this.mUserId).getPackageShortcutsIfExists(nextArgRequired);
                    if (packageShortcutsIfExists == null) {
                        return;
                    }
                    packageShortcutsIfExists.dumpShortcuts(getOutPrintWriter(), this.mShortcutMatchFlags);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void handleVerifyStates() throws com.android.server.pm.ShortcutService.CommandException {
            try {
                com.android.server.pm.ShortcutService.this.verifyStatesForce();
            } catch (java.lang.Throwable th) {
                throw new com.android.server.pm.ShortcutService.CommandException(th.getMessage() + "\n" + android.util.Log.getStackTraceString(th));
            }
        }

        private void handleHasShortcutAccess() throws com.android.server.pm.ShortcutService.CommandException {
            synchronized (com.android.server.pm.ShortcutService.this.mLock) {
                parseOptionsLocked(true);
                getOutPrintWriter().println(java.lang.Boolean.toString(com.android.server.pm.ShortcutService.this.hasShortcutHostPermissionInner(getNextArgRequired(), this.mUserId)));
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    long injectCurrentTimeMillis() {
        return java.lang.System.currentTimeMillis();
    }

    @com.android.internal.annotations.VisibleForTesting
    long injectElapsedRealtime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @com.android.internal.annotations.VisibleForTesting
    long injectUptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }

    @com.android.internal.annotations.VisibleForTesting
    int injectBinderCallingUid() {
        return android.content.pm.IShortcutService.Stub.getCallingUid();
    }

    @com.android.internal.annotations.VisibleForTesting
    int injectBinderCallingPid() {
        return android.content.pm.IShortcutService.Stub.getCallingPid();
    }

    private int getCallingUserId() {
        return android.os.UserHandle.getUserId(injectBinderCallingUid());
    }

    long injectClearCallingIdentity() {
        return android.os.Binder.clearCallingIdentity();
    }

    void injectRestoreCallingIdentity(long j) {
        android.os.Binder.restoreCallingIdentity(j);
    }

    java.lang.String injectBuildFingerprint() {
        return android.os.Build.VERSION.INCREMENTAL;
    }

    final void wtf(java.lang.String str) {
        wtf(str, null);
    }

    void wtf(java.lang.String str, java.lang.Throwable th) {
        if (th == null) {
            th = new java.lang.RuntimeException("Stacktrace");
        }
        synchronized (this.mWtfLock) {
            this.mWtfCount++;
            this.mLastWtfStacktrace = new java.lang.Exception("Last failure was logged here:");
        }
        android.util.Slog.wtf(TAG, str, th);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File injectSystemDataPath() {
        return android.os.Environment.getDataSystemDirectory();
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File injectUserDataPath(int i) {
        return new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), DIRECTORY_PER_USER);
    }

    public java.io.File getDumpPath() {
        return new java.io.File(injectUserDataPath(0), DIRECTORY_DUMP);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean injectIsLowRamDevice() {
        return android.app.ActivityManager.isLowRamDeviceStatic();
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectRegisterUidObserver(android.app.IUidObserver iUidObserver, int i) {
        try {
            android.app.ActivityManager.getService().registerUidObserver(iUidObserver, i, -1, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void injectRegisterRoleHoldersListener(android.app.role.OnRoleHoldersChangedListener onRoleHoldersChangedListener) {
        this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(this.mContext.getMainExecutor(), onRoleHoldersChangedListener, android.os.UserHandle.ALL);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String injectGetHomeRoleHolderAsUser(int i) {
        java.util.List roleHoldersAsUser = this.mRoleManager.getRoleHoldersAsUser("android.app.role.HOME", android.os.UserHandle.of(i));
        if (roleHoldersAsUser.isEmpty()) {
            return null;
        }
        return (java.lang.String) roleHoldersAsUser.get(0);
    }

    java.io.File getUserBitmapFilePath(int i) {
        return new java.io.File(injectUserDataPath(i), DIRECTORY_BITMAPS);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.util.SparseArray<com.android.server.pm.ShortcutUser> getShortcutsForTest() {
        return this.mUsers;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getMaxShortcutsForTest() {
        return this.mMaxShortcuts;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getMaxUpdatesPerIntervalForTest() {
        return this.mMaxUpdatesPerInterval;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getResetIntervalForTest() {
        return this.mResetInterval;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getMaxIconDimensionForTest() {
        return this.mMaxIconDimension;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.graphics.Bitmap.CompressFormat getIconPersistFormatForTest() {
        return this.mIconPersistFormat;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getIconPersistQualityForTest() {
        return this.mIconPersistQuality;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pm.ShortcutPackage getPackageShortcutForTest(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.ShortcutUser shortcutUser = this.mUsers.get(i);
                if (shortcutUser == null) {
                    return null;
                }
                return shortcutUser.getAllPackagesForTest().get(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.pm.ShortcutInfo getPackageShortcutForTest(java.lang.String str, java.lang.String str2, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.ShortcutPackage packageShortcutForTest = getPackageShortcutForTest(str, i);
                if (packageShortcutForTest == null) {
                    return null;
                }
                return packageShortcutForTest.findShortcutById(str2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updatePackageShortcutForTest(java.lang.String str, java.lang.String str2, int i, java.util.function.Consumer<android.content.pm.ShortcutInfo> consumer) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.ShortcutPackage packageShortcutForTest = getPackageShortcutForTest(str, i);
                if (packageShortcutForTest == null) {
                    return;
                }
                consumer.accept(packageShortcutForTest.findShortcutById(str2));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pm.ShortcutLauncher getLauncherShortcutForTest(java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.ShortcutUser shortcutUser = this.mUsers.get(i);
                if (shortcutUser == null) {
                    return null;
                }
                return shortcutUser.getAllLaunchersForTest().get(android.content.pm.UserPackage.of(i, str));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pm.ShortcutRequestPinProcessor getShortcutRequestPinProcessorForTest() {
        return this.mShortcutRequestPinProcessor;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean injectShouldPerformVerification() {
        return false;
    }

    final void verifyStates() {
        if (injectShouldPerformVerification()) {
            verifyStatesInner();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void verifyStatesForce() {
        verifyStatesInner();
    }

    private void verifyStatesInner() {
        synchronized (this.mLock) {
            forEachLoadedUserLocked(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutService.lambda$verifyStatesInner$22((com.android.server.pm.ShortcutUser) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$verifyStatesInner$22(com.android.server.pm.ShortcutUser shortcutUser) {
        shortcutUser.forAllPackageItems(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.pm.ShortcutPackageItem) obj).verifyStates();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    void waitForBitmapSavesForTest() {
        synchronized (this.mLock) {
            forEachLoadedUserLocked(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ShortcutService.lambda$waitForBitmapSavesForTest$23((com.android.server.pm.ShortcutUser) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$waitForBitmapSavesForTest$23(com.android.server.pm.ShortcutUser shortcutUser) {
        shortcutUser.forAllPackageItems(new java.util.function.Consumer() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.pm.ShortcutPackageItem) obj).waitForBitmapSaves();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.content.pm.ShortcutInfo> prepareChangedShortcuts(android.util.ArraySet<java.lang.String> arraySet, android.util.ArraySet<java.lang.String> arraySet2, java.util.List<android.content.pm.ShortcutInfo> list, com.android.server.pm.ShortcutPackage shortcutPackage) {
        if (shortcutPackage == null) {
            return null;
        }
        if (com.android.internal.util.CollectionUtils.isEmpty(arraySet) && com.android.internal.util.CollectionUtils.isEmpty(arraySet2)) {
            return null;
        }
        final android.util.ArraySet arraySet3 = new android.util.ArraySet();
        if (!com.android.internal.util.CollectionUtils.isEmpty(arraySet)) {
            arraySet3.addAll((android.util.ArraySet) arraySet);
        }
        if (!com.android.internal.util.CollectionUtils.isEmpty(arraySet2)) {
            arraySet3.addAll((android.util.ArraySet) arraySet2);
        }
        if (!com.android.internal.util.CollectionUtils.isEmpty(list)) {
            list.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$prepareChangedShortcuts$24;
                    lambda$prepareChangedShortcuts$24 = com.android.server.pm.ShortcutService.lambda$prepareChangedShortcuts$24(arraySet3, (android.content.pm.ShortcutInfo) obj);
                    return lambda$prepareChangedShortcuts$24;
                }
            });
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        shortcutPackage.findAll(arrayList, new java.util.function.Predicate() { // from class: com.android.server.pm.ShortcutService$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$prepareChangedShortcuts$25;
                lambda$prepareChangedShortcuts$25 = com.android.server.pm.ShortcutService.lambda$prepareChangedShortcuts$25(arraySet3, (android.content.pm.ShortcutInfo) obj);
                return lambda$prepareChangedShortcuts$25;
            }
        }, 4);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$prepareChangedShortcuts$24(android.util.ArraySet arraySet, android.content.pm.ShortcutInfo shortcutInfo) {
        return arraySet.contains(shortcutInfo.getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$prepareChangedShortcuts$25(android.util.ArraySet arraySet, android.content.pm.ShortcutInfo shortcutInfo) {
        return arraySet.contains(shortcutInfo.getId());
    }

    private java.util.List<android.content.pm.ShortcutInfo> prepareChangedShortcuts(java.util.List<android.content.pm.ShortcutInfo> list, java.util.List<android.content.pm.ShortcutInfo> list2, java.util.List<android.content.pm.ShortcutInfo> list3, com.android.server.pm.ShortcutPackage shortcutPackage) {
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        addShortcutIdsToSet(arraySet, list);
        android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>();
        addShortcutIdsToSet(arraySet2, list2);
        return prepareChangedShortcuts(arraySet, arraySet2, list3, shortcutPackage);
    }

    private void addShortcutIdsToSet(android.util.ArraySet<java.lang.String> arraySet, java.util.List<android.content.pm.ShortcutInfo> list) {
        if (com.android.internal.util.CollectionUtils.isEmpty(list)) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            arraySet.add(list.get(i).getId());
        }
    }
}
