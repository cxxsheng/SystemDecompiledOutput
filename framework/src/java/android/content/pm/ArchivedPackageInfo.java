package android.content.pm;

/* loaded from: classes.dex */
public final class ArchivedPackageInfo {
    private java.lang.String mDefaultToDeviceProtectedStorage;
    private java.util.List<android.content.pm.ArchivedActivityInfo> mLauncherActivities;
    private java.lang.String mPackageName;
    private java.lang.String mRequestLegacyExternalStorage;
    private android.content.pm.SigningInfo mSigningInfo;
    private int mTargetSdkVersion;
    private java.lang.String mUserDataFragile;
    private int mVersionCode;
    private int mVersionCodeMajor;

    public ArchivedPackageInfo(java.lang.String str, android.content.pm.SigningInfo signingInfo, java.util.List<android.content.pm.ArchivedActivityInfo> list) {
        this.mVersionCode = 0;
        this.mVersionCodeMajor = 0;
        this.mTargetSdkVersion = 0;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(signingInfo);
        java.util.Objects.requireNonNull(list);
        this.mPackageName = str;
        this.mSigningInfo = signingInfo;
        this.mLauncherActivities = list;
    }

    public ArchivedPackageInfo(android.content.pm.ArchivedPackageParcel archivedPackageParcel) {
        this.mVersionCode = 0;
        this.mVersionCodeMajor = 0;
        this.mTargetSdkVersion = 0;
        this.mPackageName = archivedPackageParcel.packageName;
        this.mSigningInfo = new android.content.pm.SigningInfo(archivedPackageParcel.signingDetails);
        this.mVersionCode = archivedPackageParcel.versionCode;
        this.mVersionCodeMajor = archivedPackageParcel.versionCodeMajor;
        this.mTargetSdkVersion = archivedPackageParcel.targetSdkVersion;
        this.mDefaultToDeviceProtectedStorage = archivedPackageParcel.defaultToDeviceProtectedStorage;
        this.mRequestLegacyExternalStorage = archivedPackageParcel.requestLegacyExternalStorage;
        this.mUserDataFragile = archivedPackageParcel.userDataFragile;
        this.mLauncherActivities = new java.util.ArrayList();
        if (archivedPackageParcel.archivedActivities != null) {
            for (android.content.pm.ArchivedActivityParcel archivedActivityParcel : archivedPackageParcel.archivedActivities) {
                this.mLauncherActivities.add(new android.content.pm.ArchivedActivityInfo(archivedActivityParcel));
            }
        }
    }

    android.content.pm.ArchivedPackageParcel getParcel() {
        android.content.pm.ArchivedPackageParcel archivedPackageParcel = new android.content.pm.ArchivedPackageParcel();
        archivedPackageParcel.packageName = this.mPackageName;
        archivedPackageParcel.signingDetails = this.mSigningInfo.getSigningDetails();
        archivedPackageParcel.versionCode = this.mVersionCode;
        archivedPackageParcel.versionCodeMajor = this.mVersionCodeMajor;
        archivedPackageParcel.targetSdkVersion = this.mTargetSdkVersion;
        archivedPackageParcel.defaultToDeviceProtectedStorage = this.mDefaultToDeviceProtectedStorage;
        archivedPackageParcel.requestLegacyExternalStorage = this.mRequestLegacyExternalStorage;
        archivedPackageParcel.userDataFragile = this.mUserDataFragile;
        archivedPackageParcel.archivedActivities = new android.content.pm.ArchivedActivityParcel[this.mLauncherActivities.size()];
        int length = archivedPackageParcel.archivedActivities.length;
        for (int i = 0; i < length; i++) {
            archivedPackageParcel.archivedActivities[i] = this.mLauncherActivities.get(i).getParcel();
        }
        return archivedPackageParcel;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.content.pm.SigningInfo getSigningInfo() {
        return this.mSigningInfo;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int getVersionCodeMajor() {
        return this.mVersionCodeMajor;
    }

    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    public java.lang.String getDefaultToDeviceProtectedStorage() {
        return this.mDefaultToDeviceProtectedStorage;
    }

    public java.lang.String getRequestLegacyExternalStorage() {
        return this.mRequestLegacyExternalStorage;
    }

    public java.lang.String getUserDataFragile() {
        return this.mUserDataFragile;
    }

    public java.util.List<android.content.pm.ArchivedActivityInfo> getLauncherActivities() {
        return this.mLauncherActivities;
    }

    public android.content.pm.ArchivedPackageInfo setPackageName(java.lang.String str) {
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setSigningInfo(android.content.pm.SigningInfo signingInfo) {
        this.mSigningInfo = signingInfo;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSigningInfo);
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setVersionCode(int i) {
        this.mVersionCode = i;
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setVersionCodeMajor(int i) {
        this.mVersionCodeMajor = i;
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setTargetSdkVersion(int i) {
        this.mTargetSdkVersion = i;
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setDefaultToDeviceProtectedStorage(java.lang.String str) {
        this.mDefaultToDeviceProtectedStorage = str;
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setRequestLegacyExternalStorage(java.lang.String str) {
        this.mRequestLegacyExternalStorage = str;
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setUserDataFragile(java.lang.String str) {
        this.mUserDataFragile = str;
        return this;
    }

    public android.content.pm.ArchivedPackageInfo setLauncherActivities(java.util.List<android.content.pm.ArchivedActivityInfo> list) {
        this.mLauncherActivities = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mLauncherActivities);
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
