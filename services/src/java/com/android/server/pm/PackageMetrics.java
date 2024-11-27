package com.android.server.pm;

/* loaded from: classes2.dex */
final class PackageMetrics {
    public static final int STEP_COMMIT = 4;
    public static final int STEP_DEXOPT = 5;
    public static final int STEP_FREEZE_INSTALL = 6;
    public static final int STEP_PREPARE = 1;
    public static final int STEP_RECONCILE = 3;
    public static final int STEP_SCAN = 2;
    private final com.android.server.pm.InstallRequest mInstallRequest;
    private final android.util.SparseArray<com.android.server.pm.PackageMetrics.InstallStep> mInstallSteps = new android.util.SparseArray<>();
    private final long mInstallStartTimestampMillis = java.lang.System.currentTimeMillis();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StepInt {
    }

    PackageMetrics(com.android.server.pm.InstallRequest installRequest) {
        this.mInstallRequest = installRequest;
    }

    public void onInstallSucceed() {
        reportInstallationToSecurityLog(this.mInstallRequest.getUserId());
        reportInstallationStats(true);
    }

    public void onInstallFailed() {
        reportInstallationStats(false);
    }

    private void reportInstallationStats(boolean z) {
        java.lang.String str;
        long j;
        long j2;
        long j3;
        long j4;
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        if (userManagerInternal != null) {
            long currentTimeMillis = java.lang.System.currentTimeMillis() - this.mInstallStartTimestampMillis;
            android.util.Pair<int[], long[]> installStepDurations = getInstallStepDurations();
            int[] newUsers = this.mInstallRequest.getNewUsers();
            int[] originUsers = this.mInstallRequest.getOriginUsers();
            if (z || this.mInstallRequest.isInstallFromAdb()) {
                str = null;
            } else {
                str = this.mInstallRequest.getName();
            }
            int installerPackageUid = this.mInstallRequest.getInstallerPackageUid();
            long j5 = 0;
            if (!z) {
                j2 = 0;
                j3 = 0;
            } else {
                try {
                    com.android.server.pm.PackageSetting scannedPackageSetting = this.mInstallRequest.getScannedPackageSetting();
                    if (scannedPackageSetting == null) {
                        j4 = 0;
                    } else {
                        j = scannedPackageSetting.getVersionCode();
                        try {
                            j5 = j;
                            j4 = getApksSize(scannedPackageSetting.getPath());
                        } catch (java.lang.IllegalStateException | java.lang.NullPointerException e) {
                            j2 = 0;
                            j3 = j;
                            com.android.internal.util.FrameworkStatsLog.write(524, this.mInstallRequest.getSessionId(), str, getUid(this.mInstallRequest.getAppId(), this.mInstallRequest.getUserId()), newUsers, userManagerInternal.getUserTypesForStatsd(newUsers), originUsers, userManagerInternal.getUserTypesForStatsd(originUsers), this.mInstallRequest.getReturnCode(), this.mInstallRequest.getInternalErrorCode(), j2, j3, (int[]) installStepDurations.first, (long[]) installStepDurations.second, currentTimeMillis, this.mInstallRequest.getInstallFlags(), installerPackageUid, -1, this.mInstallRequest.getDataLoaderType(), this.mInstallRequest.getRequireUserAction(), this.mInstallRequest.isInstantInstall(), this.mInstallRequest.isInstallReplace(), this.mInstallRequest.isInstallSystem(), this.mInstallRequest.isInstallInherit(), this.mInstallRequest.isInstallForUsers(), this.mInstallRequest.isInstallMove(), false);
                        }
                    }
                    j3 = j5;
                    j2 = j4;
                } catch (java.lang.IllegalStateException | java.lang.NullPointerException e2) {
                    j = 0;
                }
            }
            com.android.internal.util.FrameworkStatsLog.write(524, this.mInstallRequest.getSessionId(), str, getUid(this.mInstallRequest.getAppId(), this.mInstallRequest.getUserId()), newUsers, userManagerInternal.getUserTypesForStatsd(newUsers), originUsers, userManagerInternal.getUserTypesForStatsd(originUsers), this.mInstallRequest.getReturnCode(), this.mInstallRequest.getInternalErrorCode(), j2, j3, (int[]) installStepDurations.first, (long[]) installStepDurations.second, currentTimeMillis, this.mInstallRequest.getInstallFlags(), installerPackageUid, -1, this.mInstallRequest.getDataLoaderType(), this.mInstallRequest.getRequireUserAction(), this.mInstallRequest.isInstantInstall(), this.mInstallRequest.isInstallReplace(), this.mInstallRequest.isInstallSystem(), this.mInstallRequest.isInstallInherit(), this.mInstallRequest.isInstallForUsers(), this.mInstallRequest.isInstallMove(), false);
        }
    }

    private static int getUid(int i, int i2) {
        if (i2 == -1) {
            i2 = android.app.ActivityManager.getCurrentUser();
        }
        return android.os.UserHandle.getUid(i2, i);
    }

    private long getApksSize(final java.io.File file) {
        final java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong();
        try {
            java.nio.file.Files.walkFileTree(file.toPath(), new java.nio.file.SimpleFileVisitor<java.nio.file.Path>() { // from class: com.android.server.pm.PackageMetrics.1
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public java.nio.file.FileVisitResult preVisitDirectory(java.nio.file.Path path, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) throws java.io.IOException {
                    if (path.equals(file.toPath())) {
                        return java.nio.file.FileVisitResult.CONTINUE;
                    }
                    return java.nio.file.FileVisitResult.SKIP_SUBTREE;
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public java.nio.file.FileVisitResult visitFile(java.nio.file.Path path, java.nio.file.attribute.BasicFileAttributes basicFileAttributes) throws java.io.IOException {
                    if (path.toFile().isFile() && android.content.pm.parsing.ApkLiteParseUtils.isApkFile(path.toFile())) {
                        atomicLong.addAndGet(path.toFile().length());
                    }
                    return java.nio.file.FileVisitResult.CONTINUE;
                }
            });
        } catch (java.io.IOException e) {
        }
        return atomicLong.get();
    }

    public void onStepStarted(int i) {
        this.mInstallSteps.put(i, new com.android.server.pm.PackageMetrics.InstallStep());
    }

    public void onStepFinished(int i) {
        com.android.server.pm.PackageMetrics.InstallStep installStep = this.mInstallSteps.get(i);
        if (installStep != null) {
            installStep.finish();
        }
    }

    public void onStepFinished(int i, long j) {
        this.mInstallSteps.put(i, new com.android.server.pm.PackageMetrics.InstallStep(j));
    }

    private android.util.Pair<int[], long[]> getInstallStepDurations() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int i = 0; i < this.mInstallSteps.size(); i++) {
            if (this.mInstallSteps.valueAt(i).getDurationMillis() >= 0) {
                arrayList.add(java.lang.Integer.valueOf(this.mInstallSteps.keyAt(i)));
                arrayList2.add(java.lang.Long.valueOf(this.mInstallSteps.valueAt(i).getDurationMillis()));
            }
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        long[] jArr = new long[arrayList2.size()];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = ((java.lang.Integer) arrayList.get(i2)).intValue();
            jArr[i2] = ((java.lang.Long) arrayList2.get(i2)).longValue();
        }
        return new android.util.Pair<>(iArr, jArr);
    }

    private static class InstallStep {
        private long mDurationMillis;
        private final long mStartTimestampMillis;

        InstallStep() {
            this.mDurationMillis = -1L;
            this.mStartTimestampMillis = java.lang.System.currentTimeMillis();
        }

        InstallStep(long j) {
            this.mDurationMillis = -1L;
            this.mStartTimestampMillis = -1L;
            this.mDurationMillis = j;
        }

        void finish() {
            this.mDurationMillis = java.lang.System.currentTimeMillis() - this.mStartTimestampMillis;
        }

        long getDurationMillis() {
            return this.mDurationMillis;
        }
    }

    public static void onUninstallSucceeded(com.android.server.pm.PackageRemovedInfo packageRemovedInfo, int i, int i2) {
        com.android.server.pm.UserManagerInternal userManagerInternal;
        if (packageRemovedInfo.mIsUpdate || (userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)) == null) {
            return;
        }
        int[] iArr = packageRemovedInfo.mRemovedUsers;
        int[] userTypesForStatsd = userManagerInternal.getUserTypesForStatsd(iArr);
        int[] iArr2 = packageRemovedInfo.mOrigUsers;
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PACKAGE_UNINSTALLATION_REPORTED, getUid(packageRemovedInfo.mUid, i2), iArr, userTypesForStatsd, iArr2, userManagerInternal.getUserTypesForStatsd(iArr2), i, 1, packageRemovedInfo.mIsRemovedPackageSystemUpdate, !packageRemovedInfo.mRemovedForAllUsers);
        reportUninstallationToSecurityLog(packageRemovedInfo.mRemovedPackage, packageRemovedInfo.mRemovedPackageVersionCode, i2);
    }

    public static void onVerificationFailed(com.android.server.pm.VerifyingSession verifyingSession) {
        com.android.internal.util.FrameworkStatsLog.write(524, verifyingSession.getSessionId(), (java.lang.String) null, -1, (int[]) null, (int[]) null, (int[]) null, (int[]) null, verifyingSession.getRet(), 0, 0L, 0L, (int[]) null, (long[]) null, 0L, 0, verifyingSession.getInstallerPackageUid(), -1, verifyingSession.getDataLoaderType(), verifyingSession.getUserActionRequiredType(), verifyingSession.isInstant(), false, false, verifyingSession.isInherit(), false, false, verifyingSession.isStaged());
    }

    private void reportInstallationToSecurityLog(int i) {
        if (!android.app.admin.SecurityLog.isLoggingEnabled()) {
            return;
        }
        try {
            com.android.server.pm.PackageSetting scannedPackageSetting = this.mInstallRequest.getScannedPackageSetting();
            if (scannedPackageSetting == null) {
                return;
            }
            java.lang.String packageName = scannedPackageSetting.getPackageName();
            long versionCode = scannedPackageSetting.getVersionCode();
            if (!this.mInstallRequest.isInstallReplace()) {
                android.app.admin.SecurityLog.writeEvent(210041, new java.lang.Object[]{packageName, java.lang.Long.valueOf(versionCode), java.lang.Integer.valueOf(i)});
            } else {
                android.app.admin.SecurityLog.writeEvent(210042, new java.lang.Object[]{packageName, java.lang.Long.valueOf(versionCode), java.lang.Integer.valueOf(i)});
            }
        } catch (java.lang.IllegalStateException | java.lang.NullPointerException e) {
        }
    }

    private static void reportUninstallationToSecurityLog(java.lang.String str, long j, int i) {
        if (!android.app.admin.SecurityLog.isLoggingEnabled()) {
            return;
        }
        android.app.admin.SecurityLog.writeEvent(210043, new java.lang.Object[]{str, java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i)});
    }
}
