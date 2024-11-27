package com.android.server.wm;

/* loaded from: classes3.dex */
public class ScreenRecordingCallbackController {

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private com.android.server.wm.WindowContainer<com.android.server.wm.WindowContainer> mRecordedWC;
    private final com.android.server.wm.WindowManagerService mWms;

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.ScreenRecordingCallbackController.Callback> mCallbacks = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private final android.util.ArrayMap<java.lang.Integer, java.lang.Boolean> mLastInvokedStateByUid = new android.util.ArrayMap<>();
    private boolean mWatcherCallbackRegistered = false;

    private final class Callback implements android.os.IBinder.DeathRecipient {
        android.window.IScreenRecordingCallback mCallback;
        int mUid;

        Callback(android.window.IScreenRecordingCallback iScreenRecordingCallback, int i) {
            this.mCallback = iScreenRecordingCallback;
            this.mUid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.wm.ScreenRecordingCallbackController.this.unregister(this.mCallback);
        }
    }

    private final class MediaProjectionWatcherCallback extends android.media.projection.IMediaProjectionWatcherCallback.Stub {
        private MediaProjectionWatcherCallback() {
        }

        public void onStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
            com.android.server.wm.ScreenRecordingCallbackController.this.onScreenRecordingStart(mediaProjectionInfo);
        }

        public void onStop(android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
            com.android.server.wm.ScreenRecordingCallbackController.this.onScreenRecordingStop();
        }

        public void onRecordingSessionSet(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.view.ContentRecordingSession contentRecordingSession) {
        }
    }

    ScreenRecordingCallbackController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWms = windowManagerService;
    }

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private void setRecordedWindowContainer(final android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
        if (mediaProjectionInfo.getLaunchCookie() == null) {
            this.mRecordedWC = this.mWms.mRoot.getDefaultDisplay();
        } else {
            this.mRecordedWC = this.mWms.mRoot.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ScreenRecordingCallbackController$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$setRecordedWindowContainer$0;
                    lambda$setRecordedWindowContainer$0 = com.android.server.wm.ScreenRecordingCallbackController.lambda$setRecordedWindowContainer$0(mediaProjectionInfo, (com.android.server.wm.ActivityRecord) obj);
                    return lambda$setRecordedWindowContainer$0;
                }
            }).getTask();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setRecordedWindowContainer$0(android.media.projection.MediaProjectionInfo mediaProjectionInfo, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.mLaunchCookie == mediaProjectionInfo.getLaunchCookie().binder;
    }

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private void ensureMediaProjectionWatcherCallbackRegistered() {
        if (this.mWatcherCallbackRegistered) {
            return;
        }
        android.media.projection.IMediaProjectionManager asInterface = android.media.projection.IMediaProjectionManager.Stub.asInterface(android.os.ServiceManager.getService("media_projection"));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        android.media.projection.MediaProjectionInfo mediaProjectionInfo = null;
        try {
            try {
                mediaProjectionInfo = asInterface.addCallback(new com.android.server.wm.ScreenRecordingCallbackController.MediaProjectionWatcherCallback());
                this.mWatcherCallbackRegistered = true;
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_ERROR, 4666728330189027178L, 0, "Failed to register MediaProjectionWatcherCallback", null);
            }
            if (mediaProjectionInfo != null) {
                setRecordedWindowContainer(mediaProjectionInfo);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean register(android.window.IScreenRecordingCallback iScreenRecordingCallback) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWms.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ensureMediaProjectionWatcherCallbackRegistered();
                android.os.IBinder asBinder = iScreenRecordingCallback.asBinder();
                int callingUid = android.os.Binder.getCallingUid();
                if (this.mCallbacks.containsKey(asBinder)) {
                    boolean booleanValue = this.mLastInvokedStateByUid.get(java.lang.Integer.valueOf(callingUid)).booleanValue();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return booleanValue;
                }
                com.android.server.wm.ScreenRecordingCallbackController.Callback callback = new com.android.server.wm.ScreenRecordingCallbackController.Callback(iScreenRecordingCallback, callingUid);
                try {
                    asBinder.linkToDeath(callback, 0);
                    boolean uidHasRecordedActivity = uidHasRecordedActivity(callback.mUid);
                    this.mLastInvokedStateByUid.put(java.lang.Integer.valueOf(callback.mUid), java.lang.Boolean.valueOf(uidHasRecordedActivity));
                    this.mCallbacks.put(asBinder, callback);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return uidHasRecordedActivity;
                } catch (android.os.RemoteException e) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void unregister(android.window.IScreenRecordingCallback iScreenRecordingCallback) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWms.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.os.IBinder asBinder = iScreenRecordingCallback.asBinder();
                com.android.server.wm.ScreenRecordingCallbackController.Callback remove = this.mCallbacks.remove(asBinder);
                if (remove == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                boolean z = false;
                asBinder.unlinkToDeath(remove, 0);
                int i = 0;
                while (true) {
                    if (i >= this.mCallbacks.size()) {
                        break;
                    }
                    if (this.mCallbacks.valueAt(i).mUid != remove.mUid) {
                        i++;
                    } else {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    this.mLastInvokedStateByUid.remove(java.lang.Integer.valueOf(remove.mUid));
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenRecordingStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWms.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                setRecordedWindowContainer(mediaProjectionInfo);
                dispatchCallbacks(getRecordedUids(), true);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScreenRecordingStop() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWms.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                dispatchCallbacks(getRecordedUids(), false);
                this.mRecordedWC = null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    void onProcessActivityVisibilityChanged(int i, boolean z) {
        if (this.mRecordedWC == null || !this.mLastInvokedStateByUid.containsKey(java.lang.Integer.valueOf(i)) || z == this.mLastInvokedStateByUid.get(java.lang.Integer.valueOf(i)).booleanValue()) {
            return;
        }
        boolean uidHasRecordedActivity = uidHasRecordedActivity(i);
        if (!z || uidHasRecordedActivity) {
            if (!z && uidHasRecordedActivity) {
                return;
            }
            android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
            arraySet.add(java.lang.Integer.valueOf(i));
            dispatchCallbacks(arraySet, z);
        }
    }

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private boolean uidHasRecordedActivity(final int i) {
        if (this.mRecordedWC == null) {
            return false;
        }
        final boolean[] zArr = {false};
        this.mRecordedWC.forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.ScreenRecordingCallbackController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$uidHasRecordedActivity$1;
                lambda$uidHasRecordedActivity$1 = com.android.server.wm.ScreenRecordingCallbackController.lambda$uidHasRecordedActivity$1(i, zArr, (com.android.server.wm.ActivityRecord) obj);
                return lambda$uidHasRecordedActivity$1;
            }
        }, true);
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$uidHasRecordedActivity$1(int i, boolean[] zArr, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.getUid() != i || !activityRecord.isVisibleRequested()) {
            return false;
        }
        zArr[0] = true;
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private android.util.ArraySet<java.lang.Integer> getRecordedUids() {
        final android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
        if (this.mRecordedWC == null) {
            return arraySet;
        }
        this.mRecordedWC.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ScreenRecordingCallbackController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.ScreenRecordingCallbackController.this.lambda$getRecordedUids$2(arraySet, (com.android.server.wm.ActivityRecord) obj);
            }
        }, true);
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRecordedUids$2(android.util.ArraySet arraySet, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.isVisibleRequested() && this.mLastInvokedStateByUid.containsKey(java.lang.Integer.valueOf(activityRecord.getUid()))) {
            arraySet.add(java.lang.Integer.valueOf(activityRecord.getUid()));
        }
    }

    @com.android.internal.annotations.GuardedBy({"WindowManagerService.mGlobalLock"})
    private void dispatchCallbacks(android.util.ArraySet<java.lang.Integer> arraySet, final boolean z) {
        if (arraySet.isEmpty()) {
            return;
        }
        for (int i = 0; i < arraySet.size(); i++) {
            this.mLastInvokedStateByUid.put(arraySet.valueAt(i), java.lang.Boolean.valueOf(z));
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            if (arraySet.contains(java.lang.Integer.valueOf(this.mCallbacks.valueAt(i2).mUid))) {
                arrayList.add(this.mCallbacks.valueAt(i2).mCallback);
            }
        }
        this.mWms.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ScreenRecordingCallbackController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ScreenRecordingCallbackController.lambda$dispatchCallbacks$3(arrayList, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dispatchCallbacks$3(java.util.ArrayList arrayList, boolean z) {
        for (int i = 0; i < arrayList.size(); i++) {
            try {
                ((android.window.IScreenRecordingCallback) arrayList.get(i)).onScreenRecordingStateChanged(z);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.format("ScreenRecordingCallbackController:\n", new java.lang.Object[0]);
        printWriter.format("  Registered callbacks:\n", new java.lang.Object[0]);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            printWriter.format("    callback=%s uid=%s\n", this.mCallbacks.keyAt(i), java.lang.Integer.valueOf(this.mCallbacks.valueAt(i).mUid));
        }
        printWriter.format("  Last invoked states:\n", new java.lang.Object[0]);
        for (int i2 = 0; i2 < this.mLastInvokedStateByUid.size(); i2++) {
            printWriter.format("    uid=%s isVisibleInScreenRecording=%s\n", this.mLastInvokedStateByUid.keyAt(i2), this.mLastInvokedStateByUid.valueAt(i2));
        }
    }
}
