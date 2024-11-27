package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowManagerThreadPriorityBooster extends com.android.server.ThreadPriorityBooster {
    private final int mAnimationThreadId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mAppTransitionRunning;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBoundsAnimationRunning;
    private final java.lang.Object mLock;
    private final int mSurfaceAnimationThreadId;

    WindowManagerThreadPriorityBooster() {
        super(-4, 5);
        this.mLock = new java.lang.Object();
        this.mAnimationThreadId = com.android.server.AnimationThread.get().getThreadId();
        this.mSurfaceAnimationThreadId = com.android.server.wm.SurfaceAnimationThread.get().getThreadId();
    }

    @Override // com.android.server.ThreadPriorityBooster
    public void boost() {
        int myTid = android.os.Process.myTid();
        if (myTid == this.mAnimationThreadId || myTid == this.mSurfaceAnimationThreadId) {
            return;
        }
        super.boost();
    }

    @Override // com.android.server.ThreadPriorityBooster
    public void reset() {
        int myTid = android.os.Process.myTid();
        if (myTid == this.mAnimationThreadId || myTid == this.mSurfaceAnimationThreadId) {
            return;
        }
        super.reset();
    }

    void setAppTransitionRunning(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mAppTransitionRunning != z) {
                    this.mAppTransitionRunning = z;
                    updatePriorityLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setBoundsAnimationRunning(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mBoundsAnimationRunning != z) {
                    this.mBoundsAnimationRunning = z;
                    updatePriorityLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updatePriorityLocked() {
        int i = (this.mAppTransitionRunning || this.mBoundsAnimationRunning) ? -10 : -4;
        setBoostToPriority(i);
        android.os.Process.setThreadPriority(this.mAnimationThreadId, i);
        android.os.Process.setThreadPriority(this.mSurfaceAnimationThreadId, i);
    }
}
