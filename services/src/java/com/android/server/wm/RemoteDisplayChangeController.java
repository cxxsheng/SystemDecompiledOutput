package com.android.server.wm;

/* loaded from: classes3.dex */
public class RemoteDisplayChangeController {
    private static final int REMOTE_DISPLAY_CHANGE_TIMEOUT_MS = 800;
    private static final java.lang.String REMOTE_DISPLAY_CHANGE_TRACE_TAG = "RemoteDisplayChange";
    private static final java.lang.String TAG = "RemoteDisplayChangeController";
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final com.android.server.wm.WindowManagerService mService;
    private final java.lang.Runnable mTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.RemoteDisplayChangeController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.wm.RemoteDisplayChangeController.this.onContinueTimedOut();
        }
    };
    private final java.util.List<com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback> mCallbacks = new java.util.ArrayList();

    public interface ContinueRemoteDisplayChangeCallback {
        void onContinueRemoteDisplayChange(@android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction);
    }

    RemoteDisplayChangeController(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        this.mService = displayContent.mWmService;
        this.mDisplayContent = displayContent;
    }

    public boolean isWaitingForRemoteDisplayChange() {
        return !this.mCallbacks.isEmpty();
    }

    public boolean performRemoteDisplayChange(int i, int i2, @android.annotation.Nullable android.window.DisplayAreaInfo displayAreaInfo, com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
        if (this.mService.mDisplayChangeController == null) {
            return false;
        }
        this.mCallbacks.add(continueRemoteDisplayChangeCallback);
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.beginAsyncSection(REMOTE_DISPLAY_CHANGE_TRACE_TAG, continueRemoteDisplayChangeCallback.hashCode());
        }
        if (displayAreaInfo != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, 1736084564226683342L, 85, null, java.lang.Long.valueOf(i), java.lang.Long.valueOf(displayAreaInfo.configuration.windowConfiguration.getMaxBounds().width()), java.lang.Long.valueOf(displayAreaInfo.configuration.windowConfiguration.getMaxBounds().height()), java.lang.Long.valueOf(i2));
        }
        android.view.IDisplayChangeWindowCallback createCallback = createCallback(continueRemoteDisplayChangeCallback);
        try {
            this.mService.mH.removeCallbacks(this.mTimeoutRunnable);
            this.mService.mH.postDelayed(this.mTimeoutRunnable, 800L);
            this.mService.mDisplayChangeController.onDisplayChange(this.mDisplayContent.mDisplayId, i, i2, displayAreaInfo, createCallback);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception while dispatching remote display-change", e);
            this.mCallbacks.remove(continueRemoteDisplayChangeCallback);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onContinueTimedOut() {
        android.util.Slog.e(TAG, "RemoteDisplayChange timed-out, UI might get messed-up after this.");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                try {
                    com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback = this.mCallbacks.get(i);
                    if (i == this.mCallbacks.size() - 1) {
                        this.mCallbacks.clear();
                    }
                    continueRemoteDisplayChangeCallback.onContinueRemoteDisplayChange(null);
                    if (android.os.Trace.isTagEnabled(32L)) {
                        android.os.Trace.endAsyncSection(REMOTE_DISPLAY_CHANGE_TRACE_TAG, continueRemoteDisplayChangeCallback.hashCode());
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            onCompleted();
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private void onCompleted() {
        if (this.mDisplayContent.mWaitingForConfig) {
            this.mDisplayContent.sendNewConfiguration();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void continueDisplayChange(@android.annotation.NonNull com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback, @android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int indexOf = this.mCallbacks.indexOf(continueRemoteDisplayChangeCallback);
                if (indexOf < 0) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                for (int i = 0; i < indexOf; i++) {
                    com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback2 = this.mCallbacks.get(i);
                    continueRemoteDisplayChangeCallback2.onContinueRemoteDisplayChange(null);
                    if (android.os.Trace.isTagEnabled(32L)) {
                        android.os.Trace.endAsyncSection(REMOTE_DISPLAY_CHANGE_TRACE_TAG, continueRemoteDisplayChangeCallback2.hashCode());
                    }
                }
                this.mCallbacks.subList(0, indexOf + 1).clear();
                boolean isEmpty = this.mCallbacks.isEmpty();
                if (isEmpty) {
                    this.mService.mH.removeCallbacks(this.mTimeoutRunnable);
                }
                continueRemoteDisplayChangeCallback.onContinueRemoteDisplayChange(windowContainerTransaction);
                if (isEmpty) {
                    onCompleted();
                }
                if (android.os.Trace.isTagEnabled(32L)) {
                    android.os.Trace.endAsyncSection(REMOTE_DISPLAY_CHANGE_TRACE_TAG, continueRemoteDisplayChangeCallback.hashCode());
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.wm.RemoteDisplayChangeController$1, reason: invalid class name */
    class AnonymousClass1 extends android.view.IDisplayChangeWindowCallback.Stub {
        final /* synthetic */ com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback val$callback;

        AnonymousClass1(com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
            this.val$callback = continueRemoteDisplayChangeCallback;
        }

        public void continueDisplayChange(final android.window.WindowContainerTransaction windowContainerTransaction) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.RemoteDisplayChangeController.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!com.android.server.wm.RemoteDisplayChangeController.this.mCallbacks.contains(this.val$callback)) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.WindowManagerService.H h = com.android.server.wm.RemoteDisplayChangeController.this.mService.mH;
                    final com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback = this.val$callback;
                    h.post(new java.lang.Runnable() { // from class: com.android.server.wm.RemoteDisplayChangeController$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.wm.RemoteDisplayChangeController.AnonymousClass1.this.lambda$continueDisplayChange$0(continueRemoteDisplayChangeCallback, windowContainerTransaction);
                        }
                    });
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$continueDisplayChange$0(com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback, android.window.WindowContainerTransaction windowContainerTransaction) {
            com.android.server.wm.RemoteDisplayChangeController.this.continueDisplayChange(continueRemoteDisplayChangeCallback, windowContainerTransaction);
        }
    }

    private android.view.IDisplayChangeWindowCallback createCallback(@android.annotation.NonNull com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
        return new com.android.server.wm.RemoteDisplayChangeController.AnonymousClass1(continueRemoteDisplayChangeCallback);
    }
}
