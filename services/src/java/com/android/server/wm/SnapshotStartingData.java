package com.android.server.wm;

/* loaded from: classes3.dex */
class SnapshotStartingData extends com.android.server.wm.StartingData {
    private final com.android.server.wm.WindowManagerService mService;
    private final android.window.TaskSnapshot mSnapshot;

    SnapshotStartingData(com.android.server.wm.WindowManagerService windowManagerService, android.window.TaskSnapshot taskSnapshot, int i) {
        super(windowManagerService, i);
        this.mService = windowManagerService;
        this.mSnapshot = taskSnapshot;
    }

    @Override // com.android.server.wm.StartingData
    com.android.server.wm.StartingSurfaceController.StartingSurface createStartingSurface(com.android.server.wm.ActivityRecord activityRecord) {
        return this.mService.mStartingSurfaceController.createTaskSnapshotSurface(activityRecord, this.mSnapshot);
    }

    @Override // com.android.server.wm.StartingData
    boolean needRevealAnimation() {
        return false;
    }

    @Override // com.android.server.wm.StartingData
    boolean hasImeSurface() {
        return this.mSnapshot.hasImeSurface();
    }
}
