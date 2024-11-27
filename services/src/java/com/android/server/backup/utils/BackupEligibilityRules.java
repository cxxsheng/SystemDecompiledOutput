package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class BackupEligibilityRules {
    private static final boolean DEBUG = false;
    static final long IGNORE_ALLOW_BACKUP_IN_D2D = 183147249;
    static final long RESTRICT_ADB_BACKUP = 171032338;
    private final int mBackupDestination;
    private boolean mIsProfileUser;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final boolean mSkipRestoreForLaunchedApps;
    private final int mUserId;
    private static final java.util.Set<java.lang.String> systemPackagesAllowedForProfileUser = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{com.android.server.backup.UserBackupManagerService.PACKAGE_MANAGER_SENTINEL, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME});
    private static final java.util.Set<java.lang.String> systemPackagesAllowedForNonSystemUsers = com.android.server.backup.SetUtils.union(systemPackagesAllowedForProfileUser, com.google.android.collect.Sets.newArraySet(new java.lang.String[]{com.android.server.backup.UserBackupManagerService.WALLPAPER_PACKAGE, com.android.server.backup.UserBackupManagerService.SETTINGS_PACKAGE}));

    public static com.android.server.backup.utils.BackupEligibilityRules forBackup(android.content.pm.PackageManager packageManager, android.content.pm.PackageManagerInternal packageManagerInternal, int i, android.content.Context context) {
        return new com.android.server.backup.utils.BackupEligibilityRules(packageManager, packageManagerInternal, i, context, 0);
    }

    public BackupEligibilityRules(android.content.pm.PackageManager packageManager, android.content.pm.PackageManagerInternal packageManagerInternal, int i, android.content.Context context, int i2) {
        this(packageManager, packageManagerInternal, i, context, i2, false);
    }

    public BackupEligibilityRules(android.content.pm.PackageManager packageManager, android.content.pm.PackageManagerInternal packageManagerInternal, int i, android.content.Context context, int i2, boolean z) {
        this.mIsProfileUser = false;
        this.mPackageManager = packageManager;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mUserId = i;
        this.mBackupDestination = i2;
        this.mIsProfileUser = ((android.os.UserManager) context.getSystemService(android.os.UserManager.class)).isProfile();
        this.mSkipRestoreForLaunchedApps = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean appIsEligibleForBackup(android.content.pm.ApplicationInfo applicationInfo) {
        if (!isAppBackupAllowed(applicationInfo)) {
            return false;
        }
        if (android.os.UserHandle.isCore(applicationInfo.uid)) {
            if (this.mUserId != 0) {
                if (this.mIsProfileUser && !systemPackagesAllowedForProfileUser.contains(applicationInfo.packageName)) {
                    return false;
                }
                if (!this.mIsProfileUser && !systemPackagesAllowedForNonSystemUsers.contains(applicationInfo.packageName)) {
                    return false;
                }
            }
            if (applicationInfo.backupAgentName == null) {
                return false;
            }
        }
        if (applicationInfo.packageName.equals(com.android.server.backup.UserBackupManagerService.SHARED_BACKUP_AGENT_PACKAGE) || applicationInfo.isInstantApp()) {
            return false;
        }
        return !appIsDisabled(applicationInfo);
    }

    public boolean isAppBackupAllowed(android.content.pm.ApplicationInfo applicationInfo) {
        boolean z = (applicationInfo.flags & 32768) != 0;
        switch (this.mBackupDestination) {
            case 0:
                break;
            case 1:
                if ((!((applicationInfo.flags & 1) != 0) && android.app.compat.CompatChanges.isChangeEnabled(IGNORE_ALLOW_BACKUP_IN_D2D, applicationInfo.packageName, android.os.UserHandle.of(this.mUserId))) || z) {
                    break;
                }
                break;
            case 2:
                java.lang.String str = applicationInfo.packageName;
                if (str == null) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Invalid ApplicationInfo object");
                    break;
                } else if (android.app.compat.CompatChanges.isChangeEnabled(RESTRICT_ADB_BACKUP, str, android.os.UserHandle.of(this.mUserId))) {
                    if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str)) {
                        boolean z2 = (applicationInfo.flags & 8) != 0;
                        boolean z3 = (applicationInfo.flags & 2) != 0;
                        if (android.os.UserHandle.isCore(applicationInfo.uid) || z2) {
                            try {
                                break;
                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Failed to read allowAdbBackup property for + " + str);
                                return z;
                            }
                        }
                    }
                }
                break;
            default:
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unknown operation type:" + this.mBackupDestination);
                break;
        }
        return false;
    }

    public boolean appIsRunningAndEligibleForBackupWithTransport(@android.annotation.Nullable com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str) {
        try {
            android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(str, 134217728, this.mUserId);
            android.content.pm.ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
            if (!appIsEligibleForBackup(applicationInfo) || appIsStopped(applicationInfo) || appIsDisabled(applicationInfo)) {
                return false;
            }
            if (transportConnection != null) {
                try {
                    return transportConnection.connectOrThrow("AppBackupUtils.appIsRunningAndEligibleForBackupWithTransport").isAppEligibleForBackup(packageInfoAsUser, appGetsFullBackup(packageInfoAsUser));
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to ask about eligibility: " + e.getMessage());
                    return true;
                }
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            return false;
        }
    }

    public boolean isAppEligibleForRestore(android.content.pm.ApplicationInfo applicationInfo) {
        if (this.mSkipRestoreForLaunchedApps && applicationInfo.backupAgentName == null) {
            return !this.mPackageManagerInternal.wasPackageEverLaunched(applicationInfo.packageName, this.mUserId);
        }
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean appIsDisabled(android.content.pm.ApplicationInfo applicationInfo) {
        switch (this.mPackageManagerInternal.getApplicationEnabledState(applicationInfo.packageName, this.mUserId)) {
            case 0:
                return !applicationInfo.enabled;
            case 1:
            default:
                return false;
            case 2:
            case 3:
            case 4:
                return true;
        }
    }

    public boolean appIsStopped(android.content.pm.ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 2097152) != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean appGetsFullBackup(android.content.pm.PackageInfo packageInfo) {
        return packageInfo.applicationInfo.backupAgentName == null || (packageInfo.applicationInfo.flags & 67108864) != 0;
    }

    public boolean appIsKeyValueOnly(android.content.pm.PackageInfo packageInfo) {
        return !appGetsFullBackup(packageInfo);
    }

    public boolean signaturesMatch(android.content.pm.Signature[] signatureArr, android.content.pm.PackageInfo packageInfo) {
        boolean z;
        if (packageInfo == null || packageInfo.packageName == null) {
            return false;
        }
        if ((packageInfo.applicationInfo.flags & 1) != 0) {
            return true;
        }
        if (com.android.internal.util.ArrayUtils.isEmpty(signatureArr)) {
            return false;
        }
        android.content.pm.SigningInfo signingInfo = packageInfo.signingInfo;
        if (signingInfo == null) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "signingInfo is empty, app was either unsigned or the flag PackageManager#GET_SIGNING_CERTIFICATES was not specified");
            return false;
        }
        if (signatureArr.length == 1) {
            return this.mPackageManagerInternal.isDataRestoreSafe(signatureArr[0], packageInfo.packageName);
        }
        android.content.pm.Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
        int length = apkContentsSigners.length;
        for (android.content.pm.Signature signature : signatureArr) {
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = false;
                    break;
                }
                if (!signature.equals(apkContentsSigners[i])) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public int getBackupDestination() {
        return this.mBackupDestination;
    }
}
