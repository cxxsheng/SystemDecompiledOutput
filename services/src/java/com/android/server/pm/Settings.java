package com.android.server.pm;

/* loaded from: classes2.dex */
public final class Settings implements com.android.server.utils.Watchable, com.android.server.utils.Snappable, com.android.server.pm.ResilientAtomicFile.ReadEventLogger {
    private static final java.lang.String ATTR_APP_LINK_GENERATION = "app-link-generation";
    private static final java.lang.String ATTR_ARCHIVE_ACTIVITY_TITLE = "activity-title";
    private static final java.lang.String ATTR_ARCHIVE_ICON_PATH = "icon-path";
    private static final java.lang.String ATTR_ARCHIVE_INSTALLER_TITLE = "installer-title";
    private static final java.lang.String ATTR_ARCHIVE_MONOCHROME_ICON_PATH = "monochrome-icon-path";
    private static final java.lang.String ATTR_ARCHIVE_ORIGINAL_COMPONENT_NAME = "original-component-name";
    private static final java.lang.String ATTR_ARCHIVE_TIME = "archive-time";
    private static final java.lang.String ATTR_BLOCKED = "blocked";

    @java.lang.Deprecated
    private static final java.lang.String ATTR_BLOCK_UNINSTALL = "blockUninstall";
    private static final java.lang.String ATTR_BUILD_FINGERPRINT = "buildFingerprint";
    private static final java.lang.String ATTR_CE_DATA_INODE = "ceDataInode";
    private static final java.lang.String ATTR_DATABASE_VERSION = "databaseVersion";
    private static final java.lang.String ATTR_DE_DATA_INODE = "deDataInode";
    private static final java.lang.String ATTR_DISTRACTION_FLAGS = "distraction_flags";
    private static final java.lang.String ATTR_DOMAIN_VERIFICATION_STATE = "domainVerificationStatus";
    private static final java.lang.String ATTR_ENABLED = "enabled";
    private static final java.lang.String ATTR_ENABLED_CALLER = "enabledCaller";
    private static final java.lang.String ATTR_ENFORCEMENT = "enforcement";
    private static final java.lang.String ATTR_FINGERPRINT = "fingerprint";
    private static final java.lang.String ATTR_FIRST_INSTALL_TIME = "first-install-time";
    private static final java.lang.String ATTR_FLAGS = "flags";
    private static final java.lang.String ATTR_GRANTED = "granted";
    private static final java.lang.String ATTR_HARMFUL_APP_WARNING = "harmful-app-warning";
    private static final java.lang.String ATTR_HIDDEN = "hidden";
    private static final java.lang.String ATTR_INSTALLED = "inst";
    private static final java.lang.String ATTR_INSTALL_REASON = "install-reason";
    private static final java.lang.String ATTR_INSTANT_APP = "instant-app";
    private static final java.lang.String ATTR_MIN_ASPECT_RATIO = "min-aspect-ratio";
    public static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_NOT_LAUNCHED = "nl";
    private static final java.lang.String ATTR_OPTIONAL = "optional";
    public static final java.lang.String ATTR_PACKAGE = "package";
    private static final java.lang.String ATTR_PACKAGE_NAME = "packageName";
    private static final java.lang.String ATTR_SDK_VERSION = "sdkVersion";
    private static final java.lang.String ATTR_SPLASH_SCREEN_THEME = "splash-screen-theme";
    private static final java.lang.String ATTR_STOPPED = "stopped";
    private static final java.lang.String ATTR_SUSPENDED = "suspended";
    private static final java.lang.String ATTR_SUSPENDING_PACKAGE = "suspending-package";

    @java.lang.Deprecated
    private static final java.lang.String ATTR_SUSPEND_DIALOG_MESSAGE = "suspend_dialog_message";
    private static final java.lang.String ATTR_UNINSTALL_REASON = "uninstall-reason";
    private static final java.lang.String ATTR_VALUE = "value";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final java.lang.String ATTR_VIRTUAL_PRELOAD = "virtual-preload";
    private static final java.lang.String ATTR_VOLUME_UUID = "volumeUuid";
    public static final int CURRENT_DATABASE_VERSION = 3;
    private static final boolean DEBUG_KERNEL = false;
    private static final boolean DEBUG_MU = false;
    private static final boolean DEBUG_PARSER = false;
    static final boolean DEBUG_STOPPED = false;
    private static final int PRE_M_APP_INFO_FLAG_CANT_SAVE_STATE = 268435456;
    private static final int PRE_M_APP_INFO_FLAG_HIDDEN = 134217728;
    private static final int PRE_M_APP_INFO_FLAG_PRIVILEGED = 1073741824;
    private static final java.lang.String RUNTIME_PERMISSIONS_FILE_NAME = "runtime-permissions.xml";
    private static final java.lang.String TAG = "PackageSettings";
    public static final java.lang.String TAG_ALL_INTENT_FILTER_VERIFICATION = "all-intent-filter-verifications";
    private static final java.lang.String TAG_ARCHIVE_ACTIVITY_INFO = "archive-activity-info";
    private static final java.lang.String TAG_ARCHIVE_STATE = "archive-state";
    private static final java.lang.String TAG_BLOCK_UNINSTALL = "block-uninstall";
    private static final java.lang.String TAG_BLOCK_UNINSTALL_PACKAGES = "block-uninstall-packages";
    private static final java.lang.String TAG_CHILD_PACKAGE = "child-package";
    static final java.lang.String TAG_CROSS_PROFILE_INTENT_FILTERS = "crossProfile-intent-filters";
    private static final java.lang.String TAG_DEFAULT_APPS = "default-apps";
    private static final java.lang.String TAG_DEFAULT_BROWSER = "default-browser";
    private static final java.lang.String TAG_DEFAULT_DIALER = "default-dialer";
    private static final java.lang.String TAG_DISABLED_COMPONENTS = "disabled-components";
    public static final java.lang.String TAG_DOMAIN_VERIFICATION = "domain-verification";
    private static final java.lang.String TAG_ENABLED_COMPONENTS = "enabled-components";
    public static final java.lang.String TAG_ITEM = "item";
    private static final java.lang.String TAG_MIME_GROUP = "mime-group";
    private static final java.lang.String TAG_MIME_TYPE = "mime-type";
    private static final java.lang.String TAG_PACKAGE = "pkg";
    private static final java.lang.String TAG_PACKAGE_RESTRICTIONS = "package-restrictions";
    private static final java.lang.String TAG_PERMISSIONS = "perms";
    private static final java.lang.String TAG_PERSISTENT_PREFERRED_ACTIVITIES = "persistent-preferred-activities";
    private static final java.lang.String TAG_READ_EXTERNAL_STORAGE = "read-external-storage";
    private static final java.lang.String TAG_RUNTIME_PERMISSIONS = "runtime-permissions";
    private static final java.lang.String TAG_SHARED_USER = "shared-user";

    @java.lang.Deprecated
    private static final java.lang.String TAG_SUSPENDED_APP_EXTRAS = "suspended-app-extras";

    @java.lang.Deprecated
    private static final java.lang.String TAG_SUSPENDED_DIALOG_INFO = "suspended-dialog-info";

    @java.lang.Deprecated
    private static final java.lang.String TAG_SUSPENDED_LAUNCHER_EXTRAS = "suspended-launcher-extras";
    private static final java.lang.String TAG_SUSPEND_PARAMS = "suspend-params";
    private static final java.lang.String TAG_USES_SDK_LIB = "uses-sdk-lib";
    private static final java.lang.String TAG_USES_STATIC_LIB = "uses-static-lib";
    private static final java.lang.String TAG_VERSION = "version";

    @com.android.server.utils.Watched(manual = true)
    private final com.android.server.pm.AppIdSettingMap mAppIds;
    private final java.io.File mBackupStoppedPackagesFilename;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedSparseArray<android.util.ArraySet<java.lang.String>> mBlockUninstallPackages;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedSparseArray<com.android.server.pm.CrossProfileIntentResolver> mCrossProfileIntentResolvers;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseArray<com.android.server.pm.CrossProfileIntentResolver>> mCrossProfileIntentResolversSnapshot;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> mDisabledSysPackages;

    @com.android.server.utils.Watched(manual = true)
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;
    private final android.os.Handler mHandler;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArraySet<java.lang.String> mInstallerPackages;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArraySet<java.lang.String>> mInstallerPackagesSnapshot;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.Settings.KernelPackageState> mKernelMapping;
    private final java.io.File mKernelMappingFilename;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.Settings.KernelPackageState>> mKernelMappingSnapshot;
    private final com.android.server.pm.KeySetManagerService mKeySetManagerService;
    private final com.android.server.pm.PackageManagerTracedLock mLock;

    @android.annotation.NonNull
    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedSparseIntArray mNextAppLinkGeneration;
    private final com.android.server.utils.Watcher mObserver;
    private final java.io.File mPackageListFilename;
    private final java.lang.Object mPackageRestrictionsLock;

    @com.android.server.utils.Watched
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> mPackages;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting>> mPackagesSnapshot;

    @com.android.internal.annotations.GuardedBy({"mPackageRestrictionsLock"})
    private final android.util.SparseIntArray mPendingAsyncPackageRestrictionsWrites;

    @com.android.server.utils.Watched
    final com.android.server.utils.WatchedSparseArray<java.lang.String> mPendingDefaultBrowser;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArrayList<com.android.server.pm.PackageSetting> mPendingPackages;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedArrayList<com.android.server.pm.PackageSetting>> mPendingPackagesSnapshot;

    @com.android.server.utils.Watched(manual = true)
    private final com.android.server.pm.permission.LegacyPermissionDataProvider mPermissionDataProvider;

    @com.android.server.utils.Watched(manual = true)
    final com.android.server.pm.permission.LegacyPermissionSettings mPermissions;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedSparseArray<com.android.server.pm.PersistentPreferredIntentResolver> mPersistentPreferredActivities;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseArray<com.android.server.pm.PersistentPreferredIntentResolver>> mPersistentPreferredActivitiesSnapshot;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedSparseArray<com.android.server.pm.PreferredIntentResolver> mPreferredActivities;
    private final com.android.server.utils.SnapshotCache<com.android.server.utils.WatchedSparseArray<com.android.server.pm.PreferredIntentResolver>> mPreferredActivitiesSnapshot;
    private final java.io.File mPreviousSettingsFilename;
    final java.lang.StringBuilder mReadMessages;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, java.lang.String> mRenamedPackages;

    @com.android.server.utils.Watched(manual = true)
    private final com.android.server.pm.Settings.RuntimePermissionPersistence mRuntimePermissionsPersistence;
    private final java.io.File mSettingsFilename;
    private final java.io.File mSettingsReserveCopyFilename;

    @com.android.server.utils.Watched
    final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> mSharedUsers;
    private final com.android.server.utils.SnapshotCache<com.android.server.pm.Settings> mSnapshot;
    private final java.io.File mStoppedPackagesFilename;
    private final java.io.File mSystemDir;

    @com.android.server.utils.Watched(manual = true)
    private android.content.pm.VerifierDeviceIdentity mVerifierDeviceIdentity;

    @com.android.server.utils.Watched
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.Settings.VersionInfo> mVersion;
    private final com.android.server.utils.WatchableImpl mWatchable;
    static final java.lang.Object[] FLAG_DUMP_SPEC = {1, "SYSTEM", 2, "DEBUGGABLE", 4, "HAS_CODE", 8, "PERSISTENT", 16, "FACTORY_TEST", 32, "ALLOW_TASK_REPARENTING", 64, "ALLOW_CLEAR_USER_DATA", 128, "UPDATED_SYSTEM_APP", 256, "TEST_ONLY", 16384, "VM_SAFE_MODE", 32768, "ALLOW_BACKUP", 65536, "KILL_AFTER_RESTORE", 131072, "RESTORE_ANY_VERSION", 262144, "EXTERNAL_STORAGE", 1048576, "LARGE_HEAP"};
    private static final java.lang.Object[] PRIVATE_FLAG_DUMP_SPEC = {1024, "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE", 4096, "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION", 2048, "PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE", 134217728, "ALLOW_AUDIO_PLAYBACK_CAPTURE", 536870912, "PRIVATE_FLAG_REQUEST_LEGACY_EXTERNAL_STORAGE", 8192, "BACKUP_IN_FOREGROUND", 2, "CANT_SAVE_STATE", 32, "DEFAULT_TO_DEVICE_PROTECTED_STORAGE", 64, "DIRECT_BOOT_AWARE", 16, "HAS_DOMAIN_URLS", 1, "HIDDEN", 128, "EPHEMERAL", 32768, "ISOLATED_SPLIT_LOADING", 131072, "OEM", 256, "PARTIALLY_DIRECT_BOOT_AWARE", 8, "PRIVILEGED", 512, "REQUIRED_FOR_SYSTEM_USER", 16384, "STATIC_SHARED_LIBRARY", 262144, "VENDOR", 524288, "PRODUCT", 2097152, "SYSTEM_EXT", 65536, "VIRTUAL_PRELOAD", 1073741824, "ODM", Integer.MIN_VALUE, "PRIVATE_FLAG_ALLOW_NATIVE_HEAP_POINTER_TAGGING", 16777216, "PRIVATE_FLAG_HAS_FRAGILE_USER_DATA"};

    public static class DatabaseVersion {
        public static final int FIRST_VERSION = 1;
        public static final int SIGNATURE_END_ENTITY = 2;
        public static final int SIGNATURE_MALFORMED_RECOVER = 3;
    }

    private static final class KernelPackageState {
        int appId;
        int[] excludedUserIds;

        private KernelPackageState() {
        }
    }

    @Override // com.android.server.utils.Watchable
    public void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    protected void onChanged() {
        dispatchChange(this);
    }

    public static class VersionInfo {
        java.lang.String buildFingerprint;
        int databaseVersion;
        java.lang.String fingerprint;
        int sdkVersion;

        public void forceCurrent() {
            this.sdkVersion = android.os.Build.VERSION.SDK_INT;
            this.databaseVersion = 3;
            this.buildFingerprint = android.os.Build.VERSION.INCREMENTAL;
            this.fingerprint = android.content.pm.PackagePartitions.FINGERPRINT;
        }
    }

    private com.android.server.utils.SnapshotCache<com.android.server.pm.Settings> makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.Settings>(this, this) { // from class: com.android.server.pm.Settings.2
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.Settings createSnapshot() {
                com.android.server.pm.Settings settings = new com.android.server.pm.Settings();
                settings.mWatchable.seal();
                return settings;
            }
        };
    }

    private void registerObservers() {
        this.mPackages.registerObserver(this.mObserver);
        this.mInstallerPackages.registerObserver(this.mObserver);
        this.mKernelMapping.registerObserver(this.mObserver);
        this.mDisabledSysPackages.registerObserver(this.mObserver);
        this.mBlockUninstallPackages.registerObserver(this.mObserver);
        this.mVersion.registerObserver(this.mObserver);
        this.mPreferredActivities.registerObserver(this.mObserver);
        this.mPersistentPreferredActivities.registerObserver(this.mObserver);
        this.mCrossProfileIntentResolvers.registerObserver(this.mObserver);
        this.mSharedUsers.registerObserver(this.mObserver);
        this.mAppIds.registerObserver(this.mObserver);
        this.mRenamedPackages.registerObserver(this.mObserver);
        this.mNextAppLinkGeneration.registerObserver(this.mObserver);
        this.mPendingDefaultBrowser.registerObserver(this.mObserver);
        this.mPendingPackages.registerObserver(this.mObserver);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public Settings(java.util.Map<java.lang.String, com.android.server.pm.PackageSetting> map) {
        this.mWatchable = new com.android.server.utils.WatchableImpl();
        this.mPackageRestrictionsLock = new java.lang.Object();
        this.mPendingAsyncPackageRestrictionsWrites = new android.util.SparseIntArray();
        this.mDisabledSysPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mBlockUninstallPackages = new com.android.server.utils.WatchedSparseArray<>();
        this.mVersion = new com.android.server.utils.WatchedArrayMap<>();
        this.mSharedUsers = new com.android.server.utils.WatchedArrayMap<>();
        this.mRenamedPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mPendingDefaultBrowser = new com.android.server.utils.WatchedSparseArray<>();
        this.mNextAppLinkGeneration = new com.android.server.utils.WatchedSparseIntArray();
        this.mReadMessages = new java.lang.StringBuilder();
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.Settings.this.dispatchChange(watchable);
            }
        };
        this.mPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPackages, this.mPackages, "Settings.mPackages");
        this.mKernelMapping = new com.android.server.utils.WatchedArrayMap<>();
        this.mKernelMappingSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mKernelMapping, this.mKernelMapping, "Settings.mKernelMapping");
        this.mInstallerPackages = new com.android.server.utils.WatchedArraySet<>();
        this.mInstallerPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mInstallerPackages, this.mInstallerPackages, "Settings.mInstallerPackages");
        this.mPreferredActivities = new com.android.server.utils.WatchedSparseArray<>();
        this.mPreferredActivitiesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPreferredActivities, this.mPreferredActivities, "Settings.mPreferredActivities");
        this.mPersistentPreferredActivities = new com.android.server.utils.WatchedSparseArray<>();
        this.mPersistentPreferredActivitiesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPersistentPreferredActivities, this.mPersistentPreferredActivities, "Settings.mPersistentPreferredActivities");
        this.mCrossProfileIntentResolvers = new com.android.server.utils.WatchedSparseArray<>();
        this.mCrossProfileIntentResolversSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mCrossProfileIntentResolvers, this.mCrossProfileIntentResolvers, "Settings.mCrossProfileIntentResolvers");
        this.mPendingPackages = new com.android.server.utils.WatchedArrayList<>();
        this.mPendingPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPendingPackages, this.mPendingPackages, "Settings.mPendingPackages");
        this.mKeySetManagerService = new com.android.server.pm.KeySetManagerService(this.mPackages);
        this.mHandler = new android.os.Handler(com.android.internal.os.BackgroundThread.getHandler().getLooper());
        this.mLock = new com.android.server.pm.PackageManagerTracedLock();
        this.mPackages.putAll(map);
        this.mAppIds = new com.android.server.pm.AppIdSettingMap();
        this.mSystemDir = null;
        this.mPermissions = null;
        this.mRuntimePermissionsPersistence = null;
        this.mPermissionDataProvider = null;
        this.mSettingsFilename = null;
        this.mSettingsReserveCopyFilename = null;
        this.mPreviousSettingsFilename = null;
        this.mPackageListFilename = null;
        this.mStoppedPackagesFilename = null;
        this.mBackupStoppedPackagesFilename = null;
        this.mKernelMappingFilename = null;
        this.mDomainVerificationManager = null;
        registerObservers();
        com.android.server.utils.Watchable.verifyWatchedAttributes(this, this.mObserver);
        this.mSnapshot = makeCache();
    }

    Settings(java.io.File file, com.android.permission.persistence.RuntimePermissionsPersistence runtimePermissionsPersistence, com.android.server.pm.permission.LegacyPermissionDataProvider legacyPermissionDataProvider, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationManagerInternal domainVerificationManagerInternal, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock) {
        this.mWatchable = new com.android.server.utils.WatchableImpl();
        this.mPackageRestrictionsLock = new java.lang.Object();
        this.mPendingAsyncPackageRestrictionsWrites = new android.util.SparseIntArray();
        this.mDisabledSysPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mBlockUninstallPackages = new com.android.server.utils.WatchedSparseArray<>();
        this.mVersion = new com.android.server.utils.WatchedArrayMap<>();
        this.mSharedUsers = new com.android.server.utils.WatchedArrayMap<>();
        this.mRenamedPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mPendingDefaultBrowser = new com.android.server.utils.WatchedSparseArray<>();
        this.mNextAppLinkGeneration = new com.android.server.utils.WatchedSparseIntArray();
        this.mReadMessages = new java.lang.StringBuilder();
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.Settings.this.dispatchChange(watchable);
            }
        };
        this.mPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPackages, this.mPackages, "Settings.mPackages");
        this.mKernelMapping = new com.android.server.utils.WatchedArrayMap<>();
        this.mKernelMappingSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mKernelMapping, this.mKernelMapping, "Settings.mKernelMapping");
        this.mInstallerPackages = new com.android.server.utils.WatchedArraySet<>();
        this.mInstallerPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mInstallerPackages, this.mInstallerPackages, "Settings.mInstallerPackages");
        this.mPreferredActivities = new com.android.server.utils.WatchedSparseArray<>();
        this.mPreferredActivitiesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPreferredActivities, this.mPreferredActivities, "Settings.mPreferredActivities");
        this.mPersistentPreferredActivities = new com.android.server.utils.WatchedSparseArray<>();
        this.mPersistentPreferredActivitiesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPersistentPreferredActivities, this.mPersistentPreferredActivities, "Settings.mPersistentPreferredActivities");
        this.mCrossProfileIntentResolvers = new com.android.server.utils.WatchedSparseArray<>();
        this.mCrossProfileIntentResolversSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mCrossProfileIntentResolvers, this.mCrossProfileIntentResolvers, "Settings.mCrossProfileIntentResolvers");
        this.mPendingPackages = new com.android.server.utils.WatchedArrayList<>();
        this.mPendingPackagesSnapshot = new com.android.server.utils.SnapshotCache.Auto(this.mPendingPackages, this.mPendingPackages, "Settings.mPendingPackages");
        this.mKeySetManagerService = new com.android.server.pm.KeySetManagerService(this.mPackages);
        this.mHandler = handler;
        this.mLock = packageManagerTracedLock;
        this.mAppIds = new com.android.server.pm.AppIdSettingMap();
        this.mPermissions = new com.android.server.pm.permission.LegacyPermissionSettings();
        this.mRuntimePermissionsPersistence = new com.android.server.pm.Settings.RuntimePermissionPersistence(runtimePermissionsPersistence, new java.util.function.Consumer<java.lang.Integer>() { // from class: com.android.server.pm.Settings.3
            @Override // java.util.function.Consumer
            public void accept(java.lang.Integer num) {
                com.android.server.pm.Settings.this.mRuntimePermissionsPersistence.writeStateForUser(num.intValue(), com.android.server.pm.Settings.this.mPermissionDataProvider, com.android.server.pm.Settings.this.mPackages, com.android.server.pm.Settings.this.mSharedUsers, com.android.server.pm.Settings.this.mHandler, com.android.server.pm.Settings.this.mLock, false);
            }
        });
        this.mPermissionDataProvider = legacyPermissionDataProvider;
        this.mSystemDir = new java.io.File(file, "system");
        this.mSystemDir.mkdirs();
        android.os.FileUtils.setPermissions(this.mSystemDir.toString(), 509, -1, -1);
        this.mSettingsFilename = new java.io.File(this.mSystemDir, "packages.xml");
        this.mSettingsReserveCopyFilename = new java.io.File(this.mSystemDir, "packages.xml.reservecopy");
        this.mPreviousSettingsFilename = new java.io.File(this.mSystemDir, "packages-backup.xml");
        this.mPackageListFilename = new java.io.File(this.mSystemDir, "packages.list");
        android.os.FileUtils.setPermissions(this.mPackageListFilename, com.android.internal.util.FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1032);
        java.io.File file2 = new java.io.File("/config/sdcardfs");
        this.mKernelMappingFilename = file2.exists() ? file2 : null;
        this.mStoppedPackagesFilename = new java.io.File(this.mSystemDir, "packages-stopped.xml");
        this.mBackupStoppedPackagesFilename = new java.io.File(this.mSystemDir, "packages-stopped-backup.xml");
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        registerObservers();
        com.android.server.utils.Watchable.verifyWatchedAttributes(this, this.mObserver);
        this.mSnapshot = makeCache();
    }

    private Settings(com.android.server.pm.Settings settings) {
        this.mWatchable = new com.android.server.utils.WatchableImpl();
        this.mPackageRestrictionsLock = new java.lang.Object();
        this.mPendingAsyncPackageRestrictionsWrites = new android.util.SparseIntArray();
        this.mDisabledSysPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mBlockUninstallPackages = new com.android.server.utils.WatchedSparseArray<>();
        this.mVersion = new com.android.server.utils.WatchedArrayMap<>();
        this.mSharedUsers = new com.android.server.utils.WatchedArrayMap<>();
        this.mRenamedPackages = new com.android.server.utils.WatchedArrayMap<>();
        this.mPendingDefaultBrowser = new com.android.server.utils.WatchedSparseArray<>();
        this.mNextAppLinkGeneration = new com.android.server.utils.WatchedSparseIntArray();
        this.mReadMessages = new java.lang.StringBuilder();
        this.mObserver = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.Settings.1
            @Override // com.android.server.utils.Watcher
            public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
                com.android.server.pm.Settings.this.dispatchChange(watchable);
            }
        };
        this.mPackages = settings.mPackagesSnapshot.snapshot();
        this.mPackagesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mKernelMapping = settings.mKernelMappingSnapshot.snapshot();
        this.mKernelMappingSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mInstallerPackages = settings.mInstallerPackagesSnapshot.snapshot();
        this.mInstallerPackagesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mKeySetManagerService = new com.android.server.pm.KeySetManagerService(settings.mKeySetManagerService, this.mPackages);
        this.mHandler = null;
        this.mLock = null;
        this.mRuntimePermissionsPersistence = settings.mRuntimePermissionsPersistence;
        this.mSettingsFilename = null;
        this.mSettingsReserveCopyFilename = null;
        this.mPreviousSettingsFilename = null;
        this.mPackageListFilename = null;
        this.mStoppedPackagesFilename = null;
        this.mBackupStoppedPackagesFilename = null;
        this.mKernelMappingFilename = null;
        this.mDomainVerificationManager = settings.mDomainVerificationManager;
        this.mDisabledSysPackages.snapshot(settings.mDisabledSysPackages);
        this.mBlockUninstallPackages.snapshot(settings.mBlockUninstallPackages);
        this.mVersion.putAll(settings.mVersion);
        this.mVerifierDeviceIdentity = settings.mVerifierDeviceIdentity;
        this.mPreferredActivities = settings.mPreferredActivitiesSnapshot.snapshot();
        this.mPreferredActivitiesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mPersistentPreferredActivities = settings.mPersistentPreferredActivitiesSnapshot.snapshot();
        this.mPersistentPreferredActivitiesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mCrossProfileIntentResolvers = settings.mCrossProfileIntentResolversSnapshot.snapshot();
        this.mCrossProfileIntentResolversSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mSharedUsers.snapshot(settings.mSharedUsers);
        this.mAppIds = settings.mAppIds.snapshot();
        this.mRenamedPackages.snapshot(settings.mRenamedPackages);
        this.mNextAppLinkGeneration.snapshot(settings.mNextAppLinkGeneration);
        this.mPendingDefaultBrowser.snapshot(settings.mPendingDefaultBrowser);
        this.mPendingPackages = settings.mPendingPackagesSnapshot.snapshot();
        this.mPendingPackagesSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mSystemDir = null;
        this.mPermissions = settings.mPermissions;
        this.mPermissionDataProvider = settings.mPermissionDataProvider;
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.Settings snapshot() {
        return this.mSnapshot.snapshot();
    }

    private void invalidatePackageCache() {
        com.android.server.pm.PackageManagerService.invalidatePackageInfoCache();
        android.app.compat.ChangeIdStateCache.invalidate();
        onChanged();
    }

    com.android.server.pm.PackageSetting getPackageLPr(java.lang.String str) {
        return this.mPackages.get(str);
    }

    com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> getPackagesLocked() {
        return this.mPackages;
    }

    com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> getDisabledSystemPackagesLocked() {
        return this.mDisabledSysPackages;
    }

    com.android.server.pm.KeySetManagerService getKeySetManagerService() {
        return this.mKeySetManagerService;
    }

    java.lang.String getRenamedPackageLPr(java.lang.String str) {
        return this.mRenamedPackages.get(str);
    }

    java.lang.String addRenamedPackageLPw(java.lang.String str, java.lang.String str2) {
        return this.mRenamedPackages.put(str, str2);
    }

    void removeRenamedPackageLPw(java.lang.String str) {
        this.mRenamedPackages.remove(str);
    }

    void pruneRenamedPackagesLPw() {
        for (int size = this.mRenamedPackages.size() - 1; size >= 0; size--) {
            if (this.mPackages.get(this.mRenamedPackages.valueAt(size)) == null) {
                this.mRenamedPackages.removeAt(size);
            }
        }
    }

    com.android.server.pm.SharedUserSetting getSharedUserLPw(java.lang.String str, int i, int i2, boolean z) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.SharedUserSetting sharedUserSetting = this.mSharedUsers.get(str);
        if (sharedUserSetting == null && z) {
            sharedUserSetting = new com.android.server.pm.SharedUserSetting(str, i, i2);
            sharedUserSetting.mAppId = this.mAppIds.acquireAndRegisterNewAppId(sharedUserSetting);
            if (sharedUserSetting.mAppId < 0) {
                throw new com.android.server.pm.PackageManagerException(-4, "Creating shared user " + str + " failed");
            }
            android.util.Log.i("PackageManager", "New shared user " + str + ": id=" + sharedUserSetting.mAppId);
            this.mSharedUsers.put(str, sharedUserSetting);
        }
        return sharedUserSetting;
    }

    com.android.server.utils.WatchedArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.SharedUserApi> getSharedUsersLocked() {
        return this.mSharedUsers;
    }

    java.util.Collection<com.android.server.pm.SharedUserSetting> getAllSharedUsersLPw() {
        return this.mSharedUsers.values();
    }

    boolean disableSystemPackageLPw(java.lang.String str, boolean z) {
        com.android.server.pm.PackageSetting packageSetting;
        com.android.server.pm.PackageSetting packageSetting2 = this.mPackages.get(str);
        if (packageSetting2 == null) {
            android.util.Log.w("PackageManager", "Package " + str + " is not an installed package");
            return false;
        }
        if (this.mDisabledSysPackages.get(str) != null || packageSetting2.getPkg() == null || !packageSetting2.isSystem() || packageSetting2.isUpdatedSystemApp()) {
            return false;
        }
        if (z) {
            packageSetting = new com.android.server.pm.PackageSetting(packageSetting2);
        } else {
            packageSetting = packageSetting2;
        }
        packageSetting2.getPkgState().setUpdatedSystemApp(true);
        this.mDisabledSysPackages.put(str, packageSetting);
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            sharedUserSettingLPr.mDisabledPackages.add(packageSetting);
        }
        return true;
    }

    com.android.server.pm.PackageSetting enableSystemPackageLPw(java.lang.String str) {
        com.android.server.pm.PackageSetting packageSetting = this.mDisabledSysPackages.get(str);
        if (packageSetting == null) {
            android.util.Log.w("PackageManager", "Package " + str + " is not disabled");
            return null;
        }
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            sharedUserSettingLPr.mDisabledPackages.remove(packageSetting);
        }
        packageSetting.getPkgState().setUpdatedSystemApp(false);
        com.android.server.pm.PackageSetting addPackageLPw = addPackageLPw(str, packageSetting.getRealName(), packageSetting.getPath(), packageSetting.getAppId(), packageSetting.getFlags(), packageSetting.getPrivateFlags(), this.mDomainVerificationManager.generateNewId());
        if (addPackageLPw != null) {
            addPackageLPw.setLegacyNativeLibraryPath(packageSetting.getLegacyNativeLibraryPath());
            addPackageLPw.setPrimaryCpuAbi(packageSetting.getPrimaryCpuAbiLegacy());
            addPackageLPw.setSecondaryCpuAbi(packageSetting.getSecondaryCpuAbiLegacy());
            addPackageLPw.setCpuAbiOverride(packageSetting.getCpuAbiOverride());
            addPackageLPw.setLongVersionCode(packageSetting.getVersionCode());
            addPackageLPw.setUsesSdkLibraries(packageSetting.getUsesSdkLibraries());
            addPackageLPw.setUsesSdkLibrariesVersionsMajor(packageSetting.getUsesSdkLibrariesVersionsMajor());
            addPackageLPw.setUsesSdkLibrariesOptional(packageSetting.getUsesSdkLibrariesOptional());
            addPackageLPw.setUsesStaticLibraries(packageSetting.getUsesStaticLibraries());
            addPackageLPw.setUsesStaticLibrariesVersions(packageSetting.getUsesStaticLibrariesVersions());
            addPackageLPw.setMimeGroups(packageSetting.getMimeGroups());
            addPackageLPw.setAppMetadataFilePath(packageSetting.getAppMetadataFilePath());
            addPackageLPw.setAppMetadataSource(packageSetting.getAppMetadataSource());
            addPackageLPw.getPkgState().setUpdatedSystemApp(false);
            addPackageLPw.setTargetSdkVersion(packageSetting.getTargetSdkVersion());
            addPackageLPw.setRestrictUpdateHash(packageSetting.getRestrictUpdateHash());
            addPackageLPw.setScannedAsStoppedSystemApp(packageSetting.isScannedAsStoppedSystemApp());
        }
        this.mDisabledSysPackages.remove(str);
        return addPackageLPw;
    }

    boolean isDisabledSystemPackageLPr(java.lang.String str) {
        return this.mDisabledSysPackages.containsKey(str);
    }

    void removeDisabledSystemPackageLPw(java.lang.String str) {
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr;
        com.android.server.pm.PackageSetting remove = this.mDisabledSysPackages.remove(str);
        if (remove != null && (sharedUserSettingLPr = getSharedUserSettingLPr(remove)) != null) {
            sharedUserSettingLPr.mDisabledPackages.remove(remove);
            checkAndPruneSharedUserLPw(sharedUserSettingLPr, false);
        }
    }

    com.android.server.pm.PackageSetting addPackageLPw(java.lang.String str, java.lang.String str2, java.io.File file, int i, int i2, int i3, @android.annotation.NonNull java.util.UUID uuid) {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(str);
        if (packageSetting != null) {
            if (packageSetting.getAppId() == i) {
                return packageSetting;
            }
            com.android.server.pm.PackageManagerService.reportSettingsProblem(6, "Adding duplicate package, keeping first: " + str);
            return null;
        }
        com.android.server.pm.PackageSetting appId = new com.android.server.pm.PackageSetting(str, str2, file, i2, i3, uuid).setAppId(i);
        if (!this.mAppIds.registerExistingAppId(i, appId, str)) {
            return null;
        }
        this.mPackages.put(str, appId);
        return appId;
    }

    com.android.server.pm.SharedUserSetting addSharedUserLPw(java.lang.String str, int i, int i2, int i3) {
        com.android.server.pm.SharedUserSetting sharedUserSetting = this.mSharedUsers.get(str);
        if (sharedUserSetting != null) {
            if (sharedUserSetting.mAppId == i) {
                return sharedUserSetting;
            }
            com.android.server.pm.PackageManagerService.reportSettingsProblem(6, "Adding duplicate shared user, keeping first: " + str);
            return null;
        }
        com.android.server.pm.SharedUserSetting sharedUserSetting2 = new com.android.server.pm.SharedUserSetting(str, i2, i3);
        sharedUserSetting2.mAppId = i;
        if (!this.mAppIds.registerExistingAppId(i, sharedUserSetting2, str)) {
            return null;
        }
        this.mSharedUsers.put(str, sharedUserSetting2);
        return sharedUserSetting2;
    }

    void pruneSharedUsersLPw() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.SharedUserSetting> entry : this.mSharedUsers.entrySet()) {
            com.android.server.pm.SharedUserSetting value = entry.getValue();
            if (value == null) {
                arrayList.add(entry.getKey());
            } else {
                com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting> packageSettings = value.getPackageSettings();
                boolean z = false;
                for (int size = packageSettings.size() - 1; size >= 0; size--) {
                    if (this.mPackages.get(packageSettings.valueAt(size).getPackageName()) == null) {
                        packageSettings.removeAt(size);
                        z = true;
                    }
                }
                com.android.server.utils.WatchedArraySet<com.android.server.pm.PackageSetting> disabledPackageSettings = value.getDisabledPackageSettings();
                for (int size2 = disabledPackageSettings.size() - 1; size2 >= 0; size2--) {
                    if (this.mDisabledSysPackages.get(disabledPackageSettings.valueAt(size2).getPackageName()) == null) {
                        disabledPackageSettings.removeAt(size2);
                        z = true;
                    }
                }
                if (z) {
                    value.onChanged();
                }
                if (packageSettings.isEmpty() && disabledPackageSettings.isEmpty()) {
                    arrayList2.add(value);
                }
            }
        }
        final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> watchedArrayMap = this.mSharedUsers;
        java.util.Objects.requireNonNull(watchedArrayMap);
        arrayList.forEach(new java.util.function.Consumer() { // from class: com.android.server.pm.Settings$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.utils.WatchedArrayMap.this.remove((java.lang.String) obj);
            }
        });
        arrayList2.forEach(new java.util.function.Consumer() { // from class: com.android.server.pm.Settings$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.Settings.this.lambda$pruneSharedUsersLPw$0((com.android.server.pm.SharedUserSetting) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pruneSharedUsersLPw$0(com.android.server.pm.SharedUserSetting sharedUserSetting) {
        checkAndPruneSharedUserLPw(sharedUserSetting, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0120, code lost:
    
        if (r4.preCreated == false) goto L35;
     */
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static com.android.server.pm.PackageSetting createNewSetting(java.lang.String str, com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.PackageSetting packageSetting2, java.lang.String str2, com.android.server.pm.SharedUserSetting sharedUserSetting, java.io.File file, java.lang.String str3, java.lang.String str4, java.lang.String str5, long j, int i, int i2, android.os.UserHandle userHandle, boolean z, boolean z2, boolean z3, boolean z4, com.android.server.pm.UserManagerService userManagerService, java.lang.String[] strArr, long[] jArr, boolean[] zArr, java.lang.String[] strArr2, long[] jArr2, java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.UUID uuid, int i3, byte[] bArr) {
        int i4;
        boolean z5;
        if (packageSetting != null) {
            com.android.server.pm.PackageSetting restrictUpdateHash = new com.android.server.pm.PackageSetting(packageSetting, str).setPath(file).setLegacyNativeLibraryPath(str3).setPrimaryCpuAbi(str4).setSecondaryCpuAbi(str5).setSignatures(new com.android.server.pm.PackageSignatures()).setLongVersionCode(j).setUsesSdkLibraries(strArr).setUsesSdkLibrariesVersionsMajor(jArr).setUsesSdkLibrariesOptional(zArr).setUsesStaticLibraries(strArr2).setUsesStaticLibrariesVersions(jArr2).setLastModifiedTime(file.lastModified()).setDomainSetId(uuid).setTargetSdkVersion(i3).setRestrictUpdateHash(bArr);
            restrictUpdateHash.setFlags(i).setPrivateFlags(i2);
            return restrictUpdateHash;
        }
        int identifier = userHandle != null ? userHandle.getIdentifier() : 0;
        com.android.server.pm.PackageSetting lastModifiedTime = new com.android.server.pm.PackageSetting(str, str2, file, i, i2, uuid).setUsesSdkLibraries(strArr).setUsesSdkLibrariesVersionsMajor(jArr).setUsesSdkLibrariesOptional(zArr).setUsesStaticLibraries(strArr2).setUsesStaticLibrariesVersions(jArr2).setLegacyNativeLibraryPath(str3).setPrimaryCpuAbi(str4).setSecondaryCpuAbi(str5).setLongVersionCode(j).setMimeGroups(createMimeGroups(set)).setTargetSdkVersion(i3).setRestrictUpdateHash(bArr).setLastModifiedTime(file.lastModified());
        if (sharedUserSetting != null) {
            lastModifiedTime.setSharedUserAppId(sharedUserSetting.mAppId);
        }
        if ((i & 1) == 0) {
            java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers(userManagerService);
            if (allUsers != null && z) {
                java.util.Iterator<android.content.pm.UserInfo> it = allUsers.iterator();
                while (it.hasNext()) {
                    android.content.pm.UserInfo next = it.next();
                    if (userHandle != null) {
                        i4 = identifier;
                        if (i4 == -1) {
                            if (!isAdbInstallDisallowed(userManagerService, next.id)) {
                            }
                        }
                        if (i4 != next.id) {
                            z5 = false;
                            lastModifiedTime.setUserState(next.id, 0L, 0L, 0, z5, true, true, false, 0, null, z2, z3, null, null, null, 0, 0, null, null, 0L, 0, null);
                            identifier = i4;
                        }
                    } else {
                        i4 = identifier;
                    }
                    z5 = true;
                    lastModifiedTime.setUserState(next.id, 0L, 0L, 0, z5, true, true, false, 0, null, z2, z3, null, null, null, 0, 0, null, null, 0L, 0, null);
                    identifier = i4;
                }
            }
        } else if (z4) {
            lastModifiedTime.setStopped(true, identifier);
            lastModifiedTime.setScannedAsStoppedSystemApp(true);
        }
        if (sharedUserSetting != null) {
            lastModifiedTime.setAppId(sharedUserSetting.mAppId);
            return lastModifiedTime;
        }
        if (packageSetting2 != null) {
            lastModifiedTime.setSignatures(new com.android.server.pm.PackageSignatures(packageSetting2.getSignatures()));
            lastModifiedTime.setAppId(packageSetting2.getAppId());
            lastModifiedTime.getLegacyPermissionState().copyFrom(packageSetting2.getLegacyPermissionState());
            java.util.List<android.content.pm.UserInfo> allUsers2 = getAllUsers(userManagerService);
            if (allUsers2 != null) {
                java.util.Iterator<android.content.pm.UserInfo> it2 = allUsers2.iterator();
                while (it2.hasNext()) {
                    int i5 = it2.next().id;
                    lastModifiedTime.setDisabledComponentsCopy(packageSetting2.getDisabledComponents(i5), i5);
                    lastModifiedTime.setEnabledComponentsCopy(packageSetting2.getEnabledComponents(i5), i5);
                }
                return lastModifiedTime;
            }
            return lastModifiedTime;
        }
        return lastModifiedTime;
    }

    private static java.util.Map<java.lang.String, java.util.Set<java.lang.String>> createMimeGroups(java.util.Set<java.lang.String> set) {
        if (set == null) {
            return null;
        }
        return new com.android.server.pm.Settings.KeySetToValueMap(set, new android.util.ArraySet());
    }

    static void updatePackageSetting(@android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, @android.annotation.Nullable com.android.server.pm.SharedUserSetting sharedUserSetting, @android.annotation.Nullable com.android.server.pm.SharedUserSetting sharedUserSetting2, @android.annotation.NonNull java.io.File file, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, int i2, @android.annotation.NonNull com.android.server.pm.UserManagerService userManagerService, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable long[] jArr, @android.annotation.Nullable boolean[] zArr, @android.annotation.Nullable java.lang.String[] strArr2, @android.annotation.Nullable long[] jArr2, @android.annotation.Nullable java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.UUID uuid, int i3, byte[] bArr, boolean z) throws com.android.server.pm.PackageManagerException {
        int i4;
        java.util.List<android.content.pm.UserInfo> allUsers;
        java.lang.String packageName = packageSetting.getPackageName();
        java.io.File path = packageSetting.getPath();
        if (sharedUserSetting2 == null) {
            packageSetting.setSharedUserAppId(-1);
        } else if (java.util.Objects.equals(sharedUserSetting, sharedUserSetting2)) {
            packageSetting.setSharedUserAppId(sharedUserSetting2.mAppId);
        } else {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Package ");
            sb.append(packageName);
            sb.append(" shared user changed from ");
            sb.append(sharedUserSetting != null ? sharedUserSetting.name : "<nothing>");
            sb.append(" to ");
            sb.append(sharedUserSetting2.name);
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb.toString());
            throw new com.android.server.pm.PackageManagerException(-24, "Updating application package " + packageName + " failed");
        }
        if (!path.equals(file)) {
            boolean isSystem = packageSetting.isSystem();
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append("Update");
            sb2.append(isSystem ? " system" : "");
            sb2.append(" package ");
            sb2.append(packageName);
            sb2.append(" code path from ");
            sb2.append(packageSetting.getPathString());
            sb2.append(" to ");
            sb2.append(file.toString());
            sb2.append("; Retain data and using new");
            android.util.Slog.i("PackageManager", sb2.toString());
            if (!isSystem) {
                if ((i & 1) != 0 && packageSetting2 == null && (allUsers = getAllUsers(userManagerService)) != null) {
                    for (android.content.pm.UserInfo userInfo : allUsers) {
                        packageSetting.setInstalled(true, userInfo.id);
                        packageSetting.setUninstallReason(0, userInfo.id);
                    }
                }
                packageSetting.setLegacyNativeLibraryPath(str);
            }
            packageSetting.setPath(file);
            if (z && android.content.pm.Flags.improveInstallDontKill()) {
                packageSetting.addOldPath(path);
            }
        }
        packageSetting.setPrimaryCpuAbi(str2).setSecondaryCpuAbi(str3).updateMimeGroups(set).setDomainSetId(uuid).setTargetSdkVersion(i3).setRestrictUpdateHash(bArr);
        if (strArr != null && jArr != null && zArr != null && strArr.length == jArr.length && strArr.length == zArr.length) {
            packageSetting.setUsesSdkLibraries(strArr).setUsesSdkLibrariesVersionsMajor(jArr).setUsesSdkLibrariesOptional(zArr);
        } else {
            packageSetting.setUsesSdkLibraries(null).setUsesSdkLibrariesVersionsMajor(null).setUsesSdkLibrariesOptional(null);
        }
        if (strArr2 != null && jArr2 != null && strArr2.length == jArr2.length) {
            packageSetting.setUsesStaticLibraries(strArr2).setUsesStaticLibrariesVersions(jArr2);
        } else {
            packageSetting.setUsesStaticLibraries(null).setUsesStaticLibrariesVersions(null);
        }
        packageSetting.setFlags((packageSetting.getFlags() & (-2)) | (i & 1));
        if ((packageSetting.getPrivateFlags() & 512) != 0) {
            i4 = i2 | 512;
        } else {
            i4 = i2 & (-513);
        }
        packageSetting.setPrivateFlags(i4);
    }

    boolean registerAppIdLPw(com.android.server.pm.PackageSetting packageSetting, boolean z) throws com.android.server.pm.PackageManagerException {
        boolean z2;
        if (packageSetting.getAppId() == 0 || z) {
            packageSetting.setAppId(this.mAppIds.acquireAndRegisterNewAppId(packageSetting));
            z2 = true;
        } else {
            z2 = this.mAppIds.registerExistingAppId(packageSetting.getAppId(), packageSetting, packageSetting.getPackageName());
        }
        if (packageSetting.getAppId() < 0) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Package " + packageSetting.getPackageName() + " could not be assigned a valid UID");
            throw new com.android.server.pm.PackageManagerException(-4, "Package " + packageSetting.getPackageName() + " could not be assigned a valid UID");
        }
        return z2;
    }

    void writeUserRestrictionsLPw(com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.PackageSetting packageSetting2) {
        java.util.List<android.content.pm.UserInfo> allUsers;
        java.lang.Object readUserState;
        if (getPackageLPr(packageSetting.getPackageName()) == null || (allUsers = getAllUsers(com.android.server.pm.UserManagerService.getInstance())) == null) {
            return;
        }
        for (android.content.pm.UserInfo userInfo : allUsers) {
            if (packageSetting2 == null) {
                readUserState = com.android.server.pm.pkg.PackageUserState.DEFAULT;
            } else {
                readUserState = packageSetting2.readUserState(userInfo.id);
            }
            if (!readUserState.equals(packageSetting.readUserState(userInfo.id))) {
                writePackageRestrictionsLPr(userInfo.id);
            }
        }
    }

    static boolean isAdbInstallDisallowed(com.android.server.pm.UserManagerService userManagerService, int i) {
        return userManagerService.hasUserRestriction("no_debugging_features", i);
    }

    void insertPackageSettingLPw(com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (packageSetting.getSigningDetails().getSignatures() == null) {
            packageSetting.setSigningDetails(androidPackage.getSigningDetails());
        }
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null && sharedUserSettingLPr.signatures.mSigningDetails.getSignatures() == null) {
            sharedUserSettingLPr.signatures.mSigningDetails = androidPackage.getSigningDetails();
        }
        addPackageSettingLPw(packageSetting, sharedUserSettingLPr);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void addPackageSettingLPw(com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.SharedUserSetting sharedUserSetting) {
        this.mPackages.put(packageSetting.getPackageName(), packageSetting);
        if (sharedUserSetting != null) {
            com.android.server.pm.SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
            if (sharedUserSettingLPr != null && sharedUserSettingLPr != sharedUserSetting) {
                com.android.server.pm.PackageManagerService.reportSettingsProblem(6, "Package " + packageSetting.getPackageName() + " was user " + sharedUserSettingLPr + " but is now " + sharedUserSetting + "; I am not changing its files so it will probably fail!");
                sharedUserSettingLPr.removePackage(packageSetting);
            } else if (packageSetting.getAppId() != 0 && packageSetting.getAppId() != sharedUserSetting.mAppId) {
                com.android.server.pm.PackageManagerService.reportSettingsProblem(6, "Package " + packageSetting.getPackageName() + " was app id " + packageSetting.getAppId() + " but is now user " + sharedUserSetting + " with app id " + sharedUserSetting.mAppId + "; I am not changing its files so it will probably fail!");
            }
            sharedUserSetting.addPackage(packageSetting);
            packageSetting.setSharedUserAppId(sharedUserSetting.mAppId);
            packageSetting.setAppId(sharedUserSetting.mAppId);
        }
        com.android.server.pm.SettingBase settingLPr = getSettingLPr(packageSetting.getAppId());
        if (sharedUserSetting == null) {
            if (settingLPr != null && settingLPr != packageSetting) {
                this.mAppIds.replaceSetting(packageSetting.getAppId(), packageSetting);
                return;
            }
            return;
        }
        if (settingLPr != null && settingLPr != sharedUserSetting) {
            this.mAppIds.replaceSetting(packageSetting.getAppId(), sharedUserSetting);
        }
    }

    boolean checkAndPruneSharedUserLPw(com.android.server.pm.SharedUserSetting sharedUserSetting, boolean z) {
        if ((z || (sharedUserSetting.getPackageStates().isEmpty() && sharedUserSetting.getDisabledPackageStates().isEmpty())) && this.mSharedUsers.remove(sharedUserSetting.name) != null) {
            removeAppIdLPw(sharedUserSetting.mAppId);
            return true;
        }
        return false;
    }

    boolean removePackageAndAppIdLPw(java.lang.String str) {
        com.android.server.pm.PackageSetting remove = this.mPackages.remove(str);
        if (remove == null) {
            return false;
        }
        removeInstallerPackageStatus(str);
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(remove);
        if (sharedUserSettingLPr != null) {
            sharedUserSettingLPr.removePackage(remove);
            return checkAndPruneSharedUserLPw(sharedUserSettingLPr, false);
        }
        removeAppIdLPw(remove.getAppId());
        return true;
    }

    private void removeInstallerPackageStatus(java.lang.String str) {
        if (!this.mInstallerPackages.contains(str)) {
            return;
        }
        for (int i = 0; i < this.mPackages.size(); i++) {
            this.mPackages.valueAt(i).removeInstallerPackage(str);
        }
        this.mInstallerPackages.remove(str);
    }

    public com.android.server.pm.SettingBase getSettingLPr(int i) {
        return this.mAppIds.getSetting(i);
    }

    void removeAppIdLPw(int i) {
        this.mAppIds.removeSetting(i);
    }

    void convertSharedUserSettingsLPw(com.android.server.pm.SharedUserSetting sharedUserSetting) {
        com.android.server.pm.PackageSetting valueAt = sharedUserSetting.getPackageSettings().valueAt(0);
        this.mAppIds.replaceSetting(sharedUserSetting.getAppId(), valueAt);
        valueAt.setSharedUserAppId(-1);
        if (!sharedUserSetting.getDisabledPackageSettings().isEmpty()) {
            sharedUserSetting.getDisabledPackageSettings().valueAt(0).setSharedUserAppId(-1);
        }
        this.mSharedUsers.remove(sharedUserSetting.getName());
    }

    void checkAndConvertSharedUserSettingsLPw(com.android.server.pm.SharedUserSetting sharedUserSetting) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;
        if (sharedUserSetting.isSingleUser() && (pkg = sharedUserSetting.getPackageSettings().valueAt(0).getPkg()) != null && pkg.isLeavingSharedUser() && com.android.server.pm.SharedUidMigration.applyStrategy(2)) {
            convertSharedUserSettingsLPw(sharedUserSetting);
        }
    }

    com.android.server.pm.PreferredIntentResolver editPreferredActivitiesLPw(int i) {
        com.android.server.pm.PreferredIntentResolver preferredIntentResolver = this.mPreferredActivities.get(i);
        if (preferredIntentResolver == null) {
            com.android.server.pm.PreferredIntentResolver preferredIntentResolver2 = new com.android.server.pm.PreferredIntentResolver();
            this.mPreferredActivities.put(i, preferredIntentResolver2);
            return preferredIntentResolver2;
        }
        return preferredIntentResolver;
    }

    com.android.server.pm.PersistentPreferredIntentResolver editPersistentPreferredActivitiesLPw(int i) {
        com.android.server.pm.PersistentPreferredIntentResolver persistentPreferredIntentResolver = this.mPersistentPreferredActivities.get(i);
        if (persistentPreferredIntentResolver == null) {
            com.android.server.pm.PersistentPreferredIntentResolver persistentPreferredIntentResolver2 = new com.android.server.pm.PersistentPreferredIntentResolver();
            this.mPersistentPreferredActivities.put(i, persistentPreferredIntentResolver2);
            return persistentPreferredIntentResolver2;
        }
        return persistentPreferredIntentResolver;
    }

    com.android.server.pm.CrossProfileIntentResolver editCrossProfileIntentResolverLPw(int i) {
        com.android.server.pm.CrossProfileIntentResolver crossProfileIntentResolver = this.mCrossProfileIntentResolvers.get(i);
        if (crossProfileIntentResolver == null) {
            com.android.server.pm.CrossProfileIntentResolver crossProfileIntentResolver2 = new com.android.server.pm.CrossProfileIntentResolver();
            this.mCrossProfileIntentResolvers.put(i, crossProfileIntentResolver2);
            return crossProfileIntentResolver2;
        }
        return crossProfileIntentResolver;
    }

    java.lang.String getPendingDefaultBrowserLPr(int i) {
        return this.mPendingDefaultBrowser.get(i);
    }

    void setPendingDefaultBrowserLPw(java.lang.String str, int i) {
        this.mPendingDefaultBrowser.put(i, str);
    }

    java.lang.String removePendingDefaultBrowserLPw(int i) {
        return this.mPendingDefaultBrowser.removeReturnOld(i);
    }

    private java.io.File getUserSystemDirectory(int i) {
        return new java.io.File(new java.io.File(this.mSystemDir, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS), java.lang.Integer.toString(i));
    }

    private com.android.server.pm.ResilientAtomicFile getUserPackagesStateFile(int i) {
        return new com.android.server.pm.ResilientAtomicFile(new java.io.File(getUserSystemDirectory(i), "package-restrictions.xml"), new java.io.File(getUserSystemDirectory(i), "package-restrictions-backup.xml"), new java.io.File(getUserSystemDirectory(i), "package-restrictions.xml.reservecopy"), com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, "package restrictions", this);
    }

    private com.android.server.pm.ResilientAtomicFile getSettingsFile() {
        return new com.android.server.pm.ResilientAtomicFile(this.mSettingsFilename, this.mPreviousSettingsFilename, this.mSettingsReserveCopyFilename, com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, "package manager settings", this);
    }

    private java.io.File getUserRuntimePermissionsFile(int i) {
        return new java.io.File(getUserSystemDirectory(i), RUNTIME_PERMISSIONS_FILE_NAME);
    }

    void writeAllUsersPackageRestrictionsLPr() {
        writeAllUsersPackageRestrictionsLPr(false);
    }

    void writeAllUsersPackageRestrictionsLPr(boolean z) {
        java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers(com.android.server.pm.UserManagerService.getInstance());
        if (allUsers == null) {
            return;
        }
        if (z) {
            synchronized (this.mPackageRestrictionsLock) {
                this.mPendingAsyncPackageRestrictionsWrites.clear();
            }
            this.mHandler.removeMessages(30);
        }
        java.util.Iterator<android.content.pm.UserInfo> it = allUsers.iterator();
        while (it.hasNext()) {
            writePackageRestrictionsLPr(it.next().id, z);
        }
    }

    void writeAllRuntimePermissionsLPr() {
        for (int i : com.android.server.pm.UserManagerService.getInstance().getUserIds()) {
            this.mRuntimePermissionsPersistence.writeStateForUserAsync(i);
        }
    }

    boolean isPermissionUpgradeNeeded(int i) {
        return this.mRuntimePermissionsPersistence.isPermissionUpgradeNeeded(i);
    }

    void updateRuntimePermissionsFingerprint(int i) {
        this.mRuntimePermissionsPersistence.updateRuntimePermissionsFingerprint(i);
    }

    int getDefaultRuntimePermissionsVersion(int i) {
        return this.mRuntimePermissionsPersistence.getVersion(i);
    }

    void setDefaultRuntimePermissionsVersion(int i, int i2) {
        this.mRuntimePermissionsPersistence.setVersion(i, i2);
    }

    void setPermissionControllerVersion(long j) {
        this.mRuntimePermissionsPersistence.setPermissionControllerVersion(j);
    }

    public com.android.server.pm.Settings.VersionInfo findOrCreateVersion(java.lang.String str) {
        com.android.server.pm.Settings.VersionInfo versionInfo = this.mVersion.get(str);
        if (versionInfo == null) {
            com.android.server.pm.Settings.VersionInfo versionInfo2 = new com.android.server.pm.Settings.VersionInfo();
            this.mVersion.put(str, versionInfo2);
            return versionInfo2;
        }
        return versionInfo;
    }

    public com.android.server.pm.Settings.VersionInfo getInternalVersion() {
        return this.mVersion.get(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
    }

    public com.android.server.pm.Settings.VersionInfo getExternalVersion() {
        return this.mVersion.get("primary_physical");
    }

    public void onVolumeForgotten(java.lang.String str) {
        this.mVersion.remove(str);
    }

    void readPreferredActivitiesLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(TAG_ITEM)) {
                        com.android.server.pm.PreferredActivity preferredActivity = new com.android.server.pm.PreferredActivity(typedXmlPullParser);
                        if (preferredActivity.mPref.getParseError() == null) {
                            com.android.server.pm.PreferredIntentResolver editPreferredActivitiesLPw = editPreferredActivitiesLPw(i);
                            if (editPreferredActivitiesLPw.shouldAddPreferredActivity(preferredActivity)) {
                                editPreferredActivitiesLPw.addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) null, (com.android.server.pm.snapshot.PackageDataSnapshot) preferredActivity);
                            }
                        } else {
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <preferred-activity> " + preferredActivity.mPref.getParseError() + " at " + typedXmlPullParser.getPositionDescription());
                        }
                    } else {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <preferred-activities>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readPersistentPreferredActivitiesLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(TAG_ITEM)) {
                        editPersistentPreferredActivitiesLPw(i).addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) null, (com.android.server.pm.snapshot.PackageDataSnapshot) new com.android.server.pm.PersistentPreferredActivity(typedXmlPullParser));
                    } else {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <persistent-preferred-activities>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readCrossProfileIntentFiltersLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (name.equals(TAG_ITEM)) {
                        editCrossProfileIntentResolverLPw(i).addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) null, (com.android.server.pm.snapshot.PackageDataSnapshot) new com.android.server.pm.CrossProfileIntentFilter(typedXmlPullParser));
                    } else {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under crossProfile-intent-filters: " + name);
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    void readDefaultAppsLPw(org.xmlpull.v1.XmlPullParser xmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String readDefaultApps = readDefaultApps(xmlPullParser);
        if (readDefaultApps != null) {
            this.mPendingDefaultBrowser.put(i, readDefaultApps);
        }
    }

    @android.annotation.Nullable
    static java.lang.String readDefaultApps(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlPullParser.getDepth();
        java.lang.String str = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals(TAG_DEFAULT_BROWSER)) {
                    str = xmlPullParser.getAttributeValue(null, "packageName");
                } else if (!name.equals(TAG_DEFAULT_DIALER)) {
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under default-apps: " + xmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
        return str;
    }

    void readBlockUninstallPackagesLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals(TAG_BLOCK_UNINSTALL)) {
                    arraySet.add(typedXmlPullParser.getAttributeValue((java.lang.String) null, "packageName"));
                } else {
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under block-uninstall-packages: " + typedXmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        if (arraySet.isEmpty()) {
            this.mBlockUninstallPackages.remove(i);
        } else {
            this.mBlockUninstallPackages.put(i, arraySet);
        }
    }

    @Override // com.android.server.pm.ResilientAtomicFile.ReadEventLogger
    public void logEvent(int i, java.lang.String str) {
        this.mReadMessages.append(str + "\n");
        com.android.server.pm.PackageManagerService.reportSettingsProblem(i, str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:130:0x0235. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:104:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0327 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0442  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0317 A[Catch: all -> 0x00a1, IOException | XmlPullParserException -> 0x0281, TRY_LEAVE, TryCatch #0 {all -> 0x00a1, blocks: (B:237:0x0425, B:13:0x007f, B:14:0x0083, B:21:0x0091, B:25:0x00af, B:26:0x00b4, B:30:0x00be, B:47:0x00ca, B:49:0x00d7, B:190:0x00e8, B:52:0x0102, B:54:0x012d, B:55:0x0138, B:59:0x0159, B:60:0x01b9, B:64:0x01c5, B:114:0x02e3, B:116:0x02e9, B:72:0x02ff, B:74:0x0317, B:79:0x0376, B:82:0x0381, B:106:0x0327, B:125:0x01de, B:127:0x01ea, B:146:0x0238, B:148:0x0263, B:132:0x0271, B:136:0x027b, B:137:0x0286, B:151:0x0296, B:153:0x02a0, B:155:0x02aa, B:157:0x02b5, B:159:0x02be, B:160:0x01f5, B:163:0x0200, B:166:0x020b, B:169:0x0216, B:172:0x0220, B:175:0x022a, B:193:0x038f, B:195:0x03a6, B:196:0x03ac, B:198:0x03b7, B:199:0x03bb, B:201:0x03c3, B:202:0x03c7, B:204:0x03cf, B:205:0x03d3, B:207:0x03db, B:208:0x03df), top: B:4:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0324  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x044a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void readPackageRestrictionsLPr(int i, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap) {
        com.android.server.pm.ResilientAtomicFile resilientAtomicFile;
        java.lang.Throwable th;
        com.android.server.pm.ResilientAtomicFile resilientAtomicFile2;
        java.lang.Exception exc;
        java.lang.Object obj;
        java.lang.Object obj2;
        java.lang.Throwable th2;
        android.content.pm.SuspendDialogInfo openRead;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        int next;
        char c;
        boolean z;
        char c2;
        int i2;
        char c3;
        boolean z2;
        char c4;
        android.content.pm.SuspendDialogInfo suspendDialogInfo;
        com.android.modules.utils.TypedXmlPullParser typedXmlPullParser;
        int i3;
        int i4;
        int i5;
        java.lang.Exception e;
        android.content.pm.SuspendDialogInfo build;
        android.content.pm.SuspendDialogInfo suspendDialogInfo2;
        boolean z3;
        int i6;
        char c5;
        int i7 = i;
        com.android.server.pm.ResilientAtomicFile userPackagesStateFile = getUserPackagesStateFile(i);
        android.content.pm.SuspendDialogInfo suspendDialogInfo3 = null;
        try {
            obj = this.mPackageRestrictionsLock;
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
            resilientAtomicFile = userPackagesStateFile;
        } catch (java.lang.Throwable th3) {
            th = th3;
        }
        try {
            synchronized (obj) {
                try {
                    openRead = userPackagesStateFile.openRead();
                    try {
                        if (openRead == null) {
                            try {
                                for (com.android.server.pm.PackageSetting packageSetting : this.mPackages.values()) {
                                    java.lang.Object obj3 = obj;
                                    com.android.server.pm.ResilientAtomicFile resilientAtomicFile3 = userPackagesStateFile;
                                    packageSetting.setUserState(i, packageSetting.getCeDataInode(i7), packageSetting.getDeDataInode(i7), 0, true, false, false, false, 0, null, false, false, null, null, null, 0, 0, null, null, 0L, 0, null);
                                    i7 = i;
                                    userPackagesStateFile = resilientAtomicFile3;
                                    obj = obj3;
                                }
                                com.android.server.pm.ResilientAtomicFile resilientAtomicFile4 = userPackagesStateFile;
                                resilientAtomicFile4.close();
                                return;
                            } catch (java.lang.Throwable th4) {
                                th = th4;
                                obj2 = obj;
                                resilientAtomicFile = userPackagesStateFile;
                                th2 = th;
                                suspendDialogInfo3 = openRead;
                                while (true) {
                                    try {
                                        try {
                                            break;
                                        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e3) {
                                            e = e3;
                                            exc = e;
                                            resilientAtomicFile2 = resilientAtomicFile;
                                            try {
                                                resilientAtomicFile2.failRead(suspendDialogInfo3, exc);
                                                readPackageRestrictionsLPr(i, arrayMap);
                                                if (resilientAtomicFile2 == null) {
                                                }
                                            } catch (java.lang.Throwable th5) {
                                                th = th5;
                                                if (resilientAtomicFile2 != null) {
                                                }
                                            }
                                        }
                                    } catch (java.lang.Throwable th6) {
                                        th2 = th6;
                                    }
                                    th2 = th6;
                                }
                                throw th2;
                            }
                        } else {
                            resilientAtomicFile = userPackagesStateFile;
                        }
                    } catch (java.lang.Throwable th7) {
                        th = th7;
                    }
                } catch (java.lang.Throwable th8) {
                    obj2 = obj;
                    resilientAtomicFile = userPackagesStateFile;
                    th2 = th8;
                }
            }
            try {
                resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                do {
                    next = resolvePullParser.next();
                    c = 2;
                    z = true;
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                c2 = 5;
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e4) {
                e = e4;
            }
            if (next != 2) {
                this.mReadMessages.append("No start tag found in package restrictions file\n");
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager package restrictions file");
                resilientAtomicFile.close();
                return;
            }
            int depth = resolvePullParser.getDepth();
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 != z && (next2 != 3 || resolvePullParser.getDepth() > depth)) {
                    if (next2 != 3 && next2 != 4) {
                        java.lang.String name = resolvePullParser.getName();
                        if (name.equals(TAG_PACKAGE)) {
                            java.lang.String attributeValue = resolvePullParser.getAttributeValue(suspendDialogInfo3, "name");
                            com.android.server.pm.PackageSetting packageSetting2 = this.mPackages.get(attributeValue);
                            if (packageSetting2 == null) {
                                android.util.Slog.w("PackageManager", "No package known for package restrictions " + attributeValue);
                                com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                            } else {
                                long attributeLong = resolvePullParser.getAttributeLong(suspendDialogInfo3, ATTR_CE_DATA_INODE, 0L);
                                long attributeLong2 = resolvePullParser.getAttributeLong(suspendDialogInfo3, ATTR_DE_DATA_INODE, 0L);
                                boolean attributeBoolean = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_INSTALLED, z);
                                boolean attributeBoolean2 = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_STOPPED, false);
                                boolean attributeBoolean3 = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_NOT_LAUNCHED, false);
                                boolean attributeBoolean4 = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_HIDDEN, false);
                                boolean attributeBoolean5 = !attributeBoolean4 ? resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_BLOCKED, false) : attributeBoolean4;
                                int attributeInt = resolvePullParser.getAttributeInt(suspendDialogInfo3, ATTR_DISTRACTION_FLAGS, 0);
                                boolean attributeBoolean6 = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_SUSPENDED, false);
                                java.lang.String attributeValue2 = resolvePullParser.getAttributeValue(suspendDialogInfo3, ATTR_SUSPENDING_PACKAGE);
                                java.lang.String attributeValue3 = resolvePullParser.getAttributeValue(suspendDialogInfo3, ATTR_SUSPEND_DIALOG_MESSAGE);
                                if (attributeBoolean6 && attributeValue2 == null) {
                                    attributeValue2 = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
                                }
                                boolean attributeBoolean7 = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_BLOCK_UNINSTALL, false);
                                boolean attributeBoolean8 = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_INSTANT_APP, false);
                                boolean attributeBoolean9 = resolvePullParser.getAttributeBoolean(suspendDialogInfo3, ATTR_VIRTUAL_PRELOAD, false);
                                int attributeInt2 = resolvePullParser.getAttributeInt(suspendDialogInfo3, "enabled", 0);
                                java.lang.String attributeValue4 = resolvePullParser.getAttributeValue(suspendDialogInfo3, ATTR_ENABLED_CALLER);
                                java.lang.String attributeValue5 = resolvePullParser.getAttributeValue(suspendDialogInfo3, ATTR_HARMFUL_APP_WARNING);
                                int attributeInt3 = resolvePullParser.getAttributeInt(suspendDialogInfo3, ATTR_DOMAIN_VERIFICATION_STATE, 0);
                                int attributeInt4 = resolvePullParser.getAttributeInt(suspendDialogInfo3, ATTR_INSTALL_REASON, 0);
                                int attributeInt5 = resolvePullParser.getAttributeInt(suspendDialogInfo3, ATTR_UNINSTALL_REASON, 0);
                                java.lang.String attributeValue6 = resolvePullParser.getAttributeValue(suspendDialogInfo3, ATTR_SPLASH_SCREEN_THEME);
                                long attributeLongHex = resolvePullParser.getAttributeLongHex(suspendDialogInfo3, ATTR_FIRST_INSTALL_TIME, 0L);
                                int attributeInt6 = resolvePullParser.getAttributeInt(suspendDialogInfo3, ATTR_MIN_ASPECT_RATIO, 0);
                                int depth2 = resolvePullParser.getDepth();
                                android.content.pm.SuspendDialogInfo suspendDialogInfo4 = suspendDialogInfo3;
                                android.content.pm.SuspendDialogInfo suspendDialogInfo5 = suspendDialogInfo4;
                                android.content.pm.SuspendDialogInfo suspendDialogInfo6 = suspendDialogInfo5;
                                android.content.pm.SuspendDialogInfo suspendDialogInfo7 = suspendDialogInfo6;
                                android.content.pm.SuspendDialogInfo suspendDialogInfo8 = suspendDialogInfo7;
                                android.content.pm.SuspendDialogInfo suspendDialogInfo9 = suspendDialogInfo8;
                                while (true) {
                                    int next3 = resolvePullParser.next();
                                    i3 = attributeInt3;
                                    if (next3 == 1) {
                                        i4 = depth;
                                        i5 = i;
                                    } else if (next3 != 3 || resolvePullParser.getDepth() > depth2) {
                                        if (next3 == 3) {
                                            i6 = depth;
                                        } else if (next3 == 4) {
                                            i6 = depth;
                                        } else {
                                            try {
                                                java.lang.String name2 = resolvePullParser.getName();
                                                switch (name2.hashCode()) {
                                                    case -2096193594:
                                                        if (name2.equals(TAG_ARCHIVE_STATE)) {
                                                            c5 = 6;
                                                            break;
                                                        }
                                                        c5 = 65535;
                                                        break;
                                                    case -2027581689:
                                                        if (name2.equals(TAG_DISABLED_COMPONENTS)) {
                                                            c5 = 1;
                                                            break;
                                                        }
                                                        c5 = 65535;
                                                        break;
                                                    case -1963032286:
                                                        if (name2.equals(TAG_ENABLED_COMPONENTS)) {
                                                            c5 = 0;
                                                            break;
                                                        }
                                                        c5 = 65535;
                                                        break;
                                                    case -1592287551:
                                                        if (name2.equals(TAG_SUSPENDED_APP_EXTRAS)) {
                                                            c5 = 3;
                                                            break;
                                                        }
                                                        c5 = 65535;
                                                        break;
                                                    case -1422791362:
                                                        if (name2.equals(TAG_SUSPENDED_LAUNCHER_EXTRAS)) {
                                                            c5 = 4;
                                                            break;
                                                        }
                                                        c5 = 65535;
                                                        break;
                                                    case -858175433:
                                                        if (name2.equals(TAG_SUSPEND_PARAMS)) {
                                                            c5 = 5;
                                                            break;
                                                        }
                                                        c5 = 65535;
                                                        break;
                                                    case 1660896545:
                                                        if (name2.equals(TAG_SUSPENDED_DIALOG_INFO)) {
                                                            c5 = 2;
                                                            break;
                                                        }
                                                        c5 = 65535;
                                                        break;
                                                    default:
                                                        c5 = 65535;
                                                        break;
                                                }
                                                switch (c5) {
                                                    case 0:
                                                        i6 = depth;
                                                        suspendDialogInfo6 = readComponentsLPr(resolvePullParser);
                                                        attributeInt3 = i3;
                                                        depth = i6;
                                                        break;
                                                    case 1:
                                                        i6 = depth;
                                                        suspendDialogInfo7 = readComponentsLPr(resolvePullParser);
                                                        attributeInt3 = i3;
                                                        depth = i6;
                                                        break;
                                                    case 2:
                                                        i6 = depth;
                                                        suspendDialogInfo9 = android.content.pm.SuspendDialogInfo.restoreFromXml(resolvePullParser);
                                                        attributeInt3 = i3;
                                                        depth = i6;
                                                        break;
                                                    case 3:
                                                        i6 = depth;
                                                        suspendDialogInfo3 = android.os.PersistableBundle.restoreFromXml(resolvePullParser);
                                                        attributeInt3 = i3;
                                                        depth = i6;
                                                        break;
                                                    case 4:
                                                        i6 = depth;
                                                        suspendDialogInfo4 = android.os.PersistableBundle.restoreFromXml(resolvePullParser);
                                                        attributeInt3 = i3;
                                                        depth = i6;
                                                        break;
                                                    case 5:
                                                        i6 = depth;
                                                        java.util.Map.Entry<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> readSuspensionParamsLPr = readSuspensionParamsLPr(i, resolvePullParser);
                                                        if (readSuspensionParamsLPr == null) {
                                                            break;
                                                        } else {
                                                            if (suspendDialogInfo5 == null) {
                                                                suspendDialogInfo5 = new android.util.ArrayMap();
                                                            }
                                                            suspendDialogInfo5.put(readSuspensionParamsLPr.getKey(), readSuspensionParamsLPr.getValue());
                                                            attributeInt3 = i3;
                                                            depth = i6;
                                                            break;
                                                        }
                                                    case 6:
                                                        i6 = depth;
                                                        suspendDialogInfo8 = parseArchiveState(resolvePullParser);
                                                        attributeInt3 = i3;
                                                        depth = i6;
                                                        break;
                                                    default:
                                                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                                        i6 = depth;
                                                        sb.append("Unknown tag ");
                                                        sb.append(resolvePullParser.getName());
                                                        sb.append(" under tag ");
                                                        sb.append(TAG_PACKAGE);
                                                        android.util.Slog.wtf(TAG, sb.toString());
                                                        attributeInt3 = i3;
                                                        depth = i6;
                                                        break;
                                                }
                                            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e5) {
                                                e = e5;
                                                i5 = i;
                                                exc = e;
                                                suspendDialogInfo3 = openRead;
                                                resilientAtomicFile2 = resilientAtomicFile;
                                                resilientAtomicFile2.failRead(suspendDialogInfo3, exc);
                                                readPackageRestrictionsLPr(i, arrayMap);
                                                if (resilientAtomicFile2 == null) {
                                                }
                                            }
                                        }
                                        attributeInt3 = i3;
                                        depth = i6;
                                    } else {
                                        i4 = depth;
                                        i5 = i;
                                    }
                                }
                                try {
                                    if (suspendDialogInfo9 == null) {
                                        try {
                                            if (!android.text.TextUtils.isEmpty(attributeValue3)) {
                                                build = new android.content.pm.SuspendDialogInfo.Builder().setMessage(attributeValue3).build();
                                                if (attributeBoolean6 || suspendDialogInfo5 != null) {
                                                    suspendDialogInfo2 = suspendDialogInfo5;
                                                } else {
                                                    com.android.server.pm.pkg.SuspendParams suspendParams = new com.android.server.pm.pkg.SuspendParams(build, suspendDialogInfo3, suspendDialogInfo4, false);
                                                    android.content.pm.SuspendDialogInfo arrayMap2 = new android.util.ArrayMap();
                                                    arrayMap2.put(android.content.pm.UserPackage.of(i5, attributeValue2), suspendParams);
                                                    suspendDialogInfo2 = arrayMap2;
                                                }
                                                if (attributeBoolean7) {
                                                    z3 = true;
                                                } else {
                                                    z3 = true;
                                                    setBlockUninstallLPw(i5, attributeValue, true);
                                                }
                                                if (attributeLongHex != 0) {
                                                    try {
                                                        attributeLongHex = arrayMap.getOrDefault(attributeValue, 0L).longValue();
                                                    } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e6) {
                                                        e = e6;
                                                        exc = e;
                                                        suspendDialogInfo3 = openRead;
                                                        resilientAtomicFile2 = resilientAtomicFile;
                                                        resilientAtomicFile2.failRead(suspendDialogInfo3, exc);
                                                        readPackageRestrictionsLPr(i, arrayMap);
                                                        if (resilientAtomicFile2 == null) {
                                                        }
                                                    }
                                                }
                                                i2 = i4;
                                                c3 = 5;
                                                z2 = z3;
                                                c4 = 2;
                                                com.android.modules.utils.TypedXmlPullParser typedXmlPullParser2 = resolvePullParser;
                                                suspendDialogInfo = null;
                                                packageSetting2.setUserState(i, attributeLong, attributeLong2, attributeInt2, attributeBoolean, attributeBoolean2, attributeBoolean3, attributeBoolean5, attributeInt, suspendDialogInfo2, attributeBoolean8, attributeBoolean9, attributeValue4, suspendDialogInfo6, suspendDialogInfo7, attributeInt4, attributeInt5, attributeValue5, attributeValue6, attributeLongHex, attributeInt6, suspendDialogInfo8);
                                                this.mDomainVerificationManager.setLegacyUserState(attributeValue, i, i3);
                                                typedXmlPullParser = typedXmlPullParser2;
                                            }
                                        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e7) {
                                            e = e7;
                                            exc = e;
                                            suspendDialogInfo3 = openRead;
                                            resilientAtomicFile2 = resilientAtomicFile;
                                            resilientAtomicFile2.failRead(suspendDialogInfo3, exc);
                                            readPackageRestrictionsLPr(i, arrayMap);
                                            if (resilientAtomicFile2 == null) {
                                            }
                                        }
                                    }
                                    this.mDomainVerificationManager.setLegacyUserState(attributeValue, i, i3);
                                    typedXmlPullParser = typedXmlPullParser2;
                                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e8) {
                                    e = e8;
                                    exc = e;
                                    suspendDialogInfo3 = openRead;
                                    resilientAtomicFile2 = resilientAtomicFile;
                                    resilientAtomicFile2.failRead(suspendDialogInfo3, exc);
                                    readPackageRestrictionsLPr(i, arrayMap);
                                    if (resilientAtomicFile2 == null) {
                                    }
                                }
                                build = suspendDialogInfo9;
                                if (attributeBoolean6) {
                                }
                                suspendDialogInfo2 = suspendDialogInfo5;
                                if (attributeBoolean7) {
                                }
                                if (attributeLongHex != 0) {
                                }
                                i2 = i4;
                                c3 = 5;
                                z2 = z3;
                                c4 = 2;
                                com.android.modules.utils.TypedXmlPullParser typedXmlPullParser22 = resolvePullParser;
                                suspendDialogInfo = null;
                                packageSetting2.setUserState(i, attributeLong, attributeLong2, attributeInt2, attributeBoolean, attributeBoolean2, attributeBoolean3, attributeBoolean5, attributeInt, suspendDialogInfo2, attributeBoolean8, attributeBoolean9, attributeValue4, suspendDialogInfo6, suspendDialogInfo7, attributeInt4, attributeInt5, attributeValue5, attributeValue6, attributeLongHex, attributeInt6, suspendDialogInfo8);
                            }
                        } else {
                            i2 = depth;
                            c3 = c2;
                            z2 = z;
                            c4 = c;
                            com.android.modules.utils.TypedXmlPullParser typedXmlPullParser3 = resolvePullParser;
                            suspendDialogInfo = suspendDialogInfo3;
                            if (name.equals("preferred-activities")) {
                                typedXmlPullParser = typedXmlPullParser3;
                                readPreferredActivitiesLPw(typedXmlPullParser, i);
                            } else {
                                typedXmlPullParser = typedXmlPullParser3;
                                if (name.equals(TAG_PERSISTENT_PREFERRED_ACTIVITIES)) {
                                    readPersistentPreferredActivitiesLPw(typedXmlPullParser, i);
                                } else if (name.equals(TAG_CROSS_PROFILE_INTENT_FILTERS)) {
                                    readCrossProfileIntentFiltersLPw(typedXmlPullParser, i);
                                } else if (name.equals(TAG_DEFAULT_APPS)) {
                                    readDefaultAppsLPw(typedXmlPullParser, i);
                                } else if (name.equals(TAG_BLOCK_UNINSTALL_PACKAGES)) {
                                    readBlockUninstallPackagesLPw(typedXmlPullParser, i);
                                } else {
                                    android.util.Slog.w("PackageManager", "Unknown element under <stopped-packages>: " + typedXmlPullParser.getName());
                                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                                }
                            }
                        }
                        resolvePullParser = typedXmlPullParser;
                        depth = i2;
                        c2 = c3;
                        z = z2;
                        c = c4;
                        suspendDialogInfo3 = suspendDialogInfo;
                    }
                }
            }
            resilientAtomicFile2 = resilientAtomicFile;
            if (resilientAtomicFile2 == null) {
                resilientAtomicFile2.close();
            }
        } catch (java.lang.Throwable th9) {
            th = th9;
            th = th;
            resilientAtomicFile2 = userPackagesStateFile;
            if (resilientAtomicFile2 != null) {
                throw th;
            }
            try {
                resilientAtomicFile2.close();
                throw th;
            } catch (java.lang.Throwable th10) {
                th.addSuppressed(th10);
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private static java.util.Map.Entry<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> readSuspensionParamsLPr(int i, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_SUSPENDING_PACKAGE);
        if (attributeValue == null) {
            android.util.Slog.wtf(TAG, "No suspendingPackage found inside tag suspend-params");
            return null;
        }
        return java.util.Map.entry(android.content.pm.UserPackage.of(i, attributeValue), com.android.server.pm.pkg.SuspendParams.restoreFromXml(typedXmlPullParser));
    }

    private static com.android.server.pm.pkg.ArchiveState parseArchiveState(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_ARCHIVE_INSTALLER_TITLE);
        long attributeLongHex = typedXmlPullParser.getAttributeLongHex((java.lang.String) null, ATTR_ARCHIVE_TIME, 0L);
        java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> parseArchiveActivityInfos = parseArchiveActivityInfos(typedXmlPullParser);
        if (attributeValue == null) {
            android.util.Slog.wtf(TAG, "parseArchiveState: installerTitle is null");
            return null;
        }
        if (parseArchiveActivityInfos.size() < 1) {
            android.util.Slog.wtf(TAG, "parseArchiveState: activityInfos is empty");
            return null;
        }
        return new com.android.server.pm.pkg.ArchiveState(parseArchiveActivityInfos, attributeValue, attributeLongHex);
    }

    private static java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> parseArchiveActivityInfos(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && typedXmlPullParser.getName().equals(TAG_ARCHIVE_ACTIVITY_INFO)) {
                java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_ARCHIVE_ACTIVITY_TITLE);
                java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_ARCHIVE_ORIGINAL_COMPONENT_NAME);
                java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_ARCHIVE_ICON_PATH);
                java.nio.file.Path of = attributeValue3 == null ? null : java.nio.file.Path.of(attributeValue3, new java.lang.String[0]);
                java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_ARCHIVE_MONOCHROME_ICON_PATH);
                java.nio.file.Path of2 = attributeValue4 != null ? java.nio.file.Path.of(attributeValue4, new java.lang.String[0]) : null;
                if (attributeValue == null || attributeValue2 == null || of == null) {
                    android.util.Slog.wtf(TAG, android.text.TextUtils.formatSimple("Missing attributes in tag %s. %s: %s, %s: %s, %s: %s", new java.lang.Object[]{TAG_ARCHIVE_ACTIVITY_INFO, ATTR_ARCHIVE_ACTIVITY_TITLE, attributeValue, ATTR_ARCHIVE_ORIGINAL_COMPONENT_NAME, attributeValue2, ATTR_ARCHIVE_ICON_PATH, of}));
                } else {
                    arrayList.add(new com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo(attributeValue, android.content.ComponentName.unflattenFromString(attributeValue2), of, of2));
                }
            }
        }
        return arrayList;
    }

    void setBlockUninstallLPw(int i, java.lang.String str, boolean z) {
        android.util.ArraySet<java.lang.String> arraySet = this.mBlockUninstallPackages.get(i);
        if (z) {
            if (arraySet == null) {
                arraySet = new android.util.ArraySet<>();
                this.mBlockUninstallPackages.put(i, arraySet);
            }
            arraySet.add(str);
            return;
        }
        if (arraySet != null) {
            arraySet.remove(str);
            if (arraySet.isEmpty()) {
                this.mBlockUninstallPackages.remove(i);
            }
        }
    }

    void clearBlockUninstallLPw(int i) {
        this.mBlockUninstallPackages.remove(i);
    }

    boolean getBlockUninstallLPr(int i, java.lang.String str) {
        android.util.ArraySet<java.lang.String> arraySet = this.mBlockUninstallPackages.get(i);
        if (arraySet == null) {
            return false;
        }
        return arraySet.contains(str);
    }

    private android.util.ArraySet<java.lang.String> readComponentsLPr(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue;
        int depth = typedXmlPullParser.getDepth();
        android.util.ArraySet<java.lang.String> arraySet = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && typedXmlPullParser.getName().equals(TAG_ITEM) && (attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name")) != null) {
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                }
                arraySet.add(attributeValue);
            }
        }
        return arraySet;
    }

    void writePreferredActivitiesLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i, boolean z) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "preferred-activities");
        com.android.server.pm.PreferredIntentResolver preferredIntentResolver = this.mPreferredActivities.get(i);
        if (preferredIntentResolver != null) {
            for (F f : preferredIntentResolver.filterSet()) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_ITEM);
                f.writeToXml(typedXmlSerializer, z);
                typedXmlSerializer.endTag((java.lang.String) null, TAG_ITEM);
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, "preferred-activities");
    }

    void writePersistentPreferredActivitiesLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_PERSISTENT_PREFERRED_ACTIVITIES);
        com.android.server.pm.PersistentPreferredIntentResolver persistentPreferredIntentResolver = this.mPersistentPreferredActivities.get(i);
        if (persistentPreferredIntentResolver != null) {
            for (F f : persistentPreferredIntentResolver.filterSet()) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_ITEM);
                f.writeToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((java.lang.String) null, TAG_ITEM);
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_PERSISTENT_PREFERRED_ACTIVITIES);
    }

    void writeCrossProfileIntentFiltersLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_CROSS_PROFILE_INTENT_FILTERS);
        com.android.server.pm.CrossProfileIntentResolver crossProfileIntentResolver = this.mCrossProfileIntentResolvers.get(i);
        if (crossProfileIntentResolver != null) {
            for (F f : crossProfileIntentResolver.filterSet()) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_ITEM);
                f.writeToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((java.lang.String) null, TAG_ITEM);
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_CROSS_PROFILE_INTENT_FILTERS);
    }

    void writeDefaultAppsLPr(org.xmlpull.v1.XmlSerializer xmlSerializer, int i) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        writeDefaultApps(xmlSerializer, this.mPendingDefaultBrowser.get(i));
    }

    static void writeDefaultApps(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer, @android.annotation.Nullable java.lang.String str) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        xmlSerializer.startTag(null, TAG_DEFAULT_APPS);
        if (!android.text.TextUtils.isEmpty(str)) {
            xmlSerializer.startTag(null, TAG_DEFAULT_BROWSER);
            xmlSerializer.attribute(null, "packageName", str);
            xmlSerializer.endTag(null, TAG_DEFAULT_BROWSER);
        }
        xmlSerializer.endTag(null, TAG_DEFAULT_APPS);
    }

    void writeBlockUninstallPackagesLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.io.IOException {
        android.util.ArraySet<java.lang.String> arraySet = this.mBlockUninstallPackages.get(i);
        if (arraySet != null) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_BLOCK_UNINSTALL_PACKAGES);
            for (int i2 = 0; i2 < arraySet.size(); i2++) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_BLOCK_UNINSTALL);
                typedXmlSerializer.attribute((java.lang.String) null, "packageName", arraySet.valueAt(i2));
                typedXmlSerializer.endTag((java.lang.String) null, TAG_BLOCK_UNINSTALL);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_BLOCK_UNINSTALL_PACKAGES);
        }
    }

    void writePackageRestrictionsLPr(int i) {
        writePackageRestrictionsLPr(i, false);
    }

    void writePackageRestrictionsLPr(final int i, final boolean z) {
        invalidatePackageCache();
        final long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (z) {
            lambda$writePackageRestrictionsLPr$1(i, uptimeMillis, z);
            return;
        }
        synchronized (this.mPackageRestrictionsLock) {
            this.mPendingAsyncPackageRestrictionsWrites.put(i, this.mPendingAsyncPackageRestrictionsWrites.get(i, 0) + 1);
        }
        this.mHandler.obtainMessage(30, new java.lang.Runnable() { // from class: com.android.server.pm.Settings$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.Settings.this.lambda$writePackageRestrictionsLPr$1(i, uptimeMillis, z);
            }
        }).sendToTarget();
    }

    void writePackageRestrictions(java.lang.Integer[] numArr) {
        invalidatePackageCache();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        for (java.lang.Integer num : numArr) {
            lambda$writePackageRestrictionsLPr$1(num.intValue(), uptimeMillis, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0030, code lost:
    
        if (r2 == null) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0032, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0035, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:?, code lost:
    
        return;
     */
    /* renamed from: writePackageRestrictions, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void lambda$writePackageRestrictionsLPr$1(int i, long j, boolean z) {
        java.io.FileOutputStream startWrite;
        com.android.server.pm.ResilientAtomicFile userPackagesStateFile = getUserPackagesStateFile(i);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                synchronized (this.mPackageRestrictionsLock) {
                    try {
                        if (!z) {
                            int i2 = this.mPendingAsyncPackageRestrictionsWrites.get(i, 0) - 1;
                            if (i2 < 0) {
                                android.util.Log.i(TAG, "Cancel writing package restrictions for user=" + i);
                            } else {
                                this.mPendingAsyncPackageRestrictionsWrites.put(i, i2);
                            }
                        }
                        try {
                            startWrite = userPackagesStateFile.startWrite();
                            try {
                            } catch (java.lang.Throwable th) {
                                th = th;
                                fileOutputStream = startWrite;
                                throw th;
                            }
                        } catch (java.io.IOException e) {
                            android.util.Slog.wtf("PackageManager", "Unable to write package manager package restrictions,  current changes will be lost at reboot", e);
                            if (userPackagesStateFile != null) {
                                userPackagesStateFile.close();
                                return;
                            }
                            return;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                    }
                }
            } catch (java.io.IOException e2) {
                e = e2;
            }
            try {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((java.lang.String) null, true);
                        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                        resolveSerializer.startTag((java.lang.String) null, TAG_PACKAGE_RESTRICTIONS);
                        for (com.android.server.pm.PackageSetting packageSetting : this.mPackages.values()) {
                            com.android.server.pm.pkg.PackageUserStateInternal readUserState = packageSetting.readUserState(i);
                            resolveSerializer.startTag((java.lang.String) null, TAG_PACKAGE);
                            resolveSerializer.attribute((java.lang.String) null, "name", packageSetting.getPackageName());
                            if (readUserState.getCeDataInode() != 0) {
                                resolveSerializer.attributeLong((java.lang.String) null, ATTR_CE_DATA_INODE, readUserState.getCeDataInode());
                            }
                            if (readUserState.getDeDataInode() != 0) {
                                resolveSerializer.attributeLong((java.lang.String) null, ATTR_DE_DATA_INODE, readUserState.getDeDataInode());
                            }
                            if (!readUserState.isInstalled()) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_INSTALLED, false);
                            }
                            if (readUserState.isStopped()) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_STOPPED, true);
                            }
                            if (readUserState.isNotLaunched()) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_NOT_LAUNCHED, true);
                            }
                            if (readUserState.isHidden()) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_HIDDEN, true);
                            }
                            if (readUserState.getDistractionFlags() != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, ATTR_DISTRACTION_FLAGS, readUserState.getDistractionFlags());
                            }
                            if (readUserState.isSuspended()) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_SUSPENDED, true);
                            }
                            if (readUserState.isInstantApp()) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_INSTANT_APP, true);
                            }
                            if (readUserState.isVirtualPreload()) {
                                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_VIRTUAL_PRELOAD, true);
                            }
                            if (readUserState.getEnabledState() != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, "enabled", readUserState.getEnabledState());
                            }
                            if (readUserState.getLastDisableAppCaller() != null) {
                                resolveSerializer.attribute((java.lang.String) null, ATTR_ENABLED_CALLER, readUserState.getLastDisableAppCaller());
                            }
                            if (readUserState.getInstallReason() != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, ATTR_INSTALL_REASON, readUserState.getInstallReason());
                            }
                            resolveSerializer.attributeLongHex((java.lang.String) null, ATTR_FIRST_INSTALL_TIME, readUserState.getFirstInstallTimeMillis());
                            if (readUserState.getUninstallReason() != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, ATTR_UNINSTALL_REASON, readUserState.getUninstallReason());
                            }
                            if (readUserState.getHarmfulAppWarning() != null) {
                                resolveSerializer.attribute((java.lang.String) null, ATTR_HARMFUL_APP_WARNING, readUserState.getHarmfulAppWarning());
                            }
                            if (readUserState.getSplashScreenTheme() != null) {
                                resolveSerializer.attribute((java.lang.String) null, ATTR_SPLASH_SCREEN_THEME, readUserState.getSplashScreenTheme());
                            }
                            if (readUserState.getMinAspectRatio() != 0) {
                                resolveSerializer.attributeInt((java.lang.String) null, ATTR_MIN_ASPECT_RATIO, readUserState.getMinAspectRatio());
                            }
                            if (readUserState.isSuspended()) {
                                for (int i3 = 0; i3 < readUserState.getSuspendParams().size(); i3++) {
                                    android.content.pm.UserPackage keyAt = readUserState.getSuspendParams().keyAt(i3);
                                    resolveSerializer.startTag((java.lang.String) null, TAG_SUSPEND_PARAMS);
                                    resolveSerializer.attribute((java.lang.String) null, ATTR_SUSPENDING_PACKAGE, keyAt.packageName);
                                    com.android.server.pm.pkg.SuspendParams valueAt = readUserState.getSuspendParams().valueAt(i3);
                                    if (valueAt != null) {
                                        valueAt.saveToXml(resolveSerializer);
                                    }
                                    resolveSerializer.endTag((java.lang.String) null, TAG_SUSPEND_PARAMS);
                                }
                            }
                            android.util.ArraySet<java.lang.String> m6429getEnabledComponents = readUserState.m6429getEnabledComponents();
                            if (m6429getEnabledComponents != null && m6429getEnabledComponents.size() > 0) {
                                resolveSerializer.startTag((java.lang.String) null, TAG_ENABLED_COMPONENTS);
                                for (int i4 = 0; i4 < m6429getEnabledComponents.size(); i4++) {
                                    resolveSerializer.startTag((java.lang.String) null, TAG_ITEM);
                                    resolveSerializer.attribute((java.lang.String) null, "name", m6429getEnabledComponents.valueAt(i4));
                                    resolveSerializer.endTag((java.lang.String) null, TAG_ITEM);
                                }
                                resolveSerializer.endTag((java.lang.String) null, TAG_ENABLED_COMPONENTS);
                            }
                            android.util.ArraySet<java.lang.String> m6428getDisabledComponents = readUserState.m6428getDisabledComponents();
                            if (m6428getDisabledComponents != null && m6428getDisabledComponents.size() > 0) {
                                resolveSerializer.startTag((java.lang.String) null, TAG_DISABLED_COMPONENTS);
                                for (int i5 = 0; i5 < m6428getDisabledComponents.size(); i5++) {
                                    resolveSerializer.startTag((java.lang.String) null, TAG_ITEM);
                                    resolveSerializer.attribute((java.lang.String) null, "name", m6428getDisabledComponents.valueAt(i5));
                                    resolveSerializer.endTag((java.lang.String) null, TAG_ITEM);
                                }
                                resolveSerializer.endTag((java.lang.String) null, TAG_DISABLED_COMPONENTS);
                            }
                            writeArchiveStateLPr(resolveSerializer, readUserState.getArchiveState());
                            resolveSerializer.endTag((java.lang.String) null, TAG_PACKAGE);
                        }
                        writePreferredActivitiesLPr(resolveSerializer, i, true);
                        writePersistentPreferredActivitiesLPr(resolveSerializer, i);
                        writeCrossProfileIntentFiltersLPr(resolveSerializer, i);
                        writeDefaultAppsLPr(resolveSerializer, i);
                        writeBlockUninstallPackagesLPr(resolveSerializer, i);
                        resolveSerializer.endTag((java.lang.String) null, TAG_PACKAGE_RESTRICTIONS);
                        resolveSerializer.endDocument();
                    } catch (java.lang.Throwable th3) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th3;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                userPackagesStateFile.finishWrite(startWrite);
                com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("package-user-" + i, android.os.SystemClock.uptimeMillis() - j);
                userPackagesStateFile.close();
            } catch (java.io.IOException e3) {
                e = e3;
                fileOutputStream = startWrite;
                android.util.Slog.wtf("PackageManager", "Unable to write package manager package restrictions,  current changes will be lost at reboot", e);
                if (fileOutputStream != null) {
                    userPackagesStateFile.failWrite(fileOutputStream);
                }
                if (userPackagesStateFile != null) {
                    userPackagesStateFile.close();
                }
            }
        } catch (java.lang.Throwable th4) {
            if (userPackagesStateFile == null) {
                throw th4;
            }
            try {
                userPackagesStateFile.close();
                throw th4;
            } catch (java.lang.Throwable th5) {
                th4.addSuppressed(th5);
                throw th4;
            }
        }
    }

    private void writeArchiveStateLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.pm.pkg.ArchiveState archiveState) throws java.io.IOException {
        if (archiveState == null) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, TAG_ARCHIVE_STATE);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_ARCHIVE_INSTALLER_TITLE, archiveState.getInstallerTitle());
        typedXmlSerializer.attributeLongHex((java.lang.String) null, ATTR_ARCHIVE_TIME, archiveState.getArchiveTimeMillis());
        for (com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo archiveActivityInfo : archiveState.getActivityInfos()) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_ARCHIVE_ACTIVITY_INFO);
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_ARCHIVE_ACTIVITY_TITLE, archiveActivityInfo.getTitle());
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_ARCHIVE_ORIGINAL_COMPONENT_NAME, archiveActivityInfo.getOriginalComponentName().flattenToString());
            if (archiveActivityInfo.getIconBitmap() != null) {
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_ARCHIVE_ICON_PATH, archiveActivityInfo.getIconBitmap().toAbsolutePath().toString());
            }
            if (archiveActivityInfo.getMonochromeIconBitmap() != null) {
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_ARCHIVE_MONOCHROME_ICON_PATH, archiveActivityInfo.getMonochromeIconBitmap().toAbsolutePath().toString());
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_ARCHIVE_ACTIVITY_INFO);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_ARCHIVE_STATE);
    }

    void readInstallPermissionsLPr(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.pm.permission.LegacyPermissionState legacyPermissionState, java.util.List<android.content.pm.UserInfo> list) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(TAG_ITEM)) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_GRANTED, true);
                        int attributeIntHex = typedXmlPullParser.getAttributeIntHex((java.lang.String) null, ATTR_FLAGS, 0);
                        java.util.Iterator<android.content.pm.UserInfo> it = list.iterator();
                        while (it.hasNext()) {
                            legacyPermissionState.putPermissionState(new com.android.server.pm.permission.LegacyPermissionState.PermissionState(attributeValue, false, attributeBoolean, attributeIntHex), it.next().id);
                        }
                    } else {
                        android.util.Slog.w("PackageManager", "Unknown element under <permissions>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    void readUsesSdkLibLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.pm.PackageSetting packageSetting) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "version", -1L);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_OPTIONAL, true);
        if (attributeValue != null && attributeLong >= 0) {
            packageSetting.setUsesSdkLibraries((java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, packageSetting.getUsesSdkLibraries(), attributeValue));
            packageSetting.setUsesSdkLibrariesVersionsMajor(com.android.internal.util.ArrayUtils.appendLong(packageSetting.getUsesSdkLibrariesVersionsMajor(), attributeLong));
            packageSetting.setUsesSdkLibrariesOptional(com.android.internal.util.ArrayUtils.appendBoolean(packageSetting.getUsesSdkLibrariesOptional(), attributeBoolean));
        }
        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    void readUsesStaticLibLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.pm.PackageSetting packageSetting) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "version", -1L);
        if (attributeValue != null && attributeLong >= 0) {
            packageSetting.setUsesStaticLibraries((java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, packageSetting.getUsesStaticLibraries(), attributeValue));
            packageSetting.setUsesStaticLibrariesVersions(com.android.internal.util.ArrayUtils.appendLong(packageSetting.getUsesStaticLibrariesVersions(), attributeLong));
        }
        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    void writeUsesSdkLibLPw(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String[] strArr, long[] jArr, boolean[] zArr) throws java.io.IOException {
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr) || com.android.internal.util.ArrayUtils.isEmpty(jArr) || strArr.length != jArr.length) {
            return;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            java.lang.String str = strArr[i];
            long j = jArr[i];
            boolean z = zArr[i];
            typedXmlSerializer.startTag((java.lang.String) null, TAG_USES_SDK_LIB);
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            typedXmlSerializer.attributeLong((java.lang.String) null, "version", j);
            typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_OPTIONAL, z);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_USES_SDK_LIB);
        }
    }

    void writeUsesStaticLibLPw(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String[] strArr, long[] jArr) throws java.io.IOException {
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr) || com.android.internal.util.ArrayUtils.isEmpty(jArr) || strArr.length != jArr.length) {
            return;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            java.lang.String str = strArr[i];
            long j = jArr[i];
            typedXmlSerializer.startTag((java.lang.String) null, TAG_USES_STATIC_LIB);
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            typedXmlSerializer.attributeLong((java.lang.String) null, "version", j);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_USES_STATIC_LIB);
        }
    }

    void readStoppedLPw() {
        java.io.FileInputStream fileInputStream;
        int next;
        if (!this.mBackupStoppedPackagesFilename.exists()) {
            fileInputStream = null;
        } else {
            try {
                fileInputStream = new java.io.FileInputStream(this.mBackupStoppedPackagesFilename);
                try {
                    this.mReadMessages.append("Reading from backup stopped packages file\n");
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(4, "Need to read from backup stopped packages file");
                    if (this.mStoppedPackagesFilename.exists()) {
                        android.util.Slog.w("PackageManager", "Cleaning up stopped packages file " + this.mStoppedPackagesFilename);
                        this.mStoppedPackagesFilename.delete();
                    }
                } catch (java.io.IOException e) {
                }
            } catch (java.io.IOException e2) {
                fileInputStream = null;
            }
        }
        if (fileInputStream == null) {
            try {
                if (!this.mStoppedPackagesFilename.exists()) {
                    this.mReadMessages.append("No stopped packages file found\n");
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(4, "No stopped packages file file; assuming all started");
                    for (com.android.server.pm.PackageSetting packageSetting : this.mPackages.values()) {
                        packageSetting.setStopped(false, 0);
                        packageSetting.setNotLaunched(false, 0);
                    }
                    return;
                }
                fileInputStream = new java.io.FileInputStream(this.mStoppedPackagesFilename);
            } catch (java.io.IOException e3) {
                this.mReadMessages.append("Error reading: " + e3.toString());
                com.android.server.pm.PackageManagerService.reportSettingsProblem(6, "Error reading settings: " + e3);
                android.util.Slog.wtf("PackageManager", "Error reading package manager stopped packages", e3);
                return;
            } catch (org.xmlpull.v1.XmlPullParserException e4) {
                this.mReadMessages.append("Error reading: " + e4.toString());
                com.android.server.pm.PackageManagerService.reportSettingsProblem(6, "Error reading stopped packages: " + e4);
                android.util.Slog.wtf("PackageManager", "Error reading package manager stopped packages", e4);
                return;
            }
        }
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
        do {
            next = resolvePullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            this.mReadMessages.append("No start tag found in stopped packages file\n");
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager stopped packages");
            return;
        }
        int depth = resolvePullParser.getDepth();
        while (true) {
            int next2 = resolvePullParser.next();
            if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                break;
            }
            if (next2 != 3 && next2 != 4) {
                if (resolvePullParser.getName().equals(TAG_PACKAGE)) {
                    java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, "name");
                    com.android.server.pm.PackageSetting packageSetting2 = this.mPackages.get(attributeValue);
                    if (packageSetting2 != null) {
                        packageSetting2.setStopped(true, 0);
                        if ("1".equals(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_NOT_LAUNCHED))) {
                            packageSetting2.setNotLaunched(true, 0);
                        }
                    } else {
                        android.util.Slog.w("PackageManager", "No package known for stopped package " + attributeValue);
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                } else {
                    android.util.Slog.w("PackageManager", "Unknown element under <stopped-packages>: " + resolvePullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                }
            }
        }
        fileInputStream.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x01ff A[Catch: all -> 0x01de, TRY_LEAVE, TryCatch #9 {all -> 0x01de, blocks: (B:17:0x01f6, B:19:0x01ff, B:100:0x01bd), top: B:99:0x01bd }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x020a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void writeLPr(@android.annotation.NonNull com.android.server.pm.Computer computer, boolean z) {
        com.android.server.pm.ResilientAtomicFile resilientAtomicFile;
        java.io.FileOutputStream fileOutputStream;
        java.lang.Throwable th;
        java.io.FileOutputStream startWrite;
        long j;
        com.android.server.pm.ResilientAtomicFile resilientAtomicFile2;
        java.lang.Throwable th2;
        long j2;
        com.android.server.pm.Settings.VersionInfo valueAt;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        invalidatePackageCache();
        java.util.ArrayList<android.content.pm.Signature> arrayList = new java.util.ArrayList<>();
        com.android.server.pm.ResilientAtomicFile settingsFile = getSettingsFile();
        try {
            try {
                startWrite = settingsFile.startWrite();
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startTag((java.lang.String) null, "packages");
                    int i = 0;
                    while (i < this.mVersion.size()) {
                        try {
                            java.lang.String keyAt = this.mVersion.keyAt(i);
                            j2 = uptimeMillis;
                            valueAt = this.mVersion.valueAt(i);
                            resolveSerializer.startTag((java.lang.String) null, "version");
                            com.android.internal.util.XmlUtils.writeStringAttribute(resolveSerializer, ATTR_VOLUME_UUID, keyAt);
                            resilientAtomicFile2 = settingsFile;
                        } catch (java.io.IOException e) {
                            e = e;
                            resilientAtomicFile2 = settingsFile;
                            fileOutputStream = startWrite;
                            resilientAtomicFile = resilientAtomicFile2;
                            android.util.Slog.wtf("PackageManager", "Unable to write package manager settings, current changes will be lost at reboot", e);
                            if (fileOutputStream != null) {
                            }
                            if (resilientAtomicFile != null) {
                            }
                        } catch (java.lang.Throwable th3) {
                            th2 = th3;
                            resilientAtomicFile2 = settingsFile;
                            th = th2;
                            resilientAtomicFile = resilientAtomicFile2;
                            if (resilientAtomicFile != null) {
                            }
                        }
                        try {
                            resolveSerializer.attributeInt((java.lang.String) null, ATTR_SDK_VERSION, valueAt.sdkVersion);
                            resolveSerializer.attributeInt((java.lang.String) null, ATTR_DATABASE_VERSION, valueAt.databaseVersion);
                            com.android.internal.util.XmlUtils.writeStringAttribute(resolveSerializer, ATTR_BUILD_FINGERPRINT, valueAt.buildFingerprint);
                            com.android.internal.util.XmlUtils.writeStringAttribute(resolveSerializer, ATTR_FINGERPRINT, valueAt.fingerprint);
                            resolveSerializer.endTag((java.lang.String) null, "version");
                            i++;
                            uptimeMillis = j2;
                            settingsFile = resilientAtomicFile2;
                        } catch (java.io.IOException e2) {
                            e = e2;
                            fileOutputStream = startWrite;
                            resilientAtomicFile = resilientAtomicFile2;
                            android.util.Slog.wtf("PackageManager", "Unable to write package manager settings, current changes will be lost at reboot", e);
                            if (fileOutputStream != null) {
                            }
                            if (resilientAtomicFile != null) {
                            }
                        } catch (java.lang.Throwable th4) {
                            th2 = th4;
                            th = th2;
                            resilientAtomicFile = resilientAtomicFile2;
                            if (resilientAtomicFile != null) {
                            }
                        }
                    }
                    j = uptimeMillis;
                    resilientAtomicFile2 = settingsFile;
                    try {
                        if (this.mVerifierDeviceIdentity != null) {
                            resolveSerializer.startTag((java.lang.String) null, "verifier");
                            resolveSerializer.attribute((java.lang.String) null, "device", this.mVerifierDeviceIdentity.toString());
                            resolveSerializer.endTag((java.lang.String) null, "verifier");
                        }
                        resolveSerializer.startTag((java.lang.String) null, "permission-trees");
                        this.mPermissions.writePermissionTrees(resolveSerializer);
                        resolveSerializer.endTag((java.lang.String) null, "permission-trees");
                        resolveSerializer.startTag((java.lang.String) null, "permissions");
                        this.mPermissions.writePermissions(resolveSerializer);
                        resolveSerializer.endTag((java.lang.String) null, "permissions");
                        for (com.android.server.pm.PackageSetting packageSetting : this.mPackages.values()) {
                            if (packageSetting.getPkg() == null || !packageSetting.getPkg().isApex()) {
                                writePackageLPr(resolveSerializer, arrayList, packageSetting);
                            }
                        }
                        for (com.android.server.pm.PackageSetting packageSetting2 : this.mDisabledSysPackages.values()) {
                            if (packageSetting2.getPkg() == null || !packageSetting2.getPkg().isApex()) {
                                writeDisabledSysPackageLPr(resolveSerializer, packageSetting2);
                            }
                        }
                        for (com.android.server.pm.SharedUserSetting sharedUserSetting : this.mSharedUsers.values()) {
                            resolveSerializer.startTag((java.lang.String) null, TAG_SHARED_USER);
                            resolveSerializer.attribute((java.lang.String) null, "name", sharedUserSetting.name);
                            resolveSerializer.attributeInt((java.lang.String) null, "userId", sharedUserSetting.mAppId);
                            sharedUserSetting.signatures.writeXml(resolveSerializer, "sigs", arrayList);
                            resolveSerializer.endTag((java.lang.String) null, TAG_SHARED_USER);
                        }
                        if (this.mRenamedPackages.size() > 0) {
                            for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : this.mRenamedPackages.entrySet()) {
                                resolveSerializer.startTag((java.lang.String) null, "renamed-package");
                                resolveSerializer.attribute((java.lang.String) null, "new", entry.getKey());
                                resolveSerializer.attribute((java.lang.String) null, "old", entry.getValue());
                                resolveSerializer.endTag((java.lang.String) null, "renamed-package");
                            }
                        }
                        this.mDomainVerificationManager.writeSettings(computer, resolveSerializer, false, -1);
                        this.mKeySetManagerService.writeKeySetManagerServiceLPr(resolveSerializer);
                        resolveSerializer.endTag((java.lang.String) null, "packages");
                        resolveSerializer.endDocument();
                        resilientAtomicFile = resilientAtomicFile2;
                    } catch (java.io.IOException e3) {
                        e = e3;
                        resilientAtomicFile = resilientAtomicFile2;
                    } catch (java.lang.Throwable th5) {
                        th = th5;
                        resilientAtomicFile = resilientAtomicFile2;
                        th = th;
                        if (resilientAtomicFile != null) {
                        }
                    }
                } catch (java.io.IOException e4) {
                    e = e4;
                    resilientAtomicFile = settingsFile;
                }
            } catch (java.lang.Throwable th6) {
                th = th6;
                resilientAtomicFile = settingsFile;
            }
        } catch (java.io.IOException e5) {
            e = e5;
            resilientAtomicFile = settingsFile;
            fileOutputStream = null;
        }
        try {
            try {
                resilientAtomicFile.finishWrite(startWrite);
                writeKernelMappingLPr();
                writePackageListLPr();
                writeAllUsersPackageRestrictionsLPr(z);
                writeAllRuntimePermissionsLPr();
                com.android.internal.logging.EventLogTags.writeCommitSysConfigFile(ATTR_PACKAGE, android.os.SystemClock.uptimeMillis() - j);
                resilientAtomicFile.close();
            } catch (java.lang.Throwable th7) {
                th = th7;
                th = th;
                if (resilientAtomicFile != null) {
                    throw th;
                }
                try {
                    resilientAtomicFile.close();
                    throw th;
                } catch (java.lang.Throwable th8) {
                    th.addSuppressed(th8);
                    throw th;
                }
            }
        } catch (java.io.IOException e6) {
            e = e6;
            fileOutputStream = startWrite;
            android.util.Slog.wtf("PackageManager", "Unable to write package manager settings, current changes will be lost at reboot", e);
            if (fileOutputStream != null) {
                resilientAtomicFile.failWrite(fileOutputStream);
            }
            if (resilientAtomicFile != null) {
                resilientAtomicFile.close();
            }
        }
    }

    private void writeKernelRemoveUserLPr(int i) {
        if (this.mKernelMappingFilename == null) {
            return;
        }
        writeIntToFile(new java.io.File(this.mKernelMappingFilename, "remove_userid"), i);
    }

    void writeKernelMappingLPr() {
        if (this.mKernelMappingFilename == null) {
            return;
        }
        java.lang.String[] list = this.mKernelMappingFilename.list();
        android.util.ArraySet arraySet = new android.util.ArraySet(list.length);
        for (java.lang.String str : list) {
            arraySet.add(str);
        }
        for (com.android.server.pm.PackageSetting packageSetting : this.mPackages.values()) {
            arraySet.remove(packageSetting.getPackageName());
            writeKernelMappingLPr(packageSetting);
        }
        for (int i = 0; i < arraySet.size(); i++) {
            java.lang.String str2 = (java.lang.String) arraySet.valueAt(i);
            this.mKernelMapping.remove(str2);
            new java.io.File(this.mKernelMappingFilename, str2).delete();
        }
    }

    void writeKernelMappingLPr(com.android.server.pm.PackageSetting packageSetting) {
        if (this.mKernelMappingFilename == null || packageSetting == null || packageSetting.getPackageName() == null) {
            return;
        }
        writeKernelMappingLPr(packageSetting.getPackageName(), packageSetting.getAppId(), packageSetting.getNotInstalledUserIds());
    }

    void writeKernelMappingLPr(java.lang.String str, int i, int[] iArr) {
        com.android.server.pm.Settings.KernelPackageState kernelPackageState = this.mKernelMapping.get(str);
        boolean z = true;
        boolean z2 = kernelPackageState == null;
        if (!z2 && java.util.Arrays.equals(iArr, kernelPackageState.excludedUserIds)) {
            z = false;
        }
        java.io.File file = new java.io.File(this.mKernelMappingFilename, str);
        if (z2) {
            file.mkdir();
            kernelPackageState = new com.android.server.pm.Settings.KernelPackageState();
            this.mKernelMapping.put(str, kernelPackageState);
        }
        if (kernelPackageState.appId != i) {
            writeIntToFile(new java.io.File(file, "appid"), i);
        }
        if (z) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (kernelPackageState.excludedUserIds == null || !com.android.internal.util.ArrayUtils.contains(kernelPackageState.excludedUserIds, iArr[i2])) {
                    writeIntToFile(new java.io.File(file, "excluded_userids"), iArr[i2]);
                }
            }
            if (kernelPackageState.excludedUserIds != null) {
                for (int i3 = 0; i3 < kernelPackageState.excludedUserIds.length; i3++) {
                    if (!com.android.internal.util.ArrayUtils.contains(iArr, kernelPackageState.excludedUserIds[i3])) {
                        writeIntToFile(new java.io.File(file, "clear_userid"), kernelPackageState.excludedUserIds[i3]);
                    }
                }
            }
            kernelPackageState.excludedUserIds = iArr;
        }
    }

    private void writeIntToFile(java.io.File file, int i) {
        try {
            android.os.FileUtils.bytesToFile(file.getAbsolutePath(), java.lang.Integer.toString(i).getBytes(java.nio.charset.StandardCharsets.US_ASCII));
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Couldn't write " + i + " to " + file.getAbsolutePath());
        }
    }

    void writePackageListLPr() {
        writePackageListLPr(-1);
    }

    void writePackageListLPr(int i) {
        java.lang.String fileSelabelLookup = android.os.SELinux.fileSelabelLookup(this.mPackageListFilename.getAbsolutePath());
        if (fileSelabelLookup == null) {
            android.util.Slog.wtf(TAG, "Failed to get SELinux context for " + this.mPackageListFilename.getAbsolutePath());
        }
        if (!android.os.SELinux.setFSCreateContext(fileSelabelLookup)) {
            android.util.Slog.wtf(TAG, "Failed to set packages.list SELinux context");
        }
        try {
            writePackageListLPrInternal(i);
        } finally {
            android.os.SELinux.setFSCreateContext((java.lang.String) null);
        }
    }

    private void writePackageListLPrInternal(int i) {
        java.io.FileOutputStream fileOutputStream;
        java.io.BufferedWriter bufferedWriter;
        int i2;
        com.android.server.pm.Settings settings = this;
        java.util.List<android.content.pm.UserInfo> activeUsers = getActiveUsers(com.android.server.pm.UserManagerService.getInstance(), true);
        int size = activeUsers.size();
        int[] iArr = new int[size];
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            iArr[i4] = activeUsers.get(i4).id;
        }
        if (i != -1) {
            iArr = com.android.internal.util.ArrayUtils.appendInt(iArr, i);
        }
        com.android.internal.util.JournaledFile journaledFile = new com.android.internal.util.JournaledFile(settings.mPackageListFilename, new java.io.File(settings.mPackageListFilename.getAbsolutePath() + ".tmp"));
        java.io.BufferedWriter bufferedWriter2 = null;
        try {
            fileOutputStream = new java.io.FileOutputStream(journaledFile.chooseForWrite());
            bufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(fileOutputStream, java.nio.charset.Charset.defaultCharset()));
        } catch (java.lang.Exception e) {
            e = e;
        }
        try {
            android.os.FileUtils.setPermissions(fileOutputStream.getFD(), com.android.internal.util.FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1032);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (com.android.server.pm.PackageSetting packageSetting : settings.mPackages.values()) {
                if (packageSetting.getPkg() == null) {
                    if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageSetting.getPackageName())) {
                        android.util.Slog.w(TAG, "Skipping " + packageSetting + " due to missing metadata");
                    }
                } else if (!packageSetting.getPkg().isApex()) {
                    java.io.File dataDir = com.android.server.pm.parsing.PackageInfoUtils.getDataDir(packageSetting, i3);
                    java.lang.String absolutePath = dataDir == null ? "null" : dataDir.getAbsolutePath();
                    boolean isDebuggable = packageSetting.getPkg().isDebuggable();
                    android.util.IntArray intArray = new android.util.IntArray();
                    int length = iArr.length;
                    int i5 = i3;
                    while (i5 < length) {
                        intArray.addAll(settings.mPermissionDataProvider.getGidsForUid(android.os.UserHandle.getUid(iArr[i5], packageSetting.getAppId())));
                        i5++;
                        settings = this;
                        iArr = iArr;
                    }
                    int[] iArr2 = iArr;
                    if (absolutePath.indexOf(32) >= 0) {
                        settings = this;
                        iArr = iArr2;
                        i3 = 0;
                    } else {
                        sb.setLength(0);
                        sb.append(packageSetting.getPkg().getPackageName());
                        sb.append(" ");
                        sb.append(packageSetting.getPkg().getUid());
                        sb.append(isDebuggable ? " 1 " : " 0 ");
                        sb.append(absolutePath);
                        sb.append(" ");
                        sb.append(packageSetting.getSeInfo());
                        sb.append(" ");
                        int size2 = intArray.size();
                        if (intArray.size() > 0) {
                            i2 = 0;
                            sb.append(intArray.get(0));
                            for (int i6 = 1; i6 < size2; i6++) {
                                sb.append(",");
                                sb.append(intArray.get(i6));
                            }
                        } else {
                            i2 = 0;
                            sb.append("none");
                        }
                        sb.append(" ");
                        sb.append(packageSetting.getPkg().isProfileableByShell() ? "1" : "0");
                        sb.append(" ");
                        sb.append(packageSetting.getPkg().getLongVersionCode());
                        sb.append(" ");
                        sb.append(packageSetting.getPkg().isProfileable() ? "1" : "0");
                        sb.append(" ");
                        if (packageSetting.isSystem()) {
                            sb.append("@system");
                        } else if (packageSetting.isProduct()) {
                            sb.append("@product");
                        } else if (packageSetting.getInstallSource().mInstallerPackageName != null && !packageSetting.getInstallSource().mInstallerPackageName.isEmpty()) {
                            sb.append(packageSetting.getInstallSource().mInstallerPackageName);
                        } else {
                            sb.append("@null");
                        }
                        sb.append("\n");
                        bufferedWriter.append((java.lang.CharSequence) sb);
                        settings = this;
                        iArr = iArr2;
                        i3 = i2;
                    }
                }
            }
            bufferedWriter.flush();
            android.os.FileUtils.sync(fileOutputStream);
            bufferedWriter.close();
            journaledFile.commit();
        } catch (java.lang.Exception e2) {
            e = e2;
            bufferedWriter2 = bufferedWriter;
            android.util.Slog.wtf(TAG, "Failed to write packages.list", e);
            libcore.io.IoUtils.closeQuietly(bufferedWriter2);
            journaledFile.rollback();
        }
    }

    void writeDisabledSysPackageLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.pm.PackageSetting packageSetting) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "updated-package");
        typedXmlSerializer.attribute((java.lang.String) null, "name", packageSetting.getPackageName());
        if (packageSetting.getRealName() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "realName", packageSetting.getRealName());
        }
        typedXmlSerializer.attribute((java.lang.String) null, "codePath", packageSetting.getPathString());
        typedXmlSerializer.attributeLongHex((java.lang.String) null, "ft", packageSetting.getLastModifiedTime());
        typedXmlSerializer.attributeLongHex((java.lang.String) null, "ut", packageSetting.getLastUpdateTime());
        typedXmlSerializer.attributeLong((java.lang.String) null, "version", packageSetting.getVersionCode());
        typedXmlSerializer.attributeInt((java.lang.String) null, "targetSdkVersion", packageSetting.getTargetSdkVersion());
        if (packageSetting.getRestrictUpdateHash() != null) {
            typedXmlSerializer.attributeBytesBase64((java.lang.String) null, "restrictUpdateHash", packageSetting.getRestrictUpdateHash());
        }
        typedXmlSerializer.attributeBoolean((java.lang.String) null, "scannedAsStoppedSystemApp", packageSetting.isScannedAsStoppedSystemApp());
        if (packageSetting.getLegacyNativeLibraryPath() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "nativeLibraryPath", packageSetting.getLegacyNativeLibraryPath());
        }
        if (packageSetting.getPrimaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "primaryCpuAbi", packageSetting.getPrimaryCpuAbiLegacy());
        }
        if (packageSetting.getSecondaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "secondaryCpuAbi", packageSetting.getSecondaryCpuAbiLegacy());
        }
        if (packageSetting.getCpuAbiOverride() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "cpuAbiOverride", packageSetting.getCpuAbiOverride());
        }
        if (!packageSetting.hasSharedUser()) {
            typedXmlSerializer.attributeInt((java.lang.String) null, "userId", packageSetting.getAppId());
        } else {
            typedXmlSerializer.attributeInt((java.lang.String) null, "sharedUserId", packageSetting.getAppId());
        }
        typedXmlSerializer.attributeFloat((java.lang.String) null, "loadingProgress", packageSetting.getLoadingProgress());
        typedXmlSerializer.attributeLongHex((java.lang.String) null, "loadingCompletedTime", packageSetting.getLoadingCompletedTime());
        if (packageSetting.getAppMetadataFilePath() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "appMetadataFilePath", packageSetting.getAppMetadataFilePath());
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "appMetadataSource", packageSetting.getAppMetadataSource());
        writeUsesSdkLibLPw(typedXmlSerializer, packageSetting.getUsesSdkLibraries(), packageSetting.getUsesSdkLibrariesVersionsMajor(), packageSetting.getUsesSdkLibrariesOptional());
        writeUsesStaticLibLPw(typedXmlSerializer, packageSetting.getUsesStaticLibraries(), packageSetting.getUsesStaticLibrariesVersions());
        typedXmlSerializer.endTag((java.lang.String) null, "updated-package");
    }

    void writePackageLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.util.ArrayList<android.content.pm.Signature> arrayList, com.android.server.pm.PackageSetting packageSetting) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, ATTR_PACKAGE);
        typedXmlSerializer.attribute((java.lang.String) null, "name", packageSetting.getPackageName());
        if (packageSetting.getRealName() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "realName", packageSetting.getRealName());
        }
        typedXmlSerializer.attribute((java.lang.String) null, "codePath", packageSetting.getPathString());
        if (packageSetting.getLegacyNativeLibraryPath() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "nativeLibraryPath", packageSetting.getLegacyNativeLibraryPath());
        }
        if (packageSetting.getPrimaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "primaryCpuAbi", packageSetting.getPrimaryCpuAbiLegacy());
        }
        if (packageSetting.getSecondaryCpuAbiLegacy() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "secondaryCpuAbi", packageSetting.getSecondaryCpuAbiLegacy());
        }
        if (packageSetting.getCpuAbiOverride() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "cpuAbiOverride", packageSetting.getCpuAbiOverride());
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "publicFlags", packageSetting.getFlags());
        typedXmlSerializer.attributeInt((java.lang.String) null, "privateFlags", packageSetting.getPrivateFlags());
        typedXmlSerializer.attributeLongHex((java.lang.String) null, "ft", packageSetting.getLastModifiedTime());
        typedXmlSerializer.attributeLongHex((java.lang.String) null, "ut", packageSetting.getLastUpdateTime());
        typedXmlSerializer.attributeLong((java.lang.String) null, "version", packageSetting.getVersionCode());
        typedXmlSerializer.attributeInt((java.lang.String) null, "targetSdkVersion", packageSetting.getTargetSdkVersion());
        if (packageSetting.getRestrictUpdateHash() != null) {
            typedXmlSerializer.attributeBytesBase64((java.lang.String) null, "restrictUpdateHash", packageSetting.getRestrictUpdateHash());
        }
        typedXmlSerializer.attributeBoolean((java.lang.String) null, "scannedAsStoppedSystemApp", packageSetting.isScannedAsStoppedSystemApp());
        if (!packageSetting.hasSharedUser()) {
            typedXmlSerializer.attributeInt((java.lang.String) null, "userId", packageSetting.getAppId());
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "isSdkLibrary", packageSetting.getAndroidPackage() != null && packageSetting.getAndroidPackage().isSdkLibrary());
        } else {
            typedXmlSerializer.attributeInt((java.lang.String) null, "sharedUserId", packageSetting.getAppId());
        }
        com.android.server.pm.InstallSource installSource = packageSetting.getInstallSource();
        if (installSource.mInstallerPackageName != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "installer", installSource.mInstallerPackageName);
        }
        if (installSource.mInstallerPackageUid != -1) {
            typedXmlSerializer.attributeInt((java.lang.String) null, "installerUid", installSource.mInstallerPackageUid);
        }
        if (installSource.mUpdateOwnerPackageName != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "updateOwner", installSource.mUpdateOwnerPackageName);
        }
        if (installSource.mInstallerAttributionTag != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "installerAttributionTag", installSource.mInstallerAttributionTag);
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "packageSource", installSource.mPackageSource);
        if (installSource.mIsOrphaned) {
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "isOrphaned", true);
        }
        if (installSource.mInitiatingPackageName != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "installInitiator", installSource.mInitiatingPackageName);
        }
        if (installSource.mIsInitiatingPackageUninstalled) {
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "installInitiatorUninstalled", true);
        }
        if (installSource.mOriginatingPackageName != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "installOriginator", installSource.mOriginatingPackageName);
        }
        if (packageSetting.getVolumeUuid() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_VOLUME_UUID, packageSetting.getVolumeUuid());
        }
        if (packageSetting.getCategoryOverride() != -1) {
            typedXmlSerializer.attributeInt((java.lang.String) null, "categoryHint", packageSetting.getCategoryOverride());
        }
        if (packageSetting.isUpdateAvailable()) {
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "updateAvailable", true);
        }
        if (packageSetting.isForceQueryableOverride()) {
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "forceQueryable", true);
        }
        if (packageSetting.isLoading()) {
            typedXmlSerializer.attributeBoolean((java.lang.String) null, "isLoading", true);
        }
        typedXmlSerializer.attributeFloat((java.lang.String) null, "loadingProgress", packageSetting.getLoadingProgress());
        typedXmlSerializer.attributeLongHex((java.lang.String) null, "loadingCompletedTime", packageSetting.getLoadingCompletedTime());
        typedXmlSerializer.attribute((java.lang.String) null, "domainSetId", packageSetting.getDomainSetId().toString());
        if (packageSetting.getAppMetadataFilePath() != null) {
            typedXmlSerializer.attribute((java.lang.String) null, "appMetadataFilePath", packageSetting.getAppMetadataFilePath());
        }
        typedXmlSerializer.attributeInt((java.lang.String) null, "appMetadataSource", packageSetting.getAppMetadataSource());
        writeUsesSdkLibLPw(typedXmlSerializer, packageSetting.getUsesSdkLibraries(), packageSetting.getUsesSdkLibrariesVersionsMajor(), packageSetting.getUsesSdkLibrariesOptional());
        writeUsesStaticLibLPw(typedXmlSerializer, packageSetting.getUsesStaticLibraries(), packageSetting.getUsesStaticLibrariesVersions());
        packageSetting.getSignatures().writeXml(typedXmlSerializer, "sigs", arrayList);
        if (installSource.mInitiatingPackageSignatures != null) {
            installSource.mInitiatingPackageSignatures.writeXml(typedXmlSerializer, "install-initiator-sigs", arrayList);
        }
        writeSigningKeySetLPr(typedXmlSerializer, packageSetting.getKeySetData());
        writeUpgradeKeySetsLPr(typedXmlSerializer, packageSetting.getKeySetData());
        writeKeySetAliasesLPr(typedXmlSerializer, packageSetting.getKeySetData());
        writeMimeGroupLPr(typedXmlSerializer, packageSetting.getMimeGroups());
        typedXmlSerializer.endTag((java.lang.String) null, ATTR_PACKAGE);
    }

    void writeSigningKeySetLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.pm.PackageKeySetData packageKeySetData) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "proper-signing-keyset");
        typedXmlSerializer.attributeLong((java.lang.String) null, "identifier", packageKeySetData.getProperSigningKeySet());
        typedXmlSerializer.endTag((java.lang.String) null, "proper-signing-keyset");
    }

    void writeUpgradeKeySetsLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.pm.PackageKeySetData packageKeySetData) throws java.io.IOException {
        if (packageKeySetData.isUsingUpgradeKeySets()) {
            for (long j : packageKeySetData.getUpgradeKeySets()) {
                typedXmlSerializer.startTag((java.lang.String) null, "upgrade-keyset");
                typedXmlSerializer.attributeLong((java.lang.String) null, "identifier", j);
                typedXmlSerializer.endTag((java.lang.String) null, "upgrade-keyset");
            }
        }
    }

    void writeKeySetAliasesLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.pm.PackageKeySetData packageKeySetData) throws java.io.IOException {
        for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : packageKeySetData.getAliases().entrySet()) {
            typedXmlSerializer.startTag((java.lang.String) null, "defined-keyset");
            typedXmlSerializer.attribute((java.lang.String) null, "alias", entry.getKey());
            typedXmlSerializer.attributeLong((java.lang.String) null, "identifier", entry.getValue().longValue());
            typedXmlSerializer.endTag((java.lang.String) null, "defined-keyset");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0206, code lost:
    
        r18.mVerifierDeviceIdentity = android.content.pm.VerifierDeviceIdentity.parse(r12.getAttributeValue((java.lang.String) null, "device"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x020c, code lost:
    
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x020f, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0212, code lost:
    
        r2 = r17;
        android.util.Slog.w(r2, "Discard invalid verifier device id: " + r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0231, code lost:
    
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x023a, code lost:
    
        if (com.android.server.pm.Settings.TAG_READ_EXTERNAL_STORAGE.equals(r1) == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0243, code lost:
    
        if (r1.equals("keyset-settings") == false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0245, code lost:
    
        r18.mKeySetManagerService.readKeySetsLPw(r12, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0256, code lost:
    
        if ("version".equals(r1) == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0258, code lost:
    
        r1 = findOrCreateVersion(com.android.internal.util.XmlUtils.readStringAttribute(r12, com.android.server.pm.Settings.ATTR_VOLUME_UUID));
        r1.sdkVersion = r12.getAttributeInt((java.lang.String) null, com.android.server.pm.Settings.ATTR_SDK_VERSION);
        r1.databaseVersion = r12.getAttributeInt((java.lang.String) null, com.android.server.pm.Settings.ATTR_DATABASE_VERSION);
        r1.buildFingerprint = com.android.internal.util.XmlUtils.readStringAttribute(r12, com.android.server.pm.Settings.ATTR_BUILD_FINGERPRINT);
        r1.fingerprint = com.android.internal.util.XmlUtils.readStringAttribute(r12, com.android.server.pm.Settings.ATTR_FINGERPRINT);
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x028b, code lost:
    
        if (r1.equals(com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_DOMAIN_VERIFICATIONS) == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0291, code lost:
    
        r18.mDomainVerificationManager.readSettings(r19, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x02a0, code lost:
    
        if (r1.equals(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.TAG_DOMAIN_VERIFICATIONS_LEGACY) == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x02a2, code lost:
    
        r18.mDomainVerificationManager.readLegacySettings(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x02a8, code lost:
    
        android.util.Slog.w(r2, "Unknown element under <packages>: " + r12.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0295, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0049, code lost:
    
        r1 = r0;
        r13 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x02e9, code lost:
    
        r11.failRead(r13, r1);
        readSettingsLPw(r19, r20, r21);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008d, code lost:
    
        if (r1 != 4) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0091, code lost:
    
        r1 = r5.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x009c, code lost:
    
        if (r1.equals(com.android.server.pm.Settings.ATTR_PACKAGE) == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x009e, code lost:
    
        r16 = r3;
        r12 = r5;
        r13 = false;
        readPackageLPw(r5, r10, r9, r20, r21);
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b8, code lost:
    
        r16 = r3;
        r17 = r4;
        r12 = r5;
        r13 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c5, code lost:
    
        if (r1.equals("permissions") == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00c7, code lost:
    
        r18.mPermissions.readPermissions(r12);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00da, code lost:
    
        if (r1.equals("permission-trees") == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00dc, code lost:
    
        r18.mPermissions.readPermissionTrees(r12);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00ef, code lost:
    
        if (r1.equals(com.android.server.pm.Settings.TAG_SHARED_USER) == false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00f1, code lost:
    
        readSharedUserLPw(r12, r10, r8);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0102, code lost:
    
        if (r1.equals("preferred-packages") == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0104, code lost:
    
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0112, code lost:
    
        if (r1.equals("preferred-activities") == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0114, code lost:
    
        readPreferredActivitiesLPw(r12, 0);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0125, code lost:
    
        if (r1.equals(com.android.server.pm.Settings.TAG_PERSISTENT_PREFERRED_ACTIVITIES) == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0127, code lost:
    
        readPersistentPreferredActivitiesLPw(r12, 0);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0137, code lost:
    
        if (r1.equals(com.android.server.pm.Settings.TAG_CROSS_PROFILE_INTENT_FILTERS) == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0139, code lost:
    
        readCrossProfileIntentFiltersLPw(r12, 0);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0149, code lost:
    
        if (r1.equals(com.android.server.pm.Settings.TAG_DEFAULT_BROWSER) == false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x014b, code lost:
    
        readDefaultAppsLPw(r12, 0);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x015c, code lost:
    
        if (r1.equals("updated-package") == false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x015e, code lost:
    
        readDisabledSysPackageLPw(r12, r8);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x016f, code lost:
    
        if (r1.equals("renamed-package") == false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0171, code lost:
    
        r1 = r12.getAttributeValue((java.lang.String) null, "new");
        r3 = r12.getAttributeValue((java.lang.String) null, "old");
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0180, code lost:
    
        if (r1 == null) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0182, code lost:
    
        if (r3 == null) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0184, code lost:
    
        r18.mRenamedPackages.put(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0189, code lost:
    
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x019e, code lost:
    
        if (r1.equals("last-platform-version") == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01a0, code lost:
    
        r1 = findOrCreateVersion(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        r2 = findOrCreateVersion("primary_physical");
        r1.sdkVersion = r12.getAttributeInt((java.lang.String) null, "internal", 0);
        r2.sdkVersion = r12.getAttributeInt((java.lang.String) null, "external", 0);
        r4 = com.android.internal.util.XmlUtils.readStringAttribute(r12, com.android.server.pm.Settings.ATTR_BUILD_FINGERPRINT);
        r2.buildFingerprint = r4;
        r1.buildFingerprint = r4;
        r3 = com.android.internal.util.XmlUtils.readStringAttribute(r12, com.android.server.pm.Settings.ATTR_FINGERPRINT);
        r2.fingerprint = r3;
        r1.fingerprint = r3;
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01d6, code lost:
    
        if (r1.equals("database-version") == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01d8, code lost:
    
        r1 = findOrCreateVersion(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL);
        r2 = findOrCreateVersion("primary_physical");
        r1.databaseVersion = r12.getAttributeInt((java.lang.String) null, "internal", 0);
        r2.databaseVersion = r12.getAttributeInt((java.lang.String) null, "external", 0);
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01fd, code lost:
    
        if (r1.equals("verifier") == false) goto L91;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean readSettingsLPw(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.util.List<android.content.pm.UserInfo> list, android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap) {
        java.io.FileInputStream openRead;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        int next;
        java.lang.String str;
        int depth;
        int i;
        com.android.modules.utils.TypedXmlPullParser typedXmlPullParser;
        java.lang.String str2;
        java.util.List<android.content.pm.UserInfo> list2 = list;
        this.mPendingPackages.clear();
        this.mInstallerPackages.clear();
        arrayMap.clear();
        android.util.ArrayMap<java.lang.Long, java.lang.Integer> arrayMap2 = new android.util.ArrayMap<>();
        java.util.ArrayList<android.content.pm.Signature> arrayList = new java.util.ArrayList<>();
        com.android.server.pm.ResilientAtomicFile settingsFile = getSettingsFile();
        int i2 = 1;
        try {
            try {
                openRead = settingsFile.openRead();
                try {
                } catch (java.io.IOException | java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e) {
                    e = e;
                }
            } catch (java.io.IOException | java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e2) {
                java.lang.Exception exc = e2;
                java.io.FileInputStream fileInputStream = null;
            }
            if (openRead == null) {
                findOrCreateVersion(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL).forceCurrent();
                findOrCreateVersion("primary_physical").forceCurrent();
                settingsFile.close();
                return false;
            }
            resolvePullParser = android.util.Xml.resolvePullParser(openRead);
            do {
                next = resolvePullParser.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            str = "PackageManager";
            if (next != 2) {
                this.mReadMessages.append("No start tag found in settings file\n");
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "No start tag found in package manager settings");
                android.util.Slog.wtf("PackageManager", "No start tag found in package manager settings");
                settingsFile.close();
                return false;
            }
            depth = resolvePullParser.getDepth();
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 == i2) {
                    break;
                }
                if (next2 == 3 && resolvePullParser.getDepth() <= depth) {
                    break;
                }
                list2 = list;
                depth = depth;
                i2 = 1;
            }
            openRead.close();
            if (settingsFile == null) {
                settingsFile.close();
                return true;
            }
            return true;
            list2 = list;
            str = str2;
            resolvePullParser = typedXmlPullParser;
            depth = i;
            i2 = 1;
        } catch (java.lang.Throwable th) {
            if (settingsFile == null) {
                throw th;
            }
            try {
                settingsFile.close();
                throw th;
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    boolean readLPw(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.util.List<android.content.pm.UserInfo> list) {
        android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap = new android.util.ArrayMap<>();
        try {
            if (!readSettingsLPw(computer, list, arrayMap)) {
                return false;
            }
            if (!this.mVersion.containsKey(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL)) {
                android.util.Slog.wtf("PackageManager", "No internal VersionInfo found in settings, using current.");
                findOrCreateVersion(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL).forceCurrent();
            }
            if (!this.mVersion.containsKey("primary_physical")) {
                android.util.Slog.wtf("PackageManager", "No external VersionInfo found in settings, using current.");
                findOrCreateVersion("primary_physical").forceCurrent();
            }
            int size = this.mPendingPackages.size();
            for (int i = 0; i < size; i++) {
                com.android.server.pm.PackageSetting packageSetting = this.mPendingPackages.get(i);
                int sharedUserAppId = packageSetting.getSharedUserAppId();
                if (sharedUserAppId > 0) {
                    com.android.server.pm.SettingBase settingLPr = getSettingLPr(sharedUserAppId);
                    if (settingLPr instanceof com.android.server.pm.SharedUserSetting) {
                        addPackageSettingLPw(packageSetting, (com.android.server.pm.SharedUserSetting) settingLPr);
                    } else if (settingLPr != null) {
                        java.lang.String str = "Bad package setting: package " + packageSetting.getPackageName() + " has shared uid " + sharedUserAppId + " that is not a shared uid\n";
                        this.mReadMessages.append(str);
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(6, str);
                    } else {
                        java.lang.String str2 = "Bad package setting: package " + packageSetting.getPackageName() + " has shared uid " + sharedUserAppId + " that is not defined\n";
                        this.mReadMessages.append(str2);
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(6, str2);
                    }
                }
            }
            this.mPendingPackages.clear();
            if (this.mBackupStoppedPackagesFilename.exists() || this.mStoppedPackagesFilename.exists()) {
                readStoppedLPw();
                this.mBackupStoppedPackagesFilename.delete();
                this.mStoppedPackagesFilename.delete();
                writePackageRestrictionsLPr(0, true);
            } else {
                java.util.Iterator<android.content.pm.UserInfo> it = list.iterator();
                while (it.hasNext()) {
                    readPackageRestrictionsLPr(it.next().id, arrayMap);
                }
            }
            for (android.content.pm.UserInfo userInfo : list) {
                this.mRuntimePermissionsPersistence.readStateForUserSync(userInfo.id, getInternalVersion(), this.mPackages, this.mSharedUsers, getUserRuntimePermissionsFile(userInfo.id));
            }
            for (com.android.server.pm.PackageSetting packageSetting2 : this.mDisabledSysPackages.values()) {
                com.android.server.pm.SettingBase settingLPr2 = getSettingLPr(packageSetting2.getAppId());
                if (settingLPr2 instanceof com.android.server.pm.SharedUserSetting) {
                    com.android.server.pm.SharedUserSetting sharedUserSetting = (com.android.server.pm.SharedUserSetting) settingLPr2;
                    sharedUserSetting.mDisabledPackages.add(packageSetting2);
                    packageSetting2.setSharedUserAppId(sharedUserSetting.mAppId);
                }
            }
            java.lang.StringBuilder sb = this.mReadMessages;
            sb.append("Read completed successfully: ");
            sb.append(this.mPackages.size());
            sb.append(" packages, ");
            sb.append(this.mSharedUsers.size());
            sb.append(" shared uids\n");
            writeKernelMappingLPr();
            return true;
        } finally {
            if (!this.mVersion.containsKey(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL)) {
                android.util.Slog.wtf("PackageManager", "No internal VersionInfo found in settings, using current.");
                findOrCreateVersion(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL).forceCurrent();
            }
            if (!this.mVersion.containsKey("primary_physical")) {
                android.util.Slog.wtf("PackageManager", "No external VersionInfo found in settings, using current.");
                findOrCreateVersion("primary_physical").forceCurrent();
            }
        }
    }

    void readPermissionStateForUserSyncLPr(int i) {
        this.mRuntimePermissionsPersistence.readStateForUserSync(i, getInternalVersion(), this.mPackages, this.mSharedUsers, getUserRuntimePermissionsFile(i));
    }

    com.android.permission.persistence.RuntimePermissionsState getLegacyPermissionsState(int i) {
        return this.mRuntimePermissionsPersistence.getLegacyPermissionsState(i, this.mPackages, this.mSharedUsers);
    }

    void applyDefaultPreferredAppsLPw(int i) {
        int i2;
        int next;
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        for (com.android.server.pm.PackageSetting packageSetting : this.mPackages.values()) {
            if ((1 & packageSetting.getFlags()) != 0 && packageSetting.getPkg() != null && !packageSetting.getPkg().getPreferredActivityFilters().isEmpty()) {
                java.util.List preferredActivityFilters = packageSetting.getPkg().getPreferredActivityFilters();
                for (int i3 = 0; i3 < preferredActivityFilters.size(); i3++) {
                    android.util.Pair pair = (android.util.Pair) preferredActivityFilters.get(i3);
                    applyDefaultPreferredActivityLPw(packageManagerInternal, ((com.android.internal.pm.pkg.component.ParsedIntentInfo) pair.second).getIntentFilter(), new android.content.ComponentName(packageSetting.getPackageName(), (java.lang.String) pair.first), i);
                }
            }
        }
        int size = com.android.server.pm.PackageManagerService.SYSTEM_PARTITIONS.size();
        int i4 = 0;
        while (i4 < size) {
            java.io.File file = new java.io.File(com.android.server.pm.PackageManagerService.SYSTEM_PARTITIONS.get(i4).getFolder(), "etc/preferred-apps");
            if (file.exists() && file.isDirectory()) {
                if (file.canRead()) {
                    java.io.File[] listFiles = file.listFiles();
                    if (com.android.internal.util.ArrayUtils.isEmpty(listFiles)) {
                        continue;
                    } else {
                        int length = listFiles.length;
                        int i5 = 0;
                        while (i5 < length) {
                            java.io.File file2 = listFiles[i5];
                            if (!file2.getPath().endsWith(".xml")) {
                                android.util.Slog.i(TAG, "Non-xml file " + file2 + " in " + file + " directory, ignoring");
                                i2 = size;
                            } else if (file2.canRead()) {
                                try {
                                    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file2);
                                    try {
                                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                                        while (true) {
                                            next = resolvePullParser.next();
                                            i2 = size;
                                            if (next == 2 || next == 1) {
                                                break;
                                            } else {
                                                size = i2;
                                            }
                                        }
                                        if (next != 2) {
                                            try {
                                                android.util.Slog.w(TAG, "Preferred apps file " + file2 + " does not have start tag");
                                                try {
                                                    fileInputStream.close();
                                                } catch (java.io.IOException e) {
                                                    e = e;
                                                    android.util.Slog.w(TAG, "Error reading apps file " + file2, e);
                                                    i5++;
                                                    size = i2;
                                                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                                                    e = e2;
                                                    android.util.Slog.w(TAG, "Error reading apps file " + file2, e);
                                                    i5++;
                                                    size = i2;
                                                }
                                            } catch (java.lang.Throwable th) {
                                                th = th;
                                                java.lang.Throwable th2 = th;
                                                try {
                                                    fileInputStream.close();
                                                } catch (java.lang.Throwable th3) {
                                                    th2.addSuppressed(th3);
                                                }
                                                throw th2;
                                            }
                                        } else if ("preferred-activities".equals(resolvePullParser.getName())) {
                                            readDefaultPreferredActivitiesLPw(resolvePullParser, i);
                                            fileInputStream.close();
                                        } else {
                                            android.util.Slog.w(TAG, "Preferred apps file " + file2 + " does not start with 'preferred-activities'");
                                            fileInputStream.close();
                                        }
                                    } catch (java.lang.Throwable th4) {
                                        th = th4;
                                        i2 = size;
                                    }
                                } catch (java.io.IOException e3) {
                                    e = e3;
                                    i2 = size;
                                } catch (org.xmlpull.v1.XmlPullParserException e4) {
                                    e = e4;
                                    i2 = size;
                                }
                            } else {
                                android.util.Slog.w(TAG, "Preferred apps file " + file2 + " cannot be read");
                                i2 = size;
                            }
                            i5++;
                            size = i2;
                        }
                    }
                } else {
                    android.util.Slog.w(TAG, "Directory " + file + " cannot be read");
                }
            }
            i4++;
            size = size;
        }
    }

    static void removeFilters(@android.annotation.NonNull com.android.server.pm.PreferredIntentResolver preferredIntentResolver, @android.annotation.NonNull com.android.server.pm.WatchedIntentFilter watchedIntentFilter, @android.annotation.NonNull java.util.List<com.android.server.pm.PreferredActivity> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            preferredIntentResolver.removeFilter((com.android.server.pm.PreferredIntentResolver) list.get(size));
        }
    }

    private void applyDefaultPreferredActivityLPw(android.content.pm.PackageManagerInternal packageManagerInternal, android.content.IntentFilter intentFilter, android.content.ComponentName componentName, int i) {
        int i2;
        android.content.Intent intent = new android.content.Intent();
        int i3 = 0;
        intent.setAction(intentFilter.getAction(0));
        int i4 = 786432;
        for (int i5 = 0; i5 < intentFilter.countCategories(); i5++) {
            java.lang.String category = intentFilter.getCategory(i5);
            if (category.equals("android.intent.category.DEFAULT")) {
                i4 = 851968;
            } else {
                intent.addCategory(category);
            }
        }
        int countDataSchemes = intentFilter.countDataSchemes();
        int i6 = 0;
        boolean z = false;
        boolean z2 = true;
        while (i6 < countDataSchemes) {
            java.lang.String dataScheme = intentFilter.getDataScheme(i6);
            if (dataScheme != null && !dataScheme.isEmpty()) {
                z = true;
            }
            int countDataSchemeSpecificParts = intentFilter.countDataSchemeSpecificParts();
            int i7 = i3;
            boolean z3 = true;
            while (i7 < countDataSchemeSpecificParts) {
                android.net.Uri.Builder builder = new android.net.Uri.Builder();
                builder.scheme(dataScheme);
                android.os.PatternMatcher dataSchemeSpecificPart = intentFilter.getDataSchemeSpecificPart(i7);
                builder.opaquePart(dataSchemeSpecificPart.getPath());
                android.content.Intent intent2 = new android.content.Intent(intent);
                intent2.setData(builder.build());
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent2, i4, componentName, dataScheme, dataSchemeSpecificPart, null, null, i);
                i7++;
                dataScheme = dataScheme;
                countDataSchemeSpecificParts = countDataSchemeSpecificParts;
                i6 = i6;
                z3 = false;
            }
            java.lang.String str = dataScheme;
            int i8 = i6;
            int countDataAuthorities = intentFilter.countDataAuthorities();
            int i9 = 0;
            while (i9 < countDataAuthorities) {
                android.content.IntentFilter.AuthorityEntry dataAuthority = intentFilter.getDataAuthority(i9);
                int countDataPaths = intentFilter.countDataPaths();
                boolean z4 = true;
                int i10 = 0;
                while (i10 < countDataPaths) {
                    android.net.Uri.Builder builder2 = new android.net.Uri.Builder();
                    builder2.scheme(str);
                    if (dataAuthority.getHost() != null) {
                        builder2.authority(dataAuthority.getHost());
                    }
                    android.os.PatternMatcher dataPath = intentFilter.getDataPath(i10);
                    builder2.path(dataPath.getPath());
                    android.content.Intent intent3 = new android.content.Intent(intent);
                    intent3.setData(builder2.build());
                    applyDefaultPreferredActivityLPw(packageManagerInternal, intent3, i4, componentName, str, null, dataAuthority, dataPath, i);
                    i10++;
                    countDataAuthorities = countDataAuthorities;
                    countDataPaths = countDataPaths;
                    i9 = i9;
                    z3 = false;
                    z4 = false;
                }
                int i11 = i9;
                int i12 = countDataAuthorities;
                if (z4) {
                    android.net.Uri.Builder builder3 = new android.net.Uri.Builder();
                    builder3.scheme(str);
                    if (dataAuthority.getHost() != null) {
                        builder3.authority(dataAuthority.getHost());
                    }
                    android.content.Intent intent4 = new android.content.Intent(intent);
                    intent4.setData(builder3.build());
                    applyDefaultPreferredActivityLPw(packageManagerInternal, intent4, i4, componentName, str, null, dataAuthority, null, i);
                    z3 = false;
                }
                i9 = i11 + 1;
                countDataAuthorities = i12;
            }
            if (z3) {
                android.net.Uri.Builder builder4 = new android.net.Uri.Builder();
                builder4.scheme(str);
                android.content.Intent intent5 = new android.content.Intent(intent);
                intent5.setData(builder4.build());
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent5, i4, componentName, str, null, null, null, i);
            }
            i6 = i8 + 1;
            i3 = 0;
            z2 = false;
        }
        int i13 = 0;
        while (i13 < intentFilter.countDataTypes()) {
            java.lang.String dataType = intentFilter.getDataType(i13);
            if (z) {
                android.net.Uri.Builder builder5 = new android.net.Uri.Builder();
                int i14 = 0;
                while (i14 < intentFilter.countDataSchemes()) {
                    java.lang.String dataScheme2 = intentFilter.getDataScheme(i14);
                    if (dataScheme2 == null || dataScheme2.isEmpty()) {
                        i2 = i14;
                    } else {
                        android.content.Intent intent6 = new android.content.Intent(intent);
                        builder5.scheme(dataScheme2);
                        intent6.setDataAndType(builder5.build(), dataType);
                        i2 = i14;
                        applyDefaultPreferredActivityLPw(packageManagerInternal, intent6, i4, componentName, dataScheme2, null, null, null, i);
                    }
                    i14 = i2 + 1;
                }
            } else {
                android.content.Intent intent7 = new android.content.Intent(intent);
                intent7.setType(dataType);
                applyDefaultPreferredActivityLPw(packageManagerInternal, intent7, i4, componentName, null, null, null, null, i);
            }
            i13++;
            z2 = false;
        }
        if (z2) {
            applyDefaultPreferredActivityLPw(packageManagerInternal, intent, i4, componentName, null, null, null, null, i);
        }
    }

    private void applyDefaultPreferredActivityLPw(android.content.pm.PackageManagerInternal packageManagerInternal, android.content.Intent intent, int i, android.content.ComponentName componentName, java.lang.String str, android.os.PatternMatcher patternMatcher, android.content.IntentFilter.AuthorityEntry authorityEntry, android.os.PatternMatcher patternMatcher2, int i2) {
        android.content.ComponentName componentName2;
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = packageManagerInternal.queryIntentActivities(intent, intent.getType(), i, android.os.Binder.getCallingUid(), i2);
        int size = queryIntentActivities == null ? 0 : queryIntentActivities.size();
        if (size < 1) {
            android.util.Slog.w(TAG, "No potential matches found for " + intent + " while setting preferred " + componentName.flattenToShortString());
            return;
        }
        int size2 = queryIntentActivities.size();
        android.content.ComponentName[] componentNameArr = new android.content.ComponentName[size2];
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        while (true) {
            if (i3 >= size) {
                componentName2 = null;
                break;
            }
            android.content.pm.ActivityInfo activityInfo = queryIntentActivities.get(i3).activityInfo;
            int i5 = size;
            componentNameArr[i3] = new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
            if ((activityInfo.applicationInfo.flags & 1) == 0) {
                if (queryIntentActivities.get(i3).match >= 0) {
                    componentName2 = componentNameArr[i3];
                    break;
                }
            } else if (componentName.getPackageName().equals(activityInfo.packageName) && componentName.getClassName().equals(activityInfo.name)) {
                i4 = queryIntentActivities.get(i3).match;
                z = true;
            }
            i3++;
            size = i5;
        }
        if (componentName2 != null && i4 > 0) {
            componentName2 = null;
        }
        if (z && componentName2 == null) {
            com.android.server.pm.WatchedIntentFilter watchedIntentFilter = new com.android.server.pm.WatchedIntentFilter();
            if (intent.getAction() != null) {
                watchedIntentFilter.addAction(intent.getAction());
            }
            if (intent.getCategories() != null) {
                java.util.Iterator<java.lang.String> it = intent.getCategories().iterator();
                while (it.hasNext()) {
                    watchedIntentFilter.addCategory(it.next());
                }
            }
            if ((i & 65536) != 0) {
                watchedIntentFilter.addCategory("android.intent.category.DEFAULT");
            }
            if (str != null) {
                watchedIntentFilter.addDataScheme(str);
            }
            if (patternMatcher != null) {
                watchedIntentFilter.addDataSchemeSpecificPart(patternMatcher.getPath(), patternMatcher.getType());
            }
            if (authorityEntry != null) {
                watchedIntentFilter.addDataAuthority(authorityEntry);
            }
            if (patternMatcher2 != null) {
                watchedIntentFilter.addDataPath(patternMatcher2);
            }
            if (intent.getType() != null) {
                try {
                    watchedIntentFilter.addDataType(intent.getType());
                } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
                    android.util.Slog.w(TAG, "Malformed mimetype " + intent.getType() + " for " + componentName);
                }
            }
            com.android.server.pm.PreferredIntentResolver editPreferredActivitiesLPw = editPreferredActivitiesLPw(i2);
            java.util.ArrayList<com.android.server.pm.PreferredActivity> findFilters = editPreferredActivitiesLPw.findFilters(watchedIntentFilter);
            if (findFilters != null) {
                removeFilters(editPreferredActivitiesLPw, watchedIntentFilter, findFilters);
            }
            editPreferredActivitiesLPw.addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) null, (com.android.server.pm.snapshot.PackageDataSnapshot) new com.android.server.pm.PreferredActivity(watchedIntentFilter, i4, componentNameArr, componentName, true));
            return;
        }
        if (componentName2 == null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("No component ");
            sb.append(componentName.flattenToShortString());
            sb.append(" found setting preferred ");
            sb.append(intent);
            sb.append("; possible matches are ");
            for (int i6 = 0; i6 < size2; i6++) {
                if (i6 > 0) {
                    sb.append(", ");
                }
                sb.append(componentNameArr[i6].flattenToShortString());
            }
            android.util.Slog.w(TAG, sb.toString());
            return;
        }
        android.util.Slog.i(TAG, "Not setting preferred " + intent + "; found third party match " + componentName2.flattenToShortString());
    }

    private void readDefaultPreferredActivitiesLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(TAG_ITEM)) {
                        com.android.server.pm.PreferredActivity preferredActivity = new com.android.server.pm.PreferredActivity(typedXmlPullParser);
                        if (preferredActivity.mPref.getParseError() == null) {
                            applyDefaultPreferredActivityLPw(packageManagerInternal, preferredActivity.getIntentFilter(), preferredActivity.mPref.mComponent, i);
                        } else {
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <preferred-activity> " + preferredActivity.mPref.getParseError() + " at " + typedXmlPullParser.getPositionDescription());
                        }
                    } else {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <preferred-activities>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readDisabledSysPackageLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.List<android.content.pm.UserInfo> list) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String str;
        int i;
        com.android.server.pm.Settings settings;
        com.android.server.pm.Settings settings2;
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState;
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "realName");
        java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "codePath");
        java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "requiredCpuAbi");
        java.lang.String attributeValue5 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "nativeLibraryPath");
        java.lang.String attributeValue6 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "primaryCpuAbi");
        java.lang.String attributeValue7 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "secondaryCpuAbi");
        java.lang.String attributeValue8 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "cpuAbiOverride");
        if (attributeValue6 == null && attributeValue4 != null) {
            str = attributeValue4;
        } else {
            str = attributeValue6;
        }
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "version", 0L);
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "targetSdkVersion", 0);
        byte[] attributeBytesBase64 = typedXmlPullParser.getAttributeBytesBase64((java.lang.String) null, "restrictUpdateHash", (byte[]) null);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "scannedAsStoppedSystemApp", false);
        if (!attributeValue3.contains("/priv-app/")) {
            i = 0;
        } else {
            i = 8;
        }
        com.android.server.pm.PackageSetting scannedAsStoppedSystemApp = new com.android.server.pm.PackageSetting(attributeValue, attributeValue2, new java.io.File(attributeValue3), 1, i, com.android.server.pm.verify.domain.DomainVerificationManagerInternal.DISABLED_ID).setLegacyNativeLibraryPath(attributeValue5).setPrimaryCpuAbi(str).setSecondaryCpuAbi(attributeValue7).setCpuAbiOverride(attributeValue8).setLongVersionCode(attributeLong).setTargetSdkVersion(attributeInt).setRestrictUpdateHash(attributeBytesBase64).setScannedAsStoppedSystemApp(attributeBoolean);
        long attributeLongHex = typedXmlPullParser.getAttributeLongHex((java.lang.String) null, "ft", 0L);
        if (attributeLongHex == 0) {
            attributeLongHex = typedXmlPullParser.getAttributeLong((java.lang.String) null, "ts", 0L);
        }
        scannedAsStoppedSystemApp.setLastModifiedTime(attributeLongHex);
        scannedAsStoppedSystemApp.setLastUpdateTime(typedXmlPullParser.getAttributeLongHex((java.lang.String) null, "ut", 0L));
        scannedAsStoppedSystemApp.setAppId(parseAppId(typedXmlPullParser));
        if (scannedAsStoppedSystemApp.getAppId() <= 0) {
            int parseSharedUserAppId = parseSharedUserAppId(typedXmlPullParser);
            scannedAsStoppedSystemApp.setAppId(parseSharedUserAppId);
            scannedAsStoppedSystemApp.setSharedUserAppId(parseSharedUserAppId);
        }
        scannedAsStoppedSystemApp.setAppMetadataFilePath(typedXmlPullParser.getAttributeValue((java.lang.String) null, "appMetadataFilePath"));
        scannedAsStoppedSystemApp.setAppMetadataSource(typedXmlPullParser.getAttributeInt((java.lang.String) null, "appMetadataSource", 0));
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                settings = this;
                break;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                settings = this;
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals(TAG_PERMISSIONS)) {
                    if (scannedAsStoppedSystemApp.hasSharedUser()) {
                        settings2 = this;
                        com.android.server.pm.SettingBase settingLPr = settings2.getSettingLPr(scannedAsStoppedSystemApp.getSharedUserAppId());
                        legacyPermissionState = settingLPr != null ? settingLPr.getLegacyPermissionState() : null;
                    } else {
                        settings2 = this;
                        legacyPermissionState = scannedAsStoppedSystemApp.getLegacyPermissionState();
                    }
                    if (legacyPermissionState != null) {
                        settings2.readInstallPermissionsLPr(typedXmlPullParser, legacyPermissionState, list);
                    }
                } else if (typedXmlPullParser.getName().equals(TAG_USES_STATIC_LIB)) {
                    readUsesStaticLibLPw(typedXmlPullParser, scannedAsStoppedSystemApp);
                } else if (typedXmlPullParser.getName().equals(TAG_USES_SDK_LIB)) {
                    readUsesSdkLibLPw(typedXmlPullParser, scannedAsStoppedSystemApp);
                } else {
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <updated-package>: " + typedXmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        settings.mDisabledSysPackages.put(attributeValue, scannedAsStoppedSystemApp);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:(2:283|(1:(7:(11:342|343|344|345|346|347|348|349|350|128|(6:130|(6:257|258|259|260|261|262)(1:132)|133|(2:134|(1:1)(2:(3:155|156|(3:252|253|254)(3:158|159|(3:249|250|251)(3:161|162|(3:246|247|248)(3:164|165|(6:233|234|(2:236|(1:238)(1:244))(1:245)|(1:240)(1:243)|241|242)(5:167|168|(4:227|228|(1:230)(1:232)|231)(2:170|(1:224)(3:172|173|(3:221|222|223)(3:175|176|(5:214|215|(1:217)(1:220)|218|219)(3:178|179|(3:211|212|213)(3:181|182|(3:208|209|210)(3:184|185|(5:202|203|(1:205)|206|207)(3:187|188|(3:199|200|201)(3:190|191|(3:196|197|198)(3:193|194|195)))))))))|225|226)))))|151))|141|(2:143|144)(1:146))(2:278|279))(10:319|320|321|322|323|324|325|326|327|328)|332|333|334|335|307|(0)(0))(9:361|362|363|364|365|366|367|368|369))(1:288))(1:379)|289|290|291|292|293|294|(4:299|300|301|302)(1:296)|297|128|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x045c, code lost:
    
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x045e, code lost:
    
        r4 = r1;
        r5 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x046b, code lost:
    
        r26 = r2;
        r9 = r4;
        r16 = r7;
        r10 = r8;
        r27 = r14;
        r19 = 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x08de  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0b84  */
    /* JADX WARN: Removed duplicated region for block: B:146:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0b92  */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.server.pm.PackageSetting] */
    /* JADX WARN: Type inference failed for: r12v16, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r1v26, types: [com.android.server.pm.Settings] */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v34 */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r1v38 */
    /* JADX WARN: Type inference failed for: r1v9, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r2v36, types: [com.android.server.pm.PackageSetting, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v55, types: [com.android.server.pm.PackageSetting] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v62 */
    /* JADX WARN: Type inference failed for: r5v64 */
    /* JADX WARN: Type inference failed for: r5v65 */
    /* JADX WARN: Type inference failed for: r5v67 */
    /* JADX WARN: Type inference failed for: r5v7, types: [com.android.server.pm.PackageSetting] */
    /* JADX WARN: Type inference failed for: r60v0, types: [com.android.server.pm.Settings] */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v63 */
    /* JADX WARN: Type inference failed for: r6v64 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 10 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 4 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 6 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 8 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readPackageLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.ArrayList<android.content.pm.Signature> arrayList, android.util.ArrayMap<java.lang.Long, java.lang.Integer> arrayMap, java.util.List<android.content.pm.UserInfo> list, android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        long j;
        int i;
        int i2;
        java.lang.String str5;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        java.lang.String str9;
        java.lang.String str10;
        java.lang.String str11;
        java.lang.String str12;
        java.lang.String str13;
        java.lang.String str14;
        java.lang.String str15;
        java.lang.String str16;
        float f;
        long j2;
        long j3;
        int i3;
        int i4;
        boolean z;
        boolean z2;
        int i5;
        boolean z3;
        boolean z4;
        int i6;
        boolean z5;
        int i7;
        float f2;
        java.lang.String str17;
        int i8;
        boolean z6;
        boolean z7;
        long j4;
        java.lang.String str18;
        java.lang.String str19;
        java.lang.String str20;
        java.lang.String str21;
        int i9;
        boolean z8;
        java.lang.String str22;
        java.lang.String str23;
        boolean z9;
        java.lang.String str24;
        int i10;
        java.lang.String str25;
        boolean z10;
        java.lang.String str26;
        java.lang.String str27;
        ?? r5;
        int i11;
        int i12;
        ?? r6;
        int next;
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState;
        java.lang.String attributeValue;
        java.lang.String attributeValue2;
        int parseAppId;
        java.lang.String str28;
        float f3;
        boolean attributeBoolean;
        int parseSharedUserAppId;
        java.lang.String attributeValue3;
        java.lang.String str29;
        float f4;
        java.lang.String attributeValue4;
        java.lang.String str30;
        int i13;
        java.lang.String attributeValue5;
        float attributeFloat;
        java.lang.String str31;
        int i14;
        long attributeLong;
        int attributeInt;
        int attributeInt2;
        java.lang.String str32;
        java.util.UUID generateNewId;
        int i15;
        int parseInt;
        int i16;
        long attributeLong2;
        java.lang.String str33;
        long attributeLongHex;
        java.lang.String intern;
        long j5;
        java.lang.String str34;
        java.lang.String str35;
        java.lang.String str36;
        java.lang.String str37;
        java.lang.String str38;
        char c;
        ?? addPackageLPw;
        java.lang.String str39;
        java.lang.String str40;
        java.lang.String str41;
        java.lang.StringBuilder sb;
        java.lang.String str42;
        java.lang.StringBuilder sb2;
        int i17;
        java.lang.String str43 = null;
        try {
            attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        } catch (java.lang.NumberFormatException e) {
            str = "Error in package manager settings: package ";
            str2 = " has bad appId ";
            str3 = "true";
            str4 = " at ";
            j = 0;
            i = -1;
            i2 = -1;
            str5 = null;
            str6 = null;
            str7 = null;
            str8 = null;
            str9 = null;
            str10 = null;
            str11 = null;
            str12 = null;
            str13 = null;
            str14 = null;
            str15 = null;
            str16 = null;
            f = 0.0f;
        }
        try {
            attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "realName");
            parseAppId = parseAppId(typedXmlPullParser);
            try {
                attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "isSdkLibrary", false);
                parseSharedUserAppId = parseSharedUserAppId(typedXmlPullParser);
                attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "codePath");
                java.lang.String attributeValue6 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "requiredCpuAbi");
                java.lang.String attributeValue7 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "nativeLibraryPath");
                try {
                    java.lang.String attributeValue8 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "primaryCpuAbi");
                    try {
                        attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "secondaryCpuAbi");
                        try {
                            attributeValue5 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "cpuAbiOverride");
                            str2 = " has bad appId ";
                            str4 = " at ";
                            try {
                                z = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "updateAvailable", false);
                            } catch (java.lang.NumberFormatException e2) {
                                str5 = attributeValue4;
                                str7 = attributeValue7;
                                str29 = attributeValue;
                                str = "Error in package manager settings: package ";
                                str30 = null;
                                str3 = "true";
                                i13 = -1;
                                f4 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                                j = 0;
                                i2 = i13;
                                str8 = str30;
                                str9 = str8;
                                str10 = str9;
                                str11 = str10;
                                str12 = str11;
                                str13 = str12;
                                str14 = str13;
                                str15 = str14;
                                str16 = str15;
                                str6 = attributeValue8;
                                f = f4;
                                str43 = str29;
                                j2 = j;
                                j3 = j2;
                                i4 = 0;
                                z = false;
                                z2 = false;
                                i5 = 0;
                                z3 = false;
                                z4 = false;
                                i6 = 0;
                                z5 = false;
                                i3 = parseAppId;
                                i = i2;
                                java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                                sb3.append(str);
                                sb3.append(str43);
                                sb3.append(str2);
                                sb3.append(i3);
                                java.lang.String str44 = str4;
                                sb3.append(str44);
                                sb3.append(typedXmlPullParser.getPositionDescription());
                                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3.toString());
                                i7 = i;
                                f2 = f;
                                str17 = str7;
                                i8 = i4;
                                z6 = z;
                                z7 = z2;
                                j4 = j2;
                                str18 = str9;
                                str19 = str10;
                                str20 = str11;
                                str21 = str12;
                                i9 = i5;
                                z8 = z3;
                                str22 = str13;
                                str23 = str14;
                                z9 = z4;
                                str24 = str15;
                                i10 = i2;
                                str25 = str16;
                                z10 = z5;
                                str26 = str44;
                                str27 = str43;
                                r5 = str8;
                                i11 = i6;
                                if (r5 == 0) {
                                }
                            }
                        } catch (java.lang.NumberFormatException e3) {
                            str5 = attributeValue4;
                            str29 = attributeValue;
                            str = "Error in package manager settings: package ";
                            str30 = null;
                            str2 = " has bad appId ";
                            str3 = "true";
                            str4 = " at ";
                            i13 = -1;
                            f4 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                            j = 0;
                            str7 = attributeValue7;
                        }
                        try {
                            z2 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "forceQueryable", false);
                            try {
                                attributeFloat = typedXmlPullParser.getAttributeFloat((java.lang.String) null, "loadingProgress", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                                try {
                                    j2 = typedXmlPullParser.getAttributeLongHex((java.lang.String) null, "loadingCompletedTime", 0L);
                                    str6 = (attributeValue8 != null || attributeValue6 == null) ? attributeValue8 : attributeValue6;
                                    str7 = attributeValue7;
                                    try {
                                        attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "version", 0L);
                                    } catch (java.lang.NumberFormatException e4) {
                                        str31 = attributeValue;
                                        str = "Error in package manager settings: package ";
                                        j = 0;
                                        str3 = "true";
                                        str5 = attributeValue4;
                                        i14 = -1;
                                    }
                                    try {
                                        attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "targetSdkVersion", 0);
                                        try {
                                            ?? attributeBytesBase64 = typedXmlPullParser.getAttributeBytesBase64((java.lang.String) null, "restrictUpdateHash", (byte[]) null);
                                            try {
                                                str9 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "installer");
                                                str10 = attributeBytesBase64;
                                            } catch (java.lang.NumberFormatException e5) {
                                                str = "Error in package manager settings: package ";
                                                str10 = attributeBytesBase64;
                                                i4 = attributeInt;
                                                str3 = "true";
                                                str5 = attributeValue4;
                                                j = 0;
                                                i2 = -1;
                                                str43 = attributeValue;
                                                j3 = 0;
                                                f = attributeFloat;
                                                str8 = null;
                                                str9 = null;
                                            }
                                        } catch (java.lang.NumberFormatException e6) {
                                            str = "Error in package manager settings: package ";
                                            i4 = attributeInt;
                                            str3 = "true";
                                            str5 = attributeValue4;
                                            j = 0;
                                            i2 = -1;
                                            str43 = attributeValue;
                                            j3 = 0;
                                            f = attributeFloat;
                                            str8 = null;
                                            str9 = null;
                                            str10 = null;
                                            str11 = null;
                                            str12 = null;
                                            i5 = 0;
                                            z3 = false;
                                            str13 = null;
                                            str14 = null;
                                            z4 = false;
                                            str15 = null;
                                            str16 = null;
                                            i6 = 0;
                                            z5 = false;
                                            i3 = parseAppId;
                                            i = i2;
                                            java.lang.StringBuilder sb32 = new java.lang.StringBuilder();
                                            sb32.append(str);
                                            sb32.append(str43);
                                            sb32.append(str2);
                                            sb32.append(i3);
                                            java.lang.String str442 = str4;
                                            sb32.append(str442);
                                            sb32.append(typedXmlPullParser.getPositionDescription());
                                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32.toString());
                                            i7 = i;
                                            f2 = f;
                                            str17 = str7;
                                            i8 = i4;
                                            z6 = z;
                                            z7 = z2;
                                            j4 = j2;
                                            str18 = str9;
                                            str19 = str10;
                                            str20 = str11;
                                            str21 = str12;
                                            i9 = i5;
                                            z8 = z3;
                                            str22 = str13;
                                            str23 = str14;
                                            z9 = z4;
                                            str24 = str15;
                                            i10 = i2;
                                            str25 = str16;
                                            z10 = z5;
                                            str26 = str442;
                                            str27 = str43;
                                            r5 = str8;
                                            i11 = i6;
                                            if (r5 == 0) {
                                            }
                                        }
                                    } catch (java.lang.NumberFormatException e7) {
                                        str31 = attributeValue;
                                        str = "Error in package manager settings: package ";
                                        str3 = "true";
                                        str5 = attributeValue4;
                                        i14 = -1;
                                        j = 0;
                                        i2 = i14;
                                        str43 = str31;
                                        j3 = j;
                                        f = attributeFloat;
                                        str8 = null;
                                        i4 = 0;
                                        str9 = null;
                                        str10 = null;
                                        str11 = null;
                                        str12 = null;
                                        i5 = 0;
                                        z3 = false;
                                        str13 = null;
                                        str14 = null;
                                        z4 = false;
                                        str15 = null;
                                        str16 = null;
                                        i6 = 0;
                                        z5 = false;
                                        i3 = parseAppId;
                                        i = i2;
                                        java.lang.StringBuilder sb322 = new java.lang.StringBuilder();
                                        sb322.append(str);
                                        sb322.append(str43);
                                        sb322.append(str2);
                                        sb322.append(i3);
                                        java.lang.String str4422 = str4;
                                        sb322.append(str4422);
                                        sb322.append(typedXmlPullParser.getPositionDescription());
                                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322.toString());
                                        i7 = i;
                                        f2 = f;
                                        str17 = str7;
                                        i8 = i4;
                                        z6 = z;
                                        z7 = z2;
                                        j4 = j2;
                                        str18 = str9;
                                        str19 = str10;
                                        str20 = str11;
                                        str21 = str12;
                                        i9 = i5;
                                        z8 = z3;
                                        str22 = str13;
                                        str23 = str14;
                                        z9 = z4;
                                        str24 = str15;
                                        i10 = i2;
                                        str25 = str16;
                                        z10 = z5;
                                        str26 = str4422;
                                        str27 = str43;
                                        r5 = str8;
                                        i11 = i6;
                                        if (r5 == 0) {
                                        }
                                    }
                                    try {
                                        attributeInt2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "installerUid", -1);
                                        try {
                                            java.lang.String attributeValue9 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "updateOwner");
                                            try {
                                                str11 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "installerAttributionTag");
                                                str12 = attributeValue9;
                                            } catch (java.lang.NumberFormatException e8) {
                                                str = "Error in package manager settings: package ";
                                                str12 = attributeValue9;
                                                i4 = attributeInt;
                                                str3 = "true";
                                                str5 = attributeValue4;
                                                j = 0;
                                                i2 = -1;
                                                str43 = attributeValue;
                                                j3 = 0;
                                                f = attributeFloat;
                                                str8 = null;
                                                str11 = null;
                                            }
                                        } catch (java.lang.NumberFormatException e9) {
                                            str = "Error in package manager settings: package ";
                                            i4 = attributeInt;
                                            str3 = "true";
                                            str5 = attributeValue4;
                                            j = 0;
                                            i2 = -1;
                                            str43 = attributeValue;
                                            j3 = 0;
                                            f = attributeFloat;
                                            str8 = null;
                                            str11 = null;
                                            str12 = null;
                                        }
                                    } catch (java.lang.NumberFormatException e10) {
                                        str = "Error in package manager settings: package ";
                                        i4 = attributeInt;
                                        str3 = "true";
                                        str5 = attributeValue4;
                                        j = 0;
                                        i2 = -1;
                                        str43 = attributeValue;
                                        j3 = 0;
                                        f = attributeFloat;
                                        str8 = null;
                                        str11 = null;
                                        str12 = null;
                                        i5 = 0;
                                        z3 = false;
                                        str13 = null;
                                        str14 = null;
                                        z4 = false;
                                        str15 = null;
                                        str16 = null;
                                        i6 = 0;
                                        z5 = false;
                                        i3 = parseAppId;
                                        i = i2;
                                        java.lang.StringBuilder sb3222 = new java.lang.StringBuilder();
                                        sb3222.append(str);
                                        sb3222.append(str43);
                                        sb3222.append(str2);
                                        sb3222.append(i3);
                                        java.lang.String str44222 = str4;
                                        sb3222.append(str44222);
                                        sb3222.append(typedXmlPullParser.getPositionDescription());
                                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222.toString());
                                        i7 = i;
                                        f2 = f;
                                        str17 = str7;
                                        i8 = i4;
                                        z6 = z;
                                        z7 = z2;
                                        j4 = j2;
                                        str18 = str9;
                                        str19 = str10;
                                        str20 = str11;
                                        str21 = str12;
                                        i9 = i5;
                                        z8 = z3;
                                        str22 = str13;
                                        str23 = str14;
                                        z9 = z4;
                                        str24 = str15;
                                        i10 = i2;
                                        str25 = str16;
                                        z10 = z5;
                                        str26 = str44222;
                                        str27 = str43;
                                        r5 = str8;
                                        i11 = i6;
                                        if (r5 == 0) {
                                        }
                                    }
                                } catch (java.lang.NumberFormatException e11) {
                                    str5 = attributeValue4;
                                    str7 = attributeValue7;
                                    str = "Error in package manager settings: package ";
                                    j = 0;
                                    str3 = "true";
                                    i2 = -1;
                                    str6 = attributeValue8;
                                    str43 = attributeValue;
                                    j2 = 0;
                                    j3 = 0;
                                }
                            } catch (java.lang.NumberFormatException e12) {
                                str5 = attributeValue4;
                                str7 = attributeValue7;
                                str = "Error in package manager settings: package ";
                                str3 = "true";
                                j = 0;
                                i2 = -1;
                                str8 = null;
                                str9 = null;
                                str10 = null;
                                str11 = null;
                                str12 = null;
                                str13 = null;
                                str14 = null;
                                str15 = null;
                                str16 = null;
                                str6 = attributeValue8;
                                f = 0.0f;
                                str43 = attributeValue;
                                j2 = 0;
                                j3 = 0;
                                i4 = 0;
                                i5 = 0;
                                z3 = false;
                                z4 = false;
                                i6 = 0;
                                z5 = false;
                                i3 = parseAppId;
                                i = i2;
                                java.lang.StringBuilder sb32222 = new java.lang.StringBuilder();
                                sb32222.append(str);
                                sb32222.append(str43);
                                sb32222.append(str2);
                                sb32222.append(i3);
                                java.lang.String str442222 = str4;
                                sb32222.append(str442222);
                                sb32222.append(typedXmlPullParser.getPositionDescription());
                                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32222.toString());
                                i7 = i;
                                f2 = f;
                                str17 = str7;
                                i8 = i4;
                                z6 = z;
                                z7 = z2;
                                j4 = j2;
                                str18 = str9;
                                str19 = str10;
                                str20 = str11;
                                str21 = str12;
                                i9 = i5;
                                z8 = z3;
                                str22 = str13;
                                str23 = str14;
                                z9 = z4;
                                str24 = str15;
                                i10 = i2;
                                str25 = str16;
                                z10 = z5;
                                str26 = str442222;
                                str27 = str43;
                                r5 = str8;
                                i11 = i6;
                                if (r5 == 0) {
                                }
                            }
                        } catch (java.lang.NumberFormatException e13) {
                            str5 = attributeValue4;
                            str7 = attributeValue7;
                            str = "Error in package manager settings: package ";
                            str3 = "true";
                            j = 0;
                            i2 = -1;
                            str8 = null;
                            str9 = null;
                            str10 = null;
                            str11 = null;
                            str12 = null;
                            str13 = null;
                            str14 = null;
                            str15 = null;
                            str16 = null;
                            str6 = attributeValue8;
                            f = 0.0f;
                            str43 = attributeValue;
                            j2 = 0;
                            j3 = 0;
                            i4 = 0;
                            z2 = false;
                            i5 = 0;
                            z3 = false;
                            z4 = false;
                            i6 = 0;
                            z5 = false;
                            i3 = parseAppId;
                            i = i2;
                            java.lang.StringBuilder sb322222 = new java.lang.StringBuilder();
                            sb322222.append(str);
                            sb322222.append(str43);
                            sb322222.append(str2);
                            sb322222.append(i3);
                            java.lang.String str4422222 = str4;
                            sb322222.append(str4422222);
                            sb322222.append(typedXmlPullParser.getPositionDescription());
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322222.toString());
                            i7 = i;
                            f2 = f;
                            str17 = str7;
                            i8 = i4;
                            z6 = z;
                            z7 = z2;
                            j4 = j2;
                            str18 = str9;
                            str19 = str10;
                            str20 = str11;
                            str21 = str12;
                            i9 = i5;
                            z8 = z3;
                            str22 = str13;
                            str23 = str14;
                            z9 = z4;
                            str24 = str15;
                            i10 = i2;
                            str25 = str16;
                            z10 = z5;
                            str26 = str4422222;
                            str27 = str43;
                            r5 = str8;
                            i11 = i6;
                            if (r5 == 0) {
                            }
                        }
                    } catch (java.lang.NumberFormatException e14) {
                        str29 = attributeValue;
                        str = "Error in package manager settings: package ";
                        str2 = " has bad appId ";
                        str3 = "true";
                        str4 = " at ";
                        f4 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                        j = 0;
                        str7 = attributeValue7;
                        i2 = -1;
                        str5 = null;
                        str8 = null;
                    }
                } catch (java.lang.NumberFormatException e15) {
                    str28 = attributeValue;
                    str = "Error in package manager settings: package ";
                    str2 = " has bad appId ";
                    str3 = "true";
                    str4 = " at ";
                    f3 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                    j = 0;
                    str7 = attributeValue7;
                    i3 = parseAppId;
                    i = -1;
                    i2 = -1;
                    str5 = null;
                    str6 = null;
                    str8 = null;
                    str9 = str8;
                    str10 = str9;
                    str11 = str10;
                    str12 = str11;
                    str13 = str12;
                    str14 = str13;
                    str15 = str14;
                    str16 = str15;
                    f = f3;
                    str43 = str28;
                    j2 = j;
                    j3 = j2;
                    i4 = 0;
                    z = false;
                    z2 = false;
                    i5 = 0;
                    z3 = false;
                    z4 = false;
                    i6 = 0;
                    z5 = false;
                    java.lang.StringBuilder sb3222222 = new java.lang.StringBuilder();
                    sb3222222.append(str);
                    sb3222222.append(str43);
                    sb3222222.append(str2);
                    sb3222222.append(i3);
                    java.lang.String str44222222 = str4;
                    sb3222222.append(str44222222);
                    sb3222222.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222222.toString());
                    i7 = i;
                    f2 = f;
                    str17 = str7;
                    i8 = i4;
                    z6 = z;
                    z7 = z2;
                    j4 = j2;
                    str18 = str9;
                    str19 = str10;
                    str20 = str11;
                    str21 = str12;
                    i9 = i5;
                    z8 = z3;
                    str22 = str13;
                    str23 = str14;
                    z9 = z4;
                    str24 = str15;
                    i10 = i2;
                    str25 = str16;
                    z10 = z5;
                    str26 = str44222222;
                    str27 = str43;
                    r5 = str8;
                    i11 = i6;
                    if (r5 == 0) {
                    }
                }
            } catch (java.lang.NumberFormatException e16) {
                str28 = attributeValue;
                str = "Error in package manager settings: package ";
                str2 = " has bad appId ";
                str3 = "true";
                str4 = " at ";
                f3 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                j = 0;
                i3 = parseAppId;
                i = -1;
                i2 = -1;
                str5 = null;
                str6 = null;
                str7 = null;
                str8 = null;
            }
        } catch (java.lang.NumberFormatException e17) {
            str = "Error in package manager settings: package ";
            str2 = " has bad appId ";
            str3 = "true";
            str4 = " at ";
            j = 0;
            i = -1;
            i2 = -1;
            str5 = null;
            str6 = null;
            str7 = null;
            str8 = null;
            str9 = null;
            str10 = null;
            str11 = null;
            str12 = null;
            str13 = null;
            str14 = null;
            str15 = null;
            str16 = null;
            f = 0.0f;
            str43 = attributeValue;
            j2 = j;
            j3 = j2;
            i3 = 0;
            i4 = 0;
            z = false;
            z2 = false;
            i5 = 0;
            z3 = false;
            z4 = false;
            i6 = 0;
            z5 = false;
            java.lang.StringBuilder sb32222222 = new java.lang.StringBuilder();
            sb32222222.append(str);
            sb32222222.append(str43);
            sb32222222.append(str2);
            sb32222222.append(i3);
            java.lang.String str442222222 = str4;
            sb32222222.append(str442222222);
            sb32222222.append(typedXmlPullParser.getPositionDescription());
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32222222.toString());
            i7 = i;
            f2 = f;
            str17 = str7;
            i8 = i4;
            z6 = z;
            z7 = z2;
            j4 = j2;
            str18 = str9;
            str19 = str10;
            str20 = str11;
            str21 = str12;
            i9 = i5;
            z8 = z3;
            str22 = str13;
            str23 = str14;
            z9 = z4;
            str24 = str15;
            i10 = i2;
            str25 = str16;
            z10 = z5;
            str26 = str442222222;
            str27 = str43;
            r5 = str8;
            i11 = i6;
            if (r5 == 0) {
            }
        }
        try {
            i5 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "packageSource", 0);
            try {
                z3 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "isOrphaned", false);
                try {
                    java.lang.String attributeValue10 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "installInitiator");
                    try {
                        str13 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "installOriginator");
                        str14 = attributeValue10;
                    } catch (java.lang.NumberFormatException e18) {
                        str = "Error in package manager settings: package ";
                        str14 = attributeValue10;
                        i4 = attributeInt;
                        str3 = "true";
                        str5 = attributeValue4;
                        j = 0;
                        i2 = -1;
                        str43 = attributeValue;
                        j3 = 0;
                        f = attributeFloat;
                        str8 = null;
                        str13 = null;
                    }
                } catch (java.lang.NumberFormatException e19) {
                    str = "Error in package manager settings: package ";
                    i4 = attributeInt;
                    str3 = "true";
                    str5 = attributeValue4;
                    j = 0;
                    i2 = -1;
                    str43 = attributeValue;
                    j3 = 0;
                    f = attributeFloat;
                    str8 = null;
                    str13 = null;
                    str14 = null;
                    z4 = false;
                    str15 = null;
                    str16 = null;
                    i6 = 0;
                    z5 = false;
                    i3 = parseAppId;
                    i = attributeInt2;
                    java.lang.StringBuilder sb322222222 = new java.lang.StringBuilder();
                    sb322222222.append(str);
                    sb322222222.append(str43);
                    sb322222222.append(str2);
                    sb322222222.append(i3);
                    java.lang.String str4422222222 = str4;
                    sb322222222.append(str4422222222);
                    sb322222222.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322222222.toString());
                    i7 = i;
                    f2 = f;
                    str17 = str7;
                    i8 = i4;
                    z6 = z;
                    z7 = z2;
                    j4 = j2;
                    str18 = str9;
                    str19 = str10;
                    str20 = str11;
                    str21 = str12;
                    i9 = i5;
                    z8 = z3;
                    str22 = str13;
                    str23 = str14;
                    z9 = z4;
                    str24 = str15;
                    i10 = i2;
                    str25 = str16;
                    z10 = z5;
                    str26 = str4422222222;
                    str27 = str43;
                    r5 = str8;
                    i11 = i6;
                    if (r5 == 0) {
                    }
                }
            } catch (java.lang.NumberFormatException e20) {
                str = "Error in package manager settings: package ";
                i4 = attributeInt;
                str3 = "true";
                str5 = attributeValue4;
                j = 0;
                i2 = -1;
                str43 = attributeValue;
                j3 = 0;
                f = attributeFloat;
                str8 = null;
                z3 = false;
                str13 = null;
                str14 = null;
                z4 = false;
                str15 = null;
                str16 = null;
                i6 = 0;
                z5 = false;
                i3 = parseAppId;
                i = attributeInt2;
                java.lang.StringBuilder sb3222222222 = new java.lang.StringBuilder();
                sb3222222222.append(str);
                sb3222222222.append(str43);
                sb3222222222.append(str2);
                sb3222222222.append(i3);
                java.lang.String str44222222222 = str4;
                sb3222222222.append(str44222222222);
                sb3222222222.append(typedXmlPullParser.getPositionDescription());
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222222222.toString());
                i7 = i;
                f2 = f;
                str17 = str7;
                i8 = i4;
                z6 = z;
                z7 = z2;
                j4 = j2;
                str18 = str9;
                str19 = str10;
                str20 = str11;
                str21 = str12;
                i9 = i5;
                z8 = z3;
                str22 = str13;
                str23 = str14;
                z9 = z4;
                str24 = str15;
                i10 = i2;
                str25 = str16;
                z10 = z5;
                str26 = str44222222222;
                str27 = str43;
                r5 = str8;
                i11 = i6;
                if (r5 == 0) {
                }
            }
            try {
                z4 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "installInitiatorUninstalled", false);
                try {
                    str15 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VOLUME_UUID);
                    try {
                        i2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "categoryHint", -1);
                        try {
                            str16 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "appMetadataFilePath");
                        } catch (java.lang.NumberFormatException e21) {
                            str = "Error in package manager settings: package ";
                            i4 = attributeInt;
                            str3 = "true";
                            str5 = attributeValue4;
                            j = 0;
                            i3 = parseAppId;
                            str43 = attributeValue;
                            j3 = 0;
                            f = attributeFloat;
                            i = attributeInt2;
                            str8 = null;
                            str16 = null;
                        }
                    } catch (java.lang.NumberFormatException e22) {
                        str = "Error in package manager settings: package ";
                        i4 = attributeInt;
                        str3 = "true";
                        str5 = attributeValue4;
                        j = 0;
                        i2 = -1;
                        str43 = attributeValue;
                        j3 = 0;
                        f = attributeFloat;
                        str8 = null;
                        str16 = null;
                        i6 = 0;
                        z5 = false;
                        i3 = parseAppId;
                        i = attributeInt2;
                        java.lang.StringBuilder sb32222222222 = new java.lang.StringBuilder();
                        sb32222222222.append(str);
                        sb32222222222.append(str43);
                        sb32222222222.append(str2);
                        sb32222222222.append(i3);
                        java.lang.String str442222222222 = str4;
                        sb32222222222.append(str442222222222);
                        sb32222222222.append(typedXmlPullParser.getPositionDescription());
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32222222222.toString());
                        i7 = i;
                        f2 = f;
                        str17 = str7;
                        i8 = i4;
                        z6 = z;
                        z7 = z2;
                        j4 = j2;
                        str18 = str9;
                        str19 = str10;
                        str20 = str11;
                        str21 = str12;
                        i9 = i5;
                        z8 = z3;
                        str22 = str13;
                        str23 = str14;
                        z9 = z4;
                        str24 = str15;
                        i10 = i2;
                        str25 = str16;
                        z10 = z5;
                        str26 = str442222222222;
                        str27 = str43;
                        r5 = str8;
                        i11 = i6;
                        if (r5 == 0) {
                        }
                    }
                } catch (java.lang.NumberFormatException e23) {
                    str = "Error in package manager settings: package ";
                    i4 = attributeInt;
                    str3 = "true";
                    str5 = attributeValue4;
                    j = 0;
                    i2 = -1;
                    str43 = attributeValue;
                    j3 = 0;
                    f = attributeFloat;
                    str8 = null;
                    str15 = null;
                    str16 = null;
                    i6 = 0;
                    z5 = false;
                    i3 = parseAppId;
                    i = attributeInt2;
                    java.lang.StringBuilder sb322222222222 = new java.lang.StringBuilder();
                    sb322222222222.append(str);
                    sb322222222222.append(str43);
                    sb322222222222.append(str2);
                    sb322222222222.append(i3);
                    java.lang.String str4422222222222 = str4;
                    sb322222222222.append(str4422222222222);
                    sb322222222222.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322222222222.toString());
                    i7 = i;
                    f2 = f;
                    str17 = str7;
                    i8 = i4;
                    z6 = z;
                    z7 = z2;
                    j4 = j2;
                    str18 = str9;
                    str19 = str10;
                    str20 = str11;
                    str21 = str12;
                    i9 = i5;
                    z8 = z3;
                    str22 = str13;
                    str23 = str14;
                    z9 = z4;
                    str24 = str15;
                    i10 = i2;
                    str25 = str16;
                    z10 = z5;
                    str26 = str4422222222222;
                    str27 = str43;
                    r5 = str8;
                    i11 = i6;
                    if (r5 == 0) {
                    }
                }
                try {
                    i6 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "appMetadataSource", 0);
                    try {
                        z5 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "scannedAsStoppedSystemApp", false);
                        try {
                            java.lang.String attributeValue11 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "domainSetId");
                            if (android.text.TextUtils.isEmpty(attributeValue11)) {
                                try {
                                    generateNewId = this.mDomainVerificationManager.generateNewId();
                                } catch (java.lang.NumberFormatException e24) {
                                    i3 = parseAppId;
                                    str43 = attributeValue;
                                    str = "Error in package manager settings: package ";
                                    i4 = attributeInt;
                                    str3 = "true";
                                    f = attributeFloat;
                                    str5 = attributeValue4;
                                    i = attributeInt2;
                                    str8 = null;
                                    j = 0;
                                    j3 = 0;
                                    java.lang.StringBuilder sb3222222222222 = new java.lang.StringBuilder();
                                    sb3222222222222.append(str);
                                    sb3222222222222.append(str43);
                                    sb3222222222222.append(str2);
                                    sb3222222222222.append(i3);
                                    java.lang.String str44222222222222 = str4;
                                    sb3222222222222.append(str44222222222222);
                                    sb3222222222222.append(typedXmlPullParser.getPositionDescription());
                                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222222222222.toString());
                                    i7 = i;
                                    f2 = f;
                                    str17 = str7;
                                    i8 = i4;
                                    z6 = z;
                                    z7 = z2;
                                    j4 = j2;
                                    str18 = str9;
                                    str19 = str10;
                                    str20 = str11;
                                    str21 = str12;
                                    i9 = i5;
                                    z8 = z3;
                                    str22 = str13;
                                    str23 = str14;
                                    z9 = z4;
                                    str24 = str15;
                                    i10 = i2;
                                    str25 = str16;
                                    z10 = z5;
                                    str26 = str44222222222222;
                                    str27 = str43;
                                    r5 = str8;
                                    i11 = i6;
                                    if (r5 == 0) {
                                    }
                                }
                            } else {
                                generateNewId = java.util.UUID.fromString(attributeValue11);
                            }
                            java.lang.String attributeValue12 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "publicFlags");
                            if (attributeValue12 != null) {
                                try {
                                    i15 = java.lang.Integer.parseInt(attributeValue12);
                                } catch (java.lang.NumberFormatException e25) {
                                    i15 = 0;
                                }
                                java.lang.String attributeValue13 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "privateFlags");
                                if (attributeValue13 != null) {
                                    try {
                                        parseInt = java.lang.Integer.parseInt(attributeValue13);
                                        i16 = i15;
                                    } catch (java.lang.NumberFormatException e26) {
                                    }
                                }
                                i16 = i15;
                                parseInt = 0;
                            } else {
                                java.lang.String attributeValue14 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_FLAGS);
                                if (attributeValue14 != null) {
                                    try {
                                        i17 = java.lang.Integer.parseInt(attributeValue14);
                                    } catch (java.lang.NumberFormatException e27) {
                                        i17 = 0;
                                    }
                                    int i18 = (134217728 & i17) != 0 ? 1 : 0;
                                    if ((268435456 & i17) != 0) {
                                        i18 |= 2;
                                    }
                                    if ((1073741824 & i17) != 0) {
                                        i18 |= 8;
                                    }
                                    i16 = i17 & (-1476395009);
                                    parseInt = i18;
                                } else {
                                    java.lang.String attributeValue15 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "system");
                                    if (attributeValue15 != null) {
                                        try {
                                            i16 = ("true".equalsIgnoreCase(attributeValue15) ? 1 : 0) | 0;
                                            parseInt = 0;
                                        } catch (java.lang.NumberFormatException e28) {
                                            i3 = parseAppId;
                                            str43 = attributeValue;
                                            str = "Error in package manager settings: package ";
                                            i4 = attributeInt;
                                            str3 = "true";
                                            f = attributeFloat;
                                            str5 = attributeValue4;
                                            i = attributeInt2;
                                            str8 = null;
                                            j = 0;
                                            j3 = 0;
                                            java.lang.StringBuilder sb32222222222222 = new java.lang.StringBuilder();
                                            sb32222222222222.append(str);
                                            sb32222222222222.append(str43);
                                            sb32222222222222.append(str2);
                                            sb32222222222222.append(i3);
                                            java.lang.String str442222222222222 = str4;
                                            sb32222222222222.append(str442222222222222);
                                            sb32222222222222.append(typedXmlPullParser.getPositionDescription());
                                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32222222222222.toString());
                                            i7 = i;
                                            f2 = f;
                                            str17 = str7;
                                            i8 = i4;
                                            z6 = z;
                                            z7 = z2;
                                            j4 = j2;
                                            str18 = str9;
                                            str19 = str10;
                                            str20 = str11;
                                            str21 = str12;
                                            i9 = i5;
                                            z8 = z3;
                                            str22 = str13;
                                            str23 = str14;
                                            z9 = z4;
                                            str24 = str15;
                                            i10 = i2;
                                            str25 = str16;
                                            z10 = z5;
                                            str26 = str442222222222222;
                                            str27 = str43;
                                            r5 = str8;
                                            i11 = i6;
                                            if (r5 == 0) {
                                            }
                                        }
                                    } else {
                                        parseInt = 0;
                                        i16 = 1;
                                    }
                                }
                            }
                            try {
                                long attributeLongHex2 = typedXmlPullParser.getAttributeLongHex((java.lang.String) null, "ft", 0L);
                                attributeLong2 = attributeLongHex2 == 0 ? typedXmlPullParser.getAttributeLong((java.lang.String) null, "ts", 0L) : attributeLongHex2;
                                i4 = attributeInt;
                                str3 = "true";
                                try {
                                    j3 = typedXmlPullParser.getAttributeLongHex((java.lang.String) null, "it", 0L);
                                    try {
                                        attributeLongHex = typedXmlPullParser.getAttributeLongHex((java.lang.String) null, "ut", 0L);
                                        if (attributeValue2 != null) {
                                            try {
                                                intern = attributeValue2.intern();
                                            } catch (java.lang.NumberFormatException e29) {
                                                i3 = parseAppId;
                                                str43 = attributeValue;
                                                str8 = null;
                                                j = 0;
                                                str = "Error in package manager settings: package ";
                                                f = attributeFloat;
                                                str5 = attributeValue4;
                                            }
                                        } else {
                                            intern = attributeValue2;
                                        }
                                    } catch (java.lang.NumberFormatException e30) {
                                        str33 = attributeValue;
                                        j = 0;
                                        str = "Error in package manager settings: package ";
                                        str5 = attributeValue4;
                                    }
                                } catch (java.lang.NumberFormatException e31) {
                                    str32 = attributeValue;
                                    str = "Error in package manager settings: package ";
                                    j = 0;
                                    str5 = attributeValue4;
                                    i3 = parseAppId;
                                    str43 = str32;
                                    j3 = j;
                                    f = attributeFloat;
                                    i = attributeInt2;
                                    str8 = null;
                                    java.lang.StringBuilder sb322222222222222 = new java.lang.StringBuilder();
                                    sb322222222222222.append(str);
                                    sb322222222222222.append(str43);
                                    sb322222222222222.append(str2);
                                    sb322222222222222.append(i3);
                                    java.lang.String str4422222222222222 = str4;
                                    sb322222222222222.append(str4422222222222222);
                                    sb322222222222222.append(typedXmlPullParser.getPositionDescription());
                                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322222222222222.toString());
                                    i7 = i;
                                    f2 = f;
                                    str17 = str7;
                                    i8 = i4;
                                    z6 = z;
                                    z7 = z2;
                                    j4 = j2;
                                    str18 = str9;
                                    str19 = str10;
                                    str20 = str11;
                                    str21 = str12;
                                    i9 = i5;
                                    z8 = z3;
                                    str22 = str13;
                                    str23 = str14;
                                    z9 = z4;
                                    str24 = str15;
                                    i10 = i2;
                                    str25 = str16;
                                    z10 = z5;
                                    str26 = str4422222222222222;
                                    str27 = str43;
                                    r5 = str8;
                                    i11 = i6;
                                    if (r5 == 0) {
                                    }
                                }
                            } catch (java.lang.NumberFormatException e32) {
                                j = 0;
                                str32 = attributeValue;
                                str = "Error in package manager settings: package ";
                                i4 = attributeInt;
                                str3 = "true";
                            }
                        } catch (java.lang.NumberFormatException e33) {
                            str32 = attributeValue;
                            str = "Error in package manager settings: package ";
                            i4 = attributeInt;
                            str3 = "true";
                            str5 = attributeValue4;
                            j = 0;
                        }
                    } catch (java.lang.NumberFormatException e34) {
                        str = "Error in package manager settings: package ";
                        i4 = attributeInt;
                        str3 = "true";
                        str5 = attributeValue4;
                        j = 0;
                        i3 = parseAppId;
                        str43 = attributeValue;
                        j3 = 0;
                        f = attributeFloat;
                        i = attributeInt2;
                        str8 = null;
                        z5 = false;
                        java.lang.StringBuilder sb3222222222222222 = new java.lang.StringBuilder();
                        sb3222222222222222.append(str);
                        sb3222222222222222.append(str43);
                        sb3222222222222222.append(str2);
                        sb3222222222222222.append(i3);
                        java.lang.String str44222222222222222 = str4;
                        sb3222222222222222.append(str44222222222222222);
                        sb3222222222222222.append(typedXmlPullParser.getPositionDescription());
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222222222222222.toString());
                        i7 = i;
                        f2 = f;
                        str17 = str7;
                        i8 = i4;
                        z6 = z;
                        z7 = z2;
                        j4 = j2;
                        str18 = str9;
                        str19 = str10;
                        str20 = str11;
                        str21 = str12;
                        i9 = i5;
                        z8 = z3;
                        str22 = str13;
                        str23 = str14;
                        z9 = z4;
                        str24 = str15;
                        i10 = i2;
                        str25 = str16;
                        z10 = z5;
                        str26 = str44222222222222222;
                        str27 = str43;
                        r5 = str8;
                        i11 = i6;
                        if (r5 == 0) {
                        }
                    }
                } catch (java.lang.NumberFormatException e35) {
                    str = "Error in package manager settings: package ";
                    i4 = attributeInt;
                    str3 = "true";
                    str5 = attributeValue4;
                    j = 0;
                    i3 = parseAppId;
                    str43 = attributeValue;
                    j3 = 0;
                    f = attributeFloat;
                    i = attributeInt2;
                    str8 = null;
                    i6 = 0;
                    z5 = false;
                    java.lang.StringBuilder sb32222222222222222 = new java.lang.StringBuilder();
                    sb32222222222222222.append(str);
                    sb32222222222222222.append(str43);
                    sb32222222222222222.append(str2);
                    sb32222222222222222.append(i3);
                    java.lang.String str442222222222222222 = str4;
                    sb32222222222222222.append(str442222222222222222);
                    sb32222222222222222.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32222222222222222.toString());
                    i7 = i;
                    f2 = f;
                    str17 = str7;
                    i8 = i4;
                    z6 = z;
                    z7 = z2;
                    j4 = j2;
                    str18 = str9;
                    str19 = str10;
                    str20 = str11;
                    str21 = str12;
                    i9 = i5;
                    z8 = z3;
                    str22 = str13;
                    str23 = str14;
                    z9 = z4;
                    str24 = str15;
                    i10 = i2;
                    str25 = str16;
                    z10 = z5;
                    str26 = str442222222222222222;
                    str27 = str43;
                    r5 = str8;
                    i11 = i6;
                    if (r5 == 0) {
                    }
                }
            } catch (java.lang.NumberFormatException e36) {
                str = "Error in package manager settings: package ";
                i4 = attributeInt;
                str3 = "true";
                str5 = attributeValue4;
                j = 0;
                i2 = -1;
                str43 = attributeValue;
                j3 = 0;
                f = attributeFloat;
                str8 = null;
                z4 = false;
                str15 = null;
                str16 = null;
                i6 = 0;
                z5 = false;
                i3 = parseAppId;
                i = attributeInt2;
                java.lang.StringBuilder sb322222222222222222 = new java.lang.StringBuilder();
                sb322222222222222222.append(str);
                sb322222222222222222.append(str43);
                sb322222222222222222.append(str2);
                sb322222222222222222.append(i3);
                java.lang.String str4422222222222222222 = str4;
                sb322222222222222222.append(str4422222222222222222);
                sb322222222222222222.append(typedXmlPullParser.getPositionDescription());
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322222222222222222.toString());
                i7 = i;
                f2 = f;
                str17 = str7;
                i8 = i4;
                z6 = z;
                z7 = z2;
                j4 = j2;
                str18 = str9;
                str19 = str10;
                str20 = str11;
                str21 = str12;
                i9 = i5;
                z8 = z3;
                str22 = str13;
                str23 = str14;
                z9 = z4;
                str24 = str15;
                i10 = i2;
                str25 = str16;
                z10 = z5;
                str26 = str4422222222222222222;
                str27 = str43;
                r5 = str8;
                i11 = i6;
                if (r5 == 0) {
                }
            }
        } catch (java.lang.NumberFormatException e37) {
            str = "Error in package manager settings: package ";
            i4 = attributeInt;
            str3 = "true";
            str5 = attributeValue4;
            j = 0;
            i2 = -1;
            str43 = attributeValue;
            j3 = 0;
            f = attributeFloat;
            str8 = null;
            i5 = 0;
            z3 = false;
            str13 = null;
            str14 = null;
            z4 = false;
            str15 = null;
            str16 = null;
            i6 = 0;
            z5 = false;
            i3 = parseAppId;
            i = attributeInt2;
            java.lang.StringBuilder sb3222222222222222222 = new java.lang.StringBuilder();
            sb3222222222222222222.append(str);
            sb3222222222222222222.append(str43);
            sb3222222222222222222.append(str2);
            sb3222222222222222222.append(i3);
            java.lang.String str44222222222222222222 = str4;
            sb3222222222222222222.append(str44222222222222222222);
            sb3222222222222222222.append(typedXmlPullParser.getPositionDescription());
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222222222222222222.toString());
            i7 = i;
            f2 = f;
            str17 = str7;
            i8 = i4;
            z6 = z;
            z7 = z2;
            j4 = j2;
            str18 = str9;
            str19 = str10;
            str20 = str11;
            str21 = str12;
            i9 = i5;
            z8 = z3;
            str22 = str13;
            str23 = str14;
            z9 = z4;
            str24 = str15;
            i10 = i2;
            str25 = str16;
            z10 = z5;
            str26 = str44222222222222222222;
            str27 = str43;
            r5 = str8;
            i11 = i6;
            if (r5 == 0) {
            }
        }
        if (attributeValue == null) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <package> has no name at " + typedXmlPullParser.getPositionDescription());
            str39 = "Error in package manager settings: package ";
            str40 = str4;
            str41 = attributeValue4;
        } else if (attributeValue3 == null) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <package> has no codePath at " + typedXmlPullParser.getPositionDescription());
            str39 = "Error in package manager settings: package ";
            str40 = str4;
            str41 = attributeValue4;
        } else {
            if (parseAppId > 0) {
                j5 = attributeLongHex;
                str34 = attributeValue5;
                str35 = "Error in package manager settings: package ";
                str36 = str2;
                str37 = str4;
                str38 = attributeValue4;
                c = 5;
            } else if (parseAppId == -1 && attributeBoolean && android.content.pm.Flags.disallowSdkLibsToBeApps()) {
                j5 = attributeLongHex;
                str34 = attributeValue5;
                str35 = "Error in package manager settings: package ";
                str36 = str2;
                str37 = str4;
                str38 = attributeValue4;
                c = 5;
            } else if (parseSharedUserAppId != 0) {
                if (parseSharedUserAppId > 0) {
                    try {
                        str42 = attributeValue4;
                        try {
                            ?? lastUpdateTime = new com.android.server.pm.PackageSetting(attributeValue.intern(), intern, new java.io.File(attributeValue3), i16, parseInt, generateNewId).setLegacyNativeLibraryPath(str7).setPrimaryCpuAbi(str6).setSecondaryCpuAbi(str42).setCpuAbiOverride(attributeValue5).setLongVersionCode(attributeLong).setSharedUserAppId(parseSharedUserAppId).setLastModifiedTime(attributeLong2).setLastUpdateTime(attributeLongHex);
                            try {
                                this.mPendingPackages.add(lastUpdateTime);
                                addPackageLPw = lastUpdateTime;
                                str5 = str42;
                                str33 = attributeValue;
                                str = "Error in package manager settings: package ";
                                j = 0;
                                str17 = str7;
                                i8 = i4;
                                z6 = z;
                                z7 = z2;
                                f2 = attributeFloat;
                                j4 = j2;
                                str18 = str9;
                                i7 = attributeInt2;
                                str21 = str12;
                                i9 = i5;
                                z8 = z3;
                                str22 = str13;
                                str23 = str14;
                                z9 = z4;
                                str24 = str15;
                                i10 = i2;
                                str25 = str16;
                                i11 = i6;
                                z10 = z5;
                                str27 = str33;
                                str26 = str4;
                                str19 = str10;
                                str20 = str11;
                                r5 = addPackageLPw;
                            } catch (java.lang.NumberFormatException e38) {
                                str8 = lastUpdateTime;
                                str5 = str42;
                                str43 = attributeValue;
                                str = "Error in package manager settings: package ";
                                f = attributeFloat;
                            }
                        } catch (java.lang.NumberFormatException e39) {
                            str5 = str42;
                            str43 = attributeValue;
                            str = "Error in package manager settings: package ";
                            f = attributeFloat;
                            str8 = null;
                            j = 0;
                            i3 = parseAppId;
                            i = attributeInt2;
                            java.lang.StringBuilder sb32222222222222222222 = new java.lang.StringBuilder();
                            sb32222222222222222222.append(str);
                            sb32222222222222222222.append(str43);
                            sb32222222222222222222.append(str2);
                            sb32222222222222222222.append(i3);
                            java.lang.String str442222222222222222222 = str4;
                            sb32222222222222222222.append(str442222222222222222222);
                            sb32222222222222222222.append(typedXmlPullParser.getPositionDescription());
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32222222222222222222.toString());
                            i7 = i;
                            f2 = f;
                            str17 = str7;
                            i8 = i4;
                            z6 = z;
                            z7 = z2;
                            j4 = j2;
                            str18 = str9;
                            str19 = str10;
                            str20 = str11;
                            str21 = str12;
                            i9 = i5;
                            z8 = z3;
                            str22 = str13;
                            str23 = str14;
                            z9 = z4;
                            str24 = str15;
                            i10 = i2;
                            str25 = str16;
                            z10 = z5;
                            str26 = str442222222222222222222;
                            str27 = str43;
                            r5 = str8;
                            i11 = i6;
                            if (r5 == 0) {
                            }
                        }
                    } catch (java.lang.NumberFormatException e40) {
                        str42 = attributeValue4;
                    }
                    if (r5 == 0) {
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                        return;
                    }
                    java.lang.String str45 = str;
                    com.android.server.pm.InstallSource create = com.android.server.pm.InstallSource.create(str23, str22, str18, i7, str21, str20, i9, z8, z9);
                    r5.setInstallSource(create).setVolumeUuid(str24).setCategoryOverride(i10).setLegacyNativeLibraryPath(str17).setPrimaryCpuAbi(str6).setSecondaryCpuAbi(str5).setUpdateAvailable(z6).setForceQueryableOverride(z7).setLoadingProgress(f2).setLoadingCompletedTime(j4).setAppMetadataFilePath(str25).setAppMetadataSource(i11).setTargetSdkVersion(i8).setRestrictUpdateHash(str19).setScannedAsStoppedSystemApp(z10);
                    java.lang.String attributeValue16 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "enabled");
                    if (attributeValue16 != null) {
                        try {
                            i12 = 0;
                        } catch (java.lang.NumberFormatException e41) {
                            i12 = 0;
                        }
                        try {
                            r5.setEnabled(java.lang.Integer.parseInt(attributeValue16), 0, "settings");
                            r6 = 1;
                        } catch (java.lang.NumberFormatException e42) {
                            if (attributeValue16.equalsIgnoreCase(str3)) {
                                r6 = 1;
                                r5.setEnabled(1, i12, "settings");
                            } else {
                                r6 = 1;
                                r6 = 1;
                                r6 = 1;
                                if (attributeValue16.equalsIgnoreCase("false")) {
                                    r5.setEnabled(2, i12, "settings");
                                } else if (attributeValue16.equalsIgnoreCase("default")) {
                                    r5.setEnabled(i12, i12, "settings");
                                } else {
                                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, str45 + str27 + " has bad enabled value: " + attributeValue16 + str26 + typedXmlPullParser.getPositionDescription());
                                }
                            }
                            addInstallerPackageNames(create);
                            int depth = typedXmlPullParser.getDepth();
                            while (true) {
                                next = typedXmlPullParser.next();
                                if (next == r6) {
                                    break;
                                } else {
                                    break;
                                }
                            }
                            if (j3 == j) {
                            }
                        }
                    } else {
                        i12 = 0;
                        r6 = 1;
                        r5.setEnabled(0, 0, "settings");
                    }
                    addInstallerPackageNames(create);
                    int depth2 = typedXmlPullParser.getDepth();
                    while (true) {
                        next = typedXmlPullParser.next();
                        if (next == r6 || (next == 3 && typedXmlPullParser.getDepth() <= depth2)) {
                            break;
                        }
                        if (next != 3 && next != 4) {
                            java.lang.String name = typedXmlPullParser.getName();
                            if (name.equals(TAG_DISABLED_COMPONENTS)) {
                                readDisabledComponentsLPw(r5, typedXmlPullParser, i12);
                            } else if (name.equals(TAG_ENABLED_COMPONENTS)) {
                                readEnabledComponentsLPw(r5, typedXmlPullParser, i12);
                            } else if (name.equals("sigs")) {
                                r5.getSignatures().readXml(typedXmlPullParser, arrayList);
                            } else if (name.equals(TAG_PERMISSIONS)) {
                                if (r5.hasSharedUser()) {
                                    com.android.server.pm.SettingBase settingLPr = getSettingLPr(r5.getSharedUserAppId());
                                    legacyPermissionState = settingLPr != null ? settingLPr.getLegacyPermissionState() : null;
                                } else {
                                    legacyPermissionState = r5.getLegacyPermissionState();
                                }
                                if (legacyPermissionState != null) {
                                    readInstallPermissionsLPr(typedXmlPullParser, legacyPermissionState, list);
                                    r5.setInstallPermissionsFixed(r6);
                                }
                            } else if (name.equals("proper-signing-keyset")) {
                                long attributeLong3 = typedXmlPullParser.getAttributeLong((java.lang.String) null, "identifier");
                                java.lang.Integer num = arrayMap.get(java.lang.Long.valueOf(attributeLong3));
                                if (num != null) {
                                    arrayMap.put(java.lang.Long.valueOf(attributeLong3), java.lang.Integer.valueOf(num.intValue() + r6));
                                } else {
                                    arrayMap.put(java.lang.Long.valueOf(attributeLong3), java.lang.Integer.valueOf((int) r6));
                                }
                                r5.getKeySetData().setProperSigningKeySet(attributeLong3);
                            } else if (!name.equals("signing-keyset")) {
                                if (name.equals("upgrade-keyset")) {
                                    r5.getKeySetData().addUpgradeKeySetById(typedXmlPullParser.getAttributeLong((java.lang.String) null, "identifier"));
                                } else if (name.equals("defined-keyset")) {
                                    long attributeLong4 = typedXmlPullParser.getAttributeLong((java.lang.String) null, "identifier");
                                    java.lang.String attributeValue17 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "alias");
                                    java.lang.Integer num2 = arrayMap.get(java.lang.Long.valueOf(attributeLong4));
                                    if (num2 != null) {
                                        arrayMap.put(java.lang.Long.valueOf(attributeLong4), java.lang.Integer.valueOf(num2.intValue() + r6));
                                    } else {
                                        arrayMap.put(java.lang.Long.valueOf(attributeLong4), java.lang.Integer.valueOf((int) r6));
                                    }
                                    r5.getKeySetData().addDefinedKeySet(attributeLong4, attributeValue17);
                                } else if (name.equals("install-initiator-sigs")) {
                                    com.android.server.pm.PackageSignatures packageSignatures = new com.android.server.pm.PackageSignatures();
                                    packageSignatures.readXml(typedXmlPullParser, arrayList);
                                    r5.setInstallSource(r5.getInstallSource().setInitiatingPackageSignatures(packageSignatures));
                                } else if (name.equals(TAG_DOMAIN_VERIFICATION)) {
                                    this.mDomainVerificationManager.addLegacySetting(r5.getPackageName(), new android.content.pm.IntentFilterVerificationInfo(typedXmlPullParser));
                                } else if (name.equals(TAG_MIME_GROUP)) {
                                    android.util.Pair<java.lang.String, java.util.Set<java.lang.String>> readMimeGroupLPw = readMimeGroupLPw(typedXmlPullParser);
                                    if (readMimeGroupLPw != null) {
                                        r5.addMimeTypes((java.lang.String) readMimeGroupLPw.first, (java.util.Set) readMimeGroupLPw.second);
                                    }
                                } else if (name.equals(TAG_USES_STATIC_LIB)) {
                                    readUsesStaticLibLPw(typedXmlPullParser, r5);
                                } else if (name.equals(TAG_USES_SDK_LIB)) {
                                    readUsesSdkLibLPw(typedXmlPullParser, r5);
                                } else {
                                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <package>: " + typedXmlPullParser.getName());
                                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                                }
                            }
                        }
                    }
                    if (j3 == j) {
                        arrayMap2.put(r5.getPackageName(), java.lang.Long.valueOf(j3));
                        return;
                    }
                    return;
                }
                str41 = attributeValue4;
                try {
                    sb2 = new java.lang.StringBuilder();
                    str39 = "Error in package manager settings: package ";
                } catch (java.lang.NumberFormatException e43) {
                    str39 = "Error in package manager settings: package ";
                }
                try {
                    sb2.append(str39);
                    sb2.append(attributeValue);
                    sb2.append(" has bad sharedUserAppId ");
                    sb2.append(parseSharedUserAppId);
                    str40 = str4;
                    try {
                        sb2.append(str40);
                        sb2.append(typedXmlPullParser.getPositionDescription());
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb2.toString());
                    } catch (java.lang.NumberFormatException e44) {
                        str5 = str41;
                        str43 = attributeValue;
                        str = str39;
                        str4 = str40;
                        f = attributeFloat;
                        str8 = null;
                        j = 0;
                        i3 = parseAppId;
                        i = attributeInt2;
                        java.lang.StringBuilder sb322222222222222222222 = new java.lang.StringBuilder();
                        sb322222222222222222222.append(str);
                        sb322222222222222222222.append(str43);
                        sb322222222222222222222.append(str2);
                        sb322222222222222222222.append(i3);
                        java.lang.String str4422222222222222222222 = str4;
                        sb322222222222222222222.append(str4422222222222222222222);
                        sb322222222222222222222.append(typedXmlPullParser.getPositionDescription());
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322222222222222222222.toString());
                        i7 = i;
                        f2 = f;
                        str17 = str7;
                        i8 = i4;
                        z6 = z;
                        z7 = z2;
                        j4 = j2;
                        str18 = str9;
                        str19 = str10;
                        str20 = str11;
                        str21 = str12;
                        i9 = i5;
                        z8 = z3;
                        str22 = str13;
                        str23 = str14;
                        z9 = z4;
                        str24 = str15;
                        i10 = i2;
                        str25 = str16;
                        z10 = z5;
                        str26 = str4422222222222222222222;
                        str27 = str43;
                        r5 = str8;
                        i11 = i6;
                        if (r5 == 0) {
                        }
                    }
                } catch (java.lang.NumberFormatException e45) {
                    str5 = str41;
                    str43 = attributeValue;
                    str = str39;
                    f = attributeFloat;
                    str8 = null;
                    j = 0;
                    i3 = parseAppId;
                    i = attributeInt2;
                    java.lang.StringBuilder sb3222222222222222222222 = new java.lang.StringBuilder();
                    sb3222222222222222222222.append(str);
                    sb3222222222222222222222.append(str43);
                    sb3222222222222222222222.append(str2);
                    sb3222222222222222222222.append(i3);
                    java.lang.String str44222222222222222222222 = str4;
                    sb3222222222222222222222.append(str44222222222222222222222);
                    sb3222222222222222222222.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222222222222222222222.toString());
                    i7 = i;
                    f2 = f;
                    str17 = str7;
                    i8 = i4;
                    z6 = z;
                    z7 = z2;
                    j4 = j2;
                    str18 = str9;
                    str19 = str10;
                    str20 = str11;
                    str21 = str12;
                    i9 = i5;
                    z8 = z3;
                    str22 = str13;
                    str23 = str14;
                    z9 = z4;
                    str24 = str15;
                    i10 = i2;
                    str25 = str16;
                    z10 = z5;
                    str26 = str44222222222222222222222;
                    str27 = str43;
                    r5 = str8;
                    i11 = i6;
                    if (r5 == 0) {
                    }
                }
                f = attributeFloat;
                str8 = null;
                j = 0;
                i3 = parseAppId;
                i = attributeInt2;
                java.lang.StringBuilder sb32222222222222222222222 = new java.lang.StringBuilder();
                sb32222222222222222222222.append(str);
                sb32222222222222222222222.append(str43);
                sb32222222222222222222222.append(str2);
                sb32222222222222222222222.append(i3);
                java.lang.String str442222222222222222222222 = str4;
                sb32222222222222222222222.append(str442222222222222222222222);
                sb32222222222222222222222.append(typedXmlPullParser.getPositionDescription());
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb32222222222222222222222.toString());
                i7 = i;
                f2 = f;
                str17 = str7;
                i8 = i4;
                z6 = z;
                z7 = z2;
                j4 = j2;
                str18 = str9;
                str19 = str10;
                str20 = str11;
                str21 = str12;
                i9 = i5;
                z8 = z3;
                str22 = str13;
                str23 = str14;
                z9 = z4;
                str24 = str15;
                i10 = i2;
                str25 = str16;
                z10 = z5;
                str26 = str442222222222222222222222;
                str27 = str43;
                r5 = str8;
                i11 = i6;
                if (r5 == 0) {
                }
            } else {
                str39 = "Error in package manager settings: package ";
                str40 = str4;
                str41 = attributeValue4;
                try {
                    sb = new java.lang.StringBuilder();
                    sb.append(str39);
                    sb.append(attributeValue);
                    try {
                        sb.append(str2);
                        sb.append(parseAppId);
                        sb.append(str40);
                        sb.append(typedXmlPullParser.getPositionDescription());
                    } catch (java.lang.NumberFormatException e46) {
                    }
                } catch (java.lang.NumberFormatException e47) {
                }
                try {
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb.toString());
                } catch (java.lang.NumberFormatException e48) {
                    str2 = str2;
                    str5 = str41;
                    str43 = attributeValue;
                    str = str39;
                    str4 = str40;
                    f = attributeFloat;
                    str8 = null;
                    j = 0;
                    i3 = parseAppId;
                    i = attributeInt2;
                    java.lang.StringBuilder sb322222222222222222222222 = new java.lang.StringBuilder();
                    sb322222222222222222222222.append(str);
                    sb322222222222222222222222.append(str43);
                    sb322222222222222222222222.append(str2);
                    sb322222222222222222222222.append(i3);
                    java.lang.String str4422222222222222222222222 = str4;
                    sb322222222222222222222222.append(str4422222222222222222222222);
                    sb322222222222222222222222.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb322222222222222222222222.toString());
                    i7 = i;
                    f2 = f;
                    str17 = str7;
                    i8 = i4;
                    z6 = z;
                    z7 = z2;
                    j4 = j2;
                    str18 = str9;
                    str19 = str10;
                    str20 = str11;
                    str21 = str12;
                    i9 = i5;
                    z8 = z3;
                    str22 = str13;
                    str23 = str14;
                    z9 = z4;
                    str24 = str15;
                    i10 = i2;
                    str25 = str16;
                    z10 = z5;
                    str26 = str4422222222222222222222222;
                    str27 = str43;
                    r5 = str8;
                    i11 = i6;
                    if (r5 == 0) {
                    }
                }
            }
            ?? r1 = this;
            j = 0;
            long j6 = j5;
            str4 = str37;
            long j7 = attributeLong2;
            str2 = str36;
            str7 = str7;
            str5 = str38;
            str33 = attributeValue;
            str = str35;
            addPackageLPw = r1.addPackageLPw(attributeValue.intern(), intern, new java.io.File(attributeValue3), parseAppId, i16, parseInt, generateNewId);
            if (addPackageLPw == 0) {
                try {
                    java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
                    sb4.append("Failure adding appId ");
                    int i19 = parseAppId;
                    sb4.append(i19 == true ? 1 : 0);
                    sb4.append(" while parsing settings at ");
                    sb4.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(6, sb4.toString());
                    r1 = i19;
                } catch (java.lang.NumberFormatException e49) {
                    r1 = parseAppId;
                    i3 = r1;
                    str8 = addPackageLPw;
                    str43 = str33;
                    f = attributeFloat;
                    i = attributeInt2;
                    java.lang.StringBuilder sb3222222222222222222222222 = new java.lang.StringBuilder();
                    sb3222222222222222222222222.append(str);
                    sb3222222222222222222222222.append(str43);
                    sb3222222222222222222222222.append(str2);
                    sb3222222222222222222222222.append(i3);
                    java.lang.String str44222222222222222222222222 = str4;
                    sb3222222222222222222222222.append(str44222222222222222222222222);
                    sb3222222222222222222222222.append(typedXmlPullParser.getPositionDescription());
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, sb3222222222222222222222222.toString());
                    i7 = i;
                    f2 = f;
                    str17 = str7;
                    i8 = i4;
                    z6 = z;
                    z7 = z2;
                    j4 = j2;
                    str18 = str9;
                    str19 = str10;
                    str20 = str11;
                    str21 = str12;
                    i9 = i5;
                    z8 = z3;
                    str22 = str13;
                    str23 = str14;
                    z9 = z4;
                    str24 = str15;
                    i10 = i2;
                    str25 = str16;
                    z10 = z5;
                    str26 = str44222222222222222222222222;
                    str27 = str43;
                    r5 = str8;
                    i11 = i6;
                    if (r5 == 0) {
                    }
                }
            } else {
                r1 = parseAppId;
                addPackageLPw.setLegacyNativeLibraryPath(str7);
                addPackageLPw.setPrimaryCpuAbi(str6);
                addPackageLPw.setSecondaryCpuAbi(str5);
                addPackageLPw.setCpuAbiOverride(str34);
                addPackageLPw.setLongVersionCode(attributeLong);
                addPackageLPw.setLastModifiedTime(j7);
                addPackageLPw.setLastUpdateTime(j6);
            }
            str17 = str7;
            i8 = i4;
            z6 = z;
            z7 = z2;
            f2 = attributeFloat;
            j4 = j2;
            str18 = str9;
            i7 = attributeInt2;
            str21 = str12;
            i9 = i5;
            z8 = z3;
            str22 = str13;
            str23 = str14;
            z9 = z4;
            str24 = str15;
            i10 = i2;
            str25 = str16;
            i11 = i6;
            z10 = z5;
            str27 = str33;
            str26 = str4;
            str19 = str10;
            str20 = str11;
            r5 = addPackageLPw;
            if (r5 == 0) {
            }
        }
        str5 = str41;
        str33 = attributeValue;
        str = str39;
        str4 = str40;
        addPackageLPw = 0;
        j = 0;
        str17 = str7;
        i8 = i4;
        z6 = z;
        z7 = z2;
        f2 = attributeFloat;
        j4 = j2;
        str18 = str9;
        i7 = attributeInt2;
        str21 = str12;
        i9 = i5;
        z8 = z3;
        str22 = str13;
        str23 = str14;
        z9 = z4;
        str24 = str15;
        i10 = i2;
        str25 = str16;
        i11 = i6;
        z10 = z5;
        str27 = str33;
        str26 = str4;
        str19 = str10;
        str20 = str11;
        r5 = addPackageLPw;
        if (r5 == 0) {
        }
    }

    private static int parseAppId(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        return typedXmlPullParser.getAttributeInt((java.lang.String) null, "userId", 0);
    }

    private static int parseSharedUserAppId(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        return typedXmlPullParser.getAttributeInt((java.lang.String) null, "sharedUserId", 0);
    }

    void addInstallerPackageNames(com.android.server.pm.InstallSource installSource) {
        if (installSource.mInstallerPackageName != null) {
            this.mInstallerPackages.add(installSource.mInstallerPackageName);
        }
        if (installSource.mInitiatingPackageName != null) {
            this.mInstallerPackages.add(installSource.mInitiatingPackageName);
        }
        if (installSource.mOriginatingPackageName != null) {
            this.mInstallerPackages.add(installSource.mOriginatingPackageName);
        }
    }

    @android.annotation.Nullable
    private android.util.Pair<java.lang.String, java.util.Set<java.lang.String>> readMimeGroupLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        if (attributeValue == null) {
            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            return null;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals(TAG_MIME_TYPE)) {
                    java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    if (attributeValue2 != null) {
                        arraySet.add(attributeValue2);
                    }
                } else {
                    com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <mime-group>: " + typedXmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        return android.util.Pair.create(attributeValue, arraySet);
    }

    private void writeMimeGroupLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.util.Map<java.lang.String, java.util.Set<java.lang.String>> map) throws java.io.IOException {
        if (map == null) {
            return;
        }
        for (java.lang.String str : map.keySet()) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_MIME_GROUP);
            typedXmlSerializer.attribute((java.lang.String) null, "name", str);
            for (java.lang.String str2 : map.get(str)) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_MIME_TYPE);
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_VALUE, str2);
                typedXmlSerializer.endTag((java.lang.String) null, TAG_MIME_TYPE);
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_MIME_GROUP);
        }
    }

    private void readDisabledComponentsLPw(com.android.server.pm.PackageSetting packageSetting, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(TAG_ITEM)) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                        if (attributeValue != null) {
                            packageSetting.addDisabledComponent(attributeValue.intern(), i);
                        } else {
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <disabled-components> has no name at " + typedXmlPullParser.getPositionDescription());
                        }
                    } else {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <disabled-components>: " + typedXmlPullParser.getName());
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            } else {
                return;
            }
        }
    }

    private void readEnabledComponentsLPw(com.android.server.pm.PackageSetting packageSetting, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(TAG_ITEM)) {
                        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                        if (attributeValue != null) {
                            packageSetting.addEnabledComponent(attributeValue.intern(), i);
                        } else {
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <enabled-components> has no name at " + typedXmlPullParser.getPositionDescription());
                        }
                    } else {
                        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <enabled-components>: " + typedXmlPullParser.getName());
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            } else {
                return;
            }
        }
    }

    private void readSharedUserLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.ArrayList<android.content.pm.Signature> arrayList, java.util.List<android.content.pm.UserInfo> list) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        com.android.server.pm.SharedUserSetting sharedUserSetting = null;
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        int parseAppId = parseAppId(typedXmlPullParser);
        if (!typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "system", false)) {
            i = 0;
        } else {
            i = 1;
        }
        if (attributeValue == null) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: <shared-user> has no name at " + typedXmlPullParser.getPositionDescription());
        } else if (parseAppId == 0) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: shared-user " + attributeValue + " has bad appId " + parseAppId + " at " + typedXmlPullParser.getPositionDescription());
        } else {
            sharedUserSetting = addSharedUserLPw(attributeValue.intern(), parseAppId, i, 0);
            if (sharedUserSetting == null) {
                com.android.server.pm.PackageManagerService.reportSettingsProblem(6, "Occurred while parsing settings at " + typedXmlPullParser.getPositionDescription());
            }
        }
        if (sharedUserSetting != null) {
            int depth = typedXmlPullParser.getDepth();
            while (true) {
                int next = typedXmlPullParser.next();
                if (next != 1) {
                    if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                        return;
                    }
                    if (next != 3 && next != 4) {
                        java.lang.String name = typedXmlPullParser.getName();
                        if (name.equals("sigs")) {
                            sharedUserSetting.signatures.readXml(typedXmlPullParser, arrayList);
                        } else if (name.equals(TAG_PERMISSIONS)) {
                            readInstallPermissionsLPr(typedXmlPullParser, sharedUserSetting.getLegacyPermissionState(), list);
                        } else {
                            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <shared-user>: " + typedXmlPullParser.getName());
                            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                        }
                    }
                } else {
                    return;
                }
            }
        } else {
            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00c4 A[Catch: all -> 0x007a, TryCatch #2 {all -> 0x007a, blocks: (B:7:0x003c, B:9:0x0045, B:14:0x005c, B:16:0x0062, B:18:0x006e, B:23:0x0088, B:26:0x0095, B:29:0x00a0, B:30:0x00a3, B:32:0x00a7, B:34:0x00ad, B:36:0x00b3, B:40:0x00c4, B:43:0x00f7, B:47:0x0101, B:49:0x0106, B:53:0x0114, B:56:0x0129), top: B:6:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00fc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0106 A[Catch: all -> 0x007a, TryCatch #2 {all -> 0x007a, blocks: (B:7:0x003c, B:9:0x0045, B:14:0x005c, B:16:0x0062, B:18:0x006e, B:23:0x0088, B:26:0x0095, B:29:0x00a0, B:30:0x00a3, B:32:0x00a7, B:34:0x00ad, B:36:0x00b3, B:40:0x00c4, B:43:0x00f7, B:47:0x0101, B:49:0x0106, B:53:0x0114, B:56:0x0129), top: B:6:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x015d A[Catch: all -> 0x015b, TryCatch #3 {all -> 0x015b, blocks: (B:13:0x0167, B:59:0x0153, B:63:0x01a7, B:68:0x015d, B:80:0x0175), top: B:58:0x0153 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void createNewUserLI(@android.annotation.NonNull com.android.server.pm.PackageManagerService packageManagerService, @android.annotation.NonNull com.android.server.pm.Installer installer, int i, @android.annotation.Nullable java.util.Set<java.lang.String> set, java.lang.String[] strArr) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i2;
        int i3;
        long j;
        com.android.server.pm.PackageManagerService packageManagerService2 = packageManagerService;
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog("PackageSettingsTiming", 262144L);
        timingsTraceAndSlog.traceBegin("createNewUser-" + i);
        com.android.server.pm.Installer.Batch batch = new com.android.server.pm.Installer.Batch();
        boolean z6 = set == null;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                int size = this.mPackages.size();
                int i4 = 0;
                while (i4 < size) {
                    com.android.server.pm.PackageSetting valueAt = this.mPackages.valueAt(i4);
                    if (valueAt.getPkg() == null) {
                        i2 = i4;
                        i3 = size;
                        packageManagerTracedLock = packageManagerTracedLock2;
                        j = currentTimeMillis;
                    } else {
                        if (valueAt.isSystem() && !com.android.internal.util.ArrayUtils.contains(strArr, valueAt.getPackageName()) && !valueAt.getPkgState().isHiddenUntilInstalled()) {
                            z = true;
                            z2 = !z && (z6 || set.contains(valueAt.getPackageName()));
                            valueAt.setInstalled(z2, i);
                            if (android.content.pm.Flags.fixSystemAppsFirstInstallTime() && z2) {
                                valueAt.setFirstInstallTime(currentTimeMillis, i);
                            }
                            z3 = (packageManagerService2.mShouldStopSystemPackagesByDefault || !valueAt.isSystem() || valueAt.isApex() || packageManagerService2.mInitialNonStoppedSystemPackages.contains(valueAt.getPackageName())) ? false : true;
                            if (z3) {
                                z4 = z3;
                            } else {
                                z4 = z3;
                                android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
                                intent.addCategory("android.intent.category.LAUNCHER");
                                intent.setPackage(valueAt.getPackageName());
                                if (packageManagerService.snapshotComputer().queryIntentActivitiesInternal(intent, null, 786432L, 0).isEmpty()) {
                                    z5 = false;
                                    valueAt.setStopped(z5, i);
                                    valueAt.setUninstallReason((z || z2) ? 0 : 1, i);
                                    if (!z2) {
                                        i2 = i4;
                                        i3 = size;
                                        packageManagerTracedLock = packageManagerTracedLock2;
                                        j = currentTimeMillis;
                                        writeKernelMappingLPr(valueAt);
                                    } else if (valueAt.getAppId() < 0) {
                                        i2 = i4;
                                        i3 = size;
                                        packageManagerTracedLock = packageManagerTracedLock2;
                                        j = currentTimeMillis;
                                    } else {
                                        i2 = i4;
                                        i3 = size;
                                        packageManagerTracedLock = packageManagerTracedLock2;
                                        j = currentTimeMillis;
                                        try {
                                            batch.createAppData(com.android.server.pm.Installer.buildCreateAppDataArgs(valueAt.getVolumeUuid(), valueAt.getPackageName(), i, 1, valueAt.getAppId(), valueAt.getSeInfo(), valueAt.getPkg().getTargetSdkVersion(), !valueAt.getPkg().getUsesSdkLibraries().isEmpty()));
                                        } catch (java.lang.Throwable th) {
                                            th = th;
                                            throw th;
                                        }
                                    }
                                }
                            }
                            z5 = z4;
                            valueAt.setStopped(z5, i);
                            valueAt.setUninstallReason((z || z2) ? 0 : 1, i);
                            if (!z2) {
                            }
                        }
                        z = false;
                        if (z) {
                        }
                        valueAt.setInstalled(z2, i);
                        if (android.content.pm.Flags.fixSystemAppsFirstInstallTime()) {
                            valueAt.setFirstInstallTime(currentTimeMillis, i);
                        }
                        if (packageManagerService2.mShouldStopSystemPackagesByDefault) {
                        }
                        if (z3) {
                        }
                        z5 = z4;
                        valueAt.setStopped(z5, i);
                        valueAt.setUninstallReason((z || z2) ? 0 : 1, i);
                        if (!z2) {
                        }
                    }
                    i4 = i2 + 1;
                    packageManagerService2 = packageManagerService;
                    packageManagerTracedLock2 = packageManagerTracedLock;
                    size = i3;
                    currentTimeMillis = j;
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                timingsTraceAndSlog.traceBegin("createAppData");
                try {
                    batch.execute(installer);
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    android.util.Slog.w(TAG, "Failed to prepare app data", e);
                }
                timingsTraceAndSlog.traceEnd();
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock3) {
                    try {
                        applyDefaultPreferredAppsLPw(i);
                    } finally {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                timingsTraceAndSlog.traceEnd();
            } catch (java.lang.Throwable th2) {
                th = th2;
                packageManagerTracedLock = packageManagerTracedLock2;
            }
        }
    }

    void removeUserLPw(int i) {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, com.android.server.pm.PackageSetting>> it = this.mPackages.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().removeUser(i);
        }
        this.mPreferredActivities.remove(i);
        synchronized (this.mPackageRestrictionsLock) {
            getUserPackagesStateFile(i).delete();
            this.mPendingAsyncPackageRestrictionsWrites.delete(i);
        }
        removeCrossProfileIntentFiltersLPw(i);
        this.mRuntimePermissionsPersistence.onUserRemoved(i);
        this.mDomainVerificationManager.clearUser(i);
        writePackageListLPr();
        writeKernelRemoveUserLPr(i);
    }

    void removeCrossProfileIntentFiltersLPw(int i) {
        synchronized (this.mCrossProfileIntentResolvers) {
            try {
                if (this.mCrossProfileIntentResolvers.get(i) != null) {
                    this.mCrossProfileIntentResolvers.remove(i);
                    writePackageRestrictionsLPr(i);
                }
                int size = this.mCrossProfileIntentResolvers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mCrossProfileIntentResolvers.keyAt(i2);
                    com.android.server.pm.CrossProfileIntentResolver crossProfileIntentResolver = this.mCrossProfileIntentResolvers.get(keyAt);
                    java.util.Iterator it = new android.util.ArraySet(crossProfileIntentResolver.filterSet()).iterator();
                    boolean z = false;
                    while (it.hasNext()) {
                        com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = (com.android.server.pm.CrossProfileIntentFilter) it.next();
                        if (crossProfileIntentFilter.getTargetUserId() == i) {
                            crossProfileIntentResolver.removeFilter((com.android.server.pm.CrossProfileIntentResolver) crossProfileIntentFilter);
                            z = true;
                        }
                    }
                    if (z) {
                        writePackageRestrictionsLPr(keyAt);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.content.pm.VerifierDeviceIdentity getVerifierDeviceIdentityLPw(@android.annotation.NonNull com.android.server.pm.Computer computer) {
        if (this.mVerifierDeviceIdentity == null) {
            this.mVerifierDeviceIdentity = android.content.pm.VerifierDeviceIdentity.generate();
            writeLPr(computer, false);
        }
        return this.mVerifierDeviceIdentity;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getDisabledSystemPkgLPr(java.lang.String str) {
        return this.mDisabledSysPackages.get(str);
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getDisabledSystemPkgLPr(com.android.server.pm.PackageSetting packageSetting) {
        if (packageSetting == null) {
            return null;
        }
        return getDisabledSystemPkgLPr(packageSetting.getPackageName());
    }

    int getApplicationEnabledSettingLPr(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(str);
        if (packageSetting == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
        return packageSetting.getEnabled(i);
    }

    int getComponentEnabledSettingLPr(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(componentName.getPackageName());
        if (packageSetting == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(componentName.getPackageName());
        }
        return packageSetting.getCurrentEnabledStateLPr(componentName.getClassName(), i);
    }

    com.android.server.pm.SharedUserSetting getSharedUserSettingLPr(java.lang.String str) {
        return getSharedUserSettingLPr(this.mPackages.get(str));
    }

    @android.annotation.Nullable
    com.android.server.pm.SharedUserSetting getSharedUserSettingLPr(com.android.server.pm.PackageSetting packageSetting) {
        if (packageSetting == null || !packageSetting.hasSharedUser()) {
            return null;
        }
        return (com.android.server.pm.SharedUserSetting) getSettingLPr(packageSetting.getSharedUserAppId());
    }

    private static java.util.List<android.content.pm.UserInfo> getAllUsers(com.android.server.pm.UserManagerService userManagerService) {
        return getUsers(userManagerService, false, false);
    }

    private static java.util.List<android.content.pm.UserInfo> getActiveUsers(com.android.server.pm.UserManagerService userManagerService, boolean z) {
        return getUsers(userManagerService, z, true);
    }

    private static java.util.List<android.content.pm.UserInfo> getUsers(com.android.server.pm.UserManagerService userManagerService, boolean z, boolean z2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.List<android.content.pm.UserInfo> users = userManagerService.getUsers(true, z, z2);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return users;
        } catch (java.lang.NullPointerException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    java.util.List<? extends com.android.server.pm.pkg.PackageStateInternal> getVolumePackagesLPr(java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mPackages.size(); i++) {
            com.android.server.pm.PackageSetting valueAt = this.mPackages.valueAt(i);
            if (java.util.Objects.equals(str, valueAt.getVolumeUuid())) {
                arrayList.add(valueAt);
            }
        }
        return arrayList;
    }

    static void printFlags(java.io.PrintWriter printWriter, int i, java.lang.Object[] objArr) {
        printWriter.print("[ ");
        for (int i2 = 0; i2 < objArr.length; i2 += 2) {
            if ((((java.lang.Integer) objArr[i2]).intValue() & i) != 0) {
                printWriter.print(objArr[i2 + 1]);
                printWriter.print(" ");
            }
        }
        printWriter.print("]");
    }

    void dumpVersionLPr(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mVersion.size(); i++) {
            java.lang.String keyAt = this.mVersion.keyAt(i);
            com.android.server.pm.Settings.VersionInfo valueAt = this.mVersion.valueAt(i);
            if (java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, keyAt)) {
                indentingPrintWriter.println("Internal:");
            } else if (java.util.Objects.equals("primary_physical", keyAt)) {
                indentingPrintWriter.println("External:");
            } else {
                indentingPrintWriter.println("UUID " + keyAt + ":");
            }
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.printPair(ATTR_SDK_VERSION, java.lang.Integer.valueOf(valueAt.sdkVersion));
            indentingPrintWriter.printPair(ATTR_DATABASE_VERSION, java.lang.Integer.valueOf(valueAt.databaseVersion));
            indentingPrintWriter.println();
            indentingPrintWriter.printPair(ATTR_BUILD_FINGERPRINT, valueAt.buildFingerprint);
            indentingPrintWriter.printPair(ATTR_FINGERPRINT, valueAt.fingerprint);
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    @dalvik.annotation.optimization.NeverCompile
    void dumpPackageLPr(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.permission.LegacyPermissionState legacyPermissionState, java.text.SimpleDateFormat simpleDateFormat, java.util.Date date, java.util.List<android.content.pm.UserInfo> list, boolean z, boolean z2) {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, android.content.pm.overlay.OverlayPaths>> it;
        boolean z3;
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageSetting.getPkg();
        if (str2 != null) {
            printWriter.print(str2);
            printWriter.print(",");
            printWriter.print(packageSetting.getRealName() != null ? packageSetting.getRealName() : packageSetting.getPackageName());
            printWriter.print(",");
            printWriter.print(packageSetting.getAppId());
            printWriter.print(",");
            printWriter.print(packageSetting.getVersionCode());
            printWriter.print(",");
            printWriter.print(packageSetting.getLastUpdateTime());
            printWriter.print(",");
            printWriter.print(packageSetting.getInstallSource().mInstallerPackageName != null ? packageSetting.getInstallSource().mInstallerPackageName : "?");
            printWriter.print(packageSetting.getInstallSource().mInstallerPackageUid);
            printWriter.print(packageSetting.getInstallSource().mUpdateOwnerPackageName != null ? packageSetting.getInstallSource().mUpdateOwnerPackageName : "?");
            printWriter.print(packageSetting.getInstallSource().mInstallerAttributionTag != null ? "(" + packageSetting.getInstallSource().mInstallerAttributionTag + ")" : "");
            printWriter.print(",");
            printWriter.print(packageSetting.getInstallSource().mPackageSource);
            printWriter.println();
            if (pkg != null) {
                printWriter.print(str2);
                printWriter.print("-");
                printWriter.print("splt,");
                printWriter.print("base,");
                printWriter.println(pkg.getBaseRevisionCode());
                int[] splitRevisionCodes = pkg.getSplitRevisionCodes();
                for (int i = 0; i < pkg.getSplitNames().length; i++) {
                    printWriter.print(str2);
                    printWriter.print("-");
                    printWriter.print("splt,");
                    printWriter.print(pkg.getSplitNames()[i]);
                    printWriter.print(",");
                    printWriter.println(splitRevisionCodes[i]);
                }
            }
            for (android.content.pm.UserInfo userInfo : list) {
                com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageSetting.getUserStateOrDefault(userInfo.id);
                printWriter.print(str2);
                printWriter.print("-");
                printWriter.print("usr");
                printWriter.print(",");
                printWriter.print(userInfo.id);
                printWriter.print(",");
                printWriter.print(userStateOrDefault.isInstalled() ? "I" : "i");
                printWriter.print(userStateOrDefault.isHidden() ? "B" : "b");
                printWriter.print(userStateOrDefault.isSuspended() ? "SU" : "su");
                printWriter.print(userStateOrDefault.isStopped() ? "S" : "s");
                printWriter.print(userStateOrDefault.isNotLaunched() ? "l" : "L");
                printWriter.print(userStateOrDefault.isInstantApp() ? "IA" : "ia");
                printWriter.print(userStateOrDefault.isVirtualPreload() ? "VPI" : "vpi");
                printWriter.print(userStateOrDefault.isQuarantined() ? "Q" : "q");
                printWriter.print(userStateOrDefault.getHarmfulAppWarning() != null ? "HA" : "ha");
                printWriter.print(",");
                printWriter.print(userStateOrDefault.getEnabledState());
                java.lang.String lastDisableAppCaller = userStateOrDefault.getLastDisableAppCaller();
                printWriter.print(",");
                if (lastDisableAppCaller == null) {
                    lastDisableAppCaller = "?";
                }
                printWriter.print(lastDisableAppCaller);
                printWriter.print(",");
                printWriter.print(packageSetting.readUserState(userInfo.id).getFirstInstallTimeMillis());
                printWriter.print(",");
                printWriter.println();
            }
            return;
        }
        printWriter.print(str);
        printWriter.print("Package [");
        printWriter.print(packageSetting.getRealName() != null ? packageSetting.getRealName() : packageSetting.getPackageName());
        printWriter.print("] (");
        printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(packageSetting)));
        printWriter.println("):");
        if (packageSetting.getRealName() != null) {
            printWriter.print(str);
            printWriter.print("  compat name=");
            printWriter.println(packageSetting.getPackageName());
        }
        printWriter.print(str);
        printWriter.print("  appId=");
        printWriter.println(packageSetting.getAppId());
        com.android.server.pm.SharedUserSetting sharedUserSettingLPr = getSharedUserSettingLPr(packageSetting);
        if (sharedUserSettingLPr != null) {
            printWriter.print(str);
            printWriter.print("  sharedUser=");
            printWriter.println(sharedUserSettingLPr);
        }
        printWriter.print(str);
        printWriter.print("  pkg=");
        printWriter.println(pkg);
        printWriter.print(str);
        printWriter.print("  codePath=");
        printWriter.println(packageSetting.getPathString());
        if (packageSetting.getOldPaths() != null && packageSetting.getOldPaths().size() > 0) {
            java.util.Iterator<java.io.File> it2 = packageSetting.getOldPaths().iterator();
            while (it2.hasNext()) {
                java.io.File next = it2.next();
                printWriter.print(str);
                printWriter.println("    oldCodePath=" + next.getAbsolutePath());
            }
        }
        if (arraySet == null) {
            printWriter.print(str);
            printWriter.print("  resourcePath=");
            printWriter.println(packageSetting.getPathString());
            printWriter.print(str);
            printWriter.print("  legacyNativeLibraryDir=");
            printWriter.println(packageSetting.getLegacyNativeLibraryPath());
            printWriter.print(str);
            printWriter.print("  extractNativeLibs=");
            printWriter.println((packageSetting.getFlags() & 268435456) != 0 ? "true" : "false");
            printWriter.print(str);
            printWriter.print("  primaryCpuAbi=");
            printWriter.println(packageSetting.getPrimaryCpuAbiLegacy());
            printWriter.print(str);
            printWriter.print("  secondaryCpuAbi=");
            printWriter.println(packageSetting.getSecondaryCpuAbiLegacy());
            printWriter.print(str);
            printWriter.print("  cpuAbiOverride=");
            printWriter.println(packageSetting.getCpuAbiOverride());
        }
        printWriter.print(str);
        printWriter.print("  versionCode=");
        printWriter.print(packageSetting.getVersionCode());
        if (pkg != null) {
            printWriter.print(" minSdk=");
            printWriter.print(pkg.getMinSdkVersion());
        }
        printWriter.print(" targetSdk=");
        printWriter.println(packageSetting.getTargetSdkVersion());
        if (pkg != null) {
            android.util.SparseIntArray minExtensionVersions = pkg.getMinExtensionVersions();
            printWriter.print(str);
            printWriter.print("  minExtensionVersions=[");
            if (minExtensionVersions != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int size = minExtensionVersions.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.add(minExtensionVersions.keyAt(i2) + "=" + minExtensionVersions.valueAt(i2));
                }
                printWriter.print(android.text.TextUtils.join(", ", arrayList));
            }
            printWriter.print("]");
        }
        printWriter.println();
        if (pkg != null) {
            printWriter.print(str);
            printWriter.print("  versionName=");
            printWriter.println(pkg.getVersionName());
            printWriter.print(str);
            printWriter.print("  hiddenApiEnforcementPolicy=");
            printWriter.println(packageSetting.getHiddenApiEnforcementPolicy());
            printWriter.print(str);
            printWriter.print("  usesNonSdkApi=");
            printWriter.println(pkg.isNonSdkApiRequested());
            printWriter.print(str);
            printWriter.print("  splits=");
            dumpSplitNames(printWriter, pkg);
            printWriter.println();
            int signatureSchemeVersion = pkg.getSigningDetails().getSignatureSchemeVersion();
            printWriter.print(str);
            printWriter.print("  apkSigningVersion=");
            printWriter.println(signatureSchemeVersion);
            printWriter.print(str);
            printWriter.print("  flags=");
            printFlags(printWriter, com.android.server.pm.parsing.PackageInfoUtils.appInfoFlags((com.android.server.pm.pkg.AndroidPackage) pkg, (com.android.server.pm.pkg.PackageStateInternal) packageSetting), FLAG_DUMP_SPEC);
            printWriter.println();
            int appInfoPrivateFlags = com.android.server.pm.parsing.PackageInfoUtils.appInfoPrivateFlags((com.android.server.pm.pkg.AndroidPackage) pkg, (com.android.server.pm.pkg.PackageStateInternal) packageSetting);
            if (appInfoPrivateFlags != 0) {
                printWriter.print(str);
                printWriter.print("  privateFlags=");
                printFlags(printWriter, appInfoPrivateFlags, PRIVATE_FLAG_DUMP_SPEC);
                printWriter.println();
            }
            if (packageSetting.isPendingRestore()) {
                printWriter.print(str);
                printWriter.print("  pendingRestore=true");
                printWriter.println();
            }
            if (!pkg.isUpdatableSystem()) {
                printWriter.print(str);
                printWriter.print("  updatableSystem=false");
                printWriter.println();
            }
            if (pkg.getEmergencyInstaller() != null) {
                printWriter.print(str);
                printWriter.print("  emergencyInstaller=");
                printWriter.println(pkg.getEmergencyInstaller());
            }
            if (pkg.hasPreserveLegacyExternalStorage()) {
                printWriter.print(str);
                printWriter.print("  hasPreserveLegacyExternalStorage=true");
                printWriter.println();
            }
            printWriter.print(str);
            printWriter.print("  forceQueryable=");
            printWriter.print(packageSetting.getPkg().isForceQueryable());
            if (packageSetting.isForceQueryableOverride()) {
                printWriter.print(" (override=true)");
            }
            printWriter.println();
            if (!packageSetting.getPkg().getQueriesPackages().isEmpty()) {
                printWriter.append((java.lang.CharSequence) str).append((java.lang.CharSequence) "  queriesPackages=").println(packageSetting.getPkg().getQueriesPackages());
            }
            if (!packageSetting.getPkg().getQueriesIntents().isEmpty()) {
                printWriter.append((java.lang.CharSequence) str).append((java.lang.CharSequence) "  queriesIntents=").println(packageSetting.getPkg().getQueriesIntents());
            }
            printWriter.print(str);
            printWriter.print("  scannedAsStoppedSystemApp=");
            printWriter.println(packageSetting.isScannedAsStoppedSystemApp());
            printWriter.print(str);
            printWriter.print("  supportsScreens=[");
            if (!pkg.isSmallScreensSupported()) {
                z3 = true;
            } else {
                printWriter.print("small");
                z3 = false;
            }
            if (pkg.isNormalScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("medium");
                z3 = false;
            }
            if (pkg.isLargeScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("large");
                z3 = false;
            }
            if (pkg.isExtraLargeScreensSupported()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("xlarge");
                z3 = false;
            }
            if (pkg.isResizeable()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("resizeable");
                z3 = false;
            }
            if (pkg.isAnyDensity()) {
                if (!z3) {
                    printWriter.print(", ");
                }
                printWriter.print("anyDensity");
            }
            printWriter.println("]");
            java.util.List libraryNames = pkg.getLibraryNames();
            if (libraryNames != null && libraryNames.size() > 0) {
                printWriter.print(str);
                printWriter.println("  dynamic libraries:");
                for (int i3 = 0; i3 < libraryNames.size(); i3++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((java.lang.String) libraryNames.get(i3));
                }
            }
            java.lang.String str3 = " version:";
            if (pkg.getStaticSharedLibraryName() != null) {
                printWriter.print(str);
                printWriter.println("  static library:");
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print("name:");
                printWriter.print(pkg.getStaticSharedLibraryName());
                printWriter.print(" version:");
                printWriter.println(pkg.getStaticSharedLibraryVersion());
            }
            if (pkg.getSdkLibraryName() != null) {
                printWriter.print(str);
                printWriter.println("  SDK library:");
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.print("name:");
                printWriter.print(pkg.getSdkLibraryName());
                printWriter.print(" versionMajor:");
                printWriter.println(pkg.getSdkLibVersionMajor());
            }
            java.util.List usesLibraries = pkg.getUsesLibraries();
            if (usesLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesLibraries:");
                for (int i4 = 0; i4 < usesLibraries.size(); i4++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((java.lang.String) usesLibraries.get(i4));
                }
            }
            java.util.List usesStaticLibraries = pkg.getUsesStaticLibraries();
            long[] usesStaticLibrariesVersions = pkg.getUsesStaticLibrariesVersions();
            if (usesStaticLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesStaticLibraries:");
                for (int i5 = 0; i5 < usesStaticLibraries.size(); i5++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.print((java.lang.String) usesStaticLibraries.get(i5));
                    printWriter.print(" version:");
                    printWriter.println(usesStaticLibrariesVersions[i5]);
                }
            }
            java.util.List usesSdkLibraries = pkg.getUsesSdkLibraries();
            long[] usesSdkLibrariesVersionsMajor = pkg.getUsesSdkLibrariesVersionsMajor();
            boolean[] usesSdkLibrariesOptional = pkg.getUsesSdkLibrariesOptional();
            if (usesSdkLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesSdkLibraries:");
                int size2 = usesSdkLibraries.size();
                int i6 = 0;
                while (i6 < size2) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.print((java.lang.String) usesSdkLibraries.get(i6));
                    printWriter.print(str3);
                    printWriter.println(usesSdkLibrariesVersionsMajor[i6]);
                    printWriter.print(" optional:");
                    printWriter.println(usesSdkLibrariesOptional[i6]);
                    i6++;
                    str3 = str3;
                }
            }
            java.util.List usesOptionalLibraries = pkg.getUsesOptionalLibraries();
            if (usesOptionalLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesOptionalLibraries:");
                for (int i7 = 0; i7 < usesOptionalLibraries.size(); i7++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((java.lang.String) usesOptionalLibraries.get(i7));
                }
            }
            java.util.List usesNativeLibraries = pkg.getUsesNativeLibraries();
            if (usesNativeLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesNativeLibraries:");
                for (int i8 = 0; i8 < usesNativeLibraries.size(); i8++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((java.lang.String) usesNativeLibraries.get(i8));
                }
            }
            java.util.List usesOptionalNativeLibraries = pkg.getUsesOptionalNativeLibraries();
            if (usesOptionalNativeLibraries.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesOptionalNativeLibraries:");
                for (int i9 = 0; i9 < usesOptionalNativeLibraries.size(); i9++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println((java.lang.String) usesOptionalNativeLibraries.get(i9));
                }
            }
            java.util.List<java.lang.String> usesLibraryFiles = packageSetting.getPkgState().getUsesLibraryFiles();
            if (usesLibraryFiles.size() > 0) {
                printWriter.print(str);
                printWriter.println("  usesLibraryFiles:");
                for (int i10 = 0; i10 < usesLibraryFiles.size(); i10++) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println(usesLibraryFiles.get(i10));
                }
            }
            java.util.Map processes = pkg.getProcesses();
            if (!processes.isEmpty()) {
                printWriter.print(str);
                printWriter.println("  processes:");
                for (com.android.internal.pm.pkg.component.ParsedProcess parsedProcess : processes.values()) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println(parsedProcess.getName());
                    if (parsedProcess.getDeniedPermissions() != null) {
                        for (java.lang.String str4 : parsedProcess.getDeniedPermissions()) {
                            printWriter.print(str);
                            printWriter.print("      deny: ");
                            printWriter.println(str4);
                        }
                    }
                }
            }
        }
        printWriter.print(str);
        printWriter.print("  timeStamp=");
        date.setTime(packageSetting.getLastModifiedTime());
        printWriter.println(simpleDateFormat.format(date));
        printWriter.print(str);
        printWriter.print("  lastUpdateTime=");
        date.setTime(packageSetting.getLastUpdateTime());
        printWriter.println(simpleDateFormat.format(date));
        printWriter.print(str);
        printWriter.print("  installerPackageName=");
        printWriter.println(packageSetting.getInstallSource().mInstallerPackageName);
        printWriter.print(str);
        printWriter.print("  installerPackageUid=");
        printWriter.println(packageSetting.getInstallSource().mInstallerPackageUid);
        printWriter.print(str);
        printWriter.print("  initiatingPackageName=");
        printWriter.println(packageSetting.getInstallSource().mInitiatingPackageName);
        printWriter.print(str);
        printWriter.print("  originatingPackageName=");
        printWriter.println(packageSetting.getInstallSource().mOriginatingPackageName);
        if (packageSetting.getInstallSource().mUpdateOwnerPackageName != null) {
            printWriter.print(str);
            printWriter.print("  updateOwnerPackageName=");
            printWriter.println(packageSetting.getInstallSource().mUpdateOwnerPackageName);
        }
        if (packageSetting.getInstallSource().mInstallerAttributionTag != null) {
            printWriter.print(str);
            printWriter.print("  installerAttributionTag=");
            printWriter.println(packageSetting.getInstallSource().mInstallerAttributionTag);
        }
        printWriter.print(str);
        printWriter.print("  packageSource=");
        printWriter.println(packageSetting.getInstallSource().mPackageSource);
        if (packageSetting.isIncremental()) {
            printWriter.print(str);
            printWriter.println("  loadingProgress=" + ((int) (packageSetting.getLoadingProgress() * 100.0f)) + "%");
            date.setTime(packageSetting.getLoadingCompletedTime());
            printWriter.print(str);
            printWriter.println("  loadingCompletedTime=" + simpleDateFormat.format(date));
        }
        printWriter.print(str);
        printWriter.print("  appMetadataFilePath=");
        printWriter.println(packageSetting.getAppMetadataFilePath());
        printWriter.print(str);
        printWriter.print("  appMetadataSource=");
        printWriter.println(packageSetting.getAppMetadataSource());
        if (packageSetting.getVolumeUuid() != null) {
            printWriter.print(str);
            printWriter.print("  volumeUuid=");
            printWriter.println(packageSetting.getVolumeUuid());
        }
        printWriter.print(str);
        printWriter.print("  signatures=");
        printWriter.println(packageSetting.getSignatures());
        printWriter.print(str);
        printWriter.print("  installPermissionsFixed=");
        printWriter.print(packageSetting.isInstallPermissionsFixed());
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  pkgFlags=");
        printFlags(printWriter, packageSetting.getFlags(), FLAG_DUMP_SPEC);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  privatePkgFlags=");
        printFlags(printWriter, packageSetting.getPrivateFlags(), PRIVATE_FLAG_DUMP_SPEC);
        printWriter.println();
        if (packageSetting.isPendingRestore()) {
            printWriter.print(str);
            printWriter.println("  pendingRestore=true");
        }
        printWriter.print(str);
        printWriter.print("  apexModuleName=");
        printWriter.println(packageSetting.getApexModuleName());
        if (pkg != null && pkg.getOverlayTarget() != null) {
            printWriter.print(str);
            printWriter.print("  overlayTarget=");
            printWriter.println(pkg.getOverlayTarget());
            printWriter.print(str);
            printWriter.print("  overlayCategory=");
            printWriter.println(pkg.getOverlayCategory());
        }
        if (pkg != null && !pkg.getPermissions().isEmpty()) {
            java.util.List permissions = pkg.getPermissions();
            printWriter.print(str);
            printWriter.println("  declared permissions:");
            for (int i11 = 0; i11 < permissions.size(); i11++) {
                com.android.internal.pm.pkg.component.ParsedPermission parsedPermission = (com.android.internal.pm.pkg.component.ParsedPermission) permissions.get(i11);
                if (arraySet == null || arraySet.contains(parsedPermission.getName())) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.print(parsedPermission.getName());
                    printWriter.print(": prot=");
                    printWriter.print(android.content.pm.PermissionInfo.protectionToString(parsedPermission.getProtectionLevel()));
                    if ((parsedPermission.getFlags() & 1) != 0) {
                        printWriter.print(", COSTS_MONEY");
                    }
                    if ((parsedPermission.getFlags() & 2) != 0) {
                        printWriter.print(", HIDDEN");
                    }
                    if ((parsedPermission.getFlags() & 1073741824) != 0) {
                        printWriter.print(", INSTALLED");
                    }
                    printWriter.println();
                }
            }
        }
        if ((arraySet != null || z) && pkg != null && pkg.getRequestedPermissions() != null && pkg.getRequestedPermissions().size() > 0) {
            java.util.Set<java.lang.String> requestedPermissions = pkg.getRequestedPermissions();
            printWriter.print(str);
            printWriter.println("  requested permissions:");
            for (java.lang.String str5 : requestedPermissions) {
                if (arraySet == null || arraySet.contains(str5)) {
                    printWriter.print(str);
                    printWriter.print("    ");
                    printWriter.println(str5);
                }
            }
        }
        if (!packageSetting.hasSharedUser() || arraySet != null || z) {
            dumpInstallPermissionsLPr(printWriter, str + "  ", arraySet, legacyPermissionState, list);
        }
        if (z2) {
            dumpComponents(printWriter, str + "  ", packageSetting);
        }
        for (android.content.pm.UserInfo userInfo2 : list) {
            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault2 = packageSetting.getUserStateOrDefault(userInfo2.id);
            printWriter.print(str);
            printWriter.print("  User ");
            printWriter.print(userInfo2.id);
            printWriter.print(": ");
            printWriter.print("ceDataInode=");
            printWriter.print(userStateOrDefault2.getCeDataInode());
            printWriter.print(" deDataInode=");
            printWriter.print(userStateOrDefault2.getDeDataInode());
            printWriter.print(" installed=");
            printWriter.print(userStateOrDefault2.isInstalled());
            printWriter.print(" hidden=");
            printWriter.print(userStateOrDefault2.isHidden());
            printWriter.print(" suspended=");
            printWriter.print(userStateOrDefault2.isSuspended());
            printWriter.print(" distractionFlags=");
            printWriter.print(userStateOrDefault2.getDistractionFlags());
            printWriter.print(" stopped=");
            printWriter.print(userStateOrDefault2.isStopped());
            printWriter.print(" notLaunched=");
            printWriter.print(userStateOrDefault2.isNotLaunched());
            printWriter.print(" enabled=");
            printWriter.print(userStateOrDefault2.getEnabledState());
            printWriter.print(" instant=");
            printWriter.print(userStateOrDefault2.isInstantApp());
            printWriter.print(" virtual=");
            printWriter.print(userStateOrDefault2.isVirtualPreload());
            printWriter.print(" quarantined=");
            printWriter.print(userStateOrDefault2.isQuarantined());
            printWriter.println();
            printWriter.print("      installReason=");
            printWriter.println(userStateOrDefault2.getInstallReason());
            java.io.File dataDir = com.android.server.pm.parsing.PackageInfoUtils.getDataDir(packageSetting, userInfo2.id);
            printWriter.print("      dataDir=");
            printWriter.println(dataDir == null ? "null" : dataDir.getAbsolutePath());
            com.android.server.pm.pkg.PackageUserStateInternal readUserState = packageSetting.readUserState(userInfo2.id);
            printWriter.print("      firstInstallTime=");
            date.setTime(readUserState.getFirstInstallTimeMillis());
            printWriter.println(simpleDateFormat.format(date));
            if (readUserState.getArchiveState() != null) {
                com.android.server.pm.pkg.ArchiveState archiveState = readUserState.getArchiveState();
                printWriter.print("      archiveTime=");
                date.setTime(archiveState.getArchiveTimeMillis());
                printWriter.println(simpleDateFormat.format(date));
                printWriter.print("      unarchiveInstallerTitle=");
                printWriter.println(archiveState.getInstallerTitle());
                for (com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo archiveActivityInfo : archiveState.getActivityInfos()) {
                    printWriter.print("        archiveActivityInfo=");
                    printWriter.println(archiveActivityInfo.toString());
                }
            }
            printWriter.print("      uninstallReason=");
            printWriter.println(userStateOrDefault2.getUninstallReason());
            if (userStateOrDefault2.isSuspended()) {
                printWriter.print(str);
                printWriter.println("  Suspend params:");
                for (int i12 = 0; i12 < userStateOrDefault2.getSuspendParams().size(); i12++) {
                    printWriter.print(str);
                    printWriter.print("    suspendingPackage=");
                    printWriter.print(userStateOrDefault2.getSuspendParams().keyAt(i12));
                    com.android.server.pm.pkg.SuspendParams valueAt = userStateOrDefault2.getSuspendParams().valueAt(i12);
                    if (valueAt != null) {
                        printWriter.print(" dialogInfo=");
                        printWriter.print(valueAt.getDialogInfo());
                        printWriter.print(" quarantined=");
                        printWriter.println(valueAt.isQuarantined());
                    }
                    printWriter.println();
                }
            }
            android.content.pm.overlay.OverlayPaths overlayPaths = userStateOrDefault2.getOverlayPaths();
            if (overlayPaths != null) {
                if (!overlayPaths.getOverlayPaths().isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("    overlay paths:");
                    for (java.lang.String str6 : overlayPaths.getOverlayPaths()) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println(str6);
                    }
                }
                if (!overlayPaths.getResourceDirs().isEmpty()) {
                    printWriter.print(str);
                    printWriter.println("    legacy overlay paths:");
                    for (java.lang.String str7 : overlayPaths.getResourceDirs()) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println(str7);
                    }
                }
            }
            java.util.Map<java.lang.String, android.content.pm.overlay.OverlayPaths> sharedLibraryOverlayPaths = userStateOrDefault2.getSharedLibraryOverlayPaths();
            if (sharedLibraryOverlayPaths != null) {
                java.util.Iterator<java.util.Map.Entry<java.lang.String, android.content.pm.overlay.OverlayPaths>> it3 = sharedLibraryOverlayPaths.entrySet().iterator();
                while (it3.hasNext()) {
                    java.util.Map.Entry<java.lang.String, android.content.pm.overlay.OverlayPaths> next2 = it3.next();
                    android.content.pm.overlay.OverlayPaths value = next2.getValue();
                    if (value != null) {
                        if (value.getOverlayPaths().isEmpty()) {
                            it = it3;
                        } else {
                            printWriter.print(str);
                            printWriter.println("    ");
                            printWriter.print(next2.getKey());
                            printWriter.println(" overlay paths:");
                            for (java.lang.String str8 : value.getOverlayPaths()) {
                                printWriter.print(str);
                                printWriter.print("        ");
                                printWriter.println(str8);
                                it3 = it3;
                            }
                            it = it3;
                        }
                        if (!value.getResourceDirs().isEmpty()) {
                            printWriter.print(str);
                            printWriter.println("      ");
                            printWriter.print(next2.getKey());
                            printWriter.println(" legacy overlay paths:");
                            for (java.lang.String str9 : value.getResourceDirs()) {
                                printWriter.print(str);
                                printWriter.print("      ");
                                printWriter.println(str9);
                            }
                        }
                        it3 = it;
                    }
                }
            }
            java.lang.String lastDisableAppCaller2 = userStateOrDefault2.getLastDisableAppCaller();
            if (lastDisableAppCaller2 != null) {
                printWriter.print(str);
                printWriter.print("    lastDisabledCaller: ");
                printWriter.println(lastDisableAppCaller2);
            }
            if (!packageSetting.hasSharedUser()) {
                dumpGidsLPr(printWriter, str + "    ", this.mPermissionDataProvider.getGidsForUid(android.os.UserHandle.getUid(userInfo2.id, packageSetting.getAppId())));
                dumpRuntimePermissionsLPr(printWriter, str + "    ", arraySet, legacyPermissionState.getPermissionStates(userInfo2.id), z);
            }
            java.lang.String harmfulAppWarning = userStateOrDefault2.getHarmfulAppWarning();
            if (harmfulAppWarning != null) {
                printWriter.print(str);
                printWriter.print("      harmfulAppWarning: ");
                printWriter.println(harmfulAppWarning);
            }
            if (arraySet == null) {
                com.android.server.utils.WatchedArraySet<java.lang.String> disabledComponentsNoCopy = userStateOrDefault2.getDisabledComponentsNoCopy();
                if (disabledComponentsNoCopy != null && disabledComponentsNoCopy.size() > 0) {
                    printWriter.print(str);
                    printWriter.println("    disabledComponents:");
                    for (int i13 = 0; i13 < disabledComponentsNoCopy.size(); i13++) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println(disabledComponentsNoCopy.valueAt(i13));
                    }
                }
                com.android.server.utils.WatchedArraySet<java.lang.String> enabledComponentsNoCopy = userStateOrDefault2.getEnabledComponentsNoCopy();
                if (enabledComponentsNoCopy != null && enabledComponentsNoCopy.size() > 0) {
                    printWriter.print(str);
                    printWriter.println("    enabledComponents:");
                    for (int i14 = 0; i14 < enabledComponentsNoCopy.size(); i14++) {
                        printWriter.print(str);
                        printWriter.print("      ");
                        printWriter.println(enabledComponentsNoCopy.valueAt(i14));
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x0114, code lost:
    
        if (r1 != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x011a, code lost:
    
        if (r30.onTitlePrinted() == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x011c, code lost:
    
        r27.println();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x011f, code lost:
    
        r27.println("Renamed packages:");
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0126, code lost:
    
        r27.print("  ");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void dumpPackagesLPr(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, com.android.server.pm.DumpState dumpState, boolean z) {
        int i;
        boolean z2;
        boolean z3;
        com.android.server.pm.DumpState dumpState2 = dumpState;
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        boolean isOptionEnabled = dumpState2.isOptionEnabled(2);
        java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers(com.android.server.pm.UserManagerService.getInstance());
        java.util.Iterator<com.android.server.pm.PackageSetting> it = this.mPackages.values().iterator();
        boolean z4 = false;
        while (true) {
            i = 8;
            if (!it.hasNext()) {
                break;
            }
            com.android.server.pm.PackageSetting next = it.next();
            if (str == null || str.equals(next.getRealName()) || str.equals(next.getPackageName())) {
                if (next.getPkg() == null || !next.getPkg().isApex() || dumpState2.isOptionEnabled(8)) {
                    com.android.server.pm.permission.LegacyPermissionState legacyPermissionState = this.mPermissionDataProvider.getLegacyPermissionState(next.getAppId());
                    if (arraySet == null || legacyPermissionState.hasPermissionState(arraySet)) {
                        if (!z && str != null) {
                            dumpState2.setSharedUser(getSharedUserSettingLPr(next));
                        }
                        if (!z && !z4) {
                            if (dumpState.onTitlePrinted()) {
                                printWriter.println();
                            }
                            printWriter.println("Packages:");
                            z3 = true;
                        } else {
                            z3 = z4;
                        }
                        dumpPackageLPr(printWriter, "  ", z ? TAG_PACKAGE : null, arraySet, next, legacyPermissionState, simpleDateFormat, date, allUsers, str != null, isOptionEnabled);
                        dumpState2 = dumpState;
                        z4 = z3;
                        simpleDateFormat = simpleDateFormat;
                    }
                }
            }
        }
        java.text.SimpleDateFormat simpleDateFormat2 = simpleDateFormat;
        if (this.mRenamedPackages.size() > 0 && arraySet == null) {
            boolean z5 = false;
            for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : this.mRenamedPackages.entrySet()) {
                if (str == null || str.equals(entry.getKey()) || str.equals(entry.getValue())) {
                    printWriter.print("ren,");
                    printWriter.print(entry.getKey());
                    printWriter.print(z ? " -> " : ",");
                    printWriter.println(entry.getValue());
                }
            }
        }
        if (this.mDisabledSysPackages.size() > 0 && arraySet == null) {
            boolean z6 = false;
            for (com.android.server.pm.PackageSetting packageSetting : this.mDisabledSysPackages.values()) {
                if (str == null || str.equals(packageSetting.getRealName()) || str.equals(packageSetting.getPackageName())) {
                    if (packageSetting.getPkg() != null && packageSetting.getPkg().isApex()) {
                        if (!dumpState.isOptionEnabled(i)) {
                        }
                    }
                    if (!z && !z6) {
                        if (dumpState.onTitlePrinted()) {
                            printWriter.println();
                        }
                        printWriter.println("Hidden system packages:");
                        z2 = true;
                    } else {
                        z2 = z6;
                    }
                    dumpPackageLPr(printWriter, "  ", z ? "dis" : null, arraySet, packageSetting, this.mPermissionDataProvider.getLegacyPermissionState(packageSetting.getAppId()), simpleDateFormat2, date, allUsers, str != null, isOptionEnabled);
                    z6 = z2;
                    i = i;
                }
            }
        }
    }

    void dumpPackagesProto(android.util.proto.ProtoOutputStream protoOutputStream) {
        java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers(com.android.server.pm.UserManagerService.getInstance());
        int size = this.mPackages.size();
        for (int i = 0; i < size; i++) {
            this.mPackages.valueAt(i).dumpDebug(protoOutputStream, 2246267895813L, allUsers, this.mPermissionDataProvider);
        }
    }

    void dumpPermissions(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, com.android.server.pm.DumpState dumpState) {
        com.android.server.pm.permission.LegacyPermissionSettings.dumpPermissions(printWriter, str, arraySet, this.mPermissionDataProvider.getLegacyPermissions(), this.mPermissionDataProvider.getAllAppOpPermissionPackages(), true, dumpState);
    }

    void dumpSharedUsersLPr(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, com.android.server.pm.DumpState dumpState, boolean z) {
        boolean z2;
        boolean z3 = false;
        for (com.android.server.pm.SharedUserSetting sharedUserSetting : this.mSharedUsers.values()) {
            if (str == null || sharedUserSetting == dumpState.getSharedUser()) {
                com.android.server.pm.permission.LegacyPermissionState legacyPermissionState = this.mPermissionDataProvider.getLegacyPermissionState(sharedUserSetting.mAppId);
                if (arraySet == null || legacyPermissionState.hasPermissionState(arraySet)) {
                    if (!z) {
                        if (z3) {
                            z2 = z3;
                        } else {
                            if (dumpState.onTitlePrinted()) {
                                printWriter.println();
                            }
                            printWriter.println("Shared users:");
                            z2 = true;
                        }
                        printWriter.print("  SharedUser [");
                        printWriter.print(sharedUserSetting.name);
                        printWriter.print("] (");
                        printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(sharedUserSetting)));
                        printWriter.println("):");
                        printWriter.print("    ");
                        printWriter.print("appId=");
                        printWriter.println(sharedUserSetting.mAppId);
                        printWriter.print("    ");
                        printWriter.println("Packages");
                        android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = sharedUserSetting.getPackageStates();
                        int size = packageStates.size();
                        for (int i = 0; i < size; i++) {
                            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i);
                            if (valueAt != null) {
                                printWriter.print("      ");
                                printWriter.println(valueAt);
                            } else {
                                printWriter.print("      ");
                                printWriter.println("NULL?!");
                            }
                        }
                        if (dumpState.isOptionEnabled(4)) {
                            z3 = z2;
                        } else {
                            java.util.List<android.content.pm.UserInfo> allUsers = getAllUsers(com.android.server.pm.UserManagerService.getInstance());
                            dumpInstallPermissionsLPr(printWriter, "    ", arraySet, legacyPermissionState, allUsers);
                            java.util.Iterator<android.content.pm.UserInfo> it = allUsers.iterator();
                            while (it.hasNext()) {
                                int i2 = it.next().id;
                                int[] gidsForUid = this.mPermissionDataProvider.getGidsForUid(android.os.UserHandle.getUid(i2, sharedUserSetting.mAppId));
                                java.util.Collection<com.android.server.pm.permission.LegacyPermissionState.PermissionState> permissionStates = legacyPermissionState.getPermissionStates(i2);
                                if (!com.android.internal.util.ArrayUtils.isEmpty(gidsForUid) || !permissionStates.isEmpty()) {
                                    printWriter.print("    ");
                                    printWriter.print("User ");
                                    printWriter.print(i2);
                                    printWriter.println(": ");
                                    dumpGidsLPr(printWriter, "      ", gidsForUid);
                                    dumpRuntimePermissionsLPr(printWriter, "      ", arraySet, permissionStates, str != null);
                                }
                            }
                            z3 = z2;
                        }
                    } else {
                        printWriter.print("suid,");
                        printWriter.print(sharedUserSetting.mAppId);
                        printWriter.print(",");
                        printWriter.println(sharedUserSetting.name);
                    }
                }
            }
        }
    }

    void dumpSharedUsersProto(android.util.proto.ProtoOutputStream protoOutputStream) {
        int size = this.mSharedUsers.size();
        for (int i = 0; i < size; i++) {
            this.mSharedUsers.valueAt(i).dumpDebug(protoOutputStream, 2246267895814L);
        }
    }

    void dumpReadMessages(java.io.PrintWriter printWriter, com.android.server.pm.DumpState dumpState) {
        printWriter.println("Settings parse messages:");
        printWriter.print(this.mReadMessages.toString());
    }

    private static void dumpSplitNames(java.io.PrintWriter printWriter, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage == null) {
            printWriter.print("unknown");
            return;
        }
        printWriter.print("[");
        printWriter.print("base");
        if (androidPackage.getBaseRevisionCode() != 0) {
            printWriter.print(":");
            printWriter.print(androidPackage.getBaseRevisionCode());
        }
        java.lang.String[] splitNames = androidPackage.getSplitNames();
        int[] splitRevisionCodes = androidPackage.getSplitRevisionCodes();
        for (int i = 0; i < splitNames.length; i++) {
            printWriter.print(", ");
            printWriter.print(splitNames[i]);
            if (splitRevisionCodes[i] != 0) {
                printWriter.print(":");
                printWriter.print(splitRevisionCodes[i]);
            }
        }
        printWriter.print("]");
    }

    void dumpGidsLPr(java.io.PrintWriter printWriter, java.lang.String str, int[] iArr) {
        if (!com.android.internal.util.ArrayUtils.isEmpty(iArr)) {
            printWriter.print(str);
            printWriter.print("gids=");
            printWriter.println(com.android.server.pm.PackageManagerServiceUtils.arrayToString(iArr));
        }
    }

    void dumpRuntimePermissionsLPr(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, java.util.Collection<com.android.server.pm.permission.LegacyPermissionState.PermissionState> collection, boolean z) {
        boolean z2;
        java.util.Iterator<com.android.server.pm.permission.LegacyPermissionState.PermissionState> it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            } else if (it.next().isRuntime()) {
                z2 = true;
                break;
            }
        }
        if (z2 || z) {
            printWriter.print(str);
            printWriter.println("runtime permissions:");
            for (com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState : collection) {
                if (permissionState.isRuntime() && (arraySet == null || arraySet.contains(permissionState.getName()))) {
                    printWriter.print(str);
                    printWriter.print("  ");
                    printWriter.print(permissionState.getName());
                    printWriter.print(": granted=");
                    printWriter.print(permissionState.isGranted());
                    printWriter.println(permissionFlagsToString(", flags=", permissionState.getFlags()));
                }
            }
        }
    }

    private static java.lang.String permissionFlagsToString(java.lang.String str, int i) {
        java.lang.StringBuilder sb = null;
        while (i != 0) {
            if (sb == null) {
                sb = new java.lang.StringBuilder();
                sb.append(str);
                sb.append("[ ");
            }
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            sb.append(android.content.pm.PackageManager.permissionFlagToString(numberOfTrailingZeros));
            if (i != 0) {
                sb.append('|');
            }
        }
        if (sb != null) {
            sb.append(']');
            return sb.toString();
        }
        return "";
    }

    void dumpInstallPermissionsLPr(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, com.android.server.pm.permission.LegacyPermissionState legacyPermissionState, java.util.List<android.content.pm.UserInfo> list) {
        com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState;
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        java.util.Iterator<android.content.pm.UserInfo> it = list.iterator();
        while (it.hasNext()) {
            for (com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState2 : legacyPermissionState.getPermissionStates(it.next().id)) {
                if (!permissionState2.isRuntime()) {
                    java.lang.String name = permissionState2.getName();
                    if (arraySet == null || arraySet.contains(name)) {
                        arraySet2.add(name);
                    }
                }
            }
        }
        java.util.Iterator it2 = arraySet2.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            java.lang.String str2 = (java.lang.String) it2.next();
            com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState3 = legacyPermissionState.getPermissionState(str2, 0);
            java.util.Iterator<android.content.pm.UserInfo> it3 = list.iterator();
            while (it3.hasNext()) {
                int i = it3.next().id;
                if (i == 0) {
                    permissionState = permissionState3;
                } else {
                    permissionState = legacyPermissionState.getPermissionState(str2, i);
                    if (java.util.Objects.equals(permissionState, permissionState3)) {
                    }
                }
                if (!z) {
                    printWriter.print(str);
                    printWriter.println("install permissions:");
                    z = true;
                }
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.print(str2);
                printWriter.print(": granted=");
                printWriter.print(permissionState != null && permissionState.isGranted());
                printWriter.print(permissionFlagsToString(", flags=", permissionState != null ? permissionState.getFlags() : 0));
                if (i == 0) {
                    printWriter.println();
                } else {
                    printWriter.print(", userId=");
                    printWriter.println(i);
                }
            }
        }
    }

    void dumpComponents(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.pm.PackageSetting packageSetting) {
        dumpComponents(printWriter, str, "activities:", packageSetting.getPkg().getActivities());
        dumpComponents(printWriter, str, "services:", packageSetting.getPkg().getServices());
        dumpComponents(printWriter, str, "receivers:", packageSetting.getPkg().getReceivers());
        dumpComponents(printWriter, str, "providers:", packageSetting.getPkg().getProviders());
        dumpComponents(printWriter, str, "instrumentations:", packageSetting.getPkg().getInstrumentations());
    }

    void dumpComponents(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.util.List<? extends com.android.internal.pm.pkg.component.ParsedComponent> list) {
        int size = com.android.internal.util.CollectionUtils.size(list);
        if (size == 0) {
            return;
        }
        printWriter.print(str);
        printWriter.println(str2);
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedComponent parsedComponent = list.get(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.println(parsedComponent.getComponentName().flattenToShortString());
        }
    }

    public void writePermissionStateForUserLPr(int i, boolean z) {
        if (z) {
            this.mRuntimePermissionsPersistence.writeStateForUser(i, this.mPermissionDataProvider, this.mPackages, this.mSharedUsers, null, this.mLock, true);
        } else {
            this.mRuntimePermissionsPersistence.writeStateForUserAsync(i);
        }
    }

    private static class KeySetToValueMap<K, V> implements java.util.Map<K, V> {

        @android.annotation.NonNull
        private final java.util.Set<K> mKeySet;
        private final V mValue;

        KeySetToValueMap(@android.annotation.NonNull java.util.Set<K> set, V v) {
            this.mKeySet = set;
            this.mValue = v;
        }

        @Override // java.util.Map
        public int size() {
            return this.mKeySet.size();
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.mKeySet.isEmpty();
        }

        @Override // java.util.Map
        public boolean containsKey(java.lang.Object obj) {
            return this.mKeySet.contains(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(java.lang.Object obj) {
            return this.mValue == obj;
        }

        @Override // java.util.Map
        public V get(java.lang.Object obj) {
            return this.mValue;
        }

        @Override // java.util.Map
        public V put(K k, V v) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Map
        public V remove(java.lang.Object obj) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(java.util.Map<? extends K, ? extends V> map) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void clear() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Map
        public java.util.Set<K> keySet() {
            return this.mKeySet;
        }

        @Override // java.util.Map
        public java.util.Collection<V> values() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // java.util.Map
        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RuntimePermissionPersistence {
        private static final int INITIAL_VERSION = 0;
        private static final long MAX_WRITE_PERMISSIONS_DELAY_MILLIS = 2000;
        private static final int UPGRADE_VERSION = -1;
        private static final double WRITE_PERMISSIONS_DELAY_JITTER = 0.3d;
        private static final long WRITE_PERMISSIONS_DELAY_MILLIS = 1000;
        private static final java.util.Random sRandom = new java.util.Random();
        private java.lang.String mExtendedFingerprint;
        private final java.util.function.Consumer<java.lang.Integer> mInvokeWriteUserStateAsyncCallback;

        @com.android.internal.annotations.GuardedBy({"mPersistenceLock"})
        private final com.android.permission.persistence.RuntimePermissionsPersistence mPersistence;
        private final java.lang.Object mPersistenceLock = new java.lang.Object();
        private final android.os.Handler mAsyncHandler = new com.android.server.pm.Settings.RuntimePermissionPersistence.MyHandler();
        private final android.os.Handler mPersistenceHandler = new com.android.server.pm.Settings.RuntimePermissionPersistence.PersistenceHandler();
        private final java.lang.Object mLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseBooleanArray mWriteScheduled = new android.util.SparseBooleanArray();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseLongArray mLastNotWrittenMutationTimesMillis = new android.util.SparseLongArray();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final java.util.concurrent.atomic.AtomicBoolean mIsLegacyPermissionStateStale = new java.util.concurrent.atomic.AtomicBoolean(false);

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseIntArray mVersions = new android.util.SparseIntArray();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseArray<java.lang.String> mFingerprints = new android.util.SparseArray<>();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseBooleanArray mPermissionUpgradeNeeded = new android.util.SparseBooleanArray();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.SparseArray<com.android.permission.persistence.RuntimePermissionsState> mPendingStatesToWrite = new android.util.SparseArray<>();

        public RuntimePermissionPersistence(com.android.permission.persistence.RuntimePermissionsPersistence runtimePermissionsPersistence, java.util.function.Consumer<java.lang.Integer> consumer) {
            this.mPersistence = runtimePermissionsPersistence;
            this.mInvokeWriteUserStateAsyncCallback = consumer;
        }

        int getVersion(int i) {
            int i2;
            synchronized (this.mLock) {
                i2 = this.mVersions.get(i, 0);
            }
            return i2;
        }

        void setVersion(int i, int i2) {
            synchronized (this.mLock) {
                this.mVersions.put(i2, i);
                writeStateForUserAsync(i2);
            }
        }

        public boolean isPermissionUpgradeNeeded(int i) {
            boolean z;
            synchronized (this.mLock) {
                z = this.mPermissionUpgradeNeeded.get(i, true);
            }
            return z;
        }

        public void updateRuntimePermissionsFingerprint(int i) {
            synchronized (this.mLock) {
                try {
                    if (this.mExtendedFingerprint == null) {
                        throw new java.lang.RuntimeException("The version of the permission controller hasn't been set before trying to update the fingerprint.");
                    }
                    this.mFingerprints.put(i, this.mExtendedFingerprint);
                    this.mPermissionUpgradeNeeded.put(i, false);
                    writeStateForUserAsync(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setPermissionControllerVersion(long j) {
            synchronized (this.mLock) {
                try {
                    int size = this.mFingerprints.size();
                    this.mExtendedFingerprint = getExtendedFingerprint(j);
                    for (int i = 0; i < size; i++) {
                        this.mPermissionUpgradeNeeded.put(this.mFingerprints.keyAt(i), !android.text.TextUtils.equals(this.mExtendedFingerprint, this.mFingerprints.valueAt(i)));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private java.lang.String getExtendedFingerprint(long j) {
            return android.content.pm.PackagePartitions.FINGERPRINT + "?pc_version=" + j;
        }

        private static long uniformRandom(double d, double d2) {
            return (long) ((sRandom.nextDouble() * (d2 - d)) + d);
        }

        private static long nextWritePermissionDelayMillis() {
            return uniformRandom(-300.0d, 300.0d) + 1000;
        }

        public void writeStateForUserAsync(int i) {
            this.mIsLegacyPermissionStateStale.set(true);
            synchronized (this.mLock) {
                try {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    long nextWritePermissionDelayMillis = nextWritePermissionDelayMillis();
                    if (this.mWriteScheduled.get(i)) {
                        this.mAsyncHandler.removeMessages(i);
                        long j = this.mLastNotWrittenMutationTimesMillis.get(i);
                        if (uptimeMillis - j >= MAX_WRITE_PERMISSIONS_DELAY_MILLIS) {
                            this.mAsyncHandler.obtainMessage(i).sendToTarget();
                        } else {
                            long min = java.lang.Math.min(nextWritePermissionDelayMillis, java.lang.Math.max((j + MAX_WRITE_PERMISSIONS_DELAY_MILLIS) - uptimeMillis, 0L));
                            this.mAsyncHandler.sendMessageDelayed(this.mAsyncHandler.obtainMessage(i), min);
                        }
                    } else {
                        this.mLastNotWrittenMutationTimesMillis.put(i, uptimeMillis);
                        this.mAsyncHandler.sendMessageDelayed(this.mAsyncHandler.obtainMessage(i), nextWritePermissionDelayMillis);
                        this.mWriteScheduled.put(i, true);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void writeStateForUser(final int i, @android.annotation.NonNull final com.android.server.pm.permission.LegacyPermissionDataProvider legacyPermissionDataProvider, @android.annotation.NonNull final com.android.server.utils.WatchedArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> watchedArrayMap, @android.annotation.NonNull final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> watchedArrayMap2, @android.annotation.Nullable final android.os.Handler handler, @android.annotation.NonNull final com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock, final boolean z) {
            synchronized (this.mLock) {
                this.mAsyncHandler.removeMessages(i);
                this.mWriteScheduled.delete(i);
            }
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.pm.Settings$RuntimePermissionPersistence$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.Settings.RuntimePermissionPersistence.this.lambda$writeStateForUser$0(packageManagerTracedLock, z, legacyPermissionDataProvider, i, watchedArrayMap, watchedArrayMap2, handler);
                }
            };
            if (handler != null) {
                handler.post(runnable);
            } else {
                runnable.run();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$writeStateForUser$0(com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock, boolean z, com.android.server.pm.permission.LegacyPermissionDataProvider legacyPermissionDataProvider, int i, com.android.server.utils.WatchedArrayMap watchedArrayMap, com.android.server.utils.WatchedArrayMap watchedArrayMap2, android.os.Handler handler) {
            java.util.Map<java.lang.String, java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState>> packagePermissions;
            java.util.Map<java.lang.String, java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState>> shareUsersPermissions;
            boolean andSet = this.mIsLegacyPermissionStateStale.getAndSet(false);
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                if (z || andSet) {
                    try {
                        legacyPermissionDataProvider.writeLegacyPermissionStateTEMP();
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
                packagePermissions = getPackagePermissions(i, watchedArrayMap);
                shareUsersPermissions = getShareUsersPermissions(i, watchedArrayMap2);
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            synchronized (this.mLock) {
                this.mPendingStatesToWrite.put(i, new com.android.permission.persistence.RuntimePermissionsState(this.mVersions.get(i, 0), this.mFingerprints.get(i), packagePermissions, shareUsersPermissions));
            }
            if (handler != null) {
                this.mPersistenceHandler.obtainMessage(i).sendToTarget();
            } else {
                writePendingStates();
            }
        }

        @android.annotation.NonNull
        com.android.permission.persistence.RuntimePermissionsState getLegacyPermissionsState(int i, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> watchedArrayMap, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> watchedArrayMap2) {
            int i2;
            java.lang.String str;
            synchronized (this.mLock) {
                i2 = this.mVersions.get(i, 0);
                str = this.mFingerprints.get(i);
            }
            return new com.android.permission.persistence.RuntimePermissionsState(i2, str, getPackagePermissions(i, watchedArrayMap), getShareUsersPermissions(i, watchedArrayMap2));
        }

        @android.annotation.NonNull
        private java.util.Map<java.lang.String, java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState>> getPackagePermissions(int i, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> watchedArrayMap) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            int size = watchedArrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                java.lang.String keyAt = watchedArrayMap.keyAt(i2);
                com.android.server.pm.pkg.PackageStateInternal valueAt = watchedArrayMap.valueAt(i2);
                if (!valueAt.hasSharedUser()) {
                    java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState> permissionsFromPermissionsState = getPermissionsFromPermissionsState(valueAt.getLegacyPermissionState(), i);
                    if (!permissionsFromPermissionsState.isEmpty() || valueAt.isInstallPermissionsFixed()) {
                        arrayMap.put(keyAt, permissionsFromPermissionsState);
                    }
                }
            }
            return arrayMap;
        }

        @android.annotation.NonNull
        private java.util.Map<java.lang.String, java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState>> getShareUsersPermissions(int i, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> watchedArrayMap) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            int size = watchedArrayMap.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayMap.put(watchedArrayMap.keyAt(i2), getPermissionsFromPermissionsState(watchedArrayMap.valueAt(i2).getLegacyPermissionState(), i));
            }
            return arrayMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writePendingStates() {
            int keyAt;
            com.android.permission.persistence.RuntimePermissionsState valueAt;
            while (true) {
                synchronized (this.mLock) {
                    try {
                        if (this.mPendingStatesToWrite.size() != 0) {
                            keyAt = this.mPendingStatesToWrite.keyAt(0);
                            valueAt = this.mPendingStatesToWrite.valueAt(0);
                            this.mPendingStatesToWrite.removeAt(0);
                        } else {
                            return;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                synchronized (this.mPersistenceLock) {
                    this.mPersistence.writeForUser(valueAt, android.os.UserHandle.of(keyAt));
                }
            }
        }

        @android.annotation.NonNull
        private java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState> getPermissionsFromPermissionsState(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionState legacyPermissionState, int i) {
            java.util.Collection<com.android.server.pm.permission.LegacyPermissionState.PermissionState> permissionStates = legacyPermissionState.getPermissionStates(i);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState : permissionStates) {
                arrayList.add(new com.android.permission.persistence.RuntimePermissionsState.PermissionState(permissionState.getName(), permissionState.isGranted(), permissionState.getFlags()));
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onUserRemoved(int i) {
            synchronized (this.mLock) {
                this.mAsyncHandler.removeMessages(i);
                this.mPermissionUpgradeNeeded.delete(i);
                this.mVersions.delete(i);
                this.mFingerprints.remove(i);
            }
        }

        public void deleteUserRuntimePermissionsFile(int i) {
            synchronized (this.mPersistenceLock) {
                this.mPersistence.deleteForUser(android.os.UserHandle.of(i));
            }
        }

        public void readStateForUserSync(int i, @android.annotation.NonNull com.android.server.pm.Settings.VersionInfo versionInfo, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> watchedArrayMap, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> watchedArrayMap2, @android.annotation.NonNull java.io.File file) {
            com.android.permission.persistence.RuntimePermissionsState readForUser;
            synchronized (this.mPersistenceLock) {
                readForUser = this.mPersistence.readForUser(android.os.UserHandle.of(i));
            }
            if (readForUser == null) {
                readLegacyStateForUserSync(i, file, watchedArrayMap, watchedArrayMap2);
                writeStateForUserAsync(i);
                return;
            }
            synchronized (this.mLock) {
                try {
                    int version = readForUser.getVersion();
                    if (version == -1) {
                        version = -1;
                    }
                    this.mVersions.put(i, version);
                    this.mFingerprints.put(i, readForUser.getFingerprint());
                    boolean z = versionInfo.sdkVersion < 30;
                    java.util.Map packagePermissions = readForUser.getPackagePermissions();
                    int size = watchedArrayMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        java.lang.String keyAt = watchedArrayMap.keyAt(i2);
                        com.android.server.pm.PackageSetting valueAt = watchedArrayMap.valueAt(i2);
                        java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState> list = (java.util.List) packagePermissions.get(keyAt);
                        if (list != null) {
                            readPermissionsState(list, valueAt.getLegacyPermissionState(), i);
                            valueAt.setInstallPermissionsFixed(true);
                        } else if (!valueAt.hasSharedUser() && !z) {
                            com.android.server.utils.Slogf.w(com.android.server.pm.Settings.TAG, "Missing permission state for package %s on user %d", keyAt, java.lang.Integer.valueOf(i));
                            valueAt.getLegacyPermissionState().setMissing(true, i);
                        }
                    }
                    java.util.Map sharedUserPermissions = readForUser.getSharedUserPermissions();
                    int size2 = watchedArrayMap2.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        java.lang.String keyAt2 = watchedArrayMap2.keyAt(i3);
                        com.android.server.pm.SharedUserSetting valueAt2 = watchedArrayMap2.valueAt(i3);
                        java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState> list2 = (java.util.List) sharedUserPermissions.get(keyAt2);
                        if (list2 != null) {
                            readPermissionsState(list2, valueAt2.getLegacyPermissionState(), i);
                        } else if (!z) {
                            android.util.Slog.w(com.android.server.pm.Settings.TAG, "Missing permission state for shared user: " + keyAt2);
                            valueAt2.getLegacyPermissionState().setMissing(true, i);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void readPermissionsState(@android.annotation.NonNull java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState> list, @android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionState legacyPermissionState, int i) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.permission.persistence.RuntimePermissionsState.PermissionState permissionState = list.get(i2);
                legacyPermissionState.putPermissionState(new com.android.server.pm.permission.LegacyPermissionState.PermissionState(permissionState.getName(), true, permissionState.isGranted(), permissionState.getFlags()), i);
            }
        }

        private void readLegacyStateForUserSync(int i, @android.annotation.NonNull java.io.File file, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> watchedArrayMap, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> watchedArrayMap2) {
            synchronized (this.mLock) {
                if (file.exists()) {
                    try {
                        java.io.FileInputStream openRead = new android.util.AtomicFile(file).openRead();
                        try {
                            try {
                                parseLegacyRuntimePermissions(android.util.Xml.resolvePullParser(openRead), i, watchedArrayMap, watchedArrayMap2);
                            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                                throw new java.lang.IllegalStateException("Failed parsing permissions file: " + file, e);
                            }
                        } finally {
                            libcore.io.IoUtils.closeQuietly(openRead);
                        }
                    } catch (java.io.FileNotFoundException e2) {
                        android.util.Slog.i("PackageManager", "No permissions state");
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0021, code lost:
        
            if (r2 != 4) goto L54;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0024, code lost:
        
            r2 = r7.getName();
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x002d, code lost:
        
            switch(r2.hashCode()) {
                case 111052: goto L28;
                case 160289295: goto L25;
                case 485578803: goto L22;
                default: goto L21;
            };
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0051, code lost:
        
            r3 = 65535;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0053, code lost:
        
            switch(r3) {
                case 0: goto L60;
                case 1: goto L59;
                case 2: goto L62;
                default: goto L58;
            };
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0089, code lost:
        
            r2 = r7.getAttributeValue((java.lang.String) null, "name");
            r3 = r9.get(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0096, code lost:
        
            if (r3 != null) goto L63;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0098, code lost:
        
            android.util.Slog.w("PackageManager", "Unknown package:" + r2);
            com.android.internal.util.XmlUtils.skipCurrentTag(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00b3, code lost:
        
            parseLegacyPermissionsLPr(r7, r3.getLegacyPermissionState(), r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00bb, code lost:
        
            r6.mVersions.put(r8, r7.getAttributeInt((java.lang.String) null, "version", -1));
            r6.mFingerprints.put(r8, r7.getAttributeValue((java.lang.String) null, com.android.server.pm.Settings.ATTR_FINGERPRINT));
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0058, code lost:
        
            r2 = r7.getAttributeValue((java.lang.String) null, "name");
            r3 = r10.get(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0065, code lost:
        
            if (r3 != null) goto L61;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x0067, code lost:
        
            android.util.Slog.w("PackageManager", "Unknown shared user:" + r2);
            com.android.internal.util.XmlUtils.skipCurrentTag(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x0081, code lost:
        
            parseLegacyPermissionsLPr(r7, r3.getLegacyPermissionState(), r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x0038, code lost:
        
            if (r2.equals(com.android.server.pm.Settings.TAG_SHARED_USER) == false) goto L21;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x003a, code lost:
        
            r3 = 2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x0043, code lost:
        
            if (r2.equals(com.android.server.pm.Settings.TAG_RUNTIME_PERMISSIONS) == false) goto L21;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0045, code lost:
        
            r3 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x004e, code lost:
        
            if (r2.equals(com.android.server.pm.Settings.TAG_PACKAGE) == false) goto L21;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void parseLegacyRuntimePermissions(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> watchedArrayMap, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.SharedUserSetting> watchedArrayMap2) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            synchronized (this.mLock) {
                try {
                    int depth = typedXmlPullParser.getDepth();
                    while (true) {
                        int next = typedXmlPullParser.next();
                        char c = 1;
                        if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0020, code lost:
        
            if (r2 != 4) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0023, code lost:
        
            r2 = r9.getName();
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x002c, code lost:
        
            switch(r2.hashCode()) {
                case 3242771: goto L22;
                default: goto L21;
            };
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x003a, code lost:
        
            r2 = 65535;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x003b, code lost:
        
            switch(r2) {
                case 0: goto L28;
                default: goto L27;
            };
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x003f, code lost:
        
            r10.putPermissionState(new com.android.server.pm.permission.LegacyPermissionState.PermissionState(r9.getAttributeValue((java.lang.String) null, "name"), true, r9.getAttributeBoolean((java.lang.String) null, com.android.server.pm.Settings.ATTR_GRANTED, true), r9.getAttributeIntHex((java.lang.String) null, com.android.server.pm.Settings.ATTR_FLAGS, 0)), r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0036, code lost:
        
            if (r2.equals(com.android.server.pm.Settings.TAG_ITEM) == false) goto L21;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0038, code lost:
        
            r2 = 0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void parseLegacyPermissionsLPr(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.pm.permission.LegacyPermissionState legacyPermissionState, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            synchronized (this.mLock) {
                try {
                    int depth = typedXmlPullParser.getDepth();
                    while (true) {
                        int next = typedXmlPullParser.next();
                        if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private final class MyHandler extends android.os.Handler {
            public MyHandler() {
                super(com.android.internal.os.BackgroundThread.getHandler().getLooper());
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                int i = message.what;
                java.lang.Runnable runnable = (java.lang.Runnable) message.obj;
                com.android.server.pm.Settings.RuntimePermissionPersistence.this.mInvokeWriteUserStateAsyncCallback.accept(java.lang.Integer.valueOf(i));
                if (runnable != null) {
                    runnable.run();
                }
            }
        }

        private final class PersistenceHandler extends android.os.Handler {
            PersistenceHandler() {
                super(com.android.internal.os.BackgroundThread.getHandler().getLooper());
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                com.android.server.pm.Settings.RuntimePermissionPersistence.this.writePendingStates();
            }
        }
    }

    com.android.server.pm.PersistentPreferredIntentResolver getPersistentPreferredActivities(int i) {
        return this.mPersistentPreferredActivities.get(i);
    }

    com.android.server.pm.PreferredIntentResolver getPreferredActivities(int i) {
        return this.mPreferredActivities.get(i);
    }

    @android.annotation.Nullable
    com.android.server.pm.CrossProfileIntentResolver getCrossProfileIntentResolver(int i) {
        return this.mCrossProfileIntentResolvers.get(i);
    }

    void clearPackagePreferredActivities(java.lang.String str, @android.annotation.NonNull android.util.SparseBooleanArray sparseBooleanArray, int i) {
        java.util.ArrayList arrayList = null;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPreferredActivities.size(); i2++) {
            int keyAt = this.mPreferredActivities.keyAt(i2);
            com.android.server.pm.PreferredIntentResolver valueAt = this.mPreferredActivities.valueAt(i2);
            if (i == -1 || i == keyAt) {
                java.util.Iterator<F> filterIterator = valueAt.filterIterator();
                while (filterIterator.hasNext()) {
                    com.android.server.pm.PreferredActivity preferredActivity = (com.android.server.pm.PreferredActivity) filterIterator.next();
                    if (str == null || (preferredActivity.mPref.mComponent.getPackageName().equals(str) && preferredActivity.mPref.mAlways)) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(preferredActivity);
                    }
                }
                if (arrayList != null) {
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        valueAt.removeFilter((com.android.server.pm.PreferredIntentResolver) arrayList.get(i3));
                    }
                    z = true;
                    sparseBooleanArray.put(keyAt, true);
                }
            }
        }
        if (z) {
            onChanged();
        }
    }

    boolean clearPackagePersistentPreferredActivities(java.lang.String str, int i) {
        java.util.ArrayList arrayList = null;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPersistentPreferredActivities.size(); i2++) {
            int keyAt = this.mPersistentPreferredActivities.keyAt(i2);
            com.android.server.pm.PersistentPreferredIntentResolver valueAt = this.mPersistentPreferredActivities.valueAt(i2);
            if (i == keyAt) {
                java.util.Iterator<F> filterIterator = valueAt.filterIterator();
                while (filterIterator.hasNext()) {
                    com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity = (com.android.server.pm.PersistentPreferredActivity) filterIterator.next();
                    if (persistentPreferredActivity.mComponent.getPackageName().equals(str)) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(persistentPreferredActivity);
                    }
                }
                if (arrayList != null) {
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        valueAt.removeFilter((com.android.server.pm.PersistentPreferredIntentResolver) arrayList.get(i3));
                    }
                    z = true;
                }
            }
        }
        if (z) {
            onChanged();
        }
        return z;
    }

    boolean clearPersistentPreferredActivity(android.content.IntentFilter intentFilter, int i) {
        com.android.server.pm.PersistentPreferredIntentResolver persistentPreferredIntentResolver = this.mPersistentPreferredActivities.get(i);
        java.util.Iterator<F> filterIterator = persistentPreferredIntentResolver.filterIterator();
        java.util.ArrayList arrayList = null;
        while (filterIterator.hasNext()) {
            com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity = (com.android.server.pm.PersistentPreferredActivity) filterIterator.next();
            if (android.content.IntentFilter.filterEquals(persistentPreferredActivity.getIntentFilter(), intentFilter)) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(persistentPreferredActivity);
            }
        }
        boolean z = false;
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                persistentPreferredIntentResolver.removeFilter((com.android.server.pm.PersistentPreferredIntentResolver) arrayList.get(i2));
            }
            z = true;
        }
        if (z) {
            onChanged();
        }
        return z;
    }

    java.util.ArrayList<java.lang.Integer> systemReady(com.android.server.pm.resolution.ComponentResolver componentResolver) {
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int i = 0; i < this.mPreferredActivities.size(); i++) {
            com.android.server.pm.PreferredIntentResolver valueAt = this.mPreferredActivities.valueAt(i);
            arrayList2.clear();
            for (F f : valueAt.filterSet()) {
                if (!componentResolver.isActivityDefined(f.mPref.mComponent)) {
                    arrayList2.add(f);
                }
            }
            if (arrayList2.size() > 0) {
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    com.android.server.pm.PreferredActivity preferredActivity = (com.android.server.pm.PreferredActivity) arrayList2.get(i2);
                    android.util.Slog.w(TAG, "Removing dangling preferred activity: " + preferredActivity.mPref.mComponent);
                    valueAt.removeFilter((com.android.server.pm.PreferredIntentResolver) preferredActivity);
                }
                arrayList.add(java.lang.Integer.valueOf(this.mPreferredActivities.keyAt(i)));
            }
        }
        onChanged();
        return arrayList;
    }

    void dumpPreferred(java.io.PrintWriter printWriter, com.android.server.pm.DumpState dumpState, java.lang.String str) {
        for (int i = 0; i < this.mPreferredActivities.size(); i++) {
            com.android.server.pm.PreferredIntentResolver valueAt = this.mPreferredActivities.valueAt(i);
            int keyAt = this.mPreferredActivities.keyAt(i);
            if (valueAt.dump(printWriter, dumpState.getTitlePrinted() ? "\nPreferred Activities User " + keyAt + ":" : "Preferred Activities User " + keyAt + ":", "  ", str, true, false)) {
                dumpState.setTitlePrinted(true);
            }
        }
    }

    boolean isInstallerPackage(@android.annotation.NonNull java.lang.String str) {
        return this.mInstallerPackages.contains(str);
    }
}
