package com.android.server.display.mode;

/* loaded from: classes.dex */
class DisableRefreshRateSwitchingVote implements com.android.server.display.mode.Vote {
    final boolean mDisableRefreshRateSwitching;

    DisableRefreshRateSwitchingVote(boolean z) {
        this.mDisableRefreshRateSwitching = z;
    }

    @Override // com.android.server.display.mode.Vote
    public void updateSummary(com.android.server.display.mode.VoteSummary voteSummary) {
        voteSummary.disableRefreshRateSwitching = voteSummary.disableRefreshRateSwitching || this.mDisableRefreshRateSwitching;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof com.android.server.display.mode.DisableRefreshRateSwitchingVote) && this.mDisableRefreshRateSwitching == ((com.android.server.display.mode.DisableRefreshRateSwitchingVote) obj).mDisableRefreshRateSwitching;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mDisableRefreshRateSwitching));
    }

    public java.lang.String toString() {
        return "DisableRefreshRateSwitchingVote{ mDisableRefreshRateSwitching=" + this.mDisableRefreshRateSwitching + " }";
    }
}
