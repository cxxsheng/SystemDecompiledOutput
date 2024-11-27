package com.android.server.content;

/* loaded from: classes.dex */
public class SyncLogger {
    public static final int CALLING_UID_SELF = -1;
    private static final java.lang.String TAG = "SyncLogger";
    private static com.android.server.content.SyncLogger sInstance;

    SyncLogger() {
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032 A[Catch: all -> 0x0023, TryCatch #0 {all -> 0x0023, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x0011, B:10:0x0019, B:15:0x0032, B:16:0x003a, B:17:0x0025, B:20:0x0041), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003a A[Catch: all -> 0x0023, TryCatch #0 {all -> 0x0023, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x0011, B:10:0x0019, B:15:0x0032, B:16:0x003a, B:17:0x0025, B:20:0x0041), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static synchronized com.android.server.content.SyncLogger getInstance() {
        com.android.server.content.SyncLogger syncLogger;
        boolean z;
        synchronized (com.android.server.content.SyncLogger.class) {
            try {
                if (sInstance == null) {
                    java.lang.String str = android.os.SystemProperties.get("debug.synclog");
                    if (!android.os.Build.IS_DEBUGGABLE) {
                        if (!"1".equals(str)) {
                            if (android.util.Log.isLoggable(TAG, 2)) {
                            }
                            z = false;
                            if (z) {
                                sInstance = new com.android.server.content.SyncLogger.RotatingFileLogger();
                            } else {
                                sInstance = new com.android.server.content.SyncLogger();
                            }
                        }
                    }
                    if (!"0".equals(str)) {
                        z = true;
                        if (z) {
                        }
                    }
                    z = false;
                    if (z) {
                    }
                }
                syncLogger = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return syncLogger;
    }

    public void log(java.lang.Object... objArr) {
    }

    public void purgeOldLogs() {
    }

    public java.lang.String jobParametersToString(android.app.job.JobParameters jobParameters) {
        return "";
    }

    public void dumpAll(java.io.PrintWriter printWriter) {
    }

    public boolean enabled() {
        return false;
    }

    private static class RotatingFileLogger extends com.android.server.content.SyncLogger {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private long mCurrentLogFileDayTimestamp;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean mErrorShown;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.io.Writer mLogWriter;
        private static final java.text.SimpleDateFormat sTimestampFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        private static final java.text.SimpleDateFormat sFilenameDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        private static final boolean DO_LOGCAT = android.util.Log.isLoggable(com.android.server.content.SyncLogger.TAG, 3);
        private final java.lang.Object mLock = new java.lang.Object();
        private final long mKeepAgeMs = java.util.concurrent.TimeUnit.DAYS.toMillis(7);

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final java.util.Date mCachedDate = new java.util.Date();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final java.lang.StringBuilder mStringBuilder = new java.lang.StringBuilder();
        private final java.io.File mLogPath = new java.io.File(android.os.Environment.getDataSystemDirectory(), "syncmanager-log");
        private final com.android.server.content.SyncLogger.RotatingFileLogger.MyHandler mHandler = new com.android.server.content.SyncLogger.RotatingFileLogger.MyHandler(com.android.server.IoThread.get().getLooper());

        RotatingFileLogger() {
        }

        @Override // com.android.server.content.SyncLogger
        public boolean enabled() {
            return true;
        }

        private void handleException(java.lang.String str, java.lang.Exception exc) {
            if (!this.mErrorShown) {
                android.util.Slog.e(com.android.server.content.SyncLogger.TAG, str, exc);
                this.mErrorShown = true;
            }
        }

        @Override // com.android.server.content.SyncLogger
        public void log(java.lang.Object... objArr) {
            if (objArr == null) {
                return;
            }
            this.mHandler.log(java.lang.System.currentTimeMillis(), objArr);
        }

        void logInner(long j, java.lang.Object[] objArr) {
            synchronized (this.mLock) {
                try {
                    openLogLocked(j);
                    if (this.mLogWriter == null) {
                        return;
                    }
                    this.mStringBuilder.setLength(0);
                    this.mCachedDate.setTime(j);
                    this.mStringBuilder.append(sTimestampFormat.format(this.mCachedDate));
                    this.mStringBuilder.append(' ');
                    this.mStringBuilder.append(android.os.Process.myTid());
                    this.mStringBuilder.append(' ');
                    int length = this.mStringBuilder.length();
                    for (java.lang.Object obj : objArr) {
                        this.mStringBuilder.append(obj);
                    }
                    this.mStringBuilder.append('\n');
                    try {
                        this.mLogWriter.append((java.lang.CharSequence) this.mStringBuilder);
                        this.mLogWriter.flush();
                        if (DO_LOGCAT) {
                            android.util.Log.d(com.android.server.content.SyncLogger.TAG, this.mStringBuilder.substring(length));
                        }
                    } catch (java.io.IOException e) {
                        handleException("Failed to write log", e);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void openLogLocked(long j) {
            long j2 = j % 86400000;
            if (this.mLogWriter != null && j2 == this.mCurrentLogFileDayTimestamp) {
                return;
            }
            closeCurrentLogLocked();
            this.mCurrentLogFileDayTimestamp = j2;
            this.mCachedDate.setTime(j);
            java.io.File file = new java.io.File(this.mLogPath, "synclog-" + sFilenameDateFormat.format(this.mCachedDate) + ".log");
            file.getParentFile().mkdirs();
            try {
                this.mLogWriter = new java.io.FileWriter(file, true);
            } catch (java.io.IOException e) {
                handleException("Failed to open log file: " + file, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void closeCurrentLogLocked() {
            libcore.io.IoUtils.closeQuietly(this.mLogWriter);
            this.mLogWriter = null;
        }

        @Override // com.android.server.content.SyncLogger
        public void purgeOldLogs() {
            synchronized (this.mLock) {
                android.os.FileUtils.deleteOlderFiles(this.mLogPath, 1, this.mKeepAgeMs);
            }
        }

        @Override // com.android.server.content.SyncLogger
        public java.lang.String jobParametersToString(android.app.job.JobParameters jobParameters) {
            return com.android.server.content.SyncJobService.jobParametersToString(jobParameters);
        }

        @Override // com.android.server.content.SyncLogger
        public void dumpAll(java.io.PrintWriter printWriter) {
            synchronized (this.mLock) {
                try {
                    java.lang.String[] list = this.mLogPath.list();
                    if (list == null || list.length == 0) {
                        return;
                    }
                    java.util.Arrays.sort(list);
                    for (java.lang.String str : list) {
                        dumpFile(printWriter, new java.io.File(this.mLogPath, str));
                    }
                } finally {
                }
            }
        }

        private void dumpFile(java.io.PrintWriter printWriter, java.io.File file) {
            android.util.Slog.w(com.android.server.content.SyncLogger.TAG, "Dumping " + file);
            char[] cArr = new char[32768];
            try {
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file));
                while (true) {
                    try {
                        int read = bufferedReader.read(cArr);
                        if (read >= 0) {
                            if (read > 0) {
                                printWriter.write(cArr, 0, read);
                            }
                        } else {
                            bufferedReader.close();
                            return;
                        }
                    } finally {
                    }
                }
            } catch (java.io.IOException e) {
            }
        }

        private class MyHandler extends android.os.Handler {
            public static final int MSG_LOG_ID = 1;

            MyHandler(android.os.Looper looper) {
                super(looper);
            }

            public void log(long j, java.lang.Object[] objArr) {
                obtainMessage(1, com.android.internal.util.IntPair.first(j), com.android.internal.util.IntPair.second(j), objArr).sendToTarget();
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.content.SyncLogger.RotatingFileLogger.this.logInner(com.android.internal.util.IntPair.of(message.arg1, message.arg2), (java.lang.Object[]) message.obj);
                        break;
                }
            }
        }
    }

    static java.lang.String logSafe(android.accounts.Account account) {
        return account == null ? "[null]" : account.toSafeString();
    }

    static java.lang.String logSafe(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        return endPoint == null ? "[null]" : endPoint.toSafeString();
    }

    static java.lang.String logSafe(com.android.server.content.SyncOperation syncOperation) {
        return syncOperation == null ? "[null]" : syncOperation.toSafeString();
    }

    static java.lang.String logSafe(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext) {
        return activeSyncContext == null ? "[null]" : activeSyncContext.toSafeString();
    }
}
