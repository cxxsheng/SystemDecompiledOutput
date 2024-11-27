package com.android.server.am;

/* loaded from: classes.dex */
public class StackTracesDumpHelper {
    static final java.lang.String ANR_FILE_PREFIX = "anr_";
    static final java.lang.String ANR_TEMP_FILE_PREFIX = "temp_anr_";
    public static final java.lang.String ANR_TRACE_DIR = "/data/anr";
    private static final int JAVA_DUMP_MINIMUM_SIZE = 100;
    static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.GuardedBy({"StackTracesDumpHelper.class"})
    private static final java.text.SimpleDateFormat ANR_FILE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
    private static final int NATIVE_DUMP_TIMEOUT_MS = android.os.Build.HW_TIMEOUT_MULTIPLIER * 2000;
    private static final int TEMP_DUMP_TIME_LIMIT = android.os.Build.HW_TIMEOUT_MULTIPLIER * 10000;

    public static java.io.File dumpStackTraces(java.util.ArrayList<java.lang.Integer> arrayList, com.android.internal.os.ProcessCpuTracker processCpuTracker, android.util.SparseBooleanArray sparseBooleanArray, java.util.concurrent.Future<java.util.ArrayList<java.lang.Integer>> future, java.io.StringWriter stringWriter, @android.annotation.NonNull java.util.concurrent.Executor executor, com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        return dumpStackTraces(arrayList, processCpuTracker, sparseBooleanArray, future, stringWriter, null, null, null, null, executor, null, anrLatencyTracker);
    }

    public static java.io.File dumpStackTraces(java.util.ArrayList<java.lang.Integer> arrayList, com.android.internal.os.ProcessCpuTracker processCpuTracker, android.util.SparseBooleanArray sparseBooleanArray, java.util.concurrent.Future<java.util.ArrayList<java.lang.Integer>> future, java.io.StringWriter stringWriter, java.lang.String str, java.lang.String str2, @android.annotation.NonNull java.util.concurrent.Executor executor, com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        return dumpStackTraces(arrayList, processCpuTracker, sparseBooleanArray, future, stringWriter, null, str, str2, null, executor, null, anrLatencyTracker);
    }

    static java.io.File dumpStackTraces(java.util.ArrayList<java.lang.Integer> arrayList, final com.android.internal.os.ProcessCpuTracker processCpuTracker, final android.util.SparseBooleanArray sparseBooleanArray, java.util.concurrent.Future<java.util.ArrayList<java.lang.Integer>> future, java.io.StringWriter stringWriter, java.util.concurrent.atomic.AtomicLong atomicLong, java.lang.String str, java.lang.String str2, java.lang.String str3, @android.annotation.NonNull java.util.concurrent.Executor executor, java.util.concurrent.Future<java.io.File> future2, final com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        java.lang.String str4;
        java.lang.String str5;
        if (anrLatencyTracker != null) {
            try {
                anrLatencyTracker.dumpStackTracesStarted();
            } catch (java.lang.Throwable th) {
                if (anrLatencyTracker != null) {
                    anrLatencyTracker.dumpStackTracesEnded();
                }
                throw th;
            }
        }
        android.util.Slog.i(TAG, "dumpStackTraces pids=" + sparseBooleanArray);
        java.util.function.Supplier supplier = processCpuTracker != null ? new java.util.function.Supplier() { // from class: com.android.server.am.StackTracesDumpHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.util.ArrayList extraPids;
                extraPids = com.android.server.am.StackTracesDumpHelper.getExtraPids(processCpuTracker, sparseBooleanArray, anrLatencyTracker);
                return extraPids;
            }
        } : null;
        java.util.concurrent.CompletableFuture supplyAsync = supplier != null ? java.util.concurrent.CompletableFuture.supplyAsync(supplier, executor) : null;
        java.io.File file = new java.io.File(ANR_TRACE_DIR);
        try {
            java.io.File createAnrDumpFile = createAnrDumpFile(file);
            if (str != null || str2 != null || str3 != null) {
                java.lang.String absolutePath = createAnrDumpFile.getAbsolutePath();
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                if (str != null) {
                    str4 = "Subject: " + str + "\n";
                } else {
                    str4 = "";
                }
                sb.append(str4);
                if (str3 != null) {
                    str5 = str3 + "\n\n";
                } else {
                    str5 = "";
                }
                sb.append(str5);
                sb.append(str2 != null ? str2 : "");
                appendtoANRFile(absolutePath, sb.toString());
            }
            long dumpStackTraces = dumpStackTraces(createAnrDumpFile.getAbsolutePath(), arrayList, future, supplyAsync, future2, anrLatencyTracker);
            if (atomicLong != null) {
                atomicLong.set(dumpStackTraces);
            }
            maybePruneOldTraces(file);
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpStackTracesEnded();
            }
            return createAnrDumpFile;
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Exception creating ANR dump file:", e);
            if (stringWriter != null) {
                stringWriter.append("----- Exception creating ANR dump file -----\n");
                e.printStackTrace(new java.io.PrintWriter(stringWriter));
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.anrSkippedDumpStackTraces();
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpStackTracesEnded();
            }
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v16, types: [int] */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    public static long dumpStackTraces(java.lang.String str, java.util.ArrayList<java.lang.Integer> arrayList, java.util.concurrent.Future<java.util.ArrayList<java.lang.Integer>> future, java.util.concurrent.Future<java.util.ArrayList<java.lang.Integer>> future2, java.util.concurrent.Future<java.io.File> future3, com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        boolean z;
        android.util.Slog.i(TAG, "Dumping to " + str);
        long j = (long) (android.os.Build.HW_TIMEOUT_MULTIPLIER * 20000);
        long j2 = -1;
        if (future3 != null && arrayList != null && arrayList.size() > 0) {
            int intValue = arrayList.get(0).intValue();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            z = copyFirstPidTempDump(str, future3, j, anrLatencyTracker);
            j -= android.os.SystemClock.elapsedRealtime() - elapsedRealtime;
            if (j <= 0) {
                android.util.Slog.e(TAG, "Aborting stack trace dump (currently copying primary pid" + intValue + "); deadline exceeded.");
                return -1L;
            }
            if (z && intValue != com.android.server.am.ActivityManagerService.MY_PID) {
                j2 = new java.io.File(str).length();
            }
            if (z && anrLatencyTracker != null) {
                appendtoANRFile(str, anrLatencyTracker.dumpAsCommaSeparatedArrayWithHeader());
            }
        } else {
            z = false;
        }
        if (arrayList != null) {
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingFirstPidsStarted();
            }
            int size = arrayList.size();
            ?? r3 = z;
            while (r3 < size) {
                int intValue2 = arrayList.get(r3).intValue();
                boolean z2 = r3 == 0 && com.android.server.am.ActivityManagerService.MY_PID != intValue2;
                android.util.Slog.i(TAG, "Collecting stacks for pid " + intValue2);
                j -= dumpJavaTracesTombstoned(intValue2, str, j, anrLatencyTracker);
                if (j <= 0) {
                    android.util.Slog.e(TAG, "Aborting stack trace dump (current firstPid=" + intValue2 + "); deadline exceeded.");
                    return j2;
                }
                if (z2) {
                    long length = new java.io.File(str).length();
                    if (anrLatencyTracker != null) {
                        appendtoANRFile(str, anrLatencyTracker.dumpAsCommaSeparatedArrayWithHeader());
                    }
                    j2 = length;
                }
                r3++;
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingFirstPidsEnded();
            }
        }
        java.util.ArrayList<java.lang.Integer> collectPids = collectPids(future, "native pids");
        android.util.Slog.i(TAG, "dumpStackTraces nativepids=" + collectPids);
        if (collectPids != null) {
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingNativePidsStarted();
            }
            java.util.Iterator<java.lang.Integer> it = collectPids.iterator();
            while (it.hasNext()) {
                int intValue3 = it.next().intValue();
                android.util.Slog.i(TAG, "Collecting stacks for native pid " + intValue3);
                long min = java.lang.Math.min((long) NATIVE_DUMP_TIMEOUT_MS, j);
                if (anrLatencyTracker != null) {
                    anrLatencyTracker.dumpingPidStarted(intValue3);
                }
                long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                android.os.Debug.dumpNativeBacktraceToFileTimeout(intValue3, str, (int) (min / 1000));
                long elapsedRealtime3 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime2;
                if (anrLatencyTracker != null) {
                    anrLatencyTracker.dumpingPidEnded();
                }
                j -= elapsedRealtime3;
                if (j <= 0) {
                    android.util.Slog.e(TAG, "Aborting stack trace dump (current native pid=" + intValue3 + "); deadline exceeded.");
                    return j2;
                }
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingNativePidsEnded();
            }
        }
        java.util.ArrayList<java.lang.Integer> collectPids2 = collectPids(future2, "extra pids");
        if (future2 != null) {
            try {
                collectPids2 = future2.get();
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.w(TAG, "Interrupted while collecting extra pids", e);
            } catch (java.util.concurrent.ExecutionException e2) {
                android.util.Slog.w(TAG, "Failed to collect extra pids", e2.getCause());
            }
        }
        android.util.Slog.i(TAG, "dumpStackTraces extraPids=" + collectPids2);
        if (collectPids2 != null) {
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingExtraPidsStarted();
            }
            java.util.Iterator<java.lang.Integer> it2 = collectPids2.iterator();
            while (it2.hasNext()) {
                int intValue4 = it2.next().intValue();
                android.util.Slog.i(TAG, "Collecting stacks for extra pid " + intValue4);
                j -= dumpJavaTracesTombstoned(intValue4, str, j, anrLatencyTracker);
                if (j <= 0) {
                    android.util.Slog.e(TAG, "Aborting stack trace dump (current extra pid=" + intValue4 + "); deadline exceeded.");
                    return j2;
                }
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingExtraPidsEnded();
            }
        }
        appendtoANRFile(str, "----- dumping ended at " + android.os.SystemClock.uptimeMillis() + "\n");
        android.util.Slog.i(TAG, "Done dumping");
        return j2;
    }

    public static java.io.File dumpStackTracesTempFile(int i, com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        if (anrLatencyTracker != null) {
            try {
                anrLatencyTracker.dumpStackTracesTempFileStarted();
            } catch (java.lang.Throwable th) {
                if (anrLatencyTracker != null) {
                    anrLatencyTracker.dumpStackTracesTempFileEnded();
                }
                throw th;
            }
        }
        try {
            java.io.File createTempFile = java.io.File.createTempFile(ANR_TEMP_FILE_PREFIX, ".txt", new java.io.File(ANR_TRACE_DIR));
            android.util.Slog.d(TAG, "created ANR temporary file:" + createTempFile.getAbsolutePath());
            android.util.Slog.i(TAG, "Collecting stacks for pid " + i + " into temporary file " + createTempFile.getName());
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingPidStarted(i);
            }
            long dumpJavaTracesTombstoned = dumpJavaTracesTombstoned(i, createTempFile.getAbsolutePath(), TEMP_DUMP_TIME_LIMIT);
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpingPidEnded();
            }
            if (TEMP_DUMP_TIME_LIMIT <= dumpJavaTracesTombstoned) {
                android.util.Slog.e(TAG, "Aborted stack trace dump (current primary pid=" + i + "); deadline exceeded.");
                if (anrLatencyTracker != null) {
                    anrLatencyTracker.dumpStackTracesTempFileTimedOut();
                }
            }
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpStackTracesTempFileEnded();
            }
            return createTempFile;
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Exception creating temporary ANR dump file:", e);
            if (anrLatencyTracker != null) {
                anrLatencyTracker.dumpStackTracesTempFileCreationFailed();
            }
            if (anrLatencyTracker == null) {
                return null;
            }
            anrLatencyTracker.dumpStackTracesTempFileEnded();
            return null;
        }
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0031: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r3 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:51:0x0031 */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean copyFirstPidTempDump(java.lang.String str, java.util.concurrent.Future<java.io.File> future, long j, com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        try {
            try {
                z = true;
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(str, true);
                if (anrLatencyTracker != null) {
                    try {
                        anrLatencyTracker.copyingFirstPidStarted();
                    } catch (java.lang.Throwable th) {
                        try {
                            fileOutputStream.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                java.io.File file = future.get(j, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (file == null) {
                    fileOutputStream.close();
                    if (anrLatencyTracker != null) {
                        anrLatencyTracker.copyingFirstPidEnded(false);
                    }
                    return false;
                }
                java.nio.file.Files.copy(file.toPath(), fileOutputStream);
                file.delete();
                try {
                    fileOutputStream.close();
                    if (anrLatencyTracker != null) {
                        anrLatencyTracker.copyingFirstPidEnded(true);
                    }
                    return true;
                } catch (java.io.IOException e) {
                    e = e;
                    android.util.Slog.w(TAG, "Failed to read the first pid's predump file", e);
                    if (anrLatencyTracker != null) {
                        anrLatencyTracker.copyingFirstPidEnded(z);
                    }
                    return false;
                } catch (java.lang.InterruptedException e2) {
                    e = e2;
                    android.util.Slog.w(TAG, "Interrupted while collecting the first pid's predump to the main ANR file", e);
                    if (anrLatencyTracker != null) {
                        anrLatencyTracker.copyingFirstPidEnded(z);
                    }
                    return false;
                } catch (java.util.concurrent.ExecutionException e3) {
                    e = e3;
                    android.util.Slog.w(TAG, "Failed to collect the first pid's predump to the main ANR file", e.getCause());
                    if (anrLatencyTracker != null) {
                        anrLatencyTracker.copyingFirstPidEnded(z);
                    }
                    return false;
                } catch (java.util.concurrent.TimeoutException e4) {
                    e = e4;
                    android.util.Slog.w(TAG, "Copying the first pid timed out", e);
                    if (anrLatencyTracker != null) {
                        anrLatencyTracker.copyingFirstPidEnded(z);
                    }
                    return false;
                }
            } catch (java.io.IOException e5) {
                e = e5;
                z = false;
            } catch (java.lang.InterruptedException e6) {
                e = e6;
                z = false;
            } catch (java.util.concurrent.ExecutionException e7) {
                e = e7;
                z = false;
            } catch (java.util.concurrent.TimeoutException e8) {
                e = e8;
                z = false;
            } catch (java.lang.Throwable th3) {
                th = th3;
                if (anrLatencyTracker != null) {
                }
                throw th;
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
            z3 = z2;
            if (anrLatencyTracker != null) {
                anrLatencyTracker.copyingFirstPidEnded(z3);
            }
            throw th;
        }
    }

    private static synchronized java.io.File createAnrDumpFile(java.io.File file) throws java.io.IOException {
        java.io.File file2;
        synchronized (com.android.server.am.StackTracesDumpHelper.class) {
            file2 = new java.io.File(file, ANR_FILE_PREFIX + ANR_FILE_DATE_FORMAT.format(new java.util.Date()));
            if (file2.createNewFile()) {
                android.os.FileUtils.setPermissions(file2.getAbsolutePath(), com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT, -1, -1);
            } else {
                throw new java.io.IOException("Unable to create ANR dump file: createNewFile failed");
            }
        }
        return file2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.ArrayList<java.lang.Integer> getExtraPids(com.android.internal.os.ProcessCpuTracker processCpuTracker, android.util.SparseBooleanArray sparseBooleanArray, com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        if (anrLatencyTracker != null) {
            anrLatencyTracker.processCpuTrackerMethodsCalled();
        }
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
        synchronized (processCpuTracker) {
            processCpuTracker.init();
        }
        try {
            java.lang.Thread.sleep(200L);
        } catch (java.lang.InterruptedException e) {
        }
        synchronized (processCpuTracker) {
            try {
                processCpuTracker.update();
                int countWorkingStats = processCpuTracker.countWorkingStats();
                for (int i = 0; i < countWorkingStats && arrayList.size() < 2; i++) {
                    com.android.internal.os.ProcessCpuTracker.Stats workingStats = processCpuTracker.getWorkingStats(i);
                    if (sparseBooleanArray.indexOfKey(workingStats.pid) >= 0) {
                        arrayList.add(java.lang.Integer.valueOf(workingStats.pid));
                    } else {
                        android.util.Slog.i(TAG, "Skipping next CPU consuming process, not a java proc: " + workingStats.pid);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (anrLatencyTracker != null) {
            anrLatencyTracker.processCpuTrackerMethodsReturned();
        }
        return arrayList;
    }

    private static void maybePruneOldTraces(java.io.File file) {
        java.io.File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        int i = android.os.SystemProperties.getInt("tombstoned.max_anr_count", 64);
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        try {
            java.util.Arrays.sort(listFiles, java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.am.StackTracesDumpHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(java.lang.Object obj) {
                    return ((java.io.File) obj).lastModified();
                }
            }).reversed());
            for (int i2 = 0; i2 < listFiles.length; i2++) {
                if ((i2 > i || currentTimeMillis - listFiles[i2].lastModified() > 86400000) && !listFiles[i2].delete()) {
                    android.util.Slog.w(TAG, "Unable to prune stale trace file: " + listFiles[i2]);
                }
            }
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "tombstone modification times changed while sorting; not pruning", e);
        }
    }

    private static long dumpJavaTracesTombstoned(int i, java.lang.String str, long j, com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        if (anrLatencyTracker != null) {
            try {
                anrLatencyTracker.dumpingPidStarted(i);
            } catch (java.lang.Throwable th) {
                if (anrLatencyTracker != null) {
                    anrLatencyTracker.dumpingPidEnded();
                }
                throw th;
            }
        }
        long dumpJavaTracesTombstoned = dumpJavaTracesTombstoned(i, str, j);
        if (anrLatencyTracker != null) {
            anrLatencyTracker.dumpingPidEnded();
        }
        return dumpJavaTracesTombstoned;
    }

    private static long dumpJavaTracesTombstoned(int i, java.lang.String str, long j) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        int writeUptimeStartHeaderForPid = writeUptimeStartHeaderForPid(i, str);
        boolean dumpJavaBacktraceToFileTimeout = android.os.Debug.dumpJavaBacktraceToFileTimeout(i, str, (int) (j / 1000));
        if (dumpJavaBacktraceToFileTimeout) {
            try {
                if (new java.io.File(str).length() - writeUptimeStartHeaderForPid < 100) {
                    android.util.Slog.w(TAG, "Successfully created Java ANR file is empty!");
                    dumpJavaBacktraceToFileTimeout = false;
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.w(TAG, "Unable to get ANR file size", e);
                dumpJavaBacktraceToFileTimeout = false;
            }
        }
        if (!dumpJavaBacktraceToFileTimeout) {
            android.util.Slog.w(TAG, "Dumping Java threads failed, initiating native stack dump.");
            if (!android.os.Debug.dumpNativeBacktraceToFileTimeout(i, str, NATIVE_DUMP_TIMEOUT_MS / 1000)) {
                android.util.Slog.w(TAG, "Native stack dump failed!");
            }
        }
        return android.os.SystemClock.elapsedRealtime() - elapsedRealtime;
    }

    private static int appendtoANRFile(java.lang.String str, java.lang.String str2) {
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(str, true);
            try {
                byte[] bytes = str2.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                fileOutputStream.write(bytes);
                int length = bytes.length;
                fileOutputStream.close();
                return length;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Exception writing to ANR dump file:", e);
            return 0;
        }
    }

    private static int writeUptimeStartHeaderForPid(int i, java.lang.String str) {
        return appendtoANRFile(str, "----- dumping pid: " + i + " at " + android.os.SystemClock.uptimeMillis() + "\n");
    }

    private static java.util.ArrayList<java.lang.Integer> collectPids(java.util.concurrent.Future<java.util.ArrayList<java.lang.Integer>> future, java.lang.String str) {
        if (future == null) {
            return null;
        }
        try {
            return future.get();
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.w(TAG, "Interrupted while collecting " + str, e);
            return null;
        } catch (java.util.concurrent.ExecutionException e2) {
            android.util.Slog.w(TAG, "Failed to collect " + str, e2.getCause());
            return null;
        }
    }
}
