package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageArchiver {
    private static final java.lang.String ACTION_UNARCHIVE_DIALOG = "com.android.intent.action.UNARCHIVE_DIALOG";
    private static final java.lang.String ACTION_UNARCHIVE_ERROR_DIALOG = "com.android.intent.action.UNARCHIVE_ERROR_DIALOG";
    private static final java.lang.String ARCHIVE_ICONS_DIR = "package_archiver";
    private static final boolean DEBUG = true;
    private static final int DEFAULT_UNARCHIVE_FOREGROUND_TIMEOUT_MS = 120000;
    private static final java.lang.String EXTRA_INSTALLER_PACKAGE_NAME = "com.android.content.pm.extra.UNARCHIVE_INSTALLER_PACKAGE_NAME";
    private static final java.lang.String EXTRA_INSTALLER_TITLE = "com.android.content.pm.extra.UNARCHIVE_INSTALLER_TITLE";
    private static final java.lang.String EXTRA_REQUIRED_BYTES = "com.android.content.pm.extra.UNARCHIVE_EXTRA_REQUIRED_BYTES";
    public static final java.lang.String EXTRA_UNARCHIVE_INTENT_SENDER = "android.content.pm.extra.UNARCHIVE_INTENT_SENDER";
    private static final android.graphics.PorterDuffColorFilter OPACITY_LAYER_FILTER = new android.graphics.PorterDuffColorFilter(android.graphics.Color.argb(0.5f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE), android.graphics.PorterDuff.Mode.SRC_ATOP);
    private static final java.lang.String TAG = "PackageArchiverService";

    @android.annotation.Nullable
    private android.app.AppOpsManager mAppOpsManager;
    private final com.android.server.pm.AppStateHelper mAppStateHelper;
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private android.content.pm.LauncherApps mLauncherApps;
    private final java.util.Map<android.util.Pair<java.lang.Integer, java.lang.String>, android.content.IntentSender> mLauncherIntentSenders = new java.util.HashMap();
    private final com.android.server.pm.PackageManagerService mPm;

    @android.annotation.Nullable
    private android.os.UserManager mUserManager;

    PackageArchiver(android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService) {
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mAppStateHelper = new com.android.server.pm.AppStateHelper(this.mContext);
    }

    public static boolean isArchived(com.android.server.pm.pkg.PackageUserState packageUserState) {
        return (packageUserState.getArchiveState() == null || packageUserState.isInstalled()) ? false : true;
    }

    public static boolean isArchivingEnabled() {
        return android.content.pm.Flags.archiving() || android.os.SystemProperties.getBoolean("pm.archiving.enabled", false);
    }

    @com.android.internal.annotations.VisibleForTesting
    void requestArchive(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.content.IntentSender intentSender, @android.annotation.NonNull android.os.UserHandle userHandle) {
        requestArchive(str, str2, 0, intentSender, userHandle);
    }

    void requestArchive(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, int i, @android.annotation.NonNull final android.content.IntentSender intentSender, @android.annotation.NonNull android.os.UserHandle userHandle) {
        int[] iArr;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        java.util.Objects.requireNonNull(intentSender);
        java.util.Objects.requireNonNull(userHandle);
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("Requested archival of package %s for user %s.", new java.lang.Object[]{str, java.lang.Integer.valueOf(userHandle.getIdentifier())}));
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        final int identifier = userHandle.getIdentifier();
        final int callingUid = android.os.Binder.getCallingUid();
        final int callingPid = android.os.Binder.getCallingPid();
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)) {
            verifyCaller(snapshotComputer.getPackageUid(str2, 0L, identifier), callingUid);
        }
        boolean z = (i & 2) != 0;
        if (z) {
            iArr = this.mPm.mInjector.getUserManagerInternal().getUserIds();
        } else {
            iArr = new int[]{identifier};
        }
        int i2 = 0;
        for (int length = iArr.length; i2 < length; length = length) {
            snapshotComputer.enforceCrossUserPermission(callingUid, iArr[i2], true, true, "archiveApp");
            i2++;
            iArr = iArr;
        }
        int[] iArr2 = iArr;
        verifyUninstallPermissions();
        java.util.concurrent.CompletableFuture[] completableFutureArr = new java.util.concurrent.CompletableFuture[iArr2.length];
        try {
            int length2 = iArr2.length;
            for (int i3 = 0; i3 < length2; i3++) {
                completableFutureArr[i3] = createAndStoreArchiveState(str, iArr2[i3]);
            }
            final int i4 = (z ? 2 : 0) | 17;
            java.util.concurrent.CompletableFuture.allOf(completableFutureArr).thenAccept(new java.util.function.Consumer() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.PackageArchiver.this.lambda$requestArchive$0(str, str2, i4, intentSender, identifier, callingUid, callingPid, (java.lang.Void) obj);
                }
            }).exceptionally(new java.util.function.Function() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Void lambda$requestArchive$1;
                    lambda$requestArchive$1 = com.android.server.pm.PackageArchiver.this.lambda$requestArchive$1(str, intentSender, (java.lang.Throwable) obj);
                    return lambda$requestArchive$1;
                }
            });
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Failed to archive %s with message %s", new java.lang.Object[]{str, e.getMessage()}));
            throw new android.os.ParcelableException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestArchive$0(java.lang.String str, java.lang.String str2, int i, android.content.IntentSender intentSender, int i2, int i3, int i4, java.lang.Void r17) {
        this.mPm.mInstallerService.uninstall(new android.content.pm.VersionedPackage(str, -1), str2, i, intentSender, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Void lambda$requestArchive$1(java.lang.String str, android.content.IntentSender intentSender, java.lang.Throwable th) {
        android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Failed to archive %s with message %s", new java.lang.Object[]{str, th.getMessage()}));
        sendFailureStatus(intentSender, str, th.getMessage());
        return null;
    }

    @android.annotation.NonNull
    public int requestUnarchiveOnActivityStart(@android.annotation.Nullable android.content.Intent intent, @android.annotation.Nullable java.lang.String str, int i, int i2) {
        java.lang.String packageNameFromIntent = getPackageNameFromIntent(intent);
        if (packageNameFromIntent == null) {
            android.util.Slog.e(TAG, "packageName cannot be null for unarchival!");
            return -92;
        }
        if (str == null) {
            android.util.Slog.e(TAG, "callerPackageName cannot be null for unarchival!");
            return -92;
        }
        java.lang.String currentLauncherPackageName = getCurrentLauncherPackageName(getParentUserId(i));
        if ((currentLauncherPackageName == null || !str.equals(currentLauncherPackageName)) && i2 != 2000) {
            android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("callerPackageName: %s does not qualify for unarchival of package: %s!", new java.lang.Object[]{str, packageNameFromIntent}));
            return -94;
        }
        android.util.Slog.i(TAG, android.text.TextUtils.formatSimple("Unarchival is starting for: %s", new java.lang.Object[]{packageNameFromIntent}));
        try {
            requestUnarchive(packageNameFromIntent, str, getOrCreateLauncherListener(i, packageNameFromIntent), android.os.UserHandle.of(i), getAppOpsManager().checkOp(146, i2, str) == 0);
            return 102;
        } catch (java.lang.Throwable th) {
            android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Unexpected error occurred while unarchiving package %s: %s.", new java.lang.Object[]{packageNameFromIntent, th.getLocalizedMessage()}));
            return 102;
        }
    }

    private int getParentUserId(int i) {
        android.content.pm.UserInfo profileParent = getUserManager().getProfileParent(i);
        return profileParent == null ? i : profileParent.id;
    }

    public boolean isIntentResolvedToArchivedApp(android.content.Intent intent, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        java.lang.String packageNameFromIntent = getPackageNameFromIntent(intent);
        if (packageNameFromIntent == null || intent.getComponent() == null || (packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(packageNameFromIntent)) == null) {
            return false;
        }
        com.android.server.pm.pkg.PackageUserState userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!isArchived(userStateOrDefault)) {
            return false;
        }
        java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> activityInfos = userStateOrDefault.getArchiveState().getActivityInfos();
        for (int i2 = 0; i2 < activityInfos.size(); i2++) {
            if (activityInfos.get(i2).getOriginalComponentName().equals(intent.getComponent())) {
                return true;
            }
        }
        android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Package: %s is archived but component to start main activity cannot be found!", new java.lang.Object[]{packageNameFromIntent}));
        return false;
    }

    void clearArchiveState(java.lang.String str, int i) {
        com.android.server.pm.PackageSetting packageLPr;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        clearArchiveState(packageLPr, i);
    }

    void clearArchiveState(com.android.server.pm.PackageSetting packageSetting, int i) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            if (packageSetting != null) {
                try {
                    if (packageSetting.getUserStateOrDefault(i).getArchiveState() != null) {
                        android.util.Slog.e(TAG, "Clearing archive states for " + packageSetting.getPackageName());
                        packageSetting.setArchiveState(null, i);
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        java.io.File iconsDir = getIconsDir(packageSetting.getPackageName(), i);
                        if (!iconsDir.exists()) {
                            android.util.Slog.e(TAG, "Icons are already deleted at " + iconsDir.getAbsolutePath());
                            return;
                        }
                        if (!android.os.FileUtils.deleteContentsAndDir(iconsDir)) {
                            android.util.Slog.e(TAG, "Failed to clean up archive files for " + packageSetting.getPackageName());
                            return;
                        }
                        android.util.Slog.e(TAG, "Deleted icons at " + iconsDir.getAbsolutePath());
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public java.lang.String getCurrentLauncherPackageName(int i) {
        android.content.ComponentName defaultHomeActivity = this.mPm.snapshotComputer().getDefaultHomeActivity(i);
        if (defaultHomeActivity != null) {
            return defaultHomeActivity.getPackageName();
        }
        return null;
    }

    private boolean isCallingPackageValid(java.lang.String str, int i, int i2) {
        if (this.mPm.snapshotComputer().getPackageUid(str, 0L, i2) != i) {
            android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Calling package: %s does not belong to uid: %d", new java.lang.Object[]{str, java.lang.Integer.valueOf(i)}));
            return false;
        }
        return true;
    }

    private android.content.IntentSender getOrCreateLauncherListener(int i, java.lang.String str) {
        android.util.Pair<java.lang.Integer, java.lang.String> create = android.util.Pair.create(java.lang.Integer.valueOf(i), str);
        synchronized (this.mLauncherIntentSenders) {
            try {
                android.content.IntentSender intentSender = this.mLauncherIntentSenders.get(create);
                if (intentSender != null) {
                    return intentSender;
                }
                android.content.IntentSender intentSender2 = new android.content.IntentSender(new com.android.server.pm.PackageArchiver.UnarchiveIntentSender());
                this.mLauncherIntentSenders.put(create, intentSender2);
                return intentSender2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private java.util.concurrent.CompletableFuture<java.lang.Void> createAndStoreArchiveState(final java.lang.String str, final int i) throws android.content.pm.PackageManager.NameNotFoundException {
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        com.android.server.pm.pkg.PackageStateInternal packageState = getPackageState(str, snapshotComputer, android.os.Binder.getCallingUid(), i);
        verifyNotSystemApp(packageState.getFlags());
        verifyInstalled(packageState, i);
        final android.content.pm.ApplicationInfo verifyInstaller = verifyInstaller(snapshotComputer, getResponsibleInstallerPackage(packageState), i);
        verifyOptOutStatus(str, android.os.UserHandle.getUid(i, android.os.UserHandle.getUid(i, packageState.getAppId())));
        final java.util.List<android.content.pm.LauncherActivityInfo> launcherActivityInfos = getLauncherActivityInfos(packageState.getPackageName(), i);
        final java.util.concurrent.CompletableFuture<java.lang.Void> completableFuture = new java.util.concurrent.CompletableFuture<>();
        this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageArchiver.this.lambda$createAndStoreArchiveState$2(str, i, launcherActivityInfos, verifyInstaller, completableFuture);
            }
        });
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createAndStoreArchiveState$2(java.lang.String str, int i, java.util.List list, android.content.pm.ApplicationInfo applicationInfo, java.util.concurrent.CompletableFuture completableFuture) {
        try {
            storeArchiveState(str, createArchiveStateInternal(str, i, list, applicationInfo.loadLabel(this.mContext.getPackageManager()).toString()), i);
            completableFuture.complete(null);
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException e) {
            completableFuture.completeExceptionally(e);
        }
    }

    @android.annotation.Nullable
    com.android.server.pm.pkg.ArchiveState createArchiveState(@android.annotation.NonNull android.content.pm.ArchivedPackageParcel archivedPackageParcel, int i, java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo = this.mPm.snapshotComputer().getApplicationInfo(str, 0L, i);
        if (applicationInfo == null) {
            android.util.Slog.e(TAG, "Couldn't find installer " + str);
            return null;
        }
        int launcherLargeIconSize = ((android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class)).getLauncherLargeIconSize();
        android.content.pm.ArchivedPackageInfo archivedPackageInfo = new android.content.pm.ArchivedPackageInfo(archivedPackageParcel);
        try {
            java.lang.String packageName = archivedPackageInfo.getPackageName();
            java.util.List launcherActivities = archivedPackageInfo.getLauncherActivities();
            java.util.ArrayList arrayList = new java.util.ArrayList(launcherActivities.size());
            int size = launcherActivities.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.content.pm.ArchivedActivityInfo archivedActivityInfo = (android.content.pm.ArchivedActivityInfo) launcherActivities.get(i2);
                int i3 = i2 * 2;
                arrayList.add(new com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo(archivedActivityInfo.getLabel().toString(), archivedActivityInfo.getComponentName(), storeAdaptiveDrawable(packageName, archivedActivityInfo.getIcon(), i, i3 + 0, launcherLargeIconSize), storeAdaptiveDrawable(packageName, archivedActivityInfo.getMonochromeIcon(), i, i3 + 1, launcherLargeIconSize)));
            }
            return new com.android.server.pm.pkg.ArchiveState(arrayList, applicationInfo.loadLabel(this.mContext.getPackageManager()).toString());
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to create archive state", e);
            return null;
        }
    }

    com.android.server.pm.pkg.ArchiveState createArchiveStateInternal(java.lang.String str, int i, java.util.List<android.content.pm.LauncherActivityInfo> list, java.lang.String str2) throws java.io.IOException {
        int launcherLargeIconSize = ((android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class)).getLauncherLargeIconSize();
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.LauncherActivityInfo launcherActivityInfo = list.get(i2);
            arrayList.add(new com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo(launcherActivityInfo.getLabel().toString(), launcherActivityInfo.getComponentName(), storeIcon(str, launcherActivityInfo, i, (i2 * 2) + 0, launcherLargeIconSize), null));
        }
        return new com.android.server.pm.pkg.ArchiveState(arrayList, str2);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.nio.file.Path storeIcon(java.lang.String str, android.content.pm.LauncherActivityInfo launcherActivityInfo, int i, int i2, int i3) throws java.io.IOException {
        if (launcherActivityInfo.getActivityInfo().getIconResource() == 0) {
            return null;
        }
        return storeDrawable(str, launcherActivityInfo.getIcon(0), i, i2, i3);
    }

    private static java.nio.file.Path storeDrawable(java.lang.String str, @android.annotation.Nullable android.graphics.drawable.Drawable drawable, int i, int i2, int i3) throws java.io.IOException {
        if (drawable == null) {
            return null;
        }
        java.io.File file = new java.io.File(createIconsDir(str, i), i2 + ".png");
        android.graphics.Bitmap drawableToBitmap = android.content.pm.ArchivedActivityInfo.drawableToBitmap(drawable, i3);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
        try {
            if (!drawableToBitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                throw new java.io.IOException(android.text.TextUtils.formatSimple("Failure to store icon file %s", new java.lang.Object[]{file.getAbsolutePath()}));
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            if (file.exists()) {
                android.util.Slog.i(TAG, "Stored icon at " + file.getAbsolutePath());
            }
            return file.toPath();
        } catch (java.lang.Throwable th) {
            try {
                fileOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static java.nio.file.Path storeAdaptiveDrawable(java.lang.String str, @android.annotation.Nullable android.graphics.drawable.Drawable drawable, int i, int i2, int i3) throws java.io.IOException {
        if (drawable == null) {
            return null;
        }
        float extraInsetFraction = android.graphics.drawable.AdaptiveIconDrawable.getExtraInsetFraction();
        float f = extraInsetFraction / ((2.0f * extraInsetFraction) + 1.0f);
        return storeDrawable(str, new android.graphics.drawable.AdaptiveIconDrawable(new android.graphics.drawable.ColorDrawable(android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK), new android.graphics.drawable.InsetDrawable(drawable, f, f, f, f)), i, i2, i3);
    }

    private android.content.pm.ApplicationInfo verifyInstaller(com.android.server.pm.Computer computer, java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new android.content.pm.PackageManager.NameNotFoundException("No installer found");
        }
        if (android.os.Binder.getCallingUid() != 2000 && !verifySupportsUnarchival(str, i)) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Installer does not support unarchival");
        }
        android.content.pm.ApplicationInfo applicationInfo = computer.getApplicationInfo(str, 0L, i);
        if (applicationInfo == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Failed to obtain Installer info");
        }
        return applicationInfo;
    }

    public boolean verifySupportsUnarchival(java.lang.String str, final int i) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        final android.content.Intent intent = new android.content.Intent("android.intent.action.UNARCHIVE_PACKAGE").setPackage(str);
        android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda1
            public final java.lang.Object getOrThrow() {
                android.content.pm.ParceledListSlice lambda$verifySupportsUnarchival$3;
                lambda$verifySupportsUnarchival$3 = com.android.server.pm.PackageArchiver.this.lambda$verifySupportsUnarchival$3(intent, i);
                return lambda$verifySupportsUnarchival$3;
            }
        });
        return (parceledListSlice == null || parceledListSlice.getList().isEmpty()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.pm.ParceledListSlice lambda$verifySupportsUnarchival$3(android.content.Intent intent, int i) throws java.lang.Exception {
        return this.mPm.queryIntentReceivers(this.mPm.snapshotComputer(), intent, null, 0L, i);
    }

    private void verifyNotSystemApp(int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if ((i & 1) != 0 || (i & 128) != 0) {
            throw new android.content.pm.PackageManager.NameNotFoundException("System apps cannot be archived.");
        }
    }

    private void verifyInstalled(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (!packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
            throw new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("%s is not installed.", new java.lang.Object[]{packageStateInternal.getPackageName()}));
        }
    }

    public boolean isAppArchivable(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(userHandle);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        int identifier = userHandle.getIdentifier();
        snapshotComputer.enforceCrossUserPermission(android.os.Binder.getCallingUid(), identifier, true, true, "isAppArchivable");
        try {
            com.android.server.pm.pkg.PackageStateInternal packageState = getPackageState(str, this.mPm.snapshotComputer(), android.os.Binder.getCallingUid(), identifier);
            if ((packageState.getFlags() & 1) != 0 || (packageState.getFlags() & 128) != 0 || isAppOptedOutOfArchiving(str, packageState.getAppId())) {
                return false;
            }
            try {
                verifyInstaller(snapshotComputer, getResponsibleInstallerPackage(packageState), identifier);
                getLauncherActivityInfos(str, identifier);
                return true;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return false;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            throw new android.os.ParcelableException(e2);
        }
    }

    private boolean isAppOptedOutOfArchiving(final java.lang.String str, final int i) {
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda0
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isAppOptedOutOfArchiving$4;
                lambda$isAppOptedOutOfArchiving$4 = com.android.server.pm.PackageArchiver.this.lambda$isAppOptedOutOfArchiving$4(i, str);
                return lambda$isAppOptedOutOfArchiving$4;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isAppOptedOutOfArchiving$4(int i, java.lang.String str) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(getAppOpsManager().checkOpNoThrow(97, i, str) == 1);
    }

    private void verifyOptOutStatus(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (isAppOptedOutOfArchiving(str, i)) {
            throw new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("The app %s is opted out of archiving.", new java.lang.Object[]{str}));
        }
    }

    void requestUnarchive(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.content.IntentSender intentSender, @android.annotation.NonNull android.os.UserHandle userHandle) {
        requestUnarchive(str, str2, intentSender, userHandle, false);
    }

    private void requestUnarchive(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull final android.content.IntentSender intentSender, @android.annotation.NonNull final android.os.UserHandle userHandle, boolean z) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        java.util.Objects.requireNonNull(intentSender);
        java.util.Objects.requireNonNull(userHandle);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        final int identifier = userHandle.getIdentifier();
        int callingUid = android.os.Binder.getCallingUid();
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)) {
            verifyCaller(snapshotComputer.getPackageUid(str2, 0L, identifier), callingUid);
        }
        snapshotComputer.enforceCrossUserPermission(callingUid, identifier, true, true, "unarchiveApp");
        try {
            com.android.server.pm.pkg.PackageStateInternal packageState = getPackageState(str, snapshotComputer, callingUid, identifier);
            com.android.server.pm.pkg.PackageStateInternal packageState2 = getPackageState(str2, snapshotComputer, callingUid, identifier);
            verifyArchived(packageState, identifier);
            final java.lang.String responsibleInstallerPackage = getResponsibleInstallerPackage(packageState);
            if (responsibleInstallerPackage == null) {
                throw new android.os.ParcelableException(new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("No installer found to unarchive app %s.", new java.lang.Object[]{str})));
            }
            boolean z2 = this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0;
            boolean contains = packageState2.getAndroidPackage().getRequestedPermissions().contains("android.permission.REQUEST_INSTALL_PACKAGES");
            if (!z2 && !contains) {
                throw new java.lang.SecurityException("You need the com.android.permission.INSTALL_PACKAGES or com.android.permission.REQUEST_INSTALL_PACKAGES permission to request an unarchival.");
            }
            if (!z2 || z) {
                requestUnarchiveConfirmation(str, intentSender, userHandle);
                return;
            }
            try {
                final int intValue = ((java.lang.Integer) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda4
                    public final java.lang.Object getOrThrow() {
                        java.lang.Integer lambda$requestUnarchive$5;
                        lambda$requestUnarchive$5 = com.android.server.pm.PackageArchiver.this.lambda$requestUnarchive$5(str, responsibleInstallerPackage, intentSender, identifier);
                        return lambda$requestUnarchive$5;
                    }
                })).intValue();
                this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.PackageArchiver.this.lambda$requestUnarchive$6(str, userHandle, responsibleInstallerPackage, intValue);
                    }
                });
            } catch (java.lang.RuntimeException e) {
                if (e.getCause() instanceof java.io.IOException) {
                    throw android.util.ExceptionUtils.wrap((java.io.IOException) e.getCause());
                }
                throw e;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            throw new android.os.ParcelableException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$requestUnarchive$5(java.lang.String str, java.lang.String str2, android.content.IntentSender intentSender, int i) throws java.lang.Exception {
        return java.lang.Integer.valueOf(createDraftSession(str, str2, intentSender, i));
    }

    private void requestUnarchiveConfirmation(java.lang.String str, android.content.IntentSender intentSender, android.os.UserHandle userHandle) {
        android.content.Intent intent = new android.content.Intent(ACTION_UNARCHIVE_DIALOG);
        intent.putExtra(EXTRA_UNARCHIVE_INTENT_SENDER, intentSender);
        intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str);
        android.content.Intent intent2 = new android.content.Intent();
        intent2.putExtra("android.content.pm.extra.PACKAGE_NAME", str);
        intent2.putExtra("android.content.pm.extra.UNARCHIVE_STATUS", -1);
        intent2.putExtra("android.intent.extra.INTENT", intent);
        intent2.putExtra("android.intent.extra.USER", userHandle);
        sendIntent(intentSender, str, "", intent2);
    }

    private void verifyUninstallPermissions() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_PACKAGES") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_DELETE_PACKAGES") != 0) {
            throw new java.lang.SecurityException("You need the com.android.permission.DELETE_PACKAGES or com.android.permission.REQUEST_DELETE_PACKAGES permission to request an archival.");
        }
    }

    private int createDraftSession(java.lang.String str, java.lang.String str2, android.content.IntentSender intentSender, int i) throws java.io.IOException {
        android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(1);
        sessionParams.setAppPackageName(str);
        sessionParams.installFlags = 536870912;
        int packageUid = this.mPm.snapshotComputer().getPackageUid(str2, 0L, i);
        int existingDraftSessionId = this.mPm.mInstallerService.getExistingDraftSessionId(packageUid, sessionParams, i);
        if (existingDraftSessionId != -1) {
            attachListenerToSession(intentSender, existingDraftSessionId, i);
            return existingDraftSessionId;
        }
        final int createSessionInternal = this.mPm.mInstallerService.createSessionInternal(sessionParams, str2, this.mContext.getAttributionTag(), packageUid, i);
        attachListenerToSession(intentSender, createSessionInternal, i);
        this.mPm.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageArchiver.this.lambda$createDraftSession$7(createSessionInternal);
            }
        }, getUnarchiveForegroundTimeout());
        return createSessionInternal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDraftSession$7(int i) {
        this.mPm.mInstallerService.cleanupDraftIfUnclaimed(i);
    }

    private void attachListenerToSession(android.content.IntentSender intentSender, int i, int i2) {
        com.android.server.pm.PackageInstallerSession session = this.mPm.mInstallerService.getSession(i);
        int unarchivalStatus = session.getUnarchivalStatus();
        if (unarchivalStatus == 0) {
            notifyUnarchivalListener(0, session.getInstallerPackageName(), session.params.appPackageName, 0L, null, java.util.Set.of(intentSender), i2);
        } else {
            if (unarchivalStatus != -1) {
                throw new java.lang.IllegalStateException(android.text.TextUtils.formatSimple("Session %s has unarchive status%s but is still active.", new java.lang.Object[]{java.lang.Integer.valueOf(session.sessionId), java.lang.Integer.valueOf(unarchivalStatus)}));
            }
            session.registerUnarchivalListener(intentSender);
        }
    }

    @android.annotation.Nullable
    public android.graphics.Bitmap getArchivedAppIcon(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle, java.lang.String str2) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(userHandle);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = android.os.Binder.getCallingUid();
        int identifier = userHandle.getIdentifier();
        try {
            com.android.server.pm.pkg.ArchiveState anyArchiveState = getAnyArchiveState(getPackageState(str, snapshotComputer, callingUid, identifier), identifier);
            if (anyArchiveState == null || anyArchiveState.getActivityInfos().size() == 0) {
                return null;
            }
            android.graphics.Bitmap decodeIcon = decodeIcon(anyArchiveState.getActivityInfos().get(0));
            if (decodeIcon != null && getAppOpsManager().checkOp(145, callingUid, str2) == 0) {
                return includeCloudOverlay(decodeIcon);
            }
            return decodeIcon;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Package %s couldn't be found.", new java.lang.Object[]{str}), e);
            return null;
        }
    }

    @android.annotation.Nullable
    private com.android.server.pm.pkg.ArchiveState getAnyArchiveState(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (isArchived(userStateOrDefault)) {
            return userStateOrDefault.getArchiveState();
        }
        for (int i2 = 0; i2 < packageStateInternal.getUserStates().size(); i2++) {
            com.android.server.pm.pkg.PackageUserStateInternal valueAt = packageStateInternal.getUserStates().valueAt(i2);
            if (isArchived(valueAt)) {
                return valueAt.getArchiveState();
            }
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.graphics.Bitmap decodeIcon(com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo archiveActivityInfo) {
        java.nio.file.Path iconBitmap = archiveActivityInfo.getIconBitmap();
        if (iconBitmap == null) {
            return null;
        }
        android.graphics.Bitmap decodeFile = android.graphics.BitmapFactory.decodeFile(iconBitmap.toString());
        if (decodeFile == null) {
            android.util.Slog.e(TAG, "Archived icon cannot be decoded " + iconBitmap.toAbsolutePath());
            return null;
        }
        return decodeFile;
    }

    @android.annotation.Nullable
    android.graphics.Bitmap includeCloudOverlay(android.graphics.Bitmap bitmap) {
        android.graphics.drawable.Drawable drawable = this.mContext.getResources().getDrawable(android.R.drawable.app_icon_background, this.mContext.getTheme());
        if (drawable == null) {
            android.util.Slog.e(TAG, "Unable to locate cloud overlay for archived app!");
            return bitmap;
        }
        android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), bitmap);
        bitmapDrawable.setColorFilter(OPACITY_LAYER_FILTER);
        bitmapDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        android.graphics.Bitmap drawableToBitmap = android.content.pm.ArchivedActivityInfo.drawableToBitmap(new android.graphics.drawable.LayerDrawable(new android.graphics.drawable.Drawable[]{bitmapDrawable, drawable}), ((android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class)).getLauncherLargeIconSize());
        if (bitmap != null) {
            bitmap.recycle();
        }
        return drawableToBitmap;
    }

    private void verifyArchived(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (!isArchived(packageStateInternal.getUserStateOrDefault(i))) {
            throw new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("Package %s is not currently archived.", new java.lang.Object[]{packageStateInternal.getPackageName()}));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission(allOf = {"android.permission.INTERACT_ACROSS_USERS", "android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "android.permission.START_ACTIVITIES_FROM_BACKGROUND", "android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND"}, conditional = true)
    /* renamed from: unarchiveInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$requestUnarchive$6(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2, int i) {
        android.os.UserHandle userHandle2;
        int identifier = userHandle.getIdentifier();
        android.content.Intent intent = new android.content.Intent("android.intent.action.UNARCHIVE_PACKAGE");
        intent.addFlags(268435456);
        intent.putExtra("android.content.pm.extra.UNARCHIVE_ID", i);
        intent.putExtra("android.content.pm.extra.UNARCHIVE_PACKAGE_NAME", str);
        intent.putExtra("android.content.pm.extra.UNARCHIVE_ALL_USERS", identifier == -1);
        intent.setPackage(str2);
        if (identifier == -1) {
            userHandle2 = android.os.UserHandle.of(this.mPm.mUserManager.getCurrentUserId());
        } else {
            userHandle2 = userHandle;
        }
        this.mContext.sendOrderedBroadcastAsUser(intent, userHandle2, null, -1, createUnarchiveOptions(), null, null, 0, null, null);
    }

    java.util.List<android.content.pm.LauncherActivityInfo> getLauncherActivityInfos(final java.lang.String str, final int i) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.List<android.content.pm.LauncherActivityInfo> list = (java.util.List) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda3
            public final java.lang.Object getOrThrow() {
                java.util.List lambda$getLauncherActivityInfos$8;
                lambda$getLauncherActivityInfos$8 = com.android.server.pm.PackageArchiver.this.lambda$getLauncherActivityInfos$8(str, i);
                return lambda$getLauncherActivityInfos$8;
            }
        });
        if (list.isEmpty()) {
            throw new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("The app %s does not have a main activity.", new java.lang.Object[]{str}));
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.List lambda$getLauncherActivityInfos$8(java.lang.String str, int i) throws java.lang.Exception {
        return getLauncherApps().getActivityList(str, new android.os.UserHandle(i));
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.CHANGE_DEVICE_IDLE_TEMP_WHITELIST", "android.permission.START_ACTIVITIES_FROM_BACKGROUND", "android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND"})
    private android.os.Bundle createUnarchiveOptions() {
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(getUnarchiveForegroundTimeout(), 0, com.android.internal.util.FrameworkStatsLog.AUTO_ROTATE_REPORTED, "");
        return makeBasic.toBundle();
    }

    private static int getUnarchiveForegroundTimeout() {
        return DEFAULT_UNARCHIVE_FOREGROUND_TIMEOUT_MS;
    }

    static java.lang.String getResponsibleInstallerPackage(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (android.text.TextUtils.isEmpty(packageStateInternal.getInstallSource().mUpdateOwnerPackageName)) {
            return packageStateInternal.getInstallSource().mInstallerPackageName;
        }
        return packageStateInternal.getInstallSource().mUpdateOwnerPackageName;
    }

    void notifyUnarchivalListener(int i, java.lang.String str, java.lang.String str2, long j, @android.annotation.Nullable android.app.PendingIntent pendingIntent, java.util.Set<android.content.IntentSender> set, int i2) {
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str2);
        intent.putExtra("android.content.pm.extra.UNARCHIVE_STATUS", i);
        if (i != 0) {
            android.content.Intent createErrorDialogIntent = createErrorDialogIntent(i, str, str2, j, pendingIntent, i2);
            if (createErrorDialogIntent == null) {
                return;
            }
            intent.putExtra("android.intent.extra.INTENT", createErrorDialogIntent);
            intent.putExtra("android.intent.extra.USER", android.os.UserHandle.of(i2));
        }
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setPendingIntentBackgroundActivityStartMode(2);
        java.util.Iterator<android.content.IntentSender> it = set.iterator();
        while (it.hasNext()) {
            try {
                try {
                    it.next().sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
                    synchronized (this.mLauncherIntentSenders) {
                        this.mLauncherIntentSenders.remove(android.util.Pair.create(java.lang.Integer.valueOf(i2), str2));
                    }
                } catch (android.content.IntentSender.SendIntentException e) {
                    android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Failed to send unarchive intent", new java.lang.Object[0]), e);
                    synchronized (this.mLauncherIntentSenders) {
                        this.mLauncherIntentSenders.remove(android.util.Pair.create(java.lang.Integer.valueOf(i2), str2));
                    }
                }
            } catch (java.lang.Throwable th) {
                synchronized (this.mLauncherIntentSenders) {
                    this.mLauncherIntentSenders.remove(android.util.Pair.create(java.lang.Integer.valueOf(i2), str2));
                    throw th;
                }
            }
        }
    }

    @android.annotation.Nullable
    private android.content.Intent createErrorDialogIntent(int i, java.lang.String str, java.lang.String str2, long j, android.app.PendingIntent pendingIntent, int i2) {
        android.content.Intent intent = new android.content.Intent(ACTION_UNARCHIVE_ERROR_DIALOG);
        intent.putExtra("android.content.pm.extra.UNARCHIVE_STATUS", i);
        intent.putExtra("android.intent.extra.USER", android.os.UserHandle.of(i2));
        if (j > 0) {
            intent.putExtra(EXTRA_REQUIRED_BYTES, j);
        }
        if (pendingIntent != null) {
            intent.putExtra("android.intent.extra.INTENT", pendingIntent);
        }
        intent.putExtra(EXTRA_INSTALLER_PACKAGE_NAME, str);
        java.lang.String installerTitle = getInstallerTitle(str2, i2);
        if (installerTitle == null) {
            return null;
        }
        intent.putExtra(EXTRA_INSTALLER_TITLE, installerTitle);
        return intent;
    }

    private java.lang.String getInstallerTitle(java.lang.String str, int i) {
        try {
            com.android.server.pm.pkg.ArchiveState archiveState = getPackageState(str, this.mPm.snapshotComputer(), 1000, i).getUserStateOrDefault(i).getArchiveState();
            if (archiveState == null) {
                android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("notifyUnarchivalListener: App not archived %s.", new java.lang.Object[]{str}));
                return null;
            }
            return archiveState.getInstallerTitle();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("notifyUnarchivalListener: Couldn't fetch package state for %s.", new java.lang.Object[]{str}), e);
            return null;
        }
    }

    @android.annotation.NonNull
    private static com.android.server.pm.pkg.PackageStateInternal getPackageState(java.lang.String str, com.android.server.pm.Computer computer, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException {
        com.android.server.pm.pkg.PackageStateInternal packageStateFiltered = computer.getPackageStateFiltered(str, i, i2);
        if (packageStateFiltered == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("Package %s not found.", new java.lang.Object[]{str}));
        }
        return packageStateFiltered;
    }

    private android.content.pm.LauncherApps getLauncherApps() {
        if (this.mLauncherApps == null) {
            this.mLauncherApps = (android.content.pm.LauncherApps) this.mContext.getSystemService(android.content.pm.LauncherApps.class);
        }
        return this.mLauncherApps;
    }

    private android.app.AppOpsManager getAppOpsManager() {
        if (this.mAppOpsManager == null) {
            this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        }
        return this.mAppOpsManager;
    }

    private android.os.UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        }
        return this.mUserManager;
    }

    private void storeArchiveState(java.lang.String str, com.android.server.pm.pkg.ArchiveState archiveState, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                getPackageSettingLocked(str, i).modifyUserState(i).setArchiveState(archiveState);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private com.android.server.pm.PackageSetting getPackageSettingLocked(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        com.android.server.pm.PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
        if (packageLPr == null || !packageLPr.getUserStateOrDefault(i).isInstalled()) {
            throw new android.content.pm.PackageManager.NameNotFoundException(android.text.TextUtils.formatSimple("Package %s not found.", new java.lang.Object[]{str}));
        }
        return packageLPr;
    }

    private void sendFailureStatus(android.content.IntentSender intentSender, java.lang.String str, java.lang.String str2) {
        android.util.Slog.d(TAG, android.text.TextUtils.formatSimple("Failed to archive %s with message %s", new java.lang.Object[]{str, str2}));
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str);
        intent.putExtra("android.content.pm.extra.STATUS", 1);
        intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", str2);
        sendIntent(intentSender, str, str2, intent);
    }

    private void sendIntent(android.content.IntentSender intentSender, java.lang.String str, java.lang.String str2, android.content.Intent intent) {
        try {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityStartMode(2);
            intentSender.sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
            android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Failed to send status for %s with message %s", new java.lang.Object[]{str, str2}), e);
        }
    }

    private static void verifyCaller(int i, int i2) {
        if (i != i2) {
            throw new java.lang.SecurityException(android.text.TextUtils.formatSimple("The UID %s of callerPackageName set by the caller doesn't match the caller's actual UID %s.", new java.lang.Object[]{java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)}));
        }
    }

    private static java.io.File createIconsDir(java.lang.String str, int i) throws java.io.IOException {
        java.io.File iconsDir = getIconsDir(str, i);
        if (!iconsDir.isDirectory()) {
            iconsDir.delete();
            iconsDir.mkdirs();
            if (!iconsDir.isDirectory()) {
                throw new java.io.IOException("Unable to create directory " + iconsDir);
            }
            android.util.Slog.i(TAG, "Created icons directory at " + iconsDir.getAbsolutePath());
        }
        android.os.SELinux.restorecon(iconsDir);
        return iconsDir;
    }

    private static java.io.File getIconsDir(java.lang.String str, int i) {
        return new java.io.File(new java.io.File(android.os.Environment.getDataSystemCeDirectory(i), ARCHIVE_ICONS_DIR), str);
    }

    private static byte[] bytesFromBitmapFile(java.nio.file.Path path) throws java.io.IOException {
        if (path == null) {
            return null;
        }
        return android.content.pm.ArchivedActivityInfo.bytesFromBitmap(android.graphics.BitmapFactory.decodeFile(path.toString()));
    }

    @android.annotation.Nullable
    private static java.lang.String getPackageNameFromIntent(@android.annotation.Nullable android.content.Intent intent) {
        if (intent == null) {
            return null;
        }
        if (intent.getPackage() != null) {
            return intent.getPackage();
        }
        if (intent.getComponent() == null) {
            return null;
        }
        return intent.getComponent().getPackageName();
    }

    static android.content.pm.ArchivedActivityParcel[] createArchivedActivities(com.android.server.pm.pkg.ArchiveState archiveState) throws java.io.IOException {
        java.util.List<com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo> activityInfos = archiveState.getActivityInfos();
        if (activityInfos == null || activityInfos.isEmpty()) {
            throw new java.lang.IllegalArgumentException("No activities in archive state");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(activityInfos.size());
        int size = activityInfos.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.pkg.ArchiveState.ArchiveActivityInfo archiveActivityInfo = activityInfos.get(i);
            if (archiveActivityInfo != null) {
                android.content.pm.ArchivedActivityParcel archivedActivityParcel = new android.content.pm.ArchivedActivityParcel();
                archivedActivityParcel.title = archiveActivityInfo.getTitle();
                archivedActivityParcel.originalComponentName = archiveActivityInfo.getOriginalComponentName();
                archivedActivityParcel.iconBitmap = bytesFromBitmapFile(archiveActivityInfo.getIconBitmap());
                archivedActivityParcel.monochromeIconBitmap = bytesFromBitmapFile(archiveActivityInfo.getMonochromeIconBitmap());
                arrayList.add(archivedActivityParcel);
            }
        }
        if (arrayList.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Failed to extract title and icon of main activities");
        }
        return (android.content.pm.ArchivedActivityParcel[]) arrayList.toArray(new android.content.pm.ArchivedActivityParcel[arrayList.size()]);
    }

    static android.content.pm.ArchivedActivityParcel[] createArchivedActivities(java.util.List<android.content.pm.LauncherActivityInfo> list, int i) throws java.io.IOException {
        if (list == null || list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("No launcher activities");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.LauncherActivityInfo launcherActivityInfo = list.get(i2);
            if (launcherActivityInfo != null) {
                android.content.pm.ArchivedActivityParcel archivedActivityParcel = new android.content.pm.ArchivedActivityParcel();
                archivedActivityParcel.title = launcherActivityInfo.getLabel().toString();
                archivedActivityParcel.originalComponentName = launcherActivityInfo.getComponentName();
                archivedActivityParcel.iconBitmap = launcherActivityInfo.getActivityInfo().getIconResource() == 0 ? null : android.content.pm.ArchivedActivityInfo.bytesFromBitmap(android.content.pm.ArchivedActivityInfo.drawableToBitmap(launcherActivityInfo.getIcon(0), i));
                archivedActivityParcel.monochromeIconBitmap = null;
                arrayList.add(archivedActivityParcel);
            }
        }
        if (arrayList.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Failed to extract title and icon of main activities");
        }
        return (android.content.pm.ArchivedActivityParcel[]) arrayList.toArray(new android.content.pm.ArchivedActivityParcel[arrayList.size()]);
    }

    private class UnarchiveIntentSender extends android.content.IIntentSender.Stub {
        private UnarchiveIntentSender() {
        }

        public void send(int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
            if (intent.getExtras().getInt("android.content.pm.extra.UNARCHIVE_STATUS", -1) == 0) {
                return;
            }
            android.content.Intent intent2 = (android.content.Intent) intent.getParcelableExtra("android.intent.extra.INTENT", android.content.Intent.class);
            android.os.UserHandle userHandle = (android.os.UserHandle) intent.getParcelableExtra("android.intent.extra.USER", android.os.UserHandle.class);
            if (intent2 != null && userHandle != null && com.android.server.pm.PackageArchiver.this.mAppStateHelper.isAppTopVisible(com.android.server.pm.PackageArchiver.this.getCurrentLauncherPackageName(userHandle.getIdentifier()))) {
                intent2.setFlags(268435456);
                com.android.server.pm.PackageArchiver.this.mContext.startActivityAsUser(intent2, userHandle);
            }
        }
    }
}
