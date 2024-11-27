package com.android.server.criticalevents;

/* loaded from: classes.dex */
public class CriticalEventLog {
    private static final int AID_SYSTEM = 1000;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String FILENAME = "critical_event_log.pb";
    private static final java.lang.String TAG = com.android.server.criticalevents.CriticalEventLog.class.getSimpleName();
    private static com.android.server.criticalevents.CriticalEventLog sInstance;
    private final com.android.server.criticalevents.CriticalEventLog.ThreadSafeRingBuffer<com.android.server.criticalevents.nano.CriticalEventProto> mEvents;
    private final android.os.Handler mHandler;
    private long mLastSaveAttemptMs;
    private final boolean mLoadAndSaveImmediately;
    private final java.io.File mLogFile;
    private final long mMinTimeBetweenSavesMs;
    private final java.lang.Runnable mSaveRunnable;
    private final int mWindowMs;

    protected interface ILogLoader {
        void load(java.io.File file, com.android.server.criticalevents.CriticalEventLog.ThreadSafeRingBuffer<com.android.server.criticalevents.nano.CriticalEventProto> threadSafeRingBuffer);
    }

    @com.android.internal.annotations.VisibleForTesting
    CriticalEventLog(java.lang.String str, int i, int i2, long j, boolean z, final com.android.server.criticalevents.CriticalEventLog.ILogLoader iLogLoader) {
        this.mLastSaveAttemptMs = 0L;
        this.mSaveRunnable = new java.lang.Runnable() { // from class: com.android.server.criticalevents.CriticalEventLog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.criticalevents.CriticalEventLog.this.saveLogToFileNow();
            }
        };
        this.mLogFile = java.nio.file.Paths.get(str, FILENAME).toFile();
        this.mWindowMs = i2;
        this.mMinTimeBetweenSavesMs = j;
        this.mLoadAndSaveImmediately = z;
        this.mEvents = new com.android.server.criticalevents.CriticalEventLog.ThreadSafeRingBuffer<>(com.android.server.criticalevents.nano.CriticalEventProto.class, i);
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("CriticalEventLogIO");
        handlerThread.start();
        this.mHandler = new android.os.Handler(handlerThread.getLooper());
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.criticalevents.CriticalEventLog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.criticalevents.CriticalEventLog.this.lambda$new$0(iLogLoader);
            }
        };
        if (!this.mLoadAndSaveImmediately) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(com.android.server.criticalevents.CriticalEventLog.ILogLoader iLogLoader) {
        iLogLoader.load(this.mLogFile, this.mEvents);
    }

    private CriticalEventLog() {
        this("/data/misc/critical-events", 20, (int) java.time.Duration.ofMinutes(5L).toMillis(), java.time.Duration.ofSeconds(2L).toMillis(), false, new com.android.server.criticalevents.CriticalEventLog.LogLoader());
    }

    public static com.android.server.criticalevents.CriticalEventLog getInstance() {
        if (sInstance == null) {
            sInstance = new com.android.server.criticalevents.CriticalEventLog();
        }
        return sInstance;
    }

    public static void init() {
        getInstance();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long getWallTimeMillis() {
        return java.lang.System.currentTimeMillis();
    }

    public void logExcessiveBinderCalls(int i) {
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls excessiveBinderCalls = new com.android.server.criticalevents.nano.CriticalEventProto.ExcessiveBinderCalls();
        excessiveBinderCalls.uid = i;
        criticalEventProto.setExcessiveBinderCalls(excessiveBinderCalls);
        log(criticalEventProto);
    }

    public void logInstallPackagesStarted() {
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        criticalEventProto.setInstallPackages(new com.android.server.criticalevents.nano.CriticalEventProto.InstallPackages());
        log(criticalEventProto);
    }

    public void logSystemServerStarted() {
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        criticalEventProto.setSystemServerStarted(new com.android.server.criticalevents.nano.CriticalEventProto.SystemServerStarted());
        log(criticalEventProto);
    }

    public void logWatchdog(java.lang.String str, java.util.UUID uuid) {
        com.android.server.criticalevents.nano.CriticalEventProto.Watchdog watchdog = new com.android.server.criticalevents.nano.CriticalEventProto.Watchdog();
        watchdog.subject = str;
        watchdog.uuid = uuid.toString();
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        criticalEventProto.setWatchdog(watchdog);
        log(criticalEventProto);
    }

    public void logHalfWatchdog(java.lang.String str) {
        com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog halfWatchdog = new com.android.server.criticalevents.nano.CriticalEventProto.HalfWatchdog();
        halfWatchdog.subject = str;
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        criticalEventProto.setHalfWatchdog(halfWatchdog);
        log(criticalEventProto);
    }

    public void logAnr(java.lang.String str, int i, java.lang.String str2, int i2, int i3) {
        com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding appNotResponding = new com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding();
        appNotResponding.subject = str;
        appNotResponding.processClass = i;
        appNotResponding.process = str2;
        appNotResponding.uid = i2;
        appNotResponding.pid = i3;
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        criticalEventProto.setAnr(appNotResponding);
        log(criticalEventProto);
    }

    public void logJavaCrash(java.lang.String str, int i, java.lang.String str2, int i2, int i3) {
        com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash javaCrash = new com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash();
        javaCrash.exceptionClass = str;
        javaCrash.processClass = i;
        javaCrash.process = str2;
        javaCrash.uid = i2;
        javaCrash.pid = i3;
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        criticalEventProto.setJavaCrash(javaCrash);
        log(criticalEventProto);
    }

    public void logNativeCrash(int i, java.lang.String str, int i2, int i3) {
        com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash nativeCrash = new com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash();
        nativeCrash.processClass = i;
        nativeCrash.process = str;
        nativeCrash.uid = i2;
        nativeCrash.pid = i3;
        com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = new com.android.server.criticalevents.nano.CriticalEventProto();
        criticalEventProto.setNativeCrash(nativeCrash);
        log(criticalEventProto);
    }

    private void log(com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto) {
        criticalEventProto.timestampMs = getWallTimeMillis();
        appendAndSave(criticalEventProto);
    }

    @com.android.internal.annotations.VisibleForTesting
    void appendAndSave(com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto) {
        this.mEvents.append(criticalEventProto);
        saveLogToFile();
    }

    public java.lang.String logLinesForSystemServerTraceFile() {
        return logLinesForTraceFile(3, "AID_SYSTEM", 1000);
    }

    public java.lang.String logLinesForTraceFile(int i, java.lang.String str, int i2) {
        return "--- CriticalEventLog ---\n" + com.android.framework.protobuf.nano.MessageNanoPrinter.print(getOutputLogProto(i, str, i2)) + '\n';
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.criticalevents.nano.CriticalEventLogProto getOutputLogProto(int i, java.lang.String str, int i2) {
        com.android.server.criticalevents.nano.CriticalEventLogProto criticalEventLogProto = new com.android.server.criticalevents.nano.CriticalEventLogProto();
        criticalEventLogProto.timestampMs = getWallTimeMillis();
        criticalEventLogProto.windowMs = this.mWindowMs;
        criticalEventLogProto.capacity = this.mEvents.capacity();
        com.android.server.criticalevents.nano.CriticalEventProto[] recentEventsWithMinTimestamp = recentEventsWithMinTimestamp(criticalEventLogProto.timestampMs - this.mWindowMs);
        com.android.server.criticalevents.CriticalEventLog.LogSanitizer logSanitizer = new com.android.server.criticalevents.CriticalEventLog.LogSanitizer(i, str, i2);
        for (int i3 = 0; i3 < recentEventsWithMinTimestamp.length; i3++) {
            recentEventsWithMinTimestamp[i3] = logSanitizer.process(recentEventsWithMinTimestamp[i3]);
        }
        criticalEventLogProto.events = recentEventsWithMinTimestamp;
        return criticalEventLogProto;
    }

    private com.android.server.criticalevents.nano.CriticalEventProto[] recentEventsWithMinTimestamp(long j) {
        com.android.server.criticalevents.nano.CriticalEventProto[] array = this.mEvents.toArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i].timestampMs >= j) {
                return (com.android.server.criticalevents.nano.CriticalEventProto[]) java.util.Arrays.copyOfRange(array, i, array.length);
            }
        }
        return new com.android.server.criticalevents.nano.CriticalEventProto[0];
    }

    private void saveLogToFile() {
        if (this.mLoadAndSaveImmediately) {
            saveLogToFileNow();
        } else if (!this.mHandler.hasCallbacks(this.mSaveRunnable) && !this.mHandler.postDelayed(this.mSaveRunnable, saveDelayMs())) {
            android.util.Slog.w(TAG, "Error scheduling save");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long saveDelayMs() {
        return java.lang.Math.max(0L, (this.mLastSaveAttemptMs + this.mMinTimeBetweenSavesMs) - getWallTimeMillis());
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void saveLogToFileNow() {
        this.mLastSaveAttemptMs = getWallTimeMillis();
        java.io.File parentFile = this.mLogFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdir()) {
            android.util.Slog.e(TAG, "Error creating log directory: " + parentFile.getPath());
            return;
        }
        if (!this.mLogFile.exists()) {
            try {
                this.mLogFile.createNewFile();
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Error creating log file", e);
                return;
            }
        }
        com.android.server.criticalevents.nano.CriticalEventLogStorageProto criticalEventLogStorageProto = new com.android.server.criticalevents.nano.CriticalEventLogStorageProto();
        criticalEventLogStorageProto.events = this.mEvents.toArray();
        byte[] byteArray = com.android.server.criticalevents.nano.CriticalEventLogStorageProto.toByteArray(criticalEventLogStorageProto);
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(this.mLogFile, false);
            try {
                fileOutputStream.write(byteArray);
                fileOutputStream.close();
            } finally {
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Error saving log to disk.", e2);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static class ThreadSafeRingBuffer<T> {
        private final com.android.internal.util.RingBuffer<T> mBuffer;
        private final int mCapacity;

        ThreadSafeRingBuffer(java.lang.Class<T> cls, int i) {
            this.mCapacity = i;
            this.mBuffer = new com.android.internal.util.RingBuffer<>(cls, i);
        }

        synchronized void append(T t) {
            this.mBuffer.append(t);
        }

        synchronized T[] toArray() {
            return (T[]) this.mBuffer.toArray();
        }

        int capacity() {
            return this.mCapacity;
        }
    }

    static class LogLoader implements com.android.server.criticalevents.CriticalEventLog.ILogLoader {
        LogLoader() {
        }

        @Override // com.android.server.criticalevents.CriticalEventLog.ILogLoader
        public void load(java.io.File file, com.android.server.criticalevents.CriticalEventLog.ThreadSafeRingBuffer<com.android.server.criticalevents.nano.CriticalEventProto> threadSafeRingBuffer) {
            for (com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto : loadLogFromFile(file).events) {
                threadSafeRingBuffer.append(criticalEventProto);
            }
        }

        private static com.android.server.criticalevents.nano.CriticalEventLogStorageProto loadLogFromFile(java.io.File file) {
            if (!file.exists()) {
                android.util.Slog.i(com.android.server.criticalevents.CriticalEventLog.TAG, "No log found, returning empty log proto.");
                return new com.android.server.criticalevents.nano.CriticalEventLogStorageProto();
            }
            try {
                return com.android.server.criticalevents.nano.CriticalEventLogStorageProto.parseFrom(java.nio.file.Files.readAllBytes(file.toPath()));
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.criticalevents.CriticalEventLog.TAG, "Error reading log from disk.", e);
                return new com.android.server.criticalevents.nano.CriticalEventLogStorageProto();
            }
        }
    }

    private static class LogSanitizer {
        int mTraceProcessClassEnum;
        java.lang.String mTraceProcessName;
        int mTraceUid;

        LogSanitizer(int i, java.lang.String str, int i2) {
            this.mTraceProcessClassEnum = i;
            this.mTraceProcessName = str;
            this.mTraceUid = i2;
        }

        com.android.server.criticalevents.nano.CriticalEventProto process(com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto) {
            if (criticalEventProto.hasAnr()) {
                com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding anr = criticalEventProto.getAnr();
                if (shouldSanitize(anr.processClass, anr.process, anr.uid)) {
                    return sanitizeAnr(criticalEventProto);
                }
            } else if (criticalEventProto.hasJavaCrash()) {
                com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash javaCrash = criticalEventProto.getJavaCrash();
                if (shouldSanitize(javaCrash.processClass, javaCrash.process, javaCrash.uid)) {
                    return sanitizeJavaCrash(criticalEventProto);
                }
            } else if (criticalEventProto.hasNativeCrash()) {
                com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash nativeCrash = criticalEventProto.getNativeCrash();
                if (shouldSanitize(nativeCrash.processClass, nativeCrash.process, nativeCrash.uid)) {
                    return sanitizeNativeCrash(criticalEventProto);
                }
            }
            return criticalEventProto;
        }

        private boolean shouldSanitize(int i, java.lang.String str, int i2) {
            return i == 1 && this.mTraceProcessClassEnum == 1 && !(str != null && str.equals(this.mTraceProcessName) && this.mTraceUid == i2);
        }

        private static com.android.server.criticalevents.nano.CriticalEventProto sanitizeAnr(com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto) {
            com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding appNotResponding = new com.android.server.criticalevents.nano.CriticalEventProto.AppNotResponding();
            appNotResponding.processClass = criticalEventProto.getAnr().processClass;
            appNotResponding.uid = criticalEventProto.getAnr().uid;
            appNotResponding.pid = criticalEventProto.getAnr().pid;
            com.android.server.criticalevents.nano.CriticalEventProto sanitizeCriticalEventProto = sanitizeCriticalEventProto(criticalEventProto);
            sanitizeCriticalEventProto.setAnr(appNotResponding);
            return sanitizeCriticalEventProto;
        }

        private static com.android.server.criticalevents.nano.CriticalEventProto sanitizeJavaCrash(com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto) {
            com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash javaCrash = new com.android.server.criticalevents.nano.CriticalEventProto.JavaCrash();
            javaCrash.processClass = criticalEventProto.getJavaCrash().processClass;
            javaCrash.uid = criticalEventProto.getJavaCrash().uid;
            javaCrash.pid = criticalEventProto.getJavaCrash().pid;
            com.android.server.criticalevents.nano.CriticalEventProto sanitizeCriticalEventProto = sanitizeCriticalEventProto(criticalEventProto);
            sanitizeCriticalEventProto.setJavaCrash(javaCrash);
            return sanitizeCriticalEventProto;
        }

        private static com.android.server.criticalevents.nano.CriticalEventProto sanitizeNativeCrash(com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto) {
            com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash nativeCrash = new com.android.server.criticalevents.nano.CriticalEventProto.NativeCrash();
            nativeCrash.processClass = criticalEventProto.getNativeCrash().processClass;
            nativeCrash.uid = criticalEventProto.getNativeCrash().uid;
            nativeCrash.pid = criticalEventProto.getNativeCrash().pid;
            com.android.server.criticalevents.nano.CriticalEventProto sanitizeCriticalEventProto = sanitizeCriticalEventProto(criticalEventProto);
            sanitizeCriticalEventProto.setNativeCrash(nativeCrash);
            return sanitizeCriticalEventProto;
        }

        private static com.android.server.criticalevents.nano.CriticalEventProto sanitizeCriticalEventProto(com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto) {
            com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto2 = new com.android.server.criticalevents.nano.CriticalEventProto();
            criticalEventProto2.timestampMs = criticalEventProto.timestampMs;
            return criticalEventProto2;
        }
    }
}
