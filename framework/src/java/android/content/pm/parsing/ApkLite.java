package android.content.pm.parsing;

/* loaded from: classes.dex */
public class ApkLite {
    private final android.content.pm.ArchivedPackageParcel mArchivedPackage;
    private final java.lang.String mConfigForSplit;
    private final boolean mCoreApp;
    private final boolean mDebuggable;
    private final java.lang.String mEmergencyInstaller;
    private final boolean mExtractNativeLibs;
    private final boolean mFeatureSplit;
    private final boolean mHasDeviceAdminReceiver;
    private final int mInstallLocation;
    private final boolean mIsSdkLibrary;
    private final boolean mIsolatedSplits;
    private final int mMinSdkVersion;
    private final boolean mMultiArch;
    private final boolean mOverlayIsStatic;
    private final int mOverlayPriority;
    private final java.lang.String mPackageName;
    private final java.lang.String mPath;
    private final boolean mProfileableByShell;
    private final java.util.Set<java.lang.String> mRequiredSplitTypes;
    private final java.lang.String mRequiredSystemPropertyName;
    private final java.lang.String mRequiredSystemPropertyValue;
    private final int mRevisionCode;
    private final int mRollbackDataPolicy;
    private final android.content.pm.SigningDetails mSigningDetails;
    private final java.lang.String mSplitName;
    private final boolean mSplitRequired;
    private final java.util.Set<java.lang.String> mSplitTypes;
    private final java.lang.String mTargetPackageName;
    private final int mTargetSdkVersion;
    private final boolean mUpdatableSystem;
    private final boolean mUse32bitAbi;
    private final boolean mUseEmbeddedDex;
    private final java.lang.String mUsesSplitName;
    private final android.content.pm.VerifierInfo[] mVerifiers;
    private final int mVersionCode;
    private final int mVersionCodeMajor;

    public ApkLite(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, java.lang.String str4, java.lang.String str5, boolean z2, int i, int i2, int i3, int i4, java.util.List<android.content.pm.VerifierInfo> list, android.content.pm.SigningDetails signingDetails, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, java.lang.String str6, boolean z11, int i5, java.lang.String str7, java.lang.String str8, int i6, int i7, int i8, java.util.Set<java.lang.String> set, java.util.Set<java.lang.String> set2, boolean z12, boolean z13, boolean z14, java.lang.String str9) {
        this.mPath = str;
        this.mPackageName = str2;
        this.mSplitName = str3;
        this.mSplitTypes = set2;
        this.mFeatureSplit = z;
        this.mConfigForSplit = str4;
        this.mUsesSplitName = str5;
        this.mRequiredSplitTypes = set;
        this.mSplitRequired = z2 || hasAnyRequiredSplitTypes();
        this.mVersionCode = i;
        this.mVersionCodeMajor = i2;
        this.mRevisionCode = i3;
        this.mInstallLocation = i4;
        this.mVerifiers = (android.content.pm.VerifierInfo[]) list.toArray(new android.content.pm.VerifierInfo[list.size()]);
        this.mSigningDetails = signingDetails;
        this.mCoreApp = z3;
        this.mDebuggable = z4;
        this.mProfileableByShell = z5;
        this.mMultiArch = z6;
        this.mUse32bitAbi = z7;
        this.mUseEmbeddedDex = z8;
        this.mExtractNativeLibs = z9;
        this.mIsolatedSplits = z10;
        this.mTargetPackageName = str6;
        this.mOverlayIsStatic = z11;
        this.mOverlayPriority = i5;
        this.mRequiredSystemPropertyName = str7;
        this.mRequiredSystemPropertyValue = str8;
        this.mMinSdkVersion = i6;
        this.mTargetSdkVersion = i7;
        this.mRollbackDataPolicy = i8;
        this.mHasDeviceAdminReceiver = z12;
        this.mIsSdkLibrary = z13;
        this.mUpdatableSystem = z14;
        this.mEmergencyInstaller = str9;
        this.mArchivedPackage = null;
    }

    public ApkLite(java.lang.String str, android.content.pm.ArchivedPackageParcel archivedPackageParcel) {
        this.mPath = str;
        this.mPackageName = archivedPackageParcel.packageName;
        this.mSplitName = null;
        this.mSplitTypes = null;
        this.mFeatureSplit = false;
        this.mConfigForSplit = null;
        this.mUsesSplitName = null;
        this.mRequiredSplitTypes = null;
        this.mSplitRequired = hasAnyRequiredSplitTypes();
        this.mVersionCode = archivedPackageParcel.versionCode;
        this.mVersionCodeMajor = archivedPackageParcel.versionCodeMajor;
        this.mRevisionCode = 0;
        this.mInstallLocation = -1;
        this.mVerifiers = new android.content.pm.VerifierInfo[0];
        this.mSigningDetails = archivedPackageParcel.signingDetails;
        this.mCoreApp = false;
        this.mDebuggable = false;
        this.mProfileableByShell = false;
        this.mMultiArch = false;
        this.mUse32bitAbi = false;
        this.mUseEmbeddedDex = false;
        this.mExtractNativeLibs = false;
        this.mIsolatedSplits = false;
        this.mTargetPackageName = null;
        this.mOverlayIsStatic = false;
        this.mOverlayPriority = 0;
        this.mRequiredSystemPropertyName = null;
        this.mRequiredSystemPropertyValue = null;
        this.mMinSdkVersion = 1;
        this.mTargetSdkVersion = archivedPackageParcel.targetSdkVersion;
        this.mRollbackDataPolicy = 0;
        this.mHasDeviceAdminReceiver = false;
        this.mIsSdkLibrary = false;
        this.mUpdatableSystem = true;
        this.mEmergencyInstaller = null;
        this.mArchivedPackage = archivedPackageParcel;
    }

    public long getLongVersionCode() {
        return android.content.pm.PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
    }

    private boolean hasAnyRequiredSplitTypes() {
        return !com.android.internal.util.CollectionUtils.isEmpty(this.mRequiredSplitTypes);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getPath() {
        return this.mPath;
    }

    public java.lang.String getSplitName() {
        return this.mSplitName;
    }

    public java.lang.String getUsesSplitName() {
        return this.mUsesSplitName;
    }

    public java.lang.String getConfigForSplit() {
        return this.mConfigForSplit;
    }

    public java.util.Set<java.lang.String> getRequiredSplitTypes() {
        return this.mRequiredSplitTypes;
    }

    public java.util.Set<java.lang.String> getSplitTypes() {
        return this.mSplitTypes;
    }

    public int getVersionCodeMajor() {
        return this.mVersionCodeMajor;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int getRevisionCode() {
        return this.mRevisionCode;
    }

    public int getInstallLocation() {
        return this.mInstallLocation;
    }

    public int getMinSdkVersion() {
        return this.mMinSdkVersion;
    }

    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    public android.content.pm.VerifierInfo[] getVerifiers() {
        return this.mVerifiers;
    }

    public android.content.pm.SigningDetails getSigningDetails() {
        return this.mSigningDetails;
    }

    public boolean isFeatureSplit() {
        return this.mFeatureSplit;
    }

    public boolean isIsolatedSplits() {
        return this.mIsolatedSplits;
    }

    public boolean isSplitRequired() {
        return this.mSplitRequired;
    }

    public boolean isCoreApp() {
        return this.mCoreApp;
    }

    public boolean isDebuggable() {
        return this.mDebuggable;
    }

    public boolean isProfileableByShell() {
        return this.mProfileableByShell;
    }

    public boolean isMultiArch() {
        return this.mMultiArch;
    }

    public boolean isUse32bitAbi() {
        return this.mUse32bitAbi;
    }

    public boolean isExtractNativeLibs() {
        return this.mExtractNativeLibs;
    }

    public boolean isUseEmbeddedDex() {
        return this.mUseEmbeddedDex;
    }

    public java.lang.String getTargetPackageName() {
        return this.mTargetPackageName;
    }

    public boolean isOverlayIsStatic() {
        return this.mOverlayIsStatic;
    }

    public int getOverlayPriority() {
        return this.mOverlayPriority;
    }

    public java.lang.String getRequiredSystemPropertyName() {
        return this.mRequiredSystemPropertyName;
    }

    public java.lang.String getRequiredSystemPropertyValue() {
        return this.mRequiredSystemPropertyValue;
    }

    public int getRollbackDataPolicy() {
        return this.mRollbackDataPolicy;
    }

    public boolean isHasDeviceAdminReceiver() {
        return this.mHasDeviceAdminReceiver;
    }

    public boolean isIsSdkLibrary() {
        return this.mIsSdkLibrary;
    }

    public boolean isUpdatableSystem() {
        return this.mUpdatableSystem;
    }

    public java.lang.String getEmergencyInstaller() {
        return this.mEmergencyInstaller;
    }

    public android.content.pm.ArchivedPackageParcel getArchivedPackage() {
        return this.mArchivedPackage;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
