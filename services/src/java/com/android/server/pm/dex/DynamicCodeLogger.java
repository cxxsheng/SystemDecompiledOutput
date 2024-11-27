package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public class DynamicCodeLogger {
    private static final java.lang.String DCL_DEX_SUBTAG = "dcl";
    private static final java.lang.String DCL_NATIVE_SUBTAG = "dcln";
    private static final int SNET_TAG = 1397638484;
    private static final java.lang.String TAG = "DynamicCodeLogger";
    private final com.android.server.pm.Installer mInstaller;
    private final com.android.server.pm.dex.PackageDynamicCodeLoading mPackageDynamicCodeLoading;
    private android.content.pm.IPackageManager mPackageManager;

    public DynamicCodeLogger(com.android.server.pm.Installer installer) {
        this.mInstaller = installer;
        this.mPackageDynamicCodeLoading = new com.android.server.pm.dex.PackageDynamicCodeLoading();
    }

    @com.android.internal.annotations.VisibleForTesting
    DynamicCodeLogger(@android.annotation.NonNull android.content.pm.IPackageManager iPackageManager, @android.annotation.NonNull com.android.server.pm.Installer installer, @android.annotation.NonNull com.android.server.pm.dex.PackageDynamicCodeLoading packageDynamicCodeLoading) {
        this.mPackageManager = iPackageManager;
        this.mInstaller = installer;
        this.mPackageDynamicCodeLoading = packageDynamicCodeLoading;
    }

    @android.annotation.NonNull
    private android.content.pm.IPackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService(com.android.server.pm.Settings.ATTR_PACKAGE));
        }
        return this.mPackageManager;
    }

    public java.util.Set<java.lang.String> getAllPackagesWithDynamicCodeLoading() {
        return this.mPackageDynamicCodeLoading.getAllPackagesWithDynamicCodeLoading();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0174 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0148 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void logDynamicCodeLoading(java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo;
        android.content.pm.ApplicationInfo applicationInfo2;
        boolean z;
        int i;
        android.content.pm.ApplicationInfo applicationInfo3;
        java.lang.String str2;
        byte[] bArr;
        java.lang.String str3;
        int i2;
        com.android.server.pm.Installer installer;
        int i3;
        java.lang.String str4;
        com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode packageDynamicCodeInfo = getPackageDynamicCodeInfo(str);
        if (packageDynamicCodeInfo == null) {
            return;
        }
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        boolean z2 = false;
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile> entry : packageDynamicCodeInfo.mFileUsageMap.entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.pm.dex.PackageDynamicCodeLoading.DynamicCodeFile value = entry.getValue();
            int i4 = value.mUserId;
            if (sparseArray.indexOfKey(i4) < 0) {
                try {
                    android.content.pm.PackageInfo packageInfo = getPackageManager().getPackageInfo(str, 0L, i4);
                    applicationInfo = packageInfo == null ? null : packageInfo.applicationInfo;
                } catch (android.os.RemoteException e) {
                    applicationInfo = null;
                }
                sparseArray.put(i4, applicationInfo);
                if (applicationInfo != null) {
                    applicationInfo2 = applicationInfo;
                    z = z2;
                } else {
                    android.util.Slog.d(TAG, "Could not find package " + str + " for user " + i4);
                    applicationInfo2 = applicationInfo;
                    z = z2 | this.mPackageDynamicCodeLoading.removeUserPackage(str, i4);
                }
            } else {
                applicationInfo2 = (android.content.pm.ApplicationInfo) sparseArray.get(i4);
                z = z2;
            }
            if (applicationInfo2 != null) {
                if (!fileIsUnder(key, applicationInfo2.credentialProtectedDataDir)) {
                    if (fileIsUnder(key, applicationInfo2.deviceProtectedDataDir)) {
                        i = 1;
                    } else {
                        android.util.Slog.e(TAG, "Could not infer CE/DE storage for path " + key);
                        z2 = z | this.mPackageDynamicCodeLoading.removeFile(str, key, i4);
                    }
                } else {
                    i = 2;
                }
                try {
                    installer = this.mInstaller;
                    i3 = applicationInfo2.uid;
                    str4 = applicationInfo2.volumeUuid;
                    applicationInfo3 = applicationInfo2;
                    str2 = TAG;
                } catch (com.android.server.pm.Installer.InstallerException e2) {
                    e = e2;
                    applicationInfo3 = applicationInfo2;
                    str2 = TAG;
                }
                try {
                    bArr = installer.hashSecondaryDexFile(key, str, i3, str4, i);
                } catch (com.android.server.pm.Installer.InstallerException e3) {
                    e = e3;
                    android.util.Slog.e(str2, "Got InstallerException when hashing file " + key + ": " + e.getMessage());
                    bArr = null;
                    if (value.mFileType != 'D') {
                    }
                    java.lang.String computeSha256Digest = android.util.PackageUtils.computeSha256Digest(new java.io.File(key).getName().getBytes());
                    if (bArr == null) {
                    }
                    android.util.Slog.d(str2, "Got no hash for " + key);
                    z |= this.mPackageDynamicCodeLoading.removeFile(str, key, i4);
                    while (r4.hasNext()) {
                    }
                    z2 = z;
                }
                if (value.mFileType != 'D') {
                    str3 = DCL_DEX_SUBTAG;
                } else {
                    str3 = DCL_NATIVE_SUBTAG;
                }
                java.lang.String computeSha256Digest2 = android.util.PackageUtils.computeSha256Digest(new java.io.File(key).getName().getBytes());
                if (bArr == null && bArr.length == 32) {
                    computeSha256Digest2 = computeSha256Digest2 + ' ' + libcore.util.HexEncoding.encodeToString(bArr);
                } else {
                    android.util.Slog.d(str2, "Got no hash for " + key);
                    z |= this.mPackageDynamicCodeLoading.removeFile(str, key, i4);
                }
                for (java.lang.String str5 : value.mLoadingPackages) {
                    if (str5.equals(str)) {
                        i2 = applicationInfo3.uid;
                    } else {
                        try {
                            i2 = getPackageManager().getPackageUid(str5, 0L, i4);
                        } catch (android.os.RemoteException e4) {
                            i2 = -1;
                            if (i2 == -1) {
                            }
                        }
                    }
                    if (i2 == -1) {
                        writeDclEvent(str3, i2, computeSha256Digest2);
                    }
                }
                z2 = z;
            } else {
                z2 = z;
            }
        }
        if (z2) {
            this.mPackageDynamicCodeLoading.maybeWriteAsync();
        }
    }

    private boolean fileIsUnder(java.lang.String str, java.lang.String str2) {
        if (str2 == null) {
            return false;
        }
        try {
            return android.os.FileUtils.contains(new java.io.File(str2).getCanonicalPath(), new java.io.File(str).getCanonicalPath());
        } catch (java.io.IOException e) {
            return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pm.dex.PackageDynamicCodeLoading.PackageDynamicCode getPackageDynamicCodeInfo(java.lang.String str) {
        return this.mPackageDynamicCodeLoading.getPackageDynamicCodeInfo(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeDclEvent(java.lang.String str, int i, java.lang.String str2) {
        android.util.EventLog.writeEvent(SNET_TAG, str, java.lang.Integer.valueOf(i), str2);
    }

    public void recordDex(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (this.mPackageDynamicCodeLoading.record(str2, str, 68, i, str3)) {
            this.mPackageDynamicCodeLoading.maybeWriteAsync();
        }
    }

    public void recordNative(int i, java.lang.String str) {
        try {
            java.lang.String[] packagesForUid = getPackageManager().getPackagesForUid(i);
            if (packagesForUid != null) {
                if (packagesForUid.length == 0) {
                    return;
                }
                java.lang.String str2 = packagesForUid[0];
                if (this.mPackageDynamicCodeLoading.record(str2, str, 78, android.os.UserHandle.getUserId(i), str2)) {
                    this.mPackageDynamicCodeLoading.maybeWriteAsync();
                }
            }
        } catch (android.os.RemoteException e) {
        }
    }

    void clear() {
        this.mPackageDynamicCodeLoading.clear();
    }

    void removePackage(java.lang.String str) {
        if (this.mPackageDynamicCodeLoading.removePackage(str)) {
            this.mPackageDynamicCodeLoading.maybeWriteAsync();
        }
    }

    void removeUserPackage(java.lang.String str, int i) {
        if (this.mPackageDynamicCodeLoading.removeUserPackage(str, i)) {
            this.mPackageDynamicCodeLoading.maybeWriteAsync();
        }
    }

    void readAndSync(java.util.Map<java.lang.String, java.util.Set<java.lang.Integer>> map) {
        this.mPackageDynamicCodeLoading.read();
        this.mPackageDynamicCodeLoading.syncData(map);
    }

    public void writeNow() {
        this.mPackageDynamicCodeLoading.writeNow();
    }

    public void load(java.util.Map<java.lang.Integer, java.util.List<android.content.pm.PackageInfo>> map) {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.Integer, java.util.List<android.content.pm.PackageInfo>> entry : map.entrySet()) {
            java.util.List<android.content.pm.PackageInfo> value = entry.getValue();
            int intValue = entry.getKey().intValue();
            java.util.Iterator<android.content.pm.PackageInfo> it = value.iterator();
            while (it.hasNext()) {
                hashMap.computeIfAbsent(it.next().packageName, new java.util.function.Function() { // from class: com.android.server.pm.dex.DynamicCodeLogger$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.util.Set lambda$load$0;
                        lambda$load$0 = com.android.server.pm.dex.DynamicCodeLogger.lambda$load$0((java.lang.String) obj);
                        return lambda$load$0;
                    }
                }).add(java.lang.Integer.valueOf(intValue));
            }
        }
        readAndSync(hashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$load$0(java.lang.String str) {
        return new java.util.HashSet();
    }

    public void notifyPackageDataDestroyed(java.lang.String str, int i) {
        if (i == -1) {
            removePackage(str);
        } else {
            removeUserPackage(str, i);
        }
    }
}
