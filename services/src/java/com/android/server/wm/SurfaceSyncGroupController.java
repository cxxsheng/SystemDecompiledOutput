package com.android.server.wm;

/* loaded from: classes3.dex */
class SurfaceSyncGroupController {
    private static final java.lang.String TAG = "SurfaceSyncGroupController";
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.SurfaceSyncGroupController.SurfaceSyncGroupData> mSurfaceSyncGroups = new android.util.ArrayMap<>();

    SurfaceSyncGroupController() {
    }

    boolean addToSyncGroup(android.os.IBinder iBinder, boolean z, @android.annotation.Nullable final android.window.ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) {
        android.window.SurfaceSyncGroup surfaceSyncGroup;
        synchronized (this.mLock) {
            try {
                com.android.server.wm.SurfaceSyncGroupController.SurfaceSyncGroupData surfaceSyncGroupData = this.mSurfaceSyncGroups.get(iBinder);
                if (surfaceSyncGroupData == null) {
                    surfaceSyncGroup = new android.window.SurfaceSyncGroup("SurfaceSyncGroupController-" + iBinder.hashCode());
                    if (iSurfaceSyncGroupCompletedListener != null) {
                        surfaceSyncGroup.addSyncCompleteCallback(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new java.lang.Runnable() { // from class: com.android.server.wm.SurfaceSyncGroupController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.wm.SurfaceSyncGroupController.lambda$addToSyncGroup$0(iSurfaceSyncGroupCompletedListener);
                            }
                        });
                    }
                    this.mSurfaceSyncGroups.put(iBinder, new com.android.server.wm.SurfaceSyncGroupController.SurfaceSyncGroupData(android.os.Binder.getCallingUid(), surfaceSyncGroup));
                } else {
                    surfaceSyncGroup = surfaceSyncGroupData.mSurfaceSyncGroup;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.window.ITransactionReadyCallback createTransactionReadyCallback = surfaceSyncGroup.createTransactionReadyCallback(z);
        if (createTransactionReadyCallback == null) {
            return false;
        }
        addToSurfaceSyncGroupResult.mParentSyncGroup = surfaceSyncGroup.mISurfaceSyncGroup;
        addToSurfaceSyncGroupResult.mTransactionReadyCallback = createTransactionReadyCallback;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToSyncGroup$0(android.window.ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener) {
        try {
            iSurfaceSyncGroupCompletedListener.onSurfaceSyncGroupComplete();
        } catch (android.os.RemoteException e) {
        }
    }

    void markSyncGroupReady(android.os.IBinder iBinder) {
        android.window.SurfaceSyncGroup surfaceSyncGroup;
        synchronized (this.mLock) {
            com.android.server.wm.SurfaceSyncGroupController.SurfaceSyncGroupData surfaceSyncGroupData = this.mSurfaceSyncGroups.get(iBinder);
            if (surfaceSyncGroupData == null) {
                throw new java.lang.IllegalArgumentException("SurfaceSyncGroup Token has not been set up or has already been marked as ready");
            }
            if (surfaceSyncGroupData.mOwningUid != android.os.Binder.getCallingUid()) {
                throw new java.lang.IllegalArgumentException("Only process that created the SurfaceSyncGroup can call markSyncGroupReady");
            }
            surfaceSyncGroup = surfaceSyncGroupData.mSurfaceSyncGroup;
            this.mSurfaceSyncGroups.remove(iBinder);
        }
        surfaceSyncGroup.markSyncReady();
    }

    private static class SurfaceSyncGroupData {
        final int mOwningUid;
        final android.window.SurfaceSyncGroup mSurfaceSyncGroup;

        private SurfaceSyncGroupData(int i, android.window.SurfaceSyncGroup surfaceSyncGroup) {
            this.mOwningUid = i;
            this.mSurfaceSyncGroup = surfaceSyncGroup;
        }
    }
}
