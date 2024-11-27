package com.android.internal.view;

/* loaded from: classes5.dex */
public class SurfaceCallbackHelper {
    int mFinishDrawingCollected = 0;
    int mFinishDrawingExpected = 0;
    private java.lang.Runnable mFinishDrawingRunnable = new java.lang.Runnable() { // from class: com.android.internal.view.SurfaceCallbackHelper.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.internal.view.SurfaceCallbackHelper.this) {
                com.android.internal.view.SurfaceCallbackHelper.this.mFinishDrawingCollected++;
                if (com.android.internal.view.SurfaceCallbackHelper.this.mFinishDrawingCollected < com.android.internal.view.SurfaceCallbackHelper.this.mFinishDrawingExpected) {
                    return;
                }
                com.android.internal.view.SurfaceCallbackHelper.this.mRunnable.run();
            }
        }
    };
    java.lang.Runnable mRunnable;

    public SurfaceCallbackHelper(java.lang.Runnable runnable) {
        this.mRunnable = runnable;
    }

    public void dispatchSurfaceRedrawNeededAsync(android.view.SurfaceHolder surfaceHolder, android.view.SurfaceHolder.Callback[] callbackArr) {
        int i;
        if (callbackArr == null || callbackArr.length == 0) {
            this.mRunnable.run();
            return;
        }
        synchronized (this) {
            this.mFinishDrawingExpected = callbackArr.length;
            this.mFinishDrawingCollected = 0;
        }
        for (android.view.SurfaceHolder.Callback callback : callbackArr) {
            if (callback instanceof android.view.SurfaceHolder.Callback2) {
                ((android.view.SurfaceHolder.Callback2) callback).surfaceRedrawNeededAsync(surfaceHolder, this.mFinishDrawingRunnable);
            } else {
                this.mFinishDrawingRunnable.run();
            }
        }
    }
}
