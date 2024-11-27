package com.android.server;

/* loaded from: classes.dex */
public class ThreadPriorityBooster {
    private static final boolean ENABLE_LOCK_GUARD = false;
    private static final int PRIORITY_NOT_ADJUSTED = Integer.MAX_VALUE;
    private volatile int mBoostToPriority;
    private final int mLockGuardIndex;
    private final java.lang.ThreadLocal<com.android.server.ThreadPriorityBooster.PriorityState> mThreadState = new java.lang.ThreadLocal<com.android.server.ThreadPriorityBooster.PriorityState>() { // from class: com.android.server.ThreadPriorityBooster.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public com.android.server.ThreadPriorityBooster.PriorityState initialValue() {
            return new com.android.server.ThreadPriorityBooster.PriorityState();
        }
    };

    public ThreadPriorityBooster(int i, int i2) {
        this.mBoostToPriority = i;
        this.mLockGuardIndex = i2;
    }

    public void boost() {
        int threadPriority;
        com.android.server.ThreadPriorityBooster.PriorityState priorityState = this.mThreadState.get();
        if (priorityState.regionCounter == 0 && (threadPriority = android.os.Process.getThreadPriority(priorityState.tid)) > this.mBoostToPriority) {
            android.os.Process.setThreadPriority(priorityState.tid, this.mBoostToPriority);
            priorityState.prevPriority = threadPriority;
        }
        priorityState.regionCounter++;
    }

    public void reset() {
        com.android.server.ThreadPriorityBooster.PriorityState priorityState = this.mThreadState.get();
        priorityState.regionCounter--;
        if (priorityState.regionCounter == 0 && priorityState.prevPriority != Integer.MAX_VALUE) {
            android.os.Process.setThreadPriority(priorityState.tid, priorityState.prevPriority);
            priorityState.prevPriority = Integer.MAX_VALUE;
        }
    }

    protected void setBoostToPriority(int i) {
        this.mBoostToPriority = i;
        com.android.server.ThreadPriorityBooster.PriorityState priorityState = this.mThreadState.get();
        if (priorityState.regionCounter != 0 && android.os.Process.getThreadPriority(priorityState.tid) != i) {
            android.os.Process.setThreadPriority(priorityState.tid, i);
        }
    }

    private static class PriorityState {
        int prevPriority;
        int regionCounter;
        final int tid;

        private PriorityState() {
            this.tid = android.os.Process.myTid();
            this.prevPriority = Integer.MAX_VALUE;
        }
    }
}
