package com.android.server.wm;

/* loaded from: classes3.dex */
final class TaskSystemBarsListenerController {
    private final java.util.HashSet<com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener> mListeners = new java.util.HashSet<>();
    private final java.util.concurrent.Executor mBackgroundExecutor = com.android.internal.os.BackgroundThread.getExecutor();

    TaskSystemBarsListenerController() {
    }

    void registerListener(com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
        this.mListeners.add(taskSystemBarsListener);
    }

    void unregisterListener(com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener) {
        this.mListeners.remove(taskSystemBarsListener);
    }

    void dispatchTransientSystemBarVisibilityChanged(final int i, final boolean z, final boolean z2) {
        final java.util.HashSet hashSet = new java.util.HashSet(this.mListeners);
        this.mBackgroundExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.wm.TaskSystemBarsListenerController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TaskSystemBarsListenerController.lambda$dispatchTransientSystemBarVisibilityChanged$0(hashSet, i, z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dispatchTransientSystemBarVisibilityChanged$0(java.util.HashSet hashSet, int i, boolean z, boolean z2) {
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener) it.next()).onTransientSystemBarsVisibilityChanged(i, z, z2);
        }
    }
}
