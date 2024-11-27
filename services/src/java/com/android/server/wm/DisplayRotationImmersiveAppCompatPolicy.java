package com.android.server.wm;

/* loaded from: classes3.dex */
final class DisplayRotationImmersiveAppCompatPolicy {
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final com.android.server.wm.DisplayRotation mDisplayRotation;
    private final com.android.server.wm.LetterboxConfiguration mLetterboxConfiguration;

    @android.annotation.Nullable
    static com.android.server.wm.DisplayRotationImmersiveAppCompatPolicy createIfNeeded(@android.annotation.NonNull com.android.server.wm.LetterboxConfiguration letterboxConfiguration, @android.annotation.NonNull com.android.server.wm.DisplayRotation displayRotation, @android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (!letterboxConfiguration.isDisplayRotationImmersiveAppCompatPolicyEnabledAtBuildTime()) {
            return null;
        }
        return new com.android.server.wm.DisplayRotationImmersiveAppCompatPolicy(letterboxConfiguration, displayRotation, displayContent);
    }

    private DisplayRotationImmersiveAppCompatPolicy(@android.annotation.NonNull com.android.server.wm.LetterboxConfiguration letterboxConfiguration, @android.annotation.NonNull com.android.server.wm.DisplayRotation displayRotation, @android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        this.mDisplayRotation = displayRotation;
        this.mLetterboxConfiguration = letterboxConfiguration;
        this.mDisplayContent = displayContent;
    }

    boolean isRotationLockEnforced(int i) {
        boolean isRotationLockEnforcedLocked;
        if (!this.mLetterboxConfiguration.isDisplayRotationImmersiveAppCompatPolicyEnabled()) {
            return false;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mDisplayContent.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                isRotationLockEnforcedLocked = isRotationLockEnforcedLocked(i);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return isRotationLockEnforcedLocked;
    }

    private boolean isRotationLockEnforcedLocked(int i) {
        com.android.server.wm.ActivityRecord activityRecord;
        return (!this.mDisplayContent.getIgnoreOrientationRequest() || (activityRecord = this.mDisplayContent.topRunningActivity()) == null || !hasRequestedToHideStatusAndNavBars(activityRecord) || activityRecord.getTask() == null || activityRecord.getTask().getWindowingMode() != 1 || activityRecord.areBoundsLetterboxed() || activityRecord.getRequestedConfigurationOrientation() == 0 || activityRecord.getRequestedConfigurationOrientation() == surfaceRotationToConfigurationOrientation(i)) ? false : true;
    }

    private boolean hasRequestedToHideStatusAndNavBars(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowState findMainWindow = activityRecord.findMainWindow();
        return findMainWindow != null && (findMainWindow.getRequestedVisibleTypes() & (android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars())) == 0;
    }

    private int surfaceRotationToConfigurationOrientation(int i) {
        if (this.mDisplayRotation.isAnyPortrait(i)) {
            return 1;
        }
        if (this.mDisplayRotation.isLandscapeOrSeascape(i)) {
            return 2;
        }
        return 0;
    }
}
