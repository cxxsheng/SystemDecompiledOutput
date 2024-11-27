package com.android.server;

/* loaded from: classes.dex */
public final class UserspaceRebootLogger {
    private static final java.lang.String LAST_BOOT_REASON_PROPERTY = "sys.boot.reason.last";
    private static final java.lang.String TAG = "UserspaceRebootLogger";
    private static final java.lang.String USERSPACE_REBOOT_LAST_FINISHED_PROPERTY = "sys.userspace_reboot.log.last_finished";
    private static final java.lang.String USERSPACE_REBOOT_LAST_STARTED_PROPERTY = "sys.userspace_reboot.log.last_started";
    private static final java.lang.String USERSPACE_REBOOT_SHOULD_LOG_PROPERTY = "persist.sys.userspace_reboot.log.should_log";

    private UserspaceRebootLogger() {
    }

    public static void noteUserspaceRebootWasRequested() {
        if (!android.os.PowerManager.isRebootingUserspaceSupportedImpl()) {
            android.util.Slog.wtf(TAG, "noteUserspaceRebootWasRequested: Userspace reboot is not supported.");
        } else {
            android.os.SystemProperties.set(USERSPACE_REBOOT_SHOULD_LOG_PROPERTY, "1");
            android.os.SystemProperties.set(USERSPACE_REBOOT_LAST_STARTED_PROPERTY, java.lang.String.valueOf(android.os.SystemClock.elapsedRealtime()));
        }
    }

    public static void noteUserspaceRebootSuccess() {
        if (!android.os.PowerManager.isRebootingUserspaceSupportedImpl()) {
            android.util.Slog.wtf(TAG, "noteUserspaceRebootSuccess: Userspace reboot is not supported.");
        } else {
            android.os.SystemProperties.set(USERSPACE_REBOOT_LAST_FINISHED_PROPERTY, java.lang.String.valueOf(android.os.SystemClock.elapsedRealtime()));
        }
    }

    public static boolean shouldLogUserspaceRebootEvent() {
        if (android.os.PowerManager.isRebootingUserspaceSupportedImpl()) {
            return android.os.SystemProperties.getBoolean(USERSPACE_REBOOT_SHOULD_LOG_PROPERTY, false);
        }
        return false;
    }

    public static void logEventAsync(boolean z, java.util.concurrent.Executor executor) {
        if (!android.os.PowerManager.isRebootingUserspaceSupportedImpl()) {
            android.util.Slog.wtf(TAG, "logEventAsync: Userspace reboot is not supported.");
            return;
        }
        final int computeOutcome = computeOutcome();
        final int i = 1;
        final long j = computeOutcome == 1 ? android.os.SystemProperties.getLong(USERSPACE_REBOOT_LAST_FINISHED_PROPERTY, 0L) - android.os.SystemProperties.getLong(USERSPACE_REBOOT_LAST_STARTED_PROPERTY, 0L) : 0L;
        if (!z) {
            i = 2;
        }
        executor.execute(new java.lang.Runnable() { // from class: com.android.server.UserspaceRebootLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.UserspaceRebootLogger.lambda$logEventAsync$0(computeOutcome, j, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$logEventAsync$0(int i, long j, int i2) {
        android.util.Slog.i(TAG, "Logging UserspaceRebootReported atom: { outcome: " + i + " durationMillis: " + j + " encryptionState: " + i2 + " }");
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.USERSPACE_REBOOT_REPORTED, i, j, i2);
        android.os.SystemProperties.set(USERSPACE_REBOOT_SHOULD_LOG_PROPERTY, "");
    }

    private static int computeOutcome() {
        if (android.os.SystemProperties.getLong(USERSPACE_REBOOT_LAST_STARTED_PROPERTY, -1L) != -1) {
            return 1;
        }
        java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(android.os.SystemProperties.get(LAST_BOOT_REASON_PROPERTY, ""));
        if (emptyIfNull.startsWith("reboot,")) {
            emptyIfNull = emptyIfNull.substring("reboot".length());
        }
        if (emptyIfNull.startsWith("userspace_failed,watchdog_fork") || emptyIfNull.startsWith("userspace_failed,shutdown_aborted")) {
            return 2;
        }
        if (emptyIfNull.startsWith("mount_userdata_failed") || emptyIfNull.startsWith("userspace_failed,init_user0") || emptyIfNull.startsWith("userspace_failed,enablefilecrypto")) {
            return 3;
        }
        if (emptyIfNull.startsWith("userspace_failed,watchdog_triggered")) {
            return 4;
        }
        return 0;
    }
}
