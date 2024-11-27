package com.android.server.pm;

/* loaded from: classes2.dex */
final class PackageHandler extends android.os.Handler {
    private final com.android.server.pm.PackageManagerService mPm;

    PackageHandler(android.os.Looper looper, com.android.server.pm.PackageManagerService packageManagerService) {
        super(looper);
        this.mPm = packageManagerService;
    }

    @Override // android.os.Handler
    public void handleMessage(android.os.Message message) {
        try {
            doHandleMessage(message);
        } finally {
            android.os.Process.setThreadPriority(0);
        }
    }

    void doHandleMessage(android.os.Message message) {
        boolean z;
        switch (message.what) {
            case 1:
                this.mPm.sendPendingBroadcasts();
                break;
            case 9:
                com.android.server.pm.InstallRequest installRequest = this.mPm.mRunningInstalls.get(message.arg1);
                z = message.arg2 != 0;
                this.mPm.mRunningInstalls.delete(message.arg1);
                if (installRequest != null) {
                    installRequest.closeFreezer();
                    installRequest.onInstallCompleted();
                    installRequest.runPostInstallRunnable();
                    if (!installRequest.isInstallExistingForUser()) {
                        this.mPm.handlePackagePostInstall(installRequest, z);
                    }
                    android.os.Trace.asyncTraceEnd(262144L, "postInstall", message.arg1);
                    break;
                }
                break;
            case 13:
                if (!this.mPm.tryWriteSettings(false)) {
                    removeMessages(13);
                    this.mPm.scheduleWriteSettings();
                    break;
                }
                break;
            case 15:
                int i = message.arg1;
                com.android.server.pm.PackageVerificationState packageVerificationState = this.mPm.mPendingVerification.get(i);
                if (packageVerificationState == null) {
                    android.util.Slog.w("PackageManager", "Verification with id " + i + " not found. It may be invalid or overridden by integrity verification");
                    break;
                } else if (packageVerificationState.isVerificationComplete()) {
                    android.util.Slog.w("PackageManager", "Verification with id " + i + " already complete.");
                    break;
                } else {
                    com.android.server.pm.VerificationUtils.processVerificationResponse(i, packageVerificationState, (com.android.server.pm.PackageVerificationResponse) message.obj, this.mPm);
                    break;
                }
            case 16:
                int i2 = message.arg1;
                z = message.arg2 != 0;
                com.android.server.pm.PackageVerificationState packageVerificationState2 = this.mPm.mPendingVerification.get(i2);
                if (packageVerificationState2 != null && !packageVerificationState2.isVerificationComplete()) {
                    com.android.server.pm.PackageVerificationResponse packageVerificationResponse = (com.android.server.pm.PackageVerificationResponse) message.obj;
                    if (z || !packageVerificationState2.timeoutExtended(packageVerificationResponse.callerUid)) {
                        com.android.server.pm.VerificationUtils.processVerificationResponseOnTimeout(i2, packageVerificationState2, packageVerificationResponse, this.mPm);
                        break;
                    }
                }
                break;
            case 19:
                int i3 = message.arg1;
                if (!this.mPm.tryWritePackageList(i3)) {
                    removeMessages(19);
                    this.mPm.scheduleWritePackageList(i3);
                    break;
                }
                break;
            case 20:
                com.android.server.pm.InstantAppResolver.doInstantAppResolutionPhaseTwo(this.mPm.mContext, this.mPm.snapshotComputer(), this.mPm.mUserManager, this.mPm.mInstantAppResolverConnection, (android.content.pm.InstantAppRequest) message.obj, this.mPm.mInstantAppInstallerActivity, this.mPm.mHandler);
                break;
            case 21:
                int i4 = message.arg1;
                int i5 = message.arg2;
                com.android.server.pm.VerifyingSession verifyingSession = this.mPm.mPendingEnableRollback.get(i4);
                if (verifyingSession == null) {
                    android.util.Slog.w("PackageManager", "Invalid rollback enabled token " + i4 + " received");
                    break;
                } else {
                    this.mPm.mPendingEnableRollback.remove(i4);
                    if (i5 != 1) {
                        android.net.Uri fromFile = android.net.Uri.fromFile(verifyingSession.mOriginInfo.mResolvedFile);
                        android.util.Slog.w("PackageManager", "Failed to enable rollback for " + fromFile);
                        android.util.Slog.w("PackageManager", "Continuing with installation of " + fromFile);
                    }
                    android.os.Trace.asyncTraceEnd(262144L, "enable_rollback", i4);
                    verifyingSession.handleRollbackEnabled();
                    break;
                }
            case 22:
                int i6 = message.arg1;
                int i7 = message.arg2;
                com.android.server.pm.VerifyingSession verifyingSession2 = this.mPm.mPendingEnableRollback.get(i6);
                if (verifyingSession2 != null) {
                    android.net.Uri fromFile2 = android.net.Uri.fromFile(verifyingSession2.mOriginInfo.mResolvedFile);
                    android.util.Slog.w("PackageManager", "Enable rollback timed out for " + fromFile2);
                    this.mPm.mPendingEnableRollback.remove(i6);
                    android.util.Slog.w("PackageManager", "Continuing with installation of " + fromFile2);
                    android.os.Trace.asyncTraceEnd(262144L, "enable_rollback", i6);
                    verifyingSession2.handleRollbackEnabled();
                    android.content.Intent intent = new android.content.Intent("android.intent.action.CANCEL_ENABLE_ROLLBACK");
                    intent.putExtra(android.content.pm.PackageManagerInternal.EXTRA_ENABLE_ROLLBACK_SESSION_ID, i7);
                    intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.AAC_ADIF);
                    this.mPm.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.SYSTEM, "android.permission.PACKAGE_ROLLBACK_AGENT");
                    break;
                }
                break;
            case 23:
                com.android.server.pm.CleanUpArgs cleanUpArgs = (com.android.server.pm.CleanUpArgs) message.obj;
                if (cleanUpArgs != null) {
                    this.mPm.cleanUpResources(cleanUpArgs.getPackageName(), cleanUpArgs.getCodeFile(), cleanUpArgs.getInstructionSets());
                    break;
                }
                break;
            case 24:
            case 29:
                java.lang.String str = (java.lang.String) message.obj;
                if (str != null) {
                    this.mPm.notifyInstallObserver(str, message.what == 29);
                    break;
                }
                break;
            case 25:
                int i8 = message.arg1;
                com.android.server.pm.PackageVerificationState packageVerificationState3 = this.mPm.mPendingVerification.get(i8);
                if (packageVerificationState3 == null) {
                    android.util.Slog.w("PackageManager", "Integrity verification with id " + i8 + " not found. It may be invalid or overridden by verifier");
                    break;
                } else {
                    int intValue = ((java.lang.Integer) message.obj).intValue();
                    com.android.server.pm.VerifyingSession verifyingSession3 = packageVerificationState3.getVerifyingSession();
                    android.net.Uri fromFile3 = android.net.Uri.fromFile(verifyingSession3.mOriginInfo.mResolvedFile);
                    packageVerificationState3.setIntegrityVerificationResult(intValue);
                    if (intValue == 1) {
                        android.util.Slog.i("PackageManager", "Integrity check passed for " + fromFile3);
                    } else {
                        verifyingSession3.setReturnCode(-22, "Integrity check failed for " + fromFile3);
                    }
                    if (packageVerificationState3.areAllVerificationsComplete()) {
                        this.mPm.mPendingVerification.remove(i8);
                    }
                    android.os.Trace.asyncTraceEnd(262144L, "integrity_verification", i8);
                    verifyingSession3.handleIntegrityVerificationFinished();
                    break;
                }
            case 26:
                int i9 = message.arg1;
                com.android.server.pm.PackageVerificationState packageVerificationState4 = this.mPm.mPendingVerification.get(i9);
                if (packageVerificationState4 != null && !packageVerificationState4.isIntegrityVerificationComplete()) {
                    com.android.server.pm.VerifyingSession verifyingSession4 = packageVerificationState4.getVerifyingSession();
                    android.net.Uri fromFile4 = android.net.Uri.fromFile(verifyingSession4.mOriginInfo.mResolvedFile);
                    java.lang.String str2 = "Integrity verification timed out for " + fromFile4;
                    android.util.Slog.i("PackageManager", str2);
                    packageVerificationState4.setIntegrityVerificationResult(getDefaultIntegrityVerificationResponse());
                    if (getDefaultIntegrityVerificationResponse() == 1) {
                        android.util.Slog.i("PackageManager", "Integrity check times out, continuing with " + fromFile4);
                    } else {
                        verifyingSession4.setReturnCode(-22, str2);
                    }
                    if (packageVerificationState4.areAllVerificationsComplete()) {
                        this.mPm.mPendingVerification.remove(i9);
                    }
                    android.os.Trace.asyncTraceEnd(262144L, "integrity_verification", i9);
                    verifyingSession4.handleIntegrityVerificationFinished();
                    break;
                }
                break;
            case 27:
                this.mPm.mDomainVerificationManager.runMessage(message.arg1, message.obj);
                break;
            case 28:
                try {
                    this.mPm.mInjector.getSharedLibrariesImpl().pruneUnusedStaticSharedLibraries(this.mPm.snapshotComputer(), com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, android.provider.Settings.Global.getLong(this.mPm.mContext.getContentResolver(), "unused_static_shared_lib_min_cache_period", com.android.server.pm.PackageManagerService.DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD));
                    break;
                } catch (java.io.IOException e) {
                    android.util.Log.w("PackageManager", "Failed to prune unused static shared libraries :" + e.getMessage());
                    return;
                }
        }
    }

    private int getDefaultIntegrityVerificationResponse() {
        return -1;
    }
}
