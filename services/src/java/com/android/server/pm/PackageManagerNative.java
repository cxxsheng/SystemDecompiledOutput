package com.android.server.pm;

/* loaded from: classes2.dex */
final class PackageManagerNative extends android.content.pm.IPackageManagerNative.Stub {
    private final com.android.server.pm.PackageManagerService mPm;

    PackageManagerNative(com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    public java.lang.String[] getNamesForUids(int[] iArr) throws android.os.RemoteException {
        java.lang.String[] strArr;
        java.lang.String[] strArr2 = null;
        if (iArr != null) {
            try {
                if (iArr.length != 0) {
                    java.lang.String[] namesForUids = this.mPm.snapshotComputer().getNamesForUids(iArr);
                    if (namesForUids != null) {
                        strArr2 = namesForUids;
                    } else {
                        try {
                            strArr2 = new java.lang.String[iArr.length];
                        } catch (java.lang.Throwable th) {
                            th = th;
                            strArr = strArr2;
                            strArr2 = namesForUids;
                            android.util.Slog.e("PackageManager", "uids: " + java.util.Arrays.toString(iArr));
                            android.util.Slog.e("PackageManager", "names: " + java.util.Arrays.toString(strArr2));
                            android.util.Slog.e("PackageManager", "results: " + java.util.Arrays.toString(strArr));
                            android.util.Slog.e("PackageManager", "throwing exception", th);
                            throw th;
                        }
                    }
                    for (int length = strArr2.length - 1; length >= 0; length--) {
                        if (strArr2[length] == null) {
                            strArr2[length] = "";
                        }
                    }
                    return strArr2;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                strArr = null;
            }
        }
        return null;
    }

    public java.lang.String getInstallerForPackage(java.lang.String str) throws android.os.RemoteException {
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        java.lang.String installerPackageName = snapshotComputer.getInstallerPackageName(str, userId);
        if (!android.text.TextUtils.isEmpty(installerPackageName)) {
            return installerPackageName;
        }
        android.content.pm.ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(str, 0L, userId);
        if (applicationInfo != null && (applicationInfo.flags & 1) != 0) {
            return "preload";
        }
        return "";
    }

    public long getVersionCodeForPackage(java.lang.String str) throws android.os.RemoteException {
        try {
            android.content.pm.PackageInfo packageInfo = this.mPm.snapshotComputer().getPackageInfo(str, 0L, android.os.UserHandle.getUserId(android.os.Binder.getCallingUid()));
            if (packageInfo != null) {
                return packageInfo.getLongVersionCode();
            }
        } catch (java.lang.Exception e) {
        }
        return 0L;
    }

    public int getTargetSdkVersionForPackage(java.lang.String str) throws android.os.RemoteException {
        int targetSdkVersion = this.mPm.snapshotComputer().getTargetSdkVersion(str);
        if (targetSdkVersion != -1) {
            return targetSdkVersion;
        }
        throw new android.os.RemoteException("Couldn't get targetSdkVersion for package " + str);
    }

    public boolean isPackageDebuggable(java.lang.String str) throws android.os.RemoteException {
        android.content.pm.ApplicationInfo applicationInfo = this.mPm.snapshotComputer().getApplicationInfo(str, 0L, android.os.UserHandle.getCallingUserId());
        if (applicationInfo != null) {
            return (applicationInfo.flags & 2) != 0;
        }
        throw new android.os.RemoteException("Couldn't get debug flag for package " + str);
    }

    public boolean[] isAudioPlaybackCaptureAllowed(java.lang.String[] strArr) throws android.os.RemoteException {
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        int length = strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i = length - 1; i >= 0; i--) {
            android.content.pm.ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(strArr[i], 0L, userId);
            zArr[i] = applicationInfo != null && applicationInfo.isAudioPlaybackCaptureAllowed();
        }
        return zArr;
    }

    public int getLocationFlags(java.lang.String str) throws android.os.RemoteException {
        android.content.pm.ApplicationInfo applicationInfo = this.mPm.snapshotComputer().getApplicationInfo(str, 0L, android.os.UserHandle.getUserId(android.os.Binder.getCallingUid()));
        if (applicationInfo == null) {
            throw new android.os.RemoteException("Couldn't get ApplicationInfo for package " + str);
        }
        return (applicationInfo.isSystemApp() ? 1 : 0) | (applicationInfo.isVendor() ? 2 : 0) | (applicationInfo.isProduct() ? 4 : 0);
    }

    public java.lang.String getModuleMetadataPackageName() throws android.os.RemoteException {
        return this.mPm.getModuleMetadataPackageName();
    }

    public boolean hasSha256SigningCertificate(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
        return this.mPm.snapshotComputer().hasSigningCertificate(str, bArr, 1);
    }

    public boolean hasSystemFeature(java.lang.String str, int i) {
        return this.mPm.hasSystemFeature(str, i);
    }

    public void registerStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) {
        this.mPm.mInstallerService.getStagingManager().registerStagedApexObserver(iStagedApexObserver);
    }

    public void unregisterStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) {
        this.mPm.mInstallerService.getStagingManager().unregisterStagedApexObserver(iStagedApexObserver);
    }

    public java.lang.String[] getStagedApexModuleNames() {
        return (java.lang.String[]) this.mPm.mInstallerService.getStagingManager().getStagedApexModuleNames().toArray(new java.lang.String[0]);
    }

    @android.annotation.Nullable
    public android.content.pm.StagedApexInfo getStagedApexInfo(java.lang.String str) {
        return this.mPm.mInstallerService.getStagingManager().getStagedApexInfo(str);
    }
}
