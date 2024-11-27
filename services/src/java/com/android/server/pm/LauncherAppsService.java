package com.android.server.pm;

/* loaded from: classes2.dex */
public class LauncherAppsService extends com.android.server.SystemService {
    private static final java.lang.String PS_SETTINGS_INTENT = "com.android.settings.action.PRIVATE_SPACE_SETUP_FLOW";
    private static final java.lang.String VC_FILE_SUFFIX = ".vc";
    private static final java.lang.String WM_TRACE_DIR = "/data/misc/wmtrace/";
    private static final java.util.Set<java.nio.file.attribute.PosixFilePermission> WM_TRACE_FILE_PERMISSIONS = java.util.Set.of(java.nio.file.attribute.PosixFilePermission.OWNER_WRITE, java.nio.file.attribute.PosixFilePermission.GROUP_READ, java.nio.file.attribute.PosixFilePermission.OTHERS_READ, java.nio.file.attribute.PosixFilePermission.OWNER_READ);
    private final com.android.server.pm.LauncherAppsService.LauncherAppsImpl mLauncherAppsImpl;

    public static abstract class LauncherAppsServiceInternal {
        public abstract boolean startShortcut(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.graphics.Rect rect, android.os.Bundle bundle, int i3);
    }

    public LauncherAppsService(android.content.Context context) {
        super(context);
        this.mLauncherAppsImpl = new com.android.server.pm.LauncherAppsService.LauncherAppsImpl(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("launcherapps", this.mLauncherAppsImpl);
        this.mLauncherAppsImpl.registerLoadingProgressForIncrementalApps();
        com.android.server.LocalServices.addService(com.android.server.pm.LauncherAppsService.LauncherAppsServiceInternal.class, this.mLauncherAppsImpl.mInternal);
    }

    static class BroadcastCookie {
        public final int callingPid;
        public final int callingUid;
        public final java.lang.String packageName;
        public final android.os.UserHandle user;

        BroadcastCookie(android.os.UserHandle userHandle, java.lang.String str, int i, int i2) {
            this.user = userHandle;
            this.packageName = str;
            this.callingUid = i2;
            this.callingPid = i;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class LauncherAppsImpl extends android.content.pm.ILauncherApps.Stub {
        private static final boolean DEBUG = false;
        private static final java.lang.String TAG = "LauncherAppsService";
        private final android.app.ActivityManagerInternal mActivityManagerInternal;
        private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;
        private final android.app.AppOpsManager mAppOpsManager;
        private final android.os.Handler mCallbackHandler;
        private final android.content.Context mContext;
        private final android.app.admin.DevicePolicyManager mDpm;
        final com.android.server.pm.LauncherAppsService.LauncherAppsServiceInternal mInternal;
        private com.android.server.pm.PackageInstallerService mPackageInstallerService;
        private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
        private final android.app.role.RoleManager mRoleManager;
        private final com.android.server.pm.LauncherAppsService.LauncherAppsImpl.ShortcutChangeHandler mShortcutChangeHandler;
        private final android.content.pm.ShortcutServiceInternal mShortcutServiceInternal;
        private final android.os.UserManager mUm;
        private final android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal;
        private final com.android.server.pm.UserManagerInternal mUserManagerInternal;
        private final com.android.server.pm.LauncherAppsService.LauncherAppsImpl.PackageCallbackList<android.content.pm.IOnAppsChangedListener> mListeners = new com.android.server.pm.LauncherAppsService.LauncherAppsImpl.PackageCallbackList<>();
        private final com.android.server.pm.LauncherAppsService.LauncherAppsImpl.PackageRemovedListener mPackageRemovedListener = new com.android.server.pm.LauncherAppsService.LauncherAppsImpl.PackageRemovedListener();
        private final com.android.server.pm.LauncherAppsService.LauncherAppsImpl.MyPackageMonitor mPackageMonitor = new com.android.server.pm.LauncherAppsService.LauncherAppsImpl.MyPackageMonitor();

        @com.android.internal.annotations.GuardedBy({"mListeners"})
        private boolean mIsWatchingPackageBroadcasts = false;
        private final java.util.concurrent.ExecutorService mOnDumpExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();

        @android.annotation.NonNull
        private final android.os.RemoteCallbackList<android.window.IDumpCallback> mDumpCallbacks = new android.os.RemoteCallbackList<>();
        private final android.content.pm.IPackageManager mIPM = android.app.AppGlobals.getPackageManager();

        public LauncherAppsImpl(android.content.Context context) {
            this.mContext = context;
            this.mUm = (android.os.UserManager) this.mContext.getSystemService("user");
            this.mRoleManager = (android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class);
            com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
            java.util.Objects.requireNonNull(userManagerInternal);
            this.mUserManagerInternal = userManagerInternal;
            android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
            java.util.Objects.requireNonNull(usageStatsManagerInternal);
            this.mUsageStatsManagerInternal = usageStatsManagerInternal;
            android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            java.util.Objects.requireNonNull(activityManagerInternal);
            this.mActivityManagerInternal = activityManagerInternal;
            com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
            java.util.Objects.requireNonNull(activityTaskManagerInternal);
            this.mActivityTaskManagerInternal = activityTaskManagerInternal;
            android.content.pm.ShortcutServiceInternal shortcutServiceInternal = (android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class);
            java.util.Objects.requireNonNull(shortcutServiceInternal);
            this.mShortcutServiceInternal = shortcutServiceInternal;
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            java.util.Objects.requireNonNull(packageManagerInternal);
            this.mPackageManagerInternal = packageManagerInternal;
            this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
            this.mShortcutServiceInternal.addListener(this.mPackageMonitor);
            this.mShortcutChangeHandler = new com.android.server.pm.LauncherAppsService.LauncherAppsImpl.ShortcutChangeHandler(this.mUserManagerInternal);
            this.mShortcutServiceInternal.addShortcutChangeCallback(this.mShortcutChangeHandler);
            this.mCallbackHandler = com.android.internal.os.BackgroundThread.getHandler();
            this.mDpm = (android.app.admin.DevicePolicyManager) this.mContext.getSystemService("device_policy");
            this.mInternal = new com.android.server.pm.LauncherAppsService.LauncherAppsImpl.LocalService();
        }

        @com.android.internal.annotations.VisibleForTesting
        int injectBinderCallingUid() {
            return android.content.pm.ILauncherApps.Stub.getCallingUid();
        }

        @com.android.internal.annotations.VisibleForTesting
        int injectBinderCallingPid() {
            return android.content.pm.ILauncherApps.Stub.getCallingPid();
        }

        final int injectCallingUserId() {
            return android.os.UserHandle.getUserId(injectBinderCallingUid());
        }

        @com.android.internal.annotations.VisibleForTesting
        long injectClearCallingIdentity() {
            return android.os.Binder.clearCallingIdentity();
        }

        @com.android.internal.annotations.VisibleForTesting
        void injectRestoreCallingIdentity(long j) {
            android.os.Binder.restoreCallingIdentity(j);
        }

        private int getCallingUserId() {
            return android.os.UserHandle.getUserId(injectBinderCallingUid());
        }

        public void addOnAppsChangedListener(java.lang.String str, android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException {
            verifyCallingPackage(str);
            synchronized (this.mListeners) {
                try {
                    if (this.mListeners.getRegisteredCallbackCount() == 0) {
                        startWatchingPackageBroadcasts();
                    }
                    this.mListeners.unregister(iOnAppsChangedListener);
                    this.mListeners.register(iOnAppsChangedListener, new com.android.server.pm.LauncherAppsService.BroadcastCookie(android.os.UserHandle.of(getCallingUserId()), str, injectBinderCallingPid(), injectBinderCallingUid()));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removeOnAppsChangedListener(android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException {
            synchronized (this.mListeners) {
                try {
                    this.mListeners.unregister(iOnAppsChangedListener);
                    if (this.mListeners.getRegisteredCallbackCount() == 0) {
                        stopWatchingPackageBroadcasts();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void registerPackageInstallerCallback(java.lang.String str, android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) {
            verifyCallingPackage(str);
            final com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = new com.android.server.pm.LauncherAppsService.BroadcastCookie(new android.os.UserHandle(getCallingUserId()), str, android.content.pm.ILauncherApps.Stub.getCallingPid(), android.content.pm.ILauncherApps.Stub.getCallingUid());
            getPackageInstallerService().registerCallback(iPackageInstallerCallback, new java.util.function.IntPredicate() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    boolean lambda$registerPackageInstallerCallback$0;
                    lambda$registerPackageInstallerCallback$0 = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.lambda$registerPackageInstallerCallback$0(broadcastCookie, i);
                    return lambda$registerPackageInstallerCallback$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$registerPackageInstallerCallback$0(com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie, int i) {
            return isEnabledProfileOf(broadcastCookie, new android.os.UserHandle(i), "shouldReceiveEvent");
        }

        public java.util.List<android.os.UserHandle> getUserProfiles() {
            int[] enabledProfileIds;
            if (!canAccessHiddenProfile(android.content.pm.ILauncherApps.Stub.getCallingUid(), android.content.pm.ILauncherApps.Stub.getCallingPid())) {
                enabledProfileIds = this.mUm.getProfileIdsExcludingHidden(getCallingUserId(), true);
            } else {
                enabledProfileIds = this.mUm.getEnabledProfileIds(getCallingUserId());
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(enabledProfileIds.length);
            for (int i : enabledProfileIds) {
                arrayList.add(android.os.UserHandle.of(i));
            }
            return arrayList;
        }

        public android.content.pm.ParceledListSlice<android.content.pm.PackageInstaller.SessionInfo> getAllSessions(java.lang.String str) {
            int[] enabledProfileIds;
            verifyCallingPackage(str);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            final int callingUid = android.os.Binder.getCallingUid();
            if (!canAccessHiddenProfile(callingUid, android.os.Binder.getCallingPid())) {
                enabledProfileIds = this.mUm.getProfileIdsExcludingHidden(getCallingUserId(), true);
            } else {
                enabledProfileIds = this.mUm.getEnabledProfileIds(getCallingUserId());
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                for (int i : enabledProfileIds) {
                    arrayList.addAll(getPackageInstallerService().getAllSessions(i).getList());
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getAllSessions$1;
                        lambda$getAllSessions$1 = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.lambda$getAllSessions$1(callingUid, (android.content.pm.PackageInstaller.SessionInfo) obj);
                        return lambda$getAllSessions$1;
                    }
                });
                return new android.content.pm.ParceledListSlice<>(arrayList);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: shouldFilterSession, reason: merged with bridge method [inline-methods] */
        public boolean lambda$getAllSessions$1(int i, android.content.pm.PackageInstaller.SessionInfo sessionInfo) {
            return (sessionInfo == null || i == sessionInfo.getInstallerUid() || this.mPackageManagerInternal.canQueryPackage(i, sessionInfo.getAppPackageName())) ? false : true;
        }

        private com.android.server.pm.PackageInstallerService getPackageInstallerService() {
            if (this.mPackageInstallerService == null) {
                try {
                    this.mPackageInstallerService = android.os.ServiceManager.getService(com.android.server.pm.Settings.ATTR_PACKAGE).getPackageInstaller();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.wtf(TAG, "Error getting IPackageInstaller", e);
                }
            }
            return this.mPackageInstallerService;
        }

        private void startWatchingPackageBroadcasts() {
            if (!this.mIsWatchingPackageBroadcasts) {
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED_INTERNAL");
                intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
                this.mContext.registerReceiverAsUser(this.mPackageRemovedListener, android.os.UserHandle.ALL, intentFilter, null, this.mCallbackHandler);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mPackageMonitor.register(this.mContext, android.os.UserHandle.ALL, this.mCallbackHandler);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    this.mIsWatchingPackageBroadcasts = true;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        private void stopWatchingPackageBroadcasts() {
            if (this.mIsWatchingPackageBroadcasts) {
                this.mContext.unregisterReceiver(this.mPackageRemovedListener);
                this.mPackageMonitor.unregister();
                this.mIsWatchingPackageBroadcasts = false;
            }
        }

        void checkCallbackCount() {
            synchronized (this.mListeners) {
                try {
                    if (this.mListeners.getRegisteredCallbackCount() == 0) {
                        stopWatchingPackageBroadcasts();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private boolean canAccessProfile(int i, java.lang.String str) {
            return canAccessProfile(injectBinderCallingUid(), injectCallingUserId(), injectBinderCallingPid(), i, str);
        }

        private boolean canAccessProfile(int i, int i2, int i3, int i4, java.lang.String str) {
            if (i4 == i2 || injectHasInteractAcrossUsersFullPermission(i3, i)) {
                return true;
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                android.content.pm.UserInfo userInfo = this.mUm.getUserInfo(i2);
                if (userInfo != null && userInfo.isProfile()) {
                    android.util.Slog.w(TAG, str + " for another profile " + i4 + " from " + i2 + " not allowed");
                    return false;
                }
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                if (!isHiddenProfile(android.os.UserHandle.of(i4)) || canAccessHiddenProfile(i, i3)) {
                    return this.mUserManagerInternal.isProfileAccessible(i2, i4, str, true);
                }
                return false;
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        private boolean isHiddenProfile(android.os.UserHandle userHandle) {
            if (!android.multiuser.Flags.enableLauncherAppsHiddenProfileChecks()) {
                return false;
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                android.content.pm.UserProperties userProperties = this.mUm.getUserProperties(userHandle);
                if (userProperties == null) {
                    return false;
                }
                return userProperties.getProfileApiVisibility() == 1;
            } catch (java.lang.IllegalArgumentException e) {
                return false;
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        private void verifyCallingPackage(java.lang.String str) {
            verifyCallingPackage(str, injectBinderCallingUid());
        }

        private boolean canAccessHiddenProfile(int i, int i2) {
            if (!areHiddenApisChecksEnabled()) {
                return true;
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInternal.getPackage(i);
                if (androidPackage == null) {
                    return false;
                }
                if (!this.mRoleManager.getRoleHoldersAsUser("android.app.role.HOME", android.os.UserHandle.getUserHandleForUid(i)).contains(androidPackage.getPackageName())) {
                    return false;
                }
                if (this.mContext.checkPermission("android.permission.ACCESS_HIDDEN_PROFILES_FULL", i2, i) == 0) {
                    return true;
                }
                return this.mContext.checkPermission("android.permission.ACCESS_HIDDEN_PROFILES", i2, i) == 0;
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        private boolean areHiddenApisChecksEnabled() {
            return android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enableHidingProfiles() && android.multiuser.Flags.enableLauncherAppsHiddenProfileChecks() && android.multiuser.Flags.enablePermissionToAccessHiddenProfiles();
        }

        @com.android.internal.annotations.VisibleForTesting
        void verifyCallingPackage(java.lang.String str, int i) {
            int i2;
            try {
                i2 = this.mIPM.getPackageUid(str, 794624L, android.os.UserHandle.getUserId(i));
            } catch (android.os.RemoteException e) {
                i2 = -1;
            }
            if (i2 < 0) {
                android.util.Log.e(TAG, "Package not found: " + str);
            }
            if (i2 != i) {
                throw new java.lang.SecurityException("Calling package name mismatch");
            }
        }

        private android.content.pm.LauncherActivityInfoInternal getHiddenAppActivityInfo(java.lang.String str, int i, android.os.UserHandle userHandle) {
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(new android.content.ComponentName(str, android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME));
            java.util.List<android.content.pm.LauncherActivityInfoInternal> queryIntentLauncherActivities = queryIntentLauncherActivities(intent, i, userHandle);
            if (queryIntentLauncherActivities.size() > 0) {
                return queryIntentLauncherActivities.get(0);
            }
            return null;
        }

        public boolean shouldHideFromSuggestions(java.lang.String str, android.os.UserHandle userHandle) {
            int identifier = userHandle.getIdentifier();
            if (!canAccessProfile(identifier, "cannot get shouldHideFromSuggestions")) {
                return false;
            }
            if (com.android.server.pm.PackageArchiver.isArchivingEnabled() && str != null && isPackageArchived(str, userHandle)) {
                return true;
            }
            return (this.mPackageManagerInternal.filterAppAccess(str, android.os.Binder.getCallingUid(), identifier) || (this.mPackageManagerInternal.getDistractingPackageRestrictions(str, identifier) & 1) == 0) ? false : true;
        }

        public android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> getLauncherActivities(java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            android.content.pm.LauncherActivityInfoInternal hiddenAppActivityInfo;
            android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> queryActivitiesForUser = queryActivitiesForUser(str, new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(str2), userHandle);
            if (com.android.server.pm.PackageArchiver.isArchivingEnabled()) {
                queryActivitiesForUser = getActivitiesForArchivedApp(str2, userHandle, queryActivitiesForUser);
            }
            if (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "show_hidden_icon_apps_enabled", 1) == 0) {
                return queryActivitiesForUser;
            }
            if (queryActivitiesForUser == null) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                if (this.mUm.getUserInfo(userHandle.getIdentifier()).isManagedProfile()) {
                    injectRestoreCallingIdentity(injectClearCallingIdentity);
                    return queryActivitiesForUser;
                }
                if (this.mDpm.getDeviceOwnerComponentOnAnyUser() != null) {
                    injectRestoreCallingIdentity(injectClearCallingIdentity);
                    return queryActivitiesForUser;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(queryActivitiesForUser.getList());
                if (str2 != null) {
                    if (arrayList.size() > 0) {
                        injectRestoreCallingIdentity(injectClearCallingIdentity);
                        return queryActivitiesForUser;
                    }
                    if (shouldShowSyntheticActivity(userHandle, this.mPackageManagerInternal.getApplicationInfo(str2, 0L, injectBinderCallingUid, userHandle.getIdentifier())) && (hiddenAppActivityInfo = getHiddenAppActivityInfo(str2, injectBinderCallingUid, userHandle)) != null) {
                        arrayList.add(hiddenAppActivityInfo);
                    }
                    android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> parceledListSlice = new android.content.pm.ParceledListSlice<>(arrayList);
                    injectRestoreCallingIdentity(injectClearCallingIdentity);
                    return parceledListSlice;
                }
                java.util.HashSet hashSet = new java.util.HashSet();
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    hashSet.add(((android.content.pm.LauncherActivityInfoInternal) it.next()).getActivityInfo().packageName);
                }
                for (android.content.pm.ApplicationInfo applicationInfo : this.mPackageManagerInternal.getInstalledApplications(0L, userHandle.getIdentifier(), injectBinderCallingUid)) {
                    if (!hashSet.contains(applicationInfo.packageName)) {
                        if (shouldShowSyntheticActivity(userHandle, applicationInfo)) {
                            android.content.pm.LauncherActivityInfoInternal hiddenAppActivityInfo2 = getHiddenAppActivityInfo(applicationInfo.packageName, injectBinderCallingUid, userHandle);
                            if (hiddenAppActivityInfo2 != null) {
                                arrayList.add(hiddenAppActivityInfo2);
                            }
                        }
                    }
                }
                android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> parceledListSlice2 = new android.content.pm.ParceledListSlice<>(arrayList);
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                return parceledListSlice2;
            } catch (java.lang.Throwable th) {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
                throw th;
            }
        }

        private android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> getActivitiesForArchivedApp(@android.annotation.Nullable java.lang.String str, android.os.UserHandle userHandle, android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> parceledListSlice) {
            java.util.List<android.content.pm.LauncherActivityInfoInternal> generateLauncherActivitiesForArchivedApp = generateLauncherActivitiesForArchivedApp(str, userHandle);
            if (generateLauncherActivitiesForArchivedApp.isEmpty()) {
                return parceledListSlice;
            }
            if (parceledListSlice == null) {
                return new android.content.pm.ParceledListSlice<>(generateLauncherActivitiesForArchivedApp);
            }
            java.util.List list = parceledListSlice.getList();
            list.addAll(generateLauncherActivitiesForArchivedApp);
            return new android.content.pm.ParceledListSlice<>(list);
        }

        private boolean shouldShowSyntheticActivity(android.os.UserHandle userHandle, android.content.pm.ApplicationInfo applicationInfo) {
            com.android.server.pm.pkg.AndroidPackage androidPackage;
            return (applicationInfo == null || applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp() || isManagedProfileAdmin(userHandle, applicationInfo.packageName) || (androidPackage = this.mPackageManagerInternal.getPackage(applicationInfo.packageName)) == null || !requestsPermissions(androidPackage) || !hasDefaultEnableLauncherActivity(applicationInfo.packageName)) ? false : true;
        }

        private boolean requestsPermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
            return !com.android.internal.util.ArrayUtils.isEmpty(androidPackage.getRequestedPermissions());
        }

        private boolean hasDefaultEnableLauncherActivity(@android.annotation.NonNull java.lang.String str) {
            android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 512L, android.os.Binder.getCallingUid(), getCallingUserId());
            int size = queryIntentActivities.size();
            for (int i = 0; i < size; i++) {
                if (queryIntentActivities.get(i).activityInfo.enabled) {
                    return true;
                }
            }
            return false;
        }

        private boolean isManagedProfileAdmin(android.os.UserHandle userHandle, java.lang.String str) {
            android.content.ComponentName profileOwnerAsUser;
            java.util.List profiles = this.mUm.getProfiles(userHandle.getIdentifier());
            for (int i = 0; i < profiles.size(); i++) {
                android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) profiles.get(i);
                if (userInfo.isManagedProfile() && (profileOwnerAsUser = this.mDpm.getProfileOwnerAsUser(userInfo.getUserHandle())) != null && profileOwnerAsUser.getPackageName().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public android.content.pm.LauncherActivityInfoInternal resolveLauncherActivityInternal(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot resolve activity") || componentName == null || componentName.getPackageName() == null) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.pm.ActivityInfo activityInfo = this.mPackageManagerInternal.getActivityInfo(componentName, 786432L, injectBinderCallingUid, userHandle.getIdentifier());
                if (activityInfo == null) {
                    if (com.android.server.pm.PackageArchiver.isArchivingEnabled()) {
                        return getMatchingArchivedAppActivityInfo(componentName, userHandle);
                    }
                    return null;
                }
                android.content.pm.IncrementalStatesInfo incrementalStatesInfo = this.mPackageManagerInternal.getIncrementalStatesInfo(componentName.getPackageName(), injectBinderCallingUid, userHandle.getIdentifier());
                if (incrementalStatesInfo == null) {
                    return null;
                }
                return new android.content.pm.LauncherActivityInfoInternal(activityInfo, incrementalStatesInfo, userHandle);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.Nullable
        private android.content.pm.LauncherActivityInfoInternal getMatchingArchivedAppActivityInfo(@android.annotation.NonNull android.content.ComponentName componentName, android.os.UserHandle userHandle) {
            java.util.List<android.content.pm.LauncherActivityInfoInternal> generateLauncherActivitiesForArchivedApp = generateLauncherActivitiesForArchivedApp(componentName.getPackageName(), userHandle);
            if (generateLauncherActivitiesForArchivedApp.isEmpty()) {
                return null;
            }
            for (int i = 0; i < generateLauncherActivitiesForArchivedApp.size(); i++) {
                if (generateLauncherActivitiesForArchivedApp.get(i).getComponentName().equals(componentName)) {
                    return generateLauncherActivitiesForArchivedApp.get(i);
                }
            }
            android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Expected archived app component name: %s is not available!", new java.lang.Object[]{componentName}));
            return null;
        }

        public android.content.pm.ParceledListSlice getShortcutConfigActivities(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            if (!this.mShortcutServiceInternal.areShortcutsSupportedOnHomeScreen(userHandle.getIdentifier())) {
                return null;
            }
            return queryActivitiesForUser(str, new android.content.Intent("android.intent.action.CREATE_SHORTCUT").setPackage(str2), userHandle);
        }

        private android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> queryActivitiesForUser(java.lang.String str, android.content.Intent intent, android.os.UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot retrieve activities")) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                return new android.content.pm.ParceledListSlice<>(queryIntentLauncherActivities(intent, injectBinderCallingUid, userHandle));
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        private boolean isPackageArchived(@android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle) {
            return !getApplicationInfoForArchivedApp(str, userHandle).isEmpty();
        }

        @android.annotation.NonNull
        private java.util.List<android.content.pm.LauncherActivityInfoInternal> generateLauncherActivitiesForArchivedApp(@android.annotation.Nullable java.lang.String str, android.os.UserHandle userHandle) {
            java.util.List<android.content.pm.ApplicationInfo> applicationInfoForArchivedApp;
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot retrieve activities")) {
                return java.util.List.of();
            }
            if (str == null) {
                applicationInfoForArchivedApp = getApplicationInfoListForAllArchivedApps(userHandle);
            } else {
                applicationInfoForArchivedApp = getApplicationInfoForArchivedApp(str, userHandle);
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < applicationInfoForArchivedApp.size(); i++) {
                android.content.pm.ApplicationInfo applicationInfo = applicationInfoForArchivedApp.get(i);
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManagerInternal.getPackageStateInternal(applicationInfo.packageName);
                if (packageStateInternal != null) {
                    com.android.server.pm.pkg.ArchiveState archiveState = packageStateInternal.getUserStateOrDefault(userHandle.getIdentifier()).getArchiveState();
                    if (archiveState == null) {
                        android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Expected package: %s to be archived but missing ArchiveState in PackageState.", new java.lang.Object[]{applicationInfo.packageName}));
                    } else {
                        java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> activityInfos = archiveState.getActivityInfos();
                        for (int i2 = 0; i2 < activityInfos.size(); i2++) {
                            arrayList.add(constructLauncherActivityInfoForArchivedApp(userHandle, applicationInfo, activityInfos.get(i2)));
                        }
                    }
                }
            }
            return arrayList;
        }

        private static android.content.pm.LauncherActivityInfoInternal constructLauncherActivityInfoForArchivedApp(android.os.UserHandle userHandle, android.content.pm.ApplicationInfo applicationInfo, com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo archiveActivityInfo) {
            android.content.pm.ActivityInfo activityInfo = new android.content.pm.ActivityInfo();
            activityInfo.isArchived = applicationInfo.isArchived;
            activityInfo.applicationInfo = applicationInfo;
            activityInfo.packageName = archiveActivityInfo.getOriginalComponentName().getPackageName();
            activityInfo.name = archiveActivityInfo.getOriginalComponentName().getClassName();
            activityInfo.nonLocalizedLabel = archiveActivityInfo.getTitle();
            return new android.content.pm.LauncherActivityInfoInternal(activityInfo, new android.content.pm.IncrementalStatesInfo(false, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0L), userHandle);
        }

        @android.annotation.NonNull
        private java.util.List<android.content.pm.ApplicationInfo> getApplicationInfoListForAllArchivedApps(android.os.UserHandle userHandle) {
            java.util.List<android.content.pm.ApplicationInfo> installedApplicationsCrossUser = this.mPackageManagerInternal.getInstalledApplicationsCrossUser(4294967296L, userHandle.getIdentifier(), injectBinderCallingUid());
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < installedApplicationsCrossUser.size(); i++) {
                android.content.pm.ApplicationInfo applicationInfo = installedApplicationsCrossUser.get(i);
                if (applicationInfo != null && applicationInfo.isArchived) {
                    arrayList.add(applicationInfo);
                }
            }
            return arrayList;
        }

        @android.annotation.NonNull
        private java.util.List<android.content.pm.ApplicationInfo> getApplicationInfoForArchivedApp(@android.annotation.NonNull final java.lang.String str, final android.os.UserHandle userHandle) {
            final int injectBinderCallingUid = injectBinderCallingUid();
            android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda3
                public final java.lang.Object getOrThrow() {
                    android.content.pm.ApplicationInfo lambda$getApplicationInfoForArchivedApp$2;
                    lambda$getApplicationInfoForArchivedApp$2 = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.lambda$getApplicationInfoForArchivedApp$2(str, injectBinderCallingUid, userHandle);
                    return lambda$getApplicationInfoForArchivedApp$2;
                }
            });
            if (applicationInfo == null || !applicationInfo.isArchived) {
                return java.util.Collections.EMPTY_LIST;
            }
            return java.util.List.of(applicationInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.content.pm.ApplicationInfo lambda$getApplicationInfoForArchivedApp$2(java.lang.String str, int i, android.os.UserHandle userHandle) throws java.lang.Exception {
            return this.mPackageManagerInternal.getApplicationInfo(str, 4294967296L, i, userHandle.getIdentifier());
        }

        private java.util.List<android.content.pm.LauncherActivityInfoInternal> queryIntentLauncherActivities(android.content.Intent intent, int i, android.os.UserHandle userHandle) {
            android.content.pm.IncrementalStatesInfo incrementalStatesInfo;
            java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 786432L, i, userHandle.getIdentifier());
            int size = queryIntentActivities.size();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i2 = 0; i2 < size; i2++) {
                android.content.pm.ResolveInfo resolveInfo = queryIntentActivities.get(i2);
                java.lang.String str = resolveInfo.activityInfo.packageName;
                if (str != null && (incrementalStatesInfo = this.mPackageManagerInternal.getIncrementalStatesInfo(str, i, userHandle.getIdentifier())) != null) {
                    arrayList.add(new android.content.pm.LauncherActivityInfoInternal(resolveInfo.activityInfo, incrementalStatesInfo, userHandle));
                }
            }
            return arrayList;
        }

        public android.content.IntentSender getShortcutConfigActivityIntent(java.lang.String str, final android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot check package")) {
                return null;
            }
            java.util.Objects.requireNonNull(componentName);
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.Intent intent = new android.content.Intent("android.intent.action.CREATE_SHORTCUT").setPackage(componentName.getPackageName());
                if (!this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 786432L, injectBinderCallingUid, userHandle.getIdentifier()).stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getShortcutConfigActivityIntent$3;
                        lambda$getShortcutConfigActivityIntent$3 = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.lambda$getShortcutConfigActivityIntent$3(componentName, (android.content.pm.ResolveInfo) obj);
                        return lambda$getShortcutConfigActivityIntent$3;
                    }
                })) {
                    return null;
                }
                android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(this.mContext, 0, new android.content.Intent("android.intent.action.CREATE_SHORTCUT").setComponent(componentName), 1409286144, null, userHandle);
                return activityAsUser != null ? activityAsUser.getIntentSender() : null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getShortcutConfigActivityIntent$3(android.content.ComponentName componentName, android.content.pm.ResolveInfo resolveInfo) {
            return componentName.getClassName().equals(resolveInfo.activityInfo.name);
        }

        @android.annotation.Nullable
        public android.app.PendingIntent getShortcutIntent(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.NonNull android.os.UserHandle userHandle) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(str2);
            java.util.Objects.requireNonNull(str3);
            java.util.Objects.requireNonNull(userHandle);
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot get shortcuts")) {
                return null;
            }
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            this.mShortcutServiceInternal.createShortcutIntentsAsync(getCallingUserId(), str, str2, str3, userHandle.getIdentifier(), injectBinderCallingPid(), injectBinderCallingUid(), androidFuture);
            try {
                android.content.Intent[] intentArr = (android.content.Intent[]) androidFuture.get();
                if (intentArr == null || intentArr.length == 0) {
                    return null;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return injectCreatePendingIntent(0, intentArr, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, bundle, str2, this.mPackageManagerInternal.getPackageUid(str2, 268435456L, userHandle.getIdentifier()));
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                return null;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
        
            if (r10.applicationInfo.isArchived != false) goto L19;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean isPackageEnabled(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            long j;
            boolean z = false;
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot check package")) {
                return false;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.pm.PackageArchiver.isArchivingEnabled()) {
                    j = 786432;
                } else {
                    j = 4295753728L;
                }
                android.content.pm.PackageInfo packageInfo = this.mPackageManagerInternal.getPackageInfo(str2, j, injectBinderCallingUid, userHandle.getIdentifier());
                if (packageInfo != null) {
                    if (!packageInfo.applicationInfo.enabled) {
                    }
                    z = true;
                }
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.os.Bundle getSuspendedPackageLauncherExtras(java.lang.String str, android.os.UserHandle userHandle) {
            int injectBinderCallingUid = injectBinderCallingUid();
            int identifier = userHandle.getIdentifier();
            if (canAccessProfile(identifier, "Cannot get launcher extras") && !this.mPackageManagerInternal.filterAppAccess(str, injectBinderCallingUid, identifier)) {
                return this.mPackageManagerInternal.getSuspendedPackageLauncherExtras(str, identifier);
            }
            return null;
        }

        public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, java.lang.String str2, int i, android.os.UserHandle userHandle) throws android.os.RemoteException {
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot check package")) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return this.mPackageManagerInternal.getApplicationInfo(str2, i, injectBinderCallingUid, userHandle.getIdentifier());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.content.pm.LauncherApps.AppUsageLimit getAppUsageLimit(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
            verifyCallingPackage(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot access usage limit")) {
                return null;
            }
            if (!this.mActivityTaskManagerInternal.isCallerRecents(android.os.Binder.getCallingUid())) {
                throw new java.lang.SecurityException("Caller is not the recents app");
            }
            android.app.usage.UsageStatsManagerInternal.AppUsageLimitData appUsageLimit = this.mUsageStatsManagerInternal.getAppUsageLimit(str2, userHandle);
            if (appUsageLimit == null) {
                return null;
            }
            return new android.content.pm.LauncherApps.AppUsageLimit(appUsageLimit.getTotalUsageLimit(), appUsageLimit.getUsageRemaining());
        }

        private void ensureShortcutPermission(@android.annotation.NonNull java.lang.String str) {
            ensureShortcutPermission(injectBinderCallingUid(), injectBinderCallingPid(), str);
        }

        private void ensureShortcutPermission(int i, int i2, @android.annotation.NonNull java.lang.String str) {
            verifyCallingPackage(str, i);
            if (!this.mShortcutServiceInternal.hasShortcutHostPermission(android.os.UserHandle.getUserId(i), str, i2, i)) {
                throw new java.lang.SecurityException("Caller can't access shortcut information");
            }
        }

        private void ensureStrictAccessShortcutsPermission(@android.annotation.NonNull java.lang.String str) {
            verifyCallingPackage(str);
            if (!injectHasAccessShortcutsPermission(injectBinderCallingPid(), injectBinderCallingUid())) {
                throw new java.lang.SecurityException("Caller can't access shortcut information");
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        boolean injectHasAccessShortcutsPermission(int i, int i2) {
            return this.mContext.checkPermission("android.permission.ACCESS_SHORTCUTS", i, i2) == 0;
        }

        @com.android.internal.annotations.VisibleForTesting
        boolean injectHasInteractAcrossUsersFullPermission(int i, int i2) {
            return this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2) == 0;
        }

        @com.android.internal.annotations.VisibleForTesting
        android.app.PendingIntent injectCreatePendingIntent(int i, @android.annotation.NonNull android.content.Intent[] intentArr, int i2, android.os.Bundle bundle, java.lang.String str, int i3) {
            return this.mActivityManagerInternal.getPendingIntentActivityAsApp(i, intentArr, i2, (android.os.Bundle) null, str, i3);
        }

        public android.content.pm.ParceledListSlice getShortcuts(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, @android.annotation.NonNull android.os.UserHandle userHandle) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot get shortcuts")) {
                return new android.content.pm.ParceledListSlice(java.util.Collections.EMPTY_LIST);
            }
            long changedSince = shortcutQueryWrapper.getChangedSince();
            java.lang.String str2 = shortcutQueryWrapper.getPackage();
            java.util.List shortcutIds = shortcutQueryWrapper.getShortcutIds();
            java.util.List locusIds = shortcutQueryWrapper.getLocusIds();
            android.content.ComponentName activity = shortcutQueryWrapper.getActivity();
            int queryFlags = shortcutQueryWrapper.getQueryFlags();
            if (shortcutIds != null && str2 == null) {
                throw new java.lang.IllegalArgumentException("To query by shortcut ID, package name must also be set");
            }
            if (locusIds != null && str2 == null) {
                throw new java.lang.IllegalArgumentException("To query by locus ID, package name must also be set");
            }
            if ((shortcutQueryWrapper.getQueryFlags() & 2048) != 0) {
                ensureStrictAccessShortcutsPermission(str);
            }
            return new android.content.pm.ParceledListSlice(this.mShortcutServiceInternal.getShortcuts(getCallingUserId(), str, changedSince, str2, shortcutIds, locusIds, activity, queryFlags, userHandle.getIdentifier(), injectBinderCallingPid(), injectBinderCallingUid()));
        }

        public void getShortcutsAsync(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot get shortcuts")) {
                androidFuture.complete(java.util.Collections.EMPTY_LIST);
                return;
            }
            long changedSince = shortcutQueryWrapper.getChangedSince();
            java.lang.String str2 = shortcutQueryWrapper.getPackage();
            java.util.List shortcutIds = shortcutQueryWrapper.getShortcutIds();
            java.util.List locusIds = shortcutQueryWrapper.getLocusIds();
            android.content.ComponentName activity = shortcutQueryWrapper.getActivity();
            int queryFlags = shortcutQueryWrapper.getQueryFlags();
            if (shortcutIds != null && str2 == null) {
                throw new java.lang.IllegalArgumentException("To query by shortcut ID, package name must also be set");
            }
            if (locusIds != null && str2 == null) {
                throw new java.lang.IllegalArgumentException("To query by locus ID, package name must also be set");
            }
            if ((shortcutQueryWrapper.getQueryFlags() & 2048) != 0) {
                ensureStrictAccessShortcutsPermission(str);
            }
            this.mShortcutServiceInternal.getShortcutsAsync(getCallingUserId(), str, changedSince, str2, shortcutIds, locusIds, activity, queryFlags, userHandle.getIdentifier(), injectBinderCallingPid(), injectBinderCallingUid(), androidFuture);
        }

        public void registerShortcutChangeCallback(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, @android.annotation.NonNull android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) {
            ensureShortcutPermission(str);
            if (shortcutQueryWrapper.getShortcutIds() != null && shortcutQueryWrapper.getPackage() == null) {
                throw new java.lang.IllegalArgumentException("To query by shortcut ID, package name must also be set");
            }
            if (shortcutQueryWrapper.getLocusIds() != null && shortcutQueryWrapper.getPackage() == null) {
                throw new java.lang.IllegalArgumentException("To query by locus ID, package name must also be set");
            }
            android.os.UserHandle of = android.os.UserHandle.of(injectCallingUserId());
            if (injectHasInteractAcrossUsersFullPermission(injectBinderCallingPid(), injectBinderCallingUid())) {
                of = null;
            }
            this.mShortcutChangeHandler.addShortcutChangeCallback(iShortcutChangeCallback, shortcutQueryWrapper, of);
        }

        public void unregisterShortcutChangeCallback(java.lang.String str, android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) {
            ensureShortcutPermission(str);
            this.mShortcutChangeHandler.removeShortcutChangeCallback(iShortcutChangeCallback);
        }

        public void pinShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle) {
            if (!this.mShortcutServiceInternal.areShortcutsSupportedOnHomeScreen(userHandle.getIdentifier())) {
                ensureStrictAccessShortcutsPermission(str);
            } else {
                ensureShortcutPermission(str);
            }
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot pin shortcuts")) {
                return;
            }
            this.mShortcutServiceInternal.pinShortcuts(getCallingUserId(), str, str2, list, userHandle.getIdentifier());
        }

        public void cacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) {
            ensureStrictAccessShortcutsPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot cache shortcuts")) {
                return;
            }
            this.mShortcutServiceInternal.cacheShortcuts(getCallingUserId(), str, str2, list, userHandle.getIdentifier(), toShortcutsCacheFlags(i));
        }

        public void uncacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) {
            ensureStrictAccessShortcutsPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot uncache shortcuts")) {
                return;
            }
            this.mShortcutServiceInternal.uncacheShortcuts(getCallingUserId(), str, str2, list, userHandle.getIdentifier(), toShortcutsCacheFlags(i));
        }

        public int getShortcutIconResId(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(i, "Cannot access shortcuts")) {
                return 0;
            }
            return this.mShortcutServiceInternal.getShortcutIconResId(getCallingUserId(), str, str2, str3, i);
        }

        public android.os.ParcelFileDescriptor getShortcutIconFd(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(i, "Cannot access shortcuts")) {
                return null;
            }
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            this.mShortcutServiceInternal.getShortcutIconFdAsync(getCallingUserId(), str, str2, str3, i, androidFuture);
            try {
                return (android.os.ParcelFileDescriptor) androidFuture.get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public java.lang.String getShortcutIconUri(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(i, "Cannot access shortcuts")) {
                return null;
            }
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            this.mShortcutServiceInternal.getShortcutIconUriAsync(getCallingUserId(), str, str2, str3, i, androidFuture);
            try {
                return (java.lang.String) androidFuture.get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public boolean hasShortcutHostPermission(java.lang.String str) {
            verifyCallingPackage(str);
            return this.mShortcutServiceInternal.hasShortcutHostPermission(getCallingUserId(), str, injectBinderCallingPid(), injectBinderCallingUid());
        }

        @android.annotation.NonNull
        public java.util.Map<java.lang.String, android.content.pm.LauncherActivityInfoInternal> getActivityOverrides(java.lang.String str, int i) {
            ensureShortcutPermission(str);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                android.os.UserHandle managedProfile = getManagedProfile(i);
                if (managedProfile == null) {
                    return arrayMap;
                }
                for (java.util.Map.Entry entry : android.app.admin.DevicePolicyCache.getInstance().getLauncherShortcutOverrides().entrySet()) {
                    java.util.List<android.content.pm.LauncherActivityInfoInternal> queryIntentLauncherActivities = queryIntentLauncherActivities(new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage((java.lang.String) entry.getValue()), callingUid, managedProfile);
                    if (!queryIntentLauncherActivities.isEmpty()) {
                        arrayMap.put((java.lang.String) entry.getKey(), queryIntentLauncherActivities.get(0));
                    }
                }
                return arrayMap;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.Nullable
        private android.os.UserHandle getManagedProfile(int i) {
            for (android.content.pm.UserInfo userInfo : this.mUm.getProfiles(i)) {
                if (userInfo.isManagedProfile()) {
                    return userInfo.getUserHandle();
                }
            }
            return null;
        }

        public boolean startShortcut(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.graphics.Rect rect, android.os.Bundle bundle, int i) {
            return startShortcutInner(injectBinderCallingUid(), injectBinderCallingPid(), injectCallingUserId(), str, str2, str3, str4, rect, bundle, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean startShortcutInner(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.graphics.Rect rect, android.os.Bundle bundle, int i4) {
            android.os.Bundle bundle2;
            android.os.Bundle bundle3;
            verifyCallingPackage(str, i);
            if (!canAccessProfile(i4, "Cannot start activity")) {
                return false;
            }
            if (!this.mShortcutServiceInternal.isPinnedByCaller(i3, str, str2, str4, i4)) {
                ensureShortcutPermission(i, i2, str);
            }
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            this.mShortcutServiceInternal.createShortcutIntentsAsync(getCallingUserId(), str, str2, str4, i4, injectBinderCallingPid(), injectBinderCallingUid(), androidFuture);
            try {
                android.content.Intent[] intentArr = (android.content.Intent[]) androidFuture.get();
                if (intentArr == null || intentArr.length == 0) {
                    return false;
                }
                android.app.ActivityOptions fromBundle = android.app.ActivityOptions.fromBundle(bundle);
                if (fromBundle != null) {
                    if (fromBundle.isApplyActivityFlagsForBubbles()) {
                        intentArr[0].addFlags(524288);
                        intentArr[0].addFlags(134217728);
                    }
                    if (fromBundle.isApplyMultipleTaskFlagForShortcut()) {
                        intentArr[0].addFlags(134217728);
                    }
                    if (fromBundle.isApplyNoUserActionFlagForShortcut()) {
                        intentArr[0].addFlags(262144);
                    }
                }
                intentArr[0].addFlags(268435456);
                intentArr[0].setSourceBounds(rect);
                java.lang.String shortcutStartingThemeResName = this.mShortcutServiceInternal.getShortcutStartingThemeResName(i3, str, str2, str4, i4);
                if (shortcutStartingThemeResName != null && !shortcutStartingThemeResName.isEmpty()) {
                    if (bundle != null) {
                        bundle3 = bundle;
                    } else {
                        bundle3 = new android.os.Bundle();
                    }
                    bundle3.putString("android.activity.splashScreenTheme", shortcutStartingThemeResName);
                    bundle2 = bundle3;
                } else {
                    bundle2 = bundle;
                }
                return startShortcutIntentsAsPublisher(intentArr, str2, str3, bundle2, i4);
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                return false;
            }
        }

        private boolean startShortcutIntentsAsPublisher(@android.annotation.NonNull android.content.Intent[] intentArr, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.os.Bundle bundle, int i) {
            try {
                int startActivitiesAsPackage = this.mActivityTaskManagerInternal.startActivitiesAsPackage(str, str2, i, intentArr, getActivityOptionsForLauncher(bundle));
                if (android.app.ActivityManager.isStartResultSuccessful(startActivitiesAsPackage)) {
                    return true;
                }
                android.util.Log.e(TAG, "Couldn't start activity, code=" + startActivitiesAsPackage);
                return false;
            } catch (java.lang.SecurityException e) {
                return false;
            }
        }

        private android.os.Bundle getActivityOptionsForLauncher(android.os.Bundle bundle) {
            if (bundle == null) {
                return android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
            }
            android.app.ActivityOptions fromBundle = android.app.ActivityOptions.fromBundle(bundle);
            if (fromBundle.getPendingIntentBackgroundActivityStartMode() == 0) {
                return fromBundle.setPendingIntentBackgroundActivityStartMode(1).toBundle();
            }
            return bundle;
        }

        public boolean isActivityEnabled(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            boolean z = false;
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot check component")) {
                return false;
            }
            if (com.android.server.pm.PackageArchiver.isArchivingEnabled() && componentName != null && componentName.getPackageName() != null) {
                java.util.List<android.content.pm.LauncherActivityInfoInternal> generateLauncherActivitiesForArchivedApp = generateLauncherActivitiesForArchivedApp(componentName.getPackageName(), userHandle);
                if (!generateLauncherActivitiesForArchivedApp.isEmpty()) {
                    for (int i = 0; i < generateLauncherActivitiesForArchivedApp.size(); i++) {
                        if (generateLauncherActivitiesForArchivedApp.get(i).getComponentName().equals(componentName)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            switch (this.mPackageManagerInternal.getComponentEnabledSetting(componentName, injectBinderCallingUid, userHandle.getIdentifier())) {
                case 0:
                default:
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        android.content.pm.ActivityInfo activityInfo = this.mPackageManagerInternal.getActivityInfo(componentName, 786432L, injectBinderCallingUid, userHandle.getIdentifier());
                        if (activityInfo != null) {
                            if (activityInfo.isEnabled()) {
                                z = true;
                            }
                        }
                        return z;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                case 1:
                    return true;
                case 2:
                case 3:
                case 4:
                    return false;
            }
        }

        public void startSessionDetailsActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.pm.PackageInstaller.SessionInfo sessionInfo, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
            int identifier = userHandle.getIdentifier();
            if (!canAccessProfile(identifier, "Cannot start details activity")) {
                return;
            }
            android.content.Intent putExtra = new android.content.Intent("android.intent.action.VIEW").setData(new android.net.Uri.Builder().scheme("market").authority("details").appendQueryParameter("id", sessionInfo.appPackageName).build()).putExtra("android.intent.extra.REFERRER", new android.net.Uri.Builder().scheme("android-app").authority(str).build());
            putExtra.setSourceBounds(rect);
            this.mActivityTaskManagerInternal.startActivityAsUser(iApplicationThread, str, str2, putExtra, null, 268435456, getActivityOptionsForLauncher(bundle), identifier);
        }

        public android.app.PendingIntent getActivityLaunchIntent(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) {
            if (this.mContext.checkPermission("android.permission.START_TASKS_FROM_RECENTS", injectBinderCallingPid(), injectBinderCallingUid()) != 0) {
                throw new java.lang.SecurityException("Permission START_TASKS_FROM_RECENTS required");
            }
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot start activity")) {
                throw new android.content.ActivityNotFoundException("Activity could not be found");
            }
            android.content.Intent mainActivityLaunchIntent = getMainActivityLaunchIntent(componentName, userHandle, false);
            if (mainActivityLaunchIntent == null) {
                throw new java.lang.SecurityException("Attempt to launch activity without  category Intent.CATEGORY_LAUNCHER " + componentName);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return android.app.PendingIntent.getActivityAsUser(this.mContext, 0, mainActivityLaunchIntent, 33554432, null, userHandle);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.Nullable
        public android.content.pm.LauncherUserInfo getLauncherUserInfo(@android.annotation.NonNull android.os.UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Can't access LauncherUserInfo for another user")) {
                return null;
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                return this.mUserManagerInternal.getLauncherUserInfo(userHandle.getIdentifier());
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        @android.annotation.NonNull
        public java.util.List<java.lang.String> getPreInstalledSystemPackages(android.os.UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Can't access preinstalled packages for another user")) {
                return new java.util.ArrayList();
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.Set preInstallableSystemPackages = this.mUm.getPreInstallableSystemPackages(this.mUm.getUserInfo(userHandle.getIdentifier()).userType);
                return preInstallableSystemPackages == null ? new java.util.ArrayList() : java.util.List.copyOf(preInstallableSystemPackages);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.Nullable
        public android.content.IntentSender getAppMarketActivityIntent(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull android.os.UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Can't access AppMarketActivity for another user")) {
                return null;
            }
            int callingUserId = getCallingUserId();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (str2 == null) {
                    return buildAppMarketIntentSenderForUser(userHandle);
                }
                java.lang.String installerPackage = getInstallerPackage(str2, callingUserId);
                if (installerPackage != null && this.mPackageManagerInternal.getPackageUid(installerPackage, 0L, userHandle.getIdentifier()) >= 0) {
                    android.content.Intent buildMarketPackageInfoIntent = buildMarketPackageInfoIntent(str2, installerPackage, str);
                    return this.mPackageManagerInternal.queryIntentActivities(buildMarketPackageInfoIntent, buildMarketPackageInfoIntent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 131072L, android.os.Process.myUid(), userHandle.getIdentifier()).isEmpty() ? buildAppMarketIntentSenderForUser(userHandle) : buildIntentSenderForUser(buildMarketPackageInfoIntent, userHandle);
                }
                return buildAppMarketIntentSenderForUser(userHandle);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.Nullable
        public android.content.IntentSender getPrivateSpaceSettingsIntent() {
            if (!canAccessHiddenProfile(android.content.pm.ILauncherApps.Stub.getCallingUid(), android.content.pm.ILauncherApps.Stub.getCallingPid())) {
                android.util.Slog.e(TAG, "Caller cannot access hidden profiles");
                return null;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.Intent intent = new android.content.Intent(com.android.server.pm.LauncherAppsService.PS_SETTINGS_INTENT);
                intent.setFlags(268468224);
                android.app.PendingIntent activity = android.app.PendingIntent.getActivity(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
                return activity != null ? activity.getIntentSender() : null;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.Nullable
        private android.content.IntentSender buildAppMarketIntentSenderForUser(@android.annotation.NonNull android.os.UserHandle userHandle) {
            android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.APP_MARKET");
            intent.setFlags(268468224);
            return buildIntentSenderForUser(intent, userHandle);
        }

        @android.annotation.Nullable
        private android.content.IntentSender buildIntentSenderForUser(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.os.UserHandle userHandle) {
            android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, null, userHandle);
            if (activityAsUser == null) {
                return null;
            }
            return activityAsUser.getIntentSender();
        }

        @android.annotation.Nullable
        private java.lang.String getInstallerPackage(@android.annotation.NonNull java.lang.String str, int i) {
            try {
                return this.mIPM.getInstallSourceInfo(str, i).getInstallingPackageName();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Couldn't find installer for " + str, e);
                return null;
            }
        }

        @android.annotation.NonNull
        private android.content.Intent buildMarketPackageInfoIntent(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
            return new android.content.Intent("android.intent.action.VIEW").setData(new android.net.Uri.Builder().scheme("market").authority("details").appendQueryParameter("id", str).build()).putExtra("android.intent.extra.REFERRER", new android.net.Uri.Builder().scheme("android-app").authority(str3).build()).setPackage(str2).setFlags(268435456);
        }

        public void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot start activity")) {
                return;
            }
            android.content.Intent mainActivityLaunchIntent = getMainActivityLaunchIntent(componentName, userHandle, true);
            if (mainActivityLaunchIntent == null) {
                throw new java.lang.SecurityException("Attempt to launch activity without  category Intent.CATEGORY_LAUNCHER " + componentName);
            }
            mainActivityLaunchIntent.setSourceBounds(rect);
            this.mActivityTaskManagerInternal.startActivityAsUser(iApplicationThread, str, str2, mainActivityLaunchIntent, null, 268435456, getActivityOptionsForLauncher(bundle), userHandle.getIdentifier());
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x008c, code lost:
        
            if (r2 != false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x008e, code lost:
        
            if (r15 == false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0094, code lost:
        
            if (com.android.server.pm.PackageArchiver.isArchivingEnabled() == false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x009a, code lost:
        
            if (getMatchingArchivedAppActivityInfo(r13, r14) == null) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x009c, code lost:
        
            r7.setPackage(null);
            r7.setComponent(r13);
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x00a4, code lost:
        
            if (r4 != false) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x00a7, code lost:
        
            android.os.Binder.restoreCallingIdentity(r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x00aa, code lost:
        
            return null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x00ab, code lost:
        
            android.os.Binder.restoreCallingIdentity(r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x00af, code lost:
        
            return r7;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x00a3, code lost:
        
            r4 = r2;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private android.content.Intent getMainActivityLaunchIntent(android.content.ComponentName componentName, android.os.UserHandle userHandle, boolean z) {
            android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(270532608);
            intent.setPackage(componentName.getPackageName());
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 786432L, injectBinderCallingUid, userHandle.getIdentifier());
                int size = queryIntentActivities.size();
                boolean z2 = false;
                int i = 0;
                while (true) {
                    boolean z3 = true;
                    if (i >= size) {
                        break;
                    }
                    android.content.pm.ActivityInfo activityInfo = queryIntentActivities.get(i).activityInfo;
                    if (!activityInfo.packageName.equals(componentName.getPackageName()) || !activityInfo.name.equals(componentName.getClassName())) {
                        i++;
                    } else {
                        if (!activityInfo.exported) {
                            throw new java.lang.SecurityException("Cannot launch non-exported components " + componentName);
                        }
                        intent.setPackage(null);
                        intent.setComponent(componentName);
                        z2 = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void showAppDetailsAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
            int i;
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot show app details")) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.lang.String packageName = componentName.getPackageName();
                try {
                    i = this.mContext.getPackageManager().getApplicationInfo(packageName, 4194304).uid;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.d(TAG, "package not found: " + e);
                    i = -1;
                }
                android.content.Intent intent = new android.content.Intent("android.settings.APPLICATION_DETAILS_SETTINGS", android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, packageName, null));
                intent.putExtra("uId", i);
                intent.setFlags(268468224);
                intent.setSourceBounds(rect);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                this.mActivityTaskManagerInternal.startActivityAsUser(iApplicationThread, str, str2, intent, null, 268435456, getActivityOptionsForLauncher(bundle), userHandle.getIdentifier());
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor2, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor3, @android.annotation.Nullable java.lang.String[] strArr, android.os.ShellCallback shellCallback, @android.annotation.Nullable android.os.ResultReceiver resultReceiver) {
            int injectBinderCallingUid = injectBinderCallingUid();
            if (injectBinderCallingUid != 2000 && injectBinderCallingUid != 0) {
                throw new java.lang.SecurityException("Caller must be shell");
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                int exec = new com.android.server.pm.LauncherAppsService.LauncherAppsImpl.LauncherAppsShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
                if (resultReceiver != null) {
                    resultReceiver.send(exec, null);
                }
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class LauncherAppsShellCommand extends android.os.ShellCommand {
            private LauncherAppsShellCommand() {
            }

            public int onCommand(@android.annotation.Nullable java.lang.String str) {
                if ("dump-view-hierarchies".equals(str)) {
                    dumpViewCaptureDataToShell();
                    return 0;
                }
                return handleDefaultCommands(str);
            }

            private void dumpViewCaptureDataToShell() {
                try {
                    final java.util.zip.ZipOutputStream zipOutputStream = new java.util.zip.ZipOutputStream(getRawOutputStream());
                    try {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.forEachViewCaptureWindow(new java.util.function.BiConsumer() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$LauncherAppsShellCommand$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.LauncherAppsShellCommand.this.lambda$dumpViewCaptureDataToShell$0(zipOutputStream, (java.lang.String) obj, (java.io.InputStream) obj2);
                            }
                        });
                        zipOutputStream.close();
                    } finally {
                    }
                } catch (java.io.IOException e) {
                    getErrPrintWriter().write("Failed to create or close zip output stream: " + e.getMessage());
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$dumpViewCaptureDataToShell$0(java.util.zip.ZipOutputStream zipOutputStream, java.lang.String str, java.io.InputStream inputStream) {
                try {
                    zipOutputStream.putNextEntry(new java.util.zip.ZipEntry("FS" + str));
                    com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.transferViewCaptureData(inputStream, zipOutputStream);
                    zipOutputStream.closeEntry();
                } catch (java.io.IOException e) {
                    getErrPrintWriter().write("Failed to output " + str + " data to shell: " + e.getMessage());
                }
            }

            public void onHelp() {
                java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                outPrintWriter.println("Usage: cmd launcherapps COMMAND [options ...]");
                outPrintWriter.println();
                outPrintWriter.println("cmd launcherapps dump-view-hierarchies");
                outPrintWriter.println("    Output captured view hierarchies. Files will be generated in ");
                outPrintWriter.println("    `/data/misc/wmtrace/`. After pulling the data to your device,");
                outPrintWriter.println("     you can upload / visualize it at `go/winscope`.");
                outPrintWriter.println();
            }
        }

        protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
            super.dump(fileDescriptor, printWriter, strArr);
            forEachViewCaptureWindow(new java.util.function.BiConsumer() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.dumpViewCaptureDataToWmTrace((java.lang.String) obj, (java.io.InputStream) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpViewCaptureDataToWmTrace(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.InputStream inputStream) {
            java.nio.file.Path path = java.nio.file.Paths.get(str, new java.lang.String[0]);
            try {
                java.io.OutputStream newOutputStream = java.nio.file.Files.newOutputStream(path, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
                try {
                    transferViewCaptureData(inputStream, newOutputStream);
                    java.nio.file.Files.setPosixFilePermissions(path, com.android.server.pm.LauncherAppsService.WM_TRACE_FILE_PERMISSIONS);
                    if (newOutputStream != null) {
                        newOutputStream.close();
                    }
                } finally {
                }
            } catch (java.io.IOException e) {
                android.util.Log.d(TAG, "failed to write data to " + str + " in wmtrace dir", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void transferViewCaptureData(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
            new com.android.internal.util.SizedInputStream(new java.io.DataInputStream(inputStream), r0.readInt()).transferTo(outputStream);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void forEachViewCaptureWindow(@android.annotation.NonNull final java.util.function.BiConsumer<java.lang.String, java.io.InputStream> biConsumer) {
            try {
                this.mOnDumpExecutor.submit(new java.lang.Runnable() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.lambda$forEachViewCaptureWindow$4(biConsumer);
                    }
                }).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.e(TAG, "background work was interrupted", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forEachViewCaptureWindow$4(java.util.function.BiConsumer biConsumer) {
            try {
                for (int beginBroadcast = this.mDumpCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                    java.lang.String str = com.android.server.pm.LauncherAppsService.WM_TRACE_DIR + ((java.lang.String) this.mDumpCallbacks.getBroadcastCookie(beginBroadcast)) + "_" + beginBroadcast + com.android.server.pm.LauncherAppsService.VC_FILE_SUFFIX;
                    try {
                        android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
                        this.mDumpCallbacks.getBroadcastItem(beginBroadcast).onDump(createPipe[1]);
                        android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(createPipe[0]);
                        biConsumer.accept(str, autoCloseInputStream);
                        autoCloseInputStream.close();
                    } catch (java.lang.Exception e) {
                        android.util.Log.d(TAG, "failed to pipe view capture data", e);
                    }
                }
            } finally {
                this.mDumpCallbacks.finishBroadcast();
            }
        }

        @android.annotation.RequiresPermission("android.permission.READ_FRAME_BUFFER")
        public void registerDumpCallback(@android.annotation.NonNull android.window.IDumpCallback iDumpCallback) {
            if (android.content.PermissionChecker.checkCallingOrSelfPermissionForPreflight(this.mContext, "android.permission.READ_FRAME_BUFFER") == 0) {
                this.mDumpCallbacks.register(iDumpCallback, this.mContext.getPackageManager().getNameForUid(android.os.Binder.getCallingUid()));
            } else {
                android.util.Log.w(TAG, "caller lacks permissions to registerDumpCallback");
            }
        }

        @android.annotation.RequiresPermission("android.permission.READ_FRAME_BUFFER")
        public void unRegisterDumpCallback(@android.annotation.NonNull android.window.IDumpCallback iDumpCallback) {
            if (android.content.PermissionChecker.checkCallingOrSelfPermissionForPreflight(this.mContext, "android.permission.READ_FRAME_BUFFER") == 0) {
                this.mDumpCallbacks.unregister(iDumpCallback);
            } else {
                android.util.Log.w(TAG, "caller lacks permissions to unRegisterDumpCallback");
            }
        }

        public void setArchiveCompatibilityOptions(final boolean z, final boolean z2) {
            final int callingUid = android.os.Binder.getCallingUid();
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.lambda$setArchiveCompatibilityOptions$5(callingUid, z, z2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setArchiveCompatibilityOptions$5(int i, boolean z, boolean z2) throws java.lang.Exception {
            this.mAppOpsManager.setUidMode(145, i, !z ? 1 : 0);
            this.mAppOpsManager.setUidMode(146, i, !z2 ? 1 : 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEnabledProfileOf(com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie, android.os.UserHandle userHandle, java.lang.String str) {
            if (!isHiddenProfile(userHandle) || canAccessHiddenProfile(broadcastCookie.callingUid, broadcastCookie.callingPid)) {
                return this.mUserManagerInternal.isProfileAccessible(broadcastCookie.user.getIdentifier(), userHandle.getIdentifier(), str, false);
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPackageVisibleToListener(java.lang.String str, com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie, android.os.UserHandle userHandle) {
            return !this.mPackageManagerInternal.filterAppAccess(str, broadcastCookie.callingUid, userHandle.getIdentifier(), false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isCallingAppIdAllowed(int[] iArr, int i) {
            return iArr == null || i < 10000 || java.util.Arrays.binarySearch(iArr, i) > -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String[] getFilteredPackageNames(java.lang.String[] strArr, com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie, android.os.UserHandle userHandle) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : strArr) {
                if (isPackageVisibleToListener(str, broadcastCookie, userHandle)) {
                    arrayList.add(str);
                }
            }
            return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
        }

        private int toShortcutsCacheFlags(int i) {
            int i2;
            if (i == 0) {
                i2 = 16384;
            } else if (i == 1) {
                i2 = 1073741824;
            } else if (i != 2) {
                i2 = 0;
            } else {
                i2 = 536870912;
            }
            com.android.internal.util.Preconditions.checkArgumentPositive(i2, "Invalid cache owner");
            return i2;
        }

        @com.android.internal.annotations.VisibleForTesting
        void postToPackageMonitorHandler(java.lang.Runnable runnable) {
            this.mCallbackHandler.post(runnable);
        }

        void registerLoadingProgressForIncrementalApps() {
            java.util.List<android.os.UserHandle> userProfiles = this.mUm.getUserProfiles();
            if (userProfiles == null) {
                return;
            }
            for (final android.os.UserHandle userHandle : userProfiles) {
                this.mPackageManagerInternal.forEachInstalledPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.lambda$registerLoadingProgressForIncrementalApps$6(userHandle, (com.android.server.pm.pkg.AndroidPackage) obj);
                    }
                }, userHandle.getIdentifier());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerLoadingProgressForIncrementalApps$6(android.os.UserHandle userHandle, com.android.server.pm.pkg.AndroidPackage androidPackage) {
            java.lang.String packageName = androidPackage.getPackageName();
            android.content.pm.IncrementalStatesInfo incrementalStatesInfo = this.mPackageManagerInternal.getIncrementalStatesInfo(packageName, android.os.Process.myUid(), userHandle.getIdentifier());
            if (incrementalStatesInfo != null && incrementalStatesInfo.isLoading()) {
                this.mPackageManagerInternal.registerInstalledLoadingProgressCallback(packageName, new com.android.server.pm.LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback(packageName, userHandle), userHandle.getIdentifier());
            }
        }

        public static class ShortcutChangeHandler implements android.content.pm.LauncherApps.ShortcutChangeCallback {
            private final android.os.RemoteCallbackList<android.content.pm.IShortcutChangeCallback> mCallbacks = new android.os.RemoteCallbackList<>();
            private final com.android.server.pm.UserManagerInternal mUserManagerInternal;

            ShortcutChangeHandler(com.android.server.pm.UserManagerInternal userManagerInternal) {
                this.mUserManagerInternal = userManagerInternal;
            }

            public synchronized void addShortcutChangeCallback(android.content.pm.IShortcutChangeCallback iShortcutChangeCallback, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.os.UserHandle userHandle) {
                this.mCallbacks.unregister(iShortcutChangeCallback);
                this.mCallbacks.register(iShortcutChangeCallback, new android.util.Pair(shortcutQueryWrapper, userHandle));
            }

            public synchronized void removeShortcutChangeCallback(android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) {
                this.mCallbacks.unregister(iShortcutChangeCallback);
            }

            public void onShortcutsAddedOrUpdated(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) {
                onShortcutEvent(str, list, userHandle, false);
            }

            public void onShortcutsRemoved(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) {
                onShortcutEvent(str, list, userHandle, true);
            }

            private void onShortcutEvent(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle, boolean z) {
                int beginBroadcast = this.mCallbacks.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    android.content.pm.IShortcutChangeCallback broadcastItem = this.mCallbacks.getBroadcastItem(i);
                    android.util.Pair pair = (android.util.Pair) this.mCallbacks.getBroadcastCookie(i);
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) pair.second;
                    if (userHandle2 == null || hasUserAccess(userHandle2, userHandle)) {
                        java.util.List<android.content.pm.ShortcutInfo> filterShortcutsByQuery = filterShortcutsByQuery(str, list, (android.content.pm.ShortcutQueryWrapper) pair.first, z);
                        if (!com.android.internal.util.CollectionUtils.isEmpty(filterShortcutsByQuery)) {
                            if (z) {
                                try {
                                    broadcastItem.onShortcutsRemoved(str, filterShortcutsByQuery, userHandle);
                                } catch (android.os.RemoteException e) {
                                }
                            } else {
                                broadcastItem.onShortcutsAddedOrUpdated(str, filterShortcutsByQuery, userHandle);
                            }
                        }
                    }
                }
                this.mCallbacks.finishBroadcast();
            }

            public static java.util.List<android.content.pm.ShortcutInfo> filterShortcutsByQuery(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, boolean z) {
                long changedSince = shortcutQueryWrapper.getChangedSince();
                java.lang.String str2 = shortcutQueryWrapper.getPackage();
                java.util.List shortcutIds = shortcutQueryWrapper.getShortcutIds();
                java.util.List locusIds = shortcutQueryWrapper.getLocusIds();
                android.content.ComponentName activity = shortcutQueryWrapper.getActivity();
                int queryFlags = shortcutQueryWrapper.getQueryFlags();
                if (str2 != null && !str2.equals(str)) {
                    return null;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int i = ((queryFlags & 2) != 0 ? 2 : 0) | ((queryFlags & 1) != 0 ? 1 : 0) | ((queryFlags & 8) != 0 ? 32 : 0) | ((queryFlags & 16) != 0 ? 1610629120 : 0);
                for (int i2 = 0; i2 < list.size(); i2++) {
                    android.content.pm.ShortcutInfo shortcutInfo = list.get(i2);
                    if ((activity == null || activity.equals(shortcutInfo.getActivity())) && ((changedSince == 0 || changedSince <= shortcutInfo.getLastChangedTimestamp()) && ((shortcutIds == null || shortcutIds.contains(shortcutInfo.getId())) && ((locusIds == null || locusIds.contains(shortcutInfo.getLocusId())) && (z || (shortcutInfo.getFlags() & i) != 0))))) {
                        arrayList.add(shortcutInfo);
                    }
                }
                return arrayList;
            }

            private boolean hasUserAccess(android.os.UserHandle userHandle, android.os.UserHandle userHandle2) {
                int identifier = userHandle.getIdentifier();
                int identifier2 = userHandle2.getIdentifier();
                if (userHandle2 == userHandle) {
                    return true;
                }
                return this.mUserManagerInternal.isProfileAccessible(identifier, identifier2, null, false);
            }
        }

        private class PackageRemovedListener extends android.content.BroadcastReceiver {
            private PackageRemovedListener() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                if (intExtra == -10000) {
                    android.util.Slog.w(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Intent broadcast does not contain user handle: " + intent);
                    return;
                }
                if ("android.intent.action.PACKAGE_REMOVED_INTERNAL".equals(intent.getAction())) {
                    java.lang.String packageName = getPackageName(intent);
                    int[] intArrayExtra = intent.getIntArrayExtra("android.intent.extra.VISIBILITY_ALLOW_LIST");
                    if (packageName != null) {
                        if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            android.os.UserHandle userHandle = new android.os.UserHandle(intExtra);
                            int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                            for (int i = 0; i < beginBroadcast; i++) {
                                try {
                                    android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                                    com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                                    if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, userHandle, "onPackageRemoved") && com.android.server.pm.LauncherAppsService.LauncherAppsImpl.isCallingAppIdAllowed(intArrayExtra, android.os.UserHandle.getAppId(broadcastCookie.callingUid))) {
                                        try {
                                            iOnAppsChangedListener.onPackageRemoved(userHandle, packageName);
                                        } catch (android.os.RemoteException e) {
                                            android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                                        }
                                    }
                                } finally {
                                    com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                                }
                            }
                        }
                    }
                }
            }

            private java.lang.String getPackageName(android.content.Intent intent) {
                android.net.Uri data = intent.getData();
                if (data != null) {
                    return data.getSchemeSpecificPart();
                }
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class MyPackageMonitor extends com.android.internal.content.PackageMonitor implements android.content.pm.ShortcutServiceInternal.ShortcutChangeListener {
            private MyPackageMonitor() {
            }

            public void onPackageAdded(java.lang.String str, int i) {
                android.os.UserHandle userHandle = new android.os.UserHandle(getChangingUserId());
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    try {
                        android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i2);
                        com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i2);
                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, userHandle, "onPackageAdded") && com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isPackageVisibleToListener(str, broadcastCookie, userHandle)) {
                            try {
                                iOnAppsChangedListener.onPackageAdded(userHandle, str);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackageAdded(str, i);
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mPackageManagerInternal.registerInstalledLoadingProgressCallback(str, com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.new PackageLoadingProgressCallback(str, userHandle), userHandle.getIdentifier());
            }

            public void onPackageModified(java.lang.String str) {
                onPackageChanged(str);
                super.onPackageModified(str);
            }

            private void onPackageChanged(java.lang.String str) {
                android.os.UserHandle userHandle = new android.os.UserHandle(getChangingUserId());
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, userHandle, "onPackageModified") && com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isPackageVisibleToListener(str, broadcastCookie, userHandle)) {
                            try {
                                iOnAppsChangedListener.onPackageChanged(userHandle, str);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                            }
                        }
                    } finally {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                    }
                }
            }

            public void onPackagesAvailable(java.lang.String[] strArr) {
                android.os.UserHandle userHandle = new android.os.UserHandle(getChangingUserId());
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, userHandle, "onPackagesAvailable")) {
                            java.lang.String[] filteredPackageNames = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.getFilteredPackageNames(strArr, broadcastCookie, userHandle);
                            if (!com.android.internal.util.ArrayUtils.isEmpty(filteredPackageNames)) {
                                try {
                                    iOnAppsChangedListener.onPackagesAvailable(userHandle, filteredPackageNames, isReplacing());
                                } catch (android.os.RemoteException e) {
                                    android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackagesAvailable(strArr);
            }

            public void onPackagesUnavailable(java.lang.String[] strArr) {
                android.os.UserHandle userHandle = new android.os.UserHandle(getChangingUserId());
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, userHandle, "onPackagesUnavailable")) {
                            java.lang.String[] filteredPackageNames = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.getFilteredPackageNames(strArr, broadcastCookie, userHandle);
                            if (!com.android.internal.util.ArrayUtils.isEmpty(filteredPackageNames)) {
                                try {
                                    iOnAppsChangedListener.onPackagesUnavailable(userHandle, filteredPackageNames, isReplacing());
                                } catch (android.os.RemoteException e) {
                                    android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackagesUnavailable(strArr);
            }

            public void onPackagesSuspended(java.lang.String[] strArr) {
                android.os.UserHandle userHandle = new android.os.UserHandle(getChangingUserId());
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                for (java.lang.String str : strArr) {
                    android.os.Bundle suspendedPackageLauncherExtras = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mPackageManagerInternal.getSuspendedPackageLauncherExtras(str, userHandle.getIdentifier());
                    if (suspendedPackageLauncherExtras != null) {
                        arrayList.add(new android.util.Pair(str, suspendedPackageLauncherExtras));
                    } else {
                        arrayList2.add(str);
                    }
                }
                java.lang.String[] strArr2 = (java.lang.String[]) arrayList2.toArray(new java.lang.String[arrayList2.size()]);
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, userHandle, "onPackagesSuspended")) {
                            java.lang.String[] filteredPackageNames = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.getFilteredPackageNames(strArr2, broadcastCookie, userHandle);
                            try {
                                if (!com.android.internal.util.ArrayUtils.isEmpty(filteredPackageNames)) {
                                    iOnAppsChangedListener.onPackagesSuspended(userHandle, filteredPackageNames, (android.os.Bundle) null);
                                }
                                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                    android.util.Pair pair = (android.util.Pair) arrayList.get(i2);
                                    if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isPackageVisibleToListener((java.lang.String) pair.first, broadcastCookie, userHandle)) {
                                        iOnAppsChangedListener.onPackagesSuspended(userHandle, new java.lang.String[]{(java.lang.String) pair.first}, (android.os.Bundle) pair.second);
                                    }
                                }
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
            }

            public void onPackagesUnsuspended(java.lang.String[] strArr) {
                android.os.UserHandle userHandle = new android.os.UserHandle(getChangingUserId());
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, userHandle, "onPackagesUnsuspended")) {
                            java.lang.String[] filteredPackageNames = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.getFilteredPackageNames(strArr, broadcastCookie, userHandle);
                            if (!com.android.internal.util.ArrayUtils.isEmpty(filteredPackageNames)) {
                                try {
                                    iOnAppsChangedListener.onPackagesUnsuspended(userHandle, filteredPackageNames);
                                } catch (android.os.RemoteException e) {
                                    android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackagesUnsuspended(strArr);
            }

            public void onShortcutChanged(@android.annotation.NonNull final java.lang.String str, final int i) {
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.postToPackageMonitorHandler(new java.lang.Runnable() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$MyPackageMonitor$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.MyPackageMonitor.this.lambda$onShortcutChanged$0(str, i);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* renamed from: onShortcutChangedInner, reason: merged with bridge method [inline-methods] */
            public void lambda$onShortcutChanged$0(@android.annotation.NonNull java.lang.String str, int i) {
                java.lang.String str2;
                java.lang.String str3;
                int i2;
                int i3;
                android.os.UserHandle userHandle;
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.MyPackageMonitor myPackageMonitor = this;
                java.lang.String str4 = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG;
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                try {
                    try {
                        android.os.UserHandle of = android.os.UserHandle.of(i);
                        int i4 = 0;
                        while (i4 < beginBroadcast) {
                            try {
                                try {
                                    android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i4);
                                    com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i4);
                                    if (!com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, of, "onShortcutChanged")) {
                                        i2 = i4;
                                        userHandle = of;
                                        i3 = beginBroadcast;
                                        str3 = str4;
                                    } else if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isPackageVisibleToListener(str, broadcastCookie, of)) {
                                        int identifier = broadcastCookie.user.getIdentifier();
                                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mShortcutServiceInternal.hasShortcutHostPermission(identifier, broadcastCookie.packageName, broadcastCookie.callingPid, broadcastCookie.callingUid)) {
                                            android.content.pm.ShortcutServiceInternal shortcutServiceInternal = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mShortcutServiceInternal;
                                            i2 = i4;
                                            android.os.UserHandle userHandle2 = of;
                                            i3 = beginBroadcast;
                                            str3 = str4;
                                            try {
                                                try {
                                                    android.content.pm.ParceledListSlice parceledListSlice = new android.content.pm.ParceledListSlice(shortcutServiceInternal.getShortcuts(identifier, broadcastCookie.packageName, 0L, str, (java.util.List) null, (java.util.List) null, (android.content.ComponentName) null, 1055, i, broadcastCookie.callingPid, broadcastCookie.callingUid));
                                                    userHandle = userHandle2;
                                                    try {
                                                        iOnAppsChangedListener.onShortcutChanged(userHandle, str, parceledListSlice);
                                                    } catch (android.os.RemoteException e) {
                                                        e = e;
                                                        android.util.Slog.d(str3, "Callback failed ", e);
                                                        i4 = i2 + 1;
                                                        str4 = str3;
                                                        of = userHandle;
                                                        beginBroadcast = i3;
                                                        myPackageMonitor = this;
                                                    }
                                                } catch (android.os.RemoteException e2) {
                                                    e = e2;
                                                    userHandle = userHandle2;
                                                }
                                            } catch (java.lang.RuntimeException e3) {
                                                e = e3;
                                                str2 = str3;
                                                myPackageMonitor = this;
                                                android.util.Log.w(str2, e.getMessage(), e);
                                                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                                            }
                                        } else {
                                            i2 = i4;
                                            userHandle = of;
                                            i3 = beginBroadcast;
                                            str3 = str4;
                                        }
                                    } else {
                                        i2 = i4;
                                        userHandle = of;
                                        i3 = beginBroadcast;
                                        str3 = str4;
                                    }
                                    i4 = i2 + 1;
                                    str4 = str3;
                                    of = userHandle;
                                    beginBroadcast = i3;
                                    myPackageMonitor = this;
                                } catch (java.lang.RuntimeException e4) {
                                    e = e4;
                                    str3 = str4;
                                }
                            } catch (java.lang.Throwable th) {
                                th = th;
                                myPackageMonitor = this;
                                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                                throw th;
                            }
                        }
                        myPackageMonitor = this;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                    }
                } catch (java.lang.RuntimeException e5) {
                    e = e5;
                    str2 = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG;
                }
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
            }

            public void onPackageStateChanged(java.lang.String str, int i) {
                onPackageChanged(str);
                super.onPackageStateChanged(str, i);
            }
        }

        class PackageCallbackList<T extends android.os.IInterface> extends android.os.RemoteCallbackList<T> {
            PackageCallbackList() {
            }

            @Override // android.os.RemoteCallbackList
            public void onCallbackDied(T t, java.lang.Object obj) {
                com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.checkCallbackCount();
            }
        }

        class PackageLoadingProgressCallback extends android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback {
            private final java.lang.String mPackageName;
            private final android.os.UserHandle mUser;

            PackageLoadingProgressCallback(java.lang.String str, android.os.UserHandle userHandle) {
                super(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mCallbackHandler);
                this.mPackageName = str;
                this.mUser = userHandle;
            }

            @Override // android.content.pm.PackageManagerInternal.InstalledLoadingProgressCallback
            public void onLoadingProgressChanged(float f) {
                int beginBroadcast = com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        android.content.pm.IOnAppsChangedListener iOnAppsChangedListener = (android.content.pm.IOnAppsChangedListener) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        com.android.server.pm.LauncherAppsService.BroadcastCookie broadcastCookie = (com.android.server.pm.LauncherAppsService.BroadcastCookie) com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(broadcastCookie, this.mUser, "onLoadingProgressChanged") && com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.isPackageVisibleToListener(this.mPackageName, broadcastCookie, this.mUser)) {
                            try {
                                iOnAppsChangedListener.onPackageLoadingProgressChanged(this.mUser, this.mPackageName, f);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.d(com.android.server.pm.LauncherAppsService.LauncherAppsImpl.TAG, "Callback failed ", e);
                            }
                        }
                    } finally {
                        com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                    }
                }
            }
        }

        final class LocalService extends com.android.server.pm.LauncherAppsService.LauncherAppsServiceInternal {
            LocalService() {
            }

            @Override // com.android.server.pm.LauncherAppsService.LauncherAppsServiceInternal
            public boolean startShortcut(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.graphics.Rect rect, android.os.Bundle bundle, int i3) {
                return com.android.server.pm.LauncherAppsService.LauncherAppsImpl.this.startShortcutInner(i, i2, android.os.UserHandle.getUserId(i), str, str2, str3, str4, rect, bundle, i3);
            }
        }
    }
}
