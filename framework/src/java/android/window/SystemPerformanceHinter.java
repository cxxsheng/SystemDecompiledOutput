package android.window;

/* loaded from: classes4.dex */
public class SystemPerformanceHinter {
    public static final int HINT_ADPF = 4;
    public static final int HINT_ALL = 7;
    private static final int HINT_GLOBAL = 5;
    private static final int HINT_NO_OP = 0;
    private static final int HINT_PER_DISPLAY = 2;
    public static final int HINT_SF = 3;
    public static final int HINT_SF_EARLY_WAKEUP = 1;
    public static final int HINT_SF_FRAME_RATE = 2;
    private static final java.lang.String TAG = "SystemPerformanceHinter";
    private final java.util.ArrayList<android.window.SystemPerformanceHinter.HighPerfSession> mActiveSessions;
    private android.os.PerformanceHintManager.Session mAdpfSession;
    private android.window.SystemPerformanceHinter.DisplayRootProvider mDisplayRootProvider;
    private final android.os.PerformanceHintManager mPerfHintManager;
    public long mTraceTag;
    private final android.view.SurfaceControl.Transaction mTransaction;

    public interface DisplayRootProvider {
        android.view.SurfaceControl getRootForDisplay(int i);
    }

    private @interface HintFlags {
    }

    public class HighPerfSession implements java.lang.AutoCloseable {
        private final int displayId;
        private final int hintFlags;
        private java.lang.String mTraceName;
        private final java.lang.String reason;

        protected HighPerfSession(int i, int i2, java.lang.String str) {
            this.hintFlags = i;
            this.reason = str;
            this.displayId = i2;
        }

        public void start() {
            if (!android.window.SystemPerformanceHinter.this.mActiveSessions.contains(this)) {
                android.window.SystemPerformanceHinter.this.startSession(this);
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            android.window.SystemPerformanceHinter.this.endSession(this);
        }

        public void finalize() {
            close();
        }

        boolean asyncTraceBegin() {
            if (!android.os.Trace.isTagEnabled(android.window.SystemPerformanceHinter.this.mTraceTag)) {
                this.mTraceName = null;
                return false;
            }
            if (this.mTraceName == null) {
                this.mTraceName = "PerfSession-d" + this.displayId + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + this.reason;
            }
            android.os.Trace.asyncTraceForTrackBegin(android.window.SystemPerformanceHinter.this.mTraceTag, android.window.SystemPerformanceHinter.TAG, this.mTraceName, java.lang.System.identityHashCode(this));
            return true;
        }

        boolean asyncTraceEnd() {
            if (this.mTraceName == null) {
                return false;
            }
            android.os.Trace.asyncTraceForTrackEnd(android.window.SystemPerformanceHinter.this.mTraceTag, android.window.SystemPerformanceHinter.TAG, java.lang.System.identityHashCode(this));
            return true;
        }
    }

    private class NoOpHighPerfSession extends android.window.SystemPerformanceHinter.HighPerfSession {
        public NoOpHighPerfSession() {
            super(0, -1, "");
        }

        @Override // android.window.SystemPerformanceHinter.HighPerfSession
        public void start() {
        }

        @Override // android.window.SystemPerformanceHinter.HighPerfSession, java.lang.AutoCloseable
        public void close() {
        }
    }

    public SystemPerformanceHinter(android.content.Context context, android.window.SystemPerformanceHinter.DisplayRootProvider displayRootProvider) {
        this(context, displayRootProvider, null);
    }

    public SystemPerformanceHinter(android.content.Context context, android.window.SystemPerformanceHinter.DisplayRootProvider displayRootProvider, java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier) {
        android.view.SurfaceControl.Transaction transaction;
        this.mTraceTag = 4096L;
        this.mActiveSessions = new java.util.ArrayList<>();
        this.mDisplayRootProvider = displayRootProvider;
        this.mPerfHintManager = (android.os.PerformanceHintManager) context.getSystemService(android.os.PerformanceHintManager.class);
        if (supplier != null) {
            transaction = supplier.get();
        } else {
            transaction = new android.view.SurfaceControl.Transaction();
        }
        this.mTransaction = transaction;
    }

    public void setAdpfSession(android.os.PerformanceHintManager.Session session) {
        this.mAdpfSession = session;
    }

    public android.window.SystemPerformanceHinter.HighPerfSession createSession(int i, int i2, java.lang.String str) {
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Not allow empty hint flags");
        }
        if (this.mDisplayRootProvider == null && (i & 2) != 0) {
            throw new java.lang.IllegalArgumentException("Using SF frame rate hints requires a valid display root provider");
        }
        if (this.mAdpfSession == null && (i & 4) != 0) {
            throw new java.lang.IllegalArgumentException("Using ADPF hints requires an ADPF session");
        }
        if ((i & 2) != 0 && this.mDisplayRootProvider.getRootForDisplay(i2) == null) {
            android.util.Log.v(TAG, "No display root for displayId=" + i2);
            android.os.Trace.instant(32L, "PerfHint-NoDisplayRoot: " + i2);
            return new android.window.SystemPerformanceHinter.NoOpHighPerfSession();
        }
        return new android.window.SystemPerformanceHinter.HighPerfSession(i, i2, str);
    }

    public android.window.SystemPerformanceHinter.HighPerfSession startSession(int i, int i2, java.lang.String str) {
        android.window.SystemPerformanceHinter.HighPerfSession createSession = createSession(i, i2, str);
        if (createSession.hintFlags != 0) {
            startSession(createSession);
        }
        return createSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSession(android.window.SystemPerformanceHinter.HighPerfSession highPerfSession) {
        boolean z;
        boolean asyncTraceBegin = highPerfSession.asyncTraceBegin();
        int calculateActiveHintFlags = calculateActiveHintFlags(5);
        int calculateActiveHintFlagsForDisplay = calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId);
        this.mActiveSessions.add(highPerfSession);
        int calculateActiveHintFlags2 = calculateActiveHintFlags(5);
        boolean z2 = true;
        if (!nowEnabled(calculateActiveHintFlagsForDisplay, calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId), 2)) {
            z = false;
        } else {
            android.view.SurfaceControl rootForDisplay = this.mDisplayRootProvider.getRootForDisplay(highPerfSession.displayId);
            this.mTransaction.setFrameRateSelectionStrategy(rootForDisplay, 1);
            this.mTransaction.setFrameRateCategory(rootForDisplay, 5, false);
            if (asyncTraceBegin) {
                asyncTraceBegin(2, highPerfSession.displayId);
            }
            z = true;
        }
        if (!nowEnabled(calculateActiveHintFlags, calculateActiveHintFlags2, 1)) {
            z2 = z;
        } else {
            this.mTransaction.setEarlyWakeupStart();
            if (asyncTraceBegin) {
                asyncTraceBegin(1, -1);
            }
        }
        if (nowEnabled(calculateActiveHintFlags, calculateActiveHintFlags2, 4)) {
            this.mAdpfSession.sendHint(0);
            if (asyncTraceBegin) {
                asyncTraceBegin(4, -1);
            }
        }
        if (z2) {
            this.mTransaction.applyAsyncUnsafe();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endSession(android.window.SystemPerformanceHinter.HighPerfSession highPerfSession) {
        boolean asyncTraceEnd = highPerfSession.asyncTraceEnd();
        int calculateActiveHintFlags = calculateActiveHintFlags(5);
        int calculateActiveHintFlagsForDisplay = calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId);
        this.mActiveSessions.remove(highPerfSession);
        int calculateActiveHintFlags2 = calculateActiveHintFlags(5);
        boolean z = true;
        boolean z2 = false;
        if (nowDisabled(calculateActiveHintFlagsForDisplay, calculateActiveHintFlagsForDisplay(2, highPerfSession.displayId), 2)) {
            android.view.SurfaceControl rootForDisplay = this.mDisplayRootProvider.getRootForDisplay(highPerfSession.displayId);
            this.mTransaction.setFrameRateSelectionStrategy(rootForDisplay, 0);
            this.mTransaction.setFrameRateCategory(rootForDisplay, 0, false);
            if (asyncTraceEnd) {
                asyncTraceEnd(2);
            }
            z2 = true;
        }
        if (!nowDisabled(calculateActiveHintFlags, calculateActiveHintFlags2, 1)) {
            z = z2;
        } else {
            this.mTransaction.setEarlyWakeupEnd();
            if (asyncTraceEnd) {
                asyncTraceEnd(1);
            }
        }
        if (nowDisabled(calculateActiveHintFlags, calculateActiveHintFlags2, 4)) {
            this.mAdpfSession.sendHint(2);
            if (asyncTraceEnd) {
                asyncTraceEnd(4);
            }
        }
        if (z) {
            this.mTransaction.applyAsyncUnsafe();
        }
    }

    private boolean nowEnabled(int i, int i2, int i3) {
        return (i & i3) == 0 && (i2 & i3) != 0;
    }

    private boolean nowDisabled(int i, int i2, int i3) {
        return (i & i3) != 0 && (i2 & i3) == 0;
    }

    private int calculateActiveHintFlags(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.mActiveSessions.size(); i3++) {
            i2 |= this.mActiveSessions.get(i3).hintFlags & i;
        }
        return i2;
    }

    private int calculateActiveHintFlagsForDisplay(int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < this.mActiveSessions.size(); i4++) {
            if (this.mActiveSessions.get(i4).displayId == i2) {
                i3 |= this.mActiveSessions.get(i4).hintFlags & i;
            }
        }
        return i3;
    }

    private void asyncTraceBegin(int i, int i2) {
        java.lang.String str;
        switch (i) {
            case 1:
                str = "PerfHint-early_wakeup";
                break;
            case 2:
                str = "PerfHint-framerate";
                break;
            case 3:
            default:
                str = "PerfHint-" + i;
                break;
            case 4:
                str = "PerfHint-adpf";
                break;
        }
        if (i2 != -1) {
            str = str + "-d" + i2;
        }
        android.os.Trace.asyncTraceForTrackBegin(this.mTraceTag, TAG, str, i ^ java.lang.System.identityHashCode(this));
    }

    private void asyncTraceEnd(int i) {
        android.os.Trace.asyncTraceForTrackEnd(this.mTraceTag, TAG, i ^ java.lang.System.identityHashCode(this));
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        java.lang.String str2 = str + "  ";
        printWriter.println(str + TAG + ":");
        printWriter.println(str2 + "Active sessions (" + this.mActiveSessions.size() + "):");
        for (int i = 0; i < this.mActiveSessions.size(); i++) {
            android.window.SystemPerformanceHinter.HighPerfSession highPerfSession = this.mActiveSessions.get(i);
            printWriter.println(str2 + "  reason=" + highPerfSession.reason + " flags=" + highPerfSession.hintFlags + " display=" + highPerfSession.displayId);
        }
    }
}
