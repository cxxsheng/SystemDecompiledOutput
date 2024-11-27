package com.android.server.display.mode;

/* loaded from: classes.dex */
class BaseModeRefreshRateVote implements com.android.server.display.mode.Vote {
    final float mAppRequestBaseModeRefreshRate;

    BaseModeRefreshRateVote(float f) {
        this.mAppRequestBaseModeRefreshRate = f;
    }

    @Override // com.android.server.display.mode.Vote
    public void updateSummary(com.android.server.display.mode.VoteSummary voteSummary) {
        if (voteSummary.appRequestBaseModeRefreshRate == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && this.mAppRequestBaseModeRefreshRate > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            voteSummary.appRequestBaseModeRefreshRate = this.mAppRequestBaseModeRefreshRate;
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof com.android.server.display.mode.BaseModeRefreshRateVote) && java.lang.Float.compare(((com.android.server.display.mode.BaseModeRefreshRateVote) obj).mAppRequestBaseModeRefreshRate, this.mAppRequestBaseModeRefreshRate) == 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mAppRequestBaseModeRefreshRate));
    }

    public java.lang.String toString() {
        return "BaseModeRefreshRateVote{ mAppRequestBaseModeRefreshRate=" + this.mAppRequestBaseModeRefreshRate + " }";
    }
}
