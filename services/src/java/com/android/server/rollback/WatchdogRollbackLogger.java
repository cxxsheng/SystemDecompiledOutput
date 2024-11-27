package com.android.server.rollback;

/* loaded from: classes2.dex */
public final class WatchdogRollbackLogger {
    private static final java.lang.String LOGGING_PARENT_KEY = "android.content.pm.LOGGING_PARENT";
    private static final java.lang.String TAG = "WatchdogRollbackLogger";

    private WatchdogRollbackLogger() {
    }

    @android.annotation.Nullable
    private static java.lang.String getLoggingParentName(android.content.Context context, @android.annotation.NonNull java.lang.String str) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = context.getPackageManager().getPackageInfo(str, 1073741952).applicationInfo;
            if (applicationInfo.metaData == null) {
                return null;
            }
            return applicationInfo.metaData.getString(LOGGING_PARENT_KEY);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unable to discover logging parent package: " + str, e);
            return null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static android.content.pm.VersionedPackage getLogPackage(android.content.Context context, @android.annotation.NonNull android.content.pm.VersionedPackage versionedPackage) {
        java.lang.String loggingParentName = getLoggingParentName(context, versionedPackage.getPackageName());
        if (loggingParentName == null) {
            return null;
        }
        try {
            return new android.content.pm.VersionedPackage(loggingParentName, context.getPackageManager().getPackageInfo(loggingParentName, 0).getLongVersionCode());
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static java.util.Set<android.content.pm.VersionedPackage> getLogPackages(android.content.Context context, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            arraySet.add(getLogPackage(context, new android.content.pm.VersionedPackage(it.next(), 0)));
        }
        return arraySet;
    }

    static void logRollbackStatusOnBoot(android.content.Context context, int i, java.lang.String str, java.util.List<android.content.rollback.RollbackInfo> list) {
        android.content.pm.VersionedPackage versionedPackage;
        android.content.rollback.RollbackInfo rollbackInfo;
        android.content.pm.PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        java.util.Iterator<android.content.rollback.RollbackInfo> it = list.iterator();
        while (true) {
            versionedPackage = null;
            if (!it.hasNext()) {
                rollbackInfo = null;
                break;
            } else {
                rollbackInfo = it.next();
                if (i == rollbackInfo.getRollbackId()) {
                    break;
                }
            }
        }
        if (rollbackInfo == null) {
            android.util.Slog.e(TAG, "rollback info not found for last staged rollback: " + i);
            return;
        }
        if (!android.text.TextUtils.isEmpty(str)) {
            java.util.Iterator it2 = rollbackInfo.getPackages().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                android.content.rollback.PackageRollbackInfo packageRollbackInfo = (android.content.rollback.PackageRollbackInfo) it2.next();
                if (str.equals(packageRollbackInfo.getPackageName())) {
                    versionedPackage = packageRollbackInfo.getVersionRolledBackFrom();
                    break;
                }
            }
        }
        int committedSessionId = rollbackInfo.getCommittedSessionId();
        android.content.pm.PackageInstaller.SessionInfo sessionInfo = packageInstaller.getSessionInfo(committedSessionId);
        if (sessionInfo == null) {
            android.util.Slog.e(TAG, "On boot completed, could not load session id " + committedSessionId);
            return;
        }
        if (sessionInfo.isStagedSessionApplied()) {
            logEvent(versionedPackage, 2, 0, "");
        } else if (sessionInfo.isStagedSessionFailed()) {
            logEvent(versionedPackage, 3, 0, "");
        }
    }

    public static void logApexdRevert(android.content.Context context, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.NonNull java.lang.String str) {
        java.util.Iterator<android.content.pm.VersionedPackage> it = getLogPackages(context, list).iterator();
        while (it.hasNext()) {
            logEvent(it.next(), 2, 5, str);
        }
    }

    public static void logEvent(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, int i, int i2, @android.annotation.NonNull java.lang.String str) {
        android.util.Slog.i(TAG, "Watchdog event occurred with type: " + rollbackTypeToString(i) + " logPackage: " + versionedPackage + " rollbackReason: " + rollbackReasonToString(i2) + " failedPackageName: " + str);
        if (versionedPackage != null) {
            com.android.server.crashrecovery.proto.CrashRecoveryStatsLog.write(147, i, versionedPackage.getPackageName(), versionedPackage.getVersionCode(), i2, str, new byte[0]);
        } else {
            com.android.server.crashrecovery.proto.CrashRecoveryStatsLog.write(147, i, "", 0, i2, str, new byte[0]);
        }
        logTestProperties(versionedPackage, i, i2, str);
    }

    private static void logTestProperties(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, int i, int i2, @android.annotation.NonNull java.lang.String str) {
        if (!android.os.SystemProperties.getBoolean("persist.sys.rollbacktest.enabled", false)) {
            return;
        }
        java.lang.String str2 = "persist.sys.rollbacktest." + rollbackTypeToString(i);
        android.os.SystemProperties.set(str2, java.lang.String.valueOf(true));
        android.os.SystemProperties.set(str2 + ".logPackage", versionedPackage != null ? versionedPackage.toString() : "");
        android.os.SystemProperties.set(str2 + ".rollbackReason", rollbackReasonToString(i2));
        android.os.SystemProperties.set(str2 + ".failedPackageName", str);
    }

    @com.android.internal.annotations.VisibleForTesting
    static int mapFailureReasonToMetric(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 7;
            default:
                return 0;
        }
    }

    private static java.lang.String rollbackTypeToString(int i) {
        switch (i) {
            case 1:
                return "ROLLBACK_INITIATE";
            case 2:
                return "ROLLBACK_SUCCESS";
            case 3:
                return "ROLLBACK_FAILURE";
            case 4:
                return "ROLLBACK_BOOT_TRIGGERED";
            default:
                return "UNKNOWN";
        }
    }

    private static java.lang.String rollbackReasonToString(int i) {
        switch (i) {
            case 1:
                return "REASON_NATIVE_CRASH";
            case 2:
                return "REASON_EXPLICIT_HEALTH_CHECK";
            case 3:
                return "REASON_APP_CRASH";
            case 4:
                return "REASON_APP_NOT_RESPONDING";
            case 5:
                return "REASON_NATIVE_CRASH_DURING_BOOT";
            default:
                return "UNKNOWN";
        }
    }
}
