package com.android.internal.pm.parsing.pkg;

/* loaded from: classes5.dex */
public interface ParsedPackage extends com.android.server.pm.pkg.AndroidPackage {
    com.android.internal.pm.parsing.pkg.ParsedPackage addUsesLibrary(int i, java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage addUsesOptionalLibrary(int i, java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage capPermissionPriorities();

    com.android.internal.pm.parsing.pkg.ParsedPackage clearAdoptPermissions();

    com.android.internal.pm.parsing.pkg.ParsedPackage clearOriginalPackages();

    com.android.internal.pm.parsing.pkg.ParsedPackage clearProtectedBroadcasts();

    com.android.internal.pm.parsing.pkg.AndroidPackageInternal hideAsFinal();

    boolean isAppMetadataFileInApk();

    com.android.internal.pm.parsing.pkg.ParsedPackage markNotActivitiesAsNotExportedIfSingleUser();

    com.android.internal.pm.parsing.pkg.ParsedPackage removePermission(int i);

    com.android.internal.pm.parsing.pkg.ParsedPackage removeUsesLibrary(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage removeUsesOptionalLibrary(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setAllComponentsDirectBootAware(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setApex(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setBaseApkPath(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setCoreApp(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setDefaultToDeviceProtectedStorage(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setDirectBootAware(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setEmergencyInstaller(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setFactoryTest(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setNativeLibraryDir(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setNativeLibraryRootDir(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setNativeLibraryRootRequiresIsa(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setOdm(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setOem(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setPackageName(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setPath(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setPersistent(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setPrimaryCpuAbi(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setPrivileged(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setProduct(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setRestrictUpdateHash(byte[] bArr);

    com.android.internal.pm.parsing.pkg.ParsedPackage setSecondaryCpuAbi(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setSecondaryNativeLibraryDir(java.lang.String str);

    com.android.internal.pm.parsing.pkg.ParsedPackage setSignedWithPlatformKey(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setSigningDetails(android.content.pm.SigningDetails signingDetails);

    com.android.internal.pm.parsing.pkg.ParsedPackage setSplitCodePaths(java.lang.String[] strArr);

    com.android.internal.pm.parsing.pkg.ParsedPackage setStub(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setSystem(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setSystemExt(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setUid(int i);

    com.android.internal.pm.parsing.pkg.ParsedPackage setUpdatableSystem(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setVendor(boolean z);

    com.android.internal.pm.parsing.pkg.ParsedPackage setVersionCode(int i);

    com.android.internal.pm.parsing.pkg.ParsedPackage setVersionCodeMajor(int i);
}
