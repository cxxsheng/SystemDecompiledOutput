package com.android.server.display.mode;

/* loaded from: classes.dex */
final class VoteSummary {
    private static final float FLOAT_TOLERANCE = 0.01f;
    private static final java.lang.String TAG = "VoteSummary";
    public float appRequestBaseModeRefreshRate;
    public boolean disableRefreshRateSwitching;
    public int height;
    final boolean mIsDisplayResolutionRangeVotingEnabled;
    private final boolean mLoggingEnabled;
    private final boolean mSupportedModesVoteEnabled;
    private final boolean mSupportsFrameRateOverride;
    public float maxPhysicalRefreshRate;
    public float maxRenderFrameRate;
    public int minHeight;
    public float minPhysicalRefreshRate;
    public float minRenderFrameRate;
    public int minWidth;
    public java.util.List<com.android.server.display.mode.SupportedModesVote.SupportedMode> supportedModes;
    public int width;

    VoteSummary(boolean z, boolean z2, boolean z3, boolean z4) {
        this.mIsDisplayResolutionRangeVotingEnabled = z;
        this.mSupportedModesVoteEnabled = z2;
        this.mLoggingEnabled = z3;
        this.mSupportsFrameRateOverride = z4;
        reset();
    }

    void applyVotes(android.util.SparseArray<com.android.server.display.mode.Vote> sparseArray, int i, int i2) {
        reset();
        for (int i3 = i2; i3 >= i; i3--) {
            com.android.server.display.mode.Vote vote = sparseArray.get(i3);
            if (vote != null) {
                vote.updateSummary(this);
            }
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "applyVotes for range [" + com.android.server.display.mode.Vote.priorityToString(i) + ", " + com.android.server.display.mode.Vote.priorityToString(i2) + "]: " + this);
        }
    }

    void adjustSize(android.view.Display.Mode mode, android.view.Display.Mode[] modeArr) {
        if (this.height == -1 || this.width == -1) {
            this.width = mode.getPhysicalWidth();
            this.height = mode.getPhysicalHeight();
        } else if (this.mIsDisplayResolutionRangeVotingEnabled) {
            updateSummaryWithBestAllowedResolution(modeArr);
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "adjustSize: " + this);
        }
    }

    void limitRefreshRanges(com.android.server.display.mode.VoteSummary voteSummary) {
        this.minPhysicalRefreshRate = java.lang.Math.min(this.minPhysicalRefreshRate, voteSummary.minPhysicalRefreshRate);
        this.maxPhysicalRefreshRate = java.lang.Math.max(this.maxPhysicalRefreshRate, voteSummary.maxPhysicalRefreshRate);
        this.minRenderFrameRate = java.lang.Math.min(this.minRenderFrameRate, voteSummary.minRenderFrameRate);
        this.maxRenderFrameRate = java.lang.Math.max(this.maxRenderFrameRate, voteSummary.maxRenderFrameRate);
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "limitRefreshRanges: " + this);
        }
    }

    java.util.List<android.view.Display.Mode> filterModes(android.view.Display.Mode[] modeArr) {
        if (!isValid()) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z = this.appRequestBaseModeRefreshRate > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        for (android.view.Display.Mode mode : modeArr) {
            if (validateModeSupported(mode) && validateModeSize(mode) && validateModeWithinPhysicalRefreshRange(mode) && validateModeWithinRenderRefreshRange(mode) && validateModeRenderRateAchievable(mode)) {
                arrayList.add(mode);
                if (equalsWithinFloatTolerance(mode.getRefreshRate(), this.appRequestBaseModeRefreshRate)) {
                    z = false;
                }
            }
        }
        if (z) {
            return new java.util.ArrayList();
        }
        return arrayList;
    }

    android.view.Display.Mode selectBaseMode(java.util.List<android.view.Display.Mode> list, android.view.Display.Mode mode) {
        float refreshRate = this.appRequestBaseModeRefreshRate > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? this.appRequestBaseModeRefreshRate : mode.getRefreshRate();
        for (android.view.Display.Mode mode2 : list) {
            if (equalsWithinFloatTolerance(refreshRate, mode2.getRefreshRate())) {
                return mode2;
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    void disableModeSwitching(float f) {
        this.maxPhysicalRefreshRate = f;
        this.minPhysicalRefreshRate = f;
        this.maxRenderFrameRate = java.lang.Math.min(this.maxRenderFrameRate, f);
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "Disabled mode switching on summary: " + this);
        }
    }

    void disableRenderRateSwitching(float f) {
        this.minRenderFrameRate = this.maxRenderFrameRate;
        if (!isRenderRateAchievable(f)) {
            this.maxRenderFrameRate = f;
            this.minRenderFrameRate = f;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "Disabled render rate switching on summary: " + this);
        }
    }

    private boolean validateModeSize(android.view.Display.Mode mode) {
        if (mode.getPhysicalWidth() != this.width || mode.getPhysicalHeight() != this.height) {
            if (this.mLoggingEnabled) {
                android.util.Slog.w(TAG, "Discarding mode " + mode.getModeId() + ", wrong size: desiredWidth=" + this.width + ": desiredHeight=" + this.height + ": actualWidth=" + mode.getPhysicalWidth() + ": actualHeight=" + mode.getPhysicalHeight());
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean validateModeWithinPhysicalRefreshRange(android.view.Display.Mode mode) {
        float refreshRate = mode.getRefreshRate();
        if (refreshRate < this.minPhysicalRefreshRate - 0.01f || refreshRate > this.maxPhysicalRefreshRate + 0.01f) {
            if (this.mLoggingEnabled) {
                android.util.Slog.w(TAG, "Discarding mode " + mode.getModeId() + ", outside refresh rate bounds: minPhysicalRefreshRate=" + this.minPhysicalRefreshRate + ", maxPhysicalRefreshRate=" + this.maxPhysicalRefreshRate + ", modeRefreshRate=" + refreshRate);
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean validateModeWithinRenderRefreshRange(android.view.Display.Mode mode) {
        float refreshRate = mode.getRefreshRate();
        if (this.mSupportsFrameRateOverride) {
            return true;
        }
        if (refreshRate < this.minRenderFrameRate - 0.01f || refreshRate > this.maxRenderFrameRate + 0.01f) {
            if (this.mLoggingEnabled) {
                android.util.Slog.w(TAG, "Discarding mode " + mode.getModeId() + ", outside render rate bounds: minRenderFrameRate=" + this.minRenderFrameRate + ", maxRenderFrameRate=" + this.maxRenderFrameRate + ", modeRefreshRate=" + refreshRate);
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean validateModeRenderRateAchievable(android.view.Display.Mode mode) {
        float refreshRate = mode.getRefreshRate();
        if (!isRenderRateAchievable(refreshRate)) {
            if (this.mLoggingEnabled) {
                android.util.Slog.w(TAG, "Discarding mode " + mode.getModeId() + ", outside frame rate bounds: minRenderFrameRate=" + this.minRenderFrameRate + ", maxRenderFrameRate=" + this.maxRenderFrameRate + ", modePhysicalRefreshRate=" + refreshRate);
                return false;
            }
            return false;
        }
        return true;
    }

    private boolean validateModeSupported(android.view.Display.Mode mode) {
        if (this.supportedModes == null || !this.mSupportedModesVoteEnabled) {
            return true;
        }
        for (com.android.server.display.mode.SupportedModesVote.SupportedMode supportedMode : this.supportedModes) {
            if (equalsWithinFloatTolerance(mode.getRefreshRate(), supportedMode.mPeakRefreshRate) && equalsWithinFloatTolerance(mode.getVsyncRate(), supportedMode.mVsyncRate)) {
                return true;
            }
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.w(TAG, "Discarding mode " + mode.getModeId() + ", supportedMode not found: mode.refreshRate=" + mode.getRefreshRate() + ", mode.vsyncRate=" + mode.getVsyncRate() + ", supportedModes=" + this.supportedModes);
            return false;
        }
        return false;
    }

    private boolean isRenderRateAchievable(float f) {
        return f / ((float) ((int) java.lang.Math.ceil((double) ((f / this.maxRenderFrameRate) - 0.01f)))) >= this.minRenderFrameRate - 0.01f;
    }

    private boolean isValid() {
        if (this.minRenderFrameRate > this.maxRenderFrameRate + 0.01f) {
            if (this.mLoggingEnabled) {
                android.util.Slog.w(TAG, "Vote summary resulted in empty set (invalid frame rate range): minRenderFrameRate=" + this.minRenderFrameRate + ", maxRenderFrameRate=" + this.maxRenderFrameRate);
            }
            return false;
        }
        if (this.supportedModes != null && this.mSupportedModesVoteEnabled && this.supportedModes.isEmpty()) {
            if (this.mLoggingEnabled) {
                android.util.Slog.w(TAG, "Vote summary resulted in empty set (empty supportedModes)");
            }
            return false;
        }
        return true;
    }

    private void updateSummaryWithBestAllowedResolution(android.view.Display.Mode[] modeArr) {
        int physicalHeight;
        int i = this.width;
        int i2 = this.height;
        this.width = -1;
        this.height = -1;
        int i3 = 0;
        for (android.view.Display.Mode mode : modeArr) {
            if (mode.getPhysicalWidth() <= i && mode.getPhysicalHeight() <= i2 && mode.getPhysicalWidth() >= this.minWidth && mode.getPhysicalHeight() >= this.minHeight && mode.getRefreshRate() >= this.minPhysicalRefreshRate - 0.01f && mode.getRefreshRate() <= this.maxPhysicalRefreshRate + 0.01f && ((physicalHeight = mode.getPhysicalHeight() * mode.getPhysicalWidth()) > i3 || (mode.getPhysicalWidth() == i && mode.getPhysicalHeight() == i2))) {
                this.width = mode.getPhysicalWidth();
                this.height = mode.getPhysicalHeight();
                i3 = physicalHeight;
            }
        }
    }

    private void reset() {
        this.minPhysicalRefreshRate = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.maxPhysicalRefreshRate = Float.POSITIVE_INFINITY;
        this.minRenderFrameRate = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.maxRenderFrameRate = Float.POSITIVE_INFINITY;
        this.width = -1;
        this.height = -1;
        this.minWidth = 0;
        this.minHeight = 0;
        this.disableRefreshRateSwitching = false;
        this.appRequestBaseModeRefreshRate = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.supportedModes = null;
        if (this.mLoggingEnabled) {
            android.util.Slog.i(TAG, "Summary reset: " + this);
        }
    }

    private static boolean equalsWithinFloatTolerance(float f, float f2) {
        return f >= f2 - 0.01f && f <= f2 + 0.01f;
    }

    public java.lang.String toString() {
        return "VoteSummary{ minPhysicalRefreshRate=" + this.minPhysicalRefreshRate + ", maxPhysicalRefreshRate=" + this.maxPhysicalRefreshRate + ", minRenderFrameRate=" + this.minRenderFrameRate + ", maxRenderFrameRate=" + this.maxRenderFrameRate + ", width=" + this.width + ", height=" + this.height + ", minWidth=" + this.minWidth + ", minHeight=" + this.minHeight + ", disableRefreshRateSwitching=" + this.disableRefreshRateSwitching + ", appRequestBaseModeRefreshRate=" + this.appRequestBaseModeRefreshRate + ", supportedModes=" + this.supportedModes + ", mIsDisplayResolutionRangeVotingEnabled=" + this.mIsDisplayResolutionRangeVotingEnabled + ", mSupportedModesVoteEnabled=" + this.mSupportedModesVoteEnabled + ", mSupportsFrameRateOverride=" + this.mSupportsFrameRateOverride + " }";
    }
}
