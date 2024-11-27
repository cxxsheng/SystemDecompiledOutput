package com.android.server.pm;

/* loaded from: classes2.dex */
final class ReconciledPackage {
    private final java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> mAllPackages;
    public final java.util.List<android.content.pm.SharedLibraryInfo> mAllowedSharedLibraryInfos;
    public java.util.ArrayList<android.content.pm.SharedLibraryInfo> mCollectedSharedLibraryInfos;
    public final com.android.server.pm.DeletePackageAction mDeletePackageAction;

    @android.annotation.NonNull
    public final com.android.server.pm.InstallRequest mInstallRequest;
    private final java.util.List<com.android.server.pm.InstallRequest> mInstallRequests;
    public final boolean mRemoveAppKeySetData;
    public final boolean mSharedUserSignaturesChanged;
    public final android.content.pm.SigningDetails mSigningDetails;

    ReconciledPackage(java.util.List<com.android.server.pm.InstallRequest> list, java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> map, com.android.server.pm.InstallRequest installRequest, com.android.server.pm.DeletePackageAction deletePackageAction, java.util.List<android.content.pm.SharedLibraryInfo> list2, android.content.pm.SigningDetails signingDetails, boolean z, boolean z2) {
        this.mInstallRequests = list;
        this.mAllPackages = map;
        this.mInstallRequest = installRequest;
        this.mDeletePackageAction = deletePackageAction;
        this.mAllowedSharedLibraryInfos = list2;
        this.mSigningDetails = signingDetails;
        this.mSharedUserSignaturesChanged = z;
        this.mRemoveAppKeySetData = z2;
    }

    @android.annotation.NonNull
    java.util.Map<java.lang.String, com.android.server.pm.pkg.AndroidPackage> getCombinedAvailablePackages() {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mAllPackages.size() + this.mInstallRequests.size());
        arrayMap.putAll(this.mAllPackages);
        for (com.android.server.pm.InstallRequest installRequest : this.mInstallRequests) {
            arrayMap.put(installRequest.getScannedPackageSetting().getPackageName(), installRequest.getParsedPackage());
        }
        return arrayMap;
    }
}
