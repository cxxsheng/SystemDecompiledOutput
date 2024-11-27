package android.util;

/* loaded from: classes3.dex */
public final class LocalLog {
    private final java.util.Deque<java.lang.String> mLog;
    private final int mMaxLines;
    private final boolean mUseLocalTimestamps;

    public LocalLog(int i) {
        this(i, true);
    }

    public LocalLog(int i, boolean z) {
        this.mMaxLines = java.lang.Math.max(0, i);
        this.mLog = new java.util.ArrayDeque(this.mMaxLines);
        this.mUseLocalTimestamps = z;
    }

    public void log(java.lang.String str) {
        java.lang.String str2;
        if (this.mMaxLines <= 0) {
            return;
        }
        if (this.mUseLocalTimestamps) {
            str2 = java.time.LocalDateTime.now() + " - " + str;
        } else {
            str2 = java.time.Duration.ofMillis(android.os.SystemClock.elapsedRealtime()) + " / " + java.time.Instant.now() + " - " + str;
        }
        append(str2);
    }

    private synchronized void append(java.lang.String str) {
        while (this.mLog.size() >= this.mMaxLines) {
            this.mLog.remove();
        }
        this.mLog.add(str);
    }

    public synchronized void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        dump(printWriter);
    }

    public synchronized void dump(java.io.PrintWriter printWriter) {
        dump("", printWriter);
    }

    public synchronized void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        java.util.Iterator<java.lang.String> it = this.mLog.iterator();
        while (it.hasNext()) {
            printWriter.printf("%s%s\n", str, it.next());
        }
    }

    public synchronized void reverseDump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        reverseDump(printWriter);
    }

    public synchronized void reverseDump(java.io.PrintWriter printWriter) {
        java.util.Iterator<java.lang.String> descendingIterator = this.mLog.descendingIterator();
        while (descendingIterator.hasNext()) {
            printWriter.println(descendingIterator.next());
        }
    }

    public static class ReadOnlyLocalLog {
        private final android.util.LocalLog mLog;

        ReadOnlyLocalLog(android.util.LocalLog localLog) {
            this.mLog = localLog;
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            this.mLog.dump(printWriter);
        }

        public void dump(java.io.PrintWriter printWriter) {
            this.mLog.dump(printWriter);
        }

        public void reverseDump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            this.mLog.reverseDump(printWriter);
        }

        public void reverseDump(java.io.PrintWriter printWriter) {
            this.mLog.reverseDump(printWriter);
        }
    }

    public android.util.LocalLog.ReadOnlyLocalLog readOnlyLocalLog() {
        return new android.util.LocalLog.ReadOnlyLocalLog(this);
    }
}
