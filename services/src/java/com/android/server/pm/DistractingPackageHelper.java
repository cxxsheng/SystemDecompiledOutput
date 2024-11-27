package com.android.server.pm;

/* loaded from: classes2.dex */
public final class DistractingPackageHelper {
    private final com.android.server.pm.BroadcastHelper mBroadcastHelper;
    private final com.android.server.pm.PackageManagerService mPm;
    private final com.android.server.pm.SuspendPackageHelper mSuspendPackageHelper;

    DistractingPackageHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.BroadcastHelper broadcastHelper, com.android.server.pm.SuspendPackageHelper suspendPackageHelper) {
        this.mPm = packageManagerService;
        this.mBroadcastHelper = broadcastHelper;
        this.mSuspendPackageHelper = suspendPackageHelper;
    }

    java.lang.String[] setDistractingPackageRestrictionsAsUser(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String[] strArr, final int i, final int i2, int i3) {
        boolean[] zArr;
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            return strArr;
        }
        if (i != 0 && !this.mSuspendPackageHelper.isSuspendAllowedForUser(computer, i2, i3)) {
            android.util.Slog.w("PackageManager", "Cannot restrict packages due to restrictions on user " + i2);
            return strArr;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(strArr.length);
        android.util.IntArray intArray = new android.util.IntArray(strArr.length);
        java.util.ArrayList arrayList2 = new java.util.ArrayList(strArr.length);
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        if (i != 0) {
            zArr = this.mSuspendPackageHelper.canSuspendPackageForUser(computer, strArr, i2, i3);
        } else {
            zArr = null;
        }
        for (int i4 = 0; i4 < strArr.length; i4++) {
            java.lang.String str = strArr[i4];
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(str, i3, i2);
            if (packageStateForInstalledAndFiltered == null) {
                android.util.Slog.w("PackageManager", "Could not find package setting for package: " + str + ". Skipping...");
                arrayList2.add(str);
            } else if (zArr != null && !zArr[i4]) {
                arrayList2.add(str);
            } else if (i != packageStateForInstalledAndFiltered.getUserStateOrDefault(i2).getDistractionFlags()) {
                arrayList.add(str);
                intArray.add(android.os.UserHandle.getUid(i2, packageStateForInstalledAndFiltered.getAppId()));
                arraySet.add(str);
            }
        }
        this.mPm.commitPackageStateMutation(null, new java.util.function.Consumer() { // from class: com.android.server.pm.DistractingPackageHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.DistractingPackageHelper.lambda$setDistractingPackageRestrictionsAsUser$0(arraySet, i2, i, (com.android.server.pm.pkg.mutate.PackageStateMutator) obj);
            }
        });
        if (!arrayList.isEmpty()) {
            this.mBroadcastHelper.sendDistractingPackagesChanged(this.mPm.snapshotComputer(), (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]), intArray.toArray(), i2, i);
            this.mPm.scheduleWritePackageRestrictions(i2);
        }
        return (java.lang.String[]) arrayList2.toArray(new java.lang.String[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setDistractingPackageRestrictionsAsUser$0(android.util.ArraySet arraySet, int i, int i2, com.android.server.pm.pkg.mutate.PackageStateMutator packageStateMutator) {
        int size = arraySet.size();
        for (int i3 = 0; i3 < size; i3++) {
            packageStateMutator.forPackage((java.lang.String) arraySet.valueAt(i3)).userState(i).setDistractionFlags(i2);
        }
    }

    int[] getDistractingPackageRestrictionsAsUser(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String[] strArr, int i, int i2) {
        int[] iArr = new int[strArr.length];
        java.util.Arrays.fill(iArr, -1);
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            return iArr;
        }
        for (int i3 = 0; i3 < strArr.length; i3++) {
            com.android.server.pm.pkg.PackageStateInternal packageStateForInstalledAndFiltered = computer.getPackageStateForInstalledAndFiltered(strArr[i3], i2, i);
            if (packageStateForInstalledAndFiltered != null) {
                iArr[i3] = packageStateForInstalledAndFiltered.getUserStateOrDefault(i).getDistractionFlags();
            }
        }
        return iArr;
    }

    void removeDistractingPackageRestrictions(@android.annotation.NonNull com.android.server.pm.Computer computer, java.lang.String[] strArr, final int i) {
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            return;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList(strArr.length);
        android.util.IntArray intArray = new android.util.IntArray(strArr.length);
        for (java.lang.String str : strArr) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
            if (packageStateInternal != null && packageStateInternal.getUserStateOrDefault(i).getDistractionFlags() != 0) {
                arrayList.add(packageStateInternal.getPackageName());
                intArray.add(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
            }
        }
        this.mPm.commitPackageStateMutation(null, new java.util.function.Consumer() { // from class: com.android.server.pm.DistractingPackageHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.DistractingPackageHelper.lambda$removeDistractingPackageRestrictions$1(arrayList, i, (com.android.server.pm.pkg.mutate.PackageStateMutator) obj);
            }
        });
        if (!arrayList.isEmpty()) {
            this.mBroadcastHelper.sendDistractingPackagesChanged(this.mPm.snapshotComputer(), (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]), intArray.toArray(), i, 0);
            this.mPm.scheduleWritePackageRestrictions(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeDistractingPackageRestrictions$1(java.util.List list, int i, com.android.server.pm.pkg.mutate.PackageStateMutator packageStateMutator) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            packageStateMutator.forPackage((java.lang.String) list.get(i2)).userState(i).setDistractionFlags(0);
        }
    }
}
