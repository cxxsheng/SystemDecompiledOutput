package com.android.server.display.mode;

/* loaded from: classes.dex */
class VotesStatsReporter {
    private static final int REFRESH_RATE_NOT_LIMITED = 1000;
    private static final java.lang.String TAG = "VotesStatsReporter";
    private final boolean mFrameworkStatsLogReportingEnabled;
    private final boolean mIgnoredRenderRate;
    private int mLastMinPriorityReported = 18;

    public VotesStatsReporter(boolean z, boolean z2) {
        this.mIgnoredRenderRate = z;
        this.mFrameworkStatsLogReportingEnabled = z2;
    }

    void reportVoteChanged(int i, int i2, @android.annotation.Nullable com.android.server.display.mode.Vote vote) {
        if (vote == null) {
            reportVoteRemoved(i, i2);
        } else {
            reportVoteAdded(i, i2, vote);
        }
    }

    private void reportVoteAdded(int i, int i2, @android.annotation.NonNull com.android.server.display.mode.Vote vote) {
        int maxRefreshRate = getMaxRefreshRate(vote, this.mIgnoredRenderRate);
        android.os.Trace.traceCounter(131072L, "VotesStatsReporter." + i + ":" + com.android.server.display.mode.Vote.priorityToString(i2), maxRefreshRate);
        if (this.mFrameworkStatsLogReportingEnabled) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_MODE_DIRECTOR_VOTE_CHANGED, i, i2, 1, maxRefreshRate, -1);
        }
    }

    private void reportVoteRemoved(int i, int i2) {
        android.os.Trace.traceCounter(131072L, "VotesStatsReporter." + i + ":" + com.android.server.display.mode.Vote.priorityToString(i2), -1);
        if (this.mFrameworkStatsLogReportingEnabled) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_MODE_DIRECTOR_VOTE_CHANGED, i, i2, 3, -1, -1);
        }
    }

    void reportVotesActivated(int i, int i2, @android.annotation.Nullable android.view.Display.Mode mode, android.util.SparseArray<com.android.server.display.mode.Vote> sparseArray) {
        com.android.server.display.mode.Vote vote;
        if (!this.mFrameworkStatsLogReportingEnabled) {
            return;
        }
        int refreshRate = mode != null ? (int) mode.getRefreshRate() : -1;
        for (int i3 = 0; i3 <= 17; i3++) {
            if ((i3 >= this.mLastMinPriorityReported || i3 >= i2) && (vote = sparseArray.get(i3)) != null) {
                if (i3 >= this.mLastMinPriorityReported && i3 < i2) {
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_MODE_DIRECTOR_VOTE_CHANGED, i, i3, 1, getMaxRefreshRate(vote, this.mIgnoredRenderRate), refreshRate);
                }
                if (i3 >= i2 && i3 < this.mLastMinPriorityReported) {
                    com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DISPLAY_MODE_DIRECTOR_VOTE_CHANGED, i, i3, 2, getMaxRefreshRate(vote, this.mIgnoredRenderRate), refreshRate);
                }
                this.mLastMinPriorityReported = i2;
            }
        }
    }

    private static int getMaxRefreshRate(@android.annotation.NonNull com.android.server.display.mode.Vote vote, boolean z) {
        if (vote instanceof com.android.server.display.mode.RefreshRateVote.PhysicalVote) {
            return (int) ((com.android.server.display.mode.RefreshRateVote.PhysicalVote) vote).mMaxRefreshRate;
        }
        if (!z && (vote instanceof com.android.server.display.mode.RefreshRateVote.RenderVote)) {
            return (int) ((com.android.server.display.mode.RefreshRateVote.RenderVote) vote).mMaxRefreshRate;
        }
        if (vote instanceof com.android.server.display.mode.SupportedModesVote) {
            java.util.Iterator<com.android.server.display.mode.SupportedModesVote.SupportedMode> it = ((com.android.server.display.mode.SupportedModesVote) vote).mSupportedModes.iterator();
            int i = 0;
            while (it.hasNext()) {
                i = java.lang.Math.max(i, (int) it.next().mPeakRefreshRate);
            }
            return i;
        }
        int i2 = 1000;
        if (!(vote instanceof com.android.server.display.mode.CombinedVote)) {
            return 1000;
        }
        java.util.Iterator<com.android.server.display.mode.Vote> it2 = ((com.android.server.display.mode.CombinedVote) vote).mVotes.iterator();
        while (it2.hasNext()) {
            i2 = java.lang.Math.min(i2, getMaxRefreshRate(it2.next(), z));
        }
        return i2;
    }
}
