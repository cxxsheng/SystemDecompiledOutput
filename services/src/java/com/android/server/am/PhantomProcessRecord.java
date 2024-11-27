package com.android.server.am;

/* loaded from: classes.dex */
public final class PhantomProcessRecord {
    static final java.lang.String TAG = "ActivityManager";
    long mCurrentCputime;
    long mLastCputime;
    final java.lang.Object mLock;
    final java.util.function.Consumer<com.android.server.am.PhantomProcessRecord> mOnKillListener;
    final int mPid;
    final java.io.FileDescriptor mPidFd;
    final int mPpid;
    final java.lang.String mProcessName;
    final com.android.server.am.ActivityManagerService mService;
    java.lang.String mStringName;
    final int mUid;
    int mUpdateSeq;
    boolean mZombie;
    static final long[] LONG_OUT = new long[1];
    static final int[] LONG_FORMAT = {8202};
    private java.lang.Runnable mProcKillTimer = new java.lang.Runnable() { // from class: com.android.server.am.PhantomProcessRecord.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.am.PhantomProcessRecord.this.mLock) {
                android.util.Slog.w(com.android.server.am.PhantomProcessRecord.TAG, "Process " + toString() + " is still alive after " + com.android.server.am.PhantomProcessRecord.this.mService.mConstants.mProcessKillTimeoutMs + "ms");
                com.android.server.am.PhantomProcessRecord.this.mZombie = true;
                com.android.server.am.PhantomProcessRecord.this.onProcDied(false);
            }
        }
    };
    boolean mKilled = false;
    int mAdj = -1000;
    final long mKnownSince = android.os.SystemClock.elapsedRealtime();
    final android.os.Handler mKillHandler = com.android.server.am.ProcessList.sKillHandler;

    PhantomProcessRecord(java.lang.String str, int i, int i2, int i3, com.android.server.am.ActivityManagerService activityManagerService, java.util.function.Consumer<com.android.server.am.PhantomProcessRecord> consumer) throws java.lang.IllegalStateException {
        this.mProcessName = str;
        this.mUid = i;
        this.mPid = i2;
        this.mPpid = i3;
        this.mService = activityManagerService;
        this.mLock = activityManagerService.mPhantomProcessList.mLock;
        this.mOnKillListener = consumer;
        if (android.os.Process.supportsPidFd()) {
            android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
            try {
                try {
                    this.mPidFd = android.os.Process.openPidFd(i2, 0);
                    if (this.mPidFd == null) {
                        throw new java.lang.IllegalStateException();
                    }
                    return;
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Unable to open process " + i2 + ", it might be gone");
                    java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException();
                    illegalStateException.initCause(e);
                    throw illegalStateException;
                }
            } finally {
                android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
            }
        }
        this.mPidFd = null;
    }

    public long getRss(int i) {
        long[] rss = android.os.Process.getRss(i);
        if (rss == null || rss.length <= 0) {
            return 0L;
        }
        return rss[0];
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void killLocked(java.lang.String str, boolean z) {
        if (!this.mKilled) {
            android.os.Trace.traceBegin(64L, "kill");
            if (z || this.mUid == this.mService.mCurOomAdjUid) {
                this.mService.reportUidInfoMessageLocked(TAG, "Killing " + toString() + ": " + str, this.mUid);
            }
            if (this.mPid > 0) {
                android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_KILL, java.lang.Integer.valueOf(android.os.UserHandle.getUserId(this.mUid)), java.lang.Integer.valueOf(this.mPid), this.mProcessName, java.lang.Integer.valueOf(this.mAdj), str, java.lang.Long.valueOf(getRss(this.mPid)));
                if (!android.os.Process.supportsPidFd()) {
                    onProcDied(false);
                } else {
                    this.mKillHandler.postDelayed(this.mProcKillTimer, this, this.mService.mConstants.mProcessKillTimeoutMs);
                }
                android.os.Process.killProcessQuiet(this.mPid);
                com.android.server.am.ProcessList.killProcessGroup(this.mUid, this.mPid);
            }
            this.mKilled = true;
            android.os.Trace.traceEnd(64L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void updateAdjLocked() {
        if (android.os.Process.readProcFile("/proc/" + this.mPid + "/oom_score_adj", LONG_FORMAT, null, LONG_OUT, null)) {
            this.mAdj = (int) LONG_OUT[0];
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onProcDied(boolean z) {
        if (z) {
            android.util.Slog.i(TAG, "Process " + toString() + " died");
        }
        this.mKillHandler.removeCallbacks(this.mProcKillTimer, this);
        if (this.mOnKillListener != null) {
            this.mOnKillListener.accept(this);
        }
    }

    public java.lang.String toString() {
        if (this.mStringName != null) {
            return this.mStringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("PhantomProcessRecord {");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        sb.append(this.mPid);
        sb.append(':');
        sb.append(this.mPpid);
        sb.append(':');
        sb.append(this.mProcessName);
        sb.append('/');
        if (this.mUid < 10000) {
            sb.append(this.mUid);
        } else {
            sb.append('u');
            sb.append(android.os.UserHandle.getUserId(this.mUid));
            int appId = android.os.UserHandle.getAppId(this.mUid);
            if (appId >= 10000) {
                sb.append('a');
                sb.append(appId + com.android.server.am.ProcessList.INVALID_ADJ);
            } else {
                sb.append('s');
                sb.append(appId);
            }
            if (appId >= 99000 && appId <= 99999) {
                sb.append('i');
                sb.append(appId - 99000);
            }
        }
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.mStringName = sb2;
        return sb2;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        printWriter.print(str);
        printWriter.print("user #");
        printWriter.print(android.os.UserHandle.getUserId(this.mUid));
        printWriter.print(" uid=");
        printWriter.print(this.mUid);
        printWriter.print(" pid=");
        printWriter.print(this.mPid);
        printWriter.print(" ppid=");
        printWriter.print(this.mPpid);
        printWriter.print(" knownSince=");
        android.util.TimeUtils.formatDuration(this.mKnownSince, elapsedRealtime, printWriter);
        printWriter.print(" killed=");
        printWriter.println(this.mKilled);
        printWriter.print(str);
        printWriter.print("lastCpuTime=");
        printWriter.print(this.mLastCputime);
        if (this.mLastCputime > 0) {
            printWriter.print(" timeUsed=");
            android.util.TimeUtils.formatDuration(this.mCurrentCputime - this.mLastCputime, printWriter);
        }
        printWriter.print(" oom adj=");
        printWriter.print(this.mAdj);
        printWriter.print(" seq=");
        printWriter.println(this.mUpdateSeq);
    }

    boolean equals(java.lang.String str, int i, int i2) {
        return this.mUid == i && this.mPid == i2 && android.text.TextUtils.equals(this.mProcessName, str);
    }
}
