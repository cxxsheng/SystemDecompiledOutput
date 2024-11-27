package android.content.pm;

/* loaded from: classes.dex */
public class LauncherApps {
    public static final java.lang.String ACTION_CONFIRM_PIN_APPWIDGET = "android.content.pm.action.CONFIRM_PIN_APPWIDGET";
    public static final java.lang.String ACTION_CONFIRM_PIN_SHORTCUT = "android.content.pm.action.CONFIRM_PIN_SHORTCUT";
    static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_PIN_ITEM_REQUEST = "android.content.pm.extra.PIN_ITEM_REQUEST";
    public static final int FLAG_CACHE_BUBBLE_SHORTCUTS = 1;
    public static final int FLAG_CACHE_NOTIFICATION_SHORTCUTS = 0;
    public static final int FLAG_CACHE_PEOPLE_TILE_SHORTCUTS = 2;
    static final java.lang.String TAG = "LauncherApps";
    private final android.content.pm.IOnAppsChangedListener.Stub mAppsChangedListener;
    private final java.util.List<android.content.pm.LauncherApps.CallbackMessageHandler> mCallbacks;
    private final android.content.Context mContext;
    private final java.util.List<android.content.pm.PackageInstaller.SessionCallbackDelegate> mDelegates;
    private final android.content.pm.PackageManager mPm;
    private final android.content.pm.ILauncherApps mService;
    private final java.util.Map<android.content.pm.LauncherApps.ShortcutChangeCallback, android.util.Pair<java.util.concurrent.Executor, android.content.pm.IShortcutChangeCallback>> mShortcutChangeCallbacks;
    private final android.os.UserManager mUserManager;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShortcutCacheFlags {
    }

    public static abstract class Callback {
        public abstract void onPackageAdded(java.lang.String str, android.os.UserHandle userHandle);

        public abstract void onPackageChanged(java.lang.String str, android.os.UserHandle userHandle);

        public abstract void onPackageRemoved(java.lang.String str, android.os.UserHandle userHandle);

        public abstract void onPackagesAvailable(java.lang.String[] strArr, android.os.UserHandle userHandle, boolean z);

        public abstract void onPackagesUnavailable(java.lang.String[] strArr, android.os.UserHandle userHandle, boolean z);

        public void onPackagesSuspended(java.lang.String[] strArr, android.os.UserHandle userHandle) {
        }

        @java.lang.Deprecated
        public void onPackagesSuspended(java.lang.String[] strArr, android.os.UserHandle userHandle, android.os.Bundle bundle) {
            onPackagesSuspended(strArr, userHandle);
        }

        public void onPackagesUnsuspended(java.lang.String[] strArr, android.os.UserHandle userHandle) {
        }

        public void onShortcutsChanged(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) {
        }

        public void onPackageLoadingProgressChanged(java.lang.String str, android.os.UserHandle userHandle, float f) {
        }
    }

    public static class ShortcutQuery {

        @java.lang.Deprecated
        public static final int FLAG_GET_ALL_KINDS = 27;

        @java.lang.Deprecated
        public static final int FLAG_GET_DYNAMIC = 1;
        public static final int FLAG_GET_KEY_FIELDS_ONLY = 4;

        @java.lang.Deprecated
        public static final int FLAG_GET_MANIFEST = 8;

        @android.annotation.SystemApi
        public static final int FLAG_GET_PERSISTED_DATA = 4096;

        @android.annotation.SystemApi
        public static final int FLAG_GET_PERSONS_DATA = 2048;

        @java.lang.Deprecated
        public static final int FLAG_GET_PINNED = 2;
        public static final int FLAG_MATCH_ALL_KINDS = 27;
        public static final int FLAG_MATCH_ALL_KINDS_WITH_ALL_PINNED = 1051;
        public static final int FLAG_MATCH_CACHED = 16;
        public static final int FLAG_MATCH_DYNAMIC = 1;
        public static final int FLAG_MATCH_MANIFEST = 8;
        public static final int FLAG_MATCH_PINNED = 2;
        public static final int FLAG_MATCH_PINNED_BY_ANY_LAUNCHER = 1024;
        android.content.ComponentName mActivity;
        long mChangedSince;
        java.util.List<android.content.LocusId> mLocusIds;
        java.lang.String mPackage;
        int mQueryFlags;
        java.util.List<java.lang.String> mShortcutIds;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface QueryFlags {
        }

        public android.content.pm.LauncherApps.ShortcutQuery setChangedSince(long j) {
            this.mChangedSince = j;
            return this;
        }

        public android.content.pm.LauncherApps.ShortcutQuery setPackage(java.lang.String str) {
            this.mPackage = str;
            return this;
        }

        public android.content.pm.LauncherApps.ShortcutQuery setShortcutIds(java.util.List<java.lang.String> list) {
            this.mShortcutIds = list;
            return this;
        }

        public android.content.pm.LauncherApps.ShortcutQuery setLocusIds(java.util.List<android.content.LocusId> list) {
            this.mLocusIds = list;
            return this;
        }

        public android.content.pm.LauncherApps.ShortcutQuery setActivity(android.content.ComponentName componentName) {
            this.mActivity = componentName;
            return this;
        }

        public android.content.pm.LauncherApps.ShortcutQuery setQueryFlags(int i) {
            this.mQueryFlags = i;
            return this;
        }
    }

    public interface ShortcutChangeCallback {
        default void onShortcutsAddedOrUpdated(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) {
        }

        default void onShortcutsRemoved(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) {
        }
    }

    private static class ShortcutChangeCallbackProxy extends android.content.pm.IShortcutChangeCallback.Stub {
        private final java.lang.ref.WeakReference<android.util.Pair<java.util.concurrent.Executor, android.content.pm.LauncherApps.ShortcutChangeCallback>> mRemoteReferences;

        ShortcutChangeCallbackProxy(java.util.concurrent.Executor executor, android.content.pm.LauncherApps.ShortcutChangeCallback shortcutChangeCallback) {
            this.mRemoteReferences = new java.lang.ref.WeakReference<>(new android.util.Pair(executor, shortcutChangeCallback));
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsAddedOrUpdated(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) {
            android.util.Pair<java.util.concurrent.Executor, android.content.pm.LauncherApps.ShortcutChangeCallback> pair = this.mRemoteReferences.get();
            if (pair == null) {
                return;
            }
            pair.first.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.QuadConsumer() { // from class: android.content.pm.LauncherApps$ShortcutChangeCallbackProxy$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.content.pm.LauncherApps.ShortcutChangeCallback) obj).onShortcutsAddedOrUpdated((java.lang.String) obj2, (java.util.List) obj3, (android.os.UserHandle) obj4);
                }
            }, pair.second, str, list, userHandle).recycleOnUse());
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsRemoved(java.lang.String str, java.util.List<android.content.pm.ShortcutInfo> list, android.os.UserHandle userHandle) {
            android.util.Pair<java.util.concurrent.Executor, android.content.pm.LauncherApps.ShortcutChangeCallback> pair = this.mRemoteReferences.get();
            if (pair == null) {
                return;
            }
            pair.first.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.QuadConsumer() { // from class: android.content.pm.LauncherApps$ShortcutChangeCallbackProxy$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.content.pm.LauncherApps.ShortcutChangeCallback) obj).onShortcutsRemoved((java.lang.String) obj2, (java.util.List) obj3, (android.os.UserHandle) obj4);
                }
            }, pair.second, str, list, userHandle).recycleOnUse());
        }
    }

    public LauncherApps(android.content.Context context, android.content.pm.ILauncherApps iLauncherApps) {
        this.mCallbacks = new java.util.ArrayList();
        this.mDelegates = new java.util.ArrayList();
        this.mShortcutChangeCallbacks = new java.util.HashMap();
        this.mAppsChangedListener = new android.content.pm.IOnAppsChangedListener.Stub() { // from class: android.content.pm.LauncherApps.1
            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageRemoved(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackageRemoved(str, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageChanged(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackageChanged(str, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageAdded(android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackageAdded(str, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesAvailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackagesAvailable(strArr, userHandle, z);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnavailable(android.os.UserHandle userHandle, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackagesUnavailable(strArr, userHandle, z);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesSuspended(android.os.UserHandle userHandle, java.lang.String[] strArr, android.os.Bundle bundle) throws android.os.RemoteException {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackagesSuspended(strArr, bundle, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnsuspended(android.os.UserHandle userHandle, java.lang.String[] strArr) throws android.os.RemoteException {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackagesUnsuspended(strArr, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onShortcutChanged(android.os.UserHandle userHandle, java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) {
                java.util.List<android.content.pm.ShortcutInfo> list = parceledListSlice.getList();
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnShortcutChanged(str, userHandle, list);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageLoadingProgressChanged(android.os.UserHandle userHandle, java.lang.String str, float f) {
                synchronized (android.content.pm.LauncherApps.this) {
                    java.util.Iterator it = android.content.pm.LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((android.content.pm.LauncherApps.CallbackMessageHandler) it.next()).postOnPackageLoadingProgressChanged(userHandle, str, f);
                    }
                }
            }
        };
        this.mContext = context;
        this.mService = iLauncherApps;
        this.mPm = context.getPackageManager();
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
    }

    public LauncherApps(android.content.Context context) {
        this(context, android.content.pm.ILauncherApps.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.LAUNCHER_APPS_SERVICE)));
    }

    private void logErrorForInvalidProfileAccess(android.os.UserHandle userHandle) {
        if (android.os.UserHandle.myUserId() != userHandle.getIdentifier() && this.mUserManager.isManagedProfile() && this.mContext.checkSelfPermission(android.Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
            android.util.Log.w(TAG, "Accessing other profiles/users from managed profile is no longer allowed.");
        }
    }

    public java.util.List<android.os.UserHandle> getProfiles() {
        if (this.mUserManager.isManagedProfile() || (android.multiuser.Flags.enableLauncherAppsHiddenProfileChecks() && android.os.Flags.allowPrivateProfile() && this.mUserManager.isPrivateProfile())) {
            java.util.ArrayList arrayList = new java.util.ArrayList(1);
            arrayList.add(android.os.Process.myUserHandle());
            return arrayList;
        }
        if (android.multiuser.Flags.enableLauncherAppsHiddenProfileChecks()) {
            try {
                return this.mService.getUserProfiles();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mUserManager.getUserProfiles();
    }

    public java.util.List<android.content.pm.LauncherActivityInfo> getActivityList(java.lang.String str, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return convertToActivityList(this.mService.getLauncherActivities(this.mContext.getPackageName(), str, userHandle), userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.PendingIntent getMainActivityLaunchIntent(android.content.ComponentName componentName, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.getActivityLaunchIntent(this.mContext.getPackageName(), componentName, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final android.content.pm.LauncherUserInfo getLauncherUserInfo(android.os.UserHandle userHandle) {
        try {
            return this.mService.getLauncherUserInfo(userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.IntentSender getAppMarketActivityIntent(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getAppMarketActivityIntent(this.mContext.getPackageName(), str, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.String> getPreInstalledSystemPackages(android.os.UserHandle userHandle) {
        try {
            return this.mService.getPreInstalledSystemPackages(userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.IntentSender getPrivateSpaceSettingsIntent() {
        try {
            return this.mService.getPrivateSpaceSettingsIntent();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.LauncherActivityInfo resolveActivity(android.content.Intent intent, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            android.content.pm.LauncherActivityInfoInternal resolveLauncherActivityInternal = this.mService.resolveLauncherActivityInternal(this.mContext.getPackageName(), intent.getComponent(), userHandle);
            if (resolveLauncherActivityInternal == null) {
                return null;
            }
            return new android.content.pm.LauncherActivityInfo(this.mContext, resolveLauncherActivityInternal);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Map<java.lang.String, android.content.pm.LauncherActivityInfo> getActivityOverrides() {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        try {
            for (java.util.Map.Entry<java.lang.String, android.content.pm.LauncherActivityInfoInternal> entry : this.mService.getActivityOverrides(this.mContext.getPackageName(), this.mContext.getUserId()).entrySet()) {
                arrayMap.put(entry.getKey(), new android.content.pm.LauncherActivityInfo(this.mContext, entry.getValue()));
            }
            return arrayMap;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(android.content.ComponentName componentName, android.os.UserHandle userHandle, android.graphics.Rect rect, android.os.Bundle bundle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, rect, bundle, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startPackageInstallerSessionDetailsActivity(android.content.pm.PackageInstaller.SessionInfo sessionInfo, android.graphics.Rect rect, android.os.Bundle bundle) {
        try {
            this.mService.startSessionDetailsActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), sessionInfo, rect, bundle, sessionInfo.getUser());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startAppDetailsActivity(android.content.ComponentName componentName, android.os.UserHandle userHandle, android.graphics.Rect rect, android.os.Bundle bundle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.showAppDetailsAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, rect, bundle, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.PendingIntent getShortcutIntent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.getShortcutIntent(this.mContext.getPackageName(), str, str2, null, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.LauncherActivityInfo> getShortcutConfigActivityList(java.lang.String str, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return convertToActivityList(this.mService.getShortcutConfigActivities(this.mContext.getPackageName(), str, userHandle), userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private java.util.List<android.content.pm.LauncherActivityInfo> convertToActivityList(android.content.pm.ParceledListSlice<android.content.pm.LauncherActivityInfoInternal> parceledListSlice, android.os.UserHandle userHandle) {
        if (parceledListSlice == null || parceledListSlice.getList().isEmpty()) {
            return java.util.Collections.EMPTY_LIST;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = parceledListSlice.getList().iterator();
        while (it.hasNext()) {
            arrayList.add(new android.content.pm.LauncherActivityInfo(this.mContext, (android.content.pm.LauncherActivityInfoInternal) it.next()));
        }
        return arrayList;
    }

    public android.content.IntentSender getShortcutConfigActivityIntent(android.content.pm.LauncherActivityInfo launcherActivityInfo) {
        try {
            return this.mService.getShortcutConfigActivityIntent(this.mContext.getPackageName(), launcherActivityInfo.getComponentName(), launcherActivityInfo.getUser());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPackageEnabled(java.lang.String str, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.isPackageEnabled(this.mContext.getPackageName(), str, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getSuspendedPackageLauncherExtras(java.lang.String str, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.getSuspendedPackageLauncherExtras(str, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldHideFromSuggestions(java.lang.String str, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(str, "packageName");
        java.util.Objects.requireNonNull(userHandle, "user");
        try {
            return this.mService.shouldHideFromSuggestions(str, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, int i, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(str, "packageName");
        java.util.Objects.requireNonNull(userHandle, "user");
        logErrorForInvalidProfileAccess(userHandle);
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mService.getApplicationInfo(this.mContext.getPackageName(), str, i, userHandle);
            if (applicationInfo == null) {
                throw new android.content.pm.PackageManager.NameNotFoundException("Package " + str + " not found for user " + userHandle.getIdentifier());
            }
            return applicationInfo;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.content.pm.LauncherApps.AppUsageLimit getAppUsageLimit(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getAppUsageLimit(this.mContext.getPackageName(), str, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActivityEnabled(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.isActivityEnabled(this.mContext.getPackageName(), componentName, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasShortcutHostPermission() {
        try {
            return this.mService.hasShortcutHostPermission(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.content.pm.ShortcutInfo> maybeUpdateDisabledMessage(java.util.List<android.content.pm.ShortcutInfo> list) {
        if (list == null) {
            return null;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ShortcutInfo shortcutInfo = list.get(size);
            java.lang.String disabledReasonForRestoreIssue = android.content.pm.ShortcutInfo.getDisabledReasonForRestoreIssue(this.mContext, shortcutInfo.getDisabledReason());
            if (disabledReasonForRestoreIssue != null) {
                shortcutInfo.setDisabledMessage(disabledReasonForRestoreIssue);
            }
        }
        return list;
    }

    public void registerDumpCallback(android.window.IDumpCallback iDumpCallback) {
        try {
            this.mService.registerDumpCallback(iDumpCallback);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void unRegisterDumpCallback(android.window.IDumpCallback iDumpCallback) {
        try {
            this.mService.unRegisterDumpCallback(iDumpCallback);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public java.util.List<android.content.pm.ShortcutInfo> getShortcuts(android.content.pm.LauncherApps.ShortcutQuery shortcutQuery, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            if ((shortcutQuery.mQueryFlags & 4096) != 0) {
                return getShortcutsBlocked(shortcutQuery, userHandle);
            }
            return maybeUpdateDisabledMessage(this.mService.getShortcuts(this.mContext.getPackageName(), new android.content.pm.ShortcutQueryWrapper(shortcutQuery), userHandle).getList());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private java.util.List<android.content.pm.ShortcutInfo> getShortcutsBlocked(android.content.pm.LauncherApps.ShortcutQuery shortcutQuery, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        androidFuture.thenApply((java.util.function.Function<? super java.util.List<android.content.pm.ShortcutInfo>, ? extends U>) new java.util.function.Function() { // from class: android.content.pm.LauncherApps$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.List maybeUpdateDisabledMessage;
                maybeUpdateDisabledMessage = android.content.pm.LauncherApps.this.maybeUpdateDisabledMessage((java.util.List) obj);
                return maybeUpdateDisabledMessage;
            }
        });
        try {
            this.mService.getShortcutsAsync(this.mContext.getPackageName(), new android.content.pm.ShortcutQueryWrapper(shortcutQuery), userHandle, androidFuture);
            return androidFuture.get();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.content.pm.ShortcutInfo> getShortcutInfo(java.lang.String str, java.util.List<java.lang.String> list, android.os.UserHandle userHandle) {
        android.content.pm.LauncherApps.ShortcutQuery shortcutQuery = new android.content.pm.LauncherApps.ShortcutQuery();
        shortcutQuery.setPackage(str);
        shortcutQuery.setShortcutIds(list);
        shortcutQuery.setQueryFlags(27);
        return getShortcuts(shortcutQuery, userHandle);
    }

    public void pinShortcuts(java.lang.String str, java.util.List<java.lang.String> list, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.pinShortcuts(this.mContext.getPackageName(), str, list, userHandle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cacheShortcuts(java.lang.String str, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.cacheShortcuts(this.mContext.getPackageName(), str, list, userHandle, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uncacheShortcuts(java.lang.String str, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.uncacheShortcuts(this.mContext.getPackageName(), str, list, userHandle, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int getShortcutIconResId(android.content.pm.ShortcutInfo shortcutInfo) {
        return shortcutInfo.getIconResourceId();
    }

    @java.lang.Deprecated
    public int getShortcutIconResId(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        android.content.pm.LauncherApps.ShortcutQuery shortcutQuery = new android.content.pm.LauncherApps.ShortcutQuery();
        shortcutQuery.setPackage(str);
        shortcutQuery.setShortcutIds(java.util.Arrays.asList(str2));
        shortcutQuery.setQueryFlags(27);
        java.util.List<android.content.pm.ShortcutInfo> shortcuts = getShortcuts(shortcutQuery, userHandle);
        if (shortcuts.size() > 0) {
            return shortcuts.get(0).getIconResourceId();
        }
        return 0;
    }

    public android.os.ParcelFileDescriptor getShortcutIconFd(android.content.pm.ShortcutInfo shortcutInfo) {
        return getShortcutIconFd(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
    }

    public android.os.ParcelFileDescriptor getShortcutIconFd(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        return getShortcutIconFd(str, str2, userHandle.getIdentifier());
    }

    private android.os.ParcelFileDescriptor getShortcutIconFd(java.lang.String str, java.lang.String str2, int i) {
        try {
            return this.mService.getShortcutIconFd(this.mContext.getPackageName(), str, str2, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.ParcelFileDescriptor getUriShortcutIconFd(android.content.pm.ShortcutInfo shortcutInfo) {
        return getUriShortcutIconFd(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
    }

    private android.os.ParcelFileDescriptor getUriShortcutIconFd(java.lang.String str, java.lang.String str2, int i) {
        java.lang.String shortcutIconUri = getShortcutIconUri(str, str2, i);
        if (shortcutIconUri == null) {
            return null;
        }
        try {
            return this.mContext.getContentResolver().openFileDescriptor(android.net.Uri.parse(shortcutIconUri), "r");
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to open icon file: " + shortcutIconUri, e);
            return null;
        }
    }

    private java.lang.String getShortcutIconUri(java.lang.String str, java.lang.String str2, int i) {
        try {
            return this.mService.getShortcutIconUri(this.mContext.getPackageName(), str, str2, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.graphics.drawable.Drawable getShortcutIconDrawable(android.content.pm.ShortcutInfo shortcutInfo, int i) {
        if (shortcutInfo.hasIconFile()) {
            return loadDrawableFromFileDescriptor(getShortcutIconFd(shortcutInfo), shortcutInfo.hasAdaptiveBitmap());
        }
        if (shortcutInfo.hasIconUri()) {
            return loadDrawableFromFileDescriptor(getUriShortcutIconFd(shortcutInfo), shortcutInfo.hasAdaptiveBitmap());
        }
        if (shortcutInfo.hasIconResource()) {
            return loadDrawableResourceFromPackage(shortcutInfo.getPackage(), shortcutInfo.getIconResourceId(), shortcutInfo.getUserHandle(), i);
        }
        if (shortcutInfo.getIcon() == null) {
            return null;
        }
        android.graphics.drawable.Icon icon = shortcutInfo.getIcon();
        switch (icon.getType()) {
            case 1:
            case 5:
                return icon.loadDrawable(this.mContext);
            case 2:
                return loadDrawableResourceFromPackage(shortcutInfo.getPackage(), icon.getResId(), shortcutInfo.getUserHandle(), i);
            case 3:
            case 4:
            default:
                return null;
        }
    }

    private android.graphics.drawable.Drawable loadDrawableFromFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor, boolean z) {
        if (parcelFileDescriptor == null) {
            return null;
        }
        try {
            android.graphics.Bitmap decodeFileDescriptor = android.graphics.BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
            if (decodeFileDescriptor == null) {
                try {
                    parcelFileDescriptor.close();
                } catch (java.io.IOException e) {
                }
                return null;
            }
            android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), decodeFileDescriptor);
            if (z) {
                return new android.graphics.drawable.AdaptiveIconDrawable((android.graphics.drawable.Drawable) null, bitmapDrawable);
            }
            try {
                parcelFileDescriptor.close();
            } catch (java.io.IOException e2) {
            }
            return bitmapDrawable;
        } finally {
            try {
                parcelFileDescriptor.close();
            } catch (java.io.IOException e3) {
            }
        }
    }

    public android.graphics.drawable.Icon getShortcutIcon(android.content.pm.ShortcutInfo shortcutInfo) {
        if (!shortcutInfo.hasIconFile()) {
            if (!shortcutInfo.hasIconUri()) {
                return shortcutInfo.hasIconResource() ? android.graphics.drawable.Icon.createWithResource(shortcutInfo.getPackage(), shortcutInfo.getIconResourceId()) : shortcutInfo.getIcon();
            }
            java.lang.String shortcutIconUri = getShortcutIconUri(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
            if (shortcutIconUri == null) {
                return null;
            }
            return shortcutInfo.hasAdaptiveBitmap() ? android.graphics.drawable.Icon.createWithAdaptiveBitmapContentUri(shortcutIconUri) : android.graphics.drawable.Icon.createWithContentUri(shortcutIconUri);
        }
        android.os.ParcelFileDescriptor shortcutIconFd = getShortcutIconFd(shortcutInfo);
        if (shortcutIconFd == null) {
            return null;
        }
        try {
            android.graphics.Bitmap decodeFileDescriptor = android.graphics.BitmapFactory.decodeFileDescriptor(shortcutIconFd.getFileDescriptor());
            if (decodeFileDescriptor == null) {
                try {
                    shortcutIconFd.close();
                } catch (java.io.IOException e) {
                }
                return null;
            }
            if (shortcutInfo.hasAdaptiveBitmap()) {
                return android.graphics.drawable.Icon.createWithAdaptiveBitmap(decodeFileDescriptor);
            }
            android.graphics.drawable.Icon createWithBitmap = android.graphics.drawable.Icon.createWithBitmap(decodeFileDescriptor);
            try {
                shortcutIconFd.close();
            } catch (java.io.IOException e2) {
            }
            return createWithBitmap;
        } finally {
            try {
                shortcutIconFd.close();
            } catch (java.io.IOException e3) {
            }
        }
    }

    private android.graphics.drawable.Drawable loadDrawableResourceFromPackage(java.lang.String str, int i, android.os.UserHandle userHandle, int i2) {
        if (i == 0) {
            return null;
        }
        try {
            return this.mContext.getPackageManager().getResourcesForApplication(getApplicationInfo(str, 0, userHandle)).getDrawableForDensity(i, i2);
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e) {
            return null;
        }
    }

    public android.graphics.drawable.Drawable getShortcutBadgedIconDrawable(android.content.pm.ShortcutInfo shortcutInfo, int i) {
        android.graphics.drawable.Drawable shortcutIconDrawable = getShortcutIconDrawable(shortcutInfo, i);
        if (shortcutIconDrawable == null) {
            return null;
        }
        return this.mContext.getPackageManager().getUserBadgedIcon(shortcutIconDrawable, shortcutInfo.getUserHandle());
    }

    public void startShortcut(java.lang.String str, java.lang.String str2, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        startShortcut(str, str2, rect, bundle, userHandle.getIdentifier());
    }

    public void startShortcut(android.content.pm.ShortcutInfo shortcutInfo, android.graphics.Rect rect, android.os.Bundle bundle) {
        startShortcut(shortcutInfo.getPackage(), shortcutInfo.getId(), rect, bundle, shortcutInfo.getUserId());
    }

    private void startShortcut(java.lang.String str, java.lang.String str2, android.graphics.Rect rect, android.os.Bundle bundle, int i) {
        try {
            if (!this.mService.startShortcut(this.mContext.getPackageName(), str, null, str2, rect, bundle, i)) {
                throw new android.content.ActivityNotFoundException("Shortcut could not be started");
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(android.content.pm.LauncherApps.Callback callback) {
        registerCallback(callback, null);
    }

    public void registerCallback(android.content.pm.LauncherApps.Callback callback, android.os.Handler handler) {
        synchronized (this) {
            if (callback != null) {
                if (findCallbackLocked(callback) < 0) {
                    boolean z = this.mCallbacks.size() == 0;
                    addCallbackLocked(callback, handler);
                    if (z) {
                        try {
                            this.mService.addOnAppsChangedListener(this.mContext.getPackageName(), this.mAppsChangedListener);
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
            }
        }
    }

    public void unregisterCallback(android.content.pm.LauncherApps.Callback callback) {
        synchronized (this) {
            removeCallbackLocked(callback);
            if (this.mCallbacks.size() == 0) {
                try {
                    this.mService.removeOnAppsChangedListener(this.mAppsChangedListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void setArchiveCompatibility(android.content.pm.LauncherApps.ArchiveCompatibilityParams archiveCompatibilityParams) {
        try {
            this.mService.setArchiveCompatibilityOptions(archiveCompatibilityParams.isEnableIconOverlay(), archiveCompatibilityParams.isEnableUnarchivalConfirmation());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findCallbackLocked(android.content.pm.LauncherApps.Callback callback) {
        if (callback == null) {
            throw new java.lang.IllegalArgumentException("Callback cannot be null");
        }
        int size = this.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            if (this.mCallbacks.get(i).mCallback == callback) {
                return i;
            }
        }
        return -1;
    }

    private void removeCallbackLocked(android.content.pm.LauncherApps.Callback callback) {
        int findCallbackLocked = findCallbackLocked(callback);
        if (findCallbackLocked >= 0) {
            this.mCallbacks.remove(findCallbackLocked);
        }
    }

    private void addCallbackLocked(android.content.pm.LauncherApps.Callback callback, android.os.Handler handler) {
        removeCallbackLocked(callback);
        if (handler == null) {
            handler = new android.os.Handler();
        }
        this.mCallbacks.add(new android.content.pm.LauncherApps.CallbackMessageHandler(handler.getLooper(), callback));
    }

    public static class ArchiveCompatibilityParams {
        private boolean mEnableIconOverlay = true;
        private boolean mEnableUnarchivalConfirmation = true;

        public boolean isEnableIconOverlay() {
            return this.mEnableIconOverlay;
        }

        public boolean isEnableUnarchivalConfirmation() {
            return this.mEnableUnarchivalConfirmation;
        }

        public void setEnableIconOverlay(boolean z) {
            this.mEnableIconOverlay = z;
        }

        public void setEnableUnarchivalConfirmation(boolean z) {
            this.mEnableUnarchivalConfirmation = z;
        }
    }

    private static class CallbackMessageHandler extends android.os.Handler {
        private static final int MSG_ADDED = 1;
        private static final int MSG_AVAILABLE = 4;
        private static final int MSG_CHANGED = 3;
        private static final int MSG_LOADING_PROGRESS_CHANGED = 9;
        private static final int MSG_REMOVED = 2;
        private static final int MSG_SHORTCUT_CHANGED = 8;
        private static final int MSG_SUSPENDED = 6;
        private static final int MSG_UNAVAILABLE = 5;
        private static final int MSG_UNSUSPENDED = 7;
        private final android.content.pm.LauncherApps.Callback mCallback;

        private static class CallbackInfo {
            android.os.Bundle launcherExtras;
            float mLoadingProgress;
            java.lang.String packageName;
            java.lang.String[] packageNames;
            boolean replacing;
            java.util.List<android.content.pm.ShortcutInfo> shortcuts;
            android.os.UserHandle user;

            private CallbackInfo() {
            }
        }

        public CallbackMessageHandler(android.os.Looper looper, android.content.pm.LauncherApps.Callback callback) {
            super(looper, null, true);
            this.mCallback = callback;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (this.mCallback == null || !(message.obj instanceof android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo)) {
                return;
            }
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = (android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo) message.obj;
            switch (message.what) {
                case 1:
                    this.mCallback.onPackageAdded(callbackInfo.packageName, callbackInfo.user);
                    break;
                case 2:
                    this.mCallback.onPackageRemoved(callbackInfo.packageName, callbackInfo.user);
                    break;
                case 3:
                    this.mCallback.onPackageChanged(callbackInfo.packageName, callbackInfo.user);
                    break;
                case 4:
                    this.mCallback.onPackagesAvailable(callbackInfo.packageNames, callbackInfo.user, callbackInfo.replacing);
                    break;
                case 5:
                    this.mCallback.onPackagesUnavailable(callbackInfo.packageNames, callbackInfo.user, callbackInfo.replacing);
                    break;
                case 6:
                    this.mCallback.onPackagesSuspended(callbackInfo.packageNames, callbackInfo.user, callbackInfo.launcherExtras);
                    break;
                case 7:
                    this.mCallback.onPackagesUnsuspended(callbackInfo.packageNames, callbackInfo.user);
                    break;
                case 8:
                    this.mCallback.onShortcutsChanged(callbackInfo.packageName, callbackInfo.shortcuts, callbackInfo.user);
                    break;
                case 9:
                    this.mCallback.onPackageLoadingProgressChanged(callbackInfo.packageName, callbackInfo.user, callbackInfo.mLoadingProgress);
                    break;
            }
        }

        public void postOnPackageAdded(java.lang.String str, android.os.UserHandle userHandle) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            obtainMessage(1, callbackInfo).sendToTarget();
        }

        public void postOnPackageRemoved(java.lang.String str, android.os.UserHandle userHandle) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            obtainMessage(2, callbackInfo).sendToTarget();
        }

        public void postOnPackageChanged(java.lang.String str, android.os.UserHandle userHandle) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            obtainMessage(3, callbackInfo).sendToTarget();
        }

        public void postOnPackagesAvailable(java.lang.String[] strArr, android.os.UserHandle userHandle, boolean z) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.replacing = z;
            callbackInfo.user = userHandle;
            obtainMessage(4, callbackInfo).sendToTarget();
        }

        public void postOnPackagesUnavailable(java.lang.String[] strArr, android.os.UserHandle userHandle, boolean z) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.replacing = z;
            callbackInfo.user = userHandle;
            obtainMessage(5, callbackInfo).sendToTarget();
        }

        public void postOnPackagesSuspended(java.lang.String[] strArr, android.os.Bundle bundle, android.os.UserHandle userHandle) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.user = userHandle;
            callbackInfo.launcherExtras = bundle;
            obtainMessage(6, callbackInfo).sendToTarget();
        }

        public void postOnPackagesUnsuspended(java.lang.String[] strArr, android.os.UserHandle userHandle) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.user = userHandle;
            obtainMessage(7, callbackInfo).sendToTarget();
        }

        public void postOnShortcutChanged(java.lang.String str, android.os.UserHandle userHandle, java.util.List<android.content.pm.ShortcutInfo> list) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            callbackInfo.shortcuts = list;
            obtainMessage(8, callbackInfo).sendToTarget();
        }

        public void postOnPackageLoadingProgressChanged(android.os.UserHandle userHandle, java.lang.String str, float f) {
            android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo callbackInfo = new android.content.pm.LauncherApps.CallbackMessageHandler.CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            callbackInfo.mLoadingProgress = f;
            obtainMessage(9, callbackInfo).sendToTarget();
        }
    }

    public void registerPackageInstallerSessionCallback(java.util.concurrent.Executor executor, android.content.pm.PackageInstaller.SessionCallback sessionCallback) {
        if (executor == null) {
            throw new java.lang.NullPointerException("Executor must not be null");
        }
        synchronized (this.mDelegates) {
            android.content.pm.PackageInstaller.SessionCallbackDelegate sessionCallbackDelegate = new android.content.pm.PackageInstaller.SessionCallbackDelegate(sessionCallback, executor);
            try {
                this.mService.registerPackageInstallerCallback(this.mContext.getPackageName(), sessionCallbackDelegate);
                this.mDelegates.add(sessionCallbackDelegate);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterPackageInstallerSessionCallback(android.content.pm.PackageInstaller.SessionCallback sessionCallback) {
        synchronized (this.mDelegates) {
            java.util.Iterator<android.content.pm.PackageInstaller.SessionCallbackDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                android.content.pm.PackageInstaller.SessionCallbackDelegate next = it.next();
                if (next.mCallback == sessionCallback) {
                    this.mPm.getPackageInstaller().unregisterSessionCallback(next.mCallback);
                    it.remove();
                }
            }
        }
    }

    public java.util.List<android.content.pm.PackageInstaller.SessionInfo> getAllPackageInstallerSessions() {
        try {
            return this.mService.getAllSessions(this.mContext.getPackageName()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerShortcutChangeCallback(android.content.pm.LauncherApps.ShortcutChangeCallback shortcutChangeCallback, android.content.pm.LauncherApps.ShortcutQuery shortcutQuery, java.util.concurrent.Executor executor) {
        java.util.Objects.requireNonNull(shortcutChangeCallback, "Callback cannot be null");
        java.util.Objects.requireNonNull(shortcutQuery, "Query cannot be null");
        java.util.Objects.requireNonNull(executor, "Executor cannot be null");
        synchronized (this.mShortcutChangeCallbacks) {
            android.content.pm.LauncherApps.ShortcutChangeCallbackProxy shortcutChangeCallbackProxy = new android.content.pm.LauncherApps.ShortcutChangeCallbackProxy(executor, shortcutChangeCallback);
            this.mShortcutChangeCallbacks.put(shortcutChangeCallback, new android.util.Pair<>(executor, shortcutChangeCallbackProxy));
            try {
                this.mService.registerShortcutChangeCallback(this.mContext.getPackageName(), new android.content.pm.ShortcutQueryWrapper(shortcutQuery), shortcutChangeCallbackProxy);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterShortcutChangeCallback(android.content.pm.LauncherApps.ShortcutChangeCallback shortcutChangeCallback) {
        java.util.Objects.requireNonNull(shortcutChangeCallback, "Callback cannot be null");
        synchronized (this.mShortcutChangeCallbacks) {
            if (this.mShortcutChangeCallbacks.containsKey(shortcutChangeCallback)) {
                try {
                    this.mService.unregisterShortcutChangeCallback(this.mContext.getPackageName(), this.mShortcutChangeCallbacks.remove(shortcutChangeCallback).second);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public android.content.pm.LauncherApps.PinItemRequest getPinItemRequest(android.content.Intent intent) {
        return (android.content.pm.LauncherApps.PinItemRequest) intent.getParcelableExtra(EXTRA_PIN_ITEM_REQUEST, android.content.pm.LauncherApps.PinItemRequest.class);
    }

    public static final class PinItemRequest implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.LauncherApps.PinItemRequest> CREATOR = new android.os.Parcelable.Creator<android.content.pm.LauncherApps.PinItemRequest>() { // from class: android.content.pm.LauncherApps.PinItemRequest.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.LauncherApps.PinItemRequest createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.LauncherApps.PinItemRequest(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.LauncherApps.PinItemRequest[] newArray(int i) {
                return new android.content.pm.LauncherApps.PinItemRequest[i];
            }
        };
        public static final int REQUEST_TYPE_APPWIDGET = 2;
        public static final int REQUEST_TYPE_SHORTCUT = 1;
        private final android.content.pm.IPinItemRequest mInner;
        private final int mRequestType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface RequestType {
        }

        public PinItemRequest(android.content.pm.IPinItemRequest iPinItemRequest, int i) {
            this.mInner = iPinItemRequest;
            this.mRequestType = i;
        }

        public int getRequestType() {
            return this.mRequestType;
        }

        public android.content.pm.ShortcutInfo getShortcutInfo() {
            try {
                return this.mInner.getShortcutInfo();
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public android.appwidget.AppWidgetProviderInfo getAppWidgetProviderInfo(android.content.Context context) {
            try {
                android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo = this.mInner.getAppWidgetProviderInfo();
                if (appWidgetProviderInfo == null) {
                    return null;
                }
                appWidgetProviderInfo.updateDimensions(context.getResources().getDisplayMetrics());
                return appWidgetProviderInfo;
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public android.os.Bundle getExtras() {
            try {
                return this.mInner.getExtras();
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public boolean isValid() {
            try {
                return this.mInner.isValid();
            } catch (android.os.RemoteException e) {
                return false;
            }
        }

        public boolean accept(android.os.Bundle bundle) {
            try {
                return this.mInner.accept(bundle);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean accept() {
            return accept(null);
        }

        private PinItemRequest(android.os.Parcel parcel) {
            getClass().getClassLoader();
            this.mRequestType = parcel.readInt();
            this.mInner = android.content.pm.IPinItemRequest.Stub.asInterface(parcel.readStrongBinder());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mRequestType);
            parcel.writeStrongBinder(this.mInner.asBinder());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    @android.annotation.SystemApi
    public static final class AppUsageLimit implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.LauncherApps.AppUsageLimit> CREATOR = new android.os.Parcelable.Creator<android.content.pm.LauncherApps.AppUsageLimit>() { // from class: android.content.pm.LauncherApps.AppUsageLimit.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.LauncherApps.AppUsageLimit createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.LauncherApps.AppUsageLimit(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.LauncherApps.AppUsageLimit[] newArray(int i) {
                return new android.content.pm.LauncherApps.AppUsageLimit[i];
            }
        };
        private final long mTotalUsageLimit;
        private final long mUsageRemaining;

        public AppUsageLimit(long j, long j2) {
            this.mTotalUsageLimit = j;
            this.mUsageRemaining = j2;
        }

        public long getTotalUsageLimit() {
            return this.mTotalUsageLimit;
        }

        public long getUsageRemaining() {
            return this.mUsageRemaining;
        }

        private AppUsageLimit(android.os.Parcel parcel) {
            this.mTotalUsageLimit = parcel.readLong();
            this.mUsageRemaining = parcel.readLong();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.mTotalUsageLimit);
            parcel.writeLong(this.mUsageRemaining);
        }
    }
}
