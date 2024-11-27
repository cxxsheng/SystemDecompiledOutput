package com.android.server.apphibernation;

/* loaded from: classes.dex */
public final class AppHibernationService extends com.android.server.SystemService {
    private static final long PACKAGE_MATCH_FLAGS = 537698816;
    private static final java.lang.String TAG = "AppHibernationService";

    @com.android.internal.annotations.VisibleForTesting
    public static boolean sIsServiceEnabled;
    private final java.util.concurrent.Executor mBackgroundExecutor;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, com.android.server.apphibernation.GlobalLevelState> mGlobalHibernationStates;
    private final com.android.server.apphibernation.HibernationStateDiskStore<com.android.server.apphibernation.GlobalLevelState> mGlobalLevelHibernationDiskStore;
    private final android.app.IActivityManager mIActivityManager;
    private final android.content.pm.IPackageManager mIPackageManager;
    private final com.android.server.apphibernation.AppHibernationService.Injector mInjector;
    private final com.android.server.apphibernation.AppHibernationManagerInternal mLocalService;
    private final java.lang.Object mLock;
    private final boolean mOatArtifactDeletionEnabled;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final com.android.server.apphibernation.AppHibernationService.AppHibernationServiceStub mServiceStub;
    private final android.app.usage.StorageStatsManager mStorageStatsManager;
    private final android.app.usage.UsageStatsManagerInternal.UsageEventListener mUsageEventListener;
    private final android.util.SparseArray<com.android.server.apphibernation.HibernationStateDiskStore<com.android.server.apphibernation.UserLevelState>> mUserDiskStores;
    private final android.os.UserManager mUserManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.Map<java.lang.String, com.android.server.apphibernation.UserLevelState>> mUserStates;

    interface Injector {
        android.app.IActivityManager getActivityManager();

        java.util.concurrent.Executor getBackgroundExecutor();

        android.content.Context getContext();

        com.android.server.apphibernation.HibernationStateDiskStore<com.android.server.apphibernation.GlobalLevelState> getGlobalLevelDiskStore();

        android.content.pm.IPackageManager getPackageManager();

        android.content.pm.PackageManagerInternal getPackageManagerInternal();

        android.app.usage.StorageStatsManager getStorageStatsManager();

        android.app.usage.UsageStatsManagerInternal getUsageStatsManagerInternal();

        com.android.server.apphibernation.HibernationStateDiskStore<com.android.server.apphibernation.UserLevelState> getUserLevelDiskStore(int i);

        android.os.UserManager getUserManager();

        boolean isOatArtifactDeletionEnabled();
    }

    public AppHibernationService(@android.annotation.NonNull android.content.Context context) {
        this(new com.android.server.apphibernation.AppHibernationService.InjectorImpl(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    AppHibernationService(@android.annotation.NonNull com.android.server.apphibernation.AppHibernationService.Injector injector) {
        super(injector.getContext());
        this.mLock = new java.lang.Object();
        this.mUserStates = new android.util.SparseArray<>();
        this.mUserDiskStores = new android.util.SparseArray<>();
        this.mGlobalHibernationStates = new android.util.ArrayMap();
        this.mLocalService = new com.android.server.apphibernation.AppHibernationService.LocalService(this);
        this.mServiceStub = new com.android.server.apphibernation.AppHibernationService.AppHibernationServiceStub(this);
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.apphibernation.AppHibernationService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                if (intExtra == -10000) {
                    return;
                }
                java.lang.String action = intent.getAction();
                if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action)) {
                    java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        return;
                    }
                    if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                        com.android.server.apphibernation.AppHibernationService.this.onPackageAdded(schemeSpecificPart, intExtra);
                    } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                        com.android.server.apphibernation.AppHibernationService.this.onPackageRemoved(schemeSpecificPart, intExtra);
                        if (intent.getBooleanExtra("android.intent.extra.REMOVED_FOR_ALL_USERS", false)) {
                            com.android.server.apphibernation.AppHibernationService.this.onPackageRemovedForAllUsers(schemeSpecificPart);
                        }
                    }
                }
            }
        };
        this.mUsageEventListener = new android.app.usage.UsageStatsManagerInternal.UsageEventListener() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda4
            @Override // android.app.usage.UsageStatsManagerInternal.UsageEventListener
            public final void onUsageEvent(int i, android.app.usage.UsageEvents.Event event) {
                com.android.server.apphibernation.AppHibernationService.this.lambda$new$6(i, event);
            }
        };
        this.mContext = injector.getContext();
        this.mIPackageManager = injector.getPackageManager();
        this.mPackageManagerInternal = injector.getPackageManagerInternal();
        this.mIActivityManager = injector.getActivityManager();
        this.mUserManager = injector.getUserManager();
        this.mStorageStatsManager = injector.getStorageStatsManager();
        this.mGlobalLevelHibernationDiskStore = injector.getGlobalLevelDiskStore();
        this.mBackgroundExecutor = injector.getBackgroundExecutor();
        this.mOatArtifactDeletionEnabled = injector.isOatArtifactDeletionEnabled();
        this.mInjector = injector;
        android.content.Context createContextAsUser = this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        createContextAsUser.registerReceiver(this.mBroadcastReceiver, intentFilter);
        com.android.server.LocalServices.addService(com.android.server.apphibernation.AppHibernationManagerInternal.class, this.mLocalService);
        this.mInjector.getUsageStatsManagerInternal().registerListener(this.mUsageEventListener);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("app_hibernation", this.mServiceStub);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.apphibernation.AppHibernationService.this.lambda$onBootPhase$0();
                }
            });
        }
        if (i == 500) {
            sIsServiceEnabled = isDeviceConfigAppHibernationEnabled();
            android.provider.DeviceConfig.addOnPropertiesChangedListener("app_hibernation", android.app.ActivityThread.currentApplication().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda6
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.apphibernation.AppHibernationService.this.onDeviceConfigChanged(properties);
                }
            });
            android.app.StatsManager statsManager = (android.app.StatsManager) getContext().getSystemService(android.app.StatsManager.class);
            com.android.server.apphibernation.AppHibernationService.StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new com.android.server.apphibernation.AppHibernationService.StatsPullAtomCallbackImpl();
            statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.USER_LEVEL_HIBERNATED_APPS, (android.app.StatsManager.PullAtomMetadata) null, this.mBackgroundExecutor, statsPullAtomCallbackImpl);
            statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.GLOBAL_HIBERNATED_APPS, (android.app.StatsManager.PullAtomMetadata) null, this.mBackgroundExecutor, statsPullAtomCallbackImpl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0() {
        java.util.List<com.android.server.apphibernation.GlobalLevelState> readHibernationStates = this.mGlobalLevelHibernationDiskStore.readHibernationStates();
        synchronized (this.mLock) {
            initializeGlobalHibernationStates(readHibernationStates);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOatArtifactDeletionEnabled() {
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller does not have MANAGE_APP_HIBERNATION permission.");
        return this.mOatArtifactDeletionEnabled;
    }

    boolean isHibernatingForUser(java.lang.String str, int i) {
        if (!sIsServiceEnabled) {
            return false;
        }
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller did not have permission while calling isHibernatingForUser");
        int handleIncomingUser = handleIncomingUser(i, "isHibernatingForUser");
        synchronized (this.mLock) {
            try {
                if (!checkUserStatesExist(handleIncomingUser, "isHibernatingForUser", false)) {
                    return false;
                }
                com.android.server.apphibernation.UserLevelState userLevelState = this.mUserStates.get(handleIncomingUser).get(str);
                if (userLevelState != null && this.mPackageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), str)) {
                    return userLevelState.hibernated;
                }
                return false;
            } finally {
            }
        }
    }

    boolean isHibernatingGlobally(java.lang.String str) {
        if (!sIsServiceEnabled) {
            return false;
        }
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller does not have MANAGE_APP_HIBERNATION permission.");
        synchronized (this.mLock) {
            try {
                com.android.server.apphibernation.GlobalLevelState globalLevelState = this.mGlobalHibernationStates.get(str);
                if (globalLevelState != null && this.mPackageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), str)) {
                    return globalLevelState.hibernated;
                }
                return false;
            } finally {
            }
        }
    }

    void setHibernatingForUser(final java.lang.String str, int i, boolean z) {
        if (!sIsServiceEnabled) {
            return;
        }
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller does not have MANAGE_APP_HIBERNATION permission.");
        final int handleIncomingUser = handleIncomingUser(i, "setHibernatingForUser");
        synchronized (this.mLock) {
            try {
                if (checkUserStatesExist(handleIncomingUser, "setHibernatingForUser", true)) {
                    final com.android.server.apphibernation.UserLevelState userLevelState = this.mUserStates.get(handleIncomingUser).get(str);
                    if (userLevelState != null && this.mPackageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), str)) {
                        if (userLevelState.hibernated == z) {
                            return;
                        }
                        userLevelState.hibernated = z;
                        if (z) {
                            this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.apphibernation.AppHibernationService.this.lambda$setHibernatingForUser$1(str, handleIncomingUser, userLevelState);
                                }
                            });
                        } else {
                            this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.apphibernation.AppHibernationService.this.lambda$setHibernatingForUser$2(str, handleIncomingUser);
                                }
                            });
                            userLevelState.lastUnhibernatedMs = java.lang.System.currentTimeMillis();
                        }
                        final com.android.server.apphibernation.UserLevelState userLevelState2 = new com.android.server.apphibernation.UserLevelState(userLevelState);
                        this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.apphibernation.AppHibernationService.lambda$setHibernatingForUser$3(com.android.server.apphibernation.UserLevelState.this, handleIncomingUser);
                            }
                        });
                        this.mUserDiskStores.get(handleIncomingUser).scheduleWriteHibernationStates(new java.util.ArrayList(this.mUserStates.get(handleIncomingUser).values()));
                        return;
                    }
                    android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Package %s is not installed for user %s", new java.lang.Object[]{str, java.lang.Integer.valueOf(handleIncomingUser)}));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setHibernatingForUser$3(com.android.server.apphibernation.UserLevelState userLevelState, int i) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.USER_LEVEL_HIBERNATION_STATE_CHANGED, userLevelState.packageName, i, userLevelState.hibernated);
    }

    void setHibernatingGlobally(final java.lang.String str, boolean z) {
        if (!sIsServiceEnabled) {
            return;
        }
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller does not have MANAGE_APP_HIBERNATION permission.");
        synchronized (this.mLock) {
            try {
                final com.android.server.apphibernation.GlobalLevelState globalLevelState = this.mGlobalHibernationStates.get(str);
                if (globalLevelState != null && this.mPackageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), str)) {
                    if (globalLevelState.hibernated != z) {
                        globalLevelState.hibernated = z;
                        if (z) {
                            this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.apphibernation.AppHibernationService.this.lambda$setHibernatingGlobally$4(str, globalLevelState);
                                }
                            });
                        } else {
                            globalLevelState.savedByte = 0L;
                            globalLevelState.lastUnhibernatedMs = java.lang.System.currentTimeMillis();
                        }
                        this.mGlobalLevelHibernationDiskStore.scheduleWriteHibernationStates(new java.util.ArrayList(this.mGlobalHibernationStates.values()));
                    }
                    return;
                }
                android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Package %s is not installed for any user", new java.lang.Object[]{str}));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    java.util.List<java.lang.String> getHibernatingPackagesForUser(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (!sIsServiceEnabled) {
            return arrayList;
        }
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller does not have MANAGE_APP_HIBERNATION permission.");
        int handleIncomingUser = handleIncomingUser(i, "getHibernatingPackagesForUser");
        synchronized (this.mLock) {
            try {
                if (!checkUserStatesExist(handleIncomingUser, "getHibernatingPackagesForUser", true)) {
                    return arrayList;
                }
                for (com.android.server.apphibernation.UserLevelState userLevelState : this.mUserStates.get(handleIncomingUser).values()) {
                    if (this.mPackageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), userLevelState.packageName)) {
                        if (userLevelState.hibernated) {
                            arrayList.add(userLevelState.packageName);
                        }
                    }
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.Map<java.lang.String, android.apphibernation.HibernationStats> getHibernationStatsForUser(@android.annotation.Nullable java.util.Set<java.lang.String> set, int i) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        if (!sIsServiceEnabled) {
            return arrayMap;
        }
        getContext().enforceCallingOrSelfPermission("android.permission.MANAGE_APP_HIBERNATION", "Caller does not have MANAGE_APP_HIBERNATION permission.");
        int handleIncomingUser = handleIncomingUser(i, "getHibernationStatsForUser");
        synchronized (this.mLock) {
            try {
                if (!checkUserStatesExist(handleIncomingUser, "getHibernationStatsForUser", true)) {
                    return arrayMap;
                }
                java.util.Map<java.lang.String, com.android.server.apphibernation.UserLevelState> map = this.mUserStates.get(handleIncomingUser);
                if (set == null) {
                    set = map.keySet();
                }
                for (java.lang.String str : set) {
                    if (this.mPackageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), str)) {
                        if (this.mGlobalHibernationStates.containsKey(str) && map.containsKey(str)) {
                            arrayMap.put(str, new android.apphibernation.HibernationStats(this.mGlobalHibernationStates.get(str).savedByte + map.get(str).savedByte));
                        }
                        android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("No hibernation state associated with package %s user %d. Maybethe package was uninstalled? ", new java.lang.Object[]{str, java.lang.Integer.valueOf(handleIncomingUser)}));
                    }
                }
                return arrayMap;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: hibernatePackageForUser, reason: merged with bridge method [inline-methods] */
    public void lambda$setHibernatingForUser$1(@android.annotation.NonNull java.lang.String str, int i, com.android.server.apphibernation.UserLevelState userLevelState) {
        android.os.Trace.traceBegin(524288L, "hibernatePackage");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                try {
                    try {
                        android.app.usage.StorageStats queryStatsForPackage = this.mStorageStatsManager.queryStatsForPackage(this.mIPackageManager.getApplicationInfo(str, PACKAGE_MATCH_FLAGS, i).storageUuid, str, new android.os.UserHandle(i));
                        this.mIActivityManager.forceStopPackage(str, i);
                        this.mIPackageManager.deleteApplicationCacheFilesAsUser(str, i, (android.content.pm.IPackageDataObserver) null);
                        synchronized (this.mLock) {
                            userLevelState.savedByte = queryStatsForPackage.getCacheBytes();
                        }
                    } catch (android.os.RemoteException e) {
                        throw new java.lang.IllegalStateException("Failed to hibernate due to manager not being available", e);
                    }
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(TAG, "Storage device not found", e2);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                android.util.Slog.e(TAG, "Package name not found when querying storage stats", e3);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.os.Trace.traceEnd(524288L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unhibernatePackageForUser, reason: merged with bridge method [inline-methods] */
    public void lambda$setHibernatingForUser$2(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.traceBegin(524288L, "unhibernatePackage");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                java.lang.String[] strArr = {"android.permission.RECEIVE_BOOT_COMPLETED"};
                this.mIActivityManager.broadcastIntentWithFeature((android.app.IApplicationThread) null, (java.lang.String) null, new android.content.Intent("android.intent.action.LOCKED_BOOT_COMPLETED").setPackage(str), (java.lang.String) null, (android.content.IIntentReceiver) null, -1, (java.lang.String) null, (android.os.Bundle) null, strArr, (java.lang.String[]) null, (java.lang.String[]) null, -1, (android.os.Bundle) null, false, false, i);
                this.mIActivityManager.broadcastIntentWithFeature((android.app.IApplicationThread) null, (java.lang.String) null, new android.content.Intent("android.intent.action.BOOT_COMPLETED").setPackage(str), (java.lang.String) null, (android.content.IIntentReceiver) null, -1, (java.lang.String) null, (android.os.Bundle) null, strArr, (java.lang.String[]) null, (java.lang.String[]) null, -1, (android.os.Bundle) null, false, false, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.os.Trace.traceEnd(524288L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: hibernatePackageGlobally, reason: merged with bridge method [inline-methods] */
    public void lambda$setHibernatingGlobally$4(@android.annotation.NonNull java.lang.String str, com.android.server.apphibernation.GlobalLevelState globalLevelState) {
        android.os.Trace.traceBegin(524288L, "hibernatePackageGlobally");
        long max = this.mOatArtifactDeletionEnabled ? java.lang.Math.max(this.mPackageManagerInternal.deleteOatArtifactsOfPackage(str), 0L) : 0L;
        synchronized (this.mLock) {
            globalLevelState.savedByte = max;
        }
        android.os.Trace.traceEnd(524288L);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void initializeUserHibernationStates(int i, @android.annotation.Nullable java.util.List<com.android.server.apphibernation.UserLevelState> list) {
        try {
            java.util.List list2 = this.mIPackageManager.getInstalledPackages(PACKAGE_MATCH_FLAGS, i).getList();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            int size = list2.size();
            for (int i2 = 0; i2 < size; i2++) {
                java.lang.String str = ((android.content.pm.PackageInfo) list2.get(i2)).packageName;
                com.android.server.apphibernation.UserLevelState userLevelState = new com.android.server.apphibernation.UserLevelState();
                userLevelState.packageName = str;
                arrayMap.put(str, userLevelState);
            }
            if (list != null) {
                android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
                int size2 = list2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayMap2.put(((android.content.pm.PackageInfo) list2.get(i3)).packageName, (android.content.pm.PackageInfo) list2.get(i3));
                }
                int size3 = list.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    java.lang.String str2 = list.get(i4).packageName;
                    android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) arrayMap2.get(str2);
                    com.android.server.apphibernation.UserLevelState userLevelState2 = list.get(i4);
                    if (packageInfo == null) {
                        android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("No hibernation state associated with package %s user %d. Maybethe package was uninstalled? ", new java.lang.Object[]{str2, java.lang.Integer.valueOf(i)}));
                    } else {
                        if (packageInfo.applicationInfo != null) {
                            android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                            int i5 = applicationInfo.flags & 2097152;
                            applicationInfo.flags = i5;
                            if (i5 == 0 && userLevelState2.hibernated) {
                                userLevelState2.hibernated = false;
                                userLevelState2.lastUnhibernatedMs = java.lang.System.currentTimeMillis();
                            }
                        }
                        arrayMap.put(str2, userLevelState2);
                    }
                }
            }
            this.mUserStates.put(i, arrayMap);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Package manager not available", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void initializeGlobalHibernationStates(@android.annotation.Nullable java.util.List<com.android.server.apphibernation.GlobalLevelState> list) {
        try {
            java.util.List list2 = this.mIPackageManager.getInstalledPackages(541893120L, 0).getList();
            int size = list2.size();
            for (int i = 0; i < size; i++) {
                java.lang.String str = ((android.content.pm.PackageInfo) list2.get(i)).packageName;
                com.android.server.apphibernation.GlobalLevelState globalLevelState = new com.android.server.apphibernation.GlobalLevelState();
                globalLevelState.packageName = str;
                this.mGlobalHibernationStates.put(str, globalLevelState);
            }
            if (list != null) {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arraySet.add(((android.content.pm.PackageInfo) list2.get(i2)).packageName);
                }
                int size3 = list.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    com.android.server.apphibernation.GlobalLevelState globalLevelState2 = list.get(i3);
                    if (!arraySet.contains(globalLevelState2.packageName)) {
                        android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("No hibernation state associated with package %s. Maybe the package was uninstalled? ", new java.lang.Object[]{globalLevelState2.packageName}));
                    } else {
                        this.mGlobalHibernationStates.put(globalLevelState2.packageName, globalLevelState2);
                    }
                }
            }
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Package manager not available", e);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        final int userIdentifier = targetUser.getUserIdentifier();
        final com.android.server.apphibernation.HibernationStateDiskStore<com.android.server.apphibernation.UserLevelState> userLevelDiskStore = this.mInjector.getUserLevelDiskStore(userIdentifier);
        this.mUserDiskStores.put(userIdentifier, userLevelDiskStore);
        this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.apphibernation.AppHibernationService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.apphibernation.AppHibernationService.this.lambda$onUserUnlocking$5(userLevelDiskStore, userIdentifier);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserUnlocking$5(com.android.server.apphibernation.HibernationStateDiskStore hibernationStateDiskStore, int i) {
        java.util.List<com.android.server.apphibernation.UserLevelState> readHibernationStates = hibernationStateDiskStore.readHibernationStates();
        synchronized (this.mLock) {
            try {
                if (this.mUserManager.isUserUnlockingOrUnlocked(i)) {
                    initializeUserHibernationStates(i, readHibernationStates);
                    for (com.android.server.apphibernation.UserLevelState userLevelState : this.mUserStates.get(i).values()) {
                        java.lang.String str = userLevelState.packageName;
                        if (this.mGlobalHibernationStates.get(str).hibernated && !userLevelState.hibernated) {
                            setHibernatingGlobally(str, false);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        synchronized (this.mLock) {
            this.mUserDiskStores.remove(userIdentifier);
            this.mUserStates.remove(userIdentifier);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageAdded(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mUserStates.contains(i)) {
                    com.android.server.apphibernation.UserLevelState userLevelState = new com.android.server.apphibernation.UserLevelState();
                    userLevelState.packageName = str;
                    this.mUserStates.get(i).put(str, userLevelState);
                    if (!this.mGlobalHibernationStates.containsKey(str)) {
                        com.android.server.apphibernation.GlobalLevelState globalLevelState = new com.android.server.apphibernation.GlobalLevelState();
                        globalLevelState.packageName = str;
                        this.mGlobalHibernationStates.put(str, globalLevelState);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageRemoved(@android.annotation.NonNull java.lang.String str, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mUserStates.contains(i)) {
                    this.mUserStates.get(i).remove(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageRemovedForAllUsers(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            this.mGlobalHibernationStates.remove(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceConfigChanged(android.provider.DeviceConfig.Properties properties) {
        java.util.Iterator it = properties.getKeyset().iterator();
        while (it.hasNext()) {
            if (android.text.TextUtils.equals("app_hibernation_enabled", (java.lang.String) it.next())) {
                sIsServiceEnabled = isDeviceConfigAppHibernationEnabled();
                android.util.Slog.d(TAG, "App hibernation changed to enabled=" + sIsServiceEnabled);
                return;
            }
        }
    }

    private int handleIncomingUser(int i, @android.annotation.NonNull java.lang.String str) {
        try {
            return this.mIActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, false, true, str, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean checkUserStatesExist(int i, java.lang.String str, boolean z) {
        if (!this.mUserManager.isUserUnlockingOrUnlocked(i)) {
            if (z) {
                android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Attempt to call %s on stopped or nonexistent user %d", new java.lang.Object[]{str, java.lang.Integer.valueOf(i)}));
            }
            return false;
        }
        if (!this.mUserStates.contains(i)) {
            if (z) {
                android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Attempt to call %s before states have been read from disk", new java.lang.Object[]{str}));
            }
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dump(java.io.PrintWriter printWriter) {
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(getContext(), TAG, printWriter)) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            synchronized (this.mLock) {
                try {
                    int size = this.mUserStates.size();
                    for (int i = 0; i < size; i++) {
                        int keyAt = this.mUserStates.keyAt(i);
                        indentingPrintWriter.print("User Level Hibernation States, ");
                        indentingPrintWriter.printPair("user", java.lang.Integer.valueOf(keyAt));
                        indentingPrintWriter.println();
                        java.util.Map<java.lang.String, com.android.server.apphibernation.UserLevelState> map = this.mUserStates.get(keyAt);
                        indentingPrintWriter.increaseIndent();
                        java.util.Iterator<com.android.server.apphibernation.UserLevelState> it = map.values().iterator();
                        while (it.hasNext()) {
                            indentingPrintWriter.print(it.next());
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Global Level Hibernation States");
                    indentingPrintWriter.println();
                    java.util.Iterator<com.android.server.apphibernation.GlobalLevelState> it2 = this.mGlobalHibernationStates.values().iterator();
                    while (it2.hasNext()) {
                        indentingPrintWriter.print(it2.next());
                        indentingPrintWriter.println();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static final class LocalService extends com.android.server.apphibernation.AppHibernationManagerInternal {
        private final com.android.server.apphibernation.AppHibernationService mService;

        LocalService(com.android.server.apphibernation.AppHibernationService appHibernationService) {
            this.mService = appHibernationService;
        }

        @Override // com.android.server.apphibernation.AppHibernationManagerInternal
        public boolean isHibernatingForUser(java.lang.String str, int i) {
            return this.mService.isHibernatingForUser(str, i);
        }

        @Override // com.android.server.apphibernation.AppHibernationManagerInternal
        public void setHibernatingForUser(java.lang.String str, int i, boolean z) {
            this.mService.setHibernatingForUser(str, i, z);
        }

        @Override // com.android.server.apphibernation.AppHibernationManagerInternal
        public void setHibernatingGlobally(java.lang.String str, boolean z) {
            this.mService.setHibernatingGlobally(str, z);
        }

        @Override // com.android.server.apphibernation.AppHibernationManagerInternal
        public boolean isHibernatingGlobally(java.lang.String str) {
            return this.mService.isHibernatingGlobally(str);
        }

        @Override // com.android.server.apphibernation.AppHibernationManagerInternal
        public boolean isOatArtifactDeletionEnabled() {
            return this.mService.isOatArtifactDeletionEnabled();
        }
    }

    static final class AppHibernationServiceStub extends android.apphibernation.IAppHibernationService.Stub {
        final com.android.server.apphibernation.AppHibernationService mService;

        AppHibernationServiceStub(com.android.server.apphibernation.AppHibernationService appHibernationService) {
            this.mService = appHibernationService;
        }

        public boolean isHibernatingForUser(java.lang.String str, int i) {
            return this.mService.isHibernatingForUser(str, i);
        }

        public void setHibernatingForUser(java.lang.String str, int i, boolean z) {
            this.mService.setHibernatingForUser(str, i, z);
        }

        public void setHibernatingGlobally(java.lang.String str, boolean z) {
            this.mService.setHibernatingGlobally(str, z);
        }

        public boolean isHibernatingGlobally(java.lang.String str) {
            return this.mService.isHibernatingGlobally(str);
        }

        public java.util.List<java.lang.String> getHibernatingPackagesForUser(int i) {
            return this.mService.getHibernatingPackagesForUser(i);
        }

        public java.util.Map<java.lang.String, android.apphibernation.HibernationStats> getHibernationStatsForUser(@android.annotation.Nullable java.util.List<java.lang.String> list, int i) {
            return this.mService.getHibernationStatsForUser(list != null ? new android.util.ArraySet(list) : null, i);
        }

        public boolean isOatArtifactDeletionEnabled() {
            return this.mService.isOatArtifactDeletionEnabled();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
            new com.android.server.apphibernation.AppHibernationShellCommand(this.mService).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
            this.mService.dump(printWriter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(int i, android.app.usage.UsageEvents.Event event) {
        if (!isAppHibernationEnabled()) {
            return;
        }
        int i2 = event.mEventType;
        if (i2 == 7 || i2 == 1 || i2 == 31) {
            java.lang.String str = event.mPackage;
            setHibernatingForUser(str, i, false);
            setHibernatingGlobally(str, false);
        }
    }

    public static boolean isAppHibernationEnabled() {
        return sIsServiceEnabled;
    }

    private static boolean isDeviceConfigAppHibernationEnabled() {
        return android.provider.DeviceConfig.getBoolean("app_hibernation", "app_hibernation_enabled", true);
    }

    private static final class InjectorImpl implements com.android.server.apphibernation.AppHibernationService.Injector {
        private static final java.lang.String HIBERNATION_DIR_NAME = "hibernation";
        private final android.content.Context mContext;
        private final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
        private final com.android.server.apphibernation.UserLevelHibernationProto mUserLevelHibernationProto = new com.android.server.apphibernation.UserLevelHibernationProto();

        InjectorImpl(android.content.Context context) {
            this.mContext = context;
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public android.content.Context getContext() {
            return this.mContext;
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public android.content.pm.IPackageManager getPackageManager() {
            return android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService(com.android.server.pm.Settings.ATTR_PACKAGE));
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public android.content.pm.PackageManagerInternal getPackageManagerInternal() {
            return (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public android.app.IActivityManager getActivityManager() {
            return android.app.ActivityManager.getService();
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public android.os.UserManager getUserManager() {
            return (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public android.app.usage.StorageStatsManager getStorageStatsManager() {
            return (android.app.usage.StorageStatsManager) this.mContext.getSystemService(android.app.usage.StorageStatsManager.class);
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public java.util.concurrent.Executor getBackgroundExecutor() {
            return this.mScheduledExecutorService;
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public android.app.usage.UsageStatsManagerInternal getUsageStatsManagerInternal() {
            return (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public com.android.server.apphibernation.HibernationStateDiskStore<com.android.server.apphibernation.GlobalLevelState> getGlobalLevelDiskStore() {
            return new com.android.server.apphibernation.HibernationStateDiskStore<>(new java.io.File(android.os.Environment.getDataSystemDirectory(), HIBERNATION_DIR_NAME), new com.android.server.apphibernation.GlobalLevelHibernationProto(), this.mScheduledExecutorService);
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public com.android.server.apphibernation.HibernationStateDiskStore<com.android.server.apphibernation.UserLevelState> getUserLevelDiskStore(int i) {
            return new com.android.server.apphibernation.HibernationStateDiskStore<>(new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), HIBERNATION_DIR_NAME), this.mUserLevelHibernationProto, this.mScheduledExecutorService);
        }

        @Override // com.android.server.apphibernation.AppHibernationService.Injector
        public boolean isOatArtifactDeletionEnabled() {
            return this.mContext.getResources().getBoolean(android.R.bool.config_handleVolumeAliasesUsingVolumeGroups);
        }
    }

    private final class StatsPullAtomCallbackImpl implements android.app.StatsManager.StatsPullAtomCallback {
        private static final int MEGABYTE_IN_BYTES = 1000000;

        private StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
            long j;
            int i2;
            if (!com.android.server.apphibernation.AppHibernationService.isAppHibernationEnabled() && (i == 10107 || i == 10109)) {
                return 0;
            }
            switch (i) {
                case com.android.internal.util.FrameworkStatsLog.USER_LEVEL_HIBERNATED_APPS /* 10107 */:
                    java.util.List aliveUsers = com.android.server.apphibernation.AppHibernationService.this.mUserManager.getAliveUsers();
                    int size = aliveUsers.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        int i4 = ((android.content.pm.UserInfo) aliveUsers.get(i3)).id;
                        if (com.android.server.apphibernation.AppHibernationService.this.mUserManager.isUserUnlockingOrUnlocked(i4)) {
                            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, com.android.server.apphibernation.AppHibernationService.this.getHibernatingPackagesForUser(i4).size(), i4));
                        }
                    }
                    return 0;
                case 10108:
                default:
                    return 1;
                case com.android.internal.util.FrameworkStatsLog.GLOBAL_HIBERNATED_APPS /* 10109 */:
                    synchronized (com.android.server.apphibernation.AppHibernationService.this.mLock) {
                        try {
                            j = 0;
                            i2 = 0;
                            for (com.android.server.apphibernation.GlobalLevelState globalLevelState : com.android.server.apphibernation.AppHibernationService.this.mGlobalHibernationStates.values()) {
                                if (globalLevelState.hibernated) {
                                    i2++;
                                    j += globalLevelState.savedByte;
                                }
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, j / 1000000));
                    return 0;
            }
        }
    }
}
