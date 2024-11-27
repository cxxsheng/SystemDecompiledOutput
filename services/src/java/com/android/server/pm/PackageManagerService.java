package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageManagerService implements com.android.server.pm.PackageSender, android.content.pm.TestUtilityService {
    public static final java.lang.String APP_METADATA_FILE_IN_APK_PATH = "assets/app.metadata";
    public static final java.lang.String APP_METADATA_FILE_NAME = "app.metadata";
    private static final android.os.Handler.Callback BACKGROUND_HANDLER_CALLBACK;
    private static final int BLUETOOTH_UID = 1002;
    private static final long BROADCAST_DELAY = 1000;
    private static final long BROADCAST_DELAY_DURING_STARTUP = 10000;
    static final int CHECK_PENDING_INTEGRITY_VERIFICATION = 26;
    static final int CHECK_PENDING_VERIFICATION = 16;
    private static final java.lang.String COMPANION_PACKAGE_NAME = "com.android.companiondevicemanager";
    public static final java.lang.String COMPRESSED_EXTENSION = ".gz";
    static final boolean DEBUG_ABI_SELECTION = false;
    static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_DEXOPT = false;
    static final boolean DEBUG_DOMAIN_VERIFICATION = false;
    public static final boolean DEBUG_INSTALL = false;
    static final boolean DEBUG_INTENT_MATCHING = false;
    static final boolean DEBUG_PACKAGE_INFO = false;
    public static final boolean DEBUG_PACKAGE_SCANNING = false;
    public static final boolean DEBUG_PERMISSIONS = false;
    private static final boolean DEBUG_PER_UID_READ_TIMEOUTS = false;
    static final boolean DEBUG_PREFERRED = false;
    public static final boolean DEBUG_REMOVE = false;
    public static final boolean DEBUG_SETTINGS = false;
    static final boolean DEBUG_UPGRADE = false;
    static final boolean DEBUG_VERIFY = false;
    static final int DEFAULT_FILE_ACCESS_MODE = 420;
    static final int DEFAULT_NATIVE_LIBRARY_FILE_ACCESS_MODE = 493;
    static final long DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD;
    static final int DEFAULT_VERIFICATION_RESPONSE = 1;
    static final int DEFERRED_NO_KILL_INSTALL_OBSERVER = 24;
    private static final int DEFERRED_NO_KILL_INSTALL_OBSERVER_DELAY_MS = 500;
    static final int DEFERRED_NO_KILL_POST_DELETE = 23;
    private static final int DEFERRED_NO_KILL_POST_DELETE_DELAY_MS = 3000;
    private static final long DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED;
    static final int DEFERRED_PENDING_KILL_INSTALL_OBSERVER = 29;
    private static final int DEFERRED_PENDING_KILL_INSTALL_OBSERVER_DELAY_MS = 1000;
    static final int DOMAIN_VERIFICATION = 27;
    private static final android.os.incremental.PerUidReadTimeouts[] EMPTY_PER_UID_READ_TIMEOUTS_ARRAY;
    private static final boolean ENABLE_BOOST = false;
    static final int ENABLE_ROLLBACK_STATUS = 21;
    static final int ENABLE_ROLLBACK_TIMEOUT = 22;
    static final boolean HIDE_EPHEMERAL_APIS = false;
    static final int INSTANT_APP_RESOLUTION_PHASE_TWO = 20;
    static final int INTEGRITY_VERIFICATION_COMPLETE = 25;
    private static final int LOG_UID = 1007;
    public static final int MIN_INSTALLABLE_TARGET_SDK;
    private static final int NETWORKSTACK_UID = 1073;
    private static final int NFC_UID = 1027;
    static final java.lang.String PACKAGE_MIME_TYPE = "application/vnd.android.package-archive";
    static final java.lang.String PACKAGE_SCHEME = "package";
    public static final int PACKAGE_STARTABILITY_DIRECT_BOOT_UNSUPPORTED = 4;
    public static final int PACKAGE_STARTABILITY_FROZEN = 3;
    public static final int PACKAGE_STARTABILITY_NOT_FOUND = 1;
    public static final int PACKAGE_STARTABILITY_NOT_SYSTEM = 2;
    public static final int PACKAGE_STARTABILITY_OK = 0;
    static final int PACKAGE_VERIFIED = 15;
    public static final java.lang.String PLATFORM_PACKAGE_NAME = "android";
    static final int POST_INSTALL = 9;
    static final java.lang.String PRECOMPILE_LAYOUTS = "pm.precompile_layouts";
    private static final java.lang.String PROPERTY_DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED = "deferred_no_kill_post_delete_delay_ms_extended";
    private static final java.lang.String PROPERTY_INCFS_DEFAULT_TIMEOUTS = "incfs_default_timeouts";
    private static final java.lang.String PROPERTY_IS_PRE_APPROVAL_REQUEST_AVAILABLE = "is_preapproval_available";
    private static final java.lang.String PROPERTY_IS_UPDATE_OWNERSHIP_ENFORCEMENT_AVAILABLE = "is_update_ownership_enforcement_available";
    private static final java.lang.String PROPERTY_KNOWN_DIGESTERS_LIST = "known_digesters_list";
    private static final long PRUNE_UNUSED_SHARED_LIBRARIES_DELAY;
    static final int PRUNE_UNUSED_STATIC_SHARED_LIBRARIES = 28;
    private static final int RADIO_UID = 1001;
    static final char RANDOM_CODEPATH_PREFIX = '-';
    static final java.lang.String RANDOM_DIR_PREFIX = "~~";
    public static final int REASON_AB_OTA = 10;
    public static final int REASON_BACKGROUND_DEXOPT = 9;
    public static final int REASON_BOOT_AFTER_MAINLINE_UPDATE = 13;
    public static final int REASON_BOOT_AFTER_OTA = 1;
    public static final int REASON_CMDLINE = 12;
    public static final int REASON_FIRST_BOOT = 0;
    public static final int REASON_INACTIVE_PACKAGE_DOWNGRADE = 11;
    public static final int REASON_INSTALL = 3;
    public static final int REASON_INSTALL_BULK = 5;
    public static final int REASON_INSTALL_BULK_DOWNGRADED = 7;
    public static final int REASON_INSTALL_BULK_SECONDARY = 6;
    public static final int REASON_INSTALL_BULK_SECONDARY_DOWNGRADED = 8;
    public static final int REASON_INSTALL_FAST = 4;
    public static final int REASON_LAST = 14;
    public static final int REASON_POST_BOOT = 2;
    public static final int REASON_SHARED = 14;
    private static final int REQUIRED_VERIFIERS_MAX_COUNT = 2;
    static final int SCAN_AS_APEX = 67108864;
    static final int SCAN_AS_APK_IN_APEX = 8388608;
    static final int SCAN_AS_FACTORY = 33554432;
    static final int SCAN_AS_FULL_APP = 16384;
    static final int SCAN_AS_INSTANT_APP = 8192;
    static final int SCAN_AS_ODM = 4194304;
    static final int SCAN_AS_OEM = 262144;
    static final int SCAN_AS_PRIVILEGED = 131072;
    static final int SCAN_AS_PRODUCT = 1048576;
    static final int SCAN_AS_STOPPED_SYSTEM_APP = 134217728;
    static final int SCAN_AS_SYSTEM = 65536;
    static final int SCAN_AS_SYSTEM_EXT = 2097152;
    static final int SCAN_AS_VENDOR = 524288;
    static final int SCAN_AS_VIRTUAL_PRELOAD = 32768;
    static final int SCAN_BOOTING = 16;
    static final int SCAN_DONT_KILL_APP = 1024;
    static final int SCAN_DROP_CACHE = 16777216;
    static final int SCAN_FIRST_BOOT_OR_UPGRADE = 4096;
    static final int SCAN_IGNORE_FROZEN = 2048;
    static final int SCAN_INITIAL = 512;
    static final int SCAN_MOVE = 256;
    static final int SCAN_NEW_INSTALL = 4;
    static final int SCAN_NO_DEX = 1;
    static final int SCAN_REQUIRE_KNOWN = 128;
    static final int SCAN_UPDATE_SIGNATURE = 2;
    static final int SCAN_UPDATE_TIME = 8;
    static final int SEND_PENDING_BROADCAST = 1;
    private static final int SE_UID = 1068;
    static final java.lang.String SHELL_PACKAGE_NAME = "com.android.shell";
    private static final int SHELL_UID = 2000;
    private static final java.lang.String STATIC_SHARED_LIB_DELIMITER = "_";
    public static final java.lang.String STUB_SUFFIX = "-Stub";

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public static final java.util.List<com.android.server.pm.ScanPartition> SYSTEM_PARTITIONS;
    static final java.lang.String TAG = "PackageManager";
    private static final long THROW_EXCEPTION_ON_REQUIRE_INSTALL_PACKAGES_TO_ADD_INSTALLER_PACKAGE = 150857253;
    public static final boolean TRACE_SNAPSHOTS = false;
    private static final int UWB_UID = 1083;
    static final long WATCHDOG_TIMEOUT = 600000;
    static final int WRITE_DIRTY_PACKAGE_RESTRICTIONS = 14;
    private static final long WRITE_LOCK_TIMEOUT_MS = 10000;
    static final int WRITE_PACKAGE_LIST = 19;
    static final int WRITE_SETTINGS = 13;
    static final int WRITE_SETTINGS_DELAY = 10000;
    static final int WRITE_USER_PACKAGE_RESTRICTIONS = 30;
    private static final java.util.concurrent.atomic.AtomicReference<com.android.server.pm.Computer> sSnapshot;
    private static final java.util.concurrent.atomic.AtomicInteger sSnapshotPendingVersion;
    private static com.android.server.ThreadPriorityBooster sThreadPriorityBooster;

    @android.annotation.Nullable
    final java.lang.String mAmbientContextDetectionPackage;

    @com.android.server.utils.Watched(manual = true)
    private android.content.pm.ApplicationInfo mAndroidApplication;
    final com.android.server.pm.ApexManager mApexManager;
    private final com.android.server.pm.AppDataHelper mAppDataHelper;
    private final java.io.File mAppInstallDir;

    @android.annotation.Nullable
    final java.lang.String mAppPredictionServicePackage;
    private com.nvidia.NvAppProfileService mAppProfileService;

    @com.android.server.utils.Watched
    final com.android.server.pm.AppsFilterImpl mAppsFilter;
    final com.android.server.pm.dex.ArtManagerService mArtManagerService;
    private final android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> mAvailableFeatures;

    @android.annotation.Nullable
    final com.android.server.pm.BackgroundDexOptService mBackgroundDexOptService;
    final android.os.Handler mBackgroundHandler;
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private java.io.File mCacheDir;

    @android.annotation.NonNull
    final com.android.server.pm.ChangedPackagesTracker mChangedPackagesTracker;
    final com.android.server.pm.CompilerStats mCompilerStats;

    @com.android.server.utils.Watched
    final com.android.server.pm.resolution.ComponentResolver mComponentResolver;

    @android.annotation.Nullable
    final java.lang.String mConfiguratorPackage;
    final android.content.Context mContext;
    android.content.ComponentName mCustomResolverComponentName;
    private final int mDefParseFlags;
    private final com.android.server.pm.DefaultAppProvider mDefaultAppProvider;

    @android.annotation.Nullable
    final java.lang.String mDefaultTextClassifierPackage;
    private final com.android.server.pm.DeletePackageHelper mDeletePackageHelper;
    private android.app.admin.IDevicePolicyManager mDevicePolicyManager;
    private final com.android.server.pm.dex.DexManager mDexManager;
    private final com.android.server.pm.DexOptHelper mDexOptHelper;
    final android.util.ArraySet<java.lang.Integer> mDirtyUsers;
    java.util.ArrayList<android.content.ComponentName> mDisabledComponentsList;
    private final com.android.server.pm.DistractingPackageHelper mDistractingPackageHelper;
    private final com.android.server.pm.DomainVerificationConnection mDomainVerificationConnection;

    @android.annotation.NonNull
    final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;
    private final com.android.server.pm.dex.DynamicCodeLogger mDynamicCodeLogger;

    @android.annotation.Nullable
    private android.util.ArraySet<java.lang.String> mExistingPackages;
    android.content.pm.PackageManagerInternal.ExternalSourcesPolicy mExternalSourcesPolicy;
    final boolean mFactoryTest;
    private boolean mFirstBoot;
    private final com.android.server.pm.FreeStorageHelper mFreeStorageHelper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final com.android.server.utils.WatchedArrayMap<java.lang.String, java.lang.Integer> mFrozenPackages;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayMap<java.lang.String, java.lang.Integer>> mFrozenPackagesSnapshot;
    final android.os.Handler mHandler;

    @android.annotation.Nullable
    final java.lang.String mIncidentReportApproverPackage;
    final android.os.incremental.IncrementalManager mIncrementalManager;
    private final java.lang.String mIncrementalVersion;
    private final com.android.server.pm.InitAppsHelper mInitAppsHelper;

    @android.annotation.NonNull
    final java.util.Set<java.lang.String> mInitialNonStoppedSystemPackages;
    final com.android.server.pm.PackageManagerServiceInjector mInjector;
    final java.lang.Object mInstallLock;

    @android.annotation.NonNull
    private final com.android.server.pm.InstallPackageHelper mInstallPackageHelper;

    @com.android.internal.annotations.GuardedBy({"mInstallLock"})
    final com.android.server.pm.Installer mInstaller;
    final com.android.server.pm.PackageInstallerService mInstallerService;

    @com.android.server.utils.Watched(manual = true)
    android.content.pm.ActivityInfo mInstantAppInstallerActivity;

    @com.android.server.utils.Watched(manual = true)
    private final android.content.pm.ResolveInfo mInstantAppInstallerInfo;

    @com.android.server.utils.Watched
    final com.android.server.pm.InstantAppRegistry mInstantAppRegistry;
    final com.android.server.pm.InstantAppResolverConnection mInstantAppResolverConnection;
    final android.content.ComponentName mInstantAppResolverSettingsComponent;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedInstrumentation> mInstrumentation;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedInstrumentation>> mInstrumentationSnapshot;
    final boolean mIsEngBuild;
    private final boolean mIsPreNMR1Upgrade;
    private final boolean mIsPreQUpgrade;
    private final boolean mIsUpgrade;
    private final boolean mIsUserDebugBuild;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.GuardedBy({"mLock"})
    final com.android.server.utils.WatchedSparseIntArray mIsolatedOwners;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseIntArray> mIsolatedOwnersSnapshot;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mKeepUninstalledPackages"})
    private final android.util.ArraySet<java.lang.String> mKeepUninstalledPackages;
    private final com.android.server.pm.permission.LegacyPermissionManagerInternal mLegacyPermissionManager;
    private com.android.server.pm.ComputerLocked mLiveComputer;
    final com.android.server.pm.PackageManagerTracedLock mLock;
    final android.util.DisplayMetrics mMetrics;
    private final com.android.server.pm.ModuleInfoProvider mModuleInfoProvider;
    final com.android.server.pm.MovePackageHelper.MoveCallbacks mMoveCallbacks;
    int mNextInstallToken;
    private final java.util.concurrent.atomic.AtomicInteger mNextMoveId;
    private final java.util.Map<java.lang.String, com.android.server.pm.InstallRequest> mNoKillInstallObservers;

    @android.annotation.NonNull
    private final com.android.internal.content.om.OverlayConfig mOverlayConfig;

    @android.annotation.Nullable
    final java.lang.String mOverlayConfigSignaturePackage;
    private final com.android.server.pm.PackageManagerTracedLock mOverlayPathsLock;
    final com.android.server.pm.PackageDexOptimizer mPackageDexOptimizer;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageMonitorCallbackHelper mPackageMonitorCallbackHelper;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageObserverHelper mPackageObserverHelper;
    final com.android.internal.pm.parsing.PackageParser2.Callback mPackageParserCallback;
    private final com.android.server.pm.PackageProperty mPackageProperty;
    private final com.android.server.pm.pkg.mutate.PackageStateMutator mPackageStateMutator;
    private final com.android.server.pm.PackageManagerTracedLock mPackageStateWriteLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.pm.PackageUsage mPackageUsage;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.GuardedBy({"mLock"})
    final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.pkg.AndroidPackage> mPackages;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.pkg.AndroidPackage>> mPackagesSnapshot;
    final com.android.server.pm.PendingPackageBroadcasts mPendingBroadcasts;
    final android.util.SparseArray<com.android.server.pm.VerifyingSession> mPendingEnableRollback;
    int mPendingEnableRollbackToken;
    private final java.util.Map<java.lang.String, com.android.server.pm.InstallRequest> mPendingKillInstallObservers;
    final android.util.SparseArray<com.android.server.pm.PackageVerificationState> mPendingVerification;
    int mPendingVerificationToken;
    android.os.incremental.PerUidReadTimeouts[] mPerUidReadTimeoutsCache;
    final com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManager;
    private com.android.server.pm.pkg.AndroidPackage mPlatformPackage;
    private java.lang.String[] mPlatformPackageOverlayPaths;
    private java.lang.String[] mPlatformPackageOverlayResourceDirs;
    private final com.android.server.pm.PreferredActivityHelper mPreferredActivityHelper;
    private java.util.concurrent.Future<?> mPrepareAppDataFuture;
    private final int mPriorSdkVersion;
    final com.android.server.pm.ProcessLoggingHandler mProcessLoggingHandler;
    boolean mPromoteSystemApps;

    @com.android.internal.annotations.GuardedBy({"mProtectedBroadcasts"})
    final android.util.ArraySet<java.lang.String> mProtectedBroadcasts;
    final com.android.server.pm.ProtectedPackages mProtectedPackages;

    @android.annotation.Nullable
    final java.lang.String mRecentsPackage;

    @android.annotation.Nullable
    java.util.List<java.io.File> mReleaseOnSystemReady;
    private final com.android.server.pm.RemovePackageHelper mRemovePackageHelper;
    private java.lang.String[] mReplacedResolverPackageOverlayPaths;
    private java.lang.String[] mReplacedResolverPackageOverlayResourceDirs;

    @android.annotation.NonNull
    final java.lang.String mRequiredInstallerPackage;

    @android.annotation.NonNull
    final java.lang.String mRequiredPermissionControllerPackage;

    @android.annotation.NonNull
    private final java.lang.String mRequiredSdkSandboxPackage;

    @android.annotation.NonNull
    final java.lang.String mRequiredUninstallerPackage;

    @android.annotation.NonNull
    final java.lang.String[] mRequiredVerifierPackages;

    @com.android.server.utils.Watched(manual = true)
    private final android.content.pm.ActivityInfo mResolveActivity;

    @com.android.server.utils.Watched(manual = true)
    android.content.ComponentName mResolveComponentName;
    private final android.content.pm.ResolveInfo mResolveInfo;
    private final com.android.server.pm.ResolveIntentHelper mResolveIntentHelper;
    private boolean mResolverReplaced;

    @android.annotation.Nullable
    final java.lang.String mRetailDemoPackage;
    final android.util.SparseArray<com.android.server.pm.InstallRequest> mRunningInstalls;

    @com.android.server.utils.Watched(manual = true)
    private volatile boolean mSafeMode;
    private final int mSdkVersion;
    private final java.lang.String[] mSeparateProcesses;
    private long mServiceStartWithDelay;

    @android.annotation.Nullable
    final java.lang.String mServicesExtensionPackageName;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.GuardedBy({"mLock"})
    final com.android.server.pm.Settings mSettings;

    @android.annotation.Nullable
    final java.lang.String mSetupWizardPackage;

    @com.android.server.utils.Watched
    private final com.android.server.pm.SharedLibrariesImpl mSharedLibraries;

    @android.annotation.Nullable
    final java.lang.String mSharedSystemSharedLibraryPackageName;
    final boolean mShouldStopSystemPackagesByDefault;
    private final java.lang.Object mSnapshotLock;

    @android.annotation.Nullable
    private final com.android.server.pm.SnapshotStatistics mSnapshotStatistics;
    private final com.android.server.pm.StorageEventHelper mStorageEventHelper;

    @android.annotation.Nullable
    final java.lang.String mStorageManagerPackage;
    private final com.android.server.pm.SuspendPackageHelper mSuspendPackageHelper;

    @com.android.server.utils.Watched(manual = true)
    private volatile boolean mSystemReady;

    @android.annotation.Nullable
    final java.lang.String mSystemTextClassifierPackageName;
    private final android.content.pm.TestUtilityService mTestUtilityService;
    final android.util.ArraySet<java.lang.String> mTransferredPackages;
    final com.android.server.pm.UserManagerService mUserManager;
    final com.android.server.pm.UserNeedsBadgingCache mUserNeedsBadging;
    private final com.android.server.utils.Watcher mWatcher;

    @android.annotation.Nullable
    final java.lang.String mWearableSensingPackage;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedSparseBooleanArray mWebInstantAppsDisabled;
    public static final boolean DEBUG_COMPRESSION = android.os.Build.IS_DEBUGGABLE;
    public static final boolean DEBUG_INSTANT = android.os.Build.IS_DEBUGGABLE;
    static final int[] EMPTY_INT_ARRAY = new int[0];

    static class FindPreferredActivityBodyResult {
        boolean mChanged;
        android.content.pm.ResolveInfo mPreferredResolveInfo;

        FindPreferredActivityBodyResult() {
        }
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PackageStartability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScanFlags {
    }

    static {
        MIN_INSTALLABLE_TARGET_SDK = android.content.pm.Flags.minTargetSdk24() ? 24 : 23;
        SYSTEM_PARTITIONS = java.util.Collections.unmodifiableList(android.content.pm.PackagePartitions.getOrderedPartitions(new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return new com.android.server.pm.ScanPartition((android.content.pm.PackagePartitions.SystemPartition) obj);
            }
        }));
        EMPTY_PER_UID_READ_TIMEOUTS_ARRAY = new android.os.incremental.PerUidReadTimeouts[0];
        DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED = java.util.concurrent.TimeUnit.DAYS.toMillis(1L);
        PRUNE_UNUSED_SHARED_LIBRARIES_DELAY = java.util.concurrent.TimeUnit.MINUTES.toMillis(3L);
        DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD = java.util.concurrent.TimeUnit.DAYS.toMillis(7L);
        sThreadPriorityBooster = new com.android.server.ThreadPriorityBooster(-2, 3);
        sSnapshot = new java.util.concurrent.atomic.AtomicReference<>();
        sSnapshotPendingVersion = new java.util.concurrent.atomic.AtomicInteger(1);
        BACKGROUND_HANDLER_CALLBACK = new android.os.Handler.Callback() { // from class: com.android.server.pm.PackageManagerService.2
            @Override // android.os.Handler.Callback
            public boolean handleMessage(@android.annotation.NonNull android.os.Message message) {
                switch (message.what) {
                    case 14:
                        ((com.android.server.pm.PackageManagerService) message.obj).writePendingRestrictions();
                        break;
                    case 30:
                        ((java.lang.Runnable) message.obj).run();
                        break;
                }
                return true;
            }
        };
    }

    private static class DefaultSystemWrapper implements com.android.server.pm.PackageManagerServiceInjector.SystemWrapper {
        private DefaultSystemWrapper() {
        }

        @Override // com.android.server.pm.PackageManagerServiceInjector.SystemWrapper
        public void disablePackageCaches() {
            android.content.pm.PackageManager.disableApplicationInfoCache();
            android.content.pm.PackageManager.disablePackageInfoCache();
            android.app.ApplicationPackageManager.invalidateGetPackagesForUidCache();
            android.app.ApplicationPackageManager.disableGetPackagesForUidCache();
            android.app.ApplicationPackageManager.invalidateHasSystemFeatureCache();
            android.content.pm.PackageManager.corkPackageInfoCache();
        }

        @Override // com.android.server.pm.PackageManagerServiceInjector.SystemWrapper
        public void enablePackageCaches() {
            android.content.pm.PackageManager.uncorkPackageInfoCache();
        }
    }

    public static void boostPriorityForPackageManagerTracedLockedSection() {
    }

    public static void resetPriorityAfterPackageManagerTracedLockedSection() {
    }

    public static void invalidatePackageInfoCache() {
        android.content.pm.PackageManager.invalidatePackageInfoCache();
        onChanged();
    }

    class Snapshot {
        public static final int LIVE = 1;
        public static final int SNAPPED = 2;
        public final android.content.pm.ApplicationInfo androidApplication;
        public final java.lang.String appPredictionServicePackage;
        public final com.android.server.pm.AppsFilterSnapshot appsFilter;
        public final com.android.server.pm.resolution.ComponentResolverApi componentResolver;
        public final com.android.server.utils.WatchedArrayMap<java.lang.String, java.lang.Integer> frozenPackages;
        public final android.content.pm.ActivityInfo instantAppInstallerActivity;
        public final android.content.pm.ResolveInfo instantAppInstallerInfo;
        public final com.android.server.pm.InstantAppRegistry instantAppRegistry;
        public final com.android.server.utils.WatchedArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedInstrumentation> instrumentation;
        public final com.android.server.utils.WatchedSparseIntArray isolatedOwners;
        public final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.pkg.AndroidPackage> packages;
        public final android.content.pm.ActivityInfo resolveActivity;
        public final android.content.ComponentName resolveComponentName;
        public final com.android.server.pm.PackageManagerService service;
        public final com.android.server.pm.Settings settings;
        public final com.android.server.pm.SharedLibrariesRead sharedLibraries;
        public final com.android.server.utils.WatchedSparseBooleanArray webInstantAppsDisabled;

        Snapshot(int i) {
            android.content.pm.ActivityInfo activityInfo;
            if (i == 2) {
                this.settings = com.android.server.pm.PackageManagerService.this.mSettings.snapshot();
                this.isolatedOwners = (com.android.server.utils.WatchedSparseIntArray) com.android.server.pm.PackageManagerService.this.mIsolatedOwnersSnapshot.snapshot();
                this.packages = (com.android.server.utils.WatchedArrayMap) com.android.server.pm.PackageManagerService.this.mPackagesSnapshot.snapshot();
                this.instrumentation = (com.android.server.utils.WatchedArrayMap) com.android.server.pm.PackageManagerService.this.mInstrumentationSnapshot.snapshot();
                this.resolveComponentName = com.android.server.pm.PackageManagerService.this.mResolveComponentName == null ? null : com.android.server.pm.PackageManagerService.this.mResolveComponentName.clone();
                this.resolveActivity = new android.content.pm.ActivityInfo(com.android.server.pm.PackageManagerService.this.mResolveActivity);
                if (com.android.server.pm.PackageManagerService.this.mInstantAppInstallerActivity == null) {
                    activityInfo = null;
                } else {
                    activityInfo = new android.content.pm.ActivityInfo(com.android.server.pm.PackageManagerService.this.mInstantAppInstallerActivity);
                }
                this.instantAppInstallerActivity = activityInfo;
                this.instantAppInstallerInfo = new android.content.pm.ResolveInfo(com.android.server.pm.PackageManagerService.this.mInstantAppInstallerInfo);
                this.webInstantAppsDisabled = com.android.server.pm.PackageManagerService.this.mWebInstantAppsDisabled.snapshot();
                this.instantAppRegistry = com.android.server.pm.PackageManagerService.this.mInstantAppRegistry.snapshot();
                this.androidApplication = com.android.server.pm.PackageManagerService.this.mAndroidApplication != null ? new android.content.pm.ApplicationInfo(com.android.server.pm.PackageManagerService.this.mAndroidApplication) : null;
                this.appPredictionServicePackage = com.android.server.pm.PackageManagerService.this.mAppPredictionServicePackage;
                this.appsFilter = com.android.server.pm.PackageManagerService.this.mAppsFilter.snapshot();
                this.componentResolver = com.android.server.pm.PackageManagerService.this.mComponentResolver.snapshot();
                this.frozenPackages = (com.android.server.utils.WatchedArrayMap) com.android.server.pm.PackageManagerService.this.mFrozenPackagesSnapshot.snapshot();
                this.sharedLibraries = com.android.server.pm.PackageManagerService.this.mSharedLibraries.snapshot();
            } else if (i == 1) {
                this.settings = com.android.server.pm.PackageManagerService.this.mSettings;
                this.isolatedOwners = com.android.server.pm.PackageManagerService.this.mIsolatedOwners;
                this.packages = com.android.server.pm.PackageManagerService.this.mPackages;
                this.instrumentation = com.android.server.pm.PackageManagerService.this.mInstrumentation;
                this.resolveComponentName = com.android.server.pm.PackageManagerService.this.mResolveComponentName;
                this.resolveActivity = com.android.server.pm.PackageManagerService.this.mResolveActivity;
                this.instantAppInstallerActivity = com.android.server.pm.PackageManagerService.this.mInstantAppInstallerActivity;
                this.instantAppInstallerInfo = com.android.server.pm.PackageManagerService.this.mInstantAppInstallerInfo;
                this.webInstantAppsDisabled = com.android.server.pm.PackageManagerService.this.mWebInstantAppsDisabled;
                this.instantAppRegistry = com.android.server.pm.PackageManagerService.this.mInstantAppRegistry;
                this.androidApplication = com.android.server.pm.PackageManagerService.this.mAndroidApplication;
                this.appPredictionServicePackage = com.android.server.pm.PackageManagerService.this.mAppPredictionServicePackage;
                this.appsFilter = com.android.server.pm.PackageManagerService.this.mAppsFilter;
                this.componentResolver = com.android.server.pm.PackageManagerService.this.mComponentResolver;
                this.frozenPackages = com.android.server.pm.PackageManagerService.this.mFrozenPackages;
                this.sharedLibraries = com.android.server.pm.PackageManagerService.this.mSharedLibraries;
            } else {
                throw new java.lang.IllegalArgumentException();
            }
            this.service = com.android.server.pm.PackageManagerService.this;
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public com.android.server.pm.Computer snapshotComputer() {
        return snapshotComputer(true);
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public com.android.server.pm.Computer snapshotComputer(boolean z) {
        boolean holdsLock = java.lang.Thread.holdsLock(this.mLock);
        if (z && holdsLock) {
            return this.mLiveComputer;
        }
        com.android.server.pm.Computer computer = sSnapshot.get();
        int i = sSnapshotPendingVersion.get();
        if (computer != null && computer.getVersion() == i) {
            return computer.use();
        }
        if (holdsLock) {
            com.android.server.pm.Computer rebuildSnapshot = rebuildSnapshot(computer, i);
            sSnapshot.set(rebuildSnapshot);
            return rebuildSnapshot.use();
        }
        synchronized (this.mSnapshotLock) {
            try {
                com.android.server.pm.Computer computer2 = sSnapshot.get();
                int i2 = sSnapshotPendingVersion.get();
                if (computer2 != null && computer2.getVersion() == i2) {
                    return computer2.use();
                }
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.Computer computer3 = sSnapshot.get();
                        int i3 = sSnapshotPendingVersion.get();
                        if (computer3 != null && computer3.getVersion() == i3) {
                            com.android.server.pm.Computer use = computer3.use();
                            resetPriorityAfterPackageManagerTracedLockedSection();
                            return use;
                        }
                        com.android.server.pm.Computer rebuildSnapshot2 = rebuildSnapshot(computer3, i3);
                        sSnapshot.set(rebuildSnapshot2);
                        com.android.server.pm.Computer use2 = rebuildSnapshot2.use();
                        resetPriorityAfterPackageManagerTracedLockedSection();
                        return use2;
                    } catch (java.lang.Throwable th) {
                        resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.pm.Computer rebuildSnapshot(@android.annotation.Nullable com.android.server.pm.Computer computer, int i) {
        long currentTimeMicro = android.os.SystemClock.currentTimeMicro();
        int used = computer == null ? -1 : computer.getUsed();
        com.android.server.pm.ComputerEngine computerEngine = new com.android.server.pm.ComputerEngine(new com.android.server.pm.PackageManagerService.Snapshot(2), i);
        long currentTimeMicro2 = android.os.SystemClock.currentTimeMicro();
        if (this.mSnapshotStatistics != null) {
            this.mSnapshotStatistics.rebuild(currentTimeMicro, currentTimeMicro2, used, computerEngine.getPackageStates().size());
        }
        return computerEngine;
    }

    private com.android.server.pm.ComputerLocked createLiveComputer() {
        return new com.android.server.pm.ComputerLocked(new com.android.server.pm.PackageManagerService.Snapshot(1));
    }

    public static void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        sSnapshotPendingVersion.incrementAndGet();
    }

    static void onChanged() {
        onChange(null);
    }

    void notifyInstallObserver(java.lang.String str, boolean z) {
        com.android.server.pm.InstallRequest remove = z ? this.mPendingKillInstallObservers.remove(str) : this.mNoKillInstallObservers.remove(str);
        if (remove != null) {
            notifyInstallObserver(remove);
        }
    }

    void notifyInstallObserver(com.android.server.pm.InstallRequest installRequest) {
        if (installRequest.getObserver() != null) {
            try {
                installRequest.getObserver().onPackageInstalled(installRequest.getName(), installRequest.getReturnCode(), installRequest.getReturnMsg(), extrasForInstallResult(installRequest));
            } catch (android.os.RemoteException e) {
                android.util.Slog.i(TAG, "Observer no longer exists.");
            }
        }
    }

    void scheduleDeferredNoKillInstallObserver(com.android.server.pm.InstallRequest installRequest) {
        java.lang.String packageName = installRequest.getPkg().getPackageName();
        this.mNoKillInstallObservers.put(packageName, installRequest);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(24, packageName), 500L);
    }

    void scheduleDeferredNoKillPostDelete(com.android.server.pm.CleanUpArgs cleanUpArgs) {
        long j;
        android.os.Message obtainMessage = this.mHandler.obtainMessage(23, cleanUpArgs);
        if (!android.content.pm.Flags.improveInstallDontKill()) {
            j = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS;
        } else {
            j = ((java.lang.Long) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda5
                public final java.lang.Object getOrThrow() {
                    java.lang.Long lambda$scheduleDeferredNoKillPostDelete$0;
                    lambda$scheduleDeferredNoKillPostDelete$0 = com.android.server.pm.PackageManagerService.lambda$scheduleDeferredNoKillPostDelete$0();
                    return lambda$scheduleDeferredNoKillPostDelete$0;
                }
            })).longValue();
            android.util.Slog.w(TAG, "Delaying the deletion of <" + cleanUpArgs.getCodePath() + "> by " + j + "ms or till the next reboot");
        }
        this.mHandler.sendMessageDelayed(obtainMessage, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Long lambda$scheduleDeferredNoKillPostDelete$0() throws java.lang.Exception {
        return java.lang.Long.valueOf(android.provider.DeviceConfig.getLong("package_manager_service", PROPERTY_DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED, DEFERRED_NO_KILL_POST_DELETE_DELAY_MS_EXTENDED));
    }

    void schedulePruneUnusedStaticSharedLibraries(boolean z) {
        this.mHandler.removeMessages(28);
        this.mHandler.sendEmptyMessageDelayed(28, z ? getPruneUnusedSharedLibrariesDelay() : 0L);
    }

    void scheduleDeferredPendingKillInstallObserver(com.android.server.pm.InstallRequest installRequest) {
        java.lang.String packageName = installRequest.getPkg().getPackageName();
        this.mPendingKillInstallObservers.put(packageName, installRequest);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(29, packageName), 1000L);
    }

    private static long getPruneUnusedSharedLibrariesDelay() {
        return android.os.SystemProperties.getLong("debug.pm.prune_unused_shared_libraries_delay", PRUNE_UNUSED_SHARED_LIBRARIES_DELAY);
    }

    public void requestFileChecksums(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull final java.lang.String str, final int i, final int i2, @android.annotation.Nullable java.util.List list, @android.annotation.NonNull final android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener) throws java.io.FileNotFoundException {
        if (!file.exists()) {
            throw new java.io.FileNotFoundException(file.getAbsolutePath());
        }
        java.util.concurrent.Executor backgroundExecutor = this.mInjector.getBackgroundExecutor();
        final android.os.Handler backgroundHandler = this.mInjector.getBackgroundHandler();
        final java.security.cert.Certificate[] decodeCertificates = list != null ? decodeCertificates(list) : null;
        final java.util.ArrayList arrayList = new java.util.ArrayList(1);
        arrayList.add(android.util.Pair.create(null, file));
        backgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageManagerService.this.lambda$requestFileChecksums$4(backgroundHandler, arrayList, i, i2, str, decodeCertificates, iOnChecksumsReadyListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestFileChecksums$4(final android.os.Handler handler, java.util.List list, int i, int i2, java.lang.String str, java.security.cert.Certificate[] certificateArr, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        com.android.server.pm.ApkChecksums.Injector.Producer producer = new com.android.server.pm.ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda25
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final java.lang.Object produce() {
                android.content.Context lambda$requestFileChecksums$1;
                lambda$requestFileChecksums$1 = com.android.server.pm.PackageManagerService.this.lambda$requestFileChecksums$1();
                return lambda$requestFileChecksums$1;
            }
        };
        com.android.server.pm.ApkChecksums.Injector.Producer producer2 = new com.android.server.pm.ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda26
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final java.lang.Object produce() {
                android.os.Handler lambda$requestFileChecksums$2;
                lambda$requestFileChecksums$2 = com.android.server.pm.PackageManagerService.lambda$requestFileChecksums$2(handler);
                return lambda$requestFileChecksums$2;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        java.util.Objects.requireNonNull(packageManagerServiceInjector);
        com.android.server.pm.ApkChecksums.getChecksums(list, i, i2, str, certificateArr, iOnChecksumsReadyListener, new com.android.server.pm.ApkChecksums.Injector(producer, producer2, new com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda15(packageManagerServiceInjector), new com.android.server.pm.ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda27
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final java.lang.Object produce() {
                android.content.pm.PackageManagerInternal lambda$requestFileChecksums$3;
                lambda$requestFileChecksums$3 = com.android.server.pm.PackageManagerService.this.lambda$requestFileChecksums$3();
                return lambda$requestFileChecksums$3;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Context lambda$requestFileChecksums$1() {
        return this.mContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.Handler lambda$requestFileChecksums$2(android.os.Handler handler) {
        return handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.PackageManagerInternal lambda$requestFileChecksums$3() {
        return (android.content.pm.PackageManagerInternal) this.mInjector.getLocalService(android.content.pm.PackageManagerInternal.class);
    }

    void requestChecksumsInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, boolean z, final int i, final int i2, @android.annotation.Nullable java.util.List list, @android.annotation.NonNull final android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull final android.os.Handler handler) {
        final java.lang.String str2;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iOnChecksumsReadyListener);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(handler);
        android.content.pm.ApplicationInfo applicationInfoInternal = computer.getApplicationInfoInternal(str, 0L, android.os.Binder.getCallingUid(), i3);
        if (applicationInfoInternal == null) {
            throw new android.os.ParcelableException(new android.content.pm.PackageManager.NameNotFoundException(str));
        }
        android.content.pm.InstallSourceInfo installSourceInfo = computer.getInstallSourceInfo(str, i3);
        if (installSourceInfo != null) {
            java.lang.String initiatingPackageName = installSourceInfo.getInitiatingPackageName();
            if (com.android.server.pm.PackageManagerServiceUtils.isInstalledByAdb(initiatingPackageName)) {
                initiatingPackageName = installSourceInfo.getInstallingPackageName();
            }
            str2 = initiatingPackageName;
        } else {
            str2 = null;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.util.Pair.create(null, new java.io.File(applicationInfoInternal.sourceDir)));
        if (z && applicationInfoInternal.splitNames != null) {
            int length = applicationInfoInternal.splitNames.length;
            for (int i4 = 0; i4 < length; i4++) {
                arrayList.add(android.util.Pair.create(applicationInfoInternal.splitNames[i4], new java.io.File(applicationInfoInternal.splitSourceDirs[i4])));
            }
        }
        final java.security.cert.Certificate[] decodeCertificates = list != null ? decodeCertificates(list) : null;
        executor.execute(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda65
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageManagerService.this.lambda$requestChecksumsInternal$8(handler, arrayList, i, i2, str2, decodeCertificates, iOnChecksumsReadyListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestChecksumsInternal$8(final android.os.Handler handler, java.util.List list, int i, int i2, java.lang.String str, java.security.cert.Certificate[] certificateArr, android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        com.android.server.pm.ApkChecksums.Injector.Producer producer = new com.android.server.pm.ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda13
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final java.lang.Object produce() {
                android.content.Context lambda$requestChecksumsInternal$5;
                lambda$requestChecksumsInternal$5 = com.android.server.pm.PackageManagerService.this.lambda$requestChecksumsInternal$5();
                return lambda$requestChecksumsInternal$5;
            }
        };
        com.android.server.pm.ApkChecksums.Injector.Producer producer2 = new com.android.server.pm.ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda14
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final java.lang.Object produce() {
                android.os.Handler lambda$requestChecksumsInternal$6;
                lambda$requestChecksumsInternal$6 = com.android.server.pm.PackageManagerService.lambda$requestChecksumsInternal$6(handler);
                return lambda$requestChecksumsInternal$6;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector = this.mInjector;
        java.util.Objects.requireNonNull(packageManagerServiceInjector);
        com.android.server.pm.ApkChecksums.getChecksums(list, i, i2, str, certificateArr, iOnChecksumsReadyListener, new com.android.server.pm.ApkChecksums.Injector(producer, producer2, new com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda15(packageManagerServiceInjector), new com.android.server.pm.ApkChecksums.Injector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda16
            @Override // com.android.server.pm.ApkChecksums.Injector.Producer
            public final java.lang.Object produce() {
                android.content.pm.PackageManagerInternal lambda$requestChecksumsInternal$7;
                lambda$requestChecksumsInternal$7 = com.android.server.pm.PackageManagerService.this.lambda$requestChecksumsInternal$7();
                return lambda$requestChecksumsInternal$7;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.Context lambda$requestChecksumsInternal$5() {
        return this.mContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.Handler lambda$requestChecksumsInternal$6(android.os.Handler handler) {
        return handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.PackageManagerInternal lambda$requestChecksumsInternal$7() {
        return (android.content.pm.PackageManagerInternal) this.mInjector.getLocalService(android.content.pm.PackageManagerInternal.class);
    }

    @android.annotation.NonNull
    private static java.security.cert.Certificate[] decodeCertificates(@android.annotation.NonNull java.util.List list) {
        try {
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            java.security.cert.Certificate[] certificateArr = new java.security.cert.Certificate[list.size()];
            int size = list.size();
            for (int i = 0; i < size; i++) {
                certificateArr[i] = (java.security.cert.X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream((byte[]) list.get(i)));
            }
            return certificateArr;
        } catch (java.security.cert.CertificateException e) {
            throw android.util.ExceptionUtils.propagate(e);
        }
    }

    private static android.os.Bundle extrasForInstallResult(com.android.server.pm.InstallRequest installRequest) {
        android.os.Bundle bundle;
        switch (installRequest.getReturnCode()) {
            case -112:
                bundle = new android.os.Bundle();
                bundle.putString("android.content.pm.extra.FAILURE_EXISTING_PERMISSION", installRequest.getOrigPermission());
                bundle.putString("android.content.pm.extra.FAILURE_EXISTING_PACKAGE", installRequest.getOrigPackage());
                break;
            case 1:
                bundle = new android.os.Bundle();
                bundle.putBoolean("android.intent.extra.REPLACING", (installRequest.getRemovedInfo() == null || installRequest.getRemovedInfo().mRemovedPackage == null) ? false : true);
                break;
            default:
                bundle = null;
                break;
        }
        if (!installRequest.getWarnings().isEmpty()) {
            bundle.putStringArrayList("android.content.pm.extra.WARNINGS", installRequest.getWarnings());
        }
        return bundle;
    }

    android.content.pm.ArchivedPackageParcel getArchivedPackageInternal(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str);
        snapshotComputer().enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, true, "getArchivedPackage");
        android.content.pm.ArchivedPackageParcel archivedPackageParcel = new android.content.pm.ArchivedPackageParcel();
        archivedPackageParcel.packageName = str;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mSettings.getPackageLPr(str);
                if (packageLPr == null) {
                    resetPriorityAfterPackageManagerTracedLockedSection();
                    return null;
                }
                com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageLPr.getUserStateOrDefault(i);
                com.android.server.pm.pkg.ArchiveState archiveState = userStateOrDefault.getArchiveState();
                if (archiveState == null && !userStateOrDefault.isInstalled()) {
                    resetPriorityAfterPackageManagerTracedLockedSection();
                    return null;
                }
                archivedPackageParcel.signingDetails = packageLPr.getSigningDetails();
                long versionCode = packageLPr.getVersionCode();
                archivedPackageParcel.versionCodeMajor = (int) (versionCode >> 32);
                archivedPackageParcel.versionCode = (int) versionCode;
                archivedPackageParcel.targetSdkVersion = packageLPr.getTargetSdkVersion();
                archivedPackageParcel.defaultToDeviceProtectedStorage = java.lang.String.valueOf(packageLPr.isDefaultToDeviceProtectedStorage());
                archivedPackageParcel.requestLegacyExternalStorage = java.lang.String.valueOf(packageLPr.isRequestLegacyExternalStorage());
                archivedPackageParcel.userDataFragile = java.lang.String.valueOf(packageLPr.isUserDataFragile());
                resetPriorityAfterPackageManagerTracedLockedSection();
                try {
                    if (archiveState != null) {
                        archivedPackageParcel.archivedActivities = com.android.server.pm.PackageArchiver.createArchivedActivities(archiveState);
                    } else {
                        archivedPackageParcel.archivedActivities = com.android.server.pm.PackageArchiver.createArchivedActivities(this.mInstallerService.mPackageArchiver.getLauncherActivityInfos(str, i), ((android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class)).getLauncherLargeIconSize());
                    }
                    return archivedPackageParcel;
                } catch (java.lang.Exception e) {
                    throw new java.lang.IllegalArgumentException("Package does not have a main activity", e);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    void markPackageAsArchivedIfNeeded(com.android.server.pm.PackageSetting packageSetting, android.content.pm.ArchivedPackageParcel archivedPackageParcel, int[] iArr) {
        if (packageSetting == null || archivedPackageParcel == null || archivedPackageParcel.archivedActivities == null || iArr == null || iArr.length == 0) {
            return;
        }
        packageSetting.setPkg(null).setPendingRestore(true);
        for (int i : iArr) {
            packageSetting.modifyUserState(i).setInstalled(false);
        }
        java.lang.String responsibleInstallerPackage = com.android.server.pm.PackageArchiver.getResponsibleInstallerPackage(packageSetting);
        if (android.text.TextUtils.isEmpty(responsibleInstallerPackage)) {
            android.util.Slog.e(TAG, "Can't create archive state: responsible installer is empty");
            return;
        }
        for (int i2 : iArr) {
            com.android.server.pm.pkg.ArchiveState createArchiveState = this.mInstallerService.mPackageArchiver.createArchiveState(archivedPackageParcel, i2, responsibleInstallerPackage);
            if (createArchiveState != null) {
                packageSetting.modifyUserState(i2).setArchiveState(createArchiveState);
            }
        }
    }

    void scheduleWriteSettings() {
        invalidatePackageInfoCache();
        if (!this.mHandler.hasMessages(13)) {
            this.mHandler.sendEmptyMessageDelayed(13, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }
    }

    void scheduleWritePackageList(int i) {
        invalidatePackageInfoCache();
        if (!this.mHandler.hasMessages(19)) {
            android.os.Message obtainMessage = this.mHandler.obtainMessage(19);
            obtainMessage.arg1 = i;
            this.mHandler.sendMessageDelayed(obtainMessage, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }
    }

    void scheduleWritePackageRestrictions(android.os.UserHandle userHandle) {
        scheduleWritePackageRestrictions(userHandle == null ? -1 : userHandle.getIdentifier());
    }

    void scheduleWritePackageRestrictions(int i) {
        invalidatePackageInfoCache();
        if (i == -1) {
            synchronized (this.mDirtyUsers) {
                try {
                    for (int i2 : this.mUserManager.getUserIds()) {
                        this.mDirtyUsers.add(java.lang.Integer.valueOf(i2));
                    }
                } finally {
                }
            }
        } else {
            if (!this.mUserManager.exists(i)) {
                return;
            }
            synchronized (this.mDirtyUsers) {
                this.mDirtyUsers.add(java.lang.Integer.valueOf(i));
            }
        }
        if (!this.mBackgroundHandler.hasMessages(14)) {
            this.mBackgroundHandler.sendMessageDelayed(this.mBackgroundHandler.obtainMessage(14, this), com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }
    }

    void writePendingRestrictions() {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mBackgroundHandler.removeMessages(14);
                synchronized (this.mDirtyUsers) {
                    if (this.mDirtyUsers.isEmpty()) {
                        resetPriorityAfterPackageManagerTracedLockedSection();
                        return;
                    }
                    java.lang.Integer[] numArr = (java.lang.Integer[]) this.mDirtyUsers.toArray(new java.util.function.IntFunction() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68
                        @Override // java.util.function.IntFunction
                        public final java.lang.Object apply(int i) {
                            java.lang.Integer[] lambda$writePendingRestrictions$9;
                            lambda$writePendingRestrictions$9 = com.android.server.pm.PackageManagerService.lambda$writePendingRestrictions$9(i);
                            return lambda$writePendingRestrictions$9;
                        }
                    });
                    this.mDirtyUsers.clear();
                    resetPriorityAfterPackageManagerTracedLockedSection();
                    this.mSettings.writePackageRestrictions(numArr);
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer[] lambda$writePendingRestrictions$9(int i) {
        return new java.lang.Integer[i];
    }

    private boolean tryUnderLock(boolean z, long j, java.lang.Runnable runnable) {
        try {
            if (z) {
                this.mLock.lock();
            } else if (!this.mLock.tryLock(j, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                return false;
            }
            try {
                runnable.run();
                this.mLock.unlock();
                return true;
            } catch (java.lang.Throwable th) {
                this.mLock.unlock();
                throw th;
            }
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.e(TAG, "Failed to obtain mLock", e);
            return false;
        }
    }

    boolean tryWriteSettings(final boolean z) {
        return tryUnderLock(z, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageManagerService.this.lambda$tryWriteSettings$10(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryWriteSettings$10(boolean z) {
        this.mHandler.removeMessages(13);
        this.mBackgroundHandler.removeMessages(14);
        writeSettingsLPrTEMP(z);
        synchronized (this.mDirtyUsers) {
            this.mDirtyUsers.clear();
        }
    }

    boolean tryWritePackageList(final int i) {
        return tryUnderLock(false, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageManagerService.this.lambda$tryWritePackageList$11(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryWritePackageList$11(int i) {
        this.mHandler.removeMessages(19);
        this.mSettings.writePackageListLPr(i);
    }

    public static com.android.server.pm.PackageManagerService main(final android.content.Context context, final com.android.server.pm.Installer installer, @android.annotation.NonNull final com.android.server.pm.verify.domain.DomainVerificationService domainVerificationService, boolean z) {
        com.android.server.pm.PackageManagerServiceCompilerMapping.checkProperties();
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog("PackageManagerTiming", 262144L);
        timingsTraceAndSlog.traceBegin("create package manager");
        final com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = new com.android.server.pm.PackageManagerTracedLock();
        final java.lang.Object obj = new java.lang.Object();
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread("PackageManagerBg", 10, true);
        serviceThread.start();
        final android.os.Handler handler = new android.os.Handler(serviceThread.getLooper(), BACKGROUND_HANDLER_CALLBACK);
        com.android.server.pm.PackageAbiHelperImpl packageAbiHelperImpl = new com.android.server.pm.PackageAbiHelperImpl();
        java.util.List<com.android.server.pm.ScanPartition> list = SYSTEM_PARTITIONS;
        com.android.server.pm.PackageManagerServiceInjector.Producer producer = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda29
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.resolution.ComponentResolver lambda$main$12;
                lambda$main$12 = com.android.server.pm.PackageManagerService.lambda$main$12(packageManagerServiceInjector, packageManagerService);
                return lambda$main$12;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer2 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda40
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.permission.PermissionManagerServiceInternal lambda$main$13;
                lambda$main$13 = com.android.server.pm.PackageManagerService.lambda$main$13(context, packageManagerServiceInjector, packageManagerService);
                return lambda$main$13;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer3 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda51
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.UserManagerService lambda$main$14;
                lambda$main$14 = com.android.server.pm.PackageManagerService.lambda$main$14(context, installer, obj, packageManagerTracedLock, packageManagerServiceInjector, packageManagerService);
                return lambda$main$14;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer4 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda55
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.Settings lambda$main$15;
                lambda$main$15 = com.android.server.pm.PackageManagerService.lambda$main$15(com.android.server.pm.verify.domain.DomainVerificationService.this, handler, packageManagerTracedLock, packageManagerServiceInjector, packageManagerService);
                return lambda$main$15;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer5 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda56
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.AppsFilterImpl lambda$main$16;
                lambda$main$16 = com.android.server.pm.PackageManagerService.lambda$main$16(packageManagerServiceInjector, packageManagerService);
                return lambda$main$16;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer6 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda57
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.compat.PlatformCompat lambda$main$17;
                lambda$main$17 = com.android.server.pm.PackageManagerService.lambda$main$17(packageManagerServiceInjector, packageManagerService);
                return lambda$main$17;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer7 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda58
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.SystemConfig lambda$main$18;
                lambda$main$18 = com.android.server.pm.PackageManagerService.lambda$main$18(packageManagerServiceInjector, packageManagerService);
                return lambda$main$18;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer8 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda59
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.PackageDexOptimizer lambda$main$19;
                lambda$main$19 = com.android.server.pm.PackageManagerService.lambda$main$19(packageManagerServiceInjector, packageManagerService);
                return lambda$main$19;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer9 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda60
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.dex.DexManager lambda$main$20;
                lambda$main$20 = com.android.server.pm.PackageManagerService.lambda$main$20(packageManagerServiceInjector, packageManagerService);
                return lambda$main$20;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer10 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda61
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.dex.DynamicCodeLogger lambda$main$21;
                lambda$main$21 = com.android.server.pm.PackageManagerService.lambda$main$21(packageManagerServiceInjector, packageManagerService);
                return lambda$main$21;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer11 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda30
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.dex.ArtManagerService lambda$main$22;
                lambda$main$22 = com.android.server.pm.PackageManagerService.lambda$main$22(packageManagerServiceInjector, packageManagerService);
                return lambda$main$22;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer12 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda31
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.ApexManager lambda$main$23;
                lambda$main$23 = com.android.server.pm.PackageManagerService.lambda$main$23(packageManagerServiceInjector, packageManagerService);
                return lambda$main$23;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer13 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda32
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                android.os.incremental.IncrementalManager lambda$main$24;
                lambda$main$24 = com.android.server.pm.PackageManagerService.lambda$main$24(packageManagerServiceInjector, packageManagerService);
                return lambda$main$24;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer14 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda33
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.DefaultAppProvider lambda$main$27;
                lambda$main$27 = com.android.server.pm.PackageManagerService.lambda$main$27(context, packageManagerServiceInjector, packageManagerService);
                return lambda$main$27;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer15 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda34
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                android.util.DisplayMetrics lambda$main$28;
                lambda$main$28 = com.android.server.pm.PackageManagerService.lambda$main$28(packageManagerServiceInjector, packageManagerService);
                return lambda$main$28;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer16 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda35
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.internal.pm.parsing.PackageParser2 lambda$main$29;
                lambda$main$29 = com.android.server.pm.PackageManagerService.lambda$main$29(packageManagerServiceInjector, packageManagerService);
                return lambda$main$29;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer17 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda36
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.internal.pm.parsing.PackageParser2 lambda$main$30;
                lambda$main$30 = com.android.server.pm.PackageManagerService.lambda$main$30(packageManagerServiceInjector, packageManagerService);
                return lambda$main$30;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer18 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda37
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.internal.pm.parsing.PackageParser2 lambda$main$31;
                lambda$main$31 = com.android.server.pm.PackageManagerService.lambda$main$31(packageManagerServiceInjector, packageManagerService);
                return lambda$main$31;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer19 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda38
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.PackageInstallerService lambda$main$32;
                lambda$main$32 = com.android.server.pm.PackageManagerService.lambda$main$32(packageManagerServiceInjector, packageManagerService);
                return lambda$main$32;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.ProducerWithArgument producerWithArgument = new com.android.server.pm.PackageManagerServiceInjector.ProducerWithArgument() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda39
            @Override // com.android.server.pm.PackageManagerServiceInjector.ProducerWithArgument
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService, java.lang.Object obj2) {
                com.android.server.pm.InstantAppResolverConnection lambda$main$33;
                lambda$main$33 = com.android.server.pm.PackageManagerService.lambda$main$33(packageManagerServiceInjector, packageManagerService, (android.content.ComponentName) obj2);
                return lambda$main$33;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer20 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda41
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.ModuleInfoProvider lambda$main$34;
                lambda$main$34 = com.android.server.pm.PackageManagerService.lambda$main$34(packageManagerServiceInjector, packageManagerService);
                return lambda$main$34;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer21 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda42
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.permission.LegacyPermissionManagerInternal lambda$main$35;
                lambda$main$35 = com.android.server.pm.PackageManagerService.lambda$main$35(packageManagerServiceInjector, packageManagerService);
                return lambda$main$35;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer22 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda43
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.verify.domain.DomainVerificationManagerInternal lambda$main$36;
                lambda$main$36 = com.android.server.pm.PackageManagerService.lambda$main$36(com.android.server.pm.verify.domain.DomainVerificationService.this, packageManagerServiceInjector, packageManagerService);
                return lambda$main$36;
            }
        };
        com.android.server.pm.PackageManagerServiceInjector.Producer producer23 = new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda44
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
                android.os.Handler lambda$main$37;
                lambda$main$37 = com.android.server.pm.PackageManagerService.lambda$main$37(packageManagerServiceInjector, packageManagerService);
                return lambda$main$37;
            }
        };
        com.android.server.pm.PackageManagerService.DefaultSystemWrapper defaultSystemWrapper = new com.android.server.pm.PackageManagerService.DefaultSystemWrapper();
        com.android.server.pm.PackageManagerServiceInjector.ServiceProducer serviceProducer = new com.android.server.pm.PackageManagerServiceInjector.ServiceProducer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda45
            @Override // com.android.server.pm.PackageManagerServiceInjector.ServiceProducer
            public final java.lang.Object produce(java.lang.Class cls) {
                return com.android.server.LocalServices.getService(cls);
            }
        };
        java.util.Objects.requireNonNull(context);
        com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector = new com.android.server.pm.PackageManagerServiceInjector(context, packageManagerTracedLock, installer, obj, packageAbiHelperImpl, handler, list, producer, producer2, producer3, producer4, producer5, producer6, producer7, producer8, producer9, producer10, producer11, producer12, producer13, producer14, producer15, producer16, producer17, producer18, producer19, producerWithArgument, producer20, producer21, producer22, producer23, defaultSystemWrapper, serviceProducer, new com.android.server.pm.PackageManagerServiceInjector.ServiceProducer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda46
            @Override // com.android.server.pm.PackageManagerServiceInjector.ServiceProducer
            public final java.lang.Object produce(java.lang.Class cls) {
                return context.getSystemService(cls);
            }
        }, new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda47
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector2, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.BackgroundDexOptService lambda$main$38;
                lambda$main$38 = com.android.server.pm.PackageManagerService.lambda$main$38(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$38;
            }
        }, new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda48
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector2, com.android.server.pm.PackageManagerService packageManagerService) {
                android.app.backup.IBackupManager lambda$main$39;
                lambda$main$39 = com.android.server.pm.PackageManagerService.lambda$main$39(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$39;
            }
        }, new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda49
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector2, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.SharedLibrariesImpl lambda$main$40;
                lambda$main$40 = com.android.server.pm.PackageManagerService.lambda$main$40(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$40;
            }
        }, new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda50
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector2, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.CrossProfileIntentFilterHelper lambda$main$41;
                lambda$main$41 = com.android.server.pm.PackageManagerService.lambda$main$41(context, packageManagerServiceInjector2, packageManagerService);
                return lambda$main$41;
            }
        }, new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda52
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector2, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.UpdateOwnershipHelper lambda$main$42;
                lambda$main$42 = com.android.server.pm.PackageManagerService.lambda$main$42(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$42;
            }
        }, new com.android.server.pm.PackageManagerServiceInjector.Producer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda53
            @Override // com.android.server.pm.PackageManagerServiceInjector.Producer
            public final java.lang.Object produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector2, com.android.server.pm.PackageManagerService packageManagerService) {
                com.android.server.pm.PackageMonitorCallbackHelper lambda$main$43;
                lambda$main$43 = com.android.server.pm.PackageManagerService.lambda$main$43(packageManagerServiceInjector2, packageManagerService);
                return lambda$main$43;
            }
        });
        final com.android.server.pm.PackageManagerService packageManagerService = new com.android.server.pm.PackageManagerService(packageManagerServiceInjector, z, android.content.pm.PackagePartitions.FINGERPRINT, android.os.Build.IS_ENG, android.os.Build.IS_USERDEBUG, android.os.Build.VERSION.SDK_INT, android.os.Build.VERSION.INCREMENTAL);
        timingsTraceAndSlog.traceEnd();
        com.android.server.compat.CompatChange.ChangeListener changeListener = new com.android.server.compat.CompatChange.ChangeListener() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda54
            @Override // com.android.server.compat.CompatChange.ChangeListener
            public final void onCompatChange(java.lang.String str) {
                com.android.server.pm.PackageManagerService.lambda$main$45(com.android.server.pm.PackageManagerService.this, str);
            }
        };
        packageManagerServiceInjector.getCompatibility().registerListener(143539591L, changeListener);
        packageManagerServiceInjector.getCompatibility().registerListener(168782947L, changeListener);
        packageManagerService.installAllowlistedSystemPackages();
        java.util.Objects.requireNonNull(packageManagerService);
        android.os.ServiceManager.addService("package", packageManagerService.new IPackageManagerImpl());
        android.os.ServiceManager.addService("package_native", new com.android.server.pm.PackageManagerNative(packageManagerService));
        return packageManagerService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.resolution.ComponentResolver lambda$main$12(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.resolution.ComponentResolver(packageManagerServiceInjector.getUserManagerService(), packageManagerService.mUserNeedsBadging);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.permission.PermissionManagerServiceInternal lambda$main$13(android.content.Context context, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return com.android.server.pm.permission.PermissionManagerService.create(context, packageManagerServiceInjector.getSystemConfig().getAvailableFeatures());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.UserManagerService lambda$main$14(android.content.Context context, com.android.server.pm.Installer installer, java.lang.Object obj, com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.UserManagerService(context, packageManagerService, new com.android.server.pm.UserDataPreparer(installer, obj, context), packageManagerTracedLock);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.Settings lambda$main$15(com.android.server.pm.verify.domain.DomainVerificationService domainVerificationService, android.os.Handler handler, com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.Settings(android.os.Environment.getDataDirectory(), com.android.permission.persistence.RuntimePermissionsPersistence.createInstance(), packageManagerServiceInjector.getPermissionManagerServiceInternal(), domainVerificationService, handler, packageManagerTracedLock);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.AppsFilterImpl lambda$main$16(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return com.android.server.pm.AppsFilterImpl.create(packageManagerServiceInjector, (android.content.pm.PackageManagerInternal) packageManagerServiceInjector.getLocalService(android.content.pm.PackageManagerInternal.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.compat.PlatformCompat lambda$main$17(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return (com.android.server.compat.PlatformCompat) android.os.ServiceManager.getService("platform_compat");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.SystemConfig lambda$main$18(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return com.android.server.SystemConfig.getInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.PackageDexOptimizer lambda$main$19(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.PackageDexOptimizer(packageManagerServiceInjector.getInstaller(), packageManagerServiceInjector.getInstallLock(), packageManagerServiceInjector.getContext(), "*dexopt*");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.dex.DexManager lambda$main$20(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.dex.DexManager(packageManagerServiceInjector.getContext(), packageManagerServiceInjector.getPackageDexOptimizer(), packageManagerServiceInjector.getInstaller(), packageManagerServiceInjector.getInstallLock(), packageManagerServiceInjector.getDynamicCodeLogger());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.dex.DynamicCodeLogger lambda$main$21(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.dex.DynamicCodeLogger(packageManagerServiceInjector.getInstaller());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.dex.ArtManagerService lambda$main$22(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.dex.ArtManagerService(packageManagerServiceInjector.getContext(), packageManagerServiceInjector.getInstaller(), packageManagerServiceInjector.getInstallLock());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.ApexManager lambda$main$23(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return com.android.server.pm.ApexManager.getInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.incremental.IncrementalManager lambda$main$24(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return (android.os.incremental.IncrementalManager) packageManagerServiceInjector.getContext().getSystemService("incremental");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.app.role.RoleManager lambda$main$25(android.content.Context context) {
        return (android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.DefaultAppProvider lambda$main$27(final android.content.Context context, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.DefaultAppProvider(new java.util.function.Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda62
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.app.role.RoleManager lambda$main$25;
                lambda$main$25 = com.android.server.pm.PackageManagerService.lambda$main$25(context);
                return lambda$main$25;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda63
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.pm.UserManagerInternal lambda$main$26;
                lambda$main$26 = com.android.server.pm.PackageManagerService.lambda$main$26();
                return lambda$main$26;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.UserManagerInternal lambda$main$26() {
        return (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.util.DisplayMetrics lambda$main$28(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new android.util.DisplayMetrics();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.internal.pm.parsing.PackageParser2 lambda$main$29(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.internal.pm.parsing.PackageParser2(packageManagerService.mSeparateProcesses, packageManagerServiceInjector.getDisplayMetrics(), new com.android.server.pm.parsing.PackageCacher(packageManagerService.mCacheDir, packageManagerService.mPackageParserCallback), packageManagerService.mPackageParserCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.internal.pm.parsing.PackageParser2 lambda$main$30(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.internal.pm.parsing.PackageParser2(packageManagerService.mSeparateProcesses, packageManagerServiceInjector.getDisplayMetrics(), (com.android.internal.pm.parsing.IPackageCacher) null, packageManagerService.mPackageParserCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.internal.pm.parsing.PackageParser2 lambda$main$31(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.internal.pm.parsing.PackageParser2(packageManagerService.mSeparateProcesses, packageManagerServiceInjector.getDisplayMetrics(), (com.android.internal.pm.parsing.IPackageCacher) null, packageManagerService.mPackageParserCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.PackageInstallerService lambda$main$32(final com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        android.content.Context context = packageManagerServiceInjector.getContext();
        java.util.Objects.requireNonNull(packageManagerServiceInjector);
        return new com.android.server.pm.PackageInstallerService(context, packageManagerService, new java.util.function.Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda23
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.server.pm.PackageManagerServiceInjector.this.getScanningPackageParser();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.InstantAppResolverConnection lambda$main$33(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService, android.content.ComponentName componentName) {
        return new com.android.server.pm.InstantAppResolverConnection(packageManagerServiceInjector.getContext(), componentName, "android.intent.action.RESOLVE_INSTANT_APP_PACKAGE");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.ModuleInfoProvider lambda$main$34(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.ModuleInfoProvider(packageManagerServiceInjector.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.permission.LegacyPermissionManagerInternal lambda$main$35(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return com.android.server.pm.permission.LegacyPermissionManagerService.create(packageManagerServiceInjector.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.verify.domain.DomainVerificationManagerInternal lambda$main$36(com.android.server.pm.verify.domain.DomainVerificationService domainVerificationService, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return domainVerificationService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.Handler lambda$main$37(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, 0, true);
        serviceThread.start();
        return new com.android.server.pm.PackageHandler(serviceThread.getLooper(), packageManagerService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.BackgroundDexOptService lambda$main$38(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        if (com.android.server.pm.DexOptHelper.useArtService()) {
            return null;
        }
        try {
            return new com.android.server.pm.BackgroundDexOptService(packageManagerServiceInjector.getContext(), packageManagerServiceInjector.getDexManager(), packageManagerService);
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.app.backup.IBackupManager lambda$main$39(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return android.app.backup.IBackupManager.Stub.asInterface(android.os.ServiceManager.getService(com.android.server.am.HostingRecord.HOSTING_TYPE_BACKUP));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.SharedLibrariesImpl lambda$main$40(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.SharedLibrariesImpl(packageManagerService, packageManagerServiceInjector);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.CrossProfileIntentFilterHelper lambda$main$41(android.content.Context context, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.CrossProfileIntentFilterHelper(packageManagerServiceInjector.getSettings(), packageManagerServiceInjector.getUserManagerService(), packageManagerServiceInjector.getLock(), packageManagerServiceInjector.getUserManagerInternal(), context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.UpdateOwnershipHelper lambda$main$42(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.UpdateOwnershipHelper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.pm.PackageMonitorCallbackHelper lambda$main$43(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
        return new com.android.server.pm.PackageMonitorCallbackHelper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$main$45(com.android.server.pm.PackageManagerService packageManagerService, java.lang.String str) {
        synchronized (packageManagerService.mInstallLock) {
            try {
                com.android.server.pm.Computer snapshotComputer = packageManagerService.snapshotComputer();
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                if (packageStateInternal == null) {
                    android.util.Slog.e(TAG, "Failed to find package setting " + str);
                    return;
                }
                com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal.getPkg();
                com.android.server.pm.pkg.SharedUserApi sharedUser = snapshotComputer.getSharedUser(packageStateInternal.getSharedUserAppId());
                java.lang.String seInfo = packageStateInternal.getSeInfo();
                if (pkg == null) {
                    android.util.Slog.e(TAG, "Failed to find package " + str);
                    return;
                }
                final java.lang.String seInfo2 = com.android.server.pm.SELinuxMMAC.getSeInfo(packageStateInternal, pkg, sharedUser, packageManagerService.mInjector.getCompatibility());
                if (!seInfo2.equals(seInfo)) {
                    android.util.Slog.i(TAG, "Updating seInfo for package " + str + " from: " + seInfo + " to: " + seInfo2);
                    packageManagerService.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda67
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setOverrideSeInfo(seInfo2);
                        }
                    });
                    packageManagerService.mAppDataHelper.prepareAppDataAfterInstallLIF(pkg);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void installAllowlistedSystemPackages() {
        if (this.mUserManager.installWhitelistedSystemPackages(isFirstBoot(), isDeviceUpgrading(), this.mExistingPackages)) {
            scheduleWritePackageRestrictions(-1);
            scheduleWriteSettings();
        }
    }

    private void registerObservers(boolean z) {
        if (this.mPackages != null) {
            this.mPackages.registerObserver(this.mWatcher);
        }
        if (this.mSharedLibraries != null) {
            this.mSharedLibraries.registerObserver(this.mWatcher);
        }
        if (this.mInstrumentation != null) {
            this.mInstrumentation.registerObserver(this.mWatcher);
        }
        if (this.mWebInstantAppsDisabled != null) {
            this.mWebInstantAppsDisabled.registerObserver(this.mWatcher);
        }
        if (this.mAppsFilter != null) {
            this.mAppsFilter.registerObserver(this.mWatcher);
        }
        if (this.mInstantAppRegistry != null) {
            this.mInstantAppRegistry.registerObserver(this.mWatcher);
        }
        if (this.mSettings != null) {
            this.mSettings.registerObserver(this.mWatcher);
        }
        if (this.mIsolatedOwners != null) {
            this.mIsolatedOwners.registerObserver(this.mWatcher);
        }
        if (this.mComponentResolver != null) {
            this.mComponentResolver.registerObserver(this.mWatcher);
        }
        if (this.mFrozenPackages != null) {
            this.mFrozenPackages.registerObserver(this.mWatcher);
        }
        if (z) {
            com.android.server.utils.Watchable.verifyWatchedAttributes(this, this.mWatcher, (this.mIsEngBuild || this.mIsUserDebugBuild) ? false : true);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public PackageManagerService(@android.annotation.NonNull com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, @android.annotation.NonNull com.android.server.pm.PackageManagerServiceTestParams packageManagerServiceTestParams) {
        this.mOverlayPathsLock = new com.android.server.pm.PackageManagerTracedLock();
        this.mPackageStateMutator = new com.android.server.pm.pkg.mutate.PackageStateMutator(new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.pm.PackageManagerService.this.getPackageSettingForMutation((java.lang.String) obj);
            }
        }, new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.pm.PackageManagerService.this.getDisabledPackageSettingForMutation((java.lang.String) obj);
            }
        });
        this.mPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPackages, this.mPackages, "PackageManagerService.mPackages");
        this.mIsolatedOwners = new com.android.server.utils.WatchedSparseIntArray();
        this.mIsolatedOwnersSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mIsolatedOwners, this.mIsolatedOwners, "PackageManagerService.mIsolatedOwners");
        this.mExistingPackages = null;
        this.mFrozenPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mFrozenPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mFrozenPackages, this.mFrozenPackages, "PackageManagerService.mFrozenPackages");
        this.mPackageObserverHelper = new com.android.server.pm.PackageObserverHelper();
        this.mInstrumentation = new com.android.server.utils.WatchedArrayMap<>();
        this.mInstrumentationSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mInstrumentation, this.mInstrumentation, "PackageManagerService.mInstrumentation");
        this.mTransferredPackages = new android.util.ArraySet<>();
        this.mProtectedBroadcasts = new android.util.ArraySet<>();
        this.mPendingVerification = new android.util.SparseArray<>();
        this.mPendingEnableRollback = new android.util.SparseArray<>();
        this.mNextMoveId = new java.util.concurrent.atomic.AtomicInteger();
        this.mPendingVerificationToken = 0;
        this.mPendingEnableRollbackToken = 0;
        this.mWebInstantAppsDisabled = new com.android.server.utils.WatchedSparseBooleanArray();
        this.mResolveActivity = new android.content.pm.ActivityInfo();
        this.mResolveInfo = new android.content.pm.ResolveInfo();
        this.mPlatformPackageOverlayPaths = null;
        this.mPlatformPackageOverlayResourceDirs = null;
        this.mReplacedResolverPackageOverlayPaths = null;
        this.mReplacedResolverPackageOverlayResourceDirs = null;
        this.mResolverReplaced = false;
        this.mInstantAppInstallerInfo = new android.content.pm.ResolveInfo();
        this.mNoKillInstallObservers = java.util.Collections.synchronizedMap(new java.util.HashMap());
        this.mPendingKillInstallObservers = java.util.Collections.synchronizedMap(new java.util.HashMap());
        this.mKeepUninstalledPackages = new android.util.ArraySet<>();
        this.mDevicePolicyManager = null;
        this.mPackageProperty = new com.android.server.pm.PackageProperty();
        this.mDirtyUsers = new android.util.ArraySet<>();
        this.mRunningInstalls = new android.util.SparseArray<>();
        this.mNextInstallToken = 1;
        this.mPackageUsage = new com.android.server.pm.PackageUsage();
        this.mCompilerStats = new com.android.server.pm.CompilerStats();
        this.mWatcher = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.PackageManagerService.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.PackageManagerService.onChange(watchable);
            }
        };
        this.mSnapshotLock = new java.lang.Object();
        this.mInjector = packageManagerServiceInjector;
        this.mInjector.bootstrap(this);
        this.mAppsFilter = packageManagerServiceInjector.getAppsFilter();
        this.mComponentResolver = packageManagerServiceInjector.getComponentResolver();
        this.mContext = packageManagerServiceInjector.getContext();
        this.mInstaller = packageManagerServiceInjector.getInstaller();
        this.mInstallLock = packageManagerServiceInjector.getInstallLock();
        this.mLock = packageManagerServiceInjector.getLock();
        this.mPackageStateWriteLock = this.mLock;
        this.mPermissionManager = packageManagerServiceInjector.getPermissionManagerServiceInternal();
        this.mSettings = packageManagerServiceInjector.getSettings();
        this.mUserManager = packageManagerServiceInjector.getUserManagerService();
        this.mUserNeedsBadging = new com.android.server.pm.UserNeedsBadgingCache(this.mUserManager);
        this.mDomainVerificationManager = packageManagerServiceInjector.getDomainVerificationManagerInternal();
        this.mHandler = packageManagerServiceInjector.getHandler();
        this.mBackgroundHandler = packageManagerServiceInjector.getBackgroundHandler();
        this.mSharedLibraries = packageManagerServiceInjector.getSharedLibrariesImpl();
        this.mApexManager = packageManagerServiceTestParams.apexManager;
        this.mArtManagerService = packageManagerServiceTestParams.artManagerService;
        this.mAvailableFeatures = packageManagerServiceTestParams.availableFeatures;
        this.mBackgroundDexOptService = packageManagerServiceTestParams.backgroundDexOptService;
        this.mDefParseFlags = packageManagerServiceTestParams.defParseFlags;
        this.mDefaultAppProvider = packageManagerServiceTestParams.defaultAppProvider;
        this.mLegacyPermissionManager = packageManagerServiceTestParams.legacyPermissionManagerInternal;
        this.mDexManager = packageManagerServiceTestParams.dexManager;
        this.mDynamicCodeLogger = packageManagerServiceTestParams.dynamicCodeLogger;
        this.mFactoryTest = packageManagerServiceTestParams.factoryTest;
        this.mIncrementalManager = packageManagerServiceTestParams.incrementalManager;
        this.mInstallerService = packageManagerServiceTestParams.installerService;
        this.mInstantAppRegistry = packageManagerServiceTestParams.instantAppRegistry;
        this.mChangedPackagesTracker = packageManagerServiceTestParams.changedPackagesTracker;
        this.mInstantAppResolverConnection = packageManagerServiceTestParams.instantAppResolverConnection;
        this.mInstantAppResolverSettingsComponent = packageManagerServiceTestParams.instantAppResolverSettingsComponent;
        this.mIsPreNMR1Upgrade = packageManagerServiceTestParams.isPreNmr1Upgrade;
        this.mIsPreQUpgrade = packageManagerServiceTestParams.isPreQupgrade;
        this.mPriorSdkVersion = packageManagerServiceTestParams.priorSdkVersion;
        this.mIsUpgrade = packageManagerServiceTestParams.isUpgrade;
        this.mMetrics = packageManagerServiceTestParams.Metrics;
        this.mModuleInfoProvider = packageManagerServiceTestParams.moduleInfoProvider;
        this.mMoveCallbacks = packageManagerServiceTestParams.moveCallbacks;
        this.mOverlayConfig = packageManagerServiceTestParams.overlayConfig;
        this.mPackageDexOptimizer = packageManagerServiceTestParams.packageDexOptimizer;
        this.mPackageParserCallback = packageManagerServiceTestParams.packageParserCallback;
        this.mPendingBroadcasts = packageManagerServiceTestParams.pendingPackageBroadcasts;
        this.mTestUtilityService = packageManagerServiceTestParams.testUtilityService;
        this.mProcessLoggingHandler = packageManagerServiceTestParams.processLoggingHandler;
        this.mProtectedPackages = packageManagerServiceTestParams.protectedPackages;
        this.mSeparateProcesses = packageManagerServiceTestParams.separateProcesses;
        this.mRequiredVerifierPackages = packageManagerServiceTestParams.requiredVerifierPackages;
        this.mRequiredInstallerPackage = packageManagerServiceTestParams.requiredInstallerPackage;
        this.mRequiredUninstallerPackage = packageManagerServiceTestParams.requiredUninstallerPackage;
        this.mRequiredPermissionControllerPackage = packageManagerServiceTestParams.requiredPermissionControllerPackage;
        this.mSetupWizardPackage = packageManagerServiceTestParams.setupWizardPackage;
        this.mStorageManagerPackage = packageManagerServiceTestParams.storageManagerPackage;
        this.mDefaultTextClassifierPackage = packageManagerServiceTestParams.defaultTextClassifierPackage;
        this.mSystemTextClassifierPackageName = packageManagerServiceTestParams.systemTextClassifierPackage;
        this.mRetailDemoPackage = packageManagerServiceTestParams.retailDemoPackage;
        this.mRecentsPackage = packageManagerServiceTestParams.recentsPackage;
        this.mAmbientContextDetectionPackage = packageManagerServiceTestParams.ambientContextDetectionPackage;
        this.mWearableSensingPackage = packageManagerServiceTestParams.wearableSensingPackage;
        this.mConfiguratorPackage = packageManagerServiceTestParams.configuratorPackage;
        this.mAppPredictionServicePackage = packageManagerServiceTestParams.appPredictionServicePackage;
        this.mIncidentReportApproverPackage = packageManagerServiceTestParams.incidentReportApproverPackage;
        this.mServicesExtensionPackageName = packageManagerServiceTestParams.servicesExtensionPackageName;
        this.mSharedSystemSharedLibraryPackageName = packageManagerServiceTestParams.sharedSystemSharedLibraryPackageName;
        this.mOverlayConfigSignaturePackage = packageManagerServiceTestParams.overlayConfigSignaturePackage;
        this.mResolveComponentName = packageManagerServiceTestParams.resolveComponentName;
        this.mRequiredSdkSandboxPackage = packageManagerServiceTestParams.requiredSdkSandboxPackage;
        this.mInitialNonStoppedSystemPackages = packageManagerServiceTestParams.initialNonStoppedSystemPackages;
        this.mShouldStopSystemPackagesByDefault = packageManagerServiceTestParams.shouldStopSystemPackagesByDefault;
        this.mLiveComputer = createLiveComputer();
        this.mSnapshotStatistics = null;
        this.mPackages.putAll(packageManagerServiceTestParams.packages);
        this.mFreeStorageHelper = packageManagerServiceTestParams.freeStorageHelper;
        this.mSdkVersion = packageManagerServiceTestParams.sdkVersion;
        this.mAppInstallDir = packageManagerServiceTestParams.appInstallDir;
        this.mIsEngBuild = packageManagerServiceTestParams.isEngBuild;
        this.mIsUserDebugBuild = packageManagerServiceTestParams.isUserDebugBuild;
        this.mIncrementalVersion = packageManagerServiceTestParams.incrementalVersion;
        this.mDomainVerificationConnection = new com.android.server.pm.DomainVerificationConnection(this);
        this.mBroadcastHelper = packageManagerServiceTestParams.broadcastHelper;
        this.mAppDataHelper = packageManagerServiceTestParams.appDataHelper;
        this.mInstallPackageHelper = packageManagerServiceTestParams.installPackageHelper;
        this.mRemovePackageHelper = packageManagerServiceTestParams.removePackageHelper;
        this.mInitAppsHelper = packageManagerServiceTestParams.initAndSystemPackageHelper;
        this.mDeletePackageHelper = packageManagerServiceTestParams.deletePackageHelper;
        this.mPreferredActivityHelper = packageManagerServiceTestParams.preferredActivityHelper;
        this.mResolveIntentHelper = packageManagerServiceTestParams.resolveIntentHelper;
        this.mDexOptHelper = packageManagerServiceTestParams.dexOptHelper;
        this.mSuspendPackageHelper = packageManagerServiceTestParams.suspendPackageHelper;
        this.mDistractingPackageHelper = packageManagerServiceTestParams.distractingPackageHelper;
        this.mSharedLibraries.setDeletePackageHelper(this.mDeletePackageHelper);
        this.mStorageEventHelper = packageManagerServiceTestParams.storageEventHelper;
        this.mPackageMonitorCallbackHelper = packageManagerServiceTestParams.packageMonitorCallbackHelper;
        registerObservers(false);
        invalidatePackageInfoCache();
    }

    public PackageManagerService(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, boolean z, java.lang.String str, boolean z2, boolean z3, int i, java.lang.String str2) {
        java.lang.String str3;
        java.util.Iterator<com.android.server.pm.SharedUserSetting> it;
        java.util.List<java.lang.String> list;
        this.mOverlayPathsLock = new com.android.server.pm.PackageManagerTracedLock();
        this.mPackageStateMutator = new com.android.server.pm.pkg.mutate.PackageStateMutator(new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.pm.PackageManagerService.this.getPackageSettingForMutation((java.lang.String) obj);
            }
        }, new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.pm.PackageManagerService.this.getDisabledPackageSettingForMutation((java.lang.String) obj);
            }
        });
        this.mPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPackages, this.mPackages, "PackageManagerService.mPackages");
        this.mIsolatedOwners = new com.android.server.utils.WatchedSparseIntArray();
        this.mIsolatedOwnersSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mIsolatedOwners, this.mIsolatedOwners, "PackageManagerService.mIsolatedOwners");
        this.mExistingPackages = null;
        this.mFrozenPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mFrozenPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mFrozenPackages, this.mFrozenPackages, "PackageManagerService.mFrozenPackages");
        this.mPackageObserverHelper = new com.android.server.pm.PackageObserverHelper();
        this.mInstrumentation = new com.android.server.utils.WatchedArrayMap<>();
        this.mInstrumentationSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mInstrumentation, this.mInstrumentation, "PackageManagerService.mInstrumentation");
        this.mTransferredPackages = new android.util.ArraySet<>();
        this.mProtectedBroadcasts = new android.util.ArraySet<>();
        this.mPendingVerification = new android.util.SparseArray<>();
        this.mPendingEnableRollback = new android.util.SparseArray<>();
        this.mNextMoveId = new java.util.concurrent.atomic.AtomicInteger();
        int i2 = 0;
        this.mPendingVerificationToken = 0;
        this.mPendingEnableRollbackToken = 0;
        this.mWebInstantAppsDisabled = new com.android.server.utils.WatchedSparseBooleanArray();
        this.mResolveActivity = new android.content.pm.ActivityInfo();
        this.mResolveInfo = new android.content.pm.ResolveInfo();
        this.mPlatformPackageOverlayPaths = null;
        this.mPlatformPackageOverlayResourceDirs = null;
        this.mReplacedResolverPackageOverlayPaths = null;
        this.mReplacedResolverPackageOverlayResourceDirs = null;
        this.mResolverReplaced = false;
        this.mInstantAppInstallerInfo = new android.content.pm.ResolveInfo();
        this.mNoKillInstallObservers = java.util.Collections.synchronizedMap(new java.util.HashMap());
        this.mPendingKillInstallObservers = java.util.Collections.synchronizedMap(new java.util.HashMap());
        this.mKeepUninstalledPackages = new android.util.ArraySet<>();
        this.mDevicePolicyManager = null;
        this.mPackageProperty = new com.android.server.pm.PackageProperty();
        this.mDirtyUsers = new android.util.ArraySet<>();
        this.mRunningInstalls = new android.util.SparseArray<>();
        this.mNextInstallToken = 1;
        this.mPackageUsage = new com.android.server.pm.PackageUsage();
        this.mCompilerStats = new com.android.server.pm.CompilerStats();
        this.mWatcher = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.PackageManagerService.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.PackageManagerService.onChange(watchable);
            }
        };
        this.mSnapshotLock = new java.lang.Object();
        this.mIsEngBuild = z2;
        this.mIsUserDebugBuild = z3;
        this.mSdkVersion = i;
        this.mIncrementalVersion = str2;
        this.mInjector = packageManagerServiceInjector;
        this.mInjector.getSystemWrapper().disablePackageCaches();
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog("PackageManagerTiming", 262144L);
        this.mPendingBroadcasts = new com.android.server.pm.PendingPackageBroadcasts();
        this.mInjector.bootstrap(this);
        this.mLock = packageManagerServiceInjector.getLock();
        this.mPackageStateWriteLock = this.mLock;
        this.mInstallLock = packageManagerServiceInjector.getInstallLock();
        com.android.server.LockGuard.installLock(this.mLock, 3);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BOOT_PROGRESS_PMS_START, android.os.SystemClock.uptimeMillis());
        this.mContext = packageManagerServiceInjector.getContext();
        this.mFactoryTest = z;
        this.mMetrics = packageManagerServiceInjector.getDisplayMetrics();
        this.mInstaller = packageManagerServiceInjector.getInstaller();
        this.mFreeStorageHelper = new com.android.server.pm.FreeStorageHelper(this);
        timingsTraceAndSlog.traceBegin("createSubComponents");
        com.android.server.LocalServices.addService(android.content.pm.PackageManagerInternal.class, new com.android.server.pm.PackageManagerService.PackageManagerInternalImpl());
        com.android.server.LocalManagerRegistry.addManager(com.android.server.pm.PackageManagerLocal.class, new com.android.server.pm.local.PackageManagerLocalImpl(this));
        com.android.server.LocalServices.addService(android.content.pm.TestUtilityService.class, this);
        this.mTestUtilityService = (android.content.pm.TestUtilityService) com.android.server.LocalServices.getService(android.content.pm.TestUtilityService.class);
        this.mUserManager = packageManagerServiceInjector.getUserManagerService();
        this.mUserNeedsBadging = new com.android.server.pm.UserNeedsBadgingCache(this.mUserManager);
        this.mComponentResolver = packageManagerServiceInjector.getComponentResolver();
        this.mPermissionManager = packageManagerServiceInjector.getPermissionManagerServiceInternal();
        this.mSettings = packageManagerServiceInjector.getSettings();
        this.mIncrementalManager = this.mInjector.getIncrementalManager();
        this.mDefaultAppProvider = this.mInjector.getDefaultAppProvider();
        this.mLegacyPermissionManager = this.mInjector.getLegacyPermissionManagerInternal();
        final com.android.server.compat.PlatformCompat compatibility = this.mInjector.getCompatibility();
        this.mPackageParserCallback = new com.android.internal.pm.parsing.PackageParser2.Callback() { // from class: com.android.server.pm.PackageManagerService.3
            public boolean isChangeEnabled(long j, @android.annotation.NonNull android.content.pm.ApplicationInfo applicationInfo) {
                return compatibility.isChangeEnabled(j, applicationInfo);
            }

            public boolean hasFeature(java.lang.String str4) {
                return com.android.server.pm.PackageManagerService.this.hasSystemFeature(str4, 0);
            }

            public java.util.Set<java.lang.String> getHiddenApiWhitelistedApps() {
                return com.android.server.SystemConfig.getInstance().getHiddenApiWhitelistedApps();
            }

            public java.util.Set<java.lang.String> getInstallConstraintsAllowlist() {
                return com.android.server.SystemConfig.getInstance().getInstallConstraintsAllowlist();
            }
        };
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("addSharedUsers");
        this.mSettings.addSharedUserLPw("android.uid.system", 1000, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.phone", 1001, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.log", 1007, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.nfc", 1027, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.bluetooth", 1002, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.shell", 2000, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.se", SE_UID, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.networkstack", NETWORKSTACK_UID, 1, 8);
        this.mSettings.addSharedUserLPw("android.uid.uwb", UWB_UID, 1, 8);
        timingsTraceAndSlog.traceEnd();
        java.lang.String str4 = android.os.SystemProperties.get("debug.separate_processes");
        if (str4 != null && str4.length() > 0) {
            if (com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER.equals(str4)) {
                this.mDefParseFlags = 2;
                this.mSeparateProcesses = null;
                android.util.Slog.w(TAG, "Running with debug.separate_processes: * (ALL)");
            } else {
                this.mDefParseFlags = 0;
                this.mSeparateProcesses = str4.split(",");
                android.util.Slog.w(TAG, "Running with debug.separate_processes: " + str4);
            }
        } else {
            this.mDefParseFlags = 0;
            this.mSeparateProcesses = null;
        }
        this.mPackageDexOptimizer = packageManagerServiceInjector.getPackageDexOptimizer();
        this.mDexManager = packageManagerServiceInjector.getDexManager();
        this.mDynamicCodeLogger = packageManagerServiceInjector.getDynamicCodeLogger();
        this.mBackgroundDexOptService = packageManagerServiceInjector.getBackgroundDexOptService();
        this.mArtManagerService = packageManagerServiceInjector.getArtManagerService();
        this.mMoveCallbacks = new com.android.server.pm.MovePackageHelper.MoveCallbacks(com.android.server.FgThread.get().getLooper());
        this.mSharedLibraries = this.mInjector.getSharedLibrariesImpl();
        this.mBackgroundHandler = packageManagerServiceInjector.getBackgroundHandler();
        ((android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(0).getMetrics(this.mMetrics);
        timingsTraceAndSlog.traceBegin("get system config");
        com.android.server.SystemConfig systemConfig = packageManagerServiceInjector.getSystemConfig();
        this.mAvailableFeatures = systemConfig.getAvailableFeatures();
        timingsTraceAndSlog.traceEnd();
        this.mProtectedPackages = new com.android.server.pm.ProtectedPackages(this.mContext);
        this.mApexManager = packageManagerServiceInjector.getApexManager();
        this.mAppsFilter = this.mInjector.getAppsFilter();
        this.mChangedPackagesTracker = new com.android.server.pm.ChangedPackagesTracker();
        this.mAppInstallDir = new java.io.File(android.os.Environment.getDataDirectory(), "app");
        this.mDomainVerificationConnection = new com.android.server.pm.DomainVerificationConnection(this);
        this.mDomainVerificationManager = packageManagerServiceInjector.getDomainVerificationManagerInternal();
        this.mDomainVerificationManager.setConnection(this.mDomainVerificationConnection);
        this.mBroadcastHelper = new com.android.server.pm.BroadcastHelper(this.mInjector);
        this.mPackageMonitorCallbackHelper = packageManagerServiceInjector.getPackageMonitorCallbackHelper();
        this.mAppDataHelper = new com.android.server.pm.AppDataHelper(this);
        this.mRemovePackageHelper = new com.android.server.pm.RemovePackageHelper(this, this.mAppDataHelper, this.mBroadcastHelper);
        this.mDeletePackageHelper = new com.android.server.pm.DeletePackageHelper(this, this.mRemovePackageHelper, this.mBroadcastHelper);
        this.mInstallPackageHelper = new com.android.server.pm.InstallPackageHelper(this, this.mAppDataHelper, this.mRemovePackageHelper, this.mDeletePackageHelper, this.mBroadcastHelper);
        this.mInstantAppRegistry = new com.android.server.pm.InstantAppRegistry(this.mContext, this.mPermissionManager, this.mInjector.getUserManagerInternal(), this.mDeletePackageHelper);
        this.mSharedLibraries.setDeletePackageHelper(this.mDeletePackageHelper);
        this.mPreferredActivityHelper = new com.android.server.pm.PreferredActivityHelper(this, this.mBroadcastHelper);
        this.mResolveIntentHelper = new com.android.server.pm.ResolveIntentHelper(this.mContext, this.mPreferredActivityHelper, packageManagerServiceInjector.getCompatibility(), this.mUserManager, this.mDomainVerificationManager, this.mUserNeedsBadging, new java.util.function.Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.pm.ResolveInfo lambda$new$46;
                lambda$new$46 = com.android.server.pm.PackageManagerService.this.lambda$new$46();
                return lambda$new$46;
            }
        }, new java.util.function.Supplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.content.pm.ActivityInfo lambda$new$47;
                lambda$new$47 = com.android.server.pm.PackageManagerService.this.lambda$new$47();
                return lambda$new$47;
            }
        }, packageManagerServiceInjector.getBackgroundHandler());
        this.mDexOptHelper = new com.android.server.pm.DexOptHelper(this);
        this.mSuspendPackageHelper = new com.android.server.pm.SuspendPackageHelper(this, this.mInjector, this.mBroadcastHelper, this.mProtectedPackages);
        this.mDistractingPackageHelper = new com.android.server.pm.DistractingPackageHelper(this, this.mBroadcastHelper, this.mSuspendPackageHelper);
        this.mStorageEventHelper = new com.android.server.pm.StorageEventHelper(this, this.mDeletePackageHelper, this.mRemovePackageHelper);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mSnapshotStatistics = new com.android.server.pm.SnapshotStatistics();
                sSnapshotPendingVersion.incrementAndGet();
                this.mLiveComputer = createLiveComputer();
                registerObservers(true);
            } finally {
                resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
        com.android.server.pm.ComputerLocked computerLocked = this.mLiveComputer;
        synchronized (this.mInstallLock) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
            boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mHandler = packageManagerServiceInjector.getHandler();
                    this.mProcessLoggingHandler = new com.android.server.pm.ProcessLoggingHandler();
                    com.android.server.Watchdog.getInstance().addThread(this.mHandler, 600000L);
                    android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.SharedLibraryEntry> sharedLibraries = systemConfig.getSharedLibraries();
                    int size = sharedLibraries.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        this.mSharedLibraries.addBuiltInSharedLibraryLPw(sharedLibraries.valueAt(i3));
                    }
                    int i4 = 0;
                    while (i4 < size) {
                        java.lang.String keyAt = sharedLibraries.keyAt(i4);
                        com.android.server.SystemConfig.SharedLibraryEntry valueAt = sharedLibraries.valueAt(i4);
                        int length = valueAt.dependencies.length;
                        for (int i5 = i2; i5 < length; i5++) {
                            android.content.pm.SharedLibraryInfo sharedLibraryInfo = computerLocked.getSharedLibraryInfo(valueAt.dependencies[i5], -1L);
                            if (sharedLibraryInfo != null) {
                                computerLocked.getSharedLibraryInfo(keyAt, -1L).addDependency(sharedLibraryInfo);
                            }
                        }
                        i4++;
                        i2 = 0;
                    }
                    com.android.server.pm.SELinuxMMAC.readInstallPolicy();
                    timingsTraceAndSlog.traceBegin("loadFallbacks");
                    android.content.pm.FallbackCategoryProvider.loadFallbacks();
                    timingsTraceAndSlog.traceEnd();
                    timingsTraceAndSlog.traceBegin("read user settings");
                    this.mFirstBoot = !this.mSettings.readLPw(computerLocked, this.mInjector.getUserManagerInternal().getUsers(true, false, false));
                    timingsTraceAndSlog.traceEnd();
                    if (this.mFirstBoot) {
                        timingsTraceAndSlog.traceBegin("setFirstBoot: ");
                        try {
                            this.mInstaller.setFirstBoot();
                        } catch (com.android.server.pm.Installer.InstallerException e) {
                            android.util.Slog.w(TAG, "Could not set First Boot: ", e);
                        }
                        timingsTraceAndSlog.traceEnd();
                    }
                    this.mPermissionManager.readLegacyPermissionsTEMP(this.mSettings.mPermissions);
                    this.mPermissionManager.readLegacyPermissionStateTEMP();
                    if (this.mFirstBoot) {
                        com.android.server.pm.DexOptHelper.requestCopyPreoptedFiles();
                    }
                    java.lang.String string = android.content.res.Resources.getSystem().getString(android.R.string.config_controlsPackage);
                    if (!android.text.TextUtils.isEmpty(string)) {
                        this.mCustomResolverComponentName = android.content.ComponentName.unflattenFromString(string);
                    }
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.BOOT_PROGRESS_PMS_SYSTEM_SCAN_START, uptimeMillis);
                    java.lang.String str5 = java.lang.System.getenv("BOOTCLASSPATH");
                    java.lang.String str6 = java.lang.System.getenv("SYSTEMSERVERCLASSPATH");
                    if (str5 == null) {
                        android.util.Slog.w(TAG, "No BOOTCLASSPATH found!");
                    }
                    if (str6 == null) {
                        android.util.Slog.w(TAG, "No SYSTEMSERVERCLASSPATH found!");
                    }
                    com.android.server.pm.Settings.VersionInfo internalVersion = this.mSettings.getInternalVersion();
                    this.mIsUpgrade = !str.equals(internalVersion.fingerprint);
                    if (this.mIsUpgrade) {
                        com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(4, "Upgrading from " + internalVersion.fingerprint + " (" + internalVersion.buildFingerprint + ") to " + android.content.pm.PackagePartitions.FINGERPRINT + " (" + android.os.Build.VERSION.INCREMENTAL + ")");
                    }
                    this.mPriorSdkVersion = this.mIsUpgrade ? internalVersion.sdkVersion : -1;
                    this.mInitAppsHelper = new com.android.server.pm.InitAppsHelper(this, this.mApexManager, this.mInstallPackageHelper, this.mInjector.getSystemPartitions());
                    this.mPromoteSystemApps = this.mIsUpgrade && internalVersion.sdkVersion <= 22;
                    this.mIsPreNMR1Upgrade = this.mIsUpgrade && internalVersion.sdkVersion < 25;
                    this.mIsPreQUpgrade = this.mIsUpgrade && internalVersion.sdkVersion < 29;
                    com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> packagesLocked = this.mSettings.getPackagesLocked();
                    if (isDeviceUpgrading()) {
                        this.mExistingPackages = new android.util.ArraySet<>(packagesLocked.size());
                        for (int i6 = 0; i6 < packagesLocked.size(); i6++) {
                            this.mExistingPackages.add(packagesLocked.valueAt(i6).getPackageName());
                        }
                        timingsTraceAndSlog.traceBegin("cross profile intent filter update");
                        this.mInjector.getCrossProfileIntentFilterHelper().updateDefaultCrossProfileIntentFilter();
                        timingsTraceAndSlog.traceEnd();
                    }
                    this.mCacheDir = com.android.server.pm.PackageManagerServiceUtils.preparePackageParserCache(this.mIsEngBuild, this.mIsUserDebugBuild, this.mIncrementalVersion);
                    this.mInitialNonStoppedSystemPackages = this.mInjector.getSystemConfig().getInitialNonStoppedSystemPackages();
                    this.mShouldStopSystemPackagesByDefault = this.mContext.getResources().getBoolean(android.R.bool.config_startDreamImmediatelyOnDock);
                    final int[] userIds = this.mUserManager.getUserIds();
                    com.android.internal.pm.parsing.PackageParser2 scanningCachingPackageParser = this.mInjector.getScanningCachingPackageParser();
                    this.mOverlayConfig = this.mInitAppsHelper.initSystemApps(scanningCachingPackageParser, packagesLocked, userIds, uptimeMillis);
                    this.mInitAppsHelper.initNonSystemApps(scanningCachingPackageParser, userIds, uptimeMillis);
                    scanningCachingPackageParser.close();
                    this.mRequiredVerifierPackages = getRequiredButNotReallyRequiredVerifiersLPr(computerLocked);
                    this.mRequiredInstallerPackage = getRequiredInstallerLPr(computerLocked);
                    this.mRequiredUninstallerPackage = getRequiredUninstallerLPr(computerLocked);
                    this.mRequiredPermissionControllerPackage = getRequiredPermissionControllerLPr(computerLocked);
                    this.mStorageManagerPackage = getStorageManagerPackageName(computerLocked);
                    this.mSetupWizardPackage = getSetupWizardPackageNameImpl(computerLocked);
                    this.mComponentResolver.fixProtectedFilterPriorities(this.mSetupWizardPackage);
                    this.mDefaultTextClassifierPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(android.R.string.config_screenshotAppClipsServiceComponent));
                    this.mSystemTextClassifierPackageName = ensureSystemPackageName(computerLocked, this.mContext.getString(android.R.string.config_defaultProfcollectReportUploaderApp));
                    this.mConfiguratorPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(android.R.string.config_defaultSystemCaptionsManagerService));
                    this.mAppPredictionServicePackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(android.R.string.config_customVpnConfirmDialogComponent));
                    this.mIncidentReportApproverPackage = ensureSystemPackageName(computerLocked, this.mContext.getString(android.R.string.config_gnssLocationProviderPackageName));
                    this.mRetailDemoPackage = getRetailDemoPackageName();
                    this.mOverlayConfigSignaturePackage = ensureSystemPackageName(computerLocked, this.mInjector.getSystemConfig().getOverlayConfigSignaturePackage());
                    this.mRecentsPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(android.R.string.config_platformVpnConfirmDialogComponent));
                    this.mAmbientContextDetectionPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(android.R.string.config_customVpnAlwaysOnDisconnectedDialogComponent));
                    this.mWearableSensingPackage = ensureSystemPackageName(computerLocked, getPackageFromComponentString(android.R.string.config_defaultShutdownVibrationFile));
                    this.mSharedLibraries.updateAllSharedLibrariesLPw(null, null, java.util.Collections.unmodifiableMap(this.mPackages));
                    java.util.Iterator<com.android.server.pm.SharedUserSetting> it2 = this.mSettings.getAllSharedUsersLPw().iterator();
                    while (it2.hasNext()) {
                        com.android.server.pm.SharedUserSetting next = it2.next();
                        java.util.List<java.lang.String> applyAdjustedAbiToSharedUser = com.android.server.pm.ScanPackageUtils.applyAdjustedAbiToSharedUser(next, null, this.mInjector.getAbiHelper().getAdjustedAbiForSharedUser(next.getPackageStates(), null));
                        if (com.android.server.pm.DexOptHelper.useArtService() || applyAdjustedAbiToSharedUser == null) {
                            it = it2;
                        } else if (applyAdjustedAbiToSharedUser.size() <= 0) {
                            it = it2;
                        } else {
                            int size2 = applyAdjustedAbiToSharedUser.size() - 1;
                            while (size2 >= 0) {
                                java.util.Iterator<com.android.server.pm.SharedUserSetting> it3 = it2;
                                try {
                                    try {
                                        list = applyAdjustedAbiToSharedUser;
                                        try {
                                            this.mInstaller.rmdex(applyAdjustedAbiToSharedUser.get(size2), com.android.server.pm.InstructionSets.getDexCodeInstructionSet(com.android.server.pm.InstructionSets.getPreferredInstructionSet()));
                                        } catch (com.android.server.pm.Installer.InstallerException e2) {
                                        }
                                    } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e3) {
                                        throw new java.lang.RuntimeException(e3);
                                    }
                                } catch (com.android.server.pm.Installer.InstallerException e4) {
                                    list = applyAdjustedAbiToSharedUser;
                                }
                                size2--;
                                it2 = it3;
                                applyAdjustedAbiToSharedUser = list;
                            }
                            it = it2;
                        }
                        next.fixSeInfoLocked();
                        next.updateProcesses();
                        it2 = it;
                    }
                    this.mPackageUsage.read(packagesLocked);
                    this.mCompilerStats.read();
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.BOOT_PROGRESS_PMS_SCAN_END, android.os.SystemClock.uptimeMillis());
                    android.util.Slog.i(TAG, "Time to scan packages: " + ((android.os.SystemClock.uptimeMillis() - uptimeMillis) / 1000.0f) + " seconds");
                    if (this.mIsUpgrade) {
                        android.util.Slog.i(TAG, "Partitions fingerprint changed from " + internalVersion.fingerprint + " to " + android.content.pm.PackagePartitions.FINGERPRINT + "; regranting permissions for internal storage");
                    }
                    this.mPermissionManager.onStorageVolumeMounted(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, this.mIsUpgrade);
                    internalVersion.sdkVersion = this.mSdkVersion;
                    if (this.mPromoteSystemApps || this.mFirstBoot) {
                        java.util.List<android.content.pm.UserInfo> users = this.mInjector.getUserManagerInternal().getUsers(true);
                        for (int i7 = 0; i7 < users.size(); i7++) {
                            this.mSettings.applyDefaultPreferredAppsLPw(users.get(i7).id);
                        }
                    }
                    if (this.mIsUpgrade) {
                        android.util.Slog.i(TAG, "Build fingerprint changed; clearing code caches");
                        for (int i8 = 0; i8 < packagesLocked.size(); i8++) {
                            com.android.server.pm.PackageSetting valueAt2 = packagesLocked.valueAt(i8);
                            if (java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, valueAt2.getVolumeUuid())) {
                                this.mAppDataHelper.clearAppDataLIF(valueAt2.getPkg(), -1, 131111);
                            }
                        }
                        internalVersion.buildFingerprint = android.os.Build.VERSION.INCREMENTAL;
                        internalVersion.fingerprint = android.content.pm.PackagePartitions.FINGERPRINT;
                    }
                    this.mPrepareAppDataFuture = this.mAppDataHelper.fixAppsDataOnBoot();
                    this.mDisabledComponentsList = new java.util.ArrayList<>();
                    enableComponents(this.mContext.getResources().getStringArray(1057095680), false);
                    enableComponents(this.mContext.getResources().getStringArray(1057095686), false);
                    enableComponents(this.mContext.getResources().getStringArray(1057095685), true);
                    if (this.mIsPreQUpgrade) {
                        android.util.Slog.i(TAG, "Allowlisting all existing apps to hide their icons");
                        int size3 = packagesLocked.size();
                        for (int i9 = 0; i9 < size3; i9++) {
                            com.android.server.pm.PackageSetting valueAt3 = packagesLocked.valueAt(i9);
                            if ((valueAt3.getFlags() & 1) == 0) {
                                valueAt3.disableComponentLPw(android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME, 0);
                            }
                        }
                    }
                    this.mPromoteSystemApps = false;
                    internalVersion.databaseVersion = 3;
                    timingsTraceAndSlog.traceBegin("write settings");
                    writeSettingsLPrTEMP();
                    timingsTraceAndSlog.traceEnd();
                    android.util.EventLog.writeEvent(com.android.server.EventLogTags.BOOT_PROGRESS_PMS_READY, android.os.SystemClock.uptimeMillis());
                    this.mDomainVerificationManager.setProxy(com.android.server.pm.verify.domain.proxy.DomainVerificationProxy.makeProxy(getIntentFilterVerifierComponentNameLPr(computerLocked), getDomainVerificationAgentComponentNameLPr(computerLocked), this.mContext, this.mDomainVerificationManager, this.mDomainVerificationManager.getCollector(), this.mDomainVerificationConnection));
                    this.mServicesExtensionPackageName = getRequiredServicesExtensionPackageLPr(computerLocked);
                    this.mSharedSystemSharedLibraryPackageName = getRequiredSharedLibrary(computerLocked, "android.ext.shared", -1);
                    this.mSettings.setPermissionControllerVersion(computerLocked.getPackageInfo(this.mRequiredPermissionControllerPackage, 0L, 0).getLongVersionCode());
                    this.mRequiredSdkSandboxPackage = getRequiredSdkSandboxPackageName(computerLocked);
                    forEachPackageState(computerLocked, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda10
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.PackageManagerService.this.lambda$new$48(userIds, (com.android.server.pm.pkg.PackageStateInternal) obj);
                        }
                    });
                    this.mInstallerService = this.mInjector.getPackageInstallerService();
                    android.content.ComponentName instantAppResolver = getInstantAppResolver(computerLocked);
                    if (instantAppResolver != null) {
                        if (DEBUG_INSTANT) {
                            android.util.Slog.d(TAG, "Set ephemeral resolver: " + instantAppResolver);
                        }
                        this.mInstantAppResolverConnection = this.mInjector.getInstantAppResolverConnection(instantAppResolver);
                        this.mInstantAppResolverSettingsComponent = getInstantAppResolverSettingsLPr(computerLocked, instantAppResolver);
                        str3 = null;
                    } else {
                        str3 = null;
                        this.mInstantAppResolverConnection = null;
                        this.mInstantAppResolverSettingsComponent = null;
                    }
                    updateInstantAppInstallerLocked(str3);
                    java.util.HashMap hashMap = new java.util.HashMap();
                    for (int i10 : userIds) {
                        hashMap.put(java.lang.Integer.valueOf(i10), computerLocked.getInstalledPackages(0L, i10).getList());
                    }
                    this.mDexManager.load(hashMap);
                    this.mDynamicCodeLogger.load(hashMap);
                    if (this.mIsUpgrade) {
                        com.android.internal.util.FrameworkStatsLog.write(239, 13, android.os.SystemClock.uptimeMillis() - uptimeMillis);
                    }
                    if (this.mFirstBoot || isDeviceUpgrading()) {
                        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : systemConfig.getAppMetadataFilePaths().entrySet()) {
                            java.lang.String key = entry.getKey();
                            java.lang.String value = entry.getValue();
                            value = new java.io.File(value).exists() ? value : str3;
                            com.android.server.pm.PackageSetting disabledSystemPkgLPr = this.mSettings.getDisabledSystemPkgLPr(key);
                            if (disabledSystemPkgLPr == null) {
                                com.android.server.pm.PackageSetting packageLPr = this.mSettings.getPackageLPr(key);
                                if (packageLPr != null) {
                                    packageLPr.setAppMetadataFilePath(value);
                                    if (android.content.pm.Flags.aslInApkAppMetadataSource()) {
                                        packageLPr.setAppMetadataSource(3);
                                    }
                                } else {
                                    android.util.Slog.w(TAG, "Cannot set app metadata file for nonexistent package " + key);
                                }
                            } else {
                                disabledSystemPkgLPr.setAppMetadataFilePath(value);
                                if (android.content.pm.Flags.aslInApkAppMetadataSource()) {
                                    disabledSystemPkgLPr.setAppMetadataSource(3);
                                }
                            }
                        }
                    }
                    this.mLiveComputer = createLiveComputer();
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
        }
        this.mModuleInfoProvider = this.mInjector.getModuleInfoProvider();
        this.mInjector.getSystemWrapper().enablePackageCaches();
        this.mInstaller.setWarnIfHeld(this.mLock);
        com.android.internal.pm.pkg.parsing.ParsingPackageUtils.readConfigUseRoundIcon(this.mContext.getResources());
        this.mServiceStartWithDelay = android.os.SystemClock.uptimeMillis() + 60000;
        android.util.Slog.i(TAG, "Fix for b/169414761 is applied");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ResolveInfo lambda$new$46() {
        return this.mResolveInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ActivityInfo lambda$new$47() {
        return this.mInstantAppInstallerActivity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$48(int[] iArr, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal.getAndroidPackage() == null || packageStateInternal.isSystem()) {
            return;
        }
        for (int i : iArr) {
            if (packageStateInternal.getUserStateOrDefault(i).isInstantApp() && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                this.mInstantAppRegistry.addInstantApp(i, packageStateInternal.getAppId());
            }
        }
    }

    private void enableComponents(java.lang.String[] strArr, boolean z) {
        for (java.lang.String str : strArr) {
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
            if (!z) {
                this.mDisabledComponentsList.add(unflattenFromString);
            }
            android.util.Slog.v(TAG, "Changing enabled state of " + str + " to " + z);
            java.lang.String className = unflattenFromString.getClassName();
            com.android.server.pm.PackageSetting packageSetting = this.mSettings.mPackages.get(unflattenFromString.getPackageName());
            if (packageSetting == null || packageSetting.getPkg() == null || !com.android.server.pm.parsing.pkg.AndroidPackageUtils.hasComponentClassName(packageSetting.getPkg(), className)) {
                android.util.Slog.w(TAG, "Unable to change enabled state of " + str + " to " + z);
            } else if (z) {
                packageSetting.enableComponentLPw(className, 0);
            } else {
                packageSetting.disableComponentLPw(className, 0);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void updateInstantAppInstallerLocked(java.lang.String str) {
        if (this.mInstantAppInstallerActivity != null && !this.mInstantAppInstallerActivity.getComponentName().getPackageName().equals(str)) {
            return;
        }
        setUpInstantAppInstallerActivityLP(getInstantAppInstallerLPr());
    }

    public boolean isFirstBoot() {
        return this.mFirstBoot;
    }

    public boolean isDeviceUpgrading() {
        return this.mIsUpgrade || android.os.SystemProperties.getBoolean("persist.pm.mock-upgrade", false);
    }

    @android.annotation.NonNull
    private java.lang.String[] getRequiredButNotReallyRequiredVerifiersLPr(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computer, new android.content.Intent("android.intent.action.PACKAGE_NEEDS_VERIFICATION"), PACKAGE_MIME_TYPE, 1835008L, 0, android.os.Binder.getCallingUid());
        int size = queryIntentReceiversInternal.size();
        if (size == 0) {
            android.util.Log.w(TAG, "There should probably be a verifier, but, none were found");
            return libcore.util.EmptyArray.STRING;
        }
        if (size <= 2) {
            java.lang.String[] strArr = new java.lang.String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = queryIntentReceiversInternal.get(i).getComponentInfo().packageName;
                if (android.text.TextUtils.isEmpty(strArr[i])) {
                    throw new java.lang.RuntimeException("Invalid verifier: " + queryIntentReceiversInternal);
                }
            }
            return strArr;
        }
        throw new java.lang.RuntimeException("There must be no more than 2 verifiers; found " + queryIntentReceiversInternal);
    }

    @android.annotation.NonNull
    private java.lang.String getRequiredSharedLibrary(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, int i) {
        android.content.pm.SharedLibraryInfo sharedLibraryInfo = computer.getSharedLibraryInfo(str, i);
        if (sharedLibraryInfo == null) {
            throw new java.lang.IllegalStateException("Missing required shared library:" + str);
        }
        java.lang.String packageName = sharedLibraryInfo.getPackageName();
        if (packageName == null) {
            throw new java.lang.IllegalStateException("Expected a package for shared library " + str);
        }
        return packageName;
    }

    @android.annotation.NonNull
    private java.lang.String getRequiredServicesExtensionPackageLPr(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.lang.String string = this.mContext.getString(android.R.string.config_screenshotAppClipsServiceComponent);
        if (android.text.TextUtils.isEmpty(string)) {
            throw new java.lang.RuntimeException("Required services extension package failed due to config_servicesExtensionPackage is empty.");
        }
        java.lang.String ensureSystemPackageName = ensureSystemPackageName(computer, string);
        if (android.text.TextUtils.isEmpty(ensureSystemPackageName)) {
            throw new java.lang.RuntimeException("Required services extension package is missing, config_servicesExtensionPackage had defined with " + string + ", but can not find the package info on the system image, check if the package has a problem.");
        }
        return ensureSystemPackageName;
    }

    @android.annotation.NonNull
    private java.lang.String getRequiredInstallerLPr(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.INSTALL_PACKAGE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(android.net.Uri.parse("content://com.example/foo.apk"), PACKAGE_MIME_TYPE);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, PACKAGE_MIME_TYPE, 1835008L, 0);
        if (queryIntentActivitiesInternal.size() == 1) {
            if (!queryIntentActivitiesInternal.get(0).activityInfo.applicationInfo.isPrivilegedApp()) {
                throw new java.lang.RuntimeException("The installer must be a privileged app");
            }
            return queryIntentActivitiesInternal.get(0).getComponentInfo().packageName;
        }
        throw new java.lang.RuntimeException("There must be exactly one installer; found " + queryIntentActivitiesInternal);
    }

    @android.annotation.NonNull
    private java.lang.String getRequiredUninstallerLPr(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.UNINSTALL_PACKAGE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(android.net.Uri.fromParts("package", "foo.bar", null));
        android.content.pm.ResolveInfo resolveIntentInternal = this.mResolveIntentHelper.resolveIntentInternal(computer, intent, null, 1835008L, 0L, 0, false, android.os.Binder.getCallingUid());
        if (resolveIntentInternal == null || this.mResolveActivity.name.equals(resolveIntentInternal.getComponentInfo().name)) {
            throw new java.lang.RuntimeException("There must be exactly one uninstaller; found " + resolveIntentInternal);
        }
        return resolveIntentInternal.getComponentInfo().packageName;
    }

    @android.annotation.NonNull
    private java.lang.String getRequiredPermissionControllerLPr(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MANAGE_PERMISSIONS");
        intent.addCategory("android.intent.category.DEFAULT");
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, null, 1835008L, 0);
        if (queryIntentActivitiesInternal.size() == 1) {
            if (!queryIntentActivitiesInternal.get(0).activityInfo.applicationInfo.isPrivilegedApp()) {
                throw new java.lang.RuntimeException("The permissions manager must be a privileged app");
            }
            return queryIntentActivitiesInternal.get(0).getComponentInfo().packageName;
        }
        throw new java.lang.RuntimeException("There must be exactly one permissions manager; found " + queryIntentActivitiesInternal);
    }

    @android.annotation.NonNull
    private android.content.ComponentName getIntentFilterVerifierComponentNameLPr(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computer, new android.content.Intent("android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION"), PACKAGE_MIME_TYPE, 1835008L, 0, android.os.Binder.getCallingUid());
        int size = queryIntentReceiversInternal.size();
        android.content.pm.ResolveInfo resolveInfo = null;
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo2 = queryIntentReceiversInternal.get(i);
            if (checkPermission("android.permission.INTENT_FILTER_VERIFICATION_AGENT", resolveInfo2.getComponentInfo().packageName, 0) == 0 && (resolveInfo == null || resolveInfo2.priority > resolveInfo.priority)) {
                resolveInfo = resolveInfo2;
            }
        }
        if (resolveInfo != null) {
            return resolveInfo.getComponentInfo().getComponentName();
        }
        android.util.Slog.w(TAG, "Intent filter verifier not found");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.content.ComponentName getDomainVerificationAgentComponentNameLPr(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentReceiversInternal = this.mResolveIntentHelper.queryIntentReceiversInternal(computer, new android.content.Intent("android.intent.action.DOMAINS_NEED_VERIFICATION"), null, 1835008L, 0, android.os.Binder.getCallingUid());
        int size = queryIntentReceiversInternal.size();
        android.content.pm.ResolveInfo resolveInfo = null;
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo2 = queryIntentReceiversInternal.get(i);
            java.lang.String str = resolveInfo2.getComponentInfo().packageName;
            if (checkPermission("android.permission.DOMAIN_VERIFICATION_AGENT", str, 0) != 0) {
                android.util.Slog.w(TAG, "Domain verification agent found but does not hold permission: " + str);
            } else if (resolveInfo == null || resolveInfo2.priority > resolveInfo.priority) {
                if (computer.isComponentEffectivelyEnabled(resolveInfo2.getComponentInfo(), android.os.UserHandle.SYSTEM)) {
                    resolveInfo = resolveInfo2;
                } else {
                    android.util.Slog.w(TAG, "Domain verification agent found but not enabled");
                }
            }
        }
        if (resolveInfo != null) {
            return resolveInfo.getComponentInfo().getComponentName();
        }
        android.util.Slog.w(TAG, "Domain verification agent not found");
        return null;
    }

    @android.annotation.Nullable
    android.content.ComponentName getInstantAppResolver(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.config_emergency_iso_country_codes);
        if (stringArray.length == 0 && !android.os.Build.IS_DEBUGGABLE) {
            if (DEBUG_INSTANT) {
                android.util.Slog.d(TAG, "Ephemeral resolver NOT found; empty package list");
            }
            return null;
        }
        java.util.List<android.content.pm.ResolveInfo> queryIntentServicesInternal = computer.queryIntentServicesInternal(new android.content.Intent("android.intent.action.RESOLVE_INSTANT_APP_PACKAGE"), null, (!android.os.Build.IS_DEBUGGABLE ? 1048576 : 0) | 786432, 0, android.os.Binder.getCallingUid(), false);
        int size = queryIntentServicesInternal.size();
        if (size == 0) {
            if (DEBUG_INSTANT) {
                android.util.Slog.d(TAG, "Ephemeral resolver NOT found; no matching intent filters");
            }
            return null;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(java.util.Arrays.asList(stringArray));
        for (int i = 0; i < size; i++) {
            android.content.pm.ResolveInfo resolveInfo = queryIntentServicesInternal.get(i);
            if (resolveInfo.serviceInfo != null) {
                java.lang.String str = resolveInfo.serviceInfo.packageName;
                if (!arraySet.contains(str) && !android.os.Build.IS_DEBUGGABLE) {
                    if (DEBUG_INSTANT) {
                        android.util.Slog.d(TAG, "Ephemeral resolver not in allowed package list; pkg: " + str + ", info:" + resolveInfo);
                    }
                } else {
                    if (DEBUG_INSTANT) {
                        android.util.Slog.v(TAG, "Ephemeral resolver found; pkg: " + str + ", info:" + resolveInfo);
                    }
                    return new android.content.ComponentName(str, resolveInfo.serviceInfo.name);
                }
            }
        }
        if (DEBUG_INSTANT) {
            android.util.Slog.v(TAG, "Ephemeral resolver NOT found");
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.pm.ActivityInfo getInstantAppInstallerLPr() {
        java.lang.String[] strArr;
        if (this.mIsEngBuild) {
            strArr = new java.lang.String[]{"android.intent.action.INSTALL_INSTANT_APP_PACKAGE_TEST", "android.intent.action.INSTALL_INSTANT_APP_PACKAGE"};
        } else {
            strArr = new java.lang.String[]{"android.intent.action.INSTALL_INSTANT_APP_PACKAGE"};
        }
        int i = (this.mIsEngBuild ? 0 : 1048576) | (-2146697216);
        com.android.server.pm.Computer snapshotComputer = snapshotComputer();
        android.content.Intent intent = new android.content.Intent();
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(android.net.Uri.fromFile(new java.io.File("foo.apk")), PACKAGE_MIME_TYPE);
        java.util.List<android.content.pm.ResolveInfo> list = null;
        for (java.lang.String str : strArr) {
            intent.setAction(str);
            list = snapshotComputer.queryIntentActivitiesInternal(intent, PACKAGE_MIME_TYPE, i, 0);
            if (!list.isEmpty()) {
                break;
            }
            if (DEBUG_INSTANT) {
                android.util.Slog.d(TAG, "Instant App installer not found with " + str);
            }
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it = list.iterator();
        while (it.hasNext()) {
            if (checkPermission("android.permission.INSTALL_PACKAGES", it.next().activityInfo.packageName, 0) != 0 && !this.mIsEngBuild) {
                it.remove();
            }
        }
        if (list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return (android.content.pm.ActivityInfo) list.get(0).getComponentInfo();
        }
        throw new java.lang.RuntimeException("There must be at most one ephemeral installer; found " + list);
    }

    @android.annotation.Nullable
    private android.content.ComponentName getInstantAppResolverSettingsLPr(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.ComponentName componentName) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(new android.content.Intent("android.intent.action.INSTANT_APP_RESOLVER_SETTINGS").addCategory("android.intent.category.DEFAULT").setPackage(componentName.getPackageName()), null, 786432L, 0);
        if (queryIntentActivitiesInternal.isEmpty()) {
            return null;
        }
        return queryIntentActivitiesInternal.get(0).getComponentInfo().getComponentName();
    }

    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        return ((android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class)).getPermissionGroupInfo(str, i);
    }

    public void freeAllAppCacheAboveQuota(java.lang.String str) throws java.io.IOException {
        synchronized (this.mInstallLock) {
            try {
                this.mInstaller.freeCache(str, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, 2304);
            } catch (com.android.server.pm.Installer.InstallerException e) {
            }
        }
    }

    public void freeStorage(java.lang.String str, long j, int i) throws java.io.IOException {
        this.mFreeStorageHelper.freeStorage(str, j, i);
    }

    int freeCacheForInstallation(int i, android.content.pm.parsing.PackageLite packageLite, java.lang.String str, java.lang.String str2, int i2) {
        return this.mFreeStorageHelper.freeCacheForInstallation(i, packageLite, str, str2, i2);
    }

    public android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) {
        return this.mModuleInfoProvider.getModuleInfo(str, i);
    }

    void updateSequenceNumberLP(com.android.server.pm.PackageSetting packageSetting, int[] iArr) {
        this.mChangedPackagesTracker.updateSequenceNumber(packageSetting.getPackageName(), iArr);
    }

    public boolean hasSystemFeature(java.lang.String str, int i) {
        android.content.pm.FeatureInfo featureInfo = this.mAvailableFeatures.get(str);
        return featureInfo != null && featureInfo.version >= i;
    }

    public int checkPermission(java.lang.String str, java.lang.String str2, int i) {
        return this.mPermissionManager.checkPermission(str2, str, "default:0", i);
    }

    public java.lang.String getSdkSandboxPackageName() {
        return this.mRequiredSdkSandboxPackage;
    }

    java.lang.String getPackageInstallerPackageName() {
        return this.mRequiredInstallerPackage;
    }

    void requestInstantAppResolutionPhaseTwo(android.content.pm.AuxiliaryResolveInfo auxiliaryResolveInfo, android.content.Intent intent, java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, boolean z, android.os.Bundle bundle, int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(20, new android.content.pm.InstantAppRequest(auxiliaryResolveInfo, intent, str, str2, str3, z, i, bundle, false, auxiliaryResolveInfo.hostDigestPrefixSecure, auxiliaryResolveInfo.token)));
    }

    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.content.pm.ResolveInfo> queryIntentReceivers(@android.annotation.NonNull com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, long j, int i) {
        return new android.content.pm.ParceledListSlice<>(this.mResolveIntentHelper.queryIntentReceiversInternal(computer, intent, str, j, i, android.os.Binder.getCallingUid()));
    }

    public static void reportSettingsProblem(int i, java.lang.String str) {
        com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(i, str);
    }

    static void renameStaticSharedLibraryPackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
        parsedPackage.setPackageName(toStaticSharedLibraryPackageName(parsedPackage.getPackageName(), parsedPackage.getStaticSharedLibraryVersion()));
    }

    private static java.lang.String toStaticSharedLibraryPackageName(java.lang.String str, long j) {
        return str + STATIC_SHARED_LIB_DELIMITER + j;
    }

    public void performFstrimIfNeeded() {
        this.mFreeStorageHelper.performFstrimIfNeeded();
    }

    public void updatePackagesIfNeeded() {
        this.mDexOptHelper.performPackageDexOptUpgradeIfNeeded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPackageUseInternal(java.lang.String str, int i) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mSettings.getPackageLPr(str);
                if (packageLPr == null) {
                    resetPriorityAfterPackageManagerTracedLockedSection();
                } else {
                    packageLPr.getPkgState().setLastPackageUsageTimeInMills(i, currentTimeMillis);
                    resetPriorityAfterPackageManagerTracedLockedSection();
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    com.android.server.pm.dex.DexManager getDexManager() {
        return this.mDexManager;
    }

    com.android.server.pm.DexOptHelper getDexOptHelper() {
        return this.mDexOptHelper;
    }

    com.android.server.pm.dex.DynamicCodeLogger getDynamicCodeLogger() {
        return this.mDynamicCodeLogger;
    }

    public void shutdown() {
        this.mCompilerStats.writeNow();
        this.mDexManager.writePackageDexUsageNow();
        this.mDynamicCodeLogger.writeNow();
        com.android.server.PackageWatchdog.getInstance(this.mContext).writeNow();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPackageUsage.writeNow(this.mSettings.getPackagesLocked());
                if (!this.mHandler.hasMessages(13)) {
                    if (!this.mBackgroundHandler.hasMessages(14)) {
                        if (this.mHandler.hasMessages(19)) {
                        }
                    }
                }
                while (!tryWriteSettings(true)) {
                    android.util.Slog.wtf(TAG, "Failed to write settings on shutdown");
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @android.annotation.NonNull
    int[] resolveUserIds(int i) {
        return i == -1 ? this.mUserManager.getUserIds() : new int[]{i};
    }

    private void setUpInstantAppInstallerActivityLP(android.content.pm.ActivityInfo activityInfo) {
        if (activityInfo == null) {
            if (DEBUG_INSTANT) {
                android.util.Slog.d(TAG, "Clear ephemeral installer activity");
            }
            this.mInstantAppInstallerActivity = null;
            onChanged();
            return;
        }
        if (DEBUG_INSTANT) {
            android.util.Slog.d(TAG, "Set ephemeral installer activity: " + activityInfo.getComponentName());
        }
        this.mInstantAppInstallerActivity = activityInfo;
        this.mInstantAppInstallerActivity.flags |= 288;
        this.mInstantAppInstallerActivity.exported = true;
        this.mInstantAppInstallerActivity.enabled = true;
        this.mInstantAppInstallerInfo.activityInfo = this.mInstantAppInstallerActivity;
        this.mInstantAppInstallerInfo.priority = 1;
        this.mInstantAppInstallerInfo.preferredOrder = 1;
        this.mInstantAppInstallerInfo.isDefault = true;
        this.mInstantAppInstallerInfo.match = 5799936;
        onChanged();
    }

    void killApplication(java.lang.String str, int i, java.lang.String str2, int i2) {
        killApplication(str, i, -1, str2, i2);
    }

    void killApplication(java.lang.String str, int i, int i2, java.lang.String str2, int i3) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.IActivityManager service = android.app.ActivityManager.getService();
            if (service != null) {
                try {
                    service.killApplication(str, i, i2, str2, i3);
                } catch (android.os.RemoteException e) {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.PackageSender
    public void notifyPackageAdded(java.lang.String str, int i) {
        this.mPackageObserverHelper.notifyAdded(str, i);
    }

    @Override // com.android.server.pm.PackageSender
    public void notifyPackageChanged(java.lang.String str, int i) {
        this.mPackageObserverHelper.notifyChanged(str, i);
    }

    @Override // com.android.server.pm.PackageSender
    public void notifyPackageRemoved(java.lang.String str, int i) {
        this.mPackageObserverHelper.notifyRemoved(str, i);
        android.content.pm.UserPackage.removeFromCache(android.os.UserHandle.getUserId(i), str);
    }

    boolean isUserRestricted(int i, java.lang.String str) {
        if (!this.mUserManager.getUserRestrictions(i).getBoolean(str, false)) {
            return false;
        }
        android.util.Log.w(TAG, "User is restricted: " + str);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceCanSetPackagesSuspendedAsUser(@android.annotation.NonNull com.android.server.pm.Computer computer, boolean z, android.content.pm.UserPackage userPackage, int i, int i2, java.lang.String str) {
        if (i == 0 || android.os.UserHandle.getAppId(i) == 1000) {
            return;
        }
        java.lang.String deviceOwnerOrProfileOwnerPackage = this.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(i2);
        if (deviceOwnerOrProfileOwnerPackage == null || computer.getPackageUid(deviceOwnerOrProfileOwnerPackage, 0L, i2) != i) {
            boolean z2 = false;
            if (!z) {
                this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", str);
            } else {
                if (!(this.mContext.checkCallingOrSelfPermission("android.permission.QUARANTINE_APPS") == 0)) {
                    this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", str);
                }
            }
            int packageUid = computer.getPackageUid(userPackage.packageName, 0L, i2);
            boolean z3 = packageUid == i;
            if (i == 2000 && android.os.UserHandle.isSameApp(packageUid, i)) {
                z2 = true;
            }
            if (!z2 && !z3) {
                throw new java.lang.SecurityException("Suspending package " + userPackage.packageName + " in user " + i2 + " does not belong to calling uid " + i);
            }
        }
    }

    void unsuspendForSuspendingPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, int i) {
        java.lang.String[] strArr = (java.lang.String[]) computer.getPackageStates().keySet().toArray(new java.lang.String[0]);
        final android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i, str);
        java.util.Objects.requireNonNull(of);
        this.mSuspendPackageHelper.removeSuspensionsBySuspendingPackage(computer, strArr, new java.util.function.Predicate() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return of.equals((android.content.pm.UserPackage) obj);
            }
        }, i);
    }

    void removeAllDistractingPackageRestrictions(@android.annotation.NonNull com.android.server.pm.Computer computer, int i) {
        this.mDistractingPackageHelper.removeDistractingPackageRestrictions(computer, computer.getAllAvailablePackageNames(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceCanSetDistractingPackageRestrictionsAsUser(int i, int i2, java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", str);
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot(i) && android.os.UserHandle.getUserId(i) != i2) {
            throw new java.lang.SecurityException("Calling uid " + i + " cannot call for user " + i2);
        }
    }

    void setEnableRollbackCode(int i, int i2) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(21);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        this.mHandler.sendMessage(obtainMessage);
    }

    void notifyFirstLaunch(final java.lang.String str, final java.lang.String str2, final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageManagerService.this.lambda$notifyFirstLaunch$49(str, i, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyFirstLaunch$49(java.lang.String str, int i, java.lang.String str2) {
        for (int i2 = 0; i2 < this.mRunningInstalls.size(); i2++) {
            com.android.server.pm.InstallRequest valueAt = this.mRunningInstalls.valueAt(i2);
            if (valueAt.getReturnCode() == 1 && str.equals(valueAt.getPkg().getPackageName())) {
                for (int i3 = 0; i3 < valueAt.getNewUsers().length; i3++) {
                    if (i == valueAt.getNewUsers()[i3]) {
                        return;
                    }
                }
            }
        }
        boolean isInstantAppInternal = snapshotComputer().isInstantAppInternal(str, i, 1000);
        this.mBroadcastHelper.sendFirstLaunchBroadcast(str, str2, isInstantAppInternal ? EMPTY_INT_ARRAY : new int[]{i}, isInstantAppInternal ? new int[]{i} : EMPTY_INT_ARRAY);
    }

    com.android.server.pm.Settings.VersionInfo getSettingsVersionForPackage(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage.isExternalStorage()) {
            if (android.text.TextUtils.isEmpty(androidPackage.getVolumeUuid())) {
                return this.mSettings.getExternalVersion();
            }
            return this.mSettings.findOrCreateVersion(androidPackage.getVolumeUuid());
        }
        return this.mSettings.getInternalVersion();
    }

    public void deleteExistingPackageAsUser(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i) {
        this.mDeletePackageHelper.deleteExistingPackageAsUser(versionedPackage, iPackageDeleteObserver2, i);
    }

    public void deletePackageVersioned(android.content.pm.VersionedPackage versionedPackage, android.content.pm.IPackageDeleteObserver2 iPackageDeleteObserver2, int i, int i2) {
        this.mDeletePackageHelper.deletePackageVersionedInternal(versionedPackage, iPackageDeleteObserver2, i, i2, false);
    }

    boolean isCallerVerifier(@android.annotation.NonNull com.android.server.pm.Computer computer, int i) {
        int userId = android.os.UserHandle.getUserId(i);
        for (java.lang.String str : this.mRequiredVerifierPackages) {
            if (i == computer.getPackageUid(str, 0L, userId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPackageDeviceAdminOnAnyUser(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (computer.checkUidPermission("android.permission.MANAGE_USERS", callingUid) != 0) {
            android.util.EventLog.writeEvent(1397638484, "128599183", -1, "");
            throw new java.lang.SecurityException("android.permission.MANAGE_USERS permission is required to call this API");
        }
        if (computer.getInstantAppPackageName(callingUid) != null && !computer.isCallerSameApp(str, callingUid)) {
            return false;
        }
        return isPackageDeviceAdmin(str, -1);
    }

    boolean isPackageDeviceAdmin(java.lang.String str, int i) {
        int[] iArr;
        android.app.admin.IDevicePolicyManager devicePolicyManager = getDevicePolicyManager();
        if (devicePolicyManager != null) {
            try {
                android.content.ComponentName deviceOwnerComponent = devicePolicyManager.getDeviceOwnerComponent(false);
                if (str.equals(deviceOwnerComponent == null ? null : deviceOwnerComponent.getPackageName())) {
                    return true;
                }
                if (i == -1) {
                    iArr = this.mUserManager.getUserIds();
                } else {
                    iArr = new int[]{i};
                }
                for (int i2 = 0; i2 < iArr.length; i2++) {
                    if (devicePolicyManager.packageHasActiveAdmins(str, iArr[i2]) || isDeviceManagementRoleHolder(str, iArr[i2])) {
                        return true;
                    }
                }
            } catch (android.os.RemoteException e) {
            }
        }
        return false;
    }

    private boolean isDeviceManagementRoleHolder(java.lang.String str, int i) {
        return java.util.Objects.equals(str, getDevicePolicyManagementRoleHolderPackageName(i));
    }

    @android.annotation.Nullable
    public java.lang.String getDevicePolicyManagementRoleHolderPackageName(final int i) {
        return (java.lang.String) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda21
            public final java.lang.Object getOrThrow() {
                java.lang.String lambda$getDevicePolicyManagementRoleHolderPackageName$50;
                lambda$getDevicePolicyManagementRoleHolderPackageName$50 = com.android.server.pm.PackageManagerService.this.lambda$getDevicePolicyManagementRoleHolderPackageName$50(i);
                return lambda$getDevicePolicyManagementRoleHolderPackageName$50;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getDevicePolicyManagementRoleHolderPackageName$50(int i) throws java.lang.Exception {
        java.util.List roleHoldersAsUser = ((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).getRoleHoldersAsUser("android.app.role.DEVICE_POLICY_MANAGEMENT", android.os.UserHandle.of(i));
        if (roleHoldersAsUser.isEmpty()) {
            return null;
        }
        return (java.lang.String) roleHoldersAsUser.get(0);
    }

    private android.app.admin.IDevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = android.app.admin.IDevicePolicyManager.Stub.asInterface(android.os.ServiceManager.getService("device_policy"));
        }
        return this.mDevicePolicyManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clearApplicationUserDataLIF(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, int i) {
        int i2 = 0;
        if (str == null) {
            android.util.Slog.w(TAG, "Attempt to delete null packageName.");
            return false;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = computer.getPackage(str);
        if (androidPackage == null) {
            android.util.Slog.w(TAG, "Package named '" + str + "' doesn't exist.");
            return false;
        }
        this.mPermissionManager.resetRuntimePermissions(androidPackage, i);
        this.mAppDataHelper.clearAppDataLIF(androidPackage, i, 7);
        this.mAppDataHelper.clearKeystoreData(i, android.os.UserHandle.getAppId(androidPackage.getUid()));
        com.android.server.pm.UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
        android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) this.mInjector.getLocalService(android.os.storage.StorageManagerInternal.class);
        if (android.os.storage.StorageManager.isCeStorageUnlocked(i) && storageManagerInternal.isCeStoragePrepared(i)) {
            i2 = 3;
        } else if (userManagerInternal.isUserRunning(i)) {
            i2 = 1;
        }
        this.mAppDataHelper.prepareAppDataContentsLIF(androidPackage, computer.getPackageStateInternal(str), i, i2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void resetComponentEnabledSettingsIfNeededLPw(java.lang.String str, final int i) {
        final com.android.server.pm.PackageSetting packageLPr;
        com.android.server.pm.pkg.AndroidPackage androidPackage = str != null ? this.mPackages.get(str) : null;
        if (androidPackage == null || !androidPackage.isResetEnabledSettingsOnAppDataCleared() || (packageLPr = this.mSettings.getPackageLPr(str)) == null) {
            return;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.function.Consumer consumer = new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda64
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.PackageManagerService.lambda$resetComponentEnabledSettingsIfNeededLPw$51(com.android.server.pm.PackageSetting.this, i, arrayList, (com.android.internal.pm.pkg.component.ParsedMainComponent) obj);
            }
        };
        for (int i2 = 0; i2 < androidPackage.getActivities().size(); i2++) {
            consumer.accept(androidPackage.getActivities().get(i2));
        }
        for (int i3 = 0; i3 < androidPackage.getReceivers().size(); i3++) {
            consumer.accept(androidPackage.getReceivers().get(i3));
        }
        for (int i4 = 0; i4 < androidPackage.getServices().size(); i4++) {
            consumer.accept(androidPackage.getServices().get(i4));
        }
        for (int i5 = 0; i5 < androidPackage.getProviders().size(); i5++) {
            consumer.accept(androidPackage.getProviders().get(i5));
        }
        if (com.android.internal.util.ArrayUtils.isEmpty(arrayList)) {
            return;
        }
        updateSequenceNumberLP(packageLPr, new int[]{i});
        updateInstantAppInstallerLocked(str);
        scheduleWritePackageRestrictions(i);
        this.mPendingBroadcasts.addComponents(i, str, arrayList);
        if (!this.mHandler.hasMessages(1)) {
            this.mHandler.sendEmptyMessageDelayed(1, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resetComponentEnabledSettingsIfNeededLPw$51(com.android.server.pm.PackageSetting packageSetting, int i, java.util.ArrayList arrayList, com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent) {
        if (packageSetting.restoreComponentLPw(parsedMainComponent.getClassName(), i)) {
            arrayList.add(parsedMainComponent.getClassName());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void clearPackagePreferredActivitiesLPw(java.lang.String str, @android.annotation.NonNull android.util.SparseBooleanArray sparseBooleanArray, int i) {
        this.mSettings.clearPackagePreferredActivities(str, sparseBooleanArray, i);
    }

    void restorePermissionsAndUpdateRolesForNewUserInstall(java.lang.String str, int i) {
        java.lang.String pendingDefaultBrowserLPr;
        this.mPermissionManager.restoreDelayedRuntimePermissions(str, i);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                pendingDefaultBrowserLPr = this.mSettings.getPendingDefaultBrowserLPr(i);
            } finally {
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
        if (java.util.Objects.equals(str, pendingDefaultBrowserLPr)) {
            this.mDefaultAppProvider.setDefaultBrowser(str, i);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
            boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    this.mSettings.removePendingDefaultBrowserLPw(i);
                } finally {
                }
            }
            resetPriorityAfterPackageManagerTracedLockedSection();
        }
        this.mPreferredActivityHelper.updateDefaultHomeNotLocked(snapshotComputer(), i);
    }

    public void addCrossProfileIntentFilter(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.WatchedIntentFilter watchedIntentFilter, java.lang.String str, int i, int i2, int i3) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        int callingUid = android.os.Binder.getCallingUid();
        enforceOwnerRights(computer, str, callingUid);
        this.mUserManager.enforceCrossProfileIntentFilterAccess(i, i2, callingUid, true);
        com.android.server.pm.PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerInternal(), "no_debugging_features", callingUid, i);
        if (!watchedIntentFilter.checkDataPathAndSchemeSpecificParts()) {
            android.util.EventLog.writeEvent(1397638484, "246749936", java.lang.Integer.valueOf(callingUid));
            throw new java.lang.IllegalArgumentException("Invalid intent data paths or scheme specific parts in the filter.");
        }
        if (watchedIntentFilter.countActions() == 0) {
            android.util.Slog.w(TAG, "Cannot set a crossProfile intent filter with no filter actions");
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = new com.android.server.pm.CrossProfileIntentFilter(watchedIntentFilter, str, i2, i3, this.mUserManager.getCrossProfileIntentFilterAccessControl(i, i2));
                com.android.server.pm.CrossProfileIntentResolver editCrossProfileIntentResolverLPw = this.mSettings.editCrossProfileIntentResolverLPw(i);
                java.util.ArrayList<com.android.server.pm.CrossProfileIntentFilter> findFilters = editCrossProfileIntentResolverLPw.findFilters(watchedIntentFilter);
                if (findFilters != null) {
                    int size = findFilters.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        if (crossProfileIntentFilter.equalsIgnoreFilter(findFilters.get(i4))) {
                            resetPriorityAfterPackageManagerTracedLockedSection();
                            return;
                        }
                    }
                }
                editCrossProfileIntentResolverLPw.addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) snapshotComputer(), (com.android.server.pm.Computer) crossProfileIntentFilter);
                resetPriorityAfterPackageManagerTracedLockedSection();
                scheduleWritePackageRestrictions(i);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceOwnerRights(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, int i) {
        if (android.os.UserHandle.getAppId(i) == 1000) {
            return;
        }
        if (!com.android.internal.util.ArrayUtils.contains(computer.getPackagesForUid(i), str)) {
            throw new java.lang.SecurityException("Calling uid " + i + " does not own package " + str);
        }
        int userId = android.os.UserHandle.getUserId(i);
        if (computer.getPackageInfo(str, 0L, userId) == null) {
            throw new java.lang.IllegalArgumentException("Unknown package " + str + " on user " + userId);
        }
    }

    public void sendSessionCommitBroadcast(android.content.pm.PackageInstaller.SessionInfo sessionInfo, int i) {
        this.mBroadcastHelper.sendSessionCommitBroadcast(snapshotComputer(), sessionInfo, i, this.mAppPredictionServicePackage);
    }

    @android.annotation.Nullable
    private java.lang.String getSetupWizardPackageNameImpl(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.SETUP_WIZARD");
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(intent, null, 1835520L, android.os.UserHandle.myUserId());
        if (queryIntentActivitiesInternal.size() == 1) {
            return queryIntentActivitiesInternal.get(0).getComponentInfo().packageName;
        }
        android.util.Slog.e(TAG, "There should probably be exactly one setup wizard; found " + queryIntentActivitiesInternal.size() + ": matches=" + queryIntentActivitiesInternal);
        return null;
    }

    @android.annotation.Nullable
    private java.lang.String getStorageManagerPackageName(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = computer.queryIntentActivitiesInternal(new android.content.Intent("android.os.storage.action.MANAGE_STORAGE"), null, 1835520L, android.os.UserHandle.myUserId());
        if (queryIntentActivitiesInternal.size() == 1) {
            return queryIntentActivitiesInternal.get(0).getComponentInfo().packageName;
        }
        android.util.Slog.w(TAG, "There should probably be exactly one storage manager; found " + queryIntentActivitiesInternal.size() + ": matches=" + queryIntentActivitiesInternal);
        return null;
    }

    @android.annotation.NonNull
    private static java.lang.String getRequiredSdkSandboxPackageName(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentServicesInternal = computer.queryIntentServicesInternal(new android.content.Intent("com.android.sdksandbox.SdkSandboxService"), null, 1835008L, 0, android.os.Process.myUid(), false);
        if (queryIntentServicesInternal.size() == 1) {
            return queryIntentServicesInternal.get(0).getComponentInfo().packageName;
        }
        throw new java.lang.RuntimeException("There should exactly one sdk sandbox package; found " + queryIntentServicesInternal.size() + ": matches=" + queryIntentServicesInternal);
    }

    @android.annotation.Nullable
    private java.lang.String getRetailDemoPackageName() {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        android.content.pm.SigningDetails signingDetails;
        java.lang.String string = this.mContext.getString(android.R.string.config_pointing_ui_class);
        java.lang.String string2 = this.mContext.getString(android.R.string.config_pointing_ui_package);
        if (!android.text.TextUtils.isEmpty(string) && !android.text.TextUtils.isEmpty(string2) && (androidPackage = this.mPackages.get(string)) != null && (signingDetails = androidPackage.getSigningDetails()) != null && signingDetails.getSignatures() != null) {
            try {
                java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
                for (android.content.pm.Signature signature : signingDetails.getSignatures()) {
                    if (android.text.TextUtils.equals(string2, libcore.util.HexEncoding.encodeToString(messageDigest.digest(signature.toByteArray()), false))) {
                        return string;
                    }
                }
            } catch (java.security.NoSuchAlgorithmException e) {
                android.util.Slog.e(TAG, "Unable to verify signatures as getting the retail demo package name", e);
            }
        }
        return null;
    }

    @android.annotation.Nullable
    java.lang.String getPackageFromComponentString(int i) {
        android.content.ComponentName unflattenFromString;
        java.lang.String string = this.mContext.getString(i);
        if (android.text.TextUtils.isEmpty(string) || (unflattenFromString = android.content.ComponentName.unflattenFromString(string)) == null) {
            return null;
        }
        return unflattenFromString.getPackageName();
    }

    @android.annotation.Nullable
    java.lang.String ensureSystemPackageName(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            return null;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (computer.getPackageInfo(str, 2097152L, 0) != null) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return str;
            }
            android.content.pm.PackageInfo packageInfo = computer.getPackageInfo(str, 0L, 0);
            if (packageInfo != null) {
                android.util.EventLog.writeEvent(1397638484, "145981139", java.lang.Integer.valueOf(packageInfo.applicationInfo.uid), "");
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Missing required system package: ");
            sb.append(str);
            sb.append(packageInfo != null ? ", but found with extended search." : ".");
            android.util.Log.w(TAG, sb.toString());
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public void updateComponentLabelIcon(final android.content.ComponentName componentName, @android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final java.lang.Integer num, final int i) {
        if (componentName == null) {
            throw new java.lang.IllegalArgumentException("Must specify a component");
        }
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String packageName = componentName.getPackageName();
        com.android.server.pm.Computer snapshotComputer = snapshotComputer();
        if (!android.os.UserHandle.isSameApp(callingUid, snapshotComputer.getPackageUid(packageName, 0L, i))) {
            throw new java.lang.SecurityException("The calling UID (" + callingUid + ") does not match the target UID");
        }
        java.lang.String string = this.mContext.getString(android.R.string.config_mms_user_agent);
        if (android.text.TextUtils.isEmpty(string)) {
            throw new java.lang.SecurityException("There is no package defined as allowed to change a component's label or icon");
        }
        int packageUid = snapshotComputer.getPackageUid(string, 1048576L, i);
        if (packageUid == -1 || !android.os.UserHandle.isSameApp(callingUid, packageUid)) {
            throw new java.lang.SecurityException("The calling UID (" + callingUid + ") is not allowed to change a component's label or icon");
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(packageName);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null || (!packageStateInternal.isSystem() && !packageStateInternal.isUpdatedSystemApp())) {
            throw new java.lang.SecurityException("Changing the label is not allowed for " + componentName);
        }
        if (!snapshotComputer.getComponentResolver().componentExists(componentName)) {
            throw new java.lang.IllegalArgumentException("Component " + componentName + " not found");
        }
        android.util.Pair<java.lang.String, java.lang.Integer> overrideLabelIconForComponent = packageStateInternal.getUserStateOrDefault(i).getOverrideLabelIconForComponent(componentName);
        java.lang.String str2 = overrideLabelIconForComponent == null ? null : (java.lang.String) overrideLabelIconForComponent.first;
        java.lang.Integer num2 = overrideLabelIconForComponent == null ? null : (java.lang.Integer) overrideLabelIconForComponent.second;
        if (!android.text.TextUtils.equals(str2, str) || !java.util.Objects.equals(num2, num)) {
            commitPackageStateMutation(null, packageName, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PackageManagerService.lambda$updateComponentLabelIcon$52(i, componentName, str, num, (com.android.server.pm.pkg.mutate.PackageStateWrite) obj);
                }
            });
            this.mPendingBroadcasts.addComponent(i, packageName, componentName.getClassName());
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.sendEmptyMessageDelayed(1, 1000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateComponentLabelIcon$52(int i, android.content.ComponentName componentName, java.lang.String str, java.lang.Integer num, com.android.server.pm.pkg.mutate.PackageStateWrite packageStateWrite) {
        packageStateWrite.userState(i).setComponentLabelIcon(componentName, str, num);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Finally extract failed */
    public void setEnabledSettings(java.util.List<android.content.pm.PackageManager.ComponentEnabledSetting> list, int i, @android.annotation.NonNull java.lang.String str) {
        long j;
        int i2;
        com.android.server.pm.Computer computer;
        char c;
        int callingUid = android.os.Binder.getCallingUid();
        snapshotComputer().enforceCrossUserPermission(callingUid, i, false, true, "set enabled");
        int size = list.size();
        int i3 = 0;
        while (true) {
            char c2 = 3;
            if (i3 < size) {
                int enabledState = list.get(i3).getEnabledState();
                if (list.get(i3).isComponent() && this.mDisabledComponentsList.contains(list.get(i3).getComponentName())) {
                    android.util.Slog.d(TAG, "Ignoring attempt to set enabled state of disabled component " + list.get(i3).getComponentName().flattenToString());
                    return;
                }
                if (enabledState == 0 || enabledState == 1 || enabledState == 2 || enabledState == 3 || enabledState == 4) {
                    i3++;
                } else {
                    throw new java.lang.IllegalArgumentException("Invalid new component state: " + enabledState);
                }
            } else {
                if (size > 1) {
                    android.util.ArraySet arraySet = new android.util.ArraySet();
                    android.util.ArraySet arraySet2 = new android.util.ArraySet();
                    android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                    for (int i4 = 0; i4 < size; i4++) {
                        android.content.pm.PackageManager.ComponentEnabledSetting componentEnabledSetting = list.get(i4);
                        java.lang.String packageName = componentEnabledSetting.getPackageName();
                        if (componentEnabledSetting.isComponent()) {
                            android.content.ComponentName componentName = componentEnabledSetting.getComponentName();
                            if (arraySet2.contains(componentName)) {
                                throw new java.lang.IllegalArgumentException("The component " + componentName + " is duplicated");
                            }
                            arraySet2.add(componentName);
                            java.lang.Integer num = (java.lang.Integer) arrayMap.get(packageName);
                            if (num == null) {
                                arrayMap.put(packageName, java.lang.Integer.valueOf(componentEnabledSetting.getEnabledFlags()));
                            } else if ((num.intValue() & 1) != (componentEnabledSetting.getEnabledFlags() & 1)) {
                                throw new java.lang.IllegalArgumentException("A conflict of the DONT_KILL_APP flag between components in the package " + packageName);
                            }
                        } else {
                            if (arraySet.contains(packageName)) {
                                throw new java.lang.IllegalArgumentException("The package " + packageName + " is duplicated");
                            }
                            arraySet.add(packageName);
                        }
                    }
                }
                boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.CHANGE_COMPONENT_ENABLED_STATE") == 0;
                boolean[] zArr = new boolean[size];
                java.util.Arrays.fill(zArr, true);
                android.util.ArrayMap arrayMap2 = new android.util.ArrayMap(size);
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.Computer snapshotComputer = snapshotComputer();
                        int i5 = 0;
                        while (i5 < size) {
                            android.content.pm.PackageManager.ComponentEnabledSetting componentEnabledSetting2 = list.get(i5);
                            java.lang.String packageName2 = componentEnabledSetting2.getPackageName();
                            if (arrayMap2.containsKey(packageName2)) {
                                computer = snapshotComputer;
                                c = c2;
                            } else {
                                boolean contains = com.android.internal.util.ArrayUtils.contains(snapshotComputer.getPackagesForUid(callingUid), packageName2);
                                com.android.server.pm.PackageSetting packageLPr = this.mSettings.getPackageLPr(packageName2);
                                if (!contains && !z) {
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                    sb.append("Attempt to change component state; pid=");
                                    sb.append(android.os.Binder.getCallingPid());
                                    sb.append(", uid=");
                                    sb.append(callingUid);
                                    sb.append(componentEnabledSetting2.isComponent() ? ", component=" + componentEnabledSetting2.getComponentName() : ", package=" + packageName2);
                                    throw new java.lang.SecurityException(sb.toString());
                                }
                                if (packageLPr == null || snapshotComputer.shouldFilterApplicationIncludingUninstalled(packageLPr, callingUid, i)) {
                                    throw new java.lang.IllegalArgumentException(componentEnabledSetting2.isComponent() ? "Unknown component: " + componentEnabledSetting2.getComponentName() : "Unknown package: " + packageName2);
                                }
                                if (!contains && this.mProtectedPackages.isPackageStateProtected(i, packageName2)) {
                                    throw new java.lang.SecurityException("Cannot disable a protected package: " + packageName2);
                                }
                                if (callingUid != 2000) {
                                    computer = snapshotComputer;
                                    c = c2;
                                } else if ((packageLPr.getFlags() & 256) != 0) {
                                    computer = snapshotComputer;
                                    c = c2;
                                } else {
                                    int enabled = packageLPr.getEnabled(i);
                                    int enabledState2 = componentEnabledSetting2.getEnabledState();
                                    if (!componentEnabledSetting2.isComponent()) {
                                        computer = snapshotComputer;
                                        if (enabled == 3 || enabled == 0 || enabled == 1) {
                                            c = 3;
                                            if (enabledState2 == 3 || enabledState2 == 0 || enabledState2 == 1) {
                                            }
                                        }
                                    }
                                    throw new java.lang.SecurityException("Shell cannot change component state for " + componentEnabledSetting2.getComponentName() + " to " + enabledState2);
                                }
                                arrayMap2.put(packageName2, packageLPr);
                            }
                            i5++;
                            c2 = c;
                            snapshotComputer = computer;
                        }
                        for (int i6 = 0; i6 < size; i6++) {
                            android.content.pm.PackageManager.ComponentEnabledSetting componentEnabledSetting3 = list.get(i6);
                            if (componentEnabledSetting3.isComponent()) {
                                java.lang.String packageName3 = componentEnabledSetting3.getPackageName();
                                java.lang.String className = componentEnabledSetting3.getClassName();
                                if (!z && android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(className)) {
                                    throw new java.lang.SecurityException("Cannot disable a system-generated component");
                                }
                                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = ((com.android.server.pm.PackageSetting) arrayMap2.get(packageName3)).getPkg();
                                if (pkg == null || !com.android.server.pm.parsing.pkg.AndroidPackageUtils.hasComponentClassName(pkg, className)) {
                                    if (pkg != null && pkg.getTargetSdkVersion() >= 16) {
                                        throw new java.lang.IllegalArgumentException("Component class " + className + " does not exist in " + packageName3);
                                    }
                                    android.util.Slog.w(TAG, "Failed setComponentEnabledSetting: component class " + className + " does not exist in " + packageName3);
                                    zArr[i6] = false;
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
                resetPriorityAfterPackageManagerTracedLockedSection();
                for (int i7 = 0; i7 < size; i7++) {
                    android.content.pm.PackageManager.ComponentEnabledSetting componentEnabledSetting4 = list.get(i7);
                    if (!componentEnabledSetting4.isComponent()) {
                        com.android.server.pm.PackageSetting packageSetting = (com.android.server.pm.PackageSetting) arrayMap2.get(componentEnabledSetting4.getPackageName());
                        int enabledState3 = componentEnabledSetting4.getEnabledState();
                        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
                        boostPriorityForPackageManagerTracedLockedSection();
                        synchronized (packageManagerTracedLock2) {
                            try {
                                if (packageSetting.getEnabled(i) == enabledState3) {
                                    zArr[i7] = false;
                                    resetPriorityAfterPackageManagerTracedLockedSection();
                                } else {
                                    resetPriorityAfterPackageManagerTracedLockedSection();
                                    com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg2 = packageSetting.getPkg();
                                    if ((pkg2 != null && pkg2.isStub() && packageSetting.isSystem()) && ((enabledState3 == 0 || enabledState3 == 1) && !enableCompressedPackage(pkg2, packageSetting))) {
                                        android.util.Slog.w(TAG, "Failed setApplicationEnabledSetting: failed to enable commpressed package " + componentEnabledSetting4.getPackageName());
                                        zArr[i7] = false;
                                    }
                                }
                            } finally {
                                resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                    }
                }
                android.util.ArrayMap arrayMap3 = new android.util.ArrayMap(size);
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mLock;
                boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock3) {
                    try {
                        com.android.server.pm.Computer snapshotComputer2 = snapshotComputer();
                        boolean z2 = false;
                        boolean z3 = false;
                        boolean z4 = false;
                        for (int i8 = 0; i8 < size; i8 = i2 + 1) {
                            if (!zArr[i8]) {
                                i2 = i8;
                            } else {
                                android.content.pm.PackageManager.ComponentEnabledSetting componentEnabledSetting5 = list.get(i8);
                                java.lang.String packageName4 = componentEnabledSetting5.getPackageName();
                                i2 = i8;
                                if (setEnabledSettingInternalLocked(snapshotComputer2, (com.android.server.pm.PackageSetting) arrayMap2.get(packageName4), componentEnabledSetting5, i, str)) {
                                    if ((componentEnabledSetting5.getEnabledFlags() & 2) != 0) {
                                        z3 = true;
                                    }
                                    java.lang.String className2 = componentEnabledSetting5.isComponent() ? componentEnabledSetting5.getClassName() : packageName4;
                                    if ((componentEnabledSetting5.getEnabledFlags() & 1) == 0) {
                                        java.util.ArrayList arrayList = (java.util.ArrayList) arrayMap3.get(packageName4);
                                        if (arrayList == null) {
                                            arrayList = new java.util.ArrayList();
                                        }
                                        if (!arrayList.contains(className2)) {
                                            arrayList.add(className2);
                                        }
                                        arrayMap3.put(packageName4, arrayList);
                                        this.mPendingBroadcasts.remove(i, packageName4);
                                        z2 = true;
                                    } else {
                                        this.mPendingBroadcasts.addComponent(i, packageName4, className2);
                                        z2 = true;
                                        z4 = true;
                                    }
                                }
                            }
                        }
                        if (z2) {
                            if (z3) {
                                flushPackageRestrictionsAsUserInternalLocked(i);
                            } else {
                                scheduleWritePackageRestrictions(i);
                            }
                            if (z4 && !this.mHandler.hasMessages(1)) {
                                if (android.os.SystemClock.uptimeMillis() > this.mServiceStartWithDelay) {
                                    j = 1000;
                                } else {
                                    j = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
                                }
                                this.mHandler.sendEmptyMessageDelayed(1, j);
                            }
                            resetPriorityAfterPackageManagerTracedLockedSection();
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                com.android.server.pm.Computer snapshotComputer3 = snapshotComputer();
                                for (int i9 = 0; i9 < arrayMap3.size(); i9++) {
                                    java.lang.String str2 = (java.lang.String) arrayMap3.keyAt(i9);
                                    this.mBroadcastHelper.sendPackageChangedBroadcast(snapshotComputer3, str2, false, (java.util.ArrayList) arrayMap3.valueAt(i9), android.os.UserHandle.getUid(i, ((com.android.server.pm.PackageSetting) arrayMap2.get(str2)).getAppId()), null);
                                }
                                return;
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        return;
                    } catch (java.lang.Throwable th2) {
                        resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th2;
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean setEnabledSettingInternalLocked(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.pm.PackageSetting packageSetting, android.content.pm.PackageManager.ComponentEnabledSetting componentEnabledSetting, int i, java.lang.String str) {
        boolean restoreComponentLPw;
        int enabledState = componentEnabledSetting.getEnabledState();
        java.lang.String packageName = componentEnabledSetting.getPackageName();
        if (!componentEnabledSetting.isComponent()) {
            packageSetting.setEnabled(enabledState, i, str);
            if ((enabledState == 3 || enabledState == 2) && checkPermission("android.permission.SUSPEND_APPS", packageName, i) == 0) {
                unsuspendForSuspendingPackage(computer, packageName, i);
                removeAllDistractingPackageRestrictions(computer, i);
            }
            restoreComponentLPw = true;
        } else {
            java.lang.String className = componentEnabledSetting.getClassName();
            switch (enabledState) {
                case 0:
                    restoreComponentLPw = packageSetting.restoreComponentLPw(className, i);
                    break;
                case 1:
                    restoreComponentLPw = packageSetting.enableComponentLPw(className, i);
                    break;
                case 2:
                    restoreComponentLPw = packageSetting.disableComponentLPw(className, i);
                    break;
                default:
                    android.util.Slog.e(TAG, "Failed setComponentEnabledSetting: component " + packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + className + " requested an invalid new component state: " + enabledState);
                    restoreComponentLPw = false;
                    break;
            }
        }
        if (!restoreComponentLPw) {
            return false;
        }
        updateSequenceNumberLP(packageSetting, new int[]{i});
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            updateInstantAppInstallerLocked(packageName);
            return true;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void flushPackageRestrictionsAsUserInternalLocked(int i) {
        this.mSettings.writePackageRestrictionsLPr(i);
        synchronized (this.mDirtyUsers) {
            try {
                this.mDirtyUsers.remove(java.lang.Integer.valueOf(i));
                if (this.mDirtyUsers.isEmpty()) {
                    this.mBackgroundHandler.removeMessages(14);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void waitForAppDataPrepared() {
        if (this.mPrepareAppDataFuture == null) {
            return;
        }
        com.android.internal.util.ConcurrentUtils.waitForFutureNoInterrupt(this.mPrepareAppDataFuture, "wait for prepareAppData");
        this.mPrepareAppDataFuture = null;
    }

    public void systemReady() {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can claim the system is ready");
        final android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mReleaseOnSystemReady != null) {
            for (int size = this.mReleaseOnSystemReady.size() - 1; size >= 0; size--) {
                com.android.internal.content.F2fsUtils.releaseCompressedBlocks(contentResolver, this.mReleaseOnSystemReady.get(size));
            }
            this.mReleaseOnSystemReady = null;
        }
        this.mSystemReady = true;
        android.database.ContentObserver contentObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.pm.PackageManagerService.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                boolean z2 = android.provider.Settings.Global.getInt(contentResolver, "enable_ephemeral_feature", 1) == 0;
                for (int i : com.android.server.pm.UserManagerService.getInstance().getUserIds()) {
                    com.android.server.pm.PackageManagerService.this.mWebInstantAppsDisabled.put(i, z2 || android.provider.Settings.Secure.getIntForUser(contentResolver, "instant_apps_enabled", 1, i) == 0);
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("enable_ephemeral_feature"), false, contentObserver, -1);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("instant_apps_enabled"), false, contentObserver, -1);
        contentObserver.onChange(true);
        this.mAppsFilter.onSystemReady((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class));
        com.android.internal.telephony.CarrierAppUtils.disableCarrierAppsUntilPrivileged(this.mContext.getOpPackageName(), 0, this.mContext);
        disableSkuSpecificApps();
        com.android.internal.pm.pkg.parsing.ParsingPackageUtils.setCompatibilityModeEnabled(android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "compatibility_mode", 1) == 1);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                java.util.ArrayList<java.lang.Integer> systemReady = this.mSettings.systemReady(this.mComponentResolver);
                for (int i = 0; i < systemReady.size(); i++) {
                    this.mSettings.writePackageRestrictionsLPr(systemReady.get(i).intValue());
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
        this.mUserManager.systemReady();
        ((android.os.storage.StorageManager) this.mInjector.getSystemService(android.os.storage.StorageManager.class)).registerListener(this.mStorageEventHelper);
        this.mInstallerService.systemReady();
        this.mPackageDexOptimizer.systemReady();
        this.mUserManager.reconcileUsers(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        this.mStorageEventHelper.reconcileApps(snapshotComputer(), android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        this.mPermissionManager.onSystemReady();
        int[] iArr = EMPTY_INT_ARRAY;
        java.util.List<android.content.pm.UserInfo> users = this.mInjector.getUserManagerInternal().getUsers(true, true, false);
        int size2 = users.size();
        for (int i2 = 0; i2 < size2; i2++) {
            int i3 = users.get(i2).id;
            if (!java.util.Objects.equals(this.mPermissionManager.getDefaultPermissionGrantFingerprint(i3), android.os.Build.FINGERPRINT)) {
                iArr = com.android.internal.util.ArrayUtils.appendInt(iArr, i3);
            }
        }
        for (int i4 : iArr) {
            this.mLegacyPermissionManager.grantDefaultPermissions(i4);
            this.mPermissionManager.setDefaultPermissionGrantFingerprint(android.os.Build.FINGERPRINT, i4);
        }
        if (iArr == EMPTY_INT_ARRAY) {
            this.mLegacyPermissionManager.scheduleReadDefaultPermissionExceptions();
        }
        if (this.mInstantAppResolverConnection != null) {
            this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.pm.PackageManagerService.5
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    com.android.server.pm.PackageManagerService.this.mInstantAppResolverConnection.optimisticBind();
                    com.android.server.pm.PackageManagerService.this.mContext.unregisterReceiver(this);
                }
            }, new android.content.IntentFilter("android.intent.action.BOOT_COMPLETED"));
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.OVERLAY_CHANGED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.pm.PackageManagerService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                android.net.Uri data;
                java.lang.String schemeSpecificPart;
                com.android.server.pm.Computer snapshotComputer;
                com.android.server.pm.pkg.AndroidPackage androidPackage;
                if (intent == null || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null || (androidPackage = (snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer()).getPackage(schemeSpecificPart)) == null) {
                    return;
                }
                com.android.server.pm.PackageManagerService.this.mBroadcastHelper.sendPackageChangedBroadcast(snapshotComputer, androidPackage.getPackageName(), true, new java.util.ArrayList<>(java.util.Collections.singletonList(androidPackage.getPackageName())), androidPackage.getUid(), "android.intent.action.OVERLAY_CHANGED");
            }
        }, intentFilter);
        this.mModuleInfoProvider.systemReady();
        this.mInstallerService.restoreAndApplyStagedSessionIfNeeded();
        this.mExistingPackages = null;
        android.provider.DeviceConfig.addOnPropertiesChangedListener("package_manager_service", this.mInjector.getBackgroundExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda12
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.pm.PackageManagerService.this.lambda$systemReady$53(properties);
            }
        });
        if (!com.android.server.pm.DexOptHelper.useArtService()) {
            try {
                this.mBackgroundDexOptService.systemReady();
            } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        schedulePruneUnusedStaticSharedLibraries(false);
        com.android.server.art.DexUseManagerLocal dexUseManagerLocal = com.android.server.pm.DexOptHelper.getDexUseManagerLocal();
        if (dexUseManagerLocal != null) {
            dexUseManagerLocal.systemReady();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$53(android.provider.DeviceConfig.Properties properties) {
        java.util.Set keyset = properties.getKeyset();
        if (keyset.contains(PROPERTY_INCFS_DEFAULT_TIMEOUTS) || keyset.contains(PROPERTY_KNOWN_DIGESTERS_LIST)) {
            this.mPerUidReadTimeoutsCache = null;
        }
    }

    private void disableSkuSpecificApps() {
        java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.config_device_state_postures);
        java.lang.String[] stringArray2 = this.mContext.getResources().getStringArray(android.R.array.config_deviceTabletopRotations);
        if (com.android.internal.util.ArrayUtils.isEmpty(stringArray)) {
            return;
        }
        java.lang.String str = android.os.SystemProperties.get("ro.boot.hardware.sku");
        if (!android.text.TextUtils.isEmpty(str) && com.android.internal.util.ArrayUtils.contains(stringArray2, str)) {
            return;
        }
        com.android.server.pm.Computer snapshotComputer = snapshotComputer();
        for (java.lang.String str2 : stringArray) {
            setSystemAppHiddenUntilInstalled(snapshotComputer, str2, true);
            java.util.List<android.content.pm.UserInfo> users = this.mInjector.getUserManagerInternal().getUsers(false);
            for (int i = 0; i < users.size(); i++) {
                setSystemAppInstallState(snapshotComputer, str2, false, users.get(i).id);
            }
        }
    }

    public com.android.server.pm.PackageFreezer freezePackage(java.lang.String str, int i, java.lang.String str2, int i2, com.android.server.pm.InstallRequest installRequest) {
        return new com.android.server.pm.PackageFreezer(str, i, str2, this, i2, installRequest);
    }

    public com.android.server.pm.PackageFreezer freezePackageForDelete(java.lang.String str, int i, int i2, java.lang.String str2, int i3) {
        if ((i2 & 8) != 0) {
            return new com.android.server.pm.PackageFreezer(this, null);
        }
        return freezePackage(str, i, str2, i3, null);
    }

    void cleanUpUser(com.android.server.pm.UserManagerService userManagerService, int i) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                synchronized (this.mDirtyUsers) {
                    this.mDirtyUsers.remove(java.lang.Integer.valueOf(i));
                }
                this.mUserNeedsBadging.delete(i);
                this.mDeletePackageHelper.removeUnusedPackagesLPw(userManagerService, i);
                this.mSettings.removeUserLPw(i);
                this.mPendingBroadcasts.remove(i);
                this.mAppsFilter.onUserDeleted(snapshotComputer(), i);
                this.mPermissionManager.onUserRemoved(i);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
        this.mInstantAppRegistry.onUserRemoved(i);
        this.mPackageMonitorCallbackHelper.onUserRemoved(i);
    }

    void createNewUser(int i, @android.annotation.Nullable java.util.Set<java.lang.String> set, java.lang.String[] strArr) {
        synchronized (this.mInstallLock) {
            this.mSettings.createNewUserLI(this, this.mInstaller, i, set, strArr);
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                scheduleWritePackageRestrictions(i);
                scheduleWritePackageList(i);
                this.mAppsFilter.onUserCreated(snapshotComputer(), i);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
    }

    void onNewUserCreated(int i, boolean z) {
        if (!z || !readPermissionStateForUser(i)) {
            this.mPermissionManager.onUserCreated(i);
            this.mLegacyPermissionManager.grantDefaultPermissions(i);
            this.mPermissionManager.setDefaultPermissionGrantFingerprint(android.os.Build.FINGERPRINT, i);
            this.mDomainVerificationManager.clearUser(i);
        }
    }

    private boolean readPermissionStateForUser(int i) {
        boolean z;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPermissionManager.writeLegacyPermissionStateTEMP();
                this.mSettings.readPermissionStateForUserSyncLPr(i);
                this.mPermissionManager.readLegacyPermissionStateTEMP();
                z = !java.util.Objects.equals(this.mPermissionManager.getDefaultPermissionGrantFingerprint(i), android.os.Build.FINGERPRINT);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
        return z;
    }

    public boolean isStorageLow() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.storage.DeviceStorageMonitorInternal deviceStorageMonitorInternal = (com.android.server.storage.DeviceStorageMonitorInternal) this.mInjector.getLocalService(com.android.server.storage.DeviceStorageMonitorInternal.class);
            if (deviceStorageMonitorInternal != null) {
                return deviceStorageMonitorInternal.isMemoryLow();
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void deletePackageIfUnused(@android.annotation.NonNull com.android.server.pm.Computer computer, final java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return;
        }
        android.util.SparseArray<? extends com.android.server.pm.pkg.PackageUserStateInternal> userStates = packageStateInternal.getUserStates();
        for (int i = 0; i < userStates.size(); i++) {
            if (userStates.valueAt(i).isInstalled()) {
                return;
            }
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageManagerService.this.lambda$deletePackageIfUnused$54(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deletePackageIfUnused$54(java.lang.String str) {
        this.mDeletePackageHelper.deletePackageX(str, -1L, 0, 2, true);
    }

    void deletePreloadsFileCache() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CLEAR_APP_CACHE", "deletePreloadsFileCache");
        java.io.File dataPreloadsFileCacheDirectory = android.os.Environment.getDataPreloadsFileCacheDirectory();
        android.util.Slog.i(TAG, "Deleting preloaded file cache " + dataPreloadsFileCacheDirectory);
        android.os.FileUtils.deleteContents(dataPreloadsFileCacheDirectory);
    }

    void setSystemAppHiddenUntilInstalled(@android.annotation.NonNull com.android.server.pm.Computer computer, final java.lang.String str, final boolean z) {
        int callingUid = android.os.Binder.getCallingUid();
        boolean z2 = callingUid == 1001 || callingUid == 1000;
        if (!z2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setSystemAppHiddenUntilInstalled");
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || !packageStateInternal.isSystem() || packageStateInternal.getPkg() == null) {
            return;
        }
        if (packageStateInternal.getPkg().isCoreApp() && !z2) {
            throw new java.lang.SecurityException("Only system or phone callers can modify core apps");
        }
        commitPackageStateMutation(null, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda66
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.PackageManagerService.lambda$setSystemAppHiddenUntilInstalled$55(str, z, (com.android.server.pm.pkg.mutate.PackageStateMutator) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setSystemAppHiddenUntilInstalled$55(java.lang.String str, boolean z, com.android.server.pm.pkg.mutate.PackageStateMutator packageStateMutator) {
        packageStateMutator.forPackage(str).setHiddenUntilInstalled(z);
        packageStateMutator.forDisabledSystemPackage(str).setHiddenUntilInstalled(z);
    }

    boolean setSystemAppInstallState(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, boolean z, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        boolean z2 = callingUid == 1001 || callingUid == 1000;
        if (!z2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "setSystemAppHiddenUntilInstalled");
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || !packageStateInternal.isSystem() || packageStateInternal.getPkg() == null) {
            return false;
        }
        if (packageStateInternal.getPkg().isCoreApp() && !z2) {
            throw new java.lang.SecurityException("Only system or phone callers can modify core apps");
        }
        if (packageStateInternal.getUserStateOrDefault(i).isInstalled() == z) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (z) {
                this.mInstallPackageHelper.installExistingPackageAsUser(str, i, 4194304, 3, null, null);
                return true;
            }
            deletePackageVersioned(new android.content.pm.VersionedPackage(str, -1), new android.content.pm.PackageManager.LegacyPackageDeleteObserver((android.content.pm.IPackageDeleteObserver) null).getBinder(), i, 4);
            return true;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void finishPackageInstall(int i, boolean z) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("Only the system is allowed to finish installs");
        android.os.Trace.asyncTraceEnd(262144L, "restore", i);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9, i, z ? 1 : 0));
    }

    void checkPackageStartable(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (computer.getInstantAppPackageName(callingUid) != null) {
            throw new java.lang.SecurityException("Instant applications don't have access to this method");
        }
        if (!this.mUserManager.exists(i)) {
            throw new java.lang.SecurityException("User doesn't exist");
        }
        computer.enforceCrossUserPermission(callingUid, i, false, false, "checkPackageStartable");
        switch (computer.getPackageStartability(this.mSafeMode, str, callingUid, i)) {
            case 1:
                throw new java.lang.SecurityException("Package " + str + " was not found!");
            case 2:
                throw new java.lang.SecurityException("Package " + str + " not a system app!");
            case 3:
                throw new java.lang.SecurityException("Package " + str + " is currently frozen!");
            case 4:
                throw new java.lang.SecurityException("Package " + str + " is not encryption aware!");
            default:
                return;
        }
    }

    void setPackageStoppedState(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull final java.lang.String str, final boolean z, final int i) {
        java.lang.String str2;
        if (this.mUserManager.exists(i)) {
            int callingUid = android.os.Binder.getCallingUid();
            boolean z2 = false;
            if (computer.getInstantAppPackageName(callingUid) == null) {
                if (!(this.mContext.checkCallingOrSelfPermission("android.permission.CHANGE_COMPONENT_ENABLED_STATE") == 0) && !com.android.internal.util.ArrayUtils.contains(computer.getPackagesForUid(callingUid), str)) {
                    throw new java.lang.SecurityException("Permission Denial: attempt to change stopped state from pid=" + android.os.Binder.getCallingPid() + ", uid=" + callingUid + ", package=" + str);
                }
                computer.enforceCrossUserPermission(callingUid, i, true, true, "stop package");
                com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(str, callingUid, i);
                com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateForInstalledAndFiltered == null ? null : packageStateForInstalledAndFiltered.getUserStateOrDefault(i);
                if (packageStateForInstalledAndFiltered != null && userStateOrDefault.isStopped() != z) {
                    final boolean isNotLaunched = userStateOrDefault.isNotLaunched();
                    z2 = userStateOrDefault.isStopped();
                    commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda17
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.PackageManagerService.lambda$setPackageStoppedState$56(i, z, isNotLaunched, (com.android.server.pm.pkg.mutate.PackageStateWrite) obj);
                        }
                    });
                    if (isNotLaunched && (str2 = packageStateForInstalledAndFiltered.getInstallSource().mInstallerPackageName) != null) {
                        notifyFirstLaunch(str, str2, i);
                    }
                    scheduleWritePackageRestrictions(i);
                }
            }
            if (!z) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.PackageManagerService.this.lambda$setPackageStoppedState$57(str, i);
                    }
                });
                if (z2 && android.content.pm.Flags.stayStopped()) {
                    android.os.Trace.traceBegin(262144L, "unstoppedBroadcast");
                    android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) this.mInjector.getLocalService(android.content.pm.PackageManagerInternal.class);
                    final int[] resolveUserIds = resolveUserIds(i);
                    final android.util.SparseArray<int[]> visibilityAllowLists = snapshotComputer().getVisibilityAllowLists(str, resolveUserIds);
                    final android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putInt("android.intent.extra.UID", packageManagerInternal.getPackageUid(str, 0L, i));
                    bundle.putInt("android.intent.extra.user_handle", i);
                    bundle.putLong("android.intent.extra.TIME", android.os.SystemClock.elapsedRealtime());
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda19
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.PackageManagerService.this.lambda$setPackageStoppedState$58(str, bundle, resolveUserIds, visibilityAllowLists);
                        }
                    });
                    this.mPackageMonitorCallbackHelper.notifyPackageMonitor("android.intent.action.PACKAGE_UNSTOPPED", str, bundle, resolveUserIds, null, visibilityAllowLists, this.mHandler, null);
                    android.os.Trace.traceEnd(262144L);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setPackageStoppedState$56(int i, boolean z, boolean z2, com.android.server.pm.pkg.mutate.PackageStateWrite packageStateWrite) {
        com.android.server.pm.pkg.mutate.PackageUserStateWrite userState = packageStateWrite.userState(i);
        userState.setStopped(z);
        if (z2) {
            userState.setNotLaunched(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPackageStoppedState$57(java.lang.String str, int i) {
        com.android.server.apphibernation.AppHibernationManagerInternal appHibernationManagerInternal = (com.android.server.apphibernation.AppHibernationManagerInternal) this.mInjector.getLocalService(com.android.server.apphibernation.AppHibernationManagerInternal.class);
        if (appHibernationManagerInternal != null && appHibernationManagerInternal.isHibernatingForUser(str, i)) {
            appHibernationManagerInternal.setHibernatingForUser(str, i, false);
            appHibernationManagerInternal.setHibernatingGlobally(str, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPackageStoppedState$58(java.lang.String str, android.os.Bundle bundle, int[] iArr, android.util.SparseArray sparseArray) {
        this.mBroadcastHelper.sendPackageBroadcast("android.intent.action.PACKAGE_UNSTOPPED", str, bundle, 1073741824, null, null, iArr, null, sparseArray, null, null);
    }

    void notifyComponentUsed(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.PackageSetting packageLPr = this.mSettings.getPackageLPr(str);
                if (packageLPr == null) {
                    resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                if (packageLPr.getUserStateOrDefault(i).isQuarantined()) {
                    android.util.Slog.i(TAG, "Component is quarantined+suspended but being used: " + str + " by " + str2 + ", debugInfo: " + str3);
                }
                resetPriorityAfterPackageManagerTracedLockedSection();
                setPackageStoppedState(computer, str, false, i);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    public class IPackageManagerImpl extends com.android.server.pm.IPackageManagerBase {
        public IPackageManagerImpl() {
            super(com.android.server.pm.PackageManagerService.this, com.android.server.pm.PackageManagerService.this.mContext, com.android.server.pm.PackageManagerService.this.mDexOptHelper, com.android.server.pm.PackageManagerService.this.mModuleInfoProvider, com.android.server.pm.PackageManagerService.this.mPreferredActivityHelper, com.android.server.pm.PackageManagerService.this.mResolveIntentHelper, com.android.server.pm.PackageManagerService.this.mDomainVerificationManager, com.android.server.pm.PackageManagerService.this.mDomainVerificationConnection, com.android.server.pm.PackageManagerService.this.mInstallerService, com.android.server.pm.PackageManagerService.this.mPackageProperty, com.android.server.pm.PackageManagerService.this.mResolveComponentName, com.android.server.pm.PackageManagerService.this.mInstantAppResolverSettingsComponent, com.android.server.pm.PackageManagerService.this.mServicesExtensionPackageName, com.android.server.pm.PackageManagerService.this.mSharedSystemSharedLibraryPackageName);
        }

        public void checkPackageStartable(java.lang.String str, int i) {
            com.android.server.pm.PackageManagerService.this.checkPackageStartable(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, i);
        }

        public void clearApplicationProfileData(java.lang.String str) {
            com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRootOrShell("Only the system or shell can clear all profile data");
            com.android.server.pm.pkg.AndroidPackage androidPackage = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackage(str);
            com.android.server.pm.PackageFreezer freezePackage = com.android.server.pm.PackageManagerService.this.freezePackage(str, -1, "clearApplicationProfileData", 13, null);
            try {
                synchronized (com.android.server.pm.PackageManagerService.this.mInstallLock) {
                    com.android.server.pm.PackageManagerService.this.mAppDataHelper.clearAppProfilesLIF(androidPackage);
                }
                if (freezePackage != null) {
                    freezePackage.close();
                }
            } catch (java.lang.Throwable th) {
                if (freezePackage != null) {
                    try {
                        freezePackage.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @android.annotation.EnforcePermission("android.permission.CLEAR_APP_USER_DATA")
        public void clearApplicationUserData(final java.lang.String str, final android.content.pm.IPackageDataObserver iPackageDataObserver, final int i) {
            clearApplicationUserData_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, true, false, "clear application data");
            if (snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, i) == null) {
                if (iPackageDataObserver != null) {
                    com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.PackageManagerService.IPackageManagerImpl.lambda$clearApplicationUserData$0(iPackageDataObserver, str);
                        }
                    });
                }
            } else {
                if (com.android.server.pm.PackageManagerService.this.mProtectedPackages.isPackageDataProtected(i, str)) {
                    throw new java.lang.SecurityException("Cannot clear data for a protected package: " + str);
                }
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.PM_CLEAR_APP_DATA_CALLER, java.lang.Integer.valueOf(android.os.Binder.getCallingPid()), java.lang.Integer.valueOf(callingUid), str);
                com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService.IPackageManagerImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean clearApplicationUserDataLIF;
                        com.android.server.pm.PackageManagerService.this.mHandler.removeCallbacks(this);
                        com.android.server.pm.PackageFreezer freezePackage = com.android.server.pm.PackageManagerService.this.freezePackage(str, -1, "clearApplicationUserData", 10, null);
                        try {
                            synchronized (com.android.server.pm.PackageManagerService.this.mInstallLock) {
                                clearApplicationUserDataLIF = com.android.server.pm.PackageManagerService.this.clearApplicationUserDataLIF(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, i);
                            }
                            com.android.server.pm.PackageManagerService.this.mInstantAppRegistry.deleteInstantApplicationMetadata(str, i);
                            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
                            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                            synchronized (packageManagerTracedLock) {
                                if (clearApplicationUserDataLIF) {
                                    try {
                                        com.android.server.pm.PackageManagerService.this.resetComponentEnabledSettingsIfNeededLPw(str, i);
                                    } catch (java.lang.Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            if (freezePackage != null) {
                                freezePackage.close();
                            }
                            if (clearApplicationUserDataLIF) {
                                com.android.server.storage.DeviceStorageMonitorInternal deviceStorageMonitorInternal = (com.android.server.storage.DeviceStorageMonitorInternal) com.android.server.LocalServices.getService(com.android.server.storage.DeviceStorageMonitorInternal.class);
                                if (deviceStorageMonitorInternal != null) {
                                    deviceStorageMonitorInternal.checkMemory();
                                }
                                if (com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.checkPermission("android.permission.SUSPEND_APPS", str, i) == 0) {
                                    com.android.server.pm.Computer snapshotComputer2 = com.android.server.pm.PackageManagerService.this.snapshotComputer();
                                    com.android.server.pm.PackageManagerService.this.unsuspendForSuspendingPackage(snapshotComputer2, str, i);
                                    com.android.server.pm.PackageManagerService.this.removeAllDistractingPackageRestrictions(snapshotComputer2, i);
                                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = com.android.server.pm.PackageManagerService.this.mLock;
                                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                                    synchronized (packageManagerTracedLock2) {
                                        try {
                                            com.android.server.pm.PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i);
                                        } finally {
                                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                        }
                                    }
                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                }
                            }
                            if (iPackageDataObserver != null) {
                                try {
                                    iPackageDataObserver.onRemoveCompleted(str, clearApplicationUserDataLIF);
                                } catch (android.os.RemoteException e) {
                                    android.util.Log.i(com.android.server.pm.PackageManagerService.TAG, "Observer no longer exists.");
                                }
                            }
                        } catch (java.lang.Throwable th2) {
                            if (freezePackage != null) {
                                try {
                                    freezePackage.close();
                                } catch (java.lang.Throwable th3) {
                                    th2.addSuppressed(th3);
                                }
                            }
                            throw th2;
                        }
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$clearApplicationUserData$0(android.content.pm.IPackageDataObserver iPackageDataObserver, java.lang.String str) {
            try {
                iPackageDataObserver.onRemoveCompleted(str, false);
            } catch (android.os.RemoteException e) {
                android.util.Log.i(com.android.server.pm.PackageManagerService.TAG, "Observer no longer exists.");
            }
        }

        @android.annotation.EnforcePermission("android.permission.INTERACT_ACROSS_USERS_FULL")
        public void clearCrossProfileIntentFilters(int i, java.lang.String str) {
            clearCrossProfileIntentFilters_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.PackageManagerService.this.enforceOwnerRights(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, callingUid);
            com.android.server.pm.PackageManagerServiceUtils.enforceShellRestriction(com.android.server.pm.PackageManagerService.this.mInjector.getUserManagerInternal(), "no_debugging_features", callingUid, i);
            com.android.server.pm.PackageManagerService.this.mInjector.getCrossProfileIntentFilterHelper().clearCrossProfileIntentFilters(i, str, null);
            com.android.server.pm.PackageManagerService.this.scheduleWritePackageRestrictions(i);
        }

        @android.annotation.EnforcePermission("android.permission.INTERACT_ACROSS_USERS_FULL")
        public boolean removeCrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) {
            removeCrossProfileIntentFilter_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.PackageManagerService.this.enforceOwnerRights(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, callingUid);
            boolean z = false;
            com.android.server.pm.PackageManagerService.this.mUserManager.enforceCrossProfileIntentFilterAccess(i, i2, callingUid, false);
            com.android.server.pm.PackageManagerServiceUtils.enforceShellRestriction(com.android.server.pm.PackageManagerService.this.mInjector.getUserManagerInternal(), "no_debugging_features", callingUid, i);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.CrossProfileIntentResolver editCrossProfileIntentResolverLPw = com.android.server.pm.PackageManagerService.this.mSettings.editCrossProfileIntentResolverLPw(i);
                    android.util.ArraySet arraySet = new android.util.ArraySet(editCrossProfileIntentResolverLPw.filterSet());
                    int i4 = 0;
                    while (true) {
                        if (i4 >= arraySet.size()) {
                            break;
                        }
                        com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = (com.android.server.pm.CrossProfileIntentFilter) arraySet.valueAt(i4);
                        if (!android.content.IntentFilter.filterEquals(crossProfileIntentFilter.mFilter, intentFilter) || !crossProfileIntentFilter.getOwnerPackage().equals(str) || crossProfileIntentFilter.getTargetUserId() != i2 || crossProfileIntentFilter.getFlags() != i3) {
                            i4++;
                        } else {
                            editCrossProfileIntentResolverLPw.removeFilter((com.android.server.pm.CrossProfileIntentResolver) crossProfileIntentFilter);
                            z = true;
                            break;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (z) {
                com.android.server.pm.PackageManagerService.this.scheduleWritePackageRestrictions(i);
            }
            return z;
        }

        public final void deleteApplicationCacheFiles(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) {
            deleteApplicationCacheFilesAsUser(str, android.os.UserHandle.getCallingUserId(), iPackageDataObserver);
        }

        public void deleteApplicationCacheFilesAsUser(final java.lang.String str, final int i, final android.content.pm.IPackageDataObserver iPackageDataObserver) {
            final int callingUid = android.os.Binder.getCallingUid();
            if (com.android.server.pm.PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.INTERNAL_DELETE_CACHE_FILES") != 0) {
                if (com.android.server.pm.PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_CACHE_FILES") == 0) {
                    android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Calling uid " + callingUid + " does not have android.permission.INTERNAL_DELETE_CACHE_FILES, silently ignoring");
                    return;
                }
                com.android.server.pm.PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERNAL_DELETE_CACHE_FILES", null);
            }
            com.android.server.pm.PackageManagerService.this.snapshotComputer().enforceCrossUserPermission(callingUid, i, true, false, "delete application cache files");
            final int checkCallingOrSelfPermission = com.android.server.pm.PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS");
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.PM_CLEAR_APP_DATA_CALLER, java.lang.Integer.valueOf(android.os.Binder.getCallingPid()), java.lang.Integer.valueOf(callingUid), str);
            com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$deleteApplicationCacheFilesAsUser$1(str, callingUid, checkCallingOrSelfPermission, i, iPackageDataObserver);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deleteApplicationCacheFilesAsUser$1(java.lang.String str, int i, int i2, int i3, android.content.pm.IPackageDataObserver iPackageDataObserver) {
            boolean z;
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                z = true;
            } else {
                z = !packageStateInternal.getUserStateOrDefault(android.os.UserHandle.getUserId(i)).isInstantApp() || i2 == 0;
            }
            if (z) {
                synchronized (com.android.server.pm.PackageManagerService.this.mInstallLock) {
                    com.android.server.pm.pkg.AndroidPackage androidPackage = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackage(str);
                    com.android.server.pm.PackageManagerService.this.mAppDataHelper.clearAppDataLIF(androidPackage, i3, 23);
                    com.android.server.pm.PackageManagerService.this.mAppDataHelper.clearAppDataLIF(androidPackage, i3, 39);
                }
            }
            if (iPackageDataObserver != null) {
                try {
                    iPackageDataObserver.onRemoveCompleted(str, true);
                } catch (android.os.RemoteException e) {
                    android.util.Log.i(com.android.server.pm.PackageManagerService.TAG, "Observer no longer exists.");
                }
            }
        }

        public void enterSafeMode() {
            com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request entering safe mode");
            if (!com.android.server.pm.PackageManagerService.this.mSystemReady) {
                com.android.server.pm.PackageManagerService.this.mSafeMode = true;
            }
        }

        public void extendVerificationTimeout(final int i, final int i2, final long j) {
            if (i >= 0) {
                com.android.server.pm.PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can extend verification timeouts");
            }
            final int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$extendVerificationTimeout$2(i, callingUid, i2, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$extendVerificationTimeout$2(int i, int i2, int i3, long j) {
            if (i < 0) {
                i = -i;
            }
            com.android.server.pm.PackageVerificationState packageVerificationState = com.android.server.pm.PackageManagerService.this.mPendingVerification.get(i);
            if (packageVerificationState == null || !packageVerificationState.extendTimeout(i2)) {
                return;
            }
            com.android.server.pm.PackageVerificationResponse packageVerificationResponse = new com.android.server.pm.PackageVerificationResponse(i3, i2);
            if (j > 3600000) {
                j = 3600000;
            }
            if (j < 0) {
                j = 0;
            }
            android.os.Message obtainMessage = com.android.server.pm.PackageManagerService.this.mHandler.obtainMessage(15);
            obtainMessage.arg1 = i;
            obtainMessage.obj = packageVerificationResponse;
            com.android.server.pm.PackageManagerService.this.mHandler.sendMessageDelayed(obtainMessage, j);
        }

        public void flushPackageRestrictionsAsUser(int i) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            int callingUid = android.os.Binder.getCallingUid();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null || !com.android.server.pm.PackageManagerService.this.mUserManager.exists(i)) {
                return;
            }
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, false, "flushPackageRestrictions");
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        @android.annotation.EnforcePermission("android.permission.CLEAR_APP_CACHE")
        public void freeStorage(final java.lang.String str, final long j, final int i, final android.content.IntentSender intentSender) {
            freeStorage_enforcePermission();
            com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$freeStorage$3(str, j, i, intentSender);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freeStorage$3(java.lang.String str, long j, int i, android.content.IntentSender intentSender) {
            boolean z;
            try {
                com.android.server.pm.PackageManagerService.this.freeStorage(str, j, i);
                z = true;
            } catch (java.io.IOException e) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, e);
                z = false;
            }
            if (intentSender != null) {
                try {
                    android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
                    makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                    intentSender.sendIntent(null, z ? 1 : 0, null, null, null, null, makeBasic.toBundle());
                } catch (android.content.IntentSender.SendIntentException e2) {
                    android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, e2);
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.CLEAR_APP_CACHE")
        public void freeStorageAndNotify(final java.lang.String str, final long j, final int i, final android.content.pm.IPackageDataObserver iPackageDataObserver) {
            freeStorageAndNotify_enforcePermission();
            com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$freeStorageAndNotify$4(str, j, i, iPackageDataObserver);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freeStorageAndNotify$4(java.lang.String str, long j, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) {
            boolean z;
            try {
                com.android.server.pm.PackageManagerService.this.freeStorage(str, j, i);
                z = true;
            } catch (java.io.IOException e) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, e);
                z = false;
            }
            if (iPackageDataObserver != null) {
                try {
                    iPackageDataObserver.onRemoveCompleted((java.lang.String) null, z);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, e2);
                }
            }
        }

        public android.content.pm.ChangedPackages getChangedPackages(int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null || !com.android.server.pm.PackageManagerService.this.mUserManager.exists(i2)) {
                return null;
            }
            snapshotComputer.enforceCrossUserPermission(callingUid, i2, false, false, "getChangedPackages");
            android.content.pm.ChangedPackages changedPackages = com.android.server.pm.PackageManagerService.this.mChangedPackagesTracker.getChangedPackages(i, i2);
            if (changedPackages != null) {
                java.util.List<java.lang.String> packageNames = changedPackages.getPackageNames();
                for (int size = packageNames.size() - 1; size >= 0; size--) {
                    if (snapshotComputer.shouldFilterApplication(snapshotComputer.getPackageStateInternal(packageNames.get(size)), callingUid, i2)) {
                        packageNames.remove(size);
                    }
                }
            }
            return changedPackages;
        }

        public byte[] getDomainVerificationBackup(int i) {
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("Only the system may call getDomainVerificationBackup()");
            }
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                try {
                    com.android.server.pm.PackageManagerService.this.mDomainVerificationManager.writeSettings(com.android.server.pm.PackageManagerService.this.snapshotComputer(), android.util.Xml.resolveSerializer(byteArrayOutputStream), true, i);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                } finally {
                }
            } catch (java.lang.Exception e) {
                return null;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public android.os.IBinder getHoldLockToken() {
            if (!android.os.Build.IS_DEBUGGABLE) {
                throw new java.lang.SecurityException("getHoldLockToken requires a debuggable build");
            }
            com.android.server.pm.PackageManagerService.this.mContext.enforceCallingPermission("android.permission.INJECT_EVENTS", "getHoldLockToken requires INJECT_EVENTS permission");
            android.os.Binder binder = new android.os.Binder();
            binder.attachInterface(this, "holdLock:" + android.os.Binder.getCallingUid());
            return binder;
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_INSTANT_APPS")
        public java.lang.String getInstantAppAndroidId(java.lang.String str, int i) {
            getInstantAppAndroidId_enforcePermission();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, false, "getInstantAppAndroidId");
            if (!snapshotComputer.isInstantApp(str, i)) {
                return null;
            }
            return com.android.server.pm.PackageManagerService.this.mInstantAppRegistry.getInstantAppAndroidId(str, i);
        }

        public byte[] getInstantAppCookie(java.lang.String str, int i) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, false, "getInstantAppCookie");
            if (!snapshotComputer.isCallerSameApp(str, android.os.Binder.getCallingUid()) || (packageStateInternal = snapshotComputer.getPackageStateInternal(str)) == null || packageStateInternal.getPkg() == null) {
                return null;
            }
            return com.android.server.pm.PackageManagerService.this.mInstantAppRegistry.getInstantAppCookie(packageStateInternal.getPkg(), i);
        }

        public android.graphics.Bitmap getInstantAppIcon(java.lang.String str, int i) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            if (!snapshotComputer.canViewInstantApps(android.os.Binder.getCallingUid(), i)) {
                com.android.server.pm.PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS", "getInstantAppIcon");
            }
            snapshotComputer.enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, false, "getInstantAppIcon");
            return com.android.server.pm.PackageManagerService.this.mInstantAppRegistry.getInstantAppIcon(str, i);
        }

        public android.content.pm.ParceledListSlice<android.content.pm.InstantAppInfo> getInstantApps(int i) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            if (!snapshotComputer.canViewInstantApps(android.os.Binder.getCallingUid(), i)) {
                com.android.server.pm.PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS", "getEphemeralApplications");
            }
            snapshotComputer.enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, false, "getEphemeralApplications");
            java.util.List<android.content.pm.InstantAppInfo> instantApps = com.android.server.pm.PackageManagerService.this.mInstantAppRegistry.getInstantApps(snapshotComputer, i);
            if (instantApps != null) {
                return new android.content.pm.ParceledListSlice<>(instantApps);
            }
            return null;
        }

        public android.content.pm.ResolveInfo getLastChosenActivity(android.content.Intent intent, java.lang.String str, int i) {
            return com.android.server.pm.PackageManagerService.this.mPreferredActivityHelper.getLastChosenActivity(com.android.server.pm.PackageManagerService.this.snapshotComputer(), intent, str, i);
        }

        public android.content.IntentSender getLaunchIntentSenderForPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return com.android.server.pm.PackageManagerService.this.mResolveIntentHelper.getLaunchIntentSenderForPackage(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, str2, str3, i);
        }

        public java.util.List<java.lang.String> getMimeGroup(java.lang.String str, java.lang.String str2) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            com.android.server.pm.PackageManagerService.this.enforceOwnerRights(snapshotComputer, str, android.os.Binder.getCallingUid());
            return com.android.server.pm.PackageManagerService.this.getMimeGroupInternal(snapshotComputer, str, str2);
        }

        @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
        public int getMoveStatus(int i) {
            getMoveStatus_enforcePermission();
            return com.android.server.pm.PackageManagerService.this.mMoveCallbacks.mLastStatus.get(i);
        }

        @android.annotation.EnforcePermission("android.permission.GET_APP_METADATA")
        public android.os.ParcelFileDescriptor getAppMetadataFd(java.lang.String str, int i) {
            getAppMetadataFd_enforcePermission();
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, android.os.Binder.getCallingUid(), i);
            if (packageStateForInstalledAndFiltered == null) {
                throw new android.os.ParcelableException(new android.content.pm.PackageManager.NameNotFoundException(str));
            }
            java.lang.String appMetadataFilePath = packageStateForInstalledAndFiltered.getAppMetadataFilePath();
            if (appMetadataFilePath == null) {
                return null;
            }
            java.io.File file = new java.io.File(appMetadataFilePath);
            if (android.content.pm.Flags.aslInApkAppMetadataSource() && !file.exists() && packageStateForInstalledAndFiltered.getAppMetadataSource() == 1 && !com.android.server.pm.PackageManagerServiceUtils.extractAppMetadataFromApk(((com.android.server.pm.pkg.AndroidPackageSplit) packageStateForInstalledAndFiltered.getPkg().getSplits().get(0)).getPath(), file)) {
                if (file.exists()) {
                    file.delete();
                }
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.PackageSetting packageLPr = com.android.server.pm.PackageManagerService.this.mSettings.getPackageLPr(str);
                        packageLPr.setAppMetadataFilePath(null);
                        packageLPr.setAppMetadataSource(0);
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return null;
            }
            try {
                return android.os.ParcelFileDescriptor.open(file, 268435456);
            } catch (java.io.FileNotFoundException e) {
                return null;
            }
        }

        @android.annotation.EnforcePermission("android.permission.GET_APP_METADATA")
        public int getAppMetadataSource(java.lang.String str, int i) {
            getAppMetadataSource_enforcePermission();
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, android.os.Binder.getCallingUid(), i);
            if (packageStateForInstalledAndFiltered == null) {
                throw new android.os.ParcelableException(new android.content.pm.PackageManager.NameNotFoundException(str));
            }
            return packageStateForInstalledAndFiltered.getAppMetadataSource();
        }

        public java.lang.String getPermissionControllerPackageName() {
            int callingUid = android.os.Binder.getCallingUid();
            if (com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(com.android.server.pm.PackageManagerService.this.mRequiredPermissionControllerPackage, callingUid, android.os.UserHandle.getUserId(callingUid)) != null) {
                return com.android.server.pm.PackageManagerService.this.mRequiredPermissionControllerPackage;
            }
            throw new java.lang.IllegalStateException("PermissionController is not found");
        }

        public int getRuntimePermissionsVersion(int i) {
            com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
            com.android.server.pm.PackageManagerService.this.enforceAdjustRuntimePermissionsPolicyOrUpgradeRuntimePermissions("getRuntimePermissionVersion");
            return com.android.server.pm.PackageManagerService.this.mSettings.getDefaultRuntimePermissionsVersion(i);
        }

        public java.lang.String getSplashScreenTheme(@android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            int callingUid = android.os.Binder.getCallingUid();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, false, "getSplashScreenTheme");
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, i);
            if (packageStateForInstalledAndFiltered == null) {
                return null;
            }
            return packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getSplashScreenTheme();
        }

        public int getUserMinAspectRatio(@android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, android.os.Binder.getCallingUid(), i);
            if (packageStateForInstalledAndFiltered == null) {
                return 0;
            }
            return packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getMinAspectRatio();
        }

        public android.os.Bundle getSuspendedPackageAppExtras(java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshot = snapshot();
            if (snapshot.getPackageUid(str, 0L, i) != callingUid) {
                throw new java.lang.SecurityException("Calling package " + str + " does not belong to calling uid " + callingUid);
            }
            return com.android.server.pm.SuspendPackageHelper.getSuspendedPackageAppExtras(snapshot, str, i, callingUid);
        }

        public java.lang.String getSuspendingPackage(java.lang.String str, int i) {
            android.content.pm.UserPackage suspendingPackage;
            try {
                int callingUid = android.os.Binder.getCallingUid();
                com.android.server.pm.Computer snapshot = snapshot();
                if (snapshot.isPackageSuspendedForUser(str, i) && (suspendingPackage = com.android.server.pm.PackageManagerService.this.mSuspendPackageHelper.getSuspendingPackage(snapshot, str, i, callingUid)) != null) {
                    return suspendingPackage.packageName;
                }
                return null;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return null;
            }
        }

        @android.annotation.NonNull
        public android.content.pm.ParceledListSlice<android.content.pm.FeatureInfo> getSystemAvailableFeatures() {
            java.util.ArrayList arrayList = new java.util.ArrayList(com.android.server.pm.PackageManagerService.this.mAvailableFeatures.size() + 1);
            arrayList.addAll(com.android.server.pm.PackageManagerService.this.mAvailableFeatures.values());
            android.content.pm.FeatureInfo featureInfo = new android.content.pm.FeatureInfo();
            featureInfo.reqGlEsVersion = android.os.SystemProperties.getInt("ro.opengles.version", 0);
            arrayList.add(featureInfo);
            return new android.content.pm.ParceledListSlice<>(arrayList);
        }

        @android.annotation.NonNull
        public java.util.List<java.lang.String> getInitialNonStoppedSystemPackages() {
            return com.android.server.pm.PackageManagerService.this.mInitialNonStoppedSystemPackages != null ? new java.util.ArrayList(com.android.server.pm.PackageManagerService.this.mInitialNonStoppedSystemPackages) : new java.util.ArrayList();
        }

        public java.lang.String[] getUnsuspendablePackagesForUser(java.lang.String[] strArr, int i) {
            java.util.Objects.requireNonNull(strArr, "packageNames cannot be null");
            com.android.server.pm.PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.SUSPEND_APPS", "getUnsuspendablePackagesForUser");
            int callingUid = android.os.Binder.getCallingUid();
            if (android.os.UserHandle.getUserId(callingUid) != i) {
                com.android.server.pm.PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Calling uid " + callingUid + " cannot query getUnsuspendablePackagesForUser for user " + i);
            }
            return com.android.server.pm.PackageManagerService.this.mSuspendPackageHelper.getUnsuspendablePackagesForUser(com.android.server.pm.PackageManagerService.this.snapshotComputer(), strArr, i, callingUid);
        }

        @android.annotation.EnforcePermission("android.permission.PACKAGE_VERIFICATION_AGENT")
        public android.content.pm.VerifierDeviceIdentity getVerifierDeviceIdentity() throws android.os.RemoteException {
            android.content.pm.VerifierDeviceIdentity verifierDeviceIdentityLPw;
            getVerifierDeviceIdentity_enforcePermission();
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    verifierDeviceIdentityLPw = com.android.server.pm.PackageManagerService.this.mSettings.getVerifierDeviceIdentityLPw(com.android.server.pm.PackageManagerService.this.mLiveComputer);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return verifierDeviceIdentityLPw;
        }

        public void makeProviderVisible(int i, @android.annotation.NonNull java.lang.String str) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            int userId = android.os.UserHandle.getUserId(i);
            android.content.pm.ProviderInfo grantImplicitAccessProviderInfo = snapshotComputer.getGrantImplicitAccessProviderInfo(i, str);
            if (grantImplicitAccessProviderInfo == null) {
                return;
            }
            com.android.server.pm.PackageManagerService.this.grantImplicitAccess(snapshotComputer, userId, null, android.os.UserHandle.getAppId(i), grantImplicitAccessProviderInfo.applicationInfo.uid, false, false);
        }

        @android.annotation.EnforcePermission("android.permission.MAKE_UID_VISIBLE")
        public void makeUidVisible(int i, int i2) {
            makeUidVisible_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(i);
            int userId2 = android.os.UserHandle.getUserId(i2);
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, userId, false, false, "makeUidVisible");
            snapshotComputer.enforceCrossUserPermission(callingUid, userId2, false, false, "makeUidVisible");
            snapshotComputer.enforceCrossUserPermission(i, userId2, false, false, "makeUidVisible");
            com.android.server.pm.PackageManagerService.this.grantImplicitAccess(snapshotComputer, userId, null, android.os.UserHandle.getAppId(i), i2, false, false);
        }

        public void holdLock(android.os.IBinder iBinder, int i) {
            com.android.server.pm.PackageManagerService.this.mTestUtilityService.verifyHoldLockToken(iBinder);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    android.os.SystemClock.sleep(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        public int installExistingPackageAsUser(java.lang.String str, int i, int i2, int i3, java.util.List<java.lang.String> list) {
            return ((java.lang.Integer) com.android.server.pm.PackageManagerService.this.mInstallPackageHelper.installExistingPackageAsUser(str, i, i2, i3, list, null).first).intValue();
        }

        public boolean isAutoRevokeWhitelisted(java.lang.String str) {
            return ((android.app.AppOpsManager) com.android.server.pm.PackageManagerService.this.mInjector.getSystemService(android.app.AppOpsManager.class)).checkOpNoThrow(97, android.os.Binder.getCallingUid(), str) == 1;
        }

        public boolean isPackageStateProtected(@android.annotation.NonNull java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int appId = android.os.UserHandle.getAppId(callingUid);
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, true, "isPackageStateProtected");
            if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot(appId) && snapshotComputer.checkUidPermission("android.permission.MANAGE_DEVICE_ADMINS", callingUid) != 0) {
                throw new java.lang.SecurityException("Caller must have the android.permission.MANAGE_DEVICE_ADMINS permission.");
            }
            return com.android.server.pm.PackageManagerService.this.mProtectedPackages.isPackageStateProtected(i, str);
        }

        public boolean isProtectedBroadcast(java.lang.String str) {
            boolean contains;
            if (str != null && (str.startsWith("android.net.netmon.lingerExpired") || str.startsWith("com.android.server.sip.SipWakeupTimer") || str.startsWith("com.android.internal.telephony.data-reconnect") || str.startsWith("android.net.netmon.launchCaptivePortalApp"))) {
                return true;
            }
            synchronized (com.android.server.pm.PackageManagerService.this.mProtectedBroadcasts) {
                contains = com.android.server.pm.PackageManagerService.this.mProtectedBroadcasts.contains(str);
            }
            return contains;
        }

        public void logAppProcessStartIfNeeded(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.lang.String str4, int i2) {
            if (com.android.server.pm.PackageManagerService.this.snapshotComputer().getInstantAppPackageName(android.os.Binder.getCallingUid()) != null || !android.app.admin.SecurityLog.isLoggingEnabled()) {
                return;
            }
            com.android.server.pm.PackageManagerService.this.mProcessLoggingHandler.logAppProcessStart(com.android.server.pm.PackageManagerService.this.mContext, (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class), str4, str, str2, i, str3, i2);
        }

        @android.annotation.EnforcePermission("android.permission.MOVE_PACKAGE")
        public int movePackage(final java.lang.String str, final java.lang.String str2) {
            movePackage_enforcePermission();
            final int callingUid = android.os.Binder.getCallingUid();
            final android.os.UserHandle userHandle = new android.os.UserHandle(android.os.UserHandle.getUserId(callingUid));
            final int andIncrement = com.android.server.pm.PackageManagerService.this.mNextMoveId.getAndIncrement();
            com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$movePackage$5(str, str2, andIncrement, callingUid, userHandle);
                }
            });
            return andIncrement;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$movePackage$5(java.lang.String str, java.lang.String str2, int i, int i2, android.os.UserHandle userHandle) {
            try {
                new com.android.server.pm.MovePackageHelper(com.android.server.pm.PackageManagerService.this).movePackageInternal(str, str2, i, i2, userHandle);
            } catch (com.android.server.pm.PackageManagerException e) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Failed to move " + str, e);
                com.android.server.pm.PackageManagerService.this.mMoveCallbacks.notifyStatusChanged(i, e.error);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MOVE_PACKAGE")
        public int movePrimaryStorage(java.lang.String str) throws android.os.RemoteException {
            movePrimaryStorage_enforcePermission();
            final int andIncrement = com.android.server.pm.PackageManagerService.this.mNextMoveId.getAndIncrement();
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString("android.os.storage.extra.FS_UUID", str);
            com.android.server.pm.PackageManagerService.this.mMoveCallbacks.notifyCreated(andIncrement, bundle);
            ((android.os.storage.StorageManager) com.android.server.pm.PackageManagerService.this.mInjector.getSystemService(android.os.storage.StorageManager.class)).setPrimaryStorageUuid(str, new android.content.pm.IPackageMoveObserver.Stub() { // from class: com.android.server.pm.PackageManagerService.IPackageManagerImpl.2
                public void onCreated(int i, android.os.Bundle bundle2) {
                }

                public void onStatusChanged(int i, int i2, long j) {
                    com.android.server.pm.PackageManagerService.this.mMoveCallbacks.notifyStatusChanged(andIncrement, i2, j);
                }
            });
            return andIncrement;
        }

        public void notifyDexLoad(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map, java.lang.String str2) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshot = snapshot();
            if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot() && !snapshot.isCallerSameApp(str, callingUid, true)) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, android.text.TextUtils.formatSimple("Invalid dex load report. loadingPackageName=%s, uid=%d", new java.lang.Object[]{str, java.lang.Integer.valueOf(callingUid)}));
                return;
            }
            android.os.UserHandle callingUserHandle = android.os.Binder.getCallingUserHandle();
            int identifier = callingUserHandle.getIdentifier();
            com.android.server.art.DexUseManagerLocal dexUseManagerLocal = com.android.server.pm.DexOptHelper.getDexUseManagerLocal();
            if (dexUseManagerLocal != null) {
                com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = ((com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.pm.PackageManagerLocal.class)).withFilteredSnapshot(callingUid, callingUserHandle);
                if (str2 != null) {
                    try {
                        com.android.server.pm.pkg.PackageState packageState = withFilteredSnapshot.getPackageState(str);
                        if (packageState != null) {
                            java.lang.String primaryCpuAbi = packageState.getPrimaryCpuAbi();
                            if (primaryCpuAbi == null) {
                                primaryCpuAbi = android.os.Build.SUPPORTED_ABIS[0];
                            }
                            java.lang.String dexCodeInstructionSet = com.android.server.pm.InstructionSets.getDexCodeInstructionSet(dalvik.system.VMRuntime.getInstructionSet(primaryCpuAbi));
                            if (!str2.equals(dexCodeInstructionSet)) {
                                android.util.Log.wtf(com.android.server.pm.PackageManagerService.TAG, "Invalid loaderIsa in notifyDexLoad call from " + str + ", uid " + callingUid + ": expected " + dexCodeInstructionSet + ", got " + str2);
                                withFilteredSnapshot.close();
                                return;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        if (withFilteredSnapshot != null) {
                            try {
                                withFilteredSnapshot.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                }
                dexUseManagerLocal.notifyDexContainersLoaded(withFilteredSnapshot, str, map);
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                    return;
                }
                return;
            }
            android.content.pm.ApplicationInfo applicationInfo = snapshot.getApplicationInfo(str, 0L, identifier);
            if (applicationInfo == null) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Loading a package that does not exist for the calling user. package=" + str + ", user=" + identifier);
                return;
            }
            com.android.server.pm.PackageManagerService.this.mDexManager.notifyDexLoad(applicationInfo, map, str2, identifier, android.os.Process.isIsolated(callingUid));
        }

        public void notifyPackageUse(java.lang.String str, int i) {
            boolean z;
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(callingUid) != null) {
                z = snapshotComputer.isCallerSameApp(str, callingUid);
            } else {
                z = !snapshotComputer.isInstantAppInternal(str, userId, 1000);
            }
            if (!z) {
                return;
            }
            com.android.server.pm.PackageManagerService.this.notifyPackageUseInternal(str, i);
        }

        public void overrideLabelAndIcon(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull java.lang.String str, int i, int i2) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("Override label should be a valid String");
            }
            com.android.server.pm.PackageManagerService.this.updateComponentLabelIcon(componentName, str, java.lang.Integer.valueOf(i), i2);
        }

        public android.content.pm.ParceledListSlice<android.content.pm.PackageManager.Property> queryProperty(java.lang.String str, int i) {
            java.util.Objects.requireNonNull(str);
            final int callingUid = android.os.Binder.getCallingUid();
            final int callingUserId = android.os.UserHandle.getCallingUserId();
            final com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            java.util.List<android.content.pm.PackageManager.Property> queryProperty = com.android.server.pm.PackageManagerService.this.mPackageProperty.queryProperty(str, i, new java.util.function.Predicate() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$queryProperty$6;
                    lambda$queryProperty$6 = com.android.server.pm.PackageManagerService.IPackageManagerImpl.lambda$queryProperty$6(com.android.server.pm.Computer.this, callingUid, callingUserId, (java.lang.String) obj);
                    return lambda$queryProperty$6;
                }
            });
            if (queryProperty == null) {
                return android.content.pm.ParceledListSlice.emptyList();
            }
            return new android.content.pm.ParceledListSlice<>(queryProperty);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$queryProperty$6(com.android.server.pm.Computer computer, int i, int i2, java.lang.String str) {
            return computer.getPackageStateForInstalledAndFiltered(str, i, i2) == null;
        }

        public void registerDexModule(java.lang.String str, final java.lang.String str2, boolean z, final android.content.pm.IDexModuleRegisterCallback iDexModuleRegisterCallback) {
            android.util.Slog.i(com.android.server.pm.PackageManagerService.TAG, "Ignored unsupported registerDexModule call for " + str2 + " in " + str);
            final com.android.server.pm.dex.DexManager.RegisterDexModuleResult registerDexModuleResult = new com.android.server.pm.dex.DexManager.RegisterDexModuleResult(false, "registerDexModule call not supported since Android U");
            if (iDexModuleRegisterCallback != null) {
                com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.PackageManagerService.IPackageManagerImpl.lambda$registerDexModule$7(iDexModuleRegisterCallback, str2, registerDexModuleResult);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$registerDexModule$7(android.content.pm.IDexModuleRegisterCallback iDexModuleRegisterCallback, java.lang.String str, com.android.server.pm.dex.DexManager.RegisterDexModuleResult registerDexModuleResult) {
            try {
                iDexModuleRegisterCallback.onDexModuleRegistered(str, registerDexModuleResult.success, registerDexModuleResult.message);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Failed to callback after module registration " + str, e);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
        public void registerMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) {
            registerMoveCallback_enforcePermission();
            com.android.server.pm.PackageManagerService.this.mMoveCallbacks.register(iPackageMoveObserver);
        }

        public void restoreDomainVerification(byte[] bArr, int i) {
            if (android.os.Binder.getCallingUid() != 1000) {
                throw new java.lang.SecurityException("Only the system may call restorePreferredActivities()");
            }
            try {
                java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
                com.android.server.pm.PackageManagerService.this.mDomainVerificationManager.restoreSettings(com.android.server.pm.PackageManagerService.this.snapshotComputer(), android.util.Xml.resolvePullParser(byteArrayInputStream));
                byteArrayInputStream.close();
            } catch (java.lang.Exception e) {
            }
        }

        public void restoreLabelAndIcon(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
            com.android.server.pm.PackageManagerService.this.updateComponentLabelIcon(componentName, null, null, i);
        }

        public void sendDeviceCustomizationReadyBroadcast() {
            com.android.server.pm.PackageManagerService.this.mContext.enforceCallingPermission("android.permission.SEND_DEVICE_CUSTOMIZATION_READY", "sendDeviceCustomizationReadyBroadcast");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.pm.BroadcastHelper.sendDeviceCustomizationReadyBroadcast();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setApplicationCategoryHint(final java.lang.String str, final int i, final java.lang.String str2) {
            com.android.internal.util.FunctionalUtils.ThrowingBiFunction throwingBiFunction = new com.android.internal.util.FunctionalUtils.ThrowingBiFunction() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda20
                public final java.lang.Object applyOrThrow(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.pm.pkg.mutate.PackageStateMutator.Result lambda$setApplicationCategoryHint$9;
                    lambda$setApplicationCategoryHint$9 = com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$setApplicationCategoryHint$9(str2, str, i, (com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState) obj, (com.android.server.pm.Computer) obj2);
                    return lambda$setApplicationCategoryHint$9;
                }
            };
            com.android.server.pm.pkg.mutate.PackageStateMutator.Result result = (com.android.server.pm.pkg.mutate.PackageStateMutator.Result) throwingBiFunction.apply(com.android.server.pm.PackageManagerService.this.recordInitialState(), com.android.server.pm.PackageManagerService.this.snapshotComputer());
            if (result != null && result.isStateChanged() && !result.isSpecificPackageNull()) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mPackageStateWriteLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        result = (com.android.server.pm.pkg.mutate.PackageStateMutator.Result) throwingBiFunction.apply(com.android.server.pm.PackageManagerService.this.recordInitialState(), com.android.server.pm.PackageManagerService.this.snapshotComputer());
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
            if (result != null && result.isCommitted()) {
                com.android.server.pm.PackageManagerService.this.scheduleWriteSettings();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.pm.pkg.mutate.PackageStateMutator.Result lambda$setApplicationCategoryHint$9(java.lang.String str, java.lang.String str2, final int i, com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState initialState, com.android.server.pm.Computer computer) throws java.lang.Exception {
            if (computer.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
                throw new java.lang.SecurityException("Instant applications don't have access to this method");
            }
            ((android.app.AppOpsManager) com.android.server.pm.PackageManagerService.this.mInjector.getSystemService(android.app.AppOpsManager.class)).checkPackage(android.os.Binder.getCallingUid(), str);
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(str2, android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
            if (packageStateForInstalledAndFiltered == null) {
                throw new java.lang.IllegalArgumentException("Unknown target package " + str2);
            }
            if (!java.util.Objects.equals(str, packageStateForInstalledAndFiltered.getInstallSource().mInstallerPackageName)) {
                throw new java.lang.IllegalArgumentException("Calling package " + str + " is not installer for " + str2);
            }
            if (packageStateForInstalledAndFiltered.getCategoryOverride() != i) {
                return com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(initialState, str2, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setCategoryOverride(i);
                    }
                });
            }
            return null;
        }

        public void setApplicationEnabledSetting(java.lang.String str, int i, int i2, int i3, java.lang.String str2) {
            if (com.android.server.pm.PackageManagerService.this.mUserManager.exists(i3)) {
                if (str2 == null) {
                    str2 = java.lang.Integer.toString(android.os.Binder.getCallingUid());
                }
                com.android.server.pm.PackageManagerService.this.setEnabledSettings(java.util.List.of(new android.content.pm.PackageManager.ComponentEnabledSetting(str, i, i2)), i3, str2);
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_USERS")
        public boolean setApplicationHiddenSettingAsUser(java.lang.String str, final boolean z, final int i) {
            setApplicationHiddenSettingAsUser_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, true, true, "setApplicationHiddenSetting for user " + i);
            if (z && com.android.server.pm.PackageManagerService.this.isPackageDeviceAdmin(str, i)) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Not hiding package " + str + ": has active device admin");
                return false;
            }
            if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Cannot hide package: android");
                return false;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                if (packageStateInternal == null) {
                    return false;
                }
                com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                if (userStateOrDefault.isHidden() != z && userStateOrDefault.isInstalled() && !snapshotComputer.shouldFilterApplication(packageStateInternal, callingUid, i)) {
                    com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
                    if (pkg != null) {
                        if (pkg.getSdkLibraryName() != null) {
                            android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Cannot hide package: " + str + " providing SDK library: " + pkg.getSdkLibraryName());
                            return false;
                        }
                        if (pkg.getStaticSharedLibraryName() != null) {
                            android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Cannot hide package: " + str + " providing static shared library: " + pkg.getStaticSharedLibraryName());
                            return false;
                        }
                    }
                    if (z && !android.os.UserHandle.isSameApp(callingUid, packageStateInternal.getAppId()) && com.android.server.pm.PackageManagerService.this.mProtectedPackages.isPackageStateProtected(i, str)) {
                        android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Not hiding protected package: " + str);
                        return false;
                    }
                    com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.PackageManagerService.IPackageManagerImpl.lambda$setApplicationHiddenSettingAsUser$10(i, z, (com.android.server.pm.pkg.mutate.PackageStateWrite) obj);
                        }
                    });
                    com.android.server.pm.Computer snapshotComputer2 = com.android.server.pm.PackageManagerService.this.snapshotComputer();
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = snapshotComputer2.getPackageStateInternal(str);
                    if (z) {
                        com.android.server.pm.PackageManagerService.this.killApplication(str, packageStateInternal2.getAppId(), i, "hiding pkg", 13);
                        com.android.server.pm.PackageManagerService.this.mBroadcastHelper.sendApplicationHiddenForUser(str, packageStateInternal2, i, com.android.server.pm.PackageManagerService.this);
                    } else {
                        com.android.server.pm.PackageManagerService.this.mBroadcastHelper.sendPackageAddedForUser(snapshotComputer2, str, packageStateInternal2, i, false, 0, com.android.server.pm.PackageManagerService.this.mAppPredictionServicePackage);
                    }
                    com.android.server.pm.PackageManagerService.this.scheduleWritePackageRestrictions(i);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
                return false;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$setApplicationHiddenSettingAsUser$10(int i, boolean z, com.android.server.pm.pkg.mutate.PackageStateWrite packageStateWrite) {
            packageStateWrite.userState(i).setHidden(z);
        }

        @android.annotation.EnforcePermission("android.permission.DELETE_PACKAGES")
        public boolean setBlockUninstallForUser(java.lang.String str, boolean z, int i) {
            setBlockUninstallForUser_enforcePermission();
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
            if (packageStateInternal != null && packageStateInternal.getPkg() != null) {
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
                if (pkg.getSdkLibraryName() != null) {
                    android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Cannot block uninstall of package: " + str + " providing SDK library: " + pkg.getSdkLibraryName());
                    return false;
                }
                if (pkg.getStaticSharedLibraryName() != null) {
                    android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Cannot block uninstall of package: " + str + " providing static shared library: " + pkg.getStaticSharedLibraryName());
                    return false;
                }
            }
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.PackageManagerService.this.mSettings.setBlockUninstallLPw(i, str, z);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            com.android.server.pm.PackageManagerService.this.scheduleWritePackageRestrictions(i);
            return true;
        }

        public void setComponentEnabledSetting(android.content.ComponentName componentName, int i, int i2, int i3, java.lang.String str) {
            if (com.android.server.pm.PackageManagerService.this.mUserManager.exists(i3)) {
                if (str == null) {
                    str = java.lang.Integer.toString(android.os.Binder.getCallingUid());
                }
                com.android.server.pm.PackageManagerService.this.setEnabledSettings(java.util.List.of(new android.content.pm.PackageManager.ComponentEnabledSetting(componentName, i, i2)), i3, str);
            }
        }

        public void setComponentEnabledSettings(java.util.List<android.content.pm.PackageManager.ComponentEnabledSetting> list, int i, java.lang.String str) {
            if (com.android.server.pm.PackageManagerService.this.mUserManager.exists(i)) {
                if (list == null || list.isEmpty()) {
                    throw new java.lang.IllegalArgumentException("The list of enabled settings is empty");
                }
                if (str == null) {
                    str = java.lang.Integer.toString(android.os.Binder.getCallingUid());
                }
                com.android.server.pm.PackageManagerService.this.setEnabledSettings(list, i, str);
            }
        }

        public java.lang.String[] setDistractingPackageRestrictionsAsUser(java.lang.String[] strArr, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            com.android.server.pm.PackageManagerService.this.enforceCanSetDistractingPackageRestrictionsAsUser(callingUid, i2, "setDistractingPackageRestrictionsAsUser");
            java.util.Objects.requireNonNull(strArr, "packageNames cannot be null");
            return com.android.server.pm.PackageManagerService.this.mDistractingPackageHelper.setDistractingPackageRestrictionsAsUser(snapshotComputer, strArr, i, i2, callingUid);
        }

        public void setHarmfulAppWarning(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable final java.lang.CharSequence charSequence, final int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int appId = android.os.UserHandle.getAppId(callingUid);
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, true, true, "setHarmfulAppInfo");
            if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot(appId) && snapshotComputer.checkUidPermission("android.permission.SET_HARMFUL_APP_WARNINGS", callingUid) != 0) {
                throw new java.lang.SecurityException("Caller must have the android.permission.SET_HARMFUL_APP_WARNINGS permission.");
            }
            if (com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.lambda$setHarmfulAppWarning$11(i, charSequence, (com.android.server.pm.pkg.mutate.PackageStateWrite) obj);
                }
            }).isSpecificPackageNull()) {
                throw new java.lang.IllegalArgumentException("Unknown package: " + str);
            }
            com.android.server.pm.PackageManagerService.this.scheduleWritePackageRestrictions(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$setHarmfulAppWarning$11(int i, java.lang.CharSequence charSequence, com.android.server.pm.pkg.mutate.PackageStateWrite packageStateWrite) {
            packageStateWrite.userState(i).setHarmfulAppWarning(charSequence == null ? null : charSequence.toString());
        }

        @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
        public boolean setInstallLocation(int i) {
            setInstallLocation_enforcePermission();
            if (getInstallLocation() == i) {
                return true;
            }
            if (i == 0 || i == 1 || i == 2) {
                android.provider.Settings.Global.putInt(com.android.server.pm.PackageManagerService.this.mContext.getContentResolver(), "default_install_location", i);
                return true;
            }
            return false;
        }

        public void setInstallerPackageName(final java.lang.String str, @android.annotation.Nullable final java.lang.String str2) {
            final int callingUid = android.os.Binder.getCallingUid();
            final int userId = android.os.UserHandle.getUserId(callingUid);
            com.android.internal.util.FunctionalUtils.ThrowingCheckedFunction throwingCheckedFunction = new com.android.internal.util.FunctionalUtils.ThrowingCheckedFunction() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda1
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Boolean lambda$setInstallerPackageName$12;
                    lambda$setInstallerPackageName$12 = com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$setInstallerPackageName$12(callingUid, str, userId, str2, (com.android.server.pm.Computer) obj);
                    return lambda$setInstallerPackageName$12;
                }
            };
            com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState recordInitialState = com.android.server.pm.PackageManagerService.this.recordInitialState();
            if (((java.lang.Boolean) throwingCheckedFunction.apply(com.android.server.pm.PackageManagerService.this.snapshotComputer())).booleanValue()) {
                final int packageUid = str2 == null ? -1 : com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageUid(str2, 0L, userId);
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.pkg.mutate.PackageStateMutator.Result commitPackageStateMutation = com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(recordInitialState, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setInstaller(str2, packageUid);
                            }
                        });
                        if (commitPackageStateMutation.isPackagesChanged() || commitPackageStateMutation.isStateChanged()) {
                            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = com.android.server.pm.PackageManagerService.this.mPackageStateWriteLock;
                            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                            synchronized (packageManagerTracedLock2) {
                                try {
                                    if (!((java.lang.Boolean) throwingCheckedFunction.apply(com.android.server.pm.PackageManagerService.this.snapshotComputer())).booleanValue()) {
                                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                        return;
                                    } else {
                                        com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Consumer
                                            public final void accept(java.lang.Object obj) {
                                                ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setInstaller(str2, packageUid);
                                            }
                                        });
                                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                    }
                                } finally {
                                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                                }
                            }
                        }
                        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateInternal(str);
                        com.android.server.pm.PackageManagerService.this.mSettings.addInstallerPackageNames(packageStateInternal.getInstallSource());
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        com.android.server.pm.PackageManagerService.this.mAppsFilter.addPackage(com.android.server.pm.PackageManagerService.this.snapshotComputer(), packageStateInternal);
                        com.android.server.pm.PackageManagerService.this.scheduleWriteSettings();
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Boolean lambda$setInstallerPackageName$12(int i, java.lang.String str, int i2, java.lang.String str2, com.android.server.pm.Computer computer) throws java.lang.RuntimeException {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
            android.content.pm.SigningDetails signingDetails;
            if (computer.getInstantAppPackageName(i) != null) {
                return false;
            }
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(str, i, i2);
            if (packageStateForInstalledAndFiltered == null) {
                throw new java.lang.IllegalArgumentException("Unknown target package: " + str);
            }
            if (str2 == null) {
                packageStateInternal = null;
            } else {
                packageStateInternal = computer.getPackageStateForInstalledAndFiltered(str2, i, i2);
                if (packageStateInternal == null) {
                    throw new java.lang.IllegalArgumentException("Unknown installer package: " + str2);
                }
            }
            android.util.Pair<com.android.server.pm.pkg.PackageStateInternal, com.android.server.pm.pkg.SharedUserApi> packageOrSharedUser = computer.getPackageOrSharedUser(android.os.UserHandle.getAppId(i));
            if (packageOrSharedUser != null) {
                if (packageOrSharedUser.first != null) {
                    signingDetails = ((com.android.server.pm.pkg.PackageStateInternal) packageOrSharedUser.first).getSigningDetails();
                } else {
                    signingDetails = ((com.android.server.pm.pkg.SharedUserApi) packageOrSharedUser.second).getSigningDetails();
                }
                if (packageStateInternal != null && com.android.server.pm.PackageManagerServiceUtils.compareSignatures(signingDetails, packageStateInternal.getSigningDetails()) != 0) {
                    throw new java.lang.SecurityException("Caller does not have same cert as new installer package " + str2);
                }
                java.lang.String str3 = packageStateForInstalledAndFiltered.getInstallSource().mInstallerPackageName;
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = str3 != null ? computer.getPackageStateInternal(str3) : null;
                if (packageStateInternal2 != null) {
                    if (com.android.server.pm.PackageManagerServiceUtils.compareSignatures(signingDetails, packageStateInternal2.getSigningDetails()) != 0) {
                        throw new java.lang.SecurityException("Caller does not have same cert as old installer package " + str3);
                    }
                } else if (com.android.server.pm.PackageManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0) {
                    android.util.EventLog.writeEvent(1397638484, "150857253", java.lang.Integer.valueOf(i), "");
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (!com.android.server.pm.PackageManagerService.this.mInjector.getCompatibility().isChangeEnabledByUid(com.android.server.pm.PackageManagerService.THROW_EXCEPTION_ON_REQUIRE_INSTALL_PACKAGES_TO_ADD_INSTALLER_PACKAGE, i)) {
                            return false;
                        }
                        throw new java.lang.SecurityException("Neither user " + i + " nor current process has android.permission.INSTALL_PACKAGES");
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                return true;
            }
            throw new java.lang.SecurityException("Unknown calling UID: " + i);
        }

        public void relinquishUpdateOwnership(java.lang.String str) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
            int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, userId);
            if (packageStateForInstalledAndFiltered == null) {
                throw new java.lang.IllegalArgumentException("Unknown target package: " + str);
            }
            java.lang.String str2 = packageStateForInstalledAndFiltered.getInstallSource().mUpdateOwnerPackageName;
            if (str2 == null) {
                packageStateInternal = null;
            } else {
                packageStateInternal = snapshotComputer.getPackageStateInternal(str2);
            }
            if (packageStateInternal == null) {
                return;
            }
            int appId = android.os.UserHandle.getAppId(callingUid);
            int appId2 = packageStateInternal.getAppId();
            if (appId == 1000 || appId == 2000 || appId == appId2) {
                com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda22
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setUpdateOwner(null);
                    }
                });
                com.android.server.pm.PackageManagerService.this.scheduleWriteSettings();
                return;
            }
            throw new java.lang.SecurityException("Caller is not the current update owner.");
        }

        public boolean setInstantAppCookie(java.lang.String str, byte[] bArr, int i) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, true, "setInstantAppCookie");
            if (!snapshotComputer.isCallerSameApp(str, android.os.Binder.getCallingUid()) || (packageStateInternal = snapshotComputer.getPackageStateInternal(str)) == null || packageStateInternal.getPkg() == null) {
                return false;
            }
            return com.android.server.pm.PackageManagerService.this.mInstantAppRegistry.setInstantAppCookie(packageStateInternal.getPkg(), bArr, com.android.server.pm.PackageManagerService.this.mContext.getPackageManager().getInstantAppCookieMaxBytes(), i);
        }

        public void setKeepUninstalledPackages(java.util.List<java.lang.String> list) {
            com.android.server.pm.PackageManagerService.this.mContext.enforceCallingPermission("android.permission.KEEP_UNINSTALLED_PACKAGES", "setKeepUninstalledPackages requires KEEP_UNINSTALLED_PACKAGES permission");
            java.util.Objects.requireNonNull(list);
            com.android.server.pm.PackageManagerService.this.setKeepUninstalledPackagesInternal(snapshot(), list);
        }

        public void setMimeGroup(final java.lang.String str, final java.lang.String str2, java.util.List<java.lang.String> list) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            com.android.server.pm.PackageManagerService.this.enforceOwnerRights(snapshotComputer, str, android.os.Binder.getCallingUid());
            java.util.List emptyIfNull = com.android.internal.util.CollectionUtils.emptyIfNull(list);
            for (int i = 0; i < emptyIfNull.size(); i++) {
                if (((java.lang.String) emptyIfNull.get(i)).length() > 255) {
                    throw new java.lang.IllegalArgumentException("MIME type length exceeds 255 characters");
                }
            }
            final com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshotComputer.getPackageStateInternal(str);
            java.util.Set<java.lang.String> set = packageStateInternal.getMimeGroups().get(str2);
            if (set == null) {
                throw new java.lang.IllegalArgumentException("Unknown MIME group " + str2 + " for package " + str);
            }
            if (set.size() == emptyIfNull.size() && set.containsAll(emptyIfNull)) {
                return;
            }
            if (emptyIfNull.size() > 500) {
                throw new java.lang.IllegalStateException("Max limit on MIME types for MIME group " + str2 + " exceeded for package " + str);
            }
            final android.util.ArraySet arraySet = new android.util.ArraySet(emptyIfNull);
            com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setMimeGroup(str2, arraySet);
                }
            });
            if (com.android.server.pm.PackageManagerService.this.mComponentResolver.updateMimeGroup(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, str2)) {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda17
                    public final void runOrThrow() {
                        com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$setMimeGroup$17(str, packageStateInternal);
                    }
                });
            }
            com.android.server.pm.PackageManagerService.this.scheduleWriteSettings();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMimeGroup$17(java.lang.String str, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) throws java.lang.Exception {
            com.android.server.pm.PackageManagerService.this.mPreferredActivityHelper.clearPackagePreferredActivities(str, -1);
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>(java.util.Collections.singletonList(str));
            int appId = packageStateInternal.getAppId();
            int[] resolveUserIds = com.android.server.pm.PackageManagerService.this.resolveUserIds(-1);
            for (int i = 0; i < resolveUserIds.length; i++) {
                com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal = packageStateInternal.getUserStates().get(resolveUserIds[i]);
                if (packageUserStateInternal != null && packageUserStateInternal.isInstalled()) {
                    com.android.server.pm.PackageManagerService.this.mBroadcastHelper.sendPackageChangedBroadcast(snapshotComputer, str, true, arrayList, android.os.UserHandle.getUid(resolveUserIds[i], appId), "The mimeGroup is changed");
                }
            }
        }

        public void setPackageStoppedState(java.lang.String str, boolean z, int i) {
            com.android.server.pm.PackageManagerService.this.setPackageStoppedState(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, z, i);
        }

        public java.lang.String[] setPackagesSuspendedAsUser(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo, int i, java.lang.String str, int i2, int i3) {
            boolean z2;
            int callingUid = android.os.Binder.getCallingUid();
            if (android.content.pm.Flags.quarantinedEnabled()) {
                if ((i & 1) != 0) {
                    z2 = true;
                } else if (android.util.FeatureFlagUtils.isEnabled(com.android.server.pm.PackageManagerService.this.mContext, "settings_treat_pause_as_quarantine")) {
                    z2 = str.equals(com.android.server.pm.PackageManagerService.this.mContext.getString(android.R.string.config_systemWellbeing));
                }
                com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
                android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i3, str);
                com.android.server.pm.PackageManagerService.this.enforceCanSetPackagesSuspendedAsUser(snapshotComputer, z2, of, callingUid, i3, "setPackagesSuspendedAsUser");
                return com.android.server.pm.PackageManagerService.this.mSuspendPackageHelper.setPackagesSuspended(snapshotComputer, strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, of, i3, callingUid, z2);
            }
            z2 = false;
            com.android.server.pm.Computer snapshotComputer2 = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            android.content.pm.UserPackage of2 = android.content.pm.UserPackage.of(i3, str);
            com.android.server.pm.PackageManagerService.this.enforceCanSetPackagesSuspendedAsUser(snapshotComputer2, z2, of2, callingUid, i3, "setPackagesSuspendedAsUser");
            return com.android.server.pm.PackageManagerService.this.mSuspendPackageHelper.setPackagesSuspended(snapshotComputer2, strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, of2, i3, callingUid, z2);
        }

        public boolean setRequiredForSystemUser(java.lang.String str, final boolean z) {
            com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("setRequiredForSystemUser can only be run by the system or root");
            if (!com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setRequiredForSystemUser(z);
                }
            }).isCommitted()) {
                return false;
            }
            com.android.server.pm.PackageManagerService.this.scheduleWriteSettings();
            return true;
        }

        @android.annotation.EnforcePermission("android.permission.INSTALL_PACKAGES")
        public void setUserMinAspectRatio(@android.annotation.NonNull java.lang.String str, final int i, final int i2) {
            setUserMinAspectRatio_enforcePermission();
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, false, "setUserMinAspectRatio");
            com.android.server.pm.PackageManagerService.this.enforceOwnerRights(snapshotComputer, str, callingUid);
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, i);
            if (packageStateForInstalledAndFiltered == null || packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getMinAspectRatio() == i2) {
                return;
            }
            com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.lambda$setUserMinAspectRatio$19(i, i2, (com.android.server.pm.pkg.mutate.PackageStateWrite) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$setUserMinAspectRatio$19(int i, int i2, com.android.server.pm.pkg.mutate.PackageStateWrite packageStateWrite) {
            packageStateWrite.userState(i).setMinAspectRatio(i2);
        }

        public void setRuntimePermissionsVersion(int i, int i2) {
            com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
            com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
            com.android.server.pm.PackageManagerService.this.enforceAdjustRuntimePermissionsPolicyOrUpgradeRuntimePermissions("setRuntimePermissionVersion");
            com.android.server.pm.PackageManagerService.this.mSettings.setDefaultRuntimePermissionsVersion(i, i2);
        }

        public void setSplashScreenTheme(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable final java.lang.String str2, final int i) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            snapshotComputer.enforceCrossUserPermission(callingUid, i, false, false, "setSplashScreenTheme");
            com.android.server.pm.PackageManagerService.this.enforceOwnerRights(snapshotComputer, str, callingUid);
            if (snapshotComputer.getPackageStateForInstalledAndFiltered(str, callingUid, i) == null) {
                return;
            }
            com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.lambda$setSplashScreenTheme$20(i, str2, (com.android.server.pm.pkg.mutate.PackageStateWrite) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$setSplashScreenTheme$20(int i, java.lang.String str, com.android.server.pm.pkg.mutate.PackageStateWrite packageStateWrite) {
            packageStateWrite.userState(i).setSplashScreenTheme(str);
        }

        @android.annotation.EnforcePermission("android.permission.INSTALL_PACKAGES")
        public void setUpdateAvailable(java.lang.String str, final boolean z) {
            setUpdateAvailable_enforcePermission();
            com.android.server.pm.PackageManagerService.this.commitPackageStateMutation(null, str, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setUpdateAvailable(z);
                }
            });
        }

        @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
        public void unregisterMoveCallback(android.content.pm.IPackageMoveObserver iPackageMoveObserver) {
            unregisterMoveCallback_enforcePermission();
            com.android.server.pm.PackageManagerService.this.mMoveCallbacks.unregister(iPackageMoveObserver);
        }

        public void verifyPendingInstall(final int i, final int i2) throws android.os.RemoteException {
            if (i >= 0) {
                com.android.server.pm.PackageManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT", "Only package verification agents can verify applications");
            }
            final int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.PackageManagerService.IPackageManagerImpl.this.lambda$verifyPendingInstall$22(i, callingUid, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$verifyPendingInstall$22(int i, int i2, int i3) {
            if (i < 0) {
                i = -i;
            }
            com.android.server.pm.PackageVerificationState packageVerificationState = com.android.server.pm.PackageManagerService.this.mPendingVerification.get(i);
            if (packageVerificationState == null) {
                return;
            }
            if (!packageVerificationState.checkRequiredVerifierUid(i2) && !packageVerificationState.checkSufficientVerifierUid(i2)) {
                return;
            }
            android.os.Message obtainMessage = com.android.server.pm.PackageManagerService.this.mHandler.obtainMessage(15);
            com.android.server.pm.PackageVerificationResponse packageVerificationResponse = new com.android.server.pm.PackageVerificationResponse(i3, i2);
            obtainMessage.arg1 = i;
            obtainMessage.obj = packageVerificationResponse;
            com.android.server.pm.PackageManagerService.this.mHandler.sendMessage(obtainMessage);
        }

        public void registerPackageMonitorCallback(@android.annotation.NonNull android.os.IRemoteCallback iRemoteCallback, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.PackageManagerService.this.mPackageMonitorCallbackHelper.registerPackageMonitorCallback(iRemoteCallback, android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, true, true, "registerPackageMonitorCallback", com.android.server.pm.PackageManagerService.this.mContext.getPackageName()), callingUid);
        }

        public void unregisterPackageMonitorCallback(@android.annotation.NonNull android.os.IRemoteCallback iRemoteCallback) {
            com.android.server.pm.PackageManagerService.this.mPackageMonitorCallbackHelper.unregisterPackageMonitorCallback(iRemoteCallback);
        }

        public void requestPackageChecksums(@android.annotation.NonNull java.lang.String str, boolean z, int i, int i2, @android.annotation.Nullable java.util.List list, @android.annotation.NonNull android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener, int i3) {
            com.android.server.pm.PackageManagerService.this.requestChecksumsInternal(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str, z, i, i2, list, iOnChecksumsReadyListener, i3, com.android.server.pm.PackageManagerService.this.mInjector.getBackgroundExecutor(), com.android.server.pm.PackageManagerService.this.mInjector.getBackgroundHandler());
        }

        public void notifyPackagesReplacedReceived(java.lang.String[] strArr) {
            android.util.ArraySet<java.lang.String> notifyPackagesForReplacedReceived = com.android.server.pm.PackageManagerService.this.snapshotComputer().getNotifyPackagesForReplacedReceived(strArr);
            for (int i = 0; i < notifyPackagesForReplacedReceived.size(); i++) {
                com.android.server.pm.PackageManagerService.this.notifyInstallObserver(notifyPackagesForReplacedReceived.valueAt(i), false);
            }
        }

        public android.content.pm.ArchivedPackageParcel getArchivedPackage(@android.annotation.NonNull java.lang.String str, int i) {
            return com.android.server.pm.PackageManagerService.this.getArchivedPackageInternal(str, i);
        }

        public android.graphics.Bitmap getArchivedAppIcon(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str2) {
            return com.android.server.pm.PackageManagerService.this.mInstallerService.mPackageArchiver.getArchivedAppIcon(str, userHandle, str2);
        }

        public boolean isAppArchivable(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            return com.android.server.pm.PackageManagerService.this.mInstallerService.mPackageArchiver.isAppArchivable(str, userHandle);
        }

        public boolean waitForHandler(long j, boolean z) {
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            if (z) {
                android.os.Handler handler = com.android.server.pm.PackageManagerService.this.mBackgroundHandler;
                java.util.Objects.requireNonNull(countDownLatch);
                handler.post(new com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda0(countDownLatch));
            } else {
                android.os.Handler handler2 = com.android.server.pm.PackageManagerService.this.mHandler;
                java.util.Objects.requireNonNull(countDownLatch);
                handler2.post(new com.android.server.location.settings.SettingsStore$$ExternalSyntheticLambda0(countDownLatch));
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis() + j;
            while (countDownLatch.getCount() > 0) {
                try {
                    long currentTimeMillis2 = currentTimeMillis - java.lang.System.currentTimeMillis();
                    if (currentTimeMillis2 <= 0) {
                        return false;
                    }
                    return countDownLatch.await(currentTimeMillis2, java.util.concurrent.TimeUnit.MILLISECONDS);
                } catch (java.lang.InterruptedException e) {
                }
            }
            return true;
        }

        @android.annotation.Nullable
        public android.content.ComponentName getDomainVerificationAgent() {
            if (!com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(android.os.Binder.getCallingUid())) {
                throw new java.lang.SecurityException("Not allowed to query domain verification agent");
            }
            return com.android.server.pm.PackageManagerService.this.getDomainVerificationAgentComponentNameLPr(com.android.server.pm.PackageManagerService.this.snapshotComputer());
        }

        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (java.lang.RuntimeException e) {
                if (!(e instanceof java.lang.SecurityException) && !(e instanceof java.lang.IllegalArgumentException) && !(e instanceof android.os.ParcelableException)) {
                    android.util.Slog.wtf(com.android.server.pm.PackageManagerService.TAG, "Package Manager Unexpected Exception", e);
                }
                throw e;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            new com.android.server.pm.PackageManagerShellCommand(this, com.android.server.pm.PackageManagerService.this.mContext, com.android.server.pm.PackageManagerService.this.mDomainVerificationManager.getShell()).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            android.util.ArraySet arraySet;
            if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.pm.PackageManagerService.this.mContext, com.android.server.pm.PackageManagerService.TAG, printWriter)) {
                com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
                com.android.server.pm.KnownPackages knownPackages = new com.android.server.pm.KnownPackages(com.android.server.pm.PackageManagerService.this.mDefaultAppProvider, com.android.server.pm.PackageManagerService.this.mRequiredInstallerPackage, com.android.server.pm.PackageManagerService.this.mRequiredUninstallerPackage, com.android.server.pm.PackageManagerService.this.mSetupWizardPackage, com.android.server.pm.PackageManagerService.this.mRequiredVerifierPackages, com.android.server.pm.PackageManagerService.this.mDefaultTextClassifierPackage, com.android.server.pm.PackageManagerService.this.mSystemTextClassifierPackageName, com.android.server.pm.PackageManagerService.this.mRequiredPermissionControllerPackage, com.android.server.pm.PackageManagerService.this.mConfiguratorPackage, com.android.server.pm.PackageManagerService.this.mIncidentReportApproverPackage, com.android.server.pm.PackageManagerService.this.mAmbientContextDetectionPackage, com.android.server.pm.PackageManagerService.this.mWearableSensingPackage, com.android.server.pm.PackageManagerService.this.mAppPredictionServicePackage, com.android.server.pm.PackageManagerService.COMPANION_PACKAGE_NAME, com.android.server.pm.PackageManagerService.this.mRetailDemoPackage, com.android.server.pm.PackageManagerService.this.mOverlayConfigSignaturePackage, com.android.server.pm.PackageManagerService.this.mRecentsPackage);
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(com.android.server.pm.PackageManagerService.this.mAvailableFeatures);
                synchronized (com.android.server.pm.PackageManagerService.this.mProtectedBroadcasts) {
                    arraySet = new android.util.ArraySet((android.util.ArraySet) com.android.server.pm.PackageManagerService.this.mProtectedBroadcasts);
                }
                new com.android.server.pm.DumpHelper(com.android.server.pm.PackageManagerService.this.mPermissionManager, com.android.server.pm.PackageManagerService.this.mStorageEventHelper, com.android.server.pm.PackageManagerService.this.mDomainVerificationManager, com.android.server.pm.PackageManagerService.this.mInstallerService, com.android.server.pm.PackageManagerService.this.mRequiredVerifierPackages, knownPackages, com.android.server.pm.PackageManagerService.this.mChangedPackagesTracker, arrayMap, arraySet, com.android.server.pm.PackageManagerService.this.getPerUidReadTimeouts(snapshotComputer), com.android.server.pm.PackageManagerService.this.mSnapshotStatistics).doDump(snapshotComputer, fileDescriptor, printWriter, strArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PackageManagerInternalImpl extends com.android.server.pm.PackageManagerInternalBase {
        public PackageManagerInternalImpl() {
            super(com.android.server.pm.PackageManagerService.this);
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected android.content.Context getContext() {
            return com.android.server.pm.PackageManagerService.this.mContext;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.permission.PermissionManagerServiceInternal getPermissionManager() {
            return com.android.server.pm.PackageManagerService.this.mPermissionManager;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.AppDataHelper getAppDataHelper() {
            return com.android.server.pm.PackageManagerService.this.mAppDataHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.PackageObserverHelper getPackageObserverHelper() {
            return com.android.server.pm.PackageManagerService.this.mPackageObserverHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.ResolveIntentHelper getResolveIntentHelper() {
            return com.android.server.pm.PackageManagerService.this.mResolveIntentHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.SuspendPackageHelper getSuspendPackageHelper() {
            return com.android.server.pm.PackageManagerService.this.mSuspendPackageHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.DistractingPackageHelper getDistractingPackageHelper() {
            return com.android.server.pm.PackageManagerService.this.mDistractingPackageHelper;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.ProtectedPackages getProtectedPackages() {
            return com.android.server.pm.PackageManagerService.this.mProtectedPackages;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.UserNeedsBadgingCache getUserNeedsBadging() {
            return com.android.server.pm.PackageManagerService.this.mUserNeedsBadging;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.InstantAppRegistry getInstantAppRegistry() {
            return com.android.server.pm.PackageManagerService.this.mInstantAppRegistry;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.ApexManager getApexManager() {
            return com.android.server.pm.PackageManagerService.this.mApexManager;
        }

        @Override // com.android.server.pm.PackageManagerInternalBase
        @android.annotation.NonNull
        protected com.android.server.pm.dex.DexManager getDexManager() {
            return com.android.server.pm.PackageManagerService.this.mDexManager;
        }

        @Override // android.content.pm.PackageManagerInternal
        @android.annotation.NonNull
        public com.android.server.pm.dex.DynamicCodeLogger getDynamicCodeLogger() {
            return com.android.server.pm.PackageManagerService.this.mDynamicCodeLogger;
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isPlatformSigned(java.lang.String str) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                return false;
            }
            android.content.pm.SigningDetails signingDetails = packageStateInternal.getSigningDetails();
            return signingDetails.hasAncestorOrSelf(com.android.server.pm.PackageManagerService.this.mPlatformPackage.getSigningDetails()) || com.android.server.pm.PackageManagerService.this.mPlatformPackage.getSigningDetails().checkCapability(signingDetails, 4);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isDataRestoreSafe(byte[] bArr, java.lang.String str) {
            android.content.pm.SigningDetails signingDetails = snapshot().getSigningDetails(str);
            if (signingDetails == null) {
                return false;
            }
            return signingDetails.hasSha256Certificate(bArr, 1);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isDataRestoreSafe(android.content.pm.Signature signature, java.lang.String str) {
            android.content.pm.SigningDetails signingDetails = snapshot().getSigningDetails(str);
            if (signingDetails == null) {
                return false;
            }
            return signingDetails.hasCertificate(signature, 1);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean hasSignatureCapability(int i, int i2, @android.content.pm.SigningDetails.CertCapabilities int i3) {
            com.android.server.pm.Computer snapshot = snapshot();
            android.content.pm.SigningDetails signingDetails = snapshot.getSigningDetails(i);
            android.content.pm.SigningDetails signingDetails2 = snapshot.getSigningDetails(i2);
            return signingDetails.checkCapability(signingDetails2, i3) || signingDetails2.hasAncestorOrSelf(signingDetails);
        }

        @Override // android.content.pm.PackageManagerInternal
        public com.android.server.pm.PackageList getPackageList(@android.annotation.Nullable android.content.pm.PackageManagerInternal.PackageListObserver packageListObserver) {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            com.android.server.pm.PackageManagerService.this.forEachPackageState(snapshot(), new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PackageManagerService.PackageManagerInternalImpl.lambda$getPackageList$0(arrayList, (com.android.server.pm.pkg.PackageStateInternal) obj);
                }
            });
            com.android.server.pm.PackageList packageList = new com.android.server.pm.PackageList(arrayList, packageListObserver);
            if (packageListObserver != null) {
                com.android.server.pm.PackageManagerService.this.mPackageObserverHelper.addObserver(packageList);
            }
            return packageList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getPackageList$0(java.util.ArrayList arrayList, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
            if (pkg != null) {
                arrayList.add(pkg.getPackageName());
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        @android.annotation.Nullable
        public java.lang.String getDisabledSystemPackageName(@android.annotation.NonNull java.lang.String str) {
            com.android.server.pm.pkg.PackageStateInternal disabledSystemPackage = snapshot().getDisabledSystemPackage(str);
            com.android.server.pm.pkg.AndroidPackage pkg = disabledSystemPackage == null ? null : disabledSystemPackage.getPkg();
            if (pkg == null) {
                return null;
            }
            return pkg.getPackageName();
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isResolveActivityComponent(android.content.pm.ComponentInfo componentInfo) {
            return com.android.server.pm.PackageManagerService.this.mResolveActivity.packageName.equals(componentInfo.packageName) && com.android.server.pm.PackageManagerService.this.mResolveActivity.name.equals(componentInfo.name);
        }

        @Override // android.content.pm.PackageManagerInternal
        public long getCeDataInode(java.lang.String str, int i) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                return 0L;
            }
            return packageStateInternal.getUserStateOrDefault(i).getCeDataInode();
        }

        @Override // android.content.pm.PackageManagerInternal
        public void removeAllNonSystemPackageSuspensions(int i) {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            com.android.server.pm.PackageManagerService.this.mSuspendPackageHelper.removeSuspensionsBySuspendingPackage(snapshotComputer, snapshotComputer.getAllAvailablePackageNames(), new java.util.function.Predicate() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeAllNonSystemPackageSuspensions$1;
                    lambda$removeAllNonSystemPackageSuspensions$1 = com.android.server.pm.PackageManagerService.PackageManagerInternalImpl.lambda$removeAllNonSystemPackageSuspensions$1((android.content.pm.UserPackage) obj);
                    return lambda$removeAllNonSystemPackageSuspensions$1;
                }
            }, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$removeAllNonSystemPackageSuspensions$1(android.content.pm.UserPackage userPackage) {
            return !com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(userPackage.packageName);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void flushPackageRestrictions(int i) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.PackageManagerService.this.flushPackageRestrictionsAsUserInternalLocked(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        @Override // android.content.pm.PackageManagerInternal
        public java.lang.String[] setPackagesSuspendedByAdmin(int i, @android.annotation.NonNull java.lang.String[] strArr, boolean z) {
            return com.android.server.pm.PackageManagerService.this.mSuspendPackageHelper.setPackagesSuspended(com.android.server.pm.PackageManagerService.this.snapshotComputer(), strArr, z, null, null, null, android.content.pm.UserPackage.of(i, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME), i, 1000, false);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setDeviceAndProfileOwnerPackages(int i, java.lang.String str, android.util.SparseArray<java.lang.String> sparseArray) {
            com.android.server.pm.PackageManagerService.this.mProtectedPackages.setDeviceAndProfileOwnerPackages(i, str, sparseArray);
            android.util.ArraySet arraySet = new android.util.ArraySet();
            if (str != null) {
                arraySet.add(java.lang.Integer.valueOf(i));
            }
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (sparseArray.valueAt(i2) != null) {
                    removeAllNonSystemPackageSuspensions(sparseArray.keyAt(i2));
                }
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setExternalSourcesPolicy(android.content.pm.PackageManagerInternal.ExternalSourcesPolicy externalSourcesPolicy) {
            if (externalSourcesPolicy != null) {
                com.android.server.pm.PackageManagerService.this.mExternalSourcesPolicy = externalSourcesPolicy;
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isPackagePersistent(java.lang.String str) {
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot().getPackageStateInternal(str);
            return packageStateInternal != null && (pkg = packageStateInternal.getPkg()) != null && packageStateInternal.isSystem() && pkg.isPersistent();
        }

        @Override // android.content.pm.PackageManagerInternal
        public java.util.List<android.content.pm.PackageInfo> getOverlayPackages(int i) {
            android.content.pm.PackageInfo generatePackageInfo;
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = snapshotComputer.getPackageStates();
            for (int i2 = 0; i2 < packageStates.size(); i2++) {
                com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i2);
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = valueAt.getPkg();
                if (pkg != null && pkg.getOverlayTarget() != null && (generatePackageInfo = snapshotComputer.generatePackageInfo(valueAt, 0L, i)) != null) {
                    arrayList.add(generatePackageInfo);
                }
            }
            return arrayList;
        }

        @Override // android.content.pm.PackageManagerInternal
        public java.util.List<java.lang.String> getTargetPackageNames(int i) {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            com.android.server.pm.PackageManagerService.this.forEachPackageState(snapshot(), new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PackageManagerService.PackageManagerInternalImpl.lambda$getTargetPackageNames$2(arrayList, (com.android.server.pm.pkg.PackageStateInternal) obj);
                }
            });
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getTargetPackageNames$2(java.util.List list, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
            if (pkg != null && !pkg.isResourceOverlay()) {
                list.add(pkg.getPackageName());
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setEnabledOverlayPackages(int i, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> arrayMap, @android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.Set<java.lang.String> set2) {
            com.android.server.pm.PackageManagerService.this.setEnabledOverlayPackages(i, arrayMap, set, set2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void addIsolatedUid(int i, int i2) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.PackageManagerService.this.mIsolatedOwners.put(i, i2);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        @Override // android.content.pm.PackageManagerInternal
        public void removeIsolatedUid(int i) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.PackageManagerService.this.mIsolatedOwners.delete(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        @Override // android.content.pm.PackageManagerInternal
        public void notifyPackageUse(java.lang.String str, int i) {
            com.android.server.pm.PackageManagerService.this.notifyPackageUseInternal(str, i);
        }

        @Override // android.content.pm.PackageManagerInternal
        @android.annotation.Nullable
        public java.lang.String removeLegacyDefaultBrowserPackageName(int i) {
            java.lang.String removePendingDefaultBrowserLPw;
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    removePendingDefaultBrowserLPw = com.android.server.pm.PackageManagerService.this.mSettings.removePendingDefaultBrowserLPw(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return removePendingDefaultBrowserLPw;
        }

        @Override // android.content.pm.PackageManagerInternal
        public void uninstallApex(java.lang.String str, long j, int i, android.content.IntentSender intentSender, int i2) {
            if (!com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(android.os.Binder.getCallingUid())) {
                throw new java.lang.SecurityException("Not allowed to uninstall apexes");
            }
            com.android.server.pm.PackageInstallerService.PackageDeleteObserverAdapter packageDeleteObserverAdapter = new com.android.server.pm.PackageInstallerService.PackageDeleteObserverAdapter(com.android.server.pm.PackageManagerService.this.mContext, intentSender, str, false, i);
            if ((i2 & 2) == 0) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, "Can't uninstall an apex for a single user");
                return;
            }
            com.android.server.pm.ApexManager apexManager = com.android.server.pm.PackageManagerService.this.mApexManager;
            android.content.pm.PackageInfo packageInfo = snapshot().getPackageInfo(str, 1073741824L, 0);
            if (packageInfo == null) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, str + " is not an apex package");
                return;
            }
            if (j != -1 && packageInfo.getLongVersionCode() != j) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, "Active version " + packageInfo.getLongVersionCode() + " is not equal to " + j + "]");
                return;
            }
            if (!apexManager.uninstallApex(packageInfo.applicationInfo.sourceDir)) {
                packageDeleteObserverAdapter.onPackageDeleted(str, -5, "Failed to uninstall apex " + str);
                return;
            }
            packageDeleteObserverAdapter.onPackageDeleted(str, 1, null);
        }

        @Override // android.content.pm.PackageManagerInternal
        @java.lang.Deprecated
        public void legacyDumpProfiles(java.lang.String str, boolean z) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
            com.android.server.pm.pkg.AndroidPackage androidPackage = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackage(str);
            if (androidPackage == null) {
                throw new java.lang.IllegalArgumentException("Unknown package: " + str);
            }
            synchronized (com.android.server.pm.PackageManagerService.this.mInstallLock) {
                android.os.Trace.traceBegin(16384L, "dump profiles");
                com.android.server.pm.PackageManagerService.this.mArtManagerService.dumpProfiles(androidPackage, z);
                android.os.Trace.traceEnd(16384L);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        @java.lang.Deprecated
        public void legacyForceDexOpt(java.lang.String str) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
            com.android.server.pm.PackageManagerService.this.mDexOptHelper.forceDexOpt(com.android.server.pm.PackageManagerService.this.snapshotComputer(), str);
        }

        @Override // android.content.pm.PackageManagerInternal
        @java.lang.Deprecated
        public void legacyReconcileSecondaryDexFiles(java.lang.String str) throws com.android.server.pm.Installer.LegacyDexoptDisabledException {
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            if (snapshotComputer.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null || snapshotComputer.isInstantAppInternal(str, android.os.UserHandle.getCallingUserId(), 1000)) {
                return;
            }
            com.android.server.pm.PackageManagerService.this.mDexManager.reconcileSecondaryDexFiles(str);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void updateRuntimePermissionsFingerprint(int i) {
            com.android.server.pm.PackageManagerService.this.mSettings.updateRuntimePermissionsFingerprint(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void migrateLegacyObbData() {
            try {
                com.android.server.pm.PackageManagerService.this.mInstaller.migrateLegacyObbData();
            } catch (java.lang.Exception e) {
                android.util.Slog.wtf(com.android.server.pm.PackageManagerService.TAG, e);
            }
        }

        @Override // android.content.pm.PackageManagerInternal
        public void writeSettings(boolean z) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    if (z) {
                        com.android.server.pm.PackageManagerService.this.scheduleWriteSettings();
                    } else {
                        com.android.server.pm.PackageManagerService.this.writeSettingsLPrTEMP();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        @Override // android.content.pm.PackageManagerInternal
        public void writePermissionSettings(int[] iArr, boolean z) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    for (int i : iArr) {
                        com.android.server.pm.PackageManagerService.this.mSettings.writePermissionStateForUserLPr(i, !z);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        @Override // android.content.pm.PackageManagerInternal
        public com.android.server.pm.permission.LegacyPermissionSettings getLegacyPermissions() {
            com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings;
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    legacyPermissionSettings = com.android.server.pm.PackageManagerService.this.mSettings.mPermissions;
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return legacyPermissionSettings;
        }

        @Override // android.content.pm.PackageManagerInternal
        public com.android.permission.persistence.RuntimePermissionsState getLegacyPermissionsState(int i) {
            com.android.permission.persistence.RuntimePermissionsState legacyPermissionsState;
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    legacyPermissionsState = com.android.server.pm.PackageManagerService.this.mSettings.getLegacyPermissionsState(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return legacyPermissionsState;
        }

        @Override // android.content.pm.PackageManagerInternal
        public int getLegacyPermissionsVersion(int i) {
            int defaultRuntimePermissionsVersion;
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    defaultRuntimePermissionsVersion = com.android.server.pm.PackageManagerService.this.mSettings.getDefaultRuntimePermissionsVersion(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return defaultRuntimePermissionsVersion;
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isPermissionUpgradeNeeded(int i) {
            return com.android.server.pm.PackageManagerService.this.mSettings.isPermissionUpgradeNeeded(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setIntegrityVerificationResult(int i, int i2) {
            android.os.Message obtainMessage = com.android.server.pm.PackageManagerService.this.mHandler.obtainMessage(25);
            obtainMessage.arg1 = i;
            obtainMessage.obj = java.lang.Integer.valueOf(i2);
            com.android.server.pm.PackageManagerService.this.mHandler.sendMessage(obtainMessage);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void setVisibilityLogging(java.lang.String str, boolean z) {
            com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRootOrShell("Only the system or shell can set visibility logging.");
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = snapshot().getPackageStateInternal(str);
            if (packageStateInternal == null) {
                throw new java.lang.IllegalStateException("No package found for " + str);
            }
            com.android.server.pm.PackageManagerService.this.mAppsFilter.getFeatureConfig().enableLogging(packageStateInternal.getAppId(), z);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void clearBlockUninstallForUser(int i) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = com.android.server.pm.PackageManagerService.this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.PackageManagerService.this.mSettings.clearBlockUninstallLPw(i);
                    com.android.server.pm.PackageManagerService.this.mSettings.writePackageRestrictionsLPr(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean registerInstalledLoadingProgressCallback(java.lang.String str, android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback installedLoadingProgressCallback, int i) {
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, android.os.Binder.getCallingUid(), i);
            if (packageStateForInstalledAndFiltered == null) {
                return false;
            }
            if (!packageStateForInstalledAndFiltered.isLoading()) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Failed registering loading progress callback. Package is fully loaded.");
                return false;
            }
            if (com.android.server.pm.PackageManagerService.this.mIncrementalManager == null) {
                android.util.Slog.w(com.android.server.pm.PackageManagerService.TAG, "Failed registering loading progress callback. Incremental is not enabled");
                return false;
            }
            return com.android.server.pm.PackageManagerService.this.mIncrementalManager.registerLoadingProgressCallback(packageStateForInstalledAndFiltered.getPathString(), installedLoadingProgressCallback.getBinder());
        }

        @Override // android.content.pm.PackageManagerInternal
        public android.content.pm.IncrementalStatesInfo getIncrementalStatesInfo(@android.annotation.NonNull java.lang.String str, int i, int i2) {
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = com.android.server.pm.PackageManagerService.this.snapshotComputer().getPackageStateForInstalledAndFiltered(str, i, i2);
            if (packageStateForInstalledAndFiltered == null) {
                return null;
            }
            return new android.content.pm.IncrementalStatesInfo(packageStateForInstalledAndFiltered.isLoading(), packageStateForInstalledAndFiltered.getLoadingProgress(), packageStateForInstalledAndFiltered.getLoadingCompletedTime());
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isSameApp(@android.annotation.Nullable java.lang.String str, int i, int i2) {
            return isSameApp(str, 0L, i, i2);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isSameApp(@android.annotation.Nullable java.lang.String str, long j, int i, int i2) {
            if (str == null) {
                return false;
            }
            if (android.os.Process.isSdkSandboxUid(i)) {
                return str.equals(com.android.server.pm.PackageManagerService.this.mRequiredSdkSandboxPackage);
            }
            return android.os.UserHandle.isSameApp(snapshot().getPackageUid(str, j, i2), i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPackageProcessKilledForUninstall$3(java.lang.String str) {
            com.android.server.pm.PackageManagerService.this.notifyInstallObserver(str, true);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void onPackageProcessKilledForUninstall(final java.lang.String str) {
            com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.PackageManagerService.PackageManagerInternalImpl.this.lambda$onPackageProcessKilledForUninstall$3(str);
                }
            });
        }

        @Override // android.content.pm.PackageManagerInternal
        public int[] getDistractingPackageRestrictionsAsUser(@android.annotation.NonNull java.lang.String[] strArr, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            com.android.server.pm.Computer snapshotComputer = com.android.server.pm.PackageManagerService.this.snapshotComputer();
            java.util.Objects.requireNonNull(strArr, "packageNames cannot be null");
            return com.android.server.pm.PackageManagerService.this.mDistractingPackageHelper.getDistractingPackageRestrictionsAsUser(snapshotComputer, strArr, i, callingUid);
        }

        @Override // android.content.pm.PackageManagerInternal
        public android.content.pm.ParceledListSlice<android.content.pm.PackageInstaller.SessionInfo> getHistoricalSessions(int i) {
            return com.android.server.pm.PackageManagerService.this.mInstallerService.getHistoricalSessions(i);
        }

        @Override // android.content.pm.PackageManagerInternal
        public com.android.server.pm.PackageArchiver getPackageArchiver() {
            return com.android.server.pm.PackageManagerService.this.mInstallerService.mPackageArchiver;
        }

        @Override // android.content.pm.PackageManagerInternal
        public void sendPackageRestartedBroadcast(@android.annotation.NonNull final java.lang.String str, int i, final int i2) {
            android.os.Bundle bundle;
            int[] iArr;
            int userId = android.os.UserHandle.getUserId(i);
            final int[] resolveUserIds = com.android.server.pm.PackageManagerService.this.resolveUserIds(userId);
            final android.util.SparseArray<int[]> visibilityAllowLists = com.android.server.pm.PackageManagerService.this.snapshotComputer().getVisibilityAllowLists(str, resolveUserIds);
            final android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putInt("android.intent.extra.UID", i);
            bundle2.putInt("android.intent.extra.user_handle", userId);
            if (android.content.pm.Flags.stayStopped()) {
                bundle2.putLong("android.intent.extra.TIME", android.os.SystemClock.elapsedRealtime());
                com.android.server.pm.PackageManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.PackageManagerService.PackageManagerInternalImpl.this.lambda$sendPackageRestartedBroadcast$4(str, bundle2, i2, resolveUserIds, visibilityAllowLists);
                    }
                });
                bundle = bundle2;
                iArr = resolveUserIds;
            } else {
                bundle = bundle2;
                iArr = resolveUserIds;
                com.android.server.pm.PackageManagerService.this.mBroadcastHelper.sendPackageBroadcast("android.intent.action.PACKAGE_RESTARTED", str, bundle2, i2, null, null, resolveUserIds, null, visibilityAllowLists, null, null);
            }
            com.android.server.pm.PackageManagerService.this.mPackageMonitorCallbackHelper.notifyPackageMonitor("android.intent.action.PACKAGE_RESTARTED", str, bundle, iArr, null, visibilityAllowLists, com.android.server.pm.PackageManagerService.this.mHandler, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendPackageRestartedBroadcast$4(java.lang.String str, android.os.Bundle bundle, int i, int[] iArr, android.util.SparseArray sparseArray) {
            com.android.server.pm.PackageManagerService.this.mBroadcastHelper.sendPackageBroadcast("android.intent.action.PACKAGE_RESTARTED", str, bundle, i, null, null, iArr, null, sparseArray, null, null);
        }

        @Override // android.content.pm.PackageManagerInternal
        public void sendPackageDataClearedBroadcast(@android.annotation.NonNull java.lang.String str, int i, int i2, boolean z, boolean z2) {
            int[] visibilityAllowList = com.android.server.pm.PackageManagerService.this.snapshotComputer().getVisibilityAllowList(str, i2);
            android.content.Intent intent = new android.content.Intent("android.intent.action.PACKAGE_DATA_CLEARED", android.net.Uri.fromParts("package", str, null));
            intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.HE_AAC_V1);
            intent.putExtra("android.intent.extra.UID", i);
            intent.putExtra("android.intent.extra.user_handle", i2);
            if (z) {
                intent.putExtra("android.intent.extra.IS_RESTORE", true);
            }
            if (z2) {
                intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
            }
            com.android.server.pm.PackageManagerService.this.mBroadcastHelper.sendPackageBroadcastWithIntent(intent, i2, z2, 0, visibilityAllowList, null, null, null);
            com.android.server.pm.PackageManagerService.this.mPackageMonitorCallbackHelper.notifyPackageMonitorWithIntent(intent, i2, visibilityAllowList, com.android.server.pm.PackageManagerService.this.mHandler);
        }

        @Override // android.content.pm.PackageManagerInternal
        public boolean isUpgradingFromLowerThan(int i) {
            return (com.android.server.pm.PackageManagerService.this.mPriorSdkVersion != -1) && com.android.server.pm.PackageManagerService.this.mPriorSdkVersion < i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEnabledOverlayPackages(final int i, @android.annotation.NonNull final android.util.ArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> arrayMap, @android.annotation.NonNull final java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.Set<java.lang.String> set2) {
        java.lang.String str;
        int i2;
        android.content.pm.overlay.OverlayPaths overlayPaths;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        java.lang.String str2;
        int i3;
        java.util.List list;
        android.content.pm.overlay.OverlayPaths overlayPaths2;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2;
        java.lang.String str3;
        android.util.ArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> arrayMap2 = arrayMap;
        final android.util.ArrayMap arrayMap3 = new android.util.ArrayMap();
        final int size = arrayMap.size();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mOverlayPathsLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.Computer snapshotComputer = snapshotComputer();
                int i4 = 0;
                while (i4 < size) {
                    java.lang.String keyAt = arrayMap2.keyAt(i4);
                    android.content.pm.overlay.OverlayPaths valueAt = arrayMap2.valueAt(i4);
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 = snapshotComputer.getPackageStateInternal(keyAt);
                    com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal3 == null ? null : packageStateInternal3.getPkg();
                    if (keyAt == null) {
                        str = keyAt;
                        i2 = i4;
                    } else if (pkg == null) {
                        str = keyAt;
                        i2 = i4;
                    } else {
                        if (java.util.Objects.equals(packageStateInternal3.getUserStateOrDefault(i).getOverlayPaths(), valueAt)) {
                            i2 = i4;
                        } else {
                            if (pkg.getLibraryNames() == null) {
                                overlayPaths = valueAt;
                                packageStateInternal = packageStateInternal3;
                                str2 = keyAt;
                                i2 = i4;
                            } else {
                                java.util.List libraryNames = pkg.getLibraryNames();
                                int i5 = 0;
                                while (i5 < libraryNames.size()) {
                                    java.lang.String str4 = (java.lang.String) libraryNames.get(i5);
                                    int i6 = i4;
                                    android.content.pm.SharedLibraryInfo sharedLibraryInfo = snapshotComputer.getSharedLibraryInfo(str4, -1L);
                                    if (sharedLibraryInfo == null) {
                                        i3 = i5;
                                        list = libraryNames;
                                        overlayPaths2 = valueAt;
                                        packageStateInternal2 = packageStateInternal3;
                                        str3 = keyAt;
                                    } else {
                                        i3 = i5;
                                        list = libraryNames;
                                        overlayPaths2 = valueAt;
                                        packageStateInternal2 = packageStateInternal3;
                                        java.lang.String str5 = keyAt;
                                        java.util.List list2 = (java.util.List) snapshotComputer.getPackagesUsingSharedLibrary(sharedLibraryInfo, 0L, 1000, i).first;
                                        if (list2 == null) {
                                            str3 = str5;
                                        } else {
                                            android.util.ArraySet arraySet = null;
                                            for (int i7 = 0; i7 < list2.size(); i7++) {
                                                android.content.pm.VersionedPackage versionedPackage = (android.content.pm.VersionedPackage) list2.get(i7);
                                                com.android.server.pm.pkg.PackageStateInternal packageStateInternal4 = snapshotComputer.getPackageStateInternal(versionedPackage.getPackageName());
                                                if (packageStateInternal4 != null && canSetOverlayPaths(packageStateInternal4.getUserStateOrDefault(i).getSharedLibraryOverlayPaths().get(str4), overlayPaths2)) {
                                                    java.lang.String packageName = versionedPackage.getPackageName();
                                                    android.util.ArraySet add = com.android.internal.util.ArrayUtils.add(arraySet, packageName);
                                                    set.add(packageName);
                                                    arraySet = add;
                                                }
                                            }
                                            if (arraySet == null) {
                                                str3 = str5;
                                            } else {
                                                str3 = str5;
                                                android.util.ArrayMap arrayMap4 = (android.util.ArrayMap) arrayMap3.get(str3);
                                                if (arrayMap4 == null) {
                                                    arrayMap4 = new android.util.ArrayMap();
                                                    arrayMap3.put(str3, arrayMap4);
                                                }
                                                arrayMap4.put(str4, arraySet);
                                            }
                                        }
                                    }
                                    keyAt = str3;
                                    i5 = i3 + 1;
                                    packageStateInternal3 = packageStateInternal2;
                                    valueAt = overlayPaths2;
                                    i4 = i6;
                                    libraryNames = list;
                                }
                                overlayPaths = valueAt;
                                packageStateInternal = packageStateInternal3;
                                str2 = keyAt;
                                i2 = i4;
                            }
                            if (canSetOverlayPaths(packageStateInternal.getUserStateOrDefault(i).getOverlayPaths(), overlayPaths)) {
                                set.add(str2);
                            }
                        }
                        i4 = i2 + 1;
                        arrayMap2 = arrayMap;
                    }
                    android.util.Slog.e(TAG, "failed to find package " + str);
                    set2.add(str);
                    i4 = i2 + 1;
                    arrayMap2 = arrayMap;
                }
                commitPackageStateMutation(null, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.PackageManagerService.lambda$setEnabledOverlayPackages$59(size, arrayMap, set, i, arrayMap3, (com.android.server.pm.pkg.mutate.PackageStateMutator) obj);
                    }
                });
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
        if (i == 0) {
            for (int i8 = 0; i8 < size; i8++) {
                maybeUpdateSystemOverlays(arrayMap.keyAt(i8), arrayMap.valueAt(i8));
            }
        }
        invalidatePackageInfoCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setEnabledOverlayPackages$59(int i, android.util.ArrayMap arrayMap, java.util.Set set, int i2, android.util.ArrayMap arrayMap2, com.android.server.pm.pkg.mutate.PackageStateMutator packageStateMutator) {
        for (int i3 = 0; i3 < i; i3++) {
            java.lang.String str = (java.lang.String) arrayMap.keyAt(i3);
            android.content.pm.overlay.OverlayPaths overlayPaths = (android.content.pm.overlay.OverlayPaths) arrayMap.valueAt(i3);
            if (set.contains(str)) {
                packageStateMutator.forPackage(str).userState(i2).setOverlayPaths(overlayPaths);
                android.util.ArrayMap arrayMap3 = (android.util.ArrayMap) arrayMap2.get(str);
                if (arrayMap3 != null) {
                    for (int i4 = 0; i4 < arrayMap3.size(); i4++) {
                        java.lang.String str2 = (java.lang.String) arrayMap3.keyAt(i4);
                        android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap3.valueAt(i4);
                        for (int i5 = 0; i5 < arraySet.size(); i5++) {
                            packageStateMutator.forPackage((java.lang.String) arraySet.valueAt(i5)).userState(i2).setOverlayPathsForLibrary(str2, overlayPaths);
                        }
                    }
                }
            }
        }
    }

    private boolean canSetOverlayPaths(android.content.pm.overlay.OverlayPaths overlayPaths, android.content.pm.overlay.OverlayPaths overlayPaths2) {
        if (java.util.Objects.equals(overlayPaths, overlayPaths2)) {
            return false;
        }
        return ((overlayPaths == null && overlayPaths2.isEmpty()) || (overlayPaths2 == null && overlayPaths.isEmpty())) ? false : true;
    }

    private void maybeUpdateSystemOverlays(java.lang.String str, android.content.pm.overlay.OverlayPaths overlayPaths) {
        if (!this.mResolverReplaced) {
            if (str.equals(PLATFORM_PACKAGE_NAME)) {
                if (overlayPaths == null) {
                    this.mPlatformPackageOverlayPaths = null;
                    this.mPlatformPackageOverlayResourceDirs = null;
                } else {
                    this.mPlatformPackageOverlayPaths = (java.lang.String[]) overlayPaths.getOverlayPaths().toArray(new java.lang.String[0]);
                    this.mPlatformPackageOverlayResourceDirs = (java.lang.String[]) overlayPaths.getResourceDirs().toArray(new java.lang.String[0]);
                }
                applyUpdatedSystemOverlayPaths();
                return;
            }
            return;
        }
        if (str.equals(this.mResolveActivity.applicationInfo.packageName)) {
            if (overlayPaths == null) {
                this.mReplacedResolverPackageOverlayPaths = null;
                this.mReplacedResolverPackageOverlayResourceDirs = null;
            } else {
                this.mReplacedResolverPackageOverlayPaths = (java.lang.String[]) overlayPaths.getOverlayPaths().toArray(new java.lang.String[0]);
                this.mReplacedResolverPackageOverlayResourceDirs = (java.lang.String[]) overlayPaths.getResourceDirs().toArray(new java.lang.String[0]);
            }
            applyUpdatedSystemOverlayPaths();
        }
    }

    private void applyUpdatedSystemOverlayPaths() {
        if (this.mAndroidApplication == null) {
            android.util.Slog.i(TAG, "Skipped the AndroidApplication overlay paths update - no app yet");
        } else {
            this.mAndroidApplication.overlayPaths = this.mPlatformPackageOverlayPaths;
            this.mAndroidApplication.resourceDirs = this.mPlatformPackageOverlayResourceDirs;
        }
        if (this.mResolverReplaced) {
            this.mResolveActivity.applicationInfo.overlayPaths = this.mReplacedResolverPackageOverlayPaths;
            this.mResolveActivity.applicationInfo.resourceDirs = this.mReplacedResolverPackageOverlayResourceDirs;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceAdjustRuntimePermissionsPolicyOrUpgradeRuntimePermissions(@android.annotation.NonNull java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.UPGRADE_RUNTIME_PERMISSIONS") != 0) {
            throw new java.lang.SecurityException(str + " requires android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY or android.permission.UPGRADE_RUNTIME_PERMISSIONS");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    @java.lang.Deprecated
    com.android.server.pm.PackageSetting getPackageSettingForMutation(java.lang.String str) {
        return this.mSettings.getPackageLPr(str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    @java.lang.Deprecated
    com.android.server.pm.PackageSetting getDisabledPackageSettingForMutation(java.lang.String str) {
        return this.mSettings.getDisabledSystemPkgLPr(str);
    }

    @java.lang.Deprecated
    void forEachPackageSetting(java.util.function.Consumer<com.android.server.pm.PackageSetting> consumer) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                int size = this.mSettings.getPackagesLocked().size();
                for (int i = 0; i < size; i++) {
                    consumer.accept(this.mSettings.getPackagesLocked().valueAt(i));
                }
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
    }

    void forEachPackageState(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.function.Consumer<com.android.server.pm.pkg.PackageStateInternal> consumer) {
        forEachPackageState(computer.getPackageStates(), consumer);
    }

    void forEachPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.function.Consumer<com.android.server.pm.pkg.AndroidPackage> consumer) {
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        int size = packageStates.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i);
            if (valueAt.getPkg() != null) {
                consumer.accept(valueAt.getPkg());
            }
        }
    }

    void forEachPackageInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.util.function.Consumer<com.android.internal.pm.parsing.pkg.AndroidPackageInternal> consumer) {
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = computer.getPackageStates();
        int size = packageStates.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i);
            if (valueAt.getPkg() != null) {
                consumer.accept(valueAt.getPkg());
            }
        }
    }

    private void forEachPackageState(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> arrayMap, @android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.pkg.PackageStateInternal> consumer) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(arrayMap.valueAt(i));
        }
    }

    void forEachInstalledPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull final java.util.function.Consumer<com.android.server.pm.pkg.AndroidPackage> consumer, final int i) {
        forEachPackageState(computer.getPackageStates(), new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.PackageManagerService.lambda$forEachInstalledPackage$60(i, consumer, (com.android.server.pm.pkg.PackageStateInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$forEachInstalledPackage$60(int i, java.util.function.Consumer consumer, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal.getPkg() != null && packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
            consumer.accept(packageStateInternal.getPkg());
        }
    }

    boolean isHistoricalPackageUsageAvailable() {
        return this.mPackageUsage.isHistoricalPackageUsageAvailable();
    }

    public com.android.server.pm.CompilerStats.PackageStats getOrCreateCompilerPackageStats(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return getOrCreateCompilerPackageStats(androidPackage.getPackageName());
    }

    public com.android.server.pm.CompilerStats.PackageStats getOrCreateCompilerPackageStats(java.lang.String str) {
        return this.mCompilerStats.getOrCreatePackageStats(str);
    }

    void grantImplicitAccess(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, android.content.Intent intent, int i2, int i3, boolean z, boolean z2) {
        boolean grantImplicitAccess;
        com.android.server.pm.pkg.AndroidPackage androidPackage = computer.getPackage(i3);
        int uid = android.os.UserHandle.getUid(i, i2);
        if (androidPackage == null || computer.getPackage(uid) == null) {
            return;
        }
        if (computer.isInstantAppInternal(androidPackage.getPackageName(), i, i3)) {
            if (!z) {
                return;
            } else {
                grantImplicitAccess = this.mInstantAppRegistry.grantInstantAccess(i, intent, i2, android.os.UserHandle.getAppId(i3));
            }
        } else {
            grantImplicitAccess = this.mAppsFilter.grantImplicitAccess(uid, i3, z2);
        }
        if (grantImplicitAccess) {
            android.app.ApplicationPackageManager.invalidateGetPackagesForUidCache();
        }
    }

    boolean canHaveOatDir(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
            return false;
        }
        return com.android.server.pm.parsing.pkg.AndroidPackageUtils.canHaveOatDir(packageStateInternal, packageStateInternal.getPkg());
    }

    long deleteOatArtifactsOfPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str) {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRootOrShell("Only the system or shell can delete oat artifacts");
        if (com.android.server.pm.DexOptHelper.useArtService()) {
            com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
            try {
                try {
                    try {
                        long freedBytes = com.android.server.pm.DexOptHelper.getArtManagerLocal().deleteDexoptArtifacts(withFilteredSnapshot, str).getFreedBytes();
                        if (withFilteredSnapshot != null) {
                            withFilteredSnapshot.close();
                        }
                        return freedBytes;
                    } catch (java.lang.IllegalStateException e) {
                        android.util.Slog.wtfStack(TAG, e.toString());
                        if (withFilteredSnapshot != null) {
                            withFilteredSnapshot.close();
                        }
                        return -1L;
                    }
                } catch (java.lang.IllegalArgumentException e2) {
                    android.util.Log.e(TAG, e2.toString());
                    if (withFilteredSnapshot != null) {
                        withFilteredSnapshot.close();
                    }
                    return -1L;
                }
            } catch (java.lang.Throwable th) {
                if (withFilteredSnapshot != null) {
                    try {
                        withFilteredSnapshot.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
            return -1L;
        }
        try {
            return this.mDexManager.deleteOptimizedFiles(com.android.server.pm.dex.ArtUtils.createArtPackageInfo(packageStateInternal.getPkg(), packageStateInternal));
        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e3) {
            throw new java.lang.RuntimeException(e3);
        }
    }

    java.util.List<java.lang.String> getMimeGroupInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String str, java.lang.String str2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return java.util.Collections.emptyList();
        }
        java.util.Map<java.lang.String, java.util.Set<java.lang.String>> mimeGroups = packageStateInternal.getMimeGroups();
        java.util.Set<java.lang.String> set = mimeGroups != null ? mimeGroups.get(str2) : null;
        if (set == null) {
            throw new java.lang.IllegalArgumentException("Unknown MIME group " + str2 + " for package " + str);
        }
        return new java.util.ArrayList(set);
    }

    void writeSettingsLPrTEMP(boolean z) {
        snapshotComputer(false);
        this.mPermissionManager.writeLegacyPermissionsTEMP(this.mSettings.mPermissions);
        this.mSettings.writeLPr(this.mLiveComputer, z);
    }

    void writeSettingsLPrTEMP() {
        writeSettingsLPrTEMP(false);
    }

    @Override // android.content.pm.TestUtilityService
    public void verifyHoldLockToken(android.os.IBinder iBinder) {
        if (!android.os.Build.IS_DEBUGGABLE) {
            throw new java.lang.SecurityException("holdLock requires a debuggable build");
        }
        if (iBinder == null) {
            throw new java.lang.SecurityException("null holdLockToken");
        }
        if (iBinder.queryLocalInterface("holdLock:" + android.os.Binder.getCallingUid()) != this) {
            throw new java.lang.SecurityException("Invalid holdLock() token");
        }
    }

    static java.lang.String getDefaultTimeouts() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getString("package_manager_service", PROPERTY_INCFS_DEFAULT_TIMEOUTS, "");
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    static java.lang.String getKnownDigestersList() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getString("package_manager_service", PROPERTY_KNOWN_DIGESTERS_LIST, "");
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    static boolean isPreapprovalRequestAvailable() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_isCameraCompatControlForStretchedIssuesEnabled)) {
                return android.provider.DeviceConfig.getBoolean("package_manager_service", PROPERTY_IS_PRE_APPROVAL_REQUEST_AVAILABLE, true);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    static boolean isUpdateOwnershipEnforcementAvailable() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getBoolean("package_manager_service", PROPERTY_IS_UPDATE_OWNERSHIP_ENFORCEMENT_AVAILABLE, true);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    public android.os.incremental.PerUidReadTimeouts[] getPerUidReadTimeouts(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        android.os.incremental.PerUidReadTimeouts[] perUidReadTimeoutsArr = this.mPerUidReadTimeoutsCache;
        if (perUidReadTimeoutsArr == null) {
            android.os.incremental.PerUidReadTimeouts[] parsePerUidReadTimeouts = parsePerUidReadTimeouts(computer);
            this.mPerUidReadTimeoutsCache = parsePerUidReadTimeouts;
            return parsePerUidReadTimeouts;
        }
        return perUidReadTimeoutsArr;
    }

    @android.annotation.NonNull
    private android.os.incremental.PerUidReadTimeouts[] parsePerUidReadTimeouts(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        java.util.List<com.android.server.pm.PerPackageReadTimeouts> parseDigestersList = com.android.server.pm.PerPackageReadTimeouts.parseDigestersList(getDefaultTimeouts(), getKnownDigestersList());
        if (parseDigestersList.size() == 0) {
            return EMPTY_PER_UID_READ_TIMEOUTS_ARRAY;
        }
        int[] userIds = this.mInjector.getUserManagerService().getUserIds();
        java.util.ArrayList arrayList = new java.util.ArrayList(parseDigestersList.size());
        int size = parseDigestersList.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.PerPackageReadTimeouts perPackageReadTimeouts = parseDigestersList.get(i);
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(perPackageReadTimeouts.packageName);
            if (packageStateInternal != null && packageStateInternal.getAppId() >= 10000) {
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
                if (pkg.getLongVersionCode() >= perPackageReadTimeouts.versionCodes.minVersionCode && pkg.getLongVersionCode() <= perPackageReadTimeouts.versionCodes.maxVersionCode && (perPackageReadTimeouts.sha256certificate == null || pkg.getSigningDetails().hasSha256Certificate(perPackageReadTimeouts.sha256certificate))) {
                    for (int i2 : userIds) {
                        if (packageStateInternal.getUserStateOrDefault(i2).isInstalled()) {
                            int uid = android.os.UserHandle.getUid(i2, packageStateInternal.getAppId());
                            android.os.incremental.PerUidReadTimeouts perUidReadTimeouts = new android.os.incremental.PerUidReadTimeouts();
                            perUidReadTimeouts.uid = uid;
                            perUidReadTimeouts.minTimeUs = perPackageReadTimeouts.timeouts.minTimeUs;
                            perUidReadTimeouts.minPendingTimeUs = perPackageReadTimeouts.timeouts.minPendingTimeUs;
                            perUidReadTimeouts.maxPendingTimeUs = perPackageReadTimeouts.timeouts.maxPendingTimeUs;
                            arrayList.add(perUidReadTimeouts);
                        }
                    }
                }
            }
        }
        return (android.os.incremental.PerUidReadTimeouts[]) arrayList.toArray(new android.os.incremental.PerUidReadTimeouts[arrayList.size()]);
    }

    void setKeepUninstalledPackagesInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, java.util.List<java.lang.String> list) {
        com.android.internal.util.Preconditions.checkNotNull(list);
        synchronized (this.mKeepUninstalledPackages) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mKeepUninstalledPackages);
                arrayList.removeAll(list);
                this.mKeepUninstalledPackages.clear();
                this.mKeepUninstalledPackages.addAll(list);
                for (int i = 0; i < arrayList.size(); i++) {
                    deletePackageIfUnused(computer, (java.lang.String) arrayList.get(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean shouldKeepUninstalledPackageLPr(java.lang.String str) {
        boolean contains;
        synchronized (this.mKeepUninstalledPackages) {
            contains = this.mKeepUninstalledPackages.contains(str);
        }
        return contains;
    }

    boolean getSafeMode() {
        return this.mSafeMode;
    }

    android.content.ComponentName getResolveComponentName() {
        return this.mResolveComponentName;
    }

    com.android.server.pm.DefaultAppProvider getDefaultAppProvider() {
        return this.mDefaultAppProvider;
    }

    java.io.File getCacheDir() {
        return this.mCacheDir;
    }

    com.android.server.pm.PackageProperty getPackageProperty() {
        return this.mPackageProperty;
    }

    com.android.server.utils.WatchedArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedInstrumentation> getInstrumentation() {
        return this.mInstrumentation;
    }

    int getSdkVersion() {
        return this.mSdkVersion;
    }

    void addAllPackageProperties(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        this.mPackageProperty.addAllProperties(androidPackage);
    }

    void addInstrumentation(android.content.ComponentName componentName, com.android.internal.pm.pkg.component.ParsedInstrumentation parsedInstrumentation) {
        this.mInstrumentation.put(componentName, parsedInstrumentation);
    }

    java.lang.String[] getKnownPackageNamesInternal(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, int i2) {
        return new com.android.server.pm.KnownPackages(this.mDefaultAppProvider, this.mRequiredInstallerPackage, this.mRequiredUninstallerPackage, this.mSetupWizardPackage, this.mRequiredVerifierPackages, this.mDefaultTextClassifierPackage, this.mSystemTextClassifierPackageName, this.mRequiredPermissionControllerPackage, this.mConfiguratorPackage, this.mIncidentReportApproverPackage, this.mAmbientContextDetectionPackage, this.mWearableSensingPackage, this.mAppPredictionServicePackage, COMPANION_PACKAGE_NAME, this.mRetailDemoPackage, this.mOverlayConfigSignaturePackage, this.mRecentsPackage).getKnownPackageNames(computer, i, i2);
    }

    java.lang.String getActiveLauncherPackageName(int i) {
        return this.mDefaultAppProvider.getDefaultHome(i);
    }

    boolean setActiveLauncherPackage(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.util.function.Consumer<java.lang.Boolean> consumer) {
        return this.mDefaultAppProvider.setDefaultHome(str, i, this.mContext.getMainExecutor(), consumer);
    }

    @android.annotation.Nullable
    java.lang.String getDefaultBrowser(int i) {
        return this.mDefaultAppProvider.getDefaultBrowser(i);
    }

    void setDefaultBrowser(@android.annotation.Nullable java.lang.String str, int i) {
        this.mDefaultAppProvider.setDefaultBrowser(str, i);
    }

    com.android.server.pm.PackageUsage getPackageUsage() {
        return this.mPackageUsage;
    }

    java.lang.String getModuleMetadataPackageName() {
        return this.mModuleInfoProvider.getPackageName();
    }

    java.io.File getAppInstallDir() {
        return this.mAppInstallDir;
    }

    boolean isExpectingBetter(java.lang.String str) {
        return this.mInitAppsHelper.isExpectingBetter(str);
    }

    int getDefParseFlags() {
        return this.mDefParseFlags;
    }

    void setUpCustomResolverActivity(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.PackageSetting packageSetting) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mResolverReplaced = true;
                this.mResolveActivity.applicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(androidPackage, 0L, com.android.server.pm.pkg.PackageUserStateInternal.DEFAULT, 0, packageSetting);
                this.mResolveActivity.name = this.mCustomResolverComponentName.getClassName();
                this.mResolveActivity.packageName = androidPackage.getPackageName();
                this.mResolveActivity.processName = androidPackage.getProcessName();
                this.mResolveActivity.launchMode = 0;
                this.mResolveActivity.flags = 66336;
                this.mResolveActivity.theme = 0;
                this.mResolveActivity.exported = true;
                this.mResolveActivity.enabled = true;
                this.mResolveInfo.activityInfo = this.mResolveActivity;
                this.mResolveInfo.priority = 0;
                this.mResolveInfo.preferredOrder = 0;
                this.mResolveInfo.match = 0;
                this.mResolveComponentName = this.mCustomResolverComponentName;
                onChanged();
                android.util.Slog.i(TAG, "Replacing default ResolverActivity with custom activity: " + this.mResolveComponentName);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
    }

    void setPlatformPackage(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.PackageSetting packageSetting) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPlatformPackage = androidPackage;
                this.mAndroidApplication = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(androidPackage, 0L, com.android.server.pm.pkg.PackageUserStateInternal.DEFAULT, 0, packageSetting);
                if (!this.mResolverReplaced) {
                    this.mResolveActivity.applicationInfo = this.mAndroidApplication;
                    this.mResolveActivity.name = com.android.internal.app.ResolverActivity.class.getName();
                    this.mResolveActivity.packageName = this.mAndroidApplication.packageName;
                    this.mResolveActivity.processName = "system:ui";
                    this.mResolveActivity.launchMode = 0;
                    this.mResolveActivity.documentLaunchMode = 3;
                    this.mResolveActivity.flags = 70176;
                    this.mResolveActivity.theme = android.R.style.Theme.Material.Dialog.Alert;
                    this.mResolveActivity.exported = true;
                    this.mResolveActivity.enabled = true;
                    this.mResolveActivity.resizeMode = 2;
                    this.mResolveActivity.configChanges = 3504;
                    this.mResolveInfo.activityInfo = this.mResolveActivity;
                    this.mResolveInfo.priority = 0;
                    this.mResolveInfo.preferredOrder = 0;
                    this.mResolveInfo.match = 0;
                    this.mResolveComponentName = new android.content.ComponentName(this.mAndroidApplication.packageName, this.mResolveActivity.name);
                }
                onChanged();
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
        applyUpdatedSystemOverlayPaths();
    }

    android.content.pm.ApplicationInfo getCoreAndroidApplication() {
        return this.mAndroidApplication;
    }

    boolean isSystemReady() {
        return this.mSystemReady;
    }

    com.android.server.pm.pkg.AndroidPackage getPlatformPackage() {
        return this.mPlatformPackage;
    }

    boolean isPreNMR1Upgrade() {
        return this.mIsPreNMR1Upgrade;
    }

    boolean isOverlayMutable(java.lang.String str) {
        return this.mOverlayConfig.isMutable(str);
    }

    int getSystemPackageScanFlags(java.io.File file) {
        com.android.server.pm.ScanPartition scanPartition;
        java.util.List<com.android.server.pm.ScanPartition> dirsToScanAsSystem = this.mInitAppsHelper.getDirsToScanAsSystem();
        int size = dirsToScanAsSystem.size();
        do {
            size--;
            if (size < 0) {
                return 65536;
            }
            scanPartition = dirsToScanAsSystem.get(size);
        } while (!scanPartition.containsFile(file));
        int i = 65536 | scanPartition.scanFlag;
        if (scanPartition.containsPrivApp(file)) {
            return i | 131072;
        }
        return i;
    }

    android.util.Pair<java.lang.Integer, java.lang.Integer> getSystemPackageRescanFlagsAndReparseFlags(java.io.File file, int i, int i2) {
        int i3;
        java.util.List<com.android.server.pm.ScanPartition> dirsToScanAsSystem = this.mInitAppsHelper.getDirsToScanAsSystem();
        int size = dirsToScanAsSystem.size() - 1;
        while (true) {
            if (size < 0) {
                i2 = 0;
                i3 = 0;
                break;
            }
            com.android.server.pm.ScanPartition scanPartition = dirsToScanAsSystem.get(size);
            if (scanPartition.containsPrivApp(file)) {
                i3 = 131072 | i | scanPartition.scanFlag;
                break;
            }
            if (!scanPartition.containsApp(file)) {
                size--;
            } else {
                i3 = scanPartition.scanFlag | i;
                break;
            }
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i2));
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState recordInitialState() {
        return this.mPackageStateMutator.initialState(this.mChangedPackagesTracker.getSequenceNumber());
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.mutate.PackageStateMutator.Result commitPackageStateMutation(@android.annotation.Nullable com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState initialState, @android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.pkg.mutate.PackageStateMutator> consumer) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPackageStateWriteLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.pkg.mutate.PackageStateMutator.Result generateResult = this.mPackageStateMutator.generateResult(initialState, this.mChangedPackagesTracker.getSequenceNumber());
                if (generateResult != com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SUCCESS) {
                    resetPriorityAfterPackageManagerTracedLockedSection();
                    return generateResult;
                }
                consumer.accept(this.mPackageStateMutator);
                this.mPackageStateMutator.onFinished();
                resetPriorityAfterPackageManagerTracedLockedSection();
                return com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SUCCESS;
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.mutate.PackageStateMutator.Result commitPackageStateMutation(@android.annotation.Nullable com.android.server.pm.pkg.mutate.PackageStateMutator.InitialState initialState, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.function.Consumer<com.android.server.pm.pkg.mutate.PackageStateWrite> consumer) {
        com.android.server.pm.pkg.mutate.PackageStateMutator.Result result;
        if (!java.lang.Thread.holdsLock(this.mPackageStateWriteLock)) {
            result = null;
        } else {
            result = com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SUCCESS;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPackageStateWriteLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            if (result == null) {
                try {
                    result = this.mPackageStateMutator.generateResult(initialState, this.mChangedPackagesTracker.getSequenceNumber());
                } catch (java.lang.Throwable th) {
                    resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            if (result != com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SUCCESS) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                return result;
            }
            com.android.server.pm.pkg.mutate.PackageStateWrite forPackage = this.mPackageStateMutator.forPackage(str);
            if (forPackage == null) {
                com.android.server.pm.pkg.mutate.PackageStateMutator.Result result2 = com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SPECIFIC_PACKAGE_NULL;
                resetPriorityAfterPackageManagerTracedLockedSection();
                return result2;
            }
            consumer.accept(forPackage);
            forPackage.onChanged();
            resetPriorityAfterPackageManagerTracedLockedSection();
            return com.android.server.pm.pkg.mutate.PackageStateMutator.Result.SUCCESS;
        }
    }

    void notifyInstantAppPackageInstalled(java.lang.String str, int[] iArr) {
        this.mInstantAppRegistry.onPackageInstalled(snapshotComputer(), str, iArr);
    }

    void addInstallerPackageName(com.android.server.pm.InstallSource installSource) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mSettings.addInstallerPackageNames(installSource);
            } catch (java.lang.Throwable th) {
                resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        resetPriorityAfterPackageManagerTracedLockedSection();
    }

    public void reconcileSdkData(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.List<java.lang.String> list, int i, int i2, int i3, @android.annotation.NonNull java.lang.String str3, int i4) throws java.io.IOException {
        synchronized (this.mInstallLock) {
            try {
                android.os.ReconcileSdkDataArgs buildReconcileSdkDataArgs = com.android.server.pm.Installer.buildReconcileSdkDataArgs(str, str2, list, i, i2, str3, i4);
                buildReconcileSdkDataArgs.previousAppId = i3;
                try {
                    this.mInstaller.reconcileSdkData(buildReconcileSdkDataArgs);
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    throw new java.io.IOException(e.getMessage());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeCodePath(@android.annotation.Nullable java.io.File file) {
        this.mRemovePackageHelper.removeCodePath(file);
    }

    void cleanUpResources(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.lang.String[] strArr) {
        this.mRemovePackageHelper.cleanUpResources(str, file, strArr);
    }

    void cleanUpForMoveInstall(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mRemovePackageHelper.cleanUpForMoveInstall(str, str2, str3);
    }

    void sendPendingBroadcasts() {
        this.mInstallPackageHelper.sendPendingBroadcasts();
    }

    void handlePackagePostInstall(@android.annotation.NonNull com.android.server.pm.InstallRequest installRequest, boolean z) {
        this.mInstallPackageHelper.handlePackagePostInstall(installRequest, z);
    }

    android.util.Pair<java.lang.Integer, android.content.IntentSender> installExistingPackageAsUser(@android.annotation.Nullable java.lang.String str, int i, int i2, int i3, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable android.content.IntentSender intentSender) {
        return this.mInstallPackageHelper.installExistingPackageAsUser(str, i, i2, i3, list, intentSender);
    }

    com.android.server.pm.pkg.AndroidPackage initPackageTracedLI(java.io.File file, int i, int i2) throws com.android.server.pm.PackageManagerException {
        return this.mInstallPackageHelper.initPackageTracedLI(file, i, i2);
    }

    void restoreDisabledSystemPackageLIF(@android.annotation.NonNull com.android.server.pm.DeletePackageAction deletePackageAction, @android.annotation.NonNull int[] iArr, boolean z) throws com.android.server.pm.SystemDeleteException {
        this.mInstallPackageHelper.restoreDisabledSystemPackageLIF(deletePackageAction, iArr, z);
    }

    boolean enableCompressedPackage(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting) {
        return this.mInstallPackageHelper.enableCompressedPackage(androidPackage, packageSetting);
    }

    void installPackagesTraced(java.util.List<com.android.server.pm.InstallRequest> list) {
        this.mInstallPackageHelper.installPackagesTraced(list);
    }

    void restoreAndPostInstall(com.android.server.pm.InstallRequest installRequest) {
        this.mInstallPackageHelper.restoreAndPostInstall(installRequest);
    }

    android.util.Pair<java.lang.Integer, java.lang.String> verifyReplacingVersionCode(@android.annotation.NonNull android.content.pm.PackageInfoLite packageInfoLite, long j, int i) {
        return this.mInstallPackageHelper.verifyReplacingVersionCode(packageInfoLite, j, i);
    }

    int getUidForVerifier(android.content.pm.VerifierInfo verifierInfo) {
        return this.mInstallPackageHelper.getUidForVerifier(verifierInfo);
    }

    int deletePackageX(java.lang.String str, long j, int i, int i2, boolean z) {
        return this.mDeletePackageHelper.deletePackageX(str, -1L, 0, 2, true);
    }
}
