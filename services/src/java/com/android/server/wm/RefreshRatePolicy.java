package com.android.server.wm;

/* loaded from: classes3.dex */
class RefreshRatePolicy {
    static final int LAYER_PRIORITY_FOCUSED_WITHOUT_MODE = 1;
    static final int LAYER_PRIORITY_FOCUSED_WITH_MODE = 0;
    static final int LAYER_PRIORITY_NOT_FOCUSED_WITH_MODE = 2;
    static final int LAYER_PRIORITY_UNSET = -1;
    private final android.view.Display.Mode mDefaultMode;
    private final android.view.DisplayInfo mDisplayInfo;
    private final com.android.server.wm.HighRefreshRateDenylist mHighRefreshRateDenylist;
    private final android.view.Display.Mode mLowRefreshRateMode;
    private float mMaxSupportedRefreshRate;
    private float mMinSupportedRefreshRate;
    private final com.android.server.wm.RefreshRatePolicy.PackageRefreshRate mNonHighRefreshRatePackages = new com.android.server.wm.RefreshRatePolicy.PackageRefreshRate();
    private final com.android.server.wm.WindowManagerService mWmService;

    class PackageRefreshRate {
        private final java.util.HashMap<java.lang.String, android.view.SurfaceControl.RefreshRateRange> mPackages = new java.util.HashMap<>();

        PackageRefreshRate() {
        }

        public void add(java.lang.String str, float f, float f2) {
            this.mPackages.put(str, new android.view.SurfaceControl.RefreshRateRange(java.lang.Math.max(com.android.server.wm.RefreshRatePolicy.this.mMinSupportedRefreshRate, f), java.lang.Math.min(com.android.server.wm.RefreshRatePolicy.this.mMaxSupportedRefreshRate, f2)));
        }

        public android.view.SurfaceControl.RefreshRateRange get(java.lang.String str) {
            return this.mPackages.get(str);
        }

        public void remove(java.lang.String str) {
            this.mPackages.remove(str);
        }
    }

    RefreshRatePolicy(com.android.server.wm.WindowManagerService windowManagerService, android.view.DisplayInfo displayInfo, com.android.server.wm.HighRefreshRateDenylist highRefreshRateDenylist) {
        this.mDisplayInfo = displayInfo;
        this.mDefaultMode = displayInfo.getDefaultMode();
        this.mLowRefreshRateMode = findLowRefreshRateMode(displayInfo, this.mDefaultMode);
        this.mHighRefreshRateDenylist = highRefreshRateDenylist;
        this.mWmService = windowManagerService;
    }

    private android.view.Display.Mode findLowRefreshRateMode(android.view.DisplayInfo displayInfo, android.view.Display.Mode mode) {
        float[] defaultRefreshRates = displayInfo.getDefaultRefreshRates();
        float refreshRate = mode.getRefreshRate();
        this.mMinSupportedRefreshRate = refreshRate;
        this.mMaxSupportedRefreshRate = refreshRate;
        for (int length = defaultRefreshRates.length - 1; length >= 0; length--) {
            this.mMinSupportedRefreshRate = java.lang.Math.min(this.mMinSupportedRefreshRate, defaultRefreshRates[length]);
            this.mMaxSupportedRefreshRate = java.lang.Math.max(this.mMaxSupportedRefreshRate, defaultRefreshRates[length]);
            if (defaultRefreshRates[length] >= 60.0f && defaultRefreshRates[length] < refreshRate) {
                refreshRate = defaultRefreshRates[length];
            }
        }
        return displayInfo.findDefaultModeByRefreshRate(refreshRate);
    }

    void addRefreshRateRangeForPackage(java.lang.String str, float f, float f2) {
        this.mNonHighRefreshRatePackages.add(str, f, f2);
        this.mWmService.requestTraversal();
    }

    void removeRefreshRateRangeForPackage(java.lang.String str) {
        this.mNonHighRefreshRatePackages.remove(str);
        this.mWmService.requestTraversal();
    }

    int getPreferredModeId(com.android.server.wm.WindowState windowState) {
        android.view.Display.Mode mode;
        int i = windowState.mAttrs.preferredDisplayModeId;
        if (i <= 0) {
            return 0;
        }
        if (!com.android.window.flags.Flags.explicitRefreshRateHints() && windowState.isAnimationRunningSelfOrParent()) {
            android.view.Display.Mode[] modeArr = this.mDisplayInfo.supportedModes;
            int length = modeArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    mode = null;
                    break;
                }
                mode = modeArr[i2];
                if (i == mode.getModeId()) {
                    break;
                }
                i2++;
            }
            if (mode != null) {
                int physicalWidth = mode.getPhysicalWidth();
                int physicalHeight = mode.getPhysicalHeight();
                if ((physicalWidth != this.mDefaultMode.getPhysicalWidth() || physicalHeight != this.mDefaultMode.getPhysicalHeight()) && physicalWidth == this.mDisplayInfo.getNaturalWidth() && physicalHeight == this.mDisplayInfo.getNaturalHeight()) {
                    return i;
                }
            }
            return 0;
        }
        return i;
    }

    int calculatePriority(com.android.server.wm.WindowState windowState) {
        boolean isFocused = windowState.isFocused();
        int preferredModeId = getPreferredModeId(windowState);
        if (!isFocused && preferredModeId > 0) {
            return 2;
        }
        if (isFocused && preferredModeId == 0) {
            return 1;
        }
        if (isFocused && preferredModeId > 0) {
            return 0;
        }
        return -1;
    }

    public static class FrameRateVote {
        int mCompatibility;
        float mRefreshRate;
        int mSelectionStrategy;

        FrameRateVote(float f, int i, int i2) {
            update(f, i, i2);
        }

        FrameRateVote() {
            reset();
        }

        boolean update(float f, int i, int i2) {
            if (!refreshRateEquals(f) || this.mCompatibility != i || this.mSelectionStrategy != i2) {
                this.mRefreshRate = f;
                this.mCompatibility = i;
                this.mSelectionStrategy = i2;
                return true;
            }
            return false;
        }

        boolean reset() {
            return update(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0, 0);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.wm.RefreshRatePolicy.FrameRateVote)) {
                return false;
            }
            com.android.server.wm.RefreshRatePolicy.FrameRateVote frameRateVote = (com.android.server.wm.RefreshRatePolicy.FrameRateVote) obj;
            return refreshRateEquals(frameRateVote.mRefreshRate) && this.mCompatibility == frameRateVote.mCompatibility && this.mSelectionStrategy == frameRateVote.mSelectionStrategy;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Float.valueOf(this.mRefreshRate), java.lang.Integer.valueOf(this.mCompatibility), java.lang.Integer.valueOf(this.mSelectionStrategy));
        }

        public java.lang.String toString() {
            return "mRefreshRate=" + this.mRefreshRate + ", mCompatibility=" + this.mCompatibility + ", mSelectionStrategy=" + this.mSelectionStrategy;
        }

        private boolean refreshRateEquals(float f) {
            return this.mRefreshRate <= f + 0.01f && this.mRefreshRate >= f - 0.01f;
        }
    }

    boolean updateFrameRateVote(com.android.server.wm.WindowState windowState) {
        int i;
        int refreshRateSwitchingType = this.mWmService.mDisplayManagerInternal.getRefreshRateSwitchingType();
        if (refreshRateSwitchingType == 0) {
            return windowState.mFrameRateVote.reset();
        }
        if (!com.android.window.flags.Flags.explicitRefreshRateHints() && windowState.isAnimationRunningSelfOrParent()) {
            return windowState.mFrameRateVote.reset();
        }
        if (refreshRateSwitchingType != 3 && (i = windowState.mAttrs.preferredDisplayModeId) > 0) {
            for (android.view.Display.Mode mode : this.mDisplayInfo.supportedModes) {
                if (i == mode.getModeId()) {
                    return windowState.mFrameRateVote.update(mode.getRefreshRate(), 100, 1);
                }
            }
        }
        if (windowState.mAttrs.preferredRefreshRate > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return windowState.mFrameRateVote.update(windowState.mAttrs.preferredRefreshRate, 0, 1);
        }
        if (refreshRateSwitchingType != 3) {
            if (this.mHighRefreshRateDenylist.isDenylisted(windowState.getOwningPackage())) {
                return windowState.mFrameRateVote.update(this.mLowRefreshRateMode.getRefreshRate(), 100, 1);
            }
        }
        return windowState.mFrameRateVote.reset();
    }

    float getPreferredMinRefreshRate(com.android.server.wm.WindowState windowState) {
        if (windowState.isAnimationRunningSelfOrParent()) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        if (windowState.mAttrs.preferredMinDisplayRefreshRate > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return windowState.mAttrs.preferredMinDisplayRefreshRate;
        }
        android.view.SurfaceControl.RefreshRateRange refreshRateRange = this.mNonHighRefreshRatePackages.get(windowState.getOwningPackage());
        return refreshRateRange != null ? refreshRateRange.min : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    float getPreferredMaxRefreshRate(com.android.server.wm.WindowState windowState) {
        if (windowState.isAnimationRunningSelfOrParent()) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        if (windowState.mAttrs.preferredMaxDisplayRefreshRate > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return windowState.mAttrs.preferredMaxDisplayRefreshRate;
        }
        android.view.SurfaceControl.RefreshRateRange refreshRateRange = this.mNonHighRefreshRatePackages.get(windowState.getOwningPackage());
        return refreshRateRange != null ? refreshRateRange.max : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }
}
