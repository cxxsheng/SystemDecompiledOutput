package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public class PackageStateUnserialized {
    private boolean apkInUpdatedApex;
    private boolean hiddenUntilInstalled;

    @android.annotation.NonNull
    private volatile long[] lastPackageUsageTimeInMills;

    @android.annotation.Nullable
    private java.lang.String mApexModuleName;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageSetting mPackageSetting;

    @android.annotation.Nullable
    private java.lang.String overrideSeInfo;

    @android.annotation.NonNull
    private java.lang.String seInfo;
    private boolean updatedSystemApp;

    @android.annotation.NonNull
    private java.util.List<com.android.server.pm.pkg.SharedLibraryWrapper> usesLibraryInfos = java.util.Collections.emptyList();

    @android.annotation.NonNull
    private java.util.List<java.lang.String> usesLibraryFiles = java.util.Collections.emptyList();

    public PackageStateUnserialized(@android.annotation.NonNull com.android.server.pm.PackageSetting packageSetting) {
        this.mPackageSetting = packageSetting;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageStateUnserialized addUsesLibraryInfo(@android.annotation.NonNull com.android.server.pm.pkg.SharedLibraryWrapper sharedLibraryWrapper) {
        this.usesLibraryInfos = com.android.internal.util.CollectionUtils.add(this.usesLibraryInfos, sharedLibraryWrapper);
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageStateUnserialized addUsesLibraryFile(@android.annotation.NonNull java.lang.String str) {
        this.usesLibraryFiles = com.android.internal.util.CollectionUtils.add(this.usesLibraryFiles, str);
        return this;
    }

    private long[] lazyInitLastPackageUsageTimeInMills() {
        return new long[8];
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setLastPackageUsageTimeInMills(int i, long j) {
        if (i < 0) {
            return this;
        }
        if (i >= 8) {
            return this;
        }
        getLastPackageUsageTimeInMills()[i] = j;
        return this;
    }

    public long getLatestPackageUseTimeInMills() {
        long j = 0;
        for (long j2 : getLastPackageUsageTimeInMills()) {
            j = java.lang.Math.max(j, j2);
        }
        return j;
    }

    public long getLatestForegroundPackageUseTimeInMills() {
        int[] iArr = {0, 2};
        long j = 0;
        for (int i = 0; i < 2; i++) {
            j = java.lang.Math.max(j, getLastPackageUsageTimeInMills()[iArr[i]]);
        }
        return j;
    }

    public void updateFrom(com.android.server.pm.pkg.PackageStateUnserialized packageStateUnserialized) {
        this.hiddenUntilInstalled = packageStateUnserialized.hiddenUntilInstalled;
        if (!packageStateUnserialized.usesLibraryInfos.isEmpty()) {
            this.usesLibraryInfos = new java.util.ArrayList(packageStateUnserialized.usesLibraryInfos);
        }
        if (!packageStateUnserialized.usesLibraryFiles.isEmpty()) {
            this.usesLibraryFiles = new java.util.ArrayList(packageStateUnserialized.usesLibraryFiles);
        }
        this.updatedSystemApp = packageStateUnserialized.updatedSystemApp;
        this.apkInUpdatedApex = packageStateUnserialized.apkInUpdatedApex;
        this.lastPackageUsageTimeInMills = packageStateUnserialized.lastPackageUsageTimeInMills;
        this.overrideSeInfo = packageStateUnserialized.overrideSeInfo;
        this.seInfo = packageStateUnserialized.seInfo;
        this.mApexModuleName = packageStateUnserialized.mApexModuleName;
        this.mPackageSetting.onChanged();
    }

    @android.annotation.NonNull
    public java.util.List<android.content.pm.SharedLibraryInfo> getNonNativeUsesLibraryInfos() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        this.usesLibraryInfos = getUsesLibraryInfos();
        for (int i = 0; i < this.usesLibraryInfos.size(); i++) {
            com.android.server.pm.pkg.SharedLibraryWrapper sharedLibraryWrapper = this.usesLibraryInfos.get(i);
            if (!sharedLibraryWrapper.isNative()) {
                arrayList.add(sharedLibraryWrapper.getInfo());
            }
        }
        return arrayList;
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setHiddenUntilInstalled(boolean z) {
        this.hiddenUntilInstalled = z;
        this.mPackageSetting.onChanged();
        return this;
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setUsesLibraryInfos(@android.annotation.NonNull java.util.List<android.content.pm.SharedLibraryInfo> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(new com.android.server.pm.pkg.SharedLibraryWrapper(list.get(i)));
        }
        this.usesLibraryInfos = arrayList;
        this.mPackageSetting.onChanged();
        return this;
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setUsesLibraryFiles(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        this.usesLibraryFiles = list;
        this.mPackageSetting.onChanged();
        return this;
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setUpdatedSystemApp(boolean z) {
        this.updatedSystemApp = z;
        this.mPackageSetting.onChanged();
        return this;
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setApkInUpdatedApex(boolean z) {
        this.apkInUpdatedApex = z;
        this.mPackageSetting.onChanged();
        return this;
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setLastPackageUsageTimeInMills(@android.annotation.NonNull long... jArr) {
        this.lastPackageUsageTimeInMills = jArr;
        this.mPackageSetting.onChanged();
        return this;
    }

    public com.android.server.pm.pkg.PackageStateUnserialized setOverrideSeInfo(@android.annotation.Nullable java.lang.String str) {
        this.overrideSeInfo = str;
        this.mPackageSetting.onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageStateUnserialized setSeInfo(@android.annotation.NonNull java.lang.String str) {
        this.seInfo = android.text.TextUtils.safeIntern(str);
        this.mPackageSetting.onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageStateUnserialized setApexModuleName(@android.annotation.NonNull java.lang.String str) {
        this.mApexModuleName = str;
        this.mPackageSetting.onChanged();
        return this;
    }

    public boolean isHiddenUntilInstalled() {
        return this.hiddenUntilInstalled;
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.pkg.SharedLibraryWrapper> getUsesLibraryInfos() {
        return this.usesLibraryInfos;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> getUsesLibraryFiles() {
        return this.usesLibraryFiles;
    }

    public boolean isUpdatedSystemApp() {
        return this.updatedSystemApp;
    }

    public boolean isApkInUpdatedApex() {
        return this.apkInUpdatedApex;
    }

    @android.annotation.NonNull
    public long[] getLastPackageUsageTimeInMills() {
        long[] jArr = this.lastPackageUsageTimeInMills;
        if (jArr == null) {
            synchronized (this) {
                try {
                    jArr = this.lastPackageUsageTimeInMills;
                    if (jArr == null) {
                        jArr = lazyInitLastPackageUsageTimeInMills();
                        this.lastPackageUsageTimeInMills = jArr;
                    }
                } finally {
                }
            }
        }
        return jArr;
    }

    @android.annotation.Nullable
    public java.lang.String getOverrideSeInfo() {
        return this.overrideSeInfo;
    }

    @android.annotation.NonNull
    public java.lang.String getSeInfo() {
        return this.seInfo;
    }

    @android.annotation.NonNull
    public com.android.server.pm.PackageSetting getPackageSetting() {
        return this.mPackageSetting;
    }

    @android.annotation.Nullable
    public java.lang.String getApexModuleName() {
        return this.mApexModuleName;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
