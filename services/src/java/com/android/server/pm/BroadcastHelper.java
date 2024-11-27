package com.android.server.pm;

/* loaded from: classes2.dex */
public final class BroadcastHelper {
    private static final boolean DEBUG_BROADCASTS = false;
    private static final java.lang.String[] INSTANT_APP_BROADCAST_PERMISSION = {"android.permission.ACCESS_INSTANT_APPS"};
    private final android.app.ActivityManagerInternal mAmInternal;
    private final com.android.server.pm.AppsFilterSnapshot mAppsFilter;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final com.android.server.pm.PackageMonitorCallbackHelper mPackageMonitorCallbackHelper;
    private final com.android.server.pm.UserManagerInternal mUmInternal;

    BroadcastHelper(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector) {
        this.mUmInternal = packageManagerServiceInjector.getUserManagerInternal();
        this.mAmInternal = packageManagerServiceInjector.getActivityManagerInternal();
        this.mContext = packageManagerServiceInjector.getContext();
        this.mHandler = packageManagerServiceInjector.getHandler();
        this.mPackageMonitorCallbackHelper = packageManagerServiceInjector.getPackageMonitorCallbackHelper();
        this.mAppsFilter = packageManagerServiceInjector.getAppsFilter();
    }

    void sendPackageBroadcastWithIntent(android.content.Intent intent, int i, boolean z, int i2, int[] iArr, android.content.IIntentReceiver iIntentReceiver, @android.annotation.Nullable java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, @android.annotation.Nullable android.os.Bundle bundle) {
        intent.addFlags(67108864 | i2);
        android.util.SparseArray<int[]> sparseArray = new android.util.SparseArray<>();
        sparseArray.put(i, iArr);
        broadcastIntent(intent, iIntentReceiver, z, i, sparseArray, biFunction, bundle);
    }

    void sendPackageBroadcast(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, int i, java.lang.String str3, android.content.IIntentReceiver iIntentReceiver, int[] iArr, int[] iArr2, @android.annotation.Nullable android.util.SparseArray<int[]> sparseArray, @android.annotation.Nullable java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, @android.annotation.Nullable android.os.Bundle bundle2) {
        int[] iArr3;
        try {
            android.app.IActivityManager service = android.app.ActivityManager.getService();
            if (service == null) {
                return;
            }
            if (iArr == null) {
                iArr3 = service.getRunningUserIds();
            } else {
                iArr3 = iArr;
            }
            if (com.android.internal.util.ArrayUtils.isEmpty(iArr2)) {
                doSendBroadcast(str, str2, bundle, i, str3, iIntentReceiver, iArr3, false, sparseArray, biFunction, bundle2);
            } else {
                doSendBroadcast(str, str2, bundle, i, str3, iIntentReceiver, iArr2, true, null, null, bundle2);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    private void doSendBroadcast(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.os.Bundle bundle, int i, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable android.content.IIntentReceiver iIntentReceiver, @android.annotation.NonNull int[] iArr, boolean z, @android.annotation.Nullable android.util.SparseArray<int[]> sparseArray, @android.annotation.Nullable java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, @android.annotation.Nullable android.os.Bundle bundle2) {
        for (int i2 : iArr) {
            android.net.Uri uri = null;
            if (str2 != null) {
                uri = android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, str2, null);
            }
            android.content.Intent intent = new android.content.Intent(str, uri);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (str3 != null) {
                intent.setPackage(str3);
            }
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra >= 0 && android.os.UserHandle.getUserId(intExtra) != i2) {
                intent.putExtra("android.intent.extra.UID", android.os.UserHandle.getUid(i2, android.os.UserHandle.getAppId(intExtra)));
            }
            if (sparseArray != null && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str3)) {
                intent.putExtra("android.intent.extra.VISIBILITY_ALLOW_LIST", sparseArray.get(i2));
            }
            intent.putExtra("android.intent.extra.user_handle", i2);
            intent.addFlags(i | 67108864);
            broadcastIntent(intent, iIntentReceiver, z, i2, sparseArray, biFunction, bundle2);
        }
    }

    private void broadcastIntent(android.content.Intent intent, android.content.IIntentReceiver iIntentReceiver, boolean z, int i, @android.annotation.Nullable android.util.SparseArray<int[]> sparseArray, @android.annotation.Nullable java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction, @android.annotation.Nullable android.os.Bundle bundle) {
        boolean z2;
        java.lang.String[] strArr = z ? INSTANT_APP_BROADCAST_PERMISSION : null;
        if (this.mAmInternal.isModernQueueEnabled()) {
            z2 = false;
        } else {
            z2 = iIntentReceiver != null;
        }
        this.mAmInternal.broadcastIntent(intent, iIntentReceiver, strArr, z2, i, sparseArray != null ? sparseArray.get(i) : null, biFunction, bundle);
    }

    void sendResourcesChangedBroadcast(@android.annotation.NonNull final com.android.server.pm.Computer computer, boolean z, boolean z2, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull int[] iArr) {
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr) || com.android.internal.util.ArrayUtils.isEmpty(iArr)) {
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        if (z2) {
            bundle.putBoolean("android.intent.extra.REPLACING", z2);
        }
        sendPackageBroadcast(z ? "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE" : "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE", null, bundle, 0, null, null, null, null, null, new java.util.function.BiFunction() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                android.os.Bundle lambda$sendResourcesChangedBroadcast$0;
                lambda$sendResourcesChangedBroadcast$0 = com.android.server.pm.BroadcastHelper.lambda$sendResourcesChangedBroadcast$0(com.android.server.pm.Computer.this, (java.lang.Integer) obj, (android.os.Bundle) obj2);
                return lambda$sendResourcesChangedBroadcast$0;
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.Bundle lambda$sendResourcesChangedBroadcast$0(com.android.server.pm.Computer computer, java.lang.Integer num, android.os.Bundle bundle) {
        return filterExtrasChangedPackageList(computer, num.intValue(), bundle);
    }

    private void sendBootCompletedBroadcastToSystemApp(@android.annotation.NonNull java.lang.String str, boolean z, int i) {
        if (!this.mUmInternal.isUserRunning(i)) {
            return;
        }
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        try {
            android.content.Intent intent = new android.content.Intent("android.intent.action.LOCKED_BOOT_COMPLETED").setPackage(str);
            intent.putExtra("android.intent.extra.user_handle", i);
            if (z) {
                intent.addFlags(32);
            }
            java.lang.String[] strArr = {"android.permission.RECEIVE_BOOT_COMPLETED"};
            android.app.BroadcastOptions temporaryAppAllowlistBroadcastOptions = getTemporaryAppAllowlistBroadcastOptions(202);
            service.broadcastIntentWithFeature((android.app.IApplicationThread) null, (java.lang.String) null, intent, (java.lang.String) null, (android.content.IIntentReceiver) null, 0, (java.lang.String) null, (android.os.Bundle) null, strArr, (java.lang.String[]) null, (java.lang.String[]) null, -1, temporaryAppAllowlistBroadcastOptions.toBundle(), false, false, i);
            if (this.mUmInternal.isUserUnlockingOrUnlocked(i)) {
                android.content.Intent intent2 = new android.content.Intent("android.intent.action.BOOT_COMPLETED").setPackage(str);
                intent2.putExtra("android.intent.extra.user_handle", i);
                if (z) {
                    intent2.addFlags(32);
                }
                service.broadcastIntentWithFeature((android.app.IApplicationThread) null, (java.lang.String) null, intent2, (java.lang.String) null, (android.content.IIntentReceiver) null, 0, (java.lang.String) null, (android.os.Bundle) null, strArr, (java.lang.String[]) null, (java.lang.String[]) null, -1, temporaryAppAllowlistBroadcastOptions.toBundle(), false, false, i);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.NonNull
    private android.app.BroadcastOptions getTemporaryAppAllowlistBroadcastOptions(int i) {
        long j;
        if (this.mAmInternal == null) {
            j = 10000;
        } else {
            j = this.mAmInternal.getBootTimeTempAllowListDuration();
        }
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(j, 0, i, "");
        return makeBasic;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendPackageChangedBroadcast, reason: merged with bridge method [inline-methods] */
    public void lambda$sendPackageChangedBroadcast$4(@android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.NonNull java.util.ArrayList<java.lang.String> arrayList, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable int[] iArr, @android.annotation.Nullable int[] iArr2, @android.annotation.Nullable android.util.SparseArray<int[]> sparseArray) {
        int i2;
        android.os.Bundle bundle = new android.os.Bundle(4);
        bundle.putString("android.intent.extra.changed_component_name", arrayList.get(0));
        java.lang.String[] strArr = new java.lang.String[arrayList.size()];
        arrayList.toArray(strArr);
        bundle.putStringArray("android.intent.extra.changed_component_name_list", strArr);
        bundle.putBoolean("android.intent.extra.DONT_KILL_APP", z);
        bundle.putInt("android.intent.extra.UID", i);
        if (str2 != null) {
            bundle.putString("android.intent.extra.REASON", str2);
        }
        if (arrayList.contains(str)) {
            i2 = 0;
        } else {
            i2 = 1073741824;
        }
        sendPackageBroadcast("android.intent.action.PACKAGE_CHANGED", str, bundle, i2, null, null, iArr, iArr2, sparseArray, null, null);
    }

    static void sendDeviceCustomizationReadyBroadcast() {
        android.content.Intent intent = new android.content.Intent("android.intent.action.DEVICE_CUSTOMIZATION_READY");
        intent.setFlags(16777216);
        try {
            android.app.ActivityManager.getService().broadcastIntentWithFeature((android.app.IApplicationThread) null, (java.lang.String) null, intent, (java.lang.String) null, (android.content.IIntentReceiver) null, 0, (java.lang.String) null, (android.os.Bundle) null, new java.lang.String[]{"android.permission.RECEIVE_DEVICE_CUSTOMIZATION_READY"}, (java.lang.String[]) null, (java.lang.String[]) null, -1, (android.os.Bundle) null, false, false, -1);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void sendSessionCommitBroadcast(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.pm.PackageInstaller.SessionInfo sessionInfo, int i, @android.annotation.Nullable java.lang.String str) {
        com.android.server.pm.UserManagerService userManagerService = com.android.server.pm.UserManagerService.getInstance();
        if (userManagerService == null || sessionInfo.isStaged()) {
            return;
        }
        android.content.pm.UserInfo profileParent = userManagerService.getProfileParent(i);
        int i2 = profileParent != null ? profileParent.id : i;
        android.content.ComponentName defaultHomeActivity = computer.getDefaultHomeActivity(i2);
        if (defaultHomeActivity != null && canLauncherAccessProfile(defaultHomeActivity, i)) {
            this.mContext.sendBroadcastAsUser(new android.content.Intent("android.content.pm.action.SESSION_COMMITTED").putExtra("android.content.pm.extra.SESSION", sessionInfo).putExtra("android.intent.extra.USER", android.os.UserHandle.of(i)).setPackage(defaultHomeActivity.getPackageName()), android.os.UserHandle.of(i2));
        }
        if (str != null) {
            this.mContext.sendBroadcastAsUser(new android.content.Intent("android.content.pm.action.SESSION_COMMITTED").putExtra("android.content.pm.extra.SESSION", sessionInfo).putExtra("android.intent.extra.USER", android.os.UserHandle.of(i)).setPackage(str), android.os.UserHandle.of(i2));
        }
    }

    boolean canLauncherAccessProfile(android.content.ComponentName componentName, int i) {
        return (android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePermissionToAccessHiddenProfiles() && this.mUmInternal.getUserProperties(i).getProfileApiVisibility() == 1 && this.mContext.getPackageManager().checkPermission("android.permission.ACCESS_HIDDEN_PROFILES_FULL", componentName.getPackageName()) != 0 && this.mContext.getPackageManager().checkPermission("android.permission.ACCESS_HIDDEN_PROFILES", componentName.getPackageName()) != 0) ? false : true;
    }

    void sendPreferredActivityChangedBroadcast(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BroadcastHelper.lambda$sendPreferredActivityChangedBroadcast$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendPreferredActivityChangedBroadcast$1(int i) {
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        if (service == null) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED");
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.addFlags(67108864);
        try {
            service.broadcastIntentWithFeature((android.app.IApplicationThread) null, (java.lang.String) null, intent, (java.lang.String) null, (android.content.IIntentReceiver) null, 0, (java.lang.String) null, (android.os.Bundle) null, (java.lang.String[]) null, (java.lang.String[]) null, (java.lang.String[]) null, -1, (android.os.Bundle) null, false, false, i);
        } catch (android.os.RemoteException e) {
        }
    }

    void sendPostInstallBroadcasts(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull com.android.server.pm.InstallRequest installRequest, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull java.lang.String str3, @android.annotation.NonNull com.android.server.pm.PackageSender packageSender, boolean z, boolean z2, boolean z3, boolean z4) {
        java.lang.String str4;
        android.os.Bundle bundle;
        java.lang.String str5;
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        boolean z5;
        int i;
        int i2;
        int i3;
        int i4;
        if (installRequest.getRemovedInfo() != null) {
            if (installRequest.getRemovedInfo().mIsExternal) {
                java.lang.String[] strArr2 = {installRequest.getRemovedInfo().mRemovedPackage};
                int[] iArr4 = {installRequest.getRemovedInfo().mUid};
                notifyResourcesChanged(false, true, strArr2, iArr4);
                sendResourcesChangedBroadcast(computer, false, true, strArr2, iArr4);
            }
            sendPackageRemovedBroadcasts(installRequest.getRemovedInfo(), packageSender, z2, false, false);
        }
        int[] firstTimeBroadcastUserIds = installRequest.getFirstTimeBroadcastUserIds();
        int[] firstTimeBroadcastInstantUserIds = installRequest.getFirstTimeBroadcastInstantUserIds();
        int[] updateBroadcastUserIds = installRequest.getUpdateBroadcastUserIds();
        int[] updateBroadcastInstantUserIds = installRequest.getUpdateBroadcastInstantUserIds();
        if (installRequest.getInstallerPackageName() != null) {
            str4 = installRequest.getInstallerPackageName();
        } else if (installRequest.getRemovedInfo() != null) {
            str4 = installRequest.getRemovedInfo().mInstallerPackageName;
        } else {
            str4 = null;
        }
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putInt("android.intent.extra.UID", installRequest.getAppId());
        if (z3) {
            bundle2.putBoolean("android.intent.extra.REPLACING", true);
        }
        if (z4) {
            bundle2.putBoolean("android.intent.extra.ARCHIVAL", true);
        }
        bundle2.putInt("android.content.pm.extra.DATA_LOADER_TYPE", installRequest.getDataLoaderType());
        java.lang.String staticSharedLibraryName = installRequest.getPkg().getStaticSharedLibraryName();
        if (str4 == null || staticSharedLibraryName == null) {
            bundle = bundle2;
            str5 = str4;
            iArr = updateBroadcastUserIds;
            iArr2 = firstTimeBroadcastInstantUserIds;
            iArr3 = firstTimeBroadcastUserIds;
        } else {
            bundle = bundle2;
            str5 = str4;
            iArr = updateBroadcastUserIds;
            iArr2 = firstTimeBroadcastInstantUserIds;
            iArr3 = firstTimeBroadcastUserIds;
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle2, 0, str4, null, installRequest.getNewUsers(), null, null, null);
        }
        if (staticSharedLibraryName == null) {
            int appId = android.os.UserHandle.getAppId(installRequest.getAppId());
            boolean isInstallSystem = installRequest.isInstallSystem();
            boolean z6 = (installRequest.getInstallFlags() & 65536) != 0;
            sendPackageAddedForNewUsers(computer, str, isInstallSystem || z6, z6, appId, iArr3, iArr2, z4, installRequest.getDataLoaderType());
            int[] iArr5 = iArr;
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle, 0, null, null, iArr5, updateBroadcastInstantUserIds, this.mAppsFilter.getVisibilityAllowList(computer, computer.getPackageStateInternal(str, 1000), iArr5, computer.getPackageStates()), null);
            if (str5 != null) {
                sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle, 0, str5, null, iArr5, updateBroadcastInstantUserIds, null, null);
            }
            if (isPrivacySafetyLabelChangeNotificationsEnabled(this.mContext)) {
                sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle, 0, str2, null, iArr5, updateBroadcastInstantUserIds, null, null);
            }
            int length = strArr.length;
            int i5 = 0;
            while (i5 < length) {
                java.lang.String str6 = strArr[i5];
                if (str6 == null || str6.equals(str5)) {
                    i3 = i5;
                    i4 = length;
                } else {
                    i3 = i5;
                    i4 = length;
                    sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle, 0, str6, null, iArr5, updateBroadcastInstantUserIds, null, null);
                }
                i5 = i3 + 1;
                length = i4;
            }
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle, 16777216, str3, null, iArr3, updateBroadcastInstantUserIds, null, null);
            if (z3) {
                sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, null, null, iArr5, updateBroadcastInstantUserIds, installRequest.getRemovedInfo().mBroadcastAllowList, null);
                if (str5 != null) {
                    sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, str5, null, iArr5, updateBroadcastInstantUserIds, null, null);
                }
                int length2 = strArr.length;
                int i6 = 0;
                while (i6 < length2) {
                    java.lang.String str7 = strArr[i6];
                    if (str7 == null || str7.equals(str5)) {
                        i = i6;
                        i2 = length2;
                    } else {
                        i = i6;
                        i2 = length2;
                        sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, str7, null, iArr5, updateBroadcastInstantUserIds, null, null);
                    }
                    i6 = i + 1;
                    length2 = i2;
                }
                sendPackageBroadcastAndNotify("android.intent.action.MY_PACKAGE_REPLACED", null, null, 0, str, null, iArr5, updateBroadcastInstantUserIds, null, getTemporaryAppAllowlistBroadcastOptions(311).toBundle());
            } else if (z && !installRequest.isInstallSystem()) {
                sendFirstLaunchBroadcast(str, str5, iArr3, iArr2);
            }
            if (installRequest.getPkg().isExternalStorage()) {
                if (z3) {
                    z5 = true;
                } else {
                    z5 = true;
                    int packageExternalStorageType = com.android.server.pm.PackageManagerServiceUtils.getPackageExternalStorageType(((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).findVolumeByUuid(android.os.storage.StorageManager.convert(installRequest.getPkg().getVolumeUuid()).toString()), true);
                    if (packageExternalStorageType != 0) {
                        com.android.internal.util.FrameworkStatsLog.write(181, packageExternalStorageType, str);
                    }
                }
                if (!z4) {
                    java.lang.String[] strArr3 = {str};
                    int[] iArr6 = {installRequest.getPkg().getUid()};
                    sendResourcesChangedBroadcast(computer, true, true, strArr3, iArr6);
                    notifyResourcesChanged(z5, z5, strArr3, iArr6);
                    return;
                }
                return;
            }
            return;
        }
        java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> libraryConsumers = installRequest.getLibraryConsumers();
        if (!com.android.internal.util.ArrayUtils.isEmpty(libraryConsumers)) {
            boolean z7 = !z3;
            for (int i7 = 0; i7 < libraryConsumers.size(); i7++) {
                com.android.server.pm.pkg.AndroidPackage androidPackage = libraryConsumers.get(i7);
                sendPackageChangedBroadcast(computer, androidPackage.getPackageName(), z7, new java.util.ArrayList<>(java.util.Collections.singletonList(androidPackage.getPackageName())), androidPackage.getUid(), null);
            }
        }
    }

    private void sendPackageAddedForNewUsers(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull final java.lang.String str, boolean z, final boolean z2, final int i, final int[] iArr, final int[] iArr2, final boolean z3, final int i2) {
        if (com.android.internal.util.ArrayUtils.isEmpty(iArr) && com.android.internal.util.ArrayUtils.isEmpty(iArr2)) {
            return;
        }
        final android.util.SparseArray<int[]> visibilityAllowList = this.mAppsFilter.getVisibilityAllowList(computer, computer.getPackageStateInternal(str, 1000), iArr, computer.getPackageStates());
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BroadcastHelper.this.lambda$sendPackageAddedForNewUsers$2(str, i, iArr, iArr2, z3, i2, visibilityAllowList);
            }
        });
        this.mPackageMonitorCallbackHelper.notifyPackageAddedForNewUsers(str, i, iArr, iArr2, z3, i2, visibilityAllowList, this.mHandler);
        if (z && !com.android.internal.util.ArrayUtils.isEmpty(iArr)) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.BroadcastHelper.this.lambda$sendPackageAddedForNewUsers$3(iArr, str, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPackageAddedForNewUsers$3(int[] iArr, java.lang.String str, boolean z) {
        for (int i : iArr) {
            sendBootCompletedBroadcastToSystemApp(str, z, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendPackageAddedForNewUsers, reason: merged with bridge method [inline-methods] */
    public void lambda$sendPackageAddedForNewUsers$2(@android.annotation.NonNull java.lang.String str, int i, int[] iArr, int[] iArr2, boolean z, int i2, @android.annotation.NonNull android.util.SparseArray<int[]> sparseArray) {
        android.os.Bundle bundle = new android.os.Bundle(1);
        bundle.putInt("android.intent.extra.UID", android.os.UserHandle.getUid(com.android.internal.util.ArrayUtils.isEmpty(iArr) ? iArr2[0] : iArr[0], i));
        if (z) {
            bundle.putBoolean("android.intent.extra.ARCHIVAL", true);
        }
        bundle.putInt("android.content.pm.extra.DATA_LOADER_TYPE", i2);
        sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, null, null, iArr, iArr2, sparseArray, null, null);
        if (isPrivacySafetyLabelChangeNotificationsEnabled(this.mContext)) {
            sendPackageBroadcast("android.intent.action.PACKAGE_ADDED", str, bundle, 0, this.mContext.getPackageManager().getPermissionControllerPackageName(), null, iArr, iArr2, sparseArray, null, null);
        }
    }

    void sendPackageAddedForUser(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, boolean z, int i2, @android.annotation.Nullable java.lang.String str2) {
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        boolean isSystem = packageStateInternal.isSystem();
        boolean isInstantApp = userStateOrDefault.isInstantApp();
        sendPackageAddedForNewUsers(computer, str, isSystem, false, packageStateInternal.getAppId(), isInstantApp ? com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY : new int[]{i}, isInstantApp ? new int[]{i} : com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY, z, i2);
        android.content.pm.PackageInstaller.SessionInfo sessionInfo = new android.content.pm.PackageInstaller.SessionInfo();
        sessionInfo.installReason = userStateOrDefault.getInstallReason();
        sessionInfo.appPackageName = str;
        sendSessionCommitBroadcast(computer, sessionInfo, i, str2);
    }

    void sendFirstLaunchBroadcast(java.lang.String str, java.lang.String str2, int[] iArr, int[] iArr2) {
        sendPackageBroadcast("android.intent.action.PACKAGE_FIRST_LAUNCH", str, null, 0, str2, null, iArr, iArr2, null, null, null);
    }

    @android.annotation.Nullable
    private static android.os.Bundle filterExtrasChangedPackageList(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, @android.annotation.NonNull android.os.Bundle bundle) {
        if (android.os.UserHandle.isCore(i)) {
            return bundle;
        }
        java.lang.String[] stringArray = bundle.getStringArray("android.intent.extra.changed_package_list");
        if (com.android.internal.util.ArrayUtils.isEmpty(stringArray)) {
            return bundle;
        }
        android.util.Pair<java.lang.String[], int[]> filterPackages = filterPackages(computer, stringArray, bundle.getIntArray("android.intent.extra.changed_uid_list"), i, bundle.getInt("android.intent.extra.user_handle", android.os.UserHandle.getUserId(i)));
        if (com.android.internal.util.ArrayUtils.isEmpty((java.lang.String[]) filterPackages.first)) {
            return null;
        }
        android.os.Bundle bundle2 = new android.os.Bundle(bundle);
        bundle2.putStringArray("android.intent.extra.changed_package_list", (java.lang.String[]) filterPackages.first);
        bundle2.putIntArray("android.intent.extra.changed_uid_list", (int[]) filterPackages.second);
        return bundle2;
    }

    private static boolean isPrivacySafetyLabelChangeNotificationsEnabled(android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        return (!android.provider.DeviceConfig.getBoolean("privacy", "safety_label_change_notifications_enabled", true) || packageManager.hasSystemFeature("android.hardware.type.automotive") || packageManager.hasSystemFeature("android.software.leanback") || packageManager.hasSystemFeature("android.hardware.type.watch")) ? false : true;
    }

    @android.annotation.NonNull
    private static android.util.Pair<java.lang.String[], int[]> filterPackages(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable int[] iArr, int i, int i2) {
        int length = strArr.length;
        int length2 = !com.android.internal.util.ArrayUtils.isEmpty(iArr) ? iArr.length : 0;
        java.util.ArrayList arrayList = new java.util.ArrayList(length);
        int[] iArr2 = null;
        android.util.IntArray intArray = length2 > 0 ? new android.util.IntArray(length2) : null;
        for (int i3 = 0; i3 < length; i3++) {
            java.lang.String str = strArr[i3];
            if (!computer.shouldFilterApplication(computer.getPackageStateInternal(str), i, i2)) {
                arrayList.add(str);
                if (intArray != null && i3 < length2) {
                    intArray.add(iArr[i3]);
                }
            }
        }
        java.lang.String[] strArr2 = arrayList.size() > 0 ? (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]) : null;
        if (intArray != null && intArray.size() > 0) {
            iArr2 = intArray.toArray();
        }
        return new android.util.Pair<>(strArr2, iArr2);
    }

    void sendApplicationHiddenForUser(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, @android.annotation.NonNull com.android.server.pm.PackageSender packageSender) {
        com.android.server.pm.PackageRemovedInfo packageRemovedInfo = new com.android.server.pm.PackageRemovedInfo();
        packageRemovedInfo.mRemovedPackage = str;
        packageRemovedInfo.mInstallerPackageName = packageStateInternal.getInstallSource().mInstallerPackageName;
        packageRemovedInfo.mRemovedUsers = new int[]{i};
        packageRemovedInfo.mBroadcastUsers = new int[]{i};
        packageRemovedInfo.mUid = android.os.UserHandle.getUid(i, packageStateInternal.getAppId());
        packageRemovedInfo.mRemovedPackageVersionCode = packageStateInternal.getVersionCode();
        sendPackageRemovedBroadcasts(packageRemovedInfo, packageSender, true, false, false);
    }

    void sendPackageChangedBroadcast(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull final java.lang.String str, final boolean z, @android.annotation.NonNull final java.util.ArrayList<java.lang.String> arrayList, final int i, @android.annotation.NonNull final java.lang.String str2) {
        if (computer.getPackageStateInternal(str, 1000) == null) {
            return;
        }
        int userId = android.os.UserHandle.getUserId(i);
        boolean isInstantAppInternal = computer.isInstantAppInternal(str, userId, 1000);
        final int[] iArr = isInstantAppInternal ? com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY : new int[]{userId};
        int[] iArr2 = isInstantAppInternal ? new int[]{userId} : com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        final android.util.SparseArray<int[]> visibilityAllowLists = isInstantAppInternal ? null : computer.getVisibilityAllowLists(str, iArr);
        final int[] iArr3 = iArr2;
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BroadcastHelper.this.lambda$sendPackageChangedBroadcast$4(str, z, arrayList, i, str2, iArr, iArr3, visibilityAllowLists);
            }
        });
        this.mPackageMonitorCallbackHelper.notifyPackageChanged(str, z, arrayList, i, str2, iArr, iArr2, visibilityAllowLists, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPackageBroadcastAndNotify$5(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, int i, java.lang.String str3, android.content.IIntentReceiver iIntentReceiver, int[] iArr, int[] iArr2, android.util.SparseArray sparseArray, android.os.Bundle bundle2) {
        sendPackageBroadcast(str, str2, bundle, i, str3, iIntentReceiver, iArr, iArr2, sparseArray, null, bundle2);
    }

    private void sendPackageBroadcastAndNotify(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, @android.annotation.NonNull final android.os.Bundle bundle, final int i, @android.annotation.Nullable final java.lang.String str3, @android.annotation.Nullable final android.content.IIntentReceiver iIntentReceiver, @android.annotation.NonNull final int[] iArr, @android.annotation.NonNull final int[] iArr2, @android.annotation.Nullable final android.util.SparseArray<int[]> sparseArray, @android.annotation.Nullable final android.os.Bundle bundle2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BroadcastHelper.this.lambda$sendPackageBroadcastAndNotify$5(str, str2, bundle, i, str3, iIntentReceiver, iArr, iArr2, sparseArray, bundle2);
            }
        });
        if (str3 == null) {
            notifyPackageMonitor(str, str2, bundle, iArr, iArr2, sparseArray, null);
        }
    }

    void sendSystemPackageUpdatedBroadcasts(@android.annotation.NonNull com.android.server.pm.PackageRemovedInfo packageRemovedInfo) {
        if (!packageRemovedInfo.mIsRemovedPackageSystemUpdate) {
            return;
        }
        java.lang.String str = packageRemovedInfo.mRemovedPackage;
        java.lang.String str2 = packageRemovedInfo.mInstallerPackageName;
        android.util.SparseArray<int[]> sparseArray = packageRemovedInfo.mBroadcastAllowList;
        android.os.Bundle bundle = new android.os.Bundle(2);
        bundle.putInt("android.intent.extra.UID", packageRemovedInfo.mUid);
        bundle.putBoolean("android.intent.extra.REPLACING", true);
        sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle, 0, null, null, null, null, sparseArray, null);
        if (str2 != null) {
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_ADDED", str, bundle, 0, str2, null, null, null, null, null);
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, str2, null, null, null, null, null);
        }
        sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REPLACED", str, bundle, 0, null, null, null, null, sparseArray, null);
        sendPackageBroadcastAndNotify("android.intent.action.MY_PACKAGE_REPLACED", null, null, 0, str, null, null, null, null, getTemporaryBroadcastOptionsForSystemPackageUpdate(311).toBundle());
    }

    @android.annotation.NonNull
    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    private android.app.BroadcastOptions getTemporaryBroadcastOptionsForSystemPackageUpdate(int i) {
        long j;
        if (this.mAmInternal == null) {
            j = 10000;
        } else {
            j = this.mAmInternal.getBootTimeTempAllowListDuration();
        }
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(j, 0, i, "");
        return makeBasic;
    }

    void sendPackageRemovedBroadcasts(@android.annotation.NonNull com.android.server.pm.PackageRemovedInfo packageRemovedInfo, @android.annotation.NonNull com.android.server.pm.PackageSender packageSender, boolean z, boolean z2, boolean z3) {
        android.os.Bundle bundle;
        boolean z4;
        boolean z5;
        boolean z6;
        android.os.Bundle bundle2;
        java.lang.String str = packageRemovedInfo.mRemovedPackage;
        java.lang.String str2 = packageRemovedInfo.mInstallerPackageName;
        int[] iArr = packageRemovedInfo.mBroadcastUsers;
        int[] iArr2 = packageRemovedInfo.mInstantUserIds;
        android.util.SparseArray<int[]> sparseArray = packageRemovedInfo.mBroadcastAllowList;
        boolean z7 = packageRemovedInfo.mDataRemoved;
        boolean z8 = packageRemovedInfo.mIsUpdate;
        boolean z9 = packageRemovedInfo.mIsRemovedPackageSystemUpdate;
        boolean z10 = packageRemovedInfo.mRemovedForAllUsers;
        boolean z11 = packageRemovedInfo.mIsStaticSharedLib;
        android.os.Bundle bundle3 = new android.os.Bundle();
        bundle3.putInt("android.intent.extra.UID", packageRemovedInfo.mUid);
        bundle3.putBoolean("android.intent.extra.DATA_REMOVED", z7);
        bundle3.putBoolean("android.intent.extra.SYSTEM_UPDATE_UNINSTALL", z9);
        bundle3.putBoolean("android.intent.extra.DONT_KILL_APP", !z);
        bundle3.putBoolean("android.intent.extra.USER_INITIATED", !z2);
        boolean z12 = z8 || z9;
        if (z12 || z3) {
            bundle3.putBoolean("android.intent.extra.REPLACING", true);
        }
        if (z3) {
            bundle3.putBoolean("android.intent.extra.ARCHIVAL", true);
        }
        bundle3.putBoolean("android.intent.extra.REMOVED_FOR_ALL_USERS", z10);
        if (str == null || str2 == null) {
            bundle = bundle3;
            z4 = z11;
            z5 = z9;
            z6 = z7;
        } else {
            bundle = bundle3;
            z4 = z11;
            z5 = z9;
            z6 = z7;
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REMOVED", str, bundle3, 0, str2, null, iArr, iArr2, null, null);
        }
        if (z4) {
            return;
        }
        if (str != null) {
            android.os.Bundle bundle4 = bundle;
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REMOVED", str, bundle4, 0, null, null, iArr, iArr2, sparseArray, null);
            sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_REMOVED_INTERNAL", str, bundle4, 0, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, null, iArr, iArr2, sparseArray, null);
            if (z6 && !z5) {
                sendPackageBroadcastAndNotify("android.intent.action.PACKAGE_FULLY_REMOVED", str, bundle, 16777216, null, null, iArr, iArr2, sparseArray, null);
                packageSender.notifyPackageRemoved(str, packageRemovedInfo.mUid);
            }
        }
        if (packageRemovedInfo.mIsAppIdRemoved) {
            if (!z12) {
                bundle2 = bundle;
            } else {
                bundle2 = bundle;
                bundle2.putString("android.intent.extra.PACKAGE_NAME", str);
            }
            sendPackageBroadcastAndNotify("android.intent.action.UID_REMOVED", null, bundle2, 16777216, null, null, iArr, iArr2, sparseArray, null);
        }
    }

    void sendPackagesSuspendedOrUnsuspendedForUser(@android.annotation.NonNull final com.android.server.pm.Computer computer, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull int[] iArr, boolean z, final int i) {
        final android.os.Bundle bundle = new android.os.Bundle(3);
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        if (z) {
            bundle.putBoolean("android.intent.extra.quarantined", true);
        }
        final android.os.Bundle bundle2 = new android.app.BroadcastOptions().setDeferralPolicy(2).toBundle();
        final java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction = new java.util.function.BiFunction() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda7
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                android.os.Bundle lambda$sendPackagesSuspendedOrUnsuspendedForUser$6;
                lambda$sendPackagesSuspendedOrUnsuspendedForUser$6 = com.android.server.pm.BroadcastHelper.lambda$sendPackagesSuspendedOrUnsuspendedForUser$6(com.android.server.pm.Computer.this, (java.lang.Integer) obj, (android.os.Bundle) obj2);
                return lambda$sendPackagesSuspendedOrUnsuspendedForUser$6;
            }
        };
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BroadcastHelper.this.lambda$sendPackagesSuspendedOrUnsuspendedForUser$7(str, bundle, i, biFunction, bundle2);
            }
        });
        notifyPackageMonitor(str, null, bundle, new int[]{i}, null, null, biFunction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.Bundle lambda$sendPackagesSuspendedOrUnsuspendedForUser$6(com.android.server.pm.Computer computer, java.lang.Integer num, android.os.Bundle bundle) {
        return filterExtrasChangedPackageList(computer, num.intValue(), bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPackagesSuspendedOrUnsuspendedForUser$7(java.lang.String str, android.os.Bundle bundle, int i, java.util.function.BiFunction biFunction, android.os.Bundle bundle2) {
        sendPackageBroadcast(str, null, bundle, com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE, null, null, new int[]{i}, null, null, biFunction, bundle2);
    }

    void sendMyPackageSuspendedOrUnsuspended(@android.annotation.NonNull final com.android.server.pm.Computer computer, @android.annotation.NonNull final java.lang.String[] strArr, final boolean z, final int i) {
        final java.lang.String str;
        if (z) {
            str = "android.intent.action.MY_PACKAGE_SUSPENDED";
        } else {
            str = "android.intent.action.MY_PACKAGE_UNSUSPENDED";
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BroadcastHelper.this.lambda$sendMyPackageSuspendedOrUnsuspended$8(z, i, strArr, computer, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendMyPackageSuspendedOrUnsuspended$8(boolean z, int i, java.lang.String[] strArr, com.android.server.pm.Computer computer, java.lang.String str) {
        android.os.Bundle bundle;
        if (android.app.ActivityManager.getService() == null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("IActivityManager null. Cannot send MY_PACKAGE_ ");
            sb.append(z ? "" : "UN");
            sb.append("SUSPENDED broadcasts");
            android.util.Slog.wtf("PackageManager", sb.toString());
            return;
        }
        int[] iArr = {i};
        for (java.lang.String str2 : strArr) {
            android.os.Bundle bundle2 = null;
            if (!z) {
                bundle = null;
            } else {
                bundle = com.android.server.pm.SuspendPackageHelper.getSuspendedPackageAppExtras(computer, str2, i, 1000);
            }
            if (bundle != null) {
                bundle2 = new android.os.Bundle(1);
                bundle2.putBundle("android.intent.extra.SUSPENDED_PACKAGE_EXTRAS", bundle);
            }
            doSendBroadcast(str, null, bundle2, 16777216, str2, null, iArr, false, null, null, null);
        }
    }

    void sendDistractingPackagesChanged(@android.annotation.NonNull final com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull int[] iArr, final int i, int i2) {
        final android.os.Bundle bundle = new android.os.Bundle();
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        bundle.putInt("android.intent.extra.distraction_restrictions", i2);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.BroadcastHelper.this.lambda$sendDistractingPackagesChanged$10(bundle, i, computer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendDistractingPackagesChanged$10(android.os.Bundle bundle, int i, final com.android.server.pm.Computer computer) {
        sendPackageBroadcast("android.intent.action.DISTRACTING_PACKAGES_CHANGED", null, bundle, 1073741824, null, null, new int[]{i}, null, null, new java.util.function.BiFunction() { // from class: com.android.server.pm.BroadcastHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                android.os.Bundle lambda$sendDistractingPackagesChanged$9;
                lambda$sendDistractingPackagesChanged$9 = com.android.server.pm.BroadcastHelper.lambda$sendDistractingPackagesChanged$9(com.android.server.pm.Computer.this, (java.lang.Integer) obj, (android.os.Bundle) obj2);
                return lambda$sendDistractingPackagesChanged$9;
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.Bundle lambda$sendDistractingPackagesChanged$9(com.android.server.pm.Computer computer, java.lang.Integer num, android.os.Bundle bundle) {
        return filterExtrasChangedPackageList(computer, num.intValue(), bundle);
    }

    void sendResourcesChangedBroadcastAndNotify(@android.annotation.NonNull com.android.server.pm.Computer computer, boolean z, boolean z2, @android.annotation.NonNull java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> arrayList) {
        int size = arrayList.size();
        java.lang.String[] strArr = new java.lang.String[size];
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            com.android.server.pm.pkg.AndroidPackage androidPackage = arrayList.get(i);
            strArr[i] = androidPackage.getPackageName();
            iArr[i] = androidPackage.getUid();
        }
        sendResourcesChangedBroadcast(computer, z, z2, strArr, iArr);
        notifyResourcesChanged(z, z2, strArr, iArr);
    }

    private void notifyPackageMonitor(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull int[] iArr2, @android.annotation.Nullable android.util.SparseArray<int[]> sparseArray, @android.annotation.Nullable java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction) {
        this.mPackageMonitorCallbackHelper.notifyPackageMonitor(str, str2, bundle, iArr, iArr2, sparseArray, this.mHandler, biFunction);
    }

    private void notifyResourcesChanged(boolean z, boolean z2, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull int[] iArr) {
        this.mPackageMonitorCallbackHelper.notifyResourcesChanged(z, z2, strArr, iArr, this.mHandler);
    }
}
