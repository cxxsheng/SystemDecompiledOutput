package com.android.server.pm;

/* loaded from: classes2.dex */
final class InstallRequest {

    @android.annotation.Nullable
    private android.apex.ApexInfo mApexInfo;

    @android.annotation.Nullable
    private java.lang.String mApexModuleName;
    private int mAppId;

    @android.annotation.Nullable
    private android.content.pm.ArchivedPackageParcel mArchivedPackage;
    private boolean mClearCodeCache;
    private int mDexoptStatus;

    @android.annotation.Nullable
    private com.android.server.pm.PackageSetting mDisabledPs;

    @android.annotation.Nullable
    private java.lang.String mExistingPackageName;

    @android.annotation.NonNull
    private int[] mFirstTimeBroadcastInstantUserIds;

    @android.annotation.NonNull
    private int[] mFirstTimeBroadcastUserIds;

    @android.annotation.Nullable
    private com.android.server.pm.PackageFreezer mFreezer;

    @android.annotation.Nullable
    private final com.android.server.pm.InstallArgs mInstallArgs;
    private int mInternalErrorCode;
    private boolean mIsInstallForUsers;
    private boolean mIsInstallInherit;

    @android.annotation.Nullable
    private java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> mLibraryConsumers;

    @android.annotation.Nullable
    private java.lang.String mName;

    @android.annotation.Nullable
    private int[] mNewUsers;

    @android.annotation.Nullable
    private java.lang.String mOrigPackage;

    @android.annotation.Nullable
    private java.lang.String mOrigPermission;

    @android.annotation.Nullable
    private int[] mOrigUsers;

    @android.annotation.Nullable
    private com.android.server.pm.PackageSetting mOriginalPs;

    @android.annotation.Nullable
    private android.content.pm.parsing.PackageLite mPackageLite;

    @android.annotation.Nullable
    private final com.android.server.pm.PackageMetrics mPackageMetrics;
    private int mParseFlags;

    @android.annotation.Nullable
    private com.android.internal.pm.parsing.pkg.ParsedPackage mParsedPackage;

    @android.annotation.Nullable
    private com.android.server.pm.pkg.AndroidPackage mPkg;

    @android.annotation.Nullable
    private java.lang.Runnable mPostInstallRunnable;

    @android.annotation.Nullable
    private android.content.pm.verify.domain.DomainSet mPreVerifiedDomains;

    @android.annotation.Nullable
    private com.android.server.pm.PackageRemovedInfo mRemovedInfo;
    private boolean mReplace;
    private final int mRequireUserAction;
    private int mReturnCode;

    @android.annotation.Nullable
    private java.lang.String mReturnMsg;
    private int mScanFlags;

    @android.annotation.Nullable
    private com.android.server.pm.ScanResult mScanResult;
    private final int mSessionId;
    private boolean mSystem;

    @android.annotation.NonNull
    private int[] mUpdateBroadcastInstantUserIds;

    @android.annotation.NonNull
    private int[] mUpdateBroadcastUserIds;
    private final int mUserId;

    @android.annotation.NonNull
    private final java.util.ArrayList<java.lang.String> mWarnings;

    InstallRequest(com.android.server.pm.InstallingSession installingSession) {
        this.mAppId = -1;
        this.mFirstTimeBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mFirstTimeBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mWarnings = new java.util.ArrayList<>();
        this.mUserId = installingSession.getUser().getIdentifier();
        this.mInstallArgs = new com.android.server.pm.InstallArgs(installingSession.mOriginInfo, installingSession.mMoveInfo, installingSession.mObserver, installingSession.mInstallFlags, installingSession.mDevelopmentInstallFlags, installingSession.mInstallSource, installingSession.mVolumeUuid, installingSession.getUser(), null, installingSession.mPackageAbiOverride, installingSession.mPermissionStates, installingSession.mAllowlistedRestrictedPermissions, installingSession.mAutoRevokePermissionsMode, installingSession.mTraceMethod, installingSession.mTraceCookie, installingSession.mSigningDetails, installingSession.mInstallReason, installingSession.mInstallScenario, installingSession.mForceQueryableOverride, installingSession.mDataLoaderType, installingSession.mPackageSource, installingSession.mApplicationEnabledSettingPersistent);
        this.mPackageLite = installingSession.mPackageLite;
        this.mPackageMetrics = new com.android.server.pm.PackageMetrics(this);
        this.mIsInstallInherit = installingSession.mIsInherit;
        this.mSessionId = installingSession.mSessionId;
        this.mRequireUserAction = installingSession.mRequireUserAction;
        this.mPreVerifiedDomains = installingSession.mPreVerifiedDomains;
    }

    InstallRequest(int i, int i2, com.android.server.pm.pkg.AndroidPackage androidPackage, int[] iArr, java.lang.Runnable runnable) {
        this.mAppId = -1;
        this.mFirstTimeBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mFirstTimeBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mWarnings = new java.util.ArrayList<>();
        this.mUserId = i;
        this.mInstallArgs = null;
        this.mReturnCode = i2;
        this.mPkg = androidPackage;
        this.mNewUsers = iArr;
        this.mPostInstallRunnable = runnable;
        this.mPackageMetrics = new com.android.server.pm.PackageMetrics(this);
        this.mIsInstallForUsers = true;
        this.mSessionId = -1;
        this.mRequireUserAction = 0;
    }

    InstallRequest(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, int i, int i2, @android.annotation.Nullable android.os.UserHandle userHandle, com.android.server.pm.ScanResult scanResult, com.android.server.pm.PackageSetting packageSetting) {
        this.mAppId = -1;
        this.mFirstTimeBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mFirstTimeBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mWarnings = new java.util.ArrayList<>();
        if (userHandle != null) {
            this.mUserId = userHandle.getIdentifier();
        } else {
            this.mUserId = 0;
        }
        this.mInstallArgs = null;
        this.mParsedPackage = parsedPackage;
        this.mArchivedPackage = null;
        this.mParseFlags = i;
        this.mScanFlags = i2;
        this.mScanResult = scanResult;
        this.mPackageMetrics = null;
        this.mSessionId = -1;
        this.mRequireUserAction = 0;
        this.mDisabledPs = packageSetting;
    }

    @android.annotation.Nullable
    public java.lang.String getName() {
        return this.mName;
    }

    @android.annotation.Nullable
    public java.lang.String getReturnMsg() {
        return this.mReturnMsg;
    }

    @android.annotation.Nullable
    public com.android.server.pm.OriginInfo getOriginInfo() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mOriginInfo;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageRemovedInfo getRemovedInfo() {
        return this.mRemovedInfo;
    }

    @android.annotation.Nullable
    public java.lang.String getOrigPackage() {
        return this.mOrigPackage;
    }

    @android.annotation.Nullable
    public java.lang.String getOrigPermission() {
        return this.mOrigPermission;
    }

    @android.annotation.Nullable
    public java.io.File getCodeFile() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mCodeFile;
    }

    @android.annotation.Nullable
    public java.lang.String getCodePath() {
        if (this.mInstallArgs == null || this.mInstallArgs.mCodeFile == null) {
            return null;
        }
        return this.mInstallArgs.mCodeFile.getAbsolutePath();
    }

    @android.annotation.Nullable
    public java.lang.String getAbiOverride() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mAbiOverride;
    }

    public int getReturnCode() {
        return this.mReturnCode;
    }

    public int getInternalErrorCode() {
        return this.mInternalErrorCode;
    }

    @android.annotation.Nullable
    public android.content.pm.IPackageInstallObserver2 getObserver() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mObserver;
    }

    public boolean isInstallMove() {
        return (this.mInstallArgs == null || this.mInstallArgs.mMoveInfo == null) ? false : true;
    }

    @android.annotation.Nullable
    public java.lang.String getMoveToUuid() {
        if (this.mInstallArgs == null || this.mInstallArgs.mMoveInfo == null) {
            return null;
        }
        return this.mInstallArgs.mMoveInfo.mToUuid;
    }

    @android.annotation.Nullable
    public java.lang.String getMovePackageName() {
        if (this.mInstallArgs == null || this.mInstallArgs.mMoveInfo == null) {
            return null;
        }
        return this.mInstallArgs.mMoveInfo.mPackageName;
    }

    @android.annotation.Nullable
    public java.lang.String getMoveFromCodePath() {
        if (this.mInstallArgs == null || this.mInstallArgs.mMoveInfo == null) {
            return null;
        }
        return this.mInstallArgs.mMoveInfo.mFromCodePath;
    }

    @android.annotation.Nullable
    public java.io.File getOldCodeFile() {
        if (this.mRemovedInfo == null || this.mRemovedInfo.mArgs == null) {
            return null;
        }
        return this.mRemovedInfo.mArgs.getCodeFile();
    }

    @android.annotation.Nullable
    public java.lang.String[] getOldInstructionSet() {
        if (this.mRemovedInfo == null || this.mRemovedInfo.mArgs == null) {
            return null;
        }
        return this.mRemovedInfo.mArgs.getInstructionSets();
    }

    public android.os.UserHandle getUser() {
        return new android.os.UserHandle(this.mUserId);
    }

    public int getUserId() {
        return this.mUserId;
    }

    public int getInstallFlags() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mInstallFlags;
    }

    public int getDevelopmentInstallFlags() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mDevelopmentInstallFlags;
    }

    public int getInstallReason() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mInstallReason;
    }

    @android.annotation.Nullable
    public java.lang.String getVolumeUuid() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mVolumeUuid;
    }

    @android.annotation.Nullable
    public com.android.server.pm.pkg.AndroidPackage getPkg() {
        return this.mPkg;
    }

    @android.annotation.Nullable
    public android.content.pm.parsing.PackageLite getPackageLite() {
        return this.mPackageLite;
    }

    @android.annotation.Nullable
    public java.lang.String getTraceMethod() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mTraceMethod;
    }

    public int getTraceCookie() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mTraceCookie;
    }

    public boolean isUpdate() {
        return (this.mRemovedInfo == null || this.mRemovedInfo.mRemovedPackage == null) ? false : true;
    }

    public boolean isArchived() {
        return com.android.server.pm.PackageInstallerSession.isArchivedInstallation(getInstallFlags());
    }

    @android.annotation.Nullable
    public java.lang.String getRemovedPackage() {
        if (this.mRemovedInfo != null) {
            return this.mRemovedInfo.mRemovedPackage;
        }
        return null;
    }

    public boolean isInstallExistingForUser() {
        return this.mInstallArgs == null;
    }

    @android.annotation.Nullable
    public com.android.server.pm.InstallSource getInstallSource() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mInstallSource;
    }

    @android.annotation.Nullable
    public java.lang.String getInstallerPackageName() {
        if (this.mInstallArgs == null || this.mInstallArgs.mInstallSource == null) {
            return null;
        }
        return this.mInstallArgs.mInstallSource.mInstallerPackageName;
    }

    public int getInstallerPackageUid() {
        if (this.mInstallArgs == null || this.mInstallArgs.mInstallSource == null) {
            return -1;
        }
        return this.mInstallArgs.mInstallSource.mInstallerPackageUid;
    }

    public int getDataLoaderType() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mDataLoaderType;
    }

    public int getSignatureSchemeVersion() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mSigningDetails.getSignatureSchemeVersion();
    }

    @android.annotation.NonNull
    public android.content.pm.SigningDetails getSigningDetails() {
        return this.mInstallArgs == null ? android.content.pm.SigningDetails.UNKNOWN : this.mInstallArgs.mSigningDetails;
    }

    @android.annotation.Nullable
    public android.net.Uri getOriginUri() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return android.net.Uri.fromFile(this.mInstallArgs.mOriginInfo.mResolvedFile);
    }

    @android.annotation.Nullable
    public android.apex.ApexInfo getApexInfo() {
        return this.mApexInfo;
    }

    @android.annotation.Nullable
    public java.lang.String getApexModuleName() {
        return this.mApexModuleName;
    }

    public boolean isRollback() {
        return this.mInstallArgs != null && this.mInstallArgs.mInstallReason == 5;
    }

    @android.annotation.Nullable
    public int[] getNewUsers() {
        return this.mNewUsers;
    }

    @android.annotation.Nullable
    public int[] getOriginUsers() {
        return this.mOrigUsers;
    }

    public int getAppId() {
        return this.mAppId;
    }

    @android.annotation.Nullable
    public android.util.ArrayMap<java.lang.String, java.lang.Integer> getPermissionStates() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mPermissionStates;
    }

    @android.annotation.Nullable
    public java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> getLibraryConsumers() {
        return this.mLibraryConsumers;
    }

    @android.annotation.Nullable
    public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions() {
        if (this.mInstallArgs == null) {
            return null;
        }
        return this.mInstallArgs.mAllowlistedRestrictedPermissions;
    }

    public int getAutoRevokePermissionsMode() {
        if (this.mInstallArgs == null) {
            return 3;
        }
        return this.mInstallArgs.mAutoRevokePermissionsMode;
    }

    public int getPackageSource() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mPackageSource;
    }

    public int getInstallScenario() {
        if (this.mInstallArgs == null) {
            return 0;
        }
        return this.mInstallArgs.mInstallScenario;
    }

    @android.annotation.Nullable
    public com.android.internal.pm.parsing.pkg.ParsedPackage getParsedPackage() {
        return this.mParsedPackage;
    }

    @android.annotation.Nullable
    public android.content.pm.ArchivedPackageParcel getArchivedPackage() {
        return this.mArchivedPackage;
    }

    public int getParseFlags() {
        return this.mParseFlags;
    }

    public int getScanFlags() {
        return this.mScanFlags;
    }

    @android.annotation.Nullable
    public java.lang.String getExistingPackageName() {
        return this.mExistingPackageName;
    }

    @android.annotation.Nullable
    public com.android.server.pm.pkg.AndroidPackage getScanRequestOldPackage() {
        assertScanResultExists();
        return this.mScanResult.mRequest.mOldPkg;
    }

    public boolean isClearCodeCache() {
        return this.mClearCodeCache;
    }

    public boolean isInstallReplace() {
        return this.mReplace;
    }

    public boolean isInstallSystem() {
        return this.mSystem;
    }

    public boolean isInstallInherit() {
        return this.mIsInstallInherit;
    }

    public boolean isInstallForUsers() {
        return this.mIsInstallForUsers;
    }

    public boolean isInstallFromAdb() {
        return (this.mInstallArgs == null || (this.mInstallArgs.mInstallFlags & 32) == 0) ? false : true;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getOriginalPackageSetting() {
        return this.mOriginalPs;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getDisabledPackageSetting() {
        return this.mDisabledPs;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getScanRequestOldPackageSetting() {
        assertScanResultExists();
        return this.mScanResult.mRequest.mOldPkgSetting;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getScanRequestOriginalPackageSetting() {
        assertScanResultExists();
        return this.mScanResult.mRequest.mOriginalPkgSetting;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getScanRequestPackageSetting() {
        assertScanResultExists();
        return this.mScanResult.mRequest.mPkgSetting;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getScanRequestDisabledPackageSetting() {
        assertScanResultExists();
        return this.mScanResult.mRequest.mDisabledPkgSetting;
    }

    @android.annotation.Nullable
    public java.lang.String getRealPackageName() {
        assertScanResultExists();
        return this.mScanResult.mRequest.mRealPkgName;
    }

    @android.annotation.Nullable
    public java.util.List<java.lang.String> getChangedAbiCodePath() {
        assertScanResultExists();
        return this.mScanResult.mChangedAbiCodePath;
    }

    public boolean isApplicationEnabledSettingPersistent() {
        if (this.mInstallArgs == null) {
            return false;
        }
        return this.mInstallArgs.mApplicationEnabledSettingPersistent;
    }

    public boolean isForceQueryableOverride() {
        return this.mInstallArgs != null && this.mInstallArgs.mForceQueryableOverride;
    }

    @android.annotation.Nullable
    public android.content.pm.SharedLibraryInfo getSdkSharedLibraryInfo() {
        assertScanResultExists();
        return this.mScanResult.mSdkSharedLibraryInfo;
    }

    @android.annotation.Nullable
    public android.content.pm.SharedLibraryInfo getStaticSharedLibraryInfo() {
        assertScanResultExists();
        return this.mScanResult.mStaticSharedLibraryInfo;
    }

    @android.annotation.Nullable
    public java.util.List<android.content.pm.SharedLibraryInfo> getDynamicSharedLibraryInfos() {
        assertScanResultExists();
        return this.mScanResult.mDynamicSharedLibraryInfos;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getScannedPackageSetting() {
        assertScanResultExists();
        return this.mScanResult.mPkgSetting;
    }

    @android.annotation.Nullable
    public com.android.server.pm.PackageSetting getRealPackageSetting() {
        com.android.server.pm.PackageSetting scanRequestPackageSetting = isExistingSettingCopied() ? getScanRequestPackageSetting() : getScannedPackageSetting();
        if (scanRequestPackageSetting == null) {
            return getScannedPackageSetting();
        }
        return scanRequestPackageSetting;
    }

    public boolean isExistingSettingCopied() {
        assertScanResultExists();
        return this.mScanResult.mExistingSettingCopied;
    }

    public boolean needsNewAppId() {
        assertScanResultExists();
        return this.mScanResult.mPreviousAppId != -1;
    }

    public int getPreviousAppId() {
        assertScanResultExists();
        return this.mScanResult.mPreviousAppId;
    }

    public boolean isPlatformPackage() {
        assertScanResultExists();
        return this.mScanResult.mRequest.mIsPlatformPackage;
    }

    public boolean isInstantInstall() {
        return (this.mScanFlags & 8192) != 0;
    }

    public void assertScanResultExists() {
        if (this.mScanResult == null) {
            if (android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) {
                throw new java.lang.IllegalStateException("ScanResult cannot be null.");
            }
            android.util.Slog.e("PackageManager", "ScanResult is null and it should not happen");
        }
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getRequireUserAction() {
        return this.mRequireUserAction;
    }

    public int getDexoptStatus() {
        return this.mDexoptStatus;
    }

    public boolean isAllNewUsers() {
        return this.mOrigUsers == null || this.mOrigUsers.length == 0;
    }

    public int[] getFirstTimeBroadcastUserIds() {
        return this.mFirstTimeBroadcastUserIds;
    }

    public int[] getFirstTimeBroadcastInstantUserIds() {
        return this.mFirstTimeBroadcastInstantUserIds;
    }

    public int[] getUpdateBroadcastUserIds() {
        return this.mUpdateBroadcastUserIds;
    }

    public int[] getUpdateBroadcastInstantUserIds() {
        return this.mUpdateBroadcastInstantUserIds;
    }

    @android.annotation.NonNull
    public java.util.ArrayList<java.lang.String> getWarnings() {
        return this.mWarnings;
    }

    public void setScanFlags(int i) {
        this.mScanFlags = i;
    }

    public void closeFreezer() {
        if (this.mFreezer != null) {
            this.mFreezer.close();
        }
    }

    public void setPostInstallRunnable(java.lang.Runnable runnable) {
        this.mPostInstallRunnable = runnable;
    }

    public boolean hasPostInstallRunnable() {
        return this.mPostInstallRunnable != null;
    }

    public void runPostInstallRunnable() {
        if (this.mPostInstallRunnable != null) {
            this.mPostInstallRunnable.run();
        }
    }

    public void setCodeFile(java.io.File file) {
        if (this.mInstallArgs != null) {
            this.mInstallArgs.mCodeFile = file;
        }
    }

    public void setError(int i, java.lang.String str) {
        setReturnCode(i);
        setReturnMessage(str);
        android.util.Slog.w("PackageManager", str);
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onInstallFailed();
        }
    }

    public void setError(com.android.server.pm.PackageManagerException packageManagerException) {
        setError((java.lang.String) null, packageManagerException);
    }

    public void setError(java.lang.String str, com.android.server.pm.PackageManagerException packageManagerException) {
        this.mInternalErrorCode = packageManagerException.internalErrorCode;
        this.mReturnCode = packageManagerException.error;
        setReturnMessage(android.util.ExceptionUtils.getCompleteMessage(str, packageManagerException));
        android.util.Slog.w("PackageManager", str, packageManagerException);
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onInstallFailed();
        }
    }

    public void setReturnCode(int i) {
        this.mReturnCode = i;
    }

    public void setReturnMessage(java.lang.String str) {
        this.mReturnMsg = str;
    }

    public void setApexInfo(android.apex.ApexInfo apexInfo) {
        this.mApexInfo = apexInfo;
    }

    public void setApexModuleName(@android.annotation.Nullable java.lang.String str) {
        this.mApexModuleName = str;
    }

    public void setPkg(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        this.mPkg = androidPackage;
    }

    public void setAppId(int i) {
        this.mAppId = i;
    }

    public void setNewUsers(int[] iArr) {
        this.mNewUsers = iArr;
        populateBroadcastUsers();
    }

    public void setOriginPackage(java.lang.String str) {
        this.mOrigPackage = str;
    }

    public void setOriginPermission(java.lang.String str) {
        this.mOrigPermission = str;
    }

    public void setName(java.lang.String str) {
        this.mName = str;
    }

    public void setOriginUsers(int[] iArr) {
        this.mOrigUsers = iArr;
    }

    public void setFreezer(com.android.server.pm.PackageFreezer packageFreezer) {
        this.mFreezer = packageFreezer;
    }

    public void setRemovedInfo(com.android.server.pm.PackageRemovedInfo packageRemovedInfo) {
        this.mRemovedInfo = packageRemovedInfo;
    }

    public void setLibraryConsumers(java.util.ArrayList<com.android.server.pm.pkg.AndroidPackage> arrayList) {
        this.mLibraryConsumers = arrayList;
    }

    public void setPrepareResult(boolean z, int i, int i2, com.android.server.pm.pkg.PackageState packageState, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, android.content.pm.ArchivedPackageParcel archivedPackageParcel, boolean z2, boolean z3, com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.PackageSetting packageSetting2) {
        this.mReplace = z;
        this.mScanFlags = i;
        this.mParseFlags = i2;
        this.mExistingPackageName = packageState != null ? packageState.getPackageName() : null;
        this.mParsedPackage = parsedPackage;
        this.mArchivedPackage = archivedPackageParcel;
        this.mClearCodeCache = z2;
        this.mSystem = z3;
        this.mOriginalPs = packageSetting;
        this.mDisabledPs = packageSetting2;
    }

    public void setScanResult(@android.annotation.NonNull com.android.server.pm.ScanResult scanResult) {
        this.mScanResult = scanResult;
    }

    public void setScannedPackageSettingAppId(int i) {
        assertScanResultExists();
        this.mScanResult.mPkgSetting.setAppId(i);
    }

    public void setScannedPackageSettingFirstInstallTimeFromReplaced(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int[] iArr) {
        assertScanResultExists();
        this.mScanResult.mPkgSetting.setFirstInstallTimeFromReplaced(packageStateInternal, iArr);
    }

    public void setScannedPackageSettingLastUpdateTime(long j) {
        assertScanResultExists();
        this.mScanResult.mPkgSetting.setLastUpdateTime(j);
    }

    public void setRemovedAppId(int i) {
        if (this.mRemovedInfo != null) {
            this.mRemovedInfo.mUid = i;
            this.mRemovedInfo.mIsAppIdRemoved = true;
        }
    }

    private void populateBroadcastUsers() {
        assertScanResultExists();
        this.mFirstTimeBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mFirstTimeBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        this.mUpdateBroadcastInstantUserIds = com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY;
        int i = 0;
        if (isAllNewUsers()) {
            int[] iArr = this.mNewUsers;
            int length = iArr.length;
            while (i < length) {
                int i2 = iArr[i];
                if (this.mScanResult.mPkgSetting.getUserStateOrDefault(i2).isInstantApp()) {
                    this.mFirstTimeBroadcastInstantUserIds = com.android.internal.util.ArrayUtils.appendInt(this.mFirstTimeBroadcastInstantUserIds, i2);
                } else {
                    this.mFirstTimeBroadcastUserIds = com.android.internal.util.ArrayUtils.appendInt(this.mFirstTimeBroadcastUserIds, i2);
                }
                i++;
            }
            return;
        }
        int[] iArr2 = this.mNewUsers;
        int length2 = iArr2.length;
        while (i < length2) {
            int i3 = iArr2[i];
            boolean z = !com.android.internal.util.ArrayUtils.contains(this.mOrigUsers, i3);
            boolean isInstantApp = this.mScanResult.mPkgSetting.getUserStateOrDefault(i3).isInstantApp();
            if (z) {
                if (isInstantApp) {
                    this.mFirstTimeBroadcastInstantUserIds = com.android.internal.util.ArrayUtils.appendInt(this.mFirstTimeBroadcastInstantUserIds, i3);
                } else {
                    this.mFirstTimeBroadcastUserIds = com.android.internal.util.ArrayUtils.appendInt(this.mFirstTimeBroadcastUserIds, i3);
                }
            } else if (isInstantApp) {
                this.mUpdateBroadcastInstantUserIds = com.android.internal.util.ArrayUtils.appendInt(this.mUpdateBroadcastInstantUserIds, i3);
            } else {
                this.mUpdateBroadcastUserIds = com.android.internal.util.ArrayUtils.appendInt(this.mUpdateBroadcastUserIds, i3);
            }
            i++;
        }
    }

    @android.annotation.Nullable
    public android.content.pm.verify.domain.DomainSet getPreVerifiedDomains() {
        return this.mPreVerifiedDomains;
    }

    public void addWarning(@android.annotation.NonNull java.lang.String str) {
        this.mWarnings.add(str);
    }

    public void onPrepareStarted() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepStarted(1);
        }
    }

    public void onPrepareFinished() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepFinished(1);
        }
    }

    public void onScanStarted() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepStarted(2);
        }
    }

    public void onScanFinished() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepFinished(2);
        }
    }

    public void onReconcileStarted() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepStarted(3);
        }
    }

    public void onReconcileFinished() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepFinished(3);
        }
    }

    public void onCommitStarted() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepStarted(4);
        }
    }

    public void onCommitFinished() {
        if (this.mPackageMetrics != null) {
            this.mPackageMetrics.onStepFinished(4);
        }
    }

    public void onDexoptFinished(com.android.server.art.model.DexoptResult dexoptResult) {
        if (isInstallFromAdb() && android.content.pm.Flags.useArtServiceV2()) {
            java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
            java.util.Iterator it = dexoptResult.getPackageDexoptResults().iterator();
            while (it.hasNext()) {
                java.util.Iterator it2 = ((com.android.server.art.model.DexoptResult.PackageDexoptResult) it.next()).getDexContainerFileDexoptResults().iterator();
                while (it2.hasNext()) {
                    linkedHashSet.addAll(((com.android.server.art.model.DexoptResult.DexContainerFileDexoptResult) it2.next()).getExternalProfileErrors());
                }
            }
            if (!linkedHashSet.isEmpty()) {
                addWarning("Error occurred during dexopt when processing external profiles:\n  " + java.lang.String.join("\n  ", linkedHashSet));
            }
        }
        if (this.mPackageMetrics != null) {
            this.mDexoptStatus = dexoptResult.getFinalStatus();
            if (this.mDexoptStatus == 20) {
                java.util.Iterator it3 = dexoptResult.getPackageDexoptResults().iterator();
                long j = 0;
                while (it3.hasNext()) {
                    java.util.Iterator it4 = ((com.android.server.art.model.DexoptResult.PackageDexoptResult) it3.next()).getDexContainerFileDexoptResults().iterator();
                    while (it4.hasNext()) {
                        j += ((com.android.server.art.model.DexoptResult.DexContainerFileDexoptResult) it4.next()).getDex2oatWallTimeMillis();
                    }
                }
                this.mPackageMetrics.onStepFinished(5, j);
            }
        }
    }

    public void onInstallCompleted() {
        if (getReturnCode() == 1 && this.mPackageMetrics != null) {
            this.mPackageMetrics.onInstallSucceed();
        }
    }

    public void onFreezeStarted() {
        if (this.mPackageMetrics != null && android.content.pm.Flags.improveInstallFreeze()) {
            this.mPackageMetrics.onStepStarted(6);
        }
    }

    public void onFreezeCompleted() {
        if (this.mPackageMetrics != null && android.content.pm.Flags.improveInstallFreeze()) {
            this.mPackageMetrics.onStepFinished(6);
        }
    }
}
