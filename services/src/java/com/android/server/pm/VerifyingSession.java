package com.android.server.pm;

/* loaded from: classes2.dex */
final class VerifyingSession {
    private static final long DEFAULT_ENABLE_ROLLBACK_TIMEOUT_MILLIS = 10000;
    private static final long DEFAULT_INTEGRITY_VERIFICATION_TIMEOUT = 30000;
    private static final boolean DEFAULT_INTEGRITY_VERIFY_ENABLE = true;
    private static final boolean DEFAULT_VERIFY_ENABLE = true;
    private static final java.lang.String PROPERTY_ENABLE_ROLLBACK_TIMEOUT_MILLIS = "enable_rollback_timeout";
    private final int mDataLoaderType;
    private final int mInstallFlags;

    @android.annotation.NonNull
    private final com.android.server.pm.InstallSource mInstallSource;
    private final boolean mIsInherit;
    private final boolean mIsStaged;
    final android.content.pm.IPackageInstallObserver2 mObserver;
    final com.android.server.pm.OriginInfo mOriginInfo;
    private final java.lang.String mPackageAbiOverride;
    private final android.content.pm.parsing.PackageLite mPackageLite;

    @android.annotation.Nullable
    com.android.server.pm.MultiPackageVerifyingSession mParentVerifyingSession;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageManagerService mPm;
    private final long mRequiredInstalledVersionCode;
    private final int mSessionId;
    private final android.content.pm.SigningDetails mSigningDetails;
    private final android.os.UserHandle mUser;
    private final boolean mUserActionRequired;
    private final int mUserActionRequiredType;
    private final com.android.server.pm.VerificationInfo mVerificationInfo;
    private boolean mWaitForEnableRollbackToComplete;
    private boolean mWaitForIntegrityVerificationToComplete;
    private boolean mWaitForVerificationToComplete;
    private int mRet = 1;
    private java.lang.String mErrorMessage = null;

    VerifyingSession(android.os.UserHandle userHandle, java.io.File file, android.content.pm.IPackageInstallObserver2 iPackageInstallObserver2, android.content.pm.PackageInstaller.SessionParams sessionParams, com.android.server.pm.InstallSource installSource, int i, android.content.pm.SigningDetails signingDetails, int i2, android.content.pm.parsing.PackageLite packageLite, boolean z, com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        this.mUser = userHandle;
        this.mOriginInfo = com.android.server.pm.OriginInfo.fromStagedFile(file);
        this.mObserver = iPackageInstallObserver2;
        this.mInstallFlags = sessionParams.installFlags;
        this.mInstallSource = installSource;
        this.mPackageAbiOverride = sessionParams.abiOverride;
        this.mVerificationInfo = new com.android.server.pm.VerificationInfo(sessionParams.originatingUri, sessionParams.referrerUri, sessionParams.originatingUid, i);
        this.mSigningDetails = signingDetails;
        this.mRequiredInstalledVersionCode = sessionParams.requiredInstalledVersionCode;
        this.mDataLoaderType = sessionParams.dataLoaderParams != null ? sessionParams.dataLoaderParams.getType() : 0;
        this.mSessionId = i2;
        this.mPackageLite = packageLite;
        this.mUserActionRequired = z;
        this.mUserActionRequiredType = sessionParams.requireUserAction;
        this.mIsInherit = sessionParams.mode == 2;
        this.mIsStaged = sessionParams.isStaged;
    }

    public java.lang.String toString() {
        return "VerifyingSession{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " file=" + this.mOriginInfo.mFile + "}";
    }

    public void handleStartVerify() {
        android.content.pm.PackageInfoLite minimalPackageInfo = com.android.server.pm.PackageManagerServiceUtils.getMinimalPackageInfo(this.mPm.mContext, this.mPackageLite, this.mOriginInfo.mResolvedPath, this.mInstallFlags, this.mPackageAbiOverride);
        android.util.Pair<java.lang.Integer, java.lang.String> verifyReplacingVersionCode = this.mPm.verifyReplacingVersionCode(minimalPackageInfo, this.mRequiredInstalledVersionCode, this.mInstallFlags);
        setReturnCode(((java.lang.Integer) verifyReplacingVersionCode.first).intValue(), (java.lang.String) verifyReplacingVersionCode.second);
        if (this.mRet == 1 && !this.mOriginInfo.mExisting) {
            if (!isApex() && !isArchivedInstallation()) {
                sendApkVerificationRequest(minimalPackageInfo);
            }
            if ((this.mInstallFlags & 262144) != 0) {
                sendEnableRollbackRequest();
            }
        }
    }

    private void sendApkVerificationRequest(android.content.pm.PackageInfoLite packageInfoLite) {
        com.android.server.pm.PackageManagerService packageManagerService = this.mPm;
        int i = packageManagerService.mPendingVerificationToken;
        packageManagerService.mPendingVerificationToken = i + 1;
        com.android.server.pm.PackageVerificationState packageVerificationState = new com.android.server.pm.PackageVerificationState(this);
        this.mPm.mPendingVerification.append(i, packageVerificationState);
        sendIntegrityVerificationRequest(i, packageInfoLite, packageVerificationState);
        sendPackageVerificationRequest(i, packageInfoLite, packageVerificationState);
        if (packageVerificationState.areAllVerificationsComplete()) {
            this.mPm.mPendingVerification.remove(i);
        }
    }

    void sendEnableRollbackRequest() {
        com.android.server.pm.PackageManagerService packageManagerService = this.mPm;
        int i = packageManagerService.mPendingEnableRollbackToken;
        packageManagerService.mPendingEnableRollbackToken = i + 1;
        android.os.Trace.asyncTraceBegin(262144L, "enable_rollback", i);
        this.mPm.mPendingEnableRollback.append(i, this);
        android.content.Intent intent = new android.content.Intent("android.intent.action.PACKAGE_ENABLE_ROLLBACK");
        intent.putExtra(android.content.pm.PackageManagerInternal.EXTRA_ENABLE_ROLLBACK_TOKEN, i);
        intent.putExtra(android.content.pm.PackageManagerInternal.EXTRA_ENABLE_ROLLBACK_SESSION_ID, this.mSessionId);
        intent.setType("application/vnd.android.package-archive");
        intent.addFlags(268435457);
        intent.addFlags(67108864);
        this.mPm.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.SYSTEM, "android.permission.PACKAGE_ROLLBACK_AGENT");
        this.mWaitForEnableRollbackToComplete = true;
        long j = android.provider.DeviceConfig.getLong("rollback", PROPERTY_ENABLE_ROLLBACK_TIMEOUT_MILLIS, 10000L);
        long j2 = j >= 0 ? j : 10000L;
        android.os.Message obtainMessage = this.mPm.mHandler.obtainMessage(22);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = this.mSessionId;
        this.mPm.mHandler.sendMessageDelayed(obtainMessage, j2);
    }

    void sendIntegrityVerificationRequest(final int i, android.content.pm.PackageInfoLite packageInfoLite, com.android.server.pm.PackageVerificationState packageVerificationState) {
        if (!isIntegrityVerificationEnabled()) {
            packageVerificationState.setIntegrityVerificationResult(1);
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION");
        intent.setDataAndType(android.net.Uri.fromFile(new java.io.File(this.mOriginInfo.mResolvedPath)), "application/vnd.android.package-archive");
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT);
        intent.putExtra("android.content.pm.extra.VERIFICATION_ID", i);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", packageInfoLite.packageName);
        intent.putExtra("android.intent.extra.VERSION_CODE", packageInfoLite.versionCode);
        intent.putExtra("android.intent.extra.LONG_VERSION_CODE", packageInfoLite.getLongVersionCode());
        populateInstallerExtras(intent);
        intent.setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        this.mPm.mContext.sendOrderedBroadcastAsUser(intent, android.os.UserHandle.SYSTEM, null, -1, android.app.BroadcastOptions.makeBasic().toBundle(), new android.content.BroadcastReceiver() { // from class: com.android.server.pm.VerifyingSession.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent2) {
                android.os.Message obtainMessage = com.android.server.pm.VerifyingSession.this.mPm.mHandler.obtainMessage(26);
                obtainMessage.arg1 = i;
                com.android.server.pm.VerifyingSession.this.mPm.mHandler.sendMessageDelayed(obtainMessage, com.android.server.pm.VerifyingSession.this.getIntegrityVerificationTimeout());
            }
        }, null, 0, null, null);
        android.os.Trace.asyncTraceBegin(262144L, "integrity_verification", i);
        this.mWaitForIntegrityVerificationToComplete = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getIntegrityVerificationTimeout() {
        return java.lang.Math.max(android.provider.Settings.Global.getLong(this.mPm.mContext.getContentResolver(), "app_integrity_verification_timeout", 30000L), 30000L);
    }

    private boolean isIntegrityVerificationEnabled() {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x0290  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendPackageVerificationRequest(int i, android.content.pm.PackageInfoLite packageInfoLite, com.android.server.pm.PackageVerificationState packageVerificationState) {
        android.os.UserHandle userHandle;
        java.util.ArrayList arrayList;
        boolean z;
        java.lang.String str;
        boolean z2;
        java.lang.String str2;
        java.util.ArrayList arrayList2;
        int i2;
        java.util.ArrayList arrayList3;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        android.content.Intent intent;
        java.lang.String str6;
        android.os.UserHandle user = getUser();
        if (user == android.os.UserHandle.ALL) {
            user = android.os.UserHandle.of(this.mPm.mUserManager.getCurrentUserId());
        }
        if (!packageInfoLite.isSdkLibrary) {
            userHandle = user;
        } else {
            userHandle = android.os.UserHandle.SYSTEM;
        }
        int identifier = userHandle.getIdentifier();
        java.util.ArrayList arrayList4 = new java.util.ArrayList(java.util.Arrays.asList(this.mPm.mRequiredVerifierPackages));
        if ((this.mInstallFlags & 32) != 0 && (this.mInstallFlags & 524288) == 0) {
            java.lang.String str7 = android.os.SystemProperties.get("debug.pm.adb_verifier_override_packages", "");
            if (!android.text.TextUtils.isEmpty(str7)) {
                java.lang.String[] split = str7.split(";");
                java.util.ArrayList arrayList5 = new java.util.ArrayList();
                for (java.lang.String str8 : split) {
                    if (!android.text.TextUtils.isEmpty(str8) && packageExists(str8)) {
                        arrayList5.add(str8);
                    }
                }
                if (arrayList5.size() > 0 && !isAdbVerificationEnabled(packageInfoLite, identifier, true)) {
                    arrayList = arrayList5;
                    z = true;
                    if (!this.mOriginInfo.mExisting || !isVerificationEnabled(packageInfoLite, identifier, arrayList)) {
                        packageVerificationState.passRequiredVerification();
                    }
                    com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        if (!snapshotComputer.isApplicationEffectivelyEnabled(arrayList.get(size), userHandle)) {
                            android.util.Slog.w("PackageManager", "Required verifier: " + arrayList.get(size) + " is disabled");
                            arrayList.remove(size);
                        }
                    }
                    java.util.Iterator<java.lang.String> it = arrayList.iterator();
                    while (it.hasNext()) {
                        packageVerificationState.addRequiredVerifierUid(snapshotComputer.getPackageUid(it.next(), 268435456L, identifier));
                    }
                    android.content.Intent intent2 = new android.content.Intent("android.intent.action.PACKAGE_NEEDS_VERIFICATION");
                    intent2.addFlags(268435456);
                    intent2.setDataAndType(android.net.Uri.fromFile(new java.io.File(this.mOriginInfo.mResolvedPath)), "application/vnd.android.package-archive");
                    intent2.addFlags(1);
                    java.lang.String str9 = "application/vnd.android.package-archive";
                    com.android.server.pm.Computer computer = snapshotComputer;
                    java.lang.String str10 = "android.intent.action.PACKAGE_NEEDS_VERIFICATION";
                    android.content.Intent intent3 = intent2;
                    java.util.ArrayList arrayList6 = arrayList;
                    android.content.pm.ParceledListSlice<android.content.pm.ResolveInfo> queryIntentReceivers = this.mPm.queryIntentReceivers(snapshotComputer, intent2, "application/vnd.android.package-archive", 0L, identifier);
                    intent3.putExtra("android.content.pm.extra.VERIFICATION_ID", i);
                    intent3.putExtra("android.content.pm.extra.VERIFICATION_INSTALL_FLAGS", this.mInstallFlags);
                    intent3.putExtra("android.content.pm.extra.VERIFICATION_PACKAGE_NAME", packageInfoLite.packageName);
                    intent3.putExtra("android.content.pm.extra.VERIFICATION_VERSION_CODE", packageInfoLite.versionCode);
                    intent3.putExtra("android.content.pm.extra.VERIFICATION_LONG_VERSION_CODE", packageInfoLite.getLongVersionCode());
                    java.lang.String baseApkPath = this.mPackageLite.getBaseApkPath();
                    java.lang.String[] splitApkPaths = this.mPackageLite.getSplitApkPaths();
                    java.lang.String str11 = "android.content.pm.extra.VERIFICATION_ROOT_HASH";
                    if (android.os.incremental.IncrementalManager.isIncrementalPath(baseApkPath)) {
                        java.lang.String buildVerificationRootHashString = com.android.server.pm.PackageManagerServiceUtils.buildVerificationRootHashString(baseApkPath, splitApkPaths);
                        intent3.putExtra("android.content.pm.extra.VERIFICATION_ROOT_HASH", buildVerificationRootHashString);
                        str = buildVerificationRootHashString;
                    } else {
                        str = null;
                    }
                    java.lang.String str12 = "android.content.pm.extra.DATA_LOADER_TYPE";
                    intent3.putExtra("android.content.pm.extra.DATA_LOADER_TYPE", this.mDataLoaderType);
                    java.lang.String str13 = "android.content.pm.extra.SESSION_ID";
                    intent3.putExtra("android.content.pm.extra.SESSION_ID", this.mSessionId);
                    intent3.putExtra("android.content.pm.extra.USER_ACTION_REQUIRED", this.mUserActionRequired);
                    populateInstallerExtras(intent3);
                    boolean z3 = this.mDataLoaderType == 2 && this.mSigningDetails.getSignatureSchemeVersion() == 4 && getDefaultVerificationResponse() == 1;
                    long verificationTimeout = com.android.server.pm.VerificationUtils.getVerificationTimeout(this.mPm.mContext, z3);
                    java.util.List<android.content.ComponentName> matchVerifiers = matchVerifiers(packageInfoLite, queryIntentReceivers.getList(), packageVerificationState);
                    if (!packageInfoLite.isSdkLibrary) {
                        z2 = z3;
                        str2 = "android.content.pm.extra.VERIFICATION_ID";
                    } else {
                        if (matchVerifiers == null) {
                            matchVerifiers = new java.util.ArrayList<>();
                        }
                        z2 = z3;
                        str2 = "android.content.pm.extra.VERIFICATION_ID";
                        matchVerifiers.add(new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, "com.android.server.sdksandbox.SdkSandboxVerifierReceiver"));
                        packageVerificationState.addSufficientVerifier(android.os.Process.myUid());
                    }
                    com.android.server.DeviceIdleInternal deviceIdleInternal = (com.android.server.DeviceIdleInternal) this.mPm.mInjector.getLocalService(com.android.server.DeviceIdleInternal.class);
                    android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
                    makeBasic.setTemporaryAppAllowlist(verificationTimeout, 0, 305, "");
                    if (matchVerifiers != null) {
                        int size2 = matchVerifiers.size();
                        if (size2 == 0) {
                            android.util.Slog.i("PackageManager", "Additional verifiers required, but none installed.");
                            setReturnCode(-22, "Additional verifiers required, but none installed.");
                        } else {
                            int i3 = 0;
                            while (i3 < size2) {
                                android.content.ComponentName componentName = matchVerifiers.get(i3);
                                deviceIdleInternal.addPowerSaveTempWhitelistApp(android.os.Process.myUid(), componentName.getPackageName(), verificationTimeout, identifier, false, 305, "package verifier");
                                android.content.Intent intent4 = new android.content.Intent(intent3);
                                intent4.setComponent(componentName);
                                this.mPm.mContext.sendBroadcastAsUser(intent4, userHandle, null, makeBasic.toBundle());
                                i3++;
                                size2 = size2;
                                matchVerifiers = matchVerifiers;
                            }
                        }
                    }
                    if (arrayList6.size() == 0) {
                        android.util.Slog.e("PackageManager", "No required verifiers");
                        return;
                    }
                    if (android.content.pm.Flags.emergencyInstallPermission()) {
                        arrayList2 = arrayList6;
                        if (arrayList2.contains(packageInfoLite.packageName)) {
                            i2 = 2;
                            for (java.lang.String str14 : arrayList2) {
                                java.lang.String str15 = str11;
                                com.android.server.pm.Computer computer2 = computer;
                                int packageUid = computer2.getPackageUid(str14, 268435456L, identifier);
                                if (!z) {
                                    arrayList3 = arrayList2;
                                    str3 = str10;
                                    str4 = str2;
                                } else if (arrayList2.size() == 1) {
                                    arrayList3 = arrayList2;
                                    str3 = str10;
                                    str4 = str2;
                                } else {
                                    str3 = str10;
                                    android.content.Intent intent5 = new android.content.Intent(str3);
                                    intent5.addFlags(1);
                                    intent5.addFlags(268435456);
                                    intent5.addFlags(32);
                                    arrayList3 = arrayList2;
                                    java.lang.String str16 = str9;
                                    intent5.setDataAndType(android.net.Uri.fromFile(new java.io.File(this.mOriginInfo.mResolvedPath)), str16);
                                    intent5.putExtra(str13, this.mSessionId);
                                    intent5.putExtra(str12, this.mDataLoaderType);
                                    if (str == null) {
                                        str6 = str15;
                                    } else {
                                        str6 = str15;
                                        intent5.putExtra(str6, str);
                                    }
                                    intent5.setPackage(str14);
                                    str15 = str6;
                                    str9 = str16;
                                    str4 = str2;
                                    intent5.putExtra(str4, -i);
                                    intent = intent5;
                                    str5 = null;
                                    deviceIdleInternal.addPowerSaveTempWhitelistApp(android.os.Process.myUid(), str14, verificationTimeout, identifier, false, 305, "package verifier");
                                    java.lang.String str17 = str3;
                                    startVerificationTimeoutCountdown(i, z2, new com.android.server.pm.PackageVerificationResponse(i2, packageUid), verificationTimeout);
                                    this.mPm.mContext.sendOrderedBroadcastAsUser(intent, userHandle, str5, -1, makeBasic.toBundle(), null, null, 0, null, null);
                                    str13 = str13;
                                    str11 = str15;
                                    userHandle = userHandle;
                                    intent3 = intent3;
                                    arrayList2 = arrayList3;
                                    identifier = identifier;
                                    str12 = str12;
                                    str = str;
                                    str9 = str9;
                                    str2 = str4;
                                    i2 = i2;
                                    computer = computer2;
                                    str10 = str17;
                                }
                                android.content.Intent intent6 = new android.content.Intent(intent3);
                                if (!z) {
                                    intent6.setComponent(matchComponentForVerifier(str14, queryIntentReceivers.getList()));
                                } else {
                                    intent6.setPackage(str14);
                                }
                                intent = intent6;
                                str5 = "android.permission.PACKAGE_VERIFICATION_AGENT";
                                deviceIdleInternal.addPowerSaveTempWhitelistApp(android.os.Process.myUid(), str14, verificationTimeout, identifier, false, 305, "package verifier");
                                java.lang.String str172 = str3;
                                startVerificationTimeoutCountdown(i, z2, new com.android.server.pm.PackageVerificationResponse(i2, packageUid), verificationTimeout);
                                this.mPm.mContext.sendOrderedBroadcastAsUser(intent, userHandle, str5, -1, makeBasic.toBundle(), null, null, 0, null, null);
                                str13 = str13;
                                str11 = str15;
                                userHandle = userHandle;
                                intent3 = intent3;
                                arrayList2 = arrayList3;
                                identifier = identifier;
                                str12 = str12;
                                str = str;
                                str9 = str9;
                                str2 = str4;
                                i2 = i2;
                                computer = computer2;
                                str10 = str172;
                            }
                            android.os.Trace.asyncTraceBegin(262144L, "verification", i);
                            this.mWaitForVerificationToComplete = true;
                            return;
                        }
                    } else {
                        arrayList2 = arrayList6;
                    }
                    if (getDefaultVerificationResponse() == 1) {
                        i2 = 2;
                    } else {
                        i2 = -1;
                    }
                    while (r33.hasNext()) {
                    }
                    android.os.Trace.asyncTraceBegin(262144L, "verification", i);
                    this.mWaitForVerificationToComplete = true;
                    return;
                }
            }
        }
        arrayList = arrayList4;
        z = false;
        if (!this.mOriginInfo.mExisting) {
        }
        packageVerificationState.passRequiredVerification();
    }

    private void startVerificationTimeoutCountdown(int i, boolean z, com.android.server.pm.PackageVerificationResponse packageVerificationResponse, long j) {
        android.os.Message obtainMessage = this.mPm.mHandler.obtainMessage(16);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = z ? 1 : 0;
        obtainMessage.obj = packageVerificationResponse;
        this.mPm.mHandler.sendMessageDelayed(obtainMessage, j);
    }

    int getDefaultVerificationResponse() {
        if (this.mPm.mUserManager.hasUserRestriction("ensure_verify_apps", getUser().getIdentifier())) {
            return -1;
        }
        return android.provider.Settings.Global.getInt(this.mPm.mContext.getContentResolver(), "verifier_default_response", 1);
    }

    private boolean packageExists(java.lang.String str) {
        return this.mPm.snapshotComputer().getPackageStateInternal(str) != null;
    }

    private boolean isAdbVerificationEnabled(android.content.pm.PackageInfoLite packageInfoLite, int i, boolean z) {
        boolean z2 = android.provider.Settings.Global.getInt(this.mPm.mContext.getContentResolver(), "verifier_verify_adb_installs", 1) != 0;
        if (this.mPm.isUserRestricted(i, "ensure_verify_apps")) {
            if (!z2) {
                android.util.Slog.w("PackageManager", "Force verification of ADB install because of user restriction.");
            }
            return true;
        }
        if (!z2) {
            return false;
        }
        if (z && packageExists(packageInfoLite.packageName)) {
            return !packageInfoLite.debuggable;
        }
        return true;
    }

    private boolean isVerificationEnabled(android.content.pm.PackageInfoLite packageInfoLite, int i, java.util.List<java.lang.String> list) {
        int i2 = this.mVerificationInfo == null ? -1 : this.mVerificationInfo.mInstallerUid;
        boolean z = (this.mInstallFlags & 524288) != 0;
        if ((this.mInstallFlags & 32) != 0) {
            return isAdbVerificationEnabled(packageInfoLite, i, z);
        }
        if (z) {
            return false;
        }
        if (isInstant() && this.mPm.mInstantAppInstallerActivity != null) {
            java.lang.String str = this.mPm.mInstantAppInstallerActivity.packageName;
            for (java.lang.String str2 : list) {
                if (str.equals(str2)) {
                    try {
                        ((android.app.AppOpsManager) this.mPm.mInjector.getSystemService(android.app.AppOpsManager.class)).checkPackage(i2, str2);
                        return false;
                    } catch (java.lang.SecurityException e) {
                    }
                }
            }
        }
        return true;
    }

    private java.util.List<android.content.ComponentName> matchVerifiers(android.content.pm.PackageInfoLite packageInfoLite, java.util.List<android.content.pm.ResolveInfo> list, com.android.server.pm.PackageVerificationState packageVerificationState) {
        int uidForVerifier;
        if (packageInfoLite.verifiers == null || packageInfoLite.verifiers.length == 0) {
            return null;
        }
        int length = packageInfoLite.verifiers.length;
        java.util.ArrayList arrayList = new java.util.ArrayList(length + 1);
        for (int i = 0; i < length; i++) {
            android.content.pm.VerifierInfo verifierInfo = packageInfoLite.verifiers[i];
            android.content.ComponentName matchComponentForVerifier = matchComponentForVerifier(verifierInfo.packageName, list);
            if (matchComponentForVerifier != null && (uidForVerifier = this.mPm.getUidForVerifier(verifierInfo)) != -1) {
                arrayList.add(matchComponentForVerifier);
                packageVerificationState.addSufficientVerifier(uidForVerifier);
            }
        }
        return arrayList;
    }

    private static android.content.ComponentName matchComponentForVerifier(java.lang.String str, java.util.List<android.content.pm.ResolveInfo> list) {
        android.content.pm.ActivityInfo activityInfo;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                activityInfo = null;
                break;
            }
            android.content.pm.ResolveInfo resolveInfo = list.get(i);
            if (resolveInfo.activityInfo == null || !str.equals(resolveInfo.activityInfo.packageName)) {
                i++;
            } else {
                activityInfo = resolveInfo.activityInfo;
                break;
            }
        }
        if (activityInfo == null) {
            return null;
        }
        return new android.content.ComponentName(activityInfo.packageName, activityInfo.name);
    }

    void populateInstallerExtras(android.content.Intent intent) {
        intent.putExtra("android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE", this.mInstallSource.mInitiatingPackageName);
        if (this.mVerificationInfo != null) {
            if (this.mVerificationInfo.mOriginatingUri != null) {
                intent.putExtra("android.intent.extra.ORIGINATING_URI", this.mVerificationInfo.mOriginatingUri);
            }
            if (this.mVerificationInfo.mReferrer != null) {
                intent.putExtra("android.intent.extra.REFERRER", this.mVerificationInfo.mReferrer);
            }
            if (this.mVerificationInfo.mOriginatingUid >= 0) {
                intent.putExtra("android.intent.extra.ORIGINATING_UID", this.mVerificationInfo.mOriginatingUid);
            }
            if (this.mVerificationInfo.mInstallerUid >= 0) {
                intent.putExtra("android.content.pm.extra.VERIFICATION_INSTALLER_UID", this.mVerificationInfo.mInstallerUid);
            }
        }
    }

    void setReturnCode(int i, java.lang.String str) {
        if (this.mRet == 1) {
            this.mRet = i;
            this.mErrorMessage = str;
        }
    }

    void handleVerificationFinished() {
        this.mWaitForVerificationToComplete = false;
        handleReturnCode();
    }

    void handleIntegrityVerificationFinished() {
        this.mWaitForIntegrityVerificationToComplete = false;
        handleReturnCode();
    }

    void handleRollbackEnabled() {
        this.mWaitForEnableRollbackToComplete = false;
        handleReturnCode();
    }

    void handleReturnCode() {
        if (this.mWaitForVerificationToComplete || this.mWaitForIntegrityVerificationToComplete || this.mWaitForEnableRollbackToComplete) {
            return;
        }
        sendVerificationCompleteNotification();
        if (this.mRet != 1) {
            com.android.server.pm.PackageMetrics.onVerificationFailed(this);
        }
    }

    private void sendVerificationCompleteNotification() {
        if (this.mParentVerifyingSession != null) {
            this.mParentVerifyingSession.trySendVerificationCompleteNotification(this);
            return;
        }
        try {
            this.mObserver.onPackageInstalled((java.lang.String) null, this.mRet, this.mErrorMessage, new android.os.Bundle());
        } catch (android.os.RemoteException e) {
            android.util.Slog.i("PackageManager", "Observer no longer exists.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start() {
        android.os.Trace.asyncTraceEnd(262144L, "queueVerify", java.lang.System.identityHashCode(this));
        android.os.Trace.traceBegin(262144L, "start");
        handleStartVerify();
        handleReturnCode();
        android.os.Trace.traceEnd(262144L);
    }

    public void verifyStage() {
        android.os.Trace.asyncTraceBegin(262144L, "queueVerify", java.lang.System.identityHashCode(this));
        this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.VerifyingSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.VerifyingSession.this.start();
            }
        });
    }

    public void verifyStage(java.util.List<com.android.server.pm.VerifyingSession> list) throws com.android.server.pm.PackageManagerException {
        final com.android.server.pm.MultiPackageVerifyingSession multiPackageVerifyingSession = new com.android.server.pm.MultiPackageVerifyingSession(this, list);
        android.os.Handler handler = this.mPm.mHandler;
        java.util.Objects.requireNonNull(multiPackageVerifyingSession);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.pm.VerifyingSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.MultiPackageVerifyingSession.this.start();
            }
        });
    }

    public int getRet() {
        return this.mRet;
    }

    public java.lang.String getErrorMessage() {
        return this.mErrorMessage;
    }

    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getDataLoaderType() {
        return this.mDataLoaderType;
    }

    public int getUserActionRequiredType() {
        return this.mUserActionRequiredType;
    }

    public boolean isInstant() {
        return (this.mInstallFlags & 2048) != 0;
    }

    public boolean isInherit() {
        return this.mIsInherit;
    }

    public int getInstallerPackageUid() {
        return this.mInstallSource.mInstallerPackageUid;
    }

    public boolean isApex() {
        return (this.mInstallFlags & 131072) != 0;
    }

    public boolean isArchivedInstallation() {
        return (this.mInstallFlags & 134217728) != 0;
    }

    public boolean isStaged() {
        return this.mIsStaged;
    }
}
