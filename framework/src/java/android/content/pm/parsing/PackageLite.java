package android.content.pm.parsing;

/* loaded from: classes.dex */
public class PackageLite {
    private final android.content.pm.ArchivedPackageParcel mArchivedPackage;
    private final java.lang.String mBaseApkPath;
    private final java.util.Set<java.lang.String> mBaseRequiredSplitTypes;
    private final int mBaseRevisionCode;
    private final java.lang.String[] mConfigForSplit;
    private final boolean mCoreApp;
    private final boolean mDebuggable;
    private final boolean mExtractNativeLibs;
    private final int mInstallLocation;
    private final boolean[] mIsFeatureSplits;
    private final boolean mIsSdkLibrary;
    private final boolean mIsolatedSplits;
    private final boolean mMultiArch;
    private final java.lang.String mPackageName;
    private final java.lang.String mPath;
    private final boolean mProfileableByShell;
    private final java.util.Set<java.lang.String>[] mRequiredSplitTypes;
    private final android.content.pm.SigningDetails mSigningDetails;
    private final java.lang.String[] mSplitApkPaths;
    private final java.lang.String[] mSplitNames;
    private final boolean mSplitRequired;
    private final int[] mSplitRevisionCodes;
    private final java.util.Set<java.lang.String>[] mSplitTypes;
    private final int mTargetSdk;
    private final boolean mUse32bitAbi;
    private final boolean mUseEmbeddedDex;
    private final java.lang.String[] mUsesSplitNames;
    private final android.content.pm.VerifierInfo[] mVerifiers;
    private final int mVersionCode;
    private final int mVersionCodeMajor;

    public PackageLite(java.lang.String str, java.lang.String str2, android.content.pm.parsing.ApkLite apkLite, java.lang.String[] strArr, boolean[] zArr, java.lang.String[] strArr2, java.lang.String[] strArr3, java.lang.String[] strArr4, int[] iArr, int i, java.util.Set<java.lang.String>[] setArr, java.util.Set<java.lang.String>[] setArr2) {
        this.mPath = str;
        this.mBaseApkPath = str2;
        this.mPackageName = apkLite.getPackageName();
        this.mVersionCode = apkLite.getVersionCode();
        this.mVersionCodeMajor = apkLite.getVersionCodeMajor();
        this.mInstallLocation = apkLite.getInstallLocation();
        this.mVerifiers = apkLite.getVerifiers();
        this.mSigningDetails = apkLite.getSigningDetails();
        this.mBaseRevisionCode = apkLite.getRevisionCode();
        this.mCoreApp = apkLite.isCoreApp();
        this.mDebuggable = apkLite.isDebuggable();
        this.mMultiArch = apkLite.isMultiArch();
        this.mUse32bitAbi = apkLite.isUse32bitAbi();
        this.mExtractNativeLibs = apkLite.isExtractNativeLibs();
        this.mIsolatedSplits = apkLite.isIsolatedSplits();
        this.mUseEmbeddedDex = apkLite.isUseEmbeddedDex();
        this.mBaseRequiredSplitTypes = apkLite.getRequiredSplitTypes();
        this.mRequiredSplitTypes = setArr;
        this.mSplitRequired = apkLite.isSplitRequired() || hasAnyRequiredSplitTypes();
        this.mProfileableByShell = apkLite.isProfileableByShell();
        this.mIsSdkLibrary = apkLite.isIsSdkLibrary();
        this.mSplitNames = strArr;
        this.mSplitTypes = setArr2;
        this.mIsFeatureSplits = zArr;
        this.mUsesSplitNames = strArr2;
        this.mConfigForSplit = strArr3;
        this.mSplitApkPaths = strArr4;
        this.mSplitRevisionCodes = iArr;
        this.mTargetSdk = i;
        this.mArchivedPackage = apkLite.getArchivedPackage();
    }

    public java.util.List<java.lang.String> getAllApkPaths() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(this.mBaseApkPath);
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.mSplitApkPaths)) {
            java.util.Collections.addAll(arrayList, this.mSplitApkPaths);
        }
        return arrayList;
    }

    public long getLongVersionCode() {
        return android.content.pm.PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
    }

    private boolean hasAnyRequiredSplitTypes() {
        return (com.android.internal.util.CollectionUtils.isEmpty(this.mBaseRequiredSplitTypes) && com.android.internal.util.ArrayUtils.find(this.mRequiredSplitTypes, new java.util.function.Predicate() { // from class: android.content.pm.parsing.PackageLite$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.content.pm.parsing.PackageLite.lambda$hasAnyRequiredSplitTypes$0((java.util.Set) obj);
            }
        }) == null) ? false : true;
    }

    static /* synthetic */ boolean lambda$hasAnyRequiredSplitTypes$0(java.util.Set set) {
        return !com.android.internal.util.CollectionUtils.isEmpty(set);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getPath() {
        return this.mPath;
    }

    public java.lang.String getBaseApkPath() {
        return this.mBaseApkPath;
    }

    public java.lang.String[] getSplitApkPaths() {
        return this.mSplitApkPaths;
    }

    public java.lang.String[] getSplitNames() {
        return this.mSplitNames;
    }

    public java.lang.String[] getUsesSplitNames() {
        return this.mUsesSplitNames;
    }

    public java.lang.String[] getConfigForSplit() {
        return this.mConfigForSplit;
    }

    public java.util.Set<java.lang.String> getBaseRequiredSplitTypes() {
        return this.mBaseRequiredSplitTypes;
    }

    public java.util.Set<java.lang.String>[] getRequiredSplitTypes() {
        return this.mRequiredSplitTypes;
    }

    public java.util.Set<java.lang.String>[] getSplitTypes() {
        return this.mSplitTypes;
    }

    public int getVersionCodeMajor() {
        return this.mVersionCodeMajor;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public int getTargetSdk() {
        return this.mTargetSdk;
    }

    public int getBaseRevisionCode() {
        return this.mBaseRevisionCode;
    }

    public int[] getSplitRevisionCodes() {
        return this.mSplitRevisionCodes;
    }

    public int getInstallLocation() {
        return this.mInstallLocation;
    }

    public android.content.pm.VerifierInfo[] getVerifiers() {
        return this.mVerifiers;
    }

    public android.content.pm.SigningDetails getSigningDetails() {
        return this.mSigningDetails;
    }

    public boolean[] getIsFeatureSplits() {
        return this.mIsFeatureSplits;
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

    public boolean isMultiArch() {
        return this.mMultiArch;
    }

    public boolean isUse32bitAbi() {
        return this.mUse32bitAbi;
    }

    public boolean isExtractNativeLibs() {
        return this.mExtractNativeLibs;
    }

    public boolean isProfileableByShell() {
        return this.mProfileableByShell;
    }

    public boolean isUseEmbeddedDex() {
        return this.mUseEmbeddedDex;
    }

    public boolean isIsSdkLibrary() {
        return this.mIsSdkLibrary;
    }

    public android.content.pm.ArchivedPackageParcel getArchivedPackage() {
        return this.mArchivedPackage;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
