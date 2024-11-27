package com.android.server;

/* loaded from: classes.dex */
public class BootReceiver extends android.content.BroadcastReceiver {
    private static final java.lang.String E2FSCK_FS_MODIFIED = "FILE SYSTEM WAS MODIFIED";
    private static final java.lang.String ERROR_REPORT_TRACE_PIPE = "/sys/kernel/tracing/instances/bootreceiver/trace_pipe";
    private static final java.lang.String F2FS_FSCK_FS_MODIFIED = "[FSCK] Unreachable";
    private static final java.lang.String FSCK_PASS_PATTERN = "Pass ([1-9]E?):";
    private static final java.lang.String FSCK_TREE_OPTIMIZATION_PATTERN = "Inode [0-9]+ extent tree.*could be shorter";
    private static final int FS_STAT_FSCK_FS_FIXED = 1024;
    private static final java.lang.String FS_STAT_PATTERN = "fs_stat,[^,]*/([^/,]+),(0x[0-9a-fA-F]+)";
    private static final int GMSCORE_LASTK_LOG_SIZE = 196608;
    private static final int LASTK_LOG_SIZE;
    private static final java.lang.String LAST_HEADER_FILE = "last-header.txt";
    private static final java.lang.String[] LAST_KMSG_FILES;
    private static final java.lang.String LAST_SHUTDOWN_TIME_PATTERN = "powerctl_shutdown_time_ms:([0-9]+):([0-9]+)";
    private static final java.lang.String LOG_FILES_FILE = "log-files.xml";
    private static final int LOG_SIZE;
    private static final int MAX_ERROR_REPORTS = 8;
    private static final java.lang.String METRIC_SHUTDOWN_TIME_START = "begin_shutdown";
    private static final java.lang.String METRIC_SYSTEM_SERVER = "shutdown_system_server";
    private static final java.lang.String[] MOUNT_DURATION_PROPS_POSTFIX;
    private static final java.lang.String OLD_UPDATER_CLASS = "com.google.android.systemupdater.SystemUpdateReceiver";
    private static final java.lang.String OLD_UPDATER_PACKAGE = "com.google.android.systemupdater";
    private static final java.lang.String SHUTDOWN_METRICS_FILE = "/data/system/shutdown-metrics.txt";
    private static final java.lang.String SHUTDOWN_TRON_METRICS_PREFIX = "shutdown_";
    private static final java.lang.String TAG = "BootReceiver";
    private static final java.lang.String TAG_TOMBSTONE = "SYSTEM_TOMBSTONE";
    private static final java.lang.String TAG_TOMBSTONE_PROTO = "SYSTEM_TOMBSTONE_PROTO";
    private static final java.lang.String TAG_TOMBSTONE_PROTO_WITH_HEADERS = "SYSTEM_TOMBSTONE_PROTO_WITH_HEADERS";
    private static final java.lang.String TAG_TRUNCATED = "[[TRUNCATED]]\n";
    private static final java.io.File TOMBSTONE_TMP_DIR;
    private static final int UMOUNT_STATUS_NOT_AVAILABLE = 4;
    private static final java.io.File lastHeaderFile;
    private static final com.android.server.am.DropboxRateLimiter sDropboxRateLimiter;
    private static final android.util.AtomicFile sFile;
    private static int sSentReports;

    static {
        LOG_SIZE = android.os.SystemProperties.getInt("ro.debuggable", 0) == 1 ? 98304 : 65536;
        LASTK_LOG_SIZE = android.os.SystemProperties.getInt("ro.debuggable", 0) == 1 ? GMSCORE_LASTK_LOG_SIZE : 65536;
        TOMBSTONE_TMP_DIR = new java.io.File("/data/tombstones");
        sFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), LOG_FILES_FILE), "log-files");
        lastHeaderFile = new java.io.File(android.os.Environment.getDataSystemDirectory(), LAST_HEADER_FILE);
        MOUNT_DURATION_PROPS_POSTFIX = new java.lang.String[]{"early", "default", "late"};
        LAST_KMSG_FILES = new java.lang.String[]{"/sys/fs/pstore/console-ramoops", "/proc/last_kmsg"};
        sSentReports = 0;
        sDropboxRateLimiter = new com.android.server.am.DropboxRateLimiter();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final android.content.Context context, android.content.Intent intent) {
        new java.lang.Thread() { // from class: com.android.server.BootReceiver.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    com.android.server.BootReceiver.this.logBootEvents(context);
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(com.android.server.BootReceiver.TAG, "Can't log boot events", e);
                }
                try {
                    com.android.server.BootReceiver.this.removeOldUpdatePackages(context);
                } catch (java.lang.Exception e2) {
                    android.util.Slog.e(com.android.server.BootReceiver.TAG, "Can't remove old update packages", e2);
                }
            }
        }.start();
        try {
            java.io.FileDescriptor open = android.system.Os.open(ERROR_REPORT_TRACE_PIPE, android.system.OsConstants.O_RDONLY, com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
            com.android.server.IoThread.get().getLooper().getQueue().addOnFileDescriptorEventListener(open, 1, new android.os.MessageQueue.OnFileDescriptorEventListener() { // from class: com.android.server.BootReceiver.2
                final int mBufferSize = 1024;
                byte[] mTraceBuffer = new byte[1024];

                @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                public int onFileDescriptorEvents(java.io.FileDescriptor fileDescriptor, int i) {
                    try {
                        if (android.system.Os.read(fileDescriptor, this.mTraceBuffer, 0, 1024) > 0 && new java.lang.String(this.mTraceBuffer).indexOf("\n") != -1 && com.android.server.BootReceiver.sSentReports < 8) {
                            android.os.SystemProperties.set("dmesgd.start", "1");
                            com.android.server.BootReceiver.sSentReports++;
                        }
                        return 1;
                    } catch (java.lang.Exception e) {
                        android.util.Slog.wtf(com.android.server.BootReceiver.TAG, "Error watching for trace events", e);
                        return 0;
                    }
                }
            });
        } catch (android.system.ErrnoException e) {
            android.util.Slog.wtf(TAG, "Could not open /sys/kernel/tracing/instances/bootreceiver/trace_pipe", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeOldUpdatePackages(android.content.Context context) {
        android.provider.Downloads.removeAllDownloadsByPackage(context, OLD_UPDATER_PACKAGE, OLD_UPDATER_CLASS);
    }

    private static java.lang.String getPreviousBootHeaders() {
        try {
            return android.os.FileUtils.readTextFile(lastHeaderFile, 0, null);
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private static java.lang.String getCurrentBootHeaders() throws java.io.IOException {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(512);
        sb.append("Build: ");
        sb.append(android.os.Build.FINGERPRINT);
        sb.append("\n");
        sb.append("Hardware: ");
        sb.append(android.os.Build.BOARD);
        sb.append("\n");
        sb.append("Revision: ");
        sb.append(android.os.SystemProperties.get("ro.revision", ""));
        sb.append("\n");
        sb.append("Bootloader: ");
        sb.append(android.os.Build.BOOTLOADER);
        sb.append("\n");
        sb.append("Radio: ");
        sb.append(android.os.Build.getRadioVersion());
        sb.append("\n");
        sb.append("Kernel: ");
        sb.append(android.os.FileUtils.readTextFile(new java.io.File("/proc/version"), 1024, "...\n"));
        sb.append("\n");
        return sb.toString();
    }

    private static java.lang.String getBootHeadersToLogAndUpdate() throws java.io.IOException {
        java.lang.String previousBootHeaders = getPreviousBootHeaders();
        java.lang.String currentBootHeaders = getCurrentBootHeaders();
        try {
            android.os.FileUtils.stringToFile(lastHeaderFile, currentBootHeaders);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error writing " + lastHeaderFile, e);
        }
        if (previousBootHeaders == null) {
            return "isPrevious: false\n" + currentBootHeaders;
        }
        return "isPrevious: true\n" + previousBootHeaders;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logBootEvents(android.content.Context context) throws java.io.IOException {
        java.lang.String str;
        android.os.DropBoxManager dropBoxManager = (android.os.DropBoxManager) context.getSystemService("dropbox");
        java.lang.String bootHeadersToLogAndUpdate = getBootHeadersToLogAndUpdate();
        java.lang.String str2 = android.os.SystemProperties.get("ro.boot.bootreason", (java.lang.String) null);
        java.lang.String handleAftermath = android.os.RecoverySystem.handleAftermath(context);
        if (handleAftermath != null && dropBoxManager != null) {
            dropBoxManager.addText("SYSTEM_RECOVERY_LOG", bootHeadersToLogAndUpdate + handleAftermath);
        }
        if (str2 == null) {
            str = "";
        } else {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(512);
            sb.append("\n");
            sb.append("Boot info:\n");
            sb.append("Last boot reason: ");
            sb.append(str2);
            sb.append("\n");
            str = sb.toString();
        }
        java.util.HashMap<java.lang.String, java.lang.Long> readTimestamps = readTimestamps();
        if (android.os.SystemProperties.getLong("ro.runtime.firstboot", 0L) == 0) {
            android.os.SystemProperties.set("ro.runtime.firstboot", java.lang.Long.toString(java.lang.System.currentTimeMillis()));
            if (dropBoxManager != null) {
                dropBoxManager.addText("SYSTEM_BOOT", bootHeadersToLogAndUpdate);
            }
            java.lang.String str3 = str;
            addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str3, "/proc/last_kmsg", -LASTK_LOG_SIZE, "SYSTEM_LAST_KMSG");
            addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str3, "/sys/fs/pstore/console-ramoops", -LASTK_LOG_SIZE, "SYSTEM_LAST_KMSG");
            addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str3, "/sys/fs/pstore/console-ramoops-0", -LASTK_LOG_SIZE, "SYSTEM_LAST_KMSG");
            addFileToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, "/cache/recovery/log", -LOG_SIZE, "SYSTEM_RECOVERY_LOG");
            addFileToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, "/cache/recovery/last_kmsg", -LOG_SIZE, "SYSTEM_RECOVERY_KMSG");
            addAuditErrorsToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, -LOG_SIZE, "SYSTEM_AUDIT");
        } else if (dropBoxManager != null) {
            dropBoxManager.addText("SYSTEM_RESTART", bootHeadersToLogAndUpdate);
        }
        logFsShutdownTime();
        logFsMountTime();
        addFsckErrorsToDropBoxAndLogFsStat(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, -LOG_SIZE, "SYSTEM_FSCK");
        logSystemServerShutdownTimeMetrics();
        writeTimestamps(readTimestamps);
    }

    public static void initDropboxRateLimiter() {
        sDropboxRateLimiter.init();
    }

    @com.android.internal.annotations.VisibleForTesting
    public static void resetDropboxRateLimiter() {
        sDropboxRateLimiter.reset();
    }

    public static void addTombstoneToDropBox(android.content.Context context, java.io.File file, boolean z, java.lang.String str, java.util.concurrent.locks.ReentrantLock reentrantLock) {
        android.os.DropBoxManager dropBoxManager = (android.os.DropBoxManager) context.getSystemService(android.os.DropBoxManager.class);
        if (dropBoxManager == null) {
            android.util.Slog.e(TAG, "Can't log tombstone: DropBoxManager not available");
            return;
        }
        com.android.server.am.DropboxRateLimiter.RateLimitResult shouldRateLimit = sDropboxRateLimiter.shouldRateLimit(z ? TAG_TOMBSTONE_PROTO_WITH_HEADERS : TAG_TOMBSTONE, str);
        if (shouldRateLimit.shouldRateLimit()) {
            return;
        }
        java.util.HashMap<java.lang.String, java.lang.Long> readTimestamps = readTimestamps();
        try {
            if (z) {
                if (recordFileTimestamp(file, readTimestamps)) {
                    reentrantLock.lock();
                    try {
                        addAugmentedProtoToDropbox(file, dropBoxManager, shouldRateLimit);
                        reentrantLock.unlock();
                    } catch (java.lang.Throwable th) {
                        reentrantLock.unlock();
                        throw th;
                    }
                }
            } else {
                addFileToDropBox(dropBoxManager, readTimestamps, getBootHeadersToLogAndUpdate() + shouldRateLimit.createHeader(), file.getPath(), LOG_SIZE, TAG_TOMBSTONE);
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Can't log tombstone", e);
        }
        writeTimestamps(readTimestamps);
    }

    private static void addAugmentedProtoToDropbox(java.io.File file, android.os.DropBoxManager dropBoxManager, com.android.server.am.DropboxRateLimiter.RateLimitResult rateLimitResult) throws java.io.IOException {
        android.os.ParcelFileDescriptor open;
        byte[] readAllBytes = java.nio.file.Files.readAllBytes(file.toPath());
        java.io.File createTempFile = java.io.File.createTempFile(file.getName(), ".tmp", TOMBSTONE_TMP_DIR);
        java.nio.file.Files.setPosixFilePermissions(createTempFile.toPath(), java.nio.file.attribute.PosixFilePermissions.fromString("rw-rw----"));
        try {
            try {
                open = android.os.ParcelFileDescriptor.open(createTempFile, 805306368);
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.e(TAG, "failed to open for write: " + createTempFile, e);
                throw e;
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "IO exception during write: " + createTempFile, e2);
            }
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(open.getFileDescriptor());
                protoOutputStream.write(1151051235329L, readAllBytes);
                protoOutputStream.write(1120986464258L, rateLimitResult.droppedCountSinceRateLimitActivated());
                protoOutputStream.flush();
                dropBoxManager.addFile(TAG_TOMBSTONE_PROTO_WITH_HEADERS, createTempFile, 0);
                open.close();
            } catch (java.lang.Throwable th) {
                if (open != null) {
                    try {
                        open.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } finally {
            createTempFile.delete();
        }
    }

    private static void addLastkToDropBox(android.os.DropBoxManager dropBoxManager, java.util.HashMap<java.lang.String, java.lang.Long> hashMap, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) throws java.io.IOException {
        int i2;
        int length = str.length() + TAG_TRUNCATED.length() + str2.length();
        if (LASTK_LOG_SIZE + length <= GMSCORE_LASTK_LOG_SIZE) {
            i2 = i;
        } else if (GMSCORE_LASTK_LOG_SIZE > length) {
            i2 = -(GMSCORE_LASTK_LOG_SIZE - length);
        } else {
            i2 = 0;
        }
        addFileWithFootersToDropBox(dropBoxManager, hashMap, str, str2, str3, i2, str4);
    }

    private static void addFileToDropBox(android.os.DropBoxManager dropBoxManager, java.util.HashMap<java.lang.String, java.lang.Long> hashMap, java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws java.io.IOException {
        addFileWithFootersToDropBox(dropBoxManager, hashMap, str, "", str2, i, str3);
    }

    private static void addFileWithFootersToDropBox(android.os.DropBoxManager dropBoxManager, java.util.HashMap<java.lang.String, java.lang.Long> hashMap, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) throws java.io.IOException {
        if (dropBoxManager == null || !dropBoxManager.isTagEnabled(str4)) {
            return;
        }
        java.io.File file = new java.io.File(str3);
        if (!recordFileTimestamp(file, hashMap)) {
            return;
        }
        java.lang.String readTextFile = android.os.FileUtils.readTextFile(file, i, TAG_TRUNCATED);
        java.lang.String str5 = str + readTextFile + str2;
        if (str4.equals(TAG_TOMBSTONE) && readTextFile.contains(">>> system_server <<<")) {
            addTextToDropBox(dropBoxManager, "system_server_native_crash", str5, str3, i);
        }
        if (str4.equals(TAG_TOMBSTONE)) {
            com.android.internal.util.FrameworkStatsLog.write(186);
        }
        addTextToDropBox(dropBoxManager, str4, str5, str3, i);
    }

    private static boolean recordFileTimestamp(java.io.File file, java.util.HashMap<java.lang.String, java.lang.Long> hashMap) {
        long lastModified = file.lastModified();
        if (lastModified <= 0) {
            return false;
        }
        java.lang.String path = file.getPath();
        if (hashMap.containsKey(path) && hashMap.get(path).longValue() == lastModified) {
            return false;
        }
        hashMap.put(path, java.lang.Long.valueOf(lastModified));
        return true;
    }

    private static void addTextToDropBox(android.os.DropBoxManager dropBoxManager, java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        android.util.Slog.i(TAG, "Copying " + str3 + " to DropBox (" + str + ")");
        dropBoxManager.addText(str, str2);
        android.util.EventLog.writeEvent(81002, str3, java.lang.Integer.valueOf(i), str);
    }

    private static void addAuditErrorsToDropBox(android.os.DropBoxManager dropBoxManager, java.util.HashMap<java.lang.String, java.lang.Long> hashMap, java.lang.String str, int i, java.lang.String str2) throws java.io.IOException {
        if (dropBoxManager == null || !dropBoxManager.isTagEnabled(str2)) {
            return;
        }
        android.util.Slog.i(TAG, "Copying audit failures to DropBox");
        java.io.File file = new java.io.File("/proc/last_kmsg");
        long lastModified = file.lastModified();
        if (lastModified <= 0) {
            file = new java.io.File("/sys/fs/pstore/console-ramoops");
            lastModified = file.lastModified();
            if (lastModified <= 0) {
                file = new java.io.File("/sys/fs/pstore/console-ramoops-0");
                lastModified = file.lastModified();
            }
        }
        if (lastModified <= 0) {
            return;
        }
        if (hashMap.containsKey(str2) && hashMap.get(str2).longValue() == lastModified) {
            return;
        }
        hashMap.put(str2, java.lang.Long.valueOf(lastModified));
        java.lang.String readTextFile = android.os.FileUtils.readTextFile(file, i, TAG_TRUNCATED);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (java.lang.String str3 : readTextFile.split("\n")) {
            if (str3.contains("audit")) {
                sb.append(str3 + "\n");
            }
        }
        android.util.Slog.i(TAG, "Copied " + sb.toString().length() + " worth of audits to DropBox");
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(str);
        sb2.append(sb.toString());
        dropBoxManager.addText(str2, sb2.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void addFsckErrorsToDropBoxAndLogFsStat(android.os.DropBoxManager dropBoxManager, java.util.HashMap<java.lang.String, java.lang.Long> hashMap, java.lang.String str, int i, java.lang.String str2) throws java.io.IOException {
        boolean z;
        java.io.File file;
        if (dropBoxManager != null && dropBoxManager.isTagEnabled(str2)) {
            z = true;
            android.util.Slog.i(TAG, "Checking for fsck errors");
            file = new java.io.File("/dev/fscklogs/log");
            if (file.lastModified() > 0) {
                return;
            }
            java.lang.String readTextFile = android.os.FileUtils.readTextFile(file, i, TAG_TRUNCATED);
            java.util.regex.Pattern compile = java.util.regex.Pattern.compile(FS_STAT_PATTERN);
            java.lang.String[] split = readTextFile.split("\n");
            boolean z2 = false;
            int i2 = 0;
            int i3 = 0;
            for (java.lang.String str3 : split) {
                if (str3.contains(E2FSCK_FS_MODIFIED) || str3.contains(F2FS_FSCK_FS_MODIFIED)) {
                    z2 = true;
                } else if (str3.contains("fs_stat")) {
                    java.util.regex.Matcher matcher = compile.matcher(str3);
                    if (matcher.find()) {
                        handleFsckFsStat(matcher, split, i2, i3);
                        i2 = i3;
                    } else {
                        android.util.Slog.w(TAG, "cannot parse fs_stat:" + str3);
                    }
                }
                i3++;
            }
            if (z && z2) {
                addFileToDropBox(dropBoxManager, hashMap, str, "/dev/fscklogs/log", i, str2);
            }
            file.renameTo(new java.io.File("/dev/fscklogs/fsck"));
            return;
        }
        z = false;
        android.util.Slog.i(TAG, "Checking for fsck errors");
        file = new java.io.File("/dev/fscklogs/log");
        if (file.lastModified() > 0) {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void logFsMountTime() {
        char c;
        int i;
        for (java.lang.String str : MOUNT_DURATION_PROPS_POSTFIX) {
            int i2 = android.os.SystemProperties.getInt("ro.boottime.init.mount_all." + str, 0);
            if (i2 != 0) {
                switch (str.hashCode()) {
                    case 3314342:
                        if (str.equals("late")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 96278371:
                        if (str.equals("early")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544803905:
                        if (str.equals("default")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = 11;
                        com.android.internal.util.FrameworkStatsLog.write(239, i, i2);
                        break;
                    case 1:
                        i = 10;
                        com.android.internal.util.FrameworkStatsLog.write(239, i, i2);
                        break;
                    case 2:
                        i = 12;
                        com.android.internal.util.FrameworkStatsLog.write(239, i, i2);
                        break;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void logSystemServerShutdownTimeMetrics() {
        java.lang.String readTextFile;
        java.io.File file = new java.io.File(SHUTDOWN_METRICS_FILE);
        java.lang.String str = null;
        if (file.exists()) {
            try {
                readTextFile = android.os.FileUtils.readTextFile(file, 0, null);
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Problem reading " + file, e);
            }
            if (!android.text.TextUtils.isEmpty(readTextFile)) {
                java.lang.String str2 = null;
                java.lang.String str3 = null;
                java.lang.String str4 = null;
                for (java.lang.String str5 : readTextFile.split(",")) {
                    java.lang.String[] split = str5.split(":");
                    if (split.length != 2) {
                        android.util.Slog.e(TAG, "Wrong format of shutdown metrics - " + readTextFile);
                    } else {
                        if (split[0].startsWith(SHUTDOWN_TRON_METRICS_PREFIX)) {
                            logTronShutdownMetric(split[0], split[1]);
                            if (split[0].equals(METRIC_SYSTEM_SERVER)) {
                                str4 = split[1];
                            }
                        }
                        if (split[0].equals("reboot")) {
                            str = split[1];
                        } else if (split[0].equals(com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_KEY)) {
                            str2 = split[1];
                        } else if (split[0].equals(METRIC_SHUTDOWN_TIME_START)) {
                            str3 = split[1];
                        }
                    }
                }
                logStatsdShutdownAtom(str, str2, str3, str4);
            }
            file.delete();
        }
        readTextFile = null;
        if (!android.text.TextUtils.isEmpty(readTextFile)) {
        }
        file.delete();
    }

    private static void logTronShutdownMetric(java.lang.String str, java.lang.String str2) {
        try {
            int parseInt = java.lang.Integer.parseInt(str2);
            if (parseInt >= 0) {
                com.android.internal.logging.MetricsLogger.histogram((android.content.Context) null, str, parseInt);
            }
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(TAG, "Cannot parse metric " + str + " int value - " + str2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void logStatsdShutdownAtom(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        boolean z;
        java.lang.String str5;
        long parseLong;
        long parseLong2;
        if (str == null) {
            android.util.Slog.e(TAG, "No value received for reboot");
        } else {
            if (str.equals("y")) {
                z = true;
                if (str2 != null) {
                    android.util.Slog.e(TAG, "No value received for shutdown reason");
                    str5 = "<EMPTY>";
                } else {
                    str5 = str2;
                }
                if (str3 != null) {
                    android.util.Slog.e(TAG, "No value received for shutdown start time");
                } else {
                    try {
                        parseLong = java.lang.Long.parseLong(str3);
                    } catch (java.lang.NumberFormatException e) {
                        android.util.Slog.e(TAG, "Cannot parse shutdown start time: " + str3);
                    }
                    if (str4 == null) {
                        android.util.Slog.e(TAG, "No value received for shutdown duration");
                    } else {
                        try {
                            parseLong2 = java.lang.Long.parseLong(str4);
                        } catch (java.lang.NumberFormatException e2) {
                            android.util.Slog.e(TAG, "Cannot parse shutdown duration: " + str3);
                        }
                        com.android.internal.util.FrameworkStatsLog.write(56, z, str5, parseLong, parseLong2);
                    }
                    parseLong2 = 0;
                    com.android.internal.util.FrameworkStatsLog.write(56, z, str5, parseLong, parseLong2);
                }
                parseLong = 0;
                if (str4 == null) {
                }
                parseLong2 = 0;
                com.android.internal.util.FrameworkStatsLog.write(56, z, str5, parseLong, parseLong2);
            }
            if (!str.equals("n")) {
                android.util.Slog.e(TAG, "Unexpected value for reboot : " + str);
            }
        }
        z = false;
        if (str2 != null) {
        }
        if (str3 != null) {
        }
        parseLong = 0;
        if (str4 == null) {
        }
        parseLong2 = 0;
        com.android.internal.util.FrameworkStatsLog.write(56, z, str5, parseLong, parseLong2);
    }

    private static void logFsShutdownTime() {
        java.io.File file;
        java.lang.String[] strArr = LAST_KMSG_FILES;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                file = null;
                break;
            }
            file = new java.io.File(strArr[i]);
            if (file.exists()) {
                break;
            } else {
                i++;
            }
        }
        if (file == null) {
            return;
        }
        try {
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(LAST_SHUTDOWN_TIME_PATTERN, 8).matcher(android.os.FileUtils.readTextFile(file, -16384, null));
            if (matcher.find()) {
                com.android.internal.util.FrameworkStatsLog.write(239, 9, java.lang.Integer.parseInt(matcher.group(1)));
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ERROR_CODE_REPORTED, 2, java.lang.Integer.parseInt(matcher.group(2)));
                android.util.Slog.i(TAG, "boot_fs_shutdown," + matcher.group(1) + "," + matcher.group(2));
                return;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ERROR_CODE_REPORTED, 2, 4);
            android.util.Slog.w(TAG, "boot_fs_shutdown, string not found");
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "cannot read last msg", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static int fixFsckFsStat(java.lang.String str, int i, java.lang.String[] strArr, int i2, int i3) {
        java.lang.String str2;
        boolean z;
        if ((i & 1024) != 0) {
            java.util.regex.Pattern compile = java.util.regex.Pattern.compile(FSCK_PASS_PATTERN);
            java.util.regex.Pattern compile2 = java.util.regex.Pattern.compile(FSCK_TREE_OPTIMIZATION_PATTERN);
            java.lang.String str3 = "";
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            int i4 = i2;
            while (i4 < i3) {
                str2 = strArr[i4];
                if (str2.contains(E2FSCK_FS_MODIFIED) || str2.contains(F2FS_FSCK_FS_MODIFIED)) {
                    break;
                }
                if (str2.startsWith("Pass ")) {
                    java.util.regex.Matcher matcher = compile.matcher(str2);
                    if (matcher.find()) {
                        str3 = matcher.group(1);
                    }
                } else if (str2.startsWith("Inode ")) {
                    if (compile2.matcher(str2).find() && str3.equals("1")) {
                        android.util.Slog.i(TAG, "fs_stat, partition:" + str + " found tree optimization:" + str2);
                        z2 = true;
                    } else {
                        z = true;
                        break;
                    }
                } else if (str2.startsWith("[QUOTA WARNING]") && str3.equals("5")) {
                    android.util.Slog.i(TAG, "fs_stat, partition:" + str + " found quota warning:" + str2);
                    if (z2) {
                        z3 = true;
                    } else {
                        z = false;
                        z3 = true;
                        break;
                    }
                } else if (!str2.startsWith("Update quota info") || !str3.equals("5")) {
                    if (str2.startsWith("Timestamp(s) on inode") && str2.contains("beyond 2310-04-04 are likely pre-1970") && str3.equals("1")) {
                        android.util.Slog.i(TAG, "fs_stat, partition:" + str + " found timestamp adjustment:" + str2);
                        int i5 = i4 + 1;
                        if (strArr[i5].contains("Fix? yes")) {
                            i4 = i5;
                        }
                        z4 = true;
                    } else {
                        str2 = str2.trim();
                        if (!str2.isEmpty() && !str3.isEmpty()) {
                            z = true;
                            break;
                        }
                    }
                }
                i4++;
            }
            str2 = null;
            z = false;
            if (z) {
                if (str2 != null) {
                    android.util.Slog.i(TAG, "fs_stat, partition:" + str + " fix:" + str2);
                }
            } else if (z3 && !z2) {
                android.util.Slog.i(TAG, "fs_stat, got quota fix without tree optimization, partition:" + str);
            } else if ((z2 && z3) || z4) {
                android.util.Slog.i(TAG, "fs_stat, partition:" + str + " fix ignored");
                return i & (-1025);
            }
        }
        return i;
    }

    private static void handleFsckFsStat(java.util.regex.Matcher matcher, java.lang.String[] strArr, int i, int i2) {
        java.lang.String group = matcher.group(1);
        try {
            int fixFsckFsStat = fixFsckFsStat(group, java.lang.Integer.decode(matcher.group(2)).intValue(), strArr, i, i2);
            if ("userdata".equals(group) || "data".equals(group)) {
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ERROR_CODE_REPORTED, 3, fixFsckFsStat);
            }
            android.util.Slog.i(TAG, "fs_stat, partition:" + group + " stat:0x" + java.lang.Integer.toHexString(fixFsckFsStat));
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.w(TAG, "cannot parse fs_stat: partition:" + group + " stat:" + matcher.group(2));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x015a, code lost:
    
        if (r2 != false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0135, code lost:
    
        if (r7 != false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e0, code lost:
    
        if (r7 != false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x003e, code lost:
    
        if (r6 != 4) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x004c, code lost:
    
        if (r4.getName().equals("log") == false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0064, code lost:
    
        android.util.Slog.w(com.android.server.BootReceiver.TAG, "Unknown tag: " + r4.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x004e, code lost:
    
        r1.put(r4.getAttributeValue((java.lang.String) null, "filename"), java.lang.Long.valueOf(r4.getAttributeLong((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP)));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static java.util.HashMap<java.lang.String, java.lang.Long> readTimestamps() {
        java.util.HashMap<java.lang.String, java.lang.Long> hashMap;
        boolean z;
        java.lang.Throwable th;
        org.xmlpull.v1.XmlPullParserException e;
        java.lang.NullPointerException e2;
        java.lang.IllegalStateException e3;
        java.io.IOException e4;
        java.io.FileInputStream openRead;
        int next;
        synchronized (sFile) {
            hashMap = new java.util.HashMap<>();
            boolean z2 = false;
            try {
            } catch (java.lang.Throwable th2) {
                z = false;
                th = th2;
            }
            try {
                try {
                    openRead = sFile.openRead();
                } catch (java.io.FileNotFoundException e5) {
                } catch (java.io.IOException e6) {
                    z = false;
                    e4 = e6;
                } catch (java.lang.IllegalStateException e7) {
                    z = false;
                    e3 = e7;
                } catch (java.lang.NullPointerException e8) {
                    z = false;
                    e2 = e8;
                } catch (org.xmlpull.v1.XmlPullParserException e9) {
                    z = false;
                    e = e9;
                }
                try {
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                    do {
                        next = resolvePullParser.next();
                        z = true;
                        if (next == 2) {
                            break;
                        }
                    } while (next != 1);
                    if (next != 2) {
                        throw new java.lang.IllegalStateException("no start tag found");
                    }
                    int depth = resolvePullParser.getDepth();
                    while (true) {
                        int next2 = resolvePullParser.next();
                        if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                            break;
                        }
                    }
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.io.FileNotFoundException e10) {
                            z2 = true;
                            android.util.Slog.i(TAG, "No existing last log timestamp file " + sFile.getBaseFile() + "; starting empty");
                        } catch (java.io.IOException e11) {
                            e4 = e11;
                            android.util.Slog.w(TAG, "Failed parsing " + e4);
                        } catch (java.lang.IllegalStateException e12) {
                            e3 = e12;
                            android.util.Slog.w(TAG, "Failed parsing " + e3);
                            if (!z) {
                                hashMap.clear();
                            }
                            return hashMap;
                        } catch (java.lang.NullPointerException e13) {
                            e2 = e13;
                            android.util.Slog.w(TAG, "Failed parsing " + e2);
                            if (!z) {
                                hashMap.clear();
                            }
                            return hashMap;
                        } catch (org.xmlpull.v1.XmlPullParserException e14) {
                            e = e14;
                            android.util.Slog.w(TAG, "Failed parsing " + e);
                        }
                    }
                } finally {
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
                if (!z) {
                    hashMap.clear();
                }
                throw th;
            }
        }
        return hashMap;
    }

    private static void writeTimestamps(java.util.HashMap<java.lang.String, java.lang.Long> hashMap) {
        synchronized (sFile) {
            try {
                try {
                    java.io.FileOutputStream startWrite = sFile.startWrite();
                    try {
                        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((java.lang.String) null, true);
                        resolveSerializer.startTag((java.lang.String) null, "log-files");
                        for (java.lang.String str : hashMap.keySet()) {
                            resolveSerializer.startTag((java.lang.String) null, "log");
                            resolveSerializer.attribute((java.lang.String) null, "filename", str);
                            resolveSerializer.attributeLong((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP, hashMap.get(str).longValue());
                            resolveSerializer.endTag((java.lang.String) null, "log");
                        }
                        resolveSerializer.endTag((java.lang.String) null, "log-files");
                        resolveSerializer.endDocument();
                        sFile.finishWrite(startWrite);
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(TAG, "Failed to write timestamp file, using the backup: " + e);
                        sFile.failWrite(startWrite);
                    }
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(TAG, "Failed to write timestamp file: " + e2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
