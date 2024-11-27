package com.android.internal.graphics;

/* loaded from: classes4.dex */
public final class SfVsyncFrameCallbackProvider implements android.animation.AnimationHandler.AnimationFrameCallbackProvider {
    private final android.view.Choreographer mChoreographer;

    public SfVsyncFrameCallbackProvider() {
        this.mChoreographer = android.view.Choreographer.getSfInstance();
    }

    public SfVsyncFrameCallbackProvider(android.view.Choreographer choreographer) {
        this.mChoreographer = choreographer;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public void postFrameCallback(android.view.Choreographer.FrameCallback frameCallback) {
        this.mChoreographer.postFrameCallback(frameCallback);
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public void postCommitCallback(java.lang.Runnable runnable) {
        this.mChoreographer.postCallback(4, runnable, null);
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public long getFrameTime() {
        return this.mChoreographer.getFrameTime();
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public long getFrameDelay() {
        return android.view.Choreographer.getFrameDelay();
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallbackProvider
    public void setFrameDelay(long j) {
        android.view.Choreographer.setFrameDelay(j);
    }
}
