package com.android.server.display.mode;

/* loaded from: classes.dex */
class SupportedModesVote implements com.android.server.display.mode.Vote {
    final java.util.List<com.android.server.display.mode.SupportedModesVote.SupportedMode> mSupportedModes;

    SupportedModesVote(java.util.List<com.android.server.display.mode.SupportedModesVote.SupportedMode> list) {
        this.mSupportedModes = java.util.Collections.unmodifiableList(list);
    }

    @Override // com.android.server.display.mode.Vote
    public void updateSummary(com.android.server.display.mode.VoteSummary voteSummary) {
        if (voteSummary.supportedModes == null) {
            voteSummary.supportedModes = new java.util.ArrayList(this.mSupportedModes);
        } else {
            voteSummary.supportedModes.retainAll(this.mSupportedModes);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.display.mode.SupportedModesVote)) {
            return false;
        }
        return this.mSupportedModes.equals(((com.android.server.display.mode.SupportedModesVote) obj).mSupportedModes);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mSupportedModes);
    }

    public java.lang.String toString() {
        return "SupportedModesVote{ mSupportedModes=" + this.mSupportedModes + " }";
    }

    static class SupportedMode {
        final float mPeakRefreshRate;
        final float mVsyncRate;

        SupportedMode(float f, float f2) {
            this.mPeakRefreshRate = f;
            this.mVsyncRate = f2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.display.mode.SupportedModesVote.SupportedMode)) {
                return false;
            }
            com.android.server.display.mode.SupportedModesVote.SupportedMode supportedMode = (com.android.server.display.mode.SupportedModesVote.SupportedMode) obj;
            return java.lang.Float.compare(supportedMode.mPeakRefreshRate, this.mPeakRefreshRate) == 0 && java.lang.Float.compare(supportedMode.mVsyncRate, this.mVsyncRate) == 0;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Float.valueOf(this.mPeakRefreshRate), java.lang.Float.valueOf(this.mVsyncRate));
        }

        public java.lang.String toString() {
            return "SupportedMode{ mPeakRefreshRate=" + this.mPeakRefreshRate + ", mVsyncRate=" + this.mVsyncRate + " }";
        }
    }
}
