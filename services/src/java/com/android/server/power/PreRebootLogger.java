package com.android.server.power;

/* loaded from: classes2.dex */
final class PreRebootLogger {
    private static final java.lang.String PREREBOOT_DIR = "prereboot";
    private static final java.lang.String TAG = "PreRebootLogger";
    private static final java.lang.String[] BUFFERS_TO_DUMP = {"system"};
    private static final java.lang.String[] SERVICES_TO_DUMP = {"rollback", com.android.server.pm.Settings.ATTR_PACKAGE};
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final long MAX_DUMP_TIME = java.util.concurrent.TimeUnit.SECONDS.toMillis(20);

    PreRebootLogger() {
    }

    static void log(android.content.Context context) {
        log(context, getDumpDir());
    }

    @com.android.internal.annotations.VisibleForTesting
    static void log(android.content.Context context, @android.annotation.NonNull java.io.File file) {
        if (needDump(context)) {
            dump(file, MAX_DUMP_TIME);
        } else {
            wipe(file);
        }
    }

    private static boolean needDump(android.content.Context context) {
        return android.provider.Settings.Global.getInt(context.getContentResolver(), "adb_enabled", 0) == 1 && !context.getPackageManager().getPackageInstaller().getActiveStagedSessions().isEmpty();
    }

    @com.android.internal.annotations.VisibleForTesting
    static void dump(@android.annotation.NonNull final java.io.File file, long j) {
        android.util.Slog.d(TAG, "Dumping pre-reboot information...");
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(false);
        java.lang.Thread thread = new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.power.PreRebootLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.PreRebootLogger.lambda$dump$0(file, atomicBoolean);
            }
        });
        thread.start();
        try {
            thread.join(j);
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.e(TAG, "Failed to dump pre-reboot information due to interrupted", e);
        }
        if (!atomicBoolean.get()) {
            android.util.Slog.w(TAG, "Failed to dump pre-reboot information due to timeout");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$0(java.io.File file, java.util.concurrent.atomic.AtomicBoolean atomicBoolean) {
        synchronized (sLock) {
            try {
                for (java.lang.String str : BUFFERS_TO_DUMP) {
                    dumpLogsLocked(file, str);
                }
                for (java.lang.String str2 : SERVICES_TO_DUMP) {
                    dumpServiceLocked(file, str2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        atomicBoolean.set(true);
    }

    private static void wipe(@android.annotation.NonNull java.io.File file) {
        android.util.Slog.d(TAG, "Wiping pre-reboot information...");
        synchronized (sLock) {
            try {
                for (java.io.File file2 : file.listFiles()) {
                    file2.delete();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static java.io.File getDumpDir() {
        java.io.File file = new java.io.File(android.os.Environment.getDataMiscDirectory(), PREREBOOT_DIR);
        if (!file.exists() || !file.isDirectory()) {
            throw new java.lang.UnsupportedOperationException("Pre-reboot dump directory not found");
        }
        return file;
    }

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static void dumpLogsLocked(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.lang.String str) {
        try {
            java.io.File file2 = new java.io.File(file, str);
            if (file2.createNewFile()) {
                file2.setWritable(true, true);
            } else {
                new java.io.FileWriter(file2, false).flush();
            }
            java.lang.Runtime.getRuntime().exec(new java.lang.String[]{"logcat", "-d", "-b", str, "-f", file2.getAbsolutePath()}).waitFor();
        } catch (java.io.IOException | java.lang.InterruptedException e) {
            android.util.Slog.e(TAG, "Failed to dump system log buffer before reboot", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"sLock"})
    private static void dumpServiceLocked(@android.annotation.NonNull java.io.File file, @android.annotation.NonNull java.lang.String str) {
        android.os.IBinder checkService = android.os.ServiceManager.checkService(str);
        if (checkService == null) {
            return;
        }
        try {
            checkService.dump(android.os.ParcelFileDescriptor.open(new java.io.File(file, str), 738197504).getFileDescriptor(), (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class));
        } catch (android.os.RemoteException | java.io.FileNotFoundException e) {
            android.util.Slog.e(TAG, java.lang.String.format("Failed to dump %s service before reboot", str), e);
        }
    }
}
