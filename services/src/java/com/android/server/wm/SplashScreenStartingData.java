package com.android.server.wm;

/* loaded from: classes3.dex */
class SplashScreenStartingData extends com.android.server.wm.StartingData {
    private final int mTheme;

    SplashScreenStartingData(com.android.server.wm.WindowManagerService windowManagerService, int i, int i2) {
        super(windowManagerService, i2);
        this.mTheme = i;
    }

    @Override // com.android.server.wm.StartingData
    com.android.server.wm.StartingSurfaceController.StartingSurface createStartingSurface(com.android.server.wm.ActivityRecord activityRecord) {
        return this.mService.mStartingSurfaceController.createSplashScreenStartingSurface(activityRecord, this.mTheme);
    }

    @Override // com.android.server.wm.StartingData
    boolean needRevealAnimation() {
        return true;
    }
}
