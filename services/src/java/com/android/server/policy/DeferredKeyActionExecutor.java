package com.android.server.policy;

/* loaded from: classes2.dex */
class DeferredKeyActionExecutor {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DeferredKeyAction";
    private final android.util.SparseArray<com.android.server.policy.DeferredKeyActionExecutor.TimedActionsBuffer> mBuffers = new android.util.SparseArray<>();

    DeferredKeyActionExecutor() {
    }

    public void queueKeyAction(int i, long j, java.lang.Runnable runnable) {
        getActionsBufferWithLazyCleanUp(i, j).addAction(runnable);
    }

    public void setActionsExecutable(int i, long j) {
        getActionsBufferWithLazyCleanUp(i, j).setExecutable();
    }

    public void cancelQueuedAction(int i) {
        com.android.server.policy.DeferredKeyActionExecutor.TimedActionsBuffer timedActionsBuffer = this.mBuffers.get(i);
        if (timedActionsBuffer != null) {
            timedActionsBuffer.clear();
        }
    }

    private com.android.server.policy.DeferredKeyActionExecutor.TimedActionsBuffer getActionsBufferWithLazyCleanUp(int i, long j) {
        com.android.server.policy.DeferredKeyActionExecutor.TimedActionsBuffer timedActionsBuffer = this.mBuffers.get(i);
        if (timedActionsBuffer == null || timedActionsBuffer.getDownTime() != j) {
            com.android.server.policy.DeferredKeyActionExecutor.TimedActionsBuffer timedActionsBuffer2 = new com.android.server.policy.DeferredKeyActionExecutor.TimedActionsBuffer(i, j);
            this.mBuffers.put(i, timedActionsBuffer2);
            return timedActionsBuffer2;
        }
        return timedActionsBuffer;
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "Deferred key action executor:");
        if (this.mBuffers.size() == 0) {
            printWriter.println(str + "  empty");
            return;
        }
        for (int i = 0; i < this.mBuffers.size(); i++) {
            this.mBuffers.valueAt(i).dump(str, printWriter);
        }
    }

    private static class TimedActionsBuffer {
        private final java.util.List<java.lang.Runnable> mActions = new java.util.ArrayList();
        private final long mDownTime;
        private boolean mExecutable;
        private final int mKeyCode;

        TimedActionsBuffer(int i, long j) {
            this.mKeyCode = i;
            this.mDownTime = j;
        }

        long getDownTime() {
            return this.mDownTime;
        }

        void addAction(java.lang.Runnable runnable) {
            if (this.mExecutable) {
                runnable.run();
            } else {
                this.mActions.add(runnable);
            }
        }

        void setExecutable() {
            this.mExecutable = true;
            java.util.Iterator<java.lang.Runnable> it = this.mActions.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
            this.mActions.clear();
        }

        void clear() {
            this.mActions.clear();
        }

        void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            if (this.mExecutable) {
                printWriter.println(str + "  " + android.view.KeyEvent.keyCodeToString(this.mKeyCode) + ": executable");
                return;
            }
            printWriter.println(str + "  " + android.view.KeyEvent.keyCodeToString(this.mKeyCode) + ": " + this.mActions.size() + " actions queued");
        }
    }
}
