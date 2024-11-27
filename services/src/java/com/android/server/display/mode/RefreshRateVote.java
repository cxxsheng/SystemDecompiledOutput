package com.android.server.display.mode;

/* loaded from: classes.dex */
abstract class RefreshRateVote implements com.android.server.display.mode.Vote {
    final float mMaxRefreshRate;
    final float mMinRefreshRate;

    RefreshRateVote(float f, float f2) {
        this.mMinRefreshRate = f;
        this.mMaxRefreshRate = f2;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.display.mode.RefreshRateVote)) {
            return false;
        }
        com.android.server.display.mode.RefreshRateVote refreshRateVote = (com.android.server.display.mode.RefreshRateVote) obj;
        return java.lang.Float.compare(refreshRateVote.mMinRefreshRate, this.mMinRefreshRate) == 0 && java.lang.Float.compare(refreshRateVote.mMaxRefreshRate, this.mMaxRefreshRate) == 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mMinRefreshRate), java.lang.Float.valueOf(this.mMaxRefreshRate));
    }

    public java.lang.String toString() {
        return "RefreshRateVote{  mMinRefreshRate=" + this.mMinRefreshRate + ", mMaxRefreshRate=" + this.mMaxRefreshRate + " }";
    }

    static class RenderVote extends com.android.server.display.mode.RefreshRateVote {
        RenderVote(float f, float f2) {
            super(f, f2);
        }

        @Override // com.android.server.display.mode.Vote
        public void updateSummary(com.android.server.display.mode.VoteSummary voteSummary) {
            voteSummary.minRenderFrameRate = java.lang.Math.max(voteSummary.minRenderFrameRate, this.mMinRefreshRate);
            voteSummary.maxRenderFrameRate = java.lang.Math.min(voteSummary.maxRenderFrameRate, this.mMaxRefreshRate);
            voteSummary.minPhysicalRefreshRate = java.lang.Math.max(voteSummary.minPhysicalRefreshRate, this.mMinRefreshRate);
        }

        @Override // com.android.server.display.mode.RefreshRateVote
        public boolean equals(java.lang.Object obj) {
            if (obj instanceof com.android.server.display.mode.RefreshRateVote.RenderVote) {
                return super.equals(obj);
            }
            return false;
        }

        @Override // com.android.server.display.mode.RefreshRateVote
        public java.lang.String toString() {
            return "RenderVote{ " + super.toString() + " }";
        }
    }

    static class PhysicalVote extends com.android.server.display.mode.RefreshRateVote {
        PhysicalVote(float f, float f2) {
            super(f, f2);
        }

        @Override // com.android.server.display.mode.Vote
        public void updateSummary(com.android.server.display.mode.VoteSummary voteSummary) {
            voteSummary.minPhysicalRefreshRate = java.lang.Math.max(voteSummary.minPhysicalRefreshRate, this.mMinRefreshRate);
            voteSummary.maxPhysicalRefreshRate = java.lang.Math.min(voteSummary.maxPhysicalRefreshRate, this.mMaxRefreshRate);
            voteSummary.maxRenderFrameRate = java.lang.Math.min(voteSummary.maxRenderFrameRate, this.mMaxRefreshRate);
        }

        @Override // com.android.server.display.mode.RefreshRateVote
        public boolean equals(java.lang.Object obj) {
            if (obj instanceof com.android.server.display.mode.RefreshRateVote.PhysicalVote) {
                return super.equals(obj);
            }
            return false;
        }

        @Override // com.android.server.display.mode.RefreshRateVote
        public java.lang.String toString() {
            return "PhysicalVote{ " + super.toString() + " }";
        }
    }
}
