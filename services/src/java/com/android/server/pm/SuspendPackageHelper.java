package com.android.server.pm;

/* loaded from: classes2.dex */
public final class SuspendPackageHelper {
    private static final java.lang.String SYSTEM_EXEMPT_FROM_SUSPENSION = "system_exempt_from_suspension";
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private final com.android.server.pm.PackageManagerServiceInjector mInjector;
    private final com.android.server.pm.PackageManagerService mPm;
    private final com.android.server.pm.ProtectedPackages mProtectedPackages;

    SuspendPackageHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.BroadcastHelper broadcastHelper, com.android.server.pm.ProtectedPackages protectedPackages) {
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mBroadcastHelper = broadcastHelper;
        this.mProtectedPackages = protectedPackages;
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x012d, code lost:
    
        if (r1.containsKey(r24) != false) goto L58;
     */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    java.lang.String[] setPackagesSuspended(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.Nullable java.lang.String[] strArr, final boolean z, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.Nullable android.os.PersistableBundle persistableBundle2, @android.annotation.Nullable android.content.pm.SuspendDialogInfo suspendDialogInfo, @android.annotation.NonNull final android.content.pm.UserPackage userPackage, final int i, int i2, boolean z2) {
        boolean[] zArr;
        android.util.IntArray intArray;
        android.util.ArraySet arraySet;
        java.util.ArrayList arrayList;
        int i3;
        com.android.server.pm.pkg.SuspendParams suspendParams;
        boolean[] zArr2;
        android.util.IntArray intArray2;
        com.android.server.pm.Computer computer2 = computer;
        java.lang.String[] strArr2 = strArr;
        int i4 = i2;
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            return strArr2;
        }
        if (z && !z2 && !isSuspendAllowedForUser(computer2, i, i4)) {
            android.util.Slog.w("PackageManager", "Cannot suspend due to restrictions on user " + i);
            return strArr2;
        }
        final com.android.server.pm.pkg.SuspendParams suspendParams2 = z ? new com.android.server.pm.pkg.SuspendParams(suspendDialogInfo, persistableBundle, persistableBundle2, z2) : null;
        java.util.ArrayList arrayList2 = new java.util.ArrayList(strArr2.length);
        java.util.ArrayList arrayList3 = new java.util.ArrayList(strArr2.length);
        android.util.IntArray intArray3 = new android.util.IntArray(strArr2.length);
        final android.util.ArraySet arraySet2 = new android.util.ArraySet(strArr2.length);
        android.util.IntArray intArray4 = new android.util.IntArray(strArr2.length);
        if (z) {
            zArr = canSuspendPackageForUser(computer2, strArr2, i, i4);
        } else {
            zArr = null;
        }
        int i5 = 0;
        while (i5 < strArr2.length) {
            java.lang.String str = strArr2[i5];
            if (!userPackage.packageName.equals(str) || userPackage.userId != i) {
                android.util.IntArray intArray5 = intArray4;
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer2.getPackageStateInternal(str);
                if (packageStateInternal == null) {
                    suspendParams = suspendParams2;
                    zArr2 = zArr;
                    intArray2 = intArray5;
                } else if (!packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                    suspendParams = suspendParams2;
                    zArr2 = zArr;
                    intArray2 = intArray5;
                } else if (computer2.shouldFilterApplication(packageStateInternal, i4, i)) {
                    suspendParams = suspendParams2;
                    zArr2 = zArr;
                    intArray2 = intArray5;
                } else if (zArr != null && !zArr[i5]) {
                    arrayList2.add(str);
                    suspendParams = suspendParams2;
                    zArr2 = zArr;
                    intArray2 = intArray5;
                } else {
                    com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> suspendParams3 = packageStateInternal.getUserStateOrDefault(i).getSuspendParams();
                    zArr2 = zArr;
                    boolean z3 = true;
                    boolean z4 = !java.util.Objects.equals(suspendParams3 == null ? null : suspendParams3.get(userPackage), suspendParams2);
                    if (z && !z4) {
                        arrayList3.add(str);
                        intArray3.add(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
                        intArray2 = intArray5;
                        suspendParams = suspendParams2;
                    } else {
                        if (z) {
                            suspendParams = suspendParams2;
                        } else {
                            suspendParams = suspendParams2;
                            if (com.android.internal.util.CollectionUtils.size(suspendParams3) == 1) {
                            }
                        }
                        z3 = false;
                        if (z || z3) {
                            arrayList3.add(str);
                            intArray3.add(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
                        }
                        if (z4) {
                            arraySet2.add(str);
                            intArray2 = intArray5;
                            intArray2.add(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
                        } else {
                            intArray2 = intArray5;
                            android.util.Slog.w("PackageManager", "No change is needed for package: " + str + ". Skipping suspending/un-suspending.");
                        }
                    }
                }
                android.util.Slog.w("PackageManager", "Could not find package setting for package: " + str + ". Skipping suspending/un-suspending.");
                arrayList2.add(str);
            } else {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                android.util.IntArray intArray6 = intArray4;
                sb.append("Suspending package: ");
                sb.append(userPackage);
                sb.append(" trying to ");
                sb.append(z ? "" : "un");
                sb.append("suspend itself. Ignoring");
                android.util.Slog.w("PackageManager", sb.toString());
                arrayList2.add(str);
                suspendParams = suspendParams2;
                zArr2 = zArr;
                intArray2 = intArray6;
            }
            i5++;
            computer2 = computer;
            strArr2 = strArr;
            i4 = i2;
            intArray4 = intArray2;
            suspendParams2 = suspendParams;
            zArr = zArr2;
        }
        android.util.IntArray intArray7 = intArray4;
        this.mPm.commitPackageStateMutation(null, new java.util.function.Consumer() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.SuspendPackageHelper.lambda$setPackagesSuspended$0(arraySet2, i, z, userPackage, suspendParams2, (com.android.server.pm.pkg.mutate.PackageStateMutator) obj);
            }
        });
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        if (arrayList3.isEmpty()) {
            intArray = intArray7;
            arraySet = arraySet2;
            arrayList = arrayList2;
            i3 = 0;
        } else {
            i3 = 0;
            java.lang.String[] strArr3 = (java.lang.String[]) arrayList3.toArray(new java.lang.String[0]);
            com.android.server.pm.BroadcastHelper broadcastHelper = this.mBroadcastHelper;
            java.lang.String str2 = z ? "android.intent.action.PACKAGES_SUSPENDED" : "android.intent.action.PACKAGES_UNSUSPENDED";
            int[] array = intArray3.toArray();
            java.lang.String str3 = str2;
            intArray = intArray7;
            arraySet = arraySet2;
            arrayList = arrayList2;
            broadcastHelper.sendPackagesSuspendedOrUnsuspendedForUser(snapshotComputer, str3, strArr3, array, z2, i);
            this.mBroadcastHelper.sendMyPackageSuspendedOrUnsuspended(snapshotComputer, strArr3, z, i);
            this.mPm.scheduleWritePackageRestrictions(i);
        }
        if (!arraySet.isEmpty()) {
            this.mBroadcastHelper.sendPackagesSuspendedOrUnsuspendedForUser(snapshotComputer, "android.intent.action.PACKAGES_SUSPENSION_CHANGED", (java.lang.String[]) arraySet.toArray(new java.lang.String[i3]), intArray.toArray(), z2, i);
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[i3]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setPackagesSuspended$0(android.util.ArraySet arraySet, int i, boolean z, android.content.pm.UserPackage userPackage, com.android.server.pm.pkg.SuspendParams suspendParams, com.android.server.pm.pkg.mutate.PackageStateMutator packageStateMutator) {
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.pkg.mutate.PackageUserStateWrite userState = packageStateMutator.forPackage((java.lang.String) arraySet.valueAt(i2)).userState(i);
            if (z) {
                userState.putSuspendParams(userPackage, suspendParams);
            } else {
                userState.removeSuspension(userPackage);
            }
        }
    }

    @android.annotation.NonNull
    java.lang.String[] getUnsuspendablePackagesForUser(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String[] strArr, int i, int i2) {
        if (!isSuspendAllowedForUser(computer, i, i2)) {
            android.util.Slog.w("PackageManager", "Cannot suspend due to restrictions on user " + i);
            return strArr;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        boolean[] canSuspendPackageForUser = canSuspendPackageForUser(computer, strArr, i, i2);
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if (!canSuspendPackageForUser[i3]) {
                arraySet.add(strArr[i3]);
            } else if (computer.getPackageStateForInstalledAndFiltered(strArr[i3], i2, i) == null) {
                android.util.Slog.w("PackageManager", "Could not find package setting for package: " + strArr[i3]);
                arraySet.add(strArr[i3]);
            }
        }
        return (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
    }

    @android.annotation.Nullable
    static android.os.Bundle getSuspendedPackageAppExtras(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        if (packageStateInternal == null) {
            return null;
        }
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        android.os.Bundle bundle = new android.os.Bundle();
        if (userStateOrDefault.isSuspended()) {
            for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().size(); i3++) {
                com.android.server.pm.pkg.SuspendParams valueAt = userStateOrDefault.getSuspendParams().valueAt(i3);
                if (valueAt != null && valueAt.getAppExtras() != null) {
                    bundle.putAll(valueAt.getAppExtras());
                }
            }
        }
        if (bundle.size() > 0) {
            return bundle;
        }
        return null;
    }

    void removeSuspensionsBySuspendingPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull java.util.function.Predicate<android.content.pm.UserPackage> predicate, final int i) {
        android.util.ArraySet arraySet;
        java.lang.String[] strArr2 = strArr;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.IntArray intArray = new android.util.IntArray();
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int length = strArr2.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            java.lang.String str = strArr2[i2];
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal != null ? packageStateInternal.getUserStateOrDefault(i) : null;
            if (userStateOrDefault != null && userStateOrDefault.isSuspended()) {
                com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> suspendParams = userStateOrDefault.getSuspendParams();
                int i3 = 0;
                for (int i4 = 0; i4 < suspendParams.size(); i4++) {
                    android.content.pm.UserPackage keyAt = suspendParams.keyAt(i4);
                    if (predicate.test(keyAt)) {
                        android.util.ArraySet arraySet2 = (android.util.ArraySet) arrayMap.get(str);
                        if (arraySet2 != null) {
                            arraySet = arraySet2;
                        } else {
                            arraySet = new android.util.ArraySet();
                            arrayMap.put(str, arraySet);
                        }
                        arraySet.add(keyAt);
                        i3++;
                    }
                }
                if (i3 == suspendParams.size()) {
                    arrayList.add(packageStateInternal.getPackageName());
                    intArray.add(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
                }
            }
            i2++;
            strArr2 = strArr;
        }
        this.mPm.commitPackageStateMutation(null, new java.util.function.Consumer() { // from class: com.android.server.pm.SuspendPackageHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.SuspendPackageHelper.lambda$removeSuspensionsBySuspendingPackage$1(arrayMap, i, (com.android.server.pm.pkg.mutate.PackageStateMutator) obj);
            }
        });
        this.mPm.scheduleWritePackageRestrictions(i);
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        if (!arrayList.isEmpty()) {
            java.lang.String[] strArr3 = (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
            this.mBroadcastHelper.sendMyPackageSuspendedOrUnsuspended(snapshotComputer, strArr3, false, i);
            this.mBroadcastHelper.sendPackagesSuspendedOrUnsuspendedForUser(snapshotComputer, "android.intent.action.PACKAGES_UNSUSPENDED", strArr3, intArray.toArray(), false, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeSuspensionsBySuspendingPackage$1(android.util.ArrayMap arrayMap, int i, com.android.server.pm.pkg.mutate.PackageStateMutator packageStateMutator) {
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            java.lang.String str = (java.lang.String) arrayMap.keyAt(i2);
            android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.valueAt(i2);
            com.android.server.pm.pkg.mutate.PackageUserStateWrite userState = packageStateMutator.forPackage(str).userState(i);
            for (int i3 = 0; i3 < arraySet.size(); i3++) {
                userState.removeSuspension((android.content.pm.UserPackage) arraySet.valueAt(i3));
            }
        }
    }

    @android.annotation.Nullable
    android.os.Bundle getSuspendedPackageLauncherExtras(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        if (packageStateInternal == null) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (userStateOrDefault.isSuspended()) {
            for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().size(); i3++) {
                com.android.server.pm.pkg.SuspendParams valueAt = userStateOrDefault.getSuspendParams().valueAt(i3);
                if (valueAt != null && valueAt.getLauncherExtras() != null) {
                    bundle.putAll(valueAt.getLauncherExtras());
                }
            }
        }
        if (bundle.size() > 0) {
            return bundle;
        }
        return null;
    }

    boolean isPackageSuspended(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        return packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).isSuspended();
    }

    @android.annotation.Nullable
    android.content.pm.UserPackage getSuspendingPackage(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        android.content.pm.UserPackage userPackage = null;
        if (packageStateInternal == null) {
            return null;
        }
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!userStateOrDefault.isSuspended()) {
            return null;
        }
        android.content.pm.UserPackage userPackage2 = null;
        android.content.pm.UserPackage userPackage3 = null;
        for (int i3 = 0; i3 < userStateOrDefault.getSuspendParams().size(); i3++) {
            userPackage2 = userStateOrDefault.getSuspendParams().keyAt(i3);
            com.android.server.pm.pkg.SuspendParams valueAt = userStateOrDefault.getSuspendParams().valueAt(i3);
            if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(userPackage2.packageName)) {
                userPackage3 = userPackage2;
            }
            if (valueAt.isQuarantined() && userPackage == null) {
                userPackage = userPackage2;
            }
        }
        if (userPackage != null) {
            return userPackage;
        }
        if (userPackage3 != null) {
            return userPackage3;
        }
        return userPackage2;
    }

    @android.annotation.Nullable
    android.content.pm.SuspendDialogInfo getSuspendedDialogInfo(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.UserPackage userPackage, int i, int i2) {
        com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> suspendParams;
        com.android.server.pm.pkg.SuspendParams suspendParams2;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str, i2);
        if (packageStateInternal == null) {
            return null;
        }
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!userStateOrDefault.isSuspended() || (suspendParams = userStateOrDefault.getSuspendParams()) == null || (suspendParams2 = suspendParams.get(userPackage)) == null) {
            return null;
        }
        return suspendParams2.getDialogInfo();
    }

    boolean isSuspendAllowedForUser(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, int i2) {
        com.android.server.pm.UserManagerService userManagerService = this.mInjector.getUserManagerService();
        return isCallerDeviceOrProfileOwner(computer, i, i2) || !(userManagerService.hasUserRestriction("no_control_apps", i) || userManagerService.hasUserRestriction("no_uninstall_apps", i));
    }

    @android.annotation.NonNull
    boolean[] canSuspendPackageForUser(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String[] strArr, int i, int i2) {
        long j;
        com.android.server.pm.Computer computer2 = computer;
        java.lang.String[] strArr2 = strArr;
        boolean[] zArr = new boolean[strArr2.length];
        boolean isCallerDeviceOrProfileOwner = isCallerDeviceOrProfileOwner(computer2, i, i2);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.pm.DefaultAppProvider defaultAppProvider = this.mInjector.getDefaultAppProvider();
            java.lang.String defaultHome = defaultAppProvider.getDefaultHome(i);
            java.lang.String defaultDialer = defaultAppProvider.getDefaultDialer(i);
            java.lang.String knownPackageName = getKnownPackageName(computer2, 2, i);
            java.lang.String knownPackageName2 = getKnownPackageName(computer2, 3, i);
            java.lang.String knownPackageName3 = getKnownPackageName(computer2, 4, i);
            java.lang.String knownPackageName4 = getKnownPackageName(computer2, 7, i);
            int i3 = 0;
            while (i3 < strArr2.length) {
                zArr[i3] = false;
                java.lang.String str = strArr2[i3];
                j = clearCallingIdentity;
                if (this.mPm.isPackageDeviceAdmin(str, i)) {
                    try {
                        android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": has an active device admin");
                    } catch (java.lang.Throwable th) {
                        th = th;
                        android.os.Binder.restoreCallingIdentity(j);
                        throw th;
                    }
                } else if (str.equals(defaultHome)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": contains the active launcher");
                } else if (str.equals(knownPackageName)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for package installation");
                } else if (str.equals(knownPackageName2)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for package uninstallation");
                } else if (str.equals(knownPackageName3)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for package verification");
                } else if (str.equals(defaultDialer)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": is the default dialer");
                } else if (str.equals(knownPackageName4)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": required for permissions management");
                } else if (this.mProtectedPackages.isPackageStateProtected(i, str)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": protected package");
                } else if (!isCallerDeviceOrProfileOwner && computer2.getBlockUninstall(i, str)) {
                    android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": blocked by admin");
                } else {
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer2.getPackageStateInternal(str);
                    com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
                    if (pkg != null) {
                        int uid = android.os.UserHandle.getUid(i, packageStateInternal.getAppId());
                        if (pkg.isSdkLibrary()) {
                            android.util.Slog.w("PackageManager", "Cannot suspend package: " + str + " providing SDK library: " + pkg.getSdkLibraryName());
                        } else if (pkg.isStaticSharedLibrary()) {
                            android.util.Slog.w("PackageManager", "Cannot suspend package: " + str + " providing static shared library: " + pkg.getStaticSharedLibraryName());
                        } else if (exemptFromSuspensionByAppOp(uid, str)) {
                            android.util.Slog.w("PackageManager", "Cannot suspend package \"" + str + "\": has OP_SYSTEM_EXEMPT_FROM_SUSPENSION set");
                        }
                    }
                    if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
                        android.util.Slog.w("PackageManager", "Cannot suspend the platform package: " + str);
                    } else {
                        zArr[i3] = true;
                    }
                }
                i3++;
                computer2 = computer;
                strArr2 = strArr;
                clearCallingIdentity = j;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return zArr;
        } catch (java.lang.Throwable th2) {
            th = th2;
            j = clearCallingIdentity;
        }
    }

    private boolean exemptFromSuspensionByAppOp(int i, java.lang.String str) {
        return ((android.app.AppOpsManager) this.mInjector.getSystemService(android.app.AppOpsManager.class)).checkOpNoThrow(124, i, str) == 0;
    }

    private java.lang.String getKnownPackageName(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, int i2) {
        java.lang.String[] knownPackageNamesInternal = this.mPm.getKnownPackageNamesInternal(computer, i, i2);
        if (knownPackageNamesInternal.length > 0) {
            return knownPackageNamesInternal[0];
        }
        return null;
    }

    private boolean isCallerDeviceOrProfileOwner(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, int i2) {
        if (i2 == 1000) {
            return true;
        }
        java.lang.String deviceOwnerOrProfileOwnerPackage = this.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(i);
        return deviceOwnerOrProfileOwnerPackage != null && i2 == computer.getPackageUidInternal(deviceOwnerOrProfileOwnerPackage, 0L, i, i2);
    }
}
