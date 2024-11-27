package com.android.server.wm;

/* loaded from: classes3.dex */
public class ImmediateDisplayUpdater implements com.android.server.wm.DisplayUpdater {
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final android.view.DisplayInfo mDisplayInfo = new android.view.DisplayInfo();

    public ImmediateDisplayUpdater(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
        this.mDisplayInfo.copyFrom(this.mDisplayContent.getDisplayInfo());
    }

    @Override // com.android.server.wm.DisplayUpdater
    public void updateDisplayInfo(java.lang.Runnable runnable) {
        this.mDisplayContent.mWmService.mDisplayManagerInternal.getNonOverrideDisplayInfo(this.mDisplayContent.mDisplayId, this.mDisplayInfo);
        this.mDisplayContent.onDisplayInfoUpdated(this.mDisplayInfo);
        runnable.run();
    }

    @Override // com.android.server.wm.DisplayUpdater
    public void onDisplayContentDisplayPropertiesPreChanged(int i, int i2, int i3, int i4, int i5) {
        this.mDisplayContent.mDisplaySwitchTransitionLauncher.requestDisplaySwitchTransitionIfNeeded(i, i2, i3, i4, i5);
    }

    @Override // com.android.server.wm.DisplayUpdater
    public void onDisplayContentDisplayPropertiesPostChanged(int i, int i2, android.window.DisplayAreaInfo displayAreaInfo) {
        this.mDisplayContent.mDisplaySwitchTransitionLauncher.onDisplayUpdated(i, i2, displayAreaInfo);
    }
}
